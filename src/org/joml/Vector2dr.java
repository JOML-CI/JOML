/*
 * (C) Copyright 2015 Richard Greenlees

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

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Abstract base class for representing readable 2D vectors with double-precision.
 *
 * @author RGreenlees
 * @author Kai Burjack
 */
public abstract class Vector2dr {

    /**
     * @return The x component of the vector.
     */
    public abstract double x();

    /**
     * @return The y component of the vector.
     */
    public abstract double y();

    /**
     * Store one perpendicular vector of <code>v</code> in <code>dest</code>.
     *
     * @param v
     *          the vector to build one perpendicular vector of
     * @param dest
     *          will hold the result
     */
    public static void perpendicular(Vector2dr v, Vector2d dest) {
        dest.x = v.y();
        dest.y = v.x() * -1;
    }

    /**
     * Store this vector into the supplied {@link java.nio.ByteBuffer} at the current
     * buffer {@link java.nio.ByteBuffer#position() position}.
     * <p>
     * This method will not increment the position of the given ByteBuffer.
     * <p>
     * In order to specify the offset into the ByteBuffer at which
     * the vector is stored, use {@link #get(int, java.nio.ByteBuffer)}, taking
     * the absolute position as parameter.
     *
     * @param buffer
     *          will receive the values of this vector in <tt>x, y</tt> order
     * @return the passed in buffer
     * @see #get(int, java.nio.ByteBuffer)
     */
    public ByteBuffer get(ByteBuffer buffer) {
        return get(buffer.position(), buffer);
    }

    /**
     * Store this vector into the supplied {@link java.nio.ByteBuffer} starting at the specified
     * absolute buffer position/index.
     * <p>
     * This method will not increment the position of the given ByteBuffer.
     *
     * @param index
     *          the absolute position into the ByteBuffer
     * @param buffer
     *          will receive the values of this vector in <tt>x, y</tt> order
     * @return the passed in buffer
     */
    public ByteBuffer get(int index, ByteBuffer buffer) {
        buffer.putDouble(index,      x());
        buffer.putDouble(index + 8,  y());
        return buffer;
    }

    /**
     * Store this vector into the supplied {@link java.nio.DoubleBuffer} at the current
     * buffer {@link java.nio.DoubleBuffer#position() position}.
     * <p>
     * This method will not increment the position of the given DoubleBuffer.
     * <p>
     * In order to specify the offset into the DoubleBuffer at which
     * the vector is stored, use {@link #get(int, java.nio.DoubleBuffer)}, taking
     * the absolute position as parameter.
     *
     * @param buffer
     *          will receive the values of this vector in <tt>x, y</tt> order
     * @return the passed in buffer
     * @see #get(int, java.nio.DoubleBuffer)
     */
    public DoubleBuffer get(DoubleBuffer buffer) {
        return get(buffer.position(), buffer);
    }

    /**
     * Store this vector into the supplied {@link java.nio.DoubleBuffer} starting at the specified
     * absolute buffer position/index.
     * <p>
     * This method will not increment the position of the given DoubleBuffer.
     *
     * @param index
     *          the absolute position into the DoubleBuffer
     * @param buffer
     *          will receive the values of this vector in <tt>x, y</tt> order
     * @return the passed in buffer
     */
    public DoubleBuffer get(int index, DoubleBuffer buffer) {
        buffer.put(index,      x());
        buffer.put(index + 1,  y());
        return buffer;
    }

    /**
     * Subtract <tt>(x, y)</tt> from this vector and store the result in <code>dest</code>.
     *
     * @param x
     *          the x component to subtract
     * @param y
     *          the y component to subtract
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Vector2d sub(double x, double y, Vector2d dest) {
        dest.x = this.x() - x;
        dest.y = this.y() - y;
        return dest;
    }

    /**
     * Subtract <code>v</code> from <code>this</code> vector and store the result in <code>dest</code>.
     *
     * @param v
     *          the vector to subtract
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Vector2d sub(Vector2dr v, Vector2d dest) {
        dest.x = x() - v.x();
        dest.y = y() - v.y();
        return dest;
    }

    /**
     * Subtract <code>v</code> from <code>this</code> vector and store the result in <code>dest</code>.
     *
     * @param v
     *          the vector to subtract
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Vector2d sub(Vector2fr v, Vector2d dest) {
        dest.x = x() + v.x();
        dest.y = y() + v.y();
        return dest;
    }

    /**
     * Subtract <code>b</code> from <code>a</code> and store the result in <code>dest</code>.
     *
     * @param a
     *          the vector to subtract from
     * @param b
     *          the vector to subtract
     * @param dest
     *          will hold the result
     */
    public static void sub(Vector2fr a, Vector2dr b, Vector2d dest) {
        dest.x = a.x() - b.x();
        dest.y = a.y() - b.y();
    }

    /**
     * Return the dot product of this vector and <code>v</code>.
     *
     * @param v
     *          the other vector
     * @return the dot product
     */
    public double dot(Vector2dr v) {
        return x() * v.x() + y() * v.y();
    }

    /**
     * Return the angle between this vector and the supplied vector.
     *
     * @param v
     *          the other vector
     * @return the angle, in radians
     */
    public double angle(Vector2dr v) {
        double dot = x()*v.x() + y()*v.y();
        double det = x()*v.y() - y()*v.x();
        return Math.atan2(det, dot);
    }

    /**
     * Return the length of this vector.
     *
     * @return the length
     */
    public double length() {
        return Math.sqrt(x() * x() + y() * y());
    }

    /**
     * Return the distance between <code>this</code> and <code>v</code>.
     *
     * @param v
     *          the other vector
     * @return the euclidean distance
     */
    public double distance(Vector2dr v) {
        double dx = v.x() - x();
        double dy = v.y() - y();
        return Math.sqrt(dx * dx + dy * dy);
    }

    /**
     * Return the distance between <code>this</code> and <code>v</code>.
     *
     * @param v
     *          the other vector
     * @return the euclidean distance
     */
    public double distance(Vector2fr v) {
        double dx = v.x() - x();
        double dy = v.y() - y();
        return Math.sqrt(dx * dx + dy * dy);
    }

    /**
     * Normalize this vector and store the result in <code>dest</code>.
     *
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Vector2d normalize(Vector2d dest) {
        double invLength = 1.0 / Math.sqrt(x() * x() + y() * y());
        dest.x = x() * invLength;
        dest.y = y() * invLength;
        return dest;
    }

    /**
     * Add <code>(x, y)</code> to this vector and store the result in <code>dest</code>.
     *
     * @param x
     *          the x component to add
     * @param y
     *          the y component to add
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Vector2d add(double x, double y, Vector2d dest) {
        dest.x = this.x() + x;
        dest.y = this.y() + y;
        return dest;
    }

    /**
     * Add <code>v</code> to this vector and store the result in <code>dest</code>.
     *
     * @param v
     *          the vector to add
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Vector2d add(Vector2dr v, Vector2d dest) {
        dest.x = x() + v.x();
        dest.y = y() + v.y();
        return dest;
    }

    /**
     * Add <code>v</code> to this vector and store the result in <code>dest</code>.
     *
     * @param v
     *          the vector to add
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Vector2d add(Vector2fr v, Vector2d dest) {
        dest.x = x() + v.x();
        dest.y = y() + v.y();
        return dest;
    }

    /**
     * Add <code>a</code> to <code>b</code> and store the result in <code>dest</code>.
     *
     * @param a
     *          the first addend
     * @param b
     *          the second addend
     * @param dest
     *          will hold the result
     */
    public static void add(Vector2fr a, Vector2dr b, Vector2d dest) {
        dest.x = a.x() + b.x();
        dest.y = a.y() + b.y();
    }

    /**
     * Negate this vector and store the result in <code>dest</code>.
     *
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Vector2d negate(Vector2d dest) {
        dest.x = -x();
        dest.y = -y();
        return dest;
    }

    /**
     * Linearly interpolate <code>this</code> and <code>other</code> using the given interpolation factor <code>t</code>
     * and store the result in <code>dest</code>.
     * <p>
     * If <code>t</code> is <tt>0.0</tt> then the result is <code>this</code>. If the interpolation factor is <code>1.0</code>
     * then the result is <code>other</code>.
     *
     * @param other
     *          the other vector
     * @param t
     *          the interpolation factor between 0.0 and 1.0
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Vector2d lerp(Vector2dr other, double t, Vector2d dest) {
        dest.x = (1.0 - t) * x() + t * other.x();
        dest.y = (1.0 - t) * y() + t * other.y();
        return dest;
    }

    public int hashCode() {
        final int prime = 31;
        int result = 1;
        long temp;
        temp = Double.doubleToLongBits(x());
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y());
        result = prime * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Vector2dr) {
            Vector2dr other = (Vector2dr) obj;
            return Double.doubleToLongBits(x()) == Double.doubleToLongBits(other.x())
                    && Double.doubleToLongBits(y()) == Double.doubleToLongBits(other.y());
        }
        return false;
    }

    /**
     * Return a string representation of this vector.
     * <p>
     * This method creates a new {@link java.text.DecimalFormat} on every invocation with the format string "<tt> 0.000E0;-</tt>".
     *
     * @return the string representation
     */
    public String toString() {
        DecimalFormat formatter = new DecimalFormat(" 0.000E0;-"); //$NON-NLS-1$
        return toString(formatter).replaceAll("E(\\d+)", "E+$1"); //$NON-NLS-1$ //$NON-NLS-2$
    }

    /**
     * Return a string representation of this vector by formatting the vector components with the given {@link java.text.NumberFormat}.
     *
     * @param formatter
     *          the {@link java.text.NumberFormat} used to format the vector components with
     * @return the string representation
     */
    public String toString(NumberFormat formatter) {
        return "(" + formatter.format(x()) + " " + formatter.format(y()) + ")"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
    }

    /**
     * Add the component-wise multiplication of <code>a * b</code> to this vector
     * and store the result in <code>dest</code>.
     *
     * @param a
     *          the first multiplicand
     * @param b
     *          the second multiplicand
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Vector2d fma(Vector2dr a, Vector2dr b, Vector2d dest) {
        dest.x = x() + a.x() * b.x();
        dest.y = y() + a.y() * b.y();
        return dest;
    }

    /**
     * Add the component-wise multiplication of <code>a * b</code> to this vector
     * and store the result in <code>dest</code>.
     *
     * @param a
     *          the first multiplicand
     * @param b
     *          the second multiplicand
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Vector2d fma(double a, Vector2dr b, Vector2d dest) {
        dest.x = x() + a * b.x();
        dest.y = y() + a * b.y();
        return dest;
    }
}
