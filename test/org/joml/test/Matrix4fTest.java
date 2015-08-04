package org.joml.test;

import java.nio.IntBuffer;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

/**
 * Tests for the {@link Matrix4f} class.
 * 
 * @author Kai Burjack
 */
public class Matrix4fTest extends TestCase {

    /**
     * Test that project and unproject are each other's inverse operations.
     */
    public void testProjectUnproject() {
        /* Build some arbitrary viewport. */
        IntBuffer viewport = IntBuffer.wrap(new int[]{0, 0, 800, 800});

        Vector3f expected = new Vector3f(1.0f, 2.0f, -3.0f);
        Vector3f actual = new Vector3f();
        Matrix4f inverse = new Matrix4f();

        /* Build a perspective projection and then project and unproject. */
        new Matrix4f()
        .perspective((float) Math.toRadians(45.0f), 1.0f, 0.01f, 100.0f)
        .project(expected, viewport, actual)
        .unproject(actual, viewport, inverse, actual);

        /* Check for equality of the components */
        assertEquals(expected.x, actual.x, TestUtil.MANY_OPS_AROUND_ZERO_PRECISION_FLOAT);
        assertEquals(expected.y, actual.y, TestUtil.MANY_OPS_AROUND_ZERO_PRECISION_FLOAT);
        assertEquals(expected.z, actual.z, TestUtil.MANY_OPS_AROUND_ZERO_PRECISION_FLOAT);
    }

    public void testLookAt() {
        Matrix4f m1, m2;
        m1 = new Matrix4f().lookAt(0, 2, 3, 0, 0, 0, 0, 1, 0);
        m2 = new Matrix4f().translate(0, 0, -(float) Math.sqrt(2 * 2 + 3 * 3)).rotateX(
                (float) Math.atan2(2, 3));
        TestUtil.assertMatrix4fEquals(m1, m2, 1E-5f);
        m1 = new Matrix4f().lookAt(3, 2, 0, 0, 0, 0, 0, 1, 0);
        m2 = new Matrix4f().translate(0, 0, -(float) Math.sqrt(2 * 2 + 3 * 3))
                .rotateX((float) Math.atan2(2, 3)).rotateY((float) Math.toRadians(-90));
        TestUtil.assertMatrix4fEquals(m1, m2, 1E-4f);
    }

    /**
     * Test computing the frustum planes with a combined view-projection matrix with translation.
     */
    public void testFrustumPlanePerspectiveRotateTranslate() {
        Vector4f left = new Vector4f();
        Vector4f right = new Vector4f();
        Vector4f top = new Vector4f();
        Vector4f bottom = new Vector4f();
        Vector4f near = new Vector4f();
        Vector4f far = new Vector4f();

        /*
         * Build a perspective transformation and
         * move the camera 5 units "up" and rotate it clock-wise 90 degrees around Y.
         */
        new Matrix4f()
        .perspective((float) Math.toRadians(90), 1.0f, 0.1f, 100.0f)
        .rotateY((float) Math.toRadians(90))
        .translate(0, -5, 0)
            .frustumPlane(Matrix4f.PLANE_NX, left)
            .frustumPlane(Matrix4f.PLANE_PX, right)
            .frustumPlane(Matrix4f.PLANE_NY, bottom)
            .frustumPlane(Matrix4f.PLANE_PY, top)
            .frustumPlane(Matrix4f.PLANE_NZ, near)
            .frustumPlane(Matrix4f.PLANE_PZ, far);

        Vector4f expectedLeft = new Vector4f(1, 0, 1, 0).normalize3();
        Vector4f expectedRight = new Vector4f(1, 0, -1, 0).normalize3();
        Vector4f expectedTop = new Vector4f(1, -1, 0, 5).normalize3();
        Vector4f expectedBottom = new Vector4f(1, 1, 0, -5).normalize3();
        Vector4f expectedNear = new Vector4f(1, 0, 0, -0.1f).normalize3();
        Vector4f expectedFar = new Vector4f(-1, 0, 0, 100.0f).normalize3();

        TestUtil.assertVector4fEquals(expectedLeft, left, 1E-5f);
        TestUtil.assertVector4fEquals(expectedRight, right, 1E-5f);
        TestUtil.assertVector4fEquals(expectedTop, top, 1E-5f);
        TestUtil.assertVector4fEquals(expectedBottom, bottom, 1E-5f);
        TestUtil.assertVector4fEquals(expectedNear, near, 1E-5f);
        TestUtil.assertVector4fEquals(expectedFar, far, 1E-4f);
    }

    public void testIsPointInFrustumPlanePerspectiveRotate() {
        Matrix4f m = new Matrix4f()
        .perspective((float) Math.toRadians(90.0f), 1.0f, 0.1f, 100.0f)
        .rotateY((float) Math.toRadians(90));
        Assert.assertTrue(m.isPointInsideFrustum(50, 0, 0));
        Assert.assertFalse(m.isPointInsideFrustum(50, 51, 0));
    }

    public void testFrustumRay() {
        Vector3f dir = new Vector3f();
        Matrix4f m = new Matrix4f()
                .perspective((float) Math.toRadians(90), 1.0f, 0.1f, 100.0f)
                .rotateY((float) Math.toRadians(90));
        Vector3f expectedDir;
        m.frustumRayDir(0, 0, dir);
        expectedDir = new Vector3f(1, -1, -1).normalize();
        TestUtil.assertVector3fEquals(expectedDir, dir, 1E-5f);
        m.frustumRayDir(1, 0, dir);
        expectedDir = new Vector3f(1, -1, 1).normalize();
        TestUtil.assertVector3fEquals(expectedDir, dir, 1E-5f);
        m.frustumRayDir(0, 1, dir);
        expectedDir = new Vector3f(1, 1, -1).normalize();
        TestUtil.assertVector3fEquals(expectedDir, dir, 1E-5f);
        m.frustumRayDir(1, 1, dir);
        expectedDir = new Vector3f(1, 1, 1).normalize();
        TestUtil.assertVector3fEquals(expectedDir, dir, 1E-5f);
    }

    public void testFrustumRay2() {
        Vector3f dir = new Vector3f();
        Matrix4f m = new Matrix4f()
                .perspective((float) Math.toRadians(90), 1.0f, 0.1f, 100.0f)
                .rotateZ((float) Math.toRadians(45));
        Vector3f expectedDir;
        m.frustumRayDir(0, 0, dir);
        expectedDir = new Vector3f(-(float)Math.sqrt(2), 0, -1).normalize();
        TestUtil.assertVector3fEquals(expectedDir, dir, 1E-5f);
        m.frustumRayDir(1, 0, dir);
        expectedDir = new Vector3f(0, -(float)Math.sqrt(2), -1).normalize();
        TestUtil.assertVector3fEquals(expectedDir, dir, 1E-5f);
        m.frustumRayDir(0, 1, dir);
        expectedDir = new Vector3f(0, (float)Math.sqrt(2), -1).normalize();
        TestUtil.assertVector3fEquals(expectedDir, dir, 1E-5f);
        m.frustumRayDir(1, 1, dir);
        expectedDir = new Vector3f((float)Math.sqrt(2), 0, -1).normalize();
        TestUtil.assertVector3fEquals(expectedDir, dir, 1E-5f);
    }

    public void testIsSphereInFrustumOrtho() {
        Matrix4f m = new Matrix4f().ortho(-1, 1, -1, 1, -1, 1);
        Assert.assertTrue(m.isSphereInsideFrustum(1, 0, 0, 0.1f));
        Assert.assertFalse(m.isSphereInsideFrustum(1.2f, 0, 0, 0.1f));
    }

    public void testIsSphereInFrustumPerspective() {
        Matrix4f m = new Matrix4f().perspective((float) Math.PI / 2.0f, 1.0f, 0.1f, 100.0f);
        Assert.assertTrue(m.isSphereInsideFrustum(1, 0, -2, 0.1f));
        Assert.assertFalse(m.isSphereInsideFrustum(4f, 0, -2, 1.0f));
    }

    public void testIsAabInFrustumOrtho() {
        Matrix4f m = new Matrix4f().ortho(-1, 1, -1, 1, -1, 1);
        Assert.assertEquals(-1, m.isAabInsideFrustum(-20, -2, 0, 20, 2, 0));
        Assert.assertEquals(Matrix4f.PLANE_PX, m.isAabInsideFrustum(1.1f, 0, 0, 2, 2, 2));
        m = new Matrix4f().ortho(-1, 1, -1, 1, -1, 1);
        Assert.assertEquals(-1, m.isAabInsideFrustum(0, 0, 0, 2, 2, 2));
        Assert.assertEquals(Matrix4f.PLANE_PX, m.isAabInsideFrustum(1.1f, 0, 0, 2, 2, 2));
        m = new Matrix4f();
        Assert.assertEquals(-1, m.isAabInsideFrustum(0.5f, 0.5f, 0.5f, 2, 2, 2));
        Assert.assertEquals(Matrix4f.PLANE_PX, m.isAabInsideFrustum(1.5f, 0.5f, 0.5f, 2, 2, 2));
        Assert.assertEquals(Matrix4f.PLANE_NX, m.isAabInsideFrustum(-2.5f, 0.5f, 0.5f, -1.5f, 2, 2));
        Assert.assertEquals(Matrix4f.PLANE_NY, m.isAabInsideFrustum(-0.5f, -2.5f, 0.5f, 1.5f, -2, 2));
    }

    public void testIsAabInPerspective() {
        Matrix4f m = new Matrix4f().perspective((float) Math.PI / 2.0f, 1.0f, 0.1f, 100.0f); 
        Assert.assertTrue(m.isAabInsideFrustum(0, 0, -7, 1, 1, -5) == -1);
        Assert.assertFalse(m.isAabInsideFrustum(1.1f, 0, 0, 2, 2, 2) == -1);
        Assert.assertFalse(m.isAabInsideFrustum(4, 4, -3, 5, 5, -5) == -1);
        Assert.assertFalse(m.isAabInsideFrustum(-6, -6, -2, -1, -4, -4) == -1);
    }

    public void testIsPointInPerspective() {
        Matrix4f m = new Matrix4f().perspective((float) Math.PI / 2.0f, 1.0f, 0.1f, 100.0f); 
        Assert.assertTrue(m.isPointInsideFrustum(0, 0, -5));
        Assert.assertFalse(m.isPointInsideFrustum(0, 6, -5));
    }

    public void testIsAabInPerspectiveMask() {
        Matrix4f m = new Matrix4f().perspective((float) Math.PI / 2.0f, 1.0f, 0.1f, 100.0f); 
        Assert.assertEquals(-1, m.isAabInsideFrustumMasked(5.1f, 0, -3, 8, 2, -2, ~0 ^ Matrix4f.PLANE_MASK_PX));
        Assert.assertEquals(-1, m.isAabInsideFrustumMasked(-6.1f, 0, -3, -5, 2, -2, ~0 ^ Matrix4f.PLANE_MASK_NX));
        Assert.assertEquals(Matrix4f.PLANE_NX, m.isAabInsideFrustumMasked(-6.1f, 0, -3, -5, 2, -2, Matrix4f.PLANE_MASK_NX));
    }

    public void testPositiveXRotateY() {
        Vector3f dir = new Vector3f();
        Matrix4f m = new Matrix4f()
                .rotateY((float) Math.toRadians(90));
        m.positiveX(dir);
        TestUtil.assertVector3fEquals(new Vector3f(0, 0, 1), dir, 1E-7f);
    }

    public void testPositiveXRotateXY() {
        Vector3f dir = new Vector3f();
        Matrix4f m = new Matrix4f()
                .rotateY((float) Math.toRadians(90)).rotateX((float) Math.toRadians(45));
        m.positiveX(dir);
        TestUtil.assertVector3fEquals(new Vector3f(0, 1, 1).normalize(), dir, 1E-7f);
    }

    public void testPositiveXPerspectiveRotateY() {
        Vector3f dir = new Vector3f();
        Matrix4f m = new Matrix4f()
                .perspective((float) Math.toRadians(90), 1.0f, 0.1f, 100.0f)
                .rotateY((float) Math.toRadians(90));
        m.positiveX(dir);
        TestUtil.assertVector3fEquals(new Vector3f(0, 0, -1), dir, 1E-7f);
    }

    public void testPositiveXPerspectiveRotateXY() {
        Vector3f dir = new Vector3f();
        Matrix4f m = new Matrix4f()
                .perspective((float) Math.toRadians(90), 1.0f, 0.1f, 100.0f)
                .rotateY((float) Math.toRadians(90)).rotateX((float) Math.toRadians(45));
        m.positiveX(dir);
        TestUtil.assertVector3fEquals(new Vector3f(0, -1, -1).normalize(), dir, 1E-7f);
    }

    public void testFrustumCornerIdentity() {
        Matrix4f m = new Matrix4f();
        Vector3f corner = new Vector3f();
        m.frustumCorner(Matrix4f.CORNER_NXNYNZ, corner); // left, bottom, near
        TestUtil.assertVector3fEquals(new Vector3f(-1, -1, -1), corner, 1E-6f);
        m.frustumCorner(Matrix4f.CORNER_PXNYNZ, corner); // right, bottom, near
        TestUtil.assertVector3fEquals(new Vector3f(1, -1, -1), corner, 1E-6f);
        m.frustumCorner(Matrix4f.CORNER_PXNYPZ, corner); // right, bottom, far
        TestUtil.assertVector3fEquals(new Vector3f(1, -1, 1), corner, 1E-6f);
        m.frustumCorner(Matrix4f.CORNER_NXPYPZ, corner); // left, top, far
        TestUtil.assertVector3fEquals(new Vector3f(-1, 1, 1), corner, 1E-6f);
    }

    public void testFrustumCornerOrthoWide() {
        Matrix4f m = new Matrix4f().ortho2D(-2, 2, -1, 1);
        Vector3f corner = new Vector3f();
        m.frustumCorner(Matrix4f.CORNER_NXNYNZ, corner); // left, bottom, near
        TestUtil.assertVector3fEquals(new Vector3f(-2, -1, 1), corner, 1E-6f);
        m.frustumCorner(Matrix4f.CORNER_PXNYNZ, corner); // right, bottom, near
        TestUtil.assertVector3fEquals(new Vector3f(2, -1, 1), corner, 1E-6f);
        m.frustumCorner(Matrix4f.CORNER_PXNYPZ, corner); // right, bottom, far
        TestUtil.assertVector3fEquals(new Vector3f(2, -1, -1), corner, 1E-6f);
        m.frustumCorner(Matrix4f.CORNER_NXPYPZ, corner); // left, top, far
        TestUtil.assertVector3fEquals(new Vector3f(-2, 1, -1), corner, 1E-6f);
    }

    public void testFrustumCorner() {
        Matrix4f m = new Matrix4f()
        .perspective((float) Math.toRadians(90), 1.0f, 0.1f, 100.0f)
        .lookAt(0, 0, 10,
                0, 0,  0, 
                0, 1,  0);
        Vector3f corner = new Vector3f();
        m.frustumCorner(Matrix4f.CORNER_NXNYNZ, corner); // left, bottom, near
        TestUtil.assertVector3fEquals(new Vector3f(-0.1f, -0.1f, 10 - 0.1f), corner, 1E-6f);
        m.frustumCorner(Matrix4f.CORNER_PXNYNZ, corner); // right, bottom, near
        TestUtil.assertVector3fEquals(new Vector3f(0.1f, -0.1f, 10 - 0.1f), corner, 1E-6f);
        m.frustumCorner(Matrix4f.CORNER_PXNYPZ, corner); // right, bottom, far
        TestUtil.assertVector3fEquals(new Vector3f(100.0f, -100, 10 - 100f), corner, 1E-3f);
    }

    public void testFrustumCornerWide() {
        Matrix4f m = new Matrix4f()
        .perspective((float) Math.toRadians(90), 2.0f, 0.1f, 100.0f)
        .lookAt(0, 0, 10,
                0, 0,  0, 
                0, 1,  0);
        Vector3f corner = new Vector3f();
        m.frustumCorner(Matrix4f.CORNER_NXNYNZ, corner); // left, bottom, near
        TestUtil.assertVector3fEquals(new Vector3f(-0.2f, -0.1f, 10 - 0.1f), corner, 1E-5f);
        m.frustumCorner(Matrix4f.CORNER_PXNYNZ, corner); // right, bottom, near
        TestUtil.assertVector3fEquals(new Vector3f(0.2f, -0.1f, 10 - 0.1f), corner, 1E-5f);
        m.frustumCorner(Matrix4f.CORNER_PXNYPZ, corner); // right, bottom, far
        TestUtil.assertVector3fEquals(new Vector3f(200.0f, -100, 10 - 100f), corner, 1E-3f);
    }

    public void testFrustumCornerRotate() {
        Matrix4f m = new Matrix4f()
        .perspective((float) Math.toRadians(90), 1.0f, 0.1f, 100.0f)
        .lookAt(10, 0, 0, 
                 0, 0, 0, 
                 0, 1, 0);
        Vector3f corner = new Vector3f();
        m.frustumCorner(Matrix4f.CORNER_NXNYNZ, corner); // left, bottom, near
        TestUtil.assertVector3fEquals(new Vector3f(10 - 0.1f, -0.1f, 0.1f), corner, 1E-6f);
        m.frustumCorner(Matrix4f.CORNER_PXNYNZ, corner); // right, bottom, near
        TestUtil.assertVector3fEquals(new Vector3f(10 - 0.1f, -0.1f, -0.1f), corner, 1E-6f);
        m.frustumCorner(Matrix4f.CORNER_PXNYPZ, corner); // right, bottom, far
        TestUtil.assertVector3fEquals(new Vector3f(-100.0f + 10, -100, -100f), corner, 1E-3f);
    }

    public void testPerspectiveOrigin() {
        Matrix4f m = new Matrix4f()
        // test symmetric frustum with some modelview translation and rotation
        .perspective((float) Math.toRadians(90), 1.0f, 0.1f, 100.0f)
        .lookAt(6, 0, 1, 
                0, 0, 0, 
                0, 1, 0);
        Vector3f origin = new Vector3f();
        m.perspectiveOrigin(origin);
        TestUtil.assertVector3fEquals(new Vector3f(6, 0, 1), origin, 1E-5f);

        // test symmetric frustum with some modelview translation and rotation
        m = new Matrix4f()
        .perspective((float) Math.toRadians(90), 1.0f, 0.1f, 100.0f)
        .lookAt(-5, 2, 1, 
                0, 1, 0, 
                0, 1, 0);
        m.perspectiveOrigin(origin);
        TestUtil.assertVector3fEquals(new Vector3f(-5, 2, 1), origin, 1E-5f);

        // test asymmetric frustum
        m = new Matrix4f()
        .frustum(-0.1f, 0.5f, -0.1f, 0.1f, 0.1f, 100.0f)
        .lookAt(-5, 2, 1, 
                0, 1, 0, 
                0, 1, 0);
        m.perspectiveOrigin(origin);
        TestUtil.assertVector3fEquals(new Vector3f(-5, 2, 1), origin, 1E-5f);
    }

    public void testPerspectiveFov() {
        Matrix4f m = new Matrix4f()
        .perspective((float) Math.toRadians(45), 1.0f, 0.1f, 100.0f);
        float fov = m.perspectiveFov();
        assertEquals(Math.toRadians(45), fov, 1E-5);

        m = new Matrix4f()
        .perspective((float) Math.toRadians(90), 1.0f, 0.1f, 100.0f)
        .lookAt(6, 0, 1, 
                0, 0, 0, 
                0, 1, 0);
        fov = m.perspectiveFov();
        assertEquals(Math.toRadians(90), fov, 1E-5);
    }

    public void testNormal() {
        Matrix4f r = new Matrix4f().rotateY((float) Math.PI / 2);
        Matrix4f s = new Matrix4f(r).scale(0.2f);
        Matrix4f n = new Matrix4f();
        s.normal(n);
        n.normalize3x3();
        TestUtil.assertMatrix4fEquals(r, n, 1E-8f);
    }

}
