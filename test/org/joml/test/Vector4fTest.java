package org.joml.test;

import junit.framework.TestCase;

import org.joml.Vector4f;

/**
 * Test class for {@link Vector4f}.
 * @author Sebastian Fellner
 */
public class Vector4fTest extends TestCase {
    public static void testAngleVector4fVector4f() {
        Vector4f testVec1 = new Vector4f(2f, -9.37f, 5.892f, -12.5f);
        Vector4f testVec2 = new Vector4f();
        
        // angle(v, v) should give 0
        float angle = testVec1.angle(testVec1);
        assertEquals(0, angle, TestUtil.MANY_OPS_AROUND_ZERO_PRECISION_FLOAT);
        
        // angle(v, -v) should give Math.PI
        testVec1.negate(testVec2);
        angle = testVec1.angle(testVec2);
        assertEquals(Math.PI, angle, TestUtil.MANY_OPS_AROUND_ZERO_PRECISION_FLOAT);
    }
}
