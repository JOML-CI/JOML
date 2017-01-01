/*
 * (C) Copyright 2015-2017 Richard Greenlees

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
 * Contains the definition of a Vector comprising 3 floats and associated
 * transformations.
 *
 * @author Richard Greenlees
 * @author Kai Burjack
 */
public class Vector3f implements Externalizable, Vector3fc {

    private final class Proxy implements Vector3fc {
        private final Vector3fc delegate;

        Proxy(Vector3fc delegate) {
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

        public Vector3f sub(Vector3fc v, Vector3f dest) {
            return delegate.sub(v, dest);
        }

        public Vector3f sub(float x, float y, float z, Vector3f dest) {
            return delegate.sub(x, y, z, dest);
        }

        public Vector3f add(Vector3fc v, Vector3f dest) {
            return delegate.add(v, dest);
        }

        public Vector3f add(float x, float y, float z, Vector3f dest) {
            return delegate.add(x, y, z, dest);
        }

        public Vector3f fma(Vector3fc a, Vector3fc b, Vector3f dest) {
            return delegate.fma(a, b, dest);
        }

        public Vector3f fma(float a, Vector3fc b, Vector3f dest) {
            return delegate.fma(a, b, dest);
        }

        public Vector3f mul(Vector3fc v, Vector3f dest) {
            return delegate.mul(v, dest);
        }

        public Vector3f div(Vector3fc v, Vector3f dest) {
            return delegate.div(v, dest);
        }

        public Vector3f mulProject(Matrix4fc mat, Vector3f dest) {
            return delegate.mulProject(mat, dest);
        }

        public Vector3f mul(Matrix3fc mat, Vector3f dest) {
            return delegate.mul(mat, dest);
        }

        public Vector3f mulTranspose(Matrix3fc mat, Vector3f dest) {
            return delegate.mulTranspose(mat, dest);
        }

        public Vector3f mulPosition(Matrix4fc mat, Vector3f dest) {
            return delegate.mulPosition(mat, dest);
        }

        public Vector3f mulPosition(Matrix4x3fc mat, Vector3f dest) {
            return delegate.mulPosition(mat, dest);
        }

        public Vector3f mulTransposePosition(Matrix4fc mat, Vector3f dest) {
            return delegate.mulTransposePosition(mat, dest);
        }

        public float mulPositionW(Matrix4fc mat, Vector3f dest) {
            return delegate.mulPositionW(mat, dest);
        }

        public Vector3f mulDirection(Matrix4fc mat, Vector3f dest) {
            return delegate.mulDirection(mat, dest);
        }

        public Vector3f mulDirection(Matrix4x3fc mat, Vector3f dest) {
            return delegate.mulDirection(mat, dest);
        }

        public Vector3f mulTransposeDirection(Matrix4fc mat, Vector3f dest) {
            return delegate.mulTransposeDirection(mat, dest);
        }

        public Vector3f mul(float scalar, Vector3f dest) {
            return delegate.mul(scalar, dest);
        }

        public Vector3f mul(float x, float y, float z, Vector3f dest) {
            return delegate.mul(x, y, z, dest);
        }

        public Vector3f div(float scalar, Vector3f dest) {
            return delegate.div(scalar, dest);
        }

        public Vector3f div(float x, float y, float z, Vector3f dest) {
            return delegate.div(x, y, z, dest);
        }

        public Vector3f rotate(Quaternionfc quat, Vector3f dest) {
            return delegate.rotate(quat, dest);
        }

        public Quaternionf rotationTo(Vector3fc toDir, Quaternionf dest) {
            return delegate.rotationTo(toDir, dest);
        }

        public Quaternionf rotationTo(float toDirX, float toDirY, float toDirZ, Quaternionf dest) {
            return delegate.rotationTo(toDirX, toDirY, toDirZ, dest);
        }

        public float lengthSquared() {
            return delegate.lengthSquared();
        }

        public float length() {
            return delegate.length();
        }

        public Vector3f normalize(Vector3f dest) {
            return delegate.normalize(dest);
        }

        public Vector3f cross(Vector3fc v, Vector3f dest) {
            return delegate.cross(v, dest);
        }

        public Vector3f cross(float x, float y, float z, Vector3f dest) {
            return delegate.cross(x, y, z, dest);
        }

        public float distance(Vector3fc v) {
            return delegate.distance(v);
        }

        public float distance(float x, float y, float z) {
            return delegate.distance(x, y, z);
        }

        public float distanceSquared(Vector3fc v) {
            return delegate.distanceSquared(v);
        }

        public float distanceSquared(float x, float y, float z) {
            return delegate.distanceSquared(x, y, z);
        }

        public float dot(Vector3fc v) {
            return delegate.dot(v);
        }

        public float dot(float x, float y, float z) {
            return delegate.dot(x, y, z);
        }

        public float angleCos(Vector3fc v) {
            return delegate.angleCos(v);
        }

        public float angle(Vector3fc v) {
            return delegate.angle(v);
        }

        public Vector3f negate(Vector3f dest) {
            return delegate.negate(dest);
        }

        public Vector3f reflect(Vector3fc normal, Vector3f dest) {
            return delegate.reflect(normal, dest);
        }

        public Vector3f reflect(float x, float y, float z, Vector3f dest) {
            return delegate.reflect(x, y, z, dest);
        }

        public Vector3f half(Vector3fc other, Vector3f dest) {
            return delegate.half(other, dest);
        }

        public Vector3f half(float x, float y, float z, Vector3f dest) {
            return delegate.half(x, y, z, dest);
        }

        public Vector3f smoothStep(Vector3fc v, float t, Vector3f dest) {
            return delegate.smoothStep(v, t, dest);
        }

        public Vector3f hermite(Vector3fc t0, Vector3fc v1, Vector3fc t1, float t, Vector3f dest) {
            return delegate.hermite(t0, v1, t1, t, dest);
        }

        public Vector3f lerp(Vector3fc other, float t, Vector3f dest) {
            return delegate.lerp(other, t, dest);
        }

        public float get(int component) throws IllegalArgumentException {
            return delegate.get(component);
        }

        public int maxComponent() {
            return delegate.maxComponent();
        }

        public int minComponent() {
            return delegate.minComponent();
        }

        public Vector3f orthogonalize(Vector3fc v, Vector3f dest) {
            return delegate.orthogonalize(v, dest);
        }

        public Vector3f orthogonalizeUnit(Vector3fc v, Vector3f dest) {
            return delegate.orthogonalizeUnit(v, dest);
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
     * Create a new {@link Vector3f} of <tt>(0, 0, 0)</tt>.
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
        this.x = v.x();
        this.y = v.y();
        this.z = v.z();
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
        this.x = v.x();
        this.y = v.y();
        this.z = z;
    }

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
     * @param buffer values will be read in <tt>x, y, z</tt> order
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
     * @param buffer values will be read in <tt>x, y, z</tt> order
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
     * @param buffer values will be read in <tt>x, y, z</tt> order
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
     * @param buffer values will be read in <tt>x, y, z</tt> order
     */
    public Vector3f(int index, FloatBuffer buffer) {
        MemUtil.INSTANCE.get(this, index, buffer);
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
        x = v.x();
        y = v.y();
        z = v.z();
        return this;
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
        x = (float) v.x();
        y = (float) v.y();
        z = (float) v.z();
        return this;
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
        this.x = v.x();
        this.y = v.y();
        this.z = z;
        return this;
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
     *          values will be read in <tt>x, y, z</tt> order
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
     *          values will be read in <tt>x, y, z</tt> order
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
     *          values will be read in <tt>x, y, z</tt> order
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
     *          values will be read in <tt>x, y, z</tt> order
     * @return this
     */
    public Vector3f set(int index, FloatBuffer buffer) {
        MemUtil.INSTANCE.get(this, index, buffer);
        return this;
    }

    /**
     * Set the value of the specified component of this vector.
     *
     * @param component
     *          the component whose value to set, within <tt>[0..2]</tt>
     * @param value
     *          the value to set
     * @return this
     * @throws IllegalArgumentException if <code>component</code> is not within <tt>[0..2]</tt>
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

    /**
     * Subtract the supplied vector from this one and store the result in <code>this</code>.
     * 
     * @param v
     *          the vector to subtract
     * @return this
     */
    public Vector3f sub(Vector3fc v) {
        x -= v.x();
        y -= v.y();
        z -= v.z();
        return this;
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
     * @return this
     */
    public Vector3f sub(float x, float y, float z) {
        this.x -= x;
        this.y -= y;
        this.z -= z;
        return this;
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
     * @return this
     */
    public Vector3f add(Vector3fc v) {
        x += v.x();
        y += v.y();
        z += v.z();
        return this;
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
     * @return this
     */
    public Vector3f add(float x, float y, float z) {
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
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
     * @return this
     */
    public Vector3f fma(Vector3fc a, Vector3fc b) {
        x += a.x() * b.x();
        y += a.y() * b.y();
        z += a.z() * b.z();
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
    public Vector3f fma(float a, Vector3fc b) {
        x += a * b.x();
        y += a * b.y();
        z += a * b.z();
        return this;
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
     * @return this
     */
    public Vector3f mul(Vector3fc v) {
        x *= v.x();
        y *= v.y();
        z *= v.z();
        return this;
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
     * @return this
     */
    public Vector3f div(Vector3fc v) {
        x /= v.x();
        y /= v.y();
        z /= v.z();
        return this;
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
        dest.set((mat.m00() * x + mat.m10() * y + mat.m20() * z + mat.m30()) * invW,
                 (mat.m01() * x + mat.m11() * y + mat.m21() * z + mat.m31()) * invW,
                 (mat.m02() * x + mat.m12() * y + mat.m22() * z + mat.m32()) * invW);
        return dest;
    }

    /**
     * Multiply the given matrix <code>mat</code> with this Vector3f, perform perspective division.
     * <p>
     * This method uses <tt>w=1.0</tt> as the fourth vector component.
     * 
     * @param mat
     *          the matrix to multiply this vector by
     * @return this
     */
    public Vector3f mulProject(Matrix4fc mat) {
        return mulProject(mat, this);
    }

    /**
     * Multiply the given matrix with this Vector3f and store the result in <code>this</code>.
     * 
     * @param mat
     *          the matrix
     * @return this
     */
    public Vector3f mul(Matrix3fc mat) {
        return mul(mat, this);
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3fc#mul(org.joml.Matrix3fc, org.joml.Vector3f)
     */
    public Vector3f mul(Matrix3fc mat, Vector3f dest) {
        dest.set(mat.m00() * x + mat.m10() * y + mat.m20() * z,
                 mat.m01() * x + mat.m11() * y + mat.m21() * z,
                 mat.m02() * x + mat.m12() * y + mat.m22() * z);
        return dest;
    }

    /**
     * Multiply the transpose of the given matrix with this Vector3f store the result in <code>this</code>.
     * 
     * @param mat
     *          the matrix
     * @return this
     */
    public Vector3f mulTranspose(Matrix3fc mat) {
        return mul(mat, this);
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3fc#mulTranspose(org.joml.Matrix3fc, org.joml.Vector3f)
     */
    public Vector3f mulTranspose(Matrix3fc mat, Vector3f dest) {
        dest.set(mat.m00() * x + mat.m01() * y + mat.m02() * z,
                 mat.m10() * x + mat.m11() * y + mat.m12() * z,
                 mat.m20() * x + mat.m21() * y + mat.m22() * z);
        return dest;
    }

    /**
     * Multiply the given 4x4 matrix <code>mat</code> with <code>this</code>.
     * <p>
     * This method assumes the <tt>w</tt> component of <code>this</code> to be <tt>1.0</tt>.
     * 
     * @param mat
     *          the matrix to multiply this vector by
     * @return this
     */
    public Vector3f mulPosition(Matrix4fc mat) {
        return mulPosition(mat, this);
    }

    /**
     * Multiply the given 4x3 matrix <code>mat</code> with <code>this</code>.
     * <p>
     * This method assumes the <tt>w</tt> component of <code>this</code> to be <tt>1.0</tt>.
     * 
     * @param mat
     *          the matrix to multiply this vector by
     * @return this
     */
    public Vector3f mulPosition(Matrix4x3fc mat) {
        return mulPosition(mat, this);
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3fc#mulPosition(org.joml.Matrix4fc, org.joml.Vector3f)
     */
    public Vector3f mulPosition(Matrix4fc mat, Vector3f dest) {
        dest.set(mat.m00() * x + mat.m10() * y + mat.m20() * z + mat.m30(),
                 mat.m01() * x + mat.m11() * y + mat.m21() * z + mat.m31(),
                 mat.m02() * x + mat.m12() * y + mat.m22() * z + mat.m32());
        return dest;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3fc#mulPosition(org.joml.Matrix4x3fc, org.joml.Vector3f)
     */
    public Vector3f mulPosition(Matrix4x3fc mat, Vector3f dest) {
        dest.set(mat.m00() * x + mat.m10() * y + mat.m20() * z + mat.m30(),
                 mat.m01() * x + mat.m11() * y + mat.m21() * z + mat.m31(),
                 mat.m02() * x + mat.m12() * y + mat.m22() * z + mat.m32());
        return dest;
    }

    /**
     * Multiply the transpose of the given 4x4 matrix <code>mat</code> with <code>this</code>.
     * <p>
     * This method assumes the <tt>w</tt> component of <code>this</code> to be <tt>1.0</tt>.
     * 
     * @param mat
     *          the matrix whose transpose to multiply this vector by
     * @return this
     */
    public Vector3f mulTransposePosition(Matrix4fc mat) {
        return mulTransposePosition(mat, this);
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3fc#mulTransposePosition(org.joml.Matrix4fc, org.joml.Vector3f)
     */
    public Vector3f mulTransposePosition(Matrix4fc mat, Vector3f dest) {
        dest.set(mat.m00() * x + mat.m01() * y + mat.m02() * z + mat.m03(),
                 mat.m10() * x + mat.m11() * y + mat.m12() * z + mat.m13(),
                 mat.m20() * x + mat.m21() * y + mat.m22() * z + mat.m23());
        return dest;
    }

    /**
     * Multiply the given 4x4 matrix <code>mat</code> with <code>this</code> and return the <i>w</i> component
     * of the resulting 4D vector.
     * <p>
     * This method assumes the <tt>w</tt> component of <code>this</code> to be <tt>1.0</tt>.
     * 
     * @param mat
     *          the matrix to multiply this vector by
     * @return the <i>w</i> component of the resulting 4D vector after multiplication
     */
    public float mulPositionW(Matrix4fc mat) {
        return mulPositionW(mat, this);
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3fc#mulPositionW(org.joml.Matrix4fc, org.joml.Vector3f)
     */
    public float mulPositionW(Matrix4fc mat, Vector3f dest) {
        float w = mat.m03() * x + mat.m13() * y + mat.m23() * z + mat.m33();
        dest.set(mat.m00() * x + mat.m10() * y + mat.m20() * z + mat.m30(),
                 mat.m01() * x + mat.m11() * y + mat.m21() * z + mat.m31(),
                 mat.m02() * x + mat.m12() * y + mat.m22() * z + mat.m32());
        return w;
    }

    /**
     * Multiply the given 4x4 matrix <code>mat</code> with <code>this</code>.
     * <p>
     * This method assumes the <tt>w</tt> component of <code>this</code> to be <tt>0.0</tt>.
     * 
     * @param mat
     *          the matrix to multiply this vector by
     * @return this
     */
    public Vector3f mulDirection(Matrix4fc mat) {
        return mulDirection(mat, this);
    }

    /**
     * Multiply the given 4x3 matrix <code>mat</code> with <code>this</code>.
     * <p>
     * This method assumes the <tt>w</tt> component of <code>this</code> to be <tt>0.0</tt>.
     * 
     * @param mat
     *          the matrix to multiply this vector by
     * @return this
     */
    public Vector3f mulDirection(Matrix4x3fc mat) {
        return mulDirection(mat, this);
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3fc#mulDirection(org.joml.Matrix4fc, org.joml.Vector3f)
     */
    public Vector3f mulDirection(Matrix4fc mat, Vector3f dest) {
        dest.set(mat.m00() * x + mat.m10() * y + mat.m20() * z,
                 mat.m01() * x + mat.m11() * y + mat.m21() * z,
                 mat.m02() * x + mat.m12() * y + mat.m22() * z);
        return dest;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3fc#mulDirection(org.joml.Matrix4x3fc, org.joml.Vector3f)
     */
    public Vector3f mulDirection(Matrix4x3fc mat, Vector3f dest) {
        dest.set(mat.m00() * x + mat.m10() * y + mat.m20() * z,
                 mat.m01() * x + mat.m11() * y + mat.m21() * z,
                 mat.m02() * x + mat.m12() * y + mat.m22() * z);
        return dest;
    }

    /**
     * Multiply the transpose of the given 4x4 matrix <code>mat</code> with <code>this</code>.
     * <p>
     * This method assumes the <tt>w</tt> component of <code>this</code> to be <tt>0.0</tt>.
     * 
     * @param mat
     *          the matrix whose transpose to multiply this vector by
     * @return this
     */
    public Vector3f mulTransposeDirection(Matrix4fc mat) {
        return mulTransposeDirection(mat, this);
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3fc#mulTransposeDirection(org.joml.Matrix4fc, org.joml.Vector3f)
     */
    public Vector3f mulTransposeDirection(Matrix4fc mat, Vector3f dest) {
        dest.set(mat.m00() * x + mat.m01() * y + mat.m02() * z,
                 mat.m10() * x + mat.m11() * y + mat.m12() * z,
                 mat.m20() * x + mat.m21() * y + mat.m22() * z);
        return dest;
    }

    /**
     * Multiply all components of this {@link Vector3f} by the given scalar
     * value.
     * 
     * @param scalar
     *          the scalar to multiply this vector by
     * @return this
     */
    public Vector3f mul(float scalar) {
        x *= scalar;
        y *= scalar;
        z *= scalar;
        return this;
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
     * @return this
     */
    public Vector3f mul(float x, float y, float z) {
        this.x *= x;
        this.y *= y;
        this.z *= z;
        return this;
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
     * @return this
     */
    public Vector3f div(float scalar) {
        x /= scalar;
        y /= scalar;
        z /= scalar;
        return this;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3fc#div(float, org.joml.Vector3f)
     */
    public Vector3f div(float scalar, Vector3f dest) {
        dest.x = x / scalar;
        dest.y = y / scalar;
        dest.z = z / scalar;
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
     * @return this
     */
    public Vector3f div(float x, float y, float z) {
        this.x /= x;
        this.y /= y;
        this.z /= z;
        return this;
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
     * @return this
     */
    public Vector3f rotate(Quaternionfc quat) {
        quat.transform(this, this);
        return this;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3fc#rotate(org.joml.Quaternionfc, org.joml.Vector3f)
     */
    public Vector3f rotate(Quaternionfc quat, Vector3f dest) {
        quat.transform(this, dest);
        return dest;
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

    /* (non-Javadoc)
     * @see org.joml.Vector3fc#lengthSquared()
     */
    public float lengthSquared() {
        return x * x + y * y + z * z;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3fc#length()
     */
    public float length() {
        return (float) Math.sqrt(lengthSquared());
    }

    /**
     * Normalize this vector.
     * 
     * @return this
     */
    public Vector3f normalize() {
        float invLength = 1.0f / length();
        x *= invLength;
        y *= invLength;
        z *= invLength;
        return this;
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
     * Set this vector to be the cross product of itself and <code>v</code>.
     * 
     * @param v
     *          the other vector
     * @return this
     */
    public Vector3f cross(Vector3fc v) {
        return set(y * v.z() - z * v.y(),
                   z * v.x() - x * v.z(),
                   x * v.y() - y * v.x());
    }

    /**
     * Set this vector to be the cross product of itself and <tt>(x, y, z)</tt>.
     * 
     * @param x
     *          the x component of the other vector
     * @param y
     *          the y component of the other vector
     * @param z
     *          the z component of the other vector
     * @return this
     */
    public Vector3f cross(float x, float y, float z) {
        return set(this.y * z - this.z * y,
                   this.z * x - this.x * z,
                   this.x * y - this.y * x);
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3fc#cross(org.joml.Vector3fc, org.joml.Vector3f)
     */
    public Vector3f cross(Vector3fc v, Vector3f dest) {
        return dest.set(y * v.z() - z * v.y(),
                        z * v.x() - x * v.z(),
                        x * v.y() - y * v.x());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3fc#cross(float, float, float, org.joml.Vector3f)
     */
    public Vector3f cross(float x, float y, float z, Vector3f dest) {
        return dest.set(this.y * z - this.z * y,
                        this.z * x - this.x * z,
                        this.x * y - this.y * x);
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3fc#distance(org.joml.Vector3fc)
     */
    public float distance(Vector3fc v) {
        float dx = v.x() - x;
        float dy = v.y() - y;
        float dz = v.z() - z;
        return (float) Math.sqrt(dx * dx + dy * dy + dz * dz);
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
        float dx = v.x() - x;
        float dy = v.y() - y;
        float dz = v.z() - z;
        return dx * dx + dy * dy + dz * dz;
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

    /* (non-Javadoc)
     * @see org.joml.Vector3fc#dot(org.joml.Vector3fc)
     */
    public float dot(Vector3fc v) {
        return x * v.x() + y * v.y() + z * v.z();
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
        double length1Sqared = x * x + y * y + z * z;
        double length2Sqared = v.x() * v.x() + v.y() * v.y() + v.z() * v.z();
        double dot = x * v.x() + y * v.y() + z * v.z();
        return (float) (dot / (Math.sqrt(length1Sqared * length2Sqared)));
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
     * @return this
     */
    public Vector3f min(Vector3fc v) {
        this.x = x < v.x() ? x : v.x();
        this.y = y < v.y() ? y : v.y();
        this.z = z < v.z() ? z : v.z();
        return this;
    }

    /**
     * Set the components of this vector to be the component-wise maximum of this and the other vector.
     *
     * @param v
     *          the other vector
     * @return this
     */
    public Vector3f max(Vector3fc v) {
        this.x = x > v.x() ? x : v.x();
        this.y = y > v.y() ? y : v.y();
        this.z = z > v.z() ? z : v.z();
        return this;
    }

    /**
     * Set all components to zero.
     * 
     * @return this
     */
    public Vector3f zero() {
        x = 0.0f;
        y = 0.0f;
        z = 0.0f;
        return this;
    }

    /**
     * Return a string representation of this vector.
     * <p>
     * This method creates a new {@link DecimalFormat} on every invocation with the format string "<tt>0.000E0;-</tt>".
     * 
     * @return the string representation
     */
    public String toString() {
        DecimalFormat formatter = new DecimalFormat(" 0.000E0;-");
        String str = toString(formatter);
        StringBuffer res = new StringBuffer();
        int eIndex = Integer.MIN_VALUE;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c == 'E') {
                eIndex = i;
            } else if (c == ' ' && eIndex == i - 1) {
                // workaround Java 1.4 DecimalFormat bug
                res.append('+');
                continue;
            } else if (Character.isDigit(c) && eIndex == i - 1) {
                res.append('+');
            }
            res.append(c);
        }
        return res.toString();
    }

    /**
     * Return a string representation of this quaternion by formatting the vector components with the given {@link NumberFormat}.
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
     * @return this
     */
    public Vector3f negate() {
        x = -x;
        y = -y;
        z = -z;
        return this;
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

    /**
     * Reflect this vector about the given <code>normal</code> vector.
     * 
     * @param normal
     *          the vector to reflect about
     * @return this
     */
    public Vector3f reflect(Vector3fc normal) {
        float dot = this.dot(normal);
        x = x - (dot + dot) * normal.x();
        y = y - (dot + dot) * normal.y();
        z = z - (dot + dot) * normal.z();
        return this;
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
     * @return this
     */
    public Vector3f reflect(float x, float y, float z) {
        float dot = this.dot(x, y, z);
        this.x = this.x - (dot + dot) * x;
        this.y = this.y - (dot + dot) * y;
        this.z = this.z - (dot + dot) * z;
        return this;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3fc#reflect(org.joml.Vector3fc, org.joml.Vector3f)
     */
    public Vector3f reflect(Vector3fc normal, Vector3f dest) {
        float dot = this.dot(normal);
        dest.x = x - (dot + dot) * normal.x();
        dest.y = y - (dot + dot) * normal.y();
        dest.z = z - (dot + dot) * normal.z();
        return dest;
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
     * @return this
     */
    public Vector3f half(Vector3fc other) {
        return this.add(other).normalize();
    }

    /**
     * Compute the half vector between this and the vector <tt>(x, y, z)</tt>.
     * 
     * @param x
     *          the x component of the other vector
     * @param y
     *          the y component of the other vector
     * @param z
     *          the z component of the other vector
     * @return this
     */
    public Vector3f half(float x, float y, float z) {
        return this.add(x, y, z).normalize();
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3fc#half(org.joml.Vector3fc, org.joml.Vector3f)
     */
    public Vector3f half(Vector3fc other, Vector3f dest) {
        return dest.set(this).add(other).normalize();
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
     * If <code>t</code> is <tt>0.0</tt> then the result is <code>this</code>. If the interpolation factor is <code>1.0</code>
     * then the result is <code>other</code>.
     * 
     * @param other
     *          the other vector
     * @param t
     *          the interpolation factor between 0.0 and 1.0
     * @return this
     */
    public Vector3f lerp(Vector3fc other, float t) {
        return lerp(other, t, this);
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
        float invLenV = 1.0f / (float) Math.sqrt(v.x() * v.x() + v.y() * v.y() + v.z() * v.z());
        float vx = v.x() * invLenV;
        float vy = v.y() * invLenV;
        float vz = v.z() * invLenV;
        float dot = (vx * x + vy * y + vz * z);
        float rx = x - dot * vx;
        float ry = y - dot * vy;
        float rz = z - dot * vz;
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
     * @return this
     */
    public Vector3f orthogonalize(Vector3fc v) {
        return orthogonalize(v, this);
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3fc#orthogonalizeUnit(org.joml.Vector3fc, org.joml.Vector3f)
     */
    public Vector3f orthogonalizeUnit(Vector3fc v, Vector3f dest) {
        float vx = v.x();
        float vy = v.y();
        float vz = v.z();
        float dot = (vx * x + vy * y + vz * z);
        float rx = x - dot * vx;
        float ry = y - dot * vy;
        float rz = z - dot * vz;
        float invLen = 1.0f / (float) Math.sqrt(rx * rx + ry * ry + rz * rz);
        dest.x = rx * invLen;
        dest.y = ry * invLen;
        dest.z = rz * invLen;
        return dest;
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
     * @return this
     */
    public Vector3f orthogonalizeUnit(Vector3fc v) {
        return orthogonalizeUnit(v, this);
    }

    /**
     * Create a new immutable view of this {@link Vector3f}.
     * <p>
     * The observable state of the returned object is the same as that of <code>this</code>, but casting
     * the returned object to Vector3f will not be possible.
     * <p>
     * This method allocates a new instance of a class implementing Vector3fc on every call.
     * 
     * @return the immutable instance
     */
    public Vector3fc toImmutable() {
        if (!Options.DEBUG)
            return this;
        return new Proxy(this);
    }

}
