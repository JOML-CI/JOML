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
public class Quaternionf implements Externalizable {

    private static final long serialVersionUID = 1L;

    /**
     * The first component of the vector part.
     */
    public float x;
    /**
     * The second component of the vector part.
     */
    public float y;
    /**
     * The third component of the vector part.
     */
    public float z;
    /**
     * The real/scalar part of the quaternion.
     */
    public float w;

    /**
     * Create a new {@link Quaternionf} and initialize it with <tt>(x=0, y=0, z=0, w=1)</tt>, 
     * where <tt>(x, y, z)</tt> is the vector part of the quaternion and <tt>w</tt> is the real/scalar part.
     */
    public Quaternionf() {
        x = 0.0f;
        y = 0.0f;
        z = 0.0f;
        w = 1.0f;
    }

    /**
     * Create a new {@link Quaternionf} and initialize its components to the given values.
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
    public Quaternionf(float x, float y, float z, float w) {
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
    public Quaternionf(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
        w = 1.0f;
    }

    /**
     * Create a new {@link Quaternionf} and initialize its components to the same values as the given {@link Quaternionf}.
     * 
     * @param source
     *          the {@link Quaternionf} to take the component values from
     */
    public Quaternionf(Quaternionf source) {
        x = source.x;
        y = source.y;
        z = source.z;
        w = source.w;
    }

    /**
     * Create a new {@link Quaternionf} which represents the rotation of the given {@link AxisAngle4f}.
     * 
     * @param axisAngle
     *          the {@link AxisAngle4f}
     */
    public Quaternionf(AxisAngle4f axisAngle) {
        float sin = (float) Math.sin(axisAngle.angle / 2.0);
        float cos = (float) Math.cos(axisAngle.angle / 2.0);
        x = axisAngle.x * sin;
        y = axisAngle.y * sin;
        z = axisAngle.z * sin;
        w = cos;
    }

    /**
     * Normalize this quaternion.
     * 
     * @return this
     */
    public Quaternionf normalize() {
        float invNorm = (float) (1.0 / Math.sqrt(x * x + y * y + z * z + w * w));
        x *= invNorm;
        y *= invNorm;
        z *= invNorm;
        w *= invNorm;
        return this;
    }

    /**
     * Normalize this quaternion and store the result in <code>dest</code>.
     * 
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Quaternionf normalize(Quaternionf dest) {
        float invNorm = (float) (1.0 / Math.sqrt(x * x + y * y + z * z + w * w));
        dest.x = x * invNorm;
        dest.y = y * invNorm;
        dest.z = z * invNorm;
        dest.w = w * invNorm;
        return dest;
    }

    /**
     * Add <code>q2</code> to this quaternion.
     * 
     * @param q2
     *          the quaternion to add to this
     * @return this
     */
    public Quaternionf add(Quaternionf q2) {
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
     * @return dest
     */
    public Quaternionf add(Quaternionf q2, Quaternionf dest) {
        dest.x = x + q2.x;
        dest.y = y + q2.y;
        dest.z = z + q2.z;
        dest.w = w + q2.w;
        return dest;
    }

    /**
     * Return the dot of this quaternion and <code>otherQuat</code>.
     * 
     * @param otherQuat
     *          the other quaternion
     * @return the dot product
     */
    public float dot(Quaternionf otherQuat) {
        return this.x * otherQuat.x + this.y * otherQuat.y + this.z * otherQuat.z + this.w * otherQuat.w;
    }

    /**
     * Return the angle in radians represented by this quaternion rotation.
     * 
     * @return the angle in radians
     */
    public float angle() {
        float angle = (float) (2.0 * Math.acos(w));
        return (float) (angle <= Math.PI ? angle : 2.0 * Math.PI - angle);
    }

    /**
     * Set the given destination matrix to the rotation represented by <code>this</code>.
     * 
     * @param dest
     *          the matrix to write the rotation into
     * @return the passed in destination
     */
    public Matrix3f get(Matrix3f dest) {
        float q00 = 2.0f * x * x;
        float q11 = 2.0f * y * y;
        float q22 = 2.0f * z * z;

        float q01 = 2.0f * x * y;
        float q02 = 2.0f * x * z;
        float q03 = 2.0f * x * w;

        float q12 = 2.0f * y * z;
        float q13 = 2.0f * y * w;

        float q23 = 2.0f * z * w;

        dest.m00 = 1.0f - q11 - q22;
        dest.m01 = q01 + q23;
        dest.m02 = q02 - q13;
        dest.m10 = q01 - q23;
        dest.m11 = 1.0f - q22 - q00;
        dest.m12 = q12 + q03;
        dest.m20 = q02 + q13;
        dest.m21 = q12 - q03;
        dest.m22 = 1.0f - q11 - q00;
        return dest;
    }

    /**
     * Set the given destination matrix to the rotation represented by <code>this</code>.
     * 
     * @param dest
     *          the matrix to write the rotation into
     * @return the passed in destination
     */
    public Matrix3d get(Matrix3d dest) {
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
        float q00 = 2.0f * x * x;
        float q11 = 2.0f * y * y;
        float q22 = 2.0f * z * z;

        float q01 = 2.0f * x * y;
        float q02 = 2.0f * x * z;
        float q03 = 2.0f * x * w;

        float q12 = 2.0f * y * z;
        float q13 = 2.0f * y * w;

        float q23 = 2.0f * z * w;

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
        float q00 = 2.0f * x * x;
        float q11 = 2.0f * y * y;
        float q22 = 2.0f * z * z;

        float q01 = 2.0f * x * y;
        float q02 = 2.0f * x * z;
        float q03 = 2.0f * x * w;

        float q12 = 2.0f * y * z;
        float q13 = 2.0f * y * w;

        float q23 = 2.0f * z * w;

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
     * Set the given {@link AxisAngle4f} to represent the rotation of
     * <code>this</code> quaternion.
     * 
     * @param dest
     *            the {@link AxisAngle4f} to set
     * @return the passed in destination
     */
    public AxisAngle4f get(AxisAngle4f dest) {
        float x = this.x;
        float y = this.y;
        float z = this.z;
        float w = this.w;
        if (w > 1.0f) {
            float invNorm = (float) (1.0 / Math.sqrt(x * x + y * y + z * z + w * w));
            x *= invNorm;
            y *= invNorm;
            z *= invNorm;
            w *= invNorm;
        }
        dest.angle = (float) (2.0f * Math.acos(w));
        float s = (float) Math.sqrt(1.0 - w * w);
        if (s < 0.001f) {
            dest.x = x;
            dest.y = y;
            dest.z = z;
        } else {
            s = 1.0f / s;
            dest.x = x * s;
            dest.y = y * s;
            dest.z = z * s;
        }
        return dest;
    }

    /**
     * Set the given {@link Quaterniond} to the values of <code>this</code>.
     * 
     * @see Quaterniond#set(Quaterniond)
     * 
     * @param dest
     *          the {@link Quaterniond} to set
     * @return the passed in destination
     */
    public Quaterniond get(Quaterniond dest) {
        return dest.set(this);
    }

    /**
     * Set the given {@link Quaternionf} to the values of <code>this</code>.
     * 
     * @see #set(Quaternionf)
     * 
     * @param dest
     *          the {@link Quaternionf} to set
     * @return the passed in destination
     */
    public Quaternionf get(Quaternionf dest) {
        return dest.set(this);
    }

    /**
     * Set this quaternion to the given values.
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
    public Quaternionf set(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
        return this;
    }

    /**
     * Set the x, y and z components of this quaternion to the given values.
     * 
     * @param x
     *          the new value of x
     * @param y
     *          the new value of y
     * @param z
     *          the new value of z
     * @return this
     */
    public Quaternionf set(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }

    /**
     * Set this quaternion to be a copy of q.
     * 
     * @param q
     *          the {@link Quaternionf} to copy
     * @return this
     */
    public Quaternionf set(Quaternionf q) {
        x = q.x;
        y = q.y;
        z = q.z;
        w = q.w;
        return this;
    }

    /**
     * Set this quaternion to a rotation equivalent to the given {@link AxisAngle4f}.
     * 
     * @param axisAngle
     *          the {@link AxisAngle4f}
     * @return this
     */
    public Quaternionf set(AxisAngle4f axisAngle) {
        return setAngleAxis(axisAngle.angle, axisAngle.x, axisAngle.y, axisAngle.z);
    }

    /**
     * Set this quaternion to a rotation equivalent to the given {@link AxisAngle4d}.
     * 
     * @param axisAngle
     *          the {@link AxisAngle4d}
     * @return this
     */
    public Quaternionf set(AxisAngle4d axisAngle) {
        return setAngleAxis(axisAngle.angle, axisAngle.x, axisAngle.y, axisAngle.z);
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
    public Quaternionf setAngleAxis(float angle, float x, float y, float z) {
        float s = (float) Math.sin(angle * 0.5);
        this.x = x * s;
        this.y = y * s;
        this.z = z * s;
        this.w = (float) Math.cos(angle * 0.5);
        return this;
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
    public Quaternionf setAngleAxis(double angle, double x, double y, double z) {
        double s = Math.sin(angle * 0.5);
        this.x = (float) (x * s);
        this.y = (float) (y * s);
        this.z = (float) (z * s);
        this.w = (float) Math.cos(angle * 0.5);
        return this;
    }

    /**
     * Set this {@link Quaternionf} to a rotation of the given angle in radians about the supplied
     * axis, all of which are specified via the {@link AxisAngle4f}.
     * 
     * @see #rotationAxis(float, float, float, float)
     * 
     * @param axisAngle
     *            the {@link AxisAngle4f} giving the rotation angle in radians and the axis to rotate about
     * @return this
     */
    public Quaternionf rotationAxis(AxisAngle4f axisAngle) {
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
    public Quaternionf rotationAxis(float angle, float axisX, float axisY, float axisZ) {
        float hangle = angle / 2.0f;
        float sinAngle = (float) Math.sin(hangle);
        float invVLength = (float) (1.0 / Math.sqrt(axisX * axisX + axisY * axisY + axisZ * axisZ));

        x = axisX * invVLength * sinAngle;
        y = axisY * invVLength * sinAngle;
        z = axisZ * invVLength * sinAngle;
        w = (float) Math.cos(hangle);

        return this;
    }

    /**
     * Set this quaternion to a rotation of the given angle in radians about the supplied axis.
     * 
     * @see #rotationAxis(float, float, float, float)
     * 
     * @param angle
     *          the rotation angle in radians
     * @param axis
     *          the axis to rotate about
     * @return this
     */
    public Quaternionf rotationAxis(float angle, Vector3f axis) {
        return rotationAxis(angle, axis.x, axis.y, axis.z);
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
    public Quaternionf rotation(float angleX, float angleY, float angleZ) {
        double thetaX = angleX * 0.5;
        double thetaY = angleY * 0.5;
        double thetaZ = angleZ * 0.5;
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
     * Set this quaternion to represent a rotation of the given radians about the x axis.
     * 
     * @param angle
     *              the angle in radians to rotate about the x axis
     * @return this
     */
    public Quaternionf rotationX(float angle) {
        float cos = (float) Math.cos(angle * 0.5);
        float sin = (float) Math.sin(angle * 0.5);
        w = cos;
        x = sin;
        y = 0.0f;
        z = 0.0f;
        return this;
    }

    /**
     * Set this quaternion to represent a rotation of the given radians about the y axis.
     * 
     * @param angle
     *              the angle in radians to rotate about the y axis
     * @return this
     */
    public Quaternionf rotationY(float angle) {
        float cos = (float) Math.cos(angle * 0.5);
        float sin = (float) Math.sin(angle * 0.5);
        w = cos;
        x = 0.0f;
        y = sin;
        z = 0.0f;
        return this;
    }

    /**
     * Set this quaternion to represent a rotation of the given radians about the z axis.
     * 
     * @param angle
     *              the angle in radians to rotate about the z axis
     * @return this
     */
    public Quaternionf rotationZ(float angle) {
        float cos = (float) Math.cos(angle * 0.5);
        float sin = (float) Math.sin(angle * 0.5);
        w = cos;
        x = 0.0f;
        y = 0.0f;
        z = sin;
        return this;
    }

    private void setFromUnnormalized(float m00, float m01, float m02, float m10, float m11, float m12, float m20, float m21, float m22) {
        float nm00 = m00, nm01 = m01, nm02 = m02;
        float nm10 = m10, nm11 = m11, nm12 = m12;
        float nm20 = m20, nm21 = m21, nm22 = m22;
        float lenX = (float) (1.0 / Math.sqrt(m00 * m00 + m01 * m01 + m02 * m02));
        float lenY = (float) (1.0 / Math.sqrt(m10 * m10 + m11 * m11 + m12 * m12));
        float lenZ = (float) (1.0 / Math.sqrt(m20 * m20 + m21 * m21 + m22 * m22));
        nm00 *= lenX; nm01 *= lenX; nm02 *= lenX;
        nm10 *= lenY; nm11 *= lenY; nm12 *= lenY;
        nm20 *= lenZ; nm21 *= lenZ; nm22 *= lenZ;
        setFromNormalized(nm00, nm01, nm02, nm10, nm11, nm12, nm20, nm21, nm22);
    }

    private void setFromNormalized(float m00, float m01, float m02, float m10, float m11, float m12, float m20, float m21, float m22) {
        float t;
        float tr = m00 + m11 + m22;
        if (tr >= 0.0f) {
            t = (float) Math.sqrt(tr + 1.0f);
            w = t * 0.5f;
            t = 0.5f / t;
            x = (m12 - m21) * t;
            y = (m20 - m02) * t;
            z = (m01 - m10) * t;
        } else {
            if (m00 >= m11 && m00 >= m22) {
                t = (float) Math.sqrt(m00 - (m11 + m22) + 1.0);
                x = t * 0.5f;
                t = 0.5f / t;
                y = (m10 + m01) * t;
                z = (m02 + m20) * t;
                w = (m12 - m21) * t;
            } else if (m11 > m22) {
                t = (float) Math.sqrt(m11 - (m22 + m00) + 1.0);
                y = t * 0.5f;
                t = 0.5f / t;
                z = (m21 + m12) * t;
                x = (m10 + m01) * t;
                w = (m20 - m02) * t;
            } else {
                t = (float) Math.sqrt(m22 - (m00 + m11) + 1.0);
                z = t * 0.5f;
                t = 0.5f / t;
                x = (m02 + m20) * t;
                y = (m21 + m12) * t;
                w = (m01 - m10) * t;
            }
        }
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
            w = (float) (t * 0.5);
            t = 0.5 / t;
            x = (float) ((m12 - m21) * t);
            y = (float) ((m20 - m02) * t);
            z = (float) ((m01 - m10) * t);
        } else {
            if (m00 >= m11 && m00 >= m22) {
                t = Math.sqrt(m00 - (m11 + m22) + 1.0);
                x = (float) (t * 0.5);
                t = 0.5 / t;
                y = (float) ((m10 + m01) * t);
                z = (float) ((m02 + m20) * t);
                w = (float) ((m12 - m21) * t);
            } else if (m11 > m22) {
                t = (float) Math.sqrt(m11 - (m22 + m00) + 1.0);
                y = (float) (t * 0.5);
                t = 0.5 / t;
                z = (float) ((m21 + m12) * t);
                x = (float) ((m10 + m01) * t);
                w = (float) ((m20 - m02) * t);
            } else {
                t = (float) Math.sqrt(m22 - (m00 + m11) + 1.0);
                z = (float) (t * 0.5);
                t = 0.5 / t;
                x = (float) ((m02 + m20) * t);
                y = (float) ((m21 + m12) * t);
                w = (float) ((m01 - m10) * t);
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
    public Quaternionf setFromUnnormalized(Matrix4f mat) {
        setFromUnnormalized(mat.m00, mat.m01, mat.m02, mat.m10, mat.m11, mat.m12, mat.m20, mat.m21, mat.m22);
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
    public Quaternionf setFromNormalized(Matrix4f mat) {
        setFromNormalized(mat.m00, mat.m01, mat.m02, mat.m10, mat.m11, mat.m12, mat.m20, mat.m21, mat.m22);
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
    public Quaternionf setFromUnnormalized(Matrix4d mat) {
        setFromUnnormalized(mat.m00, mat.m01, mat.m02, mat.m10, mat.m11, mat.m12, mat.m20, mat.m21, mat.m22);
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
    public Quaternionf setFromNormalized(Matrix4d mat) {
        setFromNormalized(mat.m00, mat.m01, mat.m02, mat.m10, mat.m11, mat.m12, mat.m20, mat.m21, mat.m22);
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
    public Quaternionf setFromUnnormalized(Matrix3f mat) {
        setFromUnnormalized(mat.m00, mat.m01, mat.m02, mat.m10, mat.m11, mat.m12, mat.m20, mat.m21, mat.m22);
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
    public Quaternionf setFromNormalized(Matrix3f mat) {
        setFromNormalized(mat.m00, mat.m01, mat.m02, mat.m10, mat.m11, mat.m12, mat.m20, mat.m21, mat.m22);
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
    public Quaternionf setFromUnnormalized(Matrix3d mat) {
        setFromUnnormalized(mat.m00, mat.m01, mat.m02, mat.m10, mat.m11, mat.m12, mat.m20, mat.m21, mat.m22);
        return this;
    }

    /**
     * Set this quaternion to be a representation of the rotational component of the given matrix.
     * 
     * @param mat
     *          the matrix whose rotational component is used to set this quaternion
     * @return this
     */
    public Quaternionf setFromNormalized(Matrix3d mat) {
        setFromNormalized(mat.m00, mat.m01, mat.m02, mat.m10, mat.m11, mat.m12, mat.m20, mat.m21, mat.m22);
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
    public Quaternionf mul(Quaternionf q) {
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
     * @return dest
     */
    public Quaternionf mul(Quaternionf q, Quaternionf dest) {
        dest.set(w * q.x + x * q.w + y * q.z - z * q.y,
                 w * q.y - x * q.z + y * q.w + z * q.x,
                 w * q.z + x * q.y - y * q.x + z * q.w,
                 w * q.w - x * q.x - y * q.y - z * q.z);
        return dest;
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
    public Quaternionf mul(float qx, float qy, float qz, float qw) {
        set(w * qx + x * qw + y * qz - z * qy,
            w * qy - x * qz + y * qw + z * qx,
            w * qz + x * qy - y * qx + z * qw,
            w * qw - x * qx - y * qy - z * qz);
        return this;
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
    public Quaternionf mul(float qx, float qy, float qz, float qw, Quaternionf dest) {
        dest.set(w * qx + x * qw + y * qz - z * qy,
                 w * qy - x * qz + y * qw + z * qx,
                 w * qz + x * qy - y * qx + z * qw,
                 w * qw - x * qx - y * qy - z * qz);
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
    public Vector3f transform(Vector3f vec){
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
    public Vector4f transform(Vector4f vec){
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
    public Vector3f transform(Vector3f vec, Vector3f dest) {
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
    public Vector4f transform(Vector4f vec, Vector4f dest) {
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
        return dest;
    }

    /**
     * Invert this quaternion and store the {@link #normalize() normalized} result in <code>dest</code>.
     * 
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Quaternionf invert(Quaternionf dest) {
        float invNorm = 1.0f / (x * x + y * y + z * z + w * w);
        dest.x = -x * invNorm;
        dest.y = -y * invNorm;
        dest.z = -z * invNorm;
        dest.w = w * invNorm;
        return dest;
    }

    /**
     * Invert this quaternion by assuming that it is already {@link #normalize() normalized} and store the result in <code>dest</code>.
     * 
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Quaternionf unitInvert(Quaternionf dest) {
        dest.x = -x;
        dest.y = -y;
        dest.z = -z;
        dest.w = w;
        return dest;
    }

    /**
     * Invert this quaternion and {@link #normalize() normalize} it.
     * 
     * @return this
     */
    public Quaternionf invert() {
        return invert(this);
    }

    /**
     * Invert this quaternion by assuming that it is already {@link #normalize() normalized}.
     * 
     * @return this
     */
    public Quaternionf unitInvert() {
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
     *          the {@link Quaternionf} to divide this by
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Quaternionf div(Quaternionf b, Quaternionf dest) {
        float invNorm = 1.0f / (b.x * b.x + b.y * b.y + b.z * b.z + b.w * b.w);
        float x = -b.x * invNorm;
        float y = -b.y * invNorm;
        float z = -b.z * invNorm;
        float w = b.w * invNorm;
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
     * <tt>this = this * b^-1</tt>, where <tt>b^-1</tt> is the inverse of <code>b</code>.
     * 
     * @param b
     *          the {@link Quaternionf} to divide this by
     * @return this
     */
    public Quaternionf div(Quaternionf b) {
        return div(b, this);
    }

    /**
     * Conjugate this quaternion.
     * 
     * @return this
     */
    public Quaternionf conjugate() {
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
     * @return dest
     */
    public Quaternionf conjugate(Quaternionf dest) {
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
    public Quaternionf identity() {
        x = 0.0f;
        y = 0.0f;
        z = 0.0f;
        w = 1.0f;
        return this;
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
    public Quaternionf rotateXYZ(float angleX, float angleY, float angleZ) {
        return rotateXYZ(angleX, angleY, angleZ, this);
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
    public Quaternionf rotateXYZ(float angleX, float angleY, float angleZ, Quaternionf dest) {
        float sx = (float) Math.sin(angleX * 0.5);
        float cx = (float) Math.cos(angleX * 0.5);
        float sy = (float) Math.sin(angleY * 0.5);
        float cy = (float) Math.cos(angleY * 0.5);
        float sz = (float) Math.sin(angleZ * 0.5);
        float cz = (float) Math.cos(angleZ * 0.5);

        float cycz = cy * cz;
        float sysz = sy * sz;
        float sycz = sy * cz;
        float cysz = cy * sz;
        float w = cx*cycz - sx*sysz;
        float x = sx*cycz + cx*sysz;
        float y = cx*sycz - sx*cysz;
        float z = cx*cysz + sx*sycz;
        // right-multiply
        dest.set(this.w * x + this.x * w + this.y * z - this.z * y,
                 this.w * y - this.x * z + this.y * w + this.z * x,
                 this.w * z + this.x * y - this.y * x + this.z * w,
                 this.w * w - this.x * x - this.y * y - this.z * z);
        return dest;
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
    public Quaternionf rotateZYX(float angleZ, float angleY, float angleX) {
        return rotateZYX(angleZ, angleY, angleX, this);
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
    public Quaternionf rotateZYX(float angleZ, float angleY, float angleX, Quaternionf dest) {
        float sx = (float) Math.sin(angleX * 0.5);
        float cx = (float) Math.cos(angleX * 0.5);
        float sy = (float) Math.sin(angleY * 0.5);
        float cy = (float) Math.cos(angleY * 0.5);
        float sz = (float) Math.sin(angleZ * 0.5);
        float cz = (float) Math.cos(angleZ * 0.5);

        float cycz = cy * cz;
        float sysz = sy * sz;
        float sycz = sy * cz;
        float cysz = cy * sz;
        float w = cx*cycz + sx*sysz;
        float x = sx*cycz - cx*sysz;
        float y = cx*sycz + sx*cysz;
        float z = cx*cysz - sx*sycz;
        // right-multiply
        dest.set(this.w * x + this.x * w + this.y * z - this.z * y,
                 this.w * y - this.x * z + this.y * w + this.z * x,
                 this.w * z + this.x * y - this.y * x + this.z * w,
                 this.w * w - this.x * x - this.y * y - this.z * z);
        return dest;
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
    public Quaternionf rotateYXZ(float angleZ, float angleY, float angleX) {
        return rotateYXZ(angleZ, angleY, angleX, this);
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
    public Quaternionf rotateYXZ(float angleY, float angleX, float angleZ, Quaternionf dest) {
        float sx = (float) Math.sin(angleX * 0.5);
        float cx = (float) Math.cos(angleX * 0.5);
        float sy = (float) Math.sin(angleY * 0.5);
        float cy = (float) Math.cos(angleY * 0.5);
        float sz = (float) Math.sin(angleZ * 0.5);
        float cz = (float) Math.cos(angleZ * 0.5);

        float yx = cy * sx;
        float yy = sy * cx;
        float yz = sy * sx;
        float yw = cy * cx;
        float x = yx * cz + yy * sz;
        float y = yy * cz - yx * sz;
        float z = yw * sz - yz * cz;
        float w = yw * cz + yz * sz;
        // right-multiply
        dest.set(this.w * x + this.x * w + this.y * z - this.z * y,
                 this.w * y - this.x * z + this.y * w + this.z * x,
                 this.w * z + this.x * y - this.y * x + this.z * w,
                 this.w * w - this.x * x - this.y * y - this.z * z);
        return dest;
    }

    /**
     * Get the euler angles in radians in rotation sequence <tt>XYZ</tt> of this quaternion and store them in the 
     * provided parameter <code>eulerAngles</code>.
     * 
     * @param eulerAngles
     *          will hold the euler angles in radians
     * @return the passed in vector
     */
    public Vector3f getEulerAnglesXYZ(Vector3f eulerAngles) {
        eulerAngles.x = (float) Math.atan2(2.0 * (x*w - y*z), 1.0 - 2.0 * (x*x + y*y));
        eulerAngles.y = (float) Math.asin(2.0 * (x*z + y*w));
        eulerAngles.z = (float) Math.atan2(2.0 * (z*w - x*y), 1.0 - 2.0 * (y*y + z*z));
        return eulerAngles;
    }

    /**
     * Return the square of the length of this quaternion.
     * 
     * @return the length
     */
    public float lengthSquared() {
        return x * x + y * y + z * z + w * w;
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
    public Quaternionf rotationXYZ(float angleX, float angleY, float angleZ) {
        float sx = (float) Math.sin(angleX * 0.5);
        float cx = (float) Math.cos(angleX * 0.5);
        float sy = (float) Math.sin(angleY * 0.5);
        float cy = (float) Math.cos(angleY * 0.5);
        float sz = (float) Math.sin(angleZ * 0.5);
        float cz = (float) Math.cos(angleZ * 0.5);

        float cycz = cy * cz;
        float sysz = sy * sz;
        float sycz = sy * cz;
        float cysz = cy * sz;
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
    public Quaternionf rotationZYX(float angleZ, float angleY, float angleX) {
        float sx = (float) Math.sin(angleX * 0.5);
        float cx = (float) Math.cos(angleX * 0.5);
        float sy = (float) Math.sin(angleY * 0.5);
        float cy = (float) Math.cos(angleY * 0.5);
        float sz = (float) Math.sin(angleZ * 0.5);
        float cz = (float) Math.cos(angleZ * 0.5);

        float cycz = cy * cz;
        float sysz = sy * sz;
        float sycz = sy * cz;
        float cysz = cy * sz;
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
    public Quaternionf rotationYXZ(float angleY, float angleX, float angleZ) {
        float sx = (float) Math.sin(angleX * 0.5);
        float cx = (float) Math.cos(angleX * 0.5);
        float sy = (float) Math.sin(angleY * 0.5);
        float cy = (float) Math.cos(angleY * 0.5);
        float sz = (float) Math.sin(angleZ * 0.5);
        float cz = (float) Math.cos(angleZ * 0.5);

        float x = cy * sx;
        float y = sy * cx;
        float z = sy * sx;
        float w = cy * cx;
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
     * This method resorts to non-spherical linear interpolation when the absolute dot product of <code>this</code> and <code>target</code> is
     * below <tt>1E-6f</tt>.
     * 
     * @param target
     *          the target of the interpolation, which should be reached with <tt>alpha = 1.0</tt>
     * @param alpha
     *          the interpolation factor, within <tt>[0..1]</tt>
     * @return this
     */
    public Quaternionf slerp(Quaternionf target, float alpha) {
        return slerp(target, alpha, this);
    }

    /**
     * Interpolate between <code>this</code> quaternion and the specified
     * <code>target</code> using sperical linear interpolation using the specified interpolation factor <code>alpha</code>,
     * and store the result in <code>dest</code>.
     * <p>
     * This method resorts to non-spherical linear interpolation when the absolute dot product of <code>this</code> and <code>target</code> is
     * below <tt>1E-6f</tt>.
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
    public Quaternionf slerp(Quaternionf target, float alpha, Quaternionf dest) {
        float cosom = x * target.x + y * target.y + z * target.z + w * target.w;
        float absCosom = Math.abs(cosom);
        float scale0, scale1;
        if (1.0f - absCosom > 1E-6f) {
            float sinSqr = 1.0f - absCosom * absCosom;
            float sinom = (float) (1.0 / Math.sqrt(sinSqr));
            float omega = (float) Math.atan2(sinSqr * sinom, absCosom);
            scale0 = (float) (Math.sin((1.0 - alpha) * omega) * sinom);
            scale1 = (float) (Math.sin(alpha * omega) * sinom);
        } else {
            scale0 = 1.0f - alpha;
            scale1 = alpha;
        }
        scale1 = cosom >= 0.0f ? scale1 : -scale1;
        dest.x = scale0 * x + scale1 * target.x;
        dest.y = scale0 * y + scale1 * target.y;
        dest.z = scale0 * z + scale1 * target.z;
        dest.w = scale0 * w + scale1 * target.w;
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
    public Quaternionf nlerp(Quaternionf q, float factor) {
        return nlerp(q, factor, this);
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
    public Quaternionf nlerp(Quaternionf q, float factor, Quaternionf dest) {
        float cosom = x * q.x + y * q.y + z * q.z + w * q.w;
        float scale0 = 1.0f - factor;
        float scale1 = (cosom >= 0.0f) ? factor : -factor;
        dest.x = scale0 * x + scale1 * q.x;
        dest.y = scale0 * y + scale1 * q.y;
        dest.z = scale0 * z + scale1 * q.z;
        dest.w = scale0 * w + scale1 * q.w;
        float s = (float) (1.0 / Math.sqrt(dest.x * dest.x + dest.y * dest.y + dest.z * dest.z + dest.w * dest.w));
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
     * {@link #slerp(Quaternionf, float, Quaternionf) slerp},
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
    public Quaternionf nlerpIterative(Quaternionf q, float alpha, float dotThreshold, Quaternionf dest) {
        float q1x = x, q1y = y, q1z = z, q1w = w;
        float q2x = q.x, q2y = q.y, q2z = q.z, q2w = q.w;
        float dot = q1x * q2x + q1y * q2y + q1z * q2z + q1w * q2w;
        float absDot = Math.abs(dot);
        if (1.0f - 1E-6f < absDot) {
            return dest.set(this);
        }
        float alphaN = alpha;
        while (absDot < dotThreshold) {
            float scale0 = 0.5f;
            float scale1 = dot >= 0.0f ? 0.5f : -0.5f;
            if (alphaN < 0.5f) {
                q2x = scale0 * q2x + scale1 * q1x;
                q2y = scale0 * q2y + scale1 * q1y;
                q2z = scale0 * q2z + scale1 * q1z;
                q2w = scale0 * q2w + scale1 * q1w;
                float s = (float) (1.0 / Math.sqrt(q2x * q2x + q2y * q2y + q2z * q2z + q2w * q2w));
                q2x *= s;
                q2y *= s;
                q2z *= s;
                q2w *= s;
                alphaN = alphaN * 2.0f;
            } else {
                q1x = scale0 * q1x + scale1 * q2x;
                q1y = scale0 * q1y + scale1 * q2y;
                q1z = scale0 * q1z + scale1 * q2z;
                q1w = scale0 * q1w + scale1 * q2w;
                float s = (float) (1.0 / Math.sqrt(q1x * q1x + q1y * q1y + q1z * q1z + q1w * q1w));
                q1x *= s;
                q1y *= s;
                q1z *= s;
                q1w *= s;
                alphaN = alphaN * 2.0f - 1.0f;
            }
            dot = q1x * q2x + q1y * q2y + q1z * q2z + q1w * q2w;
            absDot = Math.abs(dot);
        }
        float scale0 = 1.0f - alphaN;
        float scale1 = dot >= 0.0f ? alphaN : -alphaN;
        float resX = scale0 * q1x + scale1 * q2x;
        float resY = scale0 * q1y + scale1 * q2y;
        float resZ = scale0 * q1z + scale1 * q2z;
        float resW = scale0 * q1w + scale1 * q2w;
        float s = (float) (1.0 / Math.sqrt(resX * resX + resY * resY + resZ * resZ + resW * resW));
        dest.x = resX * s;
        dest.y = resY * s;
        dest.z = resZ * s;
        dest.w = resW * s;
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
     * @see #lookRotate(float, float, float, float, float, float, Quaternionf)
     * 
     * @param dir
     *              the direction to map to the positive Z axis
     * @param up
     *              the vector which will be mapped to a vector parallel to the plane
     *              spanned by the given <code>dir</code> and <code>up</code>
     * @return this
     */
    public Quaternionf lookRotate(Vector3f dir, Vector3f up) {
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
     * @see #lookRotate(float, float, float, float, float, float, Quaternionf)
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
    public Quaternionf lookRotate(Vector3f dir, Vector3f up, Quaternionf dest) {
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
     * @see #lookRotate(float, float, float, float, float, float, Quaternionf)
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
    public Quaternionf lookRotate(float dirX, float dirY, float dirZ, float upX, float upY, float upZ) {
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
     * @return dest
     */
    public Quaternionf lookRotate(float dirX, float dirY, float dirZ, float upX, float upY, float upZ, Quaternionf dest) {
        // Normalize direction
        float invDirLength = (float) (1.0 / Math.sqrt(dirX * dirX + dirY * dirY + dirZ * dirZ));
        float dirnX = dirX * invDirLength;
        float dirnY = dirY * invDirLength;
        float dirnZ = dirZ * invDirLength;
        // left = up x dir
        float leftX, leftY, leftZ;
        leftX = upY * dirnZ - upZ * dirnY;
        leftY = upZ * dirnX - upX * dirnZ;
        leftZ = upX * dirnY - upY * dirnX;
        // normalize left
        float invLeftLength = (float) (1.0 / Math.sqrt(leftX * leftX + leftY * leftY + leftZ * leftZ));
        leftX *= invLeftLength;
        leftY *= invLeftLength;
        leftZ *= invLeftLength;
        // up = direction x left
        float upnX = dirnY * leftZ - dirnZ * leftY;
        float upnY = dirnZ * leftX - dirnX * leftZ;
        float upnZ = dirnX * leftY - dirnY * leftX;

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
            if (leftX > upnY && leftX > dirnZ) {
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
        return dest;
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
    public Quaternionf rotationTo(float fromDirX, float fromDirY, float fromDirZ, float toDirX, float toDirY, float toDirZ) {
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
            float invNorm = (float) (1.0 / Math.sqrt(x * x + y * y + z * z + w * w));
            x *= invNorm;
            y *= invNorm;
            z *= invNorm;
            w *= invNorm;
        } else {
            /* vectors are parallel, don't change anything */
        }
        return this;
    }

    /**
     * Set <code>this</code> quaternion to a rotation that rotates the <code>fromDir</code> vector to point along <code>toDir</code>.
     * <p>
     * Because there can be multiple possible rotations, this method chooses the one with the shortest arc.
     * 
     * @see #rotationTo(float, float, float, float, float, float)
     * 
     * @param fromDir
     *          the starting direction
     * @param toDir
     *          the destination direction
     * @return this
     */
    public Quaternionf rotationTo(Vector3f fromDir, Vector3f toDir) {
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
     * @return dest
     */
    public Quaternionf rotateTo(float fromDirX, float fromDirY, float fromDirZ, float toDirX, float toDirY, float toDirZ, Quaternionf dest) {
        double invFromLength = 1.0 / Math.sqrt(fromDirX * fromDirX + fromDirY * fromDirY + fromDirZ * fromDirZ);
        double fromX = fromDirX * invFromLength;
        double fromY = fromDirY * invFromLength;
        double fromZ = fromDirZ * invFromLength;
        double invToLength = 1.0 / Math.sqrt(toDirX * toDirX + toDirY * toDirY + toDirZ * toDirZ);
        double toX = toDirX * invToLength;
        double toY = toDirY * invToLength;
        double toZ = toDirZ * invToLength;
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
            double angleR = Math.PI;
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
            float invNorm = (float) (1.0 / Math.sqrt(x * x + y * y + z * z + w * w));
            x *= invNorm;
            y *= invNorm;
            z *= invNorm;
            w *= invNorm;
        } else {
            /* vectors are parallel, don't change anything */
            return dest;
        }
        /* Multiply */
        dest.set(this.w * x + this.x * w + this.y * z - this.z * y,
                 this.w * y - this.x * z + this.y * w + this.z * x,
                 this.w * z + this.x * y - this.y * x + this.z * w,
                 this.w * w - this.x * x - this.y * y - this.z * z);
        return dest;
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
     * @see #rotateTo(float, float, float, float, float, float, Quaternionf)
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
    public Quaternionf rotateTo(float fromDirX, float fromDirY, float fromDirZ, float toDirX, float toDirY, float toDirZ) {
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
     * @see #rotateTo(float, float, float, float, float, float, Quaternionf)
     * 
     * @param fromDir
     *          the starting direction
     * @param toDir
     *          the destination direction
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Quaternionf rotateTo(Vector3f fromDir, Vector3f toDir, Quaternionf dest) {
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
     * @see #rotateTo(float, float, float, float, float, float, Quaternionf)
     * 
     * @param fromDir
     *          the starting direction
     * @param toDir
     *          the destination direction
     * @return this
     */
    public Quaternionf rotateTo(Vector3f fromDir, Vector3f toDir) {
        return rotateTo(fromDir.x, fromDir.y, fromDir.z, toDir.x, toDir.y, toDir.z, this);
    }

    /**
     * Apply a rotation to <code>this</code> quaternion rotating the given radians about the basis unit axes of the cartesian space.
     * <p>
     * If <code>Q</code> is <code>this</code> quaternion and <code>R</code> the quaternion representing the 
     * specified rotation, then the new quaternion will be <code>Q * R</code>. So when transforming a
     * vector <code>v</code> with the new quaternion by using <code>Q * R * v</code>, the
     * rotation added by this method will be applied first!
     * 
     * @see #rotate(float, float, float, Quaternionf)
     * 
     * @param angleX
     *              the angle in radians to rotate about the x axis
     * @param angleY
     *              the angle in radians to rotate about the y axis
     * @param angleZ
     *              the angle in radians to rotate about the z axis
     * @return this
     */
    public Quaternionf rotate(float angleX, float angleY, float angleZ) {
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
     * @see #rotate(float, float, float, Quaternionf)
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
    public Quaternionf rotate(float angleX, float angleY, float angleZ, Quaternionf dest) {
        double thetaX = angleX * 0.5;
        double thetaY = angleY * 0.5;
        double thetaZ = angleZ * 0.5;
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
        return dest;
    }

    /**
     * Apply a rotation to <code>this</code> quaternion rotating the given radians about the x axis.
     * <p>
     * If <code>Q</code> is <code>this</code> quaternion and <code>R</code> the quaternion representing the 
     * specified rotation, then the new quaternion will be <code>Q * R</code>. So when transforming a
     * vector <code>v</code> with the new quaternion by using <code>Q * R * v</code>, the
     * rotation added by this method will be applied first!
     * 
     * @see #rotate(float, float, float, Quaternionf)
     * 
     * @param angle
     *              the angle in radians to rotate about the x axis
     * @return this
     */
    public Quaternionf rotateX(float angle) {
        return rotateX(angle, this);
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
     * @see #rotate(float, float, float, Quaternionf)
     * 
     * @param angle
     *              the angle in radians to rotate about the x axis
     * @param dest
     *              will hold the result
     * @return dest
     */
    public Quaternionf rotateX(float angle, Quaternionf dest) {
        float cos = (float) Math.cos(angle * 0.5);
        float sin = (float) Math.sin(angle * 0.5);
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
     * @see #rotate(float, float, float, Quaternionf)
     * 
     * @param angle
     *              the angle in radians to rotate about the y axis
     * @return this
     */
    public Quaternionf rotateY(float angle) {
        return rotateY(angle, this);
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
     * @see #rotate(float, float, float, Quaternionf)
     * 
     * @param angle
     *              the angle in radians to rotate about the y axis
     * @param dest
     *              will hold the result
     * @return dest
     */
    public Quaternionf rotateY(float angle, Quaternionf dest) {
        float cos = (float) Math.cos(angle * 0.5);
        float sin = (float) Math.sin(angle * 0.5);
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
     * @see #rotate(float, float, float, Quaternionf)
     * 
     * @param angle
     *              the angle in radians to rotate about the z axis
     * @return this
     */
    public Quaternionf rotateZ(float angle) {
        return rotateZ(angle, this);
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
     * @see #rotate(float, float, float, Quaternionf)
     * 
     * @param angle
     *              the angle in radians to rotate about the z axis
     * @param dest
     *              will hold the result
     * @return dest
     */
    public Quaternionf rotateZ(float angle, Quaternionf dest) {
        float cos = (float) Math.cos(angle * 0.5);
        float sin = (float) Math.sin(angle * 0.5);
        dest.set(x * cos + y * sin,
                 y * cos - x * sin,
                 w * sin + z * cos,
                 w * cos - z * sin);
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
    public Quaternionf rotateAxis(float angle, float axisX, float axisY, float axisZ, Quaternionf dest) {
        double hangle = angle / 2.0;
        double sinAngle = Math.sin(hangle);
        double invVLength = 1.0 / Math.sqrt(axisX * axisX + axisY * axisY + axisZ * axisZ);

        double rx = axisX * invVLength * sinAngle;
        double ry = axisY * invVLength * sinAngle;
        double rz = axisZ * invVLength * sinAngle;
        double rw = Math.cos(hangle);

        dest.set((float) (w * rx + x * rw + y * rz - z * ry),
                 (float) (w * ry - x * rz + y * rw + z * rx),
                 (float) (w * rz + x * ry - y * rx + z * rw),
                 (float) (w * rw - x * rx - y * ry - z * rz));
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
     * @see #rotateAxis(float, float, float, float, Quaternionf)
     * 
     * @param angle
     *              the angle in radians to rotate about the specified axis
     * @param axis
     *              the rotation axis
     * @param dest
     *              will hold the result
     * @return dest
     */
    public Quaternionf rotateAxis(float angle, Vector3f axis, Quaternionf dest) {
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
     * @see #rotateAxis(float, float, float, float, Quaternionf)
     * 
     * @param angle
     *              the angle in radians to rotate about the specified axis
     * @param axis
     *              the rotation axis
     * @return this
     */
    public Quaternionf rotateAxis(float angle, Vector3f axis) {
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
     * @see #rotateAxis(float, float, float, float, Quaternionf)
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
    public Quaternionf rotateAxis(float angle, float axisX, float axisY, float axisZ) {
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
        Quaternionf other = (Quaternionf) obj;
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
    public Quaternionf difference(Quaternionf other) {
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
     * @return dest
     */
    public Quaternionf difference(Quaternionf other, Quaternionf dest) {
        float invNorm = 1.0f / (x * x + y * y + z * z + w * w);
        float x = -this.x * invNorm;
        float y = -this.y * invNorm;
        float z = -this.z * invNorm;
        float w = this.w * invNorm;
        dest.set(w * other.x + x * other.w + y * other.z - z * other.y,
                 w * other.y - x * other.z + y * other.w + z * other.x,
                 w * other.z + x * other.y - y * other.x + z * other.w,
                 w * other.w - x * other.x - y * other.y - z * other.z);
        return dest;
    }

}
