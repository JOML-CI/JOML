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
import java.text.NumberFormat;

/**
 * Interface for readable, 2D vectors with single-precision.
 */
public interface Vector2fr {

    float x();

    float y();

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
    ByteBuffer get(ByteBuffer buffer);

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
    ByteBuffer get(int index, ByteBuffer buffer);

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
    FloatBuffer get(FloatBuffer buffer);

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
    FloatBuffer get(int index, FloatBuffer buffer);

    /**
     * Return the dot product of this vector and <code>v</code>.
     *
     * @param v
     *        the other vector
     * @return the dot product
     */
    float dot(Vector2fr v);

    /**
     * Return the angle between this vector and the supplied vector.
     *
     * @param v
     *        the other vector
     * @return the angle, in radians
     */
    float angle(Vector2fr v);

    /**
     * Return the length of this vector.
     *
     * @return the length
     */
    float length();

    /**
     * Return the length squared of this vector.
     *
     * @return the length squared
     */
    float lengthSquared();

    /**
     * Return the distance between this and <code>v</code>.
     *
     * @param v
     *        the other vector
     * @return the distance
     */
    float distance(Vector2fr v);

    /**
     * Return a string representation of this vector by formatting the vector components with the given {@link java.text.NumberFormat}.
     *
     * @param formatter
     *        the {@link java.text.NumberFormat} used to format the vector components with
     * @return the string representation
     */
    String toString(NumberFormat formatter);
}
