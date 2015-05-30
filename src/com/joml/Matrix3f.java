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
import java.nio.FloatBuffer;

/**
 * Contains the definition of a 3x3 Matrix of floats, and associated functions to transform
 * it. The matrix is column-major to match OpenGL's interpretation, and it looks like this:
 * <p>
 *      m00  m10  m20</br>
 *      m01  m11  m21</br>
 *      m02  m12  m22</br>
 * 
 * @author Richard Greenlees
 * @author Kai Burjack
 */
public class Matrix3f implements Serializable, Externalizable {

    public float m00;
    public float m01;
    public float m02;
    public float m10;
    public float m11;
    public float m12;
    public float m20;
    public float m21;
    public float m22;
    
    public Matrix3f() {
        super();
        identity();
    }

    public Matrix3f(Matrix3f mat) {
        this.m00 = mat.m00;
        this.m01 = mat.m01;
        this.m02 = mat.m02;
        this.m10 = mat.m10;
        this.m11 = mat.m11;
        this.m12 = mat.m12;
        this.m20 = mat.m20;
        this.m21 = mat.m21;
        this.m22 = mat.m22;
    }
    
    public Matrix3f(float m00, float m01, float m02, float m10, float m11,
                    float m12, float m20, float m21, float m22) {
        this.m00 = m00;
        this.m01 = m01;
        this.m02 = m02;
        this.m10 = m10;
        this.m11 = m11;
        this.m12 = m12;
        this.m20 = m20;
        this.m21 = m21;
        this.m22 = m22;
    }

    /** Set the values in this matrix to the ones in m1 */
    public Matrix3f set(Matrix3f m1) {
        m00 = m1.m00;
        m01 = m1.m01;
        m02 = m1.m02;
        m10 = m1.m10;
        m11 = m1.m11;
        m12 = m1.m12;
        m20 = m1.m20;
        m21 = m1.m21;
        m22 = m1.m22;
        return this;
    }

    /**
     * Set the values of this matrix to the ones of the given javax.vecmath.Matrix3f matrix.
     * 
     * @param javaxVecmathMatrix
     * @return this
     */
    public Matrix3f fromJavaxMatrix(javax.vecmath.Matrix3f javaxVecmathMatrix) {
        this.m00 = javaxVecmathMatrix.m00;
        this.m01 = javaxVecmathMatrix.m10;
        this.m02 = javaxVecmathMatrix.m20;
        this.m10 = javaxVecmathMatrix.m01;
        this.m11 = javaxVecmathMatrix.m11;
        this.m12 = javaxVecmathMatrix.m21;
        this.m20 = javaxVecmathMatrix.m02;
        this.m21 = javaxVecmathMatrix.m12;
        this.m22 = javaxVecmathMatrix.m22;
        return this;
    }

    /**
     * Set the values of this matrix to the ones of the given org.lwjgl.util.vector.Matrix3f matrix.
     * 
     * @param lwjglMatrix
     * @return this
     */
    public Matrix3f fromLwjglMatrix(org.lwjgl.util.vector.Matrix3f lwjglMatrix) {
        m00 = lwjglMatrix.m00;
        m01 = lwjglMatrix.m01;
        m02 = lwjglMatrix.m02;
        m10 = lwjglMatrix.m10;
        m11 = lwjglMatrix.m11;
        m12 = lwjglMatrix.m12;
        m20 = lwjglMatrix.m20;
        m21 = lwjglMatrix.m21;
        m22 = lwjglMatrix.m22;
        return this;
    }

    /**
     * Multiplies this matrix by the supplied matrix. This matrix will be the left-sided one.
     * 
     * @param right
     * @return this
     */
    public Matrix3f mul(Matrix3f right) {
        mul(this, right, this);
        return this;
    }

    /** Multiplies the left matrix by the right, and stores the results in dest. Does not modify the left or right matrices */
    public static void mul(Matrix3f left, Matrix3f right, Matrix3f dest) {
        if (left != dest && right != dest) {
            dest.m00 = left.m00 * right.m00 + left.m10 * right.m01 + left.m20 * right.m02;
            dest.m01 = left.m01 * right.m00 + left.m11 * right.m01 + left.m21 * right.m02;
            dest.m02 = left.m02 * right.m00 + left.m12 * right.m01 + left.m22 * right.m02;
            dest.m10 = left.m00 * right.m10 + left.m10 * right.m11 + left.m20 * right.m12;
            dest.m11 = left.m01 * right.m10 + left.m11 * right.m11 + left.m21 * right.m12;
            dest.m12 = left.m02 * right.m10 + left.m12 * right.m11 + left.m22 * right.m12;
            dest.m20 = left.m00 * right.m20 + left.m10 * right.m21 + left.m20 * right.m22;
            dest.m21 = left.m01 * right.m20 + left.m11 * right.m21 + left.m21 * right.m22;
            dest.m22 = left.m02 * right.m20 + left.m12 * right.m21 + left.m22 * right.m22;
        } else {
            dest.set( left.m00 * right.m00 + left.m10 * right.m01 + left.m20 * right.m02,
                      left.m01 * right.m00 + left.m11 * right.m01 + left.m21 * right.m02,
                      left.m02 * right.m00 + left.m12 * right.m01 + left.m22 * right.m02,
                      left.m00 * right.m10 + left.m10 * right.m11 + left.m20 * right.m12,
                      left.m01 * right.m10 + left.m11 * right.m11 + left.m21 * right.m12,
                      left.m02 * right.m10 + left.m12 * right.m11 + left.m22 * right.m12,
                      left.m00 * right.m20 + left.m10 * right.m21 + left.m20 * right.m22,
                      left.m01 * right.m20 + left.m11 * right.m21 + left.m21 * right.m22,
                      left.m02 * right.m20 + left.m12 * right.m21 + left.m22 * right.m22 );
        }
    }
    
    /** Sets the values within this matrix to the supplied float values. The result looks like this:
     * <p>
     * m00, m10, m20</br>
     * m01, m11, m21</br>
     * m02, m12, m22</br>
     */
    public Matrix3f set(float m00, float m01, float m02, float m10, float m11,
                    float m12, float m20, float m21, float m22) {
        this.m00 = m00;
        this.m01 = m01;
        this.m02 = m02;
        this.m10 = m10;
        this.m11 = m11;
        this.m12 = m12;
        this.m20 = m20;
        this.m21 = m21;
        this.m22 = m22;
        return this;
    }

    /** Sets the values in this matrix based on the supplied float array. The result looks like this:
     * <p>
     * 0, 3, 6</br>
     * 1, 4, 7</br>
     * 2, 5, 8</br>
     * 
     * Only uses the first 9 values, all others are ignored
     * 
     * @return this
     */
    public Matrix3f set(float m[]) {
        m00 = m[0];
        m01 = m[1];
        m02 = m[2];
        m10 = m[3];
        m11 = m[4];
        m12 = m[5];
        m20 = m[6];
        m21 = m[7];
        m22 = m[8];
        return this;
    }

    /** Returns the determinant of this Matrix */
    public float determinant() {
        return   ((m00 * m11 * m22)
               + (m10 * m21 * m02)
               + (m20 * m01 * m12))
               - ((m20 * m11 * m02)
               + (m00 * m21 * m12)
               + (m10 * m01 * m22));
    }

    /**
     * Invert this matrix.
     *
     * @return this
     */
    public Matrix3f invert() {
        float s = determinant();
        
        if (s == 0.0f) {
            return this;
        }
        s = 1.0f / s;

        return set((m11 * m22) - (m21 * m12) * s,
                 -((m01 * m22) - (m21 * m02)) * s,
                   (m01 * m12) - (m11 * m02) * s,
                 -((m10 * m22) - (m20 * m12)) * s,
                   (m00 * m22) - (m20 * m02) * s,
                 -((m00 * m12) - (m10 * m02)) * s,
                   (m10 * m21) - (m20 * m11) * s,
                 -((m00 * m21) - (m20 * m01)) * s,
                   (m00 * m11) - (m10 * m01) * s);
    }
    
    /**
     * Invert the source matrix and store the results in dest.
     */
    public static void invert(Matrix3f source, Matrix3f dest) {
        float s = source.determinant();
        if (s == 0.0f) {
            return;
        }
        s = 1.0f / s;
        if (source != dest) {
            dest.m00 = ((source.m11 * source.m22) - (source.m21 * source.m12)) * s;
            dest.m01 = -((source.m01 * source.m22) - (source.m21 * source.m02)) * s;
            dest.m02 = ((source.m01 * source.m12) - (source.m11 * source.m02)) * s;
            dest.m10 = -((source.m10 * source.m22) - (source.m20 * source.m12)) * s;
            dest.m11 = ((source.m00 * source.m22) - (source.m20 * source.m02)) * s;
            dest.m12 = -((source.m00 * source.m12) - (source.m10 * source.m02)) * s;
            dest.m20 = ((source.m10 * source.m21) - (source.m20 * source.m11)) * s;
            dest.m21 = -((source.m00 * source.m21) - (source.m20 * source.m01)) * s;
            dest.m22 = ((source.m00 * source.m11) - (source.m10 * source.m01)) * s;
        } else {
            dest.set(  ((source.m11 * source.m22) - (source.m21 * source.m12)) * s,
                      -((source.m01 * source.m22) - (source.m21 * source.m02)) * s,
                       ((source.m01 * source.m12) - (source.m11 * source.m02)) * s,
                      -((source.m10 * source.m22) - (source.m20 * source.m12)) * s,
                       ((source.m00 * source.m22) - (source.m20 * source.m02)) * s,
                      -((source.m00 * source.m12) - (source.m10 * source.m02)) * s,
                       ((source.m10 * source.m21) - (source.m20 * source.m11)) * s,
                      -((source.m00 * source.m21) - (source.m20 * source.m01)) * s,
                       ((source.m00 * source.m11) - (source.m10 * source.m01)) * s  );
        }
    }
    
    /**
     * Transpose this matrix.
     *
     * @return this
     */
    public Matrix3f transpose() {
        transpose(this, this);
        return this;
    }
    
    /**
     * Transpose the supplied original matrix and store the results in dest.
     */
    public static void transpose(Matrix3f original, Matrix3f dest) {
        if (original != dest) {
            dest.m00 = original.m00;
            dest.m01 = original.m10;
            dest.m02 = original.m20;
            dest.m10 = original.m01;
            dest.m11 = original.m11;
            dest.m12 = original.m21;
            dest.m20 = original.m02;
            dest.m21 = original.m12;
            dest.m22 = original.m22;
        } else {
            dest.set(original.m00, original.m10, original.m20,
                     original.m01, original.m11, original.m21,
                     original.m02, original.m12, original.m22);
        }
    }
    
    /**
     * Set this matrix to be a simple translation matrix.
     * <p>
     * The resulting matrix can be multiplied against another transformation
     * matrix to obtain an additional translation.
     */
    public Matrix3f translation(float x, float y) {
        this.m00 = 1.0f;
        this.m01 = 0.0f;
        this.m02 = 0.0f;
        this.m10 = 0.0f;
        this.m11 = 1.0f;
        this.m12 = 0.0f;
        this.m20 = x;
        this.m21 = y;
        this.m22 = 1.0f;
        return this;
    }

    /**
     * Set the given matrix to be a simple translation matrix.
     * <p>
     * The resulting matrix can be multiplied against another transformation
     * matrix to obtain an additional translation.
     */
    public static void translation(Vector2f position, Matrix3f dest) {
        dest.translation(position.x, position.y);
    }

    /**
     * Set the given matrix to be a simple translation matrix.
     * <p>
     * The resulting matrix can be multiplied against another transformation
     * matrix to obtain an additional translation.
     */
    public static void translation(float x, float y, Matrix3f dest) {
        dest.translation(x, y);
    }

    /**
     * Set this matrix to be a simple translation matrix.
     * <p>
     * The resulting matrix can be multiplied against another transformation
     * matrix to obtain an additional translation.
     */
    public Matrix3f translation(Vector2f position) {
        return translation(position.x, position.y);
    }
    
    /** Multiply the supplied Matrix by the supplied scalar value and store the results in dest. Does not modify the source */
    public static void mul(Matrix3f source, float scalar, Matrix3f dest) {
        dest.m00 = source.m00 * scalar;
        dest.m01 = source.m01 * scalar;
        dest.m02 = source.m02 * scalar;
        dest.m10 = source.m10 * scalar;
        dest.m11 = source.m11 * scalar;
        dest.m12 = source.m12 * scalar;
        dest.m20 = source.m20 * scalar;
        dest.m21 = source.m21 * scalar;
        dest.m22 = source.m22 * scalar;
    }
    
    public String toString() {
        return "Matrix3f { " + this.m00 + ", " + this.m10 + ", " + this.m20 + ",\n"
                + "           " + this.m01 + ", " + this.m11 + ", " + this.m21 + ",\n"
                + "           " + this.m02 + ", " + this.m12 + ", " + this.m22 + " }\n";

    }

    /**
     * Get the current values of <code>this</code> matrix and store them into
     * <code>dest</code>.
     * <p>
     * This is the reverse method of {@link #set(Matrix3f)} and allows to obtain
     * intermediate calculation results when chaining multiple transformations.
     * 
     * @param dest
     *          the destination matrix
     * @return this
     */
    public Matrix3f get(Matrix3f dest) {
        dest.set(this);
        return this;
    }

    /** Stores this matrix in the supplied FloatBuffer */
    public Matrix3f get(FloatBuffer buffer) {
        buffer.put(this.m00);
        buffer.put(this.m01);
        buffer.put(this.m02);
        buffer.put(this.m10);
        buffer.put(this.m11);
        buffer.put(this.m12);
        buffer.put(this.m20);
        buffer.put(this.m21);
        buffer.put(this.m22);
        return this;
    }

    /** Read and set the matrix values from the supplied FloatBuffer */
    public Matrix3f set(FloatBuffer buffer) {
        this.m00 = buffer.get();
        this.m01 = buffer.get();
        this.m02 = buffer.get();
        this.m10 = buffer.get();
        this.m11 = buffer.get();
        this.m12 = buffer.get();
        this.m20 = buffer.get();
        this.m21 = buffer.get();
        this.m22 = buffer.get();
        return this;
    }

    /** Sets all the values within this matrix to 0 */
    public Matrix3f zero() {
        this.m00 = 0.0f;
        this.m01 = 0.0f;
        this.m02 = 0.0f;
        this.m10 = 0.0f;
        this.m11 = 0.0f;
        this.m12 = 0.0f;
        this.m20 = 0.0f;
        this.m21 = 0.0f;
        this.m22 = 0.0f;
        return this;
    }
    
    /** Sets this matrix to the identity */
    public Matrix3f identity() {
        this.m00 = 1.0f;
        this.m01 = 0.0f;
        this.m02 = 0.0f;
        this.m10 = 0.0f;
        this.m11 = 1.0f;
        this.m12 = 0.0f;
        this.m20 = 0.0f;
        this.m21 = 0.0f;
        this.m22 = 1.0f;
        return this;
    }

    /**
     * Set this matrix to be a simple scale matrix.
     * 
     * @param x
     * 			the scale in x
     * @param y
     * 			the scale in y
     * @param z
     * 			the scale in z
     * @return this
     */
    public Matrix3f scale(float x, float y, float z) {
    	identity();
        m00 = x;
        m11 = y;
        m22 = z;
        return this;
    }
    
    /**
     * Set this matrix to be a simple scale matrix.
     * 
     * @param scale
     * 			the scale applied to each dimension
     * @return this
     */
    public Matrix3f scale(Vector3f scale) {
    	identity();
        m00 = scale.x;
        m11 = scale.y;
        m22 = scale.z;
        return this;
    }
    
    /**
     * Set the given matrix <code>dest</code> to be a simple scale matrix.
     * 
     * @param scale
     * 			the scale applied to each dimension
     */
    public static void scaling(Vector3f scale, Matrix3f dest) {
    	dest.identity();
        dest.m00 = scale.x;
        dest.m11 = scale.y;
        dest.m22 = scale.z;
    }
    
    /**
     * Set this matrix to be a simple scale matrix.
     * 
     * @param x
     * 			the scale in x
     * @param y
     * 			the scale in y
     * @param z
     * 			the scale in z
     * @return this
     */
    public Matrix3f scaling(float x, float y, float z, Matrix3f dest) {
    	dest.identity();
    	dest.m00 = x;
    	dest.m11 = y;
    	dest.m22 = z;
    	return this;
    }

    /**
     * Set this matrix to a rotation matrix which rotates the given radians about the given axis.
     * 
     * From <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Rotation_matrix_from_axis_and_angle">Wikipedia</a>
     * 
     * @return this
     */
    public Matrix3f rotation(float angle, float x, float y, float z) {
    	float cos = (float) Math.cos(angle);
    	float sin = (float) Math.sin(angle);
    	m00 = cos + x * x * (1.0f - cos);
    	m10 = x * y * (1.0f - cos) - z * sin;
    	m20 = x * z * (1.0f - cos) + y * sin;
    	m01 = y * x * (1.0f - cos) + z * sin;
    	m11 = cos + y * y * (1.0f - cos);
    	m21 = y * z * (1.0f - cos) - x * sin;
    	m02 = z * x * (1.0f - cos) - y * sin;
    	m12 = z * y * (1.0f - cos) + x * sin;
    	m22 = cos + z * z * (1.0f - cos);
    	return this;
    }

    /**
     * Set this matrix to a rotation matrix which rotates the given radians about the given axis.
     * 
     * @return this
     */
    public Matrix3f rotation(float angle, Vector3f axis) {
        return rotation(angle, axis.x, axis.y, axis.z);
    }

    /**
     * Set the destination matrix to a rotation matrix which rotates the given radians about a given axis.
     * 
     * From <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Rotation_matrix_from_axis_and_angle">Wikipedia</a>
     */
    public static void rotation(float angle, float x, float y, float z, Matrix3f dest) {
    	float cos = (float) Math.cos(angle);
    	float sin = (float) Math.sin(angle);
    	dest.m00 = cos + x * x * (1.0f - cos);
    	dest.m10 = x * y * (1.0f - cos) - z * sin;
    	dest.m20 = x * z * (1.0f - cos) + y * sin;
    	dest.m01 = y * x * (1.0f - cos) + z * sin;
    	dest.m11 = cos + y * y * (1.0f - cos);
    	dest.m21 = y * z * (1.0f - cos) - x * sin;
    	dest.m02 = z * x * (1.0f - cos) - y * sin;
    	dest.m12 = z * y * (1.0f - cos) + x * sin;
    	dest.m22 = cos + z * z * (1.0f - cos);
    }
    
    public static void rotation(float angle, Vector3f axis, Matrix3f dest) {
        rotation(angle, axis.x, axis.y, axis.z, dest);
    }

    /**
     * Transform the given vector by this matrix.
     * 
     * @param v
     * @return this
     */
    public Matrix3f transform(Vector3f v) {
        v.mul(this);
        return this;
    }

    /**
     * Transform the given vector by this matrix and store the result in <code>dest</code>.
     * 
     * @param v
     * @param dest
     * @return this
     */
    public Matrix3f transform(Vector3f v, Vector3f dest) {
        v.mul(this, dest);
        return this;
    }

    /**
     * Transform the given vector by the given matrix.
     * 
     * @param mat
     * @param v
     */
    public static void transform(Matrix3f mat, Vector3f v) {
        v.mul(mat);
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeFloat(m00);
        out.writeFloat(m01);
        out.writeFloat(m02);
        out.writeFloat(m10);
        out.writeFloat(m11);
        out.writeFloat(m12);
        out.writeFloat(m20);
        out.writeFloat(m21);
        out.writeFloat(m22);
    }

    public void readExternal(ObjectInput in) throws IOException,
            ClassNotFoundException {
        m00 = in.readFloat();
        m01 = in.readFloat();
        m02 = in.readFloat();
        m10 = in.readFloat();
        m11 = in.readFloat();
        m12 = in.readFloat();
        m20 = in.readFloat();
        m21 = in.readFloat();
        m22 = in.readFloat();
    }

    /**
     * Apply the rotation transformation of the given {@link Quaternion} to this matrix.
     * 
     * @param quat
     *          the {@link Quaternion}
     * @return this
     */
    public Matrix3f mul(Quaternion quat) {
        float q00 = 2.0f * quat.x * quat.x;
        float q11 = 2.0f * quat.y * quat.y;
        float q22 = 2.0f * quat.z * quat.z;
        float q01 = 2.0f * quat.x * quat.y;
        float q02 = 2.0f * quat.x * quat.z;
        float q03 = 2.0f * quat.x * quat.w;
        float q12 = 2.0f * quat.y * quat.z;
        float q13 = 2.0f * quat.y * quat.w;
        float q23 = 2.0f * quat.z * quat.w;

        float rm00 = 1.0f - q11 - q22;
        float rm01 = q01 + q23;
        float rm02 = q02 - q13;
        float rm10 = q01 - q23;
        float rm11 = 1.0f - q22 - q00;
        float rm12 = q12 + q03;
        float rm20 = q02 + q13;
        float rm21 = q12 - q03;
        float rm22 = 1.0f - q11 - q00;

        float nm00 = m00 * rm00 + m10 * rm01 + m20 * rm02;
        float nm01 = m01 * rm00 + m11 * rm01 + m21 * rm02;
        float nm02 = m02 * rm00 + m12 * rm01 + m22 * rm02;
        float nm10 = m00 * rm10 + m10 * rm11 + m20 * rm12;
        float nm11 = m01 * rm10 + m11 * rm11 + m21 * rm12;
        float nm12 = m02 * rm10 + m12 * rm11 + m22 * rm12;
        m20 = m00 * rm20 + m10 * rm21 + m20 * rm22;
        m21 = m01 * rm20 + m11 * rm21 + m21 * rm22;
        m22 = m02 * rm20 + m12 * rm21 + m22 * rm22;
        this.m00 = nm00;
        this.m01 = nm01;
        this.m02 = nm02;
        this.m10 = nm10;
        this.m11 = nm11;
        this.m12 = nm12;

        return this;
    }

}
