/*
 * The MIT License
 *
 * Copyright (c) 2020-2021 JOML.
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
import org.joml.Matrix3d;
import org.joml.Vector3d;

/**
 *
 * @author mhameed
 */
public class Matrix3dTest extends TestCase {

    /**
     * Test of setRow method, of class Matrix3d.
     */
    public void testSetRow_4args() {
        int row = 0;
        float x = 0.0F;
        float y = 1.0F;
        float z = 2.0F;
        Matrix3d instance = new Matrix3d();
        Vector3d inRow = new Vector3d(x, y, z);
        Vector3d outRow = new Vector3d();
        Matrix3d result = instance.setRow(row, x, y, z);
        result.getRow(row, outRow);
        assertEquals(inRow, outRow);
    }

    public static void testGet() {
        Matrix3d m = new Matrix3d(1, 2, 3, 4, 5, 6, 7, 8, 9);
        for (int c = 0; c < 3; c++)
            for (int r = 0; r < 3; r++)
                assertEquals(c*3+r+1, m.get(c, r), 0);
    }

    public static void testSet() {
        TestUtil.assertMatrix3dEquals(new Matrix3d().zero().set(0, 0, 3), new Matrix3d(3, 0, 0, 0, 0, 0, 0, 0, 0), 0);
        TestUtil.assertMatrix3dEquals(new Matrix3d().zero().set(0, 1, 3), new Matrix3d(0, 3, 0, 0, 0, 0, 0, 0, 0), 0);
        TestUtil.assertMatrix3dEquals(new Matrix3d().zero().set(0, 2, 3), new Matrix3d(0, 0, 3, 0, 0, 0, 0, 0, 0), 0);
        TestUtil.assertMatrix3dEquals(new Matrix3d().zero().set(1, 0, 3), new Matrix3d(0, 0, 0, 3, 0, 0, 0, 0, 0), 0);
        TestUtil.assertMatrix3dEquals(new Matrix3d().zero().set(1, 1, 3), new Matrix3d(0, 0, 0, 0, 3, 0, 0, 0, 0), 0);
        TestUtil.assertMatrix3dEquals(new Matrix3d().zero().set(1, 2, 3), new Matrix3d(0, 0, 0, 0, 0, 3, 0, 0, 0), 0);
        TestUtil.assertMatrix3dEquals(new Matrix3d().zero().set(2, 0, 3), new Matrix3d(0, 0, 0, 0, 0, 0, 3, 0, 0), 0);
        TestUtil.assertMatrix3dEquals(new Matrix3d().zero().set(2, 1, 3), new Matrix3d(0, 0, 0, 0, 0, 0, 0, 3, 0), 0);
        TestUtil.assertMatrix3dEquals(new Matrix3d().zero().set(2, 2, 3), new Matrix3d(0, 0, 0, 0, 0, 0, 0, 0, 3), 0);
    }

}
