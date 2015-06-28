package org.joml.test;

import junit.framework.TestCase;
import org.joml.*;

/**
 * Test class for {@link Vector2f}.
 * @author Sebastian Fellner
 */
public class Vector2fTest extends TestCase {
	public void testAngleVector2fVector2f() {
		Vector2f testVec1 = new Vector2f(-9.37f, 5.892f);
		Vector2f testVec2 = new Vector2f();
		
		// angle(v, v) should give 0
		float angle = testVec1.angle(testVec1);
		assertEquals(0, angle, TestUtil.MANY_OPS_AROUND_ZERO_PRECISION_FLOAT);
		
		// angle(v, -v) should give 180
		testVec1.negate(testVec2);
		angle = testVec1.angle(testVec2);
		assertEquals(180.0f, angle, TestUtil.MANY_OPS_AROUND_ZERO_PRECISION_FLOAT);
	}
}
