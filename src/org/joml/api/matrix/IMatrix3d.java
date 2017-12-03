/*
 * (C) Copyright 2016-2017 JOML

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
import org.joml.api.quaternion.Quaterniondc;
import org.joml.api.quaternion.Quaternionfc;
import org.joml.api.vector.IVector3d;
import org.joml.api.vector.IVector3f;
import org.joml.api.vector.Vector3dc;
import org.joml.api.vector.Vector3fc;

//#ifdef __HAS_NIO__
import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
//#endif

/**
 * Interface to a read-only view of a 3x3 matrix of double-precision floats.
 *
 * @author Kai Burjack
 */
public interface IMatrix3d {

    /**
     * Return the value of the matrix element at column 0 and row 0.
     *
     * @return the value of the matrix element
     */
    double m00();

    /**
     * Return the value of the matrix element at column 0 and row 1.
     *
     * @return the value of the matrix element
     */
    double m01();

    /**
     * Return the value of the matrix element at column 0 and row 2.
     *
     * @return the value of the matrix element
     */
    double m02();

    /**
     * Return the value of the matrix element at column 1 and row 0.
     *
     * @return the value of the matrix element
     */
    double m10();

    /**
     * Return the value of the matrix element at column 1 and row 1.
     *
     * @return the value of the matrix element
     */
    double m11();

    /**
     * Return the value of the matrix element at column 1 and row 2.
     *
     * @return the value of the matrix element
     */
    double m12();

    /**
     * Return the value of the matrix element at column 2 and row 0.
     *
     * @return the value of the matrix element
     */
    double m20();

    /**
     * Return the value of the matrix element at column 2 and row 1.
     *
     * @return the value of the matrix element
     */
    double m21();

    /**
     * Return the value of the matrix element at column 2 and row 2.
     *
     * @return the value of the matrix element
     */
    double m22();

    /**
     * Multiply this matrix by the supplied matrix and store the result in <code>dest</code>. This matrix will be the
     * left one.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the <code>right</code> matrix, then the new
     * matrix will be <code>M * R</code>. So when transforming a vector <code>v</code> with the new matrix by using
     * <code>M * R * v</code>, the transformation of the right matrix will be applied first!
     *
     * @param right the right operand
     * @param dest  will hold the result
     * @return dest
     */
    Matrix3dc mul(IMatrix3d right, Matrix3dc dest);

    /**
     * Pre-multiply this matrix by the supplied <code>left</code> matrix and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>L</code> the <code>left</code> matrix, then the new
     * matrix will be <code>L * M</code>. So when transforming a vector <code>v</code> with the new matrix by using
     * <code>L * M * v</code>, the transformation of <code>this</code> matrix will be applied first!
     *
     * @param left the left operand of the matrix multiplication
     * @param dest the destination matrix, which will hold the result
     * @return dest
     */
    Matrix3dc mulLocal(IMatrix3d left, Matrix3dc dest);

    /**
     * Multiply this matrix by the supplied matrix and store the result in <code>dest</code>. This matrix will be the
     * left one.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the <code>right</code> matrix, then the new
     * matrix will be <code>M * R</code>. So when transforming a vector <code>v</code> with the new matrix by using
     * <code>M * R * v</code>, the transformation of the right matrix will be applied first!
     *
     * @param right the right operand
     * @param dest  will hold the result
     * @return dest
     */
    Matrix3dc mul(IMatrix3f right, Matrix3dc dest);

    /**
     * Return the determinant of this matrix.
     *
     * @return the determinant
     */
    double determinant();

    /**
     * Invert <code>this</code> matrix and store the result in <code>dest</code>.
     *
     * @param dest will hold the result
     * @return dest
     */
    Matrix3dc invert(Matrix3dc dest);

    /**
     * Transpose <code>this</code> matrix and store the result in <code>dest</code>.
     *
     * @param dest will hold the result
     * @return dest
     */
    Matrix3dc transpose(Matrix3dc dest);

    /**
     * Get the current values of <code>this</code> matrix and store them into <code>dest</code>.
     *
     * @param dest the destination matrix
     * @return the passed in destination
     */
    Matrix3dc get(Matrix3dc dest);

    /**
     * Get the current values of <code>this</code> matrix and store the represented rotation into the given {@link
     * AxisAngle4fc}.
     *
     * @param dest the destination {@link AxisAngle4fc}
     * @return the passed in destination
     *
     * @see AxisAngle4fc#set(IMatrix3d)
     */
    AxisAngle4fc getRotation(AxisAngle4fc dest);

    /**
     * Get the current values of <code>this</code> matrix and store the represented rotation into the given {@link
     * Quaternionfc}.
     * <p>
     * This method assumes that the three column vectors of this matrix are not normalized and thus allows to ignore any
     * additional scaling factor that is applied to the matrix.
     *
     * @param dest the destination {@link Quaternionfc}
     * @return the passed in destination
     *
     * @see Quaternionfc#setFromUnnormalized(IMatrix3d)
     */
    Quaternionfc getUnnormalizedRotation(Quaternionfc dest);

    /**
     * Get the current values of <code>this</code> matrix and store the represented rotation into the given {@link
     * Quaternionfc}.
     * <p>
     * This method assumes that the three column vectors of this matrix are normalized.
     *
     * @param dest the destination {@link Quaternionfc}
     * @return the passed in destination
     *
     * @see Quaternionfc#setFromNormalized(IMatrix3d)
     */
    Quaternionfc getNormalizedRotation(Quaternionfc dest);

    /**
     * Get the current values of <code>this</code> matrix and store the represented rotation into the given {@link
     * Quaterniondc}.
     * <p>
     * This method assumes that the three column vectors of this matrix are not normalized and thus allows to ignore any
     * additional scaling factor that is applied to the matrix.
     *
     * @param dest the destination {@link Quaterniondc}
     * @return the passed in destination
     *
     * @see Quaterniondc#setFromUnnormalized(IMatrix3d)
     */
    Quaterniondc getUnnormalizedRotation(Quaterniondc dest);

    /**
     * Get the current values of <code>this</code> matrix and store the represented rotation into the given {@link
     * Quaterniondc}.
     * <p>
     * This method assumes that the three column vectors of this matrix are normalized.
     *
     * @param dest the destination {@link Quaterniondc}
     * @return the passed in destination
     *
     * @see Quaterniondc#setFromNormalized(IMatrix3d)
     */
    Quaterniondc getNormalizedRotation(Quaterniondc dest);

    //#ifdef __HAS_NIO__

    /**
     * Store this matrix into the supplied {@link DoubleBuffer} at the current buffer {@link DoubleBuffer#position()
     * position} using column-major order.
     * <p>
     * This method will not increment the position of the given DoubleBuffer.
     * <p>
     * In order to specify the offset into the DoubleBuffer} at which the matrix is stored, use {@link #get(int,
     * DoubleBuffer)}, taking the absolute position as parameter.
     *
     * @param buffer will receive the values of this matrix in column-major order at its current position
     * @return the passed in buffer
     *
     * @see #get(int, DoubleBuffer)
     */
    DoubleBuffer get(DoubleBuffer buffer);

    /**
     * Store this matrix into the supplied {@link DoubleBuffer} starting at the specified absolute buffer position/index
     * using column-major order.
     * <p>
     * This method will not increment the position of the given {@link DoubleBuffer}.
     *
     * @param index  the absolute position into the {@link DoubleBuffer}
     * @param buffer will receive the values of this matrix in column-major order
     * @return the passed in buffer
     */
    DoubleBuffer get(int index, DoubleBuffer buffer);

    /**
     * Store this matrix in column-major order into the supplied {@link FloatBuffer} at the current buffer {@link
     * FloatBuffer#position() position}.
     * <p>
     * This method will not increment the position of the given FloatBuffer.
     * <p>
     * In order to specify the offset into the FloatBuffer at which the matrix is stored, use {@link #get(int,
     * FloatBuffer)}, taking the absolute position as parameter.
     * <p>
     * Please note that due to this matrix storing double values those values will potentially lose precision when they
     * are converted to float values before being put into the given FloatBuffer.
     *
     * @param buffer will receive the values of this matrix in column-major order at its current position
     * @return the passed in buffer
     *
     * @see #get(int, FloatBuffer)
     */
    FloatBuffer get(FloatBuffer buffer);

    /**
     * Store this matrix in column-major order into the supplied {@link FloatBuffer} starting at the specified absolute
     * buffer position/index.
     * <p>
     * This method will not increment the position of the given FloatBuffer.
     * <p>
     * Please note that due to this matrix storing double values those values will potentially lose precision when they
     * are converted to float values before being put into the given FloatBuffer.
     *
     * @param index  the absolute position into the FloatBuffer
     * @param buffer will receive the values of this matrix in column-major order
     * @return the passed in buffer
     */
    FloatBuffer get(int index, FloatBuffer buffer);

    /**
     * Store this matrix in column-major order into the supplied {@link ByteBuffer} at the current buffer {@link
     * ByteBuffer#position() position}.
     * <p>
     * This method will not increment the position of the given ByteBuffer.
     * <p>
     * In order to specify the offset into the ByteBuffer at which the matrix is stored, use {@link #get(int,
     * ByteBuffer)}, taking the absolute position as parameter.
     *
     * @param buffer will receive the values of this matrix in column-major order at its current position
     * @return the passed in buffer
     *
     * @see #get(int, ByteBuffer)
     */
    ByteBuffer get(ByteBuffer buffer);

    /**
     * Store this matrix in column-major order into the supplied {@link ByteBuffer} starting at the specified absolute
     * buffer position/index.
     * <p>
     * This method will not increment the position of the given ByteBuffer.
     *
     * @param index  the absolute position into the ByteBuffer
     * @param buffer will receive the values of this matrix in column-major order
     * @return the passed in buffer
     */
    ByteBuffer get(int index, ByteBuffer buffer);

    /**
     * Store the elements of this matrix as float values in column-major order into the supplied {@link ByteBuffer} at
     * the current buffer {@link ByteBuffer#position() position}.
     * <p>
     * This method will not increment the position of the given ByteBuffer.
     * <p>
     * Please note that due to this matrix storing double values those values will potentially lose precision when they
     * are converted to float values before being put into the given ByteBuffer.
     * <p>
     * In order to specify the offset into the ByteBuffer at which the matrix is stored, use {@link #getFloats(int,
     * ByteBuffer)}, taking the absolute position as parameter.
     *
     * @param buffer will receive the elements of this matrix as float values in column-major order at its current
     *               position
     * @return the passed in buffer
     *
     * @see #getFloats(int, ByteBuffer)
     */
    ByteBuffer getFloats(ByteBuffer buffer);

    /**
     * Store the elements of this matrix as float values in column-major order into the supplied {@link ByteBuffer}
     * starting at the specified absolute buffer position/index.
     * <p>
     * This method will not increment the position of the given ByteBuffer.
     * <p>
     * Please note that due to this matrix storing double values those values will potentially lose precision when they
     * are converted to float values before being put into the given ByteBuffer.
     *
     * @param index  the absolute position into the ByteBuffer
     * @param buffer will receive the elements of this matrix as float values in column-major order
     * @return the passed in buffer
     */
    ByteBuffer getFloats(int index, ByteBuffer buffer);
    //#endif

    /**
     * Store this matrix into the supplied double array in column-major order at the given offset.
     *
     * @param arr    the array to write the matrix values into
     * @param offset the offset into the array
     * @return the passed in array
     */
    double[] get(double[] arr, int offset);

    /**
     * Store this matrix into the supplied double array in column-major order.
     * <p>
     * In order to specify an explicit offset into the array, use the method {@link #get(double[], int)}.
     *
     * @param arr the array to write the matrix values into
     * @return the passed in array
     *
     * @see #get(double[], int)
     */
    double[] get(double[] arr);

    /**
     * Store the elements of this matrix as float values in column-major order into the supplied float array at the
     * given offset.
     * <p>
     * Please note that due to this matrix storing double values those values will potentially lose precision when they
     * are converted to float values before being put into the given float array.
     *
     * @param arr    the array to write the matrix values into
     * @param offset the offset into the array
     * @return the passed in array
     */
    float[] get(float[] arr, int offset);

    /**
     * Store the elements of this matrix as float values in column-major order into the supplied float array.
     * <p>
     * Please note that due to this matrix storing double values those values will potentially lose precision when they
     * are converted to float values before being put into the given float array.
     * <p>
     * In order to specify an explicit offset into the array, use the method {@link #get(float[], int)}.
     *
     * @param arr the array to write the matrix values into
     * @return the passed in array
     *
     * @see #get(float[], int)
     */
    float[] get(float[] arr);

    /**
     * Apply scaling to <code>this</code> matrix by scaling the base axes by the given <tt>xyz.x</tt>, <tt>xyz.y</tt>
     * and <tt>xyz.z</tt> factors, respectively and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>S</code> the scaling matrix, then the new matrix will be
     * <code>M * S</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>M * S *
     * v</code> , the scaling will be applied first!
     *
     * @param xyz  the factors of the x, y and z component, respectively
     * @param dest will hold the result
     * @return dest
     */
    Matrix3dc scale(IVector3d xyz, Matrix3dc dest);

    /**
     * Apply scaling to this matrix by scaling the base axes by the given x, y and z factors and store the result in
     * <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>S</code> the scaling matrix, then the new matrix will be
     * <code>M * S</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>M * S *
     * v</code> , the scaling will be applied first!
     *
     * @param x    the factor of the x component
     * @param y    the factor of the y component
     * @param z    the factor of the z component
     * @param dest will hold the result
     * @return dest
     */
    Matrix3dc scale(double x, double y, double z, Matrix3dc dest);

    /**
     * Apply scaling to this matrix by uniformly scaling all base axes by the given <code>xyz</code> factor and store
     * the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>S</code> the scaling matrix, then the new matrix will be
     * <code>M * S</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>M * S *
     * v</code> , the scaling will be applied first!
     *
     * @param xyz  the factor for all components
     * @param dest will hold the result
     * @return dest
     *
     * @see #scale(double, double, double, Matrix3dc)
     */
    Matrix3dc scale(double xyz, Matrix3dc dest);

    /**
     * Pre-multiply scaling to <code>this</code> matrix by scaling the base axes by the given x, y and z factors and
     * store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>S</code> the scaling matrix, then the new matrix will be
     * <code>S * M</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>S * M *
     * v</code> , the scaling will be applied last!
     *
     * @param x    the factor of the x component
     * @param y    the factor of the y component
     * @param z    the factor of the z component
     * @param dest will hold the result
     * @return dest
     */
    Matrix3dc scaleLocal(double x, double y, double z, Matrix3dc dest);

    /**
     * Transform the given vector by this matrix.
     *
     * @param v the vector to transform
     * @return v
     */
    Vector3dc transform(Vector3dc v);

    /**
     * Transform the given vector by this matrix and store the result in <code>dest</code>.
     *
     * @param v    the vector to transform
     * @param dest will hold the result
     * @return dest
     */
    Vector3dc transform(IVector3d v, Vector3dc dest);

    /**
     * Transform the given vector by this matrix.
     *
     * @param v the vector to transform
     * @return v
     */
    Vector3fc transform(Vector3fc v);

    /**
     * Transform the given vector by this matrix and store the result in <code>dest</code>.
     *
     * @param v    the vector to transform
     * @param dest will hold the result
     * @return dest
     */
    Vector3fc transform(IVector3f v, Vector3fc dest);

    /**
     * Transform the vector <tt>(x, y, z)</tt> by this matrix and store the result in <code>dest</code>.
     *
     * @param x    the x coordinate of the vector to transform
     * @param y    the y coordinate of the vector to transform
     * @param z    the z coordinate of the vector to transform
     * @param dest will hold the result
     * @return dest
     */
    Vector3dc transform(double x, double y, double z, Vector3dc dest);

    /**
     * Transform the given vector by the transpose of this matrix.
     *
     * @param v the vector to transform
     * @return v
     */
    Vector3dc transformTranspose(Vector3dc v);

    /**
     * Transform the given vector by the transpose of this matrix and store the result in <code>dest</code>.
     *
     * @param v    the vector to transform
     * @param dest will hold the result
     * @return dest
     */
    Vector3dc transformTranspose(IVector3d v, Vector3dc dest);

    /**
     * Transform the vector <tt>(x, y, z)</tt> by the transpose of this matrix and store the result in
     * <code>dest</code>.
     *
     * @param x    the x coordinate of the vector to transform
     * @param y    the y coordinate of the vector to transform
     * @param z    the z coordinate of the vector to transform
     * @param dest will hold the result
     * @return dest
     */
    Vector3dc transformTranspose(double x, double y, double z, Vector3dc dest);

    /**
     * Apply rotation about the X axis to this matrix by rotating the given amount of radians and store the result in
     * <code>dest</code>.
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
     * @param ang  the angle in radians
     * @param dest will hold the result
     * @return dest
     */
    Matrix3dc rotateX(double ang, Matrix3dc dest);

    /**
     * Apply rotation about the Y axis to this matrix by rotating the given amount of radians and store the result in
     * <code>dest</code>.
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
     * @param ang  the angle in radians
     * @param dest will hold the result
     * @return dest
     */
    Matrix3dc rotateY(double ang, Matrix3dc dest);

    /**
     * Apply rotation about the Z axis to this matrix by rotating the given amount of radians and store the result in
     * <code>dest</code>.
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
     * @param ang  the angle in radians
     * @param dest will hold the result
     * @return dest
     */
    Matrix3dc rotateZ(double ang, Matrix3dc dest);

    /**
     * Apply rotation of <code>angleX</code> radians about the X axis, followed by a rotation of <code>angleY</code>
     * radians about the Y axis and followed by a rotation of <code>angleZ</code> radians about the Z axis and store the
     * result in <code>dest</code>.
     * <p>
     * When used with a right-handed coordinate system, the produced rotation will rotate a vector counter-clockwise
     * around the rotation axis, when viewing along the negative axis direction towards the origin. When used with a
     * left-handed coordinate system, the rotation is clockwise.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the rotation matrix, then the new matrix will be
     * <code>M * R</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>M * R *
     * v</code>, the rotation will be applied first!
     * <p>
     * This method is equivalent to calling: <tt>rotateX(angleX, dest).rotateY(angleY).rotateZ(angleZ)</tt>
     *
     * @param angleX the angle to rotate about X
     * @param angleY the angle to rotate about Y
     * @param angleZ the angle to rotate about Z
     * @param dest   will hold the result
     * @return dest
     */
    Matrix3dc rotateXYZ(double angleX, double angleY, double angleZ, Matrix3dc dest);

    /**
     * Apply rotation of <code>angleZ</code> radians about the Z axis, followed by a rotation of <code>angleY</code>
     * radians about the Y axis and followed by a rotation of <code>angleX</code> radians about the X axis and store the
     * result in <code>dest</code>.
     * <p>
     * When used with a right-handed coordinate system, the produced rotation will rotate a vector counter-clockwise
     * around the rotation axis, when viewing along the negative axis direction towards the origin. When used with a
     * left-handed coordinate system, the rotation is clockwise.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the rotation matrix, then the new matrix will be
     * <code>M * R</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>M * R *
     * v</code>, the rotation will be applied first!
     * <p>
     * This method is equivalent to calling: <tt>rotateZ(angleZ, dest).rotateY(angleY).rotateX(angleX)</tt>
     *
     * @param angleZ the angle to rotate about Z
     * @param angleY the angle to rotate about Y
     * @param angleX the angle to rotate about X
     * @param dest   will hold the result
     * @return dest
     */
    Matrix3dc rotateZYX(double angleZ, double angleY, double angleX, Matrix3dc dest);

    /**
     * Apply rotation of <code>angleY</code> radians about the Y axis, followed by a rotation of <code>angleX</code>
     * radians about the X axis and followed by a rotation of <code>angleZ</code> radians about the Z axis and store the
     * result in <code>dest</code>.
     * <p>
     * When used with a right-handed coordinate system, the produced rotation will rotate a vector counter-clockwise
     * around the rotation axis, when viewing along the negative axis direction towards the origin. When used with a
     * left-handed coordinate system, the rotation is clockwise.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the rotation matrix, then the new matrix will be
     * <code>M * R</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>M * R *
     * v</code>, the rotation will be applied first!
     * <p>
     * This method is equivalent to calling: <tt>rotateY(angleY, dest).rotateX(angleX).rotateZ(angleZ)</tt>
     *
     * @param angleY the angle to rotate about Y
     * @param angleX the angle to rotate about X
     * @param angleZ the angle to rotate about Z
     * @param dest   will hold the result
     * @return dest
     */
    Matrix3dc rotateYXZ(double angleY, double angleX, double angleZ, Matrix3dc dest);

    /**
     * Apply rotation to this matrix by rotating the given amount of radians about the given axis specified as x, y and
     * z components, and store the result in <code>dest</code>.
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
     * @param ang  the angle in radians
     * @param x    the x component of the axis
     * @param y    the y component of the axis
     * @param z    the z component of the axis
     * @param dest will hold the result
     * @return dest
     */
    Matrix3dc rotate(double ang, double x, double y, double z, Matrix3dc dest);

    /**
     * Pre-multiply a rotation to this matrix by rotating the given amount of radians about the specified <tt>(x, y,
     * z)</tt> axis and store the result in <code>dest</code>.
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
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Rotation_matrix_from_axis_and_angle">http://en.wikipedia.org</a>
     *
     * @param ang  the angle in radians
     * @param x    the x component of the axis
     * @param y    the y component of the axis
     * @param z    the z component of the axis
     * @param dest will hold the result
     * @return dest
     */
    Matrix3dc rotateLocal(double ang, double x, double y, double z, Matrix3dc dest);

    /**
     * Pre-multiply a rotation around the X axis to this matrix by rotating the given amount of radians about the X axis
     * and store the result in <code>dest</code>.
     * <p>
     * When used with a right-handed coordinate system, the produced rotation will rotate a vector counter-clockwise
     * around the rotation axis, when viewing along the negative axis direction towards the origin. When used with a
     * left-handed coordinate system, the rotation is clockwise.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the rotation matrix, then the new matrix will be
     * <code>R * M</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>R * M *
     * v</code>, the rotation will be applied last!
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Rotation_matrix_from_axis_and_angle">http://en.wikipedia.org</a>
     *
     * @param ang  the angle in radians to rotate about the X axis
     * @param dest will hold the result
     * @return dest
     */
    Matrix3dc rotateLocalX(double ang, Matrix3dc dest);

    /**
     * Pre-multiply a rotation around the Y axis to this matrix by rotating the given amount of radians about the Y axis
     * and store the result in <code>dest</code>.
     * <p>
     * When used with a right-handed coordinate system, the produced rotation will rotate a vector counter-clockwise
     * around the rotation axis, when viewing along the negative axis direction towards the origin. When used with a
     * left-handed coordinate system, the rotation is clockwise.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the rotation matrix, then the new matrix will be
     * <code>R * M</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>R * M *
     * v</code>, the rotation will be applied last!
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Rotation_matrix_from_axis_and_angle">http://en.wikipedia.org</a>
     *
     * @param ang  the angle in radians to rotate about the Y axis
     * @param dest will hold the result
     * @return dest
     */
    Matrix3dc rotateLocalY(double ang, Matrix3dc dest);

    /**
     * Pre-multiply a rotation around the Z axis to this matrix by rotating the given amount of radians about the Z axis
     * and store the result in <code>dest</code>.
     * <p>
     * When used with a right-handed coordinate system, the produced rotation will rotate a vector counter-clockwise
     * around the rotation axis, when viewing along the negative axis direction towards the origin. When used with a
     * left-handed coordinate system, the rotation is clockwise.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the rotation matrix, then the new matrix will be
     * <code>R * M</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>R * M *
     * v</code>, the rotation will be applied last!
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Rotation_matrix_from_axis_and_angle">http://en.wikipedia.org</a>
     *
     * @param ang  the angle in radians to rotate about the Z axis
     * @param dest will hold the result
     * @return dest
     */
    Matrix3dc rotateLocalZ(double ang, Matrix3dc dest);

    /**
     * Pre-multiply the rotation - and possibly scaling - transformation of the given {@link IQuaterniond} to this
     * matrix and store the result in <code>dest</code>.
     * <p>
     * When used with a right-handed coordinate system, the produced rotation will rotate a vector counter-clockwise
     * around the rotation axis, when viewing along the negative axis direction towards the origin. When used with a
     * left-handed coordinate system, the rotation is clockwise.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>Q</code> the rotation matrix obtained from the given
     * quaternion, then the new matrix will be <code>Q * M</code>. So when transforming a vector <code>v</code> with the
     * new matrix by using <code>Q * M * v</code>, the quaternion rotation will be applied last!
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Quaternion">http://en.wikipedia.org</a>
     *
     * @param quat the {@link IQuaterniond}
     * @param dest will hold the result
     * @return dest
     */
    Matrix3dc rotateLocal(IQuaterniond quat, Matrix3dc dest);

    /**
     * Pre-multiply the rotation - and possibly scaling - transformation of the given {@link IQuaternionf} to this
     * matrix and store the result in <code>dest</code>.
     * <p>
     * When used with a right-handed coordinate system, the produced rotation will rotate a vector counter-clockwise
     * around the rotation axis, when viewing along the negative axis direction towards the origin. When used with a
     * left-handed coordinate system, the rotation is clockwise.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>Q</code> the rotation matrix obtained from the given
     * quaternion, then the new matrix will be <code>Q * M</code>. So when transforming a vector <code>v</code> with the
     * new matrix by using <code>Q * M * v</code>, the quaternion rotation will be applied last!
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Quaternion">http://en.wikipedia.org</a>
     *
     * @param quat the {@link IQuaternionf}
     * @param dest will hold the result
     * @return dest
     */
    Matrix3dc rotateLocal(IQuaternionf quat, Matrix3dc dest);

    /**
     * Apply the rotation - and possibly scaling - transformation of the given {@link IQuaterniond} to this matrix and
     * store the result in <code>dest</code>.
     * <p>
     * When used with a right-handed coordinate system, the produced rotation will rotate a vector counter-clockwise
     * around the rotation axis, when viewing along the negative axis direction towards the origin. When used with a
     * left-handed coordinate system, the rotation is clockwise.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>Q</code> the rotation matrix obtained from the given
     * quaternion, then the new matrix will be <code>M * Q</code>. So when transforming a vector <code>v</code> with the
     * new matrix by using <code>M * Q * v</code>, the quaternion rotation will be applied first!
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Quaternion">http://en.wikipedia.org</a>
     *
     * @param quat the {@link IQuaterniond}
     * @param dest will hold the result
     * @return dest
     */
    Matrix3dc rotate(IQuaterniond quat, Matrix3dc dest);

    /**
     * Apply the rotation - and possibly scaling - transformation of the given {@link IQuaternionf} to this matrix and
     * store the result in <code>dest</code>.
     * <p>
     * When used with a right-handed coordinate system, the produced rotation will rotate a vector counter-clockwise
     * around the rotation axis, when viewing along the negative axis direction towards the origin. When used with a
     * left-handed coordinate system, the rotation is clockwise.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>Q</code> the rotation matrix obtained from the given
     * quaternion, then the new matrix will be <code>M * Q</code>. So when transforming a vector <code>v</code> with the
     * new matrix by using <code>M * Q * v</code>, the quaternion rotation will be applied first!
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Quaternion">http://en.wikipedia.org</a>
     *
     * @param quat the {@link IQuaternionf}
     * @param dest will hold the result
     * @return dest
     */
    Matrix3dc rotate(IQuaternionf quat, Matrix3dc dest);

    /**
     * Apply a rotation transformation, rotating about the given {@link AxisAngle4fc} and store the result in
     * <code>dest</code>.
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
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Axis_and_angle">http://en.wikipedia.org</a>
     *
     * @param axisAngle the {@link AxisAngle4fc} (needs to be {@link AxisAngle4fc#normalize() normalized})
     * @param dest      will hold the result
     * @return dest
     *
     * @see #rotate(double, double, double, double, Matrix3dc)
     */
    Matrix3dc rotate(AxisAngle4fc axisAngle, Matrix3dc dest);

    /**
     * Apply a rotation transformation, rotating about the given {@link AxisAngle4dc} and store the result in
     * <code>dest</code>.
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
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Axis_and_angle">http://en.wikipedia.org</a>
     *
     * @param axisAngle the {@link AxisAngle4dc} (needs to be {@link AxisAngle4dc#normalize() normalized})
     * @param dest      will hold the result
     * @return dest
     *
     * @see #rotate(double, double, double, double, Matrix3dc)
     */
    Matrix3dc rotate(AxisAngle4dc axisAngle, Matrix3dc dest);

    /**
     * Apply a rotation transformation, rotating the given radians about the specified axis and store the result in
     * <code>dest</code>.
     * <p>
     * The axis described by the <code>axis</code> vector needs to be a unit vector.
     * <p>
     * When used with a right-handed coordinate system, the produced rotation will rotate a vector counter-clockwise
     * around the rotation axis, when viewing along the negative axis direction towards the origin. When used with a
     * left-handed coordinate system, the rotation is clockwise.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>A</code> the rotation matrix obtained from the given axis
     * and angle, then the new matrix will be <code>M * A</code>. So when transforming a vector <code>v</code> with the
     * new matrix by using <code>M * A * v</code>, the axis-angle rotation will be applied first!
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Axis_and_angle">http://en.wikipedia.org</a>
     *
     * @param angle the angle in radians
     * @param axis  the rotation axis (needs to be {@link Vector3dc#normalize() normalized})
     * @param dest  will hold the result
     * @return dest
     *
     * @see #rotate(double, double, double, double, Matrix3dc)
     */
    Matrix3dc rotate(double angle, IVector3d axis, Matrix3dc dest);

    /**
     * Apply a rotation transformation, rotating the given radians about the specified axis and store the result in
     * <code>dest</code>.
     * <p>
     * The axis described by the <code>axis</code> vector needs to be a unit vector.
     * <p>
     * When used with a right-handed coordinate system, the produced rotation will rotate a vector counter-clockwise
     * around the rotation axis, when viewing along the negative axis direction towards the origin. When used with a
     * left-handed coordinate system, the rotation is clockwise.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>A</code> the rotation matrix obtained from the given axis
     * and angle, then the new matrix will be <code>M * A</code>. So when transforming a vector <code>v</code> with the
     * new matrix by using <code>M * A * v</code>, the axis-angle rotation will be applied first!
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Axis_and_angle">http://en.wikipedia.org</a>
     *
     * @param angle the angle in radians
     * @param axis  the rotation axis (needs to be {@link Vector3fc#normalize() normalized})
     * @param dest  will hold the result
     * @return dest
     *
     * @see #rotate(double, double, double, double, Matrix3dc)
     */
    Matrix3dc rotate(double angle, IVector3f axis, Matrix3dc dest);

    /**
     * Get the row at the given <code>row</code> index, starting with <code>0</code>.
     *
     * @param row  the row index in <tt>[0..2]</tt>
     * @param dest will hold the row components
     * @return the passed in destination
     */
    Vector3dc getRow(int row, Vector3dc dest);

    /**
     * Get the column at the given <code>column</code> index, starting with <code>0</code>.
     *
     * @param column the column index in <tt>[0..2]</tt>
     * @param dest   will hold the column components
     * @return the passed in destination
     */
    Vector3dc getColumn(int column, Vector3dc dest);

    /**
     * Get the matrix element value at the given column and row.
     *
     * @param column the colum index in <tt>[0..2]</tt>
     * @param row    the row index in <tt>[0..2]</tt>
     * @return the element value
     */
    double get(int column, int row);

    /**
     * Compute a normal matrix from <code>this</code> matrix and store it into <code>dest</code>.
     *
     * @param dest will hold the result
     * @return dest
     */
    Matrix3dc normal(Matrix3dc dest);

    /**
     * Apply a rotation transformation to this matrix to make <code>-z</code> point along <code>dir</code> and store the
     * result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>L</code> the lookalong rotation matrix, then the new
     * matrix will be <code>M * L</code>. So when transforming a vector <code>v</code> with the new matrix by using
     * <code>M * L * v</code>, the lookalong rotation transformation will be applied first!
     *
     * @param dir  the direction in space to look along
     * @param up   the direction of 'up'
     * @param dest will hold the result
     * @return dest
     *
     * @see #lookAlong(double, double, double, double, double, double, Matrix3dc)
     */
    Matrix3dc lookAlong(IVector3d dir, IVector3d up, Matrix3dc dest);

    /**
     * Apply a rotation transformation to this matrix to make <code>-z</code> point along <code>dir</code> and store the
     * result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>L</code> the lookalong rotation matrix, then the new
     * matrix will be <code>M * L</code>. So when transforming a vector <code>v</code> with the new matrix by using
     * <code>M * L * v</code>, the lookalong rotation transformation will be applied first!
     *
     * @param dirX the x-coordinate of the direction to look along
     * @param dirY the y-coordinate of the direction to look along
     * @param dirZ the z-coordinate of the direction to look along
     * @param upX  the x-coordinate of the up vector
     * @param upY  the y-coordinate of the up vector
     * @param upZ  the z-coordinate of the up vector
     * @param dest will hold the result
     * @return dest
     */
    Matrix3dc lookAlong(double dirX, double dirY, double dirZ, double upX, double upY, double upZ, Matrix3dc dest);

    /**
     * Get the scaling factors of <code>this</code> matrix for the three base axes.
     *
     * @param dest will hold the scaling factors for <tt>x</tt>, <tt>y</tt> and <tt>z</tt>
     * @return dest
     */
    Vector3dc getScale(Vector3dc dest);

    /**
     * Obtain the direction of <tt>+Z</tt> before the transformation represented by <code>this</code> matrix is
     * applied.
     * <p>
     * This method is equivalent to the following code:
     * <pre>
     * Matrix3dc inv = new Matrix3dc(this).invert();
     * inv.transform(dir.set(0, 0, 1)).normalize();
     * </pre>
     * If <code>this</code> is already an orthogonal matrix, then consider using {@link #normalizedPositiveZ(Vector3dc)}
     * instead.
     * <p>
     * Reference: <a href="http://www.euclideanspace.com/maths/algebra/matrix/functions/inverse/threeD/">http://www.euclideanspace.com</a>
     *
     * @param dir will hold the direction of <tt>+Z</tt>
     * @return dir
     */
    Vector3dc positiveZ(Vector3dc dir);

    /**
     * Obtain the direction of <tt>+Z</tt> before the transformation represented by <code>this</code> <i>orthogonal</i>
     * matrix is applied. This method only produces correct results if <code>this</code> is an <i>orthogonal</i>
     * matrix.
     * <p>
     * This method is equivalent to the following code:
     * <pre>
     * Matrix3dc inv = new Matrix3dc(this).transpose();
     * inv.transform(dir.set(0, 0, 1));
     * </pre>
     * <p>
     * Reference: <a href="http://www.euclideanspace.com/maths/algebra/matrix/functions/inverse/threeD/">http://www.euclideanspace.com</a>
     *
     * @param dir will hold the direction of <tt>+Z</tt>
     * @return dir
     */
    Vector3dc normalizedPositiveZ(Vector3dc dir);

    /**
     * Obtain the direction of <tt>+X</tt> before the transformation represented by <code>this</code> matrix is
     * applied.
     * <p>
     * This method is equivalent to the following code:
     * <pre>
     * Matrix3dc inv = new Matrix3dc(this).invert();
     * inv.transform(dir.set(1, 0, 0)).normalize();
     * </pre>
     * If <code>this</code> is already an orthogonal matrix, then consider using {@link #normalizedPositiveX(Vector3dc)}
     * instead.
     * <p>
     * Reference: <a href="http://www.euclideanspace.com/maths/algebra/matrix/functions/inverse/threeD/">http://www.euclideanspace.com</a>
     *
     * @param dir will hold the direction of <tt>+X</tt>
     * @return dir
     */
    Vector3dc positiveX(Vector3dc dir);

    /**
     * Obtain the direction of <tt>+X</tt> before the transformation represented by <code>this</code> <i>orthogonal</i>
     * matrix is applied. This method only produces correct results if <code>this</code> is an <i>orthogonal</i>
     * matrix.
     * <p>
     * This method is equivalent to the following code:
     * <pre>
     * Matrix3dc inv = new Matrix3dc(this).transpose();
     * inv.transform(dir.set(1, 0, 0));
     * </pre>
     * <p>
     * Reference: <a href="http://www.euclideanspace.com/maths/algebra/matrix/functions/inverse/threeD/">http://www.euclideanspace.com</a>
     *
     * @param dir will hold the direction of <tt>+X</tt>
     * @return dir
     */
    Vector3dc normalizedPositiveX(Vector3dc dir);

    /**
     * Obtain the direction of <tt>+Y</tt> before the transformation represented by <code>this</code> matrix is
     * applied.
     * <p>
     * This method is equivalent to the following code:
     * <pre>
     * Matrix3dc inv = new Matrix3dc(this).invert();
     * inv.transform(dir.set(0, 1, 0)).normalize();
     * </pre>
     * If <code>this</code> is already an orthogonal matrix, then consider using {@link #normalizedPositiveY(Vector3dc)}
     * instead.
     * <p>
     * Reference: <a href="http://www.euclideanspace.com/maths/algebra/matrix/functions/inverse/threeD/">http://www.euclideanspace.com</a>
     *
     * @param dir will hold the direction of <tt>+Y</tt>
     * @return dir
     */
    Vector3dc positiveY(Vector3dc dir);

    /**
     * Obtain the direction of <tt>+Y</tt> before the transformation represented by <code>this</code> <i>orthogonal</i>
     * matrix is applied. This method only produces correct results if <code>this</code> is an <i>orthogonal</i>
     * matrix.
     * <p>
     * This method is equivalent to the following code:
     * <pre>
     * Matrix3dc inv = new Matrix3dc(this).transpose();
     * inv.transform(dir.set(0, 1, 0));
     * </pre>
     * <p>
     * Reference: <a href="http://www.euclideanspace.com/maths/algebra/matrix/functions/inverse/threeD/">http://www.euclideanspace.com</a>
     *
     * @param dir will hold the direction of <tt>+Y</tt>
     * @return dir
     */
    Vector3dc normalizedPositiveY(Vector3dc dir);

    /**
     * Component-wise add <code>this</code> and <code>other</code> and store the result in <code>dest</code>.
     *
     * @param other the other addend
     * @param dest  will hold the result
     * @return dest
     */
    Matrix3dc add(IMatrix3d other, Matrix3dc dest);

    /**
     * Component-wise subtract <code>subtrahend</code> from <code>this</code> and store the result in
     * <code>dest</code>.
     *
     * @param subtrahend the subtrahend
     * @param dest       will hold the result
     * @return dest
     */
    Matrix3dc sub(IMatrix3d subtrahend, Matrix3dc dest);

    /**
     * Component-wise multiply <code>this</code> by <code>other</code> and store the result in <code>dest</code>.
     *
     * @param other the other matrix
     * @param dest  will hold the result
     * @return dest
     */
    Matrix3dc mulComponentWise(IMatrix3d other, Matrix3dc dest);

    /**
     * Linearly interpolate <code>this</code> and <code>other</code> using the given interpolation factor <code>t</code>
     * and store the result in <code>dest</code>.
     * <p>
     * If <code>t</code> is <tt>0.0</tt> then the result is <code>this</code>. If the interpolation factor is
     * <code>1.0</code> then the result is <code>other</code>.
     *
     * @param other the other matrix
     * @param t     the interpolation factor between 0.0 and 1.0
     * @param dest  will hold the result
     * @return dest
     */
    Matrix3dc lerp(IMatrix3d other, double t, Matrix3dc dest);

    /**
     * Apply a model transformation to this matrix for a right-handed coordinate system, that aligns the local
     * <code>+Z</code> axis with <code>direction</code> and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>L</code> the lookat matrix, then the new matrix will be
     * <code>M * L</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>M * L *
     * v</code>, the lookat transformation will be applied first!
     * <p>
     * This method is equivalent to calling: <tt>mul(new Matrix3d().lookAlong(new IVector3d(dir).negate(), up).invert(),
     * dest)</tt>
     *
     * @param direction the direction to rotate towards
     * @param up        the model's up vector
     * @param dest      will hold the result
     * @return dest
     *
     * @see #rotateTowards(double, double, double, double, double, double, Matrix3dc)
     */
    Matrix3dc rotateTowards(IVector3d direction, IVector3d up, Matrix3dc dest);

    /**
     * Apply a model transformation to this matrix for a right-handed coordinate system, that aligns the local
     * <code>+Z</code> axis with <code>dir</code> and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>L</code> the lookat matrix, then the new matrix will be
     * <code>M * L</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>M * L *
     * v</code>, the lookat transformation will be applied first!
     * <p>
     * This method is equivalent to calling: <tt>mul(new Matrix3d().lookAlong(-dirX, -dirY, -dirZ, upX, upY,
     * upZ).invert(), dest)</tt>
     *
     * @param dirX the x-coordinate of the direction to rotate towards
     * @param dirY the y-coordinate of the direction to rotate towards
     * @param dirZ the z-coordinate of the direction to rotate towards
     * @param upX  the x-coordinate of the up vector
     * @param upY  the y-coordinate of the up vector
     * @param upZ  the z-coordinate of the up vector
     * @param dest will hold the result
     * @return dest
     *
     * @see #rotateTowards(IVector3d, IVector3d, Matrix3dc)
     */
    Matrix3dc rotateTowards(double dirX, double dirY, double dirZ, double upX, double upY, double upZ, Matrix3dc dest);

    /**
     * Extract the Euler angles from the rotation represented by <code>this</code> matrix and store the extracted Euler
     * angles in <code>dest</code>.
     * <p>
     * This method assumes that <code>this</code> matrix only represents a rotation without scaling.
     * <p>
     * Note that the returned Euler angles must be applied in the order <tt>Z * Y * X</tt> to obtain the identical
     * matrix. This means that calling {@link IMatrix3d#rotateZYX(double, double, double, Matrix3dc)} using the obtained
     * Euler angles will yield the same rotation as the original matrix from which the Euler angles were obtained, so in
     * the below code the matrix <tt>m2</tt> should be identical to <tt>m</tt> (disregarding possible floating-point
     * inaccuracies).
     * <pre>
     * Matrix3d m = ...; // &lt;- matrix only representing rotation
     * Matrix3dc n = new Matrix3dc();
     * n.rotateZYX(m.getEulerAnglesZYX(new IVector3d()));
     * </pre>
     * <p>
     * Reference: <a href="http://nghiaho.com/?page_id=846">http://nghiaho.com/</a>
     *
     * @param dest will hold the extracted Euler angles
     * @return dest
     */
    Vector3dc getEulerAnglesZYX(Vector3dc dest);
}