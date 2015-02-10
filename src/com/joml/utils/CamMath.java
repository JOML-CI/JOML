/*
 * Feel free to do whatever you want with this code, all I've done is
 * pull together common knowledge into one easy package. Use it as a
 * base for your own work, copy/paste bits or integrate it into your
 * existing project, it's all good. Just add a thanks to me somewhere.
 */
package com.joml.utils;

import com.joml.matrix.Matrix4f;
import static com.joml.utils.TrigMath.coTangent;
import static com.joml.utils.TrigMath.degreesToRadians;
import com.joml.vector.Vector3f;
import java.nio.FloatBuffer;

/**
 * CamMath
 * 
 * Contains some handy functions for calculating matrices in relation to the view, including
 * the famous perspective, lookAt and ortho functions from the C-based GLM library
 * 
 * @author Richard Greenlees
 */
public class CamMath {
    
    /** Calculates a perspective projection matrix using the supplied parameters and stores the result in dest
     * 
     * @param fovy Field of view
     * @param aspect Aspect ratio (display width / display height)
     * @param zNear near clipping plane distance
     * @param zFar far clipping plane distance
     * @param dest Matrix4f to store the result
     */
    public static void perspective(final float fovy, final float aspect, final float zNear, final float zFar, Matrix4f dest) {
        float y_scale = coTangent(degreesToRadians(fovy / 2.0f));
        float x_scale = y_scale / aspect;
        float frustrum_length = zFar - zNear;
        
        dest.clear();
        
        dest.m00 = x_scale;
        dest.m11 = y_scale;
        dest.m22 = -((zFar + zNear) / frustrum_length);
        dest.m23 = -1.0f;
        dest.m32 = -((2.0f * zNear * zFar) / frustrum_length);
    }
    
    /** Calculates a perspective projection matrix using the supplied parameters and writes the result directly into the FloatBuffer
     * 
     * @param fovy Field of view
     * @param aspect Aspect ratio (display width / display height)
     * @param zNear near clipping plane distance
     * @param zFar far clipping plane distance
     * @param dest FloatBuffer to store the result
     */
    public static void perspective(final float fovy, final float aspect, final float zNear, final float zFar, FloatBuffer dest) {
        float y_scale = coTangent(degreesToRadians(fovy / 2.0f));
        float x_scale = y_scale / aspect;
        float frustrum_length = zFar - zNear;
        
        dest.put(x_scale);
        dest.put(0.0f);
        dest.put(0.0f);
        dest.put(0.0f);
        dest.put(0.0f);
        dest.put(y_scale);
        dest.put(0.0f);
        dest.put(0.0f);
        dest.put(0.0f);
        dest.put(0.0f);                
        dest.put(-((zFar + zNear) / frustrum_length));
        dest.put(-1.0f);
        dest.put(0.0f);
        dest.put(0.0f);
        dest.put(-((2.0f * zNear * zFar) / frustrum_length));
        dest.put(0.0f);
        
        
    }
    
    /** Calculates a view matrix
     * 
     * @param position The position of the camera
     * @param centre The point in space or direction it is looking at
     * @param up The direction of "up". In most cases it is (x=0, y=1, z=0)
     * @param dest The matrix to store the results in
     */
    public static void lookAt(Vector3f position, Vector3f centre, Vector3f up, Matrix4f dest) {
        float d = (float) Math.sqrt((position.x - centre.x) * (position.x - centre.x) + (position.y - centre.y) * (position.y - centre.y) + (position.z - centre.z) * (position.z - centre.z));

        float Xx = (up.y * ((position.z - centre.z) / d)) - (up.z * ((position.y - centre.y) / d));
        float Xy = (up.z * ((position.y - centre.y) / d)) - (up.x * ((position.z - centre.z) / d));
        float Xz = (up.x * ((position.y - centre.y) / d)) - (up.y * ((position.x - centre.x) / d));

        float Yx = ((position.y - centre.y) / d) * Xz - ((position.z - centre.z) / d) * Xy;
        float Yy = ((position.z - centre.z) / d) * Xx - ((position.x - centre.x) / d) * Xz;
        float Yz = ((position.x - centre.x) / d) * Xy - ((position.y - centre.y) / d) * Xx;

        float dX = (float) Math.sqrt(Xx * Xx + Xy * Xy + Xz * Xz);
        float dY = (float) Math.sqrt(Yx * Yx + Yy * Yy + Yz * Yz);

        float Zx = (position.x - centre.x) / d;
        float Zy = (position.y - centre.y) / d;
        float Zz = (position.z - centre.z) / d;


        Xx /= dX;
        Xy /= dX;
        Xz /= dX;

        Yx /= dY;
        Yy /= dY;
        Yz /= dY;

        dest.m00 = Xx;
        dest.m01 = Yx;
        dest.m02 = Zx;
        dest.m03 = 0.0f;
        dest.m10 = Xy;
        dest.m11 = Yy;
        dest.m12 = Zy;
        dest.m13 = 0.0f;
        dest.m20 = Xz;
        dest.m21 = Yz;
        dest.m22 = Zz;
        dest.m23 = 0.0f;
        dest.m30 = -((Xx * position.x) + (Xy * position.y) + (Xz * position.z));
        dest.m31 = 0.0f;
        dest.m32 = -((Zx * position.x) + (Zy * position.y) + (Zz * position.z));
        dest.m33 = 1.0f;        
    }
    
    /** Calculates a view matrix and stores the results directly into the FloatBuffer
     * 
     * @param position The position of the camera
     * @param centre The point in space or direction it is looking at
     * @param up The direction of "up". In most cases it is (x=0, y=1, z=0)
     * @param dest The FloatBuffer to store the results in
     */
    public static void lookAt(Vector3f position, Vector3f centre, Vector3f up, FloatBuffer dest) {
        float d = (float) Math.sqrt((position.x - centre.x) * (position.x - centre.x) + (position.y - centre.y) * (position.y - centre.y) + (position.z - centre.z) * (position.z - centre.z));

        float Xx = (up.y * ((position.z - centre.z) / d)) - (up.z * ((position.y - centre.y) / d));
        float Xy = (up.z * ((position.y - centre.y) / d)) - (up.x * ((position.z - centre.z) / d));
        float Xz = (up.x * ((position.y - centre.y) / d)) - (up.y * ((position.x - centre.x) / d));

        float Yx = ((position.y - centre.y) / d) * Xz - ((position.z - centre.z) / d) * Xy;
        float Yy = ((position.z - centre.z) / d) * Xx - ((position.x - centre.x) / d) * Xz;
        float Yz = ((position.x - centre.x) / d) * Xy - ((position.y - centre.y) / d) * Xx;

        float dX = (float) Math.sqrt(Xx * Xx + Xy * Xy + Xz * Xz);
        float dY = (float) Math.sqrt(Yx * Yx + Yy * Yy + Yz * Yz);

        float Zx = (position.x - centre.x) / d;
        float Zy = (position.y - centre.y) / d;
        float Zz = (position.z - centre.z) / d;

        Xx /= dX;
        Xy /= dX;
        Xz /= dX;

        Yx /= dY;
        Yy /= dY;
        Yz /= dY;

        dest.put(Xx);
        dest.put(Yx);
        dest.put(Zx);
        dest.put(0.0f);
        dest.put(Xy);
        dest.put(Yy);
        dest.put(Zy);
        dest.put(0.0f);
        dest.put(Xz);
        dest.put(Yz);
        dest.put(Zz);
        dest.put(0.0f);
        dest.put(-((Xx * position.x) + (Xy * position.y) + (Xz * position.z)));
        dest.put(0.0f);
        dest.put(-((Zx * position.x) + (Zy * position.y) + (Zz * position.z)));
        dest.put(1.0f);
    }
    
    /** Calculates an orthographic projection matrix using the supplied parameters and stores it directly into the FloatBuffer
     * 
     * @param left X starting position of the screen
     * @param right X end position of the screen
     * @param bottom Y starting position of the screen
     * @param top Y end position of the screen
     * @param zNear Near clipping plane distance
     * @param zFar Far clipping plane distance
     * @param dest FloatBuffer to store the results
     */
    public static void ortho(float left, float right, float bottom, float top, float zNear, float zFar, Matrix4f dest) {
        dest.clear();
        
        dest.m00 = 2.0f / (right - left);
        dest.m11 = 2.0f / (top - bottom);
        dest.m22 = (-2.0f) / (zFar - zNear);
        dest.m30 = -((right + left) / (right - left));
        dest.m31 = -((top + bottom) / (top - bottom));
        dest.m32 = -((zFar + zNear) / (zFar - zNear));
        dest.m33 = 1.0f;
    }
    
    /** Calculates an orthographic projection matrix using the supplied parameters and stores directly into the FloatBuffer
     * 
     * @param left X starting position of the screen
     * @param right X end position of the screen
     * @param bottom Y starting position of the screen
     * @param top Y end position of the screen
     * @param zNear Near clipping plane distance
     * @param zFar Far clipping plane distance
     * @param dest FloatBuffer to store the results
     */
    public static void ortho(float left, float right, float bottom, float top, float zNear, float zFar, FloatBuffer dest) {
        dest.put(2.0f / (right - left));
        dest.put(0.0f);
        dest.put(0.0f);
        dest.put(0.0f);
        dest.put(0.0f);               
        dest.put(2.0f / (top - bottom));
        dest.put(0.0f);
        dest.put(0.0f);
        dest.put(0.0f);
        dest.put(0.0f);
        dest.put(-2.0f / (zFar - zNear));
        dest.put(0.0f);
        dest.put(((right + left) / (right - left)));
        dest.put(-((top + bottom) / (top - bottom)));
        dest.put(-((zFar + zNear) / (zFar - zNear)));
        dest.put(1.0f);
    }
    
}
