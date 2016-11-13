package org.joml;

import junit.framework.TestCase;

import org.joml.*;

/**
 * Test class for {@link Vector3d}.
 * @author Sebastian Fellner
 */
public class Vector3dTest extends TestCase {
    public static void testAngleVector3dVector3d() {
        Vector3d testVec1 = new Vector3d(2, -9.37, 5.892);
        Vector3d testVec2 = new Vector3d();
        
        // angle(v, v) should give 0
        double angle = testVec1.angle(testVec1);
        assertEquals(0, angle, TestUtil.MANY_OPS_AROUND_ZERO_PRECISION_DOUBLE);
        
        // angle(v, -v) should give Math.PI
        testVec1.negate(testVec2);
        angle = testVec1.angle(testVec2);
        assertEquals(java.lang.Math.PI, angle, TestUtil.MANY_OPS_AROUND_ZERO_PRECISION_DOUBLE);
    }
}
