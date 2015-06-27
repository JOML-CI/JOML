package org.joml.test;

import junit.framework.TestCase;
import org.joml.*;

/**
 * Test class for {@link Vector3d}.
 * @author Sebastian Fellner
 */
public class Vector3dTest extends TestCase {
	public void testAngleVector3dVector3d() {
		Vector3d testVec1 = new Vector3d(2, -9.37, 5.892);
		Vector3d testVec2 = new Vector3d();
		
		// angle(v, v) should give 0
		double angle = testVec1.angle(testVec1);
		assertEquals(angle, 0, TestUtil.MANY_OPS_AROUND_ZERO_PRECISION_DOUBLE);
		
		// angle(v, -v) should give PI
		testVec1.negate(testVec2);
		angle = testVec1.angle(testVec2);
		assertEquals(angle, Math.PI, TestUtil.MANY_OPS_AROUND_ZERO_PRECISION_DOUBLE);
	}
}
