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

import org.joml.Math;
import org.joml.internal.MemUtil;
import org.joml.internal.Options;
import org.joml.internal.Runtime;

/**
 * Contains the definition of a Vector comprising 4 floats and associated
 * transformations.
 * 
 * @author Richard Greenlees
 * @author Kai Burjack
 * @author F. Neurath
 */
public class Vector4f implements Externalizable, Vector4fc {

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
     * The w component of the vector.
     */
    public float w;

    /**
     * Create a new {@link Vector4f} of <code>(0, 0, 0, 1)</code>.
     */
    public Vector4f() {
        this.w = 1.0f;
    }

    /**
     * Create a new {@link Vector4f} with the same values as <code>v</code>.
     * 
     * @param v
     *          the {@link Vector4fc} to copy the values from
     */
    public Vector4f(Vector4fc v) {
        if (v instanceof Vector4f) {
            MemUtil.INSTANCE.copy((Vector4f) v, this);
        } else {
            this.x = v.x();
            this.y = v.y();
            this.z = v.z();
            this.w = v.w();
        }
    }

    /**
     * Create a new {@link Vector4f} with the same values as <code>v</code>.
     * 
     * @param v
     *          the {@link Vector4ic} to copy the values from
     */
    public Vector4f(Vector4ic v) {
    	this(v.x(), v.y(), v.z(), v.w());
    }

    /**
     * Create a new {@link Vector4f} with the first three components from the
     * given <code>v</code> and the given <code>w</code>.
     * 
     * @param v
     *          the {@link Vector3fc}
     * @param w
     *          the w component
     */
    public Vector4f(Vector3fc v, float w) {
    	this(v.x(), v.y(), v.z(), w);
    }

    /**
     * Create a new {@link Vector4f} with the first three components from the
     * given <code>v</code> and the given <code>w</code>.
     * 
     * @param v
     *          the {@link Vector3ic}
     * @param w
     *          the w component
     */
    public Vector4f(Vector3ic v, float w) {
    	this(v.x(), v.y(), v.z(), w);
    }

    /**
     * Create a new {@link Vector4f} with the first two components from the
     * given <code>v</code> and the given <code>z</code>, and <code>w</code>.
     * 
     * @param v
     *          the {@link Vector2fc}
     * @param z
     *          the z component
     * @param w
     *          the w component
     */
    public Vector4f(Vector2fc v, float z, float w) {
    	this(v.x(), v.y(), z, w);
    }

    /**
     * Create a new {@link Vector4f} with the first two components from the
     * given <code>v</code> and the given <code>z</code>, and <code>w</code>.
     * 
     * @param v
     *          the {@link Vector2ic}
     * @param z
     *          the z component
     * @param w
     *          the w component
     */
    public Vector4f(Vector2ic v, float z, float w) {
    	this(v.x(), v.y(), z, w);
    }

    /**
     * Create a new {@link Vector4f} and initialize all four components with the given value.
     *
     * @param d
     *          the value of all four components
     */
    public Vector4f(float d) {
        MemUtil.INSTANCE.broadcast(d, this);
    }

    /**
     * Create a new {@link Vector4f} with the given component values.
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
    public Vector4f(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

//#ifdef __HAS_NIO__
    /**
     * Create a new {@link Vector4f} and read this vector from the supplied {@link ByteBuffer}
     * at the current buffer {@link ByteBuffer#position() position}.
     * <p>
     * This method will not increment the position of the given ByteBuffer.
     * <p>
     * In order to specify the offset into the ByteBuffer at which
     * the vector is read, use {@link #Vector4f(int, ByteBuffer)}, taking
     * the absolute position as parameter.
     *
     * @param buffer
     *          values will be read in <code>x, y, z, w</code> order
     * @see #Vector4f(int, ByteBuffer)
     */
    public Vector4f(ByteBuffer buffer) {
        this(buffer.position(), buffer);
    }

    /**
     * Create a new {@link Vector4f} and read this vector from the supplied {@link ByteBuffer}
     * starting at the specified absolute buffer position/index.
     * <p>
     * This method will not increment the position of the given ByteBuffer.
     *
     * @param index 
     *          the absolute position into the ByteBuffer
     * @param buffer
     *          values will be read in <code>x, y, z, w</code> order
     */
    public Vector4f(int index, ByteBuffer buffer) {
        MemUtil.INSTANCE.get(this, index, buffer);
    }

    /**
     * Create a new {@link Vector4f} and read this vector from the supplied {@link FloatBuffer}
     * at the current buffer {@link FloatBuffer#position() position}.
     * <p>
     * This method will not increment the position of the given FloatBuffer.
     * <p>
     * In order to specify the offset into the FloatBuffer at which
     * the vector is read, use {@link #Vector4f(int, FloatBuffer)}, taking
     * the absolute position as parameter.
     *
     * @param buffer
     *          values will be read in <code>x, y, z, w</code> order
     * @see #Vector4f(int, FloatBuffer)
     */
    public Vector4f(FloatBuffer buffer) {
        this(buffer.position(), buffer);
    }

    /**
     * Create a new {@link Vector4f} and read this vector from the supplied {@link FloatBuffer}
     * starting at the specified absolute buffer position/index.
     * <p>
     * This method will not increment the position of the given FloatBuffer.
     *
     * @param index 
     *          the absolute position into the FloatBuffer
     * @param buffer
     *          values will be read in <code>x, y, z, w</code> order
     */
    public Vector4f(int index, FloatBuffer buffer) {
        MemUtil.INSTANCE.get(this, index, buffer);
    }
//#endif

    private Vector4f thisOrNew() {
        return this;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4fc#x()
     */
    public float x() {
        return this.x;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4fc#y()
     */
    public float y() {
        return this.y;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4fc#z()
     */
    public float z() {
        return this.z;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4fc#w()
     */
    public float w() {
        return this.w;
    }

    /**
     * Set this {@link Vector4f} to the values of the given <code>v</code>.
     * 
     * @param v
     *          the vector whose values will be copied into this
     * @return this
     */
    public Vector4f set(Vector4fc v) {
        if (v instanceof Vector4f) {
            MemUtil.INSTANCE.copy((Vector4f) v, this);
        } else {
            this.x = v.x();
            this.y = v.y();
            this.z = v.z();
            this.w = v.w();
        }
        return this;
    }

    /**
     * Set this {@link Vector4f} to the values of the given <code>v</code>.
     * 
     * @param v
     *          the vector whose values will be copied into this
     * @return this
     */
    public Vector4f set(Vector4ic v) {
        return set(v.x(), v.y(), v.z(), v.w());
    }

    /**
     * Set this {@link Vector4f} to the values of the given <code>v</code>.
     * <p>
     * Note that due to the given vector <code>v</code> storing the components in double-precision,
     * there is the possibility to lose precision.
     * 
     * @param v
     *          the vector whose values will be copied into this
     * @return this
     */
    public Vector4f set(Vector4dc v) {
        return set((float) v.x(), (float) v.y(), (float) v.z(), (float) v.w());
    }

    /**
     * Set the first three components of this to the components of
     * <code>v</code> and the last component to <code>w</code>.
     * 
     * @param v
     *          the {@link Vector3fc} to copy
     * @param w
     *          the w component
     * @return this
     */
    public Vector4f set(Vector3fc v, float w) {
        return set(v.x(), v.y(), v.z(), w);
    }

    /**
     * Set the first three components of this to the components of
     * <code>v</code> and the last component to <code>w</code>.
     * 
     * @param v
     *          the {@link Vector3ic} to copy
     * @param w
     *          the w component
     * @return this
     */
    public Vector4f set(Vector3ic v, float w) {
        return set(v.x(), v.y(), v.z(), w);
    }

    /**
     * Sets the first two components of this to the components of given <code>v</code>
     * and last two components to the given <code>z</code>, and <code>w</code>.
     *
     * @param v
     *          the {@link Vector2fc}
     * @param z
     *          the z component
     * @param w
     *          the w component
     * @return this
     */
    public Vector4f set(Vector2fc v, float z, float w) {
        return set(v.x(), v.y(), z, w);
    }

    /**
     * Sets the first two components of this to the components of given <code>v</code>
     * and last two components to the given <code>z</code>, and <code>w</code>.
     *
     * @param v
     *          the {@link Vector2ic}
     * @param z
     *          the z component
     * @param w
     *          the w component
     * @return this
     */
    public Vector4f set(Vector2ic v, float z, float w) {
    	return set(v.x(), v.y(), z, w);
    }

    /**
     * Set the x, y, z, and w components to the supplied value.
     *
     * @param d
     *          the value of all four components
     * @return this
     */
    public Vector4f set(float d) {
        MemUtil.INSTANCE.broadcast(d, this);
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
    public Vector4f set(float x, float y, float z, float w) {
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
    public Vector4f set(ByteBuffer buffer) {
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
    public Vector4f set(int index, ByteBuffer buffer) {
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
     *          values will be read in <code>x, y, z, w</code> order
     * @return this
     * @see #set(int, FloatBuffer)
     */
    public Vector4f set(FloatBuffer buffer) {
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
     *          values will be read in <code>x, y, z, w</code> order
     * @return this
     */
    public Vector4f set(int index, FloatBuffer buffer) {
        MemUtil.INSTANCE.get(this, index, buffer);
        return this;
    }
//#endif

//#ifndef __GWT__
    /**
     * Set the values of this vector by reading 4 float values from off-heap memory,
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
    public Vector4f setFromAddress(long address) {
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
    public Vector4f setComponent(int component, float value) throws IllegalArgumentException {
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
     * @see org.joml.Vector4fc#get(java.nio.FloatBuffer)
     */
    public FloatBuffer get(FloatBuffer buffer) {
        return get(buffer.position(), buffer);
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4fc#get(int, java.nio.FloatBuffer)
     */
    public FloatBuffer get(int index, FloatBuffer buffer) {
        MemUtil.INSTANCE.put(this, index, buffer);
        return buffer;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4fc#get(java.nio.ByteBuffer)
     */
    public ByteBuffer get(ByteBuffer buffer) {
        return get(buffer.position(), buffer);
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4fc#get(int, java.nio.ByteBuffer)
     */
    public ByteBuffer get(int index, ByteBuffer buffer) {
        MemUtil.INSTANCE.put(this, index, buffer);
        return buffer;
    }
//#endif

//#ifndef __GWT__
    public Vector4fc getToAddress(long address) {
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
    public Vector4f sub(Vector4fc v) {
        return sub(v, thisOrNew());
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
    public Vector4f sub(float x, float y, float z, float w) {
        return sub(x, y, z, w, thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4fc#sub(org.joml.Vector4fc, org.joml.Vector4f)
     */
    public Vector4f sub(Vector4fc v, Vector4f dest) {
        return sub(v.x(), v.y(), v.z(), v.w(), dest);
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4fc#sub(float, float, float, float, org.joml.Vector4f)
     */
    public Vector4f sub(float x, float y, float z, float w, Vector4f dest) {
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
    public Vector4f add(Vector4fc v) {
        return add(v, thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4fc#add(org.joml.Vector4fc, org.joml.Vector4f)
     */
    public Vector4f add(Vector4fc v, Vector4f dest) {
        dest.x = x + v.x();
        dest.y = y + v.y();
        dest.z = z + v.z();
        dest.w = w + v.w();
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
     * @return a vector holding the result
     */
    public Vector4f add(float x, float y, float z, float w) {
        return add(x, y, z, w, thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4fc#add(float, float, float, float, org.joml.Vector4f)
     */
    public Vector4f add(float x, float y, float z, float w, Vector4f dest) {
        dest.x = this.x + x;
        dest.y = this.y + y;
        dest.z = this.z + z;
        dest.w = this.w + w;
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
    public Vector4f fma(Vector4fc a, Vector4fc b) {
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
    public Vector4f fma(float a, Vector4fc b) {
        return fma(a, b, thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4fc#fma(org.joml.Vector4fc, org.joml.Vector4fc, org.joml.Vector4f)
     */
    public Vector4f fma(Vector4fc a, Vector4fc b, Vector4f dest) {
        dest.x = x + a.x() * b.x();
        dest.y = y + a.y() * b.y();
        dest.z = z + a.z() * b.z();
        dest.w = w + a.w() * b.w();
        return dest;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4fc#fma(float, org.joml.Vector4fc, org.joml.Vector4f)
     */
    public Vector4f fma(float a, Vector4fc b, Vector4f dest) {
        dest.x = x + a * b.x();
        dest.y = y + a * b.y();
        dest.z = z + a * b.z();
        dest.w = w + a * b.w();
        return dest;
    }

    /**
     * Multiply this Vector4f component-wise by another Vector4f.
     * 
     * @param v
     *          the other vector
     * @return a vector holding the result
     */
    public Vector4f mul(Vector4fc v) {
        return mul(v, thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4fc#mul(org.joml.Vector4fc, org.joml.Vector4f)
     */
    public Vector4f mul(Vector4fc v, Vector4f dest) {
        dest.x = x * v.x();
        dest.y = y * v.y();
        dest.z = z * v.z();
        dest.w = w * v.w();
        return dest;
    }

    /**
     * Divide this Vector4f component-wise by another Vector4f.
     * 
     * @param v
     *          the vector to divide by
     * @return a vector holding the result
     */
    public Vector4f div(Vector4fc v) {
        return div(v, thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4fc#div(org.joml.Vector4fc, org.joml.Vector4f)
     */
    public Vector4f div(Vector4fc v, Vector4f dest) {
        dest.x = x / v.x();
        dest.y = y / v.y();
        dest.z = z / v.z();
        dest.w = w / v.w();
        return dest;
    }

    /**
     * Multiply the given matrix mat with this Vector4f and store the result in
     * <code>this</code>.
     * 
     * @param mat
     *          the matrix to multiply the vector with
     * @return a vector holding the result
     */
    public Vector4f mul(Matrix4fc mat) {
        return mul(mat, thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4fc#mul(org.joml.Matrix4fc, org.joml.Vector4f)
     */
    public Vector4f mul(Matrix4fc mat, Vector4f dest) {
        if ((mat.properties() & Matrix4fc.PROPERTY_AFFINE) != 0)
            return mulAffine(mat, dest);
        return mulGeneric(mat, dest);
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4fc#mulAffine(org.joml.Matrix4fc, org.joml.Vector4f)
     */
    public Vector4f mulAffine(Matrix4fc mat, Vector4f dest) {
        float rx = mat.m00() * x + mat.m10() * y + mat.m20() * z + mat.m30() * w;
        float ry = mat.m01() * x + mat.m11() * y + mat.m21() * z + mat.m31() * w;
        float rz = mat.m02() * x + mat.m12() * y + mat.m22() * z + mat.m32() * w;
        dest.x = rx;
        dest.y = ry;
        dest.z = rz;
        dest.w = w;
        return dest;
    }

    private Vector4f mulGeneric(Matrix4fc mat, Vector4f dest) {
        float rx = mat.m00() * x + mat.m10() * y + mat.m20() * z + mat.m30() * w;
        float ry = mat.m01() * x + mat.m11() * y + mat.m21() * z + mat.m31() * w;
        float rz = mat.m02() * x + mat.m12() * y + mat.m22() * z + mat.m32() * w;
        float rw = mat.m03() * x + mat.m13() * y + mat.m23() * z + mat.m33() * w;
        dest.x = rx;
        dest.y = ry;
        dest.z = rz;
        dest.w = rw;
        return dest;
    }

    /**
     * Multiply the given matrix mat with this Vector4f and store the result in
     * <code>this</code>.
     * 
     * @param mat
     *          the matrix to multiply the vector with
     * @return a vector holding the result
     */
    public Vector4f mul(Matrix4x3fc mat) {
        return mul(mat, thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4fc#mul(org.joml.Matrix4x3fc, org.joml.Vector4f)
     */
    public Vector4f mul(Matrix4x3fc mat, Vector4f dest) {
        float rx = mat.m00() * x + mat.m10() * y + mat.m20() * z + mat.m30() * w;
        float ry = mat.m01() * x + mat.m11() * y + mat.m21() * z + mat.m31() * w;
        float rz = mat.m02() * x + mat.m12() * y + mat.m22() * z + mat.m32() * w;
        dest.x = rx;
        dest.y = ry;
        dest.z = rz;
        dest.w = w;
        return dest;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4fc#mulProject(org.joml.Matrix4fc, org.joml.Vector4f)
     */
    public Vector4f mulProject(Matrix4fc mat, Vector4f dest) {
        float invW = 1.0f / (mat.m03() * x + mat.m13() * y + mat.m23() * z + mat.m33() * w);
        float rx = (mat.m00() * x + mat.m10() * y + mat.m20() * z + mat.m30()) * invW;
        float ry = (mat.m01() * x + mat.m11() * y + mat.m21() * z + mat.m31()) * invW;
        float rz = (mat.m02() * x + mat.m12() * y + mat.m22() * z + mat.m32()) * invW;
        dest.x = rx;
        dest.y = ry;
        dest.z = rz;
        dest.w = 1.0f;
        return dest;
    }

    /**
     * Multiply the given matrix <code>mat</code> with this Vector4f, perform perspective division.
     * 
     * @param mat
     *          the matrix to multiply this vector by
     * @return a vector holding the result
     */
    public Vector4f mulProject(Matrix4fc mat) {
        return mulProject(mat, thisOrNew());
    }

    /**
     * Multiply all components of this {@link Vector4f} by the given scalar
     * value.
     * 
     * @param scalar
     *          the scalar to multiply by
     * @return a vector holding the result
     */
    public Vector4f mul(float scalar) {
        return mul(scalar, thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4fc#mul(float, org.joml.Vector4f)
     */
    public Vector4f mul(float scalar, Vector4f dest) {
        dest.x = x * scalar;
        dest.y = y * scalar;
        dest.z = z * scalar;
        dest.w = w * scalar;
        return dest;
    }

    /**
     * Multiply the components of this Vector4f by the given scalar values and store the result in <code>this</code>.
     * 
     * @param x
     *          the x component to multiply by
     * @param y
     *          the y component to multiply by
     * @param z
     *          the z component to multiply by
     * @param w
     *          the w component to multiply by
     * @return a vector holding the result
     */
    public Vector4f mul(float x, float y, float z, float w) {
        return mul(x, y, z, w, thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4fc#mul(float, float, float, float, org.joml.Vector4f)
     */
    public Vector4f mul(float x, float y, float z, float w, Vector4f dest) {
        dest.x = this.x * x;
        dest.y = this.y * y;
        dest.z = this.z * z;
        dest.w = this.w * w;
        return dest;
    }

    /**
     * Divide all components of this {@link Vector4f} by the given scalar
     * value.
     * 
     * @param scalar
     *          the scalar to divide by
     * @return a vector holding the result
     */
    public Vector4f div(float scalar) {
        return div(scalar, thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4fc#div(float, org.joml.Vector4f)
     */
    public Vector4f div(float scalar, Vector4f dest) {
        float inv = 1.0f / scalar;
        dest.x = x * inv;
        dest.y = y * inv;
        dest.z = z * inv;
        dest.w = w * inv;
        return dest;
    }

    /**
     * Divide the components of this Vector4f by the given scalar values and store the result in <code>this</code>.
     * 
     * @param x
     *          the x component to divide by
     * @param y
     *          the y component to divide by
     * @param z
     *          the z component to divide by
     * @param w
     *          the w component to divide by
     * @return a vector holding the result
     */
    public Vector4f div(float x, float y, float z, float w) {
        return div(x, y, z, w, thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4fc#div(float, float, float, float, org.joml.Vector4f)
     */
    public Vector4f div(float x, float y, float z, float w, Vector4f dest) {
        dest.x = this.x / x;
        dest.y = this.y / y;
        dest.z = this.z / z;
        dest.w = this.w / w;
        return dest;
    }

    /**
     * Rotate this vector by the given quaternion <code>quat</code> and store the result in <code>this</code>.
     * 
     * @see Quaternionf#transform(Vector4f)
     * 
     * @param quat
     *          the quaternion to rotate this vector
     * @return a vector holding the result
     */
    public Vector4f rotate(Quaternionfc quat) {
        return rotate(quat, thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4fc#rotate(org.joml.Quaternionf, org.joml.Vector4f)
     */
    public Vector4f rotate(Quaternionfc quat, Vector4f dest) {
        return quat.transform(this, dest);
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
    public Vector4f rotateAbout(float angle, float x, float y, float z) {
        return rotateAxis(angle, x, y, z, thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4fc#rotateAxis(float, float, float, float, org.joml.Vector4f)
     */
    public Vector4f rotateAxis(float angle, float aX, float aY, float aZ, Vector4f dest) {
        float hangle = angle * 0.5f;
        float sinAngle = (float) Math.sin(hangle);
        float qx = aX * sinAngle, qy = aY * sinAngle, qz = aZ * sinAngle;
        float qw = (float) Math.cosFromSin(sinAngle, hangle);
        float w2 = qw * qw, x2 = qx * qx, y2 = qy * qy, z2 = qz * qz, zw = qz * qw;
        float xy = qx * qy, xz = qx * qz, yw = qy * qw, yz = qy * qz, xw = qx * qw;
        float nx = (w2 + x2 - z2 - y2) * x + (-zw + xy - zw + xy) * y + (yw + xz + xz + yw) * z;
        float ny = (xy + zw + zw + xy) * x + ( y2 - z2 + w2 - x2) * y + (yz + yz - xw - xw) * z;
        float nz = (xz - yw + xz - yw) * x + ( yz + yz + xw + xw) * y + (z2 - y2 - x2 + w2) * z;
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
    public Vector4f rotateX(float angle) {
        return rotateX(angle, thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4fc#rotateX(float, org.joml.Vector4f)
     */
    public Vector4f rotateX(float angle, Vector4f dest) {
        float sin = (float) Math.sin(angle), cos = (float) Math.cosFromSin(sin, angle);
        float y = this.y * cos - this.z * sin;
        float z = this.y * sin + this.z * cos;
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
    public Vector4f rotateY(float angle) {
        return rotateY(angle, thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4fc#rotateY(float, org.joml.Vector4f)
     */
    public Vector4f rotateY(float angle, Vector4f dest) {
        float sin = (float) Math.sin(angle), cos = (float) Math.cosFromSin(sin, angle);
        float x =  this.x * cos + this.z * sin;
        float z = -this.x * sin + this.z * cos;
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
    public Vector4f rotateZ(float angle) {
        return rotateZ(angle, thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4fc#rotateZ(float, org.joml.Vector4f)
     */
    public Vector4f rotateZ(float angle, Vector4f dest) {
        float sin = (float) Math.sin(angle), cos = (float) Math.cosFromSin(sin, angle);
        float x = this.x * cos - this.y * sin;
        float y = this.x * sin + this.y * cos;
        dest.x = x;
        dest.y = y;
        dest.z = this.z;
        dest.w = this.w;
        return dest;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4fc#lengthSquared()
     */
    public float lengthSquared() {
        return lengthSquared(x, y, z, w);
    }

    /**
     * Get the length squared of a 4-dimensional single-precision vector.
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
    public static float lengthSquared(float x, float y, float z, float w) {
        return x * x + y * y + z * z + w * w;
    }

    public static float lengthSquared(int x, int y, int z, int w) {
        return x * x + y * y + z * z + w * w;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4fc#length()
     */
    public float length() {
        return (float) Math.sqrt(lengthSquared());
    }

    /**
     * Get the length of a 4-dimensional single-precision vector.
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
    public static float length(float x, float y, float z, float w) {
        return (float) Math.sqrt(x * x + y * y + z * z + w * w);
    }

    /**
     * Normalizes this vector.
     * 
     * @return a vector holding the result
     */
    public Vector4f normalize() {
        return normalize(thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4fc#normalize(org.joml.Vector4f)
     */
    public Vector4f normalize(Vector4f dest) {
        float invLength = 1.0f / length();
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
    public Vector4f normalize(float length) {
        return normalize(length, thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4fc#normalize(float, org.joml.Vector4f)
     */
    public Vector4f normalize(float length, Vector4f dest) {
        float invLength = 1.0f / length() * length;
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
    public Vector4f normalize3() {
        return normalize3(thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4fc#normalize3(org.joml.Vector4f)
     */
    public Vector4f normalize3(Vector4f dest) {
        float invLength = 1.0f / (float) Math.sqrt(x * x + y * y + z * z);
        dest.x = x * invLength;
        dest.y = y * invLength;
        dest.z = z * invLength;
        dest.w = w * invLength;
        return dest;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4fc#distance(org.joml.Vector4fc)
     */
    public float distance(Vector4fc v) {
        return distance(v.x(), v.y(), v.z(), v.w());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4fc#distance(float, float, float, float)
     */
    public float distance(float x, float y, float z, float w) {
        return (float) Math.sqrt(distanceSquared(x, y, z, w));
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4fc#distanceSquared(org.joml.Vector4fc)
     */
    public float distanceSquared(Vector4fc v) {
        return distanceSquared(v.x(), v.y(), v.z(), v.w());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4fc#distanceSquared(float, float, float, float)
     */
    public float distanceSquared(float x, float y, float z, float w) {
        float dx = this.x - x;
        float dy = this.y - y;
        float dz = this.z - z;
        float dw = this.w - w;
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
    public static float distance(float x1, float y1, float z1, float w1, float x2, float y2, float z2, float w2) {
        return (float) Math.sqrt(distanceSquared(x1, y1, z1, w1, x2, y2, z2, w2));
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
    public static float distanceSquared(float x1, float y1, float z1, float w1, float x2, float y2, float z2, float w2) {
        float dx = x1 - x2;
        float dy = y1 - y2;
        float dz = z1 - z2;
        float dw = w1 - w2;
        return dx * dx + dy * dy + dz * dz + dw * dw;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4fc#dot(org.joml.Vector4fc)
     */
    public float dot(Vector4fc v) {
        return x * v.x() + y * v.y() + z * v.z() + w * v.w();
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4fc#dot(float, float, float, float)
     */
    public float dot(float x, float y, float z, float w) {
        return this.x * x + this.y * y + this.z * z + this.w * w;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4fc#angleCos(org.joml.Vector4fc)
     */
    public float angleCos(Vector4fc v) {
        double length1Squared = x * x + y * y + z * z + w * w;
        double length2Squared = v.x() * v.x() + v.y() * v.y() + v.z() * v.z() + v.w() * v.w();
        double dot = x * v.x() + y * v.y() + z * v.z() + w * v.w();
        return (float) (dot / (Math.sqrt(length1Squared * length2Squared)));
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4fc#angle(org.joml.Vector4fc)
     */
    public float angle(Vector4fc v) {
        float cos = angleCos(v);
        // This is because sometimes cos goes above 1 or below -1 because of lost precision
        cos = cos < 1 ? cos : 1;
        cos = cos > -1 ? cos : -1;
        return (float) Math.acos(cos);
    }

    /**
     * Set all components to zero.
     * 
     * @return a vector holding the result
     */
    public Vector4f zero() {
        Vector4f dest = thisOrNew();
        MemUtil.INSTANCE.zero(dest);
        return dest;
    }

    /**
     * Negate this vector.
     * 
     * @return a vector holding the result
     */
    public Vector4f negate() {
        return negate(thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4fc#negate(org.joml.Vector4f)
     */
    public Vector4f negate(Vector4f dest) {
        dest.x = -x;
        dest.y = -y;
        dest.z = -z;
        dest.w = -w;
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
        out.writeFloat(x);
        out.writeFloat(y);
        out.writeFloat(z);
        out.writeFloat(w);
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        x = in.readFloat();
        y = in.readFloat();
        z = in.readFloat();
        w = in.readFloat();
    }

    /**
     * Set the components of this vector to be the component-wise minimum of this and the other vector.
     *
     * @param v
     *          the other vector
     * @return a vector holding the result
     */
    public Vector4f min(Vector4fc v) {
        return min(v, thisOrNew());
    }

    public Vector4f min(Vector4fc v, Vector4f dest) {
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
    public Vector4f max(Vector4fc v) {
        return max(v, thisOrNew());
    }

    public Vector4f max(Vector4fc v, Vector4f dest) {
        dest.x = x > v.x() ? x : v.x();
        dest.y = y > v.y() ? y : v.y();
        dest.z = z > v.z() ? z : v.z();
        dest.w = w > v.w() ? w : v.w();
        return dest;
    }

    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Float.floatToIntBits(w);
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
        Vector4f other = (Vector4f) obj;
        if (Float.floatToIntBits(w) != Float.floatToIntBits(other.w))
            return false;
        if (Float.floatToIntBits(x) != Float.floatToIntBits(other.x))
            return false;
        if (Float.floatToIntBits(y) != Float.floatToIntBits(other.y))
            return false;
        if (Float.floatToIntBits(z) != Float.floatToIntBits(other.z))
            return false;
        return true;
    }

    public boolean equals(Vector4fc v, float delta) {
        if (this == v)
            return true;
        if (v == null)
            return false;
        if (!(v instanceof Vector4fc))
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
     * @see org.joml.Vector4fc#equals(float, float, float, float)
     */
    public boolean equals(float x, float y, float z, float w) {
        if (Float.floatToIntBits(this.x) != Float.floatToIntBits(x))
            return false;
        if (Float.floatToIntBits(this.y) != Float.floatToIntBits(y))
            return false;
        if (Float.floatToIntBits(this.z) != Float.floatToIntBits(z))
            return false;
        if (Float.floatToIntBits(this.w) != Float.floatToIntBits(w))
            return false;
        return true;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4fc#smoothStep(org.joml.Vector4fc, float, org.joml.Vector4f)
     */
    public Vector4f smoothStep(Vector4fc v, float t, Vector4f dest) {
        float t2 = t * t;
        float t3 = t2 * t;
        dest.x = (x + x - v.x() - v.x()) * t3 + (3.0f * v.x() - 3.0f * x) * t2 + x * t + x;
        dest.y = (y + y - v.y() - v.y()) * t3 + (3.0f * v.y() - 3.0f * y) * t2 + y * t + y;
        dest.z = (z + z - v.z() - v.z()) * t3 + (3.0f * v.z() - 3.0f * z) * t2 + z * t + z;
        dest.w = (w + w - v.w() - v.w()) * t3 + (3.0f * v.w() - 3.0f * w) * t2 + w * t + w;
        return dest;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4fc#hermite(org.joml.Vector4fc, org.joml.Vector4fc, org.joml.Vector4fc, float, org.joml.Vector4f)
     */
    public Vector4f hermite(Vector4fc t0, Vector4fc v1, Vector4fc t1, float t, Vector4f dest) {
        float t2 = t * t;
        float t3 = t2 * t;
        dest.x = (x + x - v1.x() - v1.x() + t1.x() + t0.x()) * t3 + (3.0f * v1.x() - 3.0f * x - t0.x() - t0.x() - t1.x()) * t2 + x * t + x;
        dest.y = (y + y - v1.y() - v1.y() + t1.y() + t0.y()) * t3 + (3.0f * v1.y() - 3.0f * y - t0.y() - t0.y() - t1.y()) * t2 + y * t + y;
        dest.z = (z + z - v1.z() - v1.z() + t1.z() + t0.z()) * t3 + (3.0f * v1.z() - 3.0f * z - t0.z() - t0.z() - t1.z()) * t2 + z * t + z;
        dest.w = (w + w - v1.w() - v1.w() + t1.w() + t0.w()) * t3 + (3.0f * v1.w() - 3.0f * w - t0.w() - t0.w() - t1.w()) * t2 + w * t + w;
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
    public Vector4f lerp(Vector4fc other, float t) {
        return lerp(other, t, thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4fc#lerp(org.joml.Vector4fc, float, org.joml.Vector4f)
     */
    public Vector4f lerp(Vector4fc other, float t, Vector4f dest) {
        dest.x = x + (other.x() - x) * t;
        dest.y = y + (other.y() - y) * t;
        dest.z = z + (other.z() - z) * t;
        dest.w = w + (other.w() - w) * t;
        return dest;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4fc#get(int)
     */
    public float get(int component) throws IllegalArgumentException {
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
     * @see org.joml.Vector4fc#maxComponent()
     */
    public int maxComponent() {
        float absX = Math.abs(x);
        float absY = Math.abs(y);
        float absZ = Math.abs(z);
        float absW = Math.abs(w);
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
     * @see org.joml.Vector4fc#minComponent()
     */
    public int minComponent() {
        float absX = Math.abs(x);
        float absY = Math.abs(y);
        float absZ = Math.abs(z);
        float absW = Math.abs(w);
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
     * infinity) {@code float} value that is less than or equal to that
     * component and is equal to a mathematical integer.
     *
     * @return a vector holding the result
     */
    public Vector4f floor() {
        return floor(thisOrNew());
    }

    public Vector4f floor(Vector4f dest) {
        dest.x = Math.floor(x);
        dest.y = Math.floor(y);
        dest.z = Math.floor(z);
        dest.w = Math.floor(w);
        return dest;
    }

    /**
     * Set each component of this vector to the smallest (closest to negative
     * infinity) {@code float} value that is greater than or equal to that
     * component and is equal to a mathematical integer.
     *
     * @return a vector holding the result
     */
    public Vector4f ceil() {
        return ceil(thisOrNew());
    }

    public Vector4f ceil(Vector4f dest) {
        dest.x = Math.ceil(x);
        dest.y = Math.ceil(y);
        dest.z = Math.ceil(z);
        dest.w = Math.ceil(w);
        return dest;
    }

    /**
     * Set each component of this vector to the closest float that is equal to
     * a mathematical integer, with ties rounding to positive infinity.
     *
     * @return a vector holding the result
     */
    public Vector4f round() {
        return round(thisOrNew());
    }

    public Vector4f round(Vector4f dest) {
        dest.x = Math.round(x);
        dest.y = Math.round(y);
        dest.z = Math.round(z);
        dest.w = Math.round(w);
        return dest;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4fc#isFinite()
     */
    public boolean isFinite() {
        return Math.isFinite(x) && Math.isFinite(y) && Math.isFinite(z) && Math.isFinite(w);
    }
}
