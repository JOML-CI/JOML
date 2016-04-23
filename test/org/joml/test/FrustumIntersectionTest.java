package org.joml.test;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.joml.FrustumIntersection;
import org.joml.Matrix4f;

/**
 * Tests for the {@link FrustumIntersection} class.
 * 
 * @author Kai Burjack
 */
public class FrustumIntersectionTest extends TestCase {

    public static void testIsSphereInFrustumOrtho() {
        Matrix4f m = new Matrix4f().ortho(-1, 1, -1, 1, -1, 1);
        FrustumIntersection c = new FrustumIntersection(m);
        Assert.assertTrue(c.testSphere(1, 0, 0, 0.1f));
        Assert.assertFalse(c.testSphere(1.2f, 0, 0, 0.1f));
    }

    public static void testIsSphereInFrustumPerspective() {
        Matrix4f m = new Matrix4f().perspective((float) Math.PI / 2.0f, 1.0f, 0.1f, 100.0f);
        FrustumIntersection c = new FrustumIntersection(m);
        Assert.assertTrue(c.testSphere(1, 0, -2, 0.1f));
        Assert.assertFalse(c.testSphere(4f, 0, -2, 1.0f));
    }

    public static void testIsAabInFrustumOrtho() {
        Matrix4f m = new Matrix4f().ortho(-1, 1, -1, 1, -1, 1);
        FrustumIntersection c = new FrustumIntersection(m);
        Assert.assertEquals(FrustumIntersection.INTERSECT, c.intersectAab(-20, -2, 0, 20, 2, 0));
        Assert.assertEquals(FrustumIntersection.INSIDE, c.intersectAab(-0.5f, -0.5f, -0.5f, 0.5f, 0.5f, 0.5f));
        Assert.assertEquals(Matrix4f.PLANE_PX, c.intersectAab(1.1f, 0, 0, 2, 2, 2));
        c.set(new Matrix4f().ortho(-1, 1, -1, 1, -1, 1));
        Assert.assertEquals(FrustumIntersection.INTERSECT, c.intersectAab(0, 0, 0, 2, 2, 2));
        Assert.assertEquals(Matrix4f.PLANE_PX, c.intersectAab(1.1f, 0, 0, 2, 2, 2));
        c.set(new Matrix4f());
        Assert.assertEquals(FrustumIntersection.INTERSECT, c.intersectAab(0.5f, 0.5f, 0.5f, 2, 2, 2));
        Assert.assertEquals(Matrix4f.PLANE_PX, c.intersectAab(1.5f, 0.5f, 0.5f, 2, 2, 2));
        Assert.assertEquals(Matrix4f.PLANE_NX, c.intersectAab(-2.5f, 0.5f, 0.5f, -1.5f, 2, 2));
        Assert.assertEquals(Matrix4f.PLANE_NY, c.intersectAab(-0.5f, -2.5f, 0.5f, 1.5f, -2, 2));
    }

    public static void testIsAabInPerspective() {
        Matrix4f m = new Matrix4f().perspective((float) Math.PI / 2.0f, 1.0f, 0.1f, 100.0f);
        FrustumIntersection c = new FrustumIntersection(m);
        Assert.assertEquals(FrustumIntersection.INSIDE, c.intersectAab(0, 0, -7, 1, 1, -5));
        Assert.assertEquals(FrustumIntersection.PLANE_PX, c.intersectAab(1.1f, 0, 0, 2, 2, 2));
        Assert.assertEquals(FrustumIntersection.PLANE_PX, c.intersectAab(4, 4, -3, 5, 5, -5));
        Assert.assertEquals(FrustumIntersection.PLANE_NY, c.intersectAab(-6, -6, -2, -1, -4, -4));
    }

    public static void testIsPointInPerspective() {
        Matrix4f m = new Matrix4f().perspective((float) Math.PI / 2.0f, 1.0f, 0.1f, 100.0f);
        FrustumIntersection c = new FrustumIntersection(m);
        Assert.assertTrue(c.testPoint(0, 0, -5));
        Assert.assertFalse(c.testPoint(0, 6, -5));
    }

    public static void testIsAabInPerspectiveMask() {
        Matrix4f m = new Matrix4f().perspective((float) Math.PI / 2.0f, 1.0f, 0.1f, 100.0f);
        FrustumIntersection c = new FrustumIntersection(m);
        Assert.assertEquals(FrustumIntersection.INTERSECT, c.intersectAabMasked(5.1f, 0, -3, 8, 2, -2, ~0 ^ FrustumIntersection.PLANE_MASK_PX));
        Assert.assertEquals(FrustumIntersection.INTERSECT, c.intersectAabMasked(-6.1f, 0, -3, -5, 2, -2, ~0 ^ FrustumIntersection.PLANE_MASK_NX));
        Assert.assertEquals(Matrix4f.PLANE_NX, c.intersectAabMasked(-6.1f, 0, -3, -5, 2, -2, FrustumIntersection.PLANE_MASK_NX));
    }

}
