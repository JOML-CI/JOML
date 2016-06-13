package org.joml.test;

import junit.framework.TestCase;

import org.joml.Matrix4x3f;
import org.joml.Vector3f;

/**
 * Tests for the {@link Matrix4x3f} class.
 * 
 * @author Kai Burjack
 */
public class Matrix4x3fTest extends TestCase {

    public static void testLookAt() {
        Matrix4x3f m1, m2;
        m1 = new Matrix4x3f().lookAt(0, 2, 3, 0, 0, 0, 0, 1, 0);
        m2 = new Matrix4x3f().translate(0, 0, -(float) Math.sqrt(2 * 2 + 3 * 3)).rotateX(
                (float) Math.atan2(2, 3));
        TestUtil.assertMatrix4x3fEquals(m1, m2, 1E-5f);
        m1 = new Matrix4x3f().lookAt(3, 2, 0, 0, 0, 0, 0, 1, 0);
        m2 = new Matrix4x3f().translate(0, 0, -(float) Math.sqrt(2 * 2 + 3 * 3))
                .rotateX((float) Math.atan2(2, 3)).rotateY((float) Math.toRadians(-90));
        TestUtil.assertMatrix4x3fEquals(m1, m2, 1E-4f);
    }

    public static void testPositiveXRotateY() {
        Vector3f dir = new Vector3f();
        Matrix4x3f m = new Matrix4x3f()
                .rotateY((float) Math.toRadians(90));
        m.positiveX(dir);
        TestUtil.assertVector3fEquals(new Vector3f(0, 0, 1), dir, 1E-7f);
    }

    public static void testPositiveYRotateX() {
        Vector3f dir = new Vector3f();
        Matrix4x3f m = new Matrix4x3f()
                .rotateX((float) Math.toRadians(90));
        m.positiveY(dir);
        TestUtil.assertVector3fEquals(new Vector3f(0, 0, -1), dir, 1E-7f);
    }

    public static void testPositiveZRotateX() {
        Vector3f dir = new Vector3f();
        Matrix4x3f m = new Matrix4x3f()
                .rotateX((float) Math.toRadians(90));
        m.positiveZ(dir);
        TestUtil.assertVector3fEquals(new Vector3f(0, 1, 0), dir, 1E-7f);
    }

    public static void testPositiveXRotateXY() {
        Vector3f dir = new Vector3f();
        Matrix4x3f m = new Matrix4x3f()
                .rotateY((float) Math.toRadians(90)).rotateX((float) Math.toRadians(45));
        m.positiveX(dir);
        TestUtil.assertVector3fEquals(new Vector3f(0, 1, 1).normalize(), dir, 1E-7f);
    }

    public static void testPositiveXYZLookAt() {
        Vector3f dir = new Vector3f();
        Matrix4x3f m = new Matrix4x3f()
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
        Matrix4x3f m = new Matrix4x3f().rotateXYZ(0.12f, 1.25f, -2.56f);
        Matrix4x3f inv = new Matrix4x3f(m).invert();
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

    public static void testNormal() {
        Matrix4x3f r = new Matrix4x3f().rotateY((float) Math.PI / 2);
        Matrix4x3f s = new Matrix4x3f(r).scale(0.2f);
        Matrix4x3f n = new Matrix4x3f();
        s.normal(n);
        n.normalize3x3();
        TestUtil.assertMatrix4x3fEquals(r, n, 1E-8f);
    }

    public static void testInvert() {
        Matrix4x3f invm = new Matrix4x3f();
        Matrix4x3f m = new Matrix4x3f();
        m.rotateX(1.2f).rotateY(0.2f).rotateZ(0.1f).translate(1, 2, 3).invert(invm);
        Vector3f orig = new Vector3f(4, -6, 8);
        Vector3f v = new Vector3f();
        Vector3f w = new Vector3f();
        m.transformPosition(orig, v);
        invm.transformPosition(v, w);
        TestUtil.assertVector3fEquals(orig, w, 1E-6f);
        invm.invert();
        TestUtil.assertMatrix4x3fEquals(m, invm, 1E-6f);
    }

    public static void testRotateXYZ() {
        Matrix4x3f m = new Matrix4x3f().rotateX(0.12f).rotateY(0.0623f).rotateZ(0.95f);
        Matrix4x3f n = new Matrix4x3f().rotateXYZ(0.12f, 0.0623f, 0.95f);
        TestUtil.assertMatrix4x3fEquals(m, n, 1E-6f);
    }

    public static void testRotateZYX() {
        Matrix4x3f m = new Matrix4x3f().rotateZ(1.12f).rotateY(0.0623f).rotateX(0.95f);
        Matrix4x3f n = new Matrix4x3f().rotateZYX(1.12f, 0.0623f, 0.95f);
        TestUtil.assertMatrix4x3fEquals(m, n, 1E-6f);
    }

    public static void testRotateYXZ() {
        Matrix4x3f m = new Matrix4x3f().rotateY(1.12f).rotateX(0.0623f).rotateZ(0.95f);
        Matrix4x3f n = new Matrix4x3f().rotateYXZ(1.12f, 0.0623f, 0.95f);
        TestUtil.assertMatrix4x3fEquals(m, n, 1E-6f);
    }

    public static void testRotateAffineXYZ() {
        Matrix4x3f m = new Matrix4x3f().rotateX(0.12f).rotateY(0.0623f).rotateZ(0.95f);
        Matrix4x3f n = new Matrix4x3f().rotateXYZ(0.12f, 0.0623f, 0.95f);
        TestUtil.assertMatrix4x3fEquals(m, n, 1E-6f);
    }

    public static void testRotateAffineZYX() {
        Matrix4x3f m = new Matrix4x3f().rotateZ(1.12f).rotateY(0.0623f).rotateX(0.95f);
        Matrix4x3f n = new Matrix4x3f().rotateZYX(1.12f, 0.0623f, 0.95f);
        TestUtil.assertMatrix4x3fEquals(m, n, 1E-6f);
    }

    public static void testRotateAffineYXZ() {
        Matrix4x3f m = new Matrix4x3f().rotateY(1.12f).rotateX(0.0623f).rotateZ(0.95f);
        Matrix4x3f n = new Matrix4x3f().rotateYXZ(1.12f, 0.0623f, 0.95f);
        TestUtil.assertMatrix4x3fEquals(m, n, 1E-6f);
    }

    public static void testRotationXYZ() {
        Matrix4x3f m = new Matrix4x3f().rotationX(0.32f).rotateY(0.5623f).rotateZ(0.95f);
        Matrix4x3f n = new Matrix4x3f().rotationXYZ(0.32f, 0.5623f, 0.95f);
        TestUtil.assertMatrix4x3fEquals(m, n, 1E-6f);
    }

    public static void testRotationZYX() {
        Matrix4x3f m = new Matrix4x3f().rotationZ(0.12f).rotateY(0.0623f).rotateX(0.95f);
        Matrix4x3f n = new Matrix4x3f().rotationZYX(0.12f, 0.0623f, 0.95f);
        TestUtil.assertMatrix4x3fEquals(m, n, 1E-6f);
    }

    public static void testRotationYXZ() {
        Matrix4x3f m = new Matrix4x3f().rotationY(0.12f).rotateX(0.0623f).rotateZ(0.95f);
        Matrix4x3f n = new Matrix4x3f().rotationYXZ(0.12f, 0.0623f, 0.95f);
        TestUtil.assertMatrix4x3fEquals(m, n, 1E-6f);
    }

}
