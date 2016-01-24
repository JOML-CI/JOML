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
 * This class is thread-safe and can be used in a multithreaded environment when testing many points against the same polygon concurrently.
 * <p>
 * Reference: <a href="http://alienryderflex.com/polygon/">http://alienryderflex.com</a>
 * 
 * @author Kai Burjack
 */
public class PolygonPointIntersection {

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

    public static class Interval {
        float start, end;
        int i, j;
    }

    static class IntervalTreeNode {
        float center;
        float minMax;
        IntervalTreeNode left;
        IntervalTreeNode right;
        List/* <Interval> */byBeginning;
        List/* <Interval> */byEnding;
        int maxCount;

        int traverse(float y, Interval[] ivals, int i) {
            int j = i;
            if (y == center && byBeginning != null) {
                int size = byBeginning.size();
                for (int b = 0; b < size; b++) {
                    Interval ival = (Interval) byBeginning.get(b);
                    ivals[j++] = ival;
                }
            } else if (y < center) {
                if (left != null && left.minMax >= y)
                    j = left.traverse(y, ivals, i);
                if (byBeginning != null) {
                    int size = byBeginning.size();
                    for (int b = 0; b < size; b++) {
                        Interval ival = (Interval) byBeginning.get(b);
                        if (ival.start > y)
                            break;
                        ivals[j++] = ival;
                    }
                }
            } else if (y > center) {
                if (right != null && right.minMax <= y)
                    j = right.traverse(y, ivals, i);
                if (byEnding != null) {
                    int size = byEnding.size();
                    for (int b = 0; b < size; b++) {
                        Interval ival = (Interval) byEnding.get(b);
                        if (ival.end < y)
                            break;
                        ivals[j++] = ival;
                    }
                }
            }
            return j;
        }
    }

    private final ByStartComparator byStartComparator = new ByStartComparator();
    private final ByEndComparator byEndComparator = new ByEndComparator();

    private final float[] verticesXY;
    private float minX, minY, maxX, maxY;
    private float centerX, centerY, radiusSquared;
    private IntervalTreeNode tree;

    /**
     * Create a new {@link PolygonPointIntersection} object with the given polygon vertices.
     * <p>
     * The <code>verticesXY</code> array contains the x and y coordinates of all vertices. This array will not be copied so its content must remain constant for
     * as long as the PolygonPointIntersection is used with it.
     * 
     * @param verticesXY
     *            contains the x and y coordinates of all vertices
     * @param count
     *            the number of vertices to use from the <code>verticesXY</code> array, staring with index 0
     */
    public PolygonPointIntersection(float[] verticesXY, int count) {
        this.verticesXY = verticesXY;
        // Do all the allocations and initializations during this constructor
        preprocess(count);
    }

    private IntervalTreeNode buildNode(List intervals, float center) {
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
        IntervalTreeNode tree = new IntervalTreeNode();
        tree.byBeginning = byStart;
        tree.byEnding = byEnd;
        tree.center = center;
        int childMaxCount = 0;
        int ownMaxCount = Math.max(byStart != null ? byStart.size() : 0, byEnd != null ? byEnd.size() : 0);
        if (left != null) {
            tree.left = buildNode(left, (leftMin + leftMax) / 2.0f);
            tree.left.minMax = leftMax;
            childMaxCount = Math.max(childMaxCount, tree.left.maxCount);
        }
        if (right != null) {
            tree.right = buildNode(right, (rightMin + rightMax) / 2.0f);
            tree.right.minMax = rightMin;
            childMaxCount = Math.max(childMaxCount, tree.right.maxCount);
        }
        tree.maxCount = ownMaxCount + childMaxCount;
        return tree;
    }

    private void preprocess(int count) {
        int i, j = count - 1;
        minX = minY = 1E38f;
        maxX = maxY = -1E38f;
        List intervals = new ArrayList(count);
        for (i = 0; i < count; i++) {
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
        centerX = (maxX + minX) * 0.5f;
        centerY = (maxY + minY) * 0.5f;
        float dx = maxX - centerX;
        float dy = maxY - centerY;
        radiusSquared = dx * dx + dy * dy;
        tree = buildNode(intervals, centerY);
    }

    /**
     * Determine the number of elements that an argument to the <code>working</code> array parameter of the {@link #pointInPolygon(float, float, Interval[])}
     * method must have in order for that method to work correctly.
     * 
     * @return the number of array elements in the <code>working</code> array argument of {@link #pointInPolygon(float, float, Interval[])}
     */
    public int workingSize() {
        return tree.maxCount;
    }

    /**
     * Test whether the given point <tt>(x, y)</tt> lies inside the polygon stored in this {@link PolygonPointIntersection} object.
     * <p>
     * This method must be given a <code>working</code> {@link Interval} array of at least {@link #workingSize()} elements. See the parameter JavaDocs for
     * further information.
     * <p>
     * This method is thread-safe and can be used to test many points concurrently. <i>Please note the restriction on the <code>working</code> parameter in the
     * JavaDocs of that parameter regarding multithreading.</i>
     * 
     * @param x
     *            the x coordinate of the point to test
     * @param y
     *            the y coordinate of the point to test
     * @param working
     *            an array of at least {@link #workingSize()} elements. This method will use this array as internal working memory. It will not make any
     *            assumptions on the state of the array (the array may be empty or not) and will leave the array in an arbitrary state. When calling this method
     *            concurrently from multiple threads, every thread must use its own <code>working</code> array and no two concurrent invocations of this method
     *            may use the same array instance
     * @return <code>true</code> iff the point lies inside the polygon; <code>false</code> otherwise
     */
    public boolean pointInPolygon(float x, float y, Interval[] working) {
        // check bounding sphere first
        float dx = (x - centerX);
        float dy = (y - centerY);
        if (dx * dx + dy * dy > radiusSquared)
            return false;
        // check bounding box next
        if (maxX < x || maxY < y || minX > x || minY > y)
            return false;
        // ask interval tree for all polygon edges intersecting 'y'
        int c = tree.traverse(y, working, 0);
        boolean oddNodes = false;
        // check the polygon edges
        for (int r = 0; r < c; r++) {
            Interval ival = working[r];
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
