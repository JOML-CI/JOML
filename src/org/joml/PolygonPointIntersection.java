package org.joml;

/**
 * Class for polygon/point intersection tests when testing many points against the same static polygon.
 * <p>
 * It can handle concave polygons.
 * <p>
 * Reference: <a href="http://alienryderflex.com/polygon/">http://alienryderflex.com</a>
 * 
 * @author Kai Burjack
 */
public class PolygonPointIntersection {

    private float[] verticesXY;
    private float[] constantAndMultiple;
    private float minX, minY, maxX, maxY;

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
        if (this.constantAndMultiple == null || this.constantAndMultiple.length < verticesXY.length) {
            this.constantAndMultiple = new float[verticesXY.length];
        }
        precalcValues();
    }

    private void precalcValues() {
        int i, j = verticesXY.length / 2 - 1;
        minX = minY = 1E38f;
        maxX = maxY = -1E38f;
        for (i = 0; i < verticesXY.length / 2; i++) {
            float yi = verticesXY[2 * i + 1];
            float yj = verticesXY[2 * j + 1];
            float xi = verticesXY[2 * i + 0];
            minX = xi < minX ? xi : minX;
            minY = yi < minY ? yi : minY;
            maxX = xi > maxX ? xi : maxX;
            maxY = yi > maxY ? yi : maxY;
            if (yj == yi) {
                constantAndMultiple[2 * i + 0] = xi;
                constantAndMultiple[2 * i + 1] = 0.0f;
            } else {
                float xj = verticesXY[2 * j + 0];
                constantAndMultiple[2 * i + 0] = xi - (yi * xj) / (yj - yi) + (yi * xi) / (yj - yi);
                constantAndMultiple[2 * i + 1] = (xj - xi) / (yj - yi);
            }
            j = i;
        }
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
        int i, j = verticesXY.length / 2 - 1;
        boolean oddNodes = false;
        // check bounding rectangle first
        if (maxX < x || maxY < y || minX > x || minY > y)
            return false;
        // now check all polygon edges
        for (i = 0; i < verticesXY.length / 2; i++) {
            float yi = verticesXY[2 * i + 1];
            float yj = verticesXY[2 * j + 1];
            if ((yi <= y && yj >= y || yj <= y && yi >= y)) {
                oddNodes ^= (y * constantAndMultiple[2 * i + 1] + constantAndMultiple[2 * i + 0] <= x);
            }
            j = i;
        }
        return oddNodes;
    }

}
