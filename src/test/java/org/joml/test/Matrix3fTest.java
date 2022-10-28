/*
 * The MIT License
 *
 * Copyright (c) 2020-2022 JOML.
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
 *
 * @author mhameed
 */
class Matrix3fTest {
    @Test
    void testSetRow_4args() {
        int row = 0;
        float x = 0.0F;
        float y = 1.0F;
        float z = 2.0F;
        Matrix3f instance = new Matrix3f();
        Vector3f inRow = new Vector3f(x, y, z);
        Vector3f outRow = new Vector3f();
        Matrix3f result = instance.setRow(row, x, y, z);
        result.getRow(row, outRow);
        assertEquals(inRow, outRow);
    }

    @Test
    void testMatrix3fTranspose() {
        float m00 = 1, m01 = 2, m02 = 3;
        float m10 = 5, m11 = 6, m12 = 7;
        float m20 = 9, m21 = 10, m22 = 11;

        Matrix3f m = new Matrix3f(m00, m01, m02, m10, m11, m12, m20, m21, m22);
        Matrix3f expect = new Matrix3f(m00, m10, m20, m01, m11, m21, m02, m12, m22);
        TestUtil.assertMatrix3fEquals(new Matrix3f(m).transpose(), expect, 1E-5f);
        TestUtil.assertMatrix3fEquals(new Matrix3f(m).transpose(new Matrix3f()), expect, 1E-5f);
    }

    @Test
    void testInvert() {
        Matrix3f invm = new Matrix3f();
        Matrix3f m = new Matrix3f();
        m.rotateXYZ(0.23f, 1.523f, -0.7234f).invert(invm);
        Vector3f orig = new Vector3f(4, -6, 8);
        Vector3f v = new Vector3f();
        Vector3f w = new Vector3f();
        m.transform(orig, v);
        invm.transform(v, w);
        TestUtil.assertVector3fEquals(orig, w, 1E-4f);
        invm.invert();
        TestUtil.assertMatrix3fEquals(m, invm, 1E-3f);
    }

    @Test
    void testGet() {
        Matrix3f m = new Matrix3f(1, 2, 3, 4, 5, 6, 7, 8, 9);
        for (int c = 0; c < 3; c++)
            for (int r = 0; r < 3; r++)
                assertEquals(c*3+r+1, m.get(c, r), 0);
    }

    @Test
    void testSet() {
        TestUtil.assertMatrix3fEquals(new Matrix3f().zero().set(0, 0, 3), new Matrix3f(3, 0, 0, 0, 0, 0, 0, 0, 0), 0);
        TestUtil.assertMatrix3fEquals(new Matrix3f().zero().set(0, 1, 3), new Matrix3f(0, 3, 0, 0, 0, 0, 0, 0, 0), 0);
        TestUtil.assertMatrix3fEquals(new Matrix3f().zero().set(0, 2, 3), new Matrix3f(0, 0, 3, 0, 0, 0, 0, 0, 0), 0);
        TestUtil.assertMatrix3fEquals(new Matrix3f().zero().set(1, 0, 3), new Matrix3f(0, 0, 0, 3, 0, 0, 0, 0, 0), 0);
        TestUtil.assertMatrix3fEquals(new Matrix3f().zero().set(1, 1, 3), new Matrix3f(0, 0, 0, 0, 3, 0, 0, 0, 0), 0);
        TestUtil.assertMatrix3fEquals(new Matrix3f().zero().set(1, 2, 3), new Matrix3f(0, 0, 0, 0, 0, 3, 0, 0, 0), 0);
        TestUtil.assertMatrix3fEquals(new Matrix3f().zero().set(2, 0, 3), new Matrix3f(0, 0, 0, 0, 0, 0, 3, 0, 0), 0);
        TestUtil.assertMatrix3fEquals(new Matrix3f().zero().set(2, 1, 3), new Matrix3f(0, 0, 0, 0, 0, 0, 0, 3, 0), 0);
        TestUtil.assertMatrix3fEquals(new Matrix3f().zero().set(2, 2, 3), new Matrix3f(0, 0, 0, 0, 0, 0, 0, 0, 3), 0);
    }
}
