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

import org.joml.api.matrix.IMatrix3x2d;
import org.joml.api.vector.IVector2d;
import org.joml.api.vector.IVector2f;
import org.joml.api.vector.IVector2i;
import org.joml.api.vector.Vector2dc;

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
 * Represents a 2D vector with double-precision.
 *
 * @author RGreenlees
 * @author Kai Burjack
 */
public class Vector2d extends Vector2dc implements Externalizable {

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
     * Create a new {@link Vector2d} and initialize its components to zero.
     */
    public Vector2d() {
    }

    /**
     * Create a new {@link Vector2d} and initialize both of its components with the given value.
     *
     * @param d the value of both components
     */
    public Vector2d(double d) {
        this(d, d);
    }

    /**
     * Create a new {@link Vector2d} and initialize its components to the given values.
     *
     * @param x the x value
     * @param y the y value
     */
    public Vector2d(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Create a new {@link Vector2d} and initialize its components to the one of the given vector.
     *
     * @param v the {@link IVector2d} to copy the values from
     */
    public Vector2d(IVector2d v) {
        x = v.x();
        y = v.y();
    }

    /**
     * Create a new {@link Vector2d} and initialize its components to the one of the given vector.
     *
     * @param v the {@link IVector2f} to copy the values from
     */
    public Vector2d(IVector2f v) {
        x = v.x();
        y = v.y();
    }

    /**
     * Create a new {@link Vector2d} and initialize its components to the one of the given vector.
     *
     * @param v the {@link IVector2i} to copy the values from
     */
    public Vector2d(IVector2i v) {
        x = v.x();
        y = v.y();
    }

    //#ifdef __HAS_NIO__

    /**
     * Create a new {@link Vector2d} and read this vector from the supplied {@link ByteBuffer} at the current buffer
     * {@link ByteBuffer#position() position}.
     * <p>
     * This method will not increment the position of the given ByteBuffer.
     * <p>
     * In order to specify the offset into the ByteBuffer at which the vector is read, use {@link #Vector2d(int,
     * ByteBuffer)}, taking the absolute position as parameter.
     *
     * @param buffer values will be read in <tt>x, y</tt> order
     * @see #Vector2d(int, ByteBuffer)
     */
    public Vector2d(ByteBuffer buffer) {
        this(buffer.position(), buffer);
    }

    /**
     * Create a new {@link Vector2d} and read this vector from the supplied {@link ByteBuffer} starting at the specified
     * absolute buffer position/index.
     * <p>
     * This method will not increment the position of the given ByteBuffer.
     *
     * @param index  the absolute position into the ByteBuffer
     * @param buffer values will be read in <tt>x, y</tt> order
     */
    public Vector2d(int index, ByteBuffer buffer) {
        MemUtil.INSTANCE.get(this, index, buffer);
    }

    /**
     * Create a new {@link Vector2d} and read this vector from the supplied {@link DoubleBuffer} at the current buffer
     * {@link DoubleBuffer#position() position}.
     * <p>
     * This method will not increment the position of the given DoubleBuffer.
     * <p>
     * In order to specify the offset into the DoubleBuffer at which the vector is read, use {@link #Vector2d(int,
     * DoubleBuffer)}, taking the absolute position as parameter.
     *
     * @param buffer values will be read in <tt>x, y</tt> order
     * @see #Vector2d(int, DoubleBuffer)
     */
    public Vector2d(DoubleBuffer buffer) {
        this(buffer.position(), buffer);
    }

    /**
     * Create a new {@link Vector2d} and read this vector from the supplied {@link DoubleBuffer} starting at the
     * specified absolute buffer position/index.
     * <p>
     * This method will not increment the position of the given DoubleBuffer.
     *
     * @param index  the absolute position into the DoubleBuffer
     * @param buffer values will be read in <tt>x, y</tt> order
     */
    public Vector2d(int index, DoubleBuffer buffer) {
        MemUtil.INSTANCE.get(this, index, buffer);
    }
    //#endif

    public Vector2dc set(double d) {
        return set(d, d);
    }

    public Vector2dc set(double x, double y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public Vector2dc set(IVector2d v) {
        x = v.x();
        y = v.y();
        return this;
    }

    public Vector2dc set(IVector2f v) {
        x = v.x();
        y = v.y();
        return this;
    }

    public Vector2dc set(IVector2i v) {
        x = v.x();
        y = v.y();
        return this;
    }

    //#ifdef __HAS_NIO__

    public Vector2dc set(ByteBuffer buffer) {
        return set(buffer.position(), buffer);
    }

    public Vector2dc set(int index, ByteBuffer buffer) {
        MemUtil.INSTANCE.get(this, index, buffer);
        return this;
    }

    public Vector2dc set(DoubleBuffer buffer) {
        return set(buffer.position(), buffer);
    }

    public Vector2dc set(int index, DoubleBuffer buffer) {
        MemUtil.INSTANCE.get(this, index, buffer);
        return this;
    }
    //#endif

    public Vector2dc setComponent(int component, double value) throws IllegalArgumentException {
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

    public Vector2dc perpendicular() {
        return set(y, x * -1);
    }

    public Vector2dc sub(IVector2d v) {
        x -= v.x();
        y -= v.y();
        return this;
    }

    public Vector2dc sub(double x, double y) {
        this.x -= x;
        this.y -= y;
        return this;
    }

    public Vector2dc sub(IVector2f v) {
        x -= v.x();
        y -= v.y();
        return this;
    }

    public Vector2dc mul(double scalar) {
        this.x *= scalar;
        this.y *= scalar;
        return this;
    }

    public Vector2dc mul(double x, double y) {
        this.x *= x;
        this.y *= y;
        return this;
    }

    public Vector2dc mul(IVector2d v) {
        x *= v.x();
        y *= v.y();
        return this;
    }

    public Vector2dc mulPosition(IMatrix3x2d mat) {
        return mulPosition(mat, this);
    }

    public Vector2dc mulDirection(IMatrix3x2d mat) {
        return mulPosition(mat, this);
    }

    public Vector2dc normalize() {
        double invLength = 1.0 / Math.sqrt(x * x + y * y);
        x *= invLength;
        y *= invLength;
        return this;
    }

    public Vector2dc normalize(double length) {
        double invLength = 1.0 / Math.sqrt(x * x + y * y) * length;
        x *= invLength;
        y *= invLength;
        return this;
    }

    public Vector2dc add(IVector2d v) {
        x += v.x();
        y += v.y();
        return this;
    }

    public Vector2dc add(double x, double y) {
        this.x += x;
        this.y += y;
        return this;
    }

    public Vector2dc add(IVector2f v) {
        x += v.x();
        y += v.y();
        return this;
    }

    public Vector2dc zero() {
        x = 0.0;
        y = 0.0;
        return this;
    }

    public Vector2dc negate() {
        x = -x;
        y = -y;
        return this;
    }

    public Vector2dc lerp(IVector2d other, double t) {
        return lerp(other, t, this);
    }

    public int hashCode() {
        final int prime = 31;
        int result = 1;
        long temp;
        temp = Double.doubleToLongBits(x);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Vector2dc))
            return false;
        Vector2dc other = (Vector2dc) obj;
        if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x()))
            return false;
        if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y()))
            return false;
        return true;
    }

    public String toString() {
        return Runtime.formatNumbers(toString(Options.NUMBER_FORMAT));
    }

    public String toString(NumberFormat formatter) {
        return "(" + formatter.format(x) + " " + formatter.format(y) + ")";
    }

    public Vector2dc fma(IVector2d a, IVector2d b) {
        x += a.x() * b.x();
        y += a.y() * b.y();
        return this;
    }

    public Vector2dc fma(double a, IVector2d b) {
        x += a * b.x();
        y += a * b.y();
        return this;
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeDouble(x);
        out.writeDouble(y);
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        x = in.readDouble();
        y = in.readDouble();
    }

    public double x() {
        return this.x;
    }

    public double y() {
        return this.y;
    }

    //#ifdef __HAS_NIO__

    public ByteBuffer get(ByteBuffer buffer) {
        return get(buffer.position(), buffer);
    }

    public ByteBuffer get(int index, ByteBuffer buffer) {
        MemUtil.INSTANCE.put(this, index, buffer);
        return buffer;
    }

    public DoubleBuffer get(DoubleBuffer buffer) {
        return get(buffer.position(), buffer);
    }

    public DoubleBuffer get(int index, DoubleBuffer buffer) {
        MemUtil.INSTANCE.put(this, index, buffer);
        return buffer;
    }
    //#endif

    public Vector2dc sub(double x, double y, Vector2dc dest) {
        dest.set(this.x - x, this.y - y);
        return dest;
    }

    public Vector2dc sub(IVector2d v, Vector2dc dest) {
        dest.set(x - v.x(), y - v.y());
        return dest;
    }

    public Vector2dc sub(IVector2f v, Vector2dc dest) {
        dest.set(x - v.x(), y - v.y());
        return dest;
    }

    public Vector2dc mul(double scalar, Vector2dc dest) {
        dest.set(x * scalar, y * scalar);
        return dest;
    }

    public Vector2dc mul(double x, double y, Vector2dc dest) {
        dest.set(this.x * x, this.y * y);
        return dest;
    }

    public Vector2dc mul(IVector2d v, Vector2dc dest) {
        dest.set(x * v.x(), y * v.y());
        return dest;
    }

    public Vector2dc mulPosition(IMatrix3x2d mat, Vector2dc dest) {
        dest.set(mat.m00() * x + mat.m10() * y + mat.m20(),
                mat.m01() * x + mat.m11() * y + mat.m21());
        return dest;
    }

    public Vector2dc mulDirection(IMatrix3x2d mat, Vector2dc dest) {
        dest.set(mat.m00() * x + mat.m10() * y,
                mat.m01() * x + mat.m11() * y);
        return dest;
    }

    public double dot(IVector2d v) {
        return x * v.x() + y * v.y();
    }

    public double angle(IVector2d v) {
        double dot = x * v.x() + y * v.y();
        double det = x * v.y() - y * v.x();
        return Math.atan2(det, dot);
    }

    public double length() {
        return Math.sqrt(x * x + y * y);
    }

    public double distance(IVector2d v) {
        return distance(v.x(), v.y());
    }

    public double distance(IVector2f v) {
        return distance(v.x(), v.y());
    }

    public double distance(double x, double y) {
        double dx = this.x - x;
        double dy = this.y - y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    public Vector2dc normalize(Vector2dc dest) {
        double invLength = 1.0 / Math.sqrt(x * x + y * y);
        dest.set(x * invLength, y * invLength);
        return dest;
    }

    public Vector2dc normalize(double length, Vector2dc dest) {
        double invLength = 1.0 / Math.sqrt(x * x + y * y) * length;
        dest.set(x * invLength, y * invLength);
        return dest;
    }

    public Vector2dc add(double x, double y, Vector2dc dest) {
        dest.set(this.x + x, this.y + y);
        return dest;
    }

    public Vector2dc add(IVector2d v, Vector2dc dest) {
        dest.set(x + v.x(), y + v.y());
        return dest;
    }

    public Vector2dc add(IVector2f v, Vector2dc dest) {
        dest.set(x + v.x(), y + v.y());
        return dest;
    }

    public Vector2dc negate(Vector2dc dest) {
        dest.set(-x, -y);
        return dest;
    }

    public Vector2dc lerp(IVector2d other, double t, Vector2dc dest) {
        dest.set(x + (other.x() - x) * t, y + (other.y() - y) * t);
        return dest;
    }

    public Vector2dc fma(IVector2d a, IVector2d b, Vector2dc dest) {
        dest.set(x + a.x() * b.x(), y + a.y() * b.y());
        return dest;
    }

    public Vector2dc fma(double a, IVector2d b, Vector2dc dest) {
        dest.set(x + a * b.x(), y + a * b.y());
        return dest;
    }
}
