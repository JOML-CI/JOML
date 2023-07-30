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
 * Contains the definition of a vector comprising 4 longs and associated
 * transformations.
 *
 * @author Kai Burjack
 */
public class Vector4L implements Externalizable, Cloneable, Vector4Lc {

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
     * The w component of the vector.
     */
    public long w;

    /**
     * Create a new {@link Vector4L} of <code>(0, 0, 0, 1)</code>.
     */
    public Vector4L() {
        this.w = 1L;
    }

    /**
     * Create a new {@link Vector4L} with the same values as <code>v</code>.
     *
     * @param v
     *          the {@link Vector4Lc} to copy the values from
     */
    public Vector4L(Vector4Lc v) {
        this.x = v.x();
        this.y = v.y();
        this.z = v.z();
        this.w = v.w();
    }

    /**
     * Create a new {@link Vector4L} with the same values as <code>v</code>.
     *
     * @param v
     *          the {@link Vector4ic} to copy the values from
     */
    public Vector4L(Vector4ic v) {
        this.x = v.x();
        this.y = v.y();
        this.z = v.z();
        this.w = v.w();
    }

    /**
     * Create a new {@link Vector4L} with the first three components from the
     * given <code>v</code> and the given <code>w</code>.
     *
     * @param v
     *          the {@link Vector3Lc}
     * @param w
     *          the w component
     */
    public Vector4L(Vector3Lc v, long w) {
        this.x = v.x();
        this.y = v.y();
        this.z = v.z();
        this.w = w;
    }

    /**
     * Create a new {@link Vector4L} with the first three components from the
     * given <code>v</code> and the given <code>w</code>.
     *
     * @param v
     *          the {@link Vector3ic}
     * @param w
     *          the w component
     */
    public Vector4L(Vector3ic v, long w) {
        this.x = v.x();
        this.y = v.y();
        this.z = v.z();
        this.w = w;
    }

    /**
     * Create a new {@link Vector4L} with the first two components from the
     * given <code>v</code> and the given <code>z</code>, and <code>w</code>.
     *
     * @param v
     *          the {@link Vector2ic}
     * @param z
     *          the z component
     * @param w
     *          the w component
     */
    public Vector4L(Vector2Lc v, long z, long w) {
        this.x = v.x();
        this.y = v.y();
        this.z = z;
        this.w = w;
    }

    /**
     * Create a new {@link Vector4L} with the first two components from the
     * given <code>v</code> and the given <code>z</code>, and <code>w</code>.
     *
     * @param v
     *          the {@link Vector2ic}
     * @param z
     *          the z component
     * @param w
     *          the w component
     */
    public Vector4L(Vector2ic v, long z, long w) {
        this.x = v.x();
        this.y = v.y();
        this.z = z;
        this.w = w;
    }

    /**
     * Create a new {@link Vector4L} with the first three components from the
     * given <code>v</code> and the given <code>w</code> and round using the given {@link RoundingMode}.
     *
     * @param v
     *          the {@link Vector3fc} to copy the values from
     * @param w
     *          the w component
     * @param mode
     *          the {@link RoundingMode} to use
     */
    public Vector4L(Vector3fc v, float w, int mode) {
        this.x = Math.roundLongUsing(v.x(), mode);
        this.y = Math.roundLongUsing(v.y(), mode);
        this.z = Math.roundLongUsing(v.z(), mode);
        this.w = Math.roundLongUsing(w, mode);
    }

    /**
     * Create a new {@link Vector4L} and initialize its components to the rounded value of
     * the given vector.
     *
     * @param v
     *          the {@link Vector4fc} to round and copy the values from
     * @param mode
     *          the {@link RoundingMode} to use
     */
    public Vector4L(Vector4fc v, int mode) {
        x = Math.roundLongUsing(v.x(), mode);
        y = Math.roundLongUsing(v.y(), mode);
        z = Math.roundLongUsing(v.z(), mode);
        w = Math.roundLongUsing(v.w(), mode);
    }

    /**
     * Create a new {@link Vector4L} and initialize its components to the rounded value of
     * the given vector.
     *
     * @param v
     *          the {@link Vector4dc} to round and copy the values from
     * @param mode
     *          the {@link RoundingMode} to use
     */
    public Vector4L(Vector4dc v, int mode) {
        x = Math.roundLongUsing(v.x(), mode);
        y = Math.roundLongUsing(v.y(), mode);
        z = Math.roundLongUsing(v.z(), mode);
        w = Math.roundLongUsing(v.w(), mode);
    }

    /**
     * Create a new {@link Vector4L} and initialize all four components with the
     * given value.
     *
     * @param s
     *          scalar value of all four components
     */
    public Vector4L(long s) {
        this.x = s;
        this.y = s;
        this.z = s;
        this.w = s;
    }

    /**
     * Create a new {@link Vector4L} with the given component values.
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
    public Vector4L(long x, long y, long z, long w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    /**
     * Create a new {@link Vector4L} and initialize its four components from the first
     * four elements of the given array.
     *
     * @param xyzw
     *          the array containing at least four elements
     */
    public Vector4L(int[] xyzw) {
        this.x = xyzw[0];
        this.y = xyzw[1];
        this.z = xyzw[2];
        this.w = xyzw[3];
    }

//#ifdef __HAS_NIO__
    /**
     * Create a new {@link Vector4L} and read this vector from the supplied
     * {@link ByteBuffer} at the current buffer
     * {@link ByteBuffer#position() position}.
     * <p>
     * This method will not increment the position of the given ByteBuffer.
     * <p>
     * In order to specify the offset into the ByteBuffer at which the vector is
     * read, use {@link #Vector4L(int, ByteBuffer)}, taking the absolute
     * position as parameter.
     *
     * @see #Vector4L(int, ByteBuffer)
     *
     * @param buffer
     *          values will be read in <code>x, y, z, w</code> order
     */
    public Vector4L(ByteBuffer buffer) {
        MemUtil.INSTANCE.get(this, buffer.position(), buffer);
    }

    /**
     * Create a new {@link Vector4L} and read this vector from the supplied
     * {@link ByteBuffer} starting at the specified absolute buffer
     * position/index.
     * <p>
     * This method will not increment the position of the given ByteBuffer.
     *
     * @param index
     *          the absolute position into the ByteBuffer
     * @param buffer
     *          values will be read in <code>x, y, z, w</code> order
     */
    public Vector4L(int index, ByteBuffer buffer) {
        MemUtil.INSTANCE.get(this, index, buffer);
    }

    /**
     * Create a new {@link Vector4L} and read this vector from the supplied
     * {@link LongBuffer} at the current buffer
     * {@link LongBuffer#position() position}.
     * <p>
     * This method will not increment the position of the given LongBuffer.
     * <p>
     * In order to specify the offset into the LongBuffer at which the vector is
     * read, use {@link #Vector4L(int, LongBuffer)}, taking the absolute position
     * as parameter.
     *
     * @see #Vector4L(int, LongBuffer)
     *
     * @param buffer
     *          values will be read in <code>x, y, z, w</code> order
     */
    public Vector4L(LongBuffer buffer) {
        MemUtil.INSTANCE.get(this, buffer.position(), buffer);
    }

    /**
     * Create a new {@link Vector4L} and read this vector from the supplied
     * {@link LongBuffer} starting at the specified absolute buffer
     * position/index.
     * <p>
     * This method will not increment the position of the given LongBuffer.
     *
     * @param index
     *          the absolute position into the LongBuffer
     * @param buffer
     *          values will be read in <code>x, y, z, w</code> order
     */
    public Vector4L(int index, LongBuffer buffer) {
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

    public long w() {
        return this.w;
    }

    /**
     * Copy the <code>(x, y, z)</code> components of <code>this</code> into the supplied <code>dest</code> vector
     * and return it.
     *
     * @param dest
     *      will hold the result
     * @return dest
     */
    public Vector3f xyz(Vector3f dest) {
        return dest.set(x, y, z);
    }

    /**
     * Copy the <code>(x, y, z)</code> components of <code>this</code> into the supplied <code>dest</code> vector
     * and return it.
     *
     * @param dest
     *      will hold the result
     * @return dest
     */
    public Vector3d xyz(Vector3d dest) {
        return dest.set(x, y, z);
    }

    /**
     * Copy the <code>(x, y, z)</code> components of <code>this</code> into the supplied <code>dest</code> vector
     * and return it.
     *
     * @param dest
     *      will hold the result
     * @return dest
     */
    public Vector3L xyz(Vector3L dest) {
        return dest.set(x, y, z);
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
     * Set this vector to the values of the given <code>v</code>.
     *
     * @param v
     *          the vector whose values will be copied into this
     * @return this
     */
    public Vector4L set(Vector4Lc v) {
        this.x = v.x();
        this.y = v.y();
        this.z = v.z();
        this.w = v.w();
        return this;
    }

    /**
     * Set this vector to the values of the given <code>v</code>.
     *
     * @param v
     *          the vector whose values will be copied into this
     * @return this
     */
    public Vector4L set(Vector4ic v) {
        this.x = v.x();
        this.y = v.y();
        this.z = v.z();
        this.w = v.w();
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
    public Vector4L set(Vector4dc v) {
        this.x = (int) v.x();
        this.y = (int) v.y();
        this.z = (int) v.z();
        this.w = (int) v.w();
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
    public Vector4L set(Vector4dc v, int mode) {
        this.x = Math.roundLongUsing(v.x(), mode);
        this.y = Math.roundLongUsing(v.y(), mode);
        this.z = Math.roundLongUsing(v.z(), mode);
        this.w = Math.roundLongUsing(v.w(), mode);
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
    public Vector4L set(Vector4fc v, int mode) {
        this.x = Math.roundLongUsing(v.x(), mode);
        this.y = Math.roundLongUsing(v.y(), mode);
        this.z = Math.roundLongUsing(v.z(), mode);
        this.w = Math.roundLongUsing(v.w(), mode);
        return this;
    }

    /**
     * Set the first three components of this to the components of
     * <code>v</code> and the last component to <code>w</code>.
     *
     * @param v
     *          the {@link Vector3ic} to copy
     * @param w
     *          the w component
     * @return this
     */
    public Vector4L set(Vector3ic v, long w) {
        this.x = v.x();
        this.y = v.y();
        this.z = v.z();
        this.w = w;
        return this;
    }

    /**
     * Sets the first two components of this to the components of given
     * <code>v</code> and last two components to the given <code>z</code>, and
     * <code>w</code>.
     *
     * @param v
     *          the {@link Vector2ic}
     * @param z
     *          the z component
     * @param w
     *          the w component
     * @return this
     */
    public Vector4L set(Vector2ic v, long z, long w) {
        this.x = v.x();
        this.y = v.y();
        this.z = z;
        this.w = w;
        return this;
    }

    /**
     * Set the x, y, z, and w components to the supplied value.
     *
     * @param s
     *          the value of all four components
     * @return this
     */
    public Vector4L set(long s) {
        this.x = s;
        this.y = s;
        this.z = s;
        this.w = s;
        return this;
    }

    /**
     * Set the x, y, z, and w components to the supplied values.
     *
     * @param x
     *          the x component
     * @param y
     *          the y component
     * @param z
     *          the z component
     * @param w
     *          the w component
     * @return this
     */
    public Vector4L set(long x, long y, long z, long w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
        return this;
    }

    /**
     * Set the four components of this vector to the first four elements of the given array.
     * 
     * @param xyzw
     *          the array containing at least four elements
     * @return this
     */
    public Vector4L set(int[] xyzw) {
        this.x = xyzw[0];
        this.y = xyzw[1];
        this.z = xyzw[2];
        this.w = xyzw[3];
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
     *          values will be read in <code>x, y, z, w</code> order
     * @return this
     */
    public Vector4L set(ByteBuffer buffer) {
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
     *          values will be read in <code>x, y, z, w</code> order
     * @return this
     */
    public Vector4L set(int index, ByteBuffer buffer) {
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
     *          values will be read in <code>x, y, z, w</code> order
     * @return this
     */
    public Vector4L set(LongBuffer buffer) {
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
     *          values will be read in <code>x, y, z, w</code> order
     * @return this
     */
    public Vector4L set(int index, LongBuffer buffer) {
        MemUtil.INSTANCE.get(this, index, buffer);
        return this;
    }
//#endif

//#ifdef __HAS_UNSAFE__
    /**
     * Set the values of this vector by reading 4 integer values from off-heap memory,
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
    public Vector4L setFromAddress(long address) {
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
        case 3:
            return w;
        default:
            throw new IllegalArgumentException();
        }
    }

    public int maxComponent() {
        long absX = Math.abs(x);
        long absY = Math.abs(y);
        long absZ = Math.abs(z);
        long absW = Math.abs(w);
        if (absX >= absY && absX >= absZ && absX >= absW) {
            return 0;
        } else if (absY >= absZ && absY >= absW) {
            return 1;
        } else if (absZ >= absW) {
            return 2;
        }
        return 3;
    }

    public int minComponent() {
        long absX = Math.abs(x);
        long absY = Math.abs(y);
        long absZ = Math.abs(z);
        long absW = Math.abs(w);
        if (absX < absY && absX < absZ && absX < absW) {
            return 0;
        } else if (absY < absZ && absY < absW) {
            return 1;
        } else if (absZ < absW) {
            return 2;
        }
        return 3;
    }

    /**
     * Set the value of the specified component of this vector.
     *
     * @param component
     *          the component whose value to set, within <code>[0..3]</code>
     * @param value
     *          the value to set
     * @return this
     * @throws IllegalArgumentException if <code>component</code> is not within <code>[0..3]</code>
     */
    public Vector4L setComponent(int component, long value) throws IllegalArgumentException {
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
    public Vector4Lc getToAddress(long address) {
        if (Options.NO_UNSAFE)
            throw new UnsupportedOperationException("Not supported when using joml.nounsafe");
        MemUtil.MemUtilUnsafe.put(this, address);
        return this;
    }
//#endif

    /**
     * Subtract the supplied vector from this one.
     *
     * @param v
     *          the vector to subtract
     * @return this
     */
    public Vector4L sub(Vector4Lc v) {
        return sub(v, this);
    }
    public Vector4L sub(Vector4Lc v, Vector4L dest) {
        dest.x = this.x - v.x();
        dest.y = this.y - v.y();
        dest.z = this.z - v.z();
        dest.w = this.w - v.w();
        return dest;
    }

    /**
     * Subtract the supplied vector from this one.
     *
     * @param v
     *          the vector to subtract
     * @return this
     */
    public Vector4L sub(Vector4ic v) {
        return sub(v, this);
    }
    public Vector4L sub(Vector4ic v, Vector4L dest) {
        dest.x = this.x - v.x();
        dest.y = this.y - v.y();
        dest.z = this.z - v.z();
        dest.w = this.w - v.w();
        return dest;
    }

    /**
     * Subtract <code>(x, y, z, w)</code> from this.
     *
     * @param x
     *          the x component to subtract
     * @param y
     *          the y component to subtract
     * @param z
     *          the z component to subtract
     * @param w
     *          the w component to subtract
     * @return this
     */
    public Vector4L sub(long x, long y, long z, long w) {
        return sub(x, y, z, w, this);
    }
    public Vector4L sub(long x, long y, long z, long w, Vector4L dest) {
        dest.x = this.x - x;
        dest.y = this.y - y;
        dest.z = this.z - z;
        dest.w = this.w - w;
        return dest;
    }

    /**
     * Add the supplied vector to this one.
     *
     * @param v
     *          the vector to add
     * @return this
     */
    public Vector4L add(Vector4Lc v) {
        return add(v, this);
    }
    public Vector4L add(Vector4Lc v, Vector4L dest) {
        dest.x = this.x + v.x();
        dest.y = this.y + v.y();
        dest.z = this.z + v.z();
        dest.w = this.w + v.w();
        return dest;
    }

    /**
     * Add the supplied vector to this one.
     *
     * @param v
     *          the vector to add
     * @return this
     */
    public Vector4L add(Vector4ic v) {
        return add(v, this);
    }
    public Vector4L add(Vector4ic v, Vector4L dest) {
        dest.x = this.x + v.x();
        dest.y = this.y + v.y();
        dest.z = this.z + v.z();
        dest.w = this.w + v.w();
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
     * @param w
     *          the w component to add
     * @return this
     */
    public Vector4L add(long x, long y, long z, long w) {
        return add(x, y, z, w, this);
    }
    public Vector4L add(long x, long y, long z, long w, Vector4L dest) {
        dest.x = this.x + x;
        dest.y = this.y + y;
        dest.z = this.z + z;
        dest.w = this.w + w;
        return dest;
    }

    /**
     * Multiply this Vector4L component-wise by another vector.
     *
     * @param v
     *          the other vector
     * @return this
     */
    public Vector4L mul(Vector4Lc v) {
        return mul(v, this);
    }
    public Vector4L mul(Vector4Lc v, Vector4L dest) {
        dest.x = x * v.x();
        dest.y = y * v.y();
        dest.z = z * v.z();
        dest.w = w * v.w();
        return dest;
    }

    /**
     * Multiply this Vector4L component-wise by another vector.
     *
     * @param v
     *          the other vector
     * @return this
     */
    public Vector4L mul(Vector4ic v) {
        return mul(v, this);
    }
    public Vector4L mul(Vector4ic v, Vector4L dest) {
        dest.x = x * v.x();
        dest.y = y * v.y();
        dest.z = z * v.z();
        dest.w = w * v.w();
        return dest;
    }

    /**
     * Divide this Vector4L component-wise by another vector.
     *
     * @param v
     *          the vector to divide by
     * @return this
     */
    public Vector4L div(Vector4Lc v) {
        return div(v, this);
    }
    public Vector4L div(Vector4Lc v, Vector4L dest) {
        dest.x = x / v.x();
        dest.y = y / v.y();
        dest.z = z / v.z();
        dest.w = w / v.w();
        return dest;
    }

    /**
     * Divide this Vector4L component-wise by another vector.
     *
     * @param v
     *          the vector to divide by
     * @return this
     */
    public Vector4L div(Vector4ic v) {
        return div(v, this);
    }
    public Vector4L div(Vector4ic v, Vector4L dest) {
        dest.x = x / v.x();
        dest.y = y / v.y();
        dest.z = z / v.z();
        dest.w = w / v.w();
        return dest;
    }

    /**
     * Multiply all components of this vector by the given scalar
     * value.
     *
     * @param scalar
     *          the scalar to multiply by
     * @return this
     */
    public Vector4L mul(long scalar) {
        return mul(scalar, this);
    }
    public Vector4L mul(long scalar, Vector4L dest) {
        dest.x = x * scalar;
        dest.y = y * scalar;
        dest.z = z * scalar;
        dest.w = w * scalar;
        return dest;
    }

    /**
     * Divide all components of this vector by the given scalar value.
     *
     * @param scalar
     *          the scalar to divide by
     * @return this
     */
    public Vector4L div(float scalar) {
        return div(scalar, this);
    }
    public Vector4L div(float scalar, Vector4L dest) {
        float invscalar = 1.0f / scalar;
        dest.x = (int) (x * invscalar);
        dest.y = (int) (y * invscalar);
        dest.z = (int) (z * invscalar);
        dest.w = (int) (w * invscalar);
        return dest;
    }

    /**
     * Divide all components of this vector by the given scalar value.
     *
     * @param scalar
     *          the scalar to divide by
     * @return this
     */
    public Vector4L div(long scalar) {
        return div(scalar, this);
    }
    public Vector4L div(long scalar, Vector4L dest) {
        dest.x = x / scalar;
        dest.y = y / scalar;
        dest.z = z / scalar;
        dest.w = w / scalar;
        return dest;
    }

    public long lengthSquared() {
        return x * x + y * y + z * z + w * w;
    }

    /**
     * Get the length squared of a 4-dimensional single-precision vector.
     *
     * @param x The vector's x component
     * @param y The vector's y component
     * @param z The vector's z component
     * @param w The vector's w component
     *
     * @return the length squared of the given vector
     */
    public static long lengthSquared(long x, long y, long z, long w) {
        return x * x + y * y + z * z + w * w;
    }

    public double length() {
        return Math.sqrt(x * x + y * y + z * z + w * w);
    }

    /**
     * Get the length of a 4-dimensional single-precision vector.
     *
     * @param x The vector's x component
     * @param y The vector's y component
     * @param z The vector's z component
     * @param w The vector's w component
     *
     * @return the length squared of the given vector
     */
    public static double length(long x, long y, long z, long w) {
        return Math.sqrt(x * x + y * y + z * z + w * w);
    }

    public double distance(Vector4Lc v) {
        return distance(v.x(), v.y(), v.z(), v.w());
    }
    public double distance(Vector4ic v) {
        return distance(v.x(), v.y(), v.z(), v.w());
    }

    public double distance(long x, long y, long z, long w) {
        long dx = this.x - x;
        long dy = this.y - y;
        long dz = this.z - z;
        long dw = this.w - w;
        return Math.sqrt(Math.fma(dx, dx, Math.fma(dy, dy, Math.fma(dz, dz, dw * dw))));
    }

    public long gridDistance(Vector4Lc v) {
        return Math.abs(v.x() - x()) + Math.abs(v.y() - y())  + Math.abs(v.z() - z())  + Math.abs(v.w() - w());
    }
    public long gridDistance(Vector4ic v) {
        return Math.abs(v.x() - x()) + Math.abs(v.y() - y())  + Math.abs(v.z() - z())  + Math.abs(v.w() - w());
    }

    public long gridDistance(long x, long y, long z, long w) {
        return Math.abs(x - x()) + Math.abs(y - y()) + Math.abs(z - z()) + Math.abs(w - w());
    }

    public long distanceSquared(Vector4Lc v) {
        return distanceSquared(v.x(), v.y(), v.z(), v.w());
    }
    public long distanceSquared(Vector4ic v) {
        return distanceSquared(v.x(), v.y(), v.z(), v.w());
    }

    public long distanceSquared(long x, long y, long z, long w) {
        long dx = this.x - x;
        long dy = this.y - y;
        long dz = this.z - z;
        long dw = this.w - w;
        return dx * dx + dy * dy + dz * dz + dw * dw;
    }

    /**
     * Return the distance between <code>(x1, y1, z1, w1)</code> and <code>(x2, y2, z2, w2)</code>.
     *
     * @param x1
     *          the x component of the first vector
     * @param y1
     *          the y component of the first vector
     * @param z1
     *          the z component of the first vector
     * @param w1
     *          the w component of the first vector
     * @param x2
     *          the x component of the second vector
     * @param y2
     *          the y component of the second vector
     * @param z2
     *          the z component of the second vector
     * @param w2
     *          the 2 component of the second vector
     * @return the euclidean distance
     */
    public static double distance(long x1, long y1, long z1, long w1, long x2, long y2, long z2, long w2) {
        long dx = x1 - x2;
        long dy = y1 - y2;
        long dz = z1 - z2;
        long dw = w1 - w2;
        return Math.sqrt(dx * dx + dy * dy + dz * dz + dw * dw);
    }

    /**
     * Return the squared distance between <code>(x1, y1, z1, w1)</code> and <code>(x2, y2, z2, w2)</code>.
     *
     * @param x1
     *          the x component of the first vector
     * @param y1
     *          the y component of the first vector
     * @param z1
     *          the z component of the first vector
     * @param w1
     *          the w component of the first vector
     * @param x2
     *          the x component of the second vector
     * @param y2
     *          the y component of the second vector
     * @param z2
     *          the z component of the second vector
     * @param w2
     *          the w component of the second vector
     * @return the euclidean distance squared
     */
    public static long distanceSquared(long x1, long y1, long z1, long w1, long x2, long y2, long z2, long w2) {
        long dx = x1 - x2;
        long dy = y1 - y2;
        long dz = z1 - z2;
        long dw = w1 - w2;
        return dx * dx + dy * dy + dz * dz + dw * dw;
    }

    public long dot(Vector4Lc v) {
        return x * v.x() + y * v.y() + z * v.z() + w * v.w();
    }
    public long dot(Vector4ic v) {
        return x * v.x() + y * v.y() + z * v.z() + w * v.w();
    }

    /**
     * Set all components to zero.
     *
     * @return this
     */
    public Vector4L zero() {
        x = 0L;
        y = 0L;
        z = 0L;
        w = 0L;
        return this;
    }

    /**
     * Negate this vector.
     *
     * @return this
     */
    public Vector4L negate() {
        return negate(this);
    }

    public Vector4L negate(Vector4L dest) {
        dest.x = -x;
        dest.y = -y;
        dest.z = -z;
        dest.w = -w;
        return dest;
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
        return "(" + formatter.format(x) + " " + formatter.format(y) + " " + formatter.format(z) + " " + formatter.format(w) + ")";
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeLong(x);
        out.writeLong(y);
        out.writeLong(z);
        out.writeLong(w);
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        x = in.readLong();
        y = in.readLong();
        z = in.readLong();
        w = in.readLong();
    }

    /**
     * Set the components of this vector to be the component-wise minimum of this and the other vector.
     *
     * @param v
     *          the other vector
     * @return this
     */
    public Vector4L min(Vector4Lc v) {
        return min(v, this);
    }
    public Vector4L min(Vector4Lc v, Vector4L dest) {
        dest.x = x < v.x() ? x : v.x();
        dest.y = y < v.y() ? y : v.y();
        dest.z = z < v.z() ? z : v.z();
        dest.w = w < v.w() ? w : v.w();
        return dest;
    }

    /**
     * Set the components of this vector to be the component-wise maximum of this and the other vector.
     *
     * @param v
     *          the other vector
     * @return this
     */
    public Vector4L max(Vector4Lc v) {
        return max(v, this);
    }
    public Vector4L max(Vector4Lc v, Vector4L dest) {
        dest.x = x > v.x() ? x : v.x();
        dest.y = y > v.y() ? y : v.y();
        dest.z = z > v.z() ? z : v.z();
        dest.w = w > v.w() ? w : v.w();
        return dest;
    }

    /**
     * Compute the absolute of each of this vector's components.
     * 
     * @return this
     */
    public Vector4L absolute() {
        return absolute(this);
    }

    public Vector4L absolute(Vector4L dest) {
        dest.x = Math.abs(x);
        dest.y = Math.abs(y);
        dest.z = Math.abs(z);
        dest.w = Math.abs(w);
        return dest;
    }

    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int)(x ^ (x >>> 32));
        result = prime * result + (int)(y ^ (y >>> 32));
        result = prime * result + (int)(z ^ (z >>> 32));
        result = prime * result + (int)(w ^ (w >>> 32));
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
        Vector4L other = (Vector4L) obj;
        if (x != other.x) {
            return false;
        }
        if (y != other.y) {
            return false;
        }
        if (z != other.z) {
            return false;
        }
        if (w != other.w) {
            return false;
        }
        return true;
    }

    public boolean equals(long x, long y, long z, long w) {
        if (this.x != x)
            return false;
        if (this.y != y)
            return false;
        if (this.z != z)
            return false;
        if (this.w != w)
            return false;
        return true;
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

}
