package org.joml.test;

import junit.framework.TestCase;

import org.joml.*;

/**
 * Test class for {@link Vector2d}.
 * @author Sebastian Fellner
 */
public class Vector2dTest extends TestCase {
    public static void testAngleVector2dVector2d() {
        Vector2d testVec1 = new Vector2d(-9.37, 5.892);
        Vector2d testVec2 = new Vector2d();
        
        // angle(v, v) should give 0
        double angle = testVec1.angle(testVec1);
        assertEquals(0, angle, TestUtil.MANY_OPS_AROUND_ZERO_PRECISION_DOUBLE);
        
        // angle(v, -v) should give Math.PI
        testVec1.negate(testVec2);
        angle = testVec1.angle(testVec2);
        assertEquals(java.lang.Math.PI, angle, TestUtil.MANY_OPS_AROUND_ZERO_PRECISION_DOUBLE);
    }
}
