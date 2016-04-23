/*
 * (C) Copyright 2015-2016 Richard Greenlees

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
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Contains the definition of a 3x3 Matrix of doubles, and associated functions to transform
 * it. The matrix is column-major to match OpenGL's interpretation, and it looks like this:
 * <p>
 *      m00  m10  m20<br>
 *      m01  m11  m21<br>
 *      m02  m12  m22<br>
 * 
 * @author Richard Greenlees
 * @author Kai Burjack
 */
public class Matrix3d implements Externalizable {

    private static final long serialVersionUID = 1L;

    public double m00, m10, m20;
    public double m01, m11, m21;
    public double m02, m12, m22;

    /**
     * Create a new {@link Matrix3d} and initialize it to {@link #identity() identity}.
     */
    public Matrix3d() {
        m00 = 1.0;
        m01 = 0.0;
        m02 = 0.0;
        m10 = 0.0;
        m11 = 1.0;
        m12 = 0.0;
        m20 = 0.0;
        m21 = 0.0;
        m22 = 1.0;
    }

    /**
     * Create a new {@link Matrix3d} and initialize it with the values from the given matrix.
     * 
     * @param mat
     *          the matrix to initialize this matrix with
     */
    public Matrix3d(Matrix3d mat) {
        m00 = mat.m00;
        m01 = mat.m01;
        m02 = mat.m02;
        m10 = mat.m10;
        m11 = mat.m11;
        m12 = mat.m12;
        m20 = mat.m20;
        m21 = mat.m21;
        m22 = mat.m22;
    }

    /**
     * Create a new {@link Matrix3d} and initialize it with the values from the given matrix.
     * 
     * @param mat
     *          the matrix to initialize this matrix with
     */
    public Matrix3d(Matrix3f mat) {
        m00 = mat.m00;
        m01 = mat.m01;
        m02 = mat.m02;
        m10 = mat.m10;
        m11 = mat.m11;
        m12 = mat.m12;
        m20 = mat.m20;
        m21 = mat.m21;
        m22 = mat.m22;
    }

    /**
     * Create a new {@link Matrix3d} and make it a copy of the upper left 3x3 of the given {@link Matrix4f}.
     *
     * @param mat
     *          the {@link Matrix4f} to copy the values from
     */
    public Matrix3d(Matrix4f mat) {
        m00 = mat.m00;
        m01 = mat.m01;
        m02 = mat.m02;
        m10 = mat.m10;
        m11 = mat.m11;
        m12 = mat.m12;
        m20 = mat.m20;
        m21 = mat.m21;
        m22 = mat.m22;
    }

    /**
     * Create a new {@link Matrix3d} and make it a copy of the upper left 3x3 of the given {@link Matrix4d}.
     *
     * @param mat
     *          the {@link Matrix4d} to copy the values from
     */
    public Matrix3d(Matrix4d mat) {
        m00 = mat.m00;
        m01 = mat.m01;
        m02 = mat.m02;
        m10 = mat.m10;
        m11 = mat.m11;
        m12 = mat.m12;
        m20 = mat.m20;
        m21 = mat.m21;
        m22 = mat.m22;
    }

    /**
     * Create a new {@link Matrix3d} and initialize its elements with the given values.
     * 
     * @param m00
     *          the value of m00
     * @param m01
     *          the value of m01
     * @param m02
     *          the value of m02
     * @param m10
     *          the value of m10
     * @param m11
     *          the value of m11
     * @param m12
     *          the value of m12
     * @param m20
     *          the value of m20
     * @param m21
     *          the value of m21
     * @param m22
     *          the value of m22
     */
    public Matrix3d(double m00, double m01, double m02,
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

    /**
     * Set the values in this matrix to the ones in m.
     * 
     * @param m
     *          the matrix whose values will be copied
     * @return this
     */
    public Matrix3d set(Matrix3d m) {
        m00 = m.m00;
        m01 = m.m01;
        m02 = m.m02;
        m10 = m.m10;
        m11 = m.m11;
        m12 = m.m12;
        m20 = m.m20;
        m21 = m.m21;
        m22 = m.m22;
        return this;
    }

    /**
     * Set the values in this matrix to the ones in m.
     * 
     * @param m
     *          the matrix whose values will be copied
     * @return this
     */
    public Matrix3d set(Matrix3f m) {
        m00 = m.m00;
        m01 = m.m01;
        m02 = m.m02;
        m10 = m.m10;
        m11 = m.m11;
        m12 = m.m12;
        m20 = m.m20;
        m21 = m.m21;
        m22 = m.m22;
        return this;
    }

    /**
     * Set the elements of this matrix to the upper left 3x3 of the given {@link Matrix4f}.
     *
     * @param mat
     *          the {@link Matrix4f} to copy the values from
     * @return this
     */
    public Matrix3d set(Matrix4f mat) {
        m00 = mat.m00;
        m01 = mat.m01;
        m02 = mat.m02;
        m10 = mat.m10;
        m11 = mat.m11;
        m12 = mat.m12;
        m20 = mat.m20;
        m21 = mat.m21;
        m22 = mat.m22;
        return this;
    }

    /**
     * Set the elements of this matrix to the upper left 3x3 of the given {@link Matrix4d}.
     *
     * @param mat
     *          the {@link Matrix4d} to copy the values from
     * @return this
     */
    public Matrix3d set(Matrix4d mat) {
        m00 = mat.m00;
        m01 = mat.m01;
        m02 = mat.m02;
        m10 = mat.m10;
        m11 = mat.m11;
        m12 = mat.m12;
        m20 = mat.m20;
        m21 = mat.m21;
        m22 = mat.m22;
        return this;
    }
    
    /**
     * Set this matrix to be equivalent to the rotation specified by the given {@link AxisAngle4f}.
     * 
     * @param axisAngle
     *          the {@link AxisAngle4f}
     * @return this
     */
    public Matrix3d set(AxisAngle4f axisAngle) {
        double x = axisAngle.x;
        double y = axisAngle.y;
        double z = axisAngle.z;
        double angle = axisAngle.angle;
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
        return this;
    }
    
    /**
     * Set this matrix to be equivalent to the rotation specified by the given {@link AxisAngle4d}.
     * 
     * @param axisAngle
     *          the {@link AxisAngle4d}
     * @return this
     */
    public Matrix3d set(AxisAngle4d axisAngle) {
        double x = axisAngle.x;
        double y = axisAngle.y;
        double z = axisAngle.z;
        double angle = axisAngle.angle;
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
        return this;
    }

    /**
     * Set this matrix to a rotation equivalent to the given quaternion.
     * 
     * @see Quaternionf#get(Matrix3d)
     * 
     * @param q
     *          the quaternion
     * @return this
     */
    public Matrix3d set(Quaternionf q) {
        q.get(this);
        return this;
    }

    /**
     * Set this matrix to a rotation equivalent to the given quaternion.
     * 
     * @see Quaterniond#get(Matrix3d)
     * 
     * @param q
     *          the quaternion
     * @return this
     */
    public Matrix3d set(Quaterniond q) {
        q.get(this);
        return this;
    }

    /**
     * Multiply this matrix by the supplied matrix.
     * This matrix will be the left one.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the <code>right</code> matrix,
     * then the new matrix will be <code>M * R</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * R * v</code>, the
     * transformation of the right matrix will be applied first!
     * 
     * @param right
     *          the right operand
     * @return this
     */
    public Matrix3d mul(Matrix3d right) {
        return mul(right, this);
    }

    /**
     * Multiply this matrix by the supplied matrix and store the result in <code>dest</code>.
     * This matrix will be the left one.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the <code>right</code> matrix,
     * then the new matrix will be <code>M * R</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * R * v</code>, the
     * transformation of the right matrix will be applied first!
     * 
     * @param right
     *          the right operand
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Matrix3d mul(Matrix3d right, Matrix3d dest) {
        dest.set(m00 * right.m00 + m10 * right.m01 + m20 * right.m02,
                 m01 * right.m00 + m11 * right.m01 + m21 * right.m02,
                 m02 * right.m00 + m12 * right.m01 + m22 * right.m02,
                 m00 * right.m10 + m10 * right.m11 + m20 * right.m12,
                 m01 * right.m10 + m11 * right.m11 + m21 * right.m12,
                 m02 * right.m10 + m12 * right.m11 + m22 * right.m12,
                 m00 * right.m20 + m10 * right.m21 + m20 * right.m22,
                 m01 * right.m20 + m11 * right.m21 + m21 * right.m22,
                 m02 * right.m20 + m12 * right.m21 + m22 * right.m22 );
        return dest;
    }

    /**
     * Multiply this matrix by the supplied matrix.
     * This matrix will be the left one.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the <code>right</code> matrix,
     * then the new matrix will be <code>M * R</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * R * v</code>, the
     * transformation of the right matrix will be applied first!
     * 
     * @param right
     *          the right operand
     * @return this
     */
    public Matrix3d mul(Matrix3f right) {
        return mul(right, this);
    }

    /**
     * Multiply this matrix by the supplied matrix and store the result in <code>dest</code>.
     * This matrix will be the left one.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the <code>right</code> matrix,
     * then the new matrix will be <code>M * R</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * R * v</code>, the
     * transformation of the right matrix will be applied first!
     * 
     * @param right
     *          the right operand
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Matrix3d mul(Matrix3f right, Matrix3d dest) {
        dest.set(m00 * right.m00 + m10 * right.m01 + m20 * right.m02,
                 m01 * right.m00 + m11 * right.m01 + m21 * right.m02,
                 m02 * right.m00 + m12 * right.m01 + m22 * right.m02,
                 m00 * right.m10 + m10 * right.m11 + m20 * right.m12,
                 m01 * right.m10 + m11 * right.m11 + m21 * right.m12,
                 m02 * right.m10 + m12 * right.m11 + m22 * right.m12,
                 m00 * right.m20 + m10 * right.m21 + m20 * right.m22,
                 m01 * right.m20 + m11 * right.m21 + m21 * right.m22,
                 m02 * right.m20 + m12 * right.m21 + m22 * right.m22);
        return dest;
    }

    /**
     * Multiply the <code>left</code> matrix by the <code>right</code> and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the <code>right</code> matrix,
     * then the new matrix will be <code>M * R</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * R * v</code>, the
     * transformation of the right matrix will be applied first!
     * 
     * @param left
     *          the left matrix
     * @param right
     *          the right matrix
     * @param dest
     *          will hold the result
     */
    public static void mul(Matrix3f left, Matrix3d right, Matrix3d dest) {
        dest.set(left.m00 * right.m00 + left.m10 * right.m01 + left.m20 * right.m02,
                 left.m01 * right.m00 + left.m11 * right.m01 + left.m21 * right.m02,
                 left.m02 * right.m00 + left.m12 * right.m01 + left.m22 * right.m02,
                 left.m00 * right.m10 + left.m10 * right.m11 + left.m20 * right.m12,
                 left.m01 * right.m10 + left.m11 * right.m11 + left.m21 * right.m12,
                 left.m02 * right.m10 + left.m12 * right.m11 + left.m22 * right.m12,
                 left.m00 * right.m20 + left.m10 * right.m21 + left.m20 * right.m22,
                 left.m01 * right.m20 + left.m11 * right.m21 + left.m21 * right.m22,
                 left.m02 * right.m20 + left.m12 * right.m21 + left.m22 * right.m22 );
    }

    /**
     * Set the values within this matrix to the supplied double values. The result looks like this:
     * <p>
     * m00, m10, m20<br>
     * m01, m11, m21<br>
     * m02, m12, m22<br>
     * 
     * @param m00
     *          the new value of m00
     * @param m01
     *          the new value of m01
     * @param m02
     *          the new value of m02
     * @param m10
     *          the new value of m10
     * @param m11
     *          the new value of m11
     * @param m12
     *          the new value of m12
     * @param m20
     *          the new value of m20
     * @param m21
     *          the new value of m21
     * @param m22
     *          the new value of m22
     * @return this
     */
    public Matrix3d set(double m00, double m01, double m02, 
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
        return this;
    }

    /**
     * Set the values in this matrix based on the supplied double array. The result looks like this:
     * <p>
     * 0, 3, 6<br>
     * 1, 4, 7<br>
     * 2, 5, 8<br>
     * <p>
     * Only uses the first 9 values, all others are ignored.
     * 
     * @param m
     *          the array to read the matrix values from
     * @return this
     */
    public Matrix3d set(double m[]) {
        m00 = m[0];
        m01 = m[1];
        m02 = m[2];
        m10 = m[3];
        m11 = m[4];
        m12 = m[5];
        m20 = m[6];
        m21 = m[7];
        m22 = m[8];
        return this;
    }

    /**
     * Set the values in this matrix based on the supplied double array. The result looks like this:
     * <p>
     * 0, 3, 6<br>
     * 1, 4, 7<br>
     * 2, 5, 8<br>
     * <p>
     * Only uses the first 9 values, all others are ignored
     *
     * @param m
     *          the array to read the matrix values from
     * @return this
     */
    public Matrix3d set(float m[]) {
        m00 = m[0];
        m01 = m[1];
        m02 = m[2];
        m10 = m[3];
        m11 = m[4];
        m12 = m[5];
        m20 = m[6];
        m21 = m[7];
        m22 = m[8];
        return this;
    }

    /**
     * Return the determinant of this matrix.
     * 
     * @return the determinant
     */
    public double determinant() {
        return (m00 * m11 - m01 * m10) * m22
             + (m02 * m10 - m00 * m12) * m21
             + (m01 * m12 - m02 * m11) * m20;
    }

    /**
     * Invert this matrix.
     * 
     * @return this
     */
    public Matrix3d invert() {
        return invert(this);
    }

    /**
     * Invert <code>this</code> matrix and store the result in <code>dest</code>.
     * 
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Matrix3d invert(Matrix3d dest) {
        double s = determinant();
        // client must make sure that matrix is invertible
        s = 1.0 / s;
        dest.set((m11 * m22 - m21 * m12) * s,
                 (m21 * m02 - m01 * m22) * s,
                 (m01 * m12 - m11 * m02) * s,
                 (m20 * m12 - m10 * m22) * s,
                 (m00 * m22 - m20 * m02) * s,
                 (m10 * m02 - m00 * m12) * s,
                 (m10 * m21 - m20 * m11) * s,
                 (m20 * m01 - m00 * m21) * s,
                 (m00 * m11 - m10 * m01) * s);
        return dest;
    }

    /**
     * Transpose this matrix.
     * 
     * @return this
     */
    public Matrix3d transpose() {
        return transpose(this);
    }

    /**
     * Transpose <code>this</code> matrix and store the result in <code>dest</code>.
     * 
     * @param dest
     *             will hold the result
     * @return dest
     */
    public Matrix3d transpose(Matrix3d dest) {
        dest.set(m00, m10, m20,
                 m01, m11, m21,
                 m02, m12, m22);
        return dest;
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
        return formatter.format(m00) + formatter.format(m10) + formatter.format(m20) + "\n" //$NON-NLS-1$
             + formatter.format(m01) + formatter.format(m11) + formatter.format(m21) + "\n" //$NON-NLS-1$
             + formatter.format(m02) + formatter.format(m12) + formatter.format(m22) + "\n"; //$NON-NLS-1$
    }

    /**
     * Get the current values of <code>this</code> matrix and store them into
     * <code>dest</code>.
     * <p>
     * This is the reverse method of {@link #set(Matrix3d)} and allows to obtain
     * intermediate calculation results when chaining multiple transformations.
     * 
     * @see #set(Matrix3d)
     * 
     * @param dest
     *          the destination matrix
     * @return the passed in destination
     */
    public Matrix3d get(Matrix3d dest) {
        return dest.set(this);
    }

    /**
     * Get the current values of <code>this</code> matrix and store the represented rotation
     * into the given {@link AxisAngle4f}.
     * 
     * @see AxisAngle4f#set(Matrix3d)
     * 
     * @param dest
     *          the destination {@link AxisAngle4f}
     * @return the passed in destination
     */
    public AxisAngle4f getRotation(AxisAngle4f dest) {
        return dest.set(this);
    }

    /**
     * Get the current values of <code>this</code> matrix and store the represented rotation
     * into the given {@link Quaternionf}.
     * <p>
     * This method assumes that the three column vectors of this matrix are not normalized and
     * thus allows to ignore any additional scaling factor that is applied to the matrix.
     * 
     * @see Quaternionf#setFromUnnormalized(Matrix3d)
     * 
     * @param dest
     *          the destination {@link Quaternionf}
     * @return the passed in destination
     */
    public Quaternionf getUnnormalizedRotation(Quaternionf dest) {
        return dest.setFromUnnormalized(this);
    }

    /**
     * Get the current values of <code>this</code> matrix and store the represented rotation
     * into the given {@link Quaternionf}.
     * <p>
     * This method assumes that the three column vectors of this matrix are normalized.
     * 
     * @see Quaternionf#setFromNormalized(Matrix3d)
     * 
     * @param dest
     *          the destination {@link Quaternionf}
     * @return the passed in destination
     */
    public Quaternionf getNormalizedRotation(Quaternionf dest) {
        return dest.setFromNormalized(this);
    }

    /**
     * Get the current values of <code>this</code> matrix and store the represented rotation
     * into the given {@link Quaterniond}.
     * <p>
     * This method assumes that the three column vectors of this matrix are not normalized and
     * thus allows to ignore any additional scaling factor that is applied to the matrix.
     * 
     * @see Quaterniond#setFromUnnormalized(Matrix3d)
     * 
     * @param dest
     *          the destination {@link Quaterniond}
     * @return the passed in destination
     */
    public Quaterniond getUnnormalizedRotation(Quaterniond dest) {
        return dest.setFromUnnormalized(this);
    }

    /**
     * Get the current values of <code>this</code> matrix and store the represented rotation
     * into the given {@link Quaterniond}.
     * <p>
     * This method assumes that the three column vectors of this matrix are normalized.
     * 
     * @see Quaterniond#setFromNormalized(Matrix3d)
     * 
     * @param dest
     *          the destination {@link Quaterniond}
     * @return the passed in destination
     */
    public Quaterniond getNormalizedRotation(Quaterniond dest) {
        return dest.setFromNormalized(this);
    }

    /**
     * Store this matrix into the supplied {@link DoubleBuffer} at the current
     * buffer {@link DoubleBuffer#position() position} using column-major order.
     * <p>
     * This method will not increment the position of the given DoubleBuffer.
     * <p>
     * In order to specify the offset into the DoubleBuffer} at which
     * the matrix is stored, use {@link #get(int, DoubleBuffer)}, taking
     * the absolute position as parameter.
     * 
     * @see #get(int, DoubleBuffer)
     * 
     * @param buffer
     *            will receive the values of this matrix in column-major order at its current position
     * @return the passed in buffer
     */
    public DoubleBuffer get(DoubleBuffer buffer) {
        return get(buffer.position(), buffer);
    }

    /**
     * Store this matrix into the supplied {@link DoubleBuffer} starting at the specified
     * absolute buffer position/index using column-major order.
     * <p>
     * This method will not increment the position of the given {@link DoubleBuffer}.
     * 
     * @param index
     *            the absolute position into the {@link DoubleBuffer}
     * @param buffer
     *            will receive the values of this matrix in column-major order
     * @return the passed in buffer
     */
    public DoubleBuffer get(int index, DoubleBuffer buffer) {
        buffer.put(index,   m00);
        buffer.put(index+1, m01);
        buffer.put(index+2, m02);
        buffer.put(index+3, m10);
        buffer.put(index+4, m11);
        buffer.put(index+5, m12);
        buffer.put(index+6, m20);
        buffer.put(index+7, m21);
        buffer.put(index+8, m22);
        return buffer;
    }

    /**
     * Store this matrix in column-major order into the supplied {@link FloatBuffer} at the current
     * buffer {@link FloatBuffer#position() position}.
     * <p>
     * This method will not increment the position of the given FloatBuffer.
     * <p>
     * In order to specify the offset into the FloatBuffer at which
     * the matrix is stored, use {@link #get(int, FloatBuffer)}, taking
     * the absolute position as parameter.
     * <p>
     * Please note that due to this matrix storing double values those values will potentially
     * lose precision when they are converted to float values before being put into the given FloatBuffer.
     * 
     * @see #get(int, FloatBuffer)
     * 
     * @param buffer
     *            will receive the values of this matrix in column-major order at its current position
     * @return the passed in buffer
     */
    public FloatBuffer get(FloatBuffer buffer) {
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
     * @return the passed in buffer
     */
    public FloatBuffer get(int index, FloatBuffer buffer) {
        buffer.put(index,   (float) m00);
        buffer.put(index+1, (float) m01);
        buffer.put(index+2, (float) m02);
        buffer.put(index+3, (float) m10);
        buffer.put(index+4, (float) m11);
        buffer.put(index+5, (float) m12);
        buffer.put(index+6, (float) m20);
        buffer.put(index+7, (float) m21);
        buffer.put(index+8, (float) m22);
        return buffer;
    }

    /**
     * Store this matrix in column-major order into the supplied {@link ByteBuffer} at the current
     * buffer {@link ByteBuffer#position() position}.
     * <p>
     * This method will not increment the position of the given ByteBuffer.
     * <p>
     * In order to specify the offset into the ByteBuffer at which
     * the matrix is stored, use {@link #get(int, ByteBuffer)}, taking
     * the absolute position as parameter.
     * 
     * @see #get(int, ByteBuffer)
     * 
     * @param buffer
     *            will receive the values of this matrix in column-major order at its current position
     * @return the passed in buffer
     */
    public ByteBuffer get(ByteBuffer buffer) {
        return get(buffer.position(), buffer);
    }

    /**
     * Store this matrix in column-major order into the supplied {@link ByteBuffer} starting at the specified
     * absolute buffer position/index.
     * <p>
     * This method will not increment the position of the given ByteBuffer.
     * 
     * @param index
     *            the absolute position into the ByteBuffer
     * @param buffer
     *            will receive the values of this matrix in column-major order
     * @return the passed in buffer
     */
    public ByteBuffer get(int index, ByteBuffer buffer) {
        buffer.putDouble(index+8*0, m00);
        buffer.putDouble(index+8*1, m01);
        buffer.putDouble(index+8*2, m02);
        buffer.putDouble(index+8*3, m10);
        buffer.putDouble(index+8*4, m11);
        buffer.putDouble(index+8*5, m12);
        buffer.putDouble(index+8*6, m20);
        buffer.putDouble(index+8*7, m21);
        buffer.putDouble(index+8*8, m22);
        return buffer;
    }

    /**
     * Store the elements of this matrix as float values in column-major order into the supplied {@link ByteBuffer} at the current
     * buffer {@link ByteBuffer#position() position}.
     * <p>
     * This method will not increment the position of the given ByteBuffer.
     * <p>
     * Please note that due to this matrix storing double values those values will potentially
     * lose precision when they are converted to float values before being put into the given ByteBuffer.
     * <p>
     * In order to specify the offset into the ByteBuffer at which
     * the matrix is stored, use {@link #getFloats(int, ByteBuffer)}, taking
     * the absolute position as parameter.
     * 
     * @see #getFloats(int, ByteBuffer)
     * 
     * @param buffer
     *            will receive the elements of this matrix as float values in column-major order at its current position
     * @return the passed in buffer
     */
    public ByteBuffer getFloats(ByteBuffer buffer) {
        return getFloats(buffer.position(), buffer);
    }

    /**
     * Store the elements of this matrix as float values in column-major order into the supplied {@link ByteBuffer}
     * starting at the specified absolute buffer position/index.
     * <p>
     * This method will not increment the position of the given ByteBuffer.
     * <p>
     * Please note that due to this matrix storing double values those values will potentially
     * lose precision when they are converted to float values before being put into the given ByteBuffer.
     * 
     * @param index
     *            the absolute position into the ByteBuffer
     * @param buffer
     *            will receive the elements of this matrix as float values in column-major order
     * @return the passed in buffer
     */
    public ByteBuffer getFloats(int index, ByteBuffer buffer) {
        buffer.putFloat(index+4*0, (float)m00);
        buffer.putFloat(index+4*1, (float)m01);
        buffer.putFloat(index+4*2, (float)m02);
        buffer.putFloat(index+4*3, (float)m10);
        buffer.putFloat(index+4*4, (float)m11);
        buffer.putFloat(index+4*5, (float)m12);
        buffer.putFloat(index+4*6, (float)m20);
        buffer.putFloat(index+4*7, (float)m21);
        buffer.putFloat(index+4*8, (float)m22);
        return buffer;
    }

    /**
     * Store this matrix into the supplied double array in column-major order at the given offset.
     * 
     * @param arr
     *          the array to write the matrix values into
     * @param offset
     *          the offset into the array
     * @return the passed in array
     */
    public double[] get(double[] arr, int offset) {
        arr[offset+0] = m00;
        arr[offset+1] = m01;
        arr[offset+2] = m02;
        arr[offset+3] = m10;
        arr[offset+4] = m11;
        arr[offset+5] = m12;
        arr[offset+6] = m20;
        arr[offset+7] = m21;
        arr[offset+8] = m22;
        return arr;
    }

    /**
     * Store this matrix into the supplied double array in column-major order.
     * <p>
     * In order to specify an explicit offset into the array, use the method {@link #get(double[], int)}.
     * 
     * @see #get(double[], int)
     * 
     * @param arr
     *          the array to write the matrix values into
     * @return the passed in array
     */
    public double[] get(double[] arr) {
        return get(arr, 0);
    }

    /**
     * Store the elements of this matrix as float values in column-major order into the supplied float array at the given offset.
     * <p>
     * Please note that due to this matrix storing double values those values will potentially
     * lose precision when they are converted to float values before being put into the given float array.
     * 
     * @param arr
     *          the array to write the matrix values into
     * @param offset
     *          the offset into the array
     * @return the passed in array
     */
    public float[] get(float[] arr, int offset) {
        arr[offset+0] = (float)m00;
        arr[offset+1] = (float)m01;
        arr[offset+2] = (float)m02;
        arr[offset+3] = (float)m10;
        arr[offset+4] = (float)m11;
        arr[offset+5] = (float)m12;
        arr[offset+6] = (float)m20;
        arr[offset+7] = (float)m21;
        arr[offset+8] = (float)m22;
        return arr;
    }

    /**
     * Store the elements of this matrix as float values in column-major order into the supplied float array.
     * <p>
     * Please note that due to this matrix storing double values those values will potentially
     * lose precision when they are converted to float values before being put into the given float array.
     * <p>
     * In order to specify an explicit offset into the array, use the method {@link #get(float[], int)}.
     * 
     * @see #get(float[], int)
     * 
     * @param arr
     *          the array to write the matrix values into
     * @return the passed in array
     */
    public float[] get(float[] arr) {
        return get(arr, 0);
    }

    /**
     * Set the values of this matrix by reading 9 double values from the given {@link DoubleBuffer} in column-major order,
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
    public Matrix3d set(DoubleBuffer buffer) {
        int pos = buffer.position();
        m00 = buffer.get(pos);
        m01 = buffer.get(pos+1);
        m02 = buffer.get(pos+2);
        m10 = buffer.get(pos+3);
        m11 = buffer.get(pos+4);
        m12 = buffer.get(pos+5);
        m20 = buffer.get(pos+6);
        m21 = buffer.get(pos+7);
        m22 = buffer.get(pos+8);
        return this;
    }

    /**
     * Set the values of this matrix by reading 9 float values from the given {@link FloatBuffer} in column-major order,
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
    public Matrix3d set(FloatBuffer buffer) {
        int pos = buffer.position();
        m00 = buffer.get(pos);
        m01 = buffer.get(pos+1);
        m02 = buffer.get(pos+2);
        m10 = buffer.get(pos+3);
        m11 = buffer.get(pos+4);
        m12 = buffer.get(pos+5);
        m20 = buffer.get(pos+6);
        m21 = buffer.get(pos+7);
        m22 = buffer.get(pos+8);
        return this;
    }

    /**
     * Set the values of this matrix by reading 9 double values from the given {@link ByteBuffer} in column-major order,
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
    public Matrix3d set(ByteBuffer buffer) {
        int pos = buffer.position();
        m00 = buffer.getDouble(pos);
        m01 = buffer.getDouble(pos+8*1);
        m02 = buffer.getDouble(pos+8*2);
        m10 = buffer.getDouble(pos+8*3);
        m11 = buffer.getDouble(pos+8*4);
        m12 = buffer.getDouble(pos+8*5);
        m20 = buffer.getDouble(pos+8*6);
        m21 = buffer.getDouble(pos+8*7);
        m22 = buffer.getDouble(pos+8*8);
        return this;
    }

    /**
     * Set the values of this matrix by reading 9 float values from the given {@link ByteBuffer} in column-major order,
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
    public Matrix3d setFloats(ByteBuffer buffer) {
        int pos = buffer.position();
        m00 = buffer.getFloat(pos);
        m01 = buffer.getFloat(pos+4*1);
        m02 = buffer.getFloat(pos+4*2);
        m10 = buffer.getFloat(pos+4*3);
        m11 = buffer.getFloat(pos+4*4);
        m12 = buffer.getFloat(pos+4*5);
        m20 = buffer.getFloat(pos+4*6);
        m21 = buffer.getFloat(pos+4*7);
        m22 = buffer.getFloat(pos+4*8);
        return this;
    }

    /**
     * Set all the values within this matrix to 0.
     * 
     * @return this
     */
    public Matrix3d zero() {
        m00 = 0.0;
        m01 = 0.0;
        m02 = 0.0;
        m10 = 0.0;
        m11 = 0.0;
        m12 = 0.0;
        m20 = 0.0;
        m21 = 0.0;
        m22 = 0.0;
        return this;
    }
    
    /**
     * Set this matrix to the identity.
     * 
     * @return this
     */
    public Matrix3d identity() {
        m00 = 1.0;
        m01 = 0.0;
        m02 = 0.0;
        m10 = 0.0;
        m11 = 1.0;
        m12 = 0.0;
        m20 = 0.0;
        m21 = 0.0;
        m22 = 1.0;
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
    public Matrix3d scaling(double factor) {
        m00 = factor;
        m01 = 0.0;
        m02 = 0.0;
        m10 = 0.0;
        m11 = factor;
        m12 = 0.0;
        m20 = 0.0;
        m21 = 0.0;
        m22 = factor;
        return this;
    }

    /**
     * Set this matrix to be a simple scale matrix.
     * 
     * @param x
     *             the scale in x
     * @param y
     *             the scale in y
     * @param z
     *             the scale in z
     * @return this
     */
    public Matrix3d scaling(double x, double y, double z) {
        m00 = x;
        m01 = 0.0;
        m02 = 0.0;
        m10 = 0.0;
        m11 = y;
        m12 = 0.0;
        m20 = 0.0;
        m21 = 0.0;
        m22 = z;
        return this;
    }

    /**
     * Set this matrix to be a simple scale matrix which scales the base axes by <tt>xyz.x</tt>, <tt>xyz.y</tt> and <tt>xyz.z</tt> respectively.
     * <p>
     * The resulting matrix can be multiplied against another transformation
     * matrix to obtain an additional scaling.
     * <p>
     * In order to post-multiply a scaling transformation directly to a
     * matrix use {@link #scale(Vector3d) scale()} instead.
     * 
     * @see #scale(Vector3d)
     * 
     * @param xyz
     *             the scale in x, y and z respectively
     * @return this
     */
    public Matrix3d scaling(Vector3d xyz) {
        m00 = xyz.x;
        m01 = 0.0;
        m02 = 0.0;
        m10 = 0.0;
        m11 = xyz.y;
        m12 = 0.0;
        m20 = 0.0;
        m21 = 0.0;
        m22 = xyz.z;
        return this;
    }

    /**
     * Apply scaling to the this matrix by scaling the base axes by the given <tt>xyz.x</tt>,
     * <tt>xyz.y</tt> and <tt>xyz.z</tt> factors, respectively and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>S</code> the scaling matrix,
     * then the new matrix will be <code>M * S</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * S * v</code>
     * , the scaling will be applied first!
     * 
     * @param xyz
     *            the factors of the x, y and z component, respectively
     * @param dest
     *            will hold the result
     * @return dest
     */
    public Matrix3d scale(Vector3d xyz, Matrix3d dest) {
        return scale(xyz.x, xyz.y, xyz.z, dest);
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
    public Matrix3d scale(Vector3d xyz) {
        return scale(xyz.x, xyz.y, xyz.z, this);
    }

    /**
     * Apply scaling to this matrix by scaling the base axes by the given x,
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
     * @return dest
     */
    public Matrix3d scale(double x, double y, double z, Matrix3d dest) {
        // scale matrix elements:
        // m00 = x, m11 = y, m22 = z
        // all others = 0
        dest.m00 = m00 * x;
        dest.m01 = m01 * x;
        dest.m02 = m02 * x;
        dest.m10 = m10 * y;
        dest.m11 = m11 * y;
        dest.m12 = m12 * y;
        dest.m20 = m20 * z;
        dest.m21 = m21 * z;
        dest.m22 = m22 * z;
        return dest;
    }

    /**
     * Apply scaling to this matrix by scaling the base axes by the given x,
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
    public Matrix3d scale(double x, double y, double z) {
        return scale(x, y, z, this);
    }

    /**
     * Apply scaling to this matrix by uniformly scaling all base axes by the given <code>xyz</code> factor
     * and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>S</code> the scaling matrix,
     * then the new matrix will be <code>M * S</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * S * v</code>
     * , the scaling will be applied first!
     * 
     * @see #scale(double, double, double, Matrix3d)
     * 
     * @param xyz
     *            the factor for all components
     * @param dest
     *            will hold the result
     * @return dest
     */
    public Matrix3d scale(double xyz, Matrix3d dest) {
        return scale(xyz, xyz, xyz, dest);
    }

    /**
     * Apply scaling to this matrix by uniformly scaling all base axes by the given <code>xyz</code> factor.
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
    public Matrix3d scale(double xyz) {
        return scale(xyz, xyz, xyz);
    }

    /**
     * Set this matrix to a rotation matrix which rotates the given radians about a given axis.
     * <p>
     * The resulting matrix can be multiplied against another transformation
     * matrix to obtain an additional rotation.
     * <p>
     * In order to post-multiply a rotation transformation directly to a
     * matrix, use {@link #rotate(double, Vector3d) rotate()} instead.
     * 
     * @see #rotate(double, Vector3d)
     * 
     * @param angle
     *          the angle in radians
     * @param axis
     *          the axis to rotate about (needs to be {@link Vector3d#normalize() normalized})
     * @return this
     */
    public Matrix3d rotation(double angle, Vector3d axis) {
        return rotation(angle, axis.x, axis.y, axis.z);
    }

    /**
     * Set this matrix to a rotation matrix which rotates the given radians about a given axis.
     * <p>
     * The resulting matrix can be multiplied against another transformation
     * matrix to obtain an additional rotation.
     * <p>
     * In order to post-multiply a rotation transformation directly to a
     * matrix, use {@link #rotate(double, Vector3f) rotate()} instead.
     * 
     * @see #rotate(double, Vector3f)
     * 
     * @param angle
     *          the angle in radians
     * @param axis
     *          the axis to rotate about (needs to be {@link Vector3f#normalize() normalized})
     * @return this
     */
    public Matrix3d rotation(double angle, Vector3f axis) {
        return rotation(angle, axis.x, axis.y, axis.z);
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
     * @param axisAngle
     *          the {@link AxisAngle4f} (needs to be {@link AxisAngle4f#normalize() normalized})
     * @return this
     */
    public Matrix3d rotation(AxisAngle4f axisAngle) {
        return rotation(axisAngle.angle, axisAngle.x, axisAngle.y, axisAngle.z);
    }

    /**
     * Set this matrix to a rotation transformation using the given {@link AxisAngle4d}.
     * <p>
     * The resulting matrix can be multiplied against another transformation
     * matrix to obtain an additional rotation.
     * <p>
     * In order to apply the rotation transformation to an existing transformation,
     * use {@link #rotate(AxisAngle4d) rotate()} instead.
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Axis_and_angle">http://en.wikipedia.org</a>
     *
     * @see #rotate(AxisAngle4d)
     * 
     * @param axisAngle
     *          the {@link AxisAngle4d} (needs to be {@link AxisAngle4d#normalize() normalized})
     * @return this
     */
    public Matrix3d rotation(AxisAngle4d axisAngle) {
        return rotation(axisAngle.angle, axisAngle.x, axisAngle.y, axisAngle.z);
    }

    /**
     * Set this matrix to a rotation matrix which rotates the given radians about a given axis.
     * <p>
     * The axis described by the three components needs to be a unit vector.
     * <p>
     * The resulting matrix can be multiplied against another transformation
     * matrix to obtain an additional rotation.
     * <p>
     * In order to apply the rotation transformation to an existing transformation,
     * use {@link #rotate(double, double, double, double) rotate()} instead.
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Rotation_matrix_from_axis_and_angle">http://en.wikipedia.org</a>
     * 
     * @see #rotate(double, double, double, double)
     * 
     * @param angle
     *          the angle in radians
     * @param x
     *          the x-component of the rotation axis
     * @param y
     *          the y-component of the rotation axis
     * @param z
     *          the z-component of the rotation axis
     * @return this
     */
    public Matrix3d rotation(double angle, double x, double y, double z) {
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);
        double C = 1.0 - cos;
        double xy = x * y, xz = x * z, yz = y * z;
        m00 = cos + x * x * C;
        m10 = xy * C - z * sin;
        m20 = xz * C + y * sin;
        m01 = xy * C + z * sin;
        m11 = cos + y * y * C;
        m21 = yz * C - x * sin;
        m02 = xz * C - y * sin;
        m12 = yz * C + x * sin;
        m22 = cos + z * z * C;
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
    public Matrix3d rotationX(double ang) {
        double sin, cos;
        if (ang == Math.PI || ang == -Math.PI) {
            cos = -1.0;
            sin = 0.0;
        } else if (ang == Math.PI * 0.5 || ang == -Math.PI * 1.5) {
            cos = 0.0;
            sin = 1.0;
        } else if (ang == -Math.PI * 0.5 || ang == Math.PI * 1.5) {
            cos = 0.0;
            sin = -1.0;
        } else {
            cos = Math.cos(ang);
            sin = Math.sin(ang);
        }
        m00 = 1.0;
        m01 = 0.0;
        m02 = 0.0;
        m10 = 0.0;
        m11 = cos;
        m12 = sin;
        m20 = 0.0;
        m21 = -sin;
        m22 = cos;
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
    public Matrix3d rotationY(double ang) {
        double sin, cos;
        if (ang == Math.PI || ang == -Math.PI) {
            cos = -1.0;
            sin = 0.0;
        } else if (ang == Math.PI * 0.5 || ang == -Math.PI * 1.5) {
            cos = 0.0;
            sin = 1.0;
        } else if (ang == -Math.PI * 0.5 || ang == Math.PI * 1.5) {
            cos = 0.0;
            sin = -1.0;
        } else {
            cos = Math.cos(ang);
            sin = Math.sin(ang);
        }
        m00 = cos;
        m01 = 0.0;
        m02 = -sin;
        m10 = 0.0;
        m11 = 1.0;
        m12 = 0.0;
        m20 = sin;
        m21 = 0.0;
        m22 = cos;
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
    public Matrix3d rotationZ(double ang) {
        double sin, cos;
        if (ang == Math.PI || ang == -Math.PI) {
            cos = -1.0;
            sin = 0.0;
        } else if (ang == Math.PI * 0.5 || ang == -Math.PI * 1.5) {
            cos = 0.0;
            sin = 1.0;
        } else if (ang == -Math.PI * 0.5 || ang == Math.PI * 1.5) {
            cos = 0.0;
            sin = -1.0;
        } else {
            cos = Math.cos(ang);
            sin = Math.sin(ang);
        }
        m00 = cos;
        m01 = sin;
        m02 = 0.0;
        m10 = -sin;
        m11 = cos;
        m12 = 0.0;
        m20 = 0.0;
        m21 = 0.0;
        m22 = 1.0;
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
     * @return this
     */
    public Matrix3d rotationXYZ(double angleX, double angleY, double angleZ) {
        double cosX =  Math.cos(angleX);
        double sinX =  Math.sin(angleX);
        double cosY =  Math.cos(angleY);
        double sinY =  Math.sin(angleY);
        double cosZ =  Math.cos(angleZ);
        double sinZ =  Math.sin(angleZ);
        double m_sinX = -sinX;
        double m_sinY = -sinY;
        double m_sinZ = -sinZ;

        // rotateX
        double nm11 = cosX;
        double nm12 = sinX;
        double nm21 = m_sinX;
        double nm22 = cosX;
        // rotateY
        double nm00 = cosY;
        double nm01 = nm21 * m_sinY;
        double nm02 = nm22 * m_sinY;
        m20 = sinY;
        m21 = nm21 * cosY;
        m22 = nm22 * cosY;
        // rotateZ
        m00 = nm00 * cosZ;
        m01 = nm01 * cosZ + nm11 * sinZ;
        m02 = nm02 * cosZ + nm12 * sinZ;
        m10 = nm00 * m_sinZ;
        m11 = nm01 * m_sinZ + nm11 * cosZ;
        m12 = nm02 * m_sinZ + nm12 * cosZ;
        return this;
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
     * @return this
     */
    public Matrix3d rotationZYX(double angleZ, double angleY, double angleX) {
        double cosZ =  Math.cos(angleZ);
        double sinZ =  Math.sin(angleZ);
        double cosY =  Math.cos(angleY);
        double sinY =  Math.sin(angleY);
        double cosX =  Math.cos(angleX);
        double sinX =  Math.sin(angleX);
        double m_sinZ = -sinZ;
        double m_sinY = -sinY;
        double m_sinX = -sinX;

        // rotateZ
        double nm00 = cosZ;
        double nm01 = sinZ;
        double nm10 = m_sinZ;
        double nm11 = cosZ;
        // rotateY
        double nm20 = nm00 * sinY;
        double nm21 = nm01 * sinY;
        double nm22 = cosY;
        m00 = nm00 * cosY;
        m01 = nm01 * cosY;
        m02 = m_sinY;
        // rotateX
        m10 = nm10 * cosX + nm20 * sinX;
        m11 = nm11 * cosX + nm21 * sinX;
        m12 = nm22 * sinX;
        m20 = nm10 * m_sinX + nm20 * cosX;
        m21 = nm11 * m_sinX + nm21 * cosX;
        m22 = nm22 * cosX;
        return this;
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
     * @return this
     */
    public Matrix3d rotationYXZ(double angleY, double angleX, double angleZ) {
        double cosY = Math.cos(angleY);
        double sinY = Math.sin(angleY);
        double cosX = Math.cos(angleX);
        double sinX = Math.sin(angleX);
        double cosZ = Math.cos(angleZ);
        double sinZ = Math.sin(angleZ);
        double m_sinY = -sinY;
        double m_sinX = -sinX;
        double m_sinZ = -sinZ;

        // rotateY
        double nm00 = cosY;
        double nm02 = m_sinY;
        double nm20 = sinY;
        double nm22 = cosY;
        // rotateX
        double nm10 = nm20 * sinX;
        double nm11 = cosX;
        double nm12 = nm22 * sinX;
        m20 = nm20 * cosX;
        m21 = m_sinX;
        m22 = nm22 * cosX;
        // rotateZ
        m00 = nm00 * cosZ + nm10 * sinZ;
        m01 = nm11 * sinZ;
        m02 = nm02 * cosZ + nm12 * sinZ;
        m10 = nm00 * m_sinZ + nm10 * cosZ;
        m11 = nm11 * cosZ;
        m12 = nm02 * m_sinZ + nm12 * cosZ;
        return this;
    }

    /**
     * Set this matrix to the rotation transformation of the given {@link Quaterniond}.
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
    public Matrix3d rotation(Quaterniond quat) {
        double dqx = quat.x + quat.x;
        double dqy = quat.y + quat.y;
        double dqz = quat.z + quat.z;
        double q00 = dqx * quat.x;
        double q11 = dqy * quat.y;
        double q22 = dqz * quat.z;
        double q01 = dqx * quat.y;
        double q02 = dqx * quat.z;
        double q03 = dqx * quat.w;
        double q12 = dqy * quat.z;
        double q13 = dqy * quat.w;
        double q23 = dqz * quat.w;

        m00 = 1.0 - q11 - q22;
        m01 = q01 + q23;
        m02 = q02 - q13;
        m10 = q01 - q23;
        m11 = 1.0 - q22 - q00;
        m12 = q12 + q03;
        m20 = q02 + q13;
        m21 = q12 - q03;
        m22 = 1.0 - q11 - q00;

        return this;
    }

    /**
     * Set this matrix to the rotation transformation of the given {@link Quaternionf}.
     * <p>
     * The resulting matrix can be multiplied against another transformation
     * matrix to obtain an additional rotation.
     * <p>
     * In order to apply the rotation transformation to an existing transformation,
     * use {@link #rotate(Quaternionf) rotate()} instead.
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Quaternion">http://en.wikipedia.org</a>
     * 
     * @see #rotate(Quaternionf)
     * 
     * @param quat
     *          the {@link Quaternionf}
     * @return this
     */
    public Matrix3d rotation(Quaternionf quat) {
        double dqx = quat.x + quat.x;
        double dqy = quat.y + quat.y;
        double dqz = quat.z + quat.z;
        double q00 = dqx * quat.x;
        double q11 = dqy * quat.y;
        double q22 = dqz * quat.z;
        double q01 = dqx * quat.y;
        double q02 = dqx * quat.z;
        double q03 = dqx * quat.w;
        double q12 = dqy * quat.z;
        double q13 = dqy * quat.w;
        double q23 = dqz * quat.w;

        m00 = 1.0 - q11 - q22;
        m01 = q01 + q23;
        m02 = q02 - q13;
        m10 = q01 - q23;
        m11 = 1.0 - q22 - q00;
        m12 = q12 + q03;
        m20 = q02 + q13;
        m21 = q12 - q03;
        m22 = 1.0 - q11 - q00;

        return this;
    }

    /**
     * Transform the given vector by this matrix.
     * 
     * @param v
     *          the vector to transform
     * @return v
     */
    public Vector3d transform(Vector3d v) {
        return v.mul(this);
    }

    /**
     * Transform the given vector by this matrix and store the result in <code>dest</code>.
     * 
     * @param v
     *          the vector to transform
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Vector3d transform(Vector3d v, Vector3d dest) {
        v.mul(this, dest);
        return dest;
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeDouble(m00);
        out.writeDouble(m01);
        out.writeDouble(m02);
        out.writeDouble(m10);
        out.writeDouble(m11);
        out.writeDouble(m12);
        out.writeDouble(m20);
        out.writeDouble(m21);
        out.writeDouble(m22);
    }

    public void readExternal(ObjectInput in) throws IOException,
            ClassNotFoundException {
        m00 = in.readDouble();
        m01 = in.readDouble();
        m02 = in.readDouble();
        m10 = in.readDouble();
        m11 = in.readDouble();
        m12 = in.readDouble();
        m20 = in.readDouble();
        m21 = in.readDouble();
        m22 = in.readDouble();
    }

    /**
     * Apply rotation about the X axis to this matrix by rotating the given amount of radians
     * and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the rotation matrix,
     * then the new matrix will be <code>M * R</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * R * v</code>
     * , the rotation will be applied first!
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Basic_rotations">http://en.wikipedia.org</a>
     * 
     * @param ang
     *            the angle in radians
     * @param dest
     *            will hold the result
     * @return dest
     */
    public Matrix3d rotateX(double ang, Matrix3d dest) {
        double sin, cos;
        if (ang == Math.PI || ang == -Math.PI) {
            cos = -1.0;
            sin = 0.0;
        } else if (ang == Math.PI * 0.5 || ang == -Math.PI * 1.5) {
            cos = 0.0;
            sin = 1.0;
        } else if (ang == -Math.PI * 0.5 || ang == Math.PI * 1.5) {
            cos = 0.0;
            sin = -1.0;
        } else {
            cos = Math.cos(ang);
            sin = Math.sin(ang);
        }
        double rm11 = cos;
        double rm21 = -sin;
        double rm12 = sin;
        double rm22 = cos;

        // add temporaries for dependent values
        double nm10 = m10 * rm11 + m20 * rm12;
        double nm11 = m11 * rm11 + m21 * rm12;
        double nm12 = m12 * rm11 + m22 * rm12;
        // set non-dependent values directly
        dest.m20 = m10 * rm21 + m20 * rm22;
        dest.m21 = m11 * rm21 + m21 * rm22;
        dest.m22 = m12 * rm21 + m22 * rm22;
        // set other values
        dest.m10 = nm10;
        dest.m11 = nm11;
        dest.m12 = nm12;
        dest.m00 = m00;
        dest.m01 = m01;
        dest.m02 = m02;

        return dest;
    }

    /**
     * Apply rotation about the X axis to this matrix by rotating the given amount of radians.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the rotation matrix,
     * then the new matrix will be <code>M * R</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * R * v</code>
     * , the rotation will be applied first!
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Basic_rotations">http://en.wikipedia.org</a>
     * 
     * @param ang
     *            the angle in radians
     * @return this
     */
    public Matrix3d rotateX(double ang) {
        return rotateX(ang, this);
    }

    /**
     * Apply rotation about the Y axis to this matrix by rotating the given amount of radians
     * and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the rotation matrix,
     * then the new matrix will be <code>M * R</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * R * v</code>
     * , the rotation will be applied first!
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Basic_rotations">http://en.wikipedia.org</a>
     * 
     * @param ang
     *            the angle in radians
     * @param dest
     *            will hold the result
     * @return dest
     */
    public Matrix3d rotateY(double ang, Matrix3d dest) {
        double sin, cos;
        if (ang == Math.PI || ang == -Math.PI) {
            cos = -1.0;
            sin = 0.0;
        } else if (ang == Math.PI * 0.5 || ang == -Math.PI * 1.5) {
            cos = 0.0;
            sin = 1.0;
        } else if (ang == -Math.PI * 0.5 || ang == Math.PI * 1.5) {
            cos = 0.0;
            sin = -1.0;
        } else {
            cos = Math.cos(ang);
            sin = Math.sin(ang);
        }
        double rm00 = cos;
        double rm20 = sin;
        double rm02 = -sin;
        double rm22 = cos;

        // add temporaries for dependent values
        double nm00 = m00 * rm00 + m20 * rm02;
        double nm01 = m01 * rm00 + m21 * rm02;
        double nm02 = m02 * rm00 + m22 * rm02;
        // set non-dependent values directly
        dest.m20 = m00 * rm20 + m20 * rm22;
        dest.m21 = m01 * rm20 + m21 * rm22;
        dest.m22 = m02 * rm20 + m22 * rm22;
        // set other values
        dest.m00 = nm00;
        dest.m01 = nm01;
        dest.m02 = nm02;
        dest.m10 = m10;
        dest.m11 = m11;
        dest.m12 = m12;

        return dest;
    }

    /**
     * Apply rotation about the Y axis to this matrix by rotating the given amount of radians.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the rotation matrix,
     * then the new matrix will be <code>M * R</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * R * v</code>
     * , the rotation will be applied first!
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Basic_rotations">http://en.wikipedia.org</a>
     * 
     * @param ang
     *            the angle in radians
     * @return this
     */
    public Matrix3d rotateY(double ang) {
        return rotateY(ang, this);
    }

    /**
     * Apply rotation about the Z axis to this matrix by rotating the given amount of radians
     * and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the rotation matrix,
     * then the new matrix will be <code>M * R</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * R * v</code>
     * , the rotation will be applied first!
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Basic_rotations">http://en.wikipedia.org</a>
     * 
     * @param ang
     *            the angle in radians
     * @param dest
     *            will hold the result
     * @return dest
     */
    public Matrix3d rotateZ(double ang, Matrix3d dest) {
        double sin, cos;
        if (ang == Math.PI || ang == -Math.PI) {
            cos = -1.0;
            sin = 0.0;
        } else if (ang == Math.PI * 0.5 || ang == -Math.PI * 1.5) {
            cos = 0.0;
            sin = 1.0;
        } else if (ang == -Math.PI * 0.5 || ang == Math.PI * 1.5) {
            cos = 0.0;
            sin = -1.0;
        } else {
            cos = Math.cos(ang);
            sin = Math.sin(ang);
        }
        double rm00 = cos;
        double rm10 = -sin;
        double rm01 = sin;
        double rm11 = cos;

        // add temporaries for dependent values
        double nm00 = m00 * rm00 + m10 * rm01;
        double nm01 = m01 * rm00 + m11 * rm01;
        double nm02 = m02 * rm00 + m12 * rm01;
        // set non-dependent values directly
        dest.m10 = m00 * rm10 + m10 * rm11;
        dest.m11 = m01 * rm10 + m11 * rm11;
        dest.m12 = m02 * rm10 + m12 * rm11;
        // set other values
        dest.m00 = nm00;
        dest.m01 = nm01;
        dest.m02 = nm02;
        dest.m20 = m20;
        dest.m21 = m21;
        dest.m22 = m22;

        return dest;
    }

    /**
     * Apply rotation about the Z axis to this matrix by rotating the given amount of radians.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the rotation matrix,
     * then the new matrix will be <code>M * R</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * R * v</code>
     * , the rotation will be applied first!
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Basic_rotations">http://en.wikipedia.org</a>
     * 
     * @param ang
     *            the angle in radians
     * @return this
     */
    public Matrix3d rotateZ(double ang) {
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
    public Matrix3d rotateXYZ(double angleX, double angleY, double angleZ) {
        return rotateXYZ(angleX, angleY, angleZ, this);
    }

    /**
     * Apply rotation of <code>angleX</code> radians about the X axis, followed by a rotation of <code>angleY</code> radians about the Y axis and
     * followed by a rotation of <code>angleZ</code> radians about the Z axis and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the rotation matrix,
     * then the new matrix will be <code>M * R</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * R * v</code>, the
     * rotation will be applied first!
     * <p>
     * This method is equivalent to calling: <tt>rotateX(angleX, dest).rotateY(angleY).rotateZ(angleZ)</tt>
     * 
     * @param angleX
     *            the angle to rotate about X
     * @param angleY
     *            the angle to rotate about Y
     * @param angleZ
     *            the angle to rotate about Z
     * @param dest
     *            will hold the result
     * @return dest
     */
    public Matrix3d rotateXYZ(double angleX, double angleY, double angleZ, Matrix3d dest) {
        double cosX = Math.cos(angleX);
        double sinX = Math.sin(angleX);
        double cosY = Math.cos(angleY);
        double sinY = Math.sin(angleY);
        double cosZ = Math.cos(angleZ);
        double sinZ = Math.sin(angleZ);
        double m_sinX = -sinX;
        double m_sinY = -sinY;
        double m_sinZ = -sinZ;

        // rotateX
        double nm10 = m10 * cosX + m20 * sinX;
        double nm11 = m11 * cosX + m21 * sinX;
        double nm12 = m12 * cosX + m22 * sinX;
        double nm20 = m10 * m_sinX + m20 * cosX;
        double nm21 = m11 * m_sinX + m21 * cosX;
        double nm22 = m12 * m_sinX + m22 * cosX;
        // rotateY
        double nm00 = m00 * cosY + nm20 * m_sinY;
        double nm01 = m01 * cosY + nm21 * m_sinY;
        double nm02 = m02 * cosY + nm22 * m_sinY;
        dest.m20 = m00 * sinY + nm20 * cosY;
        dest.m21 = m01 * sinY + nm21 * cosY;
        dest.m22 = m02 * sinY + nm22 * cosY;
        // rotateZ
        dest.m00 = nm00 * cosZ + nm10 * sinZ;
        dest.m01 = nm01 * cosZ + nm11 * sinZ;
        dest.m02 = nm02 * cosZ + nm12 * sinZ;
        dest.m10 = nm00 * m_sinZ + nm10 * cosZ;
        dest.m11 = nm01 * m_sinZ + nm11 * cosZ;
        dest.m12 = nm02 * m_sinZ + nm12 * cosZ;
        return dest;
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
    public Matrix3d rotateZYX(double angleZ, double angleY, double angleX) {
        return rotateZYX(angleZ, angleY, angleX, this);
    }

    /**
     * Apply rotation of <code>angleZ</code> radians about the Z axis, followed by a rotation of <code>angleY</code> radians about the Y axis and
     * followed by a rotation of <code>angleX</code> radians about the X axis and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the rotation matrix,
     * then the new matrix will be <code>M * R</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * R * v</code>, the
     * rotation will be applied first!
     * <p>
     * This method is equivalent to calling: <tt>rotateZ(angleZ, dest).rotateY(angleY).rotateX(angleX)</tt>
     * 
     * @param angleZ
     *            the angle to rotate about Z
     * @param angleY
     *            the angle to rotate about Y
     * @param angleX
     *            the angle to rotate about X
     * @param dest
     *            will hold the result
     * @return dest
     */
    public Matrix3d rotateZYX(double angleZ, double angleY, double angleX, Matrix3d dest) {
        double cosZ = Math.cos(angleZ);
        double sinZ = Math.sin(angleZ);
        double cosY = Math.cos(angleY);
        double sinY = Math.sin(angleY);
        double cosX = Math.cos(angleX);
        double sinX = Math.sin(angleX);
        double m_sinZ = -sinZ;
        double m_sinY = -sinY;
        double m_sinX = -sinX;

        // rotateZ
        double nm00 = m00 * cosZ + m10 * sinZ;
        double nm01 = m01 * cosZ + m11 * sinZ;
        double nm02 = m02 * cosZ + m12 * sinZ;
        double nm10 = m00 * m_sinZ + m10 * cosZ;
        double nm11 = m01 * m_sinZ + m11 * cosZ;
        double nm12 = m02 * m_sinZ + m12 * cosZ;
        // rotateY
        double nm20 = nm00 * sinY + m20 * cosY;
        double nm21 = nm01 * sinY + m21 * cosY;
        double nm22 = nm02 * sinY + m22 * cosY;
        dest.m00 = nm00 * cosY + m20 * m_sinY;
        dest.m01 = nm01 * cosY + m21 * m_sinY;
        dest.m02 = nm02 * cosY + m22 * m_sinY;
        // rotateX
        dest.m10 = nm10 * cosX + nm20 * sinX;
        dest.m11 = nm11 * cosX + nm21 * sinX;
        dest.m12 = nm12 * cosX + nm22 * sinX;
        dest.m20 = nm10 * m_sinX + nm20 * cosX;
        dest.m21 = nm11 * m_sinX + nm21 * cosX;
        dest.m22 = nm12 * m_sinX + nm22 * cosX;
        return dest;
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
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Rotation_matrix_from_axis_and_angle">http://en.wikipedia.org</a>
     * 
     * @param ang
     *            the angle in radians
     * @param x
     *            the x component of the axis
     * @param y
     *            the y component of the axis
     * @param z
     *            the z component of the axis
     * @return this
     */
    public Matrix3d rotate(double ang, double x, double y, double z) {
        return rotate(ang, x, y, z, this);
    }

    /**
     * Apply rotation to this matrix by rotating the given amount of radians
     * about the given axis specified as x, y and z components, and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the rotation matrix,
     * then the new matrix will be <code>M * R</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * R * v</code>
     * , the rotation will be applied first!
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Rotation_matrix_from_axis_and_angle">http://en.wikipedia.org</a>
     * 
     * @param ang
     *            the angle in radians
     * @param x
     *            the x component of the axis
     * @param y
     *            the y component of the axis
     * @param z
     *            the z component of the axis
     * @param dest
     *            will hold the result
     * @return dest
     */
    public Matrix3d rotate(double ang, double x, double y, double z, Matrix3d dest) {
        double s = Math.sin(ang);
        double c = Math.cos(ang);
        double C = 1.0 - c;

        // rotation matrix elements:
        // m30, m31, m32, m03, m13, m23 = 0
        double xx = x * x, xy = x * y, xz = x * z;
        double yy = y * y, yz = y * z;
        double zz = z * z;
        double rm00 = xx * C + c;
        double rm01 = xy * C + z * s;
        double rm02 = xz * C - y * s;
        double rm10 = xy * C - z * s;
        double rm11 = yy * C + c;
        double rm12 = yz * C + x * s;
        double rm20 = xz * C + y * s;
        double rm21 = yz * C - x * s;
        double rm22 = zz * C + c;

        // add temporaries for dependent values
        double nm00 = m00 * rm00 + m10 * rm01 + m20 * rm02;
        double nm01 = m01 * rm00 + m11 * rm01 + m21 * rm02;
        double nm02 = m02 * rm00 + m12 * rm01 + m22 * rm02;
        double nm10 = m00 * rm10 + m10 * rm11 + m20 * rm12;
        double nm11 = m01 * rm10 + m11 * rm11 + m21 * rm12;
        double nm12 = m02 * rm10 + m12 * rm11 + m22 * rm12;
        // set non-dependent values directly
        dest.m20 = m00 * rm20 + m10 * rm21 + m20 * rm22;
        dest.m21 = m01 * rm20 + m11 * rm21 + m21 * rm22;
        dest.m22 = m02 * rm20 + m12 * rm21 + m22 * rm22;
        // set other values
        dest.m00 = nm00;
        dest.m01 = nm01;
        dest.m02 = nm02;
        dest.m10 = nm10;
        dest.m11 = nm11;
        dest.m12 = nm12;

        return dest;
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
    public Matrix3d rotate(Quaterniond quat) {
        return rotate(quat, this);
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
     * @return dest
     */
    public Matrix3d rotate(Quaterniond quat, Matrix3d dest) {
        double dqx = quat.x + quat.x;
        double dqy = quat.y + quat.y;
        double dqz = quat.z + quat.z;
        double q00 = dqx * quat.x;
        double q11 = dqy * quat.y;
        double q22 = dqz * quat.z;
        double q01 = dqx * quat.y;
        double q02 = dqx * quat.z;
        double q03 = dqx * quat.w;
        double q12 = dqy * quat.z;
        double q13 = dqy * quat.w;
        double q23 = dqz * quat.w;

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
        double nm10 = m00 * rm10 + m10 * rm11 + m20 * rm12;
        double nm11 = m01 * rm10 + m11 * rm11 + m21 * rm12;
        double nm12 = m02 * rm10 + m12 * rm11 + m22 * rm12;
        dest.m20 = m00 * rm20 + m10 * rm21 + m20 * rm22;
        dest.m21 = m01 * rm20 + m11 * rm21 + m21 * rm22;
        dest.m22 = m02 * rm20 + m12 * rm21 + m22 * rm22;
        dest.m00 = nm00;
        dest.m01 = nm01;
        dest.m02 = nm02;
        dest.m10 = nm10;
        dest.m11 = nm11;
        dest.m12 = nm12;

        return dest;
    }

    /**
     * Apply the rotation transformation of the given {@link Quaternionf} to this matrix.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>Q</code> the rotation matrix obtained from the given quaternion,
     * then the new matrix will be <code>M * Q</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * Q * v</code>,
     * the quaternion rotation will be applied first!
     * <p>
     * In order to set the matrix to a rotation transformation without post-multiplying,
     * use {@link #rotation(Quaternionf)}.
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Quaternion">http://en.wikipedia.org</a>
     * 
     * @see #rotation(Quaternionf)
     * 
     * @param quat
     *          the {@link Quaternionf}
     * @return this
     */
    public Matrix3d rotate(Quaternionf quat) {
        return rotate(quat, this);
    }

    /**
     * Apply the rotation transformation of the given {@link Quaternionf} to this matrix and store
     * the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>Q</code> the rotation matrix obtained from the given quaternion,
     * then the new matrix will be <code>M * Q</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * Q * v</code>,
     * the quaternion rotation will be applied first!
     * <p>
     * In order to set the matrix to a rotation transformation without post-multiplying,
     * use {@link #rotation(Quaternionf)}.
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Quaternion">http://en.wikipedia.org</a>
     * 
     * @see #rotation(Quaternionf)
     * 
     * @param quat
     *          the {@link Quaternionf}
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Matrix3d rotate(Quaternionf quat, Matrix3d dest) {
        double dqx = quat.x + quat.x;
        double dqy = quat.y + quat.y;
        double dqz = quat.z + quat.z;
        double q00 = dqx * quat.x;
        double q11 = dqy * quat.y;
        double q22 = dqz * quat.z;
        double q01 = dqx * quat.y;
        double q02 = dqx * quat.z;
        double q03 = dqx * quat.w;
        double q12 = dqy * quat.z;
        double q13 = dqy * quat.w;
        double q23 = dqz * quat.w;

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
        double nm10 = m00 * rm10 + m10 * rm11 + m20 * rm12;
        double nm11 = m01 * rm10 + m11 * rm11 + m21 * rm12;
        double nm12 = m02 * rm10 + m12 * rm11 + m22 * rm12;
        dest.m20 = m00 * rm20 + m10 * rm21 + m20 * rm22;
        dest.m21 = m01 * rm20 + m11 * rm21 + m21 * rm22;
        dest.m22 = m02 * rm20 + m12 * rm21 + m22 * rm22;
        dest.m00 = nm00;
        dest.m01 = nm01;
        dest.m02 = nm02;
        dest.m10 = nm10;
        dest.m11 = nm11;
        dest.m12 = nm12;

        return dest;
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
    public Matrix3d rotate(AxisAngle4f axisAngle) {
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
     * @return dest
     */
    public Matrix3d rotate(AxisAngle4f axisAngle, Matrix3d dest) {
        return rotate(axisAngle.angle, axisAngle.x, axisAngle.y, axisAngle.z, dest);
    }

    /**
     * Apply a rotation transformation, rotating about the given {@link AxisAngle4d}, to this matrix.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>A</code> the rotation matrix obtained from the given {@link AxisAngle4d},
     * then the new matrix will be <code>M * A</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * A * v</code>,
     * the {@link AxisAngle4d} rotation will be applied first!
     * <p>
     * In order to set the matrix to a rotation transformation without post-multiplying,
     * use {@link #rotation(AxisAngle4d)}.
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Axis_and_angle">http://en.wikipedia.org</a>
     * 
     * @see #rotate(double, double, double, double)
     * @see #rotation(AxisAngle4d)
     * 
     * @param axisAngle
     *          the {@link AxisAngle4d} (needs to be {@link AxisAngle4d#normalize() normalized})
     * @return this
     */
    public Matrix3d rotate(AxisAngle4d axisAngle) {
        return rotate(axisAngle.angle, axisAngle.x, axisAngle.y, axisAngle.z);
    }

    /**
     * Apply a rotation transformation, rotating about the given {@link AxisAngle4d} and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>A</code> the rotation matrix obtained from the given {@link AxisAngle4d},
     * then the new matrix will be <code>M * A</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * A * v</code>,
     * the {@link AxisAngle4d} rotation will be applied first!
     * <p>
     * In order to set the matrix to a rotation transformation without post-multiplying,
     * use {@link #rotation(AxisAngle4d)}.
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Axis_and_angle">http://en.wikipedia.org</a>
     * 
     * @see #rotate(double, double, double, double)
     * @see #rotation(AxisAngle4d)
     * 
     * @param axisAngle
     *          the {@link AxisAngle4d} (needs to be {@link AxisAngle4d#normalize() normalized})
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Matrix3d rotate(AxisAngle4d axisAngle, Matrix3d dest) {
        return rotate(axisAngle.angle, axisAngle.x, axisAngle.y, axisAngle.z, dest);
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
     * use {@link #rotation(double, Vector3d)}.
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Axis_and_angle">http://en.wikipedia.org</a>
     * 
     * @see #rotate(double, double, double, double)
     * @see #rotation(double, Vector3d)
     * 
     * @param angle
     *          the angle in radians
     * @param axis
     *          the rotation axis (needs to be {@link Vector3d#normalize() normalized})
     * @return this
     */
    public Matrix3d rotate(double angle, Vector3d axis) {
        return rotate(angle, axis.x, axis.y, axis.z);
    }

    /**
     * Apply a rotation transformation, rotating the given radians about the specified axis and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>A</code> the rotation matrix obtained from the given axis and angle,
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
     *          the angle in radians
     * @param axis
     *          the rotation axis (needs to be {@link Vector3d#normalize() normalized})
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Matrix3d rotate(double angle, Vector3d axis, Matrix3d dest) {
        return rotate(angle, axis.x, axis.y, axis.z, dest);
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
     * use {@link #rotation(double, Vector3f)}.
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Axis_and_angle">http://en.wikipedia.org</a>
     * 
     * @see #rotate(double, double, double, double)
     * @see #rotation(double, Vector3f)
     * 
     * @param angle
     *          the angle in radians
     * @param axis
     *          the rotation axis (needs to be {@link Vector3f#normalize() normalized})
     * @return this
     */
    public Matrix3d rotate(double angle, Vector3f axis) {
        return rotate(angle, axis.x, axis.y, axis.z);
    }

    /**
     * Apply a rotation transformation, rotating the given radians about the specified axis and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>A</code> the rotation matrix obtained from the given axis and angle,
     * then the new matrix will be <code>M * A</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * A * v</code>,
     * the axis-angle rotation will be applied first!
     * <p>
     * In order to set the matrix to a rotation transformation without post-multiplying,
     * use {@link #rotation(double, Vector3f)}.
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Axis_and_angle">http://en.wikipedia.org</a>
     * 
     * @see #rotate(double, double, double, double)
     * @see #rotation(double, Vector3f)
     * 
     * @param angle
     *          the angle in radians
     * @param axis
     *          the rotation axis (needs to be {@link Vector3f#normalize() normalized})
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Matrix3d rotate(double angle, Vector3f axis, Matrix3d dest) {
        return rotate(angle, axis.x, axis.y, axis.z, dest);
    }

    /**
     * Get the row at the given <code>row</code> index, starting with <code>0</code>.
     * 
     * @param row
     *          the row index in <tt>[0..2]</tt>
     * @param dest
     *          will hold the row components
     * @return the passed in destination
     * @throws IndexOutOfBoundsException if <code>row</code> is not in <tt>[0..2]</tt>
     */
    public Vector3d getRow(int row, Vector3d dest) throws IndexOutOfBoundsException {
        switch (row) {
        case 0:
            dest.x = m00;
            dest.y = m10;
            dest.z = m20;
            break;
        case 1:
            dest.x = m01;
            dest.y = m11;
            dest.z = m21;
            break;
        case 2:
            dest.x = m02;
            dest.y = m12;
            dest.z = m22;
            break;
        default:
            throw new IndexOutOfBoundsException();
        }
        
        return dest;
    }

    /**
     * Get the column at the given <code>column</code> index, starting with <code>0</code>.
     * 
     * @param column
     *          the column index in <tt>[0..2]</tt>
     * @param dest
     *          will hold the column components
     * @return the passed in destination
     * @throws IndexOutOfBoundsException if <code>column</code> is not in <tt>[0..2]</tt>
     */
    public Vector3d getColumn(int column, Vector3d dest) throws IndexOutOfBoundsException {
        switch (column) {
        case 0:
            dest.x = m00;
            dest.y = m01;
            dest.z = m02;
            break;
        case 1:
            dest.x = m10;
            dest.y = m11;
            dest.z = m12;
            break;
        case 2:
            dest.x = m20;
            dest.y = m21;
            dest.z = m22;
            break;
        default:
            throw new IndexOutOfBoundsException();
        }
        return dest;
    }

    /**
     * Set <code>this</code> matrix to its own normal matrix.
     * <p>
     * Please note that, if <code>this</code> is an orthogonal matrix or a matrix whose columns are orthogonal vectors, 
     * then this method <i>need not</i> be invoked, since in that case <code>this</code> itself is its normal matrix.
     * In this case, use {@link #set(Matrix3d)} to set a given Matrix3f to this matrix.
     * 
     * @see #set(Matrix3d)
     * 
     * @return this
     */
    public Matrix3d normal() {
        return normal(this);
    }

    /**
     * Compute a normal matrix from <code>this</code> matrix and store it into <code>dest</code>.
     * <p>
     * Please note that, if <code>this</code> is an orthogonal matrix or a matrix whose columns are orthogonal vectors, 
     * then this method <i>need not</i> be invoked, since in that case <code>this</code> itself is its normal matrix.
     * In this case, use {@link #set(Matrix3d)} to set a given Matrix3d to this matrix.
     * 
     * @see #set(Matrix3d)
     * 
     * @param dest
     *             will hold the result
     * @return dest
     */
    public Matrix3d normal(Matrix3d dest) {
        double det = determinant();
        double s = 1.0 / det;
        /* Invert and transpose in one go */
        dest.set((m11 * m22 - m21 * m12) * s,
                 (m20 * m12 - m10 * m22) * s,
                 (m10 * m21 - m20 * m11) * s,
                 (m21 * m02 - m01 * m22) * s,
                 (m00 * m22 - m20 * m02) * s,
                 (m20 * m01 - m00 * m21) * s,
                 (m01 * m12 - m11 * m02) * s,
                 (m10 * m02 - m00 * m12) * s,
                 (m00 * m11 - m10 * m01) * s);
        return dest;
    }

    /**
     * Apply a rotation transformation to this matrix to make <code>-z</code> point along <code>dir</code>. 
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>L</code> the lookalong rotation matrix,
     * then the new matrix will be <code>M * L</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * L * v</code>, the
     * lookalong rotation transformation will be applied first!
     * <p>
     * In order to set the matrix to a lookalong transformation without post-multiplying it,
     * use {@link #setLookAlong(Vector3d, Vector3d) setLookAlong()}.
     * 
     * @see #lookAlong(double, double, double, double, double, double)
     * @see #setLookAlong(Vector3d, Vector3d)
     * 
     * @param dir
     *            the direction in space to look along
     * @param up
     *            the direction of 'up'
     * @return this
     */
    public Matrix3d lookAlong(Vector3d dir, Vector3d up) {
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
     * In order to set the matrix to a lookalong transformation without post-multiplying it,
     * use {@link #setLookAlong(Vector3d, Vector3d) setLookAlong()}.
     * 
     * @see #lookAlong(double, double, double, double, double, double)
     * @see #setLookAlong(Vector3d, Vector3d)
     * 
     * @param dir
     *            the direction in space to look along
     * @param up
     *            the direction of 'up'
     * @param dest
     *            will hold the result
     * @return dest
     */
    public Matrix3d lookAlong(Vector3d dir, Vector3d up, Matrix3d dest) {
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
     * In order to set the matrix to a lookalong transformation without post-multiplying it,
     * use {@link #setLookAlong(double, double, double, double, double, double) setLookAlong()}
     * 
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
     * @return dest
     */
    public Matrix3d lookAlong(double dirX, double dirY, double dirZ,
                              double upX, double upY, double upZ, Matrix3d dest) {
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
        double nm10 = m00 * rm10 + m10 * rm11 + m20 * rm12;
        double nm11 = m01 * rm10 + m11 * rm11 + m21 * rm12;
        double nm12 = m02 * rm10 + m12 * rm11 + m22 * rm12;
        dest.m20 = m00 * rm20 + m10 * rm21 + m20 * rm22;
        dest.m21 = m01 * rm20 + m11 * rm21 + m21 * rm22;
        dest.m22 = m02 * rm20 + m12 * rm21 + m22 * rm22;
        // set the rest of the matrix elements
        dest.m00 = nm00;
        dest.m01 = nm01;
        dest.m02 = nm02;
        dest.m10 = nm10;
        dest.m11 = nm11;
        dest.m12 = nm12;

        return dest;
    }

    /**
     * Apply a rotation transformation to this matrix to make <code>-z</code> point along <code>dir</code>. 
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>L</code> the lookalong rotation matrix,
     * then the new matrix will be <code>M * L</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * L * v</code>, the
     * lookalong rotation transformation will be applied first!
     * <p>
     * In order to set the matrix to a lookalong transformation without post-multiplying it,
     * use {@link #setLookAlong(double, double, double, double, double, double) setLookAlong()}
     * 
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
    public Matrix3d lookAlong(double dirX, double dirY, double dirZ,
                              double upX, double upY, double upZ) {
        return lookAlong(dirX, dirY, dirZ, upX, upY, upZ, this);
    }

    /**
     * Set this matrix to a rotation transformation to make <code>-z</code>
     * point along <code>dir</code>.
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
    public Matrix3d setLookAlong(Vector3d dir, Vector3d up) {
        return setLookAlong(dir.x, dir.y, dir.z, up.x, up.y, up.z);
    }

    /**
     * Set this matrix to a rotation transformation to make <code>-z</code>
     * point along <code>dir</code>.
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
    public Matrix3d setLookAlong(double dirX, double dirY, double dirZ,
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
        m10 = rightY;
        m11 = upnY;
        m12 = -dirnY;
        m20 = rightZ;
        m21 = upnZ;
        m22 = -dirnZ;

        return this;
    }

    /**
     * Get the scaling factors of <code>this</code> matrix for the three base axes.
     * 
     * @param dest
     *          will hold the scaling factors for <tt>x</tt>, <tt>y</tt> and <tt>z</tt>
     * @return dest
     */
    public Vector3d getScale(Vector3d dest) {
        dest.x = Math.sqrt(m00 * m00 + m01 * m01 + m02 * m02);
        dest.y = Math.sqrt(m10 * m10 + m11 * m11 + m12 * m12);
        dest.z = Math.sqrt(m20 * m20 + m21 * m21 + m22 * m22);
        return dest;
    }

    /**
     * Obtain the direction of <tt>+Z</tt> before the transformation represented by <code>this</code> matrix is applied.
     * <p>
     * This method is equivalent to the following code:
     * <pre>
     * Matrix3d inv = new Matrix3d(this).invert();
     * inv.transform(dir.set(0, 0, 1)).normalize();
     * </pre>
     * If <code>this</code> is already an orthogonal matrix, then consider using {@link #normalizedPositiveZ(Vector3d)} instead.
     * <p>
     * Reference: <a href="http://www.euclideanspace.com/maths/algebra/matrix/functions/inverse/threeD/">http://www.euclideanspace.com</a>
     * 
     * @param dir
     *          will hold the direction of <tt>+Z</tt>
     * @return dir
     */
    public Vector3d positiveZ(Vector3d dir) {
        dir.x = m10 * m21 - m11 * m20;
        dir.y = m20 * m01 - m21 * m00;
        dir.z = m00 * m11 - m01 * m10;
        dir.normalize();
        return dir;
    }

    /**
     * Obtain the direction of <tt>+Z</tt> before the transformation represented by <code>this</code> <i>orthogonal</i> matrix is applied.
     * This method only produces correct results if <code>this</code> is an <i>orthogonal</i> matrix.
     * <p>
     * This method is equivalent to the following code:
     * <pre>
     * Matrix3d inv = new Matrix3d(this).transpose();
     * inv.transform(dir.set(0, 0, 1)).normalize();
     * </pre>
     * <p>
     * Reference: <a href="http://www.euclideanspace.com/maths/algebra/matrix/functions/inverse/threeD/">http://www.euclideanspace.com</a>
     * 
     * @param dir
     *          will hold the direction of <tt>+Z</tt>
     * @return dir
     */
    public Vector3d normalizedPositiveZ(Vector3d dir) {
        dir.x = m02;
        dir.y = m12;
        dir.z = m22;
        return dir;
    }

    /**
     * Obtain the direction of <tt>+X</tt> before the transformation represented by <code>this</code> matrix is applied.
     * <p>
     * This method is equivalent to the following code:
     * <pre>
     * Matrix3d inv = new Matrix3d(this).invert();
     * inv.transform(dir.set(1, 0, 0)).normalize();
     * </pre>
     * If <code>this</code> is already an orthogonal matrix, then consider using {@link #normalizedPositiveX(Vector3d)} instead.
     * <p>
     * Reference: <a href="http://www.euclideanspace.com/maths/algebra/matrix/functions/inverse/threeD/">http://www.euclideanspace.com</a>
     * 
     * @param dir
     *          will hold the direction of <tt>+X</tt>
     * @return dir
     */
    public Vector3d positiveX(Vector3d dir) {
        dir.x = m11 * m22 - m12 * m21;
        dir.y = m02 * m21 - m01 * m22;
        dir.z = m01 * m12 - m02 * m11;
        dir.normalize();
        return dir;
    }

    /**
     * Obtain the direction of <tt>+X</tt> before the transformation represented by <code>this</code> <i>orthogonal</i> matrix is applied.
     * This method only produces correct results if <code>this</code> is an <i>orthogonal</i> matrix.
     * <p>
     * This method is equivalent to the following code:
     * <pre>
     * Matrix3d inv = new Matrix3d(this).transpose();
     * inv.transform(dir.set(1, 0, 0)).normalize();
     * </pre>
     * <p>
     * Reference: <a href="http://www.euclideanspace.com/maths/algebra/matrix/functions/inverse/threeD/">http://www.euclideanspace.com</a>
     * 
     * @param dir
     *          will hold the direction of <tt>+X</tt>
     * @return dir
     */
    public Vector3d normalizedPositiveX(Vector3d dir) {
        dir.x = m00;
        dir.y = m10;
        dir.z = m20;
        return dir;
    }

    /**
     * Obtain the direction of <tt>+Y</tt> before the transformation represented by <code>this</code> matrix is applied.
     * <p>
     * This method is equivalent to the following code:
     * <pre>
     * Matrix3d inv = new Matrix3d(this).invert();
     * inv.transform(dir.set(0, 1, 0)).normalize();
     * </pre>
     * If <code>this</code> is already an orthogonal matrix, then consider using {@link #normalizedPositiveY(Vector3d)} instead.
     * <p>
     * Reference: <a href="http://www.euclideanspace.com/maths/algebra/matrix/functions/inverse/threeD/">http://www.euclideanspace.com</a>
     * 
     * @param dir
     *          will hold the direction of <tt>+Y</tt>
     * @return dir
     */
    public Vector3d positiveY(Vector3d dir) {
        dir.x = m12 * m20 - m10 * m22;
        dir.y = m00 * m22 - m02 * m20;
        dir.z = m02 * m10 - m00 * m12;
        dir.normalize();
        return dir;
    }

    /**
     * Obtain the direction of <tt>+Y</tt> before the transformation represented by <code>this</code> <i>orthogonal</i> matrix is applied.
     * This method only produces correct results if <code>this</code> is an <i>orthogonal</i> matrix.
     * <p>
     * This method is equivalent to the following code:
     * <pre>
     * Matrix3d inv = new Matrix3d(this).transpose();
     * inv.transform(dir.set(0, 1, 0)).normalize();
     * </pre>
     * <p>
     * Reference: <a href="http://www.euclideanspace.com/maths/algebra/matrix/functions/inverse/threeD/">http://www.euclideanspace.com</a>
     * 
     * @param dir
     *          will hold the direction of <tt>+Y</tt>
     * @return dir
     */
    public Vector3d normalizedPositiveY(Vector3d dir) {
        dir.x = m01;
        dir.y = m11;
        dir.z = m21;
        return dir;
    }

    public int hashCode() {
        final int prime = 31;
        int result = 1;
        long temp;
        temp = Double.doubleToLongBits(m00);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(m01);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(m02);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(m10);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(m11);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(m12);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(m20);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(m21);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(m22);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Matrix3d other = (Matrix3d) obj;
        if (Double.doubleToLongBits(m00) != Double.doubleToLongBits(other.m00))
            return false;
        if (Double.doubleToLongBits(m01) != Double.doubleToLongBits(other.m01))
            return false;
        if (Double.doubleToLongBits(m02) != Double.doubleToLongBits(other.m02))
            return false;
        if (Double.doubleToLongBits(m10) != Double.doubleToLongBits(other.m10))
            return false;
        if (Double.doubleToLongBits(m11) != Double.doubleToLongBits(other.m11))
            return false;
        if (Double.doubleToLongBits(m12) != Double.doubleToLongBits(other.m12))
            return false;
        if (Double.doubleToLongBits(m20) != Double.doubleToLongBits(other.m20))
            return false;
        if (Double.doubleToLongBits(m21) != Double.doubleToLongBits(other.m21))
            return false;
        if (Double.doubleToLongBits(m22) != Double.doubleToLongBits(other.m22))
            return false;
        return true;
    }

    /**
     * Exchange the values of <code>this</code> matrix with the given <code>other</code> matrix.
     * 
     * @param other
     *          the other matrix to exchange the values with
     * @return this
     */
    public Matrix3d swap(Matrix3d other) {
        double tmp;
        tmp = m00; m00 = other.m00; other.m00 = tmp;
        tmp = m01; m01 = other.m01; other.m01 = tmp;
        tmp = m02; m02 = other.m02; other.m02 = tmp;
        tmp = m10; m10 = other.m10; other.m10 = tmp;
        tmp = m11; m11 = other.m11; other.m11 = tmp;
        tmp = m12; m12 = other.m12; other.m12 = tmp;
        tmp = m20; m20 = other.m20; other.m20 = tmp;
        tmp = m21; m21 = other.m21; other.m21 = tmp;
        tmp = m22; m22 = other.m22; other.m22 = tmp;
        return this;
    }

}
