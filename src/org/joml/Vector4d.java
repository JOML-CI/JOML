/*
 * (C) Copyright 2015 Richard Greenlees

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
 * Contains the definition of a Vector comprising 4 floats and associated transformations.
 * 
 * @author Richard Greenlees
 * @author Kai Burjack
 */
public class Vector4d implements Serializable, Externalizable {

    private static final long serialVersionUID = 1L;   

    public double x;
    public double y;
    public double z;
    public double w = 1.0;

    /**
     * Create a new {@link Vector4d} of <code>(0, 0, 0, 1)</code>.
     */
    public Vector4d() {
    }

    /**
     * Create a new {@link Vector4d} with the same values as <code>v</code>.
     * 
     * @param v
     *            the {@link Vector4d} to copy the values from
     */
    public Vector4d(Vector4d v) {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
        this.w = v.w;
    }

    /**
     * Create a new {@link Vector4d} with the first three components from the
     * given <code>v</code> and the given <code>w</code>.
     * 
     * @param v
     *            the {@link Vector3d}
     * @param w
     *            the w value
     */
    public Vector4d(Vector3d v, double w) {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
        this.w = w;
    }

    /**
     * Create a new {@link Vector4d} with the same values as <code>v</code>.
     * 
     * @param v
     *            the {@link Vector4f} to copy the values from
     */
    public Vector4d(Vector4f v) {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
        this.w = v.w;
    }

    /**
     * Create a new {@link Vector4d} with the first three components from the
     * given <code>v</code> and the given <code>w</code>.
     * 
     * @param v
     *            the {@link Vector3f}
     * @param w
     *            the w value
     */
    public Vector4d(Vector3f v, double w) {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
        this.w = w;
    }

    /**
     * Create a new {@link Vector4f} with the given component values.
     */
    public Vector4d(double x, double y, double z, double w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    /**
     * Set this {@link Vector4d} to the values of the given <code>v</code>.
     * 
     * @param v
     *            the vector whose values will be copied into this
     * @return this
     */
    public Vector4d set(Vector4d v) {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
        this.w = v.w;
        return this;
    }

    /**
     * Set this {@link Vector4d} to the values of the given <code>v</code>.
     * 
     * @param v
     *            the vector whose values will be copied into this
     * @return this
     */
    public Vector4d set(Vector4f v) {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
        this.w = v.w;
        return this;
    }

    /**
     * Set the first three components of this to the components of
     * <code>v</code> and the last component to <code>w</code>.
     * 
     * @param v
     *            the {@link Vector3d} to copy
     * @param w
     *            the w component
     * @return this
     */
    public Vector4d set(Vector3d v, double w) {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
        this.w = w;
        return this;
    }

    /**
     * Set the first three components of this to the components of
     * <code>v</code> and the last component to <code>w</code>.
     * 
     * @param v
     *            the {@link Vector3f} to copy
     * @param w
     *            the w component
     * @return this
     */
    public Vector4d set(Vector3f v, double w) {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
        this.w = w;
        return this;
    }

    /**
     * Set the components of this vector to the given values.
     * 
     * @param x
     *            the x-component
     * @param y
     *            the y-component
     * @param z
     *            the z-component
     * @param w
     *            the w component
     * @return this
     */
    public Vector4d set(double x, double y, double z, double w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
        return this;
    }

    /**
     * Subtract the supplied vector from this one.
     * 
     * @return this
     */
    public Vector4d sub(Vector4d v) {
        x -= v.x;
        y -= v.y;
        z -= v.z;
        w -= v.w;
        return this;
    }

    /**
     * Subtract the supplied vector from this one.
     * 
     * @return this
     */
    public Vector4d sub(Vector4f v) {
        x -= v.x;
        y -= v.y;
        z -= v.z;
        w -= v.w;
        return this;
    }

    /**
     * Subtract <tt>(x, y, z, w)</tt> vector from this one.
     * 
     * @return this
     */
    public Vector4d sub(double x, double y, double z, double w) {
        this.x -= x;
        this.y -= y;
        this.z -= z;
        this.w -= w;
        return this;
    }

    /**
     * Subtract <code>v2</code> from <code>v1</code> and store the result in <code>dest</code>.
     * 
     * @param v1
     *          the left operand
     * @param v2
     *          the right operand
     * @param dest
     *          will hold the result
     */
    public static void sub(Vector4d v1, Vector4d v2, Vector4d dest) {
        dest.x = v1.x - v2.x;
        dest.y = v1.y - v2.y;
        dest.z = v1.z - v2.z;
        dest.w = v1.w - v2.w;
    }

    /**
     * Subtract <code>v2</code> from <code>v1</code> and store the result in <code>dest</code>.
     * 
     * @param v1
     *          the left operand
     * @param v2
     *          the right operand
     * @param dest
     *          will hold the result
     */
    public static void sub(Vector4d v1, Vector4f v2, Vector4d dest) {
        dest.x = v1.x - v2.x;
        dest.y = v1.y - v2.y;
        dest.z = v1.z - v2.z;
        dest.w = v1.w - v2.w;
    }

    /**
     * Subtract <code>v2</code> from <code>v1</code> and store the result in <code>dest</code>.
     * 
     * @param v1
     *          the left operand
     * @param v2
     *          the right operand
     * @param dest
     *          will hold the result
     */
    public static void sub(Vector4f v1, Vector4d v2, Vector4d dest) {
        dest.x = v1.x - v2.x;
        dest.y = v1.y - v2.y;
        dest.z = v1.z - v2.z;
        dest.w = v1.w - v2.w;
    }

    /**
     * Add the supplied vector to this one.
     * 
     * @return this
     */
    public Vector4d add(Vector4d v) {
        x += v.x;
        y += v.y;
        z += v.z;
        w += v.w;
        return this;
    }

    /**
     * Add the supplied vector to this one.
     * 
     * @return this
     */
    public Vector4d add(Vector4f v) {
        x += v.x;
        y += v.y;
        z += v.z;
        w += v.w;
        return this;
    }

    /**
     * Add <code>v2</code> to <code>v1</code> and store the result in <code>dest</code>.
     */
    public static void add(Vector4d v1, Vector4d v2, Vector4d dest) {
        dest.x = v1.x + v2.x;
        dest.y = v1.y + v2.y;
        dest.z = v1.z + v2.z;
        dest.w = v1.w + v2.w;
    }

    /**
     * Add <code>v2</code> to <code>v1</code> and store the result in <code>dest</code>.
     */
    public static void add(Vector4d v1, Vector4f v2, Vector4d dest) {
        dest.x = v1.x + v2.x;
        dest.y = v1.y + v2.y;
        dest.z = v1.z + v2.z;
        dest.w = v1.w + v2.w;
    }

    /**
     * Add <code>v2</code> to <code>v1</code> and store the result in <code>dest</code>.
     */
    public static void add(Vector4f v1, Vector4d v2, Vector4d dest) {
        dest.x = v1.x + v2.x;
        dest.y = v1.y + v2.y;
        dest.z = v1.z + v2.z;
        dest.w = v1.w + v2.w;
    }

    /**
     * Add the component-wise multiplication of <code>a * b</code> to this vector.
     * 
     * @param a
     *          the first multiplicand
     * @param b
     *          the second multiplicand
     * @return this
     */
    public Vector4d fma(Vector4d a, Vector4d b) {
        x += a.x * b.x;
        y += a.y * b.y;
        z += a.z * b.z;
        w += a.w * b.w;
        return this;
    }

    /**
     * Add the component-wise multiplication of <code>a * b</code> to this vector
     * and store the result in <code>dest</code>.
     * 
     * @param a
     *          the first multiplicand
     * @param b
     *          the second multiplicand
     * @param dest
     *          will hold the result
     * @return this
     */
    public Vector4d fma(Vector4d a, Vector4d b, Vector4d dest) {
        dest.x = x + a.x * b.x;
        dest.y = y + a.y * b.y;
        dest.z = z + a.z * b.z;
        dest.w = w + a.w * b.w;
        return this;
    }

    /**
     * Multiply this {@link Vector4d} component-wise by the given {@link Vector4d}.
     * 
     * @return this
     */
    public Vector4d mul(Vector4d v) {
        x *= v.x;
        y *= v.y;
        z *= v.z;
        z *= v.w;
        return this;
    }

    /**
     * Multiply this {@link Vector4d} component-wise by the given {@link Vector4f}.
     * 
     * @return this
     */
    public Vector4d mul(Vector4f v) {
        x *= v.x;
        y *= v.y;
        z *= v.z;
        z *= v.w;
        return this;
    }

    /**
     * Multiply <code>v1</code> component-wise by <code>v2</code> and store the result in <code>dest</code>.
     */
    public static void mul(Vector4d v1, Vector4d v2, Vector4d dest) {
        dest.x = v1.x * v2.x;
        dest.y = v1.y * v2.y;
        dest.z = v1.z * v2.z;
        dest.w = v1.w * v2.w;
    }

    /**
     * Multiply this {@link Vector4d} by the given matrix <code>mat</code>.
     * 
     * @return this
     */
    public Vector4d mul(Matrix4d mat) {
        return mul(mat, this);
    }

    /**
     * Multiply this {@link Vector4d} by the given matrix mat and store the result in <code>dest</code>.
     * 
     * @param mat
     * @param dest
     * @return this
     */
    public Vector4d mul(Matrix4d mat, Vector4d dest) {
        if (this != dest) {
            dest.x = mat.m00 * x + mat.m10 * y + mat.m20 * z + mat.m30 * w;
            dest.y = mat.m01 * x + mat.m11 * y + mat.m21 * z + mat.m31 * w;
            dest.z = mat.m02 * x + mat.m12 * y + mat.m22 * z + mat.m32 * w;
            dest.w = mat.m03 * x + mat.m13 * y + mat.m23 * z + mat.m33 * w;  
        } else {
            dest.set(mat.m00 * x + mat.m10 * y + mat.m20 * z + mat.m30 * w,
                     mat.m01 * x + mat.m11 * y + mat.m21 * z + mat.m31 * w,
                     mat.m02 * x + mat.m12 * y + mat.m22 * z + mat.m32 * w, 
                     mat.m03 * x + mat.m13 * y + mat.m23 * z + mat.m33 * w);
        }
        return this;
    }

    /**
     * Multiply this {@link Vector4d} by the given matrix <code>mat</code>.
     * 
     * @return this
     */
    public Vector4d mul(Matrix4f mat) {
        return mul(mat, this);
    }

    /**
     * Multiply this Vector4d by the given matrix mat and store the result in <code>dest</code>.
     * 
     * @param mat
     * @param dest
     * @return this
     */
    public Vector4d mul(Matrix4f mat, Vector4d dest) {
        if (this != dest) {
            dest.x = mat.m00 * x + mat.m10 * y + mat.m20 * z + mat.m30 * w;
            dest.y = mat.m01 * x + mat.m11 * y + mat.m21 * z + mat.m31 * w;
            dest.z = mat.m02 * x + mat.m12 * y + mat.m22 * z + mat.m32 * w;
            dest.w = mat.m03 * x + mat.m13 * y + mat.m23 * z + mat.m33 * w;
        } else {
            dest.set(mat.m00 * x + mat.m10 * y + mat.m20 * z + mat.m30 * w,
                     mat.m01 * x + mat.m11 * y + mat.m21 * z + mat.m31 * w,
                     mat.m02 * x + mat.m12 * y + mat.m22 * z + mat.m32 * w, 
                     mat.m03 * x + mat.m13 * y + mat.m23 * z + mat.m33 * w);
        }
        return this;
    }

    /**
     * Multiply this Vector4d by the given scalar value.
     * 
     * @return this
     */
    public Vector4d mul(double scalar) {
        x *= scalar;
        y *= scalar;
        z *= scalar;
        w *= scalar;
        return this;
    }

    /**
     * Multiply the given Vector4d <code>v</code> component-wise by the <code>scalar</code> value,
     * and store the result in <code>dest</code>.
     */
    public static void mul(Vector4d v, double scalar, Vector4d dest) {
        dest.x = v.x * scalar;
        dest.y = v.y * scalar;
        dest.z = v.z * scalar;
        dest.w = v.w * scalar;
    }

    /**
     * Multiply this vector by the given quaternion <code>quat</code> and store the result in <code>this</code>.
     * 
     * @see Quaterniond#transform(Vector4d)
     * 
     * @param quat
     *          the quaternion to multiply this vector by
     * @return this
     */
    public Vector4d mul(Quaterniond quat) {
        return mul(quat, this);
    }

    /**
     * Multiply this vector by the given quaternion <code>quat</code> and store the result in <code>dest</code>.
     * 
     * @see Quaterniond#transform(Vector4d)
     * 
     * @param quat
     *          the quaternion to multiply this vector by
     * @param dest
     *          will hold the result
     * @return this
     */
    public Vector4d mul(Quaterniond quat, Vector4d dest) {
        quat.transform(this, dest);
        return this;
    }

    /**
     * Return the length squared of this vector.
     */
    public double lengthSquared() {
        return x * x + y * y + z * z + w * w;
    }

    /**
     * Return the length of this vector.
     */
    public double length() {
        return Math.sqrt(lengthSquared());
    }

    /**
     * Normalizes this vector
     */
    public void normalize() {
        double d = length();
        x /= d;
        y /= d;
        z /= d;
        w /= d;
    }

    /**
     * Normalize the original vector and store the result in <code>dest</code>.
     * 
     * @param original
     *          the vector to normalize
     * @param dest
     *          will hold the result
     */
    public static void normalize(Vector4d original, Vector4d dest) {
        double d = original.length();
        dest.set(original.x / d,
                original.y / d,
                original.z / d,
                original.w / d);
    }

    /**
     * Return the euclidean distance between <code>start</code> and <code>end</code>.
     */
    public static double distance(Vector4d start, Vector4d end) {
        return Math.sqrt((end.x - start.x) * (end.x - start.x)
                + (end.y - start.y) * (end.y - start.y)
                + (end.z - start.z) * (end.z - start.z)
                + (end.w - start.w) * (end.w - start.w));
    }

    /**
     * Return the euclidean distance between <code>this</code> and <code>v</code>.
     */
    public double distance(Vector4d v) {
        return Math.sqrt((v.x - this.x) * (v.x - this.x)
                + (v.y - this.y) * (v.y - this.y)
                + (v.z - this.z) * (v.z - this.z)
                + (v.w - this.w) * (v.w - this.w));
    }

    /**
     * Return the dot product of the supplied <code>v1</code> and
     * <code>v2</code> vectors.
     */
    public static double dot(Vector4d v1, Vector4d v2) {
        return v1.x * v2.x + v1.y * v2.y + v1.z * v2.z + v1.w * v2.w;
    }

    /**
     * Return the dot product of the supplied <code>v1</code> and
     * <code>v2</code> vectors.
     * 
     * @return the dot product
     */
    public static float dot(Vector4f v1, Vector4f v2) {
        return v1.x * v2.x + v1.y * v2.y + v1.z * v2.z + v1.w * v2.w;
    }
    
    /**
     * Return the cosinus of the angle between this vector and the supplied vector. Use this instead of Math.cos(this.angle(v)).
     * @return the cosinus of the angle
     * @see #angle(Vector4d)
     */
    public double angleCos(Vector4d v) {
        return angleCos(this, v);
    }
    
    /**
     * Return the cosinus of the angle between the supplied vectors. Use this instead of Math.cos(angle(v1, v2)).
     * @return the cosinus of the angle
     * @see #angle(Vector4d, Vector4d)
     */
    public static double angleCos(Vector4d v1, Vector4d v2) {
        double length1 = Math.sqrt((v1.x * v1.x) + (v1.y * v1.y) + (v1.z * v1.z) + (v1.w * v1.w));
        double length2 = Math.sqrt((v2.x * v2.x) + (v2.y * v2.y) + (v2.z * v2.z) + (v2.w * v2.w));
        double dot = (v1.x * v2.x) + (v1.y * v2.y) + (v1.z * v2.z) + (v1.w * v2.w);
        return dot / (length1 * length2);
    }
    
    /**
     * Return the angle between this vector and the supplied vector.
     * @return the angle, in radians
     * @see #angleCos(Vector4d)
     */
    public double angle(Vector4d v) {
        return angle(this, v);
    }
    
    /**
     * Return the angle between the supplied vectors.
     * @return the angle, in radians
     * @see #angleCos(Vector4d, Vector4d)
     */
    public static double angle(Vector4d v1, Vector4d v2) {
        double cos = angleCos(v1, v2);
        // This is because sometimes cos goes above 1 or below -1 because of lost precision
        cos = Math.min(cos, 1);
        cos = Math.max(cos, -1);
        return Math.acos(cos);
    }

    /**
     * Set all components to zero.
     * 
     * @return this
     */
    public Vector4d zero() {
        this.x = 0.0;
        this.y = 0.0;
        this.z = 0.0;
        this.w = 0.0;
        return this;
    }
    
    /**
     * Negate this vector.
     * 
     * @return this
     */
    public Vector4d negate() {
        x = -x;
        y = -y;
        z = -z;
        w = -w;
        return this;
    }
    
    /**
     * Negate original and store the result in dest.
     */
    public static void negate(Vector4d original, Vector4d dest) {
        dest.x = -original.x;
        dest.y = -original.y;
        dest.z = -original.z;
        dest.w = -original.w;
    }

    /**
     * Return a string representation of this vector.
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
     * Return a string representation of this vector by formatting the vector components with the given {@link NumberFormat}.
     * 
     * @param formatter
     *          the {@link NumberFormat} used to format the vector components with
     * @return the string representation
     */
    public String toString(NumberFormat formatter) {
        return "(" + formatter.format(x) + " " + formatter.format(y) + " " + formatter.format(z) + " " + formatter.format(w) + ")";
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeDouble(x);
        out.writeDouble(y);
        out.writeDouble(z);
    }

    public void readExternal(ObjectInput in) throws IOException,
            ClassNotFoundException {
        x = in.readDouble();
        y = in.readDouble();
        z = in.readDouble();
    }

    public int hashCode() {
        final int prime = 31;
        int result = 1;
        long temp;
        temp = Double.doubleToLongBits(w);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(x);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(z);
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
        Vector4d other = (Vector4d) obj;
        if (Double.doubleToLongBits(w) != Double.doubleToLongBits(other.w))
            return false;
        if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
            return false;
        if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y))
            return false;
        if (Double.doubleToLongBits(z) != Double.doubleToLongBits(other.z))
            return false;
        return true;
    }

    /**
     * Compute a smooth-step (i.e. hermite with zero tangents) interpolation
     * between <code>this</code> vector and the given vector <code>v</code> and
     * store the result in <code>dest</code>.
     * 
     * @param v
     *            the other vector
     * @param t
     *            the interpolation factor, within <tt>[0..1]</tt>
     * @param dest
     *            will hold the result
     * @return this
     */
    public Vector4d smoothStep(Vector4d v, double t, Vector4d dest) {
        dest.x = Interpolate.smoothStep(x, v.x, t);
        dest.y = Interpolate.smoothStep(y, v.y, t);
        dest.z = Interpolate.smoothStep(x, v.z, t);
        dest.w = Interpolate.smoothStep(w, v.w, t);
        return this;
    }

    /**
     * Compute a hermite interpolation between <code>this</code> vector and its
     * associated tangent <code>t0</code> and the given vector <code>v</code>
     * with its tangent <code>t1</code> and store the result in
     * <code>dest</code>.
     * 
     * @param t0
     *            the tangent of <code>this</code> vector
     * @param v1
     *            the other vector
     * @param t1
     *            the tangent of the other vector
     * @param t
     *            the interpolation factor, within <tt>[0..1]</tt>
     * @param dest
     *            will hold the result
     */
    public Vector4d hermite(Vector4d t0, Vector4d v1, Vector4d t1, double t, Vector4d dest) {
        dest.x = Interpolate.hermite(x, t0.x, v1.x, t1.x, t);
        dest.y = Interpolate.hermite(y, t0.y, v1.y, t1.y, t);
        dest.z = Interpolate.hermite(z, t0.z, v1.z, t1.z, t);
        dest.w = Interpolate.hermite(z, t0.w, v1.w, t1.w, t);
        return this;
    }

}
