/*
 * (C) Copyright 2016-2017 JOML

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
package org.joml.api.vector;

import org.joml.api.matrix.IMatrix4d;
import org.joml.api.matrix.IMatrix4f;
import org.joml.api.matrix.IMatrix4x3d;
import org.joml.api.matrix.IMatrix4x3f;
import org.joml.api.quaternion.IQuaterniond;

//#ifdef __HAS_NIO__

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
//#endif

/**
 * Interface to a read-only view of a 4-dimensional vector of double-precision floats.
 *
 * @author Kai Burjack
 */
public interface IVector4d {

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

    //#ifdef __HAS_NIO__

    /**
     * Store this vector into the supplied {@link ByteBuffer} at the current buffer {@link ByteBuffer#position()
     * position}.
     * <p>
     * This method will not increment the position of the given ByteBuffer.
     * <p>
     * In order to specify the offset into the ByteBuffer at which the vector is stored, use {@link #get(int,
     * ByteBuffer)}, taking the absolute position as parameter.
     *
     * @param buffer will receive the values of this vector in <tt>x, y, z, w</tt> order
     * @return the passed in buffer
     *
     * @see #get(int, ByteBuffer)
     */
    ByteBuffer get(ByteBuffer buffer);

    /**
     * Store this vector into the supplied {@link ByteBuffer} starting at the specified absolute buffer position/index.
     * <p>
     * This method will not increment the position of the given ByteBuffer.
     *
     * @param index  the absolute position into the ByteBuffer
     * @param buffer will receive the values of this vector in <tt>x, y, z, w</tt> order
     * @return the passed in buffer
     */
    ByteBuffer get(int index, ByteBuffer buffer);

    /**
     * Store this vector into the supplied {@link DoubleBuffer} at the current buffer {@link DoubleBuffer#position()
     * position}.
     * <p>
     * This method will not increment the position of the given DoubleBuffer.
     * <p>
     * In order to specify the offset into the DoubleBuffer at which the vector is stored, use {@link #get(int,
     * DoubleBuffer)}, taking the absolute position as parameter.
     *
     * @param buffer will receive the values of this vector in <tt>x, y, z, w</tt> order
     * @return the passed in buffer
     *
     * @see #get(int, DoubleBuffer)
     */
    DoubleBuffer get(DoubleBuffer buffer);

    /**
     * Store this vector into the supplied {@link DoubleBuffer} starting at the specified absolute buffer
     * position/index.
     * <p>
     * This method will not increment the position of the given DoubleBuffer.
     *
     * @param index  the absolute position into the DoubleBuffer
     * @param buffer will receive the values of this vector in <tt>x, y, z, w</tt> order
     * @return the passed in buffer
     */
    DoubleBuffer get(int index, DoubleBuffer buffer);
    //#endif

    /**
     * Subtract <tt>(x, y, z, w)</tt> from this and store the result in <code>dest</code>.
     *
     * @param x    the x component to subtract
     * @param y    the y component to subtract
     * @param z    the z component to subtract
     * @param w    the w component to subtract
     * @param dest will hold the result
     * @return dest
     */
    Vector4dc sub(double x, double y, double z, double w, Vector4dc dest);

    /**
     * Add <tt>(x, y, z, w)</tt> to this and store the result in <code>dest</code>.
     *
     * @param x    the x component to subtract
     * @param y    the y component to subtract
     * @param z    the z component to subtract
     * @param w    the w component to subtract
     * @param dest will hold the result
     * @return dest
     */
    Vector4dc add(double x, double y, double z, double w, Vector4dc dest);

    /**
     * Add the component-wise multiplication of <code>a * b</code> to this vector and store the result in
     * <code>dest</code>.
     *
     * @param a    the first multiplicand
     * @param b    the second multiplicand
     * @param dest will hold the result
     * @return dest
     */
    Vector4dc fma(IVector4d a, IVector4d b, Vector4dc dest);

    /**
     * Add the component-wise multiplication of <code>a * b</code> to this vector and store the result in
     * <code>dest</code>.
     *
     * @param a    the first multiplicand
     * @param b    the second multiplicand
     * @param dest will hold the result
     * @return dest
     */
    Vector4dc fma(double a, IVector4d b, Vector4dc dest);

    /**
     * Multiply this {@link Vector4dc} component-wise by the given {@link IVector4d} and store the result in
     * <code>dest</code>.
     *
     * @param v    the vector to multiply this by
     * @param dest will hold the result
     * @return dest
     */
    Vector4dc mul(IVector4d v, Vector4dc dest);

    /**
     * Divide this {@link Vector4dc} component-wise by the given {@link IVector4d} and store the result in
     * <code>dest</code>.
     *
     * @param v    the vector to divide this by
     * @param dest will hold the result
     * @return dest
     */
    Vector4dc div(IVector4d v, Vector4dc dest);

    /**
     * Multiply the given matrix mat with this {@link Vector4dc} and store the result in <code>dest</code>.
     *
     * @param mat  the matrix to multiply <code>this</code> by
     * @param dest will hold the result
     * @return dest
     */
    Vector4dc mul(IMatrix4d mat, Vector4dc dest);

    /**
     * Multiply the given matrix mat with this Vector4dc and store the result in <code>dest</code>.
     *
     * @param mat  the matrix to multiply the vector with
     * @param dest the destination vector to hold the result
     * @return dest
     */
    Vector4dc mul(IMatrix4x3d mat, Vector4dc dest);

    /**
     * Multiply the given matrix mat with this Vector4dc and store the result in <code>dest</code>.
     *
     * @param mat  the matrix to multiply the vector with
     * @param dest the destination vector to hold the result
     * @return dest
     */
    Vector4dc mul(IMatrix4x3f mat, Vector4dc dest);

    /**
     * Multiply the given matrix mat with this Vector4dc and store the result in <code>dest</code>.
     *
     * @param mat  the matrix to multiply <code>this</code> by
     * @param dest will hold the result
     * @return dest
     */
    Vector4dc mul(IMatrix4f mat, Vector4dc dest);

    /**
     * Multiply the given matrix <code>mat</code> with this Vector4dc, perform perspective division and store the result
     * in <code>dest</code>.
     *
     * @param mat  the matrix to multiply this vector by
     * @param dest will hold the result
     * @return dest
     */
    Vector4dc mulProject(IMatrix4d mat, Vector4dc dest);

    /**
     * Multiply this Vector4dc by the given scalar value and store the result in <code>dest</code>.
     *
     * @param scalar the factor to multiply by
     * @param dest   will hold the result
     * @return dest
     */
    Vector4dc mul(double scalar, Vector4dc dest);

    /**
     * Divide this Vector4dc by the given scalar value and store the result in <code>dest</code>.
     *
     * @param scalar the factor to divide by
     * @param dest   will hold the result
     * @return dest
     */
    Vector4dc div(double scalar, Vector4dc dest);

    /**
     * Transform this vector by the given quaternion <code>quat</code> and store the result in <code>dest</code>.
     *
     * @param quat the quaternion to transform this vector
     * @param dest will hold the result
     * @return dest
     *
     * @see IQuaterniond#transform(Vector4dc)
     */
    Vector4dc rotate(IQuaterniond quat, Vector4dc dest);

    /**
     * Rotate this vector the specified radians around the given rotation axis and store the result into
     * <code>dest</code>.
     *
     * @param angle the angle in radians
     * @param aX    the x component of the rotation axis
     * @param aY    the y component of the rotation axis
     * @param aZ    the z component of the rotation axis
     * @param dest  will hold the result
     * @return dest
     */
    Vector4dc rotateAxis(double angle, double aX, double aY, double aZ, Vector4dc dest);

    /**
     * Rotate this vector the specified radians around the X axis and store the result into <code>dest</code>.
     *
     * @param angle the angle in radians
     * @param dest  will hold the result
     * @return dest
     */
    Vector4dc rotateX(double angle, Vector4dc dest);

    /**
     * Rotate this vector the specified radians around the Y axis and store the result into <code>dest</code>.
     *
     * @param angle the angle in radians
     * @param dest  will hold the result
     * @return dest
     */
    Vector4dc rotateY(double angle, Vector4dc dest);

    /**
     * Rotate this vector the specified radians around the Z axis and store the result into <code>dest</code>.
     *
     * @param angle the angle in radians
     * @param dest  will hold the result
     * @return dest
     */
    Vector4dc rotateZ(double angle, Vector4dc dest);

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
     * @param dest will hold the result
     * @return dest
     */
    Vector4dc normalize(Vector4dc dest);

    /**
     * Scale this vector to have the given length and store the result in <code>dest</code>.
     *
     * @param length the desired length
     * @param dest   will hold the result
     * @return dest
     */
    Vector4dc normalize(double length, Vector4dc dest);

    /**
     * Normalize this vector by computing only the norm of <tt>(x, y, z)</tt> and store the result in
     * <code>dest</code>.
     *
     * @param dest will hold the result
     * @return dest
     */
    Vector4dc normalize3(Vector4dc dest);

    /**
     * Return the distance between <code>this</code> vector and <code>v</code>.
     *
     * @param v the other vector
     * @return the euclidean distance
     */
    double distance(IVector4d v);

    /**
     * Return the distance between <code>this</code> vector and <tt>(x, y, z, w)</tt>.
     *
     * @param x the x component of the other vector
     * @param y the y component of the other vector
     * @param z the z component of the other vector
     * @param w the w component of the other vector
     * @return the euclidean distance
     */
    double distance(double x, double y, double z, double w);

    /**
     * Compute the dot product (inner product) of this vector and <code>v</code>.
     *
     * @param v the other vector
     * @return the dot product
     */
    double dot(IVector4d v);

    /**
     * Compute the dot product (inner product) of this vector and <tt>(x, y, z, w)</tt>.
     *
     * @param x the x component of the other vector
     * @param y the y component of the other vector
     * @param z the z component of the other vector
     * @param w the w component of the other vector
     * @return the dot product
     */
    double dot(double x, double y, double z, double w);

    /**
     * Return the cosine of the angle between this vector and the supplied vector.
     * <p>
     * Use this instead of <code>Math.cos(angle(v))</code>.
     *
     * @param v the other vector
     * @return the cosine of the angle
     *
     * @see #angle(IVector4d)
     */
    double angleCos(IVector4d v);

    /**
     * Return the angle between this vector and the supplied vector.
     *
     * @param v the other vector
     * @return the angle, in radians
     *
     * @see #angleCos(IVector4d)
     */
    double angle(IVector4d v);

    /**
     * Negate this vector and store the result in <code>dest</code>.
     *
     * @param dest will hold the result
     * @return dest
     */
    Vector4dc negate(Vector4dc dest);

    /**
     * Compute a smooth-step (i.e. hermite with zero tangents) interpolation between <code>this</code> vector and the
     * given vector <code>v</code> and store the result in <code>dest</code>.
     *
     * @param v    the other vector
     * @param t    the interpolation factor, within <tt>[0..1]</tt>
     * @param dest will hold the result
     * @return dest
     */
    Vector4dc smoothStep(IVector4d v, double t, Vector4dc dest);

    /**
     * Compute a hermite interpolation between <code>this</code> vector and its associated tangent <code>t0</code> and
     * the given vector <code>v</code> with its tangent <code>t1</code> and store the result in <code>dest</code>.
     *
     * @param t0   the tangent of <code>this</code> vector
     * @param v1   the other vector
     * @param t1   the tangent of the other vector
     * @param t    the interpolation factor, within <tt>[0..1]</tt>
     * @param dest will hold the result
     * @return dest
     */
    Vector4dc hermite(IVector4d t0, IVector4d v1, IVector4d t1, double t, Vector4dc dest);

    /**
     * Linearly interpolate <code>this</code> and <code>other</code> using the given interpolation factor <code>t</code>
     * and store the result in <code>dest</code>.
     * <p>
     * If <code>t</code> is <tt>0.0</tt> then the result is <code>this</code>. If the interpolation factor is
     * <code>1.0</code> then the result is <code>other</code>.
     *
     * @param other the other vector
     * @param t     the interpolation factor between 0.0 and 1.0
     * @param dest  will hold the result
     * @return dest
     */
    Vector4dc lerp(IVector4d other, double t, Vector4dc dest);

    /**
     * Get the value of the specified component of this vector.
     *
     * @param component the component, within <tt>[0..3]</tt>
     * @return the value
     */
    double get(int component);
}