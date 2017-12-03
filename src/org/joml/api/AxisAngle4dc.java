/*
 * (C) Copyright 2015-2017 Kai Burjack

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
package org.joml.api;

import org.joml.api.matrix.*;
import org.joml.api.quaternion.IQuaterniond;
import org.joml.api.quaternion.IQuaternionf;
import org.joml.api.quaternion.Quaterniondc;
import org.joml.api.quaternion.Quaternionfc;
import org.joml.api.vector.*;

import java.io.Externalizable;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Represents a 3D rotation of a given radians about an axis represented as an unit 3D vector.
 * <p>
 * This class uses double-precision components.
 *
 * @author Kai Burjack
 */
public abstract class AxisAngle4dc implements Externalizable {

    /**
     * @return The x-component of the rotation axis.
     */
    public abstract double x();

    /**
     * @return The y-component of the rotation axis.
     */
    public abstract double y();

    /**
     * @return The z-component of the rotation axis.
     */
    public abstract double z();

    /**
     * @return The angle in radians.
     */
    public abstract double angle();

    /**
     * Set this {@link AxisAngle4dc} to the values of <code>a</code>.
     *
     * @param a the AngleAxis4f to copy the values from
     * @return this
     */
    public abstract AxisAngle4dc set(AxisAngle4dc a);

    /**
     * Set this {@link AxisAngle4dc} to the values of <code>a</code>.
     *
     * @param a the AngleAxis4f to copy the values from
     * @return this
     */
    public abstract AxisAngle4dc set(AxisAngle4fc a);

    /**
     * Set this {@link AxisAngle4dc} to the given values.
     *
     * @param angle the angle in radians
     * @param x     the x-coordinate of the rotation axis
     * @param y     the y-coordinate of the rotation axis
     * @param z     the z-coordinate of the rotation axis
     * @return this
     */
    public abstract AxisAngle4dc set(double angle, double x, double y, double z);

    /**
     * Set this {@link AxisAngle4dc} to the given values.
     *
     * @param angle the angle in radians
     * @param v     the rotation axis as a {@link IVector3d}
     * @return this
     */
    public abstract AxisAngle4dc set(double angle, IVector3d v);

    /**
     * Set this {@link AxisAngle4dc} to the given values.
     *
     * @param angle the angle in radians
     * @param v     the rotation axis as a {@link Vector3fc}
     * @return this
     */
    public abstract AxisAngle4dc set(double angle, Vector3fc v);

    /**
     * Set this {@link AxisAngle4dc} to be equivalent to the given {@link IQuaternionf}.
     *
     * @param q the quaternion to set this AngleAxis4d from
     * @return this
     */
    public abstract AxisAngle4dc set(IQuaternionf q);

    /**
     * Set this {@link AxisAngle4dc} to be equivalent to the given {@link IQuaterniond}.
     *
     * @param q the quaternion to set this AngleAxis4d from
     * @return this
     */
    public abstract AxisAngle4dc set(IQuaterniond q);

    /**
     * Set this {@link AxisAngle4dc} to be equivalent to the rotation of the given {@link IMatrix3f}.
     * <p>
     * Reference: <a href="http://www.euclideanspace.com/maths/geometry/rotations/conversions/matrixToAngle/">http://www.euclideanspace.com</a>
     *
     * @param m the IMatrix3f to set this AngleAxis4d from
     * @return this
     */
    public abstract AxisAngle4dc set(IMatrix3f m);

    /**
     * Set this {@link AxisAngle4dc} to be equivalent to the rotation of the given {@link IMatrix3d}.
     * <p>
     * Reference: <a href="http://www.euclideanspace.com/maths/geometry/rotations/conversions/matrixToAngle/">http://www.euclideanspace.com</a>
     *
     * @param m the IMatrix3d to set this AngleAxis4d from
     * @return this
     */
    public abstract AxisAngle4dc set(IMatrix3d m);

    /**
     * Set this {@link AxisAngle4dc} to be equivalent to the rotational component of the given {@link IMatrix4f}.
     * <p>
     * Reference: <a href="http://www.euclideanspace.com/maths/geometry/rotations/conversions/matrixToAngle/">http://www.euclideanspace.com</a>
     *
     * @param m the IMatrix4f to set this AngleAxis4d from
     * @return this
     */
    public abstract AxisAngle4dc set(IMatrix4f m);

    /**
     * Set this {@link AxisAngle4dc} to be equivalent to the rotational component of the given {@link IMatrix4x3f}.
     * <p>
     * Reference: <a href="http://www.euclideanspace.com/maths/geometry/rotations/conversions/matrixToAngle/">http://www.euclideanspace.com</a>
     *
     * @param m the IMatrix4x3f to set this AngleAxis4d from
     * @return this
     */
    public abstract AxisAngle4dc set(IMatrix4x3f m);

    /**
     * Set this {@link AxisAngle4dc} to be equivalent to the rotational component of the given {@link IMatrix4d}.
     * <p>
     * Reference: <a href="http://www.euclideanspace.com/maths/geometry/rotations/conversions/matrixToAngle/">http://www.euclideanspace.com</a>
     *
     * @param m the IMatrix4d to set this AngleAxis4d from
     * @return this
     */
    public abstract AxisAngle4dc set(IMatrix4d m);

    /**
     * Set the given {@link Quaternionfc} to be equivalent to this {@link AxisAngle4dc} rotation.
     *
     * @param q the quaternion to set
     * @return q
     *
     * @see Quaternionfc#set(AxisAngle4dc)
     */
    public abstract Quaternionfc get(Quaternionfc q);

    /**
     * Set the given {@link Quaterniondc} to be equivalent to this {@link AxisAngle4dc} rotation.
     *
     * @param q the quaternion to set
     * @return q
     *
     * @see Quaterniondc#set(AxisAngle4dc)
     */
    public abstract Quaterniondc get(Quaterniondc q);

    /**
     * Set the given {@link Matrix4fc} to a rotation transformation equivalent to this {@link AxisAngle4dc}.
     *
     * @param m the matrix to set
     * @return m
     *
     * @see Matrix4fc#set(AxisAngle4dc)
     */
    public abstract Matrix4fc get(Matrix4fc m);

    /**
     * Set the given {@link Matrix3fc} to a rotation transformation equivalent to this {@link AxisAngle4dc}.
     *
     * @param m the matrix to set
     * @return m
     *
     * @see Matrix3fc#set(AxisAngle4dc)
     */
    public abstract Matrix3fc get(Matrix3fc m);

    /**
     * Set the given {@link Matrix4dc} to a rotation transformation equivalent to this {@link AxisAngle4dc}.
     *
     * @param m the matrix to set
     * @return m
     *
     * @see Matrix4fc#set(AxisAngle4dc)
     */
    public abstract Matrix4dc get(Matrix4dc m);

    /**
     * Set the given {@link Matrix3dc} to a rotation transformation equivalent to this {@link AxisAngle4dc}.
     *
     * @param m the matrix to set
     * @return m
     *
     * @see Matrix3fc#set(AxisAngle4dc)
     */
    public abstract Matrix3dc get(Matrix3dc m);

    /**
     * Normalize the axis vector.
     *
     * @return this
     */
    public abstract AxisAngle4dc normalize();

    /**
     * Increase the rotation angle by the given amount.
     * <p>
     * This method also takes care of wrapping around.
     *
     * @param ang the angle increase
     * @return this
     */
    public abstract AxisAngle4dc rotate(double ang);

    /**
     * Transform the given vector by the rotation transformation described by this {@link AxisAngle4dc}.
     *
     * @param v the vector to transform
     * @return v
     */
    public abstract Vector3dc transform(Vector3dc v);

    /**
     * Transform the given vector by the rotation transformation described by this {@link AxisAngle4dc} and store the
     * result in <code>dest</code>.
     *
     * @param v    the vector to transform
     * @param dest will hold the result
     * @return dest
     */
    public abstract Vector3dc transform(IVector3d v, Vector3dc dest);

    /**
     * Transform the given vector by the rotation transformation described by this {@link AxisAngle4dc}.
     *
     * @param v the vector to transform
     * @return v
     */
    public abstract Vector3fc transform(Vector3fc v);

    /**
     * Transform the given vector by the rotation transformation described by this {@link AxisAngle4dc} and store the
     * result in <code>dest</code>.
     *
     * @param v    the vector to transform
     * @param dest will hold the result
     * @return dest
     */
    public abstract Vector3fc transform(IVector3f v, Vector3fc dest);

    /**
     * Transform the given vector by the rotation transformation described by this {@link AxisAngle4dc}.
     *
     * @param v the vector to transform
     * @return v
     */
    public abstract Vector4dc transform(Vector4dc v);

    /**
     * Transform the given vector by the rotation transformation described by this {@link AxisAngle4dc} and store the
     * result in <code>dest</code>.
     *
     * @param v    the vector to transform
     * @param dest will hold the result
     * @return dest
     */
    public abstract Vector4dc transform(IVector4d v, Vector4dc dest);

    /**
     * Return a string representation of this {@link AxisAngle4dc}.
     * <p>
     * This method creates a new {@link DecimalFormat} on every invocation with the format string "<tt>
     * 0.000E0;-</tt>".
     *
     * @return the string representation
     */
    public abstract String toString();

    /**
     * Return a string representation of this {@link AxisAngle4dc} by formatting the components with the given {@link
     * NumberFormat}.
     *
     * @param formatter the {@link NumberFormat} used to format the vector components with
     * @return the string representation
     */
    public abstract String toString(NumberFormat formatter);

    public abstract int hashCode();

    public abstract boolean equals(Object obj);
}
