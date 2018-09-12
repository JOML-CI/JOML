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
 * Contains the definition of a Vector comprising 3 floats and associated
 * transformations.
 *
 * @author Richard Greenlees
 * @author Kai Burjack
 * @author F. Neurath
 */
public class Vector3f implements Externalizable, Vector3fc {

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
     * Create a new {@link Vector3f} of <code>(0, 0, 0)</code>.
     */
    public Vector3f() {
    }

    /**
     * Create a new {@link Vector3f} and initialize all three components with the given value.
     *
     * @param d
     *          the value of all three components
     */
    public Vector3f(float d) {
        this(d, d, d);
    }

    /**
     * Create a new {@link Vector3f} with the given component values.
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
     *          the {@link Vector3fc} to copy the values from
     */
    public Vector3f(Vector3fc v) {
    	this(v.x(), v.y(), v.z());
    }

    /**
     * Create a new {@link Vector3f} with the same values as <code>v</code>.
     * 
     * @param v
     *          the {@link Vector3ic} to copy the values from
     */
    public Vector3f(Vector3ic v) {
    	this(v.x(), v.y(), v.z());
    }

    /**
     * Create a new {@link Vector3f} with the first two components from the
     * given <code>v</code> and the given <code>z</code>
     * 
     * @param v
     *          the {@link Vector2fc} to copy the values from
     * @param z
     *          the z component
     */
    public Vector3f(Vector2fc v, float z) {
    	this(v.x(), v.y(), z);
    }

    /**
     * Create a new {@link Vector3f} with the first two components from the
     * given <code>v</code> and the given <code>z</code>
     * 
     * @param v
     *          the {@link Vector2ic} to copy the values from
     * @param z
     *          the z component
     */
    public Vector3f(Vector2ic v, float z) {
    	this(v.x(), v.y(), z);
    }

//#ifdef __HAS_NIO__
    /**
     * Create a new {@link Vector3f} and read this vector from the supplied {@link ByteBuffer}
     * at the current buffer {@link ByteBuffer#position() position}.
     * <p>
     * This method will not increment the position of the given ByteBuffer.
     * <p>
     * In order to specify the offset into the ByteBuffer at which
     * the vector is read, use {@link #Vector3f(int, ByteBuffer)}, taking
     * the absolute position as parameter.
     *
     * @param buffer values will be read in <code>x, y, z</code> order
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
     * @param buffer values will be read in <code>x, y, z</code> order
     */
    public Vector3f(int index, ByteBuffer buffer) {
        MemUtil.INSTANCE.get(this, index, buffer);
    }

    /**
     * Create a new {@link Vector3f} and read this vector from the supplied {@link FloatBuffer}
     * at the current buffer {@link FloatBuffer#position() position}.
     * <p>
     * This method will not increment the position of the given FloatBuffer.
     * <p>
     * In order to specify the offset into the FloatBuffer at which
     * the vector is read, use {@link #Vector3f(int, FloatBuffer)}, taking
     * the absolute position as parameter.
     *
     * @param buffer values will be read in <code>x, y, z</code> order
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
     * @param buffer values will be read in <code>x, y, z</code> order
     */
    public Vector3f(int index, FloatBuffer buffer) {
        MemUtil.INSTANCE.get(this, index, buffer);
    }
//#endif

    private Vector3f thisOrNew() {
        return this;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3fc#x()
     */
    public float x() {
        return this.x;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3fc#y()
     */
    public float y() {
        return this.y;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3fc#z()
     */
    public float z() {
        return this.z;
    }

    /**
     * Set the x, y and z components to match the supplied vector.
     * 
     * @param v
     *          contains the values of x, y and z to set
     * @return this
     */
    public Vector3f set(Vector3fc v) {
        return set(v.x(), v.y(), v.z());
    }

    /**
     * Set the x, y and z components to match the supplied vector.
     * <p>
     * Note that due to the given vector <code>v</code> storing the components in double-precision,
     * there is the possibility to lose precision.
     * 
     * @param v
     *          contains the values of x, y and z to set
     * @return this
     */
    public Vector3f set(Vector3dc v) {
        return set((float) v.x(), (float) v.y(), (float) v.z());
    }

    /**
     * Set the x, y and z components to match the supplied vector.
     * 
     * @param v
     *          contains the values of x, y and z to set
     * @return this
     */
    public Vector3f set(Vector3ic v) {
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
    public Vector3f set(Vector2fc v, float z) {
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
    public Vector3f set(Vector2ic v, float z) {
        return set(v.x(), v.y(), z);
    }

    /**
     * Set the x, y, and z components to the supplied value.
     *
     * @param d
     *          the value of all three components
     * @return this
     */
    public Vector3f set(float d) {
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
    public Vector3f set(float x, float y, float z) {
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
    public Vector3f set(ByteBuffer buffer) {
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
    public Vector3f set(int index, ByteBuffer buffer) {
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
     *          values will be read in <code>x, y, z</code> order
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
     * @param index
     *          the absolute position into the FloatBuffer
     * @param buffer
     *          values will be read in <code>x, y, z</code> order
     * @return this
     */
    public Vector3f set(int index, FloatBuffer buffer) {
        MemUtil.INSTANCE.get(this, index, buffer);
        return this;
    }
//#endif

//#ifndef __GWT__
    /**
     * Set the values of this vector by reading 3 float values from off-heap memory,
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
    public Vector3f setFromAddress(long address) {
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
    public Vector3f setComponent(int component, float value) throws IllegalArgumentException {
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
     * @see org.joml.Vector3fc#get(java.nio.FloatBuffer)
     */
    public FloatBuffer get(FloatBuffer buffer) {
        return get(buffer.position(), buffer);
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3fc#get(int, java.nio.FloatBuffer)
     */
    public FloatBuffer get(int index, FloatBuffer buffer) {
        MemUtil.INSTANCE.put(this, index, buffer);
        return buffer;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3fc#get(java.nio.ByteBuffer)
     */
    public ByteBuffer get(ByteBuffer buffer) {
        return get(buffer.position(), buffer);
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3fc#get(int, java.nio.ByteBuffer)
     */
    public ByteBuffer get(int index, ByteBuffer buffer) {
        MemUtil.INSTANCE.put(this, index, buffer);
        return buffer;
    }
//#endif

//#ifndef __GWT__
    public Vector3fc getToAddress(long address) {
        if (Options.NO_UNSAFE)
            throw new UnsupportedOperationException("Not supported when using joml.nounsafe");
        MemUtil.MemUtilUnsafe unsafe = (MemUtil.MemUtilUnsafe) MemUtil.INSTANCE;
        unsafe.put(this, address);
        return this;
    }
//#endif

    /**
     * Subtract the supplied vector from this one and store the result in <code>this</code>.
     * 
     * @param v
     *          the vector to subtract
     * @return a vector holding the result
     */
    public Vector3f sub(Vector3fc v) {
        return sub(v, thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3fc#sub(org.joml.Vector3fc, org.joml.Vector3f)
     */
    public Vector3f sub(Vector3fc v, Vector3f dest) {
        dest.x = x - v.x();
        dest.y = y - v.y();
        dest.z = z - v.z();
        return dest;
    }

    /**
     * Decrement the components of this vector by the given values.
     * 
     * @param x
     *          the x component to subtract
     * @param y
     *          the y component to subtract
     * @param z
     *          the z component to subtract
     * @return a vector holding the result
     */
    public Vector3f sub(float x, float y, float z) {
        return sub(x, y, z, thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3fc#sub(float, float, float, org.joml.Vector3f)
     */
    public Vector3f sub(float x, float y, float z, Vector3f dest) {
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
    public Vector3f add(Vector3fc v) {
        return add(v, thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3fc#add(org.joml.Vector3fc, org.joml.Vector3f)
     */
    public Vector3f add(Vector3fc v, Vector3f dest) {
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
    public Vector3f add(float x, float y, float z) {
        return add(x, y, z, thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3fc#add(float, float, float, org.joml.Vector3f)
     */
    public Vector3f add(float x, float y, float z, Vector3f dest) {
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
    public Vector3f fma(Vector3fc a, Vector3fc b) {
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
    public Vector3f fma(float a, Vector3fc b) {
        return fma(a, b, thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3fc#fma(org.joml.Vector3fc, org.joml.Vector3fc, org.joml.Vector3f)
     */
    public Vector3f fma(Vector3fc a, Vector3fc b, Vector3f dest) {
        dest.x = x + a.x() * b.x();
        dest.y = y + a.y() * b.y();
        dest.z = z + a.z() * b.z();
        return dest;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3fc#fma(float, org.joml.Vector3fc, org.joml.Vector3f)
     */
    public Vector3f fma(float a, Vector3fc b, Vector3f dest) {
        dest.x = x + a * b.x();
        dest.y = y + a * b.y();
        dest.z = z + a * b.z();
        return dest;
    }

    /**
     * Multiply this Vector3f component-wise by another Vector3fc.
     * 
     * @param v
     *          the vector to multiply by
     * @return a vector holding the result
     */
    public Vector3f mul(Vector3fc v) {
        return mul(v, thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3fc#mul(org.joml.Vector3fc, org.joml.Vector3f)
     */
    public Vector3f mul(Vector3fc v, Vector3f dest) {
        dest.x = x * v.x();
        dest.y = y * v.y();
        dest.z = z * v.z();
        return dest;
    }

    /**
     * Divide this Vector3f component-wise by another Vector3fc.
     * 
     * @param v
     *          the vector to divide by
     * @return a vector holding the result
     */
    public Vector3f div(Vector3fc v) {
        return div(v.x(), v.y(), v.z(), thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3fc#div(org.joml.Vector3fc, org.joml.Vector3f)
     */
    public Vector3f div(Vector3fc v, Vector3f dest) {
        dest.x = x / v.x();
        dest.y = y / v.y();
        dest.z = z / v.z();
        return dest;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3fc#mulProject(org.joml.Matrix4fc, org.joml.Vector3f)
     */
    public Vector3f mulProject(Matrix4fc mat, Vector3f dest) {
        float invW = 1.0f / (mat.m03() * x + mat.m13() * y + mat.m23() * z + mat.m33());
        float rx = (mat.m00() * x + mat.m10() * y + mat.m20() * z + mat.m30()) * invW;
        float ry = (mat.m01() * x + mat.m11() * y + mat.m21() * z + mat.m31()) * invW;
        float rz = (mat.m02() * x + mat.m12() * y + mat.m22() * z + mat.m32()) * invW;
        dest.x = rx;
        dest.y = ry;
        dest.z = rz;
        return dest;
    }

    /**
     * Multiply the given matrix <code>mat</code> with this Vector3f, perform perspective division.
     * <p>
     * This method uses <code>w=1.0</code> as the fourth vector component.
     * 
     * @param mat
     *          the matrix to multiply this vector by
     * @return a vector holding the result
     */
    public Vector3f mulProject(Matrix4fc mat) {
        return mulProject(mat, thisOrNew());
    }

    /**
     * Multiply the given matrix with this Vector3f and store the result in <code>this</code>.
     * 
     * @param mat
     *          the matrix
     * @return a vector holding the result
     */
    public Vector3f mul(Matrix3fc mat) {
        return mul(mat, thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3fc#mul(org.joml.Matrix3fc, org.joml.Vector3f)
     */
    public Vector3f mul(Matrix3fc mat, Vector3f dest) {
        float rx = mat.m00() * x + mat.m10() * y + mat.m20() * z;
        float ry = mat.m01() * x + mat.m11() * y + mat.m21() * z;
        float rz = mat.m02() * x + mat.m12() * y + mat.m22() * z;
        dest.x = rx;
        dest.y = ry;
        dest.z = rz;
        return dest;
    }

    /**
     * Multiply the given matrix with this Vector3f and store the result in <code>this</code>.
     * 
     * @param mat
     *          the matrix
     * @return a vector holding the result
     */
    public Vector3f mul(Matrix3dc mat) {
        return mul(mat, thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3fc#mul(org.joml.Matrix3dc, org.joml.Vector3f)
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

    /**
     * Multiply the given matrix with this Vector3f and store the result in <code>this</code>.
     * 
     * @param mat
     *          the matrix
     * @return a vector holding the result
     */
    public Vector3f mul(Matrix3x2fc mat) {
        return mul(mat, thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3fc#mul(org.joml.Matrix3x2fc, org.joml.Vector3f)
     */
    public Vector3f mul(Matrix3x2fc mat, Vector3f dest) {
        float rx = mat.m00() * x + mat.m10() * y + mat.m20() * z;
        float ry = mat.m01() * x + mat.m11() * y + mat.m21() * z;
        dest.x = rx;
        dest.y = ry;
        dest.z = z;
        return dest;
    }

    /**
     * Multiply the transpose of the given matrix with this Vector3f store the result in <code>this</code>.
     * 
     * @param mat
     *          the matrix
     * @return a vector holding the result
     */
    public Vector3f mulTranspose(Matrix3fc mat) {
        return mulTranspose(mat, thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3fc#mulTranspose(org.joml.Matrix3fc, org.joml.Vector3f)
     */
    public Vector3f mulTranspose(Matrix3fc mat, Vector3f dest) {
        float rx = mat.m00() * x + mat.m01() * y + mat.m02() * z;
        float ry = mat.m10() * x + mat.m11() * y + mat.m12() * z;
        float rz = mat.m20() * x + mat.m21() * y + mat.m22() * z;
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
    public Vector3f mulPosition(Matrix4fc mat) {
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
    public Vector3f mulPosition(Matrix4x3fc mat) {
        return mulPosition(mat, thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3fc#mulPosition(org.joml.Matrix4fc, org.joml.Vector3f)
     */
    public Vector3f mulPosition(Matrix4fc mat, Vector3f dest) {
        float rx = mat.m00() * x + mat.m10() * y + mat.m20() * z + mat.m30();
        float ry = mat.m01() * x + mat.m11() * y + mat.m21() * z + mat.m31();
        float rz = mat.m02() * x + mat.m12() * y + mat.m22() * z + mat.m32();
        dest.x = rx;
        dest.y = ry;
        dest.z = rz;
        return dest;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3fc#mulPosition(org.joml.Matrix4x3fc, org.joml.Vector3f)
     */
    public Vector3f mulPosition(Matrix4x3fc mat, Vector3f dest) {
        float rx = mat.m00() * x + mat.m10() * y + mat.m20() * z + mat.m30();
        float ry = mat.m01() * x + mat.m11() * y + mat.m21() * z + mat.m31();
        float rz = mat.m02() * x + mat.m12() * y + mat.m22() * z + mat.m32();
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
    public Vector3f mulTransposePosition(Matrix4fc mat) {
        return mulTransposePosition(mat, thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3fc#mulTransposePosition(org.joml.Matrix4fc, org.joml.Vector3f)
     */
    public Vector3f mulTransposePosition(Matrix4fc mat, Vector3f dest) {
        float rx = mat.m00() * x + mat.m01() * y + mat.m02() * z + mat.m03();
        float ry = mat.m10() * x + mat.m11() * y + mat.m12() * z + mat.m13();
        float rz = mat.m20() * x + mat.m21() * y + mat.m22() * z + mat.m23();
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
    public float mulPositionW(Matrix4fc mat) {
        return mulPositionW(mat, thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3fc#mulPositionW(org.joml.Matrix4fc, org.joml.Vector3f)
     */
    public float mulPositionW(Matrix4fc mat, Vector3f dest) {
        float w = mat.m03() * x + mat.m13() * y + mat.m23() * z + mat.m33();
        float rx = mat.m00() * x + mat.m10() * y + mat.m20() * z + mat.m30();
        float ry = mat.m01() * x + mat.m11() * y + mat.m21() * z + mat.m31();
        float rz = mat.m02() * x + mat.m12() * y + mat.m22() * z + mat.m32();
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
    public Vector3f mulDirection(Matrix4dc mat) {
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
    public Vector3f mulDirection(Matrix4fc mat) {
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
    public Vector3f mulDirection(Matrix4x3fc mat) {
        return mulDirection(mat, thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3fc#mulDirection(org.joml.Matrix4dc, org.joml.Vector3f)
     */
    public Vector3f mulDirection(Matrix4dc mat, Vector3f dest) {
        double rx = mat.m00() * x + mat.m10() * y + mat.m20() * z;
        double ry = mat.m01() * x + mat.m11() * y + mat.m21() * z;
        double rz = mat.m02() * x + mat.m12() * y + mat.m22() * z;
        dest.x = (float) rx;
        dest.y = (float) ry;
        dest.z = (float) rz;
        return dest;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3fc#mulDirection(org.joml.Matrix4fc, org.joml.Vector3f)
     */
    public Vector3f mulDirection(Matrix4fc mat, Vector3f dest) {
        float rx = mat.m00() * x + mat.m10() * y + mat.m20() * z;
        float ry = mat.m01() * x + mat.m11() * y + mat.m21() * z;
        float rz = mat.m02() * x + mat.m12() * y + mat.m22() * z;
        dest.x = rx;
        dest.y = ry;
        dest.z = rz;
        return dest;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3fc#mulDirection(org.joml.Matrix4x3fc, org.joml.Vector3f)
     */
    public Vector3f mulDirection(Matrix4x3fc mat, Vector3f dest) {
        float rx = mat.m00() * x + mat.m10() * y + mat.m20() * z;
        float ry = mat.m01() * x + mat.m11() * y + mat.m21() * z;
        float rz = mat.m02() * x + mat.m12() * y + mat.m22() * z;
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
    public Vector3f mulTransposeDirection(Matrix4fc mat) {
        return mulTransposeDirection(mat, thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3fc#mulTransposeDirection(org.joml.Matrix4fc, org.joml.Vector3f)
     */
    public Vector3f mulTransposeDirection(Matrix4fc mat, Vector3f dest) {
        float rx = mat.m00() * x + mat.m01() * y + mat.m02() * z;
        float ry = mat.m10() * x + mat.m11() * y + mat.m12() * z;
        float rz = mat.m20() * x + mat.m21() * y + mat.m22() * z;
        dest.x = rx;
        dest.y = ry;
        dest.z = rz;
        return dest;
    }

    /**
     * Multiply all components of this {@link Vector3f} by the given scalar
     * value.
     * 
     * @param scalar
     *          the scalar to multiply this vector by
     * @return a vector holding the result
     */
    public Vector3f mul(float scalar) {
        return mul(scalar, thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3fc#mul(float, org.joml.Vector3f)
     */
    public Vector3f mul(float scalar, Vector3f dest) {
        dest.x = x * scalar;
        dest.y = y * scalar;
        dest.z = z * scalar;
        return dest;
    }

    /**
     * Multiply the components of this Vector3f by the given scalar values and store the result in <code>this</code>.
     * 
     * @param x
     *          the x component to multiply this vector by
     * @param y
     *          the y component to multiply this vector by
     * @param z
     *          the z component to multiply this vector by
     * @return a vector holding the result
     */
    public Vector3f mul(float x, float y, float z) {
        return mul(x, y, z, thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3fc#mul(float, float, float, org.joml.Vector3f)
     */
    public Vector3f mul(float x, float y, float z, Vector3f dest) {
        dest.x = this.x * x;
        dest.y = this.y * y;
        dest.z = this.z * z;
        return dest;
    }

    /**
     * Divide all components of this {@link Vector3f} by the given scalar
     * value.
     * 
     * @param scalar
     *          the scalar to divide by
     * @return a vector holding the result
     */
    public Vector3f div(float scalar) {
        return div(scalar, thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3fc#div(float, org.joml.Vector3f)
     */
    public Vector3f div(float scalar, Vector3f dest) {
        float inv = 1.0f / scalar;
        dest.x = x * inv;
        dest.y = y * inv;
        dest.z = z * inv;
        return dest;
    }

    /**
     * Divide the components of this Vector3f by the given scalar values and store the result in <code>this</code>.
     * 
     * @param x
     *          the x component to divide this vector by
     * @param y
     *          the y component to divide this vector by
     * @param z
     *          the z component to divide this vector by
     * @return a vector holding the result
     */
    public Vector3f div(float x, float y, float z) {
        return div(x, y, z, thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3fc#div(float, float, float, org.joml.Vector3f)
     */
    public Vector3f div(float x, float y, float z, Vector3f dest) {
        dest.x = this.x / x;
        dest.y = this.y / y;
        dest.z = this.z / z;
        return dest;
    }

    /**
     * Rotate this vector by the given quaternion <code>quat</code> and store the result in <code>this</code>.
     * 
     * @see Quaternionfc#transform(Vector3f)
     * 
     * @param quat
     *          the quaternion to rotate this vector
     * @return a vector holding the result
     */
    public Vector3f rotate(Quaternionfc quat) {
        return rotate(quat, thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3fc#rotate(org.joml.Quaternionfc, org.joml.Vector3f)
     */
    public Vector3f rotate(Quaternionfc quat, Vector3f dest) {
        return quat.transform(this, dest);
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3fc#rotationTo(org.joml.Vector3fc, org.joml.Quaternionf)
     */
    public Quaternionf rotationTo(Vector3fc toDir, Quaternionf dest) {
        return dest.rotationTo(this, toDir);
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3fc#rotationTo(float, float, float, org.joml.Quaternionf)
     */
    public Quaternionf rotationTo(float toDirX, float toDirY, float toDirZ, Quaternionf dest) {
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
    public Vector3f rotateAxis(float angle, float x, float y, float z) {
        return rotateAxis(angle, x, y, z, thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3fc#rotateAxis(float, float, float, float, org.joml.Vector3f)
     */
    public Vector3f rotateAxis(float angle, float aX, float aY, float aZ, Vector3f dest) {
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
    public Vector3f rotateX(float angle) {
        return rotateX(angle, thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3fc#rotateX(float, org.joml.Vector3f)
     */
    public Vector3f rotateX(float angle, Vector3f dest) {
        float sin = (float) Math.sin(angle), cos = (float) Math.cosFromSin(sin, angle);
        float y = this.y * cos - this.z * sin;
        float z = this.y * sin + this.z * cos;
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
    public Vector3f rotateY(float angle) {
        return rotateY(angle, thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3fc#rotateY(float, org.joml.Vector3f)
     */
    public Vector3f rotateY(float angle, Vector3f dest) {
        float sin = (float) Math.sin(angle), cos = (float) Math.cosFromSin(sin, angle);
        float x =  this.x * cos + this.z * sin;
        float z = -this.x * sin + this.z * cos;
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
    public Vector3f rotateZ(float angle) {
        return rotateZ(angle, thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3fc#rotateZ(float, org.joml.Vector3f)
     */
    public Vector3f rotateZ(float angle, Vector3f dest) {
        float sin = (float) Math.sin(angle), cos = (float) Math.cosFromSin(sin, angle);
        float x = this.x * cos - this.y * sin;
        float y = this.x * sin + this.y * cos;
        dest.x = x;
        dest.y = y;
        dest.z = this.z;
        return dest;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3fc#lengthSquared()
     */
    public float lengthSquared() {
        return lengthSquared(x, y, z);
    }

    /**
     * Get the length squared of a 3-dimensional single-precision vector.
     *
     * @param x The vector's x component
     * @param y The vector's y component
     * @param z The vector's z component
     *
     * @return the length squared of the given vector
     *
     * @author F. Neurath
     */
    public static float lengthSquared(float x, float y, float z) {
        return x * x + y * y + z * z;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3fc#length()
     */
    public float length() {
        return (float) Math.sqrt(lengthSquared());
    }

    /**
     * Get the length of a 3-dimensional single-precision vector.
     *
     * @param x The vector's x component
     * @param y The vector's y component
     * @param z The vector's z component
     *
     * @return the length of the given vector
     *
     * @author F. Neurath
     */
    public static float length(float x, float y, float z) {
        return (float) Math.sqrt(lengthSquared(x, y, z));
    }

    /**
     * Normalize this vector.
     * 
     * @return a vector holding the result
     */
    public Vector3f normalize() {
        return normalize(thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3fc#normalize(org.joml.Vector3f)
     */
    public Vector3f normalize(Vector3f dest) {
        float invLength = 1.0f / length();
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
    public Vector3f normalize(float length) {
        return normalize(length, thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3fc#normalize(float, org.joml.Vector3f)
     */
    public Vector3f normalize(float length, Vector3f dest) {
        float invLength = 1.0f / length() * length;
        dest.x = x * invLength;
        dest.y = y * invLength;
        dest.z = z * invLength;
        return dest;
    }

    /**
     * Set this vector to be the cross product of itself and <code>v</code>.
     * 
     * @param v
     *          the other vector
     * @return a vector holding the result
     */
    public Vector3f cross(Vector3fc v) {
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
    public Vector3f cross(float x, float y, float z) {
        return cross(x, y, z, thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3fc#cross(org.joml.Vector3fc, org.joml.Vector3f)
     */
    public Vector3f cross(Vector3fc v, Vector3f dest) {
        float rx = y * v.z() - z * v.y();
        float ry = z * v.x() - x * v.z();
        float rz = x * v.y() - y * v.x();
        dest.x = rx;
        dest.y = ry;
        dest.z = rz;
        return dest;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3fc#cross(float, float, float, org.joml.Vector3f)
     */
    public Vector3f cross(float x, float y, float z, Vector3f dest) {
        float rx = this.y * z - this.z * y;
        float ry = this.z * x - this.x * z;
        float rz = this.x * y - this.y * x;
        dest.x = rx;
        dest.y = ry;
        dest.z = rz;
        return dest;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3fc#distance(org.joml.Vector3fc)
     */
    public float distance(Vector3fc v) {
    	return distance(v.x(), v.y(), v.z());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3fc#distance(float, float, float)
     */
    public float distance(float x, float y, float z) {
        float dx = this.x - x;
        float dy = this.y - y;
        float dz = this.z - z;
        return (float) Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3fc#distanceSquared(org.joml.Vector3fc)
     */
    public float distanceSquared(Vector3fc v) {
        return distanceSquared(v.x(), v.y(), v.z());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3fc#distanceSquared(float, float, float)
     */
    public float distanceSquared(float x, float y, float z) {
        float dx = this.x - x;
        float dy = this.y - y;
        float dz = this.z - z;
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
    public static float distance(float x1, float y1, float z1, float x2, float y2, float z2) {
        return (float) Math.sqrt(distanceSquared(x1, y1, z1, x2, y2, z2));
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
    public static float distanceSquared(float x1, float y1, float z1, float x2, float y2, float z2) {
        float dx = x1 - x2;
        float dy = y1 - y2;
        float dz = z1 - z2;
        return dx * dx + dy * dy + dz * dz;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3fc#dot(org.joml.Vector3fc)
     */
    public float dot(Vector3fc v) {
        return dot(v.x(), v.y(), v.z());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3fc#dot(float, float, float)
     */
    public float dot(float x, float y, float z) {
        return this.x * x + this.y * y + this.z * z;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3fc#angleCos(org.joml.Vector3fc)
     */
    public float angleCos(Vector3fc v) {
        double length1Squared = x * x + y * y + z * z;
        double length2Squared = v.x() * v.x() + v.y() * v.y() + v.z() * v.z();
        double dot = x * v.x() + y * v.y() + z * v.z();
        return (float) (dot / (Math.sqrt(length1Squared * length2Squared)));
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3fc#angle(org.joml.Vector3fc)
     */
    public float angle(Vector3fc v) {
        float cos = angleCos(v);
        // This is because sometimes cos goes above 1 or below -1 because of lost precision
        cos = cos < 1 ? cos : 1;
        cos = cos > -1 ? cos : -1;
        return (float) Math.acos(cos);
    }

    /**
     * Set the components of this vector to be the component-wise minimum of this and the other vector.
     *
     * @param v
     *          the other vector
     * @return a vector holding the result
     */
    public Vector3f min(Vector3fc v) {
        return min(v, thisOrNew());
    }

    public Vector3f min(Vector3fc v, Vector3f dest) {
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
    public Vector3f max(Vector3fc v) {
        return max(v, thisOrNew());
    }

    public Vector3f max(Vector3fc v, Vector3f dest) {
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
    public Vector3f zero() {
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
     * @return a vector holding the result
     */
    public Vector3f negate() {
        return negate(thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3fc#negate(org.joml.Vector3f)
     */
    public Vector3f negate(Vector3f dest) {
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
    public Vector3f absolute() {
        return absolute(thisOrNew());
    }

    /*
     * (non-Javadoc)
     * @see org.joml.Vector3fc#absolute(org.joml.Vector3f)
     */
    public Vector3f absolute(Vector3f dest) {
        dest.x = Math.abs(this.x);
        dest.y = Math.abs(this.y);
        dest.z = Math.abs(this.z);
        return dest;
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

    public boolean equals(Vector3fc v, float delta) {
        if (this == v)
            return true;
        if (v == null)
            return false;
        if (!(v instanceof Vector3fc))
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
     * @see org.joml.Vector3fc#equals(float, float, float)
     */
    public boolean equals(float x, float y, float z) {
        if (Float.floatToIntBits(this.x) != Float.floatToIntBits(x))
            return false;
        if (Float.floatToIntBits(this.y) != Float.floatToIntBits(y))
            return false;
        if (Float.floatToIntBits(this.z) != Float.floatToIntBits(z))
            return false;
        return true;
    }

    /**
     * Reflect this vector about the given <code>normal</code> vector.
     * 
     * @param normal
     *          the vector to reflect about
     * @return a vector holding the result
     */
    public Vector3f reflect(Vector3fc normal) {
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
    public Vector3f reflect(float x, float y, float z) {
        return reflect(x, y, z, thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3fc#reflect(org.joml.Vector3fc, org.joml.Vector3f)
     */
    public Vector3f reflect(Vector3fc normal, Vector3f dest) {
        return reflect(normal.x(), normal.y(), normal.z(), dest);
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3fc#reflect(float, float, float, org.joml.Vector3f)
     */
    public Vector3f reflect(float x, float y, float z, Vector3f dest) {
        float dot = this.dot(x, y, z);
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
    public Vector3f half(Vector3fc other) {
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
    public Vector3f half(float x, float y, float z) {
        return half(x, y, z, thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3fc#half(org.joml.Vector3fc, org.joml.Vector3f)
     */
    public Vector3f half(Vector3fc other, Vector3f dest) {
        return half(other.x(), other.y(), other.z(), dest);
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3fc#half(float, float, float, org.joml.Vector3f)
     */
    public Vector3f half(float x, float y, float z, Vector3f dest) {
        return dest.set(this).add(x, y, z).normalize();
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3fc#smoothStep(org.joml.Vector3fc, float, org.joml.Vector3f)
     */
    public Vector3f smoothStep(Vector3fc v, float t, Vector3f dest) {
        float t2 = t * t;
        float t3 = t2 * t;
        dest.x = (x + x - v.x() - v.x()) * t3 + (3.0f * v.x() - 3.0f * x) * t2 + x * t + x;
        dest.y = (y + y - v.y() - v.y()) * t3 + (3.0f * v.y() - 3.0f * y) * t2 + y * t + y;
        dest.z = (z + z - v.z() - v.z()) * t3 + (3.0f * v.z() - 3.0f * z) * t2 + z * t + z;
        return dest;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3fc#hermite(org.joml.Vector3fc, org.joml.Vector3fc, org.joml.Vector3fc, float, org.joml.Vector3f)
     */
    public Vector3f hermite(Vector3fc t0, Vector3fc v1, Vector3fc t1, float t, Vector3f dest) {
        float t2 = t * t;
        float t3 = t2 * t;
        dest.x = (x + x - v1.x() - v1.x() + t1.x() + t0.x()) * t3 + (3.0f * v1.x() - 3.0f * x - t0.x() - t0.x() - t1.x()) * t2 + x * t + x;
        dest.y = (y + y - v1.y() - v1.y() + t1.y() + t0.y()) * t3 + (3.0f * v1.y() - 3.0f * y - t0.y() - t0.y() - t1.y()) * t2 + y * t + y;
        dest.z = (z + z - v1.z() - v1.z() + t1.z() + t0.z()) * t3 + (3.0f * v1.z() - 3.0f * z - t0.z() - t0.z() - t1.z()) * t2 + z * t + z;
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
    public Vector3f lerp(Vector3fc other, float t) {
        return lerp(other, t, thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3fc#lerp(org.joml.Vector3fc, float, org.joml.Vector3f)
     */
    public Vector3f lerp(Vector3fc other, float t, Vector3f dest) {
        dest.x = x + (other.x() - x) * t;
        dest.y = y + (other.y() - y) * t;
        dest.z = z + (other.z() - z) * t;
        return dest;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3fc#get(int)
     */
    public float get(int component) throws IllegalArgumentException {
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
     * @see org.joml.Vector3fc#maxComponent()
     */
    public int maxComponent() {
        float absX = Math.abs(x);
        float absY = Math.abs(y);
        float absZ = Math.abs(z);
        if (absX >= absY && absX >= absZ) {
            return 0;
        } else if (absY >= absZ) {
            return 1;
        }
        return 2;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3fc#minComponent()
     */
    public int minComponent() {
        float absX = Math.abs(x);
        float absY = Math.abs(y);
        float absZ = Math.abs(z);
        if (absX < absY && absX < absZ) {
            return 0;
        } else if (absY < absZ) {
            return 1;
        }
        return 2;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3fc#orthogonalize(org.joml.Vector3fc, org.joml.Vector3f)
     */
    public Vector3f orthogonalize(Vector3fc v, Vector3f dest) {
        /*
         * http://lolengine.net/blog/2013/09/21/picking-orthogonal-vector-combing-coconuts
         */
        float rx, ry, rz;
        if (Math.abs(v.x()) > Math.abs(v.z())) {
            rx = -v.y();
            ry = v.x();
            rz = 0.0f;
        } else {
            rx = 0.0f;
            ry = -v.z();
            rz = v.y();
        }
        float invLen = 1.0f / (float) Math.sqrt(rx * rx + ry * ry + rz * rz);
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
    public Vector3f orthogonalize(Vector3fc v) {
        return orthogonalize(v, thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3fc#orthogonalizeUnit(org.joml.Vector3fc, org.joml.Vector3f)
     */
    public Vector3f orthogonalizeUnit(Vector3fc v, Vector3f dest) {
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
    public Vector3f orthogonalizeUnit(Vector3fc v) {
        return orthogonalizeUnit(v, thisOrNew());
    }

    /**
     * Set each component of this vector to the largest (closest to positive
     * infinity) {@code float} value that is less than or equal to that
     * component and is equal to a mathematical integer.
     *
     * @return a vector holding the result
     */
    public Vector3f floor() {
        return floor(thisOrNew());
    }

    public Vector3f floor(Vector3f dest) {
        dest.x = Math.floor(x);
        dest.y = Math.floor(y);
        dest.z = Math.floor(z);
        return dest;
    }

    /**
     * Set each component of this vector to the smallest (closest to negative
     * infinity) {@code float} value that is greater than or equal to that
     * component and is equal to a mathematical integer.
     *
     * @return a vector holding the result
     */
    public Vector3f ceil() {
        return ceil(thisOrNew());
    }

    public Vector3f ceil(Vector3f dest) {
        dest.x = Math.ceil(x);
        dest.y = Math.ceil(y);
        dest.z = Math.ceil(z);
        return dest;
    }

    /**
     * Set each component of this vector to the closest float that is equal to
     * a mathematical integer, with ties rounding to positive infinity.
     *
     * @return a vector holding the result
     */
    public Vector3f round() {
        return round(thisOrNew());
    }

    public Vector3f round(Vector3f dest) {
        dest.x = Math.round(x);
        dest.y = Math.round(y);
        dest.z = Math.round(z);
        return dest;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3fc#isFinite()
     */
    public boolean isFinite() {
        return Math.isFinite(x) && Math.isFinite(y) && Math.isFinite(z);
    }

}
