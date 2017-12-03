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
import org.joml.api.vector.Vector2fc;
import org.joml.api.vector.Vector3fc;

import java.io.Externalizable;
import java.text.DecimalFormat;
import java.text.NumberFormat;

//#ifdef __HAS_NIO__
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
//#endif

/**
 * Contains the definition of a 3x2 matrix of floats, and associated functions to transform it. The matrix is
 * column-major to match OpenGL's interpretation, and it looks like this:
 * <p>
 * m00  m10  m20<br> m01  m11  m21<br>
 *
 * @author Kai Burjack
 */
public abstract class Matrix3x2fc implements IMatrix3x2f, Externalizable {

    /**
     * Set the value of the matrix element at column 0 and row 0
     *
     * @param m00 the new value
     * @return the value of the matrix element
     */
    public abstract Matrix3x2fc m00(float m00);

    /**
     * Set the value of the matrix element at column 0 and row 1
     *
     * @param m01 the new value
     * @return the value of the matrix element
     */
    public abstract Matrix3x2fc m01(float m01);

    /**
     * Set the value of the matrix element at column 1 and row 0
     *
     * @param m10 the new value
     * @return the value of the matrix element
     */
    public abstract Matrix3x2fc m10(float m10);

    /**
     * Set the value of the matrix element at column 1 and row 1
     *
     * @param m11 the new value
     * @return the value of the matrix element
     */
    public abstract Matrix3x2fc m11(float m11);

    /**
     * Set the value of the matrix element at column 2 and row 0
     *
     * @param m20 the new value
     * @return the value of the matrix element
     */
    public abstract Matrix3x2fc m20(float m20);

    /**
     * Set the value of the matrix element at column 2 and row 1
     *
     * @param m21 the new value
     * @return the value of the matrix element
     */
    public abstract Matrix3x2fc m21(float m21);

    /**
     * Set the elements of this matrix to the ones in <code>m</code>.
     *
     * @param m the matrix to copy the elements from
     * @return this
     */
    public abstract Matrix3x2fc set(IMatrix3x2f m);

    /**
     * Multiply this matrix by the supplied <code>right</code> matrix by assuming a third row in both matrices of
     * <tt>(0, 0, 1)</tt>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the <code>right</code> matrix, then the new
     * matrix will be <code>M * R</code>. So when transforming a vector <code>v</code> with the new matrix by using
     * <code>M * R * v</code>, the transformation of the right matrix will be applied first!
     *
     * @param right the right operand of the matrix multiplication
     * @return this
     */
    public abstract Matrix3x2fc mul(IMatrix3x2f right);

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
    public abstract Matrix3x2fc mulLocal(IMatrix3x2f left);

    /**
     * Set the values within this matrix to the supplied float values. The result looks like this:
     * <p>
     * m00, m10, m20<br> m01, m11, m21<br>
     *
     * @param m00 the new value of m00
     * @param m01 the new value of m01
     * @param m10 the new value of m10
     * @param m11 the new value of m11
     * @param m20 the new value of m20
     * @param m21 the new value of m21
     * @return this
     */
    public abstract Matrix3x2fc set(float m00, float m01,
                                    float m10, float m11,
                                    float m20, float m21);

    /**
     * Set the values in this matrix based on the supplied float array. The result looks like this:
     * <p>
     * 0, 2, 4<br> 1, 3, 5<br>
     * <p>
     * This method only uses the first 6 values, all others are ignored.
     *
     * @param m the array to read the matrix values from
     * @return this
     */
    public abstract Matrix3x2fc set(float m[]);

    /**
     * Invert this matrix by assuming a third row in this matrix of <tt>(0, 0, 1)</tt>.
     *
     * @return this
     */
    public abstract Matrix3x2fc invert();

    /**
     * Set this matrix to be a simple translation matrix in a two-dimensional coordinate system.
     * <p>
     * The resulting matrix can be multiplied against another transformation matrix to obtain an additional
     * translation.
     * <p>
     * In order to apply a translation via to an already existing transformation matrix, use {@link #translate(float,
     * float) translate()} instead.
     *
     * @param x the units to translate in x
     * @param y the units to translate in y
     * @return this
     *
     * @see #translate(float, float)
     */
    public abstract Matrix3x2fc translation(float x, float y);

    /**
     * Set this matrix to be a simple translation matrix in a two-dimensional coordinate system.
     * <p>
     * The resulting matrix can be multiplied against another transformation matrix to obtain an additional
     * translation.
     * <p>
     * In order to apply a translation via to an already existing transformation matrix, use {@link
     * #translate(IVector2f) translate()} instead.
     *
     * @param offset the translation
     * @return this
     *
     * @see #translate(IVector2f)
     */
    public abstract Matrix3x2fc translation(IVector2f offset);

    /**
     * Set only the translation components of this matrix <tt>(m20, m21)</tt> to the given values <tt>(x, y)</tt>.
     * <p>
     * To build a translation matrix instead, use {@link #translation(float, float)}. To apply a translation to another
     * matrix, use {@link #translate(float, float)}.
     *
     * @param x the offset to translate in x
     * @param y the offset to translate in y
     * @return this
     *
     * @see #translation(float, float)
     * @see #translate(float, float)
     */
    public abstract Matrix3x2fc setTranslation(float x, float y);

    /**
     * Set only the translation components of this matrix <tt>(m20, m21)</tt> to the given values <tt>(offset.x,
     * offset.y)</tt>.
     * <p>
     * To build a translation matrix instead, use {@link #translation(IVector2f)}. To apply a translation to another
     * matrix, use {@link #translate(IVector2f)}.
     *
     * @param offset the new translation to set
     * @return this
     *
     * @see #translation(IVector2f)
     * @see #translate(IVector2f)
     */
    public abstract Matrix3x2fc setTranslation(Vector2fc offset);

    /**
     * Apply a translation to this matrix by translating by the given number of units in x and y.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>T</code> the translation matrix, then the new matrix will
     * be <code>M * T</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>M * T *
     * v</code>, the translation will be applied first!
     * <p>
     * In order to set the matrix to a translation transformation without post-multiplying it, use {@link
     * #translation(float, float)}.
     *
     * @param x the offset to translate in x
     * @param y the offset to translate in y
     * @return this
     *
     * @see #translation(float, float)
     */
    public abstract Matrix3x2fc translate(float x, float y);

    /**
     * Apply a translation to this matrix by translating by the given number of units in x and y.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>T</code> the translation matrix, then the new matrix will
     * be <code>M * T</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>M * T *
     * v</code>, the translation will be applied first!
     * <p>
     * In order to set the matrix to a translation transformation without post-multiplying it, use {@link
     * #translation(float, float)}.
     *
     * @param offset the offset to translate
     * @return this
     *
     * @see #translation(IVector2f)
     */
    public abstract Matrix3x2fc translate(IVector2f offset);

    /**
     * Pre-multiply a translation to this matrix by translating by the given number of units in x and y.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>T</code> the translation matrix, then the new matrix will
     * be <code>T * M</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>T * M *
     * v</code>, the translation will be applied last!
     * <p>
     * In order to set the matrix to a translation transformation without pre-multiplying it, use {@link
     * #translation(IVector2f)}.
     *
     * @param offset the number of units in x and y by which to translate
     * @return this
     *
     * @see #translation(IVector2f)
     */
    public abstract Matrix3x2fc translateLocal(IVector2f offset);

    /**
     * Pre-multiply a translation to this matrix by translating by the given number of units in x and y.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>T</code> the translation matrix, then the new matrix will
     * be <code>T * M</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>T * M *
     * v</code>, the translation will be applied last!
     * <p>
     * In order to set the matrix to a translation transformation without pre-multiplying it, use {@link
     * #translation(float, float)}.
     *
     * @param x the offset to translate in x
     * @param y the offset to translate in y
     * @return this
     *
     * @see #translation(float, float)
     */
    public abstract Matrix3x2fc translateLocal(float x, float y);

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
     * Set the values of this matrix by reading 6 float values from the given {@link FloatBuffer} in column-major order,
     * starting at its current position.
     * <p>
     * The FloatBuffer is expected to contain the values in column-major order.
     * <p>
     * The position of the FloatBuffer will not be changed by this method.
     *
     * @param buffer the FloatBuffer to read the matrix values from in column-major order
     * @return this
     */
    public abstract Matrix3x2fc set(FloatBuffer buffer);

    /**
     * Set the values of this matrix by reading 6 float values from the given {@link ByteBuffer} in column-major order,
     * starting at its current position.
     * <p>
     * The ByteBuffer is expected to contain the values in column-major order.
     * <p>
     * The position of the ByteBuffer will not be changed by this method.
     *
     * @param buffer the ByteBuffer to read the matrix values from in column-major order
     * @return this
     */
    public abstract Matrix3x2fc set(ByteBuffer buffer);
    //#endif

    /**
     * Set all values within this matrix to zero.
     *
     * @return this
     */
    public abstract Matrix3x2fc zero();

    /**
     * Set this matrix to the identity.
     *
     * @return this
     */
    public abstract Matrix3x2fc identity();

    /**
     * Apply scaling to this matrix by scaling the base axes by the given x and y factors.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>S</code> the scaling matrix, then the new matrix will be
     * <code>M * S</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>M * S *
     * v</code>, the scaling will be applied first!
     *
     * @param x the factor of the x component
     * @param y the factor of the y component
     * @return this
     */
    public abstract Matrix3x2fc scale(float x, float y);

    /**
     * Apply scaling to this matrix by uniformly scaling the two base axes by the given <code>xyz</code> factor.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>S</code> the scaling matrix, then the new matrix will be
     * <code>M * S</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>M * S *
     * v</code>, the scaling will be applied first!
     *
     * @param xy the factor for the two components
     * @return this
     *
     * @see #scale(float, float)
     */
    public abstract Matrix3x2fc scale(float xy);

    /**
     * Pre-multiply scaling to this matrix by scaling the base axes by the given x and y factors.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>S</code> the scaling matrix, then the new matrix will be
     * <code>S * M</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>S * M *
     * v</code>, the scaling will be applied last!
     *
     * @param x the factor of the x component
     * @param y the factor of the y component
     * @return this
     */
    public abstract Matrix3x2fc scaleLocal(float x, float y);

    /**
     * Pre-multiply scaling to this matrix by scaling the base axes by the given xy factor.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>S</code> the scaling matrix, then the new matrix will be
     * <code>S * M</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>S * M *
     * v</code>, the scaling will be applied last!
     *
     * @param xy the factor of the x and y component
     * @return this
     */
    public abstract Matrix3x2fc scaleLocal(float xy);

    /**
     * Apply scaling to this matrix by scaling the base axes by the given sx and sy factors while using <tt>(ox,
     * oy)</tt> as the scaling origin.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>S</code> the scaling matrix, then the new matrix will be
     * <code>M * S</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>M * S *
     * v</code>, the scaling will be applied first!
     * <p>
     * This method is equivalent to calling: <tt>translate(ox, oy).scale(sx, sy).translate(-ox, -oy)</tt>
     *
     * @param sx the scaling factor of the x component
     * @param sy the scaling factor of the y component
     * @param ox the x coordinate of the scaling origin
     * @param oy the y coordinate of the scaling origin
     * @return this
     */
    public abstract Matrix3x2fc scaleAround(float sx, float sy, float ox, float oy);

    /**
     * Apply scaling to this matrix by scaling the base axes by the given <code>factor</code> while using <tt>(ox,
     * oy)</tt> as the scaling origin.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>S</code> the scaling matrix, then the new matrix will be
     * <code>M * S</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>M * S *
     * v</code>, the scaling will be applied first!
     * <p>
     * This method is equivalent to calling: <tt>translate(ox, oy).scale(factor).translate(-ox, -oy)</tt>
     *
     * @param factor the scaling factor for all axes
     * @param ox     the x coordinate of the scaling origin
     * @param oy     the y coordinate of the scaling origin
     * @return this
     */
    public abstract Matrix3x2fc scaleAround(float factor, float ox, float oy);

    /**
     * Pre-multiply scaling to this matrix by scaling the base axes by the given sx and sy factors while using <tt>(ox,
     * oy)</tt> as the scaling origin.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>S</code> the scaling matrix, then the new matrix will be
     * <code>S * M</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>S * M *
     * v</code>, the scaling will be applied last!
     * <p>
     * This method is equivalent to calling: <tt>new Matrix3x2fc().translate(ox, oy).scale(sx, sy).translate(-ox,
     * -oy).mul(this, this)</tt>
     *
     * @param sx the scaling factor of the x component
     * @param sy the scaling factor of the y component
     * @param sz the scaling factor of the z component
     * @param ox the x coordinate of the scaling origin
     * @param oy the y coordinate of the scaling origin
     * @param oz the z coordinate of the scaling origin
     * @return this
     */
    public abstract Matrix3x2fc scaleAroundLocal(float sx, float sy, float sz, float ox, float oy, float oz);

    /**
     * Pre-multiply scaling to this matrix by scaling the base axes by the given <code>factor</code> while using
     * <tt>(ox, oy)</tt> as the scaling origin.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>S</code> the scaling matrix, then the new matrix will be
     * <code>S * M</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>S * M *
     * v</code>, the scaling will be applied last!
     * <p>
     * This method is equivalent to calling: <tt>new Matrix3x2fc().translate(ox, oy).scale(factor).translate(-ox,
     * -oy).mul(this, this)</tt>
     *
     * @param factor the scaling factor for all three axes
     * @param ox     the x coordinate of the scaling origin
     * @param oy     the y coordinate of the scaling origin
     * @return this
     */
    public abstract Matrix3x2fc scaleAroundLocal(float factor, float ox, float oy);

    /**
     * Set this matrix to be a simple scale matrix, which scales the two base axes uniformly by the given factor.
     * <p>
     * The resulting matrix can be multiplied against another transformation matrix to obtain an additional scaling.
     * <p>
     * In order to post-multiply a scaling transformation directly to a matrix, use {@link #scale(float) scale()}
     * instead.
     *
     * @param factor the scale factor in x and y
     * @return this
     *
     * @see #scale(float)
     */
    public abstract Matrix3x2fc scaling(float factor);

    /**
     * Set this matrix to be a simple scale matrix.
     *
     * @param x the scale in x
     * @param y the scale in y
     * @return this
     */
    public abstract Matrix3x2fc scaling(float x, float y);

    /**
     * Set this matrix to a rotation matrix which rotates the given radians.
     * <p>
     * The resulting matrix can be multiplied against another transformation matrix to obtain an additional rotation.
     * <p>
     * In order to apply the rotation transformation to an existing transformation, use {@link #rotate(float) rotate()}
     * instead.
     *
     * @param angle the angle in radians
     * @return this
     *
     * @see #rotate(float)
     */
    public abstract Matrix3x2fc rotation(float angle);

    /**
     * Transform/multiply the given vector by this matrix by assuming a third row in this matrix of <tt>(0, 0, 1)</tt>
     * and store the result in <code>dest</code>.
     *
     * @param v    the vector to transform
     * @param dest will contain the result
     * @return dest
     *
     * @see Vector3fc#mul(IMatrix3x2f, Vector3fc)
     */
    public abstract Vector3fc transform(Vector3fc v, Vector3fc dest);

    /**
     * Apply a rotation transformation to this matrix by rotating the given amount of radians.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the rotation matrix, then the new matrix will be
     * <code>M * R</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>M * R *
     * v</code> , the rotation will be applied first!
     *
     * @param ang the angle in radians
     * @return this
     */
    public abstract Matrix3x2fc rotate(float ang);

    /**
     * Pre-multiply a rotation to this matrix by rotating the given amount of radians.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the rotation matrix, then the new matrix will be
     * <code>R * M</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>R * M *
     * v</code>, the rotation will be applied last!
     * <p>
     * In order to set the matrix to a rotation matrix without pre-multiplying the rotation transformation, use {@link
     * #rotation(float) rotation()}.
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Rotation_matrix_from_axis_and_angle">http://en.wikipedia.org</a>
     *
     * @param ang the angle in radians to rotate
     * @return this
     *
     * @see #rotation(float)
     */
    public abstract Matrix3x2fc rotateLocal(float ang);

    /**
     * Apply a rotation transformation to this matrix by rotating the given amount of radians about the specified
     * rotation center <tt>(x, y)</tt>.
     * <p>
     * This method is equivalent to calling: <tt>translate(x, y).rotate(ang).translate(-x, -y)</tt>
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the rotation matrix, then the new matrix will be
     * <code>M * R</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>M * R *
     * v</code>, the rotation will be applied first!
     *
     * @param ang the angle in radians
     * @param x   the x component of the rotation center
     * @param y   the y component of the rotation center
     * @return dest
     *
     * @see #translate(float, float)
     * @see #rotate(float)
     */
    public abstract Matrix3x2fc rotateAbout(float ang, float x, float y);

    /**
     * Apply a rotation transformation to this matrix that rotates the given normalized <code>fromDir</code> direction
     * vector to point along the normalized <code>toDir</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the rotation matrix, then the new matrix will be
     * <code>M * R</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>M * R *
     * v</code>, the rotation will be applied first!
     *
     * @param fromDir the normalized direction which should be rotate to point along <code>toDir</code>
     * @param toDir   the normalized destination direction
     * @return this
     */
    public abstract Matrix3x2fc rotateTo(IVector2f fromDir, IVector2f toDir);

    /**
     * Apply a "view" transformation to this matrix that maps the given <tt>(left, bottom)</tt> and <tt>(right,
     * top)</tt> corners to <tt>(-1, -1)</tt> and <tt>(1, 1)</tt> respectively.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>O</code> the orthographic projection matrix, then the new
     * matrix will be <code>M * O</code>. So when transforming a vector <code>v</code> with the new matrix by using
     * <code>M * O * v</code>, the orthographic projection transformation will be applied first!
     *
     * @param left   the distance from the center to the left view edge
     * @param right  the distance from the center to the right view edge
     * @param bottom the distance from the center to the bottom view edge
     * @param top    the distance from the center to the top view edge
     * @return this
     *
     * @see #setView(float, float, float, float)
     */
    public abstract Matrix3x2fc view(float left, float right, float bottom, float top);

    /**
     * Set this matrix to define a "view" transformation that maps the given <tt>(left, bottom)</tt> and <tt>(right,
     * top)</tt> corners to <tt>(-1, -1)</tt> and <tt>(1, 1)</tt> respectively.
     *
     * @param left   the distance from the center to the left view edge
     * @param right  the distance from the center to the right view edge
     * @param bottom the distance from the center to the bottom view edge
     * @param top    the distance from the center to the top view edge
     * @return this
     *
     * @see #view(float, float, float, float)
     */
    public abstract Matrix3x2fc setView(float left, float right, float bottom, float top);

    /**
     * Apply shearing to this matrix by shearing along the X axis using the Y axis factor <code>yFactor</code>.
     *
     * @param yFactor the factor for the Y component to shear along the X axis
     * @return this
     */
    public abstract Matrix3x2fc shearX(float yFactor);

    /**
     * Apply shearing to this matrix by shearing along the X axis using the Y axis factor <code>yFactor</code>, and
     * store the result in <code>dest</code>.
     *
     * @param yFactor the factor for the Y component to shear along the X axis
     * @param dest    will hold the result
     * @return dest
     */
    public abstract Matrix3x2fc shearX(float yFactor, Matrix3x2fc dest);

    /**
     * Apply shearing to this matrix by shearing along the Y axis using the X axis factor <code>xFactor</code>.
     *
     * @param xFactor the factor for the X component to shear along the Y axis
     * @return this
     */
    public abstract Matrix3x2fc shearY(float xFactor);

    /**
     * Apply shearing to this matrix by shearing along the Y axis using the X axis factor <code>xFactor</code>, and
     * store the result in <code>dest</code>.
     *
     * @param xFactor the factor for the X component to shear along the Y axis
     * @param dest    will hold the result
     * @return dest
     */
    public abstract Matrix3x2fc shearY(float xFactor, Matrix3x2fc dest);

    /**
     * Compute the extents of the coordinate system before this transformation was applied and store the resulting
     * corner coordinates in <code>corner</code> and the span vectors in <code>xDir</code> and <code>yDir</code>.
     * <p>
     * That means, given the maximum extents of the coordinate system between <tt>[-1..+1]</tt> in all dimensions, this
     * method returns one corner and the length and direction of the two base axis vectors in the coordinate system
     * before this transformation is applied, which transforms into the corner coordinates <tt>[-1, +1]</tt>.
     *
     * @param corner will hold one corner of the span
     * @param xDir   will hold the direction and length of the span along the positive X axis
     * @param yDir   will hold the direction and length of the span along the positive Y axis
     * @return this
     */
    public abstract Matrix3x2fc span(Vector2fc corner, Vector2fc xDir, Vector2fc yDir);
}
