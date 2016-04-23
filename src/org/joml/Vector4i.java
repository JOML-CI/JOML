/*
 * (C) Copyright 2015-2016 Richard Greenlees
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
import java.text.NumberFormat;

/**
 * Contains the definition of a Vector comprising 4 ints and associated
 * transformations.
 *
 * @author Richard Greenlees
 * @author Kai Burjack
 * @author Hans Uhlig
 */
public class Vector4i implements Externalizable {

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
     * The w component of the vector.
     */
    public int w = 1;

    /**
     * Create a new {@link Vector4i} of <code>(0, 0, 0, 1)</code>.
     */
    public Vector4i() {
    }

    /**
     * Create a new {@link Vector4i} with the same values as <code>v</code>.
     *
     * @param v
     *          the {@link Vector4i} to copy the values from
     */
    public Vector4i(Vector4i v) {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
        this.w = v.w;
    }

    /**
     * Create a new {@link Vector4i} with the first three components from the
     * given <code>v</code> and the given <code>w</code>.
     *
     * @param v
     *          the {@link Vector3i}
     * @param w
     *          the w component
     */
    public Vector4i(Vector3i v, int w) {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
        this.w = w;
    }

    /**
     * Create a new {@link Vector4i} with the first two components from the
     * given <code>v</code> and the given <code>z</code>, and <code>w</code>.
     *
     * @param v
     *          the {@link Vector2i}
     * @param z
     *          the z component
     * @param w
     *          the w component
     */
    public Vector4i(Vector2i v, int z, int w) {
        this.x = v.x;
        this.y = v.y;
        this.z = z;
        this.w = w;
    }

    /**
     * Create a new {@link Vector4i} and initialize all four components with the
     * given value.
     *
     * @param s
     *          scalar value of all four components
     */
    public Vector4i(int s) {
        this.x = s;
        this.y = s;
        this.z = s;
        this.w = s;
    }

    /**
     * Create a new {@link Vector4i} with the given component values.
     *
     * @param x
     *          the x component
     * @param y
     *          the y component
     * @param z
     *          the z component
     * @param w
     *          the w component
     */
    public Vector4i(int x, int y, int z, int w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    /**
     * Create a new {@link Vector4i} and read this vector from the supplied
     * {@link ByteBuffer} at the current buffer
     * {@link ByteBuffer#position() position}.
     * <p>
     * This method will not increment the position of the given ByteBuffer.
     * <p>
     * In order to specify the offset into the ByteBuffer at which the vector is
     * read, use {@link #Vector4i(int, ByteBuffer)}, taking the absolute
     * position as parameter.
     *
     * @see #Vector4i(int, ByteBuffer)
     * 
     * @param buffer
     *          values will be read in <tt>x, y, z, w</tt> order
     */
    public Vector4i(ByteBuffer buffer) {
        this(buffer.position(), buffer);
    }

    /**
     * Create a new {@link Vector4i} and read this vector from the supplied
     * {@link ByteBuffer} starting at the specified absolute buffer
     * position/index.
     * <p>
     * This method will not increment the position of the given ByteBuffer.
     *
     * @param index
     *          the absolute position into the ByteBuffer
     * @param buffer
     *          values will be read in <tt>x, y, z, w</tt> order
     */
    public Vector4i(int index, ByteBuffer buffer) {
        x = buffer.getInt(index + 0);
        y = buffer.getInt(index + 4);
        z = buffer.getInt(index + 8);
        w = buffer.getInt(index + 12);
    }

    /**
     * Create a new {@link Vector4i} and read this vector from the supplied
     * {@link IntBuffer} at the current buffer
     * {@link IntBuffer#position() position}.
     * <p>
     * This method will not increment the position of the given IntBuffer.
     * <p>
     * In order to specify the offset into the IntBuffer at which the vector is
     * read, use {@link #Vector4i(int, IntBuffer)}, taking the absolute position
     * as parameter.
     *
     * @see #Vector4i(int, IntBuffer)
     *
     * @param buffer
     *          values will be read in <tt>x, y, z, w</tt> order
     */
    public Vector4i(IntBuffer buffer) {
        this(buffer.position(), buffer);
    }

    /**
     * Create a new {@link Vector4i} and read this vector from the supplied
     * {@link IntBuffer} starting at the specified absolute buffer
     * position/index.
     * <p>
     * This method will not increment the position of the given IntBuffer.
     *
     * @param index
     *          the absolute position into the IntBuffer
     * @param buffer
     *          values will be read in <tt>x, y, z, w</tt> order
     */
    public Vector4i(int index, IntBuffer buffer) {
        x = buffer.get(index + 0);
        y = buffer.get(index + 1);
        z = buffer.get(index + 2);
        w = buffer.get(index + 3);
    }

    /**
     * Set this {@link Vector4i} to the values of the given <code>v</code>.
     *
     * @param v
     *          the vector whose values will be copied into this
     * @return this
     */
    public Vector4i set(Vector4i v) {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
        this.w = v.w;
        return this;
    }

    /**
     * Set the first three components of this to the components of
     * <code>v</code> and the last component to <code>w</code>.
     *
     * @param v
     *          the {@link Vector3i} to copy
     * @param w
     *          the w component
     * @return this
     */
    public Vector4i set(Vector3i v, int w) {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
        this.w = w;
        return this;
    }

    /**
     * Sets the first two components of this to the components of given
     * <code>v</code> and last two components to the given <code>z</code>, and
     * <code>w</code>.
     *
     * @param v
     *          the {@link Vector2i}
     * @param z
     *          the z component
     * @param w
     *          the w component
     * @return this
     */
    public Vector4i set(Vector2i v, int z, int w) {
        this.x = v.x;
        this.y = v.y;
        this.z = z;
        this.w = w;
        return this;
    }

    /**
     * Set the x, y, z, and w components to the supplied value.
     *
     * @param s
     *          the value of all four components
     * @return this
     */
    public Vector4i set(int s) {
        this.x = s;
        this.y = s;
        this.z = s;
        this.w = s;
        return this;
    }

    /**
     * Set the x, y, z, and w components to the supplied values.
     *
     * @param x
     *          the x component
     * @param y
     *          the y component
     * @param z
     *          the z component
     * @param w
     *          the w component
     * @return this
     */
    public Vector4i set(int x, int y, int z, int w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
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
     * @see #set(int, ByteBuffer)
     *
     * @param buffer
     *          values will be read in <tt>x, y, z, w</tt> order
     * @return this
     */
    public Vector4i set(ByteBuffer buffer) {
        return set(buffer.position(), buffer);
    }

    /**
     * Read this vector from the supplied {@link ByteBuffer} starting at the
     * specified absolute buffer position/index.
     * <p>
     * This method will not increment the position of the given ByteBuffer.
     *
     * @param index
     *          the absolute position into the ByteBuffer
     * @param buffer
     *          values will be read in <tt>x, y, z, w</tt> order
     * @return this
     */
    public Vector4i set(int index, ByteBuffer buffer) {
        x = buffer.getInt(index);
        y = buffer.getInt(index + 4);
        z = buffer.getInt(index + 8);
        w = buffer.getInt(index + 12);
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
     * @see #set(int, IntBuffer)
     *
     * @param buffer
     *          values will be read in <tt>x, y, z, w</tt> order
     * @return this
     */
    public Vector4i set(IntBuffer buffer) {
        return set(buffer.position(), buffer);
    }

    /**
     * Read this vector from the supplied {@link IntBuffer} starting at the
     * specified absolute buffer position/index.
     * <p>
     * This method will not increment the position of the given IntBuffer.
     *
     * @param index
     *          the absolute position into the IntBuffer
     * @param buffer
     *          values will be read in <tt>x, y, z, w</tt> order
     * @return this
     */
    public Vector4i set(int index, IntBuffer buffer) {
        x = buffer.get(index + 0);
        y = buffer.get(index + 1);
        z = buffer.get(index + 2);
        w = buffer.get(index + 3);
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
     * @param buffer
     *          will receive the values of this vector in <tt>x, y, z, w</tt> order
     * @return the passed in buffer
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
     * @param index
     *          the absolute position into the IntBuffer
     * @param buffer
     *          will receive the values of this vector in <tt>x, y, z, w</tt> order
     * @return the passed in buffer
     */
    public IntBuffer get(int index, IntBuffer buffer) {
        buffer.put(index + 0, x);
        buffer.put(index + 1, y);
        buffer.put(index + 2, z);
        buffer.put(index + 3, w);
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
     * @param buffer
     *          will receive the values of this vector in <tt>x, y, z, w</tt> order
     * @return the passed in buffer
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
     * @param index
     *          the absolute position into the ByteBuffer
     * @param buffer
     *          will receive the values of this vector in <tt>x, y, z, w</tt> order
     * @return the passed in buffer
     */
    public ByteBuffer get(int index, ByteBuffer buffer) {
        buffer.putInt(index, x);
        buffer.putInt(index + 4, y);
        buffer.putInt(index + 8, z);
        buffer.putInt(index + 12, w);
        return buffer;
    }

    /**
     * Subtract the supplied vector from this one.
     *
     * @param v
     *          the vector to subtract
     * @return this
     */
    public Vector4i sub(Vector4i v) {
        x -= v.x;
        y -= v.y;
        z -= v.z;
        w -= v.w;
        return this;
    }

    /**
     * Subtract <tt>(x, y, z, w)</tt> from this.
     *
     * @param x
     *          the x component to subtract
     * @param y
     *          the y component to subtract
     * @param z
     *          the z component to subtract
     * @param w
     *          the w component to subtract
     * @return this
     */
    public Vector4i sub(int x, int y, int z, int w) {
        this.x -= x;
        this.y -= y;
        this.z -= z;
        this.w -= w;
        return this;
    }

    /**
     * Subtract the supplied vector from this one and store the result in
     * <code>dest</code>.
     *
     * @param v
     *          the vector to subtract from <code>this</code>
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Vector4i sub(Vector4i v, Vector4i dest) {
        dest.x = x - v.x;
        dest.y = y - v.y;
        dest.z = z - v.z;
        dest.w = w - v.w;
        return dest;
    }

    /**
     * Subtract <tt>(x, y, z, w)</tt> from this and store the result in
     * <code>dest</code>.
     *
     * @param x
     *          the x component to subtract
     * @param y
     *          the y component to subtract
     * @param z
     *          the z component to subtract
     * @param w
     *          the w component to subtract
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Vector4i sub(int x, int y, int z, int w, Vector4i dest) {
        dest.x = this.x - x;
        dest.y = this.y - y;
        dest.z = this.z - z;
        dest.w = this.w - w;
        return dest;
    }

    /**
     * Add the supplied vector to this one.
     *
     * @param v
     *          the vector to add
     * @return this
     */
    public Vector4i add(Vector4i v) {
        x += v.x;
        y += v.y;
        z += v.z;
        w += v.w;
        return this;
    }

    /**
     * Add the supplied vector to this one and store the result in
     * <code>dest</code>.
     *
     * @param v
     *          the vector to add
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Vector4i add(Vector4i v, Vector4i dest) {
        dest.x = x + v.x;
        dest.y = y + v.y;
        dest.z = z + v.z;
        dest.w = w + v.w;
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
     * @param w
     *          the w component to add
     * @return this
     */
    public Vector4i add(int x, int y, int z, int w) {
        this.x += x;
        this.y += y;
        this.z += z;
        this.w += w;
        return this;
    }

    /**
     * Increment the components of this vector by the given values and store the
     * result in <code>dest</code>.
     *
     * @param x
     *          the x component to add
     * @param y
     *          the y component to add
     * @param z
     *          the z component to add
     * @param w
     *          the w component to add
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Vector4i add(int x, int y, int z, int w, Vector4i dest) {
        dest.x = this.x + x;
        dest.y = this.y + y;
        dest.z = this.z + z;
        dest.w = this.w + w;
        return dest;
    }

    /**
     * Multiply this Vector4i component-wise by another Vector4i.
     *
     * @param v
     *          the other vector
     * @return this
     */
    public Vector4i mul(Vector4i v) {
        x *= v.x;
        y *= v.y;
        z *= v.z;
        w *= v.w;
        return this;
    }

    /**
     * Multiply this Vector4i component-wise by another Vector4i and store the
     * result in <code>dest</code>.
     *
     * @param v
     *          the other vector
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Vector4i mul(Vector4i v, Vector4i dest) {
        dest.x = x * v.x;
        dest.y = y * v.y;
        dest.z = z * v.z;
        dest.w = w * v.w;
        return dest;
    }

    /**
     * Divide this Vector4i component-wise by another Vector4i.
     *
     * @param v
     *          the vector to divide by
     * @return this
     */
    public Vector4i div(Vector4i v) {
        x /= v.x;
        y /= v.y;
        z /= v.z;
        w /= v.w;
        return this;
    }

    /**
     * Divide this Vector4i component-wise by another Vector4i and store the
     * result in <code>dest</code>.
     *
     * @param v
     *          the vector to divide by
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Vector4i div(Vector4i v, Vector4i dest) {
        dest.x = x / v.x;
        dest.y = y / v.y;
        dest.z = z / v.z;
        dest.w = w / v.w;
        return dest;
    }

    /**
     * Multiply all components of this {@link Vector4i} by the given scalar
     * value.
     *
     * @param scalar
     *          the scalar to multiply by
     * @return this
     */
    public Vector4i mul(float scalar) {
        x *= scalar;
        y *= scalar;
        z *= scalar;
        w *= scalar;
        return this;
    }

    /**
     * Multiply all components of this {@link Vector4i} by the given scalar
     * value and store the result in <code>dest</code>.
     *
     * @param scalar
     *          the scalar to multiply by
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Vector4i mul(float scalar, Vector4i dest) {
        dest.x = (int) (x * scalar);
        dest.y = (int) (y * scalar);
        dest.z = (int) (z * scalar);
        dest.w = (int) (w * scalar);
        return dest;
    }

    /**
     * Divide all components of this {@link Vector4i} by the given scalar value.
     *
     * @param scalar
     *          the scalar to divide by
     * @return this
     */
    public Vector4i div(int scalar) {
        x /= scalar;
        y /= scalar;
        z /= scalar;
        w /= scalar;
        return this;
    }

    /**
     * Divide all components of this {@link Vector4i} by the given scalar value
     * and store the result in <code>dest</code>.
     *
     * @param scalar
     *          the scalar to divide by
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Vector4i div(float scalar, Vector4i dest) {
        dest.x = (int) (x / scalar);
        dest.y = (int) (y / scalar);
        dest.z = (int) (z / scalar);
        dest.w = (int) (w / scalar);
        return dest;
    }

    /**
     * Return the length squared of this vector.
     *
     * @return the length squared
     */
    public long lengthSquared() {
        return x * x + y * y + z * z + w * w;
    }

    /**
     * Return the length of this vector.
     *
     * @return the length
     */
    public double length() {
        return Math.sqrt(lengthSquared());
    }

    /**
     * Return the distance between this Vector and <code>v</code>.
     *
     * @param v
     *          the other vector
     * @return the distance
     */
    public double distance(Vector4i v) {
        return Math.sqrt(distanceSquared(v));
    }

    /**
     * Return the distance between <code>this</code> vector and <tt>(x, y, z, w)</tt>.
     *
     * @param x
     *          the x component of the other vector
     * @param y
     *          the y component of the other vector
     * @param z
     *          the z component of the other vector
     * @param w
     *          the w component of the other vector
     * @return the euclidean distance
     */
    public double distance(int x, int y, int z, int w) {
        return Math.sqrt(distanceSquared(x, y, z, w));
    }

    /**
     * Return the square of the distance between this vector and <code>v</code>.
     *
     * @param v
     *          the other vector
     * @return the squared of the distance
     */
    public int distanceSquared(Vector4i v) {
        int dx = this.x - v.x;
        int dy = this.y - v.y;
        int dz = this.z - v.z;
        int dw = this.w - v.w;
        return dx * dx + dy * dy + dz * dz + dw * dw;
    }

    /**
     * Return the square of the distance between <code>this</code> vector and
     * <tt>(x, y, z, w)</tt>.
     *
     * @param x
     *          the x component of the other vector
     * @param y
     *          the y component of the other vector
     * @param z
     *          the z component of the other vector
     * @param w
     *          the w component of the other vector
     * @return the square of the distance
     */
    public int distanceSquared(int x, int y, int z, int w) {
        int dx = this.x - x;
        int dy = this.y - y;
        int dz = this.z - z;
        int dw = this.w - w;
        return dx * dx + dy * dy + dz * dz + dw * dw;
    }

    /**
     * Compute the dot product (inner product) of this vector and <code>v</code>.
     *
     * @param v
     *          the other vector
     * @return the dot product
     */
    public int dot(Vector4i v) {
        return x * v.x + y * v.y + z * v.z + w * v.w;
    }

    /**
     * Set all components to zero.
     *
     * @return this
     */
    public Vector4i zero() {
        x = 0;
        y = 0;
        z = 0;
        w = 0;
        return this;
    }

    /**
     * Negate this vector.
     *
     * @return this
     */
    public Vector4i negate() {
        x = -x;
        y = -y;
        z = -z;
        w = -w;
        return this;
    }

    /**
     * Negate this vector and store the result in <code>dest</code>.
     *
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Vector4i negate(Vector4i dest) {
        dest.x = -x;
        dest.y = -y;
        dest.z = -z;
        dest.w = -w;
        return dest;
    }

    /**
     * Return a string representation of this vector.
     *
     * @return the string representation
     */
    public String toString() {
        return "(" + x + " " + y + " " + z + " " + w + ")"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
    }

    /**
     * Return a string representation of this vector by formatting the vector
     * components with the given {@link NumberFormat}.
     *
     * @param formatter
     *          the {@link NumberFormat} used to format the vector components with
     * @return the string representation
     */
    public String toString(NumberFormat formatter) {
        return "(" + formatter.format(x) + " " + formatter.format(y) + " " + formatter.format(z) + " " + formatter.format(w) + ")"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeInt(x);
        out.writeInt(y);
        out.writeInt(z);
        out.writeInt(w);
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        x = in.readInt();
        y = in.readInt();
        z = in.readInt();
        w = in.readInt();
    }

    /**
     * Set the components of this vector to be the component-wise minimum of
     * this and the other vector.
     *
     * @param v
     *          the other vector
     * @return this
     */
    public Vector4i min(Vector4i v) {
        this.x = Math.min(x, v.x);
        this.y = Math.min(y, v.y);
        this.z = Math.min(z, v.z);
        this.w = Math.min(w, v.w);
        return this;
    }

    /**
     * Set the components of this vector to be the component-wise maximum of
     * this and the other vector.
     *
     * @param v
     *          the other vector
     * @return this
     */
    public Vector4i max(Vector4i v) {
        this.x = Math.max(x, v.x);
        this.y = Math.max(y, v.y);
        this.z = Math.max(z, v.z);
        this.w = Math.min(w, v.w);
        return this;
    }

    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + x;
        result = prime * result + y;
        result = prime * result + z;
        result = prime * result + w;
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
        Vector4i other = (Vector4i) obj;
        if (x != other.x) {
            return false;
        }
        if (y != other.y) {
            return false;
        }
        if (z != other.z) {
            return false;
        }
        if (w != other.w) {
            return false;
        }
        return true;
    }

}
