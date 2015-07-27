/*
 * (C) Copyright 2015 Richard Greenlees

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
     * The x-coordinate of the vector.
     */
    public float x;
    /**
     * The y-coordinate of the vector.
     */
    public float y;
    /**
     * The z-coordinate of the vector.
     */
    public float z;

    /**
     * Create a new {@link Vector3f} of <tt>(0, 0, 0)</tt>.
     */
    public Vector3f() {
    }

    /**
     * Create a new {@link Vector4f} with the given component values.
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
     *            the {@link Vector3f} to copy the values from
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
     *            the {@link Vector2f} to copy the values from
     * @param z
     *            the z value
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
     * If you want to specify the offset into the ByteBuffer at which
     * the vector is read, you can use {@link #Vector3f(int, ByteBuffer)}, taking
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
     * If you want to specify the offset into the FloatBuffer at which
     * the vector is read, you can use {@link #Vector3f(int, FloatBuffer)}, taking
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
     * Set the x, y and z attributes to match the supplied vector.
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
     * Set the first two components from the given <code>v</code>
     * and the z component from the given <code>z</code>
     *
     * @param v
     *            the {@link Vector2f} to copy the values from
     * @param z
     *            the z value
     * @return this
     */
    public Vector3f set(Vector2f v, float z) {
        this.x = v.x;
        this.y = v.y;
        this.z = z;
        return this;
    }

    /**
     * Set the x, y and z attributes to the supplied float values.
     * 
     * @param x
     *          the value of x
     * @param y
     *          the value of y
     * @param z
     *          the value of z
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
     * If you want to specify the offset into the ByteBuffer at which
     * the vector is read, you can use {@link #set(int, ByteBuffer)}, taking
     * the absolute position as parameter.
     *
     * @param buffer values will be read in <tt>x, y, z</tt> order
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
     * @param index  the absolute position into the ByteBuffer
     * @param buffer values will be read in <tt>x, y, z</tt> order
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
     * If you want to specify the offset into the FloatBuffer at which
     * the vector is read, you can use {@link #set(int, FloatBuffer)}, taking
     * the absolute position as parameter.
     *
     * @param buffer values will be read in <tt>x, y, z</tt> order
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
     * @param index  the absolute position into the FloatBuffer
     * @param buffer values will be read in <tt>x, y, z</tt> order
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
     * If you want to specify the offset into the FloatBuffer at which
     * the vector is stored, you can use {@link #get(int, FloatBuffer)}, taking
     * the absolute position as parameter.
     * 
     * @see #get(int, FloatBuffer)
     * 
     * @param buffer
     *            will receive the values of this vector in <tt>x, y, z</tt> order
     * @return this
     */
    public Vector3f get(FloatBuffer buffer) {
        return get(buffer.position(), buffer);
    }

    /**
     * Store this vector into the supplied {@link FloatBuffer} starting at the specified
     * absolute buffer position/index.
     * <p>
     * This method will not increment the position of the given FloatBuffer.
     * 
     * @param index
     *            the absolute position into the FloatBuffer
     * @param buffer
     *            will receive the values of this vector in <tt>x, y, z</tt> order
     * @return this
     */
    public Vector3f get(int index, FloatBuffer buffer) {
        buffer.put(index,    x);
        buffer.put(index+1,  y);
        buffer.put(index+2,  z);
        return this;
    }

    /**
     * Store this vector into the supplied {@link ByteBuffer} at the current
     * buffer {@link ByteBuffer#position() position}.
     * <p>
     * This method will not increment the position of the given ByteBuffer.
     * <p>
     * If you want to specify the offset into the ByteBuffer at which
     * the vector is stored, you can use {@link #get(int, ByteBuffer)}, taking
     * the absolute position as parameter.
     * 
     * @see #get(int, ByteBuffer)
     * 
     * @param buffer
     *            will receive the values of this vector in <tt>x, y, z</tt> order
     * @return this
     */
    public Vector3f get(ByteBuffer buffer) {
        return get(buffer.position(), buffer);
    }

    /**
     * Store this vector into the supplied {@link ByteBuffer} starting at the specified
     * absolute buffer position/index.
     * <p>
     * This method will not increment the position of the given ByteBuffer.
     * 
     * @param index
     *            the absolute position into the ByteBuffer
     * @param buffer
     *            will receive the values of this vector in <tt>x, y, z</tt> order
     * @return this
     */
    public Vector3f get(int index, ByteBuffer buffer) {
        buffer.putFloat(index,    x);
        buffer.putFloat(index+4,  y);
        buffer.putFloat(index+8,  z);
        return this;
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
     * @return this
     */
    public Vector3f sub(Vector3f v, Vector3f dest) {
        dest.x = x - v.x;
        dest.y = y - v.y;
        dest.z = z - v.z;
        return this;
    }

    /**
     * Decrement the components of this vector by the given values.
     * 
     * @param x
     *          the x-coordinate to subtract
     * @param y
     *          the y-coordinate to subtract
     * @param z
     *          the z-coordinate to subtract
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
     *          the x-coordinate to subtract
     * @param y
     *          the y-coordinate to subtract
     * @param z
     *          the z-coordinate to subtract
     * @param dest
     *          will hold the result
     * @return this
     */
    public Vector3f sub(float x, float y, float z, Vector3f dest) {
        dest.x = this.x - x;
        dest.y = this.y - y;
        dest.z = this.z - z;
        return this;
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
     * @return this
     */
    public Vector3f add(Vector3f v, Vector3f dest) {
        dest.x = x + v.x;
        dest.y = y + v.y;
        dest.z = z + v.z;
        return this;
    }

    /**
     * Increment the components of this vector by the given values.
     * 
     * @param x
     *          the x-coordinate to add
     * @param y
     *          the y-coordinate to add
     * @param z
     *          the z-coordinate to add
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
     *          the x-coordinate to add
     * @param y
     *          the y-coordinate to add
     * @param z
     *          the z-coordinate to add
     * @param dest
     *          will hold the result
     * @return this
     */
    public Vector3f add(float x, float y, float z, Vector3f dest) {
        dest.x = this.x + x;
        dest.y = this.y + y;
        dest.z = this.z + z;
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
     * @return this
     */
    public Vector3f fma(Vector3f a, Vector3f b, Vector3f dest) {
        dest.x = x + a.x * b.x;
        dest.y = y + a.y * b.y;
        dest.z = z + a.z * b.z;
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
     * @return this
     */
    public Vector3f fma(float a, Vector3f b, Vector3f dest) {
        dest.x = x + a * b.x;
        dest.y = y + a * b.y;
        dest.z = z + a * b.z;
        return this;
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
     * @return this
     */
    public Vector3f mul(Vector3f v, Vector3f dest) {
        dest.x = x * v.x;
        dest.y = y * v.y;
        dest.z = z * v.z;
        return this;
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
     * @return this
     */
    public Vector3f div(Vector3f v, Vector3f dest) {
        dest.x = x / v.x;
        dest.y = y / v.y;
        dest.z = z / v.z;
        return this;
    }

    /**
     * Multiply this Vector3f by the given matrix <code>mat</code> and store the result in <code>this</code>.
     * 
     * @param mat
     *          the matrix to multiply this vector by
     * @return this
     */
    public Vector3f mul(Matrix4f mat) {
        return mul(mat, this);
    }

    /**
     * Multiply this Vector3f by the given matrix <code>mat</code> and store the result in <code>dest</code>.
     * 
     * @param mat
     *          the matrix to multiply this vector by
     * @param dest
     *          will hold the result
     * @return this
     */
    public Vector3f mul(Matrix4f mat, Vector3f dest) {
        if (this != dest) {
            dest.x = mat.m00 * x + mat.m10 * y + mat.m20 * z;
            dest.y = mat.m01 * x + mat.m11 * y + mat.m21 * z;
            dest.z = mat.m02 * x + mat.m12 * y + mat.m22 * z;
        } else {
            dest.set(mat.m00 * x + mat.m10 * y + mat.m20 * z,
                     mat.m01 * x + mat.m11 * y + mat.m21 * z,
                     mat.m02 * x + mat.m12 * y + mat.m22 * z);
        }
        return this;
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
     * @return this
     */
    public Vector3f mul(Matrix3f mat, Vector3f dest) {
        if (this != dest) {
            dest.x = mat.m00 * x + mat.m10 * y + mat.m20 * z;
            dest.y = mat.m01 * x + mat.m11 * y + mat.m21 * z;
            dest.z = mat.m02 * x + mat.m12 * y + mat.m22 * z;
        } else {
            dest.set(mat.m00 * x + mat.m10 * y + mat.m20 * z,
                     mat.m01 * x + mat.m11 * y + mat.m21 * z,
                     mat.m02 * x + mat.m12 * y + mat.m22 * z);
        }
        return this;
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
     * @return this
     */
    public Vector3f mul(float scalar, Vector3f dest) {
        dest.x = x * scalar;
        dest.y = y * scalar;
        dest.z = z * scalar;
        return this;
    }

    /**
     * Multiply the components of this Vector3f by the given scalar values and store the result in <code>this</code>.
     * 
     * @param x
     *          the x-coordinate to multiply this vector by
     * @param y
     *          the y-coordinate to multiply this vector by
     * @param z
     *          the z-coordinate to multiply this vector by
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
     *          the x-coordinate to multiply this vector by
     * @param y
     *          the y-coordinate to multiply this vector by
     * @param z
     *          the z-coordinate to multiply this vector by
     * @param dest
     *          will hold the result
     * @return this
     */
    public Vector3f mul(float x, float y, float z, Vector3f dest) {
        dest.x = this.x * x;
        dest.y = this.y * y;
        dest.z = this.z * z;
        return this;
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
     * @return this
     */
    public Vector3f div(float scalar, Vector3f dest) {
        dest.x = x / scalar;
        dest.y = y / scalar;
        dest.z = z / scalar;
        return this;
    }

    /**
     * Divide the components of this Vector3f by the given scalar values and store the result in <code>this</code>.
     * 
     * @param x
     *          the x-coordinate to divide this vector by
     * @param y
     *          the y-coordinate to divide this vector by
     * @param z
     *          the z-coordinate to divide this vector by
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
     *          the x-coordinate to divide this vector by
     * @param y
     *          the y-coordinate to divide this vector by
     * @param z
     *          the z-coordinate to divide this vector by
     * @param dest
     *          will hold the result
     * @return this
     */
    public Vector3f div(float x, float y, float z, Vector3f dest) {
        dest.x = this.x / x;
        dest.y = this.y / y;
        dest.z = this.z / z;
        return this;
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
     * @return this
     */
    public Vector3f rotate(Quaternionf quat, Vector3f dest) {
        quat.transform(this, dest);
        return this;
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
        float d = length();
        x /= d;
        y /= d;
        z /= d;
        return this;
    }

    /**
     * Normalize this vector and store the result in <code>dest</code>.
     * 
     * @param dest
     *          will hold the result
     * @return this
     */
    public Vector3f normalize(Vector3f dest) {
        float d = length();
        dest.x = x / d;
        dest.y = y / d;
        dest.z = z / d;
        return this;
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
     *          the x-coordinate of the other vector
     * @param y
     *          the y-coordinate of the other vector
     * @param z
     *          the z-coordinate of the other vector
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
     * @return this
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
     *          the x-coordinate of the other vector
     * @param y
     *          the y-coordinate of the other vector
     * @param z
     *          the z-coordinate of the other vector
     * @param dest
     *          will hold the result
     * @return this
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
        return (float) Math.sqrt(
                  (v.x - this.x) * (v.x - this.x)
                + (v.y - this.y) * (v.y - this.y)
                + (v.z - this.z) * (v.z - this.z));
    }

    /**
     * Return the distance between <code>this</code> vector and <tt>(x, y, z)</tt>.
     * 
     * @param x
     *            the x-coordinate of the other vector
     * @param y
     *            the y-coordinate of the other vector
     * @param z
     *            the z-coordinate of the other vector
     * @return the euclidean distance
     */
    public float distance(float x, float y, float z) {
        return (float) Math.sqrt(
                (x - this.x) * (x - this.x)
              + (y - this.y) * (y - this.y)
              + (z - this.z) * (z - this.z));
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
     *          the x-coordinate of the other vector
     * @param y
     *          the y-coordinate of the other vector
     * @param z
     *          the z-coordinate of the other vector
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
        double length1 = Math.sqrt(x * x + y * y + z * z);
        double length2 = Math.sqrt(v.x * v.x + v.y * v.y + v.z * v.z);
        double dot = x * v.x + y * v.y + z * v.z;
        return (float) (dot / (length1 * length2));
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
        cos = Math.min(cos, 1);
        cos = Math.max(cos, -1);
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
        this.x = Math.min(x, v.x);
        this.y = Math.min(y, v.y);
        this.z = Math.min(z, v.z);
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
        this.x = Math.max(x, v.x);
        this.y = Math.max(y, v.y);
        this.z = Math.max(z, v.z);
        return this;
    }

    /**
     * Set all components to zero.
     * 
     * @return this
     */
    public Vector3f zero() {
        this.x = 0.0f;
        this.y = 0.0f;
        this.z = 0.0f;
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
     * @return this
     */
    public Vector3f negate(Vector3f dest) {
        dest.x = -x;
        dest.y = -y;
        dest.z = -z;
        return this;
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
     *             the vector to reflect about
     * @return this
     */
    public Vector3f reflect(Vector3f normal) {
        float dot = this.dot(normal);
        x = x - 2.0f * dot * normal.x;
        y = y - 2.0f * dot * normal.y;
        z = z - 2.0f * dot * normal.z;
        return this;
    }

    /**
     * Reflect this vector about the given normal vector.
     * 
     * @param x
     *             the x-coordinate of the normal
     * @param y
     *             the y-coordinate of the normal
     * @param z
     *             the z-coordinate of the normal
     * @return this
     */
    public Vector3f reflect(float x, float y, float z) {
        float dot = this.dot(x, y, z);
        this.x = this.x - 2.0f * dot * x;
        this.y = this.y - 2.0f * dot * y;
        this.z = this.z - 2.0f * dot * z;
        return this;
    }

    /**
     * Reflect this vector about the given <code>normal</code> vector and store the result in <code>dest</code>.
     * 
     * @param normal
     *             the vector to reflect about
     * @param dest
     *             will hold the result
     * @return this
     */
    public Vector3f reflect(Vector3f normal, Vector3f dest) {
        float dot = this.dot(normal);
        dest.x = x - 2.0f * dot * normal.x;
        dest.y = y - 2.0f * dot * normal.y;
        dest.z = z - 2.0f * dot * normal.z;
        return this;
    }

    /**
     * Reflect this vector about the given normal vector and store the result in <code>dest</code>.
     * 
     * @param x
     *             the x-coordinate of the normal
     * @param y
     *             the y-coordinate of the normal
     * @param z
     *             the z-coordinate of the normal
     * @param dest
     *             will hold the result
     * @return this
     */
    public Vector3f reflect(float x, float y, float z, Vector3f dest) {
        float dot = this.dot(x, y, z);
        dest.x = this.x - 2.0f * dot * x;
        dest.y = this.y - 2.0f * dot * y;
        dest.z = this.z - 2.0f * dot * z;
        return this;
    }

    /**
     * Compute the half vector between this and the other vector.
     * 
     * @param other
     *             the other vector
     * @return this
     */
    public Vector3f half(Vector3f other) {
        return this.add(other).normalize();
    }

    /**
     * Compute the half vector between this and the vector <tt>(x, y, z)</tt>.
     * 
     * @param x
     *             the x-coordinate of the other vector
     * @param y
     *             the y-coordinate of the other vector
     * @param z
     *             the z-coordinate of the other vector
     * @return this
     */
    public Vector3f half(float x, float y, float z) {
        return this.add(x, y, z).normalize();
    }

    /**
     * Compute the half vector between this and the other vector and store the result in <code>dest</code>.
     * 
     * @param other
     *             the other vector
     * @param dest
     *             will hold the result
     * @return this
     */
    public Vector3f half(Vector3f other, Vector3f dest) {
        dest.set(this).add(other).normalize();
        return this;
    }

    /**
     * Compute the half vector between this and the vector <tt>(x, y, z)</tt> 
     * and store the result in <code>dest</code>.
     * 
     * @param x
     *             the x-coordinate of the other vector
     * @param y
     *             the y-coordinate of the other vector
     * @param z
     *             the z-coordinate of the other vector
     * @param dest
     *             will hold the result
     * @return this
     */
    public Vector3f half(float x, float y, float z, Vector3f dest) {
        dest.set(this).add(x, y, z).normalize();
        return this;
    }

    /**
     * Compute a smooth-step (i.e. hermite with zero tangents) interpolation
     * between <code>this</code> vector and the given vector <code>v</code> and
     * store the result in <code>dest</code>.
     * 
     * @param v
     *            the other vector
     * @param t
     *            the interpolation factor, within <tt>[0..1]</tt>
     * @param dest
     *            will hold the result
     * @return this
     */
    public Vector3f smoothStep(Vector3f v, float t, Vector3f dest) {
        dest.x = (float) Interpolate.smoothStep(x, v.x, t);
        dest.y = (float) Interpolate.smoothStep(y, v.y, t);
        dest.z = (float) Interpolate.smoothStep(x, v.z, t);
        return this;
    }

    /**
     * Compute a hermite interpolation between <code>this</code> vector with its
     * associated tangent <code>t0</code> and the given vector <code>v</code>
     * with its tangent <code>t1</code> and store the result in
     * <code>dest</code>.
     * 
     * @param t0
     *            the tangent of <code>this</code> vector
     * @param v1
     *            the other vector
     * @param t1
     *            the tangent of the other vector
     * @param t
     *            the interpolation factor, within <tt>[0..1]</tt>
     * @param dest
     *            will hold the result
     * @return this
     */
    public Vector3f hermite(Vector3f t0, Vector3f v1, Vector3f t1, float t, Vector3f dest) {
        dest.x = (float) Interpolate.hermite(x, t0.x, v1.x, t1.x, t);
        dest.y = (float) Interpolate.hermite(y, t0.y, v1.y, t1.y, t);
        dest.z = (float) Interpolate.hermite(z, t0.z, v1.z, t1.z, t);
        return this;
    }

    /**
     * Return the specified {@link Vector3f}.
     * <p>
     * When using method chaining in a fluent interface style, this method can be used to switch
     * the <i>context object</i>, on which further method invocations operate, to be the given vector.
     * 
     * @param v
     *          the {@link Vector3f} to return
     * @return that vector
     */
    public Vector3f with(Vector3f v) {
        return v;
    }

    /**
     * Return the specified {@link Vector4f}.
     * <p>
     * When using method chaining in a fluent interface style, this method can be used to switch
     * the <i>context object</i>, on which further method invocations operate, to be the given vector.
     * 
     * @param v
     *          the {@link Vector4f} to return
     * @return that vector
     */
    public Vector4f with(Vector4f v) {
        return v;
    }

    /**
     * Return the specified {@link Quaternionf}.
     * <p>
     * When using method chaining in a fluent interface style, this method can be used to switch
     * the <i>context object</i>, on which further method invocations operate, to be the given quaternion.
     * 
     * @param q
     *          the {@link Quaternionf} to return
     * @return that quaternion
     */
    public Quaternionf with(Quaternionf q) {
        return q;
    }

    /**
     * Return the specified {@link AxisAngle4f}.
     * <p>
     * When using method chaining in a fluent interface style, this method can be used to switch
     * the <i>context object</i>, on which further method invocations operate, to be the given {@link AxisAngle4f}.
     * 
     * @param a
     *          the {@link AxisAngle4f} to return
     * @return that quaternion
     */
    public AxisAngle4f with(AxisAngle4f a) {
        return a;
    }

    /**
     * Return the specified {@link Matrix3f}.
     * <p>
     * When using method chaining in a fluent interface style, this method can be used to switch
     * the <i>context object</i>, on which further method invocations operate, to be the given matrix.
     * 
     * @param m
     *          the {@link Matrix3f} to return
     * @return that matrix
     */
    public Matrix3f with(Matrix3f m) {
        return m;
    }

    /**
     * Return the specified {@link Matrix4f}.
     * <p>
     * When using method chaining in a fluent interface style, this method can be used to switch
     * the <i>context object</i>, on which further method invocations operate, to be the given matrix.
     * 
     * @param m
     *          the {@link Matrix4f} to return
     * @return that matrix
     */
    public Matrix4f with(Matrix4f m) {
        return m;
    }

}
