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
     * Create a new {@link Quaternionf} and initialize it with <tt>(x=0, y=0, z=0, w=1)</tt>, 
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
     * Create a new {@link Quaternionf} and initialize its imaginary components to the given values,
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
     * Normalize this Quaternion.
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
     * Normalize the supplied {@link Quaterniond} <code>source</code> and store the result in <code>dest</code>.
     * 
     * @param source
     *          the quaternion to normalize
     * @param dest
     *          will hold the result
     */
    public static void normalize(Quaterniond source, Quaterniond dest) {
        double norm = Math.sqrt(source.x * source.x + source.y * source.y + source.z * source.z + source.w * source.w);

        dest.x = source.x / norm;
        dest.y = source.y / norm;
        dest.z = source.z / norm;
        dest.w = source.w / norm;
    }

    /**
     * Add <code>q2</code> to <code>this</code> quaternion.
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
     * Add <code>q2</code> to <code>q1</code> and store the result in <code>dest</code>.
     * 
     * @param q1
     *          the first quaternion
     * @param q2
     *          the second quaternion
     * @param dest
     *          will hold the result
     */
    public static void add(Quaterniond q1, Quaterniond q2, Quaterniond dest) {
        dest.x = q1.x + q2.x;
        dest.y = q1.y + q2.y;
        dest.z = q1.z + q2.z;
        dest.w = q1.w + q2.w;
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
     * Return the dot product of the given <code>a</code> and <code>b</code> quaternions.
     * 
     * @param a
     *          the first quaternion
     * @param b
     *          the second quaternion
     * @return the dot product
     */
    public static double dot(Quaterniond a, Quaterniond b) {
        return a.x * b.x + a.y * b.y + a.z * b.z + a.w * b.w;
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
     * Return the angle represented by <code>q</code> in degrees.
     * 
     * @return the angle in degrees
     */
    public static double angle(Quaterniond q) {
        double angle = 2.0 * Math.acos(q.w);
        return Math.toDegrees(angle <= Math.PI ? angle : 2.0 * Math.PI - angle);
    }

    /**
     * Set the given <code>dest</code> matrix to the rotation represented by <code>q</code>.
     * 
     * @param q
     *          the quaternion
     * @param dest
     *          the matrix to store the rotation of the given quaternion to
     */
    public static void get(Quaterniond q, Matrix3d dest) {
        double q00 = 2.0 * q.x * q.x;
        double q11 = 2.0 * q.y * q.y;
        double q22 = 2.0 * q.z * q.z;

        double q01 = 2.0 * q.x * q.y;
        double q02 = 2.0 * q.x * q.z;
        double q03 = 2.0 * q.x * q.w;

        double q12 = 2.0 * q.y * q.z;
        double q13 = 2.0 * q.y * q.w;

        double q23 = 2.0 * q.z * q.w;

        dest.m00 = 1.0 - q11 - q22;
        dest.m01 = q01 + q23;
        dest.m02 = q02 - q13;
        dest.m10 = q01 - q23;
        dest.m11 = 1.0 - q22 - q00;
        dest.m12 = q12 + q03;
        dest.m20 = q02 + q13;
        dest.m21 = q12 - q03;
        dest.m22 = 1.0 - q11 - q00;
    }

    /**
     * Set the given destination matrix to the rotation represented by <code>this</code>.
     * 
     * @param dest
     *          the matrix to store the rotation of this quaternion to
     * @return this
     */
    public Quaterniond get(Matrix3d dest) {
        get(this, dest);
        return this;
    }

    /**
     * Set the given destination matrix to the rotation represented by <code>q</code>.
     */
    public static void get(Quaterniond q, Matrix4d dest) {
        double q00 = 2.0 * q.x * q.x;
        double q11 = 2.0 * q.y * q.y;
        double q22 = 2.0 * q.z * q.z;

        double q01 = 2.0 * q.x * q.y;
        double q02 = 2.0 * q.x * q.z;
        double q03 = 2.0 * q.x * q.w;

        double q12 = 2.0 * q.y * q.z;
        double q13 = 2.0 * q.y * q.w;

        double q23 = 2.0 * q.z * q.w;

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
    }

    /**
     * Set the given destination matrix to the rotation represented by <code>this</code>.
     * 
     * @return this
     */
    public Quaterniond get(Matrix4d dest) {
        get(this, dest);
        return this;
    }

    /**
     * Set this Quaternion to the new values.
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
     * Set the x, y and z components of this Quaternion to the new values.
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
     * Set this Quaternion to be a copy of q.
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
     * Set this Quaternion to be a representation of the supplied axis and
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
     * Set this Quaternion to be a representation of the supplied axis and
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
     * Set this Quaternion to be a representation of the supplied axis and
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
     * Multiply this Quaternion by <code>q</code>.
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
        mul(this, q, this);
        return this;
    }

    /**
     * Multiply this Quaternion by <code>q</code> and store the result in <code>dest</code>.
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
        mul(this, q, dest);
        return this;
    }

    /**
     * Multiply <code>a</code> by <code>b</code> and store the result in <code>dest</code>.
     * <p>
     * The result <tt>R</tt> of the multiplication is then defined as:
     * <p>
     * <tt>R = a * b</tt>
     * <p>
     * So, this method uses post-multiplication like the matrix classes, resulting in a
     * vector to be transformed by <tt>Q</tt> first, and then by <tt>T</tt>.
     */
    public static void mul(Quaterniond a, Quaterniond b, Quaterniond dest) {
        if (a != dest && b != dest) {
            dest.x = a.w * b.x + a.x * b.w + a.y * b.z - a.z * b.y;
            dest.y = a.w * b.y - a.x * b.z + a.y * b.w + a.z * b.x;
            dest.z = a.w * b.z + a.x * b.y - a.y * b.x + a.z * b.w;
            dest.w = a.w * b.w - a.x * b.x - a.y * b.y - a.z * b.z;
        } else {
            dest.set(a.w * b.x + a.x * b.w + a.y * b.z - a.z * b.y,
                     a.w * b.y - a.x * b.z + a.y * b.w + a.z * b.x,
                     a.w * b.z + a.x * b.y - a.y * b.x + a.z * b.w,
                     a.w * b.w - a.x * b.x - a.y * b.y - a.z * b.z);
        }
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
     * Invert this Quaternion.
     * 
     * @return this
     */
    public Quaterniond invert() {
        double norm = (x * x + y * y + z * z + w * w);
        x = x / norm;
        y = -y / norm;
        z = -z / norm;
        w = -w / norm;
        return this;
    }

    /**
     * Invert <code>q</code> and store the result in <code>dest</code>.
     * 
     * @param q
     *          the quaternion to invert
     * @param dest
     *          will hold the result
     */
    public static void invert(Quaterniond q, Quaterniond dest) {
        double norm = (q.x * q.x + q.y * q.y + q.z * q.z + q.w * q.w);
        dest.x = q.x / norm;
        dest.y = -q.y / norm;
        dest.z = -q.z / norm;
        dest.w = -q.w / norm;
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
        div(this, b, dest);
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
        div(this, b, this);
        return this;
    }

    /**
     * Divide <code>a</code> by <code>b</code> and store the result in <code>dest</code>.
     * <p>
     * The division expressed with the inverse is performed in the following way:
     * <p>
     * <tt>dest = a * b^-1</tt>, where <tt>b^-1</tt> is the inverse of <code>b</code>.
     * 
     * @param dest
     *          will hold the result
     */
    public static void div(Quaterniond a, Quaterniond b, Quaterniond dest) {
        double norm = (b.x * b.x + b.y * b.y + b.z * b.z + b.w * b.w);
        double x = -b.x / norm;
        double y = -b.y / norm;
        double z = -b.z / norm;
        double w = b.w / norm;
        dest.set(a.w * x + a.x * w + a.y * z - a.z * y,
                 a.w * y - a.x * z + a.y * w + a.z * x,
                 a.w * z + a.x * y - a.y * x + a.z * w,
                 a.w * w - a.x * x - a.y * y - a.z * z);
    }

    /**
     * Conjugate this Quaternion.
     * 
     * @return this;
     */
    public Quaterniond conjugate() {
        x = -x;
        y = -y;
        z = -z;
        return this;
    }

    /**
     * Conjugates a and stores the results in dest. Does not modify a
     */
    public static void conjugate(Quaterniond a, Quaterniond dest) {
        dest.x = -a.x;
        dest.y = -a.y;
        dest.z = -a.z;
        dest.w = a.w;
    }

    /**
     * Set this Quaternion to the identity.
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
     * Calculate this Quaternion using the supplied Vector3f angles (in degrees) with rotation order XYZ.
     * 
     * @return this
     */
    public Quaterniond setEulerAnglesDegXYZ(Vector3d angles) {
        return setEulerAnglesDegXYZ(angles.x, angles.y, angles.z);
    }

    /**
     * Calculate this Quaternion using the supplied Vector3f angles (in radians) with rotation order XYZ.
     * 
     * @return this
     */
    public Quaterniond setEulerAnglesRadXYZ(Vector3d angles) {
        return setEulerAnglesRadXYZ(angles.x, angles.y, angles.z);
    }

    /**
     * Calculate this Quaternion using the supplied Vector3f angles (in degrees) with rotation order ZYX.
     * 
     * @return this
     */
    public Quaterniond setEulerAnglesDegZYX(Vector3d angles) {
        return setEulerAnglesDegZYX(angles.x, angles.y, angles.z);
    }

    /**
     * Calculate this Quaternion using the supplied Vector3f angles (in radians) with rotation order ZYX.
     * 
     * @return this
     */
    public Quaterniond setEulerAnglesRadZYX(Vector3d angles) {
        return setEulerAnglesRadZYX(angles.x, angles.y, angles.z);
    }

    /**
     * Calculate this Quaternion using the supplied pitch (rotation about X), yaw (rotation about Y) and roll (rotation about Z) angles
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
     * Calculate this Quaternion using the supplied pitch (rotation about X), yaw (rotation about Y) and roll (rotation about Z) angles
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
     * Returns the length of q
     */
    public static double length(Quaterniond q) {
        return q.x * q.x + q.y * q.y + q.z * q.z + q.w * q.w;
    }

    /**
     * Calculate this Quaternion using the supplied pitch (rotation about X), yaw (rotation about Y) and roll (rotation about Z) angles
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
     * Calculate this Quaternion using the supplied pitch (rotation about X), yaw (rotation about Y) and roll (rotation about Z) angles
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
    public Quaterniond slerp(Quaterniond target, float alpha) {
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
    public Quaterniond slerp(Quaterniond target, float alpha, Quaterniond dest) {
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
    public Quaterniond nlerp(Quaterniond q, float factor) {
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
    public Quaterniond nlerp(Quaterniond q, float factor, Quaterniond dest) {
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

    
    /** Rotates dest to point towards destPoint, from the supplied sourcePoint */
    public static void lookAt(Vector3d sourcePoint, Vector3d destPoint, Vector3d up, Vector3d forward, Quaterniond dest) {
        double dirX = destPoint.x - sourcePoint.x;
        double dirY = destPoint.y - sourcePoint.y;
        double dirZ = destPoint.z - sourcePoint.z;

        double length = Math.sqrt(dirX * dirX + dirY * dirY + dirZ * dirZ);

        dirX /= length;
        dirY /= length;
        dirZ /= length;

        double dot = (forward.x * dirX) + (forward.y * dirY) + (forward.z * dirZ);

        if (Math.abs(dot + 1.0) < 0.000001) {
            dest.x = up.x;
            dest.y = up.y;
            dest.z = up.z;
            dest.w = Math.PI;
            return;
        }

        if (Math.abs(dot - 1.0) < 0.000001) {
            dest.x = 0.0;
            dest.y = 0.0;
            dest.z = 0.0;
            dest.w = 1.0;
            return;
        }

        double rotAngle = Math.acos(dot);
        
        double rotAxisX = forward.y * dirZ - forward.z * dirY;
        double rotAxisY = forward.z * dirX - forward.x * dirZ;
        double rotAxisZ = forward.x * dirY - forward.y * dirX;

        length = Math.sqrt(rotAxisX * rotAxisX + rotAxisY * rotAxisY + rotAxisZ * rotAxisZ);

        rotAxisX /= length;
        rotAxisY /= length;
        rotAxisZ /= length;

        dest.fromAxisAngleRad(rotAxisX, rotAxisY, rotAxisZ, rotAngle);
    }
    
    /**
     * Rotate <code>dest</code> to point towards <code>destPoint</code>, from the supplied <code>sourcePoint</code>.
     * 
     * @return this
     */
    public Quaterniond lookAt(Vector3d sourcePoint, Vector3d destPoint, Vector3d up, Vector3d forward) {
        double dirX = destPoint.x - sourcePoint.x;
        double dirY = destPoint.y - sourcePoint.y;
        double dirZ = destPoint.z - sourcePoint.z;

        double length = Math.sqrt(dirX * dirX + dirY * dirY + dirZ * dirZ);

        dirX /= length;
        dirY /= length;
        dirZ /= length;

        double dot = (forward.x * dirX) + (forward.y * dirY) + (forward.z * dirZ);

        if (Math.abs(dot + 1.0) < 0.000001) {
            x = up.x;
            y = up.y;
            z = up.z;
            w = Math.PI;
            return this;
        }

        if (Math.abs(dot - 1.0) < 0.000001) {
            x = 0.0f;
            y = 0.0f;
            z = 0.0f;
            w = 1.0f;
            return this;
        }

        double rotAngle = Math.acos(dot);
        
        double rotAxisX = forward.y * dirZ - forward.z * dirY;
        double rotAxisY = forward.z * dirX - forward.x * dirZ;
        double rotAxisZ = forward.x * dirY - forward.y * dirX;

        length = Math.sqrt(rotAxisX * rotAxisX + rotAxisY * rotAxisY + rotAxisZ * rotAxisZ);

        rotAxisX /= length;
        rotAxisY /= length;
        rotAxisZ /= length;

        fromAxisAngleRad(rotAxisX, rotAxisY, rotAxisZ, rotAngle);
        
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
