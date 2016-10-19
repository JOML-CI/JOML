package org.joml.test;

import junit.framework.TestCase;

import org.joml.*;

/**
 * Test class for {@link Vector4fc}.
 */
public class Vector4fcTest extends TestCase {
    public static void testProxy() {
        Vector4f testVec1 = new Vector4f(2f, -9.37f, 5.892f, -12.5f);
        Vector4fc proxy = testVec1.toImmutable();
        Vector4fc proxy2 = testVec1.toImmutable();
        assertSame(proxy, proxy2);
        assertNotSame(testVec1, proxy);
        assertNotSame(testVec1, proxy2);
        assertEquals(testVec1.x, proxy.x(), 0);
        assertEquals(testVec1.y, proxy.y(), 0);
        assertEquals(testVec1.z, proxy.z(), 0);
        assertEquals(testVec1.w, proxy.w(), 0);
    }
}
