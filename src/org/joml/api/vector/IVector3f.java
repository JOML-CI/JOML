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

import org.joml.api.matrix.*;
import org.joml.api.quaternion.IQuaternionf;
import org.joml.api.quaternion.Quaternionfc;

//#ifdef __HAS_NIO__
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
//#endif

/**
 * Interface to a read-only view of a 3-dimensional vector of single-precision floats.
 *
 * @author Kai Burjack
 */
public interface IVector3f {

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
     * @param buffer will receive the values of this vector in <tt>x, y, z</tt> order
     * @return the passed in buffer
     *
     * @see #get(int, FloatBuffer)
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
     * @param buffer will receive the values of this vector in <tt>x, y, z</tt> order
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
     * @param buffer will receive the values of this vector in <tt>x, y, z</tt> order
     * @return the passed in buffer
     *
     * @see #get(int, ByteBuffer)
     * @see #get(int, ByteBuffer)
     */
    ByteBuffer get(ByteBuffer buffer);

    /**
     * Store this vector into the supplied {@link ByteBuffer} starting at the specified absolute buffer position/index.
     * <p>
     * This method will not increment the position of the given ByteBuffer.
     *
     * @param index  the absolute position into the ByteBuffer
     * @param buffer will receive the values of this vector in <tt>x, y, z</tt> order
     * @return the passed in buffer
     */
    ByteBuffer get(int index, ByteBuffer buffer);
    //#endif

    /**
     * Subtract the supplied vector from this one and store the result in <code>dest</code>.
     *
     * @param v    the vector to subtract
     * @param dest will hold the result
     * @return dest
     */
    Vector3fc sub(IVector3f v, Vector3fc dest);

    /**
     * Decrement the components of this vector by the given values and store the result in <code>dest</code>.
     *
     * @param x    the x component to subtract
     * @param y    the y component to subtract
     * @param z    the z component to subtract
     * @param dest will hold the result
     * @return dest
     */
    Vector3fc sub(float x, float y, float z, Vector3fc dest);

    /**
     * Add the supplied vector to this one and store the result in <code>dest</code>.
     *
     * @param v    the vector to add
     * @param dest will hold the result
     * @return dest
     */
    Vector3fc add(IVector3f v, Vector3fc dest);

    /**
     * Increment the components of this vector by the given values and store the result in <code>dest</code>.
     *
     * @param x    the x component to add
     * @param y    the y component to add
     * @param z    the z component to add
     * @param dest will hold the result
     * @return dest
     */
    Vector3fc add(float x, float y, float z, Vector3fc dest);

    /**
     * Add the component-wise multiplication of <code>a * b</code> to this vector and store the result in
     * <code>dest</code>.
     *
     * @param a    the first multiplicand
     * @param b    the second multiplicand
     * @param dest will hold the result
     * @return dest
     */
    Vector3fc fma(IVector3f a, IVector3f b, Vector3fc dest);

    /**
     * Add the component-wise multiplication of <code>a * b</code> to this vector and store the result in
     * <code>dest</code>.
     *
     * @param a    the first multiplicand
     * @param b    the second multiplicand
     * @param dest will hold the result
     * @return dest
     */
    Vector3fc fma(float a, IVector3f b, Vector3fc dest);

    /**
     * Multiply this Vector3fc component-wise by another Vector3fc and store the result in <code>dest</code>.
     *
     * @param v    the vector to multiply by
     * @param dest will hold the result
     * @return dest
     */
    Vector3fc mul(IVector3f v, Vector3fc dest);

    /**
     * Divide this Vector3fc component-wise by another Vector3fc and store the result in <code>dest</code>.
     *
     * @param v    the vector to divide by
     * @param dest will hold the result
     * @return dest
     */
    Vector3fc div(IVector3f v, Vector3fc dest);

    /**
     * Multiply the given matrix <code>mat</code> with this Vector3fc, perform perspective division and store the result
     * in <code>dest</code>.
     * <p>
     * This method uses <tt>w=1.0</tt> as the fourth vector component.
     *
     * @param mat  the matrix to multiply this vector by
     * @param dest will hold the result
     * @return dest
     */
    Vector3fc mulProject(IMatrix4f mat, Vector3fc dest);

    /**
     * Multiply the given matrix with this Vector3fc and store the result in <code>dest</code>.
     *
     * @param mat  the matrix
     * @param dest will hold the result
     * @return dest
     */
    Vector3fc mul(IMatrix3f mat, Vector3fc dest);

    /**
     * Multiply the given matrix with this Vector3fc and store the result in <code>dest</code>.
     *
     * @param mat  the matrix
     * @param dest will hold the result
     * @return dest
     */
    Vector3fc mul(IMatrix3d mat, Vector3fc dest);

    /**
     * Multiply the given matrix <code>mat</code> with <code>this</code> by assuming a third row in the matrix of
     * <tt>(0, 0, 1)</tt> and store the result in <code>dest</code>.
     *
     * @param mat  the matrix to multiply this vector by
     * @param dest will hold the result
     * @return dest
     */
    Vector3fc mul(IMatrix3x2f mat, Vector3fc dest);

    /**
     * Multiply the transpose of the given matrix with this Vector3fc and store the result in <code>dest</code>.
     *
     * @param mat  the matrix
     * @param dest will hold the result
     * @return dest
     */
    Vector3fc mulTranspose(IMatrix3f mat, Vector3fc dest);

    /**
     * Multiply the given 4x4 matrix <code>mat</code> with <code>this</code> and store the result in <code>dest</code>.
     * <p>
     * This method assumes the <tt>w</tt> component of <code>this</code> to be <tt>1.0</tt>.
     *
     * @param mat  the matrix to multiply this vector by
     * @param dest will hold the result
     * @return dest
     */
    Vector3fc mulPosition(IMatrix4f mat, Vector3fc dest);

    /**
     * Multiply the given 4x3 matrix <code>mat</code> with <code>this</code> and store the result in <code>dest</code>.
     * <p>
     * This method assumes the <tt>w</tt> component of <code>this</code> to be <tt>1.0</tt>.
     *
     * @param mat  the matrix to multiply this vector by
     * @param dest will hold the result
     * @return dest
     */
    Vector3fc mulPosition(IMatrix4x3f mat, Vector3fc dest);

    /**
     * Multiply the transpose of the given 4x4 matrix <code>mat</code> with <code>this</code> and store the result in
     * <code>dest</code>.
     * <p>
     * This method assumes the <tt>w</tt> component of <code>this</code> to be <tt>1.0</tt>.
     *
     * @param mat  the matrix whose transpose to multiply this vector by
     * @param dest will hold the result
     * @return dest
     */
    Vector3fc mulTransposePosition(IMatrix4f mat, Vector3fc dest);

    /**
     * Multiply the given 4x4 matrix <code>mat</code> with <code>this</code>, store the result in <code>dest</code> and
     * return the <i>w</i> component of the resulting 4D vector.
     * <p>
     * This method assumes the <tt>w</tt> component of <code>this</code> to be <tt>1.0</tt>.
     *
     * @param mat  the matrix to multiply this vector by
     * @param dest will hold the <tt>(x, y, z)</tt> components of the resulting vector
     * @return the <i>w</i> component of the resulting 4D vector after multiplication
     */
    float mulPositionW(IMatrix4f mat, Vector3fc dest);

    /**
     * Multiply the given 4x4 matrix <code>mat</code> with <code>this</code> and store the result in <code>dest</code>.
     * <p>
     * This method assumes the <tt>w</tt> component of <code>this</code> to be <tt>0.0</tt>.
     *
     * @param mat  the matrix to multiply this vector by
     * @param dest will hold the result
     * @return dest
     */
    Vector3fc mulDirection(IMatrix4f mat, Vector3fc dest);

    /**
     * Multiply the given 4x3 matrix <code>mat</code> with <code>this</code> and store the result in <code>dest</code>.
     * <p>
     * This method assumes the <tt>w</tt> component of <code>this</code> to be <tt>0.0</tt>.
     *
     * @param mat  the matrix to multiply this vector by
     * @param dest will hold the result
     * @return dest
     */
    Vector3fc mulDirection(IMatrix4x3f mat, Vector3fc dest);

    /**
     * Multiply the transpose of the given 4x4 matrix <code>mat</code> with <code>this</code> and store the result in
     * <code>dest</code>.
     * <p>
     * This method assumes the <tt>w</tt> component of <code>this</code> to be <tt>0.0</tt>.
     *
     * @param mat  the matrix whose transpose to multiply this vector by
     * @param dest will hold the result
     * @return dest
     */
    Vector3fc mulTransposeDirection(IMatrix4f mat, Vector3fc dest);

    /**
     * Multiply all components of this {@link Vector3fc} by the given scalar value and store the result in
     * <code>dest</code>.
     *
     * @param scalar the scalar to multiply this vector by
     * @param dest   will hold the result
     * @return dest
     */
    Vector3fc mul(float scalar, Vector3fc dest);

    /**
     * Multiply the components of this Vector3fc by the given scalar values and store the result in <code>dest</code>.
     *
     * @param x    the x component to multiply this vector by
     * @param y    the y component to multiply this vector by
     * @param z    the z component to multiply this vector by
     * @param dest will hold the result
     * @return dest
     */
    Vector3fc mul(float x, float y, float z, Vector3fc dest);

    /**
     * Divide all components of this {@link Vector3fc} by the given scalar value and store the result in
     * <code>dest</code>.
     *
     * @param scalar the scalar to divide by
     * @param dest   will hold the result
     * @return dest
     */
    Vector3fc div(float scalar, Vector3fc dest);

    /**
     * Divide the components of this Vector3fc by the given scalar values and store the result in <code>dest</code>.
     *
     * @param x    the x component to divide this vector by
     * @param y    the y component to divide this vector by
     * @param z    the z component to divide this vector by
     * @param dest will hold the result
     * @return dest
     */
    Vector3fc div(float x, float y, float z, Vector3fc dest);

    /**
     * Rotate this vector by the given quaternion <code>quat</code> and store the result in <code>dest</code>.
     *
     * @param quat the quaternion to rotate this vector
     * @param dest will hold the result
     * @return dest
     *
     * @see IQuaternionf#transform(Vector3fc)
     */
    Vector3fc rotate(IQuaternionf quat, Vector3fc dest);

    /**
     * Compute the quaternion representing a rotation of <code>this</code> vector to point along <code>toDir</code> and
     * store the result in <code>dest</code>.
     * <p>
     * Because there can be multiple possible rotations, this method chooses the one with the shortest arc.
     *
     * @param toDir the destination direction
     * @param dest  will hold the result
     * @return dest
     *
     * @see Quaternionfc#rotationTo(IVector3f, IVector3f)
     */
    Quaternionfc rotationTo(IVector3f toDir, Quaternionfc dest);

    /**
     * Compute the quaternion representing a rotation of <code>this</code> vector to point along <tt>(toDirX, toDirY,
     * toDirZ)</tt> and store the result in <code>dest</code>.
     * <p>
     * Because there can be multiple possible rotations, this method chooses the one with the shortest arc.
     *
     * @param toDirX the x coordinate of the destination direction
     * @param toDirY the y coordinate of the destination direction
     * @param toDirZ the z coordinate of the destination direction
     * @param dest   will hold the result
     * @return dest
     *
     * @see Quaternionfc#rotationTo(float, float, float, float, float, float)
     */
    Quaternionfc rotationTo(float toDirX, float toDirY, float toDirZ, Quaternionfc dest);

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
    Vector3fc rotateAxis(float angle, float aX, float aY, float aZ, Vector3fc dest);

    /**
     * Rotate this vector the specified radians around the X axis and store the result into <code>dest</code>.
     *
     * @param angle the angle in radians
     * @param dest  will hold the result
     * @return dest
     */
    Vector3fc rotateX(float angle, Vector3fc dest);

    /**
     * Rotate this vector the specified radians around the Y axis and store the result into <code>dest</code>.
     *
     * @param angle the angle in radians
     * @param dest  will hold the result
     * @return dest
     */
    Vector3fc rotateY(float angle, Vector3fc dest);

    /**
     * Rotate this vector the specified radians around the Z axis and store the result into <code>dest</code>.
     *
     * @param angle the angle in radians
     * @param dest  will hold the result
     * @return dest
     */
    Vector3fc rotateZ(float angle, Vector3fc dest);

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
     * Normalize this vector and store the result in <code>dest</code>.
     *
     * @param dest will hold the result
     * @return dest
     */
    Vector3fc normalize(Vector3fc dest);

    /**
     * Scale this vector to have the given length and store the result in <code>dest</code>.
     *
     * @param length the desired length
     * @param dest   will hold the result
     * @return dest
     */
    Vector3fc normalize(float length, Vector3fc dest);

    /**
     * Compute the cross product of this vector and <code>v</code> and store the result in <code>dest</code>.
     *
     * @param v    the other vector
     * @param dest will hold the result
     * @return dest
     */
    Vector3fc cross(IVector3f v, Vector3fc dest);

    /**
     * Compute the cross product of this vector and <tt>(x, y, z)</tt> and store the result in <code>dest</code>.
     *
     * @param x    the x component of the other vector
     * @param y    the y component of the other vector
     * @param z    the z component of the other vector
     * @param dest will hold the result
     * @return dest
     */
    Vector3fc cross(float x, float y, float z, Vector3fc dest);

    /**
     * Return the distance between this Vector and <code>v</code>.
     *
     * @param v the other vector
     * @return the distance
     */
    float distance(IVector3f v);

    /**
     * Return the distance between <code>this</code> vector and <tt>(x, y, z)</tt>.
     *
     * @param x the x component of the other vector
     * @param y the y component of the other vector
     * @param z the z component of the other vector
     * @return the euclidean distance
     */
    float distance(float x, float y, float z);

    /**
     * Return the square of the distance between this vector and <code>v</code>.
     *
     * @param v the other vector
     * @return the squared of the distance
     */
    float distanceSquared(IVector3f v);

    /**
     * Return the square of the distance between <code>this</code> vector and <tt>(x, y, z)</tt>.
     *
     * @param x the x component of the other vector
     * @param y the y component of the other vector
     * @param z the z component of the other vector
     * @return the square of the distance
     */
    float distanceSquared(float x, float y, float z);

    /**
     * Return the dot product of this vector and the supplied vector.
     *
     * @param v the other vector
     * @return the dot product
     */
    float dot(IVector3f v);

    /**
     * Return the dot product of this vector and the vector <tt>(x, y, z)</tt>.
     *
     * @param x the x component of the other vector
     * @param y the y component of the other vector
     * @param z the z component of the other vector
     * @return the dot product
     */
    float dot(float x, float y, float z);

    /**
     * Return the cosine of the angle between this vector and the supplied vector. Use this instead of
     * Math.cos(this.angle(v)).
     *
     * @param v the other vector
     * @return the cosine of the angle
     *
     * @see #angle(IVector3f)
     */
    float angleCos(IVector3f v);

    /**
     * Return the angle between this vector and the supplied vector.
     *
     * @param v the other vector
     * @return the angle, in radians
     *
     * @see #angleCos(IVector3f)
     */
    float angle(IVector3f v);

    /**
     * Negate this vector and store the result in <code>dest</code>.
     *
     * @param dest will hold the result
     * @return dest
     */
    Vector3fc negate(Vector3fc dest);

    /**
     * Compute the absolute values of the individual components of <code>this</code> and store the result in
     * <code>dest</code>.
     *
     * @param dest will hold the result
     * @return dest
     */
    Vector3fc absolute(Vector3fc dest);

    /**
     * Reflect this vector about the given <code>normal</code> vector and store the result in <code>dest</code>.
     *
     * @param normal the vector to reflect about
     * @param dest   will hold the result
     * @return dest
     */
    Vector3fc reflect(IVector3f normal, Vector3fc dest);

    /**
     * Reflect this vector about the given normal vector and store the result in <code>dest</code>.
     *
     * @param x    the x component of the normal
     * @param y    the y component of the normal
     * @param z    the z component of the normal
     * @param dest will hold the result
     * @return dest
     */
    Vector3fc reflect(float x, float y, float z, Vector3fc dest);

    /**
     * Compute the half vector between this and the other vector and store the result in <code>dest</code>.
     *
     * @param other the other vector
     * @param dest  will hold the result
     * @return dest
     */
    Vector3fc half(IVector3f other, Vector3fc dest);

    /**
     * Compute the half vector between this and the vector <tt>(x, y, z)</tt> and store the result in
     * <code>dest</code>.
     *
     * @param x    the x component of the other vector
     * @param y    the y component of the other vector
     * @param z    the z component of the other vector
     * @param dest will hold the result
     * @return dest
     */
    Vector3fc half(float x, float y, float z, Vector3fc dest);

    /**
     * Compute a smooth-step (i.e. hermite with zero tangents) interpolation between <code>this</code> vector and the
     * given vector <code>v</code> and store the result in <code>dest</code>.
     *
     * @param v    the other vector
     * @param t    the interpolation factor, within <tt>[0..1]</tt>
     * @param dest will hold the result
     * @return dest
     */
    Vector3fc smoothStep(IVector3f v, float t, Vector3fc dest);

    /**
     * Compute a hermite interpolation between <code>this</code> vector with its associated tangent <code>t0</code> and
     * the given vector <code>v</code> with its tangent <code>t1</code> and store the result in <code>dest</code>.
     *
     * @param t0   the tangent of <code>this</code> vector
     * @param v1   the other vector
     * @param t1   the tangent of the other vector
     * @param t    the interpolation factor, within <tt>[0..1]</tt>
     * @param dest will hold the result
     * @return dest
     */
    Vector3fc hermite(IVector3f t0, IVector3f v1, IVector3f t1, float t, Vector3fc dest);

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
    Vector3fc lerp(IVector3f other, float t, Vector3fc dest);

    /**
     * Get the value of the specified component of this vector.
     *
     * @param component the component, within <tt>[0..2]</tt>
     * @return the value
     */
    float get(int component);

    /**
     * Determine the component with the biggest absolute value.
     *
     * @return the component index, within <tt>[0..2]</tt>
     */
    int maxComponent();

    /**
     * Determine the component with the smallest (towards zero) absolute value.
     *
     * @return the component index, within <tt>[0..2]</tt>
     */
    int minComponent();

    /**
     * Transform <code>this</code> vector so that it is orthogonal to the given vector <code>v</code>, normalize the
     * result and store it into <code>dest</code>.
     * <p>
     * Reference: <a href="https://en.wikipedia.org/wiki/Gram%E2%80%93Schmidt_process">Gram–Schmidt process</a>
     *
     * @param v    the reference vector which the result should be orthogonal to
     * @param dest will hold the result
     * @return dest
     */
    Vector3fc orthogonalize(IVector3f v, Vector3fc dest);

    /**
     * Transform <code>this</code> vector so that it is orthogonal to the given unit vector <code>v</code>, normalize
     * the result and store it into <code>dest</code>.
     * <p>
     * The vector <code>v</code> is assumed to be a {@link #normalize(Vector3fc) unit} vector.
     * <p>
     * Reference: <a href="https://en.wikipedia.org/wiki/Gram%E2%80%93Schmidt_process">Gram–Schmidt process</a>
     *
     * @param v    the reference unit vector which the result should be orthogonal to
     * @param dest will hold the result
     * @return dest
     */
    Vector3fc orthogonalizeUnit(IVector3f v, Vector3fc dest);
}