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
import java.util.Arrays;

/**
 * Contains the definition of a 3x3 Matrix of floats, and associated functions to transform
 * it. The matrix is column-major to match OpenGL's interpretation, and it looks like this:
 * <p>
 *      m00  m10  m20<br>
 *      m01  m11  m21<br>
 *      m02  m12  m22<br>
 * 
 * @author Richard Greenlees
 * @author Kai Burjack
 */
public class Matrix3f implements Externalizable {

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
    public final float[] ms = new float[9];

    /**
     * Create a new {@link Matrix3f} and set it to {@link #identity() identity}.
     */
    public Matrix3f() {
        ms[M00] = 1.0f;
        ms[M01] = 0.0f;
        ms[M02] = 0.0f;
        ms[M10] = 0.0f;
        ms[M11] = 1.0f;
        ms[M12] = 0.0f;
        ms[M20] = 0.0f;
        ms[M21] = 0.0f;
        ms[M22] = 1.0f;
    }

    /**
     * Create a new {@link Matrix3f} and make it a copy of the given matrix.
     * 
     * @param mat
     *          the {@link Matrix3f} to copy the values from
     */
    public Matrix3f(Matrix3f mat) {
    	System.arraycopy(mat.ms, 0, ms, 0, 9);
    }

    /**
     * Create a new {@link Matrix3f} and make it a copy of the upper left 3x3 of the given {@link Matrix4f}.
     * 
     * @param mat
     *          the {@link Matrix4f} to copy the values from
     */
    public Matrix3f(Matrix4f mat) {
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
    public Matrix3f(float m00, float m01, float m02,
                    float m10, float m11, float m12, 
                    float m20, float m21, float m22) {
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
     * Set the elements of this matrix to the ones in <code>m</code>.
     * 
     * @param m
     *          the matrix to copy the elements from
     * @return this
     */
    public Matrix3f set(Matrix3f m) {
    	System.arraycopy(m.ms, 0, ms, 0, 9);
        return this;
    }

    /**
     * Set the elements of this matrix to the upper left 3x3 of the given {@link Matrix4f}.
     *
     * @param mat
     *          the {@link Matrix4f} to copy the values from
     * @return this
     */
    public Matrix3f set(Matrix4f mat) {
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
     * Set this matrix to be equivalent to the rotation specified by the given {@link AxisAngle4f}.
     * 
     * @param axisAngle
     *          the {@link AxisAngle4f}
     * @return this
     */
    public Matrix3f set(AxisAngle4f axisAngle) {
        float x = axisAngle.x;
        float y = axisAngle.y;
        float z = axisAngle.z;
        float angle = axisAngle.angle;
        float invLength = (float) (1.0 / Math.sqrt(x*x + y*y + z*z));
        x *= invLength;
        y *= invLength;
        z *= invLength;
        float c = (float) Math.cos(angle);
        float s = (float) Math.sin(angle);
        float omc = 1.0f - c;
        ms[M00] = c + x*x*omc;
        ms[M11] = c + y*y*omc;
        ms[M22] = c + z*z*omc;
        float tmp1 = x*y*omc;
        float tmp2 = z*s;
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
    public Matrix3f set(AxisAngle4d axisAngle) {
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
        double omc = 1.0f - c;
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
        return this;
    }

    /**
     * Set this matrix to be equivalent to the rotation specified by the given {@link Quaternionf}.
     * 
     * @see Quaternionf#get(Matrix3f)
     * 
     * @param q
     *          the {@link Quaternionf}
     * @return this
     */
    public Matrix3f set(Quaternionf q) {
        q.get(this);
        return this;
    }

    /**
     * Set this matrix to a rotation equivalent to the given quaternion.
     * 
     * @see Quaterniond#get(Matrix3f)
     * 
     * @param q
     *          the quaternion
     * @return this
     */
    public Matrix3f set(Quaterniond q) {
        q.get(this);
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
        dest.set(ms[M00] * right.ms[M00] + ms[M10] * right.ms[M01] + ms[M20] * right.ms[M02],
                 ms[M01] * right.ms[M00] + ms[M11] * right.ms[M01] + ms[M21] * right.ms[M02],
                 ms[M02] * right.ms[M00] + ms[M12] * right.ms[M01] + ms[M22] * right.ms[M02],
                 ms[M00] * right.ms[M10] + ms[M10] * right.ms[M11] + ms[M20] * right.ms[M12],
                 ms[M01] * right.ms[M10] + ms[M11] * right.ms[M11] + ms[M21] * right.ms[M12],
                 ms[M02] * right.ms[M10] + ms[M12] * right.ms[M11] + ms[M22] * right.ms[M12],
                 ms[M00] * right.ms[M20] + ms[M10] * right.ms[M21] + ms[M20] * right.ms[M22],
                 ms[M01] * right.ms[M20] + ms[M11] * right.ms[M21] + ms[M21] * right.ms[M22],
                 ms[M02] * right.ms[M20] + ms[M12] * right.ms[M21] + ms[M22] * right.ms[M22]);
        return dest;
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
    public Matrix3f set(float m00, float m01, float m02,
                        float m10, float m11, float m12, 
                        float m20, float m21, float m22) {
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
    	System.arraycopy(m, 0, ms, 0, 9);
        return this;
    }

    /**
     * Return the determinant of this matrix.
     * 
     * @return the determinant
     */
    public float determinant() {
        return (ms[M00] * ms[M11] - ms[M01] * ms[M10]) * ms[M22]
             + (ms[M02] * ms[M10] - ms[M00] * ms[M12]) * ms[M21]
             + (ms[M01] * ms[M12] - ms[M02] * ms[M11]) * ms[M20];
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
     * This is the reverse method of {@link #set(Matrix3f)} and allows to obtain
     * intermediate calculation results when chaining multiple transformations.
     * 
     * @see #set(Matrix3f)
     * 
     * @param dest
     *          the destination matrix
     * @return the passed in destination
     */
    public Matrix3f get(Matrix3f dest) {
        return dest.set(this);
    }

    /**
     * Get the current values of <code>this</code> matrix and store them as
     * the rotational component of <code>dest</code>. All other values of <code>dest</code> will
     * be set to identity.
     * 
     * @see Matrix4f#set(Matrix3f)
     * 
     * @param dest
     *          the destination matrix
     * @return the passed in destination
     */
    public Matrix4f get(Matrix4f dest) {
        return dest.set(this);
    }

    /**
     * Get the current values of <code>this</code> matrix and store the represented rotation
     * into the given {@link AxisAngle4f}.
     * 
     * @see AxisAngle4f#set(Matrix3f)
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
     * @see Quaternionf#setFromUnnormalized(Matrix3f)
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
     * @see Quaternionf#setFromNormalized(Matrix3f)
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
     * @see Quaterniond#setFromUnnormalized(Matrix3f)
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
     * @see Quaterniond#setFromNormalized(Matrix3f)
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
        buffer.putFloat(index,    ms[M00]);
        buffer.putFloat(index+4,  ms[M01]);
        buffer.putFloat(index+8,  ms[M02]);
        buffer.putFloat(index+12, ms[M10]);
        buffer.putFloat(index+16, ms[M11]);
        buffer.putFloat(index+20, ms[M12]);
        buffer.putFloat(index+24, ms[M20]);
        buffer.putFloat(index+28, ms[M21]);
        buffer.putFloat(index+32, ms[M22]);
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
        System.arraycopy(ms, 0, arr, offset, 9);
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
        buffer.put(index,   ms[M00]);
        buffer.put(index+1, ms[M10]);
        buffer.put(index+2, ms[M20]);
        buffer.put(index+3, ms[M01]);
        buffer.put(index+4, ms[M11]);
        buffer.put(index+5, ms[M21]);
        buffer.put(index+6, ms[M02]);
        buffer.put(index+7, ms[M12]);
        buffer.put(index+8, ms[M22]);
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
        buffer.putFloat(index,    ms[M00]);
        buffer.putFloat(index+4,  ms[M10]);
        buffer.putFloat(index+8,  ms[M20]);
        buffer.putFloat(index+12, ms[M01]);
        buffer.putFloat(index+16, ms[M11]);
        buffer.putFloat(index+20, ms[M21]);
        buffer.putFloat(index+24, ms[M02]);
        buffer.putFloat(index+28, ms[M12]);
        buffer.putFloat(index+32, ms[M22]);
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
     * Set all values within this matrix to zero.
     * 
     * @return this
     */
    public Matrix3f zero() {
    	Arrays.fill(ms, 0.0f);
        return this;
    }
    
    /**
     * Set this matrix to the identity.
     * 
     * @return this
     */
    public Matrix3f identity() {
        ms[M00] = 1.0f;
        ms[M01] = 0.0f;
        ms[M02] = 0.0f;
        ms[M10] = 0.0f;
        ms[M11] = 1.0f;
        ms[M12] = 0.0f;
        ms[M20] = 0.0f;
        ms[M21] = 0.0f;
        ms[M22] = 1.0f;
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
    public Matrix3f scale(Vector3f xyz, Matrix3f dest) {
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
    public Matrix3f scale(Vector3f xyz) {
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
    public Matrix3f scale(float x, float y, float z, Matrix3f dest) {
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
    public Matrix3f scale(float x, float y, float z) {
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
     * @see #scale(float, float, float, Matrix3f)
     * 
     * @param xyz
     *            the factor for all components
     * @param dest
     *            will hold the result
     * @return dest
     */
    public Matrix3f scale(float xyz, Matrix3f dest) {
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
     * @see #scale(float, float, float)
     * 
     * @param xyz
     *            the factor for all components
     * @return this
     */
    public Matrix3f scale(float xyz) {
        return scale(xyz, xyz, xyz);
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
    public Matrix3f scaling(float factor) {
        ms[M00] = factor;
        ms[M01] = 0.0f;
        ms[M02] = 0.0f;
        ms[M10] = 0.0f;
        ms[M11] = factor;
        ms[M12] = 0.0f;
        ms[M20] = 0.0f;
        ms[M21] = 0.0f;
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
    public Matrix3f scaling(float x, float y, float z) {
        ms[M00] = x;
        ms[M01] = 0.0f;
        ms[M02] = 0.0f;
        ms[M10] = 0.0f;
        ms[M11] = y;
        ms[M12] = 0.0f;
        ms[M20] = 0.0f;
        ms[M21] = 0.0f;
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
     * matrix use {@link #scale(Vector3f) scale()} instead.
     * 
     * @see #scale(Vector3f)
     * 
     * @param xyz
     *             the scale in x, y and z respectively
     * @return this
     */
    public Matrix3f scaling(Vector3f xyz) {
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
    public Matrix3f rotation(float angle, Vector3f axis) {
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
    public Matrix3f rotation(AxisAngle4f axisAngle) {
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
    public Matrix3f rotation(float angle, float x, float y, float z) {
        float cos = (float) Math.cos(angle);
        float sin = (float) Math.sin(angle);
        float C = 1.0f - cos;
        float xy = x * y, xz = x * z, yz = y * z;
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
    public Matrix3f rotationX(float ang) {
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
        ms[M10] = 0.0f;
        ms[M11] = cos;
        ms[M12] = sin;
        ms[M20] = 0.0f;
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
    public Matrix3f rotationY(float ang) {
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
        ms[M10] = 0.0f;
        ms[M11] = 1.0f;
        ms[M12] = 0.0f;
        ms[M20] = sin;
        ms[M21] = 0.0f;
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
    public Matrix3f rotationZ(float ang) {
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
        ms[M10] = -sin;
        ms[M11] = cos;
        ms[M12] = 0.0f;
        ms[M20] = 0.0f;
        ms[M21] = 0.0f;
        ms[M22] = 1.0f;
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
    public Matrix3f rotationXYZ(float angleX, float angleY, float angleZ) {
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
    public Matrix3f rotationZYX(float angleZ, float angleY, float angleX) {
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
    public Matrix3f rotationYXZ(float angleY, float angleX, float angleZ) {
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
    public Matrix3f rotation(Quaternionf quat) {
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
        ms[M10] = q01 - q23;
        ms[M11] = 1.0f - q22 - q00;
        ms[M12] = q12 + q03;
        ms[M20] = q02 + q13;
        ms[M21] = q12 - q03;
        ms[M22] = 1.0f - q11 - q00;

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
    public Vector3f transform(Vector3f v, Vector3f dest) {
        v.mul(this, dest);
        return dest;
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeFloat(ms[M00]);
        out.writeFloat(ms[M01]);
        out.writeFloat(ms[M02]);
        out.writeFloat(ms[M10]);
        out.writeFloat(ms[M11]);
        out.writeFloat(ms[M12]);
        out.writeFloat(ms[M20]);
        out.writeFloat(ms[M21]);
        out.writeFloat(ms[M22]);
    }

    public void readExternal(ObjectInput in) throws IOException,
            ClassNotFoundException {
        ms[M00] = in.readFloat();
        ms[M01] = in.readFloat();
        ms[M02] = in.readFloat();
        ms[M10] = in.readFloat();
        ms[M11] = in.readFloat();
        ms[M12] = in.readFloat();
        ms[M20] = in.readFloat();
        ms[M21] = in.readFloat();
        ms[M22] = in.readFloat();
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
    public Matrix3f rotateX(float ang, Matrix3f dest) {
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
        float rn21 = -sin;
        float rn12 = sin;
        float rn22 = cos;

        // add temporaries for dependent values
        float nn10 = ms[M10] * rn11 + ms[M20] * rn12;
        float nn11 = ms[M11] * rn11 + ms[M21] * rn12;
        float nn12 = ms[M12] * rn11 + ms[M22] * rn12;
        // set non-dependent values directly
        dest.ms[M20] = ms[M10] * rn21 + ms[M20] * rn22;
        dest.ms[M21] = ms[M11] * rn21 + ms[M21] * rn22;
        dest.ms[M22] = ms[M12] * rn21 + ms[M22] * rn22;
        // set other values
        dest.ms[M10] = nn10;
        dest.ms[M11] = nn11;
        dest.ms[M12] = nn12;
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
    public Matrix3f rotateX(float ang) {
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
    public Matrix3f rotateY(float ang, Matrix3f dest) {
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
        float rn20 = sin;
        float rn02 = -sin;
        float rn22 = cos;

        // add temporaries for dependent values
        float nn00 = ms[M00] * rn00 + ms[M20] * rn02;
        float nn01 = ms[M01] * rn00 + ms[M21] * rn02;
        float nn02 = ms[M02] * rn00 + ms[M22] * rn02;
        // set non-dependent values directly
        dest.ms[M20] = ms[M00] * rn20 + ms[M20] * rn22;
        dest.ms[M21] = ms[M01] * rn20 + ms[M21] * rn22;
        dest.ms[M22] = ms[M02] * rn20 + ms[M22] * rn22;
        // set other values
        dest.ms[M00] = nn00;
        dest.ms[M01] = nn01;
        dest.ms[M02] = nn02;
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
    public Matrix3f rotateY(float ang) {
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
    public Matrix3f rotateZ(float ang, Matrix3f dest) {
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
        float rn10 = -sin;
        float rn01 = sin;
        float rn11 = cos;

        // add temporaries for dependent values
        float nn00 = ms[M00] * rn00 + ms[M10] * rn01;
        float nn01 = ms[M01] * rn00 + ms[M11] * rn01;
        float nn02 = ms[M02] * rn00 + ms[M12] * rn01;
        // set non-dependent values directly
        dest.ms[M10] = ms[M00] * rn10 + ms[M10] * rn11;
        dest.ms[M11] = ms[M01] * rn10 + ms[M11] * rn11;
        dest.ms[M12] = ms[M02] * rn10 + ms[M12] * rn11;
        // set other values
        dest.ms[M00] = nn00;
        dest.ms[M01] = nn01;
        dest.ms[M02] = nn02;
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
    public Matrix3f rotateZ(float ang) {
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
    public Matrix3f rotateXYZ(float angleX, float angleY, float angleZ) {
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
    public Matrix3f rotateXYZ(float angleX, float angleY, float angleZ, Matrix3f dest) {
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
        // rotateZ
        dest.ms[M00] = nn00 * cosZ + nn10 * sinZ;
        dest.ms[M01] = nn01 * cosZ + nn11 * sinZ;
        dest.ms[M02] = nn02 * cosZ + nn12 * sinZ;
        dest.ms[M10] = nn00 * m_sinZ + nn10 * cosZ;
        dest.ms[M11] = nn01 * m_sinZ + nn11 * cosZ;
        dest.ms[M12] = nn02 * m_sinZ + nn12 * cosZ;
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
    public Matrix3f rotateZYX(float angleZ, float angleY, float angleX) {
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
    public Matrix3f rotateZYX(float angleZ, float angleY, float angleX, Matrix3f dest) {
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
        // rotateX
        dest.ms[M10] = nn10 * cosX + nn20 * sinX;
        dest.ms[M11] = nn11 * cosX + nn21 * sinX;
        dest.ms[M12] = nn12 * cosX + nn22 * sinX;
        dest.ms[M20] = nn10 * m_sinX + nn20 * cosX;
        dest.ms[M21] = nn11 * m_sinX + nn21 * cosX;
        dest.ms[M22] = nn12 * m_sinX + nn22 * cosX;
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
    public Matrix3f rotate(float ang, float x, float y, float z) {
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
    public Matrix3f rotate(float ang, float x, float y, float z, Matrix3f dest) {
        float s = (float) Math.sin(ang);
        float c = (float) Math.cos(ang);
        float C = 1.0f - c;

        // rotation matrix elements:
        // ms[M30], ms[M31], ms[M32], ms[M03], ms[M13], ms[M23] = 0
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
        float nn10 = ms[M00] * rn10 + ms[M10] * rn11 + ms[M20] * rn12;
        float nn11 = ms[M01] * rn10 + ms[M11] * rn11 + ms[M21] * rn12;
        float nn12 = ms[M02] * rn10 + ms[M12] * rn11 + ms[M22] * rn12;
        // set non-dependent values directly
        dest.ms[M20] = ms[M00] * rn20 + ms[M10] * rn21 + ms[M20] * rn22;
        dest.ms[M21] = ms[M01] * rn20 + ms[M11] * rn21 + ms[M21] * rn22;
        dest.ms[M22] = ms[M02] * rn20 + ms[M12] * rn21 + ms[M22] * rn22;
        // set other values
        dest.ms[M00] = nn00;
        dest.ms[M01] = nn01;
        dest.ms[M02] = nn02;
        dest.ms[M10] = nn10;
        dest.ms[M11] = nn11;
        dest.ms[M12] = nn12;

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
    public Matrix3f rotate(Quaternionf quat) {
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
    public Matrix3f rotate(Quaternionf quat, Matrix3f dest) {
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
        float nn10 = ms[M00] * rn10 + ms[M10] * rn11 + ms[M20] * rn12;
        float nn11 = ms[M01] * rn10 + ms[M11] * rn11 + ms[M21] * rn12;
        float nn12 = ms[M02] * rn10 + ms[M12] * rn11 + ms[M22] * rn12;
        dest.ms[M20] = ms[M00] * rn20 + ms[M10] * rn21 + ms[M20] * rn22;
        dest.ms[M21] = ms[M01] * rn20 + ms[M11] * rn21 + ms[M21] * rn22;
        dest.ms[M22] = ms[M02] * rn20 + ms[M12] * rn21 + ms[M22] * rn22;
        dest.ms[M00] = nn00;
        dest.ms[M01] = nn01;
        dest.ms[M02] = nn02;
        dest.ms[M10] = nn10;
        dest.ms[M11] = nn11;
        dest.ms[M12] = nn12;

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
     * @see #rotate(float, float, float, float)
     * @see #rotation(AxisAngle4f)
     * 
     * @param axisAngle
     *          the {@link AxisAngle4f} (needs to be {@link AxisAngle4f#normalize() normalized})
     * @return this
     */
    public Matrix3f rotate(AxisAngle4f axisAngle) {
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
    public Matrix3f rotate(AxisAngle4f axisAngle, Matrix3f dest) {
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
    public Matrix3f rotate(float angle, Vector3f axis) {
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
    public Matrix3f rotate(float angle, Vector3f axis, Matrix3f dest) {
        return rotate(angle, axis.x, axis.y, axis.z, dest);
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
     * use {@link #setLookAlong(Vector3f, Vector3f) setLookAlong()}.
     * 
     * @see #lookAlong(float, float, float, float, float, float)
     * @see #setLookAlong(Vector3f, Vector3f)
     * 
     * @param dir
     *            the direction in space to look along
     * @param up
     *            the direction of 'up'
     * @return this
     */
    public Matrix3f lookAlong(Vector3f dir, Vector3f up) {
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
     * use {@link #setLookAlong(Vector3f, Vector3f) setLookAlong()}.
     * 
     * @see #lookAlong(float, float, float, float, float, float)
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
    public Matrix3f lookAlong(Vector3f dir, Vector3f up, Matrix3f dest) {
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
     * use {@link #setLookAlong(float, float, float, float, float, float) setLookAlong()}
     * 
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
    public Matrix3f lookAlong(float dirX, float dirY, float dirZ,
                              float upX, float upY, float upZ, Matrix3f dest) {
        // Normalize direction
        float invDirLength = (float) (1.0 / Math.sqrt(dirX * dirX + dirY * dirY + dirZ * dirZ));
        float dirnX = dirX * invDirLength;
        float dirnY = dirY * invDirLength;
        float dirnZ = dirZ * invDirLength;
        // right = direction x up
        float rightX, rightY, rightZ;
        rightX = dirnY * upZ - dirnZ * upY;
        rightY = dirnZ * upX - dirnX * upZ;
        rightZ = dirnX * upY - dirnY * upX;
        // normalize right
        float invRightLength = (float) (1.0 / Math.sqrt(rightX * rightX + rightY * rightY + rightZ * rightZ));
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
        float nn10 = ms[M00] * rn10 + ms[M10] * rn11 + ms[M20] * rn12;
        float nn11 = ms[M01] * rn10 + ms[M11] * rn11 + ms[M21] * rn12;
        float nn12 = ms[M02] * rn10 + ms[M12] * rn11 + ms[M22] * rn12;
        dest.ms[M20] = ms[M00] * rn20 + ms[M10] * rn21 + ms[M20] * rn22;
        dest.ms[M21] = ms[M01] * rn20 + ms[M11] * rn21 + ms[M21] * rn22;
        dest.ms[M22] = ms[M02] * rn20 + ms[M12] * rn21 + ms[M22] * rn22;
        // set the rest of the matrix elements
        dest.ms[M00] = nn00;
        dest.ms[M01] = nn01;
        dest.ms[M02] = nn02;
        dest.ms[M10] = nn10;
        dest.ms[M11] = nn11;
        dest.ms[M12] = nn12;

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
     * use {@link #setLookAlong(float, float, float, float, float, float) setLookAlong()}
     * 
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
    public Matrix3f lookAlong(float dirX, float dirY, float dirZ,
                              float upX, float upY, float upZ) {
        return lookAlong(dirX, dirY, dirZ, upX, upY, upZ, this);
    }

    /**
     * Set this matrix to a rotation transformation to make <code>-z</code>
     * point along <code>dir</code>.
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
    public Matrix3f setLookAlong(Vector3f dir, Vector3f up) {
        return setLookAlong(dir.x, dir.y, dir.z, up.x, up.y, up.z);
    }

    /**
     * Set this matrix to a rotation transformation to make <code>-z</code>
     * point along <code>dir</code>.
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
    public Matrix3f setLookAlong(float dirX, float dirY, float dirZ,
                                 float upX, float upY, float upZ) {
        // Normalize direction
        float invDirLength = (float) (1.0 / Math.sqrt(dirX * dirX + dirY * dirY + dirZ * dirZ));
        float dirnX = dirX * invDirLength;
        float dirnY = dirY * invDirLength;
        float dirnZ = dirZ * invDirLength;
        // right = direction x up
        float rightX, rightY, rightZ;
        rightX = dirnY * upZ - dirnZ * upY;
        rightY = dirnZ * upX - dirnX * upZ;
        rightZ = dirnX * upY - dirnY * upX;
        // normalize right
        float invRightLength = (float) (1.0 / Math.sqrt(rightX * rightX + rightY * rightY + rightZ * rightZ));
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
        ms[M10] = rightY;
        ms[M11] = upnY;
        ms[M12] = -dirnY;
        ms[M20] = rightZ;
        ms[M21] = upnZ;
        ms[M22] = -dirnZ;

        return this;
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
    public Vector3f getRow(int row, Vector3f dest) throws IndexOutOfBoundsException {
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
    public Vector3f getColumn(int column, Vector3f dest) throws IndexOutOfBoundsException {
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
     * In this case, use {@link #set(Matrix3f)} to set a given Matrix3f to this matrix.
     * 
     * @see #set(Matrix3f)
     * 
     * @return this
     */
    public Matrix3f normal() {
        return normal(this);
    }

    /**
     * Compute a normal matrix from <code>this</code> matrix and store it into <code>dest</code>.
     * <p>
     * Please note that, if <code>this</code> is an orthogonal matrix or a matrix whose columns are orthogonal vectors, 
     * then this method <i>need not</i> be invoked, since in that case <code>this</code> itself is its normal matrix.
     * In this case, use {@link #set(Matrix3f)} to set a given Matrix3f to this matrix.
     * 
     * @see #set(Matrix3f)
     * 
     * @param dest
     *             will hold the result
     * @return dest
     */
    public Matrix3f normal(Matrix3f dest) {
        float det = determinant();
        float s = 1.0f / det;
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
     * Obtain the direction of <tt>+Z</tt> before the transformation represented by <code>this</code> matrix is applied.
     * <p>
     * This method is equivalent to the following code:
     * <pre>
     * Matrix3f inv = new Matrix3f(this).invert();
     * inv.transform(dir.set(0, 0, 1)).normalize();
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
     * This method is equivalent to the following code:
     * <pre>
     * Matrix3f inv = new Matrix3f(this).transpose();
     * inv.transform(dir.set(0, 0, 1)).normalize();
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
     * This method is equivalent to the following code:
     * <pre>
     * Matrix3f inv = new Matrix3f(this).invert();
     * inv.transform(dir.set(1, 0, 0)).normalize();
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
     * This method is equivalent to the following code:
     * <pre>
     * Matrix3f inv = new Matrix3f(this).transpose();
     * inv.transform(dir.set(1, 0, 0)).normalize();
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
     * This method is equivalent to the following code:
     * <pre>
     * Matrix3f inv = new Matrix3f(this).invert();
     * inv.transform(dir.set(0, 1, 0)).normalize();
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
     * This method is equivalent to the following code:
     * <pre>
     * Matrix3f inv = new Matrix3f(this).transpose();
     * inv.transform(dir.set(0, 1, 0)).normalize();
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

    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Float.floatToIntBits(ms[M00]);
        result = prime * result + Float.floatToIntBits(ms[M01]);
        result = prime * result + Float.floatToIntBits(ms[M02]);
        result = prime * result + Float.floatToIntBits(ms[M10]);
        result = prime * result + Float.floatToIntBits(ms[M11]);
        result = prime * result + Float.floatToIntBits(ms[M12]);
        result = prime * result + Float.floatToIntBits(ms[M20]);
        result = prime * result + Float.floatToIntBits(ms[M21]);
        result = prime * result + Float.floatToIntBits(ms[M22]);
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Matrix3f other = (Matrix3f) obj;
        if (Float.floatToIntBits(ms[M00]) != Float.floatToIntBits(other.ms[M00]))
            return false;
        if (Float.floatToIntBits(ms[M01]) != Float.floatToIntBits(other.ms[M01]))
            return false;
        if (Float.floatToIntBits(ms[M02]) != Float.floatToIntBits(other.ms[M02]))
            return false;
        if (Float.floatToIntBits(ms[M10]) != Float.floatToIntBits(other.ms[M10]))
            return false;
        if (Float.floatToIntBits(ms[M11]) != Float.floatToIntBits(other.ms[M11]))
            return false;
        if (Float.floatToIntBits(ms[M12]) != Float.floatToIntBits(other.ms[M12]))
            return false;
        if (Float.floatToIntBits(ms[M20]) != Float.floatToIntBits(other.ms[M20]))
            return false;
        if (Float.floatToIntBits(ms[M21]) != Float.floatToIntBits(other.ms[M21]))
            return false;
        if (Float.floatToIntBits(ms[M22]) != Float.floatToIntBits(other.ms[M22]))
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
    public Matrix3f swap(Matrix3f other) {
        float tmp;
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
