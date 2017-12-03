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
package org.joml.api;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Represents a 3D plane.
 *
 * @author Kai Burjack
 */
public abstract class Planedc {

    /**
     * @return the x factor in the plane equation
     */
    public abstract double a();

    /**
     * @return the y factor in the plane equation
     */
    public abstract double b();

    /**
     * @return the z factor in the plane equation
     */
    public abstract double c();

    /**
     * @return the constant factor in the plane equation
     */
    public abstract double d();

    /**
     * Set the components of this plane.
     *
     * @param a the x factor in the plane equation
     * @param b the y factor in the plane equation
     * @param c the z factor in the plane equation
     * @param d the constant in the plane equation
     * @return this
     */
    public abstract Planedc set(double a, double b, double c, double d);

    /**
     * Normalize this plane.
     *
     * @return this
     */
    public abstract Planedc normalize();

    public abstract int hashCode();

    public abstract boolean equals(Object obj);

    /**
     * Return a string representation of this plane.
     * <p>
     * This method creates a new {@link DecimalFormat} on every invocation with the format string "<tt>0.000E0;-</tt>".
     *
     * @return the string representation
     */
    public abstract String toString();

    /**
     * Return a string representation of this plane by formatting the components with the given {@link NumberFormat}.
     *
     * @param formatter the {@link NumberFormat} used to format the components with
     * @return the string representation
     */
    public abstract String toString(NumberFormat formatter);
}
