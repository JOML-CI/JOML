package org.joml.test;

import org.joml.Intersectionf;
import org.joml.Vector2f;
import org.joml.Vector3f;

import junit.framework.TestCase;

/**
 * Tests for the {@link Intersectionf} class.
 * 
 * @author Kai Burjack
 */
public class IntersectionfTest extends TestCase {

    public static void testIntersectRayCircle() {
        Vector2f origin = new Vector2f();
        Vector2f dir = new Vector2f(1, 0);
        Vector2f center = new Vector2f(5, 0);
        float radiusSquared = 1.0f;
        Vector2f result = new Vector2f();
        boolean intersect = Intersectionf.intersectRayCircle(origin, dir, center, radiusSquared, result);
        assertTrue(intersect);
        assertEquals(4.0f, result.x, 1E-6f);
        assertEquals(6.0f, result.y, 1E-6f);
    }

    public static void testIntersectRayLine() {
        Vector2f origin = new Vector2f();
        Vector2f dir = new Vector2f(1, 1);
        Vector2f point = new Vector2f(2, 2);
        Vector2f normal = new Vector2f(-1, -1);
        float t = Intersectionf.intersectRayLine(origin, dir, point, normal, 0.0f);
        assertTrue(t >= 0.0f);
        Vector2f intersection = new Vector2f(dir).mul(t).add(origin);
        TestUtil.assertVector2fEquals(new Vector2f(2, 2), intersection, 1E-6f);
        normal = new Vector2f(1, 1);
        t = Intersectionf.intersectRayLine(origin, dir, point, normal, 0.0f);
        assertEquals(-1.0f, t, 1E-6f);
    }

    public static void testNotIntersectRayLine() {
        Vector2f origin = new Vector2f();
        Vector2f dir = new Vector2f(-1, -1);
        Vector2f point = new Vector2f(2, 2);
        Vector2f normal = new Vector2f(-1, -1);
        float t = Intersectionf.intersectRayLine(origin, dir, point, normal, 0.0f);
        assertTrue(t == -1.0f);
    }

    public static void testAarLine() {
        assertTrue(Intersectionf.testAarLine(-1, -1, 1, 1, 1, 1, 2.0f));
        assertFalse(Intersectionf.testAarLine(-1, -1, 1, 1, 1, 1, 2.1f));
        assertTrue(Intersectionf.testAarLine(-1, -1, 1, 1, 1, 1, -2.0f));
        assertFalse(Intersectionf.testAarLine(-1, -1, 1, 1, 1, 1, -2.1f));
    }

    public static void testCircleCircle() {
        assertTrue(Intersectionf.testCircleCircle(0, 0, 1, 0.5f, 0, 1));
        Vector3f res = new Vector3f();
        assertTrue(Intersectionf.intersectCircleCircle(0, 0, 1, 0.5f, 0, 1, res));
        // intersection point is (0.25, 0) <- middle between both spheres with equal radii
        TestUtil.assertVector2fEquals(new Vector2f(0.25f, 0), new Vector2f(res.x, res.y), 1E-6f);
        // cos(a) = adjside/hyp
        // cos(a) * hyp = adjside
        // acos(cos(a) * hyp) = acos(adjside)
        // y = sin(acos(adjside))
        float expectedRadius = (float) Math.sin(Math.acos(0.25));
        assertEquals(expectedRadius, res.z, 1E-6f);
    }

    public static void testClosestPointOnTriangle() {
        Vector2f p = new Vector2f();
        Intersectionf.findClosestPointOnTriangle(0, 0, 1, 0, 0, 1, -1, -1, p);
        TestUtil.assertVector2fEquals(new Vector2f(0, 0), p,1E-6f);
        Intersectionf.findClosestPointOnTriangle(0, 0, 1, 0, 0, 1, 2, 0.1f, p);
        TestUtil.assertVector2fEquals(new Vector2f(1, 0), p,1E-6f);
        Intersectionf.findClosestPointOnTriangle(0, 0, 1, 0, 0, 1, 0.5f, 3, p);
        TestUtil.assertVector2fEquals(new Vector2f(0, 1), p,1E-6f);
        Intersectionf.findClosestPointOnTriangle(0, 0, 1, 0, 0, 1, 0.5f, 0.5f, p);
        TestUtil.assertVector2fEquals(new Vector2f(0.5f, 0.5f), p,1E-6f);
    }

}
