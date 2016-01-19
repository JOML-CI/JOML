package org.joml.test;

import junit.framework.TestCase;

import org.joml.Matrix4f;
import org.joml.Quaterniond;

/**
 * Test class for {@link Quaterniond}.
 * @author Sebastian Fellner
 */
public class QuaternionDTest extends TestCase {
    
    public static void testMulQuaternionDQuaternionDQuaternionD() {
        // Multiplication with the identity quaternion should change nothing
        Quaterniond testQuat = new Quaterniond(1, 23.3, -7.57, 2.1);
        Quaterniond identityQuat = new Quaterniond().identity();
        Quaterniond resultQuat = new Quaterniond();
        
        testQuat.mul(identityQuat, resultQuat);
        assertTrue(TestUtil.quatEqual(testQuat, resultQuat, TestUtil.STANDARD_AROUND_ZERO_PRECISION_DOUBLE));
        
        identityQuat.mul(testQuat, resultQuat);
        assertTrue(TestUtil.quatEqual(testQuat, resultQuat, TestUtil.STANDARD_AROUND_ZERO_PRECISION_DOUBLE));
        
        // Multiplication with conjugate should give (0, 0, 0, dot(this, this))
        Quaterniond conjugate = new Quaterniond();
        testQuat.conjugate(conjugate);
        testQuat.mul(conjugate, resultQuat);
        
        Quaterniond wantedResultQuat = new Quaterniond(0, 0, 0, testQuat.dot(testQuat));
        assertTrue(TestUtil.quatEqual(resultQuat, wantedResultQuat, TestUtil.MANY_OPS_AROUND_ZERO_PRECISION_DOUBLE));
    }

    public static void testRotationXYZ() {
        Quaterniond v = new Quaterniond().rotationXYZ(0.12f, 0.521f, 0.951f);
        Matrix4f m = new Matrix4f().rotateXYZ(0.12f, 0.521f, 0.951f);
        Matrix4f n = new Matrix4f().set(v);
        TestUtil.assertMatrix4fEquals(m, n, 1E-4f);
    }

    public static void testRotationZYX() {
        Quaterniond v = new Quaterniond().rotationZYX(0.12f, 0.521f, 0.951f);
        Matrix4f m = new Matrix4f().rotateZYX(0.12f, 0.521f, 0.951f);
        Matrix4f n = new Matrix4f().set(v);
        TestUtil.assertMatrix4fEquals(m, n, 1E-4f);
    }

    public static void testRotationYXZ() {
        Quaterniond v = new Quaterniond().rotationYXZ(0.12f, 0.521f, 0.951f);
        Matrix4f m = new Matrix4f().rotationYXZ(0.12f, 0.521f, 0.951f);
        Matrix4f n = new Matrix4f().set(v);
        TestUtil.assertMatrix4fEquals(m, n, 1E-4f);
    }

    public static void testRotateXYZ() {
        Quaterniond v = new Quaterniond().rotateXYZ(0.12f, 0.521f, 0.951f);
        Matrix4f m = new Matrix4f().rotateXYZ(0.12f, 0.521f, 0.951f);
        Matrix4f n = new Matrix4f().set(v);
        TestUtil.assertMatrix4fEquals(m, n, 1E-4f);
    }

    public static void testRotateZYX() {
        Quaterniond v = new Quaterniond().rotateZYX(0.12f, 0.521f, 0.951f);
        Matrix4f m = new Matrix4f().rotateZYX(0.12f, 0.521f, 0.951f);
        Matrix4f n = new Matrix4f().set(v);
        TestUtil.assertMatrix4fEquals(m, n, 1E-4f);
    }

    public static void testRotateYXZ() {
        Quaterniond v = new Quaterniond().rotateYXZ(0.12f, 0.521f, 0.951f);
        Matrix4f m = new Matrix4f().rotateYXZ(0.12f, 0.521f, 0.951f);
        Matrix4f n = new Matrix4f().set(v);
        TestUtil.assertMatrix4fEquals(m, n, 1E-4f);
    }

    public static void testRotateToReturnsDestination() {
        Quaterniond rotation = new Quaterniond();
        Quaterniond destination = new Quaterniond();
        Quaterniond result = rotation.rotateTo(0, 1, 0, 0, 1, 0, destination);
        assertSame(destination, result);
    }

}
