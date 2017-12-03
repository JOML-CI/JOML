/*
 * (C) Copyright 2016-2017 JOML

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
package org.joml.api.vector;

//#ifdef __HAS_NIO__

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
//#endif

/**
 * Interface to a read-only view of a 4-dimensional vector of integers.
 *
 * @author Kai Burjack
 */
public interface IVector4i {

    /**
     * @return the value of the x component
     */
    int x();

    /**
     * @return the value of the y component
     */
    int y();

    /**
     * @return the value of the z component
     */
    int z();

    /**
     * @return the value of the w component
     */
    int w();

    //#ifdef __HAS_NIO__

    /**
     * Store this vector into the supplied {@link IntBuffer} at the current buffer {@link IntBuffer#position()
     * position}.
     * <p>
     * This method will not increment the position of the given IntBuffer.
     * <p>
     * In order to specify the offset into the IntBuffer at which the vector is stored, use {@link #get(int,
     * IntBuffer)}, taking the absolute position as parameter.
     *
     * @param buffer will receive the values of this vector in <tt>x, y, z, w</tt> order
     * @return the passed in buffer
     *
     * @see #get(int, IntBuffer)
     */
    IntBuffer get(IntBuffer buffer);

    /**
     * Store this vector into the supplied {@link IntBuffer} starting at the specified absolute buffer position/index.
     * <p>
     * This method will not increment the position of the given IntBuffer.
     *
     * @param index  the absolute position into the IntBuffer
     * @param buffer will receive the values of this vector in <tt>x, y, z, w</tt> order
     * @return the passed in buffer
     */
    IntBuffer get(int index, IntBuffer buffer);

    /**
     * Store this vector into the supplied {@link ByteBuffer} at the current buffer {@link ByteBuffer#position()
     * position}.
     * <p>
     * This method will not increment the position of the given ByteBuffer.
     * <p>
     * In order to specify the offset into the ByteBuffer at which the vector is stored, use {@link #get(int,
     * ByteBuffer)}, taking the absolute position as parameter.
     *
     * @param buffer will receive the values of this vector in <tt>x, y, z, w</tt> order
     * @return the passed in buffer
     *
     * @see #get(int, ByteBuffer)
     */
    ByteBuffer get(ByteBuffer buffer);

    /**
     * Store this vector into the supplied {@link ByteBuffer} starting at the specified absolute buffer position/index.
     * <p>
     * This method will not increment the position of the given ByteBuffer.
     *
     * @param index  the absolute position into the ByteBuffer
     * @param buffer will receive the values of this vector in <tt>x, y, z, w</tt> order
     * @return the passed in buffer
     */
    ByteBuffer get(int index, ByteBuffer buffer);
    //#endif

    /**
     * Subtract the supplied vector from this one and store the result in <code>dest</code>.
     *
     * @param v    the vector to subtract from <code>this</code>
     * @param dest will hold the result
     * @return dest
     */
    Vector4ic sub(IVector4i v, Vector4ic dest);

    /**
     * Subtract <tt>(x, y, z, w)</tt> from this and store the result in <code>dest</code>.
     *
     * @param x    the x component to subtract
     * @param y    the y component to subtract
     * @param z    the z component to subtract
     * @param w    the w component to subtract
     * @param dest will hold the result
     * @return dest
     */
    Vector4ic sub(int x, int y, int z, int w, Vector4ic dest);

    /**
     * Add the supplied vector to this one and store the result in <code>dest</code>.
     *
     * @param v    the vector to add
     * @param dest will hold the result
     * @return dest
     */
    Vector4ic add(IVector4i v, Vector4ic dest);

    /**
     * Increment the components of this vector by the given values and store the result in <code>dest</code>.
     *
     * @param x    the x component to add
     * @param y    the y component to add
     * @param z    the z component to add
     * @param w    the w component to add
     * @param dest will hold the result
     * @return dest
     */
    Vector4ic add(int x, int y, int z, int w, Vector4ic dest);

    /**
     * Multiply this Vector4ic component-wise by another IVector4i and store the result in <code>dest</code>.
     *
     * @param v    the other vector
     * @param dest will hold the result
     * @return dest
     */
    Vector4ic mul(IVector4i v, Vector4ic dest);

    /**
     * Divide this Vector4ic component-wise by another IVector4i and store the result in <code>dest</code>.
     *
     * @param v    the vector to divide by
     * @param dest will hold the result
     * @return dest
     */
    Vector4ic div(IVector4i v, Vector4ic dest);

    /**
     * Multiply all components of this {@link Vector4ic} by the given scalar value and store the result in
     * <code>dest</code>.
     *
     * @param scalar the scalar to multiply by
     * @param dest   will hold the result
     * @return dest
     */
    Vector4ic mul(float scalar, Vector4ic dest);

    /**
     * Divide all components of this {@link Vector4ic} by the given scalar value and store the result in
     * <code>dest</code>.
     *
     * @param scalar the scalar to divide by
     * @param dest   will hold the result
     * @return dest
     */
    Vector4ic div(float scalar, Vector4ic dest);

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
     * @param v the other vector
     * @return the distance
     */
    double distance(IVector4i v);

    /**
     * Return the distance between <code>this</code> vector and <tt>(x, y, z, w)</tt>.
     *
     * @param x the x component of the other vector
     * @param y the y component of the other vector
     * @param z the z component of the other vector
     * @param w the w component of the other vector
     * @return the euclidean distance
     */
    double distance(int x, int y, int z, int w);

    /**
     * Return the square of the distance between this vector and <code>v</code>.
     *
     * @param v the other vector
     * @return the squared of the distance
     */
    int distanceSquared(IVector4i v);

    /**
     * Return the square of the distance between <code>this</code> vector and <tt>(x, y, z, w)</tt>.
     *
     * @param x the x component of the other vector
     * @param y the y component of the other vector
     * @param z the z component of the other vector
     * @param w the w component of the other vector
     * @return the square of the distance
     */
    int distanceSquared(int x, int y, int z, int w);

    /**
     * Compute the dot product (inner product) of this vector and <code>v</code>.
     *
     * @param v the other vector
     * @return the dot product
     */
    int dot(IVector4i v);

    /**
     * Negate this vector and store the result in <code>dest</code>.
     *
     * @param dest will hold the result
     * @return dest
     */
    Vector4ic negate(Vector4ic dest);
}