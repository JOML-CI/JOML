package org.joml.test;

import org.joml.Quaternionf;
import org.joml.Quaternionfc;

import junit.framework.TestCase;

/**
 * Test class for {@link Quaternionfc}.
 */
public class QuaternionfcTest extends TestCase {
    public static void testProxy() {
        Quaternionf q = new Quaternionf(2f, -9.37f, 5.892f, -12.5f);
        Quaternionfc proxy = q.toImmutable();
        Quaternionfc proxy2 = q.toImmutable();
        assertNotSame(q, proxy);
        assertNotSame(q, proxy2);
        assertEquals(q.x, proxy.x(), 0);
        assertEquals(q.y, proxy.y(), 0);
        assertEquals(q.z, proxy.z(), 0);
        assertEquals(q.w, proxy.w(), 0);
    }
}
