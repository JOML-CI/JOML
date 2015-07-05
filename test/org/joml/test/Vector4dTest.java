package org.joml.test;

import junit.framework.TestCase;

import org.joml.*;

/**
 * Test class for {@link Vector4d}.
 * @author Sebastian Fellner
 */
public class Vector4dTest extends TestCase {
	public void testAngleVector4dVector4d() {
		Vector4d testVec1 = new Vector4d(2, -9.37, 5.892, -12.5);
		Vector4d testVec2 = new Vector4d();
		
		// angle(v, v) should give 0
		double angle = testVec1.angle(testVec1);
		assertEquals(0, angle, TestUtil.MANY_OPS_AROUND_ZERO_PRECISION_DOUBLE);
		
		// angle(v, -v) should give Math.PI
		testVec1.negate(testVec2);
		angle = testVec1.angle(testVec2);
		assertEquals(Math.PI, angle, TestUtil.MANY_OPS_AROUND_ZERO_PRECISION_DOUBLE);
	}
}
