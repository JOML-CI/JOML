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
//#ifdef __HAS_NIO__
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
//#endif
import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.joml.internal.MemUtil;
import org.joml.internal.Options;
import org.joml.internal.Runtime;

/**
 * Quaternion of 4 single-precision floats which can represent rotation and uniform scaling.
 *
 * @author Richard Greenlees
 * @author Kai Burjack
 */
public class Quaternionf implements Externalizable, Quaternionfc {

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
     * Create a new {@link Quaternionf} and initialize it with <code>(x=0, y=0, z=0, w=1)</code>, 
     * where <code>(x, y, z)</code> is the vector part of the quaternion and <code>w</code> is the real/scalar part.
     */
    public Quaternionf() {
        this.w = 1.0f;
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
     * Create a new {@link Quaternionf} and initialize its components to the same values as the given {@link Quaternionf}.
     * 
     * @param source
     *          the {@link Quaternionf} to take the component values from
     */
    public Quaternionf(Quaternionf source) {
        MemUtil.INSTANCE.copy(source, this);
    }

    /**
     * Create a new {@link Quaternionf} which represents the rotation of the given {@link AxisAngle4f}.
     * 
     * @param axisAngle
     *          the {@link AxisAngle4f}
     */
    public Quaternionf(AxisAngle4f axisAngle) {
        float sin = (float) Math.sin(axisAngle.angle * 0.5);
        float cos = (float) Math.cosFromSin(sin, axisAngle.angle * 0.5);
        x = axisAngle.x * sin;
        y = axisAngle.y * sin;
        z = axisAngle.z * sin;
        w = cos;
    }

    /**
     * @return the first component of the vector part
     */
    public float x() {
        return this.x;
    }

    /**
     * @return the second component of the vector part
     */
    public float y() {
        return this.y;
    }

    /**
     * @return the third component of the vector part
     */
    public float z() {
        return this.z;
    }

    /**
     * @return the real/scalar part of the quaternion
     */
    public float w() {
        return this.w;
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

    /* (non-Javadoc)
     * @see org.joml.Quaternionfc#normalize(org.joml.Quaternionf)
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
    public Quaternionf add(float x, float y, float z, float w) {
        return add(x, y, z, w, this);
    }

    /* (non-Javadoc)
     * @see org.joml.Quaternionfc#add(float, float, float, float, org.joml.Quaternionf)
     */
    public Quaternionf add(float x, float y, float z, float w, Quaternionf dest) {
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
    public Quaternionf add(Quaternionfc q2) {
        x += q2.x();
        y += q2.y();
        z += q2.z();
        w += q2.w();
        return this;
    }

    /* (non-Javadoc)
     * @see org.joml.Quaternionfc#add(org.joml.Quaternionfc, org.joml.Quaternionf)
     */
    public Quaternionf add(Quaternionfc q2, Quaternionf dest) {
        dest.x = x + q2.x();
        dest.y = y + q2.y();
        dest.z = z + q2.z();
        dest.w = w + q2.w();
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

    /* (non-Javadoc)
     * @see org.joml.Quaternionfc#angle()
     */
    public float angle() {
        float angle = (float) (2.0 * Math.acos(w));
        return (float) (angle <= Math.PI ? angle : Math.PI + Math.PI - angle);
    }

    /* (non-Javadoc)
     * @see org.joml.Quaternionfc#get(org.joml.Matrix3f)
     */
    public Matrix3f get(Matrix3f dest) {
        return dest.set(this);
    }

    /* (non-Javadoc)
     * @see org.joml.Quaternionfc#get(org.joml.Matrix3d)
     */
    public Matrix3d get(Matrix3d dest) {
        return dest.set(this);
    }

    /* (non-Javadoc)
     * @see org.joml.Quaternionfc#get(org.joml.Matrix4f)
     */
    public Matrix4f get(Matrix4f dest) {
        return dest.set(this);
    }

    /* (non-Javadoc)
     * @see org.joml.Quaternionfc#get(org.joml.Matrix4d)
     */
    public Matrix4d get(Matrix4d dest) {
        return dest.set(this);
    }

    /* (non-Javadoc)
     * @see org.joml.Quaternionfc#get(org.joml.Matrix4x3f)
     */
    public Matrix4x3f get(Matrix4x3f dest) {
        return dest.set(this);
    }

    /* (non-Javadoc)
     * @see org.joml.Quaternionfc#get(org.joml.Matrix4x3d)
     */
    public Matrix4x3d get(Matrix4x3d dest) {
        return dest.set(this);
    }

    /* (non-Javadoc)
     * @see org.joml.Quaternionfc#get(org.joml.AxisAngle4f)
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

    /* (non-Javadoc)
     * @see org.joml.Quaternionfc#get(org.joml.Quaterniond)
     */
    public Quaterniond get(Quaterniond dest) {
        return dest.set(this);
    }

    /**
     * Set the given {@link Quaternionf} to the values of <code>this</code>.
     * 
     * @see #set(Quaternionfc)
     * 
     * @param dest
     *          the {@link Quaternionf} to set
     * @return the passed in destination
     */
    public Quaternionf get(Quaternionf dest) {
        return dest.set(this);
    }

//#ifdef __HAS_NIO__
    /* (non-Javadoc)
     * @see org.joml.Quaternionfc#getAsMatrix3f(java.nio.ByteBuffer)
     */
    public ByteBuffer getAsMatrix3f(ByteBuffer dest) {
        MemUtil.INSTANCE.putMatrix3f(this, dest.position(), dest);
        return dest;
    }

    /* (non-Javadoc)
     * @see org.joml.Quaternionfc#getAsMatrix3f(java.nio.FloatBuffer)
     */
    public FloatBuffer getAsMatrix3f(FloatBuffer dest) {
        MemUtil.INSTANCE.putMatrix3f(this, dest.position(), dest);
        return dest;
    }

    /* (non-Javadoc)
     * @see org.joml.Quaternionfc#getAsMatrix4f(java.nio.ByteBuffer)
     */
    public ByteBuffer getAsMatrix4f(ByteBuffer dest) {
        MemUtil.INSTANCE.putMatrix4f(this, dest.position(), dest);
        return dest;
    }

    /* (non-Javadoc)
     * @see org.joml.Quaternionfc#getAsMatrix4f(java.nio.FloatBuffer)
     */
    public FloatBuffer getAsMatrix4f(FloatBuffer dest) {
        MemUtil.INSTANCE.putMatrix4f(this, dest.position(), dest);
        return dest;
    }

    /* (non-Javadoc)
     * @see org.joml.Quaternionfc#getAsMatrix4x3f(java.nio.ByteBuffer)
     */
    public ByteBuffer getAsMatrix4x3f(ByteBuffer dest) {
        MemUtil.INSTANCE.putMatrix4x3f(this, dest.position(), dest);
        return dest;
    }

    /* (non-Javadoc)
     * @see org.joml.Quaternionfc#getAsMatrix4x3f(java.nio.FloatBuffer)
     */
    public FloatBuffer getAsMatrix4x3f(FloatBuffer dest) {
        MemUtil.INSTANCE.putMatrix4x3f(this, dest.position(), dest);
        return dest;
    }
//#endif

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
     * Set this quaternion to be a copy of q.
     * 
     * @param q
     *          the {@link Quaternionf} to copy
     * @return this
     */
    public Quaternionf set(Quaternionfc q) {
        if (q instanceof Quaternionf)
            MemUtil.INSTANCE.copy((Quaternionf) q, this);
        else {
            this.x = q.x();
            this.y = q.y();
            this.z = q.z();
            this.w = q.w();
        }
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
    public Quaternionf setAngleAxis(float angle, float x, float y, float z) {
        float s = (float) Math.sin(angle * 0.5);
        this.x = x * s;
        this.y = y * s;
        this.z = z * s;
        this.w = (float) Math.cosFromSin(s, angle * 0.5);
        return this;
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
    public Quaternionf setAngleAxis(double angle, double x, double y, double z) {
        double s = Math.sin(angle * 0.5);
        this.x = (float) (x * s);
        this.y = (float) (y * s);
        this.z = (float) (z * s);
        this.w = (float) Math.cosFromSin(s, angle * 0.5);
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
        w = (float) Math.cosFromSin(sinAngle, hangle);

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
    public Quaternionf rotationAxis(float angle, Vector3fc axis) {
        return rotationAxis(angle, axis.x(), axis.y(), axis.z());
    }

    /**
     * Set this quaternion to represent a rotation of the given radians about the x axis.
     * 
     * @param angle
     *              the angle in radians to rotate about the x axis
     * @return this
     */
    public Quaternionf rotationX(float angle) {
        float sin = (float) Math.sin(angle * 0.5);
        float cos = (float) Math.cosFromSin(sin, angle * 0.5);
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
        float sin = (float) Math.sin(angle * 0.5);
        float cos = (float) Math.cosFromSin(sin, angle * 0.5);
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
        float sin = (float) Math.sin(angle * 0.5);
        float cos = (float) Math.cosFromSin(sin, angle * 0.5);
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
    public Quaternionf setFromUnnormalized(Matrix4fc mat) {
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
    public Quaternionf setFromUnnormalized(Matrix4x3fc mat) {
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
    public Quaternionf setFromUnnormalized(Matrix4x3dc mat) {
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
    public Quaternionf setFromNormalized(Matrix4fc mat) {
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
    public Quaternionf setFromNormalized(Matrix4x3fc mat) {
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
    public Quaternionf setFromNormalized(Matrix4x3dc mat) {
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
    public Quaternionf setFromUnnormalized(Matrix4dc mat) {
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
    public Quaternionf setFromNormalized(Matrix4dc mat) {
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
    public Quaternionf setFromUnnormalized(Matrix3fc mat) {
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
    public Quaternionf setFromNormalized(Matrix3fc mat) {
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
    public Quaternionf setFromUnnormalized(Matrix3dc mat) {
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
    public Quaternionf setFromNormalized(Matrix3dc mat) {
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
    public Quaternionf fromAxisAngleRad(Vector3fc axis, float angle) {
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
    public Quaternionf fromAxisAngleRad(float axisX, float axisY, float axisZ, float angle) {
        float hangle = angle / 2.0f;
        float sinAngle = (float) Math.sin(hangle);
        float vLength = (float) Math.sqrt(axisX * axisX + axisY * axisY + axisZ * axisZ);
        x = axisX / vLength * sinAngle;
        y = axisY / vLength * sinAngle;
        z = axisZ / vLength * sinAngle;
        w = (float) Math.cosFromSin(sinAngle, hangle);
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
    public Quaternionf fromAxisAngleDeg(Vector3fc axis, float angle) {
        return fromAxisAngleRad(axis.x(), axis.y(), axis.z(), (float) Math.toRadians(angle));
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
    public Quaternionf fromAxisAngleDeg(float axisX, float axisY, float axisZ, float angle) {
        return fromAxisAngleRad(axisX, axisY, axisZ, (float) Math.toRadians(angle));
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
    public Quaternionf mul(Quaternionfc q) {
        return mul(q, this);
    }

    /* (non-Javadoc)
     * @see org.joml.Quaternionfc#mul(org.joml.Quaternionfc, org.joml.Quaternionf)
     */
    public Quaternionf mul(Quaternionfc q, Quaternionf dest) {
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
    public Quaternionf mul(float qx, float qy, float qz, float qw) {
        set(w * qx + x * qw + y * qz - z * qy,
            w * qy - x * qz + y * qw + z * qx,
            w * qz + x * qy - y * qx + z * qw,
            w * qw - x * qx - y * qy - z * qz);
        return this;
    }

    /* (non-Javadoc)
     * @see org.joml.Quaternionfc#mul(float, float, float, float, org.joml.Quaternionf)
     */
    public Quaternionf mul(float qx, float qy, float qz, float qw, Quaternionf dest) {
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
    public Quaternionf premul(Quaternionfc q) {
        return premul(q, this);
    }

    /* (non-Javadoc)
     * @see org.joml.Quaternionfc#premul(org.joml.Quaternionfc, org.joml.Quaternionf)
     */
    public Quaternionf premul(Quaternionfc q, Quaternionf dest) {
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
    public Quaternionf premul(float qx, float qy, float qz, float qw) {
        return premul(qx, qy, qz, qw, this);
    }

    /* (non-Javadoc)
     * @see org.joml.Quaternionfc#premul(float, float, float, float, org.joml.Quaternionf)
     */
    public Quaternionf premul(float qx, float qy, float qz, float qw, Quaternionf dest) {
        dest.set(qw * x + qx * w + qy * z - qz * y,
                 qw * y - qx * z + qy * w + qz * x,
                 qw * z + qx * y - qy * x + qz * w,
                 qw * w - qx * x - qy * y - qz * z);
        return dest;
    }

    /* (non-Javadoc)
     * @see org.joml.Quaternionfc#transform(org.joml.Vector3f)
     */
    public Vector3f transform(Vector3f vec){
        return transform(vec.x, vec.y, vec.z, vec);
    }

    public Vector3f transformPositiveX(Vector3f dest) {
        float w2 = this.w * this.w;
        float x2 = this.x * this.x;
        float y2 = this.y * this.y;
        float z2 = this.z * this.z;
        float zw = this.z * this.w;
        float xy = this.x * this.y;
        float xz = this.x * this.z;
        float yw = this.y * this.w;
        dest.x = w2 + x2 - z2 - y2;
        dest.y = xy + zw + zw + xy;
        dest.z = xz - yw + xz - yw;
        return dest;
    }

    public Vector4f transformPositiveX(Vector4f dest) {
        float w2 = this.w * this.w;
        float x2 = this.x * this.x;
        float y2 = this.y * this.y;
        float z2 = this.z * this.z;
        float zw = this.z * this.w;
        float xy = this.x * this.y;
        float xz = this.x * this.z;
        float yw = this.y * this.w;
        dest.x = w2 + x2 - z2 - y2;
        dest.y = xy + zw + zw + xy;
        dest.z = xz - yw + xz - yw;
        return dest;
    }

    public Vector3f transformUnitPositiveX(Vector3f dest) {
        float y2 = y * y;
        float z2 = z * z;
        float xy = x * y;
        float xz = x * z;
        float yw = y * w;
        float zw = z * w;
        dest.x = 1.0f - y2 - y2 - z2 - z2;
        dest.y = xy + zw + xy + zw;
        dest.z = xz - yw + xz - yw;
        return dest;
    }

    public Vector4f transformUnitPositiveX(Vector4f dest) {
        float y2 = y * y;
        float z2 = z * z;
        float xy = x * y;
        float xz = x * z;
        float yw = y * w;
        float zw = z * w;
        dest.x = 1.0f - y2 - y2 - z2 - z2;
        dest.y = xy + zw + xy + zw;
        dest.z = xz - yw + xz - yw;
        return dest;
    }

    public Vector3f transformPositiveY(Vector3f dest) {
        float w2 = this.w * this.w;
        float x2 = this.x * this.x;
        float y2 = this.y * this.y;
        float z2 = this.z * this.z;
        float zw = this.z * this.w;
        float xy = this.x * this.y;
        float yz = this.y * this.z;
        float xw = this.x * this.w;
        dest.x = -zw + xy - zw + xy;
        dest.y = y2 - z2 + w2 - x2;
        dest.z = yz + yz + xw + xw;
        return dest;
    }

    public Vector4f transformPositiveY(Vector4f dest) {
        float w2 = this.w * this.w;
        float x2 = this.x * this.x;
        float y2 = this.y * this.y;
        float z2 = this.z * this.z;
        float zw = this.z * this.w;
        float xy = this.x * this.y;
        float yz = this.y * this.z;
        float xw = this.x * this.w;
        dest.x = -zw + xy - zw + xy;
        dest.y = y2 - z2 + w2 - x2;
        dest.z = yz + yz + xw + xw;
        return dest;
    }

    public Vector4f transformUnitPositiveY(Vector4f dest) {
        float x2 = x * x;
        float z2 = z * z;
        float xy = x * y;
        float yz = y * z;
        float xw = x * w;
        float zw = z * w;
        dest.x = xy - zw + xy - zw;
        dest.y = 1.0f - x2 - x2 - z2 - z2;
        dest.z = yz + yz + xw + xw;
        return dest;
    }

    public Vector3f transformUnitPositiveY(Vector3f dest) {
        float x2 = x * x;
        float z2 = z * z;
        float xy = x * y;
        float yz = y * z;
        float xw = x * w;
        float zw = z * w;
        dest.x = xy - zw + xy - zw;
        dest.y = 1.0f - x2 - x2 - z2 - z2;
        dest.z = yz + yz + xw + xw;
        return dest;
    }

    public Vector3f transformPositiveZ(Vector3f dest) {
        float w2 = this.w * this.w;
        float x2 = this.x * this.x;
        float y2 = this.y * this.y;
        float z2 = this.z * this.z;
        float xz = this.x * this.z;
        float yw = this.y * this.w;
        float yz = this.y * this.z;
        float xw = this.x * this.w;
        dest.x = yw + xz + xz + yw;
        dest.y = yz + yz - xw - xw;
        dest.z = z2 - y2 - x2 + w2;
        return dest;
    }

    public Vector4f transformPositiveZ(Vector4f dest) {
        float w2 = this.w * this.w;
        float x2 = this.x * this.x;
        float y2 = this.y * this.y;
        float z2 = this.z * this.z;
        float xz = this.x * this.z;
        float yw = this.y * this.w;
        float yz = this.y * this.z;
        float xw = this.x * this.w;
        dest.x = yw + xz + xz + yw;
        dest.y = yz + yz - xw - xw;
        dest.z = z2 - y2 - x2 + w2;
        return dest;
    }

    public Vector4f transformUnitPositiveZ(Vector4f dest) {
        float x2 = x * x;
        float y2 = y * y;
        float xz = x * z;
        float yz = y * z;
        float xw = x * w;
        float yw = y * w;
        dest.x = xz + yw + xz + yw;
        dest.y = yz + yz - xw - xw;
        dest.z = 1.0f - x2 - x2 - y2 - y2;
        return dest;
    }

    public Vector3f transformUnitPositiveZ(Vector3f dest) {
        float x2 = x * x;
        float y2 = y * y;
        float xz = x * z;
        float yz = y * z;
        float xw = x * w;
        float yw = y * w;
        dest.x = xz + yw + xz + yw;
        dest.y = yz + yz - xw - xw;
        dest.z = 1.0f - x2 - x2 - y2 - y2;
        return dest;
    }

    /* (non-Javadoc)
     * @see org.joml.Quaternionfc#transform(org.joml.Vector4f)
     */
    public Vector4f transform(Vector4f vec){
        return transform(vec, vec);
    }

    /* (non-Javadoc)
     * @see org.joml.Quaternionfc#transform(org.joml.Vector3fc, org.joml.Vector3f)
     */
    public Vector3f transform(Vector3fc vec, Vector3f dest) {
        return transform(vec.x(), vec.y(), vec.z(), dest);
    }

    /* (non-Javadoc)
     * @see org.joml.Quaternionfc#transform(org.joml.Vector3dc, org.joml.Vector3d)
     */
    public Vector3d transform(Vector3dc vec, Vector3d dest) {
        return transform(vec.x(), vec.y(), vec.z(), dest);
    }

    /* (non-Javadoc)
     * @see org.joml.Quaternionfc#transform(float, float, float, org.joml.Vector3f)
     */
    public Vector3f transform(float x, float y, float z, Vector3f dest) {
        float w2 = this.w * this.w;
        float x2 = this.x * this.x;
        float y2 = this.y * this.y;
        float z2 = this.z * this.z;
        float zw = this.z * this.w, zwd = zw + zw;
        float xy = this.x * this.y, xyd = xy + xy;
        float xz = this.x * this.z, xzd = xz + xz;
        float yw = this.y * this.w, ywd = yw + yw;
        float yz = this.y * this.z, yzd = yz + yz;
        float xw = this.x * this.w, xwd = xw + xw;
        float m00 = w2 + x2 - z2 - y2;
        float m01 = xyd + zwd;
        float m02 = xzd - ywd;
        float m10 = xyd - zwd;
        float m11 = y2 - z2 + w2 - x2;
        float m12 = yzd + xwd;
        float m20 = ywd + xzd;
        float m21 = yzd - xwd;
        float m22 = z2 - y2 - x2 + w2;
        dest.x = m00 * x + m10 * y + m20 * z;
        dest.y = m01 * x + m11 * y + m21 * z;
        dest.z = m02 * x + m12 * y + m22 * z;
        return dest;
    }

    /* (non-Javadoc)
     * @see org.joml.Quaternionfc#transform(double, double, double, org.joml.Vector3d)
     */
    public Vector3d transform(double x, double y, double z, Vector3d dest) {
        float w2 = this.w * this.w;
        float x2 = this.x * this.x;
        float y2 = this.y * this.y;
        float z2 = this.z * this.z;
        float zw = this.z * this.w, zwd = zw + zw;
        float xy = this.x * this.y, xyd = xy + xy;
        float xz = this.x * this.z, xzd = xz + xz;
        float yw = this.y * this.w, ywd = yw + yw;
        float yz = this.y * this.z, yzd = yz + yz;
        float xw = this.x * this.w, xwd = xw + xw;
        float m00 = w2 + x2 - z2 - y2;
        float m01 = xyd + zwd;
        float m02 = xzd - ywd;
        float m10 = xyd - zwd;
        float m11 = y2 - z2 + w2 - x2;
        float m12 = yzd + xwd;
        float m20 = ywd + xzd;
        float m21 = yzd - xwd;
        float m22 = z2 - y2 - x2 + w2;
        dest.x = m00 * x + m10 * y + m20 * z;
        dest.y = m01 * x + m11 * y + m21 * z;
        dest.z = m02 * x + m12 * y + m22 * z;
        return dest;
    }

    /* (non-Javadoc)
     * @see org.joml.Quaternionfc#transform(org.joml.Vector4fc, org.joml.Vector4f)
     */
    public Vector4f transform(Vector4fc vec, Vector4f dest) {
        return transform(vec.x(), vec.y(), vec.z(), dest);
    }

    /* (non-Javadoc)
     * @see org.joml.Quaternionfc#transform(float, float, float, org.joml.Vector4f)
     */
    public Vector4f transform(float x, float y, float z, Vector4f dest) {
        float w2 = this.w * this.w;
        float x2 = this.x * this.x;
        float y2 = this.y * this.y;
        float z2 = this.z * this.z;
        float zw = this.z * this.w, zwd = zw + zw;
        float xy = this.x * this.y, xyd = xy + xy;
        float xz = this.x * this.z, xzd = xz + xz;
        float yw = this.y * this.w, ywd = yw + yw;
        float yz = this.y * this.z, yzd = yz + yz;
        float xw = this.x * this.w, xwd = xw + xw;
        float m00 = w2 + x2 - z2 - y2;
        float m01 = xyd + zwd;
        float m02 = xzd - ywd;
        float m10 = xyd - zwd;
        float m11 = y2 - z2 + w2 - x2;
        float m12 = yzd + xwd;
        float m20 = ywd + xzd;
        float m21 = yzd - xwd;
        float m22 = z2 - y2 - x2 + w2;
        dest.x = m00 * x + m10 * y + m20 * z;
        dest.y = m01 * x + m11 * y + m21 * z;
        dest.z = m02 * x + m12 * y + m22 * z;
        return dest;
    }

    /* (non-Javadoc)
     * @see org.joml.Quaternionfc#invert(org.joml.Quaternionf)
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
     * Invert this quaternion and {@link #normalize() normalize} it.
     * <p>
     * If this quaternion is already normalized, then {@link #conjugate()} should be used instead.
     * 
     * @see #conjugate()
     * 
     * @return this
     */
    public Quaternionf invert() {
        return invert(this);
    }

    /* (non-Javadoc)
     * @see org.joml.Quaternionfc#div(org.joml.Quaternionfc, org.joml.Quaternionf)
     */
    public Quaternionf div(Quaternionfc b, Quaternionf dest) {
        float invNorm = 1.0f / (b.x() * b.x() + b.y() * b.y() + b.z() * b.z() + b.w() * b.w());
        float x = -b.x() * invNorm;
        float y = -b.y() * invNorm;
        float z = -b.z() * invNorm;
        float w = b.w() * invNorm;
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
     *          the {@link Quaternionf} to divide this by
     * @return this
     */
    public Quaternionf div(Quaternionfc b) {
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

    /* (non-Javadoc)
     * @see org.joml.Quaternionfc#conjugate(org.joml.Quaternionf)
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
        MemUtil.INSTANCE.identity(this);
        return this;
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
    public Quaternionf rotateXYZ(float angleX, float angleY, float angleZ) {
        return rotateXYZ(angleX, angleY, angleZ, this);
    }

    /* (non-Javadoc)
     * @see org.joml.Quaternionfc#rotateXYZ(float, float, float, org.joml.Quaternionf)
     */
    public Quaternionf rotateXYZ(float angleX, float angleY, float angleZ, Quaternionf dest) {
        float sx = (float) Math.sin(angleX * 0.5);
        float cx = (float) Math.cosFromSin(sx, angleX * 0.5);
        float sy = (float) Math.sin(angleY * 0.5);
        float cy = (float) Math.cosFromSin(sy, angleY * 0.5);
        float sz = (float) Math.sin(angleZ * 0.5);
        float cz = (float) Math.cosFromSin(sz, angleZ * 0.5);

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
    public Quaternionf rotateZYX(float angleZ, float angleY, float angleX) {
        return rotateZYX(angleZ, angleY, angleX, this);
    }

    /* (non-Javadoc)
     * @see org.joml.Quaternionfc#rotateZYX(float, float, float, org.joml.Quaternionf)
     */
    public Quaternionf rotateZYX(float angleZ, float angleY, float angleX, Quaternionf dest) {
        float sx = (float) Math.sin(angleX * 0.5);
        float cx = (float) Math.cosFromSin(sx, angleX * 0.5);
        float sy = (float) Math.sin(angleY * 0.5);
        float cy = (float) Math.cosFromSin(sy, angleY * 0.5);
        float sz = (float) Math.sin(angleZ * 0.5);
        float cz = (float) Math.cosFromSin(sz, angleZ * 0.5);

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
    public Quaternionf rotateYXZ(float angleZ, float angleY, float angleX) {
        return rotateYXZ(angleZ, angleY, angleX, this);
    }

    /* (non-Javadoc)
     * @see org.joml.Quaternionfc#rotateYXZ(float, float, float, org.joml.Quaternionf)
     */
    public Quaternionf rotateYXZ(float angleY, float angleX, float angleZ, Quaternionf dest) {
        float sx = (float) Math.sin(angleX * 0.5);
        float cx = (float) Math.cosFromSin(sx, angleX * 0.5);
        float sy = (float) Math.sin(angleY * 0.5);
        float cy = (float) Math.cosFromSin(sy, angleY * 0.5);
        float sz = (float) Math.sin(angleZ * 0.5);
        float cz = (float) Math.cosFromSin(sz, angleZ * 0.5);

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

    /* (non-Javadoc)
     * @see org.joml.Quaternionfc#getEulerAnglesXYZ(org.joml.Vector3f)
     */
    public Vector3f getEulerAnglesXYZ(Vector3f eulerAngles) {
        eulerAngles.x = (float) Math.atan2(2.0 * (x*w - y*z), 1.0 - 2.0 * (x*x + y*y));
        eulerAngles.y = (float) Math.asin(2.0 * (x*z + y*w));
        eulerAngles.z = (float) Math.atan2(2.0 * (z*w - x*y), 1.0 - 2.0 * (y*y + z*z));
        return eulerAngles;
    }

    /* (non-Javadoc)
     * @see org.joml.Quaternionfc#lengthSquared()
     */
    public float lengthSquared() {
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
    public Quaternionf rotationXYZ(float angleX, float angleY, float angleZ) {
        float sx = (float) Math.sin(angleX * 0.5);
        float cx = (float) Math.cosFromSin(sx, angleX * 0.5);
        float sy = (float) Math.sin(angleY * 0.5);
        float cy = (float) Math.cosFromSin(sy, angleY * 0.5);
        float sz = (float) Math.sin(angleZ * 0.5);
        float cz = (float) Math.cosFromSin(sz, angleZ * 0.5);

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
    public Quaternionf rotationZYX(float angleZ, float angleY, float angleX) {
        float sx = (float) Math.sin(angleX * 0.5);
        float cx = (float) Math.cosFromSin(sx, angleX * 0.5);
        float sy = (float) Math.sin(angleY * 0.5);
        float cy = (float) Math.cosFromSin(sy, angleY * 0.5);
        float sz = (float) Math.sin(angleZ * 0.5);
        float cz = (float) Math.cosFromSin(sz, angleZ * 0.5);

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
    public Quaternionf rotationYXZ(float angleY, float angleX, float angleZ) {
        float sx = (float) Math.sin(angleX * 0.5);
        float cx = (float) Math.cosFromSin(sx, angleX * 0.5);
        float sy = (float) Math.sin(angleY * 0.5);
        float cy = (float) Math.cosFromSin(sy, angleY * 0.5);
        float sz = (float) Math.sin(angleZ * 0.5);
        float cz = (float) Math.cosFromSin(sz, angleZ * 0.5);

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
     * Interpolate between <code>this</code> {@link #normalize() unit} quaternion and the specified
     * <code>target</code> {@link #normalize() unit} quaternion using spherical linear interpolation using the specified interpolation factor <code>alpha</code>.
     * <p>
     * This method resorts to non-spherical linear interpolation when the absolute dot product of <code>this</code> and <code>target</code> is
     * below <code>1E-6f</code>.
     * 
     * @param target
     *          the target of the interpolation, which should be reached with <code>alpha = 1.0</code>
     * @param alpha
     *          the interpolation factor, within <code>[0..1]</code>
     * @return this
     */
    public Quaternionf slerp(Quaternionfc target, float alpha) {
        return slerp(target, alpha, this);
    }

    /* (non-Javadoc)
     * @see org.joml.Quaternionfc#slerp(org.joml.Quaternionfc, float, org.joml.Quaternionf)
     */
    public Quaternionf slerp(Quaternionfc target, float alpha, Quaternionf dest) {
        float cosom = x * target.x() + y * target.y() + z * target.z() + w * target.w();
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
     * This method will interpolate between each two successive quaternions via {@link #slerp(Quaternionfc, float)} using their relative interpolation weights.
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
    public static Quaternionfc slerp(Quaternionf[] qs, float[] weights, Quaternionf dest) {
        dest.set(qs[0]);
        float w = weights[0];
        for (int i = 1; i < qs.length; i++) {
            float w0 = w;
            float w1 = weights[i];
            float rw1 = w1 / (w0 + w1);
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
    public Quaternionf scale(float factor) {
        return scale(factor, this);
    }

    /* (non-Javadoc)
     * @see org.joml.Quaternionfc#scale(float, org.joml.Quaternionf)
     */
    public Quaternionf scale(float factor, Quaternionf dest) {
        float sqrt = (float) Math.sqrt(factor);
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
    public Quaternionf scaling(float factor) {
        float sqrt = (float) Math.sqrt(factor);
        this.x = 0.0f;
        this.y = 0.0f;
        this.z = 0.0f;
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
    public Quaternionf integrate(float dt, float vx, float vy, float vz) {
        return integrate(dt, vx, vy, vz, this);
    }

    /* (non-Javadoc)
     * @see org.joml.Quaternionfc#integrate(float, float, float, float, org.joml.Quaternionf)
     */
    public Quaternionf integrate(float dt, float vx, float vy, float vz, Quaternionf dest) {
        float thetaX = vx * 0.5f;
        float thetaY = vy * 0.5f;
        float thetaZ = vz * 0.5f;
        float thetaMagSq = thetaX * thetaX + thetaY * thetaY + thetaZ * thetaZ;
        float s;
        float dqX, dqY, dqZ, dqW;
        if (thetaMagSq * thetaMagSq / 24.0f < 1E-8f) {
            dqW = 1.0f - thetaMagSq * 0.5f;
            s = 1.0f - thetaMagSq / 6.0f;
        } else {
            float thetaMag = (float) Math.sqrt(thetaMagSq);
            float sin = (float) Math.sin(thetaMag);
            s = sin / thetaMag;
            dqW = (float) Math.cosFromSin(sin, thetaMag);
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
    public Quaternionf nlerp(Quaternionfc q, float factor) {
        return nlerp(q, factor, this);
    }

    /* (non-Javadoc)
     * @see org.joml.Quaternionfc#nlerp(org.joml.Quaternionfc, float, org.joml.Quaternionf)
     */
    public Quaternionf nlerp(Quaternionfc q, float factor, Quaternionf dest) {
        float cosom = x * q.x() + y * q.y() + z * q.z() + w * q.w();
        float scale0 = 1.0f - factor;
        float scale1 = (cosom >= 0.0f) ? factor : -factor;
        dest.x = scale0 * x + scale1 * q.x();
        dest.y = scale0 * y + scale1 * q.y();
        dest.z = scale0 * z + scale1 * q.z();
        dest.w = scale0 * w + scale1 * q.w();
        float s = (float) (1.0 / Math.sqrt(dest.x * dest.x + dest.y * dest.y + dest.z * dest.z + dest.w * dest.w));
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
     * This method will interpolate between each two successive quaternions via {@link #nlerp(Quaternionfc, float)}
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
    public static Quaternionfc nlerp(Quaternionfc[] qs, float[] weights, Quaternionf dest) {
        dest.set(qs[0]);
        float w = weights[0];
        for (int i = 1; i < qs.length; i++) {
            float w0 = w;
            float w1 = weights[i];
            float rw1 = w1 / (w0 + w1);
            w += w1;
            dest.nlerp(qs[i], rw1);
        }
        return dest;
    }

    /* (non-Javadoc)
     * @see org.joml.Quaternionfc#nlerpIterative(org.joml.Quaternionfc, float, float, org.joml.Quaternionf)
     */
    public Quaternionf nlerpIterative(Quaternionfc q, float alpha, float dotThreshold, Quaternionf dest) {
        float q1x = x, q1y = y, q1z = z, q1w = w;
        float q2x = q.x(), q2y = q.y(), q2z = q.z(), q2w = q.w();
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
                alphaN = alphaN + alphaN;
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
                alphaN = alphaN + alphaN - 1.0f;
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
     * Compute linear (non-spherical) interpolations of <code>this</code> and the given quaternion <code>q</code>
     * iteratively and store the result in <code>this</code>.
     * <p>
     * This method performs a series of small-step nlerp interpolations to avoid doing a costly spherical linear interpolation, like
     * {@link #slerp(Quaternionfc, float, Quaternionf) slerp},
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
    public Quaternionf nlerpIterative(Quaternionfc q, float alpha, float dotThreshold) {
        return nlerpIterative(q, alpha, dotThreshold, this);
    }

    /**
     * Interpolate between all of the quaternions given in <code>qs</code> via iterative non-spherical linear interpolation using the
     * specified interpolation factors <code>weights</code>, and store the result in <code>dest</code>.
     * <p>
     * This method will interpolate between each two successive quaternions via {@link #nlerpIterative(Quaternionfc, float, float)}
     * using their relative interpolation weights.
     * <p>
     * Reference: <a href="http://gamedev.stackexchange.com/questions/62354/method-for-interpolation-between-3-quaternions#answer-62356">http://gamedev.stackexchange.com/</a>
     * 
     * @param qs
     *          the quaternions to interpolate over
     * @param weights
     *          the weights of each individual quaternion in <code>qs</code>
     * @param dotThreshold
     *          the threshold for the dot product of each two interpolated quaternions above which {@link #nlerpIterative(Quaternionfc, float, float)} performs another iteration
     *          of a small-step linear interpolation
     * @param dest
     *          will hold the result
     * @return dest
     */
    public static Quaternionfc nlerpIterative(Quaternionf[] qs, float[] weights, float dotThreshold, Quaternionf dest) {
        dest.set(qs[0]);
        float w = weights[0];
        for (int i = 1; i < qs.length; i++) {
            float w0 = w;
            float w1 = weights[i];
            float rw1 = w1 / (w0 + w1);
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
     * @see #lookAlong(float, float, float, float, float, float, Quaternionf)
     * 
     * @param dir
     *              the direction to map to the positive Z axis
     * @param up
     *              the vector which will be mapped to a vector parallel to the plane
     *              spanned by the given <code>dir</code> and <code>up</code>
     * @return this
     */
    public Quaternionf lookAlong(Vector3fc dir, Vector3fc up) {
        return lookAlong(dir.x(), dir.y(), dir.z(), up.x(), up.y(), up.z(), this);
    }

    /* (non-Javadoc)
     * @see org.joml.Quaternionfc#lookAlong(org.joml.Vector3fc, org.joml.Vector3fc, org.joml.Quaternionf)
     */
    public Quaternionf lookAlong(Vector3fc dir, Vector3fc up, Quaternionf dest) {
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
     * @see #lookAlong(float, float, float, float, float, float, Quaternionf)
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
    public Quaternionf lookAlong(float dirX, float dirY, float dirZ, float upX, float upY, float upZ) {
        return lookAlong(dirX, dirY, dirZ, upX, upY, upZ, this);
    }

    /* (non-Javadoc)
     * @see org.joml.Quaternionfc#lookAlong(float, float, float, float, float, float, org.joml.Quaternionf)
     */
    public Quaternionf lookAlong(float dirX, float dirY, float dirZ, float upX, float upY, float upZ, Quaternionf dest) {
        // Normalize direction
        float invDirLength = (float) (1.0 / Math.sqrt(dirX * dirX + dirY * dirY + dirZ * dirZ));
        float dirnX = -dirX * invDirLength;
        float dirnY = -dirY * invDirLength;
        float dirnZ = -dirZ * invDirLength;
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
    public Quaternionf rotationTo(float fromDirX, float fromDirY, float fromDirZ, float toDirX, float toDirY, float toDirZ) {
        x = fromDirY * toDirZ - fromDirZ * toDirY;
        y = fromDirZ * toDirX - fromDirX * toDirZ;
        z = fromDirX * toDirY - fromDirY * toDirX;
        w = (float) Math.sqrt((fromDirX * fromDirX + fromDirY * fromDirY + fromDirZ * fromDirZ) *
                              (toDirX * toDirX + toDirY * toDirY + toDirZ * toDirZ)) +
                 (fromDirX * toDirX + fromDirY * toDirY + fromDirZ * toDirZ);
        float invNorm = (float) (1.0 / Math.sqrt(x * x + y * y + z * z + w * w));
        if (Float.isInfinite(invNorm)) {
            // Rotation is ambiguous: Find appropriate rotation axis (1. try toDir x +Z)
            x = toDirY; y = -toDirX; z = 0.0f; w = 0.0f;
            invNorm = (float) (1.0 / Math.sqrt(x * x + y * y));
            if (Float.isInfinite(invNorm)) {
                // 2. try toDir x +X
                x = 0.0f; y = toDirZ; z = -toDirY; w = 0.0f;
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
     * @see #rotationTo(float, float, float, float, float, float)
     * 
     * @param fromDir
     *          the starting direction
     * @param toDir
     *          the destination direction
     * @return this
     */
    public Quaternionf rotationTo(Vector3fc fromDir, Vector3fc toDir) {
        return rotationTo(fromDir.x(), fromDir.y(), fromDir.z(), toDir.x(), toDir.y(), toDir.z());
    }

    /* (non-Javadoc)
     * @see org.joml.Quaternionfc#rotateTo(float, float, float, float, float, float, org.joml.Quaternionf)
     */
    public Quaternionf rotateTo(float fromDirX, float fromDirY, float fromDirZ, float toDirX, float toDirY, float toDirZ, Quaternionf dest) {
        float x = fromDirY * toDirZ - fromDirZ * toDirY;
        float y = fromDirZ * toDirX - fromDirX * toDirZ;
        float z = fromDirX * toDirY - fromDirY * toDirX;
        float w = (float) Math.sqrt((fromDirX * fromDirX + fromDirY * fromDirY + fromDirZ * fromDirZ) *
                                    (toDirX * toDirX + toDirY * toDirY + toDirZ * toDirZ)) +
                  (fromDirX * toDirX + fromDirY * toDirY + fromDirZ * toDirZ);
        float invNorm = (float) (1.0 / Math.sqrt(x * x + y * y + z * z + w * w));
        if (Float.isInfinite(invNorm)) {
            // Rotation is ambiguous: Find appropriate rotation axis (1. try toDir x +Z)
            x = toDirY; y = -toDirX; z = 0.0f; w = 0.0f;
            invNorm = (float) (1.0 / Math.sqrt(x * x + y * y));
            if (Float.isInfinite(invNorm)) {
                // 2. try toDir x +X
                x = 0.0f; y = toDirZ; z = -toDirY; w = 0.0f;
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
     * Apply a rotation to <code>this</code> that rotates the <code>fromDir</code> vector to point along <code>toDir</code>.
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

    /* (non-Javadoc)
     * @see org.joml.Quaternionfc#rotateTo(org.joml.Vector3fc, org.joml.Vector3fc, org.joml.Quaternionf)
     */
    public Quaternionf rotateTo(Vector3fc fromDir, Vector3fc toDir, Quaternionf dest) {
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
     * @see #rotateTo(float, float, float, float, float, float, Quaternionf)
     * 
     * @param fromDir
     *          the starting direction
     * @param toDir
     *          the destination direction
     * @return this
     */
    public Quaternionf rotateTo(Vector3fc fromDir, Vector3fc toDir) {
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
    public Quaternionf rotateX(float angle) {
        return rotateX(angle, this);
    }

    /* (non-Javadoc)
     * @see org.joml.Quaternionfc#rotateX(float, org.joml.Quaternionf)
     */
    public Quaternionf rotateX(float angle, Quaternionf dest) {
        float sin = (float) Math.sin(angle * 0.5);
        float cos = (float) Math.cosFromSin(sin, angle * 0.5);
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
    public Quaternionf rotateY(float angle) {
        return rotateY(angle, this);
    }

    /* (non-Javadoc)
     * @see org.joml.Quaternionfc#rotateY(float, org.joml.Quaternionf)
     */
    public Quaternionf rotateY(float angle, Quaternionf dest) {
        float sin = (float) Math.sin(angle * 0.5);
        float cos = (float) Math.cosFromSin(sin, angle * 0.5);
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
    public Quaternionf rotateZ(float angle) {
        return rotateZ(angle, this);
    }

    /* (non-Javadoc)
     * @see org.joml.Quaternionfc#rotateZ(float, org.joml.Quaternionf)
     */
    public Quaternionf rotateZ(float angle, Quaternionf dest) {
        float sin = (float) Math.sin(angle * 0.5);
        float cos = (float) Math.cosFromSin(sin, angle * 0.5);
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
    public Quaternionf rotateLocalX(float angle) {
        return rotateLocalX(angle, this);
    }

    /* (non-Javadoc)
     * @see org.joml.Quaternionfc#rotateLocalX(float, org.joml.Quaternionf)
     */
    public Quaternionf rotateLocalX(float angle, Quaternionf dest) {
        float hangle = angle * 0.5f;
        float s = (float) Math.sin(hangle);
        float c = (float) Math.cosFromSin(s, hangle);
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
    public Quaternionf rotateLocalY(float angle) {
        return rotateLocalY(angle, this);
    }

    /* (non-Javadoc)
     * @see org.joml.Quaternionfc#rotateLocalY(float, org.joml.Quaternionf)
     */
    public Quaternionf rotateLocalY(float angle, Quaternionf dest) {
        float hangle = angle * 0.5f;
        float s = (float) Math.sin(hangle);
        float c = (float) Math.cosFromSin(s, hangle);
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
    public Quaternionf rotateLocalZ(float angle) {
        return rotateLocalZ(angle, this);
    }

    /* (non-Javadoc)
     * @see org.joml.Quaternionfc#rotateLocalZ(float, org.joml.Quaternionf)
     */
    public Quaternionf rotateLocalZ(float angle, Quaternionf dest) {
        float hangle = angle * 0.5f;
        float s = (float) Math.sin(hangle);
        float c = (float) Math.cosFromSin(s, hangle);
        dest.set(c * x - s * y,
                 c * y + s * x,
                 c * z + s * w,
                 c * w - s * z);
        return dest;
    }

    /* (non-Javadoc)
     * @see org.joml.Quaternionfc#rotateAxis(float, float, float, float, org.joml.Quaternionf)
     */
    public Quaternionf rotateAxis(float angle, float axisX, float axisY, float axisZ, Quaternionf dest) {
        double hangle = angle / 2.0;
        double sinAngle = Math.sin(hangle);
        double invVLength = 1.0 / Math.sqrt(axisX * axisX + axisY * axisY + axisZ * axisZ);

        double rx = axisX * invVLength * sinAngle;
        double ry = axisY * invVLength * sinAngle;
        double rz = axisZ * invVLength * sinAngle;
        double rw = Math.cosFromSin(sinAngle, hangle);

        dest.set((float) (w * rx + x * rw + y * rz - z * ry),
                 (float) (w * ry - x * rz + y * rw + z * rx),
                 (float) (w * rz + x * ry - y * rx + z * rw),
                 (float) (w * rw - x * rx - y * ry - z * rz));
        return dest;
    }

    /* (non-Javadoc)
     * @see org.joml.Quaternionfc#rotateAxis(float, org.joml.Vector3fc, org.joml.Quaternionf)
     */
    public Quaternionf rotateAxis(float angle, Vector3fc axis, Quaternionf dest) {
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
     * @see #rotateAxis(float, float, float, float, Quaternionf)
     * 
     * @param angle
     *              the angle in radians to rotate about the specified axis
     * @param axis
     *              the rotation axis
     * @return this
     */
    public Quaternionf rotateAxis(float angle, Vector3fc axis) {
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
    public Quaternionf difference(Quaternionf other) {
        return difference(other, this);
    }

    /* (non-Javadoc)
     * @see org.joml.Quaternionfc#difference(org.joml.Quaternionf, org.joml.Quaternionf)
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

    /* (non-Javadoc)
     * @see org.joml.Quaternionfc#positiveX(org.joml.Vector3f)
     */
    public Vector3f positiveX(Vector3f dir) {
        float invNorm = 1.0f / (x * x + y * y + z * z + w * w);
        float nx = -x * invNorm;
        float ny = -y * invNorm;
        float nz = -z * invNorm;
        float nw =  w * invNorm;
        float dy = ny + ny;
        float dz = nz + nz;
        dir.x = -ny * dy - nz * dz + 1.0f;
        dir.y =  nx * dy + nw * dz;
        dir.z =  nx * dz - nw * dy;
        return dir;
    }

    /* (non-Javadoc)
     * @see org.joml.Quaternionfc#normalizedPositiveX(org.joml.Vector3f)
     */
    public Vector3f normalizedPositiveX(Vector3f dir) {
        float dy = y + y;
        float dz = z + z;
        dir.x = -y * dy - z * dz + 1.0f;
        dir.y =  x * dy - w * dz;
        dir.z =  x * dz + w * dy;
        return dir;
    }

    /* (non-Javadoc)
     * @see org.joml.Quaternionfc#positiveY(org.joml.Vector3f)
     */
    public Vector3f positiveY(Vector3f dir) {
        float invNorm = 1.0f / (x * x + y * y + z * z + w * w);
        float nx = -x * invNorm;
        float ny = -y * invNorm;
        float nz = -z * invNorm;
        float nw =  w * invNorm;
        float dx = nx + nx;
        float dy = ny + ny;
        float dz = nz + nz;
        dir.x =  nx * dy - nw * dz;
        dir.y = -nx * dx - nz * dz + 1.0f;
        dir.z =  ny * dz + nw * dx;
        return dir;
    }

    /* (non-Javadoc)
     * @see org.joml.Quaternionfc#normalizedPositiveY(org.joml.Vector3f)
     */
    public Vector3f normalizedPositiveY(Vector3f dir) {
        float dx = x + x;
        float dy = y + y;
        float dz = z + z;
        dir.x =  x * dy + w * dz;
        dir.y = -x * dx - z * dz + 1.0f;
        dir.z =  y * dz - w * dx;
        return dir;
    }

    /* (non-Javadoc)
     * @see org.joml.Quaternionfc#positiveZ(org.joml.Vector3f)
     */
    public Vector3f positiveZ(Vector3f dir) {
        float invNorm = 1.0f / (x * x + y * y + z * z + w * w);
        float nx = -x * invNorm;
        float ny = -y * invNorm;
        float nz = -z * invNorm;
        float nw =  w * invNorm;
        float dx = nx + nx;
        float dy = ny + ny;
        float dz = nz + nz;
        dir.x =  nx * dz + nw * dy;
        dir.y =  ny * dz - nw * dx;
        dir.z = -nx * dx - ny * dy + 1.0f;
        return dir;
    }

    /* (non-Javadoc)
     * @see org.joml.Quaternionfc#normalizedPositiveZ(org.joml.Vector3f)
     */
    public Vector3f normalizedPositiveZ(Vector3f dir) {
        float dx = x + x;
        float dy = y + y;
        float dz = z + z;
        dir.x =  x * dz - w * dy;
        dir.y =  y * dz + w * dx;
        dir.z = -x * dx - y * dy + 1.0f;
        return dir;
    }

}
