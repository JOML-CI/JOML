package org.joml.test;

import junit.framework.TestCase;

import org.joml.*;
import org.joml.api.matrix.Matrix4fc;
import org.joml.api.quaternion.Quaternionfc;
import org.joml.api.vector.Vector3fc;

/**
 * Test class for {@link Quaternionf}.
 * @author Sebastian Fellner
 */
public class QuaternionTest extends TestCase {
    
    public static void testMulQuaternionQuaternionQuaternion() {
        // Multiplication with the identity quaternion should change nothing
        Quaternionf testQuat = new Quaternionf(1f, 23.3f, -7.57f, 2.1f);
        Quaternionfc identityQuat = new Quaternionf().identity();
        Quaternionf resultQuat = new Quaternionf();
        
        testQuat.mul(identityQuat, resultQuat);
        assertTrue(TestUtil.quatEqual(testQuat, resultQuat, TestUtil.STANDARD_AROUND_ZERO_PRECISION_FLOAT));
        
        identityQuat.mul(testQuat, resultQuat);
        assertTrue(TestUtil.quatEqual(testQuat, resultQuat, TestUtil.STANDARD_AROUND_ZERO_PRECISION_FLOAT));

        // Multiplication with conjugate should give (0, 0, 0, dot(this, this))
        Quaternionf conjugate = new Quaternionf();
        testQuat.conjugate(conjugate);
        testQuat.mul(conjugate, resultQuat);
        
        Quaternionf wantedResultQuat = new Quaternionf(0, 0, 0, testQuat.dot(testQuat));
        assertTrue(TestUtil.quatEqual(resultQuat, wantedResultQuat, TestUtil.MANY_OPS_AROUND_ZERO_PRECISION_FLOAT));
    }

    public static void testSlerp() {
        Quaternionfc q1 = new Quaternionf().rotateY(0.0f);
        Quaternionfc q2 = new Quaternionf().rotateY((float) java.lang.Math.PI * 0.5f);
        Quaternionf q = new Quaternionf();
        Vector3f v = new Vector3f(1.0f, 0.0f, 0.0f);
        q1.slerp(q2, 0.5f, q);
        q.transform(v);
        TestUtil.assertVector3fEquals(new Vector3f(1.0f, 0.0f, -1.0f).normalize(), v, 1E-5f);
    }

    public static void testNlerp() {
        Quaternionfc q1 = new Quaternionf().rotateY(0.0f);
        Quaternionfc q2 = new Quaternionf().rotateY((float) java.lang.Math.PI * 0.5f);
        Quaternionf q = new Quaternionf();
        Vector3f v = new Vector3f(1.0f, 0.0f, 0.0f);
        q1.nlerp(q2, 0.5f, q);
        q.transform(v);
        System.out.println(new Vector3f(1.0f, 0.0f, -1.0f).normalize());
        System.out.println(v);
        System.out.println(1E-5f);
        TestUtil.assertVector3fEquals(new Vector3f(1.0f, 0.0f, -1.0f).normalize(), v, 1E-5f);
    }

    public static void testNlerpRecursive() {
        Quaternionfc q1 = new Quaternionf().rotateY(0.0f);
        Quaternionfc q2 = new Quaternionf().rotateY((float) java.lang.Math.PI * 0.5f);
        Quaternionf q = new Quaternionf();
        Vector3f v = new Vector3f(1.0f, 0.0f, 0.0f);
        q1.nlerpIterative(q2, 0.5f, 1E-5f, q);
        q.transform(v);
        TestUtil.assertVector3fEquals(new Vector3f(1.0f, 0.0f, -1.0f).normalize(), v, 1E-5f);
    }

    public static void testRotationXYZ() {
        Quaternionfc v = new Quaternionf().rotationXYZ(0.12f, 0.521f, 0.951f);
        Matrix4fc m = new Matrix4f().rotateXYZ(0.12f, 0.521f, 0.951f);
        Matrix4fc n = new Matrix4f().set(v);
        TestUtil.assertMatrix4fEquals(m, n, 1E-6f);
    }

    public static void testRotationZYX() {
        Quaternionfc v = new Quaternionf().rotationZYX(0.12f, 0.521f, 0.951f);
        Matrix4fc m = new Matrix4f().rotateZYX(0.12f, 0.521f, 0.951f);
        Matrix4fc n = new Matrix4f().set(v);
        TestUtil.assertMatrix4fEquals(m, n, 1E-6f);
    }

    public static void testRotationYXZ() {
        Quaternionfc v = new Quaternionf().rotationYXZ(0.12f, 0.521f, 0.951f);
        Matrix4fc m = new Matrix4f().rotationYXZ(0.12f, 0.521f, 0.951f);
        Matrix4fc n = new Matrix4f().set(v);
        TestUtil.assertMatrix4fEquals(m, n, 1E-6f);
    }

    public static void testRotateXYZ() {
        Quaternionfc v = new Quaternionf().rotateXYZ(0.12f, 0.521f, 0.951f);
        Matrix4fc m = new Matrix4f().rotateXYZ(0.12f, 0.521f, 0.951f);
        Matrix4fc n = new Matrix4f().set(v);
        TestUtil.assertMatrix4fEquals(m, n, 1E-6f);
    }

    public static void testRotateZYX() {
        Quaternionfc v = new Quaternionf().rotateZYX(0.12f, 0.521f, 0.951f);
        Matrix4fc m = new Matrix4f().rotateZYX(0.12f, 0.521f, 0.951f);
        Matrix4fc n = new Matrix4f().set(v);
        TestUtil.assertMatrix4fEquals(m, n, 1E-6f);
    }

    public static void testRotateYXZ() {
        Quaternionfc v = new Quaternionf().rotateYXZ(0.12f, 0.521f, 0.951f);
        Matrix4fc m = new Matrix4f().rotateYXZ(0.12f, 0.521f, 0.951f);
        Matrix4fc n = new Matrix4f().set(v);
        TestUtil.assertMatrix4fEquals(m, n, 1E-6f);
    }

    public static void testRotateTo() {
        Vector3fc v1 = new Vector3f(1, 2, 3).normalize();
        Vector3fc v2 = new Vector3f(5, -2, -1).normalize();
        Quaternionfc rotation = new Quaternionf().rotateTo(v1, v2);
        rotation.transform(v1);
        TestUtil.assertVector3fEquals(v1, v2, 1E-6f);
    }

    public static void testRotateToReturnsDestination() {
        Quaternionf rotation = new Quaternionf();
        Quaternionf destination = new Quaternionf();
        Quaternionfc result = rotation.rotateTo(0, 1, 0, 0, 1, 0, destination);
        assertSame(destination, result);
    }

}
