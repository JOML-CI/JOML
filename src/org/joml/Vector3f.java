/*
 * (C) Copyright 2015-2016 Richard Greenlees

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

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.text.DecimalFormat;
import java.text.NumberFormat;


/**
 * Contains the definition of a Vector comprising 3 floats and associated
 * transformations.
 *
 * @author Richard Greenlees
 * @author Kai Burjack
 */
public class Vector3f implements Externalizable {

    private static final long serialVersionUID = 1L;    

    /**
     * The x component of the vector.
     */
    public float x;
    /**
     * The y component of the vector.
     */
    public float y;
    /**
     * The z component of the vector.
     */
    public float z;

    /**
     * Create a new {@link Vector3f} of <tt>(0, 0, 0)</tt>.
     */
    public Vector3f() {
    }

    /**
     * Create a new {@link Vector3f} and initialize all three components with the given value.
     *
     * @param d
     *          the value of all three components
     */
    public Vector3f(float d) {
        this(d, d, d);
    }

    /**
     * Create a new {@link Vector3f} with the given component values.
     * 
     * @param x
     *          the value of x
     * @param y
     *          the value of y
     * @param z
     *          the value of z
     */
    public Vector3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Create a new {@link Vector3f} with the same values as <code>v</code>.
     * 
     * @param v
     *          the {@link Vector3f} to copy the values from
     */
    public Vector3f(Vector3f v) {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
    }

    /**
     * Create a new {@link Vector3f} with the first two components from the
     * given <code>v</code> and the given <code>z</code>
     * 
     * @param v
     *          the {@link Vector2f} to copy the values from
     * @param z
     *          the z component
     */
    public Vector3f(Vector2f v, float z) {
        this.x = v.x;
        this.y = v.y;
        this.z = z;
    }

    /**
     * Create a new {@link Vector3f} and read this vector from the supplied {@link ByteBuffer}
     * at the current buffer {@link ByteBuffer#position() position}.
     * <p>
     * This method will not increment the position of the given ByteBuffer.
     * <p>
     * In order to specify the offset into the ByteBuffer at which
     * the vector is read, use {@link #Vector3f(int, ByteBuffer)}, taking
     * the absolute position as parameter.
     *
     * @param buffer values will be read in <tt>x, y, z</tt> order
     * @see #Vector3f(int, ByteBuffer)
     */
    public Vector3f(ByteBuffer buffer) {
        this(buffer.position(), buffer);
    }

    /**
     * Create a new {@link Vector3f} and read this vector from the supplied {@link ByteBuffer}
     * starting at the specified absolute buffer position/index.
     * <p>
     * This method will not increment the position of the given ByteBuffer.
     *
     * @param index  the absolute position into the ByteBuffer
     * @param buffer values will be read in <tt>x, y, z</tt> order
     */
    public Vector3f(int index, ByteBuffer buffer) {
        x = buffer.getFloat(index);
        y = buffer.getFloat(index + 4);
        z = buffer.getFloat(index + 8);
    }

    /**
     * Create a new {@link Vector3f} and read this vector from the supplied {@link FloatBuffer}
     * at the current buffer {@link FloatBuffer#position() position}.
     * <p>
     * This method will not increment the position of the given FloatBuffer.
     * <p>
     * In order to specify the offset into the FloatBuffer at which
     * the vector is read, use {@link #Vector3f(int, FloatBuffer)}, taking
     * the absolute position as parameter.
     *
     * @param buffer values will be read in <tt>x, y, z</tt> order
     * @see #Vector3f(int, FloatBuffer)
     */
    public Vector3f(FloatBuffer buffer) {
        this(buffer.position(), buffer);
    }

    /**
     * Create a new {@link Vector3f} and read this vector from the supplied {@link FloatBuffer}
     * starting at the specified absolute buffer position/index.
     * <p>
     * This method will not increment the position of the given FloatBuffer.
     *
     * @param index  the absolute position into the FloatBuffer
     * @param buffer values will be read in <tt>x, y, z</tt> order
     */
    public Vector3f(int index, FloatBuffer buffer) {
        x = buffer.get(index);
        y = buffer.get(index + 1);
        z = buffer.get(index + 2);
    }

    /**
     * Set the x, y and z components to match the supplied vector.
     * 
     * @param v
     *          contains the values of x, y and z to set
     * @return this
     */
    public Vector3f set(Vector3f v) {
        x = v.x;
        y = v.y;
        z = v.z;
        return this;
    }

    /**
     * Set the x, y and z components to match the supplied vector.
     * <p>
     * Note that due to the given vector <code>v</code> storing the components in double-precision,
     * there is the possibility to lose precision.
     * 
     * @param v
     *          contains the values of x, y and z to set
     * @return this
     */
    public Vector3f set(Vector3d v) {
        x = (float) v.x;
        y = (float) v.y;
        z = (float) v.z;
        return this;
    }

    /**
     * Set the first two components from the given <code>v</code>
     * and the z component from the given <code>z</code>
     *
     * @param v
     *          the {@link Vector2f} to copy the values from
     * @param z
     *          the z component
     * @return this
     */
    public Vector3f set(Vector2f v, float z) {
        this.x = v.x;
        this.y = v.y;
        this.z = z;
        return this;
    }

    /**
     * Set the x, y, and z components to the supplied value.
     *
     * @param d
     *          the value of all three components
     * @return this
     */
    public Vector3f set(float d) {
        return set(d, d, d);
    }

    /**
     * Set the x, y and z components to the supplied values.
     * 
     * @param x
     *          the x component
     * @param y
     *          the y component 
     * @param z
     *          the z component
     * @return this
     */
    public Vector3f set(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }

    /**
     * Read this vector from the supplied {@link ByteBuffer} at the current
     * buffer {@link ByteBuffer#position() position}.
     * <p>
     * This method will not increment the position of the given ByteBuffer.
     * <p>
     * In order to specify the offset into the ByteBuffer at which
     * the vector is read, use {@link #set(int, ByteBuffer)}, taking
     * the absolute position as parameter.
     *
     * @param buffer
     *          values will be read in <tt>x, y, z</tt> order
     * @return this
     * @see #set(int, ByteBuffer)
     */
    public Vector3f set(ByteBuffer buffer) {
        return set(buffer.position(), buffer);
    }

    /**
     * Read this vector from the supplied {@link ByteBuffer} starting at the specified
     * absolute buffer position/index.
     * <p>
     * This method will not increment the position of the given ByteBuffer.
     *
     * @param index
     *          the absolute position into the ByteBuffer
     * @param buffer
     *          values will be read in <tt>x, y, z</tt> order
     * @return this
     */
    public Vector3f set(int index, ByteBuffer buffer) {
        x = buffer.getFloat(index);
        y = buffer.getFloat(index + 4);
        z = buffer.getFloat(index + 8);
        return this;
    }

    /**
     * Read this vector from the supplied {@link FloatBuffer} at the current
     * buffer {@link FloatBuffer#position() position}.
     * <p>
     * This method will not increment the position of the given FloatBuffer.
     * <p>
     * In order to specify the offset into the FloatBuffer at which
     * the vector is read, use {@link #set(int, FloatBuffer)}, taking
     * the absolute position as parameter.
     *
     * @param buffer
     *          values will be read in <tt>x, y, z</tt> order
     * @return this
     * @see #set(int, FloatBuffer)
     */
    public Vector3f set(FloatBuffer buffer) {
        return set(buffer.position(), buffer);
    }

    /**
     * Read this vector from the supplied {@link FloatBuffer} starting at the specified
     * absolute buffer position/index.
     * <p>
     * This method will not increment the position of the given FloatBuffer.
     *
     * @param index
     *          the absolute position into the FloatBuffer
     * @param buffer
     *          values will be read in <tt>x, y, z</tt> order
     * @return this
     */
    public Vector3f set(int index, FloatBuffer buffer) {
        x = buffer.get(index);
        y = buffer.get(index + 1);
        z = buffer.get(index + 2);
        return this;
    }

    /**
     * Store this vector into the supplied {@link FloatBuffer} at the current
     * buffer {@link FloatBuffer#position() position}.
     * <p>
     * This method will not increment the position of the given FloatBuffer.
     * <p>
     * In order to specify the offset into the FloatBuffer at which
     * the vector is stored, use {@link #get(int, FloatBuffer)}, taking
     * the absolute position as parameter.
     * 
     * @see #get(int, FloatBuffer)
     * 
     * @param buffer
     *          will receive the values of this vector in <tt>x, y, z</tt> order
     * @return the passed in buffer
     * @see #get(int, FloatBuffer)
     */
    public FloatBuffer get(FloatBuffer buffer) {
        return get(buffer.position(), buffer);
    }

    /**
     * Store this vector into the supplied {@link FloatBuffer} starting at the specified
     * absolute buffer position/index.
     * <p>
     * This method will not increment the position of the given FloatBuffer.
     * 
     * @param index
     *          the absolute position into the FloatBuffer
     * @param buffer
     *          will receive the values of this vector in <tt>x, y, z</tt> order
     * @return the passed in buffer
     */
    public FloatBuffer get(int index, FloatBuffer buffer) {
        buffer.put(index,    x);
        buffer.put(index+1,  y);
        buffer.put(index+2,  z);
        return buffer;
    }

    /**
     * Store this vector into the supplied {@link ByteBuffer} at the current
     * buffer {@link ByteBuffer#position() position}.
     * <p>
     * This method will not increment the position of the given ByteBuffer.
     * <p>
     * In order to specify the offset into the ByteBuffer at which
     * the vector is stored, use {@link #get(int, ByteBuffer)}, taking
     * the absolute position as parameter.
     * 
     * @see #get(int, ByteBuffer)
     * 
     * @param buffer
     *          will receive the values of this vector in <tt>x, y, z</tt> order
     * @return the passed in buffer
     * @see #get(int, ByteBuffer)
     */
    public ByteBuffer get(ByteBuffer buffer) {
        return get(buffer.position(), buffer);
    }

    /**
     * Store this vector into the supplied {@link ByteBuffer} starting at the specified
     * absolute buffer position/index.
     * <p>
     * This method will not increment the position of the given ByteBuffer.
     * 
     * @param index
     *          the absolute position into the ByteBuffer
     * @param buffer
     *          will receive the values of this vector in <tt>x, y, z</tt> order
     * @return the passed in buffer
     */
    public ByteBuffer get(int index, ByteBuffer buffer) {
        buffer.putFloat(index,    x);
        buffer.putFloat(index+4,  y);
        buffer.putFloat(index+8,  z);
        return buffer;
    }

    /**
     * Subtract the supplied vector from this one and store the result in <code>this</code>.
     * 
     * @param v
     *          the vector to subtract
     * @return this
     */
    public Vector3f sub(Vector3f v) {
        x -= v.x;
        y -= v.y;
        z -= v.z;
        return this;
    }

    /**
     * Subtract the supplied vector from this one and store the result in <code>dest</code>.
     * 
     * @param v
     *          the vector to subtract
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Vector3f sub(Vector3f v, Vector3f dest) {
        dest.x = x - v.x;
        dest.y = y - v.y;
        dest.z = z - v.z;
        return dest;
    }

    /**
     * Decrement the components of this vector by the given values.
     * 
     * @param x
     *          the x component to subtract
     * @param y
     *          the y component to subtract
     * @param z
     *          the z component to subtract
     * @return this
     */
    public Vector3f sub(float x, float y, float z) {
        this.x -= x;
        this.y -= y;
        this.z -= z;
        return this;
    }

    /**
     * Decrement the components of this vector by the given values and store the result in <code>dest</code>.
     * 
     * @param x
     *          the x component to subtract
     * @param y
     *          the y component to subtract
     * @param z
     *          the z component to subtract
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Vector3f sub(float x, float y, float z, Vector3f dest) {
        dest.x = this.x - x;
        dest.y = this.y - y;
        dest.z = this.z - z;
        return dest;
    }

    /**
     * Add the supplied vector to this one.
     * 
     * @param v
     *          the vector to add
     * @return this
     */
    public Vector3f add(Vector3f v) {
        x += v.x;
        y += v.y;
        z += v.z;
        return this;
    }

    /**
     * Add the supplied vector to this one and store the result in <code>dest</code>.
     * 
     * @param v
     *          the vector to add
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Vector3f add(Vector3f v, Vector3f dest) {
        dest.x = x + v.x;
        dest.y = y + v.y;
        dest.z = z + v.z;
        return dest;
    }

    /**
     * Increment the components of this vector by the given values.
     * 
     * @param x
     *          the x component to add
     * @param y
     *          the y component to add
     * @param z
     *          the z component to add
     * @return this
     */
    public Vector3f add(float x, float y, float z) {
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }

    /**
     * Increment the components of this vector by the given values and store the result in <code>dest</code>.
     * 
     * @param x
     *          the x component to add
     * @param y
     *          the y component to add
     * @param z
     *          the z component to add
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Vector3f add(float x, float y, float z, Vector3f dest) {
        dest.x = this.x + x;
        dest.y = this.y + y;
        dest.z = this.z + z;
        return dest;
    }

    /**
     * Add the component-wise multiplication of <code>a * b</code> to this vector.
     * 
     * @param a
     *          the first multiplicand
     * @param b
     *          the second multiplicand
     * @return this
     */
    public Vector3f fma(Vector3f a, Vector3f b) {
        x += a.x * b.x;
        y += a.y * b.y;
        z += a.z * b.z;
        return this;
    }

    /**
     * Add the component-wise multiplication of <code>a * b</code> to this vector.
     * 
     * @param a
     *          the first multiplicand
     * @param b
     *          the second multiplicand
     * @return this
     */
    public Vector3f fma(float a, Vector3f b) {
        x += a * b.x;
        y += a * b.y;
        z += a * b.z;
        return this;
    }

    /**
     * Add the component-wise multiplication of <code>a * b</code> to this vector
     * and store the result in <code>dest</code>.
     * 
     * @param a
     *          the first multiplicand
     * @param b
     *          the second multiplicand
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Vector3f fma(Vector3f a, Vector3f b, Vector3f dest) {
        dest.x = x + a.x * b.x;
        dest.y = y + a.y * b.y;
        dest.z = z + a.z * b.z;
        return dest;
    }

    /**
     * Add the component-wise multiplication of <code>a * b</code> to this vector
     * and store the result in <code>dest</code>.
     * 
     * @param a
     *          the first multiplicand
     * @param b
     *          the second multiplicand
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Vector3f fma(float a, Vector3f b, Vector3f dest) {
        dest.x = x + a * b.x;
        dest.y = y + a * b.y;
        dest.z = z + a * b.z;
        return dest;
    }

    /**
     * Multiply this Vector3f component-wise by another Vector3f.
     * 
     * @param v
     *          the vector to multiply by
     * @return this
     */
    public Vector3f mul(Vector3f v) {
        x *= v.x;
        y *= v.y;
        z *= v.z;
        return this;
    }

    /**
     * Multiply this Vector3f component-wise by another Vector3f and store the result in <code>dest</code>.
     * 
     * @param v
     *          the vector to multiply by
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Vector3f mul(Vector3f v, Vector3f dest) {
        dest.x = x * v.x;
        dest.y = y * v.y;
        dest.z = z * v.z;
        return dest;
    }

    /**
     * Divide this Vector3f component-wise by another Vector3f.
     * 
     * @param v
     *          the vector to divide by
     * @return this
     */
    public Vector3f div(Vector3f v) {
        x /= v.x;
        y /= v.y;
        z /= v.z;
        return this;
    }

    /**
     * Divide this Vector3f component-wise by another Vector3f and store the result in <code>dest</code>.
     * 
     * @param v
     *          the vector to divide by
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Vector3f div(Vector3f v, Vector3f dest) {
        dest.x = x / v.x;
        dest.y = y / v.y;
        dest.z = z / v.z;
        return dest;
    }

    /**
     * Multiply this Vector3f by the given matrix <code>mat</code>, perform perspective division
     * and store the result in <code>dest</code>.
     * <p>
     * This method uses <tt>w=1.0</tt> as the fourth vector component.
     * 
     * @param mat
     *          the matrix to multiply this vector by
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Vector3f mulProject(Matrix4f mat, Vector3f dest) {
        float invW = 1.0f / (mat.m03 * x + mat.m13 * y + mat.m23 * z + mat.m33);
        dest.set((mat.m00 * x + mat.m10 * y + mat.m20 * z + mat.m30) * invW,
                 (mat.m01 * x + mat.m11 * y + mat.m21 * z + mat.m31) * invW,
                 (mat.m02 * x + mat.m12 * y + mat.m22 * z + mat.m32) * invW);
        return dest;
    }

    /**
     * Multiply this Vector3f by the given matrix <code>mat</code>, perform perspective division.
     * <p>
     * This method uses <tt>w=1.0</tt> as the fourth vector component.
     * 
     * @param mat
     *          the matrix to multiply this vector by
     * @return this
     */
    public Vector3f mulProject(Matrix4f mat) {
        return mulProject(mat, this);
    }

    /**
     * Multiply this Vector3f by the given matrix and store the result in <code>this</code>.
     * 
     * @param mat
     *          the matrix
     * @return this
     */
    public Vector3f mul(Matrix3f mat) {
        return mul(mat, this);
    }

    /**
     * Multiply this Vector3f by the given matrix and store the result in <code>dest</code>.
     * 
     * @param mat
     *          the matrix
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Vector3f mul(Matrix3f mat, Vector3f dest) {
        dest.set(mat.m00 * x + mat.m10 * y + mat.m20 * z,
                 mat.m01 * x + mat.m11 * y + mat.m21 * z,
                 mat.m02 * x + mat.m12 * y + mat.m22 * z);
        return dest;
    }

    /**
     * Multiply <code>this</code> by the given 4x4 matrix <code>mat</code>.
     * <p>
     * This method assumes the <tt>w</tt> component of <code>this</code> to be <tt>1.0</tt>.
     * 
     * @param mat
     *          the matrix to multiply this vector by
     * @return this
     */
    public Vector3f mulPosition(Matrix4f mat) {
        return mulPosition(mat, this);
    }

    /**
     * Multiply <code>this</code> by the given 4x4 matrix <code>mat</code> and store the
     * result in <code>dest</code>.
     * <p>
     * This method assumes the <tt>w</tt> component of <code>this</code> to be <tt>1.0</tt>.
     * 
     * @param mat
     *          the matrix to multiply this vector by
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Vector3f mulPosition(Matrix4f mat, Vector3f dest) {
        dest.set(mat.m00 * x + mat.m10 * y + mat.m20 * z + mat.m30,
                 mat.m01 * x + mat.m11 * y + mat.m21 * z + mat.m31,
                 mat.m02 * x + mat.m12 * y + mat.m22 * z + mat.m32);
        return dest;
    }

    /**
     * Multiply <code>this</code> by the given 4x4 matrix <code>mat</code> and return the <i>w</i> component
     * of the resulting 4D vector.
     * <p>
     * This method assumes the <tt>w</tt> component of <code>this</code> to be <tt>1.0</tt>.
     * 
     * @param mat
     *          the matrix to multiply this vector by
     * @return the <i>w</i> component of the resulting 4D vector after multiplication
     */
    public float mulPositionW(Matrix4f mat) {
        return mulPositionW(mat, this);
    }

    /**
     * Multiply <code>this</code> by the given 4x4 matrix <code>mat</code>, store the
     * result in <code>dest</code> and return the <i>w</i> component of the resulting 4D vector.
     * <p>
     * This method assumes the <tt>w</tt> component of <code>this</code> to be <tt>1.0</tt>.
     * 
     * @param mat
     *          the matrix to multiply this vector by
     * @param dest
     *          will hold the <tt>(x, y, z)</tt> components of the resulting vector
     * @return the <i>w</i> component of the resulting 4D vector after multiplication
     */
    public float mulPositionW(Matrix4f mat, Vector3f dest) {
        float w = mat.m03 * x + mat.m13 * y + mat.m23 * z + mat.m33;
        dest.set(mat.m00 * x + mat.m10 * y + mat.m20 * z + mat.m30,
                 mat.m01 * x + mat.m11 * y + mat.m21 * z + mat.m31,
                 mat.m02 * x + mat.m12 * y + mat.m22 * z + mat.m32);
        return w;
    }

    /**
     * Multiply <code>this</code> by the given 4x4 matrix <code>mat</code>.
     * <p>
     * This method assumes the <tt>w</tt> component of <code>this</code> to be <tt>0.0</tt>.
     * 
     * @param mat
     *          the matrix to multiply this vector by
     * @return this
     */
    public Vector3f mulDirection(Matrix4f mat) {
        return mulDirection(mat, this);
    }

    /**
     * Multiply <code>this</code> by the given 4x4 matrix <code>mat</code> and store the
     * result in <code>dest</code>.
     * <p>
     * This method assumes the <tt>w</tt> component of <code>this</code> to be <tt>0.0</tt>.
     * 
     * @param mat
     *          the matrix to multiply this vector by
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Vector3f mulDirection(Matrix4f mat, Vector3f dest) {
        dest.set(mat.m00 * x + mat.m10 * y + mat.m20 * z,
                 mat.m01 * x + mat.m11 * y + mat.m21 * z,
                 mat.m02 * x + mat.m12 * y + mat.m22 * z);
        return dest;
    }

    /**
     * Multiply all components of this {@link Vector3f} by the given scalar
     * value.
     * 
     * @param scalar
     *          the scalar to multiply this vector by
     * @return this
     */
    public Vector3f mul(float scalar) {
        x *= scalar;
        y *= scalar;
        z *= scalar;
        return this;
    }

    /**
     * Multiply all components of this {@link Vector3f} by the given scalar
     * value and store the result in <code>dest</code>.
     * 
     * @param scalar
     *          the scalar to multiply this vector by
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Vector3f mul(float scalar, Vector3f dest) {
        dest.x = x * scalar;
        dest.y = y * scalar;
        dest.z = z * scalar;
        return dest;
    }

    /**
     * Multiply the components of this Vector3f by the given scalar values and store the result in <code>this</code>.
     * 
     * @param x
     *          the x component to multiply this vector by
     * @param y
     *          the y component to multiply this vector by
     * @param z
     *          the z component to multiply this vector by
     * @return this
     */
    public Vector3f mul(float x, float y, float z) {
        this.x *= x;
        this.y *= y;
        this.z *= z;
        return this;
    }

    /**
     * Multiply the components of this Vector3f by the given scalar values and store the result in <code>dest</code>.
     * 
     * @param x
     *          the x component to multiply this vector by
     * @param y
     *          the y component to multiply this vector by
     * @param z
     *          the z component to multiply this vector by
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Vector3f mul(float x, float y, float z, Vector3f dest) {
        dest.x = this.x * x;
        dest.y = this.y * y;
        dest.z = this.z * z;
        return dest;
    }

    /**
     * Divide all components of this {@link Vector3f} by the given scalar
     * value.
     * 
     * @param scalar
     *          the scalar to divide by
     * @return this
     */
    public Vector3f div(float scalar) {
        x /= scalar;
        y /= scalar;
        z /= scalar;
        return this;
    }

    /**
     * Divide all components of this {@link Vector3f} by the given scalar
     * value and store the result in <code>dest</code>.
     * 
     * @param scalar
     *          the scalar to divide by
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Vector3f div(float scalar, Vector3f dest) {
        dest.x = x / scalar;
        dest.y = y / scalar;
        dest.z = z / scalar;
        return dest;
    }

    /**
     * Divide the components of this Vector3f by the given scalar values and store the result in <code>this</code>.
     * 
     * @param x
     *          the x component to divide this vector by
     * @param y
     *          the y component to divide this vector by
     * @param z
     *          the z component to divide this vector by
     * @return this
     */
    public Vector3f div(float x, float y, float z) {
        this.x /= x;
        this.y /= y;
        this.z /= z;
        return this;
    }

    /**
     * Divide the components of this Vector3f by the given scalar values and store the result in <code>dest</code>.
     * 
     * @param x
     *          the x component to divide this vector by
     * @param y
     *          the y component to divide this vector by
     * @param z
     *          the z component to divide this vector by
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Vector3f div(float x, float y, float z, Vector3f dest) {
        dest.x = this.x / x;
        dest.y = this.y / y;
        dest.z = this.z / z;
        return dest;
    }

    /**
     * Rotate this vector by the given quaternion <code>quat</code> and store the result in <code>this</code>.
     * 
     * @see Quaternionf#transform(Vector3f)
     * 
     * @param quat
     *          the quaternion to rotate this vector
     * @return this
     */
    public Vector3f rotate(Quaternionf quat) {
        quat.transform(this, this);
        return this;
    }

    /**
     * Rotate this vector by the given quaternion <code>quat</code> and store the result in <code>dest</code>.
     * 
     * @see Quaternionf#transform(Vector3f)
     * 
     * @param quat
     *          the quaternion to rotate this vector
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Vector3f rotate(Quaternionf quat, Vector3f dest) {
        quat.transform(this, dest);
        return dest;
    }

    /**
     * Return the length squared of this vector.
     * 
     * @return the length squared
     */
    public float lengthSquared() {
        return x * x + y * y + z * z;
    }

    /**
     * Return the length of this vector.
     * 
     * @return the length
     */
    public float length() {
        return (float) Math.sqrt(lengthSquared());
    }

    /**
     * Normalize this vector.
     * 
     * @return this
     */
    public Vector3f normalize() {
        float invLength = 1.0f / length();
        x *= invLength;
        y *= invLength;
        z *= invLength;
        return this;
    }

    /**
     * Normalize this vector and store the result in <code>dest</code>.
     * 
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Vector3f normalize(Vector3f dest) {
        float invLength = 1.0f / length();
        dest.x = x * invLength;
        dest.y = y * invLength;
        dest.z = z * invLength;
        return dest;
    }

    /**
     * Set this vector to be the cross product of itself and <code>v</code>.
     * 
     * @param v
     *          the other vector
     * @return this
     */
    public Vector3f cross(Vector3f v) {
        return set(y * v.z - z * v.y,
                   z * v.x - x * v.z,
                   x * v.y - y * v.x);
    }

    /**
     * Set this vector to be the cross product of itself and <tt>(x, y, z)</tt>.
     * 
     * @param x
     *          the x component of the other vector
     * @param y
     *          the y component of the other vector
     * @param z
     *          the z component of the other vector
     * @return this
     */
    public Vector3f cross(float x, float y, float z) {
        return set(this.y * z - this.z * y,
                   this.z * x - this.x * z,
                   this.x * y - this.y * x);
    }

    /**
     * Compute the cross product of this vector and <code>v</code> and store the result in <code>dest</code>.
     * 
     * @param v
     *          the other vector
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Vector3f cross(Vector3f v, Vector3f dest) {
        return dest.set(y * v.z - z * v.y,
                        z * v.x - x * v.z,
                        x * v.y - y * v.x);
    }

    /**
     * Compute the cross product of this vector and <tt>(x, y, z)</tt> and store the result in <code>dest</code>.
     * 
     * @param x
     *          the x component of the other vector
     * @param y
     *          the y component of the other vector
     * @param z
     *          the z component of the other vector
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Vector3f cross(float x, float y, float z, Vector3f dest) {
        return dest.set(this.y * z - this.z * y,
                        this.z * x - this.x * z,
                        this.x * y - this.y * x);
    }

    /**
     * Return the distance between this Vector and <code>v</code>.
     * 
     * @param v
     *          the other vector
     * @return the distance
     */
    public float distance(Vector3f v) {
        float dx = v.x - x;
        float dy = v.y - y;
        float dz = v.z - z;
        return (float) Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    /**
     * Return the distance between <code>this</code> vector and <tt>(x, y, z)</tt>.
     * 
     * @param x
     *          the x component of the other vector
     * @param y
     *          the y component of the other vector
     * @param z
     *          the z component of the other vector
     * @return the euclidean distance
     */
    public float distance(float x, float y, float z) {
        float dx = this.x - x;
        float dy = this.y - y;
        float dz = this.z - z;
        return (float) Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    /**
     * Return the square of the distance between this vector and <code>v</code>.
     * 
     * @param v
     *          the other vector
     * @return the squared of the distance
     */
    public float distanceSquared(Vector3f v) {
        float dx = v.x - x;
        float dy = v.y - y;
        float dz = v.z - z;
        return dx * dx + dy * dy + dz * dz;
    }

    /**
     * Return the square of the distance between <code>this</code> vector and <tt>(x, y, z)</tt>.
     * 
     * @param x
     *          the x component of the other vector
     * @param y
     *          the y component of the other vector
     * @param z
     *          the z component of the other vector
     * @return the square of the distance
     */
    public float distanceSquared(float x, float y, float z) {
        float dx = this.x - x;
        float dy = this.y - y;
        float dz = this.z - z;
        return dx * dx + dy * dy + dz * dz;
    }

    /**
     * Return the dot product of this vector and the supplied vector.
     * 
     * @param v
     *          the other vector
     * @return the dot product
     */
    public float dot(Vector3f v) {
        return x * v.x + y * v.y + z * v.z;
    }

    /**
     * Return the dot product of this vector and the vector <tt>(x, y, z)</tt>.
     * 
     * @param x
     *          the x component of the other vector
     * @param y
     *          the y component of the other vector
     * @param z
     *          the z component of the other vector
     * @return the dot product
     */
    public float dot(float x, float y, float z) {
        return this.x * x + this.y * y + this.z * z;
    }

    /**
     * Return the cosine of the angle between this vector and the supplied vector. Use this instead of Math.cos(this.angle(v)).
     * 
     * @see #angle(Vector3f)
     * 
     * @param v
     *          the other vector
     * @return the cosine of the angle
     */
    public float angleCos(Vector3f v) {
        double length1Sqared = x * x + y * y + z * z;
        double length2Sqared = v.x * v.x + v.y * v.y + v.z * v.z;
        double dot = x * v.x + y * v.y + z * v.z;
        return (float) (dot / (Math.sqrt(length1Sqared * length2Sqared)));
    }

    /**
     * Return the angle between this vector and the supplied vector.
     * 
     * @see #angleCos(Vector3f)
     * 
     * @param v
     *          the other vector
     * @return the angle, in radians
     */
    public float angle(Vector3f v) {
        float cos = angleCos(v);
        // This is because sometimes cos goes above 1 or below -1 because of lost precision
        cos = cos < 1 ? cos : 1;
        cos = cos > -1 ? cos : -1;
        return (float) Math.acos(cos);
    }

    /**
     * Set the components of this vector to be the component-wise minimum of this and the other vector.
     *
     * @param v
     *          the other vector
     * @return this
     */
    public Vector3f min(Vector3f v) {
        this.x = x < v.x ? x : v.x;
        this.y = y < v.y ? y : v.y;
        this.z = z < v.z ? z : v.z;
        return this;
    }

    /**
     * Set the components of this vector to be the component-wise maximum of this and the other vector.
     *
     * @param v
     *          the other vector
     * @return this
     */
    public Vector3f max(Vector3f v) {
        this.x = x > v.x ? x : v.x;
        this.y = y > v.y ? y : v.y;
        this.z = z > v.z ? z : v.z;
        return this;
    }

    /**
     * Set all components to zero.
     * 
     * @return this
     */
    public Vector3f zero() {
        x = 0.0f;
        y = 0.0f;
        z = 0.0f;
        return this;
    }

    /**
     * Return a string representation of this vector.
     * <p>
     * This method creates a new {@link DecimalFormat} on every invocation with the format string "<tt> 0.000E0;-</tt>".
     * 
     * @return the string representation
     */
    public String toString() {
        DecimalFormat formatter = new DecimalFormat(" 0.000E0;-"); //$NON-NLS-1$
        return toString(formatter).replaceAll("E(\\d+)", "E+$1"); //$NON-NLS-1$ //$NON-NLS-2$
    }

    /**
     * Return a string representation of this vector by formatting the vector components with the given {@link NumberFormat}.
     * 
     * @param formatter
     *          the {@link NumberFormat} used to format the vector components with
     * @return the string representation
     */
    public String toString(NumberFormat formatter) {
        return "(" + formatter.format(x) + " " + formatter.format(y) + " " + formatter.format(z) + ")"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeFloat(x);
        out.writeFloat(y);
        out.writeFloat(z);
    }

    public void readExternal(ObjectInput in) throws IOException,
            ClassNotFoundException {
        x = in.readFloat();
        y = in.readFloat();
        z = in.readFloat();
    }

    /**
     * Negate this vector.
     * 
     * @return this
     */
    public Vector3f negate() {
        x = -x;
        y = -y;
        z = -z;
        return this;
    }

    /**
     * Negate this vector and store the result in <code>dest</code>.
     * 
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Vector3f negate(Vector3f dest) {
        dest.x = -x;
        dest.y = -y;
        dest.z = -z;
        return dest;
    }

    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Float.floatToIntBits(x);
        result = prime * result + Float.floatToIntBits(y);
        result = prime * result + Float.floatToIntBits(z);
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Vector3f other = (Vector3f) obj;
        if (Float.floatToIntBits(x) != Float.floatToIntBits(other.x))
            return false;
        if (Float.floatToIntBits(y) != Float.floatToIntBits(other.y))
            return false;
        if (Float.floatToIntBits(z) != Float.floatToIntBits(other.z))
            return false;
        return true;
    }

    /**
     * Reflect this vector about the given <code>normal</code> vector.
     * 
     * @param normal
     *          the vector to reflect about
     * @return this
     */
    public Vector3f reflect(Vector3f normal) {
        float dot = this.dot(normal);
        x = x - (dot + dot) * normal.x;
        y = y - (dot + dot) * normal.y;
        z = z - (dot + dot) * normal.z;
        return this;
    }

    /**
     * Reflect this vector about the given normal vector.
     * 
     * @param x
     *          the x component of the normal
     * @param y
     *          the y component of the normal
     * @param z
     *          the z component of the normal
     * @return this
     */
    public Vector3f reflect(float x, float y, float z) {
        float dot = this.dot(x, y, z);
        this.x = this.x - (dot + dot) * x;
        this.y = this.y - (dot + dot) * y;
        this.z = this.z - (dot + dot) * z;
        return this;
    }

    /**
     * Reflect this vector about the given <code>normal</code> vector and store the result in <code>dest</code>.
     * 
     * @param normal
     *          the vector to reflect about
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Vector3f reflect(Vector3f normal, Vector3f dest) {
        float dot = this.dot(normal);
        dest.x = x - (dot + dot) * normal.x;
        dest.y = y - (dot + dot) * normal.y;
        dest.z = z - (dot + dot) * normal.z;
        return dest;
    }

    /**
     * Reflect this vector about the given normal vector and store the result in <code>dest</code>.
     * 
     * @param x
     *          the x component of the normal
     * @param y
     *          the y component of the normal
     * @param z
     *          the z component of the normal
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Vector3f reflect(float x, float y, float z, Vector3f dest) {
        float dot = this.dot(x, y, z);
        dest.x = this.x - (dot + dot) * x;
        dest.y = this.y - (dot + dot) * y;
        dest.z = this.z - (dot + dot) * z;
        return dest;
    }

    /**
     * Compute the half vector between this and the other vector.
     * 
     * @param other
     *          the other vector
     * @return this
     */
    public Vector3f half(Vector3f other) {
        return this.add(other).normalize();
    }

    /**
     * Compute the half vector between this and the vector <tt>(x, y, z)</tt>.
     * 
     * @param x
     *          the x component of the other vector
     * @param y
     *          the y component of the other vector
     * @param z
     *          the z component of the other vector
     * @return this
     */
    public Vector3f half(float x, float y, float z) {
        return this.add(x, y, z).normalize();
    }

    /**
     * Compute the half vector between this and the other vector and store the result in <code>dest</code>.
     * 
     * @param other
     *          the other vector
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Vector3f half(Vector3f other, Vector3f dest) {
        return dest.set(this).add(other).normalize();
    }

    /**
     * Compute the half vector between this and the vector <tt>(x, y, z)</tt> 
     * and store the result in <code>dest</code>.
     * 
     * @param x
     *          the x component of the other vector
     * @param y
     *          the y component of the other vector
     * @param z
     *          the z component of the other vector
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Vector3f half(float x, float y, float z, Vector3f dest) {
        return dest.set(this).add(x, y, z).normalize();
    }

    /**
     * Compute a smooth-step (i.e. hermite with zero tangents) interpolation
     * between <code>this</code> vector and the given vector <code>v</code> and
     * store the result in <code>dest</code>.
     * 
     * @param v
     *          the other vector
     * @param t
     *          the interpolation factor, within <tt>[0..1]</tt>
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Vector3f smoothStep(Vector3f v, float t, Vector3f dest) {
        float t2 = t * t;
        float t3 = t2 * t;
        dest.x = (x + x - v.x - v.x) * t3 + (3.0f * v.x - 3.0f * x) * t2 + x * t + x;
        dest.y = (y + y - v.y - v.y) * t3 + (3.0f * v.y - 3.0f * y) * t2 + y * t + y;
        dest.z = (z + z - v.z - v.z) * t3 + (3.0f * v.z - 3.0f * z) * t2 + z * t + z;
        return dest;
    }

    /**
     * Compute a hermite interpolation between <code>this</code> vector with its
     * associated tangent <code>t0</code> and the given vector <code>v</code>
     * with its tangent <code>t1</code> and store the result in
     * <code>dest</code>.
     * 
     * @param t0
     *          the tangent of <code>this</code> vector
     * @param v1
     *          the other vector
     * @param t1
     *          the tangent of the other vector
     * @param t
     *          the interpolation factor, within <tt>[0..1]</tt>
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Vector3f hermite(Vector3f t0, Vector3f v1, Vector3f t1, float t, Vector3f dest) {
        float t2 = t * t;
        float t3 = t2 * t;
        dest.x = (x + x - v1.x - v1.x + t1.x + t0.x) * t3 + (3.0f * v1.x - 3.0f * x - t0.x - t0.x - t1.x) * t2 + x * t + x;
        dest.y = (y + y - v1.y - v1.y + t1.y + t0.y) * t3 + (3.0f * v1.y - 3.0f * y - t0.y - t0.y - t1.y) * t2 + y * t + y;
        dest.z = (z + z - v1.z - v1.z + t1.z + t0.z) * t3 + (3.0f * v1.z - 3.0f * z - t0.z - t0.z - t1.z) * t2 + z * t + z;
        return dest;
    }

    /**
     * Linearly interpolate <code>this</code> and <code>other</code> using the given interpolation factor <code>t</code>
     * and store the result in <code>this</code>.
     * <p>
     * If <code>t</code> is <tt>0.0</tt> then the result is <code>this</code>. If the interpolation factor is <code>1.0</code>
     * then the result is <code>other</code>.
     * 
     * @param other
     *          the other vector
     * @param t
     *          the interpolation factor between 0.0 and 1.0
     * @return this
     */
    public Vector3f lerp(Vector3f other, float t) {
        return lerp(other, t, this);
    }

    /**
     * Linearly interpolate <code>this</code> and <code>other</code> using the given interpolation factor <code>t</code>
     * and store the result in <code>dest</code>.
     * <p>
     * If <code>t</code> is <tt>0.0</tt> then the result is <code>this</code>. If the interpolation factor is <code>1.0</code>
     * then the result is <code>other</code>.
     * 
     * @param other
     *          the other vector
     * @param t
     *          the interpolation factor between 0.0 and 1.0
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Vector3f lerp(Vector3f other, float t, Vector3f dest) {
        dest.x = x + (other.x - x) * t;
        dest.y = y + (other.y - y) * t;
        dest.z = z + (other.z - z) * t;
        return dest;
    }

    /**
     * Get the value of the specified component of this vector.
     * 
     * @param component
     *          the component, within <tt>[0..2]</tt>
     * @return the value
     * @throws IllegalArgumentException if <code>component</code> is not within <tt>[0..2]</tt>
     */
    public float get(int component) throws IllegalArgumentException {
        switch (component) {
        case 0:
            return x;
        case 1:
            return y;
        case 2:
            return z;
        default:
            throw new IllegalArgumentException();
        }
    }

    /**
     * Set the value of the specified component of this vector.
     * 
     * @param component
     *          the component whose value to set, within <tt>[0..2]</tt>
     * @param value
     *          the value to set
     * @return this
     * @throws IllegalArgumentException if <code>component</code> is not within <tt>[0..2]</tt>
     */
    public Vector3f set(int component, float value) throws IllegalArgumentException {
        switch (component) {
        case 0:
            x = value;
            break;
        case 1:
            y = value;
            break;
        case 2:
            z = value;
            break;
        default:
            throw new IllegalArgumentException();
        }
        return this;
    }

    /**
     * Determine the component with the biggest absolute value.
     * 
     * @return the component index, within <tt>[0..2]</tt>
     */
    public int maxComponent() {
        float absX = Math.abs(x);
        float absY = Math.abs(y);
        float absZ = Math.abs(z);
        if (absX >= absY && absX >= absZ) {
            return 0;
        } else if (absY >= absZ) {
            return 1;
        }
        return 2;
    }

    /**
     * Determine the component with the smallest (towards zero) absolute value.
     * 
     * @return the component index, within <tt>[0..2]</tt>
     */
    public int minComponent() {
        float absX = Math.abs(x);
        float absY = Math.abs(y);
        float absZ = Math.abs(z);
        if (absX < absY && absX < absZ) {
            return 0;
        } else if (absY < absZ) {
            return 1;
        }
        return 2;
    }

}
