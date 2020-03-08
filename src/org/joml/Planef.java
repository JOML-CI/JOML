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
 * Represents a 3D plane using single-precision floating-point numbers.
 * 
 * @author Kai Burjack
 */
public class Planef implements Externalizable {

    /**
     * The factor <code>a</code> in the plane equation <code>a*x + b*y + c*z + d = 0</code>.
     */
    public float a;
    /**
     * The factor <code>b</code> in the plane equation <code>a*x + b*y + c*z + d = 0</code>.
     */
    public float b;
    /**
     * The factor <code>c</code> in the plane equation <code>a*x + b*y + c*z + d = 0</code>.
     */
    public float c;
    /**
     * The constant <code>d</code> in the plane equation <code>a*x + b*y + c*z + d = 0</code>.
     */
    public float d;

    /**
     * Create a new undefined {@link Planef}.
     */
    public Planef() {
    }

    /**
     * Create a new {@link Planef} as a copy of the given <code>source</code>.
     * 
     * @param source
     *          the {@link Planef} to copy from
     */
    public Planef(Planef source) {
        this.a = source.a;
        this.b = source.b;
        this.c = source.c;
        this.d = source.d;
    }

    /**
     * Create a new {@link Planef} from the given <code>point</code> lying on the plane and the given <code>normal</code>.
     * 
     * @param point
     *          any point lying on the plane
     * @param normal
     *          the normal of the plane
     */
    public Planef(Vector3fc point, Vector3fc normal) {
        this.a = normal.x();
        this.b = normal.y();
        this.c = normal.z();
        this.d = -a * point.x() - b * point.y() - c * point.z();
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
    public Planef(Vector3fc pointA, Vector3fc pointB, Vector3fc pointC) {
        float abX = pointB.x() - pointA.x(), abY = pointB.y() - pointA.y(), abZ = pointB.z() - pointA.z();
        float acX = pointC.x() - pointA.x(), acY = pointC.y() - pointA.y(), acZ = pointC.z() - pointA.z();
        this.a = abY * acZ - abZ * acY;
        this.b = abZ * acX - abX * acZ;
        this.c = abX * acY - abY * acX;
        this.d = -a * pointA.x() - b * pointA.y() - c * pointA.z();
    }

    /**
     * Create a new {@link Planef} with the plane equation <code>a*x + b*y + c*z + d = 0</code>.
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
    public Planef(float a, float b, float c, float d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
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
    public Planef set(float a, float b, float c, float d) {
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
    public Planef normalize() {
        return normalize(this);
    }

    /**
     * Normalize this plane and store the result in <code>dest</code>.
     * 
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Planef normalize(Planef dest) {
        float invLength = Math.invsqrt(a * a + b * b + c * c);
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
    public float distance(float x, float y, float z) {
        return Intersectionf.distancePointPlane(x, y, z, a, b, c, d);
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
    public static Vector4f equationFromPoints(
            Vector3f v0, Vector3f v1, Vector3f v2,
            Vector4f dest) {
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
    public static Vector4f equationFromPoints(
            float v0X, float v0Y, float v0Z, float v1X, float v1Y, float v1Z, float v2X, float v2Y, float v2Z,
            Vector4f dest) {
        float v1Y0Y = v1Y - v0Y;
        float v2Z0Z = v2Z - v0Z;
        float v2Y0Y = v2Y - v0Y;
        float v1Z0Z = v1Z - v0Z;
        float v2X0X = v2X - v0X;
        float v1X0X = v1X - v0X;
        float a = v1Y0Y * v2Z0Z - v2Y0Y * v1Z0Z;
        float b = v1Z0Z * v2X0X - v2Z0Z * v1X0X;
        float c = v1X0X * v2Y0Y - v2X0X * v1Y0Y;
        float d = -(a * v0X + b * v0Y + c * v0Z);
        dest.x = a;
        dest.y = b;
        dest.z = c;
        dest.w = d;
        return dest;
    }

    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Float.floatToIntBits(a);
        result = prime * result + Float.floatToIntBits(b);
        result = prime * result + Float.floatToIntBits(c);
        result = prime * result + Float.floatToIntBits(d);
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Planef other = (Planef) obj;
        if (Float.floatToIntBits(a) != Float.floatToIntBits(other.a))
            return false;
        if (Float.floatToIntBits(b) != Float.floatToIntBits(other.b))
            return false;
        if (Float.floatToIntBits(c) != Float.floatToIntBits(other.c))
            return false;
        if (Float.floatToIntBits(d) != Float.floatToIntBits(other.d))
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
        out.writeFloat(a);
        out.writeFloat(b);
        out.writeFloat(c);
        out.writeFloat(d);
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        a = in.readFloat();
        b = in.readFloat();
        c = in.readFloat();
        d = in.readFloat();
    }

}
