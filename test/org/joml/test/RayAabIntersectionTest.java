package org.joml.test;

import org.joml.RayAabIntersection;

import junit.framework.TestCase;

/**
 * Tests for the {@link RayAabIntersection}.
 * 
 * @author Kai Burjack
 */
public class RayAabIntersectionTest extends TestCase {

    public static void testPX() {
        RayAabIntersection r = new RayAabIntersection();
        r.set(-1, 0, 0, 1, 0, 0);
        assertTrue(r.intersect(-0.5f, -0.5f, -0.5f, 0.5f, 0.5f, 0.5f));
    }

    public static void testPY() {
        RayAabIntersection r = new RayAabIntersection();
        r.set(0, -1, 0, 0, 1, 0);
        assertTrue(r.intersect(-0.5f, -0.5f, -0.5f, 0.5f, 0.5f, 0.5f));
    }

    public static void testPZ() {
        RayAabIntersection r = new RayAabIntersection();
        r.set(0, 0, -1, 0, 0, 1);
        assertTrue(r.intersect(-0.5f, -0.5f, -0.5f, 0.5f, 0.5f, 0.5f));
    }

    public static void testNX() {
        RayAabIntersection r = new RayAabIntersection();
        r.set(1, 0, 0, -1, 0, 0);
        assertTrue(r.intersect(-0.5f, -0.5f, -0.5f, 0.5f, 0.5f, 0.5f));
    }

    public static void testNY() {
        RayAabIntersection r = new RayAabIntersection();
        r.set(0, 1, 0, 0, -1, 0);
        assertTrue(r.intersect(-0.5f, -0.5f, -0.5f, 0.5f, 0.5f, 0.5f));
    }

    public static void testNZ() {
        RayAabIntersection r = new RayAabIntersection();
        r.set(0, 0, 1, 0, 0, -1);
        assertTrue(r.intersect(-0.5f, -0.5f, -0.5f, 0.5f, 0.5f, 0.5f));
    }

    public static void testPXPY() {
        RayAabIntersection r = new RayAabIntersection();
        r.set(-1, -1, 0, 1, 1, 0);
        assertTrue(r.intersect(-0.5f, -0.5f, -0.5f, 0.5f, 0.5f, 0.5f));
    }

    public static void testPXEdge() {
        RayAabIntersection r = new RayAabIntersection();
        r.set(-1, 0.5f, 0, 1, 0, 0);
        assertTrue(r.intersect(-0.5f, -0.5f, -0.5f, 0.5f, 0.5f, 0.5f));
    }

    public static void testPXEdgeDelta() {
        RayAabIntersection r = new RayAabIntersection();
        r.set(-1, 0.500001f, 0, 1, 0, 0);
        assertFalse(r.intersect(-0.5f, -0.5f, -0.5f, 0.5f, 0.5f, 0.5f));
    }

    public static void testNXEdge() {
        RayAabIntersection r = new RayAabIntersection();
        r.set(-1, -0.5f, 0, 1, 0, 0);
        assertTrue(r.intersect(-0.5f, -0.5f, -0.5f, 0.5f, 0.5f, 0.5f));
    }

    public static void testNXEdgeDelta() {
        RayAabIntersection r = new RayAabIntersection();
        r.set(-1, -0.500001f, 0, 1, 0, 0);
        assertFalse(r.intersect(-0.5f, -0.5f, -0.5f, 0.5f, 0.5f, 0.5f));
    }

}
