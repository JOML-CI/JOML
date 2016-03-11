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
 * Contains the definition of a 3x3 Matrix of floats, and associated functions to transform
 * it. The matrix is column-major to match OpenGL's interpretation, and it looks like this:
 * <p>
 *      m00  m10  m20<br>
 *      m01  m11  m21<br>
 *      m02  m12  m22<br>
 * 
 * @author Kai Burjack
 */
public class Matrix3f implements Externalizable {

    private static final long serialVersionUID = 1L;

    public float m00, m10, m20;
    public float m01, m11, m21;
    public float m02, m12, m22;

    /**
     * Create a new {@link Matrix3f} and set it to {@link #identity() identity}.
     */
    public Matrix3f() {
        m00 = 1.0f;
        m01 = 0.0f;
        m02 = 0.0f;
        m10 = 0.0f;
        m11 = 1.0f;
        m12 = 0.0f;
        m20 = 0.0f;
        m21 = 0.0f;
        m22 = 1.0f;
    }

    /**
     * Create a new {@link Matrix3f} and make it a copy of the given matrix.
     * 
     * @param mat
     *          the {@link Matrix3f} to copy the values from
     */
    public Matrix3f(Matrix3f mat) {
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
     * Create a new 3x3 matrix using the supplied float values. The order of the parameter is column-major, 
     * so the first three parameters specify the three elements of the first column.
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
    public Matrix3f(float m00, float m01, float m02,
                    float m10, float m11, float m12, 
                    float m20, float m21, float m22) {
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
     * Set the elements of this matrix to the ones in <code>m</code>.
     * 
     * @param m
     *          the matrix to copy the elements from
     * @return this
     */
    public Matrix3f set(Matrix3f m) {
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
     * Multiply this matrix by the supplied <code>right</code> matrix.
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
    public Matrix3f mul(Matrix3f right) {
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
     *          the right operand of the matrix multiplication
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Matrix3f mul(Matrix3f right, Matrix3f dest) {
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
     * Set the values within this matrix to the supplied float values. The result looks like this:
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
    public Matrix3f set(float m00, float m01, float m02,
                        float m10, float m11, float m12, 
                        float m20, float m21, float m22) {
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
     * Set the values in this matrix based on the supplied float array. The result looks like this:
     * <p>
     * 0, 3, 6<br>
     * 1, 4, 7<br>
     * 2, 5, 8<br>
     * 
     * This method only uses the first 9 values, all others are ignored.
     * 
     * @param m
     *          the array to read the matrix values from
     * @return this
     */
    public Matrix3f set(float m[]) {
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
    public float determinant() {
        return (m00 * m11 - m01 * m10) * m22
             + (m02 * m10 - m00 * m12) * m21
             + (m01 * m12 - m02 * m11) * m20;
    }

    /**
     * Invert this matrix.
     *
     * @return this
     */
    public Matrix3f invert() {
        return invert(this);
    }

    /**
     * Invert the <code>this</code> matrix and store the result in <code>dest</code>.
     * 
     * @param dest
     *             will hold the result
     * @return dest
     */
    public Matrix3f invert(Matrix3f dest) {
        float s = determinant();
        // client must make sure that matrix is invertible
        s = 1.0f / s;
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
    public Matrix3f transpose() {
        return transpose(this);
    }

    /**
     * Transpose <code>this</code> matrix and store the result in <code>dest</code>.
     * 
     * @param dest
     *             will hold the result
     * @return dest
     */
    public Matrix3f transpose(Matrix3f dest) {
        dest.set(m00, m10, m20,
                 m01, m11, m21,
                 m02, m12, m22);
        return dest;
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
    public Matrix3f translation(float x, float y) {
        m00 = 1.0f;
        m01 = 0.0f;
        m02 = 0.0f;
        m10 = 0.0f;
        m11 = 1.0f;
        m12 = 0.0f;
        m20 = x;
        m21 = y;
        m22 = 1.0f;
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
    public Matrix3f translation(Vector2f offset) {
        m00 = 1.0f;
        m01 = 0.0f;
        m02 = 0.0f;
        m10 = 0.0f;
        m11 = 1.0f;
        m12 = 0.0f;
        m20 = offset.x;
        m21 = offset.y;
        m22 = 1.0f;
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
    public Matrix3f setTranslation(float x, float y) {
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
    public Matrix3f setTranslation(Vector2f offset) {
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
    public Matrix3f translate(float x, float y, Matrix3f dest) {
        float rm20 = x;
        float rm21 = y;
        dest.m20 = m00 * rm20 + m10 * rm21 + m20;
        dest.m21 = m01 * rm20 + m11 * rm21 + m21;
        dest.m22 = m02 * rm20 + m12 * rm21 + m22;
        dest.m00 = m00;
        dest.m01 = m01;
        dest.m02 = m02;
        dest.m10 = m10;
        dest.m11 = m11;
        dest.m12 = m12;
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
    public Matrix3f translate(float x, float y) {
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
    public Matrix3f translate(Vector2f offset, Matrix3f dest) {
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
    public Matrix3f translate(Vector2f offset) {
        return translate(offset.x, offset.y, this);
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
     * This is the reverse method of {@link #set(Matrix3f)} and allows to obtain
     * intermediate calculation results when chaining multiple transformations.
     * 
     * @see #set(Matrix3f)
     * 
     * @param dest
     *          the destination matrix
     * @return dest
     */
    public Matrix3f get(Matrix3f dest) {
        return dest.set(this);
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
        buffer.putFloat(index,    m00);
        buffer.putFloat(index+4,  m01);
        buffer.putFloat(index+8,  m02);
        buffer.putFloat(index+12, m10);
        buffer.putFloat(index+16, m11);
        buffer.putFloat(index+20, m12);
        buffer.putFloat(index+24, m20);
        buffer.putFloat(index+28, m21);
        buffer.putFloat(index+32, m22);
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
        buffer.put(index,    m00);
        buffer.put(index+1,  m10);
        buffer.put(index+2,  m20);
        buffer.put(index+3,  m01);
        buffer.put(index+4,  m11);
        buffer.put(index+5,  m21);
        buffer.put(index+6,  m02);
        buffer.put(index+7,  m12);
        buffer.put(index+8, m22);
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
        buffer.putFloat(index,    m00);
        buffer.putFloat(index+4,  m10);
        buffer.putFloat(index+8,  m20);
        buffer.putFloat(index+12, m01);
        buffer.putFloat(index+16, m11);
        buffer.putFloat(index+20, m21);
        buffer.putFloat(index+24, m02);
        buffer.putFloat(index+28, m12);
        buffer.putFloat(index+32, m22);
        return buffer;
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
    public Matrix3f set(FloatBuffer buffer) {
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
    public Matrix3f set(ByteBuffer buffer) {
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
     * Set all values within this matrix to zero.
     * 
     * @return this
     */
    public Matrix3f zero() {
        m00 = 0.0f;
        m01 = 0.0f;
        m02 = 0.0f;
        m10 = 0.0f;
        m11 = 0.0f;
        m12 = 0.0f;
        m20 = 0.0f;
        m21 = 0.0f;
        m22 = 0.0f;
        return this;
    }
    
    /**
     * Set this matrix to the identity.
     * 
     * @return this
     */
    public Matrix3f identity() {
        m00 = 1.0f;
        m01 = 0.0f;
        m02 = 0.0f;
        m10 = 0.0f;
        m11 = 1.0f;
        m12 = 0.0f;
        m20 = 0.0f;
        m21 = 0.0f;
        m22 = 1.0f;
        return this;
    }

    /**
     * Apply scaling to this matrix by scaling the unit axes by the given x and y and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>S</code> the scaling matrix,
     * then the new matrix will be <code>M * S</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * S * v</code>, the scaling will be applied first!
     * 
     * @param x
     *            the factor of the x component
     * @param y
     *            the factor of the y component
     * @param dest
     *            will hold the result
     * @return dest
     */
    public Matrix3f scale(float x, float y, Matrix3f dest) {
        // scale matrix elements:
        // m00 = x, m11 = y, m22 = 1
        // all others = 0
        dest.m00 = m00 * x;
        dest.m01 = m01 * x;
        dest.m02 = m02 * x;
        dest.m10 = m10 * y;
        dest.m11 = m11 * y;
        dest.m12 = m12 * y;
        dest.m20 = m20;
        dest.m21 = m21;
        dest.m22 = m22;
        return dest;
    }

    /**
     * Apply scaling to this matrix by scaling the base axes by the given x and y factors.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>S</code> the scaling matrix,
     * then the new matrix will be <code>M * S</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * S * v</code>, the scaling will be applied first!
     * 
     * @param x
     *            the factor of the x component
     * @param y
     *            the factor of the y component
     * @return this
     */
    public Matrix3f scale(float x, float y) {
        return scale(x, y, this);
    }

    /**
     * Apply scaling to this matrix by uniformly scaling the two base axes by the given <code>xy</code> factor
     * and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>S</code> the scaling matrix,
     * then the new matrix will be <code>M * S</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * S * v</code>, the scaling will be applied first!
     * 
     * @see #scale(float, float, Matrix3f)
     * 
     * @param xy
     *            the factor for the two components
     * @param dest
     *            will hold the result
     * @return dest
     */
    public Matrix3f scale(float xy, Matrix3f dest) {
        return scale(xy, xy, dest);
    }

    /**
     * Apply scaling to this matrix by uniformly scaling the two base axes by the given <code>xyz</code> factor.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>S</code> the scaling matrix,
     * then the new matrix will be <code>M * S</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * S * v</code>, the scaling will be applied first!
     * 
     * @see #scale(float, float)
     * 
     * @param xy
     *            the factor for the two components
     * @return this
     */
    public Matrix3f scale(float xy) {
        return scale(xy, xy);
    }

    /**
     * Set this matrix to be a simple scale matrix, which scales the two base axes uniformly by the given factor.
     * <p>
     * The resulting matrix can be multiplied against another transformation
     * matrix to obtain an additional scaling.
     * <p>
     * In order to post-multiply a scaling transformation directly to a matrix, use {@link #scale(float) scale()} instead.
     * 
     * @see #scale(float)
     * 
     * @param factor
     *             the scale factor in x and y
     * @return this
     */
    public Matrix3f scaling(float factor) {
        m00 = factor;
        m01 = 0.0f;
        m02 = 0.0f;
        m10 = 0.0f;
        m11 = factor;
        m12 = 0.0f;
        m20 = 0.0f;
        m21 = 0.0f;
        m22 = 1.0f;
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
    public Matrix3f scaling(float x, float y) {
        m00 = x;
        m01 = 0.0f;
        m02 = 0.0f;
        m10 = 0.0f;
        m11 = y;
        m12 = 0.0f;
        m20 = 0.0f;
        m21 = 0.0f;
        m22 = 1.0f;
        return this;
    }

    /**
     * Set this matrix to a rotation matrix which rotates the given radians.
     * <p>
     * The resulting matrix can be multiplied against another transformation
     * matrix to obtain an additional rotation.
     * <p>
     * In order to apply the rotation transformation to an existing transformation,
     * use {@link #rotate(float) rotate()} instead.
     * 
     * @see #rotate(float)
     * 
     * @param angle
     *          the angle in radians
     * @return this
     */
    public Matrix3f rotation(float angle) {
        float cos = (float) Math.cos(angle);
        float sin = (float) Math.sin(angle);
        m00 = cos;
        m10 = -sin;
        m20 = 0.0f;
        m01 = sin;
        m11 = cos;
        m21 = 0.0f;
        m02 = 0.0f;
        m12 = 0.0f;
        m22 = 1.0f;
        return this;
    }

    /**
     * Transform the given vector by this matrix.
     * 
     * @param v
     *          the vector to transform
     * @return v
     */
    public Vector2f transform(Vector2f v) {
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
    public Vector2f transform(Vector2f v, Vector2f dest) {
        return v.mul(this, dest);
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeFloat(m00);
        out.writeFloat(m01);
        out.writeFloat(m02);
        out.writeFloat(m10);
        out.writeFloat(m11);
        out.writeFloat(m12);
        out.writeFloat(m20);
        out.writeFloat(m21);
        out.writeFloat(m22);
    }

    public void readExternal(ObjectInput in) throws IOException,
            ClassNotFoundException {
        m00 = in.readFloat();
        m01 = in.readFloat();
        m02 = in.readFloat();
        m10 = in.readFloat();
        m11 = in.readFloat();
        m12 = in.readFloat();
        m20 = in.readFloat();
        m21 = in.readFloat();
        m22 = in.readFloat();
    }

    /**
     * Apply a rotation transformation to this matrix by rotating the given amount of radians.
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
    public Matrix3f rotate(float ang) {
        return rotate(ang, this);
    }

    /**
     * Apply a rotation transformation to this matrix by rotating the given amount of radians and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the rotation matrix,
     * then the new matrix will be <code>M * R</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * R * v</code>, the rotation will be applied first!
     * 
     * @param ang
     *            the angle in radians
     * @param dest
     *            will hold the result
     * @return dest
     */
    public Matrix3f rotate(float ang, Matrix3f dest) {
        float cos = (float) Math.cos(ang);
        float sin = (float) Math.sin(ang);
        float rm00 = cos;
        float rm01 = sin;
        float rm10 = -sin;
        float rm11 = cos;

        // add temporaries for dependent values
        float nm00 = m00 * rm00 + m10 * rm01;
        float nm01 = m01 * rm00 + m11 * rm01;
        float nm02 = m02 * rm00 + m12 * rm01;
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
     * Apply a rotation transformation to this matrix by rotating the given amount of radians about
     * the specified rotation center <tt>(x, y)</tt>.
     * <p>
     * This method is equivalent to calling: <tt>translate(x, y).rotate(ang).translate(-x, -y)</tt>
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the rotation matrix,
     * then the new matrix will be <code>M * R</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * R * v</code>, the rotation will be applied first!
     * 
     * @see #translate(float, float)
     * @see #rotate(float)
     * 
     * @param ang
     *            the angle in radians
     * @param x
     *            the x component of the rotation center
     * @param y
     *            the y component of the rotation center
     * @return dest
     */
    public Matrix3f rotateAbout(float ang, float x, float y) {
    	return rotateAbout(ang, x, y, this);
    }

    /**
     * Apply a rotation transformation to this matrix by rotating the given amount of radians about
     * the specified rotation center <tt>(x, y)</tt> and store the result in <code>dest</code>.
     * <p>
     * This method is equivalent to calling: <tt>translate(x, y, dest).rotate(ang).translate(-x, -y)</tt>
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the rotation matrix,
     * then the new matrix will be <code>M * R</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * R * v</code>, the rotation will be applied first!
     * 
     * @see #translate(float, float, Matrix3f)
     * @see #rotate(float, Matrix3f)
     * 
     * @param ang
     *            the angle in radians
     * @param x
     *            the x component of the rotation center
     * @param y
     *            the y component of the rotation center
     * @param dest
     *            will hold the result
     * @return dest
     */
    public Matrix3f rotateAbout(float ang, float x, float y, Matrix3f dest) {
        float tm20 = m00 * x + m10 * y + m20;
        float tm21 = m01 * x + m11 * y + m21;
        float tm22 = m02 * x + m12 * y + m22;
        float cos = (float) Math.cos(ang);
        float sin = (float) Math.sin(ang);
        // add temporaries for dependent values
        float nm00 = m00 * cos + m10 * sin;
        float nm01 = m01 * cos + m11 * sin;
        float nm02 = m02 * cos + m12 * sin;
        // set non-dependent values directly
        dest.m10 = m00 * -sin + m10 * cos;
        dest.m11 = m01 * -sin + m11 * cos;
        dest.m12 = m02 * -sin + m12 * cos;
        // set other values
        dest.m00 = nm00;
        dest.m01 = nm01;
        dest.m02 = nm02;
        dest.m20 = dest.m00 * -x + dest.m10 * -y + tm20;
        dest.m21 = dest.m01 * -x + dest.m11 * -y + tm21;
        dest.m22 = dest.m02 * -x + dest.m12 * -y + tm22;
        return dest;
    }

    /**
     * Apply a rotation transformation to this matrix that rotates the given normalized <code>fromDir</code> direction vector
     * to point along the normalized <code>toDir</code>, and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the rotation matrix,
     * then the new matrix will be <code>M * R</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * R * v</code>, the rotation will be applied first!
     * 
     * @param fromDir
     *            the normalized direction which should be rotate to point along <code>toDir</code>
     * @param toDir
     *            the normalized destination direction
     * @param dest
     *            will hold the result
     * @return dest
     */
    public Matrix3f rotateTo(Vector2f fromDir, Vector2f toDir, Matrix3f dest) {
        float dot = fromDir.x * toDir.x + fromDir.y * toDir.y;
        float det = fromDir.x * toDir.y - fromDir.y * toDir.x;
        float rm00 = dot;
        float rm01 = det;
        float rm10 = -det;
        float rm11 = dot;
        float nm00 = m00 * rm00 + m10 * rm01;
        float nm01 = m01 * rm00 + m11 * rm01;
        float nm02 = m02 * rm00 + m12 * rm01;
        dest.m10 = m00 * rm10 + m10 * rm11;
        dest.m11 = m01 * rm10 + m11 * rm11;
        dest.m12 = m02 * rm10 + m12 * rm11;
        dest.m00 = nm00;
        dest.m01 = nm01;
        dest.m02 = nm02;
        dest.m20 = m20;
        dest.m21 = m21;
        dest.m22 = m22;
        return dest;
    }

    /**
     * Apply a rotation transformation to this matrix that rotates the given normalized <code>fromDir</code> direction vector
     * to point along the normalized <code>toDir</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the rotation matrix,
     * then the new matrix will be <code>M * R</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * R * v</code>, the rotation will be applied first!
     * 
     * @param fromDir
     *            the normalized direction which should be rotate to point along <code>toDir</code>
     * @param toDir
     *            the normalized destination direction
     * @return this
     */
    public Matrix3f rotateTo(Vector2f fromDir, Vector2f toDir) {
        return rotateTo(fromDir, toDir, this);
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
    public Matrix3f view(float left, float right, float bottom, float top, Matrix3f dest) {
        // calculate right matrix elements
        float rm00 = 2.0f / (right - left);
        float rm11 = 2.0f / (top - bottom);
        float rm20 = (left + right) / (left - right);
        float rm21 = (bottom + top) / (bottom - top);
        float rm22 = -1.0f;

        // perform optimized multiplication
        // compute the last column first, because other rows do not depend on it
        dest.m20 = m00 * rm20 + m10 * rm21 + m20 * rm22 + m20;
        dest.m21 = m01 * rm20 + m11 * rm21 + m21 * rm22 + m21;
        dest.m22 = 1.0f;
        dest.m00 = m00 * rm00;
        dest.m01 = m01 * rm00;
        dest.m02 = m02 * rm00;
        dest.m10 = m10 * rm11;
        dest.m11 = m11 * rm11;
        dest.m12 = m12 * rm11;

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
    public Matrix3f view(float left, float right, float bottom, float top) {
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
    public Matrix3f setView(float left, float right, float bottom, float top) {
        m00 = 2.0f / (right - left);
        m01 = 0.0f;
        m02 = 0.0f;
        m10 = 0.0f;
        m11 = 2.0f / (top - bottom);
        m12 = 0.0f;
        m20 = (left + right) / (left - right);
        m21 = (bottom + top) / (bottom - top);
        m22 = -1.0f;
        return this;
    }

}
