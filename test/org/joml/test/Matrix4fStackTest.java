/*
 * The MIT License
 *
 * Copyright (c) 2016-2020 JOML.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.joml.test;

import junit.framework.TestCase;

import org.joml.Matrix4f;
import org.joml.Matrix4fStack;

/**
 * Tests for the {@link Matrix4fStack} class.
 * 
 * @author Kai Burjack
 */
public class Matrix4fStackTest extends TestCase {

    public static void testPushPop() {
        Matrix4f identity = new Matrix4f();
        Matrix4fStack m = new Matrix4fStack(2);
        m.pushMatrix();
        m.perspective(1, 2, 3, 4);
        assertFalse(identity.equals(m));
        m.popMatrix();
        assertTrue(identity.equals(m));
    }

    public static void testPushTooFar() {
        try {
            Matrix4fStack m = new Matrix4fStack(2);
            m.pushMatrix();
            m.pushMatrix();
            fail();
        } catch (IllegalStateException e) {
            // Must reach here!
            e.hashCode(); // <- use e somehow
        }
    }

    public static void testPopTooFar() {
        try {
            Matrix4fStack m = new Matrix4fStack(2);
            m.pushMatrix();
            m.popMatrix();
            m.popMatrix();
            fail();
        } catch (IllegalStateException e) {
            // Must reach here!
            e.hashCode(); // <- use 'e' somehow
        }
    }

    public static void testEquals() {
        Matrix4fStack s1 = new Matrix4fStack(3);
        Matrix4fStack s2 = new Matrix4fStack(1);
        Matrix4fStack s3 = new Matrix4fStack(2);
        Matrix4fStack s4 = new Matrix4fStack(2);
        s4.scale(2);
        Matrix4fStack s5 = new Matrix4fStack(2);
        s5.pushMatrix();
        Matrix4fStack s6 = new Matrix4fStack(2);
        s6.pushMatrix();
        s6.scale(2);
        s6.popMatrix();
        Matrix4f m1 = new Matrix4f();
        Matrix4f m2 = new Matrix4f().scale(2);
        
        // Matrix4fStack.equals(Matrix4f) only compares the 16 matrix elements
        assertTrue(s1.equals(m1));
        assertTrue(s2.equals(m1));
        assertFalse(s1.equals(m2));
        assertTrue(s4.equals(m2));

        // Matrix4fStack.equals(Matrix4fStack) compares the 16 matrix elements
        // and all matrices from the bottom to the current/top of the stack.
        assertTrue(s1.equals(s2));
        assertTrue(s1.equals(s3));
        assertFalse(s1.equals(s4));
        assertFalse(s3.equals(s5));
        assertTrue(s3.equals(s6));
    }

}
