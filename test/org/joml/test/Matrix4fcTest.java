package org.joml.test;

import junit.framework.TestCase;

import org.joml.*;

/**
 * Test class for {@link Matrix4fc}.
 */
public class Matrix4fcTest extends TestCase {
    public static void testProxy() {
        Matrix4f m = new Matrix4f();
        Matrix4fc proxy = m.readOnlyView();
        m.m00(2.0f);
        Matrix4f m2 = new Matrix4f(proxy);
        assertEquals(m.m00(), proxy.m00(), 0);
        assertEquals(m2.m00(), proxy.m00(), 0);
    }
}
