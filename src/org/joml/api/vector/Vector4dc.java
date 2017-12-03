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

import org.joml.api.matrix.IMatrix4d;
import org.joml.api.matrix.IMatrix4f;
import org.joml.api.matrix.IMatrix4x3d;
import org.joml.api.matrix.IMatrix4x3f;
import org.joml.api.quaternion.IQuaterniond;

import java.io.Externalizable;
//#ifdef __HAS_NIO__
import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
//#endif
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Contains the definition of a Vector comprising 4 doubles and associated transformations.
 *
 * @author Richard Greenlees
 * @author Kai Burjack
 */
public abstract class Vector4dc implements Externalizable, IVector4d {

    /**
     * Set this {@link Vector4dc} to the values of the given <code>v</code>.
     *
     * @param v the vector whose values will be copied into this
     * @return this
     */
    public abstract Vector4dc set(IVector4d v);

    /**
     * Set this {@link Vector4dc} to the values of the given <code>v</code>.
     *
     * @param v the vector whose values will be copied into this
     * @return this
     */
    public abstract Vector4dc set(IVector4f v);

    /**
     * Set this {@link Vector4dc} to the values of the given <code>v</code>.
     *
     * @param v the vector whose values will be copied into this
     * @return this
     */
    public abstract Vector4dc set(IVector4i v);

    /**
     * Set the x, y, and z components of this to the components of <code>v</code> and the w component to
     * <code>w</code>.
     *
     * @param v the {@link IVector3d} to copy
     * @param w the w component
     * @return this
     */
    public abstract Vector4dc set(IVector3d v, double w);

    /**
     * Set the x, y, and z components of this to the components of <code>v</code> and the w component to
     * <code>w</code>.
     *
     * @param v the {@link IVector3i} to copy
     * @param w the w component
     * @return this
     */
    public abstract Vector4dc set(IVector3i v, double w);

    /**
     * Set the x, y, and z components of this to the components of <code>v</code> and the w component to
     * <code>w</code>.
     *
     * @param v the {@link IVector3f} to copy
     * @param w the w component
     * @return this
     */
    public abstract Vector4dc set(IVector3f v, double w);

    /**
     * Set the x and y components from the given <code>v</code> and the z and w components to the given <code>z</code>
     * and <code>w</code>.
     *
     * @param v the {@link IVector2d}
     * @param z the z component
     * @param w the w component
     * @return this
     */
    public abstract Vector4dc set(IVector2d v, double z, double w);

    /**
     * Set the x and y components from the given <code>v</code> and the z and w components to the given <code>z</code>
     * and <code>w</code>.
     *
     * @param v the {@link IVector2i}
     * @param z the z component
     * @param w the w component
     * @return this
     */
    public abstract Vector4dc set(IVector2i v, double z, double w);

    /**
     * Set the x, y, z, and w components to the supplied value.
     *
     * @param d the value of all four components
     * @return this
     */
    public abstract Vector4dc set(double d);

    /**
     * Set the x and y components from the given <code>v</code> and the z and w components to the given <code>z</code>
     * and <code>w</code>.
     *
     * @param v the {@link IVector2f}
     * @param z the z components
     * @param w the w components
     * @return this
     */
    public abstract Vector4dc set(IVector2f v, double z, double w);

    /**
     * Set the x, y, z, and w components to the supplied values.
     *
     * @param x the x component
     * @param y the y component
     * @param z the z component
     * @param w the w component
     * @return this
     */
    public abstract Vector4dc set(double x, double y, double z, double w);

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
    public abstract Vector4dc set(ByteBuffer buffer);

    /**
     * Read this vector from the supplied {@link ByteBuffer} starting at the specified absolute buffer position/index.
     * <p>
     * This method will not increment the position of the given ByteBuffer.
     *
     * @param index  the absolute position into the ByteBuffer
     * @param buffer values will be read in <tt>x, y, z, w</tt> order
     * @return this
     */
    public abstract Vector4dc set(int index, ByteBuffer buffer);

    /**
     * Read this vector from the supplied {@link DoubleBuffer} at the current buffer {@link DoubleBuffer#position()
     * position}.
     * <p>
     * This method will not increment the position of the given DoubleBuffer.
     * <p>
     * In order to specify the offset into the DoubleBuffer at which the vector is read, use {@link #set(int,
     * DoubleBuffer)}, taking the absolute position as parameter.
     *
     * @param buffer values will be read in <tt>x, y, z, w</tt> order
     * @return this
     *
     * @see #set(int, DoubleBuffer)
     */
    public abstract Vector4dc set(DoubleBuffer buffer);

    /**
     * Read this vector from the supplied {@link DoubleBuffer} starting at the specified absolute buffer
     * position/index.
     * <p>
     * This method will not increment the position of the given DoubleBuffer.
     *
     * @param index  the absolute position into the DoubleBuffer
     * @param buffer values will be read in <tt>x, y, z, w</tt> order
     * @return this
     */
    public abstract Vector4dc set(int index, DoubleBuffer buffer);
    //#endif

    /**
     * Set the value of the specified component of this vector.
     *
     * @param component the component whose value to set, within <tt>[0..3]</tt>
     * @param value     the value to set
     * @return this
     *
     * @throws IllegalArgumentException if <code>component</code> is not within <tt>[0..3]</tt>
     */
    public abstract Vector4dc setComponent(int component, double value);

    /**
     * Subtract the supplied vector from this one.
     *
     * @param v the vector to subtract
     * @return this
     */
    public abstract Vector4dc sub(IVector4d v);

    /**
     * Subtract the supplied vector from this one.
     *
     * @param v the vector to subtract
     * @return this
     */
    public abstract Vector4dc sub(IVector4f v);

    /**
     * Subtract <tt>(x, y, z, w)</tt> from this.
     *
     * @param x the x component to subtract
     * @param y the y component to subtract
     * @param z the z component to subtract
     * @param w the w component to subtract
     * @return this
     */
    public abstract Vector4dc sub(double x, double y, double z, double w);

    /**
     * Add the supplied vector to this one.
     *
     * @param v the vector to add
     * @return this
     */
    public abstract Vector4dc add(IVector4d v);

    /**
     * Add <tt>(x, y, z, w)</tt> to this.
     *
     * @param x the x component to subtract
     * @param y the y component to subtract
     * @param z the z component to subtract
     * @param w the w component to subtract
     * @return this
     */
    public abstract Vector4dc add(double x, double y, double z, double w);

    /**
     * Add the supplied vector to this one.
     *
     * @param v the vector to add
     * @return this
     */
    public abstract Vector4dc add(IVector4f v);

    /**
     * Add the component-wise multiplication of <code>a * b</code> to this vector.
     *
     * @param a the first multiplicand
     * @param b the second multiplicand
     * @return this
     */
    public abstract Vector4dc fma(IVector4d a, IVector4d b);

    /**
     * Add the component-wise multiplication of <code>a * b</code> to this vector.
     *
     * @param a the first multiplicand
     * @param b the second multiplicand
     * @return this
     */
    public abstract Vector4dc fma(double a, IVector4d b);

    /**
     * Multiply this {@link Vector4dc} component-wise by the given {@link Vector4dc}.
     *
     * @param v the vector to multiply by
     * @return this
     */
    public abstract Vector4dc mul(IVector4d v);

    /**
     * Divide this {@link Vector4dc} component-wise by the given {@link IVector4d}.
     *
     * @param v the vector to divide by
     * @return this
     */
    public abstract Vector4dc div(IVector4d v);

    /**
     * Multiply this {@link Vector4dc} component-wise by the given {@link IVector4f}.
     *
     * @param v the vector to multiply by
     * @return this
     */
    public abstract Vector4dc mul(IVector4f v);

    /**
     * Multiply the given matrix <code>mat</code> with this {@link Vector4dc}.
     *
     * @param mat the matrix to multiply by
     * @return this
     */
    public abstract Vector4dc mul(IMatrix4d mat);

    /**
     * Multiply the given matrix mat with this Vector4dc and store the result in <code>this</code>.
     *
     * @param mat the matrix to multiply the vector with
     * @return this
     */
    public abstract Vector4dc mul(IMatrix4x3d mat);

    /**
     * Multiply the given matrix mat with this Vector4dc and store the result in <code>this</code>.
     *
     * @param mat the matrix to multiply the vector with
     * @return this
     */
    public abstract Vector4dc mul(IMatrix4x3f mat);

    /**
     * Multiply the given matrix <code>mat</code> with this {@link Vector4dc}.
     *
     * @param mat the matrix to multiply by
     * @return this
     */
    public abstract Vector4dc mul(IMatrix4f mat);

    /**
     * Multiply the given matrix <code>mat</code> with this Vector4dc, perform perspective division.
     *
     * @param mat the matrix to multiply this vector by
     * @return this
     */
    public abstract Vector4dc mulProject(IMatrix4d mat);

    /**
     * Multiply this Vector4dc by the given scalar value.
     *
     * @param scalar the scalar to multiply by
     * @return this
     */
    public abstract Vector4dc mul(double scalar);

    /**
     * Divide this Vector4dc by the given scalar value.
     *
     * @param scalar the scalar to divide by
     * @return this
     */
    public abstract Vector4dc div(double scalar);

    /**
     * Transform this vector by the given quaternion <code>quat</code> and store the result in <code>this</code>.
     *
     * @param quat the quaternion to transform this vector
     * @return this
     *
     * @see IQuaterniond#transform(Vector4dc)
     */
    public abstract Vector4dc rotate(IQuaterniond quat);

    /**
     * Rotate this vector the specified radians around the given rotation axis.
     *
     * @param angle the angle in radians
     * @param x     the x component of the rotation axis
     * @param y     the y component of the rotation axis
     * @param z     the z component of the rotation axis
     * @return this
     */
    public abstract Vector4dc rotateAxis(double angle, double x, double y, double z);

    /**
     * Rotate this vector the specified radians around the X axis.
     *
     * @param angle the angle in radians
     * @return this
     */
    public abstract Vector4dc rotateX(double angle);

    /**
     * Rotate this vector the specified radians around the Y axis.
     *
     * @param angle the angle in radians
     * @return this
     */
    public abstract Vector4dc rotateY(double angle);

    /**
     * Rotate this vector the specified radians around the Z axis.
     *
     * @param angle the angle in radians
     * @return this
     */
    public abstract Vector4dc rotateZ(double angle);

    /**
     * Normalizes this vector.
     *
     * @return this
     */
    public abstract Vector4dc normalize();

    /**
     * Scale this vector to have the given length.
     *
     * @param length the desired length
     * @return this
     */
    public abstract Vector4dc normalize(double length);

    /**
     * Normalize this vector by computing only the norm of <tt>(x, y, z)</tt>.
     *
     * @return this
     */
    public abstract Vector4dc normalize3();

    /**
     * Set all components to zero.
     *
     * @return this
     */
    public abstract Vector4dc zero();

    /**
     * Negate this vector.
     *
     * @return this
     */
    public abstract Vector4dc negate();

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

    public abstract int hashCode();

    public abstract boolean equals(Object obj);

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
    public abstract Vector4dc lerp(IVector4d other, double t);

    /**
     * Set the specified component of this vector to the given value.
     *
     * @param component the component, within <tt>[0..3]</tt>
     * @param value     the value
     * @return this
     *
     * @throws IllegalArgumentException if <code>component</code> is not within <tt>[0..3]</tt>
     */
    public abstract Vector4dc set(int component, double value);
}
