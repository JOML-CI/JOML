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
 * Contains the definition of a vector comprising 2 longs and associated
 * transformations.
 *
 * @author Kai Burjack
 */
public class Vector2L implements Externalizable, Cloneable, Vector2Lc {

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
     * Create a new {@link Vector2L} and initialize its components to zero.
     */
    public Vector2L() {
    }

    /**
     * Create a new {@link Vector2L} and initialize both of its components with
     * the given value.
     *
     * @param s
     *          the value of both components
     */
    public Vector2L(long s) {
        this.x = s;
        this.y = s;
    }

    /**
     * Create a new {@link Vector2L} and initialize its components to the given values.
     *
     * @param x
     *          the x component
     * @param y
     *          the y component
     */
    public Vector2L(long x, long y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Create a new {@link Vector2L} and initialize its component values and
     * round using the given {@link RoundingMode}.
     * @param x
     *          the x component
     * @param y
     *          the y component
     * @param mode
     *          the {@link RoundingMode} to use
     */
    public Vector2L(float x, float y, int mode) {
        this.x = Math.roundLongUsing(x, mode);
        this.y = Math.roundLongUsing(y, mode);
    }

    /**
     * Create a new {@link Vector2L} and initialize its component values and
     * round using the given {@link RoundingMode}.
     * @param x
     *          the x component
     * @param y
     *          the y component
     * @param mode
     *          the {@link RoundingMode} to use
     */
    public Vector2L(double x, double y, int mode) {
        this.x = Math.roundLongUsing(x, mode);
        this.y = Math.roundLongUsing(y, mode);
    }

    /**
     * Create a new {@link Vector2L} and initialize its components to the one of
     * the given vector.
     *
     * @param v
     *          the {@link Vector2Lc} to copy the values from
     */
    public Vector2L(Vector2Lc v) {
        x = v.x();
        y = v.y();
    }

    /**
     * Create a new {@link Vector2L} and initialize its components to the one of
     * the given vector.
     *
     * @param v
     *          the {@link Vector2ic} to copy the values from
     */
    public Vector2L(Vector2ic v) {
        x = v.x();
        y = v.y();
    }

    /**
     * Create a new {@link Vector2L} and initialize its components to the rounded value of
     * the given vector.
     *
     * @param v
     *          the {@link Vector2fc} to round and copy the values from
     * @param mode
     *          the {@link RoundingMode} to use
     */
    public Vector2L(Vector2fc v, int mode) {
        x = Math.roundLongUsing(v.x(), mode);
        y = Math.roundLongUsing(v.y(), mode);
    }

    /**
     * Create a new {@link Vector2L} and initialize its components to the rounded value of
     * the given vector.
     *
     * @param v
     *          the {@link Vector2dc} to round and copy the values from
     * @param mode
     *          the {@link RoundingMode} to use
     */
    public Vector2L(Vector2dc v, int mode) {
        x = Math.roundLongUsing(v.x(), mode);
        y = Math.roundLongUsing(v.y(), mode);
    }

    /**
     * Create a new {@link Vector2L} and initialize its two components from the first
     * two elements of the given array.
     *
     * @param xy
     *          the array containing at least three elements
     */
    public Vector2L(long[] xy) {
        this.x = xy[0];
        this.y = xy[1];
    }

//#ifdef __HAS_NIO__
    /**
     * Create a new {@link Vector2L} and read this vector from the supplied
     * {@link ByteBuffer} at the current buffer
     * {@link ByteBuffer#position() position}.
     * <p>
     * This method will not increment the position of the given ByteBuffer.
     * <p>
     * In order to specify the offset into the ByteBuffer at which the vector is
     * read, use {@link #Vector2L(int, ByteBuffer)}, taking the absolute
     * position as parameter.
     *
     * @see #Vector2L(int, ByteBuffer)
     *
     * @param buffer
     *          values will be read in <code>x, y</code> order
     */
    public Vector2L(ByteBuffer buffer) {
        MemUtil.INSTANCE.get(this, buffer.position(), buffer);
    }

    /**
     * Create a new {@link Vector2L} and read this vector from the supplied
     * {@link ByteBuffer} starting at the specified absolute buffer
     * position/index.
     * <p>
     * This method will not increment the position of the given ByteBuffer.
     *
     * @param index
     *          the absolute position into the ByteBuffer
     * @param buffer
     *          values will be read in <code>x, y</code> order
     */
    public Vector2L(int index, ByteBuffer buffer) {
        MemUtil.INSTANCE.get(this, index, buffer);
    }

    /**
     * Create a new {@link Vector2L} and read this vector from the supplied
     * {@link LongBuffer} at the current buffer
     * {@link LongBuffer#position() position}.
     * <p>
     * This method will not increment the position of the given IntBuffer.
     * <p>
     * In order to specify the offset into the IntBuffer at which the vector is
     * read, use {@link #Vector2L(int, LongBuffer)}, taking the absolute position
     * as parameter.
     *
     * @see #Vector2L(int, LongBuffer)
     *
     * @param buffer
     *          values will be read in <code>x, y</code> order
     */
    public Vector2L(LongBuffer buffer) {
        MemUtil.INSTANCE.get(this, buffer.position(), buffer);
    }

    /**
     * Create a new {@link Vector2L} and read this vector from the supplied
     * {@link LongBuffer} starting at the specified absolute buffer
     * position/index.
     * <p>
     * This method will not increment the position of the given IntBuffer.
     *
     * @param index
     *          the absolute position into the IntBuffer
     * @param buffer
     *          values will be read in <code>x, y</code> order
     */
    public Vector2L(int index, LongBuffer buffer) {
        MemUtil.INSTANCE.get(this, index, buffer);
    }
//#endif

    public long x() {
        return this.x;
    }

    public long y() {
        return this.y;
    }

    /**
     * Set the x and y components to the supplied value.
     *
     * @param s
     *          scalar value of both components
     * @return this
     */
    public Vector2L set(long s) {
        this.x = s;
        this.y = s;
        return this;
    }

    /**
     * Set the x and y components to the supplied values.
     *
     * @param x
     *          the x component
     * @param y
     *          the y component
     * @return this
     */
    public Vector2L set(long x, long y) {
        this.x = x;
        this.y = y;
        return this;
    }

    /**
     * Set this vector to the values of v.
     *
     * @param v
     *          the vector to copy from
     * @return this
     */
    public Vector2L set(Vector2Lc v) {
        this.x = v.x();
        this.y = v.y();
        return this;
    }

    /**
     * Set this vector to the values of v.
     *
     * @param v
     *          the vector to copy from
     * @return this
     */
    public Vector2L set(Vector2ic v) {
        this.x = v.x();
        this.y = v.y();
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
    public Vector2L set(Vector2dc v) {
        this.x = (long) v.x();
        this.y = (long) v.y();
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
    public Vector2L set(Vector2dc v, int mode) {
        this.x = Math.roundLongUsing(v.x(), mode);
        this.y = Math.roundLongUsing(v.y(), mode);
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
    public Vector2L set(Vector2fc v, int mode) {
        this.x = Math.roundLongUsing(v.x(), mode);
        this.y = Math.roundLongUsing(v.y(), mode);
        return this;
    }

    /**
     * Set the two components of this vector to the first two elements of the given array.
     * 
     * @param xy
     *          the array containing at least two elements
     * @return this
     */
    public Vector2L set(long[] xy) {
        this.x = xy[0];
        this.y = xy[1];
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
     *          values will be read in <code>x, y</code> order
     * @return this
     */
    public Vector2L set(ByteBuffer buffer) {
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
     *          values will be read in <code>x, y</code> order
     * @return this
     */
    public Vector2L set(int index, ByteBuffer buffer) {
        MemUtil.INSTANCE.get(this, index, buffer);
        return this;
    }

    /**
     * Read this vector from the supplied {@link LongBuffer} at the current
     * buffer {@link LongBuffer#position() position}.
     * <p>
     * This method will not increment the position of the given IntBuffer.
     * <p>
     * In order to specify the offset into the IntBuffer at which the vector is
     * read, use {@link #set(int, LongBuffer)}, taking the absolute position as
     * parameter.
     *
     * @see #set(int, LongBuffer)
     *
     * @param buffer
     *          values will be read in <code>x, y</code> order
     * @return this
     */
    public Vector2L set(LongBuffer buffer) {
        MemUtil.INSTANCE.get(this, buffer.position(), buffer);
        return this;
    }

    /**
     * Read this vector from the supplied {@link LongBuffer} starting at the
     * specified absolute buffer position/index.
     * <p>
     * This method will not increment the position of the given IntBuffer.
     *
     * @param index
     *          the absolute position into the IntBuffer
     * @param buffer
     *          values will be read in <code>x, y</code> order
     * @return this
     */
    public Vector2L set(int index, LongBuffer buffer) {
        MemUtil.INSTANCE.get(this, index, buffer);
        return this;
    }
//#endif

//#ifdef __HAS_UNSAFE__
    /**
     * Set the values of this vector by reading 2 integer values from off-heap memory,
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
    public Vector2L setFromAddress(long address) {
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
        default:
            throw new IllegalArgumentException();
        }
    }

    /**
     * Set the value of the specified component of this vector.
     *
     * @param component
     *          the component whose value to set, within <code>[0..1]</code>
     * @param value
     *          the value to set
     * @return this
     * @throws IllegalArgumentException if <code>component</code> is not within <code>[0..1]</code>
     */
    public Vector2L setComponent(int component, long value) throws IllegalArgumentException {
        switch (component) {
            case 0:
                x = value;
                break;
            case 1:
                y = value;
                break;
            default:
                throw new IllegalArgumentException();
        }
        return this;
    }

//#ifdef __HAS_NIO__
    public ByteBuffer get(ByteBuffer buffer) {
        MemUtil.INSTANCE.put(this, buffer.position(), buffer);
        return buffer;
    }

    public ByteBuffer get(int index, ByteBuffer buffer) {
        MemUtil.INSTANCE.put(this, index, buffer);
        return buffer;
    }

    public LongBuffer get(LongBuffer buffer) {
        MemUtil.INSTANCE.put(this, buffer.position(), buffer);
        return buffer;
    }

    public LongBuffer get(int index, LongBuffer buffer) {
        MemUtil.INSTANCE.put(this, index, buffer);
        return buffer;
    }
//#endif

//#ifdef __HAS_UNSAFE__
    public Vector2Lc getToAddress(long address) {
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
    public Vector2L sub(Vector2Lc v) {
        return sub(v, this);
    }

    public Vector2L sub(Vector2Lc v, Vector2L dest) {
        dest.x = x - v.x();
        dest.y = y - v.y();
        return dest;
    }

    /**
     * Subtract the supplied vector from this one and store the result in
     * <code>this</code>.
     *
     * @param v
     *          the vector to subtract
     * @return this
     */
    public Vector2L sub(Vector2ic v) {
        return sub(v, this);
    }

    public Vector2L sub(Vector2ic v, Vector2L dest) {
        dest.x = x - v.x();
        dest.y = y - v.y();
        return dest;
    }

    /**
     * Decrement the components of this vector by the given values.
     *
     * @param x
     *          the x component to subtract
     * @param y
     *          the y component to subtract
     * @return this
     */
    public Vector2L sub(long x, long y) {
        return sub(x, y, this);
    }

    public Vector2L sub(long x, long y, Vector2L dest) {
        dest.x = this.x - x;
        dest.y = this.y - y;
        return dest;
    }

    public long lengthSquared() {
        return x * x + y * y;
    }

    /**
     * Get the length squared of a 2-dimensional single-precision vector.
     *
     * @param x The vector's x component
     * @param y The vector's y component
     *
     * @return the length squared of the given vector
     */
    public static long lengthSquared(long x, long y) {
        return x * x + y * y;
    }

    public double length() {
        return Math.sqrt(x * x + y * y);
    }

    /**
     * Get the length of a 2-dimensional single-precision vector.
     *
     * @param x The vector's x component
     * @param y The vector's y component
     *
     * @return the length squared of the given vector
     */
    public static double length(long x, long y) {
        return Math.sqrt(x * x + y * y);
    }

    public double distance(Vector2Lc v) {
        long dx = this.x - v.x();
        long dy = this.y - v.y();
        return Math.sqrt(dx * dx + dy * dy);
    }

    public double distance(long x, long y) {
        long dx = this.x - x;
        long dy = this.y - y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    public long distanceSquared(Vector2Lc v) {
        long dx = this.x - v.x();
        long dy = this.y - v.y();
        return dx * dx + dy * dy;
    }

    public long distanceSquared(long x, long y) {
        long dx = this.x - x;
        long dy = this.y - y;
        return dx * dx + dy * dy;
    }

    public long gridDistance(Vector2Lc v) {
        return Math.abs(v.x() - x()) + Math.abs(v.y() - y());
    }

    public long gridDistance(long x, long y) {
        return Math.abs(x - x()) + Math.abs(y - y());
    }

    /**
     * Return the distance between <code>(x1, y1)</code> and <code>(x2, y2)</code>.
     *
     * @param x1
     *          the x component of the first vector
     * @param y1
     *          the y component of the first vector
     * @param x2
     *          the x component of the second vector
     * @param y2
     *          the y component of the second vector
     * @return the euclidean distance
     */
    public static double distance(long x1, long y1, long x2, long y2) {
        long dx = x1 - x2;
        long dy = y1 - y2;
        return Math.sqrt(dx * dx + dy * dy);
    }

    /**
     * Return the squared distance between <code>(x1, y1)</code> and <code>(x2, y2)</code>.
     *
     * @param x1
     *          the x component of the first vector
     * @param y1
     *          the y component of the first vector
     * @param x2
     *          the x component of the second vector
     * @param y2
     *          the y component of the second vector
     * @return the euclidean distance squared
     */
    public static long distanceSquared(long x1, long y1, long x2, long y2) {
        long dx = x1 - x2;
        long dy = y1 - y2;
        return dx * dx + dy * dy;
    }
    
    /**
     * Add <code>v</code> to this vector.
     *
     * @param v
     *          the vector to add
     * @return this
     */
    public Vector2L add(Vector2Lc v) {
        this.x = x + v.x();
        this.y = y + v.y();
        return this;
    }

    public Vector2L add(Vector2Lc v, Vector2L dest) {
        dest.x = x + v.x();
        dest.y = y + v.y();
        return dest;
    }

    /**
     * Add <code>v</code> to this vector.
     *
     * @param v
     *          the vector to add
     * @return this
     */
    public Vector2L add(Vector2ic v) {
        this.x = x + v.x();
        this.y = y + v.y();
        return this;
    }

    public Vector2L add(Vector2ic v, Vector2L dest) {
        dest.x = x + v.x();
        dest.y = y + v.y();
        return dest;
    }

    /**
     * Increment the components of this vector by the given values.
     *
     * @param x
     *          the x component to add
     * @param y
     *          the y component to add
     * @return this
     */
    public Vector2L add(long x, long y) {
        return add(x, y, this);
    }

    public Vector2L add(long x, long y, Vector2L dest) {
        dest.x = this.x + x;
        dest.y = this.y + y;
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
    public Vector2L mul(long scalar) {
        return mul(scalar, this);
    }

    public Vector2L mul(long scalar, Vector2L dest) {
        dest.x = x * scalar;
        dest.y = y * scalar;
        return dest;
    }

    /**
     * Add the supplied vector by this one.
     *
     * @param v
     *          the vector to multiply
     * @return this
     */
    public Vector2L mul(Vector2Lc v) {
        return mul(v, this);
    }

    public Vector2L mul(Vector2Lc v, Vector2L dest) {
        dest.x = x * v.x();
        dest.y = y * v.y();
        return dest;
    }

    /**
     * Add the supplied vector by this one.
     *
     * @param v
     *          the vector to multiply
     * @return this
     */
    public Vector2L mul(Vector2ic v) {
        return mul(v, this);
    }

    public Vector2L mul(Vector2ic v, Vector2L dest) {
        dest.x = x * v.x();
        dest.y = y * v.y();
        return dest;
    }

    /**
     * Multiply the components of this vector by the given values.
     *
     * @param x
     *          the x component to multiply
     * @param y
     *          the y component to multiply
     * @return this
     */
    public Vector2L mul(long x, long y) {
        return mul(x, y, this);
    }

    public Vector2L mul(long x, long y, Vector2L dest) {
        dest.x = this.x * x;
        dest.y = this.y * y;
        return dest;
    }

    /**
     * Divide all components of this vector by the given scalar value.
     *
     * @param scalar
     *          the scalar to divide by
     * @return a vector holding the result
     */
    public Vector2L div(float scalar) {
        return div(scalar, this);
    }

    public Vector2L div(float scalar, Vector2L dest) {
        float invscalar = 1.0f / scalar;
        dest.x = (int) (x * invscalar);
        dest.y = (int) (y * invscalar);
        return dest;
    }

    /**
     * Divide all components of this vector by the given scalar value.
     *
     * @param scalar
     *          the scalar to divide by
     * @return a vector holding the result
     */
    public Vector2L div(long scalar) {
        return div(scalar, this);
    }

    public Vector2L div(long scalar, Vector2L dest) {
        dest.x = x / scalar;
        dest.y = y / scalar;
        return dest;
    }
    
    /**
     * Set all components to zero.
     *
     * @return this
     */
    public Vector2L zero() {
        this.x = 0;
        this.y = 0;
        return this;
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeLong(x);
        out.writeLong(y);
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        x = in.readLong();
        y = in.readLong();
    }

    /**
     * Negate this vector.
     *
     * @return this
     */
    public Vector2L negate() {
        return negate(this);
    }

    public Vector2L negate(Vector2L dest) {
        dest.x = -x;
        dest.y = -y;
        return dest;
    }

    /**
     * Set the components of this vector to be the component-wise minimum of this and the other vector.
     *
     * @param v
     *          the other vector
     * @return this
     */
    public Vector2L min(Vector2Lc v) {
        return min(v, this);
    }

    public Vector2L min(Vector2Lc v, Vector2L dest) {
        dest.x = x < v.x() ? x : v.x();
        dest.y = y < v.y() ? y : v.y();
        return dest;
    }

    /**
     * Set the components of this vector to be the component-wise maximum of this and the other vector.
     *
     * @param v
     *          the other vector
     * @return this
     */
    public Vector2L max(Vector2Lc v) {
        return max(v, this);
    }

    public Vector2L max(Vector2Lc v, Vector2L dest) {
        dest.x = x > v.x() ? x : v.x();
        dest.y = y > v.y() ? y : v.y();
        return dest;
    }

    public long maxComponent() {
        long absX = Math.abs(x);
        long absY = Math.abs(y);
        if (absX >= absY)
            return 0L;
        return 1L;
    }

    public long minComponent() {
        long absX = Math.abs(x);
        long absY = Math.abs(y);
        if (absX < absY)
            return 0L;
        return 1L;
    }

    /**
     * Set <code>this</code> vector's components to their respective absolute values.
     * 
     * @return this
     */
    public Vector2L absolute() {
        return absolute(this);
    }

    public Vector2L absolute(Vector2L dest) {
        dest.x = Math.abs(this.x);
        dest.y = Math.abs(this.y);
        return dest;
    }

    public int hashCode() {
        final long prime = 31L;
        long result = 1L;
        result = prime * result + x;
        result = prime * result + y;
        return (int) (result ^ (result >> 32L));
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
        Vector2L other = (Vector2L) obj;
        if (x != other.x) {
            return false;
        }
        if (y != other.y) {
            return false;
        }
        return true;
    }

    public boolean equals(long x, long y) {
        if (this.x != x)
            return false;
        if (this.y != y)
            return false;
        return true;
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
        return "(" + formatter.format(x) + " " + formatter.format(y) + ")";
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

}
