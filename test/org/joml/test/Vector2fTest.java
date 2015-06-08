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
		float angle = Vector2f.angle(testVec1, testVec1);
		assertEquals(angle, 0, TestUtil.MANY_OPS_AROUND_ZERO_PRECISION_FLOAT);
		
		// angle(v, -v) should give PI
		Vector2f.negate(testVec1, testVec2);
		angle = Vector2f.angle(testVec1, testVec2);
		assertEquals(angle, Math.PI, TestUtil.MANY_OPS_AROUND_ZERO_PRECISION_FLOAT);
	}
}
