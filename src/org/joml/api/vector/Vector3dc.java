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

import org.joml.api.matrix.*;
import org.joml.api.quaternion.IQuaterniond;

import java.io.Externalizable;
import java.text.DecimalFormat;
import java.text.NumberFormat;

//#ifdef __HAS_NIO__
import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
//#endif

/**
 * Contains the definition of a Vector comprising 3 doubles and associated transformations.
 *
 * @author Richard Greenlees
 * @author Kai Burjack
 */
public abstract class Vector3dc implements Externalizable, IVector3d {

    /**
     * Set the x, y and z components to match the supplied vector.
     *
     * @param v the vector to set this vector's components from
     * @return this
     */
    public abstract Vector3dc set(IVector3d v);

    /**
     * Set the x, y and z components to match the supplied vector.
     *
     * @param v the vector to set this vector's components from
     * @return this
     */
    public abstract Vector3dc set(IVector3i v);

    /**
     * Set the first two components from the given <code>v</code> and the z component from the given <code>z</code>
     *
     * @param v the {@link IVector2d} to copy the values from
     * @param z the z component
     * @return this
     */
    public abstract Vector3dc set(IVector2d v, double z);

    /**
     * Set the first two components from the given <code>v</code> and the z component from the given <code>z</code>
     *
     * @param v the {@link IVector2i} to copy the values from
     * @param z the z component
     * @return this
     */
    public abstract Vector3dc set(IVector2i v, double z);

    /**
     * Set the x, y and z components to match the supplied vector.
     *
     * @param v the vector to set this vector's components from
     * @return this
     */
    public abstract Vector3dc set(IVector3f v);

    /**
     * Set the first two components from the given <code>v</code> and the z component from the given <code>z</code>
     *
     * @param v the {@link IVector2f} to copy the values from
     * @param z the z component
     * @return this
     */
    public abstract Vector3dc set(IVector2f v, double z);

    /**
     * Set the x, y, and z components to the supplied value.
     *
     * @param d the value of all three components
     * @return this
     */
    public abstract Vector3dc set(double d);

    /**
     * Set the x, y and z components to the supplied values.
     *
     * @param x the x component
     * @param y the y component
     * @param z the z component
     * @return this
     */
    public abstract Vector3dc set(double x, double y, double z);

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
    public abstract Vector3dc set(ByteBuffer buffer);

    /**
     * Read this vector from the supplied {@link ByteBuffer} starting at the specified absolute buffer position/index.
     * <p>
     * This method will not increment the position of the given ByteBuffer.
     *
     * @param index  the absolute position into the ByteBuffer
     * @param buffer values will be read in <tt>x, y, z</tt> order
     * @return this
     */
    public abstract Vector3dc set(int index, ByteBuffer buffer);

    /**
     * Read this vector from the supplied {@link DoubleBuffer} at the current buffer {@link DoubleBuffer#position()
     * position}.
     * <p>
     * This method will not increment the position of the given DoubleBuffer.
     * <p>
     * In order to specify the offset into the DoubleBuffer at which the vector is read, use {@link #set(int,
     * DoubleBuffer)}, taking the absolute position as parameter.
     *
     * @param buffer values will be read in <tt>x, y, z</tt> order
     * @return this
     *
     * @see #set(int, DoubleBuffer)
     */
    public abstract Vector3dc set(DoubleBuffer buffer);

    /**
     * Read this vector from the supplied {@link DoubleBuffer} starting at the specified absolute buffer
     * position/index.
     * <p>
     * This method will not increment the position of the given DoubleBuffer.
     *
     * @param index  the absolute position into the DoubleBuffer
     * @param buffer values will be read in <tt>x, y, z</tt> order
     * @return this
     */
    public abstract Vector3dc set(int index, DoubleBuffer buffer);
    //#endif

    /**
     * Set the value of the specified component of this vector.
     *
     * @param component the component whose value to set, within <tt>[0..2]</tt>
     * @param value     the value to set
     * @return this
     */
    public abstract Vector3dc setComponent(int component, double value);

    /**
     * Subtract the supplied vector from this one.
     *
     * @param v the vector to subtract from this
     * @return this
     */
    public abstract Vector3dc sub(IVector3d v);

    /**
     * Subtract the supplied vector from this one.
     *
     * @param v the vector to subtract from this
     * @return this
     */
    public abstract Vector3dc sub(IVector3f v);

    /**
     * Subtract <tt>(x, y, z)</tt> from this vector.
     *
     * @param x the x component to subtract
     * @param y the y component to subtract
     * @param z the z component to subtract
     * @return this
     */
    public abstract Vector3dc sub(double x, double y, double z);

    /**
     * Add the supplied vector to this one.
     *
     * @param v the vector to add
     * @return this
     */
    public abstract Vector3dc add(IVector3d v);

    /**
     * Add the supplied vector to this one.
     *
     * @param v the vector to add
     * @return this
     */
    public abstract Vector3dc add(IVector3f v);

    /**
     * Increment the components of this vector by the given values.
     *
     * @param x the x component to add
     * @param y the y component to add
     * @param z the z component to add
     * @return this
     */
    public abstract Vector3dc add(double x, double y, double z);

    /**
     * Add the component-wise multiplication of <code>a * b</code> to this vector.
     *
     * @param a the first multiplicand
     * @param b the second multiplicand
     * @return this
     */
    public abstract Vector3dc fma(IVector3d a, IVector3d b);

    /**
     * Add the component-wise multiplication of <code>a * b</code> to this vector.
     *
     * @param a the first multiplicand
     * @param b the second multiplicand
     * @return this
     */
    public abstract Vector3dc fma(double a, IVector3d b);

    /**
     * Add the component-wise multiplication of <code>a * b</code> to this vector.
     *
     * @param a the first multiplicand
     * @param b the second multiplicand
     * @return this
     */
    public abstract Vector3dc fma(IVector3f a, IVector3f b);

    /**
     * Add the component-wise multiplication of <code>a * b</code> to this vector.
     *
     * @param a the first multiplicand
     * @param b the second multiplicand
     * @return this
     */
    public abstract Vector3dc fma(double a, IVector3f b);

    /**
     * Multiply this Vector3dc component-wise by another IVector3d.
     *
     * @param v the vector to multiply by
     * @return this
     */
    public abstract Vector3dc mul(IVector3d v);

    /**
     * Multiply this Vector3dc component-wise by another IVector3f.
     *
     * @param v the vector to multiply by
     * @return this
     */
    public abstract Vector3dc mul(IVector3f v);

    /**
     * Divide this Vector3dc component-wise by another IVector3d.
     *
     * @param v the vector to divide by
     * @return this
     */
    public abstract Vector3dc div(Vector3dc v);

    /**
     * Divide this Vector3dc component-wise by another IVector3f.
     *
     * @param v the vector to divide by
     * @return this
     */
    public abstract Vector3dc div(IVector3f v);

    /**
     * Multiply the given matrix <code>mat</code> this Vector3dc, perform perspective division.
     * <p>
     * This method uses <tt>w=1.0</tt> as the fourth vector component.
     *
     * @param mat the matrix to multiply this vector by
     * @return this
     */
    public abstract Vector3dc mulProject(IMatrix4d mat);

    /**
     * Multiply the given matrix <code>mat</code> with this Vector3dc, perform perspective division.
     * <p>
     * This method uses <tt>w=1.0</tt> as the fourth vector component.
     *
     * @param mat the matrix to multiply this vector by
     * @return this
     */
    public abstract Vector3dc mulProject(IMatrix4f mat);

    /**
     * Multiply the given matrix <code>mat</code> with this Vector3dc.
     *
     * @param mat the matrix to multiply this vector by
     * @return this
     */
    public abstract Vector3dc mul(IMatrix3f mat);

    /**
     * Multiply the given matrix <code>mat</code> with this Vector3dc.
     *
     * @param mat the matrix to multiply this vector by
     * @return this
     */
    public abstract Vector3dc mul(IMatrix3d mat);

    /**
     * Multiply the given matrix <code>mat</code> with <code>this</code> and store the result in <code>dest</code>.
     *
     * @param mat  the matrix to multiply this vector by
     * @param dest will hold the result
     * @return dest
     */
    public abstract Vector3fc mul(IMatrix3d mat, Vector3fc dest);

    /**
     * Multiply the given matrix with this Vector3dc by assuming a third row in the matrix of <tt>(0, 0, 1)</tt> and
     * store the result in <code>this</code>.
     *
     * @param mat the matrix
     * @return this
     */
    public abstract Vector3dc mul(IMatrix3x2d mat);

    /**
     * Multiply the given matrix with this Vector3dc by assuming a third row in the matrix of <tt>(0, 0, 1)</tt> and
     * store the result in <code>this</code>.
     *
     * @param mat the matrix
     * @return this
     */
    public abstract Vector3dc mul(IMatrix3x2f mat);

    /**
     * Multiply the transpose of the given matrix with this Vector3dc and store the result in <code>this</code>.
     *
     * @param mat the matrix
     * @return this
     */
    public abstract Vector3dc mulTranspose(IMatrix3d mat);

    /**
     * Multiply the transpose of the given matrix with  this Vector3dc and store the result in <code>this</code>.
     *
     * @param mat the matrix
     * @return this
     */
    public abstract Vector3dc mulTranspose(IMatrix3f mat);

    /**
     * Multiply the given 4x4 matrix <code>mat</code> with <code>this</code>.
     * <p>
     * This method assumes the <tt>w</tt> component of <code>this</code> to be <tt>1.0</tt>.
     *
     * @param mat the matrix to multiply this vector by
     * @return this
     */
    public abstract Vector3dc mulPosition(IMatrix4f mat);

    /**
     * Multiply the given 4x4 matrix <code>mat</code> with <code>this</code>.
     * <p>
     * This method assumes the <tt>w</tt> component of <code>this</code> to be <tt>1.0</tt>.
     *
     * @param mat the matrix to multiply this vector by
     * @return this
     */
    public abstract Vector3dc mulPosition(IMatrix4d mat);

    /**
     * Multiply the given 4x3 matrix <code>mat</code> with <code>this</code>.
     * <p>
     * This method assumes the <tt>w</tt> component of <code>this</code> to be <tt>1.0</tt>.
     *
     * @param mat the matrix to multiply this vector by
     * @return this
     */
    public abstract Vector3dc mulPosition(IMatrix4x3d mat);

    /**
     * Multiply the given 4x3 matrix <code>mat</code> with <code>this</code>.
     * <p>
     * This method assumes the <tt>w</tt> component of <code>this</code> to be <tt>1.0</tt>.
     *
     * @param mat the matrix to multiply this vector by
     * @return this
     */
    public abstract Vector3dc mulPosition(IMatrix4x3f mat);

    /**
     * Multiply the transpose of the given 4x4 matrix <code>mat</code> with <code>this</code>.
     * <p>
     * This method assumes the <tt>w</tt> component of <code>this</code> to be <tt>1.0</tt>.
     *
     * @param mat the matrix whose transpose to multiply this vector by
     * @return this
     */
    public abstract Vector3dc mulTransposePosition(IMatrix4d mat);

    /**
     * Multiply the transpose of the given 4x4 matrix <code>mat</code> with <code>this</code>.
     * <p>
     * This method assumes the <tt>w</tt> component of <code>this</code> to be <tt>1.0</tt>.
     *
     * @param mat the matrix whose transpose to multiply this vector by
     * @return this
     */
    public abstract Vector3dc mulTransposePosition(IMatrix4f mat);

    /**
     * Multiply the given 4x4 matrix <code>mat</code> with <code>this</code> and return the <i>w</i> component of the
     * resulting 4D vector.
     * <p>
     * This method assumes the <tt>w</tt> component of <code>this</code> to be <tt>1.0</tt>.
     *
     * @param mat the matrix to multiply this vector by
     * @return the <i>w</i> component of the resulting 4D vector after multiplication
     */
    public abstract double mulPositionW(IMatrix4f mat);

    /**
     * Multiply the given 4x4 matrix <code>mat</code> with <code>this</code> and return the <i>w</i> component of the
     * resulting 4D vector.
     * <p>
     * This method assumes the <tt>w</tt> component of <code>this</code> to be <tt>1.0</tt>.
     *
     * @param mat the matrix to multiply this vector by
     * @return the <i>w</i> component of the resulting 4D vector after multiplication
     */
    public abstract double mulPositionW(IMatrix4d mat);

    /**
     * Multiply the given 4x4 matrix <code>mat</code> with <code>this</code>.
     * <p>
     * This method assumes the <tt>w</tt> component of <code>this</code> to be <tt>0.0</tt>.
     *
     * @param mat the matrix to multiply this vector by
     * @return this
     */
    public abstract Vector3dc mulDirection(IMatrix4f mat);

    /**
     * Multiply the given 4x4 matrix <code>mat</code> with <code>this</code>.
     * <p>
     * This method assumes the <tt>w</tt> component of <code>this</code> to be <tt>0.0</tt>.
     *
     * @param mat the matrix to multiply this vector by
     * @return this
     */
    public abstract Vector3dc mulDirection(IMatrix4d mat);

    /**
     * Multiply the given 4x3 matrix <code>mat</code> with <code>this</code>.
     * <p>
     * This method assumes the <tt>w</tt> component of <code>this</code> to be <tt>0.0</tt>.
     *
     * @param mat the matrix to multiply this vector by
     * @return this
     */
    public abstract Vector3dc mulDirection(IMatrix4x3d mat);

    /**
     * Multiply the given 4x3 matrix <code>mat</code> with <code>this</code>.
     * <p>
     * This method assumes the <tt>w</tt> component of <code>this</code> to be <tt>0.0</tt>.
     *
     * @param mat the matrix to multiply this vector by
     * @return this
     */
    public abstract Vector3dc mulDirection(IMatrix4x3f mat);

    /**
     * Multiply the transpose of the given 4x4 matrix <code>mat</code> with <code>this</code>.
     * <p>
     * This method assumes the <tt>w</tt> component of <code>this</code> to be <tt>0.0</tt>.
     *
     * @param mat the matrix whose transpose to multiply this vector by
     * @return this
     */
    public abstract Vector3dc mulTransposeDirection(IMatrix4d mat);

    /**
     * Multiply the transpose of the given 4x4 matrix <code>mat</code> with <code>this</code>.
     * <p>
     * This method assumes the <tt>w</tt> component of <code>this</code> to be <tt>0.0</tt>.
     *
     * @param mat the matrix whose transpose to multiply this vector by
     * @return this
     */
    public abstract Vector3dc mulTransposeDirection(IMatrix4f mat);

    /**
     * Multiply this Vector3dc by the given scalar value.
     *
     * @param scalar the scalar to multiply this vector by
     * @return this
     */
    public abstract Vector3dc mul(double scalar);

    /**
     * Multiply the components of this Vector3dc by the given scalar values and store the result in <code>this</code>.
     *
     * @param x the x component to multiply this vector by
     * @param y the y component to multiply this vector by
     * @param z the z component to multiply this vector by
     * @return this
     */
    public abstract Vector3dc mul(double x, double y, double z);

    /**
     * Rotate this vector by the given quaternion <code>quat</code> and store the result in <code>this</code>.
     *
     * @param quat the quaternion to rotate this vector
     * @return this
     *
     * @see IQuaterniond#transform(Vector3dc)
     */
    public abstract Vector3dc rotate(IQuaterniond quat);

    /**
     * Rotate this vector the specified radians around the given rotation axis.
     *
     * @param angle the angle in radians
     * @param x     the x component of the rotation axis
     * @param y     the y component of the rotation axis
     * @param z     the z component of the rotation axis
     * @return this
     */
    public abstract Vector3dc rotateAxis(double angle, double x, double y, double z);

    /**
     * Rotate this vector the specified radians around the X axis.
     *
     * @param angle the angle in radians
     * @return this
     */
    public abstract Vector3dc rotateX(double angle);

    /**
     * Rotate this vector the specified radians around the Y axis.
     *
     * @param angle the angle in radians
     * @return this
     */
    public abstract Vector3dc rotateY(double angle);

    /**
     * Rotate this vector the specified radians around the Z axis.
     *
     * @param angle the angle in radians
     * @return this
     */
    public abstract Vector3dc rotateZ(double angle);

    /**
     * Divide this Vector3dc by the given scalar value.
     *
     * @param scalar the scalar to divide this vector by
     * @return this
     */
    public abstract Vector3dc div(double scalar);

    /**
     * Divide the components of this Vector3dc by the given scalar values and store the result in <code>this</code>.
     *
     * @param x the x component to divide this vector by
     * @param y the y component to divide this vector by
     * @param z the z component to divide this vector by
     * @return this
     */
    public abstract Vector3dc div(double x, double y, double z);

    /**
     * Normalize this vector.
     *
     * @return this
     */
    public abstract Vector3dc normalize();

    /**
     * Scale this vector to have the given length.
     *
     * @param length the desired length
     * @return this
     */
    public abstract Vector3dc normalize(double length);

    /**
     * Set this vector to be the cross product of this and v2.
     *
     * @param v the other vector
     * @return this
     */
    public abstract Vector3dc cross(IVector3d v);

    /**
     * Set this vector to be the cross product of itself and <tt>(x, y, z)</tt>.
     *
     * @param x the x component of the other vector
     * @param y the y component of the other vector
     * @param z the z component of the other vector
     * @return this
     */
    public abstract Vector3dc cross(double x, double y, double z);

    /**
     * Set the components of this vector to be the component-wise minimum of this and the other vector.
     *
     * @param v the other vector
     * @return this
     */
    public abstract Vector3dc min(IVector3d v);

    /**
     * Set the components of this vector to be the component-wise maximum of this and the other vector.
     *
     * @param v the other vector
     * @return this
     */
    public abstract Vector3dc max(IVector3d v);

    /**
     * Set all components to zero.
     *
     * @return this
     */
    public abstract Vector3dc zero();

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
    public abstract Vector3dc negate();

    /**
     * Set <code>this</code> vector's components to their respective absolute values.
     *
     * @return this
     */
    public abstract Vector3dc absolute();

    public abstract int hashCode();

    public abstract boolean equals(Object obj);

    /**
     * Reflect this vector about the given normal vector.
     *
     * @param normal the vector to reflect about
     * @return this
     */
    public abstract Vector3dc reflect(IVector3d normal);

    /**
     * Reflect this vector about the given normal vector.
     *
     * @param x the x component of the normal
     * @param y the y component of the normal
     * @param z the z component of the normal
     * @return this
     */
    public abstract Vector3dc reflect(double x, double y, double z);

    /**
     * Compute the half vector between this and the other vector.
     *
     * @param other the other vector
     * @return this
     */
    public abstract Vector3dc half(IVector3d other);

    /**
     * Compute the half vector between this and the vector <tt>(x, y, z)</tt>.
     *
     * @param x the x component of the other vector
     * @param y the y component of the other vector
     * @param z the z component of the other vector
     * @return this
     */
    public abstract Vector3dc half(double x, double y, double z);

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
    public abstract Vector3dc lerp(IVector3d other, double t);

    /**
     * Set the specified component of this vector to the given value.
     *
     * @param component the component, within <tt>[0..2]</tt>
     * @param value     the value
     * @return this
     */
    public abstract Vector3dc set(int component, double value);

    /**
     * Transform <code>this</code> vector so that it is orthogonal to the given vector <code>v</code> and normalize the
     * result.
     * <p>
     * Reference: <a href="https://en.wikipedia.org/wiki/Gram%E2%80%93Schmidt_process">Gram–Schmidt process</a>
     *
     * @param v the reference vector which the result should be orthogonal to
     * @return this
     */
    public abstract Vector3dc orthogonalize(IVector3d v);

    /**
     * Transform <code>this</code> vector so that it is orthogonal to the given unit vector <code>v</code> and normalize
     * the result.
     * <p>
     * The vector <code>v</code> is assumed to be a {@link #normalize() unit} vector.
     * <p>
     * Reference: <a href="https://en.wikipedia.org/wiki/Gram%E2%80%93Schmidt_process">Gram–Schmidt process</a>
     *
     * @param v the reference unit vector which the result should be orthogonal to
     * @return this
     */
    public abstract Vector3dc orthogonalizeUnit(IVector3d v);
}
