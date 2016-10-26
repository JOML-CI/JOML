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
import java.nio.DoubleBuffer;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Contains the definition of a Vector comprising 3 doubles and associated
 * transformations.
 *
 * @author Richard Greenlees
 * @author Kai Burjack
 */
public class Vector3d implements Externalizable, Vector3dc {

    private final class Proxy implements Vector3dc {
        private final Vector3dc delegate;

        Proxy(Vector3dc delegate) {
            this.delegate = delegate;
        }

        public double x() {
            return delegate.x();
        }

        public double y() {
            return delegate.y();
        }

        public double z() {
            return delegate.z();
        }

        public ByteBuffer get(ByteBuffer buffer) {
            return delegate.get(buffer);
        }

        public ByteBuffer get(int index, ByteBuffer buffer) {
            return delegate.get(index, buffer);
        }

        public DoubleBuffer get(DoubleBuffer buffer) {
            return delegate.get(buffer);
        }

        public DoubleBuffer get(int index, DoubleBuffer buffer) {
            return delegate.get(index, buffer);
        }

        public Vector3d sub(Vector3dc v, Vector3d dest) {
            return delegate.sub(v, dest);
        }

        public Vector3d sub(Vector3fc v, Vector3d dest) {
            return delegate.sub(v, dest);
        }

        public Vector3d sub(double x, double y, double z, Vector3d dest) {
            return delegate.sub(x, y, z, dest);
        }

        public Vector3d add(Vector3dc v, Vector3d dest) {
            return delegate.add(v, dest);
        }

        public Vector3d add(Vector3fc v, Vector3d dest) {
            return delegate.add(v, dest);
        }

        public Vector3d add(double x, double y, double z, Vector3d dest) {
            return delegate.add(x, y, z, dest);
        }

        public Vector3d fma(Vector3dc a, Vector3dc b, Vector3d dest) {
            return delegate.fma(a, b, dest);
        }

        public Vector3d fma(double a, Vector3dc b, Vector3d dest) {
            return delegate.fma(a, b, dest);
        }

        public Vector3d fma(Vector3dc a, Vector3fc b, Vector3d dest) {
            return delegate.fma(a, b, dest);
        }

        public Vector3d fma(double a, Vector3fc b, Vector3d dest) {
            return delegate.fma(a, b, dest);
        }

        public Vector3d mul(Vector3fc v, Vector3d dest) {
            return delegate.mul(v, dest);
        }

        public Vector3d mul(Vector3dc v, Vector3d dest) {
            return delegate.mul(v, dest);
        }

        public Vector3d div(Vector3fc v, Vector3d dest) {
            return delegate.div(v, dest);
        }

        public Vector3d div(Vector3dc v, Vector3d dest) {
            return delegate.div(v, dest);
        }

        public Vector3d mulProject(Matrix4dc mat, Vector3d dest) {
            return delegate.mulProject(mat, dest);
        }

        public Vector3d mulProject(Matrix4fc mat, Vector3d dest) {
            return delegate.mulProject(mat, dest);
        }

        public Vector3d mul(Matrix3dc mat, Vector3d dest) {
            return delegate.mul(mat, dest);
        }

        public Vector3d mul(Matrix3fc mat, Vector3d dest) {
            return delegate.mul(mat, dest);
        }

        public Vector3d mulTranspose(Matrix3dc mat, Vector3d dest) {
            return delegate.mulTranspose(mat, dest);
        }

        public Vector3d mulTranspose(Matrix3fc mat, Vector3d dest) {
            return delegate.mulTranspose(mat, dest);
        }

        public Vector3d mulPosition(Matrix4dc mat, Vector3d dest) {
            return delegate.mulPosition(mat, dest);
        }

        public Vector3d mulPosition(Matrix4fc mat, Vector3d dest) {
            return delegate.mulPosition(mat, dest);
        }

        public Vector3d mulPosition(Matrix4x3dc mat, Vector3d dest) {
            return delegate.mulPosition(mat, dest);
        }

        public Vector3d mulTransposePosition(Matrix4dc mat, Vector3d dest) {
            return delegate.mulTransposePosition(mat, dest);
        }

        public Vector3d mulTransposePosition(Matrix4fc mat, Vector3d dest) {
            return delegate.mulTransposePosition(mat, dest);
        }

        public double mulPositionW(Matrix4fc mat, Vector3d dest) {
            return delegate.mulPositionW(mat, dest);
        }

        public double mulPositionW(Matrix4dc mat, Vector3d dest) {
            return delegate.mulPositionW(mat, dest);
        }

        public Vector3d mulDirection(Matrix4dc mat, Vector3d dest) {
            return delegate.mulDirection(mat, dest);
        }

        public Vector3d mulDirection(Matrix4fc mat, Vector3d dest) {
            return delegate.mulDirection(mat, dest);
        }

        public Vector3d mulDirection(Matrix4x3dc mat, Vector3d dest) {
            return delegate.mulDirection(mat, dest);
        }

        public Vector3d mulTransposeDirection(Matrix4dc mat, Vector3d dest) {
            return delegate.mulTransposeDirection(mat, dest);
        }

        public Vector3d mulTransposeDirection(Matrix4fc mat, Vector3d dest) {
            return delegate.mulTransposeDirection(mat, dest);
        }

        public Vector3d mul(double scalar, Vector3d dest) {
            return delegate.mul(scalar, dest);
        }

        public Vector3d mul(double x, double y, double z, Vector3d dest) {
            return delegate.mul(x, y, z, dest);
        }

        public Vector3d rotate(Quaterniondc quat, Vector3d dest) {
            return delegate.rotate(quat, dest);
        }

        public Vector3d div(double scalar, Vector3d dest) {
            return delegate.div(scalar, dest);
        }

        public Vector3d div(double x, double y, double z, Vector3d dest) {
            return delegate.div(x, y, z, dest);
        }

        public double lengthSquared() {
            return delegate.lengthSquared();
        }

        public double length() {
            return delegate.length();
        }

        public Vector3d normalize(Vector3d dest) {
            return delegate.normalize(dest);
        }

        public Vector3d cross(Vector3dc v, Vector3d dest) {
            return delegate.cross(v, dest);
        }

        public Vector3d cross(double x, double y, double z, Vector3d dest) {
            return delegate.cross(x, y, z, dest);
        }

        public double distance(Vector3dc v) {
            return delegate.distance(v);
        }

        public double distance(double x, double y, double z) {
            return delegate.distance(x, y, z);
        }

        public double distanceSquared(Vector3dc v) {
            return delegate.distanceSquared(v);
        }

        public double distanceSquared(double x, double y, double z) {
            return delegate.distanceSquared(x, y, z);
        }

        public double dot(Vector3dc v) {
            return delegate.dot(v);
        }

        public double dot(double x, double y, double z) {
            return delegate.dot(x, y, z);
        }

        public double angleCos(Vector3dc v) {
            return delegate.angleCos(v);
        }

        public double angle(Vector3dc v) {
            return delegate.angle(v);
        }

        public Vector3d negate(Vector3d dest) {
            return delegate.negate(dest);
        }

        public Vector3d reflect(Vector3dc normal, Vector3d dest) {
            return delegate.reflect(normal, dest);
        }

        public Vector3d reflect(double x, double y, double z, Vector3d dest) {
            return delegate.reflect(x, y, z, dest);
        }

        public Vector3d half(Vector3dc other, Vector3d dest) {
            return delegate.half(other, dest);
        }

        public Vector3d half(double x, double y, double z, Vector3d dest) {
            return delegate.half(x, y, z, dest);
        }

        public Vector3d smoothStep(Vector3dc v, double t, Vector3d dest) {
            return delegate.smoothStep(v, t, dest);
        }

        public Vector3d hermite(Vector3dc t0, Vector3dc v1, Vector3dc t1, double t, Vector3d dest) {
            return delegate.hermite(t0, v1, t1, t, dest);
        }

        public Vector3d lerp(Vector3dc other, double t, Vector3d dest) {
            return delegate.lerp(other, t, dest);
        }

        public double get(int component) throws IllegalArgumentException {
            return delegate.get(component);
        }

        public int maxComponent() {
            return delegate.maxComponent();
        }

        public int minComponent() {
            return delegate.minComponent();
        }

        public Vector3d orthogonalize(Vector3dc v, Vector3d dest) {
            return delegate.orthogonalize(v, dest);
        }

        public Vector3d orthogonalizeUnit(Vector3dc v, Vector3d dest) {
            return delegate.orthogonalizeUnit(v, dest);
        }
    }

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
        this.x = v.x();
        this.y = v.y();
        this.z = v.z();
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
        this.x = v.x();
        this.y = v.y();
        this.z = z;
    }

    /**
     * Create a new {@link Vector3d} whose values will be copied from the given vector.
     * 
     * @param v
     *          provides the initial values for the new vector
     */
    public Vector3d(Vector3dc v) {
        this.x = v.x();
        this.y = v.y();
        this.z = v.z();
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
        this.x = v.x();
        this.y = v.y();
        this.z = z;
    }

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
     * @param buffer values will be read in <tt>x, y, z</tt> order
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
     * @param buffer values will be read in <tt>x, y, z</tt> order
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
     * @param buffer values will be read in <tt>x, y, z</tt> order
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
     * @param buffer values will be read in <tt>x, y, z</tt> order
     */
    public Vector3d(int index, DoubleBuffer buffer) {
        MemUtil.INSTANCE.get(this, index, buffer);
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
        x = v.x();
        y = v.y();
        z = v.z();
        return this;
    }

    /**
     * Set the first two components from the given <code>v</code>
     * and the z component from the given <code>z</code>
     *
     * @param v
     *          the {@link Vector2d} to copy the values from
     * @param z
     *          the z component
     * @return this
     */
    public Vector3d set(Vector2dc v, double z) {
        this.x = v.x();
        this.y = v.y();
        this.z = z;
        return this;
    }

    /**
     * Set the x, y and z components to match the supplied vector.
     * 
     * @param v
     *          the vector to set this vector's components from
     * @return this
     */
    public Vector3d set(Vector3fc v) {
        x = v.x();
        y = v.y();
        z = v.z();
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
    public Vector3d set(Vector2fc v, double z) {
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
     *          values will be read in <tt>x, y, z</tt> order
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
     *          values will be read in <tt>x, y, z</tt> order
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
     *          values will be read in <tt>x, y, z</tt> order
     * @return this
     */
    public Vector3d set(int index, DoubleBuffer buffer) {
        MemUtil.INSTANCE.get(this, index, buffer);
        return this;
    }

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

    /**
     * Subtract the supplied vector from this one.
     * 
     * @param v
     *          the vector to subtract from this
     * @return this
     */
    public Vector3d sub(Vector3dc v) {
        x -= v.x();
        y -= v.y();
        z -= v.z();
        return this;
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
     * @return this
     */
    public Vector3d sub(Vector3fc v) {
        x -= v.x();
        y -= v.y();
        z -= v.z();
        return this;
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
     * Subtract <tt>(x, y, z)</tt> from this vector.
     * 
     * @param x
     *          the x component to subtract
     * @param y
     *          the y component to subtract
     * @param z
     *          the z component to subtract
     * @return this
     */
    public Vector3d sub(double x, double y, double z) {
        this.x -= x;
        this.y -= y;
        this.z -= z;
        return this;
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
     * @return this
     */
    public Vector3d add(Vector3dc v) {
        x += v.x();
        y += v.y();
        z += v.z();
        return this;
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
     * @return this
     */
    public Vector3d add(Vector3fc v) {
        x += v.x();
        y += v.y();
        z += v.z();
        return this;
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
     * @return this
     */
    public Vector3d add(double x, double y, double z) {
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
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
     * @return this
     */
    public Vector3d fma(Vector3dc a, Vector3dc b) {
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
    public Vector3d fma(double a, Vector3dc b) {
        x += a * b.x();
        y += a * b.y();
        z += a * b.z();
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
    public Vector3d fma(Vector3fc a, Vector3fc b) {
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
    public Vector3d fma(double a, Vector3fc b) {
        x += a * b.x();
        y += a * b.y();
        z += a * b.z();
        return this;
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
     * @return this
     */
    public Vector3d mul(Vector3dc v) {
        x *= v.x();
        y *= v.y();
        z *= v.z();
        return this;
    }

    /**
     * Multiply this Vector3d component-wise by another Vector3fc.
     * 
     * @param v
     *          the vector to multiply by
     * @return this
     */
    public Vector3d mul(Vector3fc v) {
        x *= v.x();
        y *= v.y();
        z *= v.z();
        return this;
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
     * @return this
     */
    public Vector3d div(Vector3d v) {
        x /= v.x();
        y /= v.y();
        z /= v.z();
        return this;
    }

    /**
     * Divide this Vector3d component-wise by another Vector3fc.
     * 
     * @param v
     *          the vector to divide by
     * @return this
     */
    public Vector3d div(Vector3fc v) {
        x /= v.x();
        y /= v.y();
        z /= v.z();
        return this;
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
        dest.set((mat.m00() * x + mat.m10() * y + mat.m20() * z + mat.m30()) * invW,
                 (mat.m01() * x + mat.m11() * y + mat.m21() * z + mat.m31()) * invW,
                 (mat.m02() * x + mat.m12() * y + mat.m22() * z + mat.m32()) * invW);
        return dest;
    }

    /**
     * Multiply the given matrix <code>mat</code> this Vector3d, perform perspective division.
     * <p>
     * This method uses <tt>w=1.0</tt> as the fourth vector component.
     * 
     * @param mat
     *          the matrix to multiply this vector by
     * @return this
     */
    public Vector3d mulProject(Matrix4dc mat) {
        return mulProject(mat, this);
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3dc#mulProject(org.joml.Matrix4fc, org.joml.Vector3d)
     */
    public Vector3d mulProject(Matrix4fc mat, Vector3d dest) {
        double invW = 1.0 / (mat.m03() * x + mat.m13() * y + mat.m23() * z + mat.m33());
        dest.set((mat.m00() * x + mat.m10() * y + mat.m20() * z + mat.m30()) * invW,
                 (mat.m01() * x + mat.m11() * y + mat.m21() * z + mat.m31()) * invW,
                 (mat.m02() * x + mat.m12() * y + mat.m22() * z + mat.m32()) * invW);
        return dest;
    }

    /**
     * Multiply the given matrix <code>mat</code> with this Vector3d, perform perspective division.
     * <p>
     * This method uses <tt>w=1.0</tt> as the fourth vector component.
     * 
     * @param mat
     *          the matrix to multiply this vector by
     * @return this
     */
    public Vector3d mulProject(Matrix4fc mat) {
        return mulProject(mat, this);
    }

    /**
     * Multiply the given matrix <code>mat</code> with this Vector3d.
     * 
     * @param mat
     *          the matrix to multiply this vector by
     * @return this
     */
    public Vector3d mul(Matrix3fc mat) {
        return mul(mat, this);
    }

    /**
     * Multiply the given matrix <code>mat</code> with this Vector3d.
     * 
     * @param mat
     *          the matrix to multiply this vector by
     * @return this
     */
    public Vector3d mul(Matrix3dc mat) {
        return mul(mat, this);
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3dc#mul(org.joml.Matrix3dc, org.joml.Vector3d)
     */
    public Vector3d mul(Matrix3dc mat, Vector3d dest) {
        dest.set(mat.m00() * x + mat.m10() * y + mat.m20() * z,
                 mat.m01() * x + mat.m11() * y + mat.m21() * z,
                 mat.m02() * x + mat.m12() * y + mat.m22() * z);
        return dest;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3dc#mul(org.joml.Matrix3fc, org.joml.Vector3d)
     */
    public Vector3d mul(Matrix3fc mat, Vector3d dest) {
        dest.set(mat.m00() * x + mat.m10() * y + mat.m20() * z,
                 mat.m01() * x + mat.m11() * y + mat.m21() * z,
                 mat.m02() * x + mat.m12() * y + mat.m22() * z);
        return dest;
    }

    /**
     * Multiply the transpose of the given matrix with this Vector3f and store the result in <code>this</code>.
     * 
     * @param mat
     *          the matrix
     * @return this
     */
    public Vector3d mulTranspose(Matrix3dc mat) {
        return mul(mat, this);
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3dc#mulTranspose(org.joml.Matrix3dc, org.joml.Vector3d)
     */
    public Vector3d mulTranspose(Matrix3dc mat, Vector3d dest) {
        dest.set(mat.m00() * x + mat.m01() * y + mat.m02() * z,
                 mat.m10() * x + mat.m11() * y + mat.m12() * z,
                 mat.m20() * x + mat.m21() * y + mat.m22() * z);
        return dest;
    }

    /**
     * Multiply the transpose of the given matrix with  this Vector3f and store the result in <code>this</code>.
     * 
     * @param mat
     *          the matrix
     * @return this
     */
    public Vector3d mulTranspose(Matrix3fc mat) {
        return mul(mat, this);
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3dc#mulTranspose(org.joml.Matrix3fc, org.joml.Vector3d)
     */
    public Vector3d mulTranspose(Matrix3fc mat, Vector3d dest) {
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
    public Vector3d mulPosition(Matrix4fc mat) {
        return mulPosition(mat, this);
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
    public Vector3d mulPosition(Matrix4dc mat) {
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
    public Vector3d mulPosition(Matrix4x3dc mat) {
        return mulPosition(mat, this);
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3dc#mulPosition(org.joml.Matrix4dc, org.joml.Vector3d)
     */
    public Vector3d mulPosition(Matrix4dc mat, Vector3d dest) {
        dest.set(mat.m00() * x + mat.m10() * y + mat.m20() * z + mat.m30(),
                 mat.m01() * x + mat.m11() * y + mat.m21() * z + mat.m31(),
                 mat.m02() * x + mat.m12() * y + mat.m22() * z + mat.m32());
        return dest;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3dc#mulPosition(org.joml.Matrix4fc, org.joml.Vector3d)
     */
    public Vector3d mulPosition(Matrix4fc mat, Vector3d dest) {
        dest.set(mat.m00() * x + mat.m10() * y + mat.m20() * z + mat.m30(),
                 mat.m01() * x + mat.m11() * y + mat.m21() * z + mat.m31(),
                 mat.m02() * x + mat.m12() * y + mat.m22() * z + mat.m32());
        return dest;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3dc#mulPosition(org.joml.Matrix4x3dc, org.joml.Vector3d)
     */
    public Vector3d mulPosition(Matrix4x3dc mat, Vector3d dest) {
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
    public Vector3d mulTransposePosition(Matrix4dc mat) {
        return mulTransposePosition(mat, this);
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3dc#mulTransposePosition(org.joml.Matrix4dc, org.joml.Vector3d)
     */
    public Vector3d mulTransposePosition(Matrix4dc mat, Vector3d dest) {
        dest.set(mat.m00() * x + mat.m01() * y + mat.m02() * z + mat.m03(),
                 mat.m10() * x + mat.m11() * y + mat.m12() * z + mat.m13(),
                 mat.m20() * x + mat.m21() * y + mat.m22() * z + mat.m23());
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
    public Vector3d mulTransposePosition(Matrix4fc mat) {
        return mulTransposePosition(mat, this);
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3dc#mulTransposePosition(org.joml.Matrix4fc, org.joml.Vector3d)
     */
    public Vector3d mulTransposePosition(Matrix4fc mat, Vector3d dest) {
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
    public double mulPositionW(Matrix4fc mat) {
        return mulPositionW(mat, this);
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3dc#mulPositionW(org.joml.Matrix4fc, org.joml.Vector3d)
     */
    public double mulPositionW(Matrix4fc mat, Vector3d dest) {
        double w = mat.m03() * x + mat.m13() * y + mat.m23() * z + mat.m33();
        dest.set(mat.m00() * x + mat.m10() * y + mat.m20() * z + mat.m30(),
                 mat.m01() * x + mat.m11() * y + mat.m21() * z + mat.m31(),
                 mat.m02() * x + mat.m12() * y + mat.m22() * z + mat.m32());
        return w;
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
    public double mulPositionW(Matrix4dc mat) {
        return mulPositionW(mat, this);
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3dc#mulPositionW(org.joml.Matrix4dc, org.joml.Vector3d)
     */
    public double mulPositionW(Matrix4dc mat, Vector3d dest) {
        double w = mat.m03() * x + mat.m13() * y + mat.m23() * z + mat.m33();
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
    public Vector3d mulDirection(Matrix4fc mat) {
        return mulDirection(mat, this);
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
    public Vector3d mulDirection(Matrix4dc mat) {
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
    public Vector3d mulDirection(Matrix4x3dc mat) {
        return mulDirection(mat, this);
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3dc#mulDirection(org.joml.Matrix4dc, org.joml.Vector3d)
     */
    public Vector3d mulDirection(Matrix4dc mat, Vector3d dest) {
        dest.set(mat.m00() * x + mat.m10() * y + mat.m20() * z,
                 mat.m01() * x + mat.m11() * y + mat.m21() * z,
                 mat.m02() * x + mat.m12() * y + mat.m22() * z);
        return dest;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3dc#mulDirection(org.joml.Matrix4fc, org.joml.Vector3d)
     */
    public Vector3d mulDirection(Matrix4fc mat, Vector3d dest) {
        dest.set(mat.m00() * x + mat.m10() * y + mat.m20() * z,
                 mat.m01() * x + mat.m11() * y + mat.m21() * z,
                 mat.m02() * x + mat.m12() * y + mat.m22() * z);
        return dest;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3dc#mulDirection(org.joml.Matrix4x3dc, org.joml.Vector3d)
     */
    public Vector3d mulDirection(Matrix4x3dc mat, Vector3d dest) {
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
    public Vector3d mulTransposeDirection(Matrix4dc mat) {
        return mulTransposeDirection(mat, this);
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3dc#mulTransposeDirection(org.joml.Matrix4dc, org.joml.Vector3d)
     */
    public Vector3d mulTransposeDirection(Matrix4dc mat, Vector3d dest) {
        dest.set(mat.m00() * x + mat.m01() * y + mat.m02() * z,
                 mat.m10() * x + mat.m11() * y + mat.m12() * z,
                 mat.m20() * x + mat.m21() * y + mat.m22() * z);
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
    public Vector3d mulTransposeDirection(Matrix4fc mat) {
        return mulTransposeDirection(mat, this);
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3dc#mulTransposeDirection(org.joml.Matrix4fc, org.joml.Vector3d)
     */
    public Vector3d mulTransposeDirection(Matrix4fc mat, Vector3d dest) {
        dest.set(mat.m00() * x + mat.m01() * y + mat.m02() * z,
                 mat.m10() * x + mat.m11() * y + mat.m12() * z,
                 mat.m20() * x + mat.m21() * y + mat.m22() * z);
        return dest;
    }

    /**
     * Multiply this Vector3d by the given scalar value.
     * 
     * @param scalar
     *          the scalar to multiply this vector by
     * @return this
     */
    public Vector3d mul(double scalar) {
        x *= scalar;
        y *= scalar;
        z *= scalar;
        return this;
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
    public Vector3d mul(double x, double y, double z) {
        this.x *= x;
        this.y *= y;
        this.z *= z;
        return this;
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
     * @return this
     */
    public Vector3d rotate(Quaterniondc quat) {
        quat.transform(this, this);
        return this;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3dc#rotate(org.joml.Quaterniondc, org.joml.Vector3d)
     */
    public Vector3d rotate(Quaterniondc quat, Vector3d dest) {
        quat.transform(this, dest);
        return dest;
    }

    /**
     * Compute the quaternion representing a rotation of <code>this</code> vector to point along <code>toDir</code>
     * and store the result in <code>dest</code>.
     * <p>
     * Because there can be multiple possible rotations, this method chooses the one with the shortest arc.
     * 
     * @see Quaterniond#rotationTo(Vector3dc, Vector3dc)
     * 
     * @param toDir
     *          the destination direction
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Quaterniond rotationTo(Vector3dc toDir, Quaterniond dest) {
        return dest.rotationTo(this, toDir);
    }

    /**
     * Compute the quaternion representing a rotation of <code>this</code> vector to point along <tt>(toDirX, toDirY, toDirZ)</tt>
     * and store the result in <code>dest</code>.
     * <p>
     * Because there can be multiple possible rotations, this method chooses the one with the shortest arc.
     * 
     * @see Quaterniond#rotationTo(double, double, double, double, double, double)
     * 
     * @param toDirX
     *          the x coordinate of the destination direction
     * @param toDirY
     *          the y coordinate of the destination direction
     * @param toDirZ
     *          the z coordinate of the destination direction
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Quaterniond rotationTo(double toDirX, double toDirY, double toDirZ, Quaterniond dest) {
        return dest.rotationTo(x, y, z, toDirX, toDirY, toDirZ);
    }

    /**
     * Divide this Vector3d by the given scalar value.
     * 
     * @param scalar
     *          the scalar to divide this vector by
     * @return this
     */
    public Vector3d div(double scalar) {
        x /= scalar;
        y /= scalar;
        z /= scalar;
        return this;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3dc#div(double, org.joml.Vector3d)
     */
    public Vector3d div(double scalar, Vector3d dest) {
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
    public Vector3d div(double x, double y, double z) {
        this.x /= x;
        this.y /= y;
        this.z /= z;
        return this;
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
        return x * x + y * y + z * z;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3dc#length()
     */
    public double length() {
        return Math.sqrt(lengthSquared());
    }

    /**
     * Normalize this vector.
     * 
     * @return this
     */
    public Vector3d normalize() {
        double invLength = 1.0 / length();
        x *= invLength;
        y *= invLength;
        z *= invLength;
        return this;
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
     * Set this vector to be the cross product of this and v2.
     * 
     * @param v
     *          the other vector
     * @return this
     */
    public Vector3d cross(Vector3dc v) {
        set(y * v.z() - z * v.y(),
            z * v.x() - x * v.z(),
            x * v.y() - y * v.x());
        return this;
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
    public Vector3d cross(double x, double y, double z) {
        return set(this.y * z - this.z * y,
                   this.z * x - this.x * z,
                   this.x * y - this.y * x);
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3dc#cross(org.joml.Vector3dc, org.joml.Vector3d)
     */
    public Vector3d cross(Vector3dc v, Vector3d dest) {
        dest.set(y * v.z() - z * v.y(),
                 z * v.x() - x * v.z(),
                 x * v.y() - y * v.x());
        return dest;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3dc#cross(double, double, double, org.joml.Vector3d)
     */
    public Vector3d cross(double x, double y, double z, Vector3d dest) {
        return dest.set(this.y * z - this.z * y,
                        this.z * x - this.x * z,
                        this.x * y - this.y * x);
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3dc#distance(org.joml.Vector3dc)
     */
    public double distance(Vector3dc v) {
        double dx = v.x() - x;
        double dy = v.y() - y;
        double dz = v.z() - z;
        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3dc#distance(double, double, double)
     */
    public double distance(double x, double y, double z) {
        double dx = this.x - x;
        double dy = this.y - y;
        double dz = this.z - z;
        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3dc#distanceSquared(org.joml.Vector3dc)
     */
    public double distanceSquared(Vector3dc v) {
        double dx = v.x() - x;
        double dy = v.y() - y;
        double dz = v.z() - z;
        return dx * dx + dy * dy + dz * dz;
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

    /* (non-Javadoc)
     * @see org.joml.Vector3dc#dot(org.joml.Vector3dc)
     */
    public double dot(Vector3dc v) {
        return x * v.x() + y * v.y() + z * v.z();
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
        double length1Sqared = x * x + y * y + z * z;
        double length2Sqared = v.x() * v.x() + v.y() * v.y() + v.z() * v.z();
        double dot = x * v.x() + y * v.y() + z * v.z();
        return dot / (Math.sqrt(length1Sqared * length2Sqared));
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
     * Set all components to zero.
     * 
     * @return this
     */
    public Vector3d zero() {
        x = 0.0;
        y = 0.0;
        z = 0.0;
        return this;
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
        return "(" + formatter.format(x) + " " + formatter.format(y) + " " + formatter.format(z) + ")"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
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
     * @return this
     */
    public Vector3d negate() {
        x = -x;
        y = -y;
        z = -z;
        return this;
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

    /**
     * Reflect this vector about the given normal vector.
     * 
     * @param normal
     *          the vector to reflect about
     * @return this
     */
    public Vector3d reflect(Vector3dc normal) {
        double dot = this.dot(normal);
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
    public Vector3d reflect(double x, double y, double z) {
        double dot = this.dot(x, y, z);
        this.x = this.x - (dot + dot) * x;
        this.y = this.y - (dot + dot) * y;
        this.z = this.z - (dot + dot) * z;
        return this;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3dc#reflect(org.joml.Vector3dc, org.joml.Vector3d)
     */
    public Vector3d reflect(Vector3dc normal, Vector3d dest) {
        double dot = this.dot(normal);
        dest.x = x - (dot + dot) * normal.x();
        dest.y = y - (dot + dot) * normal.y();
        dest.z = z - (dot + dot) * normal.z();
        return dest;
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
     * @return this
     */
    public Vector3d half(Vector3dc other) {
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
    public Vector3d half(double x, double y, double z) {
        return this.add(x, y, z).normalize();
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3dc#half(org.joml.Vector3dc, org.joml.Vector3d)
     */
    public Vector3d half(Vector3dc other, Vector3d dest) {
        return dest.set(this).add(other).normalize();
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
     * If <code>t</code> is <tt>0.0</tt> then the result is <code>this</code>. If the interpolation factor is <code>1.0</code>
     * then the result is <code>other</code>.
     * 
     * @param other
     *          the other vector
     * @param t
     *          the interpolation factor between 0.0 and 1.0
     * @return this
     */
    public Vector3d lerp(Vector3dc other, double t) {
        return lerp(other, t, this);
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
    public Vector3d set(int component, double value) throws IllegalArgumentException {
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
        double invLenV = 1.0 / Math.sqrt(v.x() * v.x() + v.y() * v.y() + v.z() * v.z());
        double vx = v.x() * invLenV;
        double vy = v.y() * invLenV;
        double vz = v.z() * invLenV;
        double dot = (vx * x + vy * y + vz * z);
        double rx = x - dot * vx;
        double ry = y - dot * vy;
        double rz = z - dot * vz;
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
     * @return this
     */
    public Vector3d orthogonalize(Vector3dc v) {
        return orthogonalize(v, this);
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3dc#orthogonalizeUnit(org.joml.Vector3dc, org.joml.Vector3d)
     */
    public Vector3d orthogonalizeUnit(Vector3dc v, Vector3d dest) {
        double vx = v.x();
        double vy = v.y();
        double vz = v.z();
        double dot = (vx * x + vy * y + vz * z);
        double rx = x - dot * vx;
        double ry = y - dot * vy;
        double rz = z - dot * vz;
        double invLen = 1.0 / Math.sqrt(rx * rx + ry * ry + rz * rz);
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
    public Vector3d orthogonalizeUnit(Vector3dc v) {
        return orthogonalizeUnit(v, this);
    }

    /**
     * Create a new immutable view of this {@link Vector3d}.
     * <p>
     * The observable state of the returned object is the same as that of <code>this</code>, but casting
     * the returned object to Vector3d will not be possible.
     * <p>
     * This method allocates a new instance of a class implementing Vector3dc on every call.
     * 
     * @return the immutable instance
     */
    public Vector3dc toImmutable() {
        if (Options.NO_PROXY)
            return this;
        return new Proxy(this);
    }

}
