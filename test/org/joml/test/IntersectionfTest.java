package org.joml.test;

import org.joml.Intersectionf;
import org.joml.Vector3f;

import junit.framework.TestCase;

/**
 * Tests for the {@link Intersectionf} class.
 * 
 * @author Kai Burjack
 */
public class IntersectionfTest extends TestCase {

    public static void testIntersectRayTrianglePX() {
        Vector3f origin = new Vector3f();
        Vector3f dir = new Vector3f(1, 0, 0);
        Vector3f v0 = new Vector3f(1, -1, -1);
        Vector3f v1 = new Vector3f(1, -1, 1);
        Vector3f v2 = new Vector3f(1, 1, 0);
        float t = Intersectionf.intersectRayTriangle(origin, dir, v0, v1, v2, 0.0f);
        assertEquals(1.0f, t, 0.0f);
    }

    public static void testIntersectRaySphere() {
        Vector3f origin = new Vector3f();
        Vector3f dir = new Vector3f(1, 0, 0);
        Vector3f center = new Vector3f(5, 0, 0);
        float radiusSquared = 1.0f;
        float[] result = new float[2];
        boolean intersect = Intersectionf.intersectRaySphere(origin, dir, center, radiusSquared, result);
        assertTrue(intersect);
        assertEquals(4.0f, result[0], 1E-6f);
        assertEquals(6.0f, result[1], 1E-6f);
    }

    public static void testIntersectRayPlane() {
        Vector3f origin = new Vector3f();
        Vector3f dir = new Vector3f(1, 1, 1);
        Vector3f point = new Vector3f(2, 2, 2);
        Vector3f normal = new Vector3f(-1, -1, -1);
        float t = Intersectionf.intersectRayPlane(origin, dir, point, normal, 0.0f);
        assertTrue(t >= 0.0f);
        Vector3f intersection = new Vector3f(dir).mul(t).add(origin);
        TestUtil.assertVector3fEquals(new Vector3f(2, 2, 2), intersection, 1E-6f);
    }

    public static void testNotIntersectRayPlane() {
        Vector3f origin = new Vector3f();
        Vector3f dir = new Vector3f(-1, -1, -1);
        Vector3f point = new Vector3f(2, 2, 2);
        Vector3f normal = new Vector3f(-1, -1, -1);
        float t = Intersectionf.intersectRayPlane(origin, dir, point, normal, 0.0f);
        assertTrue(t == -1.0f);
    }

}
