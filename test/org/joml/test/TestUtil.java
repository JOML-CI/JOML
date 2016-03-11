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

}
