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

import org.joml.api.matrix.IMatrix4f;
import org.joml.api.matrix.IMatrix4x3f;
import org.joml.api.quaternion.IQuaternionf;
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
 * Contains the definition of a Vector comprising 4 floats and associated
 * transformations.
 * 
 * @author Richard Greenlees
 * @author Kai Burjack
 */
public class Vector4f extends Vector4fc implements Externalizable {

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
     * The w component of the vector.
     */
    public float w;

    /**
     * Create a new {@link Vector4f} of <code>(0, 0, 0, 1)</code>.
     */
    public Vector4f() {
        this.w = 1.0f;
    }

    /**
     * Create a new {@link Vector4f} with the same values as <code>v</code>.
     * 
     * @param v
     *          the {@link IVector4f} to copy the values from
     */
    public Vector4f(IVector4f v) {
        if (v instanceof Vector4f) {
            MemUtil.INSTANCE.copy((Vector4f) v, this);
        } else {
            this.x = v.x();
            this.y = v.y();
            this.z = v.z();
            this.w = v.w();
        }
    }

    /**
     * Create a new {@link Vector4f} with the same values as <code>v</code>.
     * 
     * @param v
     *          the {@link IVector4i} to copy the values from
     */
    public Vector4f(IVector4i v) {
        this.x = v.x();
        this.y = v.y();
        this.z = v.z();
        this.w = v.w();
    }

    /**
     * Create a new {@link Vector4f} with the first three components from the
     * given <code>v</code> and the given <code>w</code>.
     * 
     * @param v
     *          the {@link IVector3f}
     * @param w
     *          the w component
     */
    public Vector4f(IVector3f v, float w) {
        this.x = v.x();
        this.y = v.y();
        this.z = v.z();
        this.w = w;
    }

    /**
     * Create a new {@link Vector4f} with the first three components from the
     * given <code>v</code> and the given <code>w</code>.
     * 
     * @param v
     *          the {@link IVector3i}
     * @param w
     *          the w component
     */
    public Vector4f(IVector3i v, float w) {
        this.x = v.x();
        this.y = v.y();
        this.z = v.z();
        this.w = w;
    }

    /**
     * Create a new {@link Vector4f} with the first two components from the
     * given <code>v</code> and the given <code>z</code>, and <code>w</code>.
     * 
     * @param v
     *          the {@link IVector2f}
     * @param z
     *          the z component
     * @param w
     *          the w component
     */
    public Vector4f(IVector2f v, float z, float w) {
        this.x = v.x();
        this.y = v.y();
        this.z = z;
        this.w = w;
    }

    /**
     * Create a new {@link Vector4f} with the first two components from the
     * given <code>v</code> and the given <code>z</code>, and <code>w</code>.
     * 
     * @param v
     *          the {@link IVector2i}
     * @param z
     *          the z component
     * @param w
     *          the w component
     */
    public Vector4f(IVector2i v, float z, float w) {
        this.x = v.x();
        this.y = v.y();
        this.z = z;
        this.w = w;
    }

    /**
     * Create a new {@link Vector4f} and initialize all four components with the given value.
     *
     * @param d
     *          the value of all four components
     */
    public Vector4f(float d) {
        MemUtil.INSTANCE.broadcast(d, this);
    }

    /**
     * Create a new {@link Vector4f} with the given component values.
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
    public Vector4f(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

//#ifdef __HAS_NIO__
    /**
     * Create a new {@link Vector4f} and read this vector from the supplied {@link ByteBuffer}
     * at the current buffer {@link ByteBuffer#position() position}.
     * <p>
     * This method will not increment the position of the given ByteBuffer.
     * <p>
     * In order to specify the offset into the ByteBuffer at which
     * the vector is read, use {@link #Vector4f(int, ByteBuffer)}, taking
     * the absolute position as parameter.
     *
     * @param buffer
     *          values will be read in <tt>x, y, z, w</tt> order
     * @see #Vector4f(int, ByteBuffer)
     */
    public Vector4f(ByteBuffer buffer) {
        this(buffer.position(), buffer);
    }

    /**
     * Create a new {@link Vector4f} and read this vector from the supplied {@link ByteBuffer}
     * starting at the specified absolute buffer position/index.
     * <p>
     * This method will not increment the position of the given ByteBuffer.
     *
     * @param index 
     *          the absolute position into the ByteBuffer
     * @param buffer
     *          values will be read in <tt>x, y, z, w</tt> order
     */
    public Vector4f(int index, ByteBuffer buffer) {
        MemUtil.INSTANCE.get(this, index, buffer);
    }

    /**
     * Create a new {@link Vector4f} and read this vector from the supplied {@link FloatBuffer}
     * at the current buffer {@link FloatBuffer#position() position}.
     * <p>
     * This method will not increment the position of the given FloatBuffer.
     * <p>
     * In order to specify the offset into the FloatBuffer at which
     * the vector is read, use {@link #Vector4f(int, FloatBuffer)}, taking
     * the absolute position as parameter.
     *
     * @param buffer
     *          values will be read in <tt>x, y, z, w</tt> order
     * @see #Vector4f(int, FloatBuffer)
     */
    public Vector4f(FloatBuffer buffer) {
        this(buffer.position(), buffer);
    }

    /**
     * Create a new {@link Vector4f} and read this vector from the supplied {@link FloatBuffer}
     * starting at the specified absolute buffer position/index.
     * <p>
     * This method will not increment the position of the given FloatBuffer.
     *
     * @param index 
     *          the absolute position into the FloatBuffer
     * @param buffer
     *          values will be read in <tt>x, y, z, w</tt> order
     */
    public Vector4f(int index, FloatBuffer buffer) {
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
    public float w() {
        return this.w;
    }

    @Override
    public Vector4fc set(IVector4f v) {
        if (v instanceof Vector4f) {
            MemUtil.INSTANCE.copy((Vector4f) v, this);
        } else {
            this.x = v.x();
            this.y = v.y();
            this.z = v.z();
            this.w = v.w();
        }
        return this;
    }

    @Override
    public Vector4fc set(IVector4i v) {
        this.x = v.x();
        this.y = v.y();
        this.z = v.z();
        this.w = v.w();
        return this;
    }

    @Override
    public Vector4fc set(IVector4d v) {
        this.x = (float) v.x();
        this.y = (float) v.y();
        this.z = (float) v.z();
        this.w = (float) v.w();
        return this;
    }

    @Override
    public Vector4fc set(IVector3f v, float w) {
        this.x = v.x();
        this.y = v.y();
        this.z = v.z();
        this.w = w;
        return this;
    }

    @Override
    public Vector4fc set(IVector3i v, float w) {
        this.x = v.x();
        this.y = v.y();
        this.z = v.z();
        this.w = w;
        return this;
    }

    @Override
    public Vector4fc set(IVector2f v, float z, float w) {
        this.x = v.x();
        this.y = v.y();
        this.z = z;
        this.w = w;
        return this;
    }

    @Override
    public Vector4fc set(IVector2i v, float z, float w) {
        this.x = v.x();
        this.y = v.y();
        this.z = z;
        this.w = w;
        return this;
    }

    @Override
    public Vector4fc set(float d) {
        MemUtil.INSTANCE.broadcast(d, this);
        return this;
    }

    @Override
    public Vector4fc set(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
        return this;
    }

//#ifdef __HAS_NIO__
    @Override
    public Vector4fc set(ByteBuffer buffer) {
        return set(buffer.position(), buffer);
    }

    @Override
    public Vector4fc set(int index, ByteBuffer buffer) {
        MemUtil.INSTANCE.get(this, index, buffer);
        return this;
    }

    @Override
    public Vector4fc set(FloatBuffer buffer) {
        return set(buffer.position(), buffer);
    }

    @Override
    public Vector4fc set(int index, FloatBuffer buffer) {
        MemUtil.INSTANCE.get(this, index, buffer);
        return this;
    }
//#endif

    @Override
    public Vector4fc setComponent(int component, float value) throws IllegalArgumentException {
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
    public Vector4fc sub(IVector4f v) {
        x -= v.x();
        y -= v.y();
        z -= v.z();
        w -= v.w();
        return this;
    }

    @Override
    public Vector4fc sub(float x, float y, float z, float w) {
        this.x -= x;
        this.y -= y;
        this.z -= z;
        this.w -= w;
        return this;
    }

    @Override
    public Vector4fc sub(IVector4f v, Vector4fc dest) {
        dest.set(x - v.x(), y - v.y(), z - v.z(), w - v.w());
        return dest;
    }

    @Override
    public Vector4fc sub(float x, float y, float z, float w, Vector4fc dest) {
        dest.set(this.x - x, this.y - y, this.z - z, this.w - w);
        return dest;
    }

    @Override
    public Vector4fc add(IVector4f v) {
        x += v.x();
        y += v.y();
        z += v.z();
        w += v.w();
        return this;
    }

    @Override
    public Vector4fc add(IVector4f v, Vector4fc dest) {
        dest.set(x + v.x(), y + v.y(), z + v.z(), w + v.w());
        return dest;
    }

    @Override
    public Vector4fc add(float x, float y, float z, float w) {
        this.x += x;
        this.y += y;
        this.z += z;
        this.w += w;
        return this;
    }

    @Override
    public Vector4fc add(float x, float y, float z, float w, Vector4fc dest) {
        dest.set(this.x + x, this.y + y, this.z + z, this.w + w);
        return dest;
    }

    @Override
    public Vector4fc fma(IVector4f a, IVector4f b) {
        x += a.x() * b.x();
        y += a.y() * b.y();
        z += a.z() * b.z();
        w += a.w() * b.w();
        return this;
    }

    @Override
    public Vector4fc fma(float a, IVector4f b) {
        x += a * b.x();
        y += a * b.y();
        z += a * b.z();
        w += a * b.w();
        return this;
    }

    @Override
    public Vector4fc fma(IVector4f a, IVector4f b, Vector4fc dest) {
        dest.set(x + a.x() * b.x(), y + a.y() * b.y(), z + a.z() * b.z(), w + a.w() * b.w());
        return dest;
    }

    @Override
    public Vector4fc fma(float a, IVector4f b, Vector4fc dest) {
        dest.set(x + a * b.x(), y + a * b.y(), z + a * b.z(), w + a * b.w());
        return dest;
    }

    @Override
    public Vector4fc mul(IVector4f v) {
        x *= v.x();
        y *= v.y();
        z *= v.z();
        w *= v.w();
        return this;
    }

    @Override
    public Vector4fc mul(IVector4f v, Vector4fc dest) {
        dest.set(x * v.x(), y * v.y(), z * v.z(), w * v.w());
        return dest;
    }

    @Override
    public Vector4fc div(IVector4f v) {
        x /= v.x();
        y /= v.y();
        z /= v.z();
        w /= v.w();
        return this;
    }

    @Override
    public Vector4fc div(IVector4f v, Vector4fc dest) {
        dest.set(x / v.x(), y / v.y(), z / v.z(), w / v.w());
        return dest;
    }

    @Override
    public Vector4fc mul(IMatrix4f mat) {
        return mul(mat, this);
    }

    @Override
    public Vector4fc mul(IMatrix4f mat, Vector4fc dest) {
        dest.set(mat.m00() * x + mat.m10() * y + mat.m20() * z + mat.m30() * w,
                 mat.m01() * x + mat.m11() * y + mat.m21() * z + mat.m31() * w,
                 mat.m02() * x + mat.m12() * y + mat.m22() * z + mat.m32() * w,
                 mat.m03() * x + mat.m13() * y + mat.m23() * z + mat.m33() * w);
        return dest;
    }

    @Override
    public Vector4fc mul(IMatrix4x3f mat) {
        return mul(mat, this);
    }

    @Override
    public Vector4fc mul(IMatrix4x3f mat, Vector4fc dest) {
        dest.set(mat.m00() * x + mat.m10() * y + mat.m20() * z + mat.m30() * w,
                 mat.m01() * x + mat.m11() * y + mat.m21() * z + mat.m31() * w,
                 mat.m02() * x + mat.m12() * y + mat.m22() * z + mat.m32() * w,
                 w);
        return dest;
    }

    @Override
    public Vector4fc mulProject(IMatrix4f mat, Vector4fc dest) {
        float invW = 1.0f / (mat.m03() * x + mat.m13() * y + mat.m23() * z + mat.m33() * w);
        dest.set((mat.m00() * x + mat.m10() * y + mat.m20() * z + mat.m30() * w) * invW,
                 (mat.m01() * x + mat.m11() * y + mat.m21() * z + mat.m31() * w) * invW,
                 (mat.m02() * x + mat.m12() * y + mat.m22() * z + mat.m32() * w) * invW,
                 1.0f);
        return dest;
    }

    @Override
    public Vector4fc mulProject(IMatrix4f mat) {
        return mulProject(mat, this);
    }

    @Override
    public Vector4fc mul(float scalar) {
        x *= scalar;
        y *= scalar;
        z *= scalar;
        w *= scalar;
        return this;
    }

    @Override
    public Vector4fc mul(float scalar, Vector4fc dest) {
        dest.set(x * scalar, y * scalar, z * scalar, w * scalar);
        return dest;
    }

    @Override
    public Vector4fc mul(float x, float y, float z, float w) {
        this.x *= x;
        this.y *= y;
        this.z *= z;
        this.w *= w;
        return this;
    }

    @Override
    public Vector4fc mul(float x, float y, float z, float w, Vector4fc dest) {
        dest.set(this.x * x, this.y * y, this.z * z, this.w * w);
        return dest;
    }

    @Override
    public Vector4fc div(float scalar) {
        float inv = 1.0f / scalar;
        x *= inv;
        y *= inv;
        z *= inv;
        w *= inv;
        return this;
    }

    @Override
    public Vector4fc div(float scalar, Vector4fc dest) {
        float inv = 1.0f / scalar;
        dest.set(x * inv, y * inv, z * inv, w * inv);
        return dest;
    }

    @Override
    public Vector4fc div(float x, float y, float z, float w) {
        this.x /= x;
        this.y /= y;
        this.z /= z;
        this.w /= w;
        return this;
    }

    @Override
    public Vector4fc div(float x, float y, float z, float w, Vector4fc dest) {
        dest.set(this.x / x, this.y / y, this.z / z, this.w / w);
        return dest;
    }

    @Override
    public Vector4fc rotate(IQuaternionf quat) {
        return rotate(quat, this);
    }

    @Override
    public Vector4fc rotate(IQuaternionf quat, Vector4fc dest) {
        return quat.transform(this, dest);
    }

    @Override
    public Vector4fc rotateAbout(float angle, float x, float y, float z) {
        return rotateAxis(angle, x, y, z, this);
    }

    @Override
    public Vector4fc rotateAxis(float angle, float aX, float aY, float aZ, Vector4fc dest) {
        float hangle = angle * 0.5f;
        float sinAngle = (float) Math.sin(hangle);
        float qx = aX * sinAngle, qy = aY * sinAngle, qz = aZ * sinAngle;
        float qw = (float) Math.cosFromSin(sinAngle, hangle);
        float w2 = qw * qw, x2 = qx * qx, y2 = qy * qy, z2 = qz * qz, zw = qz * qw;
        float xy = qx * qy, xz = qx * qz, yw = qy * qw, yz = qy * qz, xw = qx * qw;
        float nx = (w2 + x2 - z2 - y2) * x + (-zw + xy - zw + xy) * y + (yw + xz + xz + yw) * z;
        float ny = (xy + zw + zw + xy) * x + ( y2 - z2 + w2 - x2) * y + (yz + yz - xw - xw) * z;
        float nz = (xz - yw + xz - yw) * x + ( yz + yz + xw + xw) * y + (z2 - y2 - x2 + w2) * z;
        dest.set(nx, ny, nz, dest.w());
        return dest;
    }

    @Override
    public Vector4fc rotateX(float angle) {
        return rotateX(angle, this);
    }

    @Override
    public Vector4fc rotateX(float angle, Vector4fc dest) {
        float sin = (float) Math.sin(angle), cos = (float) Math.cosFromSin(sin, angle);
        float y = this.y * cos - this.z * sin;
        float z = this.y * sin + this.z * cos;
        dest.set(this.x, y, z, this.w);
        return dest;
    }

    @Override
    public Vector4fc rotateY(float angle) {
        return rotateY(angle, this);
    }

    @Override
    public Vector4fc rotateY(float angle, Vector4fc dest) {
        float sin = (float) Math.sin(angle), cos = (float) Math.cosFromSin(sin, angle);
        float x =  this.x * cos + this.z * sin;
        float z = -this.x * sin + this.z * cos;
        dest.set(x, this.y, z, dest.w());
        //TODO
        return dest;
    }

    @Override
    public Vector4fc rotateZ(float angle) {
        return rotateZ(angle, this);
    }

    @Override
    public Vector4fc rotateZ(float angle, Vector4fc dest) {
        float sin = (float) Math.sin(angle), cos = (float) Math.cosFromSin(sin, angle);
        float x = this.x * cos - this.y * sin;
        float y = this.x * sin + this.y * cos;
        dest.set(x, y, this.z, this.w);
        return dest;
    }

    @Override
    public float lengthSquared() {
        return x * x + y * y + z * z + w * w;
    }

    @Override
    public float length() {
        return (float) Math.sqrt(lengthSquared());
    }

    @Override
    public Vector4fc normalize() {
        float invLength = 1.0f / length();
        x *= invLength;
        y *= invLength;
        z *= invLength;
        w *= invLength;
        return this;
    }

    @Override
    public Vector4fc normalize(Vector4fc dest) {
        float invLength = 1.0f / length();
        dest.set(x * invLength, y * invLength, z * invLength, w * invLength);
        return dest;
    }

    @Override
    public Vector4fc normalize(float length) {
        float invLength = 1.0f / length() * length;
        x *= invLength;
        y *= invLength;
        z *= invLength;
        w *= invLength;
        return this;
    }

    @Override
    public Vector4fc normalize(float length, Vector4fc dest) {
        float invLength = 1.0f / length() * length;
        dest.set(x * invLength, y * invLength, z * invLength, w * invLength);
        return dest;
    }

    @Override
    public Vector4fc normalize3() {
        float invLength = (float) (1.0 / Math.sqrt(x * x + y * y + z * z));
        x *= invLength;
        y *= invLength;
        z *= invLength;
        w *= invLength;
        return this;
    }

    @Override
    public float distance(IVector4f v) {
        float dx = v.x() - x;
        float dy = v.y() - y;
        float dz = v.z() - z;
        float dw = v.w() - w;
        return (float) Math.sqrt(dx * dx + dy * dy + dz * dz + dw * dw);
    }

    @Override
    public float distance(float x, float y, float z, float w) {
        float dx = this.x - x;
        float dy = this.y - y;
        float dz = this.z - z;
        float dw = this.w - w;
        return (float) Math.sqrt(dx * dx + dy * dy + dz * dz + dw * dw);
    }

    @Override
    public float dot(IVector4f v) {
        return x * v.x() + y * v.y() + z * v.z() + w * v.w();
    }

    @Override
    public float dot(float x, float y, float z, float w) {
        return this.x * x + this.y * y + this.z * z + this.w * w;
    }

    @Override
    public float angleCos(IVector4f v) {
        double length1Sqared = x * x + y * y + z * z + w * w;
        double length2Sqared = v.x() * v.x() + v.y() * v.y() + v.z() * v.z() + v.w() * v.w();
        double dot = x * v.x() + y * v.y() + z * v.z() + w * v.w();
        return (float) (dot / (Math.sqrt(length1Sqared * length2Sqared)));
    }

    @Override
    public float angle(IVector4f v) {
        float cos = angleCos(v);
        // This is because sometimes cos goes above 1 or below -1 because of lost precision
        cos = cos < 1 ? cos : 1;
        cos = cos > -1 ? cos : -1;
        return (float) Math.acos(cos);
    }

    @Override
    public Vector4fc zero() {
        MemUtil.INSTANCE.zero(this);
        return this;
    }

    @Override
    public Vector4fc negate() {
        x = -x;
        y = -y;
        z = -z;
        w = -w;
        return this;
    }

    @Override
    public Vector4fc negate(Vector4fc dest) {
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
        out.writeFloat(x);
        out.writeFloat(y);
        out.writeFloat(z);
        out.writeFloat(w);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        x = in.readFloat();
        y = in.readFloat();
        z = in.readFloat();
        w = in.readFloat();
    }

    @Override
    public Vector4fc min(IVector4f v) {
        this.x = x < v.x() ? x : v.x();
        this.y = y < v.y() ? y : v.y();
        this.z = z < v.z() ? z : v.z();
        this.w = w < v.w() ? w : v.w();
        return this;
    }

    @Override
    public Vector4fc max(IVector4f v) {
        this.x = x > v.x() ? x : v.x();
        this.y = y > v.y() ? y : v.y();
        this.z = z > v.z() ? z : v.z();
        this.w = w > v.w() ? w : v.w();
        return this;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Float.floatToIntBits(w);
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
        if (!(obj instanceof Vector4fc))
            return false;
        Vector4fc other = (Vector4fc) obj;
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

    @Override
    public Vector4fc smoothStep(IVector4f v, float t, Vector4fc dest) {
        float t2 = t * t;
        float t3 = t2 * t;
        dest.set((x + x - v.x() - v.x()) * t3 + (3.0f * v.x() - 3.0f * x) * t2 + x * t + x,
                (y + y - v.y() - v.y()) * t3 + (3.0f * v.y() - 3.0f * y) * t2 + y * t + y,
                (z + z - v.z() - v.z()) * t3 + (3.0f * v.z() - 3.0f * z) * t2 + z * t + z,
                (w + w - v.w() - v.w()) * t3 + (3.0f * v.w() - 3.0f * w) * t2 + w * t + w);
        return dest;
    }

    @Override
    public Vector4fc hermite(IVector4f t0, IVector4f v1, IVector4f t1, float t, Vector4fc dest) {
        float t2 = t * t;
        float t3 = t2 * t;
        dest.set((x + x - v1.x() - v1.x() + t1.x() + t0.x()) * t3 + (3.0f * v1.x() - 3.0f * x - t0.x() - t0.x() - t1.x()) * t2 + x * t + x,
                (y + y - v1.y() - v1.y() + t1.y() + t0.y()) * t3 + (3.0f * v1.y() - 3.0f * y - t0.y() - t0.y() - t1.y()) * t2 + y * t + y,
                (z + z - v1.z() - v1.z() + t1.z() + t0.z()) * t3 + (3.0f * v1.z() - 3.0f * z - t0.z() - t0.z() - t1.z()) * t2 + z * t + z,
                (w + w - v1.w() - v1.w() + t1.w() + t0.w()) * t3 + (3.0f * v1.w() - 3.0f * w - t0.w() - t0.w() - t1.w()) * t2 + w * t + w);
        return dest;
    }

    @Override
    public Vector4fc lerp(IVector4f other, float t) {
        return lerp(other, t, this);
    }

    @Override
    public Vector4fc lerp(IVector4f other, float t, Vector4fc dest) {
        dest.set(x + (other.x() - x) * t,
                y + (other.y() - y) * t,
                z + (other.z() - z) * t,
                w + (other.w() - w) * t);
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
        case 3:
            return w;
        default:
            throw new IllegalArgumentException();
        }
    }

    @Override
    public Vector4fc set(int component, float value) throws IllegalArgumentException {
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
