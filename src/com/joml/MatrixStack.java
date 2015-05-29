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

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;
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
public class MatrixStack implements Serializable, Externalizable {

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
     * 
     * @return this
     */
    public MatrixStack clear() {
        curr = 0;
        mats[0].identity();
        return this;
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
        if (stack == null) {
            throw new IllegalArgumentException("stack must not be null");
        }
        stack.clear();
    }

    /**
     * Load the given {@link Matrix4f} into the current matrix of the stack.
     * 
     * @param mat
     *            the matrix which is stored in the current stack matrix
     */
    public MatrixStack loadMatrix(Matrix4f mat) {
        if (mat == null) {
            throw new IllegalArgumentException("mat must not be null");
        }
        mats[curr].set(mat);
        return this;
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
        if (mat == null) {
            throw new IllegalArgumentException("mat must not be null");
        }
        if (stack == null) {
            throw new IllegalArgumentException("stack must not be null");
        }
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
     * @return this
     */
    public MatrixStack loadMatrix(FloatBuffer columnMajorArray) {
        if (columnMajorArray == null) {
            throw new IllegalArgumentException(
                    "columnMajorArray must not be null");
        }
        mats[curr].set(columnMajorArray);
        return this;
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
     * @return this
     */
    public MatrixStack loadMatrix(float[] columnMajorArray, int offset) {
        if (columnMajorArray == null) {
            throw new IllegalArgumentException(
                    "columnMajorArray must not be null");
        }
        if (columnMajorArray.length - offset < 16) {
            throw new IllegalArgumentException(
                    "columnMajorArray does not have enough elements");
        }
        mats[curr].set(columnMajorArray, offset);
        return this;
    }

    /**
     * Increment the stack pointer by one and set the values of the new current
     * matrix to the one directly below it.
     * 
     * @return this
     */
    public MatrixStack pushMatrix() {
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
        return this;
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
        if (stack == null) {
            throw new IllegalArgumentException("stack must not be null");
        }
        stack.pushMatrix();
    }

    /**
     * Decrement the stack pointer by one.
     * <p>
     * This will effectively dispose of the current matrix.
     * 
     * @return this
     */
    public MatrixStack popMatrix() {
        if (curr == 0) {
            throw new IllegalStateException(
                    "already at the buttom of the stack");
        }
        curr--;
        return this;
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
        if (stack == null) {
            throw new IllegalArgumentException("stack must not be null");
        }
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
        if (dest == null) {
            throw new IllegalArgumentException("dest must not be null");
        }
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
        if (dest == null) {
            throw new IllegalArgumentException("dest must not be null");
        }
        if (dest.remaining() < 16) {
            throw new IllegalArgumentException(
                    "dest does not have enough space");
        }
        mats[curr].get(dest);
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
        if (dest == null) {
            throw new IllegalArgumentException("dest must not be null");
        }
        if (offset < 0) {
            throw new IllegalArgumentException("offset must not be negative");
        }
        if (dest.length - offset < 16) {
            throw new IllegalArgumentException(
                    "dest does not have enough elements");
        }
        mats[curr].get(dest, offset);
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
        if (dest == null) {
            throw new IllegalArgumentException("dest must not be null");
        }
        if (stack == null) {
            throw new IllegalArgumentException("stack must not be null");
        }
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
        if (stack == null) {
            throw new IllegalArgumentException("stack must not be null");
        }
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
     * @return this
     */
    public MatrixStack translate(float x, float y, float z) {
        Matrix4f c = mats[curr];
        c.translate(x, y, z);
        return this;
    }

    /**
     * Apply a translation to the current matrix by translating by the number of
     * units in the given {@link Vector3f}.
     * 
     * @see #translate(float, float, float)
     * 
     * @param xyz
     *            contains the number of units to translate by
     * @return this
     */
    public MatrixStack translate(Vector3f xyz) {
        if (xyz == null) {
            throw new IllegalArgumentException("xyz must not be null");
        }
        translate(xyz.x, xyz.y, xyz.z);
        return this;
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
        if (stack == null) {
            throw new IllegalArgumentException("stack must not be null");
        }
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
        if (v == null) {
            throw new IllegalArgumentException("v must not be null");
        }
        if (stack == null) {
            throw new IllegalArgumentException("v must not be null");
        }
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
     * @return this
     */
    public MatrixStack scale(float x, float y, float z) {
        Matrix4f c = mats[curr];
        c.scale(x, y, z);
        return this;
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
        if (stack == null) {
            throw new IllegalArgumentException("v must not be null");
        }
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
     * @return this
     */
    public MatrixStack scale(Vector3f xyz) {
        if (xyz == null) {
            throw new IllegalArgumentException("xyz must not be null");
        }
        this.scale(xyz.x, xyz.y, xyz.z);
        return this;
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
        if (xyz == null) {
            throw new IllegalArgumentException("xyz must not be null");
        }
        if (stack == null) {
            throw new IllegalArgumentException("v must not be null");
        }
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
     * @return this
     */
    public MatrixStack rotate(float ang, float x, float y, float z) {
        Matrix4f c = mats[curr];
        c.rotate(ang, x, y, z);
        return this;
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
        if (stack == null) {
            throw new IllegalArgumentException("v must not be null");
        }
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
     * @return this
     */
    public MatrixStack rotate(float ang, Vector3f axis) {
        if (axis == null) {
            throw new IllegalArgumentException("axis must not be null");
        }
        rotate(ang, axis.x, axis.y, axis.z);
        return this;
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
        if (axis == null) {
            throw new IllegalArgumentException("axis must not be null");
        }
        if (stack == null) {
            throw new IllegalArgumentException("stack must not be null");
        }
        stack.rotate(ang, axis.x, axis.y, axis.z);
    }

    /**
     * Set the current matrix to identity.
     * 
     * @return this
     */
    public MatrixStack loadIdentity() {
        mats[curr].identity();
        return this;
    }

    /**
     * Static version of {@link #loadIdentity()}.
     * 
     * @param stack
     *            the {@link MatrixStack} to perform the operation on
     */
    public static void loadIdentity(MatrixStack stack) {
        if (stack == null) {
            throw new IllegalArgumentException("stack must not be null");
        }
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
     *            the matrix to multiply this matrix with
     * @return this
     */
    public MatrixStack multMatrix(Matrix4f mat) {
        if (mat == null) {
            throw new IllegalArgumentException("mat must not be null");
        }
        mats[curr].mul(mat);
        return this;
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
        if (mat == null) {
            throw new IllegalArgumentException("mat must not be null");
        }
        if (stack == null) {
            throw new IllegalArgumentException("stack must not be null");
        }
        stack.multMatrix(mat);
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeInt(curr);
        out.writeInt(mats.length);
        for (int i = 0; i < mats.length; i++) {
            out.writeObject(mats[i]);
        }
    }

    public void readExternal(ObjectInput in) throws IOException,
            ClassNotFoundException {
        curr = in.readInt();
        int len = in.readInt();
        mats = new Matrix4f[len];
        for (int i = 0; i < len; i++) {
            mats[i] = (Matrix4f) in.readObject();
        }
    }

}
