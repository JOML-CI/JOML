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

import org.joml.internal.MemUtil;
import org.joml.internal.Options;
import org.joml.internal.Runtime;

/**
 * Contains the definition of a Vector comprising 3 doubles and associated
 * transformations.
 *
 * @author Richard Greenlees
 * @author Kai Burjack
 * @author F. Neurath
 */
public class Vector3d implements Externalizable, Vector3dc {

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
     * Create a new {@link Vector3d} with all components set to zero.
     */
    public Vector3d() {
    }

    /**
     * Create a new {@link Vector3d} and initialize all three components with the given value.
     *
     * @param d
     *          the value of all three components
     */
    public Vector3d(double d) {
        this(d, d, d);
    }

    /**
     * Create a new {@link Vector3d} with the given component values.
     * 
     * @param x
     *          the value of x
     * @param y
     *          the value of y
     * @param z
     *          the value of z
     */
    public Vector3d(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Create a new {@link Vector3d} whose values will be copied from the given vector.
     * 
     * @param v
     *          provides the initial values for the new vector
     */
    public Vector3d(Vector3fc v) {
    	this(v.x(), v.y(), v.z());
    }

    /**
     * Create a new {@link Vector3d} whose values will be copied from the given vector.
     * 
     * @param v
     *          provides the initial values for the new vector
     */
    public Vector3d(Vector3ic v) {
    	this(v.x(), v.y(), v.z());
    }

    /**
     * Create a new {@link Vector3d} with the first two components from the
     * given <code>v</code> and the given <code>z</code>
     *
     * @param v
     *          the {@link Vector2fc} to copy the values from
     * @param z
     *          the z component
     */
    public Vector3d(Vector2fc v, double z) {
    	this(v.x(), v.y(), z);
    }

    /**
     * Create a new {@link Vector3d} with the first two components from the
     * given <code>v</code> and the given <code>z</code>
     *
     * @param v
     *          the {@link Vector2ic} to copy the values from
     * @param z
     *          the z component
     */
    public Vector3d(Vector2ic v, double z) {
    	this(v.x(), v.y(), z);
    }

    /**
     * Create a new {@link Vector3d} whose values will be copied from the given vector.
     * 
     * @param v
     *          provides the initial values for the new vector
     */
    public Vector3d(Vector3dc v) {
    	this(v.x(), v.y(), v.z());
    }

    /**
     * Create a new {@link Vector3d} with the first two components from the
     * given <code>v</code> and the given <code>z</code>
     *
     * @param v
     *          the {@link Vector2d} to copy the values from
     * @param z
     *          the z component
     */
    public Vector3d(Vector2dc v, double z) {
    	this(v.x(), v.y(), z);
    }

//#ifdef __HAS_NIO__
    /**
     * Create a new {@link Vector3d} and read this vector from the supplied {@link ByteBuffer}
     * at the current buffer {@link ByteBuffer#position() position}.
     * <p>
     * This method will not increment the position of the given ByteBuffer.
     * <p>
     * In order to specify the offset into the ByteBuffer at which
     * the vector is read, use {@link #Vector3d(int, ByteBuffer)}, taking
     * the absolute position as parameter.
     *
     * @param buffer values will be read in <code>x, y, z</code> order
     * @see #Vector3d(int, ByteBuffer)
     */
    public Vector3d(ByteBuffer buffer) {
        this(buffer.position(), buffer);
    }

    /**
     * Create a new {@link Vector3d} and read this vector from the supplied {@link ByteBuffer}
     * starting at the specified absolute buffer position/index.
     * <p>
     * This method will not increment the position of the given ByteBuffer.
     *
     * @param index  the absolute position into the ByteBuffer
     * @param buffer values will be read in <code>x, y, z</code> order
     */
    public Vector3d(int index, ByteBuffer buffer) {
        MemUtil.INSTANCE.get(this, index, buffer);
    }

    /**
     * Create a new {@link Vector3d} and read this vector from the supplied {@link DoubleBuffer}
     * at the current buffer {@link DoubleBuffer#position() position}.
     * <p>
     * This method will not increment the position of the given DoubleBuffer.
     * <p>
     * In order to specify the offset into the DoubleBuffer at which
     * the vector is read, use {@link #Vector3d(int, DoubleBuffer)}, taking
     * the absolute position as parameter.
     *
     * @param buffer values will be read in <code>x, y, z</code> order
     * @see #Vector3d(int, DoubleBuffer)
     */
    public Vector3d(DoubleBuffer buffer) {
        this(buffer.position(), buffer);
    }

    /**
     * Create a new {@link Vector3d} and read this vector from the supplied {@link DoubleBuffer}
     * starting at the specified absolute buffer position/index.
     * <p>
     * This method will not increment the position of the given DoubleBuffer.
     *
     * @param index  the absolute position into the DoubleBuffer
     * @param buffer values will be read in <code>x, y, z</code> order
     */
    public Vector3d(int index, DoubleBuffer buffer) {
        MemUtil.INSTANCE.get(this, index, buffer);
    }
//#endif

    private Vector3d thisOrNew() {
        return this;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3dc#x()
     */
    public double x() {
        return this.x;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3dc#y()
     */
    public double y() {
        return this.y;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3dc#z()
     */
    public double z() {
        return this.z;
    }

    /**
     * Set the x, y and z components to match the supplied vector.
     * 
     * @param v
     *          the vector to set this vector's components from
     * @return this
     */
    public Vector3d set(Vector3dc v) {
        return set(v.x(), v.y(), v.z());
    }

    /**
     * Set the x, y and z components to match the supplied vector.
     * 
     * @param v
     *          the vector to set this vector's components from
     * @return this
     */
    public Vector3d set(Vector3ic v) {
        return set(v.x(), v.y(), v.z());
    }

    /**
     * Set the first two components from the given <code>v</code>
     * and the z component from the given <code>z</code>
     *
     * @param v
     *          the {@link Vector2dc} to copy the values from
     * @param z
     *          the z component
     * @return this
     */
    public Vector3d set(Vector2dc v, double z) {
        return set(v.x(), v.y(), z);
    }

    /**
     * Set the first two components from the given <code>v</code>
     * and the z component from the given <code>z</code>
     *
     * @param v
     *          the {@link Vector2ic} to copy the values from
     * @param z
     *          the z component
     * @return this
     */
    public Vector3d set(Vector2ic v, double z) {
        return set(v.x(), v.y(), z);
    }

    /**
     * Set the x, y and z components to match the supplied vector.
     * 
     * @param v
     *          the vector to set this vector's components from
     * @return this
     */
    public Vector3d set(Vector3fc v) {
        return set(v.x(), v.y(), v.z());
    }

    /**
     * Set the first two components from the given <code>v</code>
     * and the z component from the given <code>z</code>
     *
     * @param v
     *          the {@link Vector2fc} to copy the values from
     * @param z
     *          the z component
     * @return this
     */
    public Vector3d set(Vector2fc v, double z) {
        return set(v.x(), v.y(), z);
    }

    /**
     * Set the x, y, and z components to the supplied value.
     *
     * @param d
     *          the value of all three components
     * @return this
     */
    public Vector3d set(double d) {
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
    public Vector3d set(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
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
     *          values will be read in <code>x, y, z</code> order
     * @return this
     * @see #set(int, ByteBuffer)
     */
    public Vector3d set(ByteBuffer buffer) {
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
     *          values will be read in <code>x, y, z</code> order
     * @return this
     */
    public Vector3d set(int index, ByteBuffer buffer) {
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
     *          values will be read in <code>x, y, z</code> order
     * @return this
     * @see #set(int, DoubleBuffer)
     */
    public Vector3d set(DoubleBuffer buffer) {
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
     *          values will be read in <code>x, y, z</code> order
     * @return this
     */
    public Vector3d set(int index, DoubleBuffer buffer) {
        MemUtil.INSTANCE.get(this, index, buffer);
        return this;
    }
//#endif

//#ifndef __GWT__
    /**
     * Set the values of this vector by reading 3 double values from off-heap memory,
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
    public Vector3d setFromAddress(long address) {
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
     *          the component whose value to set, within <code>[0..2]</code>
     * @param value
     *          the value to set
     * @return this
     * @throws IllegalArgumentException if <code>component</code> is not within <code>[0..2]</code>
     */
    public Vector3d setComponent(int component, double value) throws IllegalArgumentException {
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

//#ifdef __HAS_NIO__
    /* (non-Javadoc)
     * @see org.joml.Vector3dc#get(java.nio.ByteBuffer)
     */
    public ByteBuffer get(ByteBuffer buffer) {
        return get(buffer.position(), buffer);
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3dc#get(int, java.nio.ByteBuffer)
     */
    public ByteBuffer get(int index, ByteBuffer buffer) {
        MemUtil.INSTANCE.put(this, index, buffer);
        return buffer;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3dc#get(java.nio.DoubleBuffer)
     */
    public DoubleBuffer get(DoubleBuffer buffer) {
        return get(buffer.position(), buffer);
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3dc#get(int, java.nio.DoubleBuffer)
     */
    public DoubleBuffer get(int index, DoubleBuffer buffer) {
        MemUtil.INSTANCE.put(this, index, buffer);
        return buffer;
    }
//#endif

//#ifndef __GWT__
    public Vector3dc getToAddress(long address) {
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
     *          the vector to subtract from this
     * @return a vector holding the result
     */
    public Vector3d sub(Vector3dc v) {
        return sub(v, thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3dc#sub(org.joml.Vector3dc, org.joml.Vector3d)
     */
    public Vector3d sub(Vector3dc v, Vector3d dest) {
        dest.x = x - v.x();
        dest.y = y - v.y();
        dest.z = z - v.z();
        return dest;
    }

    /**
     * Subtract the supplied vector from this one.
     * 
     * @param v
     *          the vector to subtract from this
     * @return a vector holding the result
     */
    public Vector3d sub(Vector3fc v) {
        return sub(v, thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3dc#sub(org.joml.Vector3fc, org.joml.Vector3d)
     */
    public Vector3d sub(Vector3fc v, Vector3d dest) {
        dest.x = x - v.x();
        dest.y = y - v.y();
        dest.z = z - v.z();
        return dest;
    }

    /**
     * Subtract <code>(x, y, z)</code> from this vector.
     * 
     * @param x
     *          the x component to subtract
     * @param y
     *          the y component to subtract
     * @param z
     *          the z component to subtract
     * @return a vector holding the result
     */
    public Vector3d sub(double x, double y, double z) {
        return sub(x, y, z, thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3dc#sub(double, double, double, org.joml.Vector3d)
     */
    public Vector3d sub(double x, double y, double z, Vector3d dest) {
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
     * @return a vector holding the result
     */
    public Vector3d add(Vector3dc v) {
        return add(v, thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3dc#add(org.joml.Vector3dc, org.joml.Vector3d)
     */
    public Vector3d add(Vector3dc v, Vector3d dest) {
        dest.x = x + v.x();
        dest.y = y + v.y();
        dest.z = z + v.z();
        return dest;
    }

    /**
     * Add the supplied vector to this one.
     * 
     * @param v
     *          the vector to add
     * @return a vector holding the result
     */
    public Vector3d add(Vector3fc v) {
        return add(v, thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3dc#add(org.joml.Vector3fc, org.joml.Vector3d)
     */
    public Vector3d add(Vector3fc v, Vector3d dest) {
        dest.x = x + v.x();
        dest.y = y + v.y();
        dest.z = z + v.z();
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
     * @return a vector holding the result
     */
    public Vector3d add(double x, double y, double z) {
        return add(x, y, z, thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3dc#add(double, double, double, org.joml.Vector3d)
     */
    public Vector3d add(double x, double y, double z, Vector3d dest) {
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
     * @return a vector holding the result
     */
    public Vector3d fma(Vector3dc a, Vector3dc b) {
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
    public Vector3d fma(double a, Vector3dc b) {
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
    public Vector3d fma(Vector3fc a, Vector3fc b) {
        return fma(a, b, thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3dc#fma(org.joml.Vector3fc, org.joml.Vector3fc, org.joml.Vector3f)
     */
    public Vector3d fma(Vector3fc a, Vector3fc b, Vector3d dest) {
        dest.x = x + a.x() * b.x();
        dest.y = y + a.y() * b.y();
        dest.z = z + a.z() * b.z();
        return dest;
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
    public Vector3d fma(double a, Vector3fc b) {
        return fma(a, b, thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3dc#fma(org.joml.Vector3dc, org.joml.Vector3dc, org.joml.Vector3d)
     */
    public Vector3d fma(Vector3dc a, Vector3dc b, Vector3d dest) {
        dest.x = x + a.x() * b.x();
        dest.y = y + a.y() * b.y();
        dest.z = z + a.z() * b.z();
        return dest;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3dc#fma(double, org.joml.Vector3dc, org.joml.Vector3d)
     */
    public Vector3d fma(double a, Vector3dc b, Vector3d dest) {
        dest.x = x + a * b.x();
        dest.y = y + a * b.y();
        dest.z = z + a * b.z();
        return dest;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3dc#fma(org.joml.Vector3dc, org.joml.Vector3fc, org.joml.Vector3d)
     */
    public Vector3d fma(Vector3dc a, Vector3fc b, Vector3d dest) {
        dest.x = x + a.x() * b.x();
        dest.y = y + a.y() * b.y();
        dest.z = z + a.z() * b.z();
        return dest;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3dc#fma(double, org.joml.Vector3fc, org.joml.Vector3d)
     */
    public Vector3d fma(double a, Vector3fc b, Vector3d dest) {
        dest.x = x + a * b.x();
        dest.y = y + a * b.y();
        dest.z = z + a * b.z();
        return dest;
    }

    /**
     * Multiply this Vector3d component-wise by another Vector3dc.
     * 
     * @param v
     *          the vector to multiply by
     * @return a vector holding the result
     */
    public Vector3d mul(Vector3dc v) {
        return mul(v, thisOrNew());
    }

    /**
     * Multiply this Vector3d component-wise by another Vector3fc.
     * 
     * @param v
     *          the vector to multiply by
     * @return a vector holding the result
     */
    public Vector3d mul(Vector3fc v) {
        return mul(v, thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3dc#mul(org.joml.Vector3fc, org.joml.Vector3d)
     */
    public Vector3d mul(Vector3fc v, Vector3d dest) {
        dest.x = x * v.x();
        dest.y = y * v.y();
        dest.z = z * v.z();
        return dest;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3dc#mul(org.joml.Vector3dc, org.joml.Vector3d)
     */
    public Vector3d mul(Vector3dc v, Vector3d dest) {
        dest.x = x * v.x();
        dest.y = y * v.y();
        dest.z = z * v.z();
        return dest;
    }

    /**
     * Divide this Vector3d component-wise by another Vector3dc.
     * 
     * @param v
     *          the vector to divide by
     * @return a vector holding the result
     */
    public Vector3d div(Vector3d v) {
        return div(v, thisOrNew());
    }

    /**
     * Divide this Vector3d component-wise by another Vector3fc.
     * 
     * @param v
     *          the vector to divide by
     * @return a vector holding the result
     */
    public Vector3d div(Vector3fc v) {
        return div(v, thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3dc#div(org.joml.Vector3fc, org.joml.Vector3d)
     */
    public Vector3d div(Vector3fc v, Vector3d dest) {
        dest.x = x / v.x();
        dest.y = y / v.y();
        dest.z = z / v.z();
        return dest;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3dc#div(org.joml.Vector3dc, org.joml.Vector3d)
     */
    public Vector3d div(Vector3dc v, Vector3d dest) {
        dest.x = x / v.x();
        dest.y = y / v.y();
        dest.z = z / v.z();
        return dest;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3dc#mulProject(org.joml.Matrix4dc, org.joml.Vector3d)
     */
    public Vector3d mulProject(Matrix4dc mat, Vector3d dest) {
        double invW = 1.0 / (mat.m03() * x + mat.m13() * y + mat.m23() * z + mat.m33());
        double rx = (mat.m00() * x + mat.m10() * y + mat.m20() * z + mat.m30()) * invW;
        double ry = (mat.m01() * x + mat.m11() * y + mat.m21() * z + mat.m31()) * invW;
        double rz = (mat.m02() * x + mat.m12() * y + mat.m22() * z + mat.m32()) * invW;
        dest.x = rx;
        dest.y = ry;
        dest.z = rz;
        return dest;
    }

    /**
     * Multiply the given matrix <code>mat</code> this Vector3d, perform perspective division.
     * <p>
     * This method uses <code>w=1.0</code> as the fourth vector component.
     * 
     * @param mat
     *          the matrix to multiply this vector by
     * @return a vector holding the result
     */
    public Vector3d mulProject(Matrix4dc mat) {
        return mulProject(mat, thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3dc#mulProject(org.joml.Matrix4fc, org.joml.Vector3d)
     */
    public Vector3d mulProject(Matrix4fc mat, Vector3d dest) {
        double invW = 1.0 / (mat.m03() * x + mat.m13() * y + mat.m23() * z + mat.m33());
        double rx = (mat.m00() * x + mat.m10() * y + mat.m20() * z + mat.m30()) * invW;
        double ry = (mat.m01() * x + mat.m11() * y + mat.m21() * z + mat.m31()) * invW;
        double rz = (mat.m02() * x + mat.m12() * y + mat.m22() * z + mat.m32()) * invW;
        dest.x = rx;
        dest.y = ry;
        dest.z = rz;
        return dest;
    }

    /**
     * Multiply the given matrix <code>mat</code> with this Vector3d, perform perspective division.
     * <p>
     * This method uses <code>w=1.0</code> as the fourth vector component.
     * 
     * @param mat
     *          the matrix to multiply this vector by
     * @return a vector holding the result
     */
    public Vector3d mulProject(Matrix4fc mat) {
        return mulProject(mat, thisOrNew());
    }

    /**
     * Multiply the given matrix <code>mat</code> with this Vector3d.
     * 
     * @param mat
     *          the matrix to multiply this vector by
     * @return a vector holding the result
     */
    public Vector3d mul(Matrix3fc mat) {
        return mul(mat, thisOrNew());
    }

    /**
     * Multiply the given matrix <code>mat</code> with this Vector3d.
     * 
     * @param mat
     *          the matrix to multiply this vector by
     * @return a vector holding the result
     */
    public Vector3d mul(Matrix3dc mat) {
        return mul(mat, thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3dc#mul(org.joml.Matrix3dc, org.joml.Vector3d)
     */
    public Vector3d mul(Matrix3dc mat, Vector3d dest) {
        double rx = mat.m00() * x + mat.m10() * y + mat.m20() * z;
        double ry = mat.m01() * x + mat.m11() * y + mat.m21() * z;
        double rz = mat.m02() * x + mat.m12() * y + mat.m22() * z;
        dest.x = rx;
        dest.y = ry;
        dest.z = rz;
        return dest;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3dc#mul(org.joml.Matrix3dc, org.joml.Vector3f)
     */
    public Vector3f mul(Matrix3dc mat, Vector3f dest) {
        double rx = mat.m00() * x + mat.m10() * y + mat.m20() * z;
        double ry = mat.m01() * x + mat.m11() * y + mat.m21() * z;
        double rz = mat.m02() * x + mat.m12() * y + mat.m22() * z;
        dest.x = (float) rx;
        dest.y = (float) ry;
        dest.z = (float) rz;
        return dest;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3dc#mul(org.joml.Matrix3fc, org.joml.Vector3d)
     */
    public Vector3d mul(Matrix3fc mat, Vector3d dest) {
        double rx = mat.m00() * x + mat.m10() * y + mat.m20() * z;
        double ry = mat.m01() * x + mat.m11() * y + mat.m21() * z;
        double rz = mat.m02() * x + mat.m12() * y + mat.m22() * z;
        dest.x = rx;
        dest.y = ry;
        dest.z = rz;
        return dest;
    }

    /**
     * Multiply the given matrix with this Vector3d by assuming a third row in the matrix of <code>(0, 0, 1)</code>
     * and store the result in <code>this</code>.
     * 
     * @param mat
     *          the matrix
     * @return a vector holding the result
     */
    public Vector3d mul(Matrix3x2dc mat) {
        return mul(mat, thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3dc#mul(org.joml.Matrix3x2dc, org.joml.Vector3d)
     */
    public Vector3d mul(Matrix3x2dc mat, Vector3d dest) {
        double rx = mat.m00() * x + mat.m10() * y + mat.m20() * z;
        double ry = mat.m01() * x + mat.m11() * y + mat.m21() * z;
        dest.x = rx;
        dest.y = ry;
        dest.z = z;
        return dest;
    }

    /**
     * Multiply the given matrix with this Vector3d by assuming a third row in the matrix of <code>(0, 0, 1)</code>
     * and store the result in <code>this</code>.
     * 
     * @param mat
     *          the matrix
     * @return a vector holding the result
     */
    public Vector3d mul(Matrix3x2fc mat) {
        return mul(mat, thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3dc#mul(org.joml.Matrix3x2fc, org.joml.Vector3d)
     */
    public Vector3d mul(Matrix3x2fc mat, Vector3d dest) {
        double rx = mat.m00() * x + mat.m10() * y + mat.m20() * z;
        double ry = mat.m01() * x + mat.m11() * y + mat.m21() * z;
        dest.x = rx;
        dest.y = ry;
        dest.z = z;
        return dest;
    }

    /**
     * Multiply the transpose of the given matrix with this Vector3d and store the result in <code>this</code>.
     * 
     * @param mat
     *          the matrix
     * @return a vector holding the result
     */
    public Vector3d mulTranspose(Matrix3dc mat) {
        return mulTranspose(mat, thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3dc#mulTranspose(org.joml.Matrix3dc, org.joml.Vector3d)
     */
    public Vector3d mulTranspose(Matrix3dc mat, Vector3d dest) {
        double rx = mat.m00() * x + mat.m01() * y + mat.m02() * z;
        double ry = mat.m10() * x + mat.m11() * y + mat.m12() * z;
        double rz = mat.m20() * x + mat.m21() * y + mat.m22() * z;
        dest.x = rx;
        dest.y = ry;
        dest.z = rz;
        return dest;
    }

    /**
     * Multiply the transpose of the given matrix with  this Vector3d and store the result in <code>this</code>.
     * 
     * @param mat
     *          the matrix
     * @return a vector holding the result
     */
    public Vector3d mulTranspose(Matrix3fc mat) {
        return mulTranspose(mat, thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3dc#mulTranspose(org.joml.Matrix3fc, org.joml.Vector3d)
     */
    public Vector3d mulTranspose(Matrix3fc mat, Vector3d dest) {
        double rx = mat.m00() * x + mat.m01() * y + mat.m02() * z;
        double ry = mat.m10() * x + mat.m11() * y + mat.m12() * z;
        double rz = mat.m20() * x + mat.m21() * y + mat.m22() * z;
        dest.x = rx;
        dest.y = ry;
        dest.z = rz;
        return dest;
    }

    /**
     * Multiply the given 4x4 matrix <code>mat</code> with <code>this</code>.
     * <p>
     * This method assumes the <code>w</code> component of <code>this</code> to be <code>1.0</code>.
     * 
     * @param mat
     *          the matrix to multiply this vector by
     * @return a vector holding the result
     */
    public Vector3d mulPosition(Matrix4fc mat) {
        return mulPosition(mat, thisOrNew());
    }

    /**
     * Multiply the given 4x4 matrix <code>mat</code> with <code>this</code>.
     * <p>
     * This method assumes the <code>w</code> component of <code>this</code> to be <code>1.0</code>.
     * 
     * @param mat
     *          the matrix to multiply this vector by
     * @return a vector holding the result
     */
    public Vector3d mulPosition(Matrix4dc mat) {
        return mulPosition(mat, thisOrNew());
    }

    /**
     * Multiply the given 4x3 matrix <code>mat</code> with <code>this</code>.
     * <p>
     * This method assumes the <code>w</code> component of <code>this</code> to be <code>1.0</code>.
     * 
     * @param mat
     *          the matrix to multiply this vector by
     * @return a vector holding the result
     */
    public Vector3d mulPosition(Matrix4x3dc mat) {
        return mulPosition(mat, thisOrNew());
    }

    /**
     * Multiply the given 4x3 matrix <code>mat</code> with <code>this</code>.
     * <p>
     * This method assumes the <code>w</code> component of <code>this</code> to be <code>1.0</code>.
     * 
     * @param mat
     *          the matrix to multiply this vector by
     * @return a vector holding the result
     */
    public Vector3d mulPosition(Matrix4x3fc mat) {
        return mulPosition(mat, thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3dc#mulPosition(org.joml.Matrix4dc, org.joml.Vector3d)
     */
    public Vector3d mulPosition(Matrix4dc mat, Vector3d dest) {
        double rx = mat.m00() * x + mat.m10() * y + mat.m20() * z + mat.m30();
        double ry = mat.m01() * x + mat.m11() * y + mat.m21() * z + mat.m31();
        double rz = mat.m02() * x + mat.m12() * y + mat.m22() * z + mat.m32();
        dest.x = rx;
        dest.y = ry;
        dest.z = rz;
        return dest;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3dc#mulPosition(org.joml.Matrix4fc, org.joml.Vector3d)
     */
    public Vector3d mulPosition(Matrix4fc mat, Vector3d dest) {
        double rx = mat.m00() * x + mat.m10() * y + mat.m20() * z + mat.m30();
        double ry = mat.m01() * x + mat.m11() * y + mat.m21() * z + mat.m31();
        double rz = mat.m02() * x + mat.m12() * y + mat.m22() * z + mat.m32();
        dest.x = rx;
        dest.y = ry;
        dest.z = rz;
        return dest;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3dc#mulPosition(org.joml.Matrix4x3dc, org.joml.Vector3d)
     */
    public Vector3d mulPosition(Matrix4x3dc mat, Vector3d dest) {
        double rx = mat.m00() * x + mat.m10() * y + mat.m20() * z + mat.m30();
        double ry = mat.m01() * x + mat.m11() * y + mat.m21() * z + mat.m31();
        double rz = mat.m02() * x + mat.m12() * y + mat.m22() * z + mat.m32();
        dest.x = rx;
        dest.y = ry;
        dest.z = rz;
        return dest;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3dc#mulPosition(org.joml.Matrix4x3fc, org.joml.Vector3d)
     */
    public Vector3d mulPosition(Matrix4x3fc mat, Vector3d dest) {
        double rx = mat.m00() * x + mat.m10() * y + mat.m20() * z + mat.m30();
        double ry = mat.m01() * x + mat.m11() * y + mat.m21() * z + mat.m31();
        double rz = mat.m02() * x + mat.m12() * y + mat.m22() * z + mat.m32();
        dest.x = rx;
        dest.y = ry;
        dest.z = rz;
        return dest;
    }

    /**
     * Multiply the transpose of the given 4x4 matrix <code>mat</code> with <code>this</code>.
     * <p>
     * This method assumes the <code>w</code> component of <code>this</code> to be <code>1.0</code>.
     * 
     * @param mat
     *          the matrix whose transpose to multiply this vector by
     * @return a vector holding the result
     */
    public Vector3d mulTransposePosition(Matrix4dc mat) {
        return mulTransposePosition(mat, thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3dc#mulTransposePosition(org.joml.Matrix4dc, org.joml.Vector3d)
     */
    public Vector3d mulTransposePosition(Matrix4dc mat, Vector3d dest) {
        double rx = mat.m00() * x + mat.m01() * y + mat.m02() * z + mat.m03();
        double ry = mat.m10() * x + mat.m11() * y + mat.m12() * z + mat.m13();
        double rz = mat.m20() * x + mat.m21() * y + mat.m22() * z + mat.m23();
        dest.x = rx;
        dest.y = ry;
        dest.z = rz;
        return dest;
    }

    /**
     * Multiply the transpose of the given 4x4 matrix <code>mat</code> with <code>this</code>.
     * <p>
     * This method assumes the <code>w</code> component of <code>this</code> to be <code>1.0</code>.
     * 
     * @param mat
     *          the matrix whose transpose to multiply this vector by
     * @return a vector holding the result
     */
    public Vector3d mulTransposePosition(Matrix4fc mat) {
        return mulTransposePosition(mat, thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3dc#mulTransposePosition(org.joml.Matrix4fc, org.joml.Vector3d)
     */
    public Vector3d mulTransposePosition(Matrix4fc mat, Vector3d dest) {
        double rx = mat.m00() * x + mat.m01() * y + mat.m02() * z + mat.m03();
        double ry = mat.m10() * x + mat.m11() * y + mat.m12() * z + mat.m13();
        double rz = mat.m20() * x + mat.m21() * y + mat.m22() * z + mat.m23();
        dest.x = rx;
        dest.y = ry;
        dest.z = rz;
        return dest;
    }

    /**
     * Multiply the given 4x4 matrix <code>mat</code> with <code>this</code> and return the <i>w</i> component
     * of the resulting 4D vector.
     * <p>
     * This method assumes the <code>w</code> component of <code>this</code> to be <code>1.0</code>.
     * 
     * @param mat
     *          the matrix to multiply this vector by
     * @return the <i>w</i> component of the resulting 4D vector after multiplication
     */
    public double mulPositionW(Matrix4fc mat) {
        return mulPositionW(mat, thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3dc#mulPositionW(org.joml.Matrix4fc, org.joml.Vector3d)
     */
    public double mulPositionW(Matrix4fc mat, Vector3d dest) {
        double w = mat.m03() * x + mat.m13() * y + mat.m23() * z + mat.m33();
        double rx = mat.m00() * x + mat.m10() * y + mat.m20() * z + mat.m30();
        double ry = mat.m01() * x + mat.m11() * y + mat.m21() * z + mat.m31();
        double rz = mat.m02() * x + mat.m12() * y + mat.m22() * z + mat.m32();
        dest.x = rx;
        dest.y = ry;
        dest.z = rz;
        return w;
    }

    /**
     * Multiply the given 4x4 matrix <code>mat</code> with <code>this</code> and return the <i>w</i> component
     * of the resulting 4D vector.
     * <p>
     * This method assumes the <code>w</code> component of <code>this</code> to be <code>1.0</code>.
     * 
     * @param mat
     *          the matrix to multiply this vector by
     * @return the <i>w</i> component of the resulting 4D vector after multiplication
     */
    public double mulPositionW(Matrix4dc mat) {
        return mulPositionW(mat, thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3dc#mulPositionW(org.joml.Matrix4dc, org.joml.Vector3d)
     */
    public double mulPositionW(Matrix4dc mat, Vector3d dest) {
        double w = mat.m03() * x + mat.m13() * y + mat.m23() * z + mat.m33();
        double rx = mat.m00() * x + mat.m10() * y + mat.m20() * z + mat.m30();
        double ry = mat.m01() * x + mat.m11() * y + mat.m21() * z + mat.m31();
        double rz = mat.m02() * x + mat.m12() * y + mat.m22() * z + mat.m32();
        dest.x = rx;
        dest.y = ry;
        dest.z = rz;
        return w;
    }

    /**
     * Multiply the given 4x4 matrix <code>mat</code> with <code>this</code>.
     * <p>
     * This method assumes the <code>w</code> component of <code>this</code> to be <code>0.0</code>.
     * 
     * @param mat
     *          the matrix to multiply this vector by
     * @return a vector holding the result
     */
    public Vector3d mulDirection(Matrix4fc mat) {
        return mulDirection(mat, thisOrNew());
    }

    /**
     * Multiply the given 4x4 matrix <code>mat</code> with <code>this</code>.
     * <p>
     * This method assumes the <code>w</code> component of <code>this</code> to be <code>0.0</code>.
     * 
     * @param mat
     *          the matrix to multiply this vector by
     * @return a vector holding the result
     */
    public Vector3d mulDirection(Matrix4dc mat) {
        return mulDirection(mat, thisOrNew());
    }

    /**
     * Multiply the given 4x3 matrix <code>mat</code> with <code>this</code>.
     * <p>
     * This method assumes the <code>w</code> component of <code>this</code> to be <code>0.0</code>.
     * 
     * @param mat
     *          the matrix to multiply this vector by
     * @return a vector holding the result
     */
    public Vector3d mulDirection(Matrix4x3dc mat) {
        return mulDirection(mat, thisOrNew());
    }

    /**
     * Multiply the given 4x3 matrix <code>mat</code> with <code>this</code>.
     * <p>
     * This method assumes the <code>w</code> component of <code>this</code> to be <code>0.0</code>.
     * 
     * @param mat
     *          the matrix to multiply this vector by
     * @return a vector holding the result
     */
    public Vector3d mulDirection(Matrix4x3fc mat) {
        return mulDirection(mat, thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3dc#mulDirection(org.joml.Matrix4dc, org.joml.Vector3d)
     */
    public Vector3d mulDirection(Matrix4dc mat, Vector3d dest) {
        double rx = mat.m00() * x + mat.m10() * y + mat.m20() * z;
        double ry = mat.m01() * x + mat.m11() * y + mat.m21() * z;
        double rz = mat.m02() * x + mat.m12() * y + mat.m22() * z;
        dest.x = rx;
        dest.y = ry;
        dest.z = rz;
        return dest;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3dc#mulDirection(org.joml.Matrix4fc, org.joml.Vector3d)
     */
    public Vector3d mulDirection(Matrix4fc mat, Vector3d dest) {
        double rx = mat.m00() * x + mat.m10() * y + mat.m20() * z;
        double ry = mat.m01() * x + mat.m11() * y + mat.m21() * z;
        double rz = mat.m02() * x + mat.m12() * y + mat.m22() * z;
        dest.x = rx;
        dest.y = ry;
        dest.z = rz;
        return dest;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3dc#mulDirection(org.joml.Matrix4x3dc, org.joml.Vector3d)
     */
    public Vector3d mulDirection(Matrix4x3dc mat, Vector3d dest) {
        double rx = mat.m00() * x + mat.m10() * y + mat.m20() * z;
        double ry = mat.m01() * x + mat.m11() * y + mat.m21() * z;
        double rz = mat.m02() * x + mat.m12() * y + mat.m22() * z;
        dest.x = rx;
        dest.y = ry;
        dest.z = rz;
        return dest;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3dc#mulDirection(org.joml.Matrix4x3fc, org.joml.Vector3d)
     */
    public Vector3d mulDirection(Matrix4x3fc mat, Vector3d dest) {
        double rx = mat.m00() * x + mat.m10() * y + mat.m20() * z;
        double ry = mat.m01() * x + mat.m11() * y + mat.m21() * z;
        double rz = mat.m02() * x + mat.m12() * y + mat.m22() * z;
        dest.x = rx;
        dest.y = ry;
        dest.z = rz;
        return dest;
    }

    /**
     * Multiply the transpose of the given 4x4 matrix <code>mat</code> with <code>this</code>.
     * <p>
     * This method assumes the <code>w</code> component of <code>this</code> to be <code>0.0</code>.
     * 
     * @param mat
     *          the matrix whose transpose to multiply this vector by
     * @return a vector holding the result
     */
    public Vector3d mulTransposeDirection(Matrix4dc mat) {
        return mulTransposeDirection(mat, thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3dc#mulTransposeDirection(org.joml.Matrix4dc, org.joml.Vector3d)
     */
    public Vector3d mulTransposeDirection(Matrix4dc mat, Vector3d dest) {
        double rx = mat.m00() * x + mat.m01() * y + mat.m02() * z;
        double ry = mat.m10() * x + mat.m11() * y + mat.m12() * z;
        double rz = mat.m20() * x + mat.m21() * y + mat.m22() * z;
        dest.x = rx;
        dest.y = ry;
        dest.z = rz;
        return dest;
    }

    /**
     * Multiply the transpose of the given 4x4 matrix <code>mat</code> with <code>this</code>.
     * <p>
     * This method assumes the <code>w</code> component of <code>this</code> to be <code>0.0</code>.
     * 
     * @param mat
     *          the matrix whose transpose to multiply this vector by
     * @return a vector holding the result
     */
    public Vector3d mulTransposeDirection(Matrix4fc mat) {
        return mulTransposeDirection(mat, thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3dc#mulTransposeDirection(org.joml.Matrix4fc, org.joml.Vector3d)
     */
    public Vector3d mulTransposeDirection(Matrix4fc mat, Vector3d dest) {
        double rx = mat.m00() * x + mat.m01() * y + mat.m02() * z;
        double ry = mat.m10() * x + mat.m11() * y + mat.m12() * z;
        double rz = mat.m20() * x + mat.m21() * y + mat.m22() * z;
        dest.x = rx;
        dest.y = ry;
        dest.z = rz;
        return dest;
    }

    /**
     * Multiply this Vector3d by the given scalar value.
     * 
     * @param scalar
     *          the scalar to multiply this vector by
     * @return a vector holding the result
     */
    public Vector3d mul(double scalar) {
        return mul(scalar, thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3dc#mul(double, org.joml.Vector3d)
     */
    public Vector3d mul(double scalar, Vector3d dest) {
        dest.x = x * scalar;
        dest.y = y * scalar;
        dest.z = z * scalar;
        return dest;
    }

    /**
     * Multiply the components of this Vector3d by the given scalar values and store the result in <code>this</code>.
     * 
     * @param x
     *          the x component to multiply this vector by
     * @param y
     *          the y component to multiply this vector by
     * @param z
     *          the z component to multiply this vector by
     * @return a vector holding the result
     */
    public Vector3d mul(double x, double y, double z) {
        return mul(x, y, z, thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3dc#mul(double, double, double, org.joml.Vector3d)
     */
    public Vector3d mul(double x, double y, double z, Vector3d dest) {
        dest.x = this.x * x;
        dest.y = this.y * y;
        dest.z = this.z * z;
        return dest;
    }

    /**
     * Rotate this vector by the given quaternion <code>quat</code> and store the result in <code>this</code>.
     * 
     * @see Quaterniond#transform(Vector3d)
     * 
     * @param quat
     *          the quaternion to rotate this vector
     * @return a vector holding the result
     */
    public Vector3d rotate(Quaterniondc quat) {
        return quat.transform(this, thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3dc#rotate(org.joml.Quaterniondc, org.joml.Vector3d)
     */
    public Vector3d rotate(Quaterniondc quat, Vector3d dest) {
        return quat.transform(this, dest);
    }

    /*
     * (non-Javadoc)
     * @see org.joml.Vector3dc#rotationTo(org.joml.Vector3dc, org.joml.Quaterniond)
     */
    public Quaterniond rotationTo(Vector3dc toDir, Quaterniond dest) {
        return dest.rotationTo(this, toDir);
    }

    /*
     * (non-Javadoc)
     * @see org.joml.Vector3dc#rotationTo(double, double, double, org.joml.Quaterniond)
     */
    public Quaterniond rotationTo(double toDirX, double toDirY, double toDirZ, Quaterniond dest) {
        return dest.rotationTo(x, y, z, toDirX, toDirY, toDirZ);
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
    public Vector3d rotateAxis(double angle, double x, double y, double z) {
        return rotateAxis(angle, x, y, z, thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3dc#rotateAxis(double, double, double, double, org.joml.Vector3d)
     */
    public Vector3d rotateAxis(double angle, double aX, double aY, double aZ, Vector3d dest) {
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
    public Vector3d rotateX(double angle) {
        return rotateX(angle, thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3dc#rotateX(double, org.joml.Vector3d)
     */
    public Vector3d rotateX(double angle, Vector3d dest) {
        double sin = Math.sin(angle), cos = Math.cosFromSin(sin, angle);
        double y = this.y * cos - this.z * sin;
        double z = this.y * sin + this.z * cos;
        dest.x = this.x;
        dest.y = y;
        dest.z = z;
        return dest;
    }

    /**
     * Rotate this vector the specified radians around the Y axis.
     * 
     * @param angle
     *          the angle in radians
     * @return a vector holding the result
     */
    public Vector3d rotateY(double angle) {
        return rotateY(angle, thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3dc#rotateY(double, org.joml.Vector3d)
     */
    public Vector3d rotateY(double angle, Vector3d dest) {
        double sin = Math.sin(angle), cos = Math.cosFromSin(sin, angle);
        double x =  this.x * cos + this.z * sin;
        double z = -this.x * sin + this.z * cos;
        dest.x = x;
        dest.y = this.y;
        dest.z = z;
        return dest;
    }

    /**
     * Rotate this vector the specified radians around the Z axis.
     * 
     * @param angle
     *          the angle in radians
     * @return a vector holding the result
     */
    public Vector3d rotateZ(double angle) {
        return rotateZ(angle, thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3dc#rotateZ(double, org.joml.Vector3d)
     */
    public Vector3d rotateZ(double angle, Vector3d dest) {
        double sin = Math.sin(angle), cos = Math.cosFromSin(sin, angle);
        double x = this.x * cos - this.y * sin;
        double y = this.x * sin + this.y * cos;
        dest.x = x;
        dest.y = y;
        dest.z = this.z;
        return dest;
    }

    /**
     * Divide this Vector3d by the given scalar value.
     * 
     * @param scalar
     *          the scalar to divide this vector by
     * @return a vector holding the result
     */
    public Vector3d div(double scalar) {
        return div(scalar, thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3dc#div(double, org.joml.Vector3d)
     */
    public Vector3d div(double scalar, Vector3d dest) {
        double inv = 1.0 / scalar;
        dest.x = x * inv;
        dest.y = y * inv;
        dest.z = z * inv;
        return dest;
    }

    /**
     * Divide the components of this Vector3d by the given scalar values and store the result in <code>this</code>.
     * 
     * @param x
     *          the x component to divide this vector by
     * @param y
     *          the y component to divide this vector by
     * @param z
     *          the z component to divide this vector by
     * @return a vector holding the result
     */
    public Vector3d div(double x, double y, double z) {
        return div(x, y, z, thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3dc#div(double, double, double, org.joml.Vector3d)
     */
    public Vector3d div(double x, double y, double z, Vector3d dest) {
        dest.x = this.x / x;
        dest.y = this.y / y;
        dest.z = this.z / z;
        return dest;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3dc#lengthSquared()
     */
    public double lengthSquared() {
        return lengthSquared(x, y, z);
    }

    /**
     * Get the length squared of a 3-dimensional double-precision vector.
     *
     * @param x The vector's x component
     * @param y The vector's y component
     * @param z The vector's z component
     *
     * @return the length squared of the given vector
     *
     * @author F. Neurath
     */
    public static double lengthSquared(double x, double y, double z) {
        return x * x + y * y + z * z;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3dc#length()
     */
    public double length() {
        return Math.sqrt(lengthSquared());
    }

    /**
     * Get the length of a 3-dimensional double-precision vector.
     *
     * @param x The vector's x component
     * @param y The vector's y component
     * @param z The vector's z component
     *
     * @return the length of the given vector
     *
     * @author F. Neurath
     */
    public static double length(double x, double y, double z) {
        return Math.sqrt(lengthSquared(x, y, z));
    }

    /**
     * Normalize this vector.
     * 
     * @return a vector holding the result
     */
    public Vector3d normalize() {
        return normalize(thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3dc#normalize(org.joml.Vector3d)
     */
    public Vector3d normalize(Vector3d dest) {
        double invLength = 1.0 / length();
        dest.x = x * invLength;
        dest.y = y * invLength;
        dest.z = z * invLength;
        return dest;
    }

    /**
     * Scale this vector to have the given length.
     * 
     * @param length
     *          the desired length
     * @return a vector holding the result
     */
    public Vector3d normalize(double length) {
        return normalize(length, thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3dc#normalize(double, org.joml.Vector3d)
     */
    public Vector3d normalize(double length, Vector3d dest) {
        double invLength = 1.0 / length() * length;
        dest.x = x * invLength;
        dest.y = y * invLength;
        dest.z = z * invLength;
        return dest;
    }

    /**
     * Set this vector to be the cross product of this and v2.
     * 
     * @param v
     *          the other vector
     * @return a vector holding the result
     */
    public Vector3d cross(Vector3dc v) {
        return cross(v, thisOrNew());
    }

    /**
     * Set this vector to be the cross product of itself and <code>(x, y, z)</code>.
     * 
     * @param x
     *          the x component of the other vector
     * @param y
     *          the y component of the other vector
     * @param z
     *          the z component of the other vector
     * @return a vector holding the result
     */
    public Vector3d cross(double x, double y, double z) {
        return cross(x, y, z, thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3dc#cross(org.joml.Vector3dc, org.joml.Vector3d)
     */
    public Vector3d cross(Vector3dc v, Vector3d dest) {
        double rx = y * v.z() - z * v.y();
        double ry = z * v.x() - x * v.z();
        double rz = x * v.y() - y * v.x();
        dest.x = rx;
        dest.y = ry;
        dest.z = rz;
        return dest;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3dc#cross(double, double, double, org.joml.Vector3d)
     */
    public Vector3d cross(double x, double y, double z, Vector3d dest) {
        double rx = this.y * z - this.z * y;
        double ry = this.z * x - this.x * z;
        double rz = this.x * y - this.y * x;
        dest.x = rx;
        dest.y = ry;
        dest.z = rz;
        return dest;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3dc#distance(org.joml.Vector3dc)
     */
    public double distance(Vector3dc v) {
    	return distance(v.x(), v.y(), v.z());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3dc#distance(double, double, double)
     */
    public double distance(double x, double y, double z) {
        return Math.sqrt(distanceSquared(x, y, z));
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3dc#distanceSquared(org.joml.Vector3dc)
     */
    public double distanceSquared(Vector3dc v) {
    	return distanceSquared(v.x(), v.y(), v.z());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3dc#distanceSquared(double, double, double)
     */
    public double distanceSquared(double x, double y, double z) {
        double dx = this.x - x;
        double dy = this.y - y;
        double dz = this.z - z;
        return dx * dx + dy * dy + dz * dz;
    }

    /**
     * Return the distance between <code>(x1, y1, z1)</code> and <code>(x2, y2, z2)</code>.
     *
     * @param x1
     *          the x component of the first vector
     * @param y1
     *          the y component of the first vector
     * @param z1
     *          the z component of the first vector
     * @param x2
     *          the x component of the second vector
     * @param y2
     *          the y component of the second vector
     * @param z2
     *          the z component of the second vector
     * @return the euclidean distance
     */
    public static double distance(double x1, double y1, double z1, double x2, double y2, double z2) {
        return Math.sqrt(distanceSquared(x1, y1, z1, x2, y2, z2));
    }

    /**
     * Return the squared distance between <code>(x1, y1, z1)</code> and <code>(x2, y2, z2)</code>.
     *
     * @param x1
     *          the x component of the first vector
     * @param y1
     *          the y component of the first vector
     * @param z1
     *          the z component of the first vector
     * @param x2
     *          the x component of the second vector
     * @param y2
     *          the y component of the second vector
     * @param z2
     *          the z component of the second vector
     * @return the euclidean distance squared
     */
    public static double distanceSquared(double x1, double y1, double z1, double x2, double y2, double z2) {
        double dx = x1 - x2;
        double dy = y1 - y2;
        double dz = z1 - z2;
        return dx * dx + dy * dy + dz * dz;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3dc#dot(org.joml.Vector3dc)
     */
    public double dot(Vector3dc v) {
        return dot(v.x(), v.y(), v.z());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3dc#dot(float, float, float)
     */
    public double dot(double x, double y, double z) {
        return this.x * x + this.y * y + this.z * z;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3dc#angleCos(org.joml.Vector3dc)
     */
    public double angleCos(Vector3dc v) {
        double length1Squared = x * x + y * y + z * z;
        double length2Squared = v.x() * v.x() + v.y() * v.y() + v.z() * v.z();
        double dot = x * v.x() + y * v.y() + z * v.z();
        return dot / (Math.sqrt(length1Squared * length2Squared));
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3dc#angle(org.joml.Vector3dc)
     */
    public double angle(Vector3dc v) {
        double cos = angleCos(v);
        // This is because sometimes cos goes above 1 or below -1 because of lost precision
        cos = cos < 1 ? cos : 1;
        cos = cos > -1 ? cos : -1;
        return Math.acos(cos);
    }

    /**
     * Set the components of this vector to be the component-wise minimum of this and the other vector.
     *
     * @param v
     *          the other vector
     * @return a vector holding the result
     */
    public Vector3d min(Vector3dc v) {
        return min(v, thisOrNew());
    }

    public Vector3d min(Vector3dc v, Vector3d dest) {
        dest.x = x < v.x() ? x : v.x();
        dest.y = y < v.y() ? y : v.y();
        dest.z = z < v.z() ? z : v.z();
        return dest;
    }

    /**
     * Set the components of this vector to be the component-wise maximum of this and the other vector.
     *
     * @param v
     *          the other vector
     * @return a vector holding the result
     */
    public Vector3d max(Vector3dc v) {
        return max(v, thisOrNew());
    }

    public Vector3d max(Vector3dc v, Vector3d dest) {
        dest.x = x > v.x() ? x : v.x();
        dest.y = y > v.y() ? y : v.y();
        dest.z = z > v.z() ? z : v.z();
        return dest;
    }

    /**
     * Set all components to zero.
     * 
     * @return a vector holding the result
     */
    public Vector3d zero() {
        return thisOrNew().set(0, 0, 0);
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
        return "(" + formatter.format(x) + " " + formatter.format(y) + " " + formatter.format(z) + ")";
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
     * Negate this vector.
     * 
     * @return a vector holding the result
     */
    public Vector3d negate() {
        return negate(thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3dc#negate(org.joml.Vector3d)
     */
    public Vector3d negate(Vector3d dest) {
        dest.x = -x;
        dest.y = -y;
        dest.z = -z;
        return dest;
    }

    /**
     * Set <code>this</code> vector's components to their respective absolute values.
     * 
     * @return a vector holding the result
     */
    public Vector3d absolute() {
        return absolute(thisOrNew());
    }

    /*
     * (non-Javadoc)
     * @see org.joml.Vector3dc#absolute(org.joml.Vector3d)
     */
    public Vector3d absolute(Vector3d dest) {
        dest.x = Math.abs(this.x);
        dest.y = Math.abs(this.y);
        dest.z = Math.abs(this.z);
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
        Vector3d other = (Vector3d) obj;
        if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
            return false;
        if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y))
            return false;
        if (Double.doubleToLongBits(z) != Double.doubleToLongBits(other.z))
            return false;
        return true;
    }

    public boolean equals(Vector3dc v, double delta) {
        if (this == v)
            return true;
        if (v == null)
            return false;
        if (!(v instanceof Vector3dc))
            return false;
        if (!Runtime.equals(x, v.x(), delta))
            return false;
        if (!Runtime.equals(y, v.y(), delta))
            return false;
        if (!Runtime.equals(z, v.z(), delta))
            return false;
        return true;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3dc#equals(double, double, double)
     */
    public boolean equals(double x, double y, double z) {
        if (Double.doubleToLongBits(this.x) != Double.doubleToLongBits(x))
            return false;
        if (Double.doubleToLongBits(this.y) != Double.doubleToLongBits(y))
            return false;
        if (Double.doubleToLongBits(this.z) != Double.doubleToLongBits(z))
            return false;
        return true;
    }

    /**
     * Reflect this vector about the given normal vector.
     * 
     * @param normal
     *          the vector to reflect about
     * @return a vector holding the result
     */
    public Vector3d reflect(Vector3dc normal) {
        return reflect(normal, thisOrNew());
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
     * @return a vector holding the result
     */
    public Vector3d reflect(double x, double y, double z) {
        return reflect(x, y, z, thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3dc#reflect(org.joml.Vector3dc, org.joml.Vector3d)
     */
    public Vector3d reflect(Vector3dc normal, Vector3d dest) {
        return reflect(normal.x(), normal.y(), normal.z(), dest);
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3dc#reflect(double, double, double, org.joml.Vector3d)
     */
    public Vector3d reflect(double x, double y, double z, Vector3d dest) {
        double dot = this.dot(x, y, z);
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
     * @return a vector holding the result
     */
    public Vector3d half(Vector3dc other) {
        return half(other, thisOrNew());
    }

    /**
     * Compute the half vector between this and the vector <code>(x, y, z)</code>.
     * 
     * @param x
     *          the x component of the other vector
     * @param y
     *          the y component of the other vector
     * @param z
     *          the z component of the other vector
     * @return a vector holding the result
     */
    public Vector3d half(double x, double y, double z) {
        return half(x, y, z, thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3dc#half(org.joml.Vector3dc, org.joml.Vector3d)
     */
    public Vector3d half(Vector3dc other, Vector3d dest) {
        return half(other.x(), other.y(), other.z(), dest);
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3dc#half(double, double, double, org.joml.Vector3d)
     */
    public Vector3d half(double x, double y, double z, Vector3d dest) {
        return dest.set(this).add(x, y, z).normalize();
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3dc#smoothStep(org.joml.Vector3dc, double, org.joml.Vector3d)
     */
    public Vector3d smoothStep(Vector3dc v, double t, Vector3d dest) {
        double t2 = t * t;
        double t3 = t2 * t;
        dest.x = (x + x - v.x() - v.x()) * t3 + (3.0 * v.x() - 3.0 * x) * t2 + x * t + x;
        dest.y = (y + y - v.y() - v.y()) * t3 + (3.0 * v.y() - 3.0 * y) * t2 + y * t + y;
        dest.z = (z + z - v.z() - v.z()) * t3 + (3.0 * v.z() - 3.0 * z) * t2 + z * t + z;
        return dest;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3dc#hermite(org.joml.Vector3dc, org.joml.Vector3dc, org.joml.Vector3dc, double, org.joml.Vector3d)
     */
    public Vector3d hermite(Vector3dc t0, Vector3dc v1, Vector3dc t1, double t, Vector3d dest) {
        double t2 = t * t;
        double t3 = t2 * t;
        dest.x = (x + x - v1.x() - v1.x() + t1.x() + t0.x()) * t3 + (3.0 * v1.x() - 3.0 * x - t0.x() - t0.x() - t1.x()) * t2 + x * t + x;
        dest.y = (y + y - v1.y() - v1.y() + t1.y() + t0.y()) * t3 + (3.0 * v1.y() - 3.0 * y - t0.y() - t0.y() - t1.y()) * t2 + y * t + y;
        dest.z = (z + z - v1.z() - v1.z() + t1.z() + t0.z()) * t3 + (3.0 * v1.z() - 3.0 * z - t0.z() - t0.z() - t1.z()) * t2 + z * t + z;
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
    public Vector3d lerp(Vector3dc other, double t) {
        return lerp(other, t, thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3dc#lerp(org.joml.Vector3dc, double, org.joml.Vector3d)
     */
    public Vector3d lerp(Vector3dc other, double t, Vector3d dest) {
        dest.x = x + (other.x() - x) * t;
        dest.y = y + (other.y() - y) * t;
        dest.z = z + (other.z() - z) * t;
        return dest;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3dc#get(int)
     */
    public double get(int component) throws IllegalArgumentException {
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

    /* (non-Javadoc)
     * @see org.joml.Vector3dc#maxComponent()
     */
    public int maxComponent() {
        double absX = Math.abs(x);
        double absY = Math.abs(y);
        double absZ = Math.abs(z);
        if (absX >= absY && absX >= absZ) {
            return 0;
        } else if (absY >= absZ) {
            return 1;
        }
        return 2;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3dc#minComponent()
     */
    public int minComponent() {
        double absX = Math.abs(x);
        double absY = Math.abs(y);
        double absZ = Math.abs(z);
        if (absX < absY && absX < absZ) {
            return 0;
        } else if (absY < absZ) {
            return 1;
        }
        return 2;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3dc#orthogonalize(org.joml.Vector3dc, org.joml.Vector3d)
     */
    public Vector3d orthogonalize(Vector3dc v, Vector3d dest) {
        /*
         * http://lolengine.net/blog/2013/09/21/picking-orthogonal-vector-combing-coconuts
         */
        double rx, ry, rz;
        if (Math.abs(v.x()) > Math.abs(v.z())) {
            rx = -v.y();
            ry = v.x();
            rz = 0.0;
        } else {
            rx = 0.0;
            ry = -v.z();
            rz = v.y();
        }
        double invLen = 1.0 / Math.sqrt(rx * rx + ry * ry + rz * rz);
        dest.x = rx * invLen;
        dest.y = ry * invLen;
        dest.z = rz * invLen;
        return dest;
    }

    /**
     * Transform <code>this</code> vector so that it is orthogonal to the given vector <code>v</code> and normalize the result.
     * <p>
     * Reference: <a href="https://en.wikipedia.org/wiki/Gram%E2%80%93Schmidt_process">Gram–Schmidt process</a>
     * 
     * @param v
     *          the reference vector which the result should be orthogonal to
     * @return a vector holding the result
     */
    public Vector3d orthogonalize(Vector3dc v) {
        return orthogonalize(v, thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3dc#orthogonalizeUnit(org.joml.Vector3dc, org.joml.Vector3d)
     */
    public Vector3d orthogonalizeUnit(Vector3dc v, Vector3d dest) {
        return orthogonalize(v, dest);
    }

    /**
     * Transform <code>this</code> vector so that it is orthogonal to the given unit vector <code>v</code> and normalize the result.
     * <p>
     * The vector <code>v</code> is assumed to be a {@link #normalize() unit} vector.
     * <p>
     * Reference: <a href="https://en.wikipedia.org/wiki/Gram%E2%80%93Schmidt_process">Gram–Schmidt process</a>
     * 
     * @param v
     *          the reference unit vector which the result should be orthogonal to
     * @return a vector holding the result
     */
    public Vector3d orthogonalizeUnit(Vector3dc v) {
        return orthogonalizeUnit(v, thisOrNew());
    }

    /**
     * Set each component of this vector to the largest (closest to positive
     * infinity) {@code double} value that is less than or equal to that
     * component and is equal to a mathematical integer.
     *
     * @return a vector holding the result
     */
    public Vector3d floor() {
        return floor(thisOrNew());
    }

    public Vector3d floor(Vector3d dest) {
        dest.x = Math.floor(x);
        dest.y = Math.floor(y);
        dest.z = Math.floor(z);
        return dest;
    }

    /**
     * Set each component of this vector to the smallest (closest to negative
     * infinity) {@code double} value that is greater than or equal to that
     * component and is equal to a mathematical integer.
     *
     * @return a vector holding the result
     */
    public Vector3d ceil() {
        return ceil(thisOrNew());
    }

    public Vector3d ceil(Vector3d dest) {
        dest.x = Math.ceil(x);
        dest.y = Math.ceil(y);
        dest.z = Math.ceil(z);
        return dest;
    }

    /**
     * Set each component of this vector to the closest double that is equal to
     * a mathematical integer, with ties rounding to positive infinity.
     *
     * @return a vector holding the result
     */
    public Vector3d round() {
        return round(thisOrNew());
    }

    public Vector3d round(Vector3d dest) {
        dest.x = Math.round(x);
        dest.y = Math.round(y);
        dest.z = Math.round(z);
        return dest;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3dc#isFinite()
     */
    public boolean isFinite() {
        return Math.isFinite(x) && Math.isFinite(y) && Math.isFinite(z);
    }

}
