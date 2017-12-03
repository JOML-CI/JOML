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

import org.joml.api.matrix.*;
import org.joml.api.quaternion.IQuaternionf;
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
 * Contains the definition of a Vector comprising 3 floats and associated
 * transformations.
 *
 * @author Richard Greenlees
 * @author Kai Burjack
 */
public class Vector3f extends Vector3fc implements Externalizable {

    private static final long serialVersionUID = 1L;

    /**
     * The x component of the vector.
     */
    public float x;
    /**
     * The y component of the vector.
     */
    public float y;
    /**
     * The z component of the vector.
     */
    public float z;

    /**
     * Create a new {@link Vector3f} of <tt>(0, 0, 0)</tt>.
     */
    public Vector3f() {
    }

    /**
     * Create a new {@link Vector3f} and initialize all three components with the given value.
     *
     * @param d
     *          the value of all three components
     */
    public Vector3f(float d) {
        this(d, d, d);
    }

    /**
     * Create a new {@link Vector3f} with the given component values.
     * 
     * @param x
     *          the value of x
     * @param y
     *          the value of y
     * @param z
     *          the value of z
     */
    public Vector3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Create a new {@link Vector3f} with the same values as <code>v</code>.
     * 
     * @param v
     *          the {@link IVector3f} to copy the values from
     */
    public Vector3f(IVector3f v) {
        this.x = v.x();
        this.y = v.y();
        this.z = v.z();
    }

    /**
     * Create a new {@link Vector3f} with the same values as <code>v</code>.
     * 
     * @param v
     *          the {@link IVector3i} to copy the values from
     */
    public Vector3f(IVector3i v) {
        this.x = v.x();
        this.y = v.y();
        this.z = v.z();
    }

    /**
     * Create a new {@link Vector3f} with the first two components from the
     * given <code>v</code> and the given <code>z</code>
     * 
     * @param v
     *          the {@link IVector2f} to copy the values from
     * @param z
     *          the z component
     */
    public Vector3f(IVector2f v, float z) {
        this.x = v.x();
        this.y = v.y();
        this.z = z;
    }

    /**
     * Create a new {@link Vector3f} with the first two components from the
     * given <code>v</code> and the given <code>z</code>
     * 
     * @param v
     *          the {@link IVector2i} to copy the values from
     * @param z
     *          the z component
     */
    public Vector3f(IVector2i v, float z) {
        this.x = v.x();
        this.y = v.y();
        this.z = z;
    }

//#ifdef __HAS_NIO__
    /**
     * Create a new {@link Vector3f} and read this vector from the supplied {@link ByteBuffer}
     * at the current buffer {@link ByteBuffer#position() position}.
     * <p>
     * This method will not increment the position of the given ByteBuffer.
     * <p>
     * In order to specify the offset into the ByteBuffer at which
     * the vector is read, use {@link #Vector3f(int, ByteBuffer)}, taking
     * the absolute position as parameter.
     *
     * @param buffer values will be read in <tt>x, y, z</tt> order
     * @see #Vector3f(int, ByteBuffer)
     */
    public Vector3f(ByteBuffer buffer) {
        this(buffer.position(), buffer);
    }

    /**
     * Create a new {@link Vector3f} and read this vector from the supplied {@link ByteBuffer}
     * starting at the specified absolute buffer position/index.
     * <p>
     * This method will not increment the position of the given ByteBuffer.
     *
     * @param index  the absolute position into the ByteBuffer
     * @param buffer values will be read in <tt>x, y, z</tt> order
     */
    public Vector3f(int index, ByteBuffer buffer) {
        MemUtil.INSTANCE.get(this, index, buffer);
    }

    /**
     * Create a new {@link Vector3f} and read this vector from the supplied {@link FloatBuffer}
     * at the current buffer {@link FloatBuffer#position() position}.
     * <p>
     * This method will not increment the position of the given FloatBuffer.
     * <p>
     * In order to specify the offset into the FloatBuffer at which
     * the vector is read, use {@link #Vector3f(int, FloatBuffer)}, taking
     * the absolute position as parameter.
     *
     * @param buffer values will be read in <tt>x, y, z</tt> order
     * @see #Vector3f(int, FloatBuffer)
     */
    public Vector3f(FloatBuffer buffer) {
        this(buffer.position(), buffer);
    }

    /**
     * Create a new {@link Vector3f} and read this vector from the supplied {@link FloatBuffer}
     * starting at the specified absolute buffer position/index.
     * <p>
     * This method will not increment the position of the given FloatBuffer.
     *
     * @param index  the absolute position into the FloatBuffer
     * @param buffer values will be read in <tt>x, y, z</tt> order
     */
    public Vector3f(int index, FloatBuffer buffer) {
        MemUtil.INSTANCE.get(this, index, buffer);
    }
//#endif

    @Override
    public float x() {
        return this.x;
    }

    @Override
    public float y() {
        return this.y;
    }

    @Override
    public float z() {
        return this.z;
    }

    @Override
    public Vector3fc set(IVector3f v) {
        x = v.x();
        y = v.y();
        z = v.z();
        return this;
    }

    @Override
    public Vector3fc set(IVector3d v) {
        x = (float) v.x();
        y = (float) v.y();
        z = (float) v.z();
        return this;
    }

    @Override
    public Vector3fc set(IVector3i v) {
        x = v.x();
        y = v.y();
        z = v.z();
        return this;
    }

    @Override
    public Vector3fc set(IVector2f v, float z) {
        this.x = v.x();
        this.y = v.y();
        this.z = z;
        return this;
    }

    @Override
    public Vector3fc set(IVector2i v, float z) {
        this.x = v.x();
        this.y = v.y();
        this.z = z;
        return this;
    }

    @Override
    public Vector3fc set(float d) {
        return set(d, d, d);
    }

    @Override
    public Vector3fc set(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }

//#ifdef __HAS_NIO__
    @Override
    public Vector3fc set(ByteBuffer buffer) {
        return set(buffer.position(), buffer);
    }

    @Override
    public Vector3fc set(int index, ByteBuffer buffer) {
        MemUtil.INSTANCE.get(this, index, buffer);
        return this;
    }

    @Override
    public Vector3fc set(FloatBuffer buffer) {
        return set(buffer.position(), buffer);
    }

    @Override
    public Vector3fc set(int index, FloatBuffer buffer) {
        MemUtil.INSTANCE.get(this, index, buffer);
        return this;
    }
//#endif

    @Override
    public Vector3fc setComponent(int component, float value) throws IllegalArgumentException {
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
            default:
                throw new IllegalArgumentException();
        }
        return this;
    }

//#ifdef __HAS_NIO__
    @Override
    public FloatBuffer get(FloatBuffer buffer) {
        return get(buffer.position(), buffer);
    }

    @Override
    public FloatBuffer get(int index, FloatBuffer buffer) {
        MemUtil.INSTANCE.put(this, index, buffer);
        return buffer;
    }

    @Override
    public ByteBuffer get(ByteBuffer buffer) {
        return get(buffer.position(), buffer);
    }

    @Override
    public ByteBuffer get(int index, ByteBuffer buffer) {
        MemUtil.INSTANCE.put(this, index, buffer);
        return buffer;
    }
//#endif

    @Override
    public Vector3fc sub(IVector3f v) {
        x -= v.x();
        y -= v.y();
        z -= v.z();
        return this;
    }

    @Override
    public Vector3fc sub(IVector3f v, Vector3fc dest) {
        dest.set(x - v.x(), y - v.y(), z - v.z());
        return dest;
    }

    @Override
    public Vector3fc sub(float x, float y, float z) {
        this.x -= x;
        this.y -= y;
        this.z -= z;
        return this;
    }

    @Override
    public Vector3fc sub(float x, float y, float z, Vector3fc dest) {
        dest.set(this.x - x, this.y - y, this.z - z);
        return dest;
    }

    @Override
    public Vector3fc add(IVector3f v) {
        x += v.x();
        y += v.y();
        z += v.z();
        return this;
    }

    @Override
    public Vector3fc add(IVector3f v, Vector3fc dest) {
        dest.set(x + v.x(), y + v.y(), z + v.z());
        return dest;
    }

    @Override
    public Vector3fc add(float x, float y, float z) {
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }

    @Override
    public Vector3fc add(float x, float y, float z, Vector3fc dest) {
        dest.set(this.x + x, this.y + y, this.z + z);
        return dest;
    }

    @Override
    public Vector3fc fma(IVector3f a, IVector3f b) {
        x += a.x() * b.x();
        y += a.y() * b.y();
        z += a.z() * b.z();
        return this;
    }

    @Override
    public Vector3fc fma(float a, IVector3f b) {
        x += a * b.x();
        y += a * b.y();
        z += a * b.z();
        return this;
    }

    @Override
    public Vector3fc fma(IVector3f a, IVector3f b, Vector3fc dest) {
        dest.set(x + a.x() * b.x(), y + a.y() * b.y(), z + a.z() * b.z());
        return dest;
    }

    @Override
    public Vector3fc fma(float a, IVector3f b, Vector3fc dest) {
        dest.set(x + a * b.x(), y + a * b.y(), z + a * b.z());
        return dest;
    }

    @Override
    public Vector3fc mul(IVector3f v) {
        x *= v.x();
        y *= v.y();
        z *= v.z();
        return this;
    }

    @Override
    public Vector3fc mul(IVector3f v, Vector3fc dest) {
        dest.set(x * v.x(), y * v.y(), z * v.z());
        return dest;
    }

    @Override
    public Vector3fc div(IVector3f v) {
        x /= v.x();
        y /= v.y();
        z /= v.z();
        return this;
    }

    @Override
    public Vector3fc div(IVector3f v, Vector3fc dest) {
        dest.set(x / v.x(), y / v.y(), z / v.z());
        return dest;
    }

    @Override
    public Vector3fc mulProject(IMatrix4f mat, Vector3fc dest) {
        float invW = 1.0f / (mat.m03() * x + mat.m13() * y + mat.m23() * z + mat.m33());
        dest.set((mat.m00() * x + mat.m10() * y + mat.m20() * z + mat.m30()) * invW,
                 (mat.m01() * x + mat.m11() * y + mat.m21() * z + mat.m31()) * invW,
                 (mat.m02() * x + mat.m12() * y + mat.m22() * z + mat.m32()) * invW);
        return dest;
    }

    @Override
    public Vector3fc mulProject(IMatrix4f mat) {
        return mulProject(mat, this);
    }

    @Override
    public Vector3fc mul(IMatrix3f mat) {
        return mul(mat, this);
    }

    @Override
    public Vector3fc mul(IMatrix3f mat, Vector3fc dest) {
        dest.set(mat.m00() * x + mat.m10() * y + mat.m20() * z,
                 mat.m01() * x + mat.m11() * y + mat.m21() * z,
                 mat.m02() * x + mat.m12() * y + mat.m22() * z);
        return dest;
    }

    @Override
    public Vector3fc mul(IMatrix3d mat) {
        return mul(mat, this);
    }

    @Override
    public Vector3fc mul(IMatrix3d mat, Vector3fc dest) {
        dest.set((float)(mat.m00() * x + mat.m10() * y + mat.m20() * z),
                 (float)(mat.m01() * x + mat.m11() * y + mat.m21() * z),
                 (float)(mat.m02() * x + mat.m12() * y + mat.m22() * z));
        return dest;
    }

    @Override
    public Vector3fc mul(IMatrix3x2f mat) {
        return mul(mat, this);
    }

    @Override
    public Vector3fc mul(IMatrix3x2f mat, Vector3fc dest) {
        dest.set(mat.m00() * x + mat.m10() * y + mat.m20() * z,
                 mat.m01() * x + mat.m11() * y + mat.m21() * z,
                 z);
        return dest;
    }

    @Override
    public Vector3fc mulTranspose(IMatrix3f mat) {
        return mulTranspose(mat, this);
    }

    @Override
    public Vector3fc mulTranspose(IMatrix3f mat, Vector3fc dest) {
        dest.set(mat.m00() * x + mat.m01() * y + mat.m02() * z,
                 mat.m10() * x + mat.m11() * y + mat.m12() * z,
                 mat.m20() * x + mat.m21() * y + mat.m22() * z);
        return dest;
    }

    @Override
    public Vector3fc mulPosition(IMatrix4f mat) {
        return mulPosition(mat, this);
    }

    @Override
    public Vector3fc mulPosition(IMatrix4x3f mat) {
        return mulPosition(mat, this);
    }

    @Override
    public Vector3fc mulPosition(IMatrix4f mat, Vector3fc dest) {
        dest.set(mat.m00() * x + mat.m10() * y + mat.m20() * z + mat.m30(),
                 mat.m01() * x + mat.m11() * y + mat.m21() * z + mat.m31(),
                 mat.m02() * x + mat.m12() * y + mat.m22() * z + mat.m32());
        return dest;
    }

    @Override
    public Vector3fc mulPosition(IMatrix4x3f mat, Vector3fc dest) {
        dest.set(mat.m00() * x + mat.m10() * y + mat.m20() * z + mat.m30(),
                 mat.m01() * x + mat.m11() * y + mat.m21() * z + mat.m31(),
                 mat.m02() * x + mat.m12() * y + mat.m22() * z + mat.m32());
        return dest;
    }

    @Override
    public Vector3fc mulTransposePosition(IMatrix4f mat) {
        return mulTransposePosition(mat, this);
    }

    @Override
    public Vector3fc mulTransposePosition(IMatrix4f mat, Vector3fc dest) {
        dest.set(mat.m00() * x + mat.m01() * y + mat.m02() * z + mat.m03(),
                 mat.m10() * x + mat.m11() * y + mat.m12() * z + mat.m13(),
                 mat.m20() * x + mat.m21() * y + mat.m22() * z + mat.m23());
        return dest;
    }

    @Override
    public float mulPositionW(IMatrix4f mat) {
        return mulPositionW(mat, this);
    }

    @Override
    public float mulPositionW(IMatrix4f mat, Vector3fc dest) {
        float w = mat.m03() * x + mat.m13() * y + mat.m23() * z + mat.m33();
        dest.set(mat.m00() * x + mat.m10() * y + mat.m20() * z + mat.m30(),
                 mat.m01() * x + mat.m11() * y + mat.m21() * z + mat.m31(),
                 mat.m02() * x + mat.m12() * y + mat.m22() * z + mat.m32());
        return w;
    }

    @Override
    public Vector3fc mulDirection(IMatrix4f mat) {
        return mulDirection(mat, this);
    }

    @Override
    public Vector3fc mulDirection(IMatrix4x3f mat) {
        return mulDirection(mat, this);
    }

    @Override
    public Vector3fc mulDirection(IMatrix4f mat, Vector3fc dest) {
        dest.set(mat.m00() * x + mat.m10() * y + mat.m20() * z,
                 mat.m01() * x + mat.m11() * y + mat.m21() * z,
                 mat.m02() * x + mat.m12() * y + mat.m22() * z);
        return dest;
    }

    @Override
    public Vector3fc mulDirection(IMatrix4x3f mat, Vector3fc dest) {
        dest.set(mat.m00() * x + mat.m10() * y + mat.m20() * z,
                 mat.m01() * x + mat.m11() * y + mat.m21() * z,
                 mat.m02() * x + mat.m12() * y + mat.m22() * z);
        return dest;
    }

    @Override
    public Vector3fc mulTransposeDirection(IMatrix4f mat) {
        return mulTransposeDirection(mat, this);
    }

    @Override
    public Vector3fc mulTransposeDirection(IMatrix4f mat, Vector3fc dest) {
        dest.set(mat.m00() * x + mat.m01() * y + mat.m02() * z,
                 mat.m10() * x + mat.m11() * y + mat.m12() * z,
                 mat.m20() * x + mat.m21() * y + mat.m22() * z);
        return dest;
    }

    @Override
    public Vector3fc mul(float scalar) {
        x *= scalar;
        y *= scalar;
        z *= scalar;
        return this;
    }

    @Override
    public Vector3fc mul(float scalar, Vector3fc dest) {
        dest.set(x * scalar, y * scalar, z * scalar);
        return dest;
    }

    @Override
    public Vector3fc mul(float x, float y, float z) {
        this.x *= x;
        this.y *= y;
        this.z *= z;
        return this;
    }

    @Override
    public Vector3fc mul(float x, float y, float z, Vector3fc dest) {
        dest.set(this.x * x, this.y * y, this.z * z);
        return dest;
    }

    @Override
    public Vector3fc div(float scalar) {
        float inv = 1.0f / scalar;
        x *= inv;
        y *= inv;
        z *= inv;
        return this;
    }

    @Override
    public Vector3fc div(float scalar, Vector3fc dest) {
        float inv = 1.0f / scalar;
        dest.set(x * inv, y * inv, z * inv);
        return dest;
    }

    @Override
    public Vector3fc div(float x, float y, float z) {
        this.x /= x;
        this.y /= y;
        this.z /= z;
        return this;
    }

    @Override
    public Vector3fc div(float x, float y, float z, Vector3fc dest) {
        dest.set(this.x / x, this.y / y, this.z / z);
        return dest;
    }

    @Override
    public Vector3fc rotate(IQuaternionf quat) {
        quat.transform(this, this);
        return this;
    }

    @Override
    public Vector3fc rotate(IQuaternionf quat, Vector3fc dest) {
        quat.transform(this, dest);
        return dest;
    }

    @Override
    public Quaternionfc rotationTo(IVector3f toDir, Quaternionfc dest) {
        return dest.rotationTo(this, toDir);
    }

    @Override
    public Quaternionfc rotationTo(float toDirX, float toDirY, float toDirZ, Quaternionfc dest) {
        return dest.rotationTo(x, y, z, toDirX, toDirY, toDirZ);
    }

    @Override
    public Vector3fc rotateAxis(float angle, float x, float y, float z) {
        return rotateAxis(angle, x, y, z, this);
    }

    @Override
    public Vector3fc rotateAxis(float angle, float aX, float aY, float aZ, Vector3fc dest) {
        float hangle = angle * 0.5f;
        float sinAngle = (float) Math.sin(hangle);
        float qx = aX * sinAngle, qy = aY * sinAngle, qz = aZ * sinAngle;
        float qw = (float) Math.cosFromSin(sinAngle, hangle);
        float w2 = qw * qw, x2 = qx * qx, y2 = qy * qy, z2 = qz * qz, zw = qz * qw;
        float xy = qx * qy, xz = qx * qz, yw = qy * qw, yz = qy * qz, xw = qx * qw;
        float nx = (w2 + x2 - z2 - y2) * x + (-zw + xy - zw + xy) * y + (yw + xz + xz + yw) * z;
        float ny = (xy + zw + zw + xy) * x + ( y2 - z2 + w2 - x2) * y + (yz + yz - xw - xw) * z;
        float nz = (xz - yw + xz - yw) * x + ( yz + yz + xw + xw) * y + (z2 - y2 - x2 + w2) * z;
        dest.set(nx, ny, nz);
        return dest;
    }

    @Override
    public Vector3fc rotateX(float angle) {
        return rotateX(angle, this);
    }

    @Override
    public Vector3fc rotateX(float angle, Vector3fc dest) {
        float sin = (float) Math.sin(angle), cos = (float) Math.cosFromSin(sin, angle);
        float y = this.y * cos - this.z * sin;
        float z = this.y * sin + this.z * cos;
        dest.set(this.x, y, z);
        return dest;
    }

    @Override
    public Vector3fc rotateY(float angle) {
        return rotateY(angle, this);
    }

    @Override
    public Vector3fc rotateY(float angle, Vector3fc dest) {
        float sin = (float) Math.sin(angle), cos = (float) Math.cosFromSin(sin, angle);
        float x =  this.x * cos + this.z * sin;
        float z = -this.x * sin + this.z * cos;
        dest.set(x, this.y, z);
        return dest;
    }

    @Override
    public Vector3fc rotateZ(float angle) {
        return rotateZ(angle, this);
    }

    @Override
    public Vector3fc rotateZ(float angle, Vector3fc dest) {
        float sin = (float) Math.sin(angle), cos = (float) Math.cosFromSin(sin, angle);
        float x = this.x * cos - this.y * sin;
        float y = this.x * sin + this.y * cos;
        dest.set(x, y, this.z);
        return dest;
    }

    @Override
    public float lengthSquared() {
        return x * x + y * y + z * z;
    }

    @Override
    public float length() {
        return (float) Math.sqrt(lengthSquared());
    }

    @Override
    public Vector3fc normalize() {
        float invLength = 1.0f / length();
        x *= invLength;
        y *= invLength;
        z *= invLength;
        return this;
    }

    @Override
    public Vector3fc normalize(Vector3fc dest) {
        float invLength = 1.0f / length();
        dest.set(x * invLength, y * invLength, z * invLength);
        return dest;
    }

    @Override
    public Vector3fc normalize(float length) {
        float invLength = 1.0f / length() * length;
        x *= invLength;
        y *= invLength;
        z *= invLength;
        return this;
    }

    @Override
    public Vector3fc normalize(float length, Vector3fc dest) {
        float invLength = 1.0f / length() * length;
        dest.set(x * invLength, y * invLength, z * invLength);
        return dest;
    }

    @Override
    public Vector3fc cross(IVector3f v) {
        return set(y * v.z() - z * v.y(),
                   z * v.x() - x * v.z(),
                   x * v.y() - y * v.x());
    }

    @Override
    public Vector3fc cross(float x, float y, float z) {
        return set(this.y * z - this.z * y,
                   this.z * x - this.x * z,
                   this.x * y - this.y * x);
    }

    @Override
    public Vector3fc cross(IVector3f v, Vector3fc dest) {
        return dest.set(y * v.z() - z * v.y(),
                        z * v.x() - x * v.z(),
                        x * v.y() - y * v.x());
    }

    @Override
    public Vector3fc cross(float x, float y, float z, Vector3fc dest) {
        return dest.set(this.y * z - this.z * y,
                        this.z * x - this.x * z,
                        this.x * y - this.y * x);
    }

    @Override
    public float distance(IVector3f v) {
        float dx = v.x() - x;
        float dy = v.y() - y;
        float dz = v.z() - z;
        return (float) Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    @Override
    public float distance(float x, float y, float z) {
        float dx = this.x - x;
        float dy = this.y - y;
        float dz = this.z - z;
        return (float) Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    @Override
    public float distanceSquared(IVector3f v) {
        float dx = v.x() - x;
        float dy = v.y() - y;
        float dz = v.z() - z;
        return dx * dx + dy * dy + dz * dz;
    }

    @Override
    public float distanceSquared(float x, float y, float z) {
        float dx = this.x - x;
        float dy = this.y - y;
        float dz = this.z - z;
        return dx * dx + dy * dy + dz * dz;
    }

    @Override
    public float dot(IVector3f v) {
        return x * v.x() + y * v.y() + z * v.z();
    }

    @Override
    public float dot(float x, float y, float z) {
        return this.x * x + this.y * y + this.z * z;
    }

    @Override
    public float angleCos(IVector3f v) {
        double length1Sqared = x * x + y * y + z * z;
        double length2Sqared = v.x() * v.x() + v.y() * v.y() + v.z() * v.z();
        double dot = x * v.x() + y * v.y() + z * v.z();
        return (float) (dot / (Math.sqrt(length1Sqared * length2Sqared)));
    }

    @Override
    public float angle(IVector3f v) {
        float cos = angleCos(v);
        // This is because sometimes cos goes above 1 or below -1 because of lost precision
        cos = cos < 1 ? cos : 1;
        cos = cos > -1 ? cos : -1;
        return (float) Math.acos(cos);
    }

    @Override
    public Vector3fc min(IVector3f v) {
        this.x = x < v.x() ? x : v.x();
        this.y = y < v.y() ? y : v.y();
        this.z = z < v.z() ? z : v.z();
        return this;
    }

    @Override
    public Vector3fc max(IVector3f v) {
        this.x = x > v.x() ? x : v.x();
        this.y = y > v.y() ? y : v.y();
        this.z = z > v.z() ? z : v.z();
        return this;
    }

    @Override
    public Vector3fc zero() {
        x = 0.0f;
        y = 0.0f;
        z = 0.0f;
        return this;
    }

    @Override
    public String toString() {
        return Runtime.formatNumbers(toString(Options.NUMBER_FORMAT));
    }

    @Override
    public String toString(NumberFormat formatter) {
        return "(" + formatter.format(x) + " " + formatter.format(y) + " " + formatter.format(z) + ")";
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeFloat(x);
        out.writeFloat(y);
        out.writeFloat(z);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        x = in.readFloat();
        y = in.readFloat();
        z = in.readFloat();
    }

    @Override
    public Vector3fc negate() {
        x = -x;
        y = -y;
        z = -z;
        return this;
    }

    @Override
    public Vector3fc negate(Vector3fc dest) {
        dest.set(-x, -y, -z);
        return dest;
    }

    @Override
    public Vector3fc absolute() {
        this.x = Math.abs(this.x);
        this.y = Math.abs(this.y);
        this.z = Math.abs(this.z);
        return this;
    }

    @Override
    public Vector3fc absolute(Vector3fc dest) {
        dest.set(Math.abs(this.x), Math.abs(this.y), Math.abs(this.z));
        return dest;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Float.floatToIntBits(x);
        result = prime * result + Float.floatToIntBits(y);
        result = prime * result + Float.floatToIntBits(z);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Vector3fc))
            return false;
        Vector3fc other = (Vector3fc) obj;
        if (Float.floatToIntBits(x) != Float.floatToIntBits(other.x()))
            return false;
        if (Float.floatToIntBits(y) != Float.floatToIntBits(other.y()))
            return false;
        if (Float.floatToIntBits(z) != Float.floatToIntBits(other.z()))
            return false;
        return true;
    }

    @Override
    public Vector3fc reflect(IVector3f normal) {
        float dot = this.dot(normal);
        x = x - (dot + dot) * normal.x();
        y = y - (dot + dot) * normal.y();
        z = z - (dot + dot) * normal.z();
        return this;
    }

    @Override
    public Vector3fc reflect(float x, float y, float z) {
        float dot = this.dot(x, y, z);
        this.x = this.x - (dot + dot) * x;
        this.y = this.y - (dot + dot) * y;
        this.z = this.z - (dot + dot) * z;
        return this;
    }

    @Override
    public Vector3fc reflect(IVector3f normal, Vector3fc dest) {
        float dot = this.dot(normal);
        dest.set(x - (dot + dot) * normal.x(), y - (dot + dot) * normal.y(), z - (dot + dot) * normal.z());
        return dest;
    }

    @Override
    public Vector3fc reflect(float x, float y, float z, Vector3fc dest) {
        float dot = this.dot(x, y, z);
        dest.set(this.x - (dot + dot) * x, this.y - (dot + dot) * y, this.z - (dot + dot) * z);
        return dest;
    }

    @Override
    public Vector3fc half(IVector3f other) {
        return this.add(other).normalize();
    }

    @Override
    public Vector3fc half(float x, float y, float z) {
        return this.add(x, y, z).normalize();
    }

    @Override
    public Vector3fc half(IVector3f other, Vector3fc dest) {
        return dest.set(this).add(other).normalize();
    }

    @Override
    public Vector3fc half(float x, float y, float z, Vector3fc dest) {
        return dest.set(this).add(x, y, z).normalize();
    }

    @Override
    public Vector3fc smoothStep(IVector3f v, float t, Vector3fc dest) {
        float t2 = t * t;
        float t3 = t2 * t;
        dest.set((x + x - v.x() - v.x()) * t3 + (3.0f * v.x() - 3.0f * x) * t2 + x * t + x,
                (y + y - v.y() - v.y()) * t3 + (3.0f * v.y() - 3.0f * y) * t2 + y * t + y,
                (z + z - v.z() - v.z()) * t3 + (3.0f * v.z() - 3.0f * z) * t2 + z * t + z);
        return dest;
    }

    @Override
    public Vector3fc hermite(IVector3f t0, IVector3f v1, IVector3f t1, float t, Vector3fc dest) {
        float t2 = t * t;
        float t3 = t2 * t;
        dest.set((x + x - v1.x() - v1.x() + t1.x() + t0.x()) * t3 + (3.0f * v1.x() - 3.0f * x - t0.x() - t0.x() - t1.x()) * t2 + x * t + x,
                (y + y - v1.y() - v1.y() + t1.y() + t0.y()) * t3 + (3.0f * v1.y() - 3.0f * y - t0.y() - t0.y() - t1.y()) * t2 + y * t + y,
                (z + z - v1.z() - v1.z() + t1.z() + t0.z()) * t3 + (3.0f * v1.z() - 3.0f * z - t0.z() - t0.z() - t1.z()) * t2 + z * t + z);
        return dest;
    }

    @Override
    public Vector3fc lerp(IVector3f other, float t) {
        return lerp(other, t, this);
    }

    @Override
    public Vector3fc lerp(IVector3f other, float t, Vector3fc dest) {
        dest.set(x + (other.x() - x) * t, y + (other.y() - y) * t, z + (other.z() - z) * t);
        return dest;
    }

    @Override
    public float get(int component) throws IllegalArgumentException {
        switch (component) {
        case 0:
            return x;
        case 1:
            return y;
        case 2:
            return z;
        default:
            throw new IllegalArgumentException();
        }
    }

    @Override
    public Vector3fc set(int component, float value) throws IllegalArgumentException {
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
        default:
            throw new IllegalArgumentException();
        }
    }

    @Override
    public int maxComponent() {
        float absX = Math.abs(x);
        float absY = Math.abs(y);
        float absZ = Math.abs(z);
        if (absX >= absY && absX >= absZ) {
            return 0;
        } else if (absY >= absZ) {
            return 1;
        }
        return 2;
    }

    @Override
    public int minComponent() {
        float absX = Math.abs(x);
        float absY = Math.abs(y);
        float absZ = Math.abs(z);
        if (absX < absY && absX < absZ) {
            return 0;
        } else if (absY < absZ) {
            return 1;
        }
        return 2;
    }

    @Override
    public Vector3fc orthogonalize(IVector3f v, Vector3fc dest) {
        float invLenV = 1.0f / (float) Math.sqrt(v.x() * v.x() + v.y() * v.y() + v.z() * v.z());
        float vx = v.x() * invLenV;
        float vy = v.y() * invLenV;
        float vz = v.z() * invLenV;
        float dot = (vx * x + vy * y + vz * z);
        float rx = x - dot * vx;
        float ry = y - dot * vy;
        float rz = z - dot * vz;
        float invLen = 1.0f / (float) Math.sqrt(rx * rx + ry * ry + rz * rz);
        dest.set(rx * invLen, ry * invLen, rz * invLen);
        return dest;
    }

    @Override
    public Vector3fc orthogonalize(IVector3f v) {
        return orthogonalize(v, this);
    }

    @Override
    public Vector3fc orthogonalizeUnit(IVector3f v, Vector3fc dest) {
        float vx = v.x();
        float vy = v.y();
        float vz = v.z();
        float dot = (vx * x + vy * y + vz * z);
        float rx = x - dot * vx;
        float ry = y - dot * vy;
        float rz = z - dot * vz;
        float invLen = 1.0f / (float) Math.sqrt(rx * rx + ry * ry + rz * rz);
        dest.set(rx * invLen, ry * invLen, rz * invLen);
        return dest;
    }

    @Override
    public Vector3fc orthogonalizeUnit(IVector3f v) {
        return orthogonalizeUnit(v, this);
    }
}
