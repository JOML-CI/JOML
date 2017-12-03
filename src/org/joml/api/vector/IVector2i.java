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
 * Interface to a read-only view of a 2-dimensional vector of integers.
 *
 * @author Kai Burjack
 */
public interface IVector2i {

    /**
     * @return the value of the x component
     */
    int x();

    /**
     * @return the value of the y component
     */
    int y();

    //#ifdef __HAS_NIO__

    /**
     * Store this vector into the supplied {@link ByteBuffer} at the current buffer {@link ByteBuffer#position()
     * position}.
     * <p>
     * This method will not increment the position of the given ByteBuffer.
     * <p>
     * In order to specify the offset into the ByteBuffer at which the vector is stored, use {@link #get(int,
     * ByteBuffer)}, taking the absolute position as parameter.
     *
     * @param buffer will receive the values of this vector in <tt>x, y</tt> order
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
     * @param buffer will receive the values of this vector in <tt>x, y</tt> order
     * @return the passed in buffer
     */
    ByteBuffer get(int index, ByteBuffer buffer);

    /**
     * Store this vector into the supplied {@link IntBuffer} at the current buffer {@link IntBuffer#position()
     * position}.
     * <p>
     * This method will not increment the position of the given IntBuffer.
     * <p>
     * In order to specify the offset into the IntBuffer at which the vector is stored, use {@link #get(int,
     * IntBuffer)}, taking the absolute position as parameter.
     *
     * @param buffer will receive the values of this vector in <tt>x, y</tt> order
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
     * @param buffer will receive the values of this vector in <tt>x, y</tt> order
     * @return the passed in buffer
     */
    IntBuffer get(int index, IntBuffer buffer);
    //#endif

    /**
     * Subtract the supplied vector from this one and store the result in <code>dest</code>.
     *
     * @param v    the vector to subtract
     * @param dest will hold the result
     * @return dest
     */
    Vector2ic sub(IVector2i v, Vector2ic dest);

    /**
     * Decrement the components of this vector by the given values and store the result in <code>dest</code>.
     *
     * @param x    the x component to subtract
     * @param y    the y component to subtract
     * @param dest will hold the result
     * @return dest
     */
    Vector2ic sub(int x, int y, Vector2ic dest);

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
    double distance(IVector2i v);

    /**
     * Return the distance between <code>this</code> vector and <tt>(x, y)</tt>.
     *
     * @param x the x component of the other vector
     * @param y the y component of the other vector
     * @return the euclidean distance
     */
    double distance(int x, int y);

    /**
     * Return the square of the distance between this vector and <code>v</code>.
     *
     * @param v the other vector
     * @return the squared of the distance
     */
    long distanceSquared(IVector2i v);

    /**
     * Return the square of the distance between <code>this</code> vector and <tt>(x, y)</tt>.
     *
     * @param x the x component of the other vector
     * @param y the y component of the other vector
     * @return the square of the distance
     */
    long distanceSquared(int x, int y);

    /**
     * Add the supplied vector to this one and store the result in <code>dest</code>.
     *
     * @param v    the vector to add
     * @param dest will hold the result
     * @return dest
     */
    Vector2ic add(IVector2i v, Vector2ic dest);

    /**
     * Increment the components of this vector by the given values and store the result in <code>dest</code>.
     *
     * @param x    the x component to add
     * @param y    the y component to add
     * @param dest will hold the result
     * @return dest
     */
    Vector2ic add(int x, int y, Vector2ic dest);

    /**
     * Multiply all components of this {@link IVector2i} by the given scalar value and store the result in
     * <code>dest</code>.
     *
     * @param scalar the scalar to multiply this vector by
     * @param dest   will hold the result
     * @return dest
     */
    Vector2ic mul(int scalar, Vector2ic dest);

    /**
     * Multiply the supplied vector by this one and store the result in <code>dest</code>.
     *
     * @param v    the vector to multiply
     * @param dest will hold the result
     * @return dest
     */
    Vector2ic mul(IVector2i v, Vector2ic dest);

    /**
     * Multiply the components of this vector by the given values and store the result in <code>dest</code>.
     *
     * @param x    the x component to multiply
     * @param y    the y component to multiply
     * @param dest will hold the result
     * @return dest
     */
    Vector2ic mul(int x, int y, Vector2ic dest);

    /**
     * Negate this vector and store the result in <code>dest</code>.
     *
     * @param dest will hold the result
     * @return dest
     */
    Vector2ic negate(Vector2ic dest);
}