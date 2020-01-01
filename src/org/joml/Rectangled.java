/*
 * The MIT License
 *
 * Copyright (c) 2017-2020 JOML
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.joml;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.joml.internal.Options;
import org.joml.internal.Runtime;

/**
 * Represents a 2D axis-aligned rectangle.
 * 
 * @author Kai Burjack
 */
public class Rectangled implements Externalizable {

    /**
     * The x coordinate of the minimum corner.
     */
    public double minX;
    /**
     * The y coordinate of the minimum corner.
     */
    public double minY;
    /**
     * The x coordinate of the maximum corner.
     */
    public double maxX;
    /**
     * The y coordinate of the maximum corner.
     */
    public double maxY;

    /**
     * Create a new {@link Rectangled} with a minimum and maximum corner of <code>(0, 0)</code>.
     */
    public Rectangled() {
    }

    /**
     * Create a new {@link Rectangled} as a copy of the given <code>source</code>.
     * 
     * @param source
     *          the {@link Rectangled} to copy from
     */
    public Rectangled(Rectangled source) {
        this.minX = source.minX;
        this.minY = source.minY;
        this.maxX = source.maxX;
        this.maxY = source.maxY;
    }

    /**
     * Create a new {@link Rectangled} with the given <code>min</code> and <code>max</code> corner coordinates.
     * 
     * @param min
     *          the minimum coordinates
     * @param max
     *          the maximum coordinates
     */
    public Rectangled(Vector2dc min, Vector2dc max) {
        this.minX = min.x();
        this.minY = min.y();
        this.maxX = max.x();
        this.maxY = max.y();
    }

    /**
     * Create a new {@link Rectangled} with the given minimum and maximum corner coordinates.
     * 
     * @param minX
     *          the x coordinate of the minimum corner
     * @param minY
     *          the y coordinate of the minimum corner
     * @param maxX
     *          the x coordinate of the maximum corner
     * @param maxY
     *          the y coordinate of the maximum corner
     */
    public Rectangled(double minX, double minY, double maxX, double maxY) {
        super();
        this.minX = minX;
        this.minY = minY;
        this.maxX = maxX;
        this.maxY = maxY;
    }

    /**
     * Check if this and the given rectangle intersect.
     * 
     * @param other
     *          the other rectangle
     * @return <code>true</code> iff both rectangles intersect; <code>false</code> otherwise
     */
    public boolean intersects(Rectangled other) {
        return minX < other.maxX && maxX >= other.minX &&
               maxY >= other.minY && minY < other.maxY;
    }

    /**
     * Check if this rectangle contains the given <code>point</code>.
     * 
     * @param point
     *          the point to test
     * @return <code>true</code> iff this rectangle contains the point; <code>false</code> otherwise
     */
    public boolean contains(Vector2d point) {
        return contains(point.x, point.y);
    }

    /**
     * Check if this rectangle contains the given point <code>(x, y)</code>.
     * 
     * @param x
     *          the x coordinate of the point to check
     * @param y
     *          the y coordinate of the point to check
     * @return <code>true</code> iff this rectangle contains the point; <code>false</code> otherwise
     */
    public boolean contains(double x, double y) {
        return x >= minX && y >= minX && x < maxX && y < maxY;
    }

    /**
     * Translate <code>this</code> by the given vector <code>xy</code>.
     * 
     * @param xy
     *          the vector to translate by
     * @return this
     */
    public Rectangled translate(Vector2dc xy) {
        return translate(xy.x(), xy.y(), this);
    }

    /**
     * Translate <code>this</code> by the given vector <code>xy</code> and store the result in <code>dest</code>.
     * 
     * @param xy
     *          the vector to translate by
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Rectangled translate(Vector2dc xy, Rectangled dest) {
        return translate(xy.x(), xy.y(), dest);
    }

    /**
     * Translate <code>this</code> by the given vector <code>xy</code>.
     * 
     * @param xy
     *          the vector to translate by
     * @return this
     */
    public Rectangled translate(Vector2fc xy) {
        return translate(xy.x(), xy.y(), this);
    }

    /**
     * Translate <code>this</code> by the given vector <code>xy</code> and store the result in <code>dest</code>.
     * 
     * @param xy
     *          the vector to translate by
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Rectangled translate(Vector2fc xy, Rectangled dest) {
        return translate(xy.x(), xy.y(), dest);
    }

    /**
     * Translate <code>this</code> by the vector <code>(x, y)</code>.
     * 
     * @param x
     *          the x coordinate to translate by
     * @param y
     *          the y coordinate to translate by
     * @return this
     */
    public Rectangled translate(double x, double y) {
        return translate(x, y, this);
    }

    /**
     * Translate <code>this</code> by the vector <code>(x, y)</code> and store the result in <code>dest</code>.
     * 
     * @param x
     *          the x coordinate to translate by
     * @param y
     *          the y coordinate to translate by
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Rectangled translate(double x, double y, Rectangled dest) {
        dest.minX = minX + x;
        dest.minY = minY + y;
        dest.maxX = maxX + x;
        dest.maxY = maxY + y;
        return dest;
    }

    public int hashCode() {
        final int prime = 31;
        int result = 1;
        long temp;
        temp = Double.doubleToLongBits(maxX);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(maxY);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(minX);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(minY);
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
        Rectangled other = (Rectangled) obj;
        if (Double.doubleToLongBits(maxX) != Double.doubleToLongBits(other.maxX))
            return false;
        if (Double.doubleToLongBits(maxY) != Double.doubleToLongBits(other.maxY))
            return false;
        if (Double.doubleToLongBits(minX) != Double.doubleToLongBits(other.minX))
            return false;
        if (Double.doubleToLongBits(minY) != Double.doubleToLongBits(other.minY))
            return false;
        return true;
    }

    /**
     * Return a string representation of this rectangle.
     * <p>
     * This method creates a new {@link DecimalFormat} on every invocation with the format string "<code>0.000E0;-</code>".
     * 
     * @return the string representation
     */
    public String toString() {
        return Runtime.formatNumbers(toString(Options.NUMBER_FORMAT));
    }

    /**
     * Return a string representation of this rectangle by formatting the vector components with the given {@link NumberFormat}.
     * 
     * @param formatter
     *          the {@link NumberFormat} used to format the vector components with
     * @return the string representation
     */
    public String toString(NumberFormat formatter) {
        return "(" + formatter.format(minX) + " " + formatter.format(minY) + ") < "
             + "(" + formatter.format(maxX) + " " + formatter.format(maxY) + ")";
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeDouble(minX);
        out.writeDouble(minY);
        out.writeDouble(maxX);
        out.writeDouble(maxY);
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        minX = in.readDouble();
        minY = in.readDouble();
        maxX = in.readDouble();
        maxY = in.readDouble();
    }

}
