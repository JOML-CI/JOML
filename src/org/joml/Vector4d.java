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
import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.text.DecimalFormat;
import java.text.NumberFormat;


/**
 * Contains the definition of a Vector comprising 4 doubles and associated transformations.
 * 
 * @author Richard Greenlees
 * @author Kai Burjack
 */
public class Vector4d implements Externalizable {

    private static final long serialVersionUID = 1L;   

    /**
     * The x-coordinate of the vector.
     */
    public double x;
    /**
     * The y-coordinate of the vector.
     */
    public double y;
    /**
     * The z-coordinate of the vector.
     */
    public double z;
    /**
     * The w-coordinate of the vector.
     */
    public double w = 1.0;

    /**
     * Create a new {@link Vector4d} of <code>(0, 0, 0, 1)</code>.
     */
    public Vector4d() {
    }

    /**
     * Create a new {@link Vector4d} with the same values as <code>v</code>.
     * 
     * @param v
     *            the {@link Vector4d} to copy the values from
     */
    public Vector4d(Vector4d v) {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
        this.w = v.w;
    }

    /**
     * Create a new {@link Vector4d} with the first three components from the
     * given <code>v</code> and the given <code>w</code>.
     * 
     * @param v
     *            the {@link Vector3d}
     * @param w
     *            the w value
     */
    public Vector4d(Vector3d v, double w) {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
        this.w = w;
    }

    /**
     * Create a new {@link Vector4d} with the first two components from the
     * given <code>v</code> and the given <code>z</code> and <code>w</code>.
     *
     * @param v
     *            the {@link Vector2d}
     * @param z
     *            the z value
     * @param w
     *            the w value
     */
    public Vector4d(Vector2d v, double z, double w) {
        this.x = v.x;
        this.y = v.y;
        this.z = z;
        this.w = w;
    }

    /**
     * Create a new {@link Vector4d} with the same values as <code>v</code>.
     * 
     * @param v
     *            the {@link Vector4f} to copy the values from
     */
    public Vector4d(Vector4f v) {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
        this.w = v.w;
    }

    /**
     * Create a new {@link Vector4d} with the first three components from the
     * given <code>v</code> and the given <code>w</code>.
     * 
     * @param v
     *            the {@link Vector3f}
     * @param w
     *            the w value
     */
    public Vector4d(Vector3f v, double w) {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
        this.w = w;
    }

    /**
     * Create a new {@link Vector4d} with the first two components from the
     * given <code>v</code> and the given <code>z</code> and <code>w</code>.
     *
     * @param v
     *            the {@link Vector2f}
     * @param z
     *            the z value
     * @param w
     *            the w value
     */
    public Vector4d(Vector2f v, double z, double w) {
        this.x = v.x;
        this.y = v.y;
        this.z = z;
        this.w = w;
    }

    /**
     * Create a new {@link Vector4f} with the given component values.
     * 
     * @param x
     *          the value of x
     * @param y
     *          the value of y
     * @param z
     *          the value of z
     * @param w
     *          the value of w
     */
    public Vector4d(double x, double y, double z, double w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    /**
     * Create a new {@link Vector4f} and read this vector from the supplied {@link ByteBuffer}
     * at the current buffer {@link ByteBuffer#position() position}.
     * <p>
     * This method will not increment the position of the given ByteBuffer.
     * <p>
     * If you want to specify the offset into the ByteBuffer at which
     * the vector is read, you can use {@link #Vector4d(int, ByteBuffer)}, taking
     * the absolute position as parameter.
     *
     * @param buffer values will be read in <tt>x, y, z, w</tt> order
     * @see #Vector4d(int, ByteBuffer)
     */
    public Vector4d(ByteBuffer buffer) {
        this(buffer.position(), buffer);
    }

    /**
     * Create a new {@link Vector4f} and read this vector from the supplied {@link ByteBuffer}
     * starting at the specified absolute buffer position/index.
     * <p>
     * This method will not increment the position of the given ByteBuffer.
     *
     * @param index  the absolute position into the ByteBuffer
     * @param buffer values will be read in <tt>x, y, z, w</tt> order
     */
    public Vector4d(int index, ByteBuffer buffer) {
        x = buffer.getDouble(index);
        y = buffer.getDouble(index + 8);
        z = buffer.getDouble(index + 16);
        w = buffer.getDouble(index + 24);
    }

    /**
     * Create a new {@link Vector4f} and read this vector from the supplied {@link DoubleBuffer}
     * at the current buffer {@link DoubleBuffer#position() position}.
     * <p>
     * This method will not increment the position of the given DoubleBuffer.
     * <p>
     * If you want to specify the offset into the DoubleBuffer at which
     * the vector is read, you can use {@link #Vector4d(int, DoubleBuffer)}, taking
     * the absolute position as parameter.
     *
     * @param buffer values will be read in <tt>x, y, z, w</tt> order
     * @see #Vector4d(int, DoubleBuffer)
     */
    public Vector4d(DoubleBuffer buffer) {
        this(buffer.position(), buffer);
    }

    /**
     * Create a new {@link Vector4f} and read this vector from the supplied {@link DoubleBuffer}
     * starting at the specified absolute buffer position/index.
     * <p>
     * This method will not increment the position of the given DoubleBuffer.
     *
     * @param index  the absolute position into the DoubleBuffer
     * @param buffer values will be read in <tt>x, y, z, w</tt> order
     */
    public Vector4d(int index, DoubleBuffer buffer) {
        x = buffer.get(index);
        y = buffer.get(index + 1);
        z = buffer.get(index + 2);
        w = buffer.get(index + 3);
    }

    /**
     * Set this {@link Vector4d} to the values of the given <code>v</code>.
     * 
     * @param v
     *            the vector whose values will be copied into this
     * @return this
     */
    public Vector4d set(Vector4d v) {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
        this.w = v.w;
        return this;
    }

    /**
     * Set this {@link Vector4d} to the values of the given <code>v</code>.
     * 
     * @param v
     *            the vector whose values will be copied into this
     * @return this
     */
    public Vector4d set(Vector4f v) {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
        this.w = v.w;
        return this;
    }

    /**
     * Set the first three components of this to the components of
     * <code>v</code> and the last component to <code>w</code>.
     * 
     * @param v
     *            the {@link Vector3d} to copy
     * @param w
     *            the w component
     * @return this
     */
    public Vector4d set(Vector3d v, double w) {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
        this.w = w;
        return this;
    }

    /**
     * Set the first three components of this to the components of
     * <code>v</code> and the last component to <code>w</code>.
     * 
     * @param v
     *            the {@link Vector3f} to copy
     * @param w
     *            the w component
     * @return this
     */
    public Vector4d set(Vector3f v, double w) {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
        this.w = w;
        return this;
    }

    /**
     * Set the first two components from the given <code>v</code>
     * and the last two components from the given <code>z</code> and <code>w</code>.
     *
     * @param v
     *            the {@link Vector2d}
     * @param z
     *            the z value
     * @param w
     *            the w value
     * @return this
     */
    public Vector4d set(Vector2d v, double z, double w) {
        this.x = v.x;
        this.y = v.y;
        this.z = z;
        this.w = w;
        return this;
    }
    
    /**
     * Set the first two components from the given <code>v</code>
     * and the last two components from the given <code>z</code> and <code>w</code>.
     *
     * @param v the {@link Vector2f}
     * @param z the z value
     * @param w the w value
     * @return this
     */
    public Vector4d set(Vector2f v, double z, double w) {
        this.x = v.x;
        this.y = v.y;
        this.z = z;
        this.w = w;
        return this;
    }

    /**
     * Set the components of this vector to the given values.
     * 
     * @param x
     *            the x-component
     * @param y
     *            the y-component
     * @param z
     *            the z-component
     * @param w
     *            the w component
     * @return this
     */
    public Vector4d set(double x, double y, double z, double w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
        return this;
    }

    /**
     * Read this vector from the supplied {@link ByteBuffer} at the current
     * buffer {@link ByteBuffer#position() position}.
     * <p>
     * This method will not increment the position of the given ByteBuffer.
     * <p>
     * If you want to specify the offset into the ByteBuffer at which
     * the vector is read, you can use {@link #set(int, ByteBuffer)}, taking
     * the absolute position as parameter.
     *
     * @param buffer values will be read in <tt>x, y, z, w</tt> order
     * @return this
     * @see #set(int, ByteBuffer)
     */
    public Vector4d set(ByteBuffer buffer) {
        return set(buffer.position(), buffer);
    }

    /**
     * Read this vector from the supplied {@link ByteBuffer} starting at the specified
     * absolute buffer position/index.
     * <p>
     * This method will not increment the position of the given ByteBuffer.
     *
     * @param index  the absolute position into the ByteBuffer
     * @param buffer values will be read in <tt>x, y, z, w</tt> order
     * @return this
     */
    public Vector4d set(int index, ByteBuffer buffer) {
        x = buffer.getDouble(index);
        y = buffer.getDouble(index + 8);
        z = buffer.getDouble(index + 16);
        w = buffer.getDouble(index + 24);
        return this;
    }

    /**
     * Read this vector from the supplied {@link DoubleBuffer} at the current
     * buffer {@link DoubleBuffer#position() position}.
     * <p>
     * This method will not increment the position of the given DoubleBuffer.
     * <p>
     * If you want to specify the offset into the DoubleBuffer at which
     * the vector is read, you can use {@link #set(int, DoubleBuffer)}, taking
     * the absolute position as parameter.
     *
     * @param buffer values will be read in <tt>x, y, z, w</tt> order
     * @return this
     * @see #set(int, DoubleBuffer)
     */
    public Vector4d set(DoubleBuffer buffer) {
        return set(buffer.position(), buffer);
    }

    /**
     * Read this vector from the supplied {@link DoubleBuffer} starting at the specified
     * absolute buffer position/index.
     * <p>
     * This method will not increment the position of the given DoubleBuffer.
     *
     * @param index  the absolute position into the DoubleBuffer
     * @param buffer values will be read in <tt>x, y, z, w</tt> order
     * @return this
     */
    public Vector4d set(int index, DoubleBuffer buffer) {
        x = buffer.get(index);
        y = buffer.get(index + 1);
        z = buffer.get(index + 2);
        w = buffer.get(index + 3);
        return this;
    }

    /**
     * Store this vector into the supplied {@link ByteBuffer} at the current
     * buffer {@link ByteBuffer#position() position}.
     * <p>
     * This method will not increment the position of the given ByteBuffer.
     * <p>
     * If you want to specify the offset into the ByteBuffer at which
     * the vector is stored, you can use {@link #get(int, ByteBuffer)}, taking
     * the absolute position as parameter.
     *
     * @param buffer will receive the values of this vector in <tt>x, y, z, w</tt> order
     * @return this
     * @see #get(int, ByteBuffer)
     */
    public Vector4d get(ByteBuffer buffer) {
        return get(buffer.position(), buffer);
    }

    /**
     * Store this vector into the supplied {@link ByteBuffer} starting at the specified
     * absolute buffer position/index.
     * <p>
     * This method will not increment the position of the given ByteBuffer.
     *
     * @param index  the absolute position into the ByteBuffer
     * @param buffer will receive the values of this vector in <tt>x, y, z, w</tt> order
     * @return this
     */
    public Vector4d get(int index, ByteBuffer buffer) {
        buffer.putDouble(index,      x);
        buffer.putDouble(index + 8,  y);
        buffer.putDouble(index + 16,  z);
        buffer.putDouble(index + 24,  w);
        return this;
    }

    /**
     * Store this vector into the supplied {@link DoubleBuffer} at the current
     * buffer {@link DoubleBuffer#position() position}.
     * <p>
     * This method will not increment the position of the given DoubleBuffer.
     * <p>
     * If you want to specify the offset into the DoubleBuffer at which
     * the vector is stored, you can use {@link #get(int, DoubleBuffer)}, taking
     * the absolute position as parameter.
     *
     * @param buffer will receive the values of this vector in <tt>x, y, z, w</tt> order
     * @return this
     * @see #get(int, DoubleBuffer)
     */
    public Vector4d get(DoubleBuffer buffer) {
        return get(buffer.position(), buffer);
    }

    /**
     * Store this vector into the supplied {@link DoubleBuffer} starting at the specified
     * absolute buffer position/index.
     * <p>
     * This method will not increment the position of the given DoubleBuffer.
     *
     * @param index  the absolute position into the DoubleBuffer
     * @param buffer will receive the values of this vector in <tt>x, y, z, w</tt> order
     * @return this
     */
    public Vector4d get(int index, DoubleBuffer buffer) {
        buffer.put(index,      x);
        buffer.put(index + 1,  y);
        buffer.put(index + 2,  z);
        buffer.put(index + 3,  w);
        return this;
    }

    /**
     * Subtract the supplied vector from this one.
     * 
     * @param v
     *          the vector to subtract
     * @return this
     */
    public Vector4d sub(Vector4d v) {
        x -= v.x;
        y -= v.y;
        z -= v.z;
        w -= v.w;
        return this;
    }

    /**
     * Subtract the supplied vector from this one.
     * 
     * @param v
     *          the vector to subtract
     * @return this
     */
    public Vector4d sub(Vector4f v) {
        x -= v.x;
        y -= v.y;
        z -= v.z;
        w -= v.w;
        return this;
    }

    /**
     * Subtract <tt>(x, y, z, w)</tt> from this.
     * 
     * @param x
     *          the x-coordinate to subtract
     * @param y
     *          the y-coordinate to subtract
     * @param z
     *          the z-coordinate to subtract
     * @param w
     *          the w-coordinate to subtract
     * @return this
     */
    public Vector4d sub(double x, double y, double z, double w) {
        this.x -= x;
        this.y -= y;
        this.z -= z;
        this.w -= w;
        return this;
    }

    /**
     * Subtract <tt>(x, y, z, w)</tt> from this and store the result in <code>dest</code>.
     * 
     * @param x
     *          the x-coordinate to subtract
     * @param y
     *          the y-coordinate to subtract
     * @param z
     *          the z-coordinate to subtract
     * @param w
     *          the w-coordinate to subtract
     * @param dest
     *          will hold the result
     * @return this
     */
    public Vector4d sub(double x, double y, double z, double w, Vector4d dest) {
        dest.x = this.x - x;
        dest.y = this.y - y;
        dest.z = this.z - z;
        dest.w = this.w - w;
        return this;
    }

    /**
     * Subtract <code>v2</code> from <code>v1</code> and store the result in <code>dest</code>.
     * 
     * @param v1
     *          the left operand
     * @param v2
     *          the right operand
     * @param dest
     *          will hold the result
     */
    public static void sub(Vector4d v1, Vector4d v2, Vector4d dest) {
        dest.x = v1.x - v2.x;
        dest.y = v1.y - v2.y;
        dest.z = v1.z - v2.z;
        dest.w = v1.w - v2.w;
    }

    /**
     * Subtract <code>v2</code> from <code>v1</code> and store the result in <code>dest</code>.
     * 
     * @param v1
     *          the left operand
     * @param v2
     *          the right operand
     * @param dest
     *          will hold the result
     */
    public static void sub(Vector4d v1, Vector4f v2, Vector4d dest) {
        dest.x = v1.x - v2.x;
        dest.y = v1.y - v2.y;
        dest.z = v1.z - v2.z;
        dest.w = v1.w - v2.w;
    }

    /**
     * Subtract <code>v2</code> from <code>v1</code> and store the result in <code>dest</code>.
     * 
     * @param v1
     *          the left operand
     * @param v2
     *          the right operand
     * @param dest
     *          will hold the result
     */
    public static void sub(Vector4f v1, Vector4d v2, Vector4d dest) {
        dest.x = v1.x - v2.x;
        dest.y = v1.y - v2.y;
        dest.z = v1.z - v2.z;
        dest.w = v1.w - v2.w;
    }

    /**
     * Add the supplied vector to this one.
     * 
     * @param v
     *          the vector to add
     * @return this
     */
    public Vector4d add(Vector4d v) {
        x += v.x;
        y += v.y;
        z += v.z;
        w += v.w;
        return this;
    }

    /**
     * Add <tt>(x, y, z, w)</tt> to this.
     * 
     * @param x
     *          the x-coordinate to subtract
     * @param y
     *          the y-coordinate to subtract
     * @param z
     *          the z-coordinate to subtract
     * @param w
     *          the w-coordinate to subtract
     * @return this
     */
    public Vector4d add(double x, double y, double z, double w) {
        this.x += x;
        this.y += y;
        this.z += z;
        this.w += w;
        return this;
    }

    /**
     * Add <tt>(x, y, z, w)</tt> to this and store the result in <code>dest</code>.
     * 
     * @param x
     *          the x-coordinate to subtract
     * @param y
     *          the y-coordinate to subtract
     * @param z
     *          the z-coordinate to subtract
     * @param w
     *          the w-coordinate to subtract
     * @param dest
     *          will hold the result
     * @return this
     */
    public Vector4d add(double x, double y, double z, double w, Vector4d dest) {
        dest.x = this.x - x;
        dest.y = this.y - y;
        dest.z = this.z - z;
        dest.w = this.w - w;
        return this;
    }

    /**
     * Add the supplied vector to this one.
     * 
     * @param v
     *          the vector to add
     * @return this
     */
    public Vector4d add(Vector4f v) {
        x += v.x;
        y += v.y;
        z += v.z;
        w += v.w;
        return this;
    }

    /**
     * Add <code>v2</code> to <code>v1</code> and store the result in <code>dest</code>.
     * 
     * @param v1
     *          the first addend
     * @param v2
     *          the second addend
     * @param dest
     *          will hold the result
     */
    public static void add(Vector4d v1, Vector4d v2, Vector4d dest) {
        dest.x = v1.x + v2.x;
        dest.y = v1.y + v2.y;
        dest.z = v1.z + v2.z;
        dest.w = v1.w + v2.w;
    }

    /**
     * Add <code>v2</code> to <code>v1</code> and store the result in <code>dest</code>.
     * 
     * @param v1
     *          the first addend
     * @param v2
     *          the second addend
     * @param dest
     *          will hold the result
     */
    public static void add(Vector4d v1, Vector4f v2, Vector4d dest) {
        dest.x = v1.x + v2.x;
        dest.y = v1.y + v2.y;
        dest.z = v1.z + v2.z;
        dest.w = v1.w + v2.w;
    }

    /**
     * Add <code>v2</code> to <code>v1</code> and store the result in <code>dest</code>.
     * 
     * @param v1
     *          the first addend
     * @param v2
     *          the second addend
     * @param dest
     *          will hold the result
     */
    public static void add(Vector4f v1, Vector4d v2, Vector4d dest) {
        dest.x = v1.x + v2.x;
        dest.y = v1.y + v2.y;
        dest.z = v1.z + v2.z;
        dest.w = v1.w + v2.w;
    }

    /**
     * Add the component-wise multiplication of <code>a * b</code> to this vector.
     * 
     * @param a
     *          the first multiplicand
     * @param b
     *          the second multiplicand
     * @return this
     */
    public Vector4d fma(Vector4d a, Vector4d b) {
        x += a.x * b.x;
        y += a.y * b.y;
        z += a.z * b.z;
        w += a.w * b.w;
        return this;
    }

    /**
     * Add the component-wise multiplication of <code>a * b</code> to this vector.
     * 
     * @param a
     *          the first multiplicand
     * @param b
     *          the second multiplicand
     * @return this
     */
    public Vector4d fma(double a, Vector4d b) {
        x += a * b.x;
        y += a * b.y;
        z += a * b.z;
        w += a * b.w;
        return this;
    }

    /**
     * Add the component-wise multiplication of <code>a * b</code> to this vector
     * and store the result in <code>dest</code>.
     * 
     * @param a
     *          the first multiplicand
     * @param b
     *          the second multiplicand
     * @param dest
     *          will hold the result
     * @return this
     */
    public Vector4d fma(Vector4d a, Vector4d b, Vector4d dest) {
        dest.x = x + a.x * b.x;
        dest.y = y + a.y * b.y;
        dest.z = z + a.z * b.z;
        dest.w = w + a.w * b.w;
        return this;
    }

    /**
     * Add the component-wise multiplication of <code>a * b</code> to this vector
     * and store the result in <code>dest</code>.
     * 
     * @param a
     *          the first multiplicand
     * @param b
     *          the second multiplicand
     * @param dest
     *          will hold the result
     * @return this
     */
    public Vector4d fma(double a, Vector4d b, Vector4d dest) {
        dest.x = x + a * b.x;
        dest.y = y + a * b.y;
        dest.z = z + a * b.z;
        dest.w = w + a * b.w;
        return this;
    }

    /**
     * Multiply this {@link Vector4d} component-wise by the given {@link Vector4d}.
     * 
     * @param v
     *          the vector to multiply by
     * @return this
     */
    public Vector4d mul(Vector4d v) {
        x *= v.x;
        y *= v.y;
        z *= v.z;
        z *= v.w;
        return this;
    }

    /**
     * Multiply this {@link Vector4d} component-wise by the given {@link Vector4d} and store the result in <code>dest</code>.
     * 
     * @param v
     * 			the vector to multiply this by
     * @param dest
     * 			will hold the result
     * @return this
     */
    public Vector4d mul(Vector4d v, Vector4d dest) {
        dest.x = x * v.x;
        dest.y = y * v.y;
        dest.z = z * v.z;
        dest.w = w * v.w;
        return this;
    }

    /**
     * Divide this {@link Vector4d} component-wise by the given {@link Vector4d}.
     * 
     * @param v
     *          the vector to divide by
     * @return this
     */
    public Vector4d div(Vector4d v) {
        x /= v.x;
        y /= v.y;
        z /= v.z;
        z /= v.w;
        return this;
    }

    /**
     * Divide this {@link Vector4d} component-wise by the given {@link Vector4d} and store the result in <code>dest</code>.
     * 
     * @param v
     * 			the vector to divide this by
     * @param dest
     * 			will hold the result
     * @return this
     */
    public Vector4d div(Vector4d v, Vector4d dest) {
        dest.x = x / v.x;
        dest.y = y / v.y;
        dest.z = z / v.z;
        dest.w = w / v.w;
        return this;
    }

    /**
     * Multiply this {@link Vector4d} component-wise by the given {@link Vector4f}.
     * 
     * @param v
     *          the vector to multiply by
     * @return this
     */
    public Vector4d mul(Vector4f v) {
        x *= v.x;
        y *= v.y;
        z *= v.z;
        z *= v.w;
        return this;
    }

    /**
     * Multiply this {@link Vector4d} by the given matrix <code>mat</code>.
     * 
     * @param mat
     *          the matrix to multiply by
     * @return this
     */
    public Vector4d mul(Matrix4d mat) {
        return mul(mat, this);
    }

    /**
     * Multiply this {@link Vector4d} by the given matrix mat and store the result in <code>dest</code>.
     * 
     * @param mat
     *          the matrix to multiply <code>this</code> by
     * @param dest
     *          will hold the result
     * @return this
     */
    public Vector4d mul(Matrix4d mat, Vector4d dest) {
        if (this != dest) {
            dest.x = mat.m00 * x + mat.m10 * y + mat.m20 * z + mat.m30 * w;
            dest.y = mat.m01 * x + mat.m11 * y + mat.m21 * z + mat.m31 * w;
            dest.z = mat.m02 * x + mat.m12 * y + mat.m22 * z + mat.m32 * w;
            dest.w = mat.m03 * x + mat.m13 * y + mat.m23 * z + mat.m33 * w;  
        } else {
            dest.set(mat.m00 * x + mat.m10 * y + mat.m20 * z + mat.m30 * w,
                     mat.m01 * x + mat.m11 * y + mat.m21 * z + mat.m31 * w,
                     mat.m02 * x + mat.m12 * y + mat.m22 * z + mat.m32 * w, 
                     mat.m03 * x + mat.m13 * y + mat.m23 * z + mat.m33 * w);
        }
        return this;
    }

    /**
     * Multiply this {@link Vector4d} by the given matrix <code>mat</code>.
     * 
     * @param mat
     *          the matrix to multiply by
     * @return this
     */
    public Vector4d mul(Matrix4f mat) {
        return mul(mat, this);
    }

    /**
     * Multiply this Vector4d by the given matrix mat and store the result in <code>dest</code>.
     *
     * @param mat
     *          the matrix to multiply <code>this</code> by
     * @param dest
     *          will hold the result
     * @return this
     */
    public Vector4d mul(Matrix4f mat, Vector4d dest) {
        if (this != dest) {
            dest.x = mat.m00 * x + mat.m10 * y + mat.m20 * z + mat.m30 * w;
            dest.y = mat.m01 * x + mat.m11 * y + mat.m21 * z + mat.m31 * w;
            dest.z = mat.m02 * x + mat.m12 * y + mat.m22 * z + mat.m32 * w;
            dest.w = mat.m03 * x + mat.m13 * y + mat.m23 * z + mat.m33 * w;
        } else {
            dest.set(mat.m00 * x + mat.m10 * y + mat.m20 * z + mat.m30 * w,
                     mat.m01 * x + mat.m11 * y + mat.m21 * z + mat.m31 * w,
                     mat.m02 * x + mat.m12 * y + mat.m22 * z + mat.m32 * w, 
                     mat.m03 * x + mat.m13 * y + mat.m23 * z + mat.m33 * w);
        }
        return this;
    }

    /**
     * Multiply this Vector4d by the given scalar value.
     * 
     * @param scalar
     *          the scalar to multiply by
     * @return this
     */
    public Vector4d mul(double scalar) {
        x *= scalar;
        y *= scalar;
        z *= scalar;
        w *= scalar;
        return this;
    }

    /**
     * Multiply this Vector4d by the given scalar value and store the result in <code>dest</code>.
     * 
     * @param scalar
     *              the factor to multiply by
     * @param dest
     *              will hold the result
     * @return this
     */
    public Vector4d mul(double scalar, Vector4d dest) {
        x *= scalar;
        y *= scalar;
        z *= scalar;
        w *= scalar;
        return this;
    }

    /**
     * Divide this Vector4d by the given scalar value.
     * 
     * @param scalar
     *          the scalar to divide by
     * @return this
     */
    public Vector4d div(double scalar) {
        x /= scalar;
        y /= scalar;
        z /= scalar;
        w /= scalar;
        return this;
    }

    /**
     * Divide this Vector4d by the given scalar value and store the result in <code>dest</code>.
     * 
     * @param scalar
     *              the factor to divide by
     * @param dest
     *              will hold the result
     * @return this
     */
    public Vector4d div(double scalar, Vector4d dest) {
        x /= scalar;
        y /= scalar;
        z /= scalar;
        w /= scalar;
        return this;
    }

    /**
     * Transform this vector by the given quaternion <code>quat</code> and store the result in <code>this</code>.
     * 
     * @see Quaterniond#transform(Vector4d)
     * 
     * @param quat
     *          the quaternion to transform this vector
     * @return this
     */
    public Vector4d rotate(Quaterniond quat) {
        return rotate(quat, this);
    }

    /**
     * Transform this vector by the given quaternion <code>quat</code> and store the result in <code>dest</code>.
     * 
     * @see Quaterniond#transform(Vector4d)
     * 
     * @param quat
     *          the quaternion to transform this vector
     * @param dest
     *          will hold the result
     * @return this
     */
    public Vector4d rotate(Quaterniond quat, Vector4d dest) {
        quat.transform(this, dest);
        return this;
    }

    /**
     * Return the length squared of this vector.
     * 
     * @return the length squared
     */
    public double lengthSquared() {
        return x * x + y * y + z * z + w * w;
    }

    /**
     * Return the length of this vector.
     * 
     * @return the length
     */
    public double length() {
        return Math.sqrt(lengthSquared());
    }

    /**
     * Normalizes this vector.
     * 
     * @return this
     */
    public Vector4d normalize() {
        double d = length();
        x /= d;
        y /= d;
        z /= d;
        w /= d;
        return this;
    }

    /**
     * Normalizes this vector and store the result in <code>dest</code>.
     * 
     * @param dest
     *          will hold the result
     * @return this
     */
    public Vector4d normalize(Vector4d dest) {
        double d = length();
        dest.x = x / d;
        dest.y = y / d;
        dest.z = z / d;
        dest.w = w / d;
        return this;
    }

    /**
     * Normalize this vector by computing only the norm of <tt>(x, y, z)</tt>.
     * 
     * @return this
     */
    public Vector4d normalize3() {
        double d = Math.sqrt(x * x + y * y + z * z);
        x /= d;
        y /= d;
        z /= d;
        w /= d;
        return this;
    }

    /**
     * Normalize this vector by computing only the norm of <tt>(x, y, z)</tt> and store the result in <code>dest</code>.
     * 
     * @param dest
     *          will hold the result
     * @return this
     */
    public Vector4d normalize3(Vector4d dest) {
        double d = Math.sqrt(x * x + y * y + z * z);
        dest.x = x / d;
        dest.y = y / d;
        dest.z = z / d;
        dest.w = w / d;
        return this;
    }

    /**
     * Return the distance between <code>this</code> vector and <code>v</code>.
     * 
     * @param v
     *          the other vector
     * @return the euclidean distance
     */
    public double distance(Vector4d v) {
        return Math.sqrt(
                (v.x - x) * (v.x - x)
              + (v.y - y) * (v.y - y)
              + (v.z - z) * (v.z - z)
              + (v.w - w) * (v.w - w));
    }

    /**
     * Return the distance between <code>this</code> vector and <tt>(x, y, z, w)</tt>.
     * 
     * @param x
     *            the x-coordinate of the other vector
     * @param y
     *            the y-coordinate of the other vector
     * @param z
     *            the z-coordinate of the other vector
     * @param w
     *            the w-coordinate of the other vector
     * @return the euclidean distance
     */
    public double distance(double x, double y, double z, double w) {
        return Math.sqrt(
                (x - this.x) * (x - this.x)
              + (y - this.y) * (y - this.y)
              + (z - this.z) * (z - this.z)
              + (w - this.w) * (w - this.w));
    }

    /**
     * Compute the dot product (inner product) of this vector and <code>v</code>.
     * 
     * @param v
     *            the other vector
     * @return the dot product
     */
    public double dot(Vector4d v) {
        return x * v.x + y * v.y + z * v.z + w * v.w;
    }

    /**
     * Compute the dot product (inner product) of this vector and <tt>(x, y, z, w)</tt>.
     * 
     * @param x
     *            the x-coordinate of the other vector
     * @param y
     *            the y-coordinate of the other vector
     * @param z
     *            the z-coordinate of the other vector
     * @param w
     *            the w-coordinate of the other vector
     * @return the dot product
     */
    public double dot(double x, double y, double z, double w) {
        return this.x * x + this.y * y + this.z * z + this.w * w;
    }

    /**
     * Return the cosine of the angle between this vector and the supplied vector.
     * <p>
     * Use this instead of <code>Math.cos(angle(v))</code>.
     * 
     * @see #angle(Vector4d)
     * 
     * @param v
     *          the other vector
     * @return the cosine of the angle
     */
    public double angleCos(Vector4d v) {
        double length1 = Math.sqrt(x * x + y * y + z * z + w * w);
        double length2 = Math.sqrt(v.x * v.x + v.y * v.y + v.z * v.z + v.w * v.w);
        double dot = x * v.x + y * v.y + z * v.z + w * v.w;
        return dot / (length1 * length2);
    }

    /**
     * Return the angle between this vector and the supplied vector.
     * 
     * @see #angleCos(Vector4d)
     * 
     * @param v
     *          the other vector
     * @return the angle, in radians
     */
    public double angle(Vector4d v) {
        double cos = angleCos(v);
        // This is because sometimes cos goes above 1 or below -1 because of lost precision
        cos = Math.min(cos, 1);
        cos = Math.max(cos, -1);
        return Math.acos(cos);
    }

    /**
     * Set all components to zero.
     * 
     * @return this
     */
    public Vector4d zero() {
        this.x = 0.0;
        this.y = 0.0;
        this.z = 0.0;
        this.w = 0.0;
        return this;
    }

    /**
     * Negate this vector.
     * 
     * @return this
     */
    public Vector4d negate() {
        x = -x;
        y = -y;
        z = -z;
        w = -w;
        return this;
    }

    /**
     * Negate this vector and store the result in <code>dest</code>.
     * 
     * @param dest
     *          will hold the result
     * @return this
     */
    public Vector4d negate(Vector4d dest) {
        dest.x = -x;
        dest.y = -y;
        dest.z = -z;
        dest.w = -w;
        return this;
    }

    /**
     * Return a string representation of this vector.
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
     * Return a string representation of this vector by formatting the vector components with the given {@link NumberFormat}.
     * 
     * @param formatter
     *          the {@link NumberFormat} used to format the vector components with
     * @return the string representation
     */
    public String toString(NumberFormat formatter) {
        return "(" + formatter.format(x) + " " + formatter.format(y) + " " + formatter.format(z) + " " + formatter.format(w) + ")"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeDouble(x);
        out.writeDouble(y);
        out.writeDouble(z);
    }

    public void readExternal(ObjectInput in) throws IOException,
            ClassNotFoundException {
        x = in.readDouble();
        y = in.readDouble();
        z = in.readDouble();
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
        if (getClass() != obj.getClass())
            return false;
        Vector4d other = (Vector4d) obj;
        if (Double.doubleToLongBits(w) != Double.doubleToLongBits(other.w))
            return false;
        if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
            return false;
        if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y))
            return false;
        if (Double.doubleToLongBits(z) != Double.doubleToLongBits(other.z))
            return false;
        return true;
    }

    /**
     * Compute a smooth-step (i.e. hermite with zero tangents) interpolation
     * between <code>this</code> vector and the given vector <code>v</code> and
     * store the result in <code>dest</code>.
     * 
     * @param v
     *            the other vector
     * @param t
     *            the interpolation factor, within <tt>[0..1]</tt>
     * @param dest
     *            will hold the result
     * @return this
     */
    public Vector4d smoothStep(Vector4d v, double t, Vector4d dest) {
        dest.x = Interpolate.smoothStep(x, v.x, t);
        dest.y = Interpolate.smoothStep(y, v.y, t);
        dest.z = Interpolate.smoothStep(x, v.z, t);
        dest.w = Interpolate.smoothStep(w, v.w, t);
        return this;
    }

    /**
     * Compute a hermite interpolation between <code>this</code> vector and its
     * associated tangent <code>t0</code> and the given vector <code>v</code>
     * with its tangent <code>t1</code> and store the result in
     * <code>dest</code>.
     * 
     * @param t0
     *            the tangent of <code>this</code> vector
     * @param v1
     *            the other vector
     * @param t1
     *            the tangent of the other vector
     * @param t
     *            the interpolation factor, within <tt>[0..1]</tt>
     * @param dest
     *            will hold the result
     * @return this
     */
    public Vector4d hermite(Vector4d t0, Vector4d v1, Vector4d t1, double t, Vector4d dest) {
        dest.x = Interpolate.hermite(x, t0.x, v1.x, t1.x, t);
        dest.y = Interpolate.hermite(y, t0.y, v1.y, t1.y, t);
        dest.z = Interpolate.hermite(z, t0.z, v1.z, t1.z, t);
        dest.w = Interpolate.hermite(z, t0.w, v1.w, t1.w, t);
        return this;
    }

}
