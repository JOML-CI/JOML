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
import java.nio.FloatBuffer;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Abstract base class containing the readable definition of a 3x3 Matrix of floats, and associated functions to transform
 * it. The matrix is column-major to match OpenGL's interpretation, and it looks like this:
 * <p>
 *      m00  m10  m20<br>
 *      m01  m11  m21<br>
 *      m02  m12  m22<br>
 *
 * @author Richard Greenlees
 * @author Kai Burjack
 */
public abstract class Matrix3fr {

    public abstract float m00();
    public abstract float m10();
    public abstract float m20();
    public abstract float m01();
    public abstract float m11();
    public abstract float m21();
    public abstract float m02();
    public abstract float m12();
    public abstract float m22();

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
    public Matrix3f mul(Matrix3fr right, Matrix3f dest) {
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
     * Return the determinant of this matrix.
     *
     * @return the determinant
     */
    public float determinant() {
        return m00() * (m11() * m22() - m12() * m21())
             + m01() * (m12() * m20() - m10() * m22())
             + m02() * (m01() * m21() - m11() * m20());
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
    public Matrix3f transpose(Matrix3f dest) {
        dest.set(m00(), m10(), m20(),
                 m01(), m11(), m21(),
                 m02(), m12(), m22());
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
        return formatter.format(m00()) + formatter.format(m10()) + formatter.format(m20()) + "\n" //$NON-NLS-1$
             + formatter.format(m01()) + formatter.format(m11()) + formatter.format(m21()) + "\n" //$NON-NLS-1$
             + formatter.format(m02()) + formatter.format(m12()) + formatter.format(m22()) + "\n"; //$NON-NLS-1$
    }

    /**
     * Get the current values of <code>this</code> matrix and store them into
     * <code>dest</code>.
     * <p>
     * This is the reverse method of {@link Matrix3f#set(Matrix3fr)} and allows to obtain
     * intermediate calculation results when chaining multiple transformations.
     *
     * @see Matrix3f#set(Matrix3fr)
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
     * @see Matrix4f#set(Matrix3fr)
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
     * @see AxisAngle4f#set(Matrix3fr)
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
     * @see Quaternionf#setFromUnnormalized(Matrix3fr)
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
     * @see Quaternionf#setFromNormalized(Matrix3fr)
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
     * @see Quaterniond#setFromUnnormalized(Matrix3fr)
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
     * @see Quaterniond#setFromNormalized(Matrix3fr)
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
        buffer.put(index,   m00());
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
        buffer.putFloat(index,    m00());
        buffer.putFloat(index+4,  m01());
        buffer.putFloat(index+8,  m02());
        buffer.putFloat(index+12, m10());
        buffer.putFloat(index+16, m11());
        buffer.putFloat(index+20, m12());
        buffer.putFloat(index+24, m20());
        buffer.putFloat(index+28, m21());
        buffer.putFloat(index+32, m22());
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
        buffer.put(index,    m00());
        buffer.put(index+1,  m10());
        buffer.put(index+2,  m20());
        buffer.put(index+3,  m01());
        buffer.put(index+4,  m11());
        buffer.put(index+5,  m21());
        buffer.put(index+6,  m02());
        buffer.put(index+7,  m12());
        buffer.put(index+8, m22());
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
        buffer.putFloat(index,    m00());
        buffer.putFloat(index+4,  m10());
        buffer.putFloat(index+8,  m20());
        buffer.putFloat(index+12, m01());
        buffer.putFloat(index+16, m11());
        buffer.putFloat(index+20, m21());
        buffer.putFloat(index+24, m02());
        buffer.putFloat(index+28, m12());
        buffer.putFloat(index+32, m22());
        return buffer;
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
    public Matrix3f scale(Vector3fr xyz, Matrix3f dest) {
        return scale(xyz.x(), xyz.y(), xyz.z(), dest);
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
        // m00() = x, m11() = y, m22() = z
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
    public Matrix3f rotationXYZ(float angleX, float angleY, float angleZ, Matrix3f dest) {
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
        float nm11 = cosX;
        float nm12 = sinX;
        float nm21 = m_sinX;
        float nm22 = cosX;
        // rotateY
        float nm00 = cosY;
        float nm01 = nm21 * m_sinY;
        float nm02 = nm22 * m_sinY;
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
    public Matrix3f rotationZYX(float angleZ, float angleY, float angleX, Matrix3f dest) {
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
        float nm00 = cosZ;
        float nm01 = sinZ;
        float nm10 = m_sinZ;
        float nm11 = cosZ;
        // rotateY
        float nm20 = nm00 * sinY;
        float nm21 = nm01 * sinY;
        float nm22 = cosY;
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
    public Matrix3f rotationYXZ(float angleY, float angleX, float angleZ, Matrix3f dest) {
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
        float nm00 = cosY;
        float nm02 = m_sinY;
        float nm20 = sinY;
        float nm22 = cosY;
        // rotateX
        float nm10 = nm20 * sinX;
        float nm11 = cosX;
        float nm12 = nm22 * sinX;
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
     * Transform the given vector by this matrix and store the result in <code>dest</code>.
     *
     * @param v
     *          the vector to transform
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Vector3f transform(Vector3fr v, Vector3f dest) {
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
    public Matrix3f rotateX(float ang, Matrix3f dest) {
        float cos = (float) Math.cos(ang);
        float sin = (float) Math.sin(ang);
        float rm11 = cos;
        float rm21 = -sin;
        float rm12 = sin;
        float rm22 = cos;

        // add temporaries for dependent values
        float nm10 = m10() * rm11 + m20() * rm12;
        float nm11 = m11() * rm11 + m21() * rm12;
        float nm12 = m12() * rm11 + m22() * rm12;
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
    public Matrix3f rotateY(float ang, Matrix3f dest) {
        float cos = (float) Math.cos(ang);
        float sin = (float) Math.sin(ang);
        float rm00 = cos;
        float rm20 = sin;
        float rm02 = -sin;
        float rm22 = cos;

        // add temporaries for dependent values
        float nm00 = m00() * rm00 + m20() * rm02;
        float nm01 = m01() * rm00 + m21() * rm02;
        float nm02 = m02() * rm00 + m22() * rm02;
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
    public Matrix3f rotateZ(float ang, Matrix3f dest) {
        float cos = (float) Math.cos(ang);
        float sin = (float) Math.sin(ang);
        float rm00 = cos;
        float rm10 = -sin;
        float rm01 = sin;
        float rm11 = cos;

        // add temporaries for dependent values
        float nm00 = m00() * rm00 + m10() * rm01;
        float nm01 = m01() * rm00 + m11() * rm01;
        float nm02 = m02() * rm00 + m12() * rm01;
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
        float nm10 = m10() * cosX + m20() * sinX;
        float nm11 = m11() * cosX + m21() * sinX;
        float nm12 = m12() * cosX + m22() * sinX;
        float nm20 = m10() * m_sinX + m20() * cosX;
        float nm21 = m11() * m_sinX + m21() * cosX;
        float nm22 = m12() * m_sinX + m22() * cosX;
        // rotateY
        float nm00 = m00() * cosY + nm20 * m_sinY;
        float nm01 = m01() * cosY + nm21 * m_sinY;
        float nm02 = m02() * cosY + nm22 * m_sinY;
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
        float nm00 = m00() * cosZ + m10() * sinZ;
        float nm01 = m01() * cosZ + m11() * sinZ;
        float nm02 = m02() * cosZ + m12() * sinZ;
        float nm10 = m00() * m_sinZ + m10() * cosZ;
        float nm11 = m01() * m_sinZ + m11() * cosZ;
        float nm12 = m02() * m_sinZ + m12() * cosZ;
        // rotateY
        float nm20 = nm00 * sinY + m20() * cosY;
        float nm21 = nm01 * sinY + m21() * cosY;
        float nm22 = nm02 * sinY + m22() * cosY;
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
    public Matrix3f rotate(float ang, float x, float y, float z, Matrix3f dest) {
        float s = (float) Math.sin(ang);
        float c = (float) Math.cos(ang);
        float C = 1.0f - c;

        // rotation matrix elements:
        // m30, m31, m32, m03, m13, m23 = 0
        float xx = x * x, xy = x * y, xz = x * z;
        float yy = y * y, yz = y * z;
        float zz = z * z;
        float rm00 = xx * C + c;
        float rm01 = xy * C + z * s;
        float rm02 = xz * C - y * s;
        float rm10 = xy * C - z * s;
        float rm11 = yy * C + c;
        float rm12 = yz * C + x * s;
        float rm20 = xz * C + y * s;
        float rm21 = yz * C - x * s;
        float rm22 = zz * C + c;

        // add temporaries for dependent values
        float nm00 = m00() * rm00 + m10() * rm01 + m20() * rm02;
        float nm01 = m01() * rm00 + m11() * rm01 + m21() * rm02;
        float nm02 = m02() * rm00 + m12() * rm01 + m22() * rm02;
        float nm10 = m00() * rm10 + m10() * rm11 + m20() * rm12;
        float nm11 = m01() * rm10 + m11() * rm11 + m21() * rm12;
        float nm12 = m02() * rm10 + m12() * rm11 + m22() * rm12;
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
     * Apply the rotation transformation of the given {@link Quaternionfr} to this matrix and store
     * the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>Q</code> the rotation matrix obtained from the given quaternion,
     * then the new matrix will be <code>M * Q</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * Q * v</code>,
     * the quaternion rotation will be applied first!
     * <p>
     * In order to set the matrix to a rotation transformation without post-multiplying,
     * use {@link Matrix3f#rotation(Quaternionfr)}.
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Quaternion">http://en.wikipedia.org</a>
     *
     * @see Matrix3f#rotation(Quaternionfr)
     *
     * @param quat
     *          the {@link Quaternionf}
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Matrix3f rotate(Quaternionfr quat, Matrix3f dest) {
        float dqx = 2.0f * quat.x();
        float dqy = 2.0f * quat.y();
        float dqz = 2.0f * quat.z();
        float q00 = dqx * quat.x();
        float q11 = dqy * quat.y();
        float q22 = dqz * quat.z();
        float q01 = dqx * quat.y();
        float q02 = dqx * quat.z();
        float q03 = dqx * quat.w();
        float q12 = dqy * quat.z();
        float q13 = dqy * quat.w();
        float q23 = dqz * quat.w();

        float rm00 = 1.0f - q11 - q22;
        float rm01 = q01 + q23;
        float rm02 = q02 - q13;
        float rm10 = q01 - q23;
        float rm11 = 1.0f - q22 - q00;
        float rm12 = q12 + q03;
        float rm20 = q02 + q13;
        float rm21 = q12 - q03;
        float rm22 = 1.0f - q11 - q00;

        float nm00 = m00() * rm00 + m10() * rm01 + m20() * rm02;
        float nm01 = m01() * rm00 + m11() * rm01 + m21() * rm02;
        float nm02 = m02() * rm00 + m12() * rm01 + m22() * rm02;
        float nm10 = m00() * rm10 + m10() * rm11 + m20() * rm12;
        float nm11 = m01() * rm10 + m11() * rm11 + m21() * rm12;
        float nm12 = m02() * rm10 + m12() * rm11 + m22() * rm12;
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
     * Apply a rotation transformation, rotating about the given {@link AxisAngle4fr} and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>A</code> the rotation matrix obtained from the given {@link AxisAngle4fr},
     * then the new matrix will be <code>M * A</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * A * v</code>,
     * the {@link AxisAngle4f} rotation will be applied first!
     * <p>
     * In order to set the matrix to a rotation transformation without post-multiplying,
     * use {@link Matrix3f#rotation(AxisAngle4fr)}.
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Axis_and_angle">http://en.wikipedia.org</a>
     *
     * @see Matrix3f#rotate(float, float, float, float)
     * @see Matrix3f#rotation(AxisAngle4fr)
     *
     * @param axisAngle
     *          the {@link AxisAngle4fr} (needs to be {@link AxisAngle4f#normalize() normalized})
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Matrix3f rotate(AxisAngle4fr axisAngle, Matrix3f dest) {
        return rotate(axisAngle.angle(), axisAngle.x(), axisAngle.y(), axisAngle.z(), dest);
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
     * use {@link Matrix3f#rotation(float, Vector3fr)}.
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Axis_and_angle">http://en.wikipedia.org</a>
     *
     * @see Matrix3f#rotate(float, float, float, float)
     * @see Matrix3f#rotation(float, Vector3fr)
     *
     * @param angle
     *          the angle in radians
     * @param axis
     *          the rotation axis (needs to be {@link Vector3f#normalize() normalized})
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Matrix3f rotate(float angle, Vector3fr axis, Matrix3f dest) {
        return rotate(angle, axis.x(), axis.y(), axis.z(), dest);
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
     * Apply a rotation transformation to this matrix to make <code>-z</code> point along <code>dir</code>
     * and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>L</code> the lookalong rotation matrix,
     * then the new matrix will be <code>M * L</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * L * v</code>, the
     * lookalong rotation transformation will be applied first!
     * <p>
     * In order to set the matrix to a lookalong transformation without post-multiplying it,
     * use {@link Matrix3f#setLookAlong(Vector3fr, Vector3fr) setLookAlong()}.
     *
     * @see Matrix3f#lookAlong(float, float, float, float, float, float)
     * @see Matrix3f#setLookAlong(Vector3fr, Vector3fr)
     *
     * @param dir
     *            the direction in space to look along
     * @param up
     *            the direction of 'up'
     * @param dest
     *            will hold the result
     * @return dest
     */
    public Matrix3f lookAlong(Vector3fr dir, Vector3fr up, Matrix3f dest) {
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
     * use {@link Matrix3f#setLookAlong(float, float, float, float, float, float) setLookAlong()}
     *
     * @see Matrix3f#setLookAlong(float, float, float, float, float, float)
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
        float rm00 = rightX;
        float rm01 = upnX;
        float rm02 = -dirnX;
        float rm10 = rightY;
        float rm11 = upnY;
        float rm12 = -dirnY;
        float rm20 = rightZ;
        float rm21 = upnZ;
        float rm22 = -dirnZ;

        // perform optimized matrix multiplication
        // introduce temporaries for dependent results
        float nm00 = m00() * rm00 + m10() * rm01 + m20() * rm02;
        float nm01 = m01() * rm00 + m11() * rm01 + m21() * rm02;
        float nm02 = m02() * rm00 + m12() * rm01 + m22() * rm02;
        float nm10 = m00() * rm10 + m10() * rm11 + m20() * rm12;
        float nm11 = m01() * rm10 + m11() * rm11 + m21() * rm12;
        float nm12 = m02() * rm10 + m12() * rm11 + m22() * rm12;
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
    public Vector3f getColumn(int column, Vector3f dest) throws IndexOutOfBoundsException {
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
     * Compute a normal matrix from <code>this</code> matrix and store it into <code>dest</code>.
     * <p>
     * Please note that, if <code>this</code> is an orthogonal matrix or a matrix whose columns are orthogonal vectors,
     * then this method <i>need not</i> be invoked, since in that case <code>this</code> itself is its normal matrix.
     * In this case, use {@link Matrix3f#set(Matrix3fr)} to set a given Matrix3f to this matrix.
     *
     * @see Matrix3f#set(Matrix3fr)
     *
     * @param dest
     *             will hold the result
     * @return dest
     */
    public Matrix3f normal(Matrix3f dest) {
        float det = determinant();
        float s = 1.0f / det;
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
}
