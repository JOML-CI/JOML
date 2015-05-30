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

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;


/**
 * Contains the definition of a Vector comprising 4 floats and associated transformations.
 * 
 * @author Richard Greenlees
 * @author Kai Burjack
 */
public class Vector4f implements Serializable, Externalizable {

    public float x;
    public float y;
    public float z;
    public float w = 1.0f;

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

    /**
     * 
     * @param v
     * @return this
     */
    public Vector4f set(Vector4f v) {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
        this.w = v.w;
        return this;
    }

    /**
     * Set the attributes to match the ones of the supplied javax.vecmath vector.
     * 
     * @param javaxVecmathVector
     * @return this
     */
    public Vector4f fromJavaxVector(javax.vecmath.Vector4f javaxVecmathVector) {
        x = javaxVecmathVector.x;
        y = javaxVecmathVector.y;
        z = javaxVecmathVector.z;
        w = javaxVecmathVector.w;
        return this;
    }

    /**
     * Set the x, y and z attributes to match the ones of the supplied org.lwjgl.util.vector vector.
     * 
     * @param lwjglVector
     * @return this
     */
    public Vector4f fromLwjglVector(org.lwjgl.util.vector.Vector4f lwjglVector) {
        this.x = lwjglVector.x;
        this.y = lwjglVector.y;
        this.z = lwjglVector.z;
        this.w = lwjglVector.w;
        return this;
    }
    
    /**
     * 
     * @param v
     * @param w
     * @return this
     */
    public Vector4f set(Vector3f v, float w) {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
        this.w = w;
        return this;
    }

    /**
     * 
     * @param x
     * @param y
     * @param z
     * @param w
     * @return this
     */
    public Vector4f set(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
        return this;
    }
    
    /**
     * Subtract the supplied vector from this one.
     * 
     * @return this
     */
    public Vector4f sub(Vector4f v) {
        x -= v.x;
        y -= v.y;
        z -= v.z;
        w -= v.w;
        return this;
    }

    /**
     * Subtract v2 from v1 and store the result in dest.
     */
    public static void sub(Vector4f v1, Vector4f v2, Vector4f dest) {
        dest.x = v1.x - v2.x;
        dest.y = v1.y - v2.y;
        dest.z = v1.z - v2.z;
        dest.w = v1.w - v2.w;
    }

    /**
     * Add the supplied vector to this one.
     * 
     * @return this
     */
    public Vector4f add(Vector4f v) {
        x += v.x;
        y += v.y;
        z += v.z;
        w += v.w;
        return this;
    }

    /**
     * Add v2 to v1 and store the result in dest.
     */
    public static void add(Vector4f v1, Vector4f v2, Vector4f dest) {
        dest.x = v1.x + v2.x;
        dest.y = v1.y + v2.y;
        dest.z = v1.z + v2.z;
        dest.w = v1.w + v2.w;
    }

    /**
     * Multiply this Vector4f by another Vector4f.
     * 
     * @return this
     */
    public Vector4f mul(Vector4f v) {
        x *= v.x;
        y *= v.y;
        z *= v.z;
        z *= v.w;
        return this;
    }

    /**
     * Multiply v1 by v2 component-wise and store the result into dest.
     */
    public static void mul(Vector4f v1, Vector4f v2, Vector4f dest) {
        dest.x = v1.x * v2.x;
        dest.y = v1.y * v2.y;
        dest.z = v1.z * v2.z;
        dest.w = v1.w * v2.w;
    }

    /**
     * Multiply this Vector4f by the given matrix mat and store the result in <code>this</code>.
     * 
     * @param mat
     *          the matrix to multiply the vector with
     * @return this
     */
    public Vector4f mul(Matrix4f mat) {
        mul(this, mat, this);
        return this;
    }

    /**
     * Multiply this Vector4f by the given matrix mat and store the result in <code>dest</code>.
     * 
     * @param mat
     *          the matrix to multiply the vector with
     * @param dest
     *          the destination vector to hold the result
     * @return this
     */
    public Vector4f mul(Matrix4f mat, Vector4f dest) {
        mul(this, mat, dest);
        return this;
    }

    /**
     * Multiply Vector4f <code>v</code> by the given matrix mat and store the
     * result in <code>dest</code>.
     * 
     * @param v
     *          the vector to multiply the matrix with
     * @param mat
     *          the matrix
     * @param dest
     *          will hold the result
     */
    public static void mul(Vector4f v, Matrix4f mat, Vector4f dest) {
        if (v != dest) {
            dest.x = mat.m00 * v.x + mat.m10 * v.y + mat.m20 * v.z + mat.m30 * v.w;
            dest.y = mat.m01 * v.x + mat.m11 * v.y + mat.m21 * v.z + mat.m31 * v.w;
            dest.z = mat.m02 * v.x + mat.m12 * v.y + mat.m22 * v.z + mat.m32 * v.w;
            dest.w = mat.m03 * v.x + mat.m13 * v.y + mat.m23 * v.z + mat.m33 * v.w;
        } else {
            dest.set(mat.m00 * v.x + mat.m10 * v.y + mat.m20 * v.z + mat.m30 * v.w,
                     mat.m01 * v.x + mat.m11 * v.y + mat.m21 * v.z + mat.m31 * v.w,
                     mat.m02 * v.x + mat.m12 * v.y + mat.m22 * v.z + mat.m32 * v.w, 
                     mat.m03 * v.x + mat.m13 * v.y + mat.m23 * v.z + mat.m33 * v.w);
        }
    }

    /**
     * Multiply this Vector4f by the given scalar value.
     * 
     * @return this
     */
    public Vector4f mul(float scalar) {
        x *= scalar;
        y *= scalar;
        z *= scalar;
        w *= scalar;
        return this;
    }

    /** Multiply the given Vector4f v by the scalar value, and store the result in dest.*/
    public static void mul(Vector4f v, float scalar, Vector4f dest) {
        dest.x = v.x * scalar;
        dest.y = v.y * scalar;
        dest.z = v.z * scalar;
        dest.w = v.w * scalar;
    }

    /**
     * Return the length squared of this vector.
     */
    public float lengthSquared() {
        return x * x + y * y + z * z + w * w;
    }

    /**
     * Return the length of this vector.
     */
    public float length() {
        return (float) Math.sqrt(lengthSquared());
    }

    /**
     * Normalizes this vector.
     * 
     * @return this
     */
    public Vector4f normalize() {
        float d = length();
        x /= d;
        y /= d;
        z /= d;
        w /= d;
        return this;
    }

    /**
     * Normalize the original vector and store the results in dest.
     */
    public static void normalize(Vector4f original, Vector4f dest) {
        float d = original.length();
        dest.set(original.x / d,
                original.y / d,
                original.z / d,
                original.w / d);
    }

    /**
     * Returns the distance between the start and end vectors.
     */
    public static float distance(Vector4f start, Vector4f end) {
        return (float) Math.sqrt(
                  (end.x - start.x) * (end.x - start.x)
                + (end.y - start.y) * (end.y - start.y)
                + (end.z - start.z) * (end.z - start.z)
                + (end.w - start.w) * (end.w - start.w));
    }
    
    /**
     * Returns the distance between this Vector and v.
     */
    public float distance(Vector4f v) {
        return (float) Math.sqrt(
                  (v.x - this.x) * (v.x - this.x)
                + (v.y - this.y) * (v.y - this.y)
                + (v.z - this.z) * (v.z - this.z)
                + (v.w - this.w) * (v.w - this.w));
    }
    
    /**
     * Compute the dot product (inner product) of this vector and the given vector. 
     * 
     * @param v
     * @return the dot product
     */
    public float dot(Vector4f v) {
        return (x * v.x) + (y * v.y) + (z * v.z) + (w * v.w);
    }
    
    /**
     * Return the dot product of the supplied v1 and v2 vectors.
     */
    public static float dot(Vector4f v1, Vector4f v2) {
        return (v1.x * v2.x) + (v1.y * v2.y) + (v1.z * v2.z) + (v1.w * v2.w);
    }
    
    /**
     * Set all components to zero.
     * 
     * @return this
     */
    public Vector4f zero() {
        this.x = 0.0f;
        this.y = 0.0f;
        this.z = 0.0f;
        this.w = 0.0f;
        return this;
    }

    public String toString() {
        return "Vector4f { " + x + ", " + y + ", " + z + ", " + w + " }";
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeFloat(x);
        out.writeFloat(y);
        out.writeFloat(z);
        out.writeFloat(w);
    }

    public void readExternal(ObjectInput in) throws IOException,
            ClassNotFoundException {
        x = in.readFloat();
        y = in.readFloat();
        z = in.readFloat();
        w = in.readFloat();
    }

    /**
     * Set the components of this vector to be the component-wise minimum of this and the other vector.
     *
     * @param v
     *          the other vector
     * @return this
     */
    public Vector4f min(Vector4f v) {
        this.x = Math.min(x, v.x);
        this.y = Math.min(y, v.y);
        this.z = Math.min(z, v.z);
        this.w = Math.min(w, v.w);
        return this;
    }

    /**
     * Set the components of this vector to be the component-wise maximum of this and the other vector.
     *
     * @param v
     *          the other vector
     * @return this
     */
    public Vector4f max(Vector4f v) {
        this.x = Math.max(x, v.x);
        this.y = Math.max(y, v.y);
        this.z = Math.max(z, v.z);
        this.w = Math.min(w, v.w);
        return this;
    }

}