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
 * Contains the definition of a Vector comprising 3 ints and associated transformations.
 *
 * @author Richard Greenlees
 * @author Kai Burjack
 * @author Hans Uhlig
 */
public abstract class Vector3ic implements Externalizable, IVector3i {

    /**
     * Set the x, y and z components to match the supplied vector.
     *
     * @param v contains the values of x, y and z to set
     * @return this
     */
    public abstract Vector3ic set(IVector3i v);

    /**
     * Set the x, y and z components to match the supplied vector.
     * <p>
     * Note that due to the given vector <code>v</code> storing the components in double-precision, there is the
     * possibility to lose precision.
     *
     * @param v contains the values of x, y and z to set
     * @return this
     */
    public abstract Vector3ic set(IVector3d v);

    /**
     * Set the first two components from the given <code>v</code> and the z component from the given <code>z</code>
     *
     * @param v the {@link IVector2i} to copy the values from
     * @param z the z component
     * @return this
     */
    public abstract Vector3ic set(IVector2i v, int z);

    /**
     * Set the x, y, and z components to the supplied value.
     *
     * @param d the value of all three components
     * @return this
     */
    public abstract Vector3ic set(int d);

    /**
     * Set the x, y and z components to the supplied values.
     *
     * @param x the x component
     * @param y the y component
     * @param z the z component
     * @return this
     */
    public abstract Vector3ic set(int x, int y, int z);

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
     * @param buffer values will be read in <tt>x, y, z</tt> order
     * @return this
     *
     * @see #set(int, ByteBuffer)
     */
    public abstract Vector3ic set(ByteBuffer buffer);

    /**
     * Read this vector from the supplied {@link ByteBuffer} starting at the specified absolute buffer position/index.
     * <p>
     * This method will not increment the position of the given ByteBuffer.
     *
     * @param index  the absolute position into the ByteBuffer
     * @param buffer values will be read in <tt>x, y, z</tt> order
     * @return this
     */
    public abstract Vector3ic set(int index, ByteBuffer buffer);

    /**
     * Read this vector from the supplied {@link IntBuffer} at the current buffer {@link IntBuffer#position()
     * position}.
     * <p>
     * This method will not increment the position of the given IntBuffer.
     * <p>
     * In order to specify the offset into the IntBuffer at which the vector is read, use {@link #set(int, IntBuffer)},
     * taking the absolute position as parameter.
     *
     * @param buffer values will be read in <tt>x, y, z</tt> order
     * @return this
     *
     * @see #set(int, IntBuffer)
     */
    public abstract Vector3ic set(IntBuffer buffer);

    /**
     * Read this vector from the supplied {@link IntBuffer} starting at the specified absolute buffer position/index.
     * <p>
     * This method will not increment the position of the given IntBuffer.
     *
     * @param index  the absolute position into the IntBuffer
     * @param buffer values will be read in <tt>x, y, z</tt> order
     * @return this
     */
    public abstract Vector3ic set(int index, IntBuffer buffer);
    //#endif

    /**
     * Set the value of the specified component of this vector.
     *
     * @param component the component whose value to set, within <tt>[0..2]</tt>
     * @param value     the value to set
     * @return this
     */
    public abstract Vector3ic setComponent(int component, int value);

    /**
     * Subtract the supplied vector from this one and store the result in <code>this</code>.
     *
     * @param v the vector to subtract
     * @return this
     */
    public abstract Vector3ic sub(IVector3i v);

    /**
     * Decrement the components of this vector by the given values.
     *
     * @param x the x component to subtract
     * @param y the y component to subtract
     * @param z the z component to subtract
     * @return this
     */
    public abstract Vector3ic sub(int x, int y, int z);

    /**
     * Add the supplied vector to this one.
     *
     * @param v the vector to add
     * @return this
     */
    public abstract Vector3ic add(IVector3i v);

    /**
     * Increment the components of this vector by the given values.
     *
     * @param x the x component to add
     * @param y the y component to add
     * @param z the z component to add
     * @return this
     */
    public abstract Vector3ic add(int x, int y, int z);

    /**
     * Multiply all components of this {@link Vector3ic} by the given scalar value.
     *
     * @param scalar the scalar to multiply this vector by
     * @return this
     */
    public abstract Vector3ic mul(int scalar);

    /**
     * Multiply all components of this {@link Vector3ic} by the given vector.
     *
     * @param v the vector to multiply
     * @return this
     */
    public abstract Vector3ic mul(IVector3i v);

    /**
     * Multiply the components of this vector by the given values.
     *
     * @param x the x component to multiply
     * @param y the y component to multiply
     * @param z the z component to multiply
     * @return this
     */
    public abstract Vector3ic mul(int x, int y, int z);

    /**
     * Set all components to zero.
     *
     * @return this
     */
    public abstract Vector3ic zero();

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
     * Negate this vector.
     *
     * @return this
     */
    public abstract Vector3ic negate();

    @Override
    public abstract int hashCode();

    @Override
    public abstract boolean equals(Object obj);
}
