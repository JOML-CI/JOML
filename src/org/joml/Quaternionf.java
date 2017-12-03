/*
 * (C) Copyright 2015-2017 Richard Greenlees

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

import org.joml.api.AxisAngle4dc;
import org.joml.api.AxisAngle4fc;
import org.joml.api.matrix.*;
import org.joml.api.quaternion.IQuaternionf;
import org.joml.api.quaternion.Quaterniondc;
import org.joml.api.quaternion.Quaternionfc;
import org.joml.api.vector.*;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
//#ifdef __HAS_NIO__
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
//#endif
import java.text.NumberFormat;

/**
 * Quaternion of 4 single-precision floats which can represent rotation and uniform scaling.
 *
 * @author Richard Greenlees
 * @author Kai Burjack
 */
public class Quaternionf extends Quaternionfc implements Externalizable {

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
     * Create a new {@link Quaternionf} and initialize it with <tt>(x=0, y=0, z=0, w=1)</tt>, where <tt>(x, y, z)</tt>
     * is the vector part of the quaternion and <tt>w</tt> is the real/scalar part.
     */
    public Quaternionf() {
        this.w = 1.0f;
    }

    /**
     * Create a new {@link Quaternionf} and initialize its components to the given values.
     *
     * @param x the first component of the imaginary part
     * @param y the second component of the imaginary part
     * @param z the third component of the imaginary part
     * @param w the real part
     */
    public Quaternionf(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    /**
     * Create a new {@link Quaternionf} and initialize its imaginary components to the given values, and its real part
     * to <tt>1.0</tt>.
     *
     * @param x the first component of the imaginary part
     * @param y the second component of the imaginary part
     * @param z the third component of the imaginary part
     */
    public Quaternionf(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
        w = 1.0f;
    }

    /**
     * Create a new {@link Quaternionf} and initialize its components to the same values as the given {@link
     * Quaternionf}.
     *
     * @param source the {@link Quaternionf} to take the component values from
     */
    public Quaternionf(Quaternionf source) {
        MemUtil.INSTANCE.copy(source, this);
    }

    /**
     * Create a new {@link Quaternionf} which represents the rotation of the given {@link AxisAngle4fc}.
     *
     * @param axisAngle the {@link AxisAngle4fc}
     */
    public Quaternionf(AxisAngle4fc axisAngle) {
        float sin = (float) Math.sin(axisAngle.angle() * 0.5);
        float cos = (float) Math.cosFromSin(sin, axisAngle.angle() * 0.5);
        x = axisAngle.x() * sin;
        y = axisAngle.y() * sin;
        z = axisAngle.z() * sin;
        w = cos;
    }

    public float x() {
        return this.x;
    }

    public float y() {
        return this.y;
    }

    public float z() {
        return this.z;
    }

    public float w() {
        return this.w;
    }

    public Quaternionfc normalize() {
        float invNorm = (float) (1.0 / Math.sqrt(x * x + y * y + z * z + w * w));
        x *= invNorm;
        y *= invNorm;
        z *= invNorm;
        w *= invNorm;
        return this;
    }

    public Quaternionfc normalize(Quaternionfc dest) {
        float invNorm = (float) (1.0 / Math.sqrt(x * x + y * y + z * z + w * w));
        dest.set(x * invNorm, y * invNorm, z * invNorm, w * invNorm);
        return dest;
    }

    public Quaternionfc add(float x, float y, float z, float w) {
        return add(x, y, z, w, this);
    }

    public Quaternionfc add(float x, float y, float z, float w, Quaternionfc dest) {
        dest.set(this.x + x, this.y + y, this.z + z, this.w + w);
        return dest;
    }

    public Quaternionfc add(IQuaternionf q2) {
        x += q2.x();
        y += q2.y();
        z += q2.z();
        w += q2.w();
        return this;
    }

    public Quaternionfc add(IQuaternionf q2, Quaternionfc dest) {
        dest.set(x + q2.x(), y + q2.y(), z + q2.z(), w + q2.w());
        return dest;
    }

    public float dot(Quaternionfc otherQuat) {
        return this.x * otherQuat.x() + this.y * otherQuat.y() + this.z * otherQuat.z() + this.w * otherQuat.w();
    }

    public float angle() {
        float angle = (float) (2.0 * Math.acos(w));
        return (float) (angle <= Math.PI ? angle : Math.PI + Math.PI - angle);
    }

    public Matrix3fc get(Matrix3fc dest) {
        return dest.set(this);
    }

    public Matrix3dc get(Matrix3dc dest) {
        return dest.set(this);
    }

    public Matrix4fc get(Matrix4fc dest) {
        return dest.set(this);
    }

    public Matrix4dc get(Matrix4dc dest) {
        return dest.set(this);
    }

    public Matrix4x3fc get(Matrix4x3fc dest) {
        return dest.set(this);
    }

    public Matrix4x3dc get(Matrix4x3dc dest) {
        return dest.set(this);
    }

    public AxisAngle4fc get(AxisAngle4fc dest) {
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
        // I swear I will fix this in the next commit
        float s = (float) Math.sqrt(1.0 - w * w);
        s = s < 0.001f ? 1 : 1.0f / s;
        dest.set((float) (2.0f * Math.acos(w)), x * s, y * s, z * s);
        return dest;
    }

    public Quaterniondc get(Quaterniondc dest) {
        return dest.set(this);
    }

    public Quaternionfc get(Quaternionfc dest) {
        return dest.set(this);
    }

    //#ifdef __HAS_NIO__

    public ByteBuffer getAsMatrix3f(ByteBuffer dest) {
        MemUtil.INSTANCE.putMatrix3f(this, dest.position(), dest);
        return dest;
    }

    public FloatBuffer getAsMatrix3f(FloatBuffer dest) {
        MemUtil.INSTANCE.putMatrix3f(this, dest.position(), dest);
        return dest;
    }

    public ByteBuffer getAsMatrix4f(ByteBuffer dest) {
        MemUtil.INSTANCE.putMatrix4f(this, dest.position(), dest);
        return dest;
    }

    public FloatBuffer getAsMatrix4f(FloatBuffer dest) {
        MemUtil.INSTANCE.putMatrix4f(this, dest.position(), dest);
        return dest;
    }

    public ByteBuffer getAsMatrix4x3f(ByteBuffer dest) {
        MemUtil.INSTANCE.putMatrix4x3f(this, dest.position(), dest);
        return dest;
    }

    public FloatBuffer getAsMatrix4x3f(FloatBuffer dest) {
        MemUtil.INSTANCE.putMatrix4x3f(this, dest.position(), dest);
        return dest;
    }
    //#endif

    public Quaternionfc set(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
        return this;
    }

    public Quaternionfc set(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }

    public Quaternionfc set(IQuaternionf q) {
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

    public Quaternionfc set(AxisAngle4fc axisAngle) {
        return setAngleAxis(axisAngle.angle(), axisAngle.x(), axisAngle.y(), axisAngle.z());
    }

    public Quaternionfc set(AxisAngle4dc axisAngle) {
        return setAngleAxis(axisAngle.angle(), axisAngle.x(), axisAngle.y(), axisAngle.z());
    }

    public Quaternionfc setAngleAxis(float angle, float x, float y, float z) {
        float s = (float) Math.sin(angle * 0.5);
        this.x = x * s;
        this.y = y * s;
        this.z = z * s;
        this.w = (float) Math.cosFromSin(s, angle * 0.5);
        return this;
    }

    public Quaternionfc setAngleAxis(double angle, double x, double y, double z) {
        double s = Math.sin(angle * 0.5);
        this.x = (float) (x * s);
        this.y = (float) (y * s);
        this.z = (float) (z * s);
        this.w = (float) Math.cosFromSin(s, angle * 0.5);
        return this;
    }

    public Quaternionfc rotationAxis(AxisAngle4fc axisAngle) {
        return rotationAxis(axisAngle.angle(), axisAngle.x(), axisAngle.y(), axisAngle.z());
    }

    public Quaternionfc rotationAxis(float angle, float axisX, float axisY, float axisZ) {
        float hangle = angle / 2.0f;
        float sinAngle = (float) Math.sin(hangle);
        float invVLength = (float) (1.0 / Math.sqrt(axisX * axisX + axisY * axisY + axisZ * axisZ));

        x = axisX * invVLength * sinAngle;
        y = axisY * invVLength * sinAngle;
        z = axisZ * invVLength * sinAngle;
        w = (float) Math.cosFromSin(sinAngle, hangle);

        return this;
    }

    public Quaternionfc rotationAxis(float angle, IVector3f axis) {
        return rotationAxis(angle, axis.x(), axis.y(), axis.z());
    }

    public Quaternionfc rotation(float angleX, float angleY, float angleZ) {
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
            double sin = Math.sin(thetaMag);
            s = sin / thetaMag;
            w = (float) Math.cosFromSin(sin, thetaMag);
        }
        x = (float) (thetaX * s);
        y = (float) (thetaY * s);
        z = (float) (thetaZ * s);
        return this;
    }

    public Quaternionfc rotationX(float angle) {
        float sin = (float) Math.sin(angle * 0.5);
        float cos = (float) Math.cosFromSin(sin, angle * 0.5);
        w = cos;
        x = sin;
        y = 0.0f;
        z = 0.0f;
        return this;
    }

    public Quaternionfc rotationY(float angle) {
        float sin = (float) Math.sin(angle * 0.5);
        float cos = (float) Math.cosFromSin(sin, angle * 0.5);
        w = cos;
        x = 0.0f;
        y = sin;
        z = 0.0f;
        return this;
    }

    public Quaternionfc rotationZ(float angle) {
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
        nm00 *= lenX;
        nm01 *= lenX;
        nm02 *= lenX;
        nm10 *= lenY;
        nm11 *= lenY;
        nm12 *= lenY;
        nm20 *= lenZ;
        nm21 *= lenZ;
        nm22 *= lenZ;
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
        nm00 *= lenX;
        nm01 *= lenX;
        nm02 *= lenX;
        nm10 *= lenY;
        nm11 *= lenY;
        nm12 *= lenY;
        nm20 *= lenZ;
        nm21 *= lenZ;
        nm22 *= lenZ;
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

    public Quaternionfc setFromUnnormalized(IMatrix4f mat) {
        setFromUnnormalized(mat.m00(), mat.m01(), mat.m02(), mat.m10(), mat.m11(), mat.m12(), mat.m20(), mat.m21(), mat.m22());
        return this;
    }

    public Quaternionfc setFromUnnormalized(IMatrix4x3f mat) {
        setFromUnnormalized(mat.m00(), mat.m01(), mat.m02(), mat.m10(), mat.m11(), mat.m12(), mat.m20(), mat.m21(), mat.m22());
        return this;
    }

    public Quaternionfc setFromUnnormalized(IMatrix4x3d mat) {
        setFromUnnormalized(mat.m00(), mat.m01(), mat.m02(), mat.m10(), mat.m11(), mat.m12(), mat.m20(), mat.m21(), mat.m22());
        return this;
    }

    public Quaternionfc setFromNormalized(IMatrix4f mat) {
        setFromNormalized(mat.m00(), mat.m01(), mat.m02(), mat.m10(), mat.m11(), mat.m12(), mat.m20(), mat.m21(), mat.m22());
        return this;
    }

    public Quaternionfc setFromNormalized(IMatrix4x3f mat) {
        setFromNormalized(mat.m00(), mat.m01(), mat.m02(), mat.m10(), mat.m11(), mat.m12(), mat.m20(), mat.m21(), mat.m22());
        return this;
    }

    public Quaternionfc setFromNormalized(IMatrix4x3d mat) {
        setFromNormalized(mat.m00(), mat.m01(), mat.m02(), mat.m10(), mat.m11(), mat.m12(), mat.m20(), mat.m21(), mat.m22());
        return this;
    }

    public Quaternionfc setFromUnnormalized(IMatrix4d mat) {
        setFromUnnormalized(mat.m00(), mat.m01(), mat.m02(), mat.m10(), mat.m11(), mat.m12(), mat.m20(), mat.m21(), mat.m22());
        return this;
    }

    public Quaternionfc setFromNormalized(IMatrix4d mat) {
        setFromNormalized(mat.m00(), mat.m01(), mat.m02(), mat.m10(), mat.m11(), mat.m12(), mat.m20(), mat.m21(), mat.m22());
        return this;
    }

    public Quaternionfc setFromUnnormalized(IMatrix3f mat) {
        setFromUnnormalized(mat.m00(), mat.m01(), mat.m02(), mat.m10(), mat.m11(), mat.m12(), mat.m20(), mat.m21(), mat.m22());
        return this;
    }

    public Quaternionfc setFromNormalized(IMatrix3f mat) {
        setFromNormalized(mat.m00(), mat.m01(), mat.m02(), mat.m10(), mat.m11(), mat.m12(), mat.m20(), mat.m21(), mat.m22());
        return this;
    }

    public Quaternionfc setFromUnnormalized(IMatrix3d mat) {
        setFromUnnormalized(mat.m00(), mat.m01(), mat.m02(), mat.m10(), mat.m11(), mat.m12(), mat.m20(), mat.m21(), mat.m22());
        return this;
    }

    public Quaternionfc setFromNormalized(IMatrix3d mat) {
        setFromNormalized(mat.m00(), mat.m01(), mat.m02(), mat.m10(), mat.m11(), mat.m12(), mat.m20(), mat.m21(), mat.m22());
        return this;
    }

    public Quaternionfc fromAxisAngleRad(IVector3f axis, float angle) {
        return fromAxisAngleRad(axis.x(), axis.y(), axis.z(), angle);
    }

    public Quaternionfc fromAxisAngleRad(float axisX, float axisY, float axisZ, float angle) {
        float hangle = angle / 2.0f;
        float sinAngle = (float) Math.sin(hangle);
        float vLength = (float) Math.sqrt(axisX * axisX + axisY * axisY + axisZ * axisZ);
        x = axisX / vLength * sinAngle;
        y = axisY / vLength * sinAngle;
        z = axisZ / vLength * sinAngle;
        w = (float) Math.cosFromSin(sinAngle, hangle);
        return this;
    }

    public Quaternionfc fromAxisAngleDeg(IVector3f axis, float angle) {
        return fromAxisAngleRad(axis.x(), axis.y(), axis.z(), (float) Math.toRadians(angle));
    }

    public Quaternionfc fromAxisAngleDeg(float axisX, float axisY, float axisZ, float angle) {
        return fromAxisAngleRad(axisX, axisY, axisZ, (float) Math.toRadians(angle));
    }

    public Quaternionfc mul(IQuaternionf q) {
        return mul(q, this);
    }

    public Quaternionfc mul(IQuaternionf q, Quaternionfc dest) {
        dest.set(w * q.x() + x * q.w() + y * q.z() - z * q.y(),
                w * q.y() - x * q.z() + y * q.w() + z * q.x(),
                w * q.z() + x * q.y() - y * q.x() + z * q.w(),
                w * q.w() - x * q.x() - y * q.y() - z * q.z());
        return dest;
    }

    public Quaternionfc mul(float qx, float qy, float qz, float qw) {
        set(w * qx + x * qw + y * qz - z * qy,
                w * qy - x * qz + y * qw + z * qx,
                w * qz + x * qy - y * qx + z * qw,
                w * qw - x * qx - y * qy - z * qz);
        return this;
    }

    public Quaternionfc mul(float qx, float qy, float qz, float qw, Quaternionfc dest) {
        dest.set(w * qx + x * qw + y * qz - z * qy,
                w * qy - x * qz + y * qw + z * qx,
                w * qz + x * qy - y * qx + z * qw,
                w * qw - x * qx - y * qy - z * qz);
        return dest;
    }

    public Quaternionfc premul(IQuaternionf q) {
        return premul(q, this);
    }

    public Quaternionfc premul(IQuaternionf q, Quaternionfc dest) {
        dest.set(q.w() * x + q.x() * w + q.y() * z - q.z() * y,
                q.w() * y - q.x() * z + q.y() * w + q.z() * x,
                q.w() * z + q.x() * y - q.y() * x + q.z() * w,
                q.w() * w - q.x() * x - q.y() * y - q.z() * z);
        return dest;
    }

    public Quaternionfc premul(float qx, float qy, float qz, float qw) {
        return premul(qx, qy, qz, qw, this);
    }

    public Quaternionfc premul(float qx, float qy, float qz, float qw, Quaternionfc dest) {
        dest.set(qw * x + qx * w + qy * z - qz * y,
                qw * y - qx * z + qy * w + qz * x,
                qw * z + qx * y - qy * x + qz * w,
                qw * w - qx * x - qy * y - qz * z);
        return dest;
    }

    public Vector3fc transform(Vector3fc vec) {
        return transform(vec.x(), vec.y(), vec.z(), vec);
    }

    public Vector4fc transform(Vector4fc vec) {
        return transform(vec, vec);
    }

    public Vector3fc transform(IVector3f vec, Vector3fc dest) {
        return transform(vec.x(), vec.y(), vec.z(), dest);
    }

    public Vector3dc transform(IVector3d vec, Vector3dc dest) {
        return transform(vec.x(), vec.y(), vec.z(), dest);
    }

    public Vector3fc transform(float x, float y, float z, Vector3fc dest) {
        float w2 = this.w * this.w;
        float x2 = this.x * this.x;
        float y2 = this.y * this.y;
        float z2 = this.z * this.z;
        float zw = this.z * this.w;
        float xy = this.x * this.y;
        float xz = this.x * this.z;
        float yw = this.y * this.w;
        float yz = this.y * this.z;
        float xw = this.x * this.w;
        float m00 = w2 + x2 - z2 - y2;
        float m01 = xy + zw + zw + xy;
        float m02 = xz - yw + xz - yw;
        float m10 = -zw + xy - zw + xy;
        float m11 = y2 - z2 + w2 - x2;
        float m12 = yz + yz + xw + xw;
        float m20 = yw + xz + xz + yw;
        float m21 = yz + yz - xw - xw;
        float m22 = z2 - y2 - x2 + w2;
        dest.set(m00 * x + m10 * y + m20 * z,
                m01 * x + m11 * y + m21 * z,
                m02 * x + m12 * y + m22 * z);
        return dest;
    }

    public Vector3dc transform(double x, double y, double z, Vector3dc dest) {
        float w2 = this.w * this.w;
        float x2 = this.x * this.x;
        float y2 = this.y * this.y;
        float z2 = this.z * this.z;
        float zw = this.z * this.w;
        float xy = this.x * this.y;
        float xz = this.x * this.z;
        float yw = this.y * this.w;
        float yz = this.y * this.z;
        float xw = this.x * this.w;
        float m00 = w2 + x2 - z2 - y2;
        float m01 = xy + zw + zw + xy;
        float m02 = xz - yw + xz - yw;
        float m10 = -zw + xy - zw + xy;
        float m11 = y2 - z2 + w2 - x2;
        float m12 = yz + yz + xw + xw;
        float m20 = yw + xz + xz + yw;
        float m21 = yz + yz - xw - xw;
        float m22 = z2 - y2 - x2 + w2;
        dest.set(m00 * x + m10 * y + m20 * z,
                m01 * x + m11 * y + m21 * z,
                m02 * x + m12 * y + m22 * z);
        return dest;
    }

    public Vector4fc transform(IVector4f vec, Vector4fc dest) {
        return transform(vec.x(), vec.y(), vec.z(), dest);
    }

    public Vector4fc transform(float x, float y, float z, Vector4fc dest) {
        float w2 = this.w * this.w;
        float x2 = this.x * this.x;
        float y2 = this.y * this.y;
        float z2 = this.z * this.z;
        float zw = this.z * this.w;
        float xy = this.x * this.y;
        float xz = this.x * this.z;
        float yw = this.y * this.w;
        float yz = this.y * this.z;
        float xw = this.x * this.w;
        float m00 = w2 + x2 - z2 - y2;
        float m01 = xy + zw + zw + xy;
        float m02 = xz - yw + xz - yw;
        float m10 = -zw + xy - zw + xy;
        float m11 = y2 - z2 + w2 - x2;
        float m12 = yz + yz + xw + xw;
        float m20 = yw + xz + xz + yw;
        float m21 = yz + yz - xw - xw;
        float m22 = z2 - y2 - x2 + w2;
        dest.set(m00 * x + m10 * y + m20 * z,
                m01 * x + m11 * y + m21 * z,
                m02 * x + m12 * y + m22 * z,
                dest.w());
        return dest;
    }

    public Quaternionfc invert(Quaternionfc dest) {
        float invNorm = 1.0f / (x * x + y * y + z * z + w * w);
        dest.set(-x * invNorm, -y * invNorm, -z * invNorm, w * invNorm);
        return dest;
    }

    public Quaternionfc invert() {
        return invert(this);
    }

    public Quaternionfc div(IQuaternionf b, Quaternionfc dest) {
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

    public Quaternionfc div(IQuaternionf b) {
        return div(b, this);
    }

    public Quaternionfc conjugate() {
        x = -x;
        y = -y;
        z = -z;
        return this;
    }

    public Quaternionfc conjugate(Quaternionfc dest) {
        dest.set(-x, -y, -z, w);
        return dest;
    }

    public Quaternionfc identity() {
        MemUtil.INSTANCE.identity(this);
        return this;
    }

    public Quaternionfc rotateXYZ(float angleX, float angleY, float angleZ) {
        return rotateXYZ(angleX, angleY, angleZ, this);
    }

    public Quaternionfc rotateXYZ(float angleX, float angleY, float angleZ, Quaternionfc dest) {
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
        float w = cx * cycz - sx * sysz;
        float x = sx * cycz + cx * sysz;
        float y = cx * sycz - sx * cysz;
        float z = cx * cysz + sx * sycz;
        // right-multiply
        dest.set(this.w * x + this.x * w + this.y * z - this.z * y,
                this.w * y - this.x * z + this.y * w + this.z * x,
                this.w * z + this.x * y - this.y * x + this.z * w,
                this.w * w - this.x * x - this.y * y - this.z * z);
        return dest;
    }

    public Quaternionfc rotateZYX(float angleZ, float angleY, float angleX) {
        return rotateZYX(angleZ, angleY, angleX, this);
    }

    public Quaternionfc rotateZYX(float angleZ, float angleY, float angleX, Quaternionfc dest) {
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
        float w = cx * cycz + sx * sysz;
        float x = sx * cycz - cx * sysz;
        float y = cx * sycz + sx * cysz;
        float z = cx * cysz - sx * sycz;
        // right-multiply
        dest.set(this.w * x + this.x * w + this.y * z - this.z * y,
                this.w * y - this.x * z + this.y * w + this.z * x,
                this.w * z + this.x * y - this.y * x + this.z * w,
                this.w * w - this.x * x - this.y * y - this.z * z);
        return dest;
    }

    public Quaternionfc rotateYXZ(float angleZ, float angleY, float angleX) {
        return rotateYXZ(angleZ, angleY, angleX, this);
    }

    public Quaternionfc rotateYXZ(float angleY, float angleX, float angleZ, Quaternionfc dest) {
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

    public Vector3fc getEulerAnglesXYZ(Vector3fc eulerAngles) {
        eulerAngles.set((float) Math.atan2(2.0 * (x * w - y * z), 1.0 - 2.0 * (x * x + y * y)),
                (float) Math.asin(2.0 * (x * z + y * w)),
                (float) Math.atan2(2.0 * (z * w - x * y), 1.0 - 2.0 * (y * y + z * z)));
        return eulerAngles;
    }

    public float lengthSquared() {
        return x * x + y * y + z * z + w * w;
    }

    public Quaternionfc rotationXYZ(float angleX, float angleY, float angleZ) {
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
        w = cx * cycz - sx * sysz;
        x = sx * cycz + cx * sysz;
        y = cx * sycz - sx * cysz;
        z = cx * cysz + sx * sycz;

        return this;
    }

    public Quaternionfc rotationZYX(float angleZ, float angleY, float angleX) {
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
        w = cx * cycz + sx * sysz;
        x = sx * cycz - cx * sysz;
        y = cx * sycz + sx * cysz;
        z = cx * cysz - sx * sycz;

        return this;
    }

    public Quaternionfc rotationYXZ(float angleY, float angleX, float angleZ) {
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

    public Quaternionfc slerp(IQuaternionf target, float alpha) {
        return slerp(target, alpha, this);
    }

    public Quaternionfc slerp(IQuaternionf target, float alpha, Quaternionfc dest) {
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
        dest.set(scale0 * x + scale1 * target.x(), scale0 * y + scale1 * target.y(), scale0 * z + scale1 * target.z(), scale0 * w + scale1 * target.w());
        return dest;
    }

    /**
     * Interpolate between all of the quaternions given in <code>qs</code> via spherical linear interpolation using the
     * specified interpolation factors <code>weights</code>, and store the result in <code>dest</code>.
     * <p>
     * This method will interpolate between each two successive quaternions via {@link #slerp(IQuaternionf, float)}
     * using their relative interpolation weights.
     * <p>
     * This method resorts to non-spherical linear interpolation when the absolute dot product of any two interpolated
     * quaternions is below <tt>1E-6f</tt>.
     * <p>
     * Reference: <a href="http://gamedev.stackexchange.com/questions/62354/method-for-interpolation-between-3-quaternions#answer-62356">http://gamedev.stackexchange.com/</a>
     *
     * @param qs      the quaternions to interpolate over
     * @param weights the weights of each individual quaternion in <code>qs</code>
     * @param dest    will hold the result
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

    public Quaternionfc scale(float factor) {
        return scale(factor, this);
    }

    public Quaternionfc scale(float factor, Quaternionfc dest) {
        float sqrt = (float) Math.sqrt(factor);
        dest.set(sqrt * x, sqrt * y, sqrt * z, sqrt * w);
        return this;
    }

    public Quaternionfc scaling(float factor) {
        float sqrt = (float) Math.sqrt(factor);
        this.x = 0.0f;
        this.y = 0.0f;
        this.z = 0.0f;
        this.w = sqrt;
        return this;
    }

    public Quaternionfc integrate(float dt, float vx, float vy, float vz) {
        return integrate(dt, vx, vy, vz, this);
    }

    public Quaternionfc integrate(float dt, float vx, float vy, float vz, Quaternionfc dest) {
        return rotateLocal(dt * vx, dt * vy, dt * vz, dest);
    }

    public Quaternionfc nlerp(IQuaternionf q, float factor) {
        return nlerp(q, factor, this);
    }

    public Quaternionfc nlerp(IQuaternionf q, float factor, Quaternionfc dest) {
        float cosom = x * q.x() + y * q.y() + z * q.z() + w * q.w();
        float scale0 = 1.0f - factor;
        float scale1 = (cosom >= 0.0f) ? factor : -factor;
        dest.set(scale0 * x + scale1 * q.x(), scale0 * y + scale1 * q.y(), scale0 * z + scale1 * q.z(), scale0 * w + scale1 * q.w());
        float s = (float) (1.0 / Math.sqrt(dest.x() * dest.x() + dest.y() * dest.y() + dest.z() * dest.z() + dest.w() * dest.w()));
        dest.set(dest.x() * s, dest.y() * s, dest.z() * s, dest.w() * s);
        return dest;
    }

    /**
     * Interpolate between all of the quaternions given in <code>qs</code> via non-spherical linear interpolation using
     * the specified interpolation factors <code>weights</code>, and store the result in <code>dest</code>.
     * <p>
     * This method will interpolate between each two successive quaternions via {@link #nlerp(IQuaternionf, float)}
     * using their relative interpolation weights.
     * <p>
     * Reference: <a href="http://gamedev.stackexchange.com/questions/62354/method-for-interpolation-between-3-quaternions#answer-62356">http://gamedev.stackexchange.com/</a>
     *
     * @param qs      the quaternions to interpolate over
     * @param weights the weights of each individual quaternion in <code>qs</code>
     * @param dest    will hold the result
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

    public Quaternionfc nlerpIterative(IQuaternionf q, float alpha, float dotThreshold, Quaternionfc dest) {
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
        dest.set(resX * s, resY * s, resZ * s, resW * s);
        return dest;
    }

    public Quaternionfc nlerpIterative(IQuaternionf q, float alpha, float dotThreshold) {
        return nlerpIterative(q, alpha, dotThreshold, this);
    }

    /**
     * Interpolate between all of the quaternions given in <code>qs</code> via iterative non-spherical linear
     * interpolation using the specified interpolation factors <code>weights</code>, and store the result in
     * <code>dest</code>.
     * <p>
     * This method will interpolate between each two successive quaternions via {@link #nlerpIterative(IQuaternionf,
     * float, float)} using their relative interpolation weights.
     * <p>
     * Reference: <a href="http://gamedev.stackexchange.com/questions/62354/method-for-interpolation-between-3-quaternions#answer-62356">http://gamedev.stackexchange.com/</a>
     *
     * @param qs           the quaternions to interpolate over
     * @param weights      the weights of each individual quaternion in <code>qs</code>
     * @param dotThreshold the threshold for the dot product of each two interpolated quaternions above which {@link
     *                     #nlerpIterative(IQuaternionf, float, float)} performs another iteration of a small-step
     *                     linear interpolation
     * @param dest         will hold the result
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

    public Quaternionfc lookAlong(IVector3f dir, IVector3f up) {
        return lookAlong(dir.x(), dir.y(), dir.z(), up.x(), up.y(), up.z(), this);
    }

    public Quaternionfc lookAlong(IVector3f dir, IVector3f up, Quaternionfc dest) {
        return lookAlong(dir.x(), dir.y(), dir.z(), up.x(), up.y(), up.z(), dest);
    }

    public Quaternionfc lookAlong(float dirX, float dirY, float dirZ, float upX, float upY, float upZ) {
        return lookAlong(dirX, dirY, dirZ, upX, upY, upZ, this);
    }

    public Quaternionfc lookAlong(float dirX, float dirY, float dirZ, float upX, float upY, float upZ, Quaternionfc dest) {
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

    public Quaternionfc rotationTo(float fromDirX, float fromDirY, float fromDirZ, float toDirX, float toDirY, float toDirZ) {
        x = fromDirY * toDirZ - fromDirZ * toDirY;
        y = fromDirZ * toDirX - fromDirX * toDirZ;
        z = fromDirX * toDirY - fromDirY * toDirX;
        w = (float) Math.sqrt((fromDirX * fromDirX + fromDirY * fromDirY + fromDirZ * fromDirZ) *
                (toDirX * toDirX + toDirY * toDirY + toDirZ * toDirZ)) +
                (fromDirX * toDirX + fromDirY * toDirY + fromDirZ * toDirZ);
        float invNorm = (float) (1.0 / Math.sqrt(x * x + y * y + z * z + w * w));
        if (Float.isInfinite(invNorm)) {
            // Rotation is ambiguous: Find appropriate rotation axis (1. try toDir x +Z)
            x = toDirY;
            y = -toDirX;
            z = 0.0f;
            w = 0.0f;
            invNorm = (float) (1.0 / Math.sqrt(x * x + y * y));
            if (Float.isInfinite(invNorm)) {
                // 2. try toDir x +X
                x = 0.0f;
                y = toDirZ;
                z = -toDirY;
                w = 0.0f;
                invNorm = (float) (1.0 / Math.sqrt(y * y + z * z));
            }
        }
        x *= invNorm;
        y *= invNorm;
        z *= invNorm;
        w *= invNorm;
        return this;
    }

    public Quaternionfc rotationTo(IVector3f fromDir, IVector3f toDir) {
        return rotationTo(fromDir.x(), fromDir.y(), fromDir.z(), toDir.x(), toDir.y(), toDir.z());
    }

    public Quaternionfc rotateTo(float fromDirX, float fromDirY, float fromDirZ, float toDirX, float toDirY, float toDirZ, Quaternionfc dest) {
        float x = fromDirY * toDirZ - fromDirZ * toDirY;
        float y = fromDirZ * toDirX - fromDirX * toDirZ;
        float z = fromDirX * toDirY - fromDirY * toDirX;
        float w = (float) Math.sqrt((fromDirX * fromDirX + fromDirY * fromDirY + fromDirZ * fromDirZ) *
                (toDirX * toDirX + toDirY * toDirY + toDirZ * toDirZ)) +
                (fromDirX * toDirX + fromDirY * toDirY + fromDirZ * toDirZ);
        float invNorm = (float) (1.0 / Math.sqrt(x * x + y * y + z * z + w * w));
        if (Float.isInfinite(invNorm)) {
            // Rotation is ambiguous: Find appropriate rotation axis (1. try toDir x +Z)
            x = toDirY;
            y = -toDirX;
            z = 0.0f;
            w = 0.0f;
            invNorm = (float) (1.0 / Math.sqrt(x * x + y * y));
            if (Float.isInfinite(invNorm)) {
                // 2. try toDir x +X
                x = 0.0f;
                y = toDirZ;
                z = -toDirY;
                w = 0.0f;
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

    public Quaternionfc rotateTo(float fromDirX, float fromDirY, float fromDirZ, float toDirX, float toDirY, float toDirZ) {
        return rotateTo(fromDirX, fromDirY, fromDirZ, toDirX, toDirY, toDirZ, this);
    }

    public Quaternionfc rotateTo(IVector3f fromDir, IVector3f toDir, Quaternionfc dest) {
        return rotateTo(fromDir.x(), fromDir.y(), fromDir.z(), toDir.x(), toDir.y(), toDir.z(), dest);
    }

    public Quaternionfc rotateTo(IVector3f fromDir, IVector3f toDir) {
        return rotateTo(fromDir.x(), fromDir.y(), fromDir.z(), toDir.x(), toDir.y(), toDir.z(), this);
    }

    public Quaternionfc rotate(float angleX, float angleY, float angleZ) {
        return rotate(angleX, angleY, angleZ, this);
    }

    public Quaternionfc rotate(float angleX, float angleY, float angleZ, Quaternionfc dest) {
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
            double sin = Math.sin(thetaMag);
            s = sin / thetaMag;
            dqW = Math.cosFromSin(sin, thetaMag);
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

    public Quaternionfc rotateLocal(float angleX, float angleY, float angleZ) {
        return rotateLocal(angleX, angleY, angleZ, this);
    }

    public Quaternionfc rotateLocal(float angleX, float angleY, float angleZ, Quaternionfc dest) {
        float thetaX = angleX * 0.5f;
        float thetaY = angleY * 0.5f;
        float thetaZ = angleZ * 0.5f;
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

    public Quaternionfc rotateX(float angle) {
        return rotateX(angle, this);
    }

    public Quaternionfc rotateX(float angle, Quaternionfc dest) {
        float sin = (float) Math.sin(angle * 0.5);
        float cos = (float) Math.cosFromSin(sin, angle * 0.5);
        dest.set(w * sin + x * cos,
                y * cos + z * sin,
                z * cos - y * sin,
                w * cos - x * sin);
        return dest;
    }

    public Quaternionfc rotateY(float angle) {
        return rotateY(angle, this);
    }

    public Quaternionfc rotateY(float angle, Quaternionfc dest) {
        float sin = (float) Math.sin(angle * 0.5);
        float cos = (float) Math.cosFromSin(sin, angle * 0.5);
        dest.set(x * cos - z * sin,
                w * sin + y * cos,
                x * sin + z * cos,
                w * cos - y * sin);
        return dest;
    }

    public Quaternionfc rotateZ(float angle) {
        return rotateZ(angle, this);
    }

    public Quaternionfc rotateZ(float angle, Quaternionfc dest) {
        float sin = (float) Math.sin(angle * 0.5);
        float cos = (float) Math.cosFromSin(sin, angle * 0.5);
        dest.set(x * cos + y * sin,
                y * cos - x * sin,
                w * sin + z * cos,
                w * cos - z * sin);
        return dest;
    }

    public Quaternionfc rotateLocalX(float angle) {
        return rotateLocalX(angle, this);
    }

    public Quaternionfc rotateLocalX(float angle, Quaternionfc dest) {
        float hangle = angle * 0.5f;
        float s = (float) Math.sin(hangle);
        float c = (float) Math.cosFromSin(s, hangle);
        dest.set(c * x + s * w,
                c * y - s * z,
                c * z + s * y,
                c * w - s * x);
        return dest;
    }

    public Quaternionfc rotateLocalY(float angle) {
        return rotateLocalY(angle, this);
    }

    public Quaternionfc rotateLocalY(float angle, Quaternionfc dest) {
        float hangle = angle * 0.5f;
        float s = (float) Math.sin(hangle);
        float c = (float) Math.cosFromSin(s, hangle);
        dest.set(c * x + s * z,
                c * y + s * w,
                c * z - s * x,
                c * w - s * y);
        return dest;
    }

    public Quaternionfc rotateLocalZ(float angle) {
        return rotateLocalZ(angle, this);
    }

    public Quaternionfc rotateLocalZ(float angle, Quaternionfc dest) {
        float hangle = angle * 0.5f;
        float s = (float) Math.sin(hangle);
        float c = (float) Math.cosFromSin(s, hangle);
        dest.set(c * x - s * y,
                c * y + s * x,
                c * z + s * w,
                c * w - s * z);
        return dest;
    }

    public Quaternionfc rotateAxis(float angle, float axisX, float axisY, float axisZ, Quaternionfc dest) {
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

    public Quaternionfc rotateAxis(float angle, IVector3f axis, Quaternionfc dest) {
        return rotateAxis(angle, axis.x(), axis.y(), axis.z(), dest);
    }

    public Quaternionfc rotateAxis(float angle, IVector3f axis) {
        return rotateAxis(angle, axis.x(), axis.y(), axis.z(), this);
    }

    public Quaternionfc rotateAxis(float angle, float axisX, float axisY, float axisZ) {
        return rotateAxis(angle, axisX, axisY, axisZ, this);
    }

    public String toString() {
        return Runtime.formatNumbers(toString(Options.NUMBER_FORMAT));
    }

    public String toString(NumberFormat formatter) {
        return "(" + formatter.format(x) + " " + formatter.format(y) + " " + formatter.format(z) + " " + formatter.format(w) + ")";
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeFloat(x);
        out.writeFloat(y);
        out.writeFloat(z);
        out.writeFloat(w);
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
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
        if (!(obj instanceof Quaternionfc))
            return false;
        Quaternionfc other = (Quaternionfc) obj;
        if (Float.floatToIntBits(w) != Float.floatToIntBits(other.w()))
            return false;
        if (Float.floatToIntBits(x) != Float.floatToIntBits(other.x()))
            return false;
        if (Float.floatToIntBits(y) != Float.floatToIntBits(other.y()))
            return false;
        if (Float.floatToIntBits(z) != Float.floatToIntBits(other.z()))
            return false;
        return true;
    }

    public Quaternionfc difference(Quaternionfc other) {
        return difference(other, this);
    }

    public Quaternionfc difference(Quaternionfc other, Quaternionfc dest) {
        float invNorm = 1.0f / (x * x + y * y + z * z + w * w);
        float x = -this.x * invNorm;
        float y = -this.y * invNorm;
        float z = -this.z * invNorm;
        float w = this.w * invNorm;
        dest.set(w * other.x() + x * other.w() + y * other.z() - z * other.y(),
                w * other.y() - x * other.z() + y * other.w() + z * other.x(),
                w * other.z() + x * other.y() - y * other.x() + z * other.w(),
                w * other.w() - x * other.x() - y * other.y() - z * other.z());
        return dest;
    }

    public Vector3fc positiveX(Vector3fc dir) {
        float invNorm = 1.0f / (x * x + y * y + z * z + w * w);
        float nx = -x * invNorm;
        float ny = -y * invNorm;
        float nz = -z * invNorm;
        float nw = w * invNorm;
        float dy = ny + ny;
        float dz = nz + nz;
        dir.set(-ny * dy - nz * dz + 1.0f,
                nx * dy + nw * dz,
                nx * dz - nw * dy);
        return dir;
    }

    public Vector3fc normalizedPositiveX(Vector3fc dir) {
        float dy = y + y;
        float dz = z + z;
        dir.set(-y * dy - z * dz + 1.0f,
                x * dy - w * dz,
                x * dz + w * dy);
        return dir;
    }

    public Vector3fc positiveY(Vector3fc dir) {
        float invNorm = 1.0f / (x * x + y * y + z * z + w * w);
        float nx = -x * invNorm;
        float ny = -y * invNorm;
        float nz = -z * invNorm;
        float nw = w * invNorm;
        float dx = nx + nx;
        float dy = ny + ny;
        float dz = nz + nz;
        dir.set(nx * dy - nw * dz,
                -nx * dx - nz * dz + 1.0f,
                ny * dz + nw * dx);
        return dir;
    }

    public Vector3fc normalizedPositiveY(Vector3fc dir) {
        float dx = x + x;
        float dy = y + y;
        float dz = z + z;
        dir.set(x * dy + w * dz,
                -x * dx - z * dz + 1.0f,
                y * dz - w * dx);
        return dir;
    }

    public Vector3fc positiveZ(Vector3fc dir) {
        float invNorm = 1.0f / (x * x + y * y + z * z + w * w);
        float nx = -x * invNorm;
        float ny = -y * invNorm;
        float nz = -z * invNorm;
        float nw = w * invNorm;
        float dx = nx + nx;
        float dy = ny + ny;
        float dz = nz + nz;
        dir.set(nx * dz + nw * dy,
                ny * dz - nw * dx,
                -nx * dx - ny * dy + 1.0f);
        return dir;
    }

    public Vector3fc normalizedPositiveZ(Vector3fc dir) {
        float dx = x + x;
        float dy = y + y;
        float dz = z + z;
        dir.set(x * dz - w * dy,
                y * dz + w * dx,
                -x * dx - y * dy + 1.0f);
        return dir;
    }
}
