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
 * Represents a 3D sphere.
 * 
 * @author Kai Burjack
 */
public class Spheref {

    public float x, y, z, r;

    /**
     * Create a new {@link Spheref} with center position <code>(0, 0, 0)</code> and radius = <code>0</code>.
     */
    public Spheref() {
    }

    /**
     * Create a new {@link Spheref} as a copy of the given <code>source</code>.
     * 
     * @param source
     *          the {@link Spheref} to copy from
     */
    public Spheref(Spheref source) {
        this.x = source.x;
        this.y = source.y;
        this.z = source.z;
        this.r = source.r;
    }

    /**
     * Create a new {@link Spheref} with center position <code>(x, y, z)</code> and radius <code>r</code>.
     * 
     * @param x
     *          the x coordinate of the sphere's center
     * @param y
     *          the y coordinate of the sphere's center
     * @param z
     *          the z coordinate of the sphere's center
     * @param r
     *          the radius of the sphere
     */
    public Spheref(float x, float y, float z, float r) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.r = r;
    }

    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Float.floatToIntBits(r);
        result = prime * result + Float.floatToIntBits(x);
        result = prime * result + Float.floatToIntBits(y);
        result = prime * result + Float.floatToIntBits(z);
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Spheref other = (Spheref) obj;
        if (Float.floatToIntBits(r) != Float.floatToIntBits(other.r))
            return false;
        if (Float.floatToIntBits(x) != Float.floatToIntBits(other.x))
            return false;
        if (Float.floatToIntBits(y) != Float.floatToIntBits(other.y))
            return false;
        if (Float.floatToIntBits(z) != Float.floatToIntBits(other.z))
            return false;
        return true;
    }

    /**
     * Return a string representation of this sphere.
     * <p>
     * This method creates a new {@link DecimalFormat} on every invocation with the format string "<code>0.000E0;-</code>".
     * 
     * @return the string representation
     */
    public String toString() {
        return Runtime.formatNumbers(toString(Options.NUMBER_FORMAT));
    }

    /**
     * Return a string representation of this sphere by formatting the components with the given {@link NumberFormat}.
     * 
     * @param formatter
     *          the {@link NumberFormat} used to format the components with
     * @return the string representation
     */
    public String toString(NumberFormat formatter) {
        return "[" + formatter.format(x) + " " + formatter.format(y) + " " + formatter.format(z) + " " + formatter.format(r) + "]";
    }

}
