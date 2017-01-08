package org.joml.test;

import org.joml.Matrix3x2f;
import org.joml.Vector2f;

import junit.framework.TestCase;

/**
 * Tests for the {@link Matrix3x2f} class.
 * 
 * @author Kai Burjack
 */
public class Matrix3x2fTest extends TestCase {

    public static void testInvert() {
        Matrix3x2f m = new Matrix3x2f(1, 2, 4, 5, -0.5f, -2f);
        Vector2f v = new Vector2f(4, 0.5f);
        m.transformPosition(v);
        Vector2f v2 = new Vector2f(v);
        m.invert();
        m.transformPosition(v2);
        TestUtil.assertVector2fEquals(new Vector2f(4, 0.5f), v2, 1E-6f);
    }

    public static void testView() {
        Matrix3x2f m = new Matrix3x2f().view(-4, 0.5f, -2, 3);
        Vector2f v = new Vector2f(-4, -2);
        m.transformPosition(v);
        TestUtil.assertVector2fEquals(new Vector2f(-1, -1), v, 0f);
        v.set(0.5f, 3);
        m.transformPosition(v);
        TestUtil.assertVector2fEquals(new Vector2f(1, 1), v, 0f);
    }

    public static void testUnproject() {
        Matrix3x2f m = new Matrix3x2f().view(-3, 2, -4, 1);
        TestUtil.assertVector2fEquals(new Vector2f(-3, -4), m.unproject(0, 0, new int[] {0, 0, 800, 600}, new Vector2f()), 1E-6f);
        TestUtil.assertVector2fEquals(new Vector2f(2, 1), m.unproject(800, 600, new int[] {0, 0, 800, 600}, new Vector2f()), 1E-6f);
    }

}
