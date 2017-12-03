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
package org.joml.api.matrix;

import org.joml.api.AxisAngle4dc;
import org.joml.api.AxisAngle4fc;
import org.joml.api.quaternion.IQuaterniond;
import org.joml.api.quaternion.IQuaternionf;
import org.joml.api.vector.IVector3f;
import org.joml.api.vector.Vector3fc;

import java.io.Externalizable;
import java.text.DecimalFormat;
import java.text.NumberFormat;

//#ifdef __HAS_NIO__
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
//#endif

/**
 * Contains the definition of a 3x3 matrix of floats, and associated functions to transform it. The matrix is
 * column-major to match OpenGL's interpretation, and it looks like this:
 * <p>
 * m00  m10  m20<br> m01  m11  m21<br> m02  m12  m22<br>
 *
 * @author Richard Greenlees
 * @author Kai Burjack
 */
public abstract class Matrix3fc implements Externalizable, IMatrix3f {

    /**
     * Set the value of the matrix element at column 0 and row 0
     *
     * @param m00 the new value
     * @return the value of the matrix element
     */
    public abstract Matrix3fc m00(float m00);

    /**
     * Set the value of the matrix element at column 0 and row 1
     *
     * @param m01 the new value
     * @return the value of the matrix element
     */
    public abstract Matrix3fc m01(float m01);

    /**
     * Set the value of the matrix element at column 0 and row 2
     *
     * @param m02 the new value
     * @return the value of the matrix element
     */
    public abstract Matrix3fc m02(float m02);

    /**
     * Set the value of the matrix element at column 1 and row 0
     *
     * @param m10 the new value
     * @return the value of the matrix element
     */
    public abstract Matrix3fc m10(float m10);

    /**
     * Set the value of the matrix element at column 1 and row 1
     *
     * @param m11 the new value
     * @return the value of the matrix element
     */
    public abstract Matrix3fc m11(float m11);

    /**
     * Set the value of the matrix element at column 1 and row 2
     *
     * @param m12 the new value
     * @return the value of the matrix element
     */
    public abstract Matrix3fc m12(float m12);

    /**
     * Set the value of the matrix element at column 2 and row 0
     *
     * @param m20 the new value
     * @return the value of the matrix element
     */
    public abstract Matrix3fc m20(float m20);

    /**
     * Set the value of the matrix element at column 2 and row 1
     *
     * @param m21 the new value
     * @return the value of the matrix element
     */
    public abstract Matrix3fc m21(float m21);

    /**
     * Set the value of the matrix element at column 2 and row 2
     *
     * @param m22 the new value
     * @return the value of the matrix element
     */
    public abstract Matrix3fc m22(float m22);

    /**
     * Set the elements of this matrix to the ones in <code>m</code>.
     *
     * @param m the matrix to copy the elements from
     * @return this
     */
    public abstract Matrix3fc set(IMatrix3f m);

    /**
     * Set the elements of this matrix to the upper left 3x3 of the given {@link IMatrix4f}.
     *
     * @param mat the {@link IMatrix4f} to copy the values from
     * @return this
     */
    public abstract Matrix3fc set(IMatrix4f mat);

    /**
     * Set this matrix to be equivalent to the rotation specified by the given {@link AxisAngle4fc}.
     *
     * @param axisAngle the {@link AxisAngle4fc}
     * @return this
     */
    public abstract Matrix3fc set(AxisAngle4fc axisAngle);

    /**
     * Set this matrix to be equivalent to the rotation specified by the given {@link AxisAngle4dc}.
     *
     * @param axisAngle the {@link AxisAngle4dc}
     * @return this
     */
    public abstract Matrix3fc set(AxisAngle4dc axisAngle);

    /**
     * Set this matrix to be equivalent to the rotation - and possibly scaling - specified by the given {@link
     * IQuaternionf}.
     * <p>
     * This method is equivalent to calling: <tt>rotation(q)</tt>
     * <p>
     * Reference: <a href="http://www.euclideanspace.com/maths/geometry/rotations/conversions/quaternionToMatrix/">http://www.euclideanspace.com/</a>
     *
     * @param q the {@link IQuaternionf}
     * @return this
     *
     * @see #rotation(IQuaternionf)
     */
    public abstract Matrix3fc set(IQuaternionf q);

    /**
     * Set this matrix to a rotation - and possibly scaling - equivalent to the given quaternion.
     * <p>
     * Reference: <a href="http://www.euclideanspace.com/maths/geometry/rotations/conversions/quaternionToMatrix/">http://www.euclideanspace.com/</a>
     *
     * @param q the quaternion
     * @return this
     */
    public abstract Matrix3fc set(IQuaterniond q);

    /**
     * Multiply this matrix by the supplied <code>right</code> matrix.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the <code>right</code> matrix, then the new
     * matrix will be <code>M * R</code>. So when transforming a vector <code>v</code> with the new matrix by using
     * <code>M * R * v</code>, the transformation of the right matrix will be applied first!
     *
     * @param right the right operand of the matrix multiplication
     * @return this
     */
    public abstract Matrix3fc mul(IMatrix3f right);

    /**
     * Pre-multiply this matrix by the supplied <code>left</code> matrix and store the result in <code>this</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>L</code> the <code>left</code> matrix, then the new
     * matrix will be <code>L * M</code>. So when transforming a vector <code>v</code> with the new matrix by using
     * <code>L * M * v</code>, the transformation of <code>this</code> matrix will be applied first!
     *
     * @param left the left operand of the matrix multiplication
     * @return this
     */
    public abstract Matrix3fc mulLocal(IMatrix3f left);

    /**
     * Set the values within this matrix to the supplied float values. The result looks like this:
     * <p>
     * m00, m10, m20<br> m01, m11, m21<br> m02, m12, m22<br>
     *
     * @param m00 the new value of m00
     * @param m01 the new value of m01
     * @param m02 the new value of m02
     * @param m10 the new value of m10
     * @param m11 the new value of m11
     * @param m12 the new value of m12
     * @param m20 the new value of m20
     * @param m21 the new value of m21
     * @param m22 the new value of m22
     * @return this
     */
    public abstract Matrix3fc set(float m00, float m01, float m02,
                                  float m10, float m11, float m12,
                                  float m20, float m21, float m22);

    /**
     * Set the values in this matrix based on the supplied float array. The result looks like this:
     * <p>
     * 0, 3, 6<br> 1, 4, 7<br> 2, 5, 8<br>
     * <p>
     * This method only uses the first 9 values, all others are ignored.
     *
     * @param m the array to read the matrix values from
     * @return this
     */
    public abstract Matrix3fc set(float m[]);

    /**
     * Set the three columns of this matrix to the supplied vectors, respectively.
     *
     * @param col0 the first column
     * @param col1 the second column
     * @param col2 the third column
     * @return this
     */
    public abstract Matrix3fc set(IVector3f col0, IVector3f col1, IVector3f col2);

    /**
     * Invert this matrix.
     *
     * @return this
     */
    public abstract Matrix3fc invert();

    /**
     * Transpose this matrix.
     *
     * @return this
     */
    public abstract Matrix3fc transpose();

    /**
     * Return a string representation of this matrix.
     * <p>
     * This method creates a new {@link DecimalFormat} on every invocation with the format string "<tt>0.000E0;-</tt>".
     *
     * @return the string representation
     */
    public abstract String toString();

    /**
     * Return a string representation of this matrix by formatting the matrix elements with the given {@link
     * NumberFormat}.
     *
     * @param formatter the {@link NumberFormat} used to format the matrix values with
     * @return the string representation
     */
    public abstract String toString(NumberFormat formatter);

    //#ifdef __HAS_NIO__

    /**
     * Set the values of this matrix by reading 9 float values from the given {@link FloatBuffer} in column-major order,
     * starting at its current position.
     * <p>
     * The FloatBuffer is expected to contain the values in column-major order.
     * <p>
     * The position of the FloatBuffer will not be changed by this method.
     *
     * @param buffer the FloatBuffer to read the matrix values from in column-major order
     * @return this
     */
    public abstract Matrix3fc set(FloatBuffer buffer);

    /**
     * Set the values of this matrix by reading 9 float values from the given {@link ByteBuffer} in column-major order,
     * starting at its current position.
     * <p>
     * The ByteBuffer is expected to contain the values in column-major order.
     * <p>
     * The position of the ByteBuffer will not be changed by this method.
     *
     * @param buffer the ByteBuffer to read the matrix values from in column-major order
     * @return this
     */
    public abstract Matrix3fc set(ByteBuffer buffer);
    //#endif

    /**
     * Set all values within this matrix to zero.
     *
     * @return this
     */
    public abstract Matrix3fc zero();

    /**
     * Set this matrix to the identity.
     *
     * @return this
     */
    public abstract Matrix3fc identity();

    /**
     * Apply scaling to this matrix by scaling the base axes by the given <tt>xyz.x</tt>, <tt>xyz.y</tt> and
     * <tt>xyz.z</tt> factors, respectively.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>S</code> the scaling matrix, then the new matrix will be
     * <code>M * S</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>M * S *
     * v</code>, the scaling will be applied first!
     *
     * @param xyz the factors of the x, y and z component, respectively
     * @return this
     */
    public abstract Matrix3fc scale(IVector3f xyz);

    /**
     * Apply scaling to this matrix by scaling the base axes by the given x, y and z factors.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>S</code> the scaling matrix, then the new matrix will be
     * <code>M * S</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>M * S *
     * v</code> , the scaling will be applied first!
     *
     * @param x the factor of the x component
     * @param y the factor of the y component
     * @param z the factor of the z component
     * @return this
     */
    public abstract Matrix3fc scale(float x, float y, float z);

    /**
     * Apply scaling to this matrix by uniformly scaling all base axes by the given <code>xyz</code> factor.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>S</code> the scaling matrix, then the new matrix will be
     * <code>M * S</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>M * S *
     * v</code> , the scaling will be applied first!
     *
     * @param xyz the factor for all components
     * @return this
     *
     * @see #scale(float, float, float)
     */
    public abstract Matrix3fc scale(float xyz);

    /**
     * Pre-multiply scaling to this matrix by scaling the base axes by the given x, y and z factors.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>S</code> the scaling matrix, then the new matrix will be
     * <code>S * M</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>S * M *
     * v</code>, the scaling will be applied last!
     *
     * @param x the factor of the x component
     * @param y the factor of the y component
     * @param z the factor of the z component
     * @return this
     */
    public abstract Matrix3fc scaleLocal(float x, float y, float z);

    /**
     * Set this matrix to be a simple scale matrix, which scales all axes uniformly by the given factor.
     * <p>
     * The resulting matrix can be multiplied against another transformation matrix to obtain an additional scaling.
     * <p>
     * In order to post-multiply a scaling transformation directly to a matrix, use {@link #scale(float) scale()}
     * instead.
     *
     * @param factor the scale factor in x, y and z
     * @return this
     *
     * @see #scale(float)
     */
    public abstract Matrix3fc scaling(float factor);

    /**
     * Set this matrix to be a simple scale matrix.
     *
     * @param x the scale in x
     * @param y the scale in y
     * @param z the scale in z
     * @return this
     */
    public abstract Matrix3fc scaling(float x, float y, float z);

    /**
     * Set this matrix to be a simple scale matrix which scales the base axes by <tt>xyz.x</tt>, <tt>xyz.y</tt> and
     * <tt>xyz.z</tt> respectively.
     * <p>
     * The resulting matrix can be multiplied against another transformation matrix to obtain an additional scaling.
     * <p>
     * In order to post-multiply a scaling transformation directly to a matrix use {@link #scale(IVector3f) scale()}
     * instead.
     *
     * @param xyz the scale in x, y and z respectively
     * @return this
     *
     * @see #scale(IVector3f)
     */
    public abstract Matrix3fc scaling(IVector3f xyz);

    /**
     * Set this matrix to a rotation matrix which rotates the given radians about a given axis.
     * <p>
     * The axis described by the <code>axis</code> vector needs to be a unit vector.
     * <p>
     * When used with a right-handed coordinate system, the produced rotation will rotate a vector counter-clockwise
     * around the rotation axis, when viewing along the negative axis direction towards the origin. When used with a
     * left-handed coordinate system, the rotation is clockwise.
     * <p>
     * The resulting matrix can be multiplied against another transformation matrix to obtain an additional rotation.
     * <p>
     * In order to post-multiply a rotation transformation directly to a matrix, use {@link #rotate(float, IVector3f)
     * rotate()} instead.
     *
     * @param angle the angle in radians
     * @param axis  the axis to rotate about (needs to be {@link Vector3fc#normalize() normalized})
     * @return this
     *
     * @see #rotate(float, IVector3f)
     */
    public abstract Matrix3fc rotation(float angle, IVector3f axis);

    /**
     * Set this matrix to a rotation transformation using the given {@link AxisAngle4fc}.
     * <p>
     * When used with a right-handed coordinate system, the produced rotation will rotate a vector counter-clockwise
     * around the rotation axis, when viewing along the negative axis direction towards the origin. When used with a
     * left-handed coordinate system, the rotation is clockwise.
     * <p>
     * The resulting matrix can be multiplied against another transformation matrix to obtain an additional rotation.
     * <p>
     * In order to apply the rotation transformation to an existing transformation, use {@link #rotate(AxisAngle4fc)
     * rotate()} instead.
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Axis_and_angle">http://en.wikipedia.org</a>
     *
     * @param axisAngle the {@link AxisAngle4fc} (needs to be {@link AxisAngle4fc#normalize() normalized})
     * @return this
     *
     * @see #rotate(AxisAngle4fc)
     */
    public abstract Matrix3fc rotation(AxisAngle4fc axisAngle);

    /**
     * Set this matrix to a rotation matrix which rotates the given radians about a given axis.
     * <p>
     * The axis described by the three components needs to be a unit vector.
     * <p>
     * When used with a right-handed coordinate system, the produced rotation will rotate a vector counter-clockwise
     * around the rotation axis, when viewing along the negative axis direction towards the origin. When used with a
     * left-handed coordinate system, the rotation is clockwise.
     * <p>
     * The resulting matrix can be multiplied against another transformation matrix to obtain an additional rotation.
     * <p>
     * In order to apply the rotation transformation to an existing transformation, use {@link #rotate(float, float,
     * float, float) rotate()} instead.
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Rotation_matrix_from_axis_and_angle">http://en.wikipedia.org</a>
     *
     * @param angle the angle in radians
     * @param x     the x-component of the rotation axis
     * @param y     the y-component of the rotation axis
     * @param z     the z-component of the rotation axis
     * @return this
     *
     * @see #rotate(float, float, float, float)
     */
    public abstract Matrix3fc rotation(float angle, float x, float y, float z);

    /**
     * Set this matrix to a rotation transformation about the X axis.
     * <p>
     * When used with a right-handed coordinate system, the produced rotation will rotate a vector counter-clockwise
     * around the rotation axis, when viewing along the negative axis direction towards the origin. When used with a
     * left-handed coordinate system, the rotation is clockwise.
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Basic_rotations">http://en.wikipedia.org</a>
     *
     * @param ang the angle in radians
     * @return this
     */
    public abstract Matrix3fc rotationX(float ang);

    /**
     * Set this matrix to a rotation transformation about the Y axis.
     * <p>
     * When used with a right-handed coordinate system, the produced rotation will rotate a vector counter-clockwise
     * around the rotation axis, when viewing along the negative axis direction towards the origin. When used with a
     * left-handed coordinate system, the rotation is clockwise.
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Basic_rotations">http://en.wikipedia.org</a>
     *
     * @param ang the angle in radians
     * @return this
     */
    public abstract Matrix3fc rotationY(float ang);

    /**
     * Set this matrix to a rotation transformation about the Z axis.
     * <p>
     * When used with a right-handed coordinate system, the produced rotation will rotate a vector counter-clockwise
     * around the rotation axis, when viewing along the negative axis direction towards the origin. When used with a
     * left-handed coordinate system, the rotation is clockwise.
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Basic_rotations">http://en.wikipedia.org</a>
     *
     * @param ang the angle in radians
     * @return this
     */
    public abstract Matrix3fc rotationZ(float ang);

    /**
     * Set this matrix to a rotation of <code>angleX</code> radians about the X axis, followed by a rotation of
     * <code>angleY</code> radians about the Y axis and followed by a rotation of <code>angleZ</code> radians about the
     * Z axis.
     * <p>
     * When used with a right-handed coordinate system, the produced rotation will rotate a vector counter-clockwise
     * around the rotation axis, when viewing along the negative axis direction towards the origin. When used with a
     * left-handed coordinate system, the rotation is clockwise.
     * <p>
     * This method is equivalent to calling: <tt>rotationX(angleX).rotateY(angleY).rotateZ(angleZ)</tt>
     *
     * @param angleX the angle to rotate about X
     * @param angleY the angle to rotate about Y
     * @param angleZ the angle to rotate about Z
     * @return this
     */
    public abstract Matrix3fc rotationXYZ(float angleX, float angleY, float angleZ);

    /**
     * Set this matrix to a rotation of <code>angleZ</code> radians about the Z axis, followed by a rotation of
     * <code>angleY</code> radians about the Y axis and followed by a rotation of <code>angleX</code> radians about the
     * X axis.
     * <p>
     * When used with a right-handed coordinate system, the produced rotation will rotate a vector counter-clockwise
     * around the rotation axis, when viewing along the negative axis direction towards the origin. When used with a
     * left-handed coordinate system, the rotation is clockwise.
     * <p>
     * This method is equivalent to calling: <tt>rotationZ(angleZ).rotateY(angleY).rotateX(angleX)</tt>
     *
     * @param angleZ the angle to rotate about Z
     * @param angleY the angle to rotate about Y
     * @param angleX the angle to rotate about X
     * @return this
     */
    public abstract Matrix3fc rotationZYX(float angleZ, float angleY, float angleX);

    /**
     * Set this matrix to a rotation of <code>angleY</code> radians about the Y axis, followed by a rotation of
     * <code>angleX</code> radians about the X axis and followed by a rotation of <code>angleZ</code> radians about the
     * Z axis.
     * <p>
     * When used with a right-handed coordinate system, the produced rotation will rotate a vector counter-clockwise
     * around the rotation axis, when viewing along the negative axis direction towards the origin. When used with a
     * left-handed coordinate system, the rotation is clockwise.
     * <p>
     * This method is equivalent to calling: <tt>rotationY(angleY).rotateX(angleX).rotateZ(angleZ)</tt>
     *
     * @param angleY the angle to rotate about Y
     * @param angleX the angle to rotate about X
     * @param angleZ the angle to rotate about Z
     * @return this
     */
    public abstract Matrix3fc rotationYXZ(float angleY, float angleX, float angleZ);

    /**
     * Set this matrix to the rotation - and possibly scaling - transformation of the given {@link IQuaternionf}.
     * <p>
     * When used with a right-handed coordinate system, the produced rotation will rotate a vector counter-clockwise
     * around the rotation axis, when viewing along the negative axis direction towards the origin. When used with a
     * left-handed coordinate system, the rotation is clockwise.
     * <p>
     * The resulting matrix can be multiplied against another transformation matrix to obtain an additional rotation.
     * <p>
     * In order to apply the rotation transformation to an existing transformation, use {@link #rotate(IQuaternionf)
     * rotate()} instead.
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Quaternion">http://en.wikipedia.org</a>
     *
     * @param quat the {@link IQuaternionf}
     * @return this
     *
     * @see #rotate(IQuaternionf)
     */
    public abstract Matrix3fc rotation(IQuaternionf quat);

    /**
     * Apply rotation about the X axis to this matrix by rotating the given amount of radians.
     * <p>
     * When used with a right-handed coordinate system, the produced rotation will rotate a vector counter-clockwise
     * around the rotation axis, when viewing along the negative axis direction towards the origin. When used with a
     * left-handed coordinate system, the rotation is clockwise.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the rotation matrix, then the new matrix will be
     * <code>M * R</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>M * R *
     * v</code> , the rotation will be applied first!
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Basic_rotations">http://en.wikipedia.org</a>
     *
     * @param ang the angle in radians
     * @return this
     */
    public abstract Matrix3fc rotateX(float ang);

    /**
     * Apply rotation about the Y axis to this matrix by rotating the given amount of radians.
     * <p>
     * When used with a right-handed coordinate system, the produced rotation will rotate a vector counter-clockwise
     * around the rotation axis, when viewing along the negative axis direction towards the origin. When used with a
     * left-handed coordinate system, the rotation is clockwise.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the rotation matrix, then the new matrix will be
     * <code>M * R</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>M * R *
     * v</code> , the rotation will be applied first!
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Basic_rotations">http://en.wikipedia.org</a>
     *
     * @param ang the angle in radians
     * @return this
     */
    public abstract Matrix3fc rotateY(float ang);

    /**
     * Apply rotation about the Z axis to this matrix by rotating the given amount of radians.
     * <p>
     * When used with a right-handed coordinate system, the produced rotation will rotate a vector counter-clockwise
     * around the rotation axis, when viewing along the negative axis direction towards the origin. When used with a
     * left-handed coordinate system, the rotation is clockwise.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the rotation matrix, then the new matrix will be
     * <code>M * R</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>M * R *
     * v</code> , the rotation will be applied first!
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Basic_rotations">http://en.wikipedia.org</a>
     *
     * @param ang the angle in radians
     * @return this
     */
    public abstract Matrix3fc rotateZ(float ang);

    /**
     * Apply rotation of <code>angles.x</code> radians about the X axis, followed by a rotation of <code>angles.y</code>
     * radians about the Y axis and followed by a rotation of <code>angles.z</code> radians about the Z axis.
     * <p>
     * When used with a right-handed coordinate system, the produced rotation will rotate a vector counter-clockwise
     * around the rotation axis, when viewing along the negative axis direction towards the origin. When used with a
     * left-handed coordinate system, the rotation is clockwise.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the rotation matrix, then the new matrix will be
     * <code>M * R</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>M * R *
     * v</code>, the rotation will be applied first!
     * <p>
     * This method is equivalent to calling: <tt>rotateX(angles.x).rotateY(angles.y).rotateZ(angles.z)</tt>
     *
     * @param angles the Euler angles
     * @return this
     */
    public abstract Matrix3fc rotateXYZ(Vector3fc angles);

    /**
     * Apply rotation of <code>angleX</code> radians about the X axis, followed by a rotation of <code>angleY</code>
     * radians about the Y axis and followed by a rotation of <code>angleZ</code> radians about the Z axis.
     * <p>
     * When used with a right-handed coordinate system, the produced rotation will rotate a vector counter-clockwise
     * around the rotation axis, when viewing along the negative axis direction towards the origin. When used with a
     * left-handed coordinate system, the rotation is clockwise.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the rotation matrix, then the new matrix will be
     * <code>M * R</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>M * R *
     * v</code>, the rotation will be applied first!
     * <p>
     * This method is equivalent to calling: <tt>rotateX(angleX).rotateY(angleY).rotateZ(angleZ)</tt>
     *
     * @param angleX the angle to rotate about X
     * @param angleY the angle to rotate about Y
     * @param angleZ the angle to rotate about Z
     * @return this
     */
    public abstract Matrix3fc rotateXYZ(float angleX, float angleY, float angleZ);

    /**
     * Apply rotation of <code>angles.z</code> radians about the Z axis, followed by a rotation of <code>angles.y</code>
     * radians about the Y axis and followed by a rotation of <code>angles.x</code> radians about the X axis.
     * <p>
     * When used with a right-handed coordinate system, the produced rotation will rotate a vector counter-clockwise
     * around the rotation axis, when viewing along the negative axis direction towards the origin. When used with a
     * left-handed coordinate system, the rotation is clockwise.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the rotation matrix, then the new matrix will be
     * <code>M * R</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>M * R *
     * v</code>, the rotation will be applied first!
     * <p>
     * This method is equivalent to calling: <tt>rotateZ(angles.z).rotateY(angles.y).rotateX(angles.x)</tt>
     *
     * @param angles the Euler angles
     * @return this
     */
    public abstract Matrix3fc rotateZYX(Vector3fc angles);

    /**
     * Apply rotation of <code>angleZ</code> radians about the Z axis, followed by a rotation of <code>angleY</code>
     * radians about the Y axis and followed by a rotation of <code>angleX</code> radians about the X axis.
     * <p>
     * When used with a right-handed coordinate system, the produced rotation will rotate a vector counter-clockwise
     * around the rotation axis, when viewing along the negative axis direction towards the origin. When used with a
     * left-handed coordinate system, the rotation is clockwise.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the rotation matrix, then the new matrix will be
     * <code>M * R</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>M * R *
     * v</code>, the rotation will be applied first!
     * <p>
     * This method is equivalent to calling: <tt>rotateZ(angleZ).rotateY(angleY).rotateX(angleX)</tt>
     *
     * @param angleZ the angle to rotate about Z
     * @param angleY the angle to rotate about Y
     * @param angleX the angle to rotate about X
     * @return this
     */
    public abstract Matrix3fc rotateZYX(float angleZ, float angleY, float angleX);

    /**
     * Apply rotation of <code>angles.y</code> radians about the Y axis, followed by a rotation of <code>angles.x</code>
     * radians about the X axis and followed by a rotation of <code>angles.z</code> radians about the Z axis.
     * <p>
     * When used with a right-handed coordinate system, the produced rotation will rotate a vector counter-clockwise
     * around the rotation axis, when viewing along the negative axis direction towards the origin. When used with a
     * left-handed coordinate system, the rotation is clockwise.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the rotation matrix, then the new matrix will be
     * <code>M * R</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>M * R *
     * v</code>, the rotation will be applied first!
     * <p>
     * This method is equivalent to calling: <tt>rotateY(angles.y).rotateX(angles.x).rotateZ(angles.z)</tt>
     *
     * @param angles the Euler angles
     * @return this
     */
    public abstract Matrix3fc rotateYXZ(Vector3fc angles);

    /**
     * Apply rotation of <code>angleY</code> radians about the Y axis, followed by a rotation of <code>angleX</code>
     * radians about the X axis and followed by a rotation of <code>angleZ</code> radians about the Z axis.
     * <p>
     * When used with a right-handed coordinate system, the produced rotation will rotate a vector counter-clockwise
     * around the rotation axis, when viewing along the negative axis direction towards the origin. When used with a
     * left-handed coordinate system, the rotation is clockwise.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the rotation matrix, then the new matrix will be
     * <code>M * R</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>M * R *
     * v</code>, the rotation will be applied first!
     * <p>
     * This method is equivalent to calling: <tt>rotateY(angleY).rotateX(angleX).rotateZ(angleZ)</tt>
     *
     * @param angleY the angle to rotate about Y
     * @param angleX the angle to rotate about X
     * @param angleZ the angle to rotate about Z
     * @return this
     */
    public abstract Matrix3fc rotateYXZ(float angleY, float angleX, float angleZ);

    /**
     * Apply rotation to this matrix by rotating the given amount of radians about the given axis specified as x, y and
     * z components.
     * <p>
     * The axis described by the three components needs to be a unit vector.
     * <p>
     * When used with a right-handed coordinate system, the produced rotation will rotate a vector counter-clockwise
     * around the rotation axis, when viewing along the negative axis direction towards the origin. When used with a
     * left-handed coordinate system, the rotation is clockwise.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the rotation matrix, then the new matrix will be
     * <code>M * R</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>M * R *
     * v</code> , the rotation will be applied first!
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Rotation_matrix_from_axis_and_angle">http://en.wikipedia.org</a>
     *
     * @param ang the angle in radians
     * @param x   the x component of the axis
     * @param y   the y component of the axis
     * @param z   the z component of the axis
     * @return this
     */
    public abstract Matrix3fc rotate(float ang, float x, float y, float z);

    /**
     * Pre-multiply a rotation to this matrix by rotating the given amount of radians about the specified <tt>(x, y,
     * z)</tt> axis.
     * <p>
     * The axis described by the three components needs to be a unit vector.
     * <p>
     * When used with a right-handed coordinate system, the produced rotation will rotate a vector counter-clockwise
     * around the rotation axis, when viewing along the negative axis direction towards the origin. When used with a
     * left-handed coordinate system, the rotation is clockwise.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the rotation matrix, then the new matrix will be
     * <code>R * M</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>R * M *
     * v</code>, the rotation will be applied last!
     * <p>
     * In order to set the matrix to a rotation matrix without pre-multiplying the rotation transformation, use {@link
     * #rotation(float, float, float, float) rotation()}.
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Rotation_matrix_from_axis_and_angle">http://en.wikipedia.org</a>
     *
     * @param ang the angle in radians
     * @param x   the x component of the axis
     * @param y   the y component of the axis
     * @param z   the z component of the axis
     * @return this
     *
     * @see #rotation(float, float, float, float)
     */
    public abstract Matrix3fc rotateLocal(float ang, float x, float y, float z);

    /**
     * Pre-multiply a rotation to this matrix by rotating the given amount of radians about the X axis.
     * <p>
     * When used with a right-handed coordinate system, the produced rotation will rotate a vector counter-clockwise
     * around the rotation axis, when viewing along the negative axis direction towards the origin. When used with a
     * left-handed coordinate system, the rotation is clockwise.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the rotation matrix, then the new matrix will be
     * <code>R * M</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>R * M *
     * v</code>, the rotation will be applied last!
     * <p>
     * In order to set the matrix to a rotation matrix without pre-multiplying the rotation transformation, use {@link
     * #rotationX(float) rotationX()}.
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Rotation_matrix_from_axis_and_angle">http://en.wikipedia.org</a>
     *
     * @param ang the angle in radians to rotate about the X axis
     * @return this
     *
     * @see #rotationX(float)
     */
    public abstract Matrix3fc rotateLocalX(float ang);

    /**
     * Pre-multiply a rotation to this matrix by rotating the given amount of radians about the Y axis.
     * <p>
     * When used with a right-handed coordinate system, the produced rotation will rotate a vector counter-clockwise
     * around the rotation axis, when viewing along the negative axis direction towards the origin. When used with a
     * left-handed coordinate system, the rotation is clockwise.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the rotation matrix, then the new matrix will be
     * <code>R * M</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>R * M *
     * v</code>, the rotation will be applied last!
     * <p>
     * In order to set the matrix to a rotation matrix without pre-multiplying the rotation transformation, use {@link
     * #rotationY(float) rotationY()}.
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Rotation_matrix_from_axis_and_angle">http://en.wikipedia.org</a>
     *
     * @param ang the angle in radians to rotate about the Y axis
     * @return this
     *
     * @see #rotationY(float)
     */
    public abstract Matrix3fc rotateLocalY(float ang);

    /**
     * Pre-multiply a rotation to this matrix by rotating the given amount of radians about the Z axis.
     * <p>
     * When used with a right-handed coordinate system, the produced rotation will rotate a vector counter-clockwise
     * around the rotation axis, when viewing along the negative axis direction towards the origin. When used with a
     * left-handed coordinate system, the rotation is clockwise.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the rotation matrix, then the new matrix will be
     * <code>R * M</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>R * M *
     * v</code>, the rotation will be applied last!
     * <p>
     * In order to set the matrix to a rotation matrix without pre-multiplying the rotation transformation, use {@link
     * #rotationZ(float) rotationY()}.
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Rotation_matrix_from_axis_and_angle">http://en.wikipedia.org</a>
     *
     * @param ang the angle in radians to rotate about the Z axis
     * @return this
     *
     * @see #rotationY(float)
     */
    public abstract Matrix3fc rotateLocalZ(float ang);

    /**
     * Apply the rotation - and possibly scaling - transformation of the given {@link IQuaternionf} to this matrix.
     * <p>
     * When used with a right-handed coordinate system, the produced rotation will rotate a vector counter-clockwise
     * around the rotation axis, when viewing along the negative axis direction towards the origin. When used with a
     * left-handed coordinate system, the rotation is clockwise.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>Q</code> the rotation matrix obtained from the given
     * quaternion, then the new matrix will be <code>M * Q</code>. So when transforming a vector <code>v</code> with the
     * new matrix by using <code>M * Q * v</code>, the quaternion rotation will be applied first!
     * <p>
     * In order to set the matrix to a rotation transformation without post-multiplying, use {@link
     * #rotation(IQuaternionf)}.
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Quaternion">http://en.wikipedia.org</a>
     *
     * @param quat the {@link IQuaternionf}
     * @return this
     *
     * @see #rotation(IQuaternionf)
     */
    public abstract Matrix3fc rotate(IQuaternionf quat);

    /**
     * Pre-multiply the rotation - and possibly scaling - transformation of the given {@link IQuaternionf} to this
     * matrix.
     * <p>
     * When used with a right-handed coordinate system, the produced rotation will rotate a vector counter-clockwise
     * around the rotation axis, when viewing along the negative axis direction towards the origin. When used with a
     * left-handed coordinate system, the rotation is clockwise.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>Q</code> the rotation matrix obtained from the given
     * quaternion, then the new matrix will be <code>Q * M</code>. So when transforming a vector <code>v</code> with the
     * new matrix by using <code>Q * M * v</code>, the quaternion rotation will be applied last!
     * <p>
     * In order to set the matrix to a rotation transformation without pre-multiplying, use {@link
     * #rotation(IQuaternionf)}.
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Quaternion">http://en.wikipedia.org</a>
     *
     * @param quat the {@link IQuaternionf}
     * @return this
     *
     * @see #rotation(IQuaternionf)
     */
    public abstract Matrix3fc rotateLocal(IQuaternionf quat);

    /**
     * Apply a rotation transformation, rotating about the given {@link AxisAngle4fc}, to this matrix.
     * <p>
     * When used with a right-handed coordinate system, the produced rotation will rotate a vector counter-clockwise
     * around the rotation axis, when viewing along the negative axis direction towards the origin. When used with a
     * left-handed coordinate system, the rotation is clockwise.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>A</code> the rotation matrix obtained from the given
     * {@link AxisAngle4fc}, then the new matrix will be <code>M * A</code>. So when transforming a vector
     * <code>v</code> with the new matrix by using <code>M * A * v</code>, the {@link AxisAngle4fc} rotation will be
     * applied first!
     * <p>
     * In order to set the matrix to a rotation transformation without post-multiplying, use {@link
     * #rotation(AxisAngle4fc)}.
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Axis_and_angle">http://en.wikipedia.org</a>
     *
     * @param axisAngle the {@link AxisAngle4fc} (needs to be {@link AxisAngle4fc#normalize() normalized})
     * @return this
     *
     * @see #rotate(float, float, float, float)
     * @see #rotation(AxisAngle4fc)
     */
    public abstract Matrix3fc rotate(AxisAngle4fc axisAngle);

    /**
     * Apply a rotation transformation, rotating the given radians about the specified axis, to this matrix.
     * <p>
     * When used with a right-handed coordinate system, the produced rotation will rotate a vector counter-clockwise
     * around the rotation axis, when viewing along the negative axis direction towards the origin. When used with a
     * left-handed coordinate system, the rotation is clockwise.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>A</code> the rotation matrix obtained from the given
     * angle and axis, then the new matrix will be <code>M * A</code>. So when transforming a vector <code>v</code> with
     * the new matrix by using <code>M * A * v</code>, the axis-angle rotation will be applied first!
     * <p>
     * In order to set the matrix to a rotation transformation without post-multiplying, use {@link #rotation(float,
     * IVector3f)}.
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Axis_and_angle">http://en.wikipedia.org</a>
     *
     * @param angle the angle in radians
     * @param axis  the rotation axis (needs to be {@link Vector3fc#normalize() normalized})
     * @return this
     *
     * @see #rotate(float, float, float, float)
     * @see #rotation(float, IVector3f)
     */
    public abstract Matrix3fc rotate(float angle, IVector3f axis);

    /**
     * Apply a rotation transformation to this matrix to make <code>-z</code> point along <code>dir</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>L</code> the lookalong rotation matrix, then the new
     * matrix will be <code>M * L</code>. So when transforming a vector <code>v</code> with the new matrix by using
     * <code>M * L * v</code>, the lookalong rotation transformation will be applied first!
     * <p>
     * In order to set the matrix to a lookalong transformation without post-multiplying it, use {@link
     * #setLookAlong(IVector3f, IVector3f) setLookAlong()}.
     *
     * @param dir the direction in space to look along
     * @param up  the direction of 'up'
     * @return this
     *
     * @see #lookAlong(float, float, float, float, float, float)
     * @see #setLookAlong(IVector3f, IVector3f)
     */
    public abstract Matrix3fc lookAlong(IVector3f dir, IVector3f up);

    /**
     * Apply a rotation transformation to this matrix to make <code>-z</code> point along <code>dir</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>L</code> the lookalong rotation matrix, then the new
     * matrix will be <code>M * L</code>. So when transforming a vector <code>v</code> with the new matrix by using
     * <code>M * L * v</code>, the lookalong rotation transformation will be applied first!
     * <p>
     * In order to set the matrix to a lookalong transformation without post-multiplying it, use {@link
     * #setLookAlong(float, float, float, float, float, float) setLookAlong()}
     *
     * @param dirX the x-coordinate of the direction to look along
     * @param dirY the y-coordinate of the direction to look along
     * @param dirZ the z-coordinate of the direction to look along
     * @param upX  the x-coordinate of the up vector
     * @param upY  the y-coordinate of the up vector
     * @param upZ  the z-coordinate of the up vector
     * @return this
     *
     * @see #setLookAlong(float, float, float, float, float, float)
     */
    public abstract Matrix3fc lookAlong(float dirX, float dirY, float dirZ,
                                        float upX, float upY, float upZ);

    /**
     * Set this matrix to a rotation transformation to make <code>-z</code> point along <code>dir</code>.
     * <p>
     * In order to apply the lookalong transformation to any previous existing transformation, use {@link
     * #lookAlong(IVector3f, IVector3f)}.
     *
     * @param dir the direction in space to look along
     * @param up  the direction of 'up'
     * @return this
     *
     * @see #setLookAlong(IVector3f, IVector3f)
     * @see #lookAlong(IVector3f, IVector3f)
     */
    public abstract Matrix3fc setLookAlong(IVector3f dir, IVector3f up);

    /**
     * Set this matrix to a rotation transformation to make <code>-z</code> point along <code>dir</code>.
     * <p>
     * In order to apply the lookalong transformation to any previous existing transformation, use {@link
     * #lookAlong(float, float, float, float, float, float) lookAlong()}
     *
     * @param dirX the x-coordinate of the direction to look along
     * @param dirY the y-coordinate of the direction to look along
     * @param dirZ the z-coordinate of the direction to look along
     * @param upX  the x-coordinate of the up vector
     * @param upY  the y-coordinate of the up vector
     * @param upZ  the z-coordinate of the up vector
     * @return this
     *
     * @see #setLookAlong(float, float, float, float, float, float)
     * @see #lookAlong(float, float, float, float, float, float)
     */
    public abstract Matrix3fc setLookAlong(float dirX, float dirY, float dirZ,
                                           float upX, float upY, float upZ);

    /**
     * Set the row at the given <code>row</code> index, starting with <code>0</code>.
     *
     * @param row the row index in <tt>[0..2]</tt>
     * @param src the row components to set
     * @return this
     */
    public abstract Matrix3fc setRow(int row, IVector3f src);

    /**
     * Set the row at the given <code>row</code> index, starting with <code>0</code>.
     *
     * @param row the row index in <tt>[0..2]</tt>
     * @param x   the first element in the row
     * @param y   the second element in the row
     * @param z   the third element in the row
     * @return this
     */
    public abstract Matrix3fc setRow(int row, float x, float y, float z);

    /**
     * Set the column at the given <code>column</code> index, starting with <code>0</code>.
     *
     * @param column the column index in <tt>[0..2]</tt>
     * @param src    the column components to set
     * @return this
     */
    public abstract Matrix3fc setColumn(int column, IVector3f src);

    /**
     * Set the column at the given <code>column</code> index, starting with <code>0</code>.
     *
     * @param column the column index in <tt>[0..2]</tt>
     * @param x      the first element in the column
     * @param y      the second element in the column
     * @param z      the third element in the column
     * @return this
     */
    public abstract Matrix3fc setColumn(int column, float x, float y, float z);

    /**
     * Set the matrix element at the given column and row to the specified value.
     *
     * @param column the colum index in <tt>[0..2]</tt>
     * @param row    the row index in <tt>[0..2]</tt>
     * @param value  the value
     * @return this
     */
    public abstract Matrix3fc set(int column, int row, float value);

    /**
     * Set <code>this</code> matrix to its own normal matrix.
     * <p>
     * Please note that, if <code>this</code> is an orthogonal matrix or a matrix whose columns are orthogonal vectors,
     * then this method <i>need not</i> be invoked, since in that case <code>this</code> itself is its normal matrix. In
     * this case, use {@link #set(IMatrix3f)} to set a given Matrix3fc to this matrix.
     *
     * @return this
     *
     * @see #set(IMatrix3f)
     */
    public abstract Matrix3fc normal();

    public abstract int hashCode();

    public abstract boolean equals(Object obj);

    /**
     * Exchange the values of <code>this</code> matrix with the given <code>other</code> matrix.
     *
     * @param other the other matrix to exchange the values with
     * @return this
     */
    public abstract Matrix3fc swap(Matrix3fc other);

    /**
     * Component-wise add <code>this</code> and <code>other</code>.
     *
     * @param other the other addend
     * @return this
     */
    public abstract Matrix3fc add(IMatrix3f other);

    /**
     * Component-wise subtract <code>subtrahend</code> from <code>this</code>.
     *
     * @param subtrahend the subtrahend
     * @return this
     */
    public abstract Matrix3fc sub(IMatrix3f subtrahend);


    /**
     * Component-wise multiply <code>this</code> by <code>other</code>.
     *
     * @param other the other matrix
     * @return this
     */
    public abstract Matrix3fc mulComponentWise(IMatrix3f other);


    /**
     * Set this matrix to a skew-symmetric matrix using the following layout:
     * <pre>
     *  0,  a, -b
     * -a,  0,  c
     *  b, -c,  0
     * </pre>
     * <p>
     * Reference: <a href="https://en.wikipedia.org/wiki/Skew-symmetric_matrix">https://en.wikipedia.org</a>
     *
     * @param a the value used for the matrix elements m01 and m10
     * @param b the value used for the matrix elements m02 and m20
     * @param c the value used for the matrix elements m12 and m21
     * @return this
     */
    public abstract Matrix3fc setSkewSymmetric(float a, float b, float c);

    /**
     * Linearly interpolate <code>this</code> and <code>other</code> using the given interpolation factor <code>t</code>
     * and store the result in <code>this</code>.
     * <p>
     * If <code>t</code> is <tt>0.0</tt> then the result is <code>this</code>. If the interpolation factor is
     * <code>1.0</code> then the result is <code>other</code>.
     *
     * @param other the other matrix
     * @param t     the interpolation factor between 0.0 and 1.0
     * @return this
     */
    public abstract Matrix3fc lerp(IMatrix3f other, float t);


    /**
     * Apply a model transformation to this matrix for a right-handed coordinate system, that aligns the local
     * <code>+Z</code> axis with <code>direction</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>L</code> the lookat matrix, then the new matrix will be
     * <code>M * L</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>M * L *
     * v</code>, the lookat transformation will be applied first!
     * <p>
     * In order to set the matrix to a rotation transformation without post-multiplying it, use {@link
     * #rotationTowards(IVector3f, IVector3f) rotationTowards()}.
     * <p>
     * This method is equivalent to calling: <tt>mul(new Matrix3fc().lookAlong(new Vector3fc(dir).negate(),
     * up).invert())</tt>
     *
     * @param direction the direction to orient towards
     * @param up        the up vector
     * @return this
     *
     * @see #rotateTowards(float, float, float, float, float, float)
     * @see #rotationTowards(IVector3f, IVector3f)
     */
    public abstract Matrix3fc rotateTowards(IVector3f direction, IVector3f up);

    /**
     * Apply a model transformation to this matrix for a right-handed coordinate system, that aligns the local
     * <code>+Z</code> axis with <code>direction</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>L</code> the lookat matrix, then the new matrix will be
     * <code>M * L</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>M * L *
     * v</code>, the lookat transformation will be applied first!
     * <p>
     * In order to set the matrix to a rotation transformation without post-multiplying it, use {@link
     * #rotationTowards(float, float, float, float, float, float) rotationTowards()}.
     * <p>
     * This method is equivalent to calling: <tt>mul(new Matrix3fc().lookAlong(-dirX, -dirY, -dirZ, upX, upY,
     * upZ).invert())</tt>
     *
     * @param dirX the x-coordinate of the direction to rotate towards
     * @param dirY the y-coordinate of the direction to rotate towards
     * @param dirZ the z-coordinate of the direction to rotate towards
     * @param upX  the x-coordinate of the up vector
     * @param upY  the y-coordinate of the up vector
     * @param upZ  the z-coordinate of the up vector
     * @return this
     *
     * @see #rotateTowards(IVector3f, IVector3f)
     * @see #rotationTowards(float, float, float, float, float, float)
     */
    public abstract Matrix3fc rotateTowards(float dirX, float dirY, float dirZ, float upX, float upY, float upZ);


    /**
     * Set this matrix to a model transformation for a right-handed coordinate system, that aligns the local
     * <code>-z</code> axis with <code>center - eye</code>.
     * <p>
     * In order to apply the rotation transformation to a previous existing transformation, use {@link
     * #rotateTowards(float, float, float, float, float, float) rotateTowards}.
     * <p>
     * This method is equivalent to calling: <tt>setLookAlong(new Vector3fc(dir).negate(), up).invert()</tt>
     *
     * @param dir the direction to orient the local -z axis towards
     * @param up  the up vector
     * @return this
     *
     * @see #rotationTowards(IVector3f, IVector3f)
     * @see #rotateTowards(float, float, float, float, float, float)
     */
    public abstract Matrix3fc rotationTowards(IVector3f dir, IVector3f up);

    /**
     * Set this matrix to a model transformation for a right-handed coordinate system, that aligns the local
     * <code>-z</code> axis with <code>center - eye</code>.
     * <p>
     * In order to apply the rotation transformation to a previous existing transformation, use {@link
     * #rotateTowards(float, float, float, float, float, float) rotateTowards}.
     * <p>
     * This method is equivalent to calling: <tt>setLookAlong(-dirX, -dirY, -dirZ, upX, upY, upZ).invert()</tt>
     *
     * @param dirX the x-coordinate of the direction to rotate towards
     * @param dirY the y-coordinate of the direction to rotate towards
     * @param dirZ the z-coordinate of the direction to rotate towards
     * @param upX  the x-coordinate of the up vector
     * @param upY  the y-coordinate of the up vector
     * @param upZ  the z-coordinate of the up vector
     * @return this
     *
     * @see #rotateTowards(IVector3f, IVector3f)
     * @see #rotationTowards(float, float, float, float, float, float)
     */
    public abstract Matrix3fc rotationTowards(float dirX, float dirY, float dirZ, float upX, float upY, float upZ);
}
