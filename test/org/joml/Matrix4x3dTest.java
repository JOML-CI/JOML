package org.joml;

import junit.framework.TestCase;

import org.joml.Matrix4x3d;
import org.joml.Matrix4x3d;
import org.joml.Vector3d;
import org.joml.Math;

/**
 * Tests for the {@link Matrix4x3d} class.
 * 
 * @author Kai Burjack
 */
public class Matrix4x3dTest extends TestCase {

    public static void testLookAt() {
        Matrix4x3d m1, m2;
        m1 = new Matrix4x3d().lookAt(0, 2, 3, 0, 0, 0, 0, 1, 0);
        m2 = new Matrix4x3d().translate(0, 0, -(float) Math.sqrt(2 * 2 + 3 * 3)).rotateX(
                (float) Math.atan2(2, 3));
        TestUtil.assertMatrix4x3dEquals(m1, m2, 1E-5f);
        m1 = new Matrix4x3d().lookAt(3, 2, 0, 0, 0, 0, 0, 1, 0);
        m2 = new Matrix4x3d().translate(0, 0, -(float) Math.sqrt(2 * 2 + 3 * 3))
                .rotateX((float) Math.atan2(2, 3)).rotateY((float) Math.toRadians(-90));
        TestUtil.assertMatrix4x3dEquals(m1, m2, 1E-4f);
    }

    public static void testPositiveXRotateY() {
        Vector3d dir = new Vector3d();
        Matrix4x3d m = new Matrix4x3d()
                .rotateY((float) Math.toRadians(90));
        m.positiveX(dir);
        TestUtil.assertVector3dEquals(new Vector3d(0, 0, 1), dir, 1E-7f);
    }

    public static void testPositiveYRotateX() {
        Vector3d dir = new Vector3d();
        Matrix4x3d m = new Matrix4x3d()
                .rotateX((float) Math.toRadians(90));
        m.positiveY(dir);
        TestUtil.assertVector3dEquals(new Vector3d(0, 0, -1), dir, 1E-7f);
    }

    public static void testPositiveZRotateX() {
        Vector3d dir = new Vector3d();
        Matrix4x3d m = new Matrix4x3d()
                .rotateX((float) Math.toRadians(90));
        m.positiveZ(dir);
        TestUtil.assertVector3dEquals(new Vector3d(0, 1, 0), dir, 1E-7f);
    }

    public static void testPositiveXRotateXY() {
        Vector3d dir = new Vector3d();
        Matrix4x3d m = new Matrix4x3d()
                .rotateY((float) Math.toRadians(90)).rotateX((float) Math.toRadians(45));
        m.positiveX(dir);
        TestUtil.assertVector3dEquals(new Vector3d(0, 1, 1).normalize(), dir, 1E-7f);
    }

    public static void testPositiveXYZLookAt() {
        Vector3d dir = new Vector3d();
        Matrix4x3d m = new Matrix4x3d()
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
        Matrix4x3d m = new Matrix4x3d().rotateXYZ(0.12f, 1.25f, -2.56f);
        Matrix4x3d inv = new Matrix4x3d(m).invert();
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

    public static void testNormal() {
        Matrix4x3d r = new Matrix4x3d().rotateY((float) Math.PI / 2);
        Matrix4x3d s = new Matrix4x3d(r).scale(0.2f);
        Matrix4x3d n = new Matrix4x3d();
        s.normal(n);
        n.normalize3x3();
        TestUtil.assertMatrix4x3dEquals(r, n, 1E-8f);
    }

    public static void testInvert() {
        Matrix4x3d invm = new Matrix4x3d();
        Matrix4x3d m = new Matrix4x3d();
        m.rotateX(1.2f).rotateY(0.2f).rotateZ(0.1f).translate(1, 2, 3).invert(invm);
        Vector3d orig = new Vector3d(4, -6, 8);
        Vector3d v = new Vector3d();
        Vector3d w = new Vector3d();
        m.transformPosition(orig, v);
        invm.transformPosition(v, w);
        TestUtil.assertVector3dEquals(orig, w, 1E-6f);
        invm.invert();
        TestUtil.assertMatrix4x3dEquals(m, invm, 1E-6f);
    }

    public static void testRotateXYZ() {
        Matrix4x3d m = new Matrix4x3d().rotateX(0.12f).rotateY(0.0623f).rotateZ(0.95f);
        Matrix4x3d n = new Matrix4x3d().rotateXYZ(0.12f, 0.0623f, 0.95f);
        TestUtil.assertMatrix4x3dEquals(m, n, 1E-6f);
    }

    public static void testRotateZYX() {
        Matrix4x3d m = new Matrix4x3d().rotateZ(1.12f).rotateY(0.0623f).rotateX(0.95f);
        Matrix4x3d n = new Matrix4x3d().rotateZYX(1.12f, 0.0623f, 0.95f);
        TestUtil.assertMatrix4x3dEquals(m, n, 1E-6f);
    }

    public static void testRotateYXZ() {
        Matrix4x3d m = new Matrix4x3d().rotateY(1.12f).rotateX(0.0623f).rotateZ(0.95f);
        Matrix4x3d n = new Matrix4x3d().rotateYXZ(1.12f, 0.0623f, 0.95f);
        TestUtil.assertMatrix4x3dEquals(m, n, 1E-6f);
    }

    public static void testRotateAffineXYZ() {
        Matrix4x3d m = new Matrix4x3d().rotateX(0.12f).rotateY(0.0623f).rotateZ(0.95f);
        Matrix4x3d n = new Matrix4x3d().rotateXYZ(0.12f, 0.0623f, 0.95f);
        TestUtil.assertMatrix4x3dEquals(m, n, 1E-6f);
    }

    public static void testRotateAffineZYX() {
        Matrix4x3d m = new Matrix4x3d().rotateZ(1.12f).rotateY(0.0623f).rotateX(0.95f);
        Matrix4x3d n = new Matrix4x3d().rotateZYX(1.12f, 0.0623f, 0.95f);
        TestUtil.assertMatrix4x3dEquals(m, n, 1E-6f);
    }

    public static void testRotateAffineYXZ() {
        Matrix4x3d m = new Matrix4x3d().rotateY(1.12f).rotateX(0.0623f).rotateZ(0.95f);
        Matrix4x3d n = new Matrix4x3d().rotateYXZ(1.12f, 0.0623f, 0.95f);
        TestUtil.assertMatrix4x3dEquals(m, n, 1E-6f);
    }

    public static void testRotationXYZ() {
        Matrix4x3d m = new Matrix4x3d().rotationX(0.32f).rotateY(0.5623f).rotateZ(0.95f);
        Matrix4x3d n = new Matrix4x3d().rotationXYZ(0.32f, 0.5623f, 0.95f);
        TestUtil.assertMatrix4x3dEquals(m, n, 1E-6f);
    }

    public static void testRotationZYX() {
        Matrix4x3d m = new Matrix4x3d().rotationZ(0.12f).rotateY(0.0623f).rotateX(0.95f);
        Matrix4x3d n = new Matrix4x3d().rotationZYX(0.12f, 0.0623f, 0.95f);
        TestUtil.assertMatrix4x3dEquals(m, n, 1E-6f);
    }

    public static void testRotationYXZ() {
        Matrix4x3d m = new Matrix4x3d().rotationY(0.12f).rotateX(0.0623f).rotateZ(0.95f);
        Matrix4x3d n = new Matrix4x3d().rotationYXZ(0.12f, 0.0623f, 0.95f);
        TestUtil.assertMatrix4x3dEquals(m, n, 1E-6f);
    }

}
