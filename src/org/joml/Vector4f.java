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
 * Contains the definition of a Vector comprising 4 floats and associated
 * transformations.
 * 
 * @author Richard Greenlees
 * @author Kai Burjack
 */
public class Vector4f implements Serializable, Externalizable {

    private static final long serialVersionUID = 1L;   

    public float x;
    public float y;
    public float z;
    public float w = 1.0f;

    /**
     * Create a new {@link Vector4f} of <code>(0, 0, 0, 1)</code>.
     */
    public Vector4f() {
    }

    /**
     * Create a new {@link Vector4f} with the same values as <code>v</code>.
     * 
     * @param v
     *            the {@link Vector4f} to copy the values from
     */
    public Vector4f(Vector4f v) {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
        this.w = v.w;
    }

    /**
     * Create a new {@link Vector4f} with the first three components from the
     * given <code>v</code> and the given <code>w</code>.
     * 
     * @param v
     *            the {@link Vector3f}
     * @param w
     *            the w value
     */
    public Vector4f(Vector3f v, float w) {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
        this.w = w;
    }

    /**
     * Create a new {@link Vector4f} with the given component values.
     */
    public Vector4f(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    /**
     * Set this {@link Vector4f} to the values of the given <code>v</code>.
     * 
     * @param v
     *            the vector whose values will be copied into this
     * @return this
     */
    public Vector4f set(Vector4f v) {
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
     *            the {@link Vector3f} to copy
     * @param w
     *            the w component
     * @return this
     */
    public Vector4f set(Vector3f v, float w) {
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
    public Vector4f set(float x, float y, float z, float w) {
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
    public Vector4f sub(Vector4f v) {
        x -= v.x;
        y -= v.y;
        z -= v.z;
        w -= v.w;
        return this;
    }

    /**
     * Decrement the components of this vector by the given values.
     * 
     * @return this
     */
    public Vector4f sub(float x, float y, float z, float w) {
        this.x -= x;
        this.y -= y;
        this.z -= z;
        this.w -= w;
        return this;
    }

    /**
     * Subtract v2 from v1 and store the result in dest.
     */
    public static void sub(Vector4f v1, Vector4f v2, Vector4f dest) {
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
    public Vector4f add(Vector4f v) {
        x += v.x;
        y += v.y;
        z += v.z;
        w += v.w;
        return this;
    }

    /**
     * Increment the components of this vector by the given values.
     * 
     * @return this
     */
    public Vector4f add(float x, float y, float z, float w) {
        this.x += x;
        this.y += y;
        this.z += z;
        this.w += w;
        return this;
    }

    /**
     * Add v2 to v1 and store the result in <code>dest</code>.
     */
    public static void add(Vector4f v1, Vector4f v2, Vector4f dest) {
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
    public Vector4f fma(Vector4f a, Vector4f b) {
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
    public Vector4f fma(Vector4f a, Vector4f b, Vector4f dest) {
        dest.x = x + a.x * b.x;
        dest.y = y + a.y * b.y;
        dest.z = z + a.z * b.z;
        dest.w = w + a.w * b.w;
        return this;
    }

    /**
     * Multiply this Vector4f by another Vector4f.
     * 
     * @return this
     */
    public Vector4f mul(Vector4f v) {
        x *= v.x;
        y *= v.y;
        z *= v.z;
        z *= v.w;
        return this;
    }

    /**
     * Multiply v1 by v2 component-wise and store the result into dest.
     */
    public static void mul(Vector4f v1, Vector4f v2, Vector4f dest) {
        dest.x = v1.x * v2.x;
        dest.y = v1.y * v2.y;
        dest.z = v1.z * v2.z;
        dest.w = v1.w * v2.w;
    }

    /**
     * Multiply this Vector4f by the given matrix mat and store the result in
     * <code>this</code>.
     * 
     * @param mat
     *            the matrix to multiply the vector with
     * @return this
     */
    public Vector4f mul(Matrix4f mat) {
        mul(this, mat, this);
        return this;
    }

    /**
     * Multiply this Vector4f by the given matrix mat and store the result in
     * <code>dest</code>.
     * 
     * @param mat
     *            the matrix to multiply the vector with
     * @param dest
     *            the destination vector to hold the result
     * @return this
     */
    public Vector4f mul(Matrix4f mat, Vector4f dest) {
        mul(this, mat, dest);
        return this;
    }

    /**
     * Multiply <code>v</code> by the given matrix <code>mat</code> and store
     * the result in <code>dest</code>.
     * 
     * @param v
     *            the vector to multiply the matrix with
     * @param mat
     *            the matrix
     * @param dest
     *            will hold the result
     */
    public static void mul(Vector4f v, Matrix4f mat, Vector4f dest) {
        if (v != dest) {
            dest.x = mat.m00 * v.x + mat.m10 * v.y + mat.m20 * v.z + mat.m30 * v.w;
            dest.y = mat.m01 * v.x + mat.m11 * v.y + mat.m21 * v.z + mat.m31 * v.w;
            dest.z = mat.m02 * v.x + mat.m12 * v.y + mat.m22 * v.z + mat.m32 * v.w;
            dest.w = mat.m03 * v.x + mat.m13 * v.y + mat.m23 * v.z + mat.m33 * v.w;
        } else {
            dest.set(mat.m00 * v.x + mat.m10 * v.y + mat.m20 * v.z + mat.m30 * v.w,
                     mat.m01 * v.x + mat.m11 * v.y + mat.m21 * v.z + mat.m31 * v.w,
                     mat.m02 * v.x + mat.m12 * v.y + mat.m22 * v.z + mat.m32 * v.w,
                     mat.m03 * v.x + mat.m13 * v.y + mat.m23 * v.z + mat.m33 * v.w);
        }
    }

    /**
     * Multiply all components of this {@link Vector4f} by the given scalar
     * value.
     * 
     * @return this
     */
    public Vector4f mul(float scalar) {
        x *= scalar;
        y *= scalar;
        z *= scalar;
        w *= scalar;
        return this;
    }

    /**
     * Multiply the components of this Vector4f by the given scalar values and store the result in <code>this</code>.
     * 
     * @return this
     */
    public Vector4f mul(float x, float y, float z, float w) {
        this.x *= x;
        this.y *= y;
        this.z *= z;
        this.w *= w;
        return this;
    }

    /**
     * Multiply the given Vector4f v by the scalar value, and store the result
     * in dest.
     */
    public static void mul(Vector4f v, float scalar, Vector4f dest) {
        dest.x = v.x * scalar;
        dest.y = v.y * scalar;
        dest.z = v.z * scalar;
        dest.w = v.w * scalar;
    }

    /**
     * Multiply this vector by the given quaternion <code>quat</code> and store the result in <code>this</code>.
     * 
     * @see Quaternionf#transform(Vector4f)
     * 
     * @param quat
     *          the quaternion to multiply this vector by
     * @return this
     */
    public Vector4f mul(Quaternionf quat) {
        mul(this, quat, this);
        return this;
    }

    /**
     * Multiply this vector by the given quaternion <code>quat</code> and store the result in <code>dest</code>.
     * 
     * @see Quaternionf#transform(Vector4f)
     * 
     * @param quat
     *          the quaternion to multiply this vector by
     * @param dest
     *          will hold the result
     * @return this
     */
    public Vector4f mul(Quaternionf quat, Vector4f dest) {
        mul(this, quat, dest);
        return this;
    }

    /**
     * Multiply the vector <code>v</code> by the given quaternion <code>mat</code> and store the result in <code>dest</code>.
     * 
     * @see Quaternionf#transform(Vector4f)
     * 
     * @param v
     *          the vector to multiply
     * @param quat
     *          the quaternion to multiply the vector by
     * @param dest
     *          will hold the result
     */
    public static void mul(Vector4f v, Quaternionf quat, Vector4f dest) {
        quat.transform(v, dest);
    }

    /**
     * Return the length squared of this vector.
     * 
     * @return the length squared
     */
    public float lengthSquared() {
        return x * x + y * y + z * z + w * w;
    }

    /**
     * Return the length of this vector.
     * 
     * @return the length
     */
    public float length() {
        return (float) Math.sqrt(lengthSquared());
    }

    /**
     * Normalizes this vector.
     * 
     * @return this
     */
    public Vector4f normalize() {
        float d = length();
        x /= d;
        y /= d;
        z /= d;
        w /= d;
        return this;
    }

    /**
     * Normalize the <code>original</code> vector and store the result in
     * <code>dest</code>.
     * 
     * @param original
     *            the vector to normalize
     * @param dest
     *            will hold the result
     */
    public static void normalize(Vector4f original, Vector4f dest) {
        float d = original.length();
        dest.set(original.x / d, original.y / d, original.z / d, original.w / d);
    }

    /**
     * Return the distance between <code>start</code> and <code>end</code>.
     * 
     * @param start
     *            the first vector
     * @param end
     *            the second vector
     * @return the euclidean distance
     */
    public static float distance(Vector4f start, Vector4f end) {
        return (float) Math.sqrt(
                  (end.x - start.x) * (end.x - start.x)
                + (end.y - start.y) * (end.y - start.y)
                + (end.z - start.z) * (end.z - start.z)
                + (end.w - start.w) * (end.w - start.w));
    }

    /**
     * Return the distance between <code>this</code> vector and <code>v</code>.
     * 
     * @return the euclidean distance
     */
    public float distance(Vector4f v) {
        return distance(this, v);
    }

    /**
     * Compute the dot product (inner product) of this vector and <code>v</code>
     * .
     * 
     * @param v
     *            the other vector
     * @return the dot product
     */
    public float dot(Vector4f v) {
        return x * v.x + y * v.y + z * v.z + w * v.w;
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
     * @see #angle(Vector4f)
     */
    public float angleCos(Vector4f v) {
    	return angleCos(this, v);
    }
    
    /**
     * Return the cosinus of the angle between the supplied vectors. Use this instead of Math.cos(angle(v1, v2)).
     * @return the cosinus of the angle
     * @see #angle(Vector4f, Vector4f)
     */
    public static float angleCos(Vector4f v1, Vector4f v2) {
    	float length1 = (float) Math.sqrt((v1.x * v1.x) + (v1.y * v1.y) + (v1.z * v1.z) + (v1.w * v1.w));
    	float length2 = (float) Math.sqrt((v2.x * v2.x) + (v2.y * v2.y) + (v2.z * v2.z) + (v2.w * v2.w));
    	float dot = (v1.x * v2.x) + (v1.y * v2.y) + (v1.z * v2.z) + (v1.w * v2.w);
    	return dot / (length1 * length2);
    }
    
    /**
     * Return the angle between this vector and the supplied vector.
     * @return the angle, in radians
     * @see #angleCos(Vector4f)
     */
    public float angle(Vector4f v) {
    	return angle(this, v);
    }
    
    /**
     * Return the angle between the supplied vectors.
     * @return the angle, in radians
     * @see #angleCos(Vector4f, Vector4f)
     */
    public static float angle(Vector4f v1, Vector4f v2) {
    	float cos = angleCos(v1, v2);
    	// This is because sometimes cos goes above 1 or below -1 because of lost precision
    	cos = Math.min(cos, 1);
    	cos = Math.max(cos, -1);
    	return (float) Math.acos(cos);
    }

    /**
     * Set all components to zero.
     * 
     * @return this
     */
    public Vector4f zero() {
        this.x = 0.0f;
        this.y = 0.0f;
        this.z = 0.0f;
        this.w = 0.0f;
        return this;
    }
    
    /**
     * Negate this vector.
     * 
     * @return this
     */
    public Vector4f negate() {
        x = -x;
        y = -y;
        z = -z;
        w = -w;
        return this;
    }
    
    /**
     * Negate original and store the result in dest.
     */
    public static void negate(Vector4f original, Vector4f dest) {
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
        out.writeFloat(x);
        out.writeFloat(y);
        out.writeFloat(z);
        out.writeFloat(w);
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        x = in.readFloat();
        y = in.readFloat();
        z = in.readFloat();
        w = in.readFloat();
    }

    /**
     * Set the components of this vector to be the component-wise minimum of
     * this and the other vector.
     *
     * @param v
     *            the other vector
     * @return this
     */
    public Vector4f min(Vector4f v) {
        this.x = Math.min(x, v.x);
        this.y = Math.min(y, v.y);
        this.z = Math.min(z, v.z);
        this.w = Math.min(w, v.w);
        return this;
    }

    /**
     * Set the components of this vector to be the component-wise maximum of
     * this and the other vector.
     *
     * @param v
     *            the other vector
     * @return this
     */
    public Vector4f max(Vector4f v) {
        this.x = Math.max(x, v.x);
        this.y = Math.max(y, v.y);
        this.z = Math.max(z, v.z);
        this.w = Math.min(w, v.w);
        return this;
    }

    public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(w);
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
		Vector4f other = (Vector4f) obj;
		if (Float.floatToIntBits(w) != Float.floatToIntBits(other.w))
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
    public Vector4f smoothStep(Vector4d v, float t, Vector4f dest) {
        dest.x = (float) Interpolate.smoothStep(x, v.x, t);
        dest.y = (float) Interpolate.smoothStep(y, v.y, t);
        dest.z = (float) Interpolate.smoothStep(x, v.z, t);
        dest.w = (float) Interpolate.smoothStep(w, v.w, t);
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
    public Vector4f hermite(Vector4f t0, Vector4f v1, Vector4f t1, double t, Vector4f dest) {
        dest.x = (float) Interpolate.hermite(x, t0.x, v1.x, t1.x, t);
        dest.y = (float) Interpolate.hermite(y, t0.y, v1.y, t1.y, t);
        dest.z = (float) Interpolate.hermite(z, t0.z, v1.z, t1.z, t);
        dest.w = (float) Interpolate.hermite(z, t0.w, v1.w, t1.w, t);
        return this;
    }

    /**
     * Return the specified {@link Vector3f}.
     * <p>
     * When using method chaining in a fluent interface style, this method can be used to switch
     * the <i>context object</i>, on which further method invocations operate, to be the given vector.
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
     * <p>
     * When using method chaining in a fluent interface style, this method can be used to switch
     * the <i>context object</i>, on which further method invocations operate, to be the given vector.
     * 
     * @param v
     *          the {@link Vector4f} to return
     * @return that vector
     */
    public Vector4f with(Vector4f v) {
        return v;
    }

    /**
     * Return the specified {@link Quaternionf}.
     * <p>
     * When using method chaining in a fluent interface style, this method can be used to switch
     * the <i>context object</i>, on which further method invocations operate, to be the given quaternion.
     * 
     * @param q
     *          the {@link Quaternionf} to return
     * @return that quaternion
     */
    public Quaternionf with(Quaternionf q) {
        return q;
    }

    /**
     * Return the specified {@link AxisAngle4f}.
     * <p>
     * When using method chaining in a fluent interface style, this method can be used to switch
     * the <i>context object</i>, on which further method invocations operate, to be the given {@link AxisAngle4f}.
     * 
     * @param a
     *          the {@link AxisAngle4f} to return
     * @return that quaternion
     */
    public AxisAngle4f with(AxisAngle4f a) {
        return a;
    }

    /**
     * Return the specified {@link Matrix3f}.
     * <p>
     * When using method chaining in a fluent interface style, this method can be used to switch
     * the <i>context object</i>, on which further method invocations operate, to be the given matrix.
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
     * <p>
     * When using method chaining in a fluent interface style, this method can be used to switch
     * the <i>context object</i>, on which further method invocations operate, to be the given matrix.
     * 
     * @param m
     *          the {@link Matrix4f} to return
     * @return that matrix
     */
    public Matrix4f with(Matrix4f m) {
        return m;
    }

}
