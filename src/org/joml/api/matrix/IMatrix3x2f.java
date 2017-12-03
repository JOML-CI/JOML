/*
 * (C) Copyright 2017 JOML

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

import org.joml.api.vector.IVector2f;
import org.joml.api.vector.IVector3f;
import org.joml.api.vector.Vector2fc;
import org.joml.api.vector.Vector3fc;

//#ifdef __HAS_NIO__
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
//#endif

//#ifdef __GWT__
import com.google.gwt.typedarrays.shared.Float32Array;
//#endif

/**
 * Interface to a read-only view of a 3x2 matrix of single-precision floats.
 *
 * @author Kai Burjack
 */
public interface IMatrix3x2f {

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
     * Multiply this matrix by the supplied <code>right</code> matrix by assuming a third row in both matrices of
     * <tt>(0, 0, 1)</tt> and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the <code>right</code> matrix, then the new
     * matrix will be <code>M * R</code>. So when transforming a vector <code>v</code> with the new matrix by using
     * <code>M * R * v</code>, the transformation of the right matrix will be applied first!
     *
     * @param right the right operand of the matrix multiplication
     * @param dest  will hold the result
     * @return dest
     */
    Matrix3x2fc mul(IMatrix3x2f right, Matrix3x2fc dest);

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
    Matrix3x2fc mulLocal(IMatrix3x2f left, Matrix3x2fc dest);

    /**
     * Return the determinant of this matrix.
     *
     * @return the determinant
     */
    float determinant();

    /**
     * Invert the <code>this</code> matrix by assuming a third row in this matrix of <tt>(0, 0, 1)</tt> and store the
     * result in <code>dest</code>.
     *
     * @param dest will hold the result
     * @return dest
     */
    Matrix3x2fc invert(Matrix3x2fc dest);

    /**
     * Apply a translation to this matrix by translating by the given number of units in x and y and store the result in
     * <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>T</code> the translation matrix, then the new matrix will
     * be <code>M * T</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>M * T *
     * v</code>, the translation will be applied first!
     *
     * @param x    the offset to translate in x
     * @param y    the offset to translate in y
     * @param dest will hold the result
     * @return dest
     */
    Matrix3x2fc translate(float x, float y, Matrix3x2fc dest);

    /**
     * Apply a translation to this matrix by translating by the given number of units in x and y, and store the result
     * in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>T</code> the translation matrix, then the new matrix will
     * be <code>M * T</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>M * T *
     * v</code>, the translation will be applied first!
     *
     * @param offset the offset to translate
     * @param dest   will hold the result
     * @return dest
     */
    Matrix3x2fc translate(IVector2f offset, Matrix3x2fc dest);

    /**
     * Pre-multiply a translation to this matrix by translating by the given number of units in x and y and store the
     * result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>T</code> the translation matrix, then the new matrix will
     * be <code>T * M</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>T * M *
     * v</code>, the translation will be applied last!
     *
     * @param offset the number of units in x and y by which to translate
     * @param dest   will hold the result
     * @return dest
     */
    Matrix3x2fc translateLocal(IVector2f offset, Matrix3x2fc dest);

    /**
     * Pre-multiply a translation to this matrix by translating by the given number of units in x and y and store the
     * result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>T</code> the translation matrix, then the new matrix will
     * be <code>T * M</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>T * M *
     * v</code>, the translation will be applied last!
     *
     * @param x    the offset to translate in x
     * @param y    the offset to translate in y
     * @param dest will hold the result
     * @return dest
     */
    Matrix3x2fc translateLocal(float x, float y, Matrix3x2fc dest);

    /**
     * Get the current values of <code>this</code> matrix and store them into <code>dest</code>.
     *
     * @param dest the destination matrix
     * @return dest
     */
    Matrix3x2fc get(Matrix3x2fc dest);

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
     * Store this matrix as an equivalent 4x4 matrix in column-major order into the supplied {@link FloatBuffer} at the
     * current buffer {@link FloatBuffer#position() position}.
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
     * Store this matrix as an equivalent 4x4 matrix in column-major order into the supplied {@link FloatBuffer}
     * starting at the specified absolute buffer position/index.
     * <p>
     * This method will not increment the position of the given FloatBuffer.
     *
     * @param index  the absolute position into the FloatBuffer
     * @param buffer will receive the values of this matrix in column-major order
     * @return the passed in buffer
     */
    FloatBuffer get4x4(int index, FloatBuffer buffer);

    /**
     * Store this matrix as an equivalent 4x4 matrix in column-major order into the supplied {@link ByteBuffer} at the
     * current buffer {@link ByteBuffer#position() position}.
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
     * Store this matrix as an equivalent 4x4 matrix in column-major order into the supplied {@link ByteBuffer} starting
     * at the specified absolute buffer position/index.
     * <p>
     * This method will not increment the position of the given ByteBuffer.
     *
     * @param index  the absolute position into the ByteBuffer
     * @param buffer will receive the values of this matrix in column-major order
     * @return the passed in buffer
     */
    ByteBuffer get4x4(int index, ByteBuffer buffer);
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

    /**
     * Apply scaling to this matrix by scaling the unit axes by the given x and y and store the result in
     * <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>S</code> the scaling matrix, then the new matrix will be
     * <code>M * S</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>M * S *
     * v</code>, the scaling will be applied first!
     *
     * @param x    the factor of the x component
     * @param y    the factor of the y component
     * @param dest will hold the result
     * @return dest
     */
    Matrix3x2fc scale(float x, float y, Matrix3x2fc dest);

    /**
     * Pre-multiply scaling to <code>this</code> matrix by scaling the base axes by the given sx and sy factors while
     * using the given <tt>(ox, oy)</tt> as the scaling origin, and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>S</code> the scaling matrix, then the new matrix will be
     * <code>S * M</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>S * M *
     * v</code> , the scaling will be applied last!
     * <p>
     * This method is equivalent to calling: <tt>new Matrix3x2fc().translate(ox, oy).scale(sx, sy).translate(-ox,
     * -oy).mul(this, dest)</tt>
     *
     * @param sx   the scaling factor of the x component
     * @param sy   the scaling factor of the y component
     * @param ox   the x coordinate of the scaling origin
     * @param oy   the y coordinate of the scaling origin
     * @param dest will hold the result
     * @return dest
     */
    Matrix3x2fc scaleAroundLocal(float sx, float sy, float ox, float oy, Matrix3x2fc dest);

    /**
     * Pre-multiply scaling to this matrix by scaling the base axes by the given <code>factor</code> while using
     * <tt>(ox, oy)</tt> as the scaling origin, and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>S</code> the scaling matrix, then the new matrix will be
     * <code>S * M</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>S * M *
     * v</code>, the scaling will be applied last!
     * <p>
     * This method is equivalent to calling: <tt>new Matrix3x2fc().translate(ox, oy).scale(factor).translate(-ox,
     * -oy).mul(this, dest)</tt>
     *
     * @param factor the scaling factor for all three axes
     * @param ox     the x coordinate of the scaling origin
     * @param oy     the y coordinate of the scaling origin
     * @param dest   will hold the result
     * @return this
     */
    Matrix3x2fc scaleAroundLocal(float factor, float ox, float oy, Matrix3x2fc dest);

    /**
     * Apply scaling to this matrix by uniformly scaling the two base axes by the given <code>xy</code> factor and store
     * the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>S</code> the scaling matrix, then the new matrix will be
     * <code>M * S</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>M * S *
     * v</code>, the scaling will be applied first!
     *
     * @param xy   the factor for the two components
     * @param dest will hold the result
     * @return dest
     *
     * @see #scale(float, float, Matrix3x2fc)
     */
    Matrix3x2fc scale(float xy, Matrix3x2fc dest);

    /**
     * Pre-multiply scaling to <code>this</code> matrix by scaling the two base axes by the given <code>xy</code>
     * factor, and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>S</code> the scaling matrix, then the new matrix will be
     * <code>S * M</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>S * M *
     * v</code> , the scaling will be applied last!
     *
     * @param xy   the factor to scale all two base axes by
     * @param dest will hold the result
     * @return dest
     */
    Matrix3x2fc scaleLocal(float xy, Matrix3x2fc dest);

    /**
     * Pre-multiply scaling to <code>this</code> matrix by scaling the base axes by the given x and y factors and store
     * the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>S</code> the scaling matrix, then the new matrix will be
     * <code>S * M</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>S * M *
     * v</code> , the scaling will be applied last!
     *
     * @param x    the factor of the x component
     * @param y    the factor of the y component
     * @param dest will hold the result
     * @return dest
     */
    Matrix3x2fc scaleLocal(float x, float y, Matrix3x2fc dest);

    /**
     * Apply scaling to <code>this</code> matrix by scaling the base axes by the given sx and sy factors while using
     * <tt>(ox, oy)</tt> as the scaling origin, and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>S</code> the scaling matrix, then the new matrix will be
     * <code>M * S</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>M * S *
     * v</code> , the scaling will be applied first!
     * <p>
     * This method is equivalent to calling: <tt>translate(ox, oy, dest).scale(sx, sy).translate(-ox, -oy)</tt>
     *
     * @param sx   the scaling factor of the x component
     * @param sy   the scaling factor of the y component
     * @param ox   the x coordinate of the scaling origin
     * @param oy   the y coordinate of the scaling origin
     * @param dest will hold the result
     * @return dest
     */
    Matrix3x2fc scaleAround(float sx, float sy, float ox, float oy, Matrix3x2fc dest);

    /**
     * Apply scaling to this matrix by scaling the base axes by the given <code>factor</code> while using <tt>(ox,
     * oy)</tt> as the scaling origin, and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>S</code> the scaling matrix, then the new matrix will be
     * <code>M * S</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>M * S *
     * v</code>, the scaling will be applied first!
     * <p>
     * This method is equivalent to calling: <tt>translate(ox, oy, dest).scale(factor).translate(-ox, -oy)</tt>
     *
     * @param factor the scaling factor for all three axes
     * @param ox     the x coordinate of the scaling origin
     * @param oy     the y coordinate of the scaling origin
     * @param dest   will hold the result
     * @return this
     */
    Matrix3x2fc scaleAround(float factor, float ox, float oy, Matrix3x2fc dest);

    /**
     * Transform/multiply the given vector by this matrix by assuming a third row in this matrix of <tt>(0, 0, 1)</tt>
     * and store the result in that vector.
     *
     * @param v the vector to transform and to hold the final result
     * @return v
     *
     * @see Vector3fc#mul(IMatrix3x2f)
     */
    Vector3fc transform(Vector3fc v);

    /**
     * Transform/multiply the given vector by this matrix and store the result in <code>dest</code>.
     *
     * @param v    the vector to transform
     * @param dest will contain the result
     * @return dest
     *
     * @see IVector3f#mul(IMatrix3x2f, Vector3fc)
     */
    Vector3fc transform(IVector3f v, Vector3fc dest);

    /**
     * Transform/multiply the given vector <tt>(x, y, z)</tt> by this matrix and store the result in <code>dest</code>.
     *
     * @param x    the x component of the vector to transform
     * @param y    the y component of the vector to transform
     * @param z    the z component of the vector to transform
     * @param dest will contain the result
     * @return dest
     */
    Vector3fc transform(float x, float y, float z, Vector3fc dest);

    /**
     * Transform/multiply the given 2D-vector, as if it was a 3D-vector with z=1, by this matrix and store the result in
     * that vector.
     * <p>
     * The given 2D-vector is treated as a 3D-vector with its z-component being 1.0, so it will represent a
     * position/location in 2D-space rather than a direction.
     * <p>
     * In order to store the result in another vector, use {@link #transformPosition(IVector2f, Vector2fc)}.
     *
     * @param v the vector to transform and to hold the final result
     * @return v
     *
     * @see #transformPosition(IVector2f, Vector2fc)
     * @see #transform(Vector3fc)
     */
    Vector2fc transformPosition(Vector2fc v);

    /**
     * Transform/multiply the given 2D-vector, as if it was a 3D-vector with z=1, by this matrix and store the result in
     * <code>dest</code>.
     * <p>
     * The given 2D-vector is treated as a 3D-vector with its z-component being 1.0, so it will represent a
     * position/location in 2D-space rather than a direction.
     * <p>
     * In order to store the result in the same vector, use {@link #transformPosition(Vector2fc)}.
     *
     * @param v    the vector to transform
     * @param dest will hold the result
     * @return dest
     *
     * @see #transformPosition(Vector2fc)
     * @see #transform(IVector3f, Vector3fc)
     */
    Vector2fc transformPosition(IVector2f v, Vector2fc dest);

    /**
     * Transform/multiply the given 2D-vector <tt>(x, y)</tt>, as if it was a 3D-vector with z=1, by this matrix and
     * store the result in <code>dest</code>.
     * <p>
     * The given 2D-vector is treated as a 3D-vector with its z-component being 1.0, so it will represent a
     * position/location in 2D-space rather than a direction.
     * <p>
     * In order to store the result in the same vector, use {@link #transformPosition(Vector2fc)}.
     *
     * @param x    the x component of the vector to transform
     * @param y    the y component of the vector to transform
     * @param dest will hold the result
     * @return dest
     *
     * @see #transformPosition(Vector2fc)
     * @see #transform(IVector3f, Vector3fc)
     */
    Vector2fc transformPosition(float x, float y, Vector2fc dest);

    /**
     * Transform/multiply the given 2D-vector, as if it was a 3D-vector with z=0, by this matrix and store the result in
     * that vector.
     * <p>
     * The given 2D-vector is treated as a 3D-vector with its z-component being <tt>0.0</tt>, so it will represent a
     * direction in 2D-space rather than a position. This method will therefore not take the translation part of the
     * matrix into account.
     * <p>
     * In order to store the result in another vector, use {@link #transformDirection(IVector2f, Vector2fc)}.
     *
     * @param v the vector to transform and to hold the final result
     * @return v
     *
     * @see #transformDirection(IVector2f, Vector2fc)
     */
    Vector2fc transformDirection(Vector2fc v);

    /**
     * Transform/multiply the given 2D-vector, as if it was a 3D-vector with z=0, by this matrix and store the result in
     * <code>dest</code>.
     * <p>
     * The given 2D-vector is treated as a 3D-vector with its z-component being <tt>0.0</tt>, so it will represent a
     * direction in 2D-space rather than a position. This method will therefore not take the translation part of the
     * matrix into account.
     * <p>
     * In order to store the result in the same vector, use {@link #transformDirection(Vector2fc)}.
     *
     * @param v    the vector to transform
     * @param dest will hold the result
     * @return dest
     *
     * @see #transformDirection(Vector2fc)
     */
    Vector2fc transformDirection(IVector2f v, Vector2fc dest);

    /**
     * Transform/multiply the given 2D-vector <tt>(x, y)</tt>, as if it was a 3D-vector with z=0, by this matrix and
     * store the result in <code>dest</code>.
     * <p>
     * The given 2D-vector is treated as a 3D-vector with its z-component being <tt>0.0</tt>, so it will represent a
     * direction in 2D-space rather than a position. This method will therefore not take the translation part of the
     * matrix into account.
     * <p>
     * In order to store the result in the same vector, use {@link #transformDirection(Vector2fc)}.
     *
     * @param x    the x component of the vector to transform
     * @param y    the y component of the vector to transform
     * @param dest will hold the result
     * @return dest
     *
     * @see #transformDirection(Vector2fc)
     */
    Vector2fc transformDirection(float x, float y, Vector2fc dest);

    /**
     * Apply a rotation transformation to this matrix by rotating the given amount of radians and store the result in
     * <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the rotation matrix, then the new matrix will be
     * <code>M * R</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>M * R *
     * v</code>, the rotation will be applied first!
     *
     * @param ang  the angle in radians
     * @param dest will hold the result
     * @return dest
     */
    Matrix3x2fc rotate(float ang, Matrix3x2fc dest);

    /**
     * Pre-multiply a rotation to this matrix by rotating the given amount of radians and store the result in
     * <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the rotation matrix, then the new matrix will be
     * <code>R * M</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>R * M *
     * v</code>, the rotation will be applied last!
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Rotation_matrix_from_axis_and_angle">http://en.wikipedia.org</a>
     *
     * @param ang  the angle in radians
     * @param dest will hold the result
     * @return dest
     */
    Matrix3x2fc rotateLocal(float ang, Matrix3x2fc dest);

    /**
     * Apply a rotation transformation to this matrix by rotating the given amount of radians about the specified
     * rotation center <tt>(x, y)</tt> and store the result in <code>dest</code>.
     * <p>
     * This method is equivalent to calling: <tt>translate(x, y, dest).rotate(ang).translate(-x, -y)</tt>
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the rotation matrix, then the new matrix will be
     * <code>M * R</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>M * R *
     * v</code>, the rotation will be applied first!
     *
     * @param ang  the angle in radians
     * @param x    the x component of the rotation center
     * @param y    the y component of the rotation center
     * @param dest will hold the result
     * @return dest
     *
     * @see #translate(float, float, Matrix3x2fc)
     * @see #rotate(float, Matrix3x2fc)
     */
    Matrix3x2fc rotateAbout(float ang, float x, float y, Matrix3x2fc dest);

    /**
     * Apply a rotation transformation to this matrix that rotates the given normalized <code>fromDir</code> direction
     * vector to point along the normalized <code>toDir</code>, and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the rotation matrix, then the new matrix will be
     * <code>M * R</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>M * R *
     * v</code>, the rotation will be applied first!
     *
     * @param fromDir the normalized direction which should be rotate to point along <code>toDir</code>
     * @param toDir   the normalized destination direction
     * @param dest    will hold the result
     * @return dest
     */
    Matrix3x2fc rotateTo(IVector2f fromDir, IVector2f toDir, Matrix3x2fc dest);

    /**
     * Apply a "view" transformation to this matrix that maps the given <tt>(left, bottom)</tt> and <tt>(right,
     * top)</tt> corners to <tt>(-1, -1)</tt> and <tt>(1, 1)</tt> respectively and store the result in
     * <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>O</code> the orthographic projection matrix, then the new
     * matrix will be <code>M * O</code>. So when transforming a vector <code>v</code> with the new matrix by using
     * <code>M * O * v</code>, the orthographic projection transformation will be applied first!
     *
     * @param left   the distance from the center to the left view edge
     * @param right  the distance from the center to the right view edge
     * @param bottom the distance from the center to the bottom view edge
     * @param top    the distance from the center to the top view edge
     * @param dest   will hold the result
     * @return dest
     */
    Matrix3x2fc view(float left, float right, float bottom, float top, Matrix3x2fc dest);

    /**
     * Obtain the position that gets transformed to the origin by <code>this</code> matrix. This can be used to get the
     * position of the "camera" from a given <i>view</i> transformation matrix.
     * <p>
     * This method is equivalent to the following code:
     * <pre>
     * Matrix3x2fc inv = new Matrix3x2fc(this).invertAffine();
     * inv.transform(origin.set(0, 0));
     * </pre>
     *
     * @param origin will hold the position transformed to the origin
     * @return origin
     */
    Vector2fc origin(Vector2fc origin);

    /**
     * Obtain the extents of the view transformation of <code>this</code> matrix and store it in <code>area</code>. This
     * can be used to determine which region of the screen (i.e. the NDC space) is covered by the view.
     *
     * @param area will hold the view area as <tt>[minX, minY, maxX, maxY]</tt>
     * @return area
     */
    float[] viewArea(float[] area);

    /**
     * Obtain the direction of <tt>+X</tt> before the transformation represented by <code>this</code> matrix is
     * applied.
     * <p>
     * This method uses the rotation component of the left 2x2 submatrix to obtain the direction that is transformed to
     * <tt>+X</tt> by <code>this</code> matrix.
     * <p>
     * This method is equivalent to the following code:
     * <pre>
     * Matrix3x2fc inv = new Matrix3x2fc(this).invert();
     * inv.transformDirection(dir.set(1, 0)).normalize();
     * </pre>
     * If <code>this</code> is already an orthogonal matrix, then consider using {@link #normalizedPositiveX(Vector2fc)}
     * instead.
     * <p>
     * Reference: <a href="http://www.euclideanspace.com/maths/algebra/matrix/functions/inverse/threeD/">http://www.euclideanspace.com</a>
     *
     * @param dir will hold the direction of <tt>+X</tt>
     * @return dir
     */
    Vector2fc positiveX(Vector2fc dir);

    /**
     * Obtain the direction of <tt>+X</tt> before the transformation represented by <code>this</code> <i>orthogonal</i>
     * matrix is applied. This method only produces correct results if <code>this</code> is an <i>orthogonal</i>
     * matrix.
     * <p>
     * This method uses the rotation component of the left 2x2 submatrix to obtain the direction that is transformed to
     * <tt>+X</tt> by <code>this</code> matrix.
     * <p>
     * This method is equivalent to the following code:
     * <pre>
     * Matrix3x2fc inv = new Matrix3x2fc(this).transpose();
     * inv.transformDirection(dir.set(1, 0));
     * </pre>
     * <p>
     * Reference: <a href="http://www.euclideanspace.com/maths/algebra/matrix/functions/inverse/threeD/">http://www.euclideanspace.com</a>
     *
     * @param dir will hold the direction of <tt>+X</tt>
     * @return dir
     */
    Vector2fc normalizedPositiveX(Vector2fc dir);

    /**
     * Obtain the direction of <tt>+Y</tt> before the transformation represented by <code>this</code> matrix is
     * applied.
     * <p>
     * This method uses the rotation component of the left 2x2 submatrix to obtain the direction that is transformed to
     * <tt>+Y</tt> by <code>this</code> matrix.
     * <p>
     * This method is equivalent to the following code:
     * <pre>
     * Matrix3x2fc inv = new Matrix3x2fc(this).invert();
     * inv.transformDirection(dir.set(0, 1)).normalize();
     * </pre>
     * If <code>this</code> is already an orthogonal matrix, then consider using {@link #normalizedPositiveY(Vector2fc)}
     * instead.
     * <p>
     * Reference: <a href="http://www.euclideanspace.com/maths/algebra/matrix/functions/inverse/threeD/">http://www.euclideanspace.com</a>
     *
     * @param dir will hold the direction of <tt>+Y</tt>
     * @return dir
     */
    Vector2fc positiveY(Vector2fc dir);

    /**
     * Obtain the direction of <tt>+Y</tt> before the transformation represented by <code>this</code> <i>orthogonal</i>
     * matrix is applied. This method only produces correct results if <code>this</code> is an <i>orthogonal</i>
     * matrix.
     * <p>
     * This method uses the rotation component of the left 2x2 submatrix to obtain the direction that is transformed to
     * <tt>+Y</tt> by <code>this</code> matrix.
     * <p>
     * This method is equivalent to the following code:
     * <pre>
     * Matrix3x2fc inv = new Matrix3x2fc(this).transpose();
     * inv.transformDirection(dir.set(0, 1));
     * </pre>
     * <p>
     * Reference: <a href="http://www.euclideanspace.com/maths/algebra/matrix/functions/inverse/threeD/">http://www.euclideanspace.com</a>
     *
     * @param dir will hold the direction of <tt>+Y</tt>
     * @return dir
     */
    Vector2fc normalizedPositiveY(Vector2fc dir);

    /**
     * Unproject the given window coordinates <tt>(winX, winY)</tt> by <code>this</code> matrix using the specified
     * viewport.
     * <p>
     * This method first converts the given window coordinates to normalized device coordinates in the range
     * <tt>[-1..1]</tt> and then transforms those NDC coordinates by the inverse of <code>this</code> matrix.
     * <p>
     * As a necessary computation step for unprojecting, this method computes the inverse of <code>this</code> matrix.
     * In order to avoid computing the matrix inverse with every invocation, the inverse of <code>this</code> matrix can
     * be built once outside using {@link #invert(Matrix3x2fc)} and then the method {@link #unprojectInv(float, float,
     * int[], Vector2fc) unprojectInv()} can be invoked on it.
     *
     * @param winX     the x-coordinate in window coordinates (pixels)
     * @param winY     the y-coordinate in window coordinates (pixels)
     * @param viewport the viewport described by <tt>[x, y, width, height]</tt>
     * @param dest     will hold the unprojected position
     * @return dest
     *
     * @see #unprojectInv(float, float, int[], Vector2fc)
     * @see #invert(Matrix3x2fc)
     */
    Vector2fc unproject(float winX, float winY, int[] viewport, Vector2fc dest);

    /**
     * Unproject the given window coordinates <tt>(winX, winY)</tt> by <code>this</code> matrix using the specified
     * viewport.
     * <p>
     * This method differs from {@link #unproject(float, float, int[], Vector2fc) unproject()} in that it assumes that
     * <code>this</code> is already the inverse matrix of the original projection matrix. It exists to avoid recomputing
     * the matrix inverse with every invocation.
     *
     * @param winX     the x-coordinate in window coordinates (pixels)
     * @param winY     the y-coordinate in window coordinates (pixels)
     * @param viewport the viewport described by <tt>[x, y, width, height]</tt>
     * @param dest     will hold the unprojected position
     * @return dest
     *
     * @see #unproject(float, float, int[], Vector2fc)
     */
    Vector2fc unprojectInv(float winX, float winY, int[] viewport, Vector2fc dest);

    /**
     * Test whether the given point <tt>(x, y)</tt> is within the frustum defined by <code>this</code> matrix.
     * <p>
     * This method assumes <code>this</code> matrix to be a transformation from any arbitrary coordinate system/space
     * <tt>M</tt> into standard OpenGL clip space and tests whether the given point with the coordinates <tt>(x, y,
     * z)</tt> given in space <tt>M</tt> is within the clip space.
     * <p>
     * Reference: <a href="http://gamedevs.org/uploads/fast-extraction-viewing-frustum-planes-from-world-view-projection-matrix.pdf">
     * Fast Extraction of Viewing Frustum Planes from the World-View-Projection Matrix</a>
     *
     * @param x the x-coordinate of the point
     * @param y the y-coordinate of the point
     * @return <code>true</code> if the given point is inside the frustum; <code>false</code> otherwise
     */
    boolean testPoint(float x, float y);

    /**
     * Test whether the given circle is partly or completely within or outside of the frustum defined by
     * <code>this</code> matrix.
     * <p>
     * This method assumes <code>this</code> matrix to be a transformation from any arbitrary coordinate system/space
     * <tt>M</tt> into standard OpenGL clip space and tests whether the given sphere with the coordinates <tt>(x, y,
     * z)</tt> given in space <tt>M</tt> is within the clip space.
     * <p>
     * Reference: <a href="http://gamedevs.org/uploads/fast-extraction-viewing-frustum-planes-from-world-view-projection-matrix.pdf">
     * Fast Extraction of Viewing Frustum Planes from the World-View-Projection Matrix</a>
     *
     * @param x the x-coordinate of the circle's center
     * @param y the y-coordinate of the circle's center
     * @param r the circle's radius
     * @return <code>true</code> if the given circle is partly or completely inside the frustum; <code>false</code>
     * otherwise
     */
    boolean testCircle(float x, float y, float r);

    /**
     * Test whether the given axis-aligned rectangle is partly or completely within or outside of the frustum defined by
     * <code>this</code> matrix. The rectangle is specified via its min and max corner coordinates.
     * <p>
     * This method assumes <code>this</code> matrix to be a transformation from any arbitrary coordinate system/space
     * <tt>M</tt> into standard OpenGL clip space and tests whether the given axis-aligned rectangle with its minimum
     * corner coordinates <tt>(minX, minY, minZ)</tt> and maximum corner coordinates <tt>(maxX, maxY, maxZ)</tt> given
     * in space <tt>M</tt> is within the clip space.
     * <p>
     * Reference: <a href="http://old.cescg.org/CESCG-2002/DSykoraJJelinek/">Efficient View Frustum Culling</a> <br>
     * Reference: <a href="http://gamedevs.org/uploads/fast-extraction-viewing-frustum-planes-from-world-view-projection-matrix.pdf">
     * Fast Extraction of Viewing Frustum Planes from the World-View-Projection Matrix</a>
     *
     * @param minX the x-coordinate of the minimum corner
     * @param minY the y-coordinate of the minimum corner
     * @param maxX the x-coordinate of the maximum corner
     * @param maxY the y-coordinate of the maximum corner
     * @return <code>true</code> if the axis-aligned box is completely or partly inside of the frustum;
     * <code>false</code> otherwise
     */
    boolean testAar(float minX, float minY, float maxX, float maxY);
}
