/*
 * The MIT License
 *
 * Copyright 2019 JOML.
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
     * Test of setRow method, of class Matrix3f.
     */
    public void testSetRow_4args() {
        System.out.println("setRow");
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

}
