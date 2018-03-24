/*
 * (C) Copyright 2016-2018 JOML

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

//#ifdef __HAS_NIO__
import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
//#endif

/**
 * Interface to a read-only view of a 2-dimensional vector of double-precision floats.
 * 
 * @author Kai Burjack
 */
public interface Vector2dc {

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
     * Store this vector into the supplied {@link ByteBuffer} at the current
     * buffer {@link ByteBuffer#position() position}.
     * <p>
     * This method will not increment the position of the given ByteBuffer.
     * <p>
     * In order to specify the offset into the ByteBuffer at which
     * the vector is stored, use {@link #get(int, ByteBuffer)}, taking
     * the absolute position as parameter.
     *
     * @param buffer
     *          will receive the values of this vector in <tt>x, y</tt> order
     * @return the passed in buffer
     * @see #get(int, ByteBuffer)
     */
    ByteBuffer get(ByteBuffer buffer);

    /**
     * Store this vector into the supplied {@link ByteBuffer} starting at the specified
     * absolute buffer position/index.
     * <p>
     * This method will not increment the position of the given ByteBuffer.
     *
     * @param index 
     *          the absolute position into the ByteBuffer
     * @param buffer
     *          will receive the values of this vector in <tt>x, y</tt> order
     * @return the passed in buffer
     */
    ByteBuffer get(int index, ByteBuffer buffer);

    /**
     * Store this vector into the supplied {@link DoubleBuffer} at the current
     * buffer {@link DoubleBuffer#position() position}.
     * <p>
     * This method will not increment the position of the given DoubleBuffer.
     * <p>
     * In order to specify the offset into the DoubleBuffer at which
     * the vector is stored, use {@link #get(int, DoubleBuffer)}, taking
     * the absolute position as parameter.
     *
     * @param buffer
     *          will receive the values of this vector in <tt>x, y</tt> order
     * @return the passed in buffer
     * @see #get(int, DoubleBuffer)
     */
    DoubleBuffer get(DoubleBuffer buffer);

    /**
     * Store this vector into the supplied {@link DoubleBuffer} starting at the specified
     * absolute buffer position/index.
     * <p>
     * This method will not increment the position of the given DoubleBuffer.
     *
     * @param index
     *          the absolute position into the DoubleBuffer
     * @param buffer
     *          will receive the values of this vector in <tt>x, y</tt> order
     * @return the passed in buffer
     */
    DoubleBuffer get(int index, DoubleBuffer buffer);
//#endif

//#ifndef __GWT__
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
    Vector2dc getToAddress(long address);
//#endif

    /**
     * Subtract <tt>(x, y)</tt> from this vector and store the result in <code>dest</code>.
     * 
     * @param x
     *          the x component to subtract
     * @param y
     *          the y component to subtract
     * @param dest
     *          will hold the result         
     * @return dest
     */
    Vector2d sub(double x, double y, Vector2d dest);

    /**
     * Subtract <code>v</code> from <code>this</code> vector and store the result in <code>dest</code>.
     * 
     * @param v
     *          the vector to subtract
     * @param dest
     *          will hold the result
     * @return dest
     */
    Vector2d sub(Vector2dc v, Vector2d dest);

    /**
     * Subtract <code>v</code> from <code>this</code> vector and store the result in <code>dest</code>.
     * 
     * @param v
     *          the vector to subtract
     * @param dest
     *          will hold the result
     * @return dest
     */
    Vector2d sub(Vector2fc v, Vector2d dest);

    /**
     * Multiply the components of this vector by the given scalar and store the result in <code>dest</code>.
     * 
     * @param scalar
     *        the value to multiply this vector's components by
     * @param dest
     *        will hold the result
     * @return dest
     */
    Vector2d mul(double scalar, Vector2d dest);

    /**
     * Multiply the components of this Vector2d by the given scalar values and store the result in <code>dest</code>.
     * 
     * @param x
     *          the x component to multiply this vector by
     * @param y
     *          the y component to multiply this vector by
     * @param dest
     *          will hold the result
     * @return dest
     */
    Vector2d mul(double x, double y, Vector2d dest);

    /**
     * Multiply this Vector2d component-wise by another Vector2d and store the result in <code>dest</code>.
     * 
     * @param v
     *          the vector to multiply by
     * @param dest
     *          will hold the result
     * @return dest
     */
    Vector2d mul(Vector2dc v, Vector2d dest);

    /**
     * Multiply the given 3x2 matrix <code>mat</code> with <code>this</code> and store the
     * result in <code>dest</code>.
     * <p>
     * This method assumes the <tt>z</tt> component of <code>this</code> to be <tt>1.0</tt>.
     * 
     * @param mat
     *          the matrix to multiply this vector by
     * @param dest
     *          will hold the result
     * @return dest
     */
    Vector2d mulPosition(Matrix3x2dc mat, Vector2d dest);

    /**
     * Multiply the given 3x2 matrix <code>mat</code> with <code>this</code> and store the
     * result in <code>dest</code>.
     * <p>
     * This method assumes the <tt>z</tt> component of <code>this</code> to be <tt>0.0</tt>.
     * 
     * @param mat
     *          the matrix to multiply this vector by
     * @param dest
     *          will hold the result
     * @return dest
     */
    Vector2d mulDirection(Matrix3x2dc mat, Vector2d dest);

    /**
     * Return the dot product of this vector and <code>v</code>.
     * 
     * @param v
     *          the other vector
     * @return the dot product
     */
    double dot(Vector2dc v);

    /**
     * Return the angle between this vector and the supplied vector.
     * 
     * @param v
     *          the other vector
     * @return the angle, in radians
     */
    double angle(Vector2dc v);

    /**
     * Return the length of this vector.
     * 
     * @return the length
     */
    double length();

    /**
     * Return the length squared of this vector.
     *
     * @return the length squared
     */
    double lengthSquared();

    /**
     * Return the distance between <code>this</code> and <code>v</code>.
     * 
     * @param v
     *          the other vector
     * @return the euclidean distance
     */
    double distance(Vector2dc v);

    /**
     * Return the distance between <code>this</code> and <code>v</code>.
     * 
     * @param v
     *          the other vector
     * @return the euclidean distance
     */
    double distance(Vector2fc v);

    /**
     * Return the distance between <code>this</code> vector and <tt>(x, y)</tt>.
     * 
     * @param x
     *          the x component of the other vector
     * @param y
     *          the y component of the other vector
     * @return the euclidean distance
     */
    double distance(double x, double y);

    /**
     * Normalize this vector and store the result in <code>dest</code>.
     * 
     * @param dest
     *          will hold the result
     * @return dest
     */
    Vector2d normalize(Vector2d dest);

    /**
     * Scale this vector to have the given length and store the result in <code>dest</code>.
     * 
     * @param length
     *          the desired length
     * @param dest
     *          will hold the result
     * @return dest
     */
    Vector2d normalize(double length, Vector2d dest);

    /**
     * Add <code>(x, y)</code> to this vector and store the result in <code>dest</code>.
     * 
     * @param x
     *          the x component to add
     * @param y
     *          the y component to add
     * @param dest
     *          will hold the result
     * @return dest
     */
    Vector2d add(double x, double y, Vector2d dest);

    /**
     * Add <code>v</code> to this vector and store the result in <code>dest</code>.
     * 
     * @param v
     *          the vector to add
     * @param dest
     *          will hold the result
     * @return dest
     */
    Vector2d add(Vector2dc v, Vector2d dest);

    /**
     * Add <code>v</code> to this vector and store the result in <code>dest</code>.
     * 
     * @param v
     *          the vector to add
     * @param dest
     *          will hold the result
     * @return dest
     */
    Vector2d add(Vector2fc v, Vector2d dest);

    /**
     * Negate this vector and store the result in <code>dest</code>.
     * 
     * @param dest
     *          will hold the result
     * @return dest
     */
    Vector2d negate(Vector2d dest);

    /**
     * Linearly interpolate <code>this</code> and <code>other</code> using the given interpolation factor <code>t</code>
     * and store the result in <code>dest</code>.
     * <p>
     * If <code>t</code> is <tt>0.0</tt> then the result is <code>this</code>. If the interpolation factor is <code>1.0</code>
     * then the result is <code>other</code>.
     * 
     * @param other
     *          the other vector
     * @param t
     *          the interpolation factor between 0.0 and 1.0
     * @param dest
     *          will hold the result
     * @return dest
     */
    Vector2d lerp(Vector2dc other, double t, Vector2d dest);

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
     * @return dest
     */
    Vector2d fma(Vector2dc a, Vector2dc b, Vector2d dest);

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
     * @return dest
     */
    Vector2d fma(double a, Vector2dc b, Vector2d dest);

    /**
     * Set the components of <code>dest</code> to be the component-wise minimum of this and the other vector.
     *
     * @param v
     *          the other vector
     * @param dest
     * 			will hold the result
     * @return dest
     */
    Vector2d min(Vector2dc v, Vector2d dest);

    /**
     * Set the components of <code>dest</code> to be the component-wise maximum of this and the other vector.
     *
     * @param v
     *          the other vector
     * @param dest
     * 			will hold the result
     * @return dest
     */
    Vector2d max(Vector2dc v, Vector2d dest);

    /**
     * Get the value of the specified component of this vector.
     * 
     * @param component
     *          the component, within <tt>[0..1]</tt>
     * @return the value
     * @throws IllegalArgumentException if <code>component</code> is not within <tt>[0..1]</tt>
     */
    double get(int component) throws IllegalArgumentException;

}