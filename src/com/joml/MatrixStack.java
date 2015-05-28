/*
 * (C) Copyright 2015 Kai Burjack
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software
 * and associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 *  1) The above copyright notice and this permission notice shall be included
 *     in all copies or substantial portions of the Software.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 */
package com.joml;

import java.nio.FloatBuffer;

/**
 * Resembles the matrix stack known from legacy OpenGL.
 * <p>
 * The operation names and semantics resemble those of the legacy OpenGL matrix
 * stack.
 * <p>
 * As with the OpenGL version there is no way to get a hold of any matrix
 * instance within the stack. You can only load the current matrix into a
 * user-supplied {@link Matrix4f} instance.
 * 
 * @author Kai Burjack
 */
public class MatrixStack {

    /**
     * The matrix stack as a non-growable array. The size of the stack must be
     * specified in the {@link #MatrixStack(int) constructor}.
     */
    private Matrix4f[] mats;

    /**
     * The index of the "current" matrix within {@link #mats}.
     */
    private int curr;

    /**
     * Create a new {@link MatrixStack} of the given size.
     * <p>
     * Initially the stack pointer is at zero and the current matrix is set to
     * identity.
     * 
     * @param stackSize
     *            the size of the stack. This must be at least 1
     */
    public MatrixStack(int stackSize) {
        if (stackSize < 1) {
            throw new IllegalArgumentException("stackSize must be >= 1");
        }
        mats = new Matrix4f[stackSize];
        mats[0] = new Matrix4f();
    }

    /**
     * Dispose of all but the first matrix in this stack and set the stack
     * counter to zero.
     * <p>
     * The first matrix will also be set to identity.
     */
    public void clear() {
        curr = 0;
        mats[0].identity();
    }

    /**
     * Static version of {@link #clear()}.
     * 
     * @see #clear()
     * 
     * @param stack
     *            the {@link MatrixStack} to apply the operation on
     */
    public static void clear(MatrixStack stack) {
        stack.clear();
    }

    /**
     * Load the given {@link Matrix4f} into the current matrix of the stack.
     * 
     * @param mat
     *            the matrix which is stored in the current stack matrix
     */
    public void loadMatrix(Matrix4f mat) {
        if (mat == null) {
            throw new IllegalArgumentException("mat must not be null");
        }
        mats[curr].set(mat);
    }

    /**
     * Static version of {@link #loadMatrix(Matrix4f)}.
     * 
     * @see #loadMatrix(Matrix4f)
     * 
     * @param mat
     *            the matrix which is stored in the current stack matrix
     * @param stack
     *            the {@link MatrixStack} to apply the operation on
     */
    public static void loadMatrix(Matrix4f mat, MatrixStack stack) {
        stack.loadMatrix(mat);
    }

    /**
     * Load the values of a column-major matrix from the given
     * {@link FloatBuffer} into the current matrix of the stack.
     * 
     * @param columnMajorArray
     *            the values of the 4x4 matrix as a column-major
     *            {@link FloatBuffer} containing at least 16 floats starting at
     *            the relative {@link FloatBuffer#position()}
     */
    public void loadMatrix(FloatBuffer columnMajorArray) {
        if (columnMajorArray == null) {
            throw new IllegalArgumentException(
                    "columnMajorArray must not be null");
        }
        mats[curr].set(columnMajorArray);
    }

    /**
     * Load the values of a column-major matrix from the given float array into
     * the current matrix of the stack.
     * 
     * @param columnMajorArray
     *            the values of the 4x4 matrix as a column-major float array of
     *            at least 16 floats
     * @param offset
     *            the offset into the array
     */
    public void loadMatrix(float[] columnMajorArray, int offset) {
        if (columnMajorArray == null) {
            throw new IllegalArgumentException(
                    "columnMajorArray must not be null");
        }
        mats[curr].set(columnMajorArray, offset);
    }

    /**
     * Increment the stack pointer by one and set the values of the new current
     * matrix to the one directly below it.
     */
    public void pushMatrix() {
        if (curr == mats.length - 1) {
            throw new IllegalStateException("max stack size of " + (curr + 1)
                    + " reached");
        }
        if (mats[curr + 1] == null) {
            mats[curr + 1] = new Matrix4f(mats[curr]);
        } else {
            mats[curr + 1].set(mats[curr]);
        }
        curr++;
    }

    /**
     * Static version of {@link #pushMatrix()}.
     * 
     * @see #pushMatrix()
     * 
     * @param stack
     *            the {@link MatrixStack} to apply the operation on
     */
    public static void pushMatrix(MatrixStack stack) {
        stack.pushMatrix();
    }

    /**
     * Decrement the stack pointer by one.
     * <p>
     * This will effectively dispose of the current matrix.
     */
    public void popMatrix() {
        if (curr == 0) {
            throw new IllegalStateException(
                    "already at the buttom of the stack");
        }
        curr--;
    }

    /**
     * Static version of {@link #popMatrix()}.
     * 
     * @see #popMatrix()
     * 
     * @param stack
     *            the {@link MatrixStack} to apply the operation on
     */
    public static void popMatrix(MatrixStack stack) {
        stack.popMatrix();
    }

    /**
     * Store the current matrix of the stack into the supplied <code>dest</code>
     * matrix.
     * 
     * @param dest
     *            the destination {@link Matrix4f} into which to store the
     *            current stack matrix
     * @return <code>dest</code>
     */
    public Matrix4f get(Matrix4f dest) {
        dest.set(mats[curr]);
        return dest;
    }

    /**
     * Store the column-major values of the current matrix of the stack into the
     * supplied {@link FloatBuffer}.
     * 
     * @param dest
     *            the destination {@link FloatBuffer} into which to store the
     *            column-major values of the current stack matrix
     * @return <code>dest</code>
     */
    public FloatBuffer get(FloatBuffer dest) {
        mats[curr].store(dest);
        return dest;
    }

    /**
     * Store the column-major values of the current matrix of the stack into the
     * supplied float array at the given offset.
     * 
     * @param dest
     *            the destination float array into which to store the
     *            column-major values of the current stack matrix
     * @param offset
     *            the array offset
     * @return <code>dest</code>
     */
    public float[] get(float[] dest, int offset) {
        mats[curr].store(dest, offset);
        return dest;
    }

    /**
     * Static version of {@link #get(Matrix4f)}.
     * 
     * @param dest
     *            the destination {@link Matrix4f} into which to store the
     *            current stack matrix
     * @param stack
     *            the {@link MatrixStack} to apply the operation on
     * @return <code>dest</code>
     */
    public static Matrix4f get(Matrix4f dest, MatrixStack stack) {
        return stack.get(dest);
    }

    /**
     * Get a reference to the current matrix of the stack directly without
     * copying its values to a user-supplied matrix like in
     * {@link #get(Matrix4f)}.
     * <p>
     * Note that any changes made to this matrix will also change the current
     * stack matrix!
     *
     * @return the current stack matrix
     */
    public Matrix4f getDirect() {
        return mats[curr];
    }

    /**
     * Static version of {@link #getDirect()}.
     * 
     * @param stack
     *            the {@link MatrixStack} to apply the operation on
     * @return the current stack matrix
     */
    public static Matrix4f getDirect(MatrixStack stack) {
        return stack.getDirect();
    }

    /**
     * Apply a translation to the current matrix by translating by the given
     * number of units in x, y and z.
     * <p>
     * If <code>C</code> is the current matrix and <code>T</code> the
     * translation matrix, then the new current matrix will be
     * <code>C * T</code>. So when transforming a vector <code>v</code> with the
     * new matrix by using <code>C * T * v</code>, the translation will be
     * applied first!
     * 
     * @param x
     * @param y
     * @param z
     */
    public void translate(float x, float y, float z) {
        Matrix4f c = mats[curr];
        // translation matrix elements:
        // m00, m11, m22, m33 = 1
        // m30 = x, m31 = y, m32 = z
        // all others = 0
        c.m30 = c.m00 * x + c.m10 * y + c.m20 * z + c.m30;
        c.m31 = c.m01 * x + c.m11 * y + c.m21 * z + c.m31;
        c.m32 = c.m02 * x + c.m12 * y + c.m22 * z + c.m32;
        c.m33 = c.m03 * x + c.m13 * y + c.m23 * z + c.m33;
    }

    /**
     * Apply a translation to the current matrix by translating by the number of
     * units in the given {@link Vector3f}.
     * 
     * @see #translate(float, float, float)
     * 
     * @param xyz
     *            contains the number of units to translate by
     */
    public void translate(Vector3f xyz) {
        translate(xyz.x, xyz.y, xyz.z);
    }

    /**
     * Static version of {@link #translate(float, float, float)} which applies
     * the transformation to the given {@link MatrixStack}.
     * 
     * @see #translate(float, float, float)
     * 
     * @param x
     *            the number of units on the x axis to translate by
     * @param y
     *            the number of units on the y axis to translate by
     * @param z
     *            the number of units on the z axis to translate by
     * @param stack
     *            the {@link MatrixStack} to apply the transformation on
     */
    public static void translate(float x, float y, float z, MatrixStack stack) {
        stack.translate(x, y, z);
    }

    /**
     * Static version of {@link #translate(Vector3f)} which applies the
     * transformation to the given {@link MatrixStack}.
     * 
     * @see #translate(Vector3f)
     * 
     * @param v
     *            the vector containing the number of units to translate by
     * @param stack
     *            the {@link MatrixStack} to apply the transformation on
     */
    public static void translate(Vector3f v, MatrixStack stack) {
        stack.translate(v);
    }

    /**
     * Apply scaling to the current matrix by scaling by the given x, y and z
     * factors.
     * <p>
     * If <code>C</code> is the current matrix and <code>S</code> the scaling
     * matrix, then the new current matrix will be <code>C * S</code>. So when
     * transforming a vector <code>v</code> with the new matrix by using
     * <code>C * S * v</code>, the scaling will be applied first!
     * 
     * @param x
     *            the factor of the x component
     * @param y
     *            the factor of the y component
     * @param z
     *            the factor of the z component
     */
    public void scale(float x, float y, float z) {
        Matrix4f c = mats[curr];
        // scale matrix elements:
        // m00 = x, m11 = y, m22 = z
        // m33 = 1
        // all others = 0
        c.m00 = c.m00 * x;
        c.m01 = c.m01 * x;
        c.m02 = c.m02 * x;
        c.m03 = c.m03 * x;
        c.m10 = c.m10 * y;
        c.m11 = c.m11 * y;
        c.m12 = c.m12 * y;
        c.m13 = c.m13 * y;
        c.m20 = c.m20 * z;
        c.m21 = c.m21 * z;
        c.m22 = c.m22 * z;
        c.m23 = c.m23 * z;
    }

    /**
     * Static version of {@link #scale(float, float, float)} which applies the
     * transformation to the given {@link MatrixStack}.
     * 
     * @see #scale(float, float, float)
     * 
     * @param x
     *            the factor to scale the x component by
     * @param y
     *            the factor to scale the y component by
     * @param z
     *            the factor to scale the z component by
     * @param stack
     *            the {@link MatrixStack} to apply the transformation on
     */
    public static void scale(float x, float y, float z, MatrixStack stack) {
        stack.scale(x, y, z);
    }

    /**
     * Apply a scaling transformation to the current matrix by scaling by the
     * factors in the given {@link Vector3f}.
     * 
     * @see #scale(float, float, float)
     * 
     * @param xyz
     *            contains the factors to scale by
     */
    public void scale(Vector3f xyz) {
        this.scale(xyz.x, xyz.y, xyz.z);
    }

    /**
     * Static version of {@link #scale(Vector3f)} which applies the
     * transformation to the given {@link MatrixStack}.
     * 
     * @see #scale(Vector3f)
     * 
     * @param v
     *            the vector containing the factors to scale by
     * @param stack
     *            the {@link MatrixStack} to apply the transformation on
     */
    public static void scale(Vector3f xyz, MatrixStack stack) {
        stack.scale(xyz);
    }

    /**
     * Apply rotation to the current matrix by rotating the given amount of
     * degrees about the given axis specified as x, y and z component.
     * <p>
     * If <code>C</code> is the current matrix and <code>R</code> the rotation
     * matrix, then the new current matrix will be <code>C * R</code>. So when
     * transforming a vector <code>v</code> with the new matrix by using
     * <code>C * R * v</code>, the rotation will be applied first!
     * 
     * @param ang
     *            the angle is in degrees
     * @param x
     *            the x component of the axis
     * @param y
     *            the y component of the axis
     * @param z
     *            the z component of the axis
     */
    public void rotate(float ang, float x, float y, float z) {
        Matrix4f c = mats[curr];
        // rotation matrix elements:
        // m30, m31, m32, m03, m13, m23 = 0
        // m33 = 1
        float cos = (float) Math.cos(TrigMath.degreesToRadians(ang));
        float sin = (float) Math.sin(TrigMath.degreesToRadians(ang));
        float m00 = (cos + x * x * (1.0f - cos));
        float m10 = x * y * (1.0f - cos) - z * sin;
        float m20 = x * z * (1.0f - cos) + y * sin;
        float m01 = y * x * (1.0f - cos) + z * sin;
        float m11 = cos + y * y * (1.0f - cos);
        float m21 = y * z * (1.0f - cos) - x * sin;
        float m02 = z * x * (1.0f - cos) - y * sin;
        float m12 = z * y * (1.0f - cos) + x * sin;
        float m22 = cos + z * z * (1.0f - cos);

        float nm00 = c.m00 * m00 + c.m10 * m01 + c.m20 * m02;
        float nm01 = c.m01 * m00 + c.m11 * m01 + c.m21 * m02;
        float nm02 = c.m02 * m00 + c.m12 * m01 + c.m22 * m02;
        float nm03 = c.m03 * m00 + c.m13 * m01 + c.m23 * m02;
        float nm10 = c.m00 * m10 + c.m10 * m11 + c.m20 * m12;
        float nm11 = c.m01 * m10 + c.m11 * m11 + c.m21 * m12;
        float nm12 = c.m02 * m10 + c.m12 * m11 + c.m22 * m12;
        float nm13 = c.m03 * m10 + c.m13 * m11 + c.m23 * m12;
        float nm20 = c.m00 * m20 + c.m10 * m21 + c.m20 * m22;
        float nm21 = c.m01 * m20 + c.m11 * m21 + c.m21 * m22;
        float nm22 = c.m02 * m20 + c.m12 * m21 + c.m22 * m22;
        float nm23 = c.m03 * m20 + c.m13 * m21 + c.m23 * m22;

        c.m00 = nm00;
        c.m01 = nm01;
        c.m02 = nm02;
        c.m03 = nm03;
        c.m10 = nm10;
        c.m11 = nm11;
        c.m12 = nm12;
        c.m13 = nm13;
        c.m20 = nm20;
        c.m21 = nm21;
        c.m22 = nm22;
        c.m23 = nm23;
    }

    /**
     * Static version of {@link #rotate(float, float, float, float)} which
     * applies the transformation to the given {@link MatrixStack}.
     * 
     * @see #rotate(float, float, float, float)
     * 
     * @param ang
     *            the angle in degrees
     * @param x
     *            the x component of the axis to rotate about
     * @param y
     *            the y component of the axis to rotate about
     * @param z
     *            the z component of the axis to rotate about
     * @param stack
     *            the {@link MatrixStack} to apply the transformation on
     */
    public static void rotate(float ang, float x, float y, float z,
            MatrixStack stack) {
        stack.rotate(ang, x, y, z);
    }

    /**
     * Apply a rotation transformation to the current matrix by rotating the
     * given amount of degrees about the given <code>axis</code>
     * {@link Vector3f}.
     * 
     * @see #rotate(float, float, float, float)
     * 
     * @param ang
     *            the angle in degrees
     * @param axis
     *            the axis to rotate about
     */
    public void rotate(float ang, Vector3f axis) {
        rotate(ang, axis.x, axis.y, axis.z);
    }

    /**
     * Static version of {@link #rotate(float, Vector3f)} which applies the
     * transformation to the given {@link MatrixStack}.
     * 
     * @see #rotate(float, Vector3f)
     * 
     * @param ang
     *            the angle in degrees
     * @param axis
     *            the axis to rotate about
     * @param stack
     *            the {@link MatrixStack} to apply the transformation on
     */
    public static void rotate(float ang, Vector3f axis, MatrixStack stack) {
        stack.rotate(ang, axis.x, axis.y, axis.z);
    }

    /**
     * Set the current matrix to identity.
     */
    public void loadIdentity() {
        mats[curr].identity();
    }

    /**
     * Static version of {@link #loadIdentity()}.
     * 
     * @param stack
     *            the {@link MatrixStack} to perform the operation on
     */
    public static void loadIdentity(MatrixStack stack) {
        stack.loadIdentity();
    }

    /**
     * Right-multiply the given matrix <code>mat</code> against the current
     * matrix. If <code>C</code> is the current matrix and <code>M</code> the
     * supplied matrix, then the new current matrix will be <code>C * M</code>.
     * So when transforming a vector <code>v</code> with the new matrix by using
     * <code>C * M * v</code>, the supplied matrix <code>mat</code> will be
     * applied first!
     * 
     * @param mat
     */
    public void multMatrix(Matrix4f mat) {
        if (mat == null) {
            throw new IllegalArgumentException("mat must not be null");
        }
        mats[curr].mul(mat);
    }

    /**
     * Static version of {@link #multMatrix(Matrix4f)}.
     * 
     * @see #multMatrix(Matrix4f)
     * 
     * @param mat
     *            the matrix to multiply the current stack matrix with
     * @param stack
     *            the {@link MatrixStack} to apply the operation on
     */
    public static void multMatrix(Matrix4f mat, MatrixStack stack) {
        stack.multMatrix(mat);
    }

}
