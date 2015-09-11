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

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Abstract base class containing the readable definition of a Vector comprising 4 floats and associated
 * transformations.
 *
 * @author Richard Greenlees
 * @author Kai Burjack
 */
public abstract class Vector4fr {

    /**
     * @return The x component of the vector.
     */
    public abstract float x();
    /**
     * @return The y component of the vector.
     */
    public abstract float y();
    /**
     * @return The z component of the vector.
     */
    public abstract float z();
    /**
     * @return The w component of the vector.
     */
    public abstract float w();

    /**
     * Store this vector into the supplied {@link FloatBuffer} at the current
     * buffer {@link FloatBuffer#position() position}.
     * <p>
     * This method will not increment the position of the given FloatBuffer.
     * <p>
     * In order to specify the offset into the FloatBuffer at which
     * the vector is stored, use {@link #get(int, FloatBuffer)}, taking
     * the absolute position as parameter.
     *
     * @param buffer
     *          will receive the values of this vector in <tt>x, y, z, w</tt> order
     * @return the passed in buffer
     * @see #get(int, FloatBuffer)
     */
    public FloatBuffer get(FloatBuffer buffer) {
        return get(buffer.position(), buffer);
    }

    /**
     * Store this vector into the supplied {@link FloatBuffer} starting at the specified
     * absolute buffer position/index.
     * <p>
     * This method will not increment the position of the given FloatBuffer.
     *
     * @param index
     *          the absolute position into the FloatBuffer
     * @param buffer
     *          will receive the values of this vector in <tt>x, y, z, w</tt> order
     * @return the passed in buffer
     */
    public FloatBuffer get(int index, FloatBuffer buffer) {
        buffer.put(index,    x());
        buffer.put(index+1,  y());
        buffer.put(index+2,  z());
        buffer.put(index+3,  w());
        return buffer;
    }

    /**
     * Store this vector into the supplied {@link ByteBuffer} at the current
     * buffer {@link ByteBuffer#position() position}.
     * <p>
     * This method will not increment the position of the given ByteBuffer.
     * <p>
     * In order to specify the offset into the ByteBuffer at which
     * the vector is stored, use {@link #get(int, ByteBuffer)}, taking
     * the absolute position as parameter.
     *
     * @param buffer
     *          will receive the values of this vector in <tt>x, y, z, w</tt> order
     * @return the passed in buffer
     * @see #get(int, ByteBuffer)
     */
    public ByteBuffer get(ByteBuffer buffer) {
        return get(buffer.position(), buffer);
    }

    /**
     * Store this vector into the supplied {@link ByteBuffer} starting at the specified
     * absolute buffer position/index.
     * <p>
     * This method will not increment the position of the given ByteBuffer.
     *
     * @param index
     *          the absolute position into the ByteBuffer
     * @param buffer
     *          will receive the values of this vector in <tt>x, y, z, w</tt> order
     * @return the passed in buffer
     */
    public ByteBuffer get(int index, ByteBuffer buffer) {
        buffer.putFloat(index,    x());
        buffer.putFloat(index+4,  y());
        buffer.putFloat(index+8,  z());
        buffer.putFloat(index+12, w());
        return buffer;
    }

    /**
     * Subtract the supplied vector from this one and store the result in <code>dest</code>.
     *
     * @param v
     *          the vector to subtract from <code>this</code>
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Vector4f sub(Vector4fr v, Vector4f dest) {
        dest.x = x() - v.x();
        dest.y = y() - v.y();
        dest.z = z() - v.z();
        dest.w = w() - v.w();
        return dest;
    }

    /**
     * Subtract <tt>(x, y, z, w)</tt> from this and store the result in <code>dest</code>.
     *
     * @param x
     *          the x component to subtract
     * @param y
     *          the y component to subtract
     * @param z
     *          the z component to subtract
     * @param w
     *          the w component to subtract
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Vector4f sub(float x, float y, float z, float w, Vector4f dest) {
        dest.x = this.x() - x;
        dest.y = this.y() - y;
        dest.z = this.z() - z;
        dest.w = this.w() - w;
        return dest;
    }

    /**
     * Add the supplied vector to this one and store the result in <code>dest</code>.
     *
     * @param v
     *          the vector to add
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Vector4f add(Vector4fr v, Vector4f dest) {
        dest.x = x() + v.x();
        dest.y = y() + v.y();
        dest.z = z() + v.z();
        dest.w = w() + v.w();
        return dest;
    }

    /**
     * Increment the components of this vector by the given values and store the result in <code>dest</code>.
     *
     * @param x
     *          the x component to add
     * @param y
     *          the y component to add
     * @param z
     *          the z component to add
     * @param w
     *          the w component to add
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Vector4f add(float x, float y, float z, float w, Vector4f dest) {
        dest.x = this.x() + x;
        dest.y = this.y() + y;
        dest.z = this.z() + z;
        dest.w = this.w() + w;
        return dest;
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
     * @return dest
     */
    public Vector4f fma(Vector4fr a, Vector4fr b, Vector4f dest) {
        dest.x = x() + a.x() * b.x();
        dest.y = y() + a.y() * b.y();
        dest.z = z() + a.z() * b.z();
        dest.w = w() + a.w() * b.w();
        return dest;
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
     * @return dest
     */
    public Vector4f fma(float a, Vector4fr b, Vector4f dest) {
        dest.x = x() + a * b.x();
        dest.y = y() + a * b.y();
        dest.z = z() + a * b.z();
        dest.w = w() + a * b.w();
        return dest;
    }

    /**
     * Multiply this Vector4f component-wise by another Vector4fr and store the result in <code>dest</code>.
     *
     * @param v
     *          the other vector
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Vector4f mul(Vector4fr v, Vector4f dest) {
        dest.x = x() * v.x();
        dest.y = y() * v.y();
        dest.z = z() * v.z();
        dest.w = w() * v.w();
        return dest;
    }

    /**
     * Divide this Vector4f component-wise by another Vector4fr and store the result in <code>dest</code>.
     *
     * @param v
     *          the vector to divide by
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Vector4f div(Vector4fr v, Vector4f dest) {
        dest.x = x() / v.x();
        dest.y = y() / v.y();
        dest.z = z() / v.z();
        dest.w = w() / v.w();
        return dest;
    }

    /**
     * Multiply this Vector4f by the given matrix mat and store the result in
     * <code>dest</code>.
     *
     * @param mat
     *          the matrix to multiply the vector with
     * @param dest
     *          the destination vector to hold the result
     * @return dest
     */
    public Vector4f mul(Matrix4fr mat, Vector4f dest) {
        dest.set(mat.m00() * x() + mat.m10() * y() + mat.m20() * z() + mat.m30() * w(),
                 mat.m01() * x() + mat.m11() * y() + mat.m21() * z() + mat.m31() * w(),
                 mat.m02() * x() + mat.m12() * y() + mat.m22() * z() + mat.m32() * w(),
                 mat.m03() * x() + mat.m13() * y() + mat.m23() * z() + mat.m33() * w());
        return dest;
    }

    /**
     * Multiply this Vector4f by the given matrix <code>mat</code>, perform perspective division
     * and store the result in <code>dest</code>.
     *
     * @param mat
     *          the matrix to multiply this vector by
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Vector4f mulProject(Matrix4fr mat, Vector4f dest) {
        float invW = 1.0f / (mat.m03() * x() + mat.m13() * y() + mat.m23() * z() + mat.m33() * w());
        dest.set((mat.m00() * x() + mat.m10() * y() + mat.m20() * z() + mat.m30() * w()) * invW,
                 (mat.m01() * x() + mat.m11() * y() + mat.m21() * z() + mat.m31() * w()) * invW,
                 (mat.m02() * x() + mat.m12() * y() + mat.m22() * z() + mat.m32() * w()) * invW,
                 1.0f);
        return dest;
    }

    /**
     * Multiply all components of this {@link Vector4f} by the given scalar
     * value and store the result in <code>dest</code>.
     *
     * @param scalar
     *          the scalar to multiply by
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Vector4f mul(float scalar, Vector4f dest) {
        dest.x = x() * scalar;
        dest.y = y() * scalar;
        dest.z = z() * scalar;
        dest.w = w() * scalar;
        return dest;
    }

    /**
     * Multiply the components of this Vector4f by the given scalar values and store the result in <code>dest</code>.
     *
     * @param x
     *          the x component to multiply by
     * @param y
     *          the y component to multiply by
     * @param z
     *          the z component to multiply by
     * @param w
     *          the w component to multiply by
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Vector4f mul(float x, float y, float z, float w, Vector4f dest) {
        dest.x = this.x() * x;
        dest.y = this.y() * y;
        dest.z = this.z() * z;
        dest.w = this.w() * w;
        return dest;
    }

    /**
     * Divide all components of this {@link Vector4f} by the given scalar
     * value and store the result in <code>dest</code>.
     *
     * @param scalar
     *          the scalar to divide by
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Vector4f div(float scalar, Vector4f dest) {
        dest.x = x() / scalar;
        dest.y = y() / scalar;
        dest.z = z() / scalar;
        dest.w = w() / scalar;
        return dest;
    }

    /**
     * Divide the components of this Vector4f by the given scalar values and store the result in <code>dest</code>.
     *
     * @param x
     *          the x component to divide by
     * @param y
     *          the y component to divide by
     * @param z
     *          the z component to divide by
     * @param w
     *          the w component to divide by
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Vector4f div(float x, float y, float z, float w, Vector4f dest) {
        dest.x = this.x() / x;
        dest.y = this.y() / y;
        dest.z = this.z() / z;
        dest.w = this.w() / w;
        return dest;
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
     * @return dest
     */
    public Vector4f rotate(Quaternionfr quat, Vector4f dest) {
        return quat.transform(this, dest);
    }

    /**
     * Return the length squared of this vector.
     *
     * @return the length squared
     */
    public float lengthSquared() {
        return x() * x() + y() * y() + z() * z() + w() * w();
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
     * Normalizes this vector and store the result in <code>dest</code>.
     *
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Vector4f normalize(Vector4f dest) {
        float invLength = 1.0f / length();
        dest.x = x() * invLength;
        dest.y = y() * invLength;
        dest.z = z() * invLength;
        dest.w = w() * invLength;
        return dest;
    }

    /**
     * Return the distance between <code>this</code> vector and <code>v</code>.
     *
     * @param v
     *          the other vector
     * @return the euclidean distance
     */
    public float distance(Vector4fr v) {
        float dx = v.x() - x();
        float dy = v.y() - y();
        float dz = v.z() - z();
        float dw = v.w() - w();
        return (float) Math.sqrt(dx * dx + dy * dy + dz * dz + dw * dw);
    }

    /**
     * Return the distance between <code>this</code> vector and <tt>(x, y, z, w)</tt>.
     *
     * @param x
     *          the x component of the other vector
     * @param y
     *          the y component of the other vector
     * @param z
     *          the z component of the other vector
     * @param w
     *          the w component of the other vector
     * @return the euclidean distance
     */
    public float distance(float x, float y, float z, float w) {
        float dx = this.x() - x;
        float dy = this.y() - y;
        float dz = this.z() - z;
        float dw = this.w() - w;
        return (float) Math.sqrt(dx * dx + dy * dy + dz * dz + dw * dw);
    }

    /**
     * Compute the dot product (inner product) of this vector and <code>v</code>
     * .
     *
     * @param v
     *          the other vector
     * @return the dot product
     */
    public float dot(Vector4fr v) {
        return x() * v.x() + y() * v.y() + z() * v.z() + w() * v.w();
    }

    /**
     * Compute the dot product (inner product) of this vector and <tt>(x, y, z, w)</tt>.
     *
     * @param x
     *          the x component of the other vector
     * @param y
     *          the y component of the other vector
     * @param z
     *          the z component of the other vector
     * @param w
     *          the w component of the other vector
     * @return the dot product
     */
    public float dot(float x, float y, float z, float w) {
        return this.x() * x + this.y() * y + this.z() * z + this.w() * w;
    }

    /**
     * Return the cosine of the angle between this vector and the supplied vector. Use this instead of <code>Math.cos(angle(v))</code>.
     *
     * @see #angle(Vector4fr)
     *
     * @param v
     *          the other vector
     * @return the cosine of the angle
     */
    public float angleCos(Vector4fr v) {
        double length1Squared = x() * x() + y() * y() + z() * z() + w() * w();
        double length2Squared = v.x() * v.x() + v.y() * v.y() + v.z() * v.z() + v.w() * v.w();
        double dot = x() * v.x() + y() * v.y() + z() * v.z() + w() * v.w();
        return (float) (dot / (Math.sqrt(length1Squared * length2Squared)));
    }

    /**
     * Return the angle between this vector and the supplied vector.
     *
     * @see #angleCos(Vector4fr)
     *
     * @param v
     *          the other vector
     * @return the angle, in radians
     */
    public float angle(Vector4fr v) {
        float cos = angleCos(v);
        // This is because sometimes cos goes above 1 or below -1 because of lost precision
        cos = Math.min(cos, 1);
        cos = Math.max(cos, -1);
        return (float) Math.acos(cos);
    }

    /**
     * Negate this vector and store the result in <code>dest</code>.
     *
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Vector4f negate(Vector4f dest) {
        dest.x = -x();
        dest.y = -y();
        dest.z = -z();
        dest.w = -w();
        return dest;
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
        return "(" + formatter.format(x()) + " " + formatter.format(y()) + " " + formatter.format(z()) + " " + formatter.format(w()) + ")"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
    }

    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Float.floatToIntBits(w());
        result = prime * result + Float.floatToIntBits(x());
        result = prime * result + Float.floatToIntBits(y());
        result = prime * result + Float.floatToIntBits(z());
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Vector4fr) {
            Vector4fr other = (Vector4fr) obj;
            return Float.floatToIntBits(w()) == Float.floatToIntBits(other.w())
                    && Float.floatToIntBits(x()) == Float.floatToIntBits(other.x())
                    && Float.floatToIntBits(y()) == Float.floatToIntBits(other.y())
                    && Float.floatToIntBits(z()) == Float.floatToIntBits(other.z());
        }
        return false;
    }

    /**
     * Compute a smooth-step (i.e. hermite with zero tangents) interpolation
     * between <code>this</code> vector and the given vector <code>v</code> and
     * store the result in <code>dest</code>.
     *
     * @param v
     *          the other vector
     * @param t
     *          the interpolation factor, within <tt>[0..1]</tt>
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Vector4f smoothStep(Vector4fr v, float t, Vector4f dest) {
        float t2 = t * t;
        float t3 = t2 * t;
        dest.x = (2.0f * x() - 2.0f * v.x()) * t3 + (3.0f * v.x() - 3.0f * x()) * t2 + x() * t + x();
        dest.y = (2.0f * y() - 2.0f * v.y()) * t3 + (3.0f * v.y() - 3.0f * y()) * t2 + y() * t + y();
        dest.z = (2.0f * z() - 2.0f * v.z()) * t3 + (3.0f * v.z() - 3.0f * z()) * t2 + z() * t + z();
        dest.w = (2.0f * w() - 2.0f * v.w()) * t3 + (3.0f * v.w() - 3.0f * w()) * t2 + w() * t + w();
        return dest;
    }

    /**
     * Compute a hermite interpolation between <code>this</code> vector and its
     * associated tangent <code>t0</code> and the given vector <code>v</code>
     * with its tangent <code>t1</code> and store the result in
     * <code>dest</code>.
     *
     * @param t0
     *          the tangent of <code>this</code> vector
     * @param v1
     *          the other vector
     * @param t1
     *          the tangent of the other vector
     * @param t
     *          the interpolation factor, within <tt>[0..1]</tt>
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Vector4f hermite(Vector4fr t0, Vector4fr v1, Vector4fr t1, float t, Vector4f dest) {
        float t2 = t * t;
        float t3 = t2 * t;
        dest.x = (2.0f * x() - 2.0f * v1.x() + t1.x() + t0.x()) * t3 + (3.0f * v1.x() - 3.0f * x() - 2.0f * t0.x() - t1.x()) * t2 + x() * t + x();
        dest.y = (2.0f * y() - 2.0f * v1.y() + t1.y() + t0.y()) * t3 + (3.0f * v1.y() - 3.0f * y() - 2.0f * t0.y() - t1.y()) * t2 + y() * t + y();
        dest.z = (2.0f * z() - 2.0f * v1.z() + t1.z() + t0.z()) * t3 + (3.0f * v1.z() - 3.0f * z() - 2.0f * t0.z() - t1.z()) * t2 + z() * t + z();
        dest.w = (2.0f * w() - 2.0f * v1.w() + t1.w() + t0.w()) * t3 + (3.0f * v1.w() - 3.0f * w() - 2.0f * t0.w() - t1.w()) * t2 + w() * t + w();
        return dest;
    }

    /**
     * Linearly interpolate <code>this</code> and <code>other</code> using the given interpolation factor <code>t</code>
     * and store the result in <code>dest</code>.
     * <p>
     * If <code>t</code> is <tt>0.0</tt> then the result is <code>this</code>. If the interpolation factor is <code>1.0</code>
     * then the result is <code>other</code>.
     *
     * @param other
     *          the other vector
     * @param t
     *          the interpolation factor between 0.0 and 1.0
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Vector4f lerp(Vector4fr other, float t, Vector4f dest) {
        dest.x = (1.0f - t) * x() + t * other.x();
        dest.y = (1.0f - t) * y() + t * other.y();
        dest.z = (1.0f - t) * z() + t * other.z();
        dest.w = (1.0f - t) * w() + t * other.w();
        return dest;
    }
}
