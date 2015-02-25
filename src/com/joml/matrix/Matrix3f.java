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
package com.joml.matrix;

import java.nio.FloatBuffer;

/**
 * Matrix3f
 * 
 * Contains the definition of a 3x3 Matrix of floats, and associated functions to transform
 * it. The matrix is column-major to match OpenGL's interpretation, and it looks like this:
 * 
 *      m00  m10  m20
 *      m01  m11  m21
 *      m02  m12  m22
 * 
 * @author Richard Greenlees
 */

public class Matrix3f {
    
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
    }

    public Matrix3f(float diagonal) {
        super();
        this.m00 = diagonal;
        this.m11 = diagonal;
        this.m22 = diagonal;
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
    public void set(Matrix3f m1) {
        m00 = m1.m00;
        m01 = m1.m01;
        m02 = m1.m02;
        m10 = m1.m10;
        m11 = m1.m11;
        m12 = m1.m12;
        m20 = m1.m20;
        m21 = m1.m21;
        m22 = m1.m22;
    }

    /** Multiplies this matrix by the supplied matrix. This matrix will be the left-sided one */
    public void mul(Matrix3f right) {
        set( this.m00 * right.m00 + this.m10 * right.m01 + this.m20 * right.m02,
             this.m01 * right.m00 + this.m11 * right.m01 + this.m21 * right.m02,
             this.m02 * right.m00 + this.m12 * right.m01 + this.m22 * right.m02,
             this.m00 * right.m10 + this.m10 * right.m11 + this.m20 * right.m12,
             this.m01 * right.m10 + this.m11 * right.m11 + this.m21 * right.m12,
             this.m02 * right.m10 + this.m12 * right.m11 + this.m22 * right.m12,
             this.m00 * right.m20 + this.m10 * right.m21 + this.m20 * right.m22,
             this.m01 * right.m20 + this.m11 * right.m21 + this.m21 * right.m22,
             this.m02 * right.m20 + this.m12 * right.m21 + this.m22 * right.m22 );
    }
    
    /** Multiplies the left matrix by the right, and stores the results in dest. Does not modify the left or right matrices */
    public static void mul(Matrix3f left, Matrix3f right, Matrix3f dest) {
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
    
    /** Multiplies the left matrix by the right, and stores the results in dest. Does not modify the left or right matrices. 
    * <B>This is not alias safe so make sure dest is not the same object as the original or you WILL get incorrect results!</B> */
    public static void mulFast(Matrix3f left, Matrix3f right, Matrix3f dest) {
        dest.m00 = left.m00 * right.m00 + left.m10 * right.m01 + left.m20 * right.m02;
        dest.m01 = left.m01 * right.m00 + left.m11 * right.m01 + left.m21 * right.m02;
        dest.m02 = left.m02 * right.m00 + left.m12 * right.m01 + left.m22 * right.m02;
        dest.m10 = left.m00 * right.m10 + left.m10 * right.m11 + left.m20 * right.m12;
        dest.m11 = left.m01 * right.m10 + left.m11 * right.m11 + left.m21 * right.m12;
        dest.m12 = left.m02 * right.m10 + left.m12 * right.m11 + left.m22 * right.m12;
        dest.m20 = left.m00 * right.m20 + left.m10 * right.m21 + left.m20 * right.m22;
        dest.m21 = left.m01 * right.m20 + left.m11 * right.m21 + left.m21 * right.m22;
        dest.m22 = left.m02 * right.m20 + left.m12 * right.m21 + left.m22 * right.m22;
    }

    /** Sets the values within this matrix to the supplied float values. The result looks like this:<br><br>
     * 
     * m00, m10, m20
     * m01, m11, m21
     * m02, m12, m22
     * 
     */
    public void set(float m00, float m01, float m02, float m10, float m11,
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

    /** Sets the values in this matrix based on the supplied float array. The result looks like this:<br><br>
     * 
     * 0, 3, 6<br>
     * 1, 4, 7<br>
     * 2, 5, 8<br><br>
     * 
     * Only uses the first 9 values, all others are ignored
     * 
     */
    public void set(float m[]) {
        m00 = m[0];
        m01 = m[1];
        m02 = m[2];
        m10 = m[3];
        m11 = m[4];
        m12 = m[5];
        m20 = m[6];
        m21 = m[7];
        m22 = m[8];
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

    /** Inverts this matrix */
    public void invert() {
        float s = determinant();
        
        if (s == 0.0f) {
            return;
        }
        s = 1.0f / s;

       set( (m11 * m22) - (m21 * m12),
           -((m01 * m22) - (m21 * m02)),
            (m01 * m12) - (m11 * m02),
           -((m10 * m22) - (m20 * m12)),
            (m00 * m22) - (m20 * m02),
           -((m00 * m12) - (m10 * m02)),
            (m10 * m21) - (m20 * m11),
           -((m00 * m21) - (m20 * m01)),
            (m00 * m11) - (m10 * m01));
        
        mul(s);
    }
    
    /** Inverts the source matrix and stores the results in dest. Does not modify the source */
    public static void invert(Matrix3f source, Matrix3f dest) {
        float s = source.determinant();
        if (s == 0.0f) {
            return;
        }
        s = 1.0f / s;
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
    
    /** Inverts the source matrix and stores the results in dest. Does not modify the source
    * <B>This is not alias safe so make sure dest is not the same object as the original or you WILL get incorrect results!</B> */
    public static void invertFast(Matrix3f source, Matrix3f dest) {
        float s = source.determinant();
        if (s == 0.0f) {
            return;
        }
        s = 1.0f / s;
        
        dest.m00 = ((source.m11 * source.m22) - (source.m21 * source.m12)) * s;
        dest.m01 = -((source.m01 * source.m22) - (source.m21 * source.m02)) * s;
        dest.m02 = ((source.m01 * source.m12) - (source.m11 * source.m02)) * s;
        dest.m10 = -((source.m10 * source.m22) - (source.m20 * source.m12)) * s;
        dest.m11 = ((source.m00 * source.m22) - (source.m20 * source.m02)) * s;
        dest.m12 = -((source.m00 * source.m12) - (source.m10 * source.m02)) * s;
        dest.m20 = ((source.m10 * source.m21) - (source.m20 * source.m11)) * s;
        dest.m21 = -((source.m00 * source.m21) - (source.m20 * source.m01)) * s;
        dest.m22 = ((source.m00 * source.m11) - (source.m10 * source.m01)) * s;
    }
    
    /** Inverts the source matrix and stores the results in dest. Does not modify the source
    * <B>This is not alias safe so make sure dest is not the same object as the original or you WILL get incorrect results!</B> */
    public static void invert(Matrix3f source, FloatBuffer dest) {
        float s = source.determinant();
        if (s == 0.0f) {
            return;
        }
        s = 1.0f / s;
        
        dest.put(((source.m11 * source.m22) - (source.m21 * source.m12)) * s);
        dest.put(-((source.m01 * source.m22) - (source.m21 * source.m02)) * s);
        dest.put(((source.m01 * source.m12) - (source.m11 * source.m02)) * s);
        dest.put(-((source.m10 * source.m22) - (source.m20 * source.m12)) * s);
        dest.put(((source.m00 * source.m22) - (source.m20 * source.m02)) * s);
        dest.put(-((source.m00 * source.m12) - (source.m10 * source.m02)) * s);
        dest.put(((source.m10 * source.m21) - (source.m20 * source.m11)) * s);
        dest.put(-((source.m00 * source.m21) - (source.m20 * source.m01)) * s);
        dest.put(((source.m00 * source.m11) - (source.m10 * source.m01)) * s);
    }
    
    /** Transposes this matrix */
    public void transpose() {
        set(m00, m10, m20,
            m01, m11, m21,
            m02, m12, m22);
    }
    
    /** Transposes the supplied original matrix and stores the results in dest. The original is not modified */
    public static void transpose(Matrix3f original, Matrix3f dest) {
        dest.set(original.m00, original.m10, original.m20,
                 original.m01, original.m11, original.m21,
                 original.m02, original.m12, original.m22);
    }
    
    /** Transposes the supplied original matrix and stores the results in dest. The original is not modified */
    public static void transpose(Matrix3f original, FloatBuffer dest) {
        dest.put(original.m00);
        dest.put(original.m10);
        dest.put(original.m20);
        dest.put(original.m01);
        dest.put(original.m11);
        dest.put(original.m21);
        dest.put(original.m02);
        dest.put(original.m12);
        dest.put(original.m22);
    }
    
    /** Transposes the supplied original matrix and stores the results in dest. The original is not modified.
    * <B>This is not alias safe so make sure dest is not the same object as the original or you WILL get incorrect results!</B> */
    public static void transposeFast(Matrix3f original, Matrix3f dest) {
        dest.m00 = original.m00;
        dest.m01 = original.m10;
        dest.m02 = original.m20;
        dest.m10 = original.m01;
        dest.m11 = original.m11;
        dest.m12 = original.m21;
        dest.m20 = original.m02;
        dest.m21 = original.m12;
        dest.m22 = original.m22;
    }

    /** Multiply this matrix by the scalar value */
    public void mul(float scalar) {
        m00 *= scalar;
        m01 *= scalar;
        m02 *= scalar;
        m10 *= scalar;
        m11 *= scalar;
        m12 *= scalar;
        m20 *= scalar;
        m21 *= scalar;
        m22 *= scalar;
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
    
    /** Multiply the supplied Matrix by the supplied scalar value and store the results in dest. Does not modify the source */
    public static void mul(Matrix3f source, float scalar, FloatBuffer dest) {
        dest.put(source.m00 * scalar);
        dest.put(source.m01 * scalar);
        dest.put(source.m02 * scalar);
        dest.put(source.m10 * scalar);
        dest.put(source.m11 * scalar);
        dest.put(source.m12 * scalar);
        dest.put(source.m20 * scalar);
        dest.put(source.m21 * scalar);
        dest.put(source.m22 * scalar);
    }
    
    public String toString() {
        return "Matrix3f { " + this.m00 + ", " + this.m10 + ", " + this.m20 + ",\n"
                + "           " + this.m01 + ", " + this.m11 + ", " + this.m21 + ",\n"
                + "           " + this.m02 + ", " + this.m12 + ", " + this.m22 + " }\n";

    }

    /** Stores this matrix in the supplied FloatBuffer */
    public void store(FloatBuffer buffer) {
        buffer.put(this.m00);
        buffer.put(this.m01);
        buffer.put(this.m02);
        buffer.put(this.m10);
        buffer.put(this.m11);
        buffer.put(this.m12);
        buffer.put(this.m20);
        buffer.put(this.m21);
        buffer.put(this.m22);
    }

    /** Sets all the values within this matrix to 0 */
    public void clear() {
        this.m00 = 0.0f;
        this.m01 = 0.0f;
        this.m02 = 0.0f;
        this.m10 = 0.0f;
        this.m11 = 0.0f;
        this.m12 = 0.0f;
        this.m20 = 0.0f;
        this.m21 = 0.0f;
        this.m22 = 0.0f;
    }
    
    /** Sets this matrix to the identity */
    public void identity() {
        this.m00 = 1.0f;
        this.m01 = 0.0f;
        this.m02 = 0.0f;
        this.m10 = 0.0f;
        this.m11 = 1.0f;
        this.m12 = 0.0f;
        this.m20 = 0.0f;
        this.m21 = 0.0f;
        this.m22 = 1.0f;
    }


}
