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
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Contains the definition of a Vector comprising 4 floats and associated
 * transformations.
 * 
 * @author Richard Greenlees
 * @author Kai Burjack
 */
public class Vector4f implements Externalizable {

    private static final long serialVersionUID = 1L;   

    /**
     * The x-coordinate of the vector.
     */
    public float x;
    /**
     * The y-coordinate of the vector.
     */
    public float y;
    /**
     * The z-coordinate of the vector.
     */
    public float z;
    /**
     * The w-coordinate of the vector.
     */
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
     * 
     * @param x
     *          the value of x
     * @param y
     *          the value of y
     * @param z
     *          the value of z
     * @param w
     *          the value of w
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
     * @param v
     *          the vector to subtract
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
     * Subtract <tt>(x, y, z, w)</tt> from this.
     * 
     * @param x
     *          the x-coordinate to subtract
     * @param y
     *          the y-coordinate to subtract
     * @param z
     *          the z-coordinate to subtract
     * @param w
     *          the w-coordinate to subtract
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
     * Subtract the supplied vector from this one and store the result in <code>dest</code>.
     * 
     * @param v
     *          the vector to subtract from <code>this</code>
     * @param dest
     *          will hold the result
     * @return this
     */
    public Vector4f sub(Vector4f v, Vector4f dest) {
        dest.x = x - v.x;
        dest.y = y - v.y;
        dest.z = z - v.z;
        dest.w = w - v.w;
        return this;
    }

    /**
     * Subtract <tt>(x, y, z, w)</tt> from this and store the result in <code>dest</code>.
     * 
     * @param x
     *          the x-coordinate to subtract
     * @param y
     *          the y-coordinate to subtract
     * @param z
     *          the z-coordinate to subtract
     * @param w
     *          the w-coordinate to subtract
     * @param dest
     *          will hold the result
     * @return this
     */
    public Vector4f sub(float x, float y, float z, float w, Vector4f dest) {
        dest.x = this.x - x;
        dest.y = this.y - y;
        dest.z = this.z - z;
        dest.w = this.w - w;
        return this;
    }

    /**
     * Add the supplied vector to this one.
     * 
     * @param v
     *          the vector to add
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
     * Add the supplied vector to this one and store the result in <code>dest</code>.
     * 
     * @param v
     *          the vector to add
     * @param dest
     *          will hold the result
     * @return this
     */
    public Vector4f add(Vector4f v, Vector4f dest) {
        dest.x = x + v.x;
        dest.y = y + v.y;
        dest.z = z + v.z;
        dest.w = w + v.w;
        return this;
    }

    /**
     * Increment the components of this vector by the given values.
     * 
     * @param x
     *          the x-coordinate to add
     * @param y
     *          the y-coordinate to add
     * @param z
     *          the z-coordinate to add
     * @param w
     *          the w-coordinate to add
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
     * Increment the components of this vector by the given values and store the result in <code>dest</code>.
     * 
     * @param x
     *          the x-coordinate to add
     * @param y
     *          the y-coordinate to add
     * @param z
     *          the z-coordinate to add
     * @param w
     *          the w-coordinate to add
     * @param dest
     *          will hold the result
     * @return this
     */
    public Vector4f add(float x, float y, float z, float w, Vector4f dest) {
        dest.x = this.x + x;
        dest.y = this.y + y;
        dest.z = this.z + z;
        dest.w = this.w + w;
        return this;
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
     * Add the component-wise multiplication of <code>a * b</code> to this vector.
     * 
     * @param a
     *          the first multiplicand
     * @param b
     *          the second multiplicand
     * @return this
     */
    public Vector4f fma(float a, Vector4f b) {
        x += a * b.x;
        y += a * b.y;
        z += a * b.z;
        w += a * b.w;
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
    public Vector4f fma(float a, Vector4f b, Vector4f dest) {
        dest.x = x + a * b.x;
        dest.y = y + a * b.y;
        dest.z = z + a * b.z;
        dest.w = w + a * b.w;
        return this;
    }

    /**
     * Multiply this Vector4f component-wise by another Vector4f.
     * 
     * @param v
     *          the other vector
     * @return this
     */
    public Vector4f mul(Vector4f v) {
        x *= v.x;
        y *= v.y;
        z *= v.z;
        w *= v.w;
        return this;
    }

    /**
     * Multiply this Vector4f component-wise by another Vector4f and store the result in <code>dest</code>.
     * 
     * @param v
     *          the other vector
     * @param dest
     *          will hold the result
     * @return this
     */
    public Vector4f mul(Vector4f v, Vector4f dest) {
        dest.x = x * v.x;
        dest.y = y * v.y;
        dest.z = z * v.z;
        dest.w = w * v.w;
        return this;
    }

    /**
     * Divide this Vector4f component-wise by another Vector4f.
     * 
     * @param v
     *          the vector to divide by
     * @return this
     */
    public Vector4f div(Vector4f v) {
        x /= v.x;
        y /= v.y;
        z /= v.z;
        w /= v.w;
        return this;
    }

    /**
     * Divide this Vector4f component-wise by another Vector4f and store the result in <code>dest</code>.
     * 
     * @param v
     *          the vector to divide by
     * @param dest
     *          will hold the result
     * @return this
     */
    public Vector4f div(Vector4f v, Vector4f dest) {
        dest.x = x / v.x;
        dest.y = y / v.y;
        dest.z = z / v.z;
        dest.w = w / v.w;
        return this;
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
        return mul(mat, this);
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
     * Multiply all components of this {@link Vector4f} by the given scalar
     * value.
     * 
     * @param scalar
     *          the scalar to multiply by
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
     * Multiply all components of this {@link Vector4f} by the given scalar
     * value and store the result in <code>dest</code>.
     * 
     * @param scalar
     *          the scalar to multiply by
     * @param dest
     *          will hold the result
     * @return this
     */
    public Vector4f mul(float scalar, Vector4f dest) {
        dest.x = x * scalar;
        dest.y = y * scalar;
        dest.z = z * scalar;
        dest.w = w * scalar;
        return this;
    }

    /**
     * Multiply the components of this Vector4f by the given scalar values and store the result in <code>this</code>.
     * 
     * @param x
     *          the x-coordinate to multiply by
     * @param y
     *          the y-coordinate to multiply by
     * @param z
     *          the z-coordinate to multiply by
     * @param w
     *          the w-coordinate to multiply by
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
     * Multiply the components of this Vector4f by the given scalar values and store the result in <code>dest</code>.
     * 
     * @param x
     *          the x-coordinate to multiply by
     * @param y
     *          the y-coordinate to multiply by
     * @param z
     *          the z-coordinate to multiply by
     * @param w
     *          the w-coordinate to multiply by
     * @param dest
     *          will hold the result
     * @return this
     */
    public Vector4f mul(float x, float y, float z, float w, Vector4f dest) {
        dest.x = this.x * x;
        dest.y = this.y * y;
        dest.z = this.z * z;
        dest.w = this.w * w;
        return this;
    }

    /**
     * Divide all components of this {@link Vector4f} by the given scalar
     * value.
     * 
     * @param scalar
     *          the scalar to divide by
     * @return this
     */
    public Vector4f div(float scalar) {
        x /= scalar;
        y /= scalar;
        z /= scalar;
        w /= scalar;
        return this;
    }

    /**
     * Divide all components of this {@link Vector4f} by the given scalar
     * value and store the result in <code>dest</code>.
     * 
     * @param scalar
     *          the scalar to divide by
     * @param dest
     *          will hold the result
     * @return this
     */
    public Vector4f div(float scalar, Vector4f dest) {
        dest.x = x / scalar;
        dest.y = y / scalar;
        dest.z = z / scalar;
        dest.w = w / scalar;
        return this;
    }

    /**
     * Divide the components of this Vector4f by the given scalar values and store the result in <code>this</code>.
     * 
     * @param x
     *          the x-coordinate to divide by
     * @param y
     *          the y-coordinate to divide by
     * @param z
     *          the z-coordinate to divide by
     * @param w
     *          the w-coordinate to divide by
     * @return this
     */
    public Vector4f div(float x, float y, float z, float w) {
        this.x /= x;
        this.y /= y;
        this.z /= z;
        this.w /= w;
        return this;
    }

    /**
     * Divide the components of this Vector4f by the given scalar values and store the result in <code>dest</code>.
     * 
     * @param x
     *          the x-coordinate to divide by
     * @param y
     *          the y-coordinate to divide by
     * @param z
     *          the z-coordinate to divide by
     * @param w
     *          the w-coordinate to divide by
     * @param dest
     *          will hold the result
     * @return this
     */
    public Vector4f div(float x, float y, float z, float w, Vector4f dest) {
        dest.x = this.x / x;
        dest.y = this.y / y;
        dest.z = this.z / z;
        dest.w = this.w / w;
        return this;
    }

    /**
     * Rotate this vector by the given quaternion <code>quat</code> and store the result in <code>this</code>.
     * 
     * @see Quaternionf#transform(Vector4f)
     * 
     * @param quat
     *          the quaternion to rotate this vector
     * @return this
     */
    public Vector4f rotate(Quaternionf quat) {
        return rotate(quat, this);
    }

    /**
     * Rotate this vector by the given quaternion <code>quat</code> and store the result in <code>dest</code>.
     * 
     * @see Quaternionf#transform(Vector4f)
     * 
     * @param quat
     *          the quaternion to rotate this vector
     * @param dest
     *          will hold the result
     * @return this
     */
    public Vector4f rotate(Quaternionf quat, Vector4f dest) {
        quat.transform(this, dest);
        return this;
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
     * Normalizes this vector and store the result in <code>dest</code>.
     * 
     * @param dest
     *          will hold the result
     * @return this
     */
    public Vector4f normalize(Vector4f dest) {
        float d = length();
        dest.x = x / d;
        dest.y = y / d;
        dest.z = z / d;
        dest.w = w / d;
        return this;
    }

    /**
     * Normalize this vector by computing only the norm of <tt>(x, y, z)</tt>.
     * 
     * @return this
     */
    public Vector4f normalize3() {
        float d = (float) Math.sqrt(x * x + y * y + z * z);
        x /= d;
        y /= d;
        z /= d;
        w /= d;
        return this;
    }

    /**
     * Return the distance between <code>this</code> vector and <code>v</code>.
     * 
     * @param v
     *          the other vector
     * @return the euclidean distance
     */
    public float distance(Vector4f v) {
        return (float) Math.sqrt(
                (v.x - x) * (v.x - x)
              + (v.y - y) * (v.y - y)
              + (v.z - z) * (v.z - z)
              + (v.w - w) * (v.w - w));
    }

    /**
     * Return the distance between <code>this</code> vector and <tt>(x, y, z, w)</tt>.
     * 
     * @param x
     *            the x-coordinate of the other vector
     * @param y
     *            the y-coordinate of the other vector
     * @param z
     *            the z-coordinate of the other vector
     * @param w
     *            the w-coordinate of the other vector
     * @return the euclidean distance
     */
    public float distance(float x, float y, float z, float w) {
        return (float) Math.sqrt(
                (x - this.x) * (x - this.x)
              + (y - this.y) * (y - this.y)
              + (z - this.z) * (z - this.z)
              + (w - this.w) * (w - this.w));
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
     * Compute the dot product (inner product) of this vector and <tt>(x, y, z, w)</tt>.
     * 
     * @param x
     *            the x-coordinate of the other vector
     * @param y
     *            the y-coordinate of the other vector
     * @param z
     *            the z-coordinate of the other vector
     * @param w
     *            the w-coordinate of the other vector
     * @return the dot product
     */
    public float dot(float x, float y, float z, float w) {
        return this.x * x + this.y * y + this.z * z + this.w * w;
    }

    /**
     * Return the cosine of the angle between this vector and the supplied vector. Use this instead of <code>Math.cos(angle(v))</code>.
     * 
     * @see #angle(Vector4f)
     * 
     * @param v
     *          the other vector
     * @return the cosine of the angle
     */
    public float angleCos(Vector4f v) {
        double length1 = Math.sqrt(x * x + y * y + z * z + w * w);
        double length2 = Math.sqrt(v.x * v.x + v.y * v.y + v.z * v.z + v.w * v.w);
        double dot = x * v.x + y * v.y + z * v.z + w * v.w;
        return (float) (dot / (length1 * length2));
    }

    /**
     * Return the angle between this vector and the supplied vector.
     * 
     * @see #angleCos(Vector4f)
     * 
     * @param v
     *          the other vector
     * @return the angle, in radians
     */
    public float angle(Vector4f v) {
        float cos = angleCos(v);
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
     * Negate this vector and store the result in <code>dest</code>.
     * 
     * @param dest
     *          will hold the result
     * @return this
     */
    public Vector4f negate(Vector4f dest) {
        dest.x = -x;
        dest.y = -y;
        dest.z = -z;
        dest.w = -w;
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
        DecimalFormat formatter = new DecimalFormat(" 0.000E0;-"); //$NON-NLS-1$
        return toString(formatter).replaceAll("E(\\d+)", "E+$1"); //$NON-NLS-1$ //$NON-NLS-2$
    }

    /**
     * Return a string representation of this vector by formatting the vector components with the given {@link NumberFormat}.
     * 
     * @param formatter
     *          the {@link NumberFormat} used to format the vector components with
     * @return the string representation
     */
    public String toString(NumberFormat formatter) {
        return "(" + formatter.format(x) + " " + formatter.format(y) + " " + formatter.format(z) + " " + formatter.format(w) + ")"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
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

}
