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
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Contains the definition and functions for rotations expressed as
 * 4-dimensional vectors
 *
 * @author Richard Greenlees
 * @author Kai Burjack
 */
public class Quaterniond implements Serializable, Externalizable {

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
        x = 0.0f;
        y = 0.0f;
        z = 0.0f;
        w = 1.0f;
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
        this.w = 1.0f;
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
     * Return the angle represented by this quaternion rotation in degrees.
     * 
     * @return the angle in degrees
     */
    public double angle() {
        double angle = 2.0 * Math.acos(w);
        return Math.toDegrees(angle <= Math.PI ? angle : 2.0f * Math.PI - angle);
    }

    /**
     * Set the given destination matrix to the rotation represented by <code>this</code>.
     * 
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
     * Set this quaternion to the new values.
     * 
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
     * Set this {@link Quaterniond} to be equivalent to the given
     * {@link AxisAngle4f}.
     * 
     * @param angleAxis
     *            the {@link AxisAngle4f}
     * @return this
     */
    public Quaterniond set(AxisAngle4f angleAxis) {
        return setAngleAxis(angleAxis.angle, angleAxis.x, angleAxis.y, angleAxis.z);
    }

    /**
     * Set this quaternion to be a representation of the supplied axis and
     * angle (in degrees).
     * 
     * @return this
     */
    public Quaterniond setAngleAxis(double angle, double axisX, double axisY, double axisZ) {
        double hangle = Math.toRadians(angle / 2.0);
        double sinAngle = Math.sin(hangle);
        double vLength = Math.sqrt(axisX * axisX + axisY * axisY + axisZ * axisZ);

        x = (axisX / vLength) * sinAngle;
        y = (axisY / vLength) * sinAngle;
        z = (axisZ / vLength) * sinAngle;
        w = Math.cos(hangle);

        return this;
    }

    /**
     * Set this quaternion to be a representation of the supplied axis and
     * angle (in degrees).
     * 
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

        x = (axis.x / vLength) * sinAngle;
        y = (axis.y / vLength) * sinAngle;
        z = (axis.z / vLength) * sinAngle;
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

        x = (axisX / vLength) * sinAngle;
        y = (axisY / vLength) * sinAngle;
        z = (axisZ / vLength) * sinAngle;
        w = Math.cos(hangle);
        
        return this;
    }

    /**
     * Set this quaternion to be a representation of the supplied axis and
     * angle (in degrees).
     * 
     * @param axis
     *          the rotation axis
     * @param angle
     *          the angle in radians
     * @return this
     */
    public Quaterniond fromAxisAngleDeg(Vector3d axis, double angle) {
        double hangle = Math.toRadians(angle) / 2.0f;
        double sinAngle = Math.sin(hangle);
        double vLength = axis.length();

        x = (axis.x / vLength) * sinAngle;
        y = (axis.y / vLength) * sinAngle;
        z = (axis.z / vLength) * sinAngle;
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
     * Invert this quaternion and store the result in <code>dest</code>.
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
     * Invert this quaternion.
     * 
     * @return this
     */
    public Quaterniond invert() {
        return invert(this);
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
        double norm = (b.x * b.x + b.y * b.y + b.z * b.z + b.w * b.w);
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
        x = 0.0f;
        y = 0.0f;
        z = 0.0f;
        w = 1.0f;
        return this;
    }

    /**
     * Calculate this quaternion using the supplied Vector3f angles (in degrees) with rotation order XYZ.
     * 
     * @return this
     */
    public Quaterniond setEulerAnglesDegXYZ(Vector3d angles) {
        return setEulerAnglesDegXYZ(angles.x, angles.y, angles.z);
    }

    /**
     * Calculate this quaternion using the supplied Vector3f angles (in radians) with rotation order XYZ.
     * 
     * @return this
     */
    public Quaterniond setEulerAnglesRadXYZ(Vector3d angles) {
        return setEulerAnglesRadXYZ(angles.x, angles.y, angles.z);
    }

    /**
     * Calculate this quaternion using the supplied Vector3f angles (in degrees) with rotation order ZYX.
     * 
     * @return this
     */
    public Quaterniond setEulerAnglesDegZYX(Vector3d angles) {
        return setEulerAnglesDegZYX(angles.x, angles.y, angles.z);
    }

    /**
     * Calculate this quaternion using the supplied Vector3f angles (in radians) with rotation order ZYX.
     * 
     * @return this
     */
    public Quaterniond setEulerAnglesRadZYX(Vector3d angles) {
        return setEulerAnglesRadZYX(angles.x, angles.y, angles.z);
    }

    /**
     * Calculate this quaternion using the supplied pitch (rotation about X), yaw (rotation about Y) and roll (rotation about Z) angles
     * (in degrees) with rotation order XYZ.
     * <p>
     * This method implements the solution outlined in <a href="http://gamedev.stackexchange.com/questions/13436/glm-euler-angles-to-quaternion#answer-13446">this stackexchange answer</a>.
     * 
     * @return this
     */
    public Quaterniond setEulerAnglesDegXYZ(double rotationAboutX, double rotationAboutY, double rotationAboutZ) {
        double sx = Math.sin(Math.toRadians(rotationAboutX) * 0.5);
        double cx = Math.cos(Math.toRadians(rotationAboutX) * 0.5);
        double sy = Math.sin(Math.toRadians(rotationAboutY) * 0.5);
        double cy = Math.cos(Math.toRadians(rotationAboutY) * 0.5);
        double sz = Math.sin(Math.toRadians(rotationAboutZ) * 0.5);
        double cz = Math.cos(Math.toRadians(rotationAboutZ) * 0.5);

        x = cx*cy*cz + sx*sy*sz;
        y = sx*cy*cz - cx*sy*sz;
        z = cx*sy*cz + sx*cy*sz;
        w = cx*cy*sz - sx*sy*cz;
        
        return this;
    }

    /**
     * Calculate this quaternion using the supplied pitch (rotation about X), yaw (rotation about Y) and roll (rotation about Z) angles
     * (in degrees) with rotation order ZYX.
     * <p>
     * This method implements the solution outlined in <a href="http://gamedev.stackexchange.com/questions/13436/glm-euler-angles-to-quaternion#answer-13446">this stackexchange answer</a>.
     * 
     * @return this
     */
    public Quaterniond setEulerAnglesDegZYX(double rotationAboutX, double rotationAboutY, double rotationAboutZ) {
        double sx = Math.sin(Math.toRadians(rotationAboutX) * 0.5);
        double cx = Math.cos(Math.toRadians(rotationAboutX) * 0.5);
        double sy = Math.sin(Math.toRadians(rotationAboutY) * 0.5);
        double cy = Math.cos(Math.toRadians(rotationAboutY) * 0.5);
        double sz = Math.sin(Math.toRadians(rotationAboutZ) * 0.5);
        double cz = Math.cos(Math.toRadians(rotationAboutZ) * 0.5);

        x = cx*cy*cz - sx*sy*sz;
        y = sx*cy*cz + cx*sy*sz;
        z = cx*sy*cz - sx*cy*sz;
        w = cx*cy*sz + sx*sy*cz;
        
        return this;
    }

    /**
     * Returns the length of this quaternion
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
     * @return this
     */
    public Quaterniond setEulerAnglesRadXYZ(double rotationAboutX, double rotationAboutY, double rotationAboutZ) {
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
     * @return this
     */
    public Quaterniond setEulerAnglesRadZYX(double rotationAboutX, double rotationAboutY, double rotationAboutZ) {
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
        double q2, q3;
        double q4 = (x * target.x) + (y * target.y) + (z * target.z) + (w * target.w);
        boolean flag = false;
        if (q4 < 0.0) {
            flag = true;
            q4 = -q4;
        }
        if (q4 > 0.999999) {
            q3 = 1.0F - alpha;
            q2 = flag ? -alpha : alpha;
        } else {
            double q5 = Math.acos(q4);
            double q6 = 1.0 / Math.sin(q5);
            q3 = Math.sin(((1.0 - alpha) * q5)) * q6;
            q2 = flag ? -Math.sin(alpha * q5) * q6 : Math.sin(alpha * q5) * q6;
        }
        dest.x = q3 * x + q2 * target.x;
        dest.y = q3 * y + q2 * target.y;
        dest.z = q3 * z + q2 * target.z;
        dest.w = q3 * w + q2 * target.w;
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
        DecimalFormat formatter = new DecimalFormat(" 0.000E0;-");
        return toString(formatter).replaceAll("E(\\d+)", "E+$1");
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

}
