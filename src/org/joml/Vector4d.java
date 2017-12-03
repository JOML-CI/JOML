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

import org.joml.api.matrix.IMatrix4d;
import org.joml.api.matrix.IMatrix4f;
import org.joml.api.matrix.IMatrix4x3d;
import org.joml.api.matrix.IMatrix4x3f;
import org.joml.api.quaternion.IQuaterniond;
import org.joml.api.vector.*;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
//#ifdef __HAS_NIO__
import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
//#endif
import java.text.NumberFormat;

/**
 * Contains the definition of a Vector comprising 4 doubles and associated transformations.
 * 
 * @author Richard Greenlees
 * @author Kai Burjack
 */
public class Vector4d extends Vector4dc implements Externalizable {

    private static final long serialVersionUID = 1L;

    /**
     * The x component of the vector.
     */
    public double x;
    /**
     * The y component of the vector.
     */
    public double y;
    /**
     * The z component of the vector.
     */
    public double z;
    /**
     * The w component of the vector.
     */
    public double w;

    /**
     * Create a new {@link Vector4d} of <code>(0, 0, 0, 1)</code>.
     */
    public Vector4d() {
        this.w = 1.0;
    }

    /**
     * Create a new {@link Vector4d} with the same values as <code>v</code>.
     * 
     * @param v
     *          the {@link IVector4d} to copy the values from
     */
    public Vector4d(IVector4d v) {
        this.x = v.x();
        this.y = v.y();
        this.z = v.z();
        this.w = v.w();
    }

    /**
     * Create a new {@link Vector4d} with the same values as <code>v</code>.
     * 
     * @param v
     *          the {@link IVector4i} to copy the values from
     */
    public Vector4d(IVector4i v) {
        this.x = v.x();
        this.y = v.y();
        this.z = v.z();
        this.w = v.w();
    }

    /**
     * Create a new {@link Vector4d} with the first three components from the
     * given <code>v</code> and the given <code>w</code>.
     * 
     * @param v
     *          the {@link IVector3d}
     * @param w
     *          the w component
     */
    public Vector4d(IVector3d v, double w) {
        this.x = v.x();
        this.y = v.y();
        this.z = v.z();
        this.w = w;
    }

    /**
     * Create a new {@link Vector4d} with the first three components from the
     * given <code>v</code> and the given <code>w</code>.
     * 
     * @param v
     *          the {@link IVector3i}
     * @param w
     *          the w component
     */
    public Vector4d(IVector3i v, double w) {
        this.x = v.x();
        this.y = v.y();
        this.z = v.z();
        this.w = w;
    }

    /**
     * Create a new {@link Vector4d} with the first two components from the
     * given <code>v</code> and the given <code>z</code> and <code>w</code>.
     *
     * @param v
     *          the {@link IVector2d}
     * @param z
     *          the z component
     * @param w
     *          the w component
     */
    public Vector4d(IVector2d v, double z, double w) {
        this.x = v.x();
        this.y = v.y();
        this.z = z;
        this.w = w;
    }

    /**
     * Create a new {@link Vector4d} with the first two components from the
     * given <code>v</code> and the given <code>z</code> and <code>w</code>.
     *
     * @param v
     *          the {@link IVector2i}
     * @param z
     *          the z component
     * @param w
     *          the w component
     */
    public Vector4d(IVector2i v, double z, double w) {
        this.x = v.x();
        this.y = v.y();
        this.z = z;
        this.w = w;
    }

    /**
     * Create a new {@link Vector4d} and initialize all four components with the given value.
     *
     * @param d
     *          the value of all four components
     */
    public Vector4d(double d) {
        this(d, d, d, d); 
    }

    /**
     * Create a new {@link Vector4d} with the same values as <code>v</code>.
     * 
     * @param v
     *          the {@link IVector4f} to copy the values from
     */
    public Vector4d(IVector4f v) {
        this.x = v.x();
        this.y = v.y();
        this.z = v.z();
        this.w = v.w();
    }

    /**
     * Create a new {@link Vector4d} with the x, y, and z components from the
     * given <code>v</code> and the w component from the given <code>w</code>.
     * 
     * @param v
     *          the {@link IVector3f}
     * @param w
     *          the w component
     */
    public Vector4d(IVector3f v, double w) {
        this.x = v.x();
        this.y = v.y();
        this.z = v.z();
        this.w = w;
    }

    /**
     * Create a new {@link Vector4d} with the x and y components from the
     * given <code>v</code> and the z and w components from the given <code>z</code> and <code>w</code>.
     *
     * @param v
     *          the {@link IVector2f}
     * @param z
     *          the z component
     * @param w
     *          the w component
     */
    public Vector4d(IVector2f v, double z, double w) {
        this.x = v.x();
        this.y = v.y();
        this.z = z;
        this.w = w;
    }

    /**
     * Create a new {@link Vector4d} with the given component values.
     * 
     * @param x    
     *          the x component
     * @param y
     *          the y component
     * @param z
     *          the z component
     * @param w
     *          the w component
     */
    public Vector4d(double x, double y, double z, double w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

//#ifdef __HAS_NIO__
    /**
     * Create a new {@link Vector4d} and read this vector from the supplied {@link ByteBuffer}
     * at the current buffer {@link ByteBuffer#position() position}.
     * <p>
     * This method will not increment the position of the given ByteBuffer.
     * <p>
     * In order to specify the offset into the ByteBuffer at which
     * the vector is read, use {@link #Vector4d(int, ByteBuffer)}, taking
     * the absolute position as parameter.
     *
     * @param buffer
     *          values will be read in <tt>x, y, z, w</tt> order
     * @see #Vector4d(int, ByteBuffer)
     */
    public Vector4d(ByteBuffer buffer) {
        this(buffer.position(), buffer);
    }

    /**
     * Create a new {@link Vector4d} and read this vector from the supplied {@link ByteBuffer}
     * starting at the specified absolute buffer position/index.
     * <p>
     * This method will not increment the position of the given ByteBuffer.
     *
     * @param index  the absolute position into the ByteBuffer
     * @param buffer values will be read in <tt>x, y, z, w</tt> order
     */
    public Vector4d(int index, ByteBuffer buffer) {
        MemUtil.INSTANCE.get(this, index, buffer);
    }

    /**
     * Create a new {@link Vector4d} and read this vector from the supplied {@link DoubleBuffer}
     * at the current buffer {@link DoubleBuffer#position() position}.
     * <p>
     * This method will not increment the position of the given DoubleBuffer.
     * <p>
     * In order to specify the offset into the DoubleBuffer at which
     * the vector is read, use {@link #Vector4d(int, DoubleBuffer)}, taking
     * the absolute position as parameter.
     *
     * @param buffer values will be read in <tt>x, y, z, w</tt> order
     * @see #Vector4d(int, DoubleBuffer)
     */
    public Vector4d(DoubleBuffer buffer) {
        this(buffer.position(), buffer);
    }

    /**
     * Create a new {@link Vector4d} and read this vector from the supplied {@link DoubleBuffer}
     * starting at the specified absolute buffer position/index.
     * <p>
     * This method will not increment the position of the given DoubleBuffer.
     *
     * @param index  the absolute position into the DoubleBuffer
     * @param buffer values will be read in <tt>x, y, z, w</tt> order
     */
    public Vector4d(int index, DoubleBuffer buffer) {
        MemUtil.INSTANCE.get(this, index, buffer);
    }
//#endif

    @Override
    public double x() {
        return this.x;
    }

    @Override
    public double y() {
        return this.y;
    }

    @Override
    public double z() {
        return this.z;
    }

    @Override
    public double w() {
        return this.w;
    }

    @Override
    public Vector4dc set(IVector4d v) {
        this.x = v.x();
        this.y = v.y();
        this.z = v.z();
        this.w = v.w();
        return this;
    }

    @Override
    public Vector4dc set(IVector4f v) {
        this.x = v.x();
        this.y = v.y();
        this.z = v.z();
        this.w = v.w();
        return this;
    }

    @Override
    public Vector4dc set(IVector4i v) {
        this.x = v.x();
        this.y = v.y();
        this.z = v.z();
        this.w = v.w();
        return this;
    }

    @Override
    public Vector4dc set(IVector3d v, double w) {
        this.x = v.x();
        this.y = v.y();
        this.z = v.z();
        this.w = w;
        return this;
    }

    @Override
    public Vector4dc set(IVector3i v, double w) {
        this.x = v.x();
        this.y = v.y();
        this.z = v.z();
        this.w = w;
        return this;
    }

    @Override
    public Vector4dc set(IVector3f v, double w) {
        this.x = v.x();
        this.y = v.y();
        this.z = v.z();
        this.w = w;
        return this;
    }

    @Override
    public Vector4dc set(IVector2d v, double z, double w) {
        this.x = v.x();
        this.y = v.y();
        this.z = z;
        this.w = w;
        return this;
    }

    @Override
    public Vector4dc set(IVector2i v, double z, double w) {
        this.x = v.x();
        this.y = v.y();
        this.z = z;
        this.w = w;
        return this;
    }

    @Override
    public Vector4dc set(double d) {
        return set(d, d, d, d);
    }

    @Override
    public Vector4dc set(IVector2f v, double z, double w) {
        this.x = v.x();
        this.y = v.y();
        this.z = z;
        this.w = w;
        return this;
    }

    @Override
    public Vector4dc set(double x, double y, double z, double w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
        return this;
    }

//#ifdef __HAS_NIO__
    @Override
    public Vector4dc set(ByteBuffer buffer) {
        return set(buffer.position(), buffer);
    }

    @Override
    public Vector4dc set(int index, ByteBuffer buffer) {
        MemUtil.INSTANCE.get(this, index, buffer);
        return this;
    }

    @Override
    public Vector4dc set(DoubleBuffer buffer) {
        return set(buffer.position(), buffer);
    }

    @Override
    public Vector4dc set(int index, DoubleBuffer buffer) {
        MemUtil.INSTANCE.get(this, index, buffer);
        return this;
    }
//#endif

    @Override
    public Vector4dc setComponent(int component, double value) throws IllegalArgumentException {
        switch (component) {
            case 0:
                x = value;
                break;
            case 1:
                y = value;
                break;
            case 2:
                z = value;
                break;
            case 3:
                w = value;
                break;
            default:
                throw new IllegalArgumentException();
        }
        return this;
    }

//#ifdef __HAS_NIO__
    @Override
    public ByteBuffer get(ByteBuffer buffer) {
        return get(buffer.position(), buffer);
    }

    @Override
    public ByteBuffer get(int index, ByteBuffer buffer) {
        MemUtil.INSTANCE.put(this, index, buffer);
        return buffer;
    }

    @Override
    public DoubleBuffer get(DoubleBuffer buffer) {
        return get(buffer.position(), buffer);
    }

    @Override
    public DoubleBuffer get(int index, DoubleBuffer buffer) {
        MemUtil.INSTANCE.put(this, index, buffer);
        return buffer;
    }
//#endif

    @Override
    public Vector4dc sub(IVector4d v) {
        x -= v.x();
        y -= v.y();
        z -= v.z();
        w -= v.w();
        return this;
    }

    @Override
    public Vector4dc sub(IVector4f v) {
        x -= v.x();
        y -= v.y();
        z -= v.z();
        w -= v.w();
        return this;
    }

    @Override
    public Vector4dc sub(double x, double y, double z, double w) {
        this.x -= x;
        this.y -= y;
        this.z -= z;
        this.w -= w;
        return this;
    }

    @Override
    public Vector4dc sub(double x, double y, double z, double w, Vector4dc dest) {
        dest.set(this.x - x, this.y - y, this.z - z, this.w - w);
        return dest;
    }

    @Override
    public Vector4dc add(IVector4d v) {
        x += v.x();
        y += v.y();
        z += v.z();
        w += v.w();
        return this;
    }

    @Override
    public Vector4dc add(double x, double y, double z, double w) {
        this.x += x;
        this.y += y;
        this.z += z;
        this.w += w;
        return this;
    }

    @Override
    public Vector4dc add(double x, double y, double z, double w, Vector4dc dest) {
        dest.set(this.x - x, this.y - y, this.z - z, this.w - w);
        return dest;
    }

    @Override
    public Vector4dc add(IVector4f v) {
        x += v.x();
        y += v.y();
        z += v.z();
        w += v.w();
        return this;
    }

    @Override
    public Vector4dc fma(IVector4d a, IVector4d b) {
        x += a.x() * b.x();
        y += a.y() * b.y();
        z += a.z() * b.z();
        w += a.w() * b.w();
        return this;
    }

    @Override
    public Vector4dc fma(double a, IVector4d b) {
        x += a * b.x();
        y += a * b.y();
        z += a * b.z();
        w += a * b.w();
        return this;
    }

    @Override
    public Vector4dc fma(IVector4d a, IVector4d b, Vector4dc dest) {
        dest.set(x + a.x() * b.x(), y + a.y() * b.y(), z + a.z() * b.z(), w + a.w() * b.w());
        return dest;
    }

    @Override
    public Vector4dc fma(double a, IVector4d b, Vector4dc dest) {
        dest.set(x + a * b.x(), y + a * b.y(), z + a * b.z(), w + a * b.w());
        return dest;
    }

    @Override
    public Vector4dc mul(IVector4d v) {
        x *= v.x();
        y *= v.y();
        z *= v.z();
        z *= v.w();
        return this;
    }

    @Override
    public Vector4dc mul(IVector4d v, Vector4dc dest) {
        dest.set(x * v.x(), y * v.y(), z * v.z(), w * v.w());
        return dest;
    }

    @Override
    public Vector4dc div(IVector4d v) {
        x /= v.x();
        y /= v.y();
        z /= v.z();
        z /= v.w();
        return this;
    }

    @Override
    public Vector4dc div(IVector4d v, Vector4dc dest) {
        dest.set(x / v.x(), y / v.y(), z / v.z(), w / v.w());
        return dest;
    }

    @Override
    public Vector4dc mul(IVector4f v) {
        x *= v.x();
        y *= v.y();
        z *= v.z();
        z *= v.w();
        return this;
    }

    @Override
    public Vector4dc mul(IMatrix4d mat) {
        return mul(mat, this);
    }

    @Override
    public Vector4dc mul(IMatrix4d mat, Vector4dc dest) {
        dest.set(mat.m00() * x + mat.m10() * y + mat.m20() * z + mat.m30() * w,
                 mat.m01() * x + mat.m11() * y + mat.m21() * z + mat.m31() * w,
                 mat.m02() * x + mat.m12() * y + mat.m22() * z + mat.m32() * w, 
                 mat.m03() * x + mat.m13() * y + mat.m23() * z + mat.m33() * w);
        return dest;
    }

    @Override
    public Vector4dc mul(IMatrix4x3d mat) {
        return mul(mat, this);
    }

    @Override
    public Vector4dc mul(IMatrix4x3d mat, Vector4dc dest) {
        dest.set(mat.m00() * x + mat.m10() * y + mat.m20() * z + mat.m30() * w,
                 mat.m01() * x + mat.m11() * y + mat.m21() * z + mat.m31() * w,
                 mat.m02() * x + mat.m12() * y + mat.m22() * z + mat.m32() * w,
                 w);
        return dest;
    }

    @Override
    public Vector4dc mul(IMatrix4x3f mat) {
        return mul(mat, this);
    }

    @Override
    public Vector4dc mul(IMatrix4x3f mat, Vector4dc dest) {
        dest.set(mat.m00() * x + mat.m10() * y + mat.m20() * z + mat.m30() * w,
                 mat.m01() * x + mat.m11() * y + mat.m21() * z + mat.m31() * w,
                 mat.m02() * x + mat.m12() * y + mat.m22() * z + mat.m32() * w,
                 w);
        return dest;
    }

    @Override
    public Vector4dc mul(IMatrix4f mat) {
        return mul(mat, this);
    }

    @Override
    public Vector4dc mul(IMatrix4f mat, Vector4dc dest) {
        dest.set(mat.m00() * x + mat.m10() * y + mat.m20() * z + mat.m30() * w,
                 mat.m01() * x + mat.m11() * y + mat.m21() * z + mat.m31() * w,
                 mat.m02() * x + mat.m12() * y + mat.m22() * z + mat.m32() * w, 
                 mat.m03() * x + mat.m13() * y + mat.m23() * z + mat.m33() * w);
        return dest;
    }

    @Override
    public Vector4dc mulProject(IMatrix4d mat, Vector4dc dest) {
        double invW = 1.0 / (mat.m03() * x + mat.m13() * y + mat.m23() * z + mat.m33() * w);
        dest.set((mat.m00() * x + mat.m10() * y + mat.m20() * z + mat.m30() * w) * invW,
                 (mat.m01() * x + mat.m11() * y + mat.m21() * z + mat.m31() * w) * invW,
                 (mat.m02() * x + mat.m12() * y + mat.m22() * z + mat.m32() * w) * invW,
                 1.0);
        return dest;
    }

    @Override
    public Vector4dc mulProject(IMatrix4d mat) {
        return mulProject(mat, this);
    }

    @Override
    public Vector4dc mul(double scalar) {
        x *= scalar;
        y *= scalar;
        z *= scalar;
        w *= scalar;
        return this;
    }

    @Override
    public Vector4dc mul(double scalar, Vector4dc dest) {
        dest.set(x * scalar, y * scalar, z * scalar, w * scalar);
        return dest;
    }

    @Override
    public Vector4dc div(double scalar) {
        double inv = 1.0 / scalar;
        x *= inv;
        y *= inv;
        z *= inv;
        w *= inv;
        return this;
    }

    @Override
    public Vector4dc div(double scalar, Vector4dc dest) {
        double inv = 1.0 / scalar;
        dest.set(x * inv, y * inv, z * inv, w * inv);
        return dest;
    }

    @Override
    public Vector4dc rotate(IQuaterniond quat) {
        return rotate(quat, this);
    }

    @Override
    public Vector4dc rotate(IQuaterniond quat, Vector4dc dest) {
        quat.transform(this, dest);
        return dest;
    }

    @Override
    public Vector4dc rotateAxis(double angle, double x, double y, double z) {
        return rotateAxis(angle, x, y, z, this);
    }

    @Override
    public Vector4dc rotateAxis(double angle, double aX, double aY, double aZ, Vector4dc dest) {
        double hangle = angle * 0.5;
        double sinAngle = Math.sin(hangle);
        double qx = aX * sinAngle, qy = aY * sinAngle, qz = aZ * sinAngle;
        double qw = Math.cosFromSin(sinAngle, hangle);
        double w2 = qw * qw, x2 = qx * qx, y2 = qy * qy, z2 = qz * qz, zw = qz * qw;
        double xy = qx * qy, xz = qx * qz, yw = qy * qw, yz = qy * qz, xw = qx * qw;
        double nx = (w2 + x2 - z2 - y2) * x + (-zw + xy - zw + xy) * y + (yw + xz + xz + yw) * z;
        double ny = (xy + zw + zw + xy) * x + ( y2 - z2 + w2 - x2) * y + (yz + yz - xw - xw) * z;
        double nz = (xz - yw + xz - yw) * x + ( yz + yz + xw + xw) * y + (z2 - y2 - x2 + w2) * z;
        dest.set(nx, ny, nz, dest.w());
        return dest;
    }

    @Override
    public Vector4dc rotateX(double angle) {
        return rotateX(angle, this);
    }

    @Override
    public Vector4dc rotateX(double angle, Vector4dc dest) {
        double sin = Math.sin(angle), cos = Math.cosFromSin(sin, angle);
        double y = this.y * cos - this.z * sin;
        double z = this.y * sin + this.z * cos;
        dest.set(this.x, y, z, this.w);
        return dest;
    }

    @Override
    public Vector4dc rotateY(double angle) {
        return rotateY(angle, this);
    }

    @Override
    public Vector4dc rotateY(double angle, Vector4dc dest) {
        double sin = Math.sin(angle), cos = Math.cosFromSin(sin, angle);
        double x =  this.x * cos + this.z * sin;
        double z = -this.x * sin + this.z * cos;
        dest.set(x, this.y, z, this.w);
        return dest;
    }

    @Override
    public Vector4dc rotateZ(double angle) {
        return rotateZ(angle, this);
    }

    @Override
    public Vector4dc rotateZ(double angle, Vector4dc dest) {
        double sin = Math.sin(angle), cos = Math.cosFromSin(sin, angle);
        double x = this.x * cos - this.y * sin;
        double y = this.x * sin + this.y * cos;
        dest.set(x, y, this.z, this.w);
        return dest;
    }

    @Override
    public double lengthSquared() {
        return x * x + y * y + z * z + w * w;
    }

    @Override
    public double length() {
        return Math.sqrt(lengthSquared());
    }

    @Override
    public Vector4dc normalize() {
        double invLength = 1.0 / length();
        x *= invLength;
        y *= invLength;
        z *= invLength;
        w *= invLength;
        return this;
    }

    @Override
    public Vector4dc normalize(Vector4dc dest) {
        double invLength = 1.0 / length();
        dest.set(x * invLength, y * invLength, z * invLength, w * invLength);
        return dest;
    }

    @Override
    public Vector4dc normalize(double length) {
        double invLength = 1.0 / length() * length;
        x *= invLength;
        y *= invLength;
        z *= invLength;
        w *= invLength;
        return this;
    }

    @Override
    public Vector4dc normalize(double length, Vector4dc dest) {
        double invLength = 1.0 / length() * length;
        dest.set(x * invLength, y * invLength, z * invLength, w * invLength);
        return dest;
    }

    @Override
    public Vector4dc normalize3() {
        double invLength = 1.0 / Math.sqrt(x * x + y * y + z * z);
        x *= invLength;
        y *= invLength;
        z *= invLength;
        w *= invLength;
        return this;
    }

    @Override
    public Vector4dc normalize3(Vector4dc dest) {
        double invLength = 1.0 / Math.sqrt(x * x + y * y + z * z);
        dest.set(x * invLength, y * invLength, z * invLength, w * invLength);
        return dest;
    }

    @Override
    public double distance(IVector4d v) {
        double dx = v.x() - x;
        double dy = v.y() - y;
        double dz = v.z() - z;
        double dw = v.w() - w;
        return Math.sqrt(dx * dx + dy * dy + dz * dz + dw * dw);
    }

    @Override
    public double distance(double x, double y, double z, double w) {
        double dx = this.x - x;
        double dy = this.y - y;
        double dz = this.z - z;
        double dw = this.w - w;
        return Math.sqrt(dx * dx + dy * dy + dz * dz + dw * dw);
    }

    @Override
    public double dot(IVector4d v) {
        return x * v.x() + y * v.y() + z * v.z() + w * v.w();
    }

    @Override
    public double dot(double x, double y, double z, double w) {
        return this.x * x + this.y * y + this.z * z + this.w * w;
    }

    @Override
    public double angleCos(IVector4d v) {
        double length1Sqared = x * x + y * y + z * z + w * w;
        double length2Sqared = v.x() * v.x() + v.y() * v.y() + v.z() * v.z() + v.w() * v.w();
        double dot = x * v.x() + y * v.y() + z * v.z() + w * v.w();
        return dot / (Math.sqrt(length1Sqared * length2Sqared));
    }

    @Override
    public double angle(IVector4d v) {
        double cos = angleCos(v);
        // This is because sometimes cos goes above 1 or below -1 because of lost precision
        cos = cos < 1 ? cos : 1;
        cos = cos > -1 ? cos : -1;
        return Math.acos(cos);
    }

    @Override
    public Vector4dc zero() {
        x = 0.0;
        y = 0.0;
        z = 0.0;
        w = 0.0;
        return this;
    }

    @Override
    public Vector4dc negate() {
        x = -x;
        y = -y;
        z = -z;
        w = -w;
        return this;
    }

    @Override
    public Vector4dc negate(Vector4dc dest) {
        dest.set(-x, -y, -z, -w);
        return dest;
    }

    @Override
    public String toString() {
        return Runtime.formatNumbers(toString(Options.NUMBER_FORMAT));
    }

    @Override
    public String toString(NumberFormat formatter) {
        return "(" + formatter.format(x) + " " + formatter.format(y) + " " + formatter.format(z) + " " + formatter.format(w) + ")";
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeDouble(x);
        out.writeDouble(y);
        out.writeDouble(z);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        x = in.readDouble();
        y = in.readDouble();
        z = in.readDouble();
    }

    @Override
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Vector4dc))
            return false;
        Vector4dc other = (Vector4dc) obj;
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

    @Override
    public Vector4dc smoothStep(IVector4d v, double t, Vector4dc dest) {
        double t2 = t * t;
        double t3 = t2 * t;
        dest.set((x + x - v.x() - v.x()) * t3 + (3.0 * v.x() - 3.0 * x) * t2 + x * t + x,
                (y + y - v.y() - v.y()) * t3 + (3.0 * v.y() - 3.0 * y) * t2 + y * t + y,
                (z + z - v.z() - v.z()) * t3 + (3.0 * v.z() - 3.0 * z) * t2 + z * t + z,
                (w + w - v.w() - v.w()) * t3 + (3.0 * v.w() - 3.0 * w) * t2 + w * t + w);
        return dest;
    }

    @Override
    public Vector4dc hermite(IVector4d t0, IVector4d v1, IVector4d t1, double t, Vector4dc dest) {
        double t2 = t * t;
        double t3 = t2 * t;
        dest.set((x + x - v1.x() - v1.x() + t1.x() + t0.x()) * t3 + (3.0 * v1.x() - 3.0 * x - t0.x() - t0.x() - t1.x()) * t2 + x * t + x,
                (y + y - v1.y() - v1.y() + t1.y() + t0.y()) * t3 + (3.0 * v1.y() - 3.0 * y - t0.y() - t0.y() - t1.y()) * t2 + y * t + y,
                (z + z - v1.z() - v1.z() + t1.z() + t0.z()) * t3 + (3.0 * v1.z() - 3.0 * z - t0.z() - t0.z() - t1.z()) * t2 + z * t + z,
                (w + w - v1.w() - v1.w() + t1.w() + t0.w()) * t3 + (3.0 * v1.w() - 3.0 * w - t0.w() - t0.w() - t1.w()) * t2 + w * t + w);
        return dest;
    }

    @Override
    public Vector4dc lerp(IVector4d other, double t) {
        return lerp(other, t, this);
    }

    @Override
    public Vector4dc lerp(IVector4d other, double t, Vector4dc dest) {
        dest.set(x + (other.x() - x) * t,
                y + (other.y() - y) * t,
                z + (other.z() - z) * t,
                w + (other.w() - w) * t);
        return dest;
    }

    @Override
    public double get(int component) throws IllegalArgumentException {
        switch (component) {
        case 0:
            return x;
        case 1:
            return y;
        case 2:
            return z;
        case 3:
            return w;
        default:
            throw new IllegalArgumentException();
        }
    }

    @Override
    public Vector4dc set(int component, double value) throws IllegalArgumentException {
        switch (component) {
        case 0:
            this.x = value;
            return this;
        case 1:
            this.y = value;
            return this;
        case 2:
            this.z = value;
            return this;
        case 3:
            this.w = value;
            return this;
        default:
            throw new IllegalArgumentException();
        }
    }
}
