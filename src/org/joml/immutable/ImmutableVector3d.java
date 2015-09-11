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

import org.joml.Vector3dr;
import org.joml.Vector3fr;

/**
 * Contains the definition of an immutable Vector comprising 3 doubles and associated
 * transformations.
 */
public class ImmutableVector3d extends Vector3dr {
    /**
     * The x component of the vector.
     */
    public final double x;
    /**
     * The y component of the vector.
     */
    public final double y;
    /**
     * The z component of the vector.
     */
    public final double z;

    /**
     * Create a new {@link ImmutableVector3d} with all components set to zero.
     */
    public ImmutableVector3d() {
        this(0, 0, 0);
    }

    /**
     * Create a new {@link ImmutableVector3d} and initialize all three components with the given value.
     *
     * @param d the value of all three components
     */
    public ImmutableVector3d(double d) {
        this(d, d, d);
    }

    /**
     * Create a new {@link ImmutableVector3d} with the given component values.
     *
     * @param x the value of x
     * @param y the value of y
     * @param z the value of z
     */
    public ImmutableVector3d(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Create a new {@link ImmutableVector3d} whose values will be copied from the given vector.
     *
     * @param v provides the initial values for the new vector
     */
    public ImmutableVector3d(Vector3fr v) {
        this.x = v.x();
        this.y = v.y();
        this.z = v.z();
    }

    /**
     * Create a new {@link ImmutableVector3d} whose values will be copied from the given vector.
     *
     * @param v provides the initial values for the new vector
     */
    public ImmutableVector3d(Vector3dr v) {
        this.x = v.x();
        this.y = v.y();
        this.z = v.z();
    }

    public double x() {
        return x;
    }

    public double y() {
        return y;
    }

    public double z() {
        return z;
    }
}
