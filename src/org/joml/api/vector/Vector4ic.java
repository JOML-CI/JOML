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
 * Contains the definition of a Vector comprising 4 ints and associated transformations.
 *
 * @author Richard Greenlees
 * @author Kai Burjack
 * @author Hans Uhlig
 */
public abstract class Vector4ic implements Externalizable, IVector4i {

    /**
     * Set this {@link Vector4ic} to the values of the given <code>v</code>.
     *
     * @param v the vector whose values will be copied into this
     * @return this
     */
    public abstract Vector4ic set(IVector4i v);

    /**
     * Set the first three components of this to the components of <code>v</code> and the last component to
     * <code>w</code>.
     *
     * @param v the {@link IVector3i} to copy
     * @param w the w component
     * @return this
     */
    public abstract Vector4ic set(IVector3i v, int w);

    /**
     * Sets the first two components of this to the components of given <code>v</code> and last two components to the
     * given <code>z</code>, and <code>w</code>.
     *
     * @param v the {@link IVector2i}
     * @param z the z component
     * @param w the w component
     * @return this
     */
    public abstract Vector4ic set(IVector2i v, int z, int w);

    /**
     * Set the x, y, z, and w components to the supplied value.
     *
     * @param s the value of all four components
     * @return this
     */
    public abstract Vector4ic set(int s);

    /**
     * Set the x, y, z, and w components to the supplied values.
     *
     * @param x the x component
     * @param y the y component
     * @param z the z component
     * @param w the w component
     * @return this
     */
    public abstract Vector4ic set(int x, int y, int z, int w);

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
     * @param buffer values will be read in <tt>x, y, z, w</tt> order
     * @return this
     *
     * @see #set(int, ByteBuffer)
     */
    public abstract Vector4ic set(ByteBuffer buffer);

    /**
     * Read this vector from the supplied {@link ByteBuffer} starting at the specified absolute buffer position/index.
     * <p>
     * This method will not increment the position of the given ByteBuffer.
     *
     * @param index  the absolute position into the ByteBuffer
     * @param buffer values will be read in <tt>x, y, z, w</tt> order
     * @return this
     */
    public abstract Vector4ic set(int index, ByteBuffer buffer);

    /**
     * Read this vector from the supplied {@link IntBuffer} at the current buffer {@link IntBuffer#position()
     * position}.
     * <p>
     * This method will not increment the position of the given IntBuffer.
     * <p>
     * In order to specify the offset into the IntBuffer at which the vector is read, use {@link #set(int, IntBuffer)},
     * taking the absolute position as parameter.
     *
     * @param buffer values will be read in <tt>x, y, z, w</tt> order
     * @return this
     *
     * @see #set(int, IntBuffer)
     */
    public abstract Vector4ic set(IntBuffer buffer);

    /**
     * Read this vector from the supplied {@link IntBuffer} starting at the specified absolute buffer position/index.
     * <p>
     * This method will not increment the position of the given IntBuffer.
     *
     * @param index  the absolute position into the IntBuffer
     * @param buffer values will be read in <tt>x, y, z, w</tt> order
     * @return this
     */
    public abstract Vector4ic set(int index, IntBuffer buffer);
    //#endif

    /**
     * Set the value of the specified component of this vector.
     *
     * @param component the component whose value to set, within <tt>[0..3]</tt>
     * @param value     the value to set
     * @return this
     */
    public abstract Vector4ic setComponent(int component, int value);

    /**
     * Subtract the supplied vector from this one.
     *
     * @param v the vector to subtract
     * @return this
     */
    public abstract Vector4ic sub(IVector4i v);

    /**
     * Subtract <tt>(x, y, z, w)</tt> from this.
     *
     * @param x the x component to subtract
     * @param y the y component to subtract
     * @param z the z component to subtract
     * @param w the w component to subtract
     * @return this
     */
    public abstract Vector4ic sub(int x, int y, int z, int w);

    /**
     * Add the supplied vector to this one.
     *
     * @param v the vector to add
     * @return this
     */
    public abstract Vector4ic add(IVector4i v);

    /**
     * Increment the components of this vector by the given values.
     *
     * @param x the x component to add
     * @param y the y component to add
     * @param z the z component to add
     * @param w the w component to add
     * @return this
     */
    public abstract Vector4ic add(int x, int y, int z, int w);

    /**
     * Multiply this Vector4ic component-wise by another Vector4ic.
     *
     * @param v the other vector
     * @return this
     */
    public abstract Vector4ic mul(IVector4i v);

    /**
     * Divide this Vector4ic component-wise by another Vector4ic.
     *
     * @param v the vector to divide by
     * @return this
     */
    public abstract Vector4ic div(IVector4i v);

    /**
     * Multiply all components of this {@link Vector4ic} by the given scalar value.
     *
     * @param scalar the scalar to multiply by
     * @return this
     */
    public abstract Vector4ic mul(float scalar);

    /**
     * Divide all components of this {@link Vector4ic} by the given scalar value.
     *
     * @param scalar the scalar to divide by
     * @return this
     */
    public abstract Vector4ic div(int scalar);

    /**
     * Set all components to zero.
     *
     * @return this
     */
    public abstract Vector4ic zero();

    /**
     * Negate this vector.
     *
     * @return this
     */
    public abstract Vector4ic negate();

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
     * Set the components of this vector to be the component-wise minimum of this and the other vector.
     *
     * @param v the other vector
     * @return this
     */
    public abstract Vector4ic min(IVector4i v);

    /**
     * Set the components of this vector to be the component-wise maximum of this and the other vector.
     *
     * @param v the other vector
     * @return this
     */
    public abstract Vector4ic max(IVector4i v);

    @Override
    public abstract int hashCode();

    @Override
    public abstract boolean equals(Object obj);
}
