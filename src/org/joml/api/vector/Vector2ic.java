/*
 * (C) Copyright 2015-2017 Richard Greenlees
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
 *
 */
package org.joml.api.vector;

import java.io.Externalizable;
import java.text.DecimalFormat;
import java.text.NumberFormat;

//#ifdef __HAS_NIO__
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
//#endif

/**
 * Represents a 2D vector with single-precision.
 *
 * @author RGreenlees
 * @author Kai Burjack
 * @author Hans Uhlig
 */
public abstract class Vector2ic implements Externalizable, IVector2i {

    /**
     * Set the x and y components to the supplied value.
     *
     * @param s scalar value of both components
     * @return this
     */
    public abstract Vector2ic set(int s);

    /**
     * Set the x and y components to the supplied values.
     *
     * @param x the x component
     * @param y the y component
     * @return this
     */
    public abstract Vector2ic set(int x, int y);

    /**
     * Set this {@link Vector2ic} to the values of v.
     *
     * @param v the vector to copy from
     * @return this
     */
    public abstract Vector2ic set(IVector2i v);

    /**
     * Set this {@link Vector2ic} to the values of v.
     * <p>
     * Note that due to the given vector <code>v</code> storing the components in double-precision, there is the
     * possibility to lose precision.
     *
     * @param v the vector to copy from
     * @return this
     */
    public abstract Vector2ic set(IVector2d v);

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
    public abstract Vector2ic set(ByteBuffer buffer);

    /**
     * Read this vector from the supplied {@link ByteBuffer} starting at the specified absolute buffer position/index.
     * <p>
     * This method will not increment the position of the given ByteBuffer.
     *
     * @param index  the absolute position into the ByteBuffer
     * @param buffer values will be read in <tt>x, y</tt> order
     * @return this
     */
    public abstract Vector2ic set(int index, ByteBuffer buffer);

    /**
     * Read this vector from the supplied {@link IntBuffer} at the current buffer {@link IntBuffer#position()
     * position}.
     * <p>
     * This method will not increment the position of the given IntBuffer.
     * <p>
     * In order to specify the offset into the IntBuffer at which the vector is read, use {@link #set(int, IntBuffer)},
     * taking the absolute position as parameter.
     *
     * @param buffer values will be read in <tt>x, y</tt> order
     * @return this
     *
     * @see #set(int, IntBuffer)
     */
    public abstract Vector2ic set(IntBuffer buffer);

    /**
     * Read this vector from the supplied {@link IntBuffer} starting at the specified absolute buffer position/index.
     * <p>
     * This method will not increment the position of the given IntBuffer.
     *
     * @param index  the absolute position into the IntBuffer
     * @param buffer values will be read in <tt>x, y</tt> order
     * @return this
     */
    public abstract Vector2ic set(int index, IntBuffer buffer);
    //#endif

    /**
     * Set the value of the specified component of this vector.
     *
     * @param component the component whose value to set, within <tt>[0..1]</tt>
     * @param value     the value to set
     * @return this
     */
    public abstract Vector2ic setComponent(int component, int value);

    /**
     * Subtract the supplied vector from this one and store the result in <code>this</code>.
     *
     * @param v the vector to subtract
     * @return this
     */
    public abstract Vector2ic sub(IVector2i v);

    /**
     * Decrement the components of this vector by the given values.
     *
     * @param x the x component to subtract
     * @param y the y component to subtract
     * @return this
     */
    public abstract Vector2ic sub(int x, int y);

    /**
     * Add <code>v</code> to this vector.
     *
     * @param v the vector to add
     * @return this
     */
    public abstract Vector2ic add(IVector2i v);

    /**
     * Increment the components of this vector by the given values.
     *
     * @param x the x component to add
     * @param y the y component to add
     * @return this
     */
    public abstract Vector2ic add(int x, int y);

    /**
     * Multiply all components of this {@link Vector2ic} by the given scalar value.
     *
     * @param scalar the scalar to multiply this vector by
     * @return this
     */
    public abstract Vector2ic mul(int scalar);

    /**
     * Add the supplied vector by this one.
     *
     * @param v the vector to multiply
     * @return this
     */
    public abstract Vector2ic mul(IVector2i v);

    /**
     * Multiply the components of this vector by the given values.
     *
     * @param x the x component to multiply
     * @param y the y component to multiply
     * @return this
     */
    public abstract Vector2ic mul(int x, int y);

    /**
     * Set all components to zero.
     *
     * @return this
     */
    public abstract Vector2ic zero();

    /**
     * Negate this vector.
     *
     * @return this
     */
    public abstract Vector2ic negate();

    public abstract int hashCode();

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
}
