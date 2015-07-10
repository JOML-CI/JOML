package org.joml.test;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.joml.Culler;
import org.joml.Matrix4f;

/**
 * Tests for the {@link Culler} class.
 * 
 * @author Kai Burjack
 */
public class CullerTest extends TestCase {

    public void testIsSphereInFrustumOrtho() {
        Matrix4f m = new Matrix4f().ortho(-1, 1, -1, 1, -1, 1);
        Culler c = new Culler(m);
        Assert.assertTrue(c.isSphereInsideFrustum(1, 0, 0, 0.1f));
        Assert.assertFalse(c.isSphereInsideFrustum(1.2f, 0, 0, 0.1f));
    }

    public void testIsSphereInFrustumPerspective() {
        Matrix4f m = new Matrix4f().perspective((float) Math.PI / 2.0f, 1.0f, 0.1f, 100.0f);
        Culler c = new Culler(m);
        Assert.assertTrue(c.isSphereInsideFrustum(1, 0, -2, 0.1f));
        Assert.assertFalse(c.isSphereInsideFrustum(4f, 0, -2, 1.0f));
    }

    public void testIsAabInFrustumOrtho() {
        Matrix4f m = new Matrix4f().ortho(-1, 1, -1, 1, -1, 1);
        Culler c = new Culler(m);
        Assert.assertEquals(-1, c.isAabInsideFrustum(-20, -2, 0, 20, 2, 0));
        Assert.assertEquals(Matrix4f.PLANE_PX, c.isAabInsideFrustum(1.1f, 0, 0, 2, 2, 2));
        new Matrix4f().ortho(-1, 1, -1, 1, -1, 1).get(c);
        Assert.assertEquals(-1, c.isAabInsideFrustum(0, 0, 0, 2, 2, 2));
        Assert.assertEquals(Matrix4f.PLANE_PX, c.isAabInsideFrustum(1.1f, 0, 0, 2, 2, 2));
        new Matrix4f().get(c);
        Assert.assertEquals(-1, c.isAabInsideFrustum(0.5f, 0.5f, 0.5f, 2, 2, 2));
        Assert.assertEquals(Matrix4f.PLANE_PX, c.isAabInsideFrustum(1.5f, 0.5f, 0.5f, 2, 2, 2));
        Assert.assertEquals(Matrix4f.PLANE_NX, c.isAabInsideFrustum(-2.5f, 0.5f, 0.5f, -1.5f, 2, 2));
        Assert.assertEquals(Matrix4f.PLANE_NY, c.isAabInsideFrustum(-0.5f, -2.5f, 0.5f, 1.5f, -2, 2));
    }

    public void testIsAabInPerspective() {
        Matrix4f m = new Matrix4f().perspective((float) Math.PI / 2.0f, 1.0f, 0.1f, 100.0f);
        Culler c = new Culler(m);
        Assert.assertTrue(c.isAabInsideFrustum(0, 0, -7, 1, 1, -5) == -1);
        Assert.assertFalse(c.isAabInsideFrustum(1.1f, 0, 0, 2, 2, 2) == -1);
        Assert.assertFalse(c.isAabInsideFrustum(4, 4, -3, 5, 5, -5) == -1);
        Assert.assertFalse(c.isAabInsideFrustum(-6, -6, -2, -1, -4, -4) == -1);
    }

    public void testIsPointInPerspective() {
        Matrix4f m = new Matrix4f().perspective((float) Math.PI / 2.0f, 1.0f, 0.1f, 100.0f);
        Culler c = new Culler(m);
        Assert.assertTrue(c.isPointInsideFrustum(0, 0, -5));
        Assert.assertFalse(c.isPointInsideFrustum(0, 6, -5));
    }

    public void testIsAabInPerspectiveMask() {
        Matrix4f m = new Matrix4f().perspective((float) Math.PI / 2.0f, 1.0f, 0.1f, 100.0f);
        Culler c = new Culler(m);
        Assert.assertEquals(-1, c.isAabInsideFrustumMasked(5.1f, 0, -3, 8, 2, -2, ~0 ^ Matrix4f.PLANE_MASK_PX));
        Assert.assertEquals(-1, c.isAabInsideFrustumMasked(-6.1f, 0, -3, -5, 2, -2, ~0 ^ Matrix4f.PLANE_MASK_NX));
        Assert.assertEquals(Matrix4f.PLANE_NX, c.isAabInsideFrustumMasked(-6.1f, 0, -3, -5, 2, -2, Matrix4f.PLANE_MASK_NX));
    }


}
