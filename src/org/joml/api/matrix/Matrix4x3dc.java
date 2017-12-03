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
import org.joml.api.vector.*;

import java.io.Externalizable;
import java.text.DecimalFormat;
import java.text.NumberFormat;

//#ifdef __HAS_NIO__
import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
//#endif

/**
 * Contains the definition of an affine 4x3 matrix (4 columns, 3 rows) of doubles, and associated functions to transform
 * it. The matrix is column-major to match OpenGL's interpretation, and it looks like this:
 * <p>
 * m00  m10  m20  m30<br> m01  m11  m21  m31<br> m02  m12  m22  m32<br>
 *
 * @author Richard Greenlees
 * @author Kai Burjack
 */
public abstract class Matrix4x3dc implements Externalizable, IMatrix4x3d {

    /**
     * Set the value of the matrix element at column 0 and row 0
     *
     * @param m00 the new value
     * @return the value of the matrix element
     */
    public abstract Matrix4x3dc m00(double m00);

    /**
     * Set the value of the matrix element at column 0 and row 1
     *
     * @param m01 the new value
     * @return the value of the matrix element
     */
    public abstract Matrix4x3dc m01(double m01);

    /**
     * Set the value of the matrix element at column 0 and row 2
     *
     * @param m02 the new value
     * @return the value of the matrix element
     */
    public abstract Matrix4x3dc m02(double m02);

    /**
     * Set the value of the matrix element at column 1 and row 0
     *
     * @param m10 the new value
     * @return the value of the matrix element
     */
    public abstract Matrix4x3dc m10(double m10);

    /**
     * Set the value of the matrix element at column 1 and row 1
     *
     * @param m11 the new value
     * @return the value of the matrix element
     */
    public abstract Matrix4x3dc m11(double m11);

    /**
     * Set the value of the matrix element at column 1 and row 2
     *
     * @param m12 the new value
     * @return the value of the matrix element
     */
    public abstract Matrix4x3dc m12(double m12);

    /**
     * Set the value of the matrix element at column 2 and row 0
     *
     * @param m20 the new value
     * @return the value of the matrix element
     */
    public abstract Matrix4x3dc m20(double m20);

    /**
     * Set the value of the matrix element at column 2 and row 1
     *
     * @param m21 the new value
     * @return the value of the matrix element
     */
    public abstract Matrix4x3dc m21(double m21);

    /**
     * Set the value of the matrix element at column 2 and row 2
     *
     * @param m22 the new value
     * @return the value of the matrix element
     */
    public abstract Matrix4x3dc m22(double m22);

    /**
     * Set the value of the matrix element at column 3 and row 0
     *
     * @param m30 the new value
     * @return the value of the matrix element
     */
    public abstract Matrix4x3dc m30(double m30);

    /**
     * Set the value of the matrix element at column 3 and row 1
     *
     * @param m31 the new value
     * @return the value of the matrix element
     */
    public abstract Matrix4x3dc m31(double m31);

    /**
     * Set the value of the matrix element at column 3 and row 2
     *
     * @param m32 the new value
     * @return the value of the matrix element
     */
    public abstract Matrix4x3dc m32(double m32);

    /**
     * Sets the property of the matrix.
     *
     * @param properties the property to be set.
     * @return this
     */
    public abstract Matrix4x3dc properties(int properties);

    /**
     * Reset this matrix to the identity.
     * <p>
     * Please note that if a call to {@link #identity()} is immediately followed by a call to: {@link #translate(double,
     * double, double) translate}, {@link #rotate(double, double, double, double) rotate}, {@link #scale(double, double,
     * double) scale}, {@link #ortho(double, double, double, double, double, double) ortho}, {@link #ortho2D(double,
     * double, double, double) ortho2D}, {@link #lookAt(double, double, double, double, double, double, double, double,
     * double) lookAt}, {@link #lookAlong(double, double, double, double, double, double) lookAlong}, or any of their
     * overloads, then the call to {@link #identity()} can be omitted and the subsequent call replaced with: {@link
     * #translation(double, double, double) translation}, {@link #rotation(double, double, double, double) rotation},
     * {@link #scaling(double, double, double) scaling}, {@link #setOrtho(double, double, double, double, double,
     * double) setOrtho}, {@link #setOrtho2D(double, double, double, double) setOrtho2D}, {@link #setLookAt(double,
     * double, double, double, double, double, double, double, double) setLookAt}, {@link #setLookAlong(double, double,
     * double, double, double, double) setLookAlong}, or any of their overloads.
     *
     * @return this
     */
    public abstract Matrix4x3dc identity();

    /**
     * Store the values of the given matrix <code>m</code> into <code>this</code> matrix.
     *
     * @param m the matrix to copy the values from
     * @return this
     */
    public abstract Matrix4x3dc set(IMatrix4x3d m);

    /**
     * Store the values of the given matrix <code>m</code> into <code>this</code> matrix.
     *
     * @param m the matrix to copy the values from
     * @return this
     */
    public abstract Matrix4x3dc set(IMatrix4x3f m);

    /**
     * Store the values of the upper 4x3 submatrix of <code>m</code> into <code>this</code> matrix.
     *
     * @param m the matrix to copy the values from
     * @return this
     *
     * @see IMatrix4d#get4x3(Matrix4x3dc)
     */
    public abstract Matrix4x3dc set(IMatrix4d m);

    /**
     * Set the left 3x3 submatrix of this {@link Matrix4x3dc} to the given {@link IMatrix3d} and the rest to identity.
     *
     * @param mat the {@link IMatrix3d}
     * @return this
     */
    public abstract Matrix4x3dc set(IMatrix3d mat);

    /**
     * Set the four columns of this matrix to the supplied vectors, respectively.
     *
     * @param col0 the first column
     * @param col1 the second column
     * @param col2 the third column
     * @param col3 the fourth column
     * @return this
     */
    public abstract Matrix4x3dc set(IVector3d col0,
                                    IVector3d col1,
                                    IVector3d col2,
                                    IVector3d col3);

    /**
     * Set the left 3x3 submatrix of this {@link Matrix4x3dc} to that of the given {@link IMatrix4x3d} and don't change
     * the other elements.
     *
     * @param mat the {@link IMatrix4x3d}
     * @return this
     */
    public abstract Matrix4x3dc set3x3(IMatrix4x3d mat);

    /**
     * Set this matrix to be equivalent to the rotation specified by the given {@link AxisAngle4fc}.
     *
     * @param axisAngle the {@link AxisAngle4fc}
     * @return this
     */
    public abstract Matrix4x3dc set(AxisAngle4fc axisAngle);

    /**
     * Set this matrix to be equivalent to the rotation specified by the given {@link AxisAngle4dc}.
     *
     * @param axisAngle the {@link AxisAngle4dc}
     * @return this
     */
    public abstract Matrix4x3dc set(AxisAngle4dc axisAngle);

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
    public abstract Matrix4x3dc set(IQuaternionf q);

    /**
     * Set this matrix to be equivalent to the rotation - and possibly scaling - specified by the given {@link
     * IQuaterniond}.
     * <p>
     * This method is equivalent to calling: <tt>rotation(q)</tt>
     *
     * @param q the {@link IQuaterniond}
     * @return this
     */
    public abstract Matrix4x3dc set(IQuaterniond q);

    /**
     * Multiply this matrix by the supplied <code>right</code> matrix.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the <code>right</code> matrix, then the new
     * matrix will be <code>M * R</code>. So when transforming a vector <code>v</code> with the new matrix by using
     * <code>M * R * v</code>, the transformation of the right matrix will be applied first!
     *
     * @param right the right operand of the multiplication
     * @return this
     */
    public abstract Matrix4x3dc mul(IMatrix4x3d right);

    /**
     * Multiply this matrix by the supplied <code>right</code> matrix.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the <code>right</code> matrix, then the new
     * matrix will be <code>M * R</code>. So when transforming a vector <code>v</code> with the new matrix by using
     * <code>M * R * v</code>, the transformation of the right matrix will be applied first!
     *
     * @param right the right operand of the multiplication
     * @return this
     */
    public abstract Matrix4x3dc mul(IMatrix4x3f right);

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
    public abstract Matrix4x3dc mulOrtho(IMatrix4x3d view);

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
    public abstract Matrix4x3dc fma(IMatrix4x3d other, double otherFactor);

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
    public abstract Matrix4x3dc fma(IMatrix4x3f other, double otherFactor);

    /**
     * Component-wise add <code>this</code> and <code>other</code>.
     *
     * @param other the other addend
     * @return this
     */
    public abstract Matrix4x3dc add(IMatrix4x3d other);

    /**
     * Component-wise add <code>this</code> and <code>other</code>.
     *
     * @param other the other addend
     * @return this
     */
    public abstract Matrix4x3dc add(IMatrix4x3f other);

    /**
     * Component-wise subtract <code>subtrahend</code> from <code>this</code>.
     *
     * @param subtrahend the subtrahend
     * @return this
     */
    public abstract Matrix4x3dc sub(IMatrix4x3d subtrahend);

    /**
     * Component-wise subtract <code>subtrahend</code> from <code>this</code>.
     *
     * @param subtrahend the subtrahend
     * @return this
     */
    public abstract Matrix4x3dc sub(IMatrix4x3f subtrahend);

    /**
     * Component-wise multiply <code>this</code> by <code>other</code>.
     *
     * @param other the other matrix
     * @return this
     */
    public abstract Matrix4x3dc mulComponentWise(IMatrix4x3d other);

    /**
     * Set the values within this matrix to the supplied double values. The matrix will look like this:<br><br>
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
    public abstract Matrix4x3dc set(double m00, double m01, double m02,
                                    double m10, double m11, double m12,
                                    double m20, double m21, double m22,
                                    double m30, double m31, double m32);

    /**
     * Set the values in the matrix using a double array that contains the matrix elements in column-major order.
     * <p>
     * The results will look like this:<br><br>
     * <p>
     * 0, 3, 6, 9<br> 1, 4, 7, 10<br> 2, 5, 8, 11<br>
     *
     * @param m   the array to read the matrix values from
     * @param off the offset into the array
     * @return this
     *
     * @see #set(double[])
     */
    public abstract Matrix4x3dc set(double m[], int off);

    /**
     * Set the values in the matrix using a double array that contains the matrix elements in column-major order.
     * <p>
     * The results will look like this:<br><br>
     * <p>
     * 0, 3, 6, 9<br> 1, 4, 7, 10<br> 2, 5, 8, 11<br>
     *
     * @param m the array to read the matrix values from
     * @return this
     *
     * @see #set(double[], int)
     */
    public abstract Matrix4x3dc set(double m[]);

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
    public abstract Matrix4x3dc set(float m[], int off);

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
    public abstract Matrix4x3dc set(float m[]);

    //#ifdef __HAS_NIO__

    /**
     * Set the values of this matrix by reading 12 double values from the given {@link DoubleBuffer} in column-major
     * order, starting at its current position.
     * <p>
     * The DoubleBuffer is expected to contain the values in column-major order.
     * <p>
     * The position of the DoubleBuffer will not be changed by this method.
     *
     * @param buffer the DoubleBuffer to read the matrix values from in column-major order
     * @return this
     */
    public abstract Matrix4x3dc set(DoubleBuffer buffer);

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
    public abstract Matrix4x3dc set(FloatBuffer buffer);

    /**
     * Set the values of this matrix by reading 12 double values from the given {@link ByteBuffer} in column-major
     * order, starting at its current position.
     * <p>
     * The ByteBuffer is expected to contain the values in column-major order.
     * <p>
     * The position of the ByteBuffer will not be changed by this method.
     *
     * @param buffer the ByteBuffer to read the matrix values from in column-major order
     * @return this
     */
    public abstract Matrix4x3dc set(ByteBuffer buffer);

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
    public abstract Matrix4x3dc setFloats(ByteBuffer buffer);
    //#endif

    /**
     * Invert this matrix.
     *
     * @return this
     */
    public abstract Matrix4x3dc invert();

    /**
     * Invert <code>this</code> orthographic projection matrix.
     * <p>
     * This method can be used to quickly obtain the inverse of an orthographic projection matrix.
     *
     * @return this
     */
    public abstract Matrix4x3dc invertOrtho();

    /**
     * Invert this matrix by assuming that it has unit scaling (i.e. {@link #transformDirection(Vector3dc)
     * transformDirection} does not change the {@link IVector3d#length() length} of the vector).
     * <p>
     * Reference: <a href="http://www.gamedev.net/topic/425118-inverse--matrix/">http://www.gamedev.net/</a>
     *
     * @return this
     */
    public abstract Matrix4x3dc invertUnitScale();

    /**
     * Transpose only the left 3x3 submatrix of this matrix and set the rest of the matrix elements to identity.
     *
     * @return this
     */
    public abstract Matrix4x3dc transpose3x3();

    /**
     * Set this matrix to be a simple translation matrix.
     * <p>
     * The resulting matrix can be multiplied against another transformation matrix to obtain an additional
     * translation.
     *
     * @param x the offset to translate in x
     * @param y the offset to translate in y
     * @param z the offset to translate in z
     * @return this
     */
    public abstract Matrix4x3dc translation(double x, double y, double z);

    /**
     * Set this matrix to be a simple translation matrix.
     * <p>
     * The resulting matrix can be multiplied against another transformation matrix to obtain an additional
     * translation.
     *
     * @param offset the offsets in x, y and z to translate
     * @return this
     */
    public abstract Matrix4x3dc translation(IVector3f offset);

    /**
     * Set this matrix to be a simple translation matrix.
     * <p>
     * The resulting matrix can be multiplied against another transformation matrix to obtain an additional
     * translation.
     *
     * @param offset the offsets in x, y and z to translate
     * @return this
     */
    public abstract Matrix4x3dc translation(IVector3d offset);

    /**
     * Set only the translation components <tt>(m30, m31, m32)</tt> of this matrix to the given values <tt>(x, y,
     * z)</tt>.
     * <p>
     * To build a translation matrix instead, use {@link #translation(double, double, double)}. To apply a translation,
     * use {@link #translate(double, double, double)}.
     *
     * @param x the units to translate in x
     * @param y the units to translate in y
     * @param z the units to translate in z
     * @return this
     *
     * @see #translation(double, double, double)
     * @see #translate(double, double, double)
     */
    public abstract Matrix4x3dc setTranslation(double x, double y, double z);

    /**
     * Set only the translation components <tt>(m30, m31, m32)</tt> of this matrix to the given values <tt>(xyz.x,
     * xyz.y, xyz.z)</tt>.
     * <p>
     * To build a translation matrix instead, use {@link #translation(IVector3d)}. To apply a translation, use {@link
     * #translate(IVector3d)}.
     *
     * @param xyz the units to translate in <tt>(x, y, z)</tt>
     * @return this
     *
     * @see #translation(IVector3d)
     * @see #translate(IVector3d)
     */
    public abstract Matrix4x3dc setTranslation(IVector3d xyz);

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

    /**
     * Set all the values within this matrix to 0.
     *
     * @return this
     */
    public abstract Matrix4x3dc zero();

    /**
     * Set this matrix to be a simple scale matrix, which scales all axes uniformly by the given factor.
     * <p>
     * The resulting matrix can be multiplied against another transformation matrix to obtain an additional scaling.
     * <p>
     * In order to post-multiply a scaling transformation directly to a matrix, use {@link #scale(double) scale()}
     * instead.
     *
     * @param factor the scale factor in x, y and z
     * @return this
     *
     * @see #scale(double)
     */
    public abstract Matrix4x3dc scaling(double factor);

    /**
     * Set this matrix to be a simple scale matrix.
     *
     * @param x the scale in x
     * @param y the scale in y
     * @param z the scale in z
     * @return this
     */
    public abstract Matrix4x3dc scaling(double x, double y, double z);

    /**
     * Set this matrix to be a simple scale matrix which scales the base axes by <tt>xyz.x</tt>, <tt>xyz.y</tt> and
     * <tt>xyz.z</tt>, respectively.
     * <p>
     * The resulting matrix can be multiplied against another transformation matrix to obtain an additional scaling.
     * <p>
     * In order to post-multiply a scaling transformation directly to a matrix use {@link #scale(IVector3d) scale()}
     * instead.
     *
     * @param xyz the scale in x, y and z, respectively
     * @return this
     *
     * @see #scale(IVector3d)
     */
    public abstract Matrix4x3dc scaling(IVector3d xyz);

    /**
     * Set this matrix to a rotation matrix which rotates the given radians about a given axis.
     * <p>
     * When used with a right-handed coordinate system, the produced rotation will rotate a vector counter-clockwise
     * around the rotation axis, when viewing along the negative axis direction towards the origin. When used with a
     * left-handed coordinate system, the rotation is clockwise.
     * <p>
     * From <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Rotation_matrix_from_axis_and_angle">Wikipedia</a>
     *
     * @param angle the angle in radians
     * @param x     the x-coordinate of the axis to rotate about
     * @param y     the y-coordinate of the axis to rotate about
     * @param z     the z-coordinate of the axis to rotate about
     * @return this
     */
    public abstract Matrix4x3dc rotation(double angle, double x, double y, double z);

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
    public abstract Matrix4x3dc rotationX(double ang);

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
    public abstract Matrix4x3dc rotationY(double ang);

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
    public abstract Matrix4x3dc rotationZ(double ang);

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
    public abstract Matrix4x3dc rotationXYZ(double angleX, double angleY, double angleZ);

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
    public abstract Matrix4x3dc rotationZYX(double angleZ, double angleY, double angleX);

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
    public abstract Matrix4x3dc rotationYXZ(double angleY, double angleX, double angleZ);

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
    public abstract Matrix4x3dc setRotationXYZ(double angleX, double angleY, double angleZ);

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
    public abstract Matrix4x3dc setRotationZYX(double angleZ, double angleY, double angleX);

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
    public abstract Matrix4x3dc setRotationYXZ(double angleY, double angleX, double angleZ);

    /**
     * Set this matrix to a rotation matrix which rotates the given radians about a given axis.
     * <p>
     * The axis described by the <code>axis</code> vector needs to be a unit vector.
     * <p>
     * When used with a right-handed coordinate system, the produced rotation will rotate a vector counter-clockwise
     * around the rotation axis, when viewing along the negative axis direction towards the origin. When used with a
     * left-handed coordinate system, the rotation is clockwise.
     *
     * @param angle the angle in radians
     * @param axis  the axis to rotate about
     * @return this
     */
    public abstract Matrix4x3dc rotation(double angle, IVector3d axis);

    /**
     * Set this matrix to a rotation matrix which rotates the given radians about a given axis.
     * <p>
     * The axis described by the <code>axis</code> vector needs to be a unit vector.
     * <p>
     * When used with a right-handed coordinate system, the produced rotation will rotate a vector counter-clockwise
     * around the rotation axis, when viewing along the negative axis direction towards the origin. When used with a
     * left-handed coordinate system, the rotation is clockwise.
     *
     * @param angle the angle in radians
     * @param axis  the axis to rotate about
     * @return this
     */
    public abstract Matrix4x3dc rotation(double angle, IVector3f axis);

    /**
     * Set the left 3x3 submatrix of this {@link Matrix4x3dc} to the given {@link IMatrix3d} and don't change the other
     * elements.
     *
     * @param mat the 3x3 matrix
     * @return this
     */
    public abstract Matrix4x3dc set3x3(IMatrix3d mat);

    /**
     * Set the left 3x3 submatrix of this {@link Matrix4x3dc} to the given {@link IMatrix3f} and don't change the other
     * elements.
     *
     * @param mat the 3x3 matrix
     * @return this
     */
    public abstract Matrix4x3dc set3x3(IMatrix3f mat);

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
    public abstract Matrix4x3dc scale(IVector3d xyz);

    /**
     * Apply scaling to <code>this</code> matrix by scaling the base axes by the given x, y and z factors.
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
    public abstract Matrix4x3dc scale(double x, double y, double z);

    /**
     * Apply scaling to this matrix by uniformly scaling all base axes by the given xyz factor.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>S</code> the scaling matrix, then the new matrix will be
     * <code>M * S</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>M * S *
     * v</code> , the scaling will be applied first!
     *
     * @param xyz the factor for all components
     * @return this
     *
     * @see #scale(double, double, double)
     */
    public abstract Matrix4x3dc scale(double xyz);

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
    public abstract Matrix4x3dc scaleLocal(double x, double y, double z);

    /**
     * Apply rotation to this matrix by rotating the given amount of radians about the given axis specified as x, y and
     * z components.
     * <p>
     * When used with a right-handed coordinate system, the produced rotation will rotate a vector counter-clockwise
     * around the rotation axis, when viewing along the negative axis direction towards the origin. When used with a
     * left-handed coordinate system, the rotation is clockwise.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the rotation matrix, then the new matrix will be
     * <code>M * R</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>M * R *
     * v</code> , the rotation will be applied first!
     * <p>
     * In order to set the matrix to a rotation matrix without post-multiplying the rotation transformation, use {@link
     * #rotation(double, double, double, double) rotation()}.
     *
     * @param ang the angle is in radians
     * @param x   the x component of the axis
     * @param y   the y component of the axis
     * @param z   the z component of the axis
     * @return this
     *
     * @see #rotation(double, double, double, double)
     */
    public abstract Matrix4x3dc rotate(double ang, double x, double y, double z);

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
     * #rotation(double, double, double, double) rotation()}.
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Rotation_matrix_from_axis_and_angle">http://en.wikipedia.org</a>
     *
     * @param ang the angle in radians
     * @param x   the x component of the axis
     * @param y   the y component of the axis
     * @param z   the z component of the axis
     * @return this
     *
     * @see #rotation(double, double, double, double)
     */
    public abstract Matrix4x3dc rotateLocal(double ang, double x, double y, double z);

    /**
     * Apply a translation to this matrix by translating by the given number of units in x, y and z.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>T</code> the translation matrix, then the new matrix will
     * be <code>M * T</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>M * T *
     * v</code>, the translation will be applied first!
     * <p>
     * In order to set the matrix to a translation transformation without post-multiplying it, use {@link
     * #translation(IVector3d)}.
     *
     * @param offset the number of units in x, y and z by which to translate
     * @return this
     *
     * @see #translation(IVector3d)
     */
    public abstract Matrix4x3dc translate(IVector3d offset);

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
    public abstract Matrix4x3dc translate(IVector3f offset);

    /**
     * Apply a translation to this matrix by translating by the given number of units in x, y and z.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>T</code> the translation matrix, then the new matrix will
     * be <code>M * T</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>M * T *
     * v</code>, the translation will be applied first!
     * <p>
     * In order to set the matrix to a translation transformation without post-multiplying it, use {@link
     * #translation(double, double, double)}.
     *
     * @param x the offset to translate in x
     * @param y the offset to translate in y
     * @param z the offset to translate in z
     * @return this
     *
     * @see #translation(double, double, double)
     */
    public abstract Matrix4x3dc translate(double x, double y, double z);

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
    public abstract Matrix4x3dc translateLocal(IVector3f offset);

    /**
     * Pre-multiply a translation to this matrix by translating by the given number of units in x, y and z.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>T</code> the translation matrix, then the new matrix will
     * be <code>T * M</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>T * M *
     * v</code>, the translation will be applied last!
     * <p>
     * In order to set the matrix to a translation transformation without pre-multiplying it, use {@link
     * #translation(IVector3d)}.
     *
     * @param offset the number of units in x, y and z by which to translate
     * @return this
     *
     * @see #translation(IVector3d)
     */
    public abstract Matrix4x3dc translateLocal(IVector3d offset);

    /**
     * Pre-multiply a translation to this matrix by translating by the given number of units in x, y and z.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>T</code> the translation matrix, then the new matrix will
     * be <code>T * M</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>T * M *
     * v</code>, the translation will be applied last!
     * <p>
     * In order to set the matrix to a translation transformation without pre-multiplying it, use {@link
     * #translation(double, double, double)}.
     *
     * @param x the offset to translate in x
     * @param y the offset to translate in y
     * @param z the offset to translate in z
     * @return this
     *
     * @see #translation(double, double, double)
     */
    public abstract Matrix4x3dc translateLocal(double x, double y, double z);

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
    public abstract Matrix4x3dc rotateX(double ang);

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
    public abstract Matrix4x3dc rotateY(double ang);

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
    public abstract Matrix4x3dc rotateZ(double ang);

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
    public abstract Matrix4x3dc rotateXYZ(Vector3dc angles);

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
    public abstract Matrix4x3dc rotateXYZ(double angleX, double angleY, double angleZ);

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
    public abstract Matrix4x3dc rotateZYX(Vector3dc angles);

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
    public abstract Matrix4x3dc rotateZYX(double angleZ, double angleY, double angleX);

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
    public abstract Matrix4x3dc rotateYXZ(Vector3dc angles);

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
    public abstract Matrix4x3dc rotateYXZ(double angleY, double angleX, double angleZ);

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
     * @param angleAxis the {@link AxisAngle4fc} (needs to be {@link AxisAngle4fc#normalize() normalized})
     * @return this
     *
     * @see #rotate(AxisAngle4fc)
     */
    public abstract Matrix4x3dc rotation(AxisAngle4fc angleAxis);

    /**
     * Set this matrix to a rotation transformation using the given {@link AxisAngle4dc}.
     * <p>
     * When used with a right-handed coordinate system, the produced rotation will rotate a vector counter-clockwise
     * around the rotation axis, when viewing along the negative axis direction towards the origin. When used with a
     * left-handed coordinate system, the rotation is clockwise.
     * <p>
     * The resulting matrix can be multiplied against another transformation matrix to obtain an additional rotation.
     * <p>
     * In order to apply the rotation transformation to an existing transformation, use {@link #rotate(AxisAngle4dc)
     * rotate()} instead.
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Axis_and_angle">http://en.wikipedia.org</a>
     *
     * @param angleAxis the {@link AxisAngle4dc} (needs to be {@link AxisAngle4dc#normalize() normalized})
     * @return this
     *
     * @see #rotate(AxisAngle4dc)
     */
    public abstract Matrix4x3dc rotation(AxisAngle4dc angleAxis);

    /**
     * Set this matrix to the rotation - and possibly scaling - transformation of the given {@link IQuaterniond}.
     * <p>
     * When used with a right-handed coordinate system, the produced rotation will rotate a vector counter-clockwise
     * around the rotation axis, when viewing along the negative axis direction towards the origin. When used with a
     * left-handed coordinate system, the rotation is clockwise.
     * <p>
     * The resulting matrix can be multiplied against another transformation matrix to obtain an additional rotation.
     * <p>
     * In order to apply the rotation transformation to an existing transformation, use {@link #rotate(IQuaterniond)
     * rotate()} instead.
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Quaternion">http://en.wikipedia.org</a>
     *
     * @param quat the {@link IQuaterniond}
     * @return this
     *
     * @see #rotate(IQuaterniond)
     */
    public abstract Matrix4x3dc rotation(IQuaterniond quat);

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
    public abstract Matrix4x3dc rotation(IQuaternionf quat);

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
     * @see #translation(double, double, double)
     * @see #rotate(IQuaterniond)
     * @see #scale(double, double, double)
     */
    public abstract Matrix4x3dc translationRotateScale(double tx, double ty, double tz,
                                                       double qx, double qy, double qz, double qw,
                                                       double sx, double sy, double sz);

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
    public abstract Matrix4x3dc translationRotateScale(IVector3f translation,
                                                       IQuaternionf quat,
                                                       IVector3f scale);

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
     * @see #translation(IVector3d)
     * @see #rotate(IQuaterniond)
     */
    public abstract Matrix4x3dc translationRotateScale(IVector3d translation,
                                                       IQuaterniond quat,
                                                       IVector3d scale);

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
     * @see #translation(double, double, double)
     * @see #rotate(IQuaterniond)
     * @see #scale(double, double, double)
     * @see #mul(IMatrix4x3d)
     */
    public abstract Matrix4x3dc translationRotateScaleMul(
            double tx, double ty, double tz,
            double qx, double qy, double qz, double qw,
            double sx, double sy, double sz,
            IMatrix4x3d m);

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
     * @see #translation(IVector3d)
     * @see #rotate(IQuaterniond)
     * @see #mul(IMatrix4x3d)
     */
    public abstract Matrix4x3dc translationRotateScaleMul(IVector3d translation, IQuaterniond quat, IVector3d scale, IMatrix4x3d m);

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
     * @see #translation(double, double, double)
     * @see #rotate(IQuaterniond)
     */
    public abstract Matrix4x3dc translationRotate(double tx, double ty, double tz, IQuaterniond quat);

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
     * @see #translation(double, double, double)
     * @see #rotate(IQuaternionf)
     * @see #mul(IMatrix4x3d)
     */
    public abstract Matrix4x3dc translationRotateMul(double tx, double ty, double tz, IQuaternionf quat, IMatrix4x3d mat);

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
     * @see #translation(double, double, double)
     * @see #rotate(IQuaternionf)
     * @see #mul(IMatrix4x3d)
     */
    public abstract Matrix4x3dc translationRotateMul(double tx, double ty, double tz, double qx, double qy, double qz, double qw, IMatrix4x3d mat);

    /**
     * Apply the rotation - and possibly scaling - transformation of the given {@link IQuaterniond} to this matrix.
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
     * #rotation(IQuaterniond)}.
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Quaternion">http://en.wikipedia.org</a>
     *
     * @param quat the {@link IQuaterniond}
     * @return this
     *
     * @see #rotation(IQuaterniond)
     */
    public abstract Matrix4x3dc rotate(IQuaterniond quat);

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
    public abstract Matrix4x3dc rotate(IQuaternionf quat);

    /**
     * Pre-multiply the rotation transformation of the given {@link IQuaterniond} to this matrix.
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
     * #rotation(IQuaterniond)}.
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Quaternion">http://en.wikipedia.org</a>
     *
     * @param quat the {@link IQuaterniond}
     * @return this
     *
     * @see #rotation(IQuaterniond)
     */
    public abstract Matrix4x3dc rotateLocal(IQuaterniond quat);

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
    public abstract Matrix4x3dc rotateLocal(IQuaternionf quat);

    /**
     * Apply a rotation transformation, rotating about the given {@link AxisAngle4fc}, to this matrix.
     * <p>
     * The axis described by the <code>axis</code> vector needs to be a unit vector.
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
     * @see #rotate(double, double, double, double)
     * @see #rotation(AxisAngle4fc)
     */
    public abstract Matrix4x3dc rotate(AxisAngle4fc axisAngle);

    /**
     * Apply a rotation transformation, rotating about the given {@link AxisAngle4dc}, to this matrix.
     * <p>
     * When used with a right-handed coordinate system, the produced rotation will rotate a vector counter-clockwise
     * around the rotation axis, when viewing along the negative axis direction towards the origin. When used with a
     * left-handed coordinate system, the rotation is clockwise.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>A</code> the rotation matrix obtained from the given
     * {@link AxisAngle4dc}, then the new matrix will be <code>M * A</code>. So when transforming a vector
     * <code>v</code> with the new matrix by using <code>M * A * v</code>, the {@link AxisAngle4dc} rotation will be
     * applied first!
     * <p>
     * In order to set the matrix to a rotation transformation without post-multiplying, use {@link
     * #rotation(AxisAngle4dc)}.
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Axis_and_angle">http://en.wikipedia.org</a>
     *
     * @param axisAngle the {@link AxisAngle4dc} (needs to be {@link AxisAngle4dc#normalize() normalized})
     * @return this
     *
     * @see #rotate(double, double, double, double)
     * @see #rotation(AxisAngle4dc)
     */
    public abstract Matrix4x3dc rotate(AxisAngle4dc axisAngle);

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
     * In order to set the matrix to a rotation transformation without post-multiplying, use {@link #rotation(double,
     * IVector3d)}.
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Axis_and_angle">http://en.wikipedia.org</a>
     *
     * @param angle the angle in radians
     * @param axis  the rotation axis (needs to be {@link Vector3dc#normalize() normalized})
     * @return this
     *
     * @see #rotate(double, double, double, double)
     * @see #rotation(double, IVector3d)
     */
    public abstract Matrix4x3dc rotate(double angle, IVector3d axis);

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
     * In order to set the matrix to a rotation transformation without post-multiplying, use {@link #rotation(double,
     * IVector3f)}.
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Axis_and_angle">http://en.wikipedia.org</a>
     *
     * @param angle the angle in radians
     * @param axis  the rotation axis (needs to be {@link Vector3fc#normalize() normalized})
     * @return this
     *
     * @see #rotate(double, double, double, double)
     * @see #rotation(double, IVector3f)
     */
    public abstract Matrix4x3dc rotate(double angle, IVector3f axis);

    /**
     * Set the row at the given <code>row</code> index, starting with <code>0</code>.
     *
     * @param row the row index in <tt>[0..2]</tt>
     * @param src the row components to set
     * @return this
     */
    public abstract Matrix4x3dc setRow(int row, IVector4d src);

    /**
     * Set the column at the given <code>column</code> index, starting with <code>0</code>.
     *
     * @param column the column index in <tt>[0..3]</tt>
     * @param src    the column components to set
     * @return this
     */
    public abstract Matrix4x3dc setColumn(int column, IVector3d src);

    /**
     * Compute a normal matrix from the left 3x3 submatrix of <code>this</code> and store it into the left 3x3 submatrix
     * of <code>this</code>. All other values of <code>this</code> will be set to {@link #identity() identity}.
     * <p>
     * The normal matrix of <tt>m</tt> is the transpose of the inverse of <tt>m</tt>.
     * <p>
     * Please note that, if <code>this</code> is an orthogonal matrix or a matrix whose columns are orthogonal vectors,
     * then this method <i>need not</i> be invoked, since in that case <code>this</code> itself is its normal matrix. In
     * that case, use {@link #set3x3(IMatrix4x3d)} to set a given Matrix4x3dc to only the left 3x3 submatrix of this
     * matrix.
     *
     * @return this
     *
     * @see #set3x3(IMatrix4x3d)
     */
    public abstract Matrix4x3dc normal();

    /**
     * Normalize the left 3x3 submatrix of this matrix.
     * <p>
     * The resulting matrix will map unit vectors to unit vectors, though a pair of orthogonal input unit vectors need
     * not be mapped to a pair of orthogonal output vectors if the original matrix was not orthogonal itself (i.e. had
     * <i>skewing</i>).
     *
     * @return this
     */
    public abstract Matrix4x3dc normalize3x3();

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
    public abstract Matrix4x3dc reflect(double a, double b, double c, double d);

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
    public abstract Matrix4x3dc reflect(double nx, double ny, double nz, double px, double py, double pz);

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
    public abstract Matrix4x3dc reflect(IVector3d normal, IVector3d point);

    /**
     * Apply a mirror/reflection transformation to this matrix that reflects about a plane specified via the plane
     * orientation and a point on the plane.
     * <p>
     * This method can be used to build a reflection transformation based on the orientation of a mirror object in the
     * scene. It is assumed that the default mirror plane's normal is <tt>(0, 0, 1)</tt>. So, if the given {@link
     * IQuaterniond} is the identity (does not apply any additional rotation), the reflection plane will be
     * <tt>z=0</tt>, offset by the given <code>point</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the reflection matrix, then the new matrix will
     * be <code>M * R</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>M * R *
     * v</code>, the reflection will be applied first!
     *
     * @param orientation the plane orientation relative to an implied normal vector of <tt>(0, 0, 1)</tt>
     * @param point       a point on the plane
     * @return this
     */
    public abstract Matrix4x3dc reflect(IQuaterniond orientation, IVector3d point);

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
    public abstract Matrix4x3dc reflection(double a, double b, double c, double d);

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
    public abstract Matrix4x3dc reflection(double nx, double ny, double nz, double px, double py, double pz);

    /**
     * Set this matrix to a mirror/reflection transformation that reflects about the given plane specified via the plane
     * normal and a point on the plane.
     *
     * @param normal the plane normal
     * @param point  a point on the plane
     * @return this
     */
    public abstract Matrix4x3dc reflection(IVector3d normal, IVector3d point);

    /**
     * Set this matrix to a mirror/reflection transformation that reflects about a plane specified via the plane
     * orientation and a point on the plane.
     * <p>
     * This method can be used to build a reflection transformation based on the orientation of a mirror object in the
     * scene. It is assumed that the default mirror plane's normal is <tt>(0, 0, 1)</tt>. So, if the given {@link
     * IQuaterniond} is the identity (does not apply any additional rotation), the reflection plane will be
     * <tt>z=0</tt>, offset by the given <code>point</code>.
     *
     * @param orientation the plane orientation
     * @param point       a point on the plane
     * @return this
     */
    public abstract Matrix4x3dc reflection(IQuaterniond orientation, IVector3d point);

    /**
     * Apply an orthographic projection transformation for a right-handed coordinate system using the given NDC z range
     * to this matrix.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>O</code> the orthographic projection matrix, then the new
     * matrix will be <code>M * O</code>. So when transforming a vector <code>v</code> with the new matrix by using
     * <code>M * O * v</code>, the orthographic projection transformation will be applied first!
     * <p>
     * In order to set the matrix to an orthographic projection without post-multiplying it, use {@link
     * #setOrtho(double, double, double, double, double, double, boolean) setOrtho()}.
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
     * @see #setOrtho(double, double, double, double, double, double, boolean)
     */
    public abstract Matrix4x3dc ortho(double left, double right, double bottom, double top, double zNear, double zFar, boolean zZeroToOne);

    /**
     * Apply an orthographic projection transformation for a right-handed coordinate system using OpenGL's NDC z range
     * of <tt>[-1..+1]</tt> to this matrix.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>O</code> the orthographic projection matrix, then the new
     * matrix will be <code>M * O</code>. So when transforming a vector <code>v</code> with the new matrix by using
     * <code>M * O * v</code>, the orthographic projection transformation will be applied first!
     * <p>
     * In order to set the matrix to an orthographic projection without post-multiplying it, use {@link
     * #setOrtho(double, double, double, double, double, double) setOrtho()}.
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
     * @see #setOrtho(double, double, double, double, double, double)
     */
    public abstract Matrix4x3dc ortho(double left, double right, double bottom, double top, double zNear, double zFar);

    /**
     * Apply an orthographic projection transformation for a left-handed coordiante system using the given NDC z range
     * to this matrix.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>O</code> the orthographic projection matrix, then the new
     * matrix will be <code>M * O</code>. So when transforming a vector <code>v</code> with the new matrix by using
     * <code>M * O * v</code>, the orthographic projection transformation will be applied first!
     * <p>
     * In order to set the matrix to an orthographic projection without post-multiplying it, use {@link
     * #setOrthoLH(double, double, double, double, double, double, boolean) setOrthoLH()}.
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
     * @see #setOrthoLH(double, double, double, double, double, double, boolean)
     */
    public abstract Matrix4x3dc orthoLH(double left, double right, double bottom, double top, double zNear, double zFar, boolean zZeroToOne);

    /**
     * Apply an orthographic projection transformation for a left-handed coordiante system using OpenGL's NDC z range of
     * <tt>[-1..+1]</tt> to this matrix.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>O</code> the orthographic projection matrix, then the new
     * matrix will be <code>M * O</code>. So when transforming a vector <code>v</code> with the new matrix by using
     * <code>M * O * v</code>, the orthographic projection transformation will be applied first!
     * <p>
     * In order to set the matrix to an orthographic projection without post-multiplying it, use {@link
     * #setOrthoLH(double, double, double, double, double, double) setOrthoLH()}.
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
     * @see #setOrthoLH(double, double, double, double, double, double)
     */
    public abstract Matrix4x3dc orthoLH(double left, double right, double bottom, double top, double zNear, double zFar);

    /**
     * Set this matrix to be an orthographic projection transformation for a right-handed coordinate system using the
     * given NDC z range.
     * <p>
     * In order to apply the orthographic projection to an already existing transformation, use {@link #ortho(double,
     * double, double, double, double, double, boolean) ortho()}.
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
     * @see #ortho(double, double, double, double, double, double, boolean)
     */
    public abstract Matrix4x3dc setOrtho(double left, double right, double bottom, double top, double zNear, double zFar, boolean zZeroToOne);

    /**
     * Set this matrix to be an orthographic projection transformation for a right-handed coordinate system using
     * OpenGL's NDC z range of <tt>[-1..+1]</tt>.
     * <p>
     * In order to apply the orthographic projection to an already existing transformation, use {@link #ortho(double,
     * double, double, double, double, double) ortho()}.
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
     * @see #ortho(double, double, double, double, double, double)
     */
    public abstract Matrix4x3dc setOrtho(double left, double right, double bottom, double top, double zNear, double zFar);

    /**
     * Set this matrix to be an orthographic projection transformation for a left-handed coordinate system using the
     * given NDC z range.
     * <p>
     * In order to apply the orthographic projection to an already existing transformation, use {@link #orthoLH(double,
     * double, double, double, double, double, boolean) orthoLH()}.
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
     * @see #orthoLH(double, double, double, double, double, double, boolean)
     */
    public abstract Matrix4x3dc setOrthoLH(double left, double right, double bottom, double top, double zNear, double zFar, boolean zZeroToOne);

    /**
     * Set this matrix to be an orthographic projection transformation for a left-handed coordinate system using
     * OpenGL's NDC z range of <tt>[-1..+1]</tt>.
     * <p>
     * In order to apply the orthographic projection to an already existing transformation, use {@link #orthoLH(double,
     * double, double, double, double, double) orthoLH()}.
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
     * @see #orthoLH(double, double, double, double, double, double)
     */
    public abstract Matrix4x3dc setOrthoLH(double left, double right, double bottom, double top, double zNear, double zFar);

    /**
     * Apply a symmetric orthographic projection transformation for a right-handed coordinate system using the given NDC
     * z range to this matrix.
     * <p>
     * This method is equivalent to calling {@link #ortho(double, double, double, double, double, double, boolean)
     * ortho()} with <code>left=-width/2</code>, <code>right=+width/2</code>, <code>bottom=-height/2</code> and
     * <code>top=+height/2</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>O</code> the orthographic projection matrix, then the new
     * matrix will be <code>M * O</code>. So when transforming a vector <code>v</code> with the new matrix by using
     * <code>M * O * v</code>, the orthographic projection transformation will be applied first!
     * <p>
     * In order to set the matrix to a symmetric orthographic projection without post-multiplying it, use {@link
     * #setOrthoSymmetric(double, double, double, double, boolean) setOrthoSymmetric()}.
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
     * @see #setOrthoSymmetric(double, double, double, double, boolean)
     */
    public abstract Matrix4x3dc orthoSymmetric(double width, double height, double zNear, double zFar, boolean zZeroToOne);

    /**
     * Apply a symmetric orthographic projection transformation for a right-handed coordinate system using OpenGL's NDC
     * z range of <tt>[-1..+1]</tt> to this matrix.
     * <p>
     * This method is equivalent to calling {@link #ortho(double, double, double, double, double, double) ortho()} with
     * <code>left=-width/2</code>, <code>right=+width/2</code>, <code>bottom=-height/2</code> and
     * <code>top=+height/2</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>O</code> the orthographic projection matrix, then the new
     * matrix will be <code>M * O</code>. So when transforming a vector <code>v</code> with the new matrix by using
     * <code>M * O * v</code>, the orthographic projection transformation will be applied first!
     * <p>
     * In order to set the matrix to a symmetric orthographic projection without post-multiplying it, use {@link
     * #setOrthoSymmetric(double, double, double, double) setOrthoSymmetric()}.
     * <p>
     * Reference: <a href="http://www.songho.ca/opengl/gl_projectionmatrix.html#ortho">http://www.songho.ca</a>
     *
     * @param width  the distance between the right and left frustum edges
     * @param height the distance between the top and bottom frustum edges
     * @param zNear  near clipping plane distance
     * @param zFar   far clipping plane distance
     * @return this
     *
     * @see #setOrthoSymmetric(double, double, double, double)
     */
    public abstract Matrix4x3dc orthoSymmetric(double width, double height, double zNear, double zFar);

    /**
     * Apply a symmetric orthographic projection transformation for a left-handed coordinate system using the given NDC
     * z range to this matrix.
     * <p>
     * This method is equivalent to calling {@link #orthoLH(double, double, double, double, double, double, boolean)
     * orthoLH()} with <code>left=-width/2</code>, <code>right=+width/2</code>, <code>bottom=-height/2</code> and
     * <code>top=+height/2</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>O</code> the orthographic projection matrix, then the new
     * matrix will be <code>M * O</code>. So when transforming a vector <code>v</code> with the new matrix by using
     * <code>M * O * v</code>, the orthographic projection transformation will be applied first!
     * <p>
     * In order to set the matrix to a symmetric orthographic projection without post-multiplying it, use {@link
     * #setOrthoSymmetricLH(double, double, double, double, boolean) setOrthoSymmetricLH()}.
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
     * @see #setOrthoSymmetricLH(double, double, double, double, boolean)
     */
    public abstract Matrix4x3dc orthoSymmetricLH(double width, double height, double zNear, double zFar, boolean zZeroToOne);

    /**
     * Apply a symmetric orthographic projection transformation for a left-handed coordinate system using OpenGL's NDC z
     * range of <tt>[-1..+1]</tt> to this matrix.
     * <p>
     * This method is equivalent to calling {@link #orthoLH(double, double, double, double, double, double) orthoLH()}
     * with <code>left=-width/2</code>, <code>right=+width/2</code>, <code>bottom=-height/2</code> and
     * <code>top=+height/2</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>O</code> the orthographic projection matrix, then the new
     * matrix will be <code>M * O</code>. So when transforming a vector <code>v</code> with the new matrix by using
     * <code>M * O * v</code>, the orthographic projection transformation will be applied first!
     * <p>
     * In order to set the matrix to a symmetric orthographic projection without post-multiplying it, use {@link
     * #setOrthoSymmetricLH(double, double, double, double) setOrthoSymmetricLH()}.
     * <p>
     * Reference: <a href="http://www.songho.ca/opengl/gl_projectionmatrix.html#ortho">http://www.songho.ca</a>
     *
     * @param width  the distance between the right and left frustum edges
     * @param height the distance between the top and bottom frustum edges
     * @param zNear  near clipping plane distance
     * @param zFar   far clipping plane distance
     * @return this
     *
     * @see #setOrthoSymmetricLH(double, double, double, double)
     */
    public abstract Matrix4x3dc orthoSymmetricLH(double width, double height, double zNear, double zFar);

    /**
     * Set this matrix to be a symmetric orthographic projection transformation for a right-handed coordinate system
     * using the given NDC z range.
     * <p>
     * This method is equivalent to calling {@link #setOrtho(double, double, double, double, double, double, boolean)
     * setOrtho()} with <code>left=-width/2</code>, <code>right=+width/2</code>, <code>bottom=-height/2</code> and
     * <code>top=+height/2</code>.
     * <p>
     * In order to apply the symmetric orthographic projection to an already existing transformation, use {@link
     * #orthoSymmetric(double, double, double, double, boolean) orthoSymmetric()}.
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
     * @see #orthoSymmetric(double, double, double, double, boolean)
     */
    public abstract Matrix4x3dc setOrthoSymmetric(double width, double height, double zNear, double zFar, boolean zZeroToOne);

    /**
     * Set this matrix to be a symmetric orthographic projection transformation for a right-handed coordinate system
     * using OpenGL's NDC z range of <tt>[-1..+1]</tt>.
     * <p>
     * This method is equivalent to calling {@link #setOrtho(double, double, double, double, double, double) setOrtho()}
     * with <code>left=-width/2</code>, <code>right=+width/2</code>, <code>bottom=-height/2</code> and
     * <code>top=+height/2</code>.
     * <p>
     * In order to apply the symmetric orthographic projection to an already existing transformation, use {@link
     * #orthoSymmetric(double, double, double, double) orthoSymmetric()}.
     * <p>
     * Reference: <a href="http://www.songho.ca/opengl/gl_projectionmatrix.html#ortho">http://www.songho.ca</a>
     *
     * @param width  the distance between the right and left frustum edges
     * @param height the distance between the top and bottom frustum edges
     * @param zNear  near clipping plane distance
     * @param zFar   far clipping plane distance
     * @return this
     *
     * @see #orthoSymmetric(double, double, double, double)
     */
    public abstract Matrix4x3dc setOrthoSymmetric(double width, double height, double zNear, double zFar);

    /**
     * Set this matrix to be a symmetric orthographic projection transformation for a left-handed coordinate system
     * using the given NDC z range.
     * <p>
     * This method is equivalent to calling {@link #setOrtho(double, double, double, double, double, double, boolean)
     * setOrtho()} with <code>left=-width/2</code>, <code>right=+width/2</code>, <code>bottom=-height/2</code> and
     * <code>top=+height/2</code>.
     * <p>
     * In order to apply the symmetric orthographic projection to an already existing transformation, use {@link
     * #orthoSymmetricLH(double, double, double, double, boolean) orthoSymmetricLH()}.
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
     * @see #orthoSymmetricLH(double, double, double, double, boolean)
     */
    public abstract Matrix4x3dc setOrthoSymmetricLH(double width, double height, double zNear, double zFar, boolean zZeroToOne);

    /**
     * Set this matrix to be a symmetric orthographic projection transformation for a left-handed coordinate system
     * using OpenGL's NDC z range of <tt>[-1..+1]</tt>.
     * <p>
     * This method is equivalent to calling {@link #setOrthoLH(double, double, double, double, double, double)
     * setOrthoLH()} with <code>left=-width/2</code>, <code>right=+width/2</code>, <code>bottom=-height/2</code> and
     * <code>top=+height/2</code>.
     * <p>
     * In order to apply the symmetric orthographic projection to an already existing transformation, use {@link
     * #orthoSymmetricLH(double, double, double, double) orthoSymmetricLH()}.
     * <p>
     * Reference: <a href="http://www.songho.ca/opengl/gl_projectionmatrix.html#ortho">http://www.songho.ca</a>
     *
     * @param width  the distance between the right and left frustum edges
     * @param height the distance between the top and bottom frustum edges
     * @param zNear  near clipping plane distance
     * @param zFar   far clipping plane distance
     * @return this
     *
     * @see #orthoSymmetricLH(double, double, double, double)
     */
    public abstract Matrix4x3dc setOrthoSymmetricLH(double width, double height, double zNear, double zFar);

    /**
     * Apply an orthographic projection transformation for a right-handed coordinate system to this matrix.
     * <p>
     * This method is equivalent to calling {@link #ortho(double, double, double, double, double, double) ortho()} with
     * <code>zNear=-1</code> and <code>zFar=+1</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>O</code> the orthographic projection matrix, then the new
     * matrix will be <code>M * O</code>. So when transforming a vector <code>v</code> with the new matrix by using
     * <code>M * O * v</code>, the orthographic projection transformation will be applied first!
     * <p>
     * In order to set the matrix to an orthographic projection without post-multiplying it, use {@link
     * #setOrtho2D(double, double, double, double) setOrtho2D()}.
     * <p>
     * Reference: <a href="http://www.songho.ca/opengl/gl_projectionmatrix.html#ortho">http://www.songho.ca</a>
     *
     * @param left   the distance from the center to the left frustum edge
     * @param right  the distance from the center to the right frustum edge
     * @param bottom the distance from the center to the bottom frustum edge
     * @param top    the distance from the center to the top frustum edge
     * @return this
     *
     * @see #ortho(double, double, double, double, double, double)
     * @see #setOrtho2D(double, double, double, double)
     */
    public abstract Matrix4x3dc ortho2D(double left, double right, double bottom, double top);

    /**
     * Apply an orthographic projection transformation for a left-handed coordinate system to this matrix.
     * <p>
     * This method is equivalent to calling {@link #orthoLH(double, double, double, double, double, double) orthoLH()}
     * with <code>zNear=-1</code> and <code>zFar=+1</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>O</code> the orthographic projection matrix, then the new
     * matrix will be <code>M * O</code>. So when transforming a vector <code>v</code> with the new matrix by using
     * <code>M * O * v</code>, the orthographic projection transformation will be applied first!
     * <p>
     * In order to set the matrix to an orthographic projection without post-multiplying it, use {@link
     * #setOrtho2DLH(double, double, double, double) setOrtho2DLH()}.
     * <p>
     * Reference: <a href="http://www.songho.ca/opengl/gl_projectionmatrix.html#ortho">http://www.songho.ca</a>
     *
     * @param left   the distance from the center to the left frustum edge
     * @param right  the distance from the center to the right frustum edge
     * @param bottom the distance from the center to the bottom frustum edge
     * @param top    the distance from the center to the top frustum edge
     * @return this
     *
     * @see #orthoLH(double, double, double, double, double, double)
     * @see #setOrtho2DLH(double, double, double, double)
     */
    public abstract Matrix4x3dc ortho2DLH(double left, double right, double bottom, double top);

    /**
     * Set this matrix to be an orthographic projection transformation for a right-handed coordinate system.
     * <p>
     * This method is equivalent to calling {@link #setOrtho(double, double, double, double, double, double) setOrtho()}
     * with <code>zNear=-1</code> and <code>zFar=+1</code>.
     * <p>
     * In order to apply the orthographic projection to an already existing transformation, use {@link #ortho2D(double,
     * double, double, double) ortho2D()}.
     * <p>
     * Reference: <a href="http://www.songho.ca/opengl/gl_projectionmatrix.html#ortho">http://www.songho.ca</a>
     *
     * @param left   the distance from the center to the left frustum edge
     * @param right  the distance from the center to the right frustum edge
     * @param bottom the distance from the center to the bottom frustum edge
     * @param top    the distance from the center to the top frustum edge
     * @return this
     *
     * @see #setOrtho(double, double, double, double, double, double)
     * @see #ortho2D(double, double, double, double)
     */
    public abstract Matrix4x3dc setOrtho2D(double left, double right, double bottom, double top);

    /**
     * Set this matrix to be an orthographic projection transformation for a left-handed coordinate system.
     * <p>
     * This method is equivalent to calling {@link #setOrtho(double, double, double, double, double, double)
     * setOrthoLH()} with <code>zNear=-1</code> and <code>zFar=+1</code>.
     * <p>
     * In order to apply the orthographic projection to an already existing transformation, use {@link
     * #ortho2DLH(double, double, double, double) ortho2DLH()}.
     * <p>
     * Reference: <a href="http://www.songho.ca/opengl/gl_projectionmatrix.html#ortho">http://www.songho.ca</a>
     *
     * @param left   the distance from the center to the left frustum edge
     * @param right  the distance from the center to the right frustum edge
     * @param bottom the distance from the center to the bottom frustum edge
     * @param top    the distance from the center to the top frustum edge
     * @return this
     *
     * @see #setOrthoLH(double, double, double, double, double, double)
     * @see #ortho2DLH(double, double, double, double)
     */
    public abstract Matrix4x3dc setOrtho2DLH(double left, double right, double bottom, double top);

    /**
     * Apply a rotation transformation to this matrix to make <code>-z</code> point along <code>dir</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>L</code> the lookalong rotation matrix, then the new
     * matrix will be <code>M * L</code>. So when transforming a vector <code>v</code> with the new matrix by using
     * <code>M * L * v</code>, the lookalong rotation transformation will be applied first!
     * <p>
     * This is equivalent to calling {@link #lookAt(IVector3d, IVector3d, IVector3d) lookAt} with <code>eye = (0, 0,
     * 0)</code> and <code>center = dir</code>.
     * <p>
     * In order to set the matrix to a lookalong transformation without post-multiplying it, use {@link
     * #setLookAlong(IVector3d, IVector3d) setLookAlong()}.
     *
     * @param dir the direction in space to look along
     * @param up  the direction of 'up'
     * @return this
     *
     * @see #lookAlong(double, double, double, double, double, double)
     * @see #lookAt(IVector3d, IVector3d, IVector3d)
     * @see #setLookAlong(IVector3d, IVector3d)
     */
    public abstract Matrix4x3dc lookAlong(IVector3d dir, IVector3d up);

    /**
     * Apply a rotation transformation to this matrix to make <code>-z</code> point along <code>dir</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>L</code> the lookalong rotation matrix, then the new
     * matrix will be <code>M * L</code>. So when transforming a vector <code>v</code> with the new matrix by using
     * <code>M * L * v</code>, the lookalong rotation transformation will be applied first!
     * <p>
     * This is equivalent to calling {@link #lookAt(double, double, double, double, double, double, double, double,
     * double) lookAt()} with <code>eye = (0, 0, 0)</code> and <code>center = dir</code>.
     * <p>
     * In order to set the matrix to a lookalong transformation without post-multiplying it, use {@link
     * #setLookAlong(double, double, double, double, double, double) setLookAlong()}
     *
     * @param dirX the x-coordinate of the direction to look along
     * @param dirY the y-coordinate of the direction to look along
     * @param dirZ the z-coordinate of the direction to look along
     * @param upX  the x-coordinate of the up vector
     * @param upY  the y-coordinate of the up vector
     * @param upZ  the z-coordinate of the up vector
     * @return this
     *
     * @see #lookAt(double, double, double, double, double, double, double, double, double)
     * @see #setLookAlong(double, double, double, double, double, double)
     */
    public abstract Matrix4x3dc lookAlong(double dirX, double dirY, double dirZ,
                                          double upX, double upY, double upZ);

    /**
     * Set this matrix to a rotation transformation to make <code>-z</code> point along <code>dir</code>.
     * <p>
     * This is equivalent to calling {@link #setLookAt(IVector3d, IVector3d, IVector3d) setLookAt()} with <code>eye =
     * (0, 0, 0)</code> and <code>center = dir</code>.
     * <p>
     * In order to apply the lookalong transformation to any previous existing transformation, use {@link
     * #lookAlong(IVector3d, IVector3d)}.
     *
     * @param dir the direction in space to look along
     * @param up  the direction of 'up'
     * @return this
     *
     * @see #setLookAlong(IVector3d, IVector3d)
     * @see #lookAlong(IVector3d, IVector3d)
     */
    public abstract Matrix4x3dc setLookAlong(IVector3d dir, IVector3d up);

    /**
     * Set this matrix to a rotation transformation to make <code>-z</code> point along <code>dir</code>.
     * <p>
     * This is equivalent to calling {@link #setLookAt(double, double, double, double, double, double, double, double,
     * double) setLookAt()} with <code>eye = (0, 0, 0)</code> and <code>center = dir</code>.
     * <p>
     * In order to apply the lookalong transformation to any previous existing transformation, use {@link
     * #lookAlong(double, double, double, double, double, double) lookAlong()}
     *
     * @param dirX the x-coordinate of the direction to look along
     * @param dirY the y-coordinate of the direction to look along
     * @param dirZ the z-coordinate of the direction to look along
     * @param upX  the x-coordinate of the up vector
     * @param upY  the y-coordinate of the up vector
     * @param upZ  the z-coordinate of the up vector
     * @return this
     *
     * @see #setLookAlong(double, double, double, double, double, double)
     * @see #lookAlong(double, double, double, double, double, double)
     */
    public abstract Matrix4x3dc setLookAlong(double dirX, double dirY, double dirZ,
                                             double upX, double upY, double upZ);

    /**
     * Set this matrix to be a "lookat" transformation for a right-handed coordinate system, that aligns <code>-z</code>
     * with <code>center - eye</code>.
     * <p>
     * In order to not make use of vectors to specify <code>eye</code>, <code>center</code> and <code>up</code> but use
     * primitives, like in the GLU function, use {@link #setLookAt(double, double, double, double, double, double,
     * double, double, double) setLookAt()} instead.
     * <p>
     * In order to apply the lookat transformation to a previous existing transformation, use {@link #lookAt(IVector3d,
     * IVector3d, IVector3d) lookAt()}.
     *
     * @param eye    the position of the camera
     * @param center the point in space to look at
     * @param up     the direction of 'up'
     * @return this
     *
     * @see #setLookAt(double, double, double, double, double, double, double, double, double)
     * @see #lookAt(IVector3d, IVector3d, IVector3d)
     */
    public abstract Matrix4x3dc setLookAt(IVector3d eye, IVector3d center, IVector3d up);

    /**
     * Set this matrix to be a "lookat" transformation for a right-handed coordinate system, that aligns <code>-z</code>
     * with <code>center - eye</code>.
     * <p>
     * In order to apply the lookat transformation to a previous existing transformation, use {@link #lookAt(double,
     * double, double, double, double, double, double, double, double) lookAt}.
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
     * @see #setLookAt(IVector3d, IVector3d, IVector3d)
     * @see #lookAt(double, double, double, double, double, double, double, double, double)
     */
    public abstract Matrix4x3dc setLookAt(double eyeX, double eyeY, double eyeZ,
                                          double centerX, double centerY, double centerZ,
                                          double upX, double upY, double upZ);

    /**
     * Apply a "lookat" transformation to this matrix for a right-handed coordinate system, that aligns <code>-z</code>
     * with <code>center - eye</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>L</code> the lookat matrix, then the new matrix will be
     * <code>M * L</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>M * L *
     * v</code>, the lookat transformation will be applied first!
     * <p>
     * In order to set the matrix to a lookat transformation without post-multiplying it, use {@link
     * #setLookAt(IVector3d, IVector3d, IVector3d)}.
     *
     * @param eye    the position of the camera
     * @param center the point in space to look at
     * @param up     the direction of 'up'
     * @return this
     *
     * @see #lookAt(double, double, double, double, double, double, double, double, double)
     * @see #setLookAlong(IVector3d, IVector3d)
     */
    public abstract Matrix4x3dc lookAt(IVector3d eye, IVector3d center, IVector3d up);

    /**
     * Apply a "lookat" transformation to this matrix for a right-handed coordinate system, that aligns <code>-z</code>
     * with <code>center - eye</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>L</code> the lookat matrix, then the new matrix will be
     * <code>M * L</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>M * L *
     * v</code>, the lookat transformation will be applied first!
     * <p>
     * In order to set the matrix to a lookat transformation without post-multiplying it, use {@link #setLookAt(double,
     * double, double, double, double, double, double, double, double) setLookAt()}.
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
     * @see #lookAt(IVector3d, IVector3d, IVector3d)
     * @see #setLookAt(double, double, double, double, double, double, double, double, double)
     */
    public abstract Matrix4x3dc lookAt(double eyeX, double eyeY, double eyeZ,
                                       double centerX, double centerY, double centerZ,
                                       double upX, double upY, double upZ);

    /**
     * Set this matrix to be a "lookat" transformation for a left-handed coordinate system, that aligns <code>+z</code>
     * with <code>center - eye</code>.
     * <p>
     * In order to not make use of vectors to specify <code>eye</code>, <code>center</code> and <code>up</code> but use
     * primitives, like in the GLU function, use {@link #setLookAtLH(double, double, double, double, double, double,
     * double, double, double) setLookAtLH()} instead.
     * <p>
     * In order to apply the lookat transformation to a previous existing transformation, use {@link
     * #lookAtLH(IVector3d, IVector3d, IVector3d) lookAt()}.
     *
     * @param eye    the position of the camera
     * @param center the point in space to look at
     * @param up     the direction of 'up'
     * @return this
     *
     * @see #setLookAtLH(double, double, double, double, double, double, double, double, double)
     * @see #lookAtLH(IVector3d, IVector3d, IVector3d)
     */
    public abstract Matrix4x3dc setLookAtLH(IVector3d eye, IVector3d center, IVector3d up);

    /**
     * Set this matrix to be a "lookat" transformation for a left-handed coordinate system, that aligns <code>+z</code>
     * with <code>center - eye</code>.
     * <p>
     * In order to apply the lookat transformation to a previous existing transformation, use {@link #lookAtLH(double,
     * double, double, double, double, double, double, double, double) lookAtLH}.
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
     * @see #setLookAtLH(IVector3d, IVector3d, IVector3d)
     * @see #lookAtLH(double, double, double, double, double, double, double, double, double)
     */
    public abstract Matrix4x3dc setLookAtLH(double eyeX, double eyeY, double eyeZ,
                                            double centerX, double centerY, double centerZ,
                                            double upX, double upY, double upZ);

    /**
     * Apply a "lookat" transformation to this matrix for a left-handed coordinate system, that aligns <code>+z</code>
     * with <code>center - eye</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>L</code> the lookat matrix, then the new matrix will be
     * <code>M * L</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>M * L *
     * v</code>, the lookat transformation will be applied first!
     * <p>
     * In order to set the matrix to a lookat transformation without post-multiplying it, use {@link
     * #setLookAtLH(IVector3d, IVector3d, IVector3d)}.
     *
     * @param eye    the position of the camera
     * @param center the point in space to look at
     * @param up     the direction of 'up'
     * @return this
     *
     * @see #lookAtLH(double, double, double, double, double, double, double, double, double)
     */
    public abstract Matrix4x3dc lookAtLH(IVector3d eye, IVector3d center, IVector3d up);

    /**
     * Apply a "lookat" transformation to this matrix for a left-handed coordinate system, that aligns <code>+z</code>
     * with <code>center - eye</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>L</code> the lookat matrix, then the new matrix will be
     * <code>M * L</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>M * L *
     * v</code>, the lookat transformation will be applied first!
     * <p>
     * In order to set the matrix to a lookat transformation without post-multiplying it, use {@link
     * #setLookAtLH(double, double, double, double, double, double, double, double, double) setLookAtLH()}.
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
     * @see #lookAtLH(IVector3d, IVector3d, IVector3d)
     * @see #setLookAtLH(double, double, double, double, double, double, double, double, double)
     */
    public abstract Matrix4x3dc lookAtLH(double eyeX, double eyeY, double eyeZ,
                                         double centerX, double centerY, double centerZ,
                                         double upX, double upY, double upZ);

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
    public abstract Matrix4x3dc shadow(IVector4d light, double a, double b, double c, double d);

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
    public abstract Matrix4x3dc shadow(double lightX, double lightY, double lightZ, double lightW, double a, double b, double c, double d);

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
    public abstract Matrix4x3dc shadow(IVector4d light, IMatrix4x3d planeTransform);

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
    public abstract Matrix4x3dc shadow(double lightX, double lightY, double lightZ, double lightW, IMatrix4x3d planeTransform);

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
     * @param up        the rotation axis (must be {@link Vector3dc#normalize() normalized})
     * @return this
     */
    public abstract Matrix4x3dc billboardCylindrical(IVector3d objPos, IVector3d targetPos, IVector3d up);

    /**
     * Set this matrix to a spherical billboard transformation that rotates the local +Z axis of a given object with
     * position <code>objPos</code> towards a target position at <code>targetPos</code>.
     * <p>
     * This method can be used to create the complete model transformation for a given object, including the translation
     * of the object to its position <code>objPos</code>.
     * <p>
     * If preserving an <i>up</i> vector is not necessary when rotating the +Z axis, then a shortest arc rotation can be
     * obtained using {@link #billboardSpherical(IVector3d, IVector3d)}.
     *
     * @param objPos    the position of the object to rotate towards <code>targetPos</code>
     * @param targetPos the position of the target (for example the camera) towards which to rotate the object
     * @param up        the up axis used to orient the object
     * @return this
     *
     * @see #billboardSpherical(IVector3d, IVector3d)
     */
    public abstract Matrix4x3dc billboardSpherical(IVector3d objPos, IVector3d targetPos, IVector3d up);

    /**
     * Set this matrix to a spherical billboard transformation that rotates the local +Z axis of a given object with
     * position <code>objPos</code> towards a target position at <code>targetPos</code> using a shortest arc rotation by
     * not preserving any <i>up</i> vector of the object.
     * <p>
     * This method can be used to create the complete model transformation for a given object, including the translation
     * of the object to its position <code>objPos</code>.
     * <p>
     * In order to specify an <i>up</i> vector which needs to be maintained when rotating the +Z axis of the object, use
     * {@link #billboardSpherical(IVector3d, IVector3d, IVector3d)}.
     *
     * @param objPos    the position of the object to rotate towards <code>targetPos</code>
     * @param targetPos the position of the target (for example the camera) towards which to rotate the object
     * @return this
     *
     * @see #billboardSpherical(IVector3d, IVector3d, IVector3d)
     */
    public abstract Matrix4x3dc billboardSpherical(IVector3d objPos, IVector3d targetPos);

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
    public abstract Matrix4x3dc pick(double x, double y, double width, double height, int[] viewport);

    /**
     * Exchange the values of <code>this</code> matrix with the given <code>other</code> matrix.
     *
     * @param other the other matrix to exchange the values with
     * @return this
     */
    public abstract Matrix4x3dc swap(Matrix4x3dc other);

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
    public abstract Matrix4x3dc arcball(double radius, double centerX, double centerY, double centerZ, double angleX, double angleY);

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
    public abstract Matrix4x3dc arcball(double radius, IVector3d center, double angleX, double angleY);

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
    public abstract Matrix4x3dc lerp(IMatrix4x3d other, double t);

    /**
     * Apply a model transformation to this matrix for a right-handed coordinate system, that aligns the local
     * <code>+Z</code> axis with <code>dir</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>L</code> the lookat matrix, then the new matrix will be
     * <code>M * L</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>M * L *
     * v</code>, the lookat transformation will be applied first!
     * <p>
     * In order to set the matrix to a rotation transformation without post-multiplying it, use {@link
     * #rotationTowards(IVector3d, IVector3d) rotationTowards()}.
     * <p>
     * This method is equivalent to calling: <tt>mul(new Matrix4x3dc().lookAt(new IVector3d(), new
     * IVector3d(dir).negate(), up).invert())</tt>
     *
     * @param dir the direction to orient towards
     * @param up  the up vector
     * @return this
     *
     * @see #rotateTowards(double, double, double, double, double, double)
     * @see #rotationTowards(IVector3d, IVector3d)
     */
    public abstract Matrix4x3dc rotateTowards(IVector3d dir, IVector3d up);

    /**
     * Apply a model transformation to this matrix for a right-handed coordinate system, that aligns the local
     * <code>+Z</code> axis with <code>(dirX, dirY, dirZ)</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>L</code> the lookat matrix, then the new matrix will be
     * <code>M * L</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>M * L *
     * v</code>, the lookat transformation will be applied first!
     * <p>
     * In order to set the matrix to a rotation transformation without post-multiplying it, use {@link
     * #rotationTowards(double, double, double, double, double, double) rotationTowards()}.
     * <p>
     * This method is equivalent to calling: <tt>mul(new Matrix4x3dc().lookAt(0, 0, 0, -dirX, -dirY, -dirZ, upX, upY,
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
     * @see #rotateTowards(IVector3d, IVector3d)
     * @see #rotationTowards(double, double, double, double, double, double)
     */
    public abstract Matrix4x3dc rotateTowards(double dirX, double dirY, double dirZ, double upX, double upY, double upZ);

    /**
     * Set this matrix to a model transformation for a right-handed coordinate system, that aligns the local
     * <code>-z</code> axis with <code>dir</code>.
     * <p>
     * In order to apply the rotation transformation to a previous existing transformation, use {@link
     * #rotateTowards(double, double, double, double, double, double) rotateTowards}.
     * <p>
     * This method is equivalent to calling: <tt>setLookAt(new IVector3d(), new IVector3d(dir).negate(),
     * up).invert()</tt>
     *
     * @param dir the direction to orient the local -z axis towards
     * @param up  the up vector
     * @return this
     *
     * @see #rotationTowards(IVector3d, IVector3d)
     * @see #rotateTowards(double, double, double, double, double, double)
     */
    public abstract Matrix4x3dc rotationTowards(IVector3d dir, IVector3d up);

    /**
     * Set this matrix to a model transformation for a right-handed coordinate system, that aligns the local
     * <code>-z</code> axis with <code>(dirX, dirY, dirZ)</code>.
     * <p>
     * In order to apply the rotation transformation to a previous existing transformation, use {@link
     * #rotateTowards(double, double, double, double, double, double) rotateTowards}.
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
     * @see #rotateTowards(IVector3d, IVector3d)
     * @see #rotationTowards(double, double, double, double, double, double)
     */
    public abstract Matrix4x3dc rotationTowards(double dirX, double dirY, double dirZ, double upX, double upY, double upZ);

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
     * @see #translation(IVector3d)
     * @see #rotateTowards(IVector3d, IVector3d)
     */
    public abstract Matrix4x3dc translationRotateTowards(IVector3d pos, IVector3d dir, IVector3d up);

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
     * @see #translation(double, double, double)
     * @see #rotateTowards(double, double, double, double, double, double)
     */
    public abstract Matrix4x3dc translationRotateTowards(double posX, double posY, double posZ, double dirX, double dirY, double dirZ, double upX, double upY, double upZ);
}
