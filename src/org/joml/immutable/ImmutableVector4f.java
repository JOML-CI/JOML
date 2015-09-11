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

import org.joml.Vector4fr;

/**
 * Contains the definition of an immutable Vector comprising 4 floats and associated
 * transformations.
 */
public class ImmutableVector4f extends Vector4fr {
    /**
     * The x component of the vector.
     */
    public final float x;
    /**
     * The y component of the vector.
     */
    public final float y;
    /**
     * The z component of the vector.
     */
    public final float z;
    /**
     * The w component of the vector.
     */
    public final float w;

    /**
     * Create a new {@link ImmutableVector4f} of <code>(0, 0, 0, 1)</code>.
     */
    public ImmutableVector4f() {
        this(0, 0, 0, 1);
    }

    /**
     * Create a new {@link ImmutableVector4f} with the same values as <code>v</code>.
     *
     * @param v the {@link Vector4fr} to copy the values from
     */
    public ImmutableVector4f(Vector4fr v) {
        this.x = v.x();
        this.y = v.y();
        this.z = v.z();
        this.w = v.w();
    }

    /**
     * Create a new {@link ImmutableVector4f} and initialize all four components with the given value.
     *
     * @param d the value of all four components
     */
    public ImmutableVector4f(float d) {
        this(d, d, d, d);
    }

    /**
     * Create a new {@link ImmutableVector4f} with the given component values.
     *
     * @param x the x component
     * @param y the y component
     * @param z the z component
     * @param w the w component
     */
    public ImmutableVector4f(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public float x() {
        return x;
    }

    public float y() {
        return y;
    }

    public float z() {
        return z;
    }

    public float w() {
        return w;
    }
}
