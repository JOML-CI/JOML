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
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Contains the definition and functions for rotations expressed as
 * 4-dimensional vectors
 *
 * @author Richard Greenlees
 * @author Kai Burjack
 */
public class Quaterniond implements Externalizable {

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
     * Create a new {@link Quaterniond} and initialize its components to the same values as the given {@link Quaterniond}.
     * 
     * @param source
     *          the {@link Quaterniond} to take the component values from
     */
    public Quaterniond(Quaterniond source) {
        x = source.x;
        y = source.y;
        z = source.z;
        w = source.w;
    }

    /**
     * Create a new {@link Quaterniond} and initialize its components to the same values as the given {@link Quaternionf}.
     * 
     * @param source
     *          the {@link Quaternionf} to take the component values from
     */
    public Quaterniond(Quaternionf source) {
        x = source.x;
        y = source.y;
        z = source.z;
        w = source.w;
    }

    /**
     * Normalize this quaternion.
     * 
     * @return this
     */
    public Quaterniond normalize() {
        double norm = Math.sqrt(x * x + y * y + z * z + w * w);
        x /= norm;
        y /= norm;
        z /= norm;
        w /= norm;
        return this;
    }

    /**
     * Normalize this quaternion and store the result in <code>dest</code>.
     * 
     * @param dest
     *          will hold the result
     * @return this
     */
    public Quaterniond normalize(Quaterniond dest) {
        double norm = Math.sqrt(x * x + y * y + z * z + w * w);
        dest.x = x / norm;
        dest.y = y / norm;
        dest.z = z / norm;
        dest.w = w / norm;
        return this;
    }

    /**
     * Add <code>q2</code> to this quaternion.
     * 
     * @param q2
     *          the quaternion to add to this
     * @return this
     */
    public Quaterniond add(Quaterniond q2) {
        x += q2.x;
        y += q2.y;
        z += q2.z;
        w += q2.w;
        return this;
    }

    /**
     * Add <code>q2</code> to this quaternion and store the result in <code>dest</code>.
     * 
     * @param q2
     *          the quaternion to add to this
     * @param dest
     *          will hold the result
     * @return this
     */
    public Quaterniond add(Quaterniond q2, Quaterniond dest) {
        dest.x = x + q2.x;
        dest.y = y + q2.y;
        dest.z = z + q2.z;
        dest.w = w + q2.w;
        return this;
    }

    /**
     * Return the dot product of this {@link Quaterniond} and <code>otherQuat</code>.
     * 
     * @param otherQuat
     *          the other quaternion
     * @return the dot product
     */
    public double dot(Quaterniond otherQuat) {
        return this.x * otherQuat.x + this.y * otherQuat.y + this.z * otherQuat.z + this.w * otherQuat.w;
    }

    /**
     * Return the angle in radians represented by this quaternion rotation.
     * 
     * @return the angle in radians
     */
    public double angle() {
        double angle = 2.0 * Math.acos(w);
        return angle <= Math.PI ? angle : 2.0 * Math.PI - angle;
    }

    /**
     * Set the given destination matrix to the rotation represented by <code>this</code>.
     * 
     * @param dest
     *          the matrix to write the rotation into
     * @return this
     */
    public Quaterniond get(Matrix3d dest) {
        double q00 = 2.0 * x * x;
        double q11 = 2.0 * y * y;
        double q22 = 2.0 * z * z;

        double q01 = 2.0 * x * y;
        double q02 = 2.0 * x * z;
        double q03 = 2.0 * x * w;

        double q12 = 2.0 * y * z;
        double q13 = 2.0 * y * w;

        double q23 = 2.0 * z * w;

        dest.m00 = 1.0 - q11 - q22;
        dest.m01 = q01 + q23;
        dest.m02 = q02 - q13;
        dest.m10 = q01 - q23;
        dest.m11 = 1.0 - q22 - q00;
        dest.m12 = q12 + q03;
        dest.m20 = q02 + q13;
        dest.m21 = q12 - q03;
        dest.m22 = 1.0 - q11 - q00;
        return this;
    }

    /**
     * Set the given destination matrix to the rotation represented by <code>this</code>.
     * 
     * @param dest
     *          the matrix to write the rotation into
     * @return this
     */
    public Quaterniond get(Matrix3f dest) {
        double q00 = 2.0 * x * x;
        double q11 = 2.0 * y * y;
        double q22 = 2.0 * z * z;

        double q01 = 2.0 * x * y;
        double q02 = 2.0 * x * z;
        double q03 = 2.0 * x * w;

        double q12 = 2.0 * y * z;
        double q13 = 2.0 * y * w;

        double q23 = 2.0 * z * w;

        dest.m00 = (float) (1.0 - q11 - q22);
        dest.m01 = (float) (q01 + q23);
        dest.m02 = (float) (q02 - q13);
        dest.m10 = (float) (q01 - q23);
        dest.m11 = (float) (1.0 - q22 - q00);
        dest.m12 = (float) (q12 + q03);
        dest.m20 = (float) (q02 + q13);
        dest.m21 = (float) (q12 - q03);
        dest.m22 = (float) (1.0 - q11 - q00);
        return this;
    }

    /**
     * Set the given destination matrix to the rotation represented by <code>this</code>.
     * 
     * @param dest
     *          the matrix to write the rotation into
     * @return this
     */
    public Quaterniond get(Matrix4d dest) {
        double q00 = 2.0 * x * x;
        double q11 = 2.0 * y * y;
        double q22 = 2.0 * z * z;

        double q01 = 2.0 * x * y;
        double q02 = 2.0 * x * z;
        double q03 = 2.0 * x * w;

        double q12 = 2.0 * y * z;
        double q13 = 2.0 * y * w;

        double q23 = 2.0 * z * w;

        dest.m00 = 1.0 - q11 - q22;
        dest.m01 = q01 + q23;
        dest.m02 = q02 - q13;
        dest.m03 = 0.0;
        dest.m10 = q01 - q23;
        dest.m11 = 1.0 - q22 - q00;
        dest.m12 = q12 + q03;
        dest.m13 = 0.0;
        dest.m20 = q02 + q13;
        dest.m21 = q12 - q03;
        dest.m22 = 1.0 - q11 - q00;
        dest.m30 = 0.0;
        dest.m31 = 0.0;
        dest.m32 = 0.0;
        dest.m33 = 1.0;
        return this;
    }

    /**
     * Set the given {@link Quaterniond} to the values of <code>this</code>.
     * 
     * @see #set(Quaterniond)
     * 
     * @param dest
     *          the {@link Quaterniond} to set
     * @return this
     */
    public Quaterniond get(Quaterniond dest) {
        dest.set(this);
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
    public Quaterniond set(Quaterniond q) {
        x = q.x;
        y = q.y;
        z = q.z;
        w = q.w;
        return this;
    }

    /**
     * Set this quaternion to be a copy of q.
     * 
     * @param q
     *          the {@link Quaternionf} to copy
     * @return this
     */
    public Quaterniond set(Quaternionf q) {
        x = q.x;
        y = q.y;
        z = q.z;
        w = q.w;
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
     * Set this quaternion to be a representation of the supplied axis and
     * angle (in radians).
     * 
     * @param angle
     *          the angle in radians
     * @param axisX
     *          the x-coordinate of the rotation axis
     * @param axisY
     *          the y-coordinate of the rotation axis
     * @param axisZ
     *          the z-coordinate of the rotation axis
     * @return this
     */
    public Quaterniond setAngleAxis(double angle, double axisX, double axisY, double axisZ) {
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
     * @param angle
     *          the angle in radians
     * @param axis
     *          the rotation axis
     * @return this
     */
    public Quaterniond setAngleAxis(double angle, Vector3d axis) {
        return setAngleAxis(angle, axis.x, axis.y, axis.z);
    }

    /**
     * Set this quaternion to be a representation of the rotational component of the given matrix.
     * 
     * @param mat
     *          the matrix whose rotational component is used to set this quaternion
     * @return this
     */
    public Quaterniond set(Matrix4d mat) {
        double t;
        double tr = mat.m00 + mat.m11 + mat.m22;
        if (tr >= 0.0) {
            t = Math.sqrt(tr + 1.0);
            w = t * 0.5;
            t = 0.5 / t;
            x = (mat.m12 - mat.m21) * t;
            y = (mat.m20 - mat.m02) * t;
            z = (mat.m01 - mat.m10) * t;
        } else {
            if (mat.m00 >= mat.m11 && mat.m00 >= mat.m22) {
                t = Math.sqrt(mat.m00 - (mat.m11 + mat.m22) + 1.0);
                x = t * 0.5;
                t = 0.5 / t;
                y = (mat.m10 + mat.m01) * t;
                z = (mat.m02 + mat.m20) * t;
                w = (mat.m12 - mat.m21) * t;
            } else if (mat.m11 > mat.m22) {
                t = Math.sqrt(mat.m11 - (mat.m22 + mat.m00) + 1.0);
                y = t * 0.5;
                t = 0.5 / t;
                z = (mat.m21 + mat.m12) * t;
                x = (mat.m10 + mat.m01) * t;
                w = (mat.m20 - mat.m02) * t;
            } else {
                t = Math.sqrt(mat.m22 - (mat.m00 + mat.m11) + 1.0);
                z = t * 0.5;
                t = 0.5 / t;
                x = (mat.m02 + mat.m20) * t;
                y = (mat.m21 + mat.m12) * t;
                w = (mat.m01 - mat.m10) * t;
            }
        }
        return this;
    }

    /**
     * Set this quaternion to be a representation of the rotational component of the given matrix.
     * 
     * @param mat
     *          the matrix whose rotational component is used to set this quaternion
     * @return this
     */
    public Quaterniond set(Matrix4f mat) {
        double t;
        double tr = mat.m00 + mat.m11 + mat.m22;
        if (tr >= 0.0) {
            t = Math.sqrt(tr + 1.0);
            w = t * 0.5;
            t = 0.5 / t;
            x = (mat.m12 - mat.m21) * t;
            y = (mat.m20 - mat.m02) * t;
            z = (mat.m01 - mat.m10) * t;
        } else {
            if (mat.m00 >= mat.m11 && mat.m00 >= mat.m22) {
                t = Math.sqrt(mat.m00 - (mat.m11 + mat.m22) + 1.0);
                x = t * 0.5;
                t = 0.5 / t;
                y = (mat.m10 + mat.m01) * t;
                z = (mat.m02 + mat.m20) * t;
                w = (mat.m12 - mat.m21) * t;
            } else if (mat.m11 > mat.m22) {
                t = Math.sqrt(mat.m11 - (mat.m22 + mat.m00) + 1.0);
                y = t * 0.5;
                t = 0.5 / t;
                z = (mat.m21 + mat.m12) * t;
                x = (mat.m10 + mat.m01) * t;
                w = (mat.m20 - mat.m02) * t;
            } else {
                t = Math.sqrt(mat.m22 - (mat.m00 + mat.m11) + 1.0);
                z = t * 0.5;
                t = 0.5 / t;
                x = (mat.m02 + mat.m20) * t;
                y = (mat.m21 + mat.m12) * t;
                w = (mat.m01 - mat.m10) * t;
            }
        }
        return this;
    }

    /**
     * Set this quaternion to be a representation of the rotational component of the given matrix.
     * 
     * @param mat
     *          the matrix whose rotational component is used to set this quaternion
     * @return this
     */
    public Quaterniond set(Matrix3d mat) {
        double t;
        double tr = mat.m00 + mat.m11 + mat.m22;
        if (tr >= 0.0) {
            t = Math.sqrt(tr + 1.0);
            w = t * 0.5;
            t = 0.5 / t;
            x = (mat.m12 - mat.m21) * t;
            y = (mat.m20 - mat.m02) * t;
            z = (mat.m01 - mat.m10) * t;
        } else {
            if (mat.m00 >= mat.m11 && mat.m00 >= mat.m22) {
                t = Math.sqrt(mat.m00 - (mat.m11 + mat.m22) + 1.0);
                x = t * 0.5;
                t = 0.5 / t;
                y = (mat.m10 + mat.m01) * t;
                z = (mat.m02 + mat.m20) * t;
                w = (mat.m12 - mat.m21) * t;
            } else if (mat.m11 > mat.m22) {
                t = Math.sqrt(mat.m11 - (mat.m22 + mat.m00) + 1.0);
                y = t * 0.5;
                t = 0.5 / t;
                z = (mat.m21 + mat.m12) * t;
                x = (mat.m10 + mat.m01) * t;
                w = (mat.m20 - mat.m02) * t;
            } else {
                t = Math.sqrt(mat.m22 - (mat.m00 + mat.m11) + 1.0);
                z = t * 0.5;
                t = 0.5 / t;
                x = (mat.m02 + mat.m20) * t;
                y = (mat.m21 + mat.m12) * t;
                w = (mat.m01 - mat.m10) * t;
            }
        }
        return this;
    }

    /**
     * Set this quaternion to be a representation of the rotational component of the given matrix.
     * 
     * @param mat
     *          the matrix whose rotational component is used to set this quaternion
     * @return this
     */
    public Quaterniond set(Matrix3f mat) {
        double t;
        double tr = mat.m00 + mat.m11 + mat.m22;
        if (tr >= 0.0) {
            t = Math.sqrt(tr + 1.0);
            w = t * 0.5;
            t = 0.5 / t;
            x = (mat.m12 - mat.m21) * t;
            y = (mat.m20 - mat.m02) * t;
            z = (mat.m01 - mat.m10) * t;
        } else {
            if (mat.m00 >= mat.m11 && mat.m00 >= mat.m22) {
                t = Math.sqrt(mat.m00 - (mat.m11 + mat.m22) + 1.0);
                x = t * 0.5;
                t = 0.5 / t;
                y = (mat.m10 + mat.m01) * t;
                z = (mat.m02 + mat.m20) * t;
                w = (mat.m12 - mat.m21) * t;
            } else if (mat.m11 > mat.m22) {
                t = Math.sqrt(mat.m11 - (mat.m22 + mat.m00) + 1.0);
                y = t * 0.5;
                t = 0.5 / t;
                z = (mat.m21 + mat.m12) * t;
                x = (mat.m10 + mat.m01) * t;
                w = (mat.m20 - mat.m02) * t;
            } else {
                t = Math.sqrt(mat.m22 - (mat.m00 + mat.m11) + 1.0);
                z = t * 0.5;
                t = 0.5 / t;
                x = (mat.m02 + mat.m20) * t;
                y = (mat.m21 + mat.m12) * t;
                w = (mat.m01 - mat.m10) * t;
            }
        }
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
    public Quaterniond fromAxisAngleRad(Vector3d axis, double angle) {
        double hangle = angle / 2.0;
        double sinAngle = Math.sin(hangle);
        double vLength = axis.length();

        x = axis.x / vLength * sinAngle;
        y = axis.y / vLength * sinAngle;
        z = axis.z / vLength * sinAngle;
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
    public Quaterniond fromAxisAngleDeg(Vector3d axis, double angle) {
        double hangle = angle / 2.0;
        double sinAngle = Math.sin(hangle);
        double vLength = axis.length();

        x = axis.x / vLength * sinAngle;
        y = axis.y / vLength * sinAngle;
        z = axis.z / vLength * sinAngle;
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
    public Quaterniond mul(Quaterniond q) {
        return mul(q, this);
    }

    /**
     * Multiply this quaternion by <code>q</code> and store the result in <code>dest</code>.
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
     *            the quaternion to multiply <code>this</code> by
     * @param dest
     *            will hold the result
     * @return this
     */
    public Quaterniond mul(Quaterniond q, Quaterniond dest) {
        if (this != dest && q != dest) {
            dest.x = w * q.x + x * q.w + y * q.z - z * q.y;
            dest.y = w * q.y - x * q.z + y * q.w + z * q.x;
            dest.z = w * q.z + x * q.y - y * q.x + z * q.w;
            dest.w = w * q.w - x * q.x - y * q.y - z * q.z;
        } else {
            dest.set(w * q.x + x * q.w + y * q.z - z * q.y,
                     w * q.y - x * q.z + y * q.w + z * q.x,
                     w * q.z + x * q.y - y * q.x + z * q.w,
                     w * q.w - x * q.x - y * q.y - z * q.z);
        }
        return this;
    }

    /**
     * Transform the given vector by this quaternion.
     * This will apply the rotation described by this quaternion to the given vector.
     * 
     * @param vec
     *          the vector to transform
     * @return this
     */
    public Quaterniond transform(Vector3d vec){
        return transform(vec, vec);
    }

    /**
     * Transform the given vector by this quaternion and store the result in <code>dest</code>.
     * This will apply the rotation described by this quaternion to the given vector.
     * 
     * @param vec
     *          the vector to transform
     * @param dest
     *          will hold the result
     * @return this
     */
    public Quaterniond transform(Vector3d vec, Vector3d dest) {
        double num = x * 2.0;
        double num2 = y * 2.0;
        double num3 = z * 2.0;
        double num4 = x * num;
        double num5 = y * num2;
        double num6 = z * num3;
        double num7 = x * num2;
        double num8 = x * num3;
        double num9 = y * num3;
        double num10 = w * num;
        double num11 = w * num2;
        double num12 = w * num3;
        dest.set((1.0 - (num5 + num6)) * vec.x + (num7 - num12) * vec.y + (num8 + num11) * vec.z,
                 (num7 + num12) * vec.x + (1.0 - (num4 + num6)) * vec.y + (num9 - num10) * vec.z,
                 (num8 - num11) * vec.x + (num9 + num10) * vec.y + (1.0 - (num4 + num5)) * vec.z);
        return this;
    }

    /**
     * Transform the given vector by this quaternion.
     * This will apply the rotation described by this quaternion to the given vector.
     * <p>
     * Only the first three components of the given 4D vector are being used and modified.
     * 
     * @param vec
     *          the vector to transform
     * @return this
     */
    public Quaterniond transform(Vector4d vec){
        return transform(vec, vec);
    }

    /**
     * Transform the given vector by this quaternion and store the result in <code>dest</code>.
     * This will apply the rotation described by this quaternion to the given vector.
     * <p>
     * Only the first three components of the given 4D vector are being used and set on the destination.
     * 
     * @param vec
     *          the vector to transform
     * @param dest
     *          will hold the result
     * @return this
     */
    public Quaterniond transform(Vector4d vec, Vector4d dest) {
        double num = x * 2.0;
        double num2 = y * 2.0;
        double num3 = z * 2.0;
        double num4 = x * num;
        double num5 = y * num2;
        double num6 = z * num3;
        double num7 = x * num2;
        double num8 = x * num3;
        double num9 = y * num3;
        double num10 = w * num;
        double num11 = w * num2;
        double num12 = w * num3;
        dest.set((1.0 - (num5 + num6)) * vec.x + (num7 - num12) * vec.y + (num8 + num11) * vec.z,
                 (num7 + num12) * vec.x + (1.0 - (num4 + num6)) * vec.y + (num9 - num10) * vec.z,
                 (num8 - num11) * vec.x + (num9 + num10) * vec.y + (1.0 - (num4 + num5)) * vec.z,
                 dest.w);
        return this;
    }

    /**
     * Invert this quaternion and store the {@link #normalize() normalized} result in <code>dest</code>.
     * 
     * @param dest
     *          will hold the result
     * @return this
     */
    public Quaterniond invert(Quaterniond dest) {
        double norm = x * x + y * y + z * z + w * w;
        dest.x = -x / norm;
        dest.y = -y / norm;
        dest.z = -z / norm;
        dest.w = w / norm;
        return this;
    }

    /**
     * Invert this quaternion by assuming that it is already {@link #normalize() normalized} and store the result in <code>dest</code>.
     * 
     * @param dest
     *          will hold the result
     * @return this
     */
    public Quaterniond unitInvert(Quaterniond dest) {
        dest.x = -x;
        dest.y = -y;
        dest.z = -z;
        dest.w = w;
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
     * Divide <code>this</code> quaternion by <code>b</code> and store the result in <code>dest</code>.
     * <p>
     * The division expressed using the inverse is performed in the following way:
     * <p>
     * <tt>dest = this * b^-1</tt>, where <tt>b^-1</tt> is the inverse of <code>b</code>.
     * 
     * @param b
     *          the {@link Quaterniond} to divide this by
     * @param dest
     *          will hold the result
     * @return this
     */
    public Quaterniond div(Quaterniond b, Quaterniond dest) {
        double norm = b.x * b.x + b.y * b.y + b.z * b.z + b.w * b.w;
        double x = -b.x / norm;
        double y = -b.y / norm;
        double z = -b.z / norm;
        double w = b.w / norm;
        dest.set(this.w * x + this.x * w + this.y * z - this.z * y,
                 this.w * y - this.x * z + this.y * w + this.z * x,
                 this.w * z + this.x * y - this.y * x + this.z * w,
                 this.w * w - this.x * x - this.y * y - this.z * z);
        return this;
    }

    /**
     * Divide <code>this</code> quaternion by <code>b</code>.
     * <p>
     * The division expressed using the inverse is performed in the following way:
     * <p>
     * <tt>this = this * b^-1</tt>, where <tt>b^-1</tt> is the inverse of <code>b</code>.
     * 
     * @param b
     *          the {@link Quaterniond} to divide this by
     * @return this
     */
    public Quaterniond div(Quaterniond b) {
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
     * Conjugate this quaternion and store the result in <code>dest</code>.
     * 
     * @param dest
     *          will hold the result
     * @return this
     */
    public Quaterniond conjugate(Quaterniond dest) {
        dest.x = -x;
        dest.y = -y;
        dest.z = -z;
        dest.w = w;
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
     * Return the length of this quaternion.
     * 
     * @return the length
     */
    public double length() {
        return x * x + y * y + z * z + w * w;
    }

    /**
     * Calculate this quaternion using the supplied pitch (rotation about X), yaw (rotation about Y) and roll (rotation about Z) angles
     * (in radians) with rotation order XYZ.
     * <p>
     * This method implements the solution outlined in <a href="http://gamedev.stackexchange.com/questions/13436/glm-euler-angles-to-quaternion#answer-13446">this stackexchange answer</a>.
     * 
     * @param rotationAboutX
     *          the angle in radians to rotate about x
     * @param rotationAboutY
     *          the angle in radians to rotate about y
     * @param rotationAboutZ
     *          the angle in radians to rotate about z
     * @return this
     */
    public Quaterniond setEulerAnglesXYZ(double rotationAboutX, double rotationAboutY, double rotationAboutZ) {
        double sx = Math.sin(rotationAboutX * 0.5);
        double cx = Math.cos(rotationAboutX * 0.5);
        double sy = Math.sin(rotationAboutY * 0.5);
        double cy = Math.cos(rotationAboutY * 0.5);
        double sz = Math.sin(rotationAboutZ * 0.5);
        double cz = Math.cos(rotationAboutZ * 0.5);

        x = cx*cy*cz + sx*sy*sz;
        y = sx*cy*cz - cx*sy*sz;
        z = cx*sy*cz + sx*cy*sz;
        w = cx*cy*sz - sx*sy*cz;

        return this;
    }

    /**
     * Calculate this quaternion using the supplied pitch (rotation about X), yaw (rotation about Y) and roll (rotation about Z) angles
     * (in radians) with rotation order ZYX.
     * <p>
     * This method implements the solution outlined in <a href="http://gamedev.stackexchange.com/questions/13436/glm-euler-angles-to-quaternion#answer-13446">this stackexchange answer</a>.
     * 
     * @param rotationAboutX
     *          the angle in radians to rotate about x
     * @param rotationAboutY
     *          the angle in radians to rotate about y
     * @param rotationAboutZ
     *          the angle in radians to rotate about z
     * @return this
     */
    public Quaterniond setEulerAnglesZYX(double rotationAboutX, double rotationAboutY, double rotationAboutZ) {
        double sx = Math.sin(rotationAboutX * 0.5);
        double cx = Math.cos(rotationAboutX * 0.5);
        double sy = Math.sin(rotationAboutY * 0.5);
        double cy = Math.cos(rotationAboutY * 0.5);
        double sz = Math.sin(rotationAboutZ * 0.5);
        double cz = Math.cos(rotationAboutZ * 0.5);

        x = cx*cy*cz - sx*sy*sz;
        y = sx*cy*cz + cx*sy*sz;
        z = cx*sy*cz - sx*cy*sz;
        w = cx*cy*sz + sx*sy*cz;

        return this;
    }

    /**
     * Interpolate between <code>this</code> quaternion and the specified
     * <code>target</code> using sperical linear interpolation using the specified interpolation factor <code>alpha</code>.
     * 
     * @param target
     *          the target of the interpolation, which should be reached with <tt>alpha = 1.0</tt>
     * @param alpha
     *          the interpolation factor, within <tt>[0..1]</tt>
     * @return this
     */
    public Quaterniond slerp(Quaterniond target, double alpha) {
        return slerp(target, alpha, this);
    }

    /**
     * Interpolate between <code>this</code> quaternion and the specified
     * <code>target</code> using sperical linear interpolation using the specified interpolation factor <code>alpha</code>,
     * and store the result in <code>dest</code>.
     * 
     * @param target
     *          the target of the interpolation, which should be reached with <tt>alpha = 1.0</tt>
     * @param alpha
     *          the interpolation factor, within <tt>[0..1]</tt>
     * @param dest
     *          will hold the result
     * @return this
     */
    public Quaterniond slerp(Quaterniond target, double alpha, Quaterniond dest) {
        double dot = x * target.x + y * target.y + z * target.z + w * target.w;
        // Thresholds to accelerate computations
        double nlerpThreshold = 0.95;
        double sinThetaThreshold = 0.01;
        // Check if we must use slerp and cannot get away with simple linear interpolation
        if (dot > -nlerpThreshold && dot < nlerpThreshold) {
            double absDot = Math.abs(dot);
            double theta = Math.acos(absDot);
            double sinTheta = 1.0f;
            // Check if we need to compute sinTheta
            if (dot < -sinThetaThreshold || dot > sinThetaThreshold) {
                sinTheta = Math.sin(theta);
            }
            double q1 = Math.sin(theta * (1.0f - alpha));
            double q2 = Math.sin(theta * alpha);
            dest.x = (q1 * x + q2 * target.x) / sinTheta;
            dest.y = (q1 * y + q2 * target.y) / sinTheta;
            dest.z = (q1 * z + q2 * target.z) / sinTheta;
            dest.w = (q1 * w + q2 * target.w) / sinTheta; 
        } else {
            nlerp(target, alpha, dest);
        }
        return this;
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
    public Quaterniond nlerp(Quaterniond q, double factor) {
        return nlerp(q, factor, this);
    }

    /**
     * Compute a linear (non-spherical) interpolation of <code>this</code> and the given quaternion <code>q</code>
     * and store the result in <code>dest</code>.
     * 
     * @param q
     *          the other quaternion
     * @param factor
     *          the interpolation factor. It is between 0.0 and 1.0
     * @param dest
     *          will hold the result
     * @return this
     */
    public Quaterniond nlerp(Quaterniond q, double factor, Quaterniond dest) {
        double dot = this.dot(q);
        double blendI = 1.0 - factor;
        if (dot < 0.0) {
            dest.w = blendI * w + factor * -q.w;
            dest.x = blendI * x + factor * -q.x;
            dest.y = blendI * y + factor * -q.y;
            dest.z = blendI * z + factor * -q.z;
        } else {
            dest.w = blendI * w + factor * q.w;
            dest.x = blendI * x + factor * q.x;
            dest.y = blendI * y + factor * q.y;
            dest.z = blendI * z + factor * q.z;
        }
        dest.normalize();
        return this;
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
    public Quaterniond lookRotate(Vector3d dir, Vector3d up) {
        return lookRotate(dir.x, dir.y, dir.z, up.x, up.y, up.z, this);
    }

    /**
     * Apply a rotation to this quaternion that maps the given direction to the positive Z axis, and store the result in <code>dest</code>.
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
     * @param dest
     *              will hold the result
     * @return this
     */
    public Quaterniond lookRotate(Vector3d dir, Vector3d up, Quaterniond dest) {
        return lookRotate(dir.x, dir.y, dir.z, up.x, up.y, up.z, dest);
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

    /**
     * Apply a rotation to this quaternion that maps the given direction to the positive Z axis, and store the result in <code>dest</code>.
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
     * @return this
     */
    public Quaterniond lookRotate(double dirX, double dirY, double dirZ, double upX, double upY, double upZ, Quaterniond dest) {
        // Normalize direction
        double dirLength = Math.sqrt(dirX * dirX + dirY * dirY + dirZ * dirZ);
        double dirnX = dirX / dirLength;
        double dirnY = dirY / dirLength;
        double dirnZ = dirZ / dirLength;
        // left = up x dir
        double leftX, leftY, leftZ;
        leftX = upY * dirnZ - upZ * dirnY;
        leftY = upZ * dirnX - upX * dirnZ;
        leftZ = upX * dirnY - upY * dirnX;
        // normalize left
        double leftLength = Math.sqrt(leftX * leftX + leftY * leftY + leftZ * leftZ);
        leftX /= leftLength;
        leftY /= leftLength;
        leftZ /= leftLength;
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
        return this;
    }

    /**
     * Return a string representation of this quaternion.
     * <p>
     * This method creates a new {@link DecimalFormat} on every invocation with the format string "<tt> 0.000E0;-</tt>".
     * 
     * @return the string representation
     */
    public String toString() {
        DecimalFormat formatter = new DecimalFormat(" 0.000E0;-"); //$NON-NLS-1$
        return toString(formatter).replaceAll("E(\\d+)", "E+$1"); //$NON-NLS-1$ //$NON-NLS-2$
    }

    /**
     * Return a string representation of this quaternion by formatting the components with the given {@link NumberFormat}.
     * 
     * @param formatter
     *          the {@link NumberFormat} used to format the quaternion components with
     * @return the string representation
     */
    public String toString(NumberFormat formatter) {
        return "(" + formatter.format(x) + " " + formatter.format(y) + " " + formatter.format(z) + " " + formatter.format(w) + ")"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
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
    public Quaterniond difference(Quaterniond other) {
        return difference(other, this);
    }

    /**
     * Compute the difference between <code>this</code> and the <code>other</code> quaternion
     * and store the result in <code>dest</code>.
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
     * @param dest
     *          will hold the result
     * @return this
     */
    public Quaterniond difference(Quaterniond other, Quaterniond dest) {
        double norm = x * x + y * y + z * z + w * w;
        double x = -this.x / norm;
        double y = -this.y / norm;
        double z = -this.z / norm;
        double w = this.w / norm;
        dest.set(w * other.x + x * other.w + y * other.z - z * other.y,
                 w * other.y - x * other.z + y * other.w + z * other.x,
                 w * other.z + x * other.y - y * other.x + z * other.w,
                 w * other.w - x * other.x - y * other.y - z * other.z);
        return this;
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
        double fromLength = Math.sqrt(fromDirX * fromDirX + fromDirY * fromDirY + fromDirZ * fromDirZ);
        double fromX = fromDirX / fromLength;
        double fromY = fromDirY / fromLength;
        double fromZ = fromDirZ / fromLength;
        double toLength = Math.sqrt(toDirX * toDirX + toDirY * toDirY + toDirZ * toDirZ);
        double toX = toDirX / toLength;
        double toY = toDirY / toLength;
        double toZ = toDirZ / toLength;
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
            double norm = Math.sqrt(x * x + y * y + z * z + w * w);
            x /= norm;
            y /= norm;
            z /= norm;
            w /= norm;
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
    public Quaterniond rotationTo(Vector3d fromDir, Vector3d toDir) {
        return rotationTo(fromDir.x, fromDir.y, fromDir.z, toDir.x, toDir.y, toDir.z);
    }

    /**
     * Apply a rotation to <code>this</code> that rotates the <tt>fromDir</tt> vector to point along <tt>toDir</tt> and
     * store the result in <code>dest</code>.
     * <p>
     * Since there can be multiple possible rotations, this method chooses the one with the shortest arc.
     * <p>
     * If <code>Q</code> is <code>this</code> quaternion and <code>R</code> the quaternion representing the 
     * specified rotation, then the new quaternion will be <code>Q * R</code>. So when transforming a
     * vector <code>v</code> with the new quaternion by using <code>Q * R * v</code>, the
     * rotation added by this method will be applied first!
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
     * @param dest
     *          will hold the result
     * @return this
     */
    public Quaterniond rotateTo(double fromDirX, double fromDirY, double fromDirZ,
                                double toDirX, double toDirY, double toDirZ, Quaterniond dest) {
        double fromLength = Math.sqrt(fromDirX * fromDirX + fromDirY * fromDirY + fromDirZ * fromDirZ);
        double fromX = fromDirX / fromLength;
        double fromY = fromDirY / fromLength;
        double fromZ = fromDirZ / fromLength;
        double toLength = Math.sqrt(toDirX * toDirX + toDirY * toDirY + toDirZ * toDirZ);
        double toX = toDirX / toLength;
        double toY = toDirY / toLength;
        double toZ = toDirZ / toLength;
        double dot = fromX * toX + fromY * toY + fromZ * toZ;
        double x, y, z, w;
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
            double norm = Math.sqrt(x * x + y * y + z * z + w * w);
            x /= norm;
            y /= norm;
            z /= norm;
            w /= norm;
        } else {
            /* vectors are parallel, don't change anything */
            return this;
        }
        /* Multiply */
        dest.set(this.w * x + this.x * w + this.y * z - this.z * y,
                 this.w * y - this.x * z + this.y * w + this.z * x,
                 this.w * z + this.x * y - this.y * x + this.z * w,
                 this.w * w - this.x * x - this.y * y - this.z * z);
        return this;
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
        double vLength = Math.sqrt(axisX * axisX + axisY * axisY + axisZ * axisZ);

        x = (axisX / vLength) * sinAngle;
        y = (axisY / vLength) * sinAngle;
        z = (axisZ / vLength) * sinAngle;
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
     * @see #rotation(double, double, double)
     * 
     * @param angle
     *              the angle in radians to rotate about the x axis
     * @return this
     */
    public Quaterniond rotationX(double angle) {
        return rotation(angle, 0.0, 0.0);
    }

    /**
     * Set this quaternion to represent a rotation of the given radians about the y axis.
     * 
     * @see #rotation(double, double, double)
     * 
     * @param angle
     *              the angle in radians to rotate about the y axis
     * @return this
     */
    public Quaterniond rotationY(double angle) {
        return rotation(0.0, angle, 0.0);
    }

    /**
     * Set this quaternion to represent a rotation of the given radians about the z axis.
     * 
     * @see #rotation(double, double, double)
     * 
     * @param angle
     *              the angle in radians to rotate about the z axis
     * @return this
     */
    public Quaterniond rotationZ(double angle) {
        return rotation(0.0, 0.0, angle);
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
     * Apply a rotation to <code>this</code> that rotates the <code>fromDir</code> vector to point along <code>toDir</code> and
     * store the result in <code>dest</code>.
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
     * @param dest
     *          will hold the result
     * @return this
     */
    public Quaterniond rotateTo(Vector3d fromDir, Vector3d toDir, Quaterniond dest) {
        return rotateTo(fromDir.x, fromDir.y, fromDir.z, toDir.x, toDir.y, toDir.z, dest);
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
    public Quaterniond rotateTo(Vector3d fromDir, Vector3d toDir) {
        return rotateTo(fromDir.x, fromDir.y, fromDir.z, toDir.x, toDir.y, toDir.z, this);
    }

    /**
     * Apply a rotation to <code>this</code> quaternion rotating the given radians about the basis unit axes of the
     * cartesian space and store the result in <code>dest</code>.
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
     * @param dest
     *              will hold the result
     * @return this
     */
    public Quaterniond rotate(Vector3d anglesXYZ, Quaterniond dest) {
        return rotate(anglesXYZ.x, anglesXYZ.y, anglesXYZ.z, dest);
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
    public Quaterniond rotate(Vector3d anglesXYZ) {
        return rotate(anglesXYZ.x, anglesXYZ.y, anglesXYZ.z, this);
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
     * Apply a rotation to <code>this</code> quaternion rotating the given radians about the basis unit axes of the
     * cartesian space and store the result in <code>dest</code>.
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
     * @param dest
     *              will hold the result
     * @return this
     */
    public Quaterniond rotate(double angleX, double angleY, double angleZ, Quaterniond dest) {
        double thetaX = angleX * 0.5;
        double thetaY = angleY * 0.5;
        double thetaZ = angleZ * 0.5;
        double thetaMagSq = thetaX * thetaX + thetaY * thetaY + thetaZ * thetaZ;
        double s;
        double dqX, dqY, dqZ, dqW;
        if (thetaMagSq * thetaMagSq / 24.0 < 1E-8f) {
            dqW = 1.0 - thetaMagSq / 2.0;
            s = 1.0 - thetaMagSq / 6.0;
        } else {
            double thetaMag = Math.sqrt(thetaMagSq);
            dqW = Math.cos(thetaMag);
            s = Math.sin(thetaMag) / thetaMag;
        }
        dqX = thetaX * s;
        dqY = thetaY * s;
        dqZ = thetaZ * s;
        /* Post-multiplication (like matrices multiply) */
        dest.set(w * dqX + x * dqW + y * dqZ - z * dqY,
                 w * dqY - x * dqZ + y * dqW + z * dqX,
                 w * dqZ + x * dqY - y * dqX + z * dqW,
                 w * dqW - x * dqX - y * dqY - z * dqZ);
        return this;
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
        return rotate(angle, 0.0, 0.0, this);
    }

    /**
     * Apply a rotation to <code>this</code> quaternion rotating the given radians about the x axis
     * and store the result in <code>dest</code>.
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
     * @param dest
     *              will hold the result
     * @return this
     */
    public Quaterniond rotateX(double angle, Quaterniond dest) {
        return rotate(angle, 0.0, 0.0, dest);
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
        return rotate(0.0, angle, 0.0, this);
    }

    /**
     * Apply a rotation to <code>this</code> quaternion rotating the given radians about the y axis
     * and store the result in <code>dest</code>.
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
     * @param dest
     *              will hold the result
     * @return this
     */
    public Quaterniond rotateY(double angle, Quaterniond dest) {
        return rotate(0.0, angle, 0.0, dest);
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
        return rotate(0.0, 0.0, angle, this);
    }

    /**
     * Apply a rotation to <code>this</code> quaternion rotating the given radians about the z axis
     * and store the result in <code>dest</code>.
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
     * @param dest
     *              will hold the result
     * @return this
     */
    public Quaterniond rotateZ(double angle, Quaterniond dest) {
        return rotate(0.0, 0.0, angle, dest);
    }

    /**
     * Apply a rotation to <code>this</code> quaternion rotating the given radians about the cartesian base unit axes,
     * called the euler angles using rotation sequence <tt>XYZ</tt>.
     * <p>
     * This method is equivalent to calling: <tt>rotateX(angles.x).rotateY(angles.y).rotateZ(angles.z)</tt>
     * <p>
     * If <code>Q</code> is <code>this</code> quaternion and <code>R</code> the quaternion representing the 
     * specified rotation, then the new quaternion will be <code>Q * R</code>. So when transforming a
     * vector <code>v</code> with the new quaternion by using <code>Q * R * v</code>, the
     * rotation added by this method will be applied first!
     * 
     * @param angles
     *              the euler angles in radians
     * @return this
     */
    public Quaterniond rotateXYZ(Vector3d angles) {
        return rotateXYZ(angles.x, angles.y, angles.z);
    }

    /**
     * Apply a rotation to <code>this</code> quaternion rotating the given radians about the cartesian base unit axes,
     * called the euler angles using rotation sequence <tt>ZYX</tt>.
     * <p>
     * This method is equivalent to calling: <tt>rotateZ(angles.z).rotateY(angles.y).rotateX(angles.x)</tt>
     * <p>
     * If <code>Q</code> is <code>this</code> quaternion and <code>R</code> the quaternion representing the 
     * specified rotation, then the new quaternion will be <code>Q * R</code>. So when transforming a
     * vector <code>v</code> with the new quaternion by using <code>Q * R * v</code>, the
     * rotation added by this method will be applied first!
     * 
     * @param angles
     *              the euler angles in radians
     * @return this
     */
    public Quaterniond rotateZYX(Vector3d angles) {
        return rotateZYX(angles.z, angles.y, angles.x);
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
        return rotateX(angleX).rotateY(angleY).rotateZ(angleZ);
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
     * @param angleX
     *              the angle in radians to rotate about the x axis
     * @param angleY
     *              the angle in radians to rotate about the y axis
     * @param angleZ
     *              the angle in radians to rotate about the z axis
     * @return this
     */
    public Quaterniond rotateZYX(double angleX, double angleY, double angleZ) {
        return rotateZ(angleZ).rotateY(angleY).rotateX(angleX);
    }

    /**
     * Get the euler angles in radians in rotation sequence <tt>XYZ</tt> of this quaternion and store them in the 
     * provided parameter <code>eulerAngles</code>.
     * 
     * @param eulerAngles
     *          will hold the euler angles in radians
     * @return this
     */
    public Quaterniond getEulerAnglesXYZ(Vector3d eulerAngles) {
        eulerAngles.x = Math.atan2(2.0 * (x*w - y*z), 1.0 - 2.0 * (x*x + y*y));
        eulerAngles.y = Math.asin(2.0 * (x*z + y*w));
        eulerAngles.z = Math.atan2(2.0 * (z*w - x*y), 1.0 - 2.0 * (y*y + z*z));
        return this;
    }

    /**
     * Apply a rotation to <code>this</code> quaternion rotating the given radians about the specified axis
     * and store the result in <code>dest</code>.
     * <p>
     * If <code>Q</code> is <code>this</code> quaternion and <code>R</code> the quaternion representing the 
     * specified rotation, then the new quaternion will be <code>Q * R</code>. So when transforming a
     * vector <code>v</code> with the new quaternion by using <code>Q * R * v</code>, the
     * rotation added by this method will be applied first!
     * 
     * @param angle
     *              the angle in radians to rotate about the specified axis
     * @param axisX
     *              the x coordinate of the rotation axis
     * @param axisY
     *              the y coordinate of the rotation axis
     * @param axisZ
     *              the z coordinate of the rotation axis
     * @param dest
     *              will hold the result
     * @return this
     */
    public Quaterniond rotateAxis(double angle, double axisX, double axisY, double axisZ, Quaterniond dest) {
        double hangle = angle / 2.0;
        double sinAngle = Math.sin(hangle);
        double vLength = Math.sqrt(axisX * axisX + axisY * axisY + axisZ * axisZ);

        double rx = (axisX / vLength) * sinAngle;
        double ry = (axisY / vLength) * sinAngle;
        double rz = (axisZ / vLength) * sinAngle;
        double rw = Math.cos(hangle);

        dest.set(w * rx + x * rw + y * rz - z * ry,
                 w * ry - x * rz + y * rw + z * rx,
                 w * rz + x * ry - y * rx + z * rw,
                 w * rw - x * rx - y * ry - z * rz);
        return this;
    }

    /**
     * Apply a rotation to <code>this</code> quaternion rotating the given radians about the specified axis
     * and store the result in <code>dest</code>.
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
     * @param dest
     *              will hold the result
     * @return this
     */
    public Quaterniond rotateAxis(double angle, Vector3d axis, Quaterniond dest) {
        return rotateAxis(angle, axis.x, axis.y, axis.z, dest);
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
    public Quaterniond rotateAxis(double angle, Vector3d axis) {
        return rotateAxis(angle, axis.x, axis.y, axis.z, this);
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

    /**
     * Return the specified {@link Vector3f}.
     * <p>
     * When using method chaining in a fluent interface style, this method can be used to switch
     * the <i>context object</i>, on which further method invocations operate, to be the given vector.
     * 
     * @param v
     *          the {@link Vector3f} to return
     * @return that vector
     */
    public Vector3f with(Vector3f v) {
        return v;
    }

    /**
     * Return the specified {@link Vector4f}.
     * <p>
     * When using method chaining in a fluent interface style, this method can be used to switch
     * the <i>context object</i>, on which further method invocations operate, to be the given vector.
     * 
     * @param v
     *          the {@link Vector4f} to return
     * @return that vector
     */
    public Vector4f with(Vector4f v) {
        return v;
    }

    /**
     * Return the specified {@link Quaternionf}.
     * <p>
     * When using method chaining in a fluent interface style, this method can be used to switch
     * the <i>context object</i>, on which further method invocations operate, to be the given quaternion.
     * 
     * @param q
     *          the {@link Quaternionf} to return
     * @return that quaternion
     */
    public Quaternionf with(Quaternionf q) {
        return q;
    }

    /**
     * Return the specified {@link Quaterniond}.
     * <p>
     * When using method chaining in a fluent interface style, this method can be used to switch
     * the <i>context object</i>, on which further method invocations operate, to be the given quaternion.
     * 
     * @param q
     *          the {@link Quaterniond} to return
     * @return that quaternion
     */
    public Quaterniond with(Quaterniond q) {
        return q;
    }

    /**
     * Return the specified {@link AxisAngle4f}.
     * <p>
     * When using method chaining in a fluent interface style, this method can be used to switch
     * the <i>context object</i>, on which further method invocations operate, to be the given {@link AxisAngle4f}.
     * 
     * @param a
     *          the {@link AxisAngle4f} to return
     * @return that quaternion
     */
    public AxisAngle4f with(AxisAngle4f a) {
        return a;
    }

    /**
     * Return the specified {@link Matrix3f}.
     * <p>
     * When using method chaining in a fluent interface style, this method can be used to switch
     * the <i>context object</i>, on which further method invocations operate, to be the given matrix.
     * 
     * @param m
     *          the {@link Matrix3f} to return
     * @return that matrix
     */
    public Matrix3f with(Matrix3f m) {
        return m;
    }

    /**
     * Return the specified {@link Matrix4f}.
     * <p>
     * When using method chaining in a fluent interface style, this method can be used to switch
     * the <i>context object</i>, on which further method invocations operate, to be the given matrix.
     * 
     * @param m
     *          the {@link Matrix4f} to return
     * @return that matrix
     */
    public Matrix4f with(Matrix4f m) {
        return m;
    }

}
