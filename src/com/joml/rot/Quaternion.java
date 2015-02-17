/*
 * Feel free to do whatever you want with this code, all I've done is
 * pull together common knowledge into one easy package. Use it as a
 * base for your own work, copy/paste bits or integrate it into your
 * existing project, it's all good. Just add a thanks to me somewhere.
 */
package com.joml.rot;

import com.joml.matrix.Matrix4f;
import com.joml.utils.TrigMath;
import com.joml.vector.Vector3f;
import static java.lang.Math.PI;
import java.nio.FloatBuffer;

/**
 * Quaternion
 *
 * Contains the definition and functions for rotations expressed as
 * 4-dimensional vectors
 *
 * @author Richard Greenlees
 */
public class Quaternion {

    public static final Quaternion IDENTITY = new Quaternion(0.0f, 0.0f, 0.0f, 1.0f);

    public float x;
    public float y;
    public float z;
    public float w;

    public Quaternion() {
        x = 0.0f;
        y = 0.0f;
        z = 0.0f;
        w = 1.0f;
    }

    public Quaternion(float newX, float newY, float newZ, float newW) {
        x = newX;
        y = newY;
        z = newZ;
        w = newW;
    }

    public Quaternion(float newX, float newY, float newZ) {
        x = newX;
        y = newY;
        z = newZ;
        w = 1.0f;
    }

    public Quaternion(Quaternion source) {
        x = source.x;
        y = source.y;
        z = source.z;
        w = source.w;
    }

    /**
     * Normalize this Quaternion
     */
    public void normalize() {
        float norm = (float) Math.sqrt(x * x + y * y + z * z + w * w);

        x /= norm;
        y /= norm;
        z /= norm;
        w /= norm;
    }

    /**
     * Normalizes the supplied Quaternion source and stores the results in dest.
     * Does not modify the source
     */
    public static void normalize(Quaternion source, Quaternion dest) {
        float norm = (float) Math.sqrt(source.x * source.x + source.y * source.y + source.z * source.z + source.w * source.w);

        dest.x = source.x / norm;
        dest.y = source.y / norm;
        dest.z = source.z / norm;
        dest.w = source.w / norm;
    }

    /**
     * Adds q2 to this quaternion
     */
    public void add(Quaternion q2) {
        x += q2.x;
        y += q2.y;
        z += q2.z;
        w += q2.w;
    }

    /**
     * Add q2 to q1 and store the results in dest. Does not modify q1 or q2
     */
    public static void add(Quaternion q1, Quaternion q2, Quaternion dest) {
        dest.x = q1.x + q2.x;
        dest.y = q1.y + q2.y;
        dest.z = q1.z + q2.z;
        dest.w = q1.w + q2.w;
    }

    /**
     * Returns the dot of this Quaternion and otherQuat
     */
    public float dot(Quaternion otherQuat) {
        return this.x * otherQuat.x + this.y * otherQuat.y + this.z * otherQuat.z + this.w * otherQuat.w;
    }

    /**
     * Returns the dot product of a and b
     */
    public static float dot(Quaternion a, Quaternion b) {
        return a.x * b.x + a.y * b.y + a.z * b.z + a.w * b.w;
    }

    /**
     * Finds the angle represented by this Quaternion in degrees
     */
    public final float getAngle() {
        final float angle = 2.0f * (float) Math.acos(w);
        return (angle <= PI) ? angle : 2.0f * (float) PI - angle;
    }

    /**
     * Finds the angle represented by q in degrees
     */
    public static float getAngle(Quaternion q) {
        final float angle = 2.0f * (float) Math.acos(q.w);
        return (angle <= PI) ? angle : 2.0f * (float) PI - angle;
    }

    /**
     * Generates a rotation matrix from q and stores the results in dest
     */
    public static final void getMatrix(Quaternion q, Matrix4f dest) {
        float q00 = 2.0f * q.x * q.x;
        float q11 = 2.0f * q.y * q.y;
        float q22 = 2.0f * q.z * q.z;

        float q01 = 2.0f * q.x * q.y;
        float q02 = 2.0f * q.x * q.z;
        float q03 = 2.0f * q.x * q.w;

        float q12 = 2.0f * q.y * q.z;
        float q13 = 2.0f * q.y * q.w;

        float q23 = 2.0f * q.z * q.w;

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
        dest.m30 = 0.0f;
        dest.m31 = 0.0f;
        dest.m32 = 0.0f;
        dest.m33 = 1.0f;
    }

    /**
     * Generates a rotation matrix from this Quaternion and stores the results
     * in dest
     */
    public static final void getMatrix(Quaternion quat, FloatBuffer dest) {
        float q00 = 2.0f * quat.x * quat.x;
        float q11 = 2.0f * quat.y * quat.y;
        float q22 = 2.0f * quat.z * quat.z;

        float q01 = 2.0f * quat.x * quat.y;
        float q02 = 2.0f * quat.x * quat.z;
        float q03 = 2.0f * quat.x * quat.w;

        float q12 = 2.0f * quat.y * quat.z;
        float q13 = 2.0f * quat.y * quat.w;

        float q23 = 2.0f * quat.z * quat.w;

        dest.put(1.0f - q11 - q22);
        dest.put(q01 + q23);
        dest.put(q02 - q13);
        dest.put(0.0f);
        dest.put(q01 - q23);
        dest.put(1.0f - q22 - q00);
        dest.put(q12 + q03);
        dest.put(0.0f);
        dest.put(q02 + q13);
        dest.put(q12 - q03);
        dest.put(1.0f - q11 - q00);
        dest.put(0.0f);
        dest.put(0.0f);
        dest.put(0.0f);
        dest.put(1.0f);
    }

    /**
     * Generates a rotation matrix from this Quaternion and stores the results
     * in dest
     */
    public final void getMatrix(Matrix4f dest) {
        float q00 = 2.0f * this.x * this.x;
        float q11 = 2.0f * this.y * this.y;
        float q22 = 2.0f * this.z * this.z;

        float q01 = 2.0f * this.x * this.y;
        float q02 = 2.0f * this.x * this.z;
        float q03 = 2.0f * this.x * this.w;

        float q12 = 2.0f * this.y * this.z;
        float q13 = 2.0f * this.y * this.w;

        float q23 = 2.0f * this.z * this.w;

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
        dest.m30 = 0.0f;
        dest.m31 = 0.0f;
        dest.m32 = 0.0f;
        dest.m33 = 1.0f;
    }

    /**
     * Generates a rotation matrix from this Quaternion and stores the results
     * in dest
     */
    public final void getMatrix(FloatBuffer dest) {
        float q00 = 2.0f * this.x * this.x;
        float q11 = 2.0f * this.y * this.y;
        float q22 = 2.0f * this.z * this.z;

        float q01 = 2.0f * this.x * this.y;
        float q02 = 2.0f * this.x * this.z;
        float q03 = 2.0f * this.x * this.w;

        float q12 = 2.0f * this.y * this.z;
        float q13 = 2.0f * this.y * this.w;

        float q23 = 2.0f * this.z * this.w;

        dest.put(1.0f - q11 - q22);
        dest.put(q01 + q23);
        dest.put(q02 - q13);
        dest.put(0.0f);
        dest.put(q01 - q23);
        dest.put(1.0f - q22 - q00);
        dest.put(q12 + q03);
        dest.put(0.0f);
        dest.put(q02 + q13);
        dest.put(q12 - q03);
        dest.put(1.0f - q11 - q00);
        dest.put(0.0f);
        dest.put(0.0f);
        dest.put(0.0f);
        dest.put(1.0f);
    }

    /**
     * Sets this Quaternion to the new values
     */
    public void set(float newX, float newY, float newZ, float newW) {
        x = newX;
        y = newY;
        z = newZ;
        w = newW;
    }

    /**
     * Set the x, y and z components of this Quaternion to the new values
     */
    public void set(float newX, float newY, float newZ) {
        x = newX;
        y = newY;
        z = newZ;
    }

    /**
     * Sets this Quaternion to be a copy of q. Does not modify q
     */
    public void set(Quaternion q) {
        x = q.x;
        y = q.y;
        z = q.z;
        w = q.w;
    }

    /**
     * Sets this Quaternion to be a representation of the supplied axis and
     * angle (in Radians)
     */
    public void fromAxisAngleRad(Vector3f axis, float angle) {
        float hangle = angle / 2.0f;
        float sinAngle = (float) Math.sin(hangle);
        float vLength = axis.length();

        x = (axis.x / vLength) * sinAngle;
        y = (axis.y / vLength) * sinAngle;
        z = (axis.z / vLength) * sinAngle;
        w = (float) Math.cos(hangle);
    }

    /**
     * Sets this Quaternion to be a representation of the supplied axis and
     * angle (in Radians)
     */
    public void fromAxisAngleRad(float axisX, float axisY, float axisZ, float angle) {
        float hangle = angle / 2.0f;
        float sinAngle = (float) Math.sin(hangle);
        float vLength = (float) Math.sqrt(axisX * axisX + axisY * axisY + axisZ * axisZ);

        x = (axisX / vLength) * sinAngle;
        y = (axisY / vLength) * sinAngle;
        z = (axisZ / vLength) * sinAngle;
        w = (float) Math.cos(hangle);
    }

    /**
     * Sets this Quaternion to be a representation of the supplied axis and
     * angle (in Degrees)
     */
    public void fromAxisAngleDeg(Vector3f axis, float angle) {
        float hangle = (angle * TrigMath.degreesToRadians) / 2.0f;
        float sinAngle = (float) Math.sin(hangle);
        float vLength = axis.length();

        x = (axis.x / vLength) * sinAngle;
        y = (axis.y / vLength) * sinAngle;
        z = (axis.z / vLength) * sinAngle;
        w = (float) Math.cos(hangle);
    }

    /**
     * Multiply this Quaternion by q
     */
    public void mul(Quaternion q) {
        set(x = this.x * q.x - this.y * q.y - this.z * q.z - this.w * q.w,
                y = this.x * q.y + this.y * q.x + this.z * q.w - this.w * q.z,
                z = this.x * q.z - this.y * q.w + this.z * q.x + this.w * q.y,
                w = this.x * q.w + this.y * q.z - this.z * q.y + this.w * q.x);
    }

    /**
     * Multiply a by b and store the results in dest.
     * <B>This is not alias safe so make sure dest is not the same as a or b or
     * you WILL get incorrect results!</B>
     */
    public static void mulFast(Quaternion a, Quaternion b, Quaternion dest) {
        dest.x = a.x * b.x - a.y * b.y - a.z * b.z - a.w * b.w;
        dest.y = a.x * b.y + a.y * b.x + a.z * b.w - a.w * b.z;
        dest.z = a.x * b.z - a.y * b.w + a.z * b.x + a.w * b.y;
        dest.w = a.x * b.w + a.y * b.z - a.z * b.y + a.w * b.x;
    }

    /**
     * Multiply a by b and store the results in dest.
     */
    public static void mul(Quaternion a, Quaternion b, Quaternion dest) {
        dest.set(a.x * b.x - a.y * b.y - a.z * b.z - a.w * b.w,
                a.x * b.y + a.y * b.x + a.z * b.w - a.w * b.z,
                a.x * b.z - a.y * b.w + a.z * b.x + a.w * b.y,
                a.x * b.w + a.y * b.z - a.z * b.y + a.w * b.x);
    }

    /**
     * Invert this Quaternion
     */
    public void invert() {
        float norm = (x * x + y * y + z * z + w * w);
        x = x / norm;
        y = -y / norm;
        z = -z / norm;
        w = -w / norm;
    }

    /**
     * Inverts q and stores the results in dest. Does not modify q
     */
    public static void invert(Quaternion q, Quaternion dest) {
        float norm = (q.x * q.x + q.y * q.y + q.z * q.z + q.w * q.w);
        dest.x = q.x / norm;
        dest.y = -q.y / norm;
        dest.z = -q.z / norm;
        dest.w = -q.w / norm;
    }

    /**
     * Divides this Quaternion by b
     */
    public void div(Quaternion b) {
        // TODO: Remove method calls
        invert();
        mul(b);
    }

    /**
     * Divides a by b and stores the results in dest. Does not modify a or b
     */
    public static void div(Quaternion a, Quaternion b, Quaternion dest) {
        dest.x = a.x;
        dest.y = a.y;
        dest.z = a.z;
        dest.w = a.w;

        dest.invert();
        dest.mul(b);
    }

    /**
     * Conjugates this Quaternion
     */
    public void conjugate() {
        y = -y;
        z = -z;
        w = -w;
    }

    /**
     * Conjugates a and stores the results in dest. Does not modify a
     */
    public static void conjugate(Quaternion a, Quaternion dest) {
        dest.x = a.x;
        dest.y = -a.y;
        dest.z = -a.z;
        dest.w = -a.w;
    }

    /**
     * Set this Quaternion to the identity
     */
    public void identity() {
        x = 0.0f;
        y = 0.0f;
        z = 0.0f;
        w = 1.0f;
    }

    /**
     * Calculate this Quaternion using the supplied Vector3f angles (in degrees)
     */
    public void setEulerAnglesDeg(Vector3f angles) {
        final float sp = (float) Math.sin((angles.x * TrigMath.degreesToRadians) * 0.5f);
        final float cp = (float) Math.cos((angles.x * TrigMath.degreesToRadians) * 0.5f);
        final float sy = (float) Math.sin((angles.y * TrigMath.degreesToRadians) * 0.5f);
        final float cy = (float) Math.cos((angles.y * TrigMath.degreesToRadians) * 0.5f);
        final float sr = (float) Math.sin((angles.z * TrigMath.degreesToRadians) * 0.5f);
        final float cr = (float) Math.cos((angles.z * TrigMath.degreesToRadians) * 0.5f);

        final float cysp = cy * sp;
        final float sycp = sy * cp;
        final float cycp = cy * cp;
        final float sysp = sy * sp;

        x = (cysp * cr) + (sycp * sr);
        y = (sycp * cr) - (cysp * sr);
        z = (cycp * sr) - (sysp * cr);
        w = (cycp * cr) + (sysp * sr);
    }

    /**
     * Calculate this Quaternion using the supplied Vector3f angles (in Radians)
     */
    public void setEulerAnglesRad(Vector3f angles) {
        final float sp = (float) Math.sin(angles.x * 0.5f);
        final float cp = (float) Math.cos(angles.x * 0.5f);
        final float sy = (float) Math.sin(angles.y * 0.5f);
        final float cy = (float) Math.cos(angles.y * 0.5f);
        final float sr = (float) Math.sin(angles.z * 0.5f);
        final float cr = (float) Math.cos(angles.z * 0.5f);

        final float cysp = cy * sp;
        final float sycp = sy * cp;
        final float cycp = cy * cp;
        final float sysp = sy * sp;

        x = (cysp * cr) + (sycp * sr);
        y = (sycp * cr) - (cysp * sr);
        z = (cycp * sr) - (sysp * cr);
        w = (cycp * cr) + (sysp * sr);
    }

    /**
     * Calculate this Quaternion using the supplied pitch, yaw and roll angles
     * (in degrees)
     */
    public void setEulerAnglesDeg(float pitch, float yaw, float roll) {
        final float sp = (float) Math.sin((pitch * TrigMath.degreesToRadians) * 0.5f);
        final float cp = (float) Math.cos((pitch * TrigMath.degreesToRadians) * 0.5f);
        final float sy = (float) Math.sin((yaw * TrigMath.degreesToRadians) * 0.5f);
        final float cy = (float) Math.cos((yaw * TrigMath.degreesToRadians) * 0.5f);
        final float sr = (float) Math.sin((roll * TrigMath.degreesToRadians) * 0.5f);
        final float cr = (float) Math.cos((roll * TrigMath.degreesToRadians) * 0.5f);

        final float cysp = cy * sp;
        final float sycp = sy * cp;
        final float cycp = cy * cp;
        final float sysp = sy * sp;

        x = (cysp * cr) + (sycp * sr);
        y = (sycp * cr) - (cysp * sr);
        z = (cycp * sr) - (sysp * cr);
        w = (cycp * cr) + (sysp * sr);
    }

    /**
     * Returns the length of this quaternion
     */
    public float length() {
        return x * x + y * y + z * z + w * w;
    }

    /**
     * Returns the length of q
     */
    public static float length(Quaternion q) {
        return q.x * q.x + q.y * q.y + q.z * q.z + q.w * q.w;
    }

    /**
     * Calculate this Quaternion using the supplied pitch, yaw and roll angles
     * (in Radians)
     */
    public void setEulerAnglesRad(float pitch, float yaw, float roll) {
        final float sp = (float) Math.sin(pitch * 0.5f);
        final float cp = (float) Math.cos(pitch * 0.5f);
        final float sy = (float) Math.sin(yaw * 0.5f);
        final float cy = (float) Math.cos(yaw * 0.5f);
        final float sr = (float) Math.sin(roll * 0.5f);
        final float cr = (float) Math.cos(roll * 0.5f);

        final float cysp = cy * sp;
        final float sycp = sy * cp;
        final float cycp = cy * cp;
        final float sysp = sy * sp;

        x = (cycp * cr) + (sysp * sr);
        y = (sycp * cr) - (cysp * sr);
        z = (cysp * cr) + (sycp * sr);
        w = (cycp * sr) - (sysp * cr);
    }

    /**
     * Spherical linear interpolation between this Quaternion and the specified
     * target, using the specified alpha
     */
    public void slerp(Quaternion target, float alpha) {
        final float dot = Math.abs(this.x * target.x + this.y * target.y + this.z * target.z + this.w * target.w);
        float scale1, scale2;

        if ((1 - dot) > 0.1) {

            final float angle = (float) Math.acos(dot);
            final float sinAngle = 1f / (float) Math.sin(angle);

            scale1 = ((float) Math.sin((1f - alpha) * angle) * sinAngle);
            scale2 = ((float) Math.sin((alpha * angle)) * sinAngle);
        } else {
            scale1 = 1f - alpha;
            scale2 = alpha;
        }

        if (dot < 0.f) {
            scale2 = -scale2;
        }

        x = (scale1 * x) + (scale2 * target.x);
        y = (scale1 * y) + (scale2 * target.y);
        z = (scale1 * z) + (scale2 * target.z);
        w = (scale1 * w) + (scale2 * target.w);
    }

    /**
     * Spherical linear interpolation between the start and target Quaternions,
     * using the specified alpha, and storing the results in dest. Neither the
     * start or target are modified
     */
    public static void slerp(Quaternion start, Quaternion target, float alpha, Quaternion dest) {
        final float dot = Math.abs(start.x * target.x + start.y * target.y + start.z * target.z + start.w * target.w);
        float scale1, scale2;

        if ((1.0f - dot) > 0.1) {

            final float angle = (float) Math.acos(dot);
            final float sinAngle = 1f / (float) Math.sin(angle);

            scale1 = ((float) Math.sin((1f - alpha) * angle) * sinAngle);
            scale2 = ((float) Math.sin((alpha * angle)) * sinAngle);
        } else {
            scale1 = 1f - alpha;
            scale2 = alpha;
        }

        if (dot < 0.f) {
            scale2 = -scale2;
        }

        dest.x = (scale1 * start.x) + (scale2 * target.x);
        dest.y = (scale1 * start.y) + (scale2 * target.y);
        dest.z = (scale1 * start.z) + (scale2 * target.z);
        dest.w = (scale1 * start.w) + (scale2 * target.w);
    }

    /** Rotates dest to point towards destPoint, from the supplied sourcePoint.
     * Assumes a forward Vector of (0,0,1) and an up Vector of (0,1,0)  */
    public static void LookAt(Vector3f sourcePoint, Vector3f destPoint, Quaternion dest) {
        float dirX = destPoint.x - sourcePoint.x;
        float dirY = destPoint.y - sourcePoint.y;
        float dirZ = destPoint.z - sourcePoint.z;

        float length = (float) Math.sqrt(dirX * dirX + dirY * dirY + dirZ * dirZ);

        dirX /= length;
        dirY /= length;
        dirZ /= length;

        float dot = (Vector3f.forward.x * dirX) + (Vector3f.forward.y * dirY) + (Vector3f.forward.z * dirZ);

        if (Math.abs(dot - (-1.0f)) < 0.000001f) {
            dest.x = Vector3f.up.x;
            dest.y = Vector3f.up.y;
            dest.z = Vector3f.up.z;
            dest.w = (float) Math.PI;
            return;
        }

        if (Math.abs(dot - (1.0f)) < 0.000001f) {
            dest.x = 0.0f;
            dest.y = 0.0f;
            dest.z = 0.0f;
            dest.w = 1.0f;
            return;
        }

        float rotAngle = (float) Math.acos(dot);
        
        float rotAxisX = Vector3f.forward.y * dirZ - Vector3f.forward.z * dirY;
        float rotAxisY = Vector3f.forward.z * dirX - Vector3f.forward.x * dirZ;
        float rotAxisZ = Vector3f.forward.x * dirY - Vector3f.forward.y * dirX;

        length = (float) Math.sqrt(rotAxisX * rotAxisX + rotAxisY * rotAxisY + rotAxisZ * rotAxisZ);

        rotAxisX /= length;
        rotAxisY /= length;
        rotAxisZ /= length;

        dest.fromAxisAngleRad(rotAxisX, rotAxisY, rotAxisZ, rotAngle);
    }
    
    /** Rotates this Quaternion to point towards destPoint, from the supplied sourcePoint.
     * Assumes a forward Vector of (0,0,1) and an up Vector of (0,1,0)  */
    public void LookAt(Vector3f sourcePoint, Vector3f destPoint) {
        float dirX = destPoint.x - sourcePoint.x;
        float dirY = destPoint.y - sourcePoint.y;
        float dirZ = destPoint.z - sourcePoint.z;

        float length = (float) Math.sqrt(dirX * dirX + dirY * dirY + dirZ * dirZ);

        dirX /= length;
        dirY /= length;
        dirZ /= length;

        float dot = (Vector3f.forward.x * dirX) + (Vector3f.forward.y * dirY) + (Vector3f.forward.z * dirZ);

        if (Math.abs(dot - (-1.0f)) < 0.000001f) {
            x = Vector3f.up.x;
            y = Vector3f.up.y;
            z = Vector3f.up.z;
            w = (float) Math.PI;
            return;
        }

        if (Math.abs(dot - (1.0f)) < 0.000001f) {
            x = 0.0f;
            y = 0.0f;
            z = 0.0f;
            w = 1.0f;
            return;
        }

        float rotAngle = (float) Math.acos(dot);
        
        float rotAxisX = Vector3f.forward.y * dirZ - Vector3f.forward.z * dirY;
        float rotAxisY = Vector3f.forward.z * dirX - Vector3f.forward.x * dirZ;
        float rotAxisZ = Vector3f.forward.x * dirY - Vector3f.forward.y * dirX;

        length = (float) Math.sqrt(rotAxisX * rotAxisX + rotAxisY * rotAxisY + rotAxisZ * rotAxisZ);

        rotAxisX /= length;
        rotAxisY /= length;
        rotAxisZ /= length;

        fromAxisAngleRad(rotAxisX, rotAxisY, rotAxisZ, rotAngle);
    }
    
    /** Rotates dest to point towards destPoint, from the supplied sourcePoint */
    public static void LookAt(Vector3f sourcePoint, Vector3f destPoint, Vector3f up, Vector3f forward, Quaternion dest) {
        float dirX = destPoint.x - sourcePoint.x;
        float dirY = destPoint.y - sourcePoint.y;
        float dirZ = destPoint.z - sourcePoint.z;

        float length = (float) Math.sqrt(dirX * dirX + dirY * dirY + dirZ * dirZ);

        dirX /= length;
        dirY /= length;
        dirZ /= length;

        float dot = (forward.x * dirX) + (forward.y * dirY) + (forward.z * dirZ);

        if (Math.abs(dot - (-1.0f)) < 0.000001f) {
            dest.x = up.x;
            dest.y = up.y;
            dest.z = up.z;
            dest.w = (float) Math.PI;
            return;
        }

        if (Math.abs(dot - (1.0f)) < 0.000001f) {
            dest.x = 0.0f;
            dest.y = 0.0f;
            dest.z = 0.0f;
            dest.w = 1.0f;
            return;
        }

        float rotAngle = (float) Math.acos(dot);
        
        float rotAxisX = forward.y * dirZ - forward.z * dirY;
        float rotAxisY = forward.z * dirX - forward.x * dirZ;
        float rotAxisZ = forward.x * dirY - forward.y * dirX;

        length = (float) Math.sqrt(rotAxisX * rotAxisX + rotAxisY * rotAxisY + rotAxisZ * rotAxisZ);

        rotAxisX /= length;
        rotAxisY /= length;
        rotAxisZ /= length;

        dest.fromAxisAngleRad(rotAxisX, rotAxisY, rotAxisZ, rotAngle);
    }
    
    /** Rotates dest to point towards destPoint, from the supplied sourcePoint */
    public void LookAt(Vector3f sourcePoint, Vector3f destPoint, Vector3f up, Vector3f forward) {
        float dirX = destPoint.x - sourcePoint.x;
        float dirY = destPoint.y - sourcePoint.y;
        float dirZ = destPoint.z - sourcePoint.z;

        float length = (float) Math.sqrt(dirX * dirX + dirY * dirY + dirZ * dirZ);

        dirX /= length;
        dirY /= length;
        dirZ /= length;

        float dot = (forward.x * dirX) + (forward.y * dirY) + (forward.z * dirZ);

        if (Math.abs(dot - (-1.0f)) < 0.000001f) {
            x = up.x;
            y = up.y;
            z = up.z;
            w = (float) Math.PI;
            return;
        }

        if (Math.abs(dot - (1.0f)) < 0.000001f) {
            x = 0.0f;
            y = 0.0f;
            z = 0.0f;
            w = 1.0f;
            return;
        }

        float rotAngle = (float) Math.acos(dot);
        
        float rotAxisX = forward.y * dirZ - forward.z * dirY;
        float rotAxisY = forward.z * dirX - forward.x * dirZ;
        float rotAxisZ = forward.x * dirY - forward.y * dirX;

        length = (float) Math.sqrt(rotAxisX * rotAxisX + rotAxisY * rotAxisY + rotAxisZ * rotAxisZ);

        rotAxisX /= length;
        rotAxisY /= length;
        rotAxisZ /= length;

        fromAxisAngleRad(rotAxisX, rotAxisY, rotAxisZ, rotAngle);
    }

    public String toString() {
        return "Quaternion { " + x + ", " + y + ", " + z + ", " + w + " }";
    }

}
