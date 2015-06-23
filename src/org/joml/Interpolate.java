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
package org.joml;

/**
 * Provides basic interpolation functions.
 * 
 * @author Kai Burjack
 */
public class Interpolate {

    /**
     * Interpolates between the given two values with their associated tangens
     * using hermite interpolation.
     * 
     * @param v0
     *            the first value
     * @param t0
     *            the tangent of the first value
     * @param v1
     *            the second value
     * @param t1
     *            the tangent of the second value
     * @param t
     *            the interpolation parameter, within <tt>[0..1]</tt>
     * @return the interpolated value
     */
    public static double hermite(double v0, double t0, double v1, double t1, double t) {
        double t2 = t * t;
        double t3 = t2 * t;
        double result;
        if (t == 0.0)
            result = v0;
        else if (t == 1.0)
            result = v1;
        else
            result = (2.0 * v0 - 2.0 * v1 + t1 + t0) * t3 + (3.0 * v1 - 3.0 * v0 - 2.0 * t0 - t1) * t2 + t0 * t + v0;
        return result;
    }

    /**
     * Uses hermite interpolation with zero tangents to produce smooth values
     * between <code>v1</code> and <code>v2</code> based on the value of
     * <code>t</code>.
     * 
     * @param v1
     * @param v2
     * @param t
     *            the interpolation parameter, within <tt>[0..1]</tt>
     * @return the interpolated value
     */
    public static double smoothStep(double v1, double v2, double t) {
        double result = t > 1.0 ? 1.0 : (t < 0.0 ? 0.0 : t);
        result = hermite(v1, 0.0, v2, 0.0, result);
        return result;
    }

}
