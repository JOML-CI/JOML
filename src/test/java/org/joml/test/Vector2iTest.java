/*
 * The MIT License
 *
 * Copyright (c) 2015-2023 JOML.
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
import org.joml.Vector2i;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for {@link Vector2i}.
 */
class Vector2iTest {
    @Test
    void testVector3iRounding() {
        Vector2i v1 = new Vector2i(0.0f,.6f, RoundingMode.FLOOR);
        Vector2i v2 = new Vector2i(9.5f,1.6f, RoundingMode.FLOOR);

        Vector2i v3 = new Vector2i(new Vector2f(0.0f,.6f), RoundingMode.FLOOR);
        Vector2i v4 = new Vector2i(new Vector2d(9.5f,1.6f), RoundingMode.FLOOR);

        Vector2i v5 = new Vector2i(0.0f,.6f, RoundingMode.CEILING);
        Vector2i v6 = new Vector2i(9.5f,1.6f, RoundingMode.CEILING);

        Vector2i v7 = new Vector2i(new Vector2f(0.0f,.6f), RoundingMode.CEILING);
        Vector2i v8 = new Vector2i(new Vector2d(9.5f,1.6f), RoundingMode.CEILING);

        assertEquals(v1, new Vector2i(0,0));
        assertEquals(v2, new Vector2i(9,1));

        assertEquals(v3, new Vector2i(0,0));
        assertEquals(v4, new Vector2i(9,1));

        assertEquals(v5, new Vector2i(0,1));
        assertEquals(v6, new Vector2i(10,2));

        assertEquals(v7, new Vector2i(0,1));
        assertEquals(v8, new Vector2i(10,2));
    }
}
