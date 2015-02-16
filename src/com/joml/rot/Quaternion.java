/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.joml.rot;

import com.joml.matrix.Matrix4f;
import com.joml.utils.TrigMath;
import com.joml.vector.Vector3f;
import static java.lang.Math.PI;
import java.nio.FloatBuffer;

/**
 *
 * @author RGreenlees
 */
public class Quaternion {

    public static final Quaternion IDENTITY = new Quaternion(0, 0, 0, 1);

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

    public Quaternion(Vector3f eulerAngles) {
        final float sp = (float) Math.sin((eulerAngles.x * TrigMath.degreesToRadians) * 0.5f);
        final float cp = (float) Math.cos((eulerAngles.x * TrigMath.degreesToRadians) * 0.5f);
        final float sy = (float) Math.sin((eulerAngles.y * TrigMath.degreesToRadians) * 0.5f);
        final float cy = (float) Math.cos((eulerAngles.y * TrigMath.degreesToRadians) * 0.5f);
        final float sr = (float) Math.sin((eulerAngles.z * TrigMath.degreesToRadians) * 0.5f);
        final float cr = (float) Math.cos((eulerAngles.z * TrigMath.degreesToRadians) * 0.5f);

        final float cysp = cy * sp;
        final float sycp = sy * cp;
        final float cycp = cy * cp;
        final float sysp = sy * sp;

        x = (cysp * cr) + (sycp * sr);
        y = (sycp * cr) - (cysp * sr);
        z = (cycp * sr) - (sysp * cr);
        w = (cycp * cr) + (sysp * sr);
    }

    public Quaternion(Quaternion source) {
        x = source.x;
        y = source.y;
        z = source.z;
        w = source.w;
    }

    public void normalize() {
        float norm = (float) Math.sqrt(x * x + y * y + z * z + w * w);

        x /= norm;
        y /= norm;
        z /= norm;
        w /= norm;
    }

    public void add(Quaternion q2) {
        x += q2.x;
        y += q2.y;
        z += q2.z;
        w += q2.w;
    }

    public static void add(Quaternion q1, Quaternion q2, Quaternion dest) {
        dest.x = q1.x + q2.x;
        dest.y = q1.y + q2.y;
        dest.z = q1.z + q2.z;
        dest.w = q1.w + q2.w;
    }

    public float dot(Quaternion otherQuat) {
        return this.x * otherQuat.x + this.y * otherQuat.y + this.z * otherQuat.z + this.w * otherQuat.w;
    }

    public static float dot(Quaternion a, Quaternion b) {
        return a.x * b.x + a.y * b.y + a.z * b.z + a.w * b.w;
    }

    public final float getAngle() {
        float angle = 2.0f * (float) Math.acos(w);
        return (angle <= PI) ? angle : 2.0f * (float) PI - angle;
    }

    public static float getAngle(Quaternion q) {
        float angle = 2.0f * (float) Math.acos(q.w);
        return (angle <= PI) ? angle : 2.0f * (float) PI - angle;
    }

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

    public void set(float newX, float newY, float newZ, float newW) {
        x = newX;
        y = newY;
        z = newZ;
        w = newW;
    }

    public void set(float newX, float newY, float newZ) {
        x = newX;
        y = newY;
        z = newZ;
    }

    public void set(Quaternion q) {
        x = q.x;
        y = q.y;
        z = q.z;
        w = q.w;
    }

    public void fromAxisAngleRad(Vector3f axis, float angle) {
        float hangle = angle / 2.0f;
        float sinAngle = (float) Math.sin(hangle);
        float vLength = axis.length();

        x = (axis.x / vLength) * sinAngle;
        y = (axis.y / vLength) * sinAngle;
        z = (axis.z / vLength) * sinAngle;
        w = (float) Math.cos(hangle);
    }

    public void fromAxisAngleDeg(Vector3f axis, float angle) {
        float hangle = (angle * TrigMath.degreesToRadians) / 2.0f;
        float sinAngle = (float) Math.sin(hangle);
        float vLength = axis.length();

        x = (axis.x / vLength) * sinAngle;
        y = (axis.y / vLength) * sinAngle;
        z = (axis.z / vLength) * sinAngle;
        w = (float) Math.cos(hangle);
    }

    public void mul(Quaternion q) {
        set(x = this.x * q.x - this.y * q.y - this.z * q.z - this.w * q.w,
                y = this.x * q.y + this.y * q.x + this.z * q.w - this.w * q.z,
                z = this.x * q.z - this.y * q.w + this.z * q.x + this.w * q.y,
                w = this.x * q.w + this.y * q.z - this.z * q.y + this.w * q.x);
    }

    public static void mulFast(Quaternion a, Quaternion b, Quaternion dest) {
        dest.x = a.x * b.x - a.y * b.y - a.z * b.z - a.w * b.w;
        dest.y = a.x * b.y + a.y * b.x + a.z * b.w - a.w * b.z;
        dest.z = a.x * b.z - a.y * b.w + a.z * b.x + a.w * b.y;
        dest.w = a.x * b.w + a.y * b.z - a.z * b.y + a.w * b.x;
    }

    public static void mul(Quaternion a, Quaternion b, Quaternion dest) {
        dest.set(a.x * b.x - a.y * b.y - a.z * b.z - a.w * b.w,
                a.x * b.y + a.y * b.x + a.z * b.w - a.w * b.z,
                a.x * b.z - a.y * b.w + a.z * b.x + a.w * b.y,
                a.x * b.w + a.y * b.z - a.z * b.y + a.w * b.x);
    }

    public void invert() {
        float norm = (x * x + y * y + z * z + w * w);
        x = x / norm;
        y = -y / norm;
        z = -z / norm;
        w = -w / norm;
    }

    public static void invert(Quaternion q, Quaternion dest) {
        float norm = (q.x * q.x + q.y * q.y + q.z * q.z + q.w * q.w);
        dest.x = q.x / norm;
        dest.y = -q.y / norm;
        dest.z = -q.z / norm;
        dest.w = -q.w / norm;
    }

    public void div(Quaternion b) {
        invert();
        mul(b);
    }

    public static void div(Quaternion a, Quaternion b, Quaternion dest) {
        dest.x = a.x;
        dest.y = a.y;
        dest.z = a.z;
        dest.w = a.w;

        dest.invert();
        dest.mul(b);
    }

    public void conjugate() {
        y = -y;
        z = -z;
        w = -w;
    }

    public static void conjugate(Quaternion a, Quaternion dest) {
        dest.x = a.x;
        dest.y = -a.y;
        dest.z = -a.z;
        dest.w = -a.w;
    }

    public void identity() {
        x = 0.0f;
        y = 0.0f;
        z = 0.0f;
        w = 1.0f;
    }

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

    public float length() {
        return x * x + y * y + z * z + w * w;
    }

    public static float length(Quaternion q) {
        return q.x * q.x + q.y * q.y + q.z * q.z + q.w * q.w;
    }

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
    
    public static void slerp(Quaternion start, Quaternion target, float alpha, Quaternion dest) {
        final float dot = Math.abs(start.x * target.x + start.y * target.y + start.z * target.z + start.w * target.w);
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

        dest.x = (scale1 * start.x) + (scale2 * target.x);
        dest.y = (scale1 * start.y) + (scale2 * target.y);
        dest.z = (scale1 * start.z) + (scale2 * target.z);
        dest.w = (scale1 * start.w) + (scale2 * target.w);
    }

    public String toString() {
        return "Quaternion { " + x + ", " + y + ", " + z + ", " + w + " }";
    }

}
