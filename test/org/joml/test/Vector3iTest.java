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
import org.joml.RoundingMode;
import org.joml.Vector2d;
import org.joml.Vector2f;
import org.joml.Vector3d;
import org.joml.Vector3f;
import org.joml.Vector3i;

/**
 * Test class for {@link Vector3i}.
 */
public class Vector3iTest extends TestCase {
    public static void testVector3iRounding() {
        Vector3i v1 = new Vector3i(0.0f,.6f,.7f, RoundingMode.FLOOR);
        Vector3i v2 = new Vector3i(9.5f,1.6f,5.0f, RoundingMode.FLOOR);
        Vector3i v3 = new Vector3i(new Vector3f(9.5f,1.6f,5.0f), RoundingMode.FLOOR);
        Vector3i v4 = new Vector3i(new Vector3d(9.5f,1.6f,5.0f), RoundingMode.FLOOR);

        assertEquals(v1, new Vector3i(0,0,0));
        assertEquals(v2, new Vector3i(9,1,5));
        assertEquals(v3, new Vector3i(0,0,0));
        assertEquals(v4, new Vector3i(9,1,5));
    }

    public static void testVector3iRoundingVector2() {
        Vector3i v1 = new Vector3i(new Vector2f(0.0f,.6f),.7f, RoundingMode.FLOOR);
        Vector3i v2 = new Vector3i(new Vector2f(9.5f,1.6f),5.0f, RoundingMode.FLOOR);
        Vector3i v3 = new Vector3i(new Vector2d(0.0f,.6f),.7f, RoundingMode.FLOOR);
        Vector3i v4 = new Vector3i(new Vector2d(9.5f,1.6f),5.0f, RoundingMode.FLOOR);

        assertEquals(v1, new Vector3i(0,0,0));
        assertEquals(v2, new Vector3i(9,1,5));
        assertEquals(v3, new Vector3i(0,0,0));
        assertEquals(v4, new Vector3i(9,1,5));
    }
}
