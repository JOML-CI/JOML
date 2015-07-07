package org.joml.interop;


public class Convert {
    /* Vector3f Conversions */

    /**
     * Set the x, y and z attributes to match the ones of the supplied
     * javax.vecmath vector.
     * 
     * @param javaxVecmathVector
     */
    public static void fromJavaxVector(
            javax.vecmath.Vector3f javaxVecmathVector, org.joml.Vector3f dest) {
        dest.x = javaxVecmathVector.x;
        dest.y = javaxVecmathVector.y;
        dest.z = javaxVecmathVector.z;        
    }

    /**
     * Set the x, y and z attributes to match the ones of the supplied
     * org.lwjgl.util.vector vector.
     * 
     * @param lwjglVector
     */
    public static void fromLwjglVector(
            org.lwjgl.util.vector.Vector3f lwjglVector, org.joml.Vector3f dest) {
        dest.x = lwjglVector.x;
        dest.y = lwjglVector.y;
        dest.z = lwjglVector.z;        
    }

    /* Vector4f Conversions */

    /**
     * Set the attributes to match the ones of the supplied javax.vecmath
     * vector.
     * 
     * @param javaxVecmathVector
     */
    public static void fromJavaxVector(
            javax.vecmath.Vector4f javaxVecmathVector, org.joml.Vector4f dest) {
        dest.x = javaxVecmathVector.x;
        dest.y = javaxVecmathVector.y;
        dest.z = javaxVecmathVector.z;
        dest.w = javaxVecmathVector.w;        
    }

    /**
     * Set the x, y and z attributes to match the ones of the supplied
     * org.lwjgl.util.vector vector.
     * 
     * @param lwjglVector
     */
    public static void fromLwjglVector(
            org.lwjgl.util.vector.Vector4f lwjglVector, org.joml.Vector4f dest) {
        dest.x = lwjglVector.x;
        dest.y = lwjglVector.y;
        dest.z = lwjglVector.z;
        dest.w = lwjglVector.w;        
    }

    /* Matrix3f Conversions */

    /**
     * Set the values of this matrix to the ones of the given
     * javax.vecmath.Matrix3f matrix.
     * 
     * @param javaxVecmathMatrix
     */
    public static void fromJavaxMatrix(
            javax.vecmath.Matrix3f javaxVecmathMatrix, org.joml.Matrix3f dest) {
        dest.m00 = javaxVecmathMatrix.m00;
        dest.m01 = javaxVecmathMatrix.m10;
        dest.m02 = javaxVecmathMatrix.m20;
        dest.m10 = javaxVecmathMatrix.m01;
        dest.m11 = javaxVecmathMatrix.m11;
        dest.m12 = javaxVecmathMatrix.m21;
        dest.m20 = javaxVecmathMatrix.m02;
        dest.m21 = javaxVecmathMatrix.m12;
        dest.m22 = javaxVecmathMatrix.m22;        
    }

    /**
     * Set the values of this matrix to the ones of the given
     * org.lwjgl.util.vector.Matrix3f matrix.
     * 
     * @param lwjglMatrix
     */
    public static void fromLwjglMatrix(
            org.lwjgl.util.vector.Matrix3f lwjglMatrix, org.joml.Matrix3f dest) {
        dest.m00 = lwjglMatrix.m00;
        dest.m01 = lwjglMatrix.m01;
        dest.m02 = lwjglMatrix.m02;
        dest.m10 = lwjglMatrix.m10;
        dest.m11 = lwjglMatrix.m11;
        dest.m12 = lwjglMatrix.m12;
        dest.m20 = lwjglMatrix.m20;
        dest.m21 = lwjglMatrix.m21;
        dest.m22 = lwjglMatrix.m22;        
    }

    /* Matrix4f Conversions */

    /**
     * Set the values of this matrix to the ones of the given javax.vecmath
     * matrix.
     * 
     * @param javaxVecmathMatrix
     */
    public static void fromJavaxMatrix(
            javax.vecmath.Matrix4f javaxVecmathMatrix, org.joml.Matrix4f dest) {
        dest.m00 = javaxVecmathMatrix.m00;
        dest.m01 = javaxVecmathMatrix.m10;
        dest.m02 = javaxVecmathMatrix.m20;
        dest.m03 = javaxVecmathMatrix.m30;
        dest.m10 = javaxVecmathMatrix.m01;
        dest.m11 = javaxVecmathMatrix.m11;
        dest.m12 = javaxVecmathMatrix.m21;
        dest.m13 = javaxVecmathMatrix.m31;
        dest.m20 = javaxVecmathMatrix.m02;
        dest.m21 = javaxVecmathMatrix.m12;
        dest.m22 = javaxVecmathMatrix.m22;
        dest.m23 = javaxVecmathMatrix.m32;
        dest.m30 = javaxVecmathMatrix.m03;
        dest.m31 = javaxVecmathMatrix.m13;
        dest.m32 = javaxVecmathMatrix.m23;
        dest.m33 = javaxVecmathMatrix.m33;        
    }

    /**
     * Set the values of this matrix to the ones of the given
     * org.lwjgl.util.vector.Matrix4f matrix.
     * 
     * @param lwjglMatrix
     */
    public static void fromLwjglMatrix(
            org.lwjgl.util.vector.Matrix4f lwjglMatrix, org.joml.Matrix4f dest) {
        dest.m00 = lwjglMatrix.m00;
        dest.m01 = lwjglMatrix.m01;
        dest.m02 = lwjglMatrix.m02;
        dest.m03 = lwjglMatrix.m03;
        dest.m10 = lwjglMatrix.m10;
        dest.m11 = lwjglMatrix.m11;
        dest.m12 = lwjglMatrix.m12;
        dest.m13 = lwjglMatrix.m13;
        dest.m20 = lwjglMatrix.m20;
        dest.m21 = lwjglMatrix.m21;
        dest.m22 = lwjglMatrix.m22;
        dest.m23 = lwjglMatrix.m23;
        dest.m30 = lwjglMatrix.m30;
        dest.m31 = lwjglMatrix.m31;
        dest.m32 = lwjglMatrix.m32;
        dest.m33 = lwjglMatrix.m33;        
    }

    /**
     * Set the values of this matrix to the ones of the given
     * com.badlogic.gdx.math.Matrix4 matrix.
     * 
     * @param gdxMatrix
     */
    public static void fromGdxMatrix(com.badlogic.gdx.math.Matrix4 gdxMatrix,
            org.joml.Matrix4f dest) {
        dest.m00 = gdxMatrix.val[0];
        dest.m01 = gdxMatrix.val[1];
        dest.m02 = gdxMatrix.val[2];
        dest.m03 = gdxMatrix.val[3];
        dest.m10 = gdxMatrix.val[4];
        dest.m11 = gdxMatrix.val[5];
        dest.m12 = gdxMatrix.val[6];
        dest.m13 = gdxMatrix.val[7];
        dest.m20 = gdxMatrix.val[8];
        dest.m21 = gdxMatrix.val[9];
        dest.m22 = gdxMatrix.val[10];
        dest.m23 = gdxMatrix.val[11];
        dest.m30 = gdxMatrix.val[12];
        dest.m31 = gdxMatrix.val[13];
        dest.m32 = gdxMatrix.val[14];
        dest.m33 = gdxMatrix.val[15];        
    }

    /**
     * Store the values of this matrix into the given javax.vecmath.Matrix4f.
     * 
     * @param javaxVecmathMatrix
     */
    public static void toJavaxMatrix(org.joml.Matrix4f source,
            javax.vecmath.Matrix4f javaxVecmathMatrix) {
        javaxVecmathMatrix.m00 = source.m00;
        javaxVecmathMatrix.m10 = source.m01;
        javaxVecmathMatrix.m20 = source.m02;
        javaxVecmathMatrix.m30 = source.m03;
        javaxVecmathMatrix.m01 = source.m10;
        javaxVecmathMatrix.m11 = source.m11;
        javaxVecmathMatrix.m21 = source.m12;
        javaxVecmathMatrix.m31 = source.m13;
        javaxVecmathMatrix.m02 = source.m20;
        javaxVecmathMatrix.m12 = source.m21;
        javaxVecmathMatrix.m22 = source.m22;
        javaxVecmathMatrix.m32 = source.m23;
        javaxVecmathMatrix.m03 = source.m30;
        javaxVecmathMatrix.m13 = source.m31;
        javaxVecmathMatrix.m23 = source.m32;
        javaxVecmathMatrix.m33 = source.m33;        
    }

    /**
     * Store the values of this matrix into the given
     * org.lwjgl.util.vector.Matrix4f.
     * 
     * @param lwjglMatrix
     */
    public static void toLwjglMatrix(org.joml.Matrix4f source,
            org.lwjgl.util.vector.Matrix4f lwjglMatrix) {
        lwjglMatrix.m00 = source.m00;
        lwjglMatrix.m01 = source.m01;
        lwjglMatrix.m02 = source.m02;
        lwjglMatrix.m03 = source.m03;
        lwjglMatrix.m10 = source.m10;
        lwjglMatrix.m11 = source.m11;
        lwjglMatrix.m12 = source.m12;
        lwjglMatrix.m13 = source.m13;
        lwjglMatrix.m20 = source.m20;
        lwjglMatrix.m21 = source.m21;
        lwjglMatrix.m22 = source.m22;
        lwjglMatrix.m23 = source.m23;
        lwjglMatrix.m30 = source.m30;
        lwjglMatrix.m31 = source.m31;
        lwjglMatrix.m32 = source.m32;
        lwjglMatrix.m33 = source.m33;        
    }

    /**
     * Store the values of this matrix into the given
     * com.badlogic.gdx.math.Matrix4.
     * 
     * @param gdxMatrix
     */
    public static void toGdxMatrix(org.joml.Matrix4f source,
            com.badlogic.gdx.math.Matrix4 gdxMatrix) {
        gdxMatrix.val[0] = source.m00;
        gdxMatrix.val[1] = source.m01;
        gdxMatrix.val[2] = source.m02;
        gdxMatrix.val[3] = source.m03;
        gdxMatrix.val[4] = source.m10;
        gdxMatrix.val[5] = source.m11;
        gdxMatrix.val[6] = source.m12;
        gdxMatrix.val[7] = source.m13;
        gdxMatrix.val[8] = source.m20;
        gdxMatrix.val[9] = source.m21;
        gdxMatrix.val[10] = source.m22;
        gdxMatrix.val[11] = source.m23;
        gdxMatrix.val[12] = source.m30;
        gdxMatrix.val[13] = source.m31;
        gdxMatrix.val[14] = source.m32;
        gdxMatrix.val[15] = source.m33;        
    }
}
