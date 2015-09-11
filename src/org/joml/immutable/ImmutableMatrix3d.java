/*
 * (C) Copyright 2015 Kai Burjack

 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in
 all copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 THE SOFTWARE.

 */
package org.joml.immutable;

import org.joml.Matrix3d;
import org.joml.Matrix3dr;

/**
 * Contains the definition of an immutable 3x3 Matrix of doubles. The matrix is column-major to match OpenGL's interpretation, and it looks like this:
 * <p>
 * m00  m10  m20<br>
 * m01  m11  m21<br>
 * m02  m12  m22<br>
 * </p>
 */
public class ImmutableMatrix3d extends Matrix3dr {

    public final double m00, m10, m20;
    public final double m01, m11, m21;
    public final double m02, m12, m22;

    /**
     * Create a new {@link Matrix3dr} and initialize it with the values from the given matrix.
     *
     * @param mat the matrix to initialize this matrix with
     */
    public ImmutableMatrix3d(Matrix3dr mat) {
        m00 = mat.m00();
        m01 = mat.m01();
        m02 = mat.m02();
        m10 = mat.m10();
        m11 = mat.m11();
        m12 = mat.m12();
        m20 = mat.m20();
        m21 = mat.m21();
        m22 = mat.m22();
    }

    /**
     * Create a new {@link Matrix3d} and initialize its elements with the given values.
     *
     * @param m00 the value of m00
     * @param m01 the value of m01
     * @param m02 the value of m02
     * @param m10 the value of m10
     * @param m11 the value of m11
     * @param m12 the value of m12
     * @param m20 the value of m20
     * @param m21 the value of m21
     * @param m22 the value of m22
     */
    public ImmutableMatrix3d(double m00, double m01, double m02,
                             double m10, double m11, double m12,
                             double m20, double m21, double m22) {
        this.m00 = m00;
        this.m01 = m01;
        this.m02 = m02;
        this.m10 = m10;
        this.m11 = m11;
        this.m12 = m12;
        this.m20 = m20;
        this.m21 = m21;
        this.m22 = m22;
    }

    public double m00() {
        return m00;
    }

    public double m10() {
        return m10;
    }

    public double m20() {
        return m20;
    }

    public double m01() {
        return m01;
    }

    public double m11() {
        return m11;
    }

    public double m21() {
        return m21;
    }

    public double m02() {
        return m02;
    }

    public double m12() {
        return m12;
    }

    public double m22() {
        return m22;
    }
}
