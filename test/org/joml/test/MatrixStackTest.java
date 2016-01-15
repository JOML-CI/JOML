package org.joml.test;

import junit.framework.TestCase;

import org.joml.Matrix4f;
import org.joml.MatrixStack;

/**
 * Tests for the {@link MatrixStack} class.
 * 
 * @author Kai Burjack
 */
public class MatrixStackTest extends TestCase {

    public static void testPushPop() {
        Matrix4f identity = new Matrix4f();
        MatrixStack m = new MatrixStack(2);
        m.pushMatrix();
        m.perspective(1, 2, 3, 4);
        assertFalse(identity.equals(m));
        m.popMatrix();
        assertTrue(identity.equals(m));
    }

    public static void testPushTooFar() {
        try {
            MatrixStack m = new MatrixStack(2);
            m.pushMatrix();
            m.pushMatrix();
            fail();
        } catch (IllegalStateException e) {
        }
    }

    public static void testPopTooFar() {
        try {
            MatrixStack m = new MatrixStack(2);
            m.pushMatrix();
            m.popMatrix();
            m.popMatrix();
            fail();
        } catch (IllegalStateException e) {
        }
    }

}
