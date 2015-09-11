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
package org.joml.immutable;

import org.joml.Vector2fr;

/**
 * Represents an immutable 2D vector with single-precision.
 */
public class ImmutableVector2f extends Vector2fr {
    public final float x;
    public final float y;

    /**
     * Create a new {@link ImmutableVector2f} and initialize both of its components with the given value.
     *
     * @param d the value of both components
     */
    public ImmutableVector2f(float d) {
        this(d, d);
    }

    /**
     * Create a new {@link ImmutableVector2f} and initialize its components to the given values.
     *
     * @param x the x component
     * @param y the y component
     */
    public ImmutableVector2f(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Create a new {@link ImmutableVector2f} and initialize its components to the one of the given vector.
     *
     * @param v the {@link Vector2fr} to copy the values from
     */
    public ImmutableVector2f(Vector2fr v) {
        x = v.x();
        y = v.y();
    }

    public float x() {
        return x;
    }

    public float y() {
        return y;
    }
}
