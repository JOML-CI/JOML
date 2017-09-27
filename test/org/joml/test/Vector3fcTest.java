package org.joml.test;

import junit.framework.TestCase;

import org.joml.*;

/**
 * Test class for {@link Vector3fc}.
 */
public class Vector3fcTest extends TestCase {
    public static void testProxy() {
        Vector3f testVec1 = new Vector3f(2f, -9.37f, 5.892f);
        Vector3fc proxy = testVec1.readOnlyView();
        assertEquals(testVec1.x, proxy.x(), 0);
        assertEquals(testVec1.y, proxy.y(), 0);
        assertEquals(testVec1.z, proxy.z(), 0);
    }
}
