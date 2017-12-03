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
import org.joml.api.Planedc;
import org.joml.api.quaternion.IQuaterniond;
import org.joml.api.quaternion.IQuaternionf;
import org.joml.api.quaternion.Quaterniondc;
import org.joml.api.quaternion.Quaternionfc;
import org.joml.api.vector.*;

//#ifdef __HAS_NIO__
import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
//#endif

/**
 * Interface to a read-only view of a 4x4 matrix of double-precision floats.
 *
 * @author Kai Burjack
 */
public interface IMatrix4d {

    /**
     * Argument to the first parameter of {@link #frustumPlane(int, Vector4dc)} and {@link #frustumPlane(int, Planedc)}
     * identifying the plane with equation <tt>x=-1</tt> when using the identity matrix.
     */
    int PLANE_NX = 0;

    /**
     * Argument to the first parameter of {@link #frustumPlane(int, Vector4dc)} and {@link #frustumPlane(int, Planedc)}
     * identifying the plane with equation <tt>x=1</tt> when using the identity matrix.
     */
    int PLANE_PX = 1;

    /**
     * Argument to the first parameter of {@link #frustumPlane(int, Vector4dc)} and {@link #frustumPlane(int, Planedc)}
     * identifying the plane with equation <tt>y=-1</tt> when using the identity matrix.
     */
    int PLANE_NY = 2;

    /**
     * Argument to the first parameter of {@link #frustumPlane(int, Vector4dc)} and {@link #frustumPlane(int, Planedc)}
     * identifying the plane with equation <tt>y=1</tt> when using the identity matrix.
     */
    int PLANE_PY = 3;

    /**
     * Argument to the first parameter of {@link #frustumPlane(int, Vector4dc)} and {@link #frustumPlane(int, Planedc)}
     * identifying the plane with equation <tt>z=-1</tt> when using the identity matrix.
     */
    int PLANE_NZ = 4;

    /**
     * Argument to the first parameter of {@link #frustumPlane(int, Vector4dc)} and {@link #frustumPlane(int, Planedc)}
     * identifying the plane with equation <tt>z=1</tt> when using the identity matrix.
     */
    int PLANE_PZ = 5;

    /**
     * Argument to the first parameter of {@link #frustumCorner(int, Vector3dc)} identifying the corner <tt>(-1, -1,
     * -1)</tt> when using the identity matrix.
     */
    int CORNER_NXNYNZ = 0;

    /**
     * Argument to the first parameter of {@link #frustumCorner(int, Vector3dc)} identifying the corner <tt>(1, -1,
     * -1)</tt> when using the identity matrix.
     */
    int CORNER_PXNYNZ = 1;

    /**
     * Argument to the first parameter of {@link #frustumCorner(int, Vector3dc)} identifying the corner <tt>(1, 1,
     * -1)</tt> when using the identity matrix.
     */
    int CORNER_PXPYNZ = 2;

    /**
     * Argument to the first parameter of {@link #frustumCorner(int, Vector3dc)} identifying the corner <tt>(-1, 1,
     * -1)</tt> when using the identity matrix.
     */
    int CORNER_NXPYNZ = 3;

    /**
     * Argument to the first parameter of {@link #frustumCorner(int, Vector3dc)} identifying the corner <tt>(1, -1,
     * 1)</tt> when using the identity matrix.
     */
    int CORNER_PXNYPZ = 4;

    /**
     * Argument to the first parameter of {@link #frustumCorner(int, Vector3dc)} identifying the corner <tt>(-1, -1,
     * 1)</tt> when using the identity matrix.
     */
    int CORNER_NXNYPZ = 5;

    /**
     * Argument to the first parameter of {@link #frustumCorner(int, Vector3dc)} identifying the corner <tt>(-1, 1,
     * 1)</tt> when using the identity matrix.
     */
    int CORNER_NXPYPZ = 6;

    /**
     * Argument to the first parameter of {@link #frustumCorner(int, Vector3dc)} identifying the corner <tt>(1, 1,
     * 1)</tt> when using the identity matrix.
     */
    int CORNER_PXPYPZ = 7;

    /**
     * Bit returned by {@link #properties()} to indicate that the matrix represents a perspective transformation.
     */
    byte PROPERTY_PERSPECTIVE = 1 << 0;

    /**
     * Bit returned by {@link #properties()} to indicate that the matrix represents an affine transformation.
     */
    byte PROPERTY_AFFINE = 1 << 1;

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
     * Return the value of the matrix element at column 0 and row 3.
     *
     * @return the value of the matrix element
     */
    double m03();

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
     * Return the value of the matrix element at column 1 and row 3.
     *
     * @return the value of the matrix element
     */
    double m13();

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
     * Return the value of the matrix element at column 2 and row 3.
     *
     * @return the value of the matrix element
     */
    double m23();

    /**
     * Return the value of the matrix element at column 3 and row 0.
     *
     * @return the value of the matrix element
     */
    double m30();

    /**
     * Return the value of the matrix element at column 3 and row 1.
     *
     * @return the value of the matrix element
     */
    double m31();

    /**
     * Return the value of the matrix element at column 3 and row 2.
     *
     * @return the value of the matrix element
     */
    double m32();

    /**
     * Return the value of the matrix element at column 3 and row 3.
     *
     * @return the value of the matrix element
     */
    double m33();

    /**
     * Multiply this matrix by the supplied <code>right</code> matrix and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the <code>right</code> matrix, then the new
     * matrix will be <code>M * R</code>. So when transforming a vector <code>v</code> with the new matrix by using
     * <code>M * R * v</code>, the transformation of the right matrix will be applied first!
     *
     * @param right the right operand of the multiplication
     * @param dest  will hold the result
     * @return dest
     */
    Matrix4dc mul(IMatrix4d right, Matrix4dc dest);

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
    Matrix4dc mulLocal(IMatrix4d left, Matrix4dc dest);

    /**
     * Pre-multiply this matrix by the supplied <code>left</code> matrix, both of which are assumed to be {@link
     * #isAffine() affine}, and store the result in <code>dest</code>.
     * <p>
     * This method assumes that <code>this</code> matrix and the given <code>left</code> matrix both represent an {@link
     * #isAffine() affine} transformation (i.e. their last rows are equal to <tt>(0, 0, 0, 1)</tt>) and can be used to
     * speed up matrix multiplication if the matrices only represent affine transformations, such as translation,
     * rotation, scaling and shearing (in any combination).
     * <p>
     * This method will not modify either the last row of <code>this</code> or the last row of <code>left</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>L</code> the <code>left</code> matrix, then the new
     * matrix will be <code>L * M</code>. So when transforming a vector <code>v</code> with the new matrix by using
     * <code>L * M * v</code>, the transformation of <code>this</code> matrix will be applied first!
     *
     * @param left the left operand of the matrix multiplication (the last row is assumed to be <tt>(0, 0, 0, 1)</tt>)
     * @param dest the destination matrix, which will hold the result
     * @return dest
     */
    Matrix4dc mulLocalAffine(IMatrix4d left, Matrix4dc dest);

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
    Matrix4dc mul(IMatrix3x2d right, Matrix4dc dest);

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
    Matrix4dc mul(IMatrix3x2f right, Matrix4dc dest);

    /**
     * Multiply this matrix by the supplied <code>right</code> matrix and store the result in <code>dest</code>.
     * <p>
     * The last row of the <code>right</code> matrix is assumed to be <tt>(0, 0, 0, 1)</tt>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the <code>right</code> matrix, then the new
     * matrix will be <code>M * R</code>. So when transforming a vector <code>v</code> with the new matrix by using
     * <code>M * R * v</code>, the transformation of the right matrix will be applied first!
     *
     * @param right the right operand of the matrix multiplication
     * @param dest  the destination matrix, which will hold the result
     * @return dest
     */
    Matrix4dc mul(IMatrix4x3d right, Matrix4dc dest);

    /**
     * Multiply this matrix by the supplied <code>right</code> matrix and store the result in <code>dest</code>.
     * <p>
     * The last row of the <code>right</code> matrix is assumed to be <tt>(0, 0, 0, 1)</tt>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the <code>right</code> matrix, then the new
     * matrix will be <code>M * R</code>. So when transforming a vector <code>v</code> with the new matrix by using
     * <code>M * R * v</code>, the transformation of the right matrix will be applied first!
     *
     * @param right the right operand of the matrix multiplication
     * @param dest  the destination matrix, which will hold the result
     * @return dest
     */
    Matrix4dc mul(IMatrix4x3f right, Matrix4dc dest);

    /**
     * Multiply this matrix by the supplied parameter matrix and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the <code>right</code> matrix, then the new
     * matrix will be <code>M * R</code>. So when transforming a vector <code>v</code> with the new matrix by using
     * <code>M * R * v</code>, the transformation of the right matrix will be applied first!
     *
     * @param right the right operand of the multiplication
     * @param dest  will hold the result
     * @return dest
     */
    Matrix4dc mul(IMatrix4f right, Matrix4dc dest);

    /**
     * Multiply <code>this</code> symmetric perspective projection matrix by the supplied {@link #isAffine() affine}
     * <code>view</code> matrix and store the result in <code>dest</code>.
     * <p>
     * If <code>P</code> is <code>this</code> matrix and <code>V</code> the <code>view</code> matrix, then the new
     * matrix will be <code>P * V</code>. So when transforming a vector <code>v</code> with the new matrix by using
     * <code>P * V * v</code>, the transformation of the <code>view</code> matrix will be applied first!
     *
     * @param view the {@link #isAffine() affine} matrix to multiply <code>this</code> symmetric perspective projection
     *             matrix by
     * @param dest the destination matrix, which will hold the result
     * @return dest
     */
    Matrix4dc mulPerspectiveAffine(IMatrix4d view, Matrix4dc dest);

    /**
     * Multiply this matrix by the supplied <code>right</code> matrix, which is assumed to be {@link #isAffine()
     * affine}, and store the result in <code>dest</code>.
     * <p>
     * This method assumes that the given <code>right</code> matrix represents an {@link #isAffine() affine}
     * transformation (i.e. its last row is equal to <tt>(0, 0, 0, 1)</tt>) and can be used to speed up matrix
     * multiplication if the matrix only represents affine transformations, such as translation, rotation, scaling and
     * shearing (in any combination).
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the <code>right</code> matrix, then the new
     * matrix will be <code>M * R</code>. So when transforming a vector <code>v</code> with the new matrix by using
     * <code>M * R * v</code>, the transformation of the right matrix will be applied first!
     *
     * @param right the right operand of the matrix multiplication (the last row is assumed to be <tt>(0, 0, 0,
     *              1)</tt>)
     * @param dest  the destination matrix, which will hold the result
     * @return dest
     */
    Matrix4dc mulAffineR(IMatrix4d right, Matrix4dc dest);

    /**
     * Multiply this matrix by the supplied <code>right</code> matrix, both of which are assumed to be {@link
     * #isAffine() affine}, and store the result in <code>dest</code>.
     * <p>
     * This method assumes that <code>this</code> matrix and the given <code>right</code> matrix both represent an
     * {@link #isAffine() affine} transformation (i.e. their last rows are equal to <tt>(0, 0, 0, 1)</tt>) and can be
     * used to speed up matrix multiplication if the matrices only represent affine transformations, such as
     * translation, rotation, scaling and shearing (in any combination).
     * <p>
     * This method will not modify either the last row of <code>this</code> or the last row of <code>right</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the <code>right</code> matrix, then the new
     * matrix will be <code>M * R</code>. So when transforming a vector <code>v</code> with the new matrix by using
     * <code>M * R * v</code>, the transformation of the right matrix will be applied first!
     *
     * @param right the right operand of the matrix multiplication (the last row is assumed to be <tt>(0, 0, 0,
     *              1)</tt>)
     * @param dest  the destination matrix, which will hold the result
     * @return dest
     */
    Matrix4dc mulAffine(IMatrix4d right, Matrix4dc dest);

    /**
     * Multiply this matrix, which is assumed to only contain a translation, by the supplied <code>right</code> matrix,
     * which is assumed to be {@link #isAffine() affine}, and store the result in <code>dest</code>.
     * <p>
     * This method assumes that <code>this</code> matrix only contains a translation, and that the given
     * <code>right</code> matrix represents an {@link #isAffine() affine} transformation (i.e. its last row is equal to
     * <tt>(0, 0, 0, 1)</tt>).
     * <p>
     * This method will not modify either the last row of <code>this</code> or the last row of <code>right</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the <code>right</code> matrix, then the new
     * matrix will be <code>M * R</code>. So when transforming a vector <code>v</code> with the new matrix by using
     * <code>M * R * v</code>, the transformation of the right matrix will be applied first!
     *
     * @param right the right operand of the matrix multiplication (the last row is assumed to be <tt>(0, 0, 0,
     *              1)</tt>)
     * @param dest  the destination matrix, which will hold the result
     * @return dest
     */
    Matrix4dc mulTranslationAffine(IMatrix4d right, Matrix4dc dest);

    /**
     * Multiply <code>this</code> orthographic projection matrix by the supplied {@link #isAffine() affine}
     * <code>view</code> matrix and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>V</code> the <code>view</code> matrix, then the new
     * matrix will be <code>M * V</code>. So when transforming a vector <code>v</code> with the new matrix by using
     * <code>M * V * v</code>, the transformation of the <code>view</code> matrix will be applied first!
     *
     * @param view the affine matrix which to multiply <code>this</code> with
     * @param dest the destination matrix, which will hold the result
     * @return dest
     */
    Matrix4dc mulOrthoAffine(IMatrix4d view, Matrix4dc dest);

    /**
     * Component-wise add the upper 4x3 submatrices of <code>this</code> and <code>other</code> by first multiplying
     * each component of <code>other</code>'s 4x3 submatrix by <code>otherFactor</code>, adding that to
     * <code>this</code> and storing the final result in <code>dest</code>.
     * <p>
     * The other components of <code>dest</code> will be set to the ones of <code>this</code>.
     * <p>
     * The matrices <code>this</code> and <code>other</code> will not be changed.
     *
     * @param other       the other matrix
     * @param otherFactor the factor to multiply each of the other matrix's 4x3 components
     * @param dest        will hold the result
     * @return dest
     */
    Matrix4dc fma4x3(IMatrix4d other, double otherFactor, Matrix4dc dest);

    /**
     * Component-wise add <code>this</code> and <code>other</code> and store the result in <code>dest</code>.
     *
     * @param other the other addend
     * @param dest  will hold the result
     * @return dest
     */
    Matrix4dc add(IMatrix4d other, Matrix4dc dest);

    /**
     * Component-wise subtract <code>subtrahend</code> from <code>this</code> and store the result in
     * <code>dest</code>.
     *
     * @param subtrahend the subtrahend
     * @param dest       will hold the result
     * @return dest
     */
    Matrix4dc sub(IMatrix4d subtrahend, Matrix4dc dest);

    /**
     * Component-wise multiply <code>this</code> by <code>other</code> and store the result in <code>dest</code>.
     *
     * @param other the other matrix
     * @param dest  will hold the result
     * @return dest
     */
    Matrix4dc mulComponentWise(IMatrix4d other, Matrix4dc dest);

    /**
     * Component-wise add the upper 4x3 submatrices of <code>this</code> and <code>other</code> and store the result in
     * <code>dest</code>.
     * <p>
     * The other components of <code>dest</code> will be set to the ones of <code>this</code>.
     *
     * @param other the other addend
     * @param dest  will hold the result
     * @return dest
     */
    Matrix4dc add4x3(IMatrix4d other, Matrix4dc dest);

    /**
     * Component-wise add the upper 4x3 submatrices of <code>this</code> and <code>other</code> and store the result in
     * <code>dest</code>.
     * <p>
     * The other components of <code>dest</code> will be set to the ones of <code>this</code>.
     *
     * @param other the other addend
     * @param dest  will hold the result
     * @return dest
     */
    Matrix4dc add4x3(IMatrix4f other, Matrix4dc dest);

    /**
     * Component-wise subtract the upper 4x3 submatrices of <code>subtrahend</code> from <code>this</code> and store the
     * result in <code>dest</code>.
     * <p>
     * The other components of <code>dest</code> will be set to the ones of <code>this</code>.
     *
     * @param subtrahend the subtrahend
     * @param dest       will hold the result
     * @return dest
     */
    Matrix4dc sub4x3(IMatrix4d subtrahend, Matrix4dc dest);

    /**
     * Component-wise multiply the upper 4x3 submatrices of <code>this</code> by <code>other</code> and store the result
     * in <code>dest</code>.
     * <p>
     * The other components of <code>dest</code> will be set to the ones of <code>this</code>.
     *
     * @param other the other matrix
     * @param dest  will hold the result
     * @return dest
     */
    Matrix4dc mul4x3ComponentWise(IMatrix4d other, Matrix4dc dest);

    /**
     * Return the determinant of this matrix.
     * <p>
     * If <code>this</code> matrix represents an {@link #isAffine() affine} transformation, such as translation,
     * rotation, scaling and shearing, and thus its last row is equal to <tt>(0, 0, 0, 1)</tt>, then {@link
     * #determinantAffine()} can be used instead of this method.
     *
     * @return the determinant
     *
     * @see #determinantAffine()
     */
    double determinant();

    /**
     * Return the determinant of the upper left 3x3 submatrix of this matrix.
     *
     * @return the determinant
     */
    double determinant3x3();

    /**
     * Return the determinant of this matrix by assuming that it represents an {@link #isAffine() affine} transformation
     * and thus its last row is equal to <tt>(0, 0, 0, 1)</tt>.
     *
     * @return the determinant
     */
    double determinantAffine();

    /**
     * Invert <code>this</code> matrix and store the result in <code>dest</code>.
     * <p>
     * If <code>this</code> matrix represents an {@link #isAffine() affine} transformation, such as translation,
     * rotation, scaling and shearing, and thus its last row is equal to <tt>(0, 0, 0, 1)</tt>, then {@link
     * #invertAffine(Matrix4dc)} can be used instead of this method.
     *
     * @param dest will hold the result
     * @return dest
     *
     * @see #invertAffine(Matrix4dc)
     */
    Matrix4dc invert(Matrix4dc dest);

    /**
     * If <code>this</code> is a perspective projection matrix obtained via one of the {@link #perspective(double,
     * double, double, double, Matrix4dc) perspective()} methods, that is, if <code>this</code> is a symmetrical
     * perspective frustum transformation, then this method builds the inverse of <code>this</code> and stores it into
     * the given <code>dest</code>.
     * <p>
     * This method can be used to quickly obtain the inverse of a perspective projection matrix when being obtained via
     * {@link #perspective(double, double, double, double, Matrix4dc) perspective()}.
     *
     * @param dest will hold the inverse of <code>this</code>
     * @return dest
     *
     * @see #perspective(double, double, double, double, Matrix4dc)
     */
    Matrix4dc invertPerspective(Matrix4dc dest);

    /**
     * If <code>this</code> is an arbitrary perspective projection matrix obtained via one of the {@link
     * #frustum(double, double, double, double, double, double, Matrix4dc) frustum()} methods, then this method builds
     * the inverse of <code>this</code> and stores it into the given <code>dest</code>.
     * <p>
     * This method can be used to quickly obtain the inverse of a perspective projection matrix.
     * <p>
     * If this matrix represents a symmetric perspective frustum transformation, as obtained via {@link
     * #perspective(double, double, double, double, Matrix4dc) perspective()}, then {@link
     * #invertPerspective(Matrix4dc)} should be used instead.
     *
     * @param dest will hold the inverse of <code>this</code>
     * @return dest
     *
     * @see #frustum(double, double, double, double, double, double, Matrix4dc)
     * @see #invertPerspective(Matrix4dc)
     */
    Matrix4dc invertFrustum(Matrix4dc dest);

    /**
     * Invert <code>this</code> orthographic projection matrix and store the result into the given <code>dest</code>.
     * <p>
     * This method can be used to quickly obtain the inverse of an orthographic projection matrix.
     *
     * @param dest will hold the inverse of <code>this</code>
     * @return dest
     */
    Matrix4dc invertOrtho(Matrix4dc dest);

    /**
     * If <code>this</code> is a perspective projection matrix obtained via one of the {@link #perspective(double,
     * double, double, double, Matrix4dc) perspective()} methods, that is, if <code>this</code> is a symmetrical
     * perspective frustum transformation and the given <code>view</code> matrix is {@link #isAffine() affine} and has
     * unit scaling (for example by being obtained via {@link #lookAt(double, double, double, double, double, double,
     * double, double, double, Matrix4dc) lookAt()}), then this method builds the inverse of <tt>this * view</tt> and
     * stores it into the given <code>dest</code>.
     * <p>
     * This method can be used to quickly obtain the inverse of the combination of the view and projection matrices,
     * when both were obtained via the common methods {@link #perspective(double, double, double, double, Matrix4dc)
     * perspective()} and {@link #lookAt(double, double, double, double, double, double, double, double, double,
     * Matrix4dc) lookAt()} or other methods, that build affine matrices, such as {@link #translate(double, double,
     * double, Matrix4dc) translate} and {@link #rotate(double, double, double, double, Matrix4dc)}, except for {@link
     * #scale(double, double, double, Matrix4dc) scale()}.
     * <p>
     * For the special cases of the matrices <code>this</code> and <code>view</code> mentioned above this method, this
     * method is equivalent to the following code:
     * <pre>
     * dest.set(this).mul(view).invert();
     * </pre>
     *
     * @param view the view transformation (must be {@link #isAffine() affine} and have unit scaling)
     * @param dest will hold the inverse of <tt>this * view</tt>
     * @return dest
     */
    Matrix4dc invertPerspectiveView(IMatrix4d view, Matrix4dc dest);

    /**
     * Invert this matrix by assuming that it is an {@link #isAffine() affine} transformation (i.e. its last row is
     * equal to <tt>(0, 0, 0, 1)</tt>) and write the result into <code>dest</code>.
     * <p>
     * Note that if <code>this</code> matrix also has unit scaling, then the method {@link
     * #invertAffineUnitScale(Matrix4dc)} should be used instead.
     *
     * @param dest will hold the result
     * @return dest
     *
     * @see #invertAffineUnitScale(Matrix4dc)
     */
    Matrix4dc invertAffine(Matrix4dc dest);

    /**
     * Invert this matrix by assuming that it is an {@link #isAffine() affine} transformation (i.e. its last row is
     * equal to <tt>(0, 0, 0, 1)</tt>) and has unit scaling (i.e. {@link #transformDirection(Vector3dc)
     * transformDirection} does not change the {@link IVector3d#length() length} of the vector) and write the result
     * into <code>dest</code>.
     * <p>
     * Reference: <a href="http://www.gamedev.net/topic/425118-inverse--matrix/">http://www.gamedev.net/</a>
     *
     * @param dest will hold the result
     * @return dest
     */
    Matrix4dc invertAffineUnitScale(Matrix4dc dest);

    /**
     * Invert this matrix by assuming that it is an {@link #isAffine() affine} transformation (i.e. its last row is
     * equal to <tt>(0, 0, 0, 1)</tt>) and has unit scaling (i.e. {@link #transformDirection(Vector3dc)
     * transformDirection} does not change the {@link IVector3d#length() length} of the vector), as is the case for
     * matrices built via {@link #lookAt(IVector3d, IVector3d, IVector3d, Matrix4dc)} and their overloads, and write the
     * result into <code>dest</code>.
     * <p>
     * This method is equivalent to calling {@link #invertAffineUnitScale(Matrix4dc)}
     * <p>
     * Reference: <a href="http://www.gamedev.net/topic/425118-inverse--matrix/">http://www.gamedev.net/</a>
     *
     * @param dest will hold the result
     * @return dest
     *
     * @see #invertAffineUnitScale(Matrix4dc)
     */
    Matrix4dc invertLookAt(Matrix4dc dest);

    /**
     * Transpose <code>this</code> matrix and store the result into <code>dest</code>.
     *
     * @param dest will hold the result
     * @return dest
     */
    Matrix4dc transpose(Matrix4dc dest);

    /**
     * Transpose only the upper left 3x3 submatrix of this matrix and store the result in <code>dest</code>.
     * <p>
     * All other matrix elements are left unchanged.
     *
     * @param dest will hold the result
     * @return dest
     */
    Matrix4dc transpose3x3(Matrix4dc dest);

    /**
     * Transpose only the upper left 3x3 submatrix of this matrix and store the result in <code>dest</code>.
     *
     * @param dest will hold the result
     * @return dest
     */
    Matrix3dc transpose3x3(Matrix3dc dest);

    /**
     * Get only the translation components <tt>(m30, m31, m32)</tt> of this matrix and store them in the given vector
     * <code>xyz</code>.
     *
     * @param dest will hold the translation components of this matrix
     * @return dest
     */
    Vector3dc getTranslation(Vector3dc dest);

    /**
     * Get the scaling factors of <code>this</code> matrix for the three base axes.
     *
     * @param dest will hold the scaling factors for <tt>x</tt>, <tt>y</tt> and <tt>z</tt>
     * @return dest
     */
    Vector3dc getScale(Vector3dc dest);

    /**
     * Get the current values of <code>this</code> matrix and store them into <code>dest</code>.
     *
     * @param dest the destination matrix
     * @return the passed in destination
     */
    Matrix4dc get(Matrix4dc dest);

    /**
     * Get the current values of the upper 4x3 submatrix of <code>this</code> matrix and store them into
     * <code>dest</code>.
     *
     * @param dest the destination matrix
     * @return the passed in destination
     */
    Matrix4x3dc get4x3(Matrix4x3dc dest);

    /**
     * Get the current values of the upper left 3x3 submatrix of <code>this</code> matrix and store them into
     * <code>dest</code>.
     *
     * @param dest the destination matrix
     * @return the passed in destination
     */
    Matrix3dc get3x3(Matrix3dc dest);

    /**
     * Get the current values of <code>this</code> matrix and store the represented rotation into the given {@link
     * Quaternionfc}.
     * <p>
     * This method assumes that the first three column vectors of the upper left 3x3 submatrix are not normalized and
     * thus allows to ignore any additional scaling factor that is applied to the matrix.
     *
     * @param dest the destination {@link Quaternionfc}
     * @return the passed in destination
     *
     * @see Quaternionfc#setFromUnnormalized(IMatrix4d)
     */
    Quaternionfc getUnnormalizedRotation(Quaternionfc dest);

    /**
     * Get the current values of <code>this</code> matrix and store the represented rotation into the given {@link
     * Quaternionfc}.
     * <p>
     * This method assumes that the first three column vectors of the upper left 3x3 submatrix are normalized.
     *
     * @param dest the destination {@link Quaternionfc}
     * @return the passed in destination
     *
     * @see Quaternionfc#setFromNormalized(IMatrix4d)
     */
    Quaternionfc getNormalizedRotation(Quaternionfc dest);

    /**
     * Get the current values of <code>this</code> matrix and store the represented rotation into the given {@link
     * Quaterniondc}.
     * <p>
     * This method assumes that the first three column vectors of the upper left 3x3 submatrix are not normalized and
     * thus allows to ignore any additional scaling factor that is applied to the matrix.
     *
     * @param dest the destination {@link Quaterniondc}
     * @return the passed in destination
     *
     * @see Quaterniondc#setFromUnnormalized(IMatrix4d)
     */
    Quaterniondc getUnnormalizedRotation(Quaterniondc dest);

    /**
     * Get the current values of <code>this</code> matrix and store the represented rotation into the given {@link
     * Quaterniondc}.
     * <p>
     * This method assumes that the first three column vectors of the upper left 3x3 submatrix are normalized.
     *
     * @param dest the destination {@link Quaterniondc}
     * @return the passed in destination
     *
     * @see Quaterniondc#setFromNormalized(IMatrix4d)
     */
    Quaterniondc getNormalizedRotation(Quaterniondc dest);

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

    //#ifdef __HAS_NIO__

    /**
     * Store the transpose of this matrix in column-major order into the supplied {@link DoubleBuffer} at the current
     * buffer {@link DoubleBuffer#position() position}.
     * <p>
     * This method will not increment the position of the given DoubleBuffer.
     * <p>
     * In order to specify the offset into the DoubleBuffer at which the matrix is stored, use {@link
     * #getTransposed(int, DoubleBuffer)}, taking the absolute position as parameter.
     *
     * @param buffer will receive the values of this matrix in column-major order at its current position
     * @return the passed in buffer
     *
     * @see #getTransposed(int, DoubleBuffer)
     */
    DoubleBuffer getTransposed(DoubleBuffer buffer);

    /**
     * Store the transpose of this matrix in column-major order into the supplied {@link DoubleBuffer} starting at the
     * specified absolute buffer position/index.
     * <p>
     * This method will not increment the position of the given DoubleBuffer.
     *
     * @param index  the absolute position into the DoubleBuffer
     * @param buffer will receive the values of this matrix in column-major order
     * @return the passed in buffer
     */
    DoubleBuffer getTransposed(int index, DoubleBuffer buffer);

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

    /**
     * Store the upper 4x3 submatrix of <code>this</code> matrix in row-major order into the supplied {@link
     * DoubleBuffer} at the current buffer {@link DoubleBuffer#position() position}.
     * <p>
     * This method will not increment the position of the given DoubleBuffer.
     * <p>
     * In order to specify the offset into the DoubleBuffer at which the matrix is stored, use {@link
     * #get4x3Transposed(int, DoubleBuffer)}, taking the absolute position as parameter.
     *
     * @param buffer will receive the values of the upper 4x3 submatrix in row-major order at its current position
     * @return the passed in buffer
     *
     * @see #get4x3Transposed(int, DoubleBuffer)
     */
    DoubleBuffer get4x3Transposed(DoubleBuffer buffer);

    /**
     * Store the upper 4x3 submatrix of <code>this</code> matrix in row-major order into the supplied {@link
     * DoubleBuffer} starting at the specified absolute buffer position/index.
     * <p>
     * This method will not increment the position of the given DoubleBuffer.
     *
     * @param index  the absolute position into the DoubleBuffer
     * @param buffer will receive the values of the upper 4x3 submatrix in row-major order
     * @return the passed in buffer
     */
    DoubleBuffer get4x3Transposed(int index, DoubleBuffer buffer);

    /**
     * Store the upper 4x3 submatrix of <code>this</code> matrix in row-major order into the supplied {@link ByteBuffer}
     * at the current buffer {@link ByteBuffer#position() position}.
     * <p>
     * This method will not increment the position of the given ByteBuffer.
     * <p>
     * In order to specify the offset into the ByteBuffer at which the matrix is stored, use {@link
     * #get4x3Transposed(int, ByteBuffer)}, taking the absolute position as parameter.
     *
     * @param buffer will receive the values of the upper 4x3 submatrix in row-major order at its current position
     * @return the passed in buffer
     *
     * @see #get4x3Transposed(int, ByteBuffer)
     */
    ByteBuffer get4x3Transposed(ByteBuffer buffer);

    /**
     * Store the upper 4x3 submatrix of <code>this</code> matrix in row-major order into the supplied {@link ByteBuffer}
     * starting at the specified absolute buffer position/index.
     * <p>
     * This method will not increment the position of the given ByteBuffer.
     *
     * @param index  the absolute position into the ByteBuffer
     * @param buffer will receive the values of the upper 4x3 submatrix in row-major order
     * @return the passed in buffer
     */
    ByteBuffer get4x3Transposed(int index, ByteBuffer buffer);
    //#endif

    /**
     * Transform/multiply the given vector by this matrix and store the result in that vector.
     *
     * @param v the vector to transform and to hold the final result
     * @return v
     *
     * @see Vector4dc#mul(IMatrix4d)
     */
    Vector4dc transform(Vector4dc v);

    /**
     * Transform/multiply the given vector by this matrix and store the result in <code>dest</code>.
     *
     * @param v    the vector to transform
     * @param dest will contain the result
     * @return dest
     *
     * @see Vector4dc#mul(IMatrix4d, Vector4dc)
     */
    Vector4dc transform(IVector4d v, Vector4dc dest);

    /**
     * Transform/multiply the vector <tt>(x, y, z, w)</tt> by this matrix and store the result in <code>dest</code>.
     *
     * @param x    the x coordinate of the vector to transform
     * @param y    the y coordinate of the vector to transform
     * @param z    the z coordinate of the vector to transform
     * @param w    the w coordinate of the vector to transform
     * @param dest will contain the result
     * @return dest
     */
    Vector4dc transform(double x, double y, double z, double w, Vector4dc dest);

    /**
     * Transform/multiply the given vector by this matrix, perform perspective divide and store the result in that
     * vector.
     *
     * @param v the vector to transform and to hold the final result
     * @return v
     *
     * @see Vector4dc#mulProject(IMatrix4d)
     */
    Vector4dc transformProject(Vector4dc v);

    /**
     * Transform/multiply the given vector by this matrix, perform perspective divide and store the result in
     * <code>dest</code>.
     *
     * @param v    the vector to transform
     * @param dest will contain the result
     * @return dest
     *
     * @see Vector4dc#mulProject(IMatrix4d, Vector4dc)
     */
    Vector4dc transformProject(IVector4d v, Vector4dc dest);

    /**
     * Transform/multiply the vector <tt>(x, y, z, w)</tt> by this matrix, perform perspective divide and store the
     * result in <code>dest</code>.
     *
     * @param x    the x coordinate of the direction to transform
     * @param y    the y coordinate of the direction to transform
     * @param z    the z coordinate of the direction to transform
     * @param w    the w coordinate of the direction to transform
     * @param dest will contain the result
     * @return dest
     */
    Vector4dc transformProject(double x, double y, double z, double w, Vector4dc dest);

    /**
     * Transform/multiply the given vector by this matrix, perform perspective divide and store the result in that
     * vector.
     * <p>
     * This method uses <tt>w=1.0</tt> as the fourth vector component.
     *
     * @param v the vector to transform and to hold the final result
     * @return v
     *
     * @see Vector3dc#mulProject(IMatrix4d)
     */
    Vector3dc transformProject(Vector3dc v);

    /**
     * Transform/multiply the given vector by this matrix, perform perspective divide and store the result in
     * <code>dest</code>.
     * <p>
     * This method uses <tt>w=1.0</tt> as the fourth vector component.
     *
     * @param v    the vector to transform
     * @param dest will contain the result
     * @return dest
     *
     * @see Vector3dc#mulProject(IMatrix4d, Vector3dc)
     */
    Vector3dc transformProject(IVector3d v, Vector3dc dest);

    /**
     * Transform/multiply the vector <tt>(x, y, z)</tt> by this matrix, perform perspective divide and store the result
     * in <code>dest</code>.
     * <p>
     * This method uses <tt>w=1.0</tt> as the fourth vector component.
     *
     * @param x    the x coordinate of the vector to transform
     * @param y    the y coordinate of the vector to transform
     * @param z    the z coordinate of the vector to transform
     * @param dest will contain the result
     * @return dest
     */
    Vector3dc transformProject(double x, double y, double z, Vector3dc dest);

    /**
     * Transform/multiply the given 3D-vector, as if it was a 4D-vector with w=1, by this matrix and store the result in
     * that vector.
     * <p>
     * The given 3D-vector is treated as a 4D-vector with its w-component being 1.0, so it will represent a
     * position/location in 3D-space rather than a direction. This method is therefore not suited for perspective
     * projection transformations as it will not save the <tt>w</tt> component of the transformed vector. For
     * perspective projection use {@link #transform(Vector4dc)} or {@link #transformProject(Vector3dc)} when perspective
     * divide should be applied, too.
     * <p>
     * In order to store the result in another vector, use {@link #transformPosition(IVector3d, Vector3dc)}.
     *
     * @param v the vector to transform and to hold the final result
     * @return v
     *
     * @see #transformPosition(IVector3d, Vector3dc)
     * @see #transform(Vector4dc)
     * @see #transformProject(Vector3dc)
     */
    Vector3dc transformPosition(Vector3dc v);

    /**
     * Transform/multiply the given 3D-vector, as if it was a 4D-vector with w=1, by this matrix and store the result in
     * <code>dest</code>.
     * <p>
     * The given 3D-vector is treated as a 4D-vector with its w-component being 1.0, so it will represent a
     * position/location in 3D-space rather than a direction. This method is therefore not suited for perspective
     * projection transformations as it will not save the <tt>w</tt> component of the transformed vector. For
     * perspective projection use {@link #transform(IVector4d, Vector4dc)} or {@link #transformProject(IVector3d,
     * Vector3dc)} when perspective divide should be applied, too.
     * <p>
     * In order to store the result in the same vector, use {@link #transformPosition(Vector3dc)}.
     *
     * @param v    the vector to transform
     * @param dest will hold the result
     * @return dest
     *
     * @see #transformPosition(Vector3dc)
     * @see #transform(IVector4d, Vector4dc)
     * @see #transformProject(IVector3d, Vector3dc)
     */
    Vector3dc transformPosition(IVector3d v, Vector3dc dest);

    /**
     * Transform/multiply the 3D-vector <tt>(x, y, z)</tt>, as if it was a 4D-vector with w=1, by this matrix and store
     * the result in <code>dest</code>.
     * <p>
     * The given 3D-vector is treated as a 4D-vector with its w-component being 1.0, so it will represent a
     * position/location in 3D-space rather than a direction. This method is therefore not suited for perspective
     * projection transformations as it will not save the <tt>w</tt> component of the transformed vector. For
     * perspective projection use {@link #transform(double, double, double, double, Vector4dc)} or {@link
     * #transformProject(double, double, double, Vector3dc)} when perspective divide should be applied, too.
     *
     * @param x    the x coordinate of the position
     * @param y    the y coordinate of the position
     * @param z    the z coordinate of the position
     * @param dest will hold the result
     * @return dest
     *
     * @see #transform(double, double, double, double, Vector4dc)
     * @see #transformProject(double, double, double, Vector3dc)
     */
    Vector3dc transformPosition(double x, double y, double z, Vector3dc dest);

    /**
     * Transform/multiply the given 3D-vector, as if it was a 4D-vector with w=0, by this matrix and store the result in
     * that vector.
     * <p>
     * The given 3D-vector is treated as a 4D-vector with its w-component being <tt>0.0</tt>, so it will represent a
     * direction in 3D-space rather than a position. This method will therefore not take the translation part of the
     * matrix into account.
     * <p>
     * In order to store the result in another vector, use {@link #transformDirection(IVector3d, Vector3dc)}.
     *
     * @param v the vector to transform and to hold the final result
     * @return v
     */
    Vector3dc transformDirection(Vector3dc v);

    /**
     * Transform/multiply the given 3D-vector, as if it was a 4D-vector with w=0, by this matrix and store the result in
     * <code>dest</code>.
     * <p>
     * The given 3D-vector is treated as a 4D-vector with its w-component being <tt>0.0</tt>, so it will represent a
     * direction in 3D-space rather than a position. This method will therefore not take the translation part of the
     * matrix into account.
     * <p>
     * In order to store the result in the same vector, use {@link #transformDirection(Vector3dc)}.
     *
     * @param v    the vector to transform and to hold the final result
     * @param dest will hold the result
     * @return dest
     */
    Vector3dc transformDirection(IVector3d v, Vector3dc dest);

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
     */
    Vector3fc transformDirection(IVector3f v, Vector3fc dest);

    /**
     * Transform/multiply the 3D-vector <tt>(x, y, z)</tt>, as if it was a 4D-vector with w=0, by this matrix and store
     * the result in <code>dest</code>.
     * <p>
     * The given 3D-vector is treated as a 4D-vector with its w-component being <tt>0.0</tt>, so it will represent a
     * direction in 3D-space rather than a position. This method will therefore not take the translation part of the
     * matrix into account.
     *
     * @param x    the x coordinate of the direction to transform
     * @param y    the y coordinate of the direction to transform
     * @param z    the z coordinate of the direction to transform
     * @param dest will hold the result
     * @return dest
     */
    Vector3dc transformDirection(double x, double y, double z, Vector3dc dest);

    /**
     * Transform/multiply the given 4D-vector by assuming that <code>this</code> matrix represents an {@link #isAffine()
     * affine} transformation (i.e. its last row is equal to <tt>(0, 0, 0, 1)</tt>).
     * <p>
     * In order to store the result in another vector, use {@link #transformAffine(IVector4d, Vector4dc)}.
     *
     * @param v the vector to transform and to hold the final result
     * @return v
     *
     * @see #transformAffine(IVector4d, Vector4dc)
     */
    Vector4dc transformAffine(Vector4dc v);

    /**
     * Transform/multiply the given 4D-vector by assuming that <code>this</code> matrix represents an {@link #isAffine()
     * affine} transformation (i.e. its last row is equal to <tt>(0, 0, 0, 1)</tt>) and store the result in
     * <code>dest</code>.
     * <p>
     * In order to store the result in the same vector, use {@link #transformAffine(Vector4dc)}.
     *
     * @param v    the vector to transform and to hold the final result
     * @param dest will hold the result
     * @return dest
     *
     * @see #transformAffine(Vector4dc)
     */
    Vector4dc transformAffine(IVector4d v, Vector4dc dest);

    /**
     * Transform/multiply the 4D-vector <tt>(x, y, z, w)</tt> by assuming that <code>this</code> matrix represents an
     * {@link #isAffine() affine} transformation (i.e. its last row is equal to <tt>(0, 0, 0, 1)</tt>) and store the
     * result in <code>dest</code>.
     *
     * @param x    the x coordinate of the direction to transform
     * @param y    the y coordinate of the direction to transform
     * @param z    the z coordinate of the direction to transform
     * @param w    the w coordinate of the direction to transform
     * @param dest will hold the result
     * @return dest
     */
    Vector4dc transformAffine(double x, double y, double z, double w, Vector4dc dest);

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
    Matrix4dc scale(IVector3d xyz, Matrix4dc dest);

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
    Matrix4dc scale(double x, double y, double z, Matrix4dc dest);

    /**
     * Apply scaling to this matrix by uniformly scaling all base axes by the given xyz factor and store the result in
     * <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>S</code> the scaling matrix, then the new matrix will be
     * <code>M * S</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>M * S *
     * v</code> , the scaling will be applied first!
     *
     * @param xyz  the factor for all components
     * @param dest will hold the result
     * @return dest
     *
     * @see #scale(double, double, double, Matrix4dc)
     */
    Matrix4dc scale(double xyz, Matrix4dc dest);

    /**
     * Apply scaling to <code>this</code> matrix by scaling the base axes by the given sx, sy and sz factors while using
     * <tt>(ox, oy, oz)</tt> as the scaling origin, and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>S</code> the scaling matrix, then the new matrix will be
     * <code>M * S</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>M * S *
     * v</code> , the scaling will be applied first!
     * <p>
     * This method is equivalent to calling: <tt>translate(ox, oy, oz, dest).scale(sx, sy, sz).translate(-ox, -oy,
     * -oz)</tt>
     *
     * @param sx   the scaling factor of the x component
     * @param sy   the scaling factor of the y component
     * @param sz   the scaling factor of the z component
     * @param ox   the x coordinate of the scaling origin
     * @param oy   the y coordinate of the scaling origin
     * @param oz   the z coordinate of the scaling origin
     * @param dest will hold the result
     * @return dest
     */
    Matrix4dc scaleAround(double sx, double sy, double sz, double ox, double oy, double oz, Matrix4dc dest);

    /**
     * Apply scaling to this matrix by scaling all three base axes by the given <code>factor</code> while using <tt>(ox,
     * oy, oz)</tt> as the scaling origin, and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>S</code> the scaling matrix, then the new matrix will be
     * <code>M * S</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>M * S *
     * v</code>, the scaling will be applied first!
     * <p>
     * This method is equivalent to calling: <tt>translate(ox, oy, oz, dest).scale(factor).translate(-ox, -oy,
     * -oz)</tt>
     *
     * @param factor the scaling factor for all three axes
     * @param ox     the x coordinate of the scaling origin
     * @param oy     the y coordinate of the scaling origin
     * @param oz     the z coordinate of the scaling origin
     * @param dest   will hold the result
     * @return this
     */
    Matrix4dc scaleAround(double factor, double ox, double oy, double oz, Matrix4dc dest);

    /**
     * Pre-multiply scaling to <code>this</code> matrix by scaling all base axes by the given <code>xyz</code> factor,
     * and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>S</code> the scaling matrix, then the new matrix will be
     * <code>S * M</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>S * M *
     * v</code> , the scaling will be applied last!
     *
     * @param xyz  the factor to scale all three base axes by
     * @param dest will hold the result
     * @return dest
     */
    Matrix4dc scaleLocal(double xyz, Matrix4dc dest);

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
    Matrix4dc scaleLocal(double x, double y, double z, Matrix4dc dest);

    /**
     * Pre-multiply scaling to <code>this</code> matrix by scaling the base axes by the given sx, sy and sz factors
     * while using the given <tt>(ox, oy, oz)</tt> as the scaling origin, and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>S</code> the scaling matrix, then the new matrix will be
     * <code>S * M</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>S * M *
     * v</code> , the scaling will be applied last!
     * <p>
     * This method is equivalent to calling: <tt>new Matrix4dc().translate(ox, oy, oz).scale(sx, sy, sz).translate(-ox,
     * -oy, -oz).mul(this, dest)</tt>
     *
     * @param sx   the scaling factor of the x component
     * @param sy   the scaling factor of the y component
     * @param sz   the scaling factor of the z component
     * @param ox   the x coordinate of the scaling origin
     * @param oy   the y coordinate of the scaling origin
     * @param oz   the z coordinate of the scaling origin
     * @param dest will hold the result
     * @return dest
     */
    Matrix4dc scaleAroundLocal(double sx, double sy, double sz, double ox, double oy, double oz, Matrix4dc dest);

    /**
     * Pre-multiply scaling to this matrix by scaling all three base axes by the given <code>factor</code> while using
     * <tt>(ox, oy, oz)</tt> as the scaling origin, and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>S</code> the scaling matrix, then the new matrix will be
     * <code>S * M</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>S * M *
     * v</code>, the scaling will be applied last!
     * <p>
     * This method is equivalent to calling: <tt>new Matrix4dc().translate(ox, oy, oz).scale(factor).translate(-ox, -oy,
     * -oz).mul(this, dest)</tt>
     *
     * @param factor the scaling factor for all three axes
     * @param ox     the x coordinate of the scaling origin
     * @param oy     the y coordinate of the scaling origin
     * @param oz     the z coordinate of the scaling origin
     * @param dest   will hold the result
     * @return this
     */
    Matrix4dc scaleAroundLocal(double factor, double ox, double oy, double oz, Matrix4dc dest);

    /**
     * Apply rotation to this matrix by rotating the given amount of radians about the given axis specified as x, y and
     * z components and store the result in <code>dest</code>.
     * <p>
     * When used with a right-handed coordinate system, the produced rotation will rotate a vector counter-clockwise
     * around the rotation axis, when viewing along the negative axis direction towards the origin. When used with a
     * left-handed coordinate system, the rotation is clockwise.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the rotation matrix, then the new matrix will be
     * <code>M * R</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>M * R *
     * v</code> , the rotation will be applied first!
     *
     * @param ang  the angle is in radians
     * @param x    the x component of the axis
     * @param y    the y component of the axis
     * @param z    the z component of the axis
     * @param dest will hold the result
     * @return dest
     */
    Matrix4dc rotate(double ang, double x, double y, double z, Matrix4dc dest);

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
    Matrix4dc rotateTranslation(double ang, double x, double y, double z, Matrix4dc dest);

    /**
     * Apply rotation to this {@link #isAffine() affine} matrix by rotating the given amount of radians about the
     * specified <tt>(x, y, z)</tt> axis and store the result in <code>dest</code>.
     * <p>
     * This method assumes <code>this</code> to be {@link #isAffine() affine}.
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
    Matrix4dc rotateAffine(double ang, double x, double y, double z, Matrix4dc dest);

    /**
     * Apply the rotation - and possibly scaling - transformation of the given {@link IQuaterniond} to this matrix while
     * using <tt>(ox, oy, oz)</tt> as the rotation origin, and store the result in <code>dest</code>.
     * <p>
     * When used with a right-handed coordinate system, the produced rotation will rotate a vector counter-clockwise
     * around the rotation axis, when viewing along the negative axis direction towards the origin. When used with a
     * left-handed coordinate system, the rotation is clockwise.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>Q</code> the rotation matrix obtained from the given
     * quaternion, then the new matrix will be <code>M * Q</code>. So when transforming a vector <code>v</code> with the
     * new matrix by using <code>M * Q * v</code>, the quaternion rotation will be applied first!
     * <p>
     * This method is equivalent to calling: <tt>translate(ox, oy, oz, dest).rotate(quat).translate(-ox, -oy, -oz)</tt>
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Quaternion">http://en.wikipedia.org</a>
     *
     * @param quat the {@link IQuaterniond}
     * @param ox   the x coordinate of the rotation origin
     * @param oy   the y coordinate of the rotation origin
     * @param oz   the z coordinate of the rotation origin
     * @param dest will hold the result
     * @return dest
     */
    Matrix4dc rotateAround(IQuaterniond quat, double ox, double oy, double oz, Matrix4dc dest);

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
    Matrix4dc rotateLocal(double ang, double x, double y, double z, Matrix4dc dest);

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
    Matrix4dc rotateLocalX(double ang, Matrix4dc dest);

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
    Matrix4dc rotateLocalY(double ang, Matrix4dc dest);

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
    Matrix4dc rotateLocalZ(double ang, Matrix4dc dest);

    /**
     * Pre-multiply the rotation - and possibly scaling - transformation of the given {@link IQuaterniond} to this
     * matrix while using <tt>(ox, oy, oz)</tt> as the rotation origin, and store the result in <code>dest</code>.
     * <p>
     * When used with a right-handed coordinate system, the produced rotation will rotate a vector counter-clockwise
     * around the rotation axis, when viewing along the negative axis direction towards the origin. When used with a
     * left-handed coordinate system, the rotation is clockwise.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>Q</code> the rotation matrix obtained from the given
     * quaternion, then the new matrix will be <code>Q * M</code>. So when transforming a vector <code>v</code> with the
     * new matrix by using <code>Q * M * v</code>, the quaternion rotation will be applied last!
     * <p>
     * This method is equivalent to calling: <tt>translateLocal(-ox, -oy, -oz, dest).rotateLocal(quat).translateLocal(ox,
     * oy, oz)</tt>
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Quaternion">http://en.wikipedia.org</a>
     *
     * @param quat the {@link IQuaterniond}
     * @param ox   the x coordinate of the rotation origin
     * @param oy   the y coordinate of the rotation origin
     * @param oz   the z coordinate of the rotation origin
     * @param dest will hold the result
     * @return dest
     */
    Matrix4dc rotateAroundLocal(IQuaterniond quat, double ox, double oy, double oz, Matrix4dc dest);

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
    Matrix4dc translate(IVector3d offset, Matrix4dc dest);

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
    Matrix4dc translate(IVector3f offset, Matrix4dc dest);

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
    Matrix4dc translate(double x, double y, double z, Matrix4dc dest);

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
    Matrix4dc translateLocal(IVector3f offset, Matrix4dc dest);

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
    Matrix4dc translateLocal(IVector3d offset, Matrix4dc dest);

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
    Matrix4dc translateLocal(double x, double y, double z, Matrix4dc dest);

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
    Matrix4dc rotateX(double ang, Matrix4dc dest);

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
    Matrix4dc rotateY(double ang, Matrix4dc dest);

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
    Matrix4dc rotateZ(double ang, Matrix4dc dest);

    /**
     * Apply rotation about the Z axis to align the local <tt>+X</tt> towards <tt>(dirX, dirY)</tt> and store the result
     * in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the rotation matrix, then the new matrix will be
     * <code>M * R</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>M * R *
     * v</code>, the rotation will be applied first!
     * <p>
     * The vector <tt>(dirX, dirY)</tt> must be a unit vector.
     *
     * @param dirX the x component of the normalized direction
     * @param dirY the y component of the normalized direction
     * @param dest will hold the result
     * @return this
     */
    Matrix4dc rotateTowardsXY(double dirX, double dirY, Matrix4dc dest);

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
    Matrix4dc rotateXYZ(double angleX, double angleY, double angleZ, Matrix4dc dest);

    /**
     * Apply rotation of <code>angleX</code> radians about the X axis, followed by a rotation of <code>angleY</code>
     * radians about the Y axis and followed by a rotation of <code>angleZ</code> radians about the Z axis and store the
     * result in <code>dest</code>.
     * <p>
     * When used with a right-handed coordinate system, the produced rotation will rotate a vector counter-clockwise
     * around the rotation axis, when viewing along the negative axis direction towards the origin. When used with a
     * left-handed coordinate system, the rotation is clockwise.
     * <p>
     * This method assumes that <code>this</code> matrix represents an {@link #isAffine() affine} transformation (i.e.
     * its last row is equal to <tt>(0, 0, 0, 1)</tt>) and can be used to speed up matrix multiplication if the matrix
     * only represents affine transformations, such as translation, rotation, scaling and shearing (in any
     * combination).
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the rotation matrix, then the new matrix will be
     * <code>M * R</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>M * R *
     * v</code>, the rotation will be applied first!
     *
     * @param angleX the angle to rotate about X
     * @param angleY the angle to rotate about Y
     * @param angleZ the angle to rotate about Z
     * @param dest   will hold the result
     * @return dest
     */
    Matrix4dc rotateAffineXYZ(double angleX, double angleY, double angleZ, Matrix4dc dest);

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
    Matrix4dc rotateZYX(double angleZ, double angleY, double angleX, Matrix4dc dest);

    /**
     * Apply rotation of <code>angleZ</code> radians about the Z axis, followed by a rotation of <code>angleY</code>
     * radians about the Y axis and followed by a rotation of <code>angleX</code> radians about the X axis and store the
     * result in <code>dest</code>.
     * <p>
     * When used with a right-handed coordinate system, the produced rotation will rotate a vector counter-clockwise
     * around the rotation axis, when viewing along the negative axis direction towards the origin. When used with a
     * left-handed coordinate system, the rotation is clockwise.
     * <p>
     * This method assumes that <code>this</code> matrix represents an {@link #isAffine() affine} transformation (i.e.
     * its last row is equal to <tt>(0, 0, 0, 1)</tt>) and can be used to speed up matrix multiplication if the matrix
     * only represents affine transformations, such as translation, rotation, scaling and shearing (in any
     * combination).
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the rotation matrix, then the new matrix will be
     * <code>M * R</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>M * R *
     * v</code>, the rotation will be applied first!
     *
     * @param angleZ the angle to rotate about Z
     * @param angleY the angle to rotate about Y
     * @param angleX the angle to rotate about X
     * @param dest   will hold the result
     * @return dest
     */
    Matrix4dc rotateAffineZYX(double angleZ, double angleY, double angleX, Matrix4dc dest);

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
    Matrix4dc rotateYXZ(double angleY, double angleX, double angleZ, Matrix4dc dest);

    /**
     * Apply rotation of <code>angleY</code> radians about the Y axis, followed by a rotation of <code>angleX</code>
     * radians about the X axis and followed by a rotation of <code>angleZ</code> radians about the Z axis and store the
     * result in <code>dest</code>.
     * <p>
     * When used with a right-handed coordinate system, the produced rotation will rotate a vector counter-clockwise
     * around the rotation axis, when viewing along the negative axis direction towards the origin. When used with a
     * left-handed coordinate system, the rotation is clockwise.
     * <p>
     * This method assumes that <code>this</code> matrix represents an {@link #isAffine() affine} transformation (i.e.
     * its last row is equal to <tt>(0, 0, 0, 1)</tt>) and can be used to speed up matrix multiplication if the matrix
     * only represents affine transformations, such as translation, rotation, scaling and shearing (in any
     * combination).
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the rotation matrix, then the new matrix will be
     * <code>M * R</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>M * R *
     * v</code>, the rotation will be applied first!
     *
     * @param angleY the angle to rotate about Y
     * @param angleX the angle to rotate about X
     * @param angleZ the angle to rotate about Z
     * @param dest   will hold the result
     * @return dest
     */
    Matrix4dc rotateAffineYXZ(double angleY, double angleX, double angleZ, Matrix4dc dest);

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
    Matrix4dc rotate(IQuaterniond quat, Matrix4dc dest);

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
    Matrix4dc rotate(IQuaternionf quat, Matrix4dc dest);

    /**
     * Apply the rotation - and possibly scaling - transformation of the given {@link IQuaterniond} to this {@link
     * #isAffine() affine} matrix and store the result in <code>dest</code>.
     * <p>
     * This method assumes <code>this</code> to be {@link #isAffine() affine}.
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
    Matrix4dc rotateAffine(IQuaterniond quat, Matrix4dc dest);

    /**
     * Apply the rotation - and possibly scaling - transformation of the given {@link IQuaterniond} to this matrix,
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
     * @param quat the {@link IQuaterniond}
     * @param dest will hold the result
     * @return dest
     */
    Matrix4dc rotateTranslation(IQuaterniond quat, Matrix4dc dest);

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
    Matrix4dc rotateTranslation(IQuaternionf quat, Matrix4dc dest);

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
    Matrix4dc rotateLocal(IQuaterniond quat, Matrix4dc dest);

    /**
     * Apply the rotation - and possibly scaling - transformation of the given {@link IQuaternionf} to this {@link
     * #isAffine() affine} matrix and store the result in <code>dest</code>.
     * <p>
     * This method assumes <code>this</code> to be {@link #isAffine() affine}.
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
    Matrix4dc rotateAffine(IQuaternionf quat, Matrix4dc dest);

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
    Matrix4dc rotateLocal(IQuaternionf quat, Matrix4dc dest);

    /**
     * Apply a rotation transformation, rotating about the given {@link AxisAngle4fc} and store the result in
     * <code>dest</code>.
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
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Axis_and_angle">http://en.wikipedia.org</a>
     *
     * @param axisAngle the {@link AxisAngle4fc} (needs to be {@link AxisAngle4fc#normalize() normalized})
     * @param dest      will hold the result
     * @return dest
     *
     * @see #rotate(double, double, double, double, Matrix4dc)
     */
    Matrix4dc rotate(AxisAngle4fc axisAngle, Matrix4dc dest);

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
     * @see #rotate(double, double, double, double, Matrix4dc)
     */
    Matrix4dc rotate(AxisAngle4dc axisAngle, Matrix4dc dest);

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
     * @param axis  the rotation axis (needs to be {@link Vector3dc#normalize() normalized})
     * @param dest  will hold the result
     * @return dest
     *
     * @see #rotate(double, double, double, double, Matrix4dc)
     */
    Matrix4dc rotate(double angle, IVector3d axis, Matrix4dc dest);

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
     * @see #rotate(double, double, double, double, Matrix4dc)
     */
    Matrix4dc rotate(double angle, IVector3f axis, Matrix4dc dest);

    /**
     * Get the row at the given <code>row</code> index, starting with <code>0</code>.
     *
     * @param row  the row index in <tt>[0..3]</tt>
     * @param dest will hold the row components
     * @return the passed in destination
     */
    Vector4dc getRow(int row, Vector4dc dest);

    /**
     * Get the column at the given <code>column</code> index, starting with <code>0</code>.
     *
     * @param column the column index in <tt>[0..3]</tt>
     * @param dest   will hold the column components
     * @return the passed in destination
     */
    Vector4dc getColumn(int column, Vector4dc dest);

    /**
     * Compute a normal matrix from the upper left 3x3 submatrix of <code>this</code> and store it into the upper left
     * 3x3 submatrix of <code>dest</code>. All other values of <code>dest</code> will be set to identity.
     * <p>
     * The normal matrix of <tt>m</tt> is the transpose of the inverse of <tt>m</tt>.
     *
     * @param dest will hold the result
     * @return dest
     */
    Matrix4dc normal(Matrix4dc dest);

    /**
     * Compute a normal matrix from the upper left 3x3 submatrix of <code>this</code> and store it into
     * <code>dest</code>.
     * <p>
     * The normal matrix of <tt>m</tt> is the transpose of the inverse of <tt>m</tt>.
     *
     * @param dest will hold the result
     * @return dest
     *
     * @see #get3x3(Matrix3dc)
     */
    Matrix3dc normal(Matrix3dc dest);

    /**
     * Normalize the upper left 3x3 submatrix of this matrix and store the result in <code>dest</code>.
     * <p>
     * The resulting matrix will map unit vectors to unit vectors, though a pair of orthogonal input unit vectors need
     * not be mapped to a pair of orthogonal output vectors if the original matrix was not orthogonal itself (i.e. had
     * <i>skewing</i>).
     *
     * @param dest will hold the result
     * @return dest
     */
    Matrix4dc normalize3x3(Matrix4dc dest);

    /**
     * Normalize the upper left 3x3 submatrix of this matrix and store the result in <code>dest</code>.
     * <p>
     * The resulting matrix will map unit vectors to unit vectors, though a pair of orthogonal input unit vectors need
     * not be mapped to a pair of orthogonal output vectors if the original matrix was not orthogonal itself (i.e. had
     * <i>skewing</i>).
     *
     * @param dest will hold the result
     * @return dest
     */
    Matrix3dc normalize3x3(Matrix3dc dest);

    /**
     * Unproject the given window coordinates <tt>(winX, winY, winZ)</tt> by <code>this</code> matrix using the
     * specified viewport.
     * <p>
     * This method first converts the given window coordinates to normalized device coordinates in the range
     * <tt>[-1..1]</tt> and then transforms those NDC coordinates by the inverse of <code>this</code> matrix.
     * <p>
     * The depth range of <tt>winZ</tt> is assumed to be <tt>[0..1]</tt>, which is also the OpenGL default.
     * <p>
     * As a necessary computation step for unprojecting, this method computes the inverse of <code>this</code> matrix.
     * In order to avoid computing the matrix inverse with every invocation, the inverse of <code>this</code> matrix can
     * be built once outside using {@link #invert(Matrix4dc)} and then the method {@link #unprojectInv(double, double,
     * double, int[], Vector4dc) unprojectInv()} can be invoked on it.
     *
     * @param winX     the x-coordinate in window coordinates (pixels)
     * @param winY     the y-coordinate in window coordinates (pixels)
     * @param winZ     the z-coordinate, which is the depth value in <tt>[0..1]</tt>
     * @param viewport the viewport described by <tt>[x, y, width, height]</tt>
     * @param dest     will hold the unprojected position
     * @return dest
     *
     * @see #unprojectInv(double, double, double, int[], Vector4dc)
     * @see #invert(Matrix4dc)
     */
    Vector4dc unproject(double winX, double winY, double winZ, int[] viewport, Vector4dc dest);

    /**
     * Unproject the given window coordinates <tt>(winX, winY, winZ)</tt> by <code>this</code> matrix using the
     * specified viewport.
     * <p>
     * This method first converts the given window coordinates to normalized device coordinates in the range
     * <tt>[-1..1]</tt> and then transforms those NDC coordinates by the inverse of <code>this</code> matrix.
     * <p>
     * The depth range of <tt>winZ</tt> is assumed to be <tt>[0..1]</tt>, which is also the OpenGL default.
     * <p>
     * As a necessary computation step for unprojecting, this method computes the inverse of <code>this</code> matrix.
     * In order to avoid computing the matrix inverse with every invocation, the inverse of <code>this</code> matrix can
     * be built once outside using {@link #invert(Matrix4dc)} and then the method {@link #unprojectInv(double, double,
     * double, int[], Vector3dc) unprojectInv()} can be invoked on it.
     *
     * @param winX     the x-coordinate in window coordinates (pixels)
     * @param winY     the y-coordinate in window coordinates (pixels)
     * @param winZ     the z-coordinate, which is the depth value in <tt>[0..1]</tt>
     * @param viewport the viewport described by <tt>[x, y, width, height]</tt>
     * @param dest     will hold the unprojected position
     * @return dest
     *
     * @see #unprojectInv(double, double, double, int[], Vector3dc)
     * @see #invert(Matrix4dc)
     */
    Vector3dc unproject(double winX, double winY, double winZ, int[] viewport, Vector3dc dest);

    /**
     * Unproject the given window coordinates <code>winCoords</code> by <code>this</code> matrix using the specified
     * viewport.
     * <p>
     * This method first converts the given window coordinates to normalized device coordinates in the range
     * <tt>[-1..1]</tt> and then transforms those NDC coordinates by the inverse of <code>this</code> matrix.
     * <p>
     * The depth range of <tt>winCoords.z</tt> is assumed to be <tt>[0..1]</tt>, which is also the OpenGL default.
     * <p>
     * As a necessary computation step for unprojecting, this method computes the inverse of <code>this</code> matrix.
     * In order to avoid computing the matrix inverse with every invocation, the inverse of <code>this</code> matrix can
     * be built once outside using {@link #invert(Matrix4dc)} and then the method {@link #unprojectInv(double, double,
     * double, int[], Vector4dc) unprojectInv()} can be invoked on it.
     *
     * @param winCoords the window coordinates to unproject
     * @param viewport  the viewport described by <tt>[x, y, width, height]</tt>
     * @param dest      will hold the unprojected position
     * @return dest
     *
     * @see #unprojectInv(double, double, double, int[], Vector4dc)
     * @see #unproject(double, double, double, int[], Vector4dc)
     * @see #invert(Matrix4dc)
     */
    Vector4dc unproject(IVector3d winCoords, int[] viewport, Vector4dc dest);

    /**
     * Unproject the given window coordinates <code>winCoords</code> by <code>this</code> matrix using the specified
     * viewport.
     * <p>
     * This method first converts the given window coordinates to normalized device coordinates in the range
     * <tt>[-1..1]</tt> and then transforms those NDC coordinates by the inverse of <code>this</code> matrix.
     * <p>
     * The depth range of <tt>winCoords.z</tt> is assumed to be <tt>[0..1]</tt>, which is also the OpenGL default.
     * <p>
     * As a necessary computation step for unprojecting, this method computes the inverse of <code>this</code> matrix.
     * In order to avoid computing the matrix inverse with every invocation, the inverse of <code>this</code> matrix can
     * be built once outside using {@link #invert(Matrix4dc)} and then the method {@link #unprojectInv(double, double,
     * double, int[], Vector4dc) unprojectInv()} can be invoked on it.
     *
     * @param winCoords the window coordinates to unproject
     * @param viewport  the viewport described by <tt>[x, y, width, height]</tt>
     * @param dest      will hold the unprojected position
     * @return dest
     *
     * @see #unprojectInv(double, double, double, int[], Vector4dc)
     * @see #unproject(double, double, double, int[], Vector4dc)
     * @see #invert(Matrix4dc)
     */
    Vector3dc unproject(IVector3d winCoords, int[] viewport, Vector3dc dest);

    /**
     * Unproject the given 2D window coordinates <tt>(winX, winY)</tt> by <code>this</code> matrix using the specified
     * viewport and compute the origin and the direction of the resulting ray which starts at NDC <tt>z = -1.0</tt> and
     * goes through NDC <tt>z = +1.0</tt>.
     * <p>
     * This method first converts the given window coordinates to normalized device coordinates in the range
     * <tt>[-1..1]</tt> and then transforms those NDC coordinates by the inverse of <code>this</code> matrix.
     * <p>
     * As a necessary computation step for unprojecting, this method computes the inverse of <code>this</code> matrix.
     * In order to avoid computing the matrix inverse with every invocation, the inverse of <code>this</code> matrix can
     * be built once outside using {@link #invert(Matrix4dc)} and then the method {@link #unprojectInvRay(double,
     * double, int[], Vector3dc, Vector3dc) unprojectInvRay()} can be invoked on it.
     *
     * @param winX       the x-coordinate in window coordinates (pixels)
     * @param winY       the y-coordinate in window coordinates (pixels)
     * @param viewport   the viewport described by <tt>[x, y, width, height]</tt>
     * @param originDest will hold the ray origin
     * @param dirDest    will hold the (unnormalized) ray direction
     * @return this
     *
     * @see #unprojectInvRay(double, double, int[], Vector3dc, Vector3dc)
     * @see #invert(Matrix4dc)
     */
    Matrix4dc unprojectRay(double winX, double winY, int[] viewport, Vector3dc originDest, Vector3dc dirDest);

    /**
     * Unproject the given 2D window coordinates <code>winCoords</code> by <code>this</code> matrix using the specified
     * viewport and compute the origin and the direction of the resulting ray which starts at NDC <tt>z = -1.0</tt> and
     * goes through NDC <tt>z = +1.0</tt>.
     * <p>
     * This method first converts the given window coordinates to normalized device coordinates in the range
     * <tt>[-1..1]</tt> and then transforms those NDC coordinates by the inverse of <code>this</code> matrix.
     * <p>
     * As a necessary computation step for unprojecting, this method computes the inverse of <code>this</code> matrix.
     * In order to avoid computing the matrix inverse with every invocation, the inverse of <code>this</code> matrix can
     * be built once outside using {@link #invert(Matrix4dc)} and then the method {@link #unprojectInvRay(double,
     * double, int[], Vector3dc, Vector3dc) unprojectInvRay()} can be invoked on it.
     *
     * @param winCoords  the window coordinates to unproject
     * @param viewport   the viewport described by <tt>[x, y, width, height]</tt>
     * @param originDest will hold the ray origin
     * @param dirDest    will hold the (unnormalized) ray direction
     * @return this
     *
     * @see #unprojectInvRay(double, double, int[], Vector3dc, Vector3dc)
     * @see #unprojectRay(double, double, int[], Vector3dc, Vector3dc)
     * @see #invert(Matrix4dc)
     */
    Matrix4dc unprojectRay(IVector2d winCoords, int[] viewport, Vector3dc originDest, Vector3dc dirDest);

    /**
     * Unproject the given window coordinates <code>winCoords</code> by <code>this</code> matrix using the specified
     * viewport.
     * <p>
     * This method differs from {@link #unproject(IVector3d, int[], Vector4dc) unproject()} in that it assumes that
     * <code>this</code> is already the inverse matrix of the original projection matrix. It exists to avoid recomputing
     * the matrix inverse with every invocation.
     * <p>
     * This method first converts the given window coordinates to normalized device coordinates in the range
     * <tt>[-1..1]</tt> and then transforms those NDC coordinates by <code>this</code> matrix.
     * <p>
     * The depth range of <tt>winCoords.z</tt> is assumed to be <tt>[0..1]</tt>, which is also the OpenGL default.
     *
     * @param winCoords the window coordinates to unproject
     * @param viewport  the viewport described by <tt>[x, y, width, height]</tt>
     * @param dest      will hold the unprojected position
     * @return dest
     *
     * @see #unproject(IVector3d, int[], Vector4dc)
     */
    Vector4dc unprojectInv(IVector3d winCoords, int[] viewport, Vector4dc dest);

    /**
     * Unproject the given window coordinates <tt>(winX, winY, winZ)</tt> by <code>this</code> matrix using the
     * specified viewport.
     * <p>
     * This method differs from {@link #unproject(double, double, double, int[], Vector4dc) unproject()} in that it
     * assumes that <code>this</code> is already the inverse matrix of the original projection matrix. It exists to
     * avoid recomputing the matrix inverse with every invocation.
     * <p>
     * This method first converts the given window coordinates to normalized device coordinates in the range
     * <tt>[-1..1]</tt> and then transforms those NDC coordinates by <code>this</code> matrix.
     * <p>
     * The depth range of <tt>winZ</tt> is assumed to be <tt>[0..1]</tt>, which is also the OpenGL default.
     *
     * @param winX     the x-coordinate in window coordinates (pixels)
     * @param winY     the y-coordinate in window coordinates (pixels)
     * @param winZ     the z-coordinate, which is the depth value in <tt>[0..1]</tt>
     * @param viewport the viewport described by <tt>[x, y, width, height]</tt>
     * @param dest     will hold the unprojected position
     * @return dest
     *
     * @see #unproject(double, double, double, int[], Vector4dc)
     */
    Vector4dc unprojectInv(double winX, double winY, double winZ, int[] viewport, Vector4dc dest);

    /**
     * Unproject the given window coordinates <code>winCoords</code> by <code>this</code> matrix using the specified
     * viewport.
     * <p>
     * This method differs from {@link #unproject(IVector3d, int[], Vector3dc) unproject()} in that it assumes that
     * <code>this</code> is already the inverse matrix of the original projection matrix. It exists to avoid recomputing
     * the matrix inverse with every invocation.
     * <p>
     * This method first converts the given window coordinates to normalized device coordinates in the range
     * <tt>[-1..1]</tt> and then transforms those NDC coordinates by <code>this</code> matrix.
     * <p>
     * The depth range of <tt>winCoords.z</tt> is assumed to be <tt>[0..1]</tt>, which is also the OpenGL default.
     *
     * @param winCoords the window coordinates to unproject
     * @param viewport  the viewport described by <tt>[x, y, width, height]</tt>
     * @param dest      will hold the unprojected position
     * @return dest
     *
     * @see #unproject(IVector3d, int[], Vector3dc)
     */
    Vector3dc unprojectInv(IVector3d winCoords, int[] viewport, Vector3dc dest);

    /**
     * Unproject the given window coordinates <tt>(winX, winY, winZ)</tt> by <code>this</code> matrix using the
     * specified viewport.
     * <p>
     * This method differs from {@link #unproject(double, double, double, int[], Vector3dc) unproject()} in that it
     * assumes that <code>this</code> is already the inverse matrix of the original projection matrix. It exists to
     * avoid recomputing the matrix inverse with every invocation.
     * <p>
     * This method first converts the given window coordinates to normalized device coordinates in the range
     * <tt>[-1..1]</tt> and then transforms those NDC coordinates by <code>this</code> matrix.
     * <p>
     * The depth range of <tt>winZ</tt> is assumed to be <tt>[0..1]</tt>, which is also the OpenGL default.
     *
     * @param winX     the x-coordinate in window coordinates (pixels)
     * @param winY     the y-coordinate in window coordinates (pixels)
     * @param winZ     the z-coordinate, which is the depth value in <tt>[0..1]</tt>
     * @param viewport the viewport described by <tt>[x, y, width, height]</tt>
     * @param dest     will hold the unprojected position
     * @return dest
     *
     * @see #unproject(double, double, double, int[], Vector3dc)
     */
    Vector3dc unprojectInv(double winX, double winY, double winZ, int[] viewport, Vector3dc dest);

    /**
     * Unproject the given window coordinates <code>winCoords</code> by <code>this</code> matrix using the specified
     * viewport and compute the origin and the direction of the resulting ray which starts at NDC <tt>z = -1.0</tt> and
     * goes through NDC <tt>z = +1.0</tt>.
     * <p>
     * This method differs from {@link #unprojectRay(IVector2d, int[], Vector3dc, Vector3dc) unprojectRay()} in that it
     * assumes that <code>this</code> is already the inverse matrix of the original projection matrix. It exists to
     * avoid recomputing the matrix inverse with every invocation.
     *
     * @param winCoords  the window coordinates to unproject
     * @param viewport   the viewport described by <tt>[x, y, width, height]</tt>
     * @param originDest will hold the ray origin
     * @param dirDest    will hold the (unnormalized) ray direction
     * @return this
     *
     * @see #unprojectRay(IVector2d, int[], Vector3dc, Vector3dc)
     */
    Matrix4dc unprojectInvRay(IVector2d winCoords, int[] viewport, Vector3dc originDest, Vector3dc dirDest);

    /**
     * Unproject the given 2D window coordinates <tt>(winX, winY)</tt> by <code>this</code> matrix using the specified
     * viewport and compute the origin and the direction of the resulting ray which starts at NDC <tt>z = -1.0</tt> and
     * goes through NDC <tt>z = +1.0</tt>.
     * <p>
     * This method differs from {@link #unprojectRay(double, double, int[], Vector3dc, Vector3dc) unprojectRay()} in
     * that it assumes that <code>this</code> is already the inverse matrix of the original projection matrix. It exists
     * to avoid recomputing the matrix inverse with every invocation.
     *
     * @param winX       the x-coordinate in window coordinates (pixels)
     * @param winY       the y-coordinate in window coordinates (pixels)
     * @param viewport   the viewport described by <tt>[x, y, width, height]</tt>
     * @param originDest will hold the ray origin
     * @param dirDest    will hold the (unnormalized) ray direction
     * @return this
     *
     * @see #unprojectRay(double, double, int[], Vector3dc, Vector3dc)
     */
    Matrix4dc unprojectInvRay(double winX, double winY, int[] viewport, Vector3dc originDest, Vector3dc dirDest);

    /**
     * Project the given <tt>(x, y, z)</tt> position via <code>this</code> matrix using the specified viewport and store
     * the resulting window coordinates in <code>winCoordsDest</code>.
     * <p>
     * This method transforms the given coordinates by <code>this</code> matrix including perspective division to obtain
     * normalized device coordinates, and then translates these into window coordinates by using the given
     * <code>viewport</code> settings <tt>[x, y, width, height]</tt>.
     * <p>
     * The depth range of the returned <code>winCoordsDest.z</code> will be <tt>[0..1]</tt>, which is also the OpenGL
     * default.
     *
     * @param x             the x-coordinate of the position to project
     * @param y             the y-coordinate of the position to project
     * @param z             the z-coordinate of the position to project
     * @param viewport      the viewport described by <tt>[x, y, width, height]</tt>
     * @param winCoordsDest will hold the projected window coordinates
     * @return winCoordsDest
     */
    Vector4dc project(double x, double y, double z, int[] viewport, Vector4dc winCoordsDest);

    /**
     * Project the given <tt>(x, y, z)</tt> position via <code>this</code> matrix using the specified viewport and store
     * the resulting window coordinates in <code>winCoordsDest</code>.
     * <p>
     * This method transforms the given coordinates by <code>this</code> matrix including perspective division to obtain
     * normalized device coordinates, and then translates these into window coordinates by using the given
     * <code>viewport</code> settings <tt>[x, y, width, height]</tt>.
     * <p>
     * The depth range of the returned <code>winCoordsDest.z</code> will be <tt>[0..1]</tt>, which is also the OpenGL
     * default.
     *
     * @param x             the x-coordinate of the position to project
     * @param y             the y-coordinate of the position to project
     * @param z             the z-coordinate of the position to project
     * @param viewport      the viewport described by <tt>[x, y, width, height]</tt>
     * @param winCoordsDest will hold the projected window coordinates
     * @return winCoordsDest
     */
    Vector3dc project(double x, double y, double z, int[] viewport, Vector3dc winCoordsDest);

    /**
     * Project the given <code>position</code> via <code>this</code> matrix using the specified viewport and store the
     * resulting window coordinates in <code>winCoordsDest</code>.
     * <p>
     * This method transforms the given coordinates by <code>this</code> matrix including perspective division to obtain
     * normalized device coordinates, and then translates these into window coordinates by using the given
     * <code>viewport</code> settings <tt>[x, y, width, height]</tt>.
     * <p>
     * The depth range of the returned <code>winCoordsDest.z</code> will be <tt>[0..1]</tt>, which is also the OpenGL
     * default.
     *
     * @param position      the position to project into window coordinates
     * @param viewport      the viewport described by <tt>[x, y, width, height]</tt>
     * @param winCoordsDest will hold the projected window coordinates
     * @return winCoordsDest
     *
     * @see #project(double, double, double, int[], Vector4dc)
     */
    Vector4dc project(IVector3d position, int[] viewport, Vector4dc winCoordsDest);

    /**
     * Project the given <code>position</code> via <code>this</code> matrix using the specified viewport and store the
     * resulting window coordinates in <code>winCoordsDest</code>.
     * <p>
     * This method transforms the given coordinates by <code>this</code> matrix including perspective division to obtain
     * normalized device coordinates, and then translates these into window coordinates by using the given
     * <code>viewport</code> settings <tt>[x, y, width, height]</tt>.
     * <p>
     * The depth range of the returned <code>winCoordsDest.z</code> will be <tt>[0..1]</tt>, which is also the OpenGL
     * default.
     *
     * @param position      the position to project into window coordinates
     * @param viewport      the viewport described by <tt>[x, y, width, height]</tt>
     * @param winCoordsDest will hold the projected window coordinates
     * @return winCoordsDest
     *
     * @see #project(double, double, double, int[], Vector4dc)
     */
    Vector3dc project(IVector3d position, int[] viewport, Vector3dc winCoordsDest);

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
    Matrix4dc reflect(double a, double b, double c, double d, Matrix4dc dest);

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
    Matrix4dc reflect(double nx, double ny, double nz, double px, double py, double pz, Matrix4dc dest);

    /**
     * Apply a mirror/reflection transformation to this matrix that reflects about a plane specified via the plane
     * orientation and a point on the plane, and store the result in <code>dest</code>.
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
     * @param orientation the plane orientation
     * @param point       a point on the plane
     * @param dest        will hold the result
     * @return dest
     */
    Matrix4dc reflect(IQuaterniond orientation, IVector3d point, Matrix4dc dest);

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
    Matrix4dc reflect(IVector3d normal, IVector3d point, Matrix4dc dest);

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
    Matrix4dc ortho(double left, double right, double bottom, double top, double zNear, double zFar, boolean zZeroToOne, Matrix4dc dest);

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
    Matrix4dc ortho(double left, double right, double bottom, double top, double zNear, double zFar, Matrix4dc dest);

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
    Matrix4dc orthoLH(double left, double right, double bottom, double top, double zNear, double zFar, boolean zZeroToOne, Matrix4dc dest);

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
    Matrix4dc orthoLH(double left, double right, double bottom, double top, double zNear, double zFar, Matrix4dc dest);

    /**
     * Apply a symmetric orthographic projection transformation for a right-handed coordinate system using the given NDC
     * z range to this matrix and store the result in <code>dest</code>.
     * <p>
     * This method is equivalent to calling {@link #ortho(double, double, double, double, double, double, boolean,
     * Matrix4dc) ortho()} with <code>left=-width/2</code>, <code>right=+width/2</code>, <code>bottom=-height/2</code>
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
    Matrix4dc orthoSymmetric(double width, double height, double zNear, double zFar, boolean zZeroToOne, Matrix4dc dest);

    /**
     * Apply a symmetric orthographic projection transformation for a right-handed coordinate system using OpenGL's NDC
     * z range of <tt>[-1..+1]</tt> to this matrix and store the result in <code>dest</code>.
     * <p>
     * This method is equivalent to calling {@link #ortho(double, double, double, double, double, double, Matrix4dc)
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
    Matrix4dc orthoSymmetric(double width, double height, double zNear, double zFar, Matrix4dc dest);

    /**
     * Apply a symmetric orthographic projection transformation for a left-handed coordinate system using the given NDC
     * z range to this matrix and store the result in <code>dest</code>.
     * <p>
     * This method is equivalent to calling {@link #orthoLH(double, double, double, double, double, double, boolean,
     * Matrix4dc) orthoLH()} with <code>left=-width/2</code>, <code>right=+width/2</code>, <code>bottom=-height/2</code>
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
    Matrix4dc orthoSymmetricLH(double width, double height, double zNear, double zFar, boolean zZeroToOne, Matrix4dc dest);

    /**
     * Apply a symmetric orthographic projection transformation for a left-handed coordinate system using OpenGL's NDC z
     * range of <tt>[-1..+1]</tt> to this matrix and store the result in <code>dest</code>.
     * <p>
     * This method is equivalent to calling {@link #orthoLH(double, double, double, double, double, double, Matrix4dc)
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
    Matrix4dc orthoSymmetricLH(double width, double height, double zNear, double zFar, Matrix4dc dest);

    /**
     * Apply an orthographic projection transformation for a right-handed coordinate system to this matrix and store the
     * result in <code>dest</code>.
     * <p>
     * This method is equivalent to calling {@link #ortho(double, double, double, double, double, double, Matrix4dc)
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
     * @see #ortho(double, double, double, double, double, double, Matrix4dc)
     */
    Matrix4dc ortho2D(double left, double right, double bottom, double top, Matrix4dc dest);

    /**
     * Apply an orthographic projection transformation for a left-handed coordinate system to this matrix and store the
     * result in <code>dest</code>.
     * <p>
     * This method is equivalent to calling {@link #orthoLH(double, double, double, double, double, double, Matrix4dc)
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
     * @see #orthoLH(double, double, double, double, double, double, Matrix4dc)
     */
    Matrix4dc ortho2DLH(double left, double right, double bottom, double top, Matrix4dc dest);

    /**
     * Apply a rotation transformation to this matrix to make <code>-z</code> point along <code>dir</code> and store the
     * result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>L</code> the lookalong rotation matrix, then the new
     * matrix will be <code>M * L</code>. So when transforming a vector <code>v</code> with the new matrix by using
     * <code>M * L * v</code>, the lookalong rotation transformation will be applied first!
     * <p>
     * This is equivalent to calling {@link #lookAt(IVector3d, IVector3d, IVector3d, Matrix4dc) lookAt} with <code>eye =
     * (0, 0, 0)</code> and <code>center = dir</code>.
     *
     * @param dir  the direction in space to look along
     * @param up   the direction of 'up'
     * @param dest will hold the result
     * @return dest
     *
     * @see #lookAlong(double, double, double, double, double, double, Matrix4dc)
     * @see #lookAt(IVector3d, IVector3d, IVector3d, Matrix4dc)
     */
    Matrix4dc lookAlong(IVector3d dir, IVector3d up, Matrix4dc dest);

    /**
     * Apply a rotation transformation to this matrix to make <code>-z</code> point along <code>dir</code> and store the
     * result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>L</code> the lookalong rotation matrix, then the new
     * matrix will be <code>M * L</code>. So when transforming a vector <code>v</code> with the new matrix by using
     * <code>M * L * v</code>, the lookalong rotation transformation will be applied first!
     * <p>
     * This is equivalent to calling {@link #lookAt(double, double, double, double, double, double, double, double,
     * double, Matrix4dc) lookAt()} with <code>eye = (0, 0, 0)</code> and <code>center = dir</code>.
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
     * @see #lookAt(double, double, double, double, double, double, double, double, double, Matrix4dc)
     */
    Matrix4dc lookAlong(double dirX, double dirY, double dirZ, double upX, double upY, double upZ, Matrix4dc dest);

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
     * @see #lookAt(double, double, double, double, double, double, double, double, double, Matrix4dc)
     */
    Matrix4dc lookAt(IVector3d eye, IVector3d center, IVector3d up, Matrix4dc dest);

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
     * @see #lookAt(IVector3d, IVector3d, IVector3d, Matrix4dc)
     */
    Matrix4dc lookAt(double eyeX, double eyeY, double eyeZ, double centerX, double centerY, double centerZ, double upX, double upY, double upZ, Matrix4dc dest);

    /**
     * Apply a "lookat" transformation to this matrix for a right-handed coordinate system, that aligns <code>-z</code>
     * with <code>center - eye</code> and store the result in <code>dest</code>.
     * <p>
     * This method assumes <code>this</code> to be a perspective transformation, obtained via {@link #frustum(double,
     * double, double, double, double, double, Matrix4dc) frustum()} or {@link #perspective(double, double, double,
     * double, Matrix4dc) perspective()} or one of their overloads.
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
     */
    Matrix4dc lookAtPerspective(double eyeX, double eyeY, double eyeZ, double centerX, double centerY, double centerZ, double upX, double upY, double upZ, Matrix4dc dest);

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
     */
    Matrix4dc lookAtLH(IVector3d eye, IVector3d center, IVector3d up, Matrix4dc dest);

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
     * @see #lookAtLH(IVector3d, IVector3d, IVector3d, Matrix4dc)
     */
    Matrix4dc lookAtLH(double eyeX, double eyeY, double eyeZ, double centerX, double centerY, double centerZ, double upX, double upY, double upZ, Matrix4dc dest);

    /**
     * Apply a "lookat" transformation to this matrix for a left-handed coordinate system, that aligns <code>+z</code>
     * with <code>center - eye</code> and store the result in <code>dest</code>.
     * <p>
     * This method assumes <code>this</code> to be a perspective transformation, obtained via {@link #frustumLH(double,
     * double, double, double, double, double, Matrix4dc) frustumLH()} or {@link #perspectiveLH(double, double, double,
     * double, Matrix4dc) perspectiveLH()} or one of their overloads.
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
     */
    Matrix4dc lookAtPerspectiveLH(double eyeX, double eyeY, double eyeZ, double centerX, double centerY, double centerZ, double upX, double upY, double upZ, Matrix4dc dest);

    /**
     * Apply a symmetric perspective projection frustum transformation for a right-handed coordinate system using the
     * given NDC z range to this matrix and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>P</code> the perspective projection matrix, then the new
     * matrix will be <code>M * P</code>. So when transforming a vector <code>v</code> with the new matrix by using
     * <code>M * P * v</code>, the perspective projection will be applied first!
     *
     * @param fovy       the vertical field of view in radians (must be greater than zero and less than {@link Math#PI
     *                   PI})
     * @param aspect     the aspect ratio (i.e. width / height; must be greater than zero)
     * @param zNear      near clipping plane distance. If the special value {@link Double#POSITIVE_INFINITY} is used,
     *                   the near clipping plane will be at positive infinity. In that case, <code>zFar</code> may not
     *                   also be {@link Double#POSITIVE_INFINITY}.
     * @param zFar       far clipping plane distance. If the special value {@link Double#POSITIVE_INFINITY} is used, the
     *                   far clipping plane will be at positive infinity. In that case, <code>zNear</code> may not also
     *                   be {@link Double#POSITIVE_INFINITY}.
     * @param dest       will hold the result
     * @param zZeroToOne whether to use Vulkan's and Direct3D's NDC z range of <tt>[0..+1]</tt> when <code>true</code>
     *                   or whether to use OpenGL's NDC z range of <tt>[-1..+1]</tt> when <code>false</code>
     * @return dest
     */
    Matrix4dc perspective(double fovy, double aspect, double zNear, double zFar, boolean zZeroToOne, Matrix4dc dest);

    /**
     * Apply a symmetric perspective projection frustum transformation for a right-handed coordinate system using
     * OpenGL's NDC z range of <tt>[-1..+1]</tt> to this matrix and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>P</code> the perspective projection matrix, then the new
     * matrix will be <code>M * P</code>. So when transforming a vector <code>v</code> with the new matrix by using
     * <code>M * P * v</code>, the perspective projection will be applied first!
     *
     * @param fovy   the vertical field of view in radians (must be greater than zero and less than {@link Math#PI PI})
     * @param aspect the aspect ratio (i.e. width / height; must be greater than zero)
     * @param zNear  near clipping plane distance. If the special value {@link Double#POSITIVE_INFINITY} is used, the
     *               near clipping plane will be at positive infinity. In that case, <code>zFar</code> may not also be
     *               {@link Double#POSITIVE_INFINITY}.
     * @param zFar   far clipping plane distance. If the special value {@link Double#POSITIVE_INFINITY} is used, the far
     *               clipping plane will be at positive infinity. In that case, <code>zNear</code> may not also be
     *               {@link Double#POSITIVE_INFINITY}.
     * @param dest   will hold the result
     * @return dest
     */
    Matrix4dc perspective(double fovy, double aspect, double zNear, double zFar, Matrix4dc dest);

    /**
     * Apply a symmetric perspective projection frustum transformation for a left-handed coordinate system using the
     * given NDC z range to this matrix and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>P</code> the perspective projection matrix, then the new
     * matrix will be <code>M * P</code>. So when transforming a vector <code>v</code> with the new matrix by using
     * <code>M * P * v</code>, the perspective projection will be applied first!
     *
     * @param fovy       the vertical field of view in radians (must be greater than zero and less than {@link Math#PI
     *                   PI})
     * @param aspect     the aspect ratio (i.e. width / height; must be greater than zero)
     * @param zNear      near clipping plane distance. If the special value {@link Double#POSITIVE_INFINITY} is used,
     *                   the near clipping plane will be at positive infinity. In that case, <code>zFar</code> may not
     *                   also be {@link Double#POSITIVE_INFINITY}.
     * @param zFar       far clipping plane distance. If the special value {@link Double#POSITIVE_INFINITY} is used, the
     *                   far clipping plane will be at positive infinity. In that case, <code>zNear</code> may not also
     *                   be {@link Double#POSITIVE_INFINITY}.
     * @param zZeroToOne whether to use Vulkan's and Direct3D's NDC z range of <tt>[0..+1]</tt> when <code>true</code>
     *                   or whether to use OpenGL's NDC z range of <tt>[-1..+1]</tt> when <code>false</code>
     * @param dest       will hold the result
     * @return dest
     */
    Matrix4dc perspectiveLH(double fovy, double aspect, double zNear, double zFar, boolean zZeroToOne, Matrix4dc dest);

    /**
     * Apply a symmetric perspective projection frustum transformation for a left-handed coordinate system using
     * OpenGL's NDC z range of <tt>[-1..+1]</tt> to this matrix and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>P</code> the perspective projection matrix, then the new
     * matrix will be <code>M * P</code>. So when transforming a vector <code>v</code> with the new matrix by using
     * <code>M * P * v</code>, the perspective projection will be applied first!
     *
     * @param fovy   the vertical field of view in radians (must be greater than zero and less than {@link Math#PI PI})
     * @param aspect the aspect ratio (i.e. width / height; must be greater than zero)
     * @param zNear  near clipping plane distance. If the special value {@link Double#POSITIVE_INFINITY} is used, the
     *               near clipping plane will be at positive infinity. In that case, <code>zFar</code> may not also be
     *               {@link Double#POSITIVE_INFINITY}.
     * @param zFar   far clipping plane distance. If the special value {@link Double#POSITIVE_INFINITY} is used, the far
     *               clipping plane will be at positive infinity. In that case, <code>zNear</code> may not also be
     *               {@link Double#POSITIVE_INFINITY}.
     * @param dest   will hold the result
     * @return dest
     */
    Matrix4dc perspectiveLH(double fovy, double aspect, double zNear, double zFar, Matrix4dc dest);

    /**
     * Apply an arbitrary perspective projection frustum transformation for a right-handed coordinate system using the
     * given NDC z range to this matrix and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>F</code> the frustum matrix, then the new matrix will be
     * <code>M * F</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>M * F *
     * v</code>, the frustum transformation will be applied first!
     * <p>
     * Reference: <a href="http://www.songho.ca/opengl/gl_projectionmatrix.html#perspective">http://www.songho.ca</a>
     *
     * @param left       the distance along the x-axis to the left frustum edge
     * @param right      the distance along the x-axis to the right frustum edge
     * @param bottom     the distance along the y-axis to the bottom frustum edge
     * @param top        the distance along the y-axis to the top frustum edge
     * @param zNear      near clipping plane distance. If the special value {@link Double#POSITIVE_INFINITY} is used,
     *                   the near clipping plane will be at positive infinity. In that case, <code>zFar</code> may not
     *                   also be {@link Double#POSITIVE_INFINITY}.
     * @param zFar       far clipping plane distance. If the special value {@link Double#POSITIVE_INFINITY} is used, the
     *                   far clipping plane will be at positive infinity. In that case, <code>zNear</code> may not also
     *                   be {@link Double#POSITIVE_INFINITY}.
     * @param zZeroToOne whether to use Vulkan's and Direct3D's NDC z range of <tt>[0..+1]</tt> when <code>true</code>
     *                   or whether to use OpenGL's NDC z range of <tt>[-1..+1]</tt> when <code>false</code>
     * @param dest       will hold the result
     * @return dest
     */
    Matrix4dc frustum(double left, double right, double bottom, double top, double zNear, double zFar, boolean zZeroToOne, Matrix4dc dest);

    /**
     * Apply an arbitrary perspective projection frustum transformation for a right-handed coordinate system using
     * OpenGL's NDC z range of <tt>[-1..+1]</tt> to this matrix and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>F</code> the frustum matrix, then the new matrix will be
     * <code>M * F</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>M * F *
     * v</code>, the frustum transformation will be applied first!
     * <p>
     * Reference: <a href="http://www.songho.ca/opengl/gl_projectionmatrix.html#perspective">http://www.songho.ca</a>
     *
     * @param left   the distance along the x-axis to the left frustum edge
     * @param right  the distance along the x-axis to the right frustum edge
     * @param bottom the distance along the y-axis to the bottom frustum edge
     * @param top    the distance along the y-axis to the top frustum edge
     * @param zNear  near clipping plane distance. If the special value {@link Double#POSITIVE_INFINITY} is used, the
     *               near clipping plane will be at positive infinity. In that case, <code>zFar</code> may not also be
     *               {@link Double#POSITIVE_INFINITY}.
     * @param zFar   far clipping plane distance. If the special value {@link Double#POSITIVE_INFINITY} is used, the far
     *               clipping plane will be at positive infinity. In that case, <code>zNear</code> may not also be
     *               {@link Double#POSITIVE_INFINITY}.
     * @param dest   will hold the result
     * @return dest
     */
    Matrix4dc frustum(double left, double right, double bottom, double top, double zNear, double zFar, Matrix4dc dest);

    /**
     * Apply an arbitrary perspective projection frustum transformation for a left-handed coordinate system using the
     * given NDC z range to this matrix and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>F</code> the frustum matrix, then the new matrix will be
     * <code>M * F</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>M * F *
     * v</code>, the frustum transformation will be applied first!
     * <p>
     * Reference: <a href="http://www.songho.ca/opengl/gl_projectionmatrix.html#perspective">http://www.songho.ca</a>
     *
     * @param left       the distance along the x-axis to the left frustum edge
     * @param right      the distance along the x-axis to the right frustum edge
     * @param bottom     the distance along the y-axis to the bottom frustum edge
     * @param top        the distance along the y-axis to the top frustum edge
     * @param zNear      near clipping plane distance. If the special value {@link Double#POSITIVE_INFINITY} is used,
     *                   the near clipping plane will be at positive infinity. In that case, <code>zFar</code> may not
     *                   also be {@link Double#POSITIVE_INFINITY}.
     * @param zFar       far clipping plane distance. If the special value {@link Double#POSITIVE_INFINITY} is used, the
     *                   far clipping plane will be at positive infinity. In that case, <code>zNear</code> may not also
     *                   be {@link Double#POSITIVE_INFINITY}.
     * @param zZeroToOne whether to use Vulkan's and Direct3D's NDC z range of <tt>[0..+1]</tt> when <code>true</code>
     *                   or whether to use OpenGL's NDC z range of <tt>[-1..+1]</tt> when <code>false</code>
     * @param dest       will hold the result
     * @return dest
     */
    Matrix4dc frustumLH(double left, double right, double bottom, double top, double zNear, double zFar, boolean zZeroToOne, Matrix4dc dest);

    /**
     * Apply an arbitrary perspective projection frustum transformation for a left-handed coordinate system using
     * OpenGL's NDC z range of <tt>[-1..+1]</tt> to this matrix and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>F</code> the frustum matrix, then the new matrix will be
     * <code>M * F</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>M * F *
     * v</code>, the frustum transformation will be applied first!
     * <p>
     * Reference: <a href="http://www.songho.ca/opengl/gl_projectionmatrix.html#perspective">http://www.songho.ca</a>
     *
     * @param left   the distance along the x-axis to the left frustum edge
     * @param right  the distance along the x-axis to the right frustum edge
     * @param bottom the distance along the y-axis to the bottom frustum edge
     * @param top    the distance along the y-axis to the top frustum edge
     * @param zNear  near clipping plane distance. If the special value {@link Double#POSITIVE_INFINITY} is used, the
     *               near clipping plane will be at positive infinity. In that case, <code>zFar</code> may not also be
     *               {@link Double#POSITIVE_INFINITY}.
     * @param zFar   far clipping plane distance. If the special value {@link Double#POSITIVE_INFINITY} is used, the far
     *               clipping plane will be at positive infinity. In that case, <code>zNear</code> may not also be
     *               {@link Double#POSITIVE_INFINITY}.
     * @param dest   will hold the result
     * @return dest
     */
    Matrix4dc frustumLH(double left, double right, double bottom, double top, double zNear, double zFar, Matrix4dc dest);

    /**
     * Calculate a frustum plane of <code>this</code> matrix, which can be a projection matrix or a combined
     * modelview-projection matrix, and store the result in the given <code>planeEquation</code>.
     * <p>
     * Generally, this method computes the frustum plane in the local frame of any coordinate system that existed before
     * <code>this</code> transformation was applied to it in order to yield homogeneous clipping space.
     * <p>
     * The frustum plane will be given in the form of a general plane equation: <tt>a*x + b*y + c*z + d = 0</tt>, where
     * the given {@link Vector4dc} components will hold the <tt>(a, b, c, d)</tt> values of the equation.
     * <p>
     * The plane normal, which is <tt>(a, b, c)</tt>, is directed "inwards" of the frustum. Any plane/point test using
     * <tt>a*x + b*y + c*z + d</tt> therefore will yield a result greater than zero if the point is within the frustum
     * (i.e. at the <i>positive</i> side of the frustum plane).
     * <p>
     * For performing frustum culling, a more optimized function should be used instead of manually
     * obtaining the frustum planes and testing them against points, spheres or axis-aligned boxes.
     * <p>
     * Reference: <a href="http://gamedevs.org/uploads/fast-extraction-viewing-frustum-planes-from-world-view-projection-matrix.pdf">
     * Fast Extraction of Viewing Frustum Planes from the World-View-Projection Matrix</a>
     *
     * @param plane         one of the six possible planes, given as numeric constants {@link #PLANE_NX}, {@link
     *                      #PLANE_PX}, {@link #PLANE_NY}, {@link #PLANE_PY}, {@link #PLANE_NZ} and {@link #PLANE_PZ}
     * @param planeEquation will hold the computed plane equation. The plane equation will be normalized, meaning that
     *                      <tt>(a, b, c)</tt> will be a unit vector
     * @return planeEquation
     */
    Vector4dc frustumPlane(int plane, Vector4dc planeEquation);

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
     * For performing frustum culling, a more optimized function should be used instead of manually
     * obtaining the frustum planes and testing them against points, spheres or axis-aligned boxes.
     * <p>
     * Reference: <a href="http://gamedevs.org/uploads/fast-extraction-viewing-frustum-planes-from-world-view-projection-matrix.pdf">
     * Fast Extraction of Viewing Frustum Planes from the World-View-Projection Matrix</a>
     *
     * @param plane         one of the six possible planes, given as numeric constants {@link #PLANE_NX}, {@link
     *                      #PLANE_PX}, {@link #PLANE_NY}, {@link #PLANE_PY}, {@link #PLANE_NZ} and {@link #PLANE_PZ}
     * @param planeEquation will hold the computed plane equation. The plane equation will be normalized, meaning that
     *                      <tt>(a, b, c)</tt> will be a unit vector
     * @return planeEquation
     */
    Planedc frustumPlane(int plane, Planedc planeEquation);

    /**
     * Compute the corner coordinates of the frustum defined by <code>this</code> matrix, which can be a projection
     * matrix or a combined modelview-projection matrix, and store the result in the given <code>point</code>.
     * <p>
     * Generally, this method computes the frustum corners in the local frame of any coordinate system that existed
     * before <code>this</code> transformation was applied to it in order to yield homogeneous clipping space.
     * <p>
     * Reference: <a href="http://geomalgorithms.com/a05-_intersect-1.html">http://geomalgorithms.com</a>
     * <p>
     * Reference: <a href="http://gamedevs.org/uploads/fast-extraction-viewing-frustum-planes-from-world-view-projection-matrix.pdf">
     * Fast Extraction of Viewing Frustum Planes from the World-View-Projection Matrix</a>
     *
     * @param corner one of the eight possible corners, given as numeric constants {@link #CORNER_NXNYNZ}, {@link
     *               #CORNER_PXNYNZ}, {@link #CORNER_PXPYNZ}, {@link #CORNER_NXPYNZ}, {@link #CORNER_PXNYPZ}, {@link
     *               #CORNER_NXNYPZ}, {@link #CORNER_NXPYPZ}, {@link #CORNER_PXPYPZ}
     * @param point  will hold the resulting corner point coordinates
     * @return point
     */
    Vector3dc frustumCorner(int corner, Vector3dc point);

    /**
     * Compute the eye/origin of the perspective frustum transformation defined by <code>this</code> matrix, which can
     * be a projection matrix or a combined modelview-projection matrix, and store the result in the given
     * <code>origin</code>.
     * <p>
     * Note that this method will only work using perspective projections obtained via one of the perspective methods,
     * such as {@link #perspective(double, double, double, double, Matrix4dc) perspective()} or {@link #frustum(double,
     * double, double, double, double, double, Matrix4dc) frustum()}.
     * <p>
     * Generally, this method computes the origin in the local frame of any coordinate system that existed before
     * <code>this</code> transformation was applied to it in order to yield homogeneous clipping space.
     * <p>
     * Reference: <a href="http://geomalgorithms.com/a05-_intersect-1.html">http://geomalgorithms.com</a>
     * <p>
     * Reference: <a href="http://gamedevs.org/uploads/fast-extraction-viewing-frustum-planes-from-world-view-projection-matrix.pdf">
     * Fast Extraction of Viewing Frustum Planes from the World-View-Projection Matrix</a>
     *
     * @param origin will hold the origin of the coordinate system before applying <code>this</code> perspective
     *               projection transformation
     * @return origin
     */
    Vector3dc perspectiveOrigin(Vector3dc origin);

    /**
     * Return the vertical field-of-view angle in radians of this perspective transformation matrix.
     * <p>
     * Note that this method will only work using perspective projections obtained via one of the perspective methods,
     * such as {@link #perspective(double, double, double, double, Matrix4dc) perspective()} or {@link #frustum(double,
     * double, double, double, double, double, Matrix4dc) frustum()}.
     * <p>
     * For orthogonal transformations this method will return <tt>0.0</tt>.
     * <p>
     * Reference: <a href="http://gamedevs.org/uploads/fast-extraction-viewing-frustum-planes-from-world-view-projection-matrix.pdf">
     * Fast Extraction of Viewing Frustum Planes from the World-View-Projection Matrix</a>
     *
     * @return the vertical field-of-view angle in radians
     */
    double perspectiveFov();

    /**
     * Extract the near clip plane distance from <code>this</code> perspective projection matrix.
     * <p>
     * This method only works if <code>this</code> is a perspective projection matrix, for example obtained via {@link
     * #perspective(double, double, double, double, Matrix4dc)}.
     *
     * @return the near clip plane distance
     */
    double perspectiveNear();

    /**
     * Extract the far clip plane distance from <code>this</code> perspective projection matrix.
     * <p>
     * This method only works if <code>this</code> is a perspective projection matrix, for example obtained via {@link
     * #perspective(double, double, double, double, Matrix4dc)}.
     *
     * @return the far clip plane distance
     */
    double perspectiveFar();

    /**
     * Obtain the direction of a ray starting at the center of the coordinate system and going through the near frustum
     * plane.
     * <p>
     * This method computes the <code>dir</code> vector in the local frame of any coordinate system that existed before
     * <code>this</code> transformation was applied to it in order to yield homogeneous clipping space.
     * <p>
     * The parameters <code>x</code> and <code>y</code> are used to interpolate the generated ray direction from the
     * bottom-left to the top-right frustum corners.
     * <p>
     * For optimal efficiency when building many ray directions over the whole frustum, it is recommended to use this
     * method only in order to compute the four corner rays at <tt>(0, 0)</tt>, <tt>(1, 0)</tt>, <tt>(0, 1)</tt> and
     * <tt>(1, 1)</tt> and then bilinearly interpolating between them; or to use the {@link FrustumRayBuilder}.
     * <p>
     * Reference: <a href="http://gamedevs.org/uploads/fast-extraction-viewing-frustum-planes-from-world-view-projection-matrix.pdf">
     * Fast Extraction of Viewing Frustum Planes from the World-View-Projection Matrix</a>
     *
     * @param x   the interpolation factor along the left-to-right frustum planes, within <tt>[0..1]</tt>
     * @param y   the interpolation factor along the bottom-to-top frustum planes, within <tt>[0..1]</tt>
     * @param dir will hold the normalized ray direction in the local frame of the coordinate system before transforming
     *            to homogeneous clipping space using <code>this</code> matrix
     * @return dir
     */
    Vector3dc frustumRayDir(double x, double y, Vector3dc dir);

    /**
     * Obtain the direction of <tt>+Z</tt> before the transformation represented by <code>this</code> matrix is
     * applied.
     * <p>
     * This method uses the rotation component of the upper left 3x3 submatrix to obtain the direction that is
     * transformed to <tt>+Z</tt> by <code>this</code> matrix.
     * <p>
     * This method is equivalent to the following code:
     * <pre>
     * Matrix4dc inv = new Matrix4dc(this).invert();
     * inv.transformDirection(dir.set(0, 0, 1)).normalize();
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
     * This method uses the rotation component of the upper left 3x3 submatrix to obtain the direction that is
     * transformed to <tt>+Z</tt> by <code>this</code> matrix.
     * <p>
     * This method is equivalent to the following code:
     * <pre>
     * Matrix4dc inv = new Matrix4dc(this).transpose();
     * inv.transformDirection(dir.set(0, 0, 1));
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
     * This method uses the rotation component of the upper left 3x3 submatrix to obtain the direction that is
     * transformed to <tt>+X</tt> by <code>this</code> matrix.
     * <p>
     * This method is equivalent to the following code:
     * <pre>
     * Matrix4dc inv = new Matrix4dc(this).invert();
     * inv.transformDirection(dir.set(1, 0, 0)).normalize();
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
     * This method uses the rotation component of the upper left 3x3 submatrix to obtain the direction that is
     * transformed to <tt>+X</tt> by <code>this</code> matrix.
     * <p>
     * This method is equivalent to the following code:
     * <pre>
     * Matrix4dc inv = new Matrix4dc(this).transpose();
     * inv.transformDirection(dir.set(1, 0, 0));
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
     * This method uses the rotation component of the upper left 3x3 submatrix to obtain the direction that is
     * transformed to <tt>+Y</tt> by <code>this</code> matrix.
     * <p>
     * This method is equivalent to the following code:
     * <pre>
     * Matrix4dc inv = new Matrix4dc(this).invert();
     * inv.transformDirection(dir.set(0, 1, 0)).normalize();
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
     * This method uses the rotation component of the upper left 3x3 submatrix to obtain the direction that is
     * transformed to <tt>+Y</tt> by <code>this</code> matrix.
     * <p>
     * This method is equivalent to the following code:
     * <pre>
     * Matrix4dc inv = new Matrix4dc(this).transpose();
     * inv.transformDirection(dir.set(0, 1, 0));
     * </pre>
     * <p>
     * Reference: <a href="http://www.euclideanspace.com/maths/algebra/matrix/functions/inverse/threeD/">http://www.euclideanspace.com</a>
     *
     * @param dir will hold the direction of <tt>+Y</tt>
     * @return dir
     */
    Vector3dc normalizedPositiveY(Vector3dc dir);

    /**
     * Obtain the position that gets transformed to the origin by <code>this</code> {@link #isAffine() affine} matrix.
     * This can be used to get the position of the "camera" from a given <i>view</i> transformation matrix.
     * <p>
     * This method only works with {@link #isAffine() affine} matrices.
     * <p>
     * This method is equivalent to the following code:
     * <pre>
     * Matrix4fc inv = new Matrix4fc(this).invertAffine();
     * inv.transformPosition(origin.set(0, 0, 0));
     * </pre>
     *
     * @param origin will hold the position transformed to the origin
     * @return origin
     */
    Vector3dc originAffine(Vector3dc origin);

    /**
     * Obtain the position that gets transformed to the origin by <code>this</code> matrix. This can be used to get the
     * position of the "camera" from a given <i>view/projection</i> transformation matrix.
     * <p>
     * This method is equivalent to the following code:
     * <pre>
     * Matrix4fc inv = new Matrix4fc(this).invert();
     * inv.transformPosition(origin.set(0, 0, 0));
     * </pre>
     *
     * @param origin will hold the position transformed to the origin
     * @return origin
     */
    Vector3dc origin(Vector3dc origin);

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
    Matrix4dc shadow(IVector4d light, double a, double b, double c, double d, Matrix4dc dest);

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
    Matrix4dc shadow(double lightX, double lightY, double lightZ, double lightW, double a, double b, double c, double d, Matrix4dc dest);

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
    Matrix4dc shadow(IVector4d light, IMatrix4d planeTransform, Matrix4dc dest);

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
    Matrix4dc shadow(double lightX, double lightY, double lightZ, double lightW, IMatrix4d planeTransform, Matrix4dc dest);

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
    Matrix4dc pick(double x, double y, double width, double height, int[] viewport, Matrix4dc dest);

    /**
     * Determine whether this matrix describes an affine transformation. This is the case iff its last row is equal to
     * <tt>(0, 0, 0, 1)</tt>.
     *
     * @return <code>true</code> iff this matrix is affine; <code>false</code> otherwise
     */
    boolean isAffine();

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
    Matrix4dc arcball(double radius, double centerX, double centerY, double centerZ, double angleX, double angleY, Matrix4dc dest);

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
    Matrix4dc arcball(double radius, IVector3d center, double angleX, double angleY, Matrix4dc dest);

    /**
     * Compute the <i>range matrix</i> for the Projected Grid transformation as described in chapter "2.4.2 Creating the
     * range conversion matrix" of the paper <a href="http://fileadmin.cs.lth.se/graphics/theses/projects/projgrid/projgrid-lq.pdf">Real-time
     * water rendering - Introducing the projected grid concept</a> based on the <i>inverse</i> of the view-projection
     * matrix which is assumed to be <code>this</code>, and store that range matrix into <code>dest</code>.
     * <p>
     * If the projected grid will not be visible then this method returns <code>null</code>.
     * <p>
     * This method uses the <tt>y = 0</tt> plane for the projection.
     *
     * @param projector the projector view-projection transformation
     * @param sLower    the lower (smallest) Y-coordinate which any transformed vertex might have while still being
     *                  visible on the projected grid
     * @param sUpper    the upper (highest) Y-coordinate which any transformed vertex might have while still being
     *                  visible on the projected grid
     * @param dest      will hold the resulting range matrix
     * @return the computed range matrix; or <code>null</code> if the projected grid will not be visible
     */
    Matrix4dc projectedGridRange(IMatrix4d projector, double sLower, double sUpper, Matrix4dc dest);

    /**
     * Change the near and far clip plane distances of <code>this</code> perspective frustum transformation matrix and
     * store the result in <code>dest</code>.
     * <p>
     * This method only works if <code>this</code> is a perspective projection frustum transformation, for example
     * obtained via {@link #perspective(double, double, double, double, Matrix4dc) perspective()} or {@link
     * #frustum(double, double, double, double, double, double, Matrix4dc) frustum()}.
     *
     * @param near the new near clip plane distance
     * @param far  the new far clip plane distance
     * @param dest will hold the resulting matrix
     * @return dest
     *
     * @see #perspective(double, double, double, double, Matrix4dc)
     * @see #frustum(double, double, double, double, double, double, Matrix4dc)
     */
    Matrix4dc perspectiveFrustumSlice(double near, double far, Matrix4dc dest);

    /**
     * Build an ortographic projection transformation that fits the view-projection transformation represented by
     * <code>this</code> into the given affine <code>view</code> transformation.
     * <p>
     * The transformation represented by <code>this</code> must be given as the {@link #invert(Matrix4dc) inverse} of a
     * typical combined camera view-projection transformation, whose projection can be either orthographic or
     * perspective.
     * <p>
     * The <code>view</code> must be an {@link #isAffine() affine} transformation which in the application of Cascaded
     * Shadow Maps is usually the light view transformation. It be obtained via any affine transformation or for example
     * via {@link #lookAt(double, double, double, double, double, double, double, double, double, Matrix4dc) lookAt()}.
     * <p>
     * Reference: <a href="http://developer.download.nvidia.com/SDK/10.5/opengl/screenshots/samples/cascaded_shadow_maps.html">OpenGL
     * SDK - Cascaded Shadow Maps</a>
     *
     * @param view the view transformation to build a corresponding orthographic projection to fit the frustum of
     *             <code>this</code>
     * @param dest will hold the crop projection transformation
     * @return dest
     */
    Matrix4dc orthoCrop(IMatrix4d view, Matrix4dc dest);

    /**
     * Transform the axis-aligned box given as the minimum corner <tt>(minX, minY, minZ)</tt> and maximum corner
     * <tt>(maxX, maxY, maxZ)</tt> by <code>this</code> {@link #isAffine() affine} matrix and compute the axis-aligned
     * box of the result whose minimum corner is stored in <code>outMin</code> and maximum corner stored in
     * <code>outMax</code>.
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
    Matrix4dc transformAab(double minX, double minY, double minZ, double maxX, double maxY, double maxZ, Vector3dc outMin, Vector3dc outMax);

    /**
     * Transform the axis-aligned box given as the minimum corner <code>min</code> and maximum corner <code>max</code>
     * by <code>this</code> {@link #isAffine() affine} matrix and compute the axis-aligned box of the result whose
     * minimum corner is stored in <code>outMin</code> and maximum corner stored in <code>outMax</code>.
     *
     * @param min    the minimum corner of the axis-aligned box
     * @param max    the maximum corner of the axis-aligned box
     * @param outMin will hold the minimum corner of the resulting axis-aligned box
     * @param outMax will hold the maximum corner of the resulting axis-aligned box
     * @return this
     */
    Matrix4dc transformAab(IVector3d min, IVector3d max, Vector3dc outMin, Vector3dc outMax);

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
    Matrix4dc lerp(IMatrix4d other, double t, Matrix4dc dest);

    /**
     * Apply a model transformation to this matrix for a right-handed coordinate system, that aligns the local
     * <code>+Z</code> axis with <code>direction</code> and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>L</code> the lookat matrix, then the new matrix will be
     * <code>M * L</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>M * L *
     * v</code>, the lookat transformation will be applied first!
     * <p>
     * This method is equivalent to calling: <tt>mulAffine(new Matrix4d().lookAt(new IVector3d(), new
     * IVector3d(dir).negate(), up).invertAffine(), dest)</tt>
     *
     * @param direction the direction to rotate towards
     * @param up        the up vector
     * @param dest      will hold the result
     * @return dest
     *
     * @see #rotateTowards(double, double, double, double, double, double, Matrix4dc)
     */
    Matrix4dc rotateTowards(IVector3d direction, IVector3d up, Matrix4dc dest);

    /**
     * Apply a model transformation to this matrix for a right-handed coordinate system, that aligns the local
     * <code>+Z</code> axis with <code>dir</code> and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>L</code> the lookat matrix, then the new matrix will be
     * <code>M * L</code>. So when transforming a vector <code>v</code> with the new matrix by using <code>M * L *
     * v</code>, the lookat transformation will be applied first!
     * <p>
     * This method is equivalent to calling: <tt>mulAffine(new Matrix4d().lookAt(0, 0, 0, -dirX, -dirY, -dirZ, upX, upY,
     * upZ).invertAffine(), dest)</tt>
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
     * @see #rotateTowards(IVector3d, IVector3d, Matrix4dc)
     */
    Matrix4dc rotateTowards(double dirX, double dirY, double dirZ, double upX, double upY, double upZ, Matrix4dc dest);

    /**
     * Extract the Euler angles from the rotation represented by the upper left 3x3 submatrix of <code>this</code> and
     * store the extracted Euler angles in <code>dest</code>.
     * <p>
     * This method assumes that the upper left of <code>this</code> only represents a rotation without scaling.
     * <p>
     * Note that the returned Euler angles must be applied in the order <tt>Z * Y * X</tt> to obtain the identical
     * matrix. This means that calling {@link IMatrix4d#rotateZYX(double, double, double, Matrix4dc)} using the obtained
     * Euler angles will yield the same rotation as the original matrix from which the Euler angles were obtained, so in
     * the below code the matrix <tt>m2</tt> should be identical to <tt>m</tt> (disregarding possible floating-point
     * inaccuracies).
     * <pre>
     * Matrix4d m = ...; // &lt;- matrix only representing rotation
     * Matrix4dc n = new Matrix4dc();
     * n.rotateZYX(m.getEulerAnglesZYX(new IVector3d()));
     * </pre>
     * <p>
     * Reference: <a href="http://nghiaho.com/?page_id=846">http://nghiaho.com/</a>
     *
     * @param dest will hold the extracted Euler angles
     * @return dest
     */
    Vector3dc getEulerAnglesZYX(Vector3dc dest);

    /**
     * Test whether the given point <tt>(x, y, z)</tt> is within the frustum defined by <code>this</code> matrix.
     * <p>
     * This method assumes <code>this</code> matrix to be a transformation from any arbitrary coordinate system/space
     * <tt>M</tt> into standard OpenGL clip space and tests whether the given point with the coordinates <tt>(x, y,
     * z)</tt> given in space <tt>M</tt> is within the clip space.
     * <p>
     * When testing multiple points using the same transformation matrix.
     * <p>
     * Reference: <a href="http://gamedevs.org/uploads/fast-extraction-viewing-frustum-planes-from-world-view-projection-matrix.pdf">
     * Fast Extraction of Viewing Frustum Planes from the World-View-Projection Matrix</a>
     *
     * @param x the x-coordinate of the point
     * @param y the y-coordinate of the point
     * @param z the z-coordinate of the point
     * @return <code>true</code> if the given point is inside the frustum; <code>false</code> otherwise
     */
    boolean testPoint(double x, double y, double z);

    /**
     * Test whether the given sphere is partly or completely within or outside of the frustum defined by
     * <code>this</code> matrix.
     * <p>
     * This method assumes <code>this</code> matrix to be a transformation from any arbitrary coordinate system/space
     * <tt>M</tt> into standard OpenGL clip space and tests whether the given sphere with the coordinates <tt>(x, y,
     * z)</tt> given in space <tt>M</tt> is within the clip space.
     * <p>
     * When testing multiple spheres using the same transformation matrix, or more sophisticated/optimized intersection
     * algorithms are required.
     * <p>
     * The algorithm implemented by this method is conservative. This means that in certain circumstances a <i>false
     * positive</i> can occur, when the method returns <tt>true</tt> for spheres that are actually not visible. See <a
     * href="http://iquilezles.org/www/articles/frustumcorrect/frustumcorrect.htm">iquilezles.org</a> for an examination
     * of this problem.
     * <p>
     * Reference: <a href="http://gamedevs.org/uploads/fast-extraction-viewing-frustum-planes-from-world-view-projection-matrix.pdf">
     * Fast Extraction of Viewing Frustum Planes from the World-View-Projection Matrix</a>
     *
     * @param x the x-coordinate of the sphere's center
     * @param y the y-coordinate of the sphere's center
     * @param z the z-coordinate of the sphere's center
     * @param r the sphere's radius
     * @return <code>true</code> if the given sphere is partly or completely inside the frustum; <code>false</code>
     * otherwise
     */
    boolean testSphere(double x, double y, double z, double r);

    /**
     * Test whether the given axis-aligned box is partly or completely within or outside of the frustum defined by
     * <code>this</code> matrix. The box is specified via its min and max corner coordinates.
     * <p>
     * This method assumes <code>this</code> matrix to be a transformation from any arbitrary coordinate system/space
     * <tt>M</tt> into standard OpenGL clip space and tests whether the given axis-aligned box with its minimum corner
     * coordinates <tt>(minX, minY, minZ)</tt> and maximum corner coordinates <tt>(maxX, maxY, maxZ)</tt> given in space
     * <tt>M</tt> is within the clip space.
     * <p>
     * When testing multiple axis-aligned boxes using the same transformation matrix, or more sophisticated/optimized
     * intersection algorithms are required.
     * <p>
     * The algorithm implemented by this method is conservative. This means that in certain circumstances a <i>false
     * positive</i> can occur, when the method returns <tt>-1</tt> for boxes that are actually not visible/do not
     * intersect the frustum. See <a href="http://iquilezles.org/www/articles/frustumcorrect/frustumcorrect.htm">iquilezles.org</a>
     * for an examination of this problem.
     * <p>
     * Reference: <a href="http://old.cescg.org/CESCG-2002/DSykoraJJelinek/">Efficient View Frustum Culling</a> <br>
     * Reference: <a href="http://gamedevs.org/uploads/fast-extraction-viewing-frustum-planes-from-world-view-projection-matrix.pdf">
     * Fast Extraction of Viewing Frustum Planes from the World-View-Projection Matrix</a>
     *
     * @param minX the x-coordinate of the minimum corner
     * @param minY the y-coordinate of the minimum corner
     * @param minZ the z-coordinate of the minimum corner
     * @param maxX the x-coordinate of the maximum corner
     * @param maxY the y-coordinate of the maximum corner
     * @param maxZ the z-coordinate of the maximum corner
     * @return <code>true</code> if the axis-aligned box is completely or partly inside of the frustum;
     * <code>false</code> otherwise
     */
    boolean testAab(double minX, double minY, double minZ, double maxX, double maxY, double maxZ);
}