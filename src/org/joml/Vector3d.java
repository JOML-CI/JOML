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
import org.joml.api.quaternion.IQuaterniond;
import org.joml.api.quaternion.Quaterniondc;
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
 * Contains the definition of a Vector comprising 3 doubles and associated
 * transformations.
 *
 * @author Richard Greenlees
 * @author Kai Burjack
 */
public class Vector3d extends Vector3dc implements Externalizable {

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
     * Create a new {@link Vector3d} with all components set to zero.
     */
    public Vector3d() {
    }

    /**
     * Create a new {@link Vector3d} and initialize all three components with the given value.
     *
     * @param d
     *          the value of all three components
     */
    public Vector3d(double d) {
        this(d, d, d);
    }

    /**
     * Create a new {@link Vector3d} with the given component values.
     * 
     * @param x
     *          the value of x
     * @param y
     *          the value of y
     * @param z
     *          the value of z
     */
    public Vector3d(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Create a new {@link Vector3d} whose values will be copied from the given vector.
     * 
     * @param v
     *          provides the initial values for the new vector
     */
    public Vector3d(IVector3f v) {
        this.x = v.x();
        this.y = v.y();
        this.z = v.z();
    }

    /**
     * Create a new {@link Vector3d} whose values will be copied from the given vector.
     * 
     * @param v
     *          provides the initial values for the new vector
     */
    public Vector3d(IVector3i v) {
        this.x = v.x();
        this.y = v.y();
        this.z = v.z();
    }

    /**
     * Create a new {@link Vector3d} with the first two components from the
     * given <code>v</code> and the given <code>z</code>
     *
     * @param v
     *          the {@link IVector2f} to copy the values from
     * @param z
     *          the z component
     */
    public Vector3d(IVector2f v, double z) {
        this.x = v.x();
        this.y = v.y();
        this.z = z;
    }

    /**
     * Create a new {@link Vector3d} with the first two components from the
     * given <code>v</code> and the given <code>z</code>
     *
     * @param v
     *          the {@link IVector2i} to copy the values from
     * @param z
     *          the z component
     */
    public Vector3d(IVector2i v, double z) {
        this.x = v.x();
        this.y = v.y();
        this.z = z;
    }

    /**
     * Create a new {@link Vector3d} whose values will be copied from the given vector.
     * 
     * @param v
     *          provides the initial values for the new vector
     */
    public Vector3d(IVector3d v) {
        this.x = v.x();
        this.y = v.y();
        this.z = v.z();
    }

    /**
     * Create a new {@link Vector3d} with the first two components from the
     * given <code>v</code> and the given <code>z</code>
     *
     * @param v
     *          the {@link IVector2d} to copy the values from
     * @param z
     *          the z component
     */
    public Vector3d(IVector2d v, double z) {
        this.x = v.x();
        this.y = v.y();
        this.z = z;
    }

    //#ifdef __HAS_NIO__
    /**
     * Create a new {@link Vector3d} and read this vector from the supplied {@link ByteBuffer}
     * at the current buffer {@link ByteBuffer#position() position}.
     * <p>
     * This method will not increment the position of the given ByteBuffer.
     * <p>
     * In order to specify the offset into the ByteBuffer at which
     * the vector is read, use {@link #Vector3d(int, ByteBuffer)}, taking
     * the absolute position as parameter.
     *
     * @param buffer values will be read in <tt>x, y, z</tt> order
     * @see #Vector3d(int, ByteBuffer)
     */
    public Vector3d(ByteBuffer buffer) {
        this(buffer.position(), buffer);
    }

    /**
     * Create a new {@link Vector3d} and read this vector from the supplied {@link ByteBuffer}
     * starting at the specified absolute buffer position/index.
     * <p>
     * This method will not increment the position of the given ByteBuffer.
     *
     * @param index  the absolute position into the ByteBuffer
     * @param buffer values will be read in <tt>x, y, z</tt> order
     */
    public Vector3d(int index, ByteBuffer buffer) {
        MemUtil.INSTANCE.get(this, index, buffer);
    }

    /**
     * Create a new {@link Vector3d} and read this vector from the supplied {@link DoubleBuffer}
     * at the current buffer {@link DoubleBuffer#position() position}.
     * <p>
     * This method will not increment the position of the given DoubleBuffer.
     * <p>
     * In order to specify the offset into the DoubleBuffer at which
     * the vector is read, use {@link #Vector3d(int, DoubleBuffer)}, taking
     * the absolute position as parameter.
     *
     * @param buffer values will be read in <tt>x, y, z</tt> order
     * @see #Vector3d(int, DoubleBuffer)
     */
    public Vector3d(DoubleBuffer buffer) {
        this(buffer.position(), buffer);
    }

    /**
     * Create a new {@link Vector3d} and read this vector from the supplied {@link DoubleBuffer}
     * starting at the specified absolute buffer position/index.
     * <p>
     * This method will not increment the position of the given DoubleBuffer.
     *
     * @param index  the absolute position into the DoubleBuffer
     * @param buffer values will be read in <tt>x, y, z</tt> order
     */
    public Vector3d(int index, DoubleBuffer buffer) {
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
    public Vector3dc set(IVector3d v) {
        x = v.x();
        y = v.y();
        z = v.z();
        return this;
    }

    @Override
    public Vector3dc set(IVector3i v) {
        x = v.x();
        y = v.y();
        z = v.z();
        return this;
    }

    @Override
    public Vector3dc set(IVector2d v, double z) {
        this.x = v.x();
        this.y = v.y();
        this.z = z;
        return this;
    }

    @Override
    public Vector3dc set(IVector2i v, double z) {
        this.x = v.x();
        this.y = v.y();
        this.z = z;
        return this;
    }

    @Override
    public Vector3dc set(IVector3f v) {
        x = v.x();
        y = v.y();
        z = v.z();
        return this;
    }

    @Override
    public Vector3dc set(IVector2f v, double z) {
        this.x = v.x();
        this.y = v.y();
        this.z = z;
        return this;
    }

    @Override
    public Vector3dc set(double d) {
        return set(d, d, d);
    }

    @Override
    public Vector3dc set(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }

    //#ifdef __HAS_NIO__
    @Override
    public Vector3dc set(ByteBuffer buffer) {
        return set(buffer.position(), buffer);
    }

    @Override
    public Vector3dc set(int index, ByteBuffer buffer) {
        MemUtil.INSTANCE.get(this, index, buffer);
        return this;
    }

    @Override
    public Vector3dc set(DoubleBuffer buffer) {
        return set(buffer.position(), buffer);
    }

    @Override
    public Vector3dc set(int index, DoubleBuffer buffer) {
        MemUtil.INSTANCE.get(this, index, buffer);
        return this;
    }
    //#endif

    @Override
    public Vector3dc setComponent(int component, double value) throws IllegalArgumentException {
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
    public Vector3dc sub(IVector3d v) {
        x -= v.x();
        y -= v.y();
        z -= v.z();
        return this;
    }

    @Override
    public Vector3dc sub(IVector3d v, Vector3dc dest) {
        dest.set(x - v.x(), y - v.y(), z - v.z());
        return dest;
    }

    @Override
    public Vector3dc sub(IVector3f v) {
        x -= v.x();
        y -= v.y();
        z -= v.z();
        return this;
    }

    @Override
    public Vector3dc sub(IVector3f v, Vector3dc dest) {
        dest.set(x - v.x(), y - v.y(), z - v.z());
        return dest;
    }

    @Override
    public Vector3dc sub(double x, double y, double z) {
        this.x -= x;
        this.y -= y;
        this.z -= z;
        return this;
    }

    @Override
    public Vector3dc sub(double x, double y, double z, Vector3dc dest) {
        dest.set(this.x - x, this.y - y, this.z - z);
        return dest;
    }

    @Override
    public Vector3dc add(IVector3d v) {
        x += v.x();
        y += v.y();
        z += v.z();
        return this;
    }

    @Override
    public Vector3dc add(IVector3d v, Vector3dc dest) {
        dest.set(x + v.x(), y + v.y(), z + v.z());
        return dest;
    }

    @Override
    public Vector3dc add(IVector3f v) {
        x += v.x();
        y += v.y();
        z += v.z();
        return this;
    }

    @Override
    public Vector3dc add(IVector3f v, Vector3dc dest) {
        dest.set(x + v.x(), y + v.y(), z + v.z());
        return dest;
    }

    @Override
    public Vector3dc add(double x, double y, double z) {
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }

    @Override
    public Vector3dc add(double x, double y, double z, Vector3dc dest) {
        dest.set(this.x + x, this.y + y, this.z + z);
        return dest;
    }

    @Override
    public Vector3dc fma(IVector3d a, IVector3d b) {
        x += a.x() * b.x();
        y += a.y() * b.y();
        z += a.z() * b.z();
        return this;
    }

    @Override
    public Vector3dc fma(double a, IVector3d b) {
        x += a * b.x();
        y += a * b.y();
        z += a * b.z();
        return this;
    }

    @Override
    public Vector3dc fma(IVector3f a, IVector3f b) {
        x += a.x() * b.x();
        y += a.y() * b.y();
        z += a.z() * b.z();
        return this;
    }

    @Override
    public Vector3dc fma(double a, IVector3f b) {
        x += a * b.x();
        y += a * b.y();
        z += a * b.z();
        return this;
    }

    @Override
    public Vector3dc fma(IVector3d a, IVector3d b, Vector3dc dest) {
        dest.set(x + a.x() * b.x(), y + a.y() * b.y(), z + a.z() * b.z());
        return dest;
    }

    @Override
    public Vector3dc fma(double a, IVector3d b, Vector3dc dest) {
        dest.set(x + a * b.x(), y + a * b.y(), z + a * b.z());
        return dest;
    }

    @Override
    public Vector3dc fma(IVector3d a, IVector3f b, Vector3dc dest) {
        dest.set(x + a.x() * b.x(), y + a.y() * b.y(), z + a.z() * b.z());
        return dest;
    }

    @Override
    public Vector3dc fma(double a, IVector3f b, Vector3dc dest) {
        dest.set(x + a * b.x(), y + a * b.y(), z + a * b.z());
        return dest;
    }

    @Override
    public Vector3dc mul(IVector3d v) {
        x *= v.x();
        y *= v.y();
        z *= v.z();
        return this;
    }

    @Override
    public Vector3dc mul(IVector3f v) {
        x *= v.x();
        y *= v.y();
        z *= v.z();
        return this;
    }

    @Override
    public Vector3dc mul(IVector3f v, Vector3dc dest) {
        dest.set(x * v.x(), y * v.y(), z * v.z());
        return dest;
    }

    @Override
    public Vector3dc mul(IVector3d v, Vector3dc dest) {
        dest.set(x * v.x(), y * v.y(), z * v.z());
        return dest;
    }

    @Override
    public Vector3dc div(Vector3dc v) {
        x /= v.x();
        y /= v.y();
        z /= v.z();
        return this;
    }

    @Override
    public Vector3dc div(IVector3f v) {
        x /= v.x();
        y /= v.y();
        z /= v.z();
        return this;
    }

    @Override
    public Vector3dc div(IVector3f v, Vector3dc dest) {
        dest.set(x / v.x(), y / v.y(), z / v.z());
        return dest;
    }

    @Override
    public Vector3dc div(IVector3d v, Vector3dc dest) {
        dest.set(x / v.x(), y / v.y(), z / v.z());
        return dest;
    }

    @Override
    public Vector3dc mulProject(IMatrix4d mat, Vector3dc dest) {
        double invW = 1.0 / (mat.m03() * x + mat.m13() * y + mat.m23() * z + mat.m33());
        dest.set((mat.m00() * x + mat.m10() * y + mat.m20() * z + mat.m30()) * invW,
                 (mat.m01() * x + mat.m11() * y + mat.m21() * z + mat.m31()) * invW,
                 (mat.m02() * x + mat.m12() * y + mat.m22() * z + mat.m32()) * invW);
        return dest;
    }

    @Override
    public Vector3dc mulProject(IMatrix4d mat) {
        return mulProject(mat, this);
    }

    @Override
    public Vector3dc mulProject(IMatrix4f mat, Vector3dc dest) {
        double invW = 1.0 / (mat.m03() * x + mat.m13() * y + mat.m23() * z + mat.m33());
        dest.set((mat.m00() * x + mat.m10() * y + mat.m20() * z + mat.m30()) * invW,
                 (mat.m01() * x + mat.m11() * y + mat.m21() * z + mat.m31()) * invW,
                 (mat.m02() * x + mat.m12() * y + mat.m22() * z + mat.m32()) * invW);
        return dest;
    }

    @Override
    public Vector3dc mulProject(IMatrix4f mat) {
        return mulProject(mat, this);
    }

    @Override
    public Vector3dc mul(IMatrix3f mat) {
        return mul(mat, this);
    }

    @Override
    public Vector3dc mul(IMatrix3d mat) {
        return mul(mat, this);
    }

    @Override
    public Vector3dc mul(IMatrix3d mat, Vector3dc dest) {
        dest.set(mat.m00() * x + mat.m10() * y + mat.m20() * z,
                 mat.m01() * x + mat.m11() * y + mat.m21() * z,
                 mat.m02() * x + mat.m12() * y + mat.m22() * z);
        return dest;
    }

    @Override
    public Vector3fc mul(IMatrix3d mat, Vector3fc dest) {
        dest.set((float)(mat.m00() * x + mat.m10() * y + mat.m20() * z),
                 (float)(mat.m01() * x + mat.m11() * y + mat.m21() * z),
                 (float)(mat.m02() * x + mat.m12() * y + mat.m22() * z));
        return dest;
    }

    @Override
    public Vector3dc mul(IMatrix3f mat, Vector3dc dest) {
        dest.set(mat.m00() * x + mat.m10() * y + mat.m20() * z,
                 mat.m01() * x + mat.m11() * y + mat.m21() * z,
                 mat.m02() * x + mat.m12() * y + mat.m22() * z);
        return dest;
    }

    @Override
    public Vector3dc mul(IMatrix3x2d mat) {
        return mul(mat, this);
    }

    @Override
    public Vector3dc mul(IMatrix3x2d mat, Vector3dc dest) {
        dest.set(mat.m00() * x + mat.m10() * y + mat.m20() * z,
                 mat.m01() * x + mat.m11() * y + mat.m21() * z,
                 z);
        return dest;
    }

    @Override
    public Vector3dc mul(IMatrix3x2f mat) {
        return mul(mat, this);
    }

    @Override
    public Vector3dc mul(IMatrix3x2f mat, Vector3dc dest) {
        dest.set(mat.m00() * x + mat.m10() * y + mat.m20() * z,
                 mat.m01() * x + mat.m11() * y + mat.m21() * z,
                 z);
        return dest;
    }

    @Override
    public Vector3dc mulTranspose(IMatrix3d mat) {
        return mulTranspose(mat, this);
    }

    @Override
    public Vector3dc mulTranspose(IMatrix3d mat, Vector3dc dest) {
        dest.set(mat.m00() * x + mat.m01() * y + mat.m02() * z,
                 mat.m10() * x + mat.m11() * y + mat.m12() * z,
                 mat.m20() * x + mat.m21() * y + mat.m22() * z);
        return dest;
    }

    @Override
    public Vector3dc mulTranspose(IMatrix3f mat) {
        return mulTranspose(mat, this);
    }

    @Override
    public Vector3dc mulTranspose(IMatrix3f mat, Vector3dc dest) {
        dest.set(mat.m00() * x + mat.m01() * y + mat.m02() * z,
                 mat.m10() * x + mat.m11() * y + mat.m12() * z,
                 mat.m20() * x + mat.m21() * y + mat.m22() * z);
        return dest;
    }

    @Override
    public Vector3dc mulPosition(IMatrix4f mat) {
        return mulPosition(mat, this);
    }

    @Override
    public Vector3dc mulPosition(IMatrix4d mat) {
        return mulPosition(mat, this);
    }

    @Override
    public Vector3dc mulPosition(IMatrix4x3d mat) {
        return mulPosition(mat, this);
    }

    @Override
    public Vector3dc mulPosition(IMatrix4x3f mat) {
        return mulPosition(mat, this);
    }

    @Override
    public Vector3dc mulPosition(IMatrix4d mat, Vector3dc dest) {
        dest.set(mat.m00() * x + mat.m10() * y + mat.m20() * z + mat.m30(),
                 mat.m01() * x + mat.m11() * y + mat.m21() * z + mat.m31(),
                 mat.m02() * x + mat.m12() * y + mat.m22() * z + mat.m32());
        return dest;
    }

    @Override
    public Vector3dc mulPosition(IMatrix4f mat, Vector3dc dest) {
        dest.set(mat.m00() * x + mat.m10() * y + mat.m20() * z + mat.m30(),
                 mat.m01() * x + mat.m11() * y + mat.m21() * z + mat.m31(),
                 mat.m02() * x + mat.m12() * y + mat.m22() * z + mat.m32());
        return dest;
    }

    @Override
    public Vector3dc mulPosition(IMatrix4x3d mat, Vector3dc dest) {
        dest.set(mat.m00() * x + mat.m10() * y + mat.m20() * z + mat.m30(),
                 mat.m01() * x + mat.m11() * y + mat.m21() * z + mat.m31(),
                 mat.m02() * x + mat.m12() * y + mat.m22() * z + mat.m32());
        return dest;
    }

    @Override
    public Vector3dc mulPosition(IMatrix4x3f mat, Vector3dc dest) {
        dest.set(mat.m00() * x + mat.m10() * y + mat.m20() * z + mat.m30(),
                 mat.m01() * x + mat.m11() * y + mat.m21() * z + mat.m31(),
                 mat.m02() * x + mat.m12() * y + mat.m22() * z + mat.m32());
        return dest;
    }

    @Override
    public Vector3dc mulTransposePosition(IMatrix4d mat) {
        return mulTransposePosition(mat, this);
    }

    @Override
    public Vector3dc mulTransposePosition(IMatrix4d mat, Vector3dc dest) {
        dest.set(mat.m00() * x + mat.m01() * y + mat.m02() * z + mat.m03(),
                 mat.m10() * x + mat.m11() * y + mat.m12() * z + mat.m13(),
                 mat.m20() * x + mat.m21() * y + mat.m22() * z + mat.m23());
        return dest;
    }

    @Override
    public Vector3dc mulTransposePosition(IMatrix4f mat) {
        return mulTransposePosition(mat, this);
    }

    @Override
    public Vector3dc mulTransposePosition(IMatrix4f mat, Vector3dc dest) {
        dest.set(mat.m00() * x + mat.m01() * y + mat.m02() * z + mat.m03(),
                 mat.m10() * x + mat.m11() * y + mat.m12() * z + mat.m13(),
                 mat.m20() * x + mat.m21() * y + mat.m22() * z + mat.m23());
        return dest;
    }

    @Override
    public double mulPositionW(IMatrix4f mat) {
        return mulPositionW(mat, this);
    }

    @Override
    public double mulPositionW(IMatrix4f mat, Vector3dc dest) {
        double w = mat.m03() * x + mat.m13() * y + mat.m23() * z + mat.m33();
        dest.set(mat.m00() * x + mat.m10() * y + mat.m20() * z + mat.m30(),
                 mat.m01() * x + mat.m11() * y + mat.m21() * z + mat.m31(),
                 mat.m02() * x + mat.m12() * y + mat.m22() * z + mat.m32());
        return w;
    }

    @Override
    public double mulPositionW(IMatrix4d mat) {
        return mulPositionW(mat, this);
    }

    @Override
    public double mulPositionW(IMatrix4d mat, Vector3dc dest) {
        double w = mat.m03() * x + mat.m13() * y + mat.m23() * z + mat.m33();
        dest.set(mat.m00() * x + mat.m10() * y + mat.m20() * z + mat.m30(),
                 mat.m01() * x + mat.m11() * y + mat.m21() * z + mat.m31(),
                 mat.m02() * x + mat.m12() * y + mat.m22() * z + mat.m32());
        return w;
    }

    @Override
    public Vector3dc mulDirection(IMatrix4f mat) {
        return mulDirection(mat, this);
    }

    @Override
    public Vector3dc mulDirection(IMatrix4d mat) {
        return mulDirection(mat, this);
    }

    @Override
    public Vector3dc mulDirection(IMatrix4x3d mat) {
        return mulDirection(mat, this);
    }

    @Override
    public Vector3dc mulDirection(IMatrix4x3f mat) {
        return mulDirection(mat, this);
    }

    @Override
    public Vector3dc mulDirection(IMatrix4d mat, Vector3dc dest) {
        dest.set(mat.m00() * x + mat.m10() * y + mat.m20() * z,
                 mat.m01() * x + mat.m11() * y + mat.m21() * z,
                 mat.m02() * x + mat.m12() * y + mat.m22() * z);
        return dest;
    }

    @Override
    public Vector3dc mulDirection(IMatrix4f mat, Vector3dc dest) {
        dest.set(mat.m00() * x + mat.m10() * y + mat.m20() * z,
                 mat.m01() * x + mat.m11() * y + mat.m21() * z,
                 mat.m02() * x + mat.m12() * y + mat.m22() * z);
        return dest;
    }

    @Override
    public Vector3dc mulDirection(IMatrix4x3d mat, Vector3dc dest) {
        dest.set(mat.m00() * x + mat.m10() * y + mat.m20() * z,
                 mat.m01() * x + mat.m11() * y + mat.m21() * z,
                 mat.m02() * x + mat.m12() * y + mat.m22() * z);
        return dest;
    }

    @Override
    public Vector3dc mulDirection(IMatrix4x3f mat, Vector3dc dest) {
        dest.set(mat.m00() * x + mat.m10() * y + mat.m20() * z,
                 mat.m01() * x + mat.m11() * y + mat.m21() * z,
                 mat.m02() * x + mat.m12() * y + mat.m22() * z);
        return dest;
    }

    @Override
    public Vector3dc mulTransposeDirection(IMatrix4d mat) {
        return mulTransposeDirection(mat, this);
    }

    @Override
    public Vector3dc mulTransposeDirection(IMatrix4d mat, Vector3dc dest) {
        dest.set(mat.m00() * x + mat.m01() * y + mat.m02() * z,
                 mat.m10() * x + mat.m11() * y + mat.m12() * z,
                 mat.m20() * x + mat.m21() * y + mat.m22() * z);
        return dest;
    }

    @Override
    public Vector3dc mulTransposeDirection(IMatrix4f mat) {
        return mulTransposeDirection(mat, this);
    }

    @Override
    public Vector3dc mulTransposeDirection(IMatrix4f mat, Vector3dc dest) {
        dest.set(mat.m00() * x + mat.m01() * y + mat.m02() * z,
                 mat.m10() * x + mat.m11() * y + mat.m12() * z,
                 mat.m20() * x + mat.m21() * y + mat.m22() * z);
        return dest;
    }

    @Override
    public Vector3dc mul(double scalar) {
        x *= scalar;
        y *= scalar;
        z *= scalar;
        return this;
    }

    @Override
    public Vector3dc mul(double scalar, Vector3dc dest) {
        dest.set(x * scalar, y * scalar, z * scalar);
        return dest;
    }

    @Override
    public Vector3dc mul(double x, double y, double z) {
        this.x *= x;
        this.y *= y;
        this.z *= z;
        return this;
    }

    @Override
    public Vector3dc mul(double x, double y, double z, Vector3dc dest) {
        dest.set(this.x * x, this.y * y, this.z * z);
        return dest;
    }

    @Override
    public Vector3dc rotate(IQuaterniond quat) {
        quat.transform(this, this);
        return this;
    }

    @Override
    public Vector3dc rotate(IQuaterniond quat, Vector3dc dest) {
        quat.transform(this, dest);
        return dest;
    }

    @Override
    public Quaterniondc rotationTo(IVector3d toDir, Quaterniondc dest) {
        return dest.rotationTo(this, toDir);
    }

    @Override
    public Quaterniondc rotationTo(double toDirX, double toDirY, double toDirZ, Quaterniondc dest) {
        return dest.rotationTo(x, y, z, toDirX, toDirY, toDirZ);
    }

    @Override
    public Vector3dc rotateAxis(double angle, double x, double y, double z) {
        return rotateAxis(angle, x, y, z, this);
    }

    @Override
    public Vector3dc rotateAxis(double angle, double aX, double aY, double aZ, Vector3dc dest) {
        double hangle = angle * 0.5;
        double sinAngle = Math.sin(hangle);
        double qx = aX * sinAngle, qy = aY * sinAngle, qz = aZ * sinAngle;
        double qw = Math.cosFromSin(sinAngle, hangle);
        double w2 = qw * qw, x2 = qx * qx, y2 = qy * qy, z2 = qz * qz, zw = qz * qw;
        double xy = qx * qy, xz = qx * qz, yw = qy * qw, yz = qy * qz, xw = qx * qw;
        double nx = (w2 + x2 - z2 - y2) * x + (-zw + xy - zw + xy) * y + (yw + xz + xz + yw) * z;
        double ny = (xy + zw + zw + xy) * x + ( y2 - z2 + w2 - x2) * y + (yz + yz - xw - xw) * z;
        double nz = (xz - yw + xz - yw) * x + ( yz + yz + xw + xw) * y + (z2 - y2 - x2 + w2) * z;
        dest.set(nx, ny, nz);
        return dest;
    }

    @Override
    public Vector3dc rotateX(double angle) {
        return rotateX(angle, this);
    }

    @Override
    public Vector3dc rotateX(double angle, Vector3dc dest) {
        double sin = Math.sin(angle), cos = Math.cosFromSin(sin, angle);
        double y = this.y * cos - this.z * sin;
        double z = this.y * sin + this.z * cos;
        dest.set(this.x, y, z);
        return dest;
    }

    @Override
    public Vector3dc rotateY(double angle) {
        return rotateY(angle, this);
    }

    @Override
    public Vector3dc rotateY(double angle, Vector3dc dest) {
        double sin = Math.sin(angle), cos = Math.cosFromSin(sin, angle);
        double x =  this.x * cos + this.z * sin;
        double z = -this.x * sin + this.z * cos;
        dest.set(x, this.y, z);
        return dest;
    }

    @Override
    public Vector3dc rotateZ(double angle) {
        return rotateZ(angle, this);
    }

    @Override
    public Vector3dc rotateZ(double angle, Vector3dc dest) {
        double sin = Math.sin(angle), cos = Math.cosFromSin(sin, angle);
        double x = this.x * cos - this.y * sin;
        double y = this.x * sin + this.y * cos;
        dest.set(x, y, this.z);
        return dest;
    }

    @Override
    public Vector3dc div(double scalar) {
        double inv = 1.0 / scalar;
        x *= inv;
        y *= inv;
        z *= inv;
        return this;
    }

    @Override
    public Vector3dc div(double scalar, Vector3dc dest) {
        double inv = 1.0 / scalar;
        dest.set(x * inv, y * inv, z * inv);
        return dest;
    }

    @Override
    public Vector3dc div(double x, double y, double z) {
        this.x /= x;
        this.y /= y;
        this.z /= z;
        return this;
    }

    @Override
    public Vector3dc div(double x, double y, double z, Vector3dc dest) {
        dest.set(this.x / x, this.y / y, this.z / z);
        return dest;
    }

    @Override
    public double lengthSquared() {
        return x * x + y * y + z * z;
    }

    @Override
    public double length() {
        return Math.sqrt(lengthSquared());
    }

    @Override
    public Vector3dc normalize() {
        double invLength = 1.0 / length();
        x *= invLength;
        y *= invLength;
        z *= invLength;
        return this;
    }

    @Override
    public Vector3dc normalize(Vector3dc dest) {
        double invLength = 1.0 / length();
        dest.set(x * invLength, y * invLength, z * invLength);
        return dest;
    }

    @Override
    public Vector3dc normalize(double length) {
        double invLength = 1.0 / length() * length;
        x *= invLength;
        y *= invLength;
        z *= invLength;
        return this;
    }

    @Override
    public Vector3dc normalize(double length, Vector3dc dest) {
        double invLength = 1.0 / length() * length;
        dest.set(x * invLength, y * invLength, z * invLength);
        return dest;
    }

    @Override
    public Vector3dc cross(IVector3d v) {
        set(y * v.z() - z * v.y(),
            z * v.x() - x * v.z(),
            x * v.y() - y * v.x());
        return this;
    }

    @Override
    public Vector3dc cross(double x, double y, double z) {
        return set(this.y * z - this.z * y,
                   this.z * x - this.x * z,
                   this.x * y - this.y * x);
    }

    @Override
    public Vector3dc cross(IVector3d v, Vector3dc dest) {
        dest.set(y * v.z() - z * v.y(),
                 z * v.x() - x * v.z(),
                 x * v.y() - y * v.x());
        return dest;
    }

    @Override
    public Vector3dc cross(double x, double y, double z, Vector3dc dest) {
        return dest.set(this.y * z - this.z * y,
                        this.z * x - this.x * z,
                        this.x * y - this.y * x);
    }

    @Override
    public double distance(IVector3d v) {
        double dx = v.x() - x;
        double dy = v.y() - y;
        double dz = v.z() - z;
        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    @Override
    public double distance(double x, double y, double z) {
        double dx = this.x - x;
        double dy = this.y - y;
        double dz = this.z - z;
        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    @Override
    public double distanceSquared(IVector3d v) {
        double dx = v.x() - x;
        double dy = v.y() - y;
        double dz = v.z() - z;
        return dx * dx + dy * dy + dz * dz;
    }

    @Override
    public double distanceSquared(double x, double y, double z) {
        double dx = this.x - x;
        double dy = this.y - y;
        double dz = this.z - z;
        return dx * dx + dy * dy + dz * dz;
    }

    @Override
    public double dot(IVector3d v) {
        return x * v.x() + y * v.y() + z * v.z();
    }

    @Override
    public double dot(double x, double y, double z) {
        return this.x * x + this.y * y + this.z * z;
    }

    @Override
    public double angleCos(IVector3d v) {
        double length1Sqared = x * x + y * y + z * z;
        double length2Sqared = v.x() * v.x() + v.y() * v.y() + v.z() * v.z();
        double dot = x * v.x() + y * v.y() + z * v.z();
        return dot / (Math.sqrt(length1Sqared * length2Sqared));
    }

    @Override
    public double angle(IVector3d v) {
        double cos = angleCos(v);
        // This is because sometimes cos goes above 1 or below -1 because of lost precision
        cos = cos < 1 ? cos : 1;
        cos = cos > -1 ? cos : -1;
        return Math.acos(cos);
    }

    @Override
    public Vector3dc min(IVector3d v) {
        this.x = x < v.x() ? x : v.x();
        this.y = y < v.y() ? y : v.y();
        this.z = z < v.z() ? z : v.z();
        return this;
    }

    @Override
    public Vector3dc max(IVector3d v) {
        this.x = x > v.x() ? x : v.x();
        this.y = y > v.y() ? y : v.y();
        this.z = z > v.z() ? z : v.z();
        return this;
    }

    @Override
    public Vector3dc zero() {
        x = 0.0;
        y = 0.0;
        z = 0.0;
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
    public Vector3dc negate() {
        x = -x;
        y = -y;
        z = -z;
        return this;
    }

    @Override
    public Vector3dc negate(Vector3dc dest) {
        dest.set(-x, -y, -z);
        return dest;
    }

    @Override
    public Vector3dc absolute() {
        this.x = Math.abs(this.x);
        this.y = Math.abs(this.y);
        this.z = Math.abs(this.z);
        return this;
    }

    @Override
    public Vector3dc absolute(Vector3dc dest) {
        dest.set(Math.abs(this.x), Math.abs(this.y), Math.abs(this.z));
        return dest;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        long temp;
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
        if (!(obj instanceof Vector3dc))
            return false;
        Vector3dc other = (Vector3dc) obj;
        if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x()))
            return false;
        if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y()))
            return false;
        if (Double.doubleToLongBits(z) != Double.doubleToLongBits(other.z()))
            return false;
        return true;
    }

    @Override
    public Vector3dc reflect(IVector3d normal) {
        double dot = this.dot(normal);
        x = x - (dot + dot) * normal.x();
        y = y - (dot + dot) * normal.y();
        z = z - (dot + dot) * normal.z();
        return this;
    }

    @Override
    public Vector3dc reflect(double x, double y, double z) {
        double dot = this.dot(x, y, z);
        this.x = this.x - (dot + dot) * x;
        this.y = this.y - (dot + dot) * y;
        this.z = this.z - (dot + dot) * z;
        return this;
    }

    @Override
    public Vector3dc reflect(IVector3d normal, Vector3dc dest) {
        double dot = this.dot(normal);
        dest.set(x - (dot + dot) * normal.x(), y - (dot + dot) * normal.y(), z - (dot + dot) * normal.z());
        return dest;
    }

    @Override
    public Vector3dc reflect(double x, double y, double z, Vector3dc dest) {
        double dot = this.dot(x, y, z);
        dest.set(this.x - (dot + dot) * x, this.y - (dot + dot) * y, this.z - (dot + dot) * z);
        return dest;
    }

    @Override
    public Vector3dc half(IVector3d other) {
        return this.add(other).normalize();
    }

    @Override
    public Vector3dc half(double x, double y, double z) {
        return this.add(x, y, z).normalize();
    }

    @Override
    public Vector3dc half(IVector3d other, Vector3dc dest) {
        return dest.set(this).add(other).normalize();
    }

    @Override
    public Vector3dc half(double x, double y, double z, Vector3dc dest) {
        return dest.set(this).add(x, y, z).normalize();
    }

    @Override
    public Vector3dc smoothStep(IVector3d v, double t, Vector3dc dest) {
        double t2 = t * t;
        double t3 = t2 * t;
        dest.set((x + x - v.x() - v.x()) * t3 + (3.0 * v.x() - 3.0 * x) * t2 + x * t + x,
                (y + y - v.y() - v.y()) * t3 + (3.0 * v.y() - 3.0 * y) * t2 + y * t + y,
                (z + z - v.z() - v.z()) * t3 + (3.0 * v.z() - 3.0 * z) * t2 + z * t + z);
        return dest;
    }

    @Override
    public Vector3dc hermite(IVector3d t0, IVector3d v1, IVector3d t1, double t, Vector3dc dest) {
        double t2 = t * t;
        double t3 = t2 * t;
        dest.set((x + x - v1.x() - v1.x() + t1.x() + t0.x()) * t3 + (3.0 * v1.x() - 3.0 * x - t0.x() - t0.x() - t1.x()) * t2 + x * t + x,
                (y + y - v1.y() - v1.y() + t1.y() + t0.y()) * t3 + (3.0 * v1.y() - 3.0 * y - t0.y() - t0.y() - t1.y()) * t2 + y * t + y,
                (z + z - v1.z() - v1.z() + t1.z() + t0.z()) * t3 + (3.0 * v1.z() - 3.0 * z - t0.z() - t0.z() - t1.z()) * t2 + z * t + z);
        return dest;
    }

    @Override
    public Vector3dc lerp(IVector3d other, double t) {
        return lerp(other, t, this);
    }

    @Override
    public Vector3dc lerp(IVector3d other, double t, Vector3dc dest) {
        dest.set(x + (other.x() - x) * t, y + (other.y() - y) * t, z + (other.z() - z) * t);
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
        default:
            throw new IllegalArgumentException();
        }
    }

    @Override
    public Vector3dc set(int component, double value) throws IllegalArgumentException {
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
        double absX = Math.abs(x);
        double absY = Math.abs(y);
        double absZ = Math.abs(z);
        if (absX >= absY && absX >= absZ) {
            return 0;
        } else if (absY >= absZ) {
            return 1;
        }
        return 2;
    }

    @Override
    public int minComponent() {
        double absX = Math.abs(x);
        double absY = Math.abs(y);
        double absZ = Math.abs(z);
        if (absX < absY && absX < absZ) {
            return 0;
        } else if (absY < absZ) {
            return 1;
        }
        return 2;
    }

    @Override
    public Vector3dc orthogonalize(IVector3d v, Vector3dc dest) {
        double invLenV = 1.0 / Math.sqrt(v.x() * v.x() + v.y() * v.y() + v.z() * v.z());
        double vx = v.x() * invLenV;
        double vy = v.y() * invLenV;
        double vz = v.z() * invLenV;
        double dot = (vx * x + vy * y + vz * z);
        double rx = x - dot * vx;
        double ry = y - dot * vy;
        double rz = z - dot * vz;
        double invLen = 1.0 / Math.sqrt(rx * rx + ry * ry + rz * rz);
        dest.set(rx * invLen, ry * invLen, rz * invLen);
        return dest;
    }

    @Override
    public Vector3dc orthogonalize(IVector3d v) {
        return orthogonalize(v, this);
    }

    @Override
    public Vector3dc orthogonalizeUnit(IVector3d v, Vector3dc dest) {
        double vx = v.x();
        double vy = v.y();
        double vz = v.z();
        double dot = (vx * x + vy * y + vz * z);
        double rx = x - dot * vx;
        double ry = y - dot * vy;
        double rz = z - dot * vz;
        double invLen = 1.0 / Math.sqrt(rx * rx + ry * ry + rz * rz);
        dest.set(rx * invLen, ry * invLen, rz * invLen);
        return dest;
    }

    @Override
    public Vector3dc orthogonalizeUnit(IVector3d v) {
        return orthogonalizeUnit(v, this);
    }
}
