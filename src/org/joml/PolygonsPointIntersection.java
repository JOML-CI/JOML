package org.joml;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Class for polygons/point intersection tests when testing many points against the same static concave or convex polygons.
 * <p>
 * This is an implementation of the algorithm described in <a href="http://alienryderflex.com/polygon/">http://alienryderflex.com</a> and augmented with using a
 * custom interval tree to avoid testing all polygon edges against a point, but only those that intersect the imaginary ray along the same y co-ordinate of the
 * search point.
 * <p>
 * Additionally this algorithm is able to handle a set of (overlapping/intersecting) polygons.
 * <p>
 * The method {@link #pointInPolygons(float, float)} in this class is <i>not</i> thread-safe!
 * <p>
 * Reference: <a href="http://alienryderflex.com/polygon/">http://alienryderflex.com</a>
 * 
 * @author Kai Burjack
 */
public class PolygonsPointIntersection {

    static class ByStartComparator implements Comparator {
        public int compare(Object o1, Object o2) {
            Interval i1 = (Interval) o1;
            Interval i2 = (Interval) o2;
            if (i1.start < i2.start)
                return -1;
            else if (i1.start > i2.start)
                return +1;
            return 0;
        }
    }

    static class ByEndComparator implements Comparator {
        public int compare(Object o1, Object o2) {
            Interval i1 = (Interval) o1;
            Interval i2 = (Interval) o2;
            if (i1.end < i2.end)
                return +1;
            else if (i1.end > i2.end)
                return -1;
            return 0;
        }
    }

    static class Interval {
        float start, end;
        int i, j;
        int polygonIndex;
    }

    static class IntervalTree {
        float center;
        IntervalTree left;
        IntervalTree right;
        List/* <Interval> */byBeginning;
        List/* <Interval> */byEnding;
        int maxCount;

        int traverse(float y, Interval[] ivals, int i) {
            if (y == center) {
                int size = byBeginning.size();
                for (int b = 0; b < size; b++) {
                    Interval ival = (Interval) byBeginning.get(b);
                    ivals[i++] = ival;
                }
            } else if (y < center) {
                if (left != null)
                    i = left.traverse(y, ivals, i);
                if (byBeginning != null) {
                    int size = byBeginning.size();
                    for (int b = 0; b < size; b++) {
                        Interval ival = (Interval) byBeginning.get(b);
                        if (ival.start > y)
                            break;
                        ivals[i++] = ival;
                    }
                }
            } else if (y > center) {
                if (right != null)
                    i = right.traverse(y, ivals, i);
                if (byEnding != null) {
                    int size = byEnding.size();
                    for (int b = 0; b < size; b++) {
                        Interval ival = (Interval) byEnding.get(b);
                        if (ival.end < y)
                            break;
                        ivals[i++] = ival;
                    }
                }
            }
            return i;
        }
    }

    private static final ByStartComparator byStartComparator = new ByStartComparator();
    private static final ByEndComparator byEndComparator = new ByEndComparator();

    private float[][] polygons;
    private float minX, minY, maxX, maxY;
    private float centerX, centerY, radiusSquared;
    private Interval[] intervals; // <- used as working memory for the pointInPolygon() method
    private IntervalTree tree;
    private BitSet evenOddPerPolygon = new BitSet(); // <- used as working memory for the pointInPolygon() method

    /**
     * Create a new {@link PolygonsPointIntersection} object with the given polygons.
     * <p>
     * The <code>polygons</code> array contains the a float array per polygon with the x and y coordinates of all the vertices of that polygon. This array will
     * not be copied so its content must remain constant for as long as the PolygonsPointIntersection is used with it.
     * 
     * @param polygons
     *            contains the x and y coordinates of all vertices of all polygons
     */
    public PolygonsPointIntersection(float[][] polygons) {
        set(polygons);
    }

    /**
     * Reinitialize this {@link PolygonsPointIntersection} object with the given polygons.
     * <p>
     * The <code>polygons</code> array contains the a float array per polygon with the x and y coordinates of all the vertices of that polygon. This array will
     * not be copied so its content must remain constant for as long as the PolygonsPointIntersection is used with it.
     * 
     * @param polygons
     *            contains the x and y coordinates of all vertices of all polygons
     */
    public void set(float[][] polygons) {
        this.polygons = polygons;
        buildIntervalTree();
    }

    private IntervalTree buildTree(List intervals, float center) {
        List left = null;
        List right = null;
        List byStart = null;
        List byEnd = null;
        float leftMin = 1E38f, leftMax = -1E38f, rightMin = 1E38f, rightMax = -1E38f;
        for (int i = 0; i < intervals.size(); i++) {
            Interval ival = (Interval) intervals.get(i);
            if (ival.start < center && ival.end < center) {
                if (left == null)
                    left = new ArrayList();
                left.add(ival);
                leftMin = leftMin < ival.start ? leftMin : ival.start;
                leftMax = leftMax > ival.end ? leftMax : ival.end;
            } else if (ival.start > center && ival.end > center) {
                if (right == null)
                    right = new ArrayList();
                right.add(ival);
                rightMin = rightMin < ival.start ? rightMin : ival.start;
                rightMax = rightMax > ival.end ? rightMax : ival.end;
            } else {
                if (byStart == null) {
                    byStart = new ArrayList();
                    byEnd = new ArrayList();
                }
                byStart.add(ival);
                byEnd.add(ival);
            }
        }
        if (byStart != null) {
            Collections.sort(byStart, byStartComparator);
            Collections.sort(byEnd, byEndComparator);
        }
        IntervalTree tree = new IntervalTree();
        tree.byBeginning = byStart;
        tree.byEnding = byEnd;
        tree.center = center;
        int childMaxCount = 0;
        int ownMaxCount = Math.max(byStart != null ? byStart.size() : 0, byEnd != null ? byEnd.size() : 0);
        if (left != null) {
            tree.left = buildTree(left, (leftMin + leftMax) / 2.0f);
            childMaxCount = Math.max(childMaxCount, tree.left.maxCount);
        }
        if (right != null) {
            tree.right = buildTree(right, (rightMin + rightMax) / 2.0f);
            childMaxCount = Math.max(childMaxCount, tree.right.maxCount);
        }
        tree.maxCount = ownMaxCount + childMaxCount;
        return tree;
    }

    private void buildIntervalTree() {
        List intervals = new ArrayList();
        minX = minY = 1E38f;
        maxX = maxY = -1E38f;
        // iterate over all polygons
        for (int p = 0; p < polygons.length; p++) {
            float[] verticesXY = polygons[p];
            int i, j = verticesXY.length / 2 - 1;
            // for each (x, y) vertex of that polygon
            for (i = 0; i < verticesXY.length / 2; i++) {
                float yi = verticesXY[2 * i + 1];
                float xi = verticesXY[2 * i + 0];
                float yj = verticesXY[2 * j + 1];
                minX = xi < minX ? xi : minX;
                minY = yi < minY ? yi : minY;
                maxX = xi > maxX ? xi : maxX;
                maxY = yi > maxY ? yi : maxY;
                Interval ival = new Interval();
                ival.start = yi < yj ? yi : yj;
                ival.end = yj > yi ? yj : yi;
                ival.i = i;
                ival.j = j;
                ival.polygonIndex = p;
                intervals.add(ival);
                j = i;
            }
        }
        centerX = (maxX + minX) * 0.5f;
        centerY = (maxY + minY) * 0.5f;
        float dx = maxX - centerX;
        float dy = maxY - centerY;
        radiusSquared = dx * dx + dy * dy;
        tree = buildTree(intervals, centerY);
        this.intervals = new Interval[tree.maxCount];
    }

    /**
     * Test whether the given point <tt>(x, y)</tt> lies inside any of the polygons stored in this {@link PolygonsPointIntersection} object, and return the
     * indices of all the polygons the point lies inside of, or <code>null</code> if the point lies inside none of the polygons.
     * 
     * @param x
     *            the x coordinate of the point to test
     * @param y
     *            the y coordinate of the point to test
     * @return a {@link BitSet} whose <i>i</i>-th bit is set iff the point lies inside the <i>i</i>-th polygon; or <code>null</code> if the point is not inside
     *         of any polygon
     */
    public BitSet pointInPolygons(float x, float y) {
        // check bounding sphere first
        float dx = (x - centerX);
        float dy = (y - centerY);
        if (dx * dx + dy * dy > radiusSquared)
            return null;
        // check bounding box next
        if (maxX < x || maxY < y || minX > x || minY > y)
            return null;
        // ask interval tree for all polygon edges intersecting 'y'
        int c = tree.traverse(y, intervals, 0);
        evenOddPerPolygon.clear();
        // check the polygon edges
        for (int r = 0; r < c; r++) {
            Interval ival = intervals[r];
            float[] verticesXY = polygons[ival.polygonIndex];
            int i = ival.i;
            int j = ival.j;
            float yi = verticesXY[2 * i + 1];
            float yj = verticesXY[2 * j + 1];
            float xi = verticesXY[2 * i + 0];
            float xj = verticesXY[2 * j + 0];
            if ((yi < y && yj >= y || yj < y && yi >= y) && (xi <= x || xj <= x)) {
                boolean old = evenOddPerPolygon.get(ival.polygonIndex);
                evenOddPerPolygon.set(ival.polygonIndex, old ^ (xi + (y - yi) / (yj - yi) * (xj - xi) < x));
            }
        }
        return evenOddPerPolygon.isEmpty() ? null : evenOddPerPolygon;
    }

}
