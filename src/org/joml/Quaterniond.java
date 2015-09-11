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

/**
 * Contains the definition and functions for rotations expressed as
 * 4-dimensional vectors
 *
 * @author Richard Greenlees
 * @author Kai Burjack
 */
public class Quaterniond extends Quaterniondr implements Externalizable {

    private static final long serialVersionUID = 1L;

    /**
     * The first component of the vector part.
     */
    public double x;
    /**
     * The second component of the vector part.
     */
    public double y;
    /**
     * The third component of the vector part.
     */
    public double z;
    /**
     * The real/scalar part of the quaternion.
     */
    public double w;

    /**
     * Create a new {@link Quaterniond} and initialize it with <tt>(x=0, y=0, z=0, w=1)</tt>, 
     * where <tt>(x, y, z)</tt> is the vector part of the quaternion and <tt>w</tt> is the real/scalar part.
     */
    public Quaterniond() {
        x = 0.0;
        y = 0.0;
        z = 0.0;
        w = 1.0;
    }

    /**
     * Create a new {@link Quaterniond} and initialize its components to the given values.
     * 
     * @param x
     *          the first component of the imaginary part
     * @param y
     *          the second component of the imaginary part
     * @param z
     *          the third component of the imaginary part
     * @param w
     *          the real part
     */
    public Quaterniond(double x, double y, double z, double w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    /**
     * Create a new {@link Quaterniond} and initialize its imaginary components to the given values,
     * and its real part to <tt>1.0</tt>.
     * 
     * @param x
     *          the first component of the imaginary part
     * @param y
     *          the second component of the imaginary part
     * @param z
     *          the third component of the imaginary part
     */
    public Quaterniond(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = 1.0;
    }

    /**
     * Create a new {@link Quaterniond} and initialize its components to the same values as the given {@link Quaterniondr}.
     * 
     * @param source
     *          the {@link Quaterniondr} to take the component values from
     */
    public Quaterniond(Quaterniondr source) {
        x = source.x();
        y = source.y();
        z = source.z();
        w = source.w();
    }

    /**
     * Create a new {@link Quaterniond} and initialize its components to the same values as the given {@link Quaternionfr}.
     * 
     * @param source
     *          the {@link Quaternionfr} to take the component values from
     */
    public Quaterniond(Quaternionfr source) {
        x = source.x();
        y = source.y();
        z = source.z();
        w = source.w();
    }

    /**
     * Create a new {@link Quaterniond} and initialize it to represent the same rotation as the given {@link AxisAngle4fr}.
     * 
     * @param axisAngle
     *          the axis-angle to initialize this quaternion with
     */
    public Quaterniond(AxisAngle4fr axisAngle) {
        double s = Math.sin(axisAngle.angle() * 0.5);
        x = axisAngle.x() * s;
        y = axisAngle.y() * s;
        z = axisAngle.z() * s;
        w = Math.cos(axisAngle.angle() * 0.5);
    }

    /**
     * Create a new {@link Quaterniond} and initialize it to represent the same rotation as the given {@link AxisAngle4dr}.
     * 
     * @param axisAngle
     *          the axis-angle to initialize this quaternion with
     */
    public Quaterniond(AxisAngle4dr axisAngle) {
        double s = Math.sin(axisAngle.angle() * 0.5);
        x = axisAngle.x() * s;
        y = axisAngle.y() * s;
        z = axisAngle.z() * s;
        w = Math.cos(axisAngle.angle() * 0.5);
    }

    public double x() {
        return x;
    }

    public double y() {
        return y;
    }

    public double z() {
        return z;
    }

    public double w() {
        return w;
    }

    /**
     * Normalize this quaternion.
     * 
     * @return this
     */
    public Quaterniond normalize() {
        double invNorm = 1.0 / Math.sqrt(x * x + y * y + z * z + w * w);
        x *= invNorm;
        y *= invNorm;
        z *= invNorm;
        w *= invNorm;
        return this;
    }

    /**
     * Add <code>q2</code> to this quaternion.
     * 
     * @param q2
     *          the quaternion to add to this
     * @return this
     */
    public Quaterniond add(Quaterniondr q2) {
        x += q2.x();
        y += q2.y();
        z += q2.z();
        w += q2.w();
        return this;
    }

    /**
     * Set this quaternion to the new values.
     * 
     * @param x
     *          the new value of x
     * @param y
     *          the new value of y
     * @param z
     *          the new value of z
     * @param w
     *          the new value of w
     * @return this
     */
    public Quaterniond set(double x, double y, double z, double w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
        return this;
    }

    /**
     * Set the x, y and z components of this quaternion to the new values.
     * 
     * @param x
     *          the new value of x
     * @param y
     *          the new value of y
     * @param z
     *          the new value of z
     * @return this
     */
    public Quaterniond set(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }

    /**
     * Set this quaternion to be a copy of q.
     * 
     * @param q
     *          the {@link Quaterniond} to copy
     * @return this
     */
    public Quaterniond set(Quaterniondr q) {
        x = q.x();
        y = q.y();
        z = q.z();
        w = q.w();
        return this;
    }

    /**
     * Set this quaternion to be a copy of q.
     * 
     * @param q
     *          the {@link Quaternionfr} to copy
     * @return this
     */
    public Quaterniond set(Quaternionfr q) {
        x = q.x();
        y = q.y();
        z = q.z();
        w = q.w();
        return this;
    }

    /**
     * Set this {@link Quaterniond} to be equivalent to the given
     * {@link AxisAngle4fr}.
     * 
     * @param axisAngle
     *            the {@link AxisAngle4fr}
     * @return this
     */
    public Quaterniond set(AxisAngle4fr axisAngle) {
        return setAngleAxis(axisAngle.angle(), axisAngle.x(), axisAngle.y(), axisAngle.z());
    }

    /**
     * Set this {@link Quaterniond} to be equivalent to the given
     * {@link AxisAngle4dr}.
     * 
     * @param axisAngle
     *            the {@link AxisAngle4dr}
     * @return this
     */
    public Quaterniond set(AxisAngle4dr axisAngle) {
        return setAngleAxis(axisAngle.angle(), axisAngle.x(), axisAngle.y(), axisAngle.z());
    }

    /**
     * Set this quaternion to a rotation equivalent to the supplied axis and
     * angle (in radians).
     * <p>
     * This method assumes that the given rotation axis <tt>(x, y, z)</tt> is already normalized
     * 
     * @param angle
     *          the angle in radians
     * @param x
     *          the x-component of the normalized rotation axis
     * @param y
     *          the y-component of the normalized rotation axis
     * @param z
     *          the z-component of the normalized rotation axis
     * @return this
     */
    public Quaterniond setAngleAxis(double angle, double x, double y, double z) {
        double s = Math.sin(angle * 0.5);
        this.x = x * s;
        this.y = y * s;
        this.z = z * s;
        this.w = Math.cos(angle * 0.5);
        return this;
    }

    /**
     * Set this quaternion to be a representation of the supplied axis and
     * angle (in radians).
     * 
     * @param angle
     *          the angle in radians
     * @param axis
     *          the rotation axis
     * @return this
     */
    public Quaterniond setAngleAxis(double angle, Vector3dr axis) {
        return setAngleAxis(angle, axis.x(), axis.y(), axis.z());
    }

    private void setFromUnnormalized(double m00, double m01, double m02, double m10, double m11, double m12, double m20, double m21, double m22) {
        double nm00 = m00, nm01 = m01, nm02 = m02;
        double nm10 = m10, nm11 = m11, nm12 = m12;
        double nm20 = m20, nm21 = m21, nm22 = m22;
        double lenX = 1.0 / Math.sqrt(m00 * m00 + m01 * m01 + m02 * m02);
        double lenY = 1.0 / Math.sqrt(m10 * m10 + m11 * m11 + m12 * m12);
        double lenZ = 1.0 / Math.sqrt(m20 * m20 + m21 * m21 + m22 * m22);
        nm00 *= lenX; nm01 *= lenX; nm02 *= lenX;
        nm10 *= lenY; nm11 *= lenY; nm12 *= lenY;
        nm20 *= lenZ; nm21 *= lenZ; nm22 *= lenZ;
        setFromNormalized(nm00, nm01, nm02, nm10, nm11, nm12, nm20, nm21, nm22);
    }

    private void setFromNormalized(double m00, double m01, double m02, double m10, double m11, double m12, double m20, double m21, double m22) {
        double t;
        double tr = m00 + m11 + m22;
        if (tr >= 0.0) {
            t = Math.sqrt(tr + 1.0);
            w = t * 0.5;
            t = 0.5 / t;
            x = (m12 - m21) * t;
            y = (m20 - m02) * t;
            z = (m01 - m10) * t;
        } else {
            if (m00 >= m11 && m00 >= m22) {
                t = Math.sqrt(m00 - (m11 + m22) + 1.0);
                x = t * 0.5;
                t = 0.5 / t;
                y = (m10 + m01) * t;
                z = (m02 + m20) * t;
                w = (m12 - m21) * t;
            } else if (m11 > m22) {
                t = Math.sqrt(m11 - (m22 + m00) + 1.0);
                y = t * 0.5;
                t = 0.5 / t;
                z = (m21 + m12) * t;
                x = (m10 + m01) * t;
                w = (m20 - m02) * t;
            } else {
                t = Math.sqrt(m22 - (m00 + m11) + 1.0);
                z = t * 0.5;
                t = 0.5 / t;
                x = (m02 + m20) * t;
                y = (m21 + m12) * t;
                w = (m01 - m10) * t;
            }
        }
    }

    /**
     * Set this quaternion to be a representation of the rotational component of the given matrix.
     * <p>
     * This method assumes that the first three columns of the upper left 3x3 submatrix are no unit vectors.
     * 
     * @param mat
     *          the matrix whose rotational component is used to set this quaternion
     * @return this
     */
    public Quaterniond setFromUnnormalized(Matrix4fr mat) {
        setFromUnnormalized(mat.m00(), mat.m01(), mat.m02(), mat.m10(), mat.m11(), mat.m12(), mat.m20(), mat.m21(), mat.m22());
        return this;
    }

    /**
     * Set this quaternion to be a representation of the rotational component of the given matrix.
     * <p>
     * This method assumes that the first three columns of the upper left 3x3 submatrix are unit vectors.
     * 
     * @param mat
     *          the matrix whose rotational component is used to set this quaternion
     * @return this
     */
    public Quaterniond setFromNormalized(Matrix4fr mat) {
        setFromNormalized(mat.m00(), mat.m01(), mat.m02(), mat.m10(), mat.m11(), mat.m12(), mat.m20(), mat.m21(), mat.m22());
        return this;
    }

    /**
     * Set this quaternion to be a representation of the rotational component of the given matrix.
     * <p>
     * This method assumes that the first three columns of the upper left 3x3 submatrix are no unit vectors.
     * 
     * @param mat
     *          the matrix whose rotational component is used to set this quaternion
     * @return this
     */
    public Quaterniond setFromUnnormalized(Matrix4dr mat) {
        setFromUnnormalized(mat.m00(), mat.m01(), mat.m02(), mat.m10(), mat.m11(), mat.m12(), mat.m20(), mat.m21(), mat.m22());
        return this;
    }

    /**
     * Set this quaternion to be a representation of the rotational component of the given matrix.
     * <p>
     * This method assumes that the first three columns of the upper left 3x3 submatrix are unit vectors.
     * 
     * @param mat
     *          the matrix whose rotational component is used to set this quaternion
     * @return this
     */
    public Quaterniond setFromNormalized(Matrix4dr mat) {
        setFromNormalized(mat.m00(), mat.m01(), mat.m02(), mat.m10(), mat.m11(), mat.m12(), mat.m20(), mat.m21(), mat.m22());
        return this;
    }

    /**
     * Set this quaternion to be a representation of the rotational component of the given matrix.
     * <p>
     * This method assumes that the first three columns of the upper left 3x3 submatrix are no unit vectors.
     * 
     * @param mat
     *          the matrix whose rotational component is used to set this quaternion
     * @return this
     */
    public Quaterniond setFromUnnormalized(Matrix3fr mat) {
        setFromUnnormalized(mat.m00(), mat.m01(), mat.m02(), mat.m10(), mat.m11(), mat.m12(), mat.m20(), mat.m21(), mat.m22());
        return this;
    }

    /**
     * Set this quaternion to be a representation of the rotational component of the given matrix.
     * <p>
     * This method assumes that the first three columns of the upper left 3x3 submatrix are unit vectors.
     * 
     * @param mat
     *          the matrix whose rotational component is used to set this quaternion
     * @return this
     */
    public Quaterniond setFromNormalized(Matrix3fr mat) {
        setFromNormalized(mat.m00(), mat.m01(), mat.m02(), mat.m10(), mat.m11(), mat.m12(), mat.m20(), mat.m21(), mat.m22());
        return this;
    }

    /**
     * Set this quaternion to be a representation of the rotational component of the given matrix.
     * <p>
     * This method assumes that the first three columns of the upper left 3x3 submatrix are no unit vectors.
     * 
     * @param mat
     *          the matrix whose rotational component is used to set this quaternion
     * @return this
     */
    public Quaterniond setFromUnnormalized(Matrix3dr mat) {
        setFromUnnormalized(mat.m00(), mat.m01(), mat.m02(), mat.m10(), mat.m11(), mat.m12(), mat.m20(), mat.m21(), mat.m22());
        return this;
    }

    /**
     * Set this quaternion to be a representation of the rotational component of the given matrix.
     * 
     * @param mat
     *          the matrix whose rotational component is used to set this quaternion
     * @return this
     */
    public Quaterniond setFromNormalized(Matrix3dr mat) {
        setFromNormalized(mat.m00(), mat.m01(), mat.m02(), mat.m10(), mat.m11(), mat.m12(), mat.m20(), mat.m21(), mat.m22());
        return this;
    }

    /**
     * Set this quaternion to be a representation of the supplied axis and
     * angle (in radians).
     * 
     * @param axis
     *          the rotation axis
     * @param angle
     *          the angle in radians
     * @return this
     */
    public Quaterniond fromAxisAngleRad(Vector3dr axis, double angle) {
        double hangle = angle / 2.0;
        double sinAngle = Math.sin(hangle);
        double vLength = axis.length();

        x = axis.x() / vLength * sinAngle;
        y = axis.y() / vLength * sinAngle;
        z = axis.z() / vLength * sinAngle;
        w = Math.cos(hangle);
        
        return this;
    }

    /**
     * Set this quaternion to be a representation of the supplied axis and
     * angle (in radians).
     * 
     * @param axisX
     *          the x component of the rotation axis
     * @param axisY
     *          the y component of the rotation axis
     * @param axisZ
     *          the z component of the rotation axis         
     * @param angle
     *          the angle in radians
     * @return this
     */
    public Quaterniond fromAxisAngleRad(double axisX, double axisY, double axisZ, double angle) {
        double hangle = angle / 2.0;
        double sinAngle = Math.sin(hangle);
        double vLength = Math.sqrt(axisX * axisX + axisY * axisY + axisZ * axisZ);

        x = axisX / vLength * sinAngle;
        y = axisY / vLength * sinAngle;
        z = axisZ / vLength * sinAngle;
        w = Math.cos(hangle);
        
        return this;
    }

    /**
     * Set this quaternion to be a representation of the supplied axis and
     * angle (in radians).
     * 
     * @param axis
     *          the rotation axis
     * @param angle
     *          the angle in radians
     * @return this
     */
    public Quaterniond fromAxisAngleDeg(Vector3dr axis, double angle) {
        double hangle = angle / 2.0;
        double sinAngle = Math.sin(hangle);
        double vLength = axis.length();

        x = axis.x() / vLength * sinAngle;
        y = axis.y() / vLength * sinAngle;
        z = axis.z() / vLength * sinAngle;
        w = Math.cos(hangle);
        
        return this;
    }

    /**
     * Multiply this quaternion by <code>q</code>.
     * <p>
     * If <tt>T</tt> is <code>this</code> and <tt>Q</tt> is the given
     * quaternion, then the resulting quaternion <tt>R</tt> is:
     * <p>
     * <tt>R = T * Q</tt>
     * <p>
     * So, this method uses post-multiplication like the matrix classes, resulting in a
     * vector to be transformed by <tt>Q</tt> first, and then by <tt>T</tt>.
     * 
     * @param q
     *          the quaternion to multiply <code>this</code> by
     * @return this
     */
    public Quaterniond mul(Quaterniondr q) {
        return mul(q, this);
    }

    /**
     * Multiply this quaternion by the quaternion represented via <tt>(qx, qy, qz, qw)</tt>.
     * <p>
     * If <tt>T</tt> is <code>this</code> and <tt>Q</tt> is the given
     * quaternion, then the resulting quaternion <tt>R</tt> is:
     * <p>
     * <tt>R = T * Q</tt>
     * <p>
     * So, this method uses post-multiplication like the matrix classes, resulting in a
     * vector to be transformed by <tt>Q</tt> first, and then by <tt>T</tt>.
     * 
     * @param qx
     *          the x component of the quaternion to multiply <code>this</code> by
     * @param qy
     *          the y component of the quaternion to multiply <code>this</code> by
     * @param qz
     *          the z component of the quaternion to multiply <code>this</code> by
     * @param qw
     *          the w component of the quaternion to multiply <code>this</code> by
     * @return this
     */
    public Quaterniond mul(double qx, double qy, double qz, double qw) {
        set(w * qx + x * qw + y * qz - z * qy,
            w * qy - x * qz + y * qw + z * qx,
            w * qz + x * qy - y * qx + z * qw,
            w * qw - x * qx - y * qy - z * qz);
        return this;
    }

    /**
     * Invert this quaternion and {@link #normalize() normalize} it.
     * 
     * @return this
     */
    public Quaterniond invert() {
        return invert(this);
    }

    /**
     * Invert this quaternion by assuming that it is already {@link #normalize() normalized}.
     * 
     * @return this
     */
    public Quaterniond unitInvert() {
        return unitInvert(this);
    }

    /**
     * Divide <code>this</code> quaternion by <code>b</code>.
     * <p>
     * The division expressed using the inverse is performed in the following way:
     * <p>
     * <tt>this = this * b^-1</tt>, where <tt>b^-1</tt> is the inverse of <code>b</code>.
     * 
     * @param b
     *          the {@link Quaterniondr} to divide this by
     * @return this
     */
    public Quaterniond div(Quaterniondr b) {
        return div(b, this);
    }

    /**
     * Conjugate this quaternion.
     * 
     * @return this
     */
    public Quaterniond conjugate() {
        x = -x;
        y = -y;
        z = -z;
        return this;
    }

    /**
     * Set this quaternion to the identity.
     * 
     * @return this
     */
    public Quaterniond identity() {
        x = 0.0;
        y = 0.0;
        z = 0.0;
        w = 1.0;
        return this;
    }


    /**
     * Set this quaternion from the supplied euler angles (in radians) with rotation order XYZ.
     * <p>
     * This method is equivalent to calling: <tt>rotationX(angleX).rotateY(angleY).rotateZ(angleZ)</tt>
     * <p>
     * Reference: <a href="http://gamedev.stackexchange.com/questions/13436/glm-euler-angles-to-quaternion#answer-13446">this stackexchange answer</a>
     * 
     * @param angleX
     *          the angle in radians to rotate about x
     * @param angleY
     *          the angle in radians to rotate about y
     * @param angleZ
     *          the angle in radians to rotate about z
     * @return this
     */
    public Quaterniond rotationXYZ(double angleX, double angleY, double angleZ) {
        double sx = Math.sin(angleX * 0.5);
        double cx = Math.cos(angleX * 0.5);
        double sy = Math.sin(angleY * 0.5);
        double cy = Math.cos(angleY * 0.5);
        double sz = Math.sin(angleZ * 0.5);
        double cz = Math.cos(angleZ * 0.5);

        double cycz = cy * cz;
        double sysz = sy * sz;
        double sycz = sy * cz;
        double cysz = cy * sz;
        w = cx*cycz - sx*sysz;
        x = sx*cycz + cx*sysz;
        y = cx*sycz - sx*cysz;
        z = cx*cysz + sx*sycz;

        return this;
    }

    /**
     * Set this quaternion from the supplied euler angles (in radians) with rotation order ZYX.
     * <p>
     * This method is equivalent to calling: <tt>rotationZ(angleZ).rotateY(angleY).rotateX(angleX)</tt>
     * <p>
     * Reference: <a href="http://gamedev.stackexchange.com/questions/13436/glm-euler-angles-to-quaternion#answer-13446">this stackexchange answer</a>
     * 
     * @param angleX
     *          the angle in radians to rotate about x
     * @param angleY
     *          the angle in radians to rotate about y
     * @param angleZ
     *          the angle in radians to rotate about z
     * @return this
     */
    public Quaterniond rotationZYX(double angleZ, double angleY, double angleX) {
        double sx = Math.sin(angleX * 0.5);
        double cx = Math.cos(angleX * 0.5);
        double sy = Math.sin(angleY * 0.5);
        double cy = Math.cos(angleY * 0.5);
        double sz = Math.sin(angleZ * 0.5);
        double cz = Math.cos(angleZ * 0.5);

        double cycz = cy * cz;
        double sysz = sy * sz;
        double sycz = sy * cz;
        double cysz = cy * sz;
        w = cx*cycz + sx*sysz;
        x = sx*cycz - cx*sysz;
        y = cx*sycz + sx*cysz;
        z = cx*cysz - sx*sycz;

        return this;
    }

    /**
     * Set this quaternion from the supplied euler angles (in radians) with rotation order YXZ.
     * <p>
     * This method is equivalent to calling: <tt>rotationY(angleY).rotateX(angleX).rotateZ(angleZ)</tt>
     * <p>
     * Reference: <a href="https://en.wikipedia.org/wiki/Conversion_between_quaternions_and_Euler_angles">https://en.wikipedia.org</a>
     * 
     * @param angleY
     *          the angle in radians to rotate about y
     * @param angleX
     *          the angle in radians to rotate about x
     * @param angleZ
     *          the angle in radians to rotate about z
     * @return this
     */
    public Quaterniond rotationYXZ(double angleY, double angleX, double angleZ) {
        double sx = Math.sin(angleX * 0.5);
        double cx = Math.cos(angleX * 0.5);
        double sy = Math.sin(angleY * 0.5);
        double cy = Math.cos(angleY * 0.5);
        double sz = Math.sin(angleZ * 0.5);
        double cz = Math.cos(angleZ * 0.5);

        double x = cy * sx;
        double y = sy * cx;
        double z = sy * sx;
        double w = cy * cx;
        this.x = x * cz + y * sz;
        this.y = y * cz - x * sz;
        this.z = w * sz - z * cz;
        this.w = w * cz + z * sz;

        return this;
    }

    /**
     * Interpolate between <code>this</code> quaternion and the specified
     * <code>target</code> using sperical linear interpolation using the specified interpolation factor <code>alpha</code>.
     * <p>
     * This method resorts to non-spherical linear interpolation when the absolute dot product between <code>this</code> and <code>target</code> is
     * below <tt>1E-6</tt>.
     * 
     * @param target
     *          the target of the interpolation, which should be reached with <tt>alpha = 1.0</tt>
     * @param alpha
     *          the interpolation factor, within <tt>[0..1]</tt>
     * @return this
     */
    public Quaterniond slerp(Quaterniondr target, double alpha) {
        return slerp(target, alpha, this);
    }

    /**
     * Compute a linear (non-spherical) interpolation of <code>this</code> and the given quaternion <code>q</code>
     * and store the result in <code>this</code>.
     * 
     * @param q
     *          the other quaternion
     * @param factor
     *          the interpolation factor. It is between 0.0 and 1.0
     * @return this
     */
    public Quaterniond nlerp(Quaterniondr q, double factor) {
        return nlerp(q, factor, this);
    }

    /**
     * Apply a rotation to this quaternion that maps the given direction to the positive Z axis.
     * <p>
     * Because there are multiple possibilities for such a rotation, this method will choose the one that ensures the given up direction to remain
     * parallel to the plane spanned by the <code>up</code> and <code>dir</code> vectors. 
     * <p>
     * If <code>Q</code> is <code>this</code> quaternion and <code>R</code> the quaternion representing the 
     * specified rotation, then the new quaternion will be <code>Q * R</code>. So when transforming a
     * vector <code>v</code> with the new quaternion by using <code>Q * R * v</code>, the
     * rotation added by this method will be applied first!
     * <p>
     * Reference: <a href="http://answers.unity3d.com/questions/467614/what-is-the-source-code-of-quaternionlookrotation.html">http://answers.unity3d.com</a>
     * 
     * @see #lookRotate(double, double, double, double, double, double, Quaterniond)
     * 
     * @param dir
     *              the direction to map to the positive Z axis
     * @param up
     *              the vector which will be mapped to a vector parallel to the plane
     *              spanned by the given <code>dir</code> and <code>up</code>
     * @return this
     */
    public Quaterniond lookRotate(Vector3dr dir, Vector3dr up) {
        return lookRotate(dir.x(), dir.y(), dir.z(), up.x(), up.y(), up.z(), this);
    }

    /**
     * Apply a rotation to this quaternion that maps the given direction to the positive Z axis.
     * <p>
     * Because there are multiple possibilities for such a rotation, this method will choose the one that ensures the given up direction to remain
     * parallel to the plane spanned by the <tt>up</tt> and <tt>dir</tt> vectors. 
     * <p>
     * If <code>Q</code> is <code>this</code> quaternion and <code>R</code> the quaternion representing the 
     * specified rotation, then the new quaternion will be <code>Q * R</code>. So when transforming a
     * vector <code>v</code> with the new quaternion by using <code>Q * R * v</code>, the
     * rotation added by this method will be applied first!
     * <p>
     * Reference: <a href="http://answers.unity3d.com/questions/467614/what-is-the-source-code-of-quaternionlookrotation.html">http://answers.unity3d.com</a>
     * 
     * @see #lookRotate(double, double, double, double, double, double, Quaterniond)
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
    public Quaterniond lookRotate(double dirX, double dirY, double dirZ, double upX, double upY, double upZ) {
        return lookRotate(dirX, dirY, dirZ, upX, upY, upZ, this);
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeDouble(x);
        out.writeDouble(y);
        out.writeDouble(z);
        out.writeDouble(w);
    }

    public void readExternal(ObjectInput in) throws IOException,
            ClassNotFoundException {
        x = in.readDouble();
        y = in.readDouble();
        z = in.readDouble();
        w = in.readDouble();
    }

    /**
     * Compute the difference between <code>this</code> and the <code>other</code> quaternion
     * and store the result in <code>this</code>.
     * <p>
     * The difference is the rotation that has to be applied to get from
     * <code>this</code> rotation to <code>other</code>. If <tt>T</tt> is <code>this</code>, <tt>Q</tt>
     * is <code>other</code> and <tt>D</tt> is the computed difference, then the following equation holds:
     * <p>
     * <tt>T * D = Q</tt>
     * <p>
     * It is defined as: <tt>D = T^-1 * Q</tt>, where <tt>T^-1</tt> denotes the {@link #invert() inverse} of <tt>T</tt>.
     * 
     * @param other
     *          the other quaternion
     * @return this
     */
    public Quaterniond difference(Quaterniondr other) {
        return difference(other, this);
    }


    /**
     * Set <code>this</code> quaternion to a rotation that rotates the <tt>fromDir</tt> vector to point along <tt>toDir</tt>.
     * <p>
     * Since there can be multiple possible rotations, this method chooses the one with the shortest arc.
     * 
     * @param fromDirX
     *              the x-coordinate of the direction to rotate into the destination direction
     * @param fromDirY
     *              the y-coordinate of the direction to rotate into the destination direction
     * @param fromDirZ
     *              the z-coordinate of the direction to rotate into the destination direction
     * @param toDirX
     *              the x-coordinate of the direction to rotate to
     * @param toDirY
     *              the y-coordinate of the direction to rotate to
     * @param toDirZ
     *              the z-coordinate of the direction to rotate to
     * @return this
     */
    public Quaterniond rotationTo(double fromDirX, double fromDirY, double fromDirZ, double toDirX, double toDirY, double toDirZ) {
        double invFromLength = 1.0 / Math.sqrt(fromDirX * fromDirX + fromDirY * fromDirY + fromDirZ * fromDirZ);
        double fromX = fromDirX * invFromLength;
        double fromY = fromDirY * invFromLength;
        double fromZ = fromDirZ * invFromLength;
        double invToLength = 1.0 / Math.sqrt(toDirX * toDirX + toDirY * toDirY + toDirZ * toDirZ);
        double toX = toDirX * invToLength;
        double toY = toDirY * invToLength;
        double toZ = toDirZ * invToLength;
        double dot = fromX * toX + fromY * toY + fromZ * toZ;
        if (dot < 1e-6 - 1.0) {
            /* vectors are negation of each other */
            double axisX = 0.0;
            double axisY = -fromZ;
            double axisZ = fromY;
            if (axisX * axisX + axisY * axisY + axisZ * axisZ < 1E-6) {
                axisX = fromZ;
                axisY = 0.0;
                axisZ = -fromX;
            }
            double angleR = Math.PI;
            double s = Math.sin(angleR / 2.0);
            x = axisX * s;
            y = axisY * s;
            z = axisZ * s;
            w = Math.cos(angleR / 2.0);
        } else if (dot < 1.0) {
            double s = Math.sqrt((1.0 + dot) * 2.0);
            double invs = 1.0 / s;
            double crossX = fromY * toZ - fromZ * toY;
            double crossY = fromZ * toX - fromX * toZ;
            double crossZ = fromX * toY - fromY * toX;
            x = crossX * invs;
            y = crossY * invs;
            z = crossZ * invs;
            w = s * 0.5;
            double invNorm = 1.0 / Math.sqrt(x * x + y * y + z * z + w * w);
            x *= invNorm;
            y *= invNorm;
            z *= invNorm;
            w *= invNorm;
        } else {
            /* vectors are parallel, don't change anything */
            return this;
        }
        return this;
    }

    /**
     * Set <code>this</code> quaternion to a rotation that rotates the <code>fromDir</code> vector to point along <code>toDir</code>.
     * <p>
     * Because there can be multiple possible rotations, this method chooses the one with the shortest arc.
     * 
     * @see #rotationTo(double, double, double, double, double, double)
     * 
     * @param fromDir
     *          the starting direction
     * @param toDir
     *          the destination direction
     * @return this
     */
    public Quaterniond rotationTo(Vector3dr fromDir, Vector3dr toDir) {
        return rotationTo(fromDir.x(), fromDir.y(), fromDir.z(), toDir.x(), toDir.y(), toDir.z());
    }

    /**
     * Set this {@link Quaterniond} to a rotation of the given angle in radians about the supplied
     * axis, all of which are specified via the {@link AxisAngle4f}.
     * 
     * @see #rotationAxis(double, double, double, double)
     * 
     * @param axisAngle
     *            the {@link AxisAngle4f} giving the rotation angle in radians and the axis to rotate about
     * @return this
     */
    public Quaterniond rotationAxis(AxisAngle4fr axisAngle) {
        return rotationAxis(axisAngle.angle(), axisAngle.x(), axisAngle.y(), axisAngle.z());
    }

    /**
     * Set this quaternion to a rotation of the given angle in radians about the supplied axis.
     * 
     * @param angle
     *          the rotation angle in radians
     * @param axisX
     *          the x-coordinate of the rotation axis
     * @param axisY
     *          the y-coordinate of the rotation axis
     * @param axisZ
     *          the z-coordinate of the rotation axis
     * @return this
     */
    public Quaterniond rotationAxis(double angle, double axisX, double axisY, double axisZ) {
        double hangle = angle / 2.0;
        double sinAngle = Math.sin(hangle);
        double invVLength = 1.0 / Math.sqrt(axisX * axisX + axisY * axisY + axisZ * axisZ);

        x = axisX * invVLength * sinAngle;
        y = axisY * invVLength * sinAngle;
        z = axisZ * invVLength * sinAngle;
        w = (float) Math.cos(hangle);

        return this;
    }

    /**
     * Set this quaternion to represent a rotation of the given angles in radians about the basis unit axes of the cartesian space.
     * 
     * @param angleX
     *              the angle in radians to rotate about the x axis
     * @param angleY
     *              the angle in radians to rotate about the y axis
     * @param angleZ
     *              the angle in radians to rotate about the z axis
     * @return this
     */
    public Quaterniond rotation(double angleX, double angleY, double angleZ) {
        double thetaX = angleX * 0.5;
        double thetaY = angleY * 0.5;
        double thetaZ = angleZ * 0.5;
        double thetaMagSq = thetaX * thetaX + thetaY * thetaY + thetaZ * thetaZ;
        double s;
        if (thetaMagSq * thetaMagSq / 24.0f < 1E-8f) {
            w = 1.0 - thetaMagSq / 2.0;
            s = 1.0 - thetaMagSq / 6.0;
        } else {
            double thetaMag = Math.sqrt(thetaMagSq);
            w = Math.cos(thetaMag);
            s = Math.sin(thetaMag) / thetaMag;
        }
        x = thetaX * s;
        y = thetaY * s;
        z = thetaZ * s;
        return this;
    }

    /**
     * Set this quaternion to represent a rotation of the given radians about the x axis.
     * 
     * @param angle
     *              the angle in radians to rotate about the x axis
     * @return this
     */
    public Quaterniond rotationX(double angle) {
        double cos = Math.cos(angle * 0.5);
        double sin = Math.sin(angle * 0.5);
        w = cos;
        x = sin;
        y = 0.0;
        z = 0.0;
        return this;
    }

    /**
     * Set this quaternion to represent a rotation of the given radians about the y axis.
     * 
     * @param angle
     *              the angle in radians to rotate about the y axis
     * @return this
     */
    public Quaterniond rotationY(double angle) {
        double cos = Math.cos(angle * 0.5);
        double sin = Math.sin(angle * 0.5);
        w = cos;
        x = 0.0;
        y = sin;
        z = 0.0;
        return this;
    }

    /**
     * Set this quaternion to represent a rotation of the given radians about the z axis.
     * 
     * @param angle
     *              the angle in radians to rotate about the z axis
     * @return this
     */
    public Quaterniond rotationZ(double angle) {
        double cos = Math.cos(angle * 0.5);
        double sin = Math.sin(angle * 0.5);
        w = cos;
        x = 0.0;
        y = 0.0;
        z = sin;
        return this;
    }

    /**
     * Apply a rotation to <code>this</code> that rotates the <tt>fromDir</tt> vector to point along <tt>toDir</tt>.
     * <p>
     * Since there can be multiple possible rotations, this method chooses the one with the shortest arc.
     * <p>
     * If <code>Q</code> is <code>this</code> quaternion and <code>R</code> the quaternion representing the 
     * specified rotation, then the new quaternion will be <code>Q * R</code>. So when transforming a
     * vector <code>v</code> with the new quaternion by using <code>Q * R * v</code>, the
     * rotation added by this method will be applied first!
     * 
     * @see #rotateTo(double, double, double, double, double, double, Quaterniond)
     * 
     * @param fromDirX
     *              the x-coordinate of the direction to rotate into the destination direction
     * @param fromDirY
     *              the y-coordinate of the direction to rotate into the destination direction
     * @param fromDirZ
     *              the z-coordinate of the direction to rotate into the destination direction
     * @param toDirX
     *              the x-coordinate of the direction to rotate to
     * @param toDirY
     *              the y-coordinate of the direction to rotate to
     * @param toDirZ
     *              the z-coordinate of the direction to rotate to
     * @return this
     */
    public Quaterniond rotateTo(double fromDirX, double fromDirY, double fromDirZ, double toDirX, double toDirY, double toDirZ) {
        return rotateTo(fromDirX, fromDirY, fromDirZ, toDirX, toDirY, toDirZ, this);
    }

    /**
     * Apply a rotation to <code>this</code> that rotates the <code>fromDir</code> vector to point along <code>toDir</code>.
     * <p>
     * Because there can be multiple possible rotations, this method chooses the one with the shortest arc.
     * <p>
     * If <code>Q</code> is <code>this</code> quaternion and <code>R</code> the quaternion representing the 
     * specified rotation, then the new quaternion will be <code>Q * R</code>. So when transforming a
     * vector <code>v</code> with the new quaternion by using <code>Q * R * v</code>, the
     * rotation added by this method will be applied first!
     * 
     * @see #rotateTo(double, double, double, double, double, double, Quaterniond)
     * 
     * @param fromDir
     *          the starting direction
     * @param toDir
     *          the destination direction
     * @return this
     */
    public Quaterniond rotateTo(Vector3dr fromDir, Vector3dr toDir) {
        return rotateTo(fromDir.x(), fromDir.y(), fromDir.z(), toDir.x(), toDir.y(), toDir.z(), this);
    }

    /**
     * Apply a rotation to <code>this</code> quaternion rotating the given radians about the basis unit axes
     * of the cartesian space.
     * <p>
     * If <code>Q</code> is <code>this</code> quaternion and <code>R</code> the quaternion representing the 
     * specified rotation, then the new quaternion will be <code>Q * R</code>. So when transforming a
     * vector <code>v</code> with the new quaternion by using <code>Q * R * v</code>, the
     * rotation added by this method will be applied first!
     * 
     * @see #rotate(double, double, double, Quaterniond)
     * 
     * @param anglesXYZ
     *              the angles in radians to rotate about the x, y and z axes, respectively
     * @return this
     */
    public Quaterniond rotate(Vector3dr anglesXYZ) {
        return rotate(anglesXYZ.x(), anglesXYZ.y(), anglesXYZ.z(), this);
    }

    /**
     * Apply a rotation to <code>this</code> quaternion rotating the given radians about the basis unit axes of the cartesian space.
     * <p>
     * If <code>Q</code> is <code>this</code> quaternion and <code>R</code> the quaternion representing the 
     * specified rotation, then the new quaternion will be <code>Q * R</code>. So when transforming a
     * vector <code>v</code> with the new quaternion by using <code>Q * R * v</code>, the
     * rotation added by this method will be applied first!
     * 
     * @see #rotate(double, double, double, Quaterniond)
     * 
     * @param angleX
     *              the angle in radians to rotate about the x axis
     * @param angleY
     *              the angle in radians to rotate about the y axis
     * @param angleZ
     *              the angle in radians to rotate about the z axis
     * @return this
     */
    public Quaterniond rotate(double angleX, double angleY, double angleZ) {
        return rotate(angleX, angleY, angleZ, this);
    }

    /**
     * Apply a rotation to <code>this</code> quaternion rotating the given radians about the x axis.
     * <p>
     * If <code>Q</code> is <code>this</code> quaternion and <code>R</code> the quaternion representing the 
     * specified rotation, then the new quaternion will be <code>Q * R</code>. So when transforming a
     * vector <code>v</code> with the new quaternion by using <code>Q * R * v</code>, the
     * rotation added by this method will be applied first!
     * 
     * @see #rotate(double, double, double, Quaterniond)
     * 
     * @param angle
     *              the angle in radians to rotate about the x axis
     * @return this
     */
    public Quaterniond rotateX(double angle) {
        return rotateX(angle, this);
    }

    /**
     * Apply a rotation to <code>this</code> quaternion rotating the given radians about the y axis.
     * <p>
     * If <code>Q</code> is <code>this</code> quaternion and <code>R</code> the quaternion representing the 
     * specified rotation, then the new quaternion will be <code>Q * R</code>. So when transforming a
     * vector <code>v</code> with the new quaternion by using <code>Q * R * v</code>, the
     * rotation added by this method will be applied first!
     * 
     * @see #rotate(double, double, double, Quaterniond)
     * 
     * @param angle
     *              the angle in radians to rotate about the y axis
     * @return this
     */
    public Quaterniond rotateY(double angle) {
        return rotateY(angle, this);
    }

    /**
     * Apply a rotation to <code>this</code> quaternion rotating the given radians about the z axis.
     * <p>
     * If <code>Q</code> is <code>this</code> quaternion and <code>R</code> the quaternion representing the 
     * specified rotation, then the new quaternion will be <code>Q * R</code>. So when transforming a
     * vector <code>v</code> with the new quaternion by using <code>Q * R * v</code>, the
     * rotation added by this method will be applied first!
     * 
     * @see #rotate(double, double, double, Quaterniond)
     * 
     * @param angle
     *              the angle in radians to rotate about the z axis
     * @return this
     */
    public Quaterniond rotateZ(double angle) {
        return rotateZ(angle, this);
    }

    /**
     * Apply a rotation to <code>this</code> quaternion rotating the given radians about the cartesian base unit axes,
     * called the euler angles using rotation sequence <tt>XYZ</tt>.
     * <p>
     * This method is equivalent to calling: <tt>rotateX(angleX).rotateY(angleY).rotateZ(angleZ)</tt>
     * <p>
     * If <code>Q</code> is <code>this</code> quaternion and <code>R</code> the quaternion representing the 
     * specified rotation, then the new quaternion will be <code>Q * R</code>. So when transforming a
     * vector <code>v</code> with the new quaternion by using <code>Q * R * v</code>, the
     * rotation added by this method will be applied first!
     * 
     * @param angleX
     *              the angle in radians to rotate about the x axis
     * @param angleY
     *              the angle in radians to rotate about the y axis
     * @param angleZ
     *              the angle in radians to rotate about the z axis
     * @return this
     */
    public Quaterniond rotateXYZ(double angleX, double angleY, double angleZ) {
        return rotateXYZ(angleX, angleY, angleZ, this);
    }

    /**
     * Apply a rotation to <code>this</code> quaternion rotating the given radians about the cartesian base unit axes,
     * called the euler angles, using the rotation sequence <tt>ZYX</tt>.
     * <p>
     * This method is equivalent to calling: <tt>rotateZ(angleZ).rotateY(angleY).rotateX(angleX)</tt>
     * <p>
     * If <code>Q</code> is <code>this</code> quaternion and <code>R</code> the quaternion representing the 
     * specified rotation, then the new quaternion will be <code>Q * R</code>. So when transforming a
     * vector <code>v</code> with the new quaternion by using <code>Q * R * v</code>, the
     * rotation added by this method will be applied first!
     * 
     * @param angleZ
     *              the angle in radians to rotate about the z axis
     * @param angleY
     *              the angle in radians to rotate about the y axis
     * @param angleX
     *              the angle in radians to rotate about the x axis
     * @return this
     */
    public Quaterniond rotateZYX(double angleZ, double angleY, double angleX) {
        return rotateZYX(angleZ, angleY, angleX, this);
    }

    /**
     * Apply a rotation to <code>this</code> quaternion rotating the given radians about the cartesian base unit axes,
     * called the euler angles, using the rotation sequence <tt>YXZ</tt>.
     * <p>
     * This method is equivalent to calling: <tt>rotateY(angleY).rotateX(angleX).rotateZ(angleZ)</tt>
     * <p>
     * If <code>Q</code> is <code>this</code> quaternion and <code>R</code> the quaternion representing the 
     * specified rotation, then the new quaternion will be <code>Q * R</code>. So when transforming a
     * vector <code>v</code> with the new quaternion by using <code>Q * R * v</code>, the
     * rotation added by this method will be applied first!
     * 
     * @param angleY
     *              the angle in radians to rotate about the y axis
     * @param angleX
     *              the angle in radians to rotate about the x axis
     * @param angleZ
     *              the angle in radians to rotate about the z axis
     * @return this
     */
    public Quaterniond rotateYXZ(double angleZ, double angleY, double angleX) {
        return rotateYXZ(angleZ, angleY, angleX, this);
    }

    /**
     * Apply a rotation to <code>this</code> quaternion rotating the given radians about the specified axis.
     * <p>
     * If <code>Q</code> is <code>this</code> quaternion and <code>R</code> the quaternion representing the 
     * specified rotation, then the new quaternion will be <code>Q * R</code>. So when transforming a
     * vector <code>v</code> with the new quaternion by using <code>Q * R * v</code>, the
     * rotation added by this method will be applied first!
     * 
     * @see #rotateAxis(double, double, double, double, Quaterniond)
     * 
     * @param angle
     *              the angle in radians to rotate about the specified axis
     * @param axis
     *              the rotation axis
     * @return this
     */
    public Quaterniond rotateAxis(double angle, Vector3dr axis) {
        return rotateAxis(angle, axis.x(), axis.y(), axis.z(), this);
    }

    /**
     * Apply a rotation to <code>this</code> quaternion rotating the given radians about the specified axis.
     * <p>
     * If <code>Q</code> is <code>this</code> quaternion and <code>R</code> the quaternion representing the 
     * specified rotation, then the new quaternion will be <code>Q * R</code>. So when transforming a
     * vector <code>v</code> with the new quaternion by using <code>Q * R * v</code>, the
     * rotation added by this method will be applied first!
     * 
     * @see #rotateAxis(double, double, double, double, Quaterniond)
     * 
     * @param angle
     *              the angle in radians to rotate about the specified axis
     * @param axisX
     *              the x coordinate of the rotation axis
     * @param axisY
     *              the y coordinate of the rotation axis
     * @param axisZ
     *              the z coordinate of the rotation axis
     * @return this
     */
    public Quaterniond rotateAxis(double angle, double axisX, double axisY, double axisZ) {
        return rotateAxis(angle, axisX, axisY, axisZ, this);
    }

}
