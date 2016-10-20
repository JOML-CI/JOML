/*
 * (C) Copyright 2015-2016 Richard Greenlees

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
import java.nio.FloatBuffer;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Contains the definition of a Vector comprising 4 floats and associated
 * transformations.
 * 
 * @author Richard Greenlees
 * @author Kai Burjack
 */
public class Vector4f implements Externalizable, Vector4fc {

    private final class Proxy implements Vector4fc {
        private final Vector4fc delegate;

        Proxy(Vector4fc delegate) {
            super();
            this.delegate = delegate;
        }

        public float x() {
            return delegate.x();
        }

        public float y() {
            return delegate.y();
        }

        public float z() {
            return delegate.z();
        }

        public float w() {
            return delegate.w();
        }

        public FloatBuffer get(FloatBuffer buffer) {
            return delegate.get(buffer);
        }

        public FloatBuffer get(int index, FloatBuffer buffer) {
            return delegate.get(index, buffer);
        }

        public ByteBuffer get(ByteBuffer buffer) {
            return delegate.get(buffer);
        }

        public ByteBuffer get(int index, ByteBuffer buffer) {
            return delegate.get(index, buffer);
        }

        public Vector4f sub(Vector4fc v, Vector4f dest) {
            return delegate.sub(v, dest);
        }

        public Vector4f sub(float x, float y, float z, float w, Vector4f dest) {
            return delegate.sub(x, y, z, w, dest);
        }

        public Vector4f add(Vector4fc v, Vector4f dest) {
            return delegate.add(v, dest);
        }

        public Vector4f add(float x, float y, float z, float w, Vector4f dest) {
            return delegate.add(x, y, z, w, dest);
        }

        public Vector4f fma(Vector4fc a, Vector4fc b, Vector4f dest) {
            return delegate.fma(a, b, dest);
        }

        public Vector4f fma(float a, Vector4fc b, Vector4f dest) {
            return delegate.fma(a, b, dest);
        }

        public Vector4f mul(Vector4fc v, Vector4f dest) {
            return delegate.mul(v, dest);
        }

        public Vector4f div(Vector4fc v, Vector4f dest) {
            return delegate.div(v, dest);
        }

        public Vector4f mul(Matrix4fc mat, Vector4f dest) {
            return delegate.mul(mat, dest);
        }

        public Vector4f mul(Matrix4x3f mat, Vector4f dest) {
            return delegate.mul(mat, dest);
        }

        public Vector4f mulProject(Matrix4fc mat, Vector4f dest) {
            return delegate.mulProject(mat, dest);
        }

        public Vector4f mul(float scalar, Vector4f dest) {
            return delegate.mul(scalar, dest);
        }

        public Vector4f mul(float x, float y, float z, float w, Vector4f dest) {
            return delegate.mul(x, y, z, w, dest);
        }

        public Vector4f div(float scalar, Vector4f dest) {
            return delegate.div(scalar, dest);
        }

        public Vector4f div(float x, float y, float z, float w, Vector4f dest) {
            return delegate.div(x, y, z, w, dest);
        }

        public Vector4f rotate(Quaternionfc quat, Vector4f dest) {
            return delegate.rotate(quat, dest);
        }

        public float lengthSquared() {
            return delegate.lengthSquared();
        }

        public float length() {
            return delegate.length();
        }

        public Vector4f normalize(Vector4f dest) {
            return delegate.normalize(dest);
        }

        public float distance(Vector4fc v) {
            return delegate.distance(v);
        }

        public float distance(float x, float y, float z, float w) {
            return delegate.distance(x, y, z, w);
        }

        public float dot(Vector4fc v) {
            return delegate.dot(v);
        }

        public float dot(float x, float y, float z, float w) {
            return delegate.dot(x, y, z, w);
        }

        public float angleCos(Vector4fc v) {
            return delegate.angleCos(v);
        }

        public float angle(Vector4fc v) {
            return delegate.angle(v);
        }

        public Vector4f negate(Vector4f dest) {
            return delegate.negate(dest);
        }

        public Vector4f lerp(Vector4fc other, float t, Vector4f dest) {
            return delegate.lerp(other, t, dest);
        }

        public Vector4f smoothStep(Vector4fc v, float t, Vector4f dest) {
            return delegate.smoothStep(v, t, dest);
        }

        public Vector4f hermite(Vector4fc t0, Vector4fc v1, Vector4fc t1, float t, Vector4f dest) {
            return delegate.hermite(t0, v1, t1, t, dest);
        }
    }

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
     *          the {@link Vector4f} to copy the values from
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
     * Create a new {@link Vector4f} with the first three components from the
     * given <code>v</code> and the given <code>w</code>.
     * 
     * @param v
     *          the {@link Vector3fc}
     * @param w
     *          the w component
     */
    public Vector4f(Vector3fc v, float w) {
        this.x = v.x();
        this.y = v.y();
        this.z = v.z();
        this.w = w;
    }

    /**
     * Create a new {@link Vector4f} with the first two components from the
     * given <code>v</code> and the given <code>z</code>, and <code>w</code>.
     * 
     * @param v
     *          the {@link Vector2f}
     * @param z
     *          the z component
     * @param w
     *          the w component
     */
    public Vector4f(Vector2f v, float z, float w) {
        this.x = v.x;
        this.y = v.y;
        this.z = z;
        this.w = w;
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
     *          values will be read in <tt>x, y, z, w</tt> order
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
     *          values will be read in <tt>x, y, z, w</tt> order
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
     *          values will be read in <tt>x, y, z, w</tt> order
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
     *          values will be read in <tt>x, y, z, w</tt> order
     */
    public Vector4f(int index, FloatBuffer buffer) {
        MemUtil.INSTANCE.get(this, index, buffer);
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
     * <p>
     * Note that due to the given vector <code>v</code> storing the components in double-precision,
     * there is the possibility to lose precision.
     * 
     * @param v
     *          the vector whose values will be copied into this
     * @return this
     */
    public Vector4f set(Vector4dc v) {
        this.x = (float) v.x();
        this.y = (float) v.y();
        this.z = (float) v.z();
        this.w = (float) v.w();
        return this;
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
        this.x = v.x();
        this.y = v.y();
        this.z = v.z();
        this.w = w;
        return this;
    }

    /**
     * Sets the first two components of this to the components of given <code>v</code>
     * and last two components to the given <code>z</code>, and <code>w</code>.
     *
     * @param v
     *          the {@link Vector2f}
     * @param z
     *          the z component
     * @param w
     *          the w component
     * @return this
     */
    public Vector4f set(Vector2f v, float z, float w) {
        this.x = v.x;
        this.y = v.y;
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
     *          values will be read in <tt>x, y, z, w</tt> order
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
     *          values will be read in <tt>x, y, z, w</tt> order
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
     *          values will be read in <tt>x, y, z, w</tt> order
     * @return this
     */
    public Vector4f set(int index, FloatBuffer buffer) {
        MemUtil.INSTANCE.get(this, index, buffer);
        return this;
    }

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

    /**
     * Subtract the supplied vector from this one.
     * 
     * @param v
     *          the vector to subtract
     * @return this
     */
    public Vector4f sub(Vector4fc v) {
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
    public Vector4f sub(float x, float y, float z, float w) {
        this.x -= x;
        this.y -= y;
        this.z -= z;
        this.w -= w;
        return this;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4fc#sub(org.joml.Vector4f, org.joml.Vector4f)
     */
    public Vector4f sub(Vector4fc v, Vector4f dest) {
        dest.x = x - v.x();
        dest.y = y - v.y();
        dest.z = z - v.z();
        dest.w = w - v.w();
        return dest;
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
     * @return this
     */
    public Vector4f add(Vector4fc v) {
        x += v.x();
        y += v.y();
        z += v.z();
        w += v.w();
        return this;
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
     * @return this
     */
    public Vector4f add(float x, float y, float z, float w) {
        this.x += x;
        this.y += y;
        this.z += z;
        this.w += w;
        return this;
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
     * @return this
     */
    public Vector4f fma(Vector4fc a, Vector4fc b) {
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
    public Vector4f fma(float a, Vector4fc b) {
        x += a * b.x();
        y += a * b.y();
        z += a * b.z();
        w += a * b.w();
        return this;
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
     * @return this
     */
    public Vector4f mul(Vector4fc v) {
        x *= v.x();
        y *= v.y();
        z *= v.z();
        w *= v.w();
        return this;
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
     * @return this
     */
    public Vector4f div(Vector4fc v) {
        x /= v.x();
        y /= v.y();
        z /= v.z();
        w /= v.w();
        return this;
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
     * @return this
     */
    public Vector4f mul(Matrix4fc mat) {
        return mul(mat, this);
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4fc#mul(org.joml.Matrix4fc, org.joml.Vector4f)
     */
    public Vector4f mul(Matrix4fc mat, Vector4f dest) {
        dest.set(mat.m00() * x + mat.m10() * y + mat.m20() * z + mat.m30() * w,
                 mat.m01() * x + mat.m11() * y + mat.m21() * z + mat.m31() * w,
                 mat.m02() * x + mat.m12() * y + mat.m22() * z + mat.m32() * w,
                 mat.m03() * x + mat.m13() * y + mat.m23() * z + mat.m33() * w);
        return dest;
    }

    /**
     * Multiply the given matrix mat with this Vector4f and store the result in
     * <code>this</code>.
     * 
     * @param mat
     *          the matrix to multiply the vector with
     * @return this
     */
    public Vector4f mul(Matrix4x3f mat) {
        return mul(mat, this);
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4fc#mul(org.joml.Matrix4x3f, org.joml.Vector4f)
     */
    public Vector4f mul(Matrix4x3f mat, Vector4f dest) {
        dest.set(mat.m00() * x + mat.m10() * y + mat.m20() * z + mat.m30() * w,
                 mat.m01() * x + mat.m11() * y + mat.m21() * z + mat.m31() * w,
                 mat.m02() * x + mat.m12() * y + mat.m22() * z + mat.m32() * w,
                 w);
        return dest;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4fc#mulProject(org.joml.Matrix4fc, org.joml.Vector4f)
     */
    public Vector4f mulProject(Matrix4fc mat, Vector4f dest) {
        float invW = 1.0f / (mat.m03() * x + mat.m13() * y + mat.m23() * z + mat.m33() * w);
        dest.set((mat.m00() * x + mat.m10() * y + mat.m20() * z + mat.m30() * w) * invW,
                 (mat.m01() * x + mat.m11() * y + mat.m21() * z + mat.m31() * w) * invW,
                 (mat.m02() * x + mat.m12() * y + mat.m22() * z + mat.m32() * w) * invW,
                 1.0f);
        return dest;
    }

    /**
     * Multiply the given matrix <code>mat</code> with this Vector4f, perform perspective division.
     * 
     * @param mat
     *          the matrix to multiply this vector by
     * @return this
     */
    public Vector4f mulProject(Matrix4fc mat) {
        return mulProject(mat, this);
    }

    /**
     * Multiply all components of this {@link Vector4f} by the given scalar
     * value.
     * 
     * @param scalar
     *          the scalar to multiply by
     * @return this
     */
    public Vector4f mul(float scalar) {
        x *= scalar;
        y *= scalar;
        z *= scalar;
        w *= scalar;
        return this;
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
     * @return this
     */
    public Vector4f mul(float x, float y, float z, float w) {
        this.x *= x;
        this.y *= y;
        this.z *= z;
        this.w *= w;
        return this;
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
     * @return this
     */
    public Vector4f div(float scalar) {
        x /= scalar;
        y /= scalar;
        z /= scalar;
        w /= scalar;
        return this;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4fc#div(float, org.joml.Vector4f)
     */
    public Vector4f div(float scalar, Vector4f dest) {
        dest.x = x / scalar;
        dest.y = y / scalar;
        dest.z = z / scalar;
        dest.w = w / scalar;
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
     * @return this
     */
    public Vector4f div(float x, float y, float z, float w) {
        this.x /= x;
        this.y /= y;
        this.z /= z;
        this.w /= w;
        return this;
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
     * @return this
     */
    public Vector4f rotate(Quaternionfc quat) {
        return rotate(quat, this);
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4fc#rotate(org.joml.Quaternionf, org.joml.Vector4f)
     */
    public Vector4f rotate(Quaternionfc quat, Vector4f dest) {
        return quat.transform(this, dest);
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4fc#lengthSquared()
     */
    public float lengthSquared() {
        return x * x + y * y + z * z + w * w;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4fc#length()
     */
    public float length() {
        return (float) Math.sqrt(lengthSquared());
    }

    /**
     * Normalizes this vector.
     * 
     * @return this
     */
    public Vector4f normalize() {
        float invLength = 1.0f / length();
        x *= invLength;
        y *= invLength;
        z *= invLength;
        w *= invLength;
        return this;
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
     * Normalize this vector by computing only the norm of <tt>(x, y, z)</tt>.
     * 
     * @return this
     */
    public Vector4f normalize3() {
        float invLength = (float) (1.0 / Math.sqrt(x * x + y * y + z * z));
        x *= invLength;
        y *= invLength;
        z *= invLength;
        w *= invLength;
        return this;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4fc#distance(org.joml.Vector4fc)
     */
    public float distance(Vector4fc v) {
        float dx = v.x() - x;
        float dy = v.y() - y;
        float dz = v.z() - z;
        float dw = v.w() - w;
        return (float) Math.sqrt(dx * dx + dy * dy + dz * dz + dw * dw);
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4fc#distance(float, float, float, float)
     */
    public float distance(float x, float y, float z, float w) {
        float dx = this.x - x;
        float dy = this.y - y;
        float dz = this.z - z;
        float dw = this.w - w;
        return (float) Math.sqrt(dx * dx + dy * dy + dz * dz + dw * dw);
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
        double length1Sqared = x * x + y * y + z * z + w * w;
        double length2Sqared = v.x() * v.x() + v.y() * v.y() + v.z() * v.z() + v.w() * v.w();
        double dot = x * v.x() + y * v.y() + z * v.z() + w * v.w();
        return (float) (dot / (Math.sqrt(length1Sqared * length2Sqared)));
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
     * @return this
     */
    public Vector4f zero() {
        MemUtil.INSTANCE.zero(this);
        return this;
    }

    /**
     * Negate this vector.
     * 
     * @return this
     */
    public Vector4f negate() {
        x = -x;
        y = -y;
        z = -z;
        w = -w;
        return this;
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
     * This method creates a new {@link DecimalFormat} on every invocation with the format string "<tt> 0.000E0;-</tt>".
     * 
     * @return the string representation
     */
    public String toString() {
        DecimalFormat formatter = new DecimalFormat(" 0.000E0;-"); //$NON-NLS-1$
        return toString(formatter).replaceAll("E(\\d+)", "E+$1"); //$NON-NLS-1$ //$NON-NLS-2$
    }

    /**
     * Return a string representation of this vector by formatting the vector components with the given {@link NumberFormat}.
     * 
     * @param formatter
     *          the {@link NumberFormat} used to format the vector components with
     * @return the string representation
     */
    public String toString(NumberFormat formatter) {
        return "(" + formatter.format(x) + " " + formatter.format(y) + " " + formatter.format(z) + " " + formatter.format(w) + ")"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
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
     * Set the components of this vector to be the component-wise minimum of
     * this and the other vector.
     *
     * @param v
     *          the other vector
     * @return this
     */
    public Vector4f min(Vector4fc v) {
        this.x = x < v.x() ? x : v.x();
        this.y = y < v.y() ? y : v.y();
        this.z = z < v.z() ? z : v.z();
        this.w = w < v.w() ? w : v.w();
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
    public Vector4f max(Vector4fc v) {
        this.x = x > v.x() ? x : v.x();
        this.y = y > v.y() ? y : v.y();
        this.z = z > v.z() ? z : v.z();
        this.w = w > v.w() ? w : v.w();
        return this;
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
     * If <code>t</code> is <tt>0.0</tt> then the result is <code>this</code>. If the interpolation factor is <code>1.0</code>
     * then the result is <code>other</code>.
     * 
     * @param other
     *          the other vector
     * @param t
     *          the interpolation factor between 0.0 and 1.0
     * @return this
     */
    public Vector4f lerp(Vector4fc other, float t) {
        return lerp(other, t, this);
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

    /**
     * Create a new immutable view of this {@link Vector4f}.
     * <p>
     * The observable state of the returned object is the same as that of <code>this</code>, but casting
     * the returned object to Vector4f will not be possible.
     * <p>
     * This method allocates a new instance of a class implementing Vector4fc on every call.
     * 
     * @return the immutable instance
     */
    public Vector4fc toImmutable() {
        if (Options.NO_PROXY)
            return this;
        return new Proxy(this);
    }

}
