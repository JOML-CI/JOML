/*
 * (C) Copyright 2015 Kai Burjack

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
public class MatrixStack implements Serializable {

    private static final long serialVersionUID = 1L;

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
            throw new IllegalArgumentException("stackSize must be >= 1"); //$NON-NLS-1$
        }
        mats = new Matrix4f[stackSize];
        // Allocate all matrices up front to keep the promise of being
        // "allocation-free"
        for (int i = 0; i < stackSize; i++) {
            mats[i] = new Matrix4f();
        }
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
     * Load the given {@link Matrix4f} into the current matrix of the stack.
     * 
     * @param mat
     *            the matrix which is stored in the current stack matrix
     * @return this
     */
    public MatrixStack loadMatrix(Matrix4f mat) {
        if (mat == null) {
            throw new IllegalArgumentException("mat must not be null"); //$NON-NLS-1$
        }
        mats[curr].set(mat);
        return this;
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
            throw new IllegalArgumentException("columnMajorArray must not be null"); //$NON-NLS-1$
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
            throw new IllegalArgumentException("columnMajorArray must not be null"); //$NON-NLS-1$
        }
        if (columnMajorArray.length - offset < 16) {
            throw new IllegalArgumentException("columnMajorArray does not have enough elements"); //$NON-NLS-1$
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
            throw new IllegalStateException("max stack size of " + (curr + 1) + " reached"); //$NON-NLS-1$ //$NON-NLS-2$
        }
        mats[curr + 1].set(mats[curr]);
        curr++;
        return this;
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
            throw new IllegalStateException("already at the buttom of the stack"); //$NON-NLS-1$
        }
        curr--;
        return this;
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
            throw new IllegalArgumentException("dest must not be null"); //$NON-NLS-1$
        }
        dest.set(mats[curr]);
        return dest;
    }

    /**
     * Store the current matrix into the supplied {@link FloatBuffer} at the
     * current buffer {@link FloatBuffer#position() position}.
     * <p>
     * This method will not increment the position of the given
     * FloatBuffer.
     * <p>
     * In order to specify the offset into the FloatBuffer at which
     * the matrix is stored, use {@link #get(int, FloatBuffer)}, taking
     * the absolute position as parameter.
     * 
     * @see #get(int, FloatBuffer)
     * @see Matrix4f#get(int, FloatBuffer)
     * 
     * @param dest
     *            the destination FloatBuffer into which to store the
     *            column-major values of the current stack matrix
     * @return this
     */
    public MatrixStack get(FloatBuffer dest) {
        if (dest == null) {
            throw new IllegalArgumentException("dest must not be null"); //$NON-NLS-1$
        }
        if (dest.remaining() < 16) {
            throw new IllegalArgumentException("dest does not have enough space"); //$NON-NLS-1$
        }
        mats[curr].get(dest);
        return this;
    }

    /**
     * Store the current matrix into the supplied {@link FloatBuffer} starting
     * at the specified absolute buffer position/index.
     * <p>
     * This method will not increment the position of the given
     * {@link FloatBuffer}.
     * <p>
     * In order to store the matrix at the current buffer's position, use {@link #get(FloatBuffer)} instead.
     * 
     * @see #get(FloatBuffer)
     * @see Matrix4f#get(FloatBuffer)
     * 
     * @param index
     *            the absolute position into the {@link FloatBuffer}
     * @param dest
     *            will receive the values of this matrix in column-major order
     * @return this
     */
    public MatrixStack get(int index, FloatBuffer dest) {
        if (dest == null) {
            throw new IllegalArgumentException("dest must not be null"); //$NON-NLS-1$
        }
        if (dest.remaining() < 16) {
            throw new IllegalArgumentException("dest does not have enough space"); //$NON-NLS-1$
        }
        mats[curr].get(index, dest);
        return this;
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
            throw new IllegalArgumentException("dest must not be null"); //$NON-NLS-1$
        }
        if (offset < 0) {
            throw new IllegalArgumentException("offset must not be negative"); //$NON-NLS-1$
        }
        if (dest.length - offset < 16) {
            throw new IllegalArgumentException("dest does not have enough elements"); //$NON-NLS-1$
        }
        mats[curr].get(dest, offset);
        return dest;
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
     *          the offset in x
     * @param y
     *          the offset in y
     * @param z
     *          the offset in z
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
     * <p>
     * If <code>C</code> is the current matrix and <code>T</code> the
     * translation matrix, then the new matrix will be <code>C * T</code>. So
     * when transforming a vector <code>v</code> with the new matrix by using
     * <code>C * T * v</code>, the translation will be applied first!
     * 
     * @see #translate(float, float, float)
     * 
     * @param xyz
     *            contains the number of units to translate by
     * @return this
     */
    public MatrixStack translate(Vector3f xyz) {
        if (xyz == null) {
            throw new IllegalArgumentException("xyz must not be null"); //$NON-NLS-1$
        }
        translate(xyz.x, xyz.y, xyz.z);
        return this;
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
     * Apply a scaling transformation to the current matrix by scaling by the
     * factors in the given {@link Vector3f}.
     * <p>
     * If <code>C</code> is the current matrix and <code>S</code> the scaling
     * matrix, then the new matrix will be <code>C * S</code>. So when
     * transforming a vector <code>v</code> with the new matrix by using
     * <code>C * S * v</code>, the scaling will be applied first!
     * 
     * @see #scale(float, float, float)
     * 
     * @param xyz
     *            contains the factors to scale by
     * @return this
     */
    public MatrixStack scale(Vector3f xyz) {
        if (xyz == null) {
            throw new IllegalArgumentException("xyz must not be null"); //$NON-NLS-1$
        }
        this.scale(xyz.x, xyz.y, xyz.z);
        return this;
    }

    /**
     * Apply scaling to the current matrix by uniformly scaling all unit axes by
     * the given <code>xyz</code> factor.
     * <p>
     * If <code>C</code> is the current matrix and <code>S</code> the scaling
     * matrix, then the new matrix will be <code>C * S</code>. So when
     * transforming a vector <code>v</code> with the new matrix by using
     * <code>C * S * v</code>, the scaling will be applied first!
     * 
     * @see #scale(float, float, float)
     * 
     * @param xyz
     *            the factor for all components
     * @return this
     */
    public MatrixStack scale(float xyz) {
        return scale(xyz, xyz, xyz);
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
     * Apply a rotation transformation to the current matrix by rotating the
     * given amount of degrees about the given <code>axis</code>
     * {@link Vector3f}.
     * <p>
     * If <code>C</code> is the current matrix and <code>R</code> the rotation
     * matrix, then the new current matrix will be <code>C * R</code>. So when
     * transforming a vector <code>v</code> with the new matrix by using
     * <code>C * R * v</code>, the rotation will be applied first!
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
            throw new IllegalArgumentException("axis must not be null"); //$NON-NLS-1$
        }
        rotate(ang, axis.x, axis.y, axis.z);
        return this;
    }

    /**
     * Apply the rotation transformation of the given {@link Quaternionf} to the
     * current matrix.
     * <p>
     * If <code>C</code> is the current matrix and <code>R</code> the rotation
     * matrix, then the new current matrix will be <code>C * R</code>. So when
     * transforming a vector <code>v</code> with the new matrix by using
     * <code>C * R * v</code>, the rotation will be applied first!
     * 
     * @param quat
     *            the {@link Quaternionf}
     * @return this
     */
    public MatrixStack rotate(Quaternionf quat) {
        if (quat == null) {
            throw new IllegalArgumentException("quat must not be null"); //$NON-NLS-1$
        }
        mats[curr].rotate(quat);
        return this;
    }

    /**
     * Apply a rotation transformation, rotating about the given
     * {@link AxisAngle4f}, to the current matrix.
     * <p>
     * If <code>C</code> is the current matrix and <code>R</code> the rotation
     * matrix, then the new current matrix will be <code>C * R</code>. So when
     * transforming a vector <code>v</code> with the new matrix by using
     * <code>C * R * v</code>, the rotation will be applied first!
     * 
     * @param angleAxis
     *            the {@link AxisAngle4f} (needs to be
     *            {@link AxisAngle4f#normalize() normalized})
     * @return this
     */
    public MatrixStack rotate(AxisAngle4f angleAxis) {
        if (angleAxis == null) {
            throw new IllegalArgumentException("angleAxis must not be null"); //$NON-NLS-1$
        }
        mats[curr].rotate(angleAxis);
        return this;
    }

    /**
     * Apply rotation about the X axis to the current matrix by rotating the
     * given amount of degrees.
     * <p>
     * If <code>C</code> is the current matrix and <code>R</code> the rotation
     * matrix, then the new current matrix will be <code>C * R</code>. So when
     * transforming a vector <code>v</code> with the new matrix by using
     * <code>C * R * v</code>, the rotation will be applied first!
     * 
     * @param ang
     *            the angle in degrees
     * @return this
     */
    public MatrixStack rotateX(float ang) {
        mats[curr].rotateX(ang);
        return this;
    }

    /**
     * Apply rotation about the Y axis to the current matrix by rotating the
     * given amount of degrees.
     * <p>
     * If <code>C</code> is the current matrix and <code>R</code> the rotation
     * matrix, then the new current matrix will be <code>C * R</code>. So when
     * transforming a vector <code>v</code> with the new matrix by using
     * <code>C * R * v</code>, the rotation will be applied first!
     * 
     * @param ang
     *            the angle in degrees
     * @return this
     */
    public MatrixStack rotateY(float ang) {
        mats[curr].rotateY(ang);
        return this;
    }

    /**
     * Apply rotation about the X axis to the current matrix by rotating the
     * given amount of degrees.
     * <p>
     * If <code>C</code> is the current matrix and <code>R</code> the rotation
     * matrix, then the new current matrix will be <code>C * R</code>. So when
     * transforming a vector <code>v</code> with the new matrix by using
     * <code>C * R * v</code>, the rotation will be applied first!
     * 
     * @param ang
     *            the angle in degrees
     * @return this
     */
    public MatrixStack rotateZ(float ang) {
        mats[curr].rotateZ(ang);
        return this;
    }

    /**
     * Apply rotation of <code>angleX</code> radians about the X axis to the current matrix,
     * followed by a rotation of <code>angleY</code> radians about the Y axis and
     * followed by a rotation of <code>angleZ</code> radians about the Z axis.
     * <p>
     * If <code>C</code> is the current matrix and <code>R</code> the rotation matrix,
     * then the new matrix will be <code>C * R</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>C * R * v</code>, the
     * rotation will be applied first!
     * <p>
     * This method is equivalent to calling: <tt>rotateX(angleX).rotateY(angleY).rotateZ(angleZ)</tt>
     * 
     * @param angleX
     *            the angle to rotate about X
     * @param angleY
     *            the angle to rotate about Y
     * @param angleZ
     *            the angle to rotate about Z
     * @return this
     */
    public MatrixStack rotateXYZ(float angleX, float angleY, float angleZ) {
        mats[curr].rotateXYZ(angleX, angleY, angleZ);
        return this;
    }

    /**
     * Apply rotation of <code>angleY</code> radians about the Y axis to the current matrix,
     * followed by a rotation of <code>angleX</code> radians about the X axis and
     * followed by a rotation of <code>angleZ</code> radians about the Z axis.
     * <p>
     * If <code>C</code> is the current matrix and <code>R</code> the rotation matrix,
     * then the new matrix will be <code>C * R</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>C * R * v</code>, the
     * rotation will be applied first!
     * <p>
     * This method is equivalent to calling: <tt>rotateY(angleY).rotateX(angleX).rotateZ(angleZ)</tt>
     * 
     * @param angleY
     *            the angle to rotate about Y
     * @param angleX
     *            the angle to rotate about X
     * @param angleZ
     *            the angle to rotate about Z
     * @return this
     */
    public MatrixStack rotateYXZ(float angleY, float angleX, float angleZ) {
        mats[curr].rotateYXZ(angleY, angleX, angleZ);
        return this;
    }

    /**
     * Apply rotation of <code>angleZ</code> radians about the Z axis to the current matrix,
     * followed by a rotation of <code>angleY</code> radians about the Y axis and
     * followed by a rotation of <code>angleX</code> radians about the X axis.
     * <p>
     * If <code>C</code> is the current matrix and <code>R</code> the rotation matrix,
     * then the new matrix will be <code>C * R</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>C * R * v</code>, the
     * rotation will be applied first!
     * <p>
     * This method is equivalent to calling: <tt>rotateZ(angleZ).rotateY(angleY).rotateX(angleX)</tt>
     * 
     * @param angleZ
     *            the angle to rotate about Z
     * @param angleY
     *            the angle to rotate about Y
     * @param angleX
     *            the angle to rotate about X
     * @return this
     */
    public MatrixStack rotateZYX(float angleZ, float angleY, float angleX) {
        mats[curr].rotateZYX(angleZ, angleY, angleX);
        return this;
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
     * Post-multiply the given matrix <code>mat</code> against the current
     * matrix.
     * <p>
     * If <code>C</code> is the current matrix and <code>M</code> the supplied
     * matrix, then the new current matrix will be <code>C * M</code>. So when
     * transforming a vector <code>v</code> with the new matrix by using
     * <code>C * M * v</code>, the supplied matrix <code>mat</code> will be
     * applied first!
     * 
     * @param mat
     *            the matrix to multiply this matrix with
     * @return this
     */
    public MatrixStack multMatrix(Matrix4f mat) {
        if (mat == null) {
            throw new IllegalArgumentException("mat must not be null"); //$NON-NLS-1$
        }
        mats[curr].mul(mat);
        return this;
    }

    /**
     * Apply a "lookat" transformation to the current matrix for a right-handed coordinate system, 
     * that aligns <code>-z</code> with <code>center - eye</code>.
     * <p>
     * If <code>C</code> is the current matrix and <code>L</code> the lookat
     * matrix, then the new current matrix will be <code>C * L</code>. So when
     * transforming a vector <code>v</code> with the new matrix by using
     * <code>C * L * v</code>, the lookat transformation will be applied first!
     * 
     * @param position
     *            the position of the camera
     * @param centre
     *            the point in space to look at
     * @param up
     *            the direction of 'up'. In most cases it is (x=0, y=1, z=0)
     * @return this
     */
    public MatrixStack lookAt(Vector3f position, Vector3f centre, Vector3f up) {
        mats[curr].lookAt(position, centre, up);
        return this;
    }

    /**
     * Apply a "lookat" transformation to the current matrix for a right-handed
     * coordinate system, that aligns <code>-z</code> with
     * <code>center - eye</code>.
     * <p>
     * If <code>C</code> is the current matrix and <code>L</code> the lookat
     * matrix, then the new current matrix will be <code>C * L</code>. So when
     * transforming a vector <code>v</code> with the new matrix by using
     * <code>C * L * v</code>, the lookat transformation will be applied first!
     * 
     * @see #lookAt(Vector3f, Vector3f, Vector3f)
     * 
     * @param eyeX
     *              the x-coordinate of the eye/camera location
     * @param eyeY
     *              the y-coordinate of the eye/camera location
     * @param eyeZ
     *              the z-coordinate of the eye/camera location
     * @param centerX
     *              the x-coordinate of the point to look at
     * @param centerY
     *              the y-coordinate of the point to look at
     * @param centerZ
     *              the z-coordinate of the point to look at
     * @param upX
     *              the x-coordinate of the up vector
     * @param upY
     *              the y-coordinate of the up vector
     * @param upZ
     *              the z-coordinate of the up vector
     * @return this
     */
    public MatrixStack lookAt(float eyeX, float eyeY, float eyeZ, float centerX, float centerY, float centerZ,
            float upX, float upY, float upZ) {
        mats[curr].lookAt(eyeX, eyeY, eyeZ, centerX, centerY, centerZ, upX, upY, upZ);
        return this;
    }

    /**
     * Apply a rotation transformation to the current matrix to make
     * <code>-z</code> point along <code>dir</code>.
     * <p>
     * If <code>C</code> is the current matrix and <code>L</code> the lookalong
     * matrix, then the new current matrix will be <code>C * L</code>. So when
     * transforming a vector <code>v</code> with the new matrix by using
     * <code>C * L * v</code>, the lookalong transformation will be applied
     * first!
     * <p>
     * This is equivalent to calling
     * {@link #lookAt(float, float, float, float, float, float, float, float, float)
     * lookAt} with <code>eye = (0, 0, 0)</code> and <code>center = dir</code>.
     * 
     * @param dirX
     *              the x-coordinate of the direction to look along
     * @param dirY
     *              the y-coordinate of the direction to look along
     * @param dirZ
     *              the z-coordinate of the direction to look along
     * @param upX
     *              the x-coordinate of the up vector
     * @param upY
     *              the y-coordinate of the up vector
     * @param upZ
     *              the z-coordinate of the up vector
     * @return this
     */
    public MatrixStack lookAlong(float dirX, float dirY, float dirZ, float upX, float upY, float upZ) {
        mats[curr].lookAlong(dirX, dirY, dirZ, upX, upY, upZ);
        return this;
    }

    /**
     * Apply a rotation transformation to the current matrix to make
     * <code>-z</code> point along <code>dir</code>.
     * <p>
     * If <code>C</code> is the current matrix and <code>L</code> the lookalong
     * matrix, then the new current matrix will be <code>C * L</code>. So when
     * transforming a vector <code>v</code> with the new matrix by using
     * <code>C * L * v</code>, the lookalong transformation will be applied
     * first!
     * <p>
     * This is equivalent to calling
     * {@link #lookAt(Vector3f, Vector3f, Vector3f) lookAt} with
     * <code>eye = (0, 0, 0)</code> and <code>center = dir</code>.
     * 
     * @param dir
     *            the direction in space to look along
     * @param up
     *            the direction of 'up'
     * @return this
     */
    public MatrixStack lookAlong(Vector3f dir, Vector3f up) {
        mats[curr].lookAlong(dir, up);
        return this;
    }

    /**
     * Apply a symmetric perspective projection frustum transformation to the
     * current matrix.
     * <p>
     * If <code>C</code> is the current matrix and <code>P</code> the
     * perspective projection matrix, then the new matrix will be
     * <code>C * P</code>. So when transforming a vector <code>v</code> with the
     * new matrix by using <code>C * P * v</code>, the perspective projection
     * will be applied first!
     * 
     * @param fovy
     *            the vertical field of view in degrees
     * @param aspect
     *            the aspect ratio (i.e. width / height)
     * @param zNear
     *            near clipping plane distance
     * @param zFar
     *            far clipping plane distance
     * @return this
     */
    public MatrixStack perspective(float fovy, float aspect, float zNear, float zFar) {
        mats[curr].perspective(fovy, aspect, zNear, zFar);
        return this;
    }

    /**
     * Apply an orthographic projection transformation to the current matrix.
     * <p>
     * If <code>C</code> is the current matrix and <code>O</code> the
     * orthographic projection matrix, then the new matrix will be
     * <code>C * O</code>. So when transforming a vector <code>v</code> with the
     * new matrix by using <code>C * O * v</code>, the orthographic projection
     * will be applied first!
     * 
     * @param left
     *            the distance from the center to the left frustum edge
     * @param right
     *            the distance from the center to the right frustum edge
     * @param bottom
     *            the distance from the center to the bottom frustum edge
     * @param top
     *            the distance from the center to the top frustum edge
     * @param zNear
     *            near clipping plane distance
     * @param zFar
     *            far clipping plane distance
     * @return this
     */
    public MatrixStack ortho(float left, float right, float bottom, float top, float zNear, float zFar) {
        mats[curr].ortho(left, right, bottom, top, zNear, zFar);
        return this;
    }

    /**
     * Apply an arbitrary perspective projection frustum transformation to the
     * current matrix.
     * <p>
     * If <code>C</code> is the current matrix and <code>F</code> the frustum
     * projection matrix, then the new matrix will be <code>C * F</code>. So
     * when transforming a vector <code>v</code> with the new matrix by using
     * <code>C * F * v</code>, the frustum projection will be applied first!
     * 
     * @param left
     *            the distance along the x-axis to the left frustum edge
     * @param right
     *            the distance along the x-axis to the right frustum edge
     * @param bottom
     *            the distance along the y-axis to the bottom frustum edge
     * @param top
     *            the distance along the y-axis to the top frustum edge
     * @param zNear
     *            the distance along the z-axis to the near clipping plane
     * @param zFar
     *            the distance along the z-axis to the far clipping plane
     * @return this
     */
    public MatrixStack frustum(float left, float right, float bottom, float top, float zNear, float zFar) {
        mats[curr].frustum(left, right, bottom, top, zNear, zFar);
        return this;
    }

    /**
     * Apply a mirror/reflection transformation to the current matrix that reflects about the given plane
     * specified via the plane normal and a point on the plane.
     * <p>
     * If <code>C</code> is the current matrix and <code>R</code> the reflection matrix,
     * then the new matrix will be <code>C * R</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>C * R * v</code>, the
     * reflection will be applied first!
     * 
     * @see Matrix4f#reflect(float, float, float, float, float, float)
     * 
     * @param nx
     *          the x-coordinate of the plane normal
     * @param ny
     *          the y-coordinate of the plane normal
     * @param nz
     *          the z-coordinate of the plane normal
     * @param px
     *          the x-coordinate of a point on the plane
     * @param py
     *          the y-coordinate of a point on the plane
     * @param pz
     *          the z-coordinate of a point on the plane
     * @return this
     */
    public MatrixStack reflect(float nx, float ny, float nz, float px, float py, float pz) {
        mats[curr].reflect(nx, ny, nz, px, py, pz);
        return this;
    }

    /**
     * Apply a mirror/reflection transformation to the current matrix that reflects about the given plane
     * specified via the plane normal and a point on the plane.
     * <p>
     * If <code>C</code> is the current matrix and <code>R</code> the reflection matrix,
     * then the new matrix will be <code>C * R</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>C * R * v</code>, the
     * reflection will be applied first!
     * 
     * @see Matrix4f#reflect(Vector3f, Vector3f)
     * 
     * @param normal
     *          the plane normal
     * @param point
     *          a point on the plane
     * @return this
     */
    public MatrixStack reflect(Vector3f normal, Vector3f point) {
        mats[curr].reflect(normal, point);
        return this;
    }

    /**
     * Apply a mirror/reflection transformation to the current matrix that reflects about a plane
     * specified via the plane orientation and a point on the plane.
     * <p>
     * This method can be used to build a reflection transformation based on the orientation of a mirror object in the scene.
     * It is assumed that the default mirror plane's normal is <tt>(0, 0, 1)</tt>. So, if the given {@link Quaternionf} is
     * the identity (does not apply any additional rotation), the reflection plane will be <tt>z=0</tt>, offset by the given <code>point</code>.
     * <p>
     * If <code>C</code> is the current matrix and <code>R</code> the reflection matrix,
     * then the new matrix will be <code>C * R</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>C * R * v</code>, the
     * reflection will be applied first!
     * 
     * @see Matrix4f#reflect(Quaternionf, Vector3f)
     * 
     * @param orientation
     *          the plane orientation
     * @param point
     *          a point on the plane
     * @return this
     */
    public MatrixStack reflect(Quaternionf orientation, Vector3f point) {
        mats[curr].reflect(orientation, point);
        return this;
    }

}
