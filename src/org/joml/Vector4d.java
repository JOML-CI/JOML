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
//#ifdef __HAS_NIO__
import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
//#endif
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Contains the definition of a Vector comprising 4 doubles and associated transformations.
 * 
 * @author Richard Greenlees
 * @author Kai Burjack
 */
public class Vector4d implements Externalizable, Vector4dc {

    private final class Proxy implements Vector4dc {
        private final Vector4dc delegate;
        
        Proxy(Vector4dc delegate) {
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

        public double w() {
            return delegate.w();
        }

//#ifdef __HAS_NIO__
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
//#endif

        public Vector4d sub(double x, double y, double z, double w, Vector4d dest) {
            return delegate.sub(x, y, z, w, dest);
        }

        public Vector4d add(double x, double y, double z, double w, Vector4d dest) {
            return delegate.add(x, y, z, w, dest);
        }

        public Vector4d fma(Vector4dc a, Vector4dc b, Vector4d dest) {
            return delegate.fma(a, b, dest);
        }

        public Vector4d fma(double a, Vector4dc b, Vector4d dest) {
            return delegate.fma(a, b, dest);
        }

        public Vector4d mul(Vector4dc v, Vector4d dest) {
            return delegate.mul(v, dest);
        }

        public Vector4d div(Vector4dc v, Vector4d dest) {
            return delegate.div(v, dest);
        }

        public Vector4d mul(Matrix4dc mat, Vector4d dest) {
            return delegate.mul(mat, dest);
        }

        public Vector4d mul(Matrix4x3dc mat, Vector4d dest) {
            return delegate.mul(mat, dest);
        }

        public Vector4d mul(Matrix4x3fc mat, Vector4d dest) {
            return delegate.mul(mat, dest);
        }

        public Vector4d mul(Matrix4fc mat, Vector4d dest) {
            return delegate.mul(mat, dest);
        }

        public Vector4d mulProject(Matrix4dc mat, Vector4d dest) {
            return delegate.mulProject(mat, dest);
        }

        public Vector4d mul(double scalar, Vector4d dest) {
            return delegate.mul(scalar, dest);
        }

        public Vector4d div(double scalar, Vector4d dest) {
            return delegate.div(scalar, dest);
        }

        public Vector4d rotate(Quaterniondc quat, Vector4d dest) {
            return delegate.rotate(quat, dest);
        }

        public Vector4d rotateAxis(double angle, double x, double y, double z, Vector4d dest) {
            return delegate.rotateAxis(angle, x, y, z, dest);
        }

        public Vector4d rotateX(double angle, Vector4d dest) {
            return delegate.rotateX(angle, dest);
        }

        public Vector4d rotateY(double angle, Vector4d dest) {
            return delegate.rotateY(angle, dest);
        }

        public Vector4d rotateZ(double angle, Vector4d dest) {
            return delegate.rotateZ(angle, dest);
        }

        public double lengthSquared() {
            return delegate.lengthSquared();
        }

        public double length() {
            return delegate.length();
        }

        public Vector4d normalize(Vector4d dest) {
            return delegate.normalize(dest);
        }

        public Vector4d normalize(double length, Vector4d dest) {
            return delegate.normalize(length, dest);
        }

        public Vector4d normalize3(Vector4d dest) {
            return delegate.normalize3(dest);
        }

        public double distance(Vector4dc v) {
            return delegate.distance(v);
        }

        public double distance(double x, double y, double z, double w) {
            return delegate.distance(x, y, z, w);
        }

        public double dot(Vector4dc v) {
            return delegate.dot(v);
        }

        public double dot(double x, double y, double z, double w) {
            return delegate.dot(x, y, z, w);
        }

        public double angleCos(Vector4dc v) {
            return delegate.angleCos(v);
        }

        public double angle(Vector4dc v) {
            return delegate.angle(v);
        }

        public Vector4d negate(Vector4d dest) {
            return delegate.negate(dest);
        }

        public Vector4d smoothStep(Vector4dc v, double t, Vector4d dest) {
            return delegate.smoothStep(v, t, dest);
        }

        public Vector4d hermite(Vector4dc t0, Vector4dc v1, Vector4dc t1, double t, Vector4d dest) {
            return delegate.hermite(t0, v1, t1, t, dest);
        }

        public Vector4d lerp(Vector4dc other, double t, Vector4d dest) {
            return delegate.lerp(other, t, dest);
        }

        public double get(int component) throws IllegalArgumentException {
            return delegate.get(component);
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
        this.x = v.x();
        this.y = v.y();
        this.z = v.z();
        this.w = v.w();
    }

    /**
     * Create a new {@link Vector4d} with the same values as <code>v</code>.
     * 
     * @param v
     *          the {@link Vector4ic} to copy the values from
     */
    public Vector4d(Vector4ic v) {
        this.x = v.x();
        this.y = v.y();
        this.z = v.z();
        this.w = v.w();
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
        this.x = v.x();
        this.y = v.y();
        this.z = v.z();
        this.w = w;
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
        this.x = v.x();
        this.y = v.y();
        this.z = v.z();
        this.w = w;
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
        this.x = v.x();
        this.y = v.y();
        this.z = z;
        this.w = w;
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
        this.x = v.x();
        this.y = v.y();
        this.z = z;
        this.w = w;
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
        this.x = v.x();
        this.y = v.y();
        this.z = v.z();
        this.w = v.w();
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
        this.x = v.x();
        this.y = v.y();
        this.z = v.z();
        this.w = w;
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
        this.x = v.x();
        this.y = v.y();
        this.z = z;
        this.w = w;
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
     *          values will be read in <tt>x, y, z, w</tt> order
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
     * @param buffer values will be read in <tt>x, y, z, w</tt> order
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
     * @param buffer values will be read in <tt>x, y, z, w</tt> order
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
     * @param buffer values will be read in <tt>x, y, z, w</tt> order
     */
    public Vector4d(int index, DoubleBuffer buffer) {
        MemUtil.INSTANCE.get(this, index, buffer);
    }
//#endif

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
        this.x = v.x();
        this.y = v.y();
        this.z = v.z();
        this.w = v.w();
        return this;
    }

    /**
     * Set this {@link Vector4d} to the values of the given <code>v</code>.
     * 
     * @param v
     *          the vector whose values will be copied into this
     * @return this
     */
    public Vector4d set(Vector4fc v) {
        this.x = v.x();
        this.y = v.y();
        this.z = v.z();
        this.w = v.w();
        return this;
    }

    /**
     * Set this {@link Vector4d} to the values of the given <code>v</code>.
     * 
     * @param v
     *          the vector whose values will be copied into this
     * @return this
     */
    public Vector4d set(Vector4ic v) {
        this.x = v.x();
        this.y = v.y();
        this.z = v.z();
        this.w = v.w();
        return this;
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
        this.x = v.x();
        this.y = v.y();
        this.z = v.z();
        this.w = w;
        return this;
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
        this.x = v.x();
        this.y = v.y();
        this.z = v.z();
        this.w = w;
        return this;
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
        this.x = v.x();
        this.y = v.y();
        this.z = v.z();
        this.w = w;
        return this;
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
        this.x = v.x();
        this.y = v.y();
        this.z = z;
        this.w = w;
        return this;
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
        this.x = v.x();
        this.y = v.y();
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
        this.x = v.x();
        this.y = v.y();
        this.z = z;
        this.w = w;
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
     *          values will be read in <tt>x, y, z, w</tt> order
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
     *          values will be read in <tt>x, y, z, w</tt> order
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
     *          values will be read in <tt>x, y, z, w</tt> order
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
     *          values will be read in <tt>x, y, z, w</tt> order
     * @return this
     */
    public Vector4d set(int index, DoubleBuffer buffer) {
        MemUtil.INSTANCE.get(this, index, buffer);
        return this;
    }
//#endif

    /**
     * Set the value of the specified component of this vector.
     *
     * @param component
     *          the component whose value to set, within <tt>[0..3]</tt>
     * @param value
     *          the value to set
     * @return this
     * @throws IllegalArgumentException if <code>component</code> is not within <tt>[0..3]</tt>
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

    /**
     * Subtract the supplied vector from this one.
     * 
     * @param v
     *          the vector to subtract
     * @return this
     */
    public Vector4d sub(Vector4dc v) {
        x -= v.x();
        y -= v.y();
        z -= v.z();
        w -= v.w();
        return this;
    }

    /**
     * Subtract the supplied vector from this one.
     * 
     * @param v
     *          the vector to subtract
     * @return this
     */
    public Vector4d sub(Vector4fc v) {
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
    public Vector4d sub(double x, double y, double z, double w) {
        this.x -= x;
        this.y -= y;
        this.z -= z;
        this.w -= w;
        return this;
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
     * @return this
     */
    public Vector4d add(Vector4dc v) {
        x += v.x();
        y += v.y();
        z += v.z();
        w += v.w();
        return this;
    }

    /**
     * Add <tt>(x, y, z, w)</tt> to this.
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
    public Vector4d add(double x, double y, double z, double w) {
        this.x += x;
        this.y += y;
        this.z += z;
        this.w += w;
        return this;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4dc#add(double, double, double, double, org.joml.Vector4d)
     */
    public Vector4d add(double x, double y, double z, double w, Vector4d dest) {
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
    public Vector4d add(Vector4fc v) {
        x += v.x();
        y += v.y();
        z += v.z();
        w += v.w();
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
    public Vector4d fma(Vector4dc a, Vector4dc b) {
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
    public Vector4d fma(double a, Vector4dc b) {
        x += a * b.x();
        y += a * b.y();
        z += a * b.z();
        w += a * b.w();
        return this;
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
     * @return this
     */
    public Vector4d mul(Vector4dc v) {
        x *= v.x();
        y *= v.y();
        z *= v.z();
        z *= v.w();
        return this;
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
     * @return this
     */
    public Vector4d div(Vector4dc v) {
        x /= v.x();
        y /= v.y();
        z /= v.z();
        z /= v.w();
        return this;
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
     * @return this
     */
    public Vector4d mul(Vector4fc v) {
        x *= v.x();
        y *= v.y();
        z *= v.z();
        z *= v.w();
        return this;
    }

    /**
     * Multiply the given matrix <code>mat</code> with this {@link Vector4d}.
     * 
     * @param mat
     *          the matrix to multiply by
     * @return this
     */
    public Vector4d mul(Matrix4dc mat) {
        return mul(mat, this);
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4dc#mul(org.joml.Matrix4dc, org.joml.Vector4d)
     */
    public Vector4d mul(Matrix4dc mat, Vector4d dest) {
        dest.set(mat.m00() * x + mat.m10() * y + mat.m20() * z + mat.m30() * w,
                 mat.m01() * x + mat.m11() * y + mat.m21() * z + mat.m31() * w,
                 mat.m02() * x + mat.m12() * y + mat.m22() * z + mat.m32() * w, 
                 mat.m03() * x + mat.m13() * y + mat.m23() * z + mat.m33() * w);
        return dest;
    }

    /**
     * Multiply the given matrix mat with this Vector4d and store the result in
     * <code>this</code>.
     * 
     * @param mat
     *          the matrix to multiply the vector with
     * @return this
     */
    public Vector4d mul(Matrix4x3dc mat) {
        return mul(mat, this);
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4dc#mul(org.joml.Matrix4x3dc, org.joml.Vector4d)
     */
    public Vector4d mul(Matrix4x3dc mat, Vector4d dest) {
        dest.set(mat.m00() * x + mat.m10() * y + mat.m20() * z + mat.m30() * w,
                 mat.m01() * x + mat.m11() * y + mat.m21() * z + mat.m31() * w,
                 mat.m02() * x + mat.m12() * y + mat.m22() * z + mat.m32() * w,
                 w);
        return dest;
    }

    /**
     * Multiply the given matrix mat with this Vector4d and store the result in
     * <code>this</code>.
     * 
     * @param mat
     *          the matrix to multiply the vector with
     * @return this
     */
    public Vector4d mul(Matrix4x3fc mat) {
        return mul(mat, this);
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4dc#mul(org.joml.Matrix4x3fc, org.joml.Vector4d)
     */
    public Vector4d mul(Matrix4x3fc mat, Vector4d dest) {
        dest.set(mat.m00() * x + mat.m10() * y + mat.m20() * z + mat.m30() * w,
                 mat.m01() * x + mat.m11() * y + mat.m21() * z + mat.m31() * w,
                 mat.m02() * x + mat.m12() * y + mat.m22() * z + mat.m32() * w,
                 w);
        return dest;
    }

    /**
     * Multiply the given matrix <code>mat</code> with this {@link Vector4d}.
     * 
     * @param mat
     *          the matrix to multiply by
     * @return this
     */
    public Vector4d mul(Matrix4fc mat) {
        return mul(mat, this);
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4dc#mul(org.joml.Matrix4fc, org.joml.Vector4d)
     */
    public Vector4d mul(Matrix4fc mat, Vector4d dest) {
        dest.set(mat.m00() * x + mat.m10() * y + mat.m20() * z + mat.m30() * w,
                 mat.m01() * x + mat.m11() * y + mat.m21() * z + mat.m31() * w,
                 mat.m02() * x + mat.m12() * y + mat.m22() * z + mat.m32() * w, 
                 mat.m03() * x + mat.m13() * y + mat.m23() * z + mat.m33() * w);
        return dest;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4dc#mulProject(org.joml.Matrix4dc, org.joml.Vector4d)
     */
    public Vector4d mulProject(Matrix4dc mat, Vector4d dest) {
        double invW = 1.0 / (mat.m03() * x + mat.m13() * y + mat.m23() * z + mat.m33() * w);
        dest.set((mat.m00() * x + mat.m10() * y + mat.m20() * z + mat.m30() * w) * invW,
                 (mat.m01() * x + mat.m11() * y + mat.m21() * z + mat.m31() * w) * invW,
                 (mat.m02() * x + mat.m12() * y + mat.m22() * z + mat.m32() * w) * invW,
                 1.0);
        return dest;
    }

    /**
     * Multiply the given matrix <code>mat</code> with this Vector4d, perform perspective division.
     * 
     * @param mat
     *          the matrix to multiply this vector by
     * @return this
     */
    public Vector4d mulProject(Matrix4dc mat) {
        return mulProject(mat, this);
    }

    /**
     * Multiply this Vector4d by the given scalar value.
     * 
     * @param scalar
     *          the scalar to multiply by
     * @return this
     */
    public Vector4d mul(double scalar) {
        x *= scalar;
        y *= scalar;
        z *= scalar;
        w *= scalar;
        return this;
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
     * @return this
     */
    public Vector4d div(double scalar) {
        double inv = 1.0 / scalar;
        x *= inv;
        y *= inv;
        z *= inv;
        w *= inv;
        return this;
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
     * @return this
     */
    public Vector4d rotate(Quaterniondc quat) {
        return rotate(quat, this);
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
     * @return this
     */
    public Vector4d rotateAxis(double angle, double x, double y, double z) {
        return rotateAxis(angle, x, y, z, this);
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
     * @return this
     */
    public Vector4d rotateX(double angle) {
        return rotateX(angle, this);
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4dc#rotateX(double, org.joml.Vector4d)
     */
    public Vector4d rotateX(double angle, Vector4d dest) {
        double sin = Math.sin(angle * 0.5), cos = Math.cosFromSin(sin, angle * 0.5);
        dest.x = x;
        dest.y = y * cos - z * sin;
        dest.z = y * sin + z * cos;
        dest.w = w;
        return dest;
    }

    /**
     * Rotate this vector the specified radians around the Y axis.
     * 
     * @param angle
     *          the angle in radians
     * @return this
     */
    public Vector4d rotateY(double angle) {
        return rotateY(angle, this);
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4dc#rotateY(double, org.joml.Vector4d)
     */
    public Vector4d rotateY(double angle, Vector4d dest) {
        double sin = Math.sin(angle * 0.5), cos = Math.cosFromSin(sin, angle * 0.5);
        dest.x =  x * cos + z * sin;
        dest.y =  y;
        dest.z = -x * sin + z * cos;
        dest.w = w;
        return dest;
    }

    /**
     * Rotate this vector the specified radians around the Z axis.
     * 
     * @param angle
     *          the angle in radians
     * @return this
     */
    public Vector4d rotateZ(double angle) {
        return rotateZ(angle, this);
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4dc#rotateZ(double, org.joml.Vector4d)
     */
    public Vector4d rotateZ(double angle, Vector4d dest) {
        double sin = Math.sin(angle * 0.5), cos = Math.cosFromSin(sin, angle * 0.5);
        dest.x = x * cos - y * sin;
        dest.y = x * sin + y * cos;
        dest.z = z;
        dest.w = w;
        return dest;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4dc#lengthSquared()
     */
    public double lengthSquared() {
        return x * x + y * y + z * z + w * w;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4dc#length()
     */
    public double length() {
        return Math.sqrt(lengthSquared());
    }

    /**
     * Normalizes this vector.
     * 
     * @return this
     */
    public Vector4d normalize() {
        double invLength = 1.0 / length();
        x *= invLength;
        y *= invLength;
        z *= invLength;
        w *= invLength;
        return this;
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
     * @return this
     */
    public Vector4d normalize(double length) {
        double invLength = 1.0 / length() * length;
        x *= invLength;
        y *= invLength;
        z *= invLength;
        w *= invLength;
        return this;
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
     * Normalize this vector by computing only the norm of <tt>(x, y, z)</tt>.
     * 
     * @return this
     */
    public Vector4d normalize3() {
        double invLength = 1.0 / Math.sqrt(x * x + y * y + z * z);
        x *= invLength;
        y *= invLength;
        z *= invLength;
        w *= invLength;
        return this;
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
        double dx = v.x() - x;
        double dy = v.y() - y;
        double dz = v.z() - z;
        double dw = v.w() - w;
        return Math.sqrt(dx * dx + dy * dy + dz * dz + dw * dw);
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4dc#distance(double, double, double, double)
     */
    public double distance(double x, double y, double z, double w) {
        double dx = this.x - x;
        double dy = this.y - y;
        double dz = this.z - z;
        double dw = this.w - w;
        return Math.sqrt(dx * dx + dy * dy + dz * dz + dw * dw);
    }

    /* (non-Javadoc)
     * @see org.joml.Vector4dc#dot(org.joml.Vector4dc)
     */
    public double dot(Vector4dc v) {
        return x * v.x() + y * v.y() + z * v.z() + w * v.w();
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
        double length1Sqared = x * x + y * y + z * z + w * w;
        double length2Sqared = v.x() * v.x() + v.y() * v.y() + v.z() * v.z() + v.w() * v.w();
        double dot = x * v.x() + y * v.y() + z * v.z() + w * v.w();
        return dot / (Math.sqrt(length1Sqared * length2Sqared));
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
     * @return this
     */
    public Vector4d zero() {
        x = 0.0;
        y = 0.0;
        z = 0.0;
        w = 0.0;
        return this;
    }

    /**
     * Negate this vector.
     * 
     * @return this
     */
    public Vector4d negate() {
        x = -x;
        y = -y;
        z = -z;
        w = -w;
        return this;
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
        return "(" + formatter.format(x) + " " + formatter.format(y) + " " + formatter.format(z) + " " + formatter.format(w) + ")";
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
     * If <code>t</code> is <tt>0.0</tt> then the result is <code>this</code>. If the interpolation factor is <code>1.0</code>
     * then the result is <code>other</code>.
     * 
     * @param other
     *          the other vector
     * @param t
     *          the interpolation factor between 0.0 and 1.0
     * @return this
     */
    public Vector4d lerp(Vector4dc other, double t) {
        return lerp(other, t, this);
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

    /**
     * Create a new immutable view of this {@link Vector4d}.
     * <p>
     * The observable state of the returned object is the same as that of <code>this</code>, but casting
     * the returned object to Vector4d will not be possible.
     * <p>
     * This method allocates a new instance of a class implementing Vector4dc on every call.
     * 
     * @return the immutable instance
     */
    public Vector4dc toImmutable() {
        if (!Options.DEBUG)
            return this;
        return new Proxy(this);
    }

}
