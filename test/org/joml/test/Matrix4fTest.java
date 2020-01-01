/*
 * The MIT License
 *
 * Copyright (c) 2015-2020 JOML.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.joml.test;

import junit.framework.TestCase;

import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.joml.Math;

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
        TestUtil.assertMatrix4fEquals(m1, m2, 1E-2f);
        m1 = new Matrix4f().lookAt(3, 2, 0, 0, 0, 0, 0, 1, 0);
        m2 = new Matrix4f().translate(0, 0, -(float) Math.sqrt(2 * 2 + 3 * 3))
                .rotateX((float) Math.atan2(2, 3)).rotateY((float) Math.toRadians(-90));
        TestUtil.assertMatrix4fEquals(m1, m2, 1E-2f);
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
        m.frustumPlane(Matrix4fc.PLANE_NX, left);
        m.frustumPlane(Matrix4fc.PLANE_PX, right);
        m.frustumPlane(Matrix4fc.PLANE_NY, bottom);
        m.frustumPlane(Matrix4fc.PLANE_PY, top);
        m.frustumPlane(Matrix4fc.PLANE_NZ, near);
        m.frustumPlane(Matrix4fc.PLANE_PZ, far);

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
        m.frustumCorner(Matrix4fc.CORNER_NXNYNZ, corner); // left, bottom, near
        TestUtil.assertVector3fEquals(new Vector3f(-1, -1, -1), corner, 1E-6f);
        m.frustumCorner(Matrix4fc.CORNER_PXNYNZ, corner); // right, bottom, near
        TestUtil.assertVector3fEquals(new Vector3f(1, -1, -1), corner, 1E-6f);
        m.frustumCorner(Matrix4fc.CORNER_PXNYPZ, corner); // right, bottom, far
        TestUtil.assertVector3fEquals(new Vector3f(1, -1, 1), corner, 1E-6f);
        m.frustumCorner(Matrix4fc.CORNER_NXPYPZ, corner); // left, top, far
        TestUtil.assertVector3fEquals(new Vector3f(-1, 1, 1), corner, 1E-6f);
    }

    public static void testFrustumCornerOrthoWide() {
        Matrix4f m = new Matrix4f().ortho2D(-2, 2, -1, 1);
        Vector3f corner = new Vector3f();
        m.frustumCorner(Matrix4fc.CORNER_NXNYNZ, corner); // left, bottom, near
        TestUtil.assertVector3fEquals(new Vector3f(-2, -1, 1), corner, 1E-6f);
        m.frustumCorner(Matrix4fc.CORNER_PXNYNZ, corner); // right, bottom, near
        TestUtil.assertVector3fEquals(new Vector3f(2, -1, 1), corner, 1E-6f);
        m.frustumCorner(Matrix4fc.CORNER_PXNYPZ, corner); // right, bottom, far
        TestUtil.assertVector3fEquals(new Vector3f(2, -1, -1), corner, 1E-6f);
        m.frustumCorner(Matrix4fc.CORNER_NXPYPZ, corner); // left, top, far
        TestUtil.assertVector3fEquals(new Vector3f(-2, 1, -1), corner, 1E-6f);
    }

    public static void testFrustumCorner() {
        Matrix4f m = new Matrix4f()
        .perspective((float) Math.toRadians(90), 1.0f, 0.1f, 100.0f)
        .lookAt(0, 0, 10,
                0, 0,  0, 
                0, 1,  0);
        Vector3f corner = new Vector3f();
        m.frustumCorner(Matrix4fc.CORNER_NXNYNZ, corner); // left, bottom, near
        TestUtil.assertVector3fEquals(new Vector3f(-0.1f, -0.1f, 10 - 0.1f), corner, 1E-6f);
        m.frustumCorner(Matrix4fc.CORNER_PXNYNZ, corner); // right, bottom, near
        TestUtil.assertVector3fEquals(new Vector3f(0.1f, -0.1f, 10 - 0.1f), corner, 1E-6f);
        m.frustumCorner(Matrix4fc.CORNER_PXNYPZ, corner); // right, bottom, far
        TestUtil.assertVector3fEquals(new Vector3f(100.0f, -100, 10 - 100f), corner, 1E-3f);
    }

    public static void testFrustumCornerWide() {
        Matrix4f m = new Matrix4f()
        .perspective((float) Math.toRadians(90), 2.0f, 0.1f, 100.0f)
        .lookAt(0, 0, 10,
                0, 0,  0, 
                0, 1,  0);
        Vector3f corner = new Vector3f();
        m.frustumCorner(Matrix4fc.CORNER_NXNYNZ, corner); // left, bottom, near
        TestUtil.assertVector3fEquals(new Vector3f(-0.2f, -0.1f, 10 - 0.1f), corner, 1E-5f);
        m.frustumCorner(Matrix4fc.CORNER_PXNYNZ, corner); // right, bottom, near
        TestUtil.assertVector3fEquals(new Vector3f(0.2f, -0.1f, 10 - 0.1f), corner, 1E-5f);
        m.frustumCorner(Matrix4fc.CORNER_PXNYPZ, corner); // right, bottom, far
        TestUtil.assertVector3fEquals(new Vector3f(200.0f, -100, 10 - 100f), corner, 1E-3f);
    }

    public static void testFrustumCornerRotate() {
        Matrix4f m = new Matrix4f()
        .perspective((float) Math.toRadians(90), 1.0f, 0.1f, 100.0f)
        .lookAt(10, 0, 0, 
                 0, 0, 0, 
                 0, 1, 0);
        Vector3f corner = new Vector3f();
        m.frustumCorner(Matrix4fc.CORNER_NXNYNZ, corner); // left, bottom, near
        TestUtil.assertVector3fEquals(new Vector3f(10 - 0.1f, -0.1f, 0.1f), corner, 1E-6f);
        m.frustumCorner(Matrix4fc.CORNER_PXNYNZ, corner); // right, bottom, near
        TestUtil.assertVector3fEquals(new Vector3f(10 - 0.1f, -0.1f, -0.1f), corner, 1E-6f);
        m.frustumCorner(Matrix4fc.CORNER_PXNYPZ, corner); // right, bottom, far
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

    public static void testInvertAffine() {
        Matrix4f invm = new Matrix4f();
        Matrix4f m = new Matrix4f();
        m.rotateX(1.2f).rotateY(0.2f).rotateZ(0.1f).translate(1, 2, 3).invertAffine(invm);
        Vector3f orig = new Vector3f(4, -6, 8);
        Vector3f v = new Vector3f();
        Vector3f w = new Vector3f();
        m.transformPosition(orig, v);
        invm.transformPosition(v, w);
        TestUtil.assertVector3fEquals(orig, w, 1E-6f);
        invm.invertAffine();
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
        TestUtil.assertMatrix4fEquals(m, invm, 1E-3f);
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

    public static void testRotateAffineXYZ() {
        Matrix4f m = new Matrix4f().rotateX(0.12f).rotateY(0.0623f).rotateZ(0.95f);
        Matrix4f n = new Matrix4f().rotateAffineXYZ(0.12f, 0.0623f, 0.95f);
        TestUtil.assertMatrix4fEquals(m, n, 1E-6f);
    }

    public static void testRotateAffineZYX() {
        Matrix4f m = new Matrix4f().rotateZ(1.12f).rotateY(0.0623f).rotateX(0.95f);
        Matrix4f n = new Matrix4f().rotateAffineZYX(1.12f, 0.0623f, 0.95f);
        TestUtil.assertMatrix4fEquals(m, n, 1E-6f);
    }

    public static void testRotateAffineYXZ() {
        Matrix4f m = new Matrix4f().rotateY(1.12f).rotateX(0.0623f).rotateZ(0.95f);
        Matrix4f n = new Matrix4f().rotateAffineYXZ(1.12f, 0.0623f, 0.95f);
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

    public static void testOrthoCrop() {
        Matrix4f lightView = new Matrix4f()
                .lookAt(0, 5, 0,
                        0, 0, 0,
                       -1, 0, 0);
        Matrix4f crop = new Matrix4f();
        Matrix4f fin = new Matrix4f();
        new Matrix4f().ortho2D(-1, 1, -1, 1).invertAffine().orthoCrop(lightView, crop).mulOrthoAffine(lightView, fin);
        Vector3f p = new Vector3f();
        fin.transformProject(p.set(1, -1, -1));
        assertEquals(+1.0f, p.x, 1E-6f);
        assertEquals(-1.0f, p.y, 1E-6f);
        assertEquals(+1.0f, p.z, 1E-6f);
        fin.transformProject(p.set(-1, -1, -1));
        assertEquals(+1.0f, p.x, 1E-6f);
        assertEquals(+1.0f, p.y, 1E-6f);
        assertEquals(+1.0f, p.z, 1E-6f);
    }

    public static void testOrthoCropWithPerspective() {
        Matrix4f lightView = new Matrix4f()
                .lookAt(0, 5, 0,
                        0, 0, 0,
                        0, 0, -1);
        Matrix4f crop = new Matrix4f();
        Matrix4f fin = new Matrix4f();
        new Matrix4f().perspective((float) Math.toRadians(90), 1.0f, 5, 10).invertPerspective().orthoCrop(lightView, crop).mulOrthoAffine(lightView, fin);
        Vector3f p = new Vector3f();
        fin.transformProject(p.set(0, 0, -5));
        assertEquals(+0.0f, p.x, 1E-6f);
        assertEquals(-1.0f, p.y, 1E-6f);
        assertEquals(+0.0f, p.z, 1E-6f);
        fin.transformProject(p.set(0, 0, -10));
        assertEquals(+0.0f, p.x, 1E-6f);
        assertEquals(+1.0f, p.y, 1E-6f);
        assertEquals(+0.0f, p.z, 1E-6f);
        fin.transformProject(p.set(-10, 10, -10));
        assertEquals(-1.0f, p.x, 1E-6f);
        assertEquals(+1.0f, p.y, 1E-6f);
        assertEquals(-1.0f, p.z, 1E-6f);
    }

    public static void testRotateTowardsXY() {
        Vector3f v = new Vector3f(1, 1, 0).normalize();
        Matrix4f m1 = new Matrix4f().rotateZ(v.angle(new Vector3f(1, 0, 0)), new Matrix4f());
        Matrix4f m2 = new Matrix4f().rotateTowardsXY(v.x, v.y, new Matrix4f());
        TestUtil.assertMatrix4fEquals(m1, m2, 0);
        Vector3f t = m1.transformDirection(new Vector3f(0, 1, 0));
        TestUtil.assertVector3fEquals(new Vector3f(-1, 1, 0).normalize(), t, 1E-6f);
    }

    public static void testTestPoint() {
        Matrix4f m = new Matrix4f().perspective((float)Math.toRadians(90), 1.0f, 0.1f, 10.0f).lookAt(0, 0, 10, 0, 0, 0, 0, 1, 0).scale(2);
        assertTrue(m.testPoint(0, 0, 0));
        assertTrue(m.testPoint(9.999f*0.5f, 0, 0));
        assertFalse(m.testPoint(10.001f*0.5f, 0, 0));
    }

    public static void testTestAab() {
        Matrix4f m = new Matrix4f().perspective((float)Math.toRadians(90), 1.0f, 0.1f, 10.0f).lookAt(0, 0, 10, 0, 0, 0, 0, 1, 0).scale(2);
        assertTrue(m.testAab(-1, -1, -1, 1, 1, 1));
        assertTrue(m.testAab(9.999f*0.5f, 0, 0, 10, 1, 1));
        assertFalse(m.testAab(10.001f*0.5f, 0, 0, 10, 1, 1));
    }

}
