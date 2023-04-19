/*
 * The MIT License
 *
 * Copyright (c) 2015-2023 JOML.
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

import org.joml.*;
import org.joml.Math;
import org.junit.jupiter.api.Test;

import static org.joml.test.TestUtil.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for {@link Quaterniond}.
 * @author Sebastian Fellner
 */
class QuaternionDTest {
    @Test
    void testMulQuaternionDQuaternionDQuaternionD() {
        // Multiplication with the identity quaternion should change nothing
        Quaterniond testQuat = new Quaterniond(1, 23.3, -7.57, 2.1);
        Quaterniond identityQuat = new Quaterniond().identity();
        Quaterniond resultQuat = new Quaterniond();
        
        testQuat.mul(identityQuat, resultQuat);
        assertTrue(quatEqual(testQuat, resultQuat, STANDARD_AROUND_ZERO_PRECISION_DOUBLE));
        
        identityQuat.mul(testQuat, resultQuat);
        assertTrue(quatEqual(testQuat, resultQuat, STANDARD_AROUND_ZERO_PRECISION_DOUBLE));
        
        // Multiplication with conjugate should give (0, 0, 0, dot(this, this))
        Quaterniond conjugate = new Quaterniond();
        testQuat.conjugate(conjugate);
        testQuat.mul(conjugate, resultQuat);
        
        Quaterniond wantedResultQuat = new Quaterniond(0, 0, 0, testQuat.dot(testQuat));
        assertTrue(quatEqual(resultQuat, wantedResultQuat, MANY_OPS_AROUND_ZERO_PRECISION_DOUBLE));
    }

    @Test
    void testRotationXYZ() {
        Quaterniond v = new Quaterniond().rotationXYZ(0.12f, 0.521f, 0.951f);
        Matrix4f m = new Matrix4f().rotateXYZ(0.12f, 0.521f, 0.951f);
        Matrix4f n = new Matrix4f().set(v);
        assertMatrix4fEquals(m, n, 1E-6f);
    }

    @Test
    void testRotationZYX() {
        Quaterniond v = new Quaterniond().rotationZYX(0.12f, 0.521f, 0.951f);
        Matrix4f m = new Matrix4f().rotateZYX(0.12f, 0.521f, 0.951f);
        Matrix4f n = new Matrix4f().set(v);
        assertMatrix4fEquals(m, n, 1E-6f);
    }

    @Test
    void testRotationYXZ() {
        Quaterniond v = new Quaterniond().rotationYXZ(0.12f, 0.521f, 0.951f);
        Matrix4f m = new Matrix4f().rotationYXZ(0.12f, 0.521f, 0.951f);
        Matrix4f n = new Matrix4f().set(v);
        assertMatrix4fEquals(m, n, 1E-6f);
    }

    @Test
    void testRotateXYZ() {
        Quaterniond v = new Quaterniond().rotateXYZ(0.12f, 0.521f, 0.951f);
        Matrix4f m = new Matrix4f().rotateXYZ(0.12f, 0.521f, 0.951f);
        Matrix4f n = new Matrix4f().set(v);
        assertMatrix4fEquals(m, n, 1E-6f);
    }

    @Test
    void testRotateZYX() {
        Quaterniond v = new Quaterniond().rotateZYX(0.12f, 0.521f, 0.951f);
        Matrix4f m = new Matrix4f().rotateZYX(0.12f, 0.521f, 0.951f);
        Matrix4f n = new Matrix4f().set(v);
        assertMatrix4fEquals(m, n, 1E-6f);
    }

    @Test
    void testRotateYXZ() {
        Quaterniond v = new Quaterniond().rotateYXZ(0.12f, 0.521f, 0.951f);
        Matrix4f m = new Matrix4f().rotateYXZ(0.12f, 0.521f, 0.951f);
        Matrix4f n = new Matrix4f().set(v);
        assertMatrix4fEquals(m, n, 1E-6f);
    }

    @Test
    void testRotateToReturnsDestination() {
        Quaterniondc rotation = new Quaterniond();
        Quaterniond destination = new Quaterniond();
        Quaterniondc result = rotation.rotateTo(0, 1, 0, 0, 1, 0, destination);
        assertSame(destination, result);
    }

    @Test
    void testFromAxisAngle() {
        Vector3d axis = new Vector3d(1.0, 0.0, 0.0);
        double angleDeg = 45.0;
        double angleRad = java.lang.Math.toRadians(angleDeg);
        Quaterniondc fromRad1 = new Quaterniond().fromAxisAngleRad(axis, angleRad);
        Quaterniondc fromRad2 = new Quaterniond().fromAxisAngleRad(axis.x(), axis.y(), axis.z(), angleRad);
        Quaterniondc fromDeg = new Quaterniond().fromAxisAngleDeg(axis, angleDeg);
        assertEquals(fromRad1, fromRad2);
        assertEquals(fromRad2, fromDeg);
    }

    @Test
    void testGetEulerAnglesXYZ() {
        Random rnd = new Random(1L);
        int failure = 0;
        int N = 30000;
        for (int i = 0; i < N; i++) {
            double x = (rnd.nextFloat() * 2.0 - 1.0) * Math.PI;
            double y = (rnd.nextFloat() * 2.0 - 1.0) * Math.PI;
            double z = (rnd.nextFloat() * 2.0 - 1.0) * Math.PI;
            Quaterniond p = new Quaterniond().rotateXYZ(x, y, z);
            Vector3d a = p.getEulerAnglesXYZ(new Vector3d());
            Quaterniond q = new Quaterniond().rotateX(a.x).rotateY(a.y).rotateZ(a.z);
            Vector3d v = new Vector3d(rnd.nextFloat()*2-1, rnd.nextFloat()*2-1, rnd.nextFloat()*2-1);
            Vector3d t1 = p.transform(v, new Vector3d());
            Vector3d t2 = q.transform(v, new Vector3d());
            if (!t1.equals(t2, 1E-10f))
                failure++;
        }
        if ((float)failure / N > 0.0001f) // <- allow for a failure rate of 0.01%
            throw new AssertionError();
    }

    @Test
    void testGetEulerAnglesZYX() {
        Random rnd = new Random(1L);
        int failure = 0;
        int N = 30000;
        for (int i = 0; i < N; i++) {
            double x = (rnd.nextFloat() * 2.0 - 1.0) * Math.PI;
            double y = (rnd.nextFloat() * 2.0 - 1.0) * Math.PI;
            double z = (rnd.nextFloat() * 2.0 - 1.0) * Math.PI;
            Quaterniond p = new Quaterniond().rotateZ(z).rotateY(y).rotateX(x);
            Vector3d a = p.getEulerAnglesZYX(new Vector3d());
            Quaterniond q = new Quaterniond().rotateZ(a.z).rotateY(a.y).rotateX(a.x);
            Vector3d v = new Vector3d(rnd.nextFloat()*2-1, rnd.nextFloat()*2-1, rnd.nextFloat()*2-1);
            Vector3d t1 = p.transform(v, new Vector3d());
            Vector3d t2 = q.transform(v, new Vector3d());
            if (!t1.equals(t2, 1E-10f))
                failure++;
        }
        if ((float)failure / N > 0.0001f) // <- allow for a failure rate of 0.01%
            throw new AssertionError();
    }

    @Test
    void testGetEulerAnglesZXY() {
        Random rnd = new Random(1L);
        int failure = 0;
        int N = 30000;
        for (int i = 0; i < N; i++) {
            double x = (rnd.nextFloat() * 2.0 - 1.0) * Math.PI;
            double y = (rnd.nextFloat() * 2.0 - 1.0) * Math.PI;
            double z = (rnd.nextFloat() * 2.0 - 1.0) * Math.PI;
            Quaterniond p = new Quaterniond().rotateZ(z).rotateX(x).rotateY(y);
            Vector3d a = p.getEulerAnglesZXY(new Vector3d());
            Quaterniond q = new Quaterniond().rotateZ(a.z).rotateX(a.x).rotateY(a.y);
            Vector3d v = new Vector3d(rnd.nextFloat()*2-1, rnd.nextFloat()*2-1, rnd.nextFloat()*2-1);
            Vector3d t1 = p.transform(v, new Vector3d());
            Vector3d t2 = q.transform(v, new Vector3d());
            if (!t1.equals(t2, 1E-10f))
                failure++;
        }
        if ((float)failure / N > 0.0001f) // <- allow for a failure rate of 0.01%
            throw new AssertionError();
    }

    @Test
    void testGetEulerAnglesYXZ() {
        Random rnd = new Random(1L);
        int failure = 0;
        int N = 30000;
        for (int i = 0; i < N; i++) {
            double x = (rnd.nextFloat() * 2.0 - 1.0) * Math.PI;
            double y = (rnd.nextFloat() * 2.0 - 1.0) * Math.PI;
            double z = (rnd.nextFloat() * 2.0 - 1.0) * Math.PI;
            Quaterniond p = new Quaterniond().rotateY(y).rotateX(x).rotateZ(z);
            Vector3d a = p.getEulerAnglesYXZ(new Vector3d());
            Quaterniond q = new Quaterniond().rotateY(a.y).rotateX(a.x).rotateZ(a.z);
            Vector3d v = new Vector3d(rnd.nextFloat()*2-1, rnd.nextFloat()*2-1, rnd.nextFloat()*2-1);
            Vector3d t1 = p.transform(v, new Vector3d());
            Vector3d t2 = q.transform(v, new Vector3d());
            if (!t1.equals(t2, 1E-10f))
                failure++;
        }
        if ((float)failure / N > 0.0001f) // <- allow for a failure rate of 0.01%
            throw new AssertionError();
    }
}
