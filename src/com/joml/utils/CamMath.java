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
    
    /** Calculates a view matrix
     * 
     * @param position The position of the camera
     * @param centre The point in space or direction it is looking at
     * @param up The direction of "up". In most cases it is (x=0, y=1, z=0)
     * @param dest The matrix to store the results in
     */
    public static void lookAt(Vector3f position, Vector3f centre, Vector3f up, Matrix4f dest) {
        dest.clear();
        
        Vector3f Z = new Vector3f();
        
        Vector3f.sub(position, centre,  Z);
        
        Z.normalize();
        
        Vector3f X = new Vector3f();
        
        Vector3f.cross(up, Z, X);
        
        Vector3f Y = new Vector3f();
        
        Vector3f.cross(Z, X, Y);
        
        X.normalize();
        Y.normalize();
        
        dest.m00 = X.x;
        dest.m10 = X.y;
        dest.m20 = X.z;
        dest.m30 = -Vector3f.dot(X, position);
        dest.m01 = Y.x;
        dest.m11 = Y.y;
        dest.m21 = Y.z;
        dest.m02 = Z.x;
        dest.m12 = Z.y;
        dest.m22 = Z.z;
        dest.m32 = -Vector3f.dot(Z, position);
        dest.m33 = 1.0f;
    }
    
    /** Calculates an orthographic projection matrix using the supplied parameters
     * 
     * @param left X starting position of the screen
     * @param right X end position of the screen
     * @param bottom Y starting position of the screen
     * @param top Y end position of the screen
     * @param zNear Near clipping plane distance
     * @param zFar Far clipping plane distance
     * @param dest Matrix4f to store the results
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
    
}
