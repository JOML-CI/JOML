package org.joml.test;

import org.joml.Vector4d;

import junit.framework.TestCase;

/**
 * Test class for {@link Vector4d}.
 * @author Sebastian Fellner
 */
public class Vector4dTest extends TestCase {
    public static void testAngleVector4dVector4d() {
        Vector4d testVec1 = new Vector4d(2, -9.37, 5.892, -12.5);
        Vector4d testVec2 = new Vector4d();
        
        // angle(v, v) should give 0
        double angle = testVec1.angle(testVec1);
        assertEquals(0, angle, TestUtil.MANY_OPS_AROUND_ZERO_PRECISION_DOUBLE);
        
        // angle(v, -v) should give Math.PI
        testVec1.negate(testVec2);
        angle = testVec1.angle(testVec2);
        assertEquals(java.lang.Math.PI, angle, TestUtil.MANY_OPS_AROUND_ZERO_PRECISION_DOUBLE);
    }

    public static void testMulByScalarIntoDest() {
        Vector4d testVec1 = new Vector4d(2, 2, 2, 2);
        Vector4d destVec = new Vector4d(0, 0, 0, 0);

        testVec1.mul(2, destVec);
        assertEquals(new Vector4d(4, 4, 4, 4), destVec);
        assertEquals(new Vector4d(2, 2, 2, 2), testVec1);
    }

    public static void testDivByScalarIntoDest() {
        Vector4d testVec1 = new Vector4d(2, 2, 2, 2);
        Vector4d destVec = new Vector4d(0, 0, 0, 0);

        testVec1.div(2, destVec);
        assertEquals(new Vector4d(1, 1, 1, 1), destVec);
        assertEquals(new Vector4d(2, 2, 2, 2), testVec1);
    }
}
