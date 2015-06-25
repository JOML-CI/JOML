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
import java.io.Serializable;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Contains the definition of a 4x4 Matrix of doubles, and associated functions to transform
 * it. The matrix is column-major to match OpenGL's interpretation, and it looks like this:
 * <p>
 *      m00  m10  m20  m30</br>
 *      m01  m11  m21  m31</br>
 *      m02  m12  m22  m32</br>
 *      m03  m13  m23  m33</br>
 * 
 * @author Richard Greenlees
 * @author Kai Burjack
 */
public class Matrix4d implements Serializable, Externalizable {

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
     */
    public Matrix4d(double m00, double m01, double m02, double m03, double m10, double m11, double m12, double m13, double m20,
            double m21, double m22, double m23, double m30, double m31, double m32, double m33) {
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
    public Matrix4d mul(Matrix4d right) {
       mul(this, right, this);
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
       mul(this, right, this);
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
    public static void mul(Matrix4d left, Matrix4d right, Matrix4d dest) {
        if (left != dest && right != dest) {
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
    public static void mul(Matrix4d left, Matrix4f right, Matrix4d dest) {
        if (left != dest) {
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
       mul4x3(this, right, this);
       return this;
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
       mul4x3(this, right, dest);
       return this;
    }

    /**
     * Multiply the supplied <code>left</code> matrix by the top 4x3 submatrix of the supplied <code>right</code> and store the result into <code>dest</code>.
     * This method assumes that the last row of <code>right</code> is equal to <tt>(0, 0, 0, 1)</tt>.
     * <p>
     * If <code>L</code> is the <code>left</code> matrix and <code>R</code> the <code>right</code> matrix,
     * then the new matrix will be <code>L * R</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>L * R * v</code>, the
     * transformation of the right matrix will be applied first!
     *
     * @param left
     *          the left matrix
     * @param right
     *          the right matrix (the last row is assumed to be <tt>(0, 0, 0, 1)</tt>)
     * @param dest
     *          will hold the result
     */
    public static void mul4x3(Matrix4d left, Matrix4d right, Matrix4d dest) {
        if (left != dest && right != dest) {
            dest.m00 = left.m00 * right.m00 + left.m10 * right.m01 + left.m20 * right.m02;
            dest.m01 = left.m01 * right.m00 + left.m11 * right.m01 + left.m21 * right.m02;
            dest.m02 = left.m02 * right.m00 + left.m12 * right.m01 + left.m22 * right.m02;
            dest.m03 = left.m03 * right.m00 + left.m13 * right.m01 + left.m23 * right.m02;
            dest.m10 = left.m00 * right.m10 + left.m10 * right.m11 + left.m20 * right.m12;
            dest.m11 = left.m01 * right.m10 + left.m11 * right.m11 + left.m21 * right.m12;
            dest.m12 = left.m02 * right.m10 + left.m12 * right.m11 + left.m22 * right.m12;
            dest.m13 = left.m03 * right.m10 + left.m13 * right.m11 + left.m23 * right.m12;
            dest.m20 = left.m00 * right.m20 + left.m10 * right.m21 + left.m20 * right.m22;
            dest.m21 = left.m01 * right.m20 + left.m11 * right.m21 + left.m21 * right.m22;
            dest.m22 = left.m02 * right.m20 + left.m12 * right.m21 + left.m22 * right.m22;
            dest.m23 = left.m03 * right.m20 + left.m13 * right.m21 + left.m23 * right.m22;
            dest.m30 = left.m00 * right.m30 + left.m10 * right.m31 + left.m20 * right.m32 + left.m30;
            dest.m31 = left.m01 * right.m30 + left.m11 * right.m31 + left.m21 * right.m32 + left.m31;
            dest.m32 = left.m02 * right.m30 + left.m12 * right.m31 + left.m22 * right.m32 + left.m32;
            dest.m33 = left.m03 * right.m30 + left.m13 * right.m31 + left.m23 * right.m32 + left.m33;
        } else {
            dest.set(left.m00 * right.m00 + left.m10 * right.m01 + left.m20 * right.m02,
                     left.m01 * right.m00 + left.m11 * right.m01 + left.m21 * right.m02,
                     left.m02 * right.m00 + left.m12 * right.m01 + left.m22 * right.m02,
                     left.m03 * right.m00 + left.m13 * right.m01 + left.m23 * right.m02,
                     left.m00 * right.m10 + left.m10 * right.m11 + left.m20 * right.m12,
                     left.m01 * right.m10 + left.m11 * right.m11 + left.m21 * right.m12,
                     left.m02 * right.m10 + left.m12 * right.m11 + left.m22 * right.m12,
                     left.m03 * right.m10 + left.m13 * right.m11 + left.m23 * right.m12,
                     left.m00 * right.m20 + left.m10 * right.m21 + left.m20 * right.m22,
                     left.m01 * right.m20 + left.m11 * right.m21 + left.m21 * right.m22,
                     left.m02 * right.m20 + left.m12 * right.m21 + left.m22 * right.m22,
                     left.m03 * right.m20 + left.m13 * right.m21 + left.m23 * right.m22,
                     left.m00 * right.m30 + left.m10 * right.m31 + left.m20 * right.m32 + left.m30,
                     left.m01 * right.m30 + left.m11 * right.m31 + left.m21 * right.m32 + left.m31,
                     left.m02 * right.m30 + left.m12 * right.m31 + left.m22 * right.m32 + left.m32,
                     left.m03 * right.m30 + left.m13 * right.m31 + left.m23 * right.m32 + left.m33);
        }
    }

    /** Set the values within this matrix to the supplied double values. The matrix will look like this:<br><br>
        
        m00, m10, m20, m30<br>
        m01, m11, m21, m31<br>
        m02, m12, m22, m32<br>
        m03, m13, m23, m33
      
        @return this
     */
    public Matrix4d set(double m00, double m01, double m02, double m03, double m10, double m11, double m12, double m13, double m20,
            double m21, double m22, double m23, double m30, double m31, double m32, double m33) {
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

    /** Set the values in the matrix using a double array. The results will look like this:<br><br>
     * 
     * 0, 4, 8, 12<br>
     * 1, 5, 9, 13<br>
     * 2, 6, 10, 14<br>
     * 3, 7, 11, 15<br>
     * 
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
     * Set the values of this matrix by reading 16 float values from the given {@link DoubleBuffer} in column-major order,
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
     */
    public double determinant() {
        return (m00 * m11 - m01 * m10) * (m22 * m33 - m23 * m32) - (m00 * m12 - m02 * m10) * (m21 * m33 - m23 * m31)
             + (m00 * m13 - m03 * m10) * (m21 * m32 - m22 * m31) + (m01 * m12 - m02 * m11) * (m20 * m33 - m23 * m30)
             - (m01 * m13 - m03 * m11) * (m20 * m32 - m22 * m30) + (m02 * m13 - m03 * m12) * (m20 * m31 - m21 * m30);
    }
    
    /**
     * Return the determinant of the supplied matrix.
     */
    public static double determinant(Matrix4d source) {
        return (source.m00 * source.m11 - source.m01 * source.m10) * (source.m22 * source.m33 - source.m23 * source.m32) - (source.m00 * source.m12 - source.m02 * source.m10) * (source.m21 * source.m33 - source.m23 * source.m31)
             + (source.m00 * source.m13 - source.m03 * source.m10) * (source.m21 * source.m32 - source.m22 * source.m31) + (source.m01 * source.m12 - source.m02 * source.m11) * (source.m20 * source.m33 - source.m23 * source.m30)
             - (source.m01 * source.m13 - source.m03 * source.m11) * (source.m20 * source.m32 - source.m22 * source.m30) + (source.m02 * source.m13 - source.m03 * source.m12) * (source.m20 * source.m31 - source.m21 * source.m30);
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
     * 			will hold the result
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
     * 			will hold the result
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
     * Set this matrix to be a simple translation matrix.
     * <p>
     * The resulting matrix can be multiplied against another transformation
     * matrix to obtain an additional translation.
     * 
     * @return this
     */
    public Matrix4d translation(double x, double y, double z) {
    	identity();
        m30 = x;
        m31 = y;
        m32 = z;
        return this;
    }

    /**
     * Set this matrix to be a simple translation matrix.
     * <p>
     * The resulting matrix can be multiplied against another transformation
     * matrix to obtain an additional translation.
     * 
     * @return this
     */
    public Matrix4d translation(Vector3f position) {
        translation(position.x, position.y, position.z);
        return this;
    }

    /**
     * Set this matrix to be a simple translation matrix.
     * <p>
     * The resulting matrix can be multiplied against another transformation
     * matrix to obtain an additional translation.
     * 
     * @return this
     */
    public Matrix4d translation(Vector3d position) {
        translation(position.x, position.y, position.z);
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
        DecimalFormat formatter = new DecimalFormat("  0.000E0; -");
        return toString(formatter).replaceAll("E(\\d+)", "E+$1");
    }

    /**
     * Return a string representation of this matrix by formatting the matrix elements with the given {@link NumberFormat}.
     * 
     * @param formatter
     *          the {@link NumberFormat} used to format the matrix values with
     * @return the string representation
     */
    public String toString(NumberFormat formatter) {
        return formatter.format(m00) + formatter.format(m10) + formatter.format(m20) + formatter.format(m30) + "\n"
             + formatter.format(m01) + formatter.format(m11) + formatter.format(m21) + formatter.format(m31) + "\n"
             + formatter.format(m02) + formatter.format(m12) + formatter.format(m22) + formatter.format(m32) + "\n"
             + formatter.format(m03) + formatter.format(m13) + formatter.format(m23) + formatter.format(m33) + "\n";
    }

    /**
     * Get the current values of <code>this</code> matrix and store them into
     * <code>dest</code>.
     * <p>
     * This is the reverse method of {@link #set(Matrix4d)} and allows to obtain
     * intermediate calculation results when chaining multiple transformations.
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
     * Set all the values within this matrix to 0.
     * 
     * @return this
     */
    public Matrix4d zero() {
    	identity();
        m00 = 0.0;
        m11 = 0.0;
        m22 = 0.0;
        m33 = 0.0;
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
    	identity();
        m00 = x;
        m11 = y;
        m22 = z;
        return this;
    }
    
    /**
     * Set this matrix to be a simple scale matrix.
     * 
     * @param scale
     * 			the scale applied to each dimension
     * @return this
     */
    public Matrix4d scaling(Vector3d scale) {
    	identity();
        m00 = scale.x;
        m11 = scale.y;
        m22 = scale.z;
        return this;
    }
    
    /**
     * Set the given matrix <code>dest</code> to be a simple scale matrix.
     * 
     * @param scale
     * 			the scale applied to each dimension
     */
    public static void scaling(Vector3d scale, Matrix4d dest) {
    	dest.identity();
        dest.m00 = scale.x;
        dest.m11 = scale.y;
        dest.m22 = scale.z;
    }
    
    /**
     * Set the given matrix <code>dest</code> to be a simple scale matrix.
     */
    public static void scaling(double x, double y, double z, Matrix4d dest) {
    	dest.identity();
        dest.m00 = x;
        dest.m11 = y;
        dest.m22 = z;
    }
    
    /**
     * Set this matrix to a rotation matrix which rotates the given radians about a given axis.
     * 
     * From <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Rotation_matrix_from_axis_and_angle">Wikipedia</a>
     * 
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
     * Set this matrix to a rotation matrix which rotates the given radians about a given axis.
     * 
     * @return this
     */
    public Matrix4d rotation(double angle, Vector3d axis) {
        return rotation(angle, axis.x, axis.y, axis.z);
    }
    
    /**
     * Set the destination matrix to a rotation matrix which rotates the given radians about a given axis.
     * 
     * From <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Rotation_matrix_from_axis_and_angle">Wikipedia</a>
     */
    public static void rotation(double angle, double x, double y, double z, Matrix4d dest) {
    	double cos = Math.cos(angle);
    	double sin = Math.sin(angle);
    	dest.m00 = cos + x * x * (1.0 - cos);
    	dest.m10 = x * y * (1.0 - cos) - z * sin;
    	dest.m20 = x * z * (1.0 - cos) + y * sin;
    	dest.m30 = 0.0;
    	dest.m01 = y * x * (1.0 - cos) + z * sin;
    	dest.m11 = cos + y * y * (1.0 - cos);
    	dest.m21 = y * z * (1.0 - cos) - x * sin;
    	dest.m31 = 0.0;
    	dest.m02 = z * x * (1.0 - cos) - y * sin;
    	dest.m12 = z * y * (1.0 - cos) + x * sin;
    	dest.m22 = cos + z * z * (1.0 - cos);
    	dest.m32 = 0.0;
    	dest.m03 = 0.0;
    	dest.m13 = 0.0;
    	dest.m23 = 0.0;
    	dest.m33 = 1.0;
    }

    /**
     * Set the destination matrix to a rotation matrix which rotates the given degrees about the specified axis.
     * The result will be stored in <code>dest</code>.
     * 
     * @param angle
     *          the angle in degrees
     * @param axis
     *          the axis to rotate about
     * @param dest
     *          will hold the result
     */
    public static void rotation(double angle, Vector3d axis, Matrix4d dest) {
        rotation(angle, axis.x, axis.y, axis.z, dest);
    }

    /**
     * Transform the given vector by this matrix.
     * 
     * @param v
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
     * Transform the given vector by the given matrix.
     * 
     * @param mat
     * @param v
     */
    public static void transform(Matrix4d mat, Vector4d v) {
        v.mul(mat);
    }

    /**
     * Set the upper 3x3 matrix of the given <code>dest</code> {@link Matrix4d} to the given {@link Matrix3d} and the rest to the identity.
     */
    public static void fromMatrix3(Matrix3d mat, Matrix4d dest) {
        dest.m00 = mat.m00;
        dest.m01 = mat.m01;
        dest.m02 = mat.m02;
        dest.m03 = 0.0;
        dest.m10 = mat.m10;
        dest.m11 = mat.m11;
        dest.m12 = mat.m12;
        dest.m13 = 0.0;
        dest.m20 = mat.m20;
        dest.m21 = mat.m21;
        dest.m22 = mat.m22;
        dest.m23 = 0.0;
        dest.m30 = 0.0;
        dest.m31 = 0.0;
        dest.m32 = 0.0;
        dest.m33 = 1.0;
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
        Matrix4d c = this;
        // scale matrix elements:
        // m00 = x, m11 = y, m22 = z
        // m33 = 1
        // all others = 0
        c.m00 = c.m00 * x;
        c.m01 = c.m01 * x;
        c.m02 = c.m02 * x;
        c.m03 = c.m03 * x;
        c.m10 = c.m10 * y;
        c.m11 = c.m11 * y;
        c.m12 = c.m12 * y;
        c.m13 = c.m13 * y;
        c.m20 = c.m20 * z;
        c.m21 = c.m21 * z;
        c.m22 = c.m22 * z;
        c.m23 = c.m23 * z;
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
     * 
     * @param x
     * @param y
     * @param z
     * @return this
     */
    public Matrix4d translate(double x, double y, double z) {
        Matrix4d c = this;
        // translation matrix elements:
        // m00, m11, m22, m33 = 1
        // m30 = x, m31 = y, m32 = z
        // all others = 0
        c.m30 = c.m00 * x + c.m10 * y + c.m20 * z + c.m30;
        c.m31 = c.m01 * x + c.m11 * y + c.m21 * z + c.m31;
        c.m32 = c.m02 * x + c.m12 * y + c.m22 * z + c.m32;
        c.m33 = c.m03 * x + c.m13 * y + c.m23 * z + c.m33;
        return this;
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
        double q00 = 2.0f * quat.x * quat.x;
        double q11 = 2.0f * quat.y * quat.y;
        double q22 = 2.0f * quat.z * quat.z;
        double q01 = 2.0f * quat.x * quat.y;
        double q02 = 2.0f * quat.x * quat.z;
        double q03 = 2.0f * quat.x * quat.w;
        double q12 = 2.0f * quat.y * quat.z;
        double q13 = 2.0f * quat.y * quat.w;
        double q23 = 2.0f * quat.z * quat.w;

        double rm00 = 1.0f - q11 - q22;
        double rm01 = q01 + q23;
        double rm02 = q02 - q13;
        double rm10 = q01 - q23;
        double rm11 = 1.0f - q22 - q00;
        double rm12 = q12 + q03;
        double rm20 = q02 + q13;
        double rm21 = q12 - q03;
        double rm22 = 1.0f - q11 - q00;

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
        return;
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
        return;
    }

    /**
     * Compute a normal matrix from the top-left 3x3 submatrix of <code>this</code> and store it into the top-left 3x3 submatrix of <code>dest</code>.
     * All other values of <code>dest</code> will be set to {@link #identity() identity}. 
     * 
     * @param dest
     * 			will hold the result
     * @return this
     */
    public Matrix4d normal(Matrix4d dest) {
    	// see: http://mathworld.wolfram.com/OrthogonalMatrix.html
    	double det = determinant();
    	double diff = Math.abs(Math.abs(det) - 1.0);
    	if (diff < 1E-8) {
    		/* The fast path, if only 1:1:1 scaling is being used */
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
    	}
    	/* The general case */
        double s = 1.0 / det;
        /* Invert and transpose in one go */
        dest.set(((m11 * m22) - (m21 * m12)) * s,
                -((m10 * m22) - (m20 * m12) * s),
	             ((m10 * m21) - (m20 * m11) * s),
	             0.0,
	            -((m01 * m22) - (m21 * m02)) * s,
	             ((m00 * m22) - (m20 * m02)) * s,
	            -((m00 * m21) - (m20 * m01)) * s,
	             0.0,
	             ((m01 * m12) - (m11 * m02)) * s,
	            -((m00 * m12) - (m10 * m02)) * s,
	             ((m00 * m11) - (m10 * m01)) * s,
	             0.0,
	             0.0, 0.0, 0.0, 1.0);
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
     * The depth range of <tt>winZ</tt> is assumed to be <tt>[0..1]</tt>, which is also the OpenGL default.
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
     * The depth range of <tt>winZ</tt> is assumed to be <tt>[0..1]</tt>, which is also the OpenGL default.
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
     * The depth range of <tt>winZ</tt> is assumed to be <tt>[0..1]</tt>, which is also the OpenGL default.  
     * 
     * @see #unproject(double, double, double, Matrix4d, Matrix4d, IntBuffer, Matrix4d, Vector4d)
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
    public static void project(float x, float y, float z, Matrix4f projection, Matrix4f view, IntBuffer viewport, Vector4f winCoordsDest) {
        winCoordsDest.set(x, y, z, 1.0f);
        view.transform(winCoordsDest);
        projection.transform(winCoordsDest);
        int pos = viewport.position();
        winCoordsDest.mul(1.0f / winCoordsDest.w);
        winCoordsDest.x = (winCoordsDest.x*0.5f+0.5f) * viewport.get(pos+2) + viewport.get(pos);
        winCoordsDest.y = (winCoordsDest.y*0.5f+0.5f) * viewport.get(pos+3) + viewport.get(pos+1);
        winCoordsDest.z = (1.0f+winCoordsDest.z)*0.5f;
    }

    /**
     * Project the given <code>position</code> via the given <code>view</code> and <code>projection</code> matrices using the specified viewport
     * and store the resulting window coordinates in <code>winCoordsDest</code>.
     * <p>
     * This method transforms the given coordinates by <code>projection * view</code> including perspective division to 
     * obtain normalized device coordinates, and then translates these into window coordinates by using the
     * given <code>viewport</code> settings <tt>[x, y, width, height]</tt>.
     * <p>
     * The depth range of the returned <code>winCoordsDest.z</code> will be <tt>[0..1]</tt>, which is also the OpenGL default.  
     * 
     * @see #project(float, float, float, Matrix4f, Matrix4f, IntBuffer, Vector4f)
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
    public static void project(Vector3f position, Matrix4f projection, Matrix4f view, IntBuffer viewport, Vector4f winCoordsDest) {
        project(position.x, position.y, position.z, projection, view, viewport, winCoordsDest);
    }

}
