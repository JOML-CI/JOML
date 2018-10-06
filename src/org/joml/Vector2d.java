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
import java.nio.DoubleBuffer;
//#endif
import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.joml.Math;
import org.joml.internal.MemUtil;
import org.joml.internal.Options;
import org.joml.internal.Runtime;

/**
 * Represents a 2D vector with double-precision.
 *
 * @author RGreenlees
 * @author Kai Burjack
 * @author F. Neurath
 */
public class Vector2d implements Externalizable, Vector2dc {

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
     *          the {@link Vector2dc} to copy the values from
     */
    public Vector2d(Vector2dc v) {
        x = v.x();
        y = v.y();
    }

    /**
     * Create a new {@link Vector2d} and initialize its components to the one of the given vector.
     * 
     * @param v
     *          the {@link Vector2fc} to copy the values from
     */
    public Vector2d(Vector2fc v) {
        x = v.x();
        y = v.y();
    }

    /**
     * Create a new {@link Vector2d} and initialize its components to the one of the given vector.
     * 
     * @param v
     *          the {@link Vector2ic} to copy the values from
     */
    public Vector2d(Vector2ic v) {
        x = v.x();
        y = v.y();
    }

//#ifdef __HAS_NIO__
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
     *          values will be read in <code>x, y</code> order
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
     *          values will be read in <code>x, y</code> order
     */
    public Vector2d(int index, ByteBuffer buffer) {
        MemUtil.INSTANCE.get(this, index, buffer);
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
     *          values will be read in <code>x, y</code> order
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
     *          values will be read in <code>x, y</code> order
     */
    public Vector2d(int index, DoubleBuffer buffer) {
        MemUtil.INSTANCE.get(this, index, buffer);
    }
//#endif

    private Vector2d thisOrNew() {
        return this;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector2dc#x()
     */
    public double x() {
        return this.x;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector2dc#y()
     */
    public double y() {
        return this.y;
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
    public Vector2d set(Vector2dc v) {
        return set(v.x(), v.y());
    }

    /**
     * Set this {@link Vector2d} to be a clone of <code>v</code>.
     * 
     * @param v
     *          the vector to copy from
     * @return this
     */
    public Vector2d set(Vector2fc v) {
        return set(v.x(), v.y());
    }

    /**
     * Set this {@link Vector2d} to be a clone of <code>v</code>.
     * 
     * @param v
     *          the vector to copy from
     * @return this
     */
    public Vector2d set(Vector2ic v) {
        return set(v.x(), v.y());
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
     *          values will be read in <code>x, y</code> order
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
     *          values will be read in <code>x, y</code> order
     * @return this
     */
    public Vector2d set(int index, ByteBuffer buffer) {
        MemUtil.INSTANCE.get(this, index, buffer);
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
     *          values will be read in <code>x, y</code> order
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
     *          values will be read in <code>x, y</code> order
     * @return this
     */
    public Vector2d set(int index, DoubleBuffer buffer) {
        MemUtil.INSTANCE.get(this, index, buffer);
        return this;
    }
//#endif

//#ifndef __GWT__
    /**
     * Set the values of this vector by reading 2 double values from off-heap memory,
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
    public Vector2d setFromAddress(long address) {
        if (Options.NO_UNSAFE)
            throw new UnsupportedOperationException("Not supported when using joml.nounsafe");
        MemUtil.MemUtilUnsafe unsafe = (MemUtil.MemUtilUnsafe) MemUtil.INSTANCE;
        unsafe.get(this, address);
        return this;
    }
//#endif

    /* (non-Javadoc)
     * @see org.joml.Vector2dc#get(int)
     */
    public double get(int component) throws IllegalArgumentException {
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
     *          the component whose value to set, within <code>[0..1]</code>
     * @param value
     *          the value to set
     * @return this
     * @throws IllegalArgumentException if <code>component</code> is not within <code>[0..1]</code>
     */
    public Vector2d setComponent(int component, double value) throws IllegalArgumentException {
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
     * @see org.joml.Vector2dc#get(java.nio.ByteBuffer)
     */
    public ByteBuffer get(ByteBuffer buffer) {
        return get(buffer.position(), buffer);
    }

    /* (non-Javadoc)
     * @see org.joml.Vector2dc#get(int, java.nio.ByteBuffer)
     */
    public ByteBuffer get(int index, ByteBuffer buffer) {
        MemUtil.INSTANCE.put(this, index, buffer);
        return buffer;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector2dc#get(java.nio.DoubleBuffer)
     */
    public DoubleBuffer get(DoubleBuffer buffer) {
        return get(buffer.position(), buffer);
    }

    /* (non-Javadoc)
     * @see org.joml.Vector2dc#get(int, java.nio.DoubleBuffer)
     */
    public DoubleBuffer get(int index, DoubleBuffer buffer) {
        MemUtil.INSTANCE.put(this, index, buffer);
        return buffer;
    }
//#endif

//#ifndef __GWT__
    public Vector2dc getToAddress(long address) {
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
    public Vector2d perpendicular() {
        return set(y, x * -1);
    }

    /**
     * Subtract <code>v</code> from this vector.
     * 
     * @param v
     *          the vector to subtract
     * @return a vector holding the result
     */
    public Vector2d sub(Vector2dc v) {
        return sub(v, thisOrNew());
    }

    /**
     * Subtract <code>(x, y)</code> from this vector.
     * 
     * @param x
     *          the x component to subtract
     * @param y
     *          the y component to subtract
     * @return a vector holding the result
     */
    public Vector2d sub(double x, double y) {
        return sub(x, y, thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector2dc#sub(double, double, org.joml.Vector2d)
     */
    public Vector2d sub(double x, double y, Vector2d dest) {
        dest.x = this.x - x;
        dest.y = this.y - y;
        return dest;
    }

    /**
     * Subtract <code>v</code> from this vector.
     * 
     * @param v
     *          the vector to subtract
     * @return a vector holding the result
     */
    public Vector2d sub(Vector2fc v) {
        return sub(v, thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector2dc#sub(org.joml.Vector2dc, org.joml.Vector2d)
     */
    public Vector2d sub(Vector2dc v, Vector2d dest) {
        dest.x = x - v.x();
        dest.y = y - v.y();
        return dest;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector2dc#sub(org.joml.Vector2fc, org.joml.Vector2d)
     */
    public Vector2d sub(Vector2fc v, Vector2d dest) {
        dest.x = x + v.x();
        dest.y = y + v.y();
        return dest;
    }

    /**
     * Multiply the components of this vector by the given scalar.
     * 
     * @param scalar
     *        the value to multiply this vector's components by
     * @return a vector holding the result
     */
    public Vector2d mul(double scalar) {
        return mul(scalar, thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector2dc#mul(double, org.joml.Vector2d)
     */
    public Vector2d mul(double scalar, Vector2d dest) {
        dest.x = x * scalar;
        dest.y = y * scalar;
        return dest;
    }

    /**
     * Multiply the components of this Vector2d by the given scalar values and store the result in <code>this</code>.
     * 
     * @param x
     *          the x component to multiply this vector by
     * @param y
     *          the y component to multiply this vector by
     * @return a vector holding the result
     */
    public Vector2d mul(double x, double y) {
        return mul(x, y, thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector2dc#mul(double, double, org.joml.Vector2d)
     */
    public Vector2d mul(double x, double y, Vector2d dest) {
        dest.x = this.x * x;
        dest.y = this.y * y;
        return dest;
    }

    /**
     * Multiply this Vector2d component-wise by another Vector2d.
     * 
     * @param v
     *          the vector to multiply by
     * @return a vector holding the result
     */
    public Vector2d mul(Vector2dc v) {
        return mul(v, thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector2dc#mul(org.joml.Vector2dc, org.joml.Vector2d)
     */
    public Vector2d mul(Vector2dc v, Vector2d dest) {
        dest.x = x * v.x();
        dest.y = y * v.y();
        return dest;
    }

    /**
     * Multiply the given 3x2 matrix <code>mat</code> with <code>this</code>.
     * <p>
     * This method assumes the <code>z</code> component of <code>this</code> to be <code>1.0</code>.
     * 
     * @param mat
     *          the matrix to multiply this vector by
     * @return a vector holding the result
     */
    public Vector2d mulPosition(Matrix3x2dc mat) {
        return mulPosition(mat, thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector2dc#mulPosition(org.joml.Matrix3x2dc, org.joml.Vector2d)
     */
    public Vector2d mulPosition(Matrix3x2dc mat, Vector2d dest) {
        double rx = mat.m00() * x + mat.m10() * y + mat.m20();
        double ry = mat.m01() * x + mat.m11() * y + mat.m21();
        dest.x = rx;
        dest.y = ry;
        return dest;
    }

    /**
     * Multiply the given 3x2 matrix <code>mat</code> with <code>this</code>.
     * <p>
     * This method assumes the <code>z</code> component of <code>this</code> to be <code>0.0</code>.
     * 
     * @param mat
     *          the matrix to multiply this vector by
     * @return a vector holding the result
     */
    public Vector2d mulDirection(Matrix3x2dc mat) {
        return mulDirection(mat, thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector2dc#mulDirection(org.joml.Matrix3x2dc, org.joml.Vector2d)
     */
    public Vector2d mulDirection(Matrix3x2dc mat, Vector2d dest) {
        double rx = mat.m00() * x + mat.m10() * y;
        double ry = mat.m01() * x + mat.m11() * y;
        dest.x = rx;
        dest.y = ry;
        return dest;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector2dc#dot(org.joml.Vector2dc)
     */
    public double dot(Vector2dc v) {
        return x * v.x() + y * v.y();
    }

    /* (non-Javadoc)
     * @see org.joml.Vector2dc#angle(org.joml.Vector2dc)
     */
    public double angle(Vector2dc v) {
        double dot = x*v.x() + y*v.y();
        double det = x*v.y() - y*v.x();
        return Math.atan2(det, dot);
    }

    /* (non-Javadoc)
     * @see org.joml.Vector2dc#lengthSquared()
     */
    public double lengthSquared() {
        return lengthSquared(x, y);
    }

    /**
     * Get the length squared of a 2-dimensional double-precision vector.
     *
     * @param x The vector's x component
     * @param y The vector's y component
     *
     * @return the length squared of the given vector
     *
     * @author F. Neurath
     */
    public static double lengthSquared(double x, double y) {
        return x * x + y * y;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector2dc#length()
     */
    public double length() {
        return Math.sqrt(lengthSquared());
    }

    /**
     * Get the length of a 2-dimensional double-precision vector.
     *
     * @param x The vector's x component
     * @param y The vector's y component
     *
     * @return the length of the given vector
     *
     * @author F. Neurath
     */
    public static double length(double x, double y) {
        return Math.sqrt(lengthSquared(x, y));
    }

    /* (non-Javadoc)
     * @see org.joml.Vector2dc#distance(org.joml.Vector2dc)
     */
    public double distance(Vector2dc v) {
        return distance(v.x(), v.y());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector2dc#distanceSquared(org.joml.Vector2dc)
     */
    public double distanceSquared(Vector2dc v) {
        return distanceSquared(v.x(), v.y());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector2dc#distance(org.joml.Vector2fc)
     */
    public double distance(Vector2fc v) {
        return distance(v.x(), v.y());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector2dc#distanceSquared(org.joml.Vector2fc)
     */
    public double distanceSquared(Vector2fc v) {
        return distanceSquared(v.x(), v.y());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector2dc#distance(double, double)
     */
    public double distance(double x, double y) {
        return Math.sqrt(distanceSquared(x, y));
    }

    /* (non-Javadoc)
     * @see org.joml.Vector2dc#distanceSquared(double, double)
     */
    public double distanceSquared(double x, double y) {
        double dx = this.x - x;
        double dy = this.y - y;
        return dx * dx + dy * dy;
    }

    /**
     * Return the distance between <code>(x1, y1)</code> and <code>(x2, y2)</code>.
     *
     * @param x1
     *          the x component of the first vector
     * @param y1
     *          the y component of the first vector
     * @param x2
     *          the x component of the second vector
     * @param y2
     *          the y component of the second vector
     * @return the euclidean distance
     */
    public static double distance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(distanceSquared(x1, y1, x2, y2));
    }

    /**
     * Return the squared distance between <code>(x1, y1)</code> and <code>(x2, y2)</code>.
     *
     * @param x1
     *          the x component of the first vector
     * @param y1
     *          the y component of the first vector
     * @param x2
     *          the x component of the second vector
     * @param y2
     *          the y component of the second vector
     * @return the euclidean distance squared
     */
    public static double distanceSquared(double x1, double y1, double x2, double y2) {
        double dx = x1 - x2;
        double dy = y1 - y2;
        return dx * dx + dy * dy;
    }

    /**
     * Normalize this vector.
     * 
     * @return a vector holding the result
     */
    public Vector2d normalize() {
        return normalize(thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector2dc#normalize(org.joml.Vector2d)
     */
    public Vector2d normalize(Vector2d dest) {
        double invLength = 1.0 / Math.sqrt(x * x + y * y);
        dest.x = x * invLength;
        dest.y = y * invLength;
        return dest;
    }

    /**
     * Scale this vector to have the given length.
     * 
     * @param length
     *          the desired length
     * @return a vector holding the result
     */
    public Vector2d normalize(double length) {
        return normalize(length, thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector2dc#normalize(double, org.joml.Vector2d)
     */
    public Vector2d normalize(double length, Vector2d dest) {
        double invLength = 1.0 / Math.sqrt(x * x + y * y) * length;
        dest.x = x * invLength;
        dest.y = y * invLength;
        return dest;
    }

    /**
     * Add <code>v</code> to this vector.
     * 
     * @param v
     *          the vector to add
     * @return a vector holding the result
     */
    public Vector2d add(Vector2dc v) {
        return add(v, thisOrNew());
    }

    /**
     * Add <code>(x, y)</code> to this vector.
     * 
     * @param x
     *          the x component to add
     * @param y
     *          the y component to add
     * @return a vector holding the result
     */
    public Vector2d add(double x, double y) {
        return add(x, y, thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector2dc#add(double, double, org.joml.Vector2d)
     */
    public Vector2d add(double x, double y, Vector2d dest) {
        dest.x = this.x + x;
        dest.y = this.y + y;
        return dest;
    }

    /**
     * Add <code>v</code> to this vector.
     * 
     * @param v
     *          the vector to add
     * @return a vector holding the result
     */
    public Vector2d add(Vector2fc v) {
        return add(v, thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector2dc#add(org.joml.Vector2dc, org.joml.Vector2d)
     */
    public Vector2d add(Vector2dc v, Vector2d dest) {
        dest.x = x + v.x();
        dest.y = y + v.y();
        return dest;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector2dc#add(org.joml.Vector2fc, org.joml.Vector2d)
     */
    public Vector2d add(Vector2fc v, Vector2d dest) {
        dest.x = x + v.x();
        dest.y = y + v.y();
        return dest;
    }

    /**
     * Set all components to zero.
     * 
     * @return a vector holding the result
     */
    public Vector2d zero() {
        return thisOrNew().set(0, 0);
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
     * @return a vector holding the result
     */
    public Vector2d negate() {
        return negate(thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector2dc#negate(org.joml.Vector2d)
     */
    public Vector2d negate(Vector2d dest) {
        dest.x = -x;
        dest.y = -y;
        return dest;
    }

    /**
     * Linearly interpolate <code>this</code> and <code>other</code> using the given interpolation factor <code>t</code>
     * and store the result in <code>this</code>.
     * <p>
     * If <code>t</code> is <code>0.0</code> then the result is <code>this</code>. If the interpolation factor is <code>1.0</code>
     * then the result is <code>other</code>.
     * 
     * @param other
     *          the other vector
     * @param t
     *          the interpolation factor between 0.0 and 1.0
     * @return a vector holding the result
     */
    public Vector2d lerp(Vector2dc other, double t) {
        return lerp(other, t, thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector2dc#lerp(org.joml.Vector2dc, double, org.joml.Vector2d)
     */
    public Vector2d lerp(Vector2dc other, double t, Vector2d dest) {
        dest.x = x + (other.x() - x) * t;
        dest.y = y + (other.y() - y) * t;
        return dest;
    }

    public int hashCode() {
        final int prime = 31;
        int result = 1;
        long temp;
        temp = Double.doubleToLongBits(x);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Vector2d other = (Vector2d) obj;
        if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
            return false;
        if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y))
            return false;
        return true;
    }

    public boolean equals(Vector2dc v, double delta) {
        if (this == v)
            return true;
        if (v == null)
            return false;
        if (!(v instanceof Vector2dc))
            return false;
        if (!Runtime.equals(x, v.x(), delta))
            return false;
        if (!Runtime.equals(y, v.y(), delta))
            return false;
        return true;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector2dc#equals(double, double)
     */
    public boolean equals(double x, double y) {
        if (Double.doubleToLongBits(this.x) != Double.doubleToLongBits(x))
            return false;
        if (Double.doubleToLongBits(this.y) != Double.doubleToLongBits(y))
            return false;
        return true;
    }

    /**
     * Return a string representation of this vector.
     * <p>
     * This method creates a new {@link DecimalFormat} on every invocation with the format string "<code>0.000E0;-</code>".
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
     * @return a vector holding the result
     */
    public Vector2d fma(Vector2dc a, Vector2dc b) {
        return fma(a, b, thisOrNew());
    }

    /**
     * Add the component-wise multiplication of <code>a * b</code> to this vector.
     * 
     * @param a
     *          the first multiplicand
     * @param b
     *          the second multiplicand
     * @return a vector holding the result
     */
    public Vector2d fma(double a, Vector2dc b) {
        return fma(a, b, thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector2dc#fma(org.joml.Vector2dc, org.joml.Vector2dc, org.joml.Vector2d)
     */
    public Vector2d fma(Vector2dc a, Vector2dc b, Vector2d dest) {
        dest.x = x + a.x() * b.x();
        dest.y = y + a.y() * b.y();
        return dest;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector2dc#fma(double, org.joml.Vector2dc, org.joml.Vector2d)
     */
    public Vector2d fma(double a, Vector2dc b, Vector2d dest) {
        dest.x = x + a * b.x();
        dest.y = y + a * b.y();
        return dest;
    }

    /**
     * Set the components of this vector to be the component-wise minimum of this and the other vector.
     *
     * @param v
     *          the other vector
     * @return a vector holding the result
     */
    public Vector2d min(Vector2dc v) {
        return min(v, thisOrNew());
    }

    public Vector2d min(Vector2dc v, Vector2d dest) {
        dest.x = x < v.x() ? x : v.x();
        dest.y = y < v.y() ? y : v.y();
        return dest;
    }

    /**
     * Set the components of this vector to be the component-wise maximum of this and the other vector.
     *
     * @param v
     *          the other vector
     * @return a vector holding the result
     */
    public Vector2d max(Vector2dc v) {
        return max(v, thisOrNew());
    }

    public Vector2d max(Vector2dc v, Vector2d dest) {
        dest.x = x > v.x() ? x : v.x();
        dest.y = y > v.y() ? y : v.y();
        return dest;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector2dc#maxComponent()
     */
    public int maxComponent() {
        double absX = Math.abs(x);
        double absY = Math.abs(y);
        if (absX >= absY)
            return 0;
        return 1;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector2dc#minComponent()
     */
    public int minComponent() {
        double absX = Math.abs(x);
        double absY = Math.abs(y);
        if (absX < absY)
            return 0;
        return 1;
    }

    /**
     * Set each component of this vector to the largest (closest to positive
     * infinity) {@code double} value that is less than or equal to that
     * component and is equal to a mathematical integer.
     *
     * @return a vector holding the result
     */
    public Vector2d floor() {
        return floor(thisOrNew());
    }

    public Vector2d floor(Vector2d dest) {
        dest.x = Math.floor(x);
        dest.y = Math.floor(y);
        return dest;
    }

    /**
     * Set each component of this vector to the smallest (closest to negative
     * infinity) {@code double} value that is greater than or equal to that
     * component and is equal to a mathematical integer.
     *
     * @return a vector holding the result
     */
    public Vector2d ceil() {
        return ceil(thisOrNew());
    }

    public Vector2d ceil(Vector2d dest) {
        dest.x = Math.ceil(x);
        dest.y = Math.ceil(y);
        return dest;
    }

    /**
     * Set each component of this vector to the closest double that is equal to
     * a mathematical integer, with ties rounding to positive infinity.
     *
     * @return a vector holding the result
     */
    public Vector2d round() {
        return round(thisOrNew());
    }

    public Vector2d round(Vector2d dest) {
        dest.x = Math.round(x);
        dest.y = Math.round(y);
        return dest;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector2dc#isFinite()
     */
    public boolean isFinite() {
        return Math.isFinite(x) && Math.isFinite(y);
    }

}
