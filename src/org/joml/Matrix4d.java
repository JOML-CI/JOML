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
import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;

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
public class Matrix4d extends Matrix4dr implements Externalizable {

    private static final long serialVersionUID = 1L;

    public double m00, m10, m20, m30;
    public double m01, m11, m21, m31;
    public double m02, m12, m22, m32;
    public double m03, m13, m23, m33;

    /**
     * Create a new {@link Matrix4d} and set it to {@link #identity() identity}.
     */
    public Matrix4d() {
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
    }

    /**
     * Create a new {@link Matrix4d} and make it a copy of the given matrix.
     * 
     * @param mat
     *          the {@link Matrix4dr} to copy the values from
     */
    public Matrix4d(Matrix4dr mat) {
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
     * Create a new {@link Matrix4d} and make it a copy of the given matrix.
     * 
     * @param mat
     *          the {@link Matrix4fr} to copy the values from
     */
    public Matrix4d(Matrix4fr mat) {
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
     * Create a new {@link Matrix4d} by setting its uppper left 3x3 submatrix to the values of the given {@link Matrix3dr}
     * and the rest to identity.
     * 
     * @param mat
     *          the {@link Matrix3dr}
     */
    public Matrix4d(Matrix3dr mat) {
        m00 = mat.m00();
        m01 = mat.m01();
        m02 = mat.m02();
        m10 = mat.m10();
        m11 = mat.m11();
        m12 = mat.m12();
        m20 = mat.m20();
        m21 = mat.m21();
        m22 = mat.m22();
        m33 = 1.0;
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

    public double m00() {
        return m00;
    }

    public double m10() {
        return m10;
    }

    public double m20() {
        return m20;
    }

    public double m30() {
        return m30;
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

    public double m31() {
        return m31;
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

    public double m32() {
        return m32;
    }

    public double m03() {
        return m03;
    }

    public double m13() {
        return m13;
    }

    public double m23() {
        return m23;
    }

    public double m33() {
        return m33;
    }
    
    /**
     * Reset this matrix to the identity.
     * <p>
     * Please note that if a call to {@link #identity()} is immediately followed by a call to:
     * {@link #translate(double, double, double) translate}, 
     * {@link #rotate(double, double, double, double) rotate},
     * {@link #scale(double, double, double) scale},
     * {@link #perspective(double, double, double, double) perspective},
     * {@link #frustum(double, double, double, double, double, double) frustum},
     * {@link #ortho(double, double, double, double, double, double) ortho},
     * {@link #ortho2D(double, double, double, double) ortho2D},
     * {@link #lookAt(double, double, double, double, double, double, double, double, double) lookAt},
     * {@link #lookAlong(double, double, double, double, double, double) lookAlong},
     * or any of their overloads, then the call to {@link #identity()} can be omitted and the subsequent call replaced with:
     * {@link #translation(double, double, double) translation},
     * {@link #rotation(double, double, double, double) rotation},
     * {@link #scaling(double, double, double) scaling},
     * {@link #setPerspective(double, double, double, double) setPerspective},
     * {@link #setFrustum(double, double, double, double, double, double) setFrustum},
     * {@link #setOrtho(double, double, double, double, double, double) setOrtho},
     * {@link #setOrtho2D(double, double, double, double) setOrtho2D},
     * {@link #setLookAt(double, double, double, double, double, double, double, double, double) setLookAt},
     * {@link #setLookAlong(double, double, double, double, double, double) setLookAlong},
     * or any of their overloads.
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
     * @see #Matrix4d(Matrix4dr)
     * @see #get(Matrix4d)
     * 
     * @param m
     *          the matrix to copy the values from
     * @return this
     */
    public Matrix4d set(Matrix4dr m) {
        m00 = m.m00();
        m01 = m.m01();
        m02 = m.m02();
        m03 = m.m03();
        m10 = m.m10();
        m11 = m.m11();
        m12 = m.m12();
        m13 = m.m13();
        m20 = m.m20();
        m21 = m.m21();
        m22 = m.m22();
        m23 = m.m23();
        m30 = m.m30();
        m31 = m.m31();
        m32 = m.m32();
        m33 = m.m33();
        return this;
    }

    /**
     * Store the values of the given matrix <code>m</code> into <code>this</code> matrix.
     * 
     * @see #Matrix4d(Matrix4fr)
     * 
     * @param m
     *          the matrix to copy the values from
     * @return this
     */
    public Matrix4d set(Matrix4fr m) {
        m00 = m.m00();
        m01 = m.m01();
        m02 = m.m02();
        m03 = m.m03();
        m10 = m.m10();
        m11 = m.m11();
        m12 = m.m12();
        m13 = m.m13();
        m20 = m.m20();
        m21 = m.m21();
        m22 = m.m22();
        m23 = m.m23();
        m30 = m.m30();
        m31 = m.m31();
        m32 = m.m32();
        m33 = m.m33();
        return this;
    }

    /**
     * Set the upper left 3x3 submatrix of this {@link Matrix4d} to the given {@link Matrix3dr}
     * and the rest to identity.
     * 
     * @see #Matrix4d(Matrix3dr)
     * 
     * @param mat
     *          the {@link Matrix3dr}
     * @return this
     */
    public Matrix4d set(Matrix3dr mat) {
        m00 = mat.m00();
        m01 = mat.m01();
        m02 = mat.m02();
        m03 = 0.0;
        m10 = mat.m10();
        m11 = mat.m11();
        m12 = mat.m12();
        m13 = 0.0;
        m20 = mat.m20();
        m21 = mat.m21();
        m22 = mat.m22();
        m23 = 0.0;
        m30 = 0.0;
        m31 = 0.0;
        m32 = 0.0;
        m33 = 1.0;
        return this;
    }

    /**
     * Set the upper left 3x3 submatrix of this {@link Matrix4d} to that of the given {@link Matrix4dr}
     * and the rest to identity.
     * 
     * @param mat
     *          the {@link Matrix4d}
     * @return this
     */
    public Matrix4d set3x3(Matrix4dr mat) {
        m00 = mat.m00();
        m01 = mat.m01();
        m02 = mat.m02();
        m03 = 0.0;
        m10 = mat.m10();
        m11 = mat.m11();
        m12 = mat.m12();
        m13 = 0.0;
        m20 = mat.m20();
        m21 = mat.m21();
        m22 = mat.m22();
        m23 = 0.0;
        m30 = 0.0;
        m31 = 0.0;
        m32 = 0.0;
        m33 = 1.0;
        return this;
    }

    /**
     * Set this matrix to be equivalent to the rotation specified by the given {@link AxisAngle4fr}.
     * 
     * @param axisAngle
     *          the {@link AxisAngle4fr}
     * @return this
     */
    public Matrix4d set(AxisAngle4fr axisAngle) {
        double x = axisAngle.x();
        double y = axisAngle.y();
        double z = axisAngle.z();
        double angle = axisAngle.angle();
        double invLength = 1.0 / Math.sqrt(x*x + y*y + z*z);
        x *= invLength;
        y *= invLength;
        z *= invLength;
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
        m30 = 0.0;
        m31 = 0.0;
        m32 = 0.0;
        m33 = 1.0;
        return this;
    }

    /**
     * Set this matrix to be equivalent to the rotation specified by the given {@link AxisAngle4dr}.
     * 
     * @param axisAngle
     *          the {@link AxisAngle4dr}
     * @return this
     */
    public Matrix4d set(AxisAngle4dr axisAngle) {
        double x = axisAngle.x();
        double y = axisAngle.y();
        double z = axisAngle.z();
        double angle = axisAngle.angle();
        double invLength = 1.0 / Math.sqrt(x*x + y*y + z*z);
        x *= invLength;
        y *= invLength;
        z *= invLength;
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
        m30 = 0.0;
        m31 = 0.0;
        m32 = 0.0;
        m33 = 1.0;
        return this;
    }

    /**
     * Set this matrix to be equivalent to the rotation specified by the given {@link Quaternionfr}.
     * 
     * @see Quaternionfr#get(Matrix4d)
     * 
     * @param q
     *          the {@link Quaternionfr}
     * @return this
     */
    public Matrix4d set(Quaternionfr q) {
        return q.get(this);
    }

    /**
     * Set this matrix to be equivalent to the rotation specified by the given {@link Quaterniondr}.
     * 
     * @see Quaterniondr#get(Matrix4d)
     * 
     * @param q
     *          the {@link Quaterniondr}
     * @return this
     */
    public Matrix4d set(Quaterniondr q) {
        return q.get(this);
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
    public Matrix4d mul(Matrix4dr right) {
        return mul(right, this);
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
    public Matrix4d mul(Matrix4fr right) {
        return mul(right, this);
    }

    /**
     * Multiply this matrix by the top 4x3 submatrix of the supplied <code>right</code> matrix and store the result in <code>this</code>.
     * This method assumes that the last row of <code>right</code> is equal to <tt>(0, 0, 0, 1)</tt>.
     * <p>
     * This method can be used to speed up matrix multiplication if the <code>right</code> matrix only represents affine transformations, such as
     * translation, rotation, scaling and shearing (in any combination).
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
    public Matrix4d mul4x3r(Matrix4dr right) {
       return mul4x3r(right, this);
    }

    /**
     * Multiply the top 4x3 submatrix of this matrix by the top 4x3 submatrix of the supplied <code>right</code> matrix and store the result in <code>this</code>.
     * This method assumes that the last row of both <code>this</code> and <code>right</code> is equal to <tt>(0, 0, 0, 1)</tt>.
     * <p>
     * This method can be used to speed up matrix multiplication if both <code>this</code> and the <code>right</code> matrix only represent affine transformations,
     * such as translation, rotation, scaling and shearing (in any combination).
     * <p>
     * This method will not modify either the last row of <code>this</code> or the last row of <code>right</code>.
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
    public Matrix4d mul4x3(Matrix4dr right) {
       return mul4x3(right, this);
    }

    /**
     * Component-wise add the upper 4x3 submatrices of <code>this</code> and <code>other</code>
     * by first multiplying each component of <code>other</code>'s 4x3 submatrix by <code>otherFactor</code> and
     * adding that result to <code>this</code>.
     * <p>
     * The matrix <code>other</code> will not be changed.
     * 
     * @param other
     *          the other matrix
     * @param otherFactor
     *          the factor to multiply each of the other matrix's 4x3 components
     * @return this
     */
    public Matrix4d fma4x3(Matrix4dr other, double otherFactor) {
        return fma4x3(other, otherFactor, this);
    }

    /**
     * Component-wise add <code>this</code> and <code>other</code>.
     * 
     * @param other
     *          the other addend
     * @return this
     */
    public Matrix4d add(Matrix4dr other) {
        return add(other, this);
    }

    /**
     * Component-wise subtract <code>subtrahend</code> from <code>this</code>.
     * 
     * @param subtrahend
     *          the subtrahend
     * @return this
     */
    public Matrix4d sub(Matrix4dr subtrahend) {
        return sub(subtrahend, this);
    }

    /**
     * Component-wise multiply <code>this</code> by <code>other</code>.
     * 
     * @param other
     *          the other matrix
     * @return this
     */
    public Matrix4d mulComponentWise(Matrix4dr other) {
        return mulComponentWise(other, this);
    }

    /**
     * Component-wise add the upper 4x3 submatrices of <code>this</code> and <code>other</code>.
     * 
     * @param other
     *          the other addend
     * @return this
     */
    public Matrix4d add4x3(Matrix4dr other) {
        return add4x3(other, this);
    }

    /**
     * Component-wise subtract the upper 4x3 submatrices of <code>subtrahend</code> from <code>this</code>.
     * 
     * @param subtrahend
     *          the subtrahend
     * @return this
     */
    public Matrix4d sub4x3(Matrix4dr subtrahend) {
        return sub4x3(subtrahend, this);
    }

    /**
     * Component-wise multiply the upper 4x3 submatrices of <code>this</code> by <code>other</code>.
     * 
     * @param other
     *          the other matrix
     * @return this
     */
    public Matrix4d mul4x3ComponentWise(Matrix4dr other) {
        return mul4x3ComponentWise(other, this);
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

    /**
     * Set the values in the matrix using a double array that contains the matrix elements in column-major order.
     * <p>
     * The results will look like this:<br><br>
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

    /**
     * Set the values in the matrix using a float array that contains the matrix elements in column-major order.
     * <p>
     * The results will look like this:<br><br>
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
     * Set the values of this matrix by reading 16 double values from the given {@link ByteBuffer} in column-major order,
     * starting at its current position.
     * <p>
     * The ByteBuffer is expected to contain the values in column-major order.
     * <p>
     * The position of the ByteBuffer will not be changed by this method.
     * 
     * @param buffer
     *              the ByteBuffer to read the matrix values from in column-major order
     * @return this
     */
    public Matrix4d set(ByteBuffer buffer) {
        int pos = buffer.position();
        m00 = buffer.getDouble(pos);
        m01 = buffer.getDouble(pos+8*1);
        m02 = buffer.getDouble(pos+8*2);
        m03 = buffer.getDouble(pos+8*3);
        m10 = buffer.getDouble(pos+8*4);
        m11 = buffer.getDouble(pos+8*5);
        m12 = buffer.getDouble(pos+8*6);
        m13 = buffer.getDouble(pos+8*7);
        m20 = buffer.getDouble(pos+8*8);
        m21 = buffer.getDouble(pos+8*9);
        m22 = buffer.getDouble(pos+8*10);
        m23 = buffer.getDouble(pos+8*11);
        m30 = buffer.getDouble(pos+8*12);
        m31 = buffer.getDouble(pos+8*13);
        m32 = buffer.getDouble(pos+8*14);
        m33 = buffer.getDouble(pos+8*15);
        return this;
    }

    /**
     * Set the values of this matrix by reading 16 float values from the given {@link ByteBuffer} in column-major order,
     * starting at its current position.
     * <p>
     * The ByteBuffer is expected to contain the values in column-major order.
     * <p>
     * The position of the ByteBuffer will not be changed by this method.
     * 
     * @param buffer
     *              the ByteBuffer to read the matrix values from in column-major order
     * @return this
     */
    public Matrix4d setFloats(ByteBuffer buffer) {
        int pos = buffer.position();
        m00 = buffer.getFloat(pos);
        m01 = buffer.getFloat(pos+4*1);
        m02 = buffer.getFloat(pos+4*2);
        m03 = buffer.getFloat(pos+4*3);
        m10 = buffer.getFloat(pos+4*4);
        m11 = buffer.getFloat(pos+4*5);
        m12 = buffer.getFloat(pos+4*6);
        m13 = buffer.getFloat(pos+4*7);
        m20 = buffer.getFloat(pos+4*8);
        m21 = buffer.getFloat(pos+4*9);
        m22 = buffer.getFloat(pos+4*10);
        m23 = buffer.getFloat(pos+4*11);
        m30 = buffer.getFloat(pos+4*12);
        m31 = buffer.getFloat(pos+4*13);
        m32 = buffer.getFloat(pos+4*14);
        m33 = buffer.getFloat(pos+4*15);
        return this;
    }

    /**
     * Invert this matrix.
     * <p>
     * If <code>this</code> matrix is only composed of affine transformations, such as translation, rotation, scaling and shearing,
     * and thus its last row is equal to <tt>(0, 0, 0, 1)</tt>, then {@link #invert4x3()} can be used instead of this method.
     * 
     * @see #invert4x3()
     * 
     * @return this
     */
    public Matrix4d invert() {
        return invert(this);
    }

    /**
     * Invert this matrix by assuming that its last row is equal to <tt>(0, 0, 0, 1)</tt>.
     * 
     * @return this 
     */
    public Matrix4d invert4x3() {
        return invert4x3(this);
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
     * Transpose only the upper left 3x3 submatrix of this matrix and set the rest of the matrix elements to identity.
     * 
     * @return this 
     */
    public Matrix4d transpose3x3() {
        return transpose3x3(this);
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
    public Matrix4d translation(Vector3fr offset) {
        return translation(offset.x(), offset.y(), offset.z());
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
    public Matrix4d translation(Vector3dr offset) {
        return translation(offset.x(), offset.y(), offset.z());
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
     * Set only the translation components of this matrix <tt>(m30, m31, m32)</tt> to the given values <tt>(xyz.x, xyz.y, xyz.z)</tt>.
     * <p>
     * To build a translation matrix instead, use {@link #translation(Vector3dr)}.
     * To apply a translation to another matrix, use {@link #translate(Vector3dr)}.
     * 
     * @see #translation(Vector3dr)
     * @see #translate(Vector3dr)
     * 
     * @param xyz
     *          the units to translate in <tt>(x, y, z)</tt>
     * @return this
     */
    public Matrix4d setTranslation(Vector3dr xyz) {
        m30 = xyz.x();
        m31 = xyz.y();
        m32 = xyz.z();
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
     * In order to post-multiply a scaling transformation directly to a
     * matrix, use {@link #scale(double) scale()} instead.
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
     * Set this matrix to be a simple scale matrix which scales the base axes by
     * <tt>xyz.x</tt>, <tt>xyz.y</tt> and <tt>xyz.z</tt>, respectively.
     * <p>
     * The resulting matrix can be multiplied against another transformation
     * matrix to obtain an additional scaling.
     * <p>
     * In order to post-multiply a scaling transformation directly to a
     * matrix use {@link #scale(Vector3dr) scale()} instead.
     * 
     * @see #scale(Vector3dr)
     * 
     * @param xyz
     *             the scale in x, y and z, respectively
     * @return this
     */
    public Matrix4d scaling(Vector3dr xyz) {
        return scaling(xyz.x(), xyz.y(), xyz.z());
    }

    /**
     * Set this matrix to a rotation matrix which rotates the given radians about a given axis.
     * <p>
     * From <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Rotation_matrix_from_axis_and_angle">Wikipedia</a>
     * 
     * @param angle
     *          the angle in radians
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
        double C = 1.0 - cos;
        double xy = x * y, xz = x * z, yz = y * z;
        m00 = cos + x * x * C;
        m10 = xy * C - z * sin;
        m20 = xz * C + y * sin;
        m30 = 0.0;
        m01 = xy * C + z * sin;
        m11 = cos + y * y * C;
        m21 = yz * C - x * sin;
        m31 = 0.0;
        m02 = xz * C - y * sin;
        m12 = yz * C + x * sin;
        m22 = cos + z * z * C;
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
     *            the angle in radians
     * @return this
     */
    public Matrix4d rotationX(double ang) {
        double cos = Math.cos(ang);
        double sin = Math.sin(ang);
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
     *            the angle in radians
     * @return this
     */
    public Matrix4d rotationY(double ang) {
        double cos = Math.cos(ang);
        double sin = Math.sin(ang);
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
     *            the angle in radians
     * @return this
     */
    public Matrix4d rotationZ(double ang) {
        double cos = Math.cos(ang);
        double sin = Math.sin(ang);
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
        m22 = 1.0;
        m23 = 0.0;
        m30 = 0.0;
        m31 = 0.0;
        m32 = 0.0;
        m33 = 1.0;
        return this;
    }

    /**
     * Set this matrix to a rotation of <code>angleX</code> radians about the X axis, followed by a rotation
     * of <code>angleY</code> radians about the Y axis and followed by a rotation of <code>angleZ</code> radians about the Z axis.
     * <p>
     * This method is equivalent to calling: <tt>rotationX(angleX).rotateY(angleY).rotateZ(angleZ)</tt>
     * 
     * @param angleX
     *            the angle to rotate about X
     * @param angleY
     *            the angle to rotate about Y
     * @param angleZ
     *            the angle to rotate about Z
     * @return dest
     */
    public Matrix4d rotationXYZ(double angleX, double angleY, double angleZ) {
        return rotationXYZ(angleX, angleY, angleZ, this);
    }

    /**
     * Set this matrix to a rotation of <code>angleZ</code> radians about the Z axis, followed by a rotation
     * of <code>angleY</code> radians about the Y axis and followed by a rotation of <code>angleX</code> radians about the X axis.
     * <p>
     * This method is equivalent to calling: <tt>rotationZ(angleZ).rotateY(angleY).rotateX(angleX)</tt>
     * 
     * @param angleZ
     *            the angle to rotate about Z
     * @param angleY
     *            the angle to rotate about Y
     * @param angleX
     *            the angle to rotate about X
     * @return dest
     */
    public Matrix4d rotationZYX(double angleZ, double angleY, double angleX) {
        return rotationZYX(angleZ, angleY, angleX, this);
    }

    /**
     * Set this matrix to a rotation of <code>angleY</code> radians about the Y axis, followed by a rotation
     * of <code>angleX</code> radians about the X axis and followed by a rotation of <code>angleZ</code> radians about the Z axis.
     * <p>
     * This method is equivalent to calling: <tt>rotationY(angleY).rotateX(angleX).rotateZ(angleZ)</tt>
     * 
     * @param angleY
     *            the angle to rotate about Y
     * @param angleX
     *            the angle to rotate about X
     * @param angleZ
     *            the angle to rotate about Z
     * @return dest
     */
    public Matrix4d rotationYXZ(double angleY, double angleX, double angleZ) {
        return rotationYXZ(angleY, angleX, angleZ, this);
    }

    /**
     * Set this matrix to a rotation matrix which rotates the given radians about a given axis.
     * 
     * @param angle
     *          the angle in radians
     * @param axis
     *          the axis to rotate about
     * @return this
     */
    public Matrix4d rotation(double angle, Vector3dr axis) {
        return rotation(angle, axis.x(), axis.y(), axis.z());
    }

    /**
     * Set this matrix to a rotation matrix which rotates the given radians about a given axis.
     * 
     * @param angle
     *          the angle in radians
     * @param axis
     *          the axis to rotate about
     * @return this
     */
    public Matrix4d rotation(double angle, Vector3fr axis) {
        return rotation(angle, axis.x(), axis.y(), axis.z());
    }

    /**
     * Set the upper 3x3 matrix of this {@link Matrix4d} to the given {@link Matrix3dr} and the rest to the identity.
     * 
     * @param mat
     *          the 3x3 matrix
     * @return this
     */
    public Matrix4d set3x3(Matrix3dr mat) {
        m00 = mat.m00();
        m01 = mat.m01();
        m02 = mat.m02();
        m03 = 0.0;
        m10 = mat.m10();
        m11 = mat.m11();
        m12 = mat.m12();
        m13 = 0.0;
        m20 = mat.m20();
        m21 = mat.m21();
        m22 = mat.m22();
        m23 = 0.0;
        m30 = 0.0;
        m31 = 0.0;
        m32 = 0.0;
        m33 = 1.0;
        return this;
    }

    /**
     * Apply scaling to this matrix by scaling the base axes by the given <tt>xyz.x</tt>,
     * <tt>xyz.y</tt> and <tt>xyz.z</tt> factors, respectively.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>S</code> the scaling matrix,
     * then the new matrix will be <code>M * S</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * S * v</code>, the
     * scaling will be applied first!
     * 
     * @param xyz
     *            the factors of the x, y and z component, respectively
     * @return this
     */
    public Matrix4d scale(Vector3dr xyz) {
        return scale(xyz.x(), xyz.y(), xyz.z(), this);
    }

    /**
     * Apply scaling to the this matrix by scaling the base axes by the given x,
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
     * Apply scaling to this matrix by uniformly scaling all base axes by the given xyz factor.
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
     * Apply rotation to this matrix by rotating the given amount of radians
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
     *            the angle is in radians
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
     * it, use {@link #translation(Vector3fr)}.
     * 
     * @see #translation(Vector3dr)
     * 
     * @param point
     *          the point by which to translate
     * @return this
     */
    public Matrix4d translate(Vector3dr point) {
        return translate(point.x(), point.y(), point.z());
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
     * Apply rotation about the X axis to this matrix by rotating the given amount of radians.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the rotation matrix,
     * then the new matrix will be <code>M * R</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * R * v</code>, the
     * rotation will be applied first!
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Basic_rotations">http://en.wikipedia.org</a>
     * 
     * @param ang
     *            the angle in radians
     * @return this
     */
    public Matrix4d rotateX(double ang) {
        return rotateX(ang, this);
    }

    /**
     * Apply rotation about the Y axis to this matrix by rotating the given amount of radians.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the rotation matrix,
     * then the new matrix will be <code>M * R</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * R * v</code>, the
     * rotation will be applied first!
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Basic_rotations">http://en.wikipedia.org</a>
     * 
     * @param ang
     *            the angle in radians
     * @return this
     */
    public Matrix4d rotateY(double ang) {
        return rotateY(ang, this);
    }

    /**
     * Apply rotation about the Z axis to this matrix by rotating the given amount of radians.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the rotation matrix,
     * then the new matrix will be <code>M * R</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * R * v</code>, the
     * rotation will be applied first!
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Basic_rotations">http://en.wikipedia.org</a>
     * 
     * @param ang
     *            the angle in radians
     * @return this
     */
    public Matrix4d rotateZ(double ang) {
        return rotateZ(ang, this);
    }

    /**
     * Apply rotation of <code>angleX</code> radians about the X axis, followed by a rotation of <code>angleY</code> radians about the Y axis and
     * followed by a rotation of <code>angleZ</code> radians about the Z axis.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the rotation matrix,
     * then the new matrix will be <code>M * R</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * R * v</code>, the
     * rotation will be applied first!
     * <p>
     * This method is equivalent to calling: <tt>rotateX(angleX).rotateY(angleY).rotateZ(angleZ)</tt>
     * 
     * @param angleX
     *            the angle to rotate about X
     * @param angleY
     *            the angle to rotate about Y
     * @param angleZ
     *            the angle to rotate about Z
     * @return this
     */
    public Matrix4d rotateXYZ(double angleX, double angleY, double angleZ) {
        return rotateXYZ(angleX, angleY, angleZ, this);
    }

    /**
     * Apply rotation of <code>angleX</code> radians about the X axis, followed by a rotation of <code>angleY</code> radians about the Y axis and
     * followed by a rotation of <code>angleZ</code> radians about the Z axis.
     * <p>
     * This method assumes that the last row of <code>this</code> is <tt>(0, 0, 0, 1)</tt> and can be used to speed up matrix multiplication if
     * the matrix only represents affine transformations, such as translation, rotation, scaling and shearing (in any combination).
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the rotation matrix,
     * then the new matrix will be <code>M * R</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * R * v</code>, the
     * rotation will be applied first!
     * <p>
     * This method is equivalent to calling: <tt>rotateX(angleX).rotateY(angleY).rotateZ(angleZ)</tt>
     * 
     * @param angleX
     *            the angle to rotate about X
     * @param angleY
     *            the angle to rotate about Y
     * @param angleZ
     *            the angle to rotate about Z
     * @return this
     */
    public Matrix4d rotateXYZ4x3(double angleX, double angleY, double angleZ) {
        return rotateXYZ4x3(angleX, angleY, angleZ, this);
    }

    /**
     * Apply rotation of <code>angleZ</code> radians about the Z axis, followed by a rotation of <code>angleY</code> radians about the Y axis and
     * followed by a rotation of <code>angleX</code> radians about the X axis.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the rotation matrix,
     * then the new matrix will be <code>M * R</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * R * v</code>, the
     * rotation will be applied first!
     * <p>
     * This method is equivalent to calling: <tt>rotateZ(angleZ).rotateY(angleY).rotateX(angleX)</tt>
     * 
     * @param angleZ
     *            the angle to rotate about Z
     * @param angleY
     *            the angle to rotate about Y
     * @param angleX
     *            the angle to rotate about X
     * @return this
     */
    public Matrix4d rotateZYX(double angleZ, double angleY, double angleX) {
        return rotateZYX(angleZ, angleY, angleX, this);
    }

    /**
     * Apply rotation of <code>angleZ</code> radians about the Z axis, followed by a rotation of <code>angleY</code> radians about the Y axis and
     * followed by a rotation of <code>angleX</code> radians about the X axis.
     * <p>
     * This method assumes that the last row of <code>this</code> is <tt>(0, 0, 0, 1)</tt> and can be used to speed up matrix multiplication if
     * the matrix only represents affine transformations, such as translation, rotation, scaling and shearing (in any combination).
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the rotation matrix,
     * then the new matrix will be <code>M * R</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * R * v</code>, the
     * rotation will be applied first!
     * 
     * @param angleZ
     *            the angle to rotate about Z
     * @param angleY
     *            the angle to rotate about Y
     * @param angleX
     *            the angle to rotate about X
     * @return this
     */
    public Matrix4d rotateZYX4x3(double angleZ, double angleY, double angleX) {
        return rotateZYX4x3(angleZ, angleY, angleX, this);
    }

    /**
     * Apply rotation of <code>angleY</code> radians about the Y axis, followed by a rotation of <code>angleX</code> radians about the X axis and
     * followed by a rotation of <code>angleZ</code> radians about the Z axis.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the rotation matrix,
     * then the new matrix will be <code>M * R</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * R * v</code>, the
     * rotation will be applied first!
     * <p>
     * This method is equivalent to calling: <tt>rotateY(angleY).rotateX(angleX).rotateZ(angleZ)</tt>
     * 
     * @param angleY
     *            the angle to rotate about Y
     * @param angleX
     *            the angle to rotate about X
     * @param angleZ
     *            the angle to rotate about Z
     * @return this
     */
    public Matrix4d rotateYXZ(double angleY, double angleX, double angleZ) {
        return rotateYXZ(angleY, angleX, angleZ, this);
    }

    /**
     * Apply rotation of <code>angleY</code> radians about the Y axis, followed by a rotation of <code>angleX</code> radians about the X axis and
     * followed by a rotation of <code>angleZ</code> radians about the Z axis.
     * <p>
     * This method assumes that the last row of <code>this</code> is <tt>(0, 0, 0, 1)</tt> and can be used to speed up matrix multiplication if
     * the matrix only represents affine transformations, such as translation, rotation, scaling and shearing (in any combination).
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the rotation matrix,
     * then the new matrix will be <code>M * R</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * R * v</code>, the
     * rotation will be applied first!
     * 
     * @param angleY
     *            the angle to rotate about Y
     * @param angleX
     *            the angle to rotate about X
     * @param angleZ
     *            the angle to rotate about Z
     * @return this
     */
    public Matrix4d rotateYXZ4x3(double angleY, double angleX, double angleZ) {
        return rotateYXZ4x3(angleY, angleX, angleZ, this);
    }

    /**
     * Set this matrix to a rotation transformation using the given {@link AxisAngle4fr}.
     * <p>
     * The resulting matrix can be multiplied against another transformation
     * matrix to obtain an additional rotation.
     * <p>
     * In order to apply the rotation transformation to an existing transformation,
     * use {@link #rotate(AxisAngle4fr) rotate()} instead.
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Axis_and_angle">http://en.wikipedia.org</a>
     *
     * @see #rotate(AxisAngle4fr)
     * 
     * @param angleAxis
     *          the {@link AxisAngle4fr} (needs to be {@link AxisAngle4f#normalize() normalized})
     * @return this
     */
    public Matrix4d rotation(AxisAngle4fr angleAxis) {
        return rotation(angleAxis.angle(), angleAxis.x(), angleAxis.y(), angleAxis.z());
    }

    /**
     * Set this matrix to a rotation transformation using the given {@link AxisAngle4dr}.
     * <p>
     * The resulting matrix can be multiplied against another transformation
     * matrix to obtain an additional rotation.
     * <p>
     * In order to apply the rotation transformation to an existing transformation,
     * use {@link #rotate(AxisAngle4dr) rotate()} instead.
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Axis_and_angle">http://en.wikipedia.org</a>
     *
     * @see #rotate(AxisAngle4dr)
     * 
     * @param angleAxis
     *          the {@link AxisAngle4dr} (needs to be {@link AxisAngle4d#normalize() normalized})
     * @return this
     */
    public Matrix4d rotation(AxisAngle4dr angleAxis) {
        return rotation(angleAxis.angle(), angleAxis.x(), angleAxis.y(), angleAxis.z());
    }

    /**
     * Set this matrix to the rotation transformation of the given {@link Quaterniond}.
     * <p>
     * The resulting matrix can be multiplied against another transformation
     * matrix to obtain an additional rotation.
     * <p>
     * In order to apply the rotation transformation to an existing transformation,
     * use {@link #rotate(Quaterniondr) rotate()} instead.
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Quaternion">http://en.wikipedia.org</a>
     * 
     * @see #rotate(Quaterniondr)
     * 
     * @param quat
     *          the {@link Quaterniond}
     * @return this
     */
    public Matrix4d rotation(Quaterniondr quat) {
        double dqx = 2.0 * quat.x();
        double dqy = 2.0 * quat.y();
        double dqz = 2.0 * quat.z();
        double q00 = dqx * quat.x();
        double q11 = dqy * quat.y();
        double q22 = dqz * quat.z();
        double q01 = dqx * quat.y();
        double q02 = dqx * quat.z();
        double q03 = dqx * quat.w();
        double q12 = dqy * quat.z();
        double q13 = dqy * quat.w();
        double q23 = dqz * quat.w();

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
     * Set this matrix to the rotation transformation of the given {@link Quaternionfr}.
     * <p>
     * The resulting matrix can be multiplied against another transformation
     * matrix to obtain an additional rotation.
     * <p>
     * In order to apply the rotation transformation to an existing transformation,
     * use {@link #rotate(Quaternionfr) rotate()} instead.
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Quaternion">http://en.wikipedia.org</a>
     * 
     * @see #rotate(Quaternionfr)
     * 
     * @param quat
     *          the {@link Quaternionf}
     * @return this
     */
    public Matrix4d rotation(Quaternionfr quat) {
        double dqx = 2.0 * quat.x();
        double dqy = 2.0 * quat.y();
        double dqz = 2.0 * quat.z();
        double q00 = dqx * quat.x();
        double q11 = dqy * quat.y();
        double q22 = dqz * quat.z();
        double q01 = dqx * quat.y();
        double q02 = dqx * quat.z();
        double q03 = dqx * quat.w();
        double q12 = dqy * quat.z();
        double q13 = dqy * quat.w();
        double q23 = dqz * quat.w();

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
     * Set <code>this</code> matrix to <tt>T * R * S</tt>, where <tt>T</tt> is a translation by the given <tt>(tx, ty, tz)</tt>,
     * <tt>R</tt> is a rotation transformation specified by the quaternion <tt>(qx, qy, qz, qw)</tt>, and <tt>S</tt> is a scaling transformation
     * which scales the three axes x, y and z by <tt>(sx, sy, sz)</tt>.
     * <p>
     * When transforming a vector by the resulting matrix the scaling transformation will be applied first, then the rotation and
     * at last the translation.
     * <p>
     * This method is equivalent to calling: <tt>translation(tx, ty, tz).rotate(quat).scale(sx, sy, sz)</tt>
     * 
     * @see #translation(double, double, double)
     * @see #rotate(Quaterniondr)
     * @see #scale(double, double, double)
     * 
     * @param tx
     *          the number of units by which to translate the x-component
     * @param ty
     *          the number of units by which to translate the y-component
     * @param tz
     *          the number of units by which to translate the z-component
     * @param qx
     *          the x-coordinate of the vector part of the quaternion
     * @param qy
     *          the y-coordinate of the vector part of the quaternion
     * @param qz
     *          the z-coordinate of the vector part of the quaternion
     * @param qw
     *          the scalar part of the quaternion
     * @param sx
     *          the scaling factor for the x-axis
     * @param sy
     *          the scaling factor for the y-axis
     * @param sz
     *          the scaling factor for the z-axis
     * @return this
     */
    public Matrix4d translationRotateScale(double tx, double ty, double tz, 
                                           double qx, double qy, double qz, double qw, 
                                           double sx, double sy, double sz) {
        double dqx = 2.0 * qx, dqy = 2.0 * qy, dqz = 2.0 * qz;
        double q00 = dqx * qx;
        double q11 = dqy * qy;
        double q22 = dqz * qz;
        double q01 = dqx * qy;
        double q02 = dqx * qz;
        double q03 = dqx * qw;
        double q12 = dqy * qz;
        double q13 = dqy * qw;
        double q23 = dqz * qw;
        m00 = sx - (q11 + q22) * sx;
        m01 = (q01 + q23) * sx;
        m02 = (q02 - q13) * sx;
        m03 = 0.0;
        m10 = (q01 - q23) * sy;
        m11 = sy - (q22 + q00) * sy;
        m12 = (q12 + q03) * sy;
        m13 = 0.0;
        m20 = (q02 + q13) * sz;
        m21 = (q12 - q03) * sz;
        m22 = sz - (q11 + q00) * sz;
        m23 = 0.0;
        m30 = tx;
        m31 = ty;
        m32 = tz;
        m33 = 1.0;
        return this;
    }

    /**
     * Set <code>this</code> matrix to <tt>T * R * S</tt>, where <tt>T</tt> is the given <code>translation</code>,
     * <tt>R</tt> is a rotation transformation specified by the given quaternion, and <tt>S</tt> is a scaling transformation
     * which scales the axes by <code>scale</code>.
     * <p>
     * When transforming a vector by the resulting matrix the scaling transformation will be applied first, then the rotation and
     * at last the translation.
     * <p>
     * This method is equivalent to calling: <tt>translation(translation).rotate(quat).scale(scale)</tt>
     * 
     * @see #translation(Vector3fr)
     * @see #rotate(Quaternionfr)
     * 
     * @param translation
     *          the translation
     * @param quat
     *          the quaternion representing a rotation
     * @param scale
     *          the scaling factors
     * @return this
     */
    public Matrix4d translationRotateScale(Vector3fr translation,
                                           Quaternionfr quat,
                                           Vector3fr scale) {
        return translationRotateScale(translation.x(), translation.y(), translation.z(),
                quat.x(), quat.y(), quat.z(), quat.w(), scale.x(), scale.y(), scale.z());
    }

    /**
     * Set <code>this</code> matrix to <tt>T * R * S</tt>, where <tt>T</tt> is the given <code>translation</code>,
     * <tt>R</tt> is a rotation transformation specified by the given quaternion, and <tt>S</tt> is a scaling transformation
     * which scales the axes by <code>scale</code>.
     * <p>
     * When transforming a vector by the resulting matrix the scaling transformation will be applied first, then the rotation and
     * at last the translation.
     * <p>
     * This method is equivalent to calling: <tt>translation(translation).rotate(quat).scale(scale)</tt>
     * 
     * @see #translation(Vector3dr)
     * @see #rotate(Quaterniondr)
     * 
     * @param translation
     *          the translation
     * @param quat
     *          the quaternion representing a rotation
     * @param scale
     *          the scaling factors
     * @return this
     */
    public Matrix4d translationRotateScale(Vector3dr translation,
                                           Quaterniondr quat,
                                           Vector3dr scale) {
        return translationRotateScale(translation.x(), translation.y(), translation.z(),
                quat.x(), quat.y(), quat.z(), quat.w(), scale.x(), scale.y(), scale.z());
    }

    /**
     * Set <code>this</code> matrix to <tt>T * R</tt>, where <tt>T</tt> is a translation by the given <tt>(tx, ty, tz)</tt> and
     * <tt>R</tt> is a rotation transformation specified by the given quaternion.
     * <p>
     * When transforming a vector by the resulting matrix the rotation transformation will be applied first and then the translation.
     * <p>
     * This method is equivalent to calling: <tt>translation(tx, ty, tz).rotate(quat)</tt>
     * 
     * @see #translation(double, double, double)
     * @see #rotate(Quaterniondr)
     * 
     * @param tx
     *          the number of units by which to translate the x-component
     * @param ty
     *          the number of units by which to translate the y-component
     * @param tz
     *          the number of units by which to translate the z-component
     * @param quat
     *          the quaternion representing a rotation
     * @return this
     */
    public Matrix4d translationRotate(double tx, double ty, double tz, Quaterniondr quat) {
        double dqx = 2.0 * quat.x(), dqy = 2.0 * quat.y(), dqz = 2.0 * quat.z();
        double q00 = dqx * quat.x();
        double q11 = dqy * quat.y();
        double q22 = dqz * quat.z();
        double q01 = dqx * quat.y();
        double q02 = dqx * quat.z();
        double q03 = dqx * quat.w();
        double q12 = dqy * quat.z();
        double q13 = dqy * quat.w();
        double q23 = dqz * quat.w();
        m00 = 1.0 - (q11 + q22);
        m01 = q01 + q23;
        m02 = q02 - q13;
        m03 = 0.0;
        m10 = q01 - q23;
        m11 = 1.0 - (q22 + q00);
        m12 = q12 + q03;
        m13 = 0.0;
        m20 = q02 + q13;
        m21 = q12 - q03;
        m22 = 1.0 - (q11 + q00);
        m23 = 0.0;
        m30 = tx;
        m31 = ty;
        m32 = tz;
        m33 = 1.0;
        return this;
    }

    /**
     * Apply the rotation transformation of the given {@link Quaterniondr} to this matrix.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>Q</code> the rotation matrix obtained from the given quaternion,
     * then the new matrix will be <code>M * Q</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * Q * v</code>,
     * the quaternion rotation will be applied first!
     * <p>
     * In order to set the matrix to a rotation transformation without post-multiplying,
     * use {@link #rotation(Quaterniondr)}.
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Quaternion">http://en.wikipedia.org</a>
     * 
     * @see #rotation(Quaterniondr)
     * 
     * @param quat
     *          the {@link Quaterniondr}
     * @return this
     */
    public Matrix4d rotate(Quaterniondr quat) {
        return rotate(quat, this);
    }

    /**
     * Apply the rotation transformation of the given {@link Quaternionfr} to this matrix.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>Q</code> the rotation matrix obtained from the given quaternion,
     * then the new matrix will be <code>M * Q</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * Q * v</code>,
     * the quaternion rotation will be applied first!
     * <p>
     * In order to set the matrix to a rotation transformation without post-multiplying,
     * use {@link #rotation(Quaternionfr)}.
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Quaternion">http://en.wikipedia.org</a>
     * 
     * @see #rotation(Quaternionfr)
     * 
     * @param quat
     *          the {@link Quaternionfr}
     * @return this
     */
    public Matrix4d rotate(Quaternionfr quat) {
        return rotate(quat, this);
    }

    /**
     * Apply a rotation transformation, rotating about the given {@link AxisAngle4fr}, to this matrix.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>A</code> the rotation matrix obtained from the given {@link AxisAngle4fr},
     * then the new matrix will be <code>M * A</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * A * v</code>,
     * the {@link AxisAngle4f} rotation will be applied first!
     * <p>
     * In order to set the matrix to a rotation transformation without post-multiplying,
     * use {@link #rotation(AxisAngle4fr)}.
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Axis_and_angle">http://en.wikipedia.org</a>
     * 
     * @see #rotate(double, double, double, double)
     * @see #rotation(AxisAngle4fr)
     * 
     * @param axisAngle
     *          the {@link AxisAngle4fr} (needs to be {@link AxisAngle4f#normalize() normalized})
     * @return this
     */
    public Matrix4d rotate(AxisAngle4fr axisAngle) {
        return rotate(axisAngle.angle(), axisAngle.x(), axisAngle.y(), axisAngle.z());
    }

    /**
     * Apply a rotation transformation, rotating about the given {@link AxisAngle4dr}, to this matrix.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>A</code> the rotation matrix obtained from the given {@link AxisAngle4dr},
     * then the new matrix will be <code>M * A</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * A * v</code>,
     * the {@link AxisAngle4d} rotation will be applied first!
     * <p>
     * In order to set the matrix to a rotation transformation without post-multiplying,
     * use {@link #rotation(AxisAngle4dr)}.
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Axis_and_angle">http://en.wikipedia.org</a>
     * 
     * @see #rotate(double, double, double, double)
     * @see #rotation(AxisAngle4dr)
     * 
     * @param axisAngle
     *          the {@link AxisAngle4dr} (needs to be {@link AxisAngle4d#normalize() normalized})
     * @return this
     */
    public Matrix4d rotate(AxisAngle4dr axisAngle) {
        return rotate(axisAngle.angle(), axisAngle.x(), axisAngle.y(), axisAngle.z());
    }

    /**
     * Apply a rotation transformation, rotating the given radians about the specified axis, to this matrix.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>A</code> the rotation matrix obtained from the given angle and axis,
     * then the new matrix will be <code>M * A</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * A * v</code>,
     * the axis-angle rotation will be applied first!
     * <p>
     * In order to set the matrix to a rotation transformation without post-multiplying,
     * use {@link #rotation(double, Vector3dr)}.
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Axis_and_angle">http://en.wikipedia.org</a>
     * 
     * @see #rotate(double, double, double, double)
     * @see #rotation(double, Vector3dr)
     * 
     * @param angle
     *          the angle in radians
     * @param axis
     *          the rotation axis (needs to be {@link Vector3d#normalize() normalized})
     * @return this
     */
    public Matrix4d rotate(double angle, Vector3dr axis) {
        return rotate(angle, axis.x(), axis.y(), axis.z());
    }

    /**
     * Apply a rotation transformation, rotating the given radians about the specified axis, to this matrix.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>A</code> the rotation matrix obtained from the given angle and axis,
     * then the new matrix will be <code>M * A</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * A * v</code>,
     * the axis-angle rotation will be applied first!
     * <p>
     * In order to set the matrix to a rotation transformation without post-multiplying,
     * use {@link #rotation(double, Vector3fr)}.
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Axis_and_angle">http://en.wikipedia.org</a>
     * 
     * @see #rotate(double, double, double, double)
     * @see #rotation(double, Vector3fr)
     * 
     * @param angle
     *          the angle in radians
     * @param axis
     *          the rotation axis (needs to be {@link Vector3f#normalize() normalized})
     * @return this
     */
    public Matrix4d rotate(double angle, Vector3fr axis) {
        return rotate(angle, axis.x(), axis.y(), axis.z());
    }

    /**
     * Normalize the upper left 3x3 submatrix of this matrix.
     * <p>
     * The resulting matrix will map unit vectors to unit vectors, though a pair of orthogonal input unit
     * vectors need not be mapped to a pair of orthogonal output vectors if the original matrix was not orthogonal itself
     * (i.e. had <i>skewing</i>).
     * 
     * @return this
     */
    public Matrix4d normalize3x3() {
        return normalize3x3(this);
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
    public Matrix4d reflect(Vector3dr normal, Vector3dr point) {
        return reflect(normal.x(), normal.y(), normal.z(), point.x(), point.y(), point.z());
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
    public Matrix4d reflect(Quaterniondr orientation, Vector3dr point) {
        return reflect(orientation, point, this);
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
        double da = 2.0 * a, db = 2.0 * b, dc = 2.0 * c, dd = 2.0 * d;
        m00 = 1.0 - da * a;
        m01 = -da * b;
        m02 = -da * c;
        m03 = 0.0;
        m10 = -db * a;
        m11 = 1.0 - db * b;
        m12 = -db * c;
        m13 = 0.0;
        m20 = -dc * a;
        m21 = -dc * b;
        m22 = 1.0 - dc * c;
        m23 = 0.0;
        m30 = -dd * a;
        m31 = -dd * b;
        m32 = -dd * c;
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
        double invLength = 1.0 / Math.sqrt(nx * nx + ny * ny + nz * nz);
        double nnx = nx * invLength;
        double nny = ny * invLength;
        double nnz = nz * invLength;
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
    public Matrix4d reflection(Vector3dr normal, Vector3dr point) {
        return reflection(normal.x(), normal.y(), normal.z(), point.x(), point.y(), point.z());
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
    public Matrix4d reflection(Quaterniondr orientation, Vector3dr point) {
        double num1 = orientation.x() * 2.0;
        double num2 = orientation.y() * 2.0;
        double num3 = orientation.z() * 2.0;
        double normalX = orientation.x() * num3 + orientation.w() * num2;
        double normalY = orientation.y() * num3 - orientation.w() * num1;
        double normalZ = 1.0 - (orientation.x() * num1 + orientation.y() * num2);
        return reflection(normalX, normalY, normalZ, point.x(), point.y(), point.z());
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
     * @see #ortho(double, double, double, double, double, double)
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
     * @see #setOrtho(double, double, double, double, double, double)
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
     * {@link #lookAt(Vector3dr, Vector3dr, Vector3dr) lookAt}
     * with <code>eye = (0, 0, 0)</code> and <code>center = dir</code>.
     * <p>
     * In order to set the matrix to a lookalong transformation without post-multiplying it,
     * use {@link #setLookAlong(Vector3dr, Vector3dr) setLookAlong()}.
     * 
     * @see #lookAlong(double, double, double, double, double, double)
     * @see #lookAt(Vector3dr, Vector3dr, Vector3dr)
     * @see #setLookAlong(Vector3dr, Vector3dr)
     * 
     * @param dir
     *            the direction in space to look along
     * @param up
     *            the direction of 'up'
     * @return this
     */
    public Matrix4d lookAlong(Vector3dr dir, Vector3dr up) {
        return lookAlong(dir.x(), dir.y(), dir.z(), up.x(), up.y(), up.z(), this);
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
     * {@link #setLookAt(Vector3dr, Vector3dr, Vector3dr) setLookAt()}
     * with <code>eye = (0, 0, 0)</code> and <code>center = dir</code>.
     * <p>
     * In order to apply the lookalong transformation to any previous existing transformation,
     * use {@link #lookAlong(Vector3dr, Vector3dr)}.
     * 
     * @see #setLookAlong(Vector3dr, Vector3dr)
     * @see #lookAlong(Vector3dr, Vector3dr)
     * 
     * @param dir
     *            the direction in space to look along
     * @param up
     *            the direction of 'up'
     * @return this
     */
    public Matrix4d setLookAlong(Vector3dr dir, Vector3dr up) {
        return setLookAlong(dir.x(), dir.y(), dir.z(), up.x(), up.y(), up.z());
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
        double invDirLength = 1.0 / Math.sqrt(dirX * dirX + dirY * dirY + dirZ * dirZ);
        double dirnX = dirX * invDirLength;
        double dirnY = dirY * invDirLength;
        double dirnZ = dirZ * invDirLength;
        // right = direction x up
        double rightX, rightY, rightZ;
        rightX = dirnY * upZ - dirnZ * upY;
        rightY = dirnZ * upX - dirnX * upZ;
        rightZ = dirnX * upY - dirnY * upX;
        // normalize right
        double invRightLength = 1.0 / Math.sqrt(rightX * rightX + rightY * rightY + rightZ * rightZ);
        rightX *= invRightLength;
        rightY *= invRightLength;
        rightZ *= invRightLength;
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
     * In order to not make use of vectors to specify <code>eye</code>, <code>center</code> and <code>up</code> but use primitives,
     * like in the GLU function, use {@link #setLookAt(double, double, double, double, double, double, double, double, double) setLookAt()}
     * instead.
     * <p>
     * In order to apply the lookat transformation to a previous existing transformation,
     * use {@link #lookAt(Vector3dr, Vector3dr, Vector3dr) lookAt()}.
     * 
     * @see #setLookAt(double, double, double, double, double, double, double, double, double)
     * @see #lookAt(Vector3dr, Vector3dr, Vector3dr)
     * 
     * @param eye
     *            the position of the camera
     * @param center
     *            the point in space to look at
     * @param up
     *            the direction of 'up'
     * @return this
     */
    public Matrix4d setLookAt(Vector3dr eye, Vector3dr center, Vector3dr up) {
        return setLookAt(eye.x(), eye.y(), eye.z(), center.x(), center.y(), center.z(), up.x(), up.y(), up.z());
    }

    /**
     * Set this matrix to be a "lookat" transformation for a right-handed coordinate system, 
     * that aligns <code>-z</code> with <code>center - eye</code>.
     * <p>
     * In order to apply the lookat transformation to a previous existing transformation,
     * use {@link #lookAt(double, double, double, double, double, double, double, double, double) lookAt}.
     * 
     * @see #setLookAt(Vector3dr, Vector3dr, Vector3dr)
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
        double invDirLength = 1.0 / Math.sqrt(
                  (eyeX - centerX) * (eyeX - centerX)
                + (eyeY - centerY) * (eyeY - centerY)
                + (eyeZ - centerZ) * (eyeZ - centerZ));
        dirX *= invDirLength;
        dirY *= invDirLength;
        dirZ *= invDirLength;
        // right = direction x up
        double rightX, rightY, rightZ;
        rightX = dirY * upZ - dirZ * upY;
        rightY = dirZ * upX - dirX * upZ;
        rightZ = dirX * upY - dirY * upX;
        // normalize right
        double invRightLength = 1.0 / Math.sqrt(rightX * rightX + rightY * rightY + rightZ * rightZ);
        rightX *= invRightLength;
        rightY *= invRightLength;
        rightZ *= invRightLength;
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
     * that aligns <code>-z</code> with <code>center - eye</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>L</code> the lookat matrix,
     * then the new matrix will be <code>M * L</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * L * v</code>,
     * the lookat transformation will be applied first!
     * <p>
     * In order to set the matrix to a lookat transformation without post-multiplying it,
     * use {@link #setLookAt(Vector3dr, Vector3dr, Vector3dr)}.
     * 
     * @see #lookAt(double, double, double, double, double, double, double, double, double)
     * @see #setLookAlong(Vector3dr, Vector3dr)
     * 
     * @param eye
     *            the position of the camera
     * @param center
     *            the point in space to look at
     * @param up
     *            the direction of 'up'
     * @return this
     */
    public Matrix4d lookAt(Vector3dr eye, Vector3dr center, Vector3dr up) {
        return lookAt(eye.x(), eye.y(), eye.z(), center.x(), center.y(), center.z(), up.x(), up.y(), up.z(), this);
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
     * @see #lookAt(Vector3dr, Vector3dr, Vector3dr)
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
     *            the vertical field of view in radians
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
     *            the vertical field of view in radians
     * @param aspect
     *            the aspect ratio (i.e. width / height)
     * @param zNear
     *            near clipping plane distance
     * @param zFar
     *            far clipping plane distance
     * @return this
     */
    public Matrix4d setPerspective(double fovy, double aspect, double zNear, double zFar) {
        double h = Math.tan(fovy * 0.5) * zNear;
        double w = h * aspect;
        m00 = zNear / w;
        m01 = 0.0;
        m02 = 0.0;
        m03 = 0.0;
        m10 = 0.0;
        m11 = zNear / h;
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
     * Apply a projection transformation to this matrix that projects onto the plane specified via the general plane equation
     * <tt>x*a + y*b + z*c + d = 0</tt> as if casting a shadow from a given light position/direction <code>light</code>.
     * <p>
     * If <tt>light.w</tt> is <tt>0.0</tt> the light is being treated as a directional light; if it is <tt>1.0</tt> it is a point light.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>S</code> the shadow matrix,
     * then the new matrix will be <code>M * S</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * S * v</code>, the
     * reflection will be applied first!
     * <p>
     * Reference: <a href="ftp://ftp.sgi.com/opengl/contrib/blythe/advanced99/notes/node192.html">ftp.sgi.com</a>
     * 
     * @param light
     *          the light's vector
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
    public Matrix4d shadow(Vector4d light, double a, double b, double c, double d) {
        return shadow(light.x, light.y, light.z, light.w, a, b, c, d, this);
    }

    /**
     * Apply a projection transformation to this matrix that projects onto the plane specified via the general plane equation
     * <tt>x*a + y*b + z*c + d = 0</tt> as if casting a shadow from a given light position/direction <tt>(lightX, lightY, lightZ, lightW)</tt>.
     * <p>
     * If <code>lightW</code> is <tt>0.0</tt> the light is being treated as a directional light; if it is <tt>1.0</tt> it is a point light.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>S</code> the shadow matrix,
     * then the new matrix will be <code>M * S</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * S * v</code>, the
     * reflection will be applied first!
     * <p>
     * Reference: <a href="ftp://ftp.sgi.com/opengl/contrib/blythe/advanced99/notes/node192.html">ftp.sgi.com</a>
     * 
     * @param lightX
     *          the x-component of the light's vector
     * @param lightY
     *          the y-component of the light's vector
     * @param lightZ
     *          the z-component of the light's vector
     * @param lightW
     *          the w-component of the light's vector
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
    public Matrix4d shadow(double lightX, double lightY, double lightZ, double lightW, double a, double b, double c, double d) {
        return shadow(lightX, lightY, lightZ, lightW, a, b, c, d, this);
    }

    /**
     * Apply a projection transformation to this matrix that projects onto the plane with the general plane equation
     * <tt>y = 0</tt> as if casting a shadow from a given light position/direction <code>light</code>.
     * <p>
     * Before the shadow projection is applied, the plane is transformed via the specified <code>planeTransformation</code>.
     * <p>
     * If <tt>light.w</tt> is <tt>0.0</tt> the light is being treated as a directional light; if it is <tt>1.0</tt> it is a point light.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>S</code> the shadow matrix,
     * then the new matrix will be <code>M * S</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * S * v</code>, the
     * reflection will be applied first!
     * 
     * @param light
     *          the light's vector
     * @param planeTransform
     *          the transformation to transform the implied plane <tt>y = 0</tt> before applying the projection
     * @return this
     */
    public Matrix4d shadow(Vector4dr light, Matrix4dr planeTransform) {
        return shadow(light, planeTransform, this);
    }

    /**
     * Apply a projection transformation to this matrix that projects onto the plane with the general plane equation
     * <tt>y = 0</tt> as if casting a shadow from a given light position/direction <tt>(lightX, lightY, lightZ, lightW)</tt>.
     * <p>
     * Before the shadow projection is applied, the plane is transformed via the specified <code>planeTransformation</code>.
     * <p>
     * If <code>lightW</code> is <tt>0.0</tt> the light is being treated as a directional light; if it is <tt>1.0</tt> it is a point light.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>S</code> the shadow matrix,
     * then the new matrix will be <code>M * S</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * S * v</code>, the
     * reflection will be applied first!
     * 
     * @param lightX
     *          the x-component of the light vector
     * @param lightY
     *          the y-component of the light vector
     * @param lightZ
     *          the z-component of the light vector
     * @param lightW
     *          the w-component of the light vector
     * @param planeTransform
     *          the transformation to transform the implied plane <tt>y = 0</tt> before applying the projection
     * @return this
     */
    public Matrix4d shadow(double lightX, double lightY, double lightZ, double lightW, Matrix4dr planeTransform) {
        return shadow(lightX, lightY, lightZ, lightW, planeTransform, this);
    }

}
