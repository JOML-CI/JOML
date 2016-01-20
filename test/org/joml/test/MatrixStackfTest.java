package org.joml.test;

import junit.framework.TestCase;

import org.joml.Matrix4f;
import org.joml.MatrixStackf;

/**
 * Tests for the {@link MatrixStackf} class.
 * 
 * @author Kai Burjack
 */
public class MatrixStackfTest extends TestCase {

    public static void testPushPop() {
        Matrix4f identity = new Matrix4f();
        MatrixStackf m = new MatrixStackf(2);
        m.pushMatrix();
        m.perspective(1, 2, 3, 4);
        assertFalse(identity.equals(m));
        m.popMatrix();
        assertTrue(identity.equals(m));
    }

    public static void testPushTooFar() {
        try {
            MatrixStackf m = new MatrixStackf(2);
            m.pushMatrix();
            m.pushMatrix();
            fail();
        } catch (IllegalStateException e) {
            // Must reach here!
            e.hashCode(); // <- just use 'e' somehow
        }
    }

    public static void testPopTooFar() {
        try {
            MatrixStackf m = new MatrixStackf(2);
            m.pushMatrix();
            m.popMatrix();
            m.popMatrix();
            fail();
        } catch (IllegalStateException e) {
            // Must reach here!
            e.hashCode(); // <- just use 'e' somehow
        }
    }

}
