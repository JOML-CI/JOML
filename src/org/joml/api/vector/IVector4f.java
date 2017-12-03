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

import org.joml.api.matrix.IMatrix4f;
import org.joml.api.matrix.IMatrix4x3f;
import org.joml.api.quaternion.IQuaternionf;

//#ifdef __HAS_NIO__
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
//#endif

/**
 * Interface to a read-only view of a 4-dimensional vector of single-precision floats.
 *
 * @author Kai Burjack
 */
public interface IVector4f {

    /**
     * @return the value of the x component
     */
    float x();

    /**
     * @return the value of the y component
     */
    float y();

    /**
     * @return the value of the z component
     */
    float z();

    /**
     * @return the value of the w component
     */
    float w();

    //#ifdef __HAS_NIO__

    /**
     * Store this vector into the supplied {@link FloatBuffer} at the current buffer {@link FloatBuffer#position()
     * position}.
     * <p>
     * This method will not increment the position of the given FloatBuffer.
     * <p>
     * In order to specify the offset into the FloatBuffer at which the vector is stored, use {@link #get(int,
     * FloatBuffer)}, taking the absolute position as parameter.
     *
     * @param buffer will receive the values of this vector in <tt>x, y, z, w</tt> order
     * @return the passed in buffer
     *
     * @see #get(int, FloatBuffer)
     */
    FloatBuffer get(FloatBuffer buffer);

    /**
     * Store this vector into the supplied {@link FloatBuffer} starting at the specified absolute buffer
     * position/index.
     * <p>
     * This method will not increment the position of the given FloatBuffer.
     *
     * @param index  the absolute position into the FloatBuffer
     * @param buffer will receive the values of this vector in <tt>x, y, z, w</tt> order
     * @return the passed in buffer
     */
    FloatBuffer get(int index, FloatBuffer buffer);

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
    //#endif

    /**
     * Subtract the supplied vector from this one and store the result in <code>dest</code>.
     *
     * @param v    the vector to subtract from <code>this</code>
     * @param dest will hold the result
     * @return dest
     */
    Vector4fc sub(IVector4f v, Vector4fc dest);

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
    Vector4fc sub(float x, float y, float z, float w, Vector4fc dest);

    /**
     * Add the supplied vector to this one and store the result in <code>dest</code>.
     *
     * @param v    the vector to add
     * @param dest will hold the result
     * @return dest
     */
    Vector4fc add(IVector4f v, Vector4fc dest);

    /**
     * Increment the components of this vector by the given values and store the result in <code>dest</code>.
     *
     * @param x    the x component to add
     * @param y    the y component to add
     * @param z    the z component to add
     * @param w    the w component to add
     * @param dest will hold the result
     * @return dest
     */
    Vector4fc add(float x, float y, float z, float w, Vector4fc dest);

    /**
     * Add the component-wise multiplication of <code>a * b</code> to this vector and store the result in
     * <code>dest</code>.
     *
     * @param a    the first multiplicand
     * @param b    the second multiplicand
     * @param dest will hold the result
     * @return dest
     */
    Vector4fc fma(IVector4f a, IVector4f b, Vector4fc dest);

    /**
     * Add the component-wise multiplication of <code>a * b</code> to this vector and store the result in
     * <code>dest</code>.
     *
     * @param a    the first multiplicand
     * @param b    the second multiplicand
     * @param dest will hold the result
     * @return dest
     */
    Vector4fc fma(float a, IVector4f b, Vector4fc dest);

    /**
     * Multiply this Vector4fc component-wise by another Vector4fc and store the result in <code>dest</code>.
     *
     * @param v    the other vector
     * @param dest will hold the result
     * @return dest
     */
    Vector4fc mul(IVector4f v, Vector4fc dest);

    /**
     * Divide this Vector4fc component-wise by another Vector4fc and store the result in <code>dest</code>.
     *
     * @param v    the vector to divide by
     * @param dest will hold the result
     * @return dest
     */
    Vector4fc div(IVector4f v, Vector4fc dest);

    /**
     * Multiply the given matrix mat with this Vector4fc and store the result in <code>dest</code>.
     *
     * @param mat  the matrix to multiply the vector with
     * @param dest the destination vector to hold the result
     * @return dest
     */
    Vector4fc mul(IMatrix4f mat, Vector4fc dest);

    /**
     * Multiply the given matrix mat with this Vector4fc and store the result in <code>dest</code>.
     *
     * @param mat  the matrix to multiply the vector with
     * @param dest the destination vector to hold the result
     * @return dest
     */
    Vector4fc mul(IMatrix4x3f mat, Vector4fc dest);

    /**
     * Multiply the given matrix <code>mat</code> with this Vector4fc, perform perspective division and store the result
     * in <code>dest</code>.
     *
     * @param mat  the matrix to multiply this vector by
     * @param dest will hold the result
     * @return dest
     */
    Vector4fc mulProject(IMatrix4f mat, Vector4fc dest);

    /**
     * Multiply all components of this {@link Vector4fc} by the given scalar value and store the result in
     * <code>dest</code>.
     *
     * @param scalar the scalar to multiply by
     * @param dest   will hold the result
     * @return dest
     */
    Vector4fc mul(float scalar, Vector4fc dest);

    /**
     * Multiply the components of this Vector4fc by the given scalar values and store the result in <code>dest</code>.
     *
     * @param x    the x component to multiply by
     * @param y    the y component to multiply by
     * @param z    the z component to multiply by
     * @param w    the w component to multiply by
     * @param dest will hold the result
     * @return dest
     */
    Vector4fc mul(float x, float y, float z, float w, Vector4fc dest);

    /**
     * Divide all components of this {@link Vector4fc} by the given scalar value and store the result in
     * <code>dest</code>.
     *
     * @param scalar the scalar to divide by
     * @param dest   will hold the result
     * @return dest
     */
    Vector4fc div(float scalar, Vector4fc dest);

    /**
     * Divide the components of this Vector4fc by the given scalar values and store the result in <code>dest</code>.
     *
     * @param x    the x component to divide by
     * @param y    the y component to divide by
     * @param z    the z component to divide by
     * @param w    the w component to divide by
     * @param dest will hold the result
     * @return dest
     */
    Vector4fc div(float x, float y, float z, float w, Vector4fc dest);

    /**
     * Rotate this vector by the given quaternion <code>quat</code> and store the result in <code>dest</code>.
     *
     * @param quat the quaternion to rotate this vector
     * @param dest will hold the result
     * @return dest
     *
     * @see IQuaternionf#transform(Vector4fc)
     */
    Vector4fc rotate(IQuaternionf quat, Vector4fc dest);

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
    Vector4fc rotateAxis(float angle, float aX, float aY, float aZ, Vector4fc dest);

    /**
     * Rotate this vector the specified radians around the X axis and store the result into <code>dest</code>.
     *
     * @param angle the angle in radians
     * @param dest  will hold the result
     * @return dest
     */
    Vector4fc rotateX(float angle, Vector4fc dest);

    /**
     * Rotate this vector the specified radians around the Y axis and store the result into <code>dest</code>.
     *
     * @param angle the angle in radians
     * @param dest  will hold the result
     * @return dest
     */
    Vector4fc rotateY(float angle, Vector4fc dest);

    /**
     * Rotate this vector the specified radians around the Z axis and store the result into <code>dest</code>.
     *
     * @param angle the angle in radians
     * @param dest  will hold the result
     * @return dest
     */
    Vector4fc rotateZ(float angle, Vector4fc dest);

    /**
     * Return the length squared of this vector.
     *
     * @return the length squared
     */
    float lengthSquared();

    /**
     * Return the length of this vector.
     *
     * @return the length
     */
    float length();

    /**
     * Normalizes this vector and store the result in <code>dest</code>.
     *
     * @param dest will hold the result
     * @return dest
     */
    Vector4fc normalize(Vector4fc dest);

    /**
     * Scale this vector to have the given length and store the result in <code>dest</code>.
     *
     * @param length the desired length
     * @param dest   will hold the result
     * @return dest
     */
    Vector4fc normalize(float length, Vector4fc dest);

    /**
     * Return the distance between <code>this</code> vector and <code>v</code>.
     *
     * @param v the other vector
     * @return the euclidean distance
     */
    float distance(IVector4f v);

    /**
     * Return the distance between <code>this</code> vector and <tt>(x, y, z, w)</tt>.
     *
     * @param x the x component of the other vector
     * @param y the y component of the other vector
     * @param z the z component of the other vector
     * @param w the w component of the other vector
     * @return the euclidean distance
     */
    float distance(float x, float y, float z, float w);

    /**
     * Compute the dot product (inner product) of this vector and <code>v</code> .
     *
     * @param v the other vector
     * @return the dot product
     */
    float dot(IVector4f v);

    /**
     * Compute the dot product (inner product) of this vector and <tt>(x, y, z, w)</tt>.
     *
     * @param x the x component of the other vector
     * @param y the y component of the other vector
     * @param z the z component of the other vector
     * @param w the w component of the other vector
     * @return the dot product
     */
    float dot(float x, float y, float z, float w);

    /**
     * Return the cosine of the angle between this vector and the supplied vector. Use this instead of
     * <code>Math.cos(angle(v))</code>.
     *
     * @param v the other vector
     * @return the cosine of the angle
     *
     * @see #angle(IVector4f)
     */
    float angleCos(IVector4f v);

    /**
     * Return the angle between this vector and the supplied vector.
     *
     * @param v the other vector
     * @return the angle, in radians
     *
     * @see #angleCos(IVector4f)
     */
    float angle(IVector4f v);

    /**
     * Negate this vector and store the result in <code>dest</code>.
     *
     * @param dest will hold the result
     * @return dest
     */
    Vector4fc negate(Vector4fc dest);

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
    Vector4fc lerp(IVector4f other, float t, Vector4fc dest);

    /**
     * Compute a smooth-step (i.e. hermite with zero tangents) interpolation between <code>this</code> vector and the
     * given vector <code>v</code> and store the result in <code>dest</code>.
     *
     * @param v    the other vector
     * @param t    the interpolation factor, within <tt>[0..1]</tt>
     * @param dest will hold the result
     * @return dest
     */
    Vector4fc smoothStep(IVector4f v, float t, Vector4fc dest);

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
    Vector4fc hermite(IVector4f t0, IVector4f v1, IVector4f t1, float t, Vector4fc dest);

    /**
     * Get the value of the specified component of this vector.
     *
     * @param component the component, within <tt>[0..3]</tt>
     * @return the value
     */
    float get(int component);
}