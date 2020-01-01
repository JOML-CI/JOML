/*
 * The MIT License
 *
 * Copyright (c) 2015-2020 JOML.
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

/**
 * Test class for {@link Vector4f}.
 * @author Sebastian Fellner
 */
public class Vector4fTest extends TestCase {
    public static void testAngleVector4fVector4f() {
        Vector4f testVec1 = new Vector4f(2f, -9.37f, 5.892f, -12.5f);
        Vector4f testVec2 = new Vector4f();
        
        // angle(v, v) should give 0
        float angle = testVec1.angle(testVec1);
        assertEquals(0, angle, TestUtil.MANY_OPS_AROUND_ZERO_PRECISION_FLOAT);
        
        // angle(v, -v) should give Math.PI
        testVec1.negate(testVec2);
        angle = testVec1.angle(testVec2);
        assertEquals(java.lang.Math.PI, angle, TestUtil.MANY_OPS_AROUND_ZERO_PRECISION_FLOAT);
    }
}
