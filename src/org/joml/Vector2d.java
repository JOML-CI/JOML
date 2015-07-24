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

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Represents a 2D vector with double-precision.
 *
 * @author RGreenlees
 * @author Kai Burjack
 */
public class Vector2d implements Externalizable {

    private static final long serialVersionUID = 1L;

    /**
     * The x-coordinate of the vector.
     */
    public double x;
    /**
     * The y-coordinate of the vector.
     */
    public double y;

    /**
     * Create a new {@link Vector2d} and initialize its components to zero.
     */
    public Vector2d() {
    }

    /**
     * Create a new {@link Vector2d} and initialize its components to the given values.
     * 
     * @param x
     *          the x value
     * @param y
     *          the y value
     */
    public Vector2d(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Create a new {@link Vector2d} and initialize its components to the one of the given vector.
     * 
     * @param v
     *          the {@link Vector2d} to copy the values from
     */
    public Vector2d(Vector2d v) {
        x = v.x;
        y = v.y;
    }

    /**
     * Create a new {@link Vector2d} and initialize its components to the one of the given vector.
     * 
     * @param v
     *          the {@link Vector2f} to copy the values from
     */
    public Vector2d(Vector2f v) {
        x = v.x;
        y = v.y;
    }

    /**
     * Set the x and y attributes to the supplied values.
     * 
     * @param x
     *          the x value to set
     * @param y
     *          the y value to set
     * @return this
     */
    public Vector2d set(double x, double y) {
        this.x = x;
        this.y = y;
        return this;
    }

    /**
     * Set this {@link Vector2d} to the values of v.
     * 
     * @param v
     *          the vector to copy from
     * @return this
     */
    public Vector2d set(Vector2d v) {
        x = v.x;
        y = v.y;
        return this;
    }

    /**
     * Set this {@link Vector2d} to be a clone of <code>v</code>.
     * 
     * @param v
     *          the vector to copy from
     * @return this
     */
    public Vector2d set(Vector2f v) {
        x = v.x;
        y = v.y;
        return this;
    }

    /**
     * Store one perpendicular vector of <code>v</code> in <code>dest</code>.
     * 
     * @param v
     *          the vector to build one perpendicular vector of
     * @param dest
     *          will hold the result
     */
    public static void perpendicular(Vector2d v, Vector2d dest) {
        dest.x = v.y;
        dest.y = v.x * -1;
    }

    /**
     * Set this vector to be one of its perpendicular vectors.
     * 
     * @return this
     */
    public Vector2d perpendicular() {
        return set(y, x * -1);
    }

    /**
     * Subtract <code>v</code> from this vector.
     * 
     * @param v
     *          the vector to subtract
     * @return this
     */
    public Vector2d sub(Vector2d v) {
        x -= v.x;
        y -= v.y;
        return this;
    }

    /**
     * Subtract <tt>(x, y)</tt> from this vector.
     * 
     * @param x
     *          the x-coordinate to subtract
     * @param y
     *          the y-coordinate to subtract
     * @return this
     */
    public Vector2d sub(double x, double y) {
        this.x -= x;
        this.y -= y;
        return this;
    }

    /**
     * Subtract <tt>(x, y)</tt> from this vector and store the result in <code>dest</code>.
     * 
     * @param x
     *          the x-coordinate to subtract
     * @param y
     *          the y-coordinate to subtract
     * @param dest
     *          will hold the result         
     * @return this
     */
    public Vector2d sub(double x, double y, Vector2d dest) {
        dest.x = this.x - x;
        dest.y = this.y - y;
        return this;
    }

    /**
     * Subtract <code>v</code> from this vector.
     * 
     * @param v
     *          the vector to subtract
     * @return this
     */
    public Vector2d sub(Vector2f v) {
        x -= v.x;
        y -= v.y;
        return this;
    }

    /**
     * Subtract <code>v</code> from <code>this</code> vector and store the result in <code>dest</code>.
     * 
     * @param v
     *          the vector to subtract
     * @param dest
     *          will hold the result
     * @return this
     */
    public Vector2d sub(Vector2d v, Vector2d dest) {
        dest.x = x - v.x;
        dest.y = y - v.y;
        return this;
    }

    /**
     * Subtract <code>v</code> from <code>this</code> vector and store the result in <code>dest</code>.
     * 
     * @param v
     *          the vector to subtract
     * @param dest
     *          will hold the result
     * @return this
     */
    public Vector2d sub(Vector2f v, Vector2d dest) {
        dest.x = x + v.x;
        dest.y = y + v.y;
        return this;
    }

    /**
     * Subtract <code>b</code> from <code>a</code> and store the result in <code>dest</code>.
     * 
     * @param a
     *          the vector to subtract from
     * @param b
     *          the vector to subtract
     * @param dest
     *          will hold the result
     */
    public static void sub(Vector2f a, Vector2d b, Vector2d dest) {
        dest.x = a.x - b.x;
        dest.y = a.y - b.y;
    }

    /**
     * Return the dot product of this vector and <code>v</code>.
     * 
     * @param v
     *          the other vector
     * @return the dot product
     */
    public double dot(Vector2d v) {
        return x * v.x + y * v.y;
    }

    /**
     * Return the angle between this vector and the supplied vector.
     * 
     * @param v
     *          the other vector
     * @return the angle, in radians
     */
    public double angle(Vector2d v) {
        double dot = x*v.x + y*v.y;
        double det = x*v.y - y*v.x;
        return Math.atan2(det, dot);
    }

    /**
     * Return the length of this vector.
     * 
     * @return the length
     */
    public double length() {
        return Math.sqrt((x * x) + (y * y));
    }

    /**
     * Return the distance between <code>this</code> and <code>v</code>.
     * 
     * @param v
     *          the other vector
     * @return the euclidean distance
     */
    public double distance(Vector2d v) {
        return Math.sqrt((v.x - x) * (v.x - x)
                       + (v.y - y) * (v.y - y));
    }

    /**
     * Return the distance between <code>this</code> and <code>v</code>.
     * 
     * @param v
     *          the other vector
     * @return the euclidean distance
     */
    public double distance(Vector2f v) {
        return Math.sqrt((v.x - x) * (v.x - x)
                       + (v.y - y) * (v.y - y));
    }

    /**
     * Normalize this vector.
     * 
     * @return this
     */
    public Vector2d normalize() {
        double length = Math.sqrt((x * x) + (y * y));
        x /= length;
        y /= length;
        return this;
    }

    /**
     * Normalize this vector and store the result in <code>dest</code>.
     * 
     * @param dest
     *          will hold the result
     * @return this
     */
    public Vector2d normalize(Vector2d dest) {
        double length = Math.sqrt((x * x) + (y * y));
        dest.x = x / length;
        dest.y = y / length;
        return this;
    }

    /**
     * Add <code>v</code> to this vector.
     * 
     * @param v
     *          the vector to add
     * @return this
     */
    public Vector2d add(Vector2d v) {
        x += v.x;
        y += v.y;
        return this;
    }

    /**
     * Add <code>(x, y)</code> to this vector.
     * 
     * @param x
     *          the x-coordinate to add
     * @param y
     *          the y-coordinate to add
     * @return this
     */
    public Vector2d add(double x, double y) {
        this.x += x;
        this.y += y;
        return this;
    }

    /**
     * Add <code>(x, y)</code> to this vector and store the result in <code>dest</code>.
     * 
     * @param x
     *          the x-coordinate to add
     * @param y
     *          the y-coordinate to add
     * @param dest
     *          will hold the result
     * @return this
     */
    public Vector2d add(double x, double y, Vector2d dest) {
        dest.x = this.x + x;
        dest.y = this.y + y;
        return this;
    }

    /**
     * Add <code>v</code> to this vector.
     * 
     * @param v
     *          the vector to add
     * @return this
     */
    public Vector2d add(Vector2f v) {
        x += v.x;
        y += v.y;
        return this;
    }

    /**
     * Add <code>v</code> to this vector and store the result in <code>dest</code>.
     * 
     * @param v
     *          the vector to add
     * @param dest
     *          will hold the result
     * @return this
     */
    public Vector2d add(Vector2d v, Vector2d dest) {
        dest.x = x + v.x;
        dest.y = y + v.y;
        return this;
    }

    /**
     * Add <code>v</code> to this vector and store the result in <code>dest</code>.
     * 
     * @param v
     *          the vector to add
     * @param dest
     *          will hold the result
     * @return this
     */
    public Vector2d add(Vector2f v, Vector2d dest) {
        dest.x = x + v.x;
        dest.y = y + v.y;
        return this;
    }

    /**
     * Add <code>a</code> to <code>b</code> and store the result in <code>dest</code>.
     * 
     * @param a
     *          the first addend
     * @param b
     *          the second addend
     * @param dest
     *          will hold the result
     */
    public static void add(Vector2f a, Vector2d b, Vector2d dest) {
        dest.x = a.x + b.x;
        dest.y = a.y + b.y;
    }

    /**
     * Set all components to zero.
     * 
     * @return this
     */
    public Vector2d zero() {
        this.x = 0.0;
        this.y = 0.0;
        return this;
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeDouble(x);
        out.writeDouble(y);
    }

    public void readExternal(ObjectInput in) throws IOException,
            ClassNotFoundException {
        x = in.readDouble();
        y = in.readDouble();
    }

    /**
     * Negate this vector.
     * 
     * @return this
     */
    public Vector2d negate() {
        x = -x;
        y = -y;
        return this;
    }

    /**
     * Negate this vector and store the result in <code>dest</code>.
     * 
     * @param dest
     *          will hold the result
     * @return this
     */
    public Vector2d negate(Vector2d dest) {
        dest.x = -x;
        dest.y = -y;
        return this;
    }

    public int hashCode() {
        final int prime = 31;
        int result = 1;
        long temp;
        temp = Double.doubleToLongBits(x);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Vector2d other = (Vector2d) obj;
        if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
            return false;
        if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y))
            return false;
        return true;
    }

    /**
     * Return a string representation of this vector.
     * <p>
     * This method creates a new {@link DecimalFormat} on every invocation with the format string "<tt> 0.000E0;-</tt>".
     * 
     * @return the string representation
     */
    public String toString() {
        DecimalFormat formatter = new DecimalFormat(" 0.000E0;-"); //$NON-NLS-1$
        return toString(formatter).replaceAll("E(\\d+)", "E+$1"); //$NON-NLS-1$ //$NON-NLS-2$
    }

    /**
     * Return a string representation of this vector by formatting the vector components with the given {@link NumberFormat}.
     * 
     * @param formatter
     *          the {@link NumberFormat} used to format the vector components with
     * @return the string representation
     */
    public String toString(NumberFormat formatter) {
        return "(" + formatter.format(x) + " " + formatter.format(y) + ")"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
    }

}
