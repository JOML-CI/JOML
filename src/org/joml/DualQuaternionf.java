/*
 * (C) Copyright 2016 Kai Burjack

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
 * Implementation of a Dual Quaternion using single-precision float components.
 * <p>
 * Reference: <a href="http://wscg.zcu.cz/wscg2012/short/A29-full.pdf">A Beginners Guide to Dual-Quaternions</a>
 * 
 * @author Kai Burjack
 */
public class DualQuaternionf {

    /**
     * The first component of the rotation vector part.
     */
    public float rx;
    /**
     * The second component of the rotation vector part.
     */
    public float ry;
    /**
     * The third component of the rotation vector part.
     */
    public float rz;
    /**
     * The real/scalar part of the rotation quaternion.
     */
    public float rw = 1.0f;

    /**
     * The first component of the translation vector part.
     */
    public float tx;
    /**
     * The second component of the translation vector part.
     */
    public float ty;
    /**
     * The third component of the translation vector part.
     */
    public float tz;
    /**
     * The real/scalar part of the translation quaternion.
     */
    public float tw;

    /**
     * Create a new {@link DualQuaternionf} and initialize it to the identity transformation.
     */
    public DualQuaternionf() {
    }

    /**
     * Create a new {@link DualQuaternionf} by copying the values from the given <code>other</code> dual quaternion.
     * 
     * @param other
     *          the {@link DualQuaternionf} to copy the values from
     */
    public DualQuaternionf(DualQuaternionf other) {
        MemUtil.INSTANCE.copy(other, this);
    }

    /**
     * Set this dual quaternion to identity.
     * 
     * @return this
     */
    public DualQuaternionf identity() {
        MemUtil.INSTANCE.identity(this);
        return this;
    }

    /**
     * Copy the values of the given <code>src</code> dual quaternion into <code>this</code>.
     * 
     * @param src
     *          the {@link DualQuaternionf} to copy its values from
     * @return this
     */
    public DualQuaternionf set(DualQuaternionf src) {
        MemUtil.INSTANCE.copy(src, this);
        return this;
    }

    /**
     * Set this {@link DualQuaternionf} to a translation of the vector <tt>(x, y, z)</tt>.
     * 
     * @param x
     *          the translation along the x axis
     * @param y
     *          the translation along the y axis
     * @param z
     *          the translation along the z axis
     * @return this
     */
    public DualQuaternionf translation(float x, float y, float z) {
        rx = 0.0f;
        ry = 0.0f;
        rz = 0.0f;
        rw = 1.0f;
        tx = x * 0.5f;
        ty = y * 0.5f;
        tz = z * 0.5f;
        tw = 0.0f;
        return this;
    }

    /**
     * Apply rotation about the X axis to this dual quaternion by rotating the given amount of radians 
     * and store the result in <code>dest</code>.
     * <p>
     * When used with a right-handed coordinate system, the produced rotation will rotate a vector 
     * counter-clockwise around the rotation axis, when viewing along the negative axis direction towards the origin.
     * When used with a left-handed coordinate system, the rotation is clockwise.
     * <p>
     * If <code>Q</code> is <code>this</code> dual quaternion and <code>R</code> the rotation dual quaternion,
     * then the new dual quaternion will be <code>Q * R</code>. So when transforming a
     * vector <code>v</code> with the new dual quaternion by using <code>Q * R * v</code>, the
     * rotation will be applied first!
     * 
     * @param angle
     *            the angle in radians
     * @param dest
     *            will hold the result
     * @return dest
     */
    public DualQuaternionf rotateX(float angle, DualQuaternionf dest) {
        float cos = (float) Math.cos(angle * 0.5);
        float sin = (float) Math.sin(angle * 0.5);
        float rrw = cos;
        float rrx = sin;
        float nrw = rw*rrw - rx*rrx;
        float nrx = rw*rrx + rx*rrw;
        float nry = ry*rrw + rz*rrx;
        float nrz = rz*rrw - ry*rrx;
        float ntx = tx*rrw + tw*rrx;
        float nty = ty*rrw + tz*rrx;
        float ntz = tz*rrw - ty*rrx;
        float ntw = tw*rrw - tx*rrx;
        dest.rx = nrx;
        dest.ry = nry;
        dest.rz = nrz;
        dest.rw = nrw;
        dest.tx = ntx;
        dest.ty = nty;
        dest.tz = ntz;
        dest.tw = ntw;
        return dest;
    }

    /**
     * Apply rotation about the X axis to this dual quaternion by rotating the given amount of radians.
     * <p>
     * When used with a right-handed coordinate system, the produced rotation will rotate a vector 
     * counter-clockwise around the rotation axis, when viewing along the negative axis direction towards the origin.
     * When used with a left-handed coordinate system, the rotation is clockwise.
     * <p>
     * If <code>Q</code> is <code>this</code> dual quaternion and <code>R</code> the rotation dual quaternion,
     * then the new dual quaternion will be <code>Q * R</code>. So when transforming a
     * vector <code>v</code> with the new dual quaternion by using <code>Q * R * v</code>, the
     * rotation will be applied first!
     * 
     * @param angle
     *            the angle in radians
     * @return this
     */
    public DualQuaternionf rotateX(float angle) {
        return rotateX(angle, this);
    }

    /**
     * Apply rotation about the Y axis to this dual quaternion by rotating the given amount of radians 
     * and store the result in <code>dest</code>.
     * <p>
     * When used with a right-handed coordinate system, the produced rotation will rotate a vector 
     * counter-clockwise around the rotation axis, when viewing along the negative axis direction towards the origin.
     * When used with a left-handed coordinate system, the rotation is clockwise.
     * <p>
     * If <code>Q</code> is <code>this</code> dual quaternion and <code>R</code> the rotation dual quaternion,
     * then the new dual quaternion will be <code>Q * R</code>. So when transforming a
     * vector <code>v</code> with the new dual quaternion by using <code>Q * R * v</code>, the
     * rotation will be applied first!
     * 
     * @param angle
     *            the angle in radians
     * @param dest
     *            will hold the result
     * @return dest
     */
    public DualQuaternionf rotateY(float angle, DualQuaternionf dest) {
        float cos = (float) Math.cos(angle * 0.5);
        float sin = (float) Math.sin(angle * 0.5);
        float rrw = cos;
        float rry = sin;
        float nrw = rw*rrw - ry*rry;
        float nrx = rx*rrw - rz*rry;
        float nry = rw*rry + ry*rrw;
        float nrz = rz*rrw + rx*rry;
        float ntx = tx*rrw - tz*rry;
        float nty = ty*rrw + tw*rry;
        float ntz = tz*rrw + tx*rry;
        float ntw = tw*rrw - ty*rry;
        dest.rx = nrx;
        dest.ry = nry;
        dest.rz = nrz;
        dest.rw = nrw;
        dest.tx = ntx;
        dest.ty = nty;
        dest.tz = ntz;
        dest.tw = ntw;
        return dest;
    }

    /**
     * Apply rotation about the Y axis to this dual quaternion by rotating the given amount of radians.
     * <p>
     * When used with a right-handed coordinate system, the produced rotation will rotate a vector 
     * counter-clockwise around the rotation axis, when viewing along the negative axis direction towards the origin.
     * When used with a left-handed coordinate system, the rotation is clockwise.
     * <p>
     * If <code>Q</code> is <code>this</code> dual quaternion and <code>R</code> the rotation dual quaternion,
     * then the new dual quaternion will be <code>Q * R</code>. So when transforming a
     * vector <code>v</code> with the new dual quaternion by using <code>Q * R * v</code>, the
     * rotation will be applied first!
     * 
     * @param angle
     *            the angle in radians
     * @return this
     */
    public DualQuaternionf rotateY(float angle) {
        return rotateY(angle, this);
    }

    /**
     * Apply rotation about the Z axis to this dual quaternion by rotating the given amount of radians 
     * and store the result in <code>dest</code>.
     * <p>
     * When used with a right-handed coordinate system, the produced rotation will rotate a vector 
     * counter-clockwise around the rotation axis, when viewing along the negative axis direction towards the origin.
     * When used with a left-handed coordinate system, the rotation is clockwise.
     * <p>
     * If <code>Q</code> is <code>this</code> dual quaternion and <code>R</code> the rotation dual quaternion,
     * then the new dual quaternion will be <code>Q * R</code>. So when transforming a
     * vector <code>v</code> with the new dual quaternion by using <code>Q * R * v</code>, the
     * rotation will be applied first!
     * 
     * @param angle
     *            the angle in radians
     * @param dest
     *            will hold the result
     * @return dest
     */
    public DualQuaternionf rotateZ(float angle, DualQuaternionf dest) {
        float cos = (float) Math.cos(angle * 0.5);
        float sin = (float) Math.sin(angle * 0.5);
        float rrw = cos;
        float rrz = sin;
        float nrw = rw*rrw - rz*rrz;
        float nrx = rx*rrw + ry*rrz;
        float nry = ry*rrw - rx*rrz;
        float nrz = rw*rrz + rz*rrw;
        float ntx = tx*rrw + ty*rrz;
        float nty = ty*rrw - tx*rrz;
        float ntz = tz*rrw + tw*rrz;
        float ntw = tw*rrw - tz*rrz;
        dest.rx = nrx;
        dest.ry = nry;
        dest.rz = nrz;
        dest.rw = nrw;
        dest.tx = ntx;
        dest.ty = nty;
        dest.tz = ntz;
        dest.tw = ntw;
        return dest;
    }

    /**
     * Apply rotation about the Z axis to this dual quaternion by rotating the given amount of radians.
     * <p>
     * When used with a right-handed coordinate system, the produced rotation will rotate a vector 
     * counter-clockwise around the rotation axis, when viewing along the negative axis direction towards the origin.
     * When used with a left-handed coordinate system, the rotation is clockwise.
     * <p>
     * If <code>Q</code> is <code>this</code> dual quaternion and <code>R</code> the rotation dual quaternion,
     * then the new dual quaternion will be <code>Q * R</code>. So when transforming a
     * vector <code>v</code> with the new dual quaternion by using <code>Q * R * v</code>, the
     * rotation will be applied first!
     * 
     * @param angle
     *            the angle in radians
     * @return this
     */
    public DualQuaternionf rotateZ(float angle) {
        return rotateY(angle, this);
    }

    /**
     * Set this dual quaternion to represent a rotation of the given radians about the X axis.
     * 
     * @param angle
     *              the angle in radians to rotate about the X axis
     * @return this
     */
    public DualQuaternionf rotationX(float angle) {
        MemUtil.INSTANCE.zero(this);
        float cos = (float) Math.cos(angle * 0.5);
        float sin = (float) Math.sin(angle * 0.5);
        rw = cos;
        rx = sin;
        return this;
    }

    /**
     * Set this dual quaternion to represent a rotation of the given radians about the Y axis.
     * 
     * @param angle
     *              the angle in radians to rotate about the Y axis
     * @return this
     */
    public DualQuaternionf rotationY(float angle) {
        MemUtil.INSTANCE.zero(this);
        float cos = (float) Math.cos(angle * 0.5);
        float sin = (float) Math.sin(angle * 0.5);
        rw = cos;
        ry = sin;
        return this;
    }

    /**
     * Set this dual quaternion to represent a rotation of the given radians about the Z axis.
     * 
     * @param angle
     *              the angle in radians to rotate about the Z axis
     * @return this
     */
    public DualQuaternionf rotationZ(float angle) {
        MemUtil.INSTANCE.zero(this);
        float cos = (float) Math.cos(angle * 0.5);
        float sin = (float) Math.sin(angle * 0.5);
        rw = cos;
        rz = sin;
        return this;
    }

    /**
     * Apply translation to this dual quaternion by translating by the given vector <tt>(x, y, z)</tt>
     * and store the result in <code>dest</code>.
     * <p>
     * If <code>Q</code> is <code>this</code> dual quaternion and <code>T</code> the translation dual quaternion,
     * then the new dual quaternion will be <code>Q * T</code>. So when transforming a
     * vector <code>v</code> with the new dual quaternion by using <code>Q * T * v</code>, the
     * translation will be applied first!
     * 
     * @param x
     *            the translation along the x axis
     * @param y
     *            the translation along the y axis
     * @param z
     *            the translation along the z axis
     * @param dest
     *            will hold the result
     * @return dest
     */
    public DualQuaternionf translate(float x, float y, float z, DualQuaternionf dest) {
        float rtx = x * 0.5f;
        float rty = y * 0.5f;
        float rtz = z * 0.5f;
        float nrw = rw;
        float nrx = rx;
        float nry = ry;
        float nrz = rz;
        float ntx = tx + rw*rtx + ry*rtz - rz*rty;
        float nty = ty + rw*rty - rx*rtz + rz*rtx;
        float ntz = tz + rw*rtz + rx*rty - ry*rtx;
        float ntw = tw - rx*rtx - ry*rty - rz*rtz;
        dest.rx = nrx;
        dest.ry = nry;
        dest.rz = nrz;
        dest.rw = nrw;
        dest.tx = ntx;
        dest.ty = nty;
        dest.tz = ntz;
        dest.tw = ntw;
        return dest;
    }

    /**
     * Apply translation to this dual quaternion by translating by the given vector <tt>(x, y, z)</tt>.
     * <p>
     * If <code>Q</code> is <code>this</code> dual quaternion and <code>T</code> the translation dual quaternion,
     * then the new dual quaternion will be <code>Q * T</code>. So when transforming a
     * vector <code>v</code> with the new dual quaternion by using <code>Q * T * v</code>, the
     * translation will be applied first!
     * 
     * @param x
     *            the translation along the x axis
     * @param y
     *            the translation along the y axis
     * @param z
     *            the translation along the z axis
     * @return this
     */
    public DualQuaternionf translate(float x, float y, float z) {
        return translate(x, y, z, this);
    }

    /**
     * Conjugate this dual quaternion and store the result in <code>dest</code>.
     * 
     * @param dest
     *          will hold the result
     * @return dest
     */
    public DualQuaternionf conjugate(DualQuaternionf dest) {
        dest.rx = -rx;
        dest.ry = -ry;
        dest.rz = -rz;
        dest.rw = rw;
        dest.tx = -tx;
        dest.ty = -ty;
        dest.tz = -tz;
        dest.tw = tw;
        return dest;
    }

    /**
     * Conjugate this dual quaternion.
     * 
     * @return this
     */
    public DualQuaternionf conjugate() {
        return conjugate(this);
    }

    /**
     * Multiply this dual quaternion by <code>right</code> and store the result in <code>dest</code>.
     * <p>
     * If <tt>T</tt> is <code>this</code> and <tt>Q</tt> is the given
     * quaternion, then the resulting dual quaternion <tt>R</tt> is:
     * <p>
     * <tt>R = T * Q</tt>
     * <p>
     * So, this method uses post-multiplication like the matrix classes, resulting in a
     * vector to be transformed by <tt>Q</tt> first, and then by <tt>T</tt>.
     * 
     * @param right
     *            the dual quaternion to multiply <code>this</code> by
     * @param dest
     *            will hold the result
     * @return dest
     */
    public DualQuaternionf mul(DualQuaternionf right, DualQuaternionf dest) {
        float nrw = rw*right.rw - rx*right.rx - ry*right.ry - rz*right.rz;
        float nrx = rw*right.rx + rx*right.rw + ry*right.rz - rz*right.ry;
        float nry = rw*right.ry + ry*right.rw - rx*right.rz + rz*right.rx;
        float nrz = rw*right.rz + rz*right.rw + rx*right.ry - ry*right.rx;
        float ntx = tx*right.rw + rw*right.tx + tw*right.rx + rx*right.tw -
                    tz*right.ry + ry*right.tz + ty*right.rz - rz*right.ty;
        float nty = ty*right.rw + rw*right.ty + tz*right.rx - rx*right.tz +
                    tw*right.ry + ry*right.tw - tx*right.rz + rz*right.tx;
        float ntz = tz*right.rw + rw*right.tz - ty*right.rx + rx*right.ty +
                    tx*right.ry - ry*right.tx + tw*right.rz + rz*right.tw;
        float ntw = tw*right.rw + rw*right.tw - rx*right.tx - tx*right.rx -
                    ry*right.ty - ty*right.ry - rz*right.tz - tz*right.rz;
        dest.rx = nrx;
        dest.ry = nry;
        dest.rz = nrz;
        dest.rw = nrw;
        dest.tx = ntx;
        dest.ty = nty;
        dest.tz = ntz;
        dest.tw = ntw;
        return dest;
    }

    /**
     * Multiply this dual quaternion by <code>right</code>.
     * <p>
     * If <tt>T</tt> is <code>this</code> and <tt>Q</tt> is the given
     * quaternion, then the resulting dual quaternion <tt>R</tt> is:
     * <p>
     * <tt>R = T * Q</tt>
     * <p>
     * So, this method uses post-multiplication like the matrix classes, resulting in a
     * vector to be transformed by <tt>Q</tt> first, and then by <tt>T</tt>.
     * 
     * @param right
     *            the dual quaternion to multiply <code>this</code> by
     * @return this
     */
    public DualQuaternionf mul(DualQuaternionf right) {
        return mul(right, this);
    }

    /**
     * Store the 4x4 float matrix representation of <code>this</code> dual quaternion in column-major order into the given {@link ByteBuffer}.
     * <p>
     * This is equivalent to calling: <code>this.get(new Matrix4f()).get(dest)</code>
     * 
     * @param dest
     *            the destination buffer
     * @return dest
     */
    public ByteBuffer getAsMatrix4f(ByteBuffer dest) {
        MemUtil.INSTANCE.putMatrix4f(this, dest.position(), dest);
        return dest;
    }

    /**
     * Store the 4x4 float matrix representation of <code>this</code> dual quaternion in column-major order into the given {@link FloatBuffer}.
     * <p>
     * This is equivalent to calling: <code>this.get(new Matrix4f()).get(dest)</code>
     * 
     * @param dest
     *            the destination buffer
     * @return dest
     */
    public FloatBuffer getAsMatrix4f(FloatBuffer dest) {
        MemUtil.INSTANCE.putMatrix4f(this, dest.position(), dest);
        return dest;
    }

    /**
     * Store the 4x3 float matrix representation of <code>this</code> dual quaternion in column-major order into the given {@link ByteBuffer}.
     * <p>
     * This is equivalent to calling: <code>this.get(new Matrix4x3f()).get(dest)</code>
     * 
     * @param dest
     *            the destination buffer
     * @return dest
     */
    public ByteBuffer getAsMatrix4x3f(ByteBuffer dest) {
        MemUtil.INSTANCE.putMatrix4x3f(this, dest.position(), dest);
        return dest;
    }

    /**
     * Store the 4x3 float matrix representation of <code>this</code> dual quaternion in column-major order into the given {@link FloatBuffer}.
     * <p>
     * This is equivalent to calling: <code>this.get(new Matrix4x3f()).get(dest)</code>
     * 
     * @param dest
     *            the destination buffer
     * @return dest
     */
    public FloatBuffer getAsMatrix4x3f(FloatBuffer dest) {
        MemUtil.INSTANCE.putMatrix4x3f(this, dest.position(), dest);
        return dest;
    }

    /**
     * Set the given matrix to the rotation and translation represented by <code>this</code>.
     * 
     * @param dest
     *          the matrix to write the transformation into
     * @return dest
     */
    public Matrix4f get(Matrix4f dest) {
        float dqx = rx + rx;
        float dqy = ry + ry;
        float dqz = rz + rz;
        float q00 = dqx * rx;
        float q11 = dqy * ry;
        float q22 = dqz * rz;
        float q01 = dqx * ry;
        float q02 = dqx * rz;
        float q03 = dqx * rw;
        float q12 = dqy * rz;
        float q13 = dqy * rw;
        float q23 = dqz * rw;
        dest.m00 = 1.0f - q11 - q22;
        dest.m01 = q01 + q23;
        dest.m02 = q02 - q13;
        dest.m03 = 0.0f;
        dest.m10 = q01 - q23;
        dest.m11 = 1.0f - q22 - q00;
        dest.m12 = q12 + q03;
        dest.m13 = 0.0f;
        dest.m20 = q02 + q13;
        dest.m21 = q12 - q03;
        dest.m22 = 1.0f - q11 - q00;
        dest.m23 = 0.0f;
        dest.m30 = 2.0f * (rw*tx - rx*tw + ry*tz - rz*ty);
        dest.m31 = 2.0f * (rw*ty - ry*tw - rx*tz + rz*tx);
        dest.m32 = 2.0f * (rw*tz - rz*tw + rx*ty - ry*tx);
        dest.m33 = 1.0f;
        return dest;
    }

    /**
     * Set the given matrix to the rotation and translation represented by <code>this</code>.
     * 
     * @param dest
     *          the matrix to write the transformation into
     * @return dest
     */
    public Matrix4x3f get(Matrix4x3f dest) {
        float dqx = rx + rx;
        float dqy = ry + ry;
        float dqz = rz + rz;
        float q00 = dqx * rx;
        float q11 = dqy * ry;
        float q22 = dqz * rz;
        float q01 = dqx * ry;
        float q02 = dqx * rz;
        float q03 = dqx * rw;
        float q12 = dqy * rz;
        float q13 = dqy * rw;
        float q23 = dqz * rw;
        dest.m00 = 1.0f - q11 - q22;
        dest.m01 = q01 + q23;
        dest.m02 = q02 - q13;
        dest.m10 = q01 - q23;
        dest.m11 = 1.0f - q22 - q00;
        dest.m12 = q12 + q03;
        dest.m20 = q02 + q13;
        dest.m21 = q12 - q03;
        dest.m22 = 1.0f - q11 - q00;
        dest.m30 = 2.0f * (rw*tx - rx*tw + ry*tz - rz*ty);
        dest.m31 = 2.0f * (rw*ty - ry*tw - rx*tz + rz*tx);
        dest.m32 = 2.0f * (rw*tz - rz*tw + rx*ty - ry*tx);
        return dest;
    }

    /**
     * Invert this dual quaternion and store the result in <code>dest</code>.
     * 
     * @param dest
     *          will hold the result
     * @return dest
     */
    public DualQuaternionf invert(DualQuaternionf dest) {
        float realNorm = rx*rx + ry*ry + rz*rz + rw*rw;
        float dualNorm = rx*tx + ry*ty + rz*tz + rw*tw;
        float f = realNorm - 2.0f * dualNorm;
        dest.rx = -rx * realNorm;
        dest.ry = -ry * realNorm;
        dest.rz = -rz * realNorm;
        dest.rw =  rw * realNorm;
        dest.tx = -tx * f;
        dest.ty = -ty * f;
        dest.tz = -tz * f;
        dest.tw =  tw * f;
        return dest;
    }

    /**
     * Invert this dual quaternion.
     * 
     * @return this
     */
    public DualQuaternionf invert() {
        return invert(this);
    }

    /**
     * Return a string representation of this dual quaternion.
     * <p>
     * This method creates a new {@link DecimalFormat} on every invocation with the format string "<tt>  0.000E0; -</tt>".
     * 
     * @return the string representation
     */
    public String toString() {
        DecimalFormat formatter = new DecimalFormat(" 0.000E0;-");
        return toString(formatter).replaceAll("E(\\d+)", "E+$1");
    }

    /**
     * Return a string representation of this dual quaternion by formatting the components with the given {@link NumberFormat}.
     * 
     * @param formatter
     *          the {@link NumberFormat} used to format the dual quaternion components with
     * @return the string representation
     */
    public String toString(NumberFormat formatter) {
        return "(" + formatter.format(rx) + " " + formatter.format(ry) + " " + formatter.format(rz) + " " + formatter.format(rw) + " | "
                   + formatter.format(tx) + " " + formatter.format(ty) + " " + formatter.format(tz) + " " + formatter.format(tw) + ")";
    }

}
