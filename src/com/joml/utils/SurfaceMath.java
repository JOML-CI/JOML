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

import com.joml.vector.Vector2f;
import com.joml.vector.Vector3f;

/**
 * Surface Math
 *
 * Contains utility functions related to surfaces and faces
 *
 * @author Richard Greenlees
 */
public class SurfaceMath {

    /**
     * Calculates the normal of a surface defined by points v1, v2 and v3 and
     * stores it in dest. v1, v2 and v3 are not modified
     */
    public static void normal(Vector3f v1, Vector3f v2, Vector3f v3, Vector3f dest) {
        dest.x = ((v2.y - v1.y) * (v3.z - v1.z)) - ((v2.z - v1.z) * (v3.y - v1.y));
        dest.y = ((v2.z - v1.z) * (v3.x - v1.x)) - ((v2.x - v1.x) * (v3.z - v1.z));
        dest.z = ((v2.x - v1.x) * (v3.y - v1.y)) - ((v2.y - v1.y) * (v3.x - v1.x));
    }

    /** Calculates the surface tangent for the three supplied vertices and UV coordinates and stores the result in dest
     * 
     * @param v1 XYZ of first vertex
     * @param uv1 UV of first vertex
     * @param v2 XYZ of second vertex
     * @param uv2 UV of second vertex
     * @param v3 XYZ of third vertex
     * @param uv3 UV of third vertex
     * @param dest The tangent will be stored here
     */
    public static void tangent(Vector3f v1, Vector2f uv1, Vector3f v2, Vector2f uv2, Vector3f v3, Vector2f uv3, Vector3f dest) {
        float f = 1.0f / ((uv2.x - uv1.x) * (uv3.y - uv1.y) - (uv3.x - uv1.x) * (uv2.y - uv1.y));

        dest.x = f * ((uv3.y - uv1.y) * (v2.x - v1.x) - (uv2.y - uv1.y) * (v3.x - v1.x));
        dest.y = f * ((uv3.y - uv1.y) * (v2.y - v1.y) - (uv2.y - uv1.y) * (v3.y - v1.y));
        dest.z = f * ((uv3.y - uv1.y) * (v2.z - v1.z) - (uv2.y - uv1.y) * (v3.z - v1.z));
    }
    
    /** Calculates the surface binormal for the three supplied vertices and UV coordinates and stores the result in dest
     * 
     * @param v1 XYZ of first vertex
     * @param uv1 UV of first vertex
     * @param v2 XYZ of second vertex
     * @param uv2 UV of second vertex
     * @param v3 XYZ of third vertex
     * @param uv3 UV of third vertex
     * @param dest The binormal will be stored here
     */
    public static void binormal(Vector3f v1, Vector2f uv1, Vector3f v2, Vector2f uv2, Vector3f v3, Vector2f uv3, Vector3f dest) {
        float f = 1.0f / ((uv2.x - uv1.x) * (uv3.y - uv1.y) - (uv3.x - uv1.x) * (uv2.y - uv1.y));

        dest.x = f * ((uv2.x - uv1.x) * (v3.x - v1.x) - (uv3.x - uv1.x) * (v2.x - v1.x));
        dest.y = f * ((uv2.x - uv1.x) * (v3.y - v1.y) - (uv3.x - uv1.x) * (v2.y - v1.y));
        dest.z = f * ((uv2.x - uv1.x) * (v3.z - v1.z) - (uv3.x - uv1.x) * (v2.z - v1.z));
    }

}
