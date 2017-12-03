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
import org.joml.api.vector.IVector4f;
import org.joml.api.vector.Vector3fc;

import java.io.Externalizable;
import java.text.DecimalFormat;

//#ifdef __HAS_NIO__
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
//#endif

/**
 * Contains the definition of an affine 4x3 matrix (4 columns, 3 rows) of floats, and associated functions to transform
 * it. The matrix is column-major to match OpenGL's interpretation, and it looks like this:
 * <p>
 * m00  m10  m20  m30<br> m01  m11  m21  m31<br> m02  m12  m22  m32<br>
 *
 * @author Richard Greenlees
 * @author Kai Burjack
 */
public abstract class Matrix4x3fc implements Externalizable, IMatrix4x3f {

    /**
     * Set the value of the matrix element at column 0 and row 0
     *
     * @param m00 the new value
     * @return the value of the matrix element
     */
    public abstract Matrix4x3fc m00(float m00);

    /**
     * Set the value of the matrix element at column 0 and row 1
     *
     * @param m01 the new value
     * @return the value of the matrix element
     */
    public abstract Matrix4x3fc m01(float m01);

    /**
     * Set the value of the matrix element at column 0 and row 2
     *
     * @param m02 the new value
     * @return the value of the matrix element
     */
    public abstract Matrix4x3fc m02(float m02);

    /**
     * Set the value of the matrix element at column 1 and row 0
     *
     * @param m10 the new value
     * @return the value of the matrix element
     */
    public abstract Matrix4x3fc m10(float m10);

    /**
     * Set the value of the matrix element at column 1 and row 1
     *
     * @param m11 the new value
     * @return the value of the matrix element
     */
    public abstract Matrix4x3fc m11(float m11);

    /**
     * Set the value of the matrix element at column 1 and row 2
     *
     * @param m12 the new value
     * @return the value of the matrix element
     */
    public abstract Matrix4x3fc m12(float m12);

    /**
     * Set the value of the matrix element at column 2 and row 0
     *
     * @param m20 the new value
     * @return the value of the matrix element
     */
    public abstract Matrix4x3fc m20(float m20);

    /**
     * Set the value of the matrix element at column 2 and row 1
     *
     * @param m21 the new value
     * @return the value of the matrix element
     */
    public abstract Matrix4x3fc m21(float m21);

    /**
     * Set the value of the matrix element at column 2 and row 2
     *
     * @param m22 the new value
     * @return the value of the matrix element
     */
    public abstract Matrix4x3fc m22(float m22);

    /**
     * Set the value of the matrix element at column 3 and row 0
     *
     * @param m30 the new value
     * @return the value of the matrix element
     */
    public abstract Matrix4x3fc m30(float m30);

    /**
     * Set the value of the matrix element at column 3 and row 1
     *
     * @param m31 the new value
     * @return the value of the matrix element
     */
    public abstract Matrix4x3fc m31(float m31);

    /**
     * Set the value of the matrix element at column 3 and row 2
     *
     * @param m32 the new value
     * @return the value of the matrix element
     */
    public abstract Matrix4x3fc m32(float m32);

    /**
     * Sets the property of the matrix.
     *
     * @param properties the property to be set.
     * @return this
     */
    public abstract Matrix4x3fc properties(int properties);

    /**
     * Reset this matrix to the identity.
     * <p>
     * Please note that if a call to {@link #identity()} is immediately followed by a call to: {@link #translate(float,
     * float, float) translate}, {@link #rotate(float, float, float, float) rotate}, {@link #scale(float, float, float)
     * scale}, {@link #ortho(float, float, float, float, float, float) ortho}, {@link #ortho2D(float, float, float,
     * float) ortho2D}, {@link #lookAt(float, float, float, float, float, float, float, float, float) lookAt}, {@link
     * #lookAlong(float, float, float, float, float, float) lookAlong}, or any of their overloads, then the call to
     * {@link #identity()} can be omitted and the subsequent call replaced with: {@link #translation(float, float,
     * float) translation}, {@link #rotation(float, float, float, float) rotation}, {@link #scaling(float, float, float)
     * scaling}, {@link #setOrtho(float, float, float, float, float, float) setOrtho}, {@link #setOrtho2D(float, float,
     * float, float) setOrtho2D}, {@link #setLookAt(float, float, float, float, float, float, float, float, float)
     * setLookAt}, {@link #setLookAlong(float, float, float, float, float, float) setLookAlong}, or any of their
     * overloads.
     *
     * @return this
     */
    public abstract Matrix4x3fc identity();

    /**
     * Store the values of the given matrix <code>m</code> into <code>this</code> matrix.
     *
     * @param m the matrix to copy the values from
     * @return this
     *
     * @see #get(Matrix4x3fc)
     */
    public abstract Matrix4x3fc set(IMatrix4x3f m);

    /**
     * Store the values of the upper 4x3 submatrix of <code>m</code> into <code>this</code> matrix.
     *
     * @param m the matrix to copy the values from
     * @return this
     *
     * @see IMatrix4f#get4x3(Matrix4x3fc)
     */
    public abstract Matrix4x3fc set(IMatrix4f m);

    /**
     * Set the left 3x3 submatrix of this {@link Matrix4x3fc} to the given {@link IMatrix3f} and the rest to identity.
     *
     * @param mat the {@link IMatrix3f}
     * @return this
     */
    public abstract Matrix4x3fc set(IMatrix3f mat);

    /**
     * Set this matrix to be equivalent to the rotation specified by the given {@link AxisAngle4fc}.
     *
     * @param axisAngle the {@link AxisAngle4fc}
     * @return this
     */
    public abstract Matrix4x3fc set(AxisAngle4fc axisAngle);

    /**
     * Set this matrix to be equivalent to the rotation specified by the given {@link AxisAngle4dc}.
     *
     * @param axisAngle the {@link AxisAngle4dc}
     * @return this
     */
    public abstract Matrix4x3fc set(AxisAngle4dc axisAngle);

    /**
     * Set this matrix to be equivalent to the rotation - and possibly scaling - specified by the given {@link
     * IQuaternionf}.
     * <p>
     * This method is equivalent to calling: <tt>rotation(q)</tt>
     *
     * @param q the {@link IQuaternionf}
     * @return this
     *
     * @see #rotation(IQuaternionf)
     */
    public abstract Matrix4x3fc set(IQuaternionf q);

    /**
     * Set this matrix to be equivalent to the rotation - and possibly scaling - specified by the given {@link
     * IQuaterniond}.
     * <p>
     * This method is equivalent to calling: <tt>rotation(q)</tt>
     *
     * @param q the {@link IQuaterniond}
     * @return this
     */
    public abstract Matrix4x3fc set(IQuaterniond q);

    /**
     * Set the four columns of this matrix to the supplied vectors, respectively.
     *
     * @param col0 the first column
     * @param col1 the second column
     * @param col2 the third column
     * @param col3 the fourth column
     * @return this
     */
    public abstract Matrix4x3fc set(IVector3f col0, IVector3f col1, IVector3f col2, IVector3f col3);

    /**
     * Set the left 3x3 submatrix of this {@link Matrix4x3fc} to that of the given {@link IMatrix4x3f} and don't change
     * the other elements.
     *
     * @param mat the {@link IMatrix4x3f}
     * @return this
     */
    public abstract Matrix4x3fc set3x3(IMatrix4x3f mat);

    /**
     * Multiply this matrix by the supplied <code>right</code> matrix and store the result in <code>this</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the <code>right</code> matrix, then the new
     * matrix will be <code>M * R</code>. So when transforming a vector <code>v</code> with the new matrix by using
     * <code>M * R * v</code>, the transformation of the right matrix will be applied first!
     *
     * @param right the right operand of the matrix multiplication
     * @return this
     */
    public abstract Matrix4x3fc mul(IMatrix4x3f right);

    /**
     * Multiply <code>this</code> orthographic projection matrix by the supplied <code>view</code> matrix.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>V</code> the <code>view</code> matrix, then the new
     * matrix will be <code>M * V</code>. So when transforming a vector <code>v</code> with the new matrix by using
     * <code>M * V * v</code>, the transformation of the <code>view</code> matrix will be applied first!
     *
     * @param view the matrix which to multiply <code>this</code> with
     * @return dest
     */
    public abstract Matrix4x3fc mulOrtho(IMatrix4x3f view);

    /**
     * Component-wise add <code>this</code> and <code>other</code> by first multiplying each component of
     * <code>other</code> by <code>otherFactor</code> and adding that result to <code>this</code>.
     * <p>
     * The matrix <code>other</code> will not be changed.
     *
     * @param other       the other matrix
     * @param otherFactor the factor to multiply each of the other matrix's components
     * @return this
     */
    public abstract Matrix4x3fc fma(IMatrix4x3f other, float otherFactor);

    /**
     * Component-wise add <code>this</code> and <code>other</code>.
     *
     * @param other the other addend
     * @return this
     */
    public abstract Matrix4x3fc add(IMatrix4x3f other);

    /**
     * Component-wise subtract <code>subtrahend</code> from <code>this</code>.
     *
     * @param subtrahend the subtrahend
     * @return this
     */
    public abstract Matrix4x3fc sub(IMatrix4x3f subtrahend);

    /**
     * Component-wise multiply <code>this</code> by <code>other</code>.
     *
     * @param other the other matrix
     * @return this
     */
    public abstract Matrix4x3fc mulComponentWise(IMatrix4x3f other);

    /**
     * Set the values within this matrix to the supplied float values. The matrix will look like this:<br><br>
     * <p>
     * m00, m10, m20, m30<br> m01, m11, m21, m31<br> m02, m12, m22, m32<br>
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
     * @param m30 the new value of m30
     * @param m31 the new value of m31
     * @param m32 the new value of m32
     * @return this
     */
    public abstract Matrix4x3fc set(float m00, float m01, float m02,
                                    float m10, float m11, float m12,
                                    float m20, float m21, float m22,
                                    float m30, float m31, float m32);

    /**
     * Set the values in the matrix using a float array that contains the matrix elements in column-major order.
     * <p>
     * The results will look like this:<br><br>
     * <p>
     * 0, 3, 6, 9<br> 1, 4, 7, 10<br> 2, 5, 8, 11<br>
     *
     * @param m   the array to read the matrix values from
     * @param off the offset into the array
     * @return this
     *
     * @see #set(float[])
     */
    public abstract Matrix4x3fc set(float m[], int off);

    /**
     * Set the values in the matrix using a float array that contains the matrix elements in column-major order.
     * <p>
     * The results will look like this:<br><br>
     * <p>
     * 0, 3, 6, 9<br> 1, 4, 7, 10<br> 2, 5, 8, 11<br>
     *
     * @param m the array to read the matrix values from
     * @return this
     *
     * @see #set(float[], int)
     */
    public abstract Matrix4x3fc set(float m[]);

    //#ifdef __HAS_NIO__

    /**
     * Set the values of this matrix by reading 12 float values from the given {@link FloatBuffer} in column-major
     * order, starting at its current position.
     * <p>
     * The FloatBuffer is expected to contain the values in column-major order.
     * <p>
     * The position of the FloatBuffer will not be changed by this method.
     *
     * @param buffer the FloatBuffer to read the matrix values from in column-major order
     * @return this
     */
    public abstract Matrix4x3fc set(FloatBuffer buffer);

    /**
     * Set the values of this matrix by reading 12 float values from the given {@link ByteBuffer} in column-major order,
     * starting at its current position.
     * <p>
     * The ByteBuffer is expected to contain the values in column-major order.
     * <p>
     * The position of the ByteBuffer will not be changed by this method.
     *
     * @param buffer the ByteBuffer to read the matrix values from in column-major order
     * @return this
     */
    public abstract Matrix4x3fc set(ByteBuffer buffer);
    //#endif

    /**
     * Invert this matrix.
     *
     * @return this
     */
    public abstract Matrix4x3fc invert();

    /**
     * Invert <code>this</code> orthographic projection matrix.
     * <p>
     * This method can be used to quickly obtain the inverse of an orthographic projection matrix.
     *
     * @return this
     */
    public abstract Matrix4x3fc invertOrtho();

    /**
     * Invert this matrix by assuming that it has unit scaling (i.e. {@link #transformDirection(Vector3fc)
     * transformDirection} does not change the {@link Vector3fc#length() length} of the vector).
     * <p>
     * Reference: <a href="http://www.gamedev.net/topic/425118-inverse--matrix/">http://www.gamedev.net/</a>
     *
     * @return this
     */
    public abstract Matrix4x3fc invertUnitScale();

    /**
     * Transpose only the left 3x3 submatrix of this matrix and set the rest of the matrix elements to identity.
     *
     * @return this
     */
    public abstract Matrix4x3fc transpose3x3();

    /**
     * Set this matrix to be a simple translation matrix.
     * <p>
     * The resulting matrix can be multiplied against another transformation matrix to obtain an additional
     * translation.
     * <p>
     * In order to post-multiply a translation transformation directly to a matrix, use {@link #translate(float, float,
     * float) translate()} instead.
     *
     * @param x the offset to translate in x
     * @param y the offset to translate in y
     * @param z the offset to translate in z
     * @return this
     *
     * @see #translate(float, float, float)
     */
    public abstract Matrix4x3fc translation(float x, float y, float z);

    /**
     * Set this matrix to be a simple translation matrix.
     * <p>
     * The resulting matrix can be multiplied against another transformation matrix to obtain an additional
     * translation.
     * <p>
     * In order to post-multiply a translation transformation directly to a matrix, use {@link #translate(IVector3f)
     * translate()} instead.
     *
     * @param offset the offsets in x, y and z to translate
     * @return this
     *
     * @see #translate(float, float, float)
     */
    public abstract Matrix4x3fc translation(IVector3f offset);

    /**
     * Set only the translation components <tt>(m30, m31, m32)</tt> of this matrix to the given values <tt>(x, y,
     * z)</tt>.
     * <p>
     * To build a translation matrix instead, use {@link #translation(float, float, float)}. To apply a translation, use
     * {@link #translate(float, float, float)}.
     *
     * @param x the offset to translate in x
     * @param y the offset to translate in y
     * @param z the offset to translate in z
     * @return this
     *
     * @see #translation(float, float, float)
     * @see #translate(float, float, float)
     */
    public abstract Matrix4x3fc setTranslation(float x, float y, float z);

    /**
     * Set only the translation components <tt>(m30, m31, m32)</tt> of this matrix to the values <tt>(xyz.x, xyz.y,
     * xyz.z)</tt>.
     * <p>
     * To build a translation matrix instead, use {@link #translation(IVector3f)}. To apply a translation, use {@link
     * #translate(IVector3f)}.
     *
     * @param xyz the units to translate in <tt>(x, y, z)</tt>
     * @return this
     *
     * @see #translation(IVector3f)
     * @see #translate(IVector3f)
     */
    public abstract Matrix4x3fc setTranslation(IVector3f xyz);

    /**
     * Return a string representation of this matrix.
     * <p>
     * This method creates a new {@link DecimalFormat} on every invocation with the format string "<tt>0.000E0;-</tt>".
     *
     * @return the string representation
     */
    public abstract String toString();

    /**
     * Set all the values within this matrix to <code>0</code>.
     *
     * @return this
     */
    public abstract Matrix4x3fc zero();

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
    public abstract Matrix4x3fc scaling(float factor);

    /**
     * Set this matrix to be a simple scale matrix.
     * <p>
     * The resulting matrix can be multiplied against another transformation matrix to obtain an additional scaling.
     * <p>
     * In order to post-multiply a scaling transformation directly to a matrix, use {@link #scale(float, float, float)
     * scale()} instead.
     *
     * @param x the scale in x
     * @param y the scale in y
     * @param z the scale in z
     * @return this
     *
     * @see #scale(float, float, float)
     */
    public abstract Matrix4x3fc scaling(float x, float y, float z);

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
    public abstract Matrix4x3fc scaling(IVector3f xyz);

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
    public abstract Matrix4x3fc rotation(float angle, IVector3f axis);

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
    public abstract Matrix4x3fc rotation(AxisAngle4fc axisAngle);

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
    public abstract Matrix4x3fc rotation(float angle, float x, float y, float z);

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
    public abstract Matrix4x3fc rotationX(float ang);

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
    public abstract Matrix4x3fc rotationY(float ang);

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
    public abstract Matrix4x3fc rotationZ(float ang);

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
    public abstract Matrix4x3fc rotationXYZ(float angleX, float angleY, float angleZ);

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
    public abstract Matrix4x3fc rotationZYX(float angleZ, float angleY, float angleX);

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
    public abstract Matrix4x3fc rotationYXZ(float angleY, float angleX, float angleZ);

    /**
     * Set only the left 3x3 submatrix of this matrix to a rotation of <code>angleX</code> radians about the X axis,
     * followed by a rotation of <code>angleY</code> radians about the Y axis and followed by a rotation of
     * <code>angleZ</code> radians about the Z axis.
     * <p>
     * When used with a right-handed coordinate system, the produced rotation will rotate a vector counter-clockwise
     * around the rotation axis, when viewing along the negative axis direction towards the origin. When used with a
     * left-handed coordinate system, the rotation is clockwise.
     *
     * @param angleX the angle to rotate about X
     * @param angleY the angle to rotate about Y
     * @param angleZ the angle to rotate about Z
     * @return this
     */
    public abstract Matrix4x3fc setRotationXYZ(float angleX, float angleY, float angleZ);

    /**
     * Set only the left 3x3 submatrix of this matrix to a rotation of <code>angleZ</code> radians about the Z axis,
     * followed by a rotation of <code>angleY</code> radians about the Y axis and followed by a rotation of
     * <code>angleX</code> radians about the X axis.
     * <p>
     * When used with a right-handed coordinate system, the produced rotation will rotate a vector counter-clockwise
     * around the rotation axis, when viewing along the negative axis direction towards the origin. When used with a
     * left-handed coordinate system, the rotation is clockwise.
     *
     * @param angleZ the angle to rotate about Z
     * @param angleY the angle to rotate about Y
     * @param angleX the angle to rotate about X
     * @return this
     */
    public abstract Matrix4x3fc setRotationZYX(float angleZ, float angleY, float angleX);

    /**
     * Set only the left 3x3 submatrix of this matrix to a rotation of <code>angleY</code> radians about the Y axis,
     * followed by a rotation of <code>angleX</code> radians about the X axis and followed by a rotation of
     * <code>angleZ</code> radians about the Z axis.
     * <p>
     * When used with a right-handed coordinate system, the produced rotation will rotate a vector counter-clockwise
     * around the rotation axis, when viewing along the negative axis direction towards the origin. When used with a
     * left-handed coordinate system, the rotation is clockwise.
     *
     * @param angleY the angle to rotate about Y
     * @param angleX the angle to rotate about X
     * @param angleZ the angle to rotate about Z
     * @return this
     */
    public abstract Matrix4x3fc setRotationYXZ(float angleY, float angleX, float angleZ);

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
    public abstract Matrix4x3fc rotation(IQuaternionf quat);

    /**
     * Set <code>this</code> matrix to <tt>T * R * S</tt>, where <tt>T</tt> is a translation by the given <tt>(tx, ty,
     * tz)</tt>, <tt>R</tt> is a rotation transformation specified by the quaternion <tt>(qx, qy, qz, qw)</tt>, and
     * <tt>S</tt> is a scaling transformation which scales the three axes x, y and z by <tt>(sx, sy, sz)</tt>.
     * <p>
     * When transforming a vector by the resulting matrix the scaling transformation will be applied first, then the
     * rotation and at last the translation.
     * <p>
     * When used with a right-handed coordinate system, the produced rotation will rotate a vector counter-clockwise
     * around the rotation axis, when viewing along the negative axis direction towards the origin. When used with a
     * left-handed coordinate system, the rotation is clockwise.
     * <p>
     * This method is equivalent to calling: <tt>translation(tx, ty, tz).rotate(quat).scale(sx, sy, sz)</tt>
     *
     * @param tx the number of units by which to translate the x-component
     * @param ty the number of units by which to translate the y-component
     * @param tz the number of units by which to translate the z-component
     * @param qx the x-coordinate of the vector part of the quaternion
     * @param qy the y-coordinate of the vector part of the quaternion
     * @param qz the z-coordinate of the vector part of the quaternion
     * @param qw the scalar part of the quaternion
     * @param sx the scaling factor for the x-axis
     * @param sy the scaling factor for the y-axis
     * @param sz the scaling factor for the z-axis
     * @return this
     *
     * @see #translation(float, float, float)
     * @see #rotate(IQuaternionf)
     * @see #scale(float, float, float)
     */
    public abstract Matrix4x3fc translationRotateScale(float tx, float ty, float tz,
                                                       float qx, float qy, float qz, float qw,
                                                       float sx, float sy, float sz);

    /**
     * Set <code>this</code> matrix to <tt>T * R * S</tt>, where <tt>T</tt> is the given <code>translation</code>,
     * <tt>R</tt> is a rotation transformation specified by the given quaternion, and <tt>S</tt> is a scaling
     * transformation which scales the axes by <code>scale</code>.
     * <p>
     * When transforming a vector by the resulting matrix the scaling transformation will be applied first, then the
     * rotation and at last the translation.
     * <p>
     * When used with a right-handed coordinate system, the produced rotation will rotate a vector counter-clockwise
     * around the rotation axis, when viewing along the negative axis direction towards the origin. When used with a
     * left-handed coordinate system, the rotation is clockwise.
     * <p>
     * This method is equivalent to calling: <tt>translation(translation).rotate(quat).scale(scale)</tt>
     *
     * @param translation the translation
     * @param quat        the quaternion representing a rotation
     * @param scale       the scaling factors
     * @return this
     *
     * @see #translation(IVector3f)
     * @see #rotate(IQuaternionf)
     */
    public abstract Matrix4x3fc translationRotateScale(IVector3f translation,
                                                       IQuaternionf quat,
                                                       IVector3f scale);

    /**
     * Set <code>this</code> matrix to <tt>T * R * S * M</tt>, where <tt>T</tt> is a translation by the given <tt>(tx,
     * ty, tz)</tt>, <tt>R</tt> is a rotation transformation specified by the quaternion <tt>(qx, qy, qz, qw)</tt>,
     * <tt>S</tt> is a scaling transformation which scales the three axes x, y and z by <tt>(sx, sy, sz)</tt>.
     * <p>
     * When transforming a vector by the resulting matrix the transformation described by <code>M</code> will be applied
     * first, then the scaling, then rotation and at last the translation.
     * <p>
     * When used with a right-handed coordinate system, the produced rotation will rotate a vector counter-clockwise
     * around the rotation axis, when viewing along the negative axis direction towards the origin. When used with a
     * left-handed coordinate system, the rotation is clockwise.
     * <p>
     * This method is equivalent to calling: <tt>translation(tx, ty, tz).rotate(quat).scale(sx, sy, sz).mul(m)</tt>
     *
     * @param tx the number of units by which to translate the x-component
     * @param ty the number of units by which to translate the y-component
     * @param tz the number of units by which to translate the z-component
     * @param qx the x-coordinate of the vector part of the quaternion
     * @param qy the y-coordinate of the vector part of the quaternion
     * @param qz the z-coordinate of the vector part of the quaternion
     * @param qw the scalar part of the quaternion
     * @param sx the scaling factor for the x-axis
     * @param sy the scaling factor for the y-axis
     * @param sz the scaling factor for the z-axis
     * @param m  the matrix to multiply by
     * @return this
     *
     * @see #translation(float, float, float)
     * @see #rotate(IQuaternionf)
     * @see #scale(float, float, float)
     * @see #mul(IMatrix4x3f)
     */
    public abstract Matrix4x3fc translationRotateScaleMul(float tx, float ty, float tz,
                                                          float qx, float qy, float qz, float qw,
                                                          float sx, float sy, float sz,
                                                          Matrix4x3fc m);

    /**
     * Set <code>this</code> matrix to <tt>T * R * S * M</tt>, where <tt>T</tt> is the given <code>translation</code>,
     * <tt>R</tt> is a rotation transformation specified by the given quaternion, <tt>S</tt> is a scaling transformation
     * which scales the axes by <code>scale</code>.
     * <p>
     * When transforming a vector by the resulting matrix the transformation described by <code>M</code> will be applied
     * first, then the scaling, then rotation and at last the translation.
     * <p>
     * When used with a right-handed coordinate system, the produced rotation will rotate a vector counter-clockwise
     * around the rotation axis, when viewing along the negative axis direction towards the origin. When used with a
     * left-handed coordinate system, the rotation is clockwise.
     * <p>
     * This method is equivalent to calling: <tt>translation(translation).rotate(quat).scale(scale).mul(m)</tt>
     *
     * @param translation the translation
     * @param quat        the quaternion representing a rotation
     * @param scale       the scaling factors
     * @param m           the matrix to multiply by
     * @return this
     *
     * @see #translation(IVector3f)
     * @see #rotate(IQuaternionf)
     */
    public abstract Matrix4x3fc translationRotateScaleMul(IVector3f translation, IQuaternionf quat, IVector3f scale, Matrix4x3fc m);

    /**
     * Set <code>this</code> matrix to <tt>T * R</tt>, where <tt>T</tt> is a translation by the given <tt>(tx, ty,
     * tz)</tt> and <tt>R</tt> is a rotation transformation specified by the given quaternion.
     * <p>
     * When transforming a vector by the resulting matrix the rotation transformation will be applied first and then the
     * translation.
     * <p>
     * When used with a right-handed coordinate system, the produced rotation will rotate a vector counter-clockwise
     * around the rotation axis, when viewing along the negative axis direction towards the origin. When used with a
     * left-handed coordinate system, the rotation is clockwise.
     * <p>
     * This method is equivalent to calling: <tt>translation(tx, ty, tz).rotate(quat)</tt>
     *
     * @param tx   the number of units by which to translate the x-component
     * @param ty   the number of units by which to translate the y-component
     * @param tz   the number of units by which to translate the z-component
     * @param quat the quaternion representing a rotation
     * @return this
     *
     * @see #translation(float, float, float)
     * @see #rotate(IQuaternionf)
     */
    public abstract Matrix4x3fc translationRotate(float tx, float ty, float tz, IQuaternionf quat);

    /**
     * Set <code>this</code> matrix to <tt>T * R * M</tt>, where <tt>T</tt> is a translation by the given <tt>(tx, ty,
     * tz)</tt>, <tt>R</tt> is a rotation - and possibly scaling - transformation specified by the given quaternion and
     * <tt>M</tt> is the given matrix <code>mat</code>.
     * <p>
     * When transforming a vector by the resulting matrix the transformation described by <code>M</code> will be applied
     * first, then the scaling, then rotation and at last the translation.
     * <p>
     * When used with a right-handed coordinate system, the produced rotation will rotate a vector counter-clockwise
     * around the rotation axis, when viewing along the negative axis direction towards the origin. When used with a
     * left-handed coordinate system, the rotation is clockwise.
     * <p>
     * This method is equivalent to calling: <tt>translation(tx, ty, tz).rotate(quat).mul(mat)</tt>
     *
     * @param tx   the number of units by which to translate the x-component
     * @param ty   the number of units by which to translate the y-component
     * @param tz   the number of units by which to translate the z-component
     * @param quat the quaternion representing a rotation
     * @param mat  the matrix to multiply with
     * @return this
     *
     * @see #translation(float, float, float)
     * @see #rotate(IQuaternionf)
     * @see #mul(IMatrix4x3f)
     */
    public abstract Matrix4x3fc translationRotateMul(float tx, float ty, float tz, IQuaternionf quat, IMatrix4x3f mat);

    /**
     * Set <code>this</code> matrix to <tt>T * R * M</tt>, where <tt>T</tt> is a translation by the given <tt>(tx, ty,
     * tz)</tt>, <tt>R</tt> is a rotation - and possibly scaling - transformation specified by the quaternion <tt>(qx,
     * qy, qz, qw)</tt> and <tt>M</tt> is the given matrix <code>mat</code>
     * <p>
     * When transforming a vector by the resulting matrix the transformation described by <code>M</code> will be applied
     * first, then the scaling, then rotation and at last the translation.
     * <p>
     * When used with a right-handed coordinate system, the produced rotation will rotate a vector counter-clockwise
     * around the rotation axis, when viewing along the negative axis direction towards the origin. When used with a
     * left-handed coordinate system, the rotation is clockwise.
     * <p>
     * This method is equivalent to calling: <tt>translation(tx, ty, tz).rotate(quat).mul(mat)</tt>
     *
     * @param tx  the number of units by which to translate the x-component
     * @param ty  the number of units by which to translate the y-component
     * @param tz  the number of units by which to translate the z-component
     * @param qx  the x-coordinate of the vector part of the quaternion
     * @param qy  the y-coordinate of the vector part of the quaternion
     * @param qz  the z-coordinate of the vector part of the quaternion
     * @param qw  the scalar part of the quaternion
     * @param mat the matrix to multiply with
     * @return this
     *
     * @see #translation(float, float, float)
     * @see #rotate(IQuaternionf)
     * @see #mul(IMatrix4x3f)
     */
    public abstract Matrix4x3fc translationRotateMul(float tx, float ty, float tz, float qx, float qy, float qz, float qw, IMatrix4x3f mat);

    /**
     * Set the left 3x3 submatrix of this {@link Matrix4x3fc} to the given {@link IMatrix3f} and don't change the other
     * elements.
     *
     * @param mat the 3x3 matrix
     * @return this
     */
    public abstract Matrix4x3fc set3x3(IMatrix3f mat);

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
    public abstract Matrix4x3fc scale(IVector3f xyz);

    /**
     * Apply scaling to this matrix by uniformly scaling all base axes by the given <code>xyz</code> factor.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>S</code> the scaling matrix, then the new matrix will be
     * <code>M * S</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>M * S *
     * v</code>, the scaling will be applied first!
     * <p>
     * Individual scaling of all three axes can be applied using {@link #scale(float, float, float)}.
     *
     * @param xyz the factor for all components
     * @return this
     *
     * @see #scale(float, float, float)
     */
    public abstract Matrix4x3fc scale(float xyz);

    /**
     * Apply scaling to this matrix by scaling the base axes by the given x, y and z factors.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>S</code> the scaling matrix, then the new matrix will be
     * <code>M * S</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>M * S *
     * v</code>, the scaling will be applied first!
     *
     * @param x the factor of the x component
     * @param y the factor of the y component
     * @param z the factor of the z component
     * @return this
     */
    public abstract Matrix4x3fc scale(float x, float y, float z);

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
    public abstract Matrix4x3fc scaleLocal(float x, float y, float z);

    /**
     * Apply rotation about the X axis to this matrix by rotating the given amount of radians.
     * <p>
     * When used with a right-handed coordinate system, the produced rotation will rotate a vector counter-clockwise
     * around the rotation axis, when viewing along the negative axis direction towards the origin. When used with a
     * left-handed coordinate system, the rotation is clockwise.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the rotation matrix, then the new matrix will be
     * <code>M * R</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>M * R *
     * v</code>, the rotation will be applied first!
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Basic_rotations">http://en.wikipedia.org</a>
     *
     * @param ang the angle in radians
     * @return this
     */
    public abstract Matrix4x3fc rotateX(float ang);

    /**
     * Apply rotation about the Y axis to this matrix by rotating the given amount of radians.
     * <p>
     * When used with a right-handed coordinate system, the produced rotation will rotate a vector counter-clockwise
     * around the rotation axis, when viewing along the negative axis direction towards the origin. When used with a
     * left-handed coordinate system, the rotation is clockwise.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the rotation matrix, then the new matrix will be
     * <code>M * R</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>M * R *
     * v</code>, the rotation will be applied first!
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Basic_rotations">http://en.wikipedia.org</a>
     *
     * @param ang the angle in radians
     * @return this
     */
    public abstract Matrix4x3fc rotateY(float ang);

    /**
     * Apply rotation about the Z axis to this matrix by rotating the given amount of radians.
     * <p>
     * When used with a right-handed coordinate system, the produced rotation will rotate a vector counter-clockwise
     * around the rotation axis, when viewing along the negative axis direction towards the origin. When used with a
     * left-handed coordinate system, the rotation is clockwise.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the rotation matrix, then the new matrix will be
     * <code>M * R</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>M * R *
     * v</code>, the rotation will be applied first!
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Basic_rotations">http://en.wikipedia.org</a>
     *
     * @param ang the angle in radians
     * @return this
     */
    public abstract Matrix4x3fc rotateZ(float ang);

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
    public abstract Matrix4x3fc rotateXYZ(Vector3fc angles);

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
    public abstract Matrix4x3fc rotateXYZ(float angleX, float angleY, float angleZ);

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
    public abstract Matrix4x3fc rotateZYX(Vector3fc angles);

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
    public abstract Matrix4x3fc rotateZYX(float angleZ, float angleY, float angleX);

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
    public abstract Matrix4x3fc rotateYXZ(Vector3fc angles);

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
    public abstract Matrix4x3fc rotateYXZ(float angleY, float angleX, float angleZ);

    /**
     * Apply rotation to this matrix by rotating the given amount of radians about the specified <tt>(x, y, z)</tt>
     * axis.
     * <p>
     * The axis described by the three components needs to be a unit vector.
     * <p>
     * When used with a right-handed coordinate system, the produced rotation will rotate a vector counter-clockwise
     * around the rotation axis, when viewing along the negative axis direction towards the origin. When used with a
     * left-handed coordinate system, the rotation is clockwise.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the rotation matrix, then the new matrix will be
     * <code>M * R</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>M * R *
     * v</code>, the rotation will be applied first!
     * <p>
     * In order to set the matrix to a rotation matrix without post-multiplying the rotation transformation, use {@link
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
    public abstract Matrix4x3fc rotate(float ang, float x, float y, float z);

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
    public abstract Matrix4x3fc rotateLocal(float ang, float x, float y, float z);

    /**
     * Apply a translation to this matrix by translating by the given number of units in x, y and z.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>T</code> the translation matrix, then the new matrix will
     * be <code>M * T</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>M * T *
     * v</code>, the translation will be applied first!
     * <p>
     * In order to set the matrix to a translation transformation without post-multiplying it, use {@link
     * #translation(IVector3f)}.
     *
     * @param offset the number of units in x, y and z by which to translate
     * @return this
     *
     * @see #translation(IVector3f)
     */
    public abstract Matrix4x3fc translate(IVector3f offset);

    /**
     * Apply a translation to this matrix by translating by the given number of units in x, y and z.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>T</code> the translation matrix, then the new matrix will
     * be <code>M * T</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>M * T *
     * v</code>, the translation will be applied first!
     * <p>
     * In order to set the matrix to a translation transformation without post-multiplying it, use {@link
     * #translation(float, float, float)}.
     *
     * @param x the offset to translate in x
     * @param y the offset to translate in y
     * @param z the offset to translate in z
     * @return this
     *
     * @see #translation(float, float, float)
     */
    public abstract Matrix4x3fc translate(float x, float y, float z);

    /**
     * Pre-multiply a translation to this matrix by translating by the given number of units in x, y and z.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>T</code> the translation matrix, then the new matrix will
     * be <code>T * M</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>T * M *
     * v</code>, the translation will be applied last!
     * <p>
     * In order to set the matrix to a translation transformation without pre-multiplying it, use {@link
     * #translation(IVector3f)}.
     *
     * @param offset the number of units in x, y and z by which to translate
     * @return this
     *
     * @see #translation(IVector3f)
     */
    public abstract Matrix4x3fc translateLocal(IVector3f offset);

    /**
     * Pre-multiply a translation to this matrix by translating by the given number of units in x, y and z.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>T</code> the translation matrix, then the new matrix will
     * be <code>T * M</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>T * M *
     * v</code>, the translation will be applied last!
     * <p>
     * In order to set the matrix to a translation transformation without pre-multiplying it, use {@link
     * #translation(float, float, float)}.
     *
     * @param x the offset to translate in x
     * @param y the offset to translate in y
     * @param z the offset to translate in z
     * @return this
     *
     * @see #translation(float, float, float)
     */
    public abstract Matrix4x3fc translateLocal(float x, float y, float z);

    /**
     * Apply an orthographic projection transformation for a right-handed coordinate system using the given NDC z range
     * to this matrix.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>O</code> the orthographic projection matrix, then the new
     * matrix will be <code>M * O</code>. So when transforming a vector <code>v</code> with the new matrix by using
     * <code>M * O * v</code>, the orthographic projection transformation will be applied first!
     * <p>
     * In order to set the matrix to an orthographic projection without post-multiplying it, use {@link #setOrtho(float,
     * float, float, float, float, float, boolean) setOrtho()}.
     * <p>
     * Reference: <a href="http://www.songho.ca/opengl/gl_projectionmatrix.html#ortho">http://www.songho.ca</a>
     *
     * @param left       the distance from the center to the left frustum edge
     * @param right      the distance from the center to the right frustum edge
     * @param bottom     the distance from the center to the bottom frustum edge
     * @param top        the distance from the center to the top frustum edge
     * @param zNear      near clipping plane distance
     * @param zFar       far clipping plane distance
     * @param zZeroToOne whether to use Vulkan's and Direct3D's NDC z range of <tt>[0..+1]</tt> when <code>true</code>
     *                   or whether to use OpenGL's NDC z range of <tt>[-1..+1]</tt> when <code>false</code>
     * @return this
     *
     * @see #setOrtho(float, float, float, float, float, float, boolean)
     */
    public abstract Matrix4x3fc ortho(float left, float right, float bottom, float top, float zNear, float zFar, boolean zZeroToOne);

    /**
     * Apply an orthographic projection transformation for a right-handed coordinate system using OpenGL's NDC z range
     * of <tt>[-1..+1]</tt> to this matrix.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>O</code> the orthographic projection matrix, then the new
     * matrix will be <code>M * O</code>. So when transforming a vector <code>v</code> with the new matrix by using
     * <code>M * O * v</code>, the orthographic projection transformation will be applied first!
     * <p>
     * In order to set the matrix to an orthographic projection without post-multiplying it, use {@link #setOrtho(float,
     * float, float, float, float, float) setOrtho()}.
     * <p>
     * Reference: <a href="http://www.songho.ca/opengl/gl_projectionmatrix.html#ortho">http://www.songho.ca</a>
     *
     * @param left   the distance from the center to the left frustum edge
     * @param right  the distance from the center to the right frustum edge
     * @param bottom the distance from the center to the bottom frustum edge
     * @param top    the distance from the center to the top frustum edge
     * @param zNear  near clipping plane distance
     * @param zFar   far clipping plane distance
     * @return this
     *
     * @see #setOrtho(float, float, float, float, float, float)
     */
    public abstract Matrix4x3fc ortho(float left, float right, float bottom, float top, float zNear, float zFar);

    /**
     * Apply an orthographic projection transformation for a left-handed coordiante system using the given NDC z range
     * to this matrix.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>O</code> the orthographic projection matrix, then the new
     * matrix will be <code>M * O</code>. So when transforming a vector <code>v</code> with the new matrix by using
     * <code>M * O * v</code>, the orthographic projection transformation will be applied first!
     * <p>
     * In order to set the matrix to an orthographic projection without post-multiplying it, use {@link
     * #setOrthoLH(float, float, float, float, float, float, boolean) setOrthoLH()}.
     * <p>
     * Reference: <a href="http://www.songho.ca/opengl/gl_projectionmatrix.html#ortho">http://www.songho.ca</a>
     *
     * @param left       the distance from the center to the left frustum edge
     * @param right      the distance from the center to the right frustum edge
     * @param bottom     the distance from the center to the bottom frustum edge
     * @param top        the distance from the center to the top frustum edge
     * @param zNear      near clipping plane distance
     * @param zFar       far clipping plane distance
     * @param zZeroToOne whether to use Vulkan's and Direct3D's NDC z range of <tt>[0..+1]</tt> when <code>true</code>
     *                   or whether to use OpenGL's NDC z range of <tt>[-1..+1]</tt> when <code>false</code>
     * @return this
     *
     * @see #setOrthoLH(float, float, float, float, float, float, boolean)
     */
    public abstract Matrix4x3fc orthoLH(float left, float right, float bottom, float top, float zNear, float zFar, boolean zZeroToOne);

    /**
     * Apply an orthographic projection transformation for a left-handed coordiante system using OpenGL's NDC z range of
     * <tt>[-1..+1]</tt> to this matrix.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>O</code> the orthographic projection matrix, then the new
     * matrix will be <code>M * O</code>. So when transforming a vector <code>v</code> with the new matrix by using
     * <code>M * O * v</code>, the orthographic projection transformation will be applied first!
     * <p>
     * In order to set the matrix to an orthographic projection without post-multiplying it, use {@link
     * #setOrthoLH(float, float, float, float, float, float) setOrthoLH()}.
     * <p>
     * Reference: <a href="http://www.songho.ca/opengl/gl_projectionmatrix.html#ortho">http://www.songho.ca</a>
     *
     * @param left   the distance from the center to the left frustum edge
     * @param right  the distance from the center to the right frustum edge
     * @param bottom the distance from the center to the bottom frustum edge
     * @param top    the distance from the center to the top frustum edge
     * @param zNear  near clipping plane distance
     * @param zFar   far clipping plane distance
     * @return this
     *
     * @see #setOrthoLH(float, float, float, float, float, float)
     */
    public abstract Matrix4x3fc orthoLH(float left, float right, float bottom, float top, float zNear, float zFar);

    /**
     * Set this matrix to be an orthographic projection transformation for a right-handed coordinate system using the
     * given NDC z range.
     * <p>
     * In order to apply the orthographic projection to an already existing transformation, use {@link #ortho(float,
     * float, float, float, float, float, boolean) ortho()}.
     * <p>
     * Reference: <a href="http://www.songho.ca/opengl/gl_projectionmatrix.html#ortho">http://www.songho.ca</a>
     *
     * @param left       the distance from the center to the left frustum edge
     * @param right      the distance from the center to the right frustum edge
     * @param bottom     the distance from the center to the bottom frustum edge
     * @param top        the distance from the center to the top frustum edge
     * @param zNear      near clipping plane distance
     * @param zFar       far clipping plane distance
     * @param zZeroToOne whether to use Vulkan's and Direct3D's NDC z range of <tt>[0..+1]</tt> when <code>true</code>
     *                   or whether to use OpenGL's NDC z range of <tt>[-1..+1]</tt> when <code>false</code>
     * @return this
     *
     * @see #ortho(float, float, float, float, float, float, boolean)
     */
    public abstract Matrix4x3fc setOrtho(float left, float right, float bottom, float top, float zNear, float zFar, boolean zZeroToOne);

    /**
     * Set this matrix to be an orthographic projection transformation for a right-handed coordinate system using
     * OpenGL's NDC z range of <tt>[-1..+1]</tt>.
     * <p>
     * In order to apply the orthographic projection to an already existing transformation, use {@link #ortho(float,
     * float, float, float, float, float) ortho()}.
     * <p>
     * Reference: <a href="http://www.songho.ca/opengl/gl_projectionmatrix.html#ortho">http://www.songho.ca</a>
     *
     * @param left   the distance from the center to the left frustum edge
     * @param right  the distance from the center to the right frustum edge
     * @param bottom the distance from the center to the bottom frustum edge
     * @param top    the distance from the center to the top frustum edge
     * @param zNear  near clipping plane distance
     * @param zFar   far clipping plane distance
     * @return this
     *
     * @see #ortho(float, float, float, float, float, float)
     */
    public abstract Matrix4x3fc setOrtho(float left, float right, float bottom, float top, float zNear, float zFar);

    /**
     * Set this matrix to be an orthographic projection transformation for a left-handed coordinate system using the
     * given NDC z range.
     * <p>
     * In order to apply the orthographic projection to an already existing transformation, use {@link #orthoLH(float,
     * float, float, float, float, float, boolean) orthoLH()}.
     * <p>
     * Reference: <a href="http://www.songho.ca/opengl/gl_projectionmatrix.html#ortho">http://www.songho.ca</a>
     *
     * @param left       the distance from the center to the left frustum edge
     * @param right      the distance from the center to the right frustum edge
     * @param bottom     the distance from the center to the bottom frustum edge
     * @param top        the distance from the center to the top frustum edge
     * @param zNear      near clipping plane distance
     * @param zFar       far clipping plane distance
     * @param zZeroToOne whether to use Vulkan's and Direct3D's NDC z range of <tt>[0..+1]</tt> when <code>true</code>
     *                   or whether to use OpenGL's NDC z range of <tt>[-1..+1]</tt> when <code>false</code>
     * @return this
     *
     * @see #orthoLH(float, float, float, float, float, float, boolean)
     */
    public abstract Matrix4x3fc setOrthoLH(float left, float right, float bottom, float top, float zNear, float zFar, boolean zZeroToOne);

    /**
     * Set this matrix to be an orthographic projection transformation for a left-handed coordinate system using
     * OpenGL's NDC z range of <tt>[-1..+1]</tt>.
     * <p>
     * In order to apply the orthographic projection to an already existing transformation, use {@link #orthoLH(float,
     * float, float, float, float, float) orthoLH()}.
     * <p>
     * Reference: <a href="http://www.songho.ca/opengl/gl_projectionmatrix.html#ortho">http://www.songho.ca</a>
     *
     * @param left   the distance from the center to the left frustum edge
     * @param right  the distance from the center to the right frustum edge
     * @param bottom the distance from the center to the bottom frustum edge
     * @param top    the distance from the center to the top frustum edge
     * @param zNear  near clipping plane distance
     * @param zFar   far clipping plane distance
     * @return this
     *
     * @see #orthoLH(float, float, float, float, float, float)
     */
    public abstract Matrix4x3fc setOrthoLH(float left, float right, float bottom, float top, float zNear, float zFar);

    /**
     * Apply a symmetric orthographic projection transformation for a right-handed coordinate system using the given NDC
     * z range to this matrix.
     * <p>
     * This method is equivalent to calling {@link #ortho(float, float, float, float, float, float, boolean) ortho()}
     * with <code>left=-width/2</code>, <code>right=+width/2</code>, <code>bottom=-height/2</code> and
     * <code>top=+height/2</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>O</code> the orthographic projection matrix, then the new
     * matrix will be <code>M * O</code>. So when transforming a vector <code>v</code> with the new matrix by using
     * <code>M * O * v</code>, the orthographic projection transformation will be applied first!
     * <p>
     * In order to set the matrix to a symmetric orthographic projection without post-multiplying it, use {@link
     * #setOrthoSymmetric(float, float, float, float, boolean) setOrthoSymmetric()}.
     * <p>
     * Reference: <a href="http://www.songho.ca/opengl/gl_projectionmatrix.html#ortho">http://www.songho.ca</a>
     *
     * @param width      the distance between the right and left frustum edges
     * @param height     the distance between the top and bottom frustum edges
     * @param zNear      near clipping plane distance
     * @param zFar       far clipping plane distance
     * @param zZeroToOne whether to use Vulkan's and Direct3D's NDC z range of <tt>[0..+1]</tt> when <code>true</code>
     *                   or whether to use OpenGL's NDC z range of <tt>[-1..+1]</tt> when <code>false</code>
     * @return this
     *
     * @see #setOrthoSymmetric(float, float, float, float, boolean)
     */
    public abstract Matrix4x3fc orthoSymmetric(float width, float height, float zNear, float zFar, boolean zZeroToOne);

    /**
     * Apply a symmetric orthographic projection transformation for a right-handed coordinate system using OpenGL's NDC
     * z range of <tt>[-1..+1]</tt> to this matrix.
     * <p>
     * This method is equivalent to calling {@link #ortho(float, float, float, float, float, float) ortho()} with
     * <code>left=-width/2</code>, <code>right=+width/2</code>, <code>bottom=-height/2</code> and
     * <code>top=+height/2</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>O</code> the orthographic projection matrix, then the new
     * matrix will be <code>M * O</code>. So when transforming a vector <code>v</code> with the new matrix by using
     * <code>M * O * v</code>, the orthographic projection transformation will be applied first!
     * <p>
     * In order to set the matrix to a symmetric orthographic projection without post-multiplying it, use {@link
     * #setOrthoSymmetric(float, float, float, float) setOrthoSymmetric()}.
     * <p>
     * Reference: <a href="http://www.songho.ca/opengl/gl_projectionmatrix.html#ortho">http://www.songho.ca</a>
     *
     * @param width  the distance between the right and left frustum edges
     * @param height the distance between the top and bottom frustum edges
     * @param zNear  near clipping plane distance
     * @param zFar   far clipping plane distance
     * @return this
     *
     * @see #setOrthoSymmetric(float, float, float, float)
     */
    public abstract Matrix4x3fc orthoSymmetric(float width, float height, float zNear, float zFar);

    /**
     * Apply a symmetric orthographic projection transformation for a left-handed coordinate system using the given NDC
     * z range to this matrix.
     * <p>
     * This method is equivalent to calling {@link #orthoLH(float, float, float, float, float, float, boolean)
     * orthoLH()} with <code>left=-width/2</code>, <code>right=+width/2</code>, <code>bottom=-height/2</code> and
     * <code>top=+height/2</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>O</code> the orthographic projection matrix, then the new
     * matrix will be <code>M * O</code>. So when transforming a vector <code>v</code> with the new matrix by using
     * <code>M * O * v</code>, the orthographic projection transformation will be applied first!
     * <p>
     * In order to set the matrix to a symmetric orthographic projection without post-multiplying it, use {@link
     * #setOrthoSymmetricLH(float, float, float, float, boolean) setOrthoSymmetricLH()}.
     * <p>
     * Reference: <a href="http://www.songho.ca/opengl/gl_projectionmatrix.html#ortho">http://www.songho.ca</a>
     *
     * @param width      the distance between the right and left frustum edges
     * @param height     the distance between the top and bottom frustum edges
     * @param zNear      near clipping plane distance
     * @param zFar       far clipping plane distance
     * @param zZeroToOne whether to use Vulkan's and Direct3D's NDC z range of <tt>[0..+1]</tt> when <code>true</code>
     *                   or whether to use OpenGL's NDC z range of <tt>[-1..+1]</tt> when <code>false</code>
     * @return this
     *
     * @see #setOrthoSymmetricLH(float, float, float, float, boolean)
     */
    public abstract Matrix4x3fc orthoSymmetricLH(float width, float height, float zNear, float zFar, boolean zZeroToOne);

    /**
     * Apply a symmetric orthographic projection transformation for a left-handed coordinate system using OpenGL's NDC z
     * range of <tt>[-1..+1]</tt> to this matrix.
     * <p>
     * This method is equivalent to calling {@link #orthoLH(float, float, float, float, float, float) orthoLH()} with
     * <code>left=-width/2</code>, <code>right=+width/2</code>, <code>bottom=-height/2</code> and
     * <code>top=+height/2</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>O</code> the orthographic projection matrix, then the new
     * matrix will be <code>M * O</code>. So when transforming a vector <code>v</code> with the new matrix by using
     * <code>M * O * v</code>, the orthographic projection transformation will be applied first!
     * <p>
     * In order to set the matrix to a symmetric orthographic projection without post-multiplying it, use {@link
     * #setOrthoSymmetricLH(float, float, float, float) setOrthoSymmetricLH()}.
     * <p>
     * Reference: <a href="http://www.songho.ca/opengl/gl_projectionmatrix.html#ortho">http://www.songho.ca</a>
     *
     * @param width  the distance between the right and left frustum edges
     * @param height the distance between the top and bottom frustum edges
     * @param zNear  near clipping plane distance
     * @param zFar   far clipping plane distance
     * @return this
     *
     * @see #setOrthoSymmetricLH(float, float, float, float)
     */
    public abstract Matrix4x3fc orthoSymmetricLH(float width, float height, float zNear, float zFar);

    /**
     * Set this matrix to be a symmetric orthographic projection transformation for a right-handed coordinate system
     * using the given NDC z range.
     * <p>
     * This method is equivalent to calling {@link #setOrtho(float, float, float, float, float, float, boolean)
     * setOrtho()} with <code>left=-width/2</code>, <code>right=+width/2</code>, <code>bottom=-height/2</code> and
     * <code>top=+height/2</code>.
     * <p>
     * In order to apply the symmetric orthographic projection to an already existing transformation, use {@link
     * #orthoSymmetric(float, float, float, float, boolean) orthoSymmetric()}.
     * <p>
     * Reference: <a href="http://www.songho.ca/opengl/gl_projectionmatrix.html#ortho">http://www.songho.ca</a>
     *
     * @param width      the distance between the right and left frustum edges
     * @param height     the distance between the top and bottom frustum edges
     * @param zNear      near clipping plane distance
     * @param zFar       far clipping plane distance
     * @param zZeroToOne whether to use Vulkan's and Direct3D's NDC z range of <tt>[0..+1]</tt> when <code>true</code>
     *                   or whether to use OpenGL's NDC z range of <tt>[-1..+1]</tt> when <code>false</code>
     * @return this
     *
     * @see #orthoSymmetric(float, float, float, float, boolean)
     */
    public abstract Matrix4x3fc setOrthoSymmetric(float width, float height, float zNear, float zFar, boolean zZeroToOne);

    /**
     * Set this matrix to be a symmetric orthographic projection transformation for a right-handed coordinate system
     * using OpenGL's NDC z range of <tt>[-1..+1]</tt>.
     * <p>
     * This method is equivalent to calling {@link #setOrtho(float, float, float, float, float, float) setOrtho()} with
     * <code>left=-width/2</code>, <code>right=+width/2</code>, <code>bottom=-height/2</code> and
     * <code>top=+height/2</code>.
     * <p>
     * In order to apply the symmetric orthographic projection to an already existing transformation, use {@link
     * #orthoSymmetric(float, float, float, float) orthoSymmetric()}.
     * <p>
     * Reference: <a href="http://www.songho.ca/opengl/gl_projectionmatrix.html#ortho">http://www.songho.ca</a>
     *
     * @param width  the distance between the right and left frustum edges
     * @param height the distance between the top and bottom frustum edges
     * @param zNear  near clipping plane distance
     * @param zFar   far clipping plane distance
     * @return this
     *
     * @see #orthoSymmetric(float, float, float, float)
     */
    public abstract Matrix4x3fc setOrthoSymmetric(float width, float height, float zNear, float zFar);

    /**
     * Set this matrix to be a symmetric orthographic projection transformation for a left-handed coordinate system
     * using the given NDC z range.
     * <p>
     * This method is equivalent to calling {@link #setOrtho(float, float, float, float, float, float, boolean)
     * setOrtho()} with <code>left=-width/2</code>, <code>right=+width/2</code>, <code>bottom=-height/2</code> and
     * <code>top=+height/2</code>.
     * <p>
     * In order to apply the symmetric orthographic projection to an already existing transformation, use {@link
     * #orthoSymmetricLH(float, float, float, float, boolean) orthoSymmetricLH()}.
     * <p>
     * Reference: <a href="http://www.songho.ca/opengl/gl_projectionmatrix.html#ortho">http://www.songho.ca</a>
     *
     * @param width      the distance between the right and left frustum edges
     * @param height     the distance between the top and bottom frustum edges
     * @param zNear      near clipping plane distance
     * @param zFar       far clipping plane distance
     * @param zZeroToOne whether to use Vulkan's and Direct3D's NDC z range of <tt>[0..+1]</tt> when <code>true</code>
     *                   or whether to use OpenGL's NDC z range of <tt>[-1..+1]</tt> when <code>false</code>
     * @return this
     *
     * @see #orthoSymmetricLH(float, float, float, float, boolean)
     */
    public abstract Matrix4x3fc setOrthoSymmetricLH(float width, float height, float zNear, float zFar, boolean zZeroToOne);

    /**
     * Set this matrix to be a symmetric orthographic projection transformation for a left-handed coordinate system
     * using OpenGL's NDC z range of <tt>[-1..+1]</tt>.
     * <p>
     * This method is equivalent to calling {@link #setOrthoLH(float, float, float, float, float, float) setOrthoLH()}
     * with <code>left=-width/2</code>, <code>right=+width/2</code>, <code>bottom=-height/2</code> and
     * <code>top=+height/2</code>.
     * <p>
     * In order to apply the symmetric orthographic projection to an already existing transformation, use {@link
     * #orthoSymmetricLH(float, float, float, float) orthoSymmetricLH()}.
     * <p>
     * Reference: <a href="http://www.songho.ca/opengl/gl_projectionmatrix.html#ortho">http://www.songho.ca</a>
     *
     * @param width  the distance between the right and left frustum edges
     * @param height the distance between the top and bottom frustum edges
     * @param zNear  near clipping plane distance
     * @param zFar   far clipping plane distance
     * @return this
     *
     * @see #orthoSymmetricLH(float, float, float, float)
     */
    public abstract Matrix4x3fc setOrthoSymmetricLH(float width, float height, float zNear, float zFar);

    /**
     * Apply an orthographic projection transformation for a right-handed coordinate system to this matrix.
     * <p>
     * This method is equivalent to calling {@link #ortho(float, float, float, float, float, float) ortho()} with
     * <code>zNear=-1</code> and <code>zFar=+1</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>O</code> the orthographic projection matrix, then the new
     * matrix will be <code>M * O</code>. So when transforming a vector <code>v</code> with the new matrix by using
     * <code>M * O * v</code>, the orthographic projection transformation will be applied first!
     * <p>
     * In order to set the matrix to an orthographic projection without post-multiplying it, use {@link
     * #setOrtho2D(float, float, float, float) setOrtho2D()}.
     * <p>
     * Reference: <a href="http://www.songho.ca/opengl/gl_projectionmatrix.html#ortho">http://www.songho.ca</a>
     *
     * @param left   the distance from the center to the left frustum edge
     * @param right  the distance from the center to the right frustum edge
     * @param bottom the distance from the center to the bottom frustum edge
     * @param top    the distance from the center to the top frustum edge
     * @return this
     *
     * @see #ortho(float, float, float, float, float, float)
     * @see #setOrtho2D(float, float, float, float)
     */
    public abstract Matrix4x3fc ortho2D(float left, float right, float bottom, float top);

    /**
     * Apply an orthographic projection transformation for a left-handed coordinate system to this matrix.
     * <p>
     * This method is equivalent to calling {@link #orthoLH(float, float, float, float, float, float) orthoLH()} with
     * <code>zNear=-1</code> and <code>zFar=+1</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>O</code> the orthographic projection matrix, then the new
     * matrix will be <code>M * O</code>. So when transforming a vector <code>v</code> with the new matrix by using
     * <code>M * O * v</code>, the orthographic projection transformation will be applied first!
     * <p>
     * In order to set the matrix to an orthographic projection without post-multiplying it, use {@link
     * #setOrtho2DLH(float, float, float, float) setOrtho2DLH()}.
     * <p>
     * Reference: <a href="http://www.songho.ca/opengl/gl_projectionmatrix.html#ortho">http://www.songho.ca</a>
     *
     * @param left   the distance from the center to the left frustum edge
     * @param right  the distance from the center to the right frustum edge
     * @param bottom the distance from the center to the bottom frustum edge
     * @param top    the distance from the center to the top frustum edge
     * @return this
     *
     * @see #orthoLH(float, float, float, float, float, float)
     * @see #setOrtho2DLH(float, float, float, float)
     */
    public abstract Matrix4x3fc ortho2DLH(float left, float right, float bottom, float top);

    /**
     * Set this matrix to be an orthographic projection transformation for a right-handed coordinate system.
     * <p>
     * This method is equivalent to calling {@link #setOrtho(float, float, float, float, float, float) setOrtho()} with
     * <code>zNear=-1</code> and <code>zFar=+1</code>.
     * <p>
     * In order to apply the orthographic projection to an already existing transformation, use {@link #ortho2D(float,
     * float, float, float) ortho2D()}.
     * <p>
     * Reference: <a href="http://www.songho.ca/opengl/gl_projectionmatrix.html#ortho">http://www.songho.ca</a>
     *
     * @param left   the distance from the center to the left frustum edge
     * @param right  the distance from the center to the right frustum edge
     * @param bottom the distance from the center to the bottom frustum edge
     * @param top    the distance from the center to the top frustum edge
     * @return this
     *
     * @see #setOrtho(float, float, float, float, float, float)
     * @see #ortho2D(float, float, float, float)
     */
    public abstract Matrix4x3fc setOrtho2D(float left, float right, float bottom, float top);

    /**
     * Set this matrix to be an orthographic projection transformation for a left-handed coordinate system.
     * <p>
     * This method is equivalent to calling {@link #setOrtho(float, float, float, float, float, float) setOrthoLH()}
     * with <code>zNear=-1</code> and <code>zFar=+1</code>.
     * <p>
     * In order to apply the orthographic projection to an already existing transformation, use {@link #ortho2DLH(float,
     * float, float, float) ortho2DLH()}.
     * <p>
     * Reference: <a href="http://www.songho.ca/opengl/gl_projectionmatrix.html#ortho">http://www.songho.ca</a>
     *
     * @param left   the distance from the center to the left frustum edge
     * @param right  the distance from the center to the right frustum edge
     * @param bottom the distance from the center to the bottom frustum edge
     * @param top    the distance from the center to the top frustum edge
     * @return this
     *
     * @see #setOrthoLH(float, float, float, float, float, float)
     * @see #ortho2DLH(float, float, float, float)
     */
    public abstract Matrix4x3fc setOrtho2DLH(float left, float right, float bottom, float top);

    /**
     * Apply a rotation transformation to this matrix to make <code>-z</code> point along <code>dir</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>L</code> the lookalong rotation matrix, then the new
     * matrix will be <code>M * L</code>. So when transforming a vector <code>v</code> with the new matrix by using
     * <code>M * L * v</code>, the lookalong rotation transformation will be applied first!
     * <p>
     * This is equivalent to calling {@link #lookAt(IVector3f, IVector3f, IVector3f) lookAt} with <code>eye = (0, 0,
     * 0)</code> and <code>center = dir</code>.
     * <p>
     * In order to set the matrix to a lookalong transformation without post-multiplying it, use {@link
     * #setLookAlong(IVector3f, IVector3f) setLookAlong()}.
     *
     * @param dir the direction in space to look along
     * @param up  the direction of 'up'
     * @return this
     *
     * @see #lookAlong(float, float, float, float, float, float)
     * @see #lookAt(IVector3f, IVector3f, IVector3f)
     * @see #setLookAlong(IVector3f, IVector3f)
     */
    public abstract Matrix4x3fc lookAlong(IVector3f dir, IVector3f up);

    /**
     * Apply a rotation transformation to this matrix to make <code>-z</code> point along <code>dir</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>L</code> the lookalong rotation matrix, then the new
     * matrix will be <code>M * L</code>. So when transforming a vector <code>v</code> with the new matrix by using
     * <code>M * L * v</code>, the lookalong rotation transformation will be applied first!
     * <p>
     * This is equivalent to calling {@link #lookAt(float, float, float, float, float, float, float, float, float)
     * lookAt()} with <code>eye = (0, 0, 0)</code> and <code>center = dir</code>.
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
     * @see #lookAt(float, float, float, float, float, float, float, float, float)
     * @see #setLookAlong(float, float, float, float, float, float)
     */
    public abstract Matrix4x3fc lookAlong(float dirX, float dirY, float dirZ, float upX, float upY, float upZ);

    /**
     * Set this matrix to a rotation transformation to make <code>-z</code> point along <code>dir</code>.
     * <p>
     * This is equivalent to calling {@link #setLookAt(IVector3f, IVector3f, IVector3f) setLookAt()} with <code>eye =
     * (0, 0, 0)</code> and <code>center = dir</code>.
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
    public abstract Matrix4x3fc setLookAlong(IVector3f dir, IVector3f up);

    /**
     * Set this matrix to a rotation transformation to make <code>-z</code> point along <code>dir</code>.
     * <p>
     * This is equivalent to calling {@link #setLookAt(float, float, float, float, float, float, float, float, float)
     * setLookAt()} with <code>eye = (0, 0, 0)</code> and <code>center = dir</code>.
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
    public abstract Matrix4x3fc setLookAlong(float dirX, float dirY, float dirZ, float upX, float upY, float upZ);

    /**
     * Set this matrix to be a "lookat" transformation for a right-handed coordinate system, that aligns <code>-z</code>
     * with <code>center - eye</code>.
     * <p>
     * In order to not make use of vectors to specify <code>eye</code>, <code>center</code> and <code>up</code> but use
     * primitives, like in the GLU function, use {@link #setLookAt(float, float, float, float, float, float, float,
     * float, float) setLookAt()} instead.
     * <p>
     * In order to apply the lookat transformation to a previous existing transformation, use {@link #lookAt(IVector3f,
     * IVector3f, IVector3f) lookAt()}.
     *
     * @param eye    the position of the camera
     * @param center the point in space to look at
     * @param up     the direction of 'up'
     * @return this
     *
     * @see #setLookAt(float, float, float, float, float, float, float, float, float)
     * @see #lookAt(IVector3f, IVector3f, IVector3f)
     */
    public abstract Matrix4x3fc setLookAt(IVector3f eye, IVector3f center, IVector3f up);

    /**
     * Set this matrix to be a "lookat" transformation for a right-handed coordinate system, that aligns <code>-z</code>
     * with <code>center - eye</code>.
     * <p>
     * In order to apply the lookat transformation to a previous existing transformation, use {@link #lookAt(float,
     * float, float, float, float, float, float, float, float) lookAt}.
     *
     * @param eyeX    the x-coordinate of the eye/camera location
     * @param eyeY    the y-coordinate of the eye/camera location
     * @param eyeZ    the z-coordinate of the eye/camera location
     * @param centerX the x-coordinate of the point to look at
     * @param centerY the y-coordinate of the point to look at
     * @param centerZ the z-coordinate of the point to look at
     * @param upX     the x-coordinate of the up vector
     * @param upY     the y-coordinate of the up vector
     * @param upZ     the z-coordinate of the up vector
     * @return this
     *
     * @see #setLookAt(IVector3f, IVector3f, IVector3f)
     * @see #lookAt(float, float, float, float, float, float, float, float, float)
     */
    public abstract Matrix4x3fc setLookAt(float eyeX, float eyeY, float eyeZ,
                                          float centerX, float centerY, float centerZ,
                                          float upX, float upY, float upZ);

    /**
     * Apply a "lookat" transformation to this matrix for a right-handed coordinate system, that aligns <code>-z</code>
     * with <code>center - eye</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>L</code> the lookat matrix, then the new matrix will be
     * <code>M * L</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>M * L *
     * v</code>, the lookat transformation will be applied first!
     * <p>
     * In order to set the matrix to a lookat transformation without post-multiplying it, use {@link
     * #setLookAt(IVector3f, IVector3f, IVector3f)}.
     *
     * @param eye    the position of the camera
     * @param center the point in space to look at
     * @param up     the direction of 'up'
     * @return this
     *
     * @see #lookAt(float, float, float, float, float, float, float, float, float)
     * @see #setLookAlong(IVector3f, IVector3f)
     */
    public abstract Matrix4x3fc lookAt(IVector3f eye, IVector3f center, IVector3f up);

    /**
     * Apply a "lookat" transformation to this matrix for a right-handed coordinate system, that aligns <code>-z</code>
     * with <code>center - eye</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>L</code> the lookat matrix, then the new matrix will be
     * <code>M * L</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>M * L *
     * v</code>, the lookat transformation will be applied first!
     * <p>
     * In order to set the matrix to a lookat transformation without post-multiplying it, use {@link #setLookAt(float,
     * float, float, float, float, float, float, float, float) setLookAt()}.
     *
     * @param eyeX    the x-coordinate of the eye/camera location
     * @param eyeY    the y-coordinate of the eye/camera location
     * @param eyeZ    the z-coordinate of the eye/camera location
     * @param centerX the x-coordinate of the point to look at
     * @param centerY the y-coordinate of the point to look at
     * @param centerZ the z-coordinate of the point to look at
     * @param upX     the x-coordinate of the up vector
     * @param upY     the y-coordinate of the up vector
     * @param upZ     the z-coordinate of the up vector
     * @return this
     *
     * @see #lookAt(IVector3f, IVector3f, IVector3f)
     * @see #setLookAt(float, float, float, float, float, float, float, float, float)
     */
    public abstract Matrix4x3fc lookAt(float eyeX, float eyeY, float eyeZ,
                                       float centerX, float centerY, float centerZ,
                                       float upX, float upY, float upZ);

    /**
     * Set this matrix to be a "lookat" transformation for a left-handed coordinate system, that aligns <code>+z</code>
     * with <code>center - eye</code>.
     * <p>
     * In order to not make use of vectors to specify <code>eye</code>, <code>center</code> and <code>up</code> but use
     * primitives, like in the GLU function, use {@link #setLookAtLH(float, float, float, float, float, float, float,
     * float, float) setLookAtLH()} instead.
     * <p>
     * In order to apply the lookat transformation to a previous existing transformation, use {@link
     * #lookAtLH(IVector3f, IVector3f, IVector3f) lookAt()}.
     *
     * @param eye    the position of the camera
     * @param center the point in space to look at
     * @param up     the direction of 'up'
     * @return this
     *
     * @see #setLookAtLH(float, float, float, float, float, float, float, float, float)
     * @see #lookAtLH(IVector3f, IVector3f, IVector3f)
     */
    public abstract Matrix4x3fc setLookAtLH(IVector3f eye, IVector3f center, IVector3f up);

    /**
     * Set this matrix to be a "lookat" transformation for a left-handed coordinate system, that aligns <code>+z</code>
     * with <code>center - eye</code>.
     * <p>
     * In order to apply the lookat transformation to a previous existing transformation, use {@link #lookAtLH(float,
     * float, float, float, float, float, float, float, float) lookAtLH}.
     *
     * @param eyeX    the x-coordinate of the eye/camera location
     * @param eyeY    the y-coordinate of the eye/camera location
     * @param eyeZ    the z-coordinate of the eye/camera location
     * @param centerX the x-coordinate of the point to look at
     * @param centerY the y-coordinate of the point to look at
     * @param centerZ the z-coordinate of the point to look at
     * @param upX     the x-coordinate of the up vector
     * @param upY     the y-coordinate of the up vector
     * @param upZ     the z-coordinate of the up vector
     * @return this
     *
     * @see #setLookAtLH(IVector3f, IVector3f, IVector3f)
     * @see #lookAtLH(float, float, float, float, float, float, float, float, float)
     */
    public abstract Matrix4x3fc setLookAtLH(float eyeX, float eyeY, float eyeZ,
                                            float centerX, float centerY, float centerZ,
                                            float upX, float upY, float upZ);

    /**
     * Apply a "lookat" transformation to this matrix for a left-handed coordinate system, that aligns <code>+z</code>
     * with <code>center - eye</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>L</code> the lookat matrix, then the new matrix will be
     * <code>M * L</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>M * L *
     * v</code>, the lookat transformation will be applied first!
     * <p>
     * In order to set the matrix to a lookat transformation without post-multiplying it, use {@link
     * #setLookAtLH(IVector3f, IVector3f, IVector3f)}.
     *
     * @param eye    the position of the camera
     * @param center the point in space to look at
     * @param up     the direction of 'up'
     * @return this
     *
     * @see #lookAtLH(float, float, float, float, float, float, float, float, float)
     */
    public abstract Matrix4x3fc lookAtLH(IVector3f eye, IVector3f center, IVector3f up);

    /**
     * Apply a "lookat" transformation to this matrix for a left-handed coordinate system, that aligns <code>+z</code>
     * with <code>center - eye</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>L</code> the lookat matrix, then the new matrix will be
     * <code>M * L</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>M * L *
     * v</code>, the lookat transformation will be applied first!
     * <p>
     * In order to set the matrix to a lookat transformation without post-multiplying it, use {@link #setLookAtLH(float,
     * float, float, float, float, float, float, float, float) setLookAtLH()}.
     *
     * @param eyeX    the x-coordinate of the eye/camera location
     * @param eyeY    the y-coordinate of the eye/camera location
     * @param eyeZ    the z-coordinate of the eye/camera location
     * @param centerX the x-coordinate of the point to look at
     * @param centerY the y-coordinate of the point to look at
     * @param centerZ the z-coordinate of the point to look at
     * @param upX     the x-coordinate of the up vector
     * @param upY     the y-coordinate of the up vector
     * @param upZ     the z-coordinate of the up vector
     * @return this
     *
     * @see #lookAtLH(IVector3f, IVector3f, IVector3f)
     * @see #setLookAtLH(float, float, float, float, float, float, float, float, float)
     */
    public abstract Matrix4x3fc lookAtLH(float eyeX, float eyeY, float eyeZ,
                                         float centerX, float centerY, float centerZ,
                                         float upX, float upY, float upZ);

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
    public abstract Matrix4x3fc rotate(IQuaternionf quat);

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
    public abstract Matrix4x3fc rotateLocal(IQuaternionf quat);

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
    public abstract Matrix4x3fc rotate(AxisAngle4fc axisAngle);

    /**
     * Apply a rotation transformation, rotating the given radians about the specified axis, to this matrix.
     * <p>
     * The axis described by the <code>axis</code> vector needs to be a unit vector.
     * <p>
     * When used with a right-handed coordinate system, the produced rotation will rotate a vector counter-clockwise
     * around the rotation axis, when viewing along the negative axis direction towards the origin. When used with a
     * left-handed coordinate system, the rotation is clockwise.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>A</code> the rotation matrix obtained from the given
     * axis-angle, then the new matrix will be <code>M * A</code>. So when transforming a vector <code>v</code> with the
     * new matrix by using <code>M * A * v</code>, the axis-angle rotation will be applied first!
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
    public abstract Matrix4x3fc rotate(float angle, IVector3f axis);

    /**
     * Apply a mirror/reflection transformation to this matrix that reflects about the given plane specified via the
     * equation <tt>x*a + y*b + z*c + d = 0</tt>.
     * <p>
     * The vector <tt>(a, b, c)</tt> must be a unit vector.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the reflection matrix, then the new matrix will
     * be <code>M * R</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>M * R *
     * v</code>, the reflection will be applied first!
     * <p>
     * Reference: <a href="https://msdn.microsoft.com/en-us/library/windows/desktop/bb281733(v=vs.85).aspx">msdn.microsoft.com</a>
     *
     * @param a the x factor in the plane equation
     * @param b the y factor in the plane equation
     * @param c the z factor in the plane equation
     * @param d the constant in the plane equation
     * @return this
     */
    public abstract Matrix4x3fc reflect(float a, float b, float c, float d);

    /**
     * Apply a mirror/reflection transformation to this matrix that reflects about the given plane specified via the
     * plane normal and a point on the plane.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the reflection matrix, then the new matrix will
     * be <code>M * R</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>M * R *
     * v</code>, the reflection will be applied first!
     *
     * @param nx the x-coordinate of the plane normal
     * @param ny the y-coordinate of the plane normal
     * @param nz the z-coordinate of the plane normal
     * @param px the x-coordinate of a point on the plane
     * @param py the y-coordinate of a point on the plane
     * @param pz the z-coordinate of a point on the plane
     * @return this
     */
    public abstract Matrix4x3fc reflect(float nx, float ny, float nz, float px, float py, float pz);

    /**
     * Apply a mirror/reflection transformation to this matrix that reflects about the given plane specified via the
     * plane normal and a point on the plane.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the reflection matrix, then the new matrix will
     * be <code>M * R</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>M * R *
     * v</code>, the reflection will be applied first!
     *
     * @param normal the plane normal
     * @param point  a point on the plane
     * @return this
     */
    public abstract Matrix4x3fc reflect(IVector3f normal, IVector3f point);

    /**
     * Apply a mirror/reflection transformation to this matrix that reflects about a plane specified via the plane
     * orientation and a point on the plane.
     * <p>
     * This method can be used to build a reflection transformation based on the orientation of a mirror object in the
     * scene. It is assumed that the default mirror plane's normal is <tt>(0, 0, 1)</tt>. So, if the given {@link
     * IQuaternionf} is the identity (does not apply any additional rotation), the reflection plane will be
     * <tt>z=0</tt>, offset by the given <code>point</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the reflection matrix, then the new matrix will
     * be <code>M * R</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>M * R *
     * v</code>, the reflection will be applied first!
     *
     * @param orientation the plane orientation
     * @param point       a point on the plane
     * @return this
     */
    public abstract Matrix4x3fc reflect(IQuaternionf orientation, IVector3f point);

    /**
     * Set this matrix to a mirror/reflection transformation that reflects about the given plane specified via the
     * equation <tt>x*a + y*b + z*c + d = 0</tt>.
     * <p>
     * The vector <tt>(a, b, c)</tt> must be a unit vector.
     * <p>
     * Reference: <a href="https://msdn.microsoft.com/en-us/library/windows/desktop/bb281733(v=vs.85).aspx">msdn.microsoft.com</a>
     *
     * @param a the x factor in the plane equation
     * @param b the y factor in the plane equation
     * @param c the z factor in the plane equation
     * @param d the constant in the plane equation
     * @return this
     */
    public abstract Matrix4x3fc reflection(float a, float b, float c, float d);

    /**
     * Set this matrix to a mirror/reflection transformation that reflects about the given plane specified via the plane
     * normal and a point on the plane.
     *
     * @param nx the x-coordinate of the plane normal
     * @param ny the y-coordinate of the plane normal
     * @param nz the z-coordinate of the plane normal
     * @param px the x-coordinate of a point on the plane
     * @param py the y-coordinate of a point on the plane
     * @param pz the z-coordinate of a point on the plane
     * @return this
     */
    public abstract Matrix4x3fc reflection(float nx, float ny, float nz, float px, float py, float pz);

    /**
     * Set this matrix to a mirror/reflection transformation that reflects about the given plane specified via the plane
     * normal and a point on the plane.
     *
     * @param normal the plane normal
     * @param point  a point on the plane
     * @return this
     */
    public abstract Matrix4x3fc reflection(IVector3f normal, IVector3f point);

    /**
     * Set this matrix to a mirror/reflection transformation that reflects about a plane specified via the plane
     * orientation and a point on the plane.
     * <p>
     * This method can be used to build a reflection transformation based on the orientation of a mirror object in the
     * scene. It is assumed that the default mirror plane's normal is <tt>(0, 0, 1)</tt>. So, if the given {@link
     * IQuaternionf} is the identity (does not apply any additional rotation), the reflection plane will be
     * <tt>z=0</tt>, offset by the given <code>point</code>.
     *
     * @param orientation the plane orientation
     * @param point       a point on the plane
     * @return this
     */
    public abstract Matrix4x3fc reflection(IQuaternionf orientation, IVector3f point);

    /**
     * Set the row at the given <code>row</code> index, starting with <code>0</code>.
     *
     * @param row the row index in <tt>[0..2]</tt>
     * @param src the row components to set
     * @return this
     */
    public abstract Matrix4x3fc setRow(int row, IVector4f src);

    /**
     * Set the column at the given <code>column</code> index, starting with <code>0</code>.
     *
     * @param column the column index in <tt>[0..3]</tt>
     * @param src    the column components to set
     * @return this
     */
    public abstract Matrix4x3fc setColumn(int column, IVector3f src);

    /**
     * Compute a normal matrix from the left 3x3 submatrix of <code>this</code> and store it into the left 3x3 submatrix
     * of <code>this</code>. All other values of <code>this</code> will be set to {@link #identity() identity}.
     * <p>
     * The normal matrix of <tt>m</tt> is the transpose of the inverse of <tt>m</tt>.
     * <p>
     * Please note that, if <code>this</code> is an orthogonal matrix or a matrix whose columns are orthogonal vectors,
     * then this method <i>need not</i> be invoked, since in that case <code>this</code> itself is its normal matrix. In
     * that case, use {@link #set3x3(IMatrix4x3f)} to set a given Matrix4x3fc to only the left 3x3 submatrix of this
     * matrix.
     *
     * @return this
     *
     * @see #set3x3(IMatrix4x3f)
     */
    public abstract Matrix4x3fc normal();

    /**
     * Normalize the left 3x3 submatrix of this matrix.
     * <p>
     * The resulting matrix will map unit vectors to unit vectors, though a pair of orthogonal input unit vectors need
     * not be mapped to a pair of orthogonal output vectors if the original matrix was not orthogonal itself (i.e. had
     * <i>skewing</i>).
     *
     * @return this
     */
    public abstract Matrix4x3fc normalize3x3();

    /**
     * Apply a projection transformation to this matrix that projects onto the plane specified via the general plane
     * equation <tt>x*a + y*b + z*c + d = 0</tt> as if casting a shadow from a given light position/direction
     * <code>light</code>.
     * <p>
     * If <tt>light.w</tt> is <tt>0.0</tt> the light is being treated as a directional light; if it is <tt>1.0</tt> it
     * is a point light.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>S</code> the shadow matrix, then the new matrix will be
     * <code>M * S</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>M * S *
     * v</code>, the reflection will be applied first!
     * <p>
     * Reference: <a href="ftp://ftp.sgi.com/opengl/contrib/blythe/advanced99/notes/node192.html">ftp.sgi.com</a>
     *
     * @param light the light's vector
     * @param a     the x factor in the plane equation
     * @param b     the y factor in the plane equation
     * @param c     the z factor in the plane equation
     * @param d     the constant in the plane equation
     * @return this
     */
    public abstract Matrix4x3fc shadow(IVector4f light, float a, float b, float c, float d);

    /**
     * Apply a projection transformation to this matrix that projects onto the plane specified via the general plane
     * equation <tt>x*a + y*b + z*c + d = 0</tt> as if casting a shadow from a given light position/direction
     * <tt>(lightX, lightY, lightZ, lightW)</tt>.
     * <p>
     * If <code>lightW</code> is <tt>0.0</tt> the light is being treated as a directional light; if it is <tt>1.0</tt>
     * it is a point light.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>S</code> the shadow matrix, then the new matrix will be
     * <code>M * S</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>M * S *
     * v</code>, the reflection will be applied first!
     * <p>
     * Reference: <a href="ftp://ftp.sgi.com/opengl/contrib/blythe/advanced99/notes/node192.html">ftp.sgi.com</a>
     *
     * @param lightX the x-component of the light's vector
     * @param lightY the y-component of the light's vector
     * @param lightZ the z-component of the light's vector
     * @param lightW the w-component of the light's vector
     * @param a      the x factor in the plane equation
     * @param b      the y factor in the plane equation
     * @param c      the z factor in the plane equation
     * @param d      the constant in the plane equation
     * @return this
     */
    public abstract Matrix4x3fc shadow(float lightX, float lightY, float lightZ, float lightW, float a, float b, float c, float d);

    /**
     * Apply a projection transformation to this matrix that projects onto the plane with the general plane equation
     * <tt>y = 0</tt> as if casting a shadow from a given light position/direction <code>light</code>.
     * <p>
     * Before the shadow projection is applied, the plane is transformed via the specified
     * <code>planeTransformation</code>.
     * <p>
     * If <tt>light.w</tt> is <tt>0.0</tt> the light is being treated as a directional light; if it is <tt>1.0</tt> it
     * is a point light.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>S</code> the shadow matrix, then the new matrix will be
     * <code>M * S</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>M * S *
     * v</code>, the reflection will be applied first!
     *
     * @param light          the light's vector
     * @param planeTransform the transformation to transform the implied plane <tt>y = 0</tt> before applying the
     *                       projection
     * @return this
     */
    public abstract Matrix4x3fc shadow(IVector4f light, IMatrix4x3f planeTransform);

    /**
     * Apply a projection transformation to this matrix that projects onto the plane with the general plane equation
     * <tt>y = 0</tt> as if casting a shadow from a given light position/direction <tt>(lightX, lightY, lightZ,
     * lightW)</tt>.
     * <p>
     * Before the shadow projection is applied, the plane is transformed via the specified
     * <code>planeTransformation</code>.
     * <p>
     * If <code>lightW</code> is <tt>0.0</tt> the light is being treated as a directional light; if it is <tt>1.0</tt>
     * it is a point light.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>S</code> the shadow matrix, then the new matrix will be
     * <code>M * S</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>M * S *
     * v</code>, the reflection will be applied first!
     *
     * @param lightX         the x-component of the light vector
     * @param lightY         the y-component of the light vector
     * @param lightZ         the z-component of the light vector
     * @param lightW         the w-component of the light vector
     * @param planeTransform the transformation to transform the implied plane <tt>y = 0</tt> before applying the
     *                       projection
     * @return this
     */
    public abstract Matrix4x3fc shadow(float lightX, float lightY, float lightZ, float lightW, Matrix4x3fc planeTransform);

    /**
     * Set this matrix to a cylindrical billboard transformation that rotates the local +Z axis of a given object with
     * position <code>objPos</code> towards a target position at <code>targetPos</code> while constraining a cylindrical
     * rotation around the given <code>up</code> vector.
     * <p>
     * This method can be used to create the complete model transformation for a given object, including the translation
     * of the object to its position <code>objPos</code>.
     *
     * @param objPos    the position of the object to rotate towards <code>targetPos</code>
     * @param targetPos the position of the target (for example the camera) towards which to rotate the object
     * @param up        the rotation axis (must be {@link Vector3fc#normalize() normalized})
     * @return this
     */
    public abstract Matrix4x3fc billboardCylindrical(IVector3f objPos, IVector3f targetPos, IVector3f up);

    /**
     * Set this matrix to a spherical billboard transformation that rotates the local +Z axis of a given object with
     * position <code>objPos</code> towards a target position at <code>targetPos</code>.
     * <p>
     * This method can be used to create the complete model transformation for a given object, including the translation
     * of the object to its position <code>objPos</code>.
     * <p>
     * If preserving an <i>up</i> vector is not necessary when rotating the +Z axis, then a shortest arc rotation can be
     * obtained using {@link #billboardSpherical(IVector3f, IVector3f)}.
     *
     * @param objPos    the position of the object to rotate towards <code>targetPos</code>
     * @param targetPos the position of the target (for example the camera) towards which to rotate the object
     * @param up        the up axis used to orient the object
     * @return this
     *
     * @see #billboardSpherical(IVector3f, IVector3f)
     */
    public abstract Matrix4x3fc billboardSpherical(IVector3f objPos, IVector3f targetPos, IVector3f up);

    /**
     * Set this matrix to a spherical billboard transformation that rotates the local +Z axis of a given object with
     * position <code>objPos</code> towards a target position at <code>targetPos</code> using a shortest arc rotation by
     * not preserving any <i>up</i> vector of the object.
     * <p>
     * This method can be used to create the complete model transformation for a given object, including the translation
     * of the object to its position <code>objPos</code>.
     * <p>
     * In order to specify an <i>up</i> vector which needs to be maintained when rotating the +Z axis of the object, use
     * {@link #billboardSpherical(IVector3f, IVector3f, IVector3f)}.
     *
     * @param objPos    the position of the object to rotate towards <code>targetPos</code>
     * @param targetPos the position of the target (for example the camera) towards which to rotate the object
     * @return this
     *
     * @see #billboardSpherical(IVector3f, IVector3f, IVector3f)
     */
    public abstract Matrix4x3fc billboardSpherical(IVector3f objPos, IVector3f targetPos);

    public abstract int hashCode();

    public abstract boolean equals(Object obj);

    /**
     * Apply a picking transformation to this matrix using the given window coordinates <tt>(x, y)</tt> as the pick
     * center and the given <tt>(width, height)</tt> as the size of the picking region in window coordinates.
     *
     * @param x        the x coordinate of the picking region center in window coordinates
     * @param y        the y coordinate of the picking region center in window coordinates
     * @param width    the width of the picking region in window coordinates
     * @param height   the height of the picking region in window coordinates
     * @param viewport the viewport described by <tt>[x, y, width, height]</tt>
     * @return this
     */
    public abstract Matrix4x3fc pick(float x, float y, float width, float height, int[] viewport);

    /**
     * Exchange the values of <code>this</code> matrix with the given <code>other</code> matrix.
     *
     * @param other the other matrix to exchange the values with
     * @return this
     */
    public abstract Matrix4x3fc swap(Matrix4x3fc other);

    /**
     * Apply an arcball view transformation to this matrix with the given <code>radius</code> and center <tt>(centerX,
     * centerY, centerZ)</tt> position of the arcball and the specified X and Y rotation angles.
     * <p>
     * This method is equivalent to calling: <tt>translate(0, 0, -radius).rotateX(angleX).rotateY(angleY).translate(-centerX,
     * -centerY, -centerZ)</tt>
     *
     * @param radius  the arcball radius
     * @param centerX the x coordinate of the center position of the arcball
     * @param centerY the y coordinate of the center position of the arcball
     * @param centerZ the z coordinate of the center position of the arcball
     * @param angleX  the rotation angle around the X axis in radians
     * @param angleY  the rotation angle around the Y axis in radians
     * @return dest
     */
    public abstract Matrix4x3fc arcball(float radius, float centerX, float centerY, float centerZ, float angleX, float angleY);

    /**
     * Apply an arcball view transformation to this matrix with the given <code>radius</code> and <code>center</code>
     * position of the arcball and the specified X and Y rotation angles.
     * <p>
     * This method is equivalent to calling: <tt>translate(0, 0, -radius).rotateX(angleX).rotateY(angleY).translate(-center.x,
     * -center.y, -center.z)</tt>
     *
     * @param radius the arcball radius
     * @param center the center position of the arcball
     * @param angleX the rotation angle around the X axis in radians
     * @param angleY the rotation angle around the Y axis in radians
     * @return this
     */
    public abstract Matrix4x3fc arcball(float radius, IVector3f center, float angleX, float angleY);

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
    public abstract Matrix4x3fc lerp(IMatrix4x3f other, float t);

    /**
     * Apply a model transformation to this matrix for a right-handed coordinate system, that aligns the local
     * <code>+Z</code> axis with <code>dir</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>L</code> the lookat matrix, then the new matrix will be
     * <code>M * L</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>M * L *
     * v</code>, the lookat transformation will be applied first!
     * <p>
     * In order to set the matrix to a rotation transformation without post-multiplying it, use {@link
     * #rotationTowards(IVector3f, IVector3f) rotationTowards()}.
     * <p>
     * This method is equivalent to calling: <tt>mul(new Matrix4x3fc().lookAt(new IVector3f(), new
     * IVector3f(dir).negate(), up).invert())</tt>
     *
     * @param dir the direction to orient towards
     * @param up  the up vector
     * @return this
     *
     * @see #rotateTowards(float, float, float, float, float, float)
     * @see #rotationTowards(IVector3f, IVector3f)
     */
    public abstract Matrix4x3fc rotateTowards(IVector3f dir, IVector3f up);

    /**
     * Apply a model transformation to this matrix for a right-handed coordinate system, that aligns the local
     * <code>+Z</code> axis with <code>(dirX, dirY, dirZ)</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>L</code> the lookat matrix, then the new matrix will be
     * <code>M * L</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>M * L *
     * v</code>, the lookat transformation will be applied first!
     * <p>
     * In order to set the matrix to a rotation transformation without post-multiplying it, use {@link
     * #rotationTowards(float, float, float, float, float, float) rotationTowards()}.
     * <p>
     * This method is equivalent to calling: <tt>mul(new Matrix4x3fc().lookAt(0, 0, 0, -dirX, -dirY, -dirZ, upX, upY,
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
    public abstract Matrix4x3fc rotateTowards(float dirX, float dirY, float dirZ, float upX, float upY, float upZ);

    /**
     * Set this matrix to a model transformation for a right-handed coordinate system, that aligns the local
     * <code>-z</code> axis with <code>dir</code>.
     * <p>
     * In order to apply the rotation transformation to a previous existing transformation, use {@link
     * #rotateTowards(float, float, float, float, float, float) rotateTowards}.
     * <p>
     * This method is equivalent to calling: <tt>setLookAt(new IVector3f(), new IVector3f(dir).negate(),
     * up).invert()</tt>
     *
     * @param dir the direction to orient the local -z axis towards
     * @param up  the up vector
     * @return this
     *
     * @see #rotationTowards(IVector3f, IVector3f)
     * @see #rotateTowards(float, float, float, float, float, float)
     */
    public abstract Matrix4x3fc rotationTowards(IVector3f dir, IVector3f up);

    /**
     * Set this matrix to a model transformation for a right-handed coordinate system, that aligns the local
     * <code>-z</code> axis with <code>(dirX, dirY, dirZ)</code>.
     * <p>
     * In order to apply the rotation transformation to a previous existing transformation, use {@link
     * #rotateTowards(float, float, float, float, float, float) rotateTowards}.
     * <p>
     * This method is equivalent to calling: <tt>setLookAt(0, 0, 0, -dirX, -dirY, -dirZ, upX, upY, upZ).invert()</tt>
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
    public abstract Matrix4x3fc rotationTowards(float dirX, float dirY, float dirZ, float upX, float upY, float upZ);

    /**
     * Set this matrix to a model transformation for a right-handed coordinate system, that translates to the given
     * <code>pos</code> and aligns the local <code>-z</code> axis with <code>dir</code>.
     * <p>
     * This method is equivalent to calling: <tt>translation(pos).rotateTowards(dir, up)</tt>
     *
     * @param pos the position to translate to
     * @param dir the direction to rotate towards
     * @param up  the up vector
     * @return this
     *
     * @see #translation(IVector3f)
     * @see #rotateTowards(IVector3f, IVector3f)
     */
    public abstract Matrix4x3fc translationRotateTowards(IVector3f pos, IVector3f dir, IVector3f up);

    /**
     * Set this matrix to a model transformation for a right-handed coordinate system, that translates to the given
     * <code>(posX, posY, posZ)</code> and aligns the local <code>-z</code> axis with <code>(dirX, dirY, dirZ)</code>.
     * <p>
     * This method is equivalent to calling: <tt>translation(posX, posY, posZ).rotateTowards(dirX, dirY, dirZ, upX, upY,
     * upZ)</tt>
     *
     * @param posX the x-coordinate of the position to translate to
     * @param posY the y-coordinate of the position to translate to
     * @param posZ the z-coordinate of the position to translate to
     * @param dirX the x-coordinate of the direction to rotate towards
     * @param dirY the y-coordinate of the direction to rotate towards
     * @param dirZ the z-coordinate of the direction to rotate towards
     * @param upX  the x-coordinate of the up vector
     * @param upY  the y-coordinate of the up vector
     * @param upZ  the z-coordinate of the up vector
     * @return this
     *
     * @see #translation(float, float, float)
     * @see #rotateTowards(float, float, float, float, float, float)
     */
    public abstract Matrix4x3fc translationRotateTowards(float posX, float posY, float posZ, float dirX, float dirY, float dirZ, float upX, float upY, float upZ);
}
