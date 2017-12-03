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

import org.joml.api.vector.IVector2d;
import org.joml.api.vector.IVector2i;
import org.joml.api.vector.Vector2ic;

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
 * Represents a 2D vector with single-precision.
 *
 * @author RGreenlees
 * @author Kai Burjack
 * @author Hans Uhlig
 */
public class Vector2i extends Vector2ic implements Externalizable {

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
     *          the {@link IVector2i} to copy the values from
     */
    public Vector2i(IVector2i v) {
        x = v.x();
        y = v.y();
    }

    //#ifdef __HAS_NIO__
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
    //#endif

    @Override
    public Vector2ic set(int s) {
        this.x = s;
        this.y = s;
        return this;
    }

    @Override
    public Vector2ic set(int x, int y) {
        this.x = x;
        this.y = y;
        return this;
    }

    @Override
    public Vector2ic set(IVector2i v) {
        x = v.x();
        y = v.y();
        return this;
    }

    @Override
    public Vector2ic set(IVector2d v) {
        x = (int) v.x();
        y = (int) v.y();
        return this;
    }

    //#ifdef __HAS_NIO__
    @Override
    public Vector2ic set(ByteBuffer buffer) {
        return set(buffer.position(), buffer);
    }

    @Override
    public Vector2ic set(int index, ByteBuffer buffer) {
        MemUtil.INSTANCE.get(this, index, buffer);
        return this;
    }

    @Override
    public Vector2ic set(IntBuffer buffer) {
        return set(buffer.position(), buffer);
    }

    @Override
    public Vector2ic set(int index, IntBuffer buffer) {
        MemUtil.INSTANCE.get(this, index, buffer);
        return this;
    }
    //#endif

    @Override
    public Vector2ic setComponent(int component, int value) throws IllegalArgumentException {
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

    @Override
    public Vector2ic sub(IVector2i v) {
        x -= v.x();
        y -= v.y();
        return this;
    }

    @Override
    public Vector2ic sub(int x, int y) {
        this.x -= x;
        this.y -= y;
        return this;
    }

    @Override
    public Vector2ic add(IVector2i v) {
        x += v.x();
        y += v.y();
        return this;
    }

    @Override
    public Vector2ic add(int x, int y) {
        this.x += x;
        this.y += y;
        return this;
    }

    @Override
    public Vector2ic mul(int scalar) {
        x *= scalar;
        y *= scalar;
        return this;
    }

    @Override
    public Vector2ic mul(IVector2i v) {
        x += v.x();
        y += v.y();
        return this;
    }

    @Override
    public Vector2ic mul(int x, int y) {
        this.x *= x;
        this.y *= y;
        return this;
    }

    @Override
    public Vector2ic zero() {
        this.x = 0;
        this.y = 0;
        return this;
    }

    @Override
    public Vector2ic negate() {
        x = -x;
        y = -y;
        return this;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + x;
        result = prime * result + y;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Vector2ic))
            return false;
        Vector2ic other = (Vector2ic) obj;
        if (x != other.x())
            return false;
        if (y != other.y())
            return false;
        return true;
    }

    @Override
    public String toString() {
        return Runtime.formatNumbers(toString(Options.NUMBER_FORMAT));
    }

    @Override
    public String toString(NumberFormat formatter) {
        return "(" + formatter.format(x) + " " + formatter.format(y) + ")";
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeInt(x);
        out.writeInt(y);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        x = in.readInt();
        y = in.readInt();
    }

    @Override
    public int x() {
        return this.x;
    }

    @Override
    public int y() {
        return this.y;
    }

    //#ifdef __HAS_NIO__
    @Override
    public ByteBuffer get(ByteBuffer buffer) {
        return get(buffer.position(), buffer);
    }

    @Override
    public ByteBuffer get(int index, ByteBuffer buffer) {
        MemUtil.INSTANCE.put(this, index, buffer);
        return buffer;
    }

    @Override
    public IntBuffer get(IntBuffer buffer) {
        return get(buffer.position(), buffer);
    }

    @Override
    public IntBuffer get(int index, IntBuffer buffer) {
        MemUtil.INSTANCE.put(this, index, buffer);
        return buffer;
    }
    //#endif

    @Override
    public Vector2ic sub(IVector2i v, Vector2ic dest) {
        dest.set(x - v.x(), y - v.y());
        return dest;
    }

    @Override
    public Vector2ic sub(int x, int y, Vector2ic dest) {
        dest.set(this.x - x, this.y - y);
        return dest;
    }

    @Override
    public long lengthSquared() {
        return x * x + y * y;
    }

    @Override
    public double length() {
        return Math.sqrt(lengthSquared());
    }

    @Override
    public double distance(IVector2i v) {
        return Math.sqrt(distanceSquared(v));
    }

    @Override
    public double distance(int x, int y) {
        return Math.sqrt(distanceSquared(x, y));
    }

    @Override
    public long distanceSquared(IVector2i v) {
        int dx = this.x - v.x();
        int dy = this.y - v.y();
        return dx * dx + dy * dy;
    }

    @Override
    public long distanceSquared(int x, int y) {
        int dx = this.x - x;
        int dy = this.y - y;
        return dx * dx + dy * dy;
    }

    @Override
    public Vector2ic add(IVector2i v, Vector2ic dest) {
        dest.set(x + v.x(), y + v.y());
        return dest;
    }

    @Override
    public Vector2ic add(int x, int y, Vector2ic dest) {
        dest.set(this.x + x, this.y + y);
        return dest;
    }

    @Override
    public Vector2ic mul(int scalar, Vector2ic dest) {
        dest.set(x * scalar, y * scalar);
        return dest;
    }

    @Override
    public Vector2ic mul(IVector2i v, Vector2ic dest) {
        dest.set(x * v.x(), y * v.y());
        return dest;
    }

    @Override
    public Vector2ic mul(int x, int y, Vector2ic dest) {
        dest.set(this.x * x, this.y * y);
        return dest;
    }

    @Override
    public Vector2ic negate(Vector2ic dest) {
        dest.set(-x, -y);
        return dest;
    }
}
