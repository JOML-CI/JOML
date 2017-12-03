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

import org.joml.api.vector.IVector2i;
import org.joml.api.vector.IVector3d;
import org.joml.api.vector.IVector3i;
import org.joml.api.vector.Vector3ic;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
//#ifdef __HAS_NIO__
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
//#endif
import java.text.NumberFormat;

/**
 * Contains the definition of a Vector comprising 3 ints and associated transformations.
 *
 * @author Richard Greenlees
 * @author Kai Burjack
 * @author Hans Uhlig
 */
public class Vector3i extends Vector3ic implements Externalizable {

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
     * Create a new {@link Vector3i} and initialize all three components with the given value.
     *
     * @param d the value of all three components
     */
    public Vector3i(int d) {
        this(d, d, d);
    }

    /**
     * Create a new {@link Vector3i} with the given component values.
     *
     * @param x the value of x
     * @param y the value of y
     * @param z the value of z
     */
    public Vector3i(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Create a new {@link Vector3i} with the same values as <code>v</code>.
     *
     * @param v the {@link IVector3i} to copy the values from
     */
    public Vector3i(IVector3i v) {
        this.x = v.x();
        this.y = v.y();
        this.z = v.z();
    }

    /**
     * Create a new {@link Vector3i} with the first two components from the given <code>v</code> and the given
     * <code>z</code>
     *
     * @param v the {@link IVector2i} to copy the values from
     * @param z the z component
     */
    public Vector3i(IVector2i v, int z) {
        this.x = v.x();
        this.y = v.y();
        this.z = z;
    }

    //#ifdef __HAS_NIO__

    /**
     * Create a new {@link Vector3i} and read this vector from the supplied {@link ByteBuffer} at the current buffer
     * {@link ByteBuffer#position() position}.
     * <p>
     * This method will not increment the position of the given ByteBuffer.
     * <p>
     * In order to specify the offset into the ByteBuffer at which the vector is read, use {@link #Vector3i(int,
     * ByteBuffer)}, taking the absolute position as parameter.
     *
     * @param buffer values will be read in <tt>x, y, z</tt> order
     * @see #Vector3i(int, ByteBuffer)
     */
    public Vector3i(ByteBuffer buffer) {
        this(buffer.position(), buffer);
    }

    /**
     * Create a new {@link Vector3i} and read this vector from the supplied {@link ByteBuffer} starting at the specified
     * absolute buffer position/index.
     * <p>
     * This method will not increment the position of the given ByteBuffer.
     *
     * @param index  the absolute position into the ByteBuffer
     * @param buffer values will be read in <tt>x, y, z</tt> order
     */
    public Vector3i(int index, ByteBuffer buffer) {
        MemUtil.INSTANCE.get(this, index, buffer);
    }

    /**
     * Create a new {@link Vector3i} and read this vector from the supplied {@link IntBuffer} at the current buffer
     * {@link IntBuffer#position() position}.
     * <p>
     * This method will not increment the position of the given IntBuffer.
     * <p>
     * In order to specify the offset into the IntBuffer at which the vector is read, use {@link #Vector3i(int,
     * IntBuffer)}, taking the absolute position as parameter.
     *
     * @param buffer values will be read in <tt>x, y, z</tt> order
     * @see #Vector3i(int, IntBuffer)
     */
    public Vector3i(IntBuffer buffer) {
        this(buffer.position(), buffer);
    }

    /**
     * Create a new {@link Vector3i} and read this vector from the supplied {@link IntBuffer} starting at the specified
     * absolute buffer position/index.
     * <p>
     * This method will not increment the position of the given IntBuffer.
     *
     * @param index  the absolute position into the IntBuffer
     * @param buffer values will be read in <tt>x, y, z</tt> order
     */
    public Vector3i(int index, IntBuffer buffer) {
        MemUtil.INSTANCE.get(this, index, buffer);
    }
    //#endif

    public int x() {
        return this.x;
    }

    public int y() {
        return this.y;
    }

    public int z() {
        return this.z;
    }

    public Vector3ic set(IVector3i v) {
        x = v.x();
        y = v.y();
        z = v.z();
        return this;
    }

    public Vector3ic set(IVector3d v) {
        x = (int) v.x();
        y = (int) v.y();
        z = (int) v.z();
        return this;
    }

    public Vector3ic set(IVector2i v, int z) {
        this.x = v.x();
        this.y = v.y();
        this.z = z;
        return this;
    }

    public Vector3ic set(int d) {
        return set(d, d, d);
    }

    public Vector3ic set(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }

    //#ifdef __HAS_NIO__

    public Vector3ic set(ByteBuffer buffer) {
        return set(buffer.position(), buffer);
    }

    public Vector3ic set(int index, ByteBuffer buffer) {
        MemUtil.INSTANCE.get(this, index, buffer);
        return this;
    }

    public Vector3ic set(IntBuffer buffer) {
        return set(buffer.position(), buffer);
    }

    public Vector3ic set(int index, IntBuffer buffer) {
        MemUtil.INSTANCE.get(this, index, buffer);
        return this;
    }
    //#endif

    public Vector3ic setComponent(int component, int value) throws IllegalArgumentException {
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

    public IntBuffer get(IntBuffer buffer) {
        return get(buffer.position(), buffer);
    }

    public IntBuffer get(int index, IntBuffer buffer) {
        MemUtil.INSTANCE.put(this, index, buffer);
        return buffer;
    }

    public ByteBuffer get(ByteBuffer buffer) {
        return get(buffer.position(), buffer);
    }

    public ByteBuffer get(int index, ByteBuffer buffer) {
        MemUtil.INSTANCE.put(this, index, buffer);
        return buffer;
    }
    //#endif

    public Vector3ic sub(IVector3i v) {
        x -= v.x();
        y -= v.y();
        z -= v.z();
        return this;
    }

    public Vector3ic sub(IVector3i v, Vector3ic dest) {
        dest.set(x - v.x(), y - v.y(), z - v.z());
        return dest;
    }

    public Vector3ic sub(int x, int y, int z) {
        this.x -= x;
        this.y -= y;
        this.z -= z;
        return this;
    }

    public Vector3ic sub(int x, int y, int z, Vector3ic dest) {
        dest.set(this.x - x, this.y - y, this.z - z);
        return dest;
    }

    public Vector3ic add(IVector3i v) {
        x += v.x();
        y += v.y();
        z += v.z();
        return this;
    }

    public Vector3ic add(IVector3i v, Vector3ic dest) {
        dest.set(x + v.x(), y + v.y(), z + v.z());
        return dest;
    }

    public Vector3ic add(int x, int y, int z) {
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }

    public Vector3ic add(int x, int y, int z, Vector3ic dest) {
        dest.set(this.x + x, this.y + y, this.z + z);
        return dest;
    }

    public Vector3ic mul(int scalar) {
        x *= scalar;
        y *= scalar;
        z *= scalar;
        return this;
    }

    public Vector3ic mul(int scalar, Vector3ic dest) {
        dest.set(x * scalar, y * scalar, z * scalar);
        return dest;
    }

    public Vector3ic mul(IVector3i v) {
        x *= v.x();
        y *= v.y();
        z *= v.z();
        return this;
    }

    public Vector3ic mul(IVector3i v, Vector3ic dest) {
        dest.set(x * v.x(), y * v.y(), z * v.z());
        return dest;
    }

    public Vector3ic mul(int x, int y, int z) {
        this.x *= x;
        this.y *= y;
        this.z *= z;
        return this;
    }

    public Vector3ic mul(int x, int y, int z, Vector3ic dest) {
        dest.set(this.x * x, this.y * y, this.z * z);
        return dest;
    }

    public long lengthSquared() {
        return x * x + y * y + z * z;
    }

    public double length() {
        return Math.sqrt(lengthSquared());
    }

    public double distance(IVector3i v) {
        return Math.sqrt(distanceSquared(v));
    }

    public double distance(int x, int y, int z) {
        return Math.sqrt(distanceSquared(x, y, z));
    }

    public long distanceSquared(IVector3i v) {
        int dx = this.x - v.x();
        int dy = this.y - v.y();
        int dz = this.z - v.z();
        return dx * dx + dy * dy + dz * dz;
    }

    public long distanceSquared(int x, int y, int z) {
        int dx = this.x - x;
        int dy = this.y - y;
        int dz = this.z - z;
        return dx * dx + dy * dy + dz * dz;
    }

    public Vector3ic zero() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
        return this;
    }

    public String toString() {
        return Runtime.formatNumbers(toString(Options.NUMBER_FORMAT));
    }

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

    public Vector3ic negate() {
        x = -x;
        y = -y;
        z = -z;
        return this;
    }

    public Vector3ic negate(Vector3ic dest) {
        dest.set(-x, -y, -z);
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
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Vector3ic))
            return false;
        Vector3ic other = (Vector3ic) obj;
        if (x != other.x())
            return false;
        if (y != other.y())
            return false;
        if (z != other.z())
            return false;
        return true;
    }
}
