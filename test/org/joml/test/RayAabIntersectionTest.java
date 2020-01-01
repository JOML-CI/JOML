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

import org.joml.RayAabIntersection;

import junit.framework.TestCase;

/**
 * Tests for the {@link RayAabIntersection}.
 * 
 * @author Kai Burjack
 */
public class RayAabIntersectionTest extends TestCase {

    public static void testPX() {
        RayAabIntersection r = new RayAabIntersection();
        r.set(-1, 0, 0, 1, 0, 0);
        assertTrue(r.test(-0.5f, -0.5f, -0.5f, 0.5f, 0.5f, 0.5f));
    }

    public static void testPY() {
        RayAabIntersection r = new RayAabIntersection();
        r.set(0, -1, 0, 0, 1, 0);
        assertTrue(r.test(-0.5f, -0.5f, -0.5f, 0.5f, 0.5f, 0.5f));
    }

    public static void testPZ() {
        RayAabIntersection r = new RayAabIntersection();
        r.set(0, 0, -1, 0, 0, 1);
        assertTrue(r.test(-0.5f, -0.5f, -0.5f, 0.5f, 0.5f, 0.5f));
    }

    public static void testNX() {
        RayAabIntersection r = new RayAabIntersection();
        r.set(1, 0, 0, -1, 0, 0);
        assertTrue(r.test(-0.5f, -0.5f, -0.5f, 0.5f, 0.5f, 0.5f));
    }

    public static void testNY() {
        RayAabIntersection r = new RayAabIntersection();
        r.set(0, 1, 0, 0, -1, 0);
        assertTrue(r.test(-0.5f, -0.5f, -0.5f, 0.5f, 0.5f, 0.5f));
    }

    public static void testNZ() {
        RayAabIntersection r = new RayAabIntersection();
        r.set(0, 0, 1, 0, 0, -1);
        assertTrue(r.test(-0.5f, -0.5f, -0.5f, 0.5f, 0.5f, 0.5f));
    }

    public static void testPXPY() {
        RayAabIntersection r = new RayAabIntersection();
        r.set(-1, -1, 0, 1, 1, 0);
        assertTrue(r.test(-0.5f, -0.5f, -0.5f, 0.5f, 0.5f, 0.5f));
    }

    public static void testPXEdge() {
        RayAabIntersection r = new RayAabIntersection();
        r.set(-1, 0.5f, 0, 1, 0, 0);
        assertTrue(r.test(-0.5f, -0.5f, -0.5f, 0.5f, 0.5f, 0.5f));
    }

    public static void testPXEdgeDelta() {
        RayAabIntersection r = new RayAabIntersection();
        r.set(-1, 0.500001f, 0, 1, 0, 0);
        assertFalse(r.test(-0.5f, -0.5f, -0.5f, 0.5f, 0.5f, 0.5f));
    }

    public static void testNXEdge() {
        RayAabIntersection r = new RayAabIntersection();
        r.set(-1, -0.5f, 0, 1, 0, 0);
        assertTrue(r.test(-0.5f, -0.5f, -0.5f, 0.5f, 0.5f, 0.5f));
    }

    public static void testNXEdgeDelta() {
        RayAabIntersection r = new RayAabIntersection();
        r.set(-1, -0.500001f, 0, 1, 0, 0);
        assertFalse(r.test(-0.5f, -0.5f, -0.5f, 0.5f, 0.5f, 0.5f));
    }

}
