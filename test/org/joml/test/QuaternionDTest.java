package org.joml.test;

import junit.framework.TestCase;

import org.joml.*;
import org.joml.api.matrix.Matrix4fc;
import org.joml.api.quaternion.Quaterniondc;

/**
 * Test class for {@link Quaterniond}.
 * @author Sebastian Fellner
 */
public class QuaternionDTest extends TestCase {
    
    public static void testMulQuaternionDQuaternionDQuaternionD() {
        // Multiplication with the identity quaternion should change nothing
        Quaterniond testQuat = new Quaterniond(1, 23.3, -7.57, 2.1);
        Quaterniondc identityQuat = new Quaterniond().identity();
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
        Quaterniondc v = new Quaterniond().rotationXYZ(0.12f, 0.521f, 0.951f);
        Matrix4fc m = new Matrix4f().rotateXYZ(0.12f, 0.521f, 0.951f);
        Matrix4fc n = new Matrix4f().set(v);
        TestUtil.assertMatrix4fEquals(m, n, 1E-6f);
    }

    public static void testRotationZYX() {
        Quaterniondc v = new Quaterniond().rotationZYX(0.12f, 0.521f, 0.951f);
        Matrix4fc m = new Matrix4f().rotateZYX(0.12f, 0.521f, 0.951f);
        Matrix4fc n = new Matrix4f().set(v);
        TestUtil.assertMatrix4fEquals(m, n, 1E-6f);
    }

    public static void testRotationYXZ() {
        Quaterniondc v = new Quaterniond().rotationYXZ(0.12f, 0.521f, 0.951f);
        Matrix4fc m = new Matrix4f().rotationYXZ(0.12f, 0.521f, 0.951f);
        Matrix4fc n = new Matrix4f().set(v);
        TestUtil.assertMatrix4fEquals(m, n, 1E-6f);
    }

    public static void testRotateXYZ() {
        Quaterniondc v = new Quaterniond().rotateXYZ(0.12f, 0.521f, 0.951f);
        Matrix4fc m = new Matrix4f().rotateXYZ(0.12f, 0.521f, 0.951f);
        Matrix4fc n = new Matrix4f().set(v);
        TestUtil.assertMatrix4fEquals(m, n, 1E-6f);
    }

    public static void testRotateZYX() {
        Quaterniondc v = new Quaterniond().rotateZYX(0.12f, 0.521f, 0.951f);
        Matrix4fc m = new Matrix4f().rotateZYX(0.12f, 0.521f, 0.951f);
        Matrix4fc n = new Matrix4f().set(v);
        TestUtil.assertMatrix4fEquals(m, n, 1E-6f);
    }

    public static void testRotateYXZ() {
        Quaterniondc v = new Quaterniond().rotateYXZ(0.12f, 0.521f, 0.951f);
        Matrix4fc m = new Matrix4f().rotateYXZ(0.12f, 0.521f, 0.951f);
        Matrix4fc n = new Matrix4f().set(v);
        TestUtil.assertMatrix4fEquals(m, n, 1E-6f);
    }

    public static void testRotateToReturnsDestination() {
        Quaterniondc rotation = new Quaterniond();
        Quaterniond destination = new Quaterniond();
        Quaterniondc result = rotation.rotateTo(0, 1, 0, 0, 1, 0, destination);
        assertSame(destination, result);
    }

    public static void testFromAxisAngle() {
        Vector3d axis = new Vector3d(1.0, 0.0, 0.0);
        double angleDeg = 45.0;
        double angleRad = java.lang.Math.toRadians(angleDeg);
        Quaterniondc fromRad1 = new Quaterniond().fromAxisAngleRad(axis, angleRad);
        Quaterniondc fromRad2 = new Quaterniond().fromAxisAngleRad(axis.x(), axis.y(), axis.z(), angleRad);
        Quaterniondc fromDeg = new Quaterniond().fromAxisAngleDeg(axis, angleDeg);
        assertEquals(fromRad1, fromRad2);
        assertEquals(fromRad2, fromDeg);
    }
}
