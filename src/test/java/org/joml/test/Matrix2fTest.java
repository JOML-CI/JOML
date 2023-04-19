/*
 * The MIT License
 *
 * Copyright (c) 2015-2023 Richard Greenlees
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

import org.joml.Matrix2f;
import org.joml.Vector2f;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Matrix2fTest {
    @Test
    void testMul() {
        assertTrue(new Matrix2f(87, 124, 129, 184).equals(new Matrix2f(2, 3, 5, 7).mul(new Matrix2f(11, 13, 17, 19)), 0.001f));
    }

    @Test
    void testMulLocal() {
        assertTrue(new Matrix2f(87, 124, 129, 184).equals(new Matrix2f(11, 13, 17, 19).mulLocal(new Matrix2f(2, 3, 5, 7)), 0.001f));
    }

    @Test
    void testDeterminant() {
        assertEquals(-1f, new Matrix2f(2, 3, 5, 7).determinant());
    }

    @Test
    void testInvert() {
        assertTrue(new Matrix2f(-19f/12, 13f/12, 17f/12, -11f/12).equals(new Matrix2f(11, 13, 17, 19).invert(), 0.001f));
    }

    @Test
    void testRotation() {
        final float angle = (float)Math.PI / 4;
        Matrix2f mat = new Matrix2f().rotation(angle);
        final float coord = 1 / (float) Math.sqrt(2);
        assertTrue(new Vector2f(coord, coord).equals(mat.transform(new Vector2f(1, 0)), 0.001f));
    }

    @Test
    void testNormal() {
        assertTrue(new Matrix2f(2, 3, 5, 7).invert().transpose().equals(new Matrix2f(2, 3, 5, 7).normal(), 0.001f));
    }

    @Test
    void testPositiveX() {
        Matrix2f inv = new Matrix2f(2, 3, 5, 7).invert();
        Vector2f expected = inv.transform(new Vector2f(1, 0)).normalize();
        assertTrue(expected.equals(new Matrix2f(2, 3, 5, 7).positiveX(new Vector2f()), 0.001f));
    }

    @Test
    void testPositiveY() {
        Matrix2f inv = new Matrix2f(11, 13, 17, 19).invert();
        Vector2f expected = inv.transform(new Vector2f(0, 1)).normalize();
        assertTrue(expected.equals(new Matrix2f(11, 13, 17, 19).positiveY(new Vector2f()), 0.001f));
    }
}
