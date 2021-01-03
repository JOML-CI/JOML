/*
 * The MIT License
 *
 * Copyright (c) 2015-2021 JOML.
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

import org.joml.*;
import org.joml.Math;

/**
 * Tests for the {@link Matrix4d} class.
 * 
 * @author Kai Burjack
 */
public class Matrix4dTest extends TestCase {

    /**
     * Test that project and unproject are each other's inverse operations.
     */
    public static void testProjectUnproject() {
        /* Build some arbitrary viewport. */
        int[] viewport = {0, 0, 800, 800};

        Vector3d expected = new Vector3d(1.0f, 2.0f, -3.0f);
        Vector3d actual = new Vector3d();

        /* Build a perspective projection and then project and unproject. */
        Matrix4d m = new Matrix4d()
        .perspective((float) Math.toRadians(45.0f), 1.0f, 0.01f, 100.0f);
        m.project(expected, viewport, actual);
        m.unproject(actual, viewport, actual);

        /* Check for equality of the components */
        assertEquals(expected.x, actual.x, TestUtil.MANY_OPS_AROUND_ZERO_PRECISION_FLOAT);
        assertEquals(expected.y, actual.y, TestUtil.MANY_OPS_AROUND_ZERO_PRECISION_FLOAT);
        assertEquals(expected.z, actual.z, TestUtil.MANY_OPS_AROUND_ZERO_PRECISION_FLOAT);
    }

    public static void testLookAt() {
        Matrix4d m1, m2;
        m1 = new Matrix4d().lookAt(0, 2, 3, 0, 0, 0, 0, 1, 0);
        m2 = new Matrix4d().translate(0, 0, -(float) Math.sqrt(2 * 2 + 3 * 3)).rotateX(
                (float) Math.atan2(2, 3));
        TestUtil.assertMatrix4dEquals(m1, m2, 1E-2f);
        m1 = new Matrix4d().lookAt(3, 2, 0, 0, 0, 0, 0, 1, 0);
        m2 = new Matrix4d().translate(0, 0, -(float) Math.sqrt(2 * 2 + 3 * 3))
                .rotateX((float) Math.atan2(2, 3)).rotateY((float) Math.toRadians(-90));
        TestUtil.assertMatrix4dEquals(m1, m2, 1E-2f);
    }

    /**
     * Test computing the frustum planes with a combined view-projection matrix with translation.
     */
    public static void testFrustumPlanePerspectiveRotateTranslate() {
        Vector4d left = new Vector4d();
        Vector4d right = new Vector4d();
        Vector4d top = new Vector4d();
        Vector4d bottom = new Vector4d();
        Vector4d near = new Vector4d();
        Vector4d far = new Vector4d();

        /*
         * Build a perspective transformation and
         * move the camera 5 units "up" and rotate it clock-wise 90 degrees around Y.
         */
        Matrix4d m = new Matrix4d()
        .perspective((float) Math.toRadians(90), 1.0f, 0.1f, 100.0f)
        .rotateY((float) Math.toRadians(90))
        .translate(0, -5, 0);
        m.frustumPlane(Matrix4dc.PLANE_NX, left);
        m.frustumPlane(Matrix4dc.PLANE_PX, right);
        m.frustumPlane(Matrix4dc.PLANE_NY, bottom);
        m.frustumPlane(Matrix4dc.PLANE_PY, top);
        m.frustumPlane(Matrix4dc.PLANE_NZ, near);
        m.frustumPlane(Matrix4dc.PLANE_PZ, far);

        Vector4d expectedLeft = new Vector4d(1, 0, 1, 0).normalize3();
        Vector4d expectedRight = new Vector4d(1, 0, -1, 0).normalize3();
        Vector4d expectedTop = new Vector4d(1, -1, 0, 5).normalize3();
        Vector4d expectedBottom = new Vector4d(1, 1, 0, -5).normalize3();
        Vector4d expectedNear = new Vector4d(1, 0, 0, -0.1f).normalize3();
        Vector4d expectedFar = new Vector4d(-1, 0, 0, 100.0f).normalize3();

        TestUtil.assertVector4dEquals(expectedLeft, left, 1E-5f);
        TestUtil.assertVector4dEquals(expectedRight, right, 1E-5f);
        TestUtil.assertVector4dEquals(expectedTop, top, 1E-5f);
        TestUtil.assertVector4dEquals(expectedBottom, bottom, 1E-5f);
        TestUtil.assertVector4dEquals(expectedNear, near, 1E-5f);
        TestUtil.assertVector4dEquals(expectedFar, far, 1E-4f);
    }

    public static void testFrustumRay() {
        Vector3d dir = new Vector3d();
        Matrix4d m = new Matrix4d()
                .perspective((float) Math.toRadians(90), 1.0f, 0.1f, 100.0f)
                .rotateY((float) Math.toRadians(90));
        Vector3d expectedDir;
        m.frustumRayDir(0, 0, dir);
        expectedDir = new Vector3d(1, -1, -1).normalize();
        TestUtil.assertVector3dEquals(expectedDir, dir, 1E-5f);
        m.frustumRayDir(1, 0, dir);
        expectedDir = new Vector3d(1, -1, 1).normalize();
        TestUtil.assertVector3dEquals(expectedDir, dir, 1E-5f);
        m.frustumRayDir(0, 1, dir);
        expectedDir = new Vector3d(1, 1, -1).normalize();
        TestUtil.assertVector3dEquals(expectedDir, dir, 1E-5f);
        m.frustumRayDir(1, 1, dir);
        expectedDir = new Vector3d(1, 1, 1).normalize();
        TestUtil.assertVector3dEquals(expectedDir, dir, 1E-5f);
    }

    public static void testFrustumRay2() {
        Vector3d dir = new Vector3d();
        Matrix4d m = new Matrix4d()
                .perspective((float) Math.toRadians(90), 1.0f, 0.1f, 100.0f)
                .rotateZ((float) Math.toRadians(45));
        Vector3d expectedDir;
        m.frustumRayDir(0, 0, dir);
        expectedDir = new Vector3d(-(float)Math.sqrt(2), 0, -1).normalize();
        TestUtil.assertVector3dEquals(expectedDir, dir, 1E-5f);
        m.frustumRayDir(1, 0, dir);
        expectedDir = new Vector3d(0, -(float)Math.sqrt(2), -1).normalize();
        TestUtil.assertVector3dEquals(expectedDir, dir, 1E-5f);
        m.frustumRayDir(0, 1, dir);
        expectedDir = new Vector3d(0, (float)Math.sqrt(2), -1).normalize();
        TestUtil.assertVector3dEquals(expectedDir, dir, 1E-5f);
        m.frustumRayDir(1, 1, dir);
        expectedDir = new Vector3d((float)Math.sqrt(2), 0, -1).normalize();
        TestUtil.assertVector3dEquals(expectedDir, dir, 1E-5f);
    }

    public static void testMatrix4dTranspose() {
        double m00 = 1, m01 = 2, m02 = 3, m03 = 4;
        double m10 = 5, m11 = 6, m12 = 7, m13 = 8;
        double m20 = 9, m21 = 10, m22 = 11, m23 = 12;
        double m30 = 13, m31 = 14, m32 = 15, m33 = 16;

        Matrix4d m = new Matrix4d(m00,m01,m02,m03,m10,m11,m12,m13,m20,m21,m22,m23,m30,m31,m32,m33);
        Matrix4d expect = new Matrix4d(m00,m10,m20,m30,m01,m11,m21,m31,m02,m12,m22,m32,m03,m13,m23,m33);
        TestUtil.assertMatrix4dEquals(new Matrix4d(m).transpose(),expect, 1E-5f);
        TestUtil.assertMatrix4dEquals(new Matrix4d(m).transpose(new Matrix4d()),expect, 1E-5f);
    }

    public static void testMatrix4d3fTranspose() {
        double m00 = 1, m01 = 2, m02 = 3, m03 = 4;
        double m10 = 5, m11 = 6, m12 = 7, m13 = 8;
        double m20 = 9, m21 = 10, m22 = 11, m23 = 12;
        double m30 = 13, m31 = 14, m32 = 15, m33 = 16;

        Matrix4d m = new Matrix4d(m00,m01,m02,m03,m10,m11,m12,m13,m20,m21,m22,m23,m30,m31,m32,m33);
        Matrix4d expect = new Matrix4d(m00,m10,m20,m03,m01,m11,m21,m13,m02,m12,m22,m23,m30,m31,m32,m33);
        TestUtil.assertMatrix4dEquals(new Matrix4d(m).transpose3x3(),expect, 1E-5f);
        Matrix3d expect1 = new Matrix3d(m00,m10,m20,m01,m11,m21,m02,m12,m22);
        Matrix4d expect2 = new Matrix4d(expect1);
        TestUtil.assertMatrix4dEquals(new Matrix4d(m).transpose3x3(new Matrix4d()),expect2, 1E-5f);
        TestUtil.assertMatrix3dEquals(new Matrix4d(m).transpose3x3(new Matrix3d()),expect1, 1E-5f);
    }

    public static void testPositiveXRotateY() {
        Vector3d dir = new Vector3d();
        Matrix4d m = new Matrix4d()
                .rotateY((float) Math.toRadians(90));
        m.positiveX(dir);
        TestUtil.assertVector3dEquals(new Vector3d(0, 0, 1), dir, 1E-7f);
    }

    public static void testPositiveYRotateX() {
        Vector3d dir = new Vector3d();
        Matrix4d m = new Matrix4d()
                .rotateX((float) Math.toRadians(90));
        m.positiveY(dir);
        TestUtil.assertVector3dEquals(new Vector3d(0, 0, -1), dir, 1E-7f);
    }

    public static void testPositiveZRotateX() {
        Vector3d dir = new Vector3d();
        Matrix4d m = new Matrix4d()
                .rotateX((float) Math.toRadians(90));
        m.positiveZ(dir);
        TestUtil.assertVector3dEquals(new Vector3d(0, 1, 0), dir, 1E-7f);
    }

    public static void testPositiveXRotateXY() {
        Vector3d dir = new Vector3d();
        Matrix4d m = new Matrix4d()
                .rotateY((float) Math.toRadians(90)).rotateX((float) Math.toRadians(45));
        m.positiveX(dir);
        TestUtil.assertVector3dEquals(new Vector3d(0, 1, 1).normalize(), dir, 1E-7f);
    }

    public static void testPositiveXPerspectiveRotateY() {
        Vector3d dir = new Vector3d();
        Matrix4d m = new Matrix4d()
                .perspective((float) Math.toRadians(90), 1.0f, 0.1f, 100.0f)
                .rotateY((float) Math.toRadians(90));
        m.positiveX(dir);
        TestUtil.assertVector3dEquals(new Vector3d(0, 0, -1), dir, 1E-7f);
    }

    public static void testPositiveXPerspectiveRotateXY() {
        Vector3d dir = new Vector3d();
        Matrix4d m = new Matrix4d()
                .perspective((float) Math.toRadians(90), 1.0f, 0.1f, 100.0f)
                .rotateY((float) Math.toRadians(90)).rotateX((float) Math.toRadians(45));
        m.positiveX(dir);
        TestUtil.assertVector3dEquals(new Vector3d(0, -1, -1).normalize(), dir, 1E-7f);
    }

    public static void testPositiveXYZLookAt() {
        Vector3d dir = new Vector3d();
        Matrix4d m = new Matrix4d()
                .lookAt(0, 0, 0, -1, 0, 0, 0, 1, 0);
        m.positiveX(dir);
        TestUtil.assertVector3dEquals(new Vector3d(0, 0, -1).normalize(), dir, 1E-7f);
        m.positiveY(dir);
        TestUtil.assertVector3dEquals(new Vector3d(0, 1, 0).normalize(), dir, 1E-7f);
        m.positiveZ(dir);
        TestUtil.assertVector3dEquals(new Vector3d(1, 0, 0).normalize(), dir, 1E-7f);
    }

    public static void testPositiveXYZSameAsInvert() {
        Vector3d dir = new Vector3d();
        Vector3d dir2 = new Vector3d();
        Matrix4d m = new Matrix4d().rotateXYZ(0.12f, 1.25f, -2.56f);
        Matrix4d inv = new Matrix4d(m).invert();
        m.positiveX(dir);
        inv.transformDirection(dir2.set(1, 0, 0));
        TestUtil.assertVector3dEquals(dir2, dir, 1E-6f);
        m.positiveY(dir);
        inv.transformDirection(dir2.set(0, 1, 0));
        TestUtil.assertVector3dEquals(dir2, dir, 1E-6f);
        m.positiveZ(dir);
        inv.transformDirection(dir2.set(0, 0, 1));
        TestUtil.assertVector3dEquals(dir2, dir, 1E-6f);
    }

    public static void testFrustumCornerIdentity() {
        Matrix4d m = new Matrix4d();
        Vector3d corner = new Vector3d();
        m.frustumCorner(Matrix4dc.CORNER_NXNYNZ, corner); // left, bottom, near
        TestUtil.assertVector3dEquals(new Vector3d(-1, -1, -1), corner, 1E-6f);
        m.frustumCorner(Matrix4dc.CORNER_PXNYNZ, corner); // right, bottom, near
        TestUtil.assertVector3dEquals(new Vector3d(1, -1, -1), corner, 1E-6f);
        m.frustumCorner(Matrix4dc.CORNER_PXNYPZ, corner); // right, bottom, far
        TestUtil.assertVector3dEquals(new Vector3d(1, -1, 1), corner, 1E-6f);
        m.frustumCorner(Matrix4dc.CORNER_NXPYPZ, corner); // left, top, far
        TestUtil.assertVector3dEquals(new Vector3d(-1, 1, 1), corner, 1E-6f);
    }

    public static void testFrustumCornerOrthoWide() {
        Matrix4d m = new Matrix4d().ortho2D(-2, 2, -1, 1);
        Vector3d corner = new Vector3d();
        m.frustumCorner(Matrix4dc.CORNER_NXNYNZ, corner); // left, bottom, near
        TestUtil.assertVector3dEquals(new Vector3d(-2, -1, 1), corner, 1E-6f);
        m.frustumCorner(Matrix4dc.CORNER_PXNYNZ, corner); // right, bottom, near
        TestUtil.assertVector3dEquals(new Vector3d(2, -1, 1), corner, 1E-6f);
        m.frustumCorner(Matrix4dc.CORNER_PXNYPZ, corner); // right, bottom, far
        TestUtil.assertVector3dEquals(new Vector3d(2, -1, -1), corner, 1E-6f);
        m.frustumCorner(Matrix4dc.CORNER_NXPYPZ, corner); // left, top, far
        TestUtil.assertVector3dEquals(new Vector3d(-2, 1, -1), corner, 1E-6f);
    }

    public static void testFrustumCorner() {
        Matrix4d m = new Matrix4d()
        .perspective((float) Math.toRadians(90), 1.0f, 0.1f, 100.0f)
        .lookAt(0, 0, 10,
                0, 0,  0, 
                0, 1,  0);
        Vector3d corner = new Vector3d();
        m.frustumCorner(Matrix4dc.CORNER_NXNYNZ, corner); // left, bottom, near
        TestUtil.assertVector3dEquals(new Vector3d(-0.1f, -0.1f, 10 - 0.1f), corner, 1E-6f);
        m.frustumCorner(Matrix4dc.CORNER_PXNYNZ, corner); // right, bottom, near
        TestUtil.assertVector3dEquals(new Vector3d(0.1f, -0.1f, 10 - 0.1f), corner, 1E-6f);
        m.frustumCorner(Matrix4dc.CORNER_PXNYPZ, corner); // right, bottom, far
        TestUtil.assertVector3dEquals(new Vector3d(100.0f, -100, 10 - 100f), corner, 1E-3f);
    }

    public static void testFrustumCornerWide() {
        Matrix4d m = new Matrix4d()
        .perspective((float) Math.toRadians(90), 2.0f, 0.1f, 100.0f)
        .lookAt(0, 0, 10,
                0, 0,  0, 
                0, 1,  0);
        Vector3d corner = new Vector3d();
        m.frustumCorner(Matrix4dc.CORNER_NXNYNZ, corner); // left, bottom, near
        TestUtil.assertVector3dEquals(new Vector3d(-0.2f, -0.1f, 10 - 0.1f), corner, 1E-5f);
        m.frustumCorner(Matrix4dc.CORNER_PXNYNZ, corner); // right, bottom, near
        TestUtil.assertVector3dEquals(new Vector3d(0.2f, -0.1f, 10 - 0.1f), corner, 1E-5f);
        m.frustumCorner(Matrix4dc.CORNER_PXNYPZ, corner); // right, bottom, far
        TestUtil.assertVector3dEquals(new Vector3d(200.0f, -100, 10 - 100f), corner, 1E-3f);
    }

    public static void testFrustumCornerRotate() {
        Matrix4d m = new Matrix4d()
        .perspective((float) Math.toRadians(90), 1.0f, 0.1f, 100.0f)
        .lookAt(10, 0, 0, 
                 0, 0, 0, 
                 0, 1, 0);
        Vector3d corner = new Vector3d();
        m.frustumCorner(Matrix4dc.CORNER_NXNYNZ, corner); // left, bottom, near
        TestUtil.assertVector3dEquals(new Vector3d(10 - 0.1f, -0.1f, 0.1f), corner, 1E-6f);
        m.frustumCorner(Matrix4dc.CORNER_PXNYNZ, corner); // right, bottom, near
        TestUtil.assertVector3dEquals(new Vector3d(10 - 0.1f, -0.1f, -0.1f), corner, 1E-6f);
        m.frustumCorner(Matrix4dc.CORNER_PXNYPZ, corner); // right, bottom, far
        TestUtil.assertVector3dEquals(new Vector3d(-100.0f + 10, -100, -100f), corner, 1E-3f);
    }

    public static void testPerspectiveOrigin() {
        Matrix4d m = new Matrix4d()
        // test symmetric frustum with some modelview translation and rotation
        .perspective((float) Math.toRadians(90), 1.0f, 0.1f, 100.0f)
        .lookAt(6, 0, 1, 
                0, 0, 0, 
                0, 1, 0);
        Vector3d origin = new Vector3d();
        m.perspectiveOrigin(origin);
        TestUtil.assertVector3dEquals(new Vector3d(6, 0, 1), origin, 1E-5f);

        // test symmetric frustum with some modelview translation and rotation
        m = new Matrix4d()
        .perspective((float) Math.toRadians(90), 1.0f, 0.1f, 100.0f)
        .lookAt(-5, 2, 1, 
                0, 1, 0, 
                0, 1, 0);
        m.perspectiveOrigin(origin);
        TestUtil.assertVector3dEquals(new Vector3d(-5, 2, 1), origin, 1E-5f);

        // test asymmetric frustum
        m = new Matrix4d()
        .frustum(-0.1f, 0.5f, -0.1f, 0.1f, 0.1f, 100.0f)
        .lookAt(-5, 2, 1, 
                0, 1, 0, 
                0, 1, 0);
        m.perspectiveOrigin(origin);
        TestUtil.assertVector3dEquals(new Vector3d(-5, 2, 1), origin, 1E-5f);
    }

    public static void testPerspectiveFov() {
        Matrix4d m = new Matrix4d()
        .perspective((float) Math.toRadians(45), 1.0f, 0.1f, 100.0f);
        double fov = m.perspectiveFov();
        assertEquals(Math.toRadians(45), fov, 1E-5);

        m = new Matrix4d()
        .perspective((float) Math.toRadians(90), 1.0f, 0.1f, 100.0f)
        .lookAt(6, 0, 1, 
                0, 0, 0, 
                0, 1, 0);
        fov = m.perspectiveFov();
        assertEquals(Math.toRadians(90), fov, 1E-5);
    }

    public static void testNormal() {
        Matrix4d r = new Matrix4d().rotateY((float) Math.PI / 2);
        Matrix4d s = new Matrix4d(r).scale(0.2f);
        Matrix4d n = new Matrix4d();
        s.normal(n);
        n.normalize3x3();
        TestUtil.assertMatrix4dEquals(r, n, 1E-8f);
    }

    public static void testInvertAffine() {
        Matrix4d invm = new Matrix4d();
        Matrix4d m = new Matrix4d();
        m.rotateX(1.2f).rotateY(0.2f).rotateZ(0.1f).translate(1, 2, 3).invertAffine(invm);
        Vector3d orig = new Vector3d(4, -6, 8);
        Vector3d v = new Vector3d();
        Vector3d w = new Vector3d();
        m.transformPosition(orig, v);
        invm.transformPosition(v, w);
        TestUtil.assertVector3dEquals(orig, w, 1E-6f);
        invm.invertAffine();
        TestUtil.assertMatrix4dEquals(m, invm, 1E-6f);
    }

    public static void testInvert() {
        Matrix4d invm = new Matrix4d();
        Matrix4d m = new Matrix4d();
        m.perspective(0.1123f, 0.5f, 0.1f, 100.0f).rotateX(1.2f).rotateY(0.2f).rotateZ(0.1f).translate(1, 2, 3).invert(invm);
        Vector4d orig = new Vector4d(4, -6, 8, 1);
        Vector4d v = new Vector4d();
        Vector4d w = new Vector4d();
        m.transform(orig, v);
        invm.transform(v, w);
        TestUtil.assertVector4dEquals(orig, w, 1E-4f);
        invm.invert();
        TestUtil.assertMatrix4dEquals(m, invm, 1E-3f);
    }

    public static void testRotateXYZ() {
        Matrix4d m = new Matrix4d().rotateX(0.12f).rotateY(0.0623f).rotateZ(0.95f);
        Matrix4d n = new Matrix4d().rotateXYZ(0.12f, 0.0623f, 0.95f);
        TestUtil.assertMatrix4dEquals(m, n, 1E-6f);
    }

    public static void testRotateZYX() {
        Matrix4d m = new Matrix4d().rotateZ(1.12f).rotateY(0.0623f).rotateX(0.95f);
        Matrix4d n = new Matrix4d().rotateZYX(1.12f, 0.0623f, 0.95f);
        TestUtil.assertMatrix4dEquals(m, n, 1E-6f);
    }

    public static void testRotateYXZ() {
        Matrix4d m = new Matrix4d().rotateY(1.12f).rotateX(0.0623f).rotateZ(0.95f);
        Matrix4d n = new Matrix4d().rotateYXZ(1.12f, 0.0623f, 0.95f);
        TestUtil.assertMatrix4dEquals(m, n, 1E-6f);
    }

    public static void testRotateAffineXYZ() {
        Matrix4d m = new Matrix4d().rotateX(0.12f).rotateY(0.0623f).rotateZ(0.95f);
        Matrix4d n = new Matrix4d().rotateAffineXYZ(0.12f, 0.0623f, 0.95f);
        TestUtil.assertMatrix4dEquals(m, n, 1E-6f);
    }

    public static void testRotateAffineZYX() {
        Matrix4d m = new Matrix4d().rotateZ(1.12f).rotateY(0.0623f).rotateX(0.95f);
        Matrix4d n = new Matrix4d().rotateAffineZYX(1.12f, 0.0623f, 0.95f);
        TestUtil.assertMatrix4dEquals(m, n, 1E-6f);
    }

    public static void testRotateAffineYXZ() {
        Matrix4d m = new Matrix4d().rotateY(1.12f).rotateX(0.0623f).rotateZ(0.95f);
        Matrix4d n = new Matrix4d().rotateAffineYXZ(1.12f, 0.0623f, 0.95f);
        TestUtil.assertMatrix4dEquals(m, n, 1E-6f);
    }

    public static void testRotationXYZ() {
        Matrix4d m = new Matrix4d().rotationX(0.32f).rotateY(0.5623f).rotateZ(0.95f);
        Matrix4d n = new Matrix4d().rotationXYZ(0.32f, 0.5623f, 0.95f);
        TestUtil.assertMatrix4dEquals(m, n, 1E-6f);
    }

    public static void testRotationZYX() {
        Matrix4d m = new Matrix4d().rotationZ(0.12f).rotateY(0.0623f).rotateX(0.95f);
        Matrix4d n = new Matrix4d().rotationZYX(0.12f, 0.0623f, 0.95f);
        TestUtil.assertMatrix4dEquals(m, n, 1E-6f);
    }

    public static void testRotationYXZ() {
        Matrix4d m = new Matrix4d().rotationY(0.12f).rotateX(0.0623f).rotateZ(0.95f);
        Matrix4d n = new Matrix4d().rotationYXZ(0.12f, 0.0623f, 0.95f);
        TestUtil.assertMatrix4dEquals(m, n, 1E-6f);
    }

    public static void testOrthoCrop() {
        Matrix4d lightView = new Matrix4d()
                .lookAt(0, 5, 0,
                        0, 0, 0,
                       -1, 0, 0);
        Matrix4d crop = new Matrix4d();
        Matrix4d fin = new Matrix4d();
        new Matrix4d().ortho2D(-1, 1, -1, 1).invertAffine().orthoCrop(lightView, crop).mulOrthoAffine(lightView, fin);
        Vector3d p = new Vector3d();
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
        Matrix4d lightView = new Matrix4d()
                .lookAt(0, 5, 0,
                        0, 0, 0,
                        0, 0, -1);
        Matrix4d crop = new Matrix4d();
        Matrix4d fin = new Matrix4d();
        new Matrix4d().perspective((float) Math.toRadians(90), 1.0f, 5, 10).invertPerspective().orthoCrop(lightView, crop).mulOrthoAffine(lightView, fin);
        Vector3d p = new Vector3d();
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
        Vector3d v = new Vector3d(1, 1, 0).normalize();
        Matrix4d m1 = new Matrix4d().rotateZ(v.angle(new Vector3d(1, 0, 0)), new Matrix4d());
        Matrix4d m2 = new Matrix4d().rotateTowardsXY(v.x, v.y, new Matrix4d());
        TestUtil.assertMatrix4dEquals(m1, m2, 1E-13);
        Vector3d t = m1.transformDirection(new Vector3d(0, 1, 0));
        TestUtil.assertVector3dEquals(new Vector3d(-1, 1, 0).normalize(), t, 1E-6f);
    }

    public static void testTestPoint() {
        Matrix4d m = new Matrix4d().perspective((float)Math.toRadians(90), 1.0f, 0.1f, 10.0f).lookAt(0, 0, 10, 0, 0, 0, 0, 1, 0).scale(2);
        assertTrue(m.testPoint(0, 0, 0));
        assertTrue(m.testPoint(9.999f*0.5f, 0, 0));
        assertFalse(m.testPoint(10.001f*0.5f, 0, 0));
    }

    public static void testTestAab() {
        Matrix4d m = new Matrix4d().perspective((float)Math.toRadians(90), 1.0f, 0.1f, 10.0f).lookAt(0, 0, 10, 0, 0, 0, 0, 1, 0).scale(2);
        assertTrue(m.testAab(-1, -1, -1, 1, 1, 1));
        assertTrue(m.testAab(9.999f*0.5f, 0, 0, 10, 1, 1));
        assertFalse(m.testAab(10.001f*0.5f, 0, 0, 10, 1, 1));
    }

    public static void testTransformTranspose() {
        Matrix4d m = new Matrix4d(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16);
        TestUtil.assertVector4dEquals(
                m.transformTranspose(new Vector4d(4, 5, 6, 7)), 
                m.transpose(new Matrix4d()).transform(new Vector4d(4, 5, 6, 7)), 
                1E-6f);
    }

    public static void testGet() {
        Matrix4d m = new Matrix4d(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16);
        for (int c = 0; c < 4; c++)
            for (int r = 0; r < 4; r++)
                assertEquals(c*4+r+1, m.get(c, r), 0);
    }

    public static void testSet() {
        TestUtil.assertMatrix4dEquals(new Matrix4d().zero().set(0, 0, 3), new Matrix4d(3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0), 0);
        TestUtil.assertMatrix4dEquals(new Matrix4d().zero().set(0, 1, 3), new Matrix4d(0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0), 0);
        TestUtil.assertMatrix4dEquals(new Matrix4d().zero().set(0, 2, 3), new Matrix4d(0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0), 0);
        TestUtil.assertMatrix4dEquals(new Matrix4d().zero().set(0, 3, 3), new Matrix4d(0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0), 0);
        TestUtil.assertMatrix4dEquals(new Matrix4d().zero().set(1, 0, 3), new Matrix4d(0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0), 0);
        TestUtil.assertMatrix4dEquals(new Matrix4d().zero().set(1, 1, 3), new Matrix4d(0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0), 0);
        TestUtil.assertMatrix4dEquals(new Matrix4d().zero().set(1, 2, 3), new Matrix4d(0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0), 0);
        TestUtil.assertMatrix4dEquals(new Matrix4d().zero().set(1, 3, 3), new Matrix4d(0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0), 0);
        TestUtil.assertMatrix4dEquals(new Matrix4d().zero().set(2, 0, 3), new Matrix4d(0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0), 0);
        TestUtil.assertMatrix4dEquals(new Matrix4d().zero().set(2, 1, 3), new Matrix4d(0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0), 0);
        TestUtil.assertMatrix4dEquals(new Matrix4d().zero().set(2, 2, 3), new Matrix4d(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0), 0);
        TestUtil.assertMatrix4dEquals(new Matrix4d().zero().set(2, 3, 3), new Matrix4d(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0), 0);
        TestUtil.assertMatrix4dEquals(new Matrix4d().zero().set(3, 0, 3), new Matrix4d(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0), 0);
        TestUtil.assertMatrix4dEquals(new Matrix4d().zero().set(3, 1, 3), new Matrix4d(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0), 0);
        TestUtil.assertMatrix4dEquals(new Matrix4d().zero().set(3, 2, 3), new Matrix4d(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0), 0);
        TestUtil.assertMatrix4dEquals(new Matrix4d().zero().set(3, 3, 3), new Matrix4d(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3), 0);
    }

    /**
     * https://github.com/JOML-CI/JOML/issues/266
     */
    public static void testMulPerspectiveAffine() {
        Matrix4d t = new Matrix4d().lookAt(2, 3, 4, 5, 6, 7, 8, 9, 11);
        Matrix4d p = new Matrix4d().perspective(60.0f * (float)Math.PI / 180.0f, 4.0f/3.0f, 0.1f, 1000.0f);
        Matrix4d result1 = t.invertAffine(new Matrix4d());
        Matrix4d result2 = t.invertAffine(new Matrix4d());
        p.mul(result1, result1);
        p.mul0(result2, result2);
        TestUtil.assertMatrix4dEquals(result1, result2, 0.0f);
    }

}
