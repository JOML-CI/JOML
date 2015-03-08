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
package com.joml.utils;

import com.joml.matrix.Matrix4f;
import com.joml.rot.Quaternion;
import com.joml.vector.Vector3f;
import java.nio.FloatBuffer;

/**
 * MatrixUtils
 * 
 * Contains useful functions for generating matrices that aren't to do with the camera (use CamMath for that)
 * 
 * @author Richard Greenlees
 */
public class MatrixUtils {
    
    /** Translates, rotates and scales the identity matrix using the supplied position, scale and rotation parameters
     *  to create a complete transformation matrix and stores the results in dest. Does not modify position, scale or rotation */
    public static void createTransformationMatrix(Vector3f position, Vector3f scale, Quaternion rotation, Matrix4f dest) {
        float q00 = 2.0f * rotation.x * rotation.x;
        float q11 = 2.0f * rotation.y * rotation.y;
        float q22 = 2.0f * rotation.z * rotation.z;
        float q01 = 2.0f * rotation.x * rotation.y;
        float q02 = 2.0f * rotation.x * rotation.z;
        float q03 = 2.0f * rotation.x * rotation.w;
        float q12 = 2.0f * rotation.y * rotation.z;
        float q13 = 2.0f * rotation.y * rotation.w;
        float q23 = 2.0f * rotation.z * rotation.w;
        
        dest.m00 = (1.0f - q11 - q22) * scale.x;
        dest.m01 = (q01 + q23) * scale.x;
        dest.m02 = (q02 - q13) * scale.x;
        dest.m03 = 0.0f;
        dest.m10 = (q01 - q23) * scale.y;
        dest.m11 = (1.0f - q22 - q00) * scale.y;
        dest.m12 = (q12 + q03) * scale.y;
        dest.m13 = 0.0f;
        dest.m20 = (q02 + q13) * scale.z;
        dest.m21 = (q12 - q03) * scale.z;
        dest.m22 = (1.0f - q11 - q00) * scale.z;
        dest.m23 = 0.0f;
        dest.m30 = position.x;
        dest.m31 = position.y;
        dest.m32 = position.z;
        dest.m33 = 1.0f;
    }
    
    /** Translates, rotates and scales the identity matrix using the supplied position, scale and rotation parameters
     *  to create a complete transformation matrix and stores the results in dest. Does not modify position, scale or rotation */
    public static void createTransformationMatrix(Vector3f position, Vector3f scale, Quaternion rotation, FloatBuffer dest) {
        float q00 = 2.0f * rotation.x * rotation.x;
        float q11 = 2.0f * rotation.y * rotation.y;
        float q22 = 2.0f * rotation.z * rotation.z;
        float q01 = 2.0f * rotation.x * rotation.y;
        float q02 = 2.0f * rotation.x * rotation.z;
        float q03 = 2.0f * rotation.x * rotation.w;
        float q12 = 2.0f * rotation.y * rotation.z;
        float q13 = 2.0f * rotation.y * rotation.w;
        float q23 = 2.0f * rotation.z * rotation.w;
        
        dest.put((1.0f - q11 - q22) * scale.x);
        dest.put((q01 + q23) * scale.x);
        dest.put((q02 - q13) * scale.x);
        dest.put(0.0f);
        dest.put((q01 - q23) * scale.y);
        dest.put((1.0f - q22 - q00) * scale.y);
        dest.put((q12 + q03) * scale.y);
        dest.put(0.0f);
        dest.put((q02 + q13) * scale.z);
        dest.put((q12 - q03) * scale.z);
        dest.put((1.0f - q11 - q00) * scale.z);
        dest.put(0.0f);
        dest.put(position.x);
        dest.put(position.y);
        dest.put(position.z);
        dest.put(1.0f);
    }
    
}
