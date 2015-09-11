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
import java.nio.DoubleBuffer;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Abstract base class containing the readable definition of a Vector comprising 4 doubles and associated transformations.
 *
 * @author Richard Greenlees
 * @author Kai Burjack
 */
public abstract class Vector4dr {

    /**
     * @return The x component of the vector.
     */
    public abstract double x();
    /**
     * @return The y component of the vector.
     */
    public abstract double y();
    /**
     * @return The z component of the vector.
     */
    public abstract double z();
    /**
     * @return The w component of the vector.
     */
    public abstract double w();

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
    public static void sub(Vector4dr v1, Vector4dr v2, Vector4d dest) {
        dest.x = v1.x() - v2.x();
        dest.y = v1.y() - v2.y();
        dest.z = v1.z() - v2.z();
        dest.w = v1.w() - v2.w();
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
    public static void sub(Vector4dr v1, Vector4fr v2, Vector4d dest) {
        dest.x = v1.x() - v2.x();
        dest.y = v1.y() - v2.y();
        dest.z = v1.z() - v2.z();
        dest.w = v1.w() - v2.w();
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
    public static void sub(Vector4fr v1, Vector4dr v2, Vector4d dest) {
        dest.x = v1.x() - v2.x();
        dest.y = v1.y() - v2.y();
        dest.z = v1.z() - v2.z();
        dest.w = v1.w() - v2.w();
    }

    /**
     * Add <code>v2</code> to <code>v1</code> and store the result in <code>dest</code>.
     *
     * @param v1
     *          the first addend
     * @param v2
     *          the second addend
     * @param dest
     *          will hold the result
     */
    public static void add(Vector4dr v1, Vector4dr v2, Vector4d dest) {
        dest.x = v1.x() + v2.x();
        dest.y = v1.y() + v2.y();
        dest.z = v1.z() + v2.z();
        dest.w = v1.w() + v2.w();
    }

    /**
     * Add <code>v2</code> to <code>v1</code> and store the result in <code>dest</code>.
     *
     * @param v1
     *          the first addend
     * @param v2
     *          the second addend
     * @param dest
     *          will hold the result
     */
    public static void add(Vector4dr v1, Vector4fr v2, Vector4d dest) {
        dest.x = v1.x() + v2.x();
        dest.y = v1.y() + v2.y();
        dest.z = v1.z() + v2.z();
        dest.w = v1.w() + v2.w();
    }

    /**
     * Add <code>v2</code> to <code>v1</code> and store the result in <code>dest</code>.
     *
     * @param v1
     *          the first addend
     * @param v2
     *          the second addend
     * @param dest
     *          will hold the result
     */
    public static void add(Vector4fr v1, Vector4dr v2, Vector4d dest) {
        dest.x = v1.x() + v2.x();
        dest.y = v1.y() + v2.y();
        dest.z = v1.z() + v2.z();
        dest.w = v1.w() + v2.w();
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
        buffer.putDouble(index,      x());
        buffer.putDouble(index + 8,  y());
        buffer.putDouble(index + 16,  z());
        buffer.putDouble(index + 24,  w());
        return buffer;
    }

    /**
     * Store this vector into the supplied {@link DoubleBuffer} at the current
     * buffer {@link DoubleBuffer#position() position}.
     * <p>
     * This method will not increment the position of the given DoubleBuffer.
     * <p>
     * In order to specify the offset into the DoubleBuffer at which
     * the vector is stored, use {@link #get(int, DoubleBuffer)}, taking
     * the absolute position as parameter.
     *
     * @param buffer
     *          will receive the values of this vector in <tt>x, y, z, w</tt> order
     * @return the passed in buffer
     * @see #get(int, DoubleBuffer)
     */
    public DoubleBuffer get(DoubleBuffer buffer) {
        return get(buffer.position(), buffer);
    }

    /**
     * Store this vector into the supplied {@link DoubleBuffer} starting at the specified
     * absolute buffer position/index.
     * <p>
     * This method will not increment the position of the given DoubleBuffer.
     *
     * @param index
     *          the absolute position into the DoubleBuffer
     * @param buffer
     *          will receive the values of this vector in <tt>x, y, z, w</tt> order
     * @return the passed in buffer
     */
    public DoubleBuffer get(int index, DoubleBuffer buffer) {
        buffer.put(index,      x());
        buffer.put(index + 1,  y());
        buffer.put(index + 2,  z());
        buffer.put(index + 3,  w());
        return buffer;
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
    public Vector4d sub(double x, double y, double z, double w, Vector4d dest) {
        dest.x = this.x() - x;
        dest.y = this.y() - y;
        dest.z = this.z() - z;
        dest.w = this.w() - w;
        return dest;
    }

    /**
     * Add <tt>(x, y, z, w)</tt> to this and store the result in <code>dest</code>.
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
    public Vector4d add(double x, double y, double z, double w, Vector4d dest) {
        dest.x = this.x() - x;
        dest.y = this.y() - y;
        dest.z = this.z() - z;
        dest.w = this.w() - w;
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
    public Vector4d fma(Vector4dr a, Vector4dr b, Vector4d dest) {
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
    public Vector4d fma(double a, Vector4dr b, Vector4d dest) {
        dest.x = x() + a * b.x();
        dest.y = y() + a * b.y();
        dest.z = z() + a * b.z();
        dest.w = w() + a * b.w();
        return dest;
    }

    /**
     * Multiply this {@link Vector4d} component-wise by the given {@link Vector4d} and store the result in <code>dest</code>.
     *
     * @param v
     * 			the vector to multiply this by
     * @param dest
     * 			will hold the result
     * @return dest
     */
    public Vector4d mul(Vector4dr v, Vector4d dest) {
        dest.x = x() * v.x();
        dest.y = y() * v.y();
        dest.z = z() * v.z();
        dest.w = w() * v.w();
        return dest;
    }

    /**
     * Divide this {@link Vector4d} component-wise by the given {@link Vector4d} and store the result in <code>dest</code>.
     *
     * @param v
     * 			the vector to divide this by
     * @param dest
     * 			will hold the result
     * @return dest
     */
    public Vector4d div(Vector4dr v, Vector4d dest) {
        dest.x = x() / v.x();
        dest.y = y() / v.y();
        dest.z = z() / v.z();
        dest.w = w() / v.w();
        return dest;
    }

    /**
     * Multiply this {@link Vector4d} by the given matrix mat and store the result in <code>dest</code>.
     *
     * @param mat
     *          the matrix to multiply <code>this</code> by
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Vector4d mul(Matrix4dr mat, Vector4d dest) {
        dest.set(mat.m00() * x() + mat.m10() * y() + mat.m20() * z() + mat.m30() * w(),
                 mat.m01() * x() + mat.m11() * y() + mat.m21() * z() + mat.m31() * w(),
                 mat.m02() * x() + mat.m12() * y() + mat.m22() * z() + mat.m32() * w(),
                 mat.m03() * x() + mat.m13() * y() + mat.m23() * z() + mat.m33() * w());
        return dest;
    }

    /**
     * Multiply this Vector4d by the given matrix mat and store the result in <code>dest</code>.
     *
     * @param mat
     *          the matrix to multiply <code>this</code> by
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Vector4d mul(Matrix4fr mat, Vector4d dest) {
        dest.set(mat.m00() * x() + mat.m10() * y() + mat.m20() * z() + mat.m30() * w(),
                 mat.m01() * x() + mat.m11() * y() + mat.m21() * z() + mat.m31() * w(),
                 mat.m02() * x() + mat.m12() * y() + mat.m22() * z() + mat.m32() * w(),
                 mat.m03() * x() + mat.m13() * y() + mat.m23() * z() + mat.m33() * w());
        return dest;
    }

    /**
     * Multiply this Vector4d by the given matrix <code>mat</code>, perform perspective division
     * and store the result in <code>dest</code>.
     *
     * @param mat
     *          the matrix to multiply this vector by
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Vector4d mulProject(Matrix4dr mat, Vector4d dest) {
        double invW = 1.0 / (mat.m03() * x() + mat.m13() * y() + mat.m23() * z() + mat.m33() * w());
        dest.set((mat.m00() * x() + mat.m10() * y() + mat.m20() * z() + mat.m30() * w()) * invW,
                 (mat.m01() * x() + mat.m11() * y() + mat.m21() * z() + mat.m31() * w()) * invW,
                 (mat.m02() * x() + mat.m12() * y() + mat.m22() * z() + mat.m32() * w()) * invW,
                 1.0);
        return dest;
    }

    /**
     * Multiply this Vector4d by the given scalar value and store the result in <code>dest</code>.
     *
     * @param scalar
     *          the factor to multiply by
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Vector4d mul(double scalar, Vector4d dest) {
        dest.x = x() * scalar;
        dest.y = y() * scalar;
        dest.z = z() * scalar;
        dest.w = w() * scalar;
        return dest;
    }

    /**
     * Divide this Vector4d by the given scalar value and store the result in <code>dest</code>.
     *
     * @param scalar
     *          the factor to divide by
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Vector4d div(double scalar, Vector4d dest) {
        dest.x = x() / scalar;
        dest.y = y() / scalar;
        dest.z = z() / scalar;
        dest.w = w() / scalar;
        return dest;
    }

    /**
     * Transform this vector by the given quaternion <code>quat</code> and store the result in <code>dest</code>.
     *
     * @see Quaterniond#transform(Vector4d)
     *
     * @param quat
     *          the quaternion to transform this vector
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Vector4d rotate(Quaterniondr quat, Vector4d dest) {
        quat.transform(this, dest);
        return dest;
    }

    /**
     * Return the length squared of this vector.
     *
     * @return the length squared
     */
    public double lengthSquared() {
        return x() * x() + y() * y() + z() * z() + w() * w();
    }

    /**
     * Return the length of this vector.
     *
     * @return the length
     */
    public double length() {
        return Math.sqrt(lengthSquared());
    }

    /**
     * Normalizes this vector and store the result in <code>dest</code>.
     *
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Vector4d normalize(Vector4d dest) {
        double invLength = 1.0 / length();
        dest.x = x() * invLength;
        dest.y = y() * invLength;
        dest.z = z() * invLength;
        dest.w = w() * invLength;
        return dest;
    }

    /**
     * Normalize this vector by computing only the norm of <tt>(x, y, z)</tt> and store the result in <code>dest</code>.
     *
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Vector4d normalize3(Vector4d dest) {
        double invLength = 1.0 / Math.sqrt(x() * x() + y() * y() + z() * z());
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
    public double distance(Vector4dr v) {
        double dx = v.x() - x();
        double dy = v.y() - y();
        double dz = v.z() - z();
        double dw = v.w() - w();
        return Math.sqrt(dx * dx + dy * dy + dz * dz + dw * dw);
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
    public double distance(double x, double y, double z, double w) {
        double dx = this.x() - x;
        double dy = this.y() - y;
        double dz = this.z() - z;
        double dw = this.w() - w;
        return Math.sqrt(dx * dx + dy * dy + dz * dz + dw * dw);
    }

    /**
     * Compute the dot product (inner product) of this vector and <code>v</code>.
     *
     * @param v
     *          the other vector
     * @return the dot product
     */
    public double dot(Vector4dr v) {
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
    public double dot(double x, double y, double z, double w) {
        return this.x() * x + this.y() * y + this.z() * z + this.w() * w;
    }

    /**
     * Return the cosine of the angle between this vector and the supplied vector.
     * <p>
     * Use this instead of <code>Math.cos(angle(v))</code>.
     *
     * @see #angle(Vector4dr)
     *
     * @param v
     *          the other vector
     * @return the cosine of the angle
     */
    public double angleCos(Vector4dr v) {
        double length1Sqared = x() * x() + y() * y() + z() * z() + w() * w();
        double length2Sqared = v.x() * v.x() + v.y() * v.y() + v.z() * v.z() + v.w() * v.w();
        double dot = x() * v.x() + y() * v.y() + z() * v.z() + w() * v.w();
        return dot / (Math.sqrt(length1Sqared * length2Sqared));
    }

    /**
     * Return the angle between this vector and the supplied vector.
     *
     * @see #angleCos(Vector4dr)
     *
     * @param v
     *          the other vector
     * @return the angle, in radians
     */
    public double angle(Vector4dr v) {
        double cos = angleCos(v);
        // This is because sometimes cos goes above 1 or below -1 because of lost precision
        cos = Math.min(cos, 1);
        cos = Math.max(cos, -1);
        return Math.acos(cos);
    }

    /**
     * Negate this vector and store the result in <code>dest</code>.
     *
     * @param dest
     *          will hold the result
     * @return dest
     */
    public Vector4d negate(Vector4d dest) {
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
        long temp;
        temp = Double.doubleToLongBits(w());
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(x());
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y());
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(z());
        result = prime * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Vector4dr) {
            Vector4dr other = (Vector4dr) obj;
            return Double.doubleToLongBits(w()) == Double.doubleToLongBits(other.w()) && Double.doubleToLongBits(x()) == Double.doubleToLongBits(other.x())
                    && Double.doubleToLongBits(y()) == Double.doubleToLongBits(other.y()) && Double.doubleToLongBits(z()) == Double.doubleToLongBits(other.z());
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
    public Vector4d smoothStep(Vector4dr v, double t, Vector4d dest) {
        double t2 = t * t;
        double t3 = t2 * t;
        dest.x = (2.0 * x() - 2.0 * v.x()) * t3 + (3.0 * v.x() - 3.0 * x()) * t2 + x() * t + x();
        dest.y = (2.0 * y() - 2.0 * v.y()) * t3 + (3.0 * v.y() - 3.0 * y()) * t2 + y() * t + y();
        dest.z = (2.0 * z() - 2.0 * v.z()) * t3 + (3.0 * v.z() - 3.0 * z()) * t2 + z() * t + z();
        dest.w = (2.0 * w() - 2.0 * v.w()) * t3 + (3.0 * v.w() - 3.0 * w()) * t2 + w() * t + w();
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
    public Vector4d hermite(Vector4dr t0, Vector4dr v1, Vector4dr t1, double t, Vector4d dest) {
        double t2 = t * t;
        double t3 = t2 * t;
        dest.x = (2.0 * x() - 2.0 * v1.x() + t1.x() + t0.x()) * t3 + (3.0 * v1.x() - 3.0 * x() - 2.0 * t0.x() - t1.x()) * t2 + x() * t + x();
        dest.y = (2.0 * y() - 2.0 * v1.y() + t1.y() + t0.y()) * t3 + (3.0 * v1.y() - 3.0 * y() - 2.0 * t0.y() - t1.y()) * t2 + y() * t + y();
        dest.z = (2.0 * z() - 2.0 * v1.z() + t1.z() + t0.z()) * t3 + (3.0 * v1.z() - 3.0 * z() - 2.0 * t0.z() - t1.z()) * t2 + z() * t + z();
        dest.w = (2.0 * w() - 2.0 * v1.w() + t1.w() + t0.w()) * t3 + (3.0 * v1.w() - 3.0 * w() - 2.0 * t0.w() - t1.w()) * t2 + w() * t + w();
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
    public Vector4d lerp(Vector4dr other, double t, Vector4d dest) {
        dest.x = (1.0 - t) * x() + t * other.x();
        dest.y = (1.0 - t) * y() + t * other.y();
        dest.z = (1.0 - t) * z() + t * other.z();
        dest.w = (1.0 - t) * w() + t * other.w();
        return dest;
    }
}
