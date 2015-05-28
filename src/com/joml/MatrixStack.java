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

/**
 * Resembles the matrix stack known from legacy OpenGL.
 * <p>
 * Their names and semantics resemble those of the legacy OpenGL matrix stack.
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
	 * Working matrix which is used to create rotation, translation and scaling
	 * matrices.
	 */
	private Matrix4f work;

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
		work = new Matrix4f();
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
	 * Load the given {@link Matrix4f} into the current matrix of the stack.
	 * 
	 * @param mat
	 */
	public void loadMatrix(Matrix4f mat) {
		if (mat == null) {
			throw new IllegalArgumentException("mat must not be null");
		}
		mats[curr].set(mat);
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
	 * Stores the current matrix of the stack into the supplied
	 * <code>dest</code> matrix.
	 * 
	 * @param dest
	 *            the destination {@link Matrix4f} into which to store the
	 *            current stack matrix
	 * @return returns <code>dest</code>
	 */
	public Matrix4f get(Matrix4f dest) {
		dest.set(mats[curr]);
		return dest;
	}

	/**
	 * Apply a translation to the current matrix.
	 * <p>
	 * If <code>C</code> is the current matrix and <code>T</code> the
	 * translation matrix, then the new current matrix will be
	 * <code>C * T</code>. So when transforming a vector with the new matrix,
	 * the translation will be applied first!
	 * 
	 * @param x
	 * @param y
	 * @param z
	 */
	public void translate(float x, float y, float z) {
		work.translation(x, y, z);
		mats[curr].mul(work);
	}

	/**
	 * Apply scaling to the current matrix.
	 * <p>
	 * If <code>C</code> is the current matrix and <code>S</code> the scaling
	 * matrix, then the new current matrix will be <code>C * S</code>. So when
	 * transforming a vector with the new matrix, the scaling will be applied
	 * first!
	 * 
	 * @param x
	 * @param y
	 * @param z
	 */
	public void scale(float x, float y, float z) {
		work.scale(x, y, z);
		mats[curr].mul(work);
	}

	/**
	 * Apply rotation to the current matrix.
	 * <p>
	 * If <code>C</code> is the current matrix and <code>R</code> the rotation
	 * matrix, then the new current matrix will be <code>C * R</code>. So when
	 * transforming a vector with the new matrix, the rotation will be applied
	 * first!
	 * 
	 * @param ang
	 *            the angle is in degrees
	 * @param x
	 * @param y
	 * @param z
	 */
	public void rotate(float ang, float x, float y, float z) {
		work.rotation(TrigMath.degreesToRadians(ang), x, y, z);
		mats[curr].mul(work);
	}

	/**
	 * Set the current matrix to identity.
	 */
	public void loadIdentity() {
		mats[curr].identity();
	}

	/**
	 * Right-multiply the given matrix <code>mat</code> against the current
	 * matrix. If <code>C</code> is the current matrix and <code>M</code> the
	 * supplied matrix, then the new current matrix will be <code>C * M</code>.
	 * So when transforming a vector with the new matrix, the supplied matrix
	 * <code>mat</code> will be applied first!
	 * 
	 * @param mat
	 */
	public void multMatrix(Matrix4f mat) {
		if (mat == null) {
			throw new IllegalArgumentException("mat must not be null");
		}
		mats[curr].mul(mat);
	}

}
