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

import org.joml.RoundingMode;
import org.joml.Vector2d;
import org.joml.Vector2f;
import org.joml.Vector3d;
import org.joml.Vector3f;
import org.joml.Vector3i;
import org.joml.Vector3ic;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for {@link Vector3i}.
 */
class Vector3iTest {
    static final Vector3ic v1 = new Vector3i(1, 3, 7);
    static final Vector3ic v2 = new Vector3i(2, 6, 14);
    static final Vector3ic v3 = new Vector3i(3, 9, 21);

    @Test
    void testVector3iRounding() {
        Vector3i v1 = new Vector3i(0.0f, .6f, .7f, RoundingMode.FLOOR);
        Vector3i v2 = new Vector3i(9.5f, 1.6f, 5.0f, RoundingMode.FLOOR);

        Vector3i v3 = new Vector3i(new Vector3f(0.0f, .6f, .7f), RoundingMode.FLOOR);
        Vector3i v4 = new Vector3i(new Vector3d(9.5f, 1.6f, 5.0f), RoundingMode.FLOOR);

        Vector3i v5 = new Vector3i(0.0f, .6f, .7f, RoundingMode.CEILING);
        Vector3i v6 = new Vector3i(9.5f, 1.6f, 5.0f, RoundingMode.CEILING);

        Vector3i v7 = new Vector3i(new Vector3f(0.0f, .6f, .7f), RoundingMode.CEILING);
        Vector3i v8 = new Vector3i(new Vector3d(9.5f, 1.6f, 5.0f), RoundingMode.CEILING);

        assertEquals(v1, new Vector3i(0, 0, 0));
        assertEquals(v2, new Vector3i(9, 1, 5));

        assertEquals(v3, new Vector3i(0, 0, 0));
        assertEquals(v4, new Vector3i(9, 1, 5));

        assertEquals(v5, new Vector3i(0, 1, 1));
        assertEquals(v6, new Vector3i(10, 2, 5));

        assertEquals(v7, new Vector3i(0, 1, 1));
        assertEquals(v8, new Vector3i(10, 2, 5));
    }

    @Test
    void testVector3iRoundingVector2() {
        Vector3i v1 = new Vector3i(new Vector2f(0.0f, .6f), .7f, RoundingMode.FLOOR);
        Vector3i v2 = new Vector3i(new Vector2f(9.5f, 1.6f), 5.0f, RoundingMode.FLOOR);

        Vector3i v3 = new Vector3i(new Vector2d(0.0f, .6f), .7f, RoundingMode.FLOOR);
        Vector3i v4 = new Vector3i(new Vector2d(9.5f, 1.6f), 5.0f, RoundingMode.FLOOR);

        Vector3i v5 = new Vector3i(new Vector2f(0.0f, .6f), .7f, RoundingMode.CEILING);
        Vector3i v6 = new Vector3i(new Vector2d(9.5f, 1.6f), 5.0f, RoundingMode.CEILING);


        assertEquals(v1, new Vector3i(0, 0, 0));
        assertEquals(v2, new Vector3i(9, 1, 5));

        assertEquals(v3, new Vector3i(0, 0, 0));
        assertEquals(v4, new Vector3i(9, 1, 5));

        assertEquals(v5, new Vector3i(0, 1, 1));
        assertEquals(v6, new Vector3i(10, 2, 5));
    }

    @Test
    void testEmptyConstructor() {
        Vector3i v = new Vector3i();
        assertEquals(0, v.x);
        assertEquals(0, v.y);
        assertEquals(0, v.z);
    }

    @Test
    void testTripleConstructor() {
        Vector3i v = new Vector3i(1, 2, 3);
        assertEquals(1, v.x);
        assertEquals(2, v.y);
        assertEquals(3, v.z);
    }

    @Test
    void testCopyConstructor() {
        Vector3i copy = new Vector3i(v1);
        assertEquals(v1.x(), copy.x);
        assertEquals(v1.y(), copy.y);
        assertEquals(v1.z(), copy.z);
    }

    @Test
    void testEquals() {
        assertNotEquals(v1, v2);
        assertEquals(v1, new Vector3i(v1.x(), v1.y(), v1.z()));
        assertNotEquals(v1, null);
    }

    @Test
    void testAddTriple() {
        Vector3i v = new Vector3i(v1);
        v.add(v2.x(), v2.y(), v2.z());
        assertEquals(v3, v);

        v = new Vector3i(v1);
        v.add(v2);
        assertEquals(v3, v);
    }

    @Test
    void testAddToSelf() {
        Vector3i v = new Vector3i(v1);
        v.add(v);
        assertEquals(new Vector3i(2, 6, 14), v);
    }

    @Test
    void testMin() {
        Vector3i v = new Vector3i(v1);
        v.min(new Vector3i(v1.z(), v1.y(), v1.x()));
        assertEquals(Math.min(v1.x(), v1.z()), v.x);
        assertEquals(v1.y(), v.y);
        assertEquals(Math.min(v1.x(), v1.z()), v.z);
    }

    @Test
    void testMax() {
        Vector3i v = new Vector3i(v1);
        v.max(new Vector3i(v1.z(), v1.y(), v1.x()));
        assertEquals(Math.max(v1.x(), v1.z()), v.x);
        assertEquals(v1.y(), v.y);
        assertEquals(Math.max(v1.x(), v1.z()), v.z);
    }

    @Test
    void testManhattanDistance() {
        assertEquals(0, new Vector3i().gridDistance(new Vector3i()));
        assertEquals(1, new Vector3i().gridDistance(new Vector3i(1, 0, 0)));
        assertEquals(1, new Vector3i().gridDistance(new Vector3i(0, 0, 1)));
        assertEquals(1, new Vector3i().gridDistance(new Vector3i(0, 1, 0)));
        assertEquals(3, new Vector3i().gridDistance(new Vector3i(1, 1, 1)));
        assertEquals(3, new Vector3i().gridDistance(new Vector3i(1, -1, 1)));
    }
}
