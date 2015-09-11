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
import org.joml.Quaternionfr;

/**
 * Contains the immutable definition and functions for rotations expressed as
 * 4-dimensional vectors
 */
public class ImmutableQuaternionf extends Quaternionfr {

    /**
     * The first component of the vector part.
     */
    public final float x;
    /**
     * The second component of the vector part.
     */
    public final float y;
    /**
     * The third component of the vector part.
     */
    public final float z;
    /**
     * The real/scalar part of the quaternion.
     */
    public final float w;

    /**
     * Create a new {@link ImmutableQuaternionf} and initialize it with <tt>(x=0, y=0, z=0, w=1)</tt>,
     * where <tt>(x, y, z)</tt> is the vector part of the quaternion and <tt>w</tt> is the real/scalar part.
     */
    public ImmutableQuaternionf() {
        x = 0.0f;
        y = 0.0f;
        z = 0.0f;
        w = 1.0f;
    }

    /**
     * Create a new {@link ImmutableQuaternionf} and initialize its components to the given values.
     *
     * @param x the first component of the imaginary part
     * @param y the second component of the imaginary part
     * @param z the third component of the imaginary part
     * @param w the real part
     */
    public ImmutableQuaternionf(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    /**
     * Create a new {@link ImmutableQuaternionf} and initialize its imaginary components to the given values,
     * and its real part to <tt>1.0</tt>.
     *
     * @param x the first component of the imaginary part
     * @param y the second component of the imaginary part
     * @param z the third component of the imaginary part
     */
    public ImmutableQuaternionf(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
        w = 1.0f;
    }

    /**
     * Create a new {@link ImmutableQuaternionf} and initialize its components to the same values as the given {@link Quaternionfr}.
     *
     * @param source the {@link Quaternionfr} to take the component values from
     */
    public ImmutableQuaternionf(Quaternionfr source) {
        x = source.x();
        y = source.y();
        z = source.z();
        w = source.w();
    }

    /**
     * Create a new {@link ImmutableQuaternionf} which represents the rotation of the given {@link AxisAngle4fr}.
     *
     * @param axisAngle the {@link AxisAngle4fr}
     */
    public ImmutableQuaternionf(AxisAngle4fr axisAngle) {
        float sin = (float) Math.sin(axisAngle.angle() / 2.0);
        float cos = (float) Math.cos(axisAngle.angle() / 2.0);
        x = axisAngle.x() * sin;
        y = axisAngle.y() * sin;
        z = axisAngle.z() * sin;
        w = cos;
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
