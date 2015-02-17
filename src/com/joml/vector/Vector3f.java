/*
 * Feel free to do whatever you want with this code, all I've done is
 * pull together common knowledge into one easy package. Use it as a
 * base for your own work, copy/paste bits or integrate it into your
 * existing project, it's all good. Just add a thanks to me somewhere.
 */
package com.joml.vector;

import com.joml.matrix.Matrix3f;
import com.joml.matrix.Matrix4f;

/**
 * Vector3f
 *
 * Contains the definition of a Vector comprising 3 floats and associated
 * transformations.
 *
 * @author Richard Greenlees
 */
public class Vector3f {

    public float x;
    public float y;
    public float z;
    
    public static Vector3f forward = new Vector3f(0,0,1);
    public static Vector3f up = new Vector3f(0,1,0);

    public Vector3f() {

    }

    public Vector3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3f(Vector3f clone) {
        this.x = clone.x;
        this.y = clone.y;
        this.z = clone.z;
    }

    /**
     * Sets the x, y and z attributes to match the supplied vector
     */
    public void set(Vector3f v) {
        x = v.x;
        y = v.y;
        z = v.z;
    }

    /**
     * Sets the x, y and z attributes to the supplied float values
     */
    public void set(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Subtracts the supplied vector from this one
     */
    public void sub(Vector3f v) {
        x -= v.x;
        y -= v.y;
        z -= v.z;
    }

    /**
     * Subtracts v2 from v1 and stores the results in dest. Does not modify v1
     * or v2
     */
    public static void sub(Vector3f v1, Vector3f v2, Vector3f dest) {
        dest.set(v1.x - v2.x,
                v1.y - v2.y,
                v1.z - v2.z);
    }

    /**
     * Adds the supplied vector to this one
     */
    public void add(Vector3f v) {
        x += v.x;
        y += v.y;
        z += v.z;
    }

    /**
     * Adds v2 to v1 and stores the results in dest. Does not modify v1 or v2
     */
    public static void add(Vector3f v1, Vector3f v2, Vector3f dest) {
        dest.set(v1.x + v2.x,
                v1.y + v2.y,
                v1.z + v2.z);
    }

    /** Multiply this Vector3f by another Vector3f */
    public void mul(Vector3f v) {
        x *= v.x;
        y *= v.y;
        z *= v.z;
    }

    /** Multiply v1 by v2 and store the results into dest. v1 and v2 are not modified
     <B>This is not alias safe so make sure dest is not the same as the left or right parameters or you WILL get incorrect results!</B> */
    public static void mulFast(Vector3f v1, Vector3f v2, Vector3f dest) {
        dest.x = v1.x * v2.x;
        dest.y = v1.y * v2.y;
        dest.z = v1.z * v2.z;
    }
    
    /** Multiply this Vector3f by the given rotation matrix mat */
    public void mul(Matrix4f mat) {
        set(mat.m00 * x + mat.m10 * y + mat.m20 * z,
            mat.m01 * x + mat.m11 * y + mat.m21 * z,
            mat.m02 * x + mat.m12 * y + mat.m22 * z);
    }
    
    /** Multiply Vector3f v by the given rotation matrix mat and store the results in dest. Does not modify v */
    public static void mul(Vector3f v, Matrix4f mat, Vector3f dest) {
        dest.set(mat.m00 * v.x + mat.m10 * v.y + mat.m20 * v.z,
                 mat.m01 * v.x + mat.m11 * v.y + mat.m21 * v.z,
                 mat.m02 * v.x + mat.m12 * v.y + mat.m22 * v.z);
    }
    
    /** Multiply Vector3f v by the given rotation matrix mat and store the results in dest. Does not modify v
        <B>This is not alias safe so make sure dest is not the same as the left or right parameters or you WILL get incorrect results!</B> */
    public static void mulFast(Vector3f v, Matrix4f mat, Vector3f dest) {
        dest.x = mat.m00 * v.x + mat.m10 * v.y + mat.m20 * v.z;
        dest.y = mat.m01 * v.x + mat.m11 * v.y + mat.m21 * v.z;
        dest.z = mat.m02 * v.x + mat.m12 * v.y + mat.m22 * v.z;
    }
    
    /** Multiply this Vector3f by the given rotation matrix mat */
    public void mul(Matrix3f mat) {
        set(mat.m00 * x + mat.m10 * y + mat.m20 * z,
            mat.m01 * x + mat.m11 * y + mat.m21 * z,
            mat.m02 * x + mat.m12 * y + mat.m22 * z);
    }
    
    /** Multiply Vector3f v by the given rotation matrix mat and store the results in dest. Does not modify v */
    public static void mul(Vector3f v, Matrix3f mat, Vector3f dest) {
        dest.set(mat.m00 * v.x + mat.m10 * v.y + mat.m20 * v.z,
                 mat.m01 * v.x + mat.m11 * v.y + mat.m21 * v.z,
                 mat.m02 * v.x + mat.m12 * v.y + mat.m22 * v.z);
    }
    
    /** Multiply Vector3f v by the given rotation matrix mat and store the results in dest. Does not modify v
    <B>This is not alias safe so make sure dest is not the same as the left or right parameters or you WILL get incorrect results!</B> */
    public static void mulFast(Vector3f v, Matrix3f mat, Vector3f dest) {
        dest.x = mat.m00 * v.x + mat.m10 * v.y + mat.m20 * v.z;
        dest.y = mat.m01 * v.x + mat.m11 * v.y + mat.m21 * v.z;
        dest.z = mat.m02 * v.x + mat.m12 * v.y + mat.m22 * v.z;
    }

    /** Multiply this Vector3f by the given scalar value */
    public void mul(float scalar) {
        x *= scalar;
        y *= scalar;
        z *= scalar;
    }

    /* Multiply the given Vector3f v by the scalar value, and store in dest. Does not modify v */
    public static void mul(Vector3f v, float scalar, Vector3f dest) {
        dest.x = v.x * scalar;
        dest.y = v.y * scalar;
        dest.z = v.z * scalar;
    }

    /**
     * Returns the length squared of this vector
     */
    public float lengthSquared() {
        return x * x + y * y + z * z;
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
    private static float lengthSquared(Vector3f source) {
        return source.x * source.x + source.y * source.y + source.z * source.z;
    }

    /**
     * Used internally for the distance function
     */
    private static float length(Vector3f source) {
        return (float) Math.sqrt(lengthSquared(source));
    }
    
    

    /**
     * Normalizes this vector
     */
    public void normalize() {
        float d = length();
        x /= d;
        y /= d;
        z /= d;
    }

    /**
     * Normalize the original vector and store the results in dest. Does not
     * modify the original
     */
    public static void normalize(Vector3f original, Vector3f dest) {
        float d = length(original);
        dest.set(original.x / d,
                original.y / d,
                original.z / d);
    }

    /**
     * Set this vector to be the cross of v1 and v2
     */
    public void cross(Vector3f v1, Vector3f v2) {
        set(v1.y * v2.z - v1.z * v2.y,
                v1.z * v2.x - v1.x * v2.z,
                v1.x * v2.y - v1.y * v2.x);
    }

    /**
     * Calculate the cross of v1 and v2 and store the results in dest. Does
     * modify v1 or v2
     */
    public static void cross(Vector3f v1, Vector3f v2, Vector3f dest) {
        dest.set(v1.y * v2.z - v1.z * v2.y,
                v1.z * v2.x - v1.x * v2.z,
                v1.x * v2.y - v1.y * v2.x);
    }

    /**
     * Returns the distance between the start and end vectors. Does not modify
     * either
     */
    public static float distance(Vector3f start, Vector3f end) {
        return (float) Math.sqrt((end.x - start.x) * (end.x - start.x)
                + (end.y - start.y) * (end.y - start.y)
                + (end.z - start.z) * (end.z - start.z));
    }

    /**
     * Return the dot product of the supplied v1 and v2 vectors
     */
    public static float dot(Vector3f v1, Vector3f v2) {
        return (v1.x * v2.x) + (v1.y * v2.y) + (v1.z * v2.z);
    }

    /**
     * Calculates the normal of a surface defined by points v1, v2 and v3 and
     * stores it in dest. v1, v2 and v3 are not modified
     */
    public static void normal(Vector3f v1, Vector3f v2, Vector3f v3, Vector3f dest) {
        dest.set(((v2.y - v1.y) * (v3.z - v1.z)) - ((v2.z - v1.z) * (v3.y - v1.y)),
                ((v2.z - v1.z) * (v3.x - v1.x)) - ((v2.x - v1.x) * (v3.z - v1.z)),
                ((v2.x - v1.x) * (v3.y - v1.y)) - ((v2.y - v1.y) * (v3.x - v1.x)));
    }

    public String toString() {
        return "Vector3f { " + x + ", " + y + ", " + z + " }";
    }

}
