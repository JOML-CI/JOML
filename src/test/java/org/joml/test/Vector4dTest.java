/*
 * The MIT License
 *
 * Copyright (c) 2015-2022 JOML.
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

import org.joml.*;
import org.junit.jupiter.api.Test;

import static org.joml.test.TestUtil.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for {@link Vector4d}.
 * @author Sebastian Fellner
 */
class Vector4dTest {
    @Test
    void testAngleVector4dVector4d() {
        Vector4d testVec1 = new Vector4d(2, -9.37, 5.892, -12.5);
        Vector4d testVec2 = new Vector4d();
        
        // angle(v, v) should give 0
        double angle = testVec1.angle(testVec1);
        assertEquals(0, angle, MANY_OPS_AROUND_ZERO_PRECISION_DOUBLE);
        
        // angle(v, -v) should give Math.PI
        testVec1.negate(testVec2);
        angle = testVec1.angle(testVec2);
        assertEquals(java.lang.Math.PI, angle, MANY_OPS_AROUND_ZERO_PRECISION_DOUBLE);
    }

    @Test
    void testMulByScalarIntoDest() {
        Vector4d testVec1 = new Vector4d(2, 2, 2, 2);
        Vector4d destVec = new Vector4d(0, 0, 0, 0);

        testVec1.mul(2, destVec);
        assertEquals(new Vector4d(4, 4, 4, 4), destVec);
        assertEquals(new Vector4d(2, 2, 2, 2), testVec1);
    }

    @Test
    void testDivByScalarIntoDest() {
        Vector4d testVec1 = new Vector4d(2, 2, 2, 2);
        Vector4d destVec = new Vector4d(0, 0, 0, 0);

        testVec1.div(2, destVec);
        assertEquals(new Vector4d(1, 1, 1, 1), destVec);
        assertEquals(new Vector4d(2, 2, 2, 2), testVec1);
    }
}
