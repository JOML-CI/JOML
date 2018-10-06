/*
 * (C) Copyright 2015-2018 Richard Greenlees
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
//#ifdef __HAS_NIO__
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
//#endif
import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.joml.Math;
import org.joml.internal.MemUtil;
import org.joml.internal.Options;
import org.joml.internal.Runtime;

/**
 * Contains the definition of a Vector comprising 3 ints and associated
 * transformations.
 *
 * @author Richard Greenlees
 * @author Kai Burjack
 * @author Hans Uhlig
 */
public class Vector3i implements Externalizable, Vector3ic {

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
     * Create a new {@link Vector3i} of <code>(0, 0, 0)</code>.
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
    	this(v.x(), v.y(), v.z());
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
    	this(v.x(), v.y(), z);
    }

//#ifdef __HAS_NIO__
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
     *          values will be read in <code>x, y, z</code> order
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
     *          values will be read in <code>x, y, z</code> order
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
     *          values will be read in <code>x, y, z</code> order
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
     *          values will be read in <code>x, y, z</code> order
     */
    public Vector3i(int index, IntBuffer buffer) {
        MemUtil.INSTANCE.get(this, index, buffer);
    }
//#endif

    private Vector3i thisOrNew() {
        return this;
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
        return set((int) v.x(), (int) v.y(), (int) v.z());
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
        return set(v.x(), v.y(), z);
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

//#ifdef __HAS_NIO__
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
     *          values will be read in <code>x, y, z</code> order
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
     *          values will be read in <code>x, y, z</code> order
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
     *          values will be read in <code>x, y, z</code> order
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
     *          values will be read in <code>x, y, z</code> order
     * @return this
     */
    public Vector3i set(int index, IntBuffer buffer) {
        MemUtil.INSTANCE.get(this, index, buffer);
        return this;
    }
//#endif

//#ifndef __GWT__
    /**
     * Set the values of this vector by reading 3 integer values from off-heap memory,
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
    public Vector3i setFromAddress(long address) {
        if (Options.NO_UNSAFE)
            throw new UnsupportedOperationException("Not supported when using joml.nounsafe");
        MemUtil.MemUtilUnsafe unsafe = (MemUtil.MemUtilUnsafe) MemUtil.INSTANCE;
        unsafe.get(this, address);
        return this;
    }
//#endif

    /* (non-Javadoc)
     * @see org.joml.Vector3ic#get(int)
     */
    public int get(int component) throws IllegalArgumentException {
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
     *          the component whose value to set, within <code>[0..2]</code>
     * @param value
     *          the value to set
     * @return this
     * @throws IllegalArgumentException if <code>component</code> is not within <code>[0..2]</code>
     */
    public Vector3i setComponent(int component, int value) throws IllegalArgumentException {
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
//#endif

//#ifndef __GWT__
    public Vector3ic getToAddress(long address) {
        if (Options.NO_UNSAFE)
            throw new UnsupportedOperationException("Not supported when using joml.nounsafe");
        MemUtil.MemUtilUnsafe unsafe = (MemUtil.MemUtilUnsafe) MemUtil.INSTANCE;
        unsafe.put(this, address);
        return this;
    }
//#endif

    /**
     * Subtract the supplied vector from this one and store the result in
     * <code>this</code>.
     *
     * @param v
     *          the vector to subtract
     * @return a vector holding the result
     */
    public Vector3i sub(Vector3ic v) {
        return sub(v.x(), v.y(), v.z(), thisOrNew());
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
     * @return a vector holding the result
     */
    public Vector3i sub(int x, int y, int z) {
        return sub(x, y, z, thisOrNew());
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
     * @return a vector holding the result
     */
    public Vector3i add(Vector3ic v) {
        return add(v.x(), v.y(), v.z(), thisOrNew());
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
     * @return a vector holding the result
     */
    public Vector3i add(int x, int y, int z) {
        return add(x, y, z, thisOrNew());
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
     * @return a vector holding the result
     */
    public Vector3i mul(int scalar) {
        return mul(scalar, thisOrNew());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3ic#mul(int, org.joml.Vector3i)
     */
    public Vector3i mul(int scalar, Vector3i dest) {
        dest.x = x * scalar;
        dest.y = y * scalar;
        dest.z = z * scalar;
        return dest;
    }

    /**
     * Multiply all components of this {@link Vector3i} by the given vector.
     *
     * @param v
     *          the vector to multiply
     * @return a vector holding the result
     */
    public Vector3i mul(Vector3ic v) {
        return mul(v.x(), v.y(), v.z(), thisOrNew());
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
     * @return a vector holding the result
     */
    public Vector3i mul(int x, int y, int z) {
        return mul(x, y, z, thisOrNew());
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
     */
    public static long lengthSquared(int x, int y, int z) {
        return x * x + y * y + z * z;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3ic#length()
     */
    public double length() {
        return Math.sqrt(lengthSquared());
    }

    /**
     * Get the length of a 3-dimensional single-precision vector.
     *
     * @param x The vector's x component
     * @param y The vector's y component
     * @param z The vector's z component
     *
     * @return the length squared of the given vector
     */
    public static double length(int x, int y, int z) {
        return Math.sqrt(lengthSquared(x, y, z));
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3ic#distance(org.joml.Vector3ic)
     */
    public double distance(Vector3ic v) {
        return distance(v.x(), v.y(), v.z());
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
        return distanceSquared(v.x(), v.y(), v.z());
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
    public static double distance(int x1, int y1, int z1, int x2, int y2, int z2) {
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
    public static long distanceSquared(int x1, int y1, int z1, int x2, int y2, int z2) {
        int dx = x1 - x2;
        int dy = y1 - y2;
        int dz = z1 - z2;
        return dx * dx + dy * dy + dz * dz;
    }

    /**
     * Set all components to zero.
     *
     * @return a vector holding the result
     */
    public Vector3i zero() {
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
     * @return a vector holding the result
     */
    public Vector3i negate() {
        return negate(thisOrNew());
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

    /**
     * Set the components of this vector to be the component-wise minimum of this and the other vector.
     *
     * @param v
     *          the other vector
     * @return a vector holding the result
     */
    public Vector3i min(Vector3ic v) {
        return min(v, thisOrNew());
    }

    public Vector3i min(Vector3ic v, Vector3i dest) {
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
    public Vector3i max(Vector3ic v) {
        return max(v, thisOrNew());
    }

    public Vector3i max(Vector3ic v, Vector3i dest) {
        dest.x = x > v.x() ? x : v.x();
        dest.y = y > v.y() ? y : v.y();
        dest.z = z > v.z() ? z : v.z();
        return dest;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector3ic#maxComponent()
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
     * @see org.joml.Vector3ic#minComponent()
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

    /* (non-Javadoc)
     * @see org.joml.Vector3ic#equals(int, int, int)
     */
    public boolean equals(int x, int y, int z) {
        if (this.x != x)
            return false;
        if (this.y != y)
            return false;
        if (this.z != z)
            return false;
        return true;
    }
}
