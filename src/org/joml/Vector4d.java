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
 * Contains the definition of a Vector comprising 4 doubles and associated transformations.
 * 
 * @author Richard Greenlees
 * @author Kai Burjack
 * @author F. Neurath
 */
public class Vector4d implements Externalizable, Vector4dc {

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
    public double w;

    /**
     * Create a new {@link Vector4d} of <code>(0, 0, 0, 1)</code>.
     */
    public Vector4d() {
        this.w = 1.0;
    }

    /**
     * Create a new {@link Vector4d} with the same values as <code>v</code>.
     * 
     * @param v
     *          the {@link Vector4dc} to copy the values from
     */
    public Vector4d(Vector4dc v) {
    	this(v.x(), v.y(), v.z(), v.w());
    }

    /**
     * Create a new {@link Vector4d} with the same values as <code>v</code>.
     * 
     * @param v
     *          the {@link Vector4ic} to copy the values from
     */
    public Vector4d(Vector4ic v) {
    	this(v.x(), v.y(), v.z(), v.w());
    }

    /**
     * Create a new {@link Vector4d} with the first three components from the
     * given <code>v</code> and the given <code>w</code>.
     * 
     * @param v
     *          the {@link Vector3dc}
     * @param w
     *          the w component
     */
    public Vector4d(Vector3dc v, double w) {
    	this(v.x(), v.y(), v.z(), w);
    }

    /**
     * Create a new {@link Vector4d} with the first three components from the
     * given <code>v</code> and the given <code>w</code>.
     * 
     * @param v
     *          the {@link Vector3ic}
     * @param w
     *          the w component
     */
    public Vector4d(Vector3ic v, double w) {
    	this(v.x(), v.y(), v.z(), w);
    }

    /**
     * Create a new {@link Vector4d} with the first two components from the
     * given <code>v</code> and the given <code>z</code> and <code>w</code>.
     *
     * @param v
     *          the {@link Vector2dc}
     * @param z
     *          the z component
     * @param w
     *          the w component
     */
    public Vector4d(Vector2dc v, double z, double w) {
    	this(v.x(), v.y(), z, w);
    }

    /**
     * Create a new {@link Vector4d} with the first two components from the
     * given <code>v</code> and the given <code>z</code> and <code>w</code>.
     *
     * @param v
     *          the {@link Vector2ic}
     * @param z
     *          the z component
     * @param w
     *          the w component
     */
    public Vector4d(Vector2ic v, double z, double w) {
    	this(v.x(), v.y(), z, w);
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
     * Create a new {@link Vector4d} with the same values as <code>v</code>.
     * 
     * @param v
     *          the {@link Vector4fc} to copy the values from
     */
    public Vector4d(Vector4fc v) {
    	this(v.x(), v.y(), v.z(), v.w());
    }

    /**
     * Create a new {@link Vector4d} with the x, y, and z components from the
     * given <code>v</code> and the w component from the given <code>w</code>.
     * 
     * @param v
     *          the {@link Vector3fc}
     * @param w
     *          the w component
     */
    public Vector4d(Vector3fc v, double w) {
    	this(v.x(), v.y(), v.z(), w);
    }

    /**
     * Create a new {@link Vector4d} with the x and y components from the
     * given <code>v</code> and the z and w components from the given <code>z</code> and <code>w</code>.
     *
     * @param v
     *          the {@link Vector2fc}
     * @param z
     *          the z component
     * @param w
     *          the w component
     */
    public Vector4d(Vector2fc v, double z, double w) {
    	this(v.x(), v.y(), z, w);
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

//#ifdef __HAS_NIO__
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
     *          values will be read in <code>x, y, z, w</code> order
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
     * @param buffer values will be read in <code>x, y, z, w</code> order
     */
    public Vector4d(int index, ByteBuffer buffer) {
        MemUtil.INSTANCE.get(this, index, buffer);
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
     * @param buffer values will be read in <code>x, y, z, w</code> order
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
     * @param buffer values will be read in <code>x, y, z, w</code> order
     */
    public Vector4d(int index, DoubleBuffer buffer) {
        MemUtil.INSTANCE.get(this, index, buffer);
    }
//#endif

    private Vector4d thisOrNew() {
        return this;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4dc#x()
     */
    public double x() {
        return this.x;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4dc#y()
     */
    public double y() {
        return this.y;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4dc#z()
     */
    public double z() {
        return this.z;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4dc#w()
     */
    public double w() {
        return this.w;
    }

    /**
     * Set this {@link Vector4d} to the values of the given <code>v</code>.
     * 
     * @param v
     *          the vector whose values will be copied into this
     * @return this
     */
    public Vector4d set(Vector4dc v) {
        return set(v.x(), v.y(), v.z(), v.w());
    }

    /**
     * Set this {@link Vector4d} to the values of the given <code>v</code>.
     * 
     * @param v
     *          the vector whose values will be copied into this
     * @return this
     */
    public Vector4d set(Vector4fc v) {
        return set(v.x(), v.y(), v.z(), v.w());
    }

    /**
     * Set this {@link Vector4d} to the values of the given <code>v</code>.
     * 
     * @param v
     *          the vector whose values will be copied into this
     * @return this
     */
    public Vector4d set(Vector4ic v) {
        return set(v.x(), v.y(), v.z(), v.w());
    }

    /**
     * Set the x, y, and z components of this to the components of
     * <code>v</code> and the w component to <code>w</code>.
     * 
     * @param v
     *          the {@link Vector3dc} to copy
     * @param w
     *          the w component
     * @return this
     */
    public Vector4d set(Vector3dc v, double w) {
        return set(v.x(), v.y(), v.z(), w);
    }

    /**
     * Set the x, y, and z components of this to the components of
     * <code>v</code> and the w component to <code>w</code>.
     * 
     * @param v
     *          the {@link Vector3ic} to copy
     * @param w
     *          the w component
     * @return this
     */
    public Vector4d set(Vector3ic v, double w) {
        return set(v.x(), v.y(), v.z(), w);
    }

    /**
     * Set the x, y, and z components of this to the components of
     * <code>v</code> and the w component to <code>w</code>.
     * 
     * @param v
     *          the {@link Vector3fc} to copy
     * @param w
     *          the w component
     * @return this
     */
    public Vector4d set(Vector3fc v, double w) {
        return set(v.x(), v.y(), v.z(), w);
    }

    /**
     * Set the x and y components from the given <code>v</code>
     * and the z and w components to the given <code>z</code> and <code>w</code>.
     *
     * @param v
     *          the {@link Vector2dc}
     * @param z
     *          the z component
     * @param w
     *          the w component
     * @return this
     */
    public Vector4d set(Vector2dc v, double z, double w) {
        return set(v.x(), v.y(), z, w);
    }

    /**
     * Set the x and y components from the given <code>v</code>
     * and the z and w components to the given <code>z</code> and <code>w</code>.
     *
     * @param v
     *          the {@link Vector2ic}
     * @param z
     *          the z component
     * @param w
     *          the w component
     * @return this
     */
    public Vector4d set(Vector2ic v, double z, double w) {
        return set(v.x(), v.y(), z, w);
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
     * Set the x and y components from the given <code>v</code>
     * and the z and w components to the given <code>z</code> and <code>w</code>.
     *
     * @param v
     *          the {@link Vector2fc}
     * @param z
     *          the z components
     * @param w
     *          the w components
     * @return this
     */
    public Vector4d set(Vector2fc v, double z, double w) {
        return set(v.x(), v.y(), z, w);
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
     *          values will be read in <code>x, y, z, w</code> order
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
     *          values will be read in <code>x, y, z, w</code> order
     * @return this
     */
    public Vector4d set(int index, ByteBuffer buffer) {
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
     *          values will be read in <code>x, y, z, w</code> order
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
     *          values will be read in <code>x, y, z, w</code> order
     * @return this
     */
    public Vector4d set(int index, DoubleBuffer buffer) {
        MemUtil.INSTANCE.get(this, index, buffer);
        return this;
    }
//#endif

//#ifndef __GWT__
    /**
     * Set the values of this vector by reading 4 double values from off-heap memory,
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
    public Vector4d setFromAddress(long address) {
        if (Options.NO_UNSAFE)
            throw new UnsupportedOperationException("Not supported when using joml.nounsafe");
        MemUtil.MemUtilUnsafe unsafe = (MemUtil.MemUtilUnsafe) MemUtil.INSTANCE;
        unsafe.get(this, address);
        return this;
    }
//#endif

    /**
     * Set the value of the specified component of this vector.
     *
     * @param component
     *          the component whose value to set, within <code>[0..3]</code>
     * @param value
     *          the value to set
     * @return this
     * @throws IllegalArgumentException if <code>component</code> is not within <code>[0..3]</code>
     */
    public Vector4d setComponent(int component, double value) throws IllegalArgumentException {
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
            case 3:
                w = value;
                break;
            default:
                throw new IllegalArgumentException();
        }
        return this;
    }

//#ifdef __HAS_NIO__
    /* (non-Javadoc)
     * @see org.joml.Vector4dc#get(java.nio.ByteBuffer)
     */
    public ByteBuffer get(ByteBuffer buffer) {
        return get(buffer.position(), buffer);
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4dc#get(int, java.nio.ByteBuffer)
     */
    public ByteBuffer get(int index, ByteBuffer buffer) {
        MemUtil.INSTANCE.put(this, index, buffer);
        return buffer;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4dc#get(java.nio.DoubleBuffer)
     */
    public DoubleBuffer get(DoubleBuffer buffer) {
        return get(buffer.position(), buffer);
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4dc#get(int, java.nio.DoubleBuffer)
     */
    public DoubleBuffer get(int index, DoubleBuffer buffer) {
        MemUtil.INSTANCE.put(this, index, buffer);
        return buffer;
    }
//#endif

//#ifndef __GWT__
    public Vector4dc getToAddress(long address) {
        if (Options.NO_UNSAFE)
            throw new UnsupportedOperationException("Not supported when using joml.nounsafe");
        MemUtil.MemUtilUnsafe unsafe = (MemUtil.MemUtilUnsafe) MemUtil.INSTANCE;
        unsafe.put(this, address);
        return this;
    }
//#endif

    /**
     * Subtract the supplied vector from this one.
     * 
     * @param v
     *          the vector to subtract
     * @return a vector holding the result
     */
    public Vector4d sub(Vector4dc v) {
        return sub(v, thisOrNew());
    }

    /**
     * Subtract the supplied vector from this one and store the result in <code>dest</code>.
     * 
     * @param v
     *          the vector to subtract
     * @param dest
     * 			will hold the result
     * @return dest
     */
    public Vector4d sub(Vector4dc v, Vector4d dest) {
        dest.x = x - v.x();
        dest.y = y - v.y();
        dest.z = z - v.z();
        dest.w = w - v.w();
        return dest;
    }

    /**
     * Subtract the supplied vector from this one.
     * 
     * @param v
     *          the vector to subtract
     * @return a vector holding the result
     */
    public Vector4d sub(Vector4fc v) {
        return sub(v, thisOrNew());
    }

    /**
     * Subtract the supplied vector from this one and store the result in <code>dest</code>.
     * 
     * @param v
     *          the vector to subtract
     * @param dest
     * 			will hold the result
     * @return dest
     */
    public Vector4d sub(Vector4fc v, Vector4d dest) {
        dest.x = x - v.x();
        dest.y = y - v.y();
        dest.z = z - v.z();
        dest.w = w - v.w();
        return dest;
    }

    /**
     * Subtract <code>(x, y, z, w)</code> from this.
     * 
     * @param x
     *          the x component to subtract
     * @param y
     *          the y component to subtract
     * @param z
     *          the z component to subtract
     * @param w
     *          the w component to subtract
     * @return a vector holding the result
     */
    public Vector4d sub(double x, double y, double z, double w) {
        return sub(x, y, z, w, thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4dc#sub(double, double, double, double, org.joml.Vector4d)
     */
    public Vector4d sub(double x, double y, double z, double w, Vector4d dest) {
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
     * @return a vector holding the result
     */
    public Vector4d add(Vector4dc v) {
        return add(v, thisOrNew());
    }

    public Vector4d add(Vector4dc v, Vector4d dest) {
        dest.x = x + v.x();
        dest.y = y + v.y();
        dest.z = z + v.z();
        dest.w = w + v.w();
        return dest;
    }

    public Vector4d add(Vector4fc v, Vector4d dest) {
        dest.x = x + v.x();
        dest.y = y + v.y();
        dest.z = z + v.z();
        dest.w = w + v.w();
        return dest;
    }

    /**
     * Add <code>(x, y, z, w)</code> to this.
     * 
     * @param x
     *          the x component to add
     * @param y
     *          the y component to add
     * @param z
     *          the z component to add
     * @param w
     *          the w component to add
     * @return a vector holding the result
     */
    public Vector4d add(double x, double y, double z, double w) {
        return add(x, y, z, w, thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4dc#add(double, double, double, double, org.joml.Vector4d)
     */
    public Vector4d add(double x, double y, double z, double w, Vector4d dest) {
        dest.x = this.x + x;
        dest.y = this.y + y;
        dest.z = this.z + z;
        dest.w = this.w + w;
        return dest;
    }

    /**
     * Add the supplied vector to this one.
     * 
     * @param v
     *          the vector to add
     * @return a vector holding the result
     */
    public Vector4d add(Vector4fc v) {
        return add(v, thisOrNew());
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
    public Vector4d fma(Vector4dc a, Vector4dc b) {
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
    public Vector4d fma(double a, Vector4dc b) {
        return fma(a, b, thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4dc#fma(org.joml.Vector4dc, org.joml.Vector4dc, org.joml.Vector4d)
     */
    public Vector4d fma(Vector4dc a, Vector4dc b, Vector4d dest) {
        dest.x = x + a.x() * b.x();
        dest.y = y + a.y() * b.y();
        dest.z = z + a.z() * b.z();
        dest.w = w + a.w() * b.w();
        return dest;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4dc#fma(double, org.joml.Vector4dc, org.joml.Vector4d)
     */
    public Vector4d fma(double a, Vector4dc b, Vector4d dest) {
        dest.x = x + a * b.x();
        dest.y = y + a * b.y();
        dest.z = z + a * b.z();
        dest.w = w + a * b.w();
        return dest;
    }

    /**
     * Multiply this {@link Vector4d} component-wise by the given {@link Vector4d}.
     * 
     * @param v
     *          the vector to multiply by
     * @return a vector holding the result
     */
    public Vector4d mul(Vector4dc v) {
        return mul(v, thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4dc#mul(org.joml.Vector4dc, org.joml.Vector4d)
     */
    public Vector4d mul(Vector4dc v, Vector4d dest) {
        dest.x = x * v.x();
        dest.y = y * v.y();
        dest.z = z * v.z();
        dest.w = w * v.w();
        return dest;
    }

    /**
     * Divide this {@link Vector4d} component-wise by the given {@link Vector4dc}.
     * 
     * @param v
     *          the vector to divide by
     * @return a vector holding the result
     */
    public Vector4d div(Vector4dc v) {
        return div(v, thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4dc#div(org.joml.Vector4dc, org.joml.Vector4d)
     */
    public Vector4d div(Vector4dc v, Vector4d dest) {
        dest.x = x / v.x();
        dest.y = y / v.y();
        dest.z = z / v.z();
        dest.w = w / v.w();
        return dest;
    }

    /**
     * Multiply this {@link Vector4d} component-wise by the given {@link Vector4fc}.
     * 
     * @param v
     *          the vector to multiply by
     * @return a vector holding the result
     */
    public Vector4d mul(Vector4fc v) {
        return mul(v, thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4dc#div(org.joml.Vector4fc, org.joml.Vector4d)
     */
    public Vector4d mul(Vector4fc v, Vector4d dest) {
        dest.x = x * v.x();
        dest.y = y * v.y();
        dest.z = z * v.z();
        dest.w = w * v.w();
        return dest;
    }

    /**
     * Multiply the given matrix <code>mat</code> with this {@link Vector4d}.
     * 
     * @param mat
     *          the matrix to multiply by
     * @return a vector holding the result
     */
    public Vector4d mul(Matrix4dc mat) {
        return mul(mat, thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4dc#mul(org.joml.Matrix4dc, org.joml.Vector4d)
     */
    public Vector4d mul(Matrix4dc mat, Vector4d dest) {
        if ((mat.properties() & Matrix4fc.PROPERTY_AFFINE) != 0)
            return mulAffine(mat, dest);
        return mulGeneric(mat, dest);
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4dc#mulAffine(org.joml.Matrix4dc, org.joml.Vector4d)
     */
    public Vector4d mulAffine(Matrix4dc mat, Vector4d dest) {
        double rx = mat.m00() * x + mat.m10() * y + mat.m20() * z + mat.m30() * w;
        double ry = mat.m01() * x + mat.m11() * y + mat.m21() * z + mat.m31() * w;
        double rz = mat.m02() * x + mat.m12() * y + mat.m22() * z + mat.m32() * w;
        dest.x = rx;
        dest.y = ry;
        dest.z = rz;
        dest.w = w;
        return dest;
    }

    private Vector4d mulGeneric(Matrix4dc mat, Vector4d dest) {
        double rx = mat.m00() * x + mat.m10() * y + mat.m20() * z + mat.m30() * w;
        double ry = mat.m01() * x + mat.m11() * y + mat.m21() * z + mat.m31() * w;
        double rz = mat.m02() * x + mat.m12() * y + mat.m22() * z + mat.m32() * w;
        double rw = mat.m03() * x + mat.m13() * y + mat.m23() * z + mat.m33() * w;
        dest.x = rx;
        dest.y = ry;
        dest.z = rz;
        dest.w = rw;
        return dest;
    }

    /**
     * Multiply the given matrix mat with this Vector4d and store the result in
     * <code>this</code>.
     * 
     * @param mat
     *          the matrix to multiply the vector with
     * @return a vector holding the result
     */
    public Vector4d mul(Matrix4x3dc mat) {
        return mul(mat, thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4dc#mul(org.joml.Matrix4x3dc, org.joml.Vector4d)
     */
    public Vector4d mul(Matrix4x3dc mat, Vector4d dest) {
        double rx = mat.m00() * x + mat.m10() * y + mat.m20() * z + mat.m30() * w;
        double ry = mat.m01() * x + mat.m11() * y + mat.m21() * z + mat.m31() * w;
        double rz = mat.m02() * x + mat.m12() * y + mat.m22() * z + mat.m32() * w;
        dest.x = rx;
        dest.y = ry;
        dest.z = rz;
        dest.w = w;
        return dest;
    }

    /**
     * Multiply the given matrix mat with this Vector4d and store the result in
     * <code>this</code>.
     * 
     * @param mat
     *          the matrix to multiply the vector with
     * @return a vector holding the result
     */
    public Vector4d mul(Matrix4x3fc mat) {
        return mul(mat, thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4dc#mul(org.joml.Matrix4x3fc, org.joml.Vector4d)
     */
    public Vector4d mul(Matrix4x3fc mat, Vector4d dest) {
        double rx = mat.m00() * x + mat.m10() * y + mat.m20() * z + mat.m30() * w;
        double ry = mat.m01() * x + mat.m11() * y + mat.m21() * z + mat.m31() * w;
        double rz = mat.m02() * x + mat.m12() * y + mat.m22() * z + mat.m32() * w;
        dest.x = rx;
        dest.y = ry;
        dest.z = rz;
        dest.w = w;
        return dest;
    }

    /**
     * Multiply the given matrix <code>mat</code> with this {@link Vector4d}.
     * 
     * @param mat
     *          the matrix to multiply by
     * @return a vector holding the result
     */
    public Vector4d mul(Matrix4fc mat) {
        return mul(mat, thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4dc#mul(org.joml.Matrix4fc, org.joml.Vector4d)
     */
    public Vector4d mul(Matrix4fc mat, Vector4d dest) {
        if ((mat.properties() & Matrix4fc.PROPERTY_AFFINE) != 0)
            return mulAffine(mat, dest);
        return mulGeneric(mat, dest);
    }
    private Vector4d mulAffine(Matrix4fc mat, Vector4d dest) {
        double rx = mat.m00() * x + mat.m10() * y + mat.m20() * z + mat.m30() * w;
        double ry = mat.m01() * x + mat.m11() * y + mat.m21() * z + mat.m31() * w;
        double rz = mat.m02() * x + mat.m12() * y + mat.m22() * z + mat.m32() * w;
        dest.x = rx;
        dest.y = ry;
        dest.z = rz;
        dest.w = w;
        return dest;
    }
    private Vector4d mulGeneric(Matrix4fc mat, Vector4d dest) {
        double rx = mat.m00() * x + mat.m10() * y + mat.m20() * z + mat.m30() * w;
        double ry = mat.m01() * x + mat.m11() * y + mat.m21() * z + mat.m31() * w;
        double rz = mat.m02() * x + mat.m12() * y + mat.m22() * z + mat.m32() * w;
        double rw = mat.m03() * x + mat.m13() * y + mat.m23() * z + mat.m33() * w;
        dest.x = rx;
        dest.y = ry;
        dest.z = rz;
        dest.w = rw;
        return dest;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4dc#mulProject(org.joml.Matrix4dc, org.joml.Vector4d)
     */
    public Vector4d mulProject(Matrix4dc mat, Vector4d dest) {
        double invW = 1.0 / (mat.m03() * x + mat.m13() * y + mat.m23() * z + mat.m33() * w);
        double rx = (mat.m00() * x + mat.m10() * y + mat.m20() * z + mat.m30()) * invW;
        double ry = (mat.m01() * x + mat.m11() * y + mat.m21() * z + mat.m31()) * invW;
        double rz = (mat.m02() * x + mat.m12() * y + mat.m22() * z + mat.m32()) * invW;
        dest.x = rx;
        dest.y = ry;
        dest.z = rz;
        dest.w = 1.0;
        return dest;
    }

    /**
     * Multiply the given matrix <code>mat</code> with this Vector4d, perform perspective division.
     * 
     * @param mat
     *          the matrix to multiply this vector by
     * @return a vector holding the result
     */
    public Vector4d mulProject(Matrix4dc mat) {
        return mulProject(mat, thisOrNew());
    }

    /**
     * Multiply this Vector4d by the given scalar value.
     * 
     * @param scalar
     *          the scalar to multiply by
     * @return a vector holding the result
     */
    public Vector4d mul(double scalar) {
        return mul(scalar, thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4dc#mul(double, org.joml.Vector4d)
     */
    public Vector4d mul(double scalar, Vector4d dest) {
        dest.x = x * scalar;
        dest.y = y * scalar;
        dest.z = z * scalar;
        dest.w = w * scalar;
        return dest;
    }

    /**
     * Divide this Vector4d by the given scalar value.
     * 
     * @param scalar
     *          the scalar to divide by
     * @return a vector holding the result
     */
    public Vector4d div(double scalar) {
        return div(scalar, thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4dc#div(double, org.joml.Vector4d)
     */
    public Vector4d div(double scalar, Vector4d dest) {
        double inv = 1.0 / scalar;
        dest.x = x * inv;
        dest.y = y * inv;
        dest.z = z * inv;
        dest.w = w * inv;
        return dest;
    }

    /**
     * Transform this vector by the given quaternion <code>quat</code> and store the result in <code>this</code>.
     * 
     * @see Quaterniond#transform(Vector4d)
     * 
     * @param quat
     *          the quaternion to transform this vector
     * @return a vector holding the result
     */
    public Vector4d rotate(Quaterniondc quat) {
        return rotate(quat, thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4dc#rotate(org.joml.Quaterniondc, org.joml.Vector4d)
     */
    public Vector4d rotate(Quaterniondc quat, Vector4d dest) {
        quat.transform(this, dest);
        return dest;
    }

    /**
     * Rotate this vector the specified radians around the given rotation axis.
     * 
     * @param angle
     *          the angle in radians
     * @param x
     *          the x component of the rotation axis
     * @param y
     *          the y component of the rotation axis
     * @param z
     *          the z component of the rotation axis
     * @return a vector holding the result
     */
    public Vector4d rotateAxis(double angle, double x, double y, double z) {
        return rotateAxis(angle, x, y, z, thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4dc#rotateAxis(double, double, double, double, org.joml.Vector4d)
     */
    public Vector4d rotateAxis(double angle, double aX, double aY, double aZ, Vector4d dest) {
        double hangle = angle * 0.5;
        double sinAngle = Math.sin(hangle);
        double qx = aX * sinAngle, qy = aY * sinAngle, qz = aZ * sinAngle;
        double qw = Math.cosFromSin(sinAngle, hangle);
        double w2 = qw * qw, x2 = qx * qx, y2 = qy * qy, z2 = qz * qz, zw = qz * qw;
        double xy = qx * qy, xz = qx * qz, yw = qy * qw, yz = qy * qz, xw = qx * qw;
        double nx = (w2 + x2 - z2 - y2) * x + (-zw + xy - zw + xy) * y + (yw + xz + xz + yw) * z;
        double ny = (xy + zw + zw + xy) * x + ( y2 - z2 + w2 - x2) * y + (yz + yz - xw - xw) * z;
        double nz = (xz - yw + xz - yw) * x + ( yz + yz + xw + xw) * y + (z2 - y2 - x2 + w2) * z;
        dest.x = nx;
        dest.y = ny;
        dest.z = nz;
        return dest;
    }

    /**
     * Rotate this vector the specified radians around the X axis.
     * 
     * @param angle
     *          the angle in radians
     * @return a vector holding the result
     */
    public Vector4d rotateX(double angle) {
        return rotateX(angle, thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4dc#rotateX(double, org.joml.Vector4d)
     */
    public Vector4d rotateX(double angle, Vector4d dest) {
        double sin = Math.sin(angle), cos = Math.cosFromSin(sin, angle);
        double y = this.y * cos - this.z * sin;
        double z = this.y * sin + this.z * cos;
        dest.x = this.x;
        dest.y = y;
        dest.z = z;
        dest.w = this.w;
        return dest;
    }

    /**
     * Rotate this vector the specified radians around the Y axis.
     * 
     * @param angle
     *          the angle in radians
     * @return a vector holding the result
     */
    public Vector4d rotateY(double angle) {
        return rotateY(angle, thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4dc#rotateY(double, org.joml.Vector4d)
     */
    public Vector4d rotateY(double angle, Vector4d dest) {
        double sin = Math.sin(angle), cos = Math.cosFromSin(sin, angle);
        double x =  this.x * cos + this.z * sin;
        double z = -this.x * sin + this.z * cos;
        dest.x = x;
        dest.y = this.y;
        dest.z = z;
        dest.w = this.w;
        return dest;
    }

    /**
     * Rotate this vector the specified radians around the Z axis.
     * 
     * @param angle
     *          the angle in radians
     * @return a vector holding the result
     */
    public Vector4d rotateZ(double angle) {
        return rotateZ(angle, thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4dc#rotateZ(double, org.joml.Vector4d)
     */
    public Vector4d rotateZ(double angle, Vector4d dest) {
        double sin = Math.sin(angle), cos = Math.cosFromSin(sin, angle);
        double x = this.x * cos - this.y * sin;
        double y = this.x * sin + this.y * cos;
        dest.x = x;
        dest.y = y;
        dest.z = this.z;
        dest.w = this.w;
        return dest;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4dc#lengthSquared()
     */
    public double lengthSquared() {
        return lengthSquared(x, y, z, w);
    }

    /**
     * Get the length squared of a 4-dimensional double-precision vector.
     *
     * @param x The vector's x component
     * @param y The vector's y component
     * @param z The vector's z component
     * @param w The vector's w component
     *
     * @return the length squared of the given vector
     *
     * @author F. Neurath
     */
    public static double lengthSquared(double x, double y, double z, double w) {
        return x * x + y * y + z * z + w * w;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4dc#length()
     */
    public double length() {
        return Math.sqrt(lengthSquared());
    }

    /**
     * Get the length of a 4-dimensional double-precision vector.
     *
     * @param x The vector's x component
     * @param y The vector's y component
     * @param z The vector's z component
     * @param w The vector's w component
     *
     * @return the length of the given vector
     *
     * @author F. Neurath
     */
    public static double length(double x, double y, double z, double w) {
        return Math.sqrt(lengthSquared(x, y, z, w));
    }

    /**
     * Normalizes this vector.
     * 
     * @return a vector holding the result
     */
    public Vector4d normalize() {
        return normalize(thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4dc#normalize(org.joml.Vector4d)
     */
    public Vector4d normalize(Vector4d dest) {
        double invLength = 1.0 / length();
        dest.x = x * invLength;
        dest.y = y * invLength;
        dest.z = z * invLength;
        dest.w = w * invLength;
        return dest;
    }

    /**
     * Scale this vector to have the given length.
     * 
     * @param length
     *          the desired length
     * @return a vector holding the result
     */
    public Vector4d normalize(double length) {
        return normalize(length, thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4dc#normalize(double, org.joml.Vector4d)
     */
    public Vector4d normalize(double length, Vector4d dest) {
        double invLength = 1.0 / length() * length;
        dest.x = x * invLength;
        dest.y = y * invLength;
        dest.z = z * invLength;
        dest.w = w * invLength;
        return dest;
    }

    /**
     * Normalize this vector by computing only the norm of <code>(x, y, z)</code>.
     * 
     * @return a vector holding the result
     */
    public Vector4d normalize3() {
        return normalize3(thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4dc#normalize3(org.joml.Vector4d)
     */
    public Vector4d normalize3(Vector4d dest) {
        double invLength = 1.0 / Math.sqrt(x * x + y * y + z * z);
        dest.x = x * invLength;
        dest.y = y * invLength;
        dest.z = z * invLength;
        dest.w = w * invLength;
        return dest;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4dc#distance(org.joml.Vector4dc)
     */
    public double distance(Vector4dc v) {
        return distance(v.x(), v.y(), v.z(), v.w());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4dc#distance(double, double, double, double)
     */
    public double distance(double x, double y, double z, double w) {
        return (float) Math.sqrt(distanceSquared(x, y, z, w));
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4dc#distanceSquared(org.joml.Vector4dc)
     */
    public double distanceSquared(Vector4dc v) {
        return distanceSquared(v.x(), v.y(), v.z(), v.w());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4dc#distanceSquared(double, double, double, double)
     */
    public double distanceSquared(double x, double y, double z, double w) {
        double dx = this.x - x;
        double dy = this.y - y;
        double dz = this.z - z;
        double dw = this.w - w;
        return dx * dx + dy * dy + dz * dz + dw * dw;
    }

    /**
     * Return the distance between <code>(x1, y1, z1, w1)</code> and <code>(x2, y2, z2, w2)</code>.
     *
     * @param x1
     *          the x component of the first vector
     * @param y1
     *          the y component of the first vector
     * @param z1
     *          the z component of the first vector
     * @param w1
     *          the w component of the first vector
     * @param x2
     *          the x component of the second vector
     * @param y2
     *          the y component of the second vector
     * @param z2
     *          the z component of the second vector
     * @param w2
     *          the 2 component of the second vector
     * @return the euclidean distance
     */
    public static double distance(double x1, double y1, double z1, double w1, double x2, double y2, double z2, double w2) {
        return Math.sqrt(distanceSquared(x1, y1, z1, w1, x2, y2, z2, w2));
    }

    /**
     * Return the squared distance between <code>(x1, y1, z1, w1)</code> and <code>(x2, y2, z2, w2)</code>.
     *
     * @param x1
     *          the x component of the first vector
     * @param y1
     *          the y component of the first vector
     * @param z1
     *          the z component of the first vector
     * @param w1
     *          the w component of the first vector
     * @param x2
     *          the x component of the second vector
     * @param y2
     *          the y component of the second vector
     * @param z2
     *          the z component of the second vector
     * @param w2
     *          the w component of the second vector
     * @return the euclidean distance squared
     */
    public static double distanceSquared(double x1, double y1, double z1, double w1, double x2, double y2, double z2, double w2) {
        double dx = x1 - x2;
        double dy = y1 - y2;
        double dz = z1 - z2;
        double dw = w1 - w2;
        return dx * dx + dy * dy + dz * dz + dw * dw;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4dc#dot(org.joml.Vector4dc)
     */
    public double dot(Vector4dc v) {
        return dot(v.x(), v.y(), v.z(), v.w());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4dc#dot(double, double, double, double)
     */
    public double dot(double x, double y, double z, double w) {
        return this.x * x + this.y * y + this.z * z + this.w * w;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4dc#angleCos(org.joml.Vector4dc)
     */
    public double angleCos(Vector4dc v) {
        double length1Squared = x * x + y * y + z * z + w * w;
        double length2Squared = v.x() * v.x() + v.y() * v.y() + v.z() * v.z() + v.w() * v.w();
        double dot = x * v.x() + y * v.y() + z * v.z() + w * v.w();
        return dot / (Math.sqrt(length1Squared * length2Squared));
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4dc#angle(org.joml.Vector4dc)
     */
    public double angle(Vector4dc v) {
        double cos = angleCos(v);
        // This is because sometimes cos goes above 1 or below -1 because of lost precision
        cos = cos < 1 ? cos : 1;
        cos = cos > -1 ? cos : -1;
        return Math.acos(cos);
    }

    /**
     * Set all components to zero.
     * 
     * @return a vector holding the result
     */
    public Vector4d zero() {
        return thisOrNew().set(0, 0, 0, 0);
    }

    /**
     * Negate this vector.
     * 
     * @return a vector holding the result
     */
    public Vector4d negate() {
        return negate(thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4dc#negate(org.joml.Vector4d)
     */
    public Vector4d negate(Vector4d dest) {
        dest.x = -x;
        dest.y = -y;
        dest.z = -z;
        dest.w = -w;
        return dest;
    }

    /**
     * Set the components of this vector to be the component-wise minimum of this and the other vector.
     *
     * @param v
     *          the other vector
     * @return a vector holding the result
     */
    public Vector4d min(Vector4dc v) {
        return min(v, thisOrNew());
    }

    public Vector4d min(Vector4dc v, Vector4d dest) {
        dest.x = x < v.x() ? x : v.x();
        dest.y = y < v.y() ? y : v.y();
        dest.z = z < v.z() ? z : v.z();
        dest.w = w < v.w() ? w : v.w();
        return dest;
    }

    /**
     * Set the components of this vector to be the component-wise maximum of this and the other vector.
     *
     * @param v
     *          the other vector
     * @return a vector holding the result
     */
    public Vector4d max(Vector4dc v) {
        return max(v, thisOrNew());
    }

    public Vector4d max(Vector4dc v, Vector4d dest) {
        dest.x = x > v.x() ? x : v.x();
        dest.y = y > v.y() ? y : v.y();
        dest.z = z > v.z() ? z : v.z();
        dest.w = w > v.w() ? w : v.w();
        return dest;
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
        return "(" + formatter.format(x) + " " + formatter.format(y) + " " + formatter.format(z) + " " + formatter.format(w) + ")";
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeDouble(x);
        out.writeDouble(y);
        out.writeDouble(z);
        out.writeDouble(w);
    }

    public void readExternal(ObjectInput in) throws IOException,
            ClassNotFoundException {
        x = in.readDouble();
        y = in.readDouble();
        z = in.readDouble();
        w = in.readDouble();
    }

    public int hashCode() {
        final int prime = 31;
        int result = 1;
        long temp;
        temp = Double.doubleToLongBits(w);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(x);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(z);
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
        Vector4d other = (Vector4d) obj;
        if (Double.doubleToLongBits(w) != Double.doubleToLongBits(other.w))
            return false;
        if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
            return false;
        if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y))
            return false;
        if (Double.doubleToLongBits(z) != Double.doubleToLongBits(other.z))
            return false;
        return true;
    }

    public boolean equals(Vector4dc v, double delta) {
        if (this == v)
            return true;
        if (v == null)
            return false;
        if (!(v instanceof Vector4dc))
            return false;
        if (!Runtime.equals(x, v.x(), delta))
            return false;
        if (!Runtime.equals(y, v.y(), delta))
            return false;
        if (!Runtime.equals(z, v.z(), delta))
            return false;
        if (!Runtime.equals(w, v.w(), delta))
            return false;
        return true;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4dc#equals(double, double, double, double)
     */
    public boolean equals(double x, double y, double z, double w) {
        if (Double.doubleToLongBits(this.x) != Double.doubleToLongBits(x))
            return false;
        if (Double.doubleToLongBits(this.y) != Double.doubleToLongBits(y))
            return false;
        if (Double.doubleToLongBits(this.z) != Double.doubleToLongBits(z))
            return false;
        if (Double.doubleToLongBits(this.w) != Double.doubleToLongBits(w))
            return false;
        return true;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4dc#smoothStep(org.joml.Vector4dc, double, org.joml.Vector4d)
     */
    public Vector4d smoothStep(Vector4dc v, double t, Vector4d dest) {
        double t2 = t * t;
        double t3 = t2 * t;
        dest.x = (x + x - v.x() - v.x()) * t3 + (3.0 * v.x() - 3.0 * x) * t2 + x * t + x;
        dest.y = (y + y - v.y() - v.y()) * t3 + (3.0 * v.y() - 3.0 * y) * t2 + y * t + y;
        dest.z = (z + z - v.z() - v.z()) * t3 + (3.0 * v.z() - 3.0 * z) * t2 + z * t + z;
        dest.w = (w + w - v.w() - v.w()) * t3 + (3.0 * v.w() - 3.0 * w) * t2 + w * t + w;
        return dest;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4dc#hermite(org.joml.Vector4dc, org.joml.Vector4dc, org.joml.Vector4dc, double, org.joml.Vector4d)
     */
    public Vector4d hermite(Vector4dc t0, Vector4dc v1, Vector4dc t1, double t, Vector4d dest) {
        double t2 = t * t;
        double t3 = t2 * t;
        dest.x = (x + x - v1.x() - v1.x() + t1.x() + t0.x()) * t3 + (3.0 * v1.x() - 3.0 * x - t0.x() - t0.x() - t1.x()) * t2 + x * t + x;
        dest.y = (y + y - v1.y() - v1.y() + t1.y() + t0.y()) * t3 + (3.0 * v1.y() - 3.0 * y - t0.y() - t0.y() - t1.y()) * t2 + y * t + y;
        dest.z = (z + z - v1.z() - v1.z() + t1.z() + t0.z()) * t3 + (3.0 * v1.z() - 3.0 * z - t0.z() - t0.z() - t1.z()) * t2 + z * t + z;
        dest.w = (w + w - v1.w() - v1.w() + t1.w() + t0.w()) * t3 + (3.0 * v1.w() - 3.0 * w - t0.w() - t0.w() - t1.w()) * t2 + w * t + w;
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
    public Vector4d lerp(Vector4dc other, double t) {
        return lerp(other, t, thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4dc#lerp(org.joml.Vector4dc, double, org.joml.Vector4d)
     */
    public Vector4d lerp(Vector4dc other, double t, Vector4d dest) {
        dest.x = x + (other.x() - x) * t;
        dest.y = y + (other.y() - y) * t;
        dest.z = z + (other.z() - z) * t;
        dest.w = w + (other.w() - w) * t;
        return dest;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4dc#get(int)
     */
    public double get(int component) throws IllegalArgumentException {
        switch (component) {
        case 0:
            return x;
        case 1:
            return y;
        case 2:
            return z;
        case 3:
            return w;
        default:
            throw new IllegalArgumentException();
        }
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4dc#maxComponent()
     */
    public int maxComponent() {
        double absX = Math.abs(x);
        double absY = Math.abs(y);
        double absZ = Math.abs(z);
        double absW = Math.abs(w);
        if (absX >= absY && absX >= absZ && absX >= absW) {
            return 0;
        } else if (absY >= absZ && absY >= absW) {
            return 1;
        } else if (absZ >= absW) {
            return 2;
        }
        return 3;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4dc#minComponent()
     */
    public int minComponent() {
        double absX = Math.abs(x);
        double absY = Math.abs(y);
        double absZ = Math.abs(z);
        double absW = Math.abs(w);
        if (absX < absY && absX < absZ && absX < absW) {
            return 0;
        } else if (absY < absZ && absY < absW) {
            return 1;
        } else if (absZ < absW) {
            return 2;
        }
        return 3;
    }

    /**
     * Set each component of this vector to the largest (closest to positive
     * infinity) {@code double} value that is less than or equal to that
     * component and is equal to a mathematical integer.
     *
     * @return a vector holding the result
     */
    public Vector4d floor() {
        return floor(thisOrNew());
    }

    public Vector4d floor(Vector4d dest) {
        dest.x = Math.floor(x);
        dest.y = Math.floor(y);
        dest.z = Math.floor(z);
        dest.w = Math.floor(w);
        return dest;
    }

    /**
     * Set each component of this vector to the smallest (closest to negative
     * infinity) {@code double} value that is greater than or equal to that
     * component and is equal to a mathematical integer.
     *
     * @return a vector holding the result
     */
    public Vector4d ceil() {
        return ceil(thisOrNew());
    }

    public Vector4d ceil(Vector4d dest) {
        dest.x = Math.ceil(x);
        dest.y = Math.ceil(y);
        dest.z = Math.ceil(z);
        dest.w = Math.ceil(w);
        return dest;
    }

    /**
     * Set each component of this vector to the closest double that is equal to
     * a mathematical integer, with ties rounding to positive infinity.
     *
     * @return a vector holding the result
     */
    public Vector4d round() {
        return round(thisOrNew());
    }

    public Vector4d round(Vector4d dest) {
        dest.x = Math.round(x);
        dest.y = Math.round(y);
        dest.z = Math.round(z);
        dest.w = Math.round(w);
        return dest;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4dc#isFinite()
     */
    public boolean isFinite() {
        return Math.isFinite(x) && Math.isFinite(y) && Math.isFinite(z) && Math.isFinite(w);
    }
}
