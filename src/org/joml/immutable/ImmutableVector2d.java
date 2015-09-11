/*
 * (C) Copyright 2015 Kai Burjack

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

import org.joml.Vector2dr;
import org.joml.Vector2fr;

/**
 * Represents an immutable 2D vector with double-precision.
 */
public class ImmutableVector2d {
    /**
     * The x component of the vector.
     */
    public final double x;
    /**
     * The y component of the vector.
     */
    public final double y;

    /**
     * Create a new {@link ImmutableVector2d} and initialize its components to zero.
     */
    public ImmutableVector2d() {
        this(0, 0);
    }

    /**
     * Create a new {@link ImmutableVector2d} and initialize both of its components with the given value.
     *
     * @param d the value of both components
     */
    public ImmutableVector2d(double d) {
        this(d, d);
    }

    /**
     * Create a new {@link ImmutableVector2d} and initialize its components to the given values.
     *
     * @param x the x value
     * @param y the y value
     */
    public ImmutableVector2d(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Create a new {@link ImmutableVector2d} and initialize its components to the one of the given vector.
     *
     * @param v the {@link Vector2dr} to copy the values from
     */
    public ImmutableVector2d(Vector2dr v) {
        x = v.x();
        y = v.y();
    }

    /**
     * Create a new {@link ImmutableVector2d} and initialize its components to the one of the given vector.
     *
     * @param v the {@link Vector2fr} to copy the values from
     */
    public ImmutableVector2d(Vector2fr v) {
        x = v.x();
        y = v.y();
    }

    public double x() {
        return x;
    }

    public double y() {
        return y;
    }
}
