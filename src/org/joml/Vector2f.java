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

/**
 * Represents a 2D vector with single-precision.
 *
 * @author RGreenlees
 * @author Kai Burjack
 * @author Sri Harsha Chilakapati
 */
public class Vector2f {

    /**
     * The x component of the vector.
     */
    public float x;
    /**
     * The y component of the vector.
     */
    public float y;

    /**
     * Create a new {@link Vector2f} and initialize its components to zero.
     */
    public Vector2f() {
    }

    /**
     * Create a new {@link Vector2f} and initialize both of its components with the given value.
     *
     * @param d
     *        the value of both components
     */
    public Vector2f(float d) {
        this(d, d);
    }

    /**
     * Create a new {@link Vector2f} and initialize its components to the given values.
     *
     * @param x
     *        the x component
     * @param y
     *        the y component
     */
    public Vector2f(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Create a new {@link Vector2f} and initialize its components to the one of the given vector.
     *
     * @param v
     *        the {@link Vector2f} to copy the values from
     */
    public Vector2f(Vector2f v) {
        x = v.x;
        y = v.y;
    }

    /**
     * Create a new {@link Vector2f} and read this vector from the supplied float array starting at the index 0.
     * <p>
     * If you want to specify the offset into the float array at which
     * the vector is read, you can use {@link #Vector2f(int, float[])}, taking
     * the absolute position as parameter.
     *
     * @param buffer
     *        values will be read in <tt>x, y</tt> order
     * @see #Vector2f(int, float[])
     */
    public Vector2f(float[] buffer) {
        this(0, buffer);
    }

    /**
     * Create a new {@link Vector2f} and read this vector from the supplied float array
     * starting at the specified absolute buffer position/index.
     * <p>
     * This method will not increment the position of the given float array.
     *
     * @param index
     *        the absolute position into the float array.
     * @param buffer
     *        values will be read in <tt>x, y</tt> order
     */
    public Vector2f(int index, float[] buffer) {
        x = buffer[index];
        y = buffer[index + 1];
    }

    /**
     * Set the x and y components to the supplied value.
     *
     * @param d
     *        the value of both components
     * @return this
     */
    public Vector2f set(float d) {
        return set(d, d);
    }

    /**
     * Set the x and y components to the supplied values.
     *
     * @param x
     *        the x component
     * @param y
     *        the y component
     * @return this
     */
    public Vector2f set(float x, float y) {
        this.x = x;
        this.y = y;
        return this;
    }

    /**
     * Set this {@link Vector2f} to the values of v.
     *
     * @param v
     *        the vector to copy from
     * @return this
     */
    public Vector2f set(Vector2f v) {
        x = v.x;
        y = v.y;
        return this;
    }

    /**
     * Read this vector from the supplied float array starting at the position 0.
     * <p>
     * If you want to specify the offset into the float array at which
     * the vector is read, you can use {@link #set(int, float[])}, taking
     * the absolute position as parameter.
     *
     * @param buffer
     *        values will be read in <tt>x, y</tt> order
     * @return this
     * @see #set(int, float[])
     */
    public Vector2f set(float[] buffer) {
        return set(0, buffer);
    }

    /**
     * Read this vector from the supplied float array starting at the specified
     * absolute buffer position/index.
     * <p>
     * This method will not increment the position of the given float array.
     *
     * @param index
     *        the absolute position into the float array
     * @param buffer
     *        values will be read in <tt>x, y</tt> order
     * @return this
     */
    public Vector2f set(int index, float[] buffer) {
        x = buffer[index];
        y = buffer[index + 1];
        return this;
    }

    /**
     * Store this vector into the supplied float array starting at the index 0.
     * <p>
     * If you want to specify the offset into the float array at which
     * the vector is stored, you can use {@link #get(int, float[])}, taking
     * the absolute position as parameter.
     *
     * @param buffer
     *        will receive the values of this vector in <tt>x, y</tt> order
     * @return the passed in buffer
     * @see #get(int, float[])
     */
    public float[] get(float[] buffer) {
        return get(0, buffer);
    }

    /**
     * Store this vector into the supplied float array starting at the specified
     * absolute buffer position/index.
     * <p>
     * This method will not increment the position of the given float array.
     *
     * @param index
     *        the absolute position into the float array
     * @param buffer
     *        will receive the values of this vector in <tt>x, y</tt> order
     * @return the passed in buffer
     */
    public float[] get(int index, float[] buffer) {
        buffer[index] = x;
        buffer[index + 1] = y;
        return buffer;
    }

    /**
     * Store one perpendicular vector of <code>v</code> in <code>dest</code>.
     *
     * @param v
     *        the vector to build one perpendicular vector of
     * @param dest
     *        will hold the result
     */
    public static void perpendicular(Vector2f v, Vector2f dest) {
        dest.x = v.y;
        dest.y = v.x * -1;
    }

    /**
     * Set this vector to be one of its perpendicular vectors.
     *
     * @return this
     */
    public Vector2f perpendicular() {
        return set(y, x * -1);
    }

    /**
     * Subtract <code>b</code> from <code>a</code> and store the result in <code>dest</code>.
     *
     * @param a
     *        the first operand
     * @param b
     *        the second operand
     * @param dest
     *        will hold the result of <code>a - b</code>
     */
    public static void sub(Vector2f a, Vector2f b, Vector2f dest) {
        dest.x = a.x - b.x;
        dest.y = a.y - b.y;
    }

    /**
     * Subtract <code>v</code> from this vector.
     *
     * @param v
     *        the vector to subtract from this
     * @return this
     */
    public Vector2f sub(Vector2f v) {
        x -= v.x;
        y -= v.y;
        return this;
    }

    /**
     * Subtract <tt>(x, y)</tt> from this vector.
     *
     * @param x
     *        the x component to subtract
     * @param y
     *        the y component to subtract
     * @return this
     */
    public Vector2f sub(float x, float y) {
        this.x -= x;
        this.y -= y;
        return this;
    }

    /**
     * Return the dot product of this vector and <code>v</code>.
     *
     * @param v
     *        the other vector
     * @return the dot product
     */
    public float dot(Vector2f v) {
        return x * v.x + y * v.y;
    }

    /**
     * Return the angle between this vector and the supplied vector.
     *
     * @param v
     *        the other vector
     * @return the angle, in radians
     */
    public float angle(Vector2f v) {
        float dot = x*v.x + y*v.y;
        float det = x*v.y - y*v.x;
        return (float) Math.atan2(det, dot);
    }

    /**
     * Return the length of this vector.
     *
     * @return the length
     */
    public float length() {
        return (float) Math.sqrt((x * x) + (y * y));
    }

    /**
     * Return the length squared of this vector.
     *
     * @return the length squared
     */
    public float lengthSquared() {
        return x * x + y * y;
    }

    /**
     * Return the distance between this and <code>v</code>.
     *
     * @param v
     *        the other vector
     * @return the distance
     */
    public float distance(Vector2f v) {
        return (float) Math.sqrt(
                  (v.x - x) * (v.x - x)
                + (v.y - y) * (v.y - y));
    }

    /**
     * Normalize this vector.
     *
     * @return this
     */
    public Vector2f normalize() {
        float length = (float) Math.sqrt((x * x) + (y * y));
        x /= length;
        y /= length;
        return this;
    }

    /**
     * Normalize this vector and store the result in <code>dest</code>.
     *
     * @param dest
     *        will hold the result
     * @return this
     */
    public Vector2f normalize(Vector2f dest) {
        float length = (float) Math.sqrt((x * x) + (y * y));
        dest.x = x / length;
        dest.y = y / length;
        return this;
    }

    /**
     * Add <code>v</code> to this vector.
     *
     * @param v
     *        the vector to add
     * @return this
     */
    public Vector2f add(Vector2f v) {
        x += v.x;
        y += v.y;
        return this;
    }

    /**
     * Add <code>a</code> to <code>b</code> and store the result in <code>dest</code>.
     *
     * @param a
     *        the first addend
     * @param b
     *        the second addend
     * @param dest
     *        will hold the result
     */
    public static void add(Vector2f a, Vector2f b, Vector2f dest) {
        dest.x = a.x + b.x;
        dest.y = a.y + b.y;
    }

    /**
     * Set all components to zero.
     *
     * @return this
     */
    public Vector2f zero() {
        this.x = 0.0f;
        this.y = 0.0f;
        return this;
    }

    /**
     * Negate this vector.
     *
     * @return this
     */
    public Vector2f negate() {
        x = -x;
        y = -y;
        return this;
    }

    /**
     * Negate this vector and store the result in <code>dest</code>.
     *
     * @param dest
     *        will hold the result
     * @return this
     */
    public Vector2f negate(Vector2f dest) {
        dest.x = -x;
        dest.y = -y;
        return this;
    }

    /**
     * Multiply the components of this vector by the given scalar.
     *
     * @param scalar
     *        the value to multiply this vector's components by
     * @return this
     */
    public Vector2f mul(float scalar) {
        this.x *= scalar;
        this.y *= scalar;
        return this;
    }

    /**
     * Multiply the components of this vector by the given scalar and store the result in <code>dest</code>.
     *
     * @param scalar
     *        the value to multiply this vector's components by
     * @param dest
     *        will hold the result
     * @return this
     */
    public Vector2f mul(float scalar, Vector2f dest) {
        dest.x *= scalar;
        dest.y *= scalar;
        return this;
    }

    /**
     * Linearly interpolate <code>this</code> and <code>other</code> using the given interpolation factor <code>t</code>
     * and store the result in <code>this</code>.
     * <p>
     * If <code>t</code> is <tt>0.0</tt> then the result is <code>this</code>. If the interpolation factor is <code>1.0</code>
     * then the result is <code>other</code>.
     *
     * @param other
     *          the other vector
     * @param t
     *          the interpolation factor between 0.0 and 1.0
     * @return this
     */
    public Vector2f lerp(Vector2f other, float t) {
        return lerp(other, t, this);
    }

    /**
     * Linearly interpolate <code>this</code> and <code>other</code> using the given interpolation factor <code>t</code>
     * and store the result in <code>dest</code>.
     * <p>
     * If <code>t</code> is <tt>0.0</tt> then the result is <code>this</code>. If the interpolation factor is <code>1.0</code>
     * then the result is <code>other</code>.
     *
     * @param other
     *          the other vector
     * @param t
     *          the interpolation factor between 0.0 and 1.0
     * @param dest
     *          will hold the result
     * @return this
     */
    public Vector2f lerp(Vector2f other, float t, Vector2f dest) {
        dest.x = (1.0f - t) * x + t * other.x;
        dest.y = (1.0f - t) * y + t * other.y;
        return this;
    }

    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Float.floatToIntBits(x);
        result = prime * result + Float.floatToIntBits(y);
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Vector2f other = (Vector2f) obj;
        if (Float.floatToIntBits(x) != Float.floatToIntBits(other.x))
            return false;
        if (Float.floatToIntBits(y) != Float.floatToIntBits(other.y))
            return false;
        return true;
    }

    /**
     * Return a string representation of this vector.
     *
     * @return the string representation
     */
    public String toString() {
        return "(" + (x) + " " + (y) + ")"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
    }


}
