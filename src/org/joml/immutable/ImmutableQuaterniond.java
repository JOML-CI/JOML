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

import org.joml.AxisAngle4dr;
import org.joml.AxisAngle4fr;
import org.joml.Quaterniondr;
import org.joml.Quaternionfr;

/**
 * Contains the immutable definition and functions for rotations expressed as
 * 4-dimensional vectors
 */
public class ImmutableQuaterniond extends Quaterniondr {
    /**
     * The first component of the vector part.
     */
    public final double x;
    /**
     * The second component of the vector part.
     */
    public final double y;
    /**
     * The third component of the vector part.
     */
    public final double z;
    /**
     * The real/scalar part of the quaternion.
     */
    public final double w;

    /**
     * Create a new {@link ImmutableQuaterniond} and initialize it with <tt>(x=0, y=0, z=0, w=1)</tt>,
     * where <tt>(x, y, z)</tt> is the vector part of the quaternion and <tt>w</tt> is the real/scalar part.
     */
    public ImmutableQuaterniond() {
        x = 0.0;
        y = 0.0;
        z = 0.0;
        w = 1.0;
    }

    /**
     * Create a new {@link ImmutableQuaterniond} and initialize its components to the given values.
     *
     * @param x the first component of the imaginary part
     * @param y the second component of the imaginary part
     * @param z the third component of the imaginary part
     * @param w the real part
     */
    public ImmutableQuaterniond(double x, double y, double z, double w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    /**
     * Create a new {@link ImmutableQuaterniond} and initialize its imaginary components to the given values,
     * and its real part to <tt>1.0</tt>.
     *
     * @param x the first component of the imaginary part
     * @param y the second component of the imaginary part
     * @param z the third component of the imaginary part
     */
    public ImmutableQuaterniond(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = 1.0;
    }

    /**
     * Create a new {@link ImmutableQuaterniond} and initialize its components to the same values as the given {@link Quaterniondr}.
     *
     * @param source the {@link Quaterniondr} to take the component values from
     */
    public ImmutableQuaterniond(Quaterniondr source) {
        x = source.x();
        y = source.y();
        z = source.z();
        w = source.w();
    }

    /**
     * Create a new {@link ImmutableQuaterniond} and initialize its components to the same values as the given {@link Quaternionfr}.
     *
     * @param source the {@link Quaternionfr} to take the component values from
     */
    public ImmutableQuaterniond(Quaternionfr source) {
        x = source.x();
        y = source.y();
        z = source.z();
        w = source.w();
    }

    /**
     * Create a new {@link ImmutableQuaterniond} and initialize it to represent the same rotation as the given {@link AxisAngle4fr}.
     *
     * @param axisAngle the axis-angle to initialize this quaternion with
     */
    public ImmutableQuaterniond(AxisAngle4fr axisAngle) {
        double s = Math.sin(axisAngle.angle() * 0.5);
        x = axisAngle.x() * s;
        y = axisAngle.y() * s;
        z = axisAngle.z() * s;
        w = Math.cos(axisAngle.angle() * 0.5);
    }

    /**
     * Create a new {@link ImmutableQuaterniond} and initialize it to represent the same rotation as the given {@link AxisAngle4dr}.
     *
     * @param axisAngle the axis-angle to initialize this quaternion with
     */
    public ImmutableQuaterniond(AxisAngle4dr axisAngle) {
        double s = Math.sin(axisAngle.angle() * 0.5);
        x = axisAngle.x() * s;
        y = axisAngle.y() * s;
        z = axisAngle.z() * s;
        w = Math.cos(axisAngle.angle() * 0.5);
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

    public double w() {
        return w;
    }
}
