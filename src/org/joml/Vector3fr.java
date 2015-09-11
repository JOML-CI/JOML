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
 * Abstract base class containing the readable definition of a Vector comprising 3 floats and associated
 * transformations.
 *
 * @author Richard Greenlees
 * @author Kai Burjack
 */
public abstract class Vector3fr {

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
     * Store this vector into the supplied {@link FloatBuffer} at the current
     * buffer {@link FloatBuffer#position() position}.
     * <p>
     * This method will not increment the position of the given FloatBuffer.
     * <p>
     * In order to specify the offset into the FloatBuffer at which
     * the vector is stored, use {@link #get(int, FloatBuffer)}, taking
     * the absolute position as parameter.
     *
     * @see #get(int, FloatBuffer)
     *
     * @param buffer
     *          will receive the values of this vector in <tt>x, y, z</tt> order
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
     *          will receive the values of this vector in <tt>x, y, z</tt> order
     * @return the passed in buffer
     */
    public FloatBuffer get(int index, FloatBuffer buffer) {
        buffer.put(index,    x());
        buffer.put(index+1,  y());
        buffer.put(index+2,  z());
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
     * @see #get(int, ByteBuffer)
     *
     * @param buffer
     *          will receive the values of this vector in <tt>x, y, z</tt> order
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
     *          will receive the values of this vector in <tt>x, y, z</tt> order
     * @return the passed in buffer
     */
    public ByteBuffer get(int index, ByteBuffer buffer) {
        buffer.putFloat(index,    x());
        buffer.putFloat(index+4,  y());
        buffer.putFloat(index+8,  z());
        return buffer;
    }

    /**
     * Subtract the supplied vector from this one and store the result in <code>dest</code>.
     *
     * @param v
     *          the vector to subtract
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Vector3f sub(Vector3fr v, Vector3f dest) {
        dest.x = x() - v.x();
        dest.y = y() - v.y();
        dest.z = z() - v.z();
        return dest;
    }

    /**
     * Decrement the components of this vector by the given values and store the result in <code>dest</code>.
     *
     * @param x
     *          the x component to subtract
     * @param y
     *          the y component to subtract
     * @param z
     *          the z component to subtract
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Vector3f sub(float x, float y, float z, Vector3f dest) {
        dest.x = this.x() - x;
        dest.y = this.y() - y;
        dest.z = this.z() - z;
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
    public Vector3f add(Vector3fr v, Vector3f dest) {
        dest.x = x() + v.x();
        dest.y = y() + v.y();
        dest.z = z() + v.z();
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
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Vector3f add(float x, float y, float z, Vector3f dest) {
        dest.x = this.x() + x;
        dest.y = this.y() + y;
        dest.z = this.z() + z;
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
    public Vector3f fma(Vector3fr a, Vector3fr b, Vector3f dest) {
        dest.x = x() + a.x() * b.x();
        dest.y = y() + a.y() * b.y();
        dest.z = z() + a.z() * b.z();
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
    public Vector3f fma(float a, Vector3fr b, Vector3f dest) {
        dest.x = x() + a * b.x();
        dest.y = y() + a * b.y();
        dest.z = z() + a * b.z();
        return dest;
    }

    /**
     * Multiply this Vector3f component-wise by another Vector3fr and store the result in <code>dest</code>.
     *
     * @param v
     *          the vector to multiply by
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Vector3f mul(Vector3fr v, Vector3f dest) {
        dest.x = x() * v.x();
        dest.y = y() * v.y();
        dest.z = z() * v.z();
        return dest;
    }

    /**
     * Divide this Vector3f component-wise by another Vector3fr and store the result in <code>dest</code>.
     *
     * @param v
     *          the vector to divide by
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Vector3f div(Vector3fr v, Vector3f dest) {
        dest.x = x() / v.x();
        dest.y = y() / v.y();
        dest.z = z() / v.z();
        return dest;
    }

    /**
     * Multiply this Vector3f by the given matrix <code>mat</code>, perform perspective division
     * and store the result in <code>dest</code>.
     * <p>
     * This method uses <tt>w=1.0</tt> as the fourth vector component.
     *
     * @param mat
     *          the matrix to multiply this vector by
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Vector3f mulProject(Matrix4fr mat, Vector3f dest) {
        float invW = 1.0f / (mat.m03() * x() + mat.m13() * y() + mat.m23() * z() + mat.m33());
        dest.set((mat.m00() * x() + mat.m10() * y() + mat.m20() * z() + mat.m30()) * invW,
                 (mat.m01() * x() + mat.m11() * y() + mat.m21() * z() + mat.m31()) * invW,
                 (mat.m02() * x() + mat.m12() * y() + mat.m22() * z() + mat.m32()) * invW);
        return dest;
    }

    /**
     * Multiply this Vector3f by the given matrix and store the result in <code>dest</code>.
     *
     * @param mat
     *          the matrix
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Vector3f mul(Matrix3fr mat, Vector3f dest) {
        dest.set(mat.m00() * x() + mat.m10() * y() + mat.m20() * z(),
                 mat.m01() * x() + mat.m11() * y() + mat.m21() * z(),
                 mat.m02() * x() + mat.m12() * y() + mat.m22() * z());
        return dest;
    }

    /**
     * Multiply <code>this</code> by the given 4x4 matrix <code>mat</code> and store the
     * result in <code>dest</code>.
     * <p>
     * This method assumes the <tt>w</tt> component of <code>this</code> to be <tt>1.0</tt>.
     *
     * @param mat
     *          the matrix to multiply this vector by
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Vector3f mulPoint(Matrix4fr mat, Vector3f dest) {
        dest.set(mat.m00() * x() + mat.m10() * y() + mat.m20() * z() + mat.m30(),
                 mat.m01() * x() + mat.m11() * y() + mat.m21() * z() + mat.m31(),
                 mat.m02() * x() + mat.m12() * y() + mat.m22() * z() + mat.m32());
        return dest;
    }

    /**
     * Multiply <code>this</code> by the given 4x4 matrix <code>mat</code> and store the
     * result in <code>dest</code>.
     * <p>
     * This method assumes the <tt>w</tt> component of <code>this</code> to be <tt>0.0</tt>.
     *
     * @param mat
     *          the matrix to multiply this vector by
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Vector3f mulDirection(Matrix4fr mat, Vector3f dest) {
        dest.set(mat.m00() * x() + mat.m10() * y() + mat.m20() * z(),
                 mat.m01() * x() + mat.m11() * y() + mat.m21() * z(),
                 mat.m02() * x() + mat.m12() * y() + mat.m22() * z());
        return dest;
    }

    /**
     * Multiply all components of this {@link Vector3f} by the given scalar
     * value and store the result in <code>dest</code>.
     *
     * @param scalar
     *          the scalar to multiply this vector by
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Vector3f mul(float scalar, Vector3f dest) {
        dest.x = x() * scalar;
        dest.y = y() * scalar;
        dest.z = z() * scalar;
        return dest;
    }

    /**
     * Multiply the components of this Vector3f by the given scalar values and store the result in <code>dest</code>.
     *
     * @param x
     *          the x component to multiply this vector by
     * @param y
     *          the y component to multiply this vector by
     * @param z
     *          the z component to multiply this vector by
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Vector3f mul(float x, float y, float z, Vector3f dest) {
        dest.x = this.x() * x;
        dest.y = this.y() * y;
        dest.z = this.z() * z;
        return dest;
    }

    /**
     * Divide all components of this {@link Vector3f} by the given scalar
     * value and store the result in <code>dest</code>.
     *
     * @param scalar
     *          the scalar to divide by
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Vector3f div(float scalar, Vector3f dest) {
        dest.x = x() / scalar;
        dest.y = y() / scalar;
        dest.z = z() / scalar;
        return dest;
    }

    /**
     * Divide the components of this Vector3f by the given scalar values and store the result in <code>dest</code>.
     *
     * @param x
     *          the x component to divide this vector by
     * @param y
     *          the y component to divide this vector by
     * @param z
     *          the z component to divide this vector by
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Vector3f div(float x, float y, float z, Vector3f dest) {
        dest.x = this.x() / x;
        dest.y = this.y() / y;
        dest.z = this.z() / z;
        return dest;
    }

    /**
     * Rotate this vector by the given quaternion <code>quat</code> and store the result in <code>dest</code>.
     *
     * @see Quaternionf#transform(Vector3f)
     *
     * @param quat
     *          the quaternion to rotate this vector
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Vector3f rotate(Quaternionfr quat, Vector3f dest) {
        quat.transform(this, dest);
        return dest;
    }

    /**
     * Return the length squared of this vector.
     *
     * @return the length squared
     */
    public float lengthSquared() {
        return x() * x() + y() * y() + z() * z();
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
     * Normalize this vector and store the result in <code>dest</code>.
     *
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Vector3f normalize(Vector3f dest) {
        float invLength = 1.0f / length();
        dest.x = x() * invLength;
        dest.y = y() * invLength;
        dest.z = z() * invLength;
        return dest;
    }

    /**
     * Compute the cross product of this vector and <code>v</code> and store the result in <code>dest</code>.
     *
     * @param v
     *          the other vector
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Vector3f cross(Vector3fr v, Vector3f dest) {
        return dest.set(y() * v.z() - z() * v.y(),
                        z() * v.x() - x() * v.z(),
                        x() * v.y() - y() * v.x());
    }

    /**
     * Compute the cross product of this vector and <tt>(x, y, z)</tt> and store the result in <code>dest</code>.
     *
     * @param x
     *          the x component of the other vector
     * @param y
     *          the y component of the other vector
     * @param z
     *          the z component of the other vector
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Vector3f cross(float x, float y, float z, Vector3f dest) {
        return dest.set(this.y() * z - this.z() * y,
                        this.z() * x - this.x() * z,
                        this.x() * y - this.y() * x);
    }

    /**
     * Return the distance between this Vector and <code>v</code>.
     *
     * @param v
     *          the other vector
     * @return the distance
     */
    public float distance(Vector3fr v) {
        float dx = v.x() - x();
        float dy = v.y() - y();
        float dz = v.z() - z();
        return (float) Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    /**
     * Return the distance between <code>this</code> vector and <tt>(x, y, z)</tt>.
     *
     * @param x
     *          the x component of the other vector
     * @param y
     *          the y component of the other vector
     * @param z
     *          the z component of the other vector
     * @return the euclidean distance
     */
    public float distance(float x, float y, float z) {
        float dx = this.x() - x;
        float dy = this.y() - y;
        float dz = this.z() - z;
        return (float) Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    /**
     * Return the square of the distance between this vector and <code>v</code>.
     *
     * @param v
     *          the other vector
     * @return the squared of the distance
     */
    public float distanceSquared(Vector3fr v) {
        float dx = v.x() - x();
        float dy = v.y() - y();
        float dz = v.z() - z();
        return dx * dx + dy * dy + dz * dz;
    }

    /**
     * Return the square of the distance between <code>this</code> vector and <tt>(x, y, z)</tt>.
     *
     * @param x
     *          the x component of the other vector
     * @param y
     *          the y component of the other vector
     * @param z
     *          the z component of the other vector
     * @return the square of the distance
     */
    public float distanceSquared(float x, float y, float z) {
        float dx = this.x() - x;
        float dy = this.y() - y;
        float dz = this.z() - z;
        return dx * dx + dy * dy + dz * dz;
    }

    /**
     * Return the dot product of this vector and the supplied vector.
     *
     * @param v
     *          the other vector
     * @return the dot product
     */
    public float dot(Vector3fr v) {
        return x() * v.x() + y() * v.y() + z() * v.z();
    }

    /**
     * Return the dot product of this vector and the vector <tt>(x, y, z)</tt>.
     *
     * @param x
     *          the x component of the other vector
     * @param y
     *          the y component of the other vector
     * @param z
     *          the z component of the other vector
     * @return the dot product
     */
    public float dot(float x, float y, float z) {
        return this.x() * x + this.y() * y + this.z() * z;
    }

    /**
     * Return the cosine of the angle between this vector and the supplied vector. Use this instead of Math.cos(this.angle(v)).
     *
     * @see #angle(Vector3fr)
     *
     * @param v
     *          the other vector
     * @return the cosine of the angle
     */
    public float angleCos(Vector3fr v) {
        double length1Sqared = x() * x() + y() * y() + z() * z();
        double length2Sqared = v.x() * v.x() + v.y() * v.y() + v.z() * v.z();
        double dot = x() * v.x() + y() * v.y() + z() * v.z();
        return (float) (dot / (Math.sqrt(length1Sqared * length2Sqared)));
    }

    /**
     * Return the angle between this vector and the supplied vector.
     *
     * @see #angleCos(Vector3fr)
     *
     * @param v
     *          the other vector
     * @return the angle, in radians
     */
    public float angle(Vector3fr v) {
        float cos = angleCos(v);
        // This is because sometimes cos goes above 1 or below -1 because of lost precision
        cos = Math.min(cos, 1);
        cos = Math.max(cos, -1);
        return (float) Math.acos(cos);
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
        return "(" + formatter.format(x()) + " " + formatter.format(y()) + " " + formatter.format(z()) + ")"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
    }

    /**
     * Negate this vector and store the result in <code>dest</code>.
     *
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Vector3f negate(Vector3f dest) {
        dest.x = -x();
        dest.y = -y();
        dest.z = -z();
        return dest;
    }

    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Float.floatToIntBits(x());
        result = prime * result + Float.floatToIntBits(y());
        result = prime * result + Float.floatToIntBits(z());
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Vector3fr) {
            Vector3fr other = (Vector3fr) obj;
            return Float.floatToIntBits(x()) == Float.floatToIntBits(other.x()) && Float.floatToIntBits(y()) == Float.floatToIntBits(other.y())
                    && Float.floatToIntBits(z()) == Float.floatToIntBits(other.z());
        }
        return false;
    }

    /**
     * Reflect this vector about the given <code>normal</code> vector and store the result in <code>dest</code>.
     *
     * @param normal
     *          the vector to reflect about
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Vector3f reflect(Vector3fr normal, Vector3f dest) {
        float dot = this.dot(normal);
        dest.x = x() - 2.0f * dot * normal.x();
        dest.y = y() - 2.0f * dot * normal.y();
        dest.z = z() - 2.0f * dot * normal.z();
        return dest;
    }

    /**
     * Reflect this vector about the given normal vector and store the result in <code>dest</code>.
     *
     * @param x
     *          the x component of the normal
     * @param y
     *          the y component of the normal
     * @param z
     *          the z component of the normal
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Vector3f reflect(float x, float y, float z, Vector3f dest) {
        float dot = this.dot(x, y, z);
        dest.x = this.x() - 2.0f * dot * x;
        dest.y = this.y() - 2.0f * dot * y;
        dest.z = this.z() - 2.0f * dot * z;
        return dest;
    }

    /**
     * Compute the half vector between this and the other vector and store the result in <code>dest</code>.
     *
     * @param other
     *          the other vector
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Vector3f half(Vector3fr other, Vector3f dest) {
        return dest.set(this).add(other).normalize();
    }

    /**
     * Compute the half vector between this and the vector <tt>(x, y, z)</tt>
     * and store the result in <code>dest</code>.
     *
     * @param x
     *          the x component of the other vector
     * @param y
     *          the y component of the other vector
     * @param z
     *          the z component of the other vector
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Vector3f half(float x, float y, float z, Vector3f dest) {
        return dest.set(this).add(x, y, z).normalize();
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
    public Vector3f smoothStep(Vector3fr v, float t, Vector3f dest) {
        float t2 = t * t;
        float t3 = t2 * t;
        dest.x = (2.0f * x() - 2.0f * v.x()) * t3 + (3.0f * v.x() - 3.0f * x()) * t2 + x() * t + x();
        dest.y = (2.0f * y() - 2.0f * v.y()) * t3 + (3.0f * v.y() - 3.0f * y()) * t2 + y() * t + y();
        dest.z = (2.0f * z() - 2.0f * v.z()) * t3 + (3.0f * v.z() - 3.0f * z()) * t2 + z() * t + z();
        return dest;
    }

    /**
     * Compute a hermite interpolation between <code>this</code> vector with its
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
    public Vector3f hermite(Vector3fr t0, Vector3fr v1, Vector3fr t1, float t, Vector3f dest) {
        float t2 = t * t;
        float t3 = t2 * t;
        dest.x = (2.0f * x() - 2.0f * v1.x() + t1.x() + t0.x()) * t3 + (3.0f * v1.x() - 3.0f * x() - 2.0f * t0.x() - t1.x()) * t2 + x() * t + x();
        dest.y = (2.0f * y() - 2.0f * v1.y() + t1.y() + t0.y()) * t3 + (3.0f * v1.y() - 3.0f * y() - 2.0f * t0.y() - t1.y()) * t2 + y() * t + y();
        dest.z = (2.0f * z() - 2.0f * v1.z() + t1.z() + t0.z()) * t3 + (3.0f * v1.z() - 3.0f * z() - 2.0f * t0.z() - t1.z()) * t2 + z() * t + z();
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
    public Vector3f lerp(Vector3fr other, float t, Vector3f dest) {
        dest.x = (1.0f - t) * x() + t * other.x();
        dest.y = (1.0f - t) * y() + t * other.y();
        dest.z = (1.0f - t) * z() + t * other.z();
        return dest;
    }
}
