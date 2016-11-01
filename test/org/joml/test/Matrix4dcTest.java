package org.joml.test;

import junit.framework.TestCase;

import org.joml.*;

/**
 * Test class for {@link Matrix4dc}.
 */
public class Matrix4dcTest extends TestCase {
    public static void testProxy() {
        Matrix4d m = new Matrix4d();
        Matrix4dc proxy = m.toImmutable();
        m.m00(2.0f);
        assertEquals(m.m00(), proxy.m00(), 0);
    }
}
