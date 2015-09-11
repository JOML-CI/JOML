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
import org.joml.Matrix4dr;
import org.joml.Matrix4f;
import org.joml.Matrix4fr;

/**
 * Contains the immutable definition of a 4x4 Matrix of floats, and associated functions to transform
 * it. The matrix is column-major to match OpenGL's interpretation, and it looks like this:
 * <p>
 *      m00  m10  m20  m30<br>
 *      m01  m11  m21  m31<br>
 *      m02  m12  m22  m32<br>
 *      m03  m13  m23  m33<br>
 *
 */
public class ImmutableMatrix4f extends Matrix4fr {
    public final float m00, m10, m20, m30;
    public final float m01, m11, m21, m31;
    public final float m02, m12, m22, m32;
    public final float m03, m13, m23, m33;

    /**
     * Create a new {@link ImmutableMatrix4f} and set it to {@link Matrix4f#identity() identity}.
     */
    public ImmutableMatrix4f() {
        m00 = 1.0f;
        m01 = 0.0f;
        m02 = 0.0f;
        m03 = 0.0f;
        m10 = 0.0f;
        m11 = 1.0f;
        m12 = 0.0f;
        m13 = 0.0f;
        m20 = 0.0f;
        m21 = 0.0f;
        m22 = 1.0f;
        m23 = 0.0f;
        m30 = 0.0f;
        m31 = 0.0f;
        m32 = 0.0f;
        m33 = 1.0f;
    }

    /**
     * Create a new {@link ImmutableMatrix4f} by setting its uppper left 3x3 submatrix to the values of the given {@link Matrix3fr}
     * and the rest to identity.
     *
     * @param mat the {@link Matrix3fr}
     */
    public ImmutableMatrix4f(Matrix3fr mat) {
        m00 = mat.m00();
        m01 = mat.m01();
        m02 = mat.m02();
        m03 = 0;
        m10 = mat.m10();
        m11 = mat.m11();
        m12 = mat.m12();
        m13 = 0;
        m20 = mat.m20();
        m21 = mat.m21();
        m22 = mat.m22();
        m23 = 0;
        m30 = 0;
        m31 = 0;
        m32 = 0;
        m33 = 1.0f;
    }

    /**
     * Create a new {@link ImmutableMatrix4f} and make it a copy of the given matrix.
     *
     * @param mat the {@link Matrix4fr} to copy the values from
     */
    public ImmutableMatrix4f(Matrix4fr mat) {
        m00 = mat.m00();
        m01 = mat.m01();
        m02 = mat.m02();
        m03 = mat.m03();
        m10 = mat.m10();
        m11 = mat.m11();
        m12 = mat.m12();
        m13 = mat.m13();
        m20 = mat.m20();
        m21 = mat.m21();
        m22 = mat.m22();
        m23 = mat.m23();
        m30 = mat.m30();
        m31 = mat.m31();
        m32 = mat.m32();
        m33 = mat.m33();
    }

    /**
     * Create a new {@link Matrix4f} and make it a copy of the given matrix.
     * <p>
     * Note that due to the given {@link Matrix4dr} storing values in double-precision and the constructed {@link Matrix4f} storing them
     * in single-precision, there is the possibility of losing precision.
     *
     * @param mat the {@link Matrix4dr} to copy the values from
     */
    public ImmutableMatrix4f(Matrix4dr mat) {
        m00 = (float) mat.m00();
        m01 = (float) mat.m01();
        m02 = (float) mat.m02();
        m03 = (float) mat.m03();
        m10 = (float) mat.m10();
        m11 = (float) mat.m11();
        m12 = (float) mat.m12();
        m13 = (float) mat.m13();
        m20 = (float) mat.m20();
        m21 = (float) mat.m21();
        m22 = (float) mat.m22();
        m23 = (float) mat.m23();
        m30 = (float) mat.m30();
        m31 = (float) mat.m31();
        m32 = (float) mat.m32();
        m33 = (float) mat.m33();
    }

    /**
     * Create a new 4x4 matrix using the supplied float values.
     *
     * @param m00 the value of m00
     * @param m01 the value of m01
     * @param m02 the value of m02
     * @param m03 the value of m03
     * @param m10 the value of m10
     * @param m11 the value of m11
     * @param m12 the value of m12
     * @param m13 the value of m13
     * @param m20 the value of m20
     * @param m21 the value of m21
     * @param m22 the value of m22
     * @param m23 the value of m23
     * @param m30 the value of m30
     * @param m31 the value of m31
     * @param m32 the value of m32
     * @param m33 the value of m33
     */
    public ImmutableMatrix4f(float m00, float m01, float m02, float m03,
                             float m10, float m11, float m12, float m13,
                             float m20, float m21, float m22, float m23,
                             float m30, float m31, float m32, float m33) {
        this.m00 = m00;
        this.m01 = m01;
        this.m02 = m02;
        this.m03 = m03;
        this.m10 = m10;
        this.m11 = m11;
        this.m12 = m12;
        this.m13 = m13;
        this.m20 = m20;
        this.m21 = m21;
        this.m22 = m22;
        this.m23 = m23;
        this.m30 = m30;
        this.m31 = m31;
        this.m32 = m32;
        this.m33 = m33;
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

    public float m30() {
        return m30;
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

    public float m31() {
        return m31;
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

    public float m32() {
        return m32;
    }

    public float m03() {
        return m03;
    }

    public float m13() {
        return m13;
    }

    public float m23() {
        return m23;
    }

    public float m33() {
        return m33;
    }
}
