package org.joml.test;

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
		return Math.abs(a - b) <= precision;
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
		if (Math.abs(a - b) <= precision)
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
		return Math.abs(a - b) <= precision;
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
		if (Math.abs(a - b) <= precision)
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
	public static boolean quatEqual(Quaternion a, Quaternion b, float precision) {
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
	public static boolean quatEqual(QuaternionD a, QuaternionD b, double precision) {
		return doubleEqual(a.x, b.x, precision)
			&& doubleEqual(a.y, b.y, precision)
			&& doubleEqual(a.z, b.z, precision)
			&& doubleEqual(a.w, b.w, precision);
	}
}
