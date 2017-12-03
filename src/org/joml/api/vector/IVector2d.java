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

import org.joml.api.matrix.IMatrix3x2d;

//#ifdef __HAS_NIO__
import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
//#endif

/**
 * Interface to a read-only view of a 2-dimensional vector of double-precision floats.
 *
 * @author Kai Burjack
 */
public interface IVector2d {

    /**
     * @return the value of the x component
     */
    double x();

    /**
     * @return the value of the y component
     */
    double y();

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
     * Store this vector into the supplied {@link DoubleBuffer} at the current buffer {@link DoubleBuffer#position()
     * position}.
     * <p>
     * This method will not increment the position of the given DoubleBuffer.
     * <p>
     * In order to specify the offset into the DoubleBuffer at which the vector is stored, use {@link #get(int,
     * DoubleBuffer)}, taking the absolute position as parameter.
     *
     * @param buffer will receive the values of this vector in <tt>x, y</tt> order
     * @return the passed in buffer
     *
     * @see #get(int, DoubleBuffer)
     */
    DoubleBuffer get(DoubleBuffer buffer);

    /**
     * Store this vector into the supplied {@link DoubleBuffer} starting at the specified absolute buffer
     * position/index.
     * <p>
     * This method will not increment the position of the given DoubleBuffer.
     *
     * @param index  the absolute position into the DoubleBuffer
     * @param buffer will receive the values of this vector in <tt>x, y</tt> order
     * @return the passed in buffer
     */
    DoubleBuffer get(int index, DoubleBuffer buffer);
    //#endif

    /**
     * Subtract <tt>(x, y)</tt> from this vector and store the result in <code>dest</code>.
     *
     * @param x    the x component to subtract
     * @param y    the y component to subtract
     * @param dest will hold the result
     * @return dest
     */
    Vector2dc sub(double x, double y, Vector2dc dest);

    /**
     * Subtract <code>v</code> from <code>this</code> vector and store the result in <code>dest</code>.
     *
     * @param v    the vector to subtract
     * @param dest will hold the result
     * @return dest
     */
    Vector2dc sub(IVector2d v, Vector2dc dest);

    /**
     * Subtract <code>v</code> from <code>this</code> vector and store the result in <code>dest</code>.
     *
     * @param v    the vector to subtract
     * @param dest will hold the result
     * @return dest
     */
    Vector2dc sub(IVector2f v, Vector2dc dest);

    /**
     * Multiply the components of this vector by the given scalar and store the result in <code>dest</code>.
     *
     * @param scalar the value to multiply this vector's components by
     * @param dest   will hold the result
     * @return dest
     */
    Vector2dc mul(double scalar, Vector2dc dest);

    /**
     * Multiply the components of this Vector2dc by the given scalar values and store the result in <code>dest</code>.
     *
     * @param x    the x component to multiply this vector by
     * @param y    the y component to multiply this vector by
     * @param dest will hold the result
     * @return dest
     */
    Vector2dc mul(double x, double y, Vector2dc dest);

    /**
     * Multiply this Vector2dc component-wise by another Vector2dc and store the result in <code>dest</code>.
     *
     * @param v    the vector to multiply by
     * @param dest will hold the result
     * @return dest
     */
    Vector2dc mul(IVector2d v, Vector2dc dest);

    /**
     * Multiply the given 3x2 matrix <code>mat</code> with <code>this</code> and store the result in <code>dest</code>.
     * <p>
     * This method assumes the <tt>z</tt> component of <code>this</code> to be <tt>1.0</tt>.
     *
     * @param mat  the matrix to multiply this vector by
     * @param dest will hold the result
     * @return dest
     */
    Vector2dc mulPosition(IMatrix3x2d mat, Vector2dc dest);

    /**
     * Multiply the given 3x2 matrix <code>mat</code> with <code>this</code> and store the result in <code>dest</code>.
     * <p>
     * This method assumes the <tt>z</tt> component of <code>this</code> to be <tt>0.0</tt>.
     *
     * @param mat  the matrix to multiply this vector by
     * @param dest will hold the result
     * @return dest
     */
    Vector2dc mulDirection(IMatrix3x2d mat, Vector2dc dest);

    /**
     * Return the dot product of this vector and <code>v</code>.
     *
     * @param v the other vector
     * @return the dot product
     */
    double dot(IVector2d v);

    /**
     * Return the angle between this vector and the supplied vector.
     *
     * @param v the other vector
     * @return the angle, in radians
     */
    double angle(IVector2d v);

    /**
     * Return the length of this vector.
     *
     * @return the length
     */
    double length();

    /**
     * Return the distance between <code>this</code> and <code>v</code>.
     *
     * @param v the other vector
     * @return the euclidean distance
     */
    double distance(IVector2d v);

    /**
     * Return the distance between <code>this</code> and <code>v</code>.
     *
     * @param v the other vector
     * @return the euclidean distance
     */
    double distance(IVector2f v);

    /**
     * Return the distance between <code>this</code> vector and <tt>(x, y)</tt>.
     *
     * @param x the x component of the other vector
     * @param y the y component of the other vector
     * @return the euclidean distance
     */
    double distance(double x, double y);

    /**
     * Normalize this vector and store the result in <code>dest</code>.
     *
     * @param dest will hold the result
     * @return dest
     */
    Vector2dc normalize(Vector2dc dest);

    /**
     * Scale this vector to have the given length and store the result in <code>dest</code>.
     *
     * @param length the desired length
     * @param dest   will hold the result
     * @return dest
     */
    Vector2dc normalize(double length, Vector2dc dest);

    /**
     * Add <code>(x, y)</code> to this vector and store the result in <code>dest</code>.
     *
     * @param x    the x component to add
     * @param y    the y component to add
     * @param dest will hold the result
     * @return dest
     */
    Vector2dc add(double x, double y, Vector2dc dest);

    /**
     * Add <code>v</code> to this vector and store the result in <code>dest</code>.
     *
     * @param v    the vector to add
     * @param dest will hold the result
     * @return dest
     */
    Vector2dc add(IVector2d v, Vector2dc dest);

    /**
     * Add <code>v</code> to this vector and store the result in <code>dest</code>.
     *
     * @param v    the vector to add
     * @param dest will hold the result
     * @return dest
     */
    Vector2dc add(IVector2f v, Vector2dc dest);

    /**
     * Negate this vector and store the result in <code>dest</code>.
     *
     * @param dest will hold the result
     * @return dest
     */
    Vector2dc negate(Vector2dc dest);

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
    Vector2dc lerp(IVector2d other, double t, Vector2dc dest);

    /**
     * Add the component-wise multiplication of <code>a * b</code> to this vector and store the result in
     * <code>dest</code>.
     *
     * @param a    the first multiplicand
     * @param b    the second multiplicand
     * @param dest will hold the result
     * @return dest
     */
    Vector2dc fma(IVector2d a, IVector2d b, Vector2dc dest);

    /**
     * Add the component-wise multiplication of <code>a * b</code> to this vector and store the result in
     * <code>dest</code>.
     *
     * @param a    the first multiplicand
     * @param b    the second multiplicand
     * @param dest will hold the result
     * @return dest
     */
    Vector2dc fma(double a, IVector2d b, Vector2dc dest);
}