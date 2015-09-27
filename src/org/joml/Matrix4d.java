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
import java.nio.ByteBuffer;
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

    /**
     * Argument to the first parameter of {@link #frustumPlane(int, Vector4d)}
     * identifying the plane with equation <tt>x=-1</tt> when using the identity matrix.  
     */
    public static final int PLANE_NX = 0;
    /**
     * Argument to the first parameter of {@link #frustumPlane(int, Vector4d)}
     * identifying the plane with equation <tt>x=1</tt> when using the identity matrix.  
     */
    public static final int PLANE_PX = 1;
    /**
     * Argument to the first parameter of {@link #frustumPlane(int, Vector4d)}
     * identifying the plane with equation <tt>y=-1</tt> when using the identity matrix.  
     */
    public static final int PLANE_NY= 2;
    /**
     * Argument to the first parameter of {@link #frustumPlane(int, Vector4d)}
     * identifying the plane with equation <tt>y=1</tt> when using the identity matrix.  
     */
    public static final int PLANE_PY = 3;
    /**
     * Argument to the first parameter of {@link #frustumPlane(int, Vector4d)}
     * identifying the plane with equation <tt>z=-1</tt> when using the identity matrix.  
     */
    public static final int PLANE_NZ = 4;
    /**
     * Argument to the first parameter of {@link #frustumPlane(int, Vector4d)}
     * identifying the plane with equation <tt>z=1</tt> when using the identity matrix.  
     */
    public static final int PLANE_PZ = 5;

    /**
     * Argument to the first parameter of {@link #frustumCorner(int, Vector3d)}
     * identifying the corner <tt>(-1, -1, -1)</tt> when using the identity matrix.
     */
    public static final int CORNER_NXNYNZ = 0;
    /**
     * Argument to the first parameter of {@link #frustumCorner(int, Vector3d)}
     * identifying the corner <tt>(1, -1, -1)</tt> when using the identity matrix.
     */
    public static final int CORNER_PXNYNZ = 1;
    /**
     * Argument to the first parameter of {@link #frustumCorner(int, Vector3d)}
     * identifying the corner <tt>(1, 1, -1)</tt> when using the identity matrix.
     */
    public static final int CORNER_PXPYNZ = 2;
    /**
     * Argument to the first parameter of {@link #frustumCorner(int, Vector3d)}
     * identifying the corner <tt>(-1, 1, -1)</tt> when using the identity matrix.
     */
    public static final int CORNER_NXPYNZ = 3;
    /**
     * Argument to the first parameter of {@link #frustumCorner(int, Vector3d)}
     * identifying the corner <tt>(1, -1, 1)</tt> when using the identity matrix.
     */
    public static final int CORNER_PXNYPZ = 4;
    /**
     * Argument to the first parameter of {@link #frustumCorner(int, Vector3d)}
     * identifying the corner <tt>(-1, -1, 1)</tt> when using the identity matrix.
     */
    public static final int CORNER_NXNYPZ = 5;
    /**
     * Argument to the first parameter of {@link #frustumCorner(int, Vector3d)}
     * identifying the corner <tt>(-1, 1, 1)</tt> when using the identity matrix.
     */
    public static final int CORNER_NXPYPZ = 6;
    /**
     * Argument to the first parameter of {@link #frustumCorner(int, Vector3d)}
     * identifying the corner <tt>(1, 1, 1)</tt> when using the identity matrix.
     */
    public static final int CORNER_PXPYPZ = 7;

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
     * Create a new {@link Matrix4d} by setting its uppper left 3x3 submatrix to the values of the given {@link Matrix3d}
     * and the rest to identity.
     * 
     * @param mat
     *          the {@link Matrix3d}
     */
    public Matrix4d(Matrix3d mat) {
        m00 = mat.m00;
        m01 = mat.m01;
        m02 = mat.m02;
        m10 = mat.m10;
        m11 = mat.m11;
        m12 = mat.m12;
        m20 = mat.m20;
        m21 = mat.m21;
        m22 = mat.m22;
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
     * Set the upper left 3x3 submatrix of this {@link Matrix4d} to the given {@link Matrix3d} 
     * and the rest to identity.
     * 
     * @see #Matrix4d(Matrix3d)
     * 
     * @param mat
     *          the {@link Matrix3d}
     * @return this
     */
    public Matrix4d set(Matrix3d mat) {
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
     * Set the upper left 3x3 submatrix of this {@link Matrix4d} to that of the given {@link Matrix4d} 
     * and the rest to identity.
     * 
     * @param mat
     *          the {@link Matrix4d}
     * @return this
     */
    public Matrix4d set3x3(Matrix4d mat) {
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
        m30 = 0.0;
        m31 = 0.0;
        m32 = 0.0;
        m33 = 1.0;
        return this;
    }

    /**
     * Set this matrix to be equivalent to the rotation specified by the given {@link AxisAngle4d}.
     * 
     * @param axisAngle
     *          the {@link AxisAngle4d}
     * @return this
     */
    public Matrix4d set(AxisAngle4d axisAngle) {
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
        m30 = 0.0;
        m31 = 0.0;
        m32 = 0.0;
        m33 = 1.0;
        return this;
    }

    /**
     * Set this matrix to be equivalent to the rotation specified by the given {@link Quaternionf}.
     * 
     * @see Quaternionf#get(Matrix4d)
     * 
     * @param q
     *          the {@link Quaternionf}
     * @return this
     */
    public Matrix4d set(Quaternionf q) {
        return q.get(this);
    }

    /**
     * Set this matrix to be equivalent to the rotation specified by the given {@link Quaterniond}.
     * 
     * @see Quaterniond#get(Matrix4d)
     * 
     * @param q
     *          the {@link Quaterniond}
     * @return this
     */
    public Matrix4d set(Quaterniond q) {
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
     * @return dest
     */
    public Matrix4d mul(Matrix4d right, Matrix4d dest) {
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
        return dest;
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
     * @return dest
     */
    public Matrix4d mul(Matrix4f right, Matrix4d dest) {
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
        return dest;
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
    public Matrix4d mul4x3r(Matrix4d right) {
       return mul4x3r(right, this);
    }

    /**
     * Multiply this matrix by the top 4x3 submatrix of the supplied <code>right</code> matrix and store the result in <code>dest</code>.
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
     * @param dest
     *          the destination matrix, which will hold the result
     * @return dest
     */
    public Matrix4d mul4x3r(Matrix4d right, Matrix4d dest) {
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
        return dest;
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
    public Matrix4d mul4x3(Matrix4d right) {
       return mul4x3(right, this);
    }

    /**
     * Multiply the top 4x3 submatrix of this matrix by the top 4x3 submatrix of the supplied <code>right</code> matrix and store the result in <code>dest</code>.
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
     * @param dest
     *          the destination matrix, which will hold the result
     * @return dest
     */
    public Matrix4d mul4x3(Matrix4d right, Matrix4d dest) {
        dest.set(m00 * right.m00 + m10 * right.m01 + m20 * right.m02,
                 m01 * right.m00 + m11 * right.m01 + m21 * right.m02,
                 m02 * right.m00 + m12 * right.m01 + m22 * right.m02,
                 m03,
                 m00 * right.m10 + m10 * right.m11 + m20 * right.m12,
                 m01 * right.m10 + m11 * right.m11 + m21 * right.m12,
                 m02 * right.m10 + m12 * right.m11 + m22 * right.m12,
                 m13,
                 m00 * right.m20 + m10 * right.m21 + m20 * right.m22,
                 m01 * right.m20 + m11 * right.m21 + m21 * right.m22,
                 m02 * right.m20 + m12 * right.m21 + m22 * right.m22,
                 m23,
                 m00 * right.m30 + m10 * right.m31 + m20 * right.m32 + m30,
                 m01 * right.m30 + m11 * right.m31 + m21 * right.m32 + m31,
                 m02 * right.m30 + m12 * right.m31 + m22 * right.m32 + m32,
                 m33);
        return dest;
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
    public Matrix4d fma4x3(Matrix4d other, double otherFactor) {
        return fma4x3(other, otherFactor, this);
    }

    /**
     * Component-wise add the upper 4x3 submatrices of <code>this</code> and <code>other</code>
     * by first multiplying each component of <code>other</code>'s 4x3 submatrix by <code>otherFactor</code>,
     * adding that to <code>this</code> and storing the final result in <code>dest</code>.
     * <p>
     * The other components of <code>dest</code> will be set to the ones of <code>this</code>.
     * <p>
     * The matrices <code>this</code> and <code>other</code> will not be changed.
     * 
     * @param other
     *          the other matrix
     * @param otherFactor
     *          the factor to multiply each of the other matrix's 4x3 components
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Matrix4d fma4x3(Matrix4d other, double otherFactor, Matrix4d dest) {
        dest.m00 = m00 + other.m00 * otherFactor;
        dest.m01 = m01 + other.m01 * otherFactor;
        dest.m02 = m02 + other.m02 * otherFactor;
        dest.m03 = m03;
        dest.m10 = m10 + other.m10 * otherFactor;
        dest.m11 = m11 + other.m11 * otherFactor;
        dest.m12 = m12 + other.m12 * otherFactor;
        dest.m13 = m13;
        dest.m20 = m20 + other.m20 * otherFactor;
        dest.m21 = m21 + other.m21 * otherFactor;
        dest.m22 = m22 + other.m22 * otherFactor;
        dest.m23 = m23;
        dest.m30 = m30 + other.m30 * otherFactor;
        dest.m31 = m31 + other.m31 * otherFactor;
        dest.m32 = m32 + other.m32 * otherFactor;
        dest.m33 = m33;
        return dest;
    }

    /**
     * Component-wise add <code>this</code> and <code>other</code>.
     * 
     * @param other
     *          the other addend
     * @return this
     */
    public Matrix4d add(Matrix4d other) {
        return add(other, this);
    }

    /**
     * Component-wise add <code>this</code> and <code>other</code> and store the result in <code>dest</code>.
     * 
     * @param other
     *          the other addend
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Matrix4d add(Matrix4d other, Matrix4d dest) {
        dest.m00 = m00 + other.m00;
        dest.m01 = m01 + other.m01;
        dest.m02 = m02 + other.m02;
        dest.m03 = m03 + other.m03;
        dest.m10 = m10 + other.m10;
        dest.m11 = m11 + other.m11;
        dest.m12 = m12 + other.m12;
        dest.m13 = m13 + other.m13;
        dest.m20 = m20 + other.m20;
        dest.m21 = m21 + other.m21;
        dest.m22 = m22 + other.m22;
        dest.m23 = m23 + other.m23;
        dest.m30 = m30 + other.m30;
        dest.m31 = m31 + other.m31;
        dest.m32 = m32 + other.m32;
        dest.m33 = m33 + other.m33;
        return dest;
    }

    /**
     * Component-wise subtract <code>subtrahend</code> from <code>this</code>.
     * 
     * @param subtrahend
     *          the subtrahend
     * @return this
     */
    public Matrix4d sub(Matrix4d subtrahend) {
        return sub(subtrahend, this);
    }

    /**
     * Component-wise subtract <code>subtrahend</code> from <code>this</code> and store the result in <code>dest</code>.
     * 
     * @param subtrahend
     *          the subtrahend
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Matrix4d sub(Matrix4d subtrahend, Matrix4d dest) {
        dest.m00 = m00 - subtrahend.m00;
        dest.m01 = m01 - subtrahend.m01;
        dest.m02 = m02 - subtrahend.m02;
        dest.m03 = m03 - subtrahend.m03;
        dest.m10 = m10 - subtrahend.m10;
        dest.m11 = m11 - subtrahend.m11;
        dest.m12 = m12 - subtrahend.m12;
        dest.m13 = m13 - subtrahend.m13;
        dest.m20 = m20 - subtrahend.m20;
        dest.m21 = m21 - subtrahend.m21;
        dest.m22 = m22 - subtrahend.m22;
        dest.m23 = m23 - subtrahend.m23;
        dest.m30 = m30 - subtrahend.m30;
        dest.m31 = m31 - subtrahend.m31;
        dest.m32 = m32 - subtrahend.m32;
        dest.m33 = m33 - subtrahend.m33;
        return dest;
    }

    /**
     * Component-wise multiply <code>this</code> by <code>other</code>.
     * 
     * @param other
     *          the other matrix
     * @return this
     */
    public Matrix4d mulComponentWise(Matrix4d other) {
        return mulComponentWise(other, this);
    }

    /**
     * Component-wise multiply <code>this</code> by <code>other</code> and store the result in <code>dest</code>.
     * 
     * @param other
     *          the other matrix
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Matrix4d mulComponentWise(Matrix4d other, Matrix4d dest) {
        dest.m00 = m00 * other.m00;
        dest.m01 = m01 * other.m01;
        dest.m02 = m02 * other.m02;
        dest.m03 = m03 * other.m03;
        dest.m10 = m10 * other.m10;
        dest.m11 = m11 * other.m11;
        dest.m12 = m12 * other.m12;
        dest.m13 = m13 * other.m13;
        dest.m20 = m20 * other.m20;
        dest.m21 = m21 * other.m21;
        dest.m22 = m22 * other.m22;
        dest.m23 = m23 * other.m23;
        dest.m30 = m30 * other.m30;
        dest.m31 = m31 * other.m31;
        dest.m32 = m32 * other.m32;
        dest.m33 = m33 * other.m33;
        return dest;
    }

    /**
     * Component-wise add the upper 4x3 submatrices of <code>this</code> and <code>other</code>.
     * 
     * @param other
     *          the other addend
     * @return this
     */
    public Matrix4d add4x3(Matrix4d other) {
        return add4x3(other, this);
    }

    /**
     * Component-wise add the upper 4x3 submatrices of <code>this</code> and <code>other</code>
     * and store the result in <code>dest</code>.
     * <p>
     * The other components of <code>dest</code> will be set to the ones of <code>this</code>.
     * 
     * @param other
     *          the other addend
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Matrix4d add4x3(Matrix4d other, Matrix4d dest) {
        dest.m00 = m00 + other.m00;
        dest.m01 = m01 + other.m01;
        dest.m02 = m02 + other.m02;
        dest.m03 = m03;
        dest.m10 = m10 + other.m10;
        dest.m11 = m11 + other.m11;
        dest.m12 = m12 + other.m12;
        dest.m13 = m13;
        dest.m20 = m20 + other.m20;
        dest.m21 = m21 + other.m21;
        dest.m22 = m22 + other.m22;
        dest.m23 = m23;
        dest.m30 = m30 + other.m30;
        dest.m31 = m31 + other.m31;
        dest.m32 = m32 + other.m32;
        dest.m33 = m33;
        return dest;
    }

    /**
     * Component-wise subtract the upper 4x3 submatrices of <code>subtrahend</code> from <code>this</code>.
     * 
     * @param subtrahend
     *          the subtrahend
     * @return this
     */
    public Matrix4d sub4x3(Matrix4d subtrahend) {
        return sub4x3(subtrahend, this);
    }

    /**
     * Component-wise subtract the upper 4x3 submatrices of <code>subtrahend</code> from <code>this</code>
     * and store the result in <code>dest</code>.
     * <p>
     * The other components of <code>dest</code> will be set to the ones of <code>this</code>.
     * 
     * @param subtrahend
     *          the subtrahend
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Matrix4d sub4x3(Matrix4d subtrahend, Matrix4d dest) {
        dest.m00 = m00 - subtrahend.m00;
        dest.m01 = m01 - subtrahend.m01;
        dest.m02 = m02 - subtrahend.m02;
        dest.m03 = m03;
        dest.m10 = m10 - subtrahend.m10;
        dest.m11 = m11 - subtrahend.m11;
        dest.m12 = m12 - subtrahend.m12;
        dest.m13 = m13;
        dest.m20 = m20 - subtrahend.m20;
        dest.m21 = m21 - subtrahend.m21;
        dest.m22 = m22 - subtrahend.m22;
        dest.m23 = m23;
        dest.m30 = m30 - subtrahend.m30;
        dest.m31 = m31 - subtrahend.m31;
        dest.m32 = m32 - subtrahend.m32;
        dest.m33 = m33;
        return dest;
    }

    /**
     * Component-wise multiply the upper 4x3 submatrices of <code>this</code> by <code>other</code>.
     * 
     * @param other
     *          the other matrix
     * @return this
     */
    public Matrix4d mul4x3ComponentWise(Matrix4d other) {
        return mul4x3ComponentWise(other, this);
    }

    /**
     * Component-wise multiply the upper 4x3 submatrices of <code>this</code> by <code>other</code>
     * and store the result in <code>dest</code>.
     * <p>
     * The other components of <code>dest</code> will be set to the ones of <code>this</code>.
     * 
     * @param other
     *          the other matrix
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Matrix4d mul4x3ComponentWise(Matrix4d other, Matrix4d dest) {
        dest.m00 = m00 * other.m00;
        dest.m01 = m01 * other.m01;
        dest.m02 = m02 * other.m02;
        dest.m03 = m03;
        dest.m10 = m10 * other.m10;
        dest.m11 = m11 * other.m11;
        dest.m12 = m12 * other.m12;
        dest.m13 = m13;
        dest.m20 = m20 * other.m20;
        dest.m21 = m21 * other.m21;
        dest.m22 = m22 * other.m22;
        dest.m23 = m23;
        dest.m30 = m30 * other.m30;
        dest.m31 = m31 * other.m31;
        dest.m32 = m32 * other.m32;
        dest.m33 = m33;
        return dest;
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
     * Return the determinant of this matrix.
     * <p>
     * If <code>this</code> matrix is only composed of affine transformations, such as translation, rotation, scaling and shearing,
     * and thus its last row is equal to <tt>(0, 0, 0, 1)</tt>, then {@link #determinant4x3()} can be used instead of this method.
     * 
     * @see #determinant4x3()
     * 
     * @return the determinant
     */
    public double determinant() {
        return (m00 * m11 - m01 * m10) * (m22 * m33 - m23 * m32)
             + (m02 * m10 - m00 * m12) * (m21 * m33 - m23 * m31)
             + (m00 * m13 - m03 * m10) * (m21 * m32 - m22 * m31) 
             + (m01 * m12 - m02 * m11) * (m20 * m33 - m23 * m30)
             + (m03 * m11 - m01 * m13) * (m20 * m32 - m22 * m30) 
             + (m02 * m13 - m03 * m12) * (m20 * m31 - m21 * m30);
    }

    /**
     * Return the determinant of the upper left 3x3 submatrix of this matrix.
     * 
     * @return the determinant
     */
    public double determinant3x3() {
        return m00 * (m11 * m22 - m12 * m21)
             + m01 * (m12 * m20 - m10 * m22)
             + m02 * (m01 * m21 - m11 * m20);
    }

    /**
     * Return the determinant of this matrix by assuming that its last row is equal to <tt>(0, 0, 0, 1)</tt>.
     * 
     * @return the determinant
     */
    public double determinant4x3() {
        return (m00 * m11 - m01 * m10) * m22
             + (m02 * m10 - m00 * m12) * m21
             + (m01 * m12 - m02 * m11) * m20;
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
     * Invert <code>this</code> matrix and store the result in <code>dest</code>.
     * <p>
     * If <code>this</code> matrix is only composed of affine transformations, such as translation, rotation, scaling and shearing,
     * and thus its last row is equal to <tt>(0, 0, 0, 1)</tt>, then {@link #invert4x3(Matrix4d)} can be used instead of this method.
     * 
     * @see #invert4x3(Matrix4d)
     * 
     * @param dest
     *             will hold the result
     * @return dest
     */
    public Matrix4d invert(Matrix4d dest) {
        double a = m00 * m11 - m01 * m10;
        double b = m00 * m12 - m02 * m10;
        double c = m00 * m13 - m03 * m10;
        double d = m01 * m12 - m02 * m11;
        double e = m01 * m13 - m03 * m11;
        double f = m02 * m13 - m03 * m12;
        double g = m20 * m31 - m21 * m30;
        double h = m20 * m32 - m22 * m30;
        double i = m20 * m33 - m23 * m30;
        double j = m21 * m32 - m22 * m31;
        double k = m21 * m33 - m23 * m31;
        double l = m22 * m33 - m23 * m32;
        double det = a * l - b * k + c * j + d * i - e * h + f * g;
        det = 1.0 / det;
        dest.set(( m11 * l - m12 * k + m13 * j) * det,
                 (-m01 * l + m02 * k - m03 * j) * det,
                 ( m31 * f - m32 * e + m33 * d) * det,
                 (-m21 * f + m22 * e - m23 * d) * det,
                 (-m10 * l + m12 * i - m13 * h) * det,
                 ( m00 * l - m02 * i + m03 * h) * det,
                 (-m30 * f + m32 * c - m33 * b) * det,
                 ( m20 * f - m22 * c + m23 * b) * det,
                 ( m10 * k - m11 * i + m13 * g) * det,
                 (-m00 * k + m01 * i - m03 * g) * det,
                 ( m30 * e - m31 * c + m33 * a) * det,
                 (-m20 * e + m21 * c - m23 * a) * det,
                 (-m10 * j + m11 * h - m12 * g) * det,
                 ( m00 * j - m01 * h + m02 * g) * det,
                 (-m30 * d + m31 * b - m32 * a) * det,
                 ( m20 * d - m21 * b + m22 * a) * det);
        return dest;
    }

    /**
     * Invert this matrix by assuming that its last row is equal to <tt>(0, 0, 0, 1)</tt> and write the result into <code>dest</code>.
     * 
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Matrix4d invert4x3(Matrix4d dest) {
        double s = determinant4x3();
        s = 1.0 / s;
        double m10m22 = m10 * m22;
        double m10m21 = m10 * m21;
        double m10m02 = m10 * m02;
        double m10m01 = m10 * m01;
        double m11m22 = m11 * m22;
        double m11m20 = m11 * m20;
        double m11m02 = m11 * m02;
        double m11m00 = m11 * m00;
        double m12m21 = m12 * m21;
        double m12m20 = m12 * m20;
        double m12m01 = m12 * m01;
        double m12m00 = m12 * m00;
        double m20m02 = m20 * m02;
        double m20m01 = m20 * m01;
        double m21m02 = m21 * m02;
        double m21m00 = m21 * m00;
        double m22m01 = m22 * m01;
        double m22m00 = m22 * m00;
        dest.set((m11m22 - m12m21) * s,
                 (m21m02 - m22m01) * s,
                 (m12m01 - m11m02) * s,
                 0.0,
                 (m12m20 - m10m22) * s,
                 (m22m00 - m20m02) * s,
                 (m10m02 - m12m00) * s,
                 0.0,
                 (m10m21 - m11m20) * s,
                 (m20m01 - m21m00) * s,
                 (m11m00 - m10m01) * s,
                 0.0,
                 (m10m22 * m31 - m10m21 * m32 + m11m20 * m32 - m11m22 * m30 + m12m21 * m30 - m12m20 * m31) * s,
                 (m20m02 * m31 - m20m01 * m32 + m21m00 * m32 - m21m02 * m30 + m22m01 * m30 - m22m00 * m31) * s,
                 (m11m02 * m30 - m12m01 * m30 + m12m00 * m31 - m10m02 * m31 + m10m01 * m32 - m11m00 * m32) * s,
                 1.0);
        return dest;
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
     * Transpose <code>this</code> matrix and store the result into <code>dest</code>.
     * 
     * @param dest
     *             will hold the result
     * @return dest
     */
    public Matrix4d transpose(Matrix4d dest) {
        dest.set(m00, m10, m20, m30,
                 m01, m11, m21, m31,
                 m02, m12, m22, m32,
                 m03, m13, m23, m33);
        return dest;
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
     * Transpose only the upper left 3x3 submatrix of this matrix and store the result in <code>dest</code>.
     * <p>
     * All other matrix elements of <code>dest</code> will be set to identity.
     * 
     * @param dest
     *             will hold the result
     * @return dest
     */
    public Matrix4d transpose3x3(Matrix4d dest) {
        dest.set(m00, m10, m20, 0.0,
                 m01, m11, m21, 0.0,
                 m02, m12, m22, 0.0,
                 0.0, 0.0, 0.0, 1.0);
        return dest;
    }

    /**
     * Transpose only the upper left 3x3 submatrix of this matrix and store the result in <code>dest</code>.
     * 
     * @param dest
     *             will hold the result
     * @return dest
     */
    public Matrix3d transpose3x3(Matrix3d dest) {
        dest.m00 = m00;
        dest.m01 = m10;
        dest.m02 = m20;
        dest.m10 = m01;
        dest.m11 = m11;
        dest.m12 = m21;
        dest.m20 = m02;
        dest.m21 = m12;
        dest.m22 = m22;
        return dest;
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
     * Set only the translation components of this matrix <tt>(m30, m31, m32)</tt> to the given values <tt>(xyz.x, xyz.y, xyz.z)</tt>.
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
     * Get only the translation components of this matrix <tt>(m30, m31, m32)</tt> and store them in the given vector <code>xyz</code>.
     * 
     * @param dest
     *          will hold the translation components of this matrix
     * @return dest
     */
    public Vector3d getTranslation(Vector3d dest) {
        dest.x = m30;
        dest.y = m31;
        dest.z = m32;
        return dest;
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
     * @return the passed in destination
     */
    public Matrix4d get(Matrix4d dest) {
        return dest.set(this);
    }

    /**
     * Get the current values of <code>this</code> matrix and store the represented rotation
     * into the given {@link Quaternionf}.
     * <p>
     * This method assumes that the first three column vectors of the upper left 3x3 submatrix are not normalized and
     * thus allows to ignore any additional scaling factor that is applied to the matrix.
     * 
     * @see Quaternionf#setFromUnnormalized(Matrix4d)
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
     * This method assumes that the first three column vectors of the upper left 3x3 submatrix are normalized.
     * 
     * @see Quaternionf#setFromNormalized(Matrix4d)
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
     * This method assumes that the first three column vectors of the upper left 3x3 submatrix are not normalized and
     * thus allows to ignore any additional scaling factor that is applied to the matrix.
     * 
     * @see Quaterniond#setFromUnnormalized(Matrix4d)
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
     * This method assumes that the first three column vectors of the upper left 3x3 submatrix are normalized.
     * 
     * @see Quaterniond#setFromNormalized(Matrix4d)
     * 
     * @param dest
     *          the destination {@link Quaterniond}
     * @return the passed in destination
     */
    public Quaterniond getNormalizedRotation(Quaterniond dest) {
        return dest.setFromNormalized(this);
    }

    /**
     * Store this matrix in column-major order into the supplied {@link DoubleBuffer} at the current
     * buffer {@link DoubleBuffer#position() position}.
     * <p>
     * This method will not increment the position of the given DoubleBuffer.
     * <p>
     * In order to specify the offset into the DoubleBuffer at which
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
     * Store this matrix in column-major order into the supplied {@link DoubleBuffer} starting at the specified
     * absolute buffer position/index.
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
        return buffer;
    }

    /**
     * Store this matrix in column-major order into the supplied {@link FloatBuffer} at the current
     * buffer {@link FloatBuffer#position() position}.
     * <p>
     * This method will not increment the position of the given
     * FloatBuffer.
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
        buffer.put(index,    (float) m00);
        buffer.put(index+1,  (float) m01);
        buffer.put(index+2,  (float) m02);
        buffer.put(index+3,  (float) m03);
        buffer.put(index+4,  (float) m10);
        buffer.put(index+5,  (float) m11);
        buffer.put(index+6,  (float) m12);
        buffer.put(index+7,  (float) m13);
        buffer.put(index+8,  (float) m20);
        buffer.put(index+9,  (float) m21);
        buffer.put(index+10, (float) m22);
        buffer.put(index+11, (float) m23);
        buffer.put(index+12, (float) m30);
        buffer.put(index+13, (float) m31);
        buffer.put(index+14, (float) m32);
        buffer.put(index+15, (float) m33);
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
        buffer.putDouble(index+8*0,  m00);
        buffer.putDouble(index+8*1,  m01);
        buffer.putDouble(index+8*2,  m02);
        buffer.putDouble(index+8*3,  m03);
        buffer.putDouble(index+8*4,  m10);
        buffer.putDouble(index+8*5,  m11);
        buffer.putDouble(index+8*6,  m12);
        buffer.putDouble(index+8*7,  m13);
        buffer.putDouble(index+8*8,  m20);
        buffer.putDouble(index+8*9,  m21);
        buffer.putDouble(index+8*10, m22);
        buffer.putDouble(index+8*11, m23);
        buffer.putDouble(index+8*12, m30);
        buffer.putDouble(index+8*13, m31);
        buffer.putDouble(index+8*14, m32);
        buffer.putDouble(index+8*15, m33);
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
        buffer.putFloat(index+4*0,  (float)m00);
        buffer.putFloat(index+4*1,  (float)m01);
        buffer.putFloat(index+4*2,  (float)m02);
        buffer.putFloat(index+4*3,  (float)m03);
        buffer.putFloat(index+4*4,  (float)m10);
        buffer.putFloat(index+4*5,  (float)m11);
        buffer.putFloat(index+4*6,  (float)m12);
        buffer.putFloat(index+4*7,  (float)m13);
        buffer.putFloat(index+4*8,  (float)m20);
        buffer.putFloat(index+4*9,  (float)m21);
        buffer.putFloat(index+4*10, (float)m22);
        buffer.putFloat(index+4*11, (float)m23);
        buffer.putFloat(index+4*12, (float)m30);
        buffer.putFloat(index+4*13, (float)m31);
        buffer.putFloat(index+4*14, (float)m32);
        buffer.putFloat(index+4*15, (float)m33);
        return buffer;
    }

    /**
     * Store this matrix into the supplied double array in column-major order.
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
        arr[offset+3] = m03;
        arr[offset+4] = m10;
        arr[offset+5] = m11;
        arr[offset+6] = m12;
        arr[offset+7] = m13;
        arr[offset+8] = m20;
        arr[offset+9] = m21;
        arr[offset+10] = m22;
        arr[offset+11] = m23;
        arr[offset+12] = m30;
        arr[offset+13] = m31;
        arr[offset+14] = m32;
        arr[offset+15] = m33;
        return arr;
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
     * matrix use {@link #scale(Vector3d) scale()} instead.
     * 
     * @see #scale(Vector3d)
     * 
     * @param xyz
     *             the scale in x, y and z, respectively
     * @return this
     */
    public Matrix4d scaling(Vector3d xyz) {
        return scaling(xyz.x, xyz.y, xyz.z);
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
     * Create a matrix representing a rotation of <code>angleX</code> radians about the X axis, followed by a rotation
     * of <code>angleY</code> radians about the Y axis and followed by a rotation of <code>angleZ</code> radians about the Z axis
     * and store the result in <code>dest</code>.
     * <p>
     * This method is equivalent to calling: <tt>rotationX(angleX).rotateY(angleY).rotateZ(angleZ)</tt>
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
    public Matrix4d rotationXYZ(double angleX, double angleY, double angleZ, Matrix4d dest) {
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
        dest.m20 = sinY;
        dest.m21 = nm21 * cosY;
        dest.m22 = nm22 * cosY;
        dest.m23 = 0.0;
        // rotateZ
        dest.m00 = nm00 * cosZ;
        dest.m01 = nm01 * cosZ + nm11 * sinZ;
        dest.m02 = nm02 * cosZ + nm12 * sinZ;
        dest.m03 = 0.0;
        dest.m10 = nm00 * m_sinZ;
        dest.m11 = nm01 * m_sinZ + nm11 * cosZ;
        dest.m12 = nm02 * m_sinZ + nm12 * cosZ;
        dest.m13 = 0.0;
        // set last column to identity
        dest.m30 = 0.0;
        dest.m31 = 0.0;
        dest.m32 = 0.0;
        dest.m33 = 1.0;
        return dest;
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
     * Create a matrix representing a rotation of <code>angleZ</code> radians about the Z axis, followed by a rotation
     * of <code>angleY</code> radians about the Y axis and followed by a rotation of <code>angleX</code> radians about the X axis
     * and store the result in <code>dest</code>.
     * <p>
     * This method is equivalent to calling: <tt>rotationZ(angleZ).rotateY(angleY).rotateX(angleX)</tt>
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
    public Matrix4d rotationZYX(double angleZ, double angleY, double angleX, Matrix4d dest) {
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
        dest.m00 = nm00 * cosY;
        dest.m01 = nm01 * cosY;
        dest.m02 = m_sinY;
        dest.m03 = 0.0;
        // rotateX
        dest.m10 = nm10 * cosX + nm20 * sinX;
        dest.m11 = nm11 * cosX + nm21 * sinX;
        dest.m12 = nm22 * sinX;
        dest.m13 = 0.0;
        dest.m20 = nm10 * m_sinX + nm20 * cosX;
        dest.m21 = nm11 * m_sinX + nm21 * cosX;
        dest.m22 = nm22 * cosX;
        dest.m23 = 0.0;
        // set last column to identity
        dest.m30 = 0.0;
        dest.m31 = 0.0;
        dest.m32 = 0.0;
        dest.m33 = 1.0;
        return dest;
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
     * Create a matrix representing a rotation of <code>angleY</code> radians about the Y axis, followed by a rotation
     * of <code>angleX</code> radians about the X axis and followed by a rotation of <code>angleZ</code> radians about the Z axis
     * and store the result in <code>dest</code>.
     * <p>
     * This method is equivalent to calling: <tt>rotationY(angleY).rotateX(angleX).rotateZ(angleZ)</tt>
     * 
     * @param angleY
     *            the angle to rotate about Y
     * @param angleX
     *            the angle to rotate about X
     * @param angleZ
     *            the angle to rotate about Z
     * @param dest
     *            will hold the result
     * @return dest
     */
    public Matrix4d rotationYXZ(double angleY, double angleX, double angleZ, Matrix4d dest) {
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
        dest.m20 = nm20 * cosX;
        dest.m21 = m_sinX;
        dest.m22 = nm22 * cosX;
        dest.m23 = 0.0;
        // rotateZ
        dest.m00 = nm00 * cosZ + nm10 * sinZ;
        dest.m01 = nm11 * sinZ;
        dest.m02 = nm02 * cosZ + nm12 * sinZ;
        dest.m03 = 0.0;
        dest.m10 = nm00 * m_sinZ + nm10 * cosZ;
        dest.m11 = nm11 * cosZ;
        dest.m12 = nm02 * m_sinZ + nm12 * cosZ;
        dest.m13 = 0.0;
        // set last column to identity
        dest.m30 = 0.0;
        dest.m31 = 0.0;
        dest.m32 = 0.0;
        dest.m33 = 1.0;
        return dest;
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
    public Matrix4d rotation(double angle, Vector3d axis) {
        return rotation(angle, axis.x, axis.y, axis.z);
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
    public Matrix4d rotation(double angle, Vector3f axis) {
        return rotation(angle, axis.x, axis.y, axis.z);
    }

    /**
     * Transform/multiply the given vector by this matrix and store the result in that vector.
     * 
     * @see Vector4d#mul(Matrix4d)
     * 
     * @param v
     *          the vector to transform and to hold the final result
     * @return v
     */
    public Vector4d transform(Vector4d v) {
        return v.mul(this);
    }

    /**
     * Transform/multiply the given vector by this matrix and store the result in <code>dest</code>.
     * 
     * @see Vector4d#mul(Matrix4d, Vector4d)
     * 
     * @param v
     *          the vector to transform
     * @param dest
     *          will contain the result
     * @return dest
     */
    public Vector4d transform(Vector4d v, Vector4d dest) {
        return v.mul(this, dest);
    }

    /**
     * Transform/multiply the given vector by this matrix, perform perspective divide and store the result in that vector.
     * 
     * @see Vector4d#mulProject(Matrix4d)
     * 
     * @param v
     *          the vector to transform and to hold the final result
     * @return v
     */
    public Vector4d transformProject(Vector4d v) {
        return v.mulProject(this);
    }

    /**
     * Transform/multiply the given vector by this matrix, perform perspective divide and store the result in <code>dest</code>.
     * 
     * @see Vector4d#mulProject(Matrix4d, Vector4d)
     * 
     * @param v
     *          the vector to transform
     * @param dest
     *          will contain the result
     * @return dest
     */
    public Vector4d transformProject(Vector4d v, Vector4d dest) {
        return v.mulProject(this, dest);
    }

    /**
     * Transform/multiply the given vector by this matrix, perform perspective divide and store the result in that vector.
     * <p>
     * This method uses <tt>w=1.0</tt> as the fourth vector component.
     * 
     * @see Vector3d#mulProject(Matrix4d)
     * 
     * @param v
     *          the vector to transform and to hold the final result
     * @return v
     */
    public Vector3d transformProject(Vector3d v) {
        return v.mulProject(this);
    }

    /**
     * Transform/multiply the given vector by this matrix, perform perspective divide and store the result in <code>dest</code>.
     * <p>
     * This method uses <tt>w=1.0</tt> as the fourth vector component.
     * 
     * @see Vector3d#mulProject(Matrix4d, Vector3d)
     * 
     * @param v
     *          the vector to transform
     * @param dest
     *          will contain the result
     * @return dest
     */
    public Vector3d transformProject(Vector3d v, Vector3d dest) {
        return v.mulProject(this, dest);
    }

    /**
     * Transform/multiply the given 3D-vector, as if it was a 4D-vector with w=1, by
     * this matrix and store the result in that vector.
     * <p>
     * The given 3D-vector is treated as a 4D-vector with its w-component being 1.0, so it
     * will represent a point/location in 3D-space rather than a direction. This method is therefore
     * not suited for perspective projection transformations as it will not save the
     * <tt>w</tt> component of the transformed vector.
     * For perspective projection use {@link #transform(Vector4d)}.
     * <p>
     * In order to store the result in another vector, use {@link #transformPoint(Vector3d, Vector3d)}.
     * 
     * @see #transformPoint(Vector3d, Vector3d)
     * @see #transform(Vector4d)
     * 
     * @param v
     *          the vector to transform and to hold the final result
     * @return v
     */
    public Vector3d transformPoint(Vector3d v) {
        v.set(m00 * v.x + m10 * v.y + m20 * v.z + m30,
              m01 * v.x + m11 * v.y + m21 * v.z + m31,
              m02 * v.x + m12 * v.y + m22 * v.z + m32);
        return v;
    }

    /**
     * Transform/multiply the given 3D-vector, as if it was a 4D-vector with w=1, by
     * this matrix and store the result in <code>dest</code>.
     * <p>
     * The given 3D-vector is treated as a 4D-vector with its w-component being 1.0, so it
     * will represent a point/location in 3D-space rather than a direction. This method is therefore
     * not suited for perspective projection transformations as it will not save the
     * <tt>w</tt> component of the transformed vector.
     * For perspective projection use {@link #transform(Vector4d, Vector4d)}.
     * <p>
     * In order to store the result in the same vector, use {@link #transformPoint(Vector3d)}.
     * 
     * @see #transformPoint(Vector3d)
     * @see #transform(Vector4d, Vector4d)
     * 
     * @param v
     *          the vector to transform
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Vector3d transformPoint(Vector3d v, Vector3d dest) {
        dest.set(m00 * v.x + m10 * v.y + m20 * v.z + m30,
                 m01 * v.x + m11 * v.y + m21 * v.z + m31,
                 m02 * v.x + m12 * v.y + m22 * v.z + m32);
        return dest;
    }

    /**
     * Transform/multiply the given 3D-vector, as if it was a 4D-vector with w=0, by
     * this matrix and store the result in that vector.
     * <p>
     * The given 3D-vector is treated as a 4D-vector with its w-component being <tt>0.0</tt>, so it
     * will represent a direction in 3D-space rather than a point. This method will therefore
     * not take the translation part of the matrix into account.
     * <p>
     * In order to store the result in another vector, use {@link #transformDirection(Vector3d, Vector3d)}.
     * 
     * @param v
     *          the vector to transform and to hold the final result
     * @return v
     */
    public Vector3d transformDirection(Vector3d v) {
        v.set(m00 * v.x + m10 * v.y + m20 * v.z,
              m01 * v.x + m11 * v.y + m21 * v.z,
              m02 * v.x + m12 * v.y + m22 * v.z);
        return v;
    }

    /**
     * Transform/multiply the given 3D-vector, as if it was a 4D-vector with w=0, by
     * this matrix and store the result in <code>dest</code>.
     * <p>
     * The given 3D-vector is treated as a 4D-vector with its w-component being <tt>0.0</tt>, so it
     * will represent a direction in 3D-space rather than a point. This method will therefore
     * not take the translation part of the matrix into account.
     * <p>
     * In order to store the result in the same vector, use {@link #transformDirection(Vector3d)}.
     * 
     * @param v
     *          the vector to transform and to hold the final result
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Vector3d transformDirection(Vector3d v, Vector3d dest) {
        dest.set(m00 * v.x + m10 * v.y + m20 * v.z,
                 m01 * v.x + m11 * v.y + m21 * v.z,
                 m02 * v.x + m12 * v.y + m22 * v.z);
        return dest;
    }

    /**
     * Set the upper 3x3 matrix of this {@link Matrix4d} to the given {@link Matrix3d} and the rest to the identity.
     * 
     * @param mat
     *          the 3x3 matrix
     * @return this
     */
    public Matrix4d set3x3(Matrix3d mat) {
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
    public Matrix4d scale(Vector3d xyz, Matrix4d dest) {
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
    public Matrix4d scale(Vector3d xyz) {
        return scale(xyz.x, xyz.y, xyz.z, this);
    }

    /**
     * Apply scaling to the this matrix by scaling the base axes by the given x,
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
        return dest;
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
     * Apply scaling to this matrix by uniformly scaling all base axes by the given xyz factor
     * and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>S</code> the scaling matrix,
     * then the new matrix will be <code>M * S</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * S * v</code>
     * , the scaling will be applied first!
     * 
     * @see #scale(double, double, double, Matrix4d)
     * 
     * @param xyz
     *            the factor for all components
     * @param dest
     *            will hold the result
     * @return dest
     */
    public Matrix4d scale(double xyz, Matrix4d dest) {
        return scale(xyz, xyz, xyz, dest);
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
     * about the given axis specified as x, y and z components and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the rotation matrix,
     * then the new matrix will be <code>M * R</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * R * v</code>
     * , the rotation will be applied first!
     * 
     * @param ang
     *            the angle is in radians
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
    public Matrix4d rotate(double ang, double x, double y, double z, Matrix4d dest) {
        double s = Math.sin(ang);
        double c = Math.cos(ang);
        double C = 1.0 - c;

        // rotation matrix elements:
        // m30, m31, m32, m03, m13, m23 = 0
        // m33 = 1
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
        double nm03 = m03 * rm00 + m13 * rm01 + m23 * rm02;
        double nm10 = m00 * rm10 + m10 * rm11 + m20 * rm12;
        double nm11 = m01 * rm10 + m11 * rm11 + m21 * rm12;
        double nm12 = m02 * rm10 + m12 * rm11 + m22 * rm12;
        double nm13 = m03 * rm10 + m13 * rm11 + m23 * rm12;
        // set non-dependent values directly
        dest.m20 = m00 * rm20 + m10 * rm21 + m20 * rm22;
        dest.m21 = m01 * rm20 + m11 * rm21 + m21 * rm22;
        dest.m22 = m02 * rm20 + m12 * rm21 + m22 * rm22;
        dest.m23 = m03 * rm20 + m13 * rm21 + m23 * rm22;
        // set other values
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
     * Apply rotation about the X axis to this matrix by rotating the given amount of radians 
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
     *            the angle in radians
     * @param dest
     *            will hold the result
     * @return dest
     */
    public Matrix4d rotateX(double ang, Matrix4d dest) {
        double cos = Math.cos(ang);
        double sin = Math.sin(ang);
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

        return dest;
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
     * Apply rotation about the Y axis to this matrix by rotating the given amount of radians 
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
     *            the angle in radians
     * @param dest
     *            will hold the result
     * @return dest
     */
    public Matrix4d rotateY(double ang, Matrix4d dest) {
        double cos = Math.cos(ang);
        double sin = Math.sin(ang);
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

        return dest;
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
     * Apply rotation about the Z axis to this matrix by rotating the given amount of radians 
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
     *            the angle in radians
     * @param dest
     *            will hold the result
     * @return dest
     */
    public Matrix4d rotateZ(double ang, Matrix4d dest) {
        double cos = Math.cos(ang);
        double sin = Math.sin(ang);
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
        return dest;
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
    public Matrix4d rotateXYZ(double angleX, double angleY, double angleZ, Matrix4d dest) {
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
        double nm13 = m13 * cosX + m23 * sinX;
        double nm20 = m10 * m_sinX + m20 * cosX;
        double nm21 = m11 * m_sinX + m21 * cosX;
        double nm22 = m12 * m_sinX + m22 * cosX;
        double nm23 = m13 * m_sinX + m23 * cosX;
        // rotateY
        double nm00 = m00 * cosY + nm20 * m_sinY;
        double nm01 = m01 * cosY + nm21 * m_sinY;
        double nm02 = m02 * cosY + nm22 * m_sinY;
        double nm03 = m03 * cosY + nm23 * m_sinY;
        dest.m20 = m00 * sinY + nm20 * cosY;
        dest.m21 = m01 * sinY + nm21 * cosY;
        dest.m22 = m02 * sinY + nm22 * cosY;
        dest.m23 = m03 * sinY + nm23 * cosY;
        // rotateZ
        dest.m00 = nm00 * cosZ + nm10 * sinZ;
        dest.m01 = nm01 * cosZ + nm11 * sinZ;
        dest.m02 = nm02 * cosZ + nm12 * sinZ;
        dest.m03 = nm03 * cosZ + nm13 * sinZ;
        dest.m10 = nm00 * m_sinZ + nm10 * cosZ;
        dest.m11 = nm01 * m_sinZ + nm11 * cosZ;
        dest.m12 = nm02 * m_sinZ + nm12 * cosZ;
        dest.m13 = nm03 * m_sinZ + nm13 * cosZ;
        // copy last column from 'this'
        dest.m30 = m30;
        dest.m31 = m31;
        dest.m32 = m32;
        dest.m33 = m33;
        return dest;
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
     * Apply rotation of <code>angleX</code> radians about the X axis, followed by a rotation of <code>angleY</code> radians about the Y axis and
     * followed by a rotation of <code>angleZ</code> radians about the Z axis and store the result in <code>dest</code>.
     * <p>
     * This method assumes that the last row of <code>this</code> is <tt>(0, 0, 0, 1)</tt> and can be used to speed up matrix multiplication if
     * the matrix only represents affine transformations, such as translation, rotation, scaling and shearing (in any combination).
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the rotation matrix,
     * then the new matrix will be <code>M * R</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * R * v</code>, the
     * rotation will be applied first!
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
    public Matrix4d rotateXYZ4x3(double angleX, double angleY, double angleZ, Matrix4d dest) {
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
        dest.m23 = 0.0;
        // rotateZ
        dest.m00 = nm00 * cosZ + nm10 * sinZ;
        dest.m01 = nm01 * cosZ + nm11 * sinZ;
        dest.m02 = nm02 * cosZ + nm12 * sinZ;
        dest.m03 = 0.0;
        dest.m10 = nm00 * m_sinZ + nm10 * cosZ;
        dest.m11 = nm01 * m_sinZ + nm11 * cosZ;
        dest.m12 = nm02 * m_sinZ + nm12 * cosZ;
        dest.m13 = 0.0;
        // copy last column from 'this'
        dest.m30 = m30;
        dest.m31 = m31;
        dest.m32 = m32;
        dest.m33 = m33;
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
    public Matrix4d rotateZYX(double angleZ, double angleY, double angleX) {
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
    public Matrix4d rotateZYX(double angleZ, double angleY, double angleX, Matrix4d dest) {
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
        double nm03 = m03 * cosZ + m13 * sinZ;
        double nm10 = m00 * m_sinZ + m10 * cosZ;
        double nm11 = m01 * m_sinZ + m11 * cosZ;
        double nm12 = m02 * m_sinZ + m12 * cosZ;
        double nm13 = m03 * m_sinZ + m13 * cosZ;
        // rotateY
        double nm20 = nm00 * sinY + m20 * cosY;
        double nm21 = nm01 * sinY + m21 * cosY;
        double nm22 = nm02 * sinY + m22 * cosY;
        double nm23 = nm03 * sinY + m23 * cosY;
        dest.m00 = nm00 * cosY + m20 * m_sinY;
        dest.m01 = nm01 * cosY + m21 * m_sinY;
        dest.m02 = nm02 * cosY + m22 * m_sinY;
        dest.m03 = nm03 * cosY + m23 * m_sinY;
        // rotateX
        dest.m10 = nm10 * cosX + nm20 * sinX;
        dest.m11 = nm11 * cosX + nm21 * sinX;
        dest.m12 = nm12 * cosX + nm22 * sinX;
        dest.m13 = nm13 * cosX + nm23 * sinX;
        dest.m20 = nm10 * m_sinX + nm20 * cosX;
        dest.m21 = nm11 * m_sinX + nm21 * cosX;
        dest.m22 = nm12 * m_sinX + nm22 * cosX;
        dest.m23 = nm13 * m_sinX + nm23 * cosX;
        // copy last column from 'this'
        dest.m30 = m30;
        dest.m31 = m31;
        dest.m32 = m32;
        dest.m33 = m33;
        return dest;
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
     * Apply rotation of <code>angleZ</code> radians about the Z axis, followed by a rotation of <code>angleY</code> radians about the Y axis and
     * followed by a rotation of <code>angleX</code> radians about the X axis and store the result in <code>dest</code>.
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
     * @param dest
     *            will hold the result
     * @return dest
     */
    public Matrix4d rotateZYX4x3(double angleZ, double angleY, double angleX, Matrix4d dest) {
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
        dest.m03 = 0.0;
        // rotateX
        dest.m10 = nm10 * cosX + nm20 * sinX;
        dest.m11 = nm11 * cosX + nm21 * sinX;
        dest.m12 = nm12 * cosX + nm22 * sinX;
        dest.m13 = 0.0;
        dest.m20 = nm10 * m_sinX + nm20 * cosX;
        dest.m21 = nm11 * m_sinX + nm21 * cosX;
        dest.m22 = nm12 * m_sinX + nm22 * cosX;
        dest.m23 = 0.0;
        // copy last column from 'this'
        dest.m30 = m30;
        dest.m31 = m31;
        dest.m32 = m32;
        dest.m33 = m33;
        return dest;
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
     * followed by a rotation of <code>angleZ</code> radians about the Z axis and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the rotation matrix,
     * then the new matrix will be <code>M * R</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * R * v</code>, the
     * rotation will be applied first!
     * <p>
     * This method is equivalent to calling: <tt>rotateY(angleY, dest).rotateX(angleX).rotateZ(angleZ)</tt>
     * 
     * @param angleY
     *            the angle to rotate about Y
     * @param angleX
     *            the angle to rotate about X
     * @param angleZ
     *            the angle to rotate about Z
     * @param dest
     *            will hold the result
     * @return dest
     */
    public Matrix4d rotateYXZ(double angleY, double angleX, double angleZ, Matrix4d dest) {
        double cosY =  Math.cos(angleY);
        double sinY =  Math.sin(angleY);
        double cosX =  Math.cos(angleX);
        double sinX =  Math.sin(angleX);
        double cosZ =  Math.cos(angleZ);
        double sinZ =  Math.sin(angleZ);
        double m_sinY = -sinY;
        double m_sinX = -sinX;
        double m_sinZ = -sinZ;

        // rotateY
        double nm20 = m00 * sinY + m20 * cosY;
        double nm21 = m01 * sinY + m21 * cosY;
        double nm22 = m02 * sinY + m22 * cosY;
        double nm23 = m03 * sinY + m23 * cosY;
        double nm00 = m00 * cosY + m20 * m_sinY;
        double nm01 = m01 * cosY + m21 * m_sinY;
        double nm02 = m02 * cosY + m22 * m_sinY;
        double nm03 = m03 * cosY + m23 * m_sinY;
        // rotateX
        double nm10 = m10 * cosX + nm20 * sinX;
        double nm11 = m11 * cosX + nm21 * sinX;
        double nm12 = m12 * cosX + nm22 * sinX;
        double nm13 = m13 * cosX + nm23 * sinX;
        dest.m20 = m10 * m_sinX + nm20 * cosX;
        dest.m21 = m11 * m_sinX + nm21 * cosX;
        dest.m22 = m12 * m_sinX + nm22 * cosX;
        dest.m23 = m13 * m_sinX + nm23 * cosX;
        // rotateZ
        dest.m00 = nm00 * cosZ + nm10 * sinZ;
        dest.m01 = nm01 * cosZ + nm11 * sinZ;
        dest.m02 = nm02 * cosZ + nm12 * sinZ;
        dest.m03 = nm03 * cosZ + nm13 * sinZ;
        dest.m10 = nm00 * m_sinZ + nm10 * cosZ;
        dest.m11 = nm01 * m_sinZ + nm11 * cosZ;
        dest.m12 = nm02 * m_sinZ + nm12 * cosZ;
        dest.m13 = nm03 * m_sinZ + nm13 * cosZ;
        // copy last column from 'this'
        dest.m30 = m30;
        dest.m31 = m31;
        dest.m32 = m32;
        dest.m33 = m33;
        return dest;
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
     * Apply rotation of <code>angleY</code> radians about the Y axis, followed by a rotation of <code>angleX</code> radians about the X axis and
     * followed by a rotation of <code>angleZ</code> radians about the Z axis and store the result in <code>dest</code>.
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
     * @param dest
     *            will hold the result
     * @return dest
     */
    public Matrix4d rotateYXZ4x3(double angleY, double angleX, double angleZ, Matrix4d dest) {
        double cosY =  Math.cos(angleY);
        double sinY =  Math.sin(angleY);
        double cosX =  Math.cos(angleX);
        double sinX =  Math.sin(angleX);
        double cosZ =  Math.cos(angleZ);
        double sinZ =  Math.sin(angleZ);
        double m_sinY = -sinY;
        double m_sinX = -sinX;
        double m_sinZ = -sinZ;

        // rotateY
        double nm20 = m00 * sinY + m20 * cosY;
        double nm21 = m01 * sinY + m21 * cosY;
        double nm22 = m02 * sinY + m22 * cosY;
        double nm00 = m00 * cosY + m20 * m_sinY;
        double nm01 = m01 * cosY + m21 * m_sinY;
        double nm02 = m02 * cosY + m22 * m_sinY;
        // rotateX
        double nm10 = m10 * cosX + nm20 * sinX;
        double nm11 = m11 * cosX + nm21 * sinX;
        double nm12 = m12 * cosX + nm22 * sinX;
        dest.m20 = m10 * m_sinX + nm20 * cosX;
        dest.m21 = m11 * m_sinX + nm21 * cosX;
        dest.m22 = m12 * m_sinX + nm22 * cosX;
        dest.m23 = 0.0;
        // rotateZ
        dest.m00 = nm00 * cosZ + nm10 * sinZ;
        dest.m01 = nm01 * cosZ + nm11 * sinZ;
        dest.m02 = nm02 * cosZ + nm12 * sinZ;
        dest.m03 = 0.0;
        dest.m10 = nm00 * m_sinZ + nm10 * cosZ;
        dest.m11 = nm01 * m_sinZ + nm11 * cosZ;
        dest.m12 = nm02 * m_sinZ + nm12 * cosZ;
        dest.m13 = 0.0;
        // copy last column from 'this'
        dest.m30 = m30;
        dest.m31 = m31;
        dest.m32 = m32;
        dest.m33 = m33;
        return dest;
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
     * @param angleAxis
     *          the {@link AxisAngle4d} (needs to be {@link AxisAngle4d#normalize() normalized})
     * @return this
     */
    public Matrix4d rotation(AxisAngle4d angleAxis) {
        return rotation(angleAxis.angle, angleAxis.x, angleAxis.y, angleAxis.z);
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
    public Matrix4d rotation(Quaterniond quat) {
        double dqx = 2.0 * quat.x;
        double dqy = 2.0 * quat.y;
        double dqz = 2.0 * quat.z;
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
    public Matrix4d rotation(Quaternionf quat) {
        double dqx = 2.0 * quat.x;
        double dqy = 2.0 * quat.y;
        double dqz = 2.0 * quat.z;
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
     * @see #rotate(Quaterniond)
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
     * @see #translation(Vector3f)
     * @see #rotate(Quaternionf)
     * 
     * @param translation
     *          the translation
     * @param quat
     *          the quaternion representing a rotation
     * @param scale
     *          the scaling factors
     * @return this
     */
    public Matrix4d translationRotateScale(Vector3f translation, 
                                           Quaternionf quat, 
                                           Vector3f scale) {
        return translationRotateScale(translation.x, translation.y, translation.z, quat.x, quat.y, quat.z, quat.w, scale.x, scale.y, scale.z);
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
     * @see #translation(Vector3d)
     * @see #rotate(Quaterniond)
     * 
     * @param translation
     *          the translation
     * @param quat
     *          the quaternion representing a rotation
     * @param scale
     *          the scaling factors
     * @return this
     */
    public Matrix4d translationRotateScale(Vector3d translation, 
                                           Quaterniond quat, 
                                           Vector3d scale) {
        return translationRotateScale(translation.x, translation.y, translation.z, quat.x, quat.y, quat.z, quat.w, scale.x, scale.y, scale.z);
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
     * @see #rotate(Quaterniond)
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
    public Matrix4d translationRotate(double tx, double ty, double tz, Quaterniond quat) {
        double dqx = 2.0 * quat.x, dqy = 2.0 * quat.y, dqz = 2.0 * quat.z;
        double q00 = dqx * quat.x;
        double q11 = dqy * quat.y;
        double q22 = dqz * quat.z;
        double q01 = dqx * quat.y;
        double q02 = dqx * quat.z;
        double q03 = dqx * quat.w;
        double q12 = dqy * quat.z;
        double q13 = dqy * quat.w;
        double q23 = dqz * quat.w;
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
    public Matrix4d rotate(Quaterniond quat, Matrix4d dest) {
        double dqx = 2.0 * quat.x;
        double dqy = 2.0 * quat.y;
        double dqz = 2.0 * quat.z;
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

        return dest;
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
    public Matrix4d rotate(Quaternionf quat, Matrix4d dest) {
        double dqx = 2.0 * quat.x;
        double dqy = 2.0 * quat.y;
        double dqz = 2.0 * quat.z;
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
    public Matrix4d rotate(Quaterniond quat) {
        return rotate(quat, this);
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
    public Matrix4d rotate(Quaternionf quat) {
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
     * @return dest
     */
    public Matrix4d rotate(AxisAngle4f axisAngle, Matrix4d dest) {
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
    public Matrix4d rotate(AxisAngle4d axisAngle) {
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
    public Matrix4d rotate(AxisAngle4d axisAngle, Matrix4d dest) {
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
    public Matrix4d rotate(double angle, Vector3d axis) {
        return rotate(angle, axis.x, axis.y, axis.z);
    }

    /**
     * Apply a rotation transformation, rotating the given radians about the specified axis and store the result in <code>dest</code>.
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
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Matrix4d rotate(double angle, Vector3d axis, Matrix4d dest) {
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
    public Matrix4d rotate(double angle, Vector3f axis) {
        return rotate(angle, axis.x, axis.y, axis.z);
    }

    /**
     * Apply a rotation transformation, rotating the given radians about the specified axis and store the result in <code>dest</code>.
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
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Matrix4d rotate(double angle, Vector3f axis, Matrix4d dest) {
        return rotate(angle, axis.x, axis.y, axis.z, dest);
    }

    /**
     * Get the row at the given <code>row</code> index, starting with <code>0</code>.
     * 
     * @param row
     *          the row index in <tt>[0..3]</tt>
     * @param dest
     *          will hold the row components
     * @return the passed in destination
     * @throws IndexOutOfBoundsException if <code>row</code> is not in <tt>[0..3]</tt>
     */
    public Vector4d getRow(int row, Vector4d dest) throws IndexOutOfBoundsException {
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
        
        return dest;
    }

    /**
     * Get the column at the given <code>column</code> index, starting with <code>0</code>.
     * 
     * @param column
     *          the column index in <tt>[0..3]</tt>
     * @param dest
     *          will hold the column components
     * @return the passed in destination
     * @throws IndexOutOfBoundsException if <code>column</code> is not in <tt>[0..3]</tt>
     */
    public Vector4d getColumn(int column, Vector4d dest) throws IndexOutOfBoundsException {
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
        
        return dest;
    }

    /**
     * Compute a normal matrix from the upper left 3x3 submatrix of <code>this</code>
     * and store it into the upper left 3x3 submatrix of <code>dest</code>.
     * All other values of <code>dest</code> will be set to {@link #identity() identity}.
     * <p>
     * The normal matrix of <tt>m</tt> is the transpose of the inverse of <tt>m</tt>.
     * <p>
     * Please note that, if <code>this</code> is an orthogonal matrix or a matrix whose columns are orthogonal vectors, 
     * then this method <i>need not</i> be invoked, since in that case <code>this</code> itself is its normal matrix.
     * In that case, use {@link #set3x3(Matrix4d)} to set a given Matrix4d to only the upper left 3x3 submatrix
     * of a given matrix.
     * 
     * @see #set3x3(Matrix4d)
     * 
     * @param dest
     *             will hold the result
     * @return dest
     */
    public Matrix4d normal(Matrix4d dest) {
        double det = determinant3x3();
        double s = 1.0 / det;
        /* Invert and transpose in one go */
        dest.set((m11 * m22 - m21 * m12) * s,
                 (m20 * m12 - m10 * m22) * s,
                 (m10 * m21 - m20 * m11) * s,
                 0.0f,
                 (m21 * m02 - m01 * m22) * s,
                 (m00 * m22 - m20 * m02) * s,
                 (m20 * m01 - m00 * m21) * s,
                 0.0f,
                 (m01 * m12 - m11 * m02) * s,
                 (m10 * m02 - m00 * m12) * s,
                 (m00 * m11 - m10 * m01) * s,
                 0.0f,
                 0.0f, 0.0f, 0.0f, 1.0f);
        return dest;
    }

    /**
     * Compute a normal matrix from the upper left 3x3 submatrix of <code>this</code>
     * and store it into <code>dest</code>.
     * <p>
     * The normal matrix of <tt>m</tt> is the transpose of the inverse of <tt>m</tt>.
     * <p>
     * Please note that, if <code>this</code> is an orthogonal matrix or a matrix whose columns are orthogonal vectors, 
     * then this method <i>need not</i> be invoked, since in that case <code>this</code> itself is its normal matrix.
     * In that case, use {@link #set(Matrix3d)} to set a given Matrix3d to only the upper left 3x3 submatrix
     * of this matrix.
     * 
     * @see #set(Matrix3d)
     * 
     * @param dest
     *             will hold the result
     * @return dest
     */
    public Matrix3d normal(Matrix3d dest) {
        double det = determinant3x3();
        double s = 1.0 / det;
        /* Invert and transpose in one go */
        dest.m00 = (m11 * m22 - m21 * m12) * s;
        dest.m01 = (m20 * m12 - m10 * m22) * s;
        dest.m02 = (m10 * m21 - m20 * m11) * s;
        dest.m10 = (m21 * m02 - m01 * m22) * s;
        dest.m11 = (m00 * m22 - m20 * m02) * s;
        dest.m12 = (m20 * m01 - m00 * m21) * s;
        dest.m20 = (m01 * m12 - m11 * m02) * s;
        dest.m21 = (m10 * m02 - m00 * m12) * s;
        dest.m22 = (m00 * m11 - m10 * m01) * s;
        return dest;
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
     * Normalize the upper left 3x3 submatrix of this matrix and store the result in <code>dest</code>.
     * <p>
     * The resulting matrix will map unit vectors to unit vectors, though a pair of orthogonal input unit
     * vectors need not be mapped to a pair of orthogonal output vectors if the original matrix was not orthogonal itself
     * (i.e. had <i>skewing</i>).
     * 
     * @param dest
     *             will hold the result
     * @return dest
     */
    public Matrix4d normalize3x3(Matrix4d dest) {
        double invXlen = 1.0 / Math.sqrt(m00 * m00 + m01 * m01 + m02 * m02);
        double invYlen = 1.0 / Math.sqrt(m10 * m10 + m11 * m11 + m12 * m12);
        double invZlen = 1.0 / Math.sqrt(m20 * m20 + m21 * m21 + m22 * m22);
        dest.m00 = m00 * invXlen; dest.m01 = m01 * invXlen; dest.m02 = m02 * invXlen;
        dest.m10 = m10 * invYlen; dest.m11 = m11 * invYlen; dest.m12 = m12 * invYlen;
        dest.m20 = m20 * invZlen; dest.m21 = m21 * invZlen; dest.m22 = m22 * invZlen;
        return dest;
    }

    /**
     * Normalize the upper left 3x3 submatrix of this matrix and store the result in <code>dest</code>.
     * <p>
     * The resulting matrix will map unit vectors to unit vectors, though a pair of orthogonal input unit
     * vectors need not be mapped to a pair of orthogonal output vectors if the original matrix was not orthogonal itself
     * (i.e. had <i>skewing</i>).
     * 
     * @param dest
     *             will hold the result
     * @return dest
     */
    public Matrix3d normalize3x3(Matrix3d dest) {
        double invXlen = 1.0 / Math.sqrt(m00 * m00 + m01 * m01 + m02 * m02);
        double invYlen = 1.0 / Math.sqrt(m10 * m10 + m11 * m11 + m12 * m12);
        double invZlen = 1.0 / Math.sqrt(m20 * m20 + m21 * m21 + m22 * m22);
        dest.m00 = m00 * invXlen; dest.m01 = m01 * invXlen; dest.m02 = m02 * invXlen;
        dest.m10 = m10 * invYlen; dest.m11 = m11 * invYlen; dest.m12 = m12 * invYlen;
        dest.m20 = m20 * invZlen; dest.m21 = m21 * invZlen; dest.m22 = m22 * invZlen;
        return dest;
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
     * @return dest
     */
    public Vector4d unproject(double winX, double winY, double winZ, IntBuffer viewport, Matrix4d inverseOut, Vector4d dest) {
        this.invert(inverseOut);
        inverseOut.unprojectInv(winX, winY, winZ, viewport, dest);
        return dest;
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
     * @return dest
     */
    public Vector3d unproject(double winX, double winY, double winZ, IntBuffer viewport, Matrix4d inverseOut, Vector3d dest) {
        this.invert(inverseOut);
        inverseOut.unprojectInv(winX, winY, winZ, viewport, dest);
        return dest;
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
     * @return dest
     */
    public Vector4d unproject(Vector3d winCoords, IntBuffer viewport, Matrix4d inverseOut, Vector4d dest) {
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
     * @return dest
     */
    public Vector3d unproject(Vector3d winCoords, IntBuffer viewport, Matrix4d inverseOut, Vector3d dest) {
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
     * @return dest
     */
    public Vector4d unprojectInv(Vector3d winCoords, IntBuffer viewport, Vector4d dest) {
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
     * @return dest
     */
    public Vector4d unprojectInv(double winX, double winY, double winZ, IntBuffer viewport, Vector4d dest) {
        int pos = viewport.position();
        double ndcX = (winX-viewport.get(pos))/viewport.get(pos+2)*2.0-1.0;
        double ndcY = (winY-viewport.get(pos+1))/viewport.get(pos+3)*2.0-1.0;
        double ndcZ = 2.0*winZ-1.0;
        dest.x = m00 * ndcX + m10 * ndcY + m20 * ndcZ + m30;
        dest.y = m01 * ndcX + m11 * ndcY + m21 * ndcZ + m31;
        dest.z = m02 * ndcX + m12 * ndcY + m22 * ndcZ + m32;
        dest.w = m03 * ndcX + m13 * ndcY + m23 * ndcZ + m33;
        dest.div(dest.w);
        return dest;
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
     * @return dest
     */
    public Vector3d unprojectInv(Vector3d winCoords, IntBuffer viewport, Vector3d dest) {
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
     * @return dest
     */
    public Vector3d unprojectInv(double winX, double winY, double winZ, IntBuffer viewport, Vector3d dest) {
        int pos = viewport.position();
        double ndcX = (winX-viewport.get(pos))/viewport.get(pos+2)*2.0-1.0;
        double ndcY = (winY-viewport.get(pos+1))/viewport.get(pos+3)*2.0-1.0;
        double ndcZ = 2.0*winZ-1.0;
        dest.x = m00 * ndcX + m10 * ndcY + m20 * ndcZ + m30;
        dest.y = m01 * ndcX + m11 * ndcY + m21 * ndcZ + m31;
        dest.z = m02 * ndcX + m12 * ndcY + m22 * ndcZ + m32;
        double w = m03 * ndcX + m13 * ndcY + m23 * ndcZ + m33;
        dest.div(w);
        return dest;
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
     * @return winCoordsDest
     */
    public Vector4d project(double x, double y, double z, IntBuffer viewport, Vector4d winCoordsDest) {
        winCoordsDest.x = m00 * x + m10 * y + m20 * z + m30;
        winCoordsDest.y = m01 * x + m11 * y + m21 * z + m31;
        winCoordsDest.z = m02 * x + m12 * y + m22 * z + m32;
        winCoordsDest.w = m03 * x + m13 * y + m23 * z + m33;
        int pos = viewport.position();
        winCoordsDest.div(winCoordsDest.w);
        winCoordsDest.x = (winCoordsDest.x*0.5+0.5) * viewport.get(pos+2) + viewport.get(pos);
        winCoordsDest.y = (winCoordsDest.y*0.5+0.5) * viewport.get(pos+3) + viewport.get(pos+1);
        winCoordsDest.z = (1.0+winCoordsDest.z)*0.5;
        return winCoordsDest;
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
     * @return winCoordsDest
     */
    public Vector3d project(double x, double y, double z, IntBuffer viewport, Vector3d winCoordsDest) {
        winCoordsDest.x = m00 * x + m10 * y + m20 * z + m30;
        winCoordsDest.y = m01 * x + m11 * y + m21 * z + m31;
        winCoordsDest.z = m02 * x + m12 * y + m22 * z + m32;
        double w = m03 * x + m13 * y + m23 * z + m33;
        int pos = viewport.position();
        winCoordsDest.div(w);
        winCoordsDest.x = (winCoordsDest.x*0.5+0.5) * viewport.get(pos+2) + viewport.get(pos);
        winCoordsDest.y = (winCoordsDest.y*0.5+0.5) * viewport.get(pos+3) + viewport.get(pos+1);
        winCoordsDest.z = (1.0+winCoordsDest.z)*0.5;
        return winCoordsDest;
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
     * @return winCoordsDest
     */
    public Vector4d project(Vector3d position, IntBuffer viewport, Vector4d winCoordsDest) {
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
     * @return winCoordsDest
     */
    public Vector3d project(Vector3d position, IntBuffer viewport, Vector3d winCoordsDest) {
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
        winCoordsDest.div(winCoordsDest.w);
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
     * @return dest
     */
    public Matrix4d reflect(double a, double b, double c, double d, Matrix4d dest) {
        double da = 2.0 * a, db = 2.0 * b, dc = 2.0 * c, dd = 2.0 * d;
        double rm00 = 1.0 - da * a;
        double rm01 = -da * b;
        double rm02 = -da * c;
        double rm10 = -db * a;
        double rm11 = 1.0 - db * b;
        double rm12 = -db * c;
        double rm20 = -dc * a;
        double rm21 = -dc * b;
        double rm22 = 1.0 - dc * c;
        double rm30 = -dd * a;
        double rm31 = -dd * b;
        double rm32 = -dd * c;

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

        return dest;
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
     * @return dest
     */
    public Matrix4d reflect(double nx, double ny, double nz, double px, double py, double pz, Matrix4d dest) {
        double invLength = 1.0 / Math.sqrt(nx * nx + ny * ny + nz * nz);
        double nnx = nx * invLength;
        double nny = ny * invLength;
        double nnz = nz * invLength;
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
     *          the plane orientation relative to an implied normal vector of <tt>(0, 0, 1)</tt>
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
     * @return dest
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
     * @return dest
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
     * @return dest
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
        // compute the last column first, because other columns do not depend on it
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

        return dest;
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
     * @return dest
     */
    public Matrix4d orthoSymmetric(double width, double height, double zNear, double zFar, Matrix4d dest) {
        // calculate right matrix elements
        double rm00 = 2.0 / width;
        double rm11 = 2.0 / height;
        double rm22 = -2.0 / (zFar - zNear);
        double rm32 = -(zFar + zNear) / (zFar - zNear);

        // perform optimized multiplication
        // compute the last column first, because other columns do not depend on it
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

        return dest;
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
     * @see #ortho(double, double, double, double, double, double, Matrix4d)
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
     * @return dest
     */
    public Matrix4d ortho2D(double left, double right, double bottom, double top, Matrix4d dest) {
        // calculate right matrix elements
        double rm00 = 2.0 / (right - left);
        double rm11 = 2.0 / (top - bottom);
        double rm30 = -(right + left) / (right - left);
        double rm31 = -(top + bottom) / (top - bottom);

        // perform optimized multiplication
        // compute the last column first, because other columns do not depend on it
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

        return dest;
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
     * @return dest
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
     * @return dest
     */
    public Matrix4d lookAlong(double dirX, double dirY, double dirZ,
                              double upX, double upY, double upZ, Matrix4d dest) {
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
     * @return dest
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
     * @return dest
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

        return dest;
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
     *            the vertical field of view in radians
     * @param aspect
     *            the aspect ratio (i.e. width / height)
     * @param zNear
     *            near clipping plane distance
     * @param zFar
     *            far clipping plane distance
     * @param dest
     *            will hold the result
     * @return dest
     */
    public Matrix4d perspective(double fovy, double aspect, double zNear, double zFar, Matrix4d dest) {
        double h = Math.tan(fovy * 0.5) * zNear;
        double w = h * aspect;

        // calculate right matrix elements
        double rm00 = zNear / w;
        double rm11 = zNear / h;
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

        return dest;
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
     * @return dest
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

        return dest;
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
     * Calculate a frustum plane of the this matrix, which
     * can be a projection matrix or a combined modelview-projection matrix, and store the result
     * in the given <code>planeEquation</code>.
     * <p>
     * Generally, this method computes the frustum plane in the local frame of
     * any coordinate system that existed before <code>this</code>
     * transformation was applied to it in order to yield homogeneous clipping space.
     * <p>
     * The frustum plane will be given in the form of a general plane equation:
     * <tt>a*x + b*y + c*z + d = 0</tt>, where the given {@link Vector4d} components will
     * hold the <tt>(a, b, c, d)</tt> values of the equation.
     * <p>
     * The plane normal, which is <tt>(a, b, c)</tt>, is directed "inwards" of the frustum.
     * Any plane/point test using <tt>a*x + b*y + c*z + d</tt> therefore will yield a result greater than zero
     * if the point is within the frustum (i.e. at the <i>positive</i> side of the frustum plane).
     * <p>
     * Reference: <a href="http://www.cs.otago.ac.nz/postgrads/alexis/planeExtraction.pdf">
     * Fast Extraction of Viewing Frustum Planes from the World-View-Projection Matrix</a>
     *
     * @param plane
     *          one of the six possible planes, given as numeric constants
     *          {@link #PLANE_NX}, {@link #PLANE_PX},
     *          {@link #PLANE_NY}, {@link #PLANE_PY}, 
     *          {@link #PLANE_NZ} and {@link #PLANE_PZ}
     * @param planeEquation
     *          will hold the computed plane equation.
     *          The plane equation will be normalized, meaning that <tt>(a, b, c)</tt> will be a unit vector
     * @return planeEquation
     */
    public Vector4d frustumPlane(int plane, Vector4d planeEquation) {
        switch (plane) {
        case PLANE_NX:
            planeEquation.set(m03 + m00, m13 + m10, m23 + m20, m33 + m30).normalize3();
            break;
        case PLANE_PX:
            planeEquation.set(m03 - m00, m13 - m10, m23 - m20, m33 - m30).normalize3();
            break;
        case PLANE_NY:
            planeEquation.set(m03 + m01, m13 + m11, m23 + m21, m33 + m31).normalize3();
            break;
        case PLANE_PY:
            planeEquation.set(m03 - m01, m13 - m11, m23 - m21, m33 - m31).normalize3();
            break;
        case PLANE_NZ:
            planeEquation.set(m03 + m02, m13 + m12, m23 + m22, m33 + m32).normalize3();
            break;
        case PLANE_PZ:
            planeEquation.set(m03 - m02, m13 - m12, m23 - m22, m33 - m32).normalize3();
            break;
        default:
            throw new IllegalArgumentException("plane"); //$NON-NLS-1$
        }
        return planeEquation;
    }

    /**
     * Compute the corner coordinates of the frustum defined by <code>this</code> matrix, which
     * can be a projection matrix or a combined modelview-projection matrix, and store the result
     * in the given <code>point</code>.
     * <p>
     * Generally, this method computes the frustum corners in the local frame of
     * any coordinate system that existed before <code>this</code>
     * transformation was applied to it in order to yield homogeneous clipping space.
     * <p>
     * Reference: <a href="http://geomalgorithms.com/a05-_intersect-1.html">http://geomalgorithms.com</a>
     * <p>
     * Reference: <a href="http://www.cs.otago.ac.nz/postgrads/alexis/planeExtraction.pdf">
     * Fast Extraction of Viewing Frustum Planes from the World-View-Projection Matrix</a>
     * 
     * @param corner
     *          one of the eight possible corners, given as numeric constants
     *          {@link #CORNER_NXNYNZ}, {@link #CORNER_PXNYNZ}, {@link #CORNER_PXPYNZ}, {@link #CORNER_NXPYNZ},
     *          {@link #CORNER_PXNYPZ}, {@link #CORNER_NXNYPZ}, {@link #CORNER_NXPYPZ}, {@link #CORNER_PXPYPZ}
     * @param point
     *          will hold the resulting corner point coordinates
     * @return point
     */
    public Vector3d frustumCorner(int corner, Vector3d point) {
        double d1, d2, d3;
        double n1x, n1y, n1z, n2x, n2y, n2z, n3x, n3y, n3z;
        switch (corner) {
        case CORNER_NXNYNZ: // left, bottom, near
            n1x = m03 + m00; n1y = m13 + m10; n1z = m23 + m20; d1 = m33 + m30; // left
            n2x = m03 + m01; n2y = m13 + m11; n2z = m23 + m21; d2 = m33 + m31; // bottom
            n3x = m03 + m02; n3y = m13 + m12; n3z = m23 + m22; d3 = m33 + m32; // near
            break;
        case CORNER_PXNYNZ: // right, bottom, near
            n1x = m03 - m00; n1y = m13 - m10; n1z = m23 - m20; d1 = m33 - m30; // right
            n2x = m03 + m01; n2y = m13 + m11; n2z = m23 + m21; d2 = m33 + m31; // bottom
            n3x = m03 + m02; n3y = m13 + m12; n3z = m23 + m22; d3 = m33 + m32; // near
            break;
        case CORNER_PXPYNZ: // right, top, near
            n1x = m03 - m00; n1y = m13 - m10; n1z = m23 - m20; d1 = m33 - m30; // right
            n2x = m03 - m01; n2y = m13 - m11; n2z = m23 - m21; d2 = m33 - m31; // top
            n3x = m03 + m02; n3y = m13 + m12; n3z = m23 + m22; d3 = m33 + m32; // near
            break;
        case CORNER_NXPYNZ: // left, top, near
            n1x = m03 + m00; n1y = m13 + m10; n1z = m23 + m20; d1 = m33 + m30; // left
            n2x = m03 - m01; n2y = m13 - m11; n2z = m23 - m21; d2 = m33 - m31; // top
            n3x = m03 + m02; n3y = m13 + m12; n3z = m23 + m22; d3 = m33 + m32; // near
            break;
        case CORNER_PXNYPZ: // right, bottom, far
            n1x = m03 - m00; n1y = m13 - m10; n1z = m23 - m20; d1 = m33 - m30; // right
            n2x = m03 + m01; n2y = m13 + m11; n2z = m23 + m21; d2 = m33 + m31; // bottom
            n3x = m03 - m02; n3y = m13 - m12; n3z = m23 - m22; d3 = m33 - m32; // far
            break;
        case CORNER_NXNYPZ: // left, bottom, far
            n1x = m03 + m00; n1y = m13 + m10; n1z = m23 + m20; d1 = m33 + m30; // left
            n2x = m03 + m01; n2y = m13 + m11; n2z = m23 + m21; d2 = m33 + m31; // bottom
            n3x = m03 - m02; n3y = m13 - m12; n3z = m23 - m22; d3 = m33 - m32; // far
            break;
        case CORNER_NXPYPZ: // left, top, far
            n1x = m03 + m00; n1y = m13 + m10; n1z = m23 + m20; d1 = m33 + m30; // left
            n2x = m03 - m01; n2y = m13 - m11; n2z = m23 - m21; d2 = m33 - m31; // top
            n3x = m03 - m02; n3y = m13 - m12; n3z = m23 - m22; d3 = m33 - m32; // far
            break;
        case CORNER_PXPYPZ: // right, top, far
            n1x = m03 - m00; n1y = m13 - m10; n1z = m23 - m20; d1 = m33 - m30; // right
            n2x = m03 - m01; n2y = m13 - m11; n2z = m23 - m21; d2 = m33 - m31; // top
            n3x = m03 - m02; n3y = m13 - m12; n3z = m23 - m22; d3 = m33 - m32; // far
            break;
        default:
            throw new IllegalArgumentException("corner"); //$NON-NLS-1$
        }
        double c23x, c23y, c23z;
        c23x = n2y * n3z - n2z * n3y;
        c23y = n2z * n3x - n2x * n3z;
        c23z = n2x * n3y - n2y * n3x;
        double c31x, c31y, c31z;
        c31x = n3y * n1z - n3z * n1y;
        c31y = n3z * n1x - n3x * n1z;
        c31z = n3x * n1y - n3y * n1x;
        double c12x, c12y, c12z;
        c12x = n1y * n2z - n1z * n2y;
        c12y = n1z * n2x - n1x * n2z;
        c12z = n1x * n2y - n1y * n2x;
        double invDot = 1.0 / (n1x * c23x + n1y * c23y + n1z * c23z);
        point.x = (-c23x * d1 - c31x * d2 - c12x * d3) * invDot;
        point.y = (-c23y * d1 - c31y * d2 - c12y * d3) * invDot;
        point.z = (-c23z * d1 - c31z * d2 - c12z * d3) * invDot;
        return point;
    }

    /**
     * Compute the eye/origin of the perspective frustum transformation defined by <code>this</code> matrix, 
     * which can be a projection matrix or a combined modelview-projection matrix, and store the result
     * in the given <code>origin</code>.
     * <p>
     * Note that this method will only work using perspective projections obtained via one of the
     * perspective methods, such as {@link #perspective(double, double, double, double) perspective()}
     * or {@link #frustum(double, double, double, double, double, double) frustum()}.
     * <p>
     * Generally, this method computes the origin in the local frame of
     * any coordinate system that existed before <code>this</code>
     * transformation was applied to it in order to yield homogeneous clipping space.
     * <p>
     * Reference: <a href="http://geomalgorithms.com/a05-_intersect-1.html">http://geomalgorithms.com</a>
     * <p>
     * Reference: <a href="http://www.cs.otago.ac.nz/postgrads/alexis/planeExtraction.pdf">
     * Fast Extraction of Viewing Frustum Planes from the World-View-Projection Matrix</a>
     * 
     * @param origin
     *          will hold the origin of the coordinate system before applying <code>this</code>
     *          perspective projection transformation
     * @return origin
     */
    public Vector3d perspectiveOrigin(Vector3d origin) {
        /*
         * Simply compute the intersection point of the left, right and top frustum plane.
         */
    	double d1, d2, d3;
        double n1x, n1y, n1z, n2x, n2y, n2z, n3x, n3y, n3z;
        n1x = m03 + m00; n1y = m13 + m10; n1z = m23 + m20; d1 = m33 + m30; // left
        n2x = m03 - m00; n2y = m13 - m10; n2z = m23 - m20; d2 = m33 - m30; // right
        n3x = m03 - m01; n3y = m13 - m11; n3z = m23 - m21; d3 = m33 - m31; // top
        double c23x, c23y, c23z;
        c23x = n2y * n3z - n2z * n3y;
        c23y = n2z * n3x - n2x * n3z;
        c23z = n2x * n3y - n2y * n3x;
        double c31x, c31y, c31z;
        c31x = n3y * n1z - n3z * n1y;
        c31y = n3z * n1x - n3x * n1z;
        c31z = n3x * n1y - n3y * n1x;
        double c12x, c12y, c12z;
        c12x = n1y * n2z - n1z * n2y;
        c12y = n1z * n2x - n1x * n2z;
        c12z = n1x * n2y - n1y * n2x;
        double invDot = 1.0 / (n1x * c23x + n1y * c23y + n1z * c23z);
        origin.x = (-c23x * d1 - c31x * d2 - c12x * d3) * invDot;
        origin.y = (-c23y * d1 - c31y * d2 - c12y * d3) * invDot;
        origin.z = (-c23z * d1 - c31z * d2 - c12z * d3) * invDot;
        return origin;
    }

    /**
     * Return the vertical field-of-view angle in radians of this perspective transformation matrix.
     * <p>
     * Note that this method will only work using perspective projections obtained via one of the
     * perspective methods, such as {@link #perspective(double, double, double, double) perspective()}
     * or {@link #frustum(double, double, double, double, double, double) frustum()}.
     * <p>
     * For orthogonal transformations this method will return <tt>0.0</tt>.
     * <p>
     * Reference: <a href="http://www.cs.otago.ac.nz/postgrads/alexis/planeExtraction.pdf">
     * Fast Extraction of Viewing Frustum Planes from the World-View-Projection Matrix</a>
     * 
     * @return the vertical field-of-view angle in radians
     */
    public double perspectiveFov() {
        /*
         * Compute the angle between the bottom and top frustum plane normals.
         */
        double n1x, n1y, n1z, n2x, n2y, n2z;
        n1x = m03 + m01; n1y = m13 + m11; n1z = m23 + m21; // bottom
        n2x = m01 - m03; n2y = m11 - m13; n2z = m21 - m23; // top
        double n1len = Math.sqrt(n1x * n1x + n1y * n1y + n1z * n1z);
        double n2len = Math.sqrt(n2x * n2x + n2y * n2y + n2z * n2z);
        return Math.acos((n1x * n2x + n1y * n2y + n1z * n2z) / (n1len * n2len));
    }

    /**
     * Obtain the direction of a ray starting at the center of the coordinate system and going
     * through the near frustum plane.
     * <p>
     * This method computes the <code>dir</code> vector in the local frame of
     * any coordinate system that existed before <code>this</code>
     * transformation was applied to it in order to yield homogeneous clipping space.
     * <p>
     * The parameters <code>x</code> and <code>y</code> are used to interpolate the generated ray direction
     * from the bottom-left to the top-right frustum corners.
     * <p>
     * For optimal efficiency when building many ray directions over the whole frustum,
     * it is recommended to use this method only in order to compute the four corner rays at
     * <tt>(0, 0)</tt>, <tt>(1, 0)</tt>, <tt>(0, 1)</tt> and <tt>(1, 1)</tt>
     * and then bilinearly interpolating between them; or to use the {@link FrustumRayBuilder}.
     * <p>
     * Reference: <a href="http://www.cs.otago.ac.nz/postgrads/alexis/planeExtraction.pdf">
     * Fast Extraction of Viewing Frustum Planes from the World-View-Projection Matrix</a>
     * 
     * @param x
     *          the interpolation factor along the left-to-right frustum planes, within <tt>[0..1]</tt>
     * @param y
     *          the interpolation factor along the bottom-to-top frustum planes, within <tt>[0..1]</tt>
     * @param dir
     *          will hold the normalized ray direction in the local frame of the coordinate system before 
     *          transforming to homogeneous clipping space using <code>this</code> matrix
     * @return dir
     */
    public Vector3d frustumRayDir(double x, double y, Vector3d dir) {
        /*
         * This method works by first obtaining the frustum plane normals,
         * then building the cross product to obtain the corner rays,
         * and finall bilinearly interpolating to obtain the desired direction.
         * The code below uses a condense form of doing all this making use 
         * of some mathematical identities to simplify the overall expression.
         */
        double a = m10 * m23, b = m13 * m21, c = m10 * m21, d = m11 * m23, e = m13 * m20, f = m11 * m20;
        double g = m03 * m20, h = m01 * m23, i = m01 * m20, j = m03 * m21, k = m00 * m23, l = m00 * m21;
        double m = m00 * m13, n = m03 * m11, o = m00 * m11, p = m01 * m13, q = m03 * m10, r = m01 * m10;
        double m1x, m1y, m1z;
        m1x = (d + e + f - a - b - c) * (1.0 - y) + (a - b - c + d - e + f) * y;
        m1y = (j + k + l - g - h - i) * (1.0 - y) + (g - h - i + j - k + l) * y;
        m1z = (p + q + r - m - n - o) * (1.0 - y) + (m - n - o + p - q + r) * y;
        double m2x, m2y, m2z;
        m2x = (b - c - d + e + f - a) * (1.0 - y) + (a + b - c - d - e + f) * y;
        m2y = (h - i - j + k + l - g) * (1.0 - y) + (g + h - i - j - k + l) * y;
        m2z = (n - o - p + q + r - m) * (1.0 - y) + (m + n - o - p - q + r) * y;
        dir.x = m1x * (1.0 - x) + m2x * x;
        dir.y = m1y * (1.0 - x) + m2y * x;
        dir.z = m1z * (1.0 - x) + m2z * x;
        dir.normalize();
        return dir;
    }

    /**
     * Obtain the direction of <tt>+Z</tt> before the orthogonal transformation represented by
     * <code>this</code> matrix is applied.
     * <p>
     * This method uses the rotation component of the upper left 3x3 submatrix to obtain the direction 
     * that is transformed to <tt>+Z</tt> by <code>this</code> matrix.
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
     * Obtain the direction of <tt>+X</tt> before the orthogonal transformation represented by
     * <code>this</code> matrix is applied.
     * <p>
     * This method uses the rotation component of the upper left 3x3 submatrix to obtain the direction 
     * that is transformed to <tt>+X</tt> by <code>this</code> matrix.
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
     * Obtain the direction of <tt>+Y</tt> before the orthogonal transformation represented by
     * <code>this</code> matrix is applied.
     * <p>
     * This method uses the rotation component of the upper left 3x3 submatrix to obtain the direction 
     * that is transformed to <tt>+Y</tt> by <code>this</code> matrix.
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
     * <tt>x*a + y*b + z*c + d = 0</tt> as if casting a shadow from a given light position/direction <code>light</code>
     * and store the result in <code>dest</code>.
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
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Matrix4d shadow(Vector4d light, double a, double b, double c, double d, Matrix4d dest) {
        return shadow(light.x, light.y, light.z, light.w, a, b, c, d, dest);
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
     * Apply a projection transformation to this matrix that projects onto the plane specified via the general plane equation
     * <tt>x*a + y*b + z*c + d = 0</tt> as if casting a shadow from a given light position/direction <tt>(lightX, lightY, lightZ, lightW)</tt>
     * and store the result in <code>dest</code>.
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
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Matrix4d shadow(double lightX, double lightY, double lightZ, double lightW, double a, double b, double c, double d, Matrix4d dest) {
        // normalize plane
        double invPlaneLen = 1.0 / Math.sqrt(a*a + b*b + c*c);
        double an = a * invPlaneLen;
        double bn = b * invPlaneLen;
        double cn = c * invPlaneLen;
        double dn = d * invPlaneLen;

        double dot = an * lightX + bn * lightY + cn * lightZ + dn * lightW;

        // compute right matrix elements
        double rm00 = dot - an * lightX;
        double rm01 = -an * lightY;
        double rm02 = -an * lightZ;
        double rm03 = -an * lightW;
        double rm10 = -bn * lightX;
        double rm11 = dot - bn * lightY;
        double rm12 = -bn * lightZ;
        double rm13 = -bn * lightW;
        double rm20 = -cn * lightX;
        double rm21 = -cn * lightY;
        double rm22 = dot - cn * lightZ;
        double rm23 = -cn * lightW;
        double rm30 = -dn * lightX;
        double rm31 = -dn * lightY;
        double rm32 = -dn * lightZ;
        double rm33 = dot - dn * lightW;

        // matrix multiplication
        double nm00 = m00 * rm00 + m10 * rm01 + m20 * rm02 + m30 * rm03;
        double nm01 = m01 * rm00 + m11 * rm01 + m21 * rm02 + m31 * rm03;
        double nm02 = m02 * rm00 + m12 * rm01 + m22 * rm02 + m32 * rm03;
        double nm03 = m03 * rm00 + m13 * rm01 + m23 * rm02 + m33 * rm03;
        double nm10 = m00 * rm10 + m10 * rm11 + m20 * rm12 + m30 * rm13;
        double nm11 = m01 * rm10 + m11 * rm11 + m21 * rm12 + m31 * rm13;
        double nm12 = m02 * rm10 + m12 * rm11 + m22 * rm12 + m32 * rm13;
        double nm13 = m03 * rm10 + m13 * rm11 + m23 * rm12 + m33 * rm13;
        double nm20 = m00 * rm20 + m10 * rm21 + m20 * rm22 + m30 * rm23;
        double nm21 = m01 * rm20 + m11 * rm21 + m21 * rm22 + m31 * rm23;
        double nm22 = m02 * rm20 + m12 * rm21 + m22 * rm22 + m32 * rm23;
        double nm23 = m03 * rm20 + m13 * rm21 + m23 * rm22 + m33 * rm23;
        dest.m30 = m00 * rm30 + m10 * rm31 + m20 * rm32 + m30 * rm33;
        dest.m31 = m01 * rm30 + m11 * rm31 + m21 * rm32 + m31 * rm33;
        dest.m32 = m02 * rm30 + m12 * rm31 + m22 * rm32 + m32 * rm33;
        dest.m33 = m03 * rm30 + m13 * rm31 + m23 * rm32 + m33 * rm33;
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

        return dest;
    }

    /**
     * Apply a projection transformation to this matrix that projects onto the plane with the general plane equation
     * <tt>y = 0</tt> as if casting a shadow from a given light position/direction <code>light</code>
     * and store the result in <code>dest</code>.
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
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Matrix4d shadow(Vector4d light, Matrix4d planeTransform, Matrix4d dest) {
        // compute plane equation by transforming (y = 0)
        double a = planeTransform.m10;
        double b = planeTransform.m11;
        double c = planeTransform.m12;
        double d = -a * planeTransform.m30 - b * planeTransform.m31 - c * planeTransform.m32;
        return shadow(light.x, light.y, light.z, light.w, a, b, c, d, dest);
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
    public Matrix4d shadow(Vector4d light, Matrix4d planeTransform) {
        return shadow(light, planeTransform, this);
    }

    /**
     * Apply a projection transformation to this matrix that projects onto the plane with the general plane equation
     * <tt>y = 0</tt> as if casting a shadow from a given light position/direction <tt>(lightX, lightY, lightZ, lightW)</tt>
     * and store the result in <code>dest</code>.
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
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Matrix4d shadow(double lightX, double lightY, double lightZ, double lightW, Matrix4d planeTransform, Matrix4d dest) {
        // compute plane equation by transforming (y = 0)
        double a = planeTransform.m10;
        double b = planeTransform.m11;
        double c = planeTransform.m12;
        double d = -a * planeTransform.m30 - b * planeTransform.m31 - c * planeTransform.m32;
        return shadow(lightX, lightY, lightZ, lightW, a, b, c, d, dest);
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
    public Matrix4d shadow(double lightX, double lightY, double lightZ, double lightW, Matrix4d planeTransform) {
        return shadow(lightX, lightY, lightZ, lightW, planeTransform, this);
    }

}
