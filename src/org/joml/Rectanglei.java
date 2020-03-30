/*
 * The MIT License
 *
 * Copyright (c) 2020 JOML
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
public class Rectanglei implements Externalizable {

    /**
     * The x coordinate of the minimum corner.
     */
    public int minX;
    /**
     * The y coordinate of the minimum corner.
     */
    public int minY;
    /**
     * The x coordinate of the maximum corner.
     */
    public int maxX;
    /**
     * The y coordinate of the maximum corner.
     */
    public int maxY;

    /**
     * Create a new {@link Rectanglei} with a minimum and maximum corner of <code>(0, 0)</code>.
     */
    public Rectanglei() {
    }

    /**
     * Create a new {@link Rectanglei} as a copy of the given <code>source</code>.
     *
     * @param source
     *          the {@link Rectanglei} to copy from
     */
    public Rectanglei(Rectanglei source) {
        this.minX = source.minX;
        this.minY = source.minY;
        this.maxX = source.maxX;
        this.maxY = source.maxY;
    }

    /**
     * Create a new {@link Rectanglei} with the given <code>min</code> and <code>max</code> corner coordinates.
     *
     * @param min
     *          the minimum coordinates
     * @param max
     *          the maximum coordinates
     */
    public Rectanglei(Vector2ic min, Vector2ic max) {
        this.minX = min.x();
        this.minY = min.y();
        this.maxX = max.x();
        this.maxY = max.y();
    }

    /**
     * Create a new {@link Rectanglei} with the given minimum and maximum corner coordinates.
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
    public Rectanglei(int minX, int minY, int maxX, int maxY) {
        this.minX = minX;
        this.minY = minY;
        this.maxX = maxX;
        this.maxY = maxY;
    }

    public int left() {
        return Math.min(this.minX, this.maxX);
    }

    public int right() {
        return Math.max(this.minX, this.maxX);
    }

    public int top() {
        return Math.max(this.minY, this.maxY);
    }

    public int bottom() {
        return Math.min(this.minY, this.maxY);
    }

    public int width() {
        return maxX - minX;
    }

    public int height() {
        return maxY - minY;
    }

    public Vector2i size() {
        return new Vector2i(width(),height());
    }

    public int area() {
        return Math.abs(width() * height());
    }

    public boolean empty() {
        return width() == 0 && height() == 0;
    }

    /**
     * Check if this and the given rectangle intersect.
     *
     * @param other
     *          the other rectangle
     * @return <code>true</code> iff both rectangles intersect; <code>false</code> otherwise
     */
    public boolean contains(Rectanglei other) {
        return left() <= other.left() &&
            right() >= other.right() &&
            top() >= other.top() &&
            bottom() >= other.bottom();
    }

    public Rectanglei intersect(Rectanglei other) {
        int minX = Math.max(left(), other.left());
        int maxX = Math.min(right(), other.right());
        int minY = Math.max(bottom(), other.bottom());
        int maxY = Math.min(top(), other.top());
        return new Rectanglei(minX, minY, maxX, maxY);
    }

    public boolean overlaps(Rectanglei other) {
        if (!(empty() || other.empty())) {
            int minX = Math.max(left(), other.left());
            int maxX = Math.min(right(), other.right());
            if (minX > maxX) {
                return false;
            }
            int minY = Math.max(bottom(), other.bottom());
            int maxY = Math.min(top(), other.top());
            return minY <= maxY;
        }
        return false;
    }


    /**
     * Check if this rectangle contains the given <code>point</code>.
     *
     * @param point
     *          the point to test
     * @return <code>true</code> iff this rectangle contains the point; <code>false</code> otherwise
     */
    public boolean contains(Vector2ic point) {
        return contains(point.x(), point.y());
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
    public boolean contains(int x, int y) {
        return x >= left() && x <= right() && y >= bottom() && y <= top();
    }

    /**
     * Translate <code>this</code> by the given vector <code>xy</code>.
     *
     * @param xy
     *          the vector to translate by
     * @return this
     */
    public Rectanglei translate(Vector2ic xy) {
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
    public Rectanglei translate(Vector2ic xy, Rectanglei dest) {
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
    public Rectanglei translate(int x, int y) {
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
    public Rectanglei translate(int x, int y, Rectanglei dest) {
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
     *          the scaling factor in the x and y axis.
     * @return this
     */
    public Rectanglei scale(int sf) {
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
    public Rectanglei scale(int sf, Rectanglei dest) {
        return scale(sf, sf, dest);
    }

    /**
     * Scale <code>this</code> about an anchor.
     * <p>
     * This is effectively equivalent to <br>
     * <pre>
     *     translate(-ax, -ay);
     *     scale(sf);
     *     translate(ax, ay);
     * </pre>
     *
     * @param sf
     *          the scaling factor in the x and y axis
     * @param ax
     *          the x coordinate of the anchor
     * @param ay
     *          the y coordinate of the anchor
     * @return this
     */
    public Rectanglei scale(int sf, int ax, int ay) {
        return scale(sf, sf, ax, ay);
    }

    /**
     * Scale <code>this</code> about an anchor and store the result in <code>dest</code>.
     * <p>
     * This is effectively equivalent to <br>
     * <pre>
     *     translate(-ax, -ay);
     *     scale(sf);
     *     translate(ax, ay);
     * </pre>
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
    public Rectanglei scale(int sf, int ax, int ay, Rectanglei dest) {
        return scale(sf, sf, ax, ay, dest);
    }

    /**
     * Scale <code>this</code> about an anchor.
     * <p>
     * This is effectively equivalent to <br>
     * <pre>
     *     translate(anchor.negate());
     *     scale(sf);
     *     translate(anchor.negate());
     * </pre>
     *
     * @param sf
     *          the scaling factor in the x and y axis
     * @param anchor
     *          the location of the anchor
     * @return this
     */
    public Rectanglei scale(int sf, Vector2ic anchor) {
        return scale(sf, anchor.x(), anchor.y());
    }

    /**
     * Scale <code>this</code> about an anchor and store the result in <code>dest</code>.
     * <p>
     * This is effectively equivalent to <br>
     * <pre>
     *     translate(anchor.negate());
     *     scale(sf);
     *     translate(anchor.negate());
     * </pre>
     *
     * @param sf
     *          the scaling factor in the x and y axis
     * @param anchor
     *          the location of the anchor
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Rectanglei scale(int sf, Vector2ic anchor, Rectanglei dest) {
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
    public Rectanglei scale(int sx, int sy) {
        return scale(sx, sy, 0, 0);
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
    public Rectanglei scale(int sx, int sy, Rectanglei dest) {
        return scale(sx, sy, 0, 0, dest);
    }

    /**
     * Scale <code>this</code> about an anchor.
     * This is equivalent to <br>
     * <pre>
     *     translate(-ax, -ay);
     *     scale(sx, sy);
     *     translate(ax, ay);
     * </pre>
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
    public Rectanglei scale(int sx, int sy, int ax, int ay) {
        minX = (minX - ax) * sx + ax;
        minY = (minY - ay) * sy + ay;
        maxX = (maxX - ax) * sx + ax;
        maxY = (maxY - ay) * sy + ay;
        return this;
    }

    /**
     * Scale <code>this</code> about an anchor.
     * <p>
     * This is equivalent to <br>
     * <pre>
     *     translate(anchor.negate());
     *     scale(sx, sy);
     *     translate(anchor.negate());
     * </pre>
     *
     * @param sx
     *          the scaling factor on the x axis
     * @param sy
     *          the scaling factor on the y axis
     * @param anchor
     *          the location of the anchor
     * @return this
     */
    public Rectanglei scale(int sx, int sy, Vector2ic anchor) {
        return scale(sx, sy, anchor.x(), anchor.y());
    }

    /**
     * Scale <code>this</code> about an anchor and store the result in <code>dest</code>.
     * <p>
     * This is equivalent to <br>
     * <pre>
     *     translate(-ax, -ay);
     *     scale(sx, sy);
     *     translate(ax, ay);
     * </pre>
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
    public Rectanglei scale(int sx, int sy, int ax, int ay, Rectanglei dest) {
        dest.minX = (minX - ax) * sx + ax;
        dest.minY = (minY - ay) * sy + ay;
        dest.maxX = (maxX - ax) * sx + ax;
        dest.maxY = (maxY - ay) * sy + ay;
        return dest;
    }

    /**
     * Scale <code>this</code> about an anchor and store the result in <code>dest</code>.
     * <p>
     * This is equivalent to <br>
     * <pre>
     *     translate(anchor.negate());
     *     scale(sx, sy);
     *     translate(anchor.negate());
     * </pre>
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
    public Rectanglei scale(int sx, int sy, Vector2ic anchor, Rectanglei dest) {
        return scale(sx, sy, anchor.x(), anchor.y(), dest);
    }

    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + maxX;
        result = prime * result + maxY;
        result = prime * result + minX;
        result = prime * result + minY;
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Rectanglei other = (Rectanglei) obj;
        if (maxX != other.maxX)
            return false;
        if (maxY != other.maxY)
            return false;
        if (minX != other.minX)
            return false;
        if (minY != other.minY)
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
        out.writeFloat(minX);
        out.writeFloat(minY);
        out.writeFloat(maxX);
        out.writeFloat(maxY);
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        minX = in.readInt();
        minY = in.readInt();
        maxX = in.readInt();
        maxY = in.readInt();
    }

}
