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
 * Represents a 3D plane using double-precision floating-point numbers.
 * 
 * @author Kai Burjack
 */
public class Planed implements Externalizable {

    /**
     * The factor <code>a</code> in the plane equation <code>a*x + b*y + c*z + d = 0</code>.
     */
    public double a;
    /**
     * The factor <code>b</code> in the plane equation <code>a*x + b*y + c*z + d = 0</code>.
     */
    public double b;
    /**
     * The factor <code>c</code> in the plane equation <code>a*x + b*y + c*z + d = 0</code>.
     */
    public double c;
    /**
     * The constant <code>d</code> in the plane equation <code>a*x + b*y + c*z + d = 0</code>.
     */
    public double d;

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
     * Create a new {@link Planed} with the plane equation <code>a*x + b*y + c*z + d = 0</code>.
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

    /**
     * Create a new {@link Planef} from the given three points lying on the plane.
     * <p>
     * The resulting plane is not necessarily {@link #normalize() normalized}.
     * 
     * @param pointA
     *          the first point
     * @param pointB
     *          the second point
     * @param pointC
     *          the third point
     */
    public Planed(Vector3dc pointA, Vector3dc pointB, Vector3dc pointC) {
        double abX = pointB.x() - pointA.x(), abY = pointB.y() - pointA.y(), abZ = pointB.z() - pointA.z();
        double acX = pointC.x() - pointA.x(), acY = pointC.y() - pointA.y(), acZ = pointC.z() - pointA.z();
        this.a = abY * acZ - abZ * acY;
        this.b = abZ * acX - abX * acZ;
        this.c = abX * acY - abY * acX;
        this.d = -a * pointA.x() - b * pointA.y() - c * pointA.z();
    }

    /**
     * Create a new {@link Planef} from the given three points lying on the plane.
     * <p>
     * The resulting plane is not necessarily {@link #normalize() normalized}.
     * 
     * @param pointA
     *          the first point
     * @param pointB
     *          the second point
     * @param pointC
     *          the third point
     */
    public Planed(Vector3fc pointA, Vector3fc pointB, Vector3fc pointC) {
        double abX = pointB.x() - pointA.x(), abY = pointB.y() - pointA.y(), abZ = pointB.z() - pointA.z();
        double acX = pointC.x() - pointA.x(), acY = pointC.y() - pointA.y(), acZ = pointC.z() - pointA.z();
        this.a = abY * acZ - abZ * acY;
        this.b = abZ * acX - abX * acZ;
        this.c = abX * acY - abY * acX;
        this.d = -a * pointA.x() - b * pointA.y() - c * pointA.z();
    }

    /**
     * Set the components of this plane. 
     * 
     * @param a
     *          the x factor in the plane equation
     * @param b
     *          the y factor in the plane equation
     * @param c
     *          the z factor in the plane equation
     * @param d
     *          the constant in the plane equation
     * @return this
     */
    public Planed set(double a, double b, double c, double d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        return this;
    }

    /**
     * Normalize this plane.
     * 
     * @return this
     */
    public Planed normalize() {
        return normalize(this);
    }

    /**
     * Normalize this plane and store the result in <code>dest</code>.
     * 
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Planed normalize(Planed dest) {
        double invLength = Math.invsqrt(a * a + b * b + c * c);
        dest.a = a * invLength;
        dest.b = b * invLength;
        dest.c = c * invLength;
        dest.d = d * invLength;
        return dest;
    }

    /**
     * Compute the signed distance between this plane and the given point.
     * 
     * @param x
     *          the x coordinate of the point
     * @param y
     *          the y coordinate of the point
     * @param z
     *          the z coordinate of the point
     * @return the signed distance between this plane and the point
     */
    public double distance(double x, double y, double z) {
        return Intersectiond.distancePointPlane(x, y, z, a, b, c, d);
    }

    /**
     * Compute the factors <code>a</code>, <code>b</code>, <code>c</code> and <code>d</code> in the plane equation
     * <code>a*x + b*y + c*z + d = 0</code> from the given three points on the plane, and write the values
     * to the <code>x</code>, <code>y</code>, <code>z</code> and <code>w</code> components, respectively, of the given
     * <code>dest</code> vector.
     * 
     * @param v0
     *          the first point on the plane
     * @param v1
     *          the second point on the plane
     * @param v2
     *          the third point on the plane
     * @param dest
     *          will hold the result
     * @return dest
     */
    public static Vector4d equationFromPoints(
            Vector3d v0, Vector3d v1, Vector3d v2,
            Vector4d dest) {
        return equationFromPoints(v0.x, v0.y, v0.z, v1.x, v1.y, v1.z, v2.x, v2.y, v2.z, dest);
    }

    /**
     * Compute the factors <code>a</code>, <code>b</code>, <code>c</code> and <code>d</code> in the plane equation
     * <code>a*x + b*y + c*z + d = 0</code> from the three points <code>(v0X, v0Y, v0Z)</code>, <code>(v1X, v1Y, v1Z)</code> and
     * <code>(v2X, v2Y, v2Z)</code> on the plane, and write the values to the <code>x</code>, <code>y</code>, <code>z</code> 
     * and <code>w</code> components, respectively, of the given <code>dest</code> vector.
     * 
     * @param v0X
     *          the x coordinate of the first point on the plane
     * @param v0Y
     *          the y coordinate of the first point on the plane
     * @param v0Z
     *          the z coordinate of the first point on the plane
     * @param v1X
     *          the x coordinate of the second point on the plane
     * @param v1Y
     *          the y coordinate of the second point on the plane
     * @param v1Z
     *          the z coordinate of the second point on the plane
     * @param v2X
     *          the x coordinate of the third point on the plane
     * @param v2Y
     *          the y coordinate of the third point on the plane
     * @param v2Z
     *          the z coordinate of the third point on the plane
     * @param dest
     *          will hold the result
     * @return dest
     */
    public static Vector4d equationFromPoints(
            double v0X, double v0Y, double v0Z, double v1X, double v1Y, double v1Z, double v2X, double v2Y, double v2Z,
            Vector4d dest) {
        double v1Y0Y = v1Y - v0Y;
        double v2Z0Z = v2Z - v0Z;
        double v2Y0Y = v2Y - v0Y;
        double v1Z0Z = v1Z - v0Z;
        double v2X0X = v2X - v0X;
        double v1X0X = v1X - v0X;
        double a = v1Y0Y * v2Z0Z - v2Y0Y * v1Z0Z;
        double b = v1Z0Z * v2X0X - v2Z0Z * v1X0X;
        double c = v1X0X * v2Y0Y - v2X0X * v1Y0Y;
        double d = -(a * v0X + b * v0Y + c * v0Z);
        dest.x = a;
        dest.y = b;
        dest.z = c;
        dest.w = d;
        return dest;
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
     * This method creates a new {@link DecimalFormat} on every invocation with the format string "<code>0.000E0;-</code>".
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
        return "[" + Runtime.format(a, formatter) + " " + Runtime.format(b, formatter) + " " + Runtime.format(c, formatter) + " " + Runtime.format(d, formatter) + "]";
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeDouble(a);
        out.writeDouble(b);
        out.writeDouble(c);
        out.writeDouble(d);
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        a = in.readDouble();
        b = in.readDouble();
        c = in.readDouble();
        d = in.readDouble();
    }

}
