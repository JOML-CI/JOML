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
import org.joml.api.quaternion.IQuaterniond;
import org.joml.api.quaternion.IQuaternionf;
import org.joml.api.quaternion.Quaterniondc;
import org.joml.api.vector.IVector3d;
import org.joml.api.vector.IVector4d;
import org.joml.api.vector.Vector3dc;
import org.joml.api.vector.Vector4dc;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * * Quaternion of 4 double-precision floats which can represent rotation and uniform scaling.
 *
 * @author Richard Greenlees
 * @author Kai Burjack
 */
public class Quaterniond extends Quaterniondc implements Externalizable {

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
     * Create a new {@link Quaterniond} and initialize it with <tt>(x=0, y=0, z=0, w=1)</tt>, where <tt>(x, y, z)</tt>
     * is the vector part of the quaternion and <tt>w</tt> is the real/scalar part.
     */
    public Quaterniond() {
        this.w = 1.0;
    }

    /**
     * Create a new {@link Quaterniond} and initialize its components to the given values.
     *
     * @param x the first component of the imaginary part
     * @param y the second component of the imaginary part
     * @param z the third component of the imaginary part
     * @param w the real part
     */
    public Quaterniond(double x, double y, double z, double w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    /**
     * Create a new {@link Quaterniond} and initialize its imaginary components to the given values, and its real part
     * to <tt>1.0</tt>.
     *
     * @param x the first component of the imaginary part
     * @param y the second component of the imaginary part
     * @param z the third component of the imaginary part
     */
    public Quaterniond(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = 1.0;
    }

    /**
     * Create a new {@link Quaterniond} and initialize its components to the same values as the given {@link
     * Quaterniond}.
     *
     * @param source the {@link Quaterniond} to take the component values from
     */
    public Quaterniond(IQuaterniond source) {
        x = source.x();
        y = source.y();
        z = source.z();
        w = source.w();
    }

    /**
     * Create a new {@link Quaterniond} and initialize its components to the same values as the given {@link
     * IQuaternionf}.
     *
     * @param source the {@link IQuaternionf} to take the component values from
     */
    public Quaterniond(IQuaternionf source) {
        x = source.x();
        y = source.y();
        z = source.z();
        w = source.w();
    }

    /**
     * Create a new {@link Quaterniond} and initialize it to represent the same rotation as the given {@link
     * AxisAngle4fc}.
     *
     * @param axisAngle the axis-angle to initialize this quaternion with
     */
    public Quaterniond(AxisAngle4fc axisAngle) {
        double s = Math.sin(axisAngle.angle() * 0.5);
        x = axisAngle.x() * s;
        y = axisAngle.y() * s;
        z = axisAngle.z() * s;
        w = Math.cosFromSin(s, axisAngle.angle() * 0.5);
    }

    /**
     * Create a new {@link Quaterniond} and initialize it to represent the same rotation as the given {@link
     * AxisAngle4dc}.
     *
     * @param axisAngle the axis-angle to initialize this quaternion with
     */
    public Quaterniond(AxisAngle4dc axisAngle) {
        double s = Math.sin(axisAngle.angle() * 0.5);
        x = axisAngle.x() * s;
        y = axisAngle.y() * s;
        z = axisAngle.z() * s;
        w = Math.cosFromSin(s, axisAngle.angle() * 0.5);
    }

    public double x() {
        return this.x;
    }

    public double y() {
        return this.y;
    }

    public double z() {
        return this.z;
    }

    public double w() {
        return this.w;
    }

    public Quaterniondc normalize() {
        double invNorm = 1.0 / Math.sqrt(x * x + y * y + z * z + w * w);
        x *= invNorm;
        y *= invNorm;
        z *= invNorm;
        w *= invNorm;
        return this;
    }

    public Quaterniondc normalize(Quaterniondc dest) {
        double invNorm = 1.0 / Math.sqrt(x * x + y * y + z * z + w * w);
        dest.set(x * invNorm, y * invNorm, z * invNorm, w * invNorm);
        return dest;
    }

    public Quaterniondc add(double x, double y, double z, double w) {
        return add(x, y, z, w, this);
    }

    public Quaterniondc add(double x, double y, double z, double w, Quaterniondc dest) {
        dest.set(this.x + x, this.y + y, this.z + z, this.w + w);
        return dest;
    }

    public Quaterniondc add(IQuaterniond q2) {
        x += q2.x();
        y += q2.y();
        z += q2.z();
        w += q2.w();
        return this;
    }

    public Quaterniondc add(IQuaterniond q2, Quaterniondc dest) {
        dest.set(x + q2.x(), y + q2.y(), z + q2.z(), w + q2.w());
        return dest;
    }

    public double dot(IQuaterniond otherQuat) {
        return this.x * otherQuat.x() + this.y * otherQuat.y() + this.z * otherQuat.z() + this.w * otherQuat.w();
    }

    public double angle() {
        double angle = 2.0 * Math.acos(w);
        return angle <= Math.PI ? angle : Math.PI + Math.PI - angle;
    }

    public Matrix3dc get(Matrix3dc dest) {
        return dest.set(this);
    }

    public Matrix3fc get(Matrix3fc dest) {
        return dest.set(this);
    }

    public Matrix4dc get(Matrix4dc dest) {
        return dest.set(this);
    }

    public Matrix4fc get(Matrix4fc dest) {
        return dest.set(this);
    }

    public Quaterniondc get(Quaterniondc dest) {
        return dest.set(this);
    }

    public Quaterniondc set(double x, double y, double z, double w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
        return this;
    }

    public Quaterniondc set(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }

    public Quaterniondc set(IQuaterniond q) {
        x = q.x();
        y = q.y();
        z = q.z();
        w = q.w();
        return this;
    }

    public Quaterniondc set(IQuaternionf q) {
        x = q.x();
        y = q.y();
        z = q.z();
        w = q.w();
        return this;
    }

    public Quaterniondc set(AxisAngle4fc axisAngle) {
        return setAngleAxis(axisAngle.angle(), axisAngle.x(), axisAngle.y(), axisAngle.z());
    }

    public Quaterniondc set(AxisAngle4dc axisAngle) {
        return setAngleAxis(axisAngle.angle(), axisAngle.x(), axisAngle.y(), axisAngle.z());
    }

    public Quaterniondc setAngleAxis(double angle, double x, double y, double z) {
        double s = Math.sin(angle * 0.5);
        this.x = x * s;
        this.y = y * s;
        this.z = z * s;
        this.w = Math.cosFromSin(s, angle * 0.5);
        return this;
    }

    public Quaterniondc setAngleAxis(double angle, IVector3d axis) {
        return setAngleAxis(angle, axis.x(), axis.y(), axis.z());
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

    public Quaterniondc setFromUnnormalized(IMatrix4f mat) {
        setFromUnnormalized(mat.m00(), mat.m01(), mat.m02(), mat.m10(), mat.m11(), mat.m12(), mat.m20(), mat.m21(), mat.m22());
        return this;
    }

    public Quaterniondc setFromUnnormalized(IMatrix4x3f mat) {
        setFromUnnormalized(mat.m00(), mat.m01(), mat.m02(), mat.m10(), mat.m11(), mat.m12(), mat.m20(), mat.m21(), mat.m22());
        return this;
    }

    public Quaterniondc setFromUnnormalized(IMatrix4x3d mat) {
        setFromUnnormalized(mat.m00(), mat.m01(), mat.m02(), mat.m10(), mat.m11(), mat.m12(), mat.m20(), mat.m21(), mat.m22());
        return this;
    }

    public Quaterniondc setFromNormalized(IMatrix4f mat) {
        setFromNormalized(mat.m00(), mat.m01(), mat.m02(), mat.m10(), mat.m11(), mat.m12(), mat.m20(), mat.m21(), mat.m22());
        return this;
    }

    public Quaterniondc setFromNormalized(IMatrix4x3f mat) {
        setFromNormalized(mat.m00(), mat.m01(), mat.m02(), mat.m10(), mat.m11(), mat.m12(), mat.m20(), mat.m21(), mat.m22());
        return this;
    }

    public Quaterniondc setFromNormalized(IMatrix4x3d mat) {
        setFromNormalized(mat.m00(), mat.m01(), mat.m02(), mat.m10(), mat.m11(), mat.m12(), mat.m20(), mat.m21(), mat.m22());
        return this;
    }

    public Quaterniondc setFromUnnormalized(IMatrix4d mat) {
        setFromUnnormalized(mat.m00(), mat.m01(), mat.m02(), mat.m10(), mat.m11(), mat.m12(), mat.m20(), mat.m21(), mat.m22());
        return this;
    }

    public Quaterniondc setFromNormalized(IMatrix4d mat) {
        setFromNormalized(mat.m00(), mat.m01(), mat.m02(), mat.m10(), mat.m11(), mat.m12(), mat.m20(), mat.m21(), mat.m22());
        return this;
    }

    public Quaterniondc setFromUnnormalized(IMatrix3f mat) {
        setFromUnnormalized(mat.m00(), mat.m01(), mat.m02(), mat.m10(), mat.m11(), mat.m12(), mat.m20(), mat.m21(), mat.m22());
        return this;
    }

    public Quaterniondc setFromNormalized(IMatrix3f mat) {
        setFromNormalized(mat.m00(), mat.m01(), mat.m02(), mat.m10(), mat.m11(), mat.m12(), mat.m20(), mat.m21(), mat.m22());
        return this;
    }

    public Quaterniondc setFromUnnormalized(IMatrix3d mat) {
        setFromUnnormalized(mat.m00(), mat.m01(), mat.m02(), mat.m10(), mat.m11(), mat.m12(), mat.m20(), mat.m21(), mat.m22());
        return this;
    }

    public Quaterniondc setFromNormalized(IMatrix3d mat) {
        setFromNormalized(mat.m00(), mat.m01(), mat.m02(), mat.m10(), mat.m11(), mat.m12(), mat.m20(), mat.m21(), mat.m22());
        return this;
    }

    public Quaterniondc fromAxisAngleRad(IVector3d axis, double angle) {
        return fromAxisAngleRad(axis.x(), axis.y(), axis.z(), angle);
    }

    public Quaterniondc fromAxisAngleRad(double axisX, double axisY, double axisZ, double angle) {
        double hangle = angle / 2.0;
        double sinAngle = Math.sin(hangle);
        double vLength = Math.sqrt(axisX * axisX + axisY * axisY + axisZ * axisZ);
        x = axisX / vLength * sinAngle;
        y = axisY / vLength * sinAngle;
        z = axisZ / vLength * sinAngle;
        w = Math.cosFromSin(sinAngle, hangle);
        return this;
    }

    public Quaterniondc fromAxisAngleDeg(IVector3d axis, double angle) {
        return fromAxisAngleRad(axis.x(), axis.y(), axis.z(), Math.toRadians(angle));
    }

    public Quaterniondc fromAxisAngleDeg(double axisX, double axisY, double axisZ, double angle) {
        return fromAxisAngleRad(axisX, axisY, axisZ, Math.toRadians(angle));
    }

    public Quaterniondc mul(IQuaterniond q) {
        return mul(q, this);
    }

    public Quaterniondc mul(IQuaterniond q, Quaterniondc dest) {
        dest.set(w * q.x() + x * q.w() + y * q.z() - z * q.y(),
                w * q.y() - x * q.z() + y * q.w() + z * q.x(),
                w * q.z() + x * q.y() - y * q.x() + z * q.w(),
                w * q.w() - x * q.x() - y * q.y() - z * q.z());
        return dest;
    }

    public Quaterniondc mul(double qx, double qy, double qz, double qw) {
        set(w * qx + x * qw + y * qz - z * qy,
                w * qy - x * qz + y * qw + z * qx,
                w * qz + x * qy - y * qx + z * qw,
                w * qw - x * qx - y * qy - z * qz);
        return this;
    }

    public Quaterniondc mul(double qx, double qy, double qz, double qw, Quaterniondc dest) {
        dest.set(w * qx + x * qw + y * qz - z * qy,
                w * qy - x * qz + y * qw + z * qx,
                w * qz + x * qy - y * qx + z * qw,
                w * qw - x * qx - y * qy - z * qz);
        return dest;
    }

    public Quaterniondc premul(IQuaterniond q) {
        return premul(q, this);
    }

    public Quaterniondc premul(IQuaterniond q, Quaterniondc dest) {
        dest.set(q.w() * x + q.x() * w + q.y() * z - q.z() * y,
                q.w() * y - q.x() * z + q.y() * w + q.z() * x,
                q.w() * z + q.x() * y - q.y() * x + q.z() * w,
                q.w() * w - q.x() * x - q.y() * y - q.z() * z);
        return dest;
    }

    public Quaterniondc premul(double qx, double qy, double qz, double qw) {
        return premul(qx, qy, qz, qw, this);
    }

    public Quaterniondc premul(double qx, double qy, double qz, double qw, Quaterniondc dest) {
        dest.set(qw * x + qx * w + qy * z - qz * y,
                qw * y - qx * z + qy * w + qz * x,
                qw * z + qx * y - qy * x + qz * w,
                qw * w - qx * x - qy * y - qz * z);
        return dest;
    }

    public Vector3dc transform(Vector3dc vec) {
        return transform(vec.x(), vec.y(), vec.z(), vec);
    }

    public Vector4dc transform(Vector4dc vec) {
        return transform(vec, vec);
    }

    public Vector3dc transform(IVector3d vec, Vector3dc dest) {
        return transform(vec.x(), vec.y(), vec.z(), dest);
    }

    public Vector3dc transform(double x, double y, double z, Vector3dc dest) {
        double w2 = this.w * this.w;
        double x2 = this.x * this.x;
        double y2 = this.y * this.y;
        double z2 = this.z * this.z;
        double zw = this.z * this.w;
        double xy = this.x * this.y;
        double xz = this.x * this.z;
        double yw = this.y * this.w;
        double yz = this.y * this.z;
        double xw = this.x * this.w;
        double m00 = w2 + x2 - z2 - y2;
        double m01 = xy + zw + zw + xy;
        double m02 = xz - yw + xz - yw;
        double m10 = -zw + xy - zw + xy;
        double m11 = y2 - z2 + w2 - x2;
        double m12 = yz + yz + xw + xw;
        double m20 = yw + xz + xz + yw;
        double m21 = yz + yz - xw - xw;
        double m22 = z2 - y2 - x2 + w2;
        dest.set(m00 * x + m10 * y + m20 * z,
                m01 * x + m11 * y + m21 * z,
                m02 * x + m12 * y + m22 * z);
        return dest;
    }

    public Vector4dc transform(IVector4d vec, Vector4dc dest) {
        return transform(vec.x(), vec.y(), vec.z(), dest);
    }

    public Vector4dc transform(double x, double y, double z, Vector4dc dest) {
        double w2 = this.w * this.w;
        double x2 = this.x * this.x;
        double y2 = this.y * this.y;
        double z2 = this.z * this.z;
        double zw = this.z * this.w;
        double xy = this.x * this.y;
        double xz = this.x * this.z;
        double yw = this.y * this.w;
        double yz = this.y * this.z;
        double xw = this.x * this.w;
        double m00 = w2 + x2 - z2 - y2;
        double m01 = xy + zw + zw + xy;
        double m02 = xz - yw + xz - yw;
        double m10 = -zw + xy - zw + xy;
        double m11 = y2 - z2 + w2 - x2;
        double m12 = yz + yz + xw + xw;
        double m20 = yw + xz + xz + yw;
        double m21 = yz + yz - xw - xw;
        double m22 = z2 - y2 - x2 + w2;
        dest.set(m00 * x + m10 * y + m20 * z,
                m01 * x + m11 * y + m21 * z,
                m02 * x + m12 * y + m22 * z,
                dest.w());
        return dest;
    }

    public Quaterniondc invert(Quaterniondc dest) {
        double invNorm = 1.0 / (x * x + y * y + z * z + w * w);
        dest.set(-x * invNorm, -y * invNorm, -z * invNorm, w * invNorm);
        return dest;
    }

    public Quaterniondc invert() {
        return invert(this);
    }

    public Quaterniondc div(IQuaterniond b, Quaterniondc dest) {
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

    public Quaterniondc div(IQuaterniond b) {
        return div(b, this);
    }

    public Quaterniondc conjugate() {
        x = -x;
        y = -y;
        z = -z;
        return this;
    }

    public Quaterniondc conjugate(Quaterniondc dest) {
        dest.set(-x, -y, -z, w);
        return dest;
    }

    public Quaterniondc identity() {
        x = 0.0;
        y = 0.0;
        z = 0.0;
        w = 1.0;
        return this;
    }

    public double lengthSquared() {
        return x * x + y * y + z * z + w * w;
    }

    public Quaterniondc rotationXYZ(double angleX, double angleY, double angleZ) {
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
        w = cx * cycz - sx * sysz;
        x = sx * cycz + cx * sysz;
        y = cx * sycz - sx * cysz;
        z = cx * cysz + sx * sycz;

        return this;
    }

    public Quaterniondc rotationZYX(double angleZ, double angleY, double angleX) {
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
        w = cx * cycz + sx * sysz;
        x = sx * cycz - cx * sysz;
        y = cx * sycz + sx * cysz;
        z = cx * cysz - sx * sycz;

        return this;
    }

    public Quaterniondc rotationYXZ(double angleY, double angleX, double angleZ) {
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

    public Quaterniondc slerp(IQuaterniond target, double alpha) {
        return slerp(target, alpha, this);
    }

    public Quaterniondc slerp(IQuaterniond target, double alpha, Quaterniondc dest) {
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
        dest.set(scale0 * x + scale1 * target.x(), scale0 * y + scale1 * target.y(), scale0 * z + scale1 * target.z(), scale0 * w + scale1 * target.w());
        return dest;
    }

    /**
     * Interpolate between all of the quaternions given in <code>qs</code> via spherical linear interpolation using the
     * specified interpolation factors <code>weights</code>, and store the result in <code>dest</code>.
     * <p>
     * This method will interpolate between each two successive quaternions via {@link #slerp(IQuaterniond, double)}
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

    public Quaterniondc scale(double factor) {
        return scale(factor, this);
    }

    public Quaterniondc scale(double factor, Quaterniondc dest) {
        double sqrt = Math.sqrt(factor);
        dest.set(sqrt * x, sqrt * y, sqrt * z, sqrt * w);
        return this;
    }

    public Quaterniondc scaling(float factor) {
        double sqrt = Math.sqrt(factor);
        this.x = 0.0;
        this.y = 0.0;
        this.z = 0.0;
        this.w = sqrt;
        return this;
    }

    public Quaterniondc integrate(double dt, double vx, double vy, double vz) {
        return integrate(dt, vx, vy, vz, this);
    }

    public Quaterniondc integrate(double dt, double vx, double vy, double vz, Quaterniondc dest) {
        return rotateLocal(dt * vx, dt * vy, dt * vz, dest);
    }

    public Quaterniondc nlerp(IQuaterniond q, double factor) {
        return nlerp(q, factor, this);
    }

    public Quaterniondc nlerp(IQuaterniond q, double factor, Quaterniondc dest) {
        double cosom = x * q.x() + y * q.y() + z * q.z() + w * q.w();
        double scale0 = 1.0 - factor;
        double scale1 = (cosom >= 0.0) ? factor : -factor;
        dest.set(scale0 * x + scale1 * q.x(), scale0 * y + scale1 * q.y(), scale0 * z + scale1 * q.z(), scale0 * w + scale1 * q.w());
        double s = 1.0 / Math.sqrt(dest.x() * dest.x() + dest.y() * dest.y() + dest.z() * dest.z() + dest.w() * dest.w());
        dest.mul(s, s, s, s);
        return dest;
    }

    /**
     * Interpolate between all of the quaternions given in <code>qs</code> via non-spherical linear interpolation using
     * the specified interpolation factors <code>weights</code>, and store the result in <code>dest</code>.
     * <p>
     * This method will interpolate between each two successive quaternions via {@link #nlerp(IQuaterniond, double)}
     * using their relative interpolation weights.
     * <p>
     * Reference: <a href="http://gamedev.stackexchange.com/questions/62354/method-for-interpolation-between-3-quaternions#answer-62356">http://gamedev.stackexchange.com/</a>
     *
     * @param qs      the quaternions to interpolate over
     * @param weights the weights of each individual quaternion in <code>qs</code>
     * @param dest    will hold the result
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

    public Quaterniondc nlerpIterative(IQuaterniond q, double alpha, double dotThreshold, Quaterniondc dest) {
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
        dest.mul(s, s, s, s);
        return dest;
    }

    public Quaterniondc nlerpIterative(IQuaterniond q, double alpha, double dotThreshold) {
        return nlerpIterative(q, alpha, dotThreshold, this);
    }

    /**
     * Interpolate between all of the quaternions given in <code>qs</code> via iterative non-spherical linear
     * interpolation using the specified interpolation factors <code>weights</code>, and store the result in
     * <code>dest</code>.
     * <p>
     * This method will interpolate between each two successive quaternions via {@link #nlerpIterative(IQuaterniond,
     * double, double)} using their relative interpolation weights.
     * <p>
     * Reference: <a href="http://gamedev.stackexchange.com/questions/62354/method-for-interpolation-between-3-quaternions#answer-62356">http://gamedev.stackexchange.com/</a>
     *
     * @param qs           the quaternions to interpolate over
     * @param weights      the weights of each individual quaternion in <code>qs</code>
     * @param dotThreshold the threshold for the dot product of each two interpolated quaternions above which {@link
     *                     #nlerpIterative(IQuaterniond, double, double)} performs another iteration of a small-step
     *                     linear interpolation
     * @param dest         will hold the result
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

    public Quaterniondc lookAlong(IVector3d dir, IVector3d up) {
        return lookAlong(dir.x(), dir.y(), dir.z(), up.x(), up.y(), up.z(), this);
    }

    public Quaterniondc lookAlong(IVector3d dir, IVector3d up, Quaterniondc dest) {
        return lookAlong(dir.x(), dir.y(), dir.z(), up.x(), up.y(), up.z(), dest);
    }

    public Quaterniondc lookAlong(double dirX, double dirY, double dirZ, double upX, double upY, double upZ) {
        return lookAlong(dirX, dirY, dirZ, upX, upY, upZ, this);
    }

    public Quaterniondc lookAlong(double dirX, double dirY, double dirZ, double upX, double upY, double upZ, Quaterniondc dest) {
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
        dest.set(this.w * x + this.x * w + this.y * z - this.z * y,
                this.w * y - this.x * z + this.y * w + this.z * x,
                this.w * z + this.x * y - this.y * x + this.z * w,
                this.w * w - this.x * x - this.y * y - this.z * z);
        return dest;
    }

    public String toString() {
        return Runtime.formatNumbers(toString(Options.NUMBER_FORMAT));
    }

    public String toString(NumberFormat formatter) {
        return "(" + formatter.format(x) + " " + formatter.format(y) + " " + formatter.format(z) + " " + formatter.format(w) + ")";
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeDouble(x);
        out.writeDouble(y);
        out.writeDouble(z);
        out.writeDouble(w);
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
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
        if (!(obj instanceof Quaterniondc))
            return false;
        Quaterniondc other = (Quaterniondc) obj;
        if (Double.doubleToLongBits(w) != Double.doubleToLongBits(other.w()))
            return false;
        if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x()))
            return false;
        if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y()))
            return false;
        if (Double.doubleToLongBits(z) != Double.doubleToLongBits(other.z()))
            return false;
        return true;
    }

    public Quaterniondc difference(IQuaterniond other) {
        return difference(other, this);
    }

    public Quaterniondc difference(IQuaterniond other, Quaterniondc dest) {
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

    public Quaterniondc rotationTo(double fromDirX, double fromDirY, double fromDirZ, double toDirX, double toDirY, double toDirZ) {
        x = fromDirY * toDirZ - fromDirZ * toDirY;
        y = fromDirZ * toDirX - fromDirX * toDirZ;
        z = fromDirX * toDirY - fromDirY * toDirX;
        w = Math.sqrt((fromDirX * fromDirX + fromDirY * fromDirY + fromDirZ * fromDirZ) *
                (toDirX * toDirX + toDirY * toDirY + toDirZ * toDirZ)) +
                (fromDirX * toDirX + fromDirY * toDirY + fromDirZ * toDirZ);
        double invNorm = 1.0 / Math.sqrt(x * x + y * y + z * z + w * w);
        if (Double.isInfinite(invNorm)) {
            // Rotation is ambiguous: Find appropriate rotation axis (1. try toDir x +Z)
            x = toDirY;
            y = -toDirX;
            z = 0.0;
            w = 0.0;
            invNorm = (float) (1.0 / Math.sqrt(x * x + y * y));
            if (Double.isInfinite(invNorm)) {
                // 2. try toDir x +X
                x = 0.0;
                y = toDirZ;
                z = -toDirY;
                w = 0.0;
                invNorm = (float) (1.0 / Math.sqrt(y * y + z * z));
            }
        }
        x *= invNorm;
        y *= invNorm;
        z *= invNorm;
        w *= invNorm;
        return this;
    }

    public Quaterniondc rotationTo(IVector3d fromDir, IVector3d toDir) {
        return rotationTo(fromDir.x(), fromDir.y(), fromDir.z(), toDir.x(), toDir.y(), toDir.z());
    }

    public Quaterniondc rotateTo(double fromDirX, double fromDirY, double fromDirZ,
                                 double toDirX, double toDirY, double toDirZ, Quaterniondc dest) {
        double x = fromDirY * toDirZ - fromDirZ * toDirY;
        double y = fromDirZ * toDirX - fromDirX * toDirZ;
        double z = fromDirX * toDirY - fromDirY * toDirX;
        double w = Math.sqrt((fromDirX * fromDirX + fromDirY * fromDirY + fromDirZ * fromDirZ) *
                (toDirX * toDirX + toDirY * toDirY + toDirZ * toDirZ)) +
                (fromDirX * toDirX + fromDirY * toDirY + fromDirZ * toDirZ);
        double invNorm = 1.0 / Math.sqrt(x * x + y * y + z * z + w * w);
        if (Double.isInfinite(invNorm)) {
            // Rotation is ambiguous: Find appropriate rotation axis (1. try toDir x +Z)
            x = toDirY;
            y = -toDirX;
            z = 0.0;
            w = 0.0;
            invNorm = (float) (1.0 / Math.sqrt(x * x + y * y));
            if (Double.isInfinite(invNorm)) {
                // 2. try toDir x +X
                x = 0.0;
                y = toDirZ;
                z = -toDirY;
                w = 0.0;
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

    public Quaterniondc rotationAxis(AxisAngle4fc axisAngle) {
        return rotationAxis(axisAngle.angle(), axisAngle.x(), axisAngle.y(), axisAngle.z());
    }

    public Quaterniondc rotationAxis(double angle, double axisX, double axisY, double axisZ) {
        double hangle = angle / 2.0;
        double sinAngle = Math.sin(hangle);
        double invVLength = 1.0 / Math.sqrt(axisX * axisX + axisY * axisY + axisZ * axisZ);

        x = axisX * invVLength * sinAngle;
        y = axisY * invVLength * sinAngle;
        z = axisZ * invVLength * sinAngle;
        w = (float) Math.cosFromSin(sinAngle, hangle);

        return this;
    }

    public Quaterniondc rotation(double angleX, double angleY, double angleZ) {
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
            double sin = Math.sin(thetaMag);
            s = sin / thetaMag;
            w = Math.cosFromSin(sin, thetaMag);
        }
        x = thetaX * s;
        y = thetaY * s;
        z = thetaZ * s;
        return this;
    }

    public Quaterniondc rotationX(double angle) {
        double sin = Math.sin(angle * 0.5);
        double cos = Math.cosFromSin(sin, angle * 0.5);
        w = cos;
        x = sin;
        y = 0.0;
        z = 0.0;
        return this;
    }

    public Quaterniondc rotationY(double angle) {
        double sin = Math.sin(angle * 0.5);
        double cos = Math.cosFromSin(sin, angle * 0.5);
        w = cos;
        x = 0.0;
        y = sin;
        z = 0.0;
        return this;
    }

    public Quaterniondc rotationZ(double angle) {
        double sin = Math.sin(angle * 0.5);
        double cos = Math.cosFromSin(sin, angle * 0.5);
        w = cos;
        x = 0.0;
        y = 0.0;
        z = sin;
        return this;
    }

    public Quaterniondc rotateTo(double fromDirX, double fromDirY, double fromDirZ, double toDirX, double toDirY, double toDirZ) {
        return rotateTo(fromDirX, fromDirY, fromDirZ, toDirX, toDirY, toDirZ, this);
    }

    public Quaterniondc rotateTo(IVector3d fromDir, IVector3d toDir, Quaterniondc dest) {
        return rotateTo(fromDir.x(), fromDir.y(), fromDir.z(), toDir.x(), toDir.y(), toDir.z(), dest);
    }

    public Quaterniondc rotateTo(IVector3d fromDir, IVector3d toDir) {
        return rotateTo(fromDir.x(), fromDir.y(), fromDir.z(), toDir.x(), toDir.y(), toDir.z(), this);
    }

    public Quaterniondc rotate(IVector3d anglesXYZ, Quaterniondc dest) {
        return rotate(anglesXYZ.x(), anglesXYZ.y(), anglesXYZ.z(), dest);
    }

    public Quaterniondc rotate(IVector3d anglesXYZ) {
        return rotate(anglesXYZ.x(), anglesXYZ.y(), anglesXYZ.z(), this);
    }

    public Quaterniondc rotate(double angleX, double angleY, double angleZ) {
        return rotate(angleX, angleY, angleZ, this);
    }

    public Quaterniondc rotate(double angleX, double angleY, double angleZ, Quaterniondc dest) {
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
            double sin = Math.sin(thetaMag);
            s = sin / thetaMag;
            dqW = Math.cosFromSin(sin, thetaMag);
        }
        dqX = thetaX * s;
        dqY = thetaY * s;
        dqZ = thetaZ * s;
        /* Post-multiplication (like matrices multiply) */
        dest.set(w * dqX + x * dqW + y * dqZ - z * dqY,
                w * dqY - x * dqZ + y * dqW + z * dqX,
                w * dqZ + x * dqY - y * dqX + z * dqW,
                w * dqW - x * dqX - y * dqY - z * dqZ);
        return dest;
    }

    public Quaterniondc rotateLocal(double angleX, double angleY, double angleZ) {
        return rotateLocal(angleX, angleY, angleZ, this);
    }

    public Quaterniondc rotateLocal(double angleX, double angleY, double angleZ, Quaterniondc dest) {
        double thetaX = angleX * 0.5;
        double thetaY = angleY * 0.5;
        double thetaZ = angleZ * 0.5;
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

    public Quaterniondc rotateX(double angle) {
        return rotateX(angle, this);
    }

    public Quaterniondc rotateX(double angle, Quaterniondc dest) {
        double sin = Math.sin(angle * 0.5);
        double cos = Math.cosFromSin(sin, angle * 0.5);
        dest.set(w * sin + x * cos,
                y * cos + z * sin,
                z * cos - y * sin,
                w * cos - x * sin);
        return dest;
    }

    public Quaterniondc rotateY(double angle) {
        return rotateY(angle, this);
    }

    public Quaterniondc rotateY(double angle, Quaterniondc dest) {
        double sin = Math.sin(angle * 0.5);
        double cos = Math.cosFromSin(sin, angle * 0.5);
        dest.set(x * cos - z * sin,
                w * sin + y * cos,
                x * sin + z * cos,
                w * cos - y * sin);
        return dest;
    }

    public Quaterniondc rotateZ(double angle) {
        return rotateZ(angle, this);
    }

    public Quaterniondc rotateZ(double angle, Quaterniondc dest) {
        double sin = Math.sin(angle * 0.5);
        double cos = Math.cosFromSin(sin, angle * 0.5);
        dest.set(x * cos + y * sin,
                y * cos - x * sin,
                w * sin + z * cos,
                w * cos - z * sin);
        return dest;
    }

    public Quaterniondc rotateLocalX(double angle) {
        return rotateLocalX(angle, this);
    }

    public Quaterniondc rotateLocalX(double angle, Quaterniondc dest) {
        double hangle = angle * 0.5;
        double s = Math.sin(hangle);
        double c = Math.cosFromSin(s, hangle);
        dest.set(c * x + s * w,
                c * y - s * z,
                c * z + s * y,
                c * w - s * x);
        return dest;
    }

    public Quaterniondc rotateLocalY(double angle) {
        return rotateLocalY(angle, this);
    }

    public Quaterniondc rotateLocalY(double angle, Quaterniondc dest) {
        double hangle = angle * 0.5;
        double s = Math.sin(hangle);
        double c = Math.cosFromSin(s, hangle);
        dest.set(c * x + s * z,
                c * y + s * w,
                c * z - s * x,
                c * w - s * y);
        return dest;
    }

    public Quaterniondc rotateLocalZ(double angle) {
        return rotateLocalZ(angle, this);
    }

    public Quaterniondc rotateLocalZ(double angle, Quaterniondc dest) {
        double hangle = angle * 0.5;
        double s = Math.sin(hangle);
        double c = Math.cosFromSin(s, hangle);
        dest.set(c * x - s * y,
                c * y + s * x,
                c * z + s * w,
                c * w - s * z);
        return dest;
    }

    public Quaterniondc rotateXYZ(double angleX, double angleY, double angleZ) {
        return rotateXYZ(angleX, angleY, angleZ, this);
    }

    public Quaterniondc rotateXYZ(double angleX, double angleY, double angleZ, Quaterniondc dest) {
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
        double w = cx * cycz - sx * sysz;
        double x = sx * cycz + cx * sysz;
        double y = cx * sycz - sx * cysz;
        double z = cx * cysz + sx * sycz;
        // right-multiply
        dest.set(this.w * x + this.x * w + this.y * z - this.z * y,
                this.w * y - this.x * z + this.y * w + this.z * x,
                this.w * z + this.x * y - this.y * x + this.z * w,
                this.w * w - this.x * x - this.y * y - this.z * z);
        return dest;
    }

    public Quaterniondc rotateZYX(double angleZ, double angleY, double angleX) {
        return rotateZYX(angleZ, angleY, angleX, this);
    }

    public Quaterniondc rotateZYX(double angleZ, double angleY, double angleX, Quaterniondc dest) {
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
        double w = cx * cycz + sx * sysz;
        double x = sx * cycz - cx * sysz;
        double y = cx * sycz + sx * cysz;
        double z = cx * cysz - sx * sycz;
        // right-multiply
        dest.set(this.w * x + this.x * w + this.y * z - this.z * y,
                this.w * y - this.x * z + this.y * w + this.z * x,
                this.w * z + this.x * y - this.y * x + this.z * w,
                this.w * w - this.x * x - this.y * y - this.z * z);
        return dest;
    }

    public Quaterniondc rotateYXZ(double angleZ, double angleY, double angleX) {
        return rotateYXZ(angleZ, angleY, angleX, this);
    }

    public Quaterniondc rotateYXZ(double angleY, double angleX, double angleZ, Quaterniondc dest) {
        double sx = Math.sin(angleX * 0.5);
        double cx = Math.cosFromSin(sx, angleX * 0.5);
        double sy = Math.sin(angleY * 0.5);
        double cy = Math.cosFromSin(sy, angleY * 0.5);
        double sz = Math.sin(angleZ * 0.5);
        double cz = Math.cosFromSin(sz, angleZ * 0.5);

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

    public Vector3dc getEulerAnglesXYZ(Vector3dc eulerAngles) {
        eulerAngles.set(Math.atan2(2.0 * (x * w - y * z), 1.0 - 2.0 * (x * x + y * y)),
                Math.asin(2.0 * (x * z + y * w)),
                Math.atan2(2.0 * (z * w - x * y), 1.0 - 2.0 * (y * y + z * z)));
        return eulerAngles;
    }

    public Quaterniondc rotateAxis(double angle, double axisX, double axisY, double axisZ, Quaterniondc dest) {
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

    public Quaterniondc rotateAxis(double angle, IVector3d axis, Quaterniondc dest) {
        return rotateAxis(angle, axis.x(), axis.y(), axis.z(), dest);
    }

    public Quaterniondc rotateAxis(double angle, IVector3d axis) {
        return rotateAxis(angle, axis.x(), axis.y(), axis.z(), this);
    }

    public Quaterniondc rotateAxis(double angle, double axisX, double axisY, double axisZ) {
        return rotateAxis(angle, axisX, axisY, axisZ, this);
    }

    public Vector3dc positiveX(Vector3dc dir) {
        double invNorm = 1.0f / (x * x + y * y + z * z + w * w);
        double nx = -x * invNorm;
        double ny = -y * invNorm;
        double nz = -z * invNorm;
        double nw = w * invNorm;
        double dy = ny + ny;
        double dz = nz + nz;
        dir.set(-ny * dy - nz * dz + 1.0,
                nx * dy + nw * dz,
                nx * dz - nw * dy);
        return dir;
    }

    public Vector3dc normalizedPositiveX(Vector3dc dir) {
        double dy = y + y;
        double dz = z + z;
        dir.set(-y * dy - z * dz + 1.0,
                x * dy - w * dz,
                x * dz + w * dy);
        return dir;
    }

    public Vector3dc positiveY(Vector3dc dir) {
        double invNorm = 1.0f / (x * x + y * y + z * z + w * w);
        double nx = -x * invNorm;
        double ny = -y * invNorm;
        double nz = -z * invNorm;
        double nw = w * invNorm;
        double dx = nx + nx;
        double dy = ny + ny;
        double dz = nz + nz;
        dir.set(nx * dy - nw * dz,
                -nx * dx - nz * dz + 1.0,
                ny * dz + nw * dx);
        return dir;
    }

    public Vector3dc normalizedPositiveY(Vector3dc dir) {
        double dx = x + x;
        double dy = y + y;
        double dz = z + z;
        dir.set(x * dy + w * dz,
                -x * dx - z * dz + 1.0,
                y * dz - w * dx);
        return dir;
    }

    public Vector3dc positiveZ(Vector3dc dir) {
        double invNorm = 1.0f / (x * x + y * y + z * z + w * w);
        double nx = -x * invNorm;
        double ny = -y * invNorm;
        double nz = -z * invNorm;
        double nw = w * invNorm;
        double dx = nx + nx;
        double dy = ny + ny;
        double dz = nz + nz;
        dir.set(nx * dz + nw * dy,
                ny * dz - nw * dx,
                -nx * dx - ny * dy + 1.0);
        return dir;
    }

    public Vector3dc normalizedPositiveZ(Vector3dc dir) {
        double dx = x + x;
        double dy = y + y;
        double dz = z + z;
        dir.set(x * dz - w * dy,
                y * dz + w * dx,
                -x * dx - y * dy + 1.0);
        return dir;
    }
}
