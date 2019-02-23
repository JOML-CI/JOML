/*
 * The MIT License
 *
 * Copyright (c) 2015-2019 JOML.
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

import junit.framework.Assert;

import org.joml.*;

/**
 * Utilities for testing.
 * @author Sebastian Fellner
 */
public class TestUtil {
    /**
     * Precision for methods that do many operations calculating with a magnitude around zero, giving less accuracy.
     */
    public static final float MANY_OPS_AROUND_ZERO_PRECISION_FLOAT = 0.001f;
    /**
     * Precision for methods that do basic operations calculating with a magnitude around zero.
     */
    public static final float STANDARD_AROUND_ZERO_PRECISION_FLOAT = 0.000000000000000001f;

    /**
     * Precision for methods that do many operations calculating with values with a magnitude around zero, giving less accuracy.
     */
    public static final double MANY_OPS_AROUND_ZERO_PRECISION_DOUBLE = 0.00001;
    /**
     * Precision for methods that do basic operations calculating with a magnitude around zero.
     */
    public static final double STANDARD_AROUND_ZERO_PRECISION_DOUBLE = 0.000000000000000000001;

    /**
     * Return whether two floating point numbers are equal. They are considered equal when their difference is less than or equal to the precision.
     * @param a the first number
     * @param b the second number
     * @param precision if abs(a - b) <= precision, a and b are considered equal
     * @return whether a and b are equal
     * @see #floatCompare(float, float, float)
     */
    public static boolean floatEqual(float a, float b, float precision) {
        return java.lang.Math.abs(a - b) <= precision;
    }

    /**
     * Compare two floating point numbers. They are considered equal when their difference is less than or equal to the precision.
     * @param a the first number
     * @param b the second number
     * @param precision if abs(a - b) <= precision, a and b are considered equal
     * @return 0 if a == b, 1 if a > b, -1 if a < b
     * @see #floatEqual(float, float, float)
     */
    public static int floatCompare(float a, float b, float precision) {
        if (java.lang.Math.abs(a - b) <= precision)
            return 0;
        else if (a > b)
            return 1;
        else
            return -1;
    }

    /**
     * Return whether two double-precision floating point numbers are equal. They are considered equal when their difference is 
     * less than or equal to the precision.
     * @param a the first number
     * @param b the second number
     * @param precision if abs(a - b) <= precision, a and b are considered equal
     * @return whether a and b are equal
     * @see #floatCompare(double, double, double)
     */
    public static boolean doubleEqual(double a, double b, double precision) {
        return java.lang.Math.abs(a - b) <= precision;
    }

    /**
     * Compare two double-precision floating point numbers. They are considered equal when their difference is 
     * less than or equal to the precision.
     * @param a the first number
     * @param b the second number
     * @param precision if abs(a - b) <= precision, a and b are considered equal
     * @return 0 if a == b, 1 if a > b, -1 if a < b
     * @see #floatEqual(double, double, double)
     */
    public static int doubleCompare(double a, double b, double precision) {
        if (java.lang.Math.abs(a - b) <= precision)
            return 0;
        else if (a > b)
            return 1;
        else
            return -1;
    }

    /**
     * Return whether two quaternions are equal. They are considered equal when their difference is 
     * less than or equal to the precision.
     * @param a the first quaternion
     * @param b the second quaternion
     * @param precision if abs(a.[comp] - b.[comp]) <= precision for every component comp (x, y, z, w), a and b are considered equal
     * @return whether a and b are equal
     */
    public static boolean quatEqual(Quaternionf a, Quaternionf b, float precision) {
        return floatEqual(a.x, b.x, precision)
            && floatEqual(a.y, b.y, precision)
            && floatEqual(a.z, b.z, precision)
            && floatEqual(a.w, b.w, precision);
    }

    /**
     * Return whether two quaternions are equal. They are considered equal when their difference is 
     * less than or equal to the precision.
     * @param a the first quaternion
     * @param b the second quaternion
     * @param precision if abs(a.[comp] - b.[comp]) <= precision for every component comp (x, y, z, w), a and b are considered equal
     * @return whether a and b are equal
     */
    public static boolean quatEqual(Quaterniond a, Quaterniond b, double precision) {
        return doubleEqual(a.x, b.x, precision)
            && doubleEqual(a.y, b.y, precision)
            && doubleEqual(a.z, b.z, precision)
            && doubleEqual(a.w, b.w, precision);
    }

    /**
     * Assert that both matrices are equal with respect to the given delta.
     * 
     * @param m1
     * @param m2
     * @param delta
     */
    public static void assertMatrix4fEquals(Matrix4f m1, Matrix4f m2, float delta) {
        Assert.assertEquals(m1.m00(), m2.m00(), delta);
        Assert.assertEquals(m1.m01(), m2.m01(), delta);
        Assert.assertEquals(m1.m02(), m2.m02(), delta);
        Assert.assertEquals(m1.m03(), m2.m03(), delta);
        Assert.assertEquals(m1.m10(), m2.m10(), delta);
        Assert.assertEquals(m1.m11(), m2.m11(), delta);
        Assert.assertEquals(m1.m12(), m2.m12(), delta);
        Assert.assertEquals(m1.m13(), m2.m13(), delta);
        Assert.assertEquals(m1.m20(), m2.m20(), delta);
        Assert.assertEquals(m1.m21(), m2.m21(), delta);
        Assert.assertEquals(m1.m22(), m2.m22(), delta);
        Assert.assertEquals(m1.m23(), m2.m23(), delta);
        Assert.assertEquals(m1.m30(), m2.m30(), delta);
        Assert.assertEquals(m1.m31(), m2.m31(), delta);
        Assert.assertEquals(m1.m32(), m2.m32(), delta);
        Assert.assertEquals(m1.m33(), m2.m33(), delta);
    }

    /**
     * Assert that both matrices are equal with respect to the given delta.
     * 
     * @param m1
     * @param m2
     * @param delta
     */
    public static void assertMatrix4x3fEquals(Matrix4x3fc m1, Matrix4x3fc m2, float delta) {
        Assert.assertEquals(m1.m00(), m2.m00(), delta);
        Assert.assertEquals(m1.m01(), m2.m01(), delta);
        Assert.assertEquals(m1.m02(), m2.m02(), delta);
        Assert.assertEquals(m1.m10(), m2.m10(), delta);
        Assert.assertEquals(m1.m11(), m2.m11(), delta);
        Assert.assertEquals(m1.m12(), m2.m12(), delta);
        Assert.assertEquals(m1.m20(), m2.m20(), delta);
        Assert.assertEquals(m1.m21(), m2.m21(), delta);
        Assert.assertEquals(m1.m22(), m2.m22(), delta);
        Assert.assertEquals(m1.m30(), m2.m30(), delta);
        Assert.assertEquals(m1.m31(), m2.m31(), delta);
        Assert.assertEquals(m1.m32(), m2.m32(), delta);
    }

    /**
     * Assert that both matrices are equal with respect to the given delta.
     * 
     * @param m1
     * @param m2
     * @param delta
     */
    public static void assertMatrix4x3dEquals(Matrix4x3dc m1, Matrix4x3dc m2, double delta) {
        Assert.assertEquals(m1.m00(), m2.m00(), delta);
        Assert.assertEquals(m1.m01(), m2.m01(), delta);
        Assert.assertEquals(m1.m02(), m2.m02(), delta);
        Assert.assertEquals(m1.m10(), m2.m10(), delta);
        Assert.assertEquals(m1.m11(), m2.m11(), delta);
        Assert.assertEquals(m1.m12(), m2.m12(), delta);
        Assert.assertEquals(m1.m20(), m2.m20(), delta);
        Assert.assertEquals(m1.m21(), m2.m21(), delta);
        Assert.assertEquals(m1.m22(), m2.m22(), delta);
        Assert.assertEquals(m1.m30(), m2.m30(), delta);
        Assert.assertEquals(m1.m31(), m2.m31(), delta);
        Assert.assertEquals(m1.m32(), m2.m32(), delta);
    }

    /**
     * Assert that both vectors are equal with respect to the given delta.
     * 
     * @param expected
     * @param actual
     * @param delta
     */
    public static void assertVector4fEquals(Vector4f expected, Vector4f actual, float delta) {
        Assert.assertEquals(expected.x, actual.x, delta);
        Assert.assertEquals(expected.y, actual.y, delta);
        Assert.assertEquals(expected.z, actual.z, delta);
        Assert.assertEquals(expected.w, actual.w, delta);
    }

    /**
     * Assert that both vectors are equal with respect to the given delta.
     * 
     * @param expected
     * @param actual
     * @param delta
     */
    public static void assertVector3fEquals(Vector3f expected, Vector3f actual, float delta) {
        Assert.assertEquals(expected.x, actual.x, delta);
        Assert.assertEquals(expected.y, actual.y, delta);
        Assert.assertEquals(expected.z, actual.z, delta);
    }

    /**
     * Assert that both vectors are equal with respect to the given delta.
     * 
     * @param expected
     * @param actual
     * @param delta
     */
    public static void assertVector3dEquals(Vector3d expected, Vector3d actual, double delta) {
        Assert.assertEquals(expected.x, actual.x, delta);
        Assert.assertEquals(expected.y, actual.y, delta);
        Assert.assertEquals(expected.z, actual.z, delta);
    }

    /**
     * Assert that both vectors are equal with respect to the given delta.
     * 
     * @param expected
     * @param actual
     * @param delta
     */
    public static void assertVector2fEquals(Vector2f expected, Vector2f actual, float delta) {
        Assert.assertEquals(expected.x, actual.x, delta);
        Assert.assertEquals(expected.y, actual.y, delta);
    }

}
