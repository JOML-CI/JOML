/*
 * The MIT License
 *
 * Copyright (c) 2015-2019 JOML.
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

import org.joml.AxisAngle4f;
import org.joml.Math;

/**
 * Tests for the {@link AxisAngle4f} class.
 * 
 * @author Kai Burjack
 */
public class AxisAngle4fTest extends TestCase {

    public static void testAngleNormalization() {
        AxisAngle4f a1 = new AxisAngle4f((float) Math.toRadians(20), 1.0f, 0.0f, 0.0f);
        AxisAngle4f a2 = new AxisAngle4f((float) Math.toRadians(380), 1.0f, 0.0f, 0.0f);
        assertEquals(a1.angle, a2.angle, 1E-5f);

        a1 = new AxisAngle4f((float) Math.toRadians(-20), 1.0f, 0.0f, 0.0f);
        a2 = new AxisAngle4f((float) Math.toRadians(-380.0f), 1.0f, 0.0f, 0.0f);
        assertEquals(a1.angle, a2.angle, 1E-5f);

        a1 = new AxisAngle4f((float) Math.toRadians(-20.0f) * 10.0f, 1.0f, 0.0f, 0.0f);
        a2 = new AxisAngle4f((float) Math.toRadians(-380.0f) * 10.0f, 1.0f, 0.0f, 0.0f);
        assertEquals(a1.angle, a2.angle, 1E-5f);
    }

}
