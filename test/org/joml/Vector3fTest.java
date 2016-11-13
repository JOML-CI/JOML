package org.joml;

import junit.framework.TestCase;
import org.joml.*;

/**
 * Test class for {@link Vector3f}.
 * @author Sebastian Fellner
 */
public class Vector3fTest extends TestCase {
    public static void testAngleVector3fVector3f() {
        Vector3f testVec1 = new Vector3f(2f, -9.37f, 5.892f);
        Vector3f testVec2 = new Vector3f();
        
        // angle(v, v) should give 0
        float angle = testVec1.angle(testVec1);
        assertEquals(0, angle, TestUtil.MANY_OPS_AROUND_ZERO_PRECISION_FLOAT);
        
        // angle(v, -v) should give Math.PI
        testVec1.negate(testVec2);
        angle = testVec1.angle(testVec2);
        assertEquals(java.lang.Math.PI, angle, TestUtil.MANY_OPS_AROUND_ZERO_PRECISION_FLOAT);
    }
}
