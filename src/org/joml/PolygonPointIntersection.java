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

    public static int MASK_HOLE = Integer.MIN_VALUE;

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
    }

    class IntervalTreeNode {
        float center;
        float minMax;
        IntervalTreeNode left;
        IntervalTreeNode right;
        List/* <Interval> */ byBeginning;
        List/* <Interval> */ byEnding;
        int maxCount;

        boolean computeEvenOdd(Interval ival, float x, float y, boolean evenOdd) {
            boolean newEvenOdd = evenOdd;
            int i = ival.i;
            int j = ival.j;
            float yi = verticesXY[2 * i + 1];
            float yj = verticesXY[2 * j + 1];
            float xi = verticesXY[2 * i + 0];
            float xj = verticesXY[2 * j + 0];
            if ((yi < y && yj >= y || yj < y && yi >= y) && (xi <= x || xj <= x)) {
                newEvenOdd ^= (xi + (y - yi) / (yj - yi) * (xj - xi) < x);
            }
            return newEvenOdd;
        }

        boolean traverse(float x, float y, boolean evenOdd) {
            boolean newEvenOdd = evenOdd;
            if (y == center && byBeginning != null) {
                int size = byBeginning.size();
                for (int b = 0; b < size; b++) {
                    Interval ival = (Interval) byBeginning.get(b);
                    newEvenOdd = computeEvenOdd(ival, x, y, newEvenOdd);
                }
            } else if (y < center) {
                if (left != null && left.minMax >= y)
                    newEvenOdd = left.traverse(x, y, newEvenOdd);
                if (byBeginning != null) {
                    int size = byBeginning.size();
                    for (int b = 0; b < size; b++) {
                        Interval ival = (Interval) byBeginning.get(b);
                        if (ival.start > y)
                            break;
                        newEvenOdd = computeEvenOdd(ival, x, y, newEvenOdd);
                    }
                }
            } else if (y > center) {
                if (right != null && right.minMax <= y)
                    newEvenOdd = right.traverse(x, y, newEvenOdd);
                if (byEnding != null) {
                    int size = byEnding.size();
                    for (int b = 0; b < size; b++) {
                        Interval ival = (Interval) byEnding.get(b);
                        if (ival.end < y)
                            break;
                        newEvenOdd = computeEvenOdd(ival, x, y, newEvenOdd);
                    }
                }
            }
            return newEvenOdd;
        }
    }

    private static final ByStartComparator byStartComparator = new ByStartComparator();
    private static final ByEndComparator byEndComparator = new ByEndComparator();

    protected final float[] verticesXY;
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
     * @param polygons
     *            defines the start vertices of a new polygon as well as whether that polygon is a hole. The first vertex of the first polygon is always the
     *            vertex with index 0. In order to define a hole use the {@link #MASK_HOLE} mask and bitwise OR it with the vertex index
     * @param count
     *            the number of vertices to use from the <code>verticesXY</code> array, staring with index 0
     */
    public PolygonPointIntersection(float[] verticesXY, int[] polygons, int count) {
        this.verticesXY = verticesXY;
        // Do all the allocations and initializations during this constructor
        preprocess(count, polygons);
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
                if (byStart == null || byEnd == null) {
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

    private void preprocess(int count, int[] polygons) {
        int i, j = 0;
        minX = minY = 1E38f;
        maxX = maxY = -1E38f;
        List intervals = new ArrayList(count);
        int first = 0;
        int currPoly = 0;
        boolean hole = false;
        for (i = 1; i < count; i++) {
            if (polygons.length > currPoly && (polygons[currPoly] & ~MASK_HOLE) == i) {
                /* New polygon starts. End the current. */
                float prevy = verticesXY[2 * (i - 1) + 1];
                float firsty = verticesXY[2 * first + 1];
                Interval ival = new Interval();
                ival.start = prevy < firsty ? prevy : firsty;
                ival.end = firsty > prevy ? firsty : prevy;
                if (!hole) {
                    ival.i = i - 1;
                    ival.j = first;
                } else {
                    ival.i = first;
                    ival.j = i - 1;
                }
                intervals.add(ival);
                hole = (polygons[currPoly] & MASK_HOLE) != 0;
                first = polygons[currPoly] & ~MASK_HOLE;
                currPoly++;
                i++;
                j = i - 1;
            }
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
        // Close current polygon
        float yi = verticesXY[2 * (i - 1) + 1];
        float xi = verticesXY[2 * (i - 1) + 0];
        float yj = verticesXY[2 * first + 1];
        minX = xi < minX ? xi : minX;
        minY = yi < minY ? yi : minY;
        maxX = xi > maxX ? xi : maxX;
        maxY = yi > maxY ? yi : maxY;
        Interval ival = new Interval();
        ival.start = yi < yj ? yi : yj;
        ival.end = yj > yi ? yj : yi;
        if (!hole) {
            ival.i = i - 1;
            ival.j = first;
        } else {
            ival.i = first;
            ival.j = i - 1;
        }
        intervals.add(ival);
        // compute bounding sphere and rectangle
        centerX = (maxX + minX) * 0.5f;
        centerY = (maxY + minY) * 0.5f;
        float dx = maxX - centerX;
        float dy = maxY - centerY;
        radiusSquared = dx * dx + dy * dy;
        // build interval tree
        tree = buildNode(intervals, centerY);
    }

    /**
     * Test whether the given point <tt>(x, y)</tt> lies inside the polygon stored in this {@link PolygonPointIntersection} object.
     * <p>
     * This method is thread-safe and can be used to test many points concurrently.
     * 
     * @param x
     *            the x coordinate of the point to test
     * @param y
     *            the y coordinate of the point to test
     * @return <code>true</code> iff the point lies inside the polygon; <code>false</code> otherwise
     */
    public boolean pointInPolygon(float x, float y) {
        // check bounding sphere first
        float dx = (x - centerX);
        float dy = (y - centerY);
        if (dx * dx + dy * dy > radiusSquared)
            return false;
        // check bounding box next
        if (maxX < x || maxY < y || minX > x || minY > y)
            return false;
        // ask interval tree for all polygon edges intersecting 'y' and perform
        // the even/odd/crosscutting/raycast algorithm on them
        return tree.traverse(x, y, false);
    }

}
