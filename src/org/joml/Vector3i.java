/*
 * (C) Copyright 2015 Richard Greenlees
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 *
 */
package org.joml;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Contains the definition of a Vector comprising 3 ints and associated
 * transformations.
 *
 * @author Richard Greenlees
 * @author Kai Burjack
 */
public class Vector3i implements Externalizable {

    private static final long serialVersionUID = 1L;

    /**
     * The x component of the vector.
     */
    public int x;
    /**
     * The y component of the vector.
     */
    public int y;
    /**
     * The z component of the vector.
     */
    public int z;

    /**
     * Create a new {@link Vector3i} of <tt>(0, 0, 0)</tt>.
     */
    public Vector3i() {
    }

    /**
     * Create a new {@link Vector3i} and initialize all three components with
     * the given value.
     *
     * @param d the value of all three components
     */
    public Vector3i(int d) {
        this(d, d, d);
    }

    /**
     * Create a new {@link Vector4i} with the given component values.
     *
     * @param x the value of x
     * @param y the value of y
     * @param z the value of z
     */
    public Vector3i(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Create a new {@link Vector3i} with the same values as <code>v</code>.
     *
     * @param v the {@link Vector3i} to copy the values from
     */
    public Vector3i(Vector3i v) {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
    }

    /**
     * Create a new {@link Vector3i} with the first two components from the
     * given <code>v</code> and the given <code>z</code>
     *
     * @param v the {@link Vector2i} to copy the values from
     * @param z the z component
     */
    public Vector3i(Vector2i v, int z) {
        this.x = v.x;
        this.y = v.y;
        this.z = z;
    }

    /**
     * Create a new {@link Vector3i} and read this vector from the supplied
     * {@link ByteBuffer} at the current buffer
     * {@link ByteBuffer#position() position}.
     * <p>
     * This method will not increment the position of the given ByteBuffer.
     * <p>
     * In order to specify the offset into the ByteBuffer at which the vector is
     * read, use {@link #Vector3i(int, ByteBuffer)}, taking the absolute
     * position as parameter.
     *
     * @param buffer values will be read in <tt>x, y, z</tt> order
     * @see #Vector3i(int, ByteBuffer)
     */
    public Vector3i(ByteBuffer buffer) {
        this(buffer.position(), buffer);
    }

    /**
     * Create a new {@link Vector3i} and read this vector from the supplied
     * {@link ByteBuffer} starting at the specified absolute buffer
     * position/index.
     * <p>
     * This method will not increment the position of the given ByteBuffer.
     *
     * @param index the absolute position into the ByteBuffer
     * @param buffer values will be read in <tt>x, y, z</tt> order
     */
    public Vector3i(int index, ByteBuffer buffer) {
        x = buffer.getInt(index);
        y = buffer.getInt(index + 4);
        z = buffer.getInt(index + 8);
    }

    /**
     * Create a new {@link Vector3i} and read this vector from the supplied
     * {@link IntBuffer} at the current buffer
     * {@link IntBuffer#position() position}.
     * <p>
     * This method will not increment the position of the given IntBuffer.
     * <p>
     * In order to specify the offset into the IntBuffer at which the vector is
     * read, use {@link #Vector3i(int, IntBuffer)}, taking the absolute position
     * as parameter.
     *
     * @param buffer values will be read in <tt>x, y, z</tt> order
     * @see #Vector3i(int, IntBuffer)
     */
    public Vector3i(IntBuffer buffer) {
        this(buffer.position(), buffer);
    }

    /**
     * Create a new {@link Vector3i} and read this vector from the supplied
     * {@link IntBuffer} starting at the specified absolute buffer
     * position/index.
     * <p>
     * This method will not increment the position of the given IntBuffer.
     *
     * @param index the absolute position into the IntBuffer
     * @param buffer values will be read in <tt>x, y, z</tt> order
     */
    public Vector3i(int index, IntBuffer buffer) {
        x = buffer.get(index);
        y = buffer.get(index + 1);
        z = buffer.get(index + 2);
    }

    /**
     * Set the x, y and z components to match the supplied vector.
     *
     * @param v contains the values of x, y and z to set
     * @return this
     */
    public Vector3i set(Vector3i v) {
        x = v.x;
        y = v.y;
        z = v.z;
        return this;
    }

    /**
     * Set the x, y and z components to match the supplied vector.
     * <p>
     * Note that due to the given vector <code>v</code> storing the components
     * in double-precision, there is the possibility to lose precision.
     *
     * @param v contains the values of x, y and z to set
     * @return this
     */
    public Vector3i set(Vector3d v) {
        x = (int) v.x;
        y = (int) v.y;
        z = (int) v.z;
        return this;
    }

    /**
     * Set the first two components from the given <code>v</code> and the z
     * component from the given <code>z</code>
     *
     * @param v the {@link Vector2i} to copy the values from
     * @param z the z component
     * @return this
     */
    public Vector3i set(Vector2i v, int z) {
        this.x = v.x;
        this.y = v.y;
        this.z = z;
        return this;
    }

    /**
     * Set the x, y, and z components to the supplied value.
     *
     * @param d the value of all three components
     * @return this
     */
    public Vector3i set(int d) {
        return set(d, d, d);
    }

    /**
     * Set the x, y and z components to the supplied values.
     *
     * @param x the x component
     * @param y the y component
     * @param z the z component
     * @return this
     */
    public Vector3i set(int x, int y, int z) {
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
     * In order to specify the offset into the ByteBuffer at which the vector is
     * read, use {@link #set(int, ByteBuffer)}, taking the absolute position as
     * parameter.
     *
     * @param buffer values will be read in <tt>x, y, z</tt> order
     * @return this
     * @see #set(int, ByteBuffer)
     */
    public Vector3i set(ByteBuffer buffer) {
        return set(buffer.position(), buffer);
    }

    /**
     * Read this vector from the supplied {@link ByteBuffer} starting at the
     * specified absolute buffer position/index.
     * <p>
     * This method will not increment the position of the given ByteBuffer.
     *
     * @param index the absolute position into the ByteBuffer
     * @param buffer values will be read in <tt>x, y, z</tt> order
     * @return this
     */
    public Vector3i set(int index, ByteBuffer buffer) {
        x = buffer.getInt(index);
        y = buffer.getInt(index + 4);
        z = buffer.getInt(index + 8);
        return this;
    }

    /**
     * Read this vector from the supplied {@link IntBuffer} at the current
     * buffer {@link IntBuffer#position() position}.
     * <p>
     * This method will not increment the position of the given IntBuffer.
     * <p>
     * In order to specify the offset into the IntBuffer at which the vector is
     * read, use {@link #set(int, IntBuffer)}, taking the absolute position as
     * parameter.
     *
     * @param buffer values will be read in <tt>x, y, z</tt> order
     * @return this
     * @see #set(int, IntBuffer)
     */
    public Vector3i set(IntBuffer buffer) {
        return set(buffer.position(), buffer);
    }

    /**
     * Read this vector from the supplied {@link IntBuffer} starting at the
     * specified absolute buffer position/index.
     * <p>
     * This method will not increment the position of the given IntBuffer.
     *
     * @param index the absolute position into the IntBuffer
     * @param buffer values will be read in <tt>x, y, z</tt> order
     * @return this
     */
    public Vector3i set(int index, IntBuffer buffer) {
        x = buffer.get(index);
        y = buffer.get(index + 1);
        z = buffer.get(index + 2);
        return this;
    }

    /**
     * Store this vector into the supplied {@link IntBuffer} at the current
     * buffer {@link IntBuffer#position() position}.
     * <p>
     * This method will not increment the position of the given IntBuffer.
     * <p>
     * In order to specify the offset into the IntBuffer at which the vector is
     * stored, use {@link #get(int, IntBuffer)}, taking the absolute position as
     * parameter.
     *
     * @see #get(int, IntBuffer)
     *
     * @param buffer will receive the values of this vector in <tt>x, y, z</tt>
     * order
     * @return the passed in buffer
     * @see #get(int, IntBuffer)
     */
    public IntBuffer get(IntBuffer buffer) {
        return get(buffer.position(), buffer);
    }

    /**
     * Store this vector into the supplied {@link IntBuffer} starting at the
     * specified absolute buffer position/index.
     * <p>
     * This method will not increment the position of the given IntBuffer.
     *
     * @param index the absolute position into the IntBuffer
     * @param buffer will receive the values of this vector in <tt>x, y, z</tt>
     * order
     * @return the passed in buffer
     */
    public IntBuffer get(int index, IntBuffer buffer) {
        buffer.put(index, x);
        buffer.put(index + 1, y);
        buffer.put(index + 2, z);
        return buffer;
    }

    /**
     * Store this vector into the supplied {@link ByteBuffer} at the current
     * buffer {@link ByteBuffer#position() position}.
     * <p>
     * This method will not increment the position of the given ByteBuffer.
     * <p>
     * In order to specify the offset into the ByteBuffer at which the vector is
     * stored, use {@link #get(int, ByteBuffer)}, taking the absolute position
     * as parameter.
     *
     * @see #get(int, ByteBuffer)
     *
     * @param buffer will receive the values of this vector in <tt>x, y, z</tt>
     * order
     * @return the passed in buffer
     * @see #get(int, ByteBuffer)
     */
    public ByteBuffer get(ByteBuffer buffer) {
        return get(buffer.position(), buffer);
    }

    /**
     * Store this vector into the supplied {@link ByteBuffer} starting at the
     * specified absolute buffer position/index.
     * <p>
     * This method will not increment the position of the given ByteBuffer.
     *
     * @param index the absolute position into the ByteBuffer
     * @param buffer will receive the values of this vector in <tt>x, y, z</tt>
     * order
     * @return the passed in buffer
     */
    public ByteBuffer get(int index, ByteBuffer buffer) {
        buffer.putInt(index, x);
        buffer.putInt(index + 4, y);
        buffer.putInt(index + 8, z);
        return buffer;
    }

    /**
     * Subtract the supplied vector from this one and store the result in
     * <code>this</code>.
     *
     * @param v the vector to subtract
     * @return this
     */
    public Vector3i sub(Vector3i v) {
        x -= v.x;
        y -= v.y;
        z -= v.z;
        return this;
    }

    /**
     * Subtract the supplied vector from this one and store the result in
     * <code>dest</code>.
     *
     * @param v the vector to subtract
     * @param dest will hold the result
     * @return dest
     */
    public Vector3i sub(Vector3i v, Vector3i dest) {
        dest.x = x - v.x;
        dest.y = y - v.y;
        dest.z = z - v.z;
        return dest;
    }

    /**
     * Decrement the components of this vector by the given values.
     *
     * @param x the x component to subtract
     * @param y the y component to subtract
     * @param z the z component to subtract
     * @return this
     */
    public Vector3i sub(int x, int y, int z) {
        this.x -= x;
        this.y -= y;
        this.z -= z;
        return this;
    }

    /**
     * Decrement the components of this vector by the given values and store the
     * result in <code>dest</code>.
     *
     * @param x the x component to subtract
     * @param y the y component to subtract
     * @param z the z component to subtract
     * @param dest will hold the result
     * @return dest
     */
    public Vector3i sub(int x, int y, int z, Vector3i dest) {
        dest.x = this.x - x;
        dest.y = this.y - y;
        dest.z = this.z - z;
        return dest;
    }

    /**
     * Add the supplied vector to this one.
     *
     * @param v the vector to add
     * @return this
     */
    public Vector3i add(Vector3i v) {
        x += v.x;
        y += v.y;
        z += v.z;
        return this;
    }

    /**
     * Add the supplied vector to this one and store the result in
     * <code>dest</code>.
     *
     * @param v the vector to add
     * @param dest will hold the result
     * @return dest
     */
    public Vector3i add(Vector3i v, Vector3i dest) {
        dest.x = x + v.x;
        dest.y = y + v.y;
        dest.z = z + v.z;
        return dest;
    }

    /**
     * Increment the components of this vector by the given values.
     *
     * @param x the x component to add
     * @param y the y component to add
     * @param z the z component to add
     * @return this
     */
    public Vector3i add(int x, int y, int z) {
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }

    /**
     * Increment the components of this vector by the given values and store the
     * result in <code>dest</code>.
     *
     * @param x the x component to add
     * @param y the y component to add
     * @param z the z component to add
     * @param dest will hold the result
     * @return dest
     */
    public Vector3i add(int x, int y, int z, Vector3i dest) {
        dest.x = this.x + x;
        dest.y = this.y + y;
        dest.z = this.z + z;
        return dest;
    }

    /**
     * Add the component-wise multiplication of <code>a * b</code> to this
     * vector.
     *
     * @param a the first multiplicand
     * @param b the second multiplicand
     * @return this
     */
    public Vector3i fma(Vector3i a, Vector3i b) {
        x += a.x * b.x;
        y += a.y * b.y;
        z += a.z * b.z;
        return this;
    }

    /**
     * Add the component-wise multiplication of <code>a * b</code> to this
     * vector.
     *
     * @param a the first multiplicand
     * @param b the second multiplicand
     * @return this
     */
    public Vector3i fma(int a, Vector3i b) {
        x += a * b.x;
        y += a * b.y;
        z += a * b.z;
        return this;
    }

    /**
     * Add the component-wise multiplication of <code>a * b</code> to this
     * vector and store the result in <code>dest</code>.
     *
     * @param a the first multiplicand
     * @param b the second multiplicand
     * @param dest will hold the result
     * @return dest
     */
    public Vector3i fma(Vector3i a, Vector3i b, Vector3i dest) {
        dest.x = x + a.x * b.x;
        dest.y = y + a.y * b.y;
        dest.z = z + a.z * b.z;
        return dest;
    }

    /**
     * Add the component-wise multiplication of <code>a * b</code> to this
     * vector and store the result in <code>dest</code>.
     *
     * @param a the first multiplicand
     * @param b the second multiplicand
     * @param dest will hold the result
     * @return dest
     */
    public Vector3i fma(int a, Vector3i b, Vector3i dest) {
        dest.x = x + a * b.x;
        dest.y = y + a * b.y;
        dest.z = z + a * b.z;
        return dest;
    }

    /**
     * Multiply this Vector3i component-wise by another Vector3i.
     *
     * @param v the vector to multiply by
     * @return this
     */
    public Vector3i mul(Vector3i v) {
        x *= v.x;
        y *= v.y;
        z *= v.z;
        return this;
    }

    /**
     * Multiply this Vector3i component-wise by another Vector3i and store the
     * result in <code>dest</code>.
     *
     * @param v the vector to multiply by
     * @param dest will hold the result
     * @return dest
     */
    public Vector3i mul(Vector3i v, Vector3i dest) {
        dest.x = x * v.x;
        dest.y = y * v.y;
        dest.z = z * v.z;
        return dest;
    }

    /**
     * Divide this Vector3i component-wise by another Vector3i.
     *
     * @param v the vector to divide by
     * @return this
     */
    public Vector3i div(Vector3i v) {
        x /= v.x;
        y /= v.y;
        z /= v.z;
        return this;
    }

    /**
     * Divide this Vector3i component-wise by another Vector3i and store the
     * result in <code>dest</code>.
     *
     * @param v the vector to divide by
     * @param dest will hold the result
     * @return dest
     */
    public Vector3i div(Vector3i v, Vector3i dest) {
        dest.x = x / v.x;
        dest.y = y / v.y;
        dest.z = z / v.z;
        return dest;
    }

    /**
     * Multiply all components of this {@link Vector3i} by the given scalar
     * value.
     *
     * @param scalar the scalar to multiply this vector by
     * @return this
     */
    public Vector3i mul(int scalar) {
        x *= scalar;
        y *= scalar;
        z *= scalar;
        return this;
    }

    /**
     * Multiply all components of this {@link Vector3i} by the given scalar
     * value and store the result in <code>dest</code>.
     *
     * @param scalar the scalar to multiply this vector by
     * @param dest will hold the result
     * @return dest
     */
    public Vector3i mul(int scalar, Vector3i dest) {
        dest.x = x * scalar;
        dest.y = y * scalar;
        dest.z = z * scalar;
        return dest;
    }

    /**
     * Multiply the components of this Vector3i by the given scalar values and
     * store the result in <code>this</code>.
     *
     * @param x the x component to multiply this vector by
     * @param y the y component to multiply this vector by
     * @param z the z component to multiply this vector by
     * @return this
     */
    public Vector3i mul(int x, int y, int z) {
        this.x *= x;
        this.y *= y;
        this.z *= z;
        return this;
    }

    /**
     * Multiply the components of this Vector3i by the given scalar values and
     * store the result in <code>dest</code>.
     *
     * @param x the x component to multiply this vector by
     * @param y the y component to multiply this vector by
     * @param z the z component to multiply this vector by
     * @param dest will hold the result
     * @return dest
     */
    public Vector3i mul(int x, int y, int z, Vector3i dest) {
        dest.x = this.x * x;
        dest.y = this.y * y;
        dest.z = this.z * z;
        return dest;
    }

    /**
     * Divide all components of this {@link Vector3i} by the given scalar value.
     *
     * @param scalar the scalar to divide by
     * @return this
     */
    public Vector3i div(int scalar) {
        x /= scalar;
        y /= scalar;
        z /= scalar;
        return this;
    }

    /**
     * Divide all components of this {@link Vector3i} by the given scalar value
     * and store the result in <code>dest</code>.
     *
     * @param scalar the scalar to divide by
     * @param dest will hold the result
     * @return dest
     */
    public Vector3i div(int scalar, Vector3i dest) {
        dest.x = x / scalar;
        dest.y = y / scalar;
        dest.z = z / scalar;
        return dest;
    }

    /**
     * Divide the components of this Vector3i by the given scalar values and
     * store the result in <code>this</code>.
     *
     * @param x the x component to divide this vector by
     * @param y the y component to divide this vector by
     * @param z the z component to divide this vector by
     * @return this
     */
    public Vector3i div(int x, int y, int z) {
        this.x /= x;
        this.y /= y;
        this.z /= z;
        return this;
    }

    /**
     * Divide the components of this Vector3i by the given scalar values and
     * store the result in <code>dest</code>.
     *
     * @param x the x component to divide this vector by
     * @param y the y component to divide this vector by
     * @param z the z component to divide this vector by
     * @param dest will hold the result
     * @return dest
     */
    public Vector3i div(int x, int y, int z, Vector3i dest) {
        dest.x = this.x / x;
        dest.y = this.y / y;
        dest.z = this.z / z;
        return dest;
    }

    /**
     * Return the length squared of this vector.
     *
     * @return the length squared
     */
    public int lengthSquared() {
        return x * x + y * y + z * z;
    }

    /**
     * Return the length of this vector.
     *
     * @return the length
     */
    public int length() {
        return (int) Math.sqrt(lengthSquared());
    }

    /**
     * Normalize this vector.
     *
     * @return this
     */
    public Vector3i normalize() {
        double invLength = 1.0f / length();
        x *= invLength;
        y *= invLength;
        z *= invLength;
        return this;
    }

    /**
     * Normalize this vector and store the result in <code>dest</code>.
     *
     * @param dest will hold the result
     * @return dest
     */
    public Vector3i normalize(Vector3i dest) {
        double invLength = 1.0f / length();
        dest.x = (int) (x * invLength);
        dest.y = (int) (y * invLength);
        dest.z = (int) (z * invLength);
        return dest;
    }

    /**
     * Set this vector to be the cross product of itself and <code>v</code>.
     *
     * @param v the other vector
     * @return this
     */
    public Vector3i cross(Vector3i v) {
        return set(y * v.z - z * v.y,
                z * v.x - x * v.z,
                x * v.y - y * v.x);
    }

    /**
     * Set this vector to be the cross product of itself and <tt>(x, y, z)</tt>.
     *
     * @param x the x component of the other vector
     * @param y the y component of the other vector
     * @param z the z component of the other vector
     * @return this
     */
    public Vector3i cross(int x, int y, int z) {
        return set(this.y * z - this.z * y,
                this.z * x - this.x * z,
                this.x * y - this.y * x);
    }

    /**
     * Compute the cross product of this vector and <code>v</code> and store the
     * result in <code>dest</code>.
     *
     * @param v the other vector
     * @param dest will hold the result
     * @return dest
     */
    public Vector3i cross(Vector3i v, Vector3i dest) {
        return dest.set(y * v.z - z * v.y,
                z * v.x - x * v.z,
                x * v.y - y * v.x);
    }

    /**
     * Compute the cross product of this vector and <tt>(x, y, z)</tt> and store
     * the result in <code>dest</code>.
     *
     * @param x the x component of the other vector
     * @param y the y component of the other vector
     * @param z the z component of the other vector
     * @param dest will hold the result
     * @return dest
     */
    public Vector3i cross(int x, int y, int z, Vector3i dest) {
        return dest.set(this.y * z - this.z * y,
                this.z * x - this.x * z,
                this.x * y - this.y * x);
    }

    /**
     * Return the distance between this Vector and <code>v</code>.
     *
     * @param v the other vector
     * @return the distance
     */
    public int distance(Vector3i v) {
        int dx = v.x - x;
        int dy = v.y - y;
        int dz = v.z - z;
        return (int) Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    /**
     * Return the distance between <code>this</code> vector and <tt>(x, y,
     * z)</tt>.
     *
     * @param x the x component of the other vector
     * @param y the y component of the other vector
     * @param z the z component of the other vector
     * @return the euclidean distance
     */
    public int distance(int x, int y, int z) {
        int dx = this.x - x;
        int dy = this.y - y;
        int dz = this.z - z;
        return (int) Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    /**
     * Return the square of the distance between this vector and <code>v</code>.
     *
     * @param v the other vector
     * @return the squared of the distance
     */
    public int distanceSquared(Vector3i v) {
        int dx = v.x - x;
        int dy = v.y - y;
        int dz = v.z - z;
        return dx * dx + dy * dy + dz * dz;
    }

    /**
     * Return the square of the distance between <code>this</code> vector and
     * <tt>(x, y, z)</tt>.
     *
     * @param x the x component of the other vector
     * @param y the y component of the other vector
     * @param z the z component of the other vector
     * @return the square of the distance
     */
    public int distanceSquared(int x, int y, int z) {
        int dx = this.x - x;
        int dy = this.y - y;
        int dz = this.z - z;
        return dx * dx + dy * dy + dz * dz;
    }

    /**
     * Return the dot product of this vector and the supplied vector.
     *
     * @param v the other vector
     * @return the dot product
     */
    public int dot(Vector3i v) {
        return x * v.x + y * v.y + z * v.z;
    }

    /**
     * Return the dot product of this vector and the vector <tt>(x, y, z)</tt>.
     *
     * @param x the x component of the other vector
     * @param y the y component of the other vector
     * @param z the z component of the other vector
     * @return the dot product
     */
    public int dot(int x, int y, int z) {
        return this.x * x + this.y * y + this.z * z;
    }

    /**
     * Return the cosine of the angle between this vector and the supplied
     * vector. Use this instead of Math.cos(this.angle(v)).
     *
     * @see #angle(Vector3i)
     *
     * @param v the other vector
     * @return the cosine of the angle
     */
    public int angleCos(Vector3i v) {
        double length1Sqared = x * x + y * y + z * z;
        double length2Sqared = v.x * v.x + v.y * v.y + v.z * v.z;
        double dot = x * v.x + y * v.y + z * v.z;
        return (int) (dot / (Math.sqrt(length1Sqared * length2Sqared)));
    }

    /**
     * Return the angle between this vector and the supplied vector.
     *
     * @see #angleCos(Vector3i)
     *
     * @param v the other vector
     * @return the angle, in radians
     */
    public int angle(Vector3i v) {
        int cos = angleCos(v);
        // This is because sometimes cos goes above 1 or below -1 because of lost precision
        cos = Math.min(cos, 1);
        cos = Math.max(cos, -1);
        return (int) Math.acos(cos);
    }

    /**
     * Set the components of this vector to be the component-wise minimum of
     * this and the other vector.
     *
     * @param v the other vector
     * @return this
     */
    public Vector3i min(Vector3i v) {
        this.x = Math.min(x, v.x);
        this.y = Math.min(y, v.y);
        this.z = Math.min(z, v.z);
        return this;
    }

    /**
     * Set the components of this vector to be the component-wise maximum of
     * this and the other vector.
     *
     * @param v the other vector
     * @return this
     */
    public Vector3i max(Vector3i v) {
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
    public Vector3i zero() {
        x = 0;
        y = 0;
        z = 0;
        return this;
    }

    /**
     * Return a string representation of this vector.
     * <p>
     * This method creates a new {@link DecimalFormat} on every invocation with
     * the format string "<tt> 0.000E0;-</tt>".
     *
     * @return the string representation
     */
    public String toString() {
        DecimalFormat formatter = new DecimalFormat(" 0;-"); //$NON-NLS-1$
        return toString(formatter).replaceAll("E(\\d+)", "E+$1"); //$NON-NLS-1$ //$NON-NLS-2$
    }

    /**
     * Return a string representation of this vector by formatting the vector
     * components with the given {@link NumberFormat}.
     *
     * @param formatter the {@link NumberFormat} used to format the vector
     * components with
     * @return the string representation
     */
    public String toString(NumberFormat formatter) {
        return "(" + formatter.format(x) + " " + formatter.format(y) + " " + formatter.format(z) + ")"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeInt(x);
        out.writeInt(y);
        out.writeInt(z);
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        x = in.readInt();
        y = in.readInt();
        z = in.readInt();
    }

    /**
     * Negate this vector.
     *
     * @return this
     */
    public Vector3i negate() {
        x = -x;
        y = -y;
        z = -z;
        return this;
    }

    /**
     * Negate this vector and store the result in <code>dest</code>.
     *
     * @param dest will hold the result
     * @return dest
     */
    public Vector3i negate(Vector3i dest) {
        dest.x = -x;
        dest.y = -y;
        dest.z = -z;
        return dest;
    }

    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + x;
        result = prime * result + y;
        result = prime * result + z;
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Vector3i other = (Vector3i) obj;
        if (x != other.x) {
            return false;
        }
        if (y != other.y) {
            return false;
        }
        if (z != other.z) {
            return false;
        }
        return true;
    }

}
