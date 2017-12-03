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

import org.joml.api.vector.IVector2d;
import org.joml.api.vector.IVector3d;
import org.joml.api.vector.Vector2dc;
import org.joml.api.vector.Vector3dc;

//#ifdef __HAS_NIO__
import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
//#endif

//#ifdef __GWT__
import com.google.gwt.typedarrays.shared.Float64Array;
//#endif

/**
 * Interface to a read-only view of a 3x2 matrix of double-precision floats.
 *
 * @author Kai Burjack
 */
public interface IMatrix3x2d {

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
    Matrix3x2dc mul(IMatrix3x2d right, Matrix3x2dc dest);

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
    Matrix3x2dc mulLocal(IMatrix3x2d left, Matrix3x2dc dest);

    /**
     * Return the determinant of this matrix.
     *
     * @return the determinant
     */
    double determinant();

    /**
     * Invert the <code>this</code> matrix by assuming a third row in this matrix of <tt>(0, 0, 1)</tt> and store the
     * result in <code>dest</code>.
     *
     * @param dest will hold the result
     * @return dest
     */
    Matrix3x2dc invert(Matrix3x2dc dest);

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
    Matrix3x2dc translate(double x, double y, Matrix3x2dc dest);

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
    Matrix3x2dc translate(IVector2d offset, Matrix3x2dc dest);

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
    Matrix3x2dc translateLocal(IVector2d offset, Matrix3x2dc dest);

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
    Matrix3x2dc translateLocal(double x, double y, Matrix3x2dc dest);

    /**
     * Get the current values of <code>this</code> matrix and store them into <code>dest</code>.
     *
     * @param dest the destination matrix
     * @return dest
     */
    Matrix3x2dc get(Matrix3x2dc dest);

    //#ifdef __GWT__

    /**
     * Store this matrix in column-major order into the supplied {@link Float64Array}.
     *
     * @param buffer will receive the values of this matrix in column-major order
     * @return the passed in buffer
     */
    Float64Array get(Float64Array buffer);

    /**
     * Store this matrix in column-major order into the supplied {@link Float64Array} at the given index.
     *
     * @param index  the index at which to store this matrix in the supplied Float64Array
     * @param buffer will receive the values of this matrix in column-major order
     * @return the passed in buffer
     */
    Float64Array get(int index, Float64Array buffer);
    //#endif

    //#ifdef __HAS_NIO__

    /**
     * Store this matrix in column-major order into the supplied {@link DoubleBuffer} at the current buffer {@link
     * DoubleBuffer#position() position}.
     * <p>
     * This method will not increment the position of the given DoubleBuffer.
     * <p>
     * In order to specify the offset into the DoubleBuffer at which the matrix is stored, use {@link #get(int,
     * DoubleBuffer)}, taking the absolute position as parameter.
     *
     * @param buffer will receive the values of this matrix in column-major order at its current position
     * @return the passed in buffer
     *
     * @see #get(int, DoubleBuffer)
     */
    DoubleBuffer get(DoubleBuffer buffer);

    /**
     * Store this matrix in column-major order into the supplied {@link DoubleBuffer} starting at the specified absolute
     * buffer position/index.
     * <p>
     * This method will not increment the position of the given DoubleBuffer.
     *
     * @param index  the absolute position into the DoubleBuffer
     * @param buffer will receive the values of this matrix in column-major order
     * @return the passed in buffer
     */
    DoubleBuffer get(int index, DoubleBuffer buffer);

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
     * Store this matrix as an equivalent 4x4 matrix in column-major order into the supplied {@link DoubleBuffer} at the
     * current buffer {@link DoubleBuffer#position() position}.
     * <p>
     * This method will not increment the position of the given DoubleBuffer.
     * <p>
     * In order to specify the offset into the DoubleBuffer at which the matrix is stored, use {@link #get4x4(int,
     * DoubleBuffer)}, taking the absolute position as parameter.
     *
     * @param buffer will receive the values of this matrix in column-major order at its current position
     * @return the passed in buffer
     *
     * @see #get4x4(int, DoubleBuffer)
     */
    DoubleBuffer get4x4(DoubleBuffer buffer);

    /**
     * Store this matrix as an equivalent 4x4 matrix in column-major order into the supplied {@link DoubleBuffer}
     * starting at the specified absolute buffer position/index.
     * <p>
     * This method will not increment the position of the given DoubleBuffer.
     *
     * @param index  the absolute position into the DoubleBuffer
     * @param buffer will receive the values of this matrix in column-major order
     * @return the passed in buffer
     */
    DoubleBuffer get4x4(int index, DoubleBuffer buffer);

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
     * Store this matrix as an equivalent 4x4 matrix into the supplied double array in column-major order at the given
     * offset.
     *
     * @param arr    the array to write the matrix values into
     * @param offset the offset into the array
     * @return the passed in array
     */
    double[] get4x4(double[] arr, int offset);

    /**
     * Store this matrix as an equivalent 4x4 matrix into the supplied double array in column-major order.
     * <p>
     * In order to specify an explicit offset into the array, use the method {@link #get4x4(double[], int)}.
     *
     * @param arr the array to write the matrix values into
     * @return the passed in array
     *
     * @see #get4x4(double[], int)
     */
    double[] get4x4(double[] arr);

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
    Matrix3x2dc scale(double x, double y, Matrix3x2dc dest);

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
    Matrix3x2dc scaleLocal(double xy, Matrix3x2dc dest);

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
    Matrix3x2dc scaleLocal(double x, double y, Matrix3x2dc dest);

    /**
     * Pre-multiply scaling to <code>this</code> matrix by scaling the base axes by the given sx and sy factors while
     * using the given <tt>(ox, oy)</tt> as the scaling origin, and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>S</code> the scaling matrix, then the new matrix will be
     * <code>S * M</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>S * M *
     * v</code> , the scaling will be applied last!
     * <p>
     * This method is equivalent to calling: <tt>new Matrix3x2dc().translate(ox, oy).scale(sx, sy).translate(-ox,
     * -oy).mul(this, dest)</tt>
     *
     * @param sx   the scaling factor of the x component
     * @param sy   the scaling factor of the y component
     * @param ox   the x coordinate of the scaling origin
     * @param oy   the y coordinate of the scaling origin
     * @param dest will hold the result
     * @return dest
     */
    Matrix3x2dc scaleAroundLocal(double sx, double sy, double ox, double oy, Matrix3x2dc dest);

    /**
     * Pre-multiply scaling to this matrix by scaling the base axes by the given <code>factor</code> while using
     * <tt>(ox, oy)</tt> as the scaling origin, and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>S</code> the scaling matrix, then the new matrix will be
     * <code>S * M</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>S * M *
     * v</code>, the scaling will be applied last!
     * <p>
     * This method is equivalent to calling: <tt>new Matrix3x2dc().translate(ox, oy).scale(factor).translate(-ox,
     * -oy).mul(this, dest)</tt>
     *
     * @param factor the scaling factor for all three axes
     * @param ox     the x coordinate of the scaling origin
     * @param oy     the y coordinate of the scaling origin
     * @param dest   will hold the result
     * @return this
     */
    Matrix3x2dc scaleAroundLocal(double factor, double ox, double oy, Matrix3x2dc dest);

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
     * @see #scale(double, double, Matrix3x2dc)
     */
    Matrix3x2dc scale(double xy, Matrix3x2dc dest);

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
    Matrix3x2dc scaleAround(double sx, double sy, double ox, double oy, Matrix3x2dc dest);

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
    Matrix3x2dc scaleAround(double factor, double ox, double oy, Matrix3x2dc dest);

    /**
     * Transform/multiply the given vector by this matrix by assuming a third row in this matrix of <tt>(0, 0, 1)</tt>
     * and store the result in that vector.
     *
     * @param v the vector to transform and to hold the final result
     * @return v
     *
     * @see Vector3dc#mul(IMatrix3x2d)
     */
    Vector3dc transform(Vector3dc v);

    /**
     * Transform/multiply the given vector by this matrix and store the result in <code>dest</code>.
     *
     * @param v    the vector to transform
     * @param dest will contain the result
     * @return dest
     *
     * @see Vector3dc#mul(IMatrix3x2d, Vector3dc)
     */
    Vector3dc transform(IVector3d v, Vector3dc dest);

    /**
     * Transform/multiply the given vector <tt>(x, y, z)</tt> by this matrix and store the result in <code>dest</code>.
     *
     * @param x    the x component of the vector to transform
     * @param y    the y component of the vector to transform
     * @param z    the z component of the vector to transform
     * @param dest will contain the result
     * @return dest
     */
    Vector3dc transform(double x, double y, double z, Vector3dc dest);

    /**
     * Transform/multiply the given 2D-vector, as if it was a 3D-vector with z=1, by this matrix and store the result in
     * that vector.
     * <p>
     * The given 2D-vector is treated as a 3D-vector with its z-component being 1.0, so it will represent a
     * position/location in 2D-space rather than a direction.
     * <p>
     * In order to store the result in another vector, use {@link #transformPosition(IVector2d, Vector2dc)}.
     *
     * @param v the vector to transform and to hold the final result
     * @return v
     *
     * @see #transformPosition(IVector2d, Vector2dc)
     * @see #transform(Vector3dc)
     */
    Vector2dc transformPosition(Vector2dc v);

    /**
     * Transform/multiply the given 2D-vector, as if it was a 3D-vector with z=1, by this matrix and store the result in
     * <code>dest</code>.
     * <p>
     * The given 2D-vector is treated as a 3D-vector with its z-component being 1.0, so it will represent a
     * position/location in 2D-space rather than a direction.
     * <p>
     * In order to store the result in the same vector, use {@link #transformPosition(Vector2dc)}.
     *
     * @param v    the vector to transform
     * @param dest will hold the result
     * @return dest
     *
     * @see #transformPosition(Vector2dc)
     * @see #transform(IVector3d, Vector3dc)
     */
    Vector2dc transformPosition(IVector2d v, Vector2dc dest);

    /**
     * Transform/multiply the given 2D-vector <tt>(x, y)</tt>, as if it was a 3D-vector with z=1, by this matrix and
     * store the result in <code>dest</code>.
     * <p>
     * The given 2D-vector is treated as a 3D-vector with its z-component being 1.0, so it will represent a
     * position/location in 2D-space rather than a direction.
     * <p>
     * In order to store the result in the same vector, use {@link #transformPosition(Vector2dc)}.
     *
     * @param x    the x component of the vector to transform
     * @param y    the y component of the vector to transform
     * @param dest will hold the result
     * @return dest
     *
     * @see #transformPosition(Vector2dc)
     * @see #transform(IVector3d, Vector3dc)
     */
    Vector2dc transformPosition(double x, double y, Vector2dc dest);

    /**
     * Transform/multiply the given 2D-vector, as if it was a 3D-vector with z=0, by this matrix and store the result in
     * that vector.
     * <p>
     * The given 2D-vector is treated as a 3D-vector with its z-component being <tt>0.0</tt>, so it will represent a
     * direction in 2D-space rather than a position. This method will therefore not take the translation part of the
     * matrix into account.
     * <p>
     * In order to store the result in another vector, use {@link #transformDirection(IVector2d, Vector2dc)}.
     *
     * @param v the vector to transform and to hold the final result
     * @return v
     *
     * @see #transformDirection(IVector2d, Vector2dc)
     */
    Vector2dc transformDirection(Vector2dc v);

    /**
     * Transform/multiply the given 2D-vector, as if it was a 3D-vector with z=0, by this matrix and store the result in
     * <code>dest</code>.
     * <p>
     * The given 2D-vector is treated as a 3D-vector with its z-component being <tt>0.0</tt>, so it will represent a
     * direction in 2D-space rather than a position. This method will therefore not take the translation part of the
     * matrix into account.
     * <p>
     * In order to store the result in the same vector, use {@link #transformDirection(Vector2dc)}.
     *
     * @param v    the vector to transform and to hold the final result
     * @param dest will hold the result
     * @return dest
     *
     * @see #transformDirection(Vector2dc)
     */
    Vector2dc transformDirection(IVector2d v, Vector2dc dest);

    /**
     * Transform/multiply the given 2D-vector <tt>(x, y)</tt>, as if it was a 3D-vector with z=0, by this matrix and
     * store the result in <code>dest</code>.
     * <p>
     * The given 2D-vector is treated as a 3D-vector with its z-component being <tt>0.0</tt>, so it will represent a
     * direction in 2D-space rather than a position. This method will therefore not take the translation part of the
     * matrix into account.
     * <p>
     * In order to store the result in the same vector, use {@link #transformDirection(Vector2dc)}.
     *
     * @param x    the x component of the vector to transform
     * @param y    the y component of the vector to transform
     * @param dest will hold the result
     * @return dest
     *
     * @see #transformDirection(Vector2dc)
     */
    Vector2dc transformDirection(double x, double y, Vector2dc dest);

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
    Matrix3x2dc rotate(double ang, Matrix3x2dc dest);

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
    Matrix3x2dc rotateLocal(double ang, Matrix3x2dc dest);

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
     * @see #translate(double, double, Matrix3x2dc)
     * @see #rotate(double, Matrix3x2dc)
     */
    Matrix3x2dc rotateAbout(double ang, double x, double y, Matrix3x2dc dest);

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
    Matrix3x2dc rotateTo(IVector2d fromDir, IVector2d toDir, Matrix3x2dc dest);

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
    Matrix3x2dc view(double left, double right, double bottom, double top, Matrix3x2dc dest);

    /**
     * Obtain the position that gets transformed to the origin by <code>this</code> matrix. This can be used to get the
     * position of the "camera" from a given <i>view</i> transformation matrix.
     * <p>
     * This method is equivalent to the following code:
     * <pre>
     * Matrix3x2dc inv = new Matrix3x2dc(this).invertAffine();
     * inv.transform(origin.set(0, 0));
     * </pre>
     *
     * @param origin will hold the position transformed to the origin
     * @return origin
     */
    Vector2dc origin(Vector2dc origin);

    /**
     * Obtain the extents of the view transformation of <code>this</code> matrix and store it in <code>area</code>. This
     * can be used to determine which region of the screen (i.e. the NDC space) is covered by the view.
     *
     * @param area will hold the view area as <tt>[minX, minY, maxX, maxY]</tt>
     * @return area
     */
    double[] viewArea(double[] area);

    /**
     * Obtain the direction of <tt>+X</tt> before the transformation represented by <code>this</code> matrix is
     * applied.
     * <p>
     * This method uses the rotation component of the left 2x2 submatrix to obtain the direction that is transformed to
     * <tt>+X</tt> by <code>this</code> matrix.
     * <p>
     * This method is equivalent to the following code:
     * <pre>
     * Matrix3x2dc inv = new Matrix3x2dc(this).invert();
     * inv.transformDirection(dir.set(1, 0)).normalize();
     * </pre>
     * If <code>this</code> is already an orthogonal matrix, then consider using {@link #normalizedPositiveX(Vector2dc)}
     * instead.
     * <p>
     * Reference: <a href="http://www.euclideanspace.com/maths/algebra/matrix/functions/inverse/threeD/">http://www.euclideanspace.com</a>
     *
     * @param dir will hold the direction of <tt>+X</tt>
     * @return dir
     */
    Vector2dc positiveX(Vector2dc dir);

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
     * Matrix3x2dc inv = new Matrix3x2dc(this).transpose();
     * inv.transformDirection(dir.set(1, 0));
     * </pre>
     * <p>
     * Reference: <a href="http://www.euclideanspace.com/maths/algebra/matrix/functions/inverse/threeD/">http://www.euclideanspace.com</a>
     *
     * @param dir will hold the direction of <tt>+X</tt>
     * @return dir
     */
    Vector2dc normalizedPositiveX(Vector2dc dir);

    /**
     * Obtain the direction of <tt>+Y</tt> before the transformation represented by <code>this</code> matrix is
     * applied.
     * <p>
     * This method uses the rotation component of the left 2x2 submatrix to obtain the direction that is transformed to
     * <tt>+Y</tt> by <code>this</code> matrix.
     * <p>
     * This method is equivalent to the following code:
     * <pre>
     * Matrix3x2dc inv = new Matrix3x2dc(this).invert();
     * inv.transformDirection(dir.set(0, 1)).normalize();
     * </pre>
     * If <code>this</code> is already an orthogonal matrix, then consider using {@link #normalizedPositiveY(Vector2dc)}
     * instead.
     * <p>
     * Reference: <a href="http://www.euclideanspace.com/maths/algebra/matrix/functions/inverse/threeD/">http://www.euclideanspace.com</a>
     *
     * @param dir will hold the direction of <tt>+Y</tt>
     * @return dir
     */
    Vector2dc positiveY(Vector2dc dir);

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
     * Matrix3x2dc inv = new Matrix3x2dc(this).transpose();
     * inv.transformDirection(dir.set(0, 1));
     * </pre>
     * <p>
     * Reference: <a href="http://www.euclideanspace.com/maths/algebra/matrix/functions/inverse/threeD/">http://www.euclideanspace.com</a>
     *
     * @param dir will hold the direction of <tt>+Y</tt>
     * @return dir
     */
    Vector2dc normalizedPositiveY(Vector2dc dir);

    /**
     * Unproject the given window coordinates <tt>(winX, winY)</tt> by <code>this</code> matrix using the specified
     * viewport.
     * <p>
     * This method first converts the given window coordinates to normalized device coordinates in the range
     * <tt>[-1..1]</tt> and then transforms those NDC coordinates by the inverse of <code>this</code> matrix.
     * <p>
     * As a necessary computation step for unprojecting, this method computes the inverse of <code>this</code> matrix.
     * In order to avoid computing the matrix inverse with every invocation, the inverse of <code>this</code> matrix can
     * be built once outside using {@link #invert(Matrix3x2dc)} and then the method {@link #unprojectInv(double, double,
     * int[], Vector2dc) unprojectInv()} can be invoked on it.
     *
     * @param winX     the x-coordinate in window coordinates (pixels)
     * @param winY     the y-coordinate in window coordinates (pixels)
     * @param viewport the viewport described by <tt>[x, y, width, height]</tt>
     * @param dest     will hold the unprojected position
     * @return dest
     *
     * @see #unprojectInv(double, double, int[], Vector2dc)
     * @see #invert(Matrix3x2dc)
     */
    Vector2dc unproject(double winX, double winY, int[] viewport, Vector2dc dest);

    /**
     * Unproject the given window coordinates <tt>(winX, winY)</tt> by <code>this</code> matrix using the specified
     * viewport.
     * <p>
     * This method differs from {@link #unproject(double, double, int[], Vector2dc) unproject()} in that it assumes that
     * <code>this</code> is already the inverse matrix of the original projection matrix. It exists to avoid recomputing
     * the matrix inverse with every invocation.
     *
     * @param winX     the x-coordinate in window coordinates (pixels)
     * @param winY     the y-coordinate in window coordinates (pixels)
     * @param viewport the viewport described by <tt>[x, y, width, height]</tt>
     * @param dest     will hold the unprojected position
     * @return dest
     *
     * @see #unproject(double, double, int[], Vector2dc)
     */
    Vector2dc unprojectInv(double winX, double winY, int[] viewport, Vector2dc dest);

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
    boolean testPoint(double x, double y);

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
    boolean testCircle(double x, double y, double r);

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
    boolean testAar(double minX, double minY, double maxX, double maxY);
}
