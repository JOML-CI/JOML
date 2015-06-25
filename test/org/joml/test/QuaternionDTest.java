package org.joml.test;

import junit.framework.TestCase;
import org.joml.*;

/**
 * Test class for {@link Quaterniond}.
 * @author Sebastian Fellner
 */
public class QuaternionDTest extends TestCase {
	
	public void testMulQuaternionDQuaternionDQuaternionD() {
		// Multiplication with the identity quaternion should change nothing
		Quaterniond testQuat = new Quaterniond(1, 23.3, -7.57, 2.1);
		Quaterniond identityQuat = new Quaterniond().identity();
		Quaterniond resultQuat = new Quaterniond();
		
		Quaterniond.mul(testQuat, identityQuat, resultQuat);
		assertTrue(TestUtil.quatEqual(testQuat, resultQuat, TestUtil.STANDARD_AROUND_ZERO_PRECISION_DOUBLE));
		
		Quaterniond.mul(identityQuat, testQuat, resultQuat);
		assertTrue(TestUtil.quatEqual(testQuat, resultQuat, TestUtil.STANDARD_AROUND_ZERO_PRECISION_DOUBLE));
		
		// Multiplication with conjugate should give (0, 0, 0, dot(this, this))
		Quaterniond conjugate = new Quaterniond();
		Quaterniond.conjugate(testQuat, conjugate);
		Quaterniond.mul(testQuat, conjugate, resultQuat);
		
		Quaterniond wantedResultQuat = new Quaterniond(0, 0, 0, testQuat.dot(testQuat));
		assertTrue(TestUtil.quatEqual(resultQuat, wantedResultQuat, TestUtil.MANY_OPS_AROUND_ZERO_PRECISION_DOUBLE));
	}
}
