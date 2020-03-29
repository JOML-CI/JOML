/*
 * The MIT License
 *
 * Copyright (c) 2020 JOML.
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

import org.joml.Rectangled;
import org.joml.Rectanglef;
import org.joml.Rectanglei;

/**
 * Tests of {@link Rectangled} scaling functions.
 */
public class RectangleScaleTest extends TestCase {

    public static void testNonUniformAnchoredScalingNoDestination () {
        Rectangled testA = new Rectangled (-1, -1, +1, +1);
        Rectanglef testB = new Rectanglef (-1, -1, +1, +1);
        Rectanglei testC = new Rectanglei (-1, -1, +1, +1);

        assertEquals (new Rectangled (-1, -1, +3, +5), testA.scale (2, 3, -1, -1));
        assertEquals (new Rectanglef (-1, -1, +3, +5), testB.scale (2, 3, -1, -1));
        assertEquals (new Rectanglei (-1, -1, +3, +5), testC.scale (2, 3, -1, -1));
    }

    public static void testNonUniformAnchoredScalingWithDestination () {
        Rectangled testASubject = new Rectangled (-1, -1, +1, +1);
        Rectangled testATarget  = new Rectangled ( 0,  0,  0,  0);

        Rectanglef testBSubject = new Rectanglef (-1, -1, +1, +1);
        Rectanglef testBTarget  = new Rectanglef ( 0,  0,  0,  0);

        Rectanglei testCSubject = new Rectanglei (-1, -1, +1, +1);
        Rectanglei testCTarget  = new Rectanglei ( 0,  0,  0,  0);

        assertEquals (new Rectangled (-1, -1, +3, +5), testASubject.scale (2, 3, -1, -1, testATarget));
        assertEquals (new Rectanglef (-1, -1, +3, +5), testBSubject.scale (2, 3, -1, -1, testBTarget));
        assertEquals (new Rectanglei (-1, -1, +3, +5), testCSubject.scale (2, 3, -1, -1, testCTarget));
    }
}
