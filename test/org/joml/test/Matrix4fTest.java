package org.joml.test;

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
    public static void testProjectUnproject() {
        /* Build some arbitrary viewport. */
        int[] viewport = {0, 0, 800, 800};

        Vector3f expected = new Vector3f(1.0f, 2.0f, -3.0f);
        Vector3f actual = new Vector3f();

        /* Build a perspective projection and then project and unproject. */
        Matrix4f m = new Matrix4f()
        .perspective((float) Math.toRadians(45.0f), 1.0f, 0.01f, 100.0f);
        m.project(expected, viewport, actual);
        m.unproject(actual, viewport, actual);

        /* Check for equality of the components */
        assertEquals(expected.x, actual.x, TestUtil.MANY_OPS_AROUND_ZERO_PRECISION_FLOAT);
        assertEquals(expected.y, actual.y, TestUtil.MANY_OPS_AROUND_ZERO_PRECISION_FLOAT);
        assertEquals(expected.z, actual.z, TestUtil.MANY_OPS_AROUND_ZERO_PRECISION_FLOAT);
    }

    public static void testLookAt() {
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
    public static void testFrustumPlanePerspectiveRotateTranslate() {
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
        Matrix4f m = new Matrix4f()
        .perspective((float) Math.toRadians(90), 1.0f, 0.1f, 100.0f)
        .rotateY((float) Math.toRadians(90))
        .translate(0, -5, 0);
        m.frustumPlane(Matrix4f.PLANE_NX, left);
        m.frustumPlane(Matrix4f.PLANE_PX, right);
        m.frustumPlane(Matrix4f.PLANE_NY, bottom);
        m.frustumPlane(Matrix4f.PLANE_PY, top);
        m.frustumPlane(Matrix4f.PLANE_NZ, near);
        m.frustumPlane(Matrix4f.PLANE_PZ, far);

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

    public static void testFrustumRay() {
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

    public static void testFrustumRay2() {
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

    public static void testPositiveXRotateY() {
        Vector3f dir = new Vector3f();
        Matrix4f m = new Matrix4f()
                .rotateY((float) Math.toRadians(90));
        m.positiveX(dir);
        TestUtil.assertVector3fEquals(new Vector3f(0, 0, 1), dir, 1E-7f);
    }

    public static void testPositiveYRotateX() {
        Vector3f dir = new Vector3f();
        Matrix4f m = new Matrix4f()
                .rotateX((float) Math.toRadians(90));
        m.positiveY(dir);
        TestUtil.assertVector3fEquals(new Vector3f(0, 0, -1), dir, 1E-7f);
    }

    public static void testPositiveZRotateX() {
        Vector3f dir = new Vector3f();
        Matrix4f m = new Matrix4f()
                .rotateX((float) Math.toRadians(90));
        m.positiveZ(dir);
        TestUtil.assertVector3fEquals(new Vector3f(0, 1, 0), dir, 1E-7f);
    }

    public static void testPositiveXRotateXY() {
        Vector3f dir = new Vector3f();
        Matrix4f m = new Matrix4f()
                .rotateY((float) Math.toRadians(90)).rotateX((float) Math.toRadians(45));
        m.positiveX(dir);
        TestUtil.assertVector3fEquals(new Vector3f(0, 1, 1).normalize(), dir, 1E-7f);
    }

    public static void testPositiveXPerspectiveRotateY() {
        Vector3f dir = new Vector3f();
        Matrix4f m = new Matrix4f()
                .perspective((float) Math.toRadians(90), 1.0f, 0.1f, 100.0f)
                .rotateY((float) Math.toRadians(90));
        m.positiveX(dir);
        TestUtil.assertVector3fEquals(new Vector3f(0, 0, -1), dir, 1E-7f);
    }

    public static void testPositiveXPerspectiveRotateXY() {
        Vector3f dir = new Vector3f();
        Matrix4f m = new Matrix4f()
                .perspective((float) Math.toRadians(90), 1.0f, 0.1f, 100.0f)
                .rotateY((float) Math.toRadians(90)).rotateX((float) Math.toRadians(45));
        m.positiveX(dir);
        TestUtil.assertVector3fEquals(new Vector3f(0, -1, -1).normalize(), dir, 1E-7f);
    }

    public static void testPositiveXYZLookAt() {
        Vector3f dir = new Vector3f();
        Matrix4f m = new Matrix4f()
                .lookAt(0, 0, 0, -1, 0, 0, 0, 1, 0);
        m.positiveX(dir);
        TestUtil.assertVector3fEquals(new Vector3f(0, 0, -1).normalize(), dir, 1E-7f);
        m.positiveY(dir);
        TestUtil.assertVector3fEquals(new Vector3f(0, 1, 0).normalize(), dir, 1E-7f);
        m.positiveZ(dir);
        TestUtil.assertVector3fEquals(new Vector3f(1, 0, 0).normalize(), dir, 1E-7f);
    }

    public static void testPositiveXYZSameAsInvert() {
        Vector3f dir = new Vector3f();
        Vector3f dir2 = new Vector3f();
        Matrix4f m = new Matrix4f().rotateXYZ(0.12f, 1.25f, -2.56f);
        Matrix4f inv = new Matrix4f(m).invert();
        m.positiveX(dir);
        inv.transformDirection(dir2.set(1, 0, 0));
        TestUtil.assertVector3fEquals(dir2, dir, 1E-6f);
        m.positiveY(dir);
        inv.transformDirection(dir2.set(0, 1, 0));
        TestUtil.assertVector3fEquals(dir2, dir, 1E-6f);
        m.positiveZ(dir);
        inv.transformDirection(dir2.set(0, 0, 1));
        TestUtil.assertVector3fEquals(dir2, dir, 1E-6f);
    }

    public static void testFrustumCornerIdentity() {
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

    public static void testFrustumCornerOrthoWide() {
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

    public static void testFrustumCorner() {
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

    public static void testFrustumCornerWide() {
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

    public static void testFrustumCornerRotate() {
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

    public static void testPerspectiveOrigin() {
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

    public static void testPerspectiveFov() {
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

    public static void testNormal() {
        Matrix4f r = new Matrix4f().rotateY((float) Math.PI / 2);
        Matrix4f s = new Matrix4f(r).scale(0.2f);
        Matrix4f n = new Matrix4f();
        s.normal(n);
        n.normalize3x3();
        TestUtil.assertMatrix4fEquals(r, n, 1E-8f);
    }

    public static void testInvert4x3() {
        Matrix4f invm = new Matrix4f();
        Matrix4f m = new Matrix4f();
        m.rotateX(1.2f).rotateY(0.2f).rotateZ(0.1f).translate(1, 2, 3).invert4x3(invm);
        Vector3f orig = new Vector3f(4, -6, 8);
        Vector3f v = new Vector3f();
        Vector3f w = new Vector3f();
        m.transformPosition(orig, v);
        invm.transformPosition(v, w);
        TestUtil.assertVector3fEquals(orig, w, 1E-6f);
        invm.invert4x3();
        TestUtil.assertMatrix4fEquals(m, invm, 1E-6f);
    }

    public static void testInvert() {
        Matrix4f invm = new Matrix4f();
        Matrix4f m = new Matrix4f();
        m.perspective(0.1123f, 0.5f, 0.1f, 100.0f).rotateX(1.2f).rotateY(0.2f).rotateZ(0.1f).translate(1, 2, 3).invert(invm);
        Vector4f orig = new Vector4f(4, -6, 8, 1);
        Vector4f v = new Vector4f();
        Vector4f w = new Vector4f();
        m.transform(orig, v);
        invm.transform(v, w);
        TestUtil.assertVector4fEquals(orig, w, 1E-4f);
        invm.invert();
        TestUtil.assertMatrix4fEquals(m, invm, 1E-4f);
    }

    public static void testRotateXYZ() {
        Matrix4f m = new Matrix4f().rotateX(0.12f).rotateY(0.0623f).rotateZ(0.95f);
        Matrix4f n = new Matrix4f().rotateXYZ(0.12f, 0.0623f, 0.95f);
        TestUtil.assertMatrix4fEquals(m, n, 1E-6f);
    }

    public static void testRotateZYX() {
        Matrix4f m = new Matrix4f().rotateZ(1.12f).rotateY(0.0623f).rotateX(0.95f);
        Matrix4f n = new Matrix4f().rotateZYX(1.12f, 0.0623f, 0.95f);
        TestUtil.assertMatrix4fEquals(m, n, 1E-6f);
    }

    public static void testRotateYXZ() {
        Matrix4f m = new Matrix4f().rotateY(1.12f).rotateX(0.0623f).rotateZ(0.95f);
        Matrix4f n = new Matrix4f().rotateYXZ(1.12f, 0.0623f, 0.95f);
        TestUtil.assertMatrix4fEquals(m, n, 1E-6f);
    }

    public static void testRotateXYZ4x3() {
        Matrix4f m = new Matrix4f().rotateX(0.12f).rotateY(0.0623f).rotateZ(0.95f);
        Matrix4f n = new Matrix4f().rotateXYZ4x3(0.12f, 0.0623f, 0.95f);
        TestUtil.assertMatrix4fEquals(m, n, 1E-6f);
    }

    public static void testRotateZYX4x3() {
        Matrix4f m = new Matrix4f().rotateZ(1.12f).rotateY(0.0623f).rotateX(0.95f);
        Matrix4f n = new Matrix4f().rotateZYX4x3(1.12f, 0.0623f, 0.95f);
        TestUtil.assertMatrix4fEquals(m, n, 1E-6f);
    }

    public static void testRotateYXZ4x3() {
        Matrix4f m = new Matrix4f().rotateY(1.12f).rotateX(0.0623f).rotateZ(0.95f);
        Matrix4f n = new Matrix4f().rotateYXZ4x3(1.12f, 0.0623f, 0.95f);
        TestUtil.assertMatrix4fEquals(m, n, 1E-6f);
    }

    public static void testRotationXYZ() {
        Matrix4f m = new Matrix4f().rotationX(0.32f).rotateY(0.5623f).rotateZ(0.95f);
        Matrix4f n = new Matrix4f().rotationXYZ(0.32f, 0.5623f, 0.95f);
        TestUtil.assertMatrix4fEquals(m, n, 1E-6f);
    }

    public static void testRotationZYX() {
        Matrix4f m = new Matrix4f().rotationZ(0.12f).rotateY(0.0623f).rotateX(0.95f);
        Matrix4f n = new Matrix4f().rotationZYX(0.12f, 0.0623f, 0.95f);
        TestUtil.assertMatrix4fEquals(m, n, 1E-6f);
    }

    public static void testRotationYXZ() {
        Matrix4f m = new Matrix4f().rotationY(0.12f).rotateX(0.0623f).rotateZ(0.95f);
        Matrix4f n = new Matrix4f().rotationYXZ(0.12f, 0.0623f, 0.95f);
        TestUtil.assertMatrix4fEquals(m, n, 1E-6f);
    }

}
