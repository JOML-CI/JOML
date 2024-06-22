/*
 * The MIT License
 *
 * Copyright (c) 2015-2024 JOML.
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
 * Test class for {@link Vector2f}.
 * @author Sebastian Fellner
 */
class Vector2fTest {
    @Test
    void testAngleVector2fVector2f() {
        Vector2f testVec1 = new Vector2f(-9.37f, 5.892f);
        Vector2f testVec2 = new Vector2f();
        
        // angle(v, v) should give 0
        float angle = testVec1.angle(testVec1);
        assertEquals(0, angle, MANY_OPS_AROUND_ZERO_PRECISION_FLOAT);
        
        // angle(v, -v) should give Math.PI
        testVec1.negate(testVec2);
        angle = testVec1.angle(testVec2);
        assertEquals(java.lang.Math.PI, angle, MANY_OPS_AROUND_ZERO_PRECISION_FLOAT);
    }

    @Test
    void testPerpendicular(){
        Vector2f testVec1 = new Vector2f(-9.37f, 5.892f);
        assertVector2fEquals(new Vector2f(testVec1).perpendicular(),new Vector2f(5.892f,9.37f),0.000001f);
    }
}
