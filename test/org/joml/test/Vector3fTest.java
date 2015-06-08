package org.joml.test;

import junit.framework.TestCase;
import org.joml.*;

/**
 * Test class for {@link Vector3f}.
 * @author Sebastian Fellner
 */
public class Vector3fTest extends TestCase {
	
	public void testAngleVector3fVector3f() {
		Vector3f testVec1 = new Vector3f(2f, -9.37f, 5.892f);
		Vector3f testVec2 = new Vector3f();
		
		// angle(v, v) should give 0
		float angle = Vector3f.angle(testVec1, testVec1);
		assertEquals(angle, 0, TestUtil.MANY_OPS_AROUND_ZERO_PRECISION_FLOAT);
		
		// angle(v, -v) should give PI
		Vector3f.negate(testVec1, testVec2);
		angle = Vector3f.angle(testVec1, testVec2);
		assertEquals(angle, Math.PI, TestUtil.MANY_OPS_AROUND_ZERO_PRECISION_FLOAT);
	}
}
