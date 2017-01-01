/*
 * (C) Copyright 2015-2017 Richard Greenlees
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 *
 */
package org.joml;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.text.NumberFormat;

/**
 * Represents a 2D vector with single-precision.
 *
 * @author RGreenlees
 * @author Kai Burjack
 * @author Hans Uhlig
 */
public class Vector2i implements Externalizable, Vector2ic {

    private final class Proxy implements Vector2ic {
        private final Vector2ic delegate;

        Proxy(Vector2ic delegate) {
            this.delegate = delegate;
        }

        public int x() {
            return delegate.x();
        }

        public int y() {
            return delegate.y();
        }

        public ByteBuffer get(ByteBuffer buffer) {
            return delegate.get(buffer);
        }

        public ByteBuffer get(int index, ByteBuffer buffer) {
            return delegate.get(index, buffer);
        }

        public IntBuffer get(IntBuffer buffer) {
            return delegate.get(buffer);
        }

        public IntBuffer get(int index, IntBuffer buffer) {
            return delegate.get(index, buffer);
        }

        public Vector2i sub(Vector2ic v, Vector2i dest) {
            return delegate.sub(v, dest);
        }

        public Vector2i sub(int x, int y, Vector2i dest) {
            return delegate.sub(x, y, dest);
        }

        public long lengthSquared() {
            return delegate.lengthSquared();
        }

        public double length() {
            return delegate.length();
        }

        public double distance(Vector2ic v) {
            return delegate.distance(v);
        }

        public double distance(int x, int y) {
            return delegate.distance(x, y);
        }

        public long distanceSquared(Vector2ic v) {
            return delegate.distanceSquared(v);
        }

        public long distanceSquared(int x, int y) {
            return delegate.distanceSquared(x, y);
        }

        public Vector2i add(Vector2ic v, Vector2i dest) {
            return delegate.add(v, dest);
        }

        public Vector2i add(int x, int y, Vector2i dest) {
            return delegate.add(x, y, dest);
        }

        public Vector2i mul(int scalar, Vector2i dest) {
            return delegate.mul(scalar, dest);
        }

        public Vector2i mul(Vector2ic v, Vector2i dest) {
            return delegate.mul(v, dest);
        }

        public Vector2i mul(int x, int y, Vector2i dest) {
            return delegate.mul(x, y, dest);
        }

        public Vector2i negate(Vector2i dest) {
            return delegate.negate(dest);
        }
    }

    private static final long serialVersionUID = 1L;

    /**
     * The x component of the vector.
     */
    public int x;
    /**
     * The y component of the vector.
     */
    public int y;

    /**
     * Create a new {@link Vector2i} and initialize its components to zero.
     */
    public Vector2i() {
    }

    /**
     * Create a new {@link Vector2i} and initialize both of its components with
     * the given value.
     *
     * @param s
     *          the value of both components
     */
    public Vector2i(int s) {
        this.x = s;
        this.y = s;
    }

    /**
     * Create a new {@link Vector2i} and initialize its components to the given values.
     *
     * @param x
     *          the x component
     * @param y
     *          the y component
     */
    public Vector2i(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Create a new {@link Vector2i} and initialize its components to the one of
     * the given vector.
     *
     * @param v
     *          the {@link Vector2ic} to copy the values from
     */
    public Vector2i(Vector2ic v) {
        x = v.x();
        y = v.y();
    }

    /**
     * Create a new {@link Vector2i} and read this vector from the supplied
     * {@link ByteBuffer} at the current buffer
     * {@link ByteBuffer#position() position}.
     * <p>
     * This method will not increment the position of the given ByteBuffer.
     * <p>
     * In order to specify the offset into the ByteBuffer at which the vector is
     * read, use {@link #Vector2i(int, ByteBuffer)}, taking the absolute
     * position as parameter.
     *
     * @see #Vector2i(int, ByteBuffer)
     *
     * @param buffer
     *          values will be read in <tt>x, y</tt> order
     */
    public Vector2i(ByteBuffer buffer) {
        this(buffer.position(), buffer);
    }

    /**
     * Create a new {@link Vector2i} and read this vector from the supplied
     * {@link ByteBuffer} starting at the specified absolute buffer
     * position/index.
     * <p>
     * This method will not increment the position of the given ByteBuffer.
     *
     * @param index
     *          the absolute position into the ByteBuffer
     * @param buffer
     *          values will be read in <tt>x, y</tt> order
     */
    public Vector2i(int index, ByteBuffer buffer) {
        MemUtil.INSTANCE.get(this, index, buffer);
    }

    /**
     * Create a new {@link Vector2i} and read this vector from the supplied
     * {@link IntBuffer} at the current buffer
     * {@link IntBuffer#position() position}.
     * <p>
     * This method will not increment the position of the given IntBuffer.
     * <p>
     * In order to specify the offset into the IntBuffer at which the vector is
     * read, use {@link #Vector2i(int, IntBuffer)}, taking the absolute position
     * as parameter.
     *
     * @see #Vector2i(int, IntBuffer)
     *
     * @param buffer
     *          values will be read in <tt>x, y</tt> order
     */
    public Vector2i(IntBuffer buffer) {
        this(buffer.position(), buffer);
    }

    /**
     * Create a new {@link Vector2i} and read this vector from the supplied
     * {@link IntBuffer} starting at the specified absolute buffer
     * position/index.
     * <p>
     * This method will not increment the position of the given IntBuffer.
     *
     * @param index
     *          the absolute position into the IntBuffer
     * @param buffer
     *          values will be read in <tt>x, y</tt> order
     */
    public Vector2i(int index, IntBuffer buffer) {
        MemUtil.INSTANCE.get(this, index, buffer);
    }

    /* (non-Javadoc)
     * @see org.joml.Vector2ic#x()
     */
    public int x() {
        return this.x;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector2ic#y()
     */
    public int y() {
        return this.y;
    }

    /**
     * Set the x and y components to the supplied value.
     *
     * @param s
     *          scalar value of both components
     * @return this
     */
    public Vector2i set(int s) {
        this.x = s;
        this.y = s;
        return this;
    }

    /**
     * Set the x and y components to the supplied values.
     *
     * @param x
     *          the x component
     * @param y
     *          the y component
     * @return this
     */
    public Vector2i set(int x, int y) {
        this.x = x;
        this.y = y;
        return this;
    }

    /**
     * Set this {@link Vector2i} to the values of v.
     *
     * @param v
     *          the vector to copy from
     * @return this
     */
    public Vector2i set(Vector2ic v) {
        x = v.x();
        y = v.y();
        return this;
    }

    /**
     * Set this {@link Vector2i} to the values of v.
     * <p>
     * Note that due to the given vector <code>v</code> storing the components
     * in double-precision, there is the possibility to lose precision.
     *
     * @param v
     *          the vector to copy from
     * @return this
     */
    public Vector2i set(Vector2dc v) {
        x = (int) v.x();
        y = (int) v.y();
        return this;
    }

    /**
     * Read this vector from the supplied {@link ByteBuffer} at the current
     * buffer {@link ByteBuffer#position() position}.
     * <p>
     * This method will not increment the position of the given ByteBuffer.
     * <p>
     * In order to specify the offset into the ByteBuffer at which the vector is
     * read, use {@link #set(int, ByteBuffer)}, taking the absolute position as
     * parameter.
     *
     * @see #set(int, ByteBuffer)
     *
     * @param buffer
     *          values will be read in <tt>x, y</tt> order
     * @return this
     */
    public Vector2i set(ByteBuffer buffer) {
        return set(buffer.position(), buffer);
    }

    /**
     * Read this vector from the supplied {@link ByteBuffer} starting at the
     * specified absolute buffer position/index.
     * <p>
     * This method will not increment the position of the given ByteBuffer.
     *
     * @param index
     *          the absolute position into the ByteBuffer
     * @param buffer
     *          values will be read in <tt>x, y</tt> order
     * @return this
     */
    public Vector2i set(int index, ByteBuffer buffer) {
        MemUtil.INSTANCE.get(this, index, buffer);
        return this;
    }

    /**
     * Read this vector from the supplied {@link IntBuffer} at the current
     * buffer {@link IntBuffer#position() position}.
     * <p>
     * This method will not increment the position of the given IntBuffer.
     * <p>
     * In order to specify the offset into the IntBuffer at which the vector is
     * read, use {@link #set(int, IntBuffer)}, taking the absolute position as
     * parameter.
     *
     * @see #set(int, IntBuffer)
     *
     * @param buffer
     *          values will be read in <tt>x, y</tt> order
     * @return this
     */
    public Vector2i set(IntBuffer buffer) {
        return set(buffer.position(), buffer);
    }

    /**
     * Read this vector from the supplied {@link IntBuffer} starting at the
     * specified absolute buffer position/index.
     * <p>
     * This method will not increment the position of the given IntBuffer.
     *
     * @param index
     *          the absolute position into the IntBuffer
     * @param buffer
     *          values will be read in <tt>x, y</tt> order
     * @return this
     */
    public Vector2i set(int index, IntBuffer buffer) {
        MemUtil.INSTANCE.get(this, index, buffer);
        return this;
    }

    /**
     * Set the value of the specified component of this vector.
     *
     * @param component
     *          the component whose value to set, within <tt>[0..1]</tt>
     * @param value
     *          the value to set
     * @return this
     * @throws IllegalArgumentException if <code>component</code> is not within <tt>[0..1]</tt>
     */
    public Vector2i setComponent(int component, int value) throws IllegalArgumentException {
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

    /* (non-Javadoc)
     * @see org.joml.Vector2ic#get(java.nio.ByteBuffer)
     */
    public ByteBuffer get(ByteBuffer buffer) {
        return get(buffer.position(), buffer);
    }

    /* (non-Javadoc)
     * @see org.joml.Vector2ic#get(int, java.nio.ByteBuffer)
     */
    public ByteBuffer get(int index, ByteBuffer buffer) {
        MemUtil.INSTANCE.put(this, index, buffer);
        return buffer;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector2ic#get(java.nio.IntBuffer)
     */
    public IntBuffer get(IntBuffer buffer) {
        return get(buffer.position(), buffer);
    }

    /* (non-Javadoc)
     * @see org.joml.Vector2ic#get(int, java.nio.IntBuffer)
     */
    public IntBuffer get(int index, IntBuffer buffer) {
        MemUtil.INSTANCE.put(this, index, buffer);
        return buffer;
    }

    /**
     * Subtract the supplied vector from this one and store the result in
     * <code>this</code>.
     *
     * @param v
     *          the vector to subtract
     * @return this
     */
    public Vector2i sub(Vector2ic v) {
        x -= v.x();
        y -= v.y();
        return this;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector2ic#sub(org.joml.Vector2ic, org.joml.Vector2i)
     */
    public Vector2i sub(Vector2ic v, Vector2i dest) {
        dest.x = x - v.x();
        dest.y = y - v.y();
        return dest;
    }

    /**
     * Decrement the components of this vector by the given values.
     *
     * @param x
     *          the x component to subtract
     * @param y
     *          the y component to subtract
     * @return this
     */
    public Vector2i sub(int x, int y) {
        this.x -= x;
        this.y -= y;
        return this;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector2ic#sub(int, int, org.joml.Vector2i)
     */
    public Vector2i sub(int x, int y, Vector2i dest) {
        dest.x = this.x - x;
        dest.y = this.y - y;
        return dest;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector2ic#lengthSquared()
     */
    public long lengthSquared() {
        return x * x + y * y;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector2ic#length()
     */
    public double length() {
        return Math.sqrt(lengthSquared());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector2ic#distance(org.joml.Vector2ic)
     */
    public double distance(Vector2ic v) {
        return Math.sqrt(distanceSquared(v));
    }

    /* (non-Javadoc)
     * @see org.joml.Vector2ic#distance(int, int)
     */
    public double distance(int x, int y) {
        return Math.sqrt(distanceSquared(x, y));
    }

    /* (non-Javadoc)
     * @see org.joml.Vector2ic#distanceSquared(org.joml.Vector2ic)
     */
    public long distanceSquared(Vector2ic v) {
        int dx = this.x - v.x();
        int dy = this.y - v.y();
        return dx * dx + dy * dy;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector2ic#distanceSquared(int, int)
     */
    public long distanceSquared(int x, int y) {
        int dx = this.x - x;
        int dy = this.y - y;
        return dx * dx + dy * dy;
    }
    
    /**
     * Add <code>v</code> to this vector.
     *
     * @param v
     *          the vector to add
     * @return this
     */
    public Vector2i add(Vector2ic v) {
        x += v.x();
        y += v.y();
        return this;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector2ic#add(org.joml.Vector2ic, org.joml.Vector2i)
     */
    public Vector2i add(Vector2ic v, Vector2i dest) {
        dest.x = x + v.x();
        dest.y = y + v.y();
        return dest;
    }

    /**
     * Increment the components of this vector by the given values.
     *
     * @param x
     *          the x component to add
     * @param y
     *          the y component to add
     * @return this
     */
    public Vector2i add(int x, int y) {
        this.x += x;
        this.y += y;
        return this;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector2ic#add(int, int, org.joml.Vector2i)
     */
    public Vector2i add(int x, int y, Vector2i dest) {
        dest.x = this.x + x;
        dest.y = this.y + y;
        return dest;
    }

    /**
     * Multiply all components of this {@link Vector2i} by the given scalar
     * value.
     * 
     * @param scalar
     *          the scalar to multiply this vector by
     * @return this
     */
    public Vector2i mul(int scalar) {
        x *= scalar;
        y *= scalar;
        return this;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector2ic#mul(int, org.joml.Vector2i)
     */
    public Vector2i mul(int scalar, Vector2i dest) {
        dest.x = x * scalar;
        dest.y = y * scalar;
        return dest;
    }

    /**
     * Add the supplied vector by this one.
     *
     * @param v
     *          the vector to multiply
     * @return this
     */
    public Vector2i mul(Vector2ic v) {
        x += v.x();
        y += v.y();
        return this;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector2ic#mul(org.joml.Vector2ic, org.joml.Vector2i)
     */
    public Vector2i mul(Vector2ic v, Vector2i dest) {
        dest.x = x * v.x();
        dest.y = y * v.y();
        return dest;
    }

    /**
     * Multiply the components of this vector by the given values.
     *
     * @param x
     *          the x component to multiply
     * @param y
     *          the y component to multiply
     * @return this
     */
    public Vector2i mul(int x, int y) {
        this.x *= x;
        this.y *= y;
        return this;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector2ic#mul(int, int, org.joml.Vector2i)
     */
    public Vector2i mul(int x, int y, Vector2i dest) {
        dest.x = this.x * x;
        dest.y = this.y * y;
        return dest;
    }

    /**
     * Set all components to zero.
     *
     * @return this
     */
    public Vector2i zero() {
        this.x = 0;
        this.y = 0;
        return this;
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeInt(x);
        out.writeInt(y);
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        x = in.readInt();
        y = in.readInt();
    }

    /**
     * Negate this vector.
     *
     * @return this
     */
    public Vector2i negate() {
        x = -x;
        y = -y;
        return this;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector2ic#negate(org.joml.Vector2i)
     */
    public Vector2i negate(Vector2i dest) {
        dest.x = -x;
        dest.y = -y;
        return dest;
    }

    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + x;
        result = prime * result + y;
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Vector2i other = (Vector2i) obj;
        if (x != other.x) {
            return false;
        }
        if (y != other.y) {
            return false;
        }
        return true;
    }

    /**
     * Return a string representation of this vector.
     *
     * @return the string representation
     */
    public String toString() {
        return "(" + x + " " + y + ")"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
    }

    /**
     * Return a string representation of this vector by formatting the vector
     * components with the given {@link NumberFormat}.
     *
     * @param formatter
     *          the {@link NumberFormat} used to format the vector components with
     * @return the string representation
     */
    public String toString(NumberFormat formatter) {
        return "(" + formatter.format(x) + " " + formatter.format(y) + ")"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
    }

    /**
     * Create a new immutable view of this {@link Vector2i}.
     * <p>
     * The observable state of the returned object is the same as that of <code>this</code>, but casting
     * the returned object to Vector2i will not be possible.
     * <p>
     * This method allocates a new instance of a class implementing Vector2ic on every call.
     * 
     * @return the immutable instance
     */
    public Vector2ic toImmutable() {
        if (!Options.DEBUG)
            return this;
        return new Proxy(this);
    }

}
