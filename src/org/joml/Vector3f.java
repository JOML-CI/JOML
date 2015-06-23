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
 * Contains the definition of a Vector comprising 3 floats and associated
 * transformations.
 *
 * @author Richard Greenlees
 * @author Kai Burjack
 */
public class Vector3f implements Serializable, Externalizable {

    private static final long serialVersionUID = 1L;    

    public float x;
    public float y;
    public float z;

    /**
     * Create a new {@link Vector3f} of <tt>(0, 0, 0)</tt>.
     */
    public Vector3f() {
    }

    /**
     * Create a new {@link Vector4f} with the given component values.
     */
    public Vector3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Create a new {@link Vector3f} with the same values as <code>v</code>.
     * 
     * @param v
     *            the {@link Vector3f} to copy the values from
     */
    public Vector3f(Vector3f v) {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
    }

    /**
     * Set the x, y and z attributes to match the supplied vector.
     * 
     * @param v
     * @return this
     */
    public Vector3f set(Vector3f v) {
        x = v.x;
        y = v.y;
        z = v.z;
        return this;
    }

    /**
     * Set the x, y and z attributes to the supplied float values.
     * 
     * @param x
     * @param y
     * @param z
     * @return this
     */
    public Vector3f set(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }

    /**
     * Subtract the supplied vector from this one and store the result in <code>this</code>.
     * 
     * @param v
     * @return this
     */
    public Vector3f sub(Vector3f v) {
        x -= v.x;
        y -= v.y;
        z -= v.z;
        return this;
    }

    /**
     * Decrement the components of this vector by the given values.
     * 
     * @return this
     */
    public Vector3f sub(float x, float y, float z) {
        this.x -= x;
        this.y -= y;
        this.z -= z;
        return this;
    }

    /**
     * Subtract v2 from v1 and store the result in <code>dest</code>.
     * 
     * @param v1
     *          the left operand
     * @param v2
     *          the right operand
     * @param dest
     *          will hold the result
     */
    public static void sub(Vector3f v1, Vector3f v2, Vector3f dest) {
        dest.x = v1.x - v2.x;
        dest.y = v1.y - v2.y;
        dest.z = v1.z - v2.z;
    }

    /**
     * Add the supplied vector to this one and store the result in <code>this</code>.
     * 
     * @param v
     *          the vector to add
     * @return this
     */
    public Vector3f add(Vector3f v) {
        x += v.x;
        y += v.y;
        z += v.z;
        return this;
    }

    /**
     * Increment the components of this vector by the given values.
     * 
     * @return this
     */
    public Vector3f add(float x, float y, float z) {
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }

    /**
     * Add v2 to v1 and store the result in <code>dest</code>.
     * 
     * @param v1
     *          the first vector
     * @param v2
     *          the second vector
     * @param dest
     *          will hold the result
     */
    public static void add(Vector3f v1, Vector3f v2, Vector3f dest) {
        dest.x = v1.x + v2.x;
        dest.y = v1.y + v2.y;
        dest.z = v1.z + v2.z;
    }

    /**
     * Multiply this Vector3f by the given vector component-wise and store the result in <code>this</code>.
     * 
     * @param v
     *          the other vector
     * @return this
     */
    public Vector3f mul(Vector3f v) {
        x *= v.x;
        y *= v.y;
        z *= v.z;
        return this;
    }

    /**
     * Multiply v1 by v2 component-wise and store the result in dest.
     * 
     * @param v1
     *          the first vector
     * @param v2
     *          the second vector
     * @param dest
     *          will hold the result
     */
    public static void mul(Vector3f v1, Vector3f v2, Vector3f dest) {
        dest.x = v1.x * v2.x;
        dest.y = v1.y * v2.y;
        dest.z = v1.z * v2.z;
    }

    /**
     * Multiply this Vector3f by the given matrix <code>mat</code> and store the result in <code>this</code>.
     * 
     * @param mat
     *          the matrix to multiply this vector by
     * @return this
     */
    public Vector3f mul(Matrix4f mat) {
        mul(this, mat, this);
        return this;
    }

    /**
     * Multiply this Vector3f by the given matrix <code>mat</code> and store the result in <code>dest</code>.
     * 
     * @param mat
     *          the matrix to multiply this vector by
     * @param dest
     *          will hold the result
     * @return this
     */
    public Vector3f mul(Matrix4f mat, Vector3f dest) {
        mul(this, mat, dest);
        return this;
    }

    /**
     * Multiply Vector3f v by the given matrix <code>mat</code> and store the result in dest.
     * 
     * @param v
     *          the vector to multiply
     * @param mat
     *          the matrix
     * @param dest will hold the result
     */
    public static void mul(Vector3f v, Matrix4f mat, Vector3f dest) {
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
     * Multiply this Vector3f by the given matrix and store the result in <code>this</code>.
     * 
     * @param mat
     *          the matrix
     * @return this
     */
    public Vector3f mul(Matrix3f mat) {
        mul(this, mat, this);
        return this;
    }

    /**
     * Multiply this Vector3f by the given matrix and store the result in <code>dest</code>.
     * 
     * @param mat
     *          the matrix
     * @param dest
     *          will hold the result
     * @return this
     */
    public Vector3f mul(Matrix3f mat, Vector3f dest) {
        mul(this, mat, dest);
        return this;
    }

    /**
     * Multiply Vector3f v by the given matrix and store the result in <code>dest</code>.
     * 
     * @param v
     *          the vector to multiply
     * @param mat
     *          the matrix
     * @param dest
     *          will hold the result
     */
    public static void mul(Vector3f v, Matrix3f mat, Vector3f dest) {
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
     * Multiply this Vector3f by the given scalar value and store the result in <code>this</code>.
     * 
     * @param scalar
     *          the scalar factor
     * @return this
     */
    public Vector3f mul(float scalar) {
        x *= scalar;
        y *= scalar;
        z *= scalar;
        return this;
    }

    /**
     * Multiply the components of this Vector3f by the given scalar values and store the result in <code>this</code>.
     * 
     * @return this
     */
    public Vector3f mul(float x, float y, float z) {
        this.x *= x;
        this.y *= y;
        this.z *= z;
        return this;
    }

    /**
     * Multiply the given Vector3f <code>v</code> by the scalar value and store the result in <code>dest</code>.
     * 
     * @param v
     *          the vector to scale
     * @param scalar
     *          the scalar factor
     * @param dest
     *          will hold the result
     */
    public static void mul(Vector3f v, float scalar, Vector3f dest) {
        dest.x = v.x * scalar;
        dest.y = v.y * scalar;
        dest.z = v.z * scalar;
    }

    /**
     * Multiply this vector by the given quaternion <code>quat</code> and store the result in <code>this</code>.
     * 
     * @see Quaternion#transform(Vector3f)
     * 
     * @param quat
     *          the quaternion to multiply this vector by
     * @return this
     */
    public Vector3f mul(Quaternion quat) {
        mul(this, quat, this);
        return this;
    }

    /**
     * Multiply this vector by the given quaternion <code>quat</code> and store the result in <code>dest</code>.
     * 
     * @see Quaternion#transform(Vector3f)
     * 
     * @param quat
     *          the quaternion to multiply this vector by
     * @param dest
     *          will hold the result
     * @return this
     */
    public Vector3f mul(Quaternion quat, Vector3f dest) {
        mul(this, quat, dest);
        return this;
    }

    /**
     * Multiply the vector <code>v</code> by the given quaternion <code>mat</code> and store the result in <code>dest</code>.
     * 
     * @see Quaternion#transform(Vector3f)
     * 
     * @param v
     *          the vector to multiply
     * @param quat
     *          the quaternion to multiply the vector by
     * @param dest
     *          will hold the result
     */
    public static void mul(Vector3f v, Quaternion quat, Vector3f dest) {
        quat.transform(v, dest);
    }

    /**
     * Return the length squared of this vector.
     */
    public float lengthSquared() {
        return x * x + y * y + z * z;
    }

    /**
     * Return the length of this vector.
     */
    public float length() {
        return (float) Math.sqrt(lengthSquared());
    }

    /**
     * Normalize this vector.
     * 
     * @return this
     */
    public Vector3f normalize() {
        float d = length();
        x /= d;
        y /= d;
        z /= d;
        return this;
    }

    /**
     * Normalize this vector and store the result in <code>dest</code>.
     * 
     * @param dest
     *          will hold the result
     * @return this
     */
    public Vector3f normalize(Vector3f dest) {
        float d = length();
        dest.x = x / d;
        dest.y = y / d;
        dest.z = z / d;
        return this;
    }

    /**
     * Normalize the <code>original</code> vector and store the result in <code>dest</code>.
     * 
     * @param original
     *          the vector to normalize
     * @param dest
     *          will hold the result
     */
    public static void normalize(Vector3f original, Vector3f dest) {
        float d = original.length();
        dest.set(original.x / d,
                 original.y / d,
                 original.z / d);
    }

    /**
     * Set this vector to be the cross product of itself and <code>v</code>.
     * 
     * @return this
     */
    public Vector3f cross(Vector3f v) {
        return set(y * v.z - z * v.y,
                   z * v.x - x * v.z,
                   x * v.y - y * v.x);
    }
    
    /**
     * Set this vector to be the cross product of <code>v1</code> and <code>v2</code>.
     * 
     * @return this
     */
    public Vector3f cross(Vector3f v1, Vector3f v2) {
        return set(v1.y * v2.z - v1.z * v2.y,
                   v1.z * v2.x - v1.x * v2.z,
                   v1.x * v2.y - v1.y * v2.x);
    }

    /**
     * Calculate the cross product of <code>v1</code> and <code>v2</code> and store the result in <code>dest</code>.
     */
    public static void cross(Vector3f v1, Vector3f v2, Vector3f dest) {
        dest.set(v1.y * v2.z - v1.z * v2.y,
                 v1.z * v2.x - v1.x * v2.z,
                 v1.x * v2.y - v1.y * v2.x);
    }

    /**
     * Return the distance between <code>start</code> and <code>end</code>.
     */
    public static float distance(Vector3f start, Vector3f end) {
        return (float) Math.sqrt((end.x - start.x) * (end.x - start.x)
                + (end.y - start.y) * (end.y - start.y)
                + (end.z - start.z) * (end.z - start.z));
    }

    /**
     * Return the distance between this Vector and <code>v</code>.
     */
    public float distance(Vector3f v) {
        return (float) Math.sqrt((v.x - this.x) * (v.x - this.x)
                + (v.y - this.y) * (v.y - this.y)
                + (v.z - this.z) * (v.z - this.z));
    }

    /**
     * Return the dot product of this vector and the supplied vector.
     */
    public float dot(Vector3f v) {
        return x * v.x + y * v.y + z * v.z;
    }

    /**
     * Return the dot product of the supplied v1 and v2 vectors.
     */
    public static float dot(Vector3f v1, Vector3f v2) {
        return v1.x * v2.x + v1.y * v2.y + v1.z * v2.z;
    }
    
    /**
     * Return the cosinus of the angle between this vector and the supplied vector. Use this instead of Math.cos(this.angle(v)).
     * @return the cosinus of the angle
     * @see #angle(Vector3f)
     */
    public float angleCos(Vector3f v) {
    	return angleCos(this, v);
    }
    
    /**
     * Return the cosinus of the angle between the supplied vectors. Use this instead of Math.cos(angle(v1, v2)).
     * @return the cosinus of the angle
     * @see #angle(Vector3f, Vector3f)
     */
    public static float angleCos(Vector3f v1, Vector3f v2) {
    	float length1 = (float) Math.sqrt((v1.x * v1.x) + (v1.y * v1.y) + (v1.z * v1.z));
    	float length2 = (float) Math.sqrt((v2.x * v2.x) + (v2.y * v2.y) + (v2.z * v2.z));
    	float dot = (v1.x * v2.x) + (v1.y * v2.y) + (v1.z * v2.z);
    	return dot / (length1 * length2);
    }
    
    /**
     * Return the angle between this vector and the supplied vector.
     * @return the angle, in radians
     * @see #angleCos(Vector3f)
     */
    public float angle(Vector3f v) {
    	return angle(this, v);
    }
    
    /**
     * Return the angle between the supplied vectors.
     * @return the angle, in radians
     * @see #angleCos(Vector3f, Vector3f)
     */
    public static float angle(Vector3f v1, Vector3f v2) {
    	float cos = angleCos(v1, v2);
    	// This is because sometimes cos goes above 1 or below -1 because of lost precision
    	cos = Math.min(cos, 1);
    	cos = Math.max(cos, -1);
    	return (float) Math.acos(cos);
    }

    /**
     * Set the components of this vector to be the component-wise minimum of this and the other vector.
     *
     * @param v
     *          the other vector
     * @return this
     */
    public Vector3f min(Vector3f v) {
        this.x = Math.min(x, v.x);
        this.y = Math.min(y, v.y);
        this.z = Math.min(z, v.z);
        return this;
    }

    /**
     * Set the components of this vector to be the component-wise maximum of this and the other vector.
     *
     * @param v
     *          the other vector
     * @return this
     */
    public Vector3f max(Vector3f v) {
        this.x = Math.max(x, v.x);
        this.y = Math.max(y, v.y);
        this.z = Math.max(z, v.z);
        return this;
    }

    /**
     * Set all components to zero.
     * 
     * @return this
     */
    public Vector3f zero() {
        this.x = 0.0f;
        this.y = 0.0f;
        this.z = 0.0f;
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
        out.writeFloat(x);
        out.writeFloat(y);
        out.writeFloat(z);
    }

    public void readExternal(ObjectInput in) throws IOException,
            ClassNotFoundException {
        x = in.readFloat();
        y = in.readFloat();
        z = in.readFloat();
    }

    /**
     * Negate this vector.
     * 
     * @return this
     */
    public Vector3f negate() {
        x = -x;
        y = -y;
        z = -z;
        return this;
    }
    
    /**
     * Negate original and store the result in dest.
     */
    public static void negate(Vector3f original, Vector3f dest) {
    	dest.x = -original.x;
    	dest.y = -original.y;
    	dest.z = -original.z;
    }

    public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		Vector3f other = (Vector3f) obj;
		if (Float.floatToIntBits(x) != Float.floatToIntBits(other.x))
			return false;
		if (Float.floatToIntBits(y) != Float.floatToIntBits(other.y))
			return false;
		if (Float.floatToIntBits(z) != Float.floatToIntBits(other.z))
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
	public Vector3f reflect(Vector3f normal) {
	    float dot = this.dot(normal);
	    x = x - 2.0f * dot * normal.x;
	    y = y - 2.0f * dot * normal.y;
	    z = z - 2.0f * dot * normal.z;
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
    public Vector3f reflect(Vector3f normal, Vector3f dest) {
        float dot = this.dot(normal);
        dest.x = x - 2.0f * dot * normal.x;
        dest.y = y - 2.0f * dot * normal.y;
        dest.z = z - 2.0f * dot * normal.z;
        return this;
    }

    /**
     * Compute the half vector between this and the other vector.
     * 
     * @param other
     *             the other vector
     * @return this
     */
    public Vector3f half(Vector3f other) {
        return this.add(other).normalize();
    }

    /**
     * Compute the half vector between this and the other vector and store the result in <code>dest</code>.
     * 
     * @param other
     *             the other vector
     * @param dest
     *             will hold the result
     * @return this
     */
    public Vector3f half(Vector3f other, Vector3f dest) {
        dest.set(this).add(other).normalize();
        return this;
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
    public Vector3f smoothStep(Vector3f v, float t, Vector3f dest) {
        dest.x = (float) Interpolate.smoothStep(x, v.x, t);
        dest.y = (float) Interpolate.smoothStep(y, v.y, t);
        dest.z = (float) Interpolate.smoothStep(x, v.z, t);
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
    public Vector3f hermite(Vector3f t0, Vector3f v1, Vector3f t1, float t, Vector3f dest) {
        dest.x = (float) Interpolate.hermite(x, t0.x, v1.x, t1.x, t);
        dest.y = (float) Interpolate.hermite(y, t0.y, v1.y, t1.y, t);
        dest.z = (float) Interpolate.hermite(z, t0.z, v1.z, t1.z, t);
        return this;
    }

    /**
     * Return the specified {@link Vector3f}.
     * 
     * @param v
     *          the {@link Vector3f} to return
     * @return that vector
     */
    public Vector3f with(Vector3f v) {
        return v;
    }

    /**
     * Return the specified {@link Vector4f}.
     * 
     * @param v
     *          the {@link Vector4f} to return
     * @return that vector
     */
    public Vector4f with(Vector4f v) {
        return v;
    }

    /**
     * Return the specified {@link Quaternion}.
     * 
     * @param q
     *          the {@link Quaternion} to return
     * @return that quaternion
     */
    public Quaternion with(Quaternion q) {
        return q;
    }

    /**
     * Return the specified {@link AngleAxis4f}.
     * 
     * @param a
     *          the {@link AngleAxis4f} to return
     * @return that quaternion
     */
    public AngleAxis4f with(AngleAxis4f a) {
        return a;
    }

    /**
     * Return the specified {@link Matrix3f}.
     * 
     * @param m
     *          the {@link Matrix3f} to return
     * @return that matrix
     */
    public Matrix3f with(Matrix3f m) {
        return m;
    }

    /**
     * Return the specified {@link Matrix4f}.
     * 
     * @param m
     *          the {@link Matrix4f} to return
     * @return that matrix
     */
    public Matrix4f with(Matrix4f m) {
        return m;
    }

}
