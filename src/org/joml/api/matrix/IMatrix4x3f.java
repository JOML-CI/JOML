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
import org.joml.api.Planefc;
import org.joml.api.quaternion.IQuaternionf;
import org.joml.api.quaternion.Quaterniondc;
import org.joml.api.quaternion.Quaternionfc;
import org.joml.api.vector.IVector3f;
import org.joml.api.vector.IVector4f;
import org.joml.api.vector.Vector3fc;
import org.joml.api.vector.Vector4fc;

//#ifdef __HAS_NIO__
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
//#endif

//#ifdef __GWT__
import com.google.gwt.typedarrays.shared.Float32Array;
//#endif

/**
 * Interface to a read-only view of a 4x3 matrix of single-precision floats.
 *
 * @author Kai Burjack
 */
public interface IMatrix4x3f {

    /**
     * Argument to the first parameter of {@link #frustumPlane(int, Planefc)} identifying the plane with equation
     * <tt>x=-1</tt> when using the identity matrix.
     */
    int PLANE_NX = 0;

    /**
     * Argument to the first parameter of {@link #frustumPlane(int, Planefc)} identifying the plane with equation
     * <tt>x=1</tt> when using the identity matrix.
     */
    int PLANE_PX = 1;

    /**
     * Argument to the first parameter of {@link #frustumPlane(int, Planefc)} identifying the plane with equation
     * <tt>y=-1</tt> when using the identity matrix.
     */
    int PLANE_NY = 2;

    /**
     * Argument to the first parameter of {@link #frustumPlane(int, Planefc)} identifying the plane with equation
     * <tt>y=1</tt> when using the identity matrix.
     */
    int PLANE_PY = 3;

    /**
     * Argument to the first parameter of {@link #frustumPlane(int, Planefc)} identifying the plane with equation
     * <tt>z=-1</tt> when using the identity matrix.
     */
    int PLANE_NZ = 4;

    /**
     * Argument to the first parameter of {@link #frustumPlane(int, Planefc)} identifying the plane with equation
     * <tt>z=1</tt> when using the identity matrix.
     */
    int PLANE_PZ = 5;

    /**
     * Bit returned by {@link #properties()} to indicate that the matrix represents the identity transformation.
     */
    byte PROPERTY_IDENTITY = 1 << 2;

    /**
     * Bit returned by {@link #properties()} to indicate that the matrix represents a pure translation transformation.
     */
    byte PROPERTY_TRANSLATION = 1 << 3;

    /**
     * @return the properties of the matrix
     */
    byte properties();

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
     * Return the value of the matrix element at column 3 and row 0.
     *
     * @return the value of the matrix element
     */
    float m30();

    /**
     * Return the value of the matrix element at column 3 and row 1.
     *
     * @return the value of the matrix element
     */
    float m31();

    /**
     * Return the value of the matrix element at column 3 and row 2.
     *
     * @return the value of the matrix element
     */
    float m32();

    /**
     * Get the current values of <code>this</code> matrix and store them into the upper 4x3 submatrix of
     * <code>dest</code>.
     * <p>
     * The other elements of <code>dest</code> will not be modified.
     *
     * @param dest the destination matrix
     * @return dest
     *
     * @see Matrix4fc#set4x3(IMatrix4x3f)
     */
    Matrix4fc get(Matrix4fc dest);

    /**
     * Get the current values of <code>this</code> matrix and store them into the upper 4x3 submatrix of
     * <code>dest</code>.
     * <p>
     * The other elements of <code>dest</code> will not be modified.
     *
     * @param dest the destination matrix
     * @return dest
     *
     * @see Matrix4dc#set4x3(IMatrix4x3f)
     */
    Matrix4dc get(Matrix4dc dest);

    /**
     * Multiply this matrix by the supplied <code>right</code> matrix and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the <code>right</code> matrix, then the new
     * matrix will be <code>M * R</code>. So when transforming a vector <code>v</code> with the new matrix by using
     * <code>M * R * v</code>, the transformation of the right matrix will be applied first!
     *
     * @param right the right operand of the matrix multiplication
     * @param dest  the destination matrix, which will hold the result
     * @return dest
     */
    Matrix4x3fc mul(IMatrix4x3f right, Matrix4x3fc dest);

    /**
     * Multiply this matrix, which is assumed to only contain a translation, by the supplied <code>right</code> matrix
     * and store the result in <code>dest</code>.
     * <p>
     * This method assumes that <code>this</code> matrix only contains a translation.
     * <p>
     * This method will not modify either the last row of <code>this</code> or the last row of <code>right</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the <code>right</code> matrix, then the new
     * matrix will be <code>M * R</code>. So when transforming a vector <code>v</code> with the new matrix by using
     * <code>M * R * v</code>, the transformation of the right matrix will be applied first!
     *
     * @param right the right operand of the matrix multiplication
     * @param dest  the destination matrix, which will hold the result
     * @return dest
     */
    Matrix4x3fc mulTranslation(IMatrix4x3f right, Matrix4x3fc dest);

    /**
     * Multiply <code>this</code> orthographic projection matrix by the supplied <code>view</code> matrix and store the
     * result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>V</code> the <code>view</code> matrix, then the new
     * matrix will be <code>M * V</code>. So when transforming a vector <code>v</code> with the new matrix by using
     * <code>M * V * v</code>, the transformation of the <code>view</code> matrix will be applied first!
     *
     * @param view the matrix which to multiply <code>this</code> with
     * @param dest the destination matrix, which will hold the result
     * @return dest
     */
    Matrix4x3fc mulOrtho(IMatrix4x3f view, Matrix4x3fc dest);

    /**
     * Component-wise add <code>this</code> and <code>other</code> by first multiplying each component of
     * <code>other</code> by <code>otherFactor</code>, adding that to <code>this</code> and storing the final result in
     * <code>dest</code>.
     * <p>
     * The other components of <code>dest</code> will be set to the ones of <code>this</code>.
     * <p>
     * The matrices <code>this</code> and <code>other</code> will not be changed.
     *
     * @param other       the other matrix
     * @param otherFactor the factor to multiply each of the other matrix's components
     * @param dest        will hold the result
     * @return dest
     */
    Matrix4x3fc fma(IMatrix4x3f other, float otherFactor, Matrix4x3fc dest);

    /**
     * Component-wise add <code>this</code> and <code>other</code> and store the result in <code>dest</code>.
     *
     * @param other the other addend
     * @param dest  will hold the result
     * @return dest
     */
    Matrix4x3fc add(IMatrix4x3f other, Matrix4x3fc dest);

    /**
     * Component-wise subtract <code>subtrahend</code> from <code>this</code> and store the result in
     * <code>dest</code>.
     *
     * @param subtrahend the subtrahend
     * @param dest       will hold the result
     * @return dest
     */
    Matrix4x3fc sub(IMatrix4x3f subtrahend, Matrix4x3fc dest);

    /**
     * Component-wise multiply <code>this</code> by <code>other</code> and store the result in <code>dest</code>.
     *
     * @param other the other matrix
     * @param dest  will hold the result
     * @return dest
     */
    Matrix4x3fc mulComponentWise(IMatrix4x3f other, Matrix4x3fc dest);

    /**
     * Return the determinant of this matrix.
     *
     * @return the determinant
     */
    float determinant();

    /**
     * Invert this matrix and write the result into <code>dest</code>.
     *
     * @param dest will hold the result
     * @return dest
     */
    Matrix4x3fc invert(Matrix4x3fc dest);

    /**
     * Invert <code>this</code> orthographic projection matrix and store the result into the given <code>dest</code>.
     * <p>
     * This method can be used to quickly obtain the inverse of an orthographic projection matrix.
     *
     * @param dest will hold the inverse of <code>this</code>
     * @return dest
     */
    Matrix4x3fc invertOrtho(Matrix4x3fc dest);

    /**
     * Invert this matrix by assuming that it has unit scaling (i.e. {@link #transformDirection(Vector3fc)
     * transformDirection} does not change the {@link Vector3fc#length() length} of the vector) and write the result
     * into <code>dest</code>.
     * <p>
     * Reference: <a href="http://www.gamedev.net/topic/425118-inverse--matrix/">http://www.gamedev.net/</a>
     *
     * @param dest will hold the result
     * @return dest
     */
    Matrix4x3fc invertUnitScale(Matrix4x3fc dest);

    /**
     * Transpose only the left 3x3 submatrix of this matrix and store the result in <code>dest</code>.
     * <p>
     * All other matrix elements are left unchanged.
     *
     * @param dest will hold the result
     * @return dest
     */
    Matrix4x3fc transpose3x3(Matrix4x3fc dest);

    /**
     * Transpose only the left 3x3 submatrix of this matrix and store the result in <code>dest</code>.
     *
     * @param dest will hold the result
     * @return dest
     */
    Matrix3fc transpose3x3(Matrix3fc dest);

    /**
     * Get only the translation components <tt>(m30, m31, m32)</tt> of this matrix and store them in the given vector
     * <code>xyz</code>.
     *
     * @param dest will hold the translation components of this matrix
     * @return dest
     */
    Vector3fc getTranslation(Vector3fc dest);

    /**
     * Get the scaling factors of <code>this</code> matrix for the three base axes.
     *
     * @param dest will hold the scaling factors for <tt>x</tt>, <tt>y</tt> and <tt>z</tt>
     * @return dest
     */
    Vector3fc getScale(Vector3fc dest);

    /**
     * Get the current values of <code>this</code> matrix and store them into <code>dest</code>.
     *
     * @param dest the destination matrix
     * @return the passed in destination
     */
    Matrix4x3fc get(Matrix4x3fc dest);

    /**
     * Get the current values of <code>this</code> matrix and store them into <code>dest</code>.
     *
     * @param dest the destination matrix
     * @return the passed in destination
     */
    Matrix4x3dc get(Matrix4x3dc dest);

    /**
     * Get the rotational component of <code>this</code> matrix and store the represented rotation into the given {@link
     * AxisAngle4fc}.
     *
     * @param dest the destination {@link AxisAngle4fc}
     * @return the passed in destination
     *
     * @see AxisAngle4fc#set(IMatrix4x3f)
     */
    AxisAngle4fc getRotation(AxisAngle4fc dest);

    /**
     * Get the rotational component of <code>this</code> matrix and store the represented rotation into the given {@link
     * AxisAngle4dc}.
     *
     * @param dest the destination {@link AxisAngle4dc}
     * @return the passed in destination
     *
     * @see AxisAngle4fc#set(IMatrix4x3f)
     */
    AxisAngle4dc getRotation(AxisAngle4dc dest);

    /**
     * Get the current values of <code>this</code> matrix and store the represented rotation into the given {@link
     * Quaternionfc}.
     * <p>
     * This method assumes that the first three column vectors of the left 3x3 submatrix are not normalized and thus
     * allows to ignore any additional scaling factor that is applied to the matrix.
     *
     * @param dest the destination {@link Quaternionfc}
     * @return the passed in destination
     *
     * @see Quaternionfc#setFromUnnormalized(IMatrix4x3f)
     */
    Quaternionfc getUnnormalizedRotation(Quaternionfc dest);

    /**
     * Get the current values of <code>this</code> matrix and store the represented rotation into the given {@link
     * Quaternionfc}.
     * <p>
     * This method assumes that the first three column vectors of the left 3x3 submatrix are normalized.
     *
     * @param dest the destination {@link Quaternionfc}
     * @return the passed in destination
     *
     * @see Quaternionfc#setFromNormalized(IMatrix4x3f)
     */
    Quaternionfc getNormalizedRotation(Quaternionfc dest);

    /**
     * Get the current values of <code>this</code> matrix and store the represented rotation into the given {@link
     * Quaterniondc}.
     * <p>
     * This method assumes that the first three column vectors of the left 3x3 submatrix are not normalized and thus
     * allows to ignore any additional scaling factor that is applied to the matrix.
     *
     * @param dest the destination {@link Quaterniondc}
     * @return the passed in destination
     *
     * @see Quaterniondc#setFromUnnormalized(IMatrix4x3f)
     */
    Quaterniondc getUnnormalizedRotation(Quaterniondc dest);

    /**
     * Get the current values of <code>this</code> matrix and store the represented rotation into the given {@link
     * Quaterniondc}.
     * <p>
     * This method assumes that the first three column vectors of the left 3x3 submatrix are normalized.
     *
     * @param dest the destination {@link Quaterniondc}
     * @return the passed in destination
     *
     * @see Quaterniondc#setFromNormalized(IMatrix4x3f)
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
     * Store this matrix as an equivalent 4x4 matrix into the supplied float array in column-major order at the given
     * offset.
     *
     * @param arr    the array to write the matrix values into
     * @param offset the offset into the array
     * @return the passed in array
     */
    float[] get4x4(float[] arr, int offset);

    /**
     * Store this matrix as an equivalent 4x4 matrix into the supplied float array in column-major order.
     * <p>
     * In order to specify an explicit offset into the array, use the method {@link #get4x4(float[], int)}.
     *
     * @param arr the array to write the matrix values into
     * @return the passed in array
     *
     * @see #get4x4(float[], int)
     */
    float[] get4x4(float[] arr);

    //#ifdef __HAS_NIO__

    /**
     * Store a 4x4 matrix in column-major order into the supplied {@link FloatBuffer} at the current buffer {@link
     * FloatBuffer#position() position}, where the upper 4x3 submatrix is <code>this</code> and the last row is <tt>(0,
     * 0, 0, 1)</tt>.
     * <p>
     * This method will not increment the position of the given FloatBuffer.
     * <p>
     * In order to specify the offset into the FloatBuffer at which the matrix is stored, use {@link #get4x4(int,
     * FloatBuffer)}, taking the absolute position as parameter.
     *
     * @param buffer will receive the values of this matrix in column-major order at its current position
     * @return the passed in buffer
     *
     * @see #get4x4(int, FloatBuffer)
     */
    FloatBuffer get4x4(FloatBuffer buffer);

    /**
     * Store a 4x4 matrix in column-major order into the supplied {@link FloatBuffer} starting at the specified absolute
     * buffer position/index, where the upper 4x3 submatrix is <code>this</code> and the last row is <tt>(0, 0, 0,
     * 1)</tt>.
     * <p>
     * This method will not increment the position of the given FloatBuffer.
     *
     * @param index  the absolute position into the FloatBuffer
     * @param buffer will receive the values of this matrix in column-major order
     * @return the passed in buffer
     */
    FloatBuffer get4x4(int index, FloatBuffer buffer);

    /**
     * Store a 4x4 matrix in column-major order into the supplied {@link ByteBuffer} at the current buffer {@link
     * ByteBuffer#position() position}, where the upper 4x3 submatrix is <code>this</code> and the last row is <tt>(0,
     * 0, 0, 1)</tt>.
     * <p>
     * This method will not increment the position of the given ByteBuffer.
     * <p>
     * In order to specify the offset into the ByteBuffer at which the matrix is stored, use {@link #get4x4(int,
     * ByteBuffer)}, taking the absolute position as parameter.
     *
     * @param buffer will receive the values of this matrix in column-major order at its current position
     * @return the passed in buffer
     *
     * @see #get4x4(int, ByteBuffer)
     */
    ByteBuffer get4x4(ByteBuffer buffer);

    /**
     * Store a 4x4 matrix in column-major order into the supplied {@link ByteBuffer} starting at the specified absolute
     * buffer position/index, where the upper 4x3 submatrix is <code>this</code> and the last row is <tt>(0, 0, 0,
     * 1)</tt>.
     * <p>
     * This method will not increment the position of the given ByteBuffer.
     *
     * @param index  the absolute position into the ByteBuffer
     * @param buffer will receive the values of this matrix in column-major order
     * @return the passed in buffer
     */
    ByteBuffer get4x4(int index, ByteBuffer buffer);

    /**
     * Store this matrix in row-major order into the supplied {@link FloatBuffer} at the current buffer {@link
     * FloatBuffer#position() position}.
     * <p>
     * This method will not increment the position of the given FloatBuffer.
     * <p>
     * In order to specify the offset into the FloatBuffer at which the matrix is stored, use {@link #getTransposed(int,
     * FloatBuffer)}, taking the absolute position as parameter.
     *
     * @param buffer will receive the values of this matrix in row-major order at its current position
     * @return the passed in buffer
     *
     * @see #getTransposed(int, FloatBuffer)
     */
    FloatBuffer getTransposed(FloatBuffer buffer);

    /**
     * Store this matrix in row-major order into the supplied {@link FloatBuffer} starting at the specified absolute
     * buffer position/index.
     * <p>
     * This method will not increment the position of the given FloatBuffer.
     *
     * @param index  the absolute position into the FloatBuffer
     * @param buffer will receive the values of this matrix in row-major order
     * @return the passed in buffer
     */
    FloatBuffer getTransposed(int index, FloatBuffer buffer);

    /**
     * Store this matrix in row-major order into the supplied {@link ByteBuffer} at the current buffer {@link
     * ByteBuffer#position() position}.
     * <p>
     * This method will not increment the position of the given ByteBuffer.
     * <p>
     * In order to specify the offset into the ByteBuffer at which the matrix is stored, use {@link #getTransposed(int,
     * ByteBuffer)}, taking the absolute position as parameter.
     *
     * @param buffer will receive the values of this matrix in row-major order at its current position
     * @return the passed in buffer
     *
     * @see #getTransposed(int, ByteBuffer)
     */
    ByteBuffer getTransposed(ByteBuffer buffer);

    /**
     * Store this matrix in row-major order into the supplied {@link ByteBuffer} starting at the specified absolute
     * buffer position/index.
     * <p>
     * This method will not increment the position of the given ByteBuffer.
     *
     * @param index  the absolute position into the ByteBuffer
     * @param buffer will receive the values of this matrix in row-major order
     * @return the passed in buffer
     */
    ByteBuffer getTransposed(int index, ByteBuffer buffer);
    //#endif

    /**
     * Store this matrix into the supplied float array in row-major order at the given offset.
     *
     * @param arr    the array to write the matrix values into
     * @param offset the offset into the array
     * @return the passed in array
     */
    float[] getTransposed(float[] arr, int offset);

    /**
     * Store this matrix into the supplied float array in row-major order.
     * <p>
     * In order to specify an explicit offset into the array, use the method {@link #getTransposed(float[], int)}.
     *
     * @param arr the array to write the matrix values into
     * @return the passed in array
     *
     * @see #getTransposed(float[], int)
     */
    float[] getTransposed(float[] arr);

    /**
     * Transform/multiply the given vector by this matrix and store the result in that vector.
     *
     * @param v the vector to transform and to hold the final result
     * @return v
     *
     * @see Vector4fc#mul(IMatrix4x3f)
     */
    Vector4fc transform(Vector4fc v);

    /**
     * Transform/multiply the given vector by this matrix and store the result in <code>dest</code>.
     *
     * @param v    the vector to transform
     * @param dest will contain the result
     * @return dest
     *
     * @see Vector4fc#mul(IMatrix4x3f, Vector4fc)
     */
    Vector4fc transform(IVector4f v, Vector4fc dest);

    /**
     * Transform/multiply the given 3D-vector, as if it was a 4D-vector with w=1, by this matrix and store the result in
     * that vector.
     * <p>
     * The given 3D-vector is treated as a 4D-vector with its w-component being 1.0, so it will represent a
     * position/location in 3D-space rather than a direction.
     * <p>
     * In order to store the result in another vector, use {@link #transformPosition(IVector3f, Vector3fc)}.
     *
     * @param v the vector to transform and to hold the final result
     * @return v
     *
     * @see #transformPosition(IVector3f, Vector3fc)
     * @see #transform(Vector4fc)
     */
    Vector3fc transformPosition(Vector3fc v);

    /**
     * Transform/multiply the given 3D-vector, as if it was a 4D-vector with w=1, by this matrix and store the result in
     * <code>dest</code>.
     * <p>
     * The given 3D-vector is treated as a 4D-vector with its w-component being 1.0, so it will represent a
     * position/location in 3D-space rather than a direction.
     * <p>
     * In order to store the result in the same vector, use {@link #transformPosition(Vector3fc)}.
     *
     * @param v    the vector to transform
     * @param dest will hold the result
     * @return dest
     *
     * @see #transformPosition(Vector3fc)
     * @see #transform(IVector4f, Vector4fc)
     */
    Vector3fc transformPosition(IVector3f v, Vector3fc dest);

    /**
     * Transform/multiply the given 3D-vector, as if it was a 4D-vector with w=0, by this matrix and store the result in
     * that vector.
     * <p>
     * The given 3D-vector is treated as a 4D-vector with its w-component being <tt>0.0</tt>, so it will represent a
     * direction in 3D-space rather than a position. This method will therefore not take the translation part of the
     * matrix into account.
     * <p>
     * In order to store the result in another vector, use {@link #transformDirection(IVector3f, Vector3fc)}.
     *
     * @param v the vector to transform and to hold the final result
     * @return v
     *
     * @see #transformDirection(IVector3f, Vector3fc)
     */
    Vector3fc transformDirection(Vector3fc v);

    /**
     * Transform/multiply the given 3D-vector, as if it was a 4D-vector with w=0, by this matrix and store the result in
     * <code>dest</code>.
     * <p>
     * The given 3D-vector is treated as a 4D-vector with its w-component being <tt>0.0</tt>, so it will represent a
     * direction in 3D-space rather than a position. This method will therefore not take the translation part of the
     * matrix into account.
     * <p>
     * In order to store the result in the same vector, use {@link #transformDirection(Vector3fc)}.
     *
     * @param v    the vector to transform and to hold the final result
     * @param dest will hold the result
     * @return dest
     *
     * @see #transformDirection(Vector3fc)
     */
    Vector3fc transformDirection(IVector3f v, Vector3fc dest);

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
    Matrix4x3fc scale(IVector3f xyz, Matrix4x3fc dest);

    /**
     * Apply scaling to this matrix by uniformly scaling all base axes by the given <code>xyz</code> factor and store
     * the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>S</code> the scaling matrix, then the new matrix will be
     * <code>M * S</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>M * S *
     * v</code>, the scaling will be applied first!
     * <p>
     * Individual scaling of all three axes can be applied using {@link #scale(float, float, float, Matrix4x3fc)}.
     *
     * @param xyz  the factor for all components
     * @param dest will hold the result
     * @return dest
     *
     * @see #scale(float, float, float, Matrix4x3fc)
     */
    Matrix4x3fc scale(float xyz, Matrix4x3fc dest);

    /**
     * Apply scaling to <code>this</code> matrix by scaling the base axes by the given x, y and z factors and store the
     * result in <code>dest</code>.
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
    Matrix4x3fc scale(float x, float y, float z, Matrix4x3fc dest);

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
    Matrix4x3fc scaleLocal(float x, float y, float z, Matrix4x3fc dest);

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
     * v</code>, the rotation will be applied first!
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Basic_rotations">http://en.wikipedia.org</a>
     *
     * @param ang  the angle in radians
     * @param dest will hold the result
     * @return dest
     */
    Matrix4x3fc rotateX(float ang, Matrix4x3fc dest);

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
     * v</code>, the rotation will be applied first!
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Basic_rotations">http://en.wikipedia.org</a>
     *
     * @param ang  the angle in radians
     * @param dest will hold the result
     * @return dest
     */
    Matrix4x3fc rotateY(float ang, Matrix4x3fc dest);

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
     * v</code>, the rotation will be applied first!
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Basic_rotations">http://en.wikipedia.org</a>
     *
     * @param ang  the angle in radians
     * @param dest will hold the result
     * @return dest
     */
    Matrix4x3fc rotateZ(float ang, Matrix4x3fc dest);

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
    Matrix4x3fc rotateXYZ(float angleX, float angleY, float angleZ, Matrix4x3fc dest);

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
    Matrix4x3fc rotateZYX(float angleZ, float angleY, float angleX, Matrix4x3fc dest);

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
    Matrix4x3fc rotateYXZ(float angleY, float angleX, float angleZ, Matrix4x3fc dest);

    /**
     * Apply rotation to this matrix by rotating the given amount of radians about the specified <tt>(x, y, z)</tt> axis
     * and store the result in <code>dest</code>.
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
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Rotation_matrix_from_axis_and_angle">http://en.wikipedia.org</a>
     *
     * @param ang  the angle in radians
     * @param x    the x component of the axis
     * @param y    the y component of the axis
     * @param z    the z component of the axis
     * @param dest will hold the result
     * @return dest
     */
    Matrix4x3fc rotate(float ang, float x, float y, float z, Matrix4x3fc dest);

    /**
     * Apply rotation to this matrix, which is assumed to only contain a translation, by rotating the given amount of
     * radians about the specified <tt>(x, y, z)</tt> axis and store the result in <code>dest</code>.
     * <p>
     * This method assumes <code>this</code> to only contain a translation.
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
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Rotation_matrix_from_axis_and_angle">http://en.wikipedia.org</a>
     *
     * @param ang  the angle in radians
     * @param x    the x component of the axis
     * @param y    the y component of the axis
     * @param z    the z component of the axis
     * @param dest will hold the result
     * @return dest
     */
    Matrix4x3fc rotateTranslation(float ang, float x, float y, float z, Matrix4x3fc dest);

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
    Matrix4x3fc rotateLocal(float ang, float x, float y, float z, Matrix4x3fc dest);

    /**
     * Apply a translation to this matrix by translating by the given number of units in x, y and z and store the result
     * in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>T</code> the translation matrix, then the new matrix will
     * be <code>M * T</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>M * T *
     * v</code>, the translation will be applied first!
     *
     * @param offset the number of units in x, y and z by which to translate
     * @param dest   will hold the result
     * @return dest
     */
    Matrix4x3fc translate(IVector3f offset, Matrix4x3fc dest);

    /**
     * Apply a translation to this matrix by translating by the given number of units in x, y and z and store the result
     * in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>T</code> the translation matrix, then the new matrix will
     * be <code>M * T</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>M * T *
     * v</code>, the translation will be applied first!
     *
     * @param x    the offset to translate in x
     * @param y    the offset to translate in y
     * @param z    the offset to translate in z
     * @param dest will hold the result
     * @return dest
     */
    Matrix4x3fc translate(float x, float y, float z, Matrix4x3fc dest);

    /**
     * Pre-multiply a translation to this matrix by translating by the given number of units in x, y and z and store the
     * result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>T</code> the translation matrix, then the new matrix will
     * be <code>T * M</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>T * M *
     * v</code>, the translation will be applied last!
     *
     * @param offset the number of units in x, y and z by which to translate
     * @param dest   will hold the result
     * @return dest
     */
    Matrix4x3fc translateLocal(IVector3f offset, Matrix4x3fc dest);

    /**
     * Pre-multiply a translation to this matrix by translating by the given number of units in x, y and z and store the
     * result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>T</code> the translation matrix, then the new matrix will
     * be <code>T * M</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>T * M *
     * v</code>, the translation will be applied last!
     *
     * @param x    the offset to translate in x
     * @param y    the offset to translate in y
     * @param z    the offset to translate in z
     * @param dest will hold the result
     * @return dest
     */
    Matrix4x3fc translateLocal(float x, float y, float z, Matrix4x3fc dest);

    /**
     * Apply an orthographic projection transformation for a right-handed coordinate system using the given NDC z range
     * to this matrix and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>O</code> the orthographic projection matrix, then the new
     * matrix will be <code>M * O</code>. So when transforming a vector <code>v</code> with the new matrix by using
     * <code>M * O * v</code>, the orthographic projection transformation will be applied first!
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
     * @param dest       will hold the result
     * @return dest
     */
    Matrix4x3fc ortho(float left, float right, float bottom, float top, float zNear, float zFar, boolean zZeroToOne, Matrix4x3fc dest);

    /**
     * Apply an orthographic projection transformation for a right-handed coordinate system using OpenGL's NDC z range
     * of <tt>[-1..+1]</tt> to this matrix and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>O</code> the orthographic projection matrix, then the new
     * matrix will be <code>M * O</code>. So when transforming a vector <code>v</code> with the new matrix by using
     * <code>M * O * v</code>, the orthographic projection transformation will be applied first!
     * <p>
     * Reference: <a href="http://www.songho.ca/opengl/gl_projectionmatrix.html#ortho">http://www.songho.ca</a>
     *
     * @param left   the distance from the center to the left frustum edge
     * @param right  the distance from the center to the right frustum edge
     * @param bottom the distance from the center to the bottom frustum edge
     * @param top    the distance from the center to the top frustum edge
     * @param zNear  near clipping plane distance
     * @param zFar   far clipping plane distance
     * @param dest   will hold the result
     * @return dest
     */
    Matrix4x3fc ortho(float left, float right, float bottom, float top, float zNear, float zFar, Matrix4x3fc dest);

    /**
     * Apply an orthographic projection transformation for a left-handed coordiante system using the given NDC z range
     * to this matrix and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>O</code> the orthographic projection matrix, then the new
     * matrix will be <code>M * O</code>. So when transforming a vector <code>v</code> with the new matrix by using
     * <code>M * O * v</code>, the orthographic projection transformation will be applied first!
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
     * @param dest       will hold the result
     * @return dest
     */
    Matrix4x3fc orthoLH(float left, float right, float bottom, float top, float zNear, float zFar, boolean zZeroToOne, Matrix4x3fc dest);

    /**
     * Apply an orthographic projection transformation for a left-handed coordiante system using OpenGL's NDC z range of
     * <tt>[-1..+1]</tt> to this matrix and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>O</code> the orthographic projection matrix, then the new
     * matrix will be <code>M * O</code>. So when transforming a vector <code>v</code> with the new matrix by using
     * <code>M * O * v</code>, the orthographic projection transformation will be applied first!
     * <p>
     * Reference: <a href="http://www.songho.ca/opengl/gl_projectionmatrix.html#ortho">http://www.songho.ca</a>
     *
     * @param left   the distance from the center to the left frustum edge
     * @param right  the distance from the center to the right frustum edge
     * @param bottom the distance from the center to the bottom frustum edge
     * @param top    the distance from the center to the top frustum edge
     * @param zNear  near clipping plane distance
     * @param zFar   far clipping plane distance
     * @param dest   will hold the result
     * @return dest
     */
    Matrix4x3fc orthoLH(float left, float right, float bottom, float top, float zNear, float zFar, Matrix4x3fc dest);

    /**
     * Apply a symmetric orthographic projection transformation for a right-handed coordinate system using the given NDC
     * z range to this matrix and store the result in <code>dest</code>.
     * <p>
     * This method is equivalent to calling {@link #ortho(float, float, float, float, float, float, boolean,
     * Matrix4x3fc) ortho()} with <code>left=-width/2</code>, <code>right=+width/2</code>, <code>bottom=-height/2</code>
     * and <code>top=+height/2</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>O</code> the orthographic projection matrix, then the new
     * matrix will be <code>M * O</code>. So when transforming a vector <code>v</code> with the new matrix by using
     * <code>M * O * v</code>, the orthographic projection transformation will be applied first!
     * <p>
     * Reference: <a href="http://www.songho.ca/opengl/gl_projectionmatrix.html#ortho">http://www.songho.ca</a>
     *
     * @param width      the distance between the right and left frustum edges
     * @param height     the distance between the top and bottom frustum edges
     * @param zNear      near clipping plane distance
     * @param zFar       far clipping plane distance
     * @param dest       will hold the result
     * @param zZeroToOne whether to use Vulkan's and Direct3D's NDC z range of <tt>[0..+1]</tt> when <code>true</code>
     *                   or whether to use OpenGL's NDC z range of <tt>[-1..+1]</tt> when <code>false</code>
     * @return dest
     */
    Matrix4x3fc orthoSymmetric(float width, float height, float zNear, float zFar, boolean zZeroToOne, Matrix4x3fc dest);

    /**
     * Apply a symmetric orthographic projection transformation for a right-handed coordinate system using OpenGL's NDC
     * z range of <tt>[-1..+1]</tt> to this matrix and store the result in <code>dest</code>.
     * <p>
     * This method is equivalent to calling {@link #ortho(float, float, float, float, float, float, Matrix4x3fc)
     * ortho()} with <code>left=-width/2</code>, <code>right=+width/2</code>, <code>bottom=-height/2</code> and
     * <code>top=+height/2</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>O</code> the orthographic projection matrix, then the new
     * matrix will be <code>M * O</code>. So when transforming a vector <code>v</code> with the new matrix by using
     * <code>M * O * v</code>, the orthographic projection transformation will be applied first!
     * <p>
     * Reference: <a href="http://www.songho.ca/opengl/gl_projectionmatrix.html#ortho">http://www.songho.ca</a>
     *
     * @param width  the distance between the right and left frustum edges
     * @param height the distance between the top and bottom frustum edges
     * @param zNear  near clipping plane distance
     * @param zFar   far clipping plane distance
     * @param dest   will hold the result
     * @return dest
     */
    Matrix4x3fc orthoSymmetric(float width, float height, float zNear, float zFar, Matrix4x3fc dest);

    /**
     * Apply a symmetric orthographic projection transformation for a left-handed coordinate system using the given NDC
     * z range to this matrix and store the result in <code>dest</code>.
     * <p>
     * This method is equivalent to calling {@link #orthoLH(float, float, float, float, float, float, boolean,
     * Matrix4x3fc) orthoLH()} with <code>left=-width/2</code>, <code>right=+width/2</code>,
     * <code>bottom=-height/2</code> and <code>top=+height/2</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>O</code> the orthographic projection matrix, then the new
     * matrix will be <code>M * O</code>. So when transforming a vector <code>v</code> with the new matrix by using
     * <code>M * O * v</code>, the orthographic projection transformation will be applied first!
     * <p>
     * Reference: <a href="http://www.songho.ca/opengl/gl_projectionmatrix.html#ortho">http://www.songho.ca</a>
     *
     * @param width      the distance between the right and left frustum edges
     * @param height     the distance between the top and bottom frustum edges
     * @param zNear      near clipping plane distance
     * @param zFar       far clipping plane distance
     * @param dest       will hold the result
     * @param zZeroToOne whether to use Vulkan's and Direct3D's NDC z range of <tt>[0..+1]</tt> when <code>true</code>
     *                   or whether to use OpenGL's NDC z range of <tt>[-1..+1]</tt> when <code>false</code>
     * @return dest
     */
    Matrix4x3fc orthoSymmetricLH(float width, float height, float zNear, float zFar, boolean zZeroToOne, Matrix4x3fc dest);

    /**
     * Apply a symmetric orthographic projection transformation for a left-handed coordinate system using OpenGL's NDC z
     * range of <tt>[-1..+1]</tt> to this matrix and store the result in <code>dest</code>.
     * <p>
     * This method is equivalent to calling {@link #orthoLH(float, float, float, float, float, float, Matrix4x3fc)
     * orthoLH()} with <code>left=-width/2</code>, <code>right=+width/2</code>, <code>bottom=-height/2</code> and
     * <code>top=+height/2</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>O</code> the orthographic projection matrix, then the new
     * matrix will be <code>M * O</code>. So when transforming a vector <code>v</code> with the new matrix by using
     * <code>M * O * v</code>, the orthographic projection transformation will be applied first!
     * <p>
     * Reference: <a href="http://www.songho.ca/opengl/gl_projectionmatrix.html#ortho">http://www.songho.ca</a>
     *
     * @param width  the distance between the right and left frustum edges
     * @param height the distance between the top and bottom frustum edges
     * @param zNear  near clipping plane distance
     * @param zFar   far clipping plane distance
     * @param dest   will hold the result
     * @return dest
     */
    Matrix4x3fc orthoSymmetricLH(float width, float height, float zNear, float zFar, Matrix4x3fc dest);

    /**
     * Apply an orthographic projection transformation for a right-handed coordinate system to this matrix and store the
     * result in <code>dest</code>.
     * <p>
     * This method is equivalent to calling {@link #ortho(float, float, float, float, float, float, Matrix4x3fc)
     * ortho()} with <code>zNear=-1</code> and <code>zFar=+1</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>O</code> the orthographic projection matrix, then the new
     * matrix will be <code>M * O</code>. So when transforming a vector <code>v</code> with the new matrix by using
     * <code>M * O * v</code>, the orthographic projection transformation will be applied first!
     * <p>
     * Reference: <a href="http://www.songho.ca/opengl/gl_projectionmatrix.html#ortho">http://www.songho.ca</a>
     *
     * @param left   the distance from the center to the left frustum edge
     * @param right  the distance from the center to the right frustum edge
     * @param bottom the distance from the center to the bottom frustum edge
     * @param top    the distance from the center to the top frustum edge
     * @param dest   will hold the result
     * @return dest
     *
     * @see #ortho(float, float, float, float, float, float, Matrix4x3fc)
     */
    Matrix4x3fc ortho2D(float left, float right, float bottom, float top, Matrix4x3fc dest);

    /**
     * Apply an orthographic projection transformation for a left-handed coordinate system to this matrix and store the
     * result in <code>dest</code>.
     * <p>
     * This method is equivalent to calling {@link #orthoLH(float, float, float, float, float, float, Matrix4x3fc)
     * orthoLH()} with <code>zNear=-1</code> and <code>zFar=+1</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>O</code> the orthographic projection matrix, then the new
     * matrix will be <code>M * O</code>. So when transforming a vector <code>v</code> with the new matrix by using
     * <code>M * O * v</code>, the orthographic projection transformation will be applied first!
     * <p>
     * Reference: <a href="http://www.songho.ca/opengl/gl_projectionmatrix.html#ortho">http://www.songho.ca</a>
     *
     * @param left   the distance from the center to the left frustum edge
     * @param right  the distance from the center to the right frustum edge
     * @param bottom the distance from the center to the bottom frustum edge
     * @param top    the distance from the center to the top frustum edge
     * @param dest   will hold the result
     * @return dest
     *
     * @see #orthoLH(float, float, float, float, float, float, Matrix4x3fc)
     */
    Matrix4x3fc ortho2DLH(float left, float right, float bottom, float top, Matrix4x3fc dest);

    /**
     * Apply a rotation transformation to this matrix to make <code>-z</code> point along <code>dir</code> and store the
     * result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>L</code> the lookalong rotation matrix, then the new
     * matrix will be <code>M * L</code>. So when transforming a vector <code>v</code> with the new matrix by using
     * <code>M * L * v</code>, the lookalong rotation transformation will be applied first!
     * <p>
     * This is equivalent to calling {@link #lookAt(IVector3f, IVector3f, IVector3f, Matrix4x3fc) lookAt} with <code>eye
     * = (0, 0, 0)</code> and <code>center = dir</code>.
     *
     * @param dir  the direction in space to look along
     * @param up   the direction of 'up'
     * @param dest will hold the result
     * @return dest
     *
     * @see #lookAlong(float, float, float, float, float, float, Matrix4x3fc)
     * @see #lookAt(IVector3f, IVector3f, IVector3f, Matrix4x3fc)
     */
    Matrix4x3fc lookAlong(IVector3f dir, IVector3f up, Matrix4x3fc dest);

    /**
     * Apply a rotation transformation to this matrix to make <code>-z</code> point along <code>dir</code> and store the
     * result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>L</code> the lookalong rotation matrix, then the new
     * matrix will be <code>M * L</code>. So when transforming a vector <code>v</code> with the new matrix by using
     * <code>M * L * v</code>, the lookalong rotation transformation will be applied first!
     * <p>
     * This is equivalent to calling {@link #lookAt(float, float, float, float, float, float, float, float, float,
     * Matrix4x3fc) lookAt()} with <code>eye = (0, 0, 0)</code> and <code>center = dir</code>.
     *
     * @param dirX the x-coordinate of the direction to look along
     * @param dirY the y-coordinate of the direction to look along
     * @param dirZ the z-coordinate of the direction to look along
     * @param upX  the x-coordinate of the up vector
     * @param upY  the y-coordinate of the up vector
     * @param upZ  the z-coordinate of the up vector
     * @param dest will hold the result
     * @return dest
     *
     * @see #lookAt(float, float, float, float, float, float, float, float, float, Matrix4x3fc)
     */
    Matrix4x3fc lookAlong(float dirX, float dirY, float dirZ, float upX, float upY, float upZ, Matrix4x3fc dest);

    /**
     * Apply a "lookat" transformation to this matrix for a right-handed coordinate system, that aligns <code>-z</code>
     * with <code>center - eye</code> and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>L</code> the lookat matrix, then the new matrix will be
     * <code>M * L</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>M * L *
     * v</code>, the lookat transformation will be applied first!
     *
     * @param eye    the position of the camera
     * @param center the point in space to look at
     * @param up     the direction of 'up'
     * @param dest   will hold the result
     * @return dest
     *
     * @see #lookAt(float, float, float, float, float, float, float, float, float, Matrix4x3fc)
     */
    Matrix4x3fc lookAt(IVector3f eye, IVector3f center, IVector3f up, Matrix4x3fc dest);

    /**
     * Apply a "lookat" transformation to this matrix for a right-handed coordinate system, that aligns <code>-z</code>
     * with <code>center - eye</code> and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>L</code> the lookat matrix, then the new matrix will be
     * <code>M * L</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>M * L *
     * v</code>, the lookat transformation will be applied first!
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
     * @param dest    will hold the result
     * @return dest
     *
     * @see #lookAt(IVector3f, IVector3f, IVector3f, Matrix4x3fc)
     */
    Matrix4x3fc lookAt(float eyeX, float eyeY, float eyeZ, float centerX, float centerY, float centerZ, float upX, float upY, float upZ, Matrix4x3fc dest);

    /**
     * Apply a "lookat" transformation to this matrix for a left-handed coordinate system, that aligns <code>+z</code>
     * with <code>center - eye</code> and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>L</code> the lookat matrix, then the new matrix will be
     * <code>M * L</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>M * L *
     * v</code>, the lookat transformation will be applied first!
     *
     * @param eye    the position of the camera
     * @param center the point in space to look at
     * @param up     the direction of 'up'
     * @param dest   will hold the result
     * @return dest
     *
     * @see #lookAtLH(float, float, float, float, float, float, float, float, float, Matrix4x3fc)
     */
    Matrix4x3fc lookAtLH(IVector3f eye, IVector3f center, IVector3f up, Matrix4x3fc dest);

    /**
     * Apply a "lookat" transformation to this matrix for a left-handed coordinate system, that aligns <code>+z</code>
     * with <code>center - eye</code> and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>L</code> the lookat matrix, then the new matrix will be
     * <code>M * L</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>M * L *
     * v</code>, the lookat transformation will be applied first!
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
     * @param dest    will hold the result
     * @return dest
     *
     * @see #lookAtLH(IVector3f, IVector3f, IVector3f, Matrix4x3fc)
     */
    Matrix4x3fc lookAtLH(float eyeX, float eyeY, float eyeZ, float centerX, float centerY, float centerZ, float upX, float upY, float upZ, Matrix4x3fc dest);

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
    Matrix4x3fc rotate(IQuaternionf quat, Matrix4x3fc dest);

    /**
     * Apply the rotation - and possibly scaling - transformation of the given {@link IQuaternionf} to this matrix,
     * which is assumed to only contain a translation, and store the result in <code>dest</code>.
     * <p>
     * This method assumes <code>this</code> to only contain a translation.
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
    Matrix4x3fc rotateTranslation(IQuaternionf quat, Matrix4x3fc dest);

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
    Matrix4x3fc rotateLocal(IQuaternionf quat, Matrix4x3fc dest);

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
     * @see #rotate(float, float, float, float, Matrix4x3fc)
     */
    Matrix4x3fc rotate(AxisAngle4fc axisAngle, Matrix4x3fc dest);

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
     * If <code>M</code> is <code>this</code> matrix and <code>A</code> the rotation matrix obtained from the given
     * axis-angle, then the new matrix will be <code>M * A</code>. So when transforming a vector <code>v</code> with the
     * new matrix by using <code>M * A * v</code>, the axis-angle rotation will be applied first!
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Axis_and_angle">http://en.wikipedia.org</a>
     *
     * @param angle the angle in radians
     * @param axis  the rotation axis (needs to be {@link Vector3fc#normalize() normalized})
     * @param dest  will hold the result
     * @return dest
     *
     * @see #rotate(float, float, float, float, Matrix4x3fc)
     */
    Matrix4x3fc rotate(float angle, IVector3f axis, Matrix4x3fc dest);

    /**
     * Apply a mirror/reflection transformation to this matrix that reflects about the given plane specified via the
     * equation <tt>x*a + y*b + z*c + d = 0</tt> and store the result in <code>dest</code>.
     * <p>
     * The vector <tt>(a, b, c)</tt> must be a unit vector.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the reflection matrix, then the new matrix will
     * be <code>M * R</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>M * R *
     * v</code>, the reflection will be applied first!
     * <p>
     * Reference: <a href="https://msdn.microsoft.com/en-us/library/windows/desktop/bb281733(v=vs.85).aspx">msdn.microsoft.com</a>
     *
     * @param a    the x factor in the plane equation
     * @param b    the y factor in the plane equation
     * @param c    the z factor in the plane equation
     * @param d    the constant in the plane equation
     * @param dest will hold the result
     * @return dest
     */
    Matrix4x3fc reflect(float a, float b, float c, float d, Matrix4x3fc dest);

    /**
     * Apply a mirror/reflection transformation to this matrix that reflects about the given plane specified via the
     * plane normal and a point on the plane, and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the reflection matrix, then the new matrix will
     * be <code>M * R</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>M * R *
     * v</code>, the reflection will be applied first!
     *
     * @param nx   the x-coordinate of the plane normal
     * @param ny   the y-coordinate of the plane normal
     * @param nz   the z-coordinate of the plane normal
     * @param px   the x-coordinate of a point on the plane
     * @param py   the y-coordinate of a point on the plane
     * @param pz   the z-coordinate of a point on the plane
     * @param dest will hold the result
     * @return dest
     */
    Matrix4x3fc reflect(float nx, float ny, float nz, float px, float py, float pz, Matrix4x3fc dest);

    /**
     * Apply a mirror/reflection transformation to this matrix that reflects about a plane specified via the plane
     * orientation and a point on the plane, and store the result in <code>dest</code>.
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
     * @param orientation the plane orientation relative to an implied normal vector of <tt>(0, 0, 1)</tt>
     * @param point       a point on the plane
     * @param dest        will hold the result
     * @return dest
     */
    Matrix4x3fc reflect(IQuaternionf orientation, IVector3f point, Matrix4x3fc dest);

    /**
     * Apply a mirror/reflection transformation to this matrix that reflects about the given plane specified via the
     * plane normal and a point on the plane, and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the reflection matrix, then the new matrix will
     * be <code>M * R</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>M * R *
     * v</code>, the reflection will be applied first!
     *
     * @param normal the plane normal
     * @param point  a point on the plane
     * @param dest   will hold the result
     * @return dest
     */
    Matrix4x3fc reflect(IVector3f normal, IVector3f point, Matrix4x3fc dest);

    /**
     * Get the row at the given <code>row</code> index, starting with <code>0</code>.
     *
     * @param row  the row index in <tt>[0..2]</tt>
     * @param dest will hold the row components
     * @return the passed in destination
     */
    Vector4fc getRow(int row, Vector4fc dest);

    /**
     * Get the column at the given <code>column</code> index, starting with <code>0</code>.
     *
     * @param column the column index in <tt>[0..2]</tt>
     * @param dest   will hold the column components
     * @return the passed in destination
     */
    Vector3fc getColumn(int column, Vector3fc dest);

    /**
     * Compute a normal matrix from the left 3x3 submatrix of <code>this</code> and store it into the left 3x3 submatrix
     * of <code>dest</code>. All other values of <code>dest</code> will be set to identity.
     * <p>
     * The normal matrix of <tt>m</tt> is the transpose of the inverse of <tt>m</tt>.
     *
     * @param dest will hold the result
     * @return dest
     */
    Matrix4x3fc normal(Matrix4x3fc dest);

    /**
     * Compute a normal matrix from the left 3x3 submatrix of <code>this</code> and store it into <code>dest</code>.
     * <p>
     * The normal matrix of <tt>m</tt> is the transpose of the inverse of <tt>m</tt>.
     *
     * @param dest will hold the result
     * @return dest
     */
    Matrix3fc normal(Matrix3fc dest);

    /**
     * Normalize the left 3x3 submatrix of this matrix and store the result in <code>dest</code>.
     * <p>
     * The resulting matrix will map unit vectors to unit vectors, though a pair of orthogonal input unit vectors need
     * not be mapped to a pair of orthogonal output vectors if the original matrix was not orthogonal itself (i.e. had
     * <i>skewing</i>).
     *
     * @param dest will hold the result
     * @return dest
     */
    Matrix4x3fc normalize3x3(Matrix4x3fc dest);

    /**
     * Normalize the left 3x3 submatrix of this matrix and store the result in <code>dest</code>.
     * <p>
     * The resulting matrix will map unit vectors to unit vectors, though a pair of orthogonal input unit vectors need
     * not be mapped to a pair of orthogonal output vectors if the original matrix was not orthogonal itself (i.e. had
     * <i>skewing</i>).
     *
     * @param dest will hold the result
     * @return dest
     */
    Matrix3fc normalize3x3(Matrix3fc dest);

    /**
     * Calculate a frustum plane of <code>this</code> matrix, which can be a projection matrix or a combined
     * modelview-projection matrix, and store the result in the given <code>plane</code>.
     * <p>
     * Generally, this method computes the frustum plane in the local frame of any coordinate system that existed before
     * <code>this</code> transformation was applied to it in order to yield homogeneous clipping space.
     * <p>
     * The plane normal, which is <tt>(a, b, c)</tt>, is directed "inwards" of the frustum. Any plane/point test using
     * <tt>a*x + b*y + c*z + d</tt> therefore will yield a result greater than zero if the point is within the frustum
     * (i.e. at the <i>positive</i> side of the frustum plane).
     * <p>
     * Reference: <a href="http://gamedevs.org/uploads/fast-extraction-viewing-frustum-planes-from-world-view-projection-matrix.pdf">
     * Fast Extraction of Viewing Frustum Planes from the World-View-Projection Matrix</a>
     *
     * @param which one of the six possible planes, given as numeric constants {@link #PLANE_NX}, {@link #PLANE_PX},
     *              {@link #PLANE_NY}, {@link #PLANE_PY}, {@link #PLANE_NZ} and {@link #PLANE_PZ}
     * @param plane will hold the computed plane equation. The plane equation will be normalized, meaning that <tt>(a,
     *              b, c)</tt> will be a unit vector
     * @return planeEquation
     */
    Planefc frustumPlane(int which, Planefc plane);

    /**
     * Obtain the direction of <tt>+Z</tt> before the transformation represented by <code>this</code> matrix is
     * applied.
     * <p>
     * This method uses the rotation component of the left 3x3 submatrix to obtain the direction that is transformed to
     * <tt>+Z</tt> by <code>this</code> matrix.
     * <p>
     * This method is equivalent to the following code:
     * <pre>
     * Matrix4x3fc inv = new Matrix4x3fc(this).invert();
     * inv.transformDirection(dir.set(0, 0, 1)).normalize();
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
     * This method uses the rotation component of the left 3x3 submatrix to obtain the direction that is transformed to
     * <tt>+Z</tt> by <code>this</code> matrix.
     * <p>
     * This method is equivalent to the following code:
     * <pre>
     * Matrix4x3fc inv = new Matrix4x3fc(this).transpose();
     * inv.transformDirection(dir.set(0, 0, 1)).normalize();
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
     * This method uses the rotation component of the left 3x3 submatrix to obtain the direction that is transformed to
     * <tt>+X</tt> by <code>this</code> matrix.
     * <p>
     * This method is equivalent to the following code:
     * <pre>
     * Matrix4x3fc inv = new Matrix4x3fc(this).invert();
     * inv.transformDirection(dir.set(1, 0, 0)).normalize();
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
     * This method uses the rotation component of the left 3x3 submatrix to obtain the direction that is transformed to
     * <tt>+X</tt> by <code>this</code> matrix.
     * <p>
     * This method is equivalent to the following code:
     * <pre>
     * Matrix4x3fc inv = new Matrix4x3fc(this).transpose();
     * inv.transformDirection(dir.set(1, 0, 0)).normalize();
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
     * This method uses the rotation component of the left 3x3 submatrix to obtain the direction that is transformed to
     * <tt>+Y</tt> by <code>this</code> matrix.
     * <p>
     * This method is equivalent to the following code:
     * <pre>
     * Matrix4x3fc inv = new Matrix4x3fc(this).invert();
     * inv.transformDirection(dir.set(0, 1, 0)).normalize();
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
     * This method uses the rotation component of the left 3x3 submatrix to obtain the direction that is transformed to
     * <tt>+Y</tt> by <code>this</code> matrix.
     * <p>
     * This method is equivalent to the following code:
     * <pre>
     * Matrix4x3fc inv = new Matrix4x3fc(this).transpose();
     * inv.transformDirection(dir.set(0, 1, 0)).normalize();
     * </pre>
     * <p>
     * Reference: <a href="http://www.euclideanspace.com/maths/algebra/matrix/functions/inverse/threeD/">http://www.euclideanspace.com</a>
     *
     * @param dir will hold the direction of <tt>+Y</tt>
     * @return dir
     */
    Vector3fc normalizedPositiveY(Vector3fc dir);

    /**
     * Obtain the position that gets transformed to the origin by <code>this</code> matrix. This can be used to get the
     * position of the "camera" from a given <i>view</i> transformation matrix.
     * <p>
     * This method is equivalent to the following code:
     * <pre>
     * Matrix4x3fc inv = new Matrix4x3fc(this).invert();
     * inv.transformPosition(origin.set(0, 0, 0));
     * </pre>
     *
     * @param origin will hold the position transformed to the origin
     * @return origin
     */
    Vector3fc origin(Vector3fc origin);

    /**
     * Apply a projection transformation to this matrix that projects onto the plane specified via the general plane
     * equation <tt>x*a + y*b + z*c + d = 0</tt> as if casting a shadow from a given light position/direction
     * <code>light</code> and store the result in <code>dest</code>.
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
     * @param dest  will hold the result
     * @return dest
     */
    Matrix4x3fc shadow(IVector4f light, float a, float b, float c, float d, Matrix4x3fc dest);

    /**
     * Apply a projection transformation to this matrix that projects onto the plane specified via the general plane
     * equation <tt>x*a + y*b + z*c + d = 0</tt> as if casting a shadow from a given light position/direction
     * <tt>(lightX, lightY, lightZ, lightW)</tt> and store the result in <code>dest</code>.
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
     * @param dest   will hold the result
     * @return dest
     */
    Matrix4x3fc shadow(float lightX, float lightY, float lightZ, float lightW, float a, float b, float c, float d, Matrix4x3fc dest);

    /**
     * Apply a projection transformation to this matrix that projects onto the plane with the general plane equation
     * <tt>y = 0</tt> as if casting a shadow from a given light position/direction <code>light</code> and store the
     * result in <code>dest</code>.
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
     * @param dest           will hold the result
     * @return dest
     */
    Matrix4x3fc shadow(IVector4f light, IMatrix4x3f planeTransform, Matrix4x3fc dest);

    /**
     * Apply a projection transformation to this matrix that projects onto the plane with the general plane equation
     * <tt>y = 0</tt> as if casting a shadow from a given light position/direction <tt>(lightX, lightY, lightZ,
     * lightW)</tt> and store the result in <code>dest</code>.
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
     * @param dest           will hold the result
     * @return dest
     */
    Matrix4x3fc shadow(float lightX, float lightY, float lightZ, float lightW, IMatrix4x3f planeTransform, Matrix4x3fc dest);

    /**
     * Apply a picking transformation to this matrix using the given window coordinates <tt>(x, y)</tt> as the pick
     * center and the given <tt>(width, height)</tt> as the size of the picking region in window coordinates, and store
     * the result in <code>dest</code>.
     *
     * @param x        the x coordinate of the picking region center in window coordinates
     * @param y        the y coordinate of the picking region center in window coordinates
     * @param width    the width of the picking region in window coordinates
     * @param height   the height of the picking region in window coordinates
     * @param viewport the viewport described by <tt>[x, y, width, height]</tt>
     * @param dest     the destination matrix, which will hold the result
     * @return dest
     */
    Matrix4x3fc pick(float x, float y, float width, float height, int[] viewport, Matrix4x3fc dest);

    /**
     * Apply an arcball view transformation to this matrix with the given <code>radius</code> and center <tt>(centerX,
     * centerY, centerZ)</tt> position of the arcball and the specified X and Y rotation angles, and store the result in
     * <code>dest</code>.
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
     * @param dest    will hold the result
     * @return dest
     */
    Matrix4x3fc arcball(float radius, float centerX, float centerY, float centerZ, float angleX, float angleY, Matrix4x3fc dest);

    /**
     * Apply an arcball view transformation to this matrix with the given <code>radius</code> and <code>center</code>
     * position of the arcball and the specified X and Y rotation angles, and store the result in <code>dest</code>.
     * <p>
     * This method is equivalent to calling: <tt>translate(0, 0, -radius).rotateX(angleX).rotateY(angleY).translate(-center.x,
     * -center.y, -center.z)</tt>
     *
     * @param radius the arcball radius
     * @param center the center position of the arcball
     * @param angleX the rotation angle around the X axis in radians
     * @param angleY the rotation angle around the Y axis in radians
     * @param dest   will hold the result
     * @return dest
     */
    Matrix4x3fc arcball(float radius, IVector3f center, float angleX, float angleY, Matrix4x3fc dest);

    /**
     * Transform the axis-aligned box given as the minimum corner <tt>(minX, minY, minZ)</tt> and maximum corner
     * <tt>(maxX, maxY, maxZ)</tt> by <code>this</code> matrix and compute the axis-aligned box of the result whose
     * minimum corner is stored in <code>outMin</code> and maximum corner stored in <code>outMax</code>.
     * <p>
     * Reference: <a href="http://dev.theomader.com/transform-bounding-boxes/">http://dev.theomader.com</a>
     *
     * @param minX   the x coordinate of the minimum corner of the axis-aligned box
     * @param minY   the y coordinate of the minimum corner of the axis-aligned box
     * @param minZ   the z coordinate of the minimum corner of the axis-aligned box
     * @param maxX   the x coordinate of the maximum corner of the axis-aligned box
     * @param maxY   the y coordinate of the maximum corner of the axis-aligned box
     * @param maxZ   the y coordinate of the maximum corner of the axis-aligned box
     * @param outMin will hold the minimum corner of the resulting axis-aligned box
     * @param outMax will hold the maximum corner of the resulting axis-aligned box
     * @return this
     */
    Matrix4x3fc transformAab(float minX, float minY, float minZ, float maxX, float maxY, float maxZ, Vector3fc outMin, Vector3fc outMax);

    /**
     * Transform the axis-aligned box given as the minimum corner <code>min</code> and maximum corner <code>max</code>
     * by <code>this</code> matrix and compute the axis-aligned box of the result whose minimum corner is stored in
     * <code>outMin</code> and maximum corner stored in <code>outMax</code>.
     *
     * @param min    the minimum corner of the axis-aligned box
     * @param max    the maximum corner of the axis-aligned box
     * @param outMin will hold the minimum corner of the resulting axis-aligned box
     * @param outMax will hold the maximum corner of the resulting axis-aligned box
     * @return this
     */
    Matrix4x3fc transformAab(IVector3f min, IVector3f max, Vector3fc outMin, Vector3fc outMax);

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
    Matrix4x3fc lerp(IMatrix4x3f other, float t, Matrix4x3fc dest);

    /**
     * Apply a model transformation to this matrix for a right-handed coordinate system, that aligns the local
     * <code>+Z</code> axis with <code>dir</code> and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>L</code> the lookat matrix, then the new matrix will be
     * <code>M * L</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>M * L *
     * v</code>, the lookat transformation will be applied first!
     * <p>
     * This method is equivalent to calling: <tt>mul(new Matrix4x3f().lookAt(new IVector3f(), new
     * IVector3f(dir).negate(), up).invert(), dest)</tt>
     *
     * @param dir  the direction to rotate towards
     * @param up   the up vector
     * @param dest will hold the result
     * @return dest
     *
     * @see #rotateTowards(float, float, float, float, float, float, Matrix4x3fc)
     */
    Matrix4x3fc rotateTowards(IVector3f dir, IVector3f up, Matrix4x3fc dest);

    /**
     * Apply a model transformation to this matrix for a right-handed coordinate system, that aligns the local
     * <code>+Z</code> axis with <code>(dirX, dirY, dirZ)</code> and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>L</code> the lookat matrix, then the new matrix will be
     * <code>M * L</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>M * L *
     * v</code>, the lookat transformation will be applied first!
     * <p>
     * This method is equivalent to calling: <tt>mul(new Matrix4x3f().lookAt(0, 0, 0, -dirX, -dirY, -dirZ, upX, upY,
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
     * @see #rotateTowards(IVector3f, IVector3f, Matrix4x3fc)
     */
    Matrix4x3fc rotateTowards(float dirX, float dirY, float dirZ, float upX, float upY, float upZ, Matrix4x3fc dest);

    /**
     * Extract the Euler angles from the rotation represented by the upper left 3x3 submatrix of <code>this</code> and
     * store the extracted Euler angles in <code>dest</code>.
     * <p>
     * This method assumes that the upper left of <code>this</code> only represents a rotation without scaling.
     * <p>
     * Note that the returned Euler angles must be applied in the order <tt>Z * Y * X</tt> to obtain the identical
     * matrix. This means that calling {@link IMatrix4x3f#rotateZYX(float, float, float, Matrix4x3fc)} using the
     * obtained Euler angles will yield the same rotation as the original matrix from which the Euler angles were
     * obtained, so in the below code the matrix <tt>m2</tt> should be identical to <tt>m</tt> (disregarding possible
     * floating-point inaccuracies).
     * <pre>
     * Matrix4x3f m = ...; // &lt;- matrix only representing rotation
     * Matrix4x3fc n = new Matrix4x3fc();
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