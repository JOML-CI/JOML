package org.joml.test;

import junit.framework.TestCase;
import org.joml.*;

/**
 * Test class for {@link QuaternionD}.
 * @author Sebastian Fellner
 */
public class QuaternionDTest extends TestCase {
	
	public void testMulQuaternionDQuaternionDQuaternionD() {
		// Multiplication with the identity quaternion should change nothing
		QuaternionD testQuat = new QuaternionD(1, 23.3, -7.57, 2.1);
		QuaternionD identityQuat = new QuaternionD().identity();
		QuaternionD resultQuat = new QuaternionD();
		
		QuaternionD.mul(testQuat, identityQuat, resultQuat);
		assertTrue(TestUtil.quatEqual(testQuat, resultQuat, TestUtil.STANDARD_AROUND_ZERO_PRECISION_DOUBLE));
		
		QuaternionD.mul(identityQuat, testQuat, resultQuat);
		assertTrue(TestUtil.quatEqual(testQuat, resultQuat, TestUtil.STANDARD_AROUND_ZERO_PRECISION_DOUBLE));
		
		// Multiplication with conjugate should give (0, 0, 0, dot(this, this))
		QuaternionD conjugate = new QuaternionD();
		QuaternionD.conjugate(testQuat, conjugate);
		QuaternionD.mul(testQuat, conjugate, resultQuat);
		
		QuaternionD wantedResultQuat = new QuaternionD(0, 0, 0, testQuat.dot(testQuat));
		assertTrue(TestUtil.quatEqual(resultQuat, wantedResultQuat, TestUtil.MANY_OPS_AROUND_ZERO_PRECISION_DOUBLE));
	}
}
