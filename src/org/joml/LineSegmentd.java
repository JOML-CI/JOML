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
 * Represents an undirected line segment between two points.
 * 
 * @author Kai Burjack
 */
public class LineSegmentd implements Externalizable {

    /**
     * The x coordinate of the first point.
     */
    public double aX;
    /**
     * The y coordinate of the first point.
     */
    public double aY;
    /**
     * The z coordinate of the first point.
     */
    public double aZ;
    /**
     * The x coordinate of the second point.
     */
    public double bX;
    /**
     * The y coordinate of the second point.
     */
    public double bY;
    /**
     * The z coordinate of the second point.
     */
    public double bZ;

    /**
     * Create a new {@link LineSegmentd} of zero length on the point <code>(0, 0, 0)</code>.
     */
    public LineSegmentd() {
    }

    /**
     * Create a new {@link LineSegmentd} as a copy of the given <code>source</code>.
     * 
     * @param source
     *          the {@link LineSegmentd} to copy from
     */
    public LineSegmentd(LineSegmentd source) {
        this.aX = source.aX;
        this.aY = source.aY;
        this.aZ = source.aZ;
        this.aX = source.bX;
        this.bY = source.bY;
        this.bZ = source.bZ;
    }

    /**
     * Create a new {@link LineSegmentd} between the given two points.
     * 
     * @param a
     *          the first point
     * @param b
     *          the second point
     */
    public LineSegmentd(Vector3dc a, Vector3dc b) {
        this.aX = a.x();
        this.aY = a.y();
        this.aZ = a.z();
        this.bX = b.x();
        this.bY = b.y();
        this.bZ = b.z();
    }

    /**
     * Create a new {@link LineSegmentd} between the two points.
     * 
     * @param aX
     *          the x coordinate of the first point
     * @param aY
     *          the y coordinate of the first point
     * @param aZ
     *          the z coordinate of the first point
     * @param bX
     *          the x coordinate of the second point
     * @param bY
     *          the y coordinate of the second point
     * @param bZ
     *          the z coordinate of the second point
     */
    public LineSegmentd(double aX, double aY, double aZ, double bX, double bY, double bZ) {
        super();
        this.aX = aX;
        this.aY = aY;
        this.aZ = aZ;
        this.bX = bX;
        this.bY = bY;
        this.bZ = bZ;
    }

    public int hashCode() {
        final int prime = 31;
        int result = 1;
        long temp;
        temp = Double.doubleToLongBits(aX);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(aY);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(aZ);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(bX);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(bY);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(bZ);
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
        LineSegmentd other = (LineSegmentd) obj;
        if (Double.doubleToLongBits(aX) != Double.doubleToLongBits(other.aX))
            return false;
        if (Double.doubleToLongBits(aY) != Double.doubleToLongBits(other.aY))
            return false;
        if (Double.doubleToLongBits(aZ) != Double.doubleToLongBits(other.aZ))
            return false;
        if (Double.doubleToLongBits(bX) != Double.doubleToLongBits(other.bX))
            return false;
        if (Double.doubleToLongBits(bY) != Double.doubleToLongBits(other.bY))
            return false;
        if (Double.doubleToLongBits(bZ) != Double.doubleToLongBits(other.bZ))
            return false;
        return true;
    }

    /**
     * Return a string representation of this line segment.
     * <p>
     * This method creates a new {@link DecimalFormat} on every invocation with the format string "<code>0.000E0;-</code>".
     * 
     * @return the string representation
     */
    public String toString() {
        return Runtime.formatNumbers(toString(Options.NUMBER_FORMAT));
    }

    /**
     * Return a string representation of this line segment by formatting the vector components with the given {@link NumberFormat}.
     * 
     * @param formatter
     *          the {@link NumberFormat} used to format the vector components with
     * @return the string representation
     */
    public String toString(NumberFormat formatter) {
        return "(" + Runtime.format(aX, formatter) + " " + Runtime.format(aY, formatter) + " " + Runtime.format(aZ, formatter) + ") - "
             + "(" + Runtime.format(bX, formatter) + " " + Runtime.format(bY, formatter) + " " + Runtime.format(bZ, formatter) + ")";
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeDouble(aX);
        out.writeDouble(aY);
        out.writeDouble(aZ);
        out.writeDouble(bX);
        out.writeDouble(bY);
        out.writeDouble(bZ);
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        aX = in.readDouble();
        aY = in.readDouble();
        aZ = in.readDouble();
        bX = in.readDouble();
        bY = in.readDouble();
        bZ = in.readDouble();
    }

}
