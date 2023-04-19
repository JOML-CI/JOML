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

import org.joml.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the {@link GeometryUtils} class.
 *
 * @author Jarosław Piotrowski
 */
class GeometryUtilsTest {
    @Test
    void testTangents() {
        Vector3f pos1 = new Vector3f(-1.0f,  1.0f, 0.0f);
        Vector3f pos2 = new Vector3f( 1.0f, -1.0f, 0.0f);
        Vector3f pos3 = new Vector3f( 1.0f,  1.0f, 0.0f);

        Vector2f uv1 = new Vector2f(0.0f, 1.0f);
        Vector2f uv2 = new Vector2f(1.0f, 0.0f);
        Vector2f uv3 = new Vector2f(1.0f, 1.0f);

        Vector3f t = new Vector3f(1, 0, 0);
        Vector3f b = new Vector3f(0, 1, 0);

        Vector3f tangent = new Vector3f();
        Vector3f bitangent = new Vector3f();

        GeometryUtils.tangent(pos1, uv1, pos2, uv2, pos3, uv3, tangent);
        assertEquals(t, tangent);

        GeometryUtils.bitangent(pos1, uv1, pos2, uv2, pos3, uv3, bitangent);
        assertEquals(b, bitangent);

        GeometryUtils.tangentBitangent(pos1, uv1, pos2, uv2, pos3, uv3, tangent, bitangent);
        assertEquals(t, tangent);
        assertEquals(b, bitangent);
    }
}
