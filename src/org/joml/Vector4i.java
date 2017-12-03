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
import org.joml.api.vector.IVector3i;
import org.joml.api.vector.IVector4i;
import org.joml.api.vector.Vector4ic;

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
 * Contains the definition of a Vector comprising 4 ints and associated
 * transformations.
 *
 * @author Richard Greenlees
 * @author Kai Burjack
 * @author Hans Uhlig
 */
public class Vector4i extends Vector4ic implements Externalizable {

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
     * The w component of the vector.
     */
    public int w;

    /**
     * Create a new {@link Vector4i} of <code>(0, 0, 0, 1)</code>.
     */
    public Vector4i() {
        this.w = 1;
    }

    /**
     * Create a new {@link Vector4i} with the same values as <code>v</code>.
     *
     * @param v
     *          the {@link IVector4i} to copy the values from
     */
    public Vector4i(IVector4i v) {
        if (v instanceof Vector4i) {
            MemUtil.INSTANCE.copy((Vector4i) v, this);            
        } else {
            this.x = v.x();
            this.y = v.y();
            this.z = v.z();
            this.w = v.w();
        }
    }

    /**
     * Create a new {@link Vector4i} with the first three components from the
     * given <code>v</code> and the given <code>w</code>.
     *
     * @param v
     *          the {@link IVector3i}
     * @param w
     *          the w component
     */
    public Vector4i(IVector3i v, int w) {
        this.x = v.x();
        this.y = v.y();
        this.z = v.z();
        this.w = w;
    }

    /**
     * Create a new {@link Vector4i} with the first two components from the
     * given <code>v</code> and the given <code>z</code>, and <code>w</code>.
     *
     * @param v
     *          the {@link IVector2i}
     * @param z
     *          the z component
     * @param w
     *          the w component
     */
    public Vector4i(IVector2i v, int z, int w) {
        this.x = v.x();
        this.y = v.y();
        this.z = z;
        this.w = w;
    }

    /**
     * Create a new {@link Vector4i} and initialize all four components with the
     * given value.
     *
     * @param s
     *          scalar value of all four components
     */
    public Vector4i(int s) {
        MemUtil.INSTANCE.broadcast(s, this);
    }

    /**
     * Create a new {@link Vector4i} with the given component values.
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
    public Vector4i(int x, int y, int z, int w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

//#ifdef __HAS_NIO__
    /**
     * Create a new {@link Vector4i} and read this vector from the supplied
     * {@link ByteBuffer} at the current buffer
     * {@link ByteBuffer#position() position}.
     * <p>
     * This method will not increment the position of the given ByteBuffer.
     * <p>
     * In order to specify the offset into the ByteBuffer at which the vector is
     * read, use {@link #Vector4i(int, ByteBuffer)}, taking the absolute
     * position as parameter.
     *
     * @see #Vector4i(int, ByteBuffer)
     * 
     * @param buffer
     *          values will be read in <tt>x, y, z, w</tt> order
     */
    public Vector4i(ByteBuffer buffer) {
        this(buffer.position(), buffer);
    }

    /**
     * Create a new {@link Vector4i} and read this vector from the supplied
     * {@link ByteBuffer} starting at the specified absolute buffer
     * position/index.
     * <p>
     * This method will not increment the position of the given ByteBuffer.
     *
     * @param index
     *          the absolute position into the ByteBuffer
     * @param buffer
     *          values will be read in <tt>x, y, z, w</tt> order
     */
    public Vector4i(int index, ByteBuffer buffer) {
        MemUtil.INSTANCE.get(this, index, buffer);
    }

    /**
     * Create a new {@link Vector4i} and read this vector from the supplied
     * {@link IntBuffer} at the current buffer
     * {@link IntBuffer#position() position}.
     * <p>
     * This method will not increment the position of the given IntBuffer.
     * <p>
     * In order to specify the offset into the IntBuffer at which the vector is
     * read, use {@link #Vector4i(int, IntBuffer)}, taking the absolute position
     * as parameter.
     *
     * @see #Vector4i(int, IntBuffer)
     *
     * @param buffer
     *          values will be read in <tt>x, y, z, w</tt> order
     */
    public Vector4i(IntBuffer buffer) {
        this(buffer.position(), buffer);
    }

    /**
     * Create a new {@link Vector4i} and read this vector from the supplied
     * {@link IntBuffer} starting at the specified absolute buffer
     * position/index.
     * <p>
     * This method will not increment the position of the given IntBuffer.
     *
     * @param index
     *          the absolute position into the IntBuffer
     * @param buffer
     *          values will be read in <tt>x, y, z, w</tt> order
     */
    public Vector4i(int index, IntBuffer buffer) {
        MemUtil.INSTANCE.get(this, index, buffer);
    }
//#endif

    @Override
    public int x() {
        return this.x;
    }

    @Override
    public int y() {
        return this.y;
    }

    @Override
    public int z() {
        return this.z;
    }

    @Override
    public int w() {
        return this.w;
    }

    @Override
    public Vector4ic set(IVector4i v) {
        if (v instanceof Vector4i) {
            MemUtil.INSTANCE.copy((Vector4i) v, this);
        } else {
            this.x = v.x();
            this.y = v.y();
            this.z = v.z();
            this.w = v.w();
        }
        return this;
    }

    @Override
    public Vector4ic set(IVector3i v, int w) {
        this.x = v.x();
        this.y = v.y();
        this.z = v.z();
        this.w = w;
        return this;
    }

    @Override
    public Vector4ic set(IVector2i v, int z, int w) {
        this.x = v.x();
        this.y = v.y();
        this.z = z;
        this.w = w;
        return this;
    }

    @Override
    public Vector4ic set(int s) {
        MemUtil.INSTANCE.broadcast(s, this);
        return this;
    }

    @Override
    public Vector4ic set(int x, int y, int z, int w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
        return this;
    }

//#ifdef __HAS_NIO__
    @Override
    public Vector4ic set(ByteBuffer buffer) {
        return set(buffer.position(), buffer);
    }

    @Override
    public Vector4ic set(int index, ByteBuffer buffer) {
        MemUtil.INSTANCE.get(this, index, buffer);
        return this;
    }

    @Override
    public Vector4ic set(IntBuffer buffer) {
        return set(buffer.position(), buffer);
    }

    @Override
    public Vector4ic set(int index, IntBuffer buffer) {
        MemUtil.INSTANCE.get(this, index, buffer);
        return this;
    }
//#endif

    @Override
    public Vector4ic setComponent(int component, int value) throws IllegalArgumentException {
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
    @Override
    public IntBuffer get(IntBuffer buffer) {
        return get(buffer.position(), buffer);
    }

    @Override
    public IntBuffer get(int index, IntBuffer buffer) {
        MemUtil.INSTANCE.put(this, index, buffer);
        return buffer;
    }

    @Override
    public ByteBuffer get(ByteBuffer buffer) {
        return get(buffer.position(), buffer);
    }

    @Override
    public ByteBuffer get(int index, ByteBuffer buffer) {
        MemUtil.INSTANCE.put(this, index, buffer);
        return buffer;
    }
//#endif

    @Override
    public Vector4ic sub(IVector4i v) {
        x -= v.x();
        y -= v.y();
        z -= v.z();
        w -= v.w();
        return this;
    }

    @Override
    public Vector4ic sub(int x, int y, int z, int w) {
        this.x -= x;
        this.y -= y;
        this.z -= z;
        this.w -= w;
        return this;
    }

    @Override
    public Vector4ic sub(IVector4i v, Vector4ic dest) {
        dest.set(x - v.x(), y - v.y(), z - v.z(), w - v.w());
        return dest;
    }

    @Override
    public Vector4ic sub(int x, int y, int z, int w, Vector4ic dest) {
        dest.set(this.x - x, this.y - y, this.z - z, this.w - w);
        return dest;
    }

    @Override
    public Vector4ic add(IVector4i v) {
        x += v.x();
        y += v.y();
        z += v.z();
        w += v.w();
        return this;
    }

    @Override
    public Vector4ic add(IVector4i v, Vector4ic dest) {
        dest.set(x + v.x(), y + v.y(), z + v.z(), w + v.w());
        return dest;
    }

    @Override
    public Vector4ic add(int x, int y, int z, int w) {
        this.x += x;
        this.y += y;
        this.z += z;
        this.w += w;
        return this;
    }

    @Override
    public Vector4ic add(int x, int y, int z, int w, Vector4ic dest) {
        dest.set(this.x + x, this.y + y, this.z + z, this.w + w);
        return dest;
    }

    @Override
    public Vector4ic mul(IVector4i v) {
        x *= v.x();
        y *= v.y();
        z *= v.z();
        w *= v.w();
        return this;
    }

    @Override
    public Vector4ic mul(IVector4i v, Vector4ic dest) {
        dest.set(x * v.x(), y * v.y(), z * v.z(), w * v.w());
        return dest;
    }

    @Override
    public Vector4ic div(IVector4i v) {
        x /= v.x();
        y /= v.y();
        z /= v.z();
        w /= v.w();
        return this;
    }

    @Override
    public Vector4ic div(IVector4i v, Vector4ic dest) {
        dest.set(x / v.x(), y / v.y(), z / v.z(), w / v.w());
        return dest;
    }

    @Override
    public Vector4ic mul(float scalar) {
        x *= scalar;
        y *= scalar;
        z *= scalar;
        w *= scalar;
        return this;
    }

    @Override
    public Vector4ic mul(float scalar, Vector4ic dest) {
        dest.set((int) (x * scalar), (int) (y * scalar), (int) (z * scalar), (int) (w * scalar));
        return dest;
    }

    @Override
    public Vector4ic div(int scalar) {
        x /= scalar;
        y /= scalar;
        z /= scalar;
        w /= scalar;
        return this;
    }

    @Override
    public Vector4ic div(float scalar, Vector4ic dest) {
        dest.set((int) (x / scalar), (int) (y / scalar), (int) (z / scalar), (int) (w / scalar));
        return dest;
    }

    @Override
    public long lengthSquared() {
        return x * x + y * y + z * z + w * w;
    }

    @Override
    public double length() {
        return Math.sqrt(lengthSquared());
    }

    @Override
    public double distance(IVector4i v) {
        return Math.sqrt(distanceSquared(v));
    }

    @Override
    public double distance(int x, int y, int z, int w) {
        return Math.sqrt(distanceSquared(x, y, z, w));
    }

    @Override
    public int distanceSquared(IVector4i v) {
        int dx = this.x - v.x();
        int dy = this.y - v.y();
        int dz = this.z - v.z();
        int dw = this.w - v.w();
        return dx * dx + dy * dy + dz * dz + dw * dw;
    }

    @Override
    public int distanceSquared(int x, int y, int z, int w) {
        int dx = this.x - x;
        int dy = this.y - y;
        int dz = this.z - z;
        int dw = this.w - w;
        return dx * dx + dy * dy + dz * dz + dw * dw;
    }

    @Override
    public int dot(IVector4i v) {
        return x * v.x() + y * v.y() + z * v.z() + w * v.w();
    }

    @Override
    public Vector4ic zero() {
        MemUtil.INSTANCE.zero(this);
        return this;
    }

    @Override
    public Vector4ic negate() {
        x = -x;
        y = -y;
        z = -z;
        w = -w;
        return this;
    }

    @Override
    public Vector4ic negate(Vector4ic dest) {
        dest.set(-x, -y, -z, -w);
        return dest;
    }

    @Override
    public String toString() {
        return Runtime.formatNumbers(toString(Options.NUMBER_FORMAT));
    }

    @Override
    public String toString(NumberFormat formatter) {
        return "(" + formatter.format(x) + " " + formatter.format(y) + " " + formatter.format(z) + " " + formatter.format(w) + ")";
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeInt(x);
        out.writeInt(y);
        out.writeInt(z);
        out.writeInt(w);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        x = in.readInt();
        y = in.readInt();
        z = in.readInt();
        w = in.readInt();
    }

    @Override
    public Vector4ic min(IVector4i v) {
        this.x = Math.min(x, v.x());
        this.y = Math.min(y, v.y());
        this.z = Math.min(z, v.z());
        this.w = Math.min(w, v.w());
        return this;
    }

    @Override
    public Vector4ic max(IVector4i v) {
        this.x = Math.max(x, v.x());
        this.y = Math.max(y, v.y());
        this.z = Math.max(z, v.z());
        this.w = Math.min(w, v.w());
        return this;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + x;
        result = prime * result + y;
        result = prime * result + z;
        result = prime * result + w;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Vector4ic))
            return false;
        Vector4ic other = (Vector4ic) obj;
        if (x != other.x())
            return false;
        if (y != other.y())
            return false;
        if (z != other.z())
            return false;
        if (w != other.w())
            return false;
        return true;
    }
}
