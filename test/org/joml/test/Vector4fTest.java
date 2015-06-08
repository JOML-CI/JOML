package org.joml.test;

import junit.framework.TestCase;
import org.joml.*;

/**
 * Test class for {@link Vector4f}.
 * @author Sebastian Fellner
 */
public class Vector4fTest extends TestCase {
	public void testAngleVector4fVector4f() {
		Vector4f testVec1 = new Vector4f(2f, -9.37f, 5.892f, -12.5f);
		Vector4f testVec2 = new Vector4f();
		
		// angle(v, v) should give 0
		float angle = Vector4f.angle(testVec1, testVec1);
		assertEquals(angle, 0, TestUtil.MANY_OPS_AROUND_ZERO_PRECISION_FLOAT);
		
		// angle(v, -v) should give PI
		Vector4f.negate(testVec1, testVec2);
		angle = Vector4f.angle(testVec1, testVec2);
		assertEquals(angle, Math.PI, TestUtil.MANY_OPS_AROUND_ZERO_PRECISION_FLOAT);
	}
}
