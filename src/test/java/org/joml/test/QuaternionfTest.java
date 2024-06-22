/*
 * The MIT License
 *
 * Copyright (c) 2015-2024 JOML.
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
 * Test class for {@link Quaternionf}.
 * @author Sebastian Fellner
 */
class QuaternionfTest {
    
    @Test
    void testMulQuaternionQuaternionQuaternion() {
        // Multiplication with the identity quaternion should change nothing
        Quaternionf testQuat = new Quaternionf(1f, 23.3f, -7.57f, 2.1f);
        Quaternionf identityQuat = new Quaternionf().identity();
        Quaternionf resultQuat = new Quaternionf();
        
        testQuat.mul(identityQuat, resultQuat);
        assertTrue(quatEqual(testQuat, resultQuat, STANDARD_AROUND_ZERO_PRECISION_FLOAT));
        
        identityQuat.mul(testQuat, resultQuat);
        assertTrue(quatEqual(testQuat, resultQuat, STANDARD_AROUND_ZERO_PRECISION_FLOAT));

        // Multiplication with conjugate should give (0, 0, 0, dot(this, this))
        Quaternionf conjugate = new Quaternionf();
        testQuat.conjugate(conjugate);
        testQuat.mul(conjugate, resultQuat);
        
        Quaternionf wantedResultQuat = new Quaternionf(0, 0, 0, testQuat.dot(testQuat));
        assertTrue(quatEqual(resultQuat, wantedResultQuat, MANY_OPS_AROUND_ZERO_PRECISION_FLOAT));
    }

    @Test
    void testSlerp() {
        Quaternionf q1 = new Quaternionf().rotateY(0.0f);
        Quaternionf q2 = new Quaternionf().rotateY((float) java.lang.Math.PI * 0.5f);
        Quaternionf q = new Quaternionf();
        Vector3f v = new Vector3f(1.0f, 0.0f, 0.0f);
        q1.slerp(q2, 0.5f, q);
        q.transform(v);
        assertVector3fEquals(new Vector3f(1.0f, 0.0f, -1.0f).normalize(), v, 1E-5f);
    }

    @Test
    void testNlerp() {
        Quaternionf q1 = new Quaternionf().rotateY(0.0f);
        Quaternionf q2 = new Quaternionf().rotateY((float) java.lang.Math.PI * 0.5f);
        Quaternionf q = new Quaternionf();
        Vector3f v = new Vector3f(1.0f, 0.0f, 0.0f);
        q1.nlerp(q2, 0.5f, q);
        q.transform(v);
        assertVector3fEquals(new Vector3f(1.0f, 0.0f, -1.0f).normalize(), v, 1E-5f);
    }

    @Test
    void testNlerpRecursive() {
        Quaternionf q1 = new Quaternionf().rotateY(0.0f);
        Quaternionf q2 = new Quaternionf().rotateY((float) java.lang.Math.PI * 0.5f);
        Quaternionf q = new Quaternionf();
        Vector3f v = new Vector3f(1.0f, 0.0f, 0.0f);
        q1.nlerpIterative(q2, 0.5f, 1E-5f, q);
        q.transform(v);
        assertVector3fEquals(new Vector3f(1.0f, 0.0f, -1.0f).normalize(), v, 1E-5f);
    }

    @Test
    void testRotationXYZ() {
        Quaternionf v = new Quaternionf().rotationXYZ(0.12f, 0.521f, 0.951f);
        Matrix4f m = new Matrix4f().rotateXYZ(0.12f, 0.521f, 0.951f);
        Matrix4f n = new Matrix4f().set(v);
        assertMatrix4fEquals(m, n, 1E-6f);
    }

    @Test
    void testRotationZYX() {
        Quaternionf v = new Quaternionf().rotationZYX(0.12f, 0.521f, 0.951f);
        Matrix4f m = new Matrix4f().rotateZYX(0.12f, 0.521f, 0.951f);
        Matrix4f n = new Matrix4f().set(v);
        assertMatrix4fEquals(m, n, 1E-6f);
    }

    @Test
    void testRotationYXZ() {
        Quaternionf v = new Quaternionf().rotationYXZ(0.12f, 0.521f, 0.951f);
        Matrix4f m = new Matrix4f().rotationYXZ(0.12f, 0.521f, 0.951f);
        Matrix4f n = new Matrix4f().set(v);
        assertMatrix4fEquals(m, n, 1E-6f);
    }

    @Test
    void testRotateXYZ() {
        Quaternionf v = new Quaternionf().rotateXYZ(0.12f, 0.521f, 0.951f);
        Matrix4f m = new Matrix4f().rotateXYZ(0.12f, 0.521f, 0.951f);
        Matrix4f n = new Matrix4f().set(v);
        assertMatrix4fEquals(m, n, 1E-6f);
    }

    @Test
    void testRotateZYX() {
        Quaternionf v = new Quaternionf().rotateZYX(0.12f, 0.521f, 0.951f);
        Matrix4f m = new Matrix4f().rotateZYX(0.12f, 0.521f, 0.951f);
        Matrix4f n = new Matrix4f().set(v);
        assertMatrix4fEquals(m, n, 1E-6f);
    }

    @Test
    void testRotateYXZ() {
        Quaternionf v = new Quaternionf().rotateYXZ(0.12f, 0.521f, 0.951f);
        Matrix4f m = new Matrix4f().rotateYXZ(0.12f, 0.521f, 0.951f);
        Matrix4f n = new Matrix4f().set(v);
        assertMatrix4fEquals(m, n, 1E-6f);
    }

    @Test
    void testRotateToUnit() {
        Vector3f v1 = new Vector3f(1, 2, 3).normalize();
        Vector3f v2 = new Vector3f(5, -2, -1).normalize();
        Quaternionf rotation = new Quaternionf().rotateTo(v1, v2);
        rotation.transform(v1);
        assertVector3fEquals(v1, v2, 1E-6f);
    }

    @Test
    void testRotateToNonUnit() {
        Vector3f v1 = new Vector3f(1, 2, 3).normalize().mul(3);
        Vector3f v2 = new Vector3f(5, -2, -1).normalize().mul(3);
        Quaternionf rotation = new Quaternionf().rotateTo(v1, v2);
        rotation.transform(v1);
        assertVector3fEquals(v1, v2, 1E-6f);
    }

    @Test
    void testRotateToUnitOpposite() {
        Vector3f v1 = new Vector3f(1, 2, 3).normalize();
        Vector3f v2 = new Vector3f(-1, -2, -3).normalize();
        Quaternionf rotation = new Quaternionf().rotateTo(v1, v2);
        rotation.transform(v1);
        assertVector3fEquals(v1, v2, 1E-6f);
    }

    @Test
    void testRotateToNonUnitOpposite() {
        Vector3f v1 = new Vector3f(1, 2, 3);
        Vector3f v2 = new Vector3f(-1, -2, -3);
        Quaternionf rotation = new Quaternionf().rotateTo(v1, v2);
        rotation.transform(v1);
        assertVector3fEquals(v1, v2, 1E-6f);
    }

    @Test
    void testRotateToReturnsDestination() {
        Quaternionf rotation = new Quaternionf();
        Quaternionf destination = new Quaternionf();
        Quaternionf result = rotation.rotateTo(0, 1, 0, 0, 1, 0, destination);
        assertSame(destination, result);
    }

    @Test
    void testConjugateBy() {
        Quaternionf p = new Quaternionf().rotateXYZ(0.234f, -0.62f, 0.11f);
        Quaternionf q = new Quaternionf().rotateXYZ(0.834f, 0.42f, -1.471f);
        Quaternionf r = p.mul(q.mul(p.invert(new Quaternionf()), new Quaternionf()), new Quaternionf());
        Quaternionf r2 = q.conjugateBy(p, new Quaternionf());
        assertQuaternionfEquals(r, r2, 1E-6f);
    }

    @Test
    void testGetEulerAnglesXYZ() {
        Random rnd = new Random(1L);
        int failure = 0;
        int N = 30000;
        for (int i = 0; i < N; i++) {
            float x = (rnd.nextFloat() * 2f - 1f) * (float) Math.PI;
            float y = (rnd.nextFloat() * 2f - 1f) * (float) Math.PI;
            float z = (rnd.nextFloat() * 2f - 1f) * (float) Math.PI;
            Quaternionf p = new Quaternionf().rotateXYZ(x, y, z);
            Vector3f a = p.getEulerAnglesXYZ(new Vector3f());
            Quaternionf q = new Quaternionf().rotateX(a.x).rotateY(a.y).rotateZ(a.z);
            Vector3f v = new Vector3f(rnd.nextFloat()*2-1, rnd.nextFloat()*2-1, rnd.nextFloat()*2-1);
            Vector3f t1 = p.transform(v, new Vector3f());
            Vector3f t2 = q.transform(v, new Vector3f());
            if (!t1.equals(t2, 1E-3f))
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
            float x = (rnd.nextFloat() * 2f - 1f) * (float) Math.PI;
            float y = (rnd.nextFloat() * 2f - 1f) * (float) Math.PI;
            float z = (rnd.nextFloat() * 2f - 1f) * (float) Math.PI;
            Quaternionf p = new Quaternionf().rotateZYX(z, y, x);
            Vector3f a = p.getEulerAnglesZYX(new Vector3f());
            Quaternionf q = new Quaternionf().rotateZ(a.z).rotateY(a.y).rotateX(a.x);
            Vector3f v = new Vector3f(rnd.nextFloat()*2-1, rnd.nextFloat()*2-1, rnd.nextFloat()*2-1);
            Vector3f t1 = p.transform(v, new Vector3f());
            Vector3f t2 = q.transform(v, new Vector3f());
            if (!t1.equals(t2, 1E-3f))
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
            float x = (rnd.nextFloat() * 2f - 1f) * (float) Math.PI;
            float y = (rnd.nextFloat() * 2f - 1f) * (float) Math.PI;
            float z = (rnd.nextFloat() * 2f - 1f) * (float) Math.PI;
            Quaternionf p = new Quaternionf().rotateZ(z).rotateX(x).rotateY(y);
            Vector3f a = p.getEulerAnglesZXY(new Vector3f());
            Quaternionf q = new Quaternionf().rotateZ(a.z).rotateX(a.x).rotateY(a.y);
            Vector3f v = new Vector3f(rnd.nextFloat()*2-1, rnd.nextFloat()*2-1, rnd.nextFloat()*2-1);
            Vector3f t1 = p.transform(v, new Vector3f());
            Vector3f t2 = q.transform(v, new Vector3f());
            if (!t1.equals(t2, 1E-3f))
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
            float x = (rnd.nextFloat() * 2f - 1f) * (float) Math.PI;
            float y = (rnd.nextFloat() * 2f - 1f) * (float) Math.PI;
            float z = (rnd.nextFloat() * 2f - 1f) * (float) Math.PI;
            Quaternionf p = new Quaternionf().rotateY(y).rotateX(x).rotateZ(z);
            Vector3f a = p.getEulerAnglesYXZ(new Vector3f());
            Quaternionf q = new Quaternionf().rotateY(a.y).rotateX(a.x).rotateZ(a.z);
            Vector3f v = new Vector3f(rnd.nextFloat()*2-1, rnd.nextFloat()*2-1, rnd.nextFloat()*2-1);
            Vector3f t1 = p.transform(v, new Vector3f());
            Vector3f t2 = q.transform(v, new Vector3f());
            if (!t1.equals(t2, 1E-3f))
                failure++;
        }
        if ((float)failure / N > 0.0001f) // <- allow for a failure rate of 0.01%
            throw new AssertionError();
    }
}
