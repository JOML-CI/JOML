/*
 * (C) Copyright 2015 Kai Burjack

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

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Represents a 3D rotation of a given degree about an axis represented as an
 * unit 3D vector.
 * 
 * @author Kai Burjack
 */
public class AngleAxis4f implements Serializable, Externalizable {

    private static final long serialVersionUID = 1L;

    /**
     * The angle in degrees.
     */
    public float angle;

    /**
     * The x-component of the rotation axis.
     */
    public float x;

    /**
     * The y-component of the rotation axis.
     */
    public float y;

    /**
     * The z-component of the rotation axis.
     */
    public float z;

    /**
     * Create a new {@link AngleAxis4f} with zero rotation about (0, 0, 1).
     */
    public AngleAxis4f() {
        z = 1.0f;
    }

    /**
     * Create a new {@link AngleAxis4f} with the same values of <code>a</code>.
     * 
     * @param a
     *            the {@link AngleAxis4f} to copy the values from
     */
    public AngleAxis4f(AngleAxis4f a) {
        x = a.x;
        y = a.y;
        z = a.z;
        angle = a.angle;
    }

    /**
     * Create a new {@link AngleAxis4f} from the given {@link Quaternion}.
     * <p>
     * Reference: <a href=
     * "http://www.euclideanspace.com/maths/geometry/rotations/conversions/quaternionToAngle/"
     * >http://www.euclideanspace.com</a>
     * 
     * @param q
     *            the {@link Quaternion}
     */
    public AngleAxis4f(Quaternion q) {
        float acos = (float) Math.acos(q.w);
        float sqrt = (float) Math.sqrt(1.0 - q.w * q.w);
        this.x = q.x / sqrt;
        this.y = q.y / sqrt;
        this.z = q.z / sqrt;
        this.angle = (float) Math.toDegrees(2.0 * acos);
    }

    /**
     * Create a new {@link AngleAxis4f} with the given values.
     *
     * @param angle
     *            the angle in degrees
     * @param x
     *            the x-coordinate of the rotation axis
     * @param y
     *            the y-coordinate of the rotation axis
     * @param z
     *            the z-coordinate of the rotation axis
     */
    public AngleAxis4f(float angle, float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.angle = angle;
    }

    /**
     * Set this {@link AngleAxis4f} to the values of <code>a</code>.
     * 
     * @param a
     *            the {@link AngleAxis4f} to copy the values from
     * @return this
     */
    public AngleAxis4f set(AngleAxis4f a) {
        x = a.x;
        y = a.y;
        z = a.z;
        angle = a.angle;
        return this;
    }

    /**
     * Set this {@link AngleAxis4f} to the given values.
     * 
     * @param angle
     *            the angle in radians
     * @param x
     *            the x-coordinate of the rotation axis
     * @param y
     *            the y-coordinate of the rotation axis
     * @param z
     *            the z-coordinate of the rotation axis
     * @return this
     */
    public AngleAxis4f set(float angle, float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.angle = angle;
        return this;
    }

    /**
     * Set this {@link AngleAxis4f} to be equivalent to the given
     * {@link Quaternion}.
     * 
     * @param q
     *            the {@link Quaternion}
     * @return this
     */
    public AngleAxis4f set(Quaternion q) {
        float acos = (float) Math.acos(q.w);
        float sqrt = (float) Math.sqrt(1.0 - q.w * q.w);
        this.x = q.x / sqrt;
        this.y = q.y / sqrt;
        this.z = q.z / sqrt;
        this.angle = (float) Math.toDegrees(2.0f * acos);
        return this;
    }

    /**
     * Set this {@link AngleAxis4f} to be equivalent to the rotation 
     * of the given {@link Matrix3f}.
     * 
     * @param m
     *            the {@link Matrix3f}
     * @return this
     */
    public AngleAxis4f set(Matrix3f m) {
        double cos = (m.m00 + m.m11 + m.m22 - 1.0)*0.5;
        x = (float)(m.m12 - m.m21);
        y = (float)(m.m20 - m.m02);
        z = (float)(m.m01 - m.m10);
        double sin = 0.5*Math.sqrt(x*x + y*y + z*z);
        angle = (float) Math.toDegrees(Math.atan2(sin, cos));
        return this;
    }

    /**
     * Set this {@link AngleAxis4f} to be equivalent to the rotational component 
     * of the given {@link Matrix4f}.
     * 
     * @param m
     *            the {@link Matrix4f}
     * @return this
     */
    public AngleAxis4f set(Matrix4f m) {
        double cos = (m.m00 + m.m11 + m.m22 - 1.0)*0.5;
        x = (float)(m.m12 - m.m21);
        y = (float)(m.m20 - m.m02);
        z = (float)(m.m01 - m.m10);
        double sin = 0.5*Math.sqrt(x*x + y*y + z*z);
        angle = (float) Math.toDegrees(Math.atan2(sin, cos));
        return this;
    }

    /**
     * Set the given {@link Quaternion} to be equivalent to this {@link AngleAxis4f} rotation.
     * 
     * @see Quaternion#set(AngleAxis4f)
     * 
     * @param q
     *          the {@link Quaternion}
     * @return this
     */
    public AngleAxis4f get(Quaternion q) {
        q.set(this);
        return this;
    }

    /**
     * Set the given {@link Matrix4f} to a rotation transformation equivalent to this {@link AngleAxis4f}.
     * 
     * @see Matrix4f#set(AngleAxis4f)
     * 
     * @param m
     *          the matrix to set
     * @return this
     */
    public AngleAxis4f get(Matrix4f m) {
        m.set(this);
        return this;
    }

    /**
     * Set the given {@link Matrix3f} to a rotation transformation equivalent to this {@link AngleAxis4f}.
     * 
     * @see Matrix3f#set(AngleAxis4f)
     * 
     * @param m
     *          the matrix to set
     * @return this
     */
    public AngleAxis4f get(Matrix3f m) {
        m.set(this);
        return this;
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeFloat(angle);
        out.writeFloat(x);
        out.writeFloat(y);
        out.writeFloat(z);
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        angle = in.readFloat();
        x = in.readFloat();
        y = in.readFloat();
        z = in.readFloat();
    }

    /**
     * Normalize the axis vector.
     * 
     * @return this
     */
    public AngleAxis4f normalize() {
        float length = (float) Math.sqrt(x * x + y * y + z * z);
        x /= length;
        y /= length;
        z /= length;
        return this;
    }

    /**
     * Transform the given vector by the rotation transformation described by this {@link AngleAxis4f}.
     * 
     * @param v
     *          the vector to transform
     * @return this
     */
    public AngleAxis4f transform(Vector3f v) {
        return transform(v, v);
    }

    /**
     * Transform the given vector by the rotation transformation described by this {@link AngleAxis4f}
     * and store the result in <code>dest</code>.
     * 
     * @param v
     *          the vector to transform
     * @param dest
     *          will hold the result
     * @return this
     */
    public AngleAxis4f transform(Vector3f v, Vector3f dest) {
        double cos = Math.cos(Math.toRadians(angle));
        double sin = Math.sin(Math.toRadians(angle));
        float dot = x * v.x + y * v.y + z * v.z;
        dest.set((float) (v.x * cos + sin * (y * v.z - z * v.y) + (1.0 - cos) * dot * x),
                 (float) (v.y * cos + sin * (z * v.x - x * v.z) + (1.0 - cos) * dot * y),
                 (float) (v.z * cos + sin * (x * v.y - y * v.x) + (1.0 - cos) * dot * z));
        return this;
    }

    /**
     * Return a string representation of this angle-axis.
     * <p>
     * This method creates a new {@link DecimalFormat} on every invocation with the format string "<tt> 0.000E0;-</tt>".
     * 
     * @return the string representation
     */
    public String toString() {
        DecimalFormat formatter = new DecimalFormat(" 0.000E0;-");
        return toString(formatter).replaceAll("E(\\d+)", "E+$1");
    }

    /**
     * Return a string representation of this angle-axis by formatting the components with the given {@link NumberFormat}.
     * 
     * @param formatter
     *          the {@link NumberFormat} used to format the vector components with
     * @return the string representation
     */
    public String toString(NumberFormat formatter) {
        return "(" + formatter.format(x) + formatter.format(y) + formatter.format(z) + " <|" + formatter.format(angle) + " )";
    }

    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Float.floatToIntBits(angle);
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
        AngleAxis4f other = (AngleAxis4f) obj;
        if (Float.floatToIntBits(angle) != Float.floatToIntBits(other.angle))
            return false;
        if (Float.floatToIntBits(x) != Float.floatToIntBits(other.x))
            return false;
        if (Float.floatToIntBits(y) != Float.floatToIntBits(other.y))
            return false;
        if (Float.floatToIntBits(z) != Float.floatToIntBits(other.z))
            return false;
        return true;
    }

}
