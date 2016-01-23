package org.joml;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Class for polygon/point intersection tests when testing many points against the same static concave or convex polygon.
 * <p>
 * This is an implementation of the algorithm described in <a href="http://alienryderflex.com/polygon/">http://alienryderflex.com</a> and augmented with using a
 * custom interval tree to avoid testing all polygon edges against a point, but only those that intersect the imaginary ray along the same y co-ordinate of the
 * search point.
 * <p>
 * The method {@link #pointInPolygon(float, float)} in this class is <i>not</i> thread-safe!
 * <p>
 * Reference: <a href="http://alienryderflex.com/polygon/">http://alienryderflex.com</a>
 * 
 * @author Kai Burjack
 */
public class PolygonPointIntersection {

    class ByStartComparator implements Comparator {
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

    class ByEndComparator implements Comparator {
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

    class Interval {
        float start, end;
        int i, j;
    }

    class IntervalTree {
        float center;
        IntervalTree left;
        IntervalTree right;
        List byBeginning;
        List byEnding;
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

    private float[] verticesXY;
    private float minX, minY, maxX, maxY;
    private Interval[] intervals; // <- used as working memory for the pointInPolygon() method
    private IntervalTree tree;
    private ByStartComparator byStartComparator = new ByStartComparator();
    private ByEndComparator byEndComparator = new ByEndComparator();

    /**
     * Create a new {@link PolygonPointIntersection} object with the given polygon vertices.
     * <p>
     * The <code>verticesXY</code> array contains the x and y coordinates of all vertices. This array will not be copied so its content must remain constant for
     * as long as the PolygonPointIntersection is used with it.
     * 
     * @param verticesXY
     *            contains the x and y coordinates of all vertices
     */
    public PolygonPointIntersection(float[] verticesXY) {
        set(verticesXY);
    }

    /**
     * Reinitialize this {@link PolygonPointIntersection} object with the given polygon vertices.
     * <p>
     * The <code>verticesXY</code> array contains the x and y coordinates of all vertices. This array will not be copied so its content must remain constant for
     * as long as the PolygonPointIntersection is used with it.
     * 
     * @param verticesXY
     *            contains the x and y coordinates of all vertices
     */
    public void set(float[] verticesXY) {
        this.verticesXY = verticesXY;
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
        int maxCount = (byStart != null ? byStart.size() : 0) + (byEnd != null ? byEnd.size() : 0);
        if (left != null) {
            tree.left = buildTree(left, (leftMin + leftMax) / 2.0f);
            maxCount = tree.left.maxCount > maxCount ? tree.left.maxCount : maxCount;
        }
        if (right != null) {
            tree.right = buildTree(right, (rightMin + rightMax) / 2.0f);
            maxCount = tree.right.maxCount > maxCount ? tree.right.maxCount : maxCount;
        }
        tree.maxCount = maxCount;
        return tree;
    }

    private void buildIntervalTree() {
        int i, j = verticesXY.length / 2 - 1;
        minX = minY = 1E38f;
        maxX = maxY = -1E38f;
        List intervals = new ArrayList(verticesXY.length / 2);
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
            intervals.add(ival);
            j = i;
        }
        tree = buildTree(intervals, (maxY + minY) / 2.0f);
        this.intervals = new Interval[tree.maxCount];
    }

    /**
     * Test whether the given point <tt>(x, y)</tt> lies inside the polygon stored in this {@link PolygonPointIntersection} object.
     * 
     * @param x
     *            the x coordinate of the point to test
     * @param y
     *            the y coordinate of the point to test
     * @return <code>true</code> iff the point lies inside the polygon; <code>false</code> otherwise
     */
    public boolean pointInPolygon(float x, float y) {
        // check bounding box first
        if (maxX < x || maxY < y || minX > x || minY > y)
            return false;
        int c = tree.traverse(y, intervals, 0);
        boolean oddNodes = false;
        for (int r = 0; r < c; r++) {
            Interval ival = intervals[r];
            int i = ival.i;
            int j = ival.j;
            float yi = verticesXY[2 * i + 1];
            float yj = verticesXY[2 * j + 1];
            float xi = verticesXY[2 * i + 0];
            float xj = verticesXY[2 * j + 0];
            if ((yi < y && yj >= y || yj < y && yi >= y) && (xi <= x || xj <= x)) {
                oddNodes ^= (xi + (y - yi) / (yj - yi) * (xj - xi) < x);
            }
        }
        return oddNodes;
    }

}
