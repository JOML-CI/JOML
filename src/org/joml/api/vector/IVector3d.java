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
import org.joml.api.quaternion.IQuaterniond;
import org.joml.api.quaternion.Quaterniondc;

//#ifdef __HAS_NIO__
import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
//#endif

/**
 * Interface to a read-only view of a 3-dimensional vector of double-precision floats.
 *
 * @author Kai Burjack
 */
public interface IVector3d {

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
     * @param buffer will receive the values of this vector in <tt>x, y, z</tt> order
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
     * @param buffer will receive the values of this vector in <tt>x, y, z</tt> order
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
     * @param buffer will receive the values of this vector in <tt>x, y, z</tt> order
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
     * @param buffer will receive the values of this vector in <tt>x, y, z</tt> order
     * @return the passed in buffer
     */
    DoubleBuffer get(int index, DoubleBuffer buffer);
    //#endif

    /**
     * Subtract the supplied vector from this one and store the result in <code>dest</code>.
     *
     * @param v    the vector to subtract from <code>this</code>
     * @param dest will hold the result
     * @return dest
     */
    Vector3dc sub(IVector3d v, Vector3dc dest);

    /**
     * Subtract the supplied vector from this one and store the result in <code>dest</code>.
     *
     * @param v    the vector to subtract from <code>this</code>
     * @param dest will hold the result
     * @return dest
     */
    Vector3dc sub(IVector3f v, Vector3dc dest);

    /**
     * Subtract <tt>(x, y, z)</tt> from this vector and store the result in <code>dest</code>.
     *
     * @param x    the x component to subtract
     * @param y    the y component to subtract
     * @param z    the z component to subtract
     * @param dest will hold the result
     * @return dest
     */
    Vector3dc sub(double x, double y, double z, Vector3dc dest);

    /**
     * Add the supplied vector to this one and store the result in <code>dest</code>.
     *
     * @param v    the vector to add
     * @param dest will hold the result
     * @return dest
     */
    Vector3dc add(IVector3d v, Vector3dc dest);

    /**
     * Add the supplied vector to this one and store the result in <code>dest</code>.
     *
     * @param v    the vector to add
     * @param dest will hold the result
     * @return dest
     */
    Vector3dc add(IVector3f v, Vector3dc dest);

    /**
     * Increment the components of this vector by the given values and store the result in <code>dest</code>.
     *
     * @param x    the x component to add
     * @param y    the y component to add
     * @param z    the z component to add
     * @param dest will hold the result
     * @return dest
     */
    Vector3dc add(double x, double y, double z, Vector3dc dest);

    /**
     * Add the component-wise multiplication of <code>a * b</code> to this vector and store the result in
     * <code>dest</code>.
     *
     * @param a    the first multiplicand
     * @param b    the second multiplicand
     * @param dest will hold the result
     * @return dest
     */
    Vector3dc fma(IVector3d a, IVector3d b, Vector3dc dest);

    /**
     * Add the component-wise multiplication of <code>a * b</code> to this vector and store the result in
     * <code>dest</code>.
     *
     * @param a    the first multiplicand
     * @param b    the second multiplicand
     * @param dest will hold the result
     * @return dest
     */
    Vector3dc fma(double a, IVector3d b, Vector3dc dest);

    /**
     * Add the component-wise multiplication of <code>a * b</code> to this vector and store the result in
     * <code>dest</code>.
     *
     * @param a    the first multiplicand
     * @param b    the second multiplicand
     * @param dest will hold the result
     * @return dest
     */
    Vector3dc fma(IVector3d a, IVector3f b, Vector3dc dest);

    /**
     * Add the component-wise multiplication of <code>a * b</code> to this vector and store the result in
     * <code>dest</code>.
     *
     * @param a    the first multiplicand
     * @param b    the second multiplicand
     * @param dest will hold the result
     * @return dest
     */
    Vector3dc fma(double a, IVector3f b, Vector3dc dest);

    /**
     * Multiply this Vector3dc component-wise by another Vector3fc and store the result in <code>dest</code>.
     *
     * @param v    the vector to multiply by
     * @param dest will hold the result
     * @return dest
     */
    Vector3dc mul(IVector3f v, Vector3dc dest);

    /**
     * Multiply this by <code>v</code> component-wise and store the result into <code>dest</code>.
     *
     * @param v    the vector to multiply by
     * @param dest will hold the result
     * @return dest
     */
    Vector3dc mul(IVector3d v, Vector3dc dest);

    /**
     * Divide this Vector3dc component-wise by another Vector3fc and store the result in <code>dest</code>.
     *
     * @param v    the vector to divide by
     * @param dest will hold the result
     * @return dest
     */
    Vector3dc div(IVector3f v, Vector3dc dest);

    /**
     * Divide this by <code>v</code> component-wise and store the result into <code>dest</code>.
     *
     * @param v    the vector to divide by
     * @param dest will hold the result
     * @return dest
     */
    Vector3dc div(IVector3d v, Vector3dc dest);

    /**
     * Multiply the given matrix <code>mat</code> with this Vector3dc, perform perspective division and store the result
     * in <code>dest</code>.
     * <p>
     * This method uses <tt>w=1.0</tt> as the fourth vector component.
     *
     * @param mat  the matrix to multiply this vector by
     * @param dest will hold the result
     * @return dest
     */
    Vector3dc mulProject(IMatrix4d mat, Vector3dc dest);

    /**
     * Multiply the given matrix <code>mat</code> with this Vector3dc, perform perspective division and store the result
     * in <code>dest</code>.
     * <p>
     * This method uses <tt>w=1.0</tt> as the fourth vector component.
     *
     * @param mat  the matrix to multiply this vector by
     * @param dest will hold the result
     * @return dest
     */
    Vector3dc mulProject(IMatrix4f mat, Vector3dc dest);

    /**
     * Multiply the given matrix <code>mat</code> with <code>this</code> and store the result in <code>dest</code>.
     *
     * @param mat  the matrix to multiply this vector by
     * @param dest will hold the result
     * @return dest
     */
    Vector3dc mul(IMatrix3d mat, Vector3dc dest);

    /**
     * Multiply the given matrix <code>mat</code> with <code>this</code> and store the result in <code>dest</code>.
     *
     * @param mat  the matrix to multiply this vector by
     * @param dest will hold the result
     * @return dest
     */
    Vector3dc mul(IMatrix3f mat, Vector3dc dest);

    /**
     * Multiply the given matrix <code>mat</code> with <code>this</code> by assuming a third row in the matrix of
     * <tt>(0, 0, 1)</tt> and store the result in <code>dest</code>.
     *
     * @param mat  the matrix to multiply this vector by
     * @param dest will hold the result
     * @return dest
     */
    Vector3dc mul(IMatrix3x2d mat, Vector3dc dest);

    /**
     * Multiply the given matrix <code>mat</code> with <code>this</code> by assuming a third row in the matrix of
     * <tt>(0, 0, 1)</tt> and store the result in <code>dest</code>.
     *
     * @param mat  the matrix to multiply this vector by
     * @param dest will hold the result
     * @return dest
     */
    Vector3dc mul(IMatrix3x2f mat, Vector3dc dest);

    /**
     * Multiply the transpose of the given matrix with this Vector3fc and store the result in <code>dest</code>.
     *
     * @param mat  the matrix
     * @param dest will hold the result
     * @return dest
     */
    Vector3dc mulTranspose(IMatrix3d mat, Vector3dc dest);

    /**
     * Multiply the transpose of the given matrix with this Vector3fc and store the result in <code>dest</code>.
     *
     * @param mat  the matrix
     * @param dest will hold the result
     * @return dest
     */
    Vector3dc mulTranspose(IMatrix3f mat, Vector3dc dest);

    /**
     * Multiply the given 4x4 matrix <code>mat</code> with <code>this</code> and store the result in <code>dest</code>.
     * <p>
     * This method assumes the <tt>w</tt> component of <code>this</code> to be <tt>1.0</tt>.
     *
     * @param mat  the matrix to multiply this vector by
     * @param dest will hold the result
     * @return dest
     */
    Vector3dc mulPosition(IMatrix4d mat, Vector3dc dest);

    /**
     * Multiply the given 4x4 matrix <code>mat</code> with <code>this</code> and store the result in <code>dest</code>.
     * <p>
     * This method assumes the <tt>w</tt> component of <code>this</code> to be <tt>1.0</tt>.
     *
     * @param mat  the matrix to multiply this vector by
     * @param dest will hold the result
     * @return dest
     */
    Vector3dc mulPosition(IMatrix4f mat, Vector3dc dest);

    /**
     * Multiply the given 4x3 matrix <code>mat</code> with <code>this</code> and store the result in <code>dest</code>.
     * <p>
     * This method assumes the <tt>w</tt> component of <code>this</code> to be <tt>1.0</tt>.
     *
     * @param mat  the matrix to multiply this vector by
     * @param dest will hold the result
     * @return dest
     */
    Vector3dc mulPosition(IMatrix4x3d mat, Vector3dc dest);

    /**
     * Multiply the given 4x3 matrix <code>mat</code> with <code>this</code> and store the result in <code>dest</code>.
     * <p>
     * This method assumes the <tt>w</tt> component of <code>this</code> to be <tt>1.0</tt>.
     *
     * @param mat  the matrix to multiply this vector by
     * @param dest will hold the result
     * @return dest
     */
    Vector3dc mulPosition(IMatrix4x3f mat, Vector3dc dest);

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
    Vector3dc mulTransposePosition(IMatrix4d mat, Vector3dc dest);

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
    Vector3dc mulTransposePosition(IMatrix4f mat, Vector3dc dest);

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
    double mulPositionW(IMatrix4f mat, Vector3dc dest);

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
    double mulPositionW(IMatrix4d mat, Vector3dc dest);

    /**
     * Multiply the given 4x4 matrix <code>mat</code> with <code>this</code> and store the result in <code>dest</code>.
     * <p>
     * This method assumes the <tt>w</tt> component of <code>this</code> to be <tt>0.0</tt>.
     *
     * @param mat  the matrix to multiply this vector by
     * @param dest will hold the result
     * @return dest
     */
    Vector3dc mulDirection(IMatrix4d mat, Vector3dc dest);

    /**
     * Multiply the given 4x4 matrix <code>mat</code> with <code>this</code> and store the result in <code>dest</code>.
     * <p>
     * This method assumes the <tt>w</tt> component of <code>this</code> to be <tt>0.0</tt>.
     *
     * @param mat  the matrix to multiply this vector by
     * @param dest will hold the result
     * @return dest
     */
    Vector3dc mulDirection(IMatrix4f mat, Vector3dc dest);

    /**
     * Multiply the given 4x3 matrix <code>mat</code> with <code>this</code> and store the result in <code>dest</code>.
     * <p>
     * This method assumes the <tt>w</tt> component of <code>this</code> to be <tt>0.0</tt>.
     *
     * @param mat  the matrix to multiply this vector by
     * @param dest will hold the result
     * @return dest
     */
    Vector3dc mulDirection(IMatrix4x3d mat, Vector3dc dest);

    /**
     * Multiply the given 4x3 matrix <code>mat</code> with <code>this</code> and store the result in <code>dest</code>.
     * <p>
     * This method assumes the <tt>w</tt> component of <code>this</code> to be <tt>0.0</tt>.
     *
     * @param mat  the matrix to multiply this vector by
     * @param dest will hold the result
     * @return dest
     */
    Vector3dc mulDirection(IMatrix4x3f mat, Vector3dc dest);

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
    Vector3dc mulTransposeDirection(IMatrix4d mat, Vector3dc dest);

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
    Vector3dc mulTransposeDirection(IMatrix4f mat, Vector3dc dest);

    /**
     * Multiply this Vector3dc by the given scalar value and store the result in <code>dest</code>.
     *
     * @param scalar the scalar factor
     * @param dest   will hold the result
     * @return dest
     */
    Vector3dc mul(double scalar, Vector3dc dest);

    /**
     * Multiply the components of this Vector3fc by the given scalar values and store the result in <code>dest</code>.
     *
     * @param x    the x component to multiply this vector by
     * @param y    the y component to multiply this vector by
     * @param z    the z component to multiply this vector by
     * @param dest will hold the result
     * @return dest
     */
    Vector3dc mul(double x, double y, double z, Vector3dc dest);

    /**
     * Rotate this vector by the given quaternion <code>quat</code> and store the result in <code>dest</code>.
     *
     * @param quat the quaternion to rotate this vector
     * @param dest will hold the result
     * @return dest
     *
     * @see IQuaterniond#transform(Vector3dc)
     */
    Vector3dc rotate(IQuaterniond quat, Vector3dc dest);

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
     * @see Quaterniondc#rotationTo(IVector3d, IVector3d)
     */
    Quaterniondc rotationTo(IVector3d toDir, Quaterniondc dest);

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
     * @see Quaterniondc#rotationTo(double, double, double, double, double, double)
     */
    Quaterniondc rotationTo(double toDirX, double toDirY, double toDirZ, Quaterniondc dest);

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
    Vector3dc rotateAxis(double angle, double aX, double aY, double aZ, Vector3dc dest);

    /**
     * Rotate this vector the specified radians around the X axis and store the result into <code>dest</code>.
     *
     * @param angle the angle in radians
     * @param dest  will hold the result
     * @return dest
     */
    Vector3dc rotateX(double angle, Vector3dc dest);

    /**
     * Rotate this vector the specified radians around the Y axis and store the result into <code>dest</code>.
     *
     * @param angle the angle in radians
     * @param dest  will hold the result
     * @return dest
     */
    Vector3dc rotateY(double angle, Vector3dc dest);

    /**
     * Rotate this vector the specified radians around the Z axis and store the result into <code>dest</code>.
     *
     * @param angle the angle in radians
     * @param dest  will hold the result
     * @return dest
     */
    Vector3dc rotateZ(double angle, Vector3dc dest);

    /**
     * Divide this Vector3dc by the given scalar value and store the result in <code>dest</code>.
     *
     * @param scalar the scalar to divide this vector by
     * @param dest   will hold the result
     * @return dest
     */
    Vector3dc div(double scalar, Vector3dc dest);

    /**
     * Divide the components of this Vector3fc by the given scalar values and store the result in <code>dest</code>.
     *
     * @param x    the x component to divide this vector by
     * @param y    the y component to divide this vector by
     * @param z    the z component to divide this vector by
     * @param dest will hold the result
     * @return dest
     */
    Vector3dc div(double x, double y, double z, Vector3dc dest);

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
     * Normalize this vector and store the result in <code>dest</code>.
     *
     * @param dest will hold the result
     * @return dest
     */
    Vector3dc normalize(Vector3dc dest);

    /**
     * Scale this vector to have the given length and store the result in <code>dest</code>.
     *
     * @param length the desired length
     * @param dest   will hold the result
     * @return dest
     */
    Vector3dc normalize(double length, Vector3dc dest);

    /**
     * Calculate the cross product of this and v2 and store the result in <code>dest</code>.
     *
     * @param v    the other vector
     * @param dest will hold the result
     * @return dest
     */
    Vector3dc cross(IVector3d v, Vector3dc dest);

    /**
     * Compute the cross product of this vector and <tt>(x, y, z)</tt> and store the result in <code>dest</code>.
     *
     * @param x    the x component of the other vector
     * @param y    the y component of the other vector
     * @param z    the z component of the other vector
     * @param dest will hold the result
     * @return dest
     */
    Vector3dc cross(double x, double y, double z, Vector3dc dest);

    /**
     * Return the distance between this vector and <code>v</code>.
     *
     * @param v the other vector
     * @return the distance
     */
    double distance(IVector3d v);

    /**
     * Return the distance between <code>this</code> vector and <tt>(x, y, z)</tt>.
     *
     * @param x the x component of the other vector
     * @param y the y component of the other vector
     * @param z the z component of the other vector
     * @return the euclidean distance
     */
    double distance(double x, double y, double z);

    /**
     * Return the square of the distance between this vector and <code>v</code>.
     *
     * @param v the other vector
     * @return the squared of the distance
     */
    double distanceSquared(IVector3d v);

    /**
     * Return the square of the distance between <code>this</code> vector and <tt>(x, y, z)</tt>.
     *
     * @param x the x component of the other vector
     * @param y the y component of the other vector
     * @param z the z component of the other vector
     * @return the square of the distance
     */
    double distanceSquared(double x, double y, double z);

    /**
     * Return the dot product of this vector and the supplied vector.
     *
     * @param v the other vector
     * @return the dot product
     */
    double dot(IVector3d v);

    /**
     * Return the dot product of this vector and the vector <tt>(x, y, z)</tt>.
     *
     * @param x the x component of the other vector
     * @param y the y component of the other vector
     * @param z the z component of the other vector
     * @return the dot product
     */
    double dot(double x, double y, double z);

    /**
     * Return the cosine of the angle between <code>this</code> vector and the supplied vector. Use this instead of
     * <tt>Math.cos(angle(v))</tt>.
     *
     * @param v the other vector
     * @return the cosine of the angle
     *
     * @see #angle(IVector3d)
     */
    double angleCos(IVector3d v);

    /**
     * Return the angle between this vector and the supplied vector.
     *
     * @param v the other vector
     * @return the angle, in radians
     *
     * @see #angleCos(IVector3d)
     */
    double angle(IVector3d v);

    /**
     * Negate this vector and store the result in <code>dest</code>.
     *
     * @param dest will hold the result
     * @return dest
     */
    Vector3dc negate(Vector3dc dest);

    /**
     * Compute the absolute values of the individual components of <code>this</code> and store the result in
     * <code>dest</code>.
     *
     * @param dest will hold the result
     * @return dest
     */
    Vector3dc absolute(Vector3dc dest);

    /**
     * Reflect this vector about the given normal vector and store the result in <code>dest</code>.
     *
     * @param normal the vector to reflect about
     * @param dest   will hold the result
     * @return dest
     */
    Vector3dc reflect(IVector3d normal, Vector3dc dest);

    /**
     * Reflect this vector about the given normal vector and store the result in <code>dest</code>.
     *
     * @param x    the x component of the normal
     * @param y    the y component of the normal
     * @param z    the z component of the normal
     * @param dest will hold the result
     * @return dest
     */
    Vector3dc reflect(double x, double y, double z, Vector3dc dest);

    /**
     * Compute the half vector between this and the other vector and store the result in <code>dest</code>.
     *
     * @param other the other vector
     * @param dest  will hold the result
     * @return dest
     */
    Vector3dc half(IVector3d other, Vector3dc dest);

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
    Vector3dc half(double x, double y, double z, Vector3dc dest);

    /**
     * Compute a smooth-step (i.e. hermite with zero tangents) interpolation between <code>this</code> vector and the
     * given vector <code>v</code> and store the result in <code>dest</code>.
     *
     * @param v    the other vector
     * @param t    the interpolation factor, within <tt>[0..1]</tt>
     * @param dest will hold the result
     * @return dest
     */
    Vector3dc smoothStep(IVector3d v, double t, Vector3dc dest);

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
    Vector3dc hermite(IVector3d t0, IVector3d v1, IVector3d t1, double t, Vector3dc dest);

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
    Vector3dc lerp(IVector3d other, double t, Vector3dc dest);

    /**
     * Get the value of the specified component of this vector.
     *
     * @param component the component, within <tt>[0..2]</tt>
     * @return the value
     */
    double get(int component);

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
    Vector3dc orthogonalize(IVector3d v, Vector3dc dest);

    /**
     * Transform <code>this</code> vector so that it is orthogonal to the given unit vector <code>v</code>, normalize
     * the result and store it into <code>dest</code>.
     * <p>
     * The vector <code>v</code> is assumed to be a {@link #normalize(Vector3dc) unit} vector.
     * <p>
     * Reference: <a href="https://en.wikipedia.org/wiki/Gram%E2%80%93Schmidt_process">Gram–Schmidt process</a>
     *
     * @param v    the reference unit vector which the result should be orthogonal to
     * @param dest will hold the result
     * @return dest
     */
    Vector3dc orthogonalizeUnit(IVector3d v, Vector3dc dest);
}