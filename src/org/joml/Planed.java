/*
 * (C) Copyright 2017 JOML

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

/**
 * Represents a 3D plane.
 * 
 * @author Kai Burjack
 */
public class Planed {

    public double a, b, c, d;

    /**
     * Create a new undefined {@link Planed}.
     */
    public Planed() {
    }

    /**
     * Create a new {@link Planed} as a copy of the given <code>source</code>.
     * 
     * @param source
     *          the {@link Planed} to copy from
     */
    public Planed(Planed source) {
        this.a = source.a;
        this.b = source.b;
        this.c = source.c;
        this.d = source.d;
    }

    /**
     * Create a new {@link Planed} from the given <code>point</code> lying on the plane and the given <code>normal</code>.
     * 
     * @param point
     *          any point lying on the plane
     * @param normal
     *          the normal of the plane
     */
    public Planed(Vector3dc point, Vector3dc normal) {
        this.a = normal.x();
        this.b = normal.y();
        this.c = normal.z();
        this.d = -a * point.x() - b * point.y() - c * point.z();
    }

    /**
     * Create a new {@link Planed} with the plane equation <tt>a*x + b*y + c*z + d = 0</tt>.
     * 
     * @param a
     *          the x factor in the plane equation
     * @param b
     *          the y factor in the plane equation
     * @param c
     *          the z factor in the plane equation
     * @param d
     *          the constant in the plane equation
     */
    public Planed(double a, double b, double c, double d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    public int hashCode() {
        final int prime = 31;
        int result = 1;
        long temp;
        temp = Double.doubleToLongBits(a);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(b);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(c);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(d);
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
        Planed other = (Planed) obj;
        if (Double.doubleToLongBits(a) != Double.doubleToLongBits(other.a))
            return false;
        if (Double.doubleToLongBits(b) != Double.doubleToLongBits(other.b))
            return false;
        if (Double.doubleToLongBits(c) != Double.doubleToLongBits(other.c))
            return false;
        if (Double.doubleToLongBits(d) != Double.doubleToLongBits(other.d))
            return false;
        return true;
    }

    /**
     * Return a string representation of this plane.
     * <p>
     * This method creates a new {@link DecimalFormat} on every invocation with the format string "<tt>0.000E0;-</tt>".
     * 
     * @return the string representation
     */
    public String toString() {
        return Runtime.formatNumbers(toString(Options.NUMBER_FORMAT));
    }

    /**
     * Return a string representation of this plane by formatting the components with the given {@link NumberFormat}.
     * 
     * @param formatter
     *          the {@link NumberFormat} used to format the components with
     * @return the string representation
     */
    public String toString(NumberFormat formatter) {
        return "[" + formatter.format(a) + " " + formatter.format(b) + " " + formatter.format(c) + " " + formatter.format(d) + "]";
    }

}
