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

import org.joml.api.AxisAngle4fc;
import org.joml.api.quaternion.IQuaternionf;
import org.joml.api.quaternion.Quaterniondc;
import org.joml.api.quaternion.Quaternionfc;
import org.joml.api.vector.IVector3f;
import org.joml.api.vector.Vector3fc;

//#ifdef __HAS_NIO__
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
//#endif

//#ifdef __GWT__
import com.google.gwt.typedarrays.shared.Float32Array;
//#endif

/**
 * Interface to a read-only view of a 3x3 matrix of single-precision floats.
 *
 * @author Kai Burjack
 */
public interface IMatrix3f {

    /**
     * Return the value of the matrix element at column 0 and row 0.
     *
     * @return the value of the matrix element
     */
    float m00();

    /**
     * Return the value of the matrix element at column 0 and row 1.
     *
     * @return the value of the matrix element
     */
    float m01();

    /**
     * Return the value of the matrix element at column 0 and row 2.
     *
     * @return the value of the matrix element
     */
    float m02();

    /**
     * Return the value of the matrix element at column 1 and row 0.
     *
     * @return the value of the matrix element
     */
    float m10();

    /**
     * Return the value of the matrix element at column 1 and row 1.
     *
     * @return the value of the matrix element
     */
    float m11();

    /**
     * Return the value of the matrix element at column 1 and row 2.
     *
     * @return the value of the matrix element
     */
    float m12();

    /**
     * Return the value of the matrix element at column 2 and row 0.
     *
     * @return the value of the matrix element
     */
    float m20();

    /**
     * Return the value of the matrix element at column 2 and row 1.
     *
     * @return the value of the matrix element
     */
    float m21();

    /**
     * Return the value of the matrix element at column 2 and row 2.
     *
     * @return the value of the matrix element
     */
    float m22();

    /**
     * Multiply this matrix by the supplied <code>right</code> matrix and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the <code>right</code> matrix, then the new
     * matrix will be <code>M * R</code>. So when transforming a vector <code>v</code> with the new matrix by using
     * <code>M * R * v</code>, the transformation of the right matrix will be applied first!
     *
     * @param right the right operand of the matrix multiplication
     * @param dest  will hold the result
     * @return dest
     */
    Matrix3fc mul(IMatrix3f right, Matrix3fc dest);

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
    Matrix3fc mulLocal(IMatrix3f left, Matrix3fc dest);

    /**
     * Return the determinant of this matrix.
     *
     * @return the determinant
     */
    float determinant();

    /**
     * Invert the <code>this</code> matrix and store the result in <code>dest</code>.
     *
     * @param dest will hold the result
     * @return dest
     */
    Matrix3fc invert(Matrix3fc dest);

    /**
     * Transpose <code>this</code> matrix and store the result in <code>dest</code>.
     *
     * @param dest will hold the result
     * @return dest
     */
    Matrix3fc transpose(Matrix3fc dest);

    /**
     * Get the current values of <code>this</code> matrix and store them into <code>dest</code>.
     *
     * @param dest the destination matrix
     * @return the passed in destination
     */
    Matrix3fc get(Matrix3fc dest);

    /**
     * Get the current values of <code>this</code> matrix and store them as the rotational component of
     * <code>dest</code>. All other values of <code>dest</code> will be set to identity.
     *
     * @param dest the destination matrix
     * @return the passed in destination
     *
     * @see Matrix4fc#set(IMatrix3f)
     */
    Matrix4fc get(Matrix4fc dest);

    /**
     * Get the current values of <code>this</code> matrix and store the represented rotation into the given {@link
     * AxisAngle4fc}.
     *
     * @param dest the destination {@link AxisAngle4fc}
     * @return the passed in destination
     *
     * @see AxisAngle4fc#set(IMatrix3f)
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
     * @see Quaternionfc#setFromUnnormalized(IMatrix3f)
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
     * @see Quaternionfc#setFromNormalized(IMatrix3f)
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
     * @see Quaterniondc#setFromUnnormalized(IMatrix3f)
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
     * @see Quaterniondc#setFromNormalized(IMatrix3f)
     */
    Quaterniondc getNormalizedRotation(Quaterniondc dest);

    //#ifdef __GWT__

    /**
     * Store this matrix in column-major order into the supplied {@link Float32Array}.
     *
     * @param buffer will receive the values of this matrix in column-major order
     * @return the passed in buffer
     */
    Float32Array get(Float32Array buffer);

    /**
     * Store this matrix in column-major order into the supplied {@link Float32Array} at the given index.
     *
     * @param index  the index at which to store this matrix in the supplied Float32Array
     * @param buffer will receive the values of this matrix in column-major order
     * @return the passed in buffer
     */
    Float32Array get(int index, Float32Array buffer);
    //#endif

    //#ifdef __HAS_NIO__

    /**
     * Store this matrix in column-major order into the supplied {@link FloatBuffer} at the current buffer {@link
     * FloatBuffer#position() position}.
     * <p>
     * This method will not increment the position of the given FloatBuffer.
     * <p>
     * In order to specify the offset into the FloatBuffer at which the matrix is stored, use {@link #get(int,
     * FloatBuffer)}, taking the absolute position as parameter.
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
     * Store the transpose of this matrix in column-major order into the supplied {@link FloatBuffer} at the current
     * buffer {@link FloatBuffer#position() position}.
     * <p>
     * This method will not increment the position of the given FloatBuffer.
     * <p>
     * In order to specify the offset into the FloatBuffer at which the matrix is stored, use {@link #getTransposed(int,
     * FloatBuffer)}, taking the absolute position as parameter.
     *
     * @param buffer will receive the values of this matrix in column-major order at its current position
     * @return the passed in buffer
     *
     * @see #getTransposed(int, FloatBuffer)
     */
    FloatBuffer getTransposed(FloatBuffer buffer);

    /**
     * Store the transpose of this matrix in column-major order into the supplied {@link FloatBuffer} starting at the
     * specified absolute buffer position/index.
     * <p>
     * This method will not increment the position of the given FloatBuffer.
     *
     * @param index  the absolute position into the FloatBuffer
     * @param buffer will receive the values of this matrix in column-major order
     * @return the passed in buffer
     */
    FloatBuffer getTransposed(int index, FloatBuffer buffer);

    /**
     * Store the transpose of this matrix in column-major order into the supplied {@link ByteBuffer} at the current
     * buffer {@link ByteBuffer#position() position}.
     * <p>
     * This method will not increment the position of the given ByteBuffer.
     * <p>
     * In order to specify the offset into the ByteBuffer at which the matrix is stored, use {@link #getTransposed(int,
     * ByteBuffer)}, taking the absolute position as parameter.
     *
     * @param buffer will receive the values of this matrix in column-major order at its current position
     * @return the passed in buffer
     *
     * @see #getTransposed(int, ByteBuffer)
     */
    ByteBuffer getTransposed(ByteBuffer buffer);

    /**
     * Store the transpose of this matrix in column-major order into the supplied {@link ByteBuffer} starting at the
     * specified absolute buffer position/index.
     * <p>
     * This method will not increment the position of the given ByteBuffer.
     *
     * @param index  the absolute position into the ByteBuffer
     * @param buffer will receive the values of this matrix in column-major order
     * @return the passed in buffer
     */
    ByteBuffer getTransposed(int index, ByteBuffer buffer);
    //#endif

    /**
     * Store this matrix into the supplied float array in column-major order at the given offset.
     *
     * @param arr    the array to write the matrix values into
     * @param offset the offset into the array
     * @return the passed in array
     */
    float[] get(float[] arr, int offset);

    /**
     * Store this matrix into the supplied float array in column-major order.
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
    Matrix3fc scale(IVector3f xyz, Matrix3fc dest);

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
    Matrix3fc scale(float x, float y, float z, Matrix3fc dest);

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
     * @see #scale(float, float, float, Matrix3fc)
     */
    Matrix3fc scale(float xyz, Matrix3fc dest);

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
    Matrix3fc scaleLocal(float x, float y, float z, Matrix3fc dest);

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
    Vector3fc transform(float x, float y, float z, Vector3fc dest);

    /**
     * Transform the given vector by the transpose of this matrix.
     *
     * @param v the vector to transform
     * @return v
     */
    Vector3fc transformTranspose(Vector3fc v);

    /**
     * Transform the given vector by the transpose of this matrix and store the result in <code>dest</code>.
     *
     * @param v    the vector to transform
     * @param dest will hold the result
     * @return dest
     */
    Vector3fc transformTranspose(IVector3f v, Vector3fc dest);

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
    Vector3fc transformTranspose(float x, float y, float z, Vector3fc dest);

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
    Matrix3fc rotateX(float ang, Matrix3fc dest);

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
    Matrix3fc rotateY(float ang, Matrix3fc dest);

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
    Matrix3fc rotateZ(float ang, Matrix3fc dest);

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
    Matrix3fc rotateXYZ(float angleX, float angleY, float angleZ, Matrix3fc dest);

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
    Matrix3fc rotateZYX(float angleZ, float angleY, float angleX, Matrix3fc dest);

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
    Matrix3fc rotateYXZ(float angleY, float angleX, float angleZ, Matrix3fc dest);

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
    Matrix3fc rotate(float ang, float x, float y, float z, Matrix3fc dest);

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
    Matrix3fc rotateLocal(float ang, float x, float y, float z, Matrix3fc dest);

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
    Matrix3fc rotateLocalX(float ang, Matrix3fc dest);

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
    Matrix3fc rotateLocalY(float ang, Matrix3fc dest);

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
    Matrix3fc rotateLocalZ(float ang, Matrix3fc dest);

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
    Matrix3fc rotate(IQuaternionf quat, Matrix3fc dest);

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
    Matrix3fc rotateLocal(IQuaternionf quat, Matrix3fc dest);

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
     * @see #rotate(float, float, float, float, Matrix3fc)
     */
    Matrix3fc rotate(AxisAngle4fc axisAngle, Matrix3fc dest);

    /**
     * Apply a rotation transformation, rotating the given radians about the specified axis and store the result in
     * <code>dest</code>.
     * <p>
     * When used with a right-handed coordinate system, the produced rotation will rotate a vector counter-clockwise
     * around the rotation axis, when viewing along the negative axis direction towards the origin. When used with a
     * left-handed coordinate system, the rotation is clockwise.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>A</code> the rotation matrix obtained from the given
     * angle and axis, then the new matrix will be <code>M * A</code>. So when transforming a vector <code>v</code> with
     * the new matrix by using <code>M * A * v</code>, the axis-angle rotation will be applied first!
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Axis_and_angle">http://en.wikipedia.org</a>
     *
     * @param angle the angle in radians
     * @param axis  the rotation axis (needs to be {@link Vector3fc#normalize() normalized})
     * @param dest  will hold the result
     * @return dest
     *
     * @see #rotate(float, float, float, float, Matrix3fc)
     */
    Matrix3fc rotate(float angle, IVector3f axis, Matrix3fc dest);

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
     * @see #lookAlong(float, float, float, float, float, float, Matrix3fc)
     */
    Matrix3fc lookAlong(IVector3f dir, IVector3f up, Matrix3fc dest);

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
    Matrix3fc lookAlong(float dirX, float dirY, float dirZ, float upX, float upY, float upZ, Matrix3fc dest);

    /**
     * Get the row at the given <code>row</code> index, starting with <code>0</code>.
     *
     * @param row  the row index in <tt>[0..2]</tt>
     * @param dest will hold the row components
     * @return the passed in destination
     */
    Vector3fc getRow(int row, Vector3fc dest);

    /**
     * Get the column at the given <code>column</code> index, starting with <code>0</code>.
     *
     * @param column the column index in <tt>[0..2]</tt>
     * @param dest   will hold the column components
     * @return the passed in destination
     */
    Vector3fc getColumn(int column, Vector3fc dest);

    /**
     * Get the matrix element value at the given column and row.
     *
     * @param column the colum index in <tt>[0..2]</tt>
     * @param row    the row index in <tt>[0..2]</tt>
     * @return the element value
     */
    float get(int column, int row);

    /**
     * Compute a normal matrix from <code>this</code> matrix and store it into <code>dest</code>.
     *
     * @param dest will hold the result
     * @return dest
     */
    Matrix3fc normal(Matrix3fc dest);

    /**
     * Get the scaling factors of <code>this</code> matrix for the three base axes.
     *
     * @param dest will hold the scaling factors for <tt>x</tt>, <tt>y</tt> and <tt>z</tt>
     * @return dest
     */
    Vector3fc getScale(Vector3fc dest);

    /**
     * Obtain the direction of <tt>+Z</tt> before the transformation represented by <code>this</code> matrix is
     * applied.
     * <p>
     * This method is equivalent to the following code:
     * <pre>
     * Matrix3fc inv = new Matrix3fc(this).invert();
     * inv.transform(dir.set(0, 0, 1)).normalize();
     * </pre>
     * If <code>this</code> is already an orthogonal matrix, then consider using {@link #normalizedPositiveZ(Vector3fc)}
     * instead.
     * <p>
     * Reference: <a href="http://www.euclideanspace.com/maths/algebra/matrix/functions/inverse/threeD/">http://www.euclideanspace.com</a>
     *
     * @param dir will hold the direction of <tt>+Z</tt>
     * @return dir
     */
    Vector3fc positiveZ(Vector3fc dir);

    /**
     * Obtain the direction of <tt>+Z</tt> before the transformation represented by <code>this</code> <i>orthogonal</i>
     * matrix is applied. This method only produces correct results if <code>this</code> is an <i>orthogonal</i>
     * matrix.
     * <p>
     * This method is equivalent to the following code:
     * <pre>
     * Matrix3fc inv = new Matrix3fc(this).transpose();
     * inv.transform(dir.set(0, 0, 1));
     * </pre>
     * <p>
     * Reference: <a href="http://www.euclideanspace.com/maths/algebra/matrix/functions/inverse/threeD/">http://www.euclideanspace.com</a>
     *
     * @param dir will hold the direction of <tt>+Z</tt>
     * @return dir
     */
    Vector3fc normalizedPositiveZ(Vector3fc dir);

    /**
     * Obtain the direction of <tt>+X</tt> before the transformation represented by <code>this</code> matrix is
     * applied.
     * <p>
     * This method is equivalent to the following code:
     * <pre>
     * Matrix3fc inv = new Matrix3fc(this).invert();
     * inv.transform(dir.set(1, 0, 0)).normalize();
     * </pre>
     * If <code>this</code> is already an orthogonal matrix, then consider using {@link #normalizedPositiveX(Vector3fc)}
     * instead.
     * <p>
     * Reference: <a href="http://www.euclideanspace.com/maths/algebra/matrix/functions/inverse/threeD/">http://www.euclideanspace.com</a>
     *
     * @param dir will hold the direction of <tt>+X</tt>
     * @return dir
     */
    Vector3fc positiveX(Vector3fc dir);

    /**
     * Obtain the direction of <tt>+X</tt> before the transformation represented by <code>this</code> <i>orthogonal</i>
     * matrix is applied. This method only produces correct results if <code>this</code> is an <i>orthogonal</i>
     * matrix.
     * <p>
     * This method is equivalent to the following code:
     * <pre>
     * Matrix3fc inv = new Matrix3fc(this).transpose();
     * inv.transform(dir.set(1, 0, 0));
     * </pre>
     * <p>
     * Reference: <a href="http://www.euclideanspace.com/maths/algebra/matrix/functions/inverse/threeD/">http://www.euclideanspace.com</a>
     *
     * @param dir will hold the direction of <tt>+X</tt>
     * @return dir
     */
    Vector3fc normalizedPositiveX(Vector3fc dir);

    /**
     * Obtain the direction of <tt>+Y</tt> before the transformation represented by <code>this</code> matrix is
     * applied.
     * <p>
     * This method is equivalent to the following code:
     * <pre>
     * Matrix3fc inv = new Matrix3fc(this).invert();
     * inv.transform(dir.set(0, 1, 0)).normalize();
     * </pre>
     * If <code>this</code> is already an orthogonal matrix, then consider using {@link #normalizedPositiveY(Vector3fc)}
     * instead.
     * <p>
     * Reference: <a href="http://www.euclideanspace.com/maths/algebra/matrix/functions/inverse/threeD/">http://www.euclideanspace.com</a>
     *
     * @param dir will hold the direction of <tt>+Y</tt>
     * @return dir
     */
    Vector3fc positiveY(Vector3fc dir);

    /**
     * Obtain the direction of <tt>+Y</tt> before the transformation represented by <code>this</code> <i>orthogonal</i>
     * matrix is applied. This method only produces correct results if <code>this</code> is an <i>orthogonal</i>
     * matrix.
     * <p>
     * This method is equivalent to the following code:
     * <pre>
     * Matrix3fc inv = new Matrix3fc(this).transpose();
     * inv.transform(dir.set(0, 1, 0));
     * </pre>
     * <p>
     * Reference: <a href="http://www.euclideanspace.com/maths/algebra/matrix/functions/inverse/threeD/">http://www.euclideanspace.com</a>
     *
     * @param dir will hold the direction of <tt>+Y</tt>
     * @return dir
     */
    Vector3fc normalizedPositiveY(Vector3fc dir);

    /**
     * Component-wise add <code>this</code> and <code>other</code> and store the result in <code>dest</code>.
     *
     * @param other the other addend
     * @param dest  will hold the result
     * @return dest
     */
    Matrix3fc add(IMatrix3f other, Matrix3fc dest);

    /**
     * Component-wise subtract <code>subtrahend</code> from <code>this</code> and store the result in
     * <code>dest</code>.
     *
     * @param subtrahend the subtrahend
     * @param dest       will hold the result
     * @return dest
     */
    Matrix3fc sub(IMatrix3f subtrahend, Matrix3fc dest);

    /**
     * Component-wise multiply <code>this</code> by <code>other</code> and store the result in <code>dest</code>.
     *
     * @param other the other matrix
     * @param dest  will hold the result
     * @return dest
     */
    Matrix3fc mulComponentWise(IMatrix3f other, Matrix3fc dest);

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
    Matrix3fc lerp(IMatrix3f other, float t, Matrix3fc dest);

    /**
     * Apply a model transformation to this matrix for a right-handed coordinate system, that aligns the local
     * <code>+Z</code> axis with <code>direction</code> and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>L</code> the lookat matrix, then the new matrix will be
     * <code>M * L</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>M * L *
     * v</code>, the lookat transformation will be applied first!
     * <p>
     * This method is equivalent to calling: <tt>mul(new Matrix3f().lookAlong(new IVector3f(dir).negate(), up).invert(),
     * dest)</tt>
     *
     * @param direction the direction to rotate towards
     * @param up        the model's up vector
     * @param dest      will hold the result
     * @return dest
     *
     * @see #rotateTowards(float, float, float, float, float, float, Matrix3fc)
     */
    Matrix3fc rotateTowards(IVector3f direction, IVector3f up, Matrix3fc dest);

    /**
     * Apply a model transformation to this matrix for a right-handed coordinate system, that aligns the local
     * <code>+Z</code> axis with <code>dir</code> and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>L</code> the lookat matrix, then the new matrix will be
     * <code>M * L</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>M * L *
     * v</code>, the lookat transformation will be applied first!
     * <p>
     * This method is equivalent to calling: <tt>mul(new Matrix3f().lookAlong(-dirX, -dirY, -dirZ, upX, upY,
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
     * @see #rotateTowards(IVector3f, IVector3f, Matrix3fc)
     */
    Matrix3fc rotateTowards(float dirX, float dirY, float dirZ, float upX, float upY, float upZ, Matrix3fc dest);

    /**
     * Extract the Euler angles from the rotation represented by <code>this</code> matrix and store the extracted Euler
     * angles in <code>dest</code>.
     * <p>
     * This method assumes that <code>this</code> matrix only represents a rotation without scaling.
     * <p>
     * Note that the returned Euler angles must be applied in the order <tt>Z * Y * X</tt> to obtain the identical
     * matrix. This means that calling {@link IMatrix3f#rotateZYX(float, float, float, Matrix3fc)} using the obtained
     * Euler angles will yield the same rotation as the original matrix from which the Euler angles were obtained, so in
     * the below code the matrix <tt>m2</tt> should be identical to <tt>m</tt> (disregarding possible floating-point
     * inaccuracies).
     * <pre>
     * Matrix3f m = ...; // &lt;- matrix only representing rotation
     * Matrix3fc n = new Matrix3fc();
     * n.rotateZYX(m.getEulerAnglesZYX(new IVector3f()));
     * </pre>
     * <p>
     * Reference: <a href="http://nghiaho.com/?page_id=846">http://nghiaho.com/</a>
     *
     * @param dest will hold the extracted Euler angles
     * @return dest
     */
    Vector3fc getEulerAnglesZYX(Vector3fc dest);
}