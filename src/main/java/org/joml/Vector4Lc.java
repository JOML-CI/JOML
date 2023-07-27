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

//#ifdef __HAS_NIO__
import java.nio.ByteBuffer;
import java.nio.LongBuffer;
//#endif

/**
 * Interface to a read-only view of a 4-dimensional vector of longs.
 * 
 * @author Kai Burjack
 */
public interface Vector4Lc {

    /**
     * @return the value of the x component
     */
    long x();

    /**
     * @return the value of the y component
     */
    long y();

    /**
     * @return the value of the z component
     */
    long z();

    /**
     * @return the value of the w component
     */
    long w();

//#ifdef __HAS_NIO__
    /**
     * Store this vector into the supplied {@link LongBuffer} at the current
     * buffer {@link LongBuffer#position() position}.
     * <p>
     * This method will not increment the position of the given LongBuffer.
     * <p>
     * In order to specify the offset into the LongBuffer at which the vector is
     * stored, use {@link #get(int, LongBuffer)}, taking the absolute position as
     * parameter.
     *
     * @see #get(int, LongBuffer)
     *
     * @param buffer
     *          will receive the values of this vector in <code>x, y, z, w</code> order
     * @return the passed in buffer
     */
    LongBuffer get(LongBuffer buffer);

    /**
     * Store this vector into the supplied {@link LongBuffer} starting at the
     * specified absolute buffer position/index.
     * <p>
     * This method will not increment the position of the given LongBuffer.
     *
     * @param index
     *          the absolute position into the LongBuffer
     * @param buffer
     *          will receive the values of this vector in <code>x, y, z, w</code> order
     * @return the passed in buffer
     */
    LongBuffer get(int index, LongBuffer buffer);

    /**
     * Store this vector into the supplied {@link ByteBuffer} at the current
     * buffer {@link ByteBuffer#position() position}.
     * <p>
     * This method will not increment the position of the given ByteBuffer.
     * <p>
     * In order to specify the offset into the ByteBuffer at which the vector is
     * stored, use {@link #get(int, ByteBuffer)}, taking the absolute position
     * as parameter.
     *
     * @see #get(int, ByteBuffer)
     *
     * @param buffer
     *          will receive the values of this vector in <code>x, y, z, w</code> order
     * @return the passed in buffer
     */
    ByteBuffer get(ByteBuffer buffer);

    /**
     * Store this vector into the supplied {@link ByteBuffer} starting at the
     * specified absolute buffer position/index.
     * <p>
     * This method will not increment the position of the given ByteBuffer.
     *
     * @param index
     *          the absolute position into the ByteBuffer
     * @param buffer
     *          will receive the values of this vector in <code>x, y, z, w</code> order
     * @return the passed in buffer
     */
    ByteBuffer get(int index, ByteBuffer buffer);
//#endif

//#ifdef __HAS_UNSAFE__
    /**
     * Store this vector at the given off-heap memory address.
     * <p>
     * This method will throw an {@link UnsupportedOperationException} when JOML is used with `-Djoml.nounsafe`.
     * <p>
     * <em>This method is unsafe as it can result in a crash of the JVM process when the specified address range does not belong to this process.</em>
     * 
     * @param address
     *            the off-heap address where to store this vector
     * @return this
     */
    Vector4Lc getToAddress(long address);
//#endif

    /**
     * Subtract the supplied vector from this one and store the result in
     * <code>dest</code>.
     *
     * @param v
     *          the vector to subtract from <code>this</code>
     * @param dest
     *          will hold the result
     * @return dest
     */
    Vector4L sub(Vector4Lc v, Vector4L dest);

    /**
     * Subtract the supplied vector from this one and store the result in
     * <code>dest</code>.
     *
     * @param v
     *          the vector to subtract from <code>this</code>
     * @param dest
     *          will hold the result
     * @return dest
     */
    Vector4L sub(Vector4ic v, Vector4L dest);

    /**
     * Subtract <code>(x, y, z, w)</code> from this and store the result in
     * <code>dest</code>.
     *
     * @param x
     *          the x component to subtract
     * @param y
     *          the y component to subtract
     * @param z
     *          the z component to subtract
     * @param w
     *          the w component to subtract
     * @param dest
     *          will hold the result
     * @return dest
     */
    Vector4L sub(long x, long y, long z, long w, Vector4L dest);

    /**
     * Add the supplied vector to this one and store the result in
     * <code>dest</code>.
     *
     * @param v
     *          the vector to add
     * @param dest
     *          will hold the result
     * @return dest
     */
    Vector4L add(Vector4Lc v, Vector4L dest);

    /**
     * Add the supplied vector to this one and store the result in
     * <code>dest</code>.
     *
     * @param v
     *          the vector to add
     * @param dest
     *          will hold the result
     * @return dest
     */
    Vector4L add(Vector4ic v, Vector4L dest);

    /**
     * Increment the components of this vector by the given values and store the
     * result in <code>dest</code>.
     *
     * @param x
     *          the x component to add
     * @param y
     *          the y component to add
     * @param z
     *          the z component to add
     * @param w
     *          the w component to add
     * @param dest
     *          will hold the result
     * @return dest
     */
    Vector4L add(long x, long y, long z, long w, Vector4L dest);

    /**
     * Multiply this Vector4L component-wise by another Vector4Lc and store the
     * result in <code>dest</code>.
     *
     * @param v
     *          the other vector
     * @param dest
     *          will hold the result
     * @return dest
     */
    Vector4L mul(Vector4Lc v, Vector4L dest);

    /**
     * Multiply this Vector4L component-wise by another Vector4ic and store the
     * result in <code>dest</code>.
     *
     * @param v
     *          the other vector
     * @param dest
     *          will hold the result
     * @return dest
     */
    Vector4L mul(Vector4ic v, Vector4L dest);

    /**
     * Divide this Vector4L component-wise by another Vector4Lc and store the
     * result in <code>dest</code>.
     *
     * @param v
     *          the vector to divide by
     * @param dest
     *          will hold the result
     * @return dest
     */
    Vector4L div(Vector4Lc v, Vector4L dest);

    /**
     * Divide this Vector4L component-wise by another Vector4ic and store the
     * result in <code>dest</code>.
     *
     * @param v
     *          the vector to divide by
     * @param dest
     *          will hold the result
     * @return dest
     */
    Vector4L div(Vector4ic v, Vector4L dest);

    /**
     * Multiply all components of this vector by the given scalar
     * value and store the result in <code>dest</code>.
     *
     * @param scalar
     *          the scalar to multiply by
     * @param dest
     *          will hold the result
     * @return dest
     */
    Vector4L mul(long scalar, Vector4L dest);

    /**
     * Divide all components of this vector by the given scalar value
     * and store the result in <code>dest</code>.
     *
     * @param scalar
     *          the scalar to divide by
     * @param dest
     *          will hold the result
     * @return dest
     */
    Vector4L div(float scalar, Vector4L dest);

    /**
     * Divide all components of this vector by the given scalar value
     * and store the result in <code>dest</code>.
     *
     * @param scalar
     *          the scalar to divide by
     * @param dest
     *          will hold the result
     * @return dest
     */
    Vector4L div(long scalar, Vector4L dest);

    /**
     * Return the length squared of this vector.
     *
     * @return the length squared
     */
    long lengthSquared();

    /**
     * Return the length of this vector.
     *
     * @return the length
     */
    double length();

    /**
     * Return the distance between this Vector and <code>v</code>.
     *
     * @param v
     *          the other vector
     * @return the distance
     */
    double distance(Vector4Lc v);

    /**
     * Return the distance between this Vector and <code>v</code>.
     *
     * @param v
     *          the other vector
     * @return the distance
     */
    double distance(Vector4ic v);

    /**
     * Return the distance between <code>this</code> vector and <code>(x, y, z, w)</code>.
     *
     * @param x
     *          the x component of the other vector
     * @param y
     *          the y component of the other vector
     * @param z
     *          the z component of the other vector
     * @param w
     *          the w component of the other vector
     * @return the euclidean distance
     */
    double distance(long x, long y, long z, long w);

    /**
     * Return the grid distance in between (aka 1-Norm, Minkowski or Manhattan distance)
     * <code>(x, y)</code>.
     *
     * @param v
     *          the other vector
     * @return the grid distance
     */
    long gridDistance(Vector4Lc v);

    /**
     * Return the grid distance in between (aka 1-Norm, Minkowski or Manhattan distance)
     * <code>(x, y)</code>.
     *
     * @param v
     *          the other vector
     * @return the grid distance
     */
    long gridDistance(Vector4ic v);

    /**
     * Return the grid distance in between (aka 1-Norm, Minkowski or Manhattan distance)
     * <code>(x, y)</code>.
     *
     * @param x
     *          the x component of the other vector
     * @param y
     *          the y component of the other vector
     * @param z
     *          the z component of the other vector
     * @param w
     *          the w component of the other vector
     * @return the grid distance
     */
    long gridDistance(long x, long y, long z, long w);

    /**
     * Return the square of the distance between this vector and <code>v</code>.
     *
     * @param v
     *          the other vector
     * @return the squared of the distance
     */
    long distanceSquared(Vector4Lc v);

    /**
     * Return the square of the distance between this vector and <code>v</code>.
     *
     * @param v
     *          the other vector
     * @return the squared of the distance
     */
    long distanceSquared(Vector4ic v);

    /**
     * Return the square of the distance between <code>this</code> vector and
     * <code>(x, y, z, w)</code>.
     *
     * @param x
     *          the x component of the other vector
     * @param y
     *          the y component of the other vector
     * @param z
     *          the z component of the other vector
     * @param w
     *          the w component of the other vector
     * @return the square of the distance
     */
    long distanceSquared(long x, long y, long z, long w);

    /**
     * Compute the dot product (inner product) of this vector and <code>v</code>.
     *
     * @param v
     *          the other vector
     * @return the dot product
     */
    long dot(Vector4Lc v);

    /**
     * Compute the dot product (inner product) of this vector and <code>v</code>.
     *
     * @param v
     *          the other vector
     * @return the dot product
     */
    long dot(Vector4ic v);

    /**
     * Negate this vector and store the result in <code>dest</code>.
     *
     * @param dest
     *          will hold the result
     * @return dest
     */
    Vector4L negate(Vector4L dest);

    /**
     * Set the components of <code>dest</code> to be the component-wise minimum of this and the other vector.
     *
     * @param v
     *          the other vector
     * @param dest
     *          will hold the result
     * @return dest
     */
    Vector4L min(Vector4Lc v, Vector4L dest);

    /**
     * Set the components of <code>dest</code> to be the component-wise maximum of this and the other vector.
     *
     * @param v
     *          the other vector
     * @param dest
     *          will hold the result
     * @return dest
     */
    Vector4L max(Vector4Lc v, Vector4L dest);

    /**
     * Get the value of the specified component of this vector.
     * 
     * @param component
     *          the component, within <code>[0..3]</code>
     * @return the value
     * @throws IllegalArgumentException if <code>component</code> is not within <code>[0..3]</code>
     */
    long get(int component) throws IllegalArgumentException;

    /**
     * Determine the component with the biggest absolute value.
     * 
     * @return the component index, within <code>[0..3]</code>
     */
    int maxComponent();

    /**
     * Determine the component with the smallest (towards zero) absolute value.
     * 
     * @return the component index, within <code>[0..3]</code>
     */
    int minComponent();

    /**
     * Compute the absolute of each of this vector's components
     * and store the result into <code>dest</code>.
     * 
     * @param dest
     *          will hold the result
     * @return dest
     */
    Vector4L absolute(Vector4L dest);

    /**
     * Compare the vector components of <code>this</code> vector with the given <code>(x, y, z, w)</code>
     * and return whether all of them are equal.
     *
     * @param x
     *          the x component to compare to
     * @param y
     *          the y component to compare to
     * @param z
     *          the z component to compare to
     * @param w
     *          the w component to compare to
     * @return <code>true</code> if all the vector components are equal
     */
    boolean equals(long x, long y, long z, long w);

}
