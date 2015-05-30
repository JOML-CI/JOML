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
 * Contains the definition of a Vector comprising 3 floats and associated
 * transformations.
 *
 * @author Richard Greenlees
 * @author Kai Burjack
 */
public class Vector3f implements Serializable, Externalizable {

    public float x;
    public float y;
    public float z;

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
     * Set the x, y and z attributes to match the supplied vector.
     * 
     * @param v
     * @return this
     */
    public Vector3f set(Vector3f v) {
        x = v.x;
        y = v.y;
        z = v.z;
        return this;
    }

    /**
     * Set the x, y and z attributes to match the ones of the supplied javax.vecmath vector.
     * 
     * @param javaxVecmathVector
     * @return this
     */
    public Vector3f fromJavaxVector(javax.vecmath.Vector3f javaxVecmathVector) {
        x = javaxVecmathVector.x;
        y = javaxVecmathVector.y;
        z = javaxVecmathVector.z;
        return this;
    }
    
    /**
     * Set the x, y and z attributes to match the ones of the supplied org.lwjgl.util.vector vector.
     * 
     * @param lwjglVector
     * @return this
     */
    public Vector3f fromLwjglVector(org.lwjgl.util.vector.Vector3f lwjglVector) {
        this.x = lwjglVector.x;
        this.y = lwjglVector.y;
        this.z = lwjglVector.z;
        return this;
    }
    
    /**
     * Sets the x, y and z attributes to the supplied float values
     * 
     * @param x
     * @param y
     * @param z
     * @return this
     */
    public Vector3f set(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }

    /**
     * Subtracts the supplied vector from this one
     * 
     * @param v
     * @return this
     */
    public Vector3f sub(Vector3f v) {
        x -= v.x;
        y -= v.y;
        z -= v.z;
        return this;
    }

    /**
     * Subtracts v2 from v1 and stores the results in dest. Does not modify v1
     * or v2
     */
    public static void sub(Vector3f v1, Vector3f v2, Vector3f dest) {
        dest.x = v1.x - v2.x;
        dest.y = v1.y - v2.y;
        dest.z = v1.z - v2.z;
    }

    /**
     * Adds the supplied vector to this one
     * 
     * @param v
     * @return this
     */
    public Vector3f add(Vector3f v) {
        x += v.x;
        y += v.y;
        z += v.z;
        return this;
    }

    /**
     * Adds v2 to v1 and stores the results in dest. Does not modify v1 or v2
     */
    public static void add(Vector3f v1, Vector3f v2, Vector3f dest) {
        dest.x = v1.x + v2.x;
        dest.y = v1.y + v2.y;
        dest.z = v1.z + v2.z;
    }

    /**
     * Multiply this Vector3f by another Vector3f
     * 
     * @param v
     * @return this
     */
    public Vector3f mul(Vector3f v) {
        x *= v.x;
        y *= v.y;
        z *= v.z;
        return this;
    }

    /**
     * Multiply v1 by v2 component-wise and store the result into dest.
     */
    public static void mul(Vector3f v1, Vector3f v2, Vector3f dest) {
        dest.x = v1.x * v2.x;
        dest.y = v1.y * v2.y;
        dest.z = v1.z * v2.z;
    }

    /**
     * Multiply this Vector3f by the given matrix <code>mat</code>.
     * 
     * @param mat
     * @return this
     */
    public Vector3f mul(Matrix4f mat) {
        mul(this, mat, this);
        return this;
    }

    /**
     * Multiply Vector3f v by the given matrix mat and store the
     * result in dest.
     */
    public static void mul(Vector3f v, Matrix4f mat, Vector3f dest) {
        if (v != dest) {
            dest.x = mat.m00 * v.x + mat.m10 * v.y + mat.m20 * v.z;
            dest.y = mat.m01 * v.x + mat.m11 * v.y + mat.m21 * v.z;
            dest.z = mat.m02 * v.x + mat.m12 * v.y + mat.m22 * v.z;
        } else {
            dest.set(mat.m00 * v.x + mat.m10 * v.y + mat.m20 * v.z,
                     mat.m01 * v.x + mat.m11 * v.y + mat.m21 * v.z,
                     mat.m02 * v.x + mat.m12 * v.y + mat.m22 * v.z);
        }
    }

    /**
     * Multiply this Vector3f by the given rotation matrix mat.
     * 
     * @param mat
     * @return this
     */
    public Vector3f mul(Matrix3f mat) {
        return set(mat.m00 * x + mat.m10 * y + mat.m20 * z,
                   mat.m01 * x + mat.m11 * y + mat.m21 * z,
                   mat.m02 * x + mat.m12 * y + mat.m22 * z);
    }

    /**
     * Multiply Vector3f v by the given matrix mat and store the
     * result in dest.
     */
    public static void mul(Vector3f v, Matrix3f mat, Vector3f dest) {
        if (v != dest) {
            dest.x = mat.m00 * v.x + mat.m10 * v.y + mat.m20 * v.z;
            dest.y = mat.m01 * v.x + mat.m11 * v.y + mat.m21 * v.z;
            dest.z = mat.m02 * v.x + mat.m12 * v.y + mat.m22 * v.z;
        } else {
            dest.set(mat.m00 * v.x + mat.m10 * v.y + mat.m20 * v.z,
                     mat.m01 * v.x + mat.m11 * v.y + mat.m21 * v.z,
                     mat.m02 * v.x + mat.m12 * v.y + mat.m22 * v.z);
        }
    }

    /**
     * Multiply this Vector3f by the given scalar value.
     * 
     * @param scalar
     * @return this
     */
    public Vector3f mul(float scalar) {
        x *= scalar;
        y *= scalar;
        z *= scalar;
        return this;
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
     * Normalize this vector.
     * 
     * @return this
     */
    public Vector3f normalize() {
        float d = length();
        x /= d;
        y /= d;
        z /= d;
        return this;
    }

    /**
     * Normalize the original vector and store the results in dest.
     */
    public static void normalize(Vector3f original, Vector3f dest) {
        float d = original.length();
        dest.set(original.x / d,
                 original.y / d,
                 original.z / d);
    }

    /**
     * Set this vector to be the cross of itself and v.
     * 
     * @return this
     */
    public Vector3f cross(Vector3f v) {
        return set(y * v.z - z * v.y,
                   z * v.x - x * v.z,
                   x * v.y - y * v.x);
    }
    
    /**
     * Set this vector to be the cross of v1 and v2.
     * 
     * @return this
     */
    public Vector3f cross(Vector3f v1, Vector3f v2) {
        return set(v1.y * v2.z - v1.z * v2.y,
                   v1.z * v2.x - v1.x * v2.z,
                   v1.x * v2.y - v1.y * v2.x);
    }

    /**
     * Calculate the cross of v1 and v2 and store the results in dest.
     */
    public static void cross(Vector3f v1, Vector3f v2, Vector3f dest) {
        dest.set(v1.y * v2.z - v1.z * v2.y,
                 v1.z * v2.x - v1.x * v2.z,
                 v1.x * v2.y - v1.y * v2.x);
    }

    /**
     * Return the distance between the start and end vectors.
     */
    public static float distance(Vector3f start, Vector3f end) {
        return (float) Math.sqrt((end.x - start.x) * (end.x - start.x)
                + (end.y - start.y) * (end.y - start.y)
                + (end.z - start.z) * (end.z - start.z));
    }
    
    /**
     * Return the distance between this Vector and v.
     */
    public float distance(Vector3f v) {
        return (float) Math.sqrt((v.x - this.x) * (v.x - this.x)
                + (v.y - this.y) * (v.y - this.y)
                + (v.z - this.z) * (v.z - this.z));
    }

    /**
     * Return the dot product of this vector and the supplied vector.
     */
    public float dot(Vector3f v) {
        return (x * v.x) + (y * v.y) + (z * v.z);
    }
    
    /**
     * Return the dot product of the supplied v1 and v2 vectors.
     */
    public static float dot(Vector3f v1, Vector3f v2) {
        return (v1.x * v2.x) + (v1.y * v2.y) + (v1.z * v2.z);
    }

    /**
     * Set the components of this vector to be the component-wise minimum of this and the other vector.
     *
     * @param v
     *          the other vector
     * @return this
     */
    public Vector3f min(Vector3f v) {
        this.x = Math.min(x, v.x);
        this.y = Math.min(y, v.y);
        this.z = Math.min(z, v.z);
        return this;
    }

    /**
     * Set the components of this vector to be the component-wise maximum of this and the other vector.
     *
     * @param v
     *          the other vector
     * @return this
     */
    public Vector3f max(Vector3f v) {
        this.x = Math.max(x, v.x);
        this.y = Math.max(y, v.y);
        this.z = Math.max(z, v.z);
        return this;
    }

    /**
     * Set all components to zero.
     * 
     * @return this
     */
    public Vector3f zero() {
        this.x = 0.0f;
        this.y = 0.0f;
        this.z = 0.0f;
        return this;
    }

    public String toString() {
        return "Vector3f { " + x + ", " + y + ", " + z + " }";
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeFloat(x);
        out.writeFloat(y);
        out.writeFloat(z);
    }

    public void readExternal(ObjectInput in) throws IOException,
            ClassNotFoundException {
        x = in.readFloat();
        y = in.readFloat();
        z = in.readFloat();
    }

    public Vector3f negate() {
        x = -x;
        y = -y;
        z = -z;
        return this;
    }

}
