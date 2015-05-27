/*
 * (C) Copyright 2015 Richard Greenlees
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software
 * and associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 *  1) The above copyright notice and this permission notice shall be included
 *     in all copies or substantial portions of the Software.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 */
package com.joml;

import java.nio.DoubleBuffer;

/**
 * Quaternion
 *
 * Contains the definition and functions for rotations expressed as
 * 4-dimensional vectors
 *
 * @author Richard Greenlees
 */
public class QuaternionD {

    public static QuaternionD IDENTITY = new QuaternionD(0.0f, 0.0f, 0.0f, 1.0f);

    public double x;
    public double y;
    public double z;
    public double w;

    public QuaternionD() {
        x = 0.0f;
        y = 0.0f;
        z = 0.0f;
        w = 1.0f;
    }

    public QuaternionD(double newX, double newY, double newZ, double newW) {
        x = newX;
        y = newY;
        z = newZ;
        w = newW;
    }

    public QuaternionD(double newX, double newY, double newZ) {
        x = newX;
        y = newY;
        z = newZ;
        w = 1.0f;
    }

    public QuaternionD(QuaternionD source) {
        x = source.x;
        y = source.y;
        z = source.z;
        w = source.w;
    }

    /**
     * Normalize this Quaternion
     */
    public void normalize() {
        double norm = Math.sqrt(x * x + y * y + z * z + w * w);

        x /= norm;
        y /= norm;
        z /= norm;
        w /= norm;
    }

    /**
     * Normalizes the supplied Quaternion source and stores the results in dest.
     * Does not modify the source
     */
    public static void normalize(QuaternionD source, QuaternionD dest) {
        double norm = Math.sqrt(source.x * source.x + source.y * source.y + source.z * source.z + source.w * source.w);

        dest.x = source.x / norm;
        dest.y = source.y / norm;
        dest.z = source.z / norm;
        dest.w = source.w / norm;
    }

    /**
     * Adds q2 to this quaternion
     */
    public void add(QuaternionD q2) {
        x += q2.x;
        y += q2.y;
        z += q2.z;
        w += q2.w;
    }

    /**
     * Add q2 to q1 and store the results in dest. Does not modify q1 or q2
     */
    public static void add(QuaternionD q1, QuaternionD q2, QuaternionD dest) {
        dest.x = q1.x + q2.x;
        dest.y = q1.y + q2.y;
        dest.z = q1.z + q2.z;
        dest.w = q1.w + q2.w;
    }

    /**
     * Returns the dot of this Quaternion and otherQuat
     */
    public double dot(QuaternionD otherQuat) {
        return this.x * otherQuat.x + this.y * otherQuat.y + this.z * otherQuat.z + this.w * otherQuat.w;
    }

    /**
     * Returns the dot product of a and b
     */
    public static double dot(QuaternionD a, QuaternionD b) {
        return a.x * b.x + a.y * b.y + a.z * b.z + a.w * b.w;
    }

    /**
     * Finds the angle represented by this Quaternion in degrees
     */
    public double getAngle() {
        double angle = 2.0 * Math.acos(w);
        return (angle <= Math.PI) ? angle : 2.0f * Math.PI - angle;
    }

    /**
     * Finds the angle represented by q in degrees
     */
    public static double getAngle(QuaternionD q) {
        double angle = 2.0 * Math.acos(q.w);
        return (angle <= Math.PI) ? angle : 2.0 * Math.PI - angle;
    }

    /**
     * Generates a rotation matrix from q and stores the results in dest
     */
    public static void getMatrix(QuaternionD q, Matrix4d dest) {
        double q00 = 2.0f * q.x * q.x;
        double q11 = 2.0f * q.y * q.y;
        double q22 = 2.0f * q.z * q.z;

        double q01 = 2.0f * q.x * q.y;
        double q02 = 2.0f * q.x * q.z;
        double q03 = 2.0f * q.x * q.w;

        double q12 = 2.0f * q.y * q.z;
        double q13 = 2.0f * q.y * q.w;

        double q23 = 2.0f * q.z * q.w;

        dest.m00 = 1.0 - q11 - q22;
        dest.m01 = q01 + q23;
        dest.m02 = q02 - q13;
        dest.m03 = 0.0;
        dest.m10 = q01 - q23;
        dest.m11 = 1.0 - q22 - q00;
        dest.m12 = q12 + q03;
        dest.m13 = 0.0;
        dest.m20 = q02 + q13;
        dest.m21 = q12 - q03;
        dest.m22 = 1.0 - q11 - q00;
        dest.m30 = 0.0;
        dest.m31 = 0.0;
        dest.m32 = 0.0;
        dest.m33 = 1.0;
    }

    /**
     * Generates a rotation matrix from this Quaternion and stores the results
     * in dest
     */
    public static void getMatrix(QuaternionD quat, DoubleBuffer dest) {
        double q00 = 2.0 * quat.x * quat.x;
        double q11 = 2.0 * quat.y * quat.y;
        double q22 = 2.0 * quat.z * quat.z;

        double q01 = 2.0 * quat.x * quat.y;
        double q02 = 2.0 * quat.x * quat.z;
        double q03 = 2.0 * quat.x * quat.w;

        double q12 = 2.0 * quat.y * quat.z;
        double q13 = 2.0 * quat.y * quat.w;

        double q23 = 2.0 * quat.z * quat.w;

        dest.put(1.0 - q11 - q22);
        dest.put(q01 + q23);
        dest.put(q02 - q13);
        dest.put(0.0);
        dest.put(q01 - q23);
        dest.put(1.0 - q22 - q00);
        dest.put(q12 + q03);
        dest.put(0.0);
        dest.put(q02 + q13);
        dest.put(q12 - q03);
        dest.put(1.0 - q11 - q00);
        dest.put(0.0);
        dest.put(0.0);
        dest.put(0.0);
        dest.put(1.0);
    }

    /**
     * Generates a rotation matrix from this Quaternion and stores the results
     * in dest
     */
    public void getMatrix(Matrix4d dest) {
        double q00 = 2.0 * this.x * this.x;
        double q11 = 2.0 * this.y * this.y;
        double q22 = 2.0 * this.z * this.z;

        double q01 = 2.0 * this.x * this.y;
        double q02 = 2.0 * this.x * this.z;
        double q03 = 2.0 * this.x * this.w;

        double q12 = 2.0 * this.y * this.z;
        double q13 = 2.0 * this.y * this.w;

        double q23 = 2.0 * this.z * this.w;

        dest.m00 = 1.0 - q11 - q22;
        dest.m01 = q01 + q23;
        dest.m02 = q02 - q13;
        dest.m03 = 0.0;
        dest.m10 = q01 - q23;
        dest.m11 = 1.0 - q22 - q00;
        dest.m12 = q12 + q03;
        dest.m13 = 0.0;
        dest.m20 = q02 + q13;
        dest.m21 = q12 - q03;
        dest.m22 = 1.0 - q11 - q00;
        dest.m30 = 0.0;
        dest.m31 = 0.0;
        dest.m32 = 0.0;
        dest.m33 = 1.0;
    }

    /**
     * Generates a rotation matrix from this Quaternion and stores the results
     * in dest
     */
    public void getMatrix(DoubleBuffer dest) {
        double q00 = 2.0 * this.x * this.x;
        double q11 = 2.0 * this.y * this.y;
        double q22 = 2.0 * this.z * this.z;

        double q01 = 2.0 * this.x * this.y;
        double q02 = 2.0 * this.x * this.z;
        double q03 = 2.0 * this.x * this.w;

        double q12 = 2.0 * this.y * this.z;
        double q13 = 2.0 * this.y * this.w;

        double q23 = 2.0 * this.z * this.w;

        dest.put(1.0 - q11 - q22);
        dest.put(q01 + q23);
        dest.put(q02 - q13);
        dest.put(0.0);
        dest.put(q01 - q23);
        dest.put(1.0 - q22 - q00);
        dest.put(q12 + q03);
        dest.put(0.0);
        dest.put(q02 + q13);
        dest.put(q12 - q03);
        dest.put(1.0 - q11 - q00);
        dest.put(0.0);
        dest.put(0.0);
        dest.put(0.0);
        dest.put(1.0);
    }

    /**
     * Sets this Quaternion to the new values
     */
    public void set(double newX, double newY, double newZ, double newW) {
        x = newX;
        y = newY;
        z = newZ;
        w = newW;
    }

    /**
     * Set the x, y and z components of this Quaternion to the new values
     */
    public void set(double newX, double newY, double newZ) {
        x = newX;
        y = newY;
        z = newZ;
    }

    /**
     * Sets this Quaternion to be a copy of q. Does not modify q
     */
    public void set(QuaternionD q) {
        x = q.x;
        y = q.y;
        z = q.z;
        w = q.w;
    }

    /**
     * Sets this Quaternion to be a representation of the supplied axis and
     * angle (in Radians)
     */
    public void fromAxisAngleRad(Vector3d axis, double angle) {
        double hangle = angle / 2.0;
        double sinAngle = Math.sin(hangle);
        double vLength = axis.length();

        x = (axis.x / vLength) * sinAngle;
        y = (axis.y / vLength) * sinAngle;
        z = (axis.z / vLength) * sinAngle;
        w = Math.cos(hangle);
    }

    /**
     * Sets this Quaternion to be a representation of the supplied axis and
     * angle (in Radians)
     */
    public void fromAxisAngleRad(double axisX, double axisY, double axisZ, double angle) {
        double hangle = angle / 2.0;
        double sinAngle = Math.sin(hangle);
        double vLength = Math.sqrt(axisX * axisX + axisY * axisY + axisZ * axisZ);

        x = (axisX / vLength) * sinAngle;
        y = (axisY / vLength) * sinAngle;
        z = (axisZ / vLength) * sinAngle;
        w = Math.cos(hangle);
    }

    /**
     * Sets this Quaternion to be a representation of the supplied axis and
     * angle (in Degrees)
     */
    public void fromAxisAngleDeg(Vector3d axis, double angle) {
        double hangle = (angle * TrigMath.degreesToRadians) / 2.0f;
        double sinAngle = Math.sin(hangle);
        double vLength = axis.length();

        x = (axis.x / vLength) * sinAngle;
        y = (axis.y / vLength) * sinAngle;
        z = (axis.z / vLength) * sinAngle;
        w = Math.cos(hangle);
    }

    /**
     * Multiply this Quaternion by q
     */
    public void mul(QuaternionD q) {
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
    public static void mulFast(QuaternionD a, QuaternionD b, QuaternionD dest) {
        dest.x = a.x * b.x - a.y * b.y - a.z * b.z - a.w * b.w;
        dest.y = a.x * b.y + a.y * b.x + a.z * b.w - a.w * b.z;
        dest.z = a.x * b.z - a.y * b.w + a.z * b.x + a.w * b.y;
        dest.w = a.x * b.w + a.y * b.z - a.z * b.y + a.w * b.x;
    }

    /**
     * Multiply a by b and store the results in dest.
     */
    public static void mul(QuaternionD a, QuaternionD b, QuaternionD dest) {
        dest.set(a.x * b.x - a.y * b.y - a.z * b.z - a.w * b.w,
                a.x * b.y + a.y * b.x + a.z * b.w - a.w * b.z,
                a.x * b.z - a.y * b.w + a.z * b.x + a.w * b.y,
                a.x * b.w + a.y * b.z - a.z * b.y + a.w * b.x);
    }

    /**
     * Invert this Quaternion
     */
    public void invert() {
        double norm = (x * x + y * y + z * z + w * w);
        x = x / norm;
        y = -y / norm;
        z = -z / norm;
        w = -w / norm;
    }

    /**
     * Inverts q and stores the results in dest. Does not modify q
     */
    public static void invert(QuaternionD q, QuaternionD dest) {
        double norm = (q.x * q.x + q.y * q.y + q.z * q.z + q.w * q.w);
        dest.x = q.x / norm;
        dest.y = -q.y / norm;
        dest.z = -q.z / norm;
        dest.w = -q.w / norm;
    }

    /**
     * Divides this Quaternion by b
     */
    public void div(QuaternionD b) {
        // TODO: Remove method calls
        invert();
        mul(b);
    }

    /**
     * Divides a by b and stores the results in dest. Does not modify a or b
     */
    public static void div(QuaternionD a, QuaternionD b, QuaternionD dest) {
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
    public static void conjugate(QuaternionD a, QuaternionD dest) {
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
     * Calculate this Quaternion using the supplied Vector3f angles (in degrees) with rotation order XYZ.
     */
    public void setEulerAnglesDegXYZ(Vector3d angles) {
        setEulerAnglesDegXYZ(angles.x, angles.y, angles.z);
    }

    /**
     * Calculate this Quaternion using the supplied Vector3f angles (in radians) with rotation order XYZ.
     */
    public void setEulerAnglesRadXYZ(Vector3d angles) {
        setEulerAnglesRadXYZ(angles.x, angles.y, angles.z);
    }

    /**
     * Calculate this Quaternion using the supplied Vector3f angles (in degrees) with rotation order ZYX.
     */
    public void setEulerAnglesDegZYX(Vector3d angles) {
        setEulerAnglesDegZYX(angles.x, angles.y, angles.z);
    }

    /**
     * Calculate this Quaternion using the supplied Vector3f angles (in radians) with rotation order ZYX.
     */
    public void setEulerAnglesRadZYX(Vector3d angles) {
        setEulerAnglesRadZYX(angles.x, angles.y, angles.z);
    }

    /**
     * Calculate this Quaternion using the supplied pitch (rotation about X), yaw (rotation about Y) and roll (rotation about Z) angles
     * (in degrees) with rotation order XYZ.
     * <p>
     * This method implements the solution outlined in <a href="http://gamedev.stackexchange.com/questions/13436/glm-euler-angles-to-quaternion#answer-13446">this stackexchange answer</a>.
     */
    public void setEulerAnglesDegXYZ(double rotationAboutX, double rotationAboutY, double rotationAboutZ) {
        double sx = Math.sin((rotationAboutX * TrigMath.degreesToRadians) * 0.5);
        double cx = Math.cos((rotationAboutX * TrigMath.degreesToRadians) * 0.5);
        double sy = Math.sin((rotationAboutY * TrigMath.degreesToRadians) * 0.5);
        double cy = Math.cos((rotationAboutY * TrigMath.degreesToRadians) * 0.5);
        double sz = Math.sin((rotationAboutZ * TrigMath.degreesToRadians) * 0.5);
        double cz = Math.cos((rotationAboutZ * TrigMath.degreesToRadians) * 0.5);

        x = cx*cy*cz + sx*sy*sz;
        y = sx*cy*cz - cx*sy*sz;
        z = cx*sy*cz + sx*cy*sz;
        w = cx*cy*sz - sx*sy*cz;
    }

    /**
     * Calculate this Quaternion using the supplied pitch (rotation about X), yaw (rotation about Y) and roll (rotation about Z) angles
     * (in degrees) with rotation order ZYX.
     * <p>
     * This method implements the solution outlined in <a href="http://gamedev.stackexchange.com/questions/13436/glm-euler-angles-to-quaternion#answer-13446">this stackexchange answer</a>.
     */
    public void setEulerAnglesDegZYX(double rotationAboutX, double rotationAboutY, double rotationAboutZ) {
        double sx = Math.sin(rotationAboutX * TrigMath.degreesToRadians * 0.5);
        double cx = Math.cos(rotationAboutX * TrigMath.degreesToRadians * 0.5);
        double sy = Math.sin(rotationAboutY * TrigMath.degreesToRadians * 0.5);
        double cy = Math.cos(rotationAboutY * TrigMath.degreesToRadians * 0.5);
        double sz = Math.sin(rotationAboutZ * TrigMath.degreesToRadians * 0.5);
        double cz = Math.cos(rotationAboutZ * TrigMath.degreesToRadians * 0.5);

        x = cx*cy*cz - sx*sy*sz;
        y = sx*cy*cz + cx*sy*sz;
        z = cx*sy*cz - sx*cy*sz;
        w = cx*cy*sz + sx*sy*cz;
    }

    /**
     * Returns the length of this quaternion
     */
    public double length() {
        return x * x + y * y + z * z + w * w;
    }

    /**
     * Returns the length of q
     */
    public static double length(QuaternionD q) {
        return q.x * q.x + q.y * q.y + q.z * q.z + q.w * q.w;
    }

    /**
     * Calculate this Quaternion using the supplied pitch (rotation about X), yaw (rotation about Y) and roll (rotation about Z) angles
     * (in radians) with rotation order XYZ.
     * <p>
     * This method implements the solution outlined in <a href="http://gamedev.stackexchange.com/questions/13436/glm-euler-angles-to-quaternion#answer-13446">this stackexchange answer</a>.
     */
    public void setEulerAnglesRadXYZ(double rotationAboutX, double rotationAboutY, double rotationAboutZ) {
        double sx = Math.sin(rotationAboutX * 0.5);
        double cx = Math.cos(rotationAboutX * 0.5);
        double sy = Math.sin(rotationAboutY * 0.5);
        double cy = Math.cos(rotationAboutY * 0.5);
        double sz = Math.sin(rotationAboutZ * 0.5);
        double cz = Math.cos(rotationAboutZ * 0.5);

        x = cx*cy*cz + sx*sy*sz;
        y = sx*cy*cz - cx*sy*sz;
        z = cx*sy*cz + sx*cy*sz;
        w = cx*cy*sz - sx*sy*cz;
    }

    /**
     * Calculate this Quaternion using the supplied pitch (rotation about X), yaw (rotation about Y) and roll (rotation about Z) angles
     * (in radians) with rotation order ZYX.
     * <p>
     * This method implements the solution outlined in <a href="http://gamedev.stackexchange.com/questions/13436/glm-euler-angles-to-quaternion#answer-13446">this stackexchange answer</a>.
     */
    public void setEulerAnglesRadZYX(double rotationAboutX, double rotationAboutY, double rotationAboutZ) {
        double sx = Math.sin(rotationAboutX * 0.5);
        double cx = Math.cos(rotationAboutX * 0.5);
        double sy = Math.sin(rotationAboutY * 0.5);
        double cy = Math.cos(rotationAboutY * 0.5);
        double sz = Math.sin(rotationAboutZ * 0.5);
        double cz = Math.cos(rotationAboutZ * 0.5);

        x = cx*cy*cz - sx*sy*sz;
        y = sx*cy*cz + cx*sy*sz;
        z = cx*sy*cz - sx*cy*sz;
        w = cx*cy*sz + sx*sy*cz;
    }

    /**
     * Spherical linear interpolation between this Quaternion and the specified
     * target, using the specified alpha
     */
    public void slerp(QuaternionD target, double alpha) {
        double dot = Math.abs(this.x * target.x + this.y * target.y + this.z * target.z + this.w * target.w);
        double scale1, scale2;

        if ((1 - dot) > 0.1) {
            
            double angle = Math.acos(dot);
            double sinAngle = 1.0 / Math.sin(angle);

            scale1 = (Math.sin((1.0 - alpha) * angle) * sinAngle);
            scale2 = (Math.sin((alpha * angle)) * sinAngle);
        } else {    
            scale1 = 1.0 - alpha;
            scale2 = alpha;
        }

        if (dot < 0.0) {
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
    public static void slerp(QuaternionD start, QuaternionD target, double alpha, QuaternionD dest) {
        double dot = Math.abs(start.x * target.x + start.y * target.y + start.z * target.z + start.w * target.w);
        double scale1, scale2;

        if ((1.0 - dot) > 0.0) {
            double angle = Math.acos(dot);
            double sinAngle = 1.0 / Math.sin(angle);

            scale1 = (Math.sin((1.0 - alpha) * angle) * sinAngle);
            scale2 = (Math.sin((alpha * angle)) * sinAngle);
        } else {
            scale1 = 1.0 - alpha;
            scale2 = alpha;
        }

        if (dot < 0.0) {
            scale2 = -scale2;
        }

        dest.x = (scale1 * start.x) + (scale2 * target.x);
        dest.y = (scale1 * start.y) + (scale2 * target.y);
        dest.z = (scale1 * start.z) + (scale2 * target.z);
        dest.w = (scale1 * start.w) + (scale2 * target.w);
    }

    /** Rotates dest to point towards destPoint, from the supplied sourcePoint */
    public static void LookAt(Vector3d sourcePoint, Vector3d destPoint, Vector3d up, Vector3d forward, QuaternionD dest) {
    	double dirX = destPoint.x - sourcePoint.x;
    	double dirY = destPoint.y - sourcePoint.y;
    	double dirZ = destPoint.z - sourcePoint.z;

    	double length = Math.sqrt(dirX * dirX + dirY * dirY + dirZ * dirZ);

        dirX /= length;
        dirY /= length;
        dirZ /= length;

        double dot = (forward.x * dirX) + (forward.y * dirY) + (forward.z * dirZ);

        if (Math.abs(dot + 1.0) < 0.000001) {
            dest.x = up.x;
            dest.y = up.y;
            dest.z = up.z;
            dest.w = Math.PI;
            return;
        }

        if (Math.abs(dot - 1.0) < 0.000001) {
            dest.x = 0.0;
            dest.y = 0.0;
            dest.z = 0.0;
            dest.w = 1.0;
            return;
        }

        double rotAngle = Math.acos(dot);
        
        double rotAxisX = forward.y * dirZ - forward.z * dirY;
        double rotAxisY = forward.z * dirX - forward.x * dirZ;
        double rotAxisZ = forward.x * dirY - forward.y * dirX;

        length = Math.sqrt(rotAxisX * rotAxisX + rotAxisY * rotAxisY + rotAxisZ * rotAxisZ);

        rotAxisX /= length;
        rotAxisY /= length;
        rotAxisZ /= length;

        dest.fromAxisAngleRad(rotAxisX, rotAxisY, rotAxisZ, rotAngle);
    }
    
    /** Rotates dest to point towards destPoint, from the supplied sourcePoint */
    public void LookAt(Vector3d sourcePoint, Vector3d destPoint, Vector3d up, Vector3d forward) {
    	double dirX = destPoint.x - sourcePoint.x;
    	double dirY = destPoint.y - sourcePoint.y;
    	double dirZ = destPoint.z - sourcePoint.z;

    	double length = Math.sqrt(dirX * dirX + dirY * dirY + dirZ * dirZ);

        dirX /= length;
        dirY /= length;
        dirZ /= length;

        double dot = (forward.x * dirX) + (forward.y * dirY) + (forward.z * dirZ);

        if (Math.abs(dot + 1.0) < 0.000001) {
            x = up.x;
            y = up.y;
            z = up.z;
            w = Math.PI;
            return;
        }

        if (Math.abs(dot - 1.0) < 0.000001) {
            x = 0.0f;
            y = 0.0f;
            z = 0.0f;
            w = 1.0f;
            return;
        }

        double rotAngle = Math.acos(dot);
        
        double rotAxisX = forward.y * dirZ - forward.z * dirY;
        double rotAxisY = forward.z * dirX - forward.x * dirZ;
        double rotAxisZ = forward.x * dirY - forward.y * dirX;

        length = Math.sqrt(rotAxisX * rotAxisX + rotAxisY * rotAxisY + rotAxisZ * rotAxisZ);

        rotAxisX /= length;
        rotAxisY /= length;
        rotAxisZ /= length;

        fromAxisAngleRad(rotAxisX, rotAxisY, rotAxisZ, rotAngle);
    }

    public String toString() {
        return "Quaternion { " + x + ", " + y + ", " + z + ", " + w + " }";
    }

}
