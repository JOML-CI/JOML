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

	static {
	    JNI.touch();
	    if (!JNI.hasSse) {
	        throw new AssertionError("Your CPU does not support the Streaming SIMD Extensions (SSE) instructions.");
	    }
	}

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
     * The address of the native memory. It <i>MUST</i> be 16-byte aligned.
     */
    public long address;
    /**
     * We also store internally, whether the {@link #address} is really owned by this Matrix4f.
     */
    private long ownedMemory;

    /* Native functions */

    /**
     * Allocate 16-byte aligned memory to hold 16 float values.
     * 
     * @return the memory address
     */
    public static final native long allocate();

    /**
     * Free memory allocated via {@link #allocate()}.
     * 
     * @param addr
     *          the address returned by {@link #allocate()}
     */
    public static final native void free(long addr);

    /**
     * Copy the 16 float fields from <code>src</code> to <code>dst</code>. Both addresses must be 16-byte aligned.
     * 
     * @param src
     *          the 16-byte aligned address of the source matrix memory
     * @param dst
     *          the 16-byte aligned address of the destination matrix memory
     */
    public static final native void copy(long src, long dst);

    /**
     * Multiply the matrix stored at address <code>left</code> by the matrix stored at address <code>right</code> and store the result into <code>dest</code>.
     * <p>
     * All addresses must be 16-byte aligned.
     * 
     * @param left
     *          the 16-byte aligned address of the left operand matrix
     * @param right
     *          the 16-byte aligned address of the right operand matrix
     * @param dest
     *          the 16-byte aligned address of the destination matrix
     */
    public static final native void mulNative(long left, long right, long dest);

    /**
     * Invert the matrix stored at address <code>addr</code> and store the result into <code>dest</code>.
     * <p>
     * All addresses must be 16-byte aligned.
     * 
     * @param addr
     *          the 16-byte aligned address of the matrix to invert
     * @param dest
     *          the 16-byte aligned address of the destination matrix
     */
    public static final native void invertNative(long addr, long dest);

    /**
     * Create a new {@link Matrix4f} and set it to {@link #identity() identity}.
     */
    public Matrix4f() {
    	this.address = allocate();
    	this.ownedMemory = address;
        identity();
    }

    /**
     * Create a new {@link Matrix4f} by setting its uppper left 3x3 submatrix to the values of the given {@link Matrix3f}
     * and the rest to identity.
     * 
     * @param mat
     *          the {@link Matrix3f}
     */
    public Matrix4f(Matrix3f mat) {
    	this.address = allocate();
    	this.ownedMemory = address;
        m00(mat.ms[Matrix3f.M00]);
        m01(mat.ms[Matrix3f.M01]);
        m02(mat.ms[Matrix3f.M02]);
        m03(0.0f);
        m10(mat.ms[Matrix3f.M10]);
        m11(mat.ms[Matrix3f.M11]);
        m12(mat.ms[Matrix3f.M12]);
        m13(0.0f);
        m20(mat.ms[Matrix3f.M20]);
        m21(mat.ms[Matrix3f.M21]);
        m22(mat.ms[Matrix3f.M22]);
        m23(0.0f);
        m30(0.0f);
        m31(0.0f);
        m32(0.0f);
        m33(1.0f);
    }

    /**
     * Create a new {@link Matrix4f} and make it a copy of the given matrix.
     * 
     * @param mat
     *          the {@link Matrix4f} to copy the values from
     */
    public Matrix4f(Matrix4f mat) {
    	this.address = allocate();
    	this.ownedMemory = address;
    	copy(mat.address, address);
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
        this.address = allocate();
        this.ownedMemory = address;
        m00((float) mat.m00());
        m01((float) mat.m01());
        m02((float) mat.m02());
        m03((float) mat.m03());
        m10((float) mat.m10());
        m11((float) mat.m11());
        m12((float) mat.m12());
        m13((float) mat.m13());
        m20((float) mat.m20());
        m21((float) mat.m21());
        m22((float) mat.m22());
        m23((float) mat.m23());
        m30((float) mat.m30());
        m31((float) mat.m31());
        m32((float) mat.m32());
        m33((float) mat.m33());
    }

    /**
     * Create a new 4x4 matrix using the supplied float values.
     * 
     * @param n00
     *          the value of m00()
     * @param n01
     *          the value of m01()
     * @param n02
     *          the value of m02()
     * @param n03
     *          the value of m03()
     * @param n10
     *          the value of m10()
     * @param n11
     *          the value of m11()
     * @param n12
     *          the value of m12()
     * @param n13
     *          the value of m13()
     * @param n20
     *          the value of m20()
     * @param n21
     *          the value of m21()
     * @param n22
     *          the value of m22()
     * @param n23
     *          the value of m23()
     * @param n30
     *          the value of m30()
     * @param n31
     *          the value of m31()
     * @param n32
     *          the value of m32()
     * @param n33
     *          the value of m33()
     */
    public Matrix4f(float n00, float n01, float n02, float n03,
                    float n10, float n11, float n12, float n13,
                    float n20, float n21, float n22, float n23, 
                    float n30, float n31, float n32, float n33) {
        this.address = allocate();
        this.ownedMemory = address;
        m00(n00);
        m01(n01);
        m02(n02);
        m03(n03);
        m10(n10);
        m11(n11);
        m12(n12);
        m13(n13);
        m20(n20);
        m21(n21);
        m22(n22);
        m23(n23);
        m30(n30);
        m31(n31);
        m32(n32);
        m33(n33);
    }

    /**
     * Free the native memory of this {@link Matrix4f}.
     * <p>
     * Doing so is only valid when this Matrix4f was created via one of the following constructors:
     * <ul>
     * <li>{@link #Matrix4f()}
     * <li>{@link #Matrix4f(Matrix3f)}
     * <li>{@link #Matrix4f(Matrix4d)}
     * <li>{@link #Matrix4f(Matrix4f)}
     * <li>{@link #Matrix4f(float, float, float, float, float, float, float, float, float, float, float, float, float, float, float, float)}
     * </ul>
     */
    public void free() {
        synchronized (this) {
            if (address != 0L && ownedMemory == address) {
                free(address);
                address = 0L;
                ownedMemory = 0L;
            }
        }
    }

    /**
     * Eventually free the native memory of this Matrix4f.
     */
    protected void finalize() throws Throwable {
        super.finalize();
        free();
    }

    /**
     * Return the value of the matrix element at column 0 and row 0.
     * 
     * @return the value of the matrix element
     */
    public final float m00() {
        return Unsafe.get(address+M00*4);
    }
    /**
     * Return the value of the matrix element at column 0 and row 1.
     * 
     * @return the value of the matrix element
     */
    public final float m01() {
        return Unsafe.get(address+M01*4);
    }
    /**
     * Return the value of the matrix element at column 0 and row 2.
     * 
     * @return the value of the matrix element
     */
    public final float m02() {
        return Unsafe.get(address+M02*4);
    }
    /**
     * Return the value of the matrix element at column 0 and row 3.
     * 
     * @return the value of the matrix element
     */
    public final float m03() {
        return Unsafe.get(address+M03*4);
    }
    /**
     * Return the value of the matrix element at column 1 and row 0.
     * 
     * @return the value of the matrix element
     */
    public final float m10() {
        return Unsafe.get(address+M10*4);
    }
    /**
     * Return the value of the matrix element at column 1 and row 1.
     * 
     * @return the value of the matrix element
     */
    public final float m11() {
        return Unsafe.get(address+M11*4);
    }
    /**
     * Return the value of the matrix element at column 1 and row 2.
     * 
     * @return the value of the matrix element
     */
    public final float m12() {
        return Unsafe.get(address+M12*4);
    }
    /**
     * Return the value of the matrix element at column 1 and row 3.
     * 
     * @return the value of the matrix element
     */
    public final float m13() {
        return Unsafe.get(address+M13*4);
    }
    /**
     * Return the value of the matrix element at column 2 and row 0.
     * 
     * @return the value of the matrix element
     */
    public final float m20() {
        return Unsafe.get(address+M20*4);
    }
    /**
     * Return the value of the matrix element at column 2 and row 1.
     * 
     * @return the value of the matrix element
     */
    public final float m21() {
        return Unsafe.get(address+M21*4);
    }
    /**
     * Return the value of the matrix element at column 2 and row 2.
     * 
     * @return the value of the matrix element
     */
    public final float m22() {
        return Unsafe.get(address+M22*4);
    }
    /**
     * Return the value of the matrix element at column 2 and row 3.
     * 
     * @return the value of the matrix element
     */
    public final float m23() {
        return Unsafe.get(address+M23*4);
    }
    /**
     * Return the value of the matrix element at column 3 and row 0.
     * 
     * @return the value of the matrix element
     */
    public final float m30() {
        return Unsafe.get(address+M30*4);
    }
    /**
     * Return the value of the matrix element at column 3 and row 1.
     * 
     * @return the value of the matrix element
     */
    public final float m31() {
        return Unsafe.get(address+M31*4);
    }
    /**
     * Return the value of the matrix element at column 3 and row 2.
     * 
     * @return the value of the matrix element
     */
    public final float m32() {
        return Unsafe.get(address+M32*4);
    }
    /**
     * Return the value of the matrix element at column 3 and row 3.
     * 
     * @return the value of the matrix element
     */
    public final float m33() {
        return Unsafe.get(address+M33*4);
    }

    /**
     * Set the value of the matrix element at column 0 and row 0
     * 
     * @param m00
     *          the new value
     * @return the value of the matrix element
     */
    public final Matrix4f m00(float m00) {
        Unsafe.set(address+M00*4, m00);
        return this;
    }
    /**
     * Set the value of the matrix element at column 0 and row 1
     * 
     * @param m01
     *          the new value
     * @return the value of the matrix element
     */
    public final Matrix4f m01(float m01) {
        Unsafe.set(address+M01*4, m01);
        return this;
    }
    /**
     * Set the value of the matrix element at column 0 and row 2
     * 
     * @param m02
     *          the new value
     * @return the value of the matrix element
     */
    public final Matrix4f m02(float m02) {
        Unsafe.set(address+M02*4, m02);
        return this;
    }
    /**
     * Set the value of the matrix element at column 0 and row 3
     * 
     * @param m03
     *          the new value
     * @return the value of the matrix element
     */
    public final Matrix4f m03(float m03) {
        Unsafe.set(address+M03*4, m03);
        return this;
    }
    /**
     * Set the value of the matrix element at column 1 and row 0
     * 
     * @param m10
     *          the new value
     * @return the value of the matrix element
     */
    public final Matrix4f m10(float m10) {
        Unsafe.set(address+M10*4, m10);
        return this;
    }
    /**
     * Set the value of the matrix element at column 1 and row 1
     * 
     * @param m11
     *          the new value
     * @return the value of the matrix element
     */
    public final Matrix4f m11(float m11) {
        Unsafe.set(address+M11*4, m11);
        return this;
    }
    /**
     * Set the value of the matrix element at column 1 and row 2
     * 
     * @param m12
     *          the new value
     * @return the value of the matrix element
     */
    public final Matrix4f m12(float m12) {
        Unsafe.set(address+M12*4, m12);
        return this;
    }
    /**
     * Set the value of the matrix element at column 1 and row 3
     * 
     * @param m13
     *          the new value
     * @return the value of the matrix element
     */
    public final Matrix4f m13(float m13) {
        Unsafe.set(address+M13*4, m13);
        return this;
    }
    /**
     * Set the value of the matrix element at column 2 and row 0
     * 
     * @param m20
     *          the new value
     * @return the value of the matrix element
     */
    public final Matrix4f m20(float m20) {
        Unsafe.set(address+M20*4, m20);
        return this;
    }
    /**
     * Set the value of the matrix element at column 2 and row 1
     * 
     * @param m21
     *          the new value
     * @return the value of the matrix element
     */
    public final Matrix4f m21(float m21) {
        Unsafe.set(address+M21*4, m21);
        return this;
    }
    /**
     * Set the value of the matrix element at column 2 and row 2
     * 
     * @param m22
     *          the new value
     * @return the value of the matrix element
     */
    public final Matrix4f m22(float m22) {
        Unsafe.set(address+M22*4, m22);
        return this;
    }
    /**
     * Set the value of the matrix element at column 2 and row 3
     * 
     * @param m23
     *          the new value
     * @return the value of the matrix element
     */
    public final Matrix4f m23(float m23) {
        Unsafe.set(address+M23*4, m23);
        return this;
    }
    /**
     * Set the value of the matrix element at column 3 and row 0
     * 
     * @param m30
     *          the new value
     * @return the value of the matrix element
     */
    public final Matrix4f m30(float m30) {
        Unsafe.set(address+M30*4, m30);
        return this;
    }
    /**
     * Set the value of the matrix element at column 3 and row 1
     * 
     * @param m31
     *          the new value
     * @return the value of the matrix element
     */
    public final Matrix4f m31(float m31) {
        Unsafe.set(address+M31*4, m31);
        return this;
    }
    /**
     * Set the value of the matrix element at column 3 and row 2
     * 
     * @param m32
     *          the new value
     * @return the value of the matrix element
     */
    public final Matrix4f m32(float m32) {
        Unsafe.set(address+M32*4, m32);
        return this;
    }
    /**
     * Set the value of the matrix element at column 3 and row 3
     * 
     * @param m33
     *          the new value
     * @return this
     */
    public final Matrix4f m33(float m33) {
        Unsafe.set(address+M33*4, m33);
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
        m00(1.0f);
        m01(0.0f);
        m02(0.0f);
        m03(0.0f);
        m10(0.0f);
        m11(1.0f);
        m12(0.0f);
        m13(0.0f);
        m20(0.0f);
        m21(0.0f);
        m22(1.0f);
        m23(0.0f);
        m30(0.0f);
        m31(0.0f);
        m32(0.0f);
        m33(1.0f);
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
//        copy(m.address, address);
        m00(m.m00());
        m01(m.m01());
        m02(m.m02());
        m03(m.m03());
        m10(m.m10());
        m11(m.m11());
        m12(m.m12());
        m13(m.m13());
        m20(m.m20());
        m21(m.m21());
        m22(m.m22());
        m23(m.m23());
        m30(m.m30());
        m31(m.m31());
        m32(m.m32());
        m33(m.m33());
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
        m00((float) m.m00());
        m01((float) m.m01());
        m02((float) m.m02());
        m03((float) m.m03());
        m10((float) m.m10());
        m11((float) m.m11());
        m12((float) m.m12());
        m13((float) m.m13());
        m20((float) m.m20());
        m21((float) m.m21());
        m22((float) m.m22());
        m23((float) m.m23());
        m30((float) m.m30());
        m31((float) m.m31());
        m32((float) m.m32());
        m33((float) m.m33());
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
        m00(mat.ms[Matrix3f.M00]);
        m01(mat.ms[Matrix3f.M01]);
        m02(mat.ms[Matrix3f.M02]);
        m03(0.0f);
        m10(mat.ms[Matrix3f.M10]);
        m11(mat.ms[Matrix3f.M11]);
        m12(mat.ms[Matrix3f.M12]);
        m13(0.0f);
        m20(mat.ms[Matrix3f.M20]);
        m21(mat.ms[Matrix3f.M21]);
        m22(mat.ms[Matrix3f.M22]);
        m23(0.0f);
        m30(0.0f);
        m31(0.0f);
        m32(0.0f);
        m33(1.0f);
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
        m00((float)(c + x*x*omc));
        m11((float)(c + y*y*omc));
        m22((float)(c + z*z*omc));
        double tmp1 = x*y*omc;
        double tmp2 = z*s;
        m10((float)(tmp1 - tmp2));
        m01((float)(tmp1 + tmp2));
        tmp1 = x*z*omc;
        tmp2 = y*s;
        m20((float)(tmp1 + tmp2));
        m02((float)(tmp1 - tmp2));
        tmp1 = y*z*omc;
        tmp2 = x*s;
        m21((float)(tmp1 - tmp2));
        m12((float)(tmp1 + tmp2));
        m30(0.0f);
        m31(0.0f);
        m32(0.0f);
        m33(1.0f);
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
        m00((float)(c + x*x*omc));
        m11((float)(c + y*y*omc));
        m22((float)(c + z*z*omc));
        double tmp1 = x*y*omc;
        double tmp2 = z*s;
        m10((float)(tmp1 - tmp2));
        m01((float)(tmp1 + tmp2));
        tmp1 = x*z*omc;
        tmp2 = y*s;
        m20((float)(tmp1 + tmp2));
        m02((float)(tmp1 - tmp2));
        tmp1 = y*z*omc;
        tmp2 = x*s;
        m21((float)(tmp1 - tmp2));
        m12((float)(tmp1 + tmp2));
        m30(0.0f);
        m31(0.0f);
        m32(0.0f);
        m33(1.0f);
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
        m00(mat.m00());
        m01(mat.m01());
        m02(mat.m02());
        m03(0.0f);
        m10(mat.m10());
        m11(mat.m11());
        m12(mat.m12());
        m13(0.0f);
        m20(mat.m20());
        m21(mat.m21());
        m22(mat.m22());
        m23(0.0f);
        m30(0.0f);
        m31(0.0f);
        m32(0.0f);
        m33(1.0f);
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
    	mulNative(address, right.address, dest.address);
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
        dest.set(m00() * view.m00(), m11() * view.m01(), m22() * view.m02(), m23() * view.m02(),
                 m00() * view.m10(), m11() * view.m11(), m22() * view.m12(), m23() * view.m12(),
                 m00() * view.m20(), m11() * view.m21(), m22() * view.m22(), m23() * view.m22(),
                 m00() * view.m30(), m11() * view.m31(), m22() * view.m32() + m32(), m23() * view.m32());
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
        dest.set(m00() * right.m00() + m10() * right.m01() + m20() * right.m02(),
                 m01() * right.m00() + m11() * right.m01() + m21() * right.m02(),
                 m02() * right.m00() + m12() * right.m01() + m22() * right.m02(),
                 m03() * right.m00() + m13() * right.m01() + m23() * right.m02(),
                 m00() * right.m10() + m10() * right.m11() + m20() * right.m12(),
                 m01() * right.m10() + m11() * right.m11() + m21() * right.m12(),
                 m02() * right.m10() + m12() * right.m11() + m22() * right.m12(),
                 m03() * right.m10() + m13() * right.m11() + m23() * right.m12(),
                 m00() * right.m20() + m10() * right.m21() + m20() * right.m22(),
                 m01() * right.m20() + m11() * right.m21() + m21() * right.m22(),
                 m02() * right.m20() + m12() * right.m21() + m22() * right.m22(),
                 m03() * right.m20() + m13() * right.m21() + m23() * right.m22(),
                 m00() * right.m30() + m10() * right.m31() + m20() * right.m32() + m30(),
                 m01() * right.m30() + m11() * right.m31() + m21() * right.m32() + m31(),
                 m02() * right.m30() + m12() * right.m31() + m22() * right.m32() + m32(),
                 m03() * right.m30() + m13() * right.m31() + m23() * right.m32() + m33());
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
        mulNative(address, right.address, dest.address);
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
        dest.set(m00() * view.m00(), m11() * view.m01(), m22() * view.m02(), 0.0f,
                 m00() * view.m10(), m11() * view.m11(), m22() * view.m12(), 0.0f,
                 m00() * view.m20(), m11() * view.m21(), m22() * view.m22(), 0.0f,
                 m00() * view.m30() + m30(), m11() * view.m31() + m31(), m22() * view.m32() + m32(), 1.0f);
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
        dest.m00(m00() + other.m00() * otherFactor);
        dest.m01(m01() + other.m01() * otherFactor);
        dest.m02(m02() + other.m02() * otherFactor);
        dest.m03(m03());
        dest.m10(m10() + other.m10() * otherFactor);
        dest.m11(m11() + other.m11() * otherFactor);
        dest.m12(m12() + other.m12() * otherFactor);
        dest.m13(m13());
        dest.m20(m20() + other.m20() * otherFactor);
        dest.m21(m21() + other.m21() * otherFactor);
        dest.m22(m22() + other.m22() * otherFactor);
        dest.m23(m23());
        dest.m30(m30() + other.m30() * otherFactor);
        dest.m31(m31() + other.m31() * otherFactor);
        dest.m32(m32() + other.m32() * otherFactor);
        dest.m33(m33());
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
        dest.m00(m00() + other.m00());
        dest.m01(m01() + other.m01());
        dest.m02(m02() + other.m02());
        dest.m03(m03() + other.m03());
        dest.m10(m10() + other.m10());
        dest.m11(m11() + other.m11());
        dest.m12(m12() + other.m12());
        dest.m13(m13() + other.m13());
        dest.m20(m20() + other.m20());
        dest.m21(m21() + other.m21());
        dest.m22(m22() + other.m22());
        dest.m23(m23() + other.m23());
        dest.m30(m30() + other.m30());
        dest.m31(m31() + other.m31());
        dest.m32(m32() + other.m32());
        dest.m33(m33() + other.m33());
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
        dest.m00(m00() - subtrahend.m00());
        dest.m01(m01() - subtrahend.m01());
        dest.m02(m02() - subtrahend.m02());
        dest.m03(m03() - subtrahend.m03());
        dest.m10(m10() - subtrahend.m10());
        dest.m11(m11() - subtrahend.m11());
        dest.m12(m12() - subtrahend.m12());
        dest.m13(m13() - subtrahend.m13());
        dest.m20(m20() - subtrahend.m20());
        dest.m21(m21() - subtrahend.m21());
        dest.m22(m22() - subtrahend.m22());
        dest.m23(m23() - subtrahend.m23());
        dest.m30(m30() - subtrahend.m30());
        dest.m31(m31() - subtrahend.m31());
        dest.m32(m32() - subtrahend.m32());
        dest.m33(m33() - subtrahend.m33());
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
        dest.m00(m00() * other.m00());
        dest.m01(m01() * other.m01());
        dest.m02(m02() * other.m02());
        dest.m03(m03() * other.m03());
        dest.m10(m10() * other.m10());
        dest.m11(m11() * other.m11());
        dest.m12(m12() * other.m12());
        dest.m13(m13() * other.m13());
        dest.m20(m20() * other.m20());
        dest.m21(m21() * other.m21());
        dest.m22(m22() * other.m22());
        dest.m23(m23() * other.m23());
        dest.m30(m30() * other.m30());
        dest.m31(m31() * other.m31());
        dest.m32(m32() * other.m32());
        dest.m33(m33() * other.m33());
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
        dest.m00(m00() + other.m00());
        dest.m01(m01() + other.m01());
        dest.m02(m02() + other.m02());
        dest.m03(m03());
        dest.m10(m10() + other.m10());
        dest.m11(m11() + other.m11());
        dest.m12(m12() + other.m12());
        dest.m13(m13());
        dest.m20(m20() + other.m20());
        dest.m21(m21() + other.m21());
        dest.m22(m22() + other.m22());
        dest.m23(m23());
        dest.m30(m30() + other.m30());
        dest.m31(m31() + other.m31());
        dest.m32(m32() + other.m32());
        dest.m33(m33());
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
        dest.m00(m00() - subtrahend.m00());
        dest.m01(m01() - subtrahend.m01());
        dest.m02(m02() - subtrahend.m02());
        dest.m03(m03());
        dest.m10(m10() - subtrahend.m10());
        dest.m11(m11() - subtrahend.m11());
        dest.m12(m12() - subtrahend.m12());
        dest.m13(m13());
        dest.m20(m20() - subtrahend.m20());
        dest.m21(m21() - subtrahend.m21());
        dest.m22(m22() - subtrahend.m22());
        dest.m23(m23());
        dest.m30(m30() - subtrahend.m30());
        dest.m31(m31() - subtrahend.m31());
        dest.m32(m32() - subtrahend.m32());
        dest.m33(m33());
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
        dest.m00(m00() * other.m00());
        dest.m01(m01() * other.m01());
        dest.m02(m02() * other.m02());
        dest.m03(m03());
        dest.m10(m10() * other.m10());
        dest.m11(m11() * other.m11());
        dest.m12(m12() * other.m12());
        dest.m13(m13());
        dest.m20(m20() * other.m20());
        dest.m21(m21() * other.m21());
        dest.m22(m22() * other.m22());
        dest.m23(m23());
        dest.m30(m30() * other.m30());
        dest.m31(m31() * other.m31());
        dest.m32(m32() * other.m32());
        dest.m33(m33());
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
     *          the new value of m00()
     * @param n01
     *          the new value of m01()
     * @param n02
     *          the new value of m02()
     * @param n03
     *          the new value of m03()
     * @param n10
     *          the new value of m10()
     * @param n11
     *          the new value of m11()
     * @param n12
     *          the new value of m12()
     * @param n13
     *          the new value of m13()
     * @param n20
     *          the new value of m20()
     * @param n21
     *          the new value of m21()
     * @param n22
     *          the new value of m22()
     * @param n23
     *          the new value of m23()
     * @param n30
     *          the new value of m30()
     * @param n31
     *          the new value of m31()
     * @param n32
     *          the new value of m32()
     * @param n33
     *          the new value of m33()
     * @return this
     */
    public Matrix4f set(float n00, float n01, float n02, float n03,
                        float n10, float n11, float n12, float n13,
                        float n20, float n21, float n22, float n23, 
                        float n30, float n31, float n32, float n33) {
        this.m00(n00);
        this.m01(n01);
        this.m02(n02);
        this.m03(n03);
        this.m10(n10);
        this.m11(n11);
        this.m12(n12);
        this.m13(n13);
        this.m20(n20);
        this.m21(n21);
        this.m22(n22);
        this.m23(n23);
        this.m30(n30);
        this.m31(n31);
        this.m32(n32);
        this.m33(n33);
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
        return (m00() * m11() - m01() * m10()) * (m22() * m33() - m23() * m32())
             + (m02() * m10() - m00() * m12()) * (m21() * m33() - m23() * m31())
             + (m00() * m13() - m03() * m10()) * (m21() * m32() - m22() * m31())
             + (m01() * m12() - m02() * m11()) * (m20() * m33() - m23() * m30())
             + (m03() * m11() - m01() * m13()) * (m20() * m32() - m22() * m30())
             + (m02() * m13() - m03() * m12()) * (m20() * m31() - m21() * m30());
    }

    /**
     * Return the determinant of the upper left 3x3 submatrix of this matrix.
     * 
     * @return the determinant
     */
    public float determinant3x3() {
        return (m00() * m11() - m01() * m10()) * m22()
             + (m02() * m10() - m00() * m12()) * m21()
             + (m01() * m12() - m02() * m11()) * m20();
    }

    /**
     * Return the determinant of this matrix by assuming that it represents an {@link #isAffine() affine} transformation and thus
     * its last row is equal to <tt>(0, 0, 0, 1)</tt>.
     * 
     * @return the determinant
     */
    public float determinantAffine() {
        return (m00() * m11() - m01() * m10()) * m22()
             + (m02() * m10() - m00() * m12()) * m21()
             + (m01() * m12() - m02() * m11()) * m20();
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
        invertNative(address, dest.address);
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
        float a =  1.0f / (m00() * m11());
        float l = -1.0f / (m23() * m32());
        dest.set(m11() * a, 0, 0, 0,
                 0, m00() * a, 0, 0,
                 0, 0, 0, -m23() * l,
                 0, 0, -m32() * l, m22() * l);
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
        float invM00 = 1.0f / m00();
        float invM11 = 1.0f / m11();
        float invM23 = 1.0f / m23();
        float invM32 = 1.0f / m32();
        dest.set(invM00, 0, 0, 0,
                 0, invM11, 0, 0,
                 0, 0, 0, invM32,
                 -m20() * invM00 * invM23, -m21() * invM11 * invM23, invM23, -m22() * invM23 * invM32);
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
        float invM00 = 1.0f / m00();
        float invM11 = 1.0f / m11();
        float invM22 = 1.0f / m22();
        dest.set(invM00, 0, 0, 0,
                 0, invM11, 0, 0,
                 0, 0, invM22, 0,
                 -m30() * invM00, -m31() * invM11, -m32() * invM22, 1);
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
        float a =  1.0f / (m00() * m11());
        float l = -1.0f / (m23() * m32());
        float pms00 =  m11() * a;
        float pms11 =  m00() * a;
        float pms23 = -m23() * l;
        float pms32 = -m32() * l;
        float pms33 =  m22() * l;
        float vmsM30 = -view.m00() * view.m30() - view.m01() * view.m31() - view.m02() * view.m32();
        float vmsM31 = -view.m10() * view.m30() - view.m11() * view.m31() - view.m12() * view.m32();
        float vmsM32 = -view.m20() * view.m30() - view.m21() * view.m31() - view.m22() * view.m32();
        dest.set(view.m00() * pms00, view.m10() * pms00, view.m20() * pms00, 0.0f,
                 view.m01() * pms11, view.m11() * pms11, view.m21() * pms11, 0.0f,
                 vmsM30 * pms23, vmsM31 * pms23, vmsM32 * pms23, pms23,
                 view.m02() * pms32 + vmsM30 * pms33, view.m12() * pms32 + vmsM31 * pms33, view.m22() * pms32 + vmsM32 * pms33, pms33);
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
        float msM10M22 = m10() * m22();
        float msM10M21 = m10() * m21();
        float msM10M02 = m10() * m02();
        float msM10M01 = m10() * m01();
        float msM11M22 = m11() * m22();
        float msM11M20 = m11() * m20();
        float msM11M02 = m11() * m02();
        float msM11M00 = m11() * m00();
        float msM12M21 = m12() * m21();
        float msM12M20 = m12() * m20();
        float msM12M01 = m12() * m01();
        float msM12M00 = m12() * m00();
        float msM20M02 = m20() * m02();
        float msM20M01 = m20() * m01();
        float msM21M02 = m21() * m02();
        float msM21M00 = m21() * m00();
        float msM22M01 = m22() * m01();
        float msM22M00 = m22() * m00();
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
                 (msM10M22 * m31() - msM10M21 * m32() + msM11M20 * m32() - msM11M22 * m30() + msM12M21 * m30() - msM12M20 * m31()) * s,
                 (msM20M02 * m31() - msM20M01 * m32() + msM21M00 * m32() - msM21M02 * m30() + msM22M01 * m30() - msM22M00 * m31()) * s,
                 (msM11M02 * m30() - msM12M01 * m30() + msM12M00 * m31() - msM10M02 * m31() + msM10M01 * m32() - msM11M00 * m32()) * s,
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
        dest.set(m00(), m10(), m20(), 0.0f,
                 m01(), m11(), m21(), 0.0f,
                 m02(), m12(), m22(), 0.0f,
                 -m00() * m30() - m01() * m31() - m02() * m32(),
                 -m10() * m30() - m11() * m31() - m12() * m32(),
                 -m20() * m30() - m21() * m31() - m22() * m32(),
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
        dest.set(m00(), m10(), m20(), m30(),
                 m01(), m11(), m21(), m31(),
                 m02(), m12(), m22(), m32(),
                 m03(), m13(), m23(), m33());
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
        dest.set(m00(),  m10(),  m20(),  0.0f,
                 m01(),  m11(),  m21(),  0.0f,
                 m02(),  m12(),  m22(),  0.0f,
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
        dest.ms[Matrix3f.M00] = m00();
        dest.ms[Matrix3f.M01] = m10();
        dest.ms[Matrix3f.M02] = m20();
        dest.ms[Matrix3f.M10] = m01();
        dest.ms[Matrix3f.M11] = m11();
        dest.ms[Matrix3f.M12] = m21();
        dest.ms[Matrix3f.M20] = m02();
        dest.ms[Matrix3f.M21] = m12();
        dest.ms[Matrix3f.M22] = m22();
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
        m00(1.0f);
        m01(0.0f);
        m02(0.0f);
        m03(0.0f);
        m10(0.0f);
        m11(1.0f);
        m12(0.0f);
        m13(0.0f);
        m20(0.0f);
        m21(0.0f);
        m22(1.0f);
        m23(0.0f);
        m30(x);
        m31(y);
        m32(z);
        m33(1.0f);
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
     * Set only the translation components <tt>(m30(), m31(), m32())</tt> of this matrix to the given values <tt>(x, y, z)</tt>.
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
        m30(x);
        m31(y);
        m32(z);
        return this;
    }

    /**
     * Set only the translation components <tt>(m30(), m31(), m32())</tt> of this matrix to the values <tt>(xyz.x, xyz.y, xyz.z)</tt>.
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
        m30(xyz.x);
        m31(xyz.y);
        m32(xyz.z);
        return this;
    }

    /**
     * Get only the translation components <tt>(m30(), m31(), m32())</tt> of this matrix and store them in the given vector <code>xyz</code>.
     * 
     * @param dest
     *          will hold the translation components of this matrix
     * @return dest
     */
    public Vector3f getTranslation(Vector3f dest) {
        dest.x = m30();
        dest.y = m31();
        dest.z = m32();
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
        dest.x = (float) Math.sqrt(m00() * m00() + m01() * m01() + m02() * m02());
        dest.y = (float) Math.sqrt(m10() * m10() + m11() * m11() + m12() * m12());
        dest.z = (float) Math.sqrt(m20() * m20() + m21() * m21() + m22() * m22());
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
        return formatter.format(m00()) + formatter.format(m10()) + formatter.format(m20()) + formatter.format(m30()) + "\n" //$NON-NLS-1$
             + formatter.format(m01()) + formatter.format(m11()) + formatter.format(m21()) + formatter.format(m31()) + "\n" //$NON-NLS-1$
             + formatter.format(m02()) + formatter.format(m12()) + formatter.format(m22()) + formatter.format(m32()) + "\n" //$NON-NLS-1$
             + formatter.format(m03()) + formatter.format(m13()) + formatter.format(m23()) + formatter.format(m33()) + "\n"; //$NON-NLS-1$
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
     * Set all the values within this matrix to <code>0</code>.
     * 
     * @return this
     */
    public Matrix4f zero() {
        m00(0.0f);
        m01(0.0f);
        m02(0.0f);
        m03(0.0f);
        m10(0.0f);
        m11(0.0f);
        m12(0.0f);
        m13(0.0f);
        m20(0.0f);
        m21(0.0f);
        m22(0.0f);
        m23(0.0f);
        m30(0.0f);
        m31(0.0f);
        m32(0.0f);
        m33(0.0f);
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
        m00(factor);
        m01(0.0f);
        m02(0.0f);
        m03(0.0f);
        m10(0.0f);
        m11(factor);
        m12(0.0f);
        m13(0.0f);
        m20(0.0f);
        m21(0.0f);
        m22(factor);
        m23(0.0f);
        m30(0.0f);
        m31(0.0f);
        m32(0.0f);
        m33(1.0f);
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
        m00(x);
        m01(0.0f);
        m02(0.0f);
        m03(0.0f);
        m10(0.0f);
        m11(y);
        m12(0.0f);
        m13(0.0f);
        m20(0.0f);
        m21(0.0f);
        m22(z);
        m23(0.0f);
        m30(0.0f);
        m31(0.0f);
        m32(0.0f);
        m33(1.0f);
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
        m00(cos + x * x * C);
        m10(xy * C - z * sin);
        m20(xz * C + y * sin);
        m30(0.0f);
        m01(xy * C + z * sin);
        m11(cos + y * y * C);
        m21(yz * C - x * sin);
        m31(0.0f);
        m02(xz * C - y * sin);
        m12(yz * C + x * sin);
        m22(cos + z * z * C);
        m32(0.0f);
        m03(0.0f);
        m13(0.0f);
        m23(0.0f);
        m33(1.0f);
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
        m00(1.0f);
        m01(0.0f);
        m02(0.0f);
        m03(0.0f);
        m10(0.0f);
        m11(cos);
        m12(sin);
        m13(0.0f);
        m20(0.0f);
        m21(-sin);
        m22(cos);
        m23(0.0f);
        m30(0.0f);
        m31(0.0f);
        m32(0.0f);
        m33(1.0f);
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
        m00(cos);
        m01(0.0f);
        m02(-sin);
        m03(0.0f);
        m10(0.0f);
        m11(1.0f);
        m12(0.0f);
        m13(0.0f);
        m20(sin);
        m21(0.0f);
        m22(cos);
        m23(0.0f);
        m30(0.0f);
        m31(0.0f);
        m32(0.0f);
        m33(1.0f);
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
        m00(cos);
        m01(sin);
        m02(0.0f);
        m03(0.0f);
        m10(-sin);
        m11(cos);
        m12(0.0f);
        m13(0.0f);
        m20(0.0f);
        m21(0.0f);
        m22(1.0f);
        m23(0.0f);
        m30(0.0f);
        m31(0.0f);
        m32(0.0f);
        m33(1.0f);
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
        m20(sinY);
        m21(nn21 * cosY);
        m22(nn22 * cosY);
        m23(0.0f);
        // rotateZ
        m00(nn00 * cosZ);
        m01(nn01 * cosZ + nn11 * sinZ);
        m02(nn02 * cosZ + nn12 * sinZ);
        m03(0.0f);
        m10(nn00 * m_sinZ);
        m11(nn01 * m_sinZ + nn11 * cosZ);
        m12(nn02 * m_sinZ + nn12 * cosZ);
        m13(0.0f);
        // set last column to identity
        m30(0.0f);
        m31(0.0f);
        m32(0.0f);
        m33(1.0f);
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
        m00(nn00 * cosY);
        m01(nn01 * cosY);
        m02(m_sinY);
        m03(0.0f);
        // rotateX
        m10(nn10 * cosX + nn20 * sinX);
        m11(nn11 * cosX + nn21 * sinX);
        m12(nn22 * sinX);
        m13(0.0f);
        m20(nn10 * m_sinX + nn20 * cosX);
        m21(nn11 * m_sinX + nn21 * cosX);
        m22(nn22 * cosX);
        m23(0.0f);
        // set last column to identity
        m30(0.0f);
        m31(0.0f);
        m32(0.0f);
        m33(1.0f);
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
        m20(nn20 * cosX);
        m21(m_sinX);
        m22(nn22 * cosX);
        m23(0.0f);
        // rotateZ
        m00(nn00 * cosZ + nn10 * sinZ);
        m01(nn11 * sinZ);
        m02(nn02 * cosZ + nn12 * sinZ);
        m03(0.0f);
        m10(nn00 * m_sinZ + nn10 * cosZ);
        m11(nn11 * cosZ);
        m12(nn02 * m_sinZ + nn12 * cosZ);
        m13(0.0f);
        // set last column to identity
        m30(0.0f);
        m31(0.0f);
        m32(0.0f);
        m33(1.0f);
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
        m20(sinY);
        m21(nn21 * cosY);
        m22(nn22 * cosY);
        // rotateZ
        m00(nn00 * cosZ);
        m01(nn01 * cosZ + nn11 * sinZ);
        m02(nn02 * cosZ + nn12 * sinZ);
        m10(nn00 * m_sinZ);
        m11(nn01 * m_sinZ + nn11 * cosZ);
        m12(nn02 * m_sinZ + nn12 * cosZ);
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
        m00(nn00 * cosY);
        m01(nn01 * cosY);
        m02(m_sinY);
        // rotateX
        m10(nn10 * cosX + nn20 * sinX);
        m11(nn11 * cosX + nn21 * sinX);
        m12(nn22 * sinX);
        m20(nn10 * m_sinX + nn20 * cosX);
        m21(nn11 * m_sinX + nn21 * cosX);
        m22(nn22 * cosX);
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
        m20(nn20 * cosX);
        m21(m_sinX);
        m22(nn22 * cosX);
        // rotateZ
        m00(nn00 * cosZ + nn10 * sinZ);
        m01(nn11 * sinZ);
        m02(nn02 * cosZ + nn12 * sinZ);
        m10(nn00 * m_sinZ + nn10 * cosZ);
        m11(nn11 * cosZ);
        m12(nn02 * m_sinZ + nn12 * cosZ);
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

        m00(1.0f - q11 - q22);
        m01(q01 + q23);
        m02(q02 - q13);
        m03(0.0f);
        m10(q01 - q23);
        m11(1.0f - q22 - q00);
        m12(q12 + q03);
        m13(0.0f);
        m20(q02 + q13);
        m21(q12 - q03);
        m22(1.0f - q11 - q00);
        m23(0.0f);
        m30(0.0f);
        m31(0.0f);
        m32(0.0f);
        m33(1.0f);

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
        m00(sx - (q11 + q22) * sx);
        m01((q01 + q23) * sx);
        m02((q02 - q13) * sx);
        m03(0.0f);
        m10((q01 - q23) * sy);
        m11(sy - (q22 + q00) * sy);
        m12((q12 + q03) * sy);
        m13(0.0f);
        m20((q02 + q13) * sz);
        m21((q12 - q03) * sz);
        m22(sz - (q11 + q00) * sz);
        m23(0.0f);
        m30(tx);
        m31(ty);
        m32(tz);
        m33(1.0f);
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
        float m00 = nm00 * m.m00() + nm10 * m.m01() + nm20 * m.m02();
        float m01 = nm01 * m.m00() + nm11 * m.m01() + nm21 * m.m02();
        m02(nm02 * m.m00() + nm12 * m.m01() + nm22 * m.m02());
        this.m00(m00);
        this.m01(m01);
        m03(0.0f);
        float m10 = nm00 * m.m10() + nm10 * m.m11() + nm20 * m.m12();
        float m11 = nm01 * m.m10() + nm11 * m.m11() + nm21 * m.m12();
        m12(nm02 * m.m10() + nm12 * m.m11() + nm22 * m.m12());
        this.m10(m10);
        this.m11(m11);
        m13(0.0f);
        float m20 = nm00 * m.m20() + nm10 * m.m21() + nm20 * m.m22();
        float m21 = nm01 * m.m20() + nm11 * m.m21() + nm21 * m.m22();
        m22(nm02 * m.m20() + nm12 * m.m21() + nm22 * m.m22());
        this.m20(m20);
        this.m21(m21);
        m23(0.0f);
        float m30 = nm00 * m.m30() + nm10 * m.m31() + nm20 * m.m32() + tx;
        float m31 = nm01 * m.m30() + nm11 * m.m31() + nm21 * m.m32() + ty;
        m32(nm02 * m.m30() + nm12 * m.m31() + nm22 * m.m32() + tz);
        this.m30(m30);
        this.m31(m31);
        m33(1.0f);
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
        m00(1.0f - (q11 + q22));
        m01(q01 + q23);
        m02(q02 - q13);
        m03(0.0f);
        m10(q01 - q23);
        m11(1.0f - (q22 + q00));
        m12(q12 + q03);
        m13(0.0f);
        m20(q02 + q13);
        m21(q12 - q03);
        m22(1.0f - (q11 + q00));
        m23(0.0f);
        m30(tx);
        m31(ty);
        m32(tz);
        m33(1.0f);
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
        m00(mat.ms[Matrix3f.M00]);
        m01(mat.ms[Matrix3f.M01]);
        m02(mat.ms[Matrix3f.M02]);
        m03(0.0f);
        m10(mat.ms[Matrix3f.M10]);
        m11(mat.ms[Matrix3f.M11]);
        m12(mat.ms[Matrix3f.M12]);
        m13(0.0f);
        m20(mat.ms[Matrix3f.M20]);
        m21(mat.ms[Matrix3f.M21]);
        m22(mat.ms[Matrix3f.M22]);
        m23(0.0f);
        m30(0.0f);
        m31(0.0f);
        m32(0.0f);
        m33(1.0f);
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
        v.set(m00() * v.x + m10() * v.y + m20() * v.z + m30(),
              m01() * v.x + m11() * v.y + m21() * v.z + m31(),
              m02() * v.x + m12() * v.y + m22() * v.z + m32());
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
        dest.set(m00() * v.x + m10() * v.y + m20() * v.z + m30(),
                 m01() * v.x + m11() * v.y + m21() * v.z + m31(),
                 m02() * v.x + m12() * v.y + m22() * v.z + m32());
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
        v.set(m00() * v.x + m10() * v.y + m20() * v.z,
              m01() * v.x + m11() * v.y + m21() * v.z,
              m02() * v.x + m12() * v.y + m22() * v.z);
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
        dest.set(m00() * v.x + m10() * v.y + m20() * v.z,
                 m01() * v.x + m11() * v.y + m21() * v.z,
                 m02() * v.x + m12() * v.y + m22() * v.z);
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
        v.set(m00() * v.x + m10() * v.y + m20() * v.z + m30() * v.w,
              m01() * v.x + m11() * v.y + m21() * v.z + m31() * v.w,
              m02() * v.x + m12() * v.y + m22() * v.z + m32() * v.w,
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
        dest.set(m00() * v.x + m10() * v.y + m20() * v.z + m30() * v.w,
                 m01() * v.x + m11() * v.y + m21() * v.z + m31() * v.w,
                 m02() * v.x + m12() * v.y + m22() * v.z + m32() * v.w,
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
        dest.m00(m00() * x);
        dest.m01(m01() * x);
        dest.m02(m02() * x);
        dest.m03(m03() * x);
        dest.m10(m10() * y);
        dest.m11(m11() * y);
        dest.m12(m12() * y);
        dest.m13(m13() * y);
        dest.m20(m20() * z);
        dest.m21(m21() * z);
        dest.m22(m22() * z);
        dest.m23(m23() * z);
        dest.m30(m30());
        dest.m31(m31());
        dest.m32(m32());
        dest.m33(m33());
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
        float nn10 = m10() * rn11 + m20() * rn12;
        float nn11 = m11() * rn11 + m21() * rn12;
        float nn12 = m12() * rn11 + m22() * rn12;
        float nn13 = m13() * rn11 + m23() * rn12;
        // set non-dependent values directly
        dest.m20(m10() * rn21 + m20() * rn22);
        dest.m21(m11() * rn21 + m21() * rn22);
        dest.m22(m12() * rn21 + m22() * rn22);
        dest.m23(m13() * rn21 + m23() * rn22);
        // set other values
        dest.m10(nn10);
        dest.m11(nn11);
        dest.m12(nn12);
        dest.m13(nn13);
        dest.m00(m00());
        dest.m01(m01());
        dest.m02(m02());
        dest.m03(m03());
        dest.m30(m30());
        dest.m31(m31());
        dest.m32(m32());
        dest.m33(m33());
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
        float nn00 = m00() * rn00 + m20() * rn02;
        float nn01 = m01() * rn00 + m21() * rn02;
        float nn02 = m02() * rn00 + m22() * rn02;
        float nn03 = m03() * rn00 + m23() * rn02;
        // set non-dependent values directly
        dest.m20(m00() * rn20 + m20() * rn22);
        dest.m21(m01() * rn20 + m21() * rn22);
        dest.m22(m02() * rn20 + m22() * rn22);
        dest.m23(m03() * rn20 + m23() * rn22);
        // set other values
        dest.m00(nn00);
        dest.m01(nn01);
        dest.m02(nn02);
        dest.m03(nn03);
        dest.m10(m10());
        dest.m11(m11());
        dest.m12(m12());
        dest.m13(m13());
        dest.m30(m30());
        dest.m31(m31());
        dest.m32(m32());
        dest.m33(m33());
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
        float nn00 = m00() * rn00 + m10() * rn01;
        float nn01 = m01() * rn00 + m11() * rn01;
        float nn02 = m02() * rn00 + m12() * rn01;
        float nn03 = m03() * rn00 + m13() * rn01;
        // set non-dependent values directly
        dest.m10(m00() * rn10 + m10() * rn11);
        dest.m11(m01() * rn10 + m11() * rn11);
        dest.m12(m02() * rn10 + m12() * rn11);
        dest.m13(m03() * rn10 + m13() * rn11);
        // set other values
        dest.m00(nn00);
        dest.m01(nn01);
        dest.m02(nn02);
        dest.m03(nn03);
        dest.m20(m20());
        dest.m21(m21());
        dest.m22(m22());
        dest.m23(m23());
        dest.m30(m30());
        dest.m31(m31());
        dest.m32(m32());
        dest.m33(m33());
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
        float nn10 = m10() * cosX + m20() * sinX;
        float nn11 = m11() * cosX + m21() * sinX;
        float nn12 = m12() * cosX + m22() * sinX;
        float nn13 = m13() * cosX + m23() * sinX;
        float nn20 = m10() * m_sinX + m20() * cosX;
        float nn21 = m11() * m_sinX + m21() * cosX;
        float nn22 = m12() * m_sinX + m22() * cosX;
        float nn23 = m13() * m_sinX + m23() * cosX;
        // rotateY
        float nn00 = m00() * cosY + nn20 * m_sinY;
        float nn01 = m01() * cosY + nn21 * m_sinY;
        float nn02 = m02() * cosY + nn22 * m_sinY;
        float nn03 = m03() * cosY + nn23 * m_sinY;
        dest.m20(m00() * sinY + nn20 * cosY);
        dest.m21(m01() * sinY + nn21 * cosY);
        dest.m22(m02() * sinY + nn22 * cosY);
        dest.m23(m03() * sinY + nn23 * cosY);
        // rotateZ
        dest.m00(nn00 * cosZ + nn10 * sinZ);
        dest.m01(nn01 * cosZ + nn11 * sinZ);
        dest.m02(nn02 * cosZ + nn12 * sinZ);
        dest.m03(nn03 * cosZ + nn13 * sinZ);
        dest.m10(nn00 * m_sinZ + nn10 * cosZ);
        dest.m11(nn01 * m_sinZ + nn11 * cosZ);
        dest.m12(nn02 * m_sinZ + nn12 * cosZ);
        dest.m13(nn03 * m_sinZ + nn13 * cosZ);
        // copy last column from 'this'
        dest.m30(m30());
        dest.m31(m31());
        dest.m32(m32());
        dest.m33(m33());
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
        float nn10 = m10() * cosX + m20() * sinX;
        float nn11 = m11() * cosX + m21() * sinX;
        float nn12 = m12() * cosX + m22() * sinX;
        float nn20 = m10() * m_sinX + m20() * cosX;
        float nn21 = m11() * m_sinX + m21() * cosX;
        float nn22 = m12() * m_sinX + m22() * cosX;
        // rotateY
        float nn00 = m00() * cosY + nn20 * m_sinY;
        float nn01 = m01() * cosY + nn21 * m_sinY;
        float nn02 = m02() * cosY + nn22 * m_sinY;
        dest.m20(m00() * sinY + nn20 * cosY);
        dest.m21(m01() * sinY + nn21 * cosY);
        dest.m22(m02() * sinY + nn22 * cosY);
        dest.m23(0.0f);
        // rotateZ
        dest.m00(nn00 * cosZ + nn10 * sinZ);
        dest.m01(nn01 * cosZ + nn11 * sinZ);
        dest.m02(nn02 * cosZ + nn12 * sinZ);
        dest.m03(0.0f);
        dest.m10(nn00 * m_sinZ + nn10 * cosZ);
        dest.m11(nn01 * m_sinZ + nn11 * cosZ);
        dest.m12(nn02 * m_sinZ + nn12 * cosZ);
        dest.m13(0.0f);
        // copy last column from 'this'
        dest.m30(m30());
        dest.m31(m31());
        dest.m32(m32());
        dest.m33(m33());
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
        float nn00 = m00() * cosZ + m10() * sinZ;
        float nn01 = m01() * cosZ + m11() * sinZ;
        float nn02 = m02() * cosZ + m12() * sinZ;
        float nn03 = m03() * cosZ + m13() * sinZ;
        float nn10 = m00() * m_sinZ + m10() * cosZ;
        float nn11 = m01() * m_sinZ + m11() * cosZ;
        float nn12 = m02() * m_sinZ + m12() * cosZ;
        float nn13 = m03() * m_sinZ + m13() * cosZ;
        // rotateY
        float nn20 = nn00 * sinY + m20() * cosY;
        float nn21 = nn01 * sinY + m21() * cosY;
        float nn22 = nn02 * sinY + m22() * cosY;
        float nn23 = nn03 * sinY + m23() * cosY;
        dest.m00(nn00 * cosY + m20() * m_sinY);
        dest.m01(nn01 * cosY + m21() * m_sinY);
        dest.m02(nn02 * cosY + m22() * m_sinY);
        dest.m03(nn03 * cosY + m23() * m_sinY);
        // rotateX
        dest.m10(nn10 * cosX + nn20 * sinX);
        dest.m11(nn11 * cosX + nn21 * sinX);
        dest.m12(nn12 * cosX + nn22 * sinX);
        dest.m13(nn13 * cosX + nn23 * sinX);
        dest.m20(nn10 * m_sinX + nn20 * cosX);
        dest.m21(nn11 * m_sinX + nn21 * cosX);
        dest.m22(nn12 * m_sinX + nn22 * cosX);
        dest.m23(nn13 * m_sinX + nn23 * cosX);
        // copy last column from 'this'
        dest.m30(m30());
        dest.m31(m31());
        dest.m32(m32());
        dest.m33(m33());
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
        float nn00 = m00() * cosZ + m10() * sinZ;
        float nn01 = m01() * cosZ + m11() * sinZ;
        float nn02 = m02() * cosZ + m12() * sinZ;
        float nn10 = m00() * m_sinZ + m10() * cosZ;
        float nn11 = m01() * m_sinZ + m11() * cosZ;
        float nn12 = m02() * m_sinZ + m12() * cosZ;
        // rotateY
        float nn20 = nn00 * sinY + m20() * cosY;
        float nn21 = nn01 * sinY + m21() * cosY;
        float nn22 = nn02 * sinY + m22() * cosY;
        dest.m00(nn00 * cosY + m20() * m_sinY);
        dest.m01(nn01 * cosY + m21() * m_sinY);
        dest.m02(nn02 * cosY + m22() * m_sinY);
        dest.m03(0.0f);
        // rotateX
        dest.m10(nn10 * cosX + nn20 * sinX);
        dest.m11(nn11 * cosX + nn21 * sinX);
        dest.m12(nn12 * cosX + nn22 * sinX);
        dest.m13(0.0f);
        dest.m20(nn10 * m_sinX + nn20 * cosX);
        dest.m21(nn11 * m_sinX + nn21 * cosX);
        dest.m22(nn12 * m_sinX + nn22 * cosX);
        dest.m23(0.0f);
        // copy last column from 'this'
        dest.m30(m30());
        dest.m31(m31());
        dest.m32(m32());
        dest.m33(m33());
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
        float nn20 = m00() * sinY + m20() * cosY;
        float nn21 = m01() * sinY + m21() * cosY;
        float nn22 = m02() * sinY + m22() * cosY;
        float nn23 = m03() * sinY + m23() * cosY;
        float nn00 = m00() * cosY + m20() * m_sinY;
        float nn01 = m01() * cosY + m21() * m_sinY;
        float nn02 = m02() * cosY + m22() * m_sinY;
        float nn03 = m03() * cosY + m23() * m_sinY;
        // rotateX
        float nn10 = m10() * cosX + nn20 * sinX;
        float nn11 = m11() * cosX + nn21 * sinX;
        float nn12 = m12() * cosX + nn22 * sinX;
        float nn13 = m13() * cosX + nn23 * sinX;
        dest.m20(m10() * m_sinX + nn20 * cosX);
        dest.m21(m11() * m_sinX + nn21 * cosX);
        dest.m22(m12() * m_sinX + nn22 * cosX);
        dest.m23(m13() * m_sinX + nn23 * cosX);
        // rotateZ
        dest.m00(nn00 * cosZ + nn10 * sinZ);
        dest.m01(nn01 * cosZ + nn11 * sinZ);
        dest.m02(nn02 * cosZ + nn12 * sinZ);
        dest.m03(nn03 * cosZ + nn13 * sinZ);
        dest.m10(nn00 * m_sinZ + nn10 * cosZ);
        dest.m11(nn01 * m_sinZ + nn11 * cosZ);
        dest.m12(nn02 * m_sinZ + nn12 * cosZ);
        dest.m13(nn03 * m_sinZ + nn13 * cosZ);
        // copy last column from 'this'
        dest.m30(m30());
        dest.m31(m31());
        dest.m32(m32());
        dest.m33(m33());
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
        float nn20 = m00() * sinY + m20() * cosY;
        float nn21 = m01() * sinY + m21() * cosY;
        float nn22 = m02() * sinY + m22() * cosY;
        float nn00 = m00() * cosY + m20() * m_sinY;
        float nn01 = m01() * cosY + m21() * m_sinY;
        float nn02 = m02() * cosY + m22() * m_sinY;
        // rotateX
        float nn10 = m10() * cosX + nn20 * sinX;
        float nn11 = m11() * cosX + nn21 * sinX;
        float nn12 = m12() * cosX + nn22 * sinX;
        dest.m20(m10() * m_sinX + nn20 * cosX);
        dest.m21(m11() * m_sinX + nn21 * cosX);
        dest.m22(m12() * m_sinX + nn22 * cosX);
        dest.m23(0.0f);
        // rotateZ
        dest.m00(nn00 * cosZ + nn10 * sinZ);
        dest.m01(nn01 * cosZ + nn11 * sinZ);
        dest.m02(nn02 * cosZ + nn12 * sinZ);
        dest.m03(0.0f);
        dest.m10(nn00 * m_sinZ + nn10 * cosZ);
        dest.m11(nn01 * m_sinZ + nn11 * cosZ);
        dest.m12(nn02 * m_sinZ + nn12 * cosZ);
        dest.m13(0.0f);
        // copy last column from 'this'
        dest.m30(m30());
        dest.m31(m31());
        dest.m32(m32());
        dest.m33(m33());
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
        float nn00 = m00() * rn00 + m10() * rn01 + m20() * rn02;
        float nn01 = m01() * rn00 + m11() * rn01 + m21() * rn02;
        float nn02 = m02() * rn00 + m12() * rn01 + m22() * rn02;
        float nn03 = m03() * rn00 + m13() * rn01 + m23() * rn02;
        float nn10 = m00() * rn10 + m10() * rn11 + m20() * rn12;
        float nn11 = m01() * rn10 + m11() * rn11 + m21() * rn12;
        float nn12 = m02() * rn10 + m12() * rn11 + m22() * rn12;
        float nn13 = m03() * rn10 + m13() * rn11 + m23() * rn12;
        // set non-dependent values directly
        dest.m20(m00() * rn20 + m10() * rn21 + m20() * rn22);
        dest.m21(m01() * rn20 + m11() * rn21 + m21() * rn22);
        dest.m22(m02() * rn20 + m12() * rn21 + m22() * rn22);
        dest.m23(m03() * rn20 + m13() * rn21 + m23() * rn22);
        // set other values
        dest.m00(nn00);
        dest.m01(nn01);
        dest.m02(nn02);
        dest.m03(nn03);
        dest.m10(nn10);
        dest.m11(nn11);
        dest.m12(nn12);
        dest.m13(nn13);
        dest.m30(m30());
        dest.m31(m31());
        dest.m32(m32());
        dest.m33(m33());

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
        dest.m00(m00());
        dest.m01(m01());
        dest.m02(m02());
        dest.m03(m03());
        dest.m10(m10());
        dest.m11(m11());
        dest.m12(m12());
        dest.m13(m13());
        dest.m20(m20());
        dest.m21(m21());
        dest.m22(m22());
        dest.m23(m23());
        dest.m30(m00() * x + m10() * y + m20() * z + m30());
        dest.m31(m01() * x + m11() * y + m21() * z + m31());
        dest.m32(m02() * x + m12() * y + m22() * z + m32());
        dest.m33(m03() * x + m13() * y + m23() * z + m33());
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
        // m00(), m11(), m22(), m33(1
        // m30() = x, m31() = y, m32() = z
        // all others = 0
        c.m30(c.m00() * x + c.m10() * y + c.m20() * z + c.m30());
        c.m31(c.m01() * x + c.m11() * y + c.m21() * z + c.m31());
        c.m32(c.m02() * x + c.m12() * y + c.m22() * z + c.m32());
        c.m33(c.m03() * x + c.m13() * y + c.m23() * z + c.m33());
        return this;
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeFloat(m00());
        out.writeFloat(m01());
        out.writeFloat(m02());
        out.writeFloat(m03());
        out.writeFloat(m10());
        out.writeFloat(m11());
        out.writeFloat(m12());
        out.writeFloat(m13());
        out.writeFloat(m20());
        out.writeFloat(m21());
        out.writeFloat(m22());
        out.writeFloat(m23());
        out.writeFloat(m30());
        out.writeFloat(m31());
        out.writeFloat(m32());
        out.writeFloat(m33());
    }

    public void readExternal(ObjectInput in) throws IOException,
            ClassNotFoundException {
        m00(in.readFloat());
        m01(in.readFloat());
        m02(in.readFloat());
        m03(in.readFloat());
        m10(in.readFloat());
        m11(in.readFloat());
        m12(in.readFloat());
        m13(in.readFloat());
        m20(in.readFloat());
        m21(in.readFloat());
        m22(in.readFloat());
        m23(in.readFloat());
        m30(in.readFloat());
        m31(in.readFloat());
        m32(in.readFloat());
        m33(in.readFloat());
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
        dest.m30(m00() * rn30 + m10() * rn31 + m20() * rn32 + m30());
        dest.m31(m01() * rn30 + m11() * rn31 + m21() * rn32 + m31());
        dest.m32(m02() * rn30 + m12() * rn31 + m22() * rn32 + m32());
        dest.m33(m03() * rn30 + m13() * rn31 + m23() * rn32 + m33());
        dest.m00(m00() * rn00);
        dest.m01(m01() * rn00);
        dest.m02(m02() * rn00);
        dest.m03(m03() * rn00);
        dest.m10(m10() * rn11);
        dest.m11(m11() * rn11);
        dest.m12(m12() * rn11);
        dest.m13(m13() * rn11);
        dest.m20(m20() * rn22);
        dest.m21(m21() * rn22);
        dest.m22(m22() * rn22);
        dest.m23(m23() * rn22);

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
        m00(2.0f / (right - left));
        m01(0.0f);
        m02(0.0f);
        m03(0.0f);
        m10(0.0f);
        m11(2.0f / (top - bottom));
        m12(0.0f);
        m13(0.0f);
        m20(0.0f);
        m21(0.0f);
        m22((zZeroToOne ? 1.0f : 2.0f) / (zNear - zFar));
        m23(0.0f);
        m30((right + left) / (left - right));
        m31((top + bottom) / (bottom - top));
        m32((zZeroToOne ? zNear : (zFar + zNear)) / (zNear - zFar));
        m33(1.0f);
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
        dest.m30(m20() * rn32 + m30());
        dest.m31(m21() * rn32 + m31());
        dest.m32(m22() * rn32 + m32());
        dest.m33(m23() * rn32 + m33());
        dest.m00(m00() * rn00);
        dest.m01(m01() * rn00);
        dest.m02(m02() * rn00);
        dest.m03(m03() * rn00);
        dest.m10(m10() * rn11);
        dest.m11(m11() * rn11);
        dest.m12(m12() * rn11);
        dest.m13(m13() * rn11);
        dest.m20(m20() * rn22);
        dest.m21(m21() * rn22);
        dest.m22(m22() * rn22);
        dest.m23(m23() * rn22);

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
        m00(2.0f / width);
        m01(0.0f);
        m02(0.0f);
        m03(0.0f);
        m10(0.0f);
        m11(2.0f / height);
        m12(0.0f);
        m13(0.0f);
        m20(0.0f);
        m21(0.0f);
        m22((zZeroToOne ? 1.0f : 2.0f) / (zNear - zFar));
        m23(0.0f);
        m30(0.0f);
        m31(0.0f);
        m32((zZeroToOne ? zNear : (zFar + zNear)) / (zNear - zFar));
        m33(1.0f);
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
        dest.m30(m00() * rn30 + m10() * rn31 + m30());
        dest.m31(m01() * rn30 + m11() * rn31 + m31());
        dest.m32(m02() * rn30 + m12() * rn31 + m32());
        dest.m33(m03() * rn30 + m13() * rn31 + m33());
        dest.m00(m00() * rn00);
        dest.m01(m01() * rn00);
        dest.m02(m02() * rn00);
        dest.m03(m03() * rn00);
        dest.m10(m10() * rn11);
        dest.m11(m11() * rn11);
        dest.m12(m12() * rn11);
        dest.m13(m13() * rn11);
        dest.m20(-m20());
        dest.m21(-m21());
        dest.m22(-m22());
        dest.m23(-m23());

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
        m00(2.0f / (right - left));
        m01(0.0f);
        m02(0.0f);
        m03(0.0f);
        m10(0.0f);
        m11(2.0f / (top - bottom));
        m12(0.0f);
        m13(0.0f);
        m20(0.0f);
        m21(0.0f);
        m22(-1.0f);
        m23(0.0f);
        m30(-(right + left) / (right - left));
        m31(-(top + bottom) / (top - bottom));
        m32(0.0f);
        m33(1.0f);
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
        float nn00 = m00() * rn00 + m10() * rn01 + m20() * rn02;
        float nn01 = m01() * rn00 + m11() * rn01 + m21() * rn02;
        float nn02 = m02() * rn00 + m12() * rn01 + m22() * rn02;
        float nn03 = m03() * rn00 + m13() * rn01 + m23() * rn02;
        float nn10 = m00() * rn10 + m10() * rn11 + m20() * rn12;
        float nn11 = m01() * rn10 + m11() * rn11 + m21() * rn12;
        float nn12 = m02() * rn10 + m12() * rn11 + m22() * rn12;
        float nn13 = m03() * rn10 + m13() * rn11 + m23() * rn12;
        dest.m20(m00() * rn20 + m10() * rn21 + m20() * rn22);
        dest.m21(m01() * rn20 + m11() * rn21 + m21() * rn22);
        dest.m22(m02() * rn20 + m12() * rn21 + m22() * rn22);
        dest.m23(m03() * rn20 + m13() * rn21 + m23() * rn22);
        // set the rest of the matrix elements
        dest.m00(nn00);
        dest.m01(nn01);
        dest.m02(nn02);
        dest.m03(nn03);
        dest.m10(nn10);
        dest.m11(nn11);
        dest.m12(nn12);
        dest.m13(nn13);
        dest.m30(m30());
        dest.m31(m31());
        dest.m32(m32());
        dest.m33(m33());

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

        m00(rightX);
        m01(upnX);
        m02(-dirnX);
        m03(0.0f);
        m10(rightY);
        m11(upnY);
        m12(-dirnY);
        m13(0.0f);
        m20(rightZ);
        m21(upnZ);
        m22(-dirnZ);
        m23(0.0f);
        m30(0.0f);
        m31(0.0f);
        m32(0.0f);
        m33(1.0f);

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

        m00(leftX);
        m01(upnX);
        m02(dirX);
        m03(0.0f);
        m10(leftY);
        m11(upnY);
        m12(dirY);
        m13(0.0f);
        m20(leftZ);
        m21(upnZ);
        m22(dirZ);
        m23(0.0f);
        m30(-(leftX * eyeX + leftY * eyeY + leftZ * eyeZ));
        m31(-(upnX * eyeX + upnY * eyeY + upnZ * eyeZ));
        m32(-(dirX * eyeX + dirY * eyeY + dirZ * eyeZ));
        m33(1.0f);

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
        dest.m30(m00() * rn30 + m10() * rn31 + m20() * rn32 + m30());
        dest.m31(m01() * rn30 + m11() * rn31 + m21() * rn32 + m31());
        dest.m32(m02() * rn30 + m12() * rn31 + m22() * rn32 + m32());
        dest.m33(m03() * rn30 + m13() * rn31 + m23() * rn32 + m33());
        // introduce temporaries for dependent results
        float nn00 = m00() * rn00 + m10() * rn01 + m20() * rn02;
        float nn01 = m01() * rn00 + m11() * rn01 + m21() * rn02;
        float nn02 = m02() * rn00 + m12() * rn01 + m22() * rn02;
        float nn03 = m03() * rn00 + m13() * rn01 + m23() * rn02;
        float nn10 = m00() * rn10 + m10() * rn11 + m20() * rn12;
        float nn11 = m01() * rn10 + m11() * rn11 + m21() * rn12;
        float nn12 = m02() * rn10 + m12() * rn11 + m22() * rn12;
        float nn13 = m03() * rn10 + m13() * rn11 + m23() * rn12;
        dest.m20(m00() * rn20 + m10() * rn21 + m20() * rn22);
        dest.m21(m01() * rn20 + m11() * rn21 + m21() * rn22);
        dest.m22(m02() * rn20 + m12() * rn21 + m22() * rn22);
        dest.m23(m03() * rn20 + m13() * rn21 + m23() * rn22);
        // set the rest of the matrix elements
        dest.m00(nn00);
        dest.m01(nn01);
        dest.m02(nn02);
        dest.m03(nn03);
        dest.m10(nn10);
        dest.m11(nn11);
        dest.m12(nn12);
        dest.m13(nn13);

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
        float nn20 = m20() * rn22 - m30();
        float nn21 = m21() * rn22 - m31();
        float nn22 = m22() * rn22 - m32();
        float nn23 = m23() * rn22 - m33();
        dest.m00(m00() * rn00);
        dest.m01(m01() * rn00);
        dest.m02(m02() * rn00);
        dest.m03(m03() * rn00);
        dest.m10(m10() * rn11);
        dest.m11(m11() * rn11);
        dest.m12(m12() * rn11);
        dest.m13(m13() * rn11);
        dest.m30(m20() * rn32);
        dest.m31(m21() * rn32);
        dest.m32(m22() * rn32);
        dest.m33(m23() * rn32);
        dest.m20(nn20);
        dest.m21(nn21);
        dest.m22(nn22);
        dest.m23(nn23);

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
        m00(1.0f / (h * aspect));
        m01(0.0f);
        m02(0.0f);
        m03(0.0f);
        m10(0.0f);
        m11(1.0f / h);
        m12(0.0f);
        m13(0.0f);
        m20(0.0f);
        m21(0.0f);
        boolean farInf = zFar > 0 && Float.isInfinite(zFar);
        boolean nearInf = zNear > 0 && Float.isInfinite(zNear);
        if (farInf) {
            // See: "Infinite Projection Matrix" (http://www.terathon.com/gdc07_lengyel.pdf)
            float e = 1E-6f;
            m22(e - 1.0f);
            m32((e - (zZeroToOne ? 1.0f : 2.0f)) * zNear);
        } else if (nearInf) {
            float e = 1E-6f;
            m22((zZeroToOne ? 0.0f : 1.0f) - e);
            m32(((zZeroToOne ? 1.0f : 2.0f) - e) * zFar);
        } else {
            m22((zZeroToOne ? zFar : zFar + zNear) / (zNear - zFar));
            m32((zZeroToOne ? zFar : zFar + zFar) * zNear / (zNear - zFar));
        }
        m23(-1.0f);
        m30(0.0f);
        m31(0.0f);
        m33(0.0f);
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
        float nn20 = m00() * rn20 + m10() * rn21 + m20() * rn22 - m30();
        float nn21 = m01() * rn20 + m11() * rn21 + m21() * rn22 - m31();
        float nn22 = m02() * rn20 + m12() * rn21 + m22() * rn22 - m32();
        float nn23 = m03() * rn20 + m13() * rn21 + m23() * rn22 - m33();
        dest.m00(m00() * rn00);
        dest.m01(m01() * rn00);
        dest.m02(m02() * rn00);
        dest.m03(m03() * rn00);
        dest.m10(m10() * rn11);
        dest.m11(m11() * rn11);
        dest.m12(m12() * rn11);
        dest.m13(m13() * rn11);
        dest.m30(m20() * rn32);
        dest.m31(m21() * rn32);
        dest.m32(m22() * rn32);
        dest.m33(m23() * rn32);
        dest.m20(nn20);
        dest.m21(nn21);
        dest.m22(nn22);
        dest.m23(nn23);
        dest.m30(m30());
        dest.m31(m31());
        dest.m32(m32());
        dest.m33(m33());

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
        m00((zNear + zNear) / (right - left));
        m01(0.0f);
        m02(0.0f);
        m03(0.0f);
        m10(0.0f);
        m11((zNear + zNear) / (top - bottom));
        m12(0.0f);
        m13(0.0f);
        m20((right + left) / (right - left));
        m21((top + bottom) / (top - bottom));
        boolean farInf = zFar > 0 && Float.isInfinite(zFar);
        boolean nearInf = zNear > 0 && Float.isInfinite(zNear);
        if (farInf) {
            // See: "Infinite Projection Matrix" (http://www.terathon.com/gdc07_lengyel.pdf)
            float e = 1E-6f;
            m22(e - 1.0f);
            m32((e - (zZeroToOne ? 1.0f : 2.0f)) * zNear);
        } else if (nearInf) {
            float e = 1E-6f;
            m22((zZeroToOne ? 0.0f : 1.0f) - e);
            m32(((zZeroToOne ? 1.0f : 2.0f) - e) * zFar);
        } else {
            m22((zZeroToOne ? zFar : zFar + zNear) / (zNear - zFar));
            m32((zZeroToOne ? zFar : zFar + zFar) * zNear / (zNear - zFar));
        }
        m23(-1.0f);
        m30(0.0f);
        m31(0.0f);
        m33(0.0f);
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

        float nn00 = m00() * rn00 + m10() * rn01 + m20() * rn02;
        float nn01 = m01() * rn00 + m11() * rn01 + m21() * rn02;
        float nn02 = m02() * rn00 + m12() * rn01 + m22() * rn02;
        float nn03 = m03() * rn00 + m13() * rn01 + m23() * rn02;
        float nn10 = m00() * rn10 + m10() * rn11 + m20() * rn12;
        float nn11 = m01() * rn10 + m11() * rn11 + m21() * rn12;
        float nn12 = m02() * rn10 + m12() * rn11 + m22() * rn12;
        float nn13 = m03() * rn10 + m13() * rn11 + m23() * rn12;
        dest.m20(m00() * rn20 + m10() * rn21 + m20() * rn22);
        dest.m21(m01() * rn20 + m11() * rn21 + m21() * rn22);
        dest.m22(m02() * rn20 + m12() * rn21 + m22() * rn22);
        dest.m23(m03() * rn20 + m13() * rn21 + m23() * rn22);
        dest.m00(nn00);
        dest.m01(nn01);
        dest.m02(nn02);
        dest.m03(nn03);
        dest.m10(nn10);
        dest.m11(nn11);
        dest.m12(nn12);
        dest.m13(nn13);
        dest.m30(m30());
        dest.m31(m31());
        dest.m32(m32());
        dest.m33(m33());

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
        float a = m00() * m11() - m01() * m10();
        float b = m00() * m12() - m02() * m10();
        float c = m00() * m13() - m03() * m10();
        float d = m01() * m12() - m02() * m11();
        float e = m01() * m13() - m03() * m11();
        float f = m02() * m13() - m03() * m12();
        float g = m20() * m31() - m21() * m30();
        float h = m20() * m32() - m22() * m30();
        float i = m20() * m33() - m23() * m30();
        float j = m21() * m32() - m22() * m31();
        float k = m21() * m33() - m23() * m31();
        float l = m22() * m33() - m23() * m32();
        float det = a * l - b * k + c * j + d * i - e * h + f * g;
        det = 1.0f / det;
        float imsM00 = ( m11() * l - m12() * k + m13() * j) * det;
        float imsM01 = (-m01() * l + m02() * k - m03() * j) * det;
        float imsM02 = ( m31() * f - m32() * e + m33() * d) * det;
        float imsM03 = (-m21() * f + m22() * e - m23() * d) * det;
        float imsM10 = (-m10() * l + m12() * i - m13() * h) * det;
        float imsM11 = ( m00() * l - m02() * i + m03() * h) * det;
        float imsM12 = (-m30() * f + m32() * c - m33() * b) * det;
        float imsM13 = ( m20() * f - m22() * c + m23() * b) * det;
        float imsM20 = ( m10() * k - m11() * i + m13() * g) * det;
        float imsM21 = (-m00() * k + m01() * i - m03() * g) * det;
        float imsM22 = ( m30() * e - m31() * c + m33() * a) * det;
        float imsM23 = (-m20() * e + m21() * c - m23() * a) * det;
        float imsM30 = (-m10() * j + m11() * h - m12() * g) * det;
        float imsM31 = ( m00() * j - m01() * h + m02() * g) * det;
        float imsM32 = (-m30() * d + m31() * b - m32() * a) * det;
        float imsM33 = ( m20() * d - m21() * b + m22() * a) * det;
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
        float a = m00() * m11() - m01() * m10();
        float b = m00() * m12() - m02() * m10();
        float c = m00() * m13() - m03() * m10();
        float d = m01() * m12() - m02() * m11();
        float e = m01() * m13() - m03() * m11();
        float f = m02() * m13() - m03() * m12();
        float g = m20() * m31() - m21() * m30();
        float h = m20() * m32() - m22() * m30();
        float i = m20() * m33() - m23() * m30();
        float j = m21() * m32() - m22() * m31();
        float k = m21() * m33() - m23() * m31();
        float l = m22() * m33() - m23() * m32();
        float det = a * l - b * k + c * j + d * i - e * h + f * g;
        det = 1.0f / det;
        float imsM00 = ( m11() * l - m12() * k + m13() * j) * det;
        float imsM01 = (-m01() * l + m02() * k - m03() * j) * det;
        float imsM02 = ( m31() * f - m32() * e + m33() * d) * det;
        float imsM03 = (-m21() * f + m22() * e - m23() * d) * det;
        float imsM10 = (-m10() * l + m12() * i - m13() * h) * det;
        float imsM11 = ( m00() * l - m02() * i + m03() * h) * det;
        float imsM12 = (-m30() * f + m32() * c - m33() * b) * det;
        float imsM13 = ( m20() * f - m22() * c + m23() * b) * det;
        float imsM20 = ( m10() * k - m11() * i + m13() * g) * det;
        float imsM21 = (-m00() * k + m01() * i - m03() * g) * det;
        float imsM22 = ( m30() * e - m31() * c + m33() * a) * det;
        float imsM23 = (-m20() * e + m21() * c - m23() * a) * det;
        float imsM30 = (-m10() * j + m11() * h - m12() * g) * det;
        float imsM31 = ( m00() * j - m01() * h + m02() * g) * det;
        float imsM32 = (-m30() * d + m31() * b - m32() * a) * det;
        float imsM33 = ( m20() * d - m21() * b + m22() * a) * det;
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
        dest.x = m00() * ndcX + m10() * ndcY + m20() * ndcZ + m30();
        dest.y = m01() * ndcX + m11() * ndcY + m21() * ndcZ + m31();
        dest.z = m02() * ndcX + m12() * ndcY + m22() * ndcZ + m32();
        dest.w = m03() * ndcX + m13() * ndcY + m23() * ndcZ + m33();
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
        dest.x = m00() * ndcX + m10() * ndcY + m20() * ndcZ + m30();
        dest.y = m01() * ndcX + m11() * ndcY + m21() * ndcZ + m31();
        dest.z = m02() * ndcX + m12() * ndcY + m22() * ndcZ + m32();
        float w = m03() * ndcX + m13() * ndcY + m23() * ndcZ + m33();
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
        winCoordsDest.x = m00() * x + m10() * y + m20() * z + m30();
        winCoordsDest.y = m01() * x + m11() * y + m21() * z + m31();
        winCoordsDest.z = m02() * x + m12() * y + m22() * z + m32();
        winCoordsDest.w = m03() * x + m13() * y + m23() * z + m33();
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
        winCoordsDest.x = m00() * x + m10() * y + m20() * z + m30();
        winCoordsDest.y = m01() * x + m11() * y + m21() * z + m31();
        winCoordsDest.z = m02() * x + m12() * y + m22() * z + m32();
        float w = m03() * x + m13() * y + m23() * z + m33();
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
        dest.m30(m00() * rn30 + m10() * rn31 + m20() * rn32 + m30());
        dest.m31(m01() * rn30 + m11() * rn31 + m21() * rn32 + m31());
        dest.m32(m02() * rn30 + m12() * rn31 + m22() * rn32 + m32());
        dest.m33(m03() * rn30 + m13() * rn31 + m23() * rn32 + m33());
        float nn00 = m00() * rn00 + m10() * rn01 + m20() * rn02;
        float nn01 = m01() * rn00 + m11() * rn01 + m21() * rn02;
        float nn02 = m02() * rn00 + m12() * rn01 + m22() * rn02;
        float nn03 = m03() * rn00 + m13() * rn01 + m23() * rn02;
        float nn10 = m00() * rn10 + m10() * rn11 + m20() * rn12;
        float nn11 = m01() * rn10 + m11() * rn11 + m21() * rn12;
        float nn12 = m02() * rn10 + m12() * rn11 + m22() * rn12;
        float nn13 = m03() * rn10 + m13() * rn11 + m23() * rn12;
        dest.m20(m00() * rn20 + m10() * rn21 + m20() * rn22);
        dest.m21(m01() * rn20 + m11() * rn21 + m21() * rn22);
        dest.m22(m02() * rn20 + m12() * rn21 + m22() * rn22);
        dest.m23(m03() * rn20 + m13() * rn21 + m23() * rn22);
        dest.m00(nn00);
        dest.m01(nn01);
        dest.m02(nn02);
        dest.m03(nn03);
        dest.m10(nn10);
        dest.m11(nn11);
        dest.m12(nn12);
        dest.m13(nn13);

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
        m00(1.0f - da * a);
        m01(-da * b);
        m02(-da * c);
        m03(0.0f);
        m10(-db * a);
        m11(1.0f - db * b);
        m12(-db * c);
        m13(0.0f);
        m20(-dc * a);
        m21(-dc * b);
        m22(1.0f - dc * c);
        m23(0.0f);
        m30(-dd * a);
        m31(-dd * b);
        m32(-dd * c);
        m33(1.0f);
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
            dest.x = m00();
            dest.y = m10();
            dest.z = m20();
            dest.w = m30();
            break;
        case 1:
            dest.x = m01();
            dest.y = m11();
            dest.z = m21();
            dest.w = m31();
            break;
        case 2:
            dest.x = m02();
            dest.y = m12();
            dest.z = m22();
            dest.w = m32();
            break;
        case 3:
            dest.x = m03();
            dest.y = m13();
            dest.z = m23();
            dest.w = m33();
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
            dest.x = m00();
            dest.y = m01();
            dest.z = m02();
            dest.w = m03();
            break;
        case 1:
            dest.x = m10();
            dest.y = m11();
            dest.z = m12();
            dest.w = m13();
            break;
        case 2:
            dest.x = m20();
            dest.y = m21();
            dest.z = m22();
            dest.w = m23();
            break;
        case 3:
            dest.x = m30();
            dest.y = m31();
            dest.z = m32();
            dest.w = m32();
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
        dest.set((m11() * m22() - m21() * m12()) * s,
                 (m20() * m12() - m10() * m22()) * s,
                 (m10() * m21() - m20() * m11()) * s,
                 0.0f,
                 (m21() * m02() - m01() * m22()) * s,
                 (m00() * m22() - m20() * m02()) * s,
                 (m20() * m01() - m00() * m21()) * s,
                 0.0f,
                 (m01() * m12() - m11() * m02()) * s,
                 (m10() * m02() - m00() * m12()) * s,
                 (m00() * m11() - m10() * m01()) * s,
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
        dest.ms[Matrix3f.M00] = (m11() * m22() - m21() * m12()) * s;
        dest.ms[Matrix3f.M01] = (m20() * m12() - m10() * m22()) * s;
        dest.ms[Matrix3f.M02] = (m10() * m21() - m20() * m11()) * s;
        dest.ms[Matrix3f.M10] = (m21() * m02() - m01() * m22()) * s;
        dest.ms[Matrix3f.M11] = (m00() * m22() - m20() * m02()) * s;
        dest.ms[Matrix3f.M12] = (m20() * m01() - m00() * m21()) * s;
        dest.ms[Matrix3f.M20] = (m01() * m12() - m11() * m02()) * s;
        dest.ms[Matrix3f.M21] = (m10() * m02() - m00() * m12()) * s;
        dest.ms[Matrix3f.M22] = (m00() * m11() - m10() * m01()) * s;
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
        float invXlen = (float) (1.0 / Math.sqrt(m00() * m00() + m01() * m01() + m02() * m02()));
        float invYlen = (float) (1.0 / Math.sqrt(m10() * m10() + m11() * m11() + m12() * m12()));
        float invZlen = (float) (1.0 / Math.sqrt(m20() * m20() + m21() * m21() + m22() * m22()));
        dest.m00(m00() * invXlen); dest.m01(m01() * invXlen); dest.m02(m02() * invXlen);
        dest.m10(m10() * invYlen); dest.m11(m11() * invYlen); dest.m12(m12() * invYlen);
        dest.m20(m20() * invZlen); dest.m21(m21() * invZlen); dest.m22(m22() * invZlen);
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
        float invXlen = (float) (1.0 / Math.sqrt(m00() * m00() + m01() * m01() + m02() * m02()));
        float invYlen = (float) (1.0 / Math.sqrt(m10() * m10() + m11() * m11() + m12() * m12()));
        float invZlen = (float) (1.0 / Math.sqrt(m20() * m20() + m21() * m21() + m22() * m22()));
        dest.ms[Matrix3f.M00] = m00() * invXlen; dest.ms[Matrix3f.M01] = m01() * invXlen; dest.ms[Matrix3f.M02] = m02() * invXlen;
        dest.ms[Matrix3f.M10] = m10() * invYlen; dest.ms[Matrix3f.M11] = m11() * invYlen; dest.ms[Matrix3f.M12] = m12() * invYlen;
        dest.ms[Matrix3f.M20] = m20() * invZlen; dest.ms[Matrix3f.M21] = m21() * invZlen; dest.ms[Matrix3f.M22] = m22() * invZlen;
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
            planeEquation.set(m03() + m00(), m13() + m10(), m23() + m20(), m33() + m30()).normalize3();
            break;
        case PLANE_PX:
            planeEquation.set(m03() - m00(), m13() - m10(), m23() - m20(), m33() - m30()).normalize3();
            break;
        case PLANE_NY:
            planeEquation.set(m03() + m01(), m13() + m11(), m23() + m21(), m33() + m31()).normalize3();
            break;
        case PLANE_PY:
            planeEquation.set(m03() - m01(), m13() - m11(), m23() - m21(), m33() - m31()).normalize3();
            break;
        case PLANE_NZ:
            planeEquation.set(m03() + m02(), m13() + m12(), m23() + m22(), m33() + m32()).normalize3();
            break;
        case PLANE_PZ:
            planeEquation.set(m03() - m02(), m13() - m12(), m23() - m22(), m33() - m32()).normalize3();
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
            n1x = m03() + m00(); n1y = m13() + m10(); n1z = m23() + m20(); d1 = m33() + m30(); // left
            n2x = m03() + m01(); n2y = m13() + m11(); n2z = m23() + m21(); d2 = m33() + m31(); // bottom
            n3x = m03() + m02(); n3y = m13() + m12(); n3z = m23() + m22(); d3 = m33() + m32(); // near
            break;
        case CORNER_PXNYNZ: // right, bottom, near
            n1x = m03() - m00(); n1y = m13() - m10(); n1z = m23() - m20(); d1 = m33() - m30(); // right
            n2x = m03() + m01(); n2y = m13() + m11(); n2z = m23() + m21(); d2 = m33() + m31(); // bottom
            n3x = m03() + m02(); n3y = m13() + m12(); n3z = m23() + m22(); d3 = m33() + m32(); // near
            break;
        case CORNER_PXPYNZ: // right, top, near
            n1x = m03() - m00(); n1y = m13() - m10(); n1z = m23() - m20(); d1 = m33() - m30(); // right
            n2x = m03() - m01(); n2y = m13() - m11(); n2z = m23() - m21(); d2 = m33() - m31(); // top
            n3x = m03() + m02(); n3y = m13() + m12(); n3z = m23() + m22(); d3 = m33() + m32(); // near
            break;
        case CORNER_NXPYNZ: // left, top, near
            n1x = m03() + m00(); n1y = m13() + m10(); n1z = m23() + m20(); d1 = m33() + m30(); // left
            n2x = m03() - m01(); n2y = m13() - m11(); n2z = m23() - m21(); d2 = m33() - m31(); // top
            n3x = m03() + m02(); n3y = m13() + m12(); n3z = m23() + m22(); d3 = m33() + m32(); // near
            break;
        case CORNER_PXNYPZ: // right, bottom, far
            n1x = m03() - m00(); n1y = m13() - m10(); n1z = m23() - m20(); d1 = m33() - m30(); // right
            n2x = m03() + m01(); n2y = m13() + m11(); n2z = m23() + m21(); d2 = m33() + m31(); // bottom
            n3x = m03() - m02(); n3y = m13() - m12(); n3z = m23() - m22(); d3 = m33() - m32(); // far
            break;
        case CORNER_NXNYPZ: // left, bottom, far
            n1x = m03() + m00(); n1y = m13() + m10(); n1z = m23() + m20(); d1 = m33() + m30(); // left
            n2x = m03() + m01(); n2y = m13() + m11(); n2z = m23() + m21(); d2 = m33() + m31(); // bottom
            n3x = m03() - m02(); n3y = m13() - m12(); n3z = m23() - m22(); d3 = m33() - m32(); // far
            break;
        case CORNER_NXPYPZ: // left, top, far
            n1x = m03() + m00(); n1y = m13() + m10(); n1z = m23() + m20(); d1 = m33() + m30(); // left
            n2x = m03() - m01(); n2y = m13() - m11(); n2z = m23() - m21(); d2 = m33() - m31(); // top
            n3x = m03() - m02(); n3y = m13() - m12(); n3z = m23() - m22(); d3 = m33() - m32(); // far
            break;
        case CORNER_PXPYPZ: // right, top, far
            n1x = m03() - m00(); n1y = m13() - m10(); n1z = m23() - m20(); d1 = m33() - m30(); // right
            n2x = m03() - m01(); n2y = m13() - m11(); n2z = m23() - m21(); d2 = m33() - m31(); // top
            n3x = m03() - m02(); n3y = m13() - m12(); n3z = m23() - m22(); d3 = m33() - m32(); // far
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
        n1x = m03() + m00(); n1y = m13() + m10(); n1z = m23() + m20(); d1 = m33() + m30(); // left
        n2x = m03() - m00(); n2y = m13() - m10(); n2z = m23() - m20(); d2 = m33() - m30(); // right
        n3x = m03() - m01(); n3y = m13() - m11(); n3z = m23() - m21(); d3 = m33() - m31(); // top
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
        n1x = m03() + m01(); n1y = m13() + m11(); n1z = m23() + m21(); // bottom
        n2x = m01() - m03(); n2y = m11() - m13(); n2z = m21() - m23(); // top
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
        return m32() / (m23() + m22());
    }

    /**
     * Extract the far clip plane distance from <code>this</code> perspective projection matrix.
     * <p>
     * This method only works if <code>this</code> is a perspective projection matrix, for example obtained via {@link #perspective(float, float, float, float)}.
     * 
     * @return the far clip plane distance
     */
    public float perspectiveFar() {
        return m32() / (m22() - m23());
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
        float a = m10() * m23(), b = m13() * m21(), c = m10() * m21(), d = m11() * m23(), e = m13() * m20(), f = m11() * m20();
        float g = m03() * m20(), h = m01() * m23(), i = m01() * m20(), j = m03() * m21(), k = m00() * m23(), l = m00() * m21();
        float m = m00() * m13(), n = m03() * m11(), o = m00() * m11(), p = m01() * m13(), q = m03() * m10(), r = m01() * m10();
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
        dir.x = m10() * m21() - m11() * m20();
        dir.y = m20() * m01() - m21() * m00();
        dir.z = m00() * m11() - m01() * m10();
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
        dir.x = m02();
        dir.y = m12();
        dir.z = m22();
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
        dir.x = m11() * m22() - m12() * m21();
        dir.y = m02() * m21() - m01() * m22();
        dir.z = m01() * m12() - m02() * m11();
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
        dir.x = m00();
        dir.y = m10();
        dir.z = m20();
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
        dir.x = m12() * m20() - m10() * m22();
        dir.y = m00() * m22() - m02() * m20();
        dir.z = m02() * m10() - m00() * m12();
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
        dir.x = m01();
        dir.y = m11();
        dir.z = m21();
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
        float a = m00() * m11() - m01() * m10();
        float b = m00() * m12() - m02() * m10();
        float d = m01() * m12() - m02() * m11();
        float g = m20() * m31() - m21() * m30();
        float h = m20() * m32() - m22() * m30();
        float j = m21() * m32() - m22() * m31();
        origin.x = -m10() * j + m11() * h - m12() * g;
        origin.y =  m00() * j - m01() * h + m02() * g;
        origin.z = -m30() * d + m31() * b - m32() * a;
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
        float a = m00() * m11() - m01() * m10();
        float b = m00() * m12() - m02() * m10();
        float c = m00() * m13() - m03() * m10();
        float d = m01() * m12() - m02() * m11();
        float e = m01() * m13() - m03() * m11();
        float f = m02() * m13() - m03() * m12();
        float g = m20() * m31() - m21() * m30();
        float h = m20() * m32() - m22() * m30();
        float i = m20() * m33() - m23() * m30();
        float j = m21() * m32() - m22() * m31();
        float k = m21() * m33() - m23() * m31();
        float l = m22() * m33() - m23() * m32();
        float det = a * l - b * k + c * j + d * i - e * h + f * g;
        float invDet = 1.0f / det;
        float nn30 = (-m10() * j + m11() * h - m12() * g) * invDet;
        float nn31 = ( m00() * j - m01() * h + m02() * g) * invDet;
        float nn32 = (-m30() * d + m31() * b - m32() * a) * invDet;
        float nn33 = det / ( m20() * d - m21() * b + m22() * a);
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
        float nn00 = m00() * rn00 + m10() * rn01 + m20() * rn02 + m30() * rn03;
        float nn01 = m01() * rn00 + m11() * rn01 + m21() * rn02 + m31() * rn03;
        float nn02 = m02() * rn00 + m12() * rn01 + m22() * rn02 + m32() * rn03;
        float nn03 = m03() * rn00 + m13() * rn01 + m23() * rn02 + m33() * rn03;
        float nn10 = m00() * rn10 + m10() * rn11 + m20() * rn12 + m30() * rn13;
        float nn11 = m01() * rn10 + m11() * rn11 + m21() * rn12 + m31() * rn13;
        float nn12 = m02() * rn10 + m12() * rn11 + m22() * rn12 + m32() * rn13;
        float nn13 = m03() * rn10 + m13() * rn11 + m23() * rn12 + m33() * rn13;
        float nn20 = m00() * rn20 + m10() * rn21 + m20() * rn22 + m30() * rn23;
        float nn21 = m01() * rn20 + m11() * rn21 + m21() * rn22 + m31() * rn23;
        float nn22 = m02() * rn20 + m12() * rn21 + m22() * rn22 + m32() * rn23;
        float nn23 = m03() * rn20 + m13() * rn21 + m23() * rn22 + m33() * rn23;
        dest.m30(m00() * rn30 + m10() * rn31 + m20() * rn32 + m30() * rn33);
        dest.m31(m01() * rn30 + m11() * rn31 + m21() * rn32 + m31() * rn33);
        dest.m32(m02() * rn30 + m12() * rn31 + m22() * rn32 + m32() * rn33);
        dest.m33(m03() * rn30 + m13() * rn31 + m23() * rn32 + m33() * rn33);
        dest.m00(nn00);
        dest.m01(nn01);
        dest.m02(nn02);
        dest.m03(nn03);
        dest.m10(nn10);
        dest.m11(nn11);
        dest.m12(nn12);
        dest.m13(nn13);
        dest.m20(nn20);
        dest.m21(nn21);
        dest.m22(nn22);
        dest.m23(nn23);

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
        float a = planeTransform.m10();
        float b = planeTransform.m11();
        float c = planeTransform.m12();
        float d = -a * planeTransform.m30() - b * planeTransform.m31() - c * planeTransform.m32();
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
        float a = planeTransform.m10();
        float b = planeTransform.m11();
        float c = planeTransform.m12();
        float d = -a * planeTransform.m30() - b * planeTransform.m31() - c * planeTransform.m32();
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
        m00(leftX);
        m01(leftY);
        m02(leftZ);
        m03(0.0f);
        m10(up.x);
        m11(up.y);
        m12(up.z);
        m13(0.0f);
        m20(dirX);
        m21(dirY);
        m22(dirZ);
        m23(0.0f);
        m30(objPos.x);
        m31(objPos.y);
        m32(objPos.z);
        m33(1.0f);
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
        m00(leftX);
        m01(leftY);
        m02(leftZ);
        m03(0.0f);
        m10(upX);
        m11(upY);
        m12(upZ);
        m13(0.0f);
        m20(dirX);
        m21(dirY);
        m22(dirZ);
        m23(0.0f);
        m30(objPos.x);
        m31(objPos.y);
        m32(objPos.z);
        m33(1.0f);
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
        m00(1.0f - q11);
        m01(q01);
        m02(-q13);
        m03(0.0f);
        m10(q01);
        m11(1.0f - q00);
        m12(q03);
        m13(0.0f);
        m20(q13);
        m21(-q03);
        m22(1.0f - q11 - q00);
        m23(0.0f);
        m30(objPos.x);
        m31(objPos.y);
        m32(objPos.z);
        m33(1.0f);
        return this;
    }

    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Float.floatToIntBits(m00());
        result = prime * result + Float.floatToIntBits(m01());
        result = prime * result + Float.floatToIntBits(m02());
        result = prime * result + Float.floatToIntBits(m03());
        result = prime * result + Float.floatToIntBits(m10());
        result = prime * result + Float.floatToIntBits(m11());
        result = prime * result + Float.floatToIntBits(m12());
        result = prime * result + Float.floatToIntBits(m13());
        result = prime * result + Float.floatToIntBits(m20());
        result = prime * result + Float.floatToIntBits(m21());
        result = prime * result + Float.floatToIntBits(m22());
        result = prime * result + Float.floatToIntBits(m23());
        result = prime * result + Float.floatToIntBits(m30());
        result = prime * result + Float.floatToIntBits(m31());
        result = prime * result + Float.floatToIntBits(m32());
        result = prime * result + Float.floatToIntBits(m33());
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
        if (Float.floatToIntBits(m00()) != Float.floatToIntBits(other.m00()))
            return false;
        if (Float.floatToIntBits(m01()) != Float.floatToIntBits(other.m01()))
            return false;
        if (Float.floatToIntBits(m02()) != Float.floatToIntBits(other.m02()))
            return false;
        if (Float.floatToIntBits(m03()) != Float.floatToIntBits(other.m03()))
            return false;
        if (Float.floatToIntBits(m10()) != Float.floatToIntBits(other.m10()))
            return false;
        if (Float.floatToIntBits(m11()) != Float.floatToIntBits(other.m11()))
            return false;
        if (Float.floatToIntBits(m12()) != Float.floatToIntBits(other.m12()))
            return false;
        if (Float.floatToIntBits(m13()) != Float.floatToIntBits(other.m13()))
            return false;
        if (Float.floatToIntBits(m20()) != Float.floatToIntBits(other.m20()))
            return false;
        if (Float.floatToIntBits(m21()) != Float.floatToIntBits(other.m21()))
            return false;
        if (Float.floatToIntBits(m22()) != Float.floatToIntBits(other.m22()))
            return false;
        if (Float.floatToIntBits(m23()) != Float.floatToIntBits(other.m23()))
            return false;
        if (Float.floatToIntBits(m30()) != Float.floatToIntBits(other.m30()))
            return false;
        if (Float.floatToIntBits(m31()) != Float.floatToIntBits(other.m31()))
            return false;
        if (Float.floatToIntBits(m32()) != Float.floatToIntBits(other.m32()))
            return false;
        if (Float.floatToIntBits(m33()) != Float.floatToIntBits(other.m33()))
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
        dest.m30(m00() * tx + m10() * ty + m30());
        dest.m31(m01() * tx + m11() * ty + m31());
        dest.m32(m02() * tx + m12() * ty + m32());
        dest.m33(m03() * tx + m13() * ty + m33());
        dest.m00(m00() * sx);
        dest.m01(m01() * sx);
        dest.m02(m02() * sx);
        dest.m03(m03() * sx);
        dest.m10(m10() * sy);
        dest.m11(m11() * sy);
        dest.m12(m12() * sy);
        dest.m13(m13() * sy);
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
        return m03() == 0.0f && m13() == 0.0f && m23() == 0.0f && m33() == 1.0f;
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
        tmp = m00(); m00(other.m00()); other.m00(tmp);
        tmp = m01(); m01(other.m01()); other.m01(tmp);
        tmp = m02(); m02(other.m02()); other.m02(tmp);
        tmp = m03(); m03(other.m03()); other.m03(tmp);
        tmp = m10(); m10(other.m10()); other.m10(tmp);
        tmp = m11(); m11(other.m11()); other.m11(tmp);
        tmp = m12(); m12(other.m12()); other.m12(tmp);
        tmp = m13(); m13(other.m13()); other.m13(tmp);
        tmp = m20(); m20(other.m20()); other.m20(tmp);
        tmp = m21(); m21(other.m21()); other.m21(tmp);
        tmp = m22(); m22(other.m22()); other.m22(tmp);
        tmp = m23(); m23(other.m23()); other.m23(tmp);
        tmp = m30(); m30(other.m30()); other.m30(tmp);
        tmp = m31(); m31(other.m31()); other.m31(tmp);
        tmp = m32(); m32(other.m32()); other.m32(tmp);
        tmp = m33(); m33(other.m33()); other.m33(tmp);
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
            float x = ((t & 1) << 1) - 1.0f;
            float y = (((t >>> 1) & 1) << 1) - 1.0f;
            float z = (((t >>> 2) & 1) << 1) - 1.0f;
            float invW = 1.0f / (m03() * x + m13() * y + m23() * z + m33());
            float nx = (m00() * x + m10() * y + m20() * z + m30()) * invW;
            float ny = (m01() * x + m11() * y + m21() * z + m31()) * invW;
            float nz = (m02() * x + m12() * y + m22() * z + m32()) * invW;
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
            float invW = 1.0f / (m03() * c0X + m13() * c0Y + m23() * c0Z + m33());
            float p0x = (m00() * c0X + m10() * c0Y + m20() * c0Z + m30()) * invW;
            float p0y = (m01() * c0X + m11() * c0Y + m21() * c0Z + m31()) * invW;
            float p0z = (m02() * c0X + m12() * c0Y + m22() * c0Z + m32()) * invW;
            invW = 1.0f / (m03() * c1X + m13() * c1Y + m23() * c1Z + m33());
            float p1x = (m00() * c1X + m10() * c1Y + m20() * c1Z + m30()) * invW;
            float p1y = (m01() * c1X + m11() * c1Y + m21() * c1Z + m31()) * invW;
            float p1z = (m02() * c1X + m12() * c1Y + m22() * c1Z + m32()) * invW;
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
                    invW = 1.0f / (projector.m03() * ix + projector.m23() * iz + projector.m33());
                    float px = (projector.m00() * ix + projector.m20() * iz + projector.m30()) * invW;
                    float py = (projector.m01() * ix + projector.m21() * iz + projector.m31()) * invW;
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
        float invOldNear = (m23() + m22()) / m32();
        float invNearFar = 1.0f / (near - far);
        dest.m00(m00() * invOldNear * near);
        dest.m01(m01());
        dest.m02(m02());
        dest.m03(m03());
        dest.m10(m10());
        dest.m11(m11() * invOldNear * near);
        dest.m12(m12());
        dest.m13(m13());
        dest.m20(m20());
        dest.m21(m21());
        dest.m22((far + near) * invNearFar);
        dest.m23(m23());
        dest.m30(m30());
        dest.m31(m31());
        dest.m32((far + far) * near * invNearFar);
        dest.m33(m33());
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
            float invW = 1.0f / (m03() * x + m13() * y + m23() * z + m33());
            float wx = (m00() * x + m10() * y + m20() * z + m30()) * invW;
            float wy = (m01() * x + m11() * y + m21() * z + m31()) * invW;
            float wz = (m02() * x + m12() * y + m22() * z + m32()) * invW;
            invW = 1.0f / (view.m03() * wx + view.m13() * wy + view.m23() * wz + view.m33());
            float vx = view.m00() * wx + view.m10() * wy + view.m20() * wz + view.m30();
            float vy = view.m01() * wx + view.m11() * wy + view.m21() * wz + view.m31();
            float vz = (view.m02() * wx + view.m12() * wy + view.m22() * wz + view.m32()) * invW;
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
        float xax = m00() * minX, xay = m01() * minX, xaz = m02() * minX;
        float xbx = m00() * maxX, xby = m01() * maxX, xbz = m02() * maxX;
        float yax = m10() * minY, yay = m11() * minY, yaz = m12() * minY;
        float ybx = m10() * maxY, yby = m11() * maxY, ybz = m12() * maxY;
        float zax = m20() * minZ, zay = m21() * minZ, zaz = m22() * minZ;
        float zbx = m20() * maxZ, zby = m21() * maxZ, zbz = m22() * maxZ;
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
        outMin.x = xminx + yminx + zminx + m30();
        outMin.y = xminy + yminy + zminy + m31();
        outMin.z = xminz + yminz + zminz + m32();
        outMax.x = xmaxx + ymaxx + zmaxx + m30();
        outMax.y = xmaxy + ymaxy + zmaxy + m31();
        outMax.z = xmaxz + ymaxz + zmaxz + m32();
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
