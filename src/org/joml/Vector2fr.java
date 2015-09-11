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
import java.nio.FloatBuffer;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Abstract base class for readable, 2D vectors with single-precision.
 * Provides the common implementations for non-mutating methods.
 *
 * @author RGreenlees
 * @author Kai Burjack
 */
public abstract class Vector2fr {

    /**
     * @return x value of the vector
     */
    public abstract float x();

    /**
     * @return y value of the vector
     */
    public abstract float y();

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
     *        will receive the values of this vector in <tt>x, y</tt> order
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
     *        the absolute position into the ByteBuffer
     * @param buffer
     *        will receive the values of this vector in <tt>x, y</tt> order
     * @return the passed in buffer
     */
    public ByteBuffer get(int index, ByteBuffer buffer) {
        buffer.putFloat(index, x());
        buffer.putFloat(index + 4, y());
        return buffer;
    }

    /**
     * Store this vector into the supplied {@link java.nio.FloatBuffer} at the current
     * buffer {@link java.nio.FloatBuffer#position() position}.
     * <p>
     * This method will not increment the position of the given FloatBuffer.
     * <p>
     * In order to specify the offset into the FloatBuffer at which
     * the vector is stored, use {@link #get(int, java.nio.FloatBuffer)}, taking
     * the absolute position as parameter.
     *
     * @param buffer
     *        will receive the values of this vector in <tt>x, y</tt> order
     * @return the passed in buffer
     * @see #get(int, java.nio.FloatBuffer)
     */
    public FloatBuffer get(FloatBuffer buffer) {
        return get(buffer.position(), buffer);
    }

    /**
     * Store this vector into the supplied {@link java.nio.FloatBuffer} starting at the specified
     * absolute buffer position/index.
     * <p>
     * This method will not increment the position of the given FloatBuffer.
     *
     * @param index
     *        the absolute position into the FloatBuffer
     * @param buffer
     *        will receive the values of this vector in <tt>x, y</tt> order
     * @return the passed in buffer
     */
    public FloatBuffer get(int index, FloatBuffer buffer) {
        buffer.put(index,      x());
        buffer.put(index + 1,  y());
        return buffer;
    }

    /**
     * Return the dot product of this vector and <code>v</code>.
     *
     * @param v
     *        the other vector
     * @return the dot product
     */
    public float dot(Vector2fr v) {
        return x() * v.x() + y() * v.y();
    }

    /**
     * Return the angle between this vector and the supplied vector.
     *
     * @param v
     *        the other vector
     * @return the angle, in radians
     */
    public float angle(Vector2fr v) {
        float dot = x()*v.x() + y()*v.y();
        float det = x()*v.y() - y()*v.x();
        return (float) Math.atan2(det, dot);
    }

    /**
     * Return the length of this vector.
     *
     * @return the length
     */
    public float length() {
        return (float) Math.sqrt((x() * x()) + (y() * y()));
    }

    /**
     * Return the length squared of this vector.
     *
     * @return the length squared
     */
    public float lengthSquared() {
        return x() * x() + y() * y();
    }

    /**
     * Return the distance between this and <code>v</code>.
     *
     * @param v
     *        the other vector
     * @return the distance
     */
    public float distance(Vector2fr v) {
        float dx = v.x() - x();
        float dy = v.y() - y();
        return (float) Math.sqrt(dx * dx + dy * dy);
    }

    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Float.floatToIntBits(x());
        result = prime * result + Float.floatToIntBits(y());
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Vector2fr) {
            Vector2fr other = (Vector2fr) obj;
            return Float.floatToIntBits(x()) == Float.floatToIntBits(other.x())
                    && Float.floatToIntBits(y()) == Float.floatToIntBits(other.y());
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
     *        the {@link java.text.NumberFormat} used to format the vector components with
     * @return the string representation
     */
    public String toString(NumberFormat formatter) {
        return "(" + formatter.format(x()) + " " + formatter.format(y()) + ")"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
    }

}
