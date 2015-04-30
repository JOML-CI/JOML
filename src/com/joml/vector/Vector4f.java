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
package com.joml.vector;

import com.joml.matrix.Matrix4f;

/**
 * Vector4f
 * 
 * Contains the definition of a Vector comprising 4 floats and associated transformations.
 * 
 * @author Richard Greenlees
 */
public class Vector4f {

    public float x;
    public float y;
    public float z;
    public float w;

    public Vector4f() {
    }

    public Vector4f(Vector4f clone) {
        this.x = clone.x;
        this.y = clone.y;
        this.z = clone.z;
        this.w = clone.w;
    }

    public Vector4f(Vector3f clone, float w) {
        this.x = clone.x;
        this.y = clone.y;
        this.z = clone.z;
        this.w = w;
    }

    public Vector4f(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public final void set(Vector4f v) {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
        this.w = v.w;
    }

    public final void set(Vector3f v, float w) {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
        this.w = w;
    }

    public final void set(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }
    
    /**
     * Subtracts the supplied vector from this one
     */
    public final void sub(Vector4f v) {
        x -= v.x;
        y -= v.y;
        z -= v.z;
        w -= v.w;
    }

    /**
     * Subtracts v2 from v1 and stores the results in dest. Does not modify v1
     * or v2
     */
    public final static void sub(Vector4f v1, Vector4f v2, Vector4f dest) {
        dest.set(v1.x - v2.x,
                v1.y - v2.y,
                v1.z - v2.z,
                v1.w - v2.w);
    }

    /**
     * Adds the supplied vector to this one
     */
    public final void add(Vector4f v) {
        x += v.x;
        y += v.y;
        z += v.z;
        w += v.w;
    }

    /**
     * Adds v2 to v1 and stores the results in dest. Does not modify v1 or v2
     */
    public final static void add(Vector4f v1, Vector4f v2, Vector4f dest) {
        dest.set(v1.x + v2.x,
                v1.y + v2.y,
                v1.z + v2.z,
                v1.w + v2.w);
    }

    /**
     * Multiply this Vector4f by another Vector4f
     */
    public final void mul(Vector4f v) {
        x *= v.x;
        y *= v.y;
        z *= v.z;
        z *= v.w;
    }

    /**
     * Multiply v1 by v2 and store the results into dest. v1 and v2 are not
     * modified
     * <B>This is not alias safe so make sure dest is not the same as the left
     * or right parameters or you WILL get incorrect results!</B>
     */
    public final static void mulFast(Vector4f v1, Vector4f v2, Vector4f dest) {
        dest.x = v1.x * v2.x;
        dest.y = v1.y * v2.y;
        dest.z = v1.z * v2.z;
        dest.w = v1.w * v2.w;
    }

    /**
     * Multiply this Vector4f by the given rotation matrix mat
     */
    public final void mul(Matrix4f mat) {
        set(mat.m00 * x + mat.m10 * y + mat.m20 * z + mat.m30 * w,
                mat.m01 * x + mat.m11 * y + mat.m21 * z + mat.m31 * w,
                mat.m02 * x + mat.m12 * y + mat.m22 * z + mat.m32 * w, 
                mat.m03 * x + mat.m13 * y + mat.m23 * z + mat.m33 * w);
    }

    /**
     * Multiply Vector4f v by the given rotation matrix mat and store the
     * results in dest. Does not modify v
     */
    public final static void mul(Vector4f v, Matrix4f mat, Vector4f dest) {
        dest.set(mat.m00 * v.x + mat.m10 * v.y + mat.m20 * v.z + mat.m30 * v.w,
                mat.m01 * v.x + mat.m11 * v.y + mat.m21 * v.z + mat.m31 * v.w,
                mat.m02 * v.x + mat.m12 * v.y + mat.m22 * v.z + mat.m32 * v.w, 
                mat.m03 * v.x + mat.m13 * v.y + mat.m23 * v.z + mat.m33 * v.w);
    }

    /**
     * Multiply Vector4f v by the given rotation matrix mat and store the
     * results in dest. Does not modify v
     * <B>This is not alias safe so make sure dest is not the same as the left
     * or right parameters or you WILL get incorrect results!</B>
     */
    public final static void mulFast(Vector4f v, Matrix4f mat, Vector4f dest) {
        dest.x = mat.m00 * v.x + mat.m10 * v.y + mat.m20 * v.z + mat.m30 * v.w;
        dest.y = mat.m01 * v.x + mat.m11 * v.y + mat.m21 * v.z + mat.m31 * v.w;
        dest.z = mat.m02 * v.x + mat.m12 * v.y + mat.m22 * v.z + mat.m32 * v.w;
        dest.w = mat.m03 * v.x + mat.m13 * v.y + mat.m23 * v.z + mat.m33 * v.w;
    }

    /**
     * Multiply this Vector4f by the given scalar value
     */
    public final void mul(float scalar) {
        x *= scalar;
        y *= scalar;
        z *= scalar;
        w *= scalar;
    }

    /* Multiply the given Vector4f v by the scalar value, and store in dest. Does not modify v */
    public final static void mul(Vector4f v, float scalar, Vector4f dest) {
        dest.x = v.x * scalar;
        dest.y = v.y * scalar;
        dest.z = v.z * scalar;
        dest.w = v.w * scalar;
    }

    /**
     * Returns the length squared of this vector
     */
    public float lengthSquared() {
        return x * x + y * y + z * z + w * w;
    }

    /**
     * Returns the length of this vector
     */
    public float length() {
        return (float) Math.sqrt(lengthSquared());
    }

    /**
     * Used internally for the distance function
     */
    private static float lengthSquared(Vector4f source) {
        return source.x * source.x + source.y * source.y + source.z * source.z + source.w * source.w;
    }

    /**
     * Used internally for the distance function
     */
    private static float length(Vector4f source) {
        return (float) Math.sqrt(lengthSquared(source));
    }

    /**
     * Normalizes this vector
     */
    public final void normalize() {
        float d = length();
        x /= d;
        y /= d;
        z /= d;
        w /= d;
    }

    /**
     * Normalize the original vector and store the results in dest. Does not
     * modify the original
     */
    public final static void normalize(Vector4f original, Vector4f dest) {
        float d = length(original);
        dest.set(original.x / d,
                original.y / d,
                original.z / d,
                original.w / d);
    }

    /**
     * Returns the distance between the start and end vectors. Does not modify
     * either
     */
    public static float distance(Vector4f start, Vector4f end) {
        return (float) Math.sqrt((end.x - start.x) * (end.x - start.x)
                + (end.y - start.y) * (end.y - start.y)
                + (end.z - start.z) * (end.z - start.z)
                + (end.w - start.w) * (end.w - start.w));
    }
    
    /**
     * Returns the distance between this Vector and v. Does not modify
     * either
     */
    public float distance(Vector4f v) {
        return (float) Math.sqrt((v.x - this.x) * (v.x - this.x)
                + (v.y - this.y) * (v.y - this.y)
                + (v.z - this.z) * (v.z - this.z)
                + (v.w - this.w) * (v.w - this.w));
    }

    /**
     * Return the dot product of the supplied v1 and v2 vectors
     */
    public static float dot(Vector4f v1, Vector4f v2) {
        return (v1.x * v2.x) + (v1.y * v2.y) + (v1.z * v2.z) + (v1.w * v2.w);
    }
    
    /**
     * Set all components to zero.
     */
    public void zero() {
        this.x = 0.0f;
        this.y = 0.0f;
        this.z = 0.0f;
        this.w = 0.0f;
    }

    public String toString() {
        return "Vector4f { " + x + ", " + y + ", " + z + ", " + w + " }";
    }

}