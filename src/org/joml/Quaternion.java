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
public class Quaternion implements Serializable, Externalizable {

    /**
     * The first component of the vector part.
     */
    public float x;
    /**
     * The second component of the vector part.
     */
    public float y;
    /**
     * The thirdcomponent of the vector part.
     */
    public float z;
    /**
     * The real/scalar part of the quaternion.
     */
    public float w;

    /**
     * Create a new {@link Quaternion} and initialize it with <tt>(x=0, y=0, z=0, w=1)</tt>, 
     * where <tt>(x, y, z)</tt> is the vector part of the quaternion and <tt>w</tt> is the real/scalar part.
     */
    public Quaternion() {
        x = 0.0f;
        y = 0.0f;
        z = 0.0f;
        w = 1.0f;
    }

    /**
     * Create a new {@link Quaternion} and initialize its components to the given values.
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
    public Quaternion(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    /**
     * Create a new {@link Quaternion} and initialize its imaginary components to the given values,
     * and its real part to <tt>1.0</tt>.
     * 
     * @param x
     *          the first component of the imaginary part
     * @param y
     *          the second component of the imaginary part
     * @param z
     *          the third component of the imaginary part
     */
    public Quaternion(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
        w = 1.0f;
    }

    /**
     * Create a new {@link Quaternion} and initialize its components to the same values as the given {@link Quaternion}.
     * 
     * @param source
     *          the {@link Quaternion} to take the component values from
     */
    public Quaternion(Quaternion source) {
        x = source.x;
        y = source.y;
        z = source.z;
        w = source.w;
    }

    /**
     * Create a new {@link Quaternion} which represents the rotation of the given {@link AngleAxis4f}.
     * 
     * @param axisAngle
     *          the {@link AngleAxis4f}
     */
    public Quaternion(AngleAxis4f axisAngle) {
        float sin = (float) Math.sin(Math.toRadians(axisAngle.angle) / 2.0);
        float cos = (float) Math.cos(Math.toRadians(axisAngle.angle) / 2.0);
        x = axisAngle.x * sin;
        x = axisAngle.y * sin;
        x = axisAngle.z * sin;
        w = cos;
    }

    /**
     * Normalize this Quaternion.
     * 
     * @return this
     */
    public Quaternion normalize() {
        float norm = (float) Math.sqrt(x * x + y * y + z * z + w * w);

        x /= norm;
        y /= norm;
        z /= norm;
        w /= norm;

        return this;
    }

    /**
     * Normalizes the supplied Quaternion source and stores the results in dest.
     * Does not modify the source
     */
    public static void normalize(Quaternion source, Quaternion dest) {
        float norm = (float) Math.sqrt(source.x * source.x + source.y * source.y + source.z * source.z + source.w * source.w);

        dest.x = source.x / norm;
        dest.y = source.y / norm;
        dest.z = source.z / norm;
        dest.w = source.w / norm;
    }

    /**
     * Add <code>q2</code> to this quaternion.
     * 
     * @return this
     */
    public Quaternion add(Quaternion q2) {
        x += q2.x;
        y += q2.y;
        z += q2.z;
        w += q2.w;
        return this;
    }

    /**
     * Add q2 to q1 and store the results in dest. Does not modify q1 or q2
     */
    public static void add(Quaternion q1, Quaternion q2, Quaternion dest) {
        dest.x = q1.x + q2.x;
        dest.y = q1.y + q2.y;
        dest.z = q1.z + q2.z;
        dest.w = q1.w + q2.w;
    }

    /**
     * Returns the dot of this Quaternion and otherQuat
     */
    public float dot(Quaternion otherQuat) {
        return this.x * otherQuat.x + this.y * otherQuat.y + this.z * otherQuat.z + this.w * otherQuat.w;
    }

    /**
     * Returns the dot product of a and b
     */
    public static float dot(Quaternion a, Quaternion b) {
        return a.x * b.x + a.y * b.y + a.z * b.z + a.w * b.w;
    }

    /**
     * Return the angle represented by this quaternion rotation in degrees.
     */
    public float angle() {
        float angle = 2.0f * (float) Math.acos(w);
        return (angle <= Math.PI) ? angle : 2.0f * (float) Math.PI - angle;
    }

    /**
     * Return the angle represented by <code>q</code> in degrees.
     */
    public static float angle(Quaternion q) {
        float angle = 2.0f * (float) Math.acos(q.w);
        return (angle <= Math.PI) ? angle : 2.0f * (float) Math.PI - angle;
    }

    /**
     * Set the given destination matrix to the rotation represented by <code>q</code>.
     */
    public static void get(Quaternion q, Matrix3f dest) {
        float q00 = 2.0f * q.x * q.x;
        float q11 = 2.0f * q.y * q.y;
        float q22 = 2.0f * q.z * q.z;

        float q01 = 2.0f * q.x * q.y;
        float q02 = 2.0f * q.x * q.z;
        float q03 = 2.0f * q.x * q.w;

        float q12 = 2.0f * q.y * q.z;
        float q13 = 2.0f * q.y * q.w;

        float q23 = 2.0f * q.z * q.w;

        dest.m00 = 1.0f - q11 - q22;
        dest.m01 = q01 + q23;
        dest.m02 = q02 - q13;
        dest.m10 = q01 - q23;
        dest.m11 = 1.0f - q22 - q00;
        dest.m12 = q12 + q03;
        dest.m20 = q02 + q13;
        dest.m21 = q12 - q03;
        dest.m22 = 1.0f - q11 - q00;
    }

    /**
     * Set the given destination matrix to the rotation represented by <code>this</code>.
     * 
     * @return this
     */
    public Quaternion get(Matrix3f dest) {
        get(this, dest);
        return this;
    }

    /**
     * Set the given destination matrix to the rotation represented by <code>q</code>.
     */
    public static void get(Quaternion q, Matrix4f dest) {
        float q00 = 2.0f * q.x * q.x;
        float q11 = 2.0f * q.y * q.y;
        float q22 = 2.0f * q.z * q.z;

        float q01 = 2.0f * q.x * q.y;
        float q02 = 2.0f * q.x * q.z;
        float q03 = 2.0f * q.x * q.w;

        float q12 = 2.0f * q.y * q.z;
        float q13 = 2.0f * q.y * q.w;

        float q23 = 2.0f * q.z * q.w;

        dest.m00 = 1.0f - q11 - q22;
        dest.m01 = q01 + q23;
        dest.m02 = q02 - q13;
        dest.m03 = 0.0f;
        dest.m10 = q01 - q23;
        dest.m11 = 1.0f - q22 - q00;
        dest.m12 = q12 + q03;
        dest.m13 = 0.0f;
        dest.m20 = q02 + q13;
        dest.m21 = q12 - q03;
        dest.m22 = 1.0f - q11 - q00;
        dest.m30 = 0.0f;
        dest.m31 = 0.0f;
        dest.m32 = 0.0f;
        dest.m33 = 1.0f;
    }

    /**
     * Set the given destination matrix to the rotation represented by <code>this</code>.
     * 
     * @return this
     */
    public Quaternion get(Matrix4f dest) {
        get(this, dest);
        return this;
    }

    /**
     * Set the given {@link AngleAxis4f} to represent the rotation of
     * <code>this</code> quaternion.
     * 
     * @param angleAxis
     *            the {@link AngleAxis4f} to set
     * @return this
     */
    public Quaternion get(AngleAxis4f angleAxis) {
        float x = this.x;
        float y = this.y;
        float z = this.z;
        float w = this.w;
        if (w > 1.0f) {
            float norm = (float) Math.sqrt(x * x + y * y + z * z + w * w);
            x /= norm;
            y /= norm;
            z /= norm;
            w /= norm;
        }
        angleAxis.angle = (float) (2.0f * Math.acos(w));
        float s = (float) Math.sqrt(1.0 - w * w);
        if (s < 0.001f) {
            angleAxis.x = x;
            angleAxis.y = y;
            angleAxis.z = z;
        } else {
            angleAxis.x = x / s;
            angleAxis.y = y / s;
            angleAxis.z = z / s;
        }
        return this;
    }

    /**
     * Set this Quaternion to the given values.
     * 
     * @return this
     */
    public Quaternion set(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
        return this;
    }

    /**
     * Set the x, y and z components of this Quaternion to the given values.
     * 
     * @return this
     */
    public Quaternion set(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }

    /**
     * Set this Quaternion to be a copy of q.
     * 
     * @return this
     */
    public Quaternion set(Quaternion q) {
        x = q.x;
        y = q.y;
        z = q.z;
        w = q.w;
        return this;
    }

    /**
     * Set this quaternion to a rotation equivalent to the given {@link AngleAxis4f}.
     * 
     * @param angleAxis
     *          the {@link AngleAxis4f}
     * @return this
     */
    public Quaternion set(AngleAxis4f angleAxis) {
        double angle = Math.toRadians(angleAxis.angle);
        double s = Math.sin(angle / 2.0);
        x = (float) (angleAxis.x * s);
        y = (float) (angleAxis.y * s);
        z = (float) (angleAxis.z * s);
        w = (float) Math.cos(angle / 2.0);
        return this;
    }

    /**
     * Set this quaternion to a rotation equivalent to the given angle-axis.
     * 
     * @param angle
     *          the angle in degrees
     * @param x
     *          the x-component of the rotation axis
     * @param y
     *          the y-component of the rotation axis
     * @param z
     *          the z-component of the rotation axis
     * @return this
     */
    public Quaternion setAngleAxis(float angle, float x, float y, float z) {
        double angleR = Math.toRadians(angle);
        double s = Math.sin(angleR / 2.0);
        this.x = (float) (x * s);
        this.y = (float) (y * s);
        this.z = (float) (z * s);
        this.w = (float) Math.cos(angleR / 2.0);
        return this;
    }

    /**
     * Set this {@link Quaternion} to a rotation of the given angle in degrees about the supplied
     * axis, all of which are specified via the {@link AngleAxis4f}.
     * 
     * @param angleAxis
     *            the {@link AngleAxis4f} giving the rotation angle in degrees and the axis to rotate about
     * @return this
     */
    public Quaternion rotationAxis(AngleAxis4f angleAxis) {
        return rotationAxis(angleAxis.angle, angleAxis.x, angleAxis.y, angleAxis.z);
    }

    /**
     * Set this Quaternion to a rotation of the given angle in degrees about the supplied axis.
     * 
     * @param angle
     *          the rotation angle in degrees
     * @return this
     */
    public Quaternion rotationAxis(float angle, float axisX, float axisY, float axisZ) {
        float hangle = (float) Math.toRadians(angle / 2.0);
        float sinAngle = (float) Math.sin(hangle);
        float vLength = (float) Math.sqrt(axisX * axisX + axisY * axisY + axisZ * axisZ);

        x = (axisX / vLength) * sinAngle;
        y = (axisY / vLength) * sinAngle;
        z = (axisZ / vLength) * sinAngle;
        w = (float) Math.cos(hangle);

        return this;
    }

    /**
     * Set this Quaternion to a rotation of the given angle in degrees about the supplied axis.
     * 
     * @param angle
     *          the rotation angle in degrees
     * @param axis
     *          the axis to rotate about
     * @return this
     */
    public Quaternion rotationAxis(float angle, Vector3f axis) {
        return rotationAxis(angle, axis.x, axis.y, axis.z);
    }

    /**
     * Set this quaternion to represent a rotation of the given angles in degrees about the basis unit axes of the cartesian space.
     * 
     * @param anglesXYZ
     *              the angles in degrees to rotate about the basis unit axes of the cartesian space
     * @return this
     */
    public Quaternion rotation(Vector3f anglesXYZ) {
        return rotation(anglesXYZ.x, anglesXYZ.y, anglesXYZ.z);
    }

    /**
     * Set this quaternion to represent a rotation of the given angles in degrees about the basis unit axes of the cartesian space.
     * 
     * @param angleX
     *              the angle in degrees to rotate about the x axis
     * @param angleY
     *              the angle in degrees to rotate about the y axis
     * @param angleZ
     *              the angle in degrees to rotate about the z axis
     * @return this
     */
    public Quaternion rotation(float angleX, float angleY, float angleZ) {
        double thetaX = Math.toRadians(angleX) * 0.5;
        double thetaY = Math.toRadians(angleY) * 0.5;
        double thetaZ = Math.toRadians(angleZ) * 0.5;
        double thetaMagSq = thetaX * thetaX + thetaY * thetaY + thetaZ * thetaZ;
        double s;
        if (thetaMagSq * thetaMagSq / 24.0f < 1E-8f) {
            w = (float) (1.0 - thetaMagSq / 2.0);
            s = 1.0 - thetaMagSq / 6.0;
        } else {
            double thetaMag = Math.sqrt(thetaMagSq);
            w = (float) Math.cos(thetaMag);
            s = Math.sin(thetaMag) / thetaMag;
        }
        x = (float) (thetaX * s);
        y = (float) (thetaY * s);
        z = (float) (thetaZ * s);
        return this;
    }

    /**
     * Set this quaternion to represent a rotation of the given degrees about the x axis.
     * 
     * @param angle
     *              the angle in degrees to rotate about the x axis
     * @return this
     */
    public Quaternion rotationX(float angle) {
        return rotation(angle, 0.0f, 0.0f);
    }

    /**
     * Set this quaternion to represent a rotation of the given degrees about the y axis.
     * 
     * @param angle
     *              the angle in degrees to rotate about the y axis
     * @return this
     */
    public Quaternion rotationY(float angle) {
        return rotation(0.0f, angle, 0.0f);
    }

    /**
     * Set this quaternion to represent a rotation of the given degrees about the z axis.
     * 
     * @param angle
     *              the angle in degrees to rotate about the z axis
     * @return this
     */
    public Quaternion rotationZ(float angle) {
        return rotation(0.0f, 0.0f, angle);
    }

    /**
     * Set this Quaternion to be a representation of the rotational component of the given matrix.
     * 
     * @param mat
     *          the matrix whose rotational component is used to set this Quaternion
     * @return this
     */
    public Quaternion set(Matrix4f mat) {
        double t;
        double tr = mat.m00 + mat.m11 + mat.m22;
        if (tr >= 0.0) {
            t = Math.sqrt(tr + 1.0);
            w = (float) (t * 0.5);
            t = 0.5 / t;
            x = (float) ((mat.m12 - mat.m21) * t);
            y = (float) ((mat.m20 - mat.m02) * t);
            z = (float) ((mat.m01 - mat.m10) * t);
        } else {
            double max = Math.max(Math.max(mat.m00, mat.m11), mat.m22);
            if (max == mat.m00) {
                t = Math.sqrt(mat.m00 - (mat.m11 + mat.m22) + 1.0);
                x = (float) (t * 0.5);
                t = 0.5 / t;
                y = (float) ((mat.m10 + mat.m01) * t);
                z = (float) ((mat.m02 + mat.m20) * t);
                w = (float) ((mat.m12 - mat.m21) * t);
            } else if (max == mat.m11) {
                t = Math.sqrt(mat.m11 - (mat.m22 + mat.m00) + 1.0);
                y = (float) (t * 0.5);
                t = 0.5 / t;
                z = (float) ((mat.m21 + mat.m12) * t);
                x = (float) ((mat.m10 + mat.m01) * t);
                w = (float) ((mat.m20 - mat.m02) * t);
            } else {
                t = Math.sqrt(mat.m22 - (mat.m00 + mat.m11) + 1.0);
                z = (float) (t * 0.5);
                t = 0.5 / t;
                x = (float) ((mat.m02 + mat.m20) * t);
                y = (float) ((mat.m21 + mat.m12) * t);
                w = (float) ((mat.m01 - mat.m10) * t);
            }
        }
        return this;
    }

    /**
     * Set this Quaternion to be a representation of the rotational component of the given matrix.
     * 
     * @param mat
     *          the matrix whose rotational component is used to set this Quaternion
     * @return this
     */
    public Quaternion set(Matrix3f mat) {
        double t;
        double tr = mat.m00 + mat.m11 + mat.m22;
        if (tr >= 0.0) {
            t = Math.sqrt(tr + 1.0);
            w = (float) (t * 0.5);
            t = 0.5 / t;
            x = (float) ((mat.m12 - mat.m21) * t);
            y = (float) ((mat.m20 - mat.m02) * t);
            z = (float) ((mat.m01 - mat.m10) * t);
        } else {
            double max = Math.max(Math.max(mat.m00, mat.m11), mat.m22);
            if (max == mat.m00) {
                t = Math.sqrt(mat.m00 - (mat.m11 + mat.m22) + 1.0);
                x = (float) (t * 0.5);
                t = 0.5 / t;
                y = (float) ((mat.m10 + mat.m01) * t);
                z = (float) ((mat.m02 + mat.m20) * t);
                w = (float) ((mat.m12 - mat.m21) * t);
            } else if (max == mat.m11) {
                t = Math.sqrt(mat.m11 - (mat.m22 + mat.m00) + 1.0);
                y = (float) (t * 0.5);
                t = 0.5 / t;
                z = (float) ((mat.m21 + mat.m12) * t);
                x = (float) ((mat.m10 + mat.m01) * t);
                w = (float) ((mat.m20 - mat.m02) * t);
            } else {
                t = Math.sqrt(mat.m22 - (mat.m00 + mat.m11) + 1.0);
                z = (float) (t * 0.5);
                t = 0.5 / t;
                x = (float) ((mat.m02 + mat.m20) * t);
                y = (float) ((mat.m21 + mat.m12) * t);
                w = (float) ((mat.m01 - mat.m10) * t);
            }
        }
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
    public Quaternion mul(Quaternion q) {
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
    public Quaternion mul(Quaternion q, Quaternion dest) {
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
    public static void mul(Quaternion a, Quaternion b, Quaternion dest) {
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
    public Quaternion transform(Vector3f vec){
        return transform(vec, vec);
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
    public Quaternion transform(Vector4f vec){
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
    public Quaternion transform(Vector3f vec, Vector3f dest) {
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
        dest.set((float) ((1.0 - (num5 + num6)) * vec.x + (num7 - num12) * vec.y + (num8 + num11) * vec.z),
                 (float) ((num7 + num12) * vec.x + (1.0 - (num4 + num6)) * vec.y + (num9 - num10) * vec.z),
                 (float) ((num8 - num11) * vec.x + (num9 + num10) * vec.y + (1.0 - (num4 + num5)) * vec.z));
        return this;
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
    public Quaternion transform(Vector4f vec, Vector4f dest) {
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
        dest.set((float) ((1.0 - (num5 + num6)) * vec.x + (num7 - num12) * vec.y + (num8 + num11) * vec.z),
                 (float) ((num7 + num12) * vec.x + (1.0 - (num4 + num6)) * vec.y + (num9 - num10) * vec.z),
                 (float) ((num8 - num11) * vec.x + (num9 + num10) * vec.y + (1.0 - (num4 + num5)) * vec.z),
                 dest.w);
        return this;
    }

    /**
     * Invert this Quaternion and store the result in <code>dest</code>.
     * 
     * @param dest
     *          will hold the result
     * @return this
     */
    public Quaternion invert(Quaternion dest) {
        invert(this, dest);
        return this;
    }

    /**
     * Invert this Quaternion.
     * 
     * @return this
     */
    public Quaternion invert() {
        float norm = (x * x + y * y + z * z + w * w);
        x = -x / norm;
        y = -y / norm;
        z = -z / norm;
        w = w / norm;
        return this;
    }

    /**
     * Inverts q and stores the results in dest. Does not modify q
     */
    public static void invert(Quaternion q, Quaternion dest) {
        float norm = (q.x * q.x + q.y * q.y + q.z * q.z + q.w * q.w);
        dest.x = -q.x / norm;
        dest.y = -q.y / norm;
        dest.z = -q.z / norm;
        dest.w = q.w / norm;
    }

    /**
     * Divide <code>this</code> quaternion by <code>b</code> and store the result in <code>dest</code>.
     * <p>
     * The division expressed using the inverse is performed in the following way:
     * <p>
     * <tt>dest = this * b^-1</tt>, where <tt>b^-1</tt> is the inverse of <code>b</code>.
     * 
     * @param b
     *          the {@link Quaternion} to divide this by
     * @param dest
     *          will hold the result
     * @return this
     */
    public Quaternion div(Quaternion b, Quaternion dest) {
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
     *          the {@link Quaternion} to divide this by
     * @return this
     */
    public Quaternion div(Quaternion b) {
        div(this, b, this);
        return this;
    }

    /**
     * Divide <code>a</code> by <code>b</code> and stores the results in <code>dest</code>.
     * <p>
     * The division expressed with the inverse is performed in the following way:
     * <p>
     * <tt>dest = a * b^-1</tt>, where <tt>b^-1</tt> is the inverse of <code>b</code>.
     */
    public static void div(Quaternion a, Quaternion b, Quaternion dest) {
        float norm = (b.x * b.x + b.y * b.y + b.z * b.z + b.w * b.w);
        float x = -b.x / norm;
        float y = -b.y / norm;
        float z = -b.z / norm;
        float w = b.w / norm;
        dest.set(a.w * x + a.x * w + a.y * z - a.z * y,
                 a.w * y - a.x * z + a.y * w + a.z * x,
                 a.w * z + a.x * y - a.y * x + a.z * w,
                 a.w * w - a.x * x - a.y * y - a.z * z);
    }

    /**
     * Conjugates this Quaternion.
     * 
     * @return this
     */
    public Quaternion conjugate() {
        x = -x;
        y = -y;
        z = -z;
        return this;
    }

    /**
     * Conjugates a and stores the results in dest. Does not modify a
     */
    public static void conjugate(Quaternion a, Quaternion dest) {
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
    public Quaternion identity() {
        x = 0.0f;
        y = 0.0f;
        z = 0.0f;
        w = 1.0f;
        return this;
    }

    /**
     * Set this Quaternion from the supplied euler angles (in degrees) with rotation order XYZ.
     * 
     * @return this
     */
    public Quaternion setEulerAnglesDegXYZ(Vector3f angles) {
        setEulerAnglesDegXYZ(angles.x, angles.y, angles.z);
        return this;
    }

    /**
     * Set this Quaternion from the supplied euler angles (in radians) with rotation order XYZ.
     * 
     * @return this
     */
    public Quaternion setEulerAnglesRadXYZ(Vector3f angles) {
        setEulerAnglesRadXYZ(angles.x, angles.y, angles.z);
        return this;
    }

    /**
     * Set this Quaternion from the supplied euler angles (in degrees) with rotation order ZYX.
     * 
     * @return this
     */
    public Quaternion setEulerAnglesDegZYX(Vector3f angles) {
        setEulerAnglesDegZYX(angles.x, angles.y, angles.z);
        return this;
    }

    /**
     * Set this Quaternion from the supplied euler angles (in radians) with rotation order ZYX.
     * 
     * @return this
     */
    public Quaternion setEulerAnglesRadZYX(Vector3f angles) {
        setEulerAnglesRadZYX(angles.x, angles.y, angles.z);
        return this;
    }

    /**
     * Set this Quaternion from the supplied euler angles (in degrees) with rotation order XYZ.
     * <p>
     * This method implements the solution outlined in <a href="http://gamedev.stackexchange.com/questions/13436/glm-euler-angles-to-quaternion#answer-13446">this stackexchange answer</a>.
     * 
     * @return this
     */
    public Quaternion setEulerAnglesDegXYZ(float rotationAboutX, float rotationAboutY, float rotationAboutZ) {
        float sx = (float) Math.sin(Math.toRadians(rotationAboutX) * 0.5);
        float cx = (float) Math.cos(Math.toRadians(rotationAboutX) * 0.5);
        float sy = (float) Math.sin(Math.toRadians(rotationAboutY) * 0.5);
        float cy = (float) Math.cos(Math.toRadians(rotationAboutY) * 0.5);
        float sz = (float) Math.sin(Math.toRadians(rotationAboutZ) * 0.5);
        float cz = (float) Math.cos(Math.toRadians(rotationAboutZ) * 0.5);

        x = cx*cy*cz + sx*sy*sz;
        y = sx*cy*cz - cx*sy*sz;
        z = cx*sy*cz + sx*cy*sz;
        w = cx*cy*sz - sx*sy*cz;

        return this;
    }

    /**
     * Set this Quaternion from the supplied euler angles (in degrees) with rotation order ZYX.
     * <p>
     * This method implements the solution outlined in <a href="http://gamedev.stackexchange.com/questions/13436/glm-euler-angles-to-quaternion#answer-13446">this stackexchange answer</a>.
     * 
     * @return this
     */
    public Quaternion setEulerAnglesDegZYX(float rotationAboutX, float rotationAboutY, float rotationAboutZ) {
        float sx = (float) Math.sin(Math.toRadians(rotationAboutX) * 0.5);
        float cx = (float) Math.cos(Math.toRadians(rotationAboutX) * 0.5);
        float sy = (float) Math.sin(Math.toRadians(rotationAboutY) * 0.5);
        float cy = (float) Math.cos(Math.toRadians(rotationAboutY) * 0.5);
        float sz = (float) Math.sin(Math.toRadians(rotationAboutZ) * 0.5);
        float cz = (float) Math.cos(Math.toRadians(rotationAboutZ) * 0.5);

        x = cx*cy*cz - sx*sy*sz;
        y = sx*cy*cz + cx*sy*sz;
        z = cx*sy*cz - sx*cy*sz;
        w = cx*cy*sz + sx*sy*cz;

        return this;
    }

    /**
     * Return the length of this quaternion.
     * 
     * @return the length
     */
    public float length() {
        return x * x + y * y + z * z + w * w;
    }

    /**
     * Return the length of q.
     * 
     * @return the length
     */
    public static float length(Quaternion q) {
        return q.x * q.x + q.y * q.y + q.z * q.z + q.w * q.w;
    }

    /**
     * Set this Quaternion from the supplied euler angles (in radians) with rotation order XYZ.
     * <p>
     * This method implements the solution outlined in <a href="http://gamedev.stackexchange.com/questions/13436/glm-euler-angles-to-quaternion#answer-13446">this stackexchange answer</a>.
     * 
     * @return this
     */
    public Quaternion setEulerAnglesRadXYZ(float rotationAboutX, float rotationAboutY, float rotationAboutZ) {
        float sx = (float) Math.sin(rotationAboutX * 0.5f);
        float cx = (float) Math.cos(rotationAboutX * 0.5f);
        float sy = (float) Math.sin(rotationAboutY * 0.5f);
        float cy = (float) Math.cos(rotationAboutY * 0.5f);
        float sz = (float) Math.sin(rotationAboutZ * 0.5f);
        float cz = (float) Math.cos(rotationAboutZ * 0.5f);

        x = cx*cy*cz + sx*sy*sz;
        y = sx*cy*cz - cx*sy*sz;
        z = cx*sy*cz + sx*cy*sz;
        w = cx*cy*sz - sx*sy*cz;

        return this;
    }

    /**
     * Set this Quaternion from the supplied euler angles (in radians) with rotation order ZYX.
     * <p>
     * This method implements the solution outlined in <a href="http://gamedev.stackexchange.com/questions/13436/glm-euler-angles-to-quaternion#answer-13446">this stackexchange answer</a>.
     * 
     * @return this
     */
    public Quaternion setEulerAnglesRadZYX(float rotationAboutX, float rotationAboutY, float rotationAboutZ) {
        float sx = (float) Math.sin(rotationAboutX * 0.5f);
        float cx = (float) Math.cos(rotationAboutX * 0.5f);
        float sy = (float) Math.sin(rotationAboutY * 0.5f);
        float cy = (float) Math.cos(rotationAboutY * 0.5f);
        float sz = (float) Math.sin(rotationAboutZ * 0.5f);
        float cz = (float) Math.cos(rotationAboutZ * 0.5f);

        x = cx*cy*cz - sx*sy*sz;
        y = sx*cy*cz + cx*sy*sz;
        z = cx*sy*cz - sx*cy*sz;
        w = cx*cy*sz + sx*sy*cz;

        return this;
    }

    /**
     * Spherical linear interpolation between this Quaternion and the specified
     * target, using the specified alpha.
     * 
     * @return this
     */
    public Quaternion slerp(Quaternion target, float alpha) {
        slerp(this, target, alpha, this);
        return this;
    }

    /**
     * Spherical linear interpolation between the start and target Quaternions,
     * using the specified alpha, and storing the results in dest. Neither the
     * start or target are modified
     */
    public static void slerp(Quaternion start, Quaternion target, float alpha, Quaternion dest) {
        float dot = Math.abs(start.x * target.x + start.y * target.y + start.z * target.z + start.w * target.w);
        float scale1, scale2;

        if ((1.0f - dot) > 0.1) {
            float angle = (float) Math.acos(dot);
            float sinAngle = 1f / (float) Math.sin(angle);

            scale1 = ((float) Math.sin((1f - alpha) * angle) * sinAngle);
            scale2 = ((float) Math.sin((alpha * angle)) * sinAngle);
        } else {
            scale1 = 1f - alpha;
            scale2 = alpha;
        }

        if (dot < 0.f) {
            scale2 = -scale2;
        }

        dest.x = (scale1 * start.x) + (scale2 * target.x);
        dest.y = (scale1 * start.y) + (scale2 * target.y);
        dest.z = (scale1 * start.z) + (scale2 * target.z);
        dest.w = (scale1 * start.w) + (scale2 * target.w);
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
    public Quaternion nlerp(Quaternion q, float factor) {
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
    public Quaternion nlerp(Quaternion q, float factor, Quaternion dest) {
        float dot = this.dot(q);
        float blendI = 1.0f - factor;
        if (dot < 0.0f) {
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
     * Set <code>dest</code> to be a rotation leading <code>sourcePoint</code> to rotate to <code>destPoint</code>.
     */
    public static void lookAt(Vector3f sourcePoint, Vector3f destPoint, Vector3f up, Vector3f forward, Quaternion dest) {
        float dirX = destPoint.x - sourcePoint.x;
        float dirY = destPoint.y - sourcePoint.y;
        float dirZ = destPoint.z - sourcePoint.z;

        float length = (float) Math.sqrt(dirX * dirX + dirY * dirY + dirZ * dirZ);

        dirX /= length;
        dirY /= length;
        dirZ /= length;

        float dot = (forward.x * dirX) + (forward.y * dirY) + (forward.z * dirZ);

        if (Math.abs(dot - (-1.0f)) < 1E-6f) {
            dest.x = up.x;
            dest.y = up.y;
            dest.z = up.z;
            dest.w = (float) Math.PI;
            return;
        }

        if (Math.abs(dot - (1.0f)) < 1E-6f) {
            dest.x = 0.0f;
            dest.y = 0.0f;
            dest.z = 0.0f;
            dest.w = 1.0f;
            return;
        }

        float rotAngle = (float) Math.acos(dot);

        float rotAxisX = forward.y * dirZ - forward.z * dirY;
        float rotAxisY = forward.z * dirX - forward.x * dirZ;
        float rotAxisZ = forward.x * dirY - forward.y * dirX;

        length = (float) Math.sqrt(rotAxisX * rotAxisX + rotAxisY * rotAxisY + rotAxisZ * rotAxisZ);

        rotAxisX /= length;
        rotAxisY /= length;
        rotAxisZ /= length;

        dest.rotationAxis(rotAngle, rotAxisX, rotAxisY, rotAxisZ);
    }

    /**
     * Set <code>dest</code> to be a rotation leading <code>sourcePoint</code> to rotate to <code>destPoint</code>.
     * 
     * @return this
     */
    public Quaternion lookAt(Vector3f sourcePoint, Vector3f destPoint, Vector3f up, Vector3f forward) {
        lookAt(sourcePoint, destPoint, up, forward, this);
        return this;
    }

    /**
     * Apply a rotation to this quaternion that maps the given direction to the positive Z axis.
     * <p>
     * If <code>Q</code> is <code>this</code> quaternion and <code>R</code> the quaternion representing the 
     * specified rotation, then the new quaternion will be <code>Q * R</code>. So when transforming a
     * vector <code>v</code> with the new quaternion by using <code>Q * R * v</code>, the
     * rotation added by this method will be applied first!
     * <p>
     * Reference: <a href="http://answers.unity3d.com/questions/467614/what-is-the-source-code-of-quaternionlookrotation.html">http://answers.unity3d.com</a>
     * 
     * @return this
     */
    public Quaternion rotateDirToPositiveZ(Vector3f dir, Vector3f up) {
        return rotateDirToPositiveZ(dir.x, dir.y, dir.z, up.x, up.y, up.z, this);
    }

    /**
     * Apply a rotation to this quaternion that maps the given direction to the positive Z axis, and store the result in <code>dest</code>.
     * <p>
     * If <code>Q</code> is <code>this</code> quaternion and <code>R</code> the quaternion representing the 
     * specified rotation, then the new quaternion will be <code>Q * R</code>. So when transforming a
     * vector <code>v</code> with the new quaternion by using <code>Q * R * v</code>, the
     * rotation added by this method will be applied first!
     * <p>
     * Reference: <a href="http://answers.unity3d.com/questions/467614/what-is-the-source-code-of-quaternionlookrotation.html">http://answers.unity3d.com</a>
     * 
     * @return this
     */
    public Quaternion rotateDirToPositiveZ(Vector3f dir, Vector3f up, Quaternion dest) {
        return rotateDirToPositiveZ(dir.x, dir.y, dir.z, up.x, up.y, up.z, dest);
    }

    /**
     * Apply a rotation to this quaternion that maps the given direction to the positive Z axis.
     * <p>
     * If <code>Q</code> is <code>this</code> quaternion and <code>R</code> the quaternion representing the 
     * specified rotation, then the new quaternion will be <code>Q * R</code>. So when transforming a
     * vector <code>v</code> with the new quaternion by using <code>Q * R * v</code>, the
     * rotation added by this method will be applied first!
     * <p>
     * Reference: <a href="http://answers.unity3d.com/questions/467614/what-is-the-source-code-of-quaternionlookrotation.html">http://answers.unity3d.com</a>
     * 
     * @return this
     */
    public Quaternion rotateDirToPositiveZ(float dirX, float dirY, float dirZ, float upX, float upY, float upZ) {
        return rotateDirToPositiveZ(dirX, dirY, dirZ, upX, upY, upZ, this);
    }

    /**
     * Apply a rotation to this quaternion that maps the given direction to the positive Z axis, and store the result in <code>dest</code>.
     * <p>
     * Because there are multiple possibilities for such a rotations, this method will choose the one that ensures the given up direction to remain
     * parallel to the plane spanned by the <tt>up</tt> and <tt>dir</tt> vectors. 
     * <p>
     * If <code>Q</code> is <code>this</code> quaternion and <code>R</code> the quaternion representing the 
     * specified rotation, then the new quaternion will be <code>Q * R</code>. So when transforming a
     * vector <code>v</code> with the new quaternion by using <code>Q * R * v</code>, the
     * rotation added by this method will be applied first!
     * <p>
     * Reference: <a href="http://answers.unity3d.com/questions/467614/what-is-the-source-code-of-quaternionlookrotation.html">http://answers.unity3d.com</a>
     * 
     * @return this
     */
    public Quaternion rotateDirToPositiveZ(float dirX, float dirY, float dirZ, float upX, float upY, float upZ, Quaternion dest) {
        // Normalize direction
        float dirLength = (float) Math.sqrt(dirX * dirX + dirY * dirY + dirZ * dirZ);
        float dirnX = dirX / dirLength;
        float dirnY = dirY / dirLength;
        float dirnZ = dirZ / dirLength;
        float upLength = (float) Math.sqrt(upX * upX + upY * upY + upZ * upZ);
        float upnX = upX / upLength;
        float upnY = upY / upLength;
        float upnZ = upZ / upLength;
        // left = up x dir
        float leftX, leftY, leftZ;
        leftX = upnY * dirnZ - upnZ * dirnY;
        leftY = upnZ * dirnX - upnX * dirnZ;
        leftZ = upnX * dirnY - upnY * dirnX;
        // up = direction x left
        upnX = dirnY * leftZ - dirnZ * leftY;
        upnY = dirnZ * leftX - dirnX * leftZ;
        upnZ = dirnX * leftY - dirnY * leftX;
        
        /* Convert orthonormal basis vectors to quaternion */
        float x, y, z, w;
        double t;
        double tr = leftX + upnY + dirnZ;
        if (tr >= 0.0) {
            t = Math.sqrt(tr + 1.0);
            w = (float) (t * 0.5);
            t = 0.5 / t;
            x = (float) ((dirnY - upnZ) * t);
            y = (float) ((leftZ - dirnX) * t);
            z = (float) ((upnX - leftY) * t);
        } else {
            if (leftX >= upnY && leftX >= dirnZ) {
                t = Math.sqrt(1.0 + leftX - upnY - dirnZ);
                x = (float) (t * 0.5);
                t = 0.5 / t;
                y = (float) ((leftY + upnX) * t);
                z = (float) ((dirnX + leftZ) * t);
                w = (float) ((dirnY - upnZ) * t);
            } else if (upnY > dirnZ) {
                t = Math.sqrt(1.0 + upnY - leftX - dirnZ);
                y = (float) (t * 0.5);
                t = 0.5 / t;
                x = (float) ((leftY + upnX) * t);
                z = (float) ((upnZ + dirnY) * t);
                w = (float) ((leftZ - dirnX) * t);
            } else {
                t = Math.sqrt(1.0 + dirnZ - leftX - upnY);
                z = (float) (t * 0.5);
                t = 0.5 / t;
                x = (float) ((dirnX + leftZ) * t);
                y = (float) ((upnZ + dirnY) * t);
                w = (float) ((upnX - leftY) * t);
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
     * Apply a quaternion to <code>this</code> that rotates the <tt>fromDir</tt> vector to point along <tt>toDir</tt> and
     * store the result in <code>dest</code>.
     * <p>
     * Because there can be multiple possible rotations, this method chooses the one with the shortest arc.
     * <p>
     * If <code>Q</code> is <code>this</code> quaternion and <code>R</code> the quaternion representing the 
     * specified rotation, then the new quaternion will be <code>Q * R</code>. So when transforming a
     * vector <code>v</code> with the new quaternion by using <code>Q * R * v</code>, the
     * rotation added by this method will be applied first!
     * 
     * @param dest
     *          will hold the result
     * @return this
     */
    public Quaternion rotateTo(float fromDirX, float fromDirY, float fromDirZ, float toDirX, float toDirY, float toDirZ, Quaternion dest) {
        double fromLength = Math.sqrt(fromDirX * fromDirX + fromDirY * fromDirY + fromDirZ * fromDirZ);
        double fromX = fromDirX / fromLength;
        double fromY = fromDirY / fromLength;
        double fromZ = fromDirZ / fromLength;
        double toLength = Math.sqrt(fromDirX * fromDirX + fromDirY * fromDirY + fromDirZ * fromDirZ);
        double toX = toDirX / toLength;
        double toY = toDirY / toLength;
        double toZ = toDirZ / toLength;
        double dot = fromX * toX + fromY * toY + fromZ * toZ;
        float x, y, z, w;
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
            double angleR = Math.toRadians(180.0);
            double s = Math.sin(angleR / 2.0);
            x = (float) (axisX * s);
            y = (float) (axisY * s);
            z = (float) (axisZ * s);
            w = (float) Math.cos(angleR / 2.0);
        } else if (dot < 1.0) {
            double s = Math.sqrt((1.0 + dot) * 2.0);
            double invs = 1.0 / s;
            double crossX = fromY * toZ - fromZ * toY;
            double crossY = fromZ * toX - fromX * toZ;
            double crossZ = fromX * toY - fromY * toX;
            x = (float) (crossX * invs);
            y = (float) (crossY * invs);
            z = (float) (crossZ * invs);
            w = (float) (s * 0.5);
            float norm = (float) Math.sqrt(x * x + y * y + z * z + w * w);
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
     * Apply a quaternion to <code>this</code> that rotates the <tt>fromDir</tt> vector to point along <tt>toDir</tt>.
     * <p>
     * Because there can be multiple possible rotations, this method chooses the one with the shortest arc.
     * <p>
     * If <code>Q</code> is <code>this</code> quaternion and <code>R</code> the quaternion representing the 
     * specified rotation, then the new quaternion will be <code>Q * R</code>. So when transforming a
     * vector <code>v</code> with the new quaternion by using <code>Q * R * v</code>, the
     * rotation added by this method will be applied first!
     * 
     * @return this
     */
    public Quaternion rotateTo(float fromDirX, float fromDirY, float fromDirZ, float toDirX, float toDirY, float toDirZ) {
        return rotateTo(fromDirX, fromDirY, fromDirZ, toDirX, toDirY, toDirZ, this);
    }

    /**
     * Apply a quaternion to <code>this</code> that rotates the <code>fromDir</code> vector to point along <code>toDir</code> and
     * store the result in <code>dest</code>.
     * <p>
     * Because there can be multiple possible rotations, this method chooses the one with the shortest arc.
     * <p>
     * If <code>Q</code> is <code>this</code> quaternion and <code>R</code> the quaternion representing the 
     * specified rotation, then the new quaternion will be <code>Q * R</code>. So when transforming a
     * vector <code>v</code> with the new quaternion by using <code>Q * R * v</code>, the
     * rotation added by this method will be applied first!
     * 
     * @param fromDir
     *          the starting direction
     * @param toDir
     *          the destination direction
     * @param dest
     *          will hold the result
     * @return this
     */
    public Quaternion rotateTo(Vector3f fromDir, Vector3f toDir, Quaternion dest) {
        return rotateTo(fromDir.x, fromDir.y, fromDir.z, toDir.x, toDir.y, toDir.z, dest);
    }

    /**
     * Apply a quaternion to <code>this</code> that rotates the <code>fromDir</code> vector to point along <code>toDir</code>.
     * <p>
     * Because there can be multiple possible rotations, this method chooses the one with the shortest arc.
     * <p>
     * If <code>Q</code> is <code>this</code> quaternion and <code>R</code> the quaternion representing the 
     * specified rotation, then the new quaternion will be <code>Q * R</code>. So when transforming a
     * vector <code>v</code> with the new quaternion by using <code>Q * R * v</code>, the
     * rotation added by this method will be applied first!
     * 
     * @param fromDir
     *          the starting direction
     * @param toDir
     *          the destination direction
     * @return this
     */
    public Quaternion rotateTo(Vector3f fromDir, Vector3f toDir) {
        return rotateTo(fromDir.x, fromDir.y, fromDir.z, toDir.x, toDir.y, toDir.z, this);
    }

    /**
     * Apply a rotation to <code>this</code> quaternion rotating the given degrees about the basis unit axes of the
     * cartesian space and store the result in <code>dest</code>.
     * <p>
     * If <code>Q</code> is <code>this</code> quaternion and <code>R</code> the quaternion representing the 
     * specified rotation, then the new quaternion will be <code>Q * R</code>. So when transforming a
     * vector <code>v</code> with the new quaternion by using <code>Q * R * v</code>, the
     * rotation added by this method will be applied first!
     * 
     * @param anglesXYZ
     *              the angles in degrees to rotate about the x, y and z axes, respectively
     * @param dest
     *              will hold the result
     * @return this
     */
    public Quaternion rotate(Vector3f anglesXYZ, Quaternion dest) {
        return rotate(anglesXYZ.x, anglesXYZ.y, anglesXYZ.z, dest);
    }

    /**
     * Apply a rotation to <code>this</code> quaternion rotating the given degrees about the basis unit axes
     * of the cartesian space.
     * <p>
     * If <code>Q</code> is <code>this</code> quaternion and <code>R</code> the quaternion representing the 
     * specified rotation, then the new quaternion will be <code>Q * R</code>. So when transforming a
     * vector <code>v</code> with the new quaternion by using <code>Q * R * v</code>, the
     * rotation added by this method will be applied first!
     * 
     * @param anglesXYZ
     *              the angles in degrees to rotate about the x, y and z axes, respectively
     * @return this
     */
    public Quaternion rotate(Vector3f anglesXYZ) {
        return rotate(anglesXYZ.x, anglesXYZ.y, anglesXYZ.z, this);
    }

    /**
     * Apply a rotation to <code>this</code> quaternion rotating the given degrees about the basis unit axes of the cartesian space.
     * <p>
     * If <code>Q</code> is <code>this</code> quaternion and <code>R</code> the quaternion representing the 
     * specified rotation, then the new quaternion will be <code>Q * R</code>. So when transforming a
     * vector <code>v</code> with the new quaternion by using <code>Q * R * v</code>, the
     * rotation added by this method will be applied first!
     * 
     * @param angleX
     *              the angle in degrees to rotate about the x axis
     * @param angleY
     *              the angle in degrees to rotate about the y axis
     * @param angleZ
     *              the angle in degrees to rotate about the z axis
     * @return this
     */
    public Quaternion rotate(float angleX, float angleY, float angleZ) {
        return rotate(angleX, angleY, angleZ, this);
    }

    /**
     * Apply a rotation to <code>this</code> quaternion rotating the given degrees about the basis unit axes of the
     * cartesian space and store the result in <code>dest</code>.
     * <p>
     * If <code>Q</code> is <code>this</code> quaternion and <code>R</code> the quaternion representing the 
     * specified rotation, then the new quaternion will be <code>Q * R</code>. So when transforming a
     * vector <code>v</code> with the new quaternion by using <code>Q * R * v</code>, the
     * rotation added by this method will be applied first!
     * 
     * @param angleX
     *              the angle in degrees to rotate about the x axis
     * @param angleY
     *              the angle in degrees to rotate about the y axis
     * @param angleZ
     *              the angle in degrees to rotate about the z axis
     * @param dest
     *              will hold the result
     * @return this
     */
    public Quaternion rotate(float angleX, float angleY, float angleZ, Quaternion dest) {
        double thetaX = Math.toRadians(angleX) * 0.5;
        double thetaY = Math.toRadians(angleY) * 0.5;
        double thetaZ = Math.toRadians(angleZ) * 0.5;
        double thetaMagSq = thetaX * thetaX + thetaY * thetaY + thetaZ * thetaZ;
        double s;
        double dqX, dqY, dqZ, dqW;
        if (thetaMagSq * thetaMagSq / 24.0f < 1E-8f) {
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
        /* Pre-multiplication */
//        dest.set((float) (dqW * x + dqX * w + dqY * z - dqZ * y),
//                 (float) (dqW * y - dqX * z + dqY * w + dqZ * x),
//                 (float) (dqW * z + dqX * y - dqY * x + dqZ * w),
//                 (float) (dqW * w - dqX * x - dqY * y - dqZ * z));
        /* Post-multiplication (like matrices multiply) */
        dest.set((float) (w * dqX + x * dqW + y * dqZ - z * dqY),
                 (float) (w * dqY - x * dqZ + y * dqW + z * dqX),
                 (float) (w * dqZ + x * dqY - y * dqX + z * dqW),
                 (float) (w * dqW - x * dqX - y * dqY - z * dqZ));
        return this;
    }

    /**
     * Apply a rotation to <code>this</code> quaternion rotating the given degrees about the x axis.
     * <p>
     * If <code>Q</code> is <code>this</code> quaternion and <code>R</code> the quaternion representing the 
     * specified rotation, then the new quaternion will be <code>Q * R</code>. So when transforming a
     * vector <code>v</code> with the new quaternion by using <code>Q * R * v</code>, the
     * rotation added by this method will be applied first!
     * 
     * @param angle
     *              the angle in degrees to rotate about the x axis
     * @return this
     */
    public Quaternion rotateX(float angle) {
        return rotate(angle, 0.0f, 0.0f, this);
    }

    /**
     * Apply a rotation to <code>this</code> quaternion rotating the given degrees about the x axis
     * and store the result in <code>dest</code>.
     * <p>
     * If <code>Q</code> is <code>this</code> quaternion and <code>R</code> the quaternion representing the 
     * specified rotation, then the new quaternion will be <code>Q * R</code>. So when transforming a
     * vector <code>v</code> with the new quaternion by using <code>Q * R * v</code>, the
     * rotation added by this method will be applied first!
     * 
     * @param angle
     *              the angle in degrees to rotate about the x axis
     * @param dest
     *              will hold the result
     * @return this
     */
    public Quaternion rotateX(float angle, Quaternion dest) {
        return rotate(angle, 0.0f, 0.0f, dest);
    }

    /**
     * Apply a rotation to <code>this</code> quaternion rotating the given degrees about the y axis.
     * <p>
     * If <code>Q</code> is <code>this</code> quaternion and <code>R</code> the quaternion representing the 
     * specified rotation, then the new quaternion will be <code>Q * R</code>. So when transforming a
     * vector <code>v</code> with the new quaternion by using <code>Q * R * v</code>, the
     * rotation added by this method will be applied first!
     * 
     * @param angle
     *              the angle in degrees to rotate about the y axis
     * @return this
     */
    public Quaternion rotateY(float angle) {
        return rotate(0.0f, angle, 0.0f, this);
    }

    /**
     * Apply a rotation to <code>this</code> quaternion rotating the given degrees about the y axis
     * and store the result in <code>dest</code>.
     * <p>
     * If <code>Q</code> is <code>this</code> quaternion and <code>R</code> the quaternion representing the 
     * specified rotation, then the new quaternion will be <code>Q * R</code>. So when transforming a
     * vector <code>v</code> with the new quaternion by using <code>Q * R * v</code>, the
     * rotation added by this method will be applied first!
     * 
     * @param angle
     *              the angle in degrees to rotate about the y axis
     * @param dest
     *              will hold the result
     * @return this
     */
    public Quaternion rotateY(float angle, Quaternion dest) {
        return rotate(0.0f, angle, 0.0f, dest);
    }

    /**
     * Apply a rotation to <code>this</code> quaternion rotating the given degrees about the z axis.
     * <p>
     * If <code>Q</code> is <code>this</code> quaternion and <code>R</code> the quaternion representing the 
     * specified rotation, then the new quaternion will be <code>Q * R</code>. So when transforming a
     * vector <code>v</code> with the new quaternion by using <code>Q * R * v</code>, the
     * rotation added by this method will be applied first!
     * 
     * @param angle
     *              the angle in degrees to rotate about the z axis
     * @return this
     */
    public Quaternion rotateZ(float angle) {
        return rotate(0.0f, 0.0f, angle, this);
    }

    /**
     * Apply a rotation to <code>this</code> quaternion rotating the given degrees about the z axis
     * and store the result in <code>dest</code>.
     * <p>
     * If <code>Q</code> is <code>this</code> quaternion and <code>R</code> the quaternion representing the 
     * specified rotation, then the new quaternion will be <code>Q * R</code>. So when transforming a
     * vector <code>v</code> with the new quaternion by using <code>Q * R * v</code>, the
     * rotation added by this method will be applied first!
     * 
     * @param angle
     *              the angle in degrees to rotate about the z axis
     * @param dest
     *              will hold the result
     * @return this
     */
    public Quaternion rotateZ(float angle, Quaternion dest) {
        return rotate(0.0f, 0.0f, angle, dest);
    }

    /**
     * Apply a rotation to <code>this</code> quaternion rotating the given degrees about the specified axis
     * and store the result in <code>dest</code>.
     * <p>
     * If <code>Q</code> is <code>this</code> quaternion and <code>R</code> the quaternion representing the 
     * specified rotation, then the new quaternion will be <code>Q * R</code>. So when transforming a
     * vector <code>v</code> with the new quaternion by using <code>Q * R * v</code>, the
     * rotation added by this method will be applied first!
     * 
     * @param angle
     *              the angle in degrees to rotate about the specified axis
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
    public Quaternion rotateAxis(float angle, float axisX, float axisY, float axisZ, Quaternion dest) {
        double hangle = Math.toRadians(angle / 2.0);
        double sinAngle = Math.sin(hangle);
        double vLength = Math.sqrt(axisX * axisX + axisY * axisY + axisZ * axisZ);

        double rx = (axisX / vLength) * sinAngle;
        double ry = (axisY / vLength) * sinAngle;
        double rz = (axisZ / vLength) * sinAngle;
        double rw = Math.cos(hangle);

        dest.set((float) (w * rx + x * rw + y * rz - z * ry),
                 (float) (w * ry - x * rz + y * rw + z * rx),
                 (float) (w * rz + x * ry - y * rx + z * rw),
                 (float) (w * rw - x * rx - y * ry - z * rz));
        return this;
    }

    /**
     * Apply a rotation to <code>this</code> quaternion rotating the given degrees about the specified axis.
     * <p>
     * If <code>Q</code> is <code>this</code> quaternion and <code>R</code> the quaternion representing the 
     * specified rotation, then the new quaternion will be <code>Q * R</code>. So when transforming a
     * vector <code>v</code> with the new quaternion by using <code>Q * R * v</code>, the
     * rotation added by this method will be applied first!
     * 
     * @param angle
     *              the angle in degrees to rotate about the specified axis
     * @param axis
     *              the rotation axis
     * @return this
     */
    public Quaternion rotateAxis(float angle, Vector3f axis) {
        return rotateAxis(angle, axis.x, axis.y, axis.z, this);
    }

    /**
     * Apply a rotation to <code>this</code> quaternion rotating the given degrees about the specified axis.
     * <p>
     * If <code>Q</code> is <code>this</code> quaternion and <code>R</code> the quaternion representing the 
     * specified rotation, then the new quaternion will be <code>Q * R</code>. So when transforming a
     * vector <code>v</code> with the new quaternion by using <code>Q * R * v</code>, the
     * rotation added by this method will be applied first!
     * 
     * @param angle
     *              the angle in degrees to rotate about the specified axis
     * @param axisX
     *              the x coordinate of the rotation axis
     * @param axisY
     *              the y coordinate of the rotation axis
     * @param axisZ
     *              the z coordinate of the rotation axis
     * @return this
     */
    public Quaternion rotateAxis(float angle, float axisX, float axisY, float axisZ) {
        return rotateAxis(angle, axisX, axisY, axisZ, this);
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
        out.writeFloat(x);
        out.writeFloat(y);
        out.writeFloat(z);
        out.writeFloat(w);
    }

    public void readExternal(ObjectInput in) throws IOException,
            ClassNotFoundException {
        x = in.readFloat();
        y = in.readFloat();
        z = in.readFloat();
        w = in.readFloat();
    }

    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Float.floatToIntBits(w);
        result = prime * result + Float.floatToIntBits(x);
        result = prime * result + Float.floatToIntBits(y);
        result = prime * result + Float.floatToIntBits(z);
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Quaternion other = (Quaternion) obj;
        if (Float.floatToIntBits(w) != Float.floatToIntBits(other.w))
            return false;
        if (Float.floatToIntBits(x) != Float.floatToIntBits(other.x))
            return false;
        if (Float.floatToIntBits(y) != Float.floatToIntBits(other.y))
            return false;
        if (Float.floatToIntBits(z) != Float.floatToIntBits(other.z))
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
    public Quaternion difference(Quaternion other) {
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
    public Quaternion difference(Quaternion other, Quaternion dest) {
        float norm = x * x + y * y + z * z + w * w;
        float x = -this.x / norm;
        float y = -this.y / norm;
        float z = -this.z / norm;
        float w = this.w / norm;
        dest.set(w * other.x + x * other.w + y * other.z - z * other.y,
                 w * other.y - x * other.z + y * other.w + z * other.x,
                 w * other.z + x * other.y - y * other.x + z * other.w,
                 w * other.w - x * other.x - y * other.y - z * other.z);
        return this;
    }

}
