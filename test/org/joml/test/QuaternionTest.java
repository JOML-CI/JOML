package org.joml.test;

import junit.framework.TestCase;
import org.joml.*;

/**
 * Test class for {@link Quaternion}.
 * @author Sebastian Fellner
 */
public class QuaternionTest extends TestCase {
	
	public void testMulQuaternionQuaternionQuaternion() {
		// Multiplication with the identity quaternion should change nothing
		Quaternion testQuat = new Quaternion(1f, 23.3f, -7.57f, 2.1f);
		Quaternion identityQuat = new Quaternion().identity();
		Quaternion resultQuat = new Quaternion();
		
		Quaternion.mul(testQuat, identityQuat, resultQuat);
		assertTrue(TestUtil.quatEqual(testQuat, resultQuat, TestUtil.STANDARD_AROUND_ZERO_PRECISION_FLOAT));
		
		Quaternion.mul(identityQuat, testQuat, resultQuat);
		assertTrue(TestUtil.quatEqual(testQuat, resultQuat, TestUtil.STANDARD_AROUND_ZERO_PRECISION_FLOAT));
		
		// Multiplication with conjugate should give (0, 0, 0, dot(this, this))
		Quaternion conjugate = new Quaternion();
		Quaternion.conjugate(testQuat, conjugate);
		Quaternion.mul(testQuat, conjugate, resultQuat);
		
		Quaternion wantedResultQuat = new Quaternion(0, 0, 0, testQuat.dot(testQuat));
		assertTrue(TestUtil.quatEqual(resultQuat, wantedResultQuat, TestUtil.MANY_OPS_AROUND_ZERO_PRECISION_FLOAT));
	}
}
