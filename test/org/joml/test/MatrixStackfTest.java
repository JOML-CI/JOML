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

    public static void testEquals() {
        MatrixStackf s1 = new MatrixStackf(3);
        MatrixStackf s2 = new MatrixStackf(1);
        MatrixStackf s3 = new MatrixStackf(2);
        MatrixStackf s4 = new MatrixStackf(2);
        s4.scale(2);
        MatrixStackf s5 = new MatrixStackf(2);
        s5.pushMatrix();
        MatrixStackf s6 = new MatrixStackf(2);
        s6.pushMatrix();
        s6.scale(2);
        s6.popMatrix();
        Matrix4f m1 = new Matrix4f();
        Matrix4f m2 = new Matrix4f().scale(2);
        
        // MatrixStackf.equals(Matrix4f) only compares the 16 matrix elements
        assertTrue(s1.equals(m1));
        assertTrue(s2.equals(m1));
        assertFalse(s1.equals(m2));
        assertTrue(s4.equals(m2));

        // MatrixStackf.equals(MatrixStackf) compares the 16 matrix elements
        // and all matrices from the bottom to the current/top of the stack.
        assertTrue(s1.equals(s2));
        assertTrue(s1.equals(s3));
        assertFalse(s1.equals(s4));
        assertFalse(s3.equals(s5));
        assertTrue(s3.equals(s6));
    }

}
