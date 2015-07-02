/*
 * (C) Copyright 2015 Richard Greenlees

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
package org.joml;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.nio.Buffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Contains the definition of a 4x4 Matrix of doubles, and associated functions to transform
 * it. The matrix is column-major to match OpenGL's interpretation, and it looks like this:
 * <p>
 *      m00  m10  m20  m30<br>
 *      m01  m11  m21  m31<br>
 *      m02  m12  m22  m32<br>
 *      m03  m13  m23  m33<br>
 * 
 * @author Richard Greenlees
 * @author Kai Burjack
 */
public class Matrix4d implements Externalizable {

    private static final long serialVersionUID = 1L;

    public double m00;
    public double m01;
    public double m02;
    public double m03;
    public double m10;
    public double m11;
    public double m12;
    public double m13;
    public double m20;
    public double m21;
    public double m22;
    public double m23;
    public double m30;
    public double m31;
    public double m32;
    public double m33;

    /**
     * Create a new {@link Matrix4d} and set it to {@link #identity() identity}.
     */
    public Matrix4d() {
        super();
        identity();
    }

    /**
     * Create a new {@link Matrix4d} and make it a copy of the given matrix.
     * 
     * @param mat
     *          the {@link Matrix4d} to copy the values from
     */
    public Matrix4d(Matrix4d mat) {
        m00 = mat.m00;
        m01 = mat.m01;
        m02 = mat.m02;
        m03 = mat.m03;
        m10 = mat.m10;
        m11 = mat.m11;
        m12 = mat.m12;
        m13 = mat.m13;
        m20 = mat.m20;
        m21 = mat.m21;
        m22 = mat.m22;
        m23 = mat.m23;
        m30 = mat.m30;
        m31 = mat.m31;
        m32 = mat.m32;
        m33 = mat.m33;
    }

    /**
     * Create a new {@link Matrix4d} and make it a copy of the given matrix.
     * 
     * @param mat
     *          the {@link Matrix4f} to copy the values from
     */
    public Matrix4d(Matrix4f mat) {
        m00 = mat.m00;
        m01 = mat.m01;
        m02 = mat.m02;
        m03 = mat.m03;
        m10 = mat.m10;
        m11 = mat.m11;
        m12 = mat.m12;
        m13 = mat.m13;
        m20 = mat.m20;
        m21 = mat.m21;
        m22 = mat.m22;
        m23 = mat.m23;
        m30 = mat.m30;
        m31 = mat.m31;
        m32 = mat.m32;
        m33 = mat.m33;
    }
    
    /**
     * Create a new 4x4 matrix using the supplied double values.
     * 
     * @param m00
     *          the value of m00
     * @param m01
     *          the value of m01
     * @param m02
     *          the value of m02
     * @param m03
     *          the value of m03
     * @param m10
     *          the value of m10
     * @param m11
     *          the value of m11
     * @param m12
     *          the value of m12
     * @param m13
     *          the value of m13
     * @param m20
     *          the value of m20
     * @param m21
     *          the value of m21
     * @param m22
     *          the value of m22
     * @param m23
     *          the value of m23
     * @param m30
     *          the value of m30
     * @param m31
     *          the value of m31
     * @param m32
     *          the value of m32
     * @param m33
     *          the value of m33
     */
    public Matrix4d(double m00, double m01, double m02, double m03,
                    double m10, double m11, double m12, double m13, 
                    double m20, double m21, double m22, double m23, 
                    double m30, double m31, double m32, double m33) {
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
    
    /**
     * Reset this matrix to the identity.
     * 
     * @return this
     */
    public Matrix4d identity() {
        m00 = 1.0;
        m01 = 0.0;
        m02 = 0.0;
        m03 = 0.0;
        m10 = 0.0;
        m11 = 1.0;
        m12 = 0.0;
        m13 = 0.0;
        m20 = 0.0;
        m21 = 0.0;
        m22 = 1.0;
        m23 = 0.0;
        m30 = 0.0;
        m31 = 0.0;
        m32 = 0.0;
        m33 = 1.0;
        return this;
    }

    /**
     * Store the values of the given matrix <code>m</code> into <code>this</code> matrix.
     * 
     * @see #Matrix4d(Matrix4d)
     * @see #get(Matrix4d)
     * 
     * @param m
     *          the matrix to copy the values from
     * @return this
     */
    public Matrix4d set(Matrix4d m) {
        m00 = m.m00;
        m01 = m.m01;
        m02 = m.m02;
        m03 = m.m03;
        m10 = m.m10;
        m11 = m.m11;
        m12 = m.m12;
        m13 = m.m13;
        m20 = m.m20;
        m21 = m.m21;
        m22 = m.m22;
        m23 = m.m23;
        m30 = m.m30;
        m31 = m.m31;
        m32 = m.m32;
        m33 = m.m33;
        return this;
    }

    /**
     * Store the values of the given matrix <code>m</code> into <code>this</code> matrix.
     * 
     * @see #Matrix4d(Matrix4f)
     * 
     * @param m
     *          the matrix to copy the values from
     * @return this
     */
    public Matrix4d set(Matrix4f m) {
        m00 = m.m00;
        m01 = m.m01;
        m02 = m.m02;
        m03 = m.m03;
        m10 = m.m10;
        m11 = m.m11;
        m12 = m.m12;
        m13 = m.m13;
        m20 = m.m20;
        m21 = m.m21;
        m22 = m.m22;
        m23 = m.m23;
        m30 = m.m30;
        m31 = m.m31;
        m32 = m.m32;
        m33 = m.m33;
        return this;
    }

    /**
     * Set this matrix to be equivalent to the rotation specified by the given {@link AxisAngle4f}.
     * 
     * @param axisAngle
     *          the {@link AxisAngle4f}
     * @return this
     */
    public Matrix4d set(AxisAngle4f axisAngle) {
        double x = axisAngle.x;
        double y = axisAngle.y;
        double z = axisAngle.z;
        double angle = Math.toRadians(axisAngle.angle);
        double n = Math.sqrt(x*x + y*y + z*z);
        x /= n;
        y /= n;
        z /= n;
        double c = Math.cos(angle);
        double s = Math.sin(angle);
        double omc = 1.0 - c;
        m00 = c + x*x*omc;
        m11 = c + y*y*omc;
        m22 = c + z*z*omc;
        double tmp1 = x*y*omc;
        double tmp2 = z*s;
        m10 = tmp1 - tmp2;
        m01 = tmp1 + tmp2;
        tmp1 = x*z*omc;
        tmp2 = y*s;
        m20 = tmp1 + tmp2;
        m02 = tmp1 - tmp2;
        tmp1 = y*z*omc;
        tmp2 = x*s;
        m21 = tmp1 - tmp2;
        m12 = tmp1 + tmp2;
        m30 = 0.0f;
        m31 = 0.0f;
        m32 = 0.0f;
        m33 = 1.0f;
        return this;
    }

    /**
     * Multiply this matrix by the supplied <code>right</code> matrix.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the <code>right</code> matrix,
     * then the new matrix will be <code>M * R</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * R * v</code>, the
     * transformation of the right matrix will be applied first!
     * 
     * @param right
     *          the right operand of the multiplication
     * @return this
     */
    public Matrix4d mul(Matrix4d right) {
        return mul(right, this);
    }

    /**
     * Multiply this matrix by the supplied <code>right</code> matrix and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the <code>right</code> matrix,
     * then the new matrix will be <code>M * R</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * R * v</code>, the
     * transformation of the right matrix will be applied first!
     * 
     * @param right
     *          the right operand of the multiplication
     * @param dest
     *          will hold the result
     * @return this
     */
    public Matrix4d mul(Matrix4d right, Matrix4d dest) {
        if (this != dest && right != dest) {
            dest.m00 = m00 * right.m00 + m10 * right.m01 + m20 * right.m02 + m30 * right.m03;
            dest.m01 = m01 * right.m00 + m11 * right.m01 + m21 * right.m02 + m31 * right.m03;
            dest.m02 = m02 * right.m00 + m12 * right.m01 + m22 * right.m02 + m32 * right.m03;
            dest.m03 = m03 * right.m00 + m13 * right.m01 + m23 * right.m02 + m33 * right.m03;
            dest.m10 = m00 * right.m10 + m10 * right.m11 + m20 * right.m12 + m30 * right.m13;
            dest.m11 = m01 * right.m10 + m11 * right.m11 + m21 * right.m12 + m31 * right.m13;
            dest.m12 = m02 * right.m10 + m12 * right.m11 + m22 * right.m12 + m32 * right.m13;
            dest.m13 = m03 * right.m10 + m13 * right.m11 + m23 * right.m12 + m33 * right.m13;
            dest.m20 = m00 * right.m20 + m10 * right.m21 + m20 * right.m22 + m30 * right.m23;
            dest.m21 = m01 * right.m20 + m11 * right.m21 + m21 * right.m22 + m31 * right.m23;
            dest.m22 = m02 * right.m20 + m12 * right.m21 + m22 * right.m22 + m32 * right.m23;
            dest.m23 = m03 * right.m20 + m13 * right.m21 + m23 * right.m22 + m33 * right.m23;
            dest.m30 = m00 * right.m30 + m10 * right.m31 + m20 * right.m32 + m30 * right.m33;
            dest.m31 = m01 * right.m30 + m11 * right.m31 + m21 * right.m32 + m31 * right.m33;
            dest.m32 = m02 * right.m30 + m12 * right.m31 + m22 * right.m32 + m32 * right.m33;
            dest.m33 = m03 * right.m30 + m13 * right.m31 + m23 * right.m32 + m33 * right.m33;
        } else {
            dest.set(m00 * right.m00 + m10 * right.m01 + m20 * right.m02 + m30 * right.m03,
                     m01 * right.m00 + m11 * right.m01 + m21 * right.m02 + m31 * right.m03,
                     m02 * right.m00 + m12 * right.m01 + m22 * right.m02 + m32 * right.m03,
                     m03 * right.m00 + m13 * right.m01 + m23 * right.m02 + m33 * right.m03,
                     m00 * right.m10 + m10 * right.m11 + m20 * right.m12 + m30 * right.m13,
                     m01 * right.m10 + m11 * right.m11 + m21 * right.m12 + m31 * right.m13,
                     m02 * right.m10 + m12 * right.m11 + m22 * right.m12 + m32 * right.m13,
                     m03 * right.m10 + m13 * right.m11 + m23 * right.m12 + m33 * right.m13,
                     m00 * right.m20 + m10 * right.m21 + m20 * right.m22 + m30 * right.m23,
                     m01 * right.m20 + m11 * right.m21 + m21 * right.m22 + m31 * right.m23,
                     m02 * right.m20 + m12 * right.m21 + m22 * right.m22 + m32 * right.m23,
                     m03 * right.m20 + m13 * right.m21 + m23 * right.m22 + m33 * right.m23,
                     m00 * right.m30 + m10 * right.m31 + m20 * right.m32 + m30 * right.m33,
                     m01 * right.m30 + m11 * right.m31 + m21 * right.m32 + m31 * right.m33,
                     m02 * right.m30 + m12 * right.m31 + m22 * right.m32 + m32 * right.m33,
                     m03 * right.m30 + m13 * right.m31 + m23 * right.m32 + m33 * right.m33);
        }
        return this;
    }

    /**
     * Multiply this matrix by the supplied parameter matrix.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the <code>right</code> matrix,
     * then the new matrix will be <code>M * R</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * R * v</code>, the
     * transformation of the right matrix will be applied first!
     * 
     * @param right
     *          the right operand of the multiplication
     * @return this
     */
    public Matrix4d mul(Matrix4f right) {
        return mul(right, this);
    }

    /**
     * Multiply this matrix by the supplied parameter matrix and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the <code>right</code> matrix,
     * then the new matrix will be <code>M * R</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * R * v</code>, the
     * transformation of the right matrix will be applied first!
     * 
     * @param right
     *          the right operand of the multiplication
     * @param dest
     *          will hold the result
     * @return this
     */
    public Matrix4d mul(Matrix4f right, Matrix4d dest) {
        if (this != dest) {
            dest.m00 = m00 * right.m00 + m10 * right.m01 + m20 * right.m02 + m30 * right.m03;
            dest.m01 = m01 * right.m00 + m11 * right.m01 + m21 * right.m02 + m31 * right.m03;
            dest.m02 = m02 * right.m00 + m12 * right.m01 + m22 * right.m02 + m32 * right.m03;
            dest.m03 = m03 * right.m00 + m13 * right.m01 + m23 * right.m02 + m33 * right.m03;
            dest.m10 = m00 * right.m10 + m10 * right.m11 + m20 * right.m12 + m30 * right.m13;
            dest.m11 = m01 * right.m10 + m11 * right.m11 + m21 * right.m12 + m31 * right.m13;
            dest.m12 = m02 * right.m10 + m12 * right.m11 + m22 * right.m12 + m32 * right.m13;
            dest.m13 = m03 * right.m10 + m13 * right.m11 + m23 * right.m12 + m33 * right.m13;
            dest.m20 = m00 * right.m20 + m10 * right.m21 + m20 * right.m22 + m30 * right.m23;
            dest.m21 = m01 * right.m20 + m11 * right.m21 + m21 * right.m22 + m31 * right.m23;
            dest.m22 = m02 * right.m20 + m12 * right.m21 + m22 * right.m22 + m32 * right.m23;
            dest.m23 = m03 * right.m20 + m13 * right.m21 + m23 * right.m22 + m33 * right.m23;
            dest.m30 = m00 * right.m30 + m10 * right.m31 + m20 * right.m32 + m30 * right.m33;
            dest.m31 = m01 * right.m30 + m11 * right.m31 + m21 * right.m32 + m31 * right.m33;
            dest.m32 = m02 * right.m30 + m12 * right.m31 + m22 * right.m32 + m32 * right.m33;
            dest.m33 = m03 * right.m30 + m13 * right.m31 + m23 * right.m32 + m33 * right.m33;
        } else {
            dest.set(m00 * right.m00 + m10 * right.m01 + m20 * right.m02 + m30 * right.m03,
                     m01 * right.m00 + m11 * right.m01 + m21 * right.m02 + m31 * right.m03,
                     m02 * right.m00 + m12 * right.m01 + m22 * right.m02 + m32 * right.m03,
                     m03 * right.m00 + m13 * right.m01 + m23 * right.m02 + m33 * right.m03,
                     m00 * right.m10 + m10 * right.m11 + m20 * right.m12 + m30 * right.m13,
                     m01 * right.m10 + m11 * right.m11 + m21 * right.m12 + m31 * right.m13,
                     m02 * right.m10 + m12 * right.m11 + m22 * right.m12 + m32 * right.m13,
                     m03 * right.m10 + m13 * right.m11 + m23 * right.m12 + m33 * right.m13,
                     m00 * right.m20 + m10 * right.m21 + m20 * right.m22 + m30 * right.m23,
                     m01 * right.m20 + m11 * right.m21 + m21 * right.m22 + m31 * right.m23,
                     m02 * right.m20 + m12 * right.m21 + m22 * right.m22 + m32 * right.m23,
                     m03 * right.m20 + m13 * right.m21 + m23 * right.m22 + m33 * right.m23,
                     m00 * right.m30 + m10 * right.m31 + m20 * right.m32 + m30 * right.m33,
                     m01 * right.m30 + m11 * right.m31 + m21 * right.m32 + m31 * right.m33,
                     m02 * right.m30 + m12 * right.m31 + m22 * right.m32 + m32 * right.m33,
                     m03 * right.m30 + m13 * right.m31 + m23 * right.m32 + m33 * right.m33);
        }
        return this;
    }

    /**
     * Multiply the supplied <code>left</code> matrix by the <code>right</code> and store the result into <code>dest</code>.
     * <p>
     * If <code>L</code> is the <code>left</code> matrix and <code>R</code> the <code>right</code> matrix,
     * then the new matrix will be <code>L * R</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>L * R * v</code>, the
     * transformation of the right matrix will be applied first!
     * 
     * @param left
     *          the left operand of the multiplication
     * @param right
     *          the right operand of the multiplication
     * @param dest
     *          will hold the result
     */
    public static void mul(Matrix4f left, Matrix4d right, Matrix4d dest) {
        if (right != dest) {
            dest.m00 = left.m00 * right.m00 + left.m10 * right.m01 + left.m20 * right.m02 + left.m30 * right.m03;
            dest.m01 = left.m01 * right.m00 + left.m11 * right.m01 + left.m21 * right.m02 + left.m31 * right.m03;
            dest.m02 = left.m02 * right.m00 + left.m12 * right.m01 + left.m22 * right.m02 + left.m32 * right.m03;
            dest.m03 = left.m03 * right.m00 + left.m13 * right.m01 + left.m23 * right.m02 + left.m33 * right.m03;
            dest.m10 = left.m00 * right.m10 + left.m10 * right.m11 + left.m20 * right.m12 + left.m30 * right.m13;
            dest.m11 = left.m01 * right.m10 + left.m11 * right.m11 + left.m21 * right.m12 + left.m31 * right.m13;
            dest.m12 = left.m02 * right.m10 + left.m12 * right.m11 + left.m22 * right.m12 + left.m32 * right.m13;
            dest.m13 = left.m03 * right.m10 + left.m13 * right.m11 + left.m23 * right.m12 + left.m33 * right.m13;
            dest.m20 = left.m00 * right.m20 + left.m10 * right.m21 + left.m20 * right.m22 + left.m30 * right.m23;
            dest.m21 = left.m01 * right.m20 + left.m11 * right.m21 + left.m21 * right.m22 + left.m31 * right.m23;
            dest.m22 = left.m02 * right.m20 + left.m12 * right.m21 + left.m22 * right.m22 + left.m32 * right.m23;
            dest.m23 = left.m03 * right.m20 + left.m13 * right.m21 + left.m23 * right.m22 + left.m33 * right.m23;
            dest.m30 = left.m00 * right.m30 + left.m10 * right.m31 + left.m20 * right.m32 + left.m30 * right.m33;
            dest.m31 = left.m01 * right.m30 + left.m11 * right.m31 + left.m21 * right.m32 + left.m31 * right.m33;
            dest.m32 = left.m02 * right.m30 + left.m12 * right.m31 + left.m22 * right.m32 + left.m32 * right.m33;
            dest.m33 = left.m03 * right.m30 + left.m13 * right.m31 + left.m23 * right.m32 + left.m33 * right.m33;
        } else {
            dest.set(left.m00 * right.m00 + left.m10 * right.m01 + left.m20 * right.m02 + left.m30 * right.m03,
                     left.m01 * right.m00 + left.m11 * right.m01 + left.m21 * right.m02 + left.m31 * right.m03,
                     left.m02 * right.m00 + left.m12 * right.m01 + left.m22 * right.m02 + left.m32 * right.m03,
                     left.m03 * right.m00 + left.m13 * right.m01 + left.m23 * right.m02 + left.m33 * right.m03,
                     left.m00 * right.m10 + left.m10 * right.m11 + left.m20 * right.m12 + left.m30 * right.m13,
                     left.m01 * right.m10 + left.m11 * right.m11 + left.m21 * right.m12 + left.m31 * right.m13,
                     left.m02 * right.m10 + left.m12 * right.m11 + left.m22 * right.m12 + left.m32 * right.m13,
                     left.m03 * right.m10 + left.m13 * right.m11 + left.m23 * right.m12 + left.m33 * right.m13,
                     left.m00 * right.m20 + left.m10 * right.m21 + left.m20 * right.m22 + left.m30 * right.m23,
                     left.m01 * right.m20 + left.m11 * right.m21 + left.m21 * right.m22 + left.m31 * right.m23,
                     left.m02 * right.m20 + left.m12 * right.m21 + left.m22 * right.m22 + left.m32 * right.m23,
                     left.m03 * right.m20 + left.m13 * right.m21 + left.m23 * right.m22 + left.m33 * right.m23,
                     left.m00 * right.m30 + left.m10 * right.m31 + left.m20 * right.m32 + left.m30 * right.m33,
                     left.m01 * right.m30 + left.m11 * right.m31 + left.m21 * right.m32 + left.m31 * right.m33,
                     left.m02 * right.m30 + left.m12 * right.m31 + left.m22 * right.m32 + left.m32 * right.m33,
                     left.m03 * right.m30 + left.m13 * right.m31 + left.m23 * right.m32 + left.m33 * right.m33);
        }
    }

    /**
     * Multiply this matrix by the top 4x3 submatrix of the supplied <code>right</code> matrix and store the result in <code>this</code>.
     * This method assumes that the last row of <code>right</code> is equal to <tt>(0, 0, 0, 1)</tt>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the <code>right</code> matrix,
     * then the new matrix will be <code>M * R</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * R * v</code>, the
     * transformation of the right matrix will be applied first!
     *
     * @param right
     *          the right operand of the matrix multiplication (the last row is assumed to be <tt>(0, 0, 0, 1)</tt>)
     * @return this
     */
    public Matrix4d mul4x3(Matrix4d right) {
       return mul4x3(right, this);
    }

    /**
     * Multiply this matrix by the top 4x3 submatrix of the supplied <code>right</code> matrix and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the <code>right</code> matrix,
     * then the new matrix will be <code>M * R</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * R * v</code>, the
     * transformation of the right matrix will be applied first!
     *
     * @param right
     *          the right operand of the matrix multiplication (the last row is assumed to be <tt>(0, 0, 0, 1)</tt>)
     * @param dest
     *          the destination matrix, which will hold the result
     * @return this
     */
    public Matrix4d mul4x3(Matrix4d right, Matrix4d dest) {
        if (this != dest && right != dest) {
            dest.m00 = m00 * right.m00 + m10 * right.m01 + m20 * right.m02;
            dest.m01 = m01 * right.m00 + m11 * right.m01 + m21 * right.m02;
            dest.m02 = m02 * right.m00 + m12 * right.m01 + m22 * right.m02;
            dest.m03 = m03 * right.m00 + m13 * right.m01 + m23 * right.m02;
            dest.m10 = m00 * right.m10 + m10 * right.m11 + m20 * right.m12;
            dest.m11 = m01 * right.m10 + m11 * right.m11 + m21 * right.m12;
            dest.m12 = m02 * right.m10 + m12 * right.m11 + m22 * right.m12;
            dest.m13 = m03 * right.m10 + m13 * right.m11 + m23 * right.m12;
            dest.m20 = m00 * right.m20 + m10 * right.m21 + m20 * right.m22;
            dest.m21 = m01 * right.m20 + m11 * right.m21 + m21 * right.m22;
            dest.m22 = m02 * right.m20 + m12 * right.m21 + m22 * right.m22;
            dest.m23 = m03 * right.m20 + m13 * right.m21 + m23 * right.m22;
            dest.m30 = m00 * right.m30 + m10 * right.m31 + m20 * right.m32 + m30;
            dest.m31 = m01 * right.m30 + m11 * right.m31 + m21 * right.m32 + m31;
            dest.m32 = m02 * right.m30 + m12 * right.m31 + m22 * right.m32 + m32;
            dest.m33 = m03 * right.m30 + m13 * right.m31 + m23 * right.m32 + m33;
        } else {
            dest.set(m00 * right.m00 + m10 * right.m01 + m20 * right.m02,
                     m01 * right.m00 + m11 * right.m01 + m21 * right.m02,
                     m02 * right.m00 + m12 * right.m01 + m22 * right.m02,
                     m03 * right.m00 + m13 * right.m01 + m23 * right.m02,
                     m00 * right.m10 + m10 * right.m11 + m20 * right.m12,
                     m01 * right.m10 + m11 * right.m11 + m21 * right.m12,
                     m02 * right.m10 + m12 * right.m11 + m22 * right.m12,
                     m03 * right.m10 + m13 * right.m11 + m23 * right.m12,
                     m00 * right.m20 + m10 * right.m21 + m20 * right.m22,
                     m01 * right.m20 + m11 * right.m21 + m21 * right.m22,
                     m02 * right.m20 + m12 * right.m21 + m22 * right.m22,
                     m03 * right.m20 + m13 * right.m21 + m23 * right.m22,
                     m00 * right.m30 + m10 * right.m31 + m20 * right.m32 + m30,
                     m01 * right.m30 + m11 * right.m31 + m21 * right.m32 + m31,
                     m02 * right.m30 + m12 * right.m31 + m22 * right.m32 + m32,
                     m03 * right.m30 + m13 * right.m31 + m23 * right.m32 + m33);
        }
        return this;
    }

    /** Set the values within this matrix to the supplied double values. The matrix will look like this:<br><br>
     *  
     * m00, m10, m20, m30<br>
     * m01, m11, m21, m31<br>
     * m02, m12, m22, m32<br>
     * m03, m13, m23, m33
     *
     * @param m00
     *          the new value of m00
     * @param m01
     *          the new value of m01
     * @param m02
     *          the new value of m02
     * @param m03
     *          the new value of m03
     * @param m10
     *          the new value of m10
     * @param m11
     *          the new value of m11
     * @param m12
     *          the new value of m12
     * @param m13
     *          the new value of m13
     * @param m20
     *          the new value of m20
     * @param m21
     *          the new value of m21
     * @param m22
     *          the new value of m22
     * @param m23
     *          the new value of m23
     * @param m30
     *          the new value of m30
     * @param m31
     *          the new value of m31
     * @param m32
     *          the new value of m32
     * @param m33
     *          the new value of m33
     * @return this
     */
    public Matrix4d set(double m00, double m01, double m02,double m03,
                        double m10, double m11, double m12, double m13,
                        double m20, double m21, double m22, double m23, 
                        double m30, double m31, double m32, double m33) {
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
        return this;
    }

    /** Set the values in the matrix using a double array. The results will look like this:<br><br>
     * 
     * 0, 4, 8, 12<br>
     * 1, 5, 9, 13<br>
     * 2, 6, 10, 14<br>
     * 3, 7, 11, 15<br>
     * 
     * @param m
     *          the array to read the matrix values from
     * @return this
     */
    public Matrix4d set(double m[]) {
        m00 = m[0];
        m01 = m[1];
        m02 = m[2];
        m03 = m[3];
        m10 = m[4];
        m11 = m[5];
        m12 = m[6];
        m13 = m[7];
        m20 = m[8];
        m21 = m[9];
        m22 = m[10];
        m23 = m[11];
        m30 = m[12];
        m31 = m[13];
        m32 = m[14];
        m33 = m[15];
        return this;
    }

    /** Set the values in the matrix using a float array. The results will look like this:<br><br>
     * 
     * 0, 4, 8, 12<br>
     * 1, 5, 9, 13<br>
     * 2, 6, 10, 14<br>
     * 3, 7, 11, 15<br>
     * 
     * @param m
     *          the array to read the matrix values from
     * @return this
     */
    public Matrix4d set(float m[]) {
        m00 = m[0];
        m01 = m[1];
        m02 = m[2];
        m03 = m[3];
        m10 = m[4];
        m11 = m[5];
        m12 = m[6];
        m13 = m[7];
        m20 = m[8];
        m21 = m[9];
        m22 = m[10];
        m23 = m[11];
        m30 = m[12];
        m31 = m[13];
        m32 = m[14];
        m33 = m[15];
        return this;
    }

    /**
     * Set the values of this matrix by reading 16 double values from the given {@link DoubleBuffer} in column-major order,
     * starting at its current position.
     * <p>
     * The DoubleBuffer is expected to contain the values in column-major order.
     * <p>
     * The position of the DoubleBuffer will not be changed by this method.
     * 
     * @param buffer
     *              the DoubleBuffer to read the matrix values from in column-major order
     * @return this
     */
    public Matrix4d set(DoubleBuffer buffer) {
        int pos = buffer.position();
        m00 = buffer.get(pos);
        m01 = buffer.get(pos+1);
        m02 = buffer.get(pos+2);
        m03 = buffer.get(pos+3);
        m10 = buffer.get(pos+4);
        m11 = buffer.get(pos+5);
        m12 = buffer.get(pos+6);
        m13 = buffer.get(pos+7);
        m20 = buffer.get(pos+8);
        m21 = buffer.get(pos+9);
        m22 = buffer.get(pos+10);
        m23 = buffer.get(pos+11);
        m30 = buffer.get(pos+12);
        m31 = buffer.get(pos+13);
        m32 = buffer.get(pos+14);
        m33 = buffer.get(pos+15);
        return this;
    }

    /**
     * Set the values of this matrix by reading 16 float values from the given {@link FloatBuffer} in column-major order,
     * starting at its current position.
     * <p>
     * The FloatBuffer is expected to contain the values in column-major order.
     * <p>
     * The position of the FloatBuffer will not be changed by this method.
     * 
     * @param buffer
     *              the FloatBuffer to read the matrix values from in column-major order
     * @return this
     */
    public Matrix4d set(FloatBuffer buffer) {
        int pos = buffer.position();
        m00 = buffer.get(pos);
        m01 = buffer.get(pos+1);
        m02 = buffer.get(pos+2);
        m03 = buffer.get(pos+3);
        m10 = buffer.get(pos+4);
        m11 = buffer.get(pos+5);
        m12 = buffer.get(pos+6);
        m13 = buffer.get(pos+7);
        m20 = buffer.get(pos+8);
        m21 = buffer.get(pos+9);
        m22 = buffer.get(pos+10);
        m23 = buffer.get(pos+11);
        m30 = buffer.get(pos+12);
        m31 = buffer.get(pos+13);
        m32 = buffer.get(pos+14);
        m33 = buffer.get(pos+15);
        return this;
    }

    /**
     * Return the determinant of this matrix.
     * 
     * @return the determinant
     */
    public double determinant() {
        return (m00 * m11 - m01 * m10) * (m22 * m33 - m23 * m32) - (m00 * m12 - m02 * m10) * (m21 * m33 - m23 * m31)
             + (m00 * m13 - m03 * m10) * (m21 * m32 - m22 * m31) + (m01 * m12 - m02 * m11) * (m20 * m33 - m23 * m30)
             - (m01 * m13 - m03 * m11) * (m20 * m32 - m22 * m30) + (m02 * m13 - m03 * m12) * (m20 * m31 - m21 * m30);
    }

    /**
     * Return the determinant of the top-left 3x3 submatrix of this matrix.
     * 
     * @return the determinant
     */
    public double determinant3x3() {
        return m00 * m11 * m22
             + m10 * m21 * m02
             + m20 * m01 * m12
             - m20 * m11 * m02
             - m00 * m21 * m12
             - m10 * m01 * m22;
    }

    /**
     * Invert this matrix.
     * 
     * @return this
     */
    public Matrix4d invert() {
        return invert(this);
    }

    /**
     * Invert <code>this</code> matrix and store the result in <code>dest</code>.
     * 
     * @param dest
     *             will hold the result
     * @return this
     */
    public Matrix4d invert(Matrix4d dest) {
        double s = determinant();
        if (s == 0.0) {
            return this;
        }
        s = 1.0 / s;
        if (this != dest) {
            dest.m00 = (m11 * (m22 * m33 - m23 * m32) + m12 * (m23 * m31 - m21 * m33) + m13 * (m21 * m32 - m22 * m31)) * s;
            dest.m01 = (m21 * (m02 * m33 - m03 * m32) + m22 * (m03 * m31 - m01 * m33) + m23 * (m01 * m32 - m02 * m31)) * s;
            dest.m02 = (m31 * (m02 * m13 - m03 * m12) + m32 * (m03 * m11 - m01 * m13) + m33 * (m01 * m12 - m02 * m11)) * s;
            dest.m03 = (m01 * (m13 * m22 - m12 * m23) + m02 * (m11 * m23 - m13 * m21) + m03 * (m12 * m21 - m11 * m22)) * s;
            dest.m10 = (m12 * (m20 * m33 - m23 * m30) + m13 * (m22 * m30 - m20 * m32) + m10 * (m23 * m32 - m22 * m33)) * s;
            dest.m11 = (m22 * (m00 * m33 - m03 * m30) + m23 * (m02 * m30 - m00 * m32) + m20 * (m03 * m32 - m02 * m33)) * s;
            dest.m12 = (m32 * (m00 * m13 - m03 * m10) + m33 * (m02 * m10 - m00 * m12) + m30 * (m03 * m12 - m02 * m13)) * s;
            dest.m13 = (m02 * (m13 * m20 - m10 * m23) + m03 * (m10 * m22 - m12 * m20) + m00 * (m12 * m23 - m13 * m22)) * s;
            dest.m20 = (m13 * (m20 * m31 - m21 * m30) + m10 * (m21 * m33 - m23 * m31) + m11 * (m23 * m30 - m20 * m33)) * s;
            dest.m21 = (m23 * (m00 * m31 - m01 * m30) + m20 * (m01 * m33 - m03 * m31) + m21 * (m03 * m30 - m00 * m33)) * s;
            dest.m22 = (m33 * (m00 * m11 - m01 * m10) + m30 * (m01 * m13 - m03 * m11) + m31 * (m03 * m10 - m00 * m13)) * s;
            dest.m23 = (m03 * (m11 * m20 - m10 * m21) + m00 * (m13 * m21 - m11 * m23) + m01 * (m10 * m23 - m13 * m20)) * s;
            dest.m30 = (m10 * (m22 * m31 - m21 * m32) + m11 * (m20 * m32 - m22 * m30) + m12 * (m21 * m30 - m20 * m31)) * s;
            dest.m31 = (m20 * (m02 * m31 - m01 * m32) + m21 * (m00 * m32 - m02 * m30) + m22 * (m01 * m30 - m00 * m31)) * s;
            dest.m32 = (m30 * (m02 * m11 - m01 * m12) + m31 * (m00 * m12 - m02 * m10) + m32 * (m01 * m10 - m00 * m11)) * s;
            dest.m33 = (m00 * (m11 * m22 - m12 * m21) + m01 * (m12 * m20 - m10 * m22) + m02 * (m10 * m21 - m11 * m20)) * s;
        } else {
            dest.set((m11 * (m22 * m33 - m23 * m32) + m12 * (m23 * m31 - m21 * m33) + m13 * (m21 * m32 - m22 * m31)) * s,
                     (m21 * (m02 * m33 - m03 * m32) + m22 * (m03 * m31 - m01 * m33) + m23 * (m01 * m32 - m02 * m31)) * s,
                     (m31 * (m02 * m13 - m03 * m12) + m32 * (m03 * m11 - m01 * m13) + m33 * (m01 * m12 - m02 * m11)) * s,
                     (m01 * (m13 * m22 - m12 * m23) + m02 * (m11 * m23 - m13 * m21) + m03 * (m12 * m21 - m11 * m22)) * s,
                     (m12 * (m20 * m33 - m23 * m30) + m13 * (m22 * m30 - m20 * m32) + m10 * (m23 * m32 - m22 * m33)) * s,
                     (m22 * (m00 * m33 - m03 * m30) + m23 * (m02 * m30 - m00 * m32) + m20 * (m03 * m32 - m02 * m33)) * s,
                     (m32 * (m00 * m13 - m03 * m10) + m33 * (m02 * m10 - m00 * m12) + m30 * (m03 * m12 - m02 * m13)) * s,
                     (m02 * (m13 * m20 - m10 * m23) + m03 * (m10 * m22 - m12 * m20) + m00 * (m12 * m23 - m13 * m22)) * s,
                     (m13 * (m20 * m31 - m21 * m30) + m10 * (m21 * m33 - m23 * m31) + m11 * (m23 * m30 - m20 * m33)) * s,
                     (m23 * (m00 * m31 - m01 * m30) + m20 * (m01 * m33 - m03 * m31) + m21 * (m03 * m30 - m00 * m33)) * s,
                     (m33 * (m00 * m11 - m01 * m10) + m30 * (m01 * m13 - m03 * m11) + m31 * (m03 * m10 - m00 * m13)) * s,
                     (m03 * (m11 * m20 - m10 * m21) + m00 * (m13 * m21 - m11 * m23) + m01 * (m10 * m23 - m13 * m20)) * s,
                     (m10 * (m22 * m31 - m21 * m32) + m11 * (m20 * m32 - m22 * m30) + m12 * (m21 * m30 - m20 * m31)) * s,
                     (m20 * (m02 * m31 - m01 * m32) + m21 * (m00 * m32 - m02 * m30) + m22 * (m01 * m30 - m00 * m31)) * s,
                     (m30 * (m02 * m11 - m01 * m12) + m31 * (m00 * m12 - m02 * m10) + m32 * (m01 * m10 - m00 * m11)) * s,
                     (m00 * (m11 * m22 - m12 * m21) + m01 * (m12 * m20 - m10 * m22) + m02 * (m10 * m21 - m11 * m20)) * s );
        }
        return this;
    }

    /**
     * Transpose this matrix.
     * 
     * @return this
     */
    public Matrix4d transpose() {
        return transpose(this);
    }

    /**
     * Transpose <code>this</code> matrix and store the result into <code>dest</code>.
     * 
     * @param dest
     *             will hold the result
     * @return this
     */
    public Matrix4d transpose(Matrix4d dest) {
        if (this != dest) {
            dest.m00 = m00;
            dest.m01 = m10;
            dest.m02 = m20;
            dest.m03 = m30;
            dest.m10 = m01;
            dest.m11 = m11;
            dest.m12 = m21;
            dest.m13 = m31;
            dest.m20 = m02;
            dest.m21 = m12;
            dest.m22 = m22;
            dest.m23 = m32;
            dest.m30 = m03;
            dest.m31 = m13;
            dest.m32 = m23;
            dest.m33 = m33;
        } else {
            dest.set(m00, m10, m20, m30,
                     m01, m11, m21, m31,
                     m02, m12, m22, m32,
                     m03, m13, m23, m33);
        }
        return this;
    }

    /**
     * Transpose only the top-left 3x3 submatrix of this matrix and set the rest of the matrix elements to identity.
     * 
     * @return this 
     */
    public Matrix4d transpose3x3() {
        return transpose3x3(this);
    }

    /**
     * Transpose only the top-left 3x3 submatrix of this matrix and store the result in <code>dest</code>.
     * <p>
     * All other matrix elements of <code>dest</code> will be set to identity.
     * 
     * @param dest
     *             will hold the result
     * @return this
     */
    public Matrix4d transpose3x3(Matrix4d dest) {
        if (this != dest) {
            dest.m00 = m00;
            dest.m01 = m10;
            dest.m02 = m20;
            dest.m03 = 0.0;
            dest.m10 = m01;
            dest.m11 = m11;
            dest.m12 = m21;
            dest.m13 = 0.0;
            dest.m20 = m02;
            dest.m21 = m12;
            dest.m22 = m22;
            dest.m23 = 0.0;
            dest.m30 = 0.0;
            dest.m31 = 0.0;
            dest.m32 = 0.0;
            dest.m33 = 1.0;
        } else {
            dest.set(m00, m10, m20, 0.0,
                     m01, m11, m21, 0.0,
                     m02, m12, m22, 0.0,
                     0.0, 0.0, 0.0, 1.0);
        }
        return this;
    }

    /**
     * Transpose only the top-left 3x3 submatrix of this matrix and store the result in <code>dest</code>.
     * 
     * @param dest
     *             will hold the result
     * @return this
     */
    public Matrix4d transpose3x3(Matrix3d dest) {
        dest.m00 = m00;
        dest.m01 = m10;
        dest.m02 = m20;
        dest.m10 = m01;
        dest.m11 = m11;
        dest.m12 = m21;
        dest.m20 = m02;
        dest.m21 = m12;
        dest.m22 = m22;
        return this;
    }

    /**
     * Set this matrix to be a simple translation matrix.
     * <p>
     * The resulting matrix can be multiplied against another transformation
     * matrix to obtain an additional translation.
     * 
     * @param x
     *          the offset to translate in x
     * @param y
     *          the offset to translate in y
     * @param z
     *          the offset to translate in z
     * @return this
     */
    public Matrix4d translation(double x, double y, double z) {
        m00 = 1.0;
        m01 = 0.0;
        m02 = 0.0;
        m03 = 0.0;
        m10 = 0.0;
        m11 = 1.0;
        m12 = 0.0;
        m13 = 0.0;
        m20 = 0.0;
        m21 = 0.0;
        m22 = 1.0;
        m23 = 0.0;
        m30 = x;
        m31 = y;
        m32 = z;
        m33 = 1.0;
        return this;
    }

    /**
     * Set this matrix to be a simple translation matrix.
     * <p>
     * The resulting matrix can be multiplied against another transformation
     * matrix to obtain an additional translation.
     * 
     * @param offset
     *              the offsets in x, y and z to translate
     * @return this
     */
    public Matrix4d translation(Vector3f offset) {
        return translation(offset.x, offset.y, offset.z);
    }

    /**
     * Set this matrix to be a simple translation matrix.
     * <p>
     * The resulting matrix can be multiplied against another transformation
     * matrix to obtain an additional translation.
     *
     * @param offset
     *              the offsets in x, y and z to translate
     * @return this
     */
    public Matrix4d translation(Vector3d offset) {
        return translation(offset.x, offset.y, offset.z);
    }

    /**
     * Set only the translation components of this matrix <tt>(m30, m31, m32)</tt> to the given values <tt>(x, y, z)</tt>.
     * <p>
     * To build a translation matrix instead, use {@link #translation(double, double, double)}.
     * To apply a translation to another matrix, use {@link #translate(double, double, double)}.
     * 
     * @see #translation(double, double, double)
     * @see #translate(double, double, double)
     * 
     * @param x
     *          the units to translate in x
     * @param y
     *          the units to translate in y
     * @param z
     *          the units to translate in z
     * @return this
     */
    public Matrix4d setTranslation(double x, double y, double z) {
        m30 = x;
        m31 = y;
        m32 = z;
        return this;
    }

    /**
     * Set only the translation components of this matrix <tt>(m30, m31, m32)</tt> to the given vector values <tt>(x, y, z)</tt>.
     * <p>
     * To build a translation matrix instead, use {@link #translation(Vector3d)}.
     * To apply a translation to another matrix, use {@link #translate(Vector3d)}.
     * 
     * @see #translation(Vector3d)
     * @see #translate(Vector3d)
     * 
     * @param xyz
     *          the units to translate in <tt>(x, y, z)</tt>
     * @return this
     */
    public Matrix4d setTranslation(Vector3d xyz) {
        m30 = xyz.x;
        m31 = xyz.y;
        m32 = xyz.z;
        return this;
    }

    /**
     * Return a string representation of this matrix.
     * <p>
     * This method creates a new {@link DecimalFormat} on every invocation with the format string "<tt>  0.000E0; -</tt>".
     * 
     * @return the string representation
     */
    public String toString() {
        DecimalFormat formatter = new DecimalFormat("  0.000E0; -"); //$NON-NLS-1$
        return toString(formatter).replaceAll("E(\\d+)", "E+$1"); //$NON-NLS-1$ //$NON-NLS-2$
    }

    /**
     * Return a string representation of this matrix by formatting the matrix elements with the given {@link NumberFormat}.
     * 
     * @param formatter
     *          the {@link NumberFormat} used to format the matrix values with
     * @return the string representation
     */
    public String toString(NumberFormat formatter) {
        return formatter.format(m00) + formatter.format(m10) + formatter.format(m20) + formatter.format(m30) + "\n" //$NON-NLS-1$
             + formatter.format(m01) + formatter.format(m11) + formatter.format(m21) + formatter.format(m31) + "\n" //$NON-NLS-1$
             + formatter.format(m02) + formatter.format(m12) + formatter.format(m22) + formatter.format(m32) + "\n" //$NON-NLS-1$
             + formatter.format(m03) + formatter.format(m13) + formatter.format(m23) + formatter.format(m33) + "\n"; //$NON-NLS-1$
    }

    /**
     * Get the current values of <code>this</code> matrix and store them into
     * <code>dest</code>.
     * <p>
     * This is the reverse method of {@link #set(Matrix4d)} and allows to obtain
     * intermediate calculation results when chaining multiple transformations.
     * 
     * @see #set(Matrix4d)
     * 
     * @param dest
     *          the destination matrix
     * @return this
     */
    public Matrix4d get(Matrix4d dest) {
        dest.set(this);
        return this;
    }

    /**
     * Get the rotational component of <code>this</code> matrix and store the represented rotation
     * into the given {@link AxisAngle4f}.
     * 
     * @see AxisAngle4f#set(Matrix4d)
     * 
     * @param dest
     *          the destination {@link AxisAngle4f}
     * @return this
     */
    public Matrix4d get(AxisAngle4f dest) {
        dest.set(this);
        return this;
    }

    /**
     * Get the rotational component of <code>this</code> matrix and store the represented rotation
     * into the given {@link Quaternionf}.
     * 
     * @see Quaternionf#set(Matrix4d)
     * 
     * @param dest
     *          the destination {@link Quaternionf}
     * @return this
     */
    public Matrix4d get(Quaternionf dest) {
        dest.set(this);
        return this;
    }

    /**
     * Get the rotational component of <code>this</code> matrix and store the represented rotation
     * into the given {@link Quaterniond}.
     * 
     * @see Quaterniond#set(Matrix4d)
     * 
     * @param dest
     *          the destination {@link Quaterniond}
     * @return this
     */
    public Matrix4d get(Quaterniond dest) {
        dest.set(this);
        return this;
    }

    /**
     * Store this matrix in column-major order into the supplied {@link DoubleBuffer} at the current
     * buffer {@link DoubleBuffer#position() position}.
     * <p>
     * This method will not increment the position of the given DoubleBuffer.
     * <p>
     * If you want to specify the offset into the DoubleBuffer at which
     * the matrix is stored, you can use {@link #get(int, DoubleBuffer)}, taking
     * the absolute position as parameter.
     * 
     * @see #get(int, DoubleBuffer)
     * 
     * @param buffer
     *            will receive the values of this matrix in column-major order at its current position
     * @return this
     */
    public Matrix4d get(DoubleBuffer buffer) {
        return get(buffer.position(), buffer);
    }

    /**
     * Store this matrix in column-major order into the supplied {@link DoubleBuffer} starting at the specified
     * absolute buffer position/index.
     * <p>
     * This method will not increment the position of the given {@link DoubleBuffer}.
     * 
     * @param index
     *            the absolute position into the {@link DoubleBuffer}
     * @param buffer
     *            will receive the values of this matrix in column-major order
     * @return this
     */
    public Matrix4d get(int index, DoubleBuffer buffer) {
        buffer.put(index, m00);
        buffer.put(index+1, m01);
        buffer.put(index+2, m02);
        buffer.put(index+3, m03);
        buffer.put(index+4, m10);
        buffer.put(index+5, m11);
        buffer.put(index+6, m12);
        buffer.put(index+7, m13);
        buffer.put(index+8, m20);
        buffer.put(index+9, m21);
        buffer.put(index+10, m22);
        buffer.put(index+11, m23);
        buffer.put(index+12, m30);
        buffer.put(index+13, m31);
        buffer.put(index+14, m32);
        buffer.put(index+15, m33);
        return this;
    }

    /**
     * Store this matrix in column-major order into the supplied {@link FloatBuffer} at the current
     * buffer {@link FloatBuffer#position() position}.
     * <p>
     * This method will not increment the position of the given
     * FloatBuffer.
     * <p>
     * If you want to specify the offset into the FloatBuffer at which
     * the matrix is stored, you can use {@link #get(int, FloatBuffer)}, taking
     * the absolute position as parameter.
     * <p>
     * Please note that due to this matrix storing double values those values will potentially
     * lose precision when they are converted to float values before being put into the given FloatBuffer.
     * 
     * @see #get(int, FloatBuffer)
     * 
     * @param buffer
     *            will receive the values of this matrix in column-major order at its current position
     * @return this
     */
    public Matrix4d get(FloatBuffer buffer) {
        return get(buffer.position(), buffer);
    }

    /**
     * Store this matrix in column-major order into the supplied {@link FloatBuffer} starting at the specified
     * absolute buffer position/index.
     * <p>
     * This method will not increment the position of the given FloatBuffer.
     * <p>
     * Please note that due to this matrix storing double values those values will potentially
     * lose precision when they are converted to float values before being put into the given FloatBuffer.
     * 
     * @param index
     *            the absolute position into the FloatBuffer
     * @param buffer
     *            will receive the values of this matrix in column-major order
     * @return this
     */
    public Matrix4d get(int index, FloatBuffer buffer) {
        buffer.put(index, (float) m00);
        buffer.put(index+1, (float) m01);
        buffer.put(index+2, (float) m02);
        buffer.put(index+3, (float) m03);
        buffer.put(index+4, (float) m10);
        buffer.put(index+5, (float) m11);
        buffer.put(index+6, (float) m12);
        buffer.put(index+7, (float) m13);
        buffer.put(index+8, (float) m20);
        buffer.put(index+9, (float) m21);
        buffer.put(index+10, (float) m22);
        buffer.put(index+11, (float) m23);
        buffer.put(index+12, (float) m30);
        buffer.put(index+13, (float) m31);
        buffer.put(index+14, (float) m32);
        buffer.put(index+15, (float) m33);
        return this;
    }

    /**
     * Set all the values within this matrix to 0.
     * 
     * @return this
     */
    public Matrix4d zero() {
        m00 = 0.0;
        m01 = 0.0;
        m02 = 0.0;
        m03 = 0.0;
        m10 = 0.0;
        m11 = 0.0;
        m12 = 0.0;
        m13 = 0.0;
        m20 = 0.0;
        m21 = 0.0;
        m22 = 0.0;
        m23 = 0.0;
        m30 = 0.0;
        m31 = 0.0;
        m32 = 0.0;
        m33 = 0.0;
        return this;
    }

    /**
     * Set this matrix to be a simple scale matrix, which scales all axes uniformly by the given factor.
     * <p>
     * The resulting matrix can be multiplied against another transformation
     * matrix to obtain an additional scaling.
     * <p>
     * If you want to post-multiply a scaling transformation directly to a
     * matrix, you can use {@link #scale(double) scale()} instead.
     * 
     * @see #scale(double)
     * 
     * @param factor
     *             the scale factor in x, y and z
     * @return this
     */
    public Matrix4d scaling(double factor) {
        m00 = factor;
        m01 = 0.0;
        m02 = 0.0;
        m03 = 0.0;
        m10 = 0.0;
        m11 = factor;
        m12 = 0.0;
        m13 = 0.0;
        m20 = 0.0;
        m21 = 0.0;
        m22 = factor;
        m23 = 0.0;
        m30 = 0.0;
        m31 = 0.0;
        m32 = 0.0;
        m33 = 1.0;
        return this;
    }

    /**
     * Set this matrix to be a simple scale matrix.
     * 
     * @param x
     *          the scale in x
     * @param y
     *          the scale in y
     * @param z
     *          the scale in z         
     * @return this
     */
    public Matrix4d scaling(double x, double y, double z) {
        m00 = x;
        m01 = 0.0;
        m02 = 0.0;
        m03 = 0.0;
        m10 = 0.0;
        m11 = y;
        m12 = 0.0;
        m13 = 0.0;
        m20 = 0.0;
        m21 = 0.0;
        m22 = z;
        m23 = 0.0;
        m30 = 0.0;
        m31 = 0.0;
        m32 = 0.0;
        m33 = 1.0;
        return this;
    }

    /**
     * Set this matrix to be a simple scale matrix.
     * 
     * @param scale
     *             the scale applied to each dimension
     * @return this
     */
    public Matrix4d scaling(Vector3d scale) {
        m00 = scale.x;
        m01 = 0.0;
        m02 = 0.0;
        m03 = 0.0;
        m10 = 0.0;
        m11 = scale.y;
        m12 = 0.0;
        m13 = 0.0;
        m20 = 0.0;
        m21 = 0.0;
        m22 = scale.z;
        m23 = 0.0;
        m30 = 0.0;
        m31 = 0.0;
        m32 = 0.0;
        m33 = 1.0;
        return this;
    }

    /**
     * Set this matrix to a rotation matrix which rotates the given radians about a given axis.
     * <p>
     * From <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Rotation_matrix_from_axis_and_angle">Wikipedia</a>
     * 
     * @param angle
     *          the angle in degrees
     * @param x
     *          the x-coordinate of the axis to rotate about
     * @param y
     *          the y-coordinate of the axis to rotate about
     * @param z
     *          the z-coordinate of the axis to rotate about
     * @return this
     */
    public Matrix4d rotation(double angle, double x, double y, double z) {
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);
        m00 = cos + x * x * (1.0 - cos);
        m10 = x * y * (1.0 - cos) - z * sin;
        m20 = x * z * (1.0 - cos) + y * sin;
        m30 = 0.0;
        m01 = y * x * (1.0 - cos) + z * sin;
        m11 = cos + y * y * (1.0 - cos);
        m21 = y * z * (1.0 - cos) - x * sin;
        m31 = 0.0;
        m02 = z * x * (1.0 - cos) - y * sin;
        m12 = z * y * (1.0 - cos) + x * sin;
        m22 = cos + z * z * (1.0 - cos);
        m32 = 0.0;
        m03 = 0.0;
        m13 = 0.0;
        m23 = 0.0;
        m33 = 1.0;
        return this;
    }

    /**
     * Set this matrix to a rotation transformation about the X axis.
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Basic_rotations">http://en.wikipedia.org</a>
     * 
     * @param ang
     *            the angle in degrees
     * @return this
     */
    public Matrix4d rotationX(double ang) {
        double cos = Math.cos(Math.toRadians(ang));
        double sin = Math.sin(Math.toRadians(ang));
        m00 = 1.0;
        m01 = 0.0;
        m02 = 0.0;
        m03 = 0.0;
        m10 = 0.0;
        m11 = cos;
        m12 = sin;
        m13 = 0.0;
        m20 = 0.0;
        m21 = -sin;
        m22 = cos;
        m23 = 0.0;
        m30 = 0.0;
        m31 = 0.0;
        m32 = 0.0;
        m33 = 1.0;
        return this;
    }

    /**
     * Set this matrix to a rotation transformation about the Y axis.
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Basic_rotations">http://en.wikipedia.org</a>
     * 
     * @param ang
     *            the angle in degrees
     * @return this
     */
    public Matrix4d rotationY(double ang) {
        double cos = Math.cos(Math.toRadians(ang));
        double sin = Math.sin(Math.toRadians(ang));
        m00 = cos;
        m01 = 0.0;
        m02 = -sin;
        m03 = 0.0;
        m10 = 0.0;
        m11 = 1.0;
        m12 = 0.0;
        m13 = 0.0;
        m20 = sin;
        m21 = 0.0;
        m22 = cos;
        m23 = 0.0;
        m30 = 0.0;
        m31 = 0.0;
        m32 = 0.0;
        m33 = 1.0;
        return this;
    }

    /**
     * Set this matrix to a rotation transformation about the Z axis.
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Basic_rotations">http://en.wikipedia.org</a>
     * 
     * @param ang
     *            the angle in degrees
     * @return this
     */
    public Matrix4d rotationZ(double ang) {
        double cos = Math.cos(Math.toRadians(ang));
        double sin = Math.sin(Math.toRadians(ang));
        m00 = cos;
        m01 = sin;
        m02 = 0.0;
        m03 = 0.0;
        m10 = -sin;
        m11 = cos;
        m12 = 0.0;
        m13 = 0.0;
        m20 = 0.0;
        m21 = 0.0;
        m22 = 0.0;
        m23 = 0.0;
        m30 = 0.0;
        m31 = 0.0;
        m32 = 0.0;
        m33 = 1.0;
        return this;
    }

    /**
     * Set this matrix to a rotation matrix which rotates the given radians about a given axis.
     * 
     * @param angle
     *          the angle in degrees
     * @param axis
     *          the axis to rotate about
     * @return this
     */
    public Matrix4d rotation(double angle, Vector3d axis) {
        return rotation(angle, axis.x, axis.y, axis.z);
    }
    
    /**
     * Transform the given vector by this matrix.
     * 
     * @param v
     *          the vector to transform
     * @return this
     */
    public Matrix4d transform(Vector4d v) {
        v.mul(this);
        return this;
    }

    /**
     * Transform/multiply the given vector by this matrix and store the result in <code>dest</code>.
     * 
     * @param v
     *          the vector to transform
     * @param dest
     *          will contain the result
     * @return this
     */
    public Matrix4d transform(Vector4d v, Vector4d dest) {
        v.mul(this, dest);
        return this;
    }

    /**
     * Set the upper 3x3 matrix of this {@link Matrix4d} to the given {@link Matrix3d} and the rest to the identity.
     * 
     * @param mat
     *          the 3x3 matrix
     * @return this
     */
    public Matrix4d setMatrix3(Matrix3d mat) {
        m00 = mat.m00;
        m01 = mat.m01;
        m02 = mat.m02;
        m03 = 0.0;
        m10 = mat.m10;
        m11 = mat.m11;
        m12 = mat.m12;
        m13 = 0.0;
        m20 = mat.m20;
        m21 = mat.m21;
        m22 = mat.m22;
        m23 = 0.0;
        m30 = 0.0;
        m31 = 0.0;
        m32 = 0.0;
        m33 = 1.0;
        return this;
    }

    /**
     * Apply scaling to the this matrix by scaling the unit axes by the given x,
     * y and z factors and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>S</code> the scaling matrix,
     * then the new matrix will be <code>M * S</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * S * v</code>
     * , the scaling will be applied first!
     * 
     * @param x
     *            the factor of the x component
     * @param y
     *            the factor of the y component
     * @param z
     *            the factor of the z component
     * @param dest
     *            will hold the result
     * @return this
     */
    public Matrix4d scale(double x, double y, double z, Matrix4d dest) {
        // scale matrix elements:
        // m00 = x, m11 = y, m22 = z
        // m33 = 1
        // all others = 0
        dest.m00 = m00 * x;
        dest.m01 = m01 * x;
        dest.m02 = m02 * x;
        dest.m03 = m03 * x;
        dest.m10 = m10 * y;
        dest.m11 = m11 * y;
        dest.m12 = m12 * y;
        dest.m13 = m13 * y;
        dest.m20 = m20 * z;
        dest.m21 = m21 * z;
        dest.m22 = m22 * z;
        dest.m23 = m23 * z;
        dest.m30 = m30;
        dest.m31 = m31;
        dest.m32 = m32;
        dest.m33 = m33;
        return this;
    }

    /**
     * Apply scaling to the this matrix by scaling the unit axes by the given x,
     * y and z factors.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>S</code> the scaling matrix,
     * then the new matrix will be <code>M * S</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * S * v</code>
     * , the scaling will be applied first!
     * 
     * @param x
     *            the factor of the x component
     * @param y
     *            the factor of the y component
     * @param z
     *            the factor of the z component
     * @return this
     */
    public Matrix4d scale(double x, double y, double z) {
        return scale(x, y, z, this);
    }

    /**
     * Apply scaling to the this matrix by scaling the unit axes by the given x,
     * y and z factors.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>S</code> the scaling matrix,
     * then the new matrix will be <code>M * S</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * S * v</code>
     * , the scaling will be applied first!
     * 
     * @param xyz
     *            the factors of the x, y and z components, respectively
     * @return this
     */
    public Matrix4d scale(Vector3d xyz) {
        // scale matrix elements:
        // m00 = x, m11 = y, m22 = z
        // m33 = 1
        // all others = 0
        m00 = m00 * xyz.x;
        m01 = m01 * xyz.x;
        m02 = m02 * xyz.x;
        m03 = m03 * xyz.x;
        m10 = m10 * xyz.y;
        m11 = m11 * xyz.y;
        m12 = m12 * xyz.y;
        m13 = m13 * xyz.y;
        m20 = m20 * xyz.z;
        m21 = m21 * xyz.z;
        m22 = m22 * xyz.z;
        m23 = m23 * xyz.z;
        return this;
    }

    /**
     * Apply scaling to this matrix by uniformly scaling all unit axes by the given xyz factor.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>S</code> the scaling matrix,
     * then the new matrix will be <code>M * S</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * S * v</code>
     * , the scaling will be applied first!
     * 
     * @see #scale(double, double, double)
     * 
     * @param xyz
     *            the factor for all components
     * @return this
     */
    public Matrix4d scale(double xyz) {
        return scale(xyz, xyz, xyz);
    }

    /**
     * Apply rotation to this matrix by rotating the given amount of degrees
     * about the given axis specified as x, y and z components and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the rotation matrix,
     * then the new matrix will be <code>M * R</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * R * v</code>
     * , the rotation will be applied first!
     * 
     * @param ang
     *            the angle is in degrees
     * @param x
     *            the x component of the axis
     * @param y
     *            the y component of the axis
     * @param z
     *            the z component of the axis
     * @param dest
     *            will hold the result
     * @return this
     */
    public Matrix4d rotate(double ang, double x, double y, double z, Matrix4d dest) {
        // rotation matrix elements:
        // m30, m31, m32, m03, m13, m23 = 0
        // m33 = 1
        double cos = Math.cos(Math.toRadians(ang));
        double sin = Math.sin(Math.toRadians(ang));
        double m00 = (cos + x * x * (1.0 - cos));
        double m10 = x * y * (1.0 - cos) - z * sin;
        double m20 = x * z * (1.0 - cos) + y * sin;
        double m01 = y * x * (1.0 - cos) + z * sin;
        double m11 = cos + y * y * (1.0 - cos);
        double m21 = y * z * (1.0 - cos) - x * sin;
        double m02 = z * x * (1.0 - cos) - y * sin;
        double m12 = z * y * (1.0 - cos) + x * sin;
        double m22 = cos + z * z * (1.0 - cos);

        double nm00 = m00 * m00 + m10 * m01 + m20 * m02;
        double nm01 = m01 * m00 + m11 * m01 + m21 * m02;
        double nm02 = m02 * m00 + m12 * m01 + m22 * m02;
        double nm03 = m03 * m00 + m13 * m01 + m23 * m02;
        double nm10 = m00 * m10 + m10 * m11 + m20 * m12;
        double nm11 = m01 * m10 + m11 * m11 + m21 * m12;
        double nm12 = m02 * m10 + m12 * m11 + m22 * m12;
        double nm13 = m03 * m10 + m13 * m11 + m23 * m12;
        double nm20 = m00 * m20 + m10 * m21 + m20 * m22;
        double nm21 = m01 * m20 + m11 * m21 + m21 * m22;
        double nm22 = m02 * m20 + m12 * m21 + m22 * m22;
        double nm23 = m03 * m20 + m13 * m21 + m23 * m22;

        dest.m00 = nm00;
        dest.m01 = nm01;
        dest.m02 = nm02;
        dest.m03 = nm03;
        dest.m10 = nm10;
        dest.m11 = nm11;
        dest.m12 = nm12;
        dest.m13 = nm13;
        dest.m20 = nm20;
        dest.m21 = nm21;
        dest.m22 = nm22;
        dest.m23 = nm23;
        dest.m30 = m30;
        dest.m31 = m31;
        dest.m32 = m32;
        dest.m33 = m33;

        return this;
    }

    /**
     * Apply rotation to this matrix by rotating the given amount of degrees
     * about the given axis specified as x, y and z components.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the rotation matrix,
     * then the new matrix will be <code>M * R</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * R * v</code>
     * , the rotation will be applied first!
     * <p>
     * In order to set the matrix to a rotation matrix without post-multiplying the rotation
     * transformation, use {@link #rotation(double, double, double, double) rotation()}.
     * 
     * @see #rotation(double, double, double, double)
     *  
     * @param ang
     *            the angle is in degrees
     * @param x
     *            the x component of the axis
     * @param y
     *            the y component of the axis
     * @param z
     *            the z component of the axis
     * @return this
     */
    public Matrix4d rotate(double ang, double x, double y, double z) {
        return rotate(ang, x, y, z, this);
    }

    /**
     * Apply a translation to this matrix by translating by the given number of
     * units in x, y and z.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>T</code> the translation
     * matrix, then the new current matrix will be <code>M * T</code>. So when
     * transforming a vector <code>v</code> with the new matrix by using
     * <code>M * T * v</code>, the translation will be applied first!
     * <p>
     * In order to set the matrix to a translation transformation without post-multiplying
     * it, use {@link #translation(double, double, double)}.
     * 
     * @see #translation(double, double, double)
     * 
     * @param x
     *            the offset in x
     * @param y
     *            the offset in y
     * @param z
     *            the offset in z
     * @return this
     */
    public Matrix4d translate(double x, double y, double z) {
        // translation matrix elements:
        // m00, m11, m22, m33 = 1
        // m30 = x, m31 = y, m32 = z
        // all others = 0
        m30 = m00 * x + m10 * y + m20 * z + m30;
        m31 = m01 * x + m11 * y + m21 * z + m31;
        m32 = m02 * x + m12 * y + m22 * z + m32;
        m33 = m03 * x + m13 * y + m23 * z + m33;
        return this;
    }

    /**
     * Apply a translation to this matrix by translating by the given number of
     * units in x, y and z.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>T</code> the translation
     * matrix, then the new matrix will be <code>M * T</code>. So when
     * transforming a vector <code>v</code> with the new matrix by using
     * <code>M * T * v</code>, the translation will be applied first!
     * <p>
     * In order to set the matrix to a translation transformation without post-multiplying
     * it, use {@link #translation(Vector3f)}.
     * 
     * @see #translation(Vector3d)
     * 
     * @param point
     *          the point by which to translate
     * @return this
     */
    public Matrix4d translate(Vector3d point) {
        return translate(point.x, point.y, point.z);
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeDouble(m00);
        out.writeDouble(m01);
        out.writeDouble(m02);
        out.writeDouble(m03);
        out.writeDouble(m10);
        out.writeDouble(m11);
        out.writeDouble(m12);
        out.writeDouble(m13);
        out.writeDouble(m20);
        out.writeDouble(m21);
        out.writeDouble(m22);
        out.writeDouble(m23);
        out.writeDouble(m30);
        out.writeDouble(m31);
        out.writeDouble(m32);
        out.writeDouble(m33);
    }

    public void readExternal(ObjectInput in) throws IOException,
            ClassNotFoundException {
        m00 = in.readDouble();
        m01 = in.readDouble();
        m02 = in.readDouble();
        m03 = in.readDouble();
        m10 = in.readDouble();
        m11 = in.readDouble();
        m12 = in.readDouble();
        m13 = in.readDouble();
        m20 = in.readDouble();
        m21 = in.readDouble();
        m22 = in.readDouble();
        m23 = in.readDouble();
        m30 = in.readDouble();
        m31 = in.readDouble();
        m32 = in.readDouble();
        m33 = in.readDouble();
    }
    

    /**
     * Apply rotation about the X axis to this matrix by rotating the given amount of degrees 
     * and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the rotation matrix,
     * then the new matrix will be <code>M * R</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * R * v</code>, the
     * rotation will be applied first!
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Basic_rotations">http://en.wikipedia.org</a>
     * 
     * @param ang
     *            the angle in degrees
     * @param dest
     *            will hold the result
     * @return this
     */
    public Matrix4d rotateX(double ang, Matrix4d dest) {
        double cos = Math.cos(Math.toRadians(ang));
        double sin = Math.sin(Math.toRadians(ang));
        double rm11 = cos;
        double rm12 = sin;
        double rm21 = -sin;
        double rm22 = cos;

        // add temporaries for dependent values
        double nm10 = m10 * rm11 + m20 * rm12;
        double nm11 = m11 * rm11 + m21 * rm12;
        double nm12 = m12 * rm11 + m22 * rm12;
        double nm13 = m13 * rm11 + m23 * rm12;
        // set non-dependent values directly
        dest.m20 = m10 * rm21 + m20 * rm22;
        dest.m21 = m11 * rm21 + m21 * rm22;
        dest.m22 = m12 * rm21 + m22 * rm22;
        dest.m23 = m13 * rm21 + m23 * rm22;
        // set other values
        dest.m10 = nm10;
        dest.m11 = nm11;
        dest.m12 = nm12;
        dest.m13 = nm13;
        dest.m00 = m00;
        dest.m01 = m01;
        dest.m02 = m02;
        dest.m03 = m03;
        dest.m30 = m30;
        dest.m31 = m31;
        dest.m32 = m32;
        dest.m33 = m33;

        return this;
    }

    /**
     * Apply rotation about the X axis to this matrix by rotating the given amount of degrees.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the rotation matrix,
     * then the new matrix will be <code>M * R</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * R * v</code>, the
     * rotation will be applied first!
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Basic_rotations">http://en.wikipedia.org</a>
     * 
     * @param ang
     *            the angle in degrees
     * @return this
     */
    public Matrix4d rotateX(double ang) {
        return rotateX(ang, this);
    }

    /**
     * Apply rotation about the Y axis to this matrix by rotating the given amount of degrees 
     * and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the rotation matrix,
     * then the new matrix will be <code>M * R</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * R * v</code>, the
     * rotation will be applied first!
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Basic_rotations">http://en.wikipedia.org</a>
     * 
     * @param ang
     *            the angle in degrees
     * @param dest
     *            will hold the result
     * @return this
     */
    public Matrix4d rotateY(double ang, Matrix4d dest) {
        double cos = Math.cos(Math.toRadians(ang));
        double sin = Math.sin(Math.toRadians(ang));
        double rm00 = cos;
        double rm02 = -sin;
        double rm20 = sin;
        double rm22 = cos;

        // add temporaries for dependent values
        double nm00 = m00 * rm00 + m20 * rm02;
        double nm01 = m01 * rm00 + m21 * rm02;
        double nm02 = m02 * rm00 + m22 * rm02;
        double nm03 = m03 * rm00 + m23 * rm02;
        // set non-dependent values directly
        dest.m20 = m00 * rm20 + m20 * rm22;
        dest.m21 = m01 * rm20 + m21 * rm22;
        dest.m22 = m02 * rm20 + m22 * rm22;
        dest.m23 = m03 * rm20 + m23 * rm22;
        // set other values
        dest.m00 = nm00;
        dest.m01 = nm01;
        dest.m02 = nm02;
        dest.m03 = nm03;
        dest.m10 = m10;
        dest.m11 = m11;
        dest.m12 = m12;
        dest.m13 = m13;
        dest.m30 = m30;
        dest.m31 = m31;
        dest.m32 = m32;
        dest.m33 = m33;

        return this;
    }

    /**
     * Apply rotation about the Y axis to this matrix by rotating the given amount of degrees.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the rotation matrix,
     * then the new matrix will be <code>M * R</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * R * v</code>, the
     * rotation will be applied first!
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Basic_rotations">http://en.wikipedia.org</a>
     * 
     * @param ang
     *            the angle in degrees
     * @return this
     */
    public Matrix4d rotateY(double ang) {
        return rotateY(ang, this);
    }

    /**
     * Apply rotation about the Z axis to this matrix by rotating the given amount of degrees 
     * and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the rotation matrix,
     * then the new matrix will be <code>M * R</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * R * v</code>, the
     * rotation will be applied first!
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Basic_rotations">http://en.wikipedia.org</a>
     * 
     * @param ang
     *            the angle in degrees
     * @param dest
     *            will hold the result
     * @return this
     */
    public Matrix4d rotateZ(double ang, Matrix4d dest) {
        double cos = Math.cos(Math.toRadians(ang));
        double sin = Math.sin(Math.toRadians(ang));
        double rm00 = cos;
        double rm01 = sin;
        double rm10 = -sin;
        double rm11 = cos;

        // add temporaries for dependent values
        double nm00 = m00 * rm00 + m10 * rm01;
        double nm01 = m01 * rm00 + m11 * rm01;
        double nm02 = m02 * rm00 + m12 * rm01;
        double nm03 = m03 * rm00 + m13 * rm01;
        // set non-dependent values directly
        dest.m10 = m00 * rm10 + m10 * rm11;
        dest.m11 = m01 * rm10 + m11 * rm11;
        dest.m12 = m02 * rm10 + m12 * rm11;
        dest.m13 = m03 * rm10 + m13 * rm11;
        // set other values
        dest.m00 = nm00;
        dest.m01 = nm01;
        dest.m02 = nm02;
        dest.m03 = nm03;
        dest.m20 = m20;
        dest.m21 = m21;
        dest.m22 = m22;
        dest.m23 = m23;
        dest.m30 = m30;
        dest.m31 = m31;
        dest.m32 = m32;
        dest.m33 = m33;
        return this;
    }

    /**
     * Apply rotation about the Z axis to this matrix by rotating the given amount of degrees.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the rotation matrix,
     * then the new matrix will be <code>M * R</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * R * v</code>, the
     * rotation will be applied first!
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Basic_rotations">http://en.wikipedia.org</a>
     * 
     * @param ang
     *            the angle in degrees
     * @return this
     */
    public Matrix4d rotateZ(double ang) {
        return rotateZ(ang, this);
    }

    /**
     * Set this matrix to a rotation transformation using the given {@link AxisAngle4f}.
     * <p>
     * The resulting matrix can be multiplied against another transformation
     * matrix to obtain an additional rotation.
     * <p>
     * In order to apply the rotation transformation to an existing transformation,
     * use {@link #rotate(AxisAngle4f) rotate()} instead.
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Axis_and_angle">http://en.wikipedia.org</a>
     *
     * @see #rotate(AxisAngle4f)
     * 
     * @param angleAxis
     *          the {@link AxisAngle4f} (needs to be {@link AxisAngle4f#normalize() normalized})
     * @return this
     */
    public Matrix4d rotation(AxisAngle4f angleAxis) {
        return rotation(angleAxis.angle, angleAxis.x, angleAxis.y, angleAxis.z);
    }

    /**
     * Set this matrix to the rotation transformation of the given {@link Quaternionf}.
     * <p>
     * The resulting matrix can be multiplied against another transformation
     * matrix to obtain an additional rotation.
     * <p>
     * In order to apply the rotation transformation to an existing transformation,
     * use {@link #rotate(Quaterniond) rotate()} instead.
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Quaternion">http://en.wikipedia.org</a>
     * 
     * @see #rotate(Quaterniond)
     * 
     * @param quat
     *          the {@link Quaterniond}
     * @return this
     */
    public Matrix4d rotation(Quaterniond quat) {
        double q00 = 2.0 * quat.x * quat.x;
        double q11 = 2.0 * quat.y * quat.y;
        double q22 = 2.0 * quat.z * quat.z;
        double q01 = 2.0 * quat.x * quat.y;
        double q02 = 2.0 * quat.x * quat.z;
        double q03 = 2.0 * quat.x * quat.w;
        double q12 = 2.0 * quat.y * quat.z;
        double q13 = 2.0 * quat.y * quat.w;
        double q23 = 2.0 * quat.z * quat.w;

        m00 = 1.0 - q11 - q22;
        m01 = q01 + q23;
        m02 = q02 - q13;
        m03 = 0.0;
        m10 = q01 - q23;
        m11 = 1.0 - q22 - q00;
        m12 = q12 + q03;
        m13 = 0.0;
        m20 = q02 + q13;
        m21 = q12 - q03;
        m22 = 1.0 - q11 - q00;
        m23 = 0.0;
        m30 = 0.0;
        m31 = 0.0;
        m32 = 0.0;
        m33 = 1.0;

        return this;
    }

    /**
     * Apply the rotation transformation of the given {@link Quaterniond} to this matrix and store
     * the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>Q</code> the rotation matrix obtained from the given quaternion,
     * then the new matrix will be <code>M * Q</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * Q * v</code>,
     * the quaternion rotation will be applied first!
     * <p>
     * In order to set the matrix to a rotation transformation without post-multiplying,
     * use {@link #rotation(Quaterniond)}.
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Quaternion">http://en.wikipedia.org</a>
     * 
     * @see #rotation(Quaterniond)
     * 
     * @param quat
     *          the {@link Quaterniond}
     * @param dest
     *          will hold the result
     * @return this
     */
    public Matrix4d rotate(Quaterniond quat, Matrix4d dest) {
        double q00 = 2.0 * quat.x * quat.x;
        double q11 = 2.0 * quat.y * quat.y;
        double q22 = 2.0 * quat.z * quat.z;
        double q01 = 2.0 * quat.x * quat.y;
        double q02 = 2.0 * quat.x * quat.z;
        double q03 = 2.0 * quat.x * quat.w;
        double q12 = 2.0 * quat.y * quat.z;
        double q13 = 2.0 * quat.y * quat.w;
        double q23 = 2.0 * quat.z * quat.w;

        double rm00 = 1.0 - q11 - q22;
        double rm01 = q01 + q23;
        double rm02 = q02 - q13;
        double rm10 = q01 - q23;
        double rm11 = 1.0 - q22 - q00;
        double rm12 = q12 + q03;
        double rm20 = q02 + q13;
        double rm21 = q12 - q03;
        double rm22 = 1.0 - q11 - q00;

        double nm00 = m00 * rm00 + m10 * rm01 + m20 * rm02;
        double nm01 = m01 * rm00 + m11 * rm01 + m21 * rm02;
        double nm02 = m02 * rm00 + m12 * rm01 + m22 * rm02;
        double nm03 = m03 * rm00 + m13 * rm01 + m23 * rm02;
        double nm10 = m00 * rm10 + m10 * rm11 + m20 * rm12;
        double nm11 = m01 * rm10 + m11 * rm11 + m21 * rm12;
        double nm12 = m02 * rm10 + m12 * rm11 + m22 * rm12;
        double nm13 = m03 * rm10 + m13 * rm11 + m23 * rm12;
        dest.m20 = m00 * rm20 + m10 * rm21 + m20 * rm22;
        dest.m21 = m01 * rm20 + m11 * rm21 + m21 * rm22;
        dest.m22 = m02 * rm20 + m12 * rm21 + m22 * rm22;
        dest.m23 = m03 * rm20 + m13 * rm21 + m23 * rm22;
        dest.m00 = nm00;
        dest.m01 = nm01;
        dest.m02 = nm02;
        dest.m03 = nm03;
        dest.m10 = nm10;
        dest.m11 = nm11;
        dest.m12 = nm12;
        dest.m13 = nm13;
        dest.m30 = m30;
        dest.m31 = m31;
        dest.m32 = m32;
        dest.m33 = m33;

        return this;
    }

    /**
     * Apply the rotation transformation of the given {@link Quaterniond} to this matrix.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>Q</code> the rotation matrix obtained from the given quaternion,
     * then the new matrix will be <code>M * Q</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * Q * v</code>,
     * the quaternion rotation will be applied first!
     * <p>
     * In order to set the matrix to a rotation transformation without post-multiplying,
     * use {@link #rotation(Quaterniond)}.
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Quaternion">http://en.wikipedia.org</a>
     * 
     * @see #rotation(Quaterniond)
     * 
     * @param quat
     *          the {@link Quaterniond}
     * @return this
     */
    public Matrix4d rotate(Quaterniond quat) {
        return rotate(quat, this);
    }

    /**
     * Apply a rotation transformation, rotating about the given {@link AxisAngle4f}, to this matrix.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>A</code> the rotation matrix obtained from the given {@link AxisAngle4f},
     * then the new matrix will be <code>M * A</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * A * v</code>,
     * the {@link AxisAngle4f} rotation will be applied first!
     * <p>
     * In order to set the matrix to a rotation transformation without post-multiplying,
     * use {@link #rotation(AxisAngle4f)}.
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Axis_and_angle">http://en.wikipedia.org</a>
     * 
     * @see #rotate(double, double, double, double)
     * @see #rotation(AxisAngle4f)
     * 
     * @param axisAngle
     *          the {@link AxisAngle4f} (needs to be {@link AxisAngle4f#normalize() normalized})
     * @return this
     */
    public Matrix4d rotate(AxisAngle4f axisAngle) {
        return rotate(axisAngle.angle, axisAngle.x, axisAngle.y, axisAngle.z);
    }

    /**
     * Apply a rotation transformation, rotating about the given {@link AxisAngle4f} and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>A</code> the rotation matrix obtained from the given {@link AxisAngle4f},
     * then the new matrix will be <code>M * A</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * A * v</code>,
     * the {@link AxisAngle4f} rotation will be applied first!
     * <p>
     * In order to set the matrix to a rotation transformation without post-multiplying,
     * use {@link #rotation(AxisAngle4f)}.
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Axis_and_angle">http://en.wikipedia.org</a>
     * 
     * @see #rotate(double, double, double, double)
     * @see #rotation(AxisAngle4f)
     * 
     * @param axisAngle
     *          the {@link AxisAngle4f} (needs to be {@link AxisAngle4f#normalize() normalized})
     * @param dest
     *          will hold the result
     * @return this
     */
    public Matrix4d rotate(AxisAngle4f axisAngle, Matrix4d dest) {
        return rotate(axisAngle.angle, axisAngle.x, axisAngle.y, axisAngle.z, dest);
    }

    /**
     * Apply a rotation transformation, rotating the given degree about the specified axis, to this matrix.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>A</code> the rotation matrix obtained from the given angle and axis,
     * then the new matrix will be <code>M * A</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * A * v</code>,
     * the axis-angle rotation will be applied first!
     * <p>
     * In order to set the matrix to a rotation transformation without post-multiplying,
     * use {@link #rotation(double, Vector3d)}.
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Axis_and_angle">http://en.wikipedia.org</a>
     * 
     * @see #rotate(double, double, double, double)
     * @see #rotation(double, Vector3d)
     * 
     * @param angle
     *          the angle in degrees
     * @param axis
     *          the rotation axis (needs to be {@link Vector3d#normalize() normalized})
     * @return this
     */
    public Matrix4d rotate(double angle, Vector3d axis) {
        return rotate(angle, axis.x, axis.y, axis.z);
    }

    /**
     * Apply a rotation transformation, rotating the given degree about the specified axis and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>A</code> the rotation matrix obtained from the given angle and axis,
     * then the new matrix will be <code>M * A</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * A * v</code>,
     * the axis-angle rotation will be applied first!
     * <p>
     * In order to set the matrix to a rotation transformation without post-multiplying,
     * use {@link #rotation(double, Vector3d)}.
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Axis_and_angle">http://en.wikipedia.org</a>
     * 
     * @see #rotate(double, double, double, double)
     * @see #rotation(double, Vector3d)
     * 
     * @param angle
     *          the angle in degrees
     * @param axis
     *          the rotation axis (needs to be {@link Vector3d#normalize() normalized})
     * @param dest
     *          will hold the result
     * @return this
     */
    public Matrix4d rotate(double angle, Vector3d axis, Matrix4d dest) {
        return rotate(angle, axis.x, axis.y, axis.z, dest);
    }

    /**
     * Get the row at the given <code>row</code> index, starting with <code>0</code>.
     * 
     * @param row
     *          the row index in <tt>[0..3]</tt>
     * @param dest
     *          will hold the row components
     * @throws IndexOutOfBoundsException if <code>row</code> is not in <tt>[0..3]</tt>
     */
    public void getRow(int row, Vector4d dest) throws IndexOutOfBoundsException {
        switch (row) {
        case 0:
            dest.x = m00;
            dest.y = m10;
            dest.z = m20;
            dest.w = m30;
            break;
        case 1:
            dest.x = m01;
            dest.y = m11;
            dest.z = m21;
            dest.w = m31;
            break;
        case 2:
            dest.x = m02;
            dest.y = m12;
            dest.z = m22;
            dest.w = m32;
            break;
        case 3:
            dest.x = m03;
            dest.y = m13;
            dest.z = m23;
            dest.w = m33;
            break;
        default:
            throw new IndexOutOfBoundsException();
        }
    }

    /**
     * Get the column at the given <code>column</code> index, starting with <code>0</code>.
     * 
     * @param column
     *          the column index in <tt>[0..3]</tt>
     * @param dest
     *          will hold the column components
     * @throws IndexOutOfBoundsException if <code>column</code> is not in <tt>[0..3]</tt>
     */
    public void getColumn(int column, Vector4d dest) throws IndexOutOfBoundsException {
        switch (column) {
        case 0:
            dest.x = m00;
            dest.y = m01;
            dest.z = m02;
            dest.w = m03;
            break;
        case 1:
            dest.x = m10;
            dest.y = m11;
            dest.z = m12;
            dest.w = m13;
            break;
        case 2:
            dest.x = m20;
            dest.y = m21;
            dest.z = m22;
            dest.w = m23;
            break;
        case 3:
            dest.x = m30;
            dest.y = m31;
            dest.z = m32;
            dest.w = m32;
            break;
        default:
            throw new IndexOutOfBoundsException();
        }
    }

    /**
     * Compute a normal matrix from the top-left 3x3 submatrix of <code>this</code>
     * and store it into the top-left 3x3 submatrix of <code>dest</code>.
     * All other values of <code>dest</code> will be set to {@link #identity() identity}.
     * <p>
     * The normal matrix of <tt>m</tt> is the transpose of the inverse of <tt>m</tt>.
     * In the special case of an orthonormal 3x3 matrix (one that maps any two perpendicular 
     * unit vectors to another pair of perpendicular unit vectors) only the transpose is
     * computed.
     * 
     * @param dest
     *             will hold the result
     * @return this
     */
    public Matrix4d normal(Matrix4d dest) {
        // see: http://mathworld.wolfram.com/OrthogonalMatrix.html
        double det = determinant3x3();
        double diff = Math.abs(Math.abs(det) - 1.0);
        if (diff < 1E-8) {
            /* The fast path, if only 1:1:1 scaling is being used */
            return transpose3x3(dest);
        }
        /* The general case */
        double s = 1.0 / det;
        /* Invert and transpose in one go */
        dest.set((m11 * m22 - m21 * m12) * s,
                -(m10 * m22 - m20 * m12) * s,
                 (m10 * m21 - m20 * m11) * s,
                 0.0,
                -(m01 * m22 - m21 * m02) * s,
                 (m00 * m22 - m20 * m02) * s,
                -(m00 * m21 - m20 * m01) * s,
                 0.0,
                 (m01 * m12 - m11 * m02) * s,
                -(m00 * m12 - m10 * m02) * s,
                 (m00 * m11 - m10 * m01) * s,
                 0.0,
                 0.0, 0.0, 0.0, 1.0);
        return this;
    }

    /**
     * Compute a normal matrix from the top-left 3x3 submatrix of <code>this</code>
     * and store it into <code>dest</code>.
     * <p>
     * The normal matrix of <tt>m</tt> is the transpose of the inverse of <tt>m</tt>.
     * In the special case of an orthonormal 3x3 matrix (one that maps any two perpendicular 
     * unit vectors to another pair of perpendicular unit vectors) only the transpose is
     * computed.
     * 
     * @param dest
     *             will hold the result
     * @return this
     */
    public Matrix4d normal(Matrix3d dest) {
        // see: http://mathworld.wolfram.com/OrthogonalMatrix.html
        double det = determinant3x3();
        double diff = Math.abs(Math.abs(det) - 1.0);
        if (diff < 1E-8) {
            /* The fast path, if only 1:1:1 scaling is being used */
            return transpose3x3(dest);
        }
        /* The general case */
        double s = 1.0 / det;
        /* Invert and transpose in one go */
        dest.m00 =  (m11 * m22 - m21 * m12) * s;
        dest.m01 = -(m10 * m22 - m20 * m12) * s;
        dest.m02 =  (m10 * m21 - m20 * m11) * s;
        dest.m10 = -(m01 * m22 - m21 * m02) * s;
        dest.m11 =  (m00 * m22 - m20 * m02) * s;
        dest.m12 = -(m00 * m21 - m20 * m01) * s;
        dest.m20 =  (m01 * m12 - m11 * m02) * s;
        dest.m21 = -(m00 * m12 - m10 * m02) * s;
        dest.m22 =  (m00 * m11 - m10 * m01) * s;
        return this;
    }

    /**
     * Unproject the given window coordinates <tt>(winX, winY, winZ)</tt> by <code>this</code> matrix using the specified viewport.
     * <p>
     * This method first converts the given window coordinates to normalized device coordinates in the range <tt>[-1..1]</tt>
     * and then transforms those NDC coordinates by the inverse of <code>this</code> matrix.  
     * <p>
     * The depth range of <tt>winZ</tt> is assumed to be <tt>[0..1]</tt>, which is also the OpenGL default.
     * <p>
     * This method reads the four viewport parameters from the current IntBuffer's {@link Buffer#position() position}
     * and does not modify the buffer's position.
     * <p>
     * As a necessary computation step for unprojecting, this method computes the inverse of <code>this</code> matrix and stores
     * it into the <code>inverseOut</code> parameter matrix. In order to avoid computing the matrix inverse with every
     * invocation, the inverse of <code>this</code> matrix can be built once outside and then the method {@link #unprojectInv(double, double, double, IntBuffer, Vector4d) unprojectInv()}
     * can be invoked on it.
     * 
     * @see #unprojectInv(double, double, double, IntBuffer, Vector4d)
     * 
     * @param winX
     *          the x-coordinate in window coordinates (pixels)
     * @param winY
     *          the y-coordinate in window coordinates (pixels)
     * @param winZ
     *          the z-coordinate, which is the depth value in <tt>[0..1]</tt>
     * @param viewport
     *          the viewport described by <tt>[x, y, width, height]</tt>
     * @param inverseOut
     *          will hold the inverse of <code>this</code> after the method returns
     * @param dest
     *          will hold the unprojected position
     * @return this
     */
    public Matrix4d unproject(double winX, double winY, double winZ, IntBuffer viewport, Matrix4d inverseOut, Vector4d dest) {
        this.invert(inverseOut);
        inverseOut.unprojectInv(winX, winY, winZ, viewport, dest);
        return this;
    }

    /**
     * Unproject the given window coordinates <tt>(winX, winY, winZ)</tt> by <code>this</code> matrix using the specified viewport.
     * <p>
     * This method first converts the given window coordinates to normalized device coordinates in the range <tt>[-1..1]</tt>
     * and then transforms those NDC coordinates by the inverse of <code>this</code> matrix.  
     * <p>
     * The depth range of <tt>winZ</tt> is assumed to be <tt>[0..1]</tt>, which is also the OpenGL default.
     * <p>
     * This method reads the four viewport parameters from the current IntBuffer's {@link Buffer#position() position}
     * and does not modify the buffer's position.
     * <p>
     * As a necessary computation step for unprojecting, this method computes the inverse of <code>this</code> matrix and stores
     * it into the <code>inverseOut</code> parameter matrix. In order to avoid computing the matrix inverse with every
     * invocation, the inverse of <code>this</code> matrix can be built once outside and then the method {@link #unprojectInv(double, double, double, IntBuffer, Vector3d) unprojectInv()}
     * can be invoked on it.
     * 
     * @see #unprojectInv(double, double, double, IntBuffer, Vector3d)
     * 
     * @param winX
     *          the x-coordinate in window coordinates (pixels)
     * @param winY
     *          the y-coordinate in window coordinates (pixels)
     * @param winZ
     *          the z-coordinate, which is the depth value in <tt>[0..1]</tt>
     * @param viewport
     *          the viewport described by <tt>[x, y, width, height]</tt>
     * @param inverseOut
     *          will hold the inverse of <code>this</code> after the method returns
     * @param dest
     *          will hold the unprojected position
     * @return this
     */
    public Matrix4d unproject(double winX, double winY, double winZ, IntBuffer viewport, Matrix4d inverseOut, Vector3d dest) {
        this.invert(inverseOut);
        inverseOut.unprojectInv(winX, winY, winZ, viewport, dest);
        return this;
    }

    /**
     * Unproject the given window coordinates <code>winCoords</code> by <code>this</code> matrix using the specified viewport.
     * <p>
     * This method first converts the given window coordinates to normalized device coordinates in the range <tt>[-1..1]</tt>
     * and then transforms those NDC coordinates by the inverse of <code>this</code> matrix.  
     * <p>
     * The depth range of <tt>winCoords.z</tt> is assumed to be <tt>[0..1]</tt>, which is also the OpenGL default.
     * <p>
     * This method reads the four viewport parameters from the current IntBuffer's {@link Buffer#position() position}
     * and does not modify the buffer's position.
     * <p>
     * As a necessary computation step for unprojecting, this method computes the inverse of <code>this</code> matrix and stores
     * it into the <code>inverseOut</code> parameter matrix. In order to avoid computing the matrix inverse with every
     * invocation, the inverse of <code>this</code> matrix can be built once outside and then the method {@link #unprojectInv(double, double, double, IntBuffer, Vector4d) unprojectInv()}
     * can be invoked on it.
     * 
     * @see #unprojectInv(double, double, double, IntBuffer, Vector4d)
     * @see #unproject(double, double, double, IntBuffer, Matrix4d, Vector4d)
     * 
     * @param winCoords
     *          the window coordinates to unproject
     * @param viewport
     *          the viewport described by <tt>[x, y, width, height]</tt>
     * @param inverseOut
     *          will hold the inverse of <code>this</code> after the method returns
     * @param dest
     *          will hold the unprojected position
     * @return this
     */
    public Matrix4d unproject(Vector3d winCoords, IntBuffer viewport, Matrix4d inverseOut, Vector4d dest) {
        return unproject(winCoords.x, winCoords.y, winCoords.z, viewport, inverseOut, dest);
    }

    /**
     * Unproject the given window coordinates <code>winCoords</code> by <code>this</code> matrix using the specified viewport.
     * <p>
     * This method first converts the given window coordinates to normalized device coordinates in the range <tt>[-1..1]</tt>
     * and then transforms those NDC coordinates by the inverse of <code>this</code> matrix.  
     * <p>
     * The depth range of <tt>winCoords.z</tt> is assumed to be <tt>[0..1]</tt>, which is also the OpenGL default.
     * <p>
     * This method reads the four viewport parameters from the current IntBuffer's {@link Buffer#position() position}
     * and does not modify the buffer's position.
     * <p>
     * As a necessary computation step for unprojecting, this method computes the inverse of <code>this</code> matrix and stores
     * it into the <code>inverseOut</code> parameter matrix. In order to avoid computing the matrix inverse with every
     * invocation, the inverse of <code>this</code> matrix can be built once outside and then the method {@link #unprojectInv(double, double, double, IntBuffer, Vector4d) unprojectInv()}
     * can be invoked on it.
     * 
     * @see #unprojectInv(double, double, double, IntBuffer, Vector4d)
     * @see #unproject(double, double, double, IntBuffer, Matrix4d, Vector4d)
     * 
     * @param winCoords
     *          the window coordinates to unproject
     * @param viewport
     *          the viewport described by <tt>[x, y, width, height]</tt>
     * @param inverseOut
     *          will hold the inverse of <code>this</code> after the method returns
     * @param dest
     *          will hold the unprojected position
     * @return this
     */
    public Matrix4d unproject(Vector3d winCoords, IntBuffer viewport, Matrix4d inverseOut, Vector3d dest) {
        return unproject(winCoords.x, winCoords.y, winCoords.z, viewport, inverseOut, dest);
    }

    /**
     * Unproject the given window coordinates <code>winCoords</code> by <code>this</code> matrix using the specified viewport.
     * <p>
     * This method differs from {@link #unproject(Vector3d, IntBuffer, Matrix4d, Vector4d) unproject()} 
     * in that it assumes that <code>this</code> is already the inverse matrix of the original projection matrix.
     * It exists to avoid recomputing the matrix inverse with every invocation.
     * <p>
     * This method first converts the given window coordinates to normalized device coordinates in the range <tt>[-1..1]</tt>
     * and then transforms those NDC coordinates by <code>this</code> matrix.  
     * <p>
     * The depth range of <tt>winCoords.z</tt> is assumed to be <tt>[0..1]</tt>, which is also the OpenGL default.
     * <p>
     * This method reads the four viewport parameters from the current IntBuffer's {@link Buffer#position() position}
     * and does not modify the buffer's position.
     * 
     * @see #unproject(Vector3d, IntBuffer, Matrix4d, Vector4d)
     * 
     * @param winCoords
     *          the window coordinates to unproject
     * @param viewport
     *          the viewport described by <tt>[x, y, width, height]</tt>
     * @param dest
     *          will hold the unprojected position
     * @return this
     */
    public Matrix4d unprojectInv(Vector3d winCoords, IntBuffer viewport, Vector4d dest) {
        return unprojectInv(winCoords.x, winCoords.y, winCoords.z, viewport, dest);
    }

    /**
     * Unproject the given window coordinates <tt>(winX, winY, winZ)</tt> by <code>this</code> matrix using the specified viewport.
     * <p>
     * This method differs from {@link #unproject(double, double, double, IntBuffer, Matrix4d, Vector4d) unproject()} 
     * in that it assumes that <code>this</code> is already the inverse matrix of the original projection matrix.
     * It exists to avoid recomputing the matrix inverse with every invocation.
     * <p>
     * This method first converts the given window coordinates to normalized device coordinates in the range <tt>[-1..1]</tt>
     * and then transforms those NDC coordinates by <code>this</code> matrix.  
     * <p>
     * The depth range of <tt>winZ</tt> is assumed to be <tt>[0..1]</tt>, which is also the OpenGL default.
     * <p>
     * This method reads the four viewport parameters from the current IntBuffer's {@link Buffer#position() position}
     * and does not modify the buffer's position.
     * 
     * @see #unproject(double, double, double, IntBuffer, Matrix4d, Vector4d)
     * 
     * @param winX
     *          the x-coordinate in window coordinates (pixels)
     * @param winY
     *          the y-coordinate in window coordinates (pixels)
     * @param winZ
     *          the z-coordinate, which is the depth value in <tt>[0..1]</tt>
     * @param viewport
     *          the viewport described by <tt>[x, y, width, height]</tt>
     * @param dest
     *          will hold the unprojected position
     * @return this
     */
    public Matrix4d unprojectInv(double winX, double winY, double winZ, IntBuffer viewport, Vector4d dest) {
        int pos = viewport.position();
        double ndcX = (winX-viewport.get(pos))/viewport.get(pos+2)*2.0-1.0;
        double ndcY = (winY-viewport.get(pos+1))/viewport.get(pos+3)*2.0-1.0;
        double ndcZ = 2.0*winZ-1.0;
        dest.x = m00 * ndcX + m10 * ndcY + m20 * ndcZ + m30;
        dest.y = m01 * ndcX + m11 * ndcY + m21 * ndcZ + m31;
        dest.z = m02 * ndcX + m12 * ndcY + m22 * ndcZ + m32;
        dest.w = m03 * ndcX + m13 * ndcY + m23 * ndcZ + m33;
        dest.mul(1.0 / dest.w);
        return this;
    }

    /**
     * Unproject the given window coordinates <code>winCoords</code> by <code>this</code> matrix using the specified viewport.
     * <p>
     * This method differs from {@link #unproject(Vector3d, IntBuffer, Matrix4d, Vector3d) unproject()} 
     * in that it assumes that <code>this</code> is already the inverse matrix of the original projection matrix.
     * It exists to avoid recomputing the matrix inverse with every invocation.
     * <p>
     * This method first converts the given window coordinates to normalized device coordinates in the range <tt>[-1..1]</tt>
     * and then transforms those NDC coordinates by <code>this</code> matrix.  
     * <p>
     * The depth range of <tt>winCoords.z</tt> is assumed to be <tt>[0..1]</tt>, which is also the OpenGL default.
     * <p>
     * This method reads the four viewport parameters from the current IntBuffer's {@link Buffer#position() position}
     * and does not modify the buffer's position.
     * 
     * @see #unproject(Vector3d, IntBuffer, Matrix4d, Vector3d)
     * 
     * @param winCoords
     *          the window coordinates to unproject
     * @param viewport
     *          the viewport described by <tt>[x, y, width, height]</tt>
     * @param dest
     *          will hold the unprojected position
     * @return this
     */
    public Matrix4d unprojectInv(Vector3d winCoords, IntBuffer viewport, Vector3d dest) {
        return unprojectInv(winCoords.x, winCoords.y, winCoords.z, viewport, dest);
    }

    /**
     * Unproject the given window coordinates <tt>(winX, winY, winZ)</tt> by <code>this</code> matrix using the specified viewport.
     * <p>
     * This method differs from {@link #unproject(double, double, double, IntBuffer, Matrix4d, Vector3d) unproject()} 
     * in that it assumes that <code>this</code> is already the inverse matrix of the original projection matrix.
     * It exists to avoid recomputing the matrix inverse with every invocation.
     * <p>
     * This method first converts the given window coordinates to normalized device coordinates in the range <tt>[-1..1]</tt>
     * and then transforms those NDC coordinates by <code>this</code> matrix.  
     * <p>
     * The depth range of <tt>winZ</tt> is assumed to be <tt>[0..1]</tt>, which is also the OpenGL default.
     * <p>
     * This method reads the four viewport parameters from the current IntBuffer's {@link Buffer#position() position}
     * and does not modify the buffer's position.
     * 
     * @see #unproject(double, double, double, IntBuffer, Matrix4d, Vector3d)
     * 
     * @param winX
     *          the x-coordinate in window coordinates (pixels)
     * @param winY
     *          the y-coordinate in window coordinates (pixels)
     * @param winZ
     *          the z-coordinate, which is the depth value in <tt>[0..1]</tt>
     * @param viewport
     *          the viewport described by <tt>[x, y, width, height]</tt>
     * @param dest
     *          will hold the unprojected position
     * @return this
     */
    public Matrix4d unprojectInv(double winX, double winY, double winZ, IntBuffer viewport, Vector3d dest) {
        int pos = viewport.position();
        double ndcX = (winX-viewport.get(pos))/viewport.get(pos+2)*2.0-1.0;
        double ndcY = (winY-viewport.get(pos+1))/viewport.get(pos+3)*2.0-1.0;
        double ndcZ = 2.0*winZ-1.0;
        dest.x = m00 * ndcX + m10 * ndcY + m20 * ndcZ + m30;
        dest.y = m01 * ndcX + m11 * ndcY + m21 * ndcZ + m31;
        dest.z = m02 * ndcX + m12 * ndcY + m22 * ndcZ + m32;
        double w = m03 * ndcX + m13 * ndcY + m23 * ndcZ + m33;
        dest.mul(1.0 / w);
        return this;
    }

    /**
     * Unproject the given window coordinates <tt>(winX, winY, winZ)</tt> by the given <code>view</code> and <code>projection</code> matrices using the specified viewport.
     * <p>
     * This method first converts the given window coordinates to normalized device coordinates in the range <tt>[-1..1]</tt>
     * and then transforms those NDC coordinates by the inverse of <code>projection * view</code>.
     * <p>
     * The depth range of <tt>winZ</tt> is assumed to be <tt>[0..1]</tt>, which is also the OpenGL default.  
     * <p>
     * This method reads the four viewport parameters from the current IntBuffer's {@link Buffer#position() position}
     * and does not modify the buffer's position.
     * <p>
     * As a necessary computation step for unprojecting, this method computes the inverse of <code>projection * view</code> and stores
     * it into the <code>inverseOut</code> parameter matrix. In order to avoid computing the matrix inverse with every
     * invocation, the inverse of both matrices can be built once outside and then the method {@link #unprojectInv(double, double, double, IntBuffer, Vector4d) unprojectInv()}
     * can be invoked on it.
     * 
     * @see #unprojectInv(double, double, double, IntBuffer, Vector4d)
     * 
     * @param winX
     *          the x-coordinate in window coordinates (pixels)
     * @param winY
     *          the y-coordinate in window coordinates (pixels)
     * @param winZ
     *          the z-coordinate, which is the depth value in <tt>[0..1]</tt>
     * @param projection
     *          the projection matrix
     * @param view
     *          the view matrix
     * @param viewport
     *          the viewport described by <tt>[x, y, width, height]</tt>
     * @param inverseOut
     *          will hold the inverse of <code>projection * view</code> after the method returns
     * @param dest
     *          will hold the unprojected position
     */
    public static void unproject(double winX, double winY, double winZ, Matrix4d projection, Matrix4d view, IntBuffer viewport, Matrix4d inverseOut, Vector4d dest) {
        inverseOut.set(projection).mul(view).invert().unprojectInv(winX, winY, winZ, viewport, dest);
    }

    /**
     * Unproject the given window coordinates <code>winCoords</code> by the given <code>view</code> and <code>projection</code> matrices using the specified viewport.
     * <p>
     * This method first converts the given window coordinates to normalized device coordinates in the range <tt>[-1..1]</tt>
     * and then transforms those NDC coordinates by the inverse of <code>projection * view</code>.
     * <p>
     * The depth range of <tt>winCoords.z</tt> is assumed to be <tt>[0..1]</tt>, which is also the OpenGL default.
     * <p>
     * This method reads the four viewport parameters from the current IntBuffer's {@link Buffer#position() position}
     * and does not modify the buffer's position.
     * <p>
     * As a necessary computation step for unprojecting, this method computes the inverse of <code>projection * view</code> and stores
     * it into the <code>inverseOut</code> parameter matrix. In order to avoid computing the matrix inverse with every
     * invocation, the inverse of both matrices can be built once outside and then the method {@link #unprojectInv(double, double, double, IntBuffer, Vector4d) unprojectInv()}
     * can be invoked on it.
     * 
     * @see #unprojectInv(double, double, double, IntBuffer, Vector4d)
     * 
     * @param winCoords
     *          the window coordinate to unproject
     * @param projection
     *          the projection matrix
     * @param view
     *          the view matrix
     * @param viewport
     *          the viewport described by <tt>[x, y, width, height]</tt>
     * @param inverseOut
     *          will hold the inverse of <code>projection * view</code> after the method returns
     * @param dest
     *          will hold the unprojected position
     */
    public static void unproject(Vector3d winCoords, Matrix4d projection, Matrix4d view, IntBuffer viewport, Matrix4d inverseOut, Vector4d dest) {
        unproject(winCoords.x, winCoords.y, winCoords.z, projection, view, viewport, inverseOut, dest);
    }

    /**
     * Project the given <tt>(x, y, z)</tt> position via <code>this</code> matrix using the specified viewport
     * and store the resulting window coordinates in <code>winCoordsDest</code>.
     * <p>
     * This method transforms the given coordinates by <code>this</code> matrix including perspective division to 
     * obtain normalized device coordinates, and then translates these into window coordinates by using the
     * given <code>viewport</code> settings <tt>[x, y, width, height]</tt>.
     * <p>
     * This method reads the four viewport parameters from the current IntBuffer's {@link Buffer#position() position}
     * and does not modify the buffer's position.
     * <p>
     * The depth range of the returned <code>winCoordsDest.z</code> will be <tt>[0..1]</tt>, which is also the OpenGL default.  
     * 
     * @param x
     *          the x-coordinate of the position to project
     * @param y
     *          the y-coordinate of the position to project
     * @param z
     *          the z-coordinate of the position to project
     * @param viewport
     *          the viewport described by <tt>[x, y, width, height]</tt>
     * @param winCoordsDest
     *          will hold the projected window coordinates
     * @return this
     */
    public Matrix4d project(double x, double y, double z, IntBuffer viewport, Vector4d winCoordsDest) {
        winCoordsDest.x = m00 * x + m10 * y + m20 * z + m30;
        winCoordsDest.y = m01 * x + m11 * y + m21 * z + m31;
        winCoordsDest.z = m02 * x + m12 * y + m22 * z + m32;
        winCoordsDest.w = m03 * x + m13 * y + m23 * z + m33;
        int pos = viewport.position();
        winCoordsDest.mul(1.0 / winCoordsDest.w);
        winCoordsDest.x = (winCoordsDest.x*0.5+0.5) * viewport.get(pos+2) + viewport.get(pos);
        winCoordsDest.y = (winCoordsDest.y*0.5+0.5) * viewport.get(pos+3) + viewport.get(pos+1);
        winCoordsDest.z = (1.0+winCoordsDest.z)*0.5;
        return this;
    }

    /**
     * Project the given <tt>(x, y, z)</tt> position via <code>this</code> matrix using the specified viewport
     * and store the resulting window coordinates in <code>winCoordsDest</code>.
     * <p>
     * This method transforms the given coordinates by <code>this</code> matrix including perspective division to 
     * obtain normalized device coordinates, and then translates these into window coordinates by using the
     * given <code>viewport</code> settings <tt>[x, y, width, height]</tt>.
     * <p>
     * This method reads the four viewport parameters from the current IntBuffer's {@link Buffer#position() position}
     * and does not modify the buffer's position.
     * <p>
     * The depth range of the returned <code>winCoordsDest.z</code> will be <tt>[0..1]</tt>, which is also the OpenGL default.  
     * 
     * @param x
     *          the x-coordinate of the position to project
     * @param y
     *          the y-coordinate of the position to project
     * @param z
     *          the z-coordinate of the position to project
     * @param viewport
     *          the viewport described by <tt>[x, y, width, height]</tt>
     * @param winCoordsDest
     *          will hold the projected window coordinates
     * @return this
     */
    public Matrix4d project(double x, double y, double z, IntBuffer viewport, Vector3d winCoordsDest) {
        winCoordsDest.x = m00 * x + m10 * y + m20 * z + m30;
        winCoordsDest.y = m01 * x + m11 * y + m21 * z + m31;
        winCoordsDest.z = m02 * x + m12 * y + m22 * z + m32;
        double w = m03 * x + m13 * y + m23 * z + m33;
        int pos = viewport.position();
        winCoordsDest.mul(1.0 / w);
        winCoordsDest.x = (winCoordsDest.x*0.5+0.5) * viewport.get(pos+2) + viewport.get(pos);
        winCoordsDest.y = (winCoordsDest.y*0.5+0.5) * viewport.get(pos+3) + viewport.get(pos+1);
        winCoordsDest.z = (1.0+winCoordsDest.z)*0.5;
        return this;
    }

    /**
     * Project the given <code>position</code> via <code>this</code> matrix using the specified viewport
     * and store the resulting window coordinates in <code>winCoordsDest</code>.
     * <p>
     * This method transforms the given coordinates by <code>this</code> matrix including perspective division to 
     * obtain normalized device coordinates, and then translates these into window coordinates by using the
     * given <code>viewport</code> settings <tt>[x, y, width, height]</tt>.
     * <p>
     * This method reads the four viewport parameters from the current IntBuffer's {@link Buffer#position() position}
     * and does not modify the buffer's position.
     * <p>
     * The depth range of the returned <code>winCoordsDest.z</code> will be <tt>[0..1]</tt>, which is also the OpenGL default.  
     * 
     * @see #project(double, double, double, IntBuffer, Vector4d)
     * 
     * @param position
     *          the position to project into window coordinates
     * @param viewport
     *          the viewport described by <tt>[x, y, width, height]</tt>
     * @param winCoordsDest
     *          will hold the projected window coordinates
     * @return this
     */
    public Matrix4d project(Vector3d position, IntBuffer viewport, Vector4d winCoordsDest) {
        return project(position.x, position.y, position.z, viewport, winCoordsDest);
    }

    /**
     * Project the given <code>position</code> via <code>this</code> matrix using the specified viewport
     * and store the resulting window coordinates in <code>winCoordsDest</code>.
     * <p>
     * This method transforms the given coordinates by <code>this</code> matrix including perspective division to 
     * obtain normalized device coordinates, and then translates these into window coordinates by using the
     * given <code>viewport</code> settings <tt>[x, y, width, height]</tt>.
     * <p>
     * This method reads the four viewport parameters from the current IntBuffer's {@link Buffer#position() position}
     * and does not modify the buffer's position.
     * <p>
     * The depth range of the returned <code>winCoordsDest.z</code> will be <tt>[0..1]</tt>, which is also the OpenGL default.  
     * 
     * @see #project(double, double, double, IntBuffer, Vector4d)
     * 
     * @param position
     *          the position to project into window coordinates
     * @param viewport
     *          the viewport described by <tt>[x, y, width, height]</tt>
     * @param winCoordsDest
     *          will hold the projected window coordinates
     * @return this
     */
    public Matrix4d project(Vector3d position, IntBuffer viewport, Vector3d winCoordsDest) {
        return project(position.x, position.y, position.z, viewport, winCoordsDest);
    }

    /**
     * Project the given <tt>(x, y, z)</tt> position via the given <code>view</code> and <code>projection</code> matrices using the specified viewport
     * and store the resulting window coordinates in <code>winCoordsDest</code>.
     * <p>
     * This method transforms the given coordinates by <code>projection * view</code> including perspective division to 
     * obtain normalized device coordinates, and then translates these into window coordinates by using the
     * given <code>viewport</code> settings <tt>[x, y, width, height]</tt>.
     * <p>
     * This method reads the four viewport parameters from the current IntBuffer's {@link Buffer#position() position}
     * and does not modify the buffer's position.
     * <p>
     * The depth range of the returned <code>winCoordsDest.z</code> will be <tt>[0..1]</tt>, which is also the OpenGL default.  
     * 
     * @param x
     *          the x-coordinate of the position to project
     * @param y
     *          the y-coordinate of the position to project
     * @param z
     *          the z-coordinate of the position to project
     * @param projection
     *          the projection matrix
     * @param view
     *          the view matrix
     * @param viewport
     *          the viewport described by <tt>[x, y, width, height]</tt>
     * @param winCoordsDest
     *          will hold the projected window coordinates
     */
    public static void project(double x, double y, double z, Matrix4d projection, Matrix4d view, IntBuffer viewport, Vector4d winCoordsDest) {
        winCoordsDest.set(x, y, z, 1.0);
        view.transform(winCoordsDest);
        projection.transform(winCoordsDest);
        int pos = viewport.position();
        winCoordsDest.mul(1.0 / winCoordsDest.w);
        winCoordsDest.x = (winCoordsDest.x*0.5+0.5) * viewport.get(pos+2) + viewport.get(pos);
        winCoordsDest.y = (winCoordsDest.y*0.5+0.5) * viewport.get(pos+3) + viewport.get(pos+1);
        winCoordsDest.z = (1.0+winCoordsDest.z)*0.5;
    }

    /**
     * Project the given <code>position</code> via the given <code>view</code> and <code>projection</code> matrices using the specified viewport
     * and store the resulting window coordinates in <code>winCoordsDest</code>.
     * <p>
     * This method transforms the given coordinates by <code>projection * view</code> including perspective division to 
     * obtain normalized device coordinates, and then translates these into window coordinates by using the
     * given <code>viewport</code> settings <tt>[x, y, width, height]</tt>.
     * <p>
     * This method reads the four viewport parameters from the current IntBuffer's {@link Buffer#position() position}
     * and does not modify the buffer's position.
     * <p>
     * The depth range of the returned <code>winCoordsDest.z</code> will be <tt>[0..1]</tt>, which is also the OpenGL default.  
     * 
     * @see #project(double, double, double, Matrix4d, Matrix4d, IntBuffer, Vector4d)
     * 
     * @param position
     *          the position to project into window coordinates
     * @param projection
     *          the projection matrix
     * @param view
     *          the view matrix
     * @param viewport
     *          the viewport described by <tt>[x, y, width, height]</tt>
     * @param winCoordsDest
     *          will hold the projected window coordinates
     */
    public static void project(Vector3d position, Matrix4d projection, Matrix4d view, IntBuffer viewport, Vector4d winCoordsDest) {
        project(position.x, position.y, position.z, projection, view, viewport, winCoordsDest);
    }

    /**
     * Apply a mirror/reflection transformation to this matrix that reflects about the given plane
     * specified via the equation <tt>x*a + y*b + z*c + d = 0</tt> and store the result in <code>dest</code>.
     * <p>
     * The vector <tt>(a, b, c)</tt> must be a unit vector.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the reflection matrix,
     * then the new matrix will be <code>M * R</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * R * v</code>, the
     * reflection will be applied first!
     * <p>
     * Reference: <a href="https://msdn.microsoft.com/en-us/library/windows/desktop/bb281733(v=vs.85).aspx">msdn.microsoft.com</a>
     * 
     * @param a
     *          the x factor in the plane equation
     * @param b
     *          the y factor in the plane equation
     * @param c
     *          the z factor in the plane equation
     * @param d
     *          the constant in the plane equation
     * @param dest
     *          will hold the result
     * @return this
     */
    public Matrix4d reflect(double a, double b, double c, double d, Matrix4d dest) {
        double rm00 = 1.0 - 2.0 * a * a;
        double rm01 = -2.0 * a * b;
        double rm02 = -2.0 * a * c;
        double rm10 = -2.0 * a * b;
        double rm11 = 1.0 - 2.0 * b * b;
        double rm12 = -2.0 * b * c;
        double rm20 = -2.0 * a * c;
        double rm21 = -2.0 * b * c;
        double rm22 = 1.0 - 2.0 * c * c;
        double rm30 = -2.0 * a * d;
        double rm31 = -2.0 * b * d;
        double rm32 = -2.0 * c * d;

        // matrix multiplication
        dest.m30 = m00 * rm30 + m10 * rm31 + m20 * rm32 + m30;
        dest.m31 = m01 * rm30 + m11 * rm31 + m21 * rm32 + m31;
        dest.m32 = m02 * rm30 + m12 * rm31 + m22 * rm32 + m32;
        dest.m33 = m03 * rm30 + m13 * rm31 + m23 * rm32 + m33;
        double nm00 = m00 * rm00 + m10 * rm01 + m20 * rm02;
        double nm01 = m01 * rm00 + m11 * rm01 + m21 * rm02;
        double nm02 = m02 * rm00 + m12 * rm01 + m22 * rm02;
        double nm03 = m03 * rm00 + m13 * rm01 + m23 * rm02;
        double nm10 = m00 * rm10 + m10 * rm11 + m20 * rm12;
        double nm11 = m01 * rm10 + m11 * rm11 + m21 * rm12;
        double nm12 = m02 * rm10 + m12 * rm11 + m22 * rm12;
        double nm13 = m03 * rm10 + m13 * rm11 + m23 * rm12;
        dest.m20 = m00 * rm20 + m10 * rm21 + m20 * rm22;
        dest.m21 = m01 * rm20 + m11 * rm21 + m21 * rm22;
        dest.m22 = m02 * rm20 + m12 * rm21 + m22 * rm22;
        dest.m23 = m03 * rm20 + m13 * rm21 + m23 * rm22;
        dest.m00 = nm00;
        dest.m01 = nm01;
        dest.m02 = nm02;
        dest.m03 = nm03;
        dest.m10 = nm10;
        dest.m11 = nm11;
        dest.m12 = nm12;
        dest.m13 = nm13;

        return this;
    }

    /**
     * Apply a mirror/reflection transformation to this matrix that reflects about the given plane
     * specified via the equation <tt>x*a + y*b + z*c + d = 0</tt>.
     * <p>
     * The vector <tt>(a, b, c)</tt> must be a unit vector.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the reflection matrix,
     * then the new matrix will be <code>M * R</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * R * v</code>, the
     * reflection will be applied first!
     * <p>
     * Reference: <a href="https://msdn.microsoft.com/en-us/library/windows/desktop/bb281733(v=vs.85).aspx">msdn.microsoft.com</a>
     * 
     * @param a
     *          the x factor in the plane equation
     * @param b
     *          the y factor in the plane equation
     * @param c
     *          the z factor in the plane equation
     * @param d
     *          the constant in the plane equation
     * @return this
     */
    public Matrix4d reflect(double a, double b, double c, double d) {
        return reflect(a, b, c, d, this);
    }

    /**
     * Apply a mirror/reflection transformation to this matrix that reflects about the given plane
     * specified via the plane normal and a point on the plane.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the reflection matrix,
     * then the new matrix will be <code>M * R</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * R * v</code>, the
     * reflection will be applied first!
     * 
     * @param nx
     *          the x-coordinate of the plane normal
     * @param ny
     *          the y-coordinate of the plane normal
     * @param nz
     *          the z-coordinate of the plane normal
     * @param px
     *          the x-coordinate of a point on the plane
     * @param py
     *          the y-coordinate of a point on the plane
     * @param pz
     *          the z-coordinate of a point on the plane
     * @return this
     */
    public Matrix4d reflect(double nx, double ny, double nz, double px, double py, double pz) {
        return reflect(nx, ny, nz, px, py, pz, this);
    }

    /**
     * Apply a mirror/reflection transformation to this matrix that reflects about the given plane
     * specified via the plane normal and a point on the plane, and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the reflection matrix,
     * then the new matrix will be <code>M * R</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * R * v</code>, the
     * reflection will be applied first!
     * 
     * @param nx
     *          the x-coordinate of the plane normal
     * @param ny
     *          the y-coordinate of the plane normal
     * @param nz
     *          the z-coordinate of the plane normal
     * @param px
     *          the x-coordinate of a point on the plane
     * @param py
     *          the y-coordinate of a point on the plane
     * @param pz
     *          the z-coordinate of a point on the plane
     * @param dest
     *          will hold the result
     * @return this
     */
    public Matrix4d reflect(double nx, double ny, double nz, double px, double py, double pz, Matrix4d dest) {
        double length = Math.sqrt(nx * nx + ny * ny + nz * nz);
        double nnx = nx / length;
        double nny = ny / length;
        double nnz = nz / length;
        /* See: http://mathworld.wolfram.com/Plane.html */
        return reflect(nnx, nny, nnz, -nnx * px - nny * py - nnz * pz, dest);
    }

    /**
     * Apply a mirror/reflection transformation to this matrix that reflects about the given plane
     * specified via the plane normal and a point on the plane.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the reflection matrix,
     * then the new matrix will be <code>M * R</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * R * v</code>, the
     * reflection will be applied first!
     * 
     * @param normal
     *          the plane normal
     * @param point
     *          a point on the plane
     * @return this
     */
    public Matrix4d reflect(Vector3d normal, Vector3d point) {
        return reflect(normal.x, normal.y, normal.z, point.x, point.y, point.z);
    }

    /**
     * Apply a mirror/reflection transformation to this matrix that reflects about a plane
     * specified via the plane orientation and a point on the plane.
     * <p>
     * This method can be used to build a reflection transformation based on the orientation of a mirror object in the scene.
     * It is assumed that the default mirror plane's normal is <tt>(0, 0, 1)</tt>. So, if the given {@link Quaterniond} is
     * the identity (does not apply any additional rotation), the reflection plane will be <tt>z=0</tt>, offset by the given <code>point</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the reflection matrix,
     * then the new matrix will be <code>M * R</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * R * v</code>, the
     * reflection will be applied first!
     * 
     * @param orientation
     *          the plane orientation
     * @param point
     *          a point on the plane
     * @return this
     */
    public Matrix4d reflect(Quaterniond orientation, Vector3d point) {
        return reflect(orientation, point, this);
    }

    /**
     * Apply a mirror/reflection transformation to this matrix that reflects about a plane
     * specified via the plane orientation and a point on the plane, and store the result in <code>dest</code>.
     * <p>
     * This method can be used to build a reflection transformation based on the orientation of a mirror object in the scene.
     * It is assumed that the default mirror plane's normal is <tt>(0, 0, 1)</tt>. So, if the given {@link Quaterniond} is
     * the identity (does not apply any additional rotation), the reflection plane will be <tt>z=0</tt>, offset by the given <code>point</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the reflection matrix,
     * then the new matrix will be <code>M * R</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * R * v</code>, the
     * reflection will be applied first!
     * 
     * @param orientation
     *          the plane orientation
     * @param point
     *          a point on the plane
     * @param dest
     *          will hold the result
     * @return this
     */
    public Matrix4d reflect(Quaterniond orientation, Vector3d point, Matrix4d dest) {
        double num1 = orientation.x * 2.0;
        double num2 = orientation.y * 2.0;
        double num3 = orientation.z * 2.0;
        double normalX = orientation.x * num3 + orientation.w * num2;
        double normalY = orientation.y * num3 - orientation.w * num1;
        double normalZ = 1.0 - (orientation.x * num1 + orientation.y * num2);
        return reflect(normalX, normalY, normalZ, point.x, point.y, point.z, dest);
    }

    /**
     * Apply a mirror/reflection transformation to this matrix that reflects about the given plane
     * specified via the plane normal and a point on the plane, and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the reflection matrix,
     * then the new matrix will be <code>M * R</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * R * v</code>, the
     * reflection will be applied first!
     * 
     * @param normal
     *          the plane normal
     * @param point
     *          a point on the plane
     * @param dest
     *          will hold the result
     * @return this
     */
    public Matrix4d reflect(Vector3d normal, Vector3d point, Matrix4d dest) {
        return reflect(normal.x, normal.y, normal.z, point.x, point.y, point.z, dest);
    }

    /**
     * Set this matrix to a mirror/reflection transformation that reflects about the given plane
     * specified via the equation <tt>x*a + y*b + z*c + d = 0</tt>.
     * <p>
     * The vector <tt>(a, b, c)</tt> must be a unit vector.
     * <p>
     * Reference: <a href="https://msdn.microsoft.com/en-us/library/windows/desktop/bb281733(v=vs.85).aspx">msdn.microsoft.com</a>
     * 
     * @param a
     *          the x factor in the plane equation
     * @param b
     *          the y factor in the plane equation
     * @param c
     *          the z factor in the plane equation
     * @param d
     *          the constant in the plane equation
     * @return this
     */
    public Matrix4d reflection(double a, double b, double c, double d) {
        m00 = 1.0 - 2.0 * a * a;
        m01 = -2.0 * a * b;
        m02 = -2.0 * a * c;
        m03 = 0.0;
        m10 = -2.0 * a * b;
        m11 = 1.0 - 2.0 * b * b;
        m12 = -2.0 * b * c;
        m13 = 0.0;
        m20 = -2.0 * a * c;
        m21 = -2.0 * b * c;
        m22 = 1.0 - 2.0 * c * c;
        m23 = 0.0;
        m30 = -2.0 * a * d;
        m31 = -2.0 * b * d;
        m32 = -2.0 * c * d;
        m33 = 1.0;
        return this;
    }

    /**
     * Set this matrix to a mirror/reflection transformation that reflects about the given plane
     * specified via the plane normal and a point on the plane.
     * 
     * @param nx
     *          the x-coordinate of the plane normal
     * @param ny
     *          the y-coordinate of the plane normal
     * @param nz
     *          the z-coordinate of the plane normal
     * @param px
     *          the x-coordinate of a point on the plane
     * @param py
     *          the y-coordinate of a point on the plane
     * @param pz
     *          the z-coordinate of a point on the plane
     * @return this
     */
    public Matrix4d reflection(double nx, double ny, double nz, double px, double py, double pz) {
        double length = (float) Math.sqrt(nx * nx + ny * ny + nz * nz);
        double nnx = nx / length;
        double nny = ny / length;
        double nnz = nz / length;
        /* See: http://mathworld.wolfram.com/Plane.html */
        return reflection(nnx, nny, nnz, -nnx * px - nny * py - nnz * pz);
    }

    /**
     * Set this matrix to a mirror/reflection transformation that reflects about the given plane
     * specified via the plane normal and a point on the plane.
     * 
     * @param normal
     *          the plane normal
     * @param point
     *          a point on the plane
     * @return this
     */
    public Matrix4d reflection(Vector3d normal, Vector3d point) {
        return reflection(normal.x, normal.y, normal.z, point.x, point.y, point.z);
    }

    /**
     * Set this matrix to a mirror/reflection transformation that reflects about a plane
     * specified via the plane orientation and a point on the plane.
     * <p>
     * This method can be used to build a reflection transformation based on the orientation of a mirror object in the scene.
     * It is assumed that the default mirror plane's normal is <tt>(0, 0, 1)</tt>. So, if the given {@link Quaterniond} is
     * the identity (does not apply any additional rotation), the reflection plane will be <tt>z=0</tt>, offset by the given <code>point</code>.
     * 
     * @param orientation
     *          the plane orientation
     * @param point
     *          a point on the plane
     * @return this
     */
    public Matrix4d reflection(Quaterniond orientation, Vector3d point) {
        double num1 = orientation.x * 2.0;
        double num2 = orientation.y * 2.0;
        double num3 = orientation.z * 2.0;
        double normalX = orientation.x * num3 + orientation.w * num2;
        double normalY = orientation.y * num3 - orientation.w * num1;
        double normalZ = 1.0 - (orientation.x * num1 + orientation.y * num2);
        return reflection(normalX, normalY, normalZ, point.x, point.y, point.z);
    }
    

    /**
     * Apply an orthographic projection transformation to this matrix and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>O</code> the orthographic projection matrix,
     * then the new matrix will be <code>M * O</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * O * v</code>, the
     * orthographic projection transformation will be applied first!
     * <p>
     * In order to set the matrix to an orthographic projection without post-multiplying it,
     * use {@link #setOrtho(double, double, double, double, double, double) setOrtho()}.
     * <p>
     * Reference: <a href="http://www.songho.ca/opengl/gl_projectionmatrix.html#ortho">http://www.songho.ca</a>
     * 
     * @see #setOrtho(double, double, double, double, double, double)
     * 
     * @param left
     *            the distance from the center to the left frustum edge
     * @param right
     *            the distance from the center to the right frustum edge
     * @param bottom
     *            the distance from the center to the bottom frustum edge
     * @param top
     *            the distance from the center to the top frustum edge
     * @param zNear
     *            near clipping plane distance
     * @param zFar
     *            far clipping plane distance
     * @param dest
     *            will hold the result
     * @return this
     */
    public Matrix4d ortho(double left, double right, double bottom, double top, double zNear, double zFar, Matrix4d dest) {
        // calculate right matrix elements
        double rm00 = 2.0 / (right - left);
        double rm11 = 2.0 / (top - bottom);
        double rm22 = -2.0 / (zFar - zNear);
        double rm30 = -(right + left) / (right - left);
        double rm31 = -(top + bottom) / (top - bottom);
        double rm32 = -(zFar + zNear) / (zFar - zNear);

        // perform optimized multiplication
        // compute the last column first, because other rows do not depend on it
        dest.m30 = m00 * rm30 + m10 * rm31 + m20 * rm32 + m30;
        dest.m31 = m01 * rm30 + m11 * rm31 + m21 * rm32 + m31;
        dest.m32 = m02 * rm30 + m12 * rm31 + m22 * rm32 + m32;
        dest.m33 = m03 * rm30 + m13 * rm31 + m23 * rm32 + m33;
        dest.m00 = m00 * rm00;
        dest.m01 = m01 * rm00;
        dest.m02 = m02 * rm00;
        dest.m03 = m03 * rm00;
        dest.m10 = m10 * rm11;
        dest.m11 = m11 * rm11;
        dest.m12 = m12 * rm11;
        dest.m13 = m13 * rm11;
        dest.m20 = m20 * rm22;
        dest.m21 = m21 * rm22;
        dest.m22 = m22 * rm22;
        dest.m23 = m23 * rm22;

        return this;
    }

    /**
     * Apply an orthographic projection transformation to this matrix.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>O</code> the orthographic projection matrix,
     * then the new matrix will be <code>M * O</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * O * v</code>, the
     * orthographic projection transformation will be applied first!
     * <p>
     * In order to set the matrix to an orthographic projection without post-multiplying it,
     * use {@link #setOrtho(double, double, double, double, double, double) setOrtho()}.
     * <p>
     * Reference: <a href="http://www.songho.ca/opengl/gl_projectionmatrix.html#ortho">http://www.songho.ca</a>
     * 
     * @see #setOrtho(double, double, double, double, double, double)
     * 
     * @param left
     *            the distance from the center to the left frustum edge
     * @param right
     *            the distance from the center to the right frustum edge
     * @param bottom
     *            the distance from the center to the bottom frustum edge
     * @param top
     *            the distance from the center to the top frustum edge
     * @param zNear
     *            near clipping plane distance
     * @param zFar
     *            far clipping plane distance
     * @return this
     */
    public Matrix4d ortho(double left, double right, double bottom, double top, double zNear, double zFar) {
        return ortho(left, right, bottom, top, zNear, zFar, this);
    }

    /**
     * Set this matrix to be an orthographic projection transformation.
     * <p>
     * In order to apply the orthographic projection to an already existing transformation,
     * use {@link #ortho(double, double, double, double, double, double) ortho()}.
     * <p>
     * Reference: <a href="http://www.songho.ca/opengl/gl_projectionmatrix.html#ortho">http://www.songho.ca</a>
     * 
     * @see #ortho(double, double, double, double, double, double)
     * 
     * @param left
     *            the distance from the center to the left frustum edge
     * @param right
     *            the distance from the center to the right frustum edge
     * @param bottom
     *            the distance from the center to the bottom frustum edge
     * @param top
     *            the distance from the center to the top frustum edge
     * @param zNear
     *            near clipping plane distance
     * @param zFar
     *            far clipping plane distance
     * @return this
     */
    public Matrix4d setOrtho(double left, double right, double bottom, double top, double zNear, double zFar) {
        m00 = 2.0 / (right - left);
        m01 = 0.0;
        m02 = 0.0;
        m03 = 0.0;
        m10 = 0.0;
        m11 = 2.0 / (top - bottom);
        m12 = 0.0;
        m13 = 0.0;
        m20 = 0.0;
        m21 = 0.0;
        m22 = -2.0 / (zFar - zNear);
        m23 = 0.0;
        m30 = -(right + left) / (right - left);
        m31 = -(top + bottom) / (top - bottom);
        m32 = -(zFar + zNear) / (zFar - zNear);
        m33 = 1.0;
        return this;
    }
    

    /**
     * Apply a symmetric orthographic projection transformation to this matrix and store the result in <code>dest</code>.
     * <p>
     * This method is equivalent to calling {@link #ortho(double, double, double, double, double, double, Matrix4d) ortho()} with
     * <code>left=-width/2</code>, <code>right=+width/2</code>, <code>bottom=-height/2</code> and <code>top=+height/2</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>O</code> the orthographic projection matrix,
     * then the new matrix will be <code>M * O</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * O * v</code>, the
     * orthographic projection transformation will be applied first!
     * <p>
     * In order to set the matrix to a symmetric orthographic projection without post-multiplying it,
     * use {@link #setOrthoSymmetric(double, double, double, double) setOrthoSymmetric()}.
     * <p>
     * Reference: <a href="http://www.songho.ca/opengl/gl_projectionmatrix.html#ortho">http://www.songho.ca</a>
     * 
     * @see #setOrthoSymmetric(double, double, double, double)
     * 
     * @param width
     *            the distance between the right and left frustum edges
     * @param height
     *            the distance between the top and bottom frustum edges
     * @param zNear
     *            near clipping plane distance
     * @param zFar
     *            far clipping plane distance
     * @param dest
     *            will hold the result
     * @return this
     */
    public Matrix4d orthoSymmetric(double width, double height, double zNear, double zFar, Matrix4d dest) {
        // calculate right matrix elements
        double rm00 = 2.0f / width;
        double rm11 = 2.0f / height;
        double rm22 = -2.0f / (zFar - zNear);
        double rm32 = -(zFar + zNear) / (zFar - zNear);

        // perform optimized multiplication
        // compute the last column first, because other rows do not depend on it
        dest.m30 = m20 * rm32 + m30;
        dest.m31 = m21 * rm32 + m31;
        dest.m32 = m22 * rm32 + m32;
        dest.m33 = m23 * rm32 + m33;
        dest.m00 = m00 * rm00;
        dest.m01 = m01 * rm00;
        dest.m02 = m02 * rm00;
        dest.m03 = m03 * rm00;
        dest.m10 = m10 * rm11;
        dest.m11 = m11 * rm11;
        dest.m12 = m12 * rm11;
        dest.m13 = m13 * rm11;
        dest.m20 = m20 * rm22;
        dest.m21 = m21 * rm22;
        dest.m22 = m22 * rm22;
        dest.m23 = m23 * rm22;

        return this;
    }

    /**
     * Apply a symmetric orthographic projection transformation to this matrix.
     * <p>
     * This method is equivalent to calling {@link #ortho(double, double, double, double, double, double) ortho()} with
     * <code>left=-width/2</code>, <code>right=+width/2</code>, <code>bottom=-height/2</code> and <code>top=+height/2</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>O</code> the orthographic projection matrix,
     * then the new matrix will be <code>M * O</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * O * v</code>, the
     * orthographic projection transformation will be applied first!
     * <p>
     * In order to set the matrix to a symmetric orthographic projection without post-multiplying it,
     * use {@link #setOrthoSymmetric(double, double, double, double) setOrthoSymmetric()}.
     * <p>
     * Reference: <a href="http://www.songho.ca/opengl/gl_projectionmatrix.html#ortho">http://www.songho.ca</a>
     * 
     * @see #setOrthoSymmetric(double, double, double, double)
     * 
     * @param width
     *            the distance between the right and left frustum edges
     * @param height
     *            the distance between the top and bottom frustum edges
     * @param zNear
     *            near clipping plane distance
     * @param zFar
     *            far clipping plane distance
     * @return this
     */
    public Matrix4d orthoSymmetric(double width, double height, double zNear, double zFar) {
        return orthoSymmetric(width, height, zNear, zFar, this);
    }

    /**
     * Set this matrix to be a symmetric orthographic projection transformation.
     * <p>
     * This method is equivalent to calling {@link #setOrtho(double, double, double, double, double, double) setOrtho()} with
     * <code>left=-width/2</code>, <code>right=+width/2</code>, <code>bottom=-height/2</code> and <code>top=+height/2</code>.
     * <p>
     * In order to apply the symmetric orthographic projection to an already existing transformation,
     * use {@link #orthoSymmetric(double, double, double, double) orthoSymmetric()}.
     * <p>
     * Reference: <a href="http://www.songho.ca/opengl/gl_projectionmatrix.html#ortho">http://www.songho.ca</a>
     * 
     * @see #orthoSymmetric(double, double, double, double)
     * 
     * @param width
     *            the distance between the right and left frustum edges
     * @param height
     *            the distance between the top and bottom frustum edges
     * @param zNear
     *            near clipping plane distance
     * @param zFar
     *            far clipping plane distance
     * @return this
     */
    public Matrix4d setOrthoSymmetric(double width, double height, double zNear, double zFar) {
        m00 = 2.0 / width;
        m01 = 0.0;
        m02 = 0.0;
        m03 = 0.0;
        m10 = 0.0;
        m11 = 2.0 / height;
        m12 = 0.0;
        m13 = 0.0;
        m20 = 0.0;
        m21 = 0.0;
        m22 = -2.0 / (zFar - zNear);
        m23 = 0.0;
        m30 = 0.0;
        m31 = 0.0;
        m32 = -(zFar + zNear) / (zFar - zNear);
        m33 = 1.0;
        return this;
    }

    /**
     * Apply an orthographic projection transformation to this matrix and store the result in <code>dest</code>.
     * <p>
     * This method is equivalent to calling {@link #ortho(double, double, double, double, double, double, Matrix4d) ortho()} with
     * <code>zNear=-1</code> and <code>zFar=+1</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>O</code> the orthographic projection matrix,
     * then the new matrix will be <code>M * O</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * O * v</code>, the
     * orthographic projection transformation will be applied first!
     * <p>
     * In order to set the matrix to an orthographic projection without post-multiplying it,
     * use {@link #setOrtho2D(double, double, double, double) setOrtho()}.
     * <p>
     * Reference: <a href="http://www.songho.ca/opengl/gl_projectionmatrix.html#ortho">http://www.songho.ca</a>
     * 
     * @see #setOrtho2D(double, double, double, double)
     * 
     * @param left
     *            the distance from the center to the left frustum edge
     * @param right
     *            the distance from the center to the right frustum edge
     * @param bottom
     *            the distance from the center to the bottom frustum edge
     * @param top
     *            the distance from the center to the top frustum edge
     * @param dest
     *            will hold the result
     * @return this
     */
    public Matrix4d ortho2D(double left, double right, double bottom, double top, Matrix4d dest) {
        // calculate right matrix elements
        double rm00 = 2.0 / (right - left);
        double rm11 = 2.0 / (top - bottom);
        double rm30 = -(right + left) / (right - left);
        double rm31 = -(top + bottom) / (top - bottom);

        // perform optimized multiplication
        // compute the last column first, because other rows do not depend on it
        dest.m30 = m00 * rm30 + m10 * rm31 + m30;
        dest.m31 = m01 * rm30 + m11 * rm31 + m31;
        dest.m32 = m02 * rm30 + m12 * rm31 + m32;
        dest.m33 = m03 * rm30 + m13 * rm31 + m33;
        dest.m00 = m00 * rm00;
        dest.m01 = m01 * rm00;
        dest.m02 = m02 * rm00;
        dest.m03 = m03 * rm00;
        dest.m10 = m10 * rm11;
        dest.m11 = m11 * rm11;
        dest.m12 = m12 * rm11;
        dest.m13 = m13 * rm11;
        dest.m20 = -m20;
        dest.m21 = -m21;
        dest.m22 = -m22;
        dest.m23 = -m23;

        return this;
    }

    /**
     * Apply an orthographic projection transformation to this matrix.
     * <p>
     * This method is equivalent to calling {@link #ortho(double, double, double, double, double, double) ortho()} with
     * <code>zNear=-1</code> and <code>zFar=+1</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>O</code> the orthographic projection matrix,
     * then the new matrix will be <code>M * O</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * O * v</code>, the
     * orthographic projection transformation will be applied first!
     * <p>
     * In order to set the matrix to an orthographic projection without post-multiplying it,
     * use {@link #setOrtho2D(double, double, double, double) setOrtho2D()}.
     * <p>
     * Reference: <a href="http://www.songho.ca/opengl/gl_projectionmatrix.html#ortho">http://www.songho.ca</a>
     * 
     * @see #setOrtho2D(double, double, double, double)
     * 
     * @param left
     *            the distance from the center to the left frustum edge
     * @param right
     *            the distance from the center to the right frustum edge
     * @param bottom
     *            the distance from the center to the bottom frustum edge
     * @param top
     *            the distance from the center to the top frustum edge
     * @return this
     */
    public Matrix4d ortho2D(double left, double right, double bottom, double top) {
        return ortho2D(left, right, bottom, top, this);
    }

    /**
     * Set this matrix to be an orthographic projection transformation.
     * <p>
     * This method is equivalent to calling {@link #setOrtho(double, double, double, double, double, double) setOrtho()} with
     * <code>zNear=-1</code> and <code>zFar=+1</code>.
     * <p>
     * In order to apply the orthographic projection to an already existing transformation,
     * use {@link #ortho2D(double, double, double, double) ortho2D()}.
     * <p>
     * Reference: <a href="http://www.songho.ca/opengl/gl_projectionmatrix.html#ortho">http://www.songho.ca</a>
     * 
     * @see #ortho2D(double, double, double, double)
     * 
     * @param left
     *            the distance from the center to the left frustum edge
     * @param right
     *            the distance from the center to the right frustum edge
     * @param bottom
     *            the distance from the center to the bottom frustum edge
     * @param top
     *            the distance from the center to the top frustum edge
     * @return this
     */
    public Matrix4d setOrtho2D(double left, double right, double bottom, double top) {
        m00 = 2.0 / (right - left);
        m01 = 0.0;
        m02 = 0.0;
        m03 = 0.0;
        m10 = 0.0;
        m11 = 2.0 / (top - bottom);
        m12 = 0.0;
        m13 = 0.0;
        m20 = 0.0;
        m21 = 0.0;
        m22 = -1.0;
        m23 = 0.0;
        m30 = -(right + left) / (right - left);
        m31 = -(top + bottom) / (top - bottom);
        m32 = 0.0;
        m33 = 1.0;
        return this;
    }

    /**
     * Apply a rotation transformation to this matrix to make <code>-z</code> point along <code>dir</code>. 
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>L</code> the lookalong rotation matrix,
     * then the new matrix will be <code>M * L</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * L * v</code>, the
     * lookalong rotation transformation will be applied first!
     * <p>
     * This is equivalent to calling
     * {@link #lookAt(Vector3d, Vector3d, Vector3d) lookAt}
     * with <code>eye = (0, 0, 0)</code> and <code>center = dir</code>.
     * <p>
     * In order to set the matrix to a lookalong transformation without post-multiplying it,
     * use {@link #setLookAlong(Vector3d, Vector3d) setLookAlong()}.
     * 
     * @see #lookAlong(double, double, double, double, double, double)
     * @see #lookAt(Vector3d, Vector3d, Vector3d)
     * @see #setLookAlong(Vector3d, Vector3d)
     * 
     * @param dir
     *            the direction in space to look along
     * @param up
     *            the direction of 'up'
     * @return this
     */
    public Matrix4d lookAlong(Vector3d dir, Vector3d up) {
        return lookAlong(dir.x, dir.y, dir.z, up.x, up.y, up.z, this);
    }

    /**
     * Apply a rotation transformation to this matrix to make <code>-z</code> point along <code>dir</code>
     * and store the result in <code>dest</code>. 
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>L</code> the lookalong rotation matrix,
     * then the new matrix will be <code>M * L</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * L * v</code>, the
     * lookalong rotation transformation will be applied first!
     * <p>
     * This is equivalent to calling
     * {@link #lookAt(Vector3d, Vector3d, Vector3d) lookAt}
     * with <code>eye = (0, 0, 0)</code> and <code>center = dir</code>.
     * <p>
     * In order to set the matrix to a lookalong transformation without post-multiplying it,
     * use {@link #setLookAlong(Vector3d, Vector3d) setLookAlong()}.
     * 
     * @see #lookAlong(double, double, double, double, double, double)
     * @see #lookAt(Vector3d, Vector3d, Vector3d)
     * @see #setLookAlong(Vector3d, Vector3d)
     * 
     * @param dir
     *            the direction in space to look along
     * @param up
     *            the direction of 'up'
     * @param dest
     *            will hold the result
     * @return this
     */
    public Matrix4d lookAlong(Vector3d dir, Vector3d up, Matrix4d dest) {
        return lookAlong(dir.x, dir.y, dir.z, up.x, up.y, up.z, dest);
    }

    /**
     * Apply a rotation transformation to this matrix to make <code>-z</code> point along <code>dir</code>
     * and store the result in <code>dest</code>. 
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>L</code> the lookalong rotation matrix,
     * then the new matrix will be <code>M * L</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * L * v</code>, the
     * lookalong rotation transformation will be applied first!
     * <p>
     * This is equivalent to calling
     * {@link #lookAt(double, double, double, double, double, double, double, double, double) lookAt()}
     * with <code>eye = (0, 0, 0)</code> and <code>center = dir</code>.
     * <p>
     * In order to set the matrix to a lookalong transformation without post-multiplying it,
     * use {@link #setLookAlong(double, double, double, double, double, double) setLookAlong()}
     * 
     * @see #lookAt(double, double, double, double, double, double, double, double, double)
     * @see #setLookAlong(double, double, double, double, double, double)
     * 
     * @param dirX
     *              the x-coordinate of the direction to look along
     * @param dirY
     *              the y-coordinate of the direction to look along
     * @param dirZ
     *              the z-coordinate of the direction to look along
     * @param upX
     *              the x-coordinate of the up vector
     * @param upY
     *              the y-coordinate of the up vector
     * @param upZ
     *              the z-coordinate of the up vector
     * @param dest
     *              will hold the result
     * @return this
     */
    public Matrix4d lookAlong(double dirX, double dirY, double dirZ,
                              double upX, double upY, double upZ, Matrix4d dest) {
        // Normalize direction
        double dirLength = (float) Math.sqrt(dirX * dirX + dirY * dirY + dirZ * dirZ);
        double dirnX = dirX / dirLength;
        double dirnY = dirY / dirLength;
        double dirnZ = dirZ / dirLength;
        // right = direction x up
        double rightX, rightY, rightZ;
        rightX = dirnY * upZ - dirnZ * upY;
        rightY = dirnZ * upX - dirnX * upZ;
        rightZ = dirnX * upY - dirnY * upX;
        // normalize right
        double rightLength = Math.sqrt(rightX * rightX + rightY * rightY + rightZ * rightZ);
        rightX /= rightLength;
        rightY /= rightLength;
        rightZ /= rightLength;
        // up = right x direction
        double upnX = rightY * dirnZ - rightZ * dirnY;
        double upnY = rightZ * dirnX - rightX * dirnZ;
        double upnZ = rightX * dirnY - rightY * dirnX;

        // calculate right matrix elements
        double rm00 = rightX;
        double rm01 = upnX;
        double rm02 = -dirnX;
        double rm10 = rightY;
        double rm11 = upnY;
        double rm12 = -dirnY;
        double rm20 = rightZ;
        double rm21 = upnZ;
        double rm22 = -dirnZ;

        // perform optimized matrix multiplication
        // introduce temporaries for dependent results
        double nm00 = m00 * rm00 + m10 * rm01 + m20 * rm02;
        double nm01 = m01 * rm00 + m11 * rm01 + m21 * rm02;
        double nm02 = m02 * rm00 + m12 * rm01 + m22 * rm02;
        double nm03 = m03 * rm00 + m13 * rm01 + m23 * rm02;
        double nm10 = m00 * rm10 + m10 * rm11 + m20 * rm12;
        double nm11 = m01 * rm10 + m11 * rm11 + m21 * rm12;
        double nm12 = m02 * rm10 + m12 * rm11 + m22 * rm12;
        double nm13 = m03 * rm10 + m13 * rm11 + m23 * rm12;
        dest.m20 = m00 * rm20 + m10 * rm21 + m20 * rm22;
        dest.m21 = m01 * rm20 + m11 * rm21 + m21 * rm22;
        dest.m22 = m02 * rm20 + m12 * rm21 + m22 * rm22;
        dest.m23 = m03 * rm20 + m13 * rm21 + m23 * rm22;
        // set the rest of the matrix elements
        dest.m00 = nm00;
        dest.m01 = nm01;
        dest.m02 = nm02;
        dest.m03 = nm03;
        dest.m10 = nm10;
        dest.m11 = nm11;
        dest.m12 = nm12;
        dest.m13 = nm13;
        dest.m30 = m30;
        dest.m31 = m31;
        dest.m32 = m32;
        dest.m33 = m33;

        return this;
    }

    /**
     * Apply a rotation transformation to this matrix to make <code>-z</code> point along <code>dir</code>. 
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>L</code> the lookalong rotation matrix,
     * then the new matrix will be <code>M * L</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * L * v</code>, the
     * lookalong rotation transformation will be applied first!
     * <p>
     * This is equivalent to calling
     * {@link #lookAt(double, double, double, double, double, double, double, double, double) lookAt()}
     * with <code>eye = (0, 0, 0)</code> and <code>center = dir</code>.
     * <p>
     * In order to set the matrix to a lookalong transformation without post-multiplying it,
     * use {@link #setLookAlong(double, double, double, double, double, double) setLookAlong()}
     * 
     * @see #lookAt(double, double, double, double, double, double, double, double, double)
     * @see #setLookAlong(double, double, double, double, double, double)
     * 
     * @param dirX
     *              the x-coordinate of the direction to look along
     * @param dirY
     *              the y-coordinate of the direction to look along
     * @param dirZ
     *              the z-coordinate of the direction to look along
     * @param upX
     *              the x-coordinate of the up vector
     * @param upY
     *              the y-coordinate of the up vector
     * @param upZ
     *              the z-coordinate of the up vector
     * @return this
     */
    public Matrix4d lookAlong(double dirX, double dirY, double dirZ,
                              double upX, double upY, double upZ) {
        return lookAlong(dirX, dirY, dirZ, upX, upY, upZ, this);
    }

    /**
     * Set this matrix to a rotation transformation to make <code>-z</code>
     * point along <code>dir</code>.
     * <p>
     * This is equivalent to calling
     * {@link #setLookAt(Vector3d, Vector3d, Vector3d) setLookAt()} 
     * with <code>eye = (0, 0, 0)</code> and <code>center = dir</code>.
     * <p>
     * In order to apply the lookalong transformation to any previous existing transformation,
     * use {@link #lookAlong(Vector3d, Vector3d)}.
     * 
     * @see #setLookAlong(Vector3d, Vector3d)
     * @see #lookAlong(Vector3d, Vector3d)
     * 
     * @param dir
     *            the direction in space to look along
     * @param up
     *            the direction of 'up'
     * @return this
     */
    public Matrix4d setLookAlong(Vector3d dir, Vector3d up) {
        return setLookAlong(dir.x, dir.y, dir.z, up.x, up.y, up.z);
    }

    /**
     * Set this matrix to a rotation transformation to make <code>-z</code>
     * point along <code>dir</code>.
     * <p>
     * This is equivalent to calling
     * {@link #setLookAt(double, double, double, double, double, double, double, double, double)
     * setLookAt()} with <code>eye = (0, 0, 0)</code> and <code>center = dir</code>.
     * <p>
     * In order to apply the lookalong transformation to any previous existing transformation,
     * use {@link #lookAlong(double, double, double, double, double, double) lookAlong()}
     * 
     * @see #setLookAlong(double, double, double, double, double, double)
     * @see #lookAlong(double, double, double, double, double, double)
     * 
     * @param dirX
     *              the x-coordinate of the direction to look along
     * @param dirY
     *              the y-coordinate of the direction to look along
     * @param dirZ
     *              the z-coordinate of the direction to look along
     * @param upX
     *              the x-coordinate of the up vector
     * @param upY
     *              the y-coordinate of the up vector
     * @param upZ
     *              the z-coordinate of the up vector
     * @return this
     */
    public Matrix4d setLookAlong(double dirX, double dirY, double dirZ,
                                 double upX, double upY, double upZ) {
        // Normalize direction
        double dirLength = (float) Math.sqrt(dirX * dirX + dirY * dirY + dirZ * dirZ);
        double dirnX = dirX / dirLength;
        double dirnY = dirY / dirLength;
        double dirnZ = dirZ / dirLength;
        // right = direction x up
        double rightX, rightY, rightZ;
        rightX = dirnY * upZ - dirnZ * upY;
        rightY = dirnZ * upX - dirnX * upZ;
        rightZ = dirnX * upY - dirnY * upX;
        // normalize right
        double rightLength = Math.sqrt(rightX * rightX + rightY * rightY + rightZ * rightZ);
        rightX /= rightLength;
        rightY /= rightLength;
        rightZ /= rightLength;
        // up = right x direction
        double upnX = rightY * dirnZ - rightZ * dirnY;
        double upnY = rightZ * dirnX - rightX * dirnZ;
        double upnZ = rightX * dirnY - rightY * dirnX;

        m00 = rightX;
        m01 = upnX;
        m02 = -dirnX;
        m03 = 0.0;
        m10 = rightY;
        m11 = upnY;
        m12 = -dirnY;
        m13 = 0.0;
        m20 = rightZ;
        m21 = upnZ;
        m22 = -dirnZ;
        m23 = 0.0;
        m30 = 0.0;
        m31 = 0.0;
        m32 = 0.0;
        m33 = 1.0;

        return this;
    }

    /**
     * Set this matrix to be a "lookat" transformation for a right-handed coordinate system, that aligns
     * <code>-z</code> with <code>center - eye</code>.
     * <p>
     * If you do not want to use {@link Vector3d} instances but simple floats
     * like in the GLU function, you can use
     * {@link #setLookAt(double, double, double, double, double, double, double, double, double) setLookAt()}
     * instead.
     * <p>
     * In order to apply the lookat transformation to a previous existing transformation,
     * use {@link #lookAt(Vector3d, Vector3d, Vector3d) lookAt()}.
     * 
     * @see #setLookAt(double, double, double, double, double, double, double, double, double)
     * @see #lookAt(Vector3d, Vector3d, Vector3d)
     * 
     * @param eye
     *            the position of the camera
     * @param center
     *            the point in space to look at
     * @param up
     *            the direction of 'up'
     * @return this
     */
    public Matrix4d setLookAt(Vector3d eye, Vector3d center, Vector3d up) {
        return setLookAt(eye.x, eye.y, eye.z, center.x, center.y, center.z, up.x, up.y, up.z);
    }

    /**
     * Set this matrix to be a "lookat" transformation for a right-handed coordinate system, 
     * that aligns <code>-z</code> with <code>center - eye</code>.
     * <p>
     * In order to apply the lookat transformation to a previous existing transformation,
     * use {@link #lookAt(double, double, double, double, double, double, double, double, double) lookAt}.
     * 
     * @see #setLookAt(Vector3d, Vector3d, Vector3d)
     * @see #lookAt(double, double, double, double, double, double, double, double, double)
     * 
     * @param eyeX
     *              the x-coordinate of the eye/camera location
     * @param eyeY
     *              the y-coordinate of the eye/camera location
     * @param eyeZ
     *              the z-coordinate of the eye/camera location
     * @param centerX
     *              the x-coordinate of the point to look at
     * @param centerY
     *              the y-coordinate of the point to look at
     * @param centerZ
     *              the z-coordinate of the point to look at
     * @param upX
     *              the x-coordinate of the up vector
     * @param upY
     *              the y-coordinate of the up vector
     * @param upZ
     *              the z-coordinate of the up vector
     * @return this
     */
    public Matrix4d setLookAt(double eyeX, double eyeY, double eyeZ,
                              double centerX, double centerY, double centerZ,
                              double upX, double upY, double upZ) {
        // Compute direction from position to lookAt
        double dirX, dirY, dirZ;
        dirX = centerX - eyeX;
        dirY = centerY - eyeY;
        dirZ = centerZ - eyeZ;
        // Normalize direction
        double dirLength = (float) Math.sqrt(
                  (eyeX - centerX) * (eyeX - centerX)
                + (eyeY - centerY) * (eyeY - centerY)
                + (eyeZ - centerZ) * (eyeZ - centerZ));
        dirX /= dirLength;
        dirY /= dirLength;
        dirZ /= dirLength;
        // right = direction x up
        double rightX, rightY, rightZ;
        rightX = dirY * upZ - dirZ * upY;
        rightY = dirZ * upX - dirX * upZ;
        rightZ = dirX * upY - dirY * upX;
        // normalize right
        double rightLength = Math.sqrt(rightX * rightX + rightY * rightY + rightZ * rightZ);
        rightX /= rightLength;
        rightY /= rightLength;
        rightZ /= rightLength;
        // up = right x direction
        double upnX = rightY * dirZ - rightZ * dirY;
        double upnY = rightZ * dirX - rightX * dirZ;
        double upnZ = rightX * dirY - rightY * dirX;

        m00 = rightX;
        m01 = upnX;
        m02 = -dirX;
        m03 = 0.0;
        m10 = rightY;
        m11 = upnY;
        m12 = -dirY;
        m13 = 0.0;
        m20 = rightZ;
        m21 = upnZ;
        m22 = -dirZ;
        m23 = 0.0;
        m30 = -rightX * eyeX - rightY * eyeY - rightZ * eyeZ;
        m31 = -upnX * eyeX - upnY * eyeY - upnZ * eyeZ;
        m32 = dirX * eyeX + dirY * eyeY + dirZ * eyeZ;
        m33 = 1.0;

        return this;
    }

    /**
     * Apply a "lookat" transformation to this matrix for a right-handed coordinate system, 
     * that aligns <code>-z</code> with <code>center - eye</code> and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>L</code> the lookat matrix,
     * then the new matrix will be <code>M * L</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * L * v</code>,
     * the lookat transformation will be applied first!
     * <p>
     * In order to set the matrix to a lookat transformation without post-multiplying it,
     * use {@link #setLookAt(Vector3d, Vector3d, Vector3d)}.
     * 
     * @see #lookAt(double, double, double, double, double, double, double, double, double)
     * @see #setLookAlong(Vector3d, Vector3d)
     * 
     * @param eye
     *            the position of the camera
     * @param center
     *            the point in space to look at
     * @param up
     *            the direction of 'up'
     * @param dest
     *            will hold the result
     * @return this
     */
    public Matrix4d lookAt(Vector3d eye, Vector3d center, Vector3d up, Matrix4d dest) {
        return lookAt(eye.x, eye.y, eye.z, center.x, center.y, center.z, up.x, up.y, up.z, dest);
    }

    /**
     * Apply a "lookat" transformation to this matrix for a right-handed coordinate system, 
     * that aligns <code>-z</code> with <code>center - eye</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>L</code> the lookat matrix,
     * then the new matrix will be <code>M * L</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * L * v</code>,
     * the lookat transformation will be applied first!
     * <p>
     * In order to set the matrix to a lookat transformation without post-multiplying it,
     * use {@link #setLookAt(Vector3d, Vector3d, Vector3d)}.
     * 
     * @see #lookAt(double, double, double, double, double, double, double, double, double)
     * @see #setLookAlong(Vector3d, Vector3d)
     * 
     * @param eye
     *            the position of the camera
     * @param center
     *            the point in space to look at
     * @param up
     *            the direction of 'up'
     * @return this
     */
    public Matrix4d lookAt(Vector3d eye, Vector3d center, Vector3d up) {
        return lookAt(eye.x, eye.y, eye.z, center.x, center.y, center.z, up.x, up.y, up.z, this);
    }

    /**
     * Apply a "lookat" transformation to this matrix for a right-handed coordinate system, 
     * that aligns <code>-z</code> with <code>center - eye</code> and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>L</code> the lookat matrix,
     * then the new matrix will be <code>M * L</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * L * v</code>,
     * the lookat transformation will be applied first!
     * <p>
     * In order to set the matrix to a lookat transformation without post-multiplying it,
     * use {@link #setLookAt(double, double, double, double, double, double, double, double, double) setLookAt()}.
     * 
     * @see #lookAt(Vector3d, Vector3d, Vector3d)
     * @see #setLookAt(double, double, double, double, double, double, double, double, double)
     * 
     * @param eyeX
     *              the x-coordinate of the eye/camera location
     * @param eyeY
     *              the y-coordinate of the eye/camera location
     * @param eyeZ
     *              the z-coordinate of the eye/camera location
     * @param centerX
     *              the x-coordinate of the point to look at
     * @param centerY
     *              the y-coordinate of the point to look at
     * @param centerZ
     *              the z-coordinate of the point to look at
     * @param upX
     *              the x-coordinate of the up vector
     * @param upY
     *              the y-coordinate of the up vector
     * @param upZ
     *              the z-coordinate of the up vector
     * @param dest
     *          will hold the result
     * @return this
     */
    public Matrix4d lookAt(double eyeX, double eyeY, double eyeZ,
                           double centerX, double centerY, double centerZ,
                           double upX, double upY, double upZ, Matrix4d dest) {
        // Compute direction from position to lookAt
        double dirX, dirY, dirZ;
        dirX = centerX - eyeX;
        dirY = centerY - eyeY;
        dirZ = centerZ - eyeZ;
        // Normalize direction
        double dirLength = (float) Math.sqrt(
                  (eyeX - centerX) * (eyeX - centerX)
                + (eyeY - centerY) * (eyeY - centerY)
                + (eyeZ - centerZ) * (eyeZ - centerZ));
        dirX /= dirLength;
        dirY /= dirLength;
        dirZ /= dirLength;
        // right = direction x up
        double rightX, rightY, rightZ;
        rightX = dirY * upZ - dirZ * upY;
        rightY = dirZ * upX - dirX * upZ;
        rightZ = dirX * upY - dirY * upX;
        // normalize right
        double rightLength = Math.sqrt(rightX * rightX + rightY * rightY + rightZ * rightZ);
        rightX /= rightLength;
        rightY /= rightLength;
        rightZ /= rightLength;
        // up = right x direction
        double upnX = rightY * dirZ - rightZ * dirY;
        double upnY = rightZ * dirX - rightX * dirZ;
        double upnZ = rightX * dirY - rightY * dirX;

        // calculate right matrix elements
        double rm00 = rightX;
        double rm01 = upnX;
        double rm02 = -dirX;
        double rm10 = rightY;
        double rm11 = upnY;
        double rm12 = -dirY;
        double rm20 = rightZ;
        double rm21 = upnZ;
        double rm22 = -dirZ;
        double rm30 = -rightX * eyeX - rightY * eyeY - rightZ * eyeZ;
        double rm31 = -upnX * eyeX - upnY * eyeY - upnZ * eyeZ;
        double rm32 = dirX * eyeX + dirY * eyeY + dirZ * eyeZ;

        // perform optimized matrix multiplication
        // compute last column first, because others do not depend on it
        dest.m30 = m00 * rm30 + m10 * rm31 + m20 * rm32 + m30;
        dest.m31 = m01 * rm30 + m11 * rm31 + m21 * rm32 + m31;
        dest.m32 = m02 * rm30 + m12 * rm31 + m22 * rm32 + m32;
        dest.m33 = m03 * rm30 + m13 * rm31 + m23 * rm32 + m33;
        // introduce temporaries for dependent results
        double nm00 = m00 * rm00 + m10 * rm01 + m20 * rm02;
        double nm01 = m01 * rm00 + m11 * rm01 + m21 * rm02;
        double nm02 = m02 * rm00 + m12 * rm01 + m22 * rm02;
        double nm03 = m03 * rm00 + m13 * rm01 + m23 * rm02;
        double nm10 = m00 * rm10 + m10 * rm11 + m20 * rm12;
        double nm11 = m01 * rm10 + m11 * rm11 + m21 * rm12;
        double nm12 = m02 * rm10 + m12 * rm11 + m22 * rm12;
        double nm13 = m03 * rm10 + m13 * rm11 + m23 * rm12;
        dest.m20 = m00 * rm20 + m10 * rm21 + m20 * rm22;
        dest.m21 = m01 * rm20 + m11 * rm21 + m21 * rm22;
        dest.m22 = m02 * rm20 + m12 * rm21 + m22 * rm22;
        dest.m23 = m03 * rm20 + m13 * rm21 + m23 * rm22;
        // set the rest of the matrix elements
        dest.m00 = nm00;
        dest.m01 = nm01;
        dest.m02 = nm02;
        dest.m03 = nm03;
        dest.m10 = nm10;
        dest.m11 = nm11;
        dest.m12 = nm12;
        dest.m13 = nm13;

        return this;
    }

    /**
     * Apply a "lookat" transformation to this matrix for a right-handed coordinate system, 
     * that aligns <code>-z</code> with <code>center - eye</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>L</code> the lookat matrix,
     * then the new matrix will be <code>M * L</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * L * v</code>,
     * the lookat transformation will be applied first!
     * <p>
     * In order to set the matrix to a lookat transformation without post-multiplying it,
     * use {@link #setLookAt(double, double, double, double, double, double, double, double, double) setLookAt()}.
     * 
     * @see #lookAt(Vector3d, Vector3d, Vector3d)
     * @see #setLookAt(double, double, double, double, double, double, double, double, double)
     * 
     * @param eyeX
     *              the x-coordinate of the eye/camera location
     * @param eyeY
     *              the y-coordinate of the eye/camera location
     * @param eyeZ
     *              the z-coordinate of the eye/camera location
     * @param centerX
     *              the x-coordinate of the point to look at
     * @param centerY
     *              the y-coordinate of the point to look at
     * @param centerZ
     *              the z-coordinate of the point to look at
     * @param upX
     *              the x-coordinate of the up vector
     * @param upY
     *              the y-coordinate of the up vector
     * @param upZ
     *              the z-coordinate of the up vector
     * @return this
     */
    public Matrix4d lookAt(double eyeX, double eyeY, double eyeZ,
                           double centerX, double centerY, double centerZ,
                           double upX, double upY, double upZ) {
        return lookAt(eyeX, eyeY, eyeZ, centerX, centerY, centerZ, upX, upY, upZ, this);
    }

    /**
     * Apply a symmetric perspective projection frustum transformation to this matrix and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>P</code> the perspective projection matrix,
     * then the new matrix will be <code>M * P</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * P * v</code>,
     * the perspective projection will be applied first!
     * <p>
     * This method first computes the frustum corners using the specified parameters and then makes use of
     * {@link #frustum(double, double, double, double, double, double) frustum()} to finally apply the frustum
     * transformation.
     * <p>
     * In order to set the matrix to a perspective frustum transformation without post-multiplying,
     * use {@link #setPerspective(double, double, double, double) setPerspective}.
     * 
     * @see #frustum(double, double, double, double, double, double)
     * @see #setPerspective(double, double, double, double)
     * 
     * @param fovy
     *            the vertical field of view in degrees
     * @param aspect
     *            the aspect ratio (i.e. width / height)
     * @param zNear
     *            near clipping plane distance
     * @param zFar
     *            far clipping plane distance
     * @param dest
     *            will hold the result
     * @return this
     */
    public Matrix4d perspective(double fovy, double aspect, double zNear, double zFar, Matrix4d dest) {
        double h = Math.tan(Math.toRadians(fovy) * 0.5) * zNear;
        double w = h * aspect;

        // calculate right matrix elements
        double rm00 = 2.0 * zNear / (2.0 * w);
        double rm11 = 2.0 * zNear / (2.0 * h);
        double rm22 = -(zFar + zNear) / (zFar - zNear);
        double rm32 = -2.0 * zFar * zNear / (zFar - zNear);

        // perform optimized matrix multiplication
        double nm20 = m20 * rm22 - m30;
        double nm21 = m21 * rm22 - m31;
        double nm22 = m22 * rm22 - m32;
        double nm23 = m23 * rm22 - m33;
        dest.m00 = m00 * rm00;
        dest.m01 = m01 * rm00;
        dest.m02 = m02 * rm00;
        dest.m03 = m03 * rm00;
        dest.m10 = m10 * rm11;
        dest.m11 = m11 * rm11;
        dest.m12 = m12 * rm11;
        dest.m13 = m13 * rm11;
        dest.m30 = m20 * rm32;
        dest.m31 = m21 * rm32;
        dest.m32 = m22 * rm32;
        dest.m33 = m23 * rm32;
        dest.m20 = nm20;
        dest.m21 = nm21;
        dest.m22 = nm22;
        dest.m23 = nm23;

        return this;
    }

    /**
     * Apply a symmetric perspective projection frustum transformation to this matrix.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>P</code> the perspective projection matrix,
     * then the new matrix will be <code>M * P</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * P * v</code>,
     * the perspective projection will be applied first!
     * <p>
     * This method first computes the frustum corners using the specified parameters and then makes use of
     * {@link #frustum(double, double, double, double, double, double) frustum()} to finally apply the frustum
     * transformation.
     * <p>
     * In order to set the matrix to a perspective frustum transformation without post-multiplying,
     * use {@link #setPerspective(double, double, double, double) setPerspective}.
     * 
     * @see #frustum(double, double, double, double, double, double)
     * @see #setPerspective(double, double, double, double)
     * 
     * @param fovy
     *            the vertical field of view in degrees
     * @param aspect
     *            the aspect ratio (i.e. width / height)
     * @param zNear
     *            near clipping plane distance
     * @param zFar
     *            far clipping plane distance
     * @return this
     */
    public Matrix4d perspective(double fovy, double aspect, double zNear, double zFar) {
        return perspective(fovy, aspect, zNear, zFar, this);
    }

    /**
     * Set this matrix to be a symmetric perspective projection frustum transformation.
     * <p>
     * This method first computes the frustum corners using the specified parameters and then makes use of
     * {@link #setFrustum(double, double, double, double, double, double) setFrustum()} to finally apply the frustum
     * transformation.
     * <p>
     * In order to apply the perspective projection transformation to an existing transformation,
     * use {@link #perspective(double, double, double, double) perspective()}.
     * 
     * @see #setFrustum(double, double, double, double, double, double)
     * @see #perspective(double, double, double, double)
     * 
     * @param fovy
     *            the vertical field of view in degrees
     * @param aspect
     *            the aspect ratio (i.e. width / height)
     * @param zNear
     *            near clipping plane distance
     * @param zFar
     *            far clipping plane distance
     * @return this
     */
    public Matrix4d setPerspective(double fovy, double aspect, double zNear, double zFar) {
        double h = Math.tan(Math.toRadians(fovy) * 0.5) * zNear;
        double w = h * aspect;
        m00 = 2.0 * zNear / (2.0 * w);
        m01 = 0.0;
        m02 = 0.0;
        m03 = 0.0;
        m10 = 0.0;
        m11 = 2.0 * zNear / (2.0 * h);
        m12 = 0.0;
        m13 = 0.0;
        m20 = 0.0;
        m21 = 0.0;
        m22 = -(zFar + zNear) / (zFar - zNear);
        m23 = -1.0;
        m30 = 0.0;
        m31 = 0.0;
        m32 = -2.0 * zFar * zNear / (zFar - zNear);
        m33 = 0.0;
        return this;
    }

    /**
     * Apply an arbitrary perspective projection frustum transformation to this matrix 
     * and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>F</code> the frustum matrix,
     * then the new matrix will be <code>M * F</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * F * v</code>,
     * the frustum transformation will be applied first!
     * <p>
     * In order to set the matrix to a perspective frustum transformation without post-multiplying,
     * use {@link #setFrustum(double, double, double, double, double, double) setFrustum()}.
     * <p>
     * Reference: <a href="http://www.songho.ca/opengl/gl_projectionmatrix.html#perspective">http://www.songho.ca</a>
     * 
     * @see #setFrustum(double, double, double, double, double, double)
     * 
     * @param left
     *            the distance along the x-axis to the left frustum edge
     * @param right
     *            the distance along the x-axis to the right frustum edge
     * @param bottom
     *            the distance along the y-axis to the bottom frustum edge
     * @param top
     *            the distance along the y-axis to the top frustum edge
     * @param zNear
     *            the distance along the z-axis to the near clipping plane
     * @param zFar
     *            the distance along the z-axis to the far clipping plane
     * @param dest
     *            will hold the result
     * @return this
     */
    public Matrix4d frustum(double left, double right, double bottom, double top, double zNear, double zFar, Matrix4d dest) {
        // calculate right matrix elements
        double rm00 = 2.0 * zNear / (right - left);
        double rm11 = 2.0 * zNear / (top - bottom);
        double rm20 = (right + left) / (right - left);
        double rm21 = (top + bottom) / (top - bottom);
        double rm22 = -(zFar + zNear) / (zFar - zNear);
        double rm32 = -2.0 * zFar * zNear / (zFar - zNear);

        // perform optimized matrix multiplication
        double nm20 = m00 * rm20 + m10 * rm21 + m20 * rm22 - m30;
        double nm21 = m01 * rm20 + m11 * rm21 + m21 * rm22 - m31;
        double nm22 = m02 * rm20 + m12 * rm21 + m22 * rm22 - m32;
        double nm23 = m03 * rm20 + m13 * rm21 + m23 * rm22 - m33;
        dest.m00 = m00 * rm00;
        dest.m01 = m01 * rm00;
        dest.m02 = m02 * rm00;
        dest.m03 = m03 * rm00;
        dest.m10 = m10 * rm11;
        dest.m11 = m11 * rm11;
        dest.m12 = m12 * rm11;
        dest.m13 = m13 * rm11;
        dest.m30 = m20 * rm32;
        dest.m31 = m21 * rm32;
        dest.m32 = m22 * rm32;
        dest.m33 = m23 * rm32;
        dest.m20 = nm20;
        dest.m21 = nm21;
        dest.m22 = nm22;
        dest.m23 = nm23;
        dest.m30 = m30;
        dest.m31 = m31;
        dest.m32 = m32;
        dest.m33 = m33;

        return this;
    }

    /**
     * Apply an arbitrary perspective projection frustum transformation to this matrix.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>F</code> the frustum matrix,
     * then the new matrix will be <code>M * F</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * F * v</code>,
     * the frustum transformation will be applied first!
     * <p>
     * In order to set the matrix to a perspective frustum transformation without post-multiplying,
     * use {@link #setFrustum(double, double, double, double, double, double) setFrustum()}.
     * <p>
     * Reference: <a href="http://www.songho.ca/opengl/gl_projectionmatrix.html#perspective">http://www.songho.ca</a>
     * 
     * @see #setFrustum(double, double, double, double, double, double)
     * 
     * @param left
     *            the distance along the x-axis to the left frustum edge
     * @param right
     *            the distance along the x-axis to the right frustum edge
     * @param bottom
     *            the distance along the y-axis to the bottom frustum edge
     * @param top
     *            the distance along the y-axis to the top frustum edge
     * @param zNear
     *            the distance along the z-axis to the near clipping plane
     * @param zFar
     *            the distance along the z-axis to the far clipping plane
     * @return this
     */
    public Matrix4d frustum(double left, double right, double bottom, double top, double zNear, double zFar) {
        return frustum(left, right, bottom, top, zNear, zFar, this);
    }

    /**
     * Set this matrix to be an arbitrary perspective projection frustum transformation.
     * <p>
     * In order to apply the perspective frustum transformation to an existing transformation,
     * use {@link #frustum(double, double, double, double, double, double) frustum()}.
     * <p>
     * Reference: <a href="http://www.songho.ca/opengl/gl_projectionmatrix.html#perspective">http://www.songho.ca</a>
     * 
     * @see #frustum(double, double, double, double, double, double)
     * 
     * @param left
     *            the distance along the x-axis to the left frustum edge
     * @param right
     *            the distance along the x-axis to the right frustum edge
     * @param bottom
     *            the distance along the y-axis to the bottom frustum edge
     * @param top
     *            the distance along the y-axis to the top frustum edge
     * @param zNear
     *            the distance along the z-axis to the near clipping plane
     * @param zFar
     *            the distance along the z-axis to the far clipping plane
     * @return this
     */
    public Matrix4d setFrustum(double left, double right, double bottom, double top, double zNear, double zFar) {
        m00 = 2.0 * zNear / (right - left);
        m01 = 0.0;
        m02 = 0.0;
        m03 = 0.0;
        m10 = 0.0;
        m11 = 2.0 * zNear / (top - bottom);
        m12 = 0.0;
        m13 = 0.0;
        m20 = (right + left) / (right - left);
        m21 = (top + bottom) / (top - bottom);
        m22 = -(zFar + zNear) / (zFar - zNear);
        m23 = -1.0;
        m30 = 0.0;
        m31 = 0.0;
        m32 = -2.0 * zFar * zNear / (zFar - zNear);
        m33 = 0.0;
        return this;
    }

    /**
     * Return the specified {@link Matrix4f}.
     * <p>
     * When using method chaining in a fluent interface style, this method can be used to switch
     * the <i>context object</i>, on which further method invocations operate, to be the given matrix.
     * 
     * @param other
     *          the {@link Matrix4f} to return
     * @return that matrix
     */
    public Matrix4f with(Matrix4f other) {
        return other;
    }

    /**
     * Return the specified {@link Matrix4d}.
     * <p>
     * When using method chaining in a fluent interface style, this method can be used to switch
     * the <i>context object</i>, on which further method invocations operate, to be the given matrix.
     * 
     * @param other
     *          the {@link Matrix4d} to return
     * @return that matrix
     */
    public Matrix4d with(Matrix4d other) {
        return other;
    }

    /**
     * Return the specified {@link Vector3f}.
     * <p>
     * When using method chaining in a fluent interface style, this method can be used to switch
     * the <i>context object</i>, on which further method invocations operate, to be the given vector.
     * 
     * @param v
     *          the {@link Vector3f} to return
     * @return that vector
     */
    public Vector3f with(Vector3f v) {
        return v;
    }

    /**
     * Return the specified {@link Vector4f}.
     * <p>
     * When using method chaining in a fluent interface style, this method can be used to switch
     * the <i>context object</i>, on which further method invocations operate, to be the given vector.
     * 
     * @param v
     *          the {@link Vector4f} to return
     * @return that vector
     */
    public Vector4f with(Vector4f v) {
        return v;
    }

    /**
     * Return the specified {@link Quaternionf}.
     * <p>
     * When using method chaining in a fluent interface style, this method can be used to switch
     * the <i>context object</i>, on which further method invocations operate, to be the given quaternion.
     * 
     * @param q
     *          the {@link Quaternionf} to return
     * @return that quaternion
     */
    public Quaternionf with(Quaternionf q) {
        return q;
    }

    /**
     * Return the specified {@link Quaterniond}.
     * <p>
     * When using method chaining in a fluent interface style, this method can be used to switch
     * the <i>context object</i>, on which further method invocations operate, to be the given quaternion.
     * 
     * @param q
     *          the {@link Quaterniond} to return
     * @return that quaternion
     */
    public Quaterniond with(Quaterniond q) {
        return q;
    }

    /**
     * Return the specified {@link AxisAngle4f}.
     * <p>
     * When using method chaining in a fluent interface style, this method can be used to switch
     * the <i>context object</i>, on which further method invocations operate, to be the given {@link AxisAngle4f}.
     * 
     * @param a
     *          the {@link AxisAngle4f} to return
     * @return that quaternion
     */
    public AxisAngle4f with(AxisAngle4f a) {
        return a;
    }

    /**
     * Return the specified {@link Matrix3f}.
     * <p>
     * When using method chaining in a fluent interface style, this method can be used to switch
     * the <i>context object</i>, on which further method invocations operate, to be the given matrix.
     * 
     * @param m
     *          the {@link Matrix3f} to return
     * @return that matrix
     */
    public Matrix3f with(Matrix3f m) {
        return m;
    }

}
