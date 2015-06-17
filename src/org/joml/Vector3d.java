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
 * Contains the definition of a Vector comprising 3 doubles and associated
 * transformations.
 *
 * @author Richard Greenlees
 * @author Kai Burjack
 */
public class Vector3d implements Serializable, Externalizable {

    private static final long serialVersionUID = 1L;   

    public double x;
    public double y;
    public double z;

    /**
     * Create a new {@link Vector3d} with all components set to zero.
     */
    public Vector3d() {
    }

    /**
     * Create a new {@link Vector3d} with the given component values.
     * 
     * @param x
     * @param y
     * @param z
     */
    public Vector3d(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Create a new {@link Vector3d} whose values will be copied from the given vector.
     * 
     * @param v
     *          provides the initial values for the new vector
     */
    public Vector3d(Vector3f v) {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
    }

    /**
     * Create a new {@link Vector3d} whose values will be copied from the given vector.
     * 
     * @param v
     *          provides the initial values for the new vector
     */
    public Vector3d(Vector3d v) {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
    }

    /**
     * Set the x, y and z attributes to match the supplied vector.
     * 
     * @param v
     *          the vector to set this vector's components from
     * @return this
     */
    public Vector3d set(Vector3d v) {
        x = v.x;
        y = v.y;
        z = v.z;
        return this;
    }

    /**
     * Set the x, y and z attributes to match the supplied vector.
     * 
     * @param v
     *          the vector to set this vector's components from
     * @return this
     */
    public Vector3d set(Vector3f v) {
        x = v.x;
        y = v.y;
        z = v.z;
        return this;
    }

    /**
     * Set the x, y and z attributes to the supplied float values.
     * 
     * @return this
     */
    public Vector3d set(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }

    /**
     * Subtract the supplied vector from this one.
     * 
     * @return this
     */
    public Vector3d sub(Vector3d v) {
        x -= v.x;
        y -= v.y;
        z -= v.z;
        return this;
    }

    /**
     * Subtract the supplied vector from this one.
     * 
     * @return this
     */
    public Vector3d sub(Vector3f v) {
        x -= v.x;
        y -= v.y;
        z -= v.z;
        return this;
    }

    /**
     * Subtract <tt>(x, y, z)</tt> from this Vector2d.
     * 
     * @return this
     */
    public Vector3d sub(double x, double y, double z) {
        this.x -= x;
        this.y -= y;
        this.z -= z;
        return this;
    }

    /**
     * Subtract v2 from v1 and store the result in dest.
     */
    public static void sub(Vector3d v1, Vector3d v2, Vector3d dest) {
        dest.x = v1.x - v2.x;
        dest.y = v1.y - v2.y;
        dest.z = v1.z - v2.z;
    }

    /**
     * Subtract v2 from v1 and store the result in dest.
     */
    public static void sub(Vector3d v1, Vector3f v2, Vector3d dest) {
        dest.x = v1.x - v2.x;
        dest.y = v1.y - v2.y;
        dest.z = v1.z - v2.z;
    }

    /**
     * Subtract v2 from v1 and store the result in dest.
     */
    public static void sub(Vector3f v1, Vector3d v2, Vector3d dest) {
        dest.x = v1.x - v2.x;
        dest.y = v1.y - v2.y;
        dest.z = v1.z - v2.z;
    }

    /**
     * Add the supplied vector to this one.
     * 
     * @return this
     */
    public Vector3d add(Vector3d v) {
        x += v.x;
        y += v.y;
        z += v.z;
        return this;
    }

    /**
     * Add the supplied vector to this one.
     * 
     * @return this
     */
    public Vector3d add(Vector3f v) {
        x += v.x;
        y += v.y;
        z += v.z;
        return this;
    }

    /**
     * Add v2 to v1 and store the result in dest.
     */
    public static void add(Vector3d v1, Vector3d v2, Vector3d dest) {
        dest.x = v1.x + v2.x;
        dest.y = v1.y + v2.y;
        dest.z = v1.z + v2.z;
    }

    /**
     * Add v2 to v1 and store the result in dest.
     */
    public static void add(Vector3d v1, Vector3f v2, Vector3d dest) {
        dest.x = v1.x + v2.x;
        dest.y = v1.y + v2.y;
        dest.z = v1.z + v2.z;
    }

    /**
     * Add v2 to v1 and store the result in dest.
     */
    public static void add(Vector3f v1, Vector3d v2, Vector3d dest) {
        dest.x = v1.x + v2.x;
        dest.y = v1.y + v2.y;
        dest.z = v1.z + v2.z;
    }

    /**
     * Multiply this Vector3d component-wise by another Vector3d.
     * 
     * @return this
     */
    public Vector3d mul(Vector3d v) {
        x *= v.x;
        y *= v.y;
        z *= v.z;
        return this;
    }

    /**
     * Multiply this Vector3d component-wise by another Vector3f.
     * 
     * @return this
     */
    public Vector3d mul(Vector3f v) {
        x *= v.x;
        y *= v.y;
        z *= v.z;
        return this;
    }

    /**
     * Multiply v1 by v2 component-wise and store the result into dest.
     */
    public static void mul(Vector3d v1, Vector3d v2, Vector3d dest) {
        dest.x = v1.x * v2.x;
        dest.y = v1.y * v2.y;
        dest.z = v1.z * v2.z;
    }

    /**
     * Multiply this Vector3d by the given matrix <code>mat</code>.
     * 
     * @return this
     */
    public Vector3d mul(Matrix4f mat) {
        mul(this, mat, this);
        return this;
    }

    /**
     * Multiply this Vector3d by the given matrix <code>mat</code>.
     * 
     * @return this
     */
    public Vector3d mul(Matrix4d mat) {
        mul(this, mat, this);
        return this;
    }

    /**
     * Multiply Vector3d v by the given matrix <code>mat</code> and store the
     * result in <code>dest</code>.
     */
    public static void mul(Vector3d v, Matrix4d mat, Vector3d dest) {
        if (v != dest) {
            dest.x = mat.m00 * v.x + mat.m10 * v.y + mat.m20 * v.z;
            dest.y = mat.m01 * v.x + mat.m11 * v.y + mat.m21 * v.z;
            dest.z = mat.m02 * v.x + mat.m12 * v.y + mat.m22 * v.z;
        } else {
            dest.set(mat.m00 * v.x + mat.m10 * v.y + mat.m20 * v.z,
                     mat.m01 * v.x + mat.m11 * v.y + mat.m21 * v.z,
                     mat.m02 * v.x + mat.m12 * v.y + mat.m22 * v.z);
        }
    }

    /**
     * Multiply v by the given matrix mat and store the result in dest.
     */
    public static void mul(Vector3d v, Matrix4f mat, Vector3d dest) {
        if (v != dest) {
            dest.x = mat.m00 * v.x + mat.m10 * v.y + mat.m20 * v.z;
            dest.y = mat.m01 * v.x + mat.m11 * v.y + mat.m21 * v.z;
            dest.z = mat.m02 * v.x + mat.m12 * v.y + mat.m22 * v.z;
        } else {
            dest.set(mat.m00 * v.x + mat.m10 * v.y + mat.m20 * v.z,
                     mat.m01 * v.x + mat.m11 * v.y + mat.m21 * v.z,
                     mat.m02 * v.x + mat.m12 * v.y + mat.m22 * v.z);
        }
    }

    /**
     * Multiply this Vector3d by the given matrix <code>mat</code>.
     * 
     * @return this
     */
    public Vector3d mul(Matrix3f mat) {
        mul(this, mat, this);
        return this;
    }

    /**
     * Multiply this Vector3d by the given matrix <code>mat</code>.
     * 
     * @return this
     */
    public Vector3d mul(Matrix3d mat) {
        mul(this, mat, this);
        return this;
    }

    /**
     * Multiply <code>v</code> by the given matrix <code>mat</code> and store the
     * result in <code>dest</code>.
     */
    public static void mul(Vector3d v, Matrix3d mat, Vector3d dest) {
        if (v != dest) {
            dest.x = mat.m00 * v.x + mat.m10 * v.y + mat.m20 * v.z;
            dest.y = mat.m01 * v.x + mat.m11 * v.y + mat.m21 * v.z;
            dest.z = mat.m02 * v.x + mat.m12 * v.y + mat.m22 * v.z;
        } else {
            dest.set(mat.m00 * v.x + mat.m10 * v.y + mat.m20 * v.z,
                    mat.m01 * v.x + mat.m11 * v.y + mat.m21 * v.z,
                    mat.m02 * v.x + mat.m12 * v.y + mat.m22 * v.z);
        }
    }

    /**
     * Multiply <code>v</code> by the given matrix <code>mat</code> and store the
     * result in <code>dest</code>.
     */
    public static void mul(Vector3d v, Matrix3f mat, Vector3d dest) {
        if (v != dest) {
            dest.x = mat.m00 * v.x + mat.m10 * v.y + mat.m20 * v.z;
            dest.y = mat.m01 * v.x + mat.m11 * v.y + mat.m21 * v.z;
            dest.z = mat.m02 * v.x + mat.m12 * v.y + mat.m22 * v.z;
        } else {
            dest.set(mat.m00 * v.x + mat.m10 * v.y + mat.m20 * v.z,
                    mat.m01 * v.x + mat.m11 * v.y + mat.m21 * v.z,
                    mat.m02 * v.x + mat.m12 * v.y + mat.m22 * v.z);
        }
    }

    /**
     * Multiply this Vector3d by the given scalar value.
     * 
     * @return this
     */
    public Vector3d mul(double scalar) {
        x *= scalar;
        y *= scalar;
        z *= scalar;
        return this;
    }

    /**
     * Multiply <code>v</code> by the <code>scalar</code> value and store the result in <code>dest</code>.
     */
    public static void mul(Vector3d v, double scalar, Vector3d dest) {
        dest.x = v.x * scalar;
        dest.y = v.y * scalar;
        dest.z = v.z * scalar;
    }

    /**
     * Multiply <code>v</code> by the <code>scalar</code> value and store the result in <code>dest</code>.
     */
    public static void mul(Vector3f v, double scalar, Vector3d dest) {
        dest.x = v.x * scalar;
        dest.y = v.y * scalar;
        dest.z = v.z * scalar;
    }

    /**
     * Return the length squared of this vector.
     */
    public double lengthSquared() {
        return x * x + y * y + z * z;
    }

    /**
     * Return the length of this vector.
     */
    public double length() {
        return Math.sqrt(lengthSquared());
    }

    /**
     * Normalize this vector.
     * 
     * @return this
     */
    public Vector3d normalize() {
        double d = length();
        x /= d;
        y /= d;
        z /= d;
        return this;
    }

    /**
     * Normalize the original vector and store the result in dest.
     */
    public static void normalize(Vector3d original, Vector3d dest) {
        double d = original.length();
        dest.set(original.x / d,
                 original.y / d,
                 original.z / d);
    }

    /**
     * Set this vector to be the cross product of v1 and v2.
     * 
     * @return this
     */
    public Vector3d cross(Vector3d v1, Vector3d v2) {
        set(v1.y * v2.z - v1.z * v2.y,
            v1.z * v2.x - v1.x * v2.z,
            v1.x * v2.y - v1.y * v2.x);
        return this;
    }

    /**
     * Calculate the cross product of v1 and v2 and store the result in dest.
     */
    public static void cross(Vector3d v1, Vector3d v2, Vector3d dest) {
        dest.set(v1.y * v2.z - v1.z * v2.y,
                 v1.z * v2.x - v1.x * v2.z,
                 v1.x * v2.y - v1.y * v2.x);
    }

    /**
     * Return the distance between the start and end vectors.
     */
    public static double distance(Vector3d start, Vector3d end) {
        return Math.sqrt(
                  (end.x - start.x) * (end.x - start.x)
                + (end.y - start.y) * (end.y - start.y)
                + (end.z - start.z) * (end.z - start.z));
    }
    
    /**
     * Return the distance between this vector and <code>v</code>.
     */
    public double distance(Vector3d v) {
        return Math.sqrt(
                  (v.x - this.x) * (v.x - this.x)
                + (v.y - this.y) * (v.y - this.y)
                + (v.z - this.z) * (v.z - this.z));
    }

    /**
     * Return the dot product of the supplied v1 and v2 vectors.
     */
    public static double dot(Vector3d v1, Vector3d v2) {
        return v1.x * v2.x + v1.y * v2.y + v1.z * v2.z;
    }

    /**
     * Return the dot product of this vector and the supplied vector.
     */
    public double dot(Vector3d v) {
        return x * v.x + y * v.y + z * v.z;
    }

    /**
     * Return the cosinus of the angle between this vector and the supplied vector. Use this instead of Math.cos(this.angle(v)).
     * @return the cosinus of the angle
     * @see #angle(Vector3d)
     */
    public double angleCos(Vector3d v) {
    	return angleCos(this, v);
    }
    
    /**
     * Return the cosinus of the angle between the supplied vectors. Use this instead of Math.cos(angle(v1, v2)).
     * @return the cosinus of the angle
     * @see #angle(Vector3d, Vector3d)
     */
    public static double angleCos(Vector3d v1, Vector3d v2) {
    	double length1 = Math.sqrt((v1.x * v1.x) + (v1.y * v1.y) + (v1.z * v1.z));
    	double length2 = Math.sqrt((v2.x * v2.x) + (v2.y * v2.y) + (v2.z * v2.z));
    	double dot = (v1.x * v2.x) + (v1.y * v2.y) + (v1.z * v2.z);
    	return dot / (length1 * length2);
    }
    
    /**
     * Return the angle between this vector and the supplied vector.
     * @return the angle, in radians
     * @see #angleCos(Vector3d)
     */
    public double angle(Vector3d v) {
    	return angle(this, v);
    }
    
    /**
     * Return the angle between the supplied vectors.
     * @return the angle, in radians
     * @see #angleCos(Vector3d, Vector3d)
     */
    public static double angle(Vector3d v1, Vector3d v2) {
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
    public Vector3d zero() {
        this.x = 0.0;
        this.y = 0.0;
        this.z = 0.0;
        return this;
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
        return "(" + formatter.format(x) + " " + formatter.format(y) + " " + formatter.format(z) + ")";
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

    /**
     * Negate this vector.
     * 
     * @return this
     */
    public Vector3d negate() {
        x = -x;
        y = -y;
        z = -z;
        return this;
    }
    
    /**
     * Negate original and store the result in dest.
     */
    public static void negate(Vector3d original, Vector3d dest) {
    	dest.x = -original.x;
    	dest.y = -original.y;
    	dest.z = -original.z;
    }

    public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
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
		Vector3d other = (Vector3d) obj;
		if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
			return false;
		if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y))
			return false;
		if (Double.doubleToLongBits(z) != Double.doubleToLongBits(other.z))
			return false;
		return true;
	}

    /**
     * Reflect this vector about the given normal vector.
     * 
     * @param normal
     *             the vector to reflect about
     * @return this
     */
    public Vector3d reflect(Vector3d normal) {
        double dot = this.dot(normal);
        x = x - 2.0 * dot * normal.x;
        y = y - 2.0 * dot * normal.y;
        z = z - 2.0 * dot * normal.z;
        return this;
    }

    /**
     * Reflect this vector about the given normal vector and store the result in <code>dest</code>.
     * 
     * @param normal
     *             the vector to reflect about
     * @param dest
     *             will hold the result
     * @return this
     */
    public Vector3d reflect(Vector3d normal, Vector3d dest) {
        double dot = this.dot(normal);
        dest.x = x - 2.0 * dot * normal.x;
        dest.y = y - 2.0 * dot * normal.y;
        dest.z = z - 2.0 * dot * normal.z;
        return this;
    }

    /**
     * Compute the half vector between this and the other vector.
     * 
     * @param other
     *             the other vector
     */
    public Vector3d half(Vector3d other) {
        return this.add(other).normalize();
    }

    /**
     * Compute the half vector between this and the other vector and store the result in <code>dest</code>.
     * 
     * @param other
     *             the other vector
     * @param dest
     *             will hold the result
     */
    public Vector3d half(Vector3d other, Vector3d dest) {
        return dest.set(this).add(other).normalize();
    }


}
