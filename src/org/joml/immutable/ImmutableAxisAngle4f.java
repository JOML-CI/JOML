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

import org.joml.AxisAngle4fr;
import org.joml.Vector3fr;

/**
 * Immutable representation of a 3D rotation of a given radians about an axis represented as an
 * unit 3D vector.
 * <p>
 * This class uses single-precision components.
 * </p>
 */
public class ImmutableAxisAngle4f extends AxisAngle4fr {
    /**
     * The angle in radians.
     */
    public final float angle;
    /**
     * The x-component of the rotation axis.
     */
    public final float x;
    /**
     * The y-component of the rotation axis.
     */
    public final float y;
    /**
     * The z-component of the rotation axis.
     */
    public final float z;

    /**
     * Create a new {@link ImmutableAxisAngle4f} with the same values of <code>a</code>.
     *
     * @param a the AngleAxis4fr to copy the values from
     */
    public ImmutableAxisAngle4f(AxisAngle4fr a) {
        x = a.x();
        y = a.y();
        z = a.z();
        angle = (float) ((a.angle() < 0.0 ? 2.0 * Math.PI + a.angle() % (2.0 * Math.PI) : a.angle()) % (2.0 * Math.PI));
    }

    /**
     * Create a new {@link ImmutableAxisAngle4f} with the given values.
     *
     * @param angle the angle in radians
     * @param x     the x-coordinate of the rotation axis
     * @param y     the y-coordinate of the rotation axis
     * @param z     the z-coordinate of the rotation axis
     */
    public ImmutableAxisAngle4f(float angle, float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.angle = (float) ((angle < 0.0 ? 2.0 * Math.PI + angle % (2.0 * Math.PI) : angle) % (2.0 * Math.PI));
    }

    /**
     * Create a new {@link ImmutableAxisAngle4f} with the given values.
     *
     * @param angle the angle in radians
     * @param v     the rotation axis as a {@link Vector3fr}
     */
    public ImmutableAxisAngle4f(float angle, Vector3fr v) {
        this(angle, v.x(), v.y(), v.z());
    }


    public float angle() {
        return angle;
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
}
