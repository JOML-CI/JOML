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
 *      ms[M00]  ms[M10]  ms[M20]<br>
 *      ms[M01]  ms[M11]  ms[M21]<br>
 *      ms[M02]  ms[M12]  ms[M22]<br>
 * 
 * @author Richard Greenlees
 * @author Kai Burjack
 */
public class Matrix3d implements Externalizable {

    private static final long serialVersionUID = 1L;

    public static final int M00 = 0;
    public static final int M01 = 1;
    public static final int M02 = 2;
    public static final int M10 = 3;
    public static final int M11 = 4;
    public static final int M12 = 5;
    public static final int M20 = 6;
    public static final int M21 = 7;
    public static final int M22 = 8;

    /**
     * The components of this matrix stored in column-major order.
     */
    public final double[] ms = new double[9];

    /**
     * Create a new {@link Matrix3d} and initialize it to {@link #identity() identity}.
     */
    public Matrix3d() {
        ms[M00] = 1.0;
        ms[M01] = 0.0;
        ms[M02] = 0.0;
        ms[M10] = 0.0;
        ms[M11] = 1.0;
        ms[M12] = 0.0;
        ms[M20] = 0.0;
        ms[M21] = 0.0;
        ms[M22] = 1.0;
    }

    /**
     * Create a new {@link Matrix3d} and initialize it with the values from the given matrix.
     * 
     * @param mat
     *          the matrix to initialize this matrix with
     */
    public Matrix3d(Matrix3d mat) {
        ms[M00] = mat.ms[M00];
        ms[M01] = mat.ms[M01];
        ms[M02] = mat.ms[M02];
        ms[M10] = mat.ms[M10];
        ms[M11] = mat.ms[M11];
        ms[M12] = mat.ms[M12];
        ms[M20] = mat.ms[M20];
        ms[M21] = mat.ms[M21];
        ms[M22] = mat.ms[M22];
    }

    /**
     * Create a new {@link Matrix3d} and initialize it with the values from the given matrix.
     * 
     * @param mat
     *          the matrix to initialize this matrix with
     */
    public Matrix3d(Matrix3f mat) {
        ms[M00] = mat.ms[Matrix3f.M00];
        ms[M01] = mat.ms[Matrix3f.M01];
        ms[M02] = mat.ms[Matrix3f.M02];
        ms[M10] = mat.ms[Matrix3f.M10];
        ms[M11] = mat.ms[Matrix3f.M11];
        ms[M12] = mat.ms[Matrix3f.M12];
        ms[M20] = mat.ms[Matrix3f.M20];
        ms[M21] = mat.ms[Matrix3f.M21];
        ms[M22] = mat.ms[Matrix3f.M22];
    }

    /**
     * Create a new {@link Matrix3d} and make it a copy of the upper left 3x3 of the given {@link Matrix4f}.
     *
     * @param mat
     *          the {@link Matrix4f} to copy the values from
     */
    public Matrix3d(Matrix4f mat) {
        ms[M00] = mat.ms[Matrix4f.M00];
        ms[M01] = mat.ms[Matrix4f.M01];
        ms[M02] = mat.ms[Matrix4f.M02];
        ms[M10] = mat.ms[Matrix4f.M10];
        ms[M11] = mat.ms[Matrix4f.M11];
        ms[M12] = mat.ms[Matrix4f.M12];
        ms[M20] = mat.ms[Matrix4f.M20];
        ms[M21] = mat.ms[Matrix4f.M21];
        ms[M22] = mat.ms[Matrix4f.M22];
    }

    /**
     * Create a new {@link Matrix3d} and make it a copy of the upper left 3x3 of the given {@link Matrix4d}.
     *
     * @param mat
     *          the {@link Matrix4d} to copy the values from
     */
    public Matrix3d(Matrix4d mat) {
        ms[M00] = mat.ms[Matrix4d.M00];
        ms[M01] = mat.ms[Matrix4d.M01];
        ms[M02] = mat.ms[Matrix4d.M02];
        ms[M10] = mat.ms[Matrix4d.M10];
        ms[M11] = mat.ms[Matrix4d.M11];
        ms[M12] = mat.ms[Matrix4d.M12];
        ms[M20] = mat.ms[Matrix4d.M20];
        ms[M21] = mat.ms[Matrix4d.M21];
        ms[M22] = mat.ms[Matrix4d.M22];
    }

    /**
     * Create a new 3x3 matrix using the supplied float values. The order of the parameter is column-major, 
     * so the first three parameters specify the three elements of the first column.
     * 
     * @param m00
     *          the value of ms[M00]
     * @param m01
     *          the value of ms[M01]
     * @param m02
     *          the value of ms[M02]
     * @param m10
     *          the value of ms[M10]
     * @param m11
     *          the value of ms[M11]
     * @param m12
     *          the value of ms[M12]
     * @param m20
     *          the value of ms[M20]
     * @param m21
     *          the value of ms[M21]
     * @param m22
     *          the value of ms[M22]
     */
    public Matrix3d(double m00, double m01, double m02,
                    double m10, double m11, double m12, 
                    double m20, double m21, double m22) {
        ms[M00] = m00;
        ms[M01] = m01;
        ms[M02] = m02;
        ms[M10] = m10;
        ms[M11] = m11;
        ms[M12] = m12;
        ms[M20] = m20;
        ms[M21] = m21;
        ms[M22] = m22;
    }

    /**
     * Return the value of the matrix element at column 0 and row 0.
     * 
     * @return the value of the matrix element
     */
    public double m00() {
        return ms[M00];
    }
    /**
     * Return the value of the matrix element at column 0 and row 1.
     * 
     * @return the value of the matrix element
     */
    public double m01() {
        return ms[M01];
    }
    /**
     * Return the value of the matrix element at column 0 and row 2.
     * 
     * @return the value of the matrix element
     */
    public double m02() {
        return ms[M02];
    }
    /**
     * Return the value of the matrix element at column 1 and row 0.
     * 
     * @return the value of the matrix element
     */
    public double m10() {
        return ms[M10];
    }
    /**
     * Return the value of the matrix element at column 1 and row 1.
     * 
     * @return the value of the matrix element
     */
    public double m11() {
        return ms[M11];
    }
    /**
     * Return the value of the matrix element at column 1 and row 2.
     * 
     * @return the value of the matrix element
     */
    public double m12() {
        return ms[M12];
    }
    /**
     * Return the value of the matrix element at column 2 and row 0.
     * 
     * @return the value of the matrix element
     */
    public double m20() {
        return ms[M20];
    }
    /**
     * Return the value of the matrix element at column 2 and row 1.
     * 
     * @return the value of the matrix element
     */
    public double m21() {
        return ms[M21];
    }
    /**
     * Return the value of the matrix element at column 2 and row 2.
     * 
     * @return the value of the matrix element
     */
    public double m22() {
        return ms[M22];
    }

    /**
     * Set the value of the matrix element at column 0 and row 0
     * 
     * @param m00
     *          the new value
     * @return the value of the matrix element
     */
    public Matrix3d m00(double m00) {
        this.ms[M00] = m00;
        return this;
    }
    /**
     * Set the value of the matrix element at column 0 and row 1
     * 
     * @param m01
     *          the new value
     * @return the value of the matrix element
     */
    public Matrix3d m01(double m01) {
        this.ms[M01] = m01;
        return this;
    }
    /**
     * Set the value of the matrix element at column 0 and row 2
     * 
     * @param m02
     *          the new value
     * @return the value of the matrix element
     */
    public Matrix3d m02(double m02) {
        this.ms[M02] = m02;
        return this;
    }
    /**
     * Set the value of the matrix element at column 1 and row 0
     * 
     * @param m10
     *          the new value
     * @return the value of the matrix element
     */
    public Matrix3d m10(double m10) {
        this.ms[M10] = m10;
        return this;
    }
    /**
     * Set the value of the matrix element at column 1 and row 1
     * 
     * @param m11
     *          the new value
     * @return the value of the matrix element
     */
    public Matrix3d m11(double m11) {
        this.ms[M11] = m11;
        return this;
    }
    /**
     * Set the value of the matrix element at column 1 and row 2
     * 
     * @param m12
     *          the new value
     * @return the value of the matrix element
     */
    public Matrix3d m12(double m12) {
        this.ms[M12] = m12;
        return this;
    }
    /**
     * Set the value of the matrix element at column 2 and row 0
     * 
     * @param m20
     *          the new value
     * @return the value of the matrix element
     */
    public Matrix3d m20(double m20) {
        this.ms[M20] = m20;
        return this;
    }
    /**
     * Set the value of the matrix element at column 2 and row 1
     * 
     * @param m21
     *          the new value
     * @return the value of the matrix element
     */
    public Matrix3d m21(double m21) {
        this.ms[M21] = m21;
        return this;
    }
    /**
     * Set the value of the matrix element at column 2 and row 2
     * 
     * @param m22
     *          the new value
     * @return the value of the matrix element
     */
    public Matrix3d m22(double m22) {
        this.ms[M22] = m22;
        return this;
    }

    /**
     * Set the values in this matrix to the ones in m.
     * 
     * @param m
     *          the matrix whose values will be copied
     * @return this
     */
    public Matrix3d set(Matrix3d m) {
        ms[M00] = m.ms[M00];
        ms[M01] = m.ms[M01];
        ms[M02] = m.ms[M02];
        ms[M10] = m.ms[M10];
        ms[M11] = m.ms[M11];
        ms[M12] = m.ms[M12];
        ms[M20] = m.ms[M20];
        ms[M21] = m.ms[M21];
        ms[M22] = m.ms[M22];
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
        ms[M00] = m.ms[Matrix3f.M00];
        ms[M01] = m.ms[Matrix3f.M01];
        ms[M02] = m.ms[Matrix3f.M02];
        ms[M10] = m.ms[Matrix3f.M10];
        ms[M11] = m.ms[Matrix3f.M11];
        ms[M12] = m.ms[Matrix3f.M12];
        ms[M20] = m.ms[Matrix3f.M20];
        ms[M21] = m.ms[Matrix3f.M21];
        ms[M22] = m.ms[Matrix3f.M22];
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
        ms[M00] = mat.ms[Matrix4f.M00];
        ms[M01] = mat.ms[Matrix4f.M01];
        ms[M02] = mat.ms[Matrix4f.M02];
        ms[M10] = mat.ms[Matrix4f.M10];
        ms[M11] = mat.ms[Matrix4f.M11];
        ms[M12] = mat.ms[Matrix4f.M12];
        ms[M20] = mat.ms[Matrix4f.M20];
        ms[M21] = mat.ms[Matrix4f.M21];
        ms[M22] = mat.ms[Matrix4f.M22];
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
        ms[M00] = mat.ms[Matrix4d.M00];
        ms[M01] = mat.ms[Matrix4d.M01];
        ms[M02] = mat.ms[Matrix4d.M02];
        ms[M10] = mat.ms[Matrix4d.M10];
        ms[M11] = mat.ms[Matrix4d.M11];
        ms[M12] = mat.ms[Matrix4d.M12];
        ms[M20] = mat.ms[Matrix4d.M20];
        ms[M21] = mat.ms[Matrix4d.M21];
        ms[M22] = mat.ms[Matrix4d.M22];
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
        ms[M00] = c + x*x*omc;
        ms[M11] = c + y*y*omc;
        ms[M22] = c + z*z*omc;
        double tmp1 = x*y*omc;
        double tmp2 = z*s;
        ms[M10] = tmp1 - tmp2;
        ms[M01] = tmp1 + tmp2;
        tmp1 = x*z*omc;
        tmp2 = y*s;
        ms[M20] = tmp1 + tmp2;
        ms[M02] = tmp1 - tmp2;
        tmp1 = y*z*omc;
        tmp2 = x*s;
        ms[M21] = tmp1 - tmp2;
        ms[M12] = tmp1 + tmp2;
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
        ms[M00] = c + x*x*omc;
        ms[M11] = c + y*y*omc;
        ms[M22] = c + z*z*omc;
        double tmp1 = x*y*omc;
        double tmp2 = z*s;
        ms[M10] = tmp1 - tmp2;
        ms[M01] = tmp1 + tmp2;
        tmp1 = x*z*omc;
        tmp2 = y*s;
        ms[M20] = tmp1 + tmp2;
        ms[M02] = tmp1 - tmp2;
        tmp1 = y*z*omc;
        tmp2 = x*s;
        ms[M21] = tmp1 - tmp2;
        ms[M12] = tmp1 + tmp2;
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
        dest.set(ms[M00] * right.ms[M00] + ms[M10] * right.ms[M01] + ms[M20] * right.ms[M02],
                 ms[M01] * right.ms[M00] + ms[M11] * right.ms[M01] + ms[M21] * right.ms[M02],
                 ms[M02] * right.ms[M00] + ms[M12] * right.ms[M01] + ms[M22] * right.ms[M02],
                 ms[M00] * right.ms[M10] + ms[M10] * right.ms[M11] + ms[M20] * right.ms[M12],
                 ms[M01] * right.ms[M10] + ms[M11] * right.ms[M11] + ms[M21] * right.ms[M12],
                 ms[M02] * right.ms[M10] + ms[M12] * right.ms[M11] + ms[M22] * right.ms[M12],
                 ms[M00] * right.ms[M20] + ms[M10] * right.ms[M21] + ms[M20] * right.ms[M22],
                 ms[M01] * right.ms[M20] + ms[M11] * right.ms[M21] + ms[M21] * right.ms[M22],
                 ms[M02] * right.ms[M20] + ms[M12] * right.ms[M21] + ms[M22] * right.ms[M22] );
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
        dest.set(ms[M00] * right.ms[Matrix3f.M00] + ms[M10] * right.ms[Matrix3f.M01] + ms[M20] * right.ms[Matrix3f.M02],
                 ms[M01] * right.ms[Matrix3f.M00] + ms[M11] * right.ms[Matrix3f.M01] + ms[M21] * right.ms[Matrix3f.M02],
                 ms[M02] * right.ms[Matrix3f.M00] + ms[M12] * right.ms[Matrix3f.M01] + ms[M22] * right.ms[Matrix3f.M02],
                 ms[M00] * right.ms[Matrix3f.M10] + ms[M10] * right.ms[Matrix3f.M11] + ms[M20] * right.ms[Matrix3f.M12],
                 ms[M01] * right.ms[Matrix3f.M10] + ms[M11] * right.ms[Matrix3f.M11] + ms[M21] * right.ms[Matrix3f.M12],
                 ms[M02] * right.ms[Matrix3f.M10] + ms[M12] * right.ms[Matrix3f.M11] + ms[M22] * right.ms[Matrix3f.M12],
                 ms[M00] * right.ms[Matrix3f.M20] + ms[M10] * right.ms[Matrix3f.M21] + ms[M20] * right.ms[Matrix3f.M22],
                 ms[M01] * right.ms[Matrix3f.M20] + ms[M11] * right.ms[Matrix3f.M21] + ms[M21] * right.ms[Matrix3f.M22],
                 ms[M02] * right.ms[Matrix3f.M20] + ms[M12] * right.ms[Matrix3f.M21] + ms[M22] * right.ms[Matrix3f.M22]);
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
        dest.set(left.ms[Matrix3f.M00] * right.ms[M00] + left.ms[Matrix3f.M10] * right.ms[M01] + left.ms[Matrix3f.M20] * right.ms[M02],
                 left.ms[Matrix3f.M01] * right.ms[M00] + left.ms[Matrix3f.M11] * right.ms[M01] + left.ms[Matrix3f.M21] * right.ms[M02],
                 left.ms[Matrix3f.M02] * right.ms[M00] + left.ms[Matrix3f.M12] * right.ms[M01] + left.ms[Matrix3f.M22] * right.ms[M02],
                 left.ms[Matrix3f.M00] * right.ms[M10] + left.ms[Matrix3f.M10] * right.ms[M11] + left.ms[Matrix3f.M20] * right.ms[M12],
                 left.ms[Matrix3f.M01] * right.ms[M10] + left.ms[Matrix3f.M11] * right.ms[M11] + left.ms[Matrix3f.M21] * right.ms[M12],
                 left.ms[Matrix3f.M02] * right.ms[M10] + left.ms[Matrix3f.M12] * right.ms[M11] + left.ms[Matrix3f.M22] * right.ms[M12],
                 left.ms[Matrix3f.M00] * right.ms[M20] + left.ms[Matrix3f.M10] * right.ms[M21] + left.ms[Matrix3f.M20] * right.ms[M22],
                 left.ms[Matrix3f.M01] * right.ms[M20] + left.ms[Matrix3f.M11] * right.ms[M21] + left.ms[Matrix3f.M21] * right.ms[M22],
                 left.ms[Matrix3f.M02] * right.ms[M20] + left.ms[Matrix3f.M12] * right.ms[M21] + left.ms[Matrix3f.M22] * right.ms[M22] );
    }

    /**
     * Set the values within this matrix to the supplied float values. The result looks like this:
     * 
     * @param m00
     *          the new value of ms[M00]
     * @param m01
     *          the new value of ms[M01]
     * @param m02
     *          the new value of ms[M02]
     * @param m10
     *          the new value of ms[M10]
     * @param m11
     *          the new value of ms[M11]
     * @param m12
     *          the new value of ms[M12]
     * @param m20
     *          the new value of ms[M20]
     * @param m21
     *          the new value of ms[M21]
     * @param m22
     *          the new value of ms[M22]
     * @return this
     */
    public Matrix3d set(double m00, double m01, double m02,
                        double m10, double m11, double m12, 
                        double m20, double m21, double m22) {
        ms[M00] = m00;
        ms[M01] = m01;
        ms[M02] = m02;
        ms[M10] = m10;
        ms[M11] = m11;
        ms[M12] = m12;
        ms[M20] = m20;
        ms[M21] = m21;
        ms[M22] = m22;
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
        ms[M00] = m[0];
        ms[M01] = m[1];
        ms[M02] = m[2];
        ms[M10] = m[3];
        ms[M11] = m[4];
        ms[M12] = m[5];
        ms[M20] = m[6];
        ms[M21] = m[7];
        ms[M22] = m[8];
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
        ms[M00] = m[0];
        ms[M01] = m[1];
        ms[M02] = m[2];
        ms[M10] = m[3];
        ms[M11] = m[4];
        ms[M12] = m[5];
        ms[M20] = m[6];
        ms[M21] = m[7];
        ms[M22] = m[8];
        return this;
    }

    /**
     * Return the determinant of this matrix.
     * 
     * @return the determinant
     */
    public double determinant() {
        return (ms[M00] * ms[M11] - ms[M01] * ms[M10]) * ms[M22]
             + (ms[M02] * ms[M10] - ms[M00] * ms[M12]) * ms[M21]
             + (ms[M01] * ms[M12] - ms[M02] * ms[M11]) * ms[M20];
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
        dest.set((ms[M11] * ms[M22] - ms[M21] * ms[M12]) * s,
                 (ms[M21] * ms[M02] - ms[M01] * ms[M22]) * s,
                 (ms[M01] * ms[M12] - ms[M11] * ms[M02]) * s,
                 (ms[M20] * ms[M12] - ms[M10] * ms[M22]) * s,
                 (ms[M00] * ms[M22] - ms[M20] * ms[M02]) * s,
                 (ms[M10] * ms[M02] - ms[M00] * ms[M12]) * s,
                 (ms[M10] * ms[M21] - ms[M20] * ms[M11]) * s,
                 (ms[M20] * ms[M01] - ms[M00] * ms[M21]) * s,
                 (ms[M00] * ms[M11] - ms[M10] * ms[M01]) * s);
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
        dest.set(ms[M00], ms[M10], ms[M20],
                 ms[M01], ms[M11], ms[M21],
                 ms[M02], ms[M12], ms[M22]);
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
        return formatter.format(ms[M00]) + formatter.format(ms[M10]) + formatter.format(ms[M20]) + "\n" //$NON-NLS-1$
             + formatter.format(ms[M01]) + formatter.format(ms[M11]) + formatter.format(ms[M21]) + "\n" //$NON-NLS-1$
             + formatter.format(ms[M02]) + formatter.format(ms[M12]) + formatter.format(ms[M22]) + "\n"; //$NON-NLS-1$
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
        buffer.put(index,   ms[M00]);
        buffer.put(index+1, ms[M01]);
        buffer.put(index+2, ms[M02]);
        buffer.put(index+3, ms[M10]);
        buffer.put(index+4, ms[M11]);
        buffer.put(index+5, ms[M12]);
        buffer.put(index+6, ms[M20]);
        buffer.put(index+7, ms[M21]);
        buffer.put(index+8, ms[M22]);
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
        buffer.put(index,   (float) ms[M00]);
        buffer.put(index+1, (float) ms[M01]);
        buffer.put(index+2, (float) ms[M02]);
        buffer.put(index+3, (float) ms[M10]);
        buffer.put(index+4, (float) ms[M11]);
        buffer.put(index+5, (float) ms[M12]);
        buffer.put(index+6, (float) ms[M20]);
        buffer.put(index+7, (float) ms[M21]);
        buffer.put(index+8, (float) ms[M22]);
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
        buffer.putDouble(index+8*0, ms[M00]);
        buffer.putDouble(index+8*1, ms[M01]);
        buffer.putDouble(index+8*2, ms[M02]);
        buffer.putDouble(index+8*3, ms[M10]);
        buffer.putDouble(index+8*4, ms[M11]);
        buffer.putDouble(index+8*5, ms[M12]);
        buffer.putDouble(index+8*6, ms[M20]);
        buffer.putDouble(index+8*7, ms[M21]);
        buffer.putDouble(index+8*8, ms[M22]);
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
        buffer.putFloat(index+4*0, (float)ms[M00]);
        buffer.putFloat(index+4*1, (float)ms[M01]);
        buffer.putFloat(index+4*2, (float)ms[M02]);
        buffer.putFloat(index+4*3, (float)ms[M10]);
        buffer.putFloat(index+4*4, (float)ms[M11]);
        buffer.putFloat(index+4*5, (float)ms[M12]);
        buffer.putFloat(index+4*6, (float)ms[M20]);
        buffer.putFloat(index+4*7, (float)ms[M21]);
        buffer.putFloat(index+4*8, (float)ms[M22]);
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
        arr[offset+0] = ms[M00];
        arr[offset+1] = ms[M01];
        arr[offset+2] = ms[M02];
        arr[offset+3] = ms[M10];
        arr[offset+4] = ms[M11];
        arr[offset+5] = ms[M12];
        arr[offset+6] = ms[M20];
        arr[offset+7] = ms[M21];
        arr[offset+8] = ms[M22];
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
        arr[offset+0] = (float)ms[M00];
        arr[offset+1] = (float)ms[M01];
        arr[offset+2] = (float)ms[M02];
        arr[offset+3] = (float)ms[M10];
        arr[offset+4] = (float)ms[M11];
        arr[offset+5] = (float)ms[M12];
        arr[offset+6] = (float)ms[M20];
        arr[offset+7] = (float)ms[M21];
        arr[offset+8] = (float)ms[M22];
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
        ms[M00] = buffer.get(pos);
        ms[M01] = buffer.get(pos+1);
        ms[M02] = buffer.get(pos+2);
        ms[M10] = buffer.get(pos+3);
        ms[M11] = buffer.get(pos+4);
        ms[M12] = buffer.get(pos+5);
        ms[M20] = buffer.get(pos+6);
        ms[M21] = buffer.get(pos+7);
        ms[M22] = buffer.get(pos+8);
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
        ms[M00] = buffer.get(pos);
        ms[M01] = buffer.get(pos+1);
        ms[M02] = buffer.get(pos+2);
        ms[M10] = buffer.get(pos+3);
        ms[M11] = buffer.get(pos+4);
        ms[M12] = buffer.get(pos+5);
        ms[M20] = buffer.get(pos+6);
        ms[M21] = buffer.get(pos+7);
        ms[M22] = buffer.get(pos+8);
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
        ms[M00] = buffer.getDouble(pos);
        ms[M01] = buffer.getDouble(pos+8*1);
        ms[M02] = buffer.getDouble(pos+8*2);
        ms[M10] = buffer.getDouble(pos+8*3);
        ms[M11] = buffer.getDouble(pos+8*4);
        ms[M12] = buffer.getDouble(pos+8*5);
        ms[M20] = buffer.getDouble(pos+8*6);
        ms[M21] = buffer.getDouble(pos+8*7);
        ms[M22] = buffer.getDouble(pos+8*8);
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
        ms[M00] = buffer.getFloat(pos);
        ms[M01] = buffer.getFloat(pos+4*1);
        ms[M02] = buffer.getFloat(pos+4*2);
        ms[M10] = buffer.getFloat(pos+4*3);
        ms[M11] = buffer.getFloat(pos+4*4);
        ms[M12] = buffer.getFloat(pos+4*5);
        ms[M20] = buffer.getFloat(pos+4*6);
        ms[M21] = buffer.getFloat(pos+4*7);
        ms[M22] = buffer.getFloat(pos+4*8);
        return this;
    }

    /**
     * Set all the values within this matrix to 0.
     * 
     * @return this
     */
    public Matrix3d zero() {
        ms[M00] = 0.0;
        ms[M01] = 0.0;
        ms[M02] = 0.0;
        ms[M10] = 0.0;
        ms[M11] = 0.0;
        ms[M12] = 0.0;
        ms[M20] = 0.0;
        ms[M21] = 0.0;
        ms[M22] = 0.0;
        return this;
    }
    
    /**
     * Set this matrix to the identity.
     * 
     * @return this
     */
    public Matrix3d identity() {
        ms[M00] = 1.0;
        ms[M01] = 0.0;
        ms[M02] = 0.0;
        ms[M10] = 0.0;
        ms[M11] = 1.0;
        ms[M12] = 0.0;
        ms[M20] = 0.0;
        ms[M21] = 0.0;
        ms[M22] = 1.0;
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
        ms[M00] = factor;
        ms[M01] = 0.0;
        ms[M02] = 0.0;
        ms[M10] = 0.0;
        ms[M11] = factor;
        ms[M12] = 0.0;
        ms[M20] = 0.0;
        ms[M21] = 0.0;
        ms[M22] = factor;
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
        ms[M00] = x;
        ms[M01] = 0.0;
        ms[M02] = 0.0;
        ms[M10] = 0.0;
        ms[M11] = y;
        ms[M12] = 0.0;
        ms[M20] = 0.0;
        ms[M21] = 0.0;
        ms[M22] = z;
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
        ms[M00] = xyz.x;
        ms[M01] = 0.0;
        ms[M02] = 0.0;
        ms[M10] = 0.0;
        ms[M11] = xyz.y;
        ms[M12] = 0.0;
        ms[M20] = 0.0;
        ms[M21] = 0.0;
        ms[M22] = xyz.z;
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
        // ms[M00] = x, ms[M11] = y, ms[M22] = z
        // all others = 0
        dest.ms[M00] = ms[M00] * x;
        dest.ms[M01] = ms[M01] * x;
        dest.ms[M02] = ms[M02] * x;
        dest.ms[M10] = ms[M10] * y;
        dest.ms[M11] = ms[M11] * y;
        dest.ms[M12] = ms[M12] * y;
        dest.ms[M20] = ms[M20] * z;
        dest.ms[M21] = ms[M21] * z;
        dest.ms[M22] = ms[M22] * z;
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
        ms[M00] = cos + x * x * C;
        ms[M10] = xy * C - z * sin;
        ms[M20] = xz * C + y * sin;
        ms[M01] = xy * C + z * sin;
        ms[M11] = cos + y * y * C;
        ms[M21] = yz * C - x * sin;
        ms[M02] = xz * C - y * sin;
        ms[M12] = yz * C + x * sin;
        ms[M22] = cos + z * z * C;
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
        ms[M00] = 1.0;
        ms[M01] = 0.0;
        ms[M02] = 0.0;
        ms[M10] = 0.0;
        ms[M11] = cos;
        ms[M12] = sin;
        ms[M20] = 0.0;
        ms[M21] = -sin;
        ms[M22] = cos;
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
        ms[M00] = cos;
        ms[M01] = 0.0;
        ms[M02] = -sin;
        ms[M10] = 0.0;
        ms[M11] = 1.0;
        ms[M12] = 0.0;
        ms[M20] = sin;
        ms[M21] = 0.0;
        ms[M22] = cos;
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
        ms[M00] = cos;
        ms[M01] = sin;
        ms[M02] = 0.0;
        ms[M10] = -sin;
        ms[M11] = cos;
        ms[M12] = 0.0;
        ms[M20] = 0.0;
        ms[M21] = 0.0;
        ms[M22] = 1.0;
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
        ms[M20] = sinY;
        ms[M21] = nm21 * cosY;
        ms[M22] = nm22 * cosY;
        // rotateZ
        ms[M00] = nm00 * cosZ;
        ms[M01] = nm01 * cosZ + nm11 * sinZ;
        ms[M02] = nm02 * cosZ + nm12 * sinZ;
        ms[M10] = nm00 * m_sinZ;
        ms[M11] = nm01 * m_sinZ + nm11 * cosZ;
        ms[M12] = nm02 * m_sinZ + nm12 * cosZ;
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
        ms[M00] = nm00 * cosY;
        ms[M01] = nm01 * cosY;
        ms[M02] = m_sinY;
        // rotateX
        ms[M10] = nm10 * cosX + nm20 * sinX;
        ms[M11] = nm11 * cosX + nm21 * sinX;
        ms[M12] = nm22 * sinX;
        ms[M20] = nm10 * m_sinX + nm20 * cosX;
        ms[M21] = nm11 * m_sinX + nm21 * cosX;
        ms[M22] = nm22 * cosX;
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
        ms[M20] = nm20 * cosX;
        ms[M21] = m_sinX;
        ms[M22] = nm22 * cosX;
        // rotateZ
        ms[M00] = nm00 * cosZ + nm10 * sinZ;
        ms[M01] = nm11 * sinZ;
        ms[M02] = nm02 * cosZ + nm12 * sinZ;
        ms[M10] = nm00 * m_sinZ + nm10 * cosZ;
        ms[M11] = nm11 * cosZ;
        ms[M12] = nm02 * m_sinZ + nm12 * cosZ;
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

        ms[M00] = 1.0 - q11 - q22;
        ms[M01] = q01 + q23;
        ms[M02] = q02 - q13;
        ms[M10] = q01 - q23;
        ms[M11] = 1.0 - q22 - q00;
        ms[M12] = q12 + q03;
        ms[M20] = q02 + q13;
        ms[M21] = q12 - q03;
        ms[M22] = 1.0 - q11 - q00;

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

        ms[M00] = 1.0 - q11 - q22;
        ms[M01] = q01 + q23;
        ms[M02] = q02 - q13;
        ms[M10] = q01 - q23;
        ms[M11] = 1.0 - q22 - q00;
        ms[M12] = q12 + q03;
        ms[M20] = q02 + q13;
        ms[M21] = q12 - q03;
        ms[M22] = 1.0 - q11 - q00;

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
        out.writeDouble(ms[M00]);
        out.writeDouble(ms[M01]);
        out.writeDouble(ms[M02]);
        out.writeDouble(ms[M10]);
        out.writeDouble(ms[M11]);
        out.writeDouble(ms[M12]);
        out.writeDouble(ms[M20]);
        out.writeDouble(ms[M21]);
        out.writeDouble(ms[M22]);
    }

    public void readExternal(ObjectInput in) throws IOException,
            ClassNotFoundException {
        ms[M00] = in.readDouble();
        ms[M01] = in.readDouble();
        ms[M02] = in.readDouble();
        ms[M10] = in.readDouble();
        ms[M11] = in.readDouble();
        ms[M12] = in.readDouble();
        ms[M20] = in.readDouble();
        ms[M21] = in.readDouble();
        ms[M22] = in.readDouble();
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
        double rn11 = cos;
        double rn21 = -sin;
        double rn12 = sin;
        double rn22 = cos;

        // add temporaries for dependent values
        double nm10 = ms[M10] * rn11 + ms[M20] * rn12;
        double nm11 = ms[M11] * rn11 + ms[M21] * rn12;
        double nm12 = ms[M12] * rn11 + ms[M22] * rn12;
        // set non-dependent values directly
        dest.ms[M20] = ms[M10] * rn21 + ms[M20] * rn22;
        dest.ms[M21] = ms[M11] * rn21 + ms[M21] * rn22;
        dest.ms[M22] = ms[M12] * rn21 + ms[M22] * rn22;
        // set other values
        dest.ms[M10] = nm10;
        dest.ms[M11] = nm11;
        dest.ms[M12] = nm12;
        dest.ms[M00] = ms[M00];
        dest.ms[M01] = ms[M01];
        dest.ms[M02] = ms[M02];

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
        double rn00 = cos;
        double rn20 = sin;
        double rn02 = -sin;
        double rn22 = cos;

        // add temporaries for dependent values
        double nm00 = ms[M00] * rn00 + ms[M20] * rn02;
        double nm01 = ms[M01] * rn00 + ms[M21] * rn02;
        double nm02 = ms[M02] * rn00 + ms[M22] * rn02;
        // set non-dependent values directly
        dest.ms[M20] = ms[M00] * rn20 + ms[M20] * rn22;
        dest.ms[M21] = ms[M01] * rn20 + ms[M21] * rn22;
        dest.ms[M22] = ms[M02] * rn20 + ms[M22] * rn22;
        // set other values
        dest.ms[M00] = nm00;
        dest.ms[M01] = nm01;
        dest.ms[M02] = nm02;
        dest.ms[M10] = ms[M10];
        dest.ms[M11] = ms[M11];
        dest.ms[M12] = ms[M12];

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
        double rn00 = cos;
        double rn10 = -sin;
        double rn01 = sin;
        double rn11 = cos;

        // add temporaries for dependent values
        double nm00 = ms[M00] * rn00 + ms[M10] * rn01;
        double nm01 = ms[M01] * rn00 + ms[M11] * rn01;
        double nm02 = ms[M02] * rn00 + ms[M12] * rn01;
        // set non-dependent values directly
        dest.ms[M10] = ms[M00] * rn10 + ms[M10] * rn11;
        dest.ms[M11] = ms[M01] * rn10 + ms[M11] * rn11;
        dest.ms[M12] = ms[M02] * rn10 + ms[M12] * rn11;
        // set other values
        dest.ms[M00] = nm00;
        dest.ms[M01] = nm01;
        dest.ms[M02] = nm02;
        dest.ms[M20] = ms[M20];
        dest.ms[M21] = ms[M21];
        dest.ms[M22] = ms[M22];

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
        double nm10 = ms[M10] * cosX + ms[M20] * sinX;
        double nm11 = ms[M11] * cosX + ms[M21] * sinX;
        double nm12 = ms[M12] * cosX + ms[M22] * sinX;
        double nm20 = ms[M10] * m_sinX + ms[M20] * cosX;
        double nm21 = ms[M11] * m_sinX + ms[M21] * cosX;
        double nm22 = ms[M12] * m_sinX + ms[M22] * cosX;
        // rotateY
        double nm00 = ms[M00] * cosY + nm20 * m_sinY;
        double nm01 = ms[M01] * cosY + nm21 * m_sinY;
        double nm02 = ms[M02] * cosY + nm22 * m_sinY;
        dest.ms[M20] = ms[M00] * sinY + nm20 * cosY;
        dest.ms[M21] = ms[M01] * sinY + nm21 * cosY;
        dest.ms[M22] = ms[M02] * sinY + nm22 * cosY;
        // rotateZ
        dest.ms[M00] = nm00 * cosZ + nm10 * sinZ;
        dest.ms[M01] = nm01 * cosZ + nm11 * sinZ;
        dest.ms[M02] = nm02 * cosZ + nm12 * sinZ;
        dest.ms[M10] = nm00 * m_sinZ + nm10 * cosZ;
        dest.ms[M11] = nm01 * m_sinZ + nm11 * cosZ;
        dest.ms[M12] = nm02 * m_sinZ + nm12 * cosZ;
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
        double nm00 = ms[M00] * cosZ + ms[M10] * sinZ;
        double nm01 = ms[M01] * cosZ + ms[M11] * sinZ;
        double nm02 = ms[M02] * cosZ + ms[M12] * sinZ;
        double nm10 = ms[M00] * m_sinZ + ms[M10] * cosZ;
        double nm11 = ms[M01] * m_sinZ + ms[M11] * cosZ;
        double nm12 = ms[M02] * m_sinZ + ms[M12] * cosZ;
        // rotateY
        double nm20 = nm00 * sinY + ms[M20] * cosY;
        double nm21 = nm01 * sinY + ms[M21] * cosY;
        double nm22 = nm02 * sinY + ms[M22] * cosY;
        dest.ms[M00] = nm00 * cosY + ms[M20] * m_sinY;
        dest.ms[M01] = nm01 * cosY + ms[M21] * m_sinY;
        dest.ms[M02] = nm02 * cosY + ms[M22] * m_sinY;
        // rotateX
        dest.ms[M10] = nm10 * cosX + nm20 * sinX;
        dest.ms[M11] = nm11 * cosX + nm21 * sinX;
        dest.ms[M12] = nm12 * cosX + nm22 * sinX;
        dest.ms[M20] = nm10 * m_sinX + nm20 * cosX;
        dest.ms[M21] = nm11 * m_sinX + nm21 * cosX;
        dest.ms[M22] = nm12 * m_sinX + nm22 * cosX;
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
        // ms[M30], ms[M31], ms[M32], ms[M03], ms[M13], ms[M23] = 0
        double xx = x * x, xy = x * y, xz = x * z;
        double yy = y * y, yz = y * z;
        double zz = z * z;
        double rn00 = xx * C + c;
        double rn01 = xy * C + z * s;
        double rn02 = xz * C - y * s;
        double rn10 = xy * C - z * s;
        double rn11 = yy * C + c;
        double rn12 = yz * C + x * s;
        double rn20 = xz * C + y * s;
        double rn21 = yz * C - x * s;
        double rn22 = zz * C + c;

        // add temporaries for dependent values
        double nm00 = ms[M00] * rn00 + ms[M10] * rn01 + ms[M20] * rn02;
        double nm01 = ms[M01] * rn00 + ms[M11] * rn01 + ms[M21] * rn02;
        double nm02 = ms[M02] * rn00 + ms[M12] * rn01 + ms[M22] * rn02;
        double nm10 = ms[M00] * rn10 + ms[M10] * rn11 + ms[M20] * rn12;
        double nm11 = ms[M01] * rn10 + ms[M11] * rn11 + ms[M21] * rn12;
        double nm12 = ms[M02] * rn10 + ms[M12] * rn11 + ms[M22] * rn12;
        // set non-dependent values directly
        dest.ms[M20] = ms[M00] * rn20 + ms[M10] * rn21 + ms[M20] * rn22;
        dest.ms[M21] = ms[M01] * rn20 + ms[M11] * rn21 + ms[M21] * rn22;
        dest.ms[M22] = ms[M02] * rn20 + ms[M12] * rn21 + ms[M22] * rn22;
        // set other values
        dest.ms[M00] = nm00;
        dest.ms[M01] = nm01;
        dest.ms[M02] = nm02;
        dest.ms[M10] = nm10;
        dest.ms[M11] = nm11;
        dest.ms[M12] = nm12;

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

        double rn00 = 1.0 - q11 - q22;
        double rn01 = q01 + q23;
        double rn02 = q02 - q13;
        double rn10 = q01 - q23;
        double rn11 = 1.0 - q22 - q00;
        double rn12 = q12 + q03;
        double rn20 = q02 + q13;
        double rn21 = q12 - q03;
        double rn22 = 1.0 - q11 - q00;

        double nm00 = ms[M00] * rn00 + ms[M10] * rn01 + ms[M20] * rn02;
        double nm01 = ms[M01] * rn00 + ms[M11] * rn01 + ms[M21] * rn02;
        double nm02 = ms[M02] * rn00 + ms[M12] * rn01 + ms[M22] * rn02;
        double nm10 = ms[M00] * rn10 + ms[M10] * rn11 + ms[M20] * rn12;
        double nm11 = ms[M01] * rn10 + ms[M11] * rn11 + ms[M21] * rn12;
        double nm12 = ms[M02] * rn10 + ms[M12] * rn11 + ms[M22] * rn12;
        dest.ms[M20] = ms[M00] * rn20 + ms[M10] * rn21 + ms[M20] * rn22;
        dest.ms[M21] = ms[M01] * rn20 + ms[M11] * rn21 + ms[M21] * rn22;
        dest.ms[M22] = ms[M02] * rn20 + ms[M12] * rn21 + ms[M22] * rn22;
        dest.ms[M00] = nm00;
        dest.ms[M01] = nm01;
        dest.ms[M02] = nm02;
        dest.ms[M10] = nm10;
        dest.ms[M11] = nm11;
        dest.ms[M12] = nm12;

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

        double rn00 = 1.0 - q11 - q22;
        double rn01 = q01 + q23;
        double rn02 = q02 - q13;
        double rn10 = q01 - q23;
        double rn11 = 1.0 - q22 - q00;
        double rn12 = q12 + q03;
        double rn20 = q02 + q13;
        double rn21 = q12 - q03;
        double rn22 = 1.0 - q11 - q00;

        double nm00 = ms[M00] * rn00 + ms[M10] * rn01 + ms[M20] * rn02;
        double nm01 = ms[M01] * rn00 + ms[M11] * rn01 + ms[M21] * rn02;
        double nm02 = ms[M02] * rn00 + ms[M12] * rn01 + ms[M22] * rn02;
        double nm10 = ms[M00] * rn10 + ms[M10] * rn11 + ms[M20] * rn12;
        double nm11 = ms[M01] * rn10 + ms[M11] * rn11 + ms[M21] * rn12;
        double nm12 = ms[M02] * rn10 + ms[M12] * rn11 + ms[M22] * rn12;
        dest.ms[M20] = ms[M00] * rn20 + ms[M10] * rn21 + ms[M20] * rn22;
        dest.ms[M21] = ms[M01] * rn20 + ms[M11] * rn21 + ms[M21] * rn22;
        dest.ms[M22] = ms[M02] * rn20 + ms[M12] * rn21 + ms[M22] * rn22;
        dest.ms[M00] = nm00;
        dest.ms[M01] = nm01;
        dest.ms[M02] = nm02;
        dest.ms[M10] = nm10;
        dest.ms[M11] = nm11;
        dest.ms[M12] = nm12;

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
            dest.x = ms[M00];
            dest.y = ms[M10];
            dest.z = ms[M20];
            break;
        case 1:
            dest.x = ms[M01];
            dest.y = ms[M11];
            dest.z = ms[M21];
            break;
        case 2:
            dest.x = ms[M02];
            dest.y = ms[M12];
            dest.z = ms[M22];
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
            dest.x = ms[M00];
            dest.y = ms[M01];
            dest.z = ms[M02];
            break;
        case 1:
            dest.x = ms[M10];
            dest.y = ms[M11];
            dest.z = ms[M12];
            break;
        case 2:
            dest.x = ms[M20];
            dest.y = ms[M21];
            dest.z = ms[M22];
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
        dest.set((ms[M11] * ms[M22] - ms[M21] * ms[M12]) * s,
                 (ms[M20] * ms[M12] - ms[M10] * ms[M22]) * s,
                 (ms[M10] * ms[M21] - ms[M20] * ms[M11]) * s,
                 (ms[M21] * ms[M02] - ms[M01] * ms[M22]) * s,
                 (ms[M00] * ms[M22] - ms[M20] * ms[M02]) * s,
                 (ms[M20] * ms[M01] - ms[M00] * ms[M21]) * s,
                 (ms[M01] * ms[M12] - ms[M11] * ms[M02]) * s,
                 (ms[M10] * ms[M02] - ms[M00] * ms[M12]) * s,
                 (ms[M00] * ms[M11] - ms[M10] * ms[M01]) * s);
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
        double rn00 = rightX;
        double rn01 = upnX;
        double rn02 = -dirnX;
        double rn10 = rightY;
        double rn11 = upnY;
        double rn12 = -dirnY;
        double rn20 = rightZ;
        double rn21 = upnZ;
        double rn22 = -dirnZ;

        // perform optimized matrix multiplication
        // introduce temporaries for dependent results
        double nm00 = ms[M00] * rn00 + ms[M10] * rn01 + ms[M20] * rn02;
        double nm01 = ms[M01] * rn00 + ms[M11] * rn01 + ms[M21] * rn02;
        double nm02 = ms[M02] * rn00 + ms[M12] * rn01 + ms[M22] * rn02;
        double nm10 = ms[M00] * rn10 + ms[M10] * rn11 + ms[M20] * rn12;
        double nm11 = ms[M01] * rn10 + ms[M11] * rn11 + ms[M21] * rn12;
        double nm12 = ms[M02] * rn10 + ms[M12] * rn11 + ms[M22] * rn12;
        dest.ms[M20] = ms[M00] * rn20 + ms[M10] * rn21 + ms[M20] * rn22;
        dest.ms[M21] = ms[M01] * rn20 + ms[M11] * rn21 + ms[M21] * rn22;
        dest.ms[M22] = ms[M02] * rn20 + ms[M12] * rn21 + ms[M22] * rn22;
        // set the rest of the matrix elements
        dest.ms[M00] = nm00;
        dest.ms[M01] = nm01;
        dest.ms[M02] = nm02;
        dest.ms[M10] = nm10;
        dest.ms[M11] = nm11;
        dest.ms[M12] = nm12;

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

        ms[M00] = rightX;
        ms[M01] = upnX;
        ms[M02] = -dirnX;
        ms[M10] = rightY;
        ms[M11] = upnY;
        ms[M12] = -dirnY;
        ms[M20] = rightZ;
        ms[M21] = upnZ;
        ms[M22] = -dirnZ;

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
        dest.x = Math.sqrt(ms[M00] * ms[M00] + ms[M01] * ms[M01] + ms[M02] * ms[M02]);
        dest.y = Math.sqrt(ms[M10] * ms[M10] + ms[M11] * ms[M11] + ms[M12] * ms[M12]);
        dest.z = Math.sqrt(ms[M20] * ms[M20] + ms[M21] * ms[M21] + ms[M22] * ms[M22]);
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
        dir.x = ms[M10] * ms[M21] - ms[M11] * ms[M20];
        dir.y = ms[M20] * ms[M01] - ms[M21] * ms[M00];
        dir.z = ms[M00] * ms[M11] - ms[M01] * ms[M10];
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
        dir.x = ms[M02];
        dir.y = ms[M12];
        dir.z = ms[M22];
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
        dir.x = ms[M11] * ms[M22] - ms[M12] * ms[M21];
        dir.y = ms[M02] * ms[M21] - ms[M01] * ms[M22];
        dir.z = ms[M01] * ms[M12] - ms[M02] * ms[M11];
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
        dir.x = ms[M00];
        dir.y = ms[M10];
        dir.z = ms[M20];
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
        dir.x = ms[M12] * ms[M20] - ms[M10] * ms[M22];
        dir.y = ms[M00] * ms[M22] - ms[M02] * ms[M20];
        dir.z = ms[M02] * ms[M10] - ms[M00] * ms[M12];
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
        dir.x = ms[M01];
        dir.y = ms[M11];
        dir.z = ms[M21];
        return dir;
    }

    public int hashCode() {
        final int prime = 31;
        int result = 1;
        long temp;
        temp = Double.doubleToLongBits(ms[M00]);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(ms[M01]);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(ms[M02]);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(ms[M10]);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(ms[M11]);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(ms[M12]);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(ms[M20]);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(ms[M21]);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(ms[M22]);
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
        if (Double.doubleToLongBits(ms[M00]) != Double.doubleToLongBits(other.ms[M00]))
            return false;
        if (Double.doubleToLongBits(ms[M01]) != Double.doubleToLongBits(other.ms[M01]))
            return false;
        if (Double.doubleToLongBits(ms[M02]) != Double.doubleToLongBits(other.ms[M02]))
            return false;
        if (Double.doubleToLongBits(ms[M10]) != Double.doubleToLongBits(other.ms[M10]))
            return false;
        if (Double.doubleToLongBits(ms[M11]) != Double.doubleToLongBits(other.ms[M11]))
            return false;
        if (Double.doubleToLongBits(ms[M12]) != Double.doubleToLongBits(other.ms[M12]))
            return false;
        if (Double.doubleToLongBits(ms[M20]) != Double.doubleToLongBits(other.ms[M20]))
            return false;
        if (Double.doubleToLongBits(ms[M21]) != Double.doubleToLongBits(other.ms[M21]))
            return false;
        if (Double.doubleToLongBits(ms[M22]) != Double.doubleToLongBits(other.ms[M22]))
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
        tmp = ms[M00]; ms[M00] = other.ms[M00]; other.ms[M00] = tmp;
        tmp = ms[M01]; ms[M01] = other.ms[M01]; other.ms[M01] = tmp;
        tmp = ms[M02]; ms[M02] = other.ms[M02]; other.ms[M02] = tmp;
        tmp = ms[M10]; ms[M10] = other.ms[M10]; other.ms[M10] = tmp;
        tmp = ms[M11]; ms[M11] = other.ms[M11]; other.ms[M11] = tmp;
        tmp = ms[M12]; ms[M12] = other.ms[M12]; other.ms[M12] = tmp;
        tmp = ms[M20]; ms[M20] = other.ms[M20]; other.ms[M20] = tmp;
        tmp = ms[M21]; ms[M21] = other.ms[M21]; other.ms[M21] = tmp;
        tmp = ms[M22]; ms[M22] = other.ms[M22]; other.ms[M22] = tmp;
        return this;
    }

}
