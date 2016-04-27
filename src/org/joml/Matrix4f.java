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
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;

/**
 * Contains the definition of a 4x4 Matrix of floats, and associated functions to transform
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
public class Matrix4f implements Externalizable {

    private static final long serialVersionUID = 1L;

    public static final int M00 = 0;
    public static final int M01 = 1;
    public static final int M02 = 2;
    public static final int M03 = 3;
    public static final int M10 = 4;
    public static final int M11 = 5;
    public static final int M12 = 6;
    public static final int M13 = 7;
    public static final int M20 = 8;
    public static final int M21 = 9;
    public static final int M22 = 10;
    public static final int M23 = 11;
    public static final int M30 = 12;
    public static final int M31 = 13;
    public static final int M32 = 14;
    public static final int M33 = 15;

    /**
     * Argument to the first parameter of {@link #frustumPlane(int, Vector4f)}
     * identifying the plane with equation <tt>x=-1</tt> when using the identity matrix.  
     */
    public static final int PLANE_NX = 0;
    /**
     * Argument to the first parameter of {@link #frustumPlane(int, Vector4f)}
     * identifying the plane with equation <tt>x=1</tt> when using the identity matrix.  
     */
    public static final int PLANE_PX = 1;
    /**
     * Argument to the first parameter of {@link #frustumPlane(int, Vector4f)}
     * identifying the plane with equation <tt>y=-1</tt> when using the identity matrix.  
     */
    public static final int PLANE_NY= 2;
    /**
     * Argument to the first parameter of {@link #frustumPlane(int, Vector4f)}
     * identifying the plane with equation <tt>y=1</tt> when using the identity matrix.  
     */
    public static final int PLANE_PY = 3;
    /**
     * Argument to the first parameter of {@link #frustumPlane(int, Vector4f)}
     * identifying the plane with equation <tt>z=-1</tt> when using the identity matrix.  
     */
    public static final int PLANE_NZ = 4;
    /**
     * Argument to the first parameter of {@link #frustumPlane(int, Vector4f)}
     * identifying the plane with equation <tt>z=1</tt> when using the identity matrix.  
     */
    public static final int PLANE_PZ = 5;

    /**
     * Argument to the first parameter of {@link #frustumCorner(int, Vector3f)}
     * identifying the corner <tt>(-1, -1, -1)</tt> when using the identity matrix.
     */
    public static final int CORNER_NXNYNZ = 0;
    /**
     * Argument to the first parameter of {@link #frustumCorner(int, Vector3f)}
     * identifying the corner <tt>(1, -1, -1)</tt> when using the identity matrix.
     */
    public static final int CORNER_PXNYNZ = 1;
    /**
     * Argument to the first parameter of {@link #frustumCorner(int, Vector3f)}
     * identifying the corner <tt>(1, 1, -1)</tt> when using the identity matrix.
     */
    public static final int CORNER_PXPYNZ = 2;
    /**
     * Argument to the first parameter of {@link #frustumCorner(int, Vector3f)}
     * identifying the corner <tt>(-1, 1, -1)</tt> when using the identity matrix.
     */
    public static final int CORNER_NXPYNZ = 3;
    /**
     * Argument to the first parameter of {@link #frustumCorner(int, Vector3f)}
     * identifying the corner <tt>(1, -1, 1)</tt> when using the identity matrix.
     */
    public static final int CORNER_PXNYPZ = 4;
    /**
     * Argument to the first parameter of {@link #frustumCorner(int, Vector3f)}
     * identifying the corner <tt>(-1, -1, 1)</tt> when using the identity matrix.
     */
    public static final int CORNER_NXNYPZ = 5;
    /**
     * Argument to the first parameter of {@link #frustumCorner(int, Vector3f)}
     * identifying the corner <tt>(-1, 1, 1)</tt> when using the identity matrix.
     */
    public static final int CORNER_NXPYPZ = 6;
    /**
     * Argument to the first parameter of {@link #frustumCorner(int, Vector3f)}
     * identifying the corner <tt>(1, 1, 1)</tt> when using the identity matrix.
     */
    public static final int CORNER_PXPYPZ = 7;

    /**
     * The components of this matrix in column-major order.
     */
    public final float[] ms = new float[16];

    /**
     * Create a new {@link Matrix4f} and set it to {@link #identity() identity}.
     */
    public Matrix4f() {
        ms[M00] = 1.0f;
        ms[M01] = 0.0f;
        ms[M02] = 0.0f;
        ms[M03] = 0.0f;
        ms[M10] = 0.0f;
        ms[M11] = 1.0f;
        ms[M12] = 0.0f;
        ms[M13] = 0.0f;
        ms[M20] = 0.0f;
        ms[M21] = 0.0f;
        ms[M22] = 1.0f;
        ms[M23] = 0.0f;
        ms[M30] = 0.0f;
        ms[M31] = 0.0f;
        ms[M32] = 0.0f;
        ms[M33] = 1.0f;
    }

    /**
     * Create a new {@link Matrix4f} by setting its uppper left 3x3 submatrix to the values of the given {@link Matrix3f}
     * and the rest to identity.
     * 
     * @param mat
     *          the {@link Matrix3f}
     */
    public Matrix4f(Matrix3f mat) {
        ms[M00] = mat.ms[Matrix3f.M00];
        ms[M01] = mat.ms[Matrix3f.M01];
        ms[M02] = mat.ms[Matrix3f.M02];
        ms[M10] = mat.ms[Matrix3f.M10];
        ms[M11] = mat.ms[Matrix3f.M11];
        ms[M12] = mat.ms[Matrix3f.M12];
        ms[M20] = mat.ms[Matrix3f.M20];
        ms[M21] = mat.ms[Matrix3f.M21];
        ms[M22] = mat.ms[Matrix3f.M22];
        ms[M33] = 1.0f;
    }

    /**
     * Create a new {@link Matrix4f} and make it a copy of the given matrix.
     * 
     * @param mat
     *          the {@link Matrix4f} to copy the values from
     */
    public Matrix4f(Matrix4f mat) {
        System.arraycopy(mat.ms, 0, ms, 0, 16);
    }

    /**
     * Create a new {@link Matrix4f} and make it a copy of the given matrix.
     * <p>
     * Note that due to the given {@link Matrix4d} storing values in double-precision and the constructed {@link Matrix4f} storing them
     * in single-precision, there is the possibility of losing precision.
     * 
     * @param mat
     *          the {@link Matrix4d} to copy the values from
     */
    public Matrix4f(Matrix4d mat) {
        ms[M00] = (float) mat.ms[Matrix4d.M00];
        ms[M01] = (float) mat.ms[Matrix4d.M01];
        ms[M02] = (float) mat.ms[Matrix4d.M02];
        ms[M03] = (float) mat.ms[Matrix4d.M03];
        ms[M10] = (float) mat.ms[Matrix4d.M10];
        ms[M11] = (float) mat.ms[Matrix4d.M11];
        ms[M12] = (float) mat.ms[Matrix4d.M12];
        ms[M13] = (float) mat.ms[Matrix4d.M13];
        ms[M20] = (float) mat.ms[Matrix4d.M20];
        ms[M21] = (float) mat.ms[Matrix4d.M21];
        ms[M22] = (float) mat.ms[Matrix4d.M22];
        ms[M23] = (float) mat.ms[Matrix4d.M23];
        ms[M30] = (float) mat.ms[Matrix4d.M30];
        ms[M31] = (float) mat.ms[Matrix4d.M31];
        ms[M32] = (float) mat.ms[Matrix4d.M32];
        ms[M33] = (float) mat.ms[Matrix4d.M33];
    }

    /**
     * Create a new 4x4 matrix using the supplied float values.
     * 
     * @param n00
     *          the value of ms[M00]
     * @param n01
     *          the value of ms[M01]
     * @param n02
     *          the value of ms[M02]
     * @param n03
     *          the value of ms[M03]
     * @param n10
     *          the value of ms[M10]
     * @param n11
     *          the value of ms[M11]
     * @param n12
     *          the value of ms[M12]
     * @param n13
     *          the value of ms[M13]
     * @param n20
     *          the value of ms[M20]
     * @param n21
     *          the value of ms[M21]
     * @param n22
     *          the value of ms[M22]
     * @param n23
     *          the value of ms[M23]
     * @param n30
     *          the value of ms[M30]
     * @param n31
     *          the value of ms[M31]
     * @param n32
     *          the value of ms[M32]
     * @param n33
     *          the value of ms[M33]
     */
    public Matrix4f(float n00, float n01, float n02, float n03,
                    float n10, float n11, float n12, float n13,
                    float n20, float n21, float n22, float n23, 
                    float n30, float n31, float n32, float n33) {
        ms[M00] = n00;
        ms[M01] = n01;
        ms[M02] = n02;
        ms[M03] = n03;
        ms[M10] = n10;
        ms[M11] = n11;
        ms[M12] = n12;
        ms[M13] = n13;
        ms[M20] = n20;
        ms[M21] = n21;
        ms[M22] = n22;
        ms[M23] = n23;
        ms[M30] = n30;
        ms[M31] = n31;
        ms[M32] = n32;
        ms[M33] = n33;
    }

    /**
     * Create a new {@link Matrix4f} by reading its 16 float components from the given {@link FloatBuffer}
     * at the buffer's current position.
     * <p>
     * That FloatBuffer is expected to hold the values in column-major order.
     * <p>
     * The buffer's position will not be changed by this method.
     * 
     * @param buffer
     *          the {@link FloatBuffer} to read the matrix values from
     */
    public Matrix4f(FloatBuffer buffer) {
        MemUtil.INSTANCE.get(this, 0, buffer);
    }

    /**
     * Return the value of the matrix element at column 0 and row 0.
     * 
     * @return the value of the matrix element
     */
    public float m00() {
        return ms[M00];
    }
    /**
     * Return the value of the matrix element at column 0 and row 1.
     * 
     * @return the value of the matrix element
     */
    public float m01() {
        return ms[M01];
    }
    /**
     * Return the value of the matrix element at column 0 and row 2.
     * 
     * @return the value of the matrix element
     */
    public float m02() {
        return ms[M02];
    }
    /**
     * Return the value of the matrix element at column 0 and row 3.
     * 
     * @return the value of the matrix element
     */
    public float m03() {
        return ms[M03];
    }
    /**
     * Return the value of the matrix element at column 1 and row 0.
     * 
     * @return the value of the matrix element
     */
    public float m10() {
        return ms[M10];
    }
    /**
     * Return the value of the matrix element at column 1 and row 1.
     * 
     * @return the value of the matrix element
     */
    public float m11() {
        return ms[M11];
    }
    /**
     * Return the value of the matrix element at column 1 and row 2.
     * 
     * @return the value of the matrix element
     */
    public float m12() {
        return ms[M12];
    }
    /**
     * Return the value of the matrix element at column 1 and row 3.
     * 
     * @return the value of the matrix element
     */
    public float m13() {
        return ms[M13];
    }
    /**
     * Return the value of the matrix element at column 2 and row 0.
     * 
     * @return the value of the matrix element
     */
    public float m20() {
        return ms[M20];
    }
    /**
     * Return the value of the matrix element at column 2 and row 1.
     * 
     * @return the value of the matrix element
     */
    public float m21() {
        return ms[M21];
    }
    /**
     * Return the value of the matrix element at column 2 and row 2.
     * 
     * @return the value of the matrix element
     */
    public float m22() {
        return ms[M22];
    }
    /**
     * Return the value of the matrix element at column 2 and row 3.
     * 
     * @return the value of the matrix element
     */
    public float m23() {
        return ms[M23];
    }
    /**
     * Return the value of the matrix element at column 3 and row 0.
     * 
     * @return the value of the matrix element
     */
    public float m30() {
        return ms[M30];
    }
    /**
     * Return the value of the matrix element at column 3 and row 1.
     * 
     * @return the value of the matrix element
     */
    public float m31() {
        return ms[M31];
    }
    /**
     * Return the value of the matrix element at column 3 and row 2.
     * 
     * @return the value of the matrix element
     */
    public float m32() {
        return ms[M32];
    }
    /**
     * Return the value of the matrix element at column 3 and row 3.
     * 
     * @return the value of the matrix element
     */
    public float m33() {
        return ms[M33];
    }

    /**
     * Set the value of the matrix element at column 0 and row 0
     * 
     * @param m00
     *          the new value
     * @return the value of the matrix element
     */
    public Matrix4f m00(float m00) {
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
    public Matrix4f m01(float m01) {
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
    public Matrix4f m02(float m02) {
        this.ms[M02] = m02;
        return this;
    }
    /**
     * Set the value of the matrix element at column 0 and row 3
     * 
     * @param m03
     *          the new value
     * @return the value of the matrix element
     */
    public Matrix4f m03(float m03) {
        this.ms[M03] = m03;
        return this;
    }
    /**
     * Set the value of the matrix element at column 1 and row 0
     * 
     * @param m10
     *          the new value
     * @return the value of the matrix element
     */
    public Matrix4f m10(float m10) {
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
    public Matrix4f m11(float m11) {
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
    public Matrix4f m12(float m12) {
        this.ms[M12] = m12;
        return this;
    }
    /**
     * Set the value of the matrix element at column 1 and row 3
     * 
     * @param m13
     *          the new value
     * @return the value of the matrix element
     */
    public Matrix4f m13(float m13) {
        this.ms[M13] = m13;
        return this;
    }
    /**
     * Set the value of the matrix element at column 2 and row 0
     * 
     * @param m20
     *          the new value
     * @return the value of the matrix element
     */
    public Matrix4f m20(float m20) {
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
    public Matrix4f m21(float m21) {
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
    public Matrix4f m22(float m22) {
        this.ms[M22] = m22;
        return this;
    }
    /**
     * Set the value of the matrix element at column 2 and row 3
     * 
     * @param m23
     *          the new value
     * @return the value of the matrix element
     */
    public Matrix4f m23(float m23) {
        this.ms[M23] = m23;
        return this;
    }
    /**
     * Set the value of the matrix element at column 3 and row 0
     * 
     * @param m30
     *          the new value
     * @return the value of the matrix element
     */
    public Matrix4f m30(float m30) {
        this.ms[M30] = m30;
        return this;
    }
    /**
     * Set the value of the matrix element at column 3 and row 1
     * 
     * @param m31
     *          the new value
     * @return the value of the matrix element
     */
    public Matrix4f m31(float m31) {
        this.ms[M31] = m31;
        return this;
    }
    /**
     * Set the value of the matrix element at column 3 and row 2
     * 
     * @param m32
     *          the new value
     * @return the value of the matrix element
     */
    public Matrix4f m32(float m32) {
        this.ms[M32] = m32;
        return this;
    }
    /**
     * Set the value of the matrix element at column 3 and row 3
     * 
     * @param m33
     *          the new value
     * @return this
     */
    public Matrix4f m33(float m33) {
        this.ms[M33] = m33;
        return this;
    }

    /**
     * Reset this matrix to the identity.
     * <p>
     * Please note that if a call to {@link #identity()} is immediately followed by a call to:
     * {@link #translate(float, float, float) translate}, 
     * {@link #rotate(float, float, float, float) rotate},
     * {@link #scale(float, float, float) scale},
     * {@link #perspective(float, float, float, float) perspective},
     * {@link #frustum(float, float, float, float, float, float) frustum},
     * {@link #ortho(float, float, float, float, float, float) ortho},
     * {@link #ortho2D(float, float, float, float) ortho2D},
     * {@link #lookAt(float, float, float, float, float, float, float, float, float) lookAt},
     * {@link #lookAlong(float, float, float, float, float, float) lookAlong},
     * or any of their overloads, then the call to {@link #identity()} can be omitted and the subsequent call replaced with:
     * {@link #translation(float, float, float) translation},
     * {@link #rotation(float, float, float, float) rotation},
     * {@link #scaling(float, float, float) scaling},
     * {@link #setPerspective(float, float, float, float) setPerspective},
     * {@link #setFrustum(float, float, float, float, float, float) setFrustum},
     * {@link #setOrtho(float, float, float, float, float, float) setOrtho},
     * {@link #setOrtho2D(float, float, float, float) setOrtho2D},
     * {@link #setLookAt(float, float, float, float, float, float, float, float, float) setLookAt},
     * {@link #setLookAlong(float, float, float, float, float, float) setLookAlong},
     * or any of their overloads.
     * 
     * @return this
     */
    public Matrix4f identity() {
        ms[M00] = 1.0f;
        ms[M01] = 0.0f;
        ms[M02] = 0.0f;
        ms[M03] = 0.0f;
        ms[M10] = 0.0f;
        ms[M11] = 1.0f;
        ms[M12] = 0.0f;
        ms[M13] = 0.0f;
        ms[M20] = 0.0f;
        ms[M21] = 0.0f;
        ms[M22] = 1.0f;
        ms[M23] = 0.0f;
        ms[M30] = 0.0f;
        ms[M31] = 0.0f;
        ms[M32] = 0.0f;
        ms[M33] = 1.0f;
        return this;
    }

    /**
     * Store the values of the given matrix <code>m</code> into <code>this</code> matrix.
     * 
     * @see #Matrix4f(Matrix4f)
     * @see #get(Matrix4f)
     * 
     * @param m
     *          the matrix to copy the values from
     * @return this
     */
    public Matrix4f set(Matrix4f m) {
        System.arraycopy(m.ms, 0, ms, 0, 16);
        return this;
    }

    /**
     * Store the values of the given matrix <code>m</code> into <code>this</code> matrix.
     * <p>
     * Note that due to the given matrix <code>m</code> storing values in double-precision and <code>this</code> matrix storing
     * them in single-precision, there is the possibility to lose precision.
     * 
     * @see #Matrix4f(Matrix4d)
     * @see #get(Matrix4d)
     * 
     * @param m
     *          the matrix to copy the values from
     * @return this
     */
    public Matrix4f set(Matrix4d m) {
        ms[M00] = (float) m.ms[Matrix4d.M00];
        ms[M01] = (float) m.ms[Matrix4d.M01];
        ms[M02] = (float) m.ms[Matrix4d.M02];
        ms[M03] = (float) m.ms[Matrix4d.M03];
        ms[M10] = (float) m.ms[Matrix4d.M10];
        ms[M11] = (float) m.ms[Matrix4d.M11];
        ms[M12] = (float) m.ms[Matrix4d.M12];
        ms[M13] = (float) m.ms[Matrix4d.M13];
        ms[M20] = (float) m.ms[Matrix4d.M20];
        ms[M21] = (float) m.ms[Matrix4d.M21];
        ms[M22] = (float) m.ms[Matrix4d.M22];
        ms[M23] = (float) m.ms[Matrix4d.M23];
        ms[M30] = (float) m.ms[Matrix4d.M30];
        ms[M31] = (float) m.ms[Matrix4d.M31];
        ms[M32] = (float) m.ms[Matrix4d.M32];
        ms[M33] = (float) m.ms[Matrix4d.M33];
        return this;
    }

    /**
     * Set the upper left 3x3 submatrix of this {@link Matrix4f} to the given {@link Matrix3f} 
     * and the rest to identity.
     * 
     * @see #Matrix4f(Matrix3f)
     * 
     * @param mat
     *          the {@link Matrix3f}
     * @return this
     */
    public Matrix4f set(Matrix3f mat) {
        ms[M00] = mat.ms[Matrix3f.M00];
        ms[M01] = mat.ms[Matrix3f.M01];
        ms[M02] = mat.ms[Matrix3f.M02];
        ms[M03] = 0.0f;
        ms[M10] = mat.ms[Matrix3f.M10];
        ms[M11] = mat.ms[Matrix3f.M11];
        ms[M12] = mat.ms[Matrix3f.M12];
        ms[M13] = 0.0f;
        ms[M20] = mat.ms[Matrix3f.M20];
        ms[M21] = mat.ms[Matrix3f.M21];
        ms[M22] = mat.ms[Matrix3f.M22];
        ms[M23] = 0.0f;
        ms[M30] = 0.0f;
        ms[M31] = 0.0f;
        ms[M32] = 0.0f;
        ms[M33] = 1.0f;
        return this;
    }

    /**
     * Set this matrix to be equivalent to the rotation specified by the given {@link AxisAngle4f}.
     * 
     * @param axisAngle
     *          the {@link AxisAngle4f}
     * @return this
     */
    public Matrix4f set(AxisAngle4f axisAngle) {
        float x = axisAngle.x;
        float y = axisAngle.y;
        float z = axisAngle.z;
        double angle = axisAngle.angle;
        double n = Math.sqrt(x*x + y*y + z*z);
        n = 1/n;
        x *= n;
        y *= n;
        z *= n;
        double c = Math.cos(angle);
        double s = Math.sin(angle);
        double omc = 1.0 - c;
        ms[M00] = (float)(c + x*x*omc);
        ms[M11] = (float)(c + y*y*omc);
        ms[M22] = (float)(c + z*z*omc);
        double tmp1 = x*y*omc;
        double tmp2 = z*s;
        ms[M10] = (float)(tmp1 - tmp2);
        ms[M01] = (float)(tmp1 + tmp2);
        tmp1 = x*z*omc;
        tmp2 = y*s;
        ms[M20] = (float)(tmp1 + tmp2);
        ms[M02] = (float)(tmp1 - tmp2);
        tmp1 = y*z*omc;
        tmp2 = x*s;
        ms[M21] = (float)(tmp1 - tmp2);
        ms[M12] = (float)(tmp1 + tmp2);
        ms[M30] = 0.0f;
        ms[M31] = 0.0f;
        ms[M32] = 0.0f;
        ms[M33] = 1.0f;
        return this;
    }

    /**
     * Set this matrix to be equivalent to the rotation specified by the given {@link AxisAngle4d}.
     * 
     * @param axisAngle
     *          the {@link AxisAngle4d}
     * @return this
     */
    public Matrix4f set(AxisAngle4d axisAngle) {
        double x = axisAngle.x;
        double y = axisAngle.y;
        double z = axisAngle.z;
        double angle = axisAngle.angle;
        double n = Math.sqrt(x*x + y*y + z*z);
        n = 1/n;
        x *= n;
        y *= n;
        z *= n;
        double c = Math.cos(angle);
        double s = Math.sin(angle);
        double omc = 1.0 - c;
        ms[M00] = (float)(c + x*x*omc);
        ms[M11] = (float)(c + y*y*omc);
        ms[M22] = (float)(c + z*z*omc);
        double tmp1 = x*y*omc;
        double tmp2 = z*s;
        ms[M10] = (float)(tmp1 - tmp2);
        ms[M01] = (float)(tmp1 + tmp2);
        tmp1 = x*z*omc;
        tmp2 = y*s;
        ms[M20] = (float)(tmp1 + tmp2);
        ms[M02] = (float)(tmp1 - tmp2);
        tmp1 = y*z*omc;
        tmp2 = x*s;
        ms[M21] = (float)(tmp1 - tmp2);
        ms[M12] = (float)(tmp1 + tmp2);
        ms[M30] = 0.0f;
        ms[M31] = 0.0f;
        ms[M32] = 0.0f;
        ms[M33] = 1.0f;
        return this;
    }

    /**
     * Set this matrix to be equivalent to the rotation specified by the given {@link Quaternionf}.
     * 
     * @see Quaternionf#get(Matrix4f)
     * 
     * @param q
     *          the {@link Quaternionf}
     * @return this
     */
    public Matrix4f set(Quaternionf q) {
        q.get(this);
        return this;
    }

    /**
     * Set this matrix to be equivalent to the rotation specified by the given {@link Quaterniond}.
     * 
     * @see Quaterniond#get(Matrix4f)
     * 
     * @param q
     *          the {@link Quaterniond}
     * @return this
     */
    public Matrix4f set(Quaterniond q) {
        q.get(this);
        return this;
    }

    /**
     * Set the upper left 3x3 submatrix of this {@link Matrix4f} to that of the given {@link Matrix4f} 
     * and the rest to identity.
     * 
     * @param mat
     *          the {@link Matrix4f}
     * @return this
     */
    public Matrix4f set3x3(Matrix4f mat) {
        ms[M00] = mat.ms[M00];
        ms[M01] = mat.ms[M01];
        ms[M02] = mat.ms[M02];
        ms[M03] = 0.0f;
        ms[M10] = mat.ms[M10];
        ms[M11] = mat.ms[M11];
        ms[M12] = mat.ms[M12];
        ms[M13] = 0.0f;
        ms[M20] = mat.ms[M20];
        ms[M21] = mat.ms[M21];
        ms[M22] = mat.ms[M22];
        ms[M23] = 0.0f;
        ms[M30] = 0.0f;
        ms[M31] = 0.0f;
        ms[M32] = 0.0f;
        ms[M33] = 1.0f;
        return this;
    }

    /**
     * Multiply this matrix by the supplied <code>right</code> matrix and store the result in <code>this</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the <code>right</code> matrix,
     * then the new matrix will be <code>M * R</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * R * v</code>, the
     * transformation of the right matrix will be applied first!
     *
     * @param right
     *          the right operand of the matrix multiplication
     * @return this
     */
    public Matrix4f mul(Matrix4f right) {
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
    public Matrix4f mul(Matrix4f right, Matrix4f dest) {
        dest.set(ms[M00] * right.ms[M00] + ms[M10] * right.ms[M01] + ms[M20] * right.ms[M02] + ms[M30] * right.ms[M03],
                 ms[M01] * right.ms[M00] + ms[M11] * right.ms[M01] + ms[M21] * right.ms[M02] + ms[M31] * right.ms[M03],
                 ms[M02] * right.ms[M00] + ms[M12] * right.ms[M01] + ms[M22] * right.ms[M02] + ms[M32] * right.ms[M03],
                 ms[M03] * right.ms[M00] + ms[M13] * right.ms[M01] + ms[M23] * right.ms[M02] + ms[M33] * right.ms[M03],
                 ms[M00] * right.ms[M10] + ms[M10] * right.ms[M11] + ms[M20] * right.ms[M12] + ms[M30] * right.ms[M13],
                 ms[M01] * right.ms[M10] + ms[M11] * right.ms[M11] + ms[M21] * right.ms[M12] + ms[M31] * right.ms[M13],
                 ms[M02] * right.ms[M10] + ms[M12] * right.ms[M11] + ms[M22] * right.ms[M12] + ms[M32] * right.ms[M13],
                 ms[M03] * right.ms[M10] + ms[M13] * right.ms[M11] + ms[M23] * right.ms[M12] + ms[M33] * right.ms[M13],
                 ms[M00] * right.ms[M20] + ms[M10] * right.ms[M21] + ms[M20] * right.ms[M22] + ms[M30] * right.ms[M23],
                 ms[M01] * right.ms[M20] + ms[M11] * right.ms[M21] + ms[M21] * right.ms[M22] + ms[M31] * right.ms[M23],
                 ms[M02] * right.ms[M20] + ms[M12] * right.ms[M21] + ms[M22] * right.ms[M22] + ms[M32] * right.ms[M23],
                 ms[M03] * right.ms[M20] + ms[M13] * right.ms[M21] + ms[M23] * right.ms[M22] + ms[M33] * right.ms[M23],
                 ms[M00] * right.ms[M30] + ms[M10] * right.ms[M31] + ms[M20] * right.ms[M32] + ms[M30] * right.ms[M33],
                 ms[M01] * right.ms[M30] + ms[M11] * right.ms[M31] + ms[M21] * right.ms[M32] + ms[M31] * right.ms[M33],
                 ms[M02] * right.ms[M30] + ms[M12] * right.ms[M31] + ms[M22] * right.ms[M32] + ms[M32] * right.ms[M33],
                 ms[M03] * right.ms[M30] + ms[M13] * right.ms[M31] + ms[M23] * right.ms[M32] + ms[M33] * right.ms[M33]);
        return dest;
    }

    /**
     * Multiply <code>this</code> symmetric perspective projection matrix by the supplied {@link #isAffine() affine} <code>view</code> matrix.
     * <p>
     * If <code>P</code> is <code>this</code> matrix and <code>V</code> the <code>view</code> matrix,
     * then the new matrix will be <code>P * V</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>P * V * v</code>, the
     * transformation of the <code>view</code> matrix will be applied first!
     *
     * @param view
     *          the {@link #isAffine() affine} matrix to multiply <code>this</code> symmetric perspective projection matrix by
     * @return dest
     */
    public Matrix4f mulPerspectiveAffine(Matrix4f view) {
       return mulPerspectiveAffine(view, this);
    }

    /**
     * Multiply <code>this</code> symmetric perspective projection matrix by the supplied {@link #isAffine() affine} <code>view</code> matrix and store the result in <code>dest</code>.
     * <p>
     * If <code>P</code> is <code>this</code> matrix and <code>V</code> the <code>view</code> matrix,
     * then the new matrix will be <code>P * V</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>P * V * v</code>, the
     * transformation of the <code>view</code> matrix will be applied first!
     *
     * @param view
     *          the {@link #isAffine() affine} matrix to multiply <code>this</code> symmetric perspective projection matrix by
     * @param dest
     *          the destination matrix, which will hold the result
     * @return dest
     */
    public Matrix4f mulPerspectiveAffine(Matrix4f view, Matrix4f dest) {
        dest.set(ms[M00] * view.ms[M00], ms[M11] * view.ms[M01], ms[M22] * view.ms[M02], ms[M23] * view.ms[M02],
                 ms[M00] * view.ms[M10], ms[M11] * view.ms[M11], ms[M22] * view.ms[M12], ms[M23] * view.ms[M12],
                 ms[M00] * view.ms[M20], ms[M11] * view.ms[M21], ms[M22] * view.ms[M22], ms[M23] * view.ms[M22],
                 ms[M00] * view.ms[M30], ms[M11] * view.ms[M31], ms[M22] * view.ms[M32] + ms[M32], ms[M23] * view.ms[M32]);
        return dest;
    }

    /**
     * Multiply this matrix by the supplied <code>right</code> matrix, which is assumed to be {@link #isAffine() affine}, and store the result in <code>this</code>.
     * <p>
     * This method assumes that the given <code>right</code> matrix represents an {@link #isAffine() affine} transformation (i.e. its last row is equal to <tt>(0, 0, 0, 1)</tt>)
     * and can be used to speed up matrix multiplication if the matrix only represents affine transformations, such as translation, rotation, scaling and shearing (in any combination).
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
    public Matrix4f mulAffineR(Matrix4f right) {
       return mulAffineR(right, this);
    }

    /**
     * Multiply this matrix by the supplied <code>right</code> matrix, which is assumed to be {@link #isAffine() affine}, and store the result in <code>dest</code>.
     * <p>
     * This method assumes that the given <code>right</code> matrix represents an {@link #isAffine() affine} transformation (i.e. its last row is equal to <tt>(0, 0, 0, 1)</tt>)
     * and can be used to speed up matrix multiplication if the matrix only represents affine transformations, such as translation, rotation, scaling and shearing (in any combination).
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
    public Matrix4f mulAffineR(Matrix4f right, Matrix4f dest) {
        dest.set(ms[M00] * right.ms[M00] + ms[M10] * right.ms[M01] + ms[M20] * right.ms[M02],
                 ms[M01] * right.ms[M00] + ms[M11] * right.ms[M01] + ms[M21] * right.ms[M02],
                 ms[M02] * right.ms[M00] + ms[M12] * right.ms[M01] + ms[M22] * right.ms[M02],
                 ms[M03] * right.ms[M00] + ms[M13] * right.ms[M01] + ms[M23] * right.ms[M02],
                 ms[M00] * right.ms[M10] + ms[M10] * right.ms[M11] + ms[M20] * right.ms[M12],
                 ms[M01] * right.ms[M10] + ms[M11] * right.ms[M11] + ms[M21] * right.ms[M12],
                 ms[M02] * right.ms[M10] + ms[M12] * right.ms[M11] + ms[M22] * right.ms[M12],
                 ms[M03] * right.ms[M10] + ms[M13] * right.ms[M11] + ms[M23] * right.ms[M12],
                 ms[M00] * right.ms[M20] + ms[M10] * right.ms[M21] + ms[M20] * right.ms[M22],
                 ms[M01] * right.ms[M20] + ms[M11] * right.ms[M21] + ms[M21] * right.ms[M22],
                 ms[M02] * right.ms[M20] + ms[M12] * right.ms[M21] + ms[M22] * right.ms[M22],
                 ms[M03] * right.ms[M20] + ms[M13] * right.ms[M21] + ms[M23] * right.ms[M22],
                 ms[M00] * right.ms[M30] + ms[M10] * right.ms[M31] + ms[M20] * right.ms[M32] + ms[M30],
                 ms[M01] * right.ms[M30] + ms[M11] * right.ms[M31] + ms[M21] * right.ms[M32] + ms[M31],
                 ms[M02] * right.ms[M30] + ms[M12] * right.ms[M31] + ms[M22] * right.ms[M32] + ms[M32],
                 ms[M03] * right.ms[M30] + ms[M13] * right.ms[M31] + ms[M23] * right.ms[M32] + ms[M33]);
        return dest;
    }

    /**
     * Multiply this matrix by the supplied <code>right</code> matrix, both of which are assumed to be {@link #isAffine() affine}, and store the result in <code>this</code>.
     * <p>
     * This method assumes that <code>this</code> matrix and the given <code>right</code> matrix both represent an {@link #isAffine() affine} transformation
     * (i.e. their last rows are equal to <tt>(0, 0, 0, 1)</tt>)
     * and can be used to speed up matrix multiplication if the matrices only represent affine transformations, such as translation, rotation, scaling and shearing (in any combination).
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
    public Matrix4f mulAffine(Matrix4f right) {
       return mulAffine(right, this);
    }

    /**
     * Multiply this matrix by the supplied <code>right</code> matrix, both of which are assumed to be {@link #isAffine() affine}, and store the result in <code>dest</code>.
     * <p>
     * This method assumes that <code>this</code> matrix and the given <code>right</code> matrix both represent an {@link #isAffine() affine} transformation
     * (i.e. their last rows are equal to <tt>(0, 0, 0, 1)</tt>)
     * and can be used to speed up matrix multiplication if the matrices only represent affine transformations, such as translation, rotation, scaling and shearing (in any combination).
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
    public Matrix4f mulAffine(Matrix4f right, Matrix4f dest) {
        dest.set(ms[M00] * right.ms[M00] + ms[M10] * right.ms[M01] + ms[M20] * right.ms[M02],
                 ms[M01] * right.ms[M00] + ms[M11] * right.ms[M01] + ms[M21] * right.ms[M02],
                 ms[M02] * right.ms[M00] + ms[M12] * right.ms[M01] + ms[M22] * right.ms[M02],
                 ms[M03],
                 ms[M00] * right.ms[M10] + ms[M10] * right.ms[M11] + ms[M20] * right.ms[M12],
                 ms[M01] * right.ms[M10] + ms[M11] * right.ms[M11] + ms[M21] * right.ms[M12],
                 ms[M02] * right.ms[M10] + ms[M12] * right.ms[M11] + ms[M22] * right.ms[M12],
                 ms[M13],
                 ms[M00] * right.ms[M20] + ms[M10] * right.ms[M21] + ms[M20] * right.ms[M22],
                 ms[M01] * right.ms[M20] + ms[M11] * right.ms[M21] + ms[M21] * right.ms[M22],
                 ms[M02] * right.ms[M20] + ms[M12] * right.ms[M21] + ms[M22] * right.ms[M22],
                 ms[M23],
                 ms[M00] * right.ms[M30] + ms[M10] * right.ms[M31] + ms[M20] * right.ms[M32] + ms[M30],
                 ms[M01] * right.ms[M30] + ms[M11] * right.ms[M31] + ms[M21] * right.ms[M32] + ms[M31],
                 ms[M02] * right.ms[M30] + ms[M12] * right.ms[M31] + ms[M22] * right.ms[M32] + ms[M32],
                 ms[M33]);
        return dest;
    }

    /**
     * Multiply <code>this</code> orthographic projection matrix by the supplied {@link #isAffine() affine} <code>view</code> matrix.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>V</code> the <code>view</code> matrix,
     * then the new matrix will be <code>M * V</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * V * v</code>, the
     * transformation of the <code>view</code> matrix will be applied first!
     *
     * @param view
     *          the affine matrix which to multiply <code>this</code> with
     * @return dest
     */
    public Matrix4f mulOrthoAffine(Matrix4f view) {
        return mulOrthoAffine(view, this);
    }

    /**
     * Multiply <code>this</code> orthographic projection matrix by the supplied {@link #isAffine() affine} <code>view</code> matrix
     * and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>V</code> the <code>view</code> matrix,
     * then the new matrix will be <code>M * V</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * V * v</code>, the
     * transformation of the <code>view</code> matrix will be applied first!
     *
     * @param view
     *          the affine matrix which to multiply <code>this</code> with
     * @param dest
     *          the destination matrix, which will hold the result
     * @return dest
     */
    public Matrix4f mulOrthoAffine(Matrix4f view, Matrix4f dest) {
        dest.set(ms[M00] * view.ms[M00], ms[M11] * view.ms[M01], ms[M22] * view.ms[M02], 0.0f,
                 ms[M00] * view.ms[M10], ms[M11] * view.ms[M11], ms[M22] * view.ms[M12], 0.0f,
                 ms[M00] * view.ms[M20], ms[M11] * view.ms[M21], ms[M22] * view.ms[M22], 0.0f,
                 ms[M00] * view.ms[M30] + ms[M30], ms[M11] * view.ms[M31] + ms[M31], ms[M22] * view.ms[M32] + ms[M32], 1.0f);
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
    public Matrix4f fma4x3(Matrix4f other, float otherFactor) {
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
    public Matrix4f fma4x3(Matrix4f other, float otherFactor, Matrix4f dest) {
        dest.ms[M00] = ms[M00] + other.ms[M00] * otherFactor;
        dest.ms[M01] = ms[M01] + other.ms[M01] * otherFactor;
        dest.ms[M02] = ms[M02] + other.ms[M02] * otherFactor;
        dest.ms[M03] = ms[M03];
        dest.ms[M10] = ms[M10] + other.ms[M10] * otherFactor;
        dest.ms[M11] = ms[M11] + other.ms[M11] * otherFactor;
        dest.ms[M12] = ms[M12] + other.ms[M12] * otherFactor;
        dest.ms[M13] = ms[M13];
        dest.ms[M20] = ms[M20] + other.ms[M20] * otherFactor;
        dest.ms[M21] = ms[M21] + other.ms[M21] * otherFactor;
        dest.ms[M22] = ms[M22] + other.ms[M22] * otherFactor;
        dest.ms[M23] = ms[M23];
        dest.ms[M30] = ms[M30] + other.ms[M30] * otherFactor;
        dest.ms[M31] = ms[M31] + other.ms[M31] * otherFactor;
        dest.ms[M32] = ms[M32] + other.ms[M32] * otherFactor;
        dest.ms[M33] = ms[M33];
        return dest;
    }

    /**
     * Component-wise add <code>this</code> and <code>other</code>.
     * 
     * @param other
     *          the other addend
     * @return this
     */
    public Matrix4f add(Matrix4f other) {
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
    public Matrix4f add(Matrix4f other, Matrix4f dest) {
        dest.ms[M00] = ms[M00] + other.ms[M00];
        dest.ms[M01] = ms[M01] + other.ms[M01];
        dest.ms[M02] = ms[M02] + other.ms[M02];
        dest.ms[M03] = ms[M03] + other.ms[M03];
        dest.ms[M10] = ms[M10] + other.ms[M10];
        dest.ms[M11] = ms[M11] + other.ms[M11];
        dest.ms[M12] = ms[M12] + other.ms[M12];
        dest.ms[M13] = ms[M13] + other.ms[M13];
        dest.ms[M20] = ms[M20] + other.ms[M20];
        dest.ms[M21] = ms[M21] + other.ms[M21];
        dest.ms[M22] = ms[M22] + other.ms[M22];
        dest.ms[M23] = ms[M23] + other.ms[M23];
        dest.ms[M30] = ms[M30] + other.ms[M30];
        dest.ms[M31] = ms[M31] + other.ms[M31];
        dest.ms[M32] = ms[M32] + other.ms[M32];
        dest.ms[M33] = ms[M33] + other.ms[M33];
        return dest;
    }

    /**
     * Component-wise subtract <code>subtrahend</code> from <code>this</code>.
     * 
     * @param subtrahend
     *          the subtrahend
     * @return this
     */
    public Matrix4f sub(Matrix4f subtrahend) {
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
    public Matrix4f sub(Matrix4f subtrahend, Matrix4f dest) {
        dest.ms[M00] = ms[M00] - subtrahend.ms[M00];
        dest.ms[M01] = ms[M01] - subtrahend.ms[M01];
        dest.ms[M02] = ms[M02] - subtrahend.ms[M02];
        dest.ms[M03] = ms[M03] - subtrahend.ms[M03];
        dest.ms[M10] = ms[M10] - subtrahend.ms[M10];
        dest.ms[M11] = ms[M11] - subtrahend.ms[M11];
        dest.ms[M12] = ms[M12] - subtrahend.ms[M12];
        dest.ms[M13] = ms[M13] - subtrahend.ms[M13];
        dest.ms[M20] = ms[M20] - subtrahend.ms[M20];
        dest.ms[M21] = ms[M21] - subtrahend.ms[M21];
        dest.ms[M22] = ms[M22] - subtrahend.ms[M22];
        dest.ms[M23] = ms[M23] - subtrahend.ms[M23];
        dest.ms[M30] = ms[M30] - subtrahend.ms[M30];
        dest.ms[M31] = ms[M31] - subtrahend.ms[M31];
        dest.ms[M32] = ms[M32] - subtrahend.ms[M32];
        dest.ms[M33] = ms[M33] - subtrahend.ms[M33];
        return dest;
    }

    /**
     * Component-wise multiply <code>this</code> by <code>other</code>.
     * 
     * @param other
     *          the other matrix
     * @return this
     */
    public Matrix4f mulComponentWise(Matrix4f other) {
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
    public Matrix4f mulComponentWise(Matrix4f other, Matrix4f dest) {
        dest.ms[M00] = ms[M00] * other.ms[M00];
        dest.ms[M01] = ms[M01] * other.ms[M01];
        dest.ms[M02] = ms[M02] * other.ms[M02];
        dest.ms[M03] = ms[M03] * other.ms[M03];
        dest.ms[M10] = ms[M10] * other.ms[M10];
        dest.ms[M11] = ms[M11] * other.ms[M11];
        dest.ms[M12] = ms[M12] * other.ms[M12];
        dest.ms[M13] = ms[M13] * other.ms[M13];
        dest.ms[M20] = ms[M20] * other.ms[M20];
        dest.ms[M21] = ms[M21] * other.ms[M21];
        dest.ms[M22] = ms[M22] * other.ms[M22];
        dest.ms[M23] = ms[M23] * other.ms[M23];
        dest.ms[M30] = ms[M30] * other.ms[M30];
        dest.ms[M31] = ms[M31] * other.ms[M31];
        dest.ms[M32] = ms[M32] * other.ms[M32];
        dest.ms[M33] = ms[M33] * other.ms[M33];
        return dest;
    }

    /**
     * Component-wise add the upper 4x3 submatrices of <code>this</code> and <code>other</code>.
     * 
     * @param other
     *          the other addend
     * @return this
     */
    public Matrix4f add4x3(Matrix4f other) {
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
    public Matrix4f add4x3(Matrix4f other, Matrix4f dest) {
        dest.ms[M00] = ms[M00] + other.ms[M00];
        dest.ms[M01] = ms[M01] + other.ms[M01];
        dest.ms[M02] = ms[M02] + other.ms[M02];
        dest.ms[M03] = ms[M03];
        dest.ms[M10] = ms[M10] + other.ms[M10];
        dest.ms[M11] = ms[M11] + other.ms[M11];
        dest.ms[M12] = ms[M12] + other.ms[M12];
        dest.ms[M13] = ms[M13];
        dest.ms[M20] = ms[M20] + other.ms[M20];
        dest.ms[M21] = ms[M21] + other.ms[M21];
        dest.ms[M22] = ms[M22] + other.ms[M22];
        dest.ms[M23] = ms[M23];
        dest.ms[M30] = ms[M30] + other.ms[M30];
        dest.ms[M31] = ms[M31] + other.ms[M31];
        dest.ms[M32] = ms[M32] + other.ms[M32];
        dest.ms[M33] = ms[M33];
        return dest;
    }

    /**
     * Component-wise subtract the upper 4x3 submatrices of <code>subtrahend</code> from <code>this</code>.
     * 
     * @param subtrahend
     *          the subtrahend
     * @return this
     */
    public Matrix4f sub4x3(Matrix4f subtrahend) {
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
    public Matrix4f sub4x3(Matrix4f subtrahend, Matrix4f dest) {
        dest.ms[M00] = ms[M00] - subtrahend.ms[M00];
        dest.ms[M01] = ms[M01] - subtrahend.ms[M01];
        dest.ms[M02] = ms[M02] - subtrahend.ms[M02];
        dest.ms[M03] = ms[M03];
        dest.ms[M10] = ms[M10] - subtrahend.ms[M10];
        dest.ms[M11] = ms[M11] - subtrahend.ms[M11];
        dest.ms[M12] = ms[M12] - subtrahend.ms[M12];
        dest.ms[M13] = ms[M13];
        dest.ms[M20] = ms[M20] - subtrahend.ms[M20];
        dest.ms[M21] = ms[M21] - subtrahend.ms[M21];
        dest.ms[M22] = ms[M22] - subtrahend.ms[M22];
        dest.ms[M23] = ms[M23];
        dest.ms[M30] = ms[M30] - subtrahend.ms[M30];
        dest.ms[M31] = ms[M31] - subtrahend.ms[M31];
        dest.ms[M32] = ms[M32] - subtrahend.ms[M32];
        dest.ms[M33] = ms[M33];
        return dest;
    }

    /**
     * Component-wise multiply the upper 4x3 submatrices of <code>this</code> by <code>other</code>.
     * 
     * @param other
     *          the other matrix
     * @return this
     */
    public Matrix4f mul4x3ComponentWise(Matrix4f other) {
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
    public Matrix4f mul4x3ComponentWise(Matrix4f other, Matrix4f dest) {
        dest.ms[M00] = ms[M00] * other.ms[M00];
        dest.ms[M01] = ms[M01] * other.ms[M01];
        dest.ms[M02] = ms[M02] * other.ms[M02];
        dest.ms[M03] = ms[M03];
        dest.ms[M10] = ms[M10] * other.ms[M10];
        dest.ms[M11] = ms[M11] * other.ms[M11];
        dest.ms[M12] = ms[M12] * other.ms[M12];
        dest.ms[M13] = ms[M13];
        dest.ms[M20] = ms[M20] * other.ms[M20];
        dest.ms[M21] = ms[M21] * other.ms[M21];
        dest.ms[M22] = ms[M22] * other.ms[M22];
        dest.ms[M23] = ms[M23];
        dest.ms[M30] = ms[M30] * other.ms[M30];
        dest.ms[M31] = ms[M31] * other.ms[M31];
        dest.ms[M32] = ms[M32] * other.ms[M32];
        dest.ms[M33] = ms[M33];
        return dest;
    }

    /**
     * Set the values within this matrix to the supplied float values. The matrix will look like this:<br><br>
     *
     *  m00, m10, m20, m30<br>
     *  m01, m11, m21, m31<br>
     *  m02, m12, m22, m32<br>
     *  m03, m13, m23, m33
     * 
     * @param n00
     *          the new value of ms[M00]
     * @param n01
     *          the new value of ms[M01]
     * @param n02
     *          the new value of ms[M02]
     * @param n03
     *          the new value of ms[M03]
     * @param n10
     *          the new value of ms[M10]
     * @param n11
     *          the new value of ms[M11]
     * @param n12
     *          the new value of ms[M12]
     * @param n13
     *          the new value of ms[M13]
     * @param n20
     *          the new value of ms[M20]
     * @param n21
     *          the new value of ms[M21]
     * @param n22
     *          the new value of ms[M22]
     * @param n23
     *          the new value of ms[M23]
     * @param n30
     *          the new value of ms[M30]
     * @param n31
     *          the new value of ms[M31]
     * @param n32
     *          the new value of ms[M32]
     * @param n33
     *          the new value of ms[M33]
     * @return this
     */
    public Matrix4f set(float n00, float n01, float n02, float n03,
                        float n10, float n11, float n12, float n13,
                        float n20, float n21, float n22, float n23, 
                        float n30, float n31, float n32, float n33) {
        this.ms[M00] = n00;
        this.ms[M01] = n01;
        this.ms[M02] = n02;
        this.ms[M03] = n03;
        this.ms[M10] = n10;
        this.ms[M11] = n11;
        this.ms[M12] = n12;
        this.ms[M13] = n13;
        this.ms[M20] = n20;
        this.ms[M21] = n21;
        this.ms[M22] = n22;
        this.ms[M23] = n23;
        this.ms[M30] = n30;
        this.ms[M31] = n31;
        this.ms[M32] = n32;
        this.ms[M33] = n33;
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
     * @see #set(float[])
     * 
     * @param m
     *          the array to read the matrix values from
     * @param off
     *          the offset into the array
     * @return this
     */
    public Matrix4f set(float m[], int off) {
    	System.arraycopy(m, off, ms, 0, 16);
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
     * @see #set(float[], int)
     * 
     * @param m
     *          the array to read the matrix values from
     * @return this
     */
    public Matrix4f set(float m[]) {
        return set(m, 0);
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
    public Matrix4f set(FloatBuffer buffer) {
        MemUtil.INSTANCE.get(this, 0, buffer);
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
    public Matrix4f set(ByteBuffer buffer) {
        MemUtil.INSTANCE.get(this, 0, buffer);
        return this;
    }

    /**
     * Return the determinant of this matrix.
     * <p>
     * If <code>this</code> matrix represents an {@link #isAffine() affine} transformation, such as translation, rotation, scaling and shearing,
     * and thus its last row is equal to <tt>(0, 0, 0, 1)</tt>, then {@link #determinantAffine()} can be used instead of this method.
     * 
     * @see #determinantAffine()
     * 
     * @return the determinant
     */
    public float determinant() {
        return (ms[M00] * ms[M11] - ms[M01] * ms[M10]) * (ms[M22] * ms[M33] - ms[M23] * ms[M32])
             + (ms[M02] * ms[M10] - ms[M00] * ms[M12]) * (ms[M21] * ms[M33] - ms[M23] * ms[M31])
             + (ms[M00] * ms[M13] - ms[M03] * ms[M10]) * (ms[M21] * ms[M32] - ms[M22] * ms[M31])
             + (ms[M01] * ms[M12] - ms[M02] * ms[M11]) * (ms[M20] * ms[M33] - ms[M23] * ms[M30])
             + (ms[M03] * ms[M11] - ms[M01] * ms[M13]) * (ms[M20] * ms[M32] - ms[M22] * ms[M30])
             + (ms[M02] * ms[M13] - ms[M03] * ms[M12]) * (ms[M20] * ms[M31] - ms[M21] * ms[M30]);
    }

    /**
     * Return the determinant of the upper left 3x3 submatrix of this matrix.
     * 
     * @return the determinant
     */
    public float determinant3x3() {
        return (ms[M00] * ms[M11] - ms[M01] * ms[M10]) * ms[M22]
             + (ms[M02] * ms[M10] - ms[M00] * ms[M12]) * ms[M21]
             + (ms[M01] * ms[M12] - ms[M02] * ms[M11]) * ms[M20];
    }

    /**
     * Return the determinant of this matrix by assuming that it represents an {@link #isAffine() affine} transformation and thus
     * its last row is equal to <tt>(0, 0, 0, 1)</tt>.
     * 
     * @return the determinant
     */
    public float determinantAffine() {
        return (ms[M00] * ms[M11] - ms[M01] * ms[M10]) * ms[M22]
             + (ms[M02] * ms[M10] - ms[M00] * ms[M12]) * ms[M21]
             + (ms[M01] * ms[M12] - ms[M02] * ms[M11]) * ms[M20];
    }

    /**
     * Invert this matrix and write the result into <code>dest</code>.
     * <p>
     * If <code>this</code> matrix represents an {@link #isAffine() affine} transformation, such as translation, rotation, scaling and shearing,
     * and thus its last row is equal to <tt>(0, 0, 0, 1)</tt>, then {@link #invertAffine(Matrix4f)} can be used instead of this method.
     * 
     * @see #invertAffine(Matrix4f)
     * 
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Matrix4f invert(Matrix4f dest) {
        float a = ms[M00] * ms[M11] - ms[M01] * ms[M10];
        float b = ms[M00] * ms[M12] - ms[M02] * ms[M10];
        float c = ms[M00] * ms[M13] - ms[M03] * ms[M10];
        float d = ms[M01] * ms[M12] - ms[M02] * ms[M11];
        float e = ms[M01] * ms[M13] - ms[M03] * ms[M11];
        float f = ms[M02] * ms[M13] - ms[M03] * ms[M12];
        float g = ms[M20] * ms[M31] - ms[M21] * ms[M30];
        float h = ms[M20] * ms[M32] - ms[M22] * ms[M30];
        float i = ms[M20] * ms[M33] - ms[M23] * ms[M30];
        float j = ms[M21] * ms[M32] - ms[M22] * ms[M31];
        float k = ms[M21] * ms[M33] - ms[M23] * ms[M31];
        float l = ms[M22] * ms[M33] - ms[M23] * ms[M32];
        float det = a * l - b * k + c * j + d * i - e * h + f * g;
        det = 1.0f / det;
        dest.set(( ms[M11] * l - ms[M12] * k + ms[M13] * j) * det,
                 (-ms[M01] * l + ms[M02] * k - ms[M03] * j) * det,
                 ( ms[M31] * f - ms[M32] * e + ms[M33] * d) * det,
                 (-ms[M21] * f + ms[M22] * e - ms[M23] * d) * det,
                 (-ms[M10] * l + ms[M12] * i - ms[M13] * h) * det,
                 ( ms[M00] * l - ms[M02] * i + ms[M03] * h) * det,
                 (-ms[M30] * f + ms[M32] * c - ms[M33] * b) * det,
                 ( ms[M20] * f - ms[M22] * c + ms[M23] * b) * det,
                 ( ms[M10] * k - ms[M11] * i + ms[M13] * g) * det,
                 (-ms[M00] * k + ms[M01] * i - ms[M03] * g) * det,
                 ( ms[M30] * e - ms[M31] * c + ms[M33] * a) * det,
                 (-ms[M20] * e + ms[M21] * c - ms[M23] * a) * det,
                 (-ms[M10] * j + ms[M11] * h - ms[M12] * g) * det,
                 ( ms[M00] * j - ms[M01] * h + ms[M02] * g) * det,
                 (-ms[M30] * d + ms[M31] * b - ms[M32] * a) * det,
                 ( ms[M20] * d - ms[M21] * b + ms[M22] * a) * det);
        return dest;
    }

    /**
     * Invert this matrix.
     * <p>
     * If <code>this</code> matrix represents an {@link #isAffine() affine} transformation, such as translation, rotation, scaling and shearing,
     * and thus its last row is equal to <tt>(0, 0, 0, 1)</tt>, then {@link #invertAffine()} can be used instead of this method.
     * 
     * @see #invertAffine()
     * 
     * @return this
     */
    public Matrix4f invert() {
        return invert(this);
    }

    /**
     * If <code>this</code> is a perspective projection matrix obtained via one of the {@link #perspective(float, float, float, float) perspective()} methods
     * or via {@link #setPerspective(float, float, float, float) setPerspective()}, that is, if <code>this</code> is a symmetrical perspective frustum transformation,
     * then this method builds the inverse of <code>this</code> and stores it into the given <code>dest</code>.
     * <p>
     * This method can be used to quickly obtain the inverse of a perspective projection matrix when being obtained via {@link #perspective(float, float, float, float) perspective()}.
     * 
     * @see #perspective(float, float, float, float)
     * 
     * @param dest
     *          will hold the inverse of <code>this</code>
     * @return dest
     */
    public Matrix4f invertPerspective(Matrix4f dest) {
        float a =  1.0f / (ms[M00] * ms[M11]);
        float l = -1.0f / (ms[M23] * ms[M32]);
        dest.set(ms[M11] * a, 0, 0, 0,
                 0, ms[M00] * a, 0, 0,
                 0, 0, 0, -ms[M23] * l,
                 0, 0, -ms[M32] * l, ms[M22] * l);
        return dest;
    }

    /**
     * If <code>this</code> is a perspective projection matrix obtained via one of the {@link #perspective(float, float, float, float) perspective()} methods
     * or via {@link #setPerspective(float, float, float, float) setPerspective()}, that is, if <code>this</code> is a symmetrical perspective frustum transformation,
     * then this method builds the inverse of <code>this</code>.
     * <p>
     * This method can be used to quickly obtain the inverse of a perspective projection matrix when being obtained via {@link #perspective(float, float, float, float) perspective()}.
     * 
     * @see #perspective(float, float, float, float)
     * 
     * @return this
     */
    public Matrix4f invertPerspective() {
        return invertPerspective(this);
    }

    /**
     * If <code>this</code> is an arbitrary perspective projection matrix obtained via one of the {@link #frustum(float, float, float, float, float, float) frustum()}  methods
     * or via {@link #setFrustum(float, float, float, float, float, float) setFrustum()},
     * then this method builds the inverse of <code>this</code> and stores it into the given <code>dest</code>.
     * <p>
     * This method can be used to quickly obtain the inverse of a perspective projection matrix.
     * <p>
     * If this matrix represents a symmetric perspective frustum transformation, as obtained via {@link #perspective(float, float, float, float) perspective()}, then
     * {@link #invertPerspective(Matrix4f)} should be used instead.
     * 
     * @see #frustum(float, float, float, float, float, float)
     * @see #invertPerspective(Matrix4f)
     * 
     * @param dest
     *          will hold the inverse of <code>this</code>
     * @return dest
     */
    public Matrix4f invertFrustum(Matrix4f dest) {
        float invM00 = 1.0f / ms[M00];
        float invM11 = 1.0f / ms[M11];
        float invM23 = 1.0f / ms[M23];
        float invM32 = 1.0f / ms[M32];
        dest.set(invM00, 0, 0, 0,
                 0, invM11, 0, 0,
                 0, 0, 0, invM32,
                 -ms[M20] * invM00 * invM23, -ms[M21] * invM11 * invM23, invM23, -ms[M22] * invM23 * invM32);
        return dest;
    }

    /**
     * If <code>this</code> is an arbitrary perspective projection matrix obtained via one of the {@link #frustum(float, float, float, float, float, float) frustum()}  methods
     * or via {@link #setFrustum(float, float, float, float, float, float) setFrustum()},
     * then this method builds the inverse of <code>this</code>.
     * <p>
     * This method can be used to quickly obtain the inverse of a perspective projection matrix.
     * <p>
     * If this matrix represents a symmetric perspective frustum transformation, as obtained via {@link #perspective(float, float, float, float) perspective()}, then
     * {@link #invertPerspective()} should be used instead.
     * 
     * @see #frustum(float, float, float, float, float, float)
     * @see #invertPerspective()
     * 
     * @return this
     */
    public Matrix4f invertFrustum() {
        return invertFrustum(this);
    }

    /**
     * Invert <code>this</code> orthographic projection matrix and store the result into the given <code>dest</code>.
     * <p>
     * This method can be used to quickly obtain the inverse of an orthographic projection matrix.
     * 
     * @param dest
     *          will hold the inverse of <code>this</code>
     * @return dest
     */
    public Matrix4f invertOrtho(Matrix4f dest) {
        float invM00 = 1.0f / ms[M00];
        float invM11 = 1.0f / ms[M11];
        float invM22 = 1.0f / ms[M22];
        dest.set(invM00, 0, 0, 0,
                 0, invM11, 0, 0,
                 0, 0, invM22, 0,
                 -ms[M30] * invM00, -ms[M31] * invM11, -ms[M32] * invM22, 1);
        return dest;
    }

    /**
     * Invert <code>this</code> orthographic projection matrix.
     * <p>
     * This method can be used to quickly obtain the inverse of an orthographic projection matrix.
     * 
     * @return this
     */
    public Matrix4f invertOrtho() {
        return invertOrtho(this);
    }

    /**
     * If <code>this</code> is a perspective projection matrix obtained via one of the {@link #perspective(float, float, float, float) perspective()} methods
     * or via {@link #setPerspective(float, float, float, float) setPerspective()}, that is, if <code>this</code> is a symmetrical perspective frustum transformation
     * and the given <code>view</code> matrix is {@link #isAffine() affine} and has unit scaling (for example by being obtained via {@link #lookAt(float, float, float, float, float, float, float, float, float) lookAt()}),
     * then this method builds the inverse of <tt>this * view</tt> and stores it into the given <code>dest</code>.
     * <p>
     * This method can be used to quickly obtain the inverse of the combination of the view and projection matrices, when both were obtained
     * via the common methods {@link #perspective(float, float, float, float) perspective()} and {@link #lookAt(float, float, float, float, float, float, float, float, float) lookAt()} or
     * other methods, that build affine matrices, such as {@link #translate(float, float, float) translate} and {@link #rotate(float, float, float, float)}, except for {@link #scale(float, float, float) scale()}.
     * <p>
     * For the special cases of the matrices <code>this</code> and <code>view</code> mentioned above this method, this method is equivalent to the following code:
     * <pre>
     * dest.set(this).mul(view).invert();
     * </pre>
     * 
     * @param view
     *          the view transformation (must be {@link #isAffine() affine} and have unit scaling)
     * @param dest
     *          will hold the inverse of <tt>this * view</tt>
     * @return dest
     */
    public Matrix4f invertPerspectiveView(Matrix4f view, Matrix4f dest) {
        float a =  1.0f / (ms[M00] * ms[M11]);
        float l = -1.0f / (ms[M23] * ms[M32]);
        float pms00 =  ms[M11] * a;
        float pms11 =  ms[M00] * a;
        float pms23 = -ms[M23] * l;
        float pms32 = -ms[M32] * l;
        float pms33 =  ms[M22] * l;
        float vmsM30 = -view.ms[M00] * view.ms[M30] - view.ms[M01] * view.ms[M31] - view.ms[M02] * view.ms[M32];
        float vmsM31 = -view.ms[M10] * view.ms[M30] - view.ms[M11] * view.ms[M31] - view.ms[M12] * view.ms[M32];
        float vmsM32 = -view.ms[M20] * view.ms[M30] - view.ms[M21] * view.ms[M31] - view.ms[M22] * view.ms[M32];
        dest.set(view.ms[M00] * pms00, view.ms[M10] * pms00, view.ms[M20] * pms00, 0.0f,
                 view.ms[M01] * pms11, view.ms[M11] * pms11, view.ms[M21] * pms11, 0.0f,
                 vmsM30 * pms23, vmsM31 * pms23, vmsM32 * pms23, pms23,
                 view.ms[M02] * pms32 + vmsM30 * pms33, view.ms[M12] * pms32 + vmsM31 * pms33, view.ms[M22] * pms32 + vmsM32 * pms33, pms33);
        return dest;
    }

    /**
     * Invert this matrix by assuming that it is an {@link #isAffine() affine} transformation (i.e. its last row is equal to <tt>(0, 0, 0, 1)</tt>)
     * and write the result into <code>dest</code>.
     * <p>
     * Note that if <code>this</code> matrix also has unit scaling, then the method {@link #invertAffineUnitScale(Matrix4f)} should be used instead.
     * 
     * @see #invertAffineUnitScale(Matrix4f)
     * 
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Matrix4f invertAffine(Matrix4f dest) {
        float s = determinantAffine();
        s = 1.0f / s;
        float msM10M22 = ms[M10] * ms[M22];
        float msM10M21 = ms[M10] * ms[M21];
        float msM10M02 = ms[M10] * ms[M02];
        float msM10M01 = ms[M10] * ms[M01];
        float msM11M22 = ms[M11] * ms[M22];
        float msM11M20 = ms[M11] * ms[M20];
        float msM11M02 = ms[M11] * ms[M02];
        float msM11M00 = ms[M11] * ms[M00];
        float msM12M21 = ms[M12] * ms[M21];
        float msM12M20 = ms[M12] * ms[M20];
        float msM12M01 = ms[M12] * ms[M01];
        float msM12M00 = ms[M12] * ms[M00];
        float msM20M02 = ms[M20] * ms[M02];
        float msM20M01 = ms[M20] * ms[M01];
        float msM21M02 = ms[M21] * ms[M02];
        float msM21M00 = ms[M21] * ms[M00];
        float msM22M01 = ms[M22] * ms[M01];
        float msM22M00 = ms[M22] * ms[M00];
        dest.set((msM11M22 - msM12M21) * s,
                 (msM21M02 - msM22M01) * s,
                 (msM12M01 - msM11M02) * s,
                 0.0f,
                 (msM12M20 - msM10M22) * s,
                 (msM22M00 - msM20M02) * s,
                 (msM10M02 - msM12M00) * s,
                 0.0f,
                 (msM10M21 - msM11M20) * s,
                 (msM20M01 - msM21M00) * s,
                 (msM11M00 - msM10M01) * s,
                 0.0f,
                 (msM10M22 * ms[M31] - msM10M21 * ms[M32] + msM11M20 * ms[M32] - msM11M22 * ms[M30] + msM12M21 * ms[M30] - msM12M20 * ms[M31]) * s,
                 (msM20M02 * ms[M31] - msM20M01 * ms[M32] + msM21M00 * ms[M32] - msM21M02 * ms[M30] + msM22M01 * ms[M30] - msM22M00 * ms[M31]) * s,
                 (msM11M02 * ms[M30] - msM12M01 * ms[M30] + msM12M00 * ms[M31] - msM10M02 * ms[M31] + msM10M01 * ms[M32] - msM11M00 * ms[M32]) * s,
                 1.0f);
        return dest;
    }

    /**
     * Invert this matrix by assuming that it is an {@link #isAffine() affine} transformation (i.e. its last row is equal to <tt>(0, 0, 0, 1)</tt>).
     * <p>
     * Note that if <code>this</code> matrix also has unit scaling, then the method {@link #invertAffineUnitScale()} should be used instead.
     * 
     * @see #invertAffineUnitScale()
     * 
     * @return this
     */
    public Matrix4f invertAffine() {
        return invertAffine(this);
    }

    /**
     * Invert this matrix by assuming that it is an {@link #isAffine() affine} transformation (i.e. its last row is equal to <tt>(0, 0, 0, 1)</tt>)
     * and has unit scaling (i.e. {@link #transformDirection(Vector3f) transformDirection} does not change the {@link Vector3f#length() length} of the vector)
     * and write the result into <code>dest</code>.
     * <p>
     * Reference: <a href="http://www.gamedev.net/topic/425118-inverse--matrix/">http://www.gamedev.net/</a>
     * 
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Matrix4f invertAffineUnitScale(Matrix4f dest) {
        dest.set(ms[M00], ms[M10], ms[M20], 0.0f,
                 ms[M01], ms[M11], ms[M21], 0.0f,
                 ms[M02], ms[M12], ms[M22], 0.0f,
                 -ms[M00] * ms[M30] - ms[M01] * ms[M31] - ms[M02] * ms[M32],
                 -ms[M10] * ms[M30] - ms[M11] * ms[M31] - ms[M12] * ms[M32],
                 -ms[M20] * ms[M30] - ms[M21] * ms[M31] - ms[M22] * ms[M32],
                 1.0f);
        return dest;
    }

    /**
     * Invert this matrix by assuming that it is an {@link #isAffine() affine} transformation (i.e. its last row is equal to <tt>(0, 0, 0, 1)</tt>)
     * and has unit scaling (i.e. {@link #transformDirection(Vector3f) transformDirection} does not change the {@link Vector3f#length() length} of the vector).
     * <p>
     * Reference: <a href="http://www.gamedev.net/topic/425118-inverse--matrix/">http://www.gamedev.net/</a>
     * 
     * @return this
     */
    public Matrix4f invertAffineUnitScale() {
        return invertAffineUnitScale(this);
    }

    /**
     * Invert this matrix by assuming that it is an {@link #isAffine() affine} transformation (i.e. its last row is equal to <tt>(0, 0, 0, 1)</tt>)
     * and has unit scaling (i.e. {@link #transformDirection(Vector3f) transformDirection} does not change the {@link Vector3f#length() length} of the vector),
     * as is the case for matrices built via {@link #lookAt(Vector3f, Vector3f, Vector3f)} and their overloads, and write the result into <code>dest</code>.
     * <p>
     * This method is equivalent to calling {@link #invertAffineUnitScale(Matrix4f)}
     * <p>
     * Reference: <a href="http://www.gamedev.net/topic/425118-inverse--matrix/">http://www.gamedev.net/</a>
     * 
     * @see #invertAffineUnitScale(Matrix4f)
     * 
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Matrix4f invertLookAt(Matrix4f dest) {
        return invertAffineUnitScale(dest);
    }

    /**
     * Invert this matrix by assuming that it is an {@link #isAffine() affine} transformation (i.e. its last row is equal to <tt>(0, 0, 0, 1)</tt>)
     * and has unit scaling (i.e. {@link #transformDirection(Vector3f) transformDirection} does not change the {@link Vector3f#length() length} of the vector),
     * as is the case for matrices built via {@link #lookAt(Vector3f, Vector3f, Vector3f)} and their overloads.
     * <p>
     * This method is equivalent to calling {@link #invertAffineUnitScale()}
     * <p>
     * Reference: <a href="http://www.gamedev.net/topic/425118-inverse--matrix/">http://www.gamedev.net/</a>
     * 
     * @see #invertAffineUnitScale()
     * 
     * @return this
     */
    public Matrix4f invertLookAt() {
        return invertAffineUnitScale(this);
    }

    /**
     * Transpose this matrix and store the result in <code>dest</code>.
     * 
     * @param dest
     *             will hold the result
     * @return dest
     */
    public Matrix4f transpose(Matrix4f dest) {
        dest.set(ms[M00], ms[M10], ms[M20], ms[M30],
                 ms[M01], ms[M11], ms[M21], ms[M31],
                 ms[M02], ms[M12], ms[M22], ms[M32],
                 ms[M03], ms[M13], ms[M23], ms[M33]);
        return dest;
    }

    /**
     * Transpose only the upper left 3x3 submatrix of this matrix and set the rest of the matrix elements to identity.
     * 
     * @return this
     */
    public Matrix4f transpose3x3() {
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
    public Matrix4f transpose3x3(Matrix4f dest) {
        dest.set(ms[M00],  ms[M10],  ms[M20],  0.0f,
                 ms[M01],  ms[M11],  ms[M21],  0.0f,
                 ms[M02],  ms[M12],  ms[M22],  0.0f,
                 0.0f, 0.0f, 0.0f, 1.0f);
        return dest;
    }

    /**
     * Transpose only the upper left 3x3 submatrix of this matrix and store the result in <code>dest</code>.
     * 
     * @param dest
     *             will hold the result
     * @return dest
     */
    public Matrix3f transpose3x3(Matrix3f dest) {
        dest.ms[Matrix3f.M00] = ms[M00];
        dest.ms[Matrix3f.M01] = ms[M10];
        dest.ms[Matrix3f.M02] = ms[M20];
        dest.ms[Matrix3f.M10] = ms[M01];
        dest.ms[Matrix3f.M11] = ms[M11];
        dest.ms[Matrix3f.M12] = ms[M21];
        dest.ms[Matrix3f.M20] = ms[M02];
        dest.ms[Matrix3f.M21] = ms[M12];
        dest.ms[Matrix3f.M22] = ms[M22];
        return dest;
    }

    /**
     * Transpose this matrix.
     * 
     * @return this
     */
    public Matrix4f transpose() {
        return transpose(this);
    }

    /**
     * Set this matrix to be a simple translation matrix.
     * <p>
     * The resulting matrix can be multiplied against another transformation
     * matrix to obtain an additional translation.
     * <p>
     * In order to post-multiply a translation transformation directly to a
     * matrix, use {@link #translate(float, float, float) translate()} instead.
     * 
     * @see #translate(float, float, float)
     * 
     * @param x
     *          the offset to translate in x
     * @param y
     *          the offset to translate in y
     * @param z
     *          the offset to translate in z
     * @return this
     */
    public Matrix4f translation(float x, float y, float z) {
        ms[M00] = 1.0f;
        ms[M01] = 0.0f;
        ms[M02] = 0.0f;
        ms[M03] = 0.0f;
        ms[M10] = 0.0f;
        ms[M11] = 1.0f;
        ms[M12] = 0.0f;
        ms[M13] = 0.0f;
        ms[M20] = 0.0f;
        ms[M21] = 0.0f;
        ms[M22] = 1.0f;
        ms[M23] = 0.0f;
        ms[M30] = x;
        ms[M31] = y;
        ms[M32] = z;
        ms[M33] = 1.0f;
        return this;
    }

    /**
     * Set this matrix to be a simple translation matrix.
     * <p>
     * The resulting matrix can be multiplied against another transformation
     * matrix to obtain an additional translation.
     * <p>
     * In order to post-multiply a translation transformation directly to a
     * matrix, use {@link #translate(Vector3f) translate()} instead.
     * 
     * @see #translate(float, float, float)
     * 
     * @param offset
     *              the offsets in x, y and z to translate
     * @return this
     */
    public Matrix4f translation(Vector3f offset) {
        return translation(offset.x, offset.y, offset.z);
    }

    /**
     * Set only the translation components <tt>(ms[M30], ms[M31], ms[M32])</tt> of this matrix to the given values <tt>(x, y, z)</tt>.
     * <p>
     * Note that this will only work properly for orthogonal matrices (without any perspective).
     * <p>
     * To build a translation matrix instead, use {@link #translation(float, float, float)}.
     * To apply a translation to another matrix, use {@link #translate(float, float, float)}.
     * 
     * @see #translation(float, float, float)
     * @see #translate(float, float, float)
     * 
     * @param x
     *          the offset to translate in x
     * @param y
     *          the offset to translate in y
     * @param z
     *          the offset to translate in z
     * @return this
     */
    public Matrix4f setTranslation(float x, float y, float z) {
        ms[M30] = x;
        ms[M31] = y;
        ms[M32] = z;
        return this;
    }

    /**
     * Set only the translation components <tt>(ms[M30], ms[M31], ms[M32])</tt> of this matrix to the values <tt>(xyz.x, xyz.y, xyz.z)</tt>.
     * <p>
     * Note that this will only work properly for orthogonal matrices (without any perspective).
     * <p>
     * To build a translation matrix instead, use {@link #translation(Vector3f)}.
     * To apply a translation to another matrix, use {@link #translate(Vector3f)}.
     * 
     * @see #translation(Vector3f)
     * @see #translate(Vector3f)
     * 
     * @param xyz
     *          the units to translate in <tt>(x, y, z)</tt>
     * @return this
     */
    public Matrix4f setTranslation(Vector3f xyz) {
        ms[M30] = xyz.x;
        ms[M31] = xyz.y;
        ms[M32] = xyz.z;
        return this;
    }

    /**
     * Get only the translation components <tt>(ms[M30], ms[M31], ms[M32])</tt> of this matrix and store them in the given vector <code>xyz</code>.
     * 
     * @param dest
     *          will hold the translation components of this matrix
     * @return dest
     */
    public Vector3f getTranslation(Vector3f dest) {
        dest.x = ms[M30];
        dest.y = ms[M31];
        dest.z = ms[M32];
        return dest;
    }

    /**
     * Get the scaling factors of <code>this</code> matrix for the three base axes.
     * 
     * @param dest
     *          will hold the scaling factors for <tt>x</tt>, <tt>y</tt> and <tt>z</tt>
     * @return dest
     */
    public Vector3f getScale(Vector3f dest) {
        dest.x = (float) Math.sqrt(ms[M00] * ms[M00] + ms[M01] * ms[M01] + ms[M02] * ms[M02]);
        dest.y = (float) Math.sqrt(ms[M10] * ms[M10] + ms[M11] * ms[M11] + ms[M12] * ms[M12]);
        dest.z = (float) Math.sqrt(ms[M20] * ms[M20] + ms[M21] * ms[M21] + ms[M22] * ms[M22]);
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
        return formatter.format(ms[M00]) + formatter.format(ms[M10]) + formatter.format(ms[M20]) + formatter.format(ms[M30]) + "\n" //$NON-NLS-1$
             + formatter.format(ms[M01]) + formatter.format(ms[M11]) + formatter.format(ms[M21]) + formatter.format(ms[M31]) + "\n" //$NON-NLS-1$
             + formatter.format(ms[M02]) + formatter.format(ms[M12]) + formatter.format(ms[M22]) + formatter.format(ms[M32]) + "\n" //$NON-NLS-1$
             + formatter.format(ms[M03]) + formatter.format(ms[M13]) + formatter.format(ms[M23]) + formatter.format(ms[M33]) + "\n"; //$NON-NLS-1$
    }

    /**
     * Get the current values of <code>this</code> matrix and store them into
     * <code>dest</code>.
     * <p>
     * This is the reverse method of {@link #set(Matrix4f)} and allows to obtain
     * intermediate calculation results when chaining multiple transformations.
     * 
     * @see #set(Matrix4f)
     * 
     * @param dest
     *            the destination matrix
     * @return the passed in destination
     */
    public Matrix4f get(Matrix4f dest) {
        return dest.set(this);
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
     *            the destination matrix
     * @return the passed in destination
     */
    public Matrix4d get(Matrix4d dest) {
        return dest.set(this);
    }

    /**
     * Get the current values of the upper left 3x3 submatrix of <code>this</code> matrix and store them into
     * <code>dest</code>.
     * 
     * @param dest
     *            the destination matrix
     * @return the passed in destination
     */
    public Matrix3f get3x3(Matrix3f dest) {
        return dest.set(this);
    }

    /**
     * Get the current values of the upper left 3x3 submatrix of <code>this</code> matrix and store them into
     * <code>dest</code>.
     * 
     * @param dest
     *            the destination matrix
     * @return the passed in destination
     */
    public Matrix3d get3x3(Matrix3d dest) {
        return dest.set(this);
    }

    /**
     * Get the rotational component of <code>this</code> matrix and store the represented rotation
     * into the given {@link AxisAngle4f}.
     * 
     * @see AxisAngle4f#set(Matrix4f)
     * 
     * @param dest
     *          the destination {@link AxisAngle4f}
     * @return the passed in destination
     */
    public AxisAngle4f getRotation(AxisAngle4f dest) {
        return dest.set(this);
    }

    /**
     * Get the rotational component of <code>this</code> matrix and store the represented rotation
     * into the given {@link AxisAngle4d}.
     * 
     * @see AxisAngle4f#set(Matrix4f)
     * 
     * @param dest
     *          the destination {@link AxisAngle4d}
     * @return the passed in destination
     */
    public AxisAngle4d getRotation(AxisAngle4d dest) {
        return dest.set(this);
    }

    /**
     * Get the current values of <code>this</code> matrix and store the represented rotation
     * into the given {@link Quaternionf}.
     * <p>
     * This method assumes that the first three column vectors of the upper left 3x3 submatrix are not normalized and
     * thus allows to ignore any additional scaling factor that is applied to the matrix.
     * 
     * @see Quaternionf#setFromUnnormalized(Matrix4f)
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
     * @see Quaternionf#setFromNormalized(Matrix4f)
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
     * @see Quaterniond#setFromUnnormalized(Matrix4f)
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
     * @see Quaterniond#setFromNormalized(Matrix4f)
     * 
     * @param dest
     *          the destination {@link Quaterniond}
     * @return the passed in destination
     */
    public Quaterniond getNormalizedRotation(Quaterniond dest) {
        return dest.setFromNormalized(this);
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
     * 
     * @param index
     *            the absolute position into the FloatBuffer
     * @param buffer
     *            will receive the values of this matrix in column-major order
     * @return the passed in buffer
     */
    public FloatBuffer get(int index, FloatBuffer buffer) {
        MemUtil.INSTANCE.put(this, index, buffer);
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
        MemUtil.INSTANCE.put(this, index, buffer);
        return buffer;
    }

    /**
     * Store the transpose of this matrix in column-major order into the supplied {@link FloatBuffer} at the current
     * buffer {@link FloatBuffer#position() position}.
     * <p>
     * This method will not increment the position of the given FloatBuffer.
     * <p>
     * In order to specify the offset into the FloatBuffer at which
     * the matrix is stored, use {@link #getTransposed(int, FloatBuffer)}, taking
     * the absolute position as parameter.
     * 
     * @see #getTransposed(int, FloatBuffer)
     * 
     * @param buffer
     *            will receive the values of this matrix in column-major order at its current position
     * @return the passed in buffer
     */
    public FloatBuffer getTransposed(FloatBuffer buffer) {
        return getTransposed(buffer.position(), buffer);
    }

    /**
     * Store the transpose of this matrix in column-major order into the supplied {@link FloatBuffer} starting at the specified
     * absolute buffer position/index.
     * <p>
     * This method will not increment the position of the given FloatBuffer.
     * 
     * @param index
     *            the absolute position into the FloatBuffer
     * @param buffer
     *            will receive the values of this matrix in column-major order
     * @return the passed in buffer
     */
    public FloatBuffer getTransposed(int index, FloatBuffer buffer) {
        MemUtil.INSTANCE.putTransposed(this, index, buffer);
        return buffer;
    }

    /**
     * Store the transpose of this matrix in column-major order into the supplied {@link ByteBuffer} at the current
     * buffer {@link ByteBuffer#position() position}.
     * <p>
     * This method will not increment the position of the given ByteBuffer.
     * <p>
     * In order to specify the offset into the ByteBuffer at which
     * the matrix is stored, use {@link #getTransposed(int, ByteBuffer)}, taking
     * the absolute position as parameter.
     * 
     * @see #getTransposed(int, ByteBuffer)
     * 
     * @param buffer
     *            will receive the values of this matrix in column-major order at its current position
     * @return the passed in buffer
     */
    public ByteBuffer getTransposed(ByteBuffer buffer) {
        return getTransposed(buffer.position(), buffer);
    }

    /**
     * Store the transpose of this matrix in column-major order into the supplied {@link ByteBuffer} starting at the specified
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
    public ByteBuffer getTransposed(int index, ByteBuffer buffer) {
        MemUtil.INSTANCE.putTransposed(this, index, buffer);
        return buffer;
    }

    /**
     * Store this matrix into the supplied float array in column-major order at the given offset.
     * 
     * @param arr
     *          the array to write the matrix values into
     * @param offset
     *          the offset into the array
     * @return the passed in array
     */
    public float[] get(float[] arr, int offset) {
        System.arraycopy(ms, 0, arr, offset, 16);
        return arr;
    }

    /**
     * Store this matrix into the supplied float array in column-major order.
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
     * Set all the values within this matrix to <code>0</code>.
     * 
     * @return this
     */
    public Matrix4f zero() {
        Arrays.fill(ms, 0.0f);
        return this;
    }

    /**
     * Set this matrix to be a simple scale matrix, which scales all axes uniformly by the given factor.
     * <p>
     * The resulting matrix can be multiplied against another transformation
     * matrix to obtain an additional scaling.
     * <p>
     * In order to post-multiply a scaling transformation directly to a
     * matrix, use {@link #scale(float) scale()} instead.
     * 
     * @see #scale(float)
     * 
     * @param factor
     *             the scale factor in x, y and z
     * @return this
     */
    public Matrix4f scaling(float factor) {
        ms[M00] = factor;
        ms[M01] = 0.0f;
        ms[M02] = 0.0f;
        ms[M03] = 0.0f;
        ms[M10] = 0.0f;
        ms[M11] = factor;
        ms[M12] = 0.0f;
        ms[M13] = 0.0f;
        ms[M20] = 0.0f;
        ms[M21] = 0.0f;
        ms[M22] = factor;
        ms[M23] = 0.0f;
        ms[M30] = 0.0f;
        ms[M31] = 0.0f;
        ms[M32] = 0.0f;
        ms[M33] = 1.0f;
        return this;
    }

    /**
     * Set this matrix to be a simple scale matrix.
     * <p>
     * The resulting matrix can be multiplied against another transformation
     * matrix to obtain an additional scaling.
     * <p>
     * In order to post-multiply a scaling transformation directly to a
     * matrix, use {@link #scale(float, float, float) scale()} instead.
     * 
     * @see #scale(float, float, float)
     * 
     * @param x
     *             the scale in x
     * @param y
     *             the scale in y
     * @param z
     *             the scale in z
     * @return this
     */
    public Matrix4f scaling(float x, float y, float z) {
        ms[M00] = x;
        ms[M01] = 0.0f;
        ms[M02] = 0.0f;
        ms[M03] = 0.0f;
        ms[M10] = 0.0f;
        ms[M11] = y;
        ms[M12] = 0.0f;
        ms[M13] = 0.0f;
        ms[M20] = 0.0f;
        ms[M21] = 0.0f;
        ms[M22] = z;
        ms[M23] = 0.0f;
        ms[M30] = 0.0f;
        ms[M31] = 0.0f;
        ms[M32] = 0.0f;
        ms[M33] = 1.0f;
        return this;
    }
    
    /**
     * Set this matrix to be a simple scale matrix which scales the base axes by <tt>xyz.x</tt>, <tt>xyz.y</tt> and <tt>xyz.z</tt> respectively.
     * <p>
     * The resulting matrix can be multiplied against another transformation
     * matrix to obtain an additional scaling.
     * <p>
     * In order to post-multiply a scaling transformation directly to a
     * matrix use {@link #scale(Vector3f) scale()} instead.
     * 
     * @see #scale(Vector3f)
     * 
     * @param xyz
     *             the scale in x, y and z respectively
     * @return this
     */
    public Matrix4f scaling(Vector3f xyz) {
        return scaling(xyz.x, xyz.y, xyz.z);
    }

    /**
     * Set this matrix to a rotation matrix which rotates the given radians about a given axis.
     * <p>
     * The resulting matrix can be multiplied against another transformation
     * matrix to obtain an additional rotation.
     * <p>
     * In order to post-multiply a rotation transformation directly to a
     * matrix, use {@link #rotate(float, Vector3f) rotate()} instead.
     * 
     * @see #rotate(float, Vector3f)
     * 
     * @param angle
     *          the angle in radians
     * @param axis
     *          the axis to rotate about (needs to be {@link Vector3f#normalize() normalized})
     * @return this
     */
    public Matrix4f rotation(float angle, Vector3f axis) {
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
    public Matrix4f rotation(AxisAngle4f axisAngle) {
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
     * use {@link #rotate(float, float, float, float) rotate()} instead.
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Rotation_matrix_from_axis_and_angle">http://en.wikipedia.org</a>
     * 
     * @see #rotate(float, float, float, float)
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
    public Matrix4f rotation(float angle, float x, float y, float z) {
        float cos = (float) Math.cos(angle);
        float sin = (float) Math.sin(angle);
        float C = 1.0f - cos;
        float xy = x * y, xz = x * z, yz = y * z;
        ms[M00] = cos + x * x * C;
        ms[M10] = xy * C - z * sin;
        ms[M20] = xz * C + y * sin;
        ms[M30] = 0.0f;
        ms[M01] = xy * C + z * sin;
        ms[M11] = cos + y * y * C;
        ms[M21] = yz * C - x * sin;
        ms[M31] = 0.0f;
        ms[M02] = xz * C - y * sin;
        ms[M12] = yz * C + x * sin;
        ms[M22] = cos + z * z * C;
        ms[M32] = 0.0f;
        ms[M03] = 0.0f;
        ms[M13] = 0.0f;
        ms[M23] = 0.0f;
        ms[M33] = 1.0f;
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
    public Matrix4f rotationX(float ang) {
        float sin, cos;
        if (ang == (float) Math.PI || ang == -(float) Math.PI) {
            cos = -1.0f;
            sin = 0.0f;
        } else if (ang == (float) Math.PI * 0.5f || ang == -(float) Math.PI * 1.5f) {
            cos = 0.0f;
            sin = 1.0f;
        } else if (ang == (float) -Math.PI * 0.5f || ang == (float) Math.PI * 1.5f) {
            cos = 0.0f;
            sin = -1.0f;
        } else {
            cos = (float) Math.cos(ang);
            sin = (float) Math.sin(ang);
        }
        ms[M00] = 1.0f;
        ms[M01] = 0.0f;
        ms[M02] = 0.0f;
        ms[M03] = 0.0f;
        ms[M10] = 0.0f;
        ms[M11] = cos;
        ms[M12] = sin;
        ms[M13] = 0.0f;
        ms[M20] = 0.0f;
        ms[M21] = -sin;
        ms[M22] = cos;
        ms[M23] = 0.0f;
        ms[M30] = 0.0f;
        ms[M31] = 0.0f;
        ms[M32] = 0.0f;
        ms[M33] = 1.0f;
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
    public Matrix4f rotationY(float ang) {
        float sin, cos;
        if (ang == (float) Math.PI || ang == -(float) Math.PI) {
            cos = -1.0f;
            sin = 0.0f;
        } else if (ang == (float) Math.PI * 0.5f || ang == -(float) Math.PI * 1.5f) {
            cos = 0.0f;
            sin = 1.0f;
        } else if (ang == (float) -Math.PI * 0.5f || ang == (float) Math.PI * 1.5f) {
            cos = 0.0f;
            sin = -1.0f;
        } else {
            cos = (float) Math.cos(ang);
            sin = (float) Math.sin(ang);
        }
        ms[M00] = cos;
        ms[M01] = 0.0f;
        ms[M02] = -sin;
        ms[M03] = 0.0f;
        ms[M10] = 0.0f;
        ms[M11] = 1.0f;
        ms[M12] = 0.0f;
        ms[M13] = 0.0f;
        ms[M20] = sin;
        ms[M21] = 0.0f;
        ms[M22] = cos;
        ms[M23] = 0.0f;
        ms[M30] = 0.0f;
        ms[M31] = 0.0f;
        ms[M32] = 0.0f;
        ms[M33] = 1.0f;
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
    public Matrix4f rotationZ(float ang) {
        float sin, cos;
        if (ang == (float) Math.PI || ang == -(float) Math.PI) {
            cos = -1.0f;
            sin = 0.0f;
        } else if (ang == (float) Math.PI * 0.5f || ang == -(float) Math.PI * 1.5f) {
            cos = 0.0f;
            sin = 1.0f;
        } else if (ang == (float) -Math.PI * 0.5f || ang == (float) Math.PI * 1.5f) {
            cos = 0.0f;
            sin = -1.0f;
        } else {
            cos = (float) Math.cos(ang);
            sin = (float) Math.sin(ang);
        }
        ms[M00] = cos;
        ms[M01] = sin;
        ms[M02] = 0.0f;
        ms[M03] = 0.0f;
        ms[M10] = -sin;
        ms[M11] = cos;
        ms[M12] = 0.0f;
        ms[M13] = 0.0f;
        ms[M20] = 0.0f;
        ms[M21] = 0.0f;
        ms[M22] = 1.0f;
        ms[M23] = 0.0f;
        ms[M30] = 0.0f;
        ms[M31] = 0.0f;
        ms[M32] = 0.0f;
        ms[M33] = 1.0f;
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
    public Matrix4f rotationXYZ(float angleX, float angleY, float angleZ) {
        float cosX = (float) Math.cos(angleX);
        float sinX = (float) Math.sin(angleX);
        float cosY = (float) Math.cos(angleY);
        float sinY = (float) Math.sin(angleY);
        float cosZ = (float) Math.cos(angleZ);
        float sinZ = (float) Math.sin(angleZ);
        float m_sinX = -sinX;
        float m_sinY = -sinY;
        float m_sinZ = -sinZ;

        // rotateX
        float nn11 = cosX;
        float nn12 = sinX;
        float nn21 = m_sinX;
        float nn22 = cosX;
        // rotateY
        float nn00 = cosY;
        float nn01 = nn21 * m_sinY;
        float nn02 = nn22 * m_sinY;
        ms[M20] = sinY;
        ms[M21] = nn21 * cosY;
        ms[M22] = nn22 * cosY;
        ms[M23] = 0.0f;
        // rotateZ
        ms[M00] = nn00 * cosZ;
        ms[M01] = nn01 * cosZ + nn11 * sinZ;
        ms[M02] = nn02 * cosZ + nn12 * sinZ;
        ms[M03] = 0.0f;
        ms[M10] = nn00 * m_sinZ;
        ms[M11] = nn01 * m_sinZ + nn11 * cosZ;
        ms[M12] = nn02 * m_sinZ + nn12 * cosZ;
        ms[M13] = 0.0f;
        // set last column to identity
        ms[M30] = 0.0f;
        ms[M31] = 0.0f;
        ms[M32] = 0.0f;
        ms[M33] = 1.0f;
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
    public Matrix4f rotationZYX(float angleZ, float angleY, float angleX) {
        float cosZ = (float) Math.cos(angleZ);
        float sinZ = (float) Math.sin(angleZ);
        float cosY = (float) Math.cos(angleY);
        float sinY = (float) Math.sin(angleY);
        float cosX = (float) Math.cos(angleX);
        float sinX = (float) Math.sin(angleX);
        float m_sinZ = -sinZ;
        float m_sinY = -sinY;
        float m_sinX = -sinX;

        // rotateZ
        float nn00 = cosZ;
        float nn01 = sinZ;
        float nn10 = m_sinZ;
        float nn11 = cosZ;
        // rotateY
        float nn20 = nn00 * sinY;
        float nn21 = nn01 * sinY;
        float nn22 = cosY;
        ms[M00] = nn00 * cosY;
        ms[M01] = nn01 * cosY;
        ms[M02] = m_sinY;
        ms[M03] = 0.0f;
        // rotateX
        ms[M10] = nn10 * cosX + nn20 * sinX;
        ms[M11] = nn11 * cosX + nn21 * sinX;
        ms[M12] = nn22 * sinX;
        ms[M13] = 0.0f;
        ms[M20] = nn10 * m_sinX + nn20 * cosX;
        ms[M21] = nn11 * m_sinX + nn21 * cosX;
        ms[M22] = nn22 * cosX;
        ms[M23] = 0.0f;
        // set last column to identity
        ms[M30] = 0.0f;
        ms[M31] = 0.0f;
        ms[M32] = 0.0f;
        ms[M33] = 1.0f;
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
    public Matrix4f rotationYXZ(float angleY, float angleX, float angleZ) {
        float cosY = (float) Math.cos(angleY);
        float sinY = (float) Math.sin(angleY);
        float cosX = (float) Math.cos(angleX);
        float sinX = (float) Math.sin(angleX);
        float cosZ = (float) Math.cos(angleZ);
        float sinZ = (float) Math.sin(angleZ);
        float m_sinY = -sinY;
        float m_sinX = -sinX;
        float m_sinZ = -sinZ;

        // rotateY
        float nn00 = cosY;
        float nn02 = m_sinY;
        float nn20 = sinY;
        float nn22 = cosY;
        // rotateX
        float nn10 = nn20 * sinX;
        float nn11 = cosX;
        float nn12 = nn22 * sinX;
        ms[M20] = nn20 * cosX;
        ms[M21] = m_sinX;
        ms[M22] = nn22 * cosX;
        ms[M23] = 0.0f;
        // rotateZ
        ms[M00] = nn00 * cosZ + nn10 * sinZ;
        ms[M01] = nn11 * sinZ;
        ms[M02] = nn02 * cosZ + nn12 * sinZ;
        ms[M03] = 0.0f;
        ms[M10] = nn00 * m_sinZ + nn10 * cosZ;
        ms[M11] = nn11 * cosZ;
        ms[M12] = nn02 * m_sinZ + nn12 * cosZ;
        ms[M13] = 0.0f;
        // set last column to identity
        ms[M30] = 0.0f;
        ms[M31] = 0.0f;
        ms[M32] = 0.0f;
        ms[M33] = 1.0f;
        return this;
    }

    /**
     * Set only the upper left 3x3 submatrix of this matrix to a rotation of <code>angleX</code> radians about the X axis, followed by a rotation
     * of <code>angleY</code> radians about the Y axis and followed by a rotation of <code>angleZ</code> radians about the Z axis.
     * 
     * @param angleX
     *            the angle to rotate about X
     * @param angleY
     *            the angle to rotate about Y
     * @param angleZ
     *            the angle to rotate about Z
     * @return this
     */
    public Matrix4f setRotationXYZ(float angleX, float angleY, float angleZ) {
        float cosX = (float) Math.cos(angleX);
        float sinX = (float) Math.sin(angleX);
        float cosY = (float) Math.cos(angleY);
        float sinY = (float) Math.sin(angleY);
        float cosZ = (float) Math.cos(angleZ);
        float sinZ = (float) Math.sin(angleZ);
        float m_sinX = -sinX;
        float m_sinY = -sinY;
        float m_sinZ = -sinZ;

        // rotateX
        float nn11 = cosX;
        float nn12 = sinX;
        float nn21 = m_sinX;
        float nn22 = cosX;
        // rotateY
        float nn00 = cosY;
        float nn01 = nn21 * m_sinY;
        float nn02 = nn22 * m_sinY;
        ms[M20] = sinY;
        ms[M21] = nn21 * cosY;
        ms[M22] = nn22 * cosY;
        // rotateZ
        ms[M00] = nn00 * cosZ;
        ms[M01] = nn01 * cosZ + nn11 * sinZ;
        ms[M02] = nn02 * cosZ + nn12 * sinZ;
        ms[M10] = nn00 * m_sinZ;
        ms[M11] = nn01 * m_sinZ + nn11 * cosZ;
        ms[M12] = nn02 * m_sinZ + nn12 * cosZ;
        return this;
    }

    /**
     * Set only the upper left 3x3 submatrix of this matrix to a rotation of <code>angleZ</code> radians about the Z axis, followed by a rotation
     * of <code>angleY</code> radians about the Y axis and followed by a rotation of <code>angleX</code> radians about the X axis.
     * 
     * @param angleZ
     *            the angle to rotate about Z
     * @param angleY
     *            the angle to rotate about Y
     * @param angleX
     *            the angle to rotate about X
     * @return this
     */
    public Matrix4f setRotationZYX(float angleZ, float angleY, float angleX) {
        float cosZ = (float) Math.cos(angleZ);
        float sinZ = (float) Math.sin(angleZ);
        float cosY = (float) Math.cos(angleY);
        float sinY = (float) Math.sin(angleY);
        float cosX = (float) Math.cos(angleX);
        float sinX = (float) Math.sin(angleX);
        float m_sinZ = -sinZ;
        float m_sinY = -sinY;
        float m_sinX = -sinX;

        // rotateZ
        float nn00 = cosZ;
        float nn01 = sinZ;
        float nn10 = m_sinZ;
        float nn11 = cosZ;
        // rotateY
        float nn20 = nn00 * sinY;
        float nn21 = nn01 * sinY;
        float nn22 = cosY;
        ms[M00] = nn00 * cosY;
        ms[M01] = nn01 * cosY;
        ms[M02] = m_sinY;
        // rotateX
        ms[M10] = nn10 * cosX + nn20 * sinX;
        ms[M11] = nn11 * cosX + nn21 * sinX;
        ms[M12] = nn22 * sinX;
        ms[M20] = nn10 * m_sinX + nn20 * cosX;
        ms[M21] = nn11 * m_sinX + nn21 * cosX;
        ms[M22] = nn22 * cosX;
        return this;
    }

    /**
     * Set only the upper left 3x3 submatrix of this matrix to a rotation of <code>angleY</code> radians about the Y axis, followed by a rotation
     * of <code>angleX</code> radians about the X axis and followed by a rotation of <code>angleZ</code> radians about the Z axis.
     * 
     * @param angleY
     *            the angle to rotate about Y
     * @param angleX
     *            the angle to rotate about X
     * @param angleZ
     *            the angle to rotate about Z
     * @return this
     */
    public Matrix4f setRotationYXZ(float angleY, float angleX, float angleZ) {
        float cosY = (float) Math.cos(angleY);
        float sinY = (float) Math.sin(angleY);
        float cosX = (float) Math.cos(angleX);
        float sinX = (float) Math.sin(angleX);
        float cosZ = (float) Math.cos(angleZ);
        float sinZ = (float) Math.sin(angleZ);
        float m_sinY = -sinY;
        float m_sinX = -sinX;
        float m_sinZ = -sinZ;

        // rotateY
        float nn00 = cosY;
        float nn02 = m_sinY;
        float nn20 = sinY;
        float nn22 = cosY;
        // rotateX
        float nn10 = nn20 * sinX;
        float nn11 = cosX;
        float nn12 = nn22 * sinX;
        ms[M20] = nn20 * cosX;
        ms[M21] = m_sinX;
        ms[M22] = nn22 * cosX;
        // rotateZ
        ms[M00] = nn00 * cosZ + nn10 * sinZ;
        ms[M01] = nn11 * sinZ;
        ms[M02] = nn02 * cosZ + nn12 * sinZ;
        ms[M10] = nn00 * m_sinZ + nn10 * cosZ;
        ms[M11] = nn11 * cosZ;
        ms[M12] = nn02 * m_sinZ + nn12 * cosZ;
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
    public Matrix4f rotation(Quaternionf quat) {
        float dqx = quat.x + quat.x;
        float dqy = quat.y + quat.y;
        float dqz = quat.z + quat.z;
        float q00 = dqx * quat.x;
        float q11 = dqy * quat.y;
        float q22 = dqz * quat.z;
        float q01 = dqx * quat.y;
        float q02 = dqx * quat.z;
        float q03 = dqx * quat.w;
        float q12 = dqy * quat.z;
        float q13 = dqy * quat.w;
        float q23 = dqz * quat.w;

        ms[M00] = 1.0f - q11 - q22;
        ms[M01] = q01 + q23;
        ms[M02] = q02 - q13;
        ms[M03] = 0.0f;
        ms[M10] = q01 - q23;
        ms[M11] = 1.0f - q22 - q00;
        ms[M12] = q12 + q03;
        ms[M13] = 0.0f;
        ms[M20] = q02 + q13;
        ms[M21] = q12 - q03;
        ms[M22] = 1.0f - q11 - q00;
        ms[M23] = 0.0f;
        ms[M30] = 0.0f;
        ms[M31] = 0.0f;
        ms[M32] = 0.0f;
        ms[M33] = 1.0f;

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
    public Matrix4f translationRotateScale(Vector3f translation, 
                                           Quaternionf quat, 
                                           Vector3f scale) {
        return translationRotateScale(translation.x, translation.y, translation.z, quat.x, quat.y, quat.z, quat.w, scale.x, scale.y, scale.z);
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
     * @see #translation(float, float, float)
     * @see #rotate(Quaternionf)
     * @see #scale(float, float, float)
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
    public Matrix4f translationRotateScale(float tx, float ty, float tz, 
                                           float qx, float qy, float qz, float qw, 
                                           float sx, float sy, float sz) {
        float dqx = qx + qx;
        float dqy = qy + qy;
        float dqz = qz + qz;
        float q00 = dqx * qx;
        float q11 = dqy * qy;
        float q22 = dqz * qz;
        float q01 = dqx * qy;
        float q02 = dqx * qz;
        float q03 = dqx * qw;
        float q12 = dqy * qz;
        float q13 = dqy * qw;
        float q23 = dqz * qw;
        ms[M00] = sx - (q11 + q22) * sx;
        ms[M01] = (q01 + q23) * sx;
        ms[M02] = (q02 - q13) * sx;
        ms[M03] = 0.0f;
        ms[M10] = (q01 - q23) * sy;
        ms[M11] = sy - (q22 + q00) * sy;
        ms[M12] = (q12 + q03) * sy;
        ms[M13] = 0.0f;
        ms[M20] = (q02 + q13) * sz;
        ms[M21] = (q12 - q03) * sz;
        ms[M22] = sz - (q11 + q00) * sz;
        ms[M23] = 0.0f;
        ms[M30] = tx;
        ms[M31] = ty;
        ms[M32] = tz;
        ms[M33] = 1.0f;
        return this;
    }

    /**
     * Set <code>this</code> matrix to <tt>T * R * S * M</tt>, where <tt>T</tt> is the given <code>translation</code>,
     * <tt>R</tt> is a rotation transformation specified by the given quaternion, <tt>S</tt> is a scaling transformation
     * which scales the axes by <code>scale</code> and <code>M</code> is an {@link #isAffine() affine} matrix.
     * <p>
     * When transforming a vector by the resulting matrix the transformation described by <code>M</code> will be applied first, then the scaling, then rotation and
     * at last the translation.
     * <p>
     * This method is equivalent to calling: <tt>translation(translation).rotate(quat).scale(scale).mulAffine(m)</tt>
     * 
     * @see #translation(Vector3f)
     * @see #rotate(Quaternionf)
     * @see #mulAffine(Matrix4f)
     * 
     * @param translation
     *          the translation
     * @param quat
     *          the quaternion representing a rotation
     * @param scale
     *          the scaling factors
     * @param m
     *          the {@link #isAffine() affine} matrix to multiply by
     * @return this
     */
    public Matrix4f translationRotateScaleMulAffine(Vector3f translation, 
                                                    Quaternionf quat, 
                                                    Vector3f scale,
                                                    Matrix4f m) {
        return translationRotateScaleMulAffine(translation.x, translation.y, translation.z, quat.x, quat.y, quat.z, quat.w, scale.x, scale.y, scale.z, m);
    }

    /**
     * Set <code>this</code> matrix to <tt>T * R * S * M</tt>, where <tt>T</tt> is a translation by the given <tt>(tx, ty, tz)</tt>,
     * <tt>R</tt> is a rotation transformation specified by the quaternion <tt>(qx, qy, qz, qw)</tt>, <tt>S</tt> is a scaling transformation
     * which scales the three axes x, y and z by <tt>(sx, sy, sz)</tt> and <code>M</code> is an {@link #isAffine() affine} matrix.
     * <p>
     * When transforming a vector by the resulting matrix the transformation described by <code>M</code> will be applied first, then the scaling, then rotation and
     * at last the translation.
     * <p>
     * This method is equivalent to calling: <tt>translation(tx, ty, tz).rotate(quat).scale(sx, sy, sz).mulAffine(m)</tt>
     * 
     * @see #translation(float, float, float)
     * @see #rotate(Quaternionf)
     * @see #scale(float, float, float)
     * @see #mulAffine(Matrix4f)
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
     * @param m
     *          the {@link #isAffine() affine} matrix to multiply by
     * @return this
     */
    public Matrix4f translationRotateScaleMulAffine(float tx, float ty, float tz, 
                                                    float qx, float qy, float qz, float qw, 
                                                    float sx, float sy, float sz,
                                                    Matrix4f m) {
        float dqx = qx + qx;
        float dqy = qy + qy;
        float dqz = qz + qz;
        float q00 = dqx * qx;
        float q11 = dqy * qy;
        float q22 = dqz * qz;
        float q01 = dqx * qy;
        float q02 = dqx * qz;
        float q03 = dqx * qw;
        float q12 = dqy * qz;
        float q13 = dqy * qw;
        float q23 = dqz * qw;
        float nm00 = sx - (q11 + q22) * sx;
        float nm01 = (q01 + q23) * sx;
        float nm02 = (q02 - q13) * sx;
        float nm10 = (q01 - q23) * sy;
        float nm11 = sy - (q22 + q00) * sy;
        float nm12 = (q12 + q03) * sy;
        float nm20 = (q02 + q13) * sz;
        float nm21 = (q12 - q03) * sz;
        float nm22 = sz - (q11 + q00) * sz;
        float m00 = nm00 * m.ms[M00] + nm10 * m.ms[M01] + nm20 * m.ms[M02];
        float m01 = nm01 * m.ms[M00] + nm11 * m.ms[M01] + nm21 * m.ms[M02];
        ms[M02] = nm02 * m.ms[M00] + nm12 * m.ms[M01] + nm22 * m.ms[M02];
        this.ms[M00] = m00;
        this.ms[M01] = m01;
        ms[M03] = 0.0f;
        float m10 = nm00 * m.ms[M10] + nm10 * m.ms[M11] + nm20 * m.ms[M12];
        float m11 = nm01 * m.ms[M10] + nm11 * m.ms[M11] + nm21 * m.ms[M12];
        ms[M12] = nm02 * m.ms[M10] + nm12 * m.ms[M11] + nm22 * m.ms[M12];
        this.ms[M10] = m10;
        this.ms[M11] = m11;
        ms[M13] = 0.0f;
        float m20 = nm00 * m.ms[M20] + nm10 * m.ms[M21] + nm20 * m.ms[M22];
        float m21 = nm01 * m.ms[M20] + nm11 * m.ms[M21] + nm21 * m.ms[M22];
        ms[M22] = nm02 * m.ms[M20] + nm12 * m.ms[M21] + nm22 * m.ms[M22];
        this.ms[M20] = m20;
        this.ms[M21] = m21;
        ms[M23] = 0.0f;
        float m30 = nm00 * m.ms[M30] + nm10 * m.ms[M31] + nm20 * m.ms[M32] + tx;
        float m31 = nm01 * m.ms[M30] + nm11 * m.ms[M31] + nm21 * m.ms[M32] + ty;
        ms[M32] = nm02 * m.ms[M30] + nm12 * m.ms[M31] + nm22 * m.ms[M32] + tz;
        this.ms[M30] = m30;
        this.ms[M31] = m31;
        ms[M33] = 1.0f;
        return this;
    }

    /**
     * Set <code>this</code> matrix to <tt>T * R</tt>, where <tt>T</tt> is a translation by the given <tt>(tx, ty, tz)</tt> and
     * <tt>R</tt> is a rotation transformation specified by the given quaternion.
     * <p>
     * When transforming a vector by the resulting matrix the rotation transformation will be applied first and then the translation.
     * <p>
     * This method is equivalent to calling: <tt>translation(tx, ty, tz).rotate(quat)</tt>
     * 
     * @see #translation(float, float, float)
     * @see #rotate(Quaternionf)
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
    public Matrix4f translationRotate(float tx, float ty, float tz, Quaternionf quat) {
        float dqx = quat.x + quat.x;
        float dqy = quat.y + quat.y;
        float dqz = quat.z + quat.z;
        float q00 = dqx * quat.x;
        float q11 = dqy * quat.y;
        float q22 = dqz * quat.z;
        float q01 = dqx * quat.y;
        float q02 = dqx * quat.z;
        float q03 = dqx * quat.w;
        float q12 = dqy * quat.z;
        float q13 = dqy * quat.w;
        float q23 = dqz * quat.w;
        ms[M00] = 1.0f - (q11 + q22);
        ms[M01] = q01 + q23;
        ms[M02] = q02 - q13;
        ms[M03] = 0.0f;
        ms[M10] = q01 - q23;
        ms[M11] = 1.0f - (q22 + q00);
        ms[M12] = q12 + q03;
        ms[M13] = 0.0f;
        ms[M20] = q02 + q13;
        ms[M21] = q12 - q03;
        ms[M22] = 1.0f - (q11 + q00);
        ms[M23] = 0.0f;
        ms[M30] = tx;
        ms[M31] = ty;
        ms[M32] = tz;
        ms[M33] = 1.0f;
        return this;
    }

    /**
     * Set the upper 3x3 matrix of this {@link Matrix4f} to the given {@link Matrix3f} and the rest to the identity.
     * 
     * @param mat
     *          the 3x3 matrix
     * @return this
     */
    public Matrix4f set3x3(Matrix3f mat) {
        ms[M00] = mat.ms[Matrix3f.M00];
        ms[M01] = mat.ms[Matrix3f.M01];
        ms[M02] = mat.ms[Matrix3f.M02];
        ms[M03] = 0.0f;
        ms[M10] = mat.ms[Matrix3f.M10];
        ms[M11] = mat.ms[Matrix3f.M11];
        ms[M12] = mat.ms[Matrix3f.M12];
        ms[M13] = 0.0f;
        ms[M20] = mat.ms[Matrix3f.M20];
        ms[M21] = mat.ms[Matrix3f.M21];
        ms[M22] = mat.ms[Matrix3f.M22];
        ms[M23] = 0.0f;
        ms[M30] = 0.0f;
        ms[M31] = 0.0f;
        ms[M32] = 0.0f;
        ms[M33] = 1.0f;
        return this;
    }

    /**
     * Transform/multiply the given vector by this matrix and store the result in that vector.
     * 
     * @see Vector4f#mul(Matrix4f)
     * 
     * @param v
     *          the vector to transform and to hold the final result
     * @return v
     */
    public Vector4f transform(Vector4f v) {
        return v.mul(this);
    }

    /**
     * Transform/multiply the given vector by this matrix and store the result in <code>dest</code>.
     * 
     * @see Vector4f#mul(Matrix4f, Vector4f)
     * 
     * @param v
     *          the vector to transform
     * @param dest
     *          will contain the result
     * @return dest
     */
    public Vector4f transform(Vector4f v, Vector4f dest) {
        return v.mul(this, dest);
    }

    /**
     * Transform/multiply the given vector by this matrix, perform perspective divide and store the result in that vector.
     * 
     * @see Vector4f#mulProject(Matrix4f)
     * 
     * @param v
     *          the vector to transform and to hold the final result
     * @return v
     */
    public Vector4f transformProject(Vector4f v) {
        return v.mulProject(this);
    }

    /**
     * Transform/multiply the given vector by this matrix, perform perspective divide and store the result in <code>dest</code>.
     * 
     * @see Vector4f#mulProject(Matrix4f, Vector4f)
     * 
     * @param v
     *          the vector to transform
     * @param dest
     *          will contain the result
     * @return dest
     */
    public Vector4f transformProject(Vector4f v, Vector4f dest) {
        return v.mulProject(this, dest);
    }

    /**
     * Transform/multiply the given vector by this matrix, perform perspective divide and store the result in that vector.
     * <p>
     * This method uses <tt>w=1.0</tt> as the fourth vector component.
     * 
     * @see Vector3f#mulProject(Matrix4f)
     * 
     * @param v
     *          the vector to transform and to hold the final result
     * @return v
     */
    public Vector3f transformProject(Vector3f v) {
        return v.mulProject(this);
    }

    /**
     * Transform/multiply the given vector by this matrix, perform perspective divide and store the result in <code>dest</code>.
     * <p>
     * This method uses <tt>w=1.0</tt> as the fourth vector component.
     * 
     * @see Vector3f#mulProject(Matrix4f, Vector3f)
     * 
     * @param v
     *          the vector to transform
     * @param dest
     *          will contain the result
     * @return dest
     */
    public Vector3f transformProject(Vector3f v, Vector3f dest) {
        return v.mulProject(this, dest);
    }

    /**
     * Transform/multiply the given 3D-vector, as if it was a 4D-vector with w=1, by
     * this matrix and store the result in that vector.
     * <p>
     * The given 3D-vector is treated as a 4D-vector with its w-component being 1.0, so it
     * will represent a position/location in 3D-space rather than a direction. This method is therefore
     * not suited for perspective projection transformations as it will not save the
     * <tt>w</tt> component of the transformed vector.
     * For perspective projection use {@link #transform(Vector4f)} or {@link #transformProject(Vector3f)}
     * when perspective divide should be applied, too.
     * <p>
     * In order to store the result in another vector, use {@link #transformPosition(Vector3f, Vector3f)}.
     * 
     * @see #transformPosition(Vector3f, Vector3f)
     * @see #transform(Vector4f)
     * @see #transformProject(Vector3f)
     * 
     * @param v
     *          the vector to transform and to hold the final result
     * @return v
     */
    public Vector3f transformPosition(Vector3f v) {
        v.set(ms[M00] * v.x + ms[M10] * v.y + ms[M20] * v.z + ms[M30],
              ms[M01] * v.x + ms[M11] * v.y + ms[M21] * v.z + ms[M31],
              ms[M02] * v.x + ms[M12] * v.y + ms[M22] * v.z + ms[M32]);
        return v;
    }

    /**
     * Transform/multiply the given 3D-vector, as if it was a 4D-vector with w=1, by
     * this matrix and store the result in <code>dest</code>.
     * <p>
     * The given 3D-vector is treated as a 4D-vector with its w-component being 1.0, so it
     * will represent a position/location in 3D-space rather than a direction. This method is therefore
     * not suited for perspective projection transformations as it will not save the
     * <tt>w</tt> component of the transformed vector.
     * For perspective projection use {@link #transform(Vector4f, Vector4f)} or
     * {@link #transformProject(Vector3f, Vector3f)} when perspective divide should be applied, too.
     * <p>
     * In order to store the result in the same vector, use {@link #transformPosition(Vector3f)}.
     * 
     * @see #transformPosition(Vector3f)
     * @see #transform(Vector4f, Vector4f)
     * @see #transformProject(Vector3f, Vector3f)
     * 
     * @param v
     *          the vector to transform
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Vector3f transformPosition(Vector3f v, Vector3f dest) {
        dest.set(ms[M00] * v.x + ms[M10] * v.y + ms[M20] * v.z + ms[M30],
                 ms[M01] * v.x + ms[M11] * v.y + ms[M21] * v.z + ms[M31],
                 ms[M02] * v.x + ms[M12] * v.y + ms[M22] * v.z + ms[M32]);
        return dest;
    }

    /**
     * Transform/multiply the given 3D-vector, as if it was a 4D-vector with w=0, by
     * this matrix and store the result in that vector.
     * <p>
     * The given 3D-vector is treated as a 4D-vector with its w-component being <tt>0.0</tt>, so it
     * will represent a direction in 3D-space rather than a position. This method will therefore
     * not take the translation part of the matrix into account.
     * <p>
     * In order to store the result in another vector, use {@link #transformDirection(Vector3f, Vector3f)}.
     * 
     * @see #transformDirection(Vector3f, Vector3f)
     * 
     * @param v
     *          the vector to transform and to hold the final result
     * @return v
     */
    public Vector3f transformDirection(Vector3f v) {
        v.set(ms[M00] * v.x + ms[M10] * v.y + ms[M20] * v.z,
              ms[M01] * v.x + ms[M11] * v.y + ms[M21] * v.z,
              ms[M02] * v.x + ms[M12] * v.y + ms[M22] * v.z);
        return v;
    }

    /**
     * Transform/multiply the given 3D-vector, as if it was a 4D-vector with w=0, by
     * this matrix and store the result in <code>dest</code>.
     * <p>
     * The given 3D-vector is treated as a 4D-vector with its w-component being <tt>0.0</tt>, so it
     * will represent a direction in 3D-space rather than a position. This method will therefore
     * not take the translation part of the matrix into account.
     * <p>
     * In order to store the result in the same vector, use {@link #transformDirection(Vector3f)}.
     * 
     * @see #transformDirection(Vector3f)
     * 
     * @param v
     *          the vector to transform and to hold the final result
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Vector3f transformDirection(Vector3f v, Vector3f dest) {
        dest.set(ms[M00] * v.x + ms[M10] * v.y + ms[M20] * v.z,
                 ms[M01] * v.x + ms[M11] * v.y + ms[M21] * v.z,
                 ms[M02] * v.x + ms[M12] * v.y + ms[M22] * v.z);
        return dest;
    }

    /**
     * Transform/multiply the given 4D-vector by assuming that <code>this</code> matrix represents an {@link #isAffine() affine} transformation
     * (i.e. its last row is equal to <tt>(0, 0, 0, 1)</tt>).
     * <p>
     * In order to store the result in another vector, use {@link #transformAffine(Vector4f, Vector4f)}.
     * 
     * @see #transformAffine(Vector4f, Vector4f)
     * 
     * @param v
     *          the vector to transform and to hold the final result
     * @return v
     */
    public Vector4f transformAffine(Vector4f v) {
        v.set(ms[M00] * v.x + ms[M10] * v.y + ms[M20] * v.z + ms[M30] * v.w,
              ms[M01] * v.x + ms[M11] * v.y + ms[M21] * v.z + ms[M31] * v.w,
              ms[M02] * v.x + ms[M12] * v.y + ms[M22] * v.z + ms[M32] * v.w,
              v.w);
        return v;
    }

    /**
     * Transform/multiply the given 4D-vector by assuming that <code>this</code> matrix represents an {@link #isAffine() affine} transformation
     * (i.e. its last row is equal to <tt>(0, 0, 0, 1)</tt>) and store the result in <code>dest</code>.
     * <p>
     * In order to store the result in the same vector, use {@link #transformAffine(Vector4f)}.
     * 
     * @see #transformAffine(Vector4f)
     * 
     * @param v
     *          the vector to transform and to hold the final result
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Vector4f transformAffine(Vector4f v, Vector4f dest) {
        dest.set(ms[M00] * v.x + ms[M10] * v.y + ms[M20] * v.z + ms[M30] * v.w,
                 ms[M01] * v.x + ms[M11] * v.y + ms[M21] * v.z + ms[M31] * v.w,
                 ms[M02] * v.x + ms[M12] * v.y + ms[M22] * v.z + ms[M32] * v.w,
                 v.w);
        return dest;
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
    public Matrix4f scale(Vector3f xyz, Matrix4f dest) {
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
    public Matrix4f scale(Vector3f xyz) {
        return scale(xyz.x, xyz.y, xyz.z, this);
    }

    /**
     * Apply scaling to this matrix by uniformly scaling all base axes by the given <code>xyz</code> factor
     * and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>S</code> the scaling matrix,
     * then the new matrix will be <code>M * S</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * S * v</code>, the
     * scaling will be applied first!
     * <p>
     * Individual scaling of all three axes can be applied using {@link #scale(float, float, float, Matrix4f)}. 
     * 
     * @see #scale(float, float, float, Matrix4f)
     * 
     * @param xyz
     *            the factor for all components
     * @param dest
     *            will hold the result
     * @return dest
     */
    public Matrix4f scale(float xyz, Matrix4f dest) {
        return scale(xyz, xyz, xyz, dest);
    }

    /**
     * Apply scaling to this matrix by uniformly scaling all base axes by the given <code>xyz</code> factor.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>S</code> the scaling matrix,
     * then the new matrix will be <code>M * S</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * S * v</code>, the
     * scaling will be applied first!
     * <p>
     * Individual scaling of all three axes can be applied using {@link #scale(float, float, float)}. 
     * 
     * @see #scale(float, float, float)
     * 
     * @param xyz
     *            the factor for all components
     * @return this
     */
    public Matrix4f scale(float xyz) {
        return scale(xyz, xyz, xyz);
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
    public Matrix4f scale(float x, float y, float z, Matrix4f dest) {
        // scale matrix elements:
        // ms[M00] = x, ms[M11] = y, ms[M22] = z
        // ms[M33] = 1
        // all others = 0
        dest.ms[M00] = ms[M00] * x;
        dest.ms[M01] = ms[M01] * x;
        dest.ms[M02] = ms[M02] * x;
        dest.ms[M03] = ms[M03] * x;
        dest.ms[M10] = ms[M10] * y;
        dest.ms[M11] = ms[M11] * y;
        dest.ms[M12] = ms[M12] * y;
        dest.ms[M13] = ms[M13] * y;
        dest.ms[M20] = ms[M20] * z;
        dest.ms[M21] = ms[M21] * z;
        dest.ms[M22] = ms[M22] * z;
        dest.ms[M23] = ms[M23] * z;
        dest.ms[M30] = ms[M30];
        dest.ms[M31] = ms[M31];
        dest.ms[M32] = ms[M32];
        dest.ms[M33] = ms[M33];
        return dest;
    }

    /**
     * Apply scaling to this matrix by scaling the base axes by the given x,
     * y and z factors.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>S</code> the scaling matrix,
     * then the new matrix will be <code>M * S</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * S * v</code>, the
     * scaling will be applied first!
     * 
     * @param x
     *            the factor of the x component
     * @param y
     *            the factor of the y component
     * @param z
     *            the factor of the z component
     * @return this
     */
    public Matrix4f scale(float x, float y, float z) {
        return scale(x, y, z, this);
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
    public Matrix4f rotateX(float ang, Matrix4f dest) {
        float sin, cos;
        if (ang == (float) Math.PI || ang == -(float) Math.PI) {
            cos = -1.0f;
            sin = 0.0f;
        } else if (ang == (float) Math.PI * 0.5f || ang == -(float) Math.PI * 1.5f) {
            cos = 0.0f;
            sin = 1.0f;
        } else if (ang == (float) -Math.PI * 0.5f || ang == (float) Math.PI * 1.5f) {
            cos = 0.0f;
            sin = -1.0f;
        } else {
            cos = (float) Math.cos(ang);
            sin = (float) Math.sin(ang);
        }
        float rn11 = cos;
        float rn12 = sin;
        float rn21 = -sin;
        float rn22 = cos;

        // add temporaries for dependent values
        float nn10 = ms[M10] * rn11 + ms[M20] * rn12;
        float nn11 = ms[M11] * rn11 + ms[M21] * rn12;
        float nn12 = ms[M12] * rn11 + ms[M22] * rn12;
        float nn13 = ms[M13] * rn11 + ms[M23] * rn12;
        // set non-dependent values directly
        dest.ms[M20] = ms[M10] * rn21 + ms[M20] * rn22;
        dest.ms[M21] = ms[M11] * rn21 + ms[M21] * rn22;
        dest.ms[M22] = ms[M12] * rn21 + ms[M22] * rn22;
        dest.ms[M23] = ms[M13] * rn21 + ms[M23] * rn22;
        // set other values
        dest.ms[M10] = nn10;
        dest.ms[M11] = nn11;
        dest.ms[M12] = nn12;
        dest.ms[M13] = nn13;
        dest.ms[M00] = ms[M00];
        dest.ms[M01] = ms[M01];
        dest.ms[M02] = ms[M02];
        dest.ms[M03] = ms[M03];
        dest.ms[M30] = ms[M30];
        dest.ms[M31] = ms[M31];
        dest.ms[M32] = ms[M32];
        dest.ms[M33] = ms[M33];
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
    public Matrix4f rotateX(float ang) {
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
    public Matrix4f rotateY(float ang, Matrix4f dest) {
        float cos, sin;
        if (ang == (float) Math.PI || ang == -(float) Math.PI) {
            cos = -1.0f;
            sin = 0.0f;
        } else if (ang == (float) Math.PI * 0.5f || ang == -(float) Math.PI * 1.5f) {
            cos = 0.0f;
            sin = 1.0f;
        } else if (ang == (float) -Math.PI * 0.5f || ang == (float) Math.PI * 1.5f) {
            cos = 0.0f;
            sin = -1.0f;
        } else {
            cos = (float) Math.cos(ang);
            sin = (float) Math.sin(ang);
        }
        float rn00 = cos;
        float rn02 = -sin;
        float rn20 = sin;
        float rn22 = cos;

        // add temporaries for dependent values
        float nn00 = ms[M00] * rn00 + ms[M20] * rn02;
        float nn01 = ms[M01] * rn00 + ms[M21] * rn02;
        float nn02 = ms[M02] * rn00 + ms[M22] * rn02;
        float nn03 = ms[M03] * rn00 + ms[M23] * rn02;
        // set non-dependent values directly
        dest.ms[M20] = ms[M00] * rn20 + ms[M20] * rn22;
        dest.ms[M21] = ms[M01] * rn20 + ms[M21] * rn22;
        dest.ms[M22] = ms[M02] * rn20 + ms[M22] * rn22;
        dest.ms[M23] = ms[M03] * rn20 + ms[M23] * rn22;
        // set other values
        dest.ms[M00] = nn00;
        dest.ms[M01] = nn01;
        dest.ms[M02] = nn02;
        dest.ms[M03] = nn03;
        dest.ms[M10] = ms[M10];
        dest.ms[M11] = ms[M11];
        dest.ms[M12] = ms[M12];
        dest.ms[M13] = ms[M13];
        dest.ms[M30] = ms[M30];
        dest.ms[M31] = ms[M31];
        dest.ms[M32] = ms[M32];
        dest.ms[M33] = ms[M33];
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
    public Matrix4f rotateY(float ang) {
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
    public Matrix4f rotateZ(float ang, Matrix4f dest) {
        float sin, cos;
        if (ang == (float) Math.PI || ang == -(float) Math.PI) {
            cos = -1.0f;
            sin = 0.0f;
        } else if (ang == (float) Math.PI * 0.5f || ang == -(float) Math.PI * 1.5f) {
            cos = 0.0f;
            sin = 1.0f;
        } else if (ang == (float) -Math.PI * 0.5f || ang == (float) Math.PI * 1.5f) {
            cos = 0.0f;
            sin = -1.0f;
        } else {
            cos = (float) Math.cos(ang);
            sin = (float) Math.sin(ang);
        }
        float rn00 = cos;
        float rn01 = sin;
        float rn10 = -sin;
        float rn11 = cos;

        // add temporaries for dependent values
        float nn00 = ms[M00] * rn00 + ms[M10] * rn01;
        float nn01 = ms[M01] * rn00 + ms[M11] * rn01;
        float nn02 = ms[M02] * rn00 + ms[M12] * rn01;
        float nn03 = ms[M03] * rn00 + ms[M13] * rn01;
        // set non-dependent values directly
        dest.ms[M10] = ms[M00] * rn10 + ms[M10] * rn11;
        dest.ms[M11] = ms[M01] * rn10 + ms[M11] * rn11;
        dest.ms[M12] = ms[M02] * rn10 + ms[M12] * rn11;
        dest.ms[M13] = ms[M03] * rn10 + ms[M13] * rn11;
        // set other values
        dest.ms[M00] = nn00;
        dest.ms[M01] = nn01;
        dest.ms[M02] = nn02;
        dest.ms[M03] = nn03;
        dest.ms[M20] = ms[M20];
        dest.ms[M21] = ms[M21];
        dest.ms[M22] = ms[M22];
        dest.ms[M23] = ms[M23];
        dest.ms[M30] = ms[M30];
        dest.ms[M31] = ms[M31];
        dest.ms[M32] = ms[M32];
        dest.ms[M33] = ms[M33];
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
    public Matrix4f rotateZ(float ang) {
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
    public Matrix4f rotateXYZ(float angleX, float angleY, float angleZ) {
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
    public Matrix4f rotateXYZ(float angleX, float angleY, float angleZ, Matrix4f dest) {
        float cosX = (float) Math.cos(angleX);
        float sinX = (float) Math.sin(angleX);
        float cosY = (float) Math.cos(angleY);
        float sinY = (float) Math.sin(angleY);
        float cosZ = (float) Math.cos(angleZ);
        float sinZ = (float) Math.sin(angleZ);
        float m_sinX = -sinX;
        float m_sinY = -sinY;
        float m_sinZ = -sinZ;

        // rotateX
        float nn10 = ms[M10] * cosX + ms[M20] * sinX;
        float nn11 = ms[M11] * cosX + ms[M21] * sinX;
        float nn12 = ms[M12] * cosX + ms[M22] * sinX;
        float nn13 = ms[M13] * cosX + ms[M23] * sinX;
        float nn20 = ms[M10] * m_sinX + ms[M20] * cosX;
        float nn21 = ms[M11] * m_sinX + ms[M21] * cosX;
        float nn22 = ms[M12] * m_sinX + ms[M22] * cosX;
        float nn23 = ms[M13] * m_sinX + ms[M23] * cosX;
        // rotateY
        float nn00 = ms[M00] * cosY + nn20 * m_sinY;
        float nn01 = ms[M01] * cosY + nn21 * m_sinY;
        float nn02 = ms[M02] * cosY + nn22 * m_sinY;
        float nn03 = ms[M03] * cosY + nn23 * m_sinY;
        dest.ms[M20] = ms[M00] * sinY + nn20 * cosY;
        dest.ms[M21] = ms[M01] * sinY + nn21 * cosY;
        dest.ms[M22] = ms[M02] * sinY + nn22 * cosY;
        dest.ms[M23] = ms[M03] * sinY + nn23 * cosY;
        // rotateZ
        dest.ms[M00] = nn00 * cosZ + nn10 * sinZ;
        dest.ms[M01] = nn01 * cosZ + nn11 * sinZ;
        dest.ms[M02] = nn02 * cosZ + nn12 * sinZ;
        dest.ms[M03] = nn03 * cosZ + nn13 * sinZ;
        dest.ms[M10] = nn00 * m_sinZ + nn10 * cosZ;
        dest.ms[M11] = nn01 * m_sinZ + nn11 * cosZ;
        dest.ms[M12] = nn02 * m_sinZ + nn12 * cosZ;
        dest.ms[M13] = nn03 * m_sinZ + nn13 * cosZ;
        // copy last column from 'this'
        dest.ms[M30] = ms[M30];
        dest.ms[M31] = ms[M31];
        dest.ms[M32] = ms[M32];
        dest.ms[M33] = ms[M33];
        return dest;
    }

    /**
     * Apply rotation of <code>angleX</code> radians about the X axis, followed by a rotation of <code>angleY</code> radians about the Y axis and
     * followed by a rotation of <code>angleZ</code> radians about the Z axis.
     * <p>
     * This method assumes that <code>this</code> matrix represents an {@link #isAffine() affine} transformation (i.e. its last row is equal to <tt>(0, 0, 0, 1)</tt>)
     * and can be used to speed up matrix multiplication if the matrix only represents affine transformations, such as translation, rotation, scaling and shearing (in any combination).
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
    public Matrix4f rotateAffineXYZ(float angleX, float angleY, float angleZ) {
        return rotateAffineXYZ(angleX, angleY, angleZ, this);
    }

    /**
     * Apply rotation of <code>angleX</code> radians about the X axis, followed by a rotation of <code>angleY</code> radians about the Y axis and
     * followed by a rotation of <code>angleZ</code> radians about the Z axis and store the result in <code>dest</code>.
     * <p>
     * This method assumes that <code>this</code> matrix represents an {@link #isAffine() affine} transformation (i.e. its last row is equal to <tt>(0, 0, 0, 1)</tt>)
     * and can be used to speed up matrix multiplication if the matrix only represents affine transformations, such as translation, rotation, scaling and shearing (in any combination).
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
    public Matrix4f rotateAffineXYZ(float angleX, float angleY, float angleZ, Matrix4f dest) {
        float cosX = (float) Math.cos(angleX);
        float sinX = (float) Math.sin(angleX);
        float cosY = (float) Math.cos(angleY);
        float sinY = (float) Math.sin(angleY);
        float cosZ = (float) Math.cos(angleZ);
        float sinZ = (float) Math.sin(angleZ);
        float m_sinX = -sinX;
        float m_sinY = -sinY;
        float m_sinZ = -sinZ;

        // rotateX
        float nn10 = ms[M10] * cosX + ms[M20] * sinX;
        float nn11 = ms[M11] * cosX + ms[M21] * sinX;
        float nn12 = ms[M12] * cosX + ms[M22] * sinX;
        float nn20 = ms[M10] * m_sinX + ms[M20] * cosX;
        float nn21 = ms[M11] * m_sinX + ms[M21] * cosX;
        float nn22 = ms[M12] * m_sinX + ms[M22] * cosX;
        // rotateY
        float nn00 = ms[M00] * cosY + nn20 * m_sinY;
        float nn01 = ms[M01] * cosY + nn21 * m_sinY;
        float nn02 = ms[M02] * cosY + nn22 * m_sinY;
        dest.ms[M20] = ms[M00] * sinY + nn20 * cosY;
        dest.ms[M21] = ms[M01] * sinY + nn21 * cosY;
        dest.ms[M22] = ms[M02] * sinY + nn22 * cosY;
        dest.ms[M23] = 0.0f;
        // rotateZ
        dest.ms[M00] = nn00 * cosZ + nn10 * sinZ;
        dest.ms[M01] = nn01 * cosZ + nn11 * sinZ;
        dest.ms[M02] = nn02 * cosZ + nn12 * sinZ;
        dest.ms[M03] = 0.0f;
        dest.ms[M10] = nn00 * m_sinZ + nn10 * cosZ;
        dest.ms[M11] = nn01 * m_sinZ + nn11 * cosZ;
        dest.ms[M12] = nn02 * m_sinZ + nn12 * cosZ;
        dest.ms[M13] = 0.0f;
        // copy last column from 'this'
        dest.ms[M30] = ms[M30];
        dest.ms[M31] = ms[M31];
        dest.ms[M32] = ms[M32];
        dest.ms[M33] = ms[M33];
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
    public Matrix4f rotateZYX(float angleZ, float angleY, float angleX) {
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
    public Matrix4f rotateZYX(float angleZ, float angleY, float angleX, Matrix4f dest) {
        float cosZ = (float) Math.cos(angleZ);
        float sinZ = (float) Math.sin(angleZ);
        float cosY = (float) Math.cos(angleY);
        float sinY = (float) Math.sin(angleY);
        float cosX = (float) Math.cos(angleX);
        float sinX = (float) Math.sin(angleX);
        float m_sinZ = -sinZ;
        float m_sinY = -sinY;
        float m_sinX = -sinX;

        // rotateZ
        float nn00 = ms[M00] * cosZ + ms[M10] * sinZ;
        float nn01 = ms[M01] * cosZ + ms[M11] * sinZ;
        float nn02 = ms[M02] * cosZ + ms[M12] * sinZ;
        float nn03 = ms[M03] * cosZ + ms[M13] * sinZ;
        float nn10 = ms[M00] * m_sinZ + ms[M10] * cosZ;
        float nn11 = ms[M01] * m_sinZ + ms[M11] * cosZ;
        float nn12 = ms[M02] * m_sinZ + ms[M12] * cosZ;
        float nn13 = ms[M03] * m_sinZ + ms[M13] * cosZ;
        // rotateY
        float nn20 = nn00 * sinY + ms[M20] * cosY;
        float nn21 = nn01 * sinY + ms[M21] * cosY;
        float nn22 = nn02 * sinY + ms[M22] * cosY;
        float nn23 = nn03 * sinY + ms[M23] * cosY;
        dest.ms[M00] = nn00 * cosY + ms[M20] * m_sinY;
        dest.ms[M01] = nn01 * cosY + ms[M21] * m_sinY;
        dest.ms[M02] = nn02 * cosY + ms[M22] * m_sinY;
        dest.ms[M03] = nn03 * cosY + ms[M23] * m_sinY;
        // rotateX
        dest.ms[M10] = nn10 * cosX + nn20 * sinX;
        dest.ms[M11] = nn11 * cosX + nn21 * sinX;
        dest.ms[M12] = nn12 * cosX + nn22 * sinX;
        dest.ms[M13] = nn13 * cosX + nn23 * sinX;
        dest.ms[M20] = nn10 * m_sinX + nn20 * cosX;
        dest.ms[M21] = nn11 * m_sinX + nn21 * cosX;
        dest.ms[M22] = nn12 * m_sinX + nn22 * cosX;
        dest.ms[M23] = nn13 * m_sinX + nn23 * cosX;
        // copy last column from 'this'
        dest.ms[M30] = ms[M30];
        dest.ms[M31] = ms[M31];
        dest.ms[M32] = ms[M32];
        dest.ms[M33] = ms[M33];
        return dest;
    }

    /**
     * Apply rotation of <code>angleZ</code> radians about the Z axis, followed by a rotation of <code>angleY</code> radians about the Y axis and
     * followed by a rotation of <code>angleX</code> radians about the X axis.
     * <p>
     * This method assumes that <code>this</code> matrix represents an {@link #isAffine() affine} transformation (i.e. its last row is equal to <tt>(0, 0, 0, 1)</tt>)
     * and can be used to speed up matrix multiplication if the matrix only represents affine transformations, such as translation, rotation, scaling and shearing (in any combination).
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
    public Matrix4f rotateAffineZYX(float angleZ, float angleY, float angleX) {
        return rotateAffineZYX(angleZ, angleY, angleX, this);
    }

    /**
     * Apply rotation of <code>angleZ</code> radians about the Z axis, followed by a rotation of <code>angleY</code> radians about the Y axis and
     * followed by a rotation of <code>angleX</code> radians about the X axis and store the result in <code>dest</code>.
     * <p>
     * This method assumes that <code>this</code> matrix represents an {@link #isAffine() affine} transformation (i.e. its last row is equal to <tt>(0, 0, 0, 1)</tt>)
     * and can be used to speed up matrix multiplication if the matrix only represents affine transformations, such as translation, rotation, scaling and shearing (in any combination).
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
    public Matrix4f rotateAffineZYX(float angleZ, float angleY, float angleX, Matrix4f dest) {
        float cosZ = (float) Math.cos(angleZ);
        float sinZ = (float) Math.sin(angleZ);
        float cosY = (float) Math.cos(angleY);
        float sinY = (float) Math.sin(angleY);
        float cosX = (float) Math.cos(angleX);
        float sinX = (float) Math.sin(angleX);
        float m_sinZ = -sinZ;
        float m_sinY = -sinY;
        float m_sinX = -sinX;

        // rotateZ
        float nn00 = ms[M00] * cosZ + ms[M10] * sinZ;
        float nn01 = ms[M01] * cosZ + ms[M11] * sinZ;
        float nn02 = ms[M02] * cosZ + ms[M12] * sinZ;
        float nn10 = ms[M00] * m_sinZ + ms[M10] * cosZ;
        float nn11 = ms[M01] * m_sinZ + ms[M11] * cosZ;
        float nn12 = ms[M02] * m_sinZ + ms[M12] * cosZ;
        // rotateY
        float nn20 = nn00 * sinY + ms[M20] * cosY;
        float nn21 = nn01 * sinY + ms[M21] * cosY;
        float nn22 = nn02 * sinY + ms[M22] * cosY;
        dest.ms[M00] = nn00 * cosY + ms[M20] * m_sinY;
        dest.ms[M01] = nn01 * cosY + ms[M21] * m_sinY;
        dest.ms[M02] = nn02 * cosY + ms[M22] * m_sinY;
        dest.ms[M03] = 0.0f;
        // rotateX
        dest.ms[M10] = nn10 * cosX + nn20 * sinX;
        dest.ms[M11] = nn11 * cosX + nn21 * sinX;
        dest.ms[M12] = nn12 * cosX + nn22 * sinX;
        dest.ms[M13] = 0.0f;
        dest.ms[M20] = nn10 * m_sinX + nn20 * cosX;
        dest.ms[M21] = nn11 * m_sinX + nn21 * cosX;
        dest.ms[M22] = nn12 * m_sinX + nn22 * cosX;
        dest.ms[M23] = 0.0f;
        // copy last column from 'this'
        dest.ms[M30] = ms[M30];
        dest.ms[M31] = ms[M31];
        dest.ms[M32] = ms[M32];
        dest.ms[M33] = ms[M33];
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
    public Matrix4f rotateYXZ(float angleY, float angleX, float angleZ) {
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
    public Matrix4f rotateYXZ(float angleY, float angleX, float angleZ, Matrix4f dest) {
        float cosY = (float) Math.cos(angleY);
        float sinY = (float) Math.sin(angleY);
        float cosX = (float) Math.cos(angleX);
        float sinX = (float) Math.sin(angleX);
        float cosZ = (float) Math.cos(angleZ);
        float sinZ = (float) Math.sin(angleZ);
        float m_sinY = -sinY;
        float m_sinX = -sinX;
        float m_sinZ = -sinZ;

        // rotateY
        float nn20 = ms[M00] * sinY + ms[M20] * cosY;
        float nn21 = ms[M01] * sinY + ms[M21] * cosY;
        float nn22 = ms[M02] * sinY + ms[M22] * cosY;
        float nn23 = ms[M03] * sinY + ms[M23] * cosY;
        float nn00 = ms[M00] * cosY + ms[M20] * m_sinY;
        float nn01 = ms[M01] * cosY + ms[M21] * m_sinY;
        float nn02 = ms[M02] * cosY + ms[M22] * m_sinY;
        float nn03 = ms[M03] * cosY + ms[M23] * m_sinY;
        // rotateX
        float nn10 = ms[M10] * cosX + nn20 * sinX;
        float nn11 = ms[M11] * cosX + nn21 * sinX;
        float nn12 = ms[M12] * cosX + nn22 * sinX;
        float nn13 = ms[M13] * cosX + nn23 * sinX;
        dest.ms[M20] = ms[M10] * m_sinX + nn20 * cosX;
        dest.ms[M21] = ms[M11] * m_sinX + nn21 * cosX;
        dest.ms[M22] = ms[M12] * m_sinX + nn22 * cosX;
        dest.ms[M23] = ms[M13] * m_sinX + nn23 * cosX;
        // rotateZ
        dest.ms[M00] = nn00 * cosZ + nn10 * sinZ;
        dest.ms[M01] = nn01 * cosZ + nn11 * sinZ;
        dest.ms[M02] = nn02 * cosZ + nn12 * sinZ;
        dest.ms[M03] = nn03 * cosZ + nn13 * sinZ;
        dest.ms[M10] = nn00 * m_sinZ + nn10 * cosZ;
        dest.ms[M11] = nn01 * m_sinZ + nn11 * cosZ;
        dest.ms[M12] = nn02 * m_sinZ + nn12 * cosZ;
        dest.ms[M13] = nn03 * m_sinZ + nn13 * cosZ;
        // copy last column from 'this'
        dest.ms[M30] = ms[M30];
        dest.ms[M31] = ms[M31];
        dest.ms[M32] = ms[M32];
        dest.ms[M33] = ms[M33];
        return dest;
    }

    /**
     * Apply rotation of <code>angleY</code> radians about the Y axis, followed by a rotation of <code>angleX</code> radians about the X axis and
     * followed by a rotation of <code>angleZ</code> radians about the Z axis.
     * <p>
     * This method assumes that <code>this</code> matrix represents an {@link #isAffine() affine} transformation (i.e. its last row is equal to <tt>(0, 0, 0, 1)</tt>)
     * and can be used to speed up matrix multiplication if the matrix only represents affine transformations, such as translation, rotation, scaling and shearing (in any combination).
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
    public Matrix4f rotateAffineYXZ(float angleY, float angleX, float angleZ) {
        return rotateAffineYXZ(angleY, angleX, angleZ, this);
    }

    /**
     * Apply rotation of <code>angleY</code> radians about the Y axis, followed by a rotation of <code>angleX</code> radians about the X axis and
     * followed by a rotation of <code>angleZ</code> radians about the Z axis and store the result in <code>dest</code>.
     * <p>
     * This method assumes that <code>this</code> matrix represents an {@link #isAffine() affine} transformation (i.e. its last row is equal to <tt>(0, 0, 0, 1)</tt>)
     * and can be used to speed up matrix multiplication if the matrix only represents affine transformations, such as translation, rotation, scaling and shearing (in any combination).
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
    public Matrix4f rotateAffineYXZ(float angleY, float angleX, float angleZ, Matrix4f dest) {
        float cosY = (float) Math.cos(angleY);
        float sinY = (float) Math.sin(angleY);
        float cosX = (float) Math.cos(angleX);
        float sinX = (float) Math.sin(angleX);
        float cosZ = (float) Math.cos(angleZ);
        float sinZ = (float) Math.sin(angleZ);
        float m_sinY = -sinY;
        float m_sinX = -sinX;
        float m_sinZ = -sinZ;

        // rotateY
        float nn20 = ms[M00] * sinY + ms[M20] * cosY;
        float nn21 = ms[M01] * sinY + ms[M21] * cosY;
        float nn22 = ms[M02] * sinY + ms[M22] * cosY;
        float nn00 = ms[M00] * cosY + ms[M20] * m_sinY;
        float nn01 = ms[M01] * cosY + ms[M21] * m_sinY;
        float nn02 = ms[M02] * cosY + ms[M22] * m_sinY;
        // rotateX
        float nn10 = ms[M10] * cosX + nn20 * sinX;
        float nn11 = ms[M11] * cosX + nn21 * sinX;
        float nn12 = ms[M12] * cosX + nn22 * sinX;
        dest.ms[M20] = ms[M10] * m_sinX + nn20 * cosX;
        dest.ms[M21] = ms[M11] * m_sinX + nn21 * cosX;
        dest.ms[M22] = ms[M12] * m_sinX + nn22 * cosX;
        dest.ms[M23] = 0.0f;
        // rotateZ
        dest.ms[M00] = nn00 * cosZ + nn10 * sinZ;
        dest.ms[M01] = nn01 * cosZ + nn11 * sinZ;
        dest.ms[M02] = nn02 * cosZ + nn12 * sinZ;
        dest.ms[M03] = 0.0f;
        dest.ms[M10] = nn00 * m_sinZ + nn10 * cosZ;
        dest.ms[M11] = nn01 * m_sinZ + nn11 * cosZ;
        dest.ms[M12] = nn02 * m_sinZ + nn12 * cosZ;
        dest.ms[M13] = 0.0f;
        // copy last column from 'this'
        dest.ms[M30] = ms[M30];
        dest.ms[M31] = ms[M31];
        dest.ms[M32] = ms[M32];
        dest.ms[M33] = ms[M33];
        return dest;
    }

    /**
     * Apply rotation to this matrix by rotating the given amount of radians
     * about the specified <tt>(x, y, z)</tt> axis and store the result in <code>dest</code>.
     * <p>
     * The axis described by the three components needs to be a unit vector.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the rotation matrix,
     * then the new matrix will be <code>M * R</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * R * v</code>, the
     * rotation will be applied first!
     * <p>
     * In order to set the matrix to a rotation matrix without post-multiplying the rotation
     * transformation, use {@link #rotation(float, float, float, float) rotation()}.
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Rotation_matrix_from_axis_and_angle">http://en.wikipedia.org</a>
     * 
     * @see #rotation(float, float, float, float)
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
    public Matrix4f rotate(float ang, float x, float y, float z, Matrix4f dest) {
        float s = (float) Math.sin(ang);
        float c = (float) Math.cos(ang);
        float C = 1.0f - c;

        // rotation matrix elements:
        // ms[M30], ms[M31], ms[M32], ms[M03], ms[M13], ms[M23] = 0
        // ms[M33] = 1
        float xx = x * x, xy = x * y, xz = x * z;
        float yy = y * y, yz = y * z;
        float zz = z * z;
        float rn00 = xx * C + c;
        float rn01 = xy * C + z * s;
        float rn02 = xz * C - y * s;
        float rn10 = xy * C - z * s;
        float rn11 = yy * C + c;
        float rn12 = yz * C + x * s;
        float rn20 = xz * C + y * s;
        float rn21 = yz * C - x * s;
        float rn22 = zz * C + c;

        // add temporaries for dependent values
        float nn00 = ms[M00] * rn00 + ms[M10] * rn01 + ms[M20] * rn02;
        float nn01 = ms[M01] * rn00 + ms[M11] * rn01 + ms[M21] * rn02;
        float nn02 = ms[M02] * rn00 + ms[M12] * rn01 + ms[M22] * rn02;
        float nn03 = ms[M03] * rn00 + ms[M13] * rn01 + ms[M23] * rn02;
        float nn10 = ms[M00] * rn10 + ms[M10] * rn11 + ms[M20] * rn12;
        float nn11 = ms[M01] * rn10 + ms[M11] * rn11 + ms[M21] * rn12;
        float nn12 = ms[M02] * rn10 + ms[M12] * rn11 + ms[M22] * rn12;
        float nn13 = ms[M03] * rn10 + ms[M13] * rn11 + ms[M23] * rn12;
        // set non-dependent values directly
        dest.ms[M20] = ms[M00] * rn20 + ms[M10] * rn21 + ms[M20] * rn22;
        dest.ms[M21] = ms[M01] * rn20 + ms[M11] * rn21 + ms[M21] * rn22;
        dest.ms[M22] = ms[M02] * rn20 + ms[M12] * rn21 + ms[M22] * rn22;
        dest.ms[M23] = ms[M03] * rn20 + ms[M13] * rn21 + ms[M23] * rn22;
        // set other values
        dest.ms[M00] = nn00;
        dest.ms[M01] = nn01;
        dest.ms[M02] = nn02;
        dest.ms[M03] = nn03;
        dest.ms[M10] = nn10;
        dest.ms[M11] = nn11;
        dest.ms[M12] = nn12;
        dest.ms[M13] = nn13;
        dest.ms[M30] = ms[M30];
        dest.ms[M31] = ms[M31];
        dest.ms[M32] = ms[M32];
        dest.ms[M33] = ms[M33];

        return dest;
    }

    /**
     * Apply rotation to this matrix by rotating the given amount of radians
     * about the specified <tt>(x, y, z)</tt> axis.
     * <p>
     * The axis described by the three components needs to be a unit vector.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the rotation matrix,
     * then the new matrix will be <code>M * R</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * R * v</code>, the
     * rotation will be applied first!
     * <p>
     * In order to set the matrix to a rotation matrix without post-multiplying the rotation
     * transformation, use {@link #rotation(float, float, float, float) rotation()}.
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Rotation_matrix_from_axis_and_angle">http://en.wikipedia.org</a>
     * 
     * @see #rotation(float, float, float, float)
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
    public Matrix4f rotate(float ang, float x, float y, float z) {
        return rotate(ang, x, y, z, this);
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
     * @see #translation(Vector3f)
     * 
     * @param offset
     *          the number of units in x, y and z by which to translate
     * @return this
     */
    public Matrix4f translate(Vector3f offset) {
        return translate(offset.x, offset.y, offset.z);
    }

    /**
     * Apply a translation to this matrix by translating by the given number of
     * units in x, y and z and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>T</code> the translation
     * matrix, then the new matrix will be <code>M * T</code>. So when
     * transforming a vector <code>v</code> with the new matrix by using
     * <code>M * T * v</code>, the translation will be applied first!
     * <p>
     * In order to set the matrix to a translation transformation without post-multiplying
     * it, use {@link #translation(Vector3f)}.
     * 
     * @see #translation(Vector3f)
     * 
     * @param offset
     *          the number of units in x, y and z by which to translate
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Matrix4f translate(Vector3f offset, Matrix4f dest) {
        return translate(offset.x, offset.y, offset.z, dest);
    }

    /**
     * Apply a translation to this matrix by translating by the given number of
     * units in x, y and z and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>T</code> the translation
     * matrix, then the new matrix will be <code>M * T</code>. So when
     * transforming a vector <code>v</code> with the new matrix by using
     * <code>M * T * v</code>, the translation will be applied first!
     * <p>
     * In order to set the matrix to a translation transformation without post-multiplying
     * it, use {@link #translation(float, float, float)}.
     * 
     * @see #translation(float, float, float)
     * 
     * @param x
     *          the offset to translate in x
     * @param y
     *          the offset to translate in y
     * @param z
     *          the offset to translate in z
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Matrix4f translate(float x, float y, float z, Matrix4f dest) {
        // translation matrix elements:
        // ms[M00], ms[M11], ms[M22], ms[M33] = 1
        // ms[M30] = x, ms[M31] = y, ms[M32] = z
        // all others = 0
        dest.ms[M00] = ms[M00];
        dest.ms[M01] = ms[M01];
        dest.ms[M02] = ms[M02];
        dest.ms[M03] = ms[M03];
        dest.ms[M10] = ms[M10];
        dest.ms[M11] = ms[M11];
        dest.ms[M12] = ms[M12];
        dest.ms[M13] = ms[M13];
        dest.ms[M20] = ms[M20];
        dest.ms[M21] = ms[M21];
        dest.ms[M22] = ms[M22];
        dest.ms[M23] = ms[M23];
        dest.ms[M30] = ms[M00] * x + ms[M10] * y + ms[M20] * z + ms[M30];
        dest.ms[M31] = ms[M01] * x + ms[M11] * y + ms[M21] * z + ms[M31];
        dest.ms[M32] = ms[M02] * x + ms[M12] * y + ms[M22] * z + ms[M32];
        dest.ms[M33] = ms[M03] * x + ms[M13] * y + ms[M23] * z + ms[M33];
        return dest;
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
     * it, use {@link #translation(float, float, float)}.
     * 
     * @see #translation(float, float, float)
     * 
     * @param x
     *          the offset to translate in x
     * @param y
     *          the offset to translate in y
     * @param z
     *          the offset to translate in z
     * @return this
     */
    public Matrix4f translate(float x, float y, float z) {
        Matrix4f c = this;
        // translation matrix elements:
        // ms[M00], ms[M11], ms[M22], ms[M33] = 1
        // ms[M30] = x, ms[M31] = y, ms[M32] = z
        // all others = 0
        c.ms[M30] = c.ms[M00] * x + c.ms[M10] * y + c.ms[M20] * z + c.ms[M30];
        c.ms[M31] = c.ms[M01] * x + c.ms[M11] * y + c.ms[M21] * z + c.ms[M31];
        c.ms[M32] = c.ms[M02] * x + c.ms[M12] * y + c.ms[M22] * z + c.ms[M32];
        c.ms[M33] = c.ms[M03] * x + c.ms[M13] * y + c.ms[M23] * z + c.ms[M33];
        return this;
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeFloat(ms[M00]);
        out.writeFloat(ms[M01]);
        out.writeFloat(ms[M02]);
        out.writeFloat(ms[M03]);
        out.writeFloat(ms[M10]);
        out.writeFloat(ms[M11]);
        out.writeFloat(ms[M12]);
        out.writeFloat(ms[M13]);
        out.writeFloat(ms[M20]);
        out.writeFloat(ms[M21]);
        out.writeFloat(ms[M22]);
        out.writeFloat(ms[M23]);
        out.writeFloat(ms[M30]);
        out.writeFloat(ms[M31]);
        out.writeFloat(ms[M32]);
        out.writeFloat(ms[M33]);
    }

    public void readExternal(ObjectInput in) throws IOException,
            ClassNotFoundException {
        ms[M00] = in.readFloat();
        ms[M01] = in.readFloat();
        ms[M02] = in.readFloat();
        ms[M03] = in.readFloat();
        ms[M10] = in.readFloat();
        ms[M11] = in.readFloat();
        ms[M12] = in.readFloat();
        ms[M13] = in.readFloat();
        ms[M20] = in.readFloat();
        ms[M21] = in.readFloat();
        ms[M22] = in.readFloat();
        ms[M23] = in.readFloat();
        ms[M30] = in.readFloat();
        ms[M31] = in.readFloat();
        ms[M32] = in.readFloat();
        ms[M33] = in.readFloat();
    }

    /**
     * Apply an orthographic projection transformation using the given NDC z range to this matrix and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>O</code> the orthographic projection matrix,
     * then the new matrix will be <code>M * O</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * O * v</code>, the
     * orthographic projection transformation will be applied first!
     * <p>
     * In order to set the matrix to an orthographic projection without post-multiplying it,
     * use {@link #setOrtho(float, float, float, float, float, float, boolean) setOrtho()}.
     * <p>
     * Reference: <a href="http://www.songho.ca/opengl/gl_projectionmatrix.html#ortho">http://www.songho.ca</a>
     * 
     * @see #setOrtho(float, float, float, float, float, float, boolean)
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
     * @param zZeroToOne
     *            whether to use Vulkan's and Direct3D's NDC z range of <tt>[0..+1]</tt> when <code>true</code>
     *            or whether to use OpenGL's NDC z range of <tt>[-1..+1]</tt> when <code>false</code>
     * @param dest
     *            will hold the result
     * @return dest
     */
    public Matrix4f ortho(float left, float right, float bottom, float top, float zNear, float zFar, boolean zZeroToOne, Matrix4f dest) {
        // calculate right matrix elements
        float rn00 = 2.0f / (right - left);
        float rn11 = 2.0f / (top - bottom);
        float rn22 = (zZeroToOne ? 1.0f : 2.0f) / (zNear - zFar);
        float rn30 = (left + right) / (left - right);
        float rn31 = (top + bottom) / (bottom - top);
        float rn32 = (zZeroToOne ? zNear : (zFar + zNear)) / (zNear - zFar);

        // perform optimized multiplication
        // compute the last column first, because other columns do not depend on it
        dest.ms[M30] = ms[M00] * rn30 + ms[M10] * rn31 + ms[M20] * rn32 + ms[M30];
        dest.ms[M31] = ms[M01] * rn30 + ms[M11] * rn31 + ms[M21] * rn32 + ms[M31];
        dest.ms[M32] = ms[M02] * rn30 + ms[M12] * rn31 + ms[M22] * rn32 + ms[M32];
        dest.ms[M33] = ms[M03] * rn30 + ms[M13] * rn31 + ms[M23] * rn32 + ms[M33];
        dest.ms[M00] = ms[M00] * rn00;
        dest.ms[M01] = ms[M01] * rn00;
        dest.ms[M02] = ms[M02] * rn00;
        dest.ms[M03] = ms[M03] * rn00;
        dest.ms[M10] = ms[M10] * rn11;
        dest.ms[M11] = ms[M11] * rn11;
        dest.ms[M12] = ms[M12] * rn11;
        dest.ms[M13] = ms[M13] * rn11;
        dest.ms[M20] = ms[M20] * rn22;
        dest.ms[M21] = ms[M21] * rn22;
        dest.ms[M22] = ms[M22] * rn22;
        dest.ms[M23] = ms[M23] * rn22;

        return dest;
    }

    /**
     * Apply an orthographic projection transformation using OpenGL's NDC z range of <tt>[-1..+1]</tt> to this matrix and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>O</code> the orthographic projection matrix,
     * then the new matrix will be <code>M * O</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * O * v</code>, the
     * orthographic projection transformation will be applied first!
     * <p>
     * In order to set the matrix to an orthographic projection without post-multiplying it,
     * use {@link #setOrtho(float, float, float, float, float, float) setOrtho()}.
     * <p>
     * Reference: <a href="http://www.songho.ca/opengl/gl_projectionmatrix.html#ortho">http://www.songho.ca</a>
     * 
     * @see #setOrtho(float, float, float, float, float, float)
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
    public Matrix4f ortho(float left, float right, float bottom, float top, float zNear, float zFar, Matrix4f dest) {
        return ortho(left, right, bottom, top, zNear, zFar, false, dest);
    }

    /**
     * Apply an orthographic projection transformation using the given NDC z range to this matrix.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>O</code> the orthographic projection matrix,
     * then the new matrix will be <code>M * O</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * O * v</code>, the
     * orthographic projection transformation will be applied first!
     * <p>
     * In order to set the matrix to an orthographic projection without post-multiplying it,
     * use {@link #setOrtho(float, float, float, float, float, float, boolean) setOrtho()}.
     * <p>
     * Reference: <a href="http://www.songho.ca/opengl/gl_projectionmatrix.html#ortho">http://www.songho.ca</a>
     * 
     * @see #setOrtho(float, float, float, float, float, float, boolean)
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
     * @param zZeroToOne
     *            whether to use Vulkan's and Direct3D's NDC z range of <tt>[0..+1]</tt> when <code>true</code>
     *            or whether to use OpenGL's NDC z range of <tt>[-1..+1]</tt> when <code>false</code>
     * @return this
     */
    public Matrix4f ortho(float left, float right, float bottom, float top, float zNear, float zFar, boolean zZeroToOne) {
        return ortho(left, right, bottom, top, zNear, zFar, zZeroToOne, this);
    }

    /**
     * Apply an orthographic projection transformation using OpenGL's NDC z range of <tt>[-1..+1]</tt> to this matrix.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>O</code> the orthographic projection matrix,
     * then the new matrix will be <code>M * O</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * O * v</code>, the
     * orthographic projection transformation will be applied first!
     * <p>
     * In order to set the matrix to an orthographic projection without post-multiplying it,
     * use {@link #setOrtho(float, float, float, float, float, float) setOrtho()}.
     * <p>
     * Reference: <a href="http://www.songho.ca/opengl/gl_projectionmatrix.html#ortho">http://www.songho.ca</a>
     * 
     * @see #setOrtho(float, float, float, float, float, float)
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
    public Matrix4f ortho(float left, float right, float bottom, float top, float zNear, float zFar) {
        return ortho(left, right, bottom, top, zNear, zFar, false);
    }

    /**
     * Set this matrix to be an orthographic projection transformation using the given NDC z range.
     * <p>
     * In order to apply the orthographic projection to an already existing transformation,
     * use {@link #ortho(float, float, float, float, float, float, boolean) ortho()}.
     * <p>
     * Reference: <a href="http://www.songho.ca/opengl/gl_projectionmatrix.html#ortho">http://www.songho.ca</a>
     * 
     * @see #ortho(float, float, float, float, float, float, boolean)
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
     * @param zZeroToOne
     *            whether to use Vulkan's and Direct3D's NDC z range of <tt>[0..+1]</tt> when <code>true</code>
     *            or whether to use OpenGL's NDC z range of <tt>[-1..+1]</tt> when <code>false</code>
     * @return this
     */
    public Matrix4f setOrtho(float left, float right, float bottom, float top, float zNear, float zFar, boolean zZeroToOne) {
        ms[M00] = 2.0f / (right - left);
        ms[M01] = 0.0f;
        ms[M02] = 0.0f;
        ms[M03] = 0.0f;
        ms[M10] = 0.0f;
        ms[M11] = 2.0f / (top - bottom);
        ms[M12] = 0.0f;
        ms[M13] = 0.0f;
        ms[M20] = 0.0f;
        ms[M21] = 0.0f;
        ms[M22] = (zZeroToOne ? 1.0f : 2.0f) / (zNear - zFar);
        ms[M23] = 0.0f;
        ms[M30] = (right + left) / (left - right);
        ms[M31] = (top + bottom) / (bottom - top);
        ms[M32] = (zZeroToOne ? zNear : (zFar + zNear)) / (zNear - zFar);
        ms[M33] = 1.0f;
        return this;
    }

    /**
     * Set this matrix to be an orthographic projection transformation using OpenGL's NDC z range of <tt>[-1..+1]</tt>.
     * <p>
     * In order to apply the orthographic projection to an already existing transformation,
     * use {@link #ortho(float, float, float, float, float, float) ortho()}.
     * <p>
     * Reference: <a href="http://www.songho.ca/opengl/gl_projectionmatrix.html#ortho">http://www.songho.ca</a>
     * 
     * @see #ortho(float, float, float, float, float, float)
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
    public Matrix4f setOrtho(float left, float right, float bottom, float top, float zNear, float zFar) {
        return setOrtho(left, right, bottom, top, zNear, zFar, false);
    }

    /**
     * Apply a symmetric orthographic projection transformation using the given NDC z range to this matrix and store the result in <code>dest</code>.
     * <p>
     * This method is equivalent to calling {@link #ortho(float, float, float, float, float, float, boolean, Matrix4f) ortho()} with
     * <code>left=-width/2</code>, <code>right=+width/2</code>, <code>bottom=-height/2</code> and <code>top=+height/2</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>O</code> the orthographic projection matrix,
     * then the new matrix will be <code>M * O</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * O * v</code>, the
     * orthographic projection transformation will be applied first!
     * <p>
     * In order to set the matrix to a symmetric orthographic projection without post-multiplying it,
     * use {@link #setOrthoSymmetric(float, float, float, float, boolean) setOrthoSymmetric()}.
     * <p>
     * Reference: <a href="http://www.songho.ca/opengl/gl_projectionmatrix.html#ortho">http://www.songho.ca</a>
     * 
     * @see #setOrthoSymmetric(float, float, float, float, boolean)
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
     * @param zZeroToOne
     *            whether to use Vulkan's and Direct3D's NDC z range of <tt>[0..+1]</tt> when <code>true</code>
     *            or whether to use OpenGL's NDC z range of <tt>[-1..+1]</tt> when <code>false</code>
     * @return dest
     */
    public Matrix4f orthoSymmetric(float width, float height, float zNear, float zFar, boolean zZeroToOne, Matrix4f dest) {
        // calculate right matrix elements
        float rn00 = 2.0f / width;
        float rn11 = 2.0f / height;
        float rn22 = (zZeroToOne ? 1.0f : 2.0f) / (zNear - zFar);
        float rn32 = (zZeroToOne ? zNear : (zFar + zNear)) / (zNear - zFar);

        // perform optimized multiplication
        // compute the last column first, because other columns do not depend on it
        dest.ms[M30] = ms[M20] * rn32 + ms[M30];
        dest.ms[M31] = ms[M21] * rn32 + ms[M31];
        dest.ms[M32] = ms[M22] * rn32 + ms[M32];
        dest.ms[M33] = ms[M23] * rn32 + ms[M33];
        dest.ms[M00] = ms[M00] * rn00;
        dest.ms[M01] = ms[M01] * rn00;
        dest.ms[M02] = ms[M02] * rn00;
        dest.ms[M03] = ms[M03] * rn00;
        dest.ms[M10] = ms[M10] * rn11;
        dest.ms[M11] = ms[M11] * rn11;
        dest.ms[M12] = ms[M12] * rn11;
        dest.ms[M13] = ms[M13] * rn11;
        dest.ms[M20] = ms[M20] * rn22;
        dest.ms[M21] = ms[M21] * rn22;
        dest.ms[M22] = ms[M22] * rn22;
        dest.ms[M23] = ms[M23] * rn22;

        return dest;
    }

    /**
     * Apply a symmetric orthographic projection transformation using OpenGL's NDC z range of <tt>[-1..+1]</tt> to this matrix and store the result in <code>dest</code>.
     * <p>
     * This method is equivalent to calling {@link #ortho(float, float, float, float, float, float, Matrix4f) ortho()} with
     * <code>left=-width/2</code>, <code>right=+width/2</code>, <code>bottom=-height/2</code> and <code>top=+height/2</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>O</code> the orthographic projection matrix,
     * then the new matrix will be <code>M * O</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * O * v</code>, the
     * orthographic projection transformation will be applied first!
     * <p>
     * In order to set the matrix to a symmetric orthographic projection without post-multiplying it,
     * use {@link #setOrthoSymmetric(float, float, float, float) setOrthoSymmetric()}.
     * <p>
     * Reference: <a href="http://www.songho.ca/opengl/gl_projectionmatrix.html#ortho">http://www.songho.ca</a>
     * 
     * @see #setOrthoSymmetric(float, float, float, float)
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
    public Matrix4f orthoSymmetric(float width, float height, float zNear, float zFar, Matrix4f dest) {
        return orthoSymmetric(width, height, zNear, zFar, false, dest);
    }

    /**
     * Apply a symmetric orthographic projection transformation using the given NDC z range to this matrix.
     * <p>
     * This method is equivalent to calling {@link #ortho(float, float, float, float, float, float, boolean) ortho()} with
     * <code>left=-width/2</code>, <code>right=+width/2</code>, <code>bottom=-height/2</code> and <code>top=+height/2</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>O</code> the orthographic projection matrix,
     * then the new matrix will be <code>M * O</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * O * v</code>, the
     * orthographic projection transformation will be applied first!
     * <p>
     * In order to set the matrix to a symmetric orthographic projection without post-multiplying it,
     * use {@link #setOrthoSymmetric(float, float, float, float, boolean) setOrthoSymmetric()}.
     * <p>
     * Reference: <a href="http://www.songho.ca/opengl/gl_projectionmatrix.html#ortho">http://www.songho.ca</a>
     * 
     * @see #setOrthoSymmetric(float, float, float, float, boolean)
     * 
     * @param width
     *            the distance between the right and left frustum edges
     * @param height
     *            the distance between the top and bottom frustum edges
     * @param zNear
     *            near clipping plane distance
     * @param zFar
     *            far clipping plane distance
     * @param zZeroToOne
     *            whether to use Vulkan's and Direct3D's NDC z range of <tt>[0..+1]</tt> when <code>true</code>
     *            or whether to use OpenGL's NDC z range of <tt>[-1..+1]</tt> when <code>false</code>
     * @return this
     */
    public Matrix4f orthoSymmetric(float width, float height, float zNear, float zFar, boolean zZeroToOne) {
        return orthoSymmetric(width, height, zNear, zFar, zZeroToOne, this);
    }

    /**
     * Apply a symmetric orthographic projection transformation using OpenGL's NDC z range of <tt>[-1..+1]</tt> to this matrix.
     * <p>
     * This method is equivalent to calling {@link #ortho(float, float, float, float, float, float) ortho()} with
     * <code>left=-width/2</code>, <code>right=+width/2</code>, <code>bottom=-height/2</code> and <code>top=+height/2</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>O</code> the orthographic projection matrix,
     * then the new matrix will be <code>M * O</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * O * v</code>, the
     * orthographic projection transformation will be applied first!
     * <p>
     * In order to set the matrix to a symmetric orthographic projection without post-multiplying it,
     * use {@link #setOrthoSymmetric(float, float, float, float) setOrthoSymmetric()}.
     * <p>
     * Reference: <a href="http://www.songho.ca/opengl/gl_projectionmatrix.html#ortho">http://www.songho.ca</a>
     * 
     * @see #setOrthoSymmetric(float, float, float, float)
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
    public Matrix4f orthoSymmetric(float width, float height, float zNear, float zFar) {
        return orthoSymmetric(width, height, zNear, zFar, false, this);
    }

    /**
     * Set this matrix to be a symmetric orthographic projection transformation using the given NDC z range.
     * <p>
     * This method is equivalent to calling {@link #setOrtho(float, float, float, float, float, float, boolean) setOrtho()} with
     * <code>left=-width/2</code>, <code>right=+width/2</code>, <code>bottom=-height/2</code> and <code>top=+height/2</code>.
     * <p>
     * In order to apply the symmetric orthographic projection to an already existing transformation,
     * use {@link #orthoSymmetric(float, float, float, float, boolean) orthoSymmetric()}.
     * <p>
     * Reference: <a href="http://www.songho.ca/opengl/gl_projectionmatrix.html#ortho">http://www.songho.ca</a>
     * 
     * @see #orthoSymmetric(float, float, float, float, boolean)
     * 
     * @param width
     *            the distance between the right and left frustum edges
     * @param height
     *            the distance between the top and bottom frustum edges
     * @param zNear
     *            near clipping plane distance
     * @param zFar
     *            far clipping plane distance
     * @param zZeroToOne
     *            whether to use Vulkan's and Direct3D's NDC z range of <tt>[0..+1]</tt> when <code>true</code>
     *            or whether to use OpenGL's NDC z range of <tt>[-1..+1]</tt> when <code>false</code>
     * @return this
     */
    public Matrix4f setOrthoSymmetric(float width, float height, float zNear, float zFar, boolean zZeroToOne) {
        ms[M00] = 2.0f / width;
        ms[M01] = 0.0f;
        ms[M02] = 0.0f;
        ms[M03] = 0.0f;
        ms[M10] = 0.0f;
        ms[M11] = 2.0f / height;
        ms[M12] = 0.0f;
        ms[M13] = 0.0f;
        ms[M20] = 0.0f;
        ms[M21] = 0.0f;
        ms[M22] = (zZeroToOne ? 1.0f : 2.0f) / (zNear - zFar);
        ms[M23] = 0.0f;
        ms[M30] = 0.0f;
        ms[M31] = 0.0f;
        ms[M32] = (zZeroToOne ? zNear : (zFar + zNear)) / (zNear - zFar);
        ms[M33] = 1.0f;
        return this;
    }

    /**
     * Set this matrix to be a symmetric orthographic projection transformation using OpenGL's NDC z range of <tt>[-1..+1]</tt>.
     * <p>
     * This method is equivalent to calling {@link #setOrtho(float, float, float, float, float, float) setOrtho()} with
     * <code>left=-width/2</code>, <code>right=+width/2</code>, <code>bottom=-height/2</code> and <code>top=+height/2</code>.
     * <p>
     * In order to apply the symmetric orthographic projection to an already existing transformation,
     * use {@link #orthoSymmetric(float, float, float, float) orthoSymmetric()}.
     * <p>
     * Reference: <a href="http://www.songho.ca/opengl/gl_projectionmatrix.html#ortho">http://www.songho.ca</a>
     * 
     * @see #orthoSymmetric(float, float, float, float)
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
    public Matrix4f setOrthoSymmetric(float width, float height, float zNear, float zFar) {
        return setOrthoSymmetric(width, height, zNear, zFar, false);
    }

    /**
     * Apply an orthographic projection transformation to this matrix and store the result in <code>dest</code>.
     * <p>
     * This method is equivalent to calling {@link #ortho(float, float, float, float, float, float, Matrix4f) ortho()} with
     * <code>zNear=-1</code> and <code>zFar=+1</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>O</code> the orthographic projection matrix,
     * then the new matrix will be <code>M * O</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * O * v</code>, the
     * orthographic projection transformation will be applied first!
     * <p>
     * In order to set the matrix to an orthographic projection without post-multiplying it,
     * use {@link #setOrtho2D(float, float, float, float) setOrtho()}.
     * <p>
     * Reference: <a href="http://www.songho.ca/opengl/gl_projectionmatrix.html#ortho">http://www.songho.ca</a>
     * 
     * @see #ortho(float, float, float, float, float, float, Matrix4f)
     * @see #setOrtho2D(float, float, float, float)
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
    public Matrix4f ortho2D(float left, float right, float bottom, float top, Matrix4f dest) {
        // calculate right matrix elements
        float rn00 = 2.0f / (right - left);
        float rn11 = 2.0f / (top - bottom);
        float rn30 = -(right + left) / (right - left);
        float rn31 = -(top + bottom) / (top - bottom);

        // perform optimized multiplication
        // compute the last column first, because other columns do not depend on it
        dest.ms[M30] = ms[M00] * rn30 + ms[M10] * rn31 + ms[M30];
        dest.ms[M31] = ms[M01] * rn30 + ms[M11] * rn31 + ms[M31];
        dest.ms[M32] = ms[M02] * rn30 + ms[M12] * rn31 + ms[M32];
        dest.ms[M33] = ms[M03] * rn30 + ms[M13] * rn31 + ms[M33];
        dest.ms[M00] = ms[M00] * rn00;
        dest.ms[M01] = ms[M01] * rn00;
        dest.ms[M02] = ms[M02] * rn00;
        dest.ms[M03] = ms[M03] * rn00;
        dest.ms[M10] = ms[M10] * rn11;
        dest.ms[M11] = ms[M11] * rn11;
        dest.ms[M12] = ms[M12] * rn11;
        dest.ms[M13] = ms[M13] * rn11;
        dest.ms[M20] = -ms[M20];
        dest.ms[M21] = -ms[M21];
        dest.ms[M22] = -ms[M22];
        dest.ms[M23] = -ms[M23];

        return dest;
    }

    /**
     * Apply an orthographic projection transformation to this matrix.
     * <p>
     * This method is equivalent to calling {@link #ortho(float, float, float, float, float, float) ortho()} with
     * <code>zNear=-1</code> and <code>zFar=+1</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>O</code> the orthographic projection matrix,
     * then the new matrix will be <code>M * O</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * O * v</code>, the
     * orthographic projection transformation will be applied first!
     * <p>
     * In order to set the matrix to an orthographic projection without post-multiplying it,
     * use {@link #setOrtho2D(float, float, float, float) setOrtho2D()}.
     * <p>
     * Reference: <a href="http://www.songho.ca/opengl/gl_projectionmatrix.html#ortho">http://www.songho.ca</a>
     * 
     * @see #ortho(float, float, float, float, float, float)
     * @see #setOrtho2D(float, float, float, float)
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
    public Matrix4f ortho2D(float left, float right, float bottom, float top) {
        return ortho2D(left, right, bottom, top, this);
    }

    /**
     * Set this matrix to be an orthographic projection transformation.
     * <p>
     * This method is equivalent to calling {@link #setOrtho(float, float, float, float, float, float) setOrtho()} with
     * <code>zNear=-1</code> and <code>zFar=+1</code>.
     * <p>
     * In order to apply the orthographic projection to an already existing transformation,
     * use {@link #ortho2D(float, float, float, float) ortho2D()}.
     * <p>
     * Reference: <a href="http://www.songho.ca/opengl/gl_projectionmatrix.html#ortho">http://www.songho.ca</a>
     * 
     * @see #setOrtho(float, float, float, float, float, float)
     * @see #ortho2D(float, float, float, float)
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
    public Matrix4f setOrtho2D(float left, float right, float bottom, float top) {
        ms[M00] = 2.0f / (right - left);
        ms[M01] = 0.0f;
        ms[M02] = 0.0f;
        ms[M03] = 0.0f;
        ms[M10] = 0.0f;
        ms[M11] = 2.0f / (top - bottom);
        ms[M12] = 0.0f;
        ms[M13] = 0.0f;
        ms[M20] = 0.0f;
        ms[M21] = 0.0f;
        ms[M22] = -1.0f;
        ms[M23] = 0.0f;
        ms[M30] = -(right + left) / (right - left);
        ms[M31] = -(top + bottom) / (top - bottom);
        ms[M32] = 0.0f;
        ms[M33] = 1.0f;
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
     * {@link #lookAt(Vector3f, Vector3f, Vector3f) lookAt}
     * with <code>eye = (0, 0, 0)</code> and <code>center = dir</code>.
     * <p>
     * In order to set the matrix to a lookalong transformation without post-multiplying it,
     * use {@link #setLookAlong(Vector3f, Vector3f) setLookAlong()}.
     * 
     * @see #lookAlong(float, float, float, float, float, float)
     * @see #lookAt(Vector3f, Vector3f, Vector3f)
     * @see #setLookAlong(Vector3f, Vector3f)
     * 
     * @param dir
     *            the direction in space to look along
     * @param up
     *            the direction of 'up'
     * @return this
     */
    public Matrix4f lookAlong(Vector3f dir, Vector3f up) {
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
     * {@link #lookAt(Vector3f, Vector3f, Vector3f) lookAt}
     * with <code>eye = (0, 0, 0)</code> and <code>center = dir</code>.
     * <p>
     * In order to set the matrix to a lookalong transformation without post-multiplying it,
     * use {@link #setLookAlong(Vector3f, Vector3f) setLookAlong()}.
     * 
     * @see #lookAlong(float, float, float, float, float, float)
     * @see #lookAt(Vector3f, Vector3f, Vector3f)
     * @see #setLookAlong(Vector3f, Vector3f)
     * 
     * @param dir
     *            the direction in space to look along
     * @param up
     *            the direction of 'up'
     * @param dest
     *            will hold the result
     * @return dest
     */
    public Matrix4f lookAlong(Vector3f dir, Vector3f up, Matrix4f dest) {
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
     * {@link #lookAt(float, float, float, float, float, float, float, float, float) lookAt()}
     * with <code>eye = (0, 0, 0)</code> and <code>center = dir</code>.
     * <p>
     * In order to set the matrix to a lookalong transformation without post-multiplying it,
     * use {@link #setLookAlong(float, float, float, float, float, float) setLookAlong()}
     * 
     * @see #lookAt(float, float, float, float, float, float, float, float, float)
     * @see #setLookAlong(float, float, float, float, float, float)
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
    public Matrix4f lookAlong(float dirX, float dirY, float dirZ,
                              float upX, float upY, float upZ, Matrix4f dest) {
        // Normalize direction
        float invDirLength = 1.0f / (float) Math.sqrt(dirX * dirX + dirY * dirY + dirZ * dirZ);
        float dirnX = dirX * invDirLength;
        float dirnY = dirY * invDirLength;
        float dirnZ = dirZ * invDirLength;
        // right = direction x up
        float rightX, rightY, rightZ;
        rightX = dirnY * upZ - dirnZ * upY;
        rightY = dirnZ * upX - dirnX * upZ;
        rightZ = dirnX * upY - dirnY * upX;
        // normalize right
        float invRightLength = 1.0f / (float) Math.sqrt(rightX * rightX + rightY * rightY + rightZ * rightZ);
        rightX *= invRightLength;
        rightY *= invRightLength;
        rightZ *= invRightLength;
        // up = right x direction
        float upnX = rightY * dirnZ - rightZ * dirnY;
        float upnY = rightZ * dirnX - rightX * dirnZ;
        float upnZ = rightX * dirnY - rightY * dirnX;

        // calculate right matrix elements
        float rn00 = rightX;
        float rn01 = upnX;
        float rn02 = -dirnX;
        float rn10 = rightY;
        float rn11 = upnY;
        float rn12 = -dirnY;
        float rn20 = rightZ;
        float rn21 = upnZ;
        float rn22 = -dirnZ;

        // perform optimized matrix multiplication
        // introduce temporaries for dependent results
        float nn00 = ms[M00] * rn00 + ms[M10] * rn01 + ms[M20] * rn02;
        float nn01 = ms[M01] * rn00 + ms[M11] * rn01 + ms[M21] * rn02;
        float nn02 = ms[M02] * rn00 + ms[M12] * rn01 + ms[M22] * rn02;
        float nn03 = ms[M03] * rn00 + ms[M13] * rn01 + ms[M23] * rn02;
        float nn10 = ms[M00] * rn10 + ms[M10] * rn11 + ms[M20] * rn12;
        float nn11 = ms[M01] * rn10 + ms[M11] * rn11 + ms[M21] * rn12;
        float nn12 = ms[M02] * rn10 + ms[M12] * rn11 + ms[M22] * rn12;
        float nn13 = ms[M03] * rn10 + ms[M13] * rn11 + ms[M23] * rn12;
        dest.ms[M20] = ms[M00] * rn20 + ms[M10] * rn21 + ms[M20] * rn22;
        dest.ms[M21] = ms[M01] * rn20 + ms[M11] * rn21 + ms[M21] * rn22;
        dest.ms[M22] = ms[M02] * rn20 + ms[M12] * rn21 + ms[M22] * rn22;
        dest.ms[M23] = ms[M03] * rn20 + ms[M13] * rn21 + ms[M23] * rn22;
        // set the rest of the matrix elements
        dest.ms[M00] = nn00;
        dest.ms[M01] = nn01;
        dest.ms[M02] = nn02;
        dest.ms[M03] = nn03;
        dest.ms[M10] = nn10;
        dest.ms[M11] = nn11;
        dest.ms[M12] = nn12;
        dest.ms[M13] = nn13;
        dest.ms[M30] = ms[M30];
        dest.ms[M31] = ms[M31];
        dest.ms[M32] = ms[M32];
        dest.ms[M33] = ms[M33];

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
     * {@link #lookAt(float, float, float, float, float, float, float, float, float) lookAt()}
     * with <code>eye = (0, 0, 0)</code> and <code>center = dir</code>.
     * <p>
     * In order to set the matrix to a lookalong transformation without post-multiplying it,
     * use {@link #setLookAlong(float, float, float, float, float, float) setLookAlong()}
     * 
     * @see #lookAt(float, float, float, float, float, float, float, float, float)
     * @see #setLookAlong(float, float, float, float, float, float)
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
    public Matrix4f lookAlong(float dirX, float dirY, float dirZ,
                              float upX, float upY, float upZ) {
        return lookAlong(dirX, dirY, dirZ, upX, upY, upZ, this);
    }

    /**
     * Set this matrix to a rotation transformation to make <code>-z</code>
     * point along <code>dir</code>.
     * <p>
     * This is equivalent to calling
     * {@link #setLookAt(Vector3f, Vector3f, Vector3f) setLookAt()} 
     * with <code>eye = (0, 0, 0)</code> and <code>center = dir</code>.
     * <p>
     * In order to apply the lookalong transformation to any previous existing transformation,
     * use {@link #lookAlong(Vector3f, Vector3f)}.
     * 
     * @see #setLookAlong(Vector3f, Vector3f)
     * @see #lookAlong(Vector3f, Vector3f)
     * 
     * @param dir
     *            the direction in space to look along
     * @param up
     *            the direction of 'up'
     * @return this
     */
    public Matrix4f setLookAlong(Vector3f dir, Vector3f up) {
        return setLookAlong(dir.x, dir.y, dir.z, up.x, up.y, up.z);
    }

    /**
     * Set this matrix to a rotation transformation to make <code>-z</code>
     * point along <code>dir</code>.
     * <p>
     * This is equivalent to calling
     * {@link #setLookAt(float, float, float, float, float, float, float, float, float)
     * setLookAt()} with <code>eye = (0, 0, 0)</code> and <code>center = dir</code>.
     * <p>
     * In order to apply the lookalong transformation to any previous existing transformation,
     * use {@link #lookAlong(float, float, float, float, float, float) lookAlong()}
     * 
     * @see #setLookAlong(float, float, float, float, float, float)
     * @see #lookAlong(float, float, float, float, float, float)
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
    public Matrix4f setLookAlong(float dirX, float dirY, float dirZ,
                                 float upX, float upY, float upZ) {
        // Normalize direction
        float invDirLength = 1.0f / (float) Math.sqrt(dirX * dirX + dirY * dirY + dirZ * dirZ);
        float dirnX = dirX * invDirLength;
        float dirnY = dirY * invDirLength;
        float dirnZ = dirZ * invDirLength;
        // right = direction x up
        float rightX, rightY, rightZ;
        rightX = dirnY * upZ - dirnZ * upY;
        rightY = dirnZ * upX - dirnX * upZ;
        rightZ = dirnX * upY - dirnY * upX;
        // normalize right
        float invRightLength = 1.0f / (float) Math.sqrt(rightX * rightX + rightY * rightY + rightZ * rightZ);
        rightX *= invRightLength;
        rightY *= invRightLength;
        rightZ *= invRightLength;
        // up = right x direction
        float upnX = rightY * dirnZ - rightZ * dirnY;
        float upnY = rightZ * dirnX - rightX * dirnZ;
        float upnZ = rightX * dirnY - rightY * dirnX;

        ms[M00] = rightX;
        ms[M01] = upnX;
        ms[M02] = -dirnX;
        ms[M03] = 0.0f;
        ms[M10] = rightY;
        ms[M11] = upnY;
        ms[M12] = -dirnY;
        ms[M13] = 0.0f;
        ms[M20] = rightZ;
        ms[M21] = upnZ;
        ms[M22] = -dirnZ;
        ms[M23] = 0.0f;
        ms[M30] = 0.0f;
        ms[M31] = 0.0f;
        ms[M32] = 0.0f;
        ms[M33] = 1.0f;

        return this;
    }

    /**
     * Set this matrix to be a "lookat" transformation for a right-handed coordinate system, that aligns
     * <code>-z</code> with <code>center - eye</code>.
     * <p>
     * In order to not make use of vectors to specify <code>eye</code>, <code>center</code> and <code>up</code> but use primitives,
     * like in the GLU function, use {@link #setLookAt(float, float, float, float, float, float, float, float, float) setLookAt()}
     * instead.
     * <p>
     * In order to apply the lookat transformation to a previous existing transformation,
     * use {@link #lookAt(Vector3f, Vector3f, Vector3f) lookAt()}.
     * 
     * @see #setLookAt(float, float, float, float, float, float, float, float, float)
     * @see #lookAt(Vector3f, Vector3f, Vector3f)
     * 
     * @param eye
     *            the position of the camera
     * @param center
     *            the point in space to look at
     * @param up
     *            the direction of 'up'
     * @return this
     */
    public Matrix4f setLookAt(Vector3f eye, Vector3f center, Vector3f up) {
        return setLookAt(eye.x, eye.y, eye.z, center.x, center.y, center.z, up.x, up.y, up.z);
    }

    /**
     * Set this matrix to be a "lookat" transformation for a right-handed coordinate system, 
     * that aligns <code>-z</code> with <code>center - eye</code>.
     * <p>
     * In order to apply the lookat transformation to a previous existing transformation,
     * use {@link #lookAt(float, float, float, float, float, float, float, float, float) lookAt}.
     * 
     * @see #setLookAt(Vector3f, Vector3f, Vector3f)
     * @see #lookAt(float, float, float, float, float, float, float, float, float)
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
    public Matrix4f setLookAt(float eyeX, float eyeY, float eyeZ,
                              float centerX, float centerY, float centerZ,
                              float upX, float upY, float upZ) {
        // Compute direction from position to lookAt
        float dirX, dirY, dirZ;
        dirX = eyeX - centerX;
        dirY = eyeY - centerY;
        dirZ = eyeZ - centerZ;
        // Normalize direction
        float invDirLength = 1.0f / (float) Math.sqrt(dirX * dirX + dirY * dirY + dirZ * dirZ);
        dirX *= invDirLength;
        dirY *= invDirLength;
        dirZ *= invDirLength;
        // left = up x direction
        float leftX, leftY, leftZ;
        leftX = upY * dirZ - upZ * dirY;
        leftY = upZ * dirX - upX * dirZ;
        leftZ = upX * dirY - upY * dirX;
        // normalize left
        float invLeftLength = 1.0f / (float) Math.sqrt(leftX * leftX + leftY * leftY + leftZ * leftZ);
        leftX *= invLeftLength;
        leftY *= invLeftLength;
        leftZ *= invLeftLength;
        // up = direction x left
        float upnX = dirY * leftZ - dirZ * leftY;
        float upnY = dirZ * leftX - dirX * leftZ;
        float upnZ = dirX * leftY - dirY * leftX;

        ms[M00] = leftX;
        ms[M01] = upnX;
        ms[M02] = dirX;
        ms[M03] = 0.0f;
        ms[M10] = leftY;
        ms[M11] = upnY;
        ms[M12] = dirY;
        ms[M13] = 0.0f;
        ms[M20] = leftZ;
        ms[M21] = upnZ;
        ms[M22] = dirZ;
        ms[M23] = 0.0f;
        ms[M30] = -(leftX * eyeX + leftY * eyeY + leftZ * eyeZ);
        ms[M31] = -(upnX * eyeX + upnY * eyeY + upnZ * eyeZ);
        ms[M32] = -(dirX * eyeX + dirY * eyeY + dirZ * eyeZ);
        ms[M33] = 1.0f;

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
     * use {@link #setLookAt(Vector3f, Vector3f, Vector3f)}.
     * 
     * @see #lookAt(float, float, float, float, float, float, float, float, float)
     * @see #setLookAlong(Vector3f, Vector3f)
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
    public Matrix4f lookAt(Vector3f eye, Vector3f center, Vector3f up, Matrix4f dest) {
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
     * use {@link #setLookAt(Vector3f, Vector3f, Vector3f)}.
     * 
     * @see #lookAt(float, float, float, float, float, float, float, float, float)
     * @see #setLookAlong(Vector3f, Vector3f)
     * 
     * @param eye
     *            the position of the camera
     * @param center
     *            the point in space to look at
     * @param up
     *            the direction of 'up'
     * @return this
     */
    public Matrix4f lookAt(Vector3f eye, Vector3f center, Vector3f up) {
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
     * use {@link #setLookAt(float, float, float, float, float, float, float, float, float) setLookAt()}.
     * 
     * @see #lookAt(Vector3f, Vector3f, Vector3f)
     * @see #setLookAt(float, float, float, float, float, float, float, float, float)
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
    public Matrix4f lookAt(float eyeX, float eyeY, float eyeZ,
                           float centerX, float centerY, float centerZ,
                           float upX, float upY, float upZ, Matrix4f dest) {
        // Compute direction from position to lookAt
        float dirX, dirY, dirZ;
        dirX = eyeX - centerX;
        dirY = eyeY - centerY;
        dirZ = eyeZ - centerZ;
        // Normalize direction
        float invDirLength = 1.0f / (float) Math.sqrt(dirX * dirX + dirY * dirY + dirZ * dirZ);
        dirX *= invDirLength;
        dirY *= invDirLength;
        dirZ *= invDirLength;
        // left = up x direction
        float leftX, leftY, leftZ;
        leftX = upY * dirZ - upZ * dirY;
        leftY = upZ * dirX - upX * dirZ;
        leftZ = upX * dirY - upY * dirX;
        // normalize left
        float invLeftLength = 1.0f / (float) Math.sqrt(leftX * leftX + leftY * leftY + leftZ * leftZ);
        leftX *= invLeftLength;
        leftY *= invLeftLength;
        leftZ *= invLeftLength;
        // up = direction x left
        float upnX = dirY * leftZ - dirZ * leftY;
        float upnY = dirZ * leftX - dirX * leftZ;
        float upnZ = dirX * leftY - dirY * leftX;

        // calculate right matrix elements
        float rn00 = leftX;
        float rn01 = upnX;
        float rn02 = dirX;
        float rn10 = leftY;
        float rn11 = upnY;
        float rn12 = dirY;
        float rn20 = leftZ;
        float rn21 = upnZ;
        float rn22 = dirZ;
        float rn30 = -(leftX * eyeX + leftY * eyeY + leftZ * eyeZ);
        float rn31 = -(upnX * eyeX + upnY * eyeY + upnZ * eyeZ);
        float rn32 = -(dirX * eyeX + dirY * eyeY + dirZ * eyeZ);

        // perform optimized matrix multiplication
        // compute last column first, because others do not depend on it
        dest.ms[M30] = ms[M00] * rn30 + ms[M10] * rn31 + ms[M20] * rn32 + ms[M30];
        dest.ms[M31] = ms[M01] * rn30 + ms[M11] * rn31 + ms[M21] * rn32 + ms[M31];
        dest.ms[M32] = ms[M02] * rn30 + ms[M12] * rn31 + ms[M22] * rn32 + ms[M32];
        dest.ms[M33] = ms[M03] * rn30 + ms[M13] * rn31 + ms[M23] * rn32 + ms[M33];
        // introduce temporaries for dependent results
        float nn00 = ms[M00] * rn00 + ms[M10] * rn01 + ms[M20] * rn02;
        float nn01 = ms[M01] * rn00 + ms[M11] * rn01 + ms[M21] * rn02;
        float nn02 = ms[M02] * rn00 + ms[M12] * rn01 + ms[M22] * rn02;
        float nn03 = ms[M03] * rn00 + ms[M13] * rn01 + ms[M23] * rn02;
        float nn10 = ms[M00] * rn10 + ms[M10] * rn11 + ms[M20] * rn12;
        float nn11 = ms[M01] * rn10 + ms[M11] * rn11 + ms[M21] * rn12;
        float nn12 = ms[M02] * rn10 + ms[M12] * rn11 + ms[M22] * rn12;
        float nn13 = ms[M03] * rn10 + ms[M13] * rn11 + ms[M23] * rn12;
        dest.ms[M20] = ms[M00] * rn20 + ms[M10] * rn21 + ms[M20] * rn22;
        dest.ms[M21] = ms[M01] * rn20 + ms[M11] * rn21 + ms[M21] * rn22;
        dest.ms[M22] = ms[M02] * rn20 + ms[M12] * rn21 + ms[M22] * rn22;
        dest.ms[M23] = ms[M03] * rn20 + ms[M13] * rn21 + ms[M23] * rn22;
        // set the rest of the matrix elements
        dest.ms[M00] = nn00;
        dest.ms[M01] = nn01;
        dest.ms[M02] = nn02;
        dest.ms[M03] = nn03;
        dest.ms[M10] = nn10;
        dest.ms[M11] = nn11;
        dest.ms[M12] = nn12;
        dest.ms[M13] = nn13;

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
     * use {@link #setLookAt(float, float, float, float, float, float, float, float, float) setLookAt()}.
     * 
     * @see #lookAt(Vector3f, Vector3f, Vector3f)
     * @see #setLookAt(float, float, float, float, float, float, float, float, float)
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
    public Matrix4f lookAt(float eyeX, float eyeY, float eyeZ,
                           float centerX, float centerY, float centerZ,
                           float upX, float upY, float upZ) {
        return lookAt(eyeX, eyeY, eyeZ, centerX, centerY, centerZ, upX, upY, upZ, this);
    }

    /**
     * Apply a symmetric perspective projection frustum transformation using the given NDC z range to this matrix and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>P</code> the perspective projection matrix,
     * then the new matrix will be <code>M * P</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * P * v</code>,
     * the perspective projection will be applied first!
     * <p>
     * In order to set the matrix to a perspective frustum transformation without post-multiplying,
     * use {@link #setPerspective(float, float, float, float) setPerspective}.
     * 
     * @see #setPerspective(float, float, float, float)
     * 
     * @param fovy
     *            the vertical field of view in radians (must be greater than zero and less than {@link Math#PI PI})
     * @param aspect
     *            the aspect ratio (i.e. width / height; must be greater than zero)
     * @param zNear
     *            near clipping plane distance. If the special value {@link Float#POSITIVE_INFINITY} is used, the near clipping plane will be at positive infinity.
     *            In that case, <code>zFar</code> may not also be {@link Float#POSITIVE_INFINITY}.
     * @param zFar
     *            far clipping plane distance. If the special value {@link Float#POSITIVE_INFINITY} is used, the far clipping plane will be at positive infinity.
     *            In that case, <code>zNear</code> may not also be {@link Float#POSITIVE_INFINITY}.
     * @param dest
     *            will hold the result
     * @param zZeroToOne
     *            whether to use Vulkan's and Direct3D's NDC z range of <tt>[0..+1]</tt> when <code>true</code>
     *            or whether to use OpenGL's NDC z range of <tt>[-1..+1]</tt> when <code>false</code>
     * @return dest
     */
    public Matrix4f perspective(float fovy, float aspect, float zNear, float zFar, boolean zZeroToOne, Matrix4f dest) {
        float h = (float) Math.tan(fovy * 0.5f);

        // calculate right matrix elements
        float rn00 = 1.0f / (h * aspect);
        float rn11 = 1.0f / h;
        float rn22;
        float rn32;
        boolean farInf = zFar > 0 && Float.isInfinite(zFar);
        boolean nearInf = zNear > 0 && Float.isInfinite(zNear);
        if (farInf) {
            // See: "Infinite Projection Matrix" (http://www.terathon.com/gdc07_lengyel.pdf)
            float e = 1E-6f;
            rn22 = e - 1.0f;
            rn32 = (e - (zZeroToOne ? 1.0f : 2.0f)) * zNear;
        } else if (nearInf) {
            float e = 1E-6f;
            rn22 = (zZeroToOne ? 0.0f : 1.0f) - e;
            rn32 = ((zZeroToOne ? 1.0f : 2.0f) - e) * zFar;
        } else {
            rn22 = (zZeroToOne ? zFar : zFar + zNear) / (zNear - zFar);
            rn32 = (zZeroToOne ? zFar : zFar + zFar) * zNear / (zNear - zFar);
        }
        // perform optimized matrix multiplication
        float nn20 = ms[M20] * rn22 - ms[M30];
        float nn21 = ms[M21] * rn22 - ms[M31];
        float nn22 = ms[M22] * rn22 - ms[M32];
        float nn23 = ms[M23] * rn22 - ms[M33];
        dest.ms[M00] = ms[M00] * rn00;
        dest.ms[M01] = ms[M01] * rn00;
        dest.ms[M02] = ms[M02] * rn00;
        dest.ms[M03] = ms[M03] * rn00;
        dest.ms[M10] = ms[M10] * rn11;
        dest.ms[M11] = ms[M11] * rn11;
        dest.ms[M12] = ms[M12] * rn11;
        dest.ms[M13] = ms[M13] * rn11;
        dest.ms[M30] = ms[M20] * rn32;
        dest.ms[M31] = ms[M21] * rn32;
        dest.ms[M32] = ms[M22] * rn32;
        dest.ms[M33] = ms[M23] * rn32;
        dest.ms[M20] = nn20;
        dest.ms[M21] = nn21;
        dest.ms[M22] = nn22;
        dest.ms[M23] = nn23;

        return dest;
    }

    /**
     * Apply a symmetric perspective projection frustum transformation using OpenGL's NDC z range of <tt>[-1..+1]</tt> to this matrix and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>P</code> the perspective projection matrix,
     * then the new matrix will be <code>M * P</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * P * v</code>,
     * the perspective projection will be applied first!
     * <p>
     * In order to set the matrix to a perspective frustum transformation without post-multiplying,
     * use {@link #setPerspective(float, float, float, float) setPerspective}.
     * 
     * @see #setPerspective(float, float, float, float)
     * 
     * @param fovy
     *            the vertical field of view in radians (must be greater than zero and less than {@link Math#PI PI})
     * @param aspect
     *            the aspect ratio (i.e. width / height; must be greater than zero)
     * @param zNear
     *            near clipping plane distance. If the special value {@link Float#POSITIVE_INFINITY} is used, the near clipping plane will be at positive infinity.
     *            In that case, <code>zFar</code> may not also be {@link Float#POSITIVE_INFINITY}.
     * @param zFar
     *            far clipping plane distance. If the special value {@link Float#POSITIVE_INFINITY} is used, the far clipping plane will be at positive infinity.
     *            In that case, <code>zNear</code> may not also be {@link Float#POSITIVE_INFINITY}.
     * @param dest
     *            will hold the result
     * @return dest
     */
    public Matrix4f perspective(float fovy, float aspect, float zNear, float zFar, Matrix4f dest) {
        return perspective(fovy, aspect, zNear, zFar, false, dest);
    }

    /**
     * Apply a symmetric perspective projection frustum transformation using the given NDC z range to this matrix.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>P</code> the perspective projection matrix,
     * then the new matrix will be <code>M * P</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * P * v</code>,
     * the perspective projection will be applied first!
     * <p>
     * In order to set the matrix to a perspective frustum transformation without post-multiplying,
     * use {@link #setPerspective(float, float, float, float, boolean) setPerspective}.
     * 
     * @see #setPerspective(float, float, float, float, boolean)
     * 
     * @param fovy
     *            the vertical field of view in radians (must be greater than zero and less than {@link Math#PI PI})
     * @param aspect
     *            the aspect ratio (i.e. width / height; must be greater than zero)
     * @param zNear
     *            near clipping plane distance. If the special value {@link Float#POSITIVE_INFINITY} is used, the near clipping plane will be at positive infinity.
     *            In that case, <code>zFar</code> may not also be {@link Float#POSITIVE_INFINITY}.
     * @param zFar
     *            far clipping plane distance. If the special value {@link Float#POSITIVE_INFINITY} is used, the far clipping plane will be at positive infinity.
     *            In that case, <code>zNear</code> may not also be {@link Float#POSITIVE_INFINITY}.
     * @param zZeroToOne
     *            whether to use Vulkan's and Direct3D's NDC z range of <tt>[0..+1]</tt> when <code>true</code>
     *            or whether to use OpenGL's NDC z range of <tt>[-1..+1]</tt> when <code>false</code>
     * @return this
     */
    public Matrix4f perspective(float fovy, float aspect, float zNear, float zFar, boolean zZeroToOne) {
        return perspective(fovy, aspect, zNear, zFar, zZeroToOne, this);
    }

    /**
     * Apply a symmetric perspective projection frustum transformation using OpenGL's NDC z range of <tt>[-1..+1]</tt> to this matrix.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>P</code> the perspective projection matrix,
     * then the new matrix will be <code>M * P</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * P * v</code>,
     * the perspective projection will be applied first!
     * <p>
     * In order to set the matrix to a perspective frustum transformation without post-multiplying,
     * use {@link #setPerspective(float, float, float, float) setPerspective}.
     * 
     * @see #setPerspective(float, float, float, float)
     * 
     * @param fovy
     *            the vertical field of view in radians (must be greater than zero and less than {@link Math#PI PI})
     * @param aspect
     *            the aspect ratio (i.e. width / height; must be greater than zero)
     * @param zNear
     *            near clipping plane distance. If the special value {@link Float#POSITIVE_INFINITY} is used, the near clipping plane will be at positive infinity.
     *            In that case, <code>zFar</code> may not also be {@link Float#POSITIVE_INFINITY}.
     * @param zFar
     *            far clipping plane distance. If the special value {@link Float#POSITIVE_INFINITY} is used, the far clipping plane will be at positive infinity.
     *            In that case, <code>zNear</code> may not also be {@link Float#POSITIVE_INFINITY}.
     * @return this
     */
    public Matrix4f perspective(float fovy, float aspect, float zNear, float zFar) {
        return perspective(fovy, aspect, zNear, zFar, this);
    }

    /**
     * Set this matrix to be a symmetric perspective projection frustum transformation using the given NDC z range.
     * <p>
     * In order to apply the perspective projection transformation to an existing transformation,
     * use {@link #perspective(float, float, float, float, boolean) perspective()}.
     * 
     * @see #perspective(float, float, float, float, boolean)
     * 
     * @param fovy
     *            the vertical field of view in radians (must be greater than zero and less than {@link Math#PI PI})
     * @param aspect
     *            the aspect ratio (i.e. width / height; must be greater than zero)
     * @param zNear
     *            near clipping plane distance. If the special value {@link Float#POSITIVE_INFINITY} is used, the near clipping plane will be at positive infinity.
     *            In that case, <code>zFar</code> may not also be {@link Float#POSITIVE_INFINITY}.
     * @param zFar
     *            far clipping plane distance. If the special value {@link Float#POSITIVE_INFINITY} is used, the far clipping plane will be at positive infinity.
     *            In that case, <code>zNear</code> may not also be {@link Float#POSITIVE_INFINITY}.
     * @param zZeroToOne
     *            whether to use Vulkan's and Direct3D's NDC z range of <tt>[0..+1]</tt> when <code>true</code>
     *            or whether to use OpenGL's NDC z range of <tt>[-1..+1]</tt> when <code>false</code>
     * @return this
     */
    public Matrix4f setPerspective(float fovy, float aspect, float zNear, float zFar, boolean zZeroToOne) {
        float h = (float) Math.tan(fovy * 0.5f);
        ms[M00] = 1.0f / (h * aspect);
        ms[M01] = 0.0f;
        ms[M02] = 0.0f;
        ms[M03] = 0.0f;
        ms[M10] = 0.0f;
        ms[M11] = 1.0f / h;
        ms[M12] = 0.0f;
        ms[M13] = 0.0f;
        ms[M20] = 0.0f;
        ms[M21] = 0.0f;
        boolean farInf = zFar > 0 && Float.isInfinite(zFar);
        boolean nearInf = zNear > 0 && Float.isInfinite(zNear);
        if (farInf) {
            // See: "Infinite Projection Matrix" (http://www.terathon.com/gdc07_lengyel.pdf)
            float e = 1E-6f;
            ms[M22] = e - 1.0f;
            ms[M32] = (e - (zZeroToOne ? 1.0f : 2.0f)) * zNear;
        } else if (nearInf) {
            float e = 1E-6f;
            ms[M22] = (zZeroToOne ? 0.0f : 1.0f) - e;
            ms[M32] = ((zZeroToOne ? 1.0f : 2.0f) - e) * zFar;
        } else {
            ms[M22] = (zZeroToOne ? zFar : zFar + zNear) / (zNear - zFar);
            ms[M32] = (zZeroToOne ? zFar : zFar + zFar) * zNear / (zNear - zFar);
        }
        ms[M23] = -1.0f;
        ms[M30] = 0.0f;
        ms[M31] = 0.0f;
        ms[M33] = 0.0f;
        return this;
    }

    /**
     * Set this matrix to be a symmetric perspective projection frustum transformation using OpenGL's NDC z range of <tt>[-1..+1]</tt>.
     * <p>
     * In order to apply the perspective projection transformation to an existing transformation,
     * use {@link #perspective(float, float, float, float) perspective()}.
     * 
     * @see #perspective(float, float, float, float)
     * 
     * @param fovy
     *            the vertical field of view in radians (must be greater than zero and less than {@link Math#PI PI})
     * @param aspect
     *            the aspect ratio (i.e. width / height; must be greater than zero)
     * @param zNear
     *            near clipping plane distance. If the special value {@link Float#POSITIVE_INFINITY} is used, the near clipping plane will be at positive infinity.
     *            In that case, <code>zFar</code> may not also be {@link Float#POSITIVE_INFINITY}.
     * @param zFar
     *            far clipping plane distance. If the special value {@link Float#POSITIVE_INFINITY} is used, the far clipping plane will be at positive infinity.
     *            In that case, <code>zNear</code> may not also be {@link Float#POSITIVE_INFINITY}.
     * @return this
     */
    public Matrix4f setPerspective(float fovy, float aspect, float zNear, float zFar) {
        return setPerspective(fovy, aspect, zNear, zFar, false);
    }

    /**
     * Apply an arbitrary perspective projection frustum transformation using the given NDC z range to this matrix 
     * and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>F</code> the frustum matrix,
     * then the new matrix will be <code>M * F</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * F * v</code>,
     * the frustum transformation will be applied first!
     * <p>
     * In order to set the matrix to a perspective frustum transformation without post-multiplying,
     * use {@link #setFrustum(float, float, float, float, float, float, boolean) setFrustum()}.
     * <p>
     * Reference: <a href="http://www.songho.ca/opengl/gl_projectionmatrix.html#perspective">http://www.songho.ca</a>
     * 
     * @see #setFrustum(float, float, float, float, float, float, boolean)
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
     *            near clipping plane distance. If the special value {@link Float#POSITIVE_INFINITY} is used, the near clipping plane will be at positive infinity.
     *            In that case, <code>zFar</code> may not also be {@link Float#POSITIVE_INFINITY}.
     * @param zFar
     *            far clipping plane distance. If the special value {@link Float#POSITIVE_INFINITY} is used, the far clipping plane will be at positive infinity.
     *            In that case, <code>zNear</code> may not also be {@link Float#POSITIVE_INFINITY}.
     * @param zZeroToOne
     *            whether to use Vulkan's and Direct3D's NDC z range of <tt>[0..+1]</tt> when <code>true</code>
     *            or whether to use OpenGL's NDC z range of <tt>[-1..+1]</tt> when <code>false</code>
     * @param dest
     *            will hold the result
     * @return dest
     */
    public Matrix4f frustum(float left, float right, float bottom, float top, float zNear, float zFar, boolean zZeroToOne, Matrix4f dest) {
        // calculate right matrix elements
        float rn00 = (zNear + zNear) / (right - left);
        float rn11 = (zNear + zNear) / (top - bottom);
        float rn20 = (right + left) / (right - left);
        float rn21 = (top + bottom) / (top - bottom);
        float rn22;
        float rn32;
        boolean farInf = zFar > 0 && Float.isInfinite(zFar);
        boolean nearInf = zNear > 0 && Float.isInfinite(zNear);
        if (farInf) {
            // See: "Infinite Projection Matrix" (http://www.terathon.com/gdc07_lengyel.pdf)
            float e = 1E-6f;
            rn22 = e - 1.0f;
            rn32 = (e - (zZeroToOne ? 1.0f : 2.0f)) * zNear;
        } else if (nearInf) {
            float e = 1E-6f;
            rn22 = (zZeroToOne ? 0.0f : 1.0f) - e;
            rn32 = ((zZeroToOne ? 1.0f : 2.0f) - e) * zFar;
        } else {
            rn22 = (zZeroToOne ? zFar : zFar + zNear) / (zNear - zFar);
            rn32 = (zZeroToOne ? zFar : zFar + zFar) * zNear / (zNear - zFar);
        }
        // perform optimized matrix multiplication
        float nn20 = ms[M00] * rn20 + ms[M10] * rn21 + ms[M20] * rn22 - ms[M30];
        float nn21 = ms[M01] * rn20 + ms[M11] * rn21 + ms[M21] * rn22 - ms[M31];
        float nn22 = ms[M02] * rn20 + ms[M12] * rn21 + ms[M22] * rn22 - ms[M32];
        float nn23 = ms[M03] * rn20 + ms[M13] * rn21 + ms[M23] * rn22 - ms[M33];
        dest.ms[M00] = ms[M00] * rn00;
        dest.ms[M01] = ms[M01] * rn00;
        dest.ms[M02] = ms[M02] * rn00;
        dest.ms[M03] = ms[M03] * rn00;
        dest.ms[M10] = ms[M10] * rn11;
        dest.ms[M11] = ms[M11] * rn11;
        dest.ms[M12] = ms[M12] * rn11;
        dest.ms[M13] = ms[M13] * rn11;
        dest.ms[M30] = ms[M20] * rn32;
        dest.ms[M31] = ms[M21] * rn32;
        dest.ms[M32] = ms[M22] * rn32;
        dest.ms[M33] = ms[M23] * rn32;
        dest.ms[M20] = nn20;
        dest.ms[M21] = nn21;
        dest.ms[M22] = nn22;
        dest.ms[M23] = nn23;
        dest.ms[M30] = ms[M30];
        dest.ms[M31] = ms[M31];
        dest.ms[M32] = ms[M32];
        dest.ms[M33] = ms[M33];

        return dest;
    }

    /**
     * Apply an arbitrary perspective projection frustum transformation using OpenGL's NDC z range of <tt>[-1..+1]</tt> to this matrix 
     * and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>F</code> the frustum matrix,
     * then the new matrix will be <code>M * F</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * F * v</code>,
     * the frustum transformation will be applied first!
     * <p>
     * In order to set the matrix to a perspective frustum transformation without post-multiplying,
     * use {@link #setFrustum(float, float, float, float, float, float) setFrustum()}.
     * <p>
     * Reference: <a href="http://www.songho.ca/opengl/gl_projectionmatrix.html#perspective">http://www.songho.ca</a>
     * 
     * @see #setFrustum(float, float, float, float, float, float)
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
     *            near clipping plane distance. If the special value {@link Float#POSITIVE_INFINITY} is used, the near clipping plane will be at positive infinity.
     *            In that case, <code>zFar</code> may not also be {@link Float#POSITIVE_INFINITY}.
     * @param zFar
     *            far clipping plane distance. If the special value {@link Float#POSITIVE_INFINITY} is used, the far clipping plane will be at positive infinity.
     *            In that case, <code>zNear</code> may not also be {@link Float#POSITIVE_INFINITY}.
     * @param dest
     *            will hold the result
     * @return dest
     */
    public Matrix4f frustum(float left, float right, float bottom, float top, float zNear, float zFar, Matrix4f dest) {
        return frustum(left, right, bottom, top, zNear, zFar, false, dest);
    }

    /**
     * Apply an arbitrary perspective projection frustum transformation using the given NDC z range to this matrix.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>F</code> the frustum matrix,
     * then the new matrix will be <code>M * F</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * F * v</code>,
     * the frustum transformation will be applied first!
     * <p>
     * In order to set the matrix to a perspective frustum transformation without post-multiplying,
     * use {@link #setFrustum(float, float, float, float, float, float, boolean) setFrustum()}.
     * <p>
     * Reference: <a href="http://www.songho.ca/opengl/gl_projectionmatrix.html#perspective">http://www.songho.ca</a>
     * 
     * @see #setFrustum(float, float, float, float, float, float, boolean)
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
     *            near clipping plane distance. If the special value {@link Float#POSITIVE_INFINITY} is used, the near clipping plane will be at positive infinity.
     *            In that case, <code>zFar</code> may not also be {@link Float#POSITIVE_INFINITY}.
     * @param zFar
     *            far clipping plane distance. If the special value {@link Float#POSITIVE_INFINITY} is used, the far clipping plane will be at positive infinity.
     *            In that case, <code>zNear</code> may not also be {@link Float#POSITIVE_INFINITY}.
     * @param zZeroToOne
     *            whether to use Vulkan's and Direct3D's NDC z range of <tt>[0..+1]</tt> when <code>true</code>
     *            or whether to use OpenGL's NDC z range of <tt>[-1..+1]</tt> when <code>false</code>
     * @return this
     */
    public Matrix4f frustum(float left, float right, float bottom, float top, float zNear, float zFar, boolean zZeroToOne) {
        return frustum(left, right, bottom, top, zNear, zFar, zZeroToOne, this);
    }

    /**
     * Apply an arbitrary perspective projection frustum transformation using OpenGL's NDC z range of <tt>[-1..+1]</tt> to this matrix.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>F</code> the frustum matrix,
     * then the new matrix will be <code>M * F</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * F * v</code>,
     * the frustum transformation will be applied first!
     * <p>
     * In order to set the matrix to a perspective frustum transformation without post-multiplying,
     * use {@link #setFrustum(float, float, float, float, float, float) setFrustum()}.
     * <p>
     * Reference: <a href="http://www.songho.ca/opengl/gl_projectionmatrix.html#perspective">http://www.songho.ca</a>
     * 
     * @see #setFrustum(float, float, float, float, float, float)
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
     *            near clipping plane distance. If the special value {@link Float#POSITIVE_INFINITY} is used, the near clipping plane will be at positive infinity.
     *            In that case, <code>zFar</code> may not also be {@link Float#POSITIVE_INFINITY}.
     * @param zFar
     *            far clipping plane distance. If the special value {@link Float#POSITIVE_INFINITY} is used, the far clipping plane will be at positive infinity.
     *            In that case, <code>zNear</code> may not also be {@link Float#POSITIVE_INFINITY}.
     * @return this
     */
    public Matrix4f frustum(float left, float right, float bottom, float top, float zNear, float zFar) {
        return frustum(left, right, bottom, top, zNear, zFar, this);
    }

    /**
     * Set this matrix to be an arbitrary perspective projection frustum transformation using the given NDC z range.
     * <p>
     * In order to apply the perspective frustum transformation to an existing transformation,
     * use {@link #frustum(float, float, float, float, float, float, boolean) frustum()}.
     * <p>
     * Reference: <a href="http://www.songho.ca/opengl/gl_projectionmatrix.html#perspective">http://www.songho.ca</a>
     * 
     * @see #frustum(float, float, float, float, float, float, boolean)
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
     *            near clipping plane distance. If the special value {@link Float#POSITIVE_INFINITY} is used, the near clipping plane will be at positive infinity.
     *            In that case, <code>zFar</code> may not also be {@link Float#POSITIVE_INFINITY}.
     * @param zFar
     *            far clipping plane distance. If the special value {@link Float#POSITIVE_INFINITY} is used, the far clipping plane will be at positive infinity.
     *            In that case, <code>zNear</code> may not also be {@link Float#POSITIVE_INFINITY}.
     * @param zZeroToOne
     *            whether to use Vulkan's and Direct3D's NDC z range of <tt>[0..+1]</tt> when <code>true</code>
     *            or whether to use OpenGL's NDC z range of <tt>[-1..+1]</tt> when <code>false</code>
     * @return this
     */
    public Matrix4f setFrustum(float left, float right, float bottom, float top, float zNear, float zFar, boolean zZeroToOne) {
        ms[M00] = (zNear + zNear) / (right - left);
        ms[M01] = 0.0f;
        ms[M02] = 0.0f;
        ms[M03] = 0.0f;
        ms[M10] = 0.0f;
        ms[M11] = (zNear + zNear) / (top - bottom);
        ms[M12] = 0.0f;
        ms[M13] = 0.0f;
        ms[M20] = (right + left) / (right - left);
        ms[M21] = (top + bottom) / (top - bottom);
        boolean farInf = zFar > 0 && Float.isInfinite(zFar);
        boolean nearInf = zNear > 0 && Float.isInfinite(zNear);
        if (farInf) {
            // See: "Infinite Projection Matrix" (http://www.terathon.com/gdc07_lengyel.pdf)
            float e = 1E-6f;
            ms[M22] = e - 1.0f;
            ms[M32] = (e - (zZeroToOne ? 1.0f : 2.0f)) * zNear;
        } else if (nearInf) {
            float e = 1E-6f;
            ms[M22] = (zZeroToOne ? 0.0f : 1.0f) - e;
            ms[M32] = ((zZeroToOne ? 1.0f : 2.0f) - e) * zFar;
        } else {
            ms[M22] = (zZeroToOne ? zFar : zFar + zNear) / (zNear - zFar);
            ms[M32] = (zZeroToOne ? zFar : zFar + zFar) * zNear / (zNear - zFar);
        }
        ms[M23] = -1.0f;
        ms[M30] = 0.0f;
        ms[M31] = 0.0f;
        ms[M33] = 0.0f;
        return this;
    }

    /**
     * Set this matrix to be an arbitrary perspective projection frustum transformation using OpenGL's NDC z range of <tt>[-1..+1]</tt>.
     * <p>
     * In order to apply the perspective frustum transformation to an existing transformation,
     * use {@link #frustum(float, float, float, float, float, float) frustum()}.
     * <p>
     * Reference: <a href="http://www.songho.ca/opengl/gl_projectionmatrix.html#perspective">http://www.songho.ca</a>
     * 
     * @see #frustum(float, float, float, float, float, float)
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
     *            near clipping plane distance. If the special value {@link Float#POSITIVE_INFINITY} is used, the near clipping plane will be at positive infinity.
     *            In that case, <code>zFar</code> may not also be {@link Float#POSITIVE_INFINITY}.
     * @param zFar
     *            far clipping plane distance. If the special value {@link Float#POSITIVE_INFINITY} is used, the far clipping plane will be at positive infinity.
     *            In that case, <code>zNear</code> may not also be {@link Float#POSITIVE_INFINITY}.
     * @return this
     */
    public Matrix4f setFrustum(float left, float right, float bottom, float top, float zNear, float zFar) {
        return setFrustum(left, right, bottom, top, zNear, zFar, false);
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
    public Matrix4f rotate(Quaternionf quat, Matrix4f dest) {
        float dqx = quat.x + quat.x;
        float dqy = quat.y + quat.y;
        float dqz = quat.z + quat.z;
        float q00 = dqx * quat.x;
        float q11 = dqy * quat.y;
        float q22 = dqz * quat.z;
        float q01 = dqx * quat.y;
        float q02 = dqx * quat.z;
        float q03 = dqx * quat.w;
        float q12 = dqy * quat.z;
        float q13 = dqy * quat.w;
        float q23 = dqz * quat.w;

        float rn00 = 1.0f - q11 - q22;
        float rn01 = q01 + q23;
        float rn02 = q02 - q13;
        float rn10 = q01 - q23;
        float rn11 = 1.0f - q22 - q00;
        float rn12 = q12 + q03;
        float rn20 = q02 + q13;
        float rn21 = q12 - q03;
        float rn22 = 1.0f - q11 - q00;

        float nn00 = ms[M00] * rn00 + ms[M10] * rn01 + ms[M20] * rn02;
        float nn01 = ms[M01] * rn00 + ms[M11] * rn01 + ms[M21] * rn02;
        float nn02 = ms[M02] * rn00 + ms[M12] * rn01 + ms[M22] * rn02;
        float nn03 = ms[M03] * rn00 + ms[M13] * rn01 + ms[M23] * rn02;
        float nn10 = ms[M00] * rn10 + ms[M10] * rn11 + ms[M20] * rn12;
        float nn11 = ms[M01] * rn10 + ms[M11] * rn11 + ms[M21] * rn12;
        float nn12 = ms[M02] * rn10 + ms[M12] * rn11 + ms[M22] * rn12;
        float nn13 = ms[M03] * rn10 + ms[M13] * rn11 + ms[M23] * rn12;
        dest.ms[M20] = ms[M00] * rn20 + ms[M10] * rn21 + ms[M20] * rn22;
        dest.ms[M21] = ms[M01] * rn20 + ms[M11] * rn21 + ms[M21] * rn22;
        dest.ms[M22] = ms[M02] * rn20 + ms[M12] * rn21 + ms[M22] * rn22;
        dest.ms[M23] = ms[M03] * rn20 + ms[M13] * rn21 + ms[M23] * rn22;
        dest.ms[M00] = nn00;
        dest.ms[M01] = nn01;
        dest.ms[M02] = nn02;
        dest.ms[M03] = nn03;
        dest.ms[M10] = nn10;
        dest.ms[M11] = nn11;
        dest.ms[M12] = nn12;
        dest.ms[M13] = nn13;
        dest.ms[M30] = ms[M30];
        dest.ms[M31] = ms[M31];
        dest.ms[M32] = ms[M32];
        dest.ms[M33] = ms[M33];

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
    public Matrix4f rotate(Quaternionf quat) {
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
     * @see #rotate(float, float, float, float)
     * @see #rotation(AxisAngle4f)
     * 
     * @param axisAngle
     *          the {@link AxisAngle4f} (needs to be {@link AxisAngle4f#normalize() normalized})
     * @return this
     */
    public Matrix4f rotate(AxisAngle4f axisAngle) {
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
     * @see #rotate(float, float, float, float)
     * @see #rotation(AxisAngle4f)
     * 
     * @param axisAngle
     *          the {@link AxisAngle4f} (needs to be {@link AxisAngle4f#normalize() normalized})
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Matrix4f rotate(AxisAngle4f axisAngle, Matrix4f dest) {
        return rotate(axisAngle.angle, axisAngle.x, axisAngle.y, axisAngle.z, dest);
    }

    /**
     * Apply a rotation transformation, rotating the given radians about the specified axis, to this matrix.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>A</code> the rotation matrix obtained from the given axis-angle,
     * then the new matrix will be <code>M * A</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * A * v</code>,
     * the axis-angle rotation will be applied first!
     * <p>
     * In order to set the matrix to a rotation transformation without post-multiplying,
     * use {@link #rotation(float, Vector3f)}.
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Axis_and_angle">http://en.wikipedia.org</a>
     * 
     * @see #rotate(float, float, float, float)
     * @see #rotation(float, Vector3f)
     * 
     * @param angle
     *          the angle in radians
     * @param axis
     *          the rotation axis (needs to be {@link Vector3f#normalize() normalized})
     * @return this
     */
    public Matrix4f rotate(float angle, Vector3f axis) {
        return rotate(angle, axis.x, axis.y, axis.z);
    }

    /**
     * Apply a rotation transformation, rotating the given radians about the specified axis and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>A</code> the rotation matrix obtained from the given axis-angle,
     * then the new matrix will be <code>M * A</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * A * v</code>,
     * the axis-angle rotation will be applied first!
     * <p>
     * In order to set the matrix to a rotation transformation without post-multiplying,
     * use {@link #rotation(float, Vector3f)}.
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Axis_and_angle">http://en.wikipedia.org</a>
     * 
     * @see #rotate(float, float, float, float)
     * @see #rotation(float, Vector3f)
     * 
     * @param angle
     *          the angle in radians
     * @param axis
     *          the rotation axis (needs to be {@link Vector3f#normalize() normalized})
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Matrix4f rotate(float angle, Vector3f axis, Matrix4f dest) {
        return rotate(angle, axis.x, axis.y, axis.z, dest);
    }

    /**
     * Unproject the given window coordinates <tt>(winX, winY, winZ)</tt> by <code>this</code> matrix using the specified viewport.
     * <p>
     * This method first converts the given window coordinates to normalized device coordinates in the range <tt>[-1..1]</tt>
     * and then transforms those NDC coordinates by the inverse of <code>this</code> matrix.  
     * <p>
     * The depth range of <tt>winZ</tt> is assumed to be <tt>[0..1]</tt>, which is also the OpenGL default.
     * <p>
     * As a necessary computation step for unprojecting, this method computes the inverse of <code>this</code> matrix.
     * In order to avoid computing the matrix inverse with every invocation, the inverse of <code>this</code> matrix can be built
     * once outside using {@link #invert(Matrix4f)} and then the method {@link #unprojectInv(float, float, float, int[], Vector4f) unprojectInv()} can be invoked on it.
     * 
     * @see #unprojectInv(float, float, float, int[], Vector4f)
     * @see #invert(Matrix4f)
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
    public Vector4f unproject(float winX, float winY, float winZ, int[] viewport, Vector4f dest) {
        float a = ms[M00] * ms[M11] - ms[M01] * ms[M10];
        float b = ms[M00] * ms[M12] - ms[M02] * ms[M10];
        float c = ms[M00] * ms[M13] - ms[M03] * ms[M10];
        float d = ms[M01] * ms[M12] - ms[M02] * ms[M11];
        float e = ms[M01] * ms[M13] - ms[M03] * ms[M11];
        float f = ms[M02] * ms[M13] - ms[M03] * ms[M12];
        float g = ms[M20] * ms[M31] - ms[M21] * ms[M30];
        float h = ms[M20] * ms[M32] - ms[M22] * ms[M30];
        float i = ms[M20] * ms[M33] - ms[M23] * ms[M30];
        float j = ms[M21] * ms[M32] - ms[M22] * ms[M31];
        float k = ms[M21] * ms[M33] - ms[M23] * ms[M31];
        float l = ms[M22] * ms[M33] - ms[M23] * ms[M32];
        float det = a * l - b * k + c * j + d * i - e * h + f * g;
        det = 1.0f / det;
        float imsM00 = ( ms[M11] * l - ms[M12] * k + ms[M13] * j) * det;
        float imsM01 = (-ms[M01] * l + ms[M02] * k - ms[M03] * j) * det;
        float imsM02 = ( ms[M31] * f - ms[M32] * e + ms[M33] * d) * det;
        float imsM03 = (-ms[M21] * f + ms[M22] * e - ms[M23] * d) * det;
        float imsM10 = (-ms[M10] * l + ms[M12] * i - ms[M13] * h) * det;
        float imsM11 = ( ms[M00] * l - ms[M02] * i + ms[M03] * h) * det;
        float imsM12 = (-ms[M30] * f + ms[M32] * c - ms[M33] * b) * det;
        float imsM13 = ( ms[M20] * f - ms[M22] * c + ms[M23] * b) * det;
        float imsM20 = ( ms[M10] * k - ms[M11] * i + ms[M13] * g) * det;
        float imsM21 = (-ms[M00] * k + ms[M01] * i - ms[M03] * g) * det;
        float imsM22 = ( ms[M30] * e - ms[M31] * c + ms[M33] * a) * det;
        float imsM23 = (-ms[M20] * e + ms[M21] * c - ms[M23] * a) * det;
        float imsM30 = (-ms[M10] * j + ms[M11] * h - ms[M12] * g) * det;
        float imsM31 = ( ms[M00] * j - ms[M01] * h + ms[M02] * g) * det;
        float imsM32 = (-ms[M30] * d + ms[M31] * b - ms[M32] * a) * det;
        float imsM33 = ( ms[M20] * d - ms[M21] * b + ms[M22] * a) * det;
        float ndcX = (winX-viewport[0])/viewport[2]*2.0f-1.0f;
        float ndcY = (winY-viewport[1])/viewport[3]*2.0f-1.0f;
        float ndcZ = winZ+winZ-1.0f;
        dest.x = imsM00 * ndcX + imsM10 * ndcY + imsM20 * ndcZ + imsM30;
        dest.y = imsM01 * ndcX + imsM11 * ndcY + imsM21 * ndcZ + imsM31;
        dest.z = imsM02 * ndcX + imsM12 * ndcY + imsM22 * ndcZ + imsM32;
        dest.w = imsM03 * ndcX + imsM13 * ndcY + imsM23 * ndcZ + imsM33;
        dest.div(dest.w);
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
     * As a necessary computation step for unprojecting, this method computes the inverse of <code>this</code> matrix.
     * In order to avoid computing the matrix inverse with every invocation, the inverse of <code>this</code> matrix can be built
     * once outside using {@link #invert(Matrix4f)} and then the method {@link #unprojectInv(float, float, float, int[], Vector4f) unprojectInv()} can be invoked on it.
     * 
     * @see #unprojectInv(float, float, float, int[], Vector3f)
     * @see #invert(Matrix4f)
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
    public Vector3f unproject(float winX, float winY, float winZ, int[] viewport, Vector3f dest) {
        float a = ms[M00] * ms[M11] - ms[M01] * ms[M10];
        float b = ms[M00] * ms[M12] - ms[M02] * ms[M10];
        float c = ms[M00] * ms[M13] - ms[M03] * ms[M10];
        float d = ms[M01] * ms[M12] - ms[M02] * ms[M11];
        float e = ms[M01] * ms[M13] - ms[M03] * ms[M11];
        float f = ms[M02] * ms[M13] - ms[M03] * ms[M12];
        float g = ms[M20] * ms[M31] - ms[M21] * ms[M30];
        float h = ms[M20] * ms[M32] - ms[M22] * ms[M30];
        float i = ms[M20] * ms[M33] - ms[M23] * ms[M30];
        float j = ms[M21] * ms[M32] - ms[M22] * ms[M31];
        float k = ms[M21] * ms[M33] - ms[M23] * ms[M31];
        float l = ms[M22] * ms[M33] - ms[M23] * ms[M32];
        float det = a * l - b * k + c * j + d * i - e * h + f * g;
        det = 1.0f / det;
        float imsM00 = ( ms[M11] * l - ms[M12] * k + ms[M13] * j) * det;
        float imsM01 = (-ms[M01] * l + ms[M02] * k - ms[M03] * j) * det;
        float imsM02 = ( ms[M31] * f - ms[M32] * e + ms[M33] * d) * det;
        float imsM03 = (-ms[M21] * f + ms[M22] * e - ms[M23] * d) * det;
        float imsM10 = (-ms[M10] * l + ms[M12] * i - ms[M13] * h) * det;
        float imsM11 = ( ms[M00] * l - ms[M02] * i + ms[M03] * h) * det;
        float imsM12 = (-ms[M30] * f + ms[M32] * c - ms[M33] * b) * det;
        float imsM13 = ( ms[M20] * f - ms[M22] * c + ms[M23] * b) * det;
        float imsM20 = ( ms[M10] * k - ms[M11] * i + ms[M13] * g) * det;
        float imsM21 = (-ms[M00] * k + ms[M01] * i - ms[M03] * g) * det;
        float imsM22 = ( ms[M30] * e - ms[M31] * c + ms[M33] * a) * det;
        float imsM23 = (-ms[M20] * e + ms[M21] * c - ms[M23] * a) * det;
        float imsM30 = (-ms[M10] * j + ms[M11] * h - ms[M12] * g) * det;
        float imsM31 = ( ms[M00] * j - ms[M01] * h + ms[M02] * g) * det;
        float imsM32 = (-ms[M30] * d + ms[M31] * b - ms[M32] * a) * det;
        float imsM33 = ( ms[M20] * d - ms[M21] * b + ms[M22] * a) * det;
        float ndcX = (winX-viewport[0])/viewport[2]*2.0f-1.0f;
        float ndcY = (winY-viewport[1])/viewport[3]*2.0f-1.0f;
        float ndcZ = winZ+winZ-1.0f;
        dest.x = imsM00 * ndcX + imsM10 * ndcY + imsM20 * ndcZ + imsM30;
        dest.y = imsM01 * ndcX + imsM11 * ndcY + imsM21 * ndcZ + imsM31;
        dest.z = imsM02 * ndcX + imsM12 * ndcY + imsM22 * ndcZ + imsM32;
        float w = imsM03 * ndcX + imsM13 * ndcY + imsM23 * ndcZ + imsM33;
        dest.div(w);
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
     * As a necessary computation step for unprojecting, this method computes the inverse of <code>this</code> matrix.
     * In order to avoid computing the matrix inverse with every invocation, the inverse of <code>this</code> matrix can be built
     * once outside using {@link #invert(Matrix4f)} and then the method {@link #unprojectInv(float, float, float, int[], Vector4f) unprojectInv()} can be invoked on it.
     * 
     * @see #unprojectInv(float, float, float, int[], Vector4f)
     * @see #unproject(float, float, float, int[], Vector4f)
     * @see #invert(Matrix4f)
     * 
     * @param winCoords
     *          the window coordinates to unproject
     * @param viewport
     *          the viewport described by <tt>[x, y, width, height]</tt>
     * @param dest
     *          will hold the unprojected position
     * @return dest
     */
    public Vector4f unproject(Vector3f winCoords, int[] viewport, Vector4f dest) {
        return unproject(winCoords.x, winCoords.y, winCoords.z, viewport, dest);
    }

    /**
     * Unproject the given window coordinates <code>winCoords</code> by <code>this</code> matrix using the specified viewport.
     * <p>
     * This method first converts the given window coordinates to normalized device coordinates in the range <tt>[-1..1]</tt>
     * and then transforms those NDC coordinates by the inverse of <code>this</code> matrix.  
     * <p>
     * The depth range of <tt>winCoords.z</tt> is assumed to be <tt>[0..1]</tt>, which is also the OpenGL default.
     * <p>
     * As a necessary computation step for unprojecting, this method computes the inverse of <code>this</code> matrix.
     * In order to avoid computing the matrix inverse with every invocation, the inverse of <code>this</code> matrix can be built
     * once outside using {@link #invert(Matrix4f)} and then the method {@link #unprojectInv(float, float, float, int[], Vector3f) unprojectInv()} can be invoked on it.
     * 
     * @see #unprojectInv(float, float, float, int[], Vector3f)
     * @see #unproject(float, float, float, int[], Vector3f)
     * @see #invert(Matrix4f)
     * 
     * @param winCoords
     *          the window coordinates to unproject
     * @param viewport
     *          the viewport described by <tt>[x, y, width, height]</tt>
     * @param dest
     *          will hold the unprojected position
     * @return dest
     */
    public Vector3f unproject(Vector3f winCoords, int[] viewport, Vector3f dest) {
        return unproject(winCoords.x, winCoords.y, winCoords.z, viewport, dest);
    }

    /**
     * Unproject the given window coordinates <code>winCoords</code> by <code>this</code> matrix using the specified viewport.
     * <p>
     * This method differs from {@link #unproject(Vector3f, int[], Vector4f) unproject()} 
     * in that it assumes that <code>this</code> is already the inverse matrix of the original projection matrix.
     * It exists to avoid recomputing the matrix inverse with every invocation.
     * <p>
     * The depth range of <tt>winCoords.z</tt> is assumed to be <tt>[0..1]</tt>, which is also the OpenGL default.
     * <p>
     * This method reads the four viewport parameters from the current int[]'s {@link Buffer#position() position}
     * and does not modify the buffer's position.
     * 
     * @see #unproject(Vector3f, int[], Vector4f)
     * 
     * @param winCoords
     *          the window coordinates to unproject
     * @param viewport
     *          the viewport described by <tt>[x, y, width, height]</tt>
     * @param dest
     *          will hold the unprojected position
     * @return dest
     */
    public Vector4f unprojectInv(Vector3f winCoords, int[] viewport, Vector4f dest) {
        return unprojectInv(winCoords.x, winCoords.y, winCoords.z, viewport, dest);
    }

    /**
     * Unproject the given window coordinates <tt>(winX, winY, winZ)</tt> by <code>this</code> matrix using the specified viewport.
     * <p>
     * This method differs from {@link #unproject(float, float, float, int[], Vector4f) unproject()} 
     * in that it assumes that <code>this</code> is already the inverse matrix of the original projection matrix.
     * It exists to avoid recomputing the matrix inverse with every invocation.
     * <p>
     * The depth range of <tt>winZ</tt> is assumed to be <tt>[0..1]</tt>, which is also the OpenGL default.
     * 
     * @see #unproject(float, float, float, int[], Vector4f)
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
    public Vector4f unprojectInv(float winX, float winY, float winZ, int[] viewport, Vector4f dest) {
        float ndcX = (winX-viewport[0])/viewport[2]*2.0f-1.0f;
        float ndcY = (winY-viewport[1])/viewport[3]*2.0f-1.0f;
        float ndcZ = winZ+winZ-1.0f;
        dest.x = ms[M00] * ndcX + ms[M10] * ndcY + ms[M20] * ndcZ + ms[M30];
        dest.y = ms[M01] * ndcX + ms[M11] * ndcY + ms[M21] * ndcZ + ms[M31];
        dest.z = ms[M02] * ndcX + ms[M12] * ndcY + ms[M22] * ndcZ + ms[M32];
        dest.w = ms[M03] * ndcX + ms[M13] * ndcY + ms[M23] * ndcZ + ms[M33];
        dest.div(dest.w);
        return dest;
    }

    /**
     * Unproject the given window coordinates <code>winCoords</code> by <code>this</code> matrix using the specified viewport.
     * <p>
     * This method differs from {@link #unproject(Vector3f, int[], Vector3f) unproject()} 
     * in that it assumes that <code>this</code> is already the inverse matrix of the original projection matrix.
     * It exists to avoid recomputing the matrix inverse with every invocation.
     * <p>
     * The depth range of <tt>winCoords.z</tt> is assumed to be <tt>[0..1]</tt>, which is also the OpenGL default.
     * 
     * @see #unproject(Vector3f, int[], Vector3f)
     * 
     * @param winCoords
     *          the window coordinates to unproject
     * @param viewport
     *          the viewport described by <tt>[x, y, width, height]</tt>
     * @param dest
     *          will hold the unprojected position
     * @return dest
     */
    public Vector3f unprojectInv(Vector3f winCoords, int[] viewport, Vector3f dest) {
        return unprojectInv(winCoords.x, winCoords.y, winCoords.z, viewport, dest);
    }

    /**
     * Unproject the given window coordinates <tt>(winX, winY, winZ)</tt> by <code>this</code> matrix using the specified viewport.
     * <p>
     * This method differs from {@link #unproject(float, float, float, int[], Vector3f) unproject()} 
     * in that it assumes that <code>this</code> is already the inverse matrix of the original projection matrix.
     * It exists to avoid recomputing the matrix inverse with every invocation.
     * <p>
     * The depth range of <tt>winZ</tt> is assumed to be <tt>[0..1]</tt>, which is also the OpenGL default.
     * 
     * @see #unproject(float, float, float, int[], Vector3f)
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
    public Vector3f unprojectInv(float winX, float winY, float winZ, int[] viewport, Vector3f dest) {
        float ndcX = (winX-viewport[0])/viewport[2]*2.0f-1.0f;
        float ndcY = (winY-viewport[1])/viewport[3]*2.0f-1.0f;
        float ndcZ = winZ+winZ-1.0f;
        dest.x = ms[M00] * ndcX + ms[M10] * ndcY + ms[M20] * ndcZ + ms[M30];
        dest.y = ms[M01] * ndcX + ms[M11] * ndcY + ms[M21] * ndcZ + ms[M31];
        dest.z = ms[M02] * ndcX + ms[M12] * ndcY + ms[M22] * ndcZ + ms[M32];
        float w = ms[M03] * ndcX + ms[M13] * ndcY + ms[M23] * ndcZ + ms[M33];
        dest.div(w);
        return dest;
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
     * @return winCoordsDest
     */
    public Vector4f project(float x, float y, float z, int[] viewport, Vector4f winCoordsDest) {
        winCoordsDest.x = ms[M00] * x + ms[M10] * y + ms[M20] * z + ms[M30];
        winCoordsDest.y = ms[M01] * x + ms[M11] * y + ms[M21] * z + ms[M31];
        winCoordsDest.z = ms[M02] * x + ms[M12] * y + ms[M22] * z + ms[M32];
        winCoordsDest.w = ms[M03] * x + ms[M13] * y + ms[M23] * z + ms[M33];
        winCoordsDest.div(winCoordsDest.w);
        winCoordsDest.x = (winCoordsDest.x*0.5f+0.5f) * viewport[2] + viewport[0];
        winCoordsDest.y = (winCoordsDest.y*0.5f+0.5f) * viewport[3] + viewport[1];
        winCoordsDest.z = (1.0f+winCoordsDest.z)*0.5f;
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
    public Vector3f project(float x, float y, float z, int[] viewport, Vector3f winCoordsDest) {
        winCoordsDest.x = ms[M00] * x + ms[M10] * y + ms[M20] * z + ms[M30];
        winCoordsDest.y = ms[M01] * x + ms[M11] * y + ms[M21] * z + ms[M31];
        winCoordsDest.z = ms[M02] * x + ms[M12] * y + ms[M22] * z + ms[M32];
        float w = ms[M03] * x + ms[M13] * y + ms[M23] * z + ms[M33];
        winCoordsDest.div(w);
        winCoordsDest.x = (winCoordsDest.x*0.5f+0.5f) * viewport[2] + viewport[0];
        winCoordsDest.y = (winCoordsDest.y*0.5f+0.5f) * viewport[3] + viewport[1];
        winCoordsDest.z = (1.0f+winCoordsDest.z)*0.5f;
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
     * The depth range of the returned <code>winCoordsDest.z</code> will be <tt>[0..1]</tt>, which is also the OpenGL default.  
     * 
     * @see #project(float, float, float, int[], Vector4f)
     * 
     * @param position
     *          the position to project into window coordinates
     * @param viewport
     *          the viewport described by <tt>[x, y, width, height]</tt>
     * @param winCoordsDest
     *          will hold the projected window coordinates
     * @return winCoordsDest
     */
    public Vector4f project(Vector3f position, int[] viewport, Vector4f winCoordsDest) {
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
     * @see #project(float, float, float, int[], Vector4f)
     * 
     * @param position
     *          the position to project into window coordinates
     * @param viewport
     *          the viewport described by <tt>[x, y, width, height]</tt>
     * @param winCoordsDest
     *          will hold the projected window coordinates
     * @return winCoordsDest
     */
    public Vector3f project(Vector3f position, int[] viewport, Vector3f winCoordsDest) {
        return project(position.x, position.y, position.z, viewport, winCoordsDest);
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
    public Matrix4f reflect(float a, float b, float c, float d, Matrix4f dest) {
        float da = a + a, db = b + b, dc = c + c, dd = d + d;
        float rn00 = 1.0f - da * a;
        float rn01 = -da * b;
        float rn02 = -da * c;
        float rn10 = -db * a;
        float rn11 = 1.0f - db * b;
        float rn12 = -db * c;
        float rn20 = -dc * a;
        float rn21 = -dc * b;
        float rn22 = 1.0f - dc * c;
        float rn30 = -dd * a;
        float rn31 = -dd * b;
        float rn32 = -dd * c;

        // matrix multiplication
        dest.ms[M30] = ms[M00] * rn30 + ms[M10] * rn31 + ms[M20] * rn32 + ms[M30];
        dest.ms[M31] = ms[M01] * rn30 + ms[M11] * rn31 + ms[M21] * rn32 + ms[M31];
        dest.ms[M32] = ms[M02] * rn30 + ms[M12] * rn31 + ms[M22] * rn32 + ms[M32];
        dest.ms[M33] = ms[M03] * rn30 + ms[M13] * rn31 + ms[M23] * rn32 + ms[M33];
        float nn00 = ms[M00] * rn00 + ms[M10] * rn01 + ms[M20] * rn02;
        float nn01 = ms[M01] * rn00 + ms[M11] * rn01 + ms[M21] * rn02;
        float nn02 = ms[M02] * rn00 + ms[M12] * rn01 + ms[M22] * rn02;
        float nn03 = ms[M03] * rn00 + ms[M13] * rn01 + ms[M23] * rn02;
        float nn10 = ms[M00] * rn10 + ms[M10] * rn11 + ms[M20] * rn12;
        float nn11 = ms[M01] * rn10 + ms[M11] * rn11 + ms[M21] * rn12;
        float nn12 = ms[M02] * rn10 + ms[M12] * rn11 + ms[M22] * rn12;
        float nn13 = ms[M03] * rn10 + ms[M13] * rn11 + ms[M23] * rn12;
        dest.ms[M20] = ms[M00] * rn20 + ms[M10] * rn21 + ms[M20] * rn22;
        dest.ms[M21] = ms[M01] * rn20 + ms[M11] * rn21 + ms[M21] * rn22;
        dest.ms[M22] = ms[M02] * rn20 + ms[M12] * rn21 + ms[M22] * rn22;
        dest.ms[M23] = ms[M03] * rn20 + ms[M13] * rn21 + ms[M23] * rn22;
        dest.ms[M00] = nn00;
        dest.ms[M01] = nn01;
        dest.ms[M02] = nn02;
        dest.ms[M03] = nn03;
        dest.ms[M10] = nn10;
        dest.ms[M11] = nn11;
        dest.ms[M12] = nn12;
        dest.ms[M13] = nn13;

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
    public Matrix4f reflect(float a, float b, float c, float d) {
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
    public Matrix4f reflect(float nx, float ny, float nz, float px, float py, float pz) {
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
    public Matrix4f reflect(float nx, float ny, float nz, float px, float py, float pz, Matrix4f dest) {
        float invLength = 1.0f / (float) Math.sqrt(nx * nx + ny * ny + nz * nz);
        float nnx = nx * invLength;
        float nny = ny * invLength;
        float nnz = nz * invLength;
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
    public Matrix4f reflect(Vector3f normal, Vector3f point) {
        return reflect(normal.x, normal.y, normal.z, point.x, point.y, point.z);
    }

    /**
     * Apply a mirror/reflection transformation to this matrix that reflects about a plane
     * specified via the plane orientation and a point on the plane.
     * <p>
     * This method can be used to build a reflection transformation based on the orientation of a mirror object in the scene.
     * It is assumed that the default mirror plane's normal is <tt>(0, 0, 1)</tt>. So, if the given {@link Quaternionf} is
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
    public Matrix4f reflect(Quaternionf orientation, Vector3f point) {
        return reflect(orientation, point, this);
    }

    /**
     * Apply a mirror/reflection transformation to this matrix that reflects about a plane
     * specified via the plane orientation and a point on the plane, and store the result in <code>dest</code>.
     * <p>
     * This method can be used to build a reflection transformation based on the orientation of a mirror object in the scene.
     * It is assumed that the default mirror plane's normal is <tt>(0, 0, 1)</tt>. So, if the given {@link Quaternionf} is
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
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Matrix4f reflect(Quaternionf orientation, Vector3f point, Matrix4f dest) {
        double num1 = orientation.x + orientation.x;
        double num2 = orientation.y + orientation.y;
        double num3 = orientation.z + orientation.z;
        float normalX = (float) (orientation.x * num3 + orientation.w * num2);
        float normalY = (float) (orientation.y * num3 - orientation.w * num1);
        float normalZ = (float) (1.0 - (orientation.x * num1 + orientation.y * num2));
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
    public Matrix4f reflect(Vector3f normal, Vector3f point, Matrix4f dest) {
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
    public Matrix4f reflection(float a, float b, float c, float d) {
        float da = a + a, db = b + b, dc = c + c, dd = d + d;
        ms[M00] = 1.0f - da * a;
        ms[M01] = -da * b;
        ms[M02] = -da * c;
        ms[M03] = 0.0f;
        ms[M10] = -db * a;
        ms[M11] = 1.0f - db * b;
        ms[M12] = -db * c;
        ms[M13] = 0.0f;
        ms[M20] = -dc * a;
        ms[M21] = -dc * b;
        ms[M22] = 1.0f - dc * c;
        ms[M23] = 0.0f;
        ms[M30] = -dd * a;
        ms[M31] = -dd * b;
        ms[M32] = -dd * c;
        ms[M33] = 1.0f;
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
    public Matrix4f reflection(float nx, float ny, float nz, float px, float py, float pz) {
        float invLength = 1.0f / (float) Math.sqrt(nx * nx + ny * ny + nz * nz);
        float nnx = nx * invLength;
        float nny = ny * invLength;
        float nnz = nz * invLength;
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
    public Matrix4f reflection(Vector3f normal, Vector3f point) {
        return reflection(normal.x, normal.y, normal.z, point.x, point.y, point.z);
    }

    /**
     * Set this matrix to a mirror/reflection transformation that reflects about a plane
     * specified via the plane orientation and a point on the plane.
     * <p>
     * This method can be used to build a reflection transformation based on the orientation of a mirror object in the scene.
     * It is assumed that the default mirror plane's normal is <tt>(0, 0, 1)</tt>. So, if the given {@link Quaternionf} is
     * the identity (does not apply any additional rotation), the reflection plane will be <tt>z=0</tt>, offset by the given <code>point</code>.
     * 
     * @param orientation
     *          the plane orientation
     * @param point
     *          a point on the plane
     * @return this
     */
    public Matrix4f reflection(Quaternionf orientation, Vector3f point) {
        double num1 = orientation.x + orientation.x;
        double num2 = orientation.y + orientation.y;
        double num3 = orientation.z + orientation.z;
        float normalX = (float) (orientation.x * num3 + orientation.w * num2);
        float normalY = (float) (orientation.y * num3 - orientation.w * num1);
        float normalZ = (float) (1.0 - (orientation.x * num1 + orientation.y * num2));
        return reflection(normalX, normalY, normalZ, point.x, point.y, point.z);
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
    public Vector4f getRow(int row, Vector4f dest) throws IndexOutOfBoundsException {
        switch (row) {
        case 0:
            dest.x = ms[M00];
            dest.y = ms[M10];
            dest.z = ms[M20];
            dest.w = ms[M30];
            break;
        case 1:
            dest.x = ms[M01];
            dest.y = ms[M11];
            dest.z = ms[M21];
            dest.w = ms[M31];
            break;
        case 2:
            dest.x = ms[M02];
            dest.y = ms[M12];
            dest.z = ms[M22];
            dest.w = ms[M32];
            break;
        case 3:
            dest.x = ms[M03];
            dest.y = ms[M13];
            dest.z = ms[M23];
            dest.w = ms[M33];
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
    public Vector4f getColumn(int column, Vector4f dest) throws IndexOutOfBoundsException {
        switch (column) {
        case 0:
            dest.x = ms[M00];
            dest.y = ms[M01];
            dest.z = ms[M02];
            dest.w = ms[M03];
            break;
        case 1:
            dest.x = ms[M10];
            dest.y = ms[M11];
            dest.z = ms[M12];
            dest.w = ms[M13];
            break;
        case 2:
            dest.x = ms[M20];
            dest.y = ms[M21];
            dest.z = ms[M22];
            dest.w = ms[M23];
            break;
        case 3:
            dest.x = ms[M30];
            dest.y = ms[M31];
            dest.z = ms[M32];
            dest.w = ms[M32];
            break;
        default:
            throw new IndexOutOfBoundsException();
        }
        return dest;
    }

    /**
     * Compute a normal matrix from the upper left 3x3 submatrix of <code>this</code>
     * and store it into the upper left 3x3 submatrix of <code>this</code>.
     * All other values of <code>this</code> will be set to {@link #identity() identity}.
     * <p>
     * The normal matrix of <tt>m</tt> is the transpose of the inverse of <tt>m</tt>.
     * <p>
     * Please note that, if <code>this</code> is an orthogonal matrix or a matrix whose columns are orthogonal vectors, 
     * then this method <i>need not</i> be invoked, since in that case <code>this</code> itself is its normal matrix.
     * In that case, use {@link #set3x3(Matrix4f)} to set a given Matrix4f to only the upper left 3x3 submatrix
     * of this matrix.
     * 
     * @see #set3x3(Matrix4f)
     * 
     * @return this
     */
    public Matrix4f normal() {
        return normal(this);
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
     * In that case, use {@link #set3x3(Matrix4f)} to set a given Matrix4f to only the upper left 3x3 submatrix
     * of this matrix.
     * 
     * @see #set3x3(Matrix4f)
     * 
     * @param dest
     *             will hold the result
     * @return dest
     */
    public Matrix4f normal(Matrix4f dest) {
        float det = determinant3x3();
        float s = 1.0f / det;
        /* Invert and transpose in one go */
        dest.set((ms[M11] * ms[M22] - ms[M21] * ms[M12]) * s,
                 (ms[M20] * ms[M12] - ms[M10] * ms[M22]) * s,
                 (ms[M10] * ms[M21] - ms[M20] * ms[M11]) * s,
                 0.0f,
                 (ms[M21] * ms[M02] - ms[M01] * ms[M22]) * s,
                 (ms[M00] * ms[M22] - ms[M20] * ms[M02]) * s,
                 (ms[M20] * ms[M01] - ms[M00] * ms[M21]) * s,
                 0.0f,
                 (ms[M01] * ms[M12] - ms[M11] * ms[M02]) * s,
                 (ms[M10] * ms[M02] - ms[M00] * ms[M12]) * s,
                 (ms[M00] * ms[M11] - ms[M10] * ms[M01]) * s,
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
     * In that case, use {@link Matrix3f#set(Matrix4f)} to set a given Matrix3f to only the upper left 3x3 submatrix
     * of this matrix.
     * 
     * @see Matrix3f#set(Matrix4f)
     * @see #get3x3(Matrix3f)
     * 
     * @param dest
     *             will hold the result
     * @return dest
     */
    public Matrix3f normal(Matrix3f dest) {
        float det = determinant3x3();
        float s = 1.0f / det;
        /* Invert and transpose in one go */
        dest.ms[Matrix3f.M00] = (ms[M11] * ms[M22] - ms[M21] * ms[M12]) * s;
        dest.ms[Matrix3f.M01] = (ms[M20] * ms[M12] - ms[M10] * ms[M22]) * s;
        dest.ms[Matrix3f.M02] = (ms[M10] * ms[M21] - ms[M20] * ms[M11]) * s;
        dest.ms[Matrix3f.M10] = (ms[M21] * ms[M02] - ms[M01] * ms[M22]) * s;
        dest.ms[Matrix3f.M11] = (ms[M00] * ms[M22] - ms[M20] * ms[M02]) * s;
        dest.ms[Matrix3f.M12] = (ms[M20] * ms[M01] - ms[M00] * ms[M21]) * s;
        dest.ms[Matrix3f.M20] = (ms[M01] * ms[M12] - ms[M11] * ms[M02]) * s;
        dest.ms[Matrix3f.M21] = (ms[M10] * ms[M02] - ms[M00] * ms[M12]) * s;
        dest.ms[Matrix3f.M22] = (ms[M00] * ms[M11] - ms[M10] * ms[M01]) * s;
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
    public Matrix4f normalize3x3() {
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
    public Matrix4f normalize3x3(Matrix4f dest) {
        float invXlen = (float) (1.0 / Math.sqrt(ms[M00] * ms[M00] + ms[M01] * ms[M01] + ms[M02] * ms[M02]));
        float invYlen = (float) (1.0 / Math.sqrt(ms[M10] * ms[M10] + ms[M11] * ms[M11] + ms[M12] * ms[M12]));
        float invZlen = (float) (1.0 / Math.sqrt(ms[M20] * ms[M20] + ms[M21] * ms[M21] + ms[M22] * ms[M22]));
        dest.ms[M00] = ms[M00] * invXlen; dest.ms[M01] = ms[M01] * invXlen; dest.ms[M02] = ms[M02] * invXlen;
        dest.ms[M10] = ms[M10] * invYlen; dest.ms[M11] = ms[M11] * invYlen; dest.ms[M12] = ms[M12] * invYlen;
        dest.ms[M20] = ms[M20] * invZlen; dest.ms[M21] = ms[M21] * invZlen; dest.ms[M22] = ms[M22] * invZlen;
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
    public Matrix3f normalize3x3(Matrix3f dest) {
        float invXlen = (float) (1.0 / Math.sqrt(ms[M00] * ms[M00] + ms[M01] * ms[M01] + ms[M02] * ms[M02]));
        float invYlen = (float) (1.0 / Math.sqrt(ms[M10] * ms[M10] + ms[M11] * ms[M11] + ms[M12] * ms[M12]));
        float invZlen = (float) (1.0 / Math.sqrt(ms[M20] * ms[M20] + ms[M21] * ms[M21] + ms[M22] * ms[M22]));
        dest.ms[Matrix3f.M00] = ms[M00] * invXlen; dest.ms[Matrix3f.M01] = ms[M01] * invXlen; dest.ms[Matrix3f.M02] = ms[M02] * invXlen;
        dest.ms[Matrix3f.M10] = ms[M10] * invYlen; dest.ms[Matrix3f.M11] = ms[M11] * invYlen; dest.ms[Matrix3f.M12] = ms[M12] * invYlen;
        dest.ms[Matrix3f.M20] = ms[M20] * invZlen; dest.ms[Matrix3f.M21] = ms[M21] * invZlen; dest.ms[Matrix3f.M22] = ms[M22] * invZlen;
        return dest;
    }

    /**
     * Calculate a frustum plane of <code>this</code> matrix, which
     * can be a projection matrix or a combined modelview-projection matrix, and store the result
     * in the given <code>planeEquation</code>.
     * <p>
     * Generally, this method computes the frustum plane in the local frame of
     * any coordinate system that existed before <code>this</code>
     * transformation was applied to it in order to yield homogeneous clipping space.
     * <p>
     * The frustum plane will be given in the form of a general plane equation:
     * <tt>a*x + b*y + c*z + d = 0</tt>, where the given {@link Vector4f} components will
     * hold the <tt>(a, b, c, d)</tt> values of the equation.
     * <p>
     * The plane normal, which is <tt>(a, b, c)</tt>, is directed "inwards" of the frustum.
     * Any plane/point test using <tt>a*x + b*y + c*z + d</tt> therefore will yield a result greater than zero
     * if the point is within the frustum (i.e. at the <i>positive</i> side of the frustum plane).
     * <p>
     * Reference: <a href="http://gamedevs.org/uploads/fast-extraction-viewing-frustum-planes-from-world-view-projection-matrix.pdf">
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
    public Vector4f frustumPlane(int plane, Vector4f planeEquation) {
        switch (plane) {
        case PLANE_NX:
            planeEquation.set(ms[M03] + ms[M00], ms[M13] + ms[M10], ms[M23] + ms[M20], ms[M33] + ms[M30]).normalize3();
            break;
        case PLANE_PX:
            planeEquation.set(ms[M03] - ms[M00], ms[M13] - ms[M10], ms[M23] - ms[M20], ms[M33] - ms[M30]).normalize3();
            break;
        case PLANE_NY:
            planeEquation.set(ms[M03] + ms[M01], ms[M13] + ms[M11], ms[M23] + ms[M21], ms[M33] + ms[M31]).normalize3();
            break;
        case PLANE_PY:
            planeEquation.set(ms[M03] - ms[M01], ms[M13] - ms[M11], ms[M23] - ms[M21], ms[M33] - ms[M31]).normalize3();
            break;
        case PLANE_NZ:
            planeEquation.set(ms[M03] + ms[M02], ms[M13] + ms[M12], ms[M23] + ms[M22], ms[M33] + ms[M32]).normalize3();
            break;
        case PLANE_PZ:
            planeEquation.set(ms[M03] - ms[M02], ms[M13] - ms[M12], ms[M23] - ms[M22], ms[M33] - ms[M32]).normalize3();
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
     * Reference: <a href="http://gamedevs.org/uploads/fast-extraction-viewing-frustum-planes-from-world-view-projection-matrix.pdf">
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
    public Vector3f frustumCorner(int corner, Vector3f point) {
        float d1, d2, d3;
        float n1x, n1y, n1z, n2x, n2y, n2z, n3x, n3y, n3z;
        switch (corner) {
        case CORNER_NXNYNZ: // left, bottom, near
            n1x = ms[M03] + ms[M00]; n1y = ms[M13] + ms[M10]; n1z = ms[M23] + ms[M20]; d1 = ms[M33] + ms[M30]; // left
            n2x = ms[M03] + ms[M01]; n2y = ms[M13] + ms[M11]; n2z = ms[M23] + ms[M21]; d2 = ms[M33] + ms[M31]; // bottom
            n3x = ms[M03] + ms[M02]; n3y = ms[M13] + ms[M12]; n3z = ms[M23] + ms[M22]; d3 = ms[M33] + ms[M32]; // near
            break;
        case CORNER_PXNYNZ: // right, bottom, near
            n1x = ms[M03] - ms[M00]; n1y = ms[M13] - ms[M10]; n1z = ms[M23] - ms[M20]; d1 = ms[M33] - ms[M30]; // right
            n2x = ms[M03] + ms[M01]; n2y = ms[M13] + ms[M11]; n2z = ms[M23] + ms[M21]; d2 = ms[M33] + ms[M31]; // bottom
            n3x = ms[M03] + ms[M02]; n3y = ms[M13] + ms[M12]; n3z = ms[M23] + ms[M22]; d3 = ms[M33] + ms[M32]; // near
            break;
        case CORNER_PXPYNZ: // right, top, near
            n1x = ms[M03] - ms[M00]; n1y = ms[M13] - ms[M10]; n1z = ms[M23] - ms[M20]; d1 = ms[M33] - ms[M30]; // right
            n2x = ms[M03] - ms[M01]; n2y = ms[M13] - ms[M11]; n2z = ms[M23] - ms[M21]; d2 = ms[M33] - ms[M31]; // top
            n3x = ms[M03] + ms[M02]; n3y = ms[M13] + ms[M12]; n3z = ms[M23] + ms[M22]; d3 = ms[M33] + ms[M32]; // near
            break;
        case CORNER_NXPYNZ: // left, top, near
            n1x = ms[M03] + ms[M00]; n1y = ms[M13] + ms[M10]; n1z = ms[M23] + ms[M20]; d1 = ms[M33] + ms[M30]; // left
            n2x = ms[M03] - ms[M01]; n2y = ms[M13] - ms[M11]; n2z = ms[M23] - ms[M21]; d2 = ms[M33] - ms[M31]; // top
            n3x = ms[M03] + ms[M02]; n3y = ms[M13] + ms[M12]; n3z = ms[M23] + ms[M22]; d3 = ms[M33] + ms[M32]; // near
            break;
        case CORNER_PXNYPZ: // right, bottom, far
            n1x = ms[M03] - ms[M00]; n1y = ms[M13] - ms[M10]; n1z = ms[M23] - ms[M20]; d1 = ms[M33] - ms[M30]; // right
            n2x = ms[M03] + ms[M01]; n2y = ms[M13] + ms[M11]; n2z = ms[M23] + ms[M21]; d2 = ms[M33] + ms[M31]; // bottom
            n3x = ms[M03] - ms[M02]; n3y = ms[M13] - ms[M12]; n3z = ms[M23] - ms[M22]; d3 = ms[M33] - ms[M32]; // far
            break;
        case CORNER_NXNYPZ: // left, bottom, far
            n1x = ms[M03] + ms[M00]; n1y = ms[M13] + ms[M10]; n1z = ms[M23] + ms[M20]; d1 = ms[M33] + ms[M30]; // left
            n2x = ms[M03] + ms[M01]; n2y = ms[M13] + ms[M11]; n2z = ms[M23] + ms[M21]; d2 = ms[M33] + ms[M31]; // bottom
            n3x = ms[M03] - ms[M02]; n3y = ms[M13] - ms[M12]; n3z = ms[M23] - ms[M22]; d3 = ms[M33] - ms[M32]; // far
            break;
        case CORNER_NXPYPZ: // left, top, far
            n1x = ms[M03] + ms[M00]; n1y = ms[M13] + ms[M10]; n1z = ms[M23] + ms[M20]; d1 = ms[M33] + ms[M30]; // left
            n2x = ms[M03] - ms[M01]; n2y = ms[M13] - ms[M11]; n2z = ms[M23] - ms[M21]; d2 = ms[M33] - ms[M31]; // top
            n3x = ms[M03] - ms[M02]; n3y = ms[M13] - ms[M12]; n3z = ms[M23] - ms[M22]; d3 = ms[M33] - ms[M32]; // far
            break;
        case CORNER_PXPYPZ: // right, top, far
            n1x = ms[M03] - ms[M00]; n1y = ms[M13] - ms[M10]; n1z = ms[M23] - ms[M20]; d1 = ms[M33] - ms[M30]; // right
            n2x = ms[M03] - ms[M01]; n2y = ms[M13] - ms[M11]; n2z = ms[M23] - ms[M21]; d2 = ms[M33] - ms[M31]; // top
            n3x = ms[M03] - ms[M02]; n3y = ms[M13] - ms[M12]; n3z = ms[M23] - ms[M22]; d3 = ms[M33] - ms[M32]; // far
            break;
        default:
            throw new IllegalArgumentException("corner"); //$NON-NLS-1$
        }
        float c23x, c23y, c23z;
        c23x = n2y * n3z - n2z * n3y;
        c23y = n2z * n3x - n2x * n3z;
        c23z = n2x * n3y - n2y * n3x;
        float c31x, c31y, c31z;
        c31x = n3y * n1z - n3z * n1y;
        c31y = n3z * n1x - n3x * n1z;
        c31z = n3x * n1y - n3y * n1x;
        float c12x, c12y, c12z;
        c12x = n1y * n2z - n1z * n2y;
        c12y = n1z * n2x - n1x * n2z;
        c12z = n1x * n2y - n1y * n2x;
        float invDot = 1.0f / (n1x * c23x + n1y * c23y + n1z * c23z);
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
     * perspective methods, such as {@link #perspective(float, float, float, float) perspective()}
     * or {@link #frustum(float, float, float, float, float, float) frustum()}.
     * <p>
     * Generally, this method computes the origin in the local frame of
     * any coordinate system that existed before <code>this</code>
     * transformation was applied to it in order to yield homogeneous clipping space.
     * <p>
     * Reference: <a href="http://geomalgorithms.com/a05-_intersect-1.html">http://geomalgorithms.com</a>
     * <p>
     * Reference: <a href="http://gamedevs.org/uploads/fast-extraction-viewing-frustum-planes-from-world-view-projection-matrix.pdf">
     * Fast Extraction of Viewing Frustum Planes from the World-View-Projection Matrix</a>
     * 
     * @param origin
     *          will hold the origin of the coordinate system before applying <code>this</code>
     *          perspective projection transformation
     * @return origin
     */
    public Vector3f perspectiveOrigin(Vector3f origin) {
        /*
         * Simply compute the intersection point of the left, right and top frustum plane.
         */
        float d1, d2, d3;
        float n1x, n1y, n1z, n2x, n2y, n2z, n3x, n3y, n3z;
        n1x = ms[M03] + ms[M00]; n1y = ms[M13] + ms[M10]; n1z = ms[M23] + ms[M20]; d1 = ms[M33] + ms[M30]; // left
        n2x = ms[M03] - ms[M00]; n2y = ms[M13] - ms[M10]; n2z = ms[M23] - ms[M20]; d2 = ms[M33] - ms[M30]; // right
        n3x = ms[M03] - ms[M01]; n3y = ms[M13] - ms[M11]; n3z = ms[M23] - ms[M21]; d3 = ms[M33] - ms[M31]; // top
        float c23x, c23y, c23z;
        c23x = n2y * n3z - n2z * n3y;
        c23y = n2z * n3x - n2x * n3z;
        c23z = n2x * n3y - n2y * n3x;
        float c31x, c31y, c31z;
        c31x = n3y * n1z - n3z * n1y;
        c31y = n3z * n1x - n3x * n1z;
        c31z = n3x * n1y - n3y * n1x;
        float c12x, c12y, c12z;
        c12x = n1y * n2z - n1z * n2y;
        c12y = n1z * n2x - n1x * n2z;
        c12z = n1x * n2y - n1y * n2x;
        float invDot = 1.0f / (n1x * c23x + n1y * c23y + n1z * c23z);
        origin.x = (-c23x * d1 - c31x * d2 - c12x * d3) * invDot;
        origin.y = (-c23y * d1 - c31y * d2 - c12y * d3) * invDot;
        origin.z = (-c23z * d1 - c31z * d2 - c12z * d3) * invDot;
        return origin;
    }

    /**
     * Return the vertical field-of-view angle in radians of this perspective transformation matrix.
     * <p>
     * Note that this method will only work using perspective projections obtained via one of the
     * perspective methods, such as {@link #perspective(float, float, float, float) perspective()}
     * or {@link #frustum(float, float, float, float, float, float) frustum()}.
     * <p>
     * For orthogonal transformations this method will return <tt>0.0</tt>.
     * <p>
     * Reference: <a href="http://gamedevs.org/uploads/fast-extraction-viewing-frustum-planes-from-world-view-projection-matrix.pdf">
     * Fast Extraction of Viewing Frustum Planes from the World-View-Projection Matrix</a>
     * 
     * @return the vertical field-of-view angle in radians
     */
    public float perspectiveFov() {
        /*
         * Compute the angle between the bottom and top frustum plane normals.
         */
        float n1x, n1y, n1z, n2x, n2y, n2z;
        n1x = ms[M03] + ms[M01]; n1y = ms[M13] + ms[M11]; n1z = ms[M23] + ms[M21]; // bottom
        n2x = ms[M01] - ms[M03]; n2y = ms[M11] - ms[M13]; n2z = ms[M21] - ms[M23]; // top
        float n1len = (float) Math.sqrt(n1x * n1x + n1y * n1y + n1z * n1z);
        float n2len = (float) Math.sqrt(n2x * n2x + n2y * n2y + n2z * n2z);
        return (float) Math.acos((n1x * n2x + n1y * n2y + n1z * n2z) / (n1len * n2len));
    }

    /**
     * Extract the near clip plane distance from <code>this</code> perspective projection matrix.
     * <p>
     * This method only works if <code>this</code> is a perspective projection matrix, for example obtained via {@link #perspective(float, float, float, float)}.
     * 
     * @return the near clip plane distance
     */
    public float perspectiveNear() {
        return ms[M32] / (ms[M23] + ms[M22]);
    }

    /**
     * Extract the far clip plane distance from <code>this</code> perspective projection matrix.
     * <p>
     * This method only works if <code>this</code> is a perspective projection matrix, for example obtained via {@link #perspective(float, float, float, float)}.
     * 
     * @return the far clip plane distance
     */
    public float perspectiveFar() {
        return ms[M32] / (ms[M22] - ms[M23]);
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
     * and then bilinearly interpolating between them.
     * <p>
     * Reference: <a href="http://gamedevs.org/uploads/fast-extraction-viewing-frustum-planes-from-world-view-projection-matrix.pdf">
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
    public Vector3f frustumRayDir(float x, float y, Vector3f dir) {
        /*
         * This method works by first obtaining the frustum plane normals,
         * then building the cross product to obtain the corner rays,
         * and finally bilinearly interpolating to obtain the desired direction.
         * The code below uses a condense form of doing all this making use 
         * of some mathematical identities to simplify the overall expression.
         */
        float a = ms[M10] * ms[M23], b = ms[M13] * ms[M21], c = ms[M10] * ms[M21], d = ms[M11] * ms[M23], e = ms[M13] * ms[M20], f = ms[M11] * ms[M20];
        float g = ms[M03] * ms[M20], h = ms[M01] * ms[M23], i = ms[M01] * ms[M20], j = ms[M03] * ms[M21], k = ms[M00] * ms[M23], l = ms[M00] * ms[M21];
        float m = ms[M00] * ms[M13], n = ms[M03] * ms[M11], o = ms[M00] * ms[M11], p = ms[M01] * ms[M13], q = ms[M03] * ms[M10], r = ms[M01] * ms[M10];
        float m1x, m1y, m1z;
        m1x = (d + e + f - a - b - c) * (1.0f - y) + (a - b - c + d - e + f) * y;
        m1y = (j + k + l - g - h - i) * (1.0f - y) + (g - h - i + j - k + l) * y;
        m1z = (p + q + r - m - n - o) * (1.0f - y) + (m - n - o + p - q + r) * y;
        float m2x, m2y, m2z;
        m2x = (b - c - d + e + f - a) * (1.0f - y) + (a + b - c - d - e + f) * y;
        m2y = (h - i - j + k + l - g) * (1.0f - y) + (g + h - i - j - k + l) * y;
        m2z = (n - o - p + q + r - m) * (1.0f - y) + (m + n - o - p - q + r) * y;
        dir.x = m1x + (m2x - m1x) * x;
        dir.y = m1y + (m2y - m1y) * x;
        dir.z = m1z + (m2z - m1z) * x;
        dir.normalize();
        return dir;
    }

    /**
     * Obtain the direction of <tt>+Z</tt> before the transformation represented by <code>this</code> matrix is applied.
     * <p>
     * This method uses the rotation component of the upper left 3x3 submatrix to obtain the direction 
     * that is transformed to <tt>+Z</tt> by <code>this</code> matrix.
     * <p>
     * This method is equivalent to the following code:
     * <pre>
     * Matrix4f inv = new Matrix4f(this).invert();
     * inv.transformDirection(dir.set(0, 0, 1)).normalize();
     * </pre>
     * If <code>this</code> is already an orthogonal matrix, then consider using {@link #normalizedPositiveZ(Vector3f)} instead.
     * <p>
     * Reference: <a href="http://www.euclideanspace.com/maths/algebra/matrix/functions/inverse/threeD/">http://www.euclideanspace.com</a>
     * 
     * @param dir
     *          will hold the direction of <tt>+Z</tt>
     * @return dir
     */
    public Vector3f positiveZ(Vector3f dir) {
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
     * This method uses the rotation component of the upper left 3x3 submatrix to obtain the direction 
     * that is transformed to <tt>+Z</tt> by <code>this</code> matrix.
     * <p>
     * This method is equivalent to the following code:
     * <pre>
     * Matrix4f inv = new Matrix4f(this).transpose();
     * inv.transformDirection(dir.set(0, 0, 1)).normalize();
     * </pre>
     * <p>
     * Reference: <a href="http://www.euclideanspace.com/maths/algebra/matrix/functions/inverse/threeD/">http://www.euclideanspace.com</a>
     * 
     * @param dir
     *          will hold the direction of <tt>+Z</tt>
     * @return dir
     */
    public Vector3f normalizedPositiveZ(Vector3f dir) {
        dir.x = ms[M02];
        dir.y = ms[M12];
        dir.z = ms[M22];
        return dir;
    }

    /**
     * Obtain the direction of <tt>+X</tt> before the transformation represented by <code>this</code> matrix is applied.
     * <p>
     * This method uses the rotation component of the upper left 3x3 submatrix to obtain the direction 
     * that is transformed to <tt>+X</tt> by <code>this</code> matrix.
     * <p>
     * This method is equivalent to the following code:
     * <pre>
     * Matrix4f inv = new Matrix4f(this).invert();
     * inv.transformDirection(dir.set(1, 0, 0)).normalize();
     * </pre>
     * If <code>this</code> is already an orthogonal matrix, then consider using {@link #normalizedPositiveX(Vector3f)} instead.
     * <p>
     * Reference: <a href="http://www.euclideanspace.com/maths/algebra/matrix/functions/inverse/threeD/">http://www.euclideanspace.com</a>
     * 
     * @param dir
     *          will hold the direction of <tt>+X</tt>
     * @return dir
     */
    public Vector3f positiveX(Vector3f dir) {
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
     * This method uses the rotation component of the upper left 3x3 submatrix to obtain the direction 
     * that is transformed to <tt>+X</tt> by <code>this</code> matrix.
     * <p>
     * This method is equivalent to the following code:
     * <pre>
     * Matrix4f inv = new Matrix4f(this).transpose();
     * inv.transformDirection(dir.set(1, 0, 0)).normalize();
     * </pre>
     * <p>
     * Reference: <a href="http://www.euclideanspace.com/maths/algebra/matrix/functions/inverse/threeD/">http://www.euclideanspace.com</a>
     * 
     * @param dir
     *          will hold the direction of <tt>+X</tt>
     * @return dir
     */
    public Vector3f normalizedPositiveX(Vector3f dir) {
        dir.x = ms[M00];
        dir.y = ms[M10];
        dir.z = ms[M20];
        return dir;
    }

    /**
     * Obtain the direction of <tt>+Y</tt> before the transformation represented by <code>this</code> matrix is applied.
     * <p>
     * This method uses the rotation component of the upper left 3x3 submatrix to obtain the direction 
     * that is transformed to <tt>+Y</tt> by <code>this</code> matrix.
     * <p>
     * This method is equivalent to the following code:
     * <pre>
     * Matrix4f inv = new Matrix4f(this).invert();
     * inv.transformDirection(dir.set(0, 1, 0)).normalize();
     * </pre>
     * If <code>this</code> is already an orthogonal matrix, then consider using {@link #normalizedPositiveY(Vector3f)} instead.
     * <p>
     * Reference: <a href="http://www.euclideanspace.com/maths/algebra/matrix/functions/inverse/threeD/">http://www.euclideanspace.com</a>
     * 
     * @param dir
     *          will hold the direction of <tt>+Y</tt>
     * @return dir
     */
    public Vector3f positiveY(Vector3f dir) {
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
     * This method uses the rotation component of the upper left 3x3 submatrix to obtain the direction 
     * that is transformed to <tt>+Y</tt> by <code>this</code> matrix.
     * <p>
     * This method is equivalent to the following code:
     * <pre>
     * Matrix4f inv = new Matrix4f(this).transpose();
     * inv.transformDirection(dir.set(0, 1, 0)).normalize();
     * </pre>
     * <p>
     * Reference: <a href="http://www.euclideanspace.com/maths/algebra/matrix/functions/inverse/threeD/">http://www.euclideanspace.com</a>
     * 
     * @param dir
     *          will hold the direction of <tt>+Y</tt>
     * @return dir
     */
    public Vector3f normalizedPositiveY(Vector3f dir) {
        dir.x = ms[M01];
        dir.y = ms[M11];
        dir.z = ms[M21];
        return dir;
    }

    /**
     * Obtain the position that gets transformed to the origin by <code>this</code> {@link #isAffine() affine} matrix.
     * This can be used to get the position of the "camera" from a given <i>view</i> transformation matrix.
     * <p>
     * This method only works with {@link #isAffine() affine} matrices.
     * <p>
     * This method is equivalent to the following code:
     * <pre>
     * Matrix4f inv = new Matrix4f(this).invertAffine();
     * inv.transformPosition(origin.set(0, 0, 0));
     * </pre>
     * 
     * @param origin
     *          will hold the position transformed to the origin
     * @return origin
     */
    public Vector3f originAffine(Vector3f origin) {
        float a = ms[M00] * ms[M11] - ms[M01] * ms[M10];
        float b = ms[M00] * ms[M12] - ms[M02] * ms[M10];
        float d = ms[M01] * ms[M12] - ms[M02] * ms[M11];
        float g = ms[M20] * ms[M31] - ms[M21] * ms[M30];
        float h = ms[M20] * ms[M32] - ms[M22] * ms[M30];
        float j = ms[M21] * ms[M32] - ms[M22] * ms[M31];
        origin.x = -ms[M10] * j + ms[M11] * h - ms[M12] * g;
        origin.y =  ms[M00] * j - ms[M01] * h + ms[M02] * g;
        origin.z = -ms[M30] * d + ms[M31] * b - ms[M32] * a;
        return origin;
    }

    /**
     * Obtain the position that gets transformed to the origin by <code>this</code> matrix.
     * This can be used to get the position of the "camera" from a given <i>view/projection</i> transformation matrix.
     * <p>
     * This method is equivalent to the following code:
     * <pre>
     * Matrix4f inv = new Matrix4f(this).invert();
     * inv.transformPosition(origin.set(0, 0, 0));
     * </pre>
     * 
     * @param origin
     *          will hold the position transformed to the origin
     * @return origin
     */
    public Vector3f origin(Vector3f origin) {
        float a = ms[M00] * ms[M11] - ms[M01] * ms[M10];
        float b = ms[M00] * ms[M12] - ms[M02] * ms[M10];
        float c = ms[M00] * ms[M13] - ms[M03] * ms[M10];
        float d = ms[M01] * ms[M12] - ms[M02] * ms[M11];
        float e = ms[M01] * ms[M13] - ms[M03] * ms[M11];
        float f = ms[M02] * ms[M13] - ms[M03] * ms[M12];
        float g = ms[M20] * ms[M31] - ms[M21] * ms[M30];
        float h = ms[M20] * ms[M32] - ms[M22] * ms[M30];
        float i = ms[M20] * ms[M33] - ms[M23] * ms[M30];
        float j = ms[M21] * ms[M32] - ms[M22] * ms[M31];
        float k = ms[M21] * ms[M33] - ms[M23] * ms[M31];
        float l = ms[M22] * ms[M33] - ms[M23] * ms[M32];
        float det = a * l - b * k + c * j + d * i - e * h + f * g;
        float invDet = 1.0f / det;
        float nn30 = (-ms[M10] * j + ms[M11] * h - ms[M12] * g) * invDet;
        float nn31 = ( ms[M00] * j - ms[M01] * h + ms[M02] * g) * invDet;
        float nn32 = (-ms[M30] * d + ms[M31] * b - ms[M32] * a) * invDet;
        float nn33 = det / ( ms[M20] * d - ms[M21] * b + ms[M22] * a);
        float x = nn30 * nn33;
        float y = nn31 * nn33;
        float z = nn32 * nn33;
        return origin.set(x, y, z);
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
    public Matrix4f shadow(Vector4f light, float a, float b, float c, float d) {
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
    public Matrix4f shadow(Vector4f light, float a, float b, float c, float d, Matrix4f dest) {
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
    public Matrix4f shadow(float lightX, float lightY, float lightZ, float lightW, float a, float b, float c, float d) {
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
    public Matrix4f shadow(float lightX, float lightY, float lightZ, float lightW, float a, float b, float c, float d, Matrix4f dest) {
        // normalize plane
        float invPlaneLen = (float) (1.0 / Math.sqrt(a*a + b*b + c*c));
        float an = a * invPlaneLen;
        float bn = b * invPlaneLen;
        float cn = c * invPlaneLen;
        float dn = d * invPlaneLen;

        float dot = an * lightX + bn * lightY + cn * lightZ + dn * lightW;

        // compute right matrix elements
        float rn00 = dot - an * lightX;
        float rn01 = -an * lightY;
        float rn02 = -an * lightZ;
        float rn03 = -an * lightW;
        float rn10 = -bn * lightX;
        float rn11 = dot - bn * lightY;
        float rn12 = -bn * lightZ;
        float rn13 = -bn * lightW;
        float rn20 = -cn * lightX;
        float rn21 = -cn * lightY;
        float rn22 = dot - cn * lightZ;
        float rn23 = -cn * lightW;
        float rn30 = -dn * lightX;
        float rn31 = -dn * lightY;
        float rn32 = -dn * lightZ;
        float rn33 = dot - dn * lightW;

        // matrix multiplication
        float nn00 = ms[M00] * rn00 + ms[M10] * rn01 + ms[M20] * rn02 + ms[M30] * rn03;
        float nn01 = ms[M01] * rn00 + ms[M11] * rn01 + ms[M21] * rn02 + ms[M31] * rn03;
        float nn02 = ms[M02] * rn00 + ms[M12] * rn01 + ms[M22] * rn02 + ms[M32] * rn03;
        float nn03 = ms[M03] * rn00 + ms[M13] * rn01 + ms[M23] * rn02 + ms[M33] * rn03;
        float nn10 = ms[M00] * rn10 + ms[M10] * rn11 + ms[M20] * rn12 + ms[M30] * rn13;
        float nn11 = ms[M01] * rn10 + ms[M11] * rn11 + ms[M21] * rn12 + ms[M31] * rn13;
        float nn12 = ms[M02] * rn10 + ms[M12] * rn11 + ms[M22] * rn12 + ms[M32] * rn13;
        float nn13 = ms[M03] * rn10 + ms[M13] * rn11 + ms[M23] * rn12 + ms[M33] * rn13;
        float nn20 = ms[M00] * rn20 + ms[M10] * rn21 + ms[M20] * rn22 + ms[M30] * rn23;
        float nn21 = ms[M01] * rn20 + ms[M11] * rn21 + ms[M21] * rn22 + ms[M31] * rn23;
        float nn22 = ms[M02] * rn20 + ms[M12] * rn21 + ms[M22] * rn22 + ms[M32] * rn23;
        float nn23 = ms[M03] * rn20 + ms[M13] * rn21 + ms[M23] * rn22 + ms[M33] * rn23;
        dest.ms[M30] = ms[M00] * rn30 + ms[M10] * rn31 + ms[M20] * rn32 + ms[M30] * rn33;
        dest.ms[M31] = ms[M01] * rn30 + ms[M11] * rn31 + ms[M21] * rn32 + ms[M31] * rn33;
        dest.ms[M32] = ms[M02] * rn30 + ms[M12] * rn31 + ms[M22] * rn32 + ms[M32] * rn33;
        dest.ms[M33] = ms[M03] * rn30 + ms[M13] * rn31 + ms[M23] * rn32 + ms[M33] * rn33;
        dest.ms[M00] = nn00;
        dest.ms[M01] = nn01;
        dest.ms[M02] = nn02;
        dest.ms[M03] = nn03;
        dest.ms[M10] = nn10;
        dest.ms[M11] = nn11;
        dest.ms[M12] = nn12;
        dest.ms[M13] = nn13;
        dest.ms[M20] = nn20;
        dest.ms[M21] = nn21;
        dest.ms[M22] = nn22;
        dest.ms[M23] = nn23;

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
    public Matrix4f shadow(Vector4f light, Matrix4f planeTransform, Matrix4f dest) {
        // compute plane equation by transforming (y = 0)
        float a = planeTransform.ms[M10];
        float b = planeTransform.ms[M11];
        float c = planeTransform.ms[M12];
        float d = -a * planeTransform.ms[M30] - b * planeTransform.ms[M31] - c * planeTransform.ms[M32];
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
    public Matrix4f shadow(Vector4f light, Matrix4f planeTransform) {
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
    public Matrix4f shadow(float lightX, float lightY, float lightZ, float lightW, Matrix4f planeTransform, Matrix4f dest) {
        // compute plane equation by transforming (y = 0)
        float a = planeTransform.ms[M10];
        float b = planeTransform.ms[M11];
        float c = planeTransform.ms[M12];
        float d = -a * planeTransform.ms[M30] - b * planeTransform.ms[M31] - c * planeTransform.ms[M32];
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
    public Matrix4f shadow(float lightX, float lightY, float lightZ, float lightW, Matrix4f planeTransform) {
        return shadow(lightX, lightY, lightZ, lightW, planeTransform, this);
    }

    /**
     * Set this matrix to a cylindrical billboard transformation that rotates the local +Z axis of a given object with position <code>objPos</code> towards
     * a target position at <code>targetPos</code> while constraining a cylindrical rotation around the given <code>up</code> vector.
     * <p>
     * This method can be used to create the complete model transformation for a given object, including the translation of the object to
     * its position <code>objPos</code>.
     * 
     * @param objPos
     *          the position of the object to rotate towards <code>targetPos</code>
     * @param targetPos
     *          the position of the target (for example the camera) towards which to rotate the object
     * @param up
     *          the rotation axis (must be {@link Vector3f#normalize() normalized})
     * @return this
     */
    public Matrix4f billboardCylindrical(Vector3f objPos, Vector3f targetPos, Vector3f up) {
        float dirX = targetPos.x - objPos.x;
        float dirY = targetPos.y - objPos.y;
        float dirZ = targetPos.z - objPos.z;
        // left = up x dir
        float leftX = up.y * dirZ - up.z * dirY;
        float leftY = up.z * dirX - up.x * dirZ;
        float leftZ = up.x * dirY - up.y * dirX;
        // normalize left
        float invLeftLen = 1.0f / (float) Math.sqrt(leftX * leftX + leftY * leftY + leftZ * leftZ);
        leftX *= invLeftLen;
        leftY *= invLeftLen;
        leftZ *= invLeftLen;
        // recompute dir by constraining rotation around 'up'
        // dir = left x up
        dirX = leftY * up.z - leftZ * up.y;
        dirY = leftZ * up.x - leftX * up.z;
        dirZ = leftX * up.y - leftY * up.x;
        // normalize dir
        float invDirLen = 1.0f / (float) Math.sqrt(dirX * dirX + dirY * dirY + dirZ * dirZ);
        dirX *= invDirLen;
        dirY *= invDirLen;
        dirZ *= invDirLen;
        // set matrix elements
        ms[M00] = leftX;
        ms[M01] = leftY;
        ms[M02] = leftZ;
        ms[M03] = 0.0f;
        ms[M10] = up.x;
        ms[M11] = up.y;
        ms[M12] = up.z;
        ms[M13] = 0.0f;
        ms[M20] = dirX;
        ms[M21] = dirY;
        ms[M22] = dirZ;
        ms[M23] = 0.0f;
        ms[M30] = objPos.x;
        ms[M31] = objPos.y;
        ms[M32] = objPos.z;
        ms[M33] = 1.0f;
        return this;
    }

    /**
     * Set this matrix to a spherical billboard transformation that rotates the local +Z axis of a given object with position <code>objPos</code> towards
     * a target position at <code>targetPos</code>.
     * <p>
     * This method can be used to create the complete model transformation for a given object, including the translation of the object to
     * its position <code>objPos</code>.
     * <p>
     * If preserving an <i>up</i> vector is not necessary when rotating the +Z axis, then a shortest arc rotation can be obtained 
     * using {@link #billboardSpherical(Vector3f, Vector3f)}.
     * 
     * @see #billboardSpherical(Vector3f, Vector3f)
     * 
     * @param objPos
     *          the position of the object to rotate towards <code>targetPos</code>
     * @param targetPos
     *          the position of the target (for example the camera) towards which to rotate the object
     * @param up
     *          the up axis used to orient the object
     * @return this
     */
    public Matrix4f billboardSpherical(Vector3f objPos, Vector3f targetPos, Vector3f up) {
        float dirX = targetPos.x - objPos.x;
        float dirY = targetPos.y - objPos.y;
        float dirZ = targetPos.z - objPos.z;
        // normalize dir
        float invDirLen = 1.0f / (float) Math.sqrt(dirX * dirX + dirY * dirY + dirZ * dirZ);
        dirX *= invDirLen;
        dirY *= invDirLen;
        dirZ *= invDirLen;
        // left = up x dir
        float leftX = up.y * dirZ - up.z * dirY;
        float leftY = up.z * dirX - up.x * dirZ;
        float leftZ = up.x * dirY - up.y * dirX;
        // normalize left
        float invLeftLen = 1.0f / (float) Math.sqrt(leftX * leftX + leftY * leftY + leftZ * leftZ);
        leftX *= invLeftLen;
        leftY *= invLeftLen;
        leftZ *= invLeftLen;
        // up = dir x left
        float upX = dirY * leftZ - dirZ * leftY;
        float upY = dirZ * leftX - dirX * leftZ;
        float upZ = dirX * leftY - dirY * leftX;
        // set matrix elements
        ms[M00] = leftX;
        ms[M01] = leftY;
        ms[M02] = leftZ;
        ms[M03] = 0.0f;
        ms[M10] = upX;
        ms[M11] = upY;
        ms[M12] = upZ;
        ms[M13] = 0.0f;
        ms[M20] = dirX;
        ms[M21] = dirY;
        ms[M22] = dirZ;
        ms[M23] = 0.0f;
        ms[M30] = objPos.x;
        ms[M31] = objPos.y;
        ms[M32] = objPos.z;
        ms[M33] = 1.0f;
        return this;
    }

    /**
     * Set this matrix to a spherical billboard transformation that rotates the local +Z axis of a given object with position <code>objPos</code> towards
     * a target position at <code>targetPos</code> using a shortest arc rotation by not preserving any <i>up</i> vector of the object.
     * <p>
     * This method can be used to create the complete model transformation for a given object, including the translation of the object to
     * its position <code>objPos</code>.
     * <p>
     * In order to specify an <i>up</i> vector which needs to be maintained when rotating the +Z axis of the object,
     * use {@link #billboardSpherical(Vector3f, Vector3f, Vector3f)}.
     * 
     * @see #billboardSpherical(Vector3f, Vector3f, Vector3f)
     * 
     * @param objPos
     *          the position of the object to rotate towards <code>targetPos</code>
     * @param targetPos
     *          the position of the target (for example the camera) towards which to rotate the object
     * @return this
     */
    public Matrix4f billboardSpherical(Vector3f objPos, Vector3f targetPos) {
        float toDirX = targetPos.x - objPos.x;
        float toDirY = targetPos.y - objPos.y;
        float toDirZ = targetPos.z - objPos.z;
        float x = -toDirY;
        float y = toDirX;
        float w = (float) Math.sqrt(toDirX * toDirX + toDirY * toDirY + toDirZ * toDirZ) + toDirZ;
        float invNorm = (float) (1.0 / Math.sqrt(x * x + y * y + w * w));
        x *= invNorm;
        y *= invNorm;
        w *= invNorm;
        float q00 = (x + x) * x;
        float q11 = (y + y) * y;
        float q01 = (x + x) * y;
        float q03 = (x + x) * w;
        float q13 = (y + y) * w;
        ms[M00] = 1.0f - q11;
        ms[M01] = q01;
        ms[M02] = -q13;
        ms[M03] = 0.0f;
        ms[M10] = q01;
        ms[M11] = 1.0f - q00;
        ms[M12] = q03;
        ms[M13] = 0.0f;
        ms[M20] = q13;
        ms[M21] = -q03;
        ms[M22] = 1.0f - q11 - q00;
        ms[M23] = 0.0f;
        ms[M30] = objPos.x;
        ms[M31] = objPos.y;
        ms[M32] = objPos.z;
        ms[M33] = 1.0f;
        return this;
    }

    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Float.floatToIntBits(ms[M00]);
        result = prime * result + Float.floatToIntBits(ms[M01]);
        result = prime * result + Float.floatToIntBits(ms[M02]);
        result = prime * result + Float.floatToIntBits(ms[M03]);
        result = prime * result + Float.floatToIntBits(ms[M10]);
        result = prime * result + Float.floatToIntBits(ms[M11]);
        result = prime * result + Float.floatToIntBits(ms[M12]);
        result = prime * result + Float.floatToIntBits(ms[M13]);
        result = prime * result + Float.floatToIntBits(ms[M20]);
        result = prime * result + Float.floatToIntBits(ms[M21]);
        result = prime * result + Float.floatToIntBits(ms[M22]);
        result = prime * result + Float.floatToIntBits(ms[M23]);
        result = prime * result + Float.floatToIntBits(ms[M30]);
        result = prime * result + Float.floatToIntBits(ms[M31]);
        result = prime * result + Float.floatToIntBits(ms[M32]);
        result = prime * result + Float.floatToIntBits(ms[M33]);
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Matrix4f))
            return false;
        Matrix4f other = (Matrix4f) obj;
        if (Float.floatToIntBits(ms[M00]) != Float.floatToIntBits(other.ms[M00]))
            return false;
        if (Float.floatToIntBits(ms[M01]) != Float.floatToIntBits(other.ms[M01]))
            return false;
        if (Float.floatToIntBits(ms[M02]) != Float.floatToIntBits(other.ms[M02]))
            return false;
        if (Float.floatToIntBits(ms[M03]) != Float.floatToIntBits(other.ms[M03]))
            return false;
        if (Float.floatToIntBits(ms[M10]) != Float.floatToIntBits(other.ms[M10]))
            return false;
        if (Float.floatToIntBits(ms[M11]) != Float.floatToIntBits(other.ms[M11]))
            return false;
        if (Float.floatToIntBits(ms[M12]) != Float.floatToIntBits(other.ms[M12]))
            return false;
        if (Float.floatToIntBits(ms[M13]) != Float.floatToIntBits(other.ms[M13]))
            return false;
        if (Float.floatToIntBits(ms[M20]) != Float.floatToIntBits(other.ms[M20]))
            return false;
        if (Float.floatToIntBits(ms[M21]) != Float.floatToIntBits(other.ms[M21]))
            return false;
        if (Float.floatToIntBits(ms[M22]) != Float.floatToIntBits(other.ms[M22]))
            return false;
        if (Float.floatToIntBits(ms[M23]) != Float.floatToIntBits(other.ms[M23]))
            return false;
        if (Float.floatToIntBits(ms[M30]) != Float.floatToIntBits(other.ms[M30]))
            return false;
        if (Float.floatToIntBits(ms[M31]) != Float.floatToIntBits(other.ms[M31]))
            return false;
        if (Float.floatToIntBits(ms[M32]) != Float.floatToIntBits(other.ms[M32]))
            return false;
        if (Float.floatToIntBits(ms[M33]) != Float.floatToIntBits(other.ms[M33]))
            return false;
        return true;
    }

    /**
     * Apply a picking transformation to this matrix using the given window coordinates <tt>(x, y)</tt> as the pick center
     * and the given <tt>(width, height)</tt> as the size of the picking region in window coordinates, and store the result
     * in <code>dest</code>.
     * 
     * @param x
     *          the x coordinate of the picking region center in window coordinates
     * @param y
     *          the y coordinate of the picking region center in window coordinates
     * @param width
     *          the width of the picking region in window coordinates
     * @param height
     *          the height of the picking region in window coordinates
     * @param viewport
     *          the viewport described by <tt>[x, y, width, height]</tt>
     * @param dest
     *          the destination matrix, which will hold the result
     * @return dest
     */
    public Matrix4f pick(float x, float y, float width, float height, int[] viewport, Matrix4f dest) {
        float sx = viewport[2] / width;
        float sy = viewport[3] / height;
        float tx = (viewport[2] + 2.0f * (viewport[0] - x)) / width;
        float ty = (viewport[3] + 2.0f * (viewport[1] - y)) / height;
        dest.ms[M30] = ms[M00] * tx + ms[M10] * ty + ms[M30];
        dest.ms[M31] = ms[M01] * tx + ms[M11] * ty + ms[M31];
        dest.ms[M32] = ms[M02] * tx + ms[M12] * ty + ms[M32];
        dest.ms[M33] = ms[M03] * tx + ms[M13] * ty + ms[M33];
        dest.ms[M00] = ms[M00] * sx;
        dest.ms[M01] = ms[M01] * sx;
        dest.ms[M02] = ms[M02] * sx;
        dest.ms[M03] = ms[M03] * sx;
        dest.ms[M10] = ms[M10] * sy;
        dest.ms[M11] = ms[M11] * sy;
        dest.ms[M12] = ms[M12] * sy;
        dest.ms[M13] = ms[M13] * sy;
        return dest;
    }

    /**
     * Apply a picking transformation to this matrix using the given window coordinates <tt>(x, y)</tt> as the pick center
     * and the given <tt>(width, height)</tt> as the size of the picking region in window coordinates.
     * 
     * @param x
     *          the x coordinate of the picking region center in window coordinates
     * @param y
     *          the y coordinate of the picking region center in window coordinates
     * @param width
     *          the width of the picking region in window coordinates
     * @param height
     *          the height of the picking region in window coordinates
     * @param viewport
     *          the viewport described by <tt>[x, y, width, height]</tt>
     * @return this
     */
    public Matrix4f pick(float x, float y, float width, float height, int[] viewport) {
        return pick(x, y, width, height, viewport, this);
    }

    /**
     * Determine whether this matrix describes an affine transformation. This is the case iff its last row is equal to <tt>(0, 0, 0, 1)</tt>.
     * 
     * @return <code>true</code> iff this matrix is affine; <code>false</code> otherwise
     */
    public boolean isAffine() {
        return ms[M03] == 0.0f && ms[M13] == 0.0f && ms[M23] == 0.0f && ms[M33] == 1.0f;
    }

    /**
     * Exchange the values of <code>this</code> matrix with the given <code>other</code> matrix.
     * 
     * @param other
     *          the other matrix to exchange the values with
     * @return this
     */
    public Matrix4f swap(Matrix4f other) {
        float tmp;
        tmp = ms[M00]; ms[M00] = other.ms[M00]; other.ms[M00] = tmp;
        tmp = ms[M01]; ms[M01] = other.ms[M01]; other.ms[M01] = tmp;
        tmp = ms[M02]; ms[M02] = other.ms[M02]; other.ms[M02] = tmp;
        tmp = ms[M03]; ms[M03] = other.ms[M03]; other.ms[M03] = tmp;
        tmp = ms[M10]; ms[M10] = other.ms[M10]; other.ms[M10] = tmp;
        tmp = ms[M11]; ms[M11] = other.ms[M11]; other.ms[M11] = tmp;
        tmp = ms[M12]; ms[M12] = other.ms[M12]; other.ms[M12] = tmp;
        tmp = ms[M13]; ms[M13] = other.ms[M13]; other.ms[M13] = tmp;
        tmp = ms[M20]; ms[M20] = other.ms[M20]; other.ms[M20] = tmp;
        tmp = ms[M21]; ms[M21] = other.ms[M21]; other.ms[M21] = tmp;
        tmp = ms[M22]; ms[M22] = other.ms[M22]; other.ms[M22] = tmp;
        tmp = ms[M23]; ms[M23] = other.ms[M23]; other.ms[M23] = tmp;
        tmp = ms[M30]; ms[M30] = other.ms[M30]; other.ms[M30] = tmp;
        tmp = ms[M31]; ms[M31] = other.ms[M31]; other.ms[M31] = tmp;
        tmp = ms[M32]; ms[M32] = other.ms[M32]; other.ms[M32] = tmp;
        tmp = ms[M33]; ms[M33] = other.ms[M33]; other.ms[M33] = tmp;
        return this;
    }

    /**
     * Apply an arcball view transformation to this matrix with the given <code>radius</code> and <code>center</code>
     * position of the arcball and the specified X and Y rotation angles, and store the result in <code>dest</code>.
     * <p>
     * This method is equivalent to calling: <tt>translate(0, 0, -radius).rotateX(angleX).rotateY(angleY).translate(-center.x, -center.y, -center.z)</tt>
     * 
     * @param radius
     *          the arcball radius
     * @param center
     *          the center position of the arcball
     * @param angleX
     *          the rotation angle around the X axis in radians
     * @param angleY
     *          the rotation angle around the Y axis in radians
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Matrix4f arcball(float radius, Vector3f center, float angleX, float angleY, Matrix4f dest) {
        return translate(0, 0, -radius, dest).rotateX(angleX).rotateY(angleY).translate(-center.x, -center.y, -center.z);
    }

    /**
     * Apply an arcball view transformation to this matrix with the given <code>radius</code> and <code>center</code>
     * position of the arcball and the specified X and Y rotation angles.
     * <p>
     * This method is equivalent to calling: <tt>translate(0, 0, -radius).rotateX(angleX).rotateY(angleY).translate(-center.x, -center.y, -center.z)</tt>
     * 
     * @param radius
     *          the arcball radius
     * @param center
     *          the center position of the arcball
     * @param angleX
     *          the rotation angle around the X axis in radians
     * @param angleY
     *          the rotation angle around the Y axis in radians
     * @return this
     */
    public Matrix4f arcball(float radius, Vector3f center, float angleX, float angleY) {
        return arcball(radius, center, angleX, angleY, this);
    }

    /**
     * Compute the axis-aligned bounding box of the frustum described by <code>this</code> matrix and store the minimum corner
     * coordinates in the given <code>min</code> and the maximum corner coordinates in the given <code>max</code> vector.
     * <p>
     * The matrix <code>this</code> is assumed to be the {@link #invert() inverse} of the origial view-projection matrix
     * for which to compute the axis-aligned bounding box in world-space.
     * <p>
     * The axis-aligned bounding box of the unit frustum is <tt>(-1, -1, -1)</tt>, <tt>(1, 1, 1)</tt>.
     * 
     * @param min
     *          will hold the minimum corner coordinates of the axis-aligned bounding box
     * @param max
     *          will hold the maximum corner coordinates of the axis-aligned bounding box
     * @return this
     */
    public Matrix4f frustumAabb(Vector3f min, Vector3f max) {
        float minX = Float.MAX_VALUE;
        float minY = Float.MAX_VALUE;
        float minZ = Float.MAX_VALUE;
        float maxX = -Float.MAX_VALUE;
        float maxY = -Float.MAX_VALUE;
        float maxZ = -Float.MAX_VALUE;
        for (int t = 0; t < 8; t++) {
            float x = ((t % 2) << 1) - 1.0f;
            float y = (((t >>> 1) % 2) << 1) - 1.0f;
            float z = (((t >>> 2) % 2) << 1) - 1.0f;
            float invW = 1.0f / (ms[M03] * x + ms[M13] * y + ms[M23] * z + ms[M33]);
            float nx = (ms[M00] * x + ms[M10] * y + ms[M20] * z + ms[M30]) * invW;
            float ny = (ms[M01] * x + ms[M11] * y + ms[M21] * z + ms[M31]) * invW;
            float nz = (ms[M02] * x + ms[M12] * y + ms[M22] * z + ms[M32]) * invW;
            minX = minX < nx ? minX : nx;
            minY = minY < ny ? minY : ny;
            minZ = minZ < nz ? minZ : nz;
            maxX = maxX > nx ? maxX : nx;
            maxY = maxY > ny ? maxY : ny;
            maxZ = maxZ > nz ? maxZ : nz;
        }
        min.x = minX;
        min.y = minY;
        min.z = minZ;
        max.x = maxX;
        max.y = maxY;
        max.z = maxZ;
        return this;
    }

    /**
     * Compute the <i>range matrix</i> for the Projected Grid transformation as described in chapter "2.4.2 Creating the range conversion matrix"
     * of the paper <a href="http://fileadmin.cs.lth.se/graphics/theses/projects/projgrid/projgrid-lq.pdf">Real-time water rendering - Introducing the projected grid concept</a>
     * based on the <i>inverse</i> of the view-projection matrix which is assumed to be <code>this</code>, and store that range matrix into <code>dest</code>.
     * <p>
     * If the projected grid will not be visible then this method returns <code>null</code>.
     * <p>
     * This method uses the <tt>y = 0</tt> plane for the projection.
     * 
     * @param projector
     *          the projector view-projection transformation
     * @param sLower
     *          the lower (smallest) Y-coordinate which any transformed vertex might have while still being visible on the projected grid
     * @param sUpper
     *          the upper (highest) Y-coordinate which any transformed vertex might have while still being visible on the projected grid
     * @param dest
     *          will hold the resulting range matrix
     * @return the computed range matrix; or <code>null</code> if the projected grid will not be visible
     */
    public Matrix4f projectedGridRange(Matrix4f projector, float sLower, float sUpper, Matrix4f dest) {
        // Compute intersection with frustum edges and plane
        float minX = Float.MAX_VALUE, minY = Float.MAX_VALUE;
        float maxX = -Float.MAX_VALUE, maxY = -Float.MAX_VALUE;
        boolean intersection = false;
        for (int t = 0; t < 3 * 4; t++) {
            float c0X, c0Y, c0Z;
            float c1X, c1Y, c1Z;
            if (t < 4) {
                // all x edges
                c0X = -1; c1X = +1;
                c0Y = c1Y = ((t & 1) << 1) - 1.0f;
                c0Z = c1Z = (((t >>> 1) & 1) << 1) - 1.0f;
            } else if (t < 8) {
                // all y edges
                c0Y = -1; c1Y = +1;
                c0X = c1X = ((t & 1) << 1) - 1.0f;
                c0Z = c1Z = (((t >>> 1) & 1) << 1) - 1.0f;
            } else {
                // all z edges
                c0Z = -1; c1Z = +1;
                c0X = c1X = ((t & 1) << 1) - 1.0f;
                c0Y = c1Y = (((t >>> 1) & 1) << 1) - 1.0f;
            }
            // unproject corners
            float invW = 1.0f / (ms[M03] * c0X + ms[M13] * c0Y + ms[M23] * c0Z + ms[M33]);
            float p0x = (ms[M00] * c0X + ms[M10] * c0Y + ms[M20] * c0Z + ms[M30]) * invW;
            float p0y = (ms[M01] * c0X + ms[M11] * c0Y + ms[M21] * c0Z + ms[M31]) * invW;
            float p0z = (ms[M02] * c0X + ms[M12] * c0Y + ms[M22] * c0Z + ms[M32]) * invW;
            invW = 1.0f / (ms[M03] * c1X + ms[M13] * c1Y + ms[M23] * c1Z + ms[M33]);
            float p1x = (ms[M00] * c1X + ms[M10] * c1Y + ms[M20] * c1Z + ms[M30]) * invW;
            float p1y = (ms[M01] * c1X + ms[M11] * c1Y + ms[M21] * c1Z + ms[M31]) * invW;
            float p1z = (ms[M02] * c1X + ms[M12] * c1Y + ms[M22] * c1Z + ms[M32]) * invW;
            float dirX = p1x - p0x;
            float dirY = p1y - p0y;
            float dirZ = p1z - p0z;
            float invDenom = 1.0f / dirY;
            // test for intersection
            for (int s = 0; s < 2; s++) {
                float isectT = -(p0y + (s == 0 ? sLower : sUpper)) * invDenom;
                if (isectT >= 0.0f && isectT <= 1.0f) {
                    intersection = true;
                    // project with projector matrix
                    float ix = p0x + isectT * dirX;
                    float iz = p0z + isectT * dirZ;
                    invW = 1.0f / (projector.ms[M03] * ix + projector.ms[M23] * iz + projector.ms[M33]);
                    float px = (projector.ms[M00] * ix + projector.ms[M20] * iz + projector.ms[M30]) * invW;
                    float py = (projector.ms[M01] * ix + projector.ms[M21] * iz + projector.ms[M31]) * invW;
                    minX = minX < px ? minX : px;
                    minY = minY < py ? minY : py;
                    maxX = maxX > px ? maxX : px;
                    maxY = maxY > py ? maxY : py;
                }
            }
        }
        if (!intersection)
            return null; // <- projected grid is not visible
        return dest.set(maxX - minX, 0, 0, 0, 0, maxY - minY, 0, 0, 0, 0, 1, 0, minX, minY, 0, 1);
    }

    /**
     * Change the near and far clip plane distances of <code>this</code> perspective frustum transformation matrix
     * and store the result in <code>dest</code>.
     * <p>
     * This method only works if <code>this</code> is a perspective projection frustum transformation, for example obtained
     * via {@link #perspective(float, float, float, float) perspective()} or {@link #frustum(float, float, float, float, float, float) frustum()}.
     * 
     * @see #perspective(float, float, float, float)
     * @see #frustum(float, float, float, float, float, float)
     * 
     * @param near
     *          the new near clip plane distance
     * @param far
     *          the new far clip plane distance
     * @param dest
     *          will hold the resulting matrix
     * @return dest
     */
    public Matrix4f perspectiveFrustumSlice(float near, float far, Matrix4f dest) {
        float invOldNear = (ms[M23] + ms[M22]) / ms[M32];
        float invNearFar = 1.0f / (near - far);
        dest.ms[M00] = ms[M00] * invOldNear * near;
        dest.ms[M01] = ms[M01];
        dest.ms[M02] = ms[M02];
        dest.ms[M03] = ms[M03];
        dest.ms[M10] = ms[M10];
        dest.ms[M11] = ms[M11] * invOldNear * near;
        dest.ms[M12] = ms[M12];
        dest.ms[M13] = ms[M13];
        dest.ms[M20] = ms[M20];
        dest.ms[M21] = ms[M21];
        dest.ms[M22] = (far + near) * invNearFar;
        dest.ms[M23] = ms[M23];
        dest.ms[M30] = ms[M30];
        dest.ms[M31] = ms[M31];
        dest.ms[M32] = (far + far) * near * invNearFar;
        dest.ms[M33] = ms[M33];
        return dest;
    }

    /**
     * Build an ortographic projection transformation that fits the view-projection transformation represented by <code>this</code>
     * into the given affine <code>view</code> transformation.
     * <p>
     * The transformation represented by <code>this</code> must be given as the {@link #invert() inverse} of a typical combined camera view-projection
     * transformation, whose projection can be either orthographic or perspective.
     * <p>
     * The <code>view</code> must be an {@link #isAffine() affine} transformation which in the application of Cascaded Shadow Maps is usually the light view transformation.
     * It be obtained via any affine transformation or for example via {@link #lookAt(float, float, float, float, float, float, float, float, float) lookAt()}.
     * <p>
     * Reference: <a href="http://developer.download.nvidia.com/SDK/10.5/opengl/screenshots/samples/cascaded_shadow_maps.html">OpenGL SDK - Cascaded Shadow Maps</a>
     * 
     * @param view
     *          the view transformation to build a corresponding orthographic projection to fit the frustum of <code>this</code>
     * @param dest
     *          will hold the crop projection transformation
     * @return dest
     */
    public Matrix4f orthoCrop(Matrix4f view, Matrix4f dest) {
        // determine min/max world z and min/max orthographically view-projected x/y
        float minX = Float.MAX_VALUE, maxX = -Float.MAX_VALUE;
        float minY = Float.MAX_VALUE, maxY = -Float.MAX_VALUE;
        float minZ = Float.MAX_VALUE, maxZ = -Float.MAX_VALUE;
        for (int t = 0; t < 8; t++) {
            float x = ((t & 1) << 1) - 1.0f;
            float y = (((t >>> 1) & 1) << 1) - 1.0f;
            float z = (((t >>> 2) & 1) << 1) - 1.0f;
            float invW = 1.0f / (ms[M03] * x + ms[M13] * y + ms[M23] * z + ms[M33]);
            float wx = (ms[M00] * x + ms[M10] * y + ms[M20] * z + ms[M30]) * invW;
            float wy = (ms[M01] * x + ms[M11] * y + ms[M21] * z + ms[M31]) * invW;
            float wz = (ms[M02] * x + ms[M12] * y + ms[M22] * z + ms[M32]) * invW;
            invW = 1.0f / (view.ms[M03] * wx + view.ms[M13] * wy + view.ms[M23] * wz + view.ms[M33]);
            float vx = view.ms[M00] * wx + view.ms[M10] * wy + view.ms[M20] * wz + view.ms[M30];
            float vy = view.ms[M01] * wx + view.ms[M11] * wy + view.ms[M21] * wz + view.ms[M31];
            float vz = (view.ms[M02] * wx + view.ms[M12] * wy + view.ms[M22] * wz + view.ms[M32]) * invW;
            minX = minX < vx ? minX : vx;
            maxX = maxX > vx ? maxX : vx;
            minY = minY < vy ? minY : vy;
            maxY = maxY > vy ? maxY : vy;
            minZ = minZ < vz ? minZ : vz;
            maxZ = maxZ > vz ? maxZ : vz;
        }
        // build crop projection matrix to fit 'this' frustum into view
        return dest.setOrtho(minX, maxX, minY, maxY, -maxZ, -minZ);
    }

    /**
     * Set <code>this</code> matrix to a perspective transformation that maps the trapezoid spanned by the four corner coordinates
     * <code>(p0x, p0y)</code>, <code>(p1x, p1y)</code>, <code>(p2x, p2y)</code> and <code>(p3x, p3y)</code> to the unit square <tt>[(-1, -1)..(+1, +1)]</tt>.
     * <p>
     * The corner coordinates are given in counter-clockwise order starting from the <i>left</i> corner on the smaller parallel side of the trapezoid
     * seen when looking at the trapezoid oriented with its shorter parallel edge at the bottom and its longer parallel edge at the top.
     * <p>
     * Reference: <a href="https://kenai.com/downloads/wpbdc/Documents/tsm.pdf">Notes On Implementation Of Trapezoidal Shadow Maps</a>
     * 
     * @param p0x
     *          the x coordinate of the left corner at the shorter edge of the trapezoid
     * @param p0y
     *          the y coordinate of the left corner at the shorter edge of the trapezoid
     * @param p1x
     *          the x coordinate of the right corner at the shorter edge of the trapezoid
     * @param p1y
     *          the y coordinate of the right corner at the shorter edge of the trapezoid
     * @param p2x
     *          the x coordinate of the right corner at the longer edge of the trapezoid
     * @param p2y
     *          the y coordinate of the right corner at the longer edge of the trapezoid
     * @param p3x
     *          the x coordinate of the left corner at the longer edge of the trapezoid
     * @param p3y
     *          the y coordinate of the left corner at the longer edge of the trapezoid
     * @return this
     */
    public Matrix4f trapezoidCrop(float p0x, float p0y, float p1x, float p1y, float p2x, float p2y, float p3x, float p3y) {
        float aX = p1y - p0y, aY = p0x - p1x;
        float m00 = aY;
        float m10 = -aX;
        float m30 = aX * p0y - aY * p0x;
        float m01 = aX;
        float m11 = aY;
        float m31 = -(aX * p0x + aY * p0y);
        float c3x = m00 * p3x + m10 * p3y + m30;
        float c3y = m01 * p3x + m11 * p3y + m31;
        float s = -c3x / c3y;
        m00 += s * m01;
        m10 += s * m11;
        m30 += s * m31;
        float d1x = m00 * p1x + m10 * p1y + m30;
        float d2x = m00 * p2x + m10 * p2y + m30;
        float d = d1x * c3y / (d2x - d1x);
        m31 += d;
        float sx = 2.0f / d2x;
        float sy = 1.0f / (c3y + d);
        float u = (sy + sy) * d / (1.0f - sy * d);
        float m03 = m01 * sy;
        float m13 = m11 * sy;
        float m33 = m31 * sy;
        m01 = (u + 1.0f) * m03;
        m11 = (u + 1.0f) * m13;
        m31 = (u + 1.0f) * m33 - u;
        m00 = sx * m00 - m03;
        m10 = sx * m10 - m13;
        m30 = sx * m30 - m33;
        return set(m00, m01, 0, m03,
                   m10, m11, 0, m13,
                     0,   0, 1,   0,
                   m30, m31, 0, m33);
    }

    /**
     * Transform the axis-aligned box given as the minimum corner <tt>(minX, minY, minZ)</tt> and maximum corner <tt>(maxX, maxY, maxZ)</tt>
     * by <code>this</code> {@link #isAffine() affine} matrix and compute the axis-aligned box of the result whose minimum corner is stored in <code>outMin</code>
     * and maximum corner stored in <code>outMax</code>.
     * <p>
     * Reference: <a href="http://dev.theomader.com/transform-bounding-boxes/">http://dev.theomader.com</a>
     * 
     * @param minX
     *              the x coordinate of the minimum corner of the axis-aligned box
     * @param minY
     *              the y coordinate of the minimum corner of the axis-aligned box
     * @param minZ
     *              the z coordinate of the minimum corner of the axis-aligned box
     * @param maxX
     *              the x coordinate of the maximum corner of the axis-aligned box
     * @param maxY
     *              the y coordinate of the maximum corner of the axis-aligned box
     * @param maxZ
     *              the y coordinate of the maximum corner of the axis-aligned box
     * @param outMin
     *              will hold the minimum corner of the resulting axis-aligned box
     * @param outMax
     *              will hold the maximum corner of the resulting axis-aligned box
     * @return this
     */
    public Matrix4f transformAab(float minX, float minY, float minZ, float maxX, float maxY, float maxZ, Vector3f outMin, Vector3f outMax) {
        float xax = ms[M00] * minX, xay = ms[M01] * minX, xaz = ms[M02] * minX;
        float xbx = ms[M00] * maxX, xby = ms[M01] * maxX, xbz = ms[M02] * maxX;
        float yax = ms[M10] * minY, yay = ms[M11] * minY, yaz = ms[M12] * minY;
        float ybx = ms[M10] * maxY, yby = ms[M11] * maxY, ybz = ms[M12] * maxY;
        float zax = ms[M20] * minZ, zay = ms[M21] * minZ, zaz = ms[M22] * minZ;
        float zbx = ms[M20] * maxZ, zby = ms[M21] * maxZ, zbz = ms[M22] * maxZ;
        float xminx, xminy, xminz, yminx, yminy, yminz, zminx, zminy, zminz;
        float xmaxx, xmaxy, xmaxz, ymaxx, ymaxy, ymaxz, zmaxx, zmaxy, zmaxz;
        if (xax < xbx) {
            xminx = xax;
            xmaxx = xbx;
        } else {
            xminx = xbx;
            xmaxx = xax;
        }
        if (xay < xby) {
            xminy = xay;
            xmaxy = xby;
        } else {
            xminy = xby;
            xmaxy = xay;
        }
        if (xaz < xbz) {
            xminz = xaz;
            xmaxz = xbz;
        } else {
            xminz = xbz;
            xmaxz = xaz;
        }
        if (yax < ybx) {
            yminx = yax;
            ymaxx = ybx;
        } else {
            yminx = ybx;
            ymaxx = yax;
        }
        if (yay < yby) {
            yminy = yay;
            ymaxy = yby;
        } else {
            yminy = yby;
            ymaxy = yay;
        }
        if (yaz < ybz) {
            yminz = yaz;
            ymaxz = ybz;
        } else {
            yminz = ybz;
            ymaxz = yaz;
        }
        if (zax < zbx) {
            zminx = zax;
            zmaxx = zbx;
        } else {
            zminx = zbx;
            zmaxx = zax;
        }
        if (zay < zby) {
            zminy = zay;
            zmaxy = zby;
        } else {
            zminy = zby;
            zmaxy = zay;
        }
        if (zaz < zbz) {
            zminz = zaz;
            zmaxz = zbz;
        } else {
            zminz = zbz;
            zmaxz = zaz;
        }
        outMin.x = xminx + yminx + zminx + ms[M30];
        outMin.y = xminy + yminy + zminy + ms[M31];
        outMin.z = xminz + yminz + zminz + ms[M32];
        outMax.x = xmaxx + ymaxx + zmaxx + ms[M30];
        outMax.y = xmaxy + ymaxy + zmaxy + ms[M31];
        outMax.z = xmaxz + ymaxz + zmaxz + ms[M32];
        return this;
    }

    /**
     * Transform the axis-aligned box given as the minimum corner <code>min</code> and maximum corner <code>max</code>
     * by <code>this</code> {@link #isAffine() affine} matrix and compute the axis-aligned box of the result whose minimum corner is stored in <code>outMin</code>
     * and maximum corner stored in <code>outMax</code>.
     * 
     * @param min
     *              the minimum corner of the axis-aligned box
     * @param max
     *              the maximum corner of the axis-aligned box
     * @param outMin
     *              will hold the minimum corner of the resulting axis-aligned box
     * @param outMax
     *              will hold the maximum corner of the resulting axis-aligned box
     * @return this
     */
    public Matrix4f transformAab(Vector3f min, Vector3f max, Vector3f outMin, Vector3f outMax) {
        return transformAab(min.x, min.y, min.z, max.x, max.y, max.z, outMin, outMax);
    }

}
