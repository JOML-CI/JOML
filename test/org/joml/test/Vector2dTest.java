package org.joml.test;

import junit.framework.TestCase;
import org.joml.*;

/**
 * Test class for {@link Vector2d}.
 * @author Sebastian Fellner
 */
public class Vector2dTest extends TestCase {
	public void testAngleVector2dVector2d() {
		Vector2d testVec1 = new Vector2d(-9.37, 5.892);
		Vector2d testVec2 = new Vector2d();
		
		// angle(v, v) should give 0
		double angle = Vector2d.angle(testVec1, testVec1);
		assertTrue(TestUtil.doubleEqual(angle, 0, TestUtil.MANY_OPS_PRECISION_DOUBLE));
		
		// angle(v, -v) should give PI
		Vector2d.negate(testVec1, testVec2);
		angle = Vector2d.angle(testVec1, testVec2);
		assertTrue(TestUtil.doubleEqual(angle, Math.PI, TestUtil.MANY_OPS_PRECISION_DOUBLE));
	}
}
