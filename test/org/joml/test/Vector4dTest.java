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
		double angle = Vector4d.angle(testVec1, testVec1);
		assertEquals(angle, 0, TestUtil.MANY_OPS_PRECISION_DOUBLE);
		
		// angle(v, -v) should give PI
		Vector4d.negate(testVec1, testVec2);
		angle = Vector4d.angle(testVec1, testVec2);
		assertEquals(angle, Math.PI, TestUtil.MANY_OPS_PRECISION_DOUBLE);
	}
}
