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

/**
 * Represents a 2D axis-aligned rectangle.
 * 
 * @author Kai Burjack
 */
public class Rectanglef implements Externalizable {

    /**
     * The x coordinate of the minimum corner.
     */
    public float minX;
    /**
     * The y coordinate of the minimum corner.
     */
    public float minY;
    /**
     * The x coordinate of the maximum corner.
     */
    public float maxX;
    /**
     * The y coordinate of the maximum corner.
     */
    public float maxY;

    /**
     * Create a new {@link Rectanglef} with a minimum and maximum corner of <code>(0, 0)</code>.
     */
    public Rectanglef() {
    }

    /**
     * Create a new {@link Rectanglef} as a copy of the given <code>source</code>.
     * 
     * @param source
     *          the {@link Rectanglef} to copy from
     */
    public Rectanglef(Rectanglef source) {
        this.minX = source.minX;
        this.minY = source.minY;
        this.maxX = source.maxX;
        this.maxY = source.maxY;
    }

    /**
     * Create a new {@link Rectanglef} with the given <code>min</code> and <code>max</code> corner coordinates.
     * 
     * @param min
     *          the minimum coordinates
     * @param max
     *          the maximum coordinates
     */
    public Rectanglef(Vector2fc min, Vector2fc max) {
        this.minX = min.x();
        this.minY = min.y();
        this.maxX = max.x();
        this.maxY = max.y();
    }

    /**
     * Create a new {@link Rectanglef} with the given minimum and maximum corner coordinates.
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
    public Rectanglef(float minX, float minY, float maxX, float maxY) {
        this.minX = minX;
        this.minY = minY;
        this.maxX = maxX;
        this.maxY = maxY;
    }

    /**
     * Set this {@link Rectanglei} to be a clone of <code>source</code>.
     *
     * @param source
     *            the {@link Rectanglei} to copy from
     * @return this
     */
    public Rectanglef set(Rectanglef source){
        this.minX = source.minX;
        this.minY = source.minY;
        this.maxX = source.maxX;
        this.maxY = source.maxY;
        return this;
    }

    /**
     * Set the minimum corner coordinates.
     *
     * @param minX
     *          the x coordinate of the minimum corner
     * @param minY
     *          the y coordinate of the minimum corner
     * @return this
     */
    public Rectanglef setMin(float minX, float minY) {
        this.minX = minX;
        this.minY = minY;
        return this;
    }

    /**
     * Set the minimum corner coordinates.
     *
     * @param min
     *          the minimum coordinates
     * @return this
     */
    public Rectanglef setMin(Vector2fc min) {
        this.minX = min.x();
        this.minY = min.y();
        return this;
    }


    /**
     * Set the maximum corner coordinates.
     *
     * @param maxX
     *              the x coordinate of the maximum corner
     * @param maxY
     *              the y coordinate of the maximum corner
     * @return this
     */
    public Rectanglef setMax(float maxX, float maxY) {
        this.maxX = maxX;
        this.maxY = maxY;
        return this;
    }

    /**
     * Set the maximum corner coordinates.
     *
     * @param max
     *          the maximum coordinates
     * @return this
     */
    public Rectanglef setMax(Vector2fc max) {
        this.maxX = max.x();
        this.maxY = max.y();
        return this;
    }

    /**
     * Return the length of the rectangle in the X dimension.
     *
     * @return length in the X dimension
     */
    public float lengthX() {
        return maxX - minX;
    }

    /**
     * Return the length of the rectangle in the Y dimension.
     *
     * @return length in the Y dimension
     */
    public float lengthY() {
        return maxY - minY;
    }

    /**
     * Return the area of the rectangle
     *
     * @return area
     */
    public float area() {
        return lengthX() * lengthY();
    }

    /**
     * Set <code>this</code> to the union of <code>this</code> and the given point <code>p</code>.
     *
     * @param x
     *          the x coordinate of the point
     * @param y
     *          the y coordinate of the point
     * @return this
     */
    public Rectanglef union(float x, float y) {
        return union(x, y, this);
    }

    /**
     * Set <code>this</code> to the union of <code>this</code> and the given point <code>p</code>.
     *
     * @param p
     *          the point
     * @return this
     */
    public Rectanglef union(Vector2fc p) {
        return union(p.x(), p.y(), this);
    }

    /**
     * Compute the union of <code>this</code> and the given point <code>(x, y, z)</code> and store the result in <code>dest</code>.
     *
     * @param x
     *          the x coordinate of the point
     * @param y
     *          the y coordinate of the point
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Rectanglef union(float x, float y, Rectanglef dest) {
        dest.minX = this.minX < x ? this.minX : x;
        dest.minY = this.minY < y ? this.minY : y;
        dest.maxX = this.maxX > x ? this.maxX : x;
        dest.maxY = this.maxY > y ? this.maxY : y;
        return dest;
    }

    /**
     * Compute the union of <code>this</code> and the given point <code>p</code> and store the result in <code>dest</code>.
     *
     * @param p
     *          the point
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Rectanglef union(Vector2ic p, Rectanglef dest) {
        return union(p.x(), p.y(), dest);
    }

    /**
     * Set <code>this</code> to the union of <code>this</code> and <code>other</code>.
     *
     * @param other
     *          the other {@link Rectanglef}
     * @return this
     */
    public Rectanglef union(Rectanglef other) {
        return this.union(other, this);
    }

    /**
     * Compute the union of <code>this</code> and <code>other</code> and store the result in <code>dest</code>.
     *
     * @param other
     *          the other {@link Rectanglef}
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Rectanglef union(Rectanglef other, Rectanglef dest) {
        dest.minX = this.minX < other.minX ? this.minX : other.minX;
        dest.minY = this.minY < other.minY ? this.minY : other.minY;
        dest.maxX = this.maxX > other.maxX ? this.maxX : other.maxX;
        dest.maxY = this.maxY > other.maxY ? this.maxY : other.maxY;
        return dest;
    }

    /**
     * Check if this and the given rectangle intersect.
     * 
     * @param other
     *          the other rectangle
     * @return <code>true</code> iff both rectangles intersect; <code>false</code> otherwise
     */
    public boolean intersectsRectangle(Rectangled other) {
        return minX < other.maxX && maxX > other.minX &&
               maxY > other.minY && minY < other.maxY;
    }

    /**
     * Check if this and the given rectangle intersect.
     * 
     * @param other
     *          the other rectangle
     * @return <code>true</code> iff both rectangles intersect; <code>false</code> otherwise
     */
    public boolean intersectsRectangle(Rectanglef other) {
        return minX < other.maxX && maxX > other.minX &&
               maxY > other.minY && minY < other.maxY;
    }

    /**
     * Check if this and the given rectangle intersect.
     * 
     * @param other
     *          the other rectangle
     * @return <code>true</code> iff both rectangles intersect; <code>false</code> otherwise
     */
    public boolean intersectsRectangle(Rectanglei other) {
        return minX < other.maxX && maxX > other.minX &&
               maxY > other.minY && minY < other.maxY;
    }

    private Rectanglef validate() {
        if (!isValid()) {
            minX = Float.NaN;
            minY = Float.NaN;
            maxX = Float.NaN;
            maxY = Float.NaN;
        }
        return this;
    }

    /**
     * Check whether <code>this</code> rectangle represents a valid rectangle.
     * 
     * @return <code>true</code> iff this rectangle is valid; <code>false</code> otherwise
     */
    public boolean isValid() {
        return minX < maxX && minY < maxY;
    }

    /**
     * Compute the rectangle of intersection between <code>this</code> and the given rectangle.
     * <p>
     * If the two rectangles do not intersect, then {@link Float#NaN} is stored in each component
     * of <code>dest</code>.
     * 
     * @param other
     *          the other rectangle
     * @return this
     */
    public Rectanglef intersection(Rectanglef other) {
        return intersection(other, this);
    }

    /**
     * Compute the rectangle of intersection between <code>this</code> and the given rectangle.
     * <p>
     * If the two rectangles do not intersect, then {@link Float#NaN} is stored in each component
     * of <code>dest</code>.
     * 
     * @param other
     *          the other rectangle
     * @return this
     */
    public Rectanglef intersection(Rectanglei other) {
        return intersection(other, this);
    }

    /**
     * Compute the rectangle of intersection between <code>this</code> and the given rectangle and
     * store the result in <code>dest</code>.
     * <p>
     * If the two rectangles do not intersect, then {@link Float#NaN} is stored in each component
     * of <code>dest</code>.
     * 
     * @param other
     *          the other rectangle
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Rectanglef intersection(Rectanglef other, Rectanglef dest) {
        dest.minX = Math.max(minX, other.minX);
        dest.minY = Math.max(minY, other.minY);
        dest.maxX = Math.min(maxX, other.maxX);
        dest.maxY = Math.min(maxY, other.maxY);
        return dest.validate();
    }

    /**
     * Compute the rectangle of intersection between <code>this</code> and the given rectangle and
     * store the result in <code>dest</code>.
     * <p>
     * If the two rectangles do not intersect, then {@link Double#NaN} is stored in each component
     * of <code>dest</code>.
     * 
     * @param other
     *          the other rectangle
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Rectanglef intersection(Rectanglei other, Rectanglef dest) {
        dest.minX = Math.max(minX, other.minX);
        dest.minY = Math.max(minY, other.minY);
        dest.maxX = Math.min(maxX, other.maxX);
        dest.maxY = Math.min(maxY, other.maxY);
        return dest.validate();
    }

    /**
     * Return the length of this rectangle in the X and Y dimensions and store the result in <code>dest</code>.
     * 
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Vector2f lengths(Vector2f dest) {
        return dest.set(lengthX(), lengthY());
    }

    /**
     * Check if this rectangle contains the given <code>rectangle</code>.
     * 
     * @param rectangle
     *          the rectangle to test
     * @return <code>true</code> iff this rectangle contains the rectangle; <code>false</code> otherwise
     */
    public boolean containsRectangle(Rectangled rectangle) {
        return rectangle.minX >= minX && rectangle.maxX <= maxX &&
               rectangle.minY >= minY && rectangle.maxY <= maxY;
    }

    /**
     * Check if this rectangle contains the given <code>rectangle</code>.
     * 
     * @param rectangle
     *          the rectangle to test
     * @return <code>true</code> iff this rectangle contains the rectangle; <code>false</code> otherwise
     */
    public boolean containsRectangle(Rectanglef rectangle) {
        return rectangle.minX >= minX && rectangle.maxX <= maxX &&
               rectangle.minY >= minY && rectangle.maxY <= maxY;
    }

    /**
     * Check if this rectangle contains the given <code>rectangle</code>.
     * 
     * @param rectangle
     *          the rectangle to test
     * @return <code>true</code> iff this rectangle contains the rectangle; <code>false</code> otherwise
     */
    public boolean containsRectangle(Rectanglei rectangle) {
        return rectangle.minX >= minX && rectangle.maxX <= maxX &&
               rectangle.minY >= minY && rectangle.maxY <= maxY;
    }

    /**
     * Check if this rectangle contains the given <code>point</code>.
     * 
     * @param point
     *          the point to test
     * @return <code>true</code> iff this rectangle contains the point; <code>false</code> otherwise
     */
    public boolean containsPoint(Vector2fc point) {
        return containsPoint(point.x(), point.y());
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
    public boolean containsPoint(float x, float y) {
        return x > minX && y > minY && x < maxX && y < maxY;
    }

    /**
     * Translate <code>this</code> by the given vector <code>xy</code>.
     * 
     * @param xy
     *          the vector to translate by
     * @return this
     */
    public Rectanglef translate(Vector2fc xy) {
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
    public Rectanglef translate(Vector2fc xy, Rectanglef dest) {
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
    public Rectanglef translate(float x, float y) {
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
    public Rectanglef translate(float x, float y, Rectanglef dest) {
        dest.minX = minX + x;
        dest.minY = minY + y;
        dest.maxX = maxX + x;
        dest.maxY = maxY + y;
        return dest;
    }

    /**
     * Scale <code>this</code> about the origin.
     *
     * @param sf
     *          the scaling factor in the x and y axis
     * @return this
     */
    public Rectanglef scale(float sf) {
        return scale(sf, sf);
    }

    /**
     * Scale <code>this</code> about the origin and store the result in <code>dest</code>.
     *
     * @param sf
     *          the scaling factor in the x and y axis
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Rectanglef scale(float sf, Rectanglef dest) {
        return scale(sf, sf, dest);
    }

    /**
     * Scale <code>this</code> about an anchor.
     * <p>
     * This is equivalent to <code>translate(-ax, -ay).scale(sf).translate(ax, ay)</code>
     *
     * @param sf
     *          the scaling factor in the x and y axis
     * @param ax
     *          the x coordinate of the anchor
     * @param ay
     *          the y coordinate of the anchor
     * @return this
     */
    public Rectanglef scale(float sf, float ax, float ay) {
        return scale(sf, sf, ax, ay);
    }

    /**
     * Scale <code>this</code> about an anchor and store the result in <code>dest</code>.
     * <p>
     * This is equivalent to <code>translate(-ax, -ay, dest).scale(sf).translate(ax, ay)</code>
     *
     * @param sf
     *          the scaling factor in the x and y axis
     * @param ax
     *          the x coordinate of the anchor
     * @param ay
     *          the y coordinate of the anchor
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Rectanglef scale(float sf, float ax, float ay, Rectanglef dest) {
        return scale(sf, sf, ax, ay, dest);
    }

    /**
     * Scale <code>this</code> about an anchor.
     * <p>
     * This is equivalent to <code>translate(anchor.negate()).scale(sf).translate(anchor.negate())</code>
     *
     * @param sf
     *          the scaling factor in the x and y axis
     * @param anchor
     *          the location of the anchor
     * @return this
     */
    public Rectanglef scale(float sf, Vector2fc anchor) {
        return scale(sf, anchor.x(), anchor.y());
    }

    /**
     * Scale <code>this</code> about an anchor and store the result in <code>dest</code>.
     * <p>
     * This is equivalent to <code>translate(anchor.negate(), dest).scale(sf).translate(anchor.negate())</code>
     *
     * @param sf
     *          the scaling factor in the x and y axis
     * @param anchor
     *          the location of the anchor
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Rectanglef scale(float sf, Vector2fc anchor, Rectanglef dest) {
        return scale(sf, anchor.x(), anchor.y(), dest);
    }

    /**
     * Scale <code>this</code> about the origin.
     *
     * @param sx
     *          the scaling factor on the x axis
     * @param sy
     *          the scaling factor on the y axis
     * @return this
     */
    public Rectanglef scale(float sx, float sy) {
        return scale(sx, sy, 0f, 0f);
    }

    /**
     * Scale <code>this</code> about the origin and store the result in <code>dest</code>.
     *
     * @param sx
     *          the scaling factor on the x axis
     * @param sy
     *          the scaling factor on the y axis
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Rectanglef scale(float sx, float sy, Rectanglef dest) {
        return scale(sx, sy, 0f, 0f, dest);
    }

    /**
     * Scale <code>this</code> about an anchor.
     * <p>
     * This is equivalent to <code>translate(-ax, -ay).scale(sx, sy).translate(ax, ay)</code>
     *
     * @param sx
     *          the scaling factor on the x axis
     * @param sy
     *          the scaling factor on the y axis
     * @param ax
     *          the x coordinate of the anchor
     * @param ay
     *          the y coordinate of the anchor
     * @return this
     */
    public Rectanglef scale(float sx, float sy, float ax, float ay) {
        minX = (minX - ax) * sx + ax;
        minY = (minY - ay) * sy + ay;
        maxX = (maxX - ax) * sx + ax;
        maxY = (maxY - ay) * sy + ay;
        return this;
    }

    /**
     * Scale <code>this</code> about an anchor.
     * <p>
     * This is equivalent to <code>translate(anchor.negate()).scale(sx, sy).translate(anchor.negate())</code>
     *
     * @param sx
     *          the scaling factor on the x axis
     * @param sy
     *          the scaling factor on the y axis
     * @param anchor
     *          the location of the anchor
     * @return this
     */
    public Rectanglef scale(float sx, float sy, Vector2fc anchor) {
        return scale(sx, sy, anchor.x(), anchor.y());
    }

    /**
     * Scale <code>this</code> about an anchor and store the result in <code>dest</code>.
     * <p>
     * This is equivalent to <code>translate(-ax, -ay, dest).scale(sx, sy).translate(ax, ay)</code>
     *
     * @param sx
     *          the scaling factor on the x axis
     * @param sy
     *          the scaling factor on the y axis
     * @param ax
     *          the x coordinate of the anchor
     * @param ay
     *          the y coordinate of the anchor
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Rectanglef scale(float sx, float sy, float ax, float ay, Rectanglef dest) {
        dest.minX = (minX - ax) * sx + ax;
        dest.minY = (minY - ay) * sy + ay;
        dest.maxX = (maxX - ax) * sx + ax;
        dest.maxY = (maxY - ay) * sy + ay;
        return dest;
    }

    /**
     * Scale <code>this</code> about an anchor and store the result in <code>dest</code>.
     * <p>
     * This is equivalent to <code>translate(anchor.negate(), dest).scale(sx, sy).translate(anchor.negate())</code>
     *
     * @param sx
     *          the scaling factor on the x axis
     * @param sy
     *          the scaling factor on the y axis
     * @param anchor
     *          the location of the anchor
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Rectanglef scale(float sx, float sy, Vector2fc anchor, Rectanglef dest) {
        return scale(sx, sy, anchor.x(), anchor.y(), dest);
    }

    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Float.floatToIntBits(maxX);
        result = prime * result + Float.floatToIntBits(maxY);
        result = prime * result + Float.floatToIntBits(minX);
        result = prime * result + Float.floatToIntBits(minY);
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Rectanglef other = (Rectanglef) obj;
        if (Float.floatToIntBits(maxX) != Float.floatToIntBits(other.maxX))
            return false;
        if (Float.floatToIntBits(maxY) != Float.floatToIntBits(other.maxY))
            return false;
        if (Float.floatToIntBits(minX) != Float.floatToIntBits(other.minX))
            return false;
        if (Float.floatToIntBits(minY) != Float.floatToIntBits(other.minY))
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
        return "(" + Runtime.format(minX, formatter) + " " + Runtime.format(minY, formatter) + ") < "
             + "(" + Runtime.format(maxX, formatter) + " " + Runtime.format(maxY, formatter) + ")";
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeFloat(minX);
        out.writeFloat(minY);
        out.writeFloat(maxX);
        out.writeFloat(maxY);
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        minX = in.readFloat();
        minY = in.readFloat();
        maxX = in.readFloat();
        maxY = in.readFloat();
    }

}
