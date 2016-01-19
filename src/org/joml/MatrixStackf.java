/*
 * (C) Copyright 2015-2016 Kai Burjack

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
package org.joml;

import java.util.Arrays;

/**
 * A stack of many {@link Matrix4f} instances. This resembles the matrix stack known from legacy OpenGL.
 * <p>
 * This {@link MatrixStackf} class inherits from {@link Matrix4f}, so the current/top matrix is always the {@link MatrixStackf}/{@link Matrix4f} itself. This
 * affects all operations in {@link Matrix4f} that take another {@link Matrix4f} as parameter. If a {@link MatrixStackf} is used as argument to those methods,
 * the effective argument will always be the <i>current</i> matrix of the matrix stack.
 * 
 * @author Kai Burjack
 */
public class MatrixStackf extends Matrix4f {

    private static final long serialVersionUID = 1L;

    /**
     * The matrix stack as a non-growable array. The size of the stack must be specified in the {@link #MatrixStack(int) constructor}.
     */
    private Matrix4f[] mats;

    /**
     * The index of the "current" matrix within {@link #mats}.
     */
    private int curr;

    /**
     * Create a new {@link MatrixStackf} of the given size.
     * <p>
     * Initially the stack pointer is at zero and the current matrix is set to identity.
     * 
     * @param stackSize
     *            the size of the stack. This must be at least 1, in which case the {@link MatrixStackf} simply only consists of <code>this</code>
     *            {@link Matrix4f}
     */
    public MatrixStackf(int stackSize) {
        if (stackSize < 1) {
            throw new IllegalArgumentException("stackSize must be >= 1"); //$NON-NLS-1$
        }
        mats = new Matrix4f[stackSize - 1];
        // Allocate all matrices up front to keep the promise of being "allocation-free"
        for (int i = 0; i < mats.length; i++) {
            mats[i] = new Matrix4f();
        }
    }

    /**
     * Set the stack pointer to zero and set the current/bottom matrix to {@link #identity() identity}.
     * 
     * @return this
     */
    public MatrixStackf clear() {
        curr = 0;
        identity();
        return this;
    }

    /**
     * Increment the stack pointer by one and set the values of the new current matrix to the one directly below it.
     * 
     * @return this
     */
    public MatrixStackf pushMatrix() {
        if (curr == mats.length) {
            throw new IllegalStateException("max stack size of " + (curr + 1) + " reached"); //$NON-NLS-1$ //$NON-NLS-2$
        }
        mats[curr++].set(this);
        return this;
    }

    /**
     * Decrement the stack pointer by one.
     * <p>
     * This will effectively dispose of the current matrix.
     * 
     * @return this
     */
    public MatrixStackf popMatrix() {
        if (curr == 0) {
            throw new IllegalStateException("already at the buttom of the stack"); //$NON-NLS-1$
        }
        set(mats[--curr]);
        return this;
    }

    private static int hashCode(Object[] array) {
        int prime = 31;
        if (array == null)
            return 0;
        int result = 1;
        for (int index = 0; index < array.length; index++) {
            result = prime * result + (array[index] == null ? 0 : array[index].hashCode());
        }
        return result;
    }

    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + curr;
        result = prime * result + MatrixStackf.hashCode(mats);
        return result;
    }

    /*
     * Contract between Matrix4f and MatrixStackf:
     * 
     * - Matrix4f.equals(MatrixStackf) is true iff all the 16 matrix elements are equal
     * - MatrixStackf.equals(Matrix4f) is true iff all the 16 matrix elements are equal
     * - MatrixStackf.equals(MatrixStackf) is true iff all 16 matrix elements are equal AND the matrix arrays as well as the stack pointer are equal
     * - everything else is inequal
     * 
     * (non-Javadoc)
     * @see org.joml.Matrix4f#equals(java.lang.Object)
     */
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (obj instanceof MatrixStackf) {
            MatrixStackf other = (MatrixStackf) obj;
            if (curr != other.curr)
                return false;
            if (!Arrays.equals(mats, other.mats))
                return false;
        }
        return true;
    }

}
