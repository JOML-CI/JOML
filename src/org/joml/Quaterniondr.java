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

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Abstract base class containing the readable definition and functions for rotations expressed as
 * 4-dimensional vectors
 *
 * @author Richard Greenlees
 * @author Kai Burjack
 */
public abstract class Quaterniondr {

    /**
     * @return The first component of the vector part.            *
     */
    public abstract double x();
    /**
     * @return The second component of the vector part.
     */
    public abstract double y();
    /**
     * @return The third component of the vector part.
     */
    public abstract double z();
    /**
     * @return The real/scalar part of the quaternion.
     */
    public abstract double w();

    /**
     * Normalize this quaternion and store the result in <code>dest</code>.
     *
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Quaterniond normalize(Quaterniond dest) {
        double invNorm = 1.0 / Math.sqrt(x() * x() + y() * y() + z() * z() + w() * w());
        dest.x = x() * invNorm;
        dest.y = y() * invNorm;
        dest.z = z() * invNorm;
        dest.w = w() * invNorm;
        return dest;
    }

    /**
     * Add <code>q2</code> to this quaternion and store the result in <code>dest</code>.
     *
     * @param q2
     *          the quaternion to add to this
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Quaterniond add(Quaterniondr q2, Quaterniond dest) {
        dest.x = x() + q2.x();
        dest.y = y() + q2.y();
        dest.z = z() + q2.z();
        dest.w = w() + q2.w();
        return dest;
    }

    /**
     * Return the dot product of this {@link Quaterniond} and <code>otherQuat</code>.
     *
     * @param otherQuat
     *          the other quaternion
     * @return the dot product
     */
    public double dot(Quaterniondr otherQuat) {
        return this.x() * otherQuat.x() + this.y() * otherQuat.y() + this.z() * otherQuat.z() + this.w() * otherQuat.w();
    }

    /**
     * Return the angle in radians represented by this quaternion rotation.
     *
     * @return the angle in radians
     */
    public double angle() {
        double angle = 2.0 * Math.acos(w());
        return angle <= Math.PI ? angle : 2.0 * Math.PI - angle;
    }

    /**
     * Set the given destination matrix to the rotation represented by <code>this</code>.
     *
     * @param dest
     *          the matrix to write the rotation into
     * @return the passed in destination
     */
    public Matrix3d get(Matrix3d dest) {
        double q00 = 2.0 * x() * x();
        double q11 = 2.0 * y() * y();
        double q22 = 2.0 * z() * z();

        double q01 = 2.0 * x() * y();
        double q02 = 2.0 * x() * z();
        double q03 = 2.0 * x() * w();

        double q12 = 2.0 * y() * z();
        double q13 = 2.0 * y() * w();

        double q23 = 2.0 * z() * w();

        dest.m00 = 1.0 - q11 - q22;
        dest.m01 = q01 + q23;
        dest.m02 = q02 - q13;
        dest.m10 = q01 - q23;
        dest.m11 = 1.0 - q22 - q00;
        dest.m12 = q12 + q03;
        dest.m20 = q02 + q13;
        dest.m21 = q12 - q03;
        dest.m22 = 1.0 - q11 - q00;
        return dest;
    }

    /**
     * Set the given destination matrix to the rotation represented by <code>this</code>.
     *
     * @param dest
     *          the matrix to write the rotation into
     * @return the passed in destination
     */
    public Matrix3f get(Matrix3f dest) {
        double q00 = 2.0 * x() * x();
        double q11 = 2.0 * y() * y();
        double q22 = 2.0 * z() * z();

        double q01 = 2.0 * x() * y();
        double q02 = 2.0 * x() * z();
        double q03 = 2.0 * x() * w();

        double q12 = 2.0 * y() * z();
        double q13 = 2.0 * y() * w();

        double q23 = 2.0 * z() * w();

        dest.m00 = (float) (1.0 - q11 - q22);
        dest.m01 = (float) (q01 + q23);
        dest.m02 = (float) (q02 - q13);
        dest.m10 = (float) (q01 - q23);
        dest.m11 = (float) (1.0 - q22 - q00);
        dest.m12 = (float) (q12 + q03);
        dest.m20 = (float) (q02 + q13);
        dest.m21 = (float) (q12 - q03);
        dest.m22 = (float) (1.0 - q11 - q00);
        return dest;
    }

    /**
     * Set the given destination matrix to the rotation represented by <code>this</code>.
     *
     * @param dest
     *          the matrix to write the rotation into
     * @return the passed in destination
     */
    public Matrix4d get(Matrix4d dest) {
        double q00 = 2.0 * x() * x();
        double q11 = 2.0 * y() * y();
        double q22 = 2.0 * z() * z();

        double q01 = 2.0 * x() * y();
        double q02 = 2.0 * x() * z();
        double q03 = 2.0 * x() * w();

        double q12 = 2.0 * y() * z();
        double q13 = 2.0 * y() * w();

        double q23 = 2.0 * z() * w();

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
        return dest;
    }

    /**
     * Set the given destination matrix to the rotation represented by <code>this</code>.
     *
     * @param dest
     *          the matrix to write the rotation into
     * @return the passed in destination
     */
    public Matrix4f get(Matrix4f dest) {
        double q00 = 2.0 * x() * x();
        double q11 = 2.0 * y() * y();
        double q22 = 2.0 * z() * z();

        double q01 = 2.0 * x() * y();
        double q02 = 2.0 * x() * z();
        double q03 = 2.0 * x() * w();

        double q12 = 2.0 * y() * z();
        double q13 = 2.0 * y() * w();

        double q23 = 2.0 * z() * w();

        dest.m00 = (float) (1.0 - q11 - q22);
        dest.m01 = (float) (q01 + q23);
        dest.m02 = (float) (q02 - q13);
        dest.m03 = 0.0f;
        dest.m10 = (float) (q01 - q23);
        dest.m11 = (float) (1.0 - q22 - q00);
        dest.m12 = (float) (q12 + q03);
        dest.m13 = 0.0f;
        dest.m20 = (float) (q02 + q13);
        dest.m21 = (float) (q12 - q03);
        dest.m22 = (float) (1.0 - q11 - q00);
        dest.m30 = 0.0f;
        dest.m31 = 0.0f;
        dest.m32 = 0.0f;
        dest.m33 = 1.0f;
        return dest;
    }

    /**
     * Set the given {@link Quaterniond} to the values of <code>this</code>.
     *
     * @see Quaterniond#set(Quaterniondr)
     *
     * @param dest
     *          the {@link Quaterniond} to set
     * @return the passed in destination
     */
    public Quaterniond get(Quaterniond dest) {
        return dest.set(this);
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
     * @return dest
     */
    public Quaterniond mul(Quaterniondr q, Quaterniond dest) {
        dest.set(w() * q.x() + x() * q.w() + y() * q.z() - z() * q.y(),
                 w() * q.y() - x() * q.z() + y() * q.w() + z() * q.x(),
                 w() * q.z() + x() * q.y() - y() * q.x() + z() * q.w(),
                 w() * q.w() - x() * q.x() - y() * q.y() - z() * q.z());
        return dest;
    }

    /**
     * Multiply this quaternion by the quaternion represented via <tt>(qx, qy, qz, qw)</tt> and store the result in <code>dest</code>.
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
     * @param dest
     *            will hold the result
     * @return dest
     */
    public Quaterniond mul(double qx, double qy, double qz, double qw, Quaterniond dest) {
        dest.set(w() * qx + x() * qw + y() * qz - z() * qy,
                 w() * qy - x() * qz + y() * qw + z() * qx,
                 w() * qz + x() * qy - y() * qx + z() * qw,
                 w() * qw - x() * qx - y() * qy - z() * qz);
        return dest;
    }

    /**
     * Transform the given vector by this quaternion.
     * This will apply the rotation described by this quaternion to the given vector.
     *
     * @param vec
     *          the vector to transform
     * @return vec
     */
    public Vector3d transform(Vector3d vec){
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
     * @return vec
     */
    public Vector4d transform(Vector4d vec){
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
     * @return dest
     */
    public Vector3d transform(Vector3dr vec, Vector3d dest) {
        double num = x() * 2.0;
        double num2 = y() * 2.0;
        double num3 = z() * 2.0;
        double num4 = x() * num;
        double num5 = y() * num2;
        double num6 = z() * num3;
        double num7 = x() * num2;
        double num8 = x() * num3;
        double num9 = y() * num3;
        double num10 = w() * num;
        double num11 = w() * num2;
        double num12 = w() * num3;
        dest.set((1.0 - (num5 + num6)) * vec.x() + (num7 - num12) * vec.y() + (num8 + num11) * vec.z(),
                 (num7 + num12) * vec.x() + (1.0 - (num4 + num6)) * vec.y() + (num9 - num10) * vec.z(),
                 (num8 - num11) * vec.x() + (num9 + num10) * vec.y() + (1.0 - (num4 + num5)) * vec.z());
        return dest;
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
     * @return dest
     */
    public Vector4d transform(Vector4dr vec, Vector4d dest) {
        double num = x() * 2.0;
        double num2 = y() * 2.0;
        double num3 = z() * 2.0;
        double num4 = x() * num;
        double num5 = y() * num2;
        double num6 = z() * num3;
        double num7 = x() * num2;
        double num8 = x() * num3;
        double num9 = y() * num3;
        double num10 = w() * num;
        double num11 = w() * num2;
        double num12 = w() * num3;
        dest.set((1.0 - (num5 + num6)) * vec.x() + (num7 - num12) * vec.y() + (num8 + num11) * vec.z(),
                 (num7 + num12) * vec.x() + (1.0 - (num4 + num6)) * vec.y() + (num9 - num10) * vec.z(),
                 (num8 - num11) * vec.x() + (num9 + num10) * vec.y() + (1.0 - (num4 + num5)) * vec.z(),
                 dest.w);
        return dest;
    }

    /**
     * Invert this quaternion and store the {@link Quaterniond#normalize() normalized} result in <code>dest</code>.
     *
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Quaterniond invert(Quaterniond dest) {
        double invNorm = 1.0 / (x() * x() + y() * y() + z() * z() + w() * w());
        dest.x = -x() * invNorm;
        dest.y = -y() * invNorm;
        dest.z = -z() * invNorm;
        dest.w = w() * invNorm;
        return dest;
    }

    /**
     * Invert this quaternion by assuming that it is already {@link Quaterniond#normalize() normalized} and store the result in <code>dest</code>.
     *
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Quaterniond unitInvert(Quaterniond dest) {
        dest.x = -x();
        dest.y = -y();
        dest.z = -z();
        dest.w = w();
        return dest;
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
     * @return dest
     */
    public Quaterniond div(Quaterniondr b, Quaterniond dest) {
        double invNorm = 1.0 / (b.x() * b.x() + b.y() * b.y() + b.z() * b.z() + b.w() * b.w());
        double x = -b.x() * invNorm;
        double y = -b.y() * invNorm;
        double z = -b.z() * invNorm;
        double w = b.w() * invNorm;
        dest.set(this.w() * x + this.x() * w + this.y() * z - this.z() * y,
                 this.w() * y - this.x() * z + this.y() * w + this.z() * x,
                 this.w() * z + this.x() * y - this.y() * x + this.z() * w,
                 this.w() * w - this.x() * x - this.y() * y - this.z() * z);
        return dest;
    }

    /**
     * Conjugate this quaternion and store the result in <code>dest</code>.
     *
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Quaterniond conjugate(Quaterniond dest) {
        dest.x = -x();
        dest.y = -y();
        dest.z = -z();
        dest.w = w();
        return dest;
    }

    /**
     * Return the square of the length of this quaternion.
     *
     * @return the length
     */
    public double lengthSquared() {
        return x() * x() + y() * y() + z() * z() + w() * w();
    }

    /**
     * Interpolate between <code>this</code> quaternion and the specified
     * <code>target</code> using sperical linear interpolation using the specified interpolation factor <code>alpha</code>,
     * and store the result in <code>dest</code>.
     * <p>
     * This method resorts to non-spherical linear interpolation when the absolute dot product between <code>this</code> and <code>target</code> is
     * below <tt>1E-6</tt>.
     * <p>
     * Reference: <a href="http://fabiensanglard.net/doom3_documentation/37725-293747_293747.pdf">http://fabiensanglard.net</a>
     *
     * @param target
     *          the target of the interpolation, which should be reached with <tt>alpha = 1.0</tt>
     * @param alpha
     *          the interpolation factor, within <tt>[0..1]</tt>
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Quaterniond slerp(Quaterniondr target, double alpha, Quaterniond dest) {
        double cosom = x() * target.x() + y() * target.y() + z() * target.z() + w() * target.w();
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
        dest.x = scale0 * x() + scale1 * target.x();
        dest.y = scale0 * y() + scale1 * target.y();
        dest.z = scale0 * z() + scale1 * target.z();
        dest.w = scale0 * w() + scale1 * target.w();
        return dest;
    }

    /**
     * Compute a linear (non-spherical) interpolation of <code>this</code> and the given quaternion <code>q</code>
     * and store the result in <code>dest</code>.
     * <p>
     * Reference: <a href="http://fabiensanglard.net/doom3_documentation/37725-293747_293747.pdf">http://fabiensanglard.net</a>
     *
     * @param q
     *          the other quaternion
     * @param factor
     *          the interpolation factor. It is between 0.0 and 1.0
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Quaterniond nlerp(Quaterniondr q, double factor, Quaterniond dest) {
        double cosom = x() * q.x() + y() * q.y() + z() * q.z() + w() * q.w();
        double scale0 = 1.0 - factor;
        double scale1 = (cosom >= 0.0) ? factor : -factor;
        dest.x = scale0 * x() + scale1 * q.x();
        dest.y = scale0 * y() + scale1 * q.y();
        dest.z = scale0 * z() + scale1 * q.z();
        dest.w = scale0 * w() + scale1 * q.w();
        double s = 1.0 / Math.sqrt(dest.x * dest.x + dest.y * dest.y + dest.z * dest.z + dest.w * dest.w);
        dest.x *= s;
        dest.y *= s;
        dest.z *= s;
        dest.w *= s;
        return dest;
    }

    /**
     * Compute linear (non-spherical) interpolations of <code>this</code> and the given quaternion <code>q</code>
     * iteratively and store the result in <code>dest</code>.
     * <p>
     * This method performs a series of small-step nlerp interpolations to avoid doing a costly spherical linear interpolation, like
     * {@link #slerp(Quaterniondr, double, Quaterniond) slerp},
     * by subdividing the rotation arc between <code>this</code> and <code>q</code> via non-spherical linear interpolations as long as
     * the absolute dot product of <code>this</code> and <code>q</code> is greater than the given <code>dotThreshold</code> parameter.
     * <p>
     * Thanks to <tt>@theagentd</tt> at <a href="http://www.java-gaming.org/">http://www.java-gaming.org/</a> for providing the code.
     *
     * @param q
     *          the other quaternion
     * @param alpha
     *          the interpolation factor, between 0.0 and 1.0
     * @param dotThreshold
     *          the threshold for the dot product of <code>this</code> and <code>q</code> above which this method performs another iteration
     *          of a small-step linear interpolation
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Quaterniond nlerpIterative(Quaterniondr q, double alpha, double dotThreshold, Quaterniond dest) {
        double q1x = x(), q1y = y(), q1z = z(), q1w = w();
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
                alphaN = alphaN * 2.0;
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
                alphaN = alphaN * 2.0 - 1.0;
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
     * @return dest
     */
    public Quaterniond lookRotate(Vector3dr dir, Vector3dr up, Quaterniond dest) {
        return lookRotate(dir.x(), dir.y(), dir.z(), up.x(), up.y(), up.z(), dest);
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
     * @return dest
     */
    public Quaterniond lookRotate(double dirX, double dirY, double dirZ, double upX, double upY, double upZ, Quaterniond dest) {
        // Normalize direction
        double invDirLength = 1.0 / Math.sqrt(dirX * dirX + dirY * dirY + dirZ * dirZ);
        double dirnX = dirX * invDirLength;
        double dirnY = dirY * invDirLength;
        double dirnZ = dirZ * invDirLength;
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
        dest.set(this.w() * x + this.x() * w + this.y() * z - this.z() * y,
                 this.w() * y - this.x() * z + this.y() * w + this.z() * x,
                 this.w() * z + this.x() * y - this.y() * x + this.z() * w,
                 this.w() * w - this.x() * x - this.y() * y - this.z() * z);
        return dest;
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
        return "(" + formatter.format(x()) + " " + formatter.format(y()) + " " + formatter.format(z()) + " " + formatter.format(w()) + ")"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
    }

    public int hashCode() {
        final int prime = 31;
        int result = 1;
        long temp;
        temp = Double.doubleToLongBits(w());
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(x());
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y());
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(z());
        result = prime * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Quaterniondr) {
            Quaterniondr other = (Quaterniondr) obj;
            return Double.doubleToLongBits(w()) == Double.doubleToLongBits(other.w())
                    && Double.doubleToLongBits(x()) == Double.doubleToLongBits(other.x())
                    && Double.doubleToLongBits(y()) == Double.doubleToLongBits(other.y())
                    && Double.doubleToLongBits(z()) == Double.doubleToLongBits(other.z());
        }
        return false;
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
     * It is defined as: <tt>D = T^-1 * Q</tt>, where <tt>T^-1</tt> denotes the {@link Quaterniond#invert() inverse} of <tt>T</tt>.
     *
     * @param other
     *          the other quaternion
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Quaterniond difference(Quaterniondr other, Quaterniond dest) {
        double invNorm = 1.0 / (x() * x() + y() * y() + z() * z() + w() * w());
        double x = -this.x() * invNorm;
        double y = -this.y() * invNorm;
        double z = -this.z() * invNorm;
        double w = this.w() * invNorm;
        dest.set(w * other.x() + x * other.w() + y * other.z() - z * other.y(),
                 w * other.y() - x * other.z() + y * other.w() + z * other.x(),
                 w * other.z() + x * other.y() - y * other.x() + z * other.w(),
                 w * other.w() - x * other.x() - y * other.y() - z * other.z());
        return dest;
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
     * @return dest
     */
    public Quaterniond rotateTo(double fromDirX, double fromDirY, double fromDirZ,
                                double toDirX, double toDirY, double toDirZ, Quaterniond dest) {
        double invFromLength = 1.0 / Math.sqrt(fromDirX * fromDirX + fromDirY * fromDirY + fromDirZ * fromDirZ);
        double fromX = fromDirX * invFromLength;
        double fromY = fromDirY * invFromLength;
        double fromZ = fromDirZ * invFromLength;
        double invToLength = 1.0 / Math.sqrt(toDirX * toDirX + toDirY * toDirY + toDirZ * toDirZ);
        double toX = toDirX * invToLength;
        double toY = toDirY * invToLength;
        double toZ = toDirZ * invToLength;
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
            double invNorm = 1.0 / Math.sqrt(x * x + y * y + z * z + w * w);
            x *= invNorm;
            y *= invNorm;
            z *= invNorm;
            w *= invNorm;
        } else {
            /* vectors are parallel, don't change anything */
            return dest;
        }
        /* Multiply */
        dest.set(this.w() * x + this.x() * w + this.y() * z - this.z() * y,
                 this.w() * y - this.x() * z + this.y() * w + this.z() * x,
                 this.w() * z + this.x() * y - this.y() * x + this.z() * w,
                 this.w() * w - this.x() * x - this.y() * y - this.z() * z);
        return dest;
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
     * @return dest
     */
    public Quaterniond rotateTo(Vector3dr fromDir, Vector3dr toDir, Quaterniond dest) {
        return rotateTo(fromDir.x(), fromDir.y(), fromDir.z(), toDir.x(), toDir.y(), toDir.z(), dest);
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
     * @return dest
     */
    public Quaterniond rotate(Vector3dr anglesXYZ, Quaterniond dest) {
        return rotate(anglesXYZ.x(), anglesXYZ.y(), anglesXYZ.z(), dest);
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
     * @return dest
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
        dest.set(w() * dqX + x() * dqW + y() * dqZ - z() * dqY,
                 w() * dqY - x() * dqZ + y() * dqW + z() * dqX,
                 w() * dqZ + x() * dqY - y() * dqX + z() * dqW,
                 w() * dqW - x() * dqX - y() * dqY - z() * dqZ);
        return dest;
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
     * @return dest
     */
    public Quaterniond rotateX(double angle, Quaterniond dest) {
        double cos = Math.cos(angle * 0.5);
        double sin = Math.sin(angle * 0.5);
        dest.set(w() * sin + x() * cos,
                 y() * cos + z() * sin,
                 z() * cos - y() * sin,
                 w() * cos - x() * sin);
        return dest;
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
     * @return dest
     */
    public Quaterniond rotateY(double angle, Quaterniond dest) {
        double cos = Math.cos(angle * 0.5);
        double sin = Math.sin(angle * 0.5);
        dest.set(x() * cos - z() * sin,
                 w() * sin + y() * cos,
                 x() * sin + z() * cos,
                 w() * cos - y() * sin);
        return dest;
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
     * @return dest
     */
    public Quaterniond rotateZ(double angle, Quaterniond dest) {
        double cos = Math.cos(angle * 0.5);
        double sin = Math.sin(angle * 0.5);
        dest.set(x() * cos + y() * sin,
                 y() * cos - x() * sin,
                 w() * sin + z() * cos,
                 w() * cos - z() * sin);
        return dest;
    }

    /**
     * Apply a rotation to <code>this</code> quaternion rotating the given radians about the cartesian base unit axes,
     * called the euler angles using rotation sequence <tt>XYZ</tt> and store the result in <code>dest</code>.
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
     * @param dest
     *              will hold the result
     * @return dest
     */
    public Quaterniond rotateXYZ(double angleX, double angleY, double angleZ, Quaterniond dest) {
        double sx =  Math.sin(angleX * 0.5);
        double cx =  Math.cos(angleX * 0.5);
        double sy =  Math.sin(angleY * 0.5);
        double cy =  Math.cos(angleY * 0.5);
        double sz =  Math.sin(angleZ * 0.5);
        double cz =  Math.cos(angleZ * 0.5);

        double cycz = cy * cz;
        double sysz = sy * sz;
        double sycz = sy * cz;
        double cysz = cy * sz;
        double w = cx*cycz - sx*sysz;
        double x = sx*cycz + cx*sysz;
        double y = cx*sycz - sx*cysz;
        double z = cx*cysz + sx*sycz;
        // right-multiply
        dest.set(this.w() * x + this.x() * w + this.y() * z - this.z() * y,
                 this.w() * y - this.x() * z + this.y() * w + this.z() * x,
                 this.w() * z + this.x() * y - this.y() * x + this.z() * w,
                 this.w() * w - this.x() * x - this.y() * y - this.z() * z);
        return dest;
    }

    /**
     * Apply a rotation to <code>this</code> quaternion rotating the given radians about the cartesian base unit axes,
     * called the euler angles, using the rotation sequence <tt>ZYX</tt> and store the result in <code>dest</code>.
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
     * @param dest
     *              will hold the result
     * @return dest
     */
    public Quaterniond rotateZYX(double angleZ, double angleY, double angleX, Quaterniond dest) {
        double sx =  Math.sin(angleX * 0.5);
        double cx =  Math.cos(angleX * 0.5);
        double sy =  Math.sin(angleY * 0.5);
        double cy =  Math.cos(angleY * 0.5);
        double sz =  Math.sin(angleZ * 0.5);
        double cz =  Math.cos(angleZ * 0.5);

        double cycz = cy * cz;
        double sysz = sy * sz;
        double sycz = sy * cz;
        double cysz = cy * sz;
        double w = cx*cycz + sx*sysz;
        double x = sx*cycz - cx*sysz;
        double y = cx*sycz + sx*cysz;
        double z = cx*cysz - sx*sycz;
        // right-multiply
        dest.set(this.w() * x + this.x() * w + this.y() * z - this.z() * y,
                 this.w() * y - this.x() * z + this.y() * w + this.z() * x,
                 this.w() * z + this.x() * y - this.y() * x + this.z() * w,
                 this.w() * w - this.x() * x - this.y() * y - this.z() * z);
        return dest;
    }

    /**
     * Apply a rotation to <code>this</code> quaternion rotating the given radians about the cartesian base unit axes,
     * called the euler angles, using the rotation sequence <tt>YXZ</tt> and store the result in <code>dest</code>.
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
     * @param dest
     *              will hold the result
     * @return dest
     */
    public Quaterniond rotateYXZ(double angleY, double angleX, double angleZ, Quaterniond dest) {
        double sx = Math.sin(angleX * 0.5);
        double cx = Math.cos(angleX * 0.5);
        double sy = Math.sin(angleY * 0.5);
        double cy = Math.cos(angleY * 0.5);
        double sz = Math.sin(angleZ * 0.5);
        double cz = Math.cos(angleZ * 0.5);

        double yx = cy * sx;
        double yy = sy * cx;
        double yz = sy * sx;
        double yw = cy * cx;
        double x = yx * cz + yy * sz;
        double y = yy * cz - yx * sz;
        double z = yw * sz - yz * cz;
        double w = yw * cz + yz * sz;
        // right-multiply
        dest.set(this.w() * x + this.x() * w + this.y() * z - this.z() * y,
                 this.w() * y - this.x() * z + this.y() * w + this.z() * x,
                 this.w() * z + this.x() * y - this.y() * x + this.z() * w,
                 this.w() * w - this.x() * x - this.y() * y - this.z() * z);
        return dest;
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
     * @return dest
     */
    public Quaterniond rotateAxis(double angle, double axisX, double axisY, double axisZ, Quaterniond dest) {
        double hangle = angle / 2.0;
        double sinAngle = Math.sin(hangle);
        double invVLength = 1.0 / Math.sqrt(axisX * axisX + axisY * axisY + axisZ * axisZ);

        double rx = axisX * invVLength * sinAngle;
        double ry = axisY * invVLength * sinAngle;
        double rz = axisZ * invVLength * sinAngle;
        double rw = Math.cos(hangle);

        dest.set(w() * rx + x() * rw + y() * rz - z() * ry,
                 w() * ry - x() * rz + y() * rw + z() * rx,
                 w() * rz + x() * ry - y() * rx + z() * rw,
                 w() * rw - x() * rx - y() * ry - z() * rz);
        return dest;
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
     * @return dest
     */
    public Quaterniond rotateAxis(double angle, Vector3dr axis, Quaterniond dest) {
        return rotateAxis(angle, axis.x(), axis.y(), axis.z(), dest);
    }

    /**
     * Get the euler angles in radians in rotation sequence <tt>XYZ</tt> of this quaternion and store them in the
     * provided parameter <code>eulerAngles</code>.
     *
     * @param eulerAngles
     *          will hold the euler angles in radians
     * @return the passed in vector
     */
    public Vector3d getEulerAnglesXYZ(Vector3d eulerAngles) {
        eulerAngles.x = Math.atan2(2.0 * (x() * w() - y() * z()), 1.0 - 2.0 * (x() * x() + y() * y()));
        eulerAngles.y = Math.asin(2.0 * (x() * z() + y() * w()));
        eulerAngles.z = Math.atan2(2.0 * (z() * w() - x() * y()), 1.0 - 2.0 * (y() * y() + z() * z()));
        return eulerAngles;
    }
}
