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
		double angle = Vector3d.angle(testVec1, testVec1);
		assertTrue(TestUtil.doubleEqual(angle, 0, TestUtil.MANY_OPS_PRECISION_DOUBLE));
		
		// angle(v, -v) should give PI
		Vector3d.negate(testVec1, testVec2);
		angle = Vector3d.angle(testVec1, testVec2);
		assertTrue(TestUtil.doubleEqual(angle, Math.PI, TestUtil.MANY_OPS_PRECISION_DOUBLE));
	}
}
