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

import org.joml.api.matrix.IMatrix4f;
import org.joml.api.matrix.IMatrix4x3f;
import org.joml.api.quaternion.IQuaternionf;

import java.io.Externalizable;
import java.text.DecimalFormat;
import java.text.NumberFormat;

//#ifdef __HAS_NIO__
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
//#endif

/**
 * Contains the definition of a Vector comprising 4 floats and associated transformations.
 *
 * @author Richard Greenlees
 * @author Kai Burjack
 */
public abstract class Vector4fc implements Externalizable, IVector4f {

    /**
     * Set this {@link Vector4fc} to the values of the given <code>v</code>.
     *
     * @param v the vector whose values will be copied into this
     * @return this
     */
    public abstract Vector4fc set(IVector4f v);

    /**
     * Set this {@link Vector4fc} to the values of the given <code>v</code>.
     *
     * @param v the vector whose values will be copied into this
     * @return this
     */
    public abstract Vector4fc set(IVector4i v);

    /**
     * Set this {@link Vector4fc} to the values of the given <code>v</code>.
     * <p>
     * Note that due to the given vector <code>v</code> storing the components in double-precision, there is the
     * possibility to lose precision.
     *
     * @param v the vector whose values will be copied into this
     * @return this
     */
    public abstract Vector4fc set(IVector4d v);

    /**
     * Set the first three components of this to the components of <code>v</code> and the last component to
     * <code>w</code>.
     *
     * @param v the {@link IVector3f} to copy
     * @param w the w component
     * @return this
     */
    public abstract Vector4fc set(IVector3f v, float w);

    /**
     * Set the first three components of this to the components of <code>v</code> and the last component to
     * <code>w</code>.
     *
     * @param v the {@link IVector3i} to copy
     * @param w the w component
     * @return this
     */
    public abstract Vector4fc set(IVector3i v, float w);

    /**
     * Sets the first two components of this to the components of given <code>v</code> and last two components to the
     * given <code>z</code>, and <code>w</code>.
     *
     * @param v the {@link IVector2f}
     * @param z the z component
     * @param w the w component
     * @return this
     */
    public abstract Vector4fc set(IVector2f v, float z, float w);

    /**
     * Sets the first two components of this to the components of given <code>v</code> and last two components to the
     * given <code>z</code>, and <code>w</code>.
     *
     * @param v the {@link IVector2i}
     * @param z the z component
     * @param w the w component
     * @return this
     */
    public abstract Vector4fc set(IVector2i v, float z, float w);

    /**
     * Set the x, y, z, and w components to the supplied value.
     *
     * @param d the value of all four components
     * @return this
     */
    public abstract Vector4fc set(float d);

    /**
     * Set the x, y, z, and w components to the supplied values.
     *
     * @param x the x component
     * @param y the y component
     * @param z the z component
     * @param w the w component
     * @return this
     */
    public abstract Vector4fc set(float x, float y, float z, float w);

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
    public abstract Vector4fc set(ByteBuffer buffer);

    /**
     * Read this vector from the supplied {@link ByteBuffer} starting at the specified absolute buffer position/index.
     * <p>
     * This method will not increment the position of the given ByteBuffer.
     *
     * @param index  the absolute position into the ByteBuffer
     * @param buffer values will be read in <tt>x, y, z, w</tt> order
     * @return this
     */
    public abstract Vector4fc set(int index, ByteBuffer buffer);

    /**
     * Read this vector from the supplied {@link FloatBuffer} at the current buffer {@link FloatBuffer#position()
     * position}.
     * <p>
     * This method will not increment the position of the given FloatBuffer.
     * <p>
     * In order to specify the offset into the FloatBuffer at which the vector is read, use {@link #set(int,
     * FloatBuffer)}, taking the absolute position as parameter.
     *
     * @param buffer values will be read in <tt>x, y, z, w</tt> order
     * @return this
     *
     * @see #set(int, FloatBuffer)
     */
    public abstract Vector4fc set(FloatBuffer buffer);

    /**
     * Read this vector from the supplied {@link FloatBuffer} starting at the specified absolute buffer position/index.
     * <p>
     * This method will not increment the position of the given FloatBuffer.
     *
     * @param index  the absolute position into the FloatBuffer
     * @param buffer values will be read in <tt>x, y, z, w</tt> order
     * @return this
     */
    public abstract Vector4fc set(int index, FloatBuffer buffer);
    //#endif

    /**
     * Set the value of the specified component of this vector.
     *
     * @param component the component whose value to set, within <tt>[0..3]</tt>
     * @param value     the value to set
     * @return this
     */
    public abstract Vector4fc setComponent(int component, float value);

    /**
     * Subtract the supplied vector from this one.
     *
     * @param v the vector to subtract
     * @return this
     */
    public abstract Vector4fc sub(IVector4f v);

    /**
     * Subtract <tt>(x, y, z, w)</tt> from this.
     *
     * @param x the x component to subtract
     * @param y the y component to subtract
     * @param z the z component to subtract
     * @param w the w component to subtract
     * @return this
     */
    public abstract Vector4fc sub(float x, float y, float z, float w);

    /**
     * Add the supplied vector to this one.
     *
     * @param v the vector to add
     * @return this
     */
    public abstract Vector4fc add(IVector4f v);

    /**
     * Increment the components of this vector by the given values.
     *
     * @param x the x component to add
     * @param y the y component to add
     * @param z the z component to add
     * @param w the w component to add
     * @return this
     */
    public abstract Vector4fc add(float x, float y, float z, float w);

    /**
     * Add the component-wise multiplication of <code>a * b</code> to this vector.
     *
     * @param a the first multiplicand
     * @param b the second multiplicand
     * @return this
     */
    public abstract Vector4fc fma(IVector4f a, IVector4f b);

    /**
     * Add the component-wise multiplication of <code>a * b</code> to this vector.
     *
     * @param a the first multiplicand
     * @param b the second multiplicand
     * @return this
     */
    public abstract Vector4fc fma(float a, IVector4f b);

    /**
     * Multiply this Vector4fc component-wise by another Vector4fc.
     *
     * @param v the other vector
     * @return this
     */
    public abstract Vector4fc mul(IVector4f v);

    /**
     * Divide this Vector4fc component-wise by another Vector4fc.
     *
     * @param v the vector to divide by
     * @return this
     */
    public abstract Vector4fc div(IVector4f v);

    /**
     * Multiply the given matrix mat with this Vector4fc and store the result in <code>this</code>.
     *
     * @param mat the matrix to multiply the vector with
     * @return this
     */
    public abstract Vector4fc mul(IMatrix4f mat);

    /**
     * Multiply the given matrix mat with this Vector4fc and store the result in <code>this</code>.
     *
     * @param mat the matrix to multiply the vector with
     * @return this
     */
    public abstract Vector4fc mul(IMatrix4x3f mat);

    /**
     * Multiply the given matrix <code>mat</code> with this Vector4fc, perform perspective division.
     *
     * @param mat the matrix to multiply this vector by
     * @return this
     */
    public abstract Vector4fc mulProject(IMatrix4f mat);

    /**
     * Multiply all components of this {@link Vector4fc} by the given scalar value.
     *
     * @param scalar the scalar to multiply by
     * @return this
     */
    public abstract Vector4fc mul(float scalar);

    /**
     * Multiply the components of this Vector4fc by the given scalar values and store the result in <code>this</code>.
     *
     * @param x the x component to multiply by
     * @param y the y component to multiply by
     * @param z the z component to multiply by
     * @param w the w component to multiply by
     * @return this
     */
    public abstract Vector4fc mul(float x, float y, float z, float w);

    /**
     * Divide all components of this {@link Vector4fc} by the given scalar value.
     *
     * @param scalar the scalar to divide by
     * @return this
     */
    public abstract Vector4fc div(float scalar);

    /**
     * Divide the components of this Vector4fc by the given scalar values and store the result in <code>this</code>.
     *
     * @param x the x component to divide by
     * @param y the y component to divide by
     * @param z the z component to divide by
     * @param w the w component to divide by
     * @return this
     */
    public abstract Vector4fc div(float x, float y, float z, float w);

    /**
     * Rotate this vector by the given quaternion <code>quat</code> and store the result in <code>this</code>.
     *
     * @param quat the quaternion to rotate this vector
     * @return this
     *
     * @see IQuaternionf#transform(Vector4fc)
     */
    public abstract Vector4fc rotate(IQuaternionf quat);

    /**
     * Rotate this vector the specified radians around the given rotation axis.
     *
     * @param angle the angle in radians
     * @param x     the x component of the rotation axis
     * @param y     the y component of the rotation axis
     * @param z     the z component of the rotation axis
     * @return this
     */
    public abstract Vector4fc rotateAbout(float angle, float x, float y, float z);

    /**
     * Rotate this vector the specified radians around the X axis.
     *
     * @param angle the angle in radians
     * @return this
     */
    public abstract Vector4fc rotateX(float angle);

    /**
     * Rotate this vector the specified radians around the Y axis.
     *
     * @param angle the angle in radians
     * @return this
     */
    public abstract Vector4fc rotateY(float angle);

    /**
     * Rotate this vector the specified radians around the Z axis.
     *
     * @param angle the angle in radians
     * @return this
     */
    public abstract Vector4fc rotateZ(float angle);

    /**
     * Normalizes this vector.
     *
     * @return this
     */
    public abstract Vector4fc normalize();

    /**
     * Scale this vector to have the given length.
     *
     * @param length the desired length
     * @return this
     */
    public abstract Vector4fc normalize(float length);

    /**
     * Normalize this vector by computing only the norm of <tt>(x, y, z)</tt>.
     *
     * @return this
     */
    public abstract Vector4fc normalize3();

    /**
     * Set all components to zero.
     *
     * @return this
     */
    public abstract Vector4fc zero();

    /**
     * Negate this vector.
     *
     * @return this
     */
    public abstract Vector4fc negate();

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
    public abstract Vector4fc min(IVector4f v);

    /**
     * Set the components of this vector to be the component-wise maximum of this and the other vector.
     *
     * @param v the other vector
     * @return this
     */
    public abstract Vector4fc max(IVector4f v);

    @Override
    public abstract int hashCode();

    @Override
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
    public abstract Vector4fc lerp(IVector4f other, float t);

    /**
     * Set the specified component of this vector to the given value.
     *
     * @param component the component, within <tt>[0..3]</tt>
     * @param value     the value
     * @return this
     */
    public abstract Vector4fc set(int component, float value);
}
