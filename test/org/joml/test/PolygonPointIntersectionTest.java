package org.joml.test;

import junit.framework.TestCase;

import org.joml.PolygonPointIntersection;
import org.joml.PolygonPointIntersection.Interval;

/**
 * Tests for the {@link PolygonPointIntersection} class.
 * 
 * @author Kai Burjack
 */
public class PolygonPointIntersectionTest extends TestCase {

    public static void testSimple() {
        // Define a shape that looks like a "U"
        float[] verticesXY = {
                0, 0,
                3, 0,
                3, 1,
                2, 1,
                2, 0.5f,
                1, 0.5f,
                1, 1,
                0, 1
        };
        PolygonPointIntersection isect = new PolygonPointIntersection(verticesXY);
        Interval[] working = new Interval[isect.workingSize()];
        // Left part of the "U"
        assertTrue(isect.pointInPolygon(0.1f, 0.1f, working));
        // top middle of the "U"
        assertFalse(isect.pointInPolygon(1.5f, 0.8f, working));
        // bottom middle of the "U"
        assertTrue(isect.pointInPolygon(1.5f, 0.2f, working));
        // right part of the "U"
        assertTrue(isect.pointInPolygon(2.5f, 0.1f, working));
    }

    public static void testBigCircle() {
        int polyN = 1024 * 256;
        float[] verticesXY = new float[polyN * 2];
        for (int i = 0; i < polyN; i++) {
            float x = (float) Math.cos(2.0 * Math.PI * i / polyN);
            float y = (float) Math.sin(2.0 * Math.PI * i / polyN);
            verticesXY[2 * i + 0] = x;
            verticesXY[2 * i + 1] = y;
        }
        PolygonPointIntersection isect = new PolygonPointIntersection(verticesXY);
        Interval[] working = new Interval[isect.workingSize()];
        // Center
        assertTrue(isect.pointInPolygon(0, 0, working));
        // Left outside
        assertFalse(isect.pointInPolygon(-1.1f, 0, working));
        // Top right outside
        assertFalse(isect.pointInPolygon(0.8f, 0.8f, working));
        // Top edge
        assertTrue(isect.pointInPolygon(1.0f, 0, working));
        // Bottom edge <- algorithm only detects top edges as 'inside'
        assertFalse(isect.pointInPolygon(-1.0f, 0, working));
    }

}
