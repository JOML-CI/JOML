/*
 * (C) Copyright 2015-2016 Kai Burjack

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
 * Contains various interpolation functions.
 * 
 * @author Kai Burjack
 */
public class Interpolationf {

    /**
     * Bilinearly interpolate the single scalar value 'f' over the given triangle.
     * 
     * @param v0X
     *            the x coordinate of the first triangle vertex
     * @param v0Y
     *            the y coordinate of the first triangle vertex
     * @param f0
     *            the value of 'f' at the first vertex
     * @param v1X
     *            the x coordinate of the second triangle vertex
     * @param v1Y
     *            the y coordinate of the second triangle vertex
     * @param f1
     *            the value of 'f' at the second vertex
     * @param v2X
     *            the x coordinate of the third triangle vertex
     * @param v2Y
     *            the y coordinate of the third triangle vertex
     * @param f2
     *            the value of 'f' at the third vertex
     * @param x
     *            the x coordinate of the point to interpolate 'f' at
     * @param y
     *            the y coordinate of the point to interpolate 'f' at
     * @return the interpolated value of 'f'
     */
    public static float interpolateTriangle(
            float v0X, float v0Y, float f0,
            float v1X, float v1Y, float f1,
            float v2X, float v2Y, float f2,
            float x, float y) {
        float v12Y = v1Y - v2Y;
        float v21X = v2X - v1X;
        float v02X = v0X - v2X;
        float yv2Y = y - v2Y;
        float xv2X = x - v2X;
        float v02Y = v0Y - v2Y;
        float den = (v12Y * v02X + v21X * v02Y);
        float l1 = (v12Y * xv2X + v21X * yv2Y) / den;
        float l2 = (-v02Y * xv2X + v02X * yv2Y) / den;
        float l3 = 1.0f - l1 - l2;
        return l1 * f0 + l2 * f1 + l3 * f2;
    }

    /**
     * Bilinearly interpolate the two-dimensional vector 'f' over the given triangle and store the result in <code>dest</code>.
     * 
     * @param v0X
     *            the x coordinate of the first triangle vertex
     * @param v0Y
     *            the y coordinate of the first triangle vertex
     * @param f0X
     *            the x component of the value of 'f' at the first vertex
     * @param f0Y
     *            the y component of the value of 'f' at the first vertex
     * @param v1X
     *            the x coordinate of the second triangle vertex
     * @param v1Y
     *            the y coordinate of the second triangle vertex
     * @param f1X
     *            the x component of the value of 'f' at the second vertex
     * @param f1Y
     *            the y component of the value of 'f' at the second vertex
     * @param v2X
     *            the x coordinate of the third triangle vertex
     * @param v2Y
     *            the y coordinate of the third triangle vertex
     * @param f2X
     *            the x component of the value of 'f' at the third vertex
     * @param f2Y
     *            the y component of the value of 'f' at the third vertex
     * @param x
     *            the x coordinate of the point to interpolate 'f' at
     * @param y
     *            the y coordinate of the point to interpolate 'f' at
     * @param dest
     *            will hold the interpolation result
     * @return dest
     */
    public static Vector2f interpolateTriangle(
            float v0X, float v0Y, float f0X, float f0Y,
            float v1X, float v1Y, float f1X, float f1Y,
            float v2X, float v2Y, float f2X, float f2Y,
            float x, float y, Vector2f dest) {
        float v12Y = v1Y - v2Y;
        float v21X = v2X - v1X;
        float v02X = v0X - v2X;
        float yv2Y = y - v2Y;
        float xv2X = x - v2X;
        float v02Y = v0Y - v2Y;
        float den = (v12Y * v02X + v21X * v02Y);
        float l1 = (v12Y * xv2X + v21X * yv2Y) / den;
        float l2 = (-v02Y * xv2X + v02X * yv2Y) / den;
        float l3 = 1.0f - l1 - l2;
        dest.x = l1 * f0X + l2 * f1X + l3 * f2X;
        dest.y = l1 * f0Y + l2 * f1Y + l3 * f2Y;
        return dest;
    }

    /**
     * Bilinearly interpolate the three-dimensional vector 'f' over the given triangle and store the result in <code>dest</code>.
     * 
     * @param v0X
     *            the x coordinate of the first triangle vertex
     * @param v0Y
     *            the y coordinate of the first triangle vertex
     * @param f0X
     *            the x component of the value of 'f' at the first vertex
     * @param f0Y
     *            the y component of the value of 'f' at the first vertex
     * @param f0Z
     *            the z component of the value of 'f' at the first vertex
     * @param v1X
     *            the x coordinate of the second triangle vertex
     * @param v1Y
     *            the y coordinate of the second triangle vertex
     * @param f1X
     *            the x component of the value of 'f' at the second vertex
     * @param f1Y
     *            the y component of the value of 'f' at the second vertex
     * @param f1Z
     *            the z component of the value of 'f' at the second vertex
     * @param v2X
     *            the x coordinate of the third triangle vertex
     * @param v2Y
     *            the y coordinate of the third triangle vertex
     * @param f2X
     *            the x component of the value of 'f' at the third vertex
     * @param f2Y
     *            the y component of the value of 'f' at the third vertex
     * @param f2Z
     *            the z component of the value of 'f' at the third vertex
     * @param x
     *            the x coordinate of the point to interpolate 'f' at
     * @param y
     *            the y coordinate of the point to interpolate 'f' at
     * @param dest
     *            will hold the interpolation result
     * @return the interpolated value of 'f'
     */
    public static Vector3f interpolateTriangle(
            float v0X, float v0Y, float f0X, float f0Y, float f0Z,
            float v1X, float v1Y, float f1X, float f1Y, float f1Z,
            float v2X, float v2Y, float f2X, float f2Y, float f2Z,
            float x, float y, Vector3f dest) {
        float v12Y = v1Y - v2Y;
        float v21X = v2X - v1X;
        float v02X = v0X - v2X;
        float yv2Y = y - v2Y;
        float xv2X = x - v2X;
        float v02Y = v0Y - v2Y;
        float den = (v12Y * v02X + v21X * v02Y);
        float l1 = (v12Y * xv2X + v21X * yv2Y) / den;
        float l2 = (-v02Y * xv2X + v02X * yv2Y) / den;
        float l3 = 1.0f - l1 - l2;
        dest.x = l1 * f0X + l2 * f1X + l3 * f2X;
        dest.y = l1 * f0Y + l2 * f1Y + l3 * f2Y;
        dest.z = l1 * f0Z + l2 * f1Z + l3 * f2Z;
        return dest;
    }

}
