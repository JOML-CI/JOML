/*
 * (C) Copyright 2015-2018 Richard Greenlees

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
import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.joml.internal.Options;
import org.joml.internal.Runtime;

/**
 * Quaternion of 4 double-precision floats which can represent rotation and uniform scaling.
 *
 * @author Richard Greenlees
 * @author Kai Burjack
 */
public class Quaterniond implements Externalizable, Quaterniondc {

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
     * Create a new {@link Quaterniond} and initialize it with <code>(x=0, y=0, z=0, w=1)</code>, 
     * where <code>(x, y, z)</code> is the vector part of the quaternion and <code>w</code> is the real/scalar part.
     */
    public Quaterniond() {
        this.w = 1.0;
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
     * Create a new {@link Quaterniond} and initialize its components to the same values as the given {@link Quaterniond}.
     * 
     * @param source
     *          the {@link Quaterniond} to take the component values from
     */
    public Quaterniond(Quaterniondc source) {
        x = source.x();
        y = source.y();
        z = source.z();
        w = source.w();
    }

    /**
     * Create a new {@link Quaterniond} and initialize its components to the same values as the given {@link Quaternionfc}.
     * 
     * @param source
     *          the {@link Quaternionfc} to take the component values from
     */
    public Quaterniond(Quaternionfc source) {
        x = source.x();
        y = source.y();
        z = source.z();
        w = source.w();
    }

    /**
     * Create a new {@link Quaterniond} and initialize it to represent the same rotation as the given {@link AxisAngle4f}.
     * 
     * @param axisAngle
     *          the axis-angle to initialize this quaternion with
     */
    public Quaterniond(AxisAngle4f axisAngle) {
        double s = Math.sin(axisAngle.angle * 0.5);
        x = axisAngle.x * s;
        y = axisAngle.y * s;
        z = axisAngle.z * s;
        w = Math.cosFromSin(s, axisAngle.angle * 0.5);
    }

    /**
     * Create a new {@link Quaterniond} and initialize it to represent the same rotation as the given {@link AxisAngle4d}.
     * 
     * @param axisAngle
     *          the axis-angle to initialize this quaternion with
     */
    public Quaterniond(AxisAngle4d axisAngle) {
        double s = Math.sin(axisAngle.angle * 0.5);
        x = axisAngle.x * s;
        y = axisAngle.y * s;
        z = axisAngle.z * s;
        w = Math.cosFromSin(s, axisAngle.angle * 0.5);
    }

    /**
     * @return the first component of the vector part
     */
    public double x() {
        return this.x;
    }

    /**
     * @return the second component of the vector part
     */
    public double y() {
        return this.y;
    }

    /**
     * @return the third component of the vector part
     */
    public double z() {
        return this.z;
    }

    /**
     * @return the real/scalar part of the quaternion
     */
    public double w() {
        return this.w;
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

    /* (non-Javadoc)
     * @see org.joml.Quaterniondc#normalize(org.joml.Quaterniond)
     */
    public Quaterniond normalize(Quaterniond dest) {
        double invNorm = 1.0 / Math.sqrt(x * x + y * y + z * z + w * w);
        dest.x = x * invNorm;
        dest.y = y * invNorm;
        dest.z = z * invNorm;
        dest.w = w * invNorm;
        return dest;
    }

    /**
     * Add the quaternion <code>(x, y, z, w)</code> to this quaternion.
     * 
     * @param x
     *          the x component of the vector part
     * @param y
     *          the y component of the vector part
     * @param z
     *          the z component of the vector part
     * @param w
     *          the real/scalar component
     * @return this
     */
    public Quaterniond add(double x, double y, double z, double w) {
        return add(x, y, z, w, this);
    }

    /* (non-Javadoc)
     * @see org.joml.Quaterniondc#add(double, double, double, double, org.joml.Quaterniond)
     */
    public Quaterniond add(double x, double y, double z, double w, Quaterniond dest) {
        dest.x = this.x + x;
        dest.y = this.y + y;
        dest.z = this.z + z;
        dest.w = this.w + w;
        return dest;
    }

    /**
     * Add <code>q2</code> to this quaternion.
     * 
     * @param q2
     *          the quaternion to add to this
     * @return this
     */
    public Quaterniond add(Quaterniondc q2) {
        x += q2.x();
        y += q2.y();
        z += q2.z();
        w += q2.w();
        return this;
    }

    /* (non-Javadoc)
     * @see org.joml.Quaterniondc#add(org.joml.Quaterniondc, org.joml.Quaterniond)
     */
    public Quaterniond add(Quaterniondc q2, Quaterniond dest) {
        dest.x = x + q2.x();
        dest.y = y + q2.y();
        dest.z = z + q2.z();
        dest.w = w + q2.w();
        return dest;
    }

    /* (non-Javadoc)
     * @see org.joml.Quaterniondc#dot(org.joml.Quaterniondc)
     */
    public double dot(Quaterniondc otherQuat) {
        return this.x * otherQuat.x() + this.y * otherQuat.y() + this.z * otherQuat.z() + this.w * otherQuat.w();
    }

    /* (non-Javadoc)
     * @see org.joml.Quaterniondc#angle()
     */
    public double angle() {
        double angle = 2.0 * Math.acos(w);
        return angle <= Math.PI ? angle : Math.PI + Math.PI - angle;
    }

    /* (non-Javadoc)
     * @see org.joml.Quaterniondc#get(org.joml.Matrix3d)
     */
    public Matrix3d get(Matrix3d dest) {
        return dest.set(this);
    }

    /* (non-Javadoc)
     * @see org.joml.Quaterniondc#get(org.joml.Matrix3f)
     */
    public Matrix3f get(Matrix3f dest) {
        return dest.set(this);
    }

    /* (non-Javadoc)
     * @see org.joml.Quaterniondc#get(org.joml.Matrix4d)
     */
    public Matrix4d get(Matrix4d dest) {
        return dest.set(this);
    }

    /* (non-Javadoc)
     * @see org.joml.Quaterniondc#get(org.joml.Matrix4f)
     */
    public Matrix4f get(Matrix4f dest) {
        return dest.set(this);
    }

    /**
     * Set the given {@link Quaterniond} to the values of <code>this</code>.
     * 
     * @see #set(Quaterniondc)
     * 
     * @param dest
     *          the {@link Quaterniond} to set
     * @return the passed in destination
     */
    public Quaterniond get(Quaterniond dest) {
        return dest.set(this);
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
     * Set this quaternion to be a copy of q.
     * 
     * @param q
     *          the {@link Quaterniondc} to copy
     * @return this
     */
    public Quaterniond set(Quaterniondc q) {
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
     *          the {@link Quaternionfc} to copy
     * @return this
     */
    public Quaterniond set(Quaternionfc q) {
        x = q.x();
        y = q.y();
        z = q.z();
        w = q.w();
        return this;
    }

    /**
     * Set this {@link Quaterniond} to be equivalent to the given
     * {@link AxisAngle4f}.
     * 
     * @param axisAngle
     *            the {@link AxisAngle4f}
     * @return this
     */
    public Quaterniond set(AxisAngle4f axisAngle) {
        return setAngleAxis(axisAngle.angle, axisAngle.x, axisAngle.y, axisAngle.z);
    }

    /**
     * Set this {@link Quaterniond} to be equivalent to the given
     * {@link AxisAngle4d}.
     * 
     * @param axisAngle
     *            the {@link AxisAngle4d}
     * @return this
     */
    public Quaterniond set(AxisAngle4d axisAngle) {
        return setAngleAxis(axisAngle.angle, axisAngle.x, axisAngle.y, axisAngle.z);
    }

    /**
     * Set this quaternion to a rotation equivalent to the supplied axis and
     * angle (in radians).
     * <p>
     * This method assumes that the given rotation axis <code>(x, y, z)</code> is already normalized
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
        this.w = Math.cosFromSin(s, angle * 0.5);
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
    public Quaterniond setAngleAxis(double angle, Vector3dc axis) {
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
    public Quaterniond setFromUnnormalized(Matrix4fc mat) {
        setFromUnnormalized(mat.m00(), mat.m01(), mat.m02(), mat.m10(), mat.m11(), mat.m12(), mat.m20(), mat.m21(), mat.m22());
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
    public Quaterniond setFromUnnormalized(Matrix4x3fc mat) {
        setFromUnnormalized(mat.m00(), mat.m01(), mat.m02(), mat.m10(), mat.m11(), mat.m12(), mat.m20(), mat.m21(), mat.m22());
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
    public Quaterniond setFromUnnormalized(Matrix4x3dc mat) {
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
    public Quaterniond setFromNormalized(Matrix4fc mat) {
        setFromNormalized(mat.m00(), mat.m01(), mat.m02(), mat.m10(), mat.m11(), mat.m12(), mat.m20(), mat.m21(), mat.m22());
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
    public Quaterniond setFromNormalized(Matrix4x3fc mat) {
        setFromNormalized(mat.m00(), mat.m01(), mat.m02(), mat.m10(), mat.m11(), mat.m12(), mat.m20(), mat.m21(), mat.m22());
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
    public Quaterniond setFromNormalized(Matrix4x3dc mat) {
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
    public Quaterniond setFromUnnormalized(Matrix4dc mat) {
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
    public Quaterniond setFromNormalized(Matrix4dc mat) {
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
    public Quaterniond setFromUnnormalized(Matrix3fc mat) {
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
    public Quaterniond setFromNormalized(Matrix3fc mat) {
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
    public Quaterniond setFromUnnormalized(Matrix3dc mat) {
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
    public Quaterniond setFromNormalized(Matrix3dc mat) {
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
    public Quaterniond fromAxisAngleRad(Vector3dc axis, double angle) {
        return fromAxisAngleRad(axis.x(), axis.y(), axis.z(), angle);
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
        w = Math.cosFromSin(sinAngle, hangle);
        return this;
    }

    /**
     * Set this quaternion to be a representation of the supplied axis and
     * angle (in degrees).
     * 
     * @param axis
     *          the rotation axis
     * @param angle
     *          the angle in degrees
     * @return this
     */
    public Quaterniond fromAxisAngleDeg(Vector3dc axis, double angle) {
        return fromAxisAngleRad(axis.x(), axis.y(), axis.z(), Math.toRadians(angle));
    }

    /**
     * Set this quaternion to be a representation of the supplied axis and
     * angle (in degrees).
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
    public Quaterniond fromAxisAngleDeg(double axisX, double axisY, double axisZ, double angle) {
        return fromAxisAngleRad(axisX, axisY, axisZ, Math.toRadians(angle));
    }

    /**
     * Multiply this quaternion by <code>q</code>.
     * <p>
     * If <code>T</code> is <code>this</code> and <code>Q</code> is the given
     * quaternion, then the resulting quaternion <code>R</code> is:
     * <p>
     * <code>R = T * Q</code>
     * <p>
     * So, this method uses post-multiplication like the matrix classes, resulting in a
     * vector to be transformed by <code>Q</code> first, and then by <code>T</code>.
     * 
     * @param q
     *          the quaternion to multiply <code>this</code> by
     * @return this
     */
    public Quaterniond mul(Quaterniondc q) {
        return mul(q, this);
    }

    /* (non-Javadoc)
     * @see org.joml.Quaterniondc#mul(org.joml.Quaterniondc, org.joml.Quaterniond)
     */
    public Quaterniond mul(Quaterniondc q, Quaterniond dest) {
        dest.set(w * q.x() + x * q.w() + y * q.z() - z * q.y(),
                 w * q.y() - x * q.z() + y * q.w() + z * q.x(),
                 w * q.z() + x * q.y() - y * q.x() + z * q.w(),
                 w * q.w() - x * q.x() - y * q.y() - z * q.z());
        return dest;
    }

    /**
     * Multiply this quaternion by the quaternion represented via <code>(qx, qy, qz, qw)</code>.
     * <p>
     * If <code>T</code> is <code>this</code> and <code>Q</code> is the given
     * quaternion, then the resulting quaternion <code>R</code> is:
     * <p>
     * <code>R = T * Q</code>
     * <p>
     * So, this method uses post-multiplication like the matrix classes, resulting in a
     * vector to be transformed by <code>Q</code> first, and then by <code>T</code>.
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

    /* (non-Javadoc)
     * @see org.joml.Quaterniondc#mul(double, double, double, double, org.joml.Quaterniond)
     */
    public Quaterniond mul(double qx, double qy, double qz, double qw, Quaterniond dest) {
        dest.set(w * qx + x * qw + y * qz - z * qy,
                 w * qy - x * qz + y * qw + z * qx,
                 w * qz + x * qy - y * qx + z * qw,
                 w * qw - x * qx - y * qy - z * qz);
        return dest;
    }

    /**
     * Pre-multiply this quaternion by <code>q</code>.
     * <p>
     * If <code>T</code> is <code>this</code> and <code>Q</code> is the given quaternion, then the resulting quaternion <code>R</code> is:
     * <p>
     * <code>R = Q * T</code>
     * <p>
     * So, this method uses pre-multiplication, resulting in a vector to be transformed by <code>T</code> first, and then by <code>Q</code>.
     * 
     * @param q
     *            the quaternion to pre-multiply <code>this</code> by
     * @return this
     */
    public Quaterniond premul(Quaterniondc q) {
        return premul(q, this);
    }

    /* (non-Javadoc)
     * @see org.joml.Quaterniondc#premul(org.joml.Quaterniondc, org.joml.Quaterniond)
     */
    public Quaterniond premul(Quaterniondc q, Quaterniond dest) {
        dest.set(q.w() * x + q.x() * w + q.y() * z - q.z() * y,
                 q.w() * y - q.x() * z + q.y() * w + q.z() * x,
                 q.w() * z + q.x() * y - q.y() * x + q.z() * w,
                 q.w() * w - q.x() * x - q.y() * y - q.z() * z);
        return dest;
    }

    /**
     * Pre-multiply this quaternion by the quaternion represented via <code>(qx, qy, qz, qw)</code>.
     * <p>
     * If <code>T</code> is <code>this</code> and <code>Q</code> is the given quaternion, then the resulting quaternion <code>R</code> is:
     * <p>
     * <code>R = Q * T</code>
     * <p>
     * So, this method uses pre-multiplication, resulting in a vector to be transformed by <code>T</code> first, and then by <code>Q</code>.
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
    public Quaterniond premul(double qx, double qy, double qz, double qw) {
        return premul(qx, qy, qz, qw, this);
    }

    /* (non-Javadoc)
     * @see org.joml.Quaterniondc#premul(double, double, double, double, org.joml.Quaterniond)
     */
    public Quaterniond premul(double qx, double qy, double qz, double qw, Quaterniond dest) {
        dest.set(qw * x + qx * w + qy * z - qz * y,
                 qw * y - qx * z + qy * w + qz * x,
                 qw * z + qx * y - qy * x + qz * w,
                 qw * w - qx * x - qy * y - qz * z);
        return dest;
    }

    /* (non-Javadoc)
     * @see org.joml.Quaterniondc#transform(org.joml.Vector3d)
     */
    public Vector3d transform(Vector3d vec){
        return transform(vec.x, vec.y, vec.z, vec);
    }

    public Vector3d transformPositiveX(Vector3d dest) {
        double w2 = w * w;
        double x2 = x * x;
        double y2 = y * y;
        double z2 = z * z;
        double zw = z * w;
        double xy = x * y;
        double xz = x * z;
        double yw = y * w;
        dest.x = w2 + x2 - z2 - y2;
        dest.y = xy + zw + zw + xy;
        dest.z = xz - yw + xz - yw;
        return dest;
    }

    public Vector4d transformPositiveX(Vector4d dest) {
        double w2 = w * w;
        double x2 = x * x;
        double y2 = y * y;
        double z2 = z * z;
        double zw = z * w;
        double xy = x * y;
        double xz = x * z;
        double yw = y * w;
        dest.x = w2 + x2 - z2 - y2;
        dest.y = xy + zw + zw + xy;
        dest.z = xz - yw + xz - yw;
        return dest;
    }

    public Vector3d transformUnitPositiveX(Vector3d dest) {
        double y2 = y * y;
        double z2 = z * z;
        double xy = x * y;
        double xz = x * z;
        double yw = y * w;
        double zw = z * w;
        dest.x = 1.0 - y2 - y2 - z2 - z2;
        dest.y = xy + zw + xy + zw;
        dest.z = xz - yw + xz - yw;
        return dest;
    }

    public Vector4d transformUnitPositiveX(Vector4d dest) {
        double y2 = y * y;
        double z2 = z * z;
        double xy = x * y;
        double xz = x * z;
        double yw = y * w;
        double zw = z * w;
        dest.x = 1.0 - y2 - y2 - z2 - z2;
        dest.y = xy + zw + xy + zw;
        dest.z = xz - yw + xz - yw;
        return dest;
    }

    public Vector3d transformPositiveY(Vector3d dest) {
        double w2 = w * w;
        double x2 = x * x;
        double y2 = y * y;
        double z2 = z * z;
        double zw = z * w;
        double xy = x * y;
        double yz = y * z;
        double xw = x * w;
        dest.x = -zw + xy - zw + xy;
        dest.y = y2 - z2 + w2 - x2;
        dest.z = yz + yz + xw + xw;
        return dest;
    }

    public Vector4d transformPositiveY(Vector4d dest) {
        double w2 = w * w;
        double x2 = x * x;
        double y2 = y * y;
        double z2 = z * z;
        double zw = z * w;
        double xy = x * y;
        double yz = y * z;
        double xw = x * w;
        dest.x = -zw + xy - zw + xy;
        dest.y = y2 - z2 + w2 - x2;
        dest.z = yz + yz + xw + xw;
        return dest;
    }

    public Vector4d transformUnitPositiveY(Vector4d dest) {
        double x2 = x * x;
        double z2 = z * z;
        double xy = x * y;
        double yz = y * z;
        double xw = x * w;
        double zw = z * w;
        dest.x = xy - zw + xy - zw;
        dest.y = 1.0 - x2 - x2 - z2 - z2;
        dest.z = yz + yz + xw + xw;
        return dest;
    }

    public Vector3d transformUnitPositiveY(Vector3d dest) {
        double x2 = x * x;
        double z2 = z * z;
        double xy = x * y;
        double yz = y * z;
        double xw = x * w;
        double zw = z * w;
        dest.x = xy - zw + xy - zw;
        dest.y = 1.0 - x2 - x2 - z2 - z2;
        dest.z = yz + yz + xw + xw;
        return dest;
    }

    public Vector3d transformPositiveZ(Vector3d dest) {
        double w2 = w * w;
        double x2 = x * x;
        double y2 = y * y;
        double z2 = z * z;
        double xz = x * z;
        double yw = y * w;
        double yz = y * z;
        double xw = x * w;
        dest.x = yw + xz + xz + yw;
        dest.y = yz + yz - xw - xw;
        dest.z = z2 - y2 - x2 + w2;
        return dest;
    }

    public Vector4d transformPositiveZ(Vector4d dest) {
        double w2 = w * w;
        double x2 = x * x;
        double y2 = y * y;
        double z2 = z * z;
        double xz = x * z;
        double yw = y * w;
        double yz = y * z;
        double xw = x * w;
        dest.x = yw + xz + xz + yw;
        dest.y = yz + yz - xw - xw;
        dest.z = z2 - y2 - x2 + w2;
        return dest;
    }

    public Vector4d transformUnitPositiveZ(Vector4d dest) {
        double x2 = x * x;
        double y2 = y * y;
        double xz = x * z;
        double yz = y * z;
        double xw = x * w;
        double yw = y * w;
        dest.x = xz + yw + xz + yw;
        dest.y = yz + yz - xw - xw;
        dest.z = 1.0 - x2 - x2 - y2 - y2;
        return dest;
    }

    public Vector3d transformUnitPositiveZ(Vector3d dest) {
        double x2 = x * x;
        double y2 = y * y;
        double xz = x * z;
        double yz = y * z;
        double xw = x * w;
        double yw = y * w;
        dest.x = xz + yw + xz + yw;
        dest.y = yz + yz - xw - xw;
        dest.z = 1.0 - x2 - x2 - y2 - y2;
        return dest;
    }

    /* (non-Javadoc)
     * @see org.joml.Quaterniondc#transform(org.joml.Vector4d)
     */
    public Vector4d transform(Vector4d vec){
        return transform(vec, vec);
    }

    /* (non-Javadoc)
     * @see org.joml.Quaterniondc#transform(org.joml.Vector3dc, org.joml.Vector3d)
     */
    public Vector3d transform(Vector3dc vec, Vector3d dest) {
        return transform(vec.x(), vec.y(), vec.z(), dest);
    }

    /* (non-Javadoc)
     * @see org.joml.Quaterniondc#transform(double, double, double, org.joml.Vector3d)
     */
    public Vector3d transform(double x, double y, double z, Vector3d dest) {
        double w2 = this.w * this.w;
        double x2 = this.x * this.x;
        double y2 = this.y * this.y;
        double z2 = this.z * this.z;
        double zw = this.z * this.w, zwd = zw + zw;
        double xy = this.x * this.y, xyd = xy + xy;
        double xz = this.x * this.z, xzd = xz + xz;
        double yw = this.y * this.w, ywd = yw + yw;
        double yz = this.y * this.z, yzd = yz + yz;
        double xw = this.x * this.w, xwd = xw + xw;
        double m00 = w2 + x2 - z2 - y2;
        double m01 = xyd + zwd;
        double m02 = xzd - ywd;
        double m10 = xyd - zwd;
        double m11 = y2 - z2 + w2 - x2;
        double m12 = yzd + xwd;
        double m20 = ywd + xzd;
        double m21 = yzd - xwd;
        double m22 = z2 - y2 - x2 + w2;
        dest.x = m00 * x + m10 * y + m20 * z;
        dest.y = m01 * x + m11 * y + m21 * z;
        dest.z = m02 * x + m12 * y + m22 * z;
        return dest;
    }

    /* (non-Javadoc)
     * @see org.joml.Quaterniondc#transform(org.joml.Vector4dc, org.joml.Vector4d)
     */
    public Vector4d transform(Vector4dc vec, Vector4d dest) {
        return transform(vec.x(), vec.y(), vec.z(), dest);
    }

    /* (non-Javadoc)
     * @see org.joml.Quaterniondc#transform(double, double, double, org.joml.Vector4d)
     */
    public Vector4d transform(double x, double y, double z, Vector4d dest) {
        double w2 = this.w * this.w;
        double x2 = this.x * this.x;
        double y2 = this.y * this.y;
        double z2 = this.z * this.z;
        double zw = this.z * this.w, zwd = zw + zw;
        double xy = this.x * this.y, xyd = xy + xy;
        double xz = this.x * this.z, xzd = xz + xz;
        double yw = this.y * this.w, ywd = yw + yw;
        double yz = this.y * this.z, yzd = yz + yz;
        double xw = this.x * this.w, xwd = xw + xw;
        double m00 = w2 + x2 - z2 - y2;
        double m01 = xyd + zwd;
        double m02 = xzd - ywd;
        double m10 = xyd - zwd;
        double m11 = y2 - z2 + w2 - x2;
        double m12 = yzd + xwd;
        double m20 = ywd + xzd;
        double m21 = yzd - xwd;
        double m22 = z2 - y2 - x2 + w2;
        dest.x = m00 * x + m10 * y + m20 * z;
        dest.y = m01 * x + m11 * y + m21 * z;
        dest.z = m02 * x + m12 * y + m22 * z;
        return dest;
    }

    /* (non-Javadoc)
     * @see org.joml.Quaterniondc#invert(org.joml.Quaterniond)
     */
    public Quaterniond invert(Quaterniond dest) {
        double invNorm = 1.0 / (x * x + y * y + z * z + w * w);
        dest.x = -x * invNorm;
        dest.y = -y * invNorm;
        dest.z = -z * invNorm;
        dest.w = w * invNorm;
        return dest;
    }

    /**
     * Invert this quaternion and {@link #normalize() normalize} it.
     * <p>
     * If this quaternion is already normalized, then {@link #conjugate()} should be used instead.
     * 
     * @see #conjugate()
     * 
     * @return this
     */
    public Quaterniond invert() {
        return invert(this);
    }

    /* (non-Javadoc)
     * @see org.joml.Quaterniondc#div(org.joml.Quaterniondc, org.joml.Quaterniond)
     */
    public Quaterniond div(Quaterniondc b, Quaterniond dest) {
        double invNorm = 1.0 / (b.x() * b.x() + b.y() * b.y() + b.z() * b.z() + b.w() * b.w());
        double x = -b.x() * invNorm;
        double y = -b.y() * invNorm;
        double z = -b.z() * invNorm;
        double w = b.w() * invNorm;
        dest.set(this.w * x + this.x * w + this.y * z - this.z * y,
                 this.w * y - this.x * z + this.y * w + this.z * x,
                 this.w * z + this.x * y - this.y * x + this.z * w,
                 this.w * w - this.x * x - this.y * y - this.z * z);
        return dest;
    }

    /**
     * Divide <code>this</code> quaternion by <code>b</code>.
     * <p>
     * The division expressed using the inverse is performed in the following way:
     * <p>
     * <code>this = this * b^-1</code>, where <code>b^-1</code> is the inverse of <code>b</code>.
     * 
     * @param b
     *          the {@link Quaterniondc} to divide this by
     * @return this
     */
    public Quaterniond div(Quaterniondc b) {
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

    /* (non-Javadoc)
     * @see org.joml.Quaterniondc#conjugate(org.joml.Quaterniond)
     */
    public Quaterniond conjugate(Quaterniond dest) {
        dest.x = -x;
        dest.y = -y;
        dest.z = -z;
        dest.w = w;
        return dest;
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

    /* (non-Javadoc)
     * @see org.joml.Quaterniondc#lengthSquared()
     */
    public double lengthSquared() {
        return x * x + y * y + z * z + w * w;
    }


    /**
     * Set this quaternion from the supplied euler angles (in radians) with rotation order XYZ.
     * <p>
     * This method is equivalent to calling: <code>rotationX(angleX).rotateY(angleY).rotateZ(angleZ)</code>
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
        double cx = Math.cosFromSin(sx, angleX * 0.5);
        double sy = Math.sin(angleY * 0.5);
        double cy = Math.cosFromSin(sy, angleY * 0.5);
        double sz = Math.sin(angleZ * 0.5);
        double cz = Math.cosFromSin(sz, angleZ * 0.5);

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
     * This method is equivalent to calling: <code>rotationZ(angleZ).rotateY(angleY).rotateX(angleX)</code>
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
        double cx = Math.cosFromSin(sx, angleX * 0.5);
        double sy = Math.sin(angleY * 0.5);
        double cy = Math.cosFromSin(sy, angleY * 0.5);
        double sz = Math.sin(angleZ * 0.5);
        double cz = Math.cosFromSin(sz, angleZ * 0.5);

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
     * This method is equivalent to calling: <code>rotationY(angleY).rotateX(angleX).rotateZ(angleZ)</code>
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
        double cx = Math.cosFromSin(sx, angleX * 0.5);
        double sy = Math.sin(angleY * 0.5);
        double cy = Math.cosFromSin(sy, angleY * 0.5);
        double sz = Math.sin(angleZ * 0.5);
        double cz = Math.cosFromSin(sz, angleZ * 0.5);

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
     * Interpolate between <code>this</code> {@link #normalize() unit} quaternion and the specified
     * <code>target</code> {@link #normalize() unit} quaternion using spherical linear interpolation using the specified interpolation factor <code>alpha</code>.
     * <p>
     * This method resorts to non-spherical linear interpolation when the absolute dot product between <code>this</code> and <code>target</code> is
     * below <code>1E-6</code>.
     * 
     * @param target
     *          the target of the interpolation, which should be reached with <code>alpha = 1.0</code>
     * @param alpha
     *          the interpolation factor, within <code>[0..1]</code>
     * @return this
     */
    public Quaterniond slerp(Quaterniondc target, double alpha) {
        return slerp(target, alpha, this);
    }

    /* (non-Javadoc)
     * @see org.joml.Quaterniondc#slerp(org.joml.Quaterniondc, double, org.joml.Quaterniond)
     */
    public Quaterniond slerp(Quaterniondc target, double alpha, Quaterniond dest) {
        double cosom = x * target.x() + y * target.y() + z * target.z() + w * target.w();
        double absCosom = Math.abs(cosom);
        double scale0, scale1;
        if (1.0 - absCosom > 1E-6) {
            double sinSqr = 1.0 - absCosom * absCosom;
            double sinom = 1.0 / Math.sqrt(sinSqr);
            double omega = Math.atan2(sinSqr * sinom, absCosom);
            scale0 = Math.sin((1.0 - alpha) * omega) * sinom;
            scale1 = Math.sin(alpha * omega) * sinom;
        } else {
            scale0 = 1.0 - alpha;
            scale1 = alpha;
        }
        scale1 = cosom >= 0.0 ? scale1 : -scale1;
        dest.x = scale0 * x + scale1 * target.x();
        dest.y = scale0 * y + scale1 * target.y();
        dest.z = scale0 * z + scale1 * target.z();
        dest.w = scale0 * w + scale1 * target.w();
        return dest;
    }

    /**
     * Interpolate between all of the quaternions given in <code>qs</code> via spherical linear interpolation using the specified interpolation factors <code>weights</code>,
     * and store the result in <code>dest</code>.
     * <p>
     * This method will interpolate between each two successive quaternions via {@link #slerp(Quaterniondc, double)} using their relative interpolation weights.
     * <p>
     * This method resorts to non-spherical linear interpolation when the absolute dot product of any two interpolated quaternions is below <code>1E-6f</code>.
     * <p>
     * Reference: <a href="http://gamedev.stackexchange.com/questions/62354/method-for-interpolation-between-3-quaternions#answer-62356">http://gamedev.stackexchange.com/</a>
     * 
     * @param qs
     *          the quaternions to interpolate over
     * @param weights
     *          the weights of each individual quaternion in <code>qs</code>
     * @param dest
     *          will hold the result
     * @return dest
     */
    public static Quaterniondc slerp(Quaterniond[] qs, double[] weights, Quaterniond dest) {
        dest.set(qs[0]);
        double w = weights[0];
        for (int i = 1; i < qs.length; i++) {
            double w0 = w;
            double w1 = weights[i];
            double rw1 = w1 / (w0 + w1);
            w += w1;
            dest.slerp(qs[i], rw1);
        }
        return dest;
    }

    /**
     * Apply scaling to this quaternion, which results in any vector transformed by this quaternion to change
     * its length by the given <code>factor</code>.
     * 
     * @param factor
     *          the scaling factor
     * @return this
     */
    public Quaterniond scale(double factor) {
        return scale(factor, this);
    }

    /* (non-Javadoc)
     * @see org.joml.Quaterniondc#scale(double, org.joml.Quaterniond)
     */
    public Quaterniond scale(double factor, Quaterniond dest) {
        double sqrt = Math.sqrt(factor);
        dest.x = sqrt * x;
        dest.y = sqrt * y;
        dest.z = sqrt * z;
        dest.w = sqrt * w;
        return dest;
    }

    /**
     * Set this quaternion to represent scaling, which results in a transformed vector to change
     * its length by the given <code>factor</code>.
     * 
     * @param factor
     *          the scaling factor
     * @return this
     */
    public Quaterniond scaling(double factor) {
        double sqrt = Math.sqrt(factor);
        this.x = 0.0;
        this.y = 0.0;
        this.z = 0.0;
        this.w = sqrt;
        return this;
    }

    /**
     * Integrate the rotation given by the angular velocity <code>(vx, vy, vz)</code> around the x, y and z axis, respectively,
     * with respect to the given elapsed time delta <code>dt</code> and add the differentiate rotation to the rotation represented by this quaternion.
     * <p>
     * This method pre-multiplies the rotation given by <code>dt</code> and <code>(vx, vy, vz)</code> by <code>this</code>, so
     * the angular velocities are always relative to the local coordinate system of the rotation represented by <code>this</code> quaternion.
     * <p>
     * This method is equivalent to calling: <code>rotateLocal(dt * vx, dt * vy, dt * vz)</code>
     * <p>
     * Reference: <a href="http://physicsforgames.blogspot.de/2010/02/quaternions.html">http://physicsforgames.blogspot.de/</a>
     * 
     * @param dt
     *          the delta time
     * @param vx
     *          the angular velocity around the x axis
     * @param vy
     *          the angular velocity around the y axis
     * @param vz
     *          the angular velocity around the z axis
     * @return this
     */
    public Quaterniond integrate(double dt, double vx, double vy, double vz) {
        return integrate(dt, vx, vy, vz, this);
    }

    /* (non-Javadoc)
     * @see org.joml.Quaterniondc#integrate(double, double, double, double, org.joml.Quaterniond)
     */
    public Quaterniond integrate(double dt, double vx, double vy, double vz, Quaterniond dest) {
        double thetaX = vx * 0.5;
        double thetaY = vy * 0.5;
        double thetaZ = vz * 0.5;
        double thetaMagSq = thetaX * thetaX + thetaY * thetaY + thetaZ * thetaZ;
        double s;
        double dqX, dqY, dqZ, dqW;
        if (thetaMagSq * thetaMagSq / 24.0 < 1E-8) {
            dqW = 1.0 - thetaMagSq * 0.5;
            s = 1.0 - thetaMagSq / 6.0;
        } else {
            double thetaMag = Math.sqrt(thetaMagSq);
            double sin = Math.sin(thetaMag);
            s = sin / thetaMag;
            dqW = Math.cosFromSin(sin, thetaMag);
        }
        dqX = thetaX * s;
        dqY = thetaY * s;
        dqZ = thetaZ * s;
        /* Pre-multiplication */
        dest.set(dqW * x + dqX * w + dqY * z - dqZ * y,
                 dqW * y - dqX * z + dqY * w + dqZ * x,
                 dqW * z + dqX * y - dqY * x + dqZ * w,
                 dqW * w - dqX * x - dqY * y - dqZ * z);
        return dest;
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
    public Quaterniond nlerp(Quaterniondc q, double factor) {
        return nlerp(q, factor, this);
    }

    /* (non-Javadoc)
     * @see org.joml.Quaterniondc#nlerp(org.joml.Quaterniondc, double, org.joml.Quaterniond)
     */
    public Quaterniond nlerp(Quaterniondc q, double factor, Quaterniond dest) {
        double cosom = x * q.x() + y * q.y() + z * q.z() + w * q.w();
        double scale0 = 1.0 - factor;
        double scale1 = (cosom >= 0.0) ? factor : -factor;
        dest.x = scale0 * x + scale1 * q.x();
        dest.y = scale0 * y + scale1 * q.y();
        dest.z = scale0 * z + scale1 * q.z();
        dest.w = scale0 * w + scale1 * q.w();
        double s = 1.0 / Math.sqrt(dest.x * dest.x + dest.y * dest.y + dest.z * dest.z + dest.w * dest.w);
        dest.x *= s;
        dest.y *= s;
        dest.z *= s;
        dest.w *= s;
        return dest;
    }

    /**
     * Interpolate between all of the quaternions given in <code>qs</code> via non-spherical linear interpolation using the
     * specified interpolation factors <code>weights</code>, and store the result in <code>dest</code>.
     * <p>
     * This method will interpolate between each two successive quaternions via {@link #nlerp(Quaterniondc, double)}
     * using their relative interpolation weights.
     * <p>
     * Reference: <a href="http://gamedev.stackexchange.com/questions/62354/method-for-interpolation-between-3-quaternions#answer-62356">http://gamedev.stackexchange.com/</a>
     * 
     * @param qs
     *          the quaternions to interpolate over
     * @param weights
     *          the weights of each individual quaternion in <code>qs</code>
     * @param dest
     *          will hold the result
     * @return dest
     */
    public static Quaterniondc nlerp(Quaterniond[] qs, double[] weights, Quaterniond dest) {
        dest.set(qs[0]);
        double w = weights[0];
        for (int i = 1; i < qs.length; i++) {
            double w0 = w;
            double w1 = weights[i];
            double rw1 = w1 / (w0 + w1);
            w += w1;
            dest.nlerp(qs[i], rw1);
        }
        return dest;
    }

    /* (non-Javadoc)
     * @see org.joml.Quaterniondc#nlerpIterative(org.joml.Quaterniondc, double, double, org.joml.Quaterniond)
     */
    public Quaterniond nlerpIterative(Quaterniondc q, double alpha, double dotThreshold, Quaterniond dest) {
        double q1x = x, q1y = y, q1z = z, q1w = w;
        double q2x = q.x(), q2y = q.y(), q2z = q.z(), q2w = q.w();
        double dot = q1x * q2x + q1y * q2y + q1z * q2z + q1w * q2w;
        double absDot = Math.abs(dot);
        if (1.0 - 1E-6 < absDot) {
            return dest.set(this);
        }
        double alphaN = alpha;
        while (absDot < dotThreshold) {
            double scale0 = 0.5;
            double scale1 = dot >= 0.0 ? 0.5 : -0.5;
            if (alphaN < 0.5) {
                q2x = scale0 * q2x + scale1 * q1x;
                q2y = scale0 * q2y + scale1 * q1y;
                q2z = scale0 * q2z + scale1 * q1z;
                q2w = scale0 * q2w + scale1 * q1w;
                double s = 1.0 / Math.sqrt(q2x * q2x + q2y * q2y + q2z * q2z + q2w * q2w);
                q2x *= s;
                q2y *= s;
                q2z *= s;
                q2w *= s;
                alphaN = alphaN + alphaN;
            } else {
                q1x = scale0 * q1x + scale1 * q2x;
                q1y = scale0 * q1y + scale1 * q2y;
                q1z = scale0 * q1z + scale1 * q2z;
                q1w = scale0 * q1w + scale1 * q2w;
                double s = 1.0 / Math.sqrt(q1x * q1x + q1y * q1y + q1z * q1z + q1w * q1w);
                q1x *= s;
                q1y *= s;
                q1z *= s;
                q1w *= s;
                alphaN = alphaN + alphaN - 1.0;
            }
            dot = q1x * q2x + q1y * q2y + q1z * q2z + q1w * q2w;
            absDot = Math.abs(dot);
        }
        double scale0 = 1.0 - alphaN;
        double scale1 = dot >= 0.0 ? alphaN : -alphaN;
        double destX = scale0 * q1x + scale1 * q2x;
        double destY = scale0 * q1y + scale1 * q2y;
        double destZ = scale0 * q1z + scale1 * q2z;
        double destW = scale0 * q1w + scale1 * q2w;
        double s = 1.0 / Math.sqrt(destX * destX + destY * destY + destZ * destZ + destW * destW);
        dest.x *= s;
        dest.y *= s;
        dest.z *= s;
        dest.w *= s;
        return dest;
    }

    /**
     * Compute linear (non-spherical) interpolations of <code>this</code> and the given quaternion <code>q</code>
     * iteratively and store the result in <code>this</code>.
     * <p>
     * This method performs a series of small-step nlerp interpolations to avoid doing a costly spherical linear interpolation, like
     * {@link #slerp(Quaterniondc, double, Quaterniond) slerp},
     * by subdividing the rotation arc between <code>this</code> and <code>q</code> via non-spherical linear interpolations as long as
     * the absolute dot product of <code>this</code> and <code>q</code> is greater than the given <code>dotThreshold</code> parameter.
     * <p>
     * Thanks to <code>@theagentd</code> at <a href="http://www.java-gaming.org/">http://www.java-gaming.org/</a> for providing the code.
     * 
     * @param q
     *          the other quaternion
     * @param alpha
     *          the interpolation factor, between 0.0 and 1.0
     * @param dotThreshold
     *          the threshold for the dot product of <code>this</code> and <code>q</code> above which this method performs another iteration
     *          of a small-step linear interpolation
     * @return this
     */
    public Quaterniond nlerpIterative(Quaterniondc q, double alpha, double dotThreshold) {
        return nlerpIterative(q, alpha, dotThreshold, this);
    }

    /**
     * Interpolate between all of the quaternions given in <code>qs</code> via iterative non-spherical linear interpolation using the
     * specified interpolation factors <code>weights</code>, and store the result in <code>dest</code>.
     * <p>
     * This method will interpolate between each two successive quaternions via {@link #nlerpIterative(Quaterniondc, double, double)}
     * using their relative interpolation weights.
     * <p>
     * Reference: <a href="http://gamedev.stackexchange.com/questions/62354/method-for-interpolation-between-3-quaternions#answer-62356">http://gamedev.stackexchange.com/</a>
     * 
     * @param qs
     *          the quaternions to interpolate over
     * @param weights
     *          the weights of each individual quaternion in <code>qs</code>
     * @param dotThreshold
     *          the threshold for the dot product of each two interpolated quaternions above which {@link #nlerpIterative(Quaterniondc, double, double)} performs another iteration
     *          of a small-step linear interpolation
     * @param dest
     *          will hold the result
     * @return dest
     */
    public static Quaterniond nlerpIterative(Quaterniondc[] qs, double[] weights, double dotThreshold, Quaterniond dest) {
        dest.set(qs[0]);
        double w = weights[0];
        for (int i = 1; i < qs.length; i++) {
            double w0 = w;
            double w1 = weights[i];
            double rw1 = w1 / (w0 + w1);
            w += w1;
            dest.nlerpIterative(qs[i], rw1, dotThreshold);
        }
        return dest;
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
     * @see #lookAlong(double, double, double, double, double, double, Quaterniond)
     * 
     * @param dir
     *              the direction to map to the positive Z axis
     * @param up
     *              the vector which will be mapped to a vector parallel to the plane
     *              spanned by the given <code>dir</code> and <code>up</code>
     * @return this
     */
    public Quaterniond lookAlong(Vector3dc dir, Vector3dc up) {
        return lookAlong(dir.x(), dir.y(), dir.z(), up.x(), up.y(), up.z(), this);
    }

    /* (non-Javadoc)
     * @see org.joml.Quaterniondc#lookAlong(org.joml.Vector3dc, org.joml.Vector3dc, org.joml.Quaterniond)
     */
    public Quaterniond lookAlong(Vector3dc dir, Vector3dc up, Quaterniond dest) {
        return lookAlong(dir.x(), dir.y(), dir.z(), up.x(), up.y(), up.z(), dest);
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
     * @see #lookAlong(double, double, double, double, double, double, Quaterniond)
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
    public Quaterniond lookAlong(double dirX, double dirY, double dirZ, double upX, double upY, double upZ) {
        return lookAlong(dirX, dirY, dirZ, upX, upY, upZ, this);
    }

    /* (non-Javadoc)
     * @see org.joml.Quaterniondc#lookAlong(double, double, double, double, double, double, org.joml.Quaterniond)
     */
    public Quaterniond lookAlong(double dirX, double dirY, double dirZ, double upX, double upY, double upZ, Quaterniond dest) {
        // Normalize direction
        double invDirLength = 1.0 / Math.sqrt(dirX * dirX + dirY * dirY + dirZ * dirZ);
        double dirnX = -dirX * invDirLength;
        double dirnY = -dirY * invDirLength;
        double dirnZ = -dirZ * invDirLength;
        // left = up x dir
        double leftX, leftY, leftZ;
        leftX = upY * dirnZ - upZ * dirnY;
        leftY = upZ * dirnX - upX * dirnZ;
        leftZ = upX * dirnY - upY * dirnX;
        // normalize left
        double invLeftLength = 1.0 / Math.sqrt(leftX * leftX + leftY * leftY + leftZ * leftZ);
        leftX *= invLeftLength;
        leftY *= invLeftLength;
        leftZ *= invLeftLength;
        // up = direction x left
        double upnX = dirnY * leftZ - dirnZ * leftY;
        double upnY = dirnZ * leftX - dirnX * leftZ;
        double upnZ = dirnX * leftY - dirnY * leftX;

        /* Convert orthonormal basis vectors to quaternion */
        double x, y, z, w;
        double t;
        double tr = leftX + upnY + dirnZ;
        if (tr >= 0.0) {
            t = Math.sqrt(tr + 1.0);
            w = t * 0.5;
            t = 0.5 / t;
            x = (dirnY - upnZ) * t;
            y = (leftZ - dirnX) * t;
            z = (upnX - leftY) * t;
        } else {
            if (leftX > upnY && leftX > dirnZ) {
                t = Math.sqrt(1.0 + leftX - upnY - dirnZ);
                x = t * 0.5;
                t = 0.5 / t;
                y = (leftY + upnX) * t;
                z = (dirnX + leftZ) * t;
                w = (dirnY - upnZ) * t;
            } else if (upnY > dirnZ) {
                t = Math.sqrt(1.0 + upnY - leftX - dirnZ);
                y = t * 0.5;
                t = 0.5 / t;
                x = (leftY + upnX) * t;
                z = (upnZ + dirnY) * t;
                w = (leftZ - dirnX) * t;
            } else {
                t = Math.sqrt(1.0 + dirnZ - leftX - upnY);
                z = t * 0.5;
                t = 0.5 / t;
                x = (dirnX + leftZ) * t;
                y = (upnZ + dirnY) * t;
                w = (upnX - leftY) * t;
            }
        }
        /* Multiply */
        dest.set(this.w * x + this.x * w + this.y * z - this.z * y,
                 this.w * y - this.x * z + this.y * w + this.z * x,
                 this.w * z + this.x * y - this.y * x + this.z * w,
                 this.w * w - this.x * x - this.y * y - this.z * z);
        return dest;
    }

    /**
     * Return a string representation of this quaternion.
     * <p>
     * This method creates a new {@link DecimalFormat} on every invocation with the format string "<code>0.000E0;-</code>".
     * 
     * @return the string representation
     */
    public String toString() {
        return Runtime.formatNumbers(toString(Options.NUMBER_FORMAT));
    }

    /**
     * Return a string representation of this quaternion by formatting the components with the given {@link NumberFormat}.
     * 
     * @param formatter
     *          the {@link NumberFormat} used to format the quaternion components with
     * @return the string representation
     */
    public String toString(NumberFormat formatter) {
        return "(" + formatter.format(x) + " " + formatter.format(y) + " " + formatter.format(z) + " " + formatter.format(w) + ")";
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

    public int hashCode() {
        final int prime = 31;
        int result = 1;
        long temp;
        temp = Double.doubleToLongBits(w);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(x);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(z);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Quaterniond other = (Quaterniond) obj;
        if (Double.doubleToLongBits(w) != Double.doubleToLongBits(other.w))
            return false;
        if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
            return false;
        if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y))
            return false;
        if (Double.doubleToLongBits(z) != Double.doubleToLongBits(other.z))
            return false;
        return true;
    }

    /**
     * Compute the difference between <code>this</code> and the <code>other</code> quaternion
     * and store the result in <code>this</code>.
     * <p>
     * The difference is the rotation that has to be applied to get from
     * <code>this</code> rotation to <code>other</code>. If <code>T</code> is <code>this</code>, <code>Q</code>
     * is <code>other</code> and <code>D</code> is the computed difference, then the following equation holds:
     * <p>
     * <code>T * D = Q</code>
     * <p>
     * It is defined as: <code>D = T^-1 * Q</code>, where <code>T^-1</code> denotes the {@link #invert() inverse} of <code>T</code>.
     * 
     * @param other
     *          the other quaternion
     * @return this
     */
    public Quaterniond difference(Quaterniondc other) {
        return difference(other, this);
    }

    /* (non-Javadoc)
     * @see org.joml.Quaterniondc#difference(org.joml.Quaterniondc, org.joml.Quaterniond)
     */
    public Quaterniond difference(Quaterniondc other, Quaterniond dest) {
        double invNorm = 1.0 / (x * x + y * y + z * z + w * w);
        double x = -this.x * invNorm;
        double y = -this.y * invNorm;
        double z = -this.z * invNorm;
        double w = this.w * invNorm;
        dest.set(w * other.x() + x * other.w() + y * other.z() - z * other.y(),
                 w * other.y() - x * other.z() + y * other.w() + z * other.x(),
                 w * other.z() + x * other.y() - y * other.x() + z * other.w(),
                 w * other.w() - x * other.x() - y * other.y() - z * other.z());
        return dest;
    }
    

    /**
     * Set <code>this</code> quaternion to a rotation that rotates the <code>fromDir</code> vector to point along <code>toDir</code>.
     * <p>
     * Since there can be multiple possible rotations, this method chooses the one with the shortest arc.
     * <p>
     * Reference: <a href="http://stackoverflow.com/questions/1171849/finding-quaternion-representing-the-rotation-from-one-vector-to-another#answer-1171995">stackoverflow.com</a>
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
        x = fromDirY * toDirZ - fromDirZ * toDirY;
        y = fromDirZ * toDirX - fromDirX * toDirZ;
        z = fromDirX * toDirY - fromDirY * toDirX;
        w = Math.sqrt((fromDirX * fromDirX + fromDirY * fromDirY + fromDirZ * fromDirZ) *
                              (toDirX * toDirX + toDirY * toDirY + toDirZ * toDirZ)) +
                 (fromDirX * toDirX + fromDirY * toDirY + fromDirZ * toDirZ);
        double invNorm = 1.0 / Math.sqrt(x * x + y * y + z * z + w * w);
        if (Double.isInfinite(invNorm)) {
            // Rotation is ambiguous: Find appropriate rotation axis (1. try toDir x +Z)
            x = toDirY; y = -toDirX; z = 0.0; w = 0.0;
            invNorm = (float) (1.0 / Math.sqrt(x * x + y * y));
            if (Double.isInfinite(invNorm)) {
                // 2. try toDir x +X
                x = 0.0; y = toDirZ; z = -toDirY; w = 0.0;
                invNorm = (float) (1.0 / Math.sqrt(y * y + z * z));
            }
        }
        x *= invNorm;
        y *= invNorm;
        z *= invNorm;
        w *= invNorm;
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
    public Quaterniond rotationTo(Vector3dc fromDir, Vector3dc toDir) {
        return rotationTo(fromDir.x(), fromDir.y(), fromDir.z(), toDir.x(), toDir.y(), toDir.z());
    }

    /* (non-Javadoc)
     * @see org.joml.Quaterniondc#rotateTo(double, double, double, double, double, double, org.joml.Quaterniond)
     */
    public Quaterniond rotateTo(double fromDirX, double fromDirY, double fromDirZ,
                                double toDirX, double toDirY, double toDirZ, Quaterniond dest) {
        double x = fromDirY * toDirZ - fromDirZ * toDirY;
        double y = fromDirZ * toDirX - fromDirX * toDirZ;
        double z = fromDirX * toDirY - fromDirY * toDirX;
        double w = Math.sqrt((fromDirX * fromDirX + fromDirY * fromDirY + fromDirZ * fromDirZ) *
                                    (toDirX * toDirX + toDirY * toDirY + toDirZ * toDirZ)) +
                  (fromDirX * toDirX + fromDirY * toDirY + fromDirZ * toDirZ);
        double invNorm = 1.0 / Math.sqrt(x * x + y * y + z * z + w * w);
        if (Double.isInfinite(invNorm)) {
            // Rotation is ambiguous: Find appropriate rotation axis (1. try toDir x +Z)
            x = toDirY; y = -toDirX; z = 0.0; w = 0.0;
            invNorm = (float) (1.0 / Math.sqrt(x * x + y * y));
            if (Double.isInfinite(invNorm)) {
                // 2. try toDir x +X
                x = 0.0; y = toDirZ; z = -toDirY; w = 0.0;
                invNorm = (float) (1.0 / Math.sqrt(y * y + z * z));
            }
        }
        x *= invNorm;
        y *= invNorm;
        z *= invNorm;
        w *= invNorm;
        /* Multiply */
        dest.set(this.w * x + this.x * w + this.y * z - this.z * y,
                 this.w * y - this.x * z + this.y * w + this.z * x,
                 this.w * z + this.x * y - this.y * x + this.z * w,
                 this.w * w - this.x * x - this.y * y - this.z * z);
        return dest;
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
    public Quaterniond rotationAxis(AxisAngle4f axisAngle) {
        return rotationAxis(axisAngle.angle, axisAngle.x, axisAngle.y, axisAngle.z);
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
        w = (float) Math.cosFromSin(sinAngle, hangle);

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
        double sin = Math.sin(angle * 0.5);
        double cos = Math.cosFromSin(sin, angle * 0.5);
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
        double sin = Math.sin(angle * 0.5);
        double cos = Math.cosFromSin(sin, angle * 0.5);
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
        double sin = Math.sin(angle * 0.5);
        double cos = Math.cosFromSin(sin, angle * 0.5);
        w = cos;
        x = 0.0;
        y = 0.0;
        z = sin;
        return this;
    }

    /**
     * Apply a rotation to <code>this</code> that rotates the <code>fromDir</code> vector to point along <code>toDir</code>.
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

    /* (non-Javadoc)
     * @see org.joml.Quaterniondc#rotateTo(org.joml.Vector3dc, org.joml.Vector3dc, org.joml.Quaterniond)
     */
    public Quaterniond rotateTo(Vector3dc fromDir, Vector3dc toDir, Quaterniond dest) {
        return rotateTo(fromDir.x(), fromDir.y(), fromDir.z(), toDir.x(), toDir.y(), toDir.z(), dest);
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
    public Quaterniond rotateTo(Vector3dc fromDir, Vector3dc toDir) {
        return rotateTo(fromDir.x(), fromDir.y(), fromDir.z(), toDir.x(), toDir.y(), toDir.z(), this);
    }

    /**
     * Apply a rotation to <code>this</code> quaternion rotating the given radians about the x axis.
     * <p>
     * If <code>Q</code> is <code>this</code> quaternion and <code>R</code> the quaternion representing the 
     * specified rotation, then the new quaternion will be <code>Q * R</code>. So when transforming a
     * vector <code>v</code> with the new quaternion by using <code>Q * R * v</code>, the
     * rotation added by this method will be applied first!
     * 
     * @param angle
     *              the angle in radians to rotate about the x axis
     * @return this
     */
    public Quaterniond rotateX(double angle) {
        return rotateX(angle, this);
    }

    /* (non-Javadoc)
     * @see org.joml.Quaterniondc#rotateX(double, org.joml.Quaterniond)
     */
    public Quaterniond rotateX(double angle, Quaterniond dest) {
        double sin = Math.sin(angle * 0.5);
        double cos = Math.cosFromSin(sin, angle * 0.5);
        dest.set(w * sin + x * cos,
                 y * cos + z * sin,
                 z * cos - y * sin,
                 w * cos - x * sin);
        return dest;
    }

    /**
     * Apply a rotation to <code>this</code> quaternion rotating the given radians about the y axis.
     * <p>
     * If <code>Q</code> is <code>this</code> quaternion and <code>R</code> the quaternion representing the 
     * specified rotation, then the new quaternion will be <code>Q * R</code>. So when transforming a
     * vector <code>v</code> with the new quaternion by using <code>Q * R * v</code>, the
     * rotation added by this method will be applied first!
     * 
     * @param angle
     *              the angle in radians to rotate about the y axis
     * @return this
     */
    public Quaterniond rotateY(double angle) {
        return rotateY(angle, this);
    }

    /* (non-Javadoc)
     * @see org.joml.Quaterniondc#rotateY(double, org.joml.Quaterniond)
     */
    public Quaterniond rotateY(double angle, Quaterniond dest) {
        double sin = Math.sin(angle * 0.5);
        double cos = Math.cosFromSin(sin, angle * 0.5);
        dest.set(x * cos - z * sin,
                 w * sin + y * cos,
                 x * sin + z * cos,
                 w * cos - y * sin);
        return dest;
    }

    /**
     * Apply a rotation to <code>this</code> quaternion rotating the given radians about the z axis.
     * <p>
     * If <code>Q</code> is <code>this</code> quaternion and <code>R</code> the quaternion representing the 
     * specified rotation, then the new quaternion will be <code>Q * R</code>. So when transforming a
     * vector <code>v</code> with the new quaternion by using <code>Q * R * v</code>, the
     * rotation added by this method will be applied first!
     * 
     * @param angle
     *              the angle in radians to rotate about the z axis
     * @return this
     */
    public Quaterniond rotateZ(double angle) {
        return rotateZ(angle, this);
    }

    /* (non-Javadoc)
     * @see org.joml.Quaterniondc#rotateZ(double, org.joml.Quaterniond)
     */
    public Quaterniond rotateZ(double angle, Quaterniond dest) {
        double sin = Math.sin(angle * 0.5);
        double cos = Math.cosFromSin(sin, angle * 0.5);
        dest.set(x * cos + y * sin,
                 y * cos - x * sin,
                 w * sin + z * cos,
                 w * cos - z * sin);
        return dest;
    }

    /**
     * Apply a rotation to <code>this</code> quaternion rotating the given radians about the local x axis.
     * <p>
     * If <code>Q</code> is <code>this</code> quaternion and <code>R</code> the quaternion representing the 
     * specified rotation, then the new quaternion will be <code>R * Q</code>. So when transforming a
     * vector <code>v</code> with the new quaternion by using <code>R * Q * v</code>, the
     * rotation represented by <code>this</code> will be applied first!
     * 
     * @param angle
     *              the angle in radians to rotate about the local x axis
     * @return this
     */
    public Quaterniond rotateLocalX(double angle) {
        return rotateLocalX(angle, this);
    }

    /* (non-Javadoc)
     * @see org.joml.Quaterniondc#rotateLocalX(double, org.joml.Quaterniond)
     */
    public Quaterniond rotateLocalX(double angle, Quaterniond dest) {
        double hangle = angle * 0.5;
        double s = Math.sin(hangle);
        double c = Math.cosFromSin(s, hangle);
        dest.set(c * x + s * w,
                 c * y - s * z,
                 c * z + s * y,
                 c * w - s * x);
        return dest;
    }

    /**
     * Apply a rotation to <code>this</code> quaternion rotating the given radians about the local y axis.
     * <p>
     * If <code>Q</code> is <code>this</code> quaternion and <code>R</code> the quaternion representing the 
     * specified rotation, then the new quaternion will be <code>R * Q</code>. So when transforming a
     * vector <code>v</code> with the new quaternion by using <code>R * Q * v</code>, the
     * rotation represented by <code>this</code> will be applied first!
     * 
     * @param angle
     *              the angle in radians to rotate about the local y axis
     * @return this
     */
    public Quaterniond rotateLocalY(double angle) {
        return rotateLocalY(angle, this);
    }

    /* (non-Javadoc)
     * @see org.joml.Quaterniondc#rotateLocalY(double, org.joml.Quaterniond)
     */
    public Quaterniond rotateLocalY(double angle, Quaterniond dest) {
        double hangle = angle * 0.5;
        double s = Math.sin(hangle);
        double c = Math.cosFromSin(s, hangle);
        dest.set(c * x + s * z,
                 c * y + s * w,
                 c * z - s * x,
                 c * w - s * y);
        return dest;
    }

    /**
     * Apply a rotation to <code>this</code> quaternion rotating the given radians about the local z axis.
     * <p>
     * If <code>Q</code> is <code>this</code> quaternion and <code>R</code> the quaternion representing the 
     * specified rotation, then the new quaternion will be <code>R * Q</code>. So when transforming a
     * vector <code>v</code> with the new quaternion by using <code>R * Q * v</code>, the
     * rotation represented by <code>this</code> will be applied first!
     * 
     * @param angle
     *              the angle in radians to rotate about the local z axis
     * @return this
     */
    public Quaterniond rotateLocalZ(double angle) {
        return rotateLocalZ(angle, this);
    }

    /* (non-Javadoc)
     * @see org.joml.Quaterniondc#rotateLocalZ(double, org.joml.Quaterniond)
     */
    public Quaterniond rotateLocalZ(double angle, Quaterniond dest) {
        double hangle = angle * 0.5;
        double s = Math.sin(hangle);
        double c = Math.cosFromSin(s, hangle);
        dest.set(c * x - s * y,
                 c * y + s * x,
                 c * z + s * w,
                 c * w - s * z);
        return dest;
    }

    /**
     * Apply a rotation to <code>this</code> quaternion rotating the given radians about the cartesian base unit axes,
     * called the euler angles using rotation sequence <code>XYZ</code>.
     * <p>
     * This method is equivalent to calling: <code>rotateX(angleX).rotateY(angleY).rotateZ(angleZ)</code>
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

    /* (non-Javadoc)
     * @see org.joml.Quaterniondc#rotateXYZ(double, double, double, org.joml.Quaterniond)
     */
    public Quaterniond rotateXYZ(double angleX, double angleY, double angleZ, Quaterniond dest) {
        double sx =  Math.sin(angleX * 0.5);
        double cx =  Math.cosFromSin(sx, angleX * 0.5);
        double sy =  Math.sin(angleY * 0.5);
        double cy =  Math.cosFromSin(sy, angleY * 0.5);
        double sz =  Math.sin(angleZ * 0.5);
        double cz =  Math.cosFromSin(sz, angleZ * 0.5);

        double cycz = cy * cz;
        double sysz = sy * sz;
        double sycz = sy * cz;
        double cysz = cy * sz;
        double w = cx*cycz - sx*sysz;
        double x = sx*cycz + cx*sysz;
        double y = cx*sycz - sx*cysz;
        double z = cx*cysz + sx*sycz;
        // right-multiply
        dest.set(this.w * x + this.x * w + this.y * z - this.z * y,
                 this.w * y - this.x * z + this.y * w + this.z * x,
                 this.w * z + this.x * y - this.y * x + this.z * w,
                 this.w * w - this.x * x - this.y * y - this.z * z);
        return dest;
    }

    /**
     * Apply a rotation to <code>this</code> quaternion rotating the given radians about the cartesian base unit axes,
     * called the euler angles, using the rotation sequence <code>ZYX</code>.
     * <p>
     * This method is equivalent to calling: <code>rotateZ(angleZ).rotateY(angleY).rotateX(angleX)</code>
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

    /* (non-Javadoc)
     * @see org.joml.Quaterniondc#rotateZYX(double, double, double, org.joml.Quaterniond)
     */
    public Quaterniond rotateZYX(double angleZ, double angleY, double angleX, Quaterniond dest) {
        double sx =  Math.sin(angleX * 0.5);
        double cx =  Math.cosFromSin(sx, angleX * 0.5);
        double sy =  Math.sin(angleY * 0.5);
        double cy =  Math.cosFromSin(sy, angleY * 0.5);
        double sz =  Math.sin(angleZ * 0.5);
        double cz =  Math.cosFromSin(sz, angleZ * 0.5);

        double cycz = cy * cz;
        double sysz = sy * sz;
        double sycz = sy * cz;
        double cysz = cy * sz;
        double w = cx*cycz + sx*sysz;
        double x = sx*cycz - cx*sysz;
        double y = cx*sycz + sx*cysz;
        double z = cx*cysz - sx*sycz;
        // right-multiply
        dest.set(this.w * x + this.x * w + this.y * z - this.z * y,
                 this.w * y - this.x * z + this.y * w + this.z * x,
                 this.w * z + this.x * y - this.y * x + this.z * w,
                 this.w * w - this.x * x - this.y * y - this.z * z);
        return dest;
    }

    /**
     * Apply a rotation to <code>this</code> quaternion rotating the given radians about the cartesian base unit axes,
     * called the euler angles, using the rotation sequence <code>YXZ</code>.
     * <p>
     * This method is equivalent to calling: <code>rotateY(angleY).rotateX(angleX).rotateZ(angleZ)</code>
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

    /* (non-Javadoc)
     * @see org.joml.Quaterniondc#rotateYXZ(double, double, double, org.joml.Quaterniond)
     */
    public Quaterniond rotateYXZ(double angleY, double angleX, double angleZ, Quaterniond dest) {
        double sx =  Math.sin(angleX * 0.5);
        double cx =  Math.cosFromSin(sx, angleX * 0.5);
        double sy =  Math.sin(angleY * 0.5);
        double cy =  Math.cosFromSin(sy, angleY * 0.5);
        double sz =  Math.sin(angleZ * 0.5);
        double cz =  Math.cosFromSin(sz, angleZ * 0.5);

        double yx = cy * sx;
        double yy = sy * cx;
        double yz = sy * sx;
        double yw = cy * cx;
        double x = yx * cz + yy * sz;
        double y = yy * cz - yx * sz;
        double z = yw * sz - yz * cz;
        double w = yw * cz + yz * sz;
        // right-multiply
        dest.set(this.w * x + this.x * w + this.y * z - this.z * y,
                 this.w * y - this.x * z + this.y * w + this.z * x,
                 this.w * z + this.x * y - this.y * x + this.z * w,
                 this.w * w - this.x * x - this.y * y - this.z * z);
        return dest;
    }

    /* (non-Javadoc)
     * @see org.joml.Quaterniondc#getEulerAnglesXYZ(org.joml.Vector3d)
     */
    public Vector3d getEulerAnglesXYZ(Vector3d eulerAngles) {
        eulerAngles.x = Math.atan2(2.0 * (x*w - y*z), 1.0 - 2.0 * (x*x + y*y));
        eulerAngles.y = Math.asin(2.0 * (x*z + y*w));
        eulerAngles.z = Math.atan2(2.0 * (z*w - x*y), 1.0 - 2.0 * (y*y + z*z));
        return eulerAngles;
    }

    /* (non-Javadoc)
     * @see org.joml.Quaterniondc#rotateAxis(double, double, double, double, org.joml.Quaterniond)
     */
    public Quaterniond rotateAxis(double angle, double axisX, double axisY, double axisZ, Quaterniond dest) {
        double hangle = angle / 2.0;
        double sinAngle = Math.sin(hangle);
        double invVLength = 1.0 / Math.sqrt(axisX * axisX + axisY * axisY + axisZ * axisZ);

        double rx = axisX * invVLength * sinAngle;
        double ry = axisY * invVLength * sinAngle;
        double rz = axisZ * invVLength * sinAngle;
        double rw = Math.cosFromSin(sinAngle, hangle);

        dest.set(w * rx + x * rw + y * rz - z * ry,
                 w * ry - x * rz + y * rw + z * rx,
                 w * rz + x * ry - y * rx + z * rw,
                 w * rw - x * rx - y * ry - z * rz);
        return dest;
    }

    /* (non-Javadoc)
     * @see org.joml.Quaterniondc#rotateAxis(double, org.joml.Vector3dc, org.joml.Quaterniond)
     */
    public Quaterniond rotateAxis(double angle, Vector3dc axis, Quaterniond dest) {
        return rotateAxis(angle, axis.x(), axis.y(), axis.z(), dest);
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
    public Quaterniond rotateAxis(double angle, Vector3dc axis) {
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

    /* (non-Javadoc)
     * @see org.joml.Quaterniondc#positiveX(org.joml.Vector3d)
     */
    public Vector3d positiveX(Vector3d dir) {
        double invNorm = 1.0 / (x * x + y * y + z * z + w * w);
        double nx = -x * invNorm;
        double ny = -y * invNorm;
        double nz = -z * invNorm;
        double nw =  w * invNorm;
        double dy = ny + ny;
        double dz = nz + nz;
        dir.x = -ny * dy - nz * dz + 1.0;
        dir.y =  nx * dy + nw * dz;
        dir.z =  nx * dz - nw * dy;
        return dir;
    }

    /* (non-Javadoc)
     * @see org.joml.Quaterniondc#normalizedPositiveX(org.joml.Vector3d)
     */
    public Vector3d normalizedPositiveX(Vector3d dir) {
        double dy = y + y;
        double dz = z + z;
        dir.x = -y * dy - z * dz + 1.0;
        dir.y =  x * dy - w * dz;
        dir.z =  x * dz + w * dy;
        return dir;
    }

    /* (non-Javadoc)
     * @see org.joml.Quaterniondc#positiveY(org.joml.Vector3d)
     */
    public Vector3d positiveY(Vector3d dir) {
        double invNorm = 1.0 / (x * x + y * y + z * z + w * w);
        double nx = -x * invNorm;
        double ny = -y * invNorm;
        double nz = -z * invNorm;
        double nw =  w * invNorm;
        double dx = nx + nx;
        double dy = ny + ny;
        double dz = nz + nz;
        dir.x =  nx * dy - nw * dz;
        dir.y = -nx * dx - nz * dz + 1.0;
        dir.z =  ny * dz + nw * dx;
        return dir;
    }

    /* (non-Javadoc)
     * @see org.joml.Quaterniondc#normalizedPositiveY(org.joml.Vector3d)
     */
    public Vector3d normalizedPositiveY(Vector3d dir) {
        double dx = x + x;
        double dy = y + y;
        double dz = z + z;
        dir.x =  x * dy + w * dz;
        dir.y = -x * dx - z * dz + 1.0;
        dir.z =  y * dz - w * dx;
        return dir;
    }

    /* (non-Javadoc)
     * @see org.joml.Quaterniondc#positiveZ(org.joml.Vector3d)
     */
    public Vector3d positiveZ(Vector3d dir) {
        double invNorm = 1.0 / (x * x + y * y + z * z + w * w);
        double nx = -x * invNorm;
        double ny = -y * invNorm;
        double nz = -z * invNorm;
        double nw =  w * invNorm;
        double dx = nx + nx;
        double dy = ny + ny;
        double dz = nz + nz;
        dir.x =  nx * dz + nw * dy;
        dir.y =  ny * dz - nw * dx;
        dir.z = -nx * dx - ny * dy + 1.0;
        return dir;
    }

    /* (non-Javadoc)
     * @see org.joml.Quaterniondc#normalizedPositiveZ(org.joml.Vector3d)
     */
    public Vector3d normalizedPositiveZ(Vector3d dir) {
        double dx = x + x;
        double dy = y + y;
        double dz = z + z;
        dir.x =  x * dz - w * dy;
        dir.y =  y * dz + w * dx;
        dir.z = -x * dx - y * dy + 1.0;
        return dir;
    }

}
