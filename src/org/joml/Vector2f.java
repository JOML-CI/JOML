/*
 * (C) Copyright 2015-2018 Richard Greenlees

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
//#ifdef __HAS_NIO__
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
//#endif
import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.joml.internal.MemUtil;
import org.joml.internal.Options;
import org.joml.internal.Runtime;

/**
 * Represents a 2D vector with single-precision.
 *
 * @author RGreenlees
 * @author Kai Burjack
 */
public class Vector2f implements Externalizable, Vector2fc {

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
     * Create a new {@link Vector2f} and initialize its components to zero.
     */
    public Vector2f() {
    }

    /**
     * Create a new {@link Vector2f} and initialize both of its components with the given value.
     *
     * @param d
     *        the value of both components
     */
    public Vector2f(float d) {
        this(d, d);
    }

    /**
     * Create a new {@link Vector2f} and initialize its components to the given values.
     * 
     * @param x
     *        the x component
     * @param y
     *        the y component
     */
    public Vector2f(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Create a new {@link Vector2f} and initialize its components to the one of the given vector.
     * 
     * @param v
     *        the {@link Vector2fc} to copy the values from
     */
    public Vector2f(Vector2fc v) {
        x = v.x();
        y = v.y();
    }

    /**
     * Create a new {@link Vector2f} and initialize its components to the one of the given vector.
     * 
     * @param v
     *        the {@link Vector2ic} to copy the values from
     */
    public Vector2f(Vector2ic v) {
        x = v.x();
        y = v.y();
    }

//#ifdef __HAS_NIO__
    /**
     * Create a new {@link Vector2f} and read this vector from the supplied {@link ByteBuffer}
     * at the current buffer {@link ByteBuffer#position() position}.
     * <p>
     * This method will not increment the position of the given ByteBuffer.
     * <p>
     * In order to specify the offset into the ByteBuffer at which
     * the vector is read, use {@link #Vector2f(int, ByteBuffer)}, taking
     * the absolute position as parameter.
     *
     * @param buffer
     *        values will be read in <tt>x, y</tt> order
     * @see #Vector2f(int, ByteBuffer)
     */
    public Vector2f(ByteBuffer buffer) {
        this(buffer.position(), buffer);
    }

    /**
     * Create a new {@link Vector2f} and read this vector from the supplied {@link ByteBuffer}
     * starting at the specified absolute buffer position/index.
     * <p>
     * This method will not increment the position of the given ByteBuffer.
     *
     * @param index
     *        the absolute position into the ByteBuffer
     * @param buffer values will be read in <tt>x, y</tt> order
     */
    public Vector2f(int index, ByteBuffer buffer) {
        MemUtil.INSTANCE.get(this, index, buffer);
    }

    /**
     * Create a new {@link Vector2f} and read this vector from the supplied {@link FloatBuffer}
     * at the current buffer {@link FloatBuffer#position() position}.
     * <p>
     * This method will not increment the position of the given FloatBuffer.
     * <p>
     * In order to specify the offset into the FloatBuffer at which
     * the vector is read, use {@link #Vector2f(int, FloatBuffer)}, taking
     * the absolute position as parameter.
     *
     * @param buffer
     *        values will be read in <tt>x, y</tt> order
     * @see #Vector2f(int, FloatBuffer)
     */
    public Vector2f(FloatBuffer buffer) {
        this(buffer.position(), buffer);
    }

    /**
     * Create a new {@link Vector2f} and read this vector from the supplied {@link FloatBuffer}
     * starting at the specified absolute buffer position/index.
     * <p>
     * This method will not increment the position of the given FloatBuffer.
     *
     * @param index 
     *        the absolute position into the FloatBuffer
     * @param buffer
     *        values will be read in <tt>x, y</tt> order
     */
    public Vector2f(int index, FloatBuffer buffer) {
        MemUtil.INSTANCE.get(this, index, buffer);
    }
//#endif

    /* (non-Javadoc)
     * @see org.joml.Vector2fc#x()
     */
    public float x() {
        return this.x;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector2fc#y()
     */
    public float y() {
        return this.y;
    }

    /**
     * Set the x and y components to the supplied value.
     *
     * @param d
     *        the value of both components
     * @return this
     */
    public Vector2f set(float d) {
        return set(d, d);
    }
    
    /**
     * Set the x and y components to the supplied values.
     * 
     * @param x
     *        the x component
     * @param y
     *        the y component
     * @return this
     */
    public Vector2f set(float x, float y) {
        this.x = x;
        this.y = y;
        return this;
    }

    /**
     * Set this {@link Vector2f} to the values of v.
     * 
     * @param v
     *        the vector to copy from
     * @return this
     */
    public Vector2f set(Vector2fc v) {
        return set(v.x(), v.y());
    }

    /**
     * Set this {@link Vector2f} to the values of v.
     * 
     * @param v
     *        the vector to copy from
     * @return this
     */
    public Vector2f set(Vector2ic v) {
        return set(v.x(), v.y());
    }

    /**
     * Set this {@link Vector2f} to the values of v.
     * <p>
     * Note that due to the given vector <code>v</code> storing the components in double-precision,
     * there is the possibility to lose precision.
     * 
     * @param v
     *        the vector to copy from
     * @return this
     */
    public Vector2f set(Vector2dc v) {
        return set((float) v.x(), (float) v.y());
    }

//#ifdef __HAS_NIO__
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
     *        values will be read in <tt>x, y</tt> order
     * @return this
     * @see #set(int, ByteBuffer)
     */
    public Vector2f set(ByteBuffer buffer) {
        return set(buffer.position(), buffer);
    }

    /**
     * Read this vector from the supplied {@link ByteBuffer} starting at the specified
     * absolute buffer position/index.
     * <p>
     * This method will not increment the position of the given ByteBuffer.
     *
     * @param index
     *        the absolute position into the ByteBuffer
     * @param buffer
     *        values will be read in <tt>x, y</tt> order
     * @return this
     */
    public Vector2f set(int index, ByteBuffer buffer) {
        MemUtil.INSTANCE.get(this, index, buffer);
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
     *        values will be read in <tt>x, y</tt> order
     * @return this
     * @see #set(int, FloatBuffer)
     */
    public Vector2f set(FloatBuffer buffer) {
        return set(buffer.position(), buffer);
    }

    /**
     * Read this vector from the supplied {@link FloatBuffer} starting at the specified
     * absolute buffer position/index.
     * <p>
     * This method will not increment the position of the given FloatBuffer.
     *
     * @param index 
     *        the absolute position into the FloatBuffer
     * @param buffer
     *        values will be read in <tt>x, y</tt> order
     * @return this
     */
    public Vector2f set(int index, FloatBuffer buffer) {
        MemUtil.INSTANCE.get(this, index, buffer);
        return this;
    }
//#endif

//#ifndef __GWT__
    /**
     * Set the values of this vector by reading 2 float values from off-heap memory,
     * starting at the given address.
     * <p>
     * This method will throw an {@link UnsupportedOperationException} when JOML is used with `-Djoml.nounsafe`.
     * <p>
     * <em>This method is unsafe as it can result in a crash of the JVM process when the specified address range does not belong to this process.</em>
     * 
     * @param address
     *              the off-heap memory address to read the vector values from
     * @return this
     */
    public Vector2f setFromAddress(long address) {
        if (Options.NO_UNSAFE)
            throw new UnsupportedOperationException("Not supported when using joml.nounsafe");
        MemUtil.MemUtilUnsafe unsafe = (MemUtil.MemUtilUnsafe) MemUtil.INSTANCE;
        unsafe.get(this, address);
        return this;
    }
//#endif

    /* (non-Javadoc)
     * @see org.joml.Vector2fc#get(int)
     */
    public float get(int component) throws IllegalArgumentException {
        switch (component) {
        case 0:
            return x;
        case 1:
            return y;
        default:
            throw new IllegalArgumentException();
        }
    }

    /**
     * Set the value of the specified component of this vector.
     *
     * @param component
     *          the component whose value to set, within <tt>[0..1]</tt>
     * @param value
     *          the value to set
     * @return this
     * @throws IllegalArgumentException if <code>component</code> is not within <tt>[0..1]</tt>
     */
    public Vector2f setComponent(int component, float value) throws IllegalArgumentException {
        switch (component) {
            case 0:
                x = value;
                break;
            case 1:
                y = value;
                break;
            default:
                throw new IllegalArgumentException();
        }
        return this;
    }

//#ifdef __HAS_NIO__
    /* (non-Javadoc)
     * @see org.joml.Vector2fc#get(java.nio.ByteBuffer)
     */
    public ByteBuffer get(ByteBuffer buffer) {
        return get(buffer.position(), buffer);
    }

    /* (non-Javadoc)
     * @see org.joml.Vector2fc#get(int, java.nio.ByteBuffer)
     */
    public ByteBuffer get(int index, ByteBuffer buffer) {
        MemUtil.INSTANCE.put(this, index, buffer);
        return buffer;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector2fc#get(java.nio.FloatBuffer)
     */
    public FloatBuffer get(FloatBuffer buffer) {
        return get(buffer.position(), buffer);
    }

    /* (non-Javadoc)
     * @see org.joml.Vector2fc#get(int, java.nio.FloatBuffer)
     */
    public FloatBuffer get(int index, FloatBuffer buffer) {
        MemUtil.INSTANCE.put(this, index, buffer);
        return buffer;
    }
//#endif

//#ifndef __GWT__
    public Vector2fc getToAddress(long address) {
        if (Options.NO_UNSAFE)
            throw new UnsupportedOperationException("Not supported when using joml.nounsafe");
        MemUtil.MemUtilUnsafe unsafe = (MemUtil.MemUtilUnsafe) MemUtil.INSTANCE;
        unsafe.put(this, address);
        return this;
    }
//#endif

    /**
     * Set this vector to be one of its perpendicular vectors.
     * 
     * @return this
     */
    public Vector2f perpendicular() {
        return set(y, x * -1);
    }

    /**
     * Subtract <code>v</code> from this vector.
     * 
     * @param v
     *          the vector to subtract
     * @return this
     */
    public Vector2f sub(Vector2fc v) {
        return sub(v, this);
    }

    /* (non-Javadoc)
     * @see org.joml.Vector2fc#sub(org.joml.Vector2fc, org.joml.Vector2f)
     */
    public Vector2f sub(Vector2fc v, Vector2f dest) {
        dest.x = x - v.x();
        dest.y = y - v.y();
        return dest;
    }

    /**
     * Subtract <tt>(x, y)</tt> from this vector.
     * 
     * @param x
     *          the x component to subtract
     * @param y
     *          the y component to subtract
     * @return this
     */
    public Vector2f sub(float x, float y) {
        return sub(x, y, this);
    }

    /* (non-Javadoc)
     * @see org.joml.Vector2fc#sub(float, float, org.joml.Vector2f)
     */
    public Vector2f sub(float x, float y, Vector2f dest) {
        dest.x = this.x - x;
        dest.y = this.y - y;
        return dest;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector2fc#dot(org.joml.Vector2fc)
     */
    public float dot(Vector2fc v) {
        return x * v.x() + y * v.y();
    }

    /* (non-Javadoc)
     * @see org.joml.Vector2fc#angle(org.joml.Vector2fc)
     */
    public float angle(Vector2fc v) {
        float dot = x*v.x() + y*v.y();
        float det = x*v.y() - y*v.x();
        return (float) Math.atan2(det, dot);
    }

    /* (non-Javadoc)
     * @see org.joml.Vector2fc#length()
     */
    public float length() {
        return (float) Math.sqrt((x * x) + (y * y));
    }

    /* (non-Javadoc)
     * @see org.joml.Vector2fc#lengthSquared()
     */
    public float lengthSquared() {
        return x * x + y * y;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector2fc#distance(org.joml.Vector2fc)
     */
    public float distance(Vector2fc v) {
        return distance(v.x(), v.y());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector2fc#distanceSquared(org.joml.Vector2fc)
     */
    public float distanceSquared(Vector2fc v) {
        return distanceSquared(v.x(), v.y());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector2fc#distance(float, float)
     */
    public float distance(float x, float y) {
        float dx = this.x - x;
        float dy = this.y - y;
        return (float) Math.sqrt(dx * dx + dy * dy);
    }

    /* (non-Javadoc)
     * @see org.joml.Vector2fc#distanceSquared(float, float)
     */
    public float distanceSquared(float x, float y) {
        float dx = this.x - x;
        float dy = this.y - y;
        return dx * dx + dy * dy;
    }

    /**
     * Normalize this vector.
     * 
     * @return this
     */
    public Vector2f normalize() {
        return normalize(this);
    }

    /* (non-Javadoc)
     * @see org.joml.Vector2fc#normalize(org.joml.Vector2f)
     */
    public Vector2f normalize(Vector2f dest) {
        float invLength = (float) (1.0 / Math.sqrt(x * x + y * y));
        dest.x = x * invLength;
        dest.y = y * invLength;
        return dest;
    }

    /**
     * Scale this vector to have the given length.
     * 
     * @param length
     *          the desired length
     * @return this
     */
    public Vector2f normalize(float length) {
        return normalize(length, this);
    }

    /* (non-Javadoc)
     * @see org.joml.Vector2fc#normalize(float, org.joml.Vector2f)
     */
    public Vector2f normalize(float length, Vector2f dest) {
        float invLength = (float) (1.0 / Math.sqrt(x * x + y * y)) * length;
        dest.x = x * invLength;
        dest.y = y * invLength;
        return dest;
    }

    /**
     * Add <code>v</code> to this vector.
     * 
     * @param v
     *        the vector to add
     * @return this
     */
    public Vector2f add(Vector2fc v) {
        return add(v, this);
    }

    /* (non-Javadoc)
     * @see org.joml.Vector2fc#add(org.joml.Vector2fc, org.joml.Vector2f)
     */
    public Vector2f add(Vector2fc v, Vector2f dest) {
        dest.x = x + v.x();
        dest.y = y + v.y();
        return dest;
    }

    /**
     * Increment the components of this vector by the given values.
     * 
     * @param x
     *          the x component to add
     * @param y
     *          the y component to add
     * @return this
     */
    public Vector2f add(float x, float y) {
        return add(x, y, this);
    }

    /* (non-Javadoc)
     * @see org.joml.Vector2fc#add(float, float, org.joml.Vector2f)
     */
    public Vector2f add(float x, float y, Vector2f dest) {
        dest.x = this.x + x;
        dest.y = this.y + y;
        return dest;
    }

    /**
     * Set all components to zero.
     * 
     * @return this
     */
    public Vector2f zero() {
        return set(0, 0);
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeFloat(x);
        out.writeFloat(y);
    }

    public void readExternal(ObjectInput in) throws IOException,
            ClassNotFoundException {
        x = in.readFloat();
        y = in.readFloat();
    }

    /**
     * Negate this vector.
     * 
     * @return this
     */
    public Vector2f negate() {
        return negate(this);
    }

    /* (non-Javadoc)
     * @see org.joml.Vector2fc#negate(org.joml.Vector2f)
     */
    public Vector2f negate(Vector2f dest) {
        dest.x = -x;
        dest.y = -y;
        return dest;
    }

    /**
     * Multiply the components of this vector by the given scalar.
     * 
     * @param scalar
     *        the value to multiply this vector's components by
     * @return this
     */
    public Vector2f mul(float scalar) {
        return mul(scalar, this);
    }

    /* (non-Javadoc)
     * @see org.joml.Vector2fc#mul(float, org.joml.Vector2f)
     */
    public Vector2f mul(float scalar, Vector2f dest) {
        dest.x = x * scalar;
        dest.y = y * scalar;
        return dest;
    }

    /**
     * Multiply the components of this Vector2f by the given scalar values and store the result in <code>this</code>.
     * 
     * @param x
     *          the x component to multiply this vector by
     * @param y
     *          the y component to multiply this vector by
     * @return this
     */
    public Vector2f mul(float x, float y) {
        return mul(x, y, this);
    }

    /* (non-Javadoc)
     * @see org.joml.Vector2fc#mul(float, float, org.joml.Vector2f)
     */
    public Vector2f mul(float x, float y, Vector2f dest) {
        dest.x = this.x * x;
        dest.y = this.y * y;
        return dest;
    }

    /**
     * Multiply this Vector2f component-wise by another Vector2f.
     * 
     * @param v
     *          the vector to multiply by
     * @return this
     */
    public Vector2f mul(Vector2fc v) {
        return mul(v, this);
    }

    /* (non-Javadoc)
     * @see org.joml.Vector2fc#mul(org.joml.Vector2fc, org.joml.Vector2f)
     */
    public Vector2f mul(Vector2fc v, Vector2f dest) {
        dest.x = x * v.x();
        dest.y = y * v.y();
        return dest;
    }

    /**
     * Multiply the given 3x2 matrix <code>mat</code> with <code>this</code>.
     * <p>
     * This method assumes the <tt>z</tt> component of <code>this</code> to be <tt>1.0</tt>.
     * 
     * @param mat
     *          the matrix to multiply this vector by
     * @return this
     */
    public Vector2f mulPosition(Matrix3x2fc mat) {
        return mulPosition(mat, this);
    }

    /* (non-Javadoc)
     * @see org.joml.Vector2fc#mulPosition(org.joml.Matrix3x2fc, org.joml.Vector2f)
     */
    public Vector2f mulPosition(Matrix3x2fc mat, Vector2f dest) {
        dest.set(mat.m00() * x + mat.m10() * y + mat.m20(),
                 mat.m01() * x + mat.m11() * y + mat.m21());
        return dest;
    }

    /**
     * Multiply the given 3x2 matrix <code>mat</code> with <code>this</code>.
     * <p>
     * This method assumes the <tt>z</tt> component of <code>this</code> to be <tt>0.0</tt>.
     * 
     * @param mat
     *          the matrix to multiply this vector by
     * @return this
     */
    public Vector2f mulDirection(Matrix3x2fc mat) {
        return mulDirection(mat, this);
    }

    /* (non-Javadoc)
     * @see org.joml.Vector2fc#mulDirection(org.joml.Matrix3x2fc, org.joml.Vector2f)
     */
    public Vector2f mulDirection(Matrix3x2fc mat, Vector2f dest) {
        dest.set(mat.m00() * x + mat.m10() * y,
                 mat.m01() * x + mat.m11() * y);
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
    public Vector2f lerp(Vector2fc other, float t) {
        return lerp(other, t, this);
    }

    /* (non-Javadoc)
     * @see org.joml.Vector2fc#lerp(org.joml.Vector2fc, float, org.joml.Vector2f)
     */
    public Vector2f lerp(Vector2fc other, float t, Vector2f dest) {
        dest.x = x + (other.x() - x) * t;
        dest.y = y + (other.y() - y) * t;
        return dest;
    }

    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Float.floatToIntBits(x);
        result = prime * result + Float.floatToIntBits(y);
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Vector2f other = (Vector2f) obj;
        if (Float.floatToIntBits(x) != Float.floatToIntBits(other.x))
            return false;
        if (Float.floatToIntBits(y) != Float.floatToIntBits(other.y))
            return false;
        return true;
    }

    /**
     * Return a string representation of this vector.
     * <p>
     * This method creates a new {@link DecimalFormat} on every invocation with the format string "<tt>0.000E0;-</tt>".
     * 
     * @return the string representation
     */
    public String toString() {
        return Runtime.formatNumbers(toString(Options.NUMBER_FORMAT));
    }

    /**
     * Return a string representation of this vector by formatting the vector components with the given {@link NumberFormat}.
     * 
     * @param formatter
     *          the {@link NumberFormat} used to format the vector components with
     * @return the string representation
     */
    public String toString(NumberFormat formatter) {
        return "(" + formatter.format(x) + " " + formatter.format(y) + ")";
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
    public Vector2f fma(Vector2fc a, Vector2fc b) {
        return fma(a, b, this);
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
    public Vector2f fma(float a, Vector2fc b) {
        return fma(a, b, this);
    }

    /* (non-Javadoc)
     * @see org.joml.Vector2fc#fma(org.joml.Vector2fc, org.joml.Vector2fc, org.joml.Vector2f)
     */
    public Vector2f fma(Vector2fc a, Vector2fc b, Vector2f dest) {
        dest.x = x + a.x() * b.x();
        dest.y = y + a.y() * b.y();
        return dest;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector2fc#fma(float, org.joml.Vector2fc, org.joml.Vector2f)
     */
    public Vector2f fma(float a, Vector2fc b, Vector2f dest) {
        dest.x = x + a * b.x();
        dest.y = y + a * b.y();
        return dest;
    }

    /**
     * Set the components of this vector to be the component-wise minimum of this and the other vector.
     *
     * @param v
     *          the other vector
     * @return this
     */
    public Vector2f min(Vector2fc v) {
        return min(v, this);
    }

    public Vector2f min(Vector2fc v, Vector2f dest) {
        dest.x = x < v.x() ? x : v.x();
        dest.y = y < v.y() ? y : v.y();
        return this;
    }

    /**
     * Set the components of this vector to be the component-wise maximum of this and the other vector.
     *
     * @param v
     *          the other vector
     * @return this
     */
    public Vector2f max(Vector2fc v) {
        return max(v, this);
    }

    public Vector2f max(Vector2fc v, Vector2f dest) {
        dest.x = x > v.x() ? x : v.x();
        dest.y = y > v.y() ? y : v.y();
        return this;
    }

}
