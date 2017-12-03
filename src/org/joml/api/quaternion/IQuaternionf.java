/*
 * (C) Copyright 2015-2017 JOML

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
package org.joml.api.quaternion;

import org.joml.api.AxisAngle4fc;
import org.joml.api.matrix.*;
import org.joml.api.vector.IVector3f;
import org.joml.api.vector.IVector4f;
import org.joml.api.vector.Vector3fc;
import org.joml.api.vector.Vector4fc;

//#ifdef __HAS_NIO__
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
//#endif

/**
 * Interface to a read-only view of a quaternion of single-precision floats.
 *
 * @author Kai Burjack
 */
public interface IQuaternionf {

    /**
     * @return the first component of the vector part
     */
    float x();

    /**
     * @return the second component of the vector part
     */
    float y();

    /**
     * @return the third component of the vector part
     */
    float z();

    /**
     * @return the real/scalar part of the quaternion
     */
    float w();

    /**
     * Normalize this quaternion and store the result in <code>dest</code>.
     *
     * @param dest will hold the result
     * @return dest
     */
    Quaternionfc normalize(Quaternionfc dest);

    /**
     * Add the quaternion <tt>(x, y, z, w)</tt> to this quaternion and store the result in <code>dest</code>.
     *
     * @param x    the x component of the vector part
     * @param y    the y component of the vector part
     * @param z    the z component of the vector part
     * @param w    the real/scalar component
     * @param dest will hold the result
     * @return dest
     */
    Quaternionfc add(float x, float y, float z, float w, Quaternionfc dest);

    /**
     * Add <code>q2</code> to this quaternion and store the result in <code>dest</code>.
     *
     * @param q2   the quaternion to add to this
     * @param dest will hold the result
     * @return dest
     */
    Quaternionfc add(IQuaternionf q2, Quaternionfc dest);

    /**
     * Return the angle in radians represented by this quaternion rotation.
     *
     * @return the angle in radians
     */
    float angle();

    /**
     * Set the given destination matrix to the rotation represented by <code>this</code>.
     *
     * @param dest the matrix to write the rotation into
     * @return the passed in destination
     *
     * @see Matrix3fc#set(IQuaternionf)
     */
    Matrix3fc get(Matrix3fc dest);

    /**
     * Set the given destination matrix to the rotation represented by <code>this</code>.
     *
     * @param dest the matrix to write the rotation into
     * @return the passed in destination
     *
     * @see Matrix3dc#set(IQuaternionf)
     */
    Matrix3dc get(Matrix3dc dest);

    /**
     * Set the given destination matrix to the rotation represented by <code>this</code>.
     *
     * @param dest the matrix to write the rotation into
     * @return the passed in destination
     *
     * @see Matrix4fc#set(IQuaternionf)
     */
    Matrix4fc get(Matrix4fc dest);

    /**
     * Set the given destination matrix to the rotation represented by <code>this</code>.
     *
     * @param dest the matrix to write the rotation into
     * @return the passed in destination
     *
     * @see Matrix4dc#set(IQuaternionf)
     */
    Matrix4dc get(Matrix4dc dest);

    /**
     * Set the given destination matrix to the rotation represented by <code>this</code>.
     *
     * @param dest the matrix to write the rotation into
     * @return the passed in destination
     *
     * @see Matrix4x3fc#set(IQuaternionf)
     */
    Matrix4x3fc get(Matrix4x3fc dest);

    /**
     * Set the given destination matrix to the rotation represented by <code>this</code>.
     *
     * @param dest the matrix to write the rotation into
     * @return the passed in destination
     *
     * @see Matrix4x3dc#set(IQuaternionf)
     */
    Matrix4x3dc get(Matrix4x3dc dest);

    /**
     * Set the given {@link AxisAngle4fc} to represent the rotation of <code>this</code> quaternion.
     *
     * @param dest the {@link AxisAngle4fc} to set
     * @return the passed in destination
     */
    AxisAngle4fc get(AxisAngle4fc dest);

    /**
     * Set the given {@link Quaterniondc} to the values of <code>this</code>.
     *
     * @param dest the {@link Quaterniondc} to set
     * @return the passed in destination
     *
     * @see Quaterniondc#set(IQuaternionf)
     */
    Quaterniondc get(Quaterniondc dest);

    /**
     * Set the given {@link Quaternionfc} to the values of <code>this</code>.
     *
     * @param dest the {@link Quaternionfc} to set
     * @return the passed in destination
     */
    Quaternionfc get(Quaternionfc dest);

    //#ifdef __HAS_NIO__

    /**
     * Store the 3x3 float matrix representation of <code>this</code> quaternion in column-major order into the given
     * {@link ByteBuffer}.
     * <p>
     * This is equivalent to calling: <code>this.get(new Matrix3fc()).get(dest)</code>
     *
     * @param dest the destination buffer
     * @return dest
     */
    ByteBuffer getAsMatrix3f(ByteBuffer dest);

    /**
     * Store the 3x3 float matrix representation of <code>this</code> quaternion in column-major order into the given
     * {@link FloatBuffer}.
     * <p>
     * This is equivalent to calling: <code>this.get(new Matrix3fc()).get(dest)</code>
     *
     * @param dest the destination buffer
     * @return dest
     */
    FloatBuffer getAsMatrix3f(FloatBuffer dest);

    /**
     * Store the 4x4 float matrix representation of <code>this</code> quaternion in column-major order into the given
     * {@link ByteBuffer}.
     * <p>
     * This is equivalent to calling: <code>this.get(new Matrix4fc()).get(dest)</code>
     *
     * @param dest the destination buffer
     * @return dest
     */
    ByteBuffer getAsMatrix4f(ByteBuffer dest);

    /**
     * Store the 4x4 float matrix representation of <code>this</code> quaternion in column-major order into the given
     * {@link FloatBuffer}.
     * <p>
     * This is equivalent to calling: <code>this.get(new Matrix4fc()).get(dest)</code>
     *
     * @param dest the destination buffer
     * @return dest
     */
    FloatBuffer getAsMatrix4f(FloatBuffer dest);

    /**
     * Store the 4x3 float matrix representation of <code>this</code> quaternion in column-major order into the given
     * {@link ByteBuffer}.
     * <p>
     * This is equivalent to calling: <code>this.get(new Matrix4x3fc()).get(dest)</code>
     *
     * @param dest the destination buffer
     * @return dest
     */
    ByteBuffer getAsMatrix4x3f(ByteBuffer dest);

    /**
     * Store the 4x3 float matrix representation of <code>this</code> quaternion in column-major order into the given
     * {@link FloatBuffer}.
     * <p>
     * This is equivalent to calling: <code>this.get(new Matrix4x3fc()).get(dest)</code>
     *
     * @param dest the destination buffer
     * @return dest
     */
    FloatBuffer getAsMatrix4x3f(FloatBuffer dest);
    //#endif

    /**
     * Multiply this quaternion by <code>q</code> and store the result in <code>dest</code>.
     * <p>
     * If <tt>T</tt> is <code>this</code> and <tt>Q</tt> is the given quaternion, then the resulting quaternion
     * <tt>R</tt> is:
     * <p>
     * <tt>R = T * Q</tt>
     * <p>
     * So, this method uses post-multiplication like the matrix classes, resulting in a vector to be transformed by
     * <tt>Q</tt> first, and then by <tt>T</tt>.
     *
     * @param q    the quaternion to multiply <code>this</code> by
     * @param dest will hold the result
     * @return dest
     */
    Quaternionfc mul(IQuaternionf q, Quaternionfc dest);

    /**
     * Multiply this quaternion by the quaternion represented via <tt>(qx, qy, qz, qw)</tt> and store the result in
     * <code>dest</code>.
     * <p>
     * If <tt>T</tt> is <code>this</code> and <tt>Q</tt> is the given quaternion, then the resulting quaternion
     * <tt>R</tt> is:
     * <p>
     * <tt>R = T * Q</tt>
     * <p>
     * So, this method uses post-multiplication like the matrix classes, resulting in a vector to be transformed by
     * <tt>Q</tt> first, and then by <tt>T</tt>.
     *
     * @param qx   the x component of the quaternion to multiply <code>this</code> by
     * @param qy   the y component of the quaternion to multiply <code>this</code> by
     * @param qz   the z component of the quaternion to multiply <code>this</code> by
     * @param qw   the w component of the quaternion to multiply <code>this</code> by
     * @param dest will hold the result
     * @return dest
     */
    Quaternionfc mul(float qx, float qy, float qz, float qw, Quaternionfc dest);

    /**
     * Pre-multiply this quaternion by <code>q</code> and store the result in <code>dest</code>.
     * <p>
     * If <tt>T</tt> is <code>this</code> and <tt>Q</tt> is the given quaternion, then the resulting quaternion
     * <tt>R</tt> is:
     * <p>
     * <tt>R = Q * T</tt>
     * <p>
     * So, this method uses pre-multiplication, resulting in a vector to be transformed by <tt>T</tt> first, and then by
     * <tt>Q</tt>.
     *
     * @param q    the quaternion to pre-multiply <code>this</code> by
     * @param dest will hold the result
     * @return dest
     */
    Quaternionfc premul(IQuaternionf q, Quaternionfc dest);

    /**
     * Pre-multiply this quaternion by the quaternion represented via <tt>(qx, qy, qz, qw)</tt> and store the result in
     * <code>dest</code>.
     * <p>
     * If <tt>T</tt> is <code>this</code> and <tt>Q</tt> is the given quaternion, then the resulting quaternion
     * <tt>R</tt> is:
     * <p>
     * <tt>R = Q * T</tt>
     * <p>
     * So, this method uses pre-multiplication, resulting in a vector to be transformed by <tt>T</tt> first, and then by
     * <tt>Q</tt>.
     *
     * @param qx   the x component of the quaternion to multiply <code>this</code> by
     * @param qy   the y component of the quaternion to multiply <code>this</code> by
     * @param qz   the z component of the quaternion to multiply <code>this</code> by
     * @param qw   the w component of the quaternion to multiply <code>this</code> by
     * @param dest will hold the result
     * @return dest
     */
    Quaternionfc premul(float qx, float qy, float qz, float qw, Quaternionfc dest);

    /**
     * Transform the given vector by this quaternion. This will apply the rotation described by this quaternion to the
     * given vector.
     *
     * @param vec the vector to transform
     * @return vec
     */
    Vector3fc transform(Vector3fc vec);

    /**
     * Transform the given vector by this quaternion. This will apply the rotation described by this quaternion to the
     * given vector.
     * <p>
     * Only the first three components of the given 4D vector are being used and modified.
     *
     * @param vec the vector to transform
     * @return vec
     */
    Vector4fc transform(Vector4fc vec);

    /**
     * Transform the given vector by this quaternion and store the result in <code>dest</code>. This will apply the
     * rotation described by this quaternion to the given vector.
     *
     * @param vec  the vector to transform
     * @param dest will hold the result
     * @return dest
     */
    Vector3fc transform(IVector3f vec, Vector3fc dest);

    /**
     * Transform the given vector <tt>(x, y, z)</tt> by this quaternion and store the result in <code>dest</code>. This
     * will apply the rotation described by this quaternion to the given vector.
     *
     * @param x    the x coordinate of the vector to transform
     * @param y    the y coordinate of the vector to transform
     * @param z    the z coordinate of the vector to transform
     * @param dest will hold the result
     * @return dest
     */
    Vector3fc transform(float x, float y, float z, Vector3fc dest);

    /**
     * Transform the given vector by this quaternion and store the result in <code>dest</code>. This will apply the
     * rotation described by this quaternion to the given vector.
     * <p>
     * Only the first three components of the given 4D vector are being used and set on the destination.
     *
     * @param vec  the vector to transform
     * @param dest will hold the result
     * @return dest
     */
    Vector4fc transform(IVector4f vec, Vector4fc dest);

    /**
     * Transform the given vector <tt>(x, y, z)</tt> by this quaternion and store the result in <code>dest</code>. This
     * will apply the rotation described by this quaternion to the given vector.
     *
     * @param x    the x coordinate of the vector to transform
     * @param y    the y coordinate of the vector to transform
     * @param z    the z coordinate of the vector to transform
     * @param dest will hold the result
     * @return dest
     */
    Vector4fc transform(float x, float y, float z, Vector4fc dest);

    /**
     * Invert this quaternion and store the {@link #normalize(Quaternionfc) normalized} result in <code>dest</code>.
     * <p>
     * If this quaternion is already normalized, then {@link #conjugate(Quaternionfc)} should be used instead.
     *
     * @param dest will hold the result
     * @return dest
     *
     * @see #conjugate(Quaternionfc)
     */
    Quaternionfc invert(Quaternionfc dest);

    /**
     * Divide <code>this</code> quaternion by <code>b</code> and store the result in <code>dest</code>.
     * <p>
     * The division expressed using the inverse is performed in the following way:
     * <p>
     * <tt>dest = this * b^-1</tt>, where <tt>b^-1</tt> is the inverse of <code>b</code>.
     *
     * @param b    the {@link IQuaternionf} to divide this by
     * @param dest will hold the result
     * @return dest
     */
    Quaternionfc div(IQuaternionf b, Quaternionfc dest);

    /**
     * Conjugate this quaternion and store the result in <code>dest</code>.
     *
     * @param dest will hold the result
     * @return dest
     */
    Quaternionfc conjugate(Quaternionfc dest);

    /**
     * Apply a rotation to <code>this</code> quaternion rotating the given radians about the cartesian base unit axes,
     * called the euler angles using rotation sequence <tt>XYZ</tt> and store the result in <code>dest</code>.
     * <p>
     * This method is equivalent to calling: <tt>rotateX(angleX, dest).rotateY(angleY).rotateZ(angleZ)</tt>
     * <p>
     * If <code>Q</code> is <code>this</code> quaternion and <code>R</code> the quaternion representing the specified
     * rotation, then the new quaternion will be <code>Q * R</code>. So when transforming a vector <code>v</code> with
     * the new quaternion by using <code>Q * R * v</code>, the rotation added by this method will be applied first!
     *
     * @param angleX the angle in radians to rotate about the x axis
     * @param angleY the angle in radians to rotate about the y axis
     * @param angleZ the angle in radians to rotate about the z axis
     * @param dest   will hold the result
     * @return dest
     */
    Quaternionfc rotateXYZ(float angleX, float angleY, float angleZ, Quaternionfc dest);

    /**
     * Apply a rotation to <code>this</code> quaternion rotating the given radians about the cartesian base unit axes,
     * called the euler angles, using the rotation sequence <tt>ZYX</tt> and store the result in <code>dest</code>.
     * <p>
     * This method is equivalent to calling: <tt>rotateZ(angleZ, dest).rotateY(angleY).rotateX(angleX)</tt>
     * <p>
     * If <code>Q</code> is <code>this</code> quaternion and <code>R</code> the quaternion representing the specified
     * rotation, then the new quaternion will be <code>Q * R</code>. So when transforming a vector <code>v</code> with
     * the new quaternion by using <code>Q * R * v</code>, the rotation added by this method will be applied first!
     *
     * @param angleZ the angle in radians to rotate about the z axis
     * @param angleY the angle in radians to rotate about the y axis
     * @param angleX the angle in radians to rotate about the x axis
     * @param dest   will hold the result
     * @return dest
     */
    Quaternionfc rotateZYX(float angleZ, float angleY, float angleX, Quaternionfc dest);

    /**
     * Apply a rotation to <code>this</code> quaternion rotating the given radians about the cartesian base unit axes,
     * called the euler angles, using the rotation sequence <tt>YXZ</tt> and store the result in <code>dest</code>.
     * <p>
     * This method is equivalent to calling: <tt>rotateY(angleY, dest).rotateX(angleX).rotateZ(angleZ)</tt>
     * <p>
     * If <code>Q</code> is <code>this</code> quaternion and <code>R</code> the quaternion representing the specified
     * rotation, then the new quaternion will be <code>Q * R</code>. So when transforming a vector <code>v</code> with
     * the new quaternion by using <code>Q * R * v</code>, the rotation added by this method will be applied first!
     *
     * @param angleY the angle in radians to rotate about the y axis
     * @param angleX the angle in radians to rotate about the x axis
     * @param angleZ the angle in radians to rotate about the z axis
     * @param dest   will hold the result
     * @return dest
     */
    Quaternionfc rotateYXZ(float angleY, float angleX, float angleZ, Quaternionfc dest);

    /**
     * Get the euler angles in radians in rotation sequence <tt>XYZ</tt> of this quaternion and store them in the
     * provided parameter <code>eulerAngles</code>.
     *
     * @param eulerAngles will hold the euler angles in radians
     * @return the passed in vector
     */
    Vector3fc getEulerAnglesXYZ(Vector3fc eulerAngles);

    /**
     * Return the square of the length of this quaternion.
     *
     * @return the length
     */
    float lengthSquared();

    /**
     * Interpolate between <code>this</code> {@link #normalize(Quaternionfc) unit} quaternion and the specified
     * <code>target</code> {@link #normalize(Quaternionfc) unit} quaternion using spherical linear interpolation using
     * the specified interpolation factor <code>alpha</code>, and store the result in <code>dest</code>.
     * <p>
     * This method resorts to non-spherical linear interpolation when the absolute dot product of <code>this</code> and
     * <code>target</code> is below <tt>1E-6f</tt>.
     * <p>
     * Reference: <a href="http://fabiensanglard.net/doom3_documentation/37725-293747_293747.pdf">http://fabiensanglard.net</a>
     *
     * @param target the target of the interpolation, which should be reached with <tt>alpha = 1.0</tt>
     * @param alpha  the interpolation factor, within <tt>[0..1]</tt>
     * @param dest   will hold the result
     * @return dest
     */
    Quaternionfc slerp(IQuaternionf target, float alpha, Quaternionfc dest);

    /**
     * Apply scaling to this quaternion, which results in any vector transformed by the quaternion to change its length
     * by the given <code>factor</code>, and store the result in <code>dest</code>.
     *
     * @param factor the scaling factor
     * @param dest   will hold the result
     * @return dest
     */
    Quaternionfc scale(float factor, Quaternionfc dest);

    /**
     * Integrate the rotation given by the angular velocity <code>(vx, vy, vz)</code> around the x, y and z axis,
     * respectively, with respect to the given elapsed time delta <code>dt</code> and add the differentiate rotation to
     * the rotation represented by this quaternion and store the result into <code>dest</code>.
     * <p>
     * This method pre-multiplies the rotation given by <code>dt</code> and <code>(vx, vy, vz)</code> by
     * <code>this</code>, so the angular velocities are always relative to the local coordinate system of the rotation
     * represented by <code>this</code> quaternion.
     * <p>
     * This method is equivalent to calling: <code>rotateLocal(dt * vx, dt * vy, dt * vz, dest)</code>
     * <p>
     * Reference: <a href="http://physicsforgames.blogspot.de/2010/02/quaternions.html">http://physicsforgames.blogspot.de/</a>
     *
     * @param dt   the delta time
     * @param vx   the angular velocity around the x axis
     * @param vy   the angular velocity around the y axis
     * @param vz   the angular velocity around the z axis
     * @param dest will hold the result
     * @return dest
     *
     * @see #rotateLocal(float, float, float, Quaternionfc)
     */
    Quaternionfc integrate(float dt, float vx, float vy, float vz, Quaternionfc dest);

    /**
     * Compute a linear (non-spherical) interpolation of <code>this</code> and the given quaternion <code>q</code> and
     * store the result in <code>dest</code>.
     * <p>
     * Reference: <a href="http://fabiensanglard.net/doom3_documentation/37725-293747_293747.pdf">http://fabiensanglard.net</a>
     *
     * @param q      the other quaternion
     * @param factor the interpolation factor. It is between 0.0 and 1.0
     * @param dest   will hold the result
     * @return dest
     */
    Quaternionfc nlerp(IQuaternionf q, float factor, Quaternionfc dest);

    /**
     * Compute linear (non-spherical) interpolations of <code>this</code> and the given quaternion <code>q</code>
     * iteratively and store the result in <code>dest</code>.
     * <p>
     * This method performs a series of small-step nlerp interpolations to avoid doing a costly spherical linear
     * interpolation, like {@link #slerp(IQuaternionf, float, Quaternionfc) slerp}, by subdividing the rotation arc
     * between <code>this</code> and <code>q</code> via non-spherical linear interpolations as long as the absolute dot
     * product of <code>this</code> and <code>q</code> is greater than the given <code>dotThreshold</code> parameter.
     * <p>
     * Thanks to <tt>@theagentd</tt> at <a href="http://www.java-gaming.org/">http://www.java-gaming.org/</a> for
     * providing the code.
     *
     * @param q            the other quaternion
     * @param alpha        the interpolation factor, between 0.0 and 1.0
     * @param dotThreshold the threshold for the dot product of <code>this</code> and <code>q</code> above which this
     *                     method performs another iteration of a small-step linear interpolation
     * @param dest         will hold the result
     * @return dest
     */
    Quaternionfc nlerpIterative(IQuaternionf q, float alpha, float dotThreshold, Quaternionfc dest);

    /**
     * Apply a rotation to this quaternion that maps the given direction to the positive Z axis, and store the result in
     * <code>dest</code>.
     * <p>
     * Because there are multiple possibilities for such a rotation, this method will choose the one that ensures the
     * given up direction to remain parallel to the plane spanned by the <code>up</code> and <code>dir</code> vectors.
     * <p>
     * If <code>Q</code> is <code>this</code> quaternion and <code>R</code> the quaternion representing the specified
     * rotation, then the new quaternion will be <code>Q * R</code>. So when transforming a vector <code>v</code> with
     * the new quaternion by using <code>Q * R * v</code>, the rotation added by this method will be applied first!
     * <p>
     * Reference: <a href="http://answers.unity3d.com/questions/467614/what-is-the-source-code-of-quaternionlookrotation.html">http://answers.unity3d.com</a>
     *
     * @param dir  the direction to map to the positive Z axis
     * @param up   the vector which will be mapped to a vector parallel to the plane spanned by the given
     *             <code>dir</code> and <code>up</code>
     * @param dest will hold the result
     * @return dest
     *
     * @see #lookAlong(float, float, float, float, float, float, Quaternionfc)
     */
    Quaternionfc lookAlong(IVector3f dir, IVector3f up, Quaternionfc dest);

    /**
     * Apply a rotation to this quaternion that maps the given direction to the positive Z axis, and store the result in
     * <code>dest</code>.
     * <p>
     * Because there are multiple possibilities for such a rotation, this method will choose the one that ensures the
     * given up direction to remain parallel to the plane spanned by the <tt>up</tt> and <tt>dir</tt> vectors.
     * <p>
     * If <code>Q</code> is <code>this</code> quaternion and <code>R</code> the quaternion representing the specified
     * rotation, then the new quaternion will be <code>Q * R</code>. So when transforming a vector <code>v</code> with
     * the new quaternion by using <code>Q * R * v</code>, the rotation added by this method will be applied first!
     * <p>
     * Reference: <a href="http://answers.unity3d.com/questions/467614/what-is-the-source-code-of-quaternionlookrotation.html">http://answers.unity3d.com</a>
     *
     * @param dirX the x-coordinate of the direction to look along
     * @param dirY the y-coordinate of the direction to look along
     * @param dirZ the z-coordinate of the direction to look along
     * @param upX  the x-coordinate of the up vector
     * @param upY  the y-coordinate of the up vector
     * @param upZ  the z-coordinate of the up vector
     * @param dest will hold the result
     * @return dest
     */
    Quaternionfc lookAlong(float dirX, float dirY, float dirZ, float upX, float upY, float upZ, Quaternionfc dest);

    /**
     * Apply a rotation to <code>this</code> that rotates the <tt>fromDir</tt> vector to point along <tt>toDir</tt> and
     * store the result in <code>dest</code>.
     * <p>
     * Since there can be multiple possible rotations, this method chooses the one with the shortest arc.
     * <p>
     * If <code>Q</code> is <code>this</code> quaternion and <code>R</code> the quaternion representing the specified
     * rotation, then the new quaternion will be <code>Q * R</code>. So when transforming a vector <code>v</code> with
     * the new quaternion by using <code>Q * R * v</code>, the rotation added by this method will be applied first!
     * <p>
     * Reference: <a href="http://stackoverflow.com/questions/1171849/finding-quaternion-representing-the-rotation-from-one-vector-to-another#answer-1171995">stackoverflow.com</a>
     *
     * @param fromDirX the x-coordinate of the direction to rotate into the destination direction
     * @param fromDirY the y-coordinate of the direction to rotate into the destination direction
     * @param fromDirZ the z-coordinate of the direction to rotate into the destination direction
     * @param toDirX   the x-coordinate of the direction to rotate to
     * @param toDirY   the y-coordinate of the direction to rotate to
     * @param toDirZ   the z-coordinate of the direction to rotate to
     * @param dest     will hold the result
     * @return dest
     */
    Quaternionfc rotateTo(float fromDirX, float fromDirY, float fromDirZ, float toDirX, float toDirY, float toDirZ, Quaternionfc dest);

    /**
     * Apply a rotation to <code>this</code> that rotates the <code>fromDir</code> vector to point along
     * <code>toDir</code> and store the result in <code>dest</code>.
     * <p>
     * Because there can be multiple possible rotations, this method chooses the one with the shortest arc.
     * <p>
     * If <code>Q</code> is <code>this</code> quaternion and <code>R</code> the quaternion representing the specified
     * rotation, then the new quaternion will be <code>Q * R</code>. So when transforming a vector <code>v</code> with
     * the new quaternion by using <code>Q * R * v</code>, the rotation added by this method will be applied first!
     *
     * @param fromDir the starting direction
     * @param toDir   the destination direction
     * @param dest    will hold the result
     * @return dest
     *
     * @see #rotateTo(float, float, float, float, float, float, Quaternionfc)
     */
    Quaternionfc rotateTo(IVector3f fromDir, IVector3f toDir, Quaternionfc dest);

    /**
     * Apply a rotation to <code>this</code> quaternion rotating the given radians about the basis unit axes of the
     * cartesian space and store the result in <code>dest</code>.
     * <p>
     * If <code>Q</code> is <code>this</code> quaternion and <code>R</code> the quaternion representing the specified
     * rotation, then the new quaternion will be <code>Q * R</code>. So when transforming a vector <code>v</code> with
     * the new quaternion by using <code>Q * R * v</code>, the rotation added by this method will be applied first!
     *
     * @param angleX the angle in radians to rotate about the x axis
     * @param angleY the angle in radians to rotate about the y axis
     * @param angleZ the angle in radians to rotate about the z axis
     * @param dest   will hold the result
     * @return dest
     */
    Quaternionfc rotate(float angleX, float angleY, float angleZ, Quaternionfc dest);

    /**
     * Apply a rotation to <code>this</code> quaternion rotating the given radians about the basis unit axes of the
     * local coordinate system represented by this quaternion and store the result in <code>dest</code>.
     * <p>
     * If <code>Q</code> is <code>this</code> quaternion and <code>R</code> the quaternion representing the specified
     * rotation, then the new quaternion will be <code>R * Q</code>. So when transforming a vector <code>v</code> with
     * the new quaternion by using <code>R * Q * v</code>, the rotation represented by <code>this</code> will be applied
     * first!
     *
     * @param angleX the angle in radians to rotate about the local x axis
     * @param angleY the angle in radians to rotate about the local y axis
     * @param angleZ the angle in radians to rotate about the local z axis
     * @param dest   will hold the result
     * @return dest
     */
    Quaternionfc rotateLocal(float angleX, float angleY, float angleZ, Quaternionfc dest);

    /**
     * Apply a rotation to <code>this</code> quaternion rotating the given radians about the x axis and store the result
     * in <code>dest</code>.
     * <p>
     * If <code>Q</code> is <code>this</code> quaternion and <code>R</code> the quaternion representing the specified
     * rotation, then the new quaternion will be <code>Q * R</code>. So when transforming a vector <code>v</code> with
     * the new quaternion by using <code>Q * R * v</code>, the rotation added by this method will be applied first!
     *
     * @param angle the angle in radians to rotate about the x axis
     * @param dest  will hold the result
     * @return dest
     *
     * @see #rotate(float, float, float, Quaternionfc)
     */
    Quaternionfc rotateX(float angle, Quaternionfc dest);

    /**
     * Apply a rotation to <code>this</code> quaternion rotating the given radians about the y axis and store the result
     * in <code>dest</code>.
     * <p>
     * If <code>Q</code> is <code>this</code> quaternion and <code>R</code> the quaternion representing the specified
     * rotation, then the new quaternion will be <code>Q * R</code>. So when transforming a vector <code>v</code> with
     * the new quaternion by using <code>Q * R * v</code>, the rotation added by this method will be applied first!
     *
     * @param angle the angle in radians to rotate about the y axis
     * @param dest  will hold the result
     * @return dest
     *
     * @see #rotate(float, float, float, Quaternionfc)
     */
    Quaternionfc rotateY(float angle, Quaternionfc dest);

    /**
     * Apply a rotation to <code>this</code> quaternion rotating the given radians about the z axis and store the result
     * in <code>dest</code>.
     * <p>
     * If <code>Q</code> is <code>this</code> quaternion and <code>R</code> the quaternion representing the specified
     * rotation, then the new quaternion will be <code>Q * R</code>. So when transforming a vector <code>v</code> with
     * the new quaternion by using <code>Q * R * v</code>, the rotation added by this method will be applied first!
     *
     * @param angle the angle in radians to rotate about the z axis
     * @param dest  will hold the result
     * @return dest
     *
     * @see #rotate(float, float, float, Quaternionfc)
     */
    Quaternionfc rotateZ(float angle, Quaternionfc dest);

    /**
     * Apply a rotation to <code>this</code> quaternion rotating the given radians about the local x axis and store the
     * result in <code>dest</code>.
     * <p>
     * If <code>Q</code> is <code>this</code> quaternion and <code>R</code> the quaternion representing the specified
     * rotation, then the new quaternion will be <code>R * Q</code>. So when transforming a vector <code>v</code> with
     * the new quaternion by using <code>R * Q * v</code>, the rotation represented by <code>this</code> will be applied
     * first!
     *
     * @param angle the angle in radians to rotate about the local x axis
     * @param dest  will hold the result
     * @return dest
     */
    Quaternionfc rotateLocalX(float angle, Quaternionfc dest);

    /**
     * Apply a rotation to <code>this</code> quaternion rotating the given radians about the local y axis and store the
     * result in <code>dest</code>.
     * <p>
     * If <code>Q</code> is <code>this</code> quaternion and <code>R</code> the quaternion representing the specified
     * rotation, then the new quaternion will be <code>R * Q</code>. So when transforming a vector <code>v</code> with
     * the new quaternion by using <code>R * Q * v</code>, the rotation represented by <code>this</code> will be applied
     * first!
     *
     * @param angle the angle in radians to rotate about the local y axis
     * @param dest  will hold the result
     * @return dest
     */
    Quaternionfc rotateLocalY(float angle, Quaternionfc dest);

    /**
     * Apply a rotation to <code>this</code> quaternion rotating the given radians about the local z axis and store the
     * result in <code>dest</code>.
     * <p>
     * If <code>Q</code> is <code>this</code> quaternion and <code>R</code> the quaternion representing the specified
     * rotation, then the new quaternion will be <code>R * Q</code>. So when transforming a vector <code>v</code> with
     * the new quaternion by using <code>R * Q * v</code>, the rotation represented by <code>this</code> will be applied
     * first!
     *
     * @param angle the angle in radians to rotate about the local z axis
     * @param dest  will hold the result
     * @return dest
     */
    Quaternionfc rotateLocalZ(float angle, Quaternionfc dest);

    /**
     * Apply a rotation to <code>this</code> quaternion rotating the given radians about the specified axis and store
     * the result in <code>dest</code>.
     * <p>
     * If <code>Q</code> is <code>this</code> quaternion and <code>R</code> the quaternion representing the specified
     * rotation, then the new quaternion will be <code>Q * R</code>. So when transforming a vector <code>v</code> with
     * the new quaternion by using <code>Q * R * v</code>, the rotation added by this method will be applied first!
     *
     * @param angle the angle in radians to rotate about the specified axis
     * @param axisX the x coordinate of the rotation axis
     * @param axisY the y coordinate of the rotation axis
     * @param axisZ the z coordinate of the rotation axis
     * @param dest  will hold the result
     * @return dest
     */
    Quaternionfc rotateAxis(float angle, float axisX, float axisY, float axisZ, Quaternionfc dest);

    /**
     * Apply a rotation to <code>this</code> quaternion rotating the given radians about the specified axis and store
     * the result in <code>dest</code>.
     * <p>
     * If <code>Q</code> is <code>this</code> quaternion and <code>R</code> the quaternion representing the specified
     * rotation, then the new quaternion will be <code>Q * R</code>. So when transforming a vector <code>v</code> with
     * the new quaternion by using <code>Q * R * v</code>, the rotation added by this method will be applied first!
     *
     * @param angle the angle in radians to rotate about the specified axis
     * @param axis  the rotation axis
     * @param dest  will hold the result
     * @return dest
     *
     * @see #rotateAxis(float, float, float, float, Quaternionfc)
     */
    Quaternionfc rotateAxis(float angle, IVector3f axis, Quaternionfc dest);

    /**
     * Compute the difference between <code>this</code> and the <code>other</code> quaternion and store the result in
     * <code>dest</code>.
     * <p>
     * The difference is the rotation that has to be applied to get from <code>this</code> rotation to
     * <code>other</code>. If <tt>T</tt> is <code>this</code>, <tt>Q</tt> is <code>other</code> and <tt>D</tt> is the
     * computed difference, then the following equation holds:
     * <p>
     * <tt>T * D = Q</tt>
     * <p>
     * It is defined as: <tt>D = T^-1 * Q</tt>, where <tt>T^-1</tt> denotes the {@link #invert(Quaternionfc) inverse} of
     * <tt>T</tt>.
     *
     * @param other the other quaternion
     * @param dest  will hold the result
     * @return dest
     */
    Quaternionfc difference(Quaternionfc other, Quaternionfc dest);

    /**
     * Obtain the direction of <tt>+X</tt> before the rotation transformation represented by <code>this</code>
     * quaternion is applied.
     * <p>
     * This method is equivalent to the following code:
     * <pre>
     * Quaternionfc inv = new Quaternionfc(this).invert();
     * inv.transform(dir.set(1, 0, 0));
     * </pre>
     *
     * @param dir will hold the direction of <tt>+X</tt>
     * @return dir
     */
    Vector3fc positiveX(Vector3fc dir);

    /**
     * Obtain the direction of <tt>+X</tt> before the rotation transformation represented by <code>this</code>
     * <i>normalized</i> quaternion is applied. The quaternion <i>must</i> be {@link #normalize(Quaternionfc) normalized}
     * for this method to work.
     * <p>
     * This method is equivalent to the following code:
     * <pre>
     * Quaternionfc inv = new Quaternionfc(this).conjugate();
     * inv.transform(dir.set(1, 0, 0));
     * </pre>
     *
     * @param dir will hold the direction of <tt>+X</tt>
     * @return dir
     */
    Vector3fc normalizedPositiveX(Vector3fc dir);

    /**
     * Obtain the direction of <tt>+Y</tt> before the rotation transformation represented by <code>this</code>
     * quaternion is applied.
     * <p>
     * This method is equivalent to the following code:
     * <pre>
     * Quaternionfc inv = new Quaternionfc(this).invert();
     * inv.transform(dir.set(0, 1, 0));
     * </pre>
     *
     * @param dir will hold the direction of <tt>+Y</tt>
     * @return dir
     */
    Vector3fc positiveY(Vector3fc dir);

    /**
     * Obtain the direction of <tt>+Y</tt> before the rotation transformation represented by <code>this</code>
     * <i>normalized</i> quaternion is applied. The quaternion <i>must</i> be {@link #normalize(Quaternionfc) normalized}
     * for this method to work.
     * <p>
     * This method is equivalent to the following code:
     * <pre>
     * Quaternionfc inv = new Quaternionfc(this).conjugate();
     * inv.transform(dir.set(0, 1, 0));
     * </pre>
     *
     * @param dir will hold the direction of <tt>+Y</tt>
     * @return dir
     */
    Vector3fc normalizedPositiveY(Vector3fc dir);

    /**
     * Obtain the direction of <tt>+Z</tt> before the rotation transformation represented by <code>this</code>
     * quaternion is applied.
     * <p>
     * This method is equivalent to the following code:
     * <pre>
     * Quaternionfc inv = new Quaternionfc(this).invert();
     * inv.transform(dir.set(0, 0, 1));
     * </pre>
     *
     * @param dir will hold the direction of <tt>+Z</tt>
     * @return dir
     */
    Vector3fc positiveZ(Vector3fc dir);

    /**
     * Obtain the direction of <tt>+Z</tt> before the rotation transformation represented by <code>this</code>
     * <i>normalized</i> quaternion is applied. The quaternion <i>must</i> be {@link #normalize(Quaternionfc) normalized}
     * for this method to work.
     * <p>
     * This method is equivalent to the following code:
     * <pre>
     * Quaternionfc inv = new Quaternionfc(this).conjugate();
     * inv.transform(dir.set(0, 0, 1));
     * </pre>
     *
     * @param dir will hold the direction of <tt>+Z</tt>
     * @return dir
     */
    Vector3fc normalizedPositiveZ(Vector3fc dir);
}