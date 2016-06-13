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
import java.nio.FloatBuffer;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Contains the definition of a 3x2 matrix of floats, and associated functions to transform
 * it. This matrix is useful to represent 2D transformations.
 * <p>
 * The matrix is column-major to match OpenGL's interpretation, and it looks like this:
 * <p>
 *      m00  m10  m20<br>
 *      m01  m11  m21<br>
 * 
 * @author Richard Greenlees
 * @author Kai Burjack
 */
public class Matrix3x2f implements Externalizable {

    private static final long serialVersionUID = 1L;

    public float m00, m10, m20;
    public float m01, m11, m21;

    /**
     * Create a new {@link Matrix3x2f} and set it to {@link #identity() identity}.
     */
    public Matrix3x2f() {
        m00 = 1.0f;
        m11 = 1.0f;
    }

    /**
     * Create a new {@link Matrix3x2f} and make it a copy of the given matrix.
     * 
     * @param mat
     *          the {@link Matrix3x2f} to copy the values from
     */
    public Matrix3x2f(Matrix3x2f mat) {
        m00 = mat.m00;
        m10 = mat.m10;
        m20 = mat.m20;
        m01 = mat.m01;
        m11 = mat.m11;
        m21 = mat.m21;
    }

    /**
     * Create a new {@link Matrix3x2f} and make it a copy of the upper 3x2 of the given {@link Matrix3f}.
     * 
     * @param mat
     *          the {@link Matrix3f} to copy the values from
     */
    public Matrix3x2f(Matrix3f mat) {
        m00 = mat.m00();
        m10 = mat.m10();
        m20 = mat.m20();
        m01 = mat.m01();
        m11 = mat.m11();
        m21 = mat.m21();
    }

    /**
     * Create a new 3x2 matrix using the supplied float values. The order of the parameter is column-major, 
     * so the first three parameters specify the three elements of the first column.
     * 
     * @param m00
     *          the value of m00
     * @param m01
     *          the value of m01
     * @param m10
     *          the value of m10
     * @param m11
     *          the value of m11
     * @param m20
     *          the value of m20
     * @param m21
     *          the value of m21
     */
    public Matrix3x2f(float m00, float m01,
                      float m10, float m11,
                      float m20, float m21) {
        this.m00 = m00;
        this.m10 = m10;
        this.m20 = m20;
        this.m01 = m01;
        this.m11 = m11;
        this.m21 = m21;
    }

    /**
     * Return the value of the matrix element at column 0 and row 0.
     * 
     * @return the value of the matrix element
     */
    public float m00() {
        return m00;
    }
    /**
     * Return the value of the matrix element at column 0 and row 1.
     * 
     * @return the value of the matrix element
     */
    public float m01() {
        return m01;
    }
    /**
     * Return the value of the matrix element at column 1 and row 0.
     * 
     * @return the value of the matrix element
     */
    public float m10() {
        return m10;
    }
    /**
     * Return the value of the matrix element at column 1 and row 1.
     * 
     * @return the value of the matrix element
     */
    public float m11() {
        return m11;
    }
    /**
     * Return the value of the matrix element at column 2 and row 0.
     * 
     * @return the value of the matrix element
     */
    public float m20() {
        return m20;
    }
    /**
     * Return the value of the matrix element at column 2 and row 1.
     * 
     * @return the value of the matrix element
     */
    public float m21() {
        return m21;
    }

    /**
     * Set the value of the matrix element at column 0 and row 0
     * 
     * @param m00
     *          the new value
     * @return the value of the matrix element
     */
    public Matrix3x2f m00(float m00) {
        this.m00 = m00;
        return this;
    }
    /**
     * Set the value of the matrix element at column 0 and row 1
     * 
     * @param m01
     *          the new value
     * @return the value of the matrix element
     */
    public Matrix3x2f m01(float m01) {
        this.m01 = m01;
        return this;
    }
    /**
     * Set the value of the matrix element at column 1 and row 0
     * 
     * @param m10
     *          the new value
     * @return the value of the matrix element
     */
    public Matrix3x2f m10(float m10) {
        this.m10 = m10;
        return this;
    }
    /**
     * Set the value of the matrix element at column 1 and row 1
     * 
     * @param m11
     *          the new value
     * @return the value of the matrix element
     */
    public Matrix3x2f m11(float m11) {
        this.m11 = m11;
        return this;
    }
    /**
     * Set the value of the matrix element at column 2 and row 0
     * 
     * @param m20
     *          the new value
     * @return the value of the matrix element
     */
    public Matrix3x2f m20(float m20) {
        this.m20 = m20;
        return this;
    }
    /**
     * Set the value of the matrix element at column 2 and row 1
     * 
     * @param m21
     *          the new value
     * @return the value of the matrix element
     */
    public Matrix3x2f m21(float m21) {
        this.m21 = m21;
        return this;
    }

    /**
     * Set the elements of this matrix to the ones in <code>m</code>.
     * 
     * @param m
     *          the matrix to copy the elements from
     * @return this
     */
    public Matrix3x2f set(Matrix3x2f m) {
        m00 = m.m00;
        m10 = m.m10;
        m20 = m.m20;
        m01 = m.m01;
        m11 = m.m11;
        m21 = m.m21;
        return this;
    }

    /**
     * Set the elements of this matrix to the upper 3x2 of the given {@link Matrix3f}.
     *
     * @param mat
     *          the {@link Matrix3f} to copy the values from
     * @return this
     */
    public Matrix3x2f set(Matrix3f mat) {
        m00 = mat.m00();
        m10 = mat.m10();
        m20 = mat.m20();
        m01 = mat.m01();
        m11 = mat.m11();
        m21 = mat.m21();
        return this;
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
     * Multiply this matrix by the supplied <code>right</code> matrix.
     * <p>
     * The multiplication is performed by assuming two 3x3 matrices with their rast row being <tt>(0, 0, 1)</tt>.
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
    public Matrix3x2f mul(Matrix3x2f right) {
        return mul(right, this);
    }

    /**
     * Multiply this matrix by the supplied <code>right</code> matrix and store the result in <code>dest</code>.
     * <p>
     * The multiplication is performed by assuming two 3x3 matrices with their rast row being <tt>(0, 0, 1)</tt>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the <code>right</code> matrix,
     * then the new matrix will be <code>M * R</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * R * v</code>, the
     * transformation of the right matrix will be applied first!
     * 
     * @param right
     *          the right operand of the matrix multiplication
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Matrix3x2f mul(Matrix3x2f right, Matrix3x2f dest) {
        float nm00 = m00 * right.m00 + m10 * right.m01;
        float nm01 = m01 * right.m00 + m11 * right.m01;
        float nm10 = m00 * right.m10 + m10 * right.m11;
        float nm11 = m01 * right.m10 + m11 * right.m11;
        float nm20 = m00 * right.m20 + m10 * right.m21 + m20;
        float nm21 = m01 * right.m20 + m11 * right.m21 + m21;
        dest.m00 = nm00;
        dest.m01 = nm01;
        dest.m10 = nm10;
        dest.m11 = nm11;
        dest.m20 = nm20;
        dest.m21 = nm21;
        return dest;
    }

    /**
     * Set the values within this matrix to the supplied float values. The result looks like this:
     * <p>
     * m00, m10, m20<br>
     * m01, m11, m21<br>
     * 
     * @param m00
     *          the new value of m00
     * @param m01
     *          the new value of m01
     * @param m10
     *          the new value of m10
     * @param m11
     *          the new value of m11
     * @param m20
     *          the new value of m20
     * @param m21
     *          the new value of m21
     * @return this
     */
    public Matrix3x2f set(float m00, float m01,
                          float m10, float m11,
                          float m20, float m21) {
        this.m00 = m00;
        this.m01 = m01;
        this.m10 = m10;
        this.m11 = m11;
        this.m20 = m20;
        this.m21 = m21;
        return this;
    }

    /**
     * Set the values in this matrix based on the supplied float array. The result looks like this:
     * <p>
     * 0, 2, 4<br>
     * 1, 3, 5<br>
     * 
     * This method only uses the first 6 values, all others are ignored.
     * 
     * @param m
     *          the array to read the matrix values from
     * @return this
     */
    public Matrix3x2f set(float m[]) {
        m00 = m[0];
        m01 = m[1];
        m10 = m[2];
        m11 = m[3];
        m20 = m[4];
        m21 = m[5];
        return this;
    }

    /**
     * Return the determinant of this matrix.
     * 
     * @return the determinant
     */
    public float determinant() {
        return m00 * m11 - m01 * m10;
    }

    /**
     * Invert this matrix.
     *
     * @return this
     */
    public Matrix3x2f invert() {
        return invert(this);
    }

    /**
     * Invert the <code>this</code> matrix and store the result in <code>dest</code>.
     * 
     * @param dest
     *             will hold the result
     * @return dest
     */
    public Matrix3x2f invert(Matrix3x2f dest) {
        float s = determinant();
        // client must make sure that matrix is invertible
        s = 1.0f / s;
        float nm00 =  m11 * s;
        float nm01 = -m01 * s;
        float nm10 = -m10 * s;
        float nm11 =  m00 * s;
        float nm20 = (m10 * m21 - m20 * m11) * s;
        float nm21 = (m20 * m01 - m00 * m21) * s;
        dest.m00 = nm00;
        dest.m01 = nm01;
        dest.m10 = nm10;
        dest.m11 = nm11;
        dest.m20 = nm20;
        dest.m21 = nm21;
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
             + formatter.format(m01) + formatter.format(m11) + formatter.format(m21) + "\n"; //$NON-NLS-1$
    }

    /**
     * Get the current values of <code>this</code> matrix and store them into
     * <code>dest</code>.
     * <p>
     * This is the reverse method of {@link #set(Matrix3x2f)} and allows to obtain
     * intermediate calculation results when chaining multiple transformations.
     * 
     * @see #set(Matrix3x2f)
     * 
     * @param dest
     *          the destination matrix
     * @return the passed in destination
     */
    public Matrix3x2f get(Matrix3x2f dest) {
        return dest.set(this);
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
        arr[offset+0] = m00;
        arr[offset+1] = m01;
        arr[offset+2] = m10;
        arr[offset+3] = m11;
        arr[offset+4] = m20;
        arr[offset+5] = m21;
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
     * Set all values within this matrix to zero.
     * 
     * @return this
     */
    public Matrix3x2f zero() {
        m00 = 0.0f;
        m01 = 0.0f;
        m10 = 0.0f;
        m11 = 0.0f;
        m20 = 0.0f;
        m21 = 0.0f;
        return this;
    }
    
    /**
     * Set this matrix to the identity.
     * 
     * @return this
     */
    public Matrix3x2f identity() {
        m00 = 1.0f;
        m01 = 0.0f;
        m10 = 0.0f;
        m11 = 1.0f;
        m20 = 0.0f;
        m21 = 0.0f;
        return this;
    }

    /**
     * Apply scaling to the this matrix by scaling the base axes by the given <tt>xy.x</tt> and
     * <tt>xy.y</tt> factors, respectively and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>S</code> the scaling matrix,
     * then the new matrix will be <code>M * S</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * S * v</code>
     * , the scaling will be applied first!
     * 
     * @param xy
     *            the factors of the x and y component, respectively
     * @param dest
     *            will hold the result
     * @return dest
     */
    public Matrix3x2f scale(Vector2f xy, Matrix3x2f dest) {
        return scale(xy.x, xy.y, dest);
    }

    /**
     * Apply scaling to this matrix by scaling the base axes by the given <tt>xy.x</tt> and
     * <tt>xy.y</tt> factors, respectively.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>S</code> the scaling matrix,
     * then the new matrix will be <code>M * S</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * S * v</code>, the
     * scaling will be applied first!
     * 
     * @param xy
     *            the factors of the x and y component, respectively
     * @return this
     */
    public Matrix3x2f scale(Vector2f xy) {
        return scale(xy.x, xy.y, this);
    }

    /**
     * Apply scaling to this matrix by scaling the base axes by the given x and
     * y factors and store the result in <code>dest</code>.
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
     * @param dest
     *            will hold the result
     * @return dest
     */
    public Matrix3x2f scale(float x, float y, Matrix3x2f dest) {
        dest.m00 = m00 * x;
        dest.m01 = m01 * x;
        dest.m10 = m10 * y;
        dest.m11 = m11 * y;
        dest.m20 = m20;
        dest.m21 = m21;
        return dest;
    }

    /**
     * Apply scaling to this matrix by scaling the base axes by the given x and y factors.
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
     * @return this
     */
    public Matrix3x2f scale(float x, float y) {
        return scale(x, y, this);
    }

    /**
     * Apply scaling to this matrix by uniformly scaling all base axes by the given <code>xy</code> factor
     * and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>S</code> the scaling matrix,
     * then the new matrix will be <code>M * S</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * S * v</code>
     * , the scaling will be applied first!
     * 
     * @see #scale(float, float, Matrix3x2f)
     * 
     * @param xy
     *            the factor for all components
     * @param dest
     *            will hold the result
     * @return dest
     */
    public Matrix3x2f scale(float xy, Matrix3x2f dest) {
        return scale(xy, xy, dest);
    }

    /**
     * Apply scaling to this matrix by uniformly scaling all base axes by the given <code>xy</code> factor.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>S</code> the scaling matrix,
     * then the new matrix will be <code>M * S</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * S * v</code>
     * , the scaling will be applied first!
     * 
     * @see #scale(float, float)
     * 
     * @param xy
     *            the factor for all components
     * @return this
     */
    public Matrix3x2f scale(float xy) {
        return scale(xy, xy);
    }

    /**
     * Pre-multiply scaling to the this matrix by scaling the base axes by the given x and y factors and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>S</code> the scaling matrix,
     * then the new matrix will be <code>S * M</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>S * M * v</code>
     * , the scaling will be applied last!
     * 
     * @param x
     *            the factor of the x component
     * @param y
     *            the factor of the y component
     * @param dest
     *            will hold the result
     * @return dest
     */
    public Matrix3x2f scaleLocal(float x, float y, Matrix3x2f dest) {
        float nm00 = x * m00;
        float nm01 = y * m01;
        float nm10 = x * m10;
        float nm11 = y * m11;
        float nm20 = x * m20;
        float nm21 = y * m21;
        dest.m00 = nm00;
        dest.m01 = nm01;
        dest.m10 = nm10;
        dest.m11 = nm11;
        dest.m20 = nm20;
        dest.m21 = nm21;
        return dest;
    }

    /**
     * Pre-multiply scaling to this matrix by scaling the base axes by the given x and y factors.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>S</code> the scaling matrix,
     * then the new matrix will be <code>S * M</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>S * M * v</code>, the
     * scaling will be applied last!
     * 
     * @param x
     *            the factor of the x component
     * @param y
     *            the factor of the y component
     * @return this
     */
    public Matrix3x2f scaleLocal(float x, float y) {
        return scaleLocal(x, y, this);
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
     *             the scale factor in x and y
     * @return this
     */
    public Matrix3x2f scaling(float factor) {
        m00 = factor;
        m01 = 0.0f;
        m10 = 0.0f;
        m11 = factor;
        m20 = 0.0f;
        m21 = 0.0f;
        return this;
    }

    /**
     * Set this matrix to be a simple scale matrix.
     * 
     * @param x
     *             the scale in x
     * @param y
     *             the scale in y
     * @return this
     */
    public Matrix3x2f scaling(float x, float y) {
        m00 = x;
        m01 = 0.0f;
        m10 = 0.0f;
        m11 = y;
        m20 = 0.0f;
        m21 = 0.0f;
        return this;
    }

    /**
     * Set this matrix to be a simple scale matrix which scales the base axes by <tt>xy.x</tt> and <tt>xy.z</tt> respectively.
     * <p>
     * The resulting matrix can be multiplied against another transformation matrix to obtain an additional scaling.
     * <p>
     * In order to post-multiply a scaling transformation directly to a matrix use {@link #scale(Vector2f) scale()} instead.
     * 
     * @see #scale(Vector2f)
     * 
     * @param xy
     *             the scale in x and y respectively
     * @return this
     */
    public Matrix3x2f scaling(Vector2f xy) {
        return scaling(xy.x, xy.y);
    }

    /**
     * Set this matrix to a rotation matrix which rotates the given radians.
     * <p>
     * The produced rotation will rotate a vector counter-clockwise.
     * <p>
     * The resulting matrix can be multiplied against another transformation matrix to obtain an additional rotation.
     * <p>
     * In order to apply the rotation transformation to an existing transformation, use {@link #rotate(float) rotate()} instead.
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Rotation_matrix_from_axis_and_angle">http://en.wikipedia.org</a>
     * 
     * @see #rotate(float)
     * 
     * @param angle
     *          the angle in radians
     * @return this
     */
    public Matrix3x2f rotation(float angle) {
        float cos = (float) Math.cos(angle);
        float sin = (float) Math.sin(angle);
        m00 = cos;
        m01 = sin;
        m10 = -sin;
        m11 = cos;
        m20 = 0.0f;
        m21 = 0.0f;
        return this;
    }

    /**
     * Transform the given vector by this matrix.
     * 
     * @param v
     *          the vector to transform
     * @return v
     */
    public Vector3f transform(Vector3f v) {
        return v.mul(v.x, v.y, v.z, v);
    }

    /**
     * Transform the given vector by this matrix and store the result in <code>dest</code>.
     * 
     * @param v
     *          the vector to transform
     * @param dest
     * 			will hold the result
     * @return dest
     */
    public Vector3f transform(Vector3f v, Vector3f dest) {
        return v.mul(v.x, v.y, v.z, dest);
    }

    /**
     * Transform the given vector by this matrix and store the result in <code>dest</code>.
     * 
     * @param x
     *          the x coordinate of the vector to transform
     * @param y
     *          the y coordinate of the vector to transform
     * @param z
     *          the z coordinate of the vector to transform
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Vector3f transform(float x, float y, float z, Vector3f dest) {
        return dest.set(m00 * x + m10 * y + m20 * z, m01 * x + m11 * y + m21 * z, z);
    }

    /**
     * Transform a position by this matrix.
     * 
     * @param v
     *          the position vector to transform
     * @return v
     */
    public Vector2f transformPosition(Vector2f v) {
        return transformPosition(v.x, v.y, v);
    }

    /**
     * Transform a position by this matrix and store the result in <code>dest</code>.
     * 
     * @param v
     *          the position vector to transform
     * @param dest
     *          will hold the result
     * @return v
     */
    public Vector2f transformPosition(Vector2f v, Vector2f dest) {
        return transformPosition(v.x, v.y, dest);
    }

    /**
     * Transform the given position by this matrix and store the result in <code>dest</code>.
     * 
     * @param x
     *          the x coordinate of the position to transform
     * @param y
     *          the y coordinate of the position to transform
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Vector2f transformPosition(float x, float y, Vector2f dest) {
        return dest.set(m00 * x + m10 * y + m20, m01 * x + m11 * y + m21);
    }

    /**
     * Transform a direction by this matrix.
     * 
     * @param v
     *          the direction vector to transform
     * @return v
     */
    public Vector2f transformDirection(Vector2f v) {
        return transformDirection(v.x, v.y, v);
    }

    /**
     * Transform a direction by this matrix and store the result in <code>dest</code>.
     * 
     * @param v
     *          the direction vector to transform
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Vector2f transformDirection(Vector2f v, Vector2f dest) {
        return transformDirection(v.x, v.y, dest);
    }

    /**
     * Transform the given direction by this matrix and store the result in <code>dest</code>.
     * 
     * @param x
     *          the x coordinate of the direction to transform
     * @param y
     *          the y coordinate of the direction to transform
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Vector2f transformDirection(float x, float y, Vector2f dest) {
        return dest.set(m00 * x + m10 * y, m01 * x + m11 * y);
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeFloat(m00);
        out.writeFloat(m01);
        out.writeFloat(m10);
        out.writeFloat(m11);
        out.writeFloat(m20);
        out.writeFloat(m21);
    }

    public void readExternal(ObjectInput in) throws IOException,
            ClassNotFoundException {
        m00 = in.readFloat();
        m01 = in.readFloat();
        m10 = in.readFloat();
        m11 = in.readFloat();
        m20 = in.readFloat();
        m21 = in.readFloat();
    }

    /**
     * Apply rotation to this matrix by rotating the given amount of radians.
     * <p>
     * The produced rotation will rotate a vector counter-clockwise.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the rotation matrix,
     * then the new matrix will be <code>M * R</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * R * v</code>
     * , the rotation will be applied first!
     * 
     * @param ang
     *            the angle in radians
     * @return this
     */
    public Matrix3x2f rotate(float ang) {
        return rotate(ang, this);
    }

    /**
     * Apply rotation to this matrix by rotating the given amount of radians and store the result in <code>dest</code>.
     * <p>
     * The produced rotation will rotate a vector counter-clockwise.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the rotation matrix,
     * then the new matrix will be <code>M * R</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * R * v</code>
     * , the rotation will be applied first!
     * 
     * @param ang
     *            the angle in radians
     * @param dest
     *            will hold the result
     * @return dest
     */
    public Matrix3x2f rotate(float ang, Matrix3x2f dest) {
        float s = (float) Math.sin(ang);
        float c = (float) Math.cos(ang);
        float rm00 = c;
        float rm01 = s;
        float rm10 = -s;
        float rm11 = c;
        float nm00 = m00 * rm00 + m10 * rm01;
        float nm01 = m01 * rm00 + m11 * rm01;
        float nm10 = m00 * rm10 + m10 * rm11;
        float nm11 = m01 * rm10 + m11 * rm11;
        dest.m20 = m20;
        dest.m21 = m21;
        dest.m00 = nm00;
        dest.m01 = nm01;
        dest.m10 = nm10;
        dest.m11 = nm11;
        return dest;
    }

    /**
     * Pre-multiply a rotation to this matrix by rotating the given amount of radians and store the result in <code>dest</code>.
     * <p>
     * The produced rotation will rotate a vector counter-clockwise.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the rotation matrix,
     * then the new matrix will be <code>R * M</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>R * M * v</code>, the
     * rotation will be applied last!
     * <p>
     * In order to set the matrix to a rotation matrix without pre-multiplying the rotation
     * transformation, use {@link #rotation(float) rotation()}.
     * 
     * @see #rotation(float)
     * 
     * @param ang
     *            the angle in radians
     * @param dest
     *            will hold the result
     * @return dest
     */
    public Matrix3x2f rotateLocal(float ang, Matrix3x2f dest) {
        float s = (float) Math.sin(ang);
        float c = (float) Math.cos(ang);
        float lm00 = c;
        float lm01 = s;
        float lm10 = -s;
        float lm11 = c;
        float nm00 = lm00 * m00 + lm10 * m01;
        float nm01 = lm01 * m00 + lm11 * m01;
        float nm10 = lm00 * m10 + lm10 * m11;
        float nm11 = lm01 * m10 + lm11 * m11;
        float nm20 = lm00 * m20 + lm10 * m21;
        float nm21 = lm01 * m20 + lm11 * m21;
        dest.m00 = nm00;
        dest.m01 = nm01;
        dest.m10 = nm10;
        dest.m11 = nm11;
        dest.m20 = nm20;
        dest.m21 = nm21;
        return dest;
    }

    /**
     * Pre-multiply a rotation to this matrix by rotating the given amount of radians.
     * <p>
     * The produced rotation will rotate a vector counter-clockwise around the rotation axis.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the rotation matrix,
     * then the new matrix will be <code>R * M</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>R * M * v</code>, the
     * rotation will be applied last!
     * <p>
     * In order to set the matrix to a rotation matrix without pre-multiplying the rotation
     * transformation, use {@link #rotation(float) rotation()}.
     * 
     * @see #rotation(float)
     * 
     * @param ang
     *            the angle in radians
     * @return this
     */
    public Matrix3x2f rotateLocal(float ang) {
        return rotateLocal(ang, this);
    }

    /**
     * Set this matrix to be a simple translation matrix in a two-dimensional coordinate system.
     * <p>
     * The resulting matrix can be multiplied against another transformation
     * matrix to obtain an additional translation.
     * <p>
     * In order to apply a translation via to an already existing transformation
     * matrix, use {@link #translate(float, float) translate()} instead.
     * 
     * @see #translate(float, float)
     * 
     * @param x
     *          the units to translate in x
     * @param y
     *          the units to translate in y
     * @return this
     */
    public Matrix3x2f translation(float x, float y) {
        m00 = 1.0f;
        m01 = 0.0f;
        m10 = 0.0f;
        m11 = 1.0f;
        m20 = x;
        m21 = y;
        return this;
    }

    /**
     * Set this matrix to be a simple translation matrix in a two-dimensional coordinate system.
     * <p>
     * The resulting matrix can be multiplied against another transformation
     * matrix to obtain an additional translation.
     * <p>
     * In order to apply a translation via to an already existing transformation
     * matrix, use {@link #translate(Vector2f) translate()} instead.
     * 
     * @see #translate(Vector2f)
     * 
     * @param offset
     *          the translation
     * @return this
     */
    public Matrix3x2f translation(Vector2f offset) {
        m00 = 1.0f;
        m01 = 0.0f;
        m10 = 0.0f;
        m11 = 1.0f;
        m20 = offset.x;
        m21 = offset.y;
        return this;
    }

    /**
     * Set only the translation components of this matrix <tt>(m20, m21)</tt> to the given values <tt>(x, y)</tt>.
     * <p>
     * To build a translation matrix instead, use {@link #translation(float, float)}.
     * To apply a translation to another matrix, use {@link #translate(float, float)}.
     * 
     * @see #translation(float, float)
     * @see #translate(float, float)
     * 
     * @param x
     *          the offset to translate in x
     * @param y
     *          the offset to translate in y
     * @return this
     */
    public Matrix3x2f setTranslation(float x, float y) {
        m20 = x;
        m21 = y;
        return this;
    }

    /**
     * Set only the translation components of this matrix <tt>(m20, m21)</tt> to the given values <tt>(offset.x, offset.y)</tt>.
     * <p>
     * To build a translation matrix instead, use {@link #translation(Vector2f)}.
     * To apply a translation to another matrix, use {@link #translate(Vector2f)}.
     * 
     * @see #translation(Vector2f)
     * @see #translate(Vector2f)
     * 
     * @param offset
     *          the new translation to set
     * @return this
     */
    public Matrix3x2f setTranslation(Vector2f offset) {
        m20 = offset.x;
        m21 = offset.y;
        return this;
    }

    /**
     * Apply a translation to this matrix by translating by the given number of units in x and y and store the result
     * in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>T</code> the translation
     * matrix, then the new matrix will be <code>M * T</code>. So when
     * transforming a vector <code>v</code> with the new matrix by using
     * <code>M * T * v</code>, the translation will be applied first!
     * <p>
     * In order to set the matrix to a translation transformation without post-multiplying
     * it, use {@link #translation(float, float)}.
     * 
     * @see #translation(float, float)
     * 
     * @param x
     *          the offset to translate in x
     * @param y
     *          the offset to translate in y
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Matrix3x2f translate(float x, float y, Matrix3x2f dest) {
        float rm20 = x;
        float rm21 = y;
        dest.m20 = m00 * rm20 + m10 * rm21 + m20;
        dest.m21 = m01 * rm20 + m11 * rm21 + m21;
        dest.m00 = m00;
        dest.m01 = m01;
        dest.m10 = m10;
        dest.m11 = m11;
        return dest;
    }

    /**
     * Apply a translation to this matrix by translating by the given number of units in x and y.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>T</code> the translation
     * matrix, then the new matrix will be <code>M * T</code>. So when
     * transforming a vector <code>v</code> with the new matrix by using
     * <code>M * T * v</code>, the translation will be applied first!
     * <p>
     * In order to set the matrix to a translation transformation without post-multiplying
     * it, use {@link #translation(float, float)}.
     * 
     * @see #translation(float, float)
     * 
     * @param x
     *          the offset to translate in x
     * @param y
     *          the offset to translate in y
     * @return this
     */
    public Matrix3x2f translate(float x, float y) {
        return translate(x, y, this);
    }

    /**
     * Apply a translation to this matrix by translating by the given number of units in x and y, and
     * store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>T</code> the translation
     * matrix, then the new matrix will be <code>M * T</code>. So when
     * transforming a vector <code>v</code> with the new matrix by using
     * <code>M * T * v</code>, the translation will be applied first!
     * <p>
     * In order to set the matrix to a translation transformation without post-multiplying
     * it, use {@link #translation(float, float)}.
     * 
     * @see #translation(Vector2f)
     * 
     * @param offset
     *          the offset to translate
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Matrix3x2f translate(Vector2f offset, Matrix3x2f dest) {
        return translate(offset.x, offset.y, dest);
    }

    /**
     * Apply a translation to this matrix by translating by the given number of units in x and y.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>T</code> the translation
     * matrix, then the new matrix will be <code>M * T</code>. So when
     * transforming a vector <code>v</code> with the new matrix by using
     * <code>M * T * v</code>, the translation will be applied first!
     * <p>
     * In order to set the matrix to a translation transformation without post-multiplying
     * it, use {@link #translation(float, float)}.
     * 
     * @see #translation(Vector2f)
     * 
     * @param offset
     *          the offset to translate
     * @return this
     */
    public Matrix3x2f translate(Vector2f offset) {
        return translate(offset.x, offset.y, this);
    }

    /**
     * Apply a "view" transformation to this matrix that maps the given <tt>(left, bottom)</tt> and
     * <tt>(right, top)</tt> corners to <tt>(-1, -1)</tt> and <tt>(1, 1)</tt> respectively and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>O</code> the orthographic projection matrix,
     * then the new matrix will be <code>M * O</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * O * v</code>, the
     * orthographic projection transformation will be applied first!
     * 
     * @see #setView(float, float, float, float)
     * 
     * @param left
     *            the distance from the center to the left view edge
     * @param right
     *            the distance from the center to the right view edge
     * @param bottom
     *            the distance from the center to the bottom view edge
     * @param top
     *            the distance from the center to the top view edge
     * @param dest
     *            will hold the result
     * @return dest
     */
    public Matrix3x2f view(float left, float right, float bottom, float top, Matrix3x2f dest) {
        float rm00 = 2.0f / (right - left);
        float rm11 = 2.0f / (top - bottom);
        float rm20 = (left + right) / (left - right);
        float rm21 = (bottom + top) / (bottom - top);
        dest.m20 = m00 * rm20 + m10 * rm21 + m20;
        dest.m21 = m01 * rm20 + m11 * rm21 + m21;
        dest.m00 = m00 * rm00;
        dest.m01 = m01 * rm00;
        dest.m10 = m10 * rm11;
        dest.m11 = m11 * rm11;
        return dest;
    }

    /**
     * Apply a "view" transformation to this matrix that maps the given <tt>(left, bottom)</tt> and
     * <tt>(right, top)</tt> corners to <tt>(-1, -1)</tt> and <tt>(1, 1)</tt> respectively.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>O</code> the orthographic projection matrix,
     * then the new matrix will be <code>M * O</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * O * v</code>, the
     * orthographic projection transformation will be applied first!
     * 
     * @see #setView(float, float, float, float)
     * 
     * @param left
     *            the distance from the center to the left view edge
     * @param right
     *            the distance from the center to the right view edge
     * @param bottom
     *            the distance from the center to the bottom view edge
     * @param top
     *            the distance from the center to the top view edge
     * @return this
     */
    public Matrix3x2f view(float left, float right, float bottom, float top) {
        return view(left, right, bottom, top, this);
    }

    /**
     * Set this matrix to define a "view" transformation that maps the given <tt>(left, bottom)</tt> and
     * <tt>(right, top)</tt> corners to <tt>(-1, -1)</tt> and <tt>(1, 1)</tt> respectively.
     * 
     * @see #view(float, float, float, float)
     * 
     * @param left
     *            the distance from the center to the left view edge
     * @param right
     *            the distance from the center to the right view edge
     * @param bottom
     *            the distance from the center to the bottom view edge
     * @param top
     *            the distance from the center to the top view edge
     * @return this
     */
    public Matrix3x2f setView(float left, float right, float bottom, float top) {
        m00 = 2.0f / (right - left);
        m01 = 0.0f;
        m10 = 0.0f;
        m11 = 2.0f / (top - bottom);
        m20 = (left + right) / (left - right);
        m21 = (bottom + top) / (bottom - top);
        return this;
    }

    /**
     * Obtain the extents of the view transformation of <code>this</code> matrix and store it in <code>min</code> and <code>max</code>.
     * This can be used to determine which region of the screen (i.e. the NDC space) is covered by the view.
     *
     * @param min
     * 			will hold the minimum corner coordinates
     * @param max
     * 			will hold the maximum corner coordinates
     * @return this
     */
    public Matrix3x2f viewArea(Vector2f min, Vector2f max) {
        float s = 1.0f / (m00 * m11 - m01 * m10);
        float rm00 =  m11 * s;
        float rm01 = -m01 * s;
        float rm10 = -m10 * s;
        float rm11 =  m00 * s;
        float rm20 = (m10 * m21 - m20 * m11) * s;
        float rm21 = (m20 * m01 - m00 * m21) * s;
        float nxnyX = -rm00 - rm10;
        float nxnyY = -rm01 - rm11;
        float pxnyX =  rm00 - rm10;
        float pxnyY =  rm01 - rm11;
        float nxpyX = -rm00 + rm10;
        float nxpyY = -rm01 + rm11;
        float pxpyX =  rm00 + rm10;
        float pxpyY =  rm01 + rm11;
        float minX = nxnyX;
        minX = minX < nxpyX ? minX : nxpyX;
        minX = minX < pxnyX ? minX : pxnyX;
        minX = minX < pxpyX ? minX : pxpyX;
        float minY = nxnyY;
        minY = minY < nxpyY ? minY : nxpyY;
        minY = minY < pxnyY ? minY : pxnyY;
        minY = minY < pxpyY ? minY : pxpyY;
        float maxX = nxnyX;
        maxX = maxX > nxpyX ? maxX : nxpyX;
        maxX = maxX > pxnyX ? maxX : pxnyX;
        maxX = maxX > pxpyX ? maxX : pxpyX;
        float maxY = nxnyY;
        maxY = maxY > nxpyY ? maxY : nxpyY;
        maxY = maxY > pxnyY ? maxY : pxnyY;
        maxY = maxY > pxpyY ? maxY : pxpyY;
        min.x = minX + rm20;
        min.y = minY + rm21;
        max.x = maxX + rm20;
        max.y = maxY + rm21;
        return this;
    }

    /**
     * Get the row at the given <code>row</code> index, starting with <code>0</code>.
     * 
     * @param row
     *          the row index in <tt>[0..1]</tt>
     * @param dest
     *          will hold the row components
     * @return the passed in destination
     * @throws IndexOutOfBoundsException if <code>row</code> is not in <tt>[0..1]</tt>
     */
    public Vector3f getRow(int row, Vector3f dest) throws IndexOutOfBoundsException {
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
    public Vector2f getColumn(int column, Vector2f dest) throws IndexOutOfBoundsException {
        switch (column) {
        case 0:
            dest.x = m00;
            dest.y = m01;
            break;
        case 1:
            dest.x = m10;
            dest.y = m11;
            break;
        case 2:
            dest.x = m20;
            dest.y = m21;
            break;
        default:
            throw new IndexOutOfBoundsException();
        }
        return dest;
    }

    /**
     * Set <code>this</code> matrix to its own normal matrix.
     * 
     * @return this
     */
    public Matrix3x2f normal() {
        return normal(this);
    }

    /**
     * Compute a normal matrix from <code>this</code> matrix and store it into <code>dest</code>.
     * 
     * @param dest
     *             will hold the result
     * @return dest
     */
    public Matrix3x2f normal(Matrix3x2f dest) {
        float det = determinant();
        float s = 1.0f / det;
        /* Invert and transpose in one go */
        dest.set( m11 * s, -m10 * s, -m01 * s, m00 * s, 0, 0);
        return dest;
    }

    /**
     * Get the scaling factors of <code>this</code> matrix for the three base axes.
     * 
     * @param dest
     *          will hold the scaling factors for <tt>x</tt> and <tt>y</tt>
     * @return dest
     */
    public Vector2f getScale(Vector2f dest) {
        dest.x = (float) Math.sqrt(m00 * m00 + m01 * m01);
        dest.y = (float) Math.sqrt(m10 * m10 + m11 * m11);
        return dest;
    }

    /**
     * Obtain the direction of <tt>+X</tt> before the transformation represented by <code>this</code> matrix is applied.
     * <p>
     * This method is equivalent to the following code:
     * <pre>
     * Matrix3x2f inv = new Matrix3x2f(this).invert();
     * inv.transform(dir.set(1, 0)).normalize();
     * </pre>
     * <p>
     * Reference: <a href="http://www.euclideanspace.com/maths/algebra/matrix/functions/inverse/threeD/">http://www.euclideanspace.com</a>
     * 
     * @param dir
     *          will hold the direction of <tt>+X</tt>
     * @return dir
     */
    public Vector2f positiveX(Vector2f dir) {
        dir.x =  m11;
        dir.y = -m01;
        dir.normalize();
        return dir;
    }

    /**
     * Obtain the direction of <tt>+Y</tt> before the transformation represented by <code>this</code> matrix is applied.
     * <p>
     * This method is equivalent to the following code:
     * <pre>
     * Matrix3x2f inv = new Matrix3x2f(this).invert();
     * inv.transform(dir.set(0, 1)).normalize();
     * </pre>
     * <p>
     * Reference: <a href="http://www.euclideanspace.com/maths/algebra/matrix/functions/inverse/threeD/">http://www.euclideanspace.com</a>
     * 
     * @param dir
     *          will hold the direction of <tt>+Y</tt>
     * @return dir
     */
    public Vector2f positiveY(Vector2f dir) {
        dir.x = -m10;
        dir.y =  m00;
        dir.normalize();
        return dir;
    }

    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Float.floatToIntBits(m00);
        result = prime * result + Float.floatToIntBits(m01);
        result = prime * result + Float.floatToIntBits(m10);
        result = prime * result + Float.floatToIntBits(m11);
        result = prime * result + Float.floatToIntBits(m20);
        result = prime * result + Float.floatToIntBits(m21);
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Matrix3x2f other = (Matrix3x2f) obj;
        if (Float.floatToIntBits(m00) != Float.floatToIntBits(other.m00))
            return false;
        if (Float.floatToIntBits(m01) != Float.floatToIntBits(other.m01))
            return false;
        if (Float.floatToIntBits(m10) != Float.floatToIntBits(other.m10))
            return false;
        if (Float.floatToIntBits(m11) != Float.floatToIntBits(other.m11))
            return false;
        if (Float.floatToIntBits(m20) != Float.floatToIntBits(other.m20))
            return false;
        if (Float.floatToIntBits(m21) != Float.floatToIntBits(other.m21))
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
    public Matrix3x2f swap(Matrix3x2f other) {
        float tmp;
        tmp = m00; m00 = other.m00; other.m00 = tmp;
        tmp = m01; m01 = other.m01; other.m01 = tmp;
        tmp = m10; m10 = other.m10; other.m10 = tmp;
        tmp = m11; m11 = other.m11; other.m11 = tmp;
        tmp = m20; m20 = other.m20; other.m20 = tmp;
        tmp = m21; m21 = other.m21; other.m21 = tmp;
        return this;
    }

}
