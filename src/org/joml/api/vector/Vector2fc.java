/*
 * (C) Copyright 2015-2017 Richard Greenlees

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

import java.io.Externalizable;
import java.text.DecimalFormat;
import java.text.NumberFormat;

//#ifdef __HAS_NIO__
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
//#endif

/**
 * Represents a 2D vector with single-precision.
 *
 * @author RGreenlees
 * @author Kai Burjack
 */
public abstract class Vector2fc implements Externalizable, IVector2f {

    /**
     * Set the x and y components to the supplied value.
     *
     * @param d the value of both components
     * @return this
     */
    public abstract Vector2fc set(float d);

    /**
     * Set the x and y components to the supplied values.
     *
     * @param x the x component
     * @param y the y component
     * @return this
     */
    public abstract Vector2fc set(float x, float y);

    /**
     * Set this {@link Vector2fc} to the values of v.
     *
     * @param v the vector to copy from
     * @return this
     */
    public abstract Vector2fc set(IVector2f v);

    /**
     * Set this {@link Vector2fc} to the values of v.
     *
     * @param v the vector to copy from
     * @return this
     */
    public abstract Vector2fc set(IVector2i v);

    /**
     * Set this {@link Vector2fc} to the values of v.
     * <p>
     * Note that due to the given vector <code>v</code> storing the components in double-precision, there is the
     * possibility to lose precision.
     *
     * @param v the vector to copy from
     * @return this
     */
    public abstract Vector2fc set(IVector2d v);

    //#ifdef __HAS_NIO__

    /**
     * Read this vector from the supplied {@link ByteBuffer} at the current buffer {@link ByteBuffer#position()
     * position}.
     * <p>
     * This method will not increment the position of the given ByteBuffer.
     * <p>
     * In order to specify the offset into the ByteBuffer at which the vector is read, use {@link #set(int,
     * ByteBuffer)}, taking the absolute position as parameter.
     *
     * @param buffer values will be read in <tt>x, y</tt> order
     * @return this
     *
     * @see #set(int, ByteBuffer)
     */
    public abstract Vector2fc set(ByteBuffer buffer);

    /**
     * Read this vector from the supplied {@link ByteBuffer} starting at the specified absolute buffer position/index.
     * <p>
     * This method will not increment the position of the given ByteBuffer.
     *
     * @param index  the absolute position into the ByteBuffer
     * @param buffer values will be read in <tt>x, y</tt> order
     * @return this
     */
    public abstract Vector2fc set(int index, ByteBuffer buffer);

    /**
     * Read this vector from the supplied {@link FloatBuffer} at the current buffer {@link FloatBuffer#position()
     * position}.
     * <p>
     * This method will not increment the position of the given FloatBuffer.
     * <p>
     * In order to specify the offset into the FloatBuffer at which the vector is read, use {@link #set(int,
     * FloatBuffer)}, taking the absolute position as parameter.
     *
     * @param buffer values will be read in <tt>x, y</tt> order
     * @return this
     *
     * @see #set(int, FloatBuffer)
     */
    public abstract Vector2fc set(FloatBuffer buffer);

    /**
     * Read this vector from the supplied {@link FloatBuffer} starting at the specified absolute buffer position/index.
     * <p>
     * This method will not increment the position of the given FloatBuffer.
     *
     * @param index  the absolute position into the FloatBuffer
     * @param buffer values will be read in <tt>x, y</tt> order
     * @return this
     */
    public abstract Vector2fc set(int index, FloatBuffer buffer);
    //#endif

    /**
     * Set the value of the specified component of this vector.
     *
     * @param component the component whose value to set, within <tt>[0..1]</tt>
     * @param value     the value to set
     * @return this
     */
    public abstract Vector2fc setComponent(int component, float value);

    /**
     * Set this vector to be one of its perpendicular vectors.
     *
     * @return this
     */
    public abstract Vector2fc perpendicular();

    /**
     * Subtract <code>v</code> from this vector.
     *
     * @param v the vector to subtract
     * @return this
     */
    public abstract Vector2fc sub(IVector2f v);

    /**
     * Subtract <tt>(x, y)</tt> from this vector.
     *
     * @param x the x component to subtract
     * @param y the y component to subtract
     * @return this
     */
    public abstract Vector2fc sub(float x, float y);

    /**
     * Normalize this vector.
     *
     * @return this
     */
    public abstract Vector2fc normalize();

    /**
     * Scale this vector to have the given length.
     *
     * @param length the desired length
     * @return this
     */
    public abstract Vector2fc normalize(float length);

    /**
     * Add <code>v</code> to this vector.
     *
     * @param v the vector to add
     * @return this
     */
    public abstract Vector2fc add(IVector2f v);

    /**
     * Increment the components of this vector by the given values.
     *
     * @param x the x component to add
     * @param y the y component to add
     * @return this
     */
    public abstract Vector2fc add(float x, float y);

    /**
     * Set all components to zero.
     *
     * @return this
     */
    public abstract Vector2fc zero();

    /**
     * Negate this vector.
     *
     * @return this
     */
    public abstract Vector2fc negate();

    /**
     * Multiply the components of this vector by the given scalar.
     *
     * @param scalar the value to multiply this vector's components by
     * @return this
     */
    public abstract Vector2fc mul(float scalar);

    /**
     * Multiply the components of this Vector2fc by the given scalar values and store the result in <code>this</code>.
     *
     * @param x the x component to multiply this vector by
     * @param y the y component to multiply this vector by
     * @return this
     */
    public abstract Vector2fc mul(float x, float y);

    /**
     * Multiply this Vector2fc component-wise by another Vector2fc.
     *
     * @param v the vector to multiply by
     * @return this
     */
    public abstract Vector2fc mul(IVector2f v);

    /**
     * Multiply the given 3x2 matrix <code>mat</code> with <code>this</code>.
     * <p>
     * This method assumes the <tt>z</tt> component of <code>this</code> to be <tt>1.0</tt>.
     *
     * @param mat the matrix to multiply this vector by
     * @return this
     */
    public abstract Vector2fc mulPosition(IMatrix3x2f mat);

    /**
     * Multiply the given 3x2 matrix <code>mat</code> with <code>this</code>.
     * <p>
     * This method assumes the <tt>z</tt> component of <code>this</code> to be <tt>0.0</tt>.
     *
     * @param mat the matrix to multiply this vector by
     * @return this
     */
    public abstract Vector2fc mulDirection(IMatrix3x2f mat);

    /**
     * Linearly interpolate <code>this</code> and <code>other</code> using the given interpolation factor <code>t</code>
     * and store the result in <code>this</code>.
     * <p>
     * If <code>t</code> is <tt>0.0</tt> then the result is <code>this</code>. If the interpolation factor is
     * <code>1.0</code> then the result is <code>other</code>.
     *
     * @param other the other vector
     * @param t     the interpolation factor between 0.0 and 1.0
     * @return this
     */
    public abstract Vector2fc lerp(IVector2f other, float t);

    @Override
    public abstract int hashCode();

    @Override
    public abstract boolean equals(Object obj);

    /**
     * Return a string representation of this vector.
     * <p>
     * This method creates a new {@link DecimalFormat} on every invocation with the format string "<tt>0.000E0;-</tt>".
     *
     * @return the string representation
     */
    public abstract String toString();

    /**
     * Return a string representation of this vector by formatting the vector components with the given {@link
     * NumberFormat}.
     *
     * @param formatter the {@link NumberFormat} used to format the vector components with
     * @return the string representation
     */
    public abstract String toString(NumberFormat formatter);

    /**
     * Add the component-wise multiplication of <code>a * b</code> to this vector.
     *
     * @param a the first multiplicand
     * @param b the second multiplicand
     * @return this
     */
    public abstract Vector2fc fma(IVector2f a, IVector2f b);

    /**
     * Add the component-wise multiplication of <code>a * b</code> to this vector.
     *
     * @param a the first multiplicand
     * @param b the second multiplicand
     * @return this
     */
    public abstract Vector2fc fma(float a, IVector2f b);
}
