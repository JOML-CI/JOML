/*
 * The MIT License
 *
 * Copyright (c) 2018-2020 JOML.
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

import org.joml.*;
import org.joml.Math;

/**
 * Tests for Math.vecLength - addressing <a href="https://github.com/JOML-CI/JOML/issues/131">Issue #131</a>
 *
 * @author F. Neurath
 */
public class MathTest extends TestCase {

    public static void testClamp(){
        // Integer value tests
        assertEquals(Math.clamp(10,20,0),10);
        assertEquals(Math.clamp(10,20,12),12);
        assertEquals(Math.clamp(10,20,30),20);

        // Double value tests
        assertEquals(Math.clamp(10f,20f,0f),10f,.0001f);
        assertEquals(Math.clamp(10f,20f,12f),12f,.0001f);
        assertEquals(Math.clamp(10f,20f,30f),20f,.0001f);

        // Float value tests
        assertEquals(Math.clamp(10.0,20.0,0.0),10.0,.0001);
        assertEquals(Math.clamp(10.0,20.0,12.0),12.0,.0001);
        assertEquals(Math.clamp(10.0,20.0,30.0),20.0,.0001);
    }

    public static void testDoubleVecLength() {
        // Integer value tests
        assertEquals(5., Vector2d.length(4, 3), .0001);
        assertEquals(6., Vector3d.length(2, -4, 4), .0001);
        assertEquals(3., Vector4d.length(2, -1, 0, -2), .0001);

        // Floating point value tests
        assertEquals(Math.sqrt(.41), Vector2d.length(.4, -.5), .0001);
        assertEquals(Math.sqrt(.3), Vector3d.length(.1, -.5, .2), .0001);
        assertEquals(1., Vector4d.length(.5, .5, .5, .5), .0001);
    }

    public static void testFloatVecLength() {
        // Integer value tests
        assertEquals(5.f, Vector2f.length(4f, 3f), .0001f);
        assertEquals(6.f, Vector3f.length(2f, -4f, 4f), .0001f);
        assertEquals(3.f, Vector4f.length(2f, -1f, 0f, -2f), .0001f);

        // Floating point value tests
        assertEquals((float) Math.sqrt(.41f), Vector2f.length(.4f, -.5f), .0001f);
        assertEquals((float) Math.sqrt(.3f), Vector3f.length(.1f, -.5f, .2f), .0001f);
        assertEquals(1.f, Vector4f.length(.5f, .5f, .5f, .5f), .0001f);
    }

    public static void testRoundUsing() {
        // TRUNCATE
        assertEquals(0, Math.roundUsing(0.2f, RoundingMode.TRUNCATE));
        assertEquals(0, Math.roundUsing(0.5f, RoundingMode.TRUNCATE));
        assertEquals(0, Math.roundUsing(0.9f, RoundingMode.TRUNCATE));
        assertEquals(1, Math.roundUsing(1.0f, RoundingMode.TRUNCATE));
        assertEquals(0, Math.roundUsing(-0.2f, RoundingMode.TRUNCATE));
        assertEquals(0, Math.roundUsing(-0.5f, RoundingMode.TRUNCATE));
        assertEquals(0, Math.roundUsing(-0.9f, RoundingMode.TRUNCATE));
        assertEquals(-1, Math.roundUsing(-1.0f, RoundingMode.TRUNCATE));
        // CEILING
        assertEquals(1, Math.roundUsing(0.2f, RoundingMode.CEILING));
        assertEquals(1, Math.roundUsing(0.5f, RoundingMode.CEILING));
        assertEquals(1, Math.roundUsing(0.9f, RoundingMode.CEILING));
        assertEquals(1, Math.roundUsing(1.0f, RoundingMode.CEILING));
        assertEquals(0, Math.roundUsing(-0.2f, RoundingMode.CEILING));
        assertEquals(0, Math.roundUsing(-0.5f, RoundingMode.CEILING));
        assertEquals(0, Math.roundUsing(-0.9f, RoundingMode.CEILING));
        assertEquals(-1, Math.roundUsing(-1.0f, RoundingMode.CEILING));
        // FLOOR
        assertEquals(0, Math.roundUsing(0.2f, RoundingMode.FLOOR));
        assertEquals(0, Math.roundUsing(0.5f, RoundingMode.FLOOR));
        assertEquals(0, Math.roundUsing(0.9f, RoundingMode.FLOOR));
        assertEquals(1, Math.roundUsing(1.0f, RoundingMode.FLOOR));
        assertEquals(-1, Math.roundUsing(-0.2f, RoundingMode.FLOOR));
        assertEquals(-1, Math.roundUsing(-0.5f, RoundingMode.FLOOR));
        assertEquals(-1, Math.roundUsing(-0.9f, RoundingMode.FLOOR));
        assertEquals(-1, Math.roundUsing(-1.0f, RoundingMode.FLOOR));
        // HALF_DOWN
        assertEquals(0, Math.roundUsing(0.2f, RoundingMode.HALF_DOWN));
        assertEquals(0, Math.roundUsing(0.5f, RoundingMode.HALF_DOWN));
        assertEquals(1, Math.roundUsing(0.9f, RoundingMode.HALF_DOWN));
        assertEquals(1, Math.roundUsing(1.0f, RoundingMode.HALF_DOWN));
        assertEquals(0, Math.roundUsing(-0.2f, RoundingMode.HALF_DOWN));
        assertEquals(0, Math.roundUsing(-0.5f, RoundingMode.HALF_DOWN));
        assertEquals(-1, Math.roundUsing(-0.9f, RoundingMode.HALF_DOWN));
        assertEquals(-1, Math.roundUsing(-1.0f, RoundingMode.HALF_DOWN));
        // HALF_UP
        assertEquals(0, Math.roundUsing(0.2f, RoundingMode.HALF_UP));
        assertEquals(1, Math.roundUsing(0.5f, RoundingMode.HALF_UP));
        assertEquals(1, Math.roundUsing(0.9f, RoundingMode.HALF_UP));
        assertEquals(1, Math.roundUsing(1.0f, RoundingMode.HALF_UP));
        assertEquals(0, Math.roundUsing(-0.2f, RoundingMode.HALF_UP));
        assertEquals(-1, Math.roundUsing(-0.5f, RoundingMode.HALF_UP));
        assertEquals(-1, Math.roundUsing(-0.9f, RoundingMode.HALF_UP));
        assertEquals(-1, Math.roundUsing(-1.0f, RoundingMode.HALF_UP));
        // HALF_EVEN
        assertEquals(0, Math.roundUsing(0.2f, RoundingMode.HALF_EVEN));
        assertEquals(0, Math.roundUsing(0.5f, RoundingMode.HALF_EVEN));
        assertEquals(1, Math.roundUsing(0.9f, RoundingMode.HALF_EVEN));
        assertEquals(1, Math.roundUsing(1.0f, RoundingMode.HALF_EVEN));
        assertEquals(0, Math.roundUsing(-0.2f, RoundingMode.HALF_EVEN));
        assertEquals(0, Math.roundUsing(-0.5f, RoundingMode.HALF_EVEN));
        assertEquals(-1, Math.roundUsing(-0.9f, RoundingMode.HALF_EVEN));
        assertEquals(-1, Math.roundUsing(-1.0f, RoundingMode.HALF_EVEN));
    }
}
