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

import org.joml.Matrix3fr;
import org.joml.Matrix4fr;

/**
 * Contains the immutable definition of a 3x3 Matrix of floats, and associated functions to transform
 * it. The matrix is column-major to match OpenGL's interpretation, and it looks like this:
 * <p>
 *      m00  m10  m20<br>
 *      m01  m11  m21<br>
 *      m02  m12  m22<br>
 *
 */
public class ImmutableMatrix3f extends Matrix3fr {

    public final float m00, m10, m20;
    public final float m01, m11, m21;
    public final float m02, m12, m22;

    public ImmutableMatrix3f() {
        m00 = 1.0f;
        m01 = 0.0f;
        m02 = 0.0f;
        m10 = 0.0f;
        m11 = 1.0f;
        m12 = 0.0f;
        m20 = 0.0f;
        m21 = 0.0f;
        m22 = 1.0f;
    }

    /**
     * Create a new {@link ImmutableMatrix3f} and make it a copy of the given matrix.
     *
     * @param mat the {@link Matrix3fr} to copy the values from
     */
    public ImmutableMatrix3f(Matrix3fr mat) {
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
     * Create a new {@link ImmutableMatrix3f} and make it a copy of the upper left 3x3 of the given {@link Matrix4fr}.
     *
     * @param mat the {@link Matrix4fr} to copy the values from
     */
    public ImmutableMatrix3f(Matrix4fr mat) {
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
     * Create a new 3x3 matrix using the supplied float values. The order of the parameter is column-major,
     * so the first three parameters specify the three elements of the first column.
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
    public ImmutableMatrix3f(float m00, float m01, float m02,
                             float m10, float m11, float m12,
                             float m20, float m21, float m22) {
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

    public float m00() {
        return m00;
    }

    public float m10() {
        return m10;
    }

    public float m20() {
        return m20;
    }

    public float m01() {
        return m01;
    }

    public float m11() {
        return m11;
    }

    public float m21() {
        return m21;
    }

    public float m02() {
        return m02;
    }

    public float m12() {
        return m12;
    }

    public float m22() {
        return m22;
    }
}
