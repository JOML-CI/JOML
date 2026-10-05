/*
 * The MIT License
 *
 * Copyright (c) 2015-2026  JOML.
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

import org.joml.Vector2i;
import org.joml.Vector3i;
import org.joml.Vector4i;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class VectorGridDistanceTest {
    @Test
    void testVector2iGridDistanceLargeCoordinates() {
        Vector2i origin = new Vector2i(0, 0);
        Vector2i other = new Vector2i(1_000_000_000, 1_000_000_000);
        assertEquals(2_000_000_000L, origin.gridDistance(other));
        assertEquals(2_000_000_000L, origin.gridDistance(1_000_000_000, 1_000_000_000));
    }

    @Test
    void testVector2iGridDistanceAcrossIntegerRange() {
        Vector2i low = new Vector2i(Integer.MIN_VALUE, Integer.MIN_VALUE);
        Vector2i high = new Vector2i(Integer.MAX_VALUE, Integer.MAX_VALUE);
        assertEquals(8589934590L, low.gridDistance(high));
        assertEquals(8589934590L, high.gridDistance(low));
        assertEquals(8589934590L, low.gridDistance(Integer.MAX_VALUE, Integer.MAX_VALUE));
    }

    @Test
    void testVector3iGridDistanceSumBeyondIntegerRange() {
        Vector3i origin = new Vector3i(0, 0, 0);
        Vector3i other = new Vector3i(1_000_000_000, 1_000_000_000, 1_000_000_000);
        assertEquals(3_000_000_000L, origin.gridDistance(other));
        assertEquals(3_000_000_000L, origin.gridDistance(1_000_000_000, 1_000_000_000, 1_000_000_000));
    }

    @Test
    void testVector3iGridDistanceAcrossIntegerRange() {
        Vector3i low = new Vector3i(Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE);
        Vector3i high = new Vector3i(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE);
        assertEquals(12884901885L, low.gridDistance(high));
        assertEquals(12884901885L, high.gridDistance(low));
        assertEquals(12884901885L, low.gridDistance(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE));
    }

    @Test
    void testVector4iGridDistanceSumBeyondIntegerRange() {
        Vector4i origin = new Vector4i(0, 0, 0, 0);
        Vector4i other = new Vector4i(1_000_000_000, 1_000_000_000, 1_000_000_000, 1_000_000_000);
        assertEquals(4_000_000_000L, origin.gridDistance(other));
        assertEquals(4_000_000_000L, origin.gridDistance(1_000_000_000, 1_000_000_000, 1_000_000_000, 1_000_000_000));
    }

    @Test
    void testVector4iGridDistanceAcrossIntegerRange() {
        Vector4i low = new Vector4i(Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE);
        Vector4i high = new Vector4i(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE);
        assertEquals(17179869180L, low.gridDistance(high));
        assertEquals(17179869180L, high.gridDistance(low));
        assertEquals(17179869180L, low.gridDistance(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE));
    }

}
