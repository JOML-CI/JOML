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
package org.joml.api.quaternion;

import org.joml.api.AxisAngle4dc;
import org.joml.api.AxisAngle4fc;
import org.joml.api.matrix.*;
import org.joml.api.vector.IVector3d;
import org.joml.api.vector.IVector3f;
import org.joml.api.vector.Vector3dc;

import java.io.Externalizable;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Quaternion of 4 single-precision floats which can represent rotation and uniform scaling.
 *
 * @author Richard Greenlees
 * @author Kai Burjack
 */
public abstract class Quaternionfc implements Externalizable, IQuaternionf {

    /**
     * Normalize this quaternion.
     *
     * @return this
     */
    public abstract Quaternionfc normalize();

    /**
     * Add the quaternion <tt>(x, y, z, w)</tt> to this quaternion.
     *
     * @param x the x component of the vector part
     * @param y the y component of the vector part
     * @param z the z component of the vector part
     * @param w the real/scalar component
     * @return this
     */
    public abstract Quaternionfc add(float x, float y, float z, float w);

    /**
     * Add <code>q2</code> to this quaternion.
     *
     * @param q2 the quaternion to add to this
     * @return this
     */
    public abstract Quaternionfc add(IQuaternionf q2);

    /**
     * Return the dot of this quaternion and <code>otherQuat</code>.
     *
     * @param otherQuat the other quaternion
     * @return the dot product
     */
    public abstract float dot(Quaternionfc otherQuat);

    /**
     * Set this quaternion to the given values.
     *
     * @param x the new value of x
     * @param y the new value of y
     * @param z the new value of z
     * @param w the new value of w
     * @return this
     */
    public abstract Quaternionfc set(float x, float y, float z, float w);

    /**
     * Set the x, y and z components of this quaternion to the given values.
     *
     * @param x the new value of x
     * @param y the new value of y
     * @param z the new value of z
     * @return this
     */
    public abstract Quaternionfc set(float x, float y, float z);

    /**
     * Set this quaternion to be a copy of q.
     *
     * @param q the {@link Quaternionfc} to copy
     * @return this
     */
    public abstract Quaternionfc set(IQuaternionf q);

    /**
     * Set this quaternion to a rotation equivalent to the given {@link AxisAngle4fc}.
     *
     * @param axisAngle the {@link AxisAngle4fc}
     * @return this
     */
    public abstract Quaternionfc set(AxisAngle4fc axisAngle);

    /**
     * Set this quaternion to a rotation equivalent to the given {@link AxisAngle4dc}.
     *
     * @param axisAngle the {@link AxisAngle4dc}
     * @return this
     */
    public abstract Quaternionfc set(AxisAngle4dc axisAngle);

    /**
     * Set this quaternion to a rotation equivalent to the supplied axis and angle (in radians).
     * <p>
     * This method assumes that the given rotation axis <tt>(x, y, z)</tt> is already normalized
     *
     * @param angle the angle in radians
     * @param x     the x-component of the normalized rotation axis
     * @param y     the y-component of the normalized rotation axis
     * @param z     the z-component of the normalized rotation axis
     * @return this
     */
    public abstract Quaternionfc setAngleAxis(float angle, float x, float y, float z);

    /**
     * Set this quaternion to a rotation equivalent to the supplied axis and angle (in radians).
     * <p>
     * This method assumes that the given rotation axis <tt>(x, y, z)</tt> is already normalized
     *
     * @param angle the angle in radians
     * @param x     the x-component of the normalized rotation axis
     * @param y     the y-component of the normalized rotation axis
     * @param z     the z-component of the normalized rotation axis
     * @return this
     */
    public abstract Quaternionfc setAngleAxis(double angle, double x, double y, double z);

    /**
     * Set this {@link Quaternionfc} to a rotation of the given angle in radians about the supplied axis, all of which
     * are specified via the {@link AxisAngle4fc}.
     *
     * @param axisAngle the {@link AxisAngle4fc} giving the rotation angle in radians and the axis to rotate about
     * @return this
     *
     * @see #rotationAxis(float, float, float, float)
     */
    public abstract Quaternionfc rotationAxis(AxisAngle4fc axisAngle);

    /**
     * Set this quaternion to a rotation of the given angle in radians about the supplied axis.
     *
     * @param angle the rotation angle in radians
     * @param axisX the x-coordinate of the rotation axis
     * @param axisY the y-coordinate of the rotation axis
     * @param axisZ the z-coordinate of the rotation axis
     * @return this
     */
    public abstract Quaternionfc rotationAxis(float angle, float axisX, float axisY, float axisZ);

    /**
     * Set this quaternion to a rotation of the given angle in radians about the supplied axis.
     *
     * @param angle the rotation angle in radians
     * @param axis  the axis to rotate about
     * @return this
     *
     * @see #rotationAxis(float, float, float, float)
     */
    public abstract Quaternionfc rotationAxis(float angle, IVector3f axis);

    /**
     * Set this quaternion to represent a rotation of the given angles in radians about the basis unit axes of the
     * cartesian space.
     *
     * @param angleX the angle in radians to rotate about the x axis
     * @param angleY the angle in radians to rotate about the y axis
     * @param angleZ the angle in radians to rotate about the z axis
     * @return this
     */
    public abstract Quaternionfc rotation(float angleX, float angleY, float angleZ);

    /**
     * Set this quaternion to represent a rotation of the given radians about the x axis.
     *
     * @param angle the angle in radians to rotate about the x axis
     * @return this
     */
    public abstract Quaternionfc rotationX(float angle);

    /**
     * Set this quaternion to represent a rotation of the given radians about the y axis.
     *
     * @param angle the angle in radians to rotate about the y axis
     * @return this
     */
    public abstract Quaternionfc rotationY(float angle);

    /**
     * Set this quaternion to represent a rotation of the given radians about the z axis.
     *
     * @param angle the angle in radians to rotate about the z axis
     * @return this
     */
    public abstract Quaternionfc rotationZ(float angle);

    /**
     * Set this quaternion to be a representation of the rotational component of the given matrix.
     * <p>
     * This method assumes that the first three columns of the upper left 3x3 submatrix are no unit vectors.
     *
     * @param mat the matrix whose rotational component is used to set this quaternion
     * @return this
     */
    public abstract Quaternionfc setFromUnnormalized(IMatrix4f mat);

    /**
     * Set this quaternion to be a representation of the rotational component of the given matrix.
     * <p>
     * This method assumes that the first three columns of the upper left 3x3 submatrix are no unit vectors.
     *
     * @param mat the matrix whose rotational component is used to set this quaternion
     * @return this
     */
    public abstract Quaternionfc setFromUnnormalized(IMatrix4x3f mat);

    /**
     * Set this quaternion to be a representation of the rotational component of the given matrix.
     * <p>
     * This method assumes that the first three columns of the upper left 3x3 submatrix are no unit vectors.
     *
     * @param mat the matrix whose rotational component is used to set this quaternion
     * @return this
     */
    public abstract Quaternionfc setFromUnnormalized(IMatrix4x3d mat);

    /**
     * Set this quaternion to be a representation of the rotational component of the given matrix.
     * <p>
     * This method assumes that the first three columns of the upper left 3x3 submatrix are unit vectors.
     *
     * @param mat the matrix whose rotational component is used to set this quaternion
     * @return this
     */
    public abstract Quaternionfc setFromNormalized(IMatrix4f mat);

    /**
     * Set this quaternion to be a representation of the rotational component of the given matrix.
     * <p>
     * This method assumes that the first three columns of the upper left 3x3 submatrix are unit vectors.
     *
     * @param mat the matrix whose rotational component is used to set this quaternion
     * @return this
     */
    public abstract Quaternionfc setFromNormalized(IMatrix4x3f mat);

    /**
     * Set this quaternion to be a representation of the rotational component of the given matrix.
     * <p>
     * This method assumes that the first three columns of the upper left 3x3 submatrix are unit vectors.
     *
     * @param mat the matrix whose rotational component is used to set this quaternion
     * @return this
     */
    public abstract Quaternionfc setFromNormalized(IMatrix4x3d mat);

    /**
     * Set this quaternion to be a representation of the rotational component of the given matrix.
     * <p>
     * This method assumes that the first three columns of the upper left 3x3 submatrix are no unit vectors.
     *
     * @param mat the matrix whose rotational component is used to set this quaternion
     * @return this
     */
    public abstract Quaternionfc setFromUnnormalized(IMatrix4d mat);

    /**
     * Set this quaternion to be a representation of the rotational component of the given matrix.
     * <p>
     * This method assumes that the first three columns of the upper left 3x3 submatrix are unit vectors.
     *
     * @param mat the matrix whose rotational component is used to set this quaternion
     * @return this
     */
    public abstract Quaternionfc setFromNormalized(IMatrix4d mat);

    /**
     * Set this quaternion to be a representation of the rotational component of the given matrix.
     * <p>
     * This method assumes that the first three columns of the upper left 3x3 submatrix are no unit vectors.
     *
     * @param mat the matrix whose rotational component is used to set this quaternion
     * @return this
     */
    public abstract Quaternionfc setFromUnnormalized(IMatrix3f mat);

    /**
     * Set this quaternion to be a representation of the rotational component of the given matrix.
     * <p>
     * This method assumes that the first three columns of the upper left 3x3 submatrix are unit vectors.
     *
     * @param mat the matrix whose rotational component is used to set this quaternion
     * @return this
     */
    public abstract Quaternionfc setFromNormalized(IMatrix3f mat);

    /**
     * Set this quaternion to be a representation of the rotational component of the given matrix.
     * <p>
     * This method assumes that the first three columns of the upper left 3x3 submatrix are no unit vectors.
     *
     * @param mat the matrix whose rotational component is used to set this quaternion
     * @return this
     */
    public abstract Quaternionfc setFromUnnormalized(IMatrix3d mat);

    /**
     * Set this quaternion to be a representation of the rotational component of the given matrix.
     *
     * @param mat the matrix whose rotational component is used to set this quaternion
     * @return this
     */
    public abstract Quaternionfc setFromNormalized(IMatrix3d mat);

    /**
     * Set this quaternion to be a representation of the supplied axis and angle (in radians).
     *
     * @param axis  the rotation axis
     * @param angle the angle in radians
     * @return this
     */
    public abstract Quaternionfc fromAxisAngleRad(IVector3f axis, float angle);

    /**
     * Set this quaternion to be a representation of the supplied axis and angle (in radians).
     *
     * @param axisX the x component of the rotation axis
     * @param axisY the y component of the rotation axis
     * @param axisZ the z component of the rotation axis
     * @param angle the angle in radians
     * @return this
     */
    public abstract Quaternionfc fromAxisAngleRad(float axisX, float axisY, float axisZ, float angle);

    /**
     * Set this quaternion to be a representation of the supplied axis and angle (in degrees).
     *
     * @param axis  the rotation axis
     * @param angle the angle in degrees
     * @return this
     */
    public abstract Quaternionfc fromAxisAngleDeg(IVector3f axis, float angle);

    /**
     * Set this quaternion to be a representation of the supplied axis and angle (in degrees).
     *
     * @param axisX the x component of the rotation axis
     * @param axisY the y component of the rotation axis
     * @param axisZ the z component of the rotation axis
     * @param angle the angle in radians
     * @return this
     */
    public abstract Quaternionfc fromAxisAngleDeg(float axisX, float axisY, float axisZ, float angle);

    /**
     * Multiply this quaternion by <code>q</code>.
     * <p>
     * If <tt>T</tt> is <code>this</code> and <tt>Q</tt> is the given quaternion, then the resulting quaternion
     * <tt>R</tt> is:
     * <p>
     * <tt>R = T * Q</tt>
     * <p>
     * So, this method uses post-multiplication like the matrix classes, resulting in a vector to be transformed by
     * <tt>Q</tt> first, and then by <tt>T</tt>.
     *
     * @param q the quaternion to multiply <code>this</code> by
     * @return this
     */
    public abstract Quaternionfc mul(IQuaternionf q);

    /**
     * Multiply this quaternion by the quaternion represented via <tt>(qx, qy, qz, qw)</tt>.
     * <p>
     * If <tt>T</tt> is <code>this</code> and <tt>Q</tt> is the given quaternion, then the resulting quaternion
     * <tt>R</tt> is:
     * <p>
     * <tt>R = T * Q</tt>
     * <p>
     * So, this method uses post-multiplication like the matrix classes, resulting in a vector to be transformed by
     * <tt>Q</tt> first, and then by <tt>T</tt>.
     *
     * @param qx the x component of the quaternion to multiply <code>this</code> by
     * @param qy the y component of the quaternion to multiply <code>this</code> by
     * @param qz the z component of the quaternion to multiply <code>this</code> by
     * @param qw the w component of the quaternion to multiply <code>this</code> by
     * @return this
     */
    public abstract Quaternionfc mul(float qx, float qy, float qz, float qw);

    /**
     * Pre-multiply this quaternion by <code>q</code>.
     * <p>
     * If <tt>T</tt> is <code>this</code> and <tt>Q</tt> is the given quaternion, then the resulting quaternion
     * <tt>R</tt> is:
     * <p>
     * <tt>R = Q * T</tt>
     * <p>
     * So, this method uses pre-multiplication, resulting in a vector to be transformed by <tt>T</tt> first, and then by
     * <tt>Q</tt>.
     *
     * @param q the quaternion to pre-multiply <code>this</code> by
     * @return this
     */
    public abstract Quaternionfc premul(IQuaternionf q);

    /**
     * Pre-multiply this quaternion by the quaternion represented via <tt>(qx, qy, qz, qw)</tt>.
     * <p>
     * If <tt>T</tt> is <code>this</code> and <tt>Q</tt> is the given quaternion, then the resulting quaternion
     * <tt>R</tt> is:
     * <p>
     * <tt>R = Q * T</tt>
     * <p>
     * So, this method uses pre-multiplication, resulting in a vector to be transformed by <tt>T</tt> first, and then by
     * <tt>Q</tt>.
     *
     * @param qx the x component of the quaternion to multiply <code>this</code> by
     * @param qy the y component of the quaternion to multiply <code>this</code> by
     * @param qz the z component of the quaternion to multiply <code>this</code> by
     * @param qw the w component of the quaternion to multiply <code>this</code> by
     * @return this
     */
    public abstract Quaternionfc premul(float qx, float qy, float qz, float qw);

    /**
     * Transform the given vector by this quaternion and store the result in <code>dest</code>. This will apply the
     * rotation described by this quaternion to the given vector.
     *
     * @param vec  the vector to transform
     * @param dest will hold the result
     * @return dest
     */
    public abstract Vector3dc transform(IVector3d vec, Vector3dc dest);

    /**
     * Transform the given vector <tt>(x, y, z)</tt> by this quaternion and store the result in <code>dest</code>. This
     * will apply the rotation described by this quaternion to the given vector.
     *
     * @param x    the x coordinate of the vector to transform
     * @param y    the y coordinate of the vector to transform
     * @param z    the z coordinate of the vector to transform
     * @param dest will hold the result
     * @return dest
     */
    public abstract Vector3dc transform(double x, double y, double z, Vector3dc dest);

    /**
     * Invert this quaternion and {@link #normalize() normalize} it.
     * <p>
     * If this quaternion is already normalized, then {@link #conjugate()} should be used instead.
     *
     * @return this
     *
     * @see #conjugate()
     */
    public abstract Quaternionfc invert();

    /**
     * Divide <code>this</code> quaternion by <code>b</code>.
     * <p>
     * The division expressed using the inverse is performed in the following way:
     * <p>
     * <tt>this = this * b^-1</tt>, where <tt>b^-1</tt> is the inverse of <code>b</code>.
     *
     * @param b the {@link Quaternionfc} to divide this by
     * @return this
     */
    public abstract Quaternionfc div(IQuaternionf b);

    /**
     * Conjugate this quaternion.
     *
     * @return this
     */
    public abstract Quaternionfc conjugate();

    /**
     * Set this quaternion to the identity.
     *
     * @return this
     */
    public abstract Quaternionfc identity();

    /**
     * Apply a rotation to <code>this</code> quaternion rotating the given radians about the cartesian base unit axes,
     * called the euler angles using rotation sequence <tt>XYZ</tt>.
     * <p>
     * This method is equivalent to calling: <tt>rotateX(angleX).rotateY(angleY).rotateZ(angleZ)</tt>
     * <p>
     * If <code>Q</code> is <code>this</code> quaternion and <code>R</code> the quaternion representing the specified
     * rotation, then the new quaternion will be <code>Q * R</code>. So when transforming a vector <code>v</code> with
     * the new quaternion by using <code>Q * R * v</code>, the rotation added by this method will be applied first!
     *
     * @param angleX the angle in radians to rotate about the x axis
     * @param angleY the angle in radians to rotate about the y axis
     * @param angleZ the angle in radians to rotate about the z axis
     * @return this
     */
    public abstract Quaternionfc rotateXYZ(float angleX, float angleY, float angleZ);

    /**
     * Apply a rotation to <code>this</code> quaternion rotating the given radians about the cartesian base unit axes,
     * called the euler angles, using the rotation sequence <tt>ZYX</tt>.
     * <p>
     * This method is equivalent to calling: <tt>rotateZ(angleZ).rotateY(angleY).rotateX(angleX)</tt>
     * <p>
     * If <code>Q</code> is <code>this</code> quaternion and <code>R</code> the quaternion representing the specified
     * rotation, then the new quaternion will be <code>Q * R</code>. So when transforming a vector <code>v</code> with
     * the new quaternion by using <code>Q * R * v</code>, the rotation added by this method will be applied first!
     *
     * @param angleZ the angle in radians to rotate about the z axis
     * @param angleY the angle in radians to rotate about the y axis
     * @param angleX the angle in radians to rotate about the x axis
     * @return this
     */
    public abstract Quaternionfc rotateZYX(float angleZ, float angleY, float angleX);

    /**
     * Apply a rotation to <code>this</code> quaternion rotating the given radians about the cartesian base unit axes,
     * called the euler angles, using the rotation sequence <tt>YXZ</tt>.
     * <p>
     * This method is equivalent to calling: <tt>rotateY(angleY).rotateX(angleX).rotateZ(angleZ)</tt>
     * <p>
     * If <code>Q</code> is <code>this</code> quaternion and <code>R</code> the quaternion representing the specified
     * rotation, then the new quaternion will be <code>Q * R</code>. So when transforming a vector <code>v</code> with
     * the new quaternion by using <code>Q * R * v</code>, the rotation added by this method will be applied first!
     *
     * @param angleY the angle in radians to rotate about the y axis
     * @param angleX the angle in radians to rotate about the x axis
     * @param angleZ the angle in radians to rotate about the z axis
     * @return this
     */
    public abstract Quaternionfc rotateYXZ(float angleZ, float angleY, float angleX);

    /**
     * Set this quaternion from the supplied euler angles (in radians) with rotation order XYZ.
     * <p>
     * This method is equivalent to calling: <tt>rotationX(angleX).rotateY(angleY).rotateZ(angleZ)</tt>
     * <p>
     * Reference: <a href="http://gamedev.stackexchange.com/questions/13436/glm-euler-angles-to-quaternion#answer-13446">this
     * stackexchange answer</a>
     *
     * @param angleX the angle in radians to rotate about x
     * @param angleY the angle in radians to rotate about y
     * @param angleZ the angle in radians to rotate about z
     * @return this
     */
    public abstract Quaternionfc rotationXYZ(float angleX, float angleY, float angleZ);

    /**
     * Set this quaternion from the supplied euler angles (in radians) with rotation order ZYX.
     * <p>
     * This method is equivalent to calling: <tt>rotationZ(angleZ).rotateY(angleY).rotateX(angleX)</tt>
     * <p>
     * Reference: <a href="http://gamedev.stackexchange.com/questions/13436/glm-euler-angles-to-quaternion#answer-13446">this
     * stackexchange answer</a>
     *
     * @param angleX the angle in radians to rotate about x
     * @param angleY the angle in radians to rotate about y
     * @param angleZ the angle in radians to rotate about z
     * @return this
     */
    public abstract Quaternionfc rotationZYX(float angleZ, float angleY, float angleX);

    /**
     * Set this quaternion from the supplied euler angles (in radians) with rotation order YXZ.
     * <p>
     * This method is equivalent to calling: <tt>rotationY(angleY).rotateX(angleX).rotateZ(angleZ)</tt>
     * <p>
     * Reference: <a href="https://en.wikipedia.org/wiki/Conversion_between_quaternions_and_Euler_angles">https://en.wikipedia.org</a>
     *
     * @param angleY the angle in radians to rotate about y
     * @param angleX the angle in radians to rotate about x
     * @param angleZ the angle in radians to rotate about z
     * @return this
     */
    public abstract Quaternionfc rotationYXZ(float angleY, float angleX, float angleZ);

    /**
     * Interpolate between <code>this</code> {@link #normalize() unit} quaternion and the specified <code>target</code>
     * {@link #normalize() unit} quaternion using spherical linear interpolation using the specified interpolation
     * factor <code>alpha</code>.
     * <p>
     * This method resorts to non-spherical linear interpolation when the absolute dot product of <code>this</code> and
     * <code>target</code> is below <tt>1E-6f</tt>.
     *
     * @param target the target of the interpolation, which should be reached with <tt>alpha = 1.0</tt>
     * @param alpha  the interpolation factor, within <tt>[0..1]</tt>
     * @return this
     */
    public abstract Quaternionfc slerp(IQuaternionf target, float alpha);

    /**
     * Apply scaling to this quaternion, which results in any vector transformed by this quaternion to change its length
     * by the given <code>factor</code>.
     *
     * @param factor the scaling factor
     * @return this
     */
    public abstract Quaternionfc scale(float factor);

    /**
     * Set this quaternion to represent scaling, which results in a transformed vector to change its length by the given
     * <code>factor</code>.
     *
     * @param factor the scaling factor
     * @return this
     */
    public abstract Quaternionfc scaling(float factor);

    /**
     * Integrate the rotation given by the angular velocity <code>(vx, vy, vz)</code> around the x, y and z axis,
     * respectively, with respect to the given elapsed time delta <code>dt</code> and add the differentiate rotation to
     * the rotation represented by this quaternion.
     * <p>
     * This method pre-multiplies the rotation given by <code>dt</code> and <code>(vx, vy, vz)</code> by
     * <code>this</code>, so the angular velocities are always relative to the local coordinate system of the rotation
     * represented by <code>this</code> quaternion.
     * <p>
     * This method is equivalent to calling: <code>rotateLocal(dt * vx, dt * vy, dt * vz)</code>
     * <p>
     * Reference: <a href="http://physicsforgames.blogspot.de/2010/02/quaternions.html">http://physicsforgames.blogspot.de/</a>
     *
     * @param dt the delta time
     * @param vx the angular velocity around the x axis
     * @param vy the angular velocity around the y axis
     * @param vz the angular velocity around the z axis
     * @return this
     *
     * @see #rotateLocal(float, float, float)
     */
    public abstract Quaternionfc integrate(float dt, float vx, float vy, float vz);

    /**
     * Compute a linear (non-spherical) interpolation of <code>this</code> and the given quaternion <code>q</code> and
     * store the result in <code>this</code>.
     *
     * @param q      the other quaternion
     * @param factor the interpolation factor. It is between 0.0 and 1.0
     * @return this
     */
    public abstract Quaternionfc nlerp(IQuaternionf q, float factor);

    /**
     * Compute linear (non-spherical) interpolations of <code>this</code> and the given quaternion <code>q</code>
     * iteratively and store the result in <code>this</code>.
     * <p>
     * This method performs a series of small-step nlerp interpolations to avoid doing a costly spherical linear
     * interpolation, like {@link #slerp(IQuaternionf, float, Quaternionfc) slerp}, by subdividing the rotation arc
     * between <code>this</code> and <code>q</code> via non-spherical linear interpolations as long as the absolute dot
     * product of <code>this</code> and <code>q</code> is greater than the given <code>dotThreshold</code> parameter.
     * <p>
     * Thanks to <tt>@theagentd</tt> at <a href="http://www.java-gaming.org/">http://www.java-gaming.org/</a> for
     * providing the code.
     *
     * @param q            the other quaternion
     * @param alpha        the interpolation factor, between 0.0 and 1.0
     * @param dotThreshold the threshold for the dot product of <code>this</code> and <code>q</code> above which this
     *                     method performs another iteration of a small-step linear interpolation
     * @return this
     */
    public abstract Quaternionfc nlerpIterative(IQuaternionf q, float alpha, float dotThreshold);

    /**
     * Apply a rotation to this quaternion that maps the given direction to the positive Z axis.
     * <p>
     * Because there are multiple possibilities for such a rotation, this method will choose the one that ensures the
     * given up direction to remain parallel to the plane spanned by the <code>up</code> and <code>dir</code> vectors.
     * <p>
     * If <code>Q</code> is <code>this</code> quaternion and <code>R</code> the quaternion representing the specified
     * rotation, then the new quaternion will be <code>Q * R</code>. So when transforming a vector <code>v</code> with
     * the new quaternion by using <code>Q * R * v</code>, the rotation added by this method will be applied first!
     * <p>
     * Reference: <a href="http://answers.unity3d.com/questions/467614/what-is-the-source-code-of-quaternionlookrotation.html">http://answers.unity3d.com</a>
     *
     * @param dir the direction to map to the positive Z axis
     * @param up  the vector which will be mapped to a vector parallel to the plane spanned by the given
     *            <code>dir</code> and <code>up</code>
     * @return this
     *
     * @see #lookAlong(float, float, float, float, float, float, Quaternionfc)
     */
    public abstract Quaternionfc lookAlong(IVector3f dir, IVector3f up);

    /**
     * Apply a rotation to this quaternion that maps the given direction to the positive Z axis.
     * <p>
     * Because there are multiple possibilities for such a rotation, this method will choose the one that ensures the
     * given up direction to remain parallel to the plane spanned by the <tt>up</tt> and <tt>dir</tt> vectors.
     * <p>
     * If <code>Q</code> is <code>this</code> quaternion and <code>R</code> the quaternion representing the specified
     * rotation, then the new quaternion will be <code>Q * R</code>. So when transforming a vector <code>v</code> with
     * the new quaternion by using <code>Q * R * v</code>, the rotation added by this method will be applied first!
     * <p>
     * Reference: <a href="http://answers.unity3d.com/questions/467614/what-is-the-source-code-of-quaternionlookrotation.html">http://answers.unity3d.com</a>
     *
     * @param dirX the x-coordinate of the direction to look along
     * @param dirY the y-coordinate of the direction to look along
     * @param dirZ the z-coordinate of the direction to look along
     * @param upX  the x-coordinate of the up vector
     * @param upY  the y-coordinate of the up vector
     * @param upZ  the z-coordinate of the up vector
     * @return this
     *
     * @see #lookAlong(float, float, float, float, float, float, Quaternionfc)
     */
    public abstract Quaternionfc lookAlong(float dirX, float dirY, float dirZ, float upX, float upY, float upZ);

    /**
     * Set <code>this</code> quaternion to a rotation that rotates the <tt>fromDir</tt> vector to point along
     * <tt>toDir</tt>.
     * <p>
     * Since there can be multiple possible rotations, this method chooses the one with the shortest arc.
     * <p>
     * Reference: <a href="http://stackoverflow.com/questions/1171849/finding-quaternion-representing-the-rotation-from-one-vector-to-another#answer-1171995">stackoverflow.com</a>
     *
     * @param fromDirX the x-coordinate of the direction to rotate into the destination direction
     * @param fromDirY the y-coordinate of the direction to rotate into the destination direction
     * @param fromDirZ the z-coordinate of the direction to rotate into the destination direction
     * @param toDirX   the x-coordinate of the direction to rotate to
     * @param toDirY   the y-coordinate of the direction to rotate to
     * @param toDirZ   the z-coordinate of the direction to rotate to
     * @return this
     */
    public abstract Quaternionfc rotationTo(float fromDirX, float fromDirY, float fromDirZ, float toDirX, float toDirY, float toDirZ);

    /**
     * Set <code>this</code> quaternion to a rotation that rotates the <code>fromDir</code> vector to point along
     * <code>toDir</code>.
     * <p>
     * Because there can be multiple possible rotations, this method chooses the one with the shortest arc.
     *
     * @param fromDir the starting direction
     * @param toDir   the destination direction
     * @return this
     *
     * @see #rotationTo(float, float, float, float, float, float)
     */
    public abstract Quaternionfc rotationTo(IVector3f fromDir, IVector3f toDir);

    /**
     * Apply a rotation to <code>this</code> that rotates the <tt>fromDir</tt> vector to point along <tt>toDir</tt>.
     * <p>
     * Since there can be multiple possible rotations, this method chooses the one with the shortest arc.
     * <p>
     * If <code>Q</code> is <code>this</code> quaternion and <code>R</code> the quaternion representing the specified
     * rotation, then the new quaternion will be <code>Q * R</code>. So when transforming a vector <code>v</code> with
     * the new quaternion by using <code>Q * R * v</code>, the rotation added by this method will be applied first!
     *
     * @param fromDirX the x-coordinate of the direction to rotate into the destination direction
     * @param fromDirY the y-coordinate of the direction to rotate into the destination direction
     * @param fromDirZ the z-coordinate of the direction to rotate into the destination direction
     * @param toDirX   the x-coordinate of the direction to rotate to
     * @param toDirY   the y-coordinate of the direction to rotate to
     * @param toDirZ   the z-coordinate of the direction to rotate to
     * @return this
     *
     * @see #rotateTo(float, float, float, float, float, float, Quaternionfc)
     */
    public abstract Quaternionfc rotateTo(float fromDirX, float fromDirY, float fromDirZ, float toDirX, float toDirY, float toDirZ);

    /**
     * Apply a rotation to <code>this</code> that rotates the <code>fromDir</code> vector to point along
     * <code>toDir</code>.
     * <p>
     * Because there can be multiple possible rotations, this method chooses the one with the shortest arc.
     * <p>
     * If <code>Q</code> is <code>this</code> quaternion and <code>R</code> the quaternion representing the specified
     * rotation, then the new quaternion will be <code>Q * R</code>. So when transforming a vector <code>v</code> with
     * the new quaternion by using <code>Q * R * v</code>, the rotation added by this method will be applied first!
     *
     * @param fromDir the starting direction
     * @param toDir   the destination direction
     * @return this
     *
     * @see #rotateTo(float, float, float, float, float, float, Quaternionfc)
     */
    public abstract Quaternionfc rotateTo(IVector3f fromDir, IVector3f toDir);

    /**
     * Apply a rotation to <code>this</code> quaternion rotating the given radians about the basis unit axes of the
     * cartesian space.
     * <p>
     * If <code>Q</code> is <code>this</code> quaternion and <code>R</code> the quaternion representing the specified
     * rotation, then the new quaternion will be <code>Q * R</code>. So when transforming a vector <code>v</code> with
     * the new quaternion by using <code>Q * R * v</code>, the rotation added by this method will be applied first!
     *
     * @param angleX the angle in radians to rotate about the x axis
     * @param angleY the angle in radians to rotate about the y axis
     * @param angleZ the angle in radians to rotate about the z axis
     * @return this
     *
     * @see #rotate(float, float, float, Quaternionfc)
     */
    public abstract Quaternionfc rotate(float angleX, float angleY, float angleZ);

    /**
     * Apply a rotation to <code>this</code> quaternion rotating the given radians about the basis unit axes of the
     * local coordinate system represented by this quaternion.
     * <p>
     * If <code>Q</code> is <code>this</code> quaternion and <code>R</code> the quaternion representing the specified
     * rotation, then the new quaternion will be <code>R * Q</code>. So when transforming a vector <code>v</code> with
     * the new quaternion by using <code>R * Q * v</code>, the rotation represented by <code>this</code> will be applied
     * first!
     *
     * @param angleX the angle in radians to rotate about the local x axis
     * @param angleY the angle in radians to rotate about the local y axis
     * @param angleZ the angle in radians to rotate about the local z axis
     * @return this
     *
     * @see #rotateLocal(float, float, float, Quaternionfc)
     */
    public abstract Quaternionfc rotateLocal(float angleX, float angleY, float angleZ);

    /**
     * Apply a rotation to <code>this</code> quaternion rotating the given radians about the x axis.
     * <p>
     * If <code>Q</code> is <code>this</code> quaternion and <code>R</code> the quaternion representing the specified
     * rotation, then the new quaternion will be <code>Q * R</code>. So when transforming a vector <code>v</code> with
     * the new quaternion by using <code>Q * R * v</code>, the rotation added by this method will be applied first!
     *
     * @param angle the angle in radians to rotate about the x axis
     * @return this
     *
     * @see #rotate(float, float, float, Quaternionfc)
     */
    public abstract Quaternionfc rotateX(float angle);

    /**
     * Apply a rotation to <code>this</code> quaternion rotating the given radians about the y axis.
     * <p>
     * If <code>Q</code> is <code>this</code> quaternion and <code>R</code> the quaternion representing the specified
     * rotation, then the new quaternion will be <code>Q * R</code>. So when transforming a vector <code>v</code> with
     * the new quaternion by using <code>Q * R * v</code>, the rotation added by this method will be applied first!
     *
     * @param angle the angle in radians to rotate about the y axis
     * @return this
     *
     * @see #rotate(float, float, float, Quaternionfc)
     */
    public abstract Quaternionfc rotateY(float angle);

    /**
     * Apply a rotation to <code>this</code> quaternion rotating the given radians about the z axis.
     * <p>
     * If <code>Q</code> is <code>this</code> quaternion and <code>R</code> the quaternion representing the specified
     * rotation, then the new quaternion will be <code>Q * R</code>. So when transforming a vector <code>v</code> with
     * the new quaternion by using <code>Q * R * v</code>, the rotation added by this method will be applied first!
     *
     * @param angle the angle in radians to rotate about the z axis
     * @return this
     *
     * @see #rotate(float, float, float, Quaternionfc)
     */
    public abstract Quaternionfc rotateZ(float angle);

    /**
     * Apply a rotation to <code>this</code> quaternion rotating the given radians about the local x axis.
     * <p>
     * If <code>Q</code> is <code>this</code> quaternion and <code>R</code> the quaternion representing the specified
     * rotation, then the new quaternion will be <code>R * Q</code>. So when transforming a vector <code>v</code> with
     * the new quaternion by using <code>R * Q * v</code>, the rotation represented by <code>this</code> will be applied
     * first!
     *
     * @param angle the angle in radians to rotate about the local x axis
     * @return this
     */
    public abstract Quaternionfc rotateLocalX(float angle);

    /**
     * Apply a rotation to <code>this</code> quaternion rotating the given radians about the local y axis.
     * <p>
     * If <code>Q</code> is <code>this</code> quaternion and <code>R</code> the quaternion representing the specified
     * rotation, then the new quaternion will be <code>R * Q</code>. So when transforming a vector <code>v</code> with
     * the new quaternion by using <code>R * Q * v</code>, the rotation represented by <code>this</code> will be applied
     * first!
     *
     * @param angle the angle in radians to rotate about the local y axis
     * @return this
     */
    public abstract Quaternionfc rotateLocalY(float angle);

    /**
     * Apply a rotation to <code>this</code> quaternion rotating the given radians about the local z axis.
     * <p>
     * If <code>Q</code> is <code>this</code> quaternion and <code>R</code> the quaternion representing the specified
     * rotation, then the new quaternion will be <code>R * Q</code>. So when transforming a vector <code>v</code> with
     * the new quaternion by using <code>R * Q * v</code>, the rotation represented by <code>this</code> will be applied
     * first!
     *
     * @param angle the angle in radians to rotate about the local z axis
     * @return this
     */
    public abstract Quaternionfc rotateLocalZ(float angle);

    /**
     * Apply a rotation to <code>this</code> quaternion rotating the given radians about the specified axis.
     * <p>
     * If <code>Q</code> is <code>this</code> quaternion and <code>R</code> the quaternion representing the specified
     * rotation, then the new quaternion will be <code>Q * R</code>. So when transforming a vector <code>v</code> with
     * the new quaternion by using <code>Q * R * v</code>, the rotation added by this method will be applied first!
     *
     * @param angle the angle in radians to rotate about the specified axis
     * @param axis  the rotation axis
     * @return this
     *
     * @see #rotateAxis(float, float, float, float, Quaternionfc)
     */
    public abstract Quaternionfc rotateAxis(float angle, IVector3f axis);

    /**
     * Apply a rotation to <code>this</code> quaternion rotating the given radians about the specified axis.
     * <p>
     * If <code>Q</code> is <code>this</code> quaternion and <code>R</code> the quaternion representing the specified
     * rotation, then the new quaternion will be <code>Q * R</code>. So when transforming a vector <code>v</code> with
     * the new quaternion by using <code>Q * R * v</code>, the rotation added by this method will be applied first!
     *
     * @param angle the angle in radians to rotate about the specified axis
     * @param axisX the x coordinate of the rotation axis
     * @param axisY the y coordinate of the rotation axis
     * @param axisZ the z coordinate of the rotation axis
     * @return this
     *
     * @see #rotateAxis(float, float, float, float, Quaternionfc)
     */
    public abstract Quaternionfc rotateAxis(float angle, float axisX, float axisY, float axisZ);

    /**
     * Return a string representation of this quaternion.
     * <p>
     * This method creates a new {@link DecimalFormat} on every invocation with the format string "<tt>0.000E0;-</tt>".
     *
     * @return the string representation
     */
    public abstract String toString();

    /**
     * Return a string representation of this quaternion by formatting the components with the given {@link
     * NumberFormat}.
     *
     * @param formatter the {@link NumberFormat} used to format the quaternion components with
     * @return the string representation
     */
    public abstract String toString(NumberFormat formatter);

    public abstract int hashCode();

    public abstract boolean equals(Object obj);

    /**
     * Compute the difference between <code>this</code> and the <code>other</code> quaternion and store the result in
     * <code>this</code>.
     * <p>
     * The difference is the rotation that has to be applied to get from <code>this</code> rotation to
     * <code>other</code>. If <tt>T</tt> is <code>this</code>, <tt>Q</tt> is <code>other</code> and <tt>D</tt> is the
     * computed difference, then the following equation holds:
     * <p>
     * <tt>T * D = Q</tt>
     * <p>
     * It is defined as: <tt>D = T^-1 * Q</tt>, where <tt>T^-1</tt> denotes the {@link #invert() inverse} of
     * <tt>T</tt>.
     *
     * @param other the other quaternion
     * @return this
     */
    public abstract Quaternionfc difference(Quaternionfc other);
}
