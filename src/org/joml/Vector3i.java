/*
 * (C) Copyright 2015-2016 Richard Greenlees
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
 * Contains the definition of a Vector comprising 3 ints and associated
 * transformations.
 *
 * @author Richard Greenlees
 * @author Kai Burjack
 * @author Hans Uhlig
 */
public class Vector3i implements Externalizable, Vector3ic {

    private final class Proxy implements Vector3ic {
        private final Vector3ic delegate;

        Proxy(Vector3ic delegate) {
            this.delegate = delegate;
        }

        public int x() {
            return delegate.x();
        }

        public int y() {
            return delegate.y();
        }

        public int z() {
            return delegate.z();
        }

        public IntBuffer get(IntBuffer buffer) {
            return delegate.get(buffer);
        }

        public IntBuffer get(int index, IntBuffer buffer) {
            return delegate.get(index, buffer);
        }

        public ByteBuffer get(ByteBuffer buffer) {
            return delegate.get(buffer);
        }

        public ByteBuffer get(int index, ByteBuffer buffer) {
            return delegate.get(index, buffer);
        }

        public Vector3i sub(Vector3ic v, Vector3i dest) {
            return delegate.sub(v, dest);
        }

        public Vector3i sub(int x, int y, int z, Vector3i dest) {
            return delegate.sub(x, y, z, dest);
        }

        public Vector3i add(Vector3ic v, Vector3i dest) {
            return delegate.add(v, dest);
        }

        public Vector3i add(int x, int y, int z, Vector3i dest) {
            return delegate.add(x, y, z, dest);
        }

        public Vector3i mul(int scalar, Vector3i dest) {
            return delegate.mul(scalar, dest);
        }

        public Vector3i mul(Vector3ic v, Vector3i dest) {
            return delegate.mul(v, dest);
        }

        public Vector3i mul(int x, int y, int z, Vector3i dest) {
            return delegate.mul(x, y, z, dest);
        }

        public long lengthSquared() {
            return delegate.lengthSquared();
        }

        public double length() {
            return delegate.length();
        }

        public double distance(Vector3ic v) {
            return delegate.distance(v);
        }

        public double distance(int x, int y, int z) {
            return delegate.distance(x, y, z);
        }

        public long distanceSquared(Vector3ic v) {
            return delegate.distanceSquared(v);
        }

        public long distanceSquared(int x, int y, int z) {
            return delegate.distanceSquared(x, y, z);
        }

        public Vector3i negate(Vector3i dest) {
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
     * The z component of the vector.
     */
    public int z;

    /**
     * Create a new {@link Vector3i} of <tt>(0, 0, 0)</tt>.
     */
    public Vector3i() {
    }

    /**
     * Create a new {@link Vector3i} and initialize all three components with
     * the given value.
     *
     * @param d
     *          the value of all three components
     */
    public Vector3i(int d) {
        this(d, d, d);
    }

    /**
     * Create a new {@link Vector3i} with the given component values.
     *
     * @param x
     *          the value of x
     * @param y
     *          the value of y
     * @param z
     *          the value of z
     */
    public Vector3i(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Create a new {@link Vector3i} with the same values as <code>v</code>.
     *
     * @param v
     *          the {@link Vector3ic} to copy the values from
     */
    public Vector3i(Vector3ic v) {
        this.x = v.x();
        this.y = v.y();
        this.z = v.z();
    }

    /**
     * Create a new {@link Vector3i} with the first two components from the
     * given <code>v</code> and the given <code>z</code>
     *
     * @param v
     *          the {@link Vector2ic} to copy the values from
     * @param z
     *          the z component
     */
    public Vector3i(Vector2ic v, int z) {
        this.x = v.x();
        this.y = v.y();
        this.z = z;
    }

    /**
     * Create a new {@link Vector3i} and read this vector from the supplied
     * {@link ByteBuffer} at the current buffer
     * {@link ByteBuffer#position() position}.
     * <p>
     * This method will not increment the position of the given ByteBuffer.
     * <p>
     * In order to specify the offset into the ByteBuffer at which the vector is
     * read, use {@link #Vector3i(int, ByteBuffer)}, taking the absolute
     * position as parameter.
     * 
     * @see #Vector3i(int, ByteBuffer)
     *
     * @param buffer
     *          values will be read in <tt>x, y, z</tt> order
     */
    public Vector3i(ByteBuffer buffer) {
        this(buffer.position(), buffer);
    }

    /**
     * Create a new {@link Vector3i} and read this vector from the supplied
     * {@link ByteBuffer} starting at the specified absolute buffer
     * position/index.
     * <p>
     * This method will not increment the position of the given ByteBuffer.
     *
     * @param index
     *          the absolute position into the ByteBuffer
     * @param buffer
     *          values will be read in <tt>x, y, z</tt> order
     */
    public Vector3i(int index, ByteBuffer buffer) {
        MemUtil.INSTANCE.get(this, index, buffer);
    }

    /**
     * Create a new {@link Vector3i} and read this vector from the supplied
     * {@link IntBuffer} at the current buffer
     * {@link IntBuffer#position() position}.
     * <p>
     * This method will not increment the position of the given IntBuffer.
     * <p>
     * In order to specify the offset into the IntBuffer at which the vector is
     * read, use {@link #Vector3i(int, IntBuffer)}, taking the absolute position
     * as parameter.
     *
     * @see #Vector3i(int, IntBuffer)
     *
     * @param buffer
     *          values will be read in <tt>x, y, z</tt> order
     */
    public Vector3i(IntBuffer buffer) {
        this(buffer.position(), buffer);
    }

    /**
     * Create a new {@link Vector3i} and read this vector from the supplied
     * {@link IntBuffer} starting at the specified absolute buffer
     * position/index.
     * <p>
     * This method will not increment the position of the given IntBuffer.
     *
     * @param index
     *          the absolute position into the IntBuffer
     * @param buffer
     *          values will be read in <tt>x, y, z</tt> order
     */
    public Vector3i(int index, IntBuffer buffer) {
        MemUtil.INSTANCE.get(this, index, buffer);
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3ic#x()
     */
    public int x() {
        return this.x;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3ic#y()
     */
    public int y() {
        return this.y;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3ic#z()
     */
    public int z() {
        return this.z;
    }

    /**
     * Set the x, y and z components to match the supplied vector.
     *
     * @param v
     *          contains the values of x, y and z to set
     * @return this
     */
    public Vector3i set(Vector3ic v) {
        x = v.x();
        y = v.y();
        z = v.z();
        return this;
    }

    /**
     * Set the x, y and z components to match the supplied vector.
     * <p>
     * Note that due to the given vector <code>v</code> storing the components
     * in double-precision, there is the possibility to lose precision.
     *
     * @param v
     *          contains the values of x, y and z to set
     * @return this
     */
    public Vector3i set(Vector3dc v) {
        x = (int) v.x();
        y = (int) v.y();
        z = (int) v.z();
        return this;
    }

    /**
     * Set the first two components from the given <code>v</code> and the z
     * component from the given <code>z</code>
     *
     * @param v
     *          the {@link Vector2ic} to copy the values from
     * @param z
     *          the z component
     * @return this
     */
    public Vector3i set(Vector2ic v, int z) {
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
    public Vector3i set(int d) {
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
    public Vector3i set(int x, int y, int z) {
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
     * In order to specify the offset into the ByteBuffer at which the vector is
     * read, use {@link #set(int, ByteBuffer)}, taking the absolute position as
     * parameter.
     *
     * @see #set(int, ByteBuffer)
     *
     * @param buffer
     *          values will be read in <tt>x, y, z</tt> order
     * @return this
     */
    public Vector3i set(ByteBuffer buffer) {
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
     *          values will be read in <tt>x, y, z</tt> order
     * @return this
     */
    public Vector3i set(int index, ByteBuffer buffer) {
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
     *          values will be read in <tt>x, y, z</tt> order
     * @return this
     */
    public Vector3i set(IntBuffer buffer) {
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
     *          values will be read in <tt>x, y, z</tt> order
     * @return this
     */
    public Vector3i set(int index, IntBuffer buffer) {
        MemUtil.INSTANCE.get(this, index, buffer);
        return this;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3ic#get(java.nio.IntBuffer)
     */
    public IntBuffer get(IntBuffer buffer) {
        return get(buffer.position(), buffer);
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3ic#get(int, java.nio.IntBuffer)
     */
    public IntBuffer get(int index, IntBuffer buffer) {
        MemUtil.INSTANCE.put(this, index, buffer);
        return buffer;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3ic#get(java.nio.ByteBuffer)
     */
    public ByteBuffer get(ByteBuffer buffer) {
        return get(buffer.position(), buffer);
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3ic#get(int, java.nio.ByteBuffer)
     */
    public ByteBuffer get(int index, ByteBuffer buffer) {
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
    public Vector3i sub(Vector3ic v) {
        x -= v.x();
        y -= v.y();
        z -= v.z();
        return this;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3ic#sub(org.joml.Vector3ic, org.joml.Vector3i)
     */
    public Vector3i sub(Vector3ic v, Vector3i dest) {
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
    public Vector3i sub(int x, int y, int z) {
        this.x -= x;
        this.y -= y;
        this.z -= z;
        return this;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3ic#sub(int, int, int, org.joml.Vector3i)
     */
    public Vector3i sub(int x, int y, int z, Vector3i dest) {
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
    public Vector3i add(Vector3ic v) {
        x += v.x();
        y += v.y();
        z += v.z();
        return this;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3ic#add(org.joml.Vector3ic, org.joml.Vector3i)
     */
    public Vector3i add(Vector3ic v, Vector3i dest) {
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
    public Vector3i add(int x, int y, int z) {
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3ic#add(int, int, int, org.joml.Vector3i)
     */
    public Vector3i add(int x, int y, int z, Vector3i dest) {
        dest.x = this.x + x;
        dest.y = this.y + y;
        dest.z = this.z + z;
        return dest;
    }

    /**
     * Multiply all components of this {@link Vector3i} by the given scalar
     * value.
     * 
     * @param scalar
     *          the scalar to multiply this vector by
     * @return this
     */
    public Vector3i mul(int scalar) {
        x *= scalar;
        y *= scalar;
        z *= scalar;
        return this;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3ic#mul(int, org.joml.Vector3i)
     */
    public Vector3i mul(int scalar, Vector3i dest) {
        dest.x = x * scalar;
        dest.y = y * scalar;
        dest.y = z * scalar;
        return dest;
    }

    /**
     * Add the supplied vector by this one.
     *
     * @param v
     *          the vector to multiply
     * @return this
     */
    public Vector3i mul(Vector3ic v) {
        x += v.x();
        y += v.y();
        z += v.z();
        return this;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3ic#mul(org.joml.Vector3ic, org.joml.Vector3i)
     */
    public Vector3i mul(Vector3ic v, Vector3i dest) {
        dest.x = x * v.x();
        dest.y = y * v.y();
        dest.z = z * v.z();
        return dest;
    }

    /**
     * Multiply the components of this vector by the given values.
     *
     * @param x
     *          the x component to multiply
     * @param y
     *          the y component to multiply
     * @param z
     *          the z component to multiply
     * @return this
     */
    public Vector3i mul(int x, int y, int z) {
        this.x *= x;
        this.y *= y;
        this.z *= z;
        return this;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3ic#mul(int, int, int, org.joml.Vector3i)
     */
    public Vector3i mul(int x, int y, int z, Vector3i dest) {
        dest.x = this.x * x;
        dest.y = this.y * y;
        dest.z = this.z * z;
        return dest;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3ic#lengthSquared()
     */
    public long lengthSquared() {
        return x * x + y * y + z * z;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3ic#length()
     */
    public double length() {
        return Math.sqrt(lengthSquared());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3ic#distance(org.joml.Vector3ic)
     */
    public double distance(Vector3ic v) {
        return Math.sqrt(distanceSquared(v));
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3ic#distance(int, int, int)
     */
    public double distance(int x, int y, int z) {
        return Math.sqrt(distanceSquared(x, y, z));
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3ic#distanceSquared(org.joml.Vector3ic)
     */
    public long distanceSquared(Vector3ic v) {
        int dx = this.x - v.x();
        int dy = this.y - v.y();
        int dz = this.z - v.z();
        return dx * dx + dy * dy + dz * dz;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3ic#distanceSquared(int, int, int)
     */
    public long distanceSquared(int x, int y, int z) {
        int dx = this.x - x;
        int dy = this.y - y;
        int dz = this.z - z;
        return dx * dx + dy * dy + dz * dz;
    }

    /**
     * Set all components to zero.
     *
     * @return this
     */
    public Vector3i zero() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
        return this;
    }

    /**
     * Return a string representation of this vector.
     *
     * @return the string representation
     */
    public String toString() {
        return "(" + x + " " + y + " " + z + ")"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
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
        out.writeInt(x);
        out.writeInt(y);
        out.writeInt(z);
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        x = in.readInt();
        y = in.readInt();
        z = in.readInt();
    }

    /**
     * Negate this vector.
     *
     * @return this
     */
    public Vector3i negate() {
        x = -x;
        y = -y;
        z = -z;
        return this;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3ic#negate(org.joml.Vector3i)
     */
    public Vector3i negate(Vector3i dest) {
        dest.x = -x;
        dest.y = -y;
        dest.z = -z;
        return dest;
    }

    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + x;
        result = prime * result + y;
        result = prime * result + z;
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
        Vector3i other = (Vector3i) obj;
        if (x != other.x) {
            return false;
        }
        if (y != other.y) {
            return false;
        }
        if (z != other.z) {
            return false;
        }
        return true;
    }

    /**
     * Create a new immutable view of this {@link Vector3i}.
     * <p>
     * The observable state of the returned object is the same as that of <code>this</code>, but casting
     * the returned object to Vector3i will not be possible.
     * <p>
     * This method allocates a new instance of a class implementing Vector3ic on every call.
     * 
     * @return the immutable instance
     */
    public Vector3ic toImmutable() {
        if (Options.NO_PROXY)
            return this;
        return new Proxy(this);
    }

}
