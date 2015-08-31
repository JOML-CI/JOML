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

/**
 *
 */
public class ImmutableVector2f extends BaseVector2f {
    private final float x;
    private final float y;

    /**
     * Create a new {@link ImmutableVector2f} and initialize both of its components with the given value.
     *
     * @param d
     *        the value of both components
     */
    public ImmutableVector2f(float d) {
        this(d, d);
    }

    /**
     * Create a new {@link ImmutableVector2f} and initialize its components to the given values.
     *
     * @param x
     *        the x component
     * @param y
     *        the y component
     */
    public ImmutableVector2f(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Create a new {@link ImmutableVector2f} and initialize its components to the one of the given vector.
     *
     * @param v
     *        the {@link Vector2fr} to copy the values from
     */
    public ImmutableVector2f(Vector2fr v) {
        x = v.x();
        y = v.y();
    }

    /**
     * Create a new {@link ImmutableVector2f} and read this vector from the supplied {@link java.nio.ByteBuffer}
     * at the current buffer {@link java.nio.ByteBuffer#position() position}.
     * <p>
     * This method will not increment the position of the given ByteBuffer.
     * <p>
     * In order to specify the offset into the ByteBuffer at which
     * the vector is read, use {@link #ImmutableVector2f(int, java.nio.ByteBuffer)}, taking
     * the absolute position as parameter.
     *
     * @param buffer
     *        values will be read in <tt>x, y</tt> order
     * @see #ImmutableVector2f(int, java.nio.ByteBuffer)
     */
    public ImmutableVector2f(ByteBuffer buffer) {
        this(buffer.position(), buffer);
    }

    /**
     * Create a new {@link ImmutableVector2f} and read this vector from the supplied {@link ByteBuffer}
     * starting at the specified absolute buffer position/index.
     * <p>
     * This method will not increment the position of the given ByteBuffer.
     *
     * @param index
     *        the absolute position into the ByteBuffer
     * @param buffer values will be read in <tt>x, y</tt> order
     */
    public ImmutableVector2f(int index, ByteBuffer buffer) {
        x = buffer.getFloat(index);
        y = buffer.getFloat(index + 4);
    }

    /**
     * Create a new {@link ImmutableVector2f} and read this vector from the supplied {@link java.nio.FloatBuffer}
     * at the current buffer {@link java.nio.FloatBuffer#position() position}.
     * <p>
     * This method will not increment the position of the given FloatBuffer.
     * <p>
     * In order to specify the offset into the FloatBuffer at which
     * the vector is read, use {@link #ImmutableVector2f(int, java.nio.FloatBuffer)}, taking
     * the absolute position as parameter.
     *
     * @param buffer
     *        values will be read in <tt>x, y</tt> order
     * @see #ImmutableVector2f(int, java.nio.FloatBuffer)
     */
    public ImmutableVector2f(FloatBuffer buffer) {
        this(buffer.position(), buffer);
    }

    /**
     * Create a new {@link ImmutableVector2f} and read this vector from the supplied {@link FloatBuffer}
     * starting at the specified absolute buffer position/index.
     * <p>
     * This method will not increment the position of the given FloatBuffer.
     *
     * @param index
     *        the absolute position into the FloatBuffer
     * @param buffer
     *        values will be read in <tt>x, y</tt> order
     */
    public ImmutableVector2f(int index, FloatBuffer buffer) {
        x = buffer.get(index);
        y = buffer.get(index + 1);
    }

    public float x() {
        return x;
    }

    public float y() {
        return y;
    }
}
