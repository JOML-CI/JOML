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

import org.joml.api.matrix.IMatrix3x2f;

//#ifdef __HAS_NIO__
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
//#endif

/**
 * Interface to a read-only view of a 2-dimensional vector of single-precision floats.
 *
 * @author Kai Burjack
 */
public interface IVector2f {

    /**
     * @return the value of the x component
     */
    float x();

    /**
     * @return the value of the y component
     */
    float y();

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
     * Store this vector into the supplied {@link FloatBuffer} at the current buffer {@link FloatBuffer#position()
     * position}.
     * <p>
     * This method will not increment the position of the given FloatBuffer.
     * <p>
     * In order to specify the offset into the FloatBuffer at which the vector is stored, use {@link #get(int,
     * FloatBuffer)}, taking the absolute position as parameter.
     *
     * @param buffer will receive the values of this vector in <tt>x, y</tt> order
     * @return the passed in buffer
     *
     * @see #get(int, FloatBuffer)
     */
    FloatBuffer get(FloatBuffer buffer);

    /**
     * Store this vector into the supplied {@link FloatBuffer} starting at the specified absolute buffer
     * position/index.
     * <p>
     * This method will not increment the position of the given FloatBuffer.
     *
     * @param index  the absolute position into the FloatBuffer
     * @param buffer will receive the values of this vector in <tt>x, y</tt> order
     * @return the passed in buffer
     */
    FloatBuffer get(int index, FloatBuffer buffer);
    //#endif

    /**
     * Subtract <code>v</code> from <code>this</code> vector and store the result in <code>dest</code>.
     *
     * @param v    the vector to subtract
     * @param dest will hold the result
     * @return dest
     */
    Vector2fc sub(IVector2f v, Vector2fc dest);

    /**
     * Subtract <tt>(x, y)</tt> from this vector and store the result in <code>dest</code>.
     *
     * @param x    the x component to subtract
     * @param y    the y component to subtract
     * @param dest will hold the result
     * @return dest
     */
    Vector2fc sub(float x, float y, Vector2fc dest);

    /**
     * Return the dot product of this vector and <code>v</code>.
     *
     * @param v the other vector
     * @return the dot product
     */
    float dot(IVector2f v);

    /**
     * Return the angle between this vector and the supplied vector.
     *
     * @param v the other vector
     * @return the angle, in radians
     */
    float angle(IVector2f v);

    /**
     * Return the length of this vector.
     *
     * @return the length
     */
    float length();

    /**
     * Return the length squared of this vector.
     *
     * @return the length squared
     */
    float lengthSquared();

    /**
     * Return the distance between this and <code>v</code>.
     *
     * @param v the other vector
     * @return the distance
     */
    float distance(IVector2f v);

    /**
     * Return the distance squared between this and <code>v</code>.
     *
     * @param v the other vector
     * @return the distance squared
     */
    float distanceSquared(IVector2f v);

    /**
     * Return the distance between <code>this</code> vector and <tt>(x, y)</tt>.
     *
     * @param x the x component of the other vector
     * @param y the y component of the other vector
     * @return the euclidean distance
     */
    float distance(float x, float y);

    /**
     * Return the distance squared between <code>this</code> vector and <tt>(x, y)</tt>.
     *
     * @param x the x component of the other vector
     * @param y the y component of the other vector
     * @return the euclidean distance squared
     */
    float distanceSquared(float x, float y);

    /**
     * Normalize this vector and store the result in <code>dest</code>.
     *
     * @param dest will hold the result
     * @return dest
     */
    Vector2fc normalize(Vector2fc dest);

    /**
     * Scale this vector to have the given length and store the result in <code>dest</code>.
     *
     * @param length the desired length
     * @param dest   will hold the result
     * @return dest
     */
    Vector2fc normalize(float length, Vector2fc dest);

    /**
     * Add the supplied vector to this one and store the result in <code>dest</code>.
     *
     * @param v    the vector to add
     * @param dest will hold the result
     * @return dest
     */
    Vector2fc add(IVector2f v, Vector2fc dest);

    /**
     * Increment the components of this vector by the given values and store the result in <code>dest</code>.
     *
     * @param x    the x component to add
     * @param y    the y component to add
     * @param dest will hold the result
     * @return dest
     */
    Vector2fc add(float x, float y, Vector2fc dest);

    /**
     * Negate this vector and store the result in <code>dest</code>.
     *
     * @param dest will hold the result
     * @return dest
     */
    Vector2fc negate(Vector2fc dest);

    /**
     * Multiply the components of this vector by the given scalar and store the result in <code>dest</code>.
     *
     * @param scalar the value to multiply this vector's components by
     * @param dest   will hold the result
     * @return dest
     */
    Vector2fc mul(float scalar, Vector2fc dest);

    /**
     * Multiply the components of this Vector2fc by the given scalar values and store the result in <code>dest</code>.
     *
     * @param x    the x component to multiply this vector by
     * @param y    the y component to multiply this vector by
     * @param dest will hold the result
     * @return dest
     */
    Vector2fc mul(float x, float y, Vector2fc dest);

    /**
     * Multiply this Vector2fc component-wise by another Vector2fc and store the result in <code>dest</code>.
     *
     * @param v    the vector to multiply by
     * @param dest will hold the result
     * @return dest
     */
    Vector2fc mul(IVector2f v, Vector2fc dest);

    /**
     * Multiply the given 3x2 matrix <code>mat</code> with <code>this</code> and store the result in <code>dest</code>.
     * <p>
     * This method assumes the <tt>z</tt> component of <code>this</code> to be <tt>1.0</tt>.
     *
     * @param mat  the matrix to multiply this vector by
     * @param dest will hold the result
     * @return dest
     */
    Vector2fc mulPosition(IMatrix3x2f mat, Vector2fc dest);

    /**
     * Multiply the given 3x2 matrix <code>mat</code> with <code>this</code> and store the result in <code>dest</code>.
     * <p>
     * This method assumes the <tt>z</tt> component of <code>this</code> to be <tt>0.0</tt>.
     *
     * @param mat  the matrix to multiply this vector by
     * @param dest will hold the result
     * @return dest
     */
    Vector2fc mulDirection(IMatrix3x2f mat, Vector2fc dest);

    /**
     * Linearly interpolate <code>this</code> and <code>other</code> using the given interpolation factor <code>t</code>
     * and store the result in <code>dest</code>.
     * <p>
     * If <code>t</code> is <tt>0.0</tt> then the result is <code>this</code>. If the interpolation factor is
     * <code>1.0</code> then the result is <code>other</code>.
     *
     * @param other the other vector
     * @param t     the interpolation factor between 0.0 and 1.0
     * @param dest  will hold the result
     * @return dest
     */
    Vector2fc lerp(IVector2f other, float t, Vector2fc dest);

    /**
     * Add the component-wise multiplication of <code>a * b</code> to this vector and store the result in
     * <code>dest</code>.
     *
     * @param a    the first multiplicand
     * @param b    the second multiplicand
     * @param dest will hold the result
     * @return dest
     */
    Vector2fc fma(IVector2f a, IVector2f b, Vector2fc dest);

    /**
     * Add the component-wise multiplication of <code>a * b</code> to this vector and store the result in
     * <code>dest</code>.
     *
     * @param a    the first multiplicand
     * @param b    the second multiplicand
     * @param dest will hold the result
     * @return dest
     */
    Vector2fc fma(float a, IVector2f b, Vector2fc dest);
}