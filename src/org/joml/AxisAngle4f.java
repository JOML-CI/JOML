/*
 * (C) Copyright 2015-2016 Kai Burjack

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
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Represents a 3D rotation of a given radians about an axis represented as an
 * unit 3D vector.
 * <p>
 * This class uses single-precision components.
 * 
 * @author Kai Burjack
 */
public class AxisAngle4f implements Externalizable {

    private static final long serialVersionUID = 1L;

    /**
     * The angle in radians.
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
     * Create a new {@link AxisAngle4f} with zero rotation about <tt>(0, 0, 1)</tt>.
     */
    public AxisAngle4f() {
        z = 1.0f;
    }

    /**
     * Create a new {@link AxisAngle4f} with the same values of <code>a</code>.
     * 
     * @param a
     *            the AngleAxis4f to copy the values from
     */
    public AxisAngle4f(AxisAngle4f a) {
        x = a.x;
        y = a.y;
        z = a.z;
        angle = (float) ((a.angle < 0.0 ? Math.PI + Math.PI + a.angle % (Math.PI + Math.PI) : a.angle) % (Math.PI + Math.PI));
    }

    /**
     * Create a new {@link AxisAngle4f} from the given {@link Quaternionf}.
     * <p>
     * Reference: <a href=
     * "http://www.euclideanspace.com/maths/geometry/rotations/conversions/quaternionToAngle/"
     * >http://www.euclideanspace.com</a>
     * 
     * @param q
     *            the quaternion from which to create the new AngleAxis4f
     */
    public AxisAngle4f(Quaternionf q) {
        float acos = (float) Math.acos(q.w);
        float invSqrt = (float) (1.0 / Math.sqrt(1.0 - q.w * q.w));
        this.x = q.x * invSqrt;
        this.y = q.y * invSqrt;
        this.z = q.z * invSqrt;
        this.angle = acos + acos;
    }

    /**
     * Create a new {@link AxisAngle4f} with the given values.
     *
     * @param angle
     *            the angle in radians
     * @param x
     *            the x-coordinate of the rotation axis
     * @param y
     *            the y-coordinate of the rotation axis
     * @param z
     *            the z-coordinate of the rotation axis
     */
    public AxisAngle4f(float angle, float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.angle = (float) ((angle < 0.0 ? Math.PI + Math.PI + angle % (Math.PI + Math.PI) : angle) % (Math.PI + Math.PI));
    }

    /**
     * Create a new {@link AxisAngle4f} with the given values.
     *
     * @param angle the angle in radians
     * @param v     the rotation axis as a {@link Vector3f}
     */
    public AxisAngle4f(float angle, Vector3f v) {
        this(angle, v.x, v.y, v.z);
    }

    /**
     * Set this {@link AxisAngle4f} to the values of <code>a</code>.
     * 
     * @param a
     *            the AngleAxis4f to copy the values from
     * @return this
     */
    public AxisAngle4f set(AxisAngle4f a) {
        x = a.x;
        y = a.y;
        z = a.z;
        angle = a.angle;
        angle = (float) ((angle < 0.0 ? Math.PI + Math.PI + angle % (Math.PI + Math.PI) : angle) % (Math.PI + Math.PI));
        return this;
    }

    /**
     * Set this {@link AxisAngle4f} to the given values.
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
    public AxisAngle4f set(float angle, float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.angle = (float) ((angle < 0.0 ? Math.PI + Math.PI + angle % (Math.PI + Math.PI) : angle) % (Math.PI + Math.PI));
        return this;
    }

    /**
     * Set this {@link AxisAngle4f} to the given values.
     *
     * @param angle
     *            the angle in radians
     * @param v    
     *            the rotation axis as a {@link Vector3f}
     * @return this
     */
    public AxisAngle4f set(float angle, Vector3f v) {
        return set(angle, v.x, v.y, v.z);
    }

    /**
     * Set this {@link AxisAngle4f} to be equivalent to the given
     * {@link Quaternionf}.
     * 
     * @param q
     *            the quaternion to set this AngleAxis4f from
     * @return this
     */
    public AxisAngle4f set(Quaternionf q) {
        double acos = Math.acos(q.w);
        double invSqrt = 1.0 / Math.sqrt(1.0 - q.w * q.w);
        this.x = (float) (q.x * invSqrt);
        this.y = (float) (q.y * invSqrt);
        this.z = (float) (q.z * invSqrt);
        this.angle = (float) (acos + acos);
        return this;
    }

    /**
     * Set this {@link AxisAngle4f} to be equivalent to the given
     * {@link Quaterniond}.
     * 
     * @param q
     *            the quaternion to set this AngleAxis4f from
     * @return this
     */
    public AxisAngle4f set(Quaterniond q) {
        double acos = Math.acos(q.w);
        double invSqrt = 1.0 / Math.sqrt(1.0 - q.w * q.w);
        this.x = (float) (q.x * invSqrt);
        this.y = (float) (q.y * invSqrt);
        this.z = (float) (q.z * invSqrt);
        this.angle = (float) (acos + acos);
        return this;
    }

    /**
     * Set this {@link AxisAngle4f} to be equivalent to the rotation 
     * of the given {@link Matrix3f}.
     * 
     * @param m
     *            the Matrix3f to set this AngleAxis4f from
     * @return this
     */
    public AxisAngle4f set(Matrix3f m) {
        double cos = (m.m00 + m.m11 + m.m22 - 1.0)*0.5;
        x = m.m12 - m.m21;
        y = m.m20 - m.m02;
        z = m.m01 - m.m10;
        double sin = 0.5*Math.sqrt(x*x + y*y + z*z);
        angle = (float) Math.atan2(sin, cos);
        return this;
    }

    /**
     * Set this {@link AxisAngle4f} to be equivalent to the rotation 
     * of the given {@link Matrix3d}.
     * 
     * @param m
     *            the Matrix3d to set this AngleAxis4f from
     * @return this
     */
    public AxisAngle4f set(Matrix3d m) {
        double cos = (m.m00 + m.m11 + m.m22 - 1.0)*0.5;
        x = (float) (m.m12 - m.m21);
        y = (float) (m.m20 - m.m02);
        z = (float) (m.m01 - m.m10);
        double sin = 0.5*Math.sqrt(x*x + y*y + z*z);
        angle = (float) Math.atan2(sin, cos);
        return this;
    }

    /**
     * Set this {@link AxisAngle4f} to be equivalent to the rotational component 
     * of the given {@link Matrix4f}.
     * 
     * @param m
     *            the Matrix4f to set this AngleAxis4f from
     * @return this
     */
    public AxisAngle4f set(Matrix4f m) {
        double cos = (m.m00 + m.m11 + m.m22 - 1.0)*0.5;
        x = m.m12 - m.m21;
        y = m.m20 - m.m02;
        z = m.m01 - m.m10;
        double sin = 0.5*Math.sqrt(x*x + y*y + z*z);
        angle = (float) Math.atan2(sin, cos);
        return this;
    }

    /**
     * Set this {@link AxisAngle4f} to be equivalent to the rotational component 
     * of the given {@link Matrix4d}.
     * 
     * @param m
     *            the Matrix4d to set this AngleAxis4f from
     * @return this
     */
    public AxisAngle4f set(Matrix4d m) {
        double cos = (m.m00 + m.m11 + m.m22 - 1.0)*0.5;
        x = (float) (m.m12 - m.m21);
        y = (float) (m.m20 - m.m02);
        z = (float) (m.m01 - m.m10);
        double sin = 0.5*Math.sqrt(x*x + y*y + z*z);
        angle = (float) Math.atan2(sin, cos);
        return this;
    }

    /**
     * Set the given {@link Quaternionf} to be equivalent to this {@link AxisAngle4f} rotation.
     * 
     * @see Quaternionf#set(AxisAngle4f)
     * 
     * @param q
     *          the quaternion to set
     * @return q
     */
    public Quaternionf get(Quaternionf q) {
        return q.set(this);
    }

    /**
     * Set the given {@link Quaterniond} to be equivalent to this {@link AxisAngle4f} rotation.
     * 
     * @see Quaterniond#set(AxisAngle4f)
     * 
     * @param q
     *          the quaternion to set
     * @return q
     */
    public Quaterniond get(Quaterniond q) {
        return q.set(this);
    }

    /**
     * Set the given {@link Matrix4f} to a rotation transformation equivalent to this {@link AxisAngle4f}.
     * 
     * @see Matrix4f#set(AxisAngle4f)
     * 
     * @param m
     *          the matrix to set
     * @return m
     */
    public Matrix4f get(Matrix4f m) {
        return m.set(this);
    }

    /**
     * Set the given {@link Matrix3f} to a rotation transformation equivalent to this {@link AxisAngle4f}.
     * 
     * @see Matrix3f#set(AxisAngle4f)
     * 
     * @param m
     *          the matrix to set
     * @return m
     */
    public Matrix3f get(Matrix3f m) {
        return m.set(this);
    }

    /**
     * Set the given {@link Matrix4d} to a rotation transformation equivalent to this {@link AxisAngle4f}.
     * 
     * @see Matrix4f#set(AxisAngle4f)
     * 
     * @param m
     *          the matrix to set
     * @return m
     */
    public Matrix4d get(Matrix4d m) {
        return m.set(this);
    }

    /**
     * Set the given {@link Matrix3d} to a rotation transformation equivalent to this {@link AxisAngle4f}.
     * 
     * @see Matrix3f#set(AxisAngle4f)
     * 
     * @param m
     *          the matrix to set
     * @return m
     */
    public Matrix3d get(Matrix3d m) {
        return m.set(this);
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
    public AxisAngle4f normalize() {
        float invLength = (float) (1.0 / Math.sqrt(x * x + y * y + z * z));
        x *= invLength;
        y *= invLength;
        z *= invLength;
        return this;
    }

    /**
     * Increase the rotation angle by the given amount.
     * <p>
     * This method also takes care of wrapping around.
     * 
     * @param ang
     *          the angle increase
     * @return this
     */
    public AxisAngle4f rotate(float ang) {
        angle += ang;
        angle = (float) ((angle < 0.0 ? Math.PI + Math.PI + angle % (Math.PI + Math.PI) : angle) % (Math.PI + Math.PI));
        return this;
    }

    /**
     * Transform the given vector by the rotation transformation described by this {@link AxisAngle4f}.
     * 
     * @param v
     *          the vector to transform
     * @return v
     */
    public Vector3f transform(Vector3f v) {
        return transform(v, v);
    }

    /**
     * Transform the given vector by the rotation transformation described by this {@link AxisAngle4f}
     * and store the result in <code>dest</code>.
     * 
     * @param v
     *          the vector to transform
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Vector3f transform(Vector3f v, Vector3f dest) {
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);
        float dot = x * v.x + y * v.y + z * v.z;
        dest.set((float) (v.x * cos + sin * (y * v.z - z * v.y) + (1.0 - cos) * dot * x),
                 (float) (v.y * cos + sin * (z * v.x - x * v.z) + (1.0 - cos) * dot * y),
                 (float) (v.z * cos + sin * (x * v.y - y * v.x) + (1.0 - cos) * dot * z));
        return dest;
    }

    /**
     * Transform the given vector by the rotation transformation described by this {@link AxisAngle4f}.
     * 
     * @param v
     *          the vector to transform
     * @return v
     */
    public Vector4f transform(Vector4f v) {
        return transform(v, v);
    }

    /**
     * Transform the given vector by the rotation transformation described by this {@link AxisAngle4f}
     * and store the result in <code>dest</code>.
     * 
     * @param v
     *          the vector to transform
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Vector4f transform(Vector4f v, Vector4f dest) {
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);
        float dot = x * v.x + y * v.y + z * v.z;
        dest.set((float) (v.x * cos + sin * (y * v.z - z * v.y) + (1.0 - cos) * dot * x),
                 (float) (v.y * cos + sin * (z * v.x - x * v.z) + (1.0 - cos) * dot * y),
                 (float) (v.z * cos + sin * (x * v.y - y * v.x) + (1.0 - cos) * dot * z),
                 dest.w);
        return dest;
    }

    /**
     * Return a string representation of this {@link AxisAngle4f}.
     * <p>
     * This method creates a new {@link DecimalFormat} on every invocation with the format string "<tt> 0.000E0;-</tt>".
     * 
     * @return the string representation
     */
    public String toString() {
        DecimalFormat formatter = new DecimalFormat(" 0.000E0;-"); //$NON-NLS-1$
        return toString(formatter).replaceAll("E(\\d+)", "E+$1"); //$NON-NLS-1$ //$NON-NLS-2$
    }

    /**
     * Return a string representation of this {@link AxisAngle4f} by formatting the components with the given {@link NumberFormat}.
     * 
     * @param formatter
     *          the {@link NumberFormat} used to format the vector components with
     * @return the string representation
     */
    public String toString(NumberFormat formatter) {
        return "(" + formatter.format(x) + formatter.format(y) + formatter.format(z) + " <|" + formatter.format(angle) + " )"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
    }

    public int hashCode() {
        final int prime = 31;
        int result = 1;
        float nangle = (float) ((angle < 0.0 ? Math.PI + Math.PI + angle % (Math.PI + Math.PI) : angle) % (Math.PI + Math.PI));
        result = prime * result + Float.floatToIntBits(nangle);
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
        AxisAngle4f other = (AxisAngle4f) obj;
        float nangle = (float) ((angle < 0.0 ? Math.PI + Math.PI + angle % (Math.PI + Math.PI) : angle) % (Math.PI + Math.PI));
        float nangleOther = (float) ((other.angle < 0.0 ? Math.PI + Math.PI + other.angle % (Math.PI + Math.PI) : other.angle) % (Math.PI + Math.PI));
        if (Float.floatToIntBits(nangle) != Float.floatToIntBits(nangleOther))
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
