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
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Contains the definition of a 3x3 Matrix of doubles, and associated functions to transform
 * it. The matrix is column-major to match OpenGL's interpretation, and it looks like this:
 * <p>
 *      m00  m10  m20</br>
 *      m01  m11  m21</br>
 *      m02  m12  m22</br>
 * 
 * @author Richard Greenlees
 * @author Kai Burjack
 */
public class Matrix3d implements Serializable, Externalizable {

    private static final long serialVersionUID = 1L;

    public double m00;
    public double m01;
    public double m02;
    public double m10;
    public double m11;
    public double m12;
    public double m20;
    public double m21;
    public double m22;

    /**
     * Create a new {@link Matrix3d} and initialize it to {@link #identity() identity}.
     */
    public Matrix3d() {
        super();
        identity();
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
     * Create a new {@link Matrix3d} and initialize its elements with the given values.
     */
    public Matrix3d(double m00, double m01, double m02, double m10, double m11, double m12, double m20, double m21, double m22) {
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
     * Multiply this matrix by the supplied matrix.
     * This matrix will be the left one.
     * 
     * @param right
     *          the right operand
     * @return this
     */
    public Matrix3d mul(Matrix3d right) {
        mul(this, right, this);
        return this;
    }

    /**
     * Multiply this matrix by the supplied matrix.
     * This matrix will be the left one.
     * 
     * @param right
     *          the right operand
     * @return this
     */
    public Matrix3d mul(Matrix3f right) {
        mul(this, right, this);
        return this;
    }

    /**
     * Multiply the left matrix by the right and store the result in dest.
     */
    public static void mul(Matrix3d left, Matrix3d right, Matrix3d dest) {
        if (left != dest && right != dest) {
            dest.m00 = left.m00 * right.m00 + left.m10 * right.m01 + left.m20 * right.m02;
            dest.m01 = left.m01 * right.m00 + left.m11 * right.m01 + left.m21 * right.m02;
            dest.m02 = left.m02 * right.m00 + left.m12 * right.m01 + left.m22 * right.m02;
            dest.m10 = left.m00 * right.m10 + left.m10 * right.m11 + left.m20 * right.m12;
            dest.m11 = left.m01 * right.m10 + left.m11 * right.m11 + left.m21 * right.m12;
            dest.m12 = left.m02 * right.m10 + left.m12 * right.m11 + left.m22 * right.m12;
            dest.m20 = left.m00 * right.m20 + left.m10 * right.m21 + left.m20 * right.m22;
            dest.m21 = left.m01 * right.m20 + left.m11 * right.m21 + left.m21 * right.m22;
            dest.m22 = left.m02 * right.m20 + left.m12 * right.m21 + left.m22 * right.m22;
        } else {
            dest.set( left.m00 * right.m00 + left.m10 * right.m01 + left.m20 * right.m02,
                      left.m01 * right.m00 + left.m11 * right.m01 + left.m21 * right.m02,
                      left.m02 * right.m00 + left.m12 * right.m01 + left.m22 * right.m02,
                      left.m00 * right.m10 + left.m10 * right.m11 + left.m20 * right.m12,
                      left.m01 * right.m10 + left.m11 * right.m11 + left.m21 * right.m12,
                      left.m02 * right.m10 + left.m12 * right.m11 + left.m22 * right.m12,
                      left.m00 * right.m20 + left.m10 * right.m21 + left.m20 * right.m22,
                      left.m01 * right.m20 + left.m11 * right.m21 + left.m21 * right.m22,
                      left.m02 * right.m20 + left.m12 * right.m21 + left.m22 * right.m22 );
        }
    }

    /**
     * Multiply the left matrix by the right and store the result in dest.
     */
    public static void mul(Matrix3f left, Matrix3d right, Matrix3d dest) {
        if (right != dest) {
            dest.m00 = left.m00 * right.m00 + left.m10 * right.m01 + left.m20 * right.m02;
            dest.m01 = left.m01 * right.m00 + left.m11 * right.m01 + left.m21 * right.m02;
            dest.m02 = left.m02 * right.m00 + left.m12 * right.m01 + left.m22 * right.m02;
            dest.m10 = left.m00 * right.m10 + left.m10 * right.m11 + left.m20 * right.m12;
            dest.m11 = left.m01 * right.m10 + left.m11 * right.m11 + left.m21 * right.m12;
            dest.m12 = left.m02 * right.m10 + left.m12 * right.m11 + left.m22 * right.m12;
            dest.m20 = left.m00 * right.m20 + left.m10 * right.m21 + left.m20 * right.m22;
            dest.m21 = left.m01 * right.m20 + left.m11 * right.m21 + left.m21 * right.m22;
            dest.m22 = left.m02 * right.m20 + left.m12 * right.m21 + left.m22 * right.m22;
        } else {
            dest.set( left.m00 * right.m00 + left.m10 * right.m01 + left.m20 * right.m02,
                      left.m01 * right.m00 + left.m11 * right.m01 + left.m21 * right.m02,
                      left.m02 * right.m00 + left.m12 * right.m01 + left.m22 * right.m02,
                      left.m00 * right.m10 + left.m10 * right.m11 + left.m20 * right.m12,
                      left.m01 * right.m10 + left.m11 * right.m11 + left.m21 * right.m12,
                      left.m02 * right.m10 + left.m12 * right.m11 + left.m22 * right.m12,
                      left.m00 * right.m20 + left.m10 * right.m21 + left.m20 * right.m22,
                      left.m01 * right.m20 + left.m11 * right.m21 + left.m21 * right.m22,
                      left.m02 * right.m20 + left.m12 * right.m21 + left.m22 * right.m22 );
        }
    }

    /**
     * Multiply the left matrix by the right and store the result in dest.
     */
    public static void mul(Matrix3d left, Matrix3f right, Matrix3d dest) {
        if (left != dest) {
            dest.m00 = left.m00 * right.m00 + left.m10 * right.m01 + left.m20 * right.m02;
            dest.m01 = left.m01 * right.m00 + left.m11 * right.m01 + left.m21 * right.m02;
            dest.m02 = left.m02 * right.m00 + left.m12 * right.m01 + left.m22 * right.m02;
            dest.m10 = left.m00 * right.m10 + left.m10 * right.m11 + left.m20 * right.m12;
            dest.m11 = left.m01 * right.m10 + left.m11 * right.m11 + left.m21 * right.m12;
            dest.m12 = left.m02 * right.m10 + left.m12 * right.m11 + left.m22 * right.m12;
            dest.m20 = left.m00 * right.m20 + left.m10 * right.m21 + left.m20 * right.m22;
            dest.m21 = left.m01 * right.m20 + left.m11 * right.m21 + left.m21 * right.m22;
            dest.m22 = left.m02 * right.m20 + left.m12 * right.m21 + left.m22 * right.m22;
        } else {
            dest.set( left.m00 * right.m00 + left.m10 * right.m01 + left.m20 * right.m02,
                      left.m01 * right.m00 + left.m11 * right.m01 + left.m21 * right.m02,
                      left.m02 * right.m00 + left.m12 * right.m01 + left.m22 * right.m02,
                      left.m00 * right.m10 + left.m10 * right.m11 + left.m20 * right.m12,
                      left.m01 * right.m10 + left.m11 * right.m11 + left.m21 * right.m12,
                      left.m02 * right.m10 + left.m12 * right.m11 + left.m22 * right.m12,
                      left.m00 * right.m20 + left.m10 * right.m21 + left.m20 * right.m22,
                      left.m01 * right.m20 + left.m11 * right.m21 + left.m21 * right.m22,
                      left.m02 * right.m20 + left.m12 * right.m21 + left.m22 * right.m22 );
        }
    }

    /**
     * Set the values within this matrix to the supplied double values. The result looks like this:
     * <p>
     * m00, m10, m20</br>
     * m01, m11, m21</br>
     * m02, m12, m22</br>
     * 
     * @return this
     */
    public Matrix3d set(double m00, double m01, double m02, double m10, double m11,
                    double m12, double m20, double m21, double m22) {
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
     * 0, 3, 6</br>
     * 1, 4, 7</br>
     * 2, 5, 8</br>
     * <p>
     * Only uses the first 9 values, all others are ignored.
     * 
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
     * 0, 3, 6</br>
     * 1, 4, 7</br>
     * 2, 5, 8</br>
     * <p>
     * Only uses the first 9 values, all others are ignored
     * 
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
     */
    public double determinant() {
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
    public Matrix3d invert() {
        invert(this, this);
        return this;
    }

    /**
     * Invert the source matrix and store the result in dest.
     */
    public static void invert(Matrix3d source, Matrix3d dest) {
        double s = source.determinant();
        if (s == 0.0f) {
            return;
        }
        s = 1.0f / s;
        if (source != dest) {
            dest.m00 = ((source.m11 * source.m22) - (source.m21 * source.m12)) * s;
            dest.m01 = -((source.m01 * source.m22) - (source.m21 * source.m02)) * s;
            dest.m02 = ((source.m01 * source.m12) - (source.m11 * source.m02)) * s;
            dest.m10 = -((source.m10 * source.m22) - (source.m20 * source.m12)) * s;
            dest.m11 = ((source.m00 * source.m22) - (source.m20 * source.m02)) * s;
            dest.m12 = -((source.m00 * source.m12) - (source.m10 * source.m02)) * s;
            dest.m20 = ((source.m10 * source.m21) - (source.m20 * source.m11)) * s;
            dest.m21 = -((source.m00 * source.m21) - (source.m20 * source.m01)) * s;
            dest.m22 = ((source.m00 * source.m11) - (source.m10 * source.m01)) * s;
        } else {
            dest.set(  ((source.m11 * source.m22) - (source.m21 * source.m12)) * s,
                      -((source.m01 * source.m22) - (source.m21 * source.m02)) * s,
                       ((source.m01 * source.m12) - (source.m11 * source.m02)) * s,
                      -((source.m10 * source.m22) - (source.m20 * source.m12)) * s,
                       ((source.m00 * source.m22) - (source.m20 * source.m02)) * s,
                      -((source.m00 * source.m12) - (source.m10 * source.m02)) * s,
                       ((source.m10 * source.m21) - (source.m20 * source.m11)) * s,
                      -((source.m00 * source.m21) - (source.m20 * source.m01)) * s,
                       ((source.m00 * source.m11) - (source.m10 * source.m01)) * s  );
        }
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
     * 			will hold the result
     * @return this
     */
    public Matrix3d transpose(Matrix3d dest) {
        if (this != dest) {
            dest.m00 = m00;
            dest.m01 = m10;
            dest.m02 = m20;
            dest.m10 = m01;
            dest.m11 = m11;
            dest.m12 = m21;
            dest.m20 = m02;
            dest.m21 = m12;
            dest.m22 = m22;
        } else {
            dest.set(m00, m10, m20,
                     m01, m11, m21,
                     m02, m12, m22);
        }
        return this;
    }

    /**
     * Set this matrix to be a simple translation matrix.
     * <p>
     * The resulting matrix can be {@link #mul(Matrix3d) multiplied} against another transformation
     * matrix to obtain an additional translation.
     * 
     * @return this
     */
    public Matrix3d translation(double x, double y) {
        m00 = 1.0;
        m01 = 0.0;
        m02 = 0.0;
        m10 = 0.0;
        m11 = 1.0;
        m12 = 0.0;
        m20 = x;
        m21 = y;
        m22 = 1.0;
        return this;
    }
 
    /**
     * Set the given matrix to be a simple translation matrix.
     * <p>
     * The resulting matrix can be {@link #mul(Matrix3d) multiplied} against another transformation
     * matrix to obtain an additional translation.
     */
    public static void translation(double x, double y, Matrix3d dest) {
    	dest.translation(x, y);
    }

    /**
     * Set this matrix to be a simple translation matrix.
     * <p>
     * The resulting matrix can be {@link #mul(Matrix3d) multiplied} against another transformation
     * matrix to obtain an additional translation.
     */
    public Matrix3d translation(Vector2d position) {
        return translation(position.x, position.y);
    }

    /**
     * Set the given matrix to be a simple translation matrix.
     * <p>
     * The resulting matrix can be {@link #mul(Matrix3d) multiplied} against another transformation
     * matrix to obtain an additional translation.
     */
    public static void translation(Vector2d position, Matrix3d dest) {
        dest.translation(position.x, position.y);
    }

    /**
     * Set this matrix to be a simple translation matrix.
     * <p>
     * The resulting matrix can be {@link #mul(Matrix3d) multiplied} against another transformation
     * matrix to obtain an additional translation.
     */
    public Matrix3d translation(Vector2f position) {
        return translation(position.x, position.y);
    }

    /**
     * Set the given matrix to be a simple translation matrix.
     * <p>
     * The resulting matrix can be {@link #mul(Matrix3d) multiplied} against another transformation
     * matrix to obtain an additional translation.
     */
    public static void translation(Vector2f position, Matrix3d dest) {
        dest.translation(position.x, position.y);
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
        return formatter.format(m00) + formatter.format(m10) + formatter.format(m20) + "\n"
             + formatter.format(m01) + formatter.format(m11) + formatter.format(m21) + "\n"
             + formatter.format(m02) + formatter.format(m12) + formatter.format(m22) + "\n";
    }

    /**
     * Get the current values of <code>this</code> matrix and store them into
     * <code>dest</code>.
     * <p>
     * This is the reverse method of {@link #set(Matrix3d)} and allows to obtain
     * intermediate calculation results when chaining multiple transformations.
     * 
     * @param dest
     *          the destination matrix
     * @return this
     */
    public Matrix3d get(Matrix3d dest) {
        dest.set(this);
        return this;
    }

    /**
     * Store this matrix into the supplied {@link DoubleBuffer} at the current
     * buffer {@link DoubleBuffer#position() position} using column-major order.
     * <p>
     * This method will not increment the position of the given DoubleBuffer.
     * <p>
     * If you want to specify the offset into the DoubleBuffer} at which
     * the matrix is stored, you can use {@link #get(int, DoubleBuffer)}, taking
     * the absolute position as parameter.
     * 
     * @see #get(int, DoubleBuffer)
     * 
     * @param buffer
     *            will receive the values of this matrix in column-major order at its current position
     * @return this
     */
    public Matrix3d get(DoubleBuffer buffer) {
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
     * @return this
     */
    public Matrix3d get(int index, DoubleBuffer buffer) {
        buffer.put(index, m00);
        buffer.put(index+1, m01);
        buffer.put(index+2, m02);
        buffer.put(index+3, m10);
        buffer.put(index+4, m11);
        buffer.put(index+5, m12);
        buffer.put(index+6, m20);
        buffer.put(index+7, m21);
        buffer.put(index+8, m22);
        return this;
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
     * Set this matrix to be a simple scale matrix.
     * 
     * @param x
     * 			the scale in x
     * @param y
     * 			the scale in y
     * @param z
     * 			the scale in z
     * @return this
     */
    public Matrix3d scale(double x, double y, double z) {
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
    public Matrix3d scale(Vector3d scale) {
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
    public static void scale(Vector3d scale, Matrix3d dest) {
    	dest.identity();
        dest.m00 = scale.x;
        dest.m11 = scale.y;
        dest.m22 = scale.z;
    }
    
    /**
     * Set this matrix to be a simple scale matrix.
     * 
     * @param x
     * 			the scale in x
     * @param y
     * 			the scale in y
     * @param z
     * 			the scale in z
     * @return this
     */
    public Matrix3d scale(double x, double y, double z, Matrix3d dest) {
    	dest.identity();
    	dest.m00 = x;
    	dest.m11 = y;
    	dest.m22 = z;
    	return this;
    }

    /**
     * Set this matrix to a rotation matrix which rotates the given degrees about a given axis.
     * 
     * @param angle
     *          the angle in degrees
     * @param axis
     *          the rotation axis
     * @return this
     */
    public Matrix3d rotation(double angle, Vector3d axis) {
    	rotation(angle, axis, this);
    	return this;
    }

    /**
     * Set the destination matrix to a rotation matrix which rotates the given degrees about a given axis.
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Rotation_matrix_from_axis_and_angle">Wikipedia</a>
     * 
     * @param angle
     *          the angle in degrees
     * @param axis
     *          the rotation axis
     * @param dest
     *          will hold the result
     */
    public static void rotation(double angle, Vector3d axis, Matrix3d dest) {
    	double cos = Math.cos(Math.toRadians(angle));
    	double sin = Math.sin(Math.toRadians(angle));
    	dest.m00 = cos + axis.x * axis.x * (1.0 - cos);
    	dest.m10 = axis.x * axis.y * (1.0 - cos) - axis.z * sin;
    	dest.m20 = axis.x * axis.z * (1.0 - cos) + axis.y * sin;
    	dest.m01 = axis.y * axis.x * (1.0 - cos) + axis.z * sin;
    	dest.m11 = cos + axis.y * axis.y * (1.0 - cos);
    	dest.m21 = axis.y * axis.z * (1.0 - cos) - axis.x * sin;
    	dest.m02 = axis.z * axis.x * (1.0 - cos) - axis.y * sin;
    	dest.m12 = axis.z * axis.y * (1.0 - cos) + axis.x * sin;
    	dest.m22 = cos + axis.z * axis.z * (1.0 - cos);
    }

    /**
     * Transform the given vector by this matrix.
     * 
     * @param v
     * @return this
     */
    public Matrix3d transform(Vector3d v) {
        v.mul(this);
        return this;
    }

    /**
     * Transform the given vector by the given matrix.
     * 
     * @param mat
     * @param v
     */
    public static void transform(Matrix3d mat, Vector3d v) {
        v.mul(mat);
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
     * Apply the rotation transformation of the given {@link Quaterniond} to this matrix.
     * 
     * @param quat
     *          the {@link Quaterniond}
     * @return this
     */
    public Matrix3d mul(Quaterniond quat) {
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
        double nm10 = m00 * rm10 + m10 * rm11 + m20 * rm12;
        double nm11 = m01 * rm10 + m11 * rm11 + m21 * rm12;
        double nm12 = m02 * rm10 + m12 * rm11 + m22 * rm12;
        m20 = m00 * rm20 + m10 * rm21 + m20 * rm22;
        m21 = m01 * rm20 + m11 * rm21 + m21 * rm22;
        m22 = m02 * rm20 + m12 * rm21 + m22 * rm22;
        m00 = nm00;
        m01 = nm01;
        m02 = nm02;
        m10 = nm10;
        m11 = nm11;
        m12 = nm12;

        return this;
    }

    /**
     * Get the row at the given <code>row</code> index, starting with <code>0</code>.
     * 
     * @param row
     *          the row index in <tt>[0..2]</tt>
     * @param dest
     *          will hold the row components
     * @throws IndexOutOfBoundsException if <code>row</code> is not in <tt>[0..2]</tt>
     */
    public void getRow(int row, Vector3d dest) throws IndexOutOfBoundsException {
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
        return;
    }

    /**
     * Get the column at the given <code>column</code> index, starting with <code>0</code>.
     * 
     * @param column
     *          the column index in <tt>[0..2]</tt>
     * @param dest
     *          will hold the column components
     * @throws IndexOutOfBoundsException if <code>column</code> is not in <tt>[0..2]</tt>
     */
    public void getColumn(int column, Vector3d dest) throws IndexOutOfBoundsException {
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
        return;
    }

    /**
     * Compute a normal matrix from <code>this</code> matrix and store it into <code>dest</code>.
     * 
     * @param dest
     * 			will hold the result
     * @return this
     */
    public Matrix3d normal(Matrix3d dest) {
    	// see: http://mathworld.wolfram.com/OrthogonalMatrix.html
    	double det = determinant();
    	double diff = Math.abs(Math.abs(det) - 1.0);
    	if (diff < 1E-8) {
    		/* The fast path, if only 1:1:1 scaling is being used */
    		return this.transpose(dest);
    	}
    	/* The general case */
    	double s = 1.0 / det;
        /* Invert and transpose in one go */
        dest.set(((m11 * m22) - (m21 * m12)) * s,
        		-((m10 * m22) - (m20 * m12)) * s,
	             ((m10 * m21) - (m20 * m11)) * s,
	            -((m01 * m22) - (m21 * m02)) * s,
	             ((m00 * m22) - (m20 * m02)) * s,
	            -((m00 * m21) - (m20 * m01)) * s,
	             ((m01 * m12) - (m11 * m02)) * s,
	            -((m00 * m12) - (m10 * m02)) * s,
	             ((m00 * m11) - (m10 * m01)) * s);
    	return this;
    }

}
