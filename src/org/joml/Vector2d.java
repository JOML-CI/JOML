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
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Represents a 2D vector with double-precision.
 *
 * @author RGreenlees
 * @author Kai Burjack
 */
public class Vector2d extends Vector2dr implements Externalizable {

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
     * Create a new {@link Vector2d} and initialize its components to zero.
     */
    public Vector2d() {
    }

    /**
     * Create a new {@link Vector2d} and initialize both of its components with the given value.
     * 
     * @param d    
     *          the value of both components
     */
    public Vector2d(double d) {
        this(d, d);
    }

    /**
     * Create a new {@link Vector2d} and initialize its components to the given values.
     * 
     * @param x
     *          the x value
     * @param y
     *          the y value
     */
    public Vector2d(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Create a new {@link Vector2d} and initialize its components to the one of the given vector.
     * 
     * @param v
     *          the {@link Vector2dr} to copy the values from
     */
    public Vector2d(Vector2dr v) {
        x = v.x();
        y = v.y();
    }

    /**
     * Create a new {@link Vector2d} and initialize its components to the one of the given vector.
     * 
     * @param v
     *          the {@link Vector2fr} to copy the values from
     */
    public Vector2d(Vector2fr v) {
        x = v.x();
        y = v.y();
    }

    /**
     * Create a new {@link Vector2d} and read this vector from the supplied {@link ByteBuffer}
     * at the current buffer {@link ByteBuffer#position() position}.
     * <p>
     * This method will not increment the position of the given ByteBuffer.
     * <p>
     * In order to specify the offset into the ByteBuffer at which
     * the vector is read, use {@link #Vector2d(int, ByteBuffer)}, taking
     * the absolute position as parameter.
     *
     * @param buffer
     *          values will be read in <tt>x, y</tt> order
     * @see #Vector2d(int, ByteBuffer)
     */
    public Vector2d(ByteBuffer buffer) {
        this(buffer.position(), buffer);
    }

    /**
     * Create a new {@link Vector2d} and read this vector from the supplied {@link ByteBuffer}
     * starting at the specified absolute buffer position/index.
     * <p>
     * This method will not increment the position of the given ByteBuffer.
     *
     * @param index
     *          the absolute position into the ByteBuffer
     * @param buffer
     *          values will be read in <tt>x, y</tt> order
     */
    public Vector2d(int index, ByteBuffer buffer) {
        x = buffer.getDouble(index);
        y = buffer.getDouble(index + 8);
    }

    /**
     * Create a new {@link Vector2d} and read this vector from the supplied {@link DoubleBuffer}
     * at the current buffer {@link DoubleBuffer#position() position}.
     * <p>
     * This method will not increment the position of the given DoubleBuffer.
     * <p>
     * In order to specify the offset into the DoubleBuffer at which
     * the vector is read, use {@link #Vector2d(int, DoubleBuffer)}, taking
     * the absolute position as parameter.
     *
     * @param buffer
     *          values will be read in <tt>x, y</tt> order
     * @see #Vector2d(int, DoubleBuffer)
     */
    public Vector2d(DoubleBuffer buffer) {
        this(buffer.position(), buffer);
    }

    /**
     * Create a new {@link Vector2d} and read this vector from the supplied {@link DoubleBuffer}
     * starting at the specified absolute buffer position/index.
     * <p>
     * This method will not increment the position of the given DoubleBuffer.
     *
     * @param index
     *          the absolute position into the DoubleBuffer
     * @param buffer
     *          values will be read in <tt>x, y</tt> order
     */
    public Vector2d(int index, DoubleBuffer buffer) {
        x = buffer.get(index);
        y = buffer.get(index + 1);
    }

    public double x() {
        return x;
    }

    public double y() {
        return y;
    }

    /**
     * Set the x and y components to the supplied value.
     *
     * @param d
     *          the value of both components
     * @return this
     */
    public Vector2d set(double d) {
        return set(d, d);
    }

    /**
     * Set the x and y components to the supplied values.
     * 
     * @param x
     *          the x value
     * @param y
     *          the y value
     * @return this
     */
    public Vector2d set(double x, double y) {
        this.x = x;
        this.y = y;
        return this;
    }

    /**
     * Set this {@link Vector2d} to the values of v.
     * 
     * @param v
     *          the vector to copy from
     * @return this
     */
    public Vector2d set(Vector2dr v) {
        x = v.x();
        y = v.y();
        return this;
    }

    /**
     * Set this {@link Vector2d} to be a clone of <code>v</code>.
     * 
     * @param v
     *          the vector to copy from
     * @return this
     */
    public Vector2d set(Vector2fr v) {
        x = v.x();
        y = v.y();
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
     *          values will be read in <tt>x, y</tt> order
     * @return this
     * @see #set(int, ByteBuffer)
     */
    public Vector2d set(ByteBuffer buffer) {
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
     *          values will be read in <tt>x, y</tt> order
     * @return this
     */
    public Vector2d set(int index, ByteBuffer buffer) {
        x = buffer.getDouble(index);
        y = buffer.getDouble(index + 8);
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
     *          values will be read in <tt>x, y</tt> order
     * @return this
     * @see #set(int, DoubleBuffer)
     */
    public Vector2d set(DoubleBuffer buffer) {
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
     *          values will be read in <tt>x, y</tt> order
     * @return this
     */
    public Vector2d set(int index, DoubleBuffer buffer) {
        x = buffer.get(index);
        y = buffer.get(index + 1);
        return this;
    }

    /**
     * Set this vector to be one of its perpendicular vectors.
     * 
     * @return this
     */
    public Vector2d perpendicular() {
        return set(y, x * -1);
    }

    /**
     * Subtract <code>v</code> from this vector.
     * 
     * @param v
     *          the vector to subtract
     * @return this
     */
    public Vector2d sub(Vector2dr v) {
        x -= v.x();
        y -= v.y();
        return this;
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
    public Vector2d sub(double x, double y) {
        this.x -= x;
        this.y -= y;
        return this;
    }

    /**
     * Subtract <code>v</code> from this vector.
     * 
     * @param v
     *          the vector to subtract
     * @return this
     */
    public Vector2d sub(Vector2fr v) {
        x -= v.x();
        y -= v.y();
        return this;
    }

    /**
     * Normalize this vector.
     * 
     * @return this
     */
    public Vector2d normalize() {
        double invLength = 1.0 / Math.sqrt(x * x + y * y);
        x *= invLength;
        y *= invLength;
        return this;
    }

    /**
     * Add <code>v</code> to this vector.
     * 
     * @param v
     *          the vector to add
     * @return this
     */
    public Vector2d add(Vector2dr v) {
        x += v.x();
        y += v.y();
        return this;
    }

    /**
     * Add <code>(x, y)</code> to this vector.
     * 
     * @param x
     *          the x component to add
     * @param y
     *          the y component to add
     * @return this
     */
    public Vector2d add(double x, double y) {
        this.x += x;
        this.y += y;
        return this;
    }

    /**
     * Add <code>v</code> to this vector.
     * 
     * @param v
     *          the vector to add
     * @return this
     */
    public Vector2d add(Vector2fr v) {
        x += v.x();
        y += v.y();
        return this;
    }

    /**
     * Set all components to zero.
     * 
     * @return this
     */
    public Vector2d zero() {
        x = 0.0;
        y = 0.0;
        return this;
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeDouble(x);
        out.writeDouble(y);
    }

    public void readExternal(ObjectInput in) throws IOException,
            ClassNotFoundException {
        x = in.readDouble();
        y = in.readDouble();
    }

    /**
     * Negate this vector.
     * 
     * @return this
     */
    public Vector2d negate() {
        x = -x;
        y = -y;
        return this;
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
    public Vector2d lerp(Vector2dr other, double t) {
        return lerp(other, t, this);
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
    public Vector2d fma(Vector2dr a, Vector2dr b) {
        x += a.x() * b.x();
        y += a.y() * b.y();
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
    public Vector2d fma(double a, Vector2dr b) {
        x += a * b.x();
        y += a * b.y();
        return this;
    }

}
