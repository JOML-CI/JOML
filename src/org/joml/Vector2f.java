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

import org.joml.api.matrix.IMatrix3x2f;
import org.joml.api.vector.IVector2d;
import org.joml.api.vector.IVector2f;
import org.joml.api.vector.IVector2i;
import org.joml.api.vector.Vector2fc;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
//#ifdef __HAS_NIO__
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
//#endif
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Represents a 2D vector with single-precision.
 *
 * @author RGreenlees
 * @author Kai Burjack
 */
public class Vector2f extends Vector2fc implements Externalizable {

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
     * Create a new {@link Vector2f} and initialize its components to zero.
     */
    public Vector2f() {
    }

    /**
     * Create a new {@link Vector2f} and initialize both of its components with the given value.
     *
     * @param d the value of both components
     */
    public Vector2f(float d) {
        this(d, d);
    }

    /**
     * Create a new {@link Vector2f} and initialize its components to the given values.
     *
     * @param x the x component
     * @param y the y component
     */
    public Vector2f(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Create a new {@link Vector2f} and initialize its components to the one of the given vector.
     *
     * @param v the {@link IVector2f} to copy the values from
     */
    public Vector2f(IVector2f v) {
        x = v.x();
        y = v.y();
    }

    /**
     * Create a new {@link Vector2f} and initialize its components to the one of the given vector.
     *
     * @param v the {@link IVector2i} to copy the values from
     */
    public Vector2f(IVector2i v) {
        x = v.x();
        y = v.y();
    }

    //#ifdef __HAS_NIO__

    /**
     * Create a new {@link Vector2f} and read this vector from the supplied {@link ByteBuffer} at the current buffer
     * {@link ByteBuffer#position() position}.
     * <p>
     * This method will not increment the position of the given ByteBuffer.
     * <p>
     * In order to specify the offset into the ByteBuffer at which the vector is read, use {@link #Vector2f(int,
     * ByteBuffer)}, taking the absolute position as parameter.
     *
     * @param buffer values will be read in <tt>x, y</tt> order
     * @see #Vector2f(int, ByteBuffer)
     */
    public Vector2f(ByteBuffer buffer) {
        this(buffer.position(), buffer);
    }

    /**
     * Create a new {@link Vector2f} and read this vector from the supplied {@link ByteBuffer} starting at the specified
     * absolute buffer position/index.
     * <p>
     * This method will not increment the position of the given ByteBuffer.
     *
     * @param index  the absolute position into the ByteBuffer
     * @param buffer values will be read in <tt>x, y</tt> order
     */
    public Vector2f(int index, ByteBuffer buffer) {
        MemUtil.INSTANCE.get(this, index, buffer);
    }

    /**
     * Create a new {@link Vector2f} and read this vector from the supplied {@link FloatBuffer} at the current buffer
     * {@link FloatBuffer#position() position}.
     * <p>
     * This method will not increment the position of the given FloatBuffer.
     * <p>
     * In order to specify the offset into the FloatBuffer at which the vector is read, use {@link #Vector2f(int,
     * FloatBuffer)}, taking the absolute position as parameter.
     *
     * @param buffer values will be read in <tt>x, y</tt> order
     * @see #Vector2f(int, FloatBuffer)
     */
    public Vector2f(FloatBuffer buffer) {
        this(buffer.position(), buffer);
    }

    /**
     * Create a new {@link Vector2f} and read this vector from the supplied {@link FloatBuffer} starting at the
     * specified absolute buffer position/index.
     * <p>
     * This method will not increment the position of the given FloatBuffer.
     *
     * @param index  the absolute position into the FloatBuffer
     * @param buffer values will be read in <tt>x, y</tt> order
     */
    public Vector2f(int index, FloatBuffer buffer) {
        MemUtil.INSTANCE.get(this, index, buffer);
    }
    //#endif

    public Vector2fc set(float d) {
        return set(d, d);
    }

    public Vector2fc set(float x, float y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public Vector2fc set(IVector2f v) {
        x = v.x();
        y = v.y();
        return this;
    }

    public Vector2fc set(IVector2i v) {
        x = v.x();
        y = v.y();
        return this;
    }

    public Vector2fc set(IVector2d v) {
        x = (float) v.x();
        y = (float) v.y();
        return this;
    }

    //#ifdef __HAS_NIO__

    public Vector2fc set(ByteBuffer buffer) {
        return set(buffer.position(), buffer);
    }

    public Vector2fc set(int index, ByteBuffer buffer) {
        MemUtil.INSTANCE.get(this, index, buffer);
        return this;
    }

    public Vector2fc set(FloatBuffer buffer) {
        return set(buffer.position(), buffer);
    }

    public Vector2fc set(int index, FloatBuffer buffer) {
        MemUtil.INSTANCE.get(this, index, buffer);
        return this;
    }
    //#endif

    public Vector2fc setComponent(int component, float value) throws IllegalArgumentException {
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

    public Vector2fc perpendicular() {
        return set(y, x * -1);
    }

    public Vector2fc sub(IVector2f v) {
        x -= v.x();
        y -= v.y();
        return this;
    }

    public Vector2fc sub(float x, float y) {
        this.x -= x;
        this.y -= y;
        return this;
    }

    public Vector2fc normalize() {
        float invLength = (float) (1.0 / Math.sqrt(x * x + y * y));
        x *= invLength;
        y *= invLength;
        return this;
    }

    public Vector2fc normalize(float length) {
        float invLength = (float) (1.0 / Math.sqrt(x * x + y * y)) * length;
        x *= invLength;
        y *= invLength;
        return this;
    }

    public Vector2fc add(IVector2f v) {
        x += v.x();
        y += v.y();
        return this;
    }

    public Vector2fc add(float x, float y) {
        this.x += x;
        this.y += y;
        return this;
    }

    public Vector2fc zero() {
        this.x = 0.0f;
        this.y = 0.0f;
        return this;
    }

    public Vector2fc negate() {
        x = -x;
        y = -y;
        return this;
    }

    public Vector2fc mul(float scalar) {
        this.x *= scalar;
        this.y *= scalar;
        return this;
    }

    public Vector2fc mul(float x, float y) {
        this.x *= x;
        this.y *= y;
        return this;
    }

    public Vector2fc mul(IVector2f v) {
        x *= v.x();
        y *= v.y();
        return this;
    }

    public Vector2fc mulPosition(IMatrix3x2f mat) {
        return mulPosition(mat, this);
    }

    public Vector2fc mulDirection(IMatrix3x2f mat) {
        return mulPosition(mat, this);
    }

    public Vector2fc lerp(IVector2f other, float t) {
        return lerp(other, t, this);
    }

    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Float.floatToIntBits(x);
        result = prime * result + Float.floatToIntBits(y);
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Vector2fc))
            return false;
        Vector2fc other = (Vector2fc) obj;
        if (Float.floatToIntBits(x) != Float.floatToIntBits(other.x()))
            return false;
        if (Float.floatToIntBits(y) != Float.floatToIntBits(other.y()))
            return false;
        return true;
    }

    public String toString() {
        return Runtime.formatNumbers(toString(Options.NUMBER_FORMAT));
    }

    public String toString(NumberFormat formatter) {
        return "(" + formatter.format(x) + " " + formatter.format(y) + ")";
    }

    public Vector2fc fma(IVector2f a, IVector2f b) {
        x += a.x() * b.x();
        y += a.y() * b.y();
        return this;
    }

    public Vector2fc fma(float a, IVector2f b) {
        x += a * b.x();
        y += a * b.y();
        return this;
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeFloat(x);
        out.writeFloat(y);
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        x = in.readFloat();
        y = in.readFloat();
    }

    public float x() {
        return this.x;
    }

    public float y() {
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

    public FloatBuffer get(FloatBuffer buffer) {
        return get(buffer.position(), buffer);
    }

    public FloatBuffer get(int index, FloatBuffer buffer) {
        MemUtil.INSTANCE.put(this, index, buffer);
        return buffer;
    }
    //#endif

    public Vector2fc sub(IVector2f v, Vector2fc dest) {
        dest.set(x - v.x(), y - v.y());
        return dest;
    }

    public Vector2fc sub(float x, float y, Vector2fc dest) {
        dest.set(this.x - x, this.y - y);
        return dest;
    }

    public float dot(IVector2f v) {
        return x * v.x() + y * v.y();
    }

    public float angle(IVector2f v) {
        float dot = x * v.x() + y * v.y();
        float det = x * v.y() - y * v.x();
        return (float) Math.atan2(det, dot);
    }

    public float length() {
        return (float) Math.sqrt((x * x) + (y * y));
    }

    public float lengthSquared() {
        return x * x + y * y;
    }

    public float distance(IVector2f v) {
        return distance(v.x(), v.y());
    }

    public float distanceSquared(IVector2f v) {
        return distanceSquared(v.x(), v.y());
    }

    public float distance(float x, float y) {
        float dx = this.x - x;
        float dy = this.y - y;
        return (float) Math.sqrt(dx * dx + dy * dy);
    }

    public float distanceSquared(float x, float y) {
        float dx = this.x - x;
        float dy = this.y - y;
        return dx * dx + dy * dy;
    }

    public Vector2fc normalize(Vector2fc dest) {
        float invLength = (float) (1.0 / Math.sqrt(x * x + y * y));
        dest.set(x * invLength, y * invLength);
        return dest;
    }

    public Vector2fc normalize(float length, Vector2fc dest) {
        float invLength = (float) (1.0 / Math.sqrt(x * x + y * y)) * length;
        dest.set(x * invLength, y * invLength);
        return dest;
    }

    public Vector2fc add(IVector2f v, Vector2fc dest) {
        dest.set(x + v.x(), y + v.y());
        return dest;
    }

    public Vector2fc add(float x, float y, Vector2fc dest) {
        dest.set(this.x + x, this.y + y);
        return dest;
    }

    public Vector2fc negate(Vector2fc dest) {
        dest.set(-x, -y);
        return dest;
    }

    public Vector2fc mul(float scalar, Vector2fc dest) {
        dest.set(x * scalar, y * scalar);
        return dest;
    }

    public Vector2fc mul(float x, float y, Vector2fc dest) {
        dest.set(this.x * x, this.y * y);
        return dest;
    }

    public Vector2fc mul(IVector2f v, Vector2fc dest) {
        dest.set(x * v.x(), y * v.y());
        return dest;
    }

    public Vector2fc mulPosition(IMatrix3x2f mat, Vector2fc dest) {
        dest.set(mat.m00() * x + mat.m10() * y + mat.m20(),
                mat.m01() * x + mat.m11() * y + mat.m21());
        return dest;
    }

    public Vector2fc mulDirection(IMatrix3x2f mat, Vector2fc dest) {
        dest.set(mat.m00() * x + mat.m10() * y,
                mat.m01() * x + mat.m11() * y);
        return dest;
    }

    public Vector2fc lerp(IVector2f other, float t, Vector2fc dest) {
        dest.set(x + (other.x() - x) * t,
                y + (other.y() - y) * t);
        return dest;
    }

    public Vector2fc fma(IVector2f a, IVector2f b, Vector2fc dest) {
        dest.set(x + a.x() * b.x(),
                y + a.y() * b.y());
        return dest;
    }

    public Vector2fc fma(float a, IVector2f b, Vector2fc dest) {
        dest.set(x + a * b.x(),
                y + a * b.y());
        return dest;
    }
}
