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

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Abstract base class containing the readable definition of a 3x3 Matrix of doubles, and associated functions to transform
 * it. The matrix is column-major to match OpenGL's interpretation, and it looks like this:
 * <p>
 *      m00  m10  m20<br>
 *      m01  m11  m21<br>
 *      m02  m12  m22<br>
 * </p>
 */
public abstract class Matrix3dr {

    public abstract double m00();
    public abstract double m10();
    public abstract double m20();
    public abstract double m01();
    public abstract double m11();
    public abstract double m21();
    public abstract double m02();
    public abstract double m12();
    public abstract double m22();

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
    public Matrix3d mul(Matrix3dr right, Matrix3d dest) {
        dest.set(m00() * right.m00() + m10() * right.m01() + m20() * right.m02(),
                 m01() * right.m00() + m11() * right.m01() + m21() * right.m02(),
                 m02() * right.m00() + m12() * right.m01() + m22() * right.m02(),
                 m00() * right.m10() + m10() * right.m11() + m20() * right.m12(),
                 m01() * right.m10() + m11() * right.m11() + m21() * right.m12(),
                 m02() * right.m10() + m12() * right.m11() + m22() * right.m12(),
                 m00() * right.m20() + m10() * right.m21() + m20() * right.m22(),
                 m01() * right.m20() + m11() * right.m21() + m21() * right.m22(),
                 m02() * right.m20() + m12() * right.m21() + m22() * right.m22() );
        return dest;
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
    public Matrix3d mul(Matrix3fr right, Matrix3d dest) {
        dest.set(m00() * right.m00() + m10() * right.m01() + m20() * right.m02(),
                 m01() * right.m00() + m11() * right.m01() + m21() * right.m02(),
                 m02() * right.m00() + m12() * right.m01() + m22() * right.m02(),
                 m00() * right.m10() + m10() * right.m11() + m20() * right.m12(),
                 m01() * right.m10() + m11() * right.m11() + m21() * right.m12(),
                 m02() * right.m10() + m12() * right.m11() + m22() * right.m12(),
                 m00() * right.m20() + m10() * right.m21() + m20() * right.m22(),
                 m01() * right.m20() + m11() * right.m21() + m21() * right.m22(),
                 m02() * right.m20() + m12() * right.m21() + m22() * right.m22());
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
    public static void mul(Matrix3fr left, Matrix3dr right, Matrix3d dest) {
        dest.set(left.m00() * right.m00() + left.m10() * right.m01() + left.m20() * right.m02(),
                left.m01() * right.m00() + left.m11() * right.m01() + left.m21() * right.m02(),
                left.m02() * right.m00() + left.m12() * right.m01() + left.m22() * right.m02(),
                left.m00() * right.m10() + left.m10() * right.m11() + left.m20() * right.m12(),
                left.m01() * right.m10() + left.m11() * right.m11() + left.m21() * right.m12(),
                left.m02() * right.m10() + left.m12() * right.m11() + left.m22() * right.m12(),
                left.m00() * right.m20() + left.m10() * right.m21() + left.m20() * right.m22(),
                left.m01() * right.m20() + left.m11() * right.m21() + left.m21() * right.m22(),
                left.m02() * right.m20() + left.m12() * right.m21() + left.m22() * right.m22() );
    }

    /**
     * Return the determinant of this matrix.
     *
     * @return the determinant
     */
    public double determinant() {
        return m00() * (m11() * m22() - m12() * m21())
             + m01() * (m12() * m20() - m10() * m22())
             + m02() * (m01() * m21() - m11() * m20());
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
        dest.set((m11() * m22() - m21() * m12()) * s,
                 (m21() * m02() - m01() * m22()) * s,
                 (m01() * m12() - m11() * m02()) * s,
                 (m20() * m12() - m10() * m22()) * s,
                 (m00() * m22() - m20() * m02()) * s,
                 (m10() * m02() - m00() * m12()) * s,
                 (m10() * m21() - m20() * m11()) * s,
                 (m20() * m01() - m00() * m21()) * s,
                 (m00() * m11() - m10() * m01()) * s);
        return dest;
    }

    /**
     * Transpose <code>this</code> matrix and store the result in <code>dest</code>.
     *
     * @param dest
     *             will hold the result
     * @return dest
     */
    public Matrix3d transpose(Matrix3d dest) {
        dest.set(m00(), m10(), m20(),
                 m01(), m11(), m21(),
                 m02(), m12(), m22());
        return dest;
    }

    /**
     * Return a string representation of this matrix.
     * <p>
     * This method creates a new {@link java.text.DecimalFormat} on every invocation with the format string "<tt>  0.000E0; -</tt>".
     *
     * @return the string representation
     */
    public String toString() {
        DecimalFormat formatter = new DecimalFormat("  0.000E0; -"); //$NON-NLS-1$
        return toString(formatter).replaceAll("E(\\d+)", "E+$1"); //$NON-NLS-1$ //$NON-NLS-2$
    }

    /**
     * Return a string representation of this matrix by formatting the matrix elements with the given {@link java.text.NumberFormat}.
     *
     * @param formatter
     *          the {@link java.text.NumberFormat} used to format the matrix values with
     * @return the string representation
     */
    public String toString(NumberFormat formatter) {
        return formatter.format(m00()) + formatter.format(m10()) + formatter.format(m20()) + "\n" //$NON-NLS-1$
             + formatter.format(m01()) + formatter.format(m11()) + formatter.format(m21()) + "\n" //$NON-NLS-1$
             + formatter.format(m02()) + formatter.format(m12()) + formatter.format(m22()) + "\n"; //$NON-NLS-1$
    }

    /**
     * Get the current values of <code>this</code> matrix and store them into
     * <code>dest</code>.
     * <p>
     * This is the reverse method of {@link Matrix3d#set(Matrix3dr)} and allows to obtain
     * intermediate calculation results when chaining multiple transformations.
     *
     * @see Matrix3d#set(Matrix3dr)
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
     * into the given {@link org.joml.AxisAngle4f}.
     *
     * @see org.joml.AxisAngle4f#set(Matrix3dr)
     *
     * @param dest
     *          the destination {@link org.joml.AxisAngle4f}
     * @return the passed in destination
     */
    public AxisAngle4f getRotation(AxisAngle4f dest) {
        return dest.set(this);
    }

    /**
     * Get the current values of <code>this</code> matrix and store the represented rotation
     * into the given {@link org.joml.Quaternionf}.
     * <p>
     * This method assumes that the three column vectors of this matrix are not normalized and
     * thus allows to ignore any additional scaling factor that is applied to the matrix.
     *
     * @see org.joml.Quaternionf#setFromUnnormalized(Matrix3dr)
     *
     * @param dest
     *          the destination {@link org.joml.Quaternionf}
     * @return the passed in destination
     */
    public Quaternionf getUnnormalizedRotation(Quaternionf dest) {
        return dest.setFromUnnormalized(this);
    }

    /**
     * Get the current values of <code>this</code> matrix and store the represented rotation
     * into the given {@link org.joml.Quaternionf}.
     * <p>
     * This method assumes that the three column vectors of this matrix are normalized.
     *
     * @see org.joml.Quaternionf#setFromNormalized(Matrix3dr)
     *
     * @param dest
     *          the destination {@link org.joml.Quaternionf}
     * @return the passed in destination
     */
    public Quaternionf getNormalizedRotation(Quaternionf dest) {
        return dest.setFromNormalized(this);
    }

    /**
     * Get the current values of <code>this</code> matrix and store the represented rotation
     * into the given {@link org.joml.Quaterniond}.
     * <p>
     * This method assumes that the three column vectors of this matrix are not normalized and
     * thus allows to ignore any additional scaling factor that is applied to the matrix.
     *
     * @see org.joml.Quaterniond#setFromUnnormalized(Matrix3dr)
     *
     * @param dest
     *          the destination {@link org.joml.Quaterniond}
     * @return the passed in destination
     */
    public Quaterniond getUnnormalizedRotation(Quaterniond dest) {
        return dest.setFromUnnormalized(this);
    }

    /**
     * Get the current values of <code>this</code> matrix and store the represented rotation
     * into the given {@link org.joml.Quaterniond}.
     * <p>
     * This method assumes that the three column vectors of this matrix are normalized.
     *
     * @see org.joml.Quaterniond#setFromNormalized(Matrix3dr)
     *
     * @param dest
     *          the destination {@link org.joml.Quaterniond}
     * @return the passed in destination
     */
    public Quaterniond getNormalizedRotation(Quaterniond dest) {
        return dest.setFromNormalized(this);
    }

    /**
     * Store this matrix into the supplied {@link java.nio.DoubleBuffer} at the current
     * buffer {@link java.nio.DoubleBuffer#position() position} using column-major order.
     * <p>
     * This method will not increment the position of the given DoubleBuffer.
     * <p>
     * In order to specify the offset into the DoubleBuffer} at which
     * the matrix is stored, use {@link #get(int, java.nio.DoubleBuffer)}, taking
     * the absolute position as parameter.
     *
     * @see #get(int, java.nio.DoubleBuffer)
     *
     * @param buffer
     *            will receive the values of this matrix in column-major order at its current position
     * @return the passed in buffer
     */
    public DoubleBuffer get(DoubleBuffer buffer) {
        return get(buffer.position(), buffer);
    }

    /**
     * Store this matrix into the supplied {@link java.nio.DoubleBuffer} starting at the specified
     * absolute buffer position/index using column-major order.
     * <p>
     * This method will not increment the position of the given {@link java.nio.DoubleBuffer}.
     *
     * @param index
     *            the absolute position into the {@link java.nio.DoubleBuffer}
     * @param buffer
     *            will receive the values of this matrix in column-major order
     * @return the passed in buffer
     */
    public DoubleBuffer get(int index, DoubleBuffer buffer) {
        buffer.put(index, m00());
        buffer.put(index+1, m01());
        buffer.put(index+2, m02());
        buffer.put(index+3, m10());
        buffer.put(index+4, m11());
        buffer.put(index+5, m12());
        buffer.put(index+6, m20());
        buffer.put(index+7, m21());
        buffer.put(index+8, m22());
        return buffer;
    }

    /**
     * Store this matrix in column-major order into the supplied {@link java.nio.FloatBuffer} at the current
     * buffer {@link java.nio.FloatBuffer#position() position}.
     * <p>
     * This method will not increment the position of the given FloatBuffer.
     * <p>
     * In order to specify the offset into the FloatBuffer at which
     * the matrix is stored, use {@link #get(int, java.nio.FloatBuffer)}, taking
     * the absolute position as parameter.
     * <p>
     * Please note that due to this matrix storing double values those values will potentially
     * lose precision when they are converted to float values before being put into the given FloatBuffer.
     *
     * @see #get(int, java.nio.FloatBuffer)
     *
     * @param buffer
     *            will receive the values of this matrix in column-major order at its current position
     * @return the passed in buffer
     */
    public FloatBuffer get(FloatBuffer buffer) {
        return get(buffer.position(), buffer);
    }

    /**
     * Store this matrix in column-major order into the supplied {@link java.nio.FloatBuffer} starting at the specified
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
        buffer.put(index, (float) m00());
        buffer.put(index+1, (float) m01());
        buffer.put(index+2, (float) m02());
        buffer.put(index+3, (float) m10());
        buffer.put(index+4, (float) m11());
        buffer.put(index+5, (float) m12());
        buffer.put(index+6, (float) m20());
        buffer.put(index+7, (float) m21());
        buffer.put(index+8, (float) m22());
        return buffer;
    }

    /**
     * Store this matrix in column-major order into the supplied {@link java.nio.ByteBuffer} at the current
     * buffer {@link java.nio.ByteBuffer#position() position}.
     * <p>
     * This method will not increment the position of the given ByteBuffer.
     * <p>
     * In order to specify the offset into the ByteBuffer at which
     * the matrix is stored, use {@link #get(int, java.nio.ByteBuffer)}, taking
     * the absolute position as parameter.
     *
     * @see #get(int, java.nio.ByteBuffer)
     *
     * @param buffer
     *            will receive the values of this matrix in column-major order at its current position
     * @return the passed in buffer
     */
    public ByteBuffer get(ByteBuffer buffer) {
        return get(buffer.position(), buffer);
    }

    /**
     * Store this matrix in column-major order into the supplied {@link java.nio.ByteBuffer} starting at the specified
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
        buffer.putDouble(index+8*0, m00());
        buffer.putDouble(index+8*1, m01());
        buffer.putDouble(index+8*2, m02());
        buffer.putDouble(index+8*3, m10());
        buffer.putDouble(index+8*4, m11());
        buffer.putDouble(index+8*5, m12());
        buffer.putDouble(index+8*6, m20());
        buffer.putDouble(index+8*7, m21());
        buffer.putDouble(index+8*8, m22());
        return buffer;
    }

    /**
     * Store the elements of this matrix as float values in column-major order into the supplied {@link java.nio.ByteBuffer} at the current
     * buffer {@link java.nio.ByteBuffer#position() position}.
     * <p>
     * This method will not increment the position of the given ByteBuffer.
     * <p>
     * Please note that due to this matrix storing double values those values will potentially
     * lose precision when they are converted to float values before being put into the given ByteBuffer.
     * <p>
     * In order to specify the offset into the ByteBuffer at which
     * the matrix is stored, use {@link #getFloats(int, java.nio.ByteBuffer)}, taking
     * the absolute position as parameter.
     *
     * @see #getFloats(int, java.nio.ByteBuffer)
     *
     * @param buffer
     *            will receive the elements of this matrix as float values in column-major order at its current position
     * @return the passed in buffer
     */
    public ByteBuffer getFloats(ByteBuffer buffer) {
        return getFloats(buffer.position(), buffer);
    }

    /**
     * Store the elements of this matrix as float values in column-major order into the supplied {@link java.nio.ByteBuffer}
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
        buffer.putFloat(index+4*0,  (float)m00());
        buffer.putFloat(index+4*1,  (float)m01());
        buffer.putFloat(index+4*2,  (float)m02());
        buffer.putFloat(index+4*3,  (float)m10());
        buffer.putFloat(index+4*4,  (float)m11());
        buffer.putFloat(index+4*5,  (float)m12());
        buffer.putFloat(index+4*6,  (float)m20());
        buffer.putFloat(index+4*7,  (float)m21());
        buffer.putFloat(index+4*8, (float)m22());
        return buffer;
    }

    /**
     * Apply scaling to this matrix by scaling the base axes by the given <tt>xyz.x</tt>,
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
        // m00 = x, m11() = y, m22() = z
        // all others = 0
        dest.m00 = m00() * x;
        dest.m01 = m01() * x;
        dest.m02 = m02() * x;
        dest.m10 = m10() * y;
        dest.m11 = m11() * y;
        dest.m12 = m12() * y;
        dest.m20 = m20() * z;
        dest.m21 = m21() * z;
        dest.m22 = m22() * z;
        return dest;
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
    public Matrix3d rotationXYZ(double angleX, double angleY, double angleZ, Matrix3d dest) {
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
        // rotateZ
        dest.m00 = nm00 * cosZ;
        dest.m01 = nm01 * cosZ + nm11 * sinZ;
        dest.m02 = nm02 * cosZ + nm12 * sinZ;
        dest.m10 = nm00 * m_sinZ;
        dest.m11 = nm01 * m_sinZ + nm11 * cosZ;
        dest.m12 = nm02 * m_sinZ + nm12 * cosZ;
        return dest;
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
    public Matrix3d rotationZYX(double angleZ, double angleY, double angleX, Matrix3d dest) {
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
        // rotateX
        dest.m10 = nm10 * cosX + nm20 * sinX;
        dest.m11 = nm11 * cosX + nm21 * sinX;
        dest.m12 = nm22 * sinX;
        dest.m20 = nm10 * m_sinX + nm20 * cosX;
        dest.m21 = nm11 * m_sinX + nm21 * cosX;
        dest.m22 = nm22 * cosX;
        return dest;
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
    public Matrix3d rotationYXZ(double angleY, double angleX, double angleZ, Matrix3d dest) {
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
        // rotateZ
        dest.m00 = nm00 * cosZ + nm10 * sinZ;
        dest.m01 = nm11 * sinZ;
        dest.m02 = nm02 * cosZ + nm12 * sinZ;
        dest.m10 = nm00 * m_sinZ + nm10 * cosZ;
        dest.m11 = nm11 * cosZ;
        dest.m12 = nm02 * m_sinZ + nm12 * cosZ;
        return dest;
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
    public Vector3d transform(Vector3dr v, Vector3d dest) {
        v.mul(this, dest);
        return dest;
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
        double cos = Math.cos(ang);
        double sin = Math.sin(ang);
        double rm11 = cos;
        double rm21 = -sin;
        double rm12 = sin;
        double rm22 = cos;

        // add temporaries for dependent values
        double nm10 = m10() * rm11 + m20() * rm12;
        double nm11 = m11() * rm11 + m21() * rm12;
        double nm12 = m12() * rm11 + m22() * rm12;
        // set non-dependent values directly
        dest.m20 = m10() * rm21 + m20() * rm22;
        dest.m21 = m11() * rm21 + m21() * rm22;
        dest.m22 = m12() * rm21 + m22() * rm22;
        // set other values
        dest.m10 = nm10;
        dest.m11 = nm11;
        dest.m12 = nm12;
        dest.m00 = m00();
        dest.m01 = m01();
        dest.m02 = m02();

        return dest;
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
        double cos = Math.cos(ang);
        double sin = Math.sin(ang);
        double rm00 = cos;
        double rm20 = sin;
        double rm02 = -sin;
        double rm22 = cos;

        // add temporaries for dependent values
        double nm00 = m00() * rm00 + m20() * rm02;
        double nm01 = m01() * rm00 + m21() * rm02;
        double nm02 = m02() * rm00 + m22() * rm02;
        // set non-dependent values directly
        dest.m20 = m00() * rm20 + m20() * rm22;
        dest.m21 = m01() * rm20 + m21() * rm22;
        dest.m22 = m02() * rm20 + m22() * rm22;
        // set other values
        dest.m00 = nm00;
        dest.m01 = nm01;
        dest.m02 = nm02;
        dest.m10 = m10();
        dest.m11 = m11();
        dest.m12 = m12();

        return dest;
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
        double cos = Math.cos(ang);
        double sin = Math.sin(ang);
        double rm00 = cos;
        double rm10 = -sin;
        double rm01 = sin;
        double rm11 = cos;

        // add temporaries for dependent values
        double nm00 = m00() * rm00 + m10() * rm01;
        double nm01 = m01() * rm00 + m11() * rm01;
        double nm02 = m02() * rm00 + m12() * rm01;
        // set non-dependent values directly
        dest.m10 = m00() * rm10 + m10() * rm11;
        dest.m11 = m01() * rm10 + m11() * rm11;
        dest.m12 = m02() * rm10 + m12() * rm11;
        // set other values
        dest.m00 = nm00;
        dest.m01 = nm01;
        dest.m02 = nm02;
        dest.m20 = m20();
        dest.m21 = m21();
        dest.m22 = m22();

        return dest;
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
        double nm10 = m10() * cosX + m20() * sinX;
        double nm11 = m11() * cosX + m21() * sinX;
        double nm12 = m12() * cosX + m22() * sinX;
        double nm20 = m10() * m_sinX + m20() * cosX;
        double nm21 = m11() * m_sinX + m21() * cosX;
        double nm22 = m12() * m_sinX + m22() * cosX;
        // rotateY
        double nm00 = m00() * cosY + nm20 * m_sinY;
        double nm01 = m01() * cosY + nm21 * m_sinY;
        double nm02 = m02() * cosY + nm22 * m_sinY;
        dest.m20 = m00() * sinY + nm20 * cosY;
        dest.m21 = m01() * sinY + nm21 * cosY;
        dest.m22 = m02() * sinY + nm22 * cosY;
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
        double nm00 = m00() * cosZ + m10() * sinZ;
        double nm01 = m01() * cosZ + m11() * sinZ;
        double nm02 = m02() * cosZ + m12() * sinZ;
        double nm10 = m00() * m_sinZ + m10() * cosZ;
        double nm11 = m01() * m_sinZ + m11() * cosZ;
        double nm12 = m02() * m_sinZ + m12() * cosZ;
        // rotateY
        double nm20 = nm00 * sinY + m20() * cosY;
        double nm21 = nm01 * sinY + m21() * cosY;
        double nm22 = nm02 * sinY + m22() * cosY;
        dest.m00 = nm00 * cosY + m20() * m_sinY;
        dest.m01 = nm01 * cosY + m21() * m_sinY;
        dest.m02 = nm02 * cosY + m22() * m_sinY;
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
        double nm00 = m00() * rm00 + m10() * rm01 + m20() * rm02;
        double nm01 = m01() * rm00 + m11() * rm01 + m21() * rm02;
        double nm02 = m02() * rm00 + m12() * rm01 + m22() * rm02;
        double nm10 = m00() * rm10 + m10() * rm11 + m20() * rm12;
        double nm11 = m01() * rm10 + m11() * rm11 + m21() * rm12;
        double nm12 = m02() * rm10 + m12() * rm11 + m22() * rm12;
        // set non-dependent values directly
        dest.m20 = m00() * rm20 + m10() * rm21 + m20() * rm22;
        dest.m21 = m01() * rm20 + m11() * rm21 + m21() * rm22;
        dest.m22 = m02() * rm20 + m12() * rm21 + m22() * rm22;
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
     * Apply the rotation transformation of the given {@link org.joml.Quaterniondr} to this matrix and store
     * the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>Q</code> the rotation matrix obtained from the given quaternion,
     * then the new matrix will be <code>M * Q</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * Q * v</code>,
     * the quaternion rotation will be applied first!
     * <p>
     * In order to set the matrix to a rotation transformation without post-multiplying,
     * use {@link Matrix3d#rotation(Quaterniondr)}.
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Quaternion">http://en.wikipedia.org</a>
     *
     * @see Matrix3d#rotation(Quaterniondr)
     *
     * @param quat
     *          the {@link org.joml.Quaterniondr}
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Matrix3d rotate(Quaterniondr quat, Matrix3d dest) {
        double dqx = 2.0f * quat.x();
        double dqy = 2.0f * quat.y();
        double dqz = 2.0f * quat.z();
        double q00 = dqx * quat.x();
        double q11 = dqy * quat.y();
        double q22 = dqz * quat.z();
        double q01 = dqx * quat.y();
        double q02 = dqx * quat.z();
        double q03 = dqx * quat.w();
        double q12 = dqy * quat.z();
        double q13 = dqy * quat.w();
        double q23 = dqz * quat.w();

        double rm00 = 1.0 - q11 - q22;
        double rm01 = q01 + q23;
        double rm02 = q02 - q13;
        double rm10 = q01 - q23;
        double rm11 = 1.0 - q22 - q00;
        double rm12 = q12 + q03;
        double rm20 = q02 + q13;
        double rm21 = q12 - q03;
        double rm22 = 1.0 - q11 - q00;

        double nm00 = m00() * rm00 + m10() * rm01 + m20() * rm02;
        double nm01 = m01() * rm00 + m11() * rm01 + m21() * rm02;
        double nm02 = m02() * rm00 + m12() * rm01 + m22() * rm02;
        double nm10 = m00() * rm10 + m10() * rm11 + m20() * rm12;
        double nm11 = m01() * rm10 + m11() * rm11 + m21() * rm12;
        double nm12 = m02() * rm10 + m12() * rm11 + m22() * rm12;
        dest.m20 = m00() * rm20 + m10() * rm21 + m20() * rm22;
        dest.m21 = m01() * rm20 + m11() * rm21 + m21() * rm22;
        dest.m22 = m02() * rm20 + m12() * rm21 + m22() * rm22;
        dest.m00 = nm00;
        dest.m01 = nm01;
        dest.m02 = nm02;
        dest.m10 = nm10;
        dest.m11 = nm11;
        dest.m12 = nm12;

        return dest;
    }

    /**
     * Apply the rotation transformation of the given {@link org.joml.Quaternionf} to this matrix and store
     * the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>Q</code> the rotation matrix obtained from the given quaternion,
     * then the new matrix will be <code>M * Q</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * Q * v</code>,
     * the quaternion rotation will be applied first!
     * <p>
     * In order to set the matrix to a rotation transformation without post-multiplying,
     * use {@link Matrix3d#rotation(Quaternionfr)}.
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Quaternion">http://en.wikipedia.org</a>
     *
     * @see Matrix3d#rotation(Quaternionfr)
     *
     * @param quat
     *          the {@link org.joml.Quaternionfr}
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Matrix3d rotate(Quaternionfr quat, Matrix3d dest) {
        double dqx = 2.0f * quat.x();
        double dqy = 2.0f * quat.y();
        double dqz = 2.0f * quat.z();
        double q00 = dqx * quat.x();
        double q11 = dqy * quat.y();
        double q22 = dqz * quat.z();
        double q01 = dqx * quat.y();
        double q02 = dqx * quat.z();
        double q03 = dqx * quat.w();
        double q12 = dqy * quat.z();
        double q13 = dqy * quat.w();
        double q23 = dqz * quat.w();

        double rm00 = 1.0 - q11 - q22;
        double rm01 = q01 + q23;
        double rm02 = q02 - q13;
        double rm10 = q01 - q23;
        double rm11 = 1.0 - q22 - q00;
        double rm12 = q12 + q03;
        double rm20 = q02 + q13;
        double rm21 = q12 - q03;
        double rm22 = 1.0 - q11 - q00;

        double nm00 = m00() * rm00 + m10() * rm01 + m20() * rm02;
        double nm01 = m01() * rm00 + m11() * rm01 + m21() * rm02;
        double nm02 = m02() * rm00 + m12() * rm01 + m22() * rm02;
        double nm10 = m00() * rm10 + m10() * rm11 + m20() * rm12;
        double nm11 = m01() * rm10 + m11() * rm11 + m21() * rm12;
        double nm12 = m02() * rm10 + m12() * rm11 + m22() * rm12;
        dest.m20 = m00() * rm20 + m10() * rm21 + m20() * rm22;
        dest.m21 = m01() * rm20 + m11() * rm21 + m21() * rm22;
        dest.m22 = m02() * rm20 + m12() * rm21 + m22() * rm22;
        dest.m00 = nm00;
        dest.m01 = nm01;
        dest.m02 = nm02;
        dest.m10 = nm10;
        dest.m11 = nm11;
        dest.m12 = nm12;

        return dest;
    }

    /**
     * Apply a rotation transformation, rotating about the given {@link org.joml.AxisAngle4fr} and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>A</code> the rotation matrix obtained from the given {@link org.joml.AxisAngle4fr},
     * then the new matrix will be <code>M * A</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * A * v</code>,
     * the {@link org.joml.AxisAngle4f} rotation will be applied first!
     * <p>
     * In order to set the matrix to a rotation transformation without post-multiplying,
     * use {@link Matrix3d#rotation(org.joml.AxisAngle4fr)}.
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Axis_and_angle">http://en.wikipedia.org</a>
     *
     * @see Matrix3d#rotate(double, double, double, double)
     * @see Matrix3d#rotation(org.joml.AxisAngle4fr)
     *
     * @param axisAngle
     *          the {@link org.joml.AxisAngle4f} (needs to be {@link org.joml.AxisAngle4f#normalize() normalized})
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Matrix3d rotate(AxisAngle4fr axisAngle, Matrix3d dest) {
        return rotate(axisAngle.angle(), axisAngle.x(), axisAngle.y(), axisAngle.z(), dest);
    }

    /**
     * Apply a rotation transformation, rotating about the given {@link org.joml.AxisAngle4dr} and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>A</code> the rotation matrix obtained from the given {@link org.joml.AxisAngle4dr},
     * then the new matrix will be <code>M * A</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * A * v</code>,
     * the {@link org.joml.AxisAngle4d} rotation will be applied first!
     * <p>
     * In order to set the matrix to a rotation transformation without post-multiplying,
     * use {@link Matrix3d#rotation(org.joml.AxisAngle4dr)}.
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Axis_and_angle">http://en.wikipedia.org</a>
     *
     * @see Matrix3d#rotate(double, double, double, double)
     * @see Matrix3d#rotation(org.joml.AxisAngle4dr)
     *
     * @param axisAngle
     *          the {@link org.joml.AxisAngle4d} (needs to be {@link org.joml.AxisAngle4d#normalize() normalized})
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Matrix3d rotate(AxisAngle4dr axisAngle, Matrix3d dest) {
        return rotate(axisAngle.angle(), axisAngle.x(), axisAngle.y(), axisAngle.z(), dest);
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
     * use {@link Matrix3d#rotation(double, Vector3dr)}.
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Axis_and_angle">http://en.wikipedia.org</a>
     *
     * @see Matrix3d#rotate(double, double, double, double)
     * @see Matrix3d#rotation(double, Vector3dr)
     *
     * @param angle
     *          the angle in radians
     * @param axis
     *          the rotation axis (needs to be {@link org.joml.Vector3d#normalize() normalized})
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Matrix3d rotate(double angle, Vector3dr axis, Matrix3d dest) {
        return rotate(angle, axis.x(), axis.y(), axis.z(), dest);
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
     * use {@link Matrix3d#rotation(double, Vector3fr)}.
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Axis_and_angle">http://en.wikipedia.org</a>
     *
     * @see Matrix3d#rotate(double, double, double, double)
     * @see Matrix3d#rotation(double, Vector3fr)
     *
     * @param angle
     *          the angle in radians
     * @param axis
     *          the rotation axis (needs to be {@link org.joml.Vector3f#normalize() normalized})
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Matrix3d rotate(double angle, Vector3fr axis, Matrix3d dest) {
        return rotate(angle, axis.x(), axis.y(), axis.z(), dest);
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
            dest.x = m00();
            dest.y = m10();
            dest.z = m20();
            break;
        case 1:
            dest.x = m01();
            dest.y = m11();
            dest.z = m21();
            break;
        case 2:
            dest.x = m02();
            dest.y = m12();
            dest.z = m22();
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
            dest.x = m00();
            dest.y = m01();
            dest.z = m02();
            break;
        case 1:
            dest.x = m10();
            dest.y = m11();
            dest.z = m12();
            break;
        case 2:
            dest.x = m20();
            dest.y = m21();
            dest.z = m22();
            break;
        default:
            throw new IndexOutOfBoundsException();
        }
        return dest;
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
     * use {@link Matrix3d#setLookAlong(Vector3dr, Vector3dr) setLookAlong()}.
     *
     * @see Matrix3d#lookAlong(double, double, double, double, double, double)
     * @see Matrix3d#setLookAlong(Vector3dr, Vector3dr)
     *
     * @param dir
     *            the direction in space to look along
     * @param up
     *            the direction of 'up'
     * @param dest
     *            will hold the result
     * @return dest
     */
    public Matrix3d lookAlong(Vector3dr dir, Vector3dr up, Matrix3d dest) {
        return lookAlong(dir.x(), dir.y(), dir.z(), up.x(), up.y(), up.z(), dest);
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
     * use {@link Matrix3d#setLookAlong(double, double, double, double, double, double) setLookAlong()}
     *
     * @see Matrix3d#setLookAlong(double, double, double, double, double, double)
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
        double nm00 = m00() * rm00 + m10() * rm01 + m20() * rm02;
        double nm01 = m01() * rm00 + m11() * rm01 + m21() * rm02;
        double nm02 = m02() * rm00 + m12() * rm01 + m22() * rm02;
        double nm10 = m00() * rm10 + m10() * rm11 + m20() * rm12;
        double nm11 = m01() * rm10 + m11() * rm11 + m21() * rm12;
        double nm12 = m02() * rm10 + m12() * rm11 + m22() * rm12;
        dest.m20 = m00() * rm20 + m10() * rm21 + m20() * rm22;
        dest.m21 = m01() * rm20 + m11() * rm21 + m21() * rm22;
        dest.m22 = m02() * rm20 + m12() * rm21 + m22() * rm22;
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
     * Get the scaling factors of <code>this</code> matrix for the three base axes.
     *
     * @param dest
     *          will hold the scaling factors for <tt>x</tt>, <tt>y</tt> and <tt>z</tt>
     * @return dest
     */
    public Vector3d getScale(Vector3d dest) {
        dest.x = Math.sqrt(m00() * m00() + m01() * m01() + m02() * m02());
        dest.y = Math.sqrt(m10() * m10() + m11() * m11() + m12() * m12());
        dest.z = Math.sqrt(m20() * m20() + m21() * m21() + m22() * m22());
        return dest;
    }

    /**
     * Compute a normal matrix from <code>this</code> matrix and store it into <code>dest</code>.
     * <p>
     * Please note that, if <code>this</code> is an orthogonal matrix or a matrix whose columns are orthogonal vectors,
     * then this method <i>need not</i> be invoked, since in that case <code>this</code> itself is its normal matrix.
     * In this case, use {@link Matrix3d#set(org.joml.Matrix3dr)} to set a given Matrix3d to this matrix.
     *
     * @see Matrix3d#set(org.joml.Matrix3dr)
     *
     * @param dest
     *             will hold the result
     * @return dest
     */
    public Matrix3d normal(Matrix3d dest) {
        double det = determinant();
        double s = 1.0 / det;
        /* Invert and transpose in one go */
        dest.set((m11() * m22() - m21() * m12()) * s,
                 (m20() * m12() - m10() * m22()) * s,
                 (m10() * m21() - m20() * m11()) * s,
                 (m21() * m02() - m01() * m22()) * s,
                 (m00() * m22() - m20() * m02()) * s,
                 (m20() * m01() - m00() * m21()) * s,
                 (m01() * m12() - m11() * m02()) * s,
                 (m10() * m02() - m00() * m12()) * s,
                 (m00() * m11() - m10() * m01()) * s);
        return dest;
    }
}
