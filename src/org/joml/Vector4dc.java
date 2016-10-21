/*
 * (C) Copyright 2016 JOML

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

/**
 * Interface to an immutable 4-dimensional vector of double-precision floats.
 * 
 * @author Kai Burjack
 */
public interface Vector4dc {

    /**
     * @return the value of the x component
     */
    double x();

    /**
     * @return the value of the y component
     */
    double y();

    /**
     * @return the value of the z component
     */
    double z();

    /**
     * @return the value of the w component
     */
    double w();

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
    ByteBuffer get(ByteBuffer buffer);

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
    ByteBuffer get(int index, ByteBuffer buffer);

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
    DoubleBuffer get(DoubleBuffer buffer);

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
    DoubleBuffer get(int index, DoubleBuffer buffer);

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
    Vector4d sub(double x, double y, double z, double w, Vector4d dest);

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
    Vector4d add(double x, double y, double z, double w, Vector4d dest);

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
    Vector4d fma(Vector4dc a, Vector4dc b, Vector4d dest);

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
    Vector4d fma(double a, Vector4dc b, Vector4d dest);

    /**
     * Multiply this {@link Vector4d} component-wise by the given {@link Vector4dc} and store the result in <code>dest</code>.
     * 
     * @param v
     *             the vector to multiply this by
     * @param dest
     *             will hold the result
     * @return dest
     */
    Vector4d mul(Vector4dc v, Vector4d dest);

    /**
     * Divide this {@link Vector4d} component-wise by the given {@link Vector4dc} and store the result in <code>dest</code>.
     * 
     * @param v
     *          the vector to divide this by
     * @param dest
     *          will hold the result
     * @return dest
     */
    Vector4d div(Vector4dc v, Vector4d dest);

    /**
     * Multiply the given matrix mat with this {@link Vector4d} and store the result in <code>dest</code>.
     * 
     * @param mat
     *          the matrix to multiply <code>this</code> by
     * @param dest
     *          will hold the result
     * @return dest
     */
    Vector4d mul(Matrix4dc mat, Vector4d dest);

    /**
     * Multiply the given matrix mat with this Vector4d and store the result in
     * <code>dest</code>.
     * 
     * @param mat
     *          the matrix to multiply the vector with
     * @param dest
     *          the destination vector to hold the result
     * @return dest
     */
    Vector4d mul(Matrix4x3d mat, Vector4d dest);

    /**
     * Multiply the given matrix mat with this Vector4d and store the result in <code>dest</code>.
     *
     * @param mat
     *          the matrix to multiply <code>this</code> by
     * @param dest
     *          will hold the result
     * @return dest
     */
    Vector4d mul(Matrix4fc mat, Vector4d dest);

    /**
     * Multiply the given matrix <code>mat</code> with this Vector4d, perform perspective division
     * and store the result in <code>dest</code>.
     * 
     * @param mat
     *          the matrix to multiply this vector by
     * @param dest
     *          will hold the result
     * @return dest
     */
    Vector4d mulProject(Matrix4dc mat, Vector4d dest);

    /**
     * Multiply this Vector4d by the given scalar value and store the result in <code>dest</code>.
     * 
     * @param scalar
     *          the factor to multiply by
     * @param dest
     *          will hold the result
     * @return dest
     */
    Vector4d mul(double scalar, Vector4d dest);

    /**
     * Divide this Vector4d by the given scalar value and store the result in <code>dest</code>.
     * 
     * @param scalar
     *          the factor to divide by
     * @param dest
     *          will hold the result
     * @return dest
     */
    Vector4d div(double scalar, Vector4d dest);

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
    Vector4d rotate(Quaterniondc quat, Vector4d dest);

    /**
     * Return the length squared of this vector.
     * 
     * @return the length squared
     */
    double lengthSquared();

    /**
     * Return the length of this vector.
     * 
     * @return the length
     */
    double length();

    /**
     * Normalizes this vector and store the result in <code>dest</code>.
     * 
     * @param dest
     *          will hold the result
     * @return dest
     */
    Vector4d normalize(Vector4d dest);

    /**
     * Normalize this vector by computing only the norm of <tt>(x, y, z)</tt> and store the result in <code>dest</code>.
     * 
     * @param dest
     *          will hold the result
     * @return dest
     */
    Vector4d normalize3(Vector4d dest);

    /**
     * Return the distance between <code>this</code> vector and <code>v</code>.
     * 
     * @param v
     *          the other vector
     * @return the euclidean distance
     */
    double distance(Vector4dc v);

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
    double distance(double x, double y, double z, double w);

    /**
     * Compute the dot product (inner product) of this vector and <code>v</code>.
     * 
     * @param v
     *          the other vector
     * @return the dot product
     */
    double dot(Vector4dc v);

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
    double dot(double x, double y, double z, double w);

    /**
     * Return the cosine of the angle between this vector and the supplied vector.
     * <p>
     * Use this instead of <code>Math.cos(angle(v))</code>.
     * 
     * @see #angle(Vector4dc)
     * 
     * @param v
     *          the other vector
     * @return the cosine of the angle
     */
    double angleCos(Vector4dc v);

    /**
     * Return the angle between this vector and the supplied vector.
     * 
     * @see #angleCos(Vector4dc)
     * 
     * @param v
     *          the other vector
     * @return the angle, in radians
     */
    double angle(Vector4dc v);

    /**
     * Negate this vector and store the result in <code>dest</code>.
     * 
     * @param dest
     *          will hold the result
     * @return dest
     */
    Vector4d negate(Vector4d dest);

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
    Vector4d smoothStep(Vector4dc v, double t, Vector4d dest);

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
    Vector4d hermite(Vector4dc t0, Vector4dc v1, Vector4dc t1, double t, Vector4d dest);

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
    Vector4d lerp(Vector4dc other, double t, Vector4d dest);

}