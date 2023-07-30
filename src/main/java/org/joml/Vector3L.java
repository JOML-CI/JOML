/*
 * The MIT License
 *
 * Copyright (c) 2023 JOML
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.joml;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
//#ifdef __HAS_NIO__
import java.nio.ByteBuffer;
import java.nio.LongBuffer;
//#endif
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Contains the definition of a vector comprising 3 longs and associated
 * transformations.
 *
 * @author Kai Burjack
 */
public class Vector3L implements Externalizable, Cloneable, Vector3Lc {

    private static final long serialVersionUID = 1L;

    /**
     * The x component of the vector.
     */
    public long x;
    /**
     * The y component of the vector.
     */
    public long y;
    /**
     * The z component of the vector.
     */
    public long z;

    /**
     * Create a new {@link Vector3L} of <code>(0, 0, 0)</code>.
     */
    public Vector3L() {
    }

    /**
     * Create a new {@link Vector3L} and initialize all three components with
     * the given value.
     *
     * @param d
     *          the value of all three components
     */
    public Vector3L(int d) {
        this.x = d;
        this.y = d;
        this.z = d;
    }

    /**
     * Create a new {@link Vector3L} with the given component values.
     *
     * @param x
     *          the value of x
     * @param y
     *          the value of y
     * @param z
     *          the value of z
     */
    public Vector3L(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Create a new {@link Vector3L} with the same values as <code>v</code>.
     *
     * @param v
     *          the {@link Vector3Lc} to copy the values from
     */
    public Vector3L(Vector3Lc v) {
        this.x = v.x();
        this.y = v.y();
        this.z = v.z();
    }

    /**
     * Create a new {@link Vector3L} with the first two components from the
     * given <code>v</code> and the given <code>z</code>
     *
     * @param v
     *          the {@link Vector2ic} to copy the values from
     * @param z
     *          the z component
     */
    public Vector3L(Vector2ic v, int z) {
        this.x = v.x();
        this.y = v.y();
        this.z = z;
    }

    /**
     * Create a new {@link Vector3L} with the given component values and
     * round using the given {@link RoundingMode}.
     *
     * @param x
     *          the value of x
     * @param y
     *          the value of y
     * @param z
     *          the value of z
     * @param mode
     *          the {@link RoundingMode} to use
     */
    public Vector3L(float x, float y, float z, int mode) {
        this.x = Math.roundLongUsing(x, mode);
        this.y = Math.roundLongUsing(y, mode);
        this.z = Math.roundLongUsing(z, mode);
    }

    /**
     * Create a new {@link Vector3L} with the given component values and
     * round using the given {@link RoundingMode}.
     *
     * @param x
     *          the value of x
     * @param y
     *          the value of y
     * @param z
     *          the value of z
     * @param mode
     *          the {@link RoundingMode} to use
     */
    public Vector3L(double x, double y, double z, int mode) {
        this.x = Math.roundLongUsing(x, mode);
        this.y = Math.roundLongUsing(y, mode);
        this.z = Math.roundLongUsing(z, mode);
    }

    /**
     * Create a new {@link Vector3L} with the first two components from the
     * given <code>v</code> and the given <code>z</code> and round using the given {@link RoundingMode}.
     *
     * @param v
     *          the {@link Vector2fc} to copy the values from
     * @param z
     *          the z component
     * @param mode
     *          the {@link RoundingMode} to use
     */
    public Vector3L(Vector2fc v, float z, int mode) {
        this.x = Math.roundLongUsing(v.x(), mode);
        this.y = Math.roundLongUsing(v.y(), mode);
        this.z = Math.roundLongUsing(z, mode);
    }

    /**
     * Create a new {@link Vector3L} and initialize its components to the rounded value of
     * the given vector.
     *
     * @param v
     *          the {@link Vector3fc} to round and copy the values from
     * @param mode
     *          the {@link RoundingMode} to use
     */
    public Vector3L(Vector3fc v, int mode) {
        this.x = Math.roundLongUsing(v.x(), mode);
        this.y = Math.roundLongUsing(v.y(), mode);
        this.z = Math.roundLongUsing(v.z(), mode);
    }

    /**
     * Create a new {@link Vector3L} with the first two components from the
     * given <code>v</code> and the given <code>z</code> and round using the given {@link RoundingMode}.
     *
     * @param v
     *          the {@link Vector2dc} to copy the values from
     * @param z
     *          the z component
     * @param mode
     *          the {@link RoundingMode} to use
     */
    public Vector3L(Vector2dc v, float z, int mode) {
        this.x = Math.roundLongUsing(v.x(), mode);
        this.y = Math.roundLongUsing(v.y(), mode);
        this.z = Math.roundLongUsing(z, mode);
    }

    /**
     * Create a new {@link Vector3L} and initialize its components to the rounded value of
     * the given vector.
     *
     * @param v
     *          the {@link Vector3dc} to round and copy the values from
     * @param mode
     *          the {@link RoundingMode} to use
     */
    public Vector3L(Vector3dc v, int mode) {
        this.x = Math.roundLongUsing(v.x(), mode);
        this.y = Math.roundLongUsing(v.y(), mode);
        this.z = Math.roundLongUsing(v.z(), mode);
    }

    /**
     * Create a new {@link Vector3L} and initialize its three components from the first
     * three elements of the given array.
     *
     * @param xyz
     *          the array containing at least three elements
     */
    public Vector3L(int[] xyz) {
        this.x = xyz[0];
        this.y = xyz[1];
        this.z = xyz[2];
    }

//#ifdef __HAS_NIO__
    /**
     * Create a new {@link Vector3L} and read this vector from the supplied
     * {@link ByteBuffer} at the current buffer
     * {@link ByteBuffer#position() position}.
     * <p>
     * This method will not increment the position of the given ByteBuffer.
     * <p>
     * In order to specify the offset into the ByteBuffer at which the vector is
     * read, use {@link #Vector3L(int, ByteBuffer)}, taking the absolute
     * position as parameter.
     *
     * @see #Vector3L(int, ByteBuffer)
     *
     * @param buffer
     *          values will be read in <code>x, y, z</code> order
     */
    public Vector3L(ByteBuffer buffer) {
        MemUtil.INSTANCE.get(this, buffer.position(), buffer);
    }

    /**
     * Create a new {@link Vector3L} and read this vector from the supplied
     * {@link ByteBuffer} starting at the specified absolute buffer
     * position/index.
     * <p>
     * This method will not increment the position of the given ByteBuffer.
     *
     * @param index
     *          the absolute position into the ByteBuffer
     * @param buffer
     *          values will be read in <code>x, y, z</code> order
     */
    public Vector3L(int index, ByteBuffer buffer) {
        MemUtil.INSTANCE.get(this, index, buffer);
    }

    /**
     * Create a new {@link Vector3L} and read this vector from the supplied
     * {@link LongBuffer} at the current buffer
     * {@link LongBuffer#position() position}.
     * <p>
     * This method will not increment the position of the given LongBuffer.
     * <p>
     * In order to specify the offset into the LongBuffer at which the vector is
     * read, use {@link #Vector3L(int, LongBuffer)}, taking the absolute position
     * as parameter.
     *
     * @see #Vector3L(int, LongBuffer)
     *
     * @param buffer
     *          values will be read in <code>x, y, z</code> order
     */
    public Vector3L(LongBuffer buffer) {
        MemUtil.INSTANCE.get(this, buffer.position(), buffer);
    }

    /**
     * Create a new {@link Vector3L} and read this vector from the supplied
     * {@link LongBuffer} starting at the specified absolute buffer
     * position/index.
     * <p>
     * This method will not increment the position of the given LongBuffer.
     *
     * @param index
     *          the absolute position into the LongBuffer
     * @param buffer
     *          values will be read in <code>x, y, z</code> order
     */
    public Vector3L(int index, LongBuffer buffer) {
        MemUtil.INSTANCE.get(this, index, buffer);
    }
//#endif

    public long x() {
        return this.x;
    }

    public long y() {
        return this.y;
    }

    public long z() {
        return this.z;
    }

    /**
     * Copy the <code>(x, y)</code> components of <code>this</code> into the supplied <code>dest</code> vector
     * and return it.
     *
     * @param dest
     *      will hold the result
     * @return dest
     */
    public Vector2f xy(Vector2f dest) {
        return dest.set(x, y);
    }

    /**
     * Copy the <code>(x, y)</code> components of <code>this</code> into the supplied <code>dest</code> vector
     * and return it.
     *
     * @param dest
     *      will hold the result
     * @return dest
     */
    public Vector2d xy(Vector2d dest) {
        return dest.set(x, y);
    }

    /**
     * Copy the <code>(x, y)</code> components of <code>this</code> into the supplied <code>dest</code> vector
     * and return it.
     *
     * @param dest
     *      will hold the result
     * @return dest
     */
    public Vector2L xy(Vector2L dest) {
        return dest.set(x, y);
    }

    /**
     * Set the x, y and z components to match the supplied vector.
     *
     * @param v
     *          contains the values of x, y and z to set
     * @return this
     */
    public Vector3L set(Vector3Lc v) {
        x = v.x();
        y = v.y();
        z = v.z();
        return this;
    }

    /**
     * Set this vector to the values of v using {@link RoundingMode#TRUNCATE} rounding.
     * <p>
     * Note that due to the given vector <code>v</code> storing the components
     * in double-precision, there is the possibility to lose precision.
     *
     * @param v
     *          the vector to copy from
     * @return this
     */
    public Vector3L set(Vector3dc v) {
        this.x = (int) v.x();
        this.y = (int) v.y();
        this.z = (int) v.z();
        return this;
    }

    /**
     * Set this vector to the values of v using the given {@link RoundingMode}.
     * <p>
     * Note that due to the given vector <code>v</code> storing the components
     * in double-precision, there is the possibility to lose precision.
     *
     * @param v
     *          the vector to copy from
     * @param mode
     *          the {@link RoundingMode} to use
     * @return this
     */
    public Vector3L set(Vector3dc v, int mode) {
        this.x = Math.roundLongUsing(v.x(), mode);
        this.y = Math.roundLongUsing(v.y(), mode);
        this.z = Math.roundLongUsing(v.z(), mode);
        return this;
    }

    /**
     * Set this vector to the values of v using the given {@link RoundingMode}.
     * <p>
     * Note that due to the given vector <code>v</code> storing the components
     * in double-precision, there is the possibility to lose precision.
     *
     * @param v
     *          the vector to copy from
     * @param mode
     *          the {@link RoundingMode} to use
     * @return this
     */
    public Vector3L set(Vector3fc v, int mode) {
        this.x = Math.roundLongUsing(v.x(), mode);
        this.y = Math.roundLongUsing(v.y(), mode);
        this.z = Math.roundLongUsing(v.z(), mode);
        return this;
    }

    /**
     * Set the first two components from the given <code>v</code> and the z
     * component from the given <code>z</code>
     *
     * @param v
     *          the {@link Vector2ic} to copy the values from
     * @param z
     *          the z component
     * @return this
     */
    public Vector3L set(Vector2ic v, long z) {
        this.x = v.x();
        this.y = v.y();
        this.z = z;
        return this;
    }

    /**
     * Set the x, y, and z components to the supplied value.
     *
     * @param d
     *          the value of all three components
     * @return this
     */
    public Vector3L set(long d) {
        this.x = d;
        this.y = d;
        this.z = d;
        return this;
    }

    /**
     * Set the x, y and z components to the supplied values.
     *
     * @param x
     *          the x component
     * @param y
     *          the y component
     * @param z
     *          the z component
     * @return this
     */
    public Vector3L set(long x, long y, long z) {
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }

    /**
     * Set the three components of this vector to the first three elements of the given array.
     * 
     * @param xyz
     *          the array containing at least three elements
     * @return this
     */
    public Vector3L set(long[] xyz) {
        this.x = xyz[0];
        this.y = xyz[1];
        this.z = xyz[2];
        return this;
    }

//#ifdef __HAS_NIO__
    /**
     * Read this vector from the supplied {@link ByteBuffer} at the current
     * buffer {@link ByteBuffer#position() position}.
     * <p>
     * This method will not increment the position of the given ByteBuffer.
     * <p>
     * In order to specify the offset into the ByteBuffer at which the vector is
     * read, use {@link #set(int, ByteBuffer)}, taking the absolute position as
     * parameter.
     *
     * @see #set(int, ByteBuffer)
     *
     * @param buffer
     *          values will be read in <code>x, y, z</code> order
     * @return this
     */
    public Vector3L set(ByteBuffer buffer) {
        MemUtil.INSTANCE.get(this, buffer.position(), buffer);
        return this;
    }

    /**
     * Read this vector from the supplied {@link ByteBuffer} starting at the
     * specified absolute buffer position/index.
     * <p>
     * This method will not increment the position of the given ByteBuffer.
     *
     * @param index
     *          the absolute position into the ByteBuffer
     * @param buffer
     *          values will be read in <code>x, y, z</code> order
     * @return this
     */
    public Vector3L set(int index, ByteBuffer buffer) {
        MemUtil.INSTANCE.get(this, index, buffer);
        return this;
    }

    /**
     * Read this vector from the supplied {@link LongBuffer} at the current
     * buffer {@link LongBuffer#position() position}.
     * <p>
     * This method will not increment the position of the given LongBuffer.
     * <p>
     * In order to specify the offset into the LongBuffer at which the vector is
     * read, use {@link #set(int, LongBuffer)}, taking the absolute position as
     * parameter.
     *
     * @see #set(int, LongBuffer)
     *
     * @param buffer
     *          values will be read in <code>x, y, z</code> order
     * @return this
     */
    public Vector3L set(LongBuffer buffer) {
        MemUtil.INSTANCE.get(this, buffer.position(), buffer);
        return this;
    }

    /**
     * Read this vector from the supplied {@link LongBuffer} starting at the
     * specified absolute buffer position/index.
     * <p>
     * This method will not increment the position of the given LongBuffer.
     *
     * @param index
     *          the absolute position into the LongBuffer
     * @param buffer
     *          values will be read in <code>x, y, z</code> order
     * @return this
     */
    public Vector3L set(int index, LongBuffer buffer) {
        MemUtil.INSTANCE.get(this, index, buffer);
        return this;
    }
//#endif

//#ifdef __HAS_UNSAFE__
    /**
     * Set the values of this vector by reading 3 integer values from off-heap memory,
     * starting at the given address.
     * <p>
     * This method will throw an {@link UnsupportedOperationException} when JOML is used with `-Djoml.nounsafe`.
     * <p>
     * <em>This method is unsafe as it can result in a crash of the JVM process when the specified address range does not belong to this process.</em>
     * 
     * @param address
     *              the off-heap memory address to read the vector values from
     * @return this
     */
    public Vector3L setFromAddress(long address) {
        if (Options.NO_UNSAFE)
            throw new UnsupportedOperationException("Not supported when using joml.nounsafe");
        MemUtil.MemUtilUnsafe.get(this, address);
        return this;
    }
//#endif

    public long get(int component) throws IllegalArgumentException {
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

    /**
     * Set the value of the specified component of this vector.
     *
     * @param component
     *          the component whose value to set, within <code>[0..2]</code>
     * @param value
     *          the value to set
     * @return this
     * @throws IllegalArgumentException if <code>component</code> is not within <code>[0..2]</code>
     */
    public Vector3L setComponent(int component, int value) throws IllegalArgumentException {
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
    public LongBuffer get(LongBuffer buffer) {
        MemUtil.INSTANCE.put(this, buffer.position(), buffer);
        return buffer;
    }

    public LongBuffer get(int index, LongBuffer buffer) {
        MemUtil.INSTANCE.put(this, index, buffer);
        return buffer;
    }

    public ByteBuffer get(ByteBuffer buffer) {
        MemUtil.INSTANCE.put(this, buffer.position(), buffer);
        return buffer;
    }

    public ByteBuffer get(int index, ByteBuffer buffer) {
        MemUtil.INSTANCE.put(this, index, buffer);
        return buffer;
    }
//#endif

//#ifdef __HAS_UNSAFE__
    public Vector3Lc getToAddress(long address) {
        if (Options.NO_UNSAFE)
            throw new UnsupportedOperationException("Not supported when using joml.nounsafe");
        MemUtil.MemUtilUnsafe.put(this, address);
        return this;
    }
//#endif

    /**
     * Subtract the supplied vector from this one and store the result in
     * <code>this</code>.
     *
     * @param v
     *          the vector to subtract
     * @return this
     */
    public Vector3L sub(Vector3Lc v) {
        return sub(v, this);
    }

    public Vector3L sub(Vector3Lc v, Vector3L dest) {
        dest.x = x - v.x();
        dest.y = y - v.y();
        dest.z = z - v.z();
        return dest;
    }

    /**
     * Decrement the components of this vector by the given values.
     *
     * @param x
     *          the x component to subtract
     * @param y
     *          the y component to subtract
     * @param z
     *          the z component to subtract
     * @return this
     */
    public Vector3L sub(long x, long y, long z) {
        return sub(x, y, z, this);
    }

    public Vector3L sub(long x, long y, long z, Vector3L dest) {
        dest.x = this.x - x;
        dest.y = this.y - y;
        dest.z = this.z - z;
        return dest;
    }

    /**
     * Add the supplied vector to this one.
     *
     * @param v
     *          the vector to add
     * @return this
     */
    public Vector3L add(Vector3Lc v) {
        return add(v, this);
    }

    public Vector3L add(Vector3Lc v, Vector3L dest) {
        dest.x = x + v.x();
        dest.y = y + v.y();
        dest.z = z + v.z();
        return dest;
    }

    /**
     * Increment the components of this vector by the given values.
     *
     * @param x
     *          the x component to add
     * @param y
     *          the y component to add
     * @param z
     *          the z component to add
     * @return this
     */
    public Vector3L add(long x, long y, long z) {
        return add(x, y, z, this);
    }

    public Vector3L add(long x, long y, long z, Vector3L dest) {
        dest.x = this.x + x;
        dest.y = this.y + y;
        dest.z = this.z + z;
        return dest;
    }

    /**
     * Multiply all components of this vector by the given scalar
     * value.
     * 
     * @param scalar
     *          the scalar to multiply this vector by
     * @return this
     */
    public Vector3L mul(long scalar) {
        return mul(scalar, this);
    }

    public Vector3L mul(long scalar, Vector3L dest) {
        dest.x = x * scalar;
        dest.y = y * scalar;
        dest.z = z * scalar;
        return dest;
    }

    /**
     * Multiply all components of this vector by the given vector.
     *
     * @param v
     *          the vector to multiply
     * @return this
     */
    public Vector3L mul(Vector3Lc v) {
        return mul(v, this);
    }

    public Vector3L mul(Vector3Lc v, Vector3L dest) {
        dest.x = x * v.x();
        dest.y = y * v.y();
        dest.z = z * v.z();
        return dest;
    }

    /**
     * Multiply the components of this vector by the given values.
     *
     * @param x
     *          the x component to multiply
     * @param y
     *          the y component to multiply
     * @param z
     *          the z component to multiply
     * @return this
     */
    public Vector3L mul(long x, long y, long z) {
        return mul(x, y, z, this);
    }

    public Vector3L mul(long x, long y, long z, Vector3L dest) {
        dest.x = this.x * x;
        dest.y = this.y * y;
        dest.z = this.z * z;
        return dest;
    }

    /**
     * Divide all components of this vector by the given scalar value.
     *
     * @param scalar
     *          the scalar to divide by
     * @return this
     */
    public Vector3L div(float scalar) {
        return div(scalar, this);
    }

    public Vector3L div(float scalar, Vector3L dest) {
        float invscalar = 1.0f / scalar;
        dest.x = (int) (x * invscalar);
        dest.y = (int) (y * invscalar);
        dest.z = (int) (z * invscalar);
        return dest;
    }

    /**
     * Divide all components of this vector by the given scalar value.
     *
     * @param scalar
     *          the scalar to divide by
     * @return this
     */
    public Vector3L div(long scalar) {
        return div(scalar, this);
    }

    public Vector3L div(long scalar, Vector3L dest) {
        dest.x = x / scalar;
        dest.y = y / scalar;
        dest.z = z / scalar;
        return dest;
    }

    public long lengthSquared() {
        return x * x + y * y + z * z;
    }

    /**
     * Get the length squared of a 3-dimensional single-precision vector.
     *
     * @param x The vector's x component
     * @param y The vector's y component
     * @param z The vector's z component
     *
     * @return the length squared of the given vector
     */
    public static long lengthSquared(long x, long y, long z) {
        return x * x + y * y + z * z;
    }

    public double length() {
        return Math.sqrt(x * x + y * y + z * z);
    }

    /**
     * Get the length of a 3-dimensional single-precision vector.
     *
     * @param x The vector's x component
     * @param y The vector's y component
     * @param z The vector's z component
     *
     * @return the length squared of the given vector
     */
    public static double length(long x, long y, long z) {
        return Math.sqrt(x * x + y * y + z * z);
    }

    public double distance(Vector3Lc v) {
        long dx = this.x - v.x();
        long dy = this.y - v.y();
        long dz = this.z - v.z();
        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    public double distance(long x, long y, long z) {
        long dx = this.x - x;
        long dy = this.y - y;
        long dz = this.z - z;
        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    public long gridDistance(Vector3Lc v) {
        return Math.abs(v.x() - x()) + Math.abs(v.y() - y())  + Math.abs(v.z() - z());
    }

    public long gridDistance(long x, long y, long z) {
        return Math.abs(x - x()) + Math.abs(y - y()) + Math.abs(z - z());
    }

    public long distanceSquared(Vector3Lc v) {
        long dx = this.x - v.x();
        long dy = this.y - v.y();
        long dz = this.z - v.z();
        return dx * dx + dy * dy + dz * dz;
    }

    public long distanceSquared(long x, long y, long z) {
        long dx = this.x - x;
        long dy = this.y - y;
        long dz = this.z - z;
        return dx * dx + dy * dy + dz * dz;
    }

    /**
     * Return the distance between <code>(x1, y1, z1)</code> and <code>(x2, y2, z2)</code>.
     *
     * @param x1
     *          the x component of the first vector
     * @param y1
     *          the y component of the first vector
     * @param z1
     *          the z component of the first vector
     * @param x2
     *          the x component of the second vector
     * @param y2
     *          the y component of the second vector
     * @param z2
     *          the z component of the second vector
     * @return the euclidean distance
     */
    public static double distance(long x1, long y1, long z1, long x2, long y2, long z2) {
        return Math.sqrt(distanceSquared(x1, y1, z1, x2, y2, z2));
    }

    /**
     * Return the squared distance between <code>(x1, y1, z1)</code> and <code>(x2, y2, z2)</code>.
     *
     * @param x1
     *          the x component of the first vector
     * @param y1
     *          the y component of the first vector
     * @param z1
     *          the z component of the first vector
     * @param x2
     *          the x component of the second vector
     * @param y2
     *          the y component of the second vector
     * @param z2
     *          the z component of the second vector
     * @return the euclidean distance squared
     */
    public static long distanceSquared(long x1, long y1, long z1, long x2, long y2, long z2) {
        long dx = x1 - x2;
        long dy = y1 - y2;
        long dz = z1 - z2;
        return dx * dx + dy * dy + dz * dz;
    }

    /**
     * Set all components to zero.
     *
     * @return this
     */
    public Vector3L zero() {
        this.x = 0L;
        this.y = 0L;
        this.z = 0L;
        return this;
    }

    /**
     * Return a string representation of this vector.
     * <p>
     * This method creates a new {@link DecimalFormat} on every invocation with the format string "<code>0.000E0;-</code>".
     * 
     * @return the string representation
     */
    public String toString() {
        return Runtime.formatNumbers(toString(Options.NUMBER_FORMAT));
    }

    /**
     * Return a string representation of this vector by formatting the vector components with the given {@link NumberFormat}.
     * 
     * @param formatter
     *          the {@link NumberFormat} used to format the vector components with
     * @return the string representation
     */
    public String toString(NumberFormat formatter) {
        return "(" + formatter.format(x) + " " + formatter.format(y) + " " + formatter.format(z) + ")";
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeLong(x);
        out.writeLong(y);
        out.writeLong(z);
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        x = in.readLong();
        y = in.readLong();
        z = in.readLong();
    }

    /**
     * Negate this vector.
     *
     * @return this
     */
    public Vector3L negate() {
        return negate(this);
    }

    public Vector3L negate(Vector3L dest) {
        dest.x = -x;
        dest.y = -y;
        dest.z = -z;
        return dest;
    }

    /**
     * Set the components of this vector to be the component-wise minimum of this and the other vector.
     *
     * @param v
     *          the other vector
     * @return this
     */
    public Vector3L min(Vector3Lc v) {
        return min(v, this);
    }

    public Vector3L min(Vector3Lc v, Vector3L dest) {
        dest.x = x < v.x() ? x : v.x();
        dest.y = y < v.y() ? y : v.y();
        dest.z = z < v.z() ? z : v.z();
        return dest;
    }

    /**
     * Set the components of this vector to be the component-wise maximum of this and the other vector.
     *
     * @param v
     *          the other vector
     * @return this
     */
    public Vector3L max(Vector3Lc v) {
        return max(v, this);
    }

    public Vector3L max(Vector3Lc v, Vector3L dest) {
        dest.x = x > v.x() ? x : v.x();
        dest.y = y > v.y() ? y : v.y();
        dest.z = z > v.z() ? z : v.z();
        return dest;
    }

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

    /**
     * Set <code>this</code> vector's components to their respective absolute values.
     * 
     * @return this
     */
    public Vector3L absolute() {
        return absolute(this);
    }

    public Vector3L absolute(Vector3L dest) {
        dest.x = Math.abs(this.x);
        dest.y = Math.abs(this.y);
        dest.z = Math.abs(this.z);
        return dest;
    }

    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int)(x ^ (x >>> 32));
        result = prime * result + (int)(y ^ (y >>> 32));
        result = prime * result + (int)(z ^ (z >>> 32));
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Vector3L other = (Vector3L) obj;
        if (x != other.x) {
            return false;
        }
        if (y != other.y) {
            return false;
        }
        if (z != other.z) {
            return false;
        }
        return true;
    }

    public boolean equals(long x, long y, long z) {
        if (this.x != x)
            return false;
        if (this.y != y)
            return false;
        if (this.z != z)
            return false;
        return true;
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

}
