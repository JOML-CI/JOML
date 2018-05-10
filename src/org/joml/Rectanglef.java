/*
 * (C) Copyright 2017-2018 JOML

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

import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.joml.internal.Options;
import org.joml.internal.Runtime;

/**
 * Represents a 2D axis-aligned rectangle.
 * 
 * @author Kai Burjack
 */
public class Rectanglef {

    public float minX, minY;
    public float maxX, maxY;

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
        super();
        this.minX = minX;
        this.minY = minY;
        this.maxX = maxX;
        this.maxY = maxY;
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
        return "(" + formatter.format(minX) + " " + formatter.format(minY) + ") < "
             + "(" + formatter.format(maxX) + " " + formatter.format(maxY) + ")";
    }

}
