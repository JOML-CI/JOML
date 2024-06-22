/*
 * The MIT License
 *
 * Copyright (c) 2016-2024 JOML.
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

import org.joml.PolygonsIntersection;
import org.joml.Math;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the {@link PolygonsIntersection} class.
 * 
 * @author Kai Burjack
 */
class PolygonPointIntersectionTest {
    @Test
    void testHole() {
        // Define two rectangles, one inside the other.
        float[] verticesXY = {
                0, 0,
                3, 0,
                3, 3,
                0, 3,
                1, 1, // <- new polygon
                2, 1,
                2, 2,
                1, 2
        };
        PolygonsIntersection isect = new PolygonsIntersection(verticesXY, new int[]{4}, verticesXY.length / 2);
        // Inside outer rectangle
        assertTrue(isect.testPoint(0.1f, 0.1f));
        // Inside inner rectangle
        assertFalse(isect.testPoint(1.5f, 1.5f));
    }

    @Test
    void testMultipolygon() {
        // Define two rectangles beneath each other.
        float[] verticesXY = {
                0, 0,
                1, 0,
                1, 1,
                0, 1,
                2, 0, // <- new polygon
                3, 0,
                3, 1,
                2, 1
        };
        PolygonsIntersection isect = new PolygonsIntersection(verticesXY, new int[]{4}, verticesXY.length / 2);
        // Left rectangle
        assertTrue(isect.testPoint(0.1f, 0.1f));
        // between the two
        assertFalse(isect.testPoint(1.5f, 0.1f));
        // right rectangle
        assertTrue(isect.testPoint(2.5f, 0.1f));
    }

    @Test
    void testSimple() {
        // Define a shape that looks like a "U"
        float[] verticesXY = {
                0, 0,
                3, 0,
                3, 1,
                2, 1,
                2, 0.5f,
                1, 0.5f,
                1, 1,
                0, 1
        };
        PolygonsIntersection isect = new PolygonsIntersection(verticesXY, new int[0], verticesXY.length / 2);
        // Left part of the "U"
        assertTrue(isect.testPoint(0.1f, 0.1f));
        // top middle of the "U"
        assertFalse(isect.testPoint(1.5f, 0.8f));
        // bottom middle of the "U"
        assertTrue(isect.testPoint(1.5f, 0.2f));
        // right part of the "U"
        assertTrue(isect.testPoint(2.5f, 0.1f));
    }

    @Test
    void testBigCircle() {
        int polyN = 1024 * 256;
        float[] verticesXY = new float[polyN * 2];
        for (int i = 0; i < polyN; i++) {
            float x = (float) Math.cos(2.0 * Math.PI * i / polyN);
            float y = (float) Math.sin(2.0 * Math.PI * i / polyN);
            verticesXY[2 * i] = x;
            verticesXY[2 * i + 1] = y;
        }
        PolygonsIntersection isect = new PolygonsIntersection(verticesXY, new int[0], polyN);
        // Center
        assertTrue(isect.testPoint(0, 0));
        // Left outside
        assertFalse(isect.testPoint(-1.1f, 0));
        // Top right outside
        assertFalse(isect.testPoint(0.8f, 0.8f));
        // Top edge
        assertTrue(isect.testPoint(1.0f, 0));
        // Bottom edge <- algorithm only detects top edges as 'inside'
        assertFalse(isect.testPoint(-1.0f, 0));
    }
}
