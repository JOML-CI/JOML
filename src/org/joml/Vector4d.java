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
import java.nio.DoubleBuffer;


/**
 * Contains the definition of a Vector comprising 4 doubles and associated transformations.
 * 
 * @author Richard Greenlees
 * @author Kai Burjack
 */
public class Vector4d extends Vector4dr implements Externalizable {

    private static final long serialVersionUID = 1L;   

    /**
     * The x component of the vector.
     */
    public double x;
    /**
     * The y component of the vector.
     */
    public double y;
    /**
     * The z component of the vector.
     */
    public double z;
    /**
     * The w component of the vector.
     */
    public double w = 1.0;

    /**
     * Create a new {@link Vector4d} of <code>(0, 0, 0, 1)</code>.
     */
    public Vector4d() {
    }

    /**
     * Create a new {@link Vector4d} with the same values as <code>v</code>.
     * 
     * @param v
     *          the {@link Vector4dr} to copy the values from
     */
    public Vector4d(Vector4dr v) {
        this.x = v.x();
        this.y = v.y();
        this.z = v.z();
        this.w = v.w();
    }

    /**
     * Create a new {@link Vector4d} with the first three components from the
     * given <code>v</code> and the given <code>w</code>.
     * 
     * @param v
     *          the {@link Vector3dr}
     * @param w
     *          the w component
     */
    public Vector4d(Vector3dr v, double w) {
        this.x = v.x();
        this.y = v.y();
        this.z = v.z();
        this.w = w;
    }

    /**
     * Create a new {@link Vector4d} with the first two components from the
     * given <code>v</code> and the given <code>z</code> and <code>w</code>.
     *
     * @param v
     *          the {@link Vector2dr}
     * @param z
     *          the z component
     * @param w
     *          the w component
     */
    public Vector4d(Vector2dr v, double z, double w) {
        this.x = v.x();
        this.y = v.y();
        this.z = z;
        this.w = w;
    }

    /**
     * Create a new {@link Vector4d} with the same values as <code>v</code>.
     * 
     * @param v
     *          the {@link Vector4fr} to copy the values from
     */
    public Vector4d(Vector4fr v) {
        this.x = v.x();
        this.y = v.y();
        this.z = v.z();
        this.w = v.w();
    }

    /**
     * Create a new {@link Vector4d} with the x, y, and z components from the
     * given <code>v</code> and the w component from the given <code>w</code>.
     * 
     * @param v
     *          the {@link Vector3fr}
     * @param w
     *          the w component
     */
    public Vector4d(Vector3fr v, double w) {
        this.x = v.x();
        this.y = v.y();
        this.z = v.z();
        this.w = w;
    }

    /**
     * Create a new {@link Vector4d} with the x and y components from the
     * given <code>v</code> and the z and w components from the given <code>z</code> and <code>w</code>.
     *
     * @param v
     *          the {@link Vector2fr}
     * @param z
     *          the z component
     * @param w
     *          the w component
     */
    public Vector4d(Vector2fr v, double z, double w) {
        this.x = v.x();
        this.y = v.y();
        this.z = z;
        this.w = w;
    }

    /**
     * Create a new {@link Vector4d} and initialize all four components with the given value.
     *
     * @param d
     *          the value of all four components
     */
    public Vector4d(double d) {
        this(d, d, d, d);
    }

    /**
     * Create a new {@link Vector4d} with the given component values.
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
    public Vector4d(double x, double y, double z, double w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    /**
     * Create a new {@link Vector4d} and read this vector from the supplied {@link ByteBuffer}
     * at the current buffer {@link ByteBuffer#position() position}.
     * <p>
     * This method will not increment the position of the given ByteBuffer.
     * <p>
     * In order to specify the offset into the ByteBuffer at which
     * the vector is read, use {@link #Vector4d(int, ByteBuffer)}, taking
     * the absolute position as parameter.
     *
     * @param buffer
     *          values will be read in <tt>x, y, z, w</tt> order
     * @see #Vector4d(int, ByteBuffer)
     */
    public Vector4d(ByteBuffer buffer) {
        this(buffer.position(), buffer);
    }

    /**
     * Create a new {@link Vector4d} and read this vector from the supplied {@link ByteBuffer}
     * starting at the specified absolute buffer position/index.
     * <p>
     * This method will not increment the position of the given ByteBuffer.
     *
     * @param index  the absolute position into the ByteBuffer
     * @param buffer values will be read in <tt>x, y, z, w</tt> order
     */
    public Vector4d(int index, ByteBuffer buffer) {
        x = buffer.getDouble(index);
        y = buffer.getDouble(index + 8);
        z = buffer.getDouble(index + 16);
        w = buffer.getDouble(index + 24);
    }

    /**
     * Create a new {@link Vector4d} and read this vector from the supplied {@link DoubleBuffer}
     * at the current buffer {@link DoubleBuffer#position() position}.
     * <p>
     * This method will not increment the position of the given DoubleBuffer.
     * <p>
     * In order to specify the offset into the DoubleBuffer at which
     * the vector is read, use {@link #Vector4d(int, DoubleBuffer)}, taking
     * the absolute position as parameter.
     *
     * @param buffer values will be read in <tt>x, y, z, w</tt> order
     * @see #Vector4d(int, DoubleBuffer)
     */
    public Vector4d(DoubleBuffer buffer) {
        this(buffer.position(), buffer);
    }

    /**
     * Create a new {@link Vector4d} and read this vector from the supplied {@link DoubleBuffer}
     * starting at the specified absolute buffer position/index.
     * <p>
     * This method will not increment the position of the given DoubleBuffer.
     *
     * @param index  the absolute position into the DoubleBuffer
     * @param buffer values will be read in <tt>x, y, z, w</tt> order
     */
    public Vector4d(int index, DoubleBuffer buffer) {
        x = buffer.get(index);
        y = buffer.get(index + 1);
        z = buffer.get(index + 2);
        w = buffer.get(index + 3);
    }

    public double x() {
        return x;
    }

    public double y() {
        return y;
    }

    public double z() {
        return z;
    }

    public double w() {
        return w;
    }

    /**
     * Set this {@link Vector4dr} to the values of the given <code>v</code>.
     * 
     * @param v
     *          the vector whose values will be copied into this
     * @return this
     */
    public Vector4d set(Vector4dr v) {
        this.x = v.x();
        this.y = v.y();
        this.z = v.z();
        this.w = v.w();
        return this;
    }

    /**
     * Set this {@link Vector4d} to the values of the given <code>v</code>.
     * 
     * @param v
     *          the vector whose values will be copied into this
     * @return this
     */
    public Vector4d set(Vector4fr v) {
        this.x = v.x();
        this.y = v.y();
        this.z = v.z();
        this.w = v.w();
        return this;
    }

    /**
     * Set the x, y, and z components of this to the components of
     * <code>v</code> and the w component to <code>w</code>.
     * 
     * @param v
     *          the {@link Vector3dr} to copy
     * @param w
     *          the w component
     * @return this
     */
    public Vector4d set(Vector3dr v, double w) {
        this.x = v.x();
        this.y = v.y();
        this.z = v.z();
        this.w = w;
        return this;
    }

    /**
     * Set the x, y, and z components of this to the components of
     * <code>v</code> and the w component to <code>w</code>.
     * 
     * @param v
     *          the {@link Vector3fr} to copy
     * @param w
     *          the w component
     * @return this
     */
    public Vector4d set(Vector3fr v, double w) {
        this.x = v.x();
        this.y = v.y();
        this.z = v.z();
        this.w = w;
        return this;
    }

    /**
     * Set the x and y components from the given <code>v</code>
     * and the z and w components to the given <code>z</code> and <code>w</code>.
     *
     * @param v
     *          the {@link Vector2dr}
     * @param z
     *          the z component
     * @param w
     *          the w component
     * @return this
     */
    public Vector4d set(Vector2dr v, double z, double w) {
        this.x = v.x();
        this.y = v.y();
        this.z = z;
        this.w = w;
        return this;
    }
    
    /**
     * Set the x and y components from the given <code>v</code>
     * and the z and w components to the given <code>z</code> and <code>w</code>.
     *
     * @param v
     *          the {@link Vector2fr}
     * @param z
     *          the z components
     * @param w
     *          the w components
     * @return this
     */
    public Vector4d set(Vector2fr v, double z, double w) {
        this.x = v.x();
        this.y = v.y();
        this.z = z;
        this.w = w;
        return this;
    }

    /**
     * Set the x, y, z, and w components to the supplied value.
     *
     * @param d
     *          the value of all four components
     * @return this
     */
    public Vector4d set(double d) {
        return set(d, d, d, d);
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
    public Vector4d set(double x, double y, double z, double w) {
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
     * In order to specify the offset into the ByteBuffer at which
     * the vector is read, use {@link #set(int, ByteBuffer)}, taking
     * the absolute position as parameter.
     *
     * @param buffer
     *          values will be read in <tt>x, y, z, w</tt> order
     * @return this
     * @see #set(int, ByteBuffer)
     */
    public Vector4d set(ByteBuffer buffer) {
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
     *          values will be read in <tt>x, y, z, w</tt> order
     * @return this
     */
    public Vector4d set(int index, ByteBuffer buffer) {
        x = buffer.getDouble(index);
        y = buffer.getDouble(index + 8);
        z = buffer.getDouble(index + 16);
        w = buffer.getDouble(index + 24);
        return this;
    }

    /**
     * Read this vector from the supplied {@link DoubleBuffer} at the current
     * buffer {@link DoubleBuffer#position() position}.
     * <p>
     * This method will not increment the position of the given DoubleBuffer.
     * <p>
     * In order to specify the offset into the DoubleBuffer at which
     * the vector is read, use {@link #set(int, DoubleBuffer)}, taking
     * the absolute position as parameter.
     *
     * @param buffer
     *          values will be read in <tt>x, y, z, w</tt> order
     * @return this
     * @see #set(int, DoubleBuffer)
     */
    public Vector4d set(DoubleBuffer buffer) {
        return set(buffer.position(), buffer);
    }

    /**
     * Read this vector from the supplied {@link DoubleBuffer} starting at the specified
     * absolute buffer position/index.
     * <p>
     * This method will not increment the position of the given DoubleBuffer.
     *
     * @param index
     *          the absolute position into the DoubleBuffer
     * @param buffer
     *          values will be read in <tt>x, y, z, w</tt> order
     * @return this
     */
    public Vector4d set(int index, DoubleBuffer buffer) {
        x = buffer.get(index);
        y = buffer.get(index + 1);
        z = buffer.get(index + 2);
        w = buffer.get(index + 3);
        return this;
    }

    /**
     * Subtract the supplied vector from this one.
     * 
     * @param v
     *          the vector to subtract
     * @return this
     */
    public Vector4d sub(Vector4dr v) {
        x -= v.x();
        y -= v.y();
        z -= v.z();
        w -= v.w();
        return this;
    }

    /**
     * Subtract the supplied vector from this one.
     * 
     * @param v
     *          the vector to subtract
     * @return this
     */
    public Vector4d sub(Vector4fr v) {
        x -= v.x();
        y -= v.y();
        z -= v.z();
        w -= v.w();
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
    public Vector4d sub(double x, double y, double z, double w) {
        this.x -= x;
        this.y -= y;
        this.z -= z;
        this.w -= w;
        return this;
    }

    /**
     * Add the supplied vector to this one.
     * 
     * @param v
     *          the vector to add
     * @return this
     */
    public Vector4d add(Vector4dr v) {
        x += v.x();
        y += v.y();
        z += v.z();
        w += v.w();
        return this;
    }

    /**
     * Add <tt>(x, y, z, w)</tt> to this.
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
    public Vector4d add(double x, double y, double z, double w) {
        this.x += x;
        this.y += y;
        this.z += z;
        this.w += w;
        return this;
    }

    /**
     * Add the supplied vector to this one.
     * 
     * @param v
     *          the vector to add
     * @return this
     */
    public Vector4d add(Vector4fr v) {
        x += v.x();
        y += v.y();
        z += v.z();
        w += v.w();
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
    public Vector4d fma(Vector4dr a, Vector4dr b) {
        x += a.x() * b.x();
        y += a.y() * b.y();
        z += a.z() * b.z();
        w += a.w() * b.w();
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
    public Vector4d fma(double a, Vector4dr b) {
        x += a * b.x();
        y += a * b.y();
        z += a * b.z();
        w += a * b.w();
        return this;
    }

    /**
     * Multiply this {@link Vector4d} component-wise by the given {@link Vector4dr}.
     * 
     * @param v
     *          the vector to multiply by
     * @return this
     */
    public Vector4d mul(Vector4dr v) {
        x *= v.x();
        y *= v.y();
        z *= v.z();
        z *= v.w();
        return this;
    }

    /**
     * Divide this {@link Vector4d} component-wise by the given {@link Vector4dr}.
     * 
     * @param v
     *          the vector to divide by
     * @return this
     */
    public Vector4d div(Vector4dr v) {
        x /= v.x();
        y /= v.y();
        z /= v.z();
        z /= v.w();
        return this;
    }

    /**
     * Multiply this {@link Vector4d} component-wise by the given {@link Vector4fr}.
     * 
     * @param v
     *          the vector to multiply by
     * @return this
     */
    public Vector4d mul(Vector4fr v) {
        x *= v.x();
        y *= v.y();
        z *= v.z();
        z *= v.w();
        return this;
    }

    /**
     * Multiply this {@link Vector4d} by the given matrix <code>mat</code>.
     * 
     * @param mat
     *          the matrix to multiply by
     * @return this
     */
    public Vector4d mul(Matrix4dr mat) {
        return mul(mat, this);
    }

    /**
     * Multiply this {@link Vector4d} by the given matrix <code>mat</code>.
     * 
     * @param mat
     *          the matrix to multiply by
     * @return this
     */
    public Vector4d mul(Matrix4fr mat) {
        return mul(mat, this);
    }

    /**
     * Multiply this Vector4d by the given matrix <code>mat</code>, perform perspective division.
     * 
     * @param mat
     *          the matrix to multiply this vector by
     * @return this
     */
    public Vector4d mulProject(Matrix4dr mat) {
        return mulProject(mat, this);
    }

    /**
     * Multiply this Vector4d by the given scalar value.
     * 
     * @param scalar
     *          the scalar to multiply by
     * @return this
     */
    public Vector4d mul(double scalar) {
        x *= scalar;
        y *= scalar;
        z *= scalar;
        w *= scalar;
        return this;
    }

    /**
     * Divide this Vector4d by the given scalar value.
     * 
     * @param scalar
     *          the scalar to divide by
     * @return this
     */
    public Vector4d div(double scalar) {
        x /= scalar;
        y /= scalar;
        z /= scalar;
        w /= scalar;
        return this;
    }

    /**
     * Transform this vector by the given quaternion <code>quat</code> and store the result in <code>this</code>.
     * 
     * @see Quaterniond#transform(Vector4d)
     * 
     * @param quat
     *          the quaternion to transform this vector
     * @return this
     */
    public Vector4d rotate(Quaterniondr quat) {
        return rotate(quat, this);
    }

    /**
     * Normalizes this vector.
     * 
     * @return this
     */
    public Vector4d normalize() {
        double invLength = 1.0 / length();
        x *= invLength;
        y *= invLength;
        z *= invLength;
        w *= invLength;
        return this;
    }

    /**
     * Normalize this vector by computing only the norm of <tt>(x, y, z)</tt>.
     * 
     * @return this
     */
    public Vector4d normalize3() {
        double invLength = 1.0 / Math.sqrt(x * x + y * y + z * z);
        x *= invLength;
        y *= invLength;
        z *= invLength;
        w *= invLength;
        return this;
    }

    /**
     * Set all components to zero.
     * 
     * @return this
     */
    public Vector4d zero() {
        x = 0.0;
        y = 0.0;
        z = 0.0;
        w = 0.0;
        return this;
    }

    /**
     * Negate this vector.
     * 
     * @return this
     */
    public Vector4d negate() {
        x = -x;
        y = -y;
        z = -z;
        w = -w;
        return this;
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeDouble(x);
        out.writeDouble(y);
        out.writeDouble(z);
    }

    public void readExternal(ObjectInput in) throws IOException,
            ClassNotFoundException {
        x = in.readDouble();
        y = in.readDouble();
        z = in.readDouble();
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
    public Vector4d lerp(Vector4dr other, double t) {
        return lerp(other, t, this);
    }

}
