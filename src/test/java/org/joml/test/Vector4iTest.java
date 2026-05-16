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
import org.joml.Vector4f;
import org.joml.Vector4i;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for {@link Vector4i}.
 */
class Vector4iTest {

    @Test
    void testVector4iEqualsWithVector4i() {
        final Vector4i vector1 = new Vector4i(3, 5, 1, 7);
        assertEquals(new Vector4i(3, 5, 1, 7), vector1);
        assertNotEquals(new Vector4i(-3, 5, 1, 7), vector1);
        assertNotEquals(new Vector4i(3, -5, 1, 7), vector1);
        assertNotEquals(new Vector4i(3, 5, -1, 7), vector1);
        assertNotEquals(new Vector4i(3, 5, 1, -7), vector1);
        assertEquals(vector1, vector1);
        assertFalse(vector1.equals(null));
        assertFalse(vector1.equals(new Vector4f(3, 5, 1, 7)));
        assertFalse(vector1.equals(new Vector2i(3, 5)));
    }

    @Test
    void testVector4iEqualsWithCoordinates() {
        final Vector4i vector1 = new Vector4i(3, 5, 1, 7);
        assertTrue(vector1.equals(3, 5, 1, 7));
        assertFalse(vector1.equals(-3, 5, 1, 7));
        assertFalse(vector1.equals(3, -5, 1, 7));
        assertFalse(vector1.equals(3, 5, -1, 7));
        assertFalse(vector1.equals(3, 5, 1, -7));
    }

    @Test
    void testVector4iGetComponentByParameter() {
        final Vector4i vector1 = new Vector4i(3, 5, 1, 10);
        assertEquals(3, vector1.get(0));
        assertEquals(5, vector1.get(1));
        assertEquals(1, vector1.get(2));
        assertEquals(10, vector1.get(3));
        assertThrows(IllegalArgumentException.class, () -> vector1.get(4));
    }

    @Test
    void testVector4iAbsoluteMinComponent() {
        assertEquals(0, new Vector4i(5, -15, 16, 17).minComponent());
        assertEquals(1, new Vector4i(-35, 2, 8, 9).minComponent());
        assertEquals(2, new Vector4i(-35, 20, 6, 9).minComponent());
        assertEquals(3, new Vector4i(-35, 20, 30, 4).minComponent());
        assertEquals(2, new Vector4i(1, 5, 0, 7).minComponent());
        assertEquals(3, new Vector4i(1, 5, 6, 0).minComponent());
    }

    @Test
    void testVector4iAbsoluteMaxComponent() {
        assertEquals(0, new Vector4i(35, -15, 16, 17).maxComponent());
        assertEquals(1, new Vector4i(5, -42, 8, 9).maxComponent());
        assertEquals(2, new Vector4i(-35, 20, 60, 9).maxComponent());
        assertEquals(3, new Vector4i(-35, 20, 30, -80).maxComponent());
        assertEquals(3, new Vector4i(1, 10, 5, 20).maxComponent());
    }

    @Test
    void testVector4iSetComponentByParameter() {
        final Vector4i vector1 = new Vector4i(2, 3, 4, 5);
        assertEquals(new Vector4i(10, 3, 4, 5), vector1.setComponent(0, 10));
        assertEquals(new Vector4i(10, -6, 4, 5), vector1.setComponent(1, -6));
        assertEquals(new Vector4i(10, -6, 5, 5), vector1.setComponent(2, 5));
        assertEquals(new Vector4i(10, -6, 5, 11), vector1.setComponent(3, 11));
        assertThrows(IllegalArgumentException.class, () -> vector1.setComponent(4, 99));
    }
}