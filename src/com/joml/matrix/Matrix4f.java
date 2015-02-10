/*
 * Feel free to do whatever you want with this code, all I've done is
 * pull together common knowledge into one easy package. Use it as a
 * base for your own work, copy/paste bits or integrate it into your
 * existing project, it's all good. Just add a thanks to me somewhere.
 */
package com.joml.matrix;

import com.joml.vector.Vector3f;
import com.joml.vector.Vector4f;
import java.nio.FloatBuffer;

/**
 * Matrix4f
 * 
 * Contains the definition of a 4x4 Matrix of floats, and associated functions to transform
 * it. The matrix is column-major to match OpenGL's interpretation, and it looks like this:
 * 
 *      m00  m10  m20  m30
 *      m01  m11  m21  m31
 *      m02  m12  m22  m32
 *      m03  m13  m23  m33
 * 
 * @author Richard Greenlees
 */

public class Matrix4f {
    
    public float m00;
    public float m01;
    public float m02;
    public float m03;
    public float m10;
    public float m11;
    public float m12;
    public float m13;
    public float m20;
    public float m21;
    public float m22;
    public float m23;
    public float m30;
    public float m31;
    public float m32;
    public float m33;

    public Matrix4f() {
        super();
    }

    public Matrix4f(float diagonal) {
        super();
        this.m00 = diagonal;
        this.m11 = diagonal;
        this.m22 = diagonal;
        this.m33 = diagonal;
    }

    /** Clones this matrix from the supplied matrix */
    public Matrix4f(Matrix4f mat) {
        this.m00 = mat.m00;
        this.m01 = mat.m01;
        this.m02 = mat.m02;
        this.m03 = mat.m03;
        this.m10 = mat.m10;
        this.m11 = mat.m11;
        this.m12 = mat.m12;
        this.m13 = mat.m13;
        this.m20 = mat.m20;
        this.m21 = mat.m21;
        this.m22 = mat.m22;
        this.m23 = mat.m23;
        this.m30 = mat.m30;
        this.m31 = mat.m31;
        this.m32 = mat.m32;
        this.m33 = mat.m33;
    }
    
    /** Create a new 4x4 matrix using the supplied float values */
    public Matrix4f(float m00, float m01, float m02, float m03, float m10, float m11, float m12, float m13, float m20,
            float m21, float m22, float m23, float m30, float m31, float m32, float m33) {
        this.m00 = m00;
        this.m01 = m01;
        this.m02 = m02;
        this.m03 = m03;
        this.m10 = m10;
        this.m11 = m11;
        this.m12 = m12;
        this.m13 = m13;
        this.m20 = m20;
        this.m21 = m21;
        this.m22 = m22;
        this.m23 = m23;
        this.m30 = m30;
        this.m31 = m31;
        this.m32 = m32;
        this.m33 = m33;
    }
    
    /** Resets this matrix to the identity */
    public void identity() {
        this.m00 = 1.0f;
        this.m01 = 0.0f;
        this.m02 = 0.0f;
        this.m03 = 0.0f;
        this.m10 = 0.0f;
        this.m11 = 1.0f;
        this.m12 = 0.0f;
        this.m13 = 0.0f;
        this.m20 = 0.0f;
        this.m21 = 0.0f;
        this.m22 = 1.0f;
        this.m23 = 0.0f;
        this.m30 = 0.0f;
        this.m31 = 0.0f;
        this.m32 = 0.0f;
        this.m33 = 1.0f;
    }

    /** Set the values within this matrix to be the same as the supplied parameter matrix */
    public void set(Matrix4f m1) {
        this.m00 = m1.m00;
        this.m01 = m1.m01;
        this.m02 = m1.m02;
        this.m03 = m1.m03;
        this.m10 = m1.m10;
        this.m11 = m1.m11;
        this.m12 = m1.m12;
        this.m13 = m1.m13;
        this.m20 = m1.m20;
        this.m21 = m1.m21;
        this.m22 = m1.m22;
        this.m23 = m1.m23;
        this.m30 = m1.m30;
        this.m31 = m1.m31;
        this.m32 = m1.m32;
        this.m33 = m1.m33;
    }
    
    public Matrix4f(FloatBuffer buffer) {
        m00 = buffer.get();
        m01 = buffer.get();
        m02 = buffer.get();
        m03 = buffer.get();
        m10 = buffer.get();
        m11 = buffer.get();
        m12 = buffer.get();
        m13 = buffer.get();
        m20 = buffer.get();
        m21 = buffer.get();
        m22 = buffer.get();
        m23 = buffer.get();
        m30 = buffer.get();
        m31 = buffer.get();
        m32 = buffer.get();
        m33 = buffer.get();
    }

    /** Multiply this matrix by the supplied parameter matrix. This matrix will be treated as the left */
    public void mul(Matrix4f right) {
       set( this.m00 * right.m00 + this.m10 * right.m01 + this.m20 * right.m02 + this.m30 * right.m03,
            this.m01 * right.m00 + this.m11 * right.m01 + this.m21 * right.m02 + this.m31 * right.m03,
            this.m02 * right.m00 + this.m12 * right.m01 + this.m22 * right.m02 + this.m32 * right.m03,
            this.m03 * right.m00 + this.m13 * right.m01 + this.m23 * right.m02 + this.m33 * right.m03,
            this.m00 * right.m10 + this.m10 * right.m11 + this.m20 * right.m12 + this.m30 * right.m13,
            this.m01 * right.m10 + this.m11 * right.m11 + this.m21 * right.m12 + this.m31 * right.m13,
            this.m02 * right.m10 + this.m12 * right.m11 + this.m22 * right.m12 + this.m32 * right.m13,
            this.m03 * right.m10 + this.m13 * right.m11 + this.m23 * right.m12 + this.m33 * right.m13,
            this.m00 * right.m20 + this.m10 * right.m21 + this.m20 * right.m22 + this.m30 * right.m23,
            this.m01 * right.m20 + this.m11 * right.m21 + this.m21 * right.m22 + this.m31 * right.m23,
            this.m02 * right.m20 + this.m12 * right.m21 + this.m22 * right.m22 + this.m32 * right.m23,
            this.m03 * right.m20 + this.m13 * right.m21 + this.m23 * right.m22 + this.m33 * right.m23,
            this.m00 * right.m30 + this.m10 * right.m31 + this.m20 * right.m32 + this.m30 * right.m33,
            this.m01 * right.m30 + this.m11 * right.m31 + this.m21 * right.m32 + this.m31 * right.m33,
            this.m02 * right.m30 + this.m12 * right.m31 + this.m22 * right.m32 + this.m32 * right.m33,
            this.m03 * right.m30 + this.m13 * right.m31 + this.m23 * right.m32 + this.m33 * right.m33 );
    }
    
    /** Multiply the supplied left matrix by the right, and store the results into dest. Function is alias safe. */
    public static void mul(Matrix4f left, Matrix4f right, Matrix4f dest) {
        dest.set(left.m00 * right.m00 + left.m10 * right.m01 + left.m20 * right.m02 + left.m30 * right.m03,
                 left.m01 * right.m00 + left.m11 * right.m01 + left.m21 * right.m02 + left.m31 * right.m03,
                 left.m02 * right.m00 + left.m12 * right.m01 + left.m22 * right.m02 + left.m32 * right.m03,
                 left.m03 * right.m00 + left.m13 * right.m01 + left.m23 * right.m02 + left.m33 * right.m03,
                 left.m00 * right.m10 + left.m10 * right.m11 + left.m20 * right.m12 + left.m30 * right.m13,
                 left.m01 * right.m10 + left.m11 * right.m11 + left.m21 * right.m12 + left.m31 * right.m13,
                 left.m02 * right.m10 + left.m12 * right.m11 + left.m22 * right.m12 + left.m32 * right.m13,
                 left.m03 * right.m10 + left.m13 * right.m11 + left.m23 * right.m12 + left.m33 * right.m13,
                 left.m00 * right.m20 + left.m10 * right.m21 + left.m20 * right.m22 + left.m30 * right.m23,
                 left.m01 * right.m20 + left.m11 * right.m21 + left.m21 * right.m22 + left.m31 * right.m23,
                 left.m02 * right.m20 + left.m12 * right.m21 + left.m22 * right.m22 + left.m32 * right.m23,
                 left.m03 * right.m20 + left.m13 * right.m21 + left.m23 * right.m22 + left.m33 * right.m23,
                 left.m00 * right.m30 + left.m10 * right.m31 + left.m20 * right.m32 + left.m30 * right.m33,
                 left.m01 * right.m30 + left.m11 * right.m31 + left.m21 * right.m32 + left.m31 * right.m33,
                 left.m02 * right.m30 + left.m12 * right.m31 + left.m22 * right.m32 + left.m32 * right.m33,
                 left.m03 * right.m30 + left.m13 * right.m31 + left.m23 * right.m32 + left.m33 * right.m33);
    }
    
    /** Multiplies the left matrix by the right, and stores the result into dest. <B>This is not alias safe 
     so make sure dest is not the same as the left or right parameters or you WILL get incorrect results!</B> */
    public static void mulFast(Matrix4f left, Matrix4f right, Matrix4f dest) {

        dest.m00 = left.m00 * right.m00 + left.m10 * right.m01 + left.m20 * right.m02 + left.m30 * right.m03;
        dest.m01 = left.m01 * right.m00 + left.m11 * right.m01 + left.m21 * right.m02 + left.m31 * right.m03;
        dest.m02 = left.m02 * right.m00 + left.m12 * right.m01 + left.m22 * right.m02 + left.m32 * right.m03;
        dest.m03 = left.m03 * right.m00 + left.m13 * right.m01 + left.m23 * right.m02 + left.m33 * right.m03;
        dest.m10 = left.m00 * right.m10 + left.m10 * right.m11 + left.m20 * right.m12 + left.m30 * right.m13;
        dest.m11 = left.m01 * right.m10 + left.m11 * right.m11 + left.m21 * right.m12 + left.m31 * right.m13;
        dest.m12 = left.m02 * right.m10 + left.m12 * right.m11 + left.m22 * right.m12 + left.m32 * right.m13;
        dest.m13 = left.m03 * right.m10 + left.m13 * right.m11 + left.m23 * right.m12 + left.m33 * right.m13;
        dest.m20 = left.m00 * right.m20 + left.m10 * right.m21 + left.m20 * right.m22 + left.m30 * right.m23;
        dest.m21 = left.m01 * right.m20 + left.m11 * right.m21 + left.m21 * right.m22 + left.m31 * right.m23;
        dest.m22 = left.m02 * right.m20 + left.m12 * right.m21 + left.m22 * right.m22 + left.m32 * right.m23;
        dest.m23 = left.m03 * right.m20 + left.m13 * right.m21 + left.m23 * right.m22 + left.m33 * right.m23;
        dest.m30 = left.m00 * right.m30 + left.m10 * right.m31 + left.m20 * right.m32 + left.m30 * right.m33;
        dest.m31 = left.m01 * right.m30 + left.m11 * right.m31 + left.m21 * right.m32 + left.m31 * right.m33;
        dest.m32 = left.m02 * right.m30 + left.m12 * right.m31 + left.m22 * right.m32 + left.m32 * right.m33;
        dest.m33 = left.m03 * right.m30 + left.m13 * right.m31 + left.m23 * right.m32 + left.m33 * right.m33;
    }
    
    /** Multiplies the left matrix by the right, and stores the result into the destination FloatBuffer */
    public static void mul(Matrix4f left, Matrix4f right, FloatBuffer dest) {
        dest.put(left.m00 * right.m00 + left.m10 * right.m01 + left.m20 * right.m02 + left.m30 * right.m03);
        dest.put(left.m01 * right.m00 + left.m11 * right.m01 + left.m21 * right.m02 + left.m31 * right.m03);
        dest.put(left.m02 * right.m00 + left.m12 * right.m01 + left.m22 * right.m02 + left.m32 * right.m03);
        dest.put(left.m03 * right.m00 + left.m13 * right.m01 + left.m23 * right.m02 + left.m33 * right.m03);
        dest.put(left.m00 * right.m10 + left.m10 * right.m11 + left.m20 * right.m12 + left.m30 * right.m13);
        dest.put(left.m01 * right.m10 + left.m11 * right.m11 + left.m21 * right.m12 + left.m31 * right.m13);
        dest.put(left.m02 * right.m10 + left.m12 * right.m11 + left.m22 * right.m12 + left.m32 * right.m13);
        dest.put(left.m03 * right.m10 + left.m13 * right.m11 + left.m23 * right.m12 + left.m33 * right.m13);
        dest.put(left.m00 * right.m20 + left.m10 * right.m21 + left.m20 * right.m22 + left.m30 * right.m23);
        dest.put(left.m01 * right.m20 + left.m11 * right.m21 + left.m21 * right.m22 + left.m31 * right.m23);
        dest.put(left.m02 * right.m20 + left.m12 * right.m21 + left.m22 * right.m22 + left.m32 * right.m23);
        dest.put(left.m03 * right.m20 + left.m13 * right.m21 + left.m23 * right.m22 + left.m33 * right.m23);
        dest.put(left.m00 * right.m30 + left.m10 * right.m31 + left.m20 * right.m32 + left.m30 * right.m33);
        dest.put(left.m01 * right.m30 + left.m11 * right.m31 + left.m21 * right.m32 + left.m31 * right.m33);
        dest.put(left.m02 * right.m30 + left.m12 * right.m31 + left.m22 * right.m32 + left.m32 * right.m33);
        dest.put(left.m03 * right.m30 + left.m13 * right.m31 + left.m23 * right.m32 + left.m33 * right.m33);
    }

    /** Set the values within this matrix to the supplied float values. The matrix will look like this:<br><br>
        
        m00, m10, m20, m30<br>
        m01, m11, m21, m31<br>
        m02, m12, m22, m32<br>
        m03, m13, m23, m33*/
    public void set(float m00, float m01, float m02, float m03, float m10, float m11, float m12, float m13, float m20,
            float m21, float m22, float m23, float m30, float m31, float m32, float m33) {
        this.m00 = m00;
        this.m01 = m01;
        this.m02 = m02;
        this.m03 = m03;
        this.m10 = m10;
        this.m11 = m11;
        this.m12 = m12;
        this.m13 = m13;
        this.m20 = m20;
        this.m21 = m21;
        this.m22 = m22;
        this.m23 = m23;
        this.m30 = m30;
        this.m31 = m31;
        this.m32 = m32;
        this.m33 = m33;
    }

    /** Set the values in the matrix using a float array. The results will look like this:<br><br>
     * 
     * 0, 4, 8, 12<br>
     * 1, 5, 9, 13<br>
     * 2, 6, 10, 14<br>
     * 3, 7, 11, 15<br>
     */
    public void set(float m[]) {
        m00 = m[0];
        m01 = m[1];
        m02 = m[2];
        m03 = m[3];
        m10 = m[4];
        m11 = m[5];
        m12 = m[6];
        m13 = m[7];
        m20 = m[8];
        m21 = m[9];
        m22 = m[10];
        m23 = m[11];
        m30 = m[12];
        m31 = m[13];
        m32 = m[14];
        m33 = m[15];
    }
    
    /** Set the values in the matrix using a FloatBuffer. The results will look like this:<br><br>
     * 
     * 0, 4, 8, 12<br>
     * 1, 5, 9, 13<br>
     * 2, 6, 10, 14<br>
     * 3, 7, 11, 15<br>
     */
    public void set(FloatBuffer buffer) {
        m00 = buffer.get();
        m01 = buffer.get();
        m02 = buffer.get();
        m03 = buffer.get();
        m10 = buffer.get();
        m11 = buffer.get();
        m12 = buffer.get();
        m13 = buffer.get();
        m20 = buffer.get();
        m21 = buffer.get();
        m22 = buffer.get();
        m23 = buffer.get();
        m30 = buffer.get();
        m31 = buffer.get();
        m32 = buffer.get();
        m33 = buffer.get();
    }
    
    /** Returns the determinant of this matrix */
    public float determinant() {
        return     (m00 * m11 - m01 * m10) * (m22 * m33 - m23 * m32) - (m00 * m12 - m02 * m10) * (m21 * m33 - m23 * m31)
                 + (m00 * m13 - m03 * m10) * (m21 * m32 - m22 * m31) + (m01 * m12 - m02 * m11) * (m20 * m33 - m23 * m30)
                 - (m01 * m13 - m03 * m11) * (m20 * m32 - m22 * m30) + (m02 * m13 - m03 * m12) * (m20 * m31 - m21 * m30);
    }
    
    /** Returns the determinant of the supplied matrix */
    public static float determinant(Matrix4f source) {
        return     (source.m00 * source.m11 - source.m01 * source.m10) * (source.m22 * source.m33 - source.m23 * source.m32) - (source.m00 * source.m12 - source.m02 * source.m10) * (source.m21 * source.m33 - source.m23 * source.m31)
                 + (source.m00 * source.m13 - source.m03 * source.m10) * (source.m21 * source.m32 - source.m22 * source.m31) + (source.m01 * source.m12 - source.m02 * source.m11) * (source.m20 * source.m33 - source.m23 * source.m30)
                 - (source.m01 * source.m13 - source.m03 * source.m11) * (source.m20 * source.m32 - source.m22 * source.m30) + (source.m02 * source.m13 - source.m03 * source.m12) * (source.m20 * source.m31 - source.m21 * source.m30);
    }

    /** Inverts this matrix */
    public void invert() {
        float s = determinant();
        if (s == 0.0f) {
            return;
        }
        s = 1.0f / s;
        set((m11 * (m22 * m33 - m23 * m32) + m12 * (m23 * m31 - m21 * m33) + m13 * (m21 * m32 - m22 * m31)) * s,
            (m21 * (m02 * m33 - m03 * m32) + m22 * (m03 * m31 - m01 * m33) + m23 * (m01 * m32 - m02 * m31)) * s , 
            (m31 * (m02 * m13 - m03 * m12) + m32 * (m03 * m11 - m01 * m13) + m33 * (m01 * m12 - m02 * m11)) * s, 
            (m01 * (m13 * m22 - m12 * m23) + m02 * (m11 * m23 - m13 * m21) + m03 * (m12 * m21 - m11 * m22)) * s,
            (m12 * (m20 * m33 - m23 * m30) + m13 * (m22 * m30 - m20 * m32) + m10 * (m23 * m32 - m22 * m33)) * s, 
            (m22 * (m00 * m33 - m03 * m30) + m23 * (m02 * m30 - m00 * m32) + m20 * (m03 * m32 - m02 * m33)) * s, 
            (m32 * (m00 * m13 - m03 * m10) + m33 * (m02 * m10 - m00 * m12) + m30 * (m03 * m12 - m02 * m13)) * s,
            (m02 * (m13 * m20 - m10 * m23) + m03 * (m10 * m22 - m12 * m20) + m00 * (m12 * m23 - m13 * m22)) * s,
            (m13 * (m20 * m31 - m21 * m30) + m10 * (m21 * m33 - m23 * m31) + m11 * (m23 * m30 - m20 * m33)) * s,
            (m23 * (m00 * m31 - m01 * m30) + m20 * (m01 * m33 - m03 * m31) + m21 * (m03 * m30 - m00 * m33)) * s,
            (m33 * (m00 * m11 - m01 * m10) + m30 * (m01 * m13 - m03 * m11) + m31 * (m03 * m10 - m00 * m13)) * s,
            (m03 * (m11 * m20 - m10 * m21) + m00 * (m13 * m21 - m11 * m23) + m01 * (m10 * m23 - m13 * m20)) * s,
            (m10 * (m22 * m31 - m21 * m32) + m11 * (m20 * m32 - m22 * m30) + m12 * (m21 * m30 - m20 * m31)) * s,
            (m20 * (m02 * m31 - m01 * m32) + m21 * (m00 * m32 - m02 * m30) + m22 * (m01 * m30 - m00 * m31)) * s,
            (m30 * (m02 * m11 - m01 * m12) + m31 * (m00 * m12 - m02 * m10) + m32 * (m01 * m10 - m00 * m11)) * s,
            (m00 * (m11 * m22 - m12 * m21) + m01 * (m12 * m20 - m10 * m22) + m02 * (m10 * m21 - m11 * m20)) * s);
    }
    
    /** Invert the supplied matrix and store the results in dest. Does not modify original matrix */
    public static void invert(Matrix4f source, Matrix4f dest) {
        float s = source.determinant();
        if (s == 0.0f) {
            return;
        }
        s = 1.0f / s;
        dest.set((source.m11 * (source.m22 * source.m33 - source.m23 * source.m32) + source.m12 * (source.m23 * source.m31 - source.m21 * source.m33) + source.m13 * (source.m21 * source.m32 - source.m22 * source.m31)) * s,
                 (source.m21 * (source.m02 * source.m33 - source.m03 * source.m32) + source.m22 * (source.m03 * source.m31 - source.m01 * source.m33) + source.m23 * (source.m01 * source.m32 - source.m02 * source.m31)) * s,
                 (source.m31 * (source.m02 * source.m13 - source.m03 * source.m12) + source.m32 * (source.m03 * source.m11 - source.m01 * source.m13) + source.m33 * (source.m01 * source.m12 - source.m02 * source.m11)) * s,
                 (source.m01 * (source.m13 * source.m22 - source.m12 * source.m23) + source.m02 * (source.m11 * source.m23 - source.m13 * source.m21) + source.m03 * (source.m12 * source.m21 - source.m11 * source.m22)) * s,
                 (source.m12 * (source.m20 * source.m33 - source.m23 * source.m30) + source.m13 * (source.m22 * source.m30 - source.m20 * source.m32) + source.m10 * (source.m23 * source.m32 - source.m22 * source.m33)) * s,
                 (source.m22 * (source.m00 * source.m33 - source.m03 * source.m30) + source.m23 * (source.m02 * source.m30 - source.m00 * source.m32) + source.m20 * (source.m03 * source.m32 - source.m02 * source.m33)) * s,
                 (source.m32 * (source.m00 * source.m13 - source.m03 * source.m10) + source.m33 * (source.m02 * source.m10 - source.m00 * source.m12) + source.m30 * (source.m03 * source.m12 - source.m02 * source.m13)) * s,
                 (source.m02 * (source.m13 * source.m20 - source.m10 * source.m23) + source.m03 * (source.m10 * source.m22 - source.m12 * source.m20) + source.m00 * (source.m12 * source.m23 - source.m13 * source.m22)) * s,
                 (source.m13 * (source.m20 * source.m31 - source.m21 * source.m30) + source.m10 * (source.m21 * source.m33 - source.m23 * source.m31) + source.m11 * (source.m23 * source.m30 - source.m20 * source.m33)) * s,
                 (source.m23 * (source.m00 * source.m31 - source.m01 * source.m30) + source.m20 * (source.m01 * source.m33 - source.m03 * source.m31) + source.m21 * (source.m03 * source.m30 - source.m00 * source.m33)) * s,
                 (source.m33 * (source.m00 * source.m11 - source.m01 * source.m10) + source.m30 * (source.m01 * source.m13 - source.m03 * source.m11) + source.m31 * (source.m03 * source.m10 - source.m00 * source.m13)) * s,
                 (source.m03 * (source.m11 * source.m20 - source.m10 * source.m21) + source.m00 * (source.m13 * source.m21 - source.m11 * source.m23) + source.m01 * (source.m10 * source.m23 - source.m13 * source.m20)) * s,
                 (source.m10 * (source.m22 * source.m31 - source.m21 * source.m32) + source.m11 * (source.m20 * source.m32 - source.m22 * source.m30) + source.m12 * (source.m21 * source.m30 - source.m20 * source.m31)) * s,
                 (source.m20 * (source.m02 * source.m31 - source.m01 * source.m32) + source.m21 * (source.m00 * source.m32 - source.m02 * source.m30) + source.m22 * (source.m01 * source.m30 - source.m00 * source.m31)) * s,
                 (source.m30 * (source.m02 * source.m11 - source.m01 * source.m12) + source.m31 * (source.m00 * source.m12 - source.m02 * source.m10) + source.m32 * (source.m01 * source.m10 - source.m00 * source.m11)) * s,
                 (source.m00 * (source.m11 * source.m22 - source.m12 * source.m21) + source.m01 * (source.m12 * source.m20 - source.m10 * source.m22) + source.m02 * (source.m10 * source.m21 - source.m11 * source.m20)) * s );
    }
    
    /** Invert the supplied matrix and store the results in dest. Does not modify original matrix */
    public static void invert(Matrix4f source, FloatBuffer dest) {
        float s = source.determinant();
        if (s == 0.0f) {
            return;
        }
        s = 1.0f / s;
        
        dest.put((source.m11 * (source.m22 * source.m33 - source.m23 * source.m32) + source.m12 * (source.m23 * source.m31 - source.m21 * source.m33) + source.m13 * (source.m21 * source.m32 - source.m22 * source.m31)) * s);
        dest.put((source.m21 * (source.m02 * source.m33 - source.m03 * source.m32) + source.m22 * (source.m03 * source.m31 - source.m01 * source.m33) + source.m23 * (source.m01 * source.m32 - source.m02 * source.m31)) * s);
        dest.put((source.m31 * (source.m02 * source.m13 - source.m03 * source.m12) + source.m32 * (source.m03 * source.m11 - source.m01 * source.m13) + source.m33 * (source.m01 * source.m12 - source.m02 * source.m11)) * s);
        dest.put((source.m01 * (source.m13 * source.m22 - source.m12 * source.m23) + source.m02 * (source.m11 * source.m23 - source.m13 * source.m21) + source.m03 * (source.m12 * source.m21 - source.m11 * source.m22)) * s);
        dest.put((source.m12 * (source.m20 * source.m33 - source.m23 * source.m30) + source.m13 * (source.m22 * source.m30 - source.m20 * source.m32) + source.m10 * (source.m23 * source.m32 - source.m22 * source.m33)) * s);
        dest.put((source.m22 * (source.m00 * source.m33 - source.m03 * source.m30) + source.m23 * (source.m02 * source.m30 - source.m00 * source.m32) + source.m20 * (source.m03 * source.m32 - source.m02 * source.m33)) * s);
        dest.put((source.m32 * (source.m00 * source.m13 - source.m03 * source.m10) + source.m33 * (source.m02 * source.m10 - source.m00 * source.m12) + source.m30 * (source.m03 * source.m12 - source.m02 * source.m13)) * s);
        dest.put((source.m02 * (source.m13 * source.m20 - source.m10 * source.m23) + source.m03 * (source.m10 * source.m22 - source.m12 * source.m20) + source.m00 * (source.m12 * source.m23 - source.m13 * source.m22)) * s);
        dest.put((source.m13 * (source.m20 * source.m31 - source.m21 * source.m30) + source.m10 * (source.m21 * source.m33 - source.m23 * source.m31) + source.m11 * (source.m23 * source.m30 - source.m20 * source.m33)) * s);
        dest.put((source.m23 * (source.m00 * source.m31 - source.m01 * source.m30) + source.m20 * (source.m01 * source.m33 - source.m03 * source.m31) + source.m21 * (source.m03 * source.m30 - source.m00 * source.m33)) * s);
        dest.put((source.m33 * (source.m00 * source.m11 - source.m01 * source.m10) + source.m30 * (source.m01 * source.m13 - source.m03 * source.m11) + source.m31 * (source.m03 * source.m10 - source.m00 * source.m13)) * s);
        dest.put((source.m03 * (source.m11 * source.m20 - source.m10 * source.m21) + source.m00 * (source.m13 * source.m21 - source.m11 * source.m23) + source.m01 * (source.m10 * source.m23 - source.m13 * source.m20)) * s);
        dest.put((source.m10 * (source.m22 * source.m31 - source.m21 * source.m32) + source.m11 * (source.m20 * source.m32 - source.m22 * source.m30) + source.m12 * (source.m21 * source.m30 - source.m20 * source.m31)) * s);
        dest.put((source.m20 * (source.m02 * source.m31 - source.m01 * source.m32) + source.m21 * (source.m00 * source.m32 - source.m02 * source.m30) + source.m22 * (source.m01 * source.m30 - source.m00 * source.m31)) * s);
        dest.put((source.m30 * (source.m02 * source.m11 - source.m01 * source.m12) + source.m31 * (source.m00 * source.m12 - source.m02 * source.m10) + source.m32 * (source.m01 * source.m10 - source.m00 * source.m11)) * s);
        dest.put((source.m00 * (source.m11 * source.m22 - source.m12 * source.m21) + source.m01 * (source.m12 * source.m20 - source.m10 * source.m22) + source.m02 * (source.m10 * source.m21 - source.m11 * source.m20)) * s);
    }
    
    /** Inverts the left matrix by the right, and stores the result into dest without modifying the source. 
    <B>This is not alias safe so make sure dest is not the same as the source or you WILL get incorrect results!</B> */
    public static void invertFast(Matrix4f source, Matrix4f dest) {
        float s = source.determinant();
        if (s == 0.0f) {
            return;
        }
        s = 1.0f / s;
        
        dest.m00 = (source.m11 * (source.m22 * source.m33 - source.m23 * source.m32) + source.m12 * (source.m23 * source.m31 - source.m21 * source.m33) + source.m13 * (source.m21 * source.m32 - source.m22 * source.m31)) * s;
        dest.m01 = (source.m21 * (source.m02 * source.m33 - source.m03 * source.m32) + source.m22 * (source.m03 * source.m31 - source.m01 * source.m33) + source.m23 * (source.m01 * source.m32 - source.m02 * source.m31)) * s;
        dest.m02 = (source.m31 * (source.m02 * source.m13 - source.m03 * source.m12) + source.m32 * (source.m03 * source.m11 - source.m01 * source.m13) + source.m33 * (source.m01 * source.m12 - source.m02 * source.m11)) * s;
        dest.m03 = (source.m01 * (source.m13 * source.m22 - source.m12 * source.m23) + source.m02 * (source.m11 * source.m23 - source.m13 * source.m21) + source.m03 * (source.m12 * source.m21 - source.m11 * source.m22)) * s;
        dest.m10 = (source.m12 * (source.m20 * source.m33 - source.m23 * source.m30) + source.m13 * (source.m22 * source.m30 - source.m20 * source.m32) + source.m10 * (source.m23 * source.m32 - source.m22 * source.m33)) * s;
        dest.m11 = (source.m22 * (source.m00 * source.m33 - source.m03 * source.m30) + source.m23 * (source.m02 * source.m30 - source.m00 * source.m32) + source.m20 * (source.m03 * source.m32 - source.m02 * source.m33)) * s;
        dest.m12 = (source.m32 * (source.m00 * source.m13 - source.m03 * source.m10) + source.m33 * (source.m02 * source.m10 - source.m00 * source.m12) + source.m30 * (source.m03 * source.m12 - source.m02 * source.m13)) * s;
        dest.m13 = (source.m02 * (source.m13 * source.m20 - source.m10 * source.m23) + source.m03 * (source.m10 * source.m22 - source.m12 * source.m20) + source.m00 * (source.m12 * source.m23 - source.m13 * source.m22)) * s;
        dest.m20 = (source.m13 * (source.m20 * source.m31 - source.m21 * source.m30) + source.m10 * (source.m21 * source.m33 - source.m23 * source.m31) + source.m11 * (source.m23 * source.m30 - source.m20 * source.m33)) * s;
        dest.m21 = (source.m23 * (source.m00 * source.m31 - source.m01 * source.m30) + source.m20 * (source.m01 * source.m33 - source.m03 * source.m31) + source.m21 * (source.m03 * source.m30 - source.m00 * source.m33)) * s;
        dest.m22 = (source.m33 * (source.m00 * source.m11 - source.m01 * source.m10) + source.m30 * (source.m01 * source.m13 - source.m03 * source.m11) + source.m31 * (source.m03 * source.m10 - source.m00 * source.m13)) * s;
        dest.m23 = (source.m03 * (source.m11 * source.m20 - source.m10 * source.m21) + source.m00 * (source.m13 * source.m21 - source.m11 * source.m23) + source.m01 * (source.m10 * source.m23 - source.m13 * source.m20)) * s;
        dest.m30 = (source.m10 * (source.m22 * source.m31 - source.m21 * source.m32) + source.m11 * (source.m20 * source.m32 - source.m22 * source.m30) + source.m12 * (source.m21 * source.m30 - source.m20 * source.m31)) * s;
        dest.m31 = (source.m20 * (source.m02 * source.m31 - source.m01 * source.m32) + source.m21 * (source.m00 * source.m32 - source.m02 * source.m30) + source.m22 * (source.m01 * source.m30 - source.m00 * source.m31)) * s;
        dest.m32 = (source.m30 * (source.m02 * source.m11 - source.m01 * source.m12) + source.m31 * (source.m00 * source.m12 - source.m02 * source.m10) + source.m32 * (source.m01 * source.m10 - source.m00 * source.m11)) * s;
        dest.m33 = (source.m00 * (source.m11 * source.m22 - source.m12 * source.m21) + source.m01 * (source.m12 * source.m20 - source.m10 * source.m22) + source.m02 * (source.m10 * source.m21 - source.m11 * source.m20)) * s;
    }

    /** Multiplies this matrix by the supplied scalar value */
    public void mul(float scalar) {
        m00 *= scalar;
        m01 *= scalar;
        m02 *= scalar;
        m03 *= scalar;
        m10 *= scalar;
        m11 *= scalar;
        m12 *= scalar;
        m13 *= scalar;
        m20 *= scalar;
        m21 *= scalar;
        m22 *= scalar;
        m23 *= scalar;
        m30 *= scalar;
        m31 *= scalar;
        m32 *= scalar;
        m33 *= scalar;
    }
    
    /** Multiplies the supplied source matrix by the supplied scalar and stores the results in dest. Does not modify the original matrix */
    public static void mul(Matrix4f source, float scalar, Matrix4f dest) {           
        dest.m00 = source.m00 * scalar;
        dest.m01 = source.m01 * scalar;
        dest.m02 = source.m02 * scalar;
        dest.m03 = source.m03 * scalar;
        dest.m10 = source.m10 * scalar;
        dest.m11 = source.m11 * scalar;
        dest.m12 = source.m12 * scalar;
        dest.m13 = source.m13 * scalar;
        dest.m20 = source.m20 * scalar;
        dest.m21 = source.m21 * scalar;
        dest.m22 = source.m22 * scalar;
        dest.m23 = source.m23 * scalar;
        dest.m30 = source.m30 * scalar;
        dest.m31 = source.m31 * scalar;
        dest.m32 = source.m32 * scalar;
        dest.m33 = source.m33 * scalar;
    }
    
    /** Transposes this matrix. Modifies the matrix directly */
    public void transpose() {
        set(m00, m10, m20, m30,
            m01, m11, m21, m31,
            m02, m12, m22, m32,
            m03, m13, m23, m33);
    }
    
    /** Transposes the original matrix and stores the results into the destination Matrix4f. Does not modify the original
     * <B>This is not alias safe so make sure dest is not the same object as the original or you WILL get incorrect results!</B> */
    public static void transposeFast(Matrix4f original, Matrix4f dest) {
        dest.m00 = original.m00;
        dest.m01 = original.m10;
        dest.m02 = original.m20;
        dest.m03 = original.m30;
        dest.m10 = original.m01;
        dest.m11 = original.m11;
        dest.m12 = original.m21;
        dest.m13 = original.m31;
        dest.m20 = original.m02;
        dest.m21 = original.m12;
        dest.m22 = original.m22;
        dest.m23 = original.m32;
        dest.m30 = original.m03;
        dest.m31 = original.m13;
        dest.m32 = original.m23;
        dest.m33 = original.m33;
    }
    
    /** Transposes the original matrix and stores the results into the destination Matrix4f. Does not modify the original */
    public static void transpose(Matrix4f original, Matrix4f dest) {
        dest.set(   original.m00,
                    original.m10,
                    original.m20,
                    original.m30,
                    original.m01,
                    original.m11,
                    original.m21,
                    original.m31,
                    original.m02,
                    original.m12,
                    original.m22,
                    original.m32,
                    original.m03,
                    original.m13,
                    original.m23,
                    original.m33 );
    }
    
    /** Transposes the original matrix and stores the results into the destination FloatBuffer. Does not modify the original */
    public static void transpose(Matrix4f original, FloatBuffer dest) {
        dest.put(original.m00);
        dest.put(original.m10);
        dest.put(original.m20);
        dest.put(original.m30);
        dest.put(original.m01);
        dest.put(original.m11);
        dest.put(original.m21);
        dest.put(original.m31);
        dest.put(original.m02);
        dest.put(original.m12);
        dest.put(original.m22);
        dest.put(original.m32);
        dest.put(original.m03);
        dest.put(original.m13);
        dest.put(original.m23);
        dest.put(original.m33);
    }
    
    /** Translate this matrix by the supplied Vector3f */
    public void translate(Vector3f position) {
        m30 = position.x;
        m31 = position.y;
        m32 = position.z;
        m33 = 1.0f;
    }
    
    /** Translate the supplied Matrix by the supplied Vector3f and store the results in dest. Does not modify the original matrix */
    public static void translate(Matrix4f matrix, Vector3f position, Matrix4f dest) {
        dest.m00 = matrix.m00;
        dest.m01 = matrix.m01;
        dest.m02 = matrix.m02;
        dest.m03 = matrix.m03;
        dest.m10 = matrix.m10;
        dest.m11 = matrix.m11;
        dest.m12 = matrix.m12;
        dest.m13 = matrix.m13;
        dest.m20 = matrix.m20;
        dest.m21 = matrix.m21;
        dest.m22 = matrix.m22;
        dest.m23 = matrix.m23;
        dest.m30 = position.x;
        dest.m31 = position.y;
        dest.m32 = position.z;
        dest.m33 = 1.0f;
    }
    
    /** Translate this matrix by the supplied Vector4f */
    public void translate(Vector4f position) {
        m30 = position.x;
        m31 = position.y;
        m32 = position.z;
        m33 = position.w;
    }
    
    /** Translate the supplied Matrix by the supplied Vector4f and store the results in dest. Does not modify the original matrix */
    public static void translate(Matrix4f matrix, Vector4f position, Matrix4f dest) {
        dest.m30 = position.x;
        dest.m31 = position.y;
        dest.m32 = position.z;
        dest.m33 = position.w;
    }
    
    /** Translate the supplied Matrix by the supplied Vector4f and store the results in the target FloatBuffer. Does not modify the original matrix */
    public static void translate(Matrix4f matrix, Vector4f position, FloatBuffer dest) {
        dest.put(matrix.m00);
        dest.put(matrix.m01);
        dest.put(matrix.m02);
        dest.put(matrix.m03);
        dest.put(matrix.m10);
        dest.put(matrix.m11);
        dest.put(matrix.m12);
        dest.put(matrix.m13);
        dest.put(matrix.m20);
        dest.put(matrix.m21);
        dest.put(matrix.m22);
        dest.put(matrix.m23);
        dest.put(position.x);
        dest.put(position.y);
        dest.put(position.z);
        dest.put(position.w);
    }

    public String toString() {
        return "Matrix4f { " + this.m00 + ", " + this.m10 + ", " + this.m20 + ", " + this.m30 + ",\n"
                + "           " + this.m01 + ", " + this.m11 + ", " + this.m21 + ", " + this.m31 + ",\n"
                + "           " + this.m02 + ", " + this.m12 + ", " + this.m22 + ", " + this.m32 + ",\n"
                + "           " + this.m03 + ", " + this.m13 + ", " + this.m23 + ", " + this.m33 + " }\n";

    }

    /** Stores this matrix in the supplied FloatBuffer */
    public void store(FloatBuffer buffer) {
        buffer.put(this.m00);
        buffer.put(this.m01);
        buffer.put(this.m02);
        buffer.put(this.m03);
        buffer.put(this.m10);
        buffer.put(this.m11);
        buffer.put(this.m12);
        buffer.put(this.m13);
        buffer.put(this.m20);
        buffer.put(this.m21);
        buffer.put(this.m22);
        buffer.put(this.m23);
        buffer.put(this.m30);
        buffer.put(this.m31);
        buffer.put(this.m32);
        buffer.put(this.m33);
    }

    /** Sets all the values within this matrix to 0 */
    public void clear() {
        this.m00 = 0.0f;
        this.m01 = 0.0f;
        this.m02 = 0.0f;
        this.m03 = 0.0f;
        this.m10 = 0.0f;
        this.m11 = 0.0f;
        this.m12 = 0.0f;
        this.m13 = 0.0f;
        this.m20 = 0.0f;
        this.m21 = 0.0f;
        this.m22 = 0.0f;
        this.m23 = 0.0f;
        this.m30 = 0.0f;
        this.m31 = 0.0f;
        this.m32 = 0.0f;
        this.m33 = 0.0f;
    }

}
