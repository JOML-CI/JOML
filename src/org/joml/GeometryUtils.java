/*
 * (C) Copyright 2015-2016 Kai Burjack

 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in
 all copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 THE SOFTWARE.

 */
package org.joml;

/**
 * Useful geometry methods.
 * 
 * @author Kai Burjack
 * @author Richard Greenlees
 */
public class GeometryUtils {

    /**
     * Compute two arbitrary vectors perpendicular to the given normalized vector <code>v</code>, and store them in <code>dest1</code> and <code>dest2</code>,
     * respectively.
     * <p>
     * The computed vectors will itself be perpendicular to each another and normalized. So the tree vectors <code>v</code>, <code>dest1</code> and
     * <code>dest2</code> form an orthonormal basis.
     * 
     * @param v
     *            the {@link Vector3f#normalize() normalized} input vector
     * @param dest1
     *            will hold the first perpendicular vector
     * @param dest2
     *            will hold the second perpendicular vector
     */
    public static void perpendicular(Vector3f v, Vector3f dest1, Vector3f dest2) {
        float magX = v.z * v.z + v.y * v.y;
        float magY = v.z * v.z + v.x * v.x;
        float magZ = v.y * v.y + v.x * v.x;
        float mag;
        if (magX > magY && magX > magZ) {
            dest1.x = 0;
            dest1.y = v.z;
            dest1.z = -v.y;
            mag = magX;
        } else if (magY > magZ) {
            dest1.x = v.z;
            dest1.y = 0;
            dest1.z = v.x;
            mag = magY;
        } else {
            dest1.x = v.y;
            dest1.y = -v.x;
            dest1.z = 0;
            mag = magZ;
        }
        float len = 1.0f / (float) Math.sqrt(mag);
        dest1.x *= len;
        dest1.y *= len;
        dest1.z *= len;
        dest2.x = v.y * dest1.z - v.z * dest1.y;
        dest2.y = v.z * dest1.x - v.x * dest1.z;
        dest2.z = v.x * dest1.y - v.y * dest1.x;
    }

    /**
     * Calculate the normal of a surface defined by points <code>v1</code>, <code>v2</code> and <code>v3</code> and store it in <code>dest</code>.
     * 
     * @param v1
     *            the first position
     * @param v2
     *            the second position
     * @param v3
     *            the third position
     * @param dest
     *            will hold the result
     */
    public static void normal(Vector3f v1, Vector3f v2, Vector3f v3, Vector3f dest) {
        dest.x = ((v2.y - v1.y) * (v3.z - v1.z)) - ((v2.z - v1.z) * (v3.y - v1.y));
        dest.y = ((v2.z - v1.z) * (v3.x - v1.x)) - ((v2.x - v1.x) * (v3.z - v1.z));
        dest.z = ((v2.x - v1.x) * (v3.y - v1.y)) - ((v2.y - v1.y) * (v3.x - v1.x));
        dest.normalize();
    }

    /**
     * Calculate the surface tangent for the three supplied vertices and UV coordinates and store the result in <code>dest</code>.
     *
     * @param v1
     *            XYZ of first vertex
     * @param uv1
     *            UV of first vertex
     * @param v2
     *            XYZ of second vertex
     * @param uv2
     *            UV of second vertex
     * @param v3
     *            XYZ of third vertex
     * @param uv3
     *            UV of third vertex
     * @param dest
     *            the tangent will be stored here
     */
    public static void tangent(Vector3f v1, Vector2f uv1, Vector3f v2, Vector2f uv2, Vector3f v3, Vector2f uv3, Vector3f dest) {
        float DeltaV1 = uv2.y - uv1.y;
        float DeltaV2 = uv3.y - uv1.y;

        float f = 1.0f / ((uv2.x - uv1.x) * DeltaV2 - (uv3.x - uv1.x) * DeltaV1);

        dest.x = f * (DeltaV2 * (v2.x - v1.x) - DeltaV1 * (v3.x - v1.x));
        dest.y = f * (DeltaV2 * (v2.y - v1.y) - DeltaV1 * (v3.y - v1.y));
        dest.z = f * (DeltaV2 * (v2.z - v1.z) - DeltaV1 * (v3.z - v1.z));
        dest.normalize();
    }

    /**
     * Calculate the surface bitangent for the three supplied vertices and UV coordinates and store the result in <code>dest</code>.
     *
     * @param v1
     *            XYZ of first vertex
     * @param uv1
     *            UV of first vertex
     * @param v2
     *            XYZ of second vertex
     * @param uv2
     *            UV of second vertex
     * @param v3
     *            XYZ of third vertex
     * @param uv3
     *            UV of third vertex
     * @param dest
     *            the binormal will be stored here
     */
    public static void bitangent(Vector3f v1, Vector2f uv1, Vector3f v2, Vector2f uv2, Vector3f v3, Vector2f uv3, Vector3f dest) {
        float DeltaU1 = uv2.x - uv1.x;
        float DeltaU2 = uv3.x - uv1.x;

        float f = 1.0f / (DeltaU1 * (uv3.y - uv1.y) - DeltaU2 * (uv2.y - uv1.y));

        dest.x = f * (-DeltaU2 * (v2.x - v1.x) - DeltaU1 * (v3.x - v1.x));
        dest.y = f * (-DeltaU2 * (v2.y - v1.y) - DeltaU1 * (v3.y - v1.y));
        dest.z = f * (-DeltaU2 * (v2.z - v1.z) - DeltaU1 * (v3.z - v1.z));
        dest.normalize();
    }

    /**
     * Calculate the surface tangent and bitangent for the three supplied vertices and UV coordinates and store the result in <code>dest</code>.
     *
     * @param v1
     *            XYZ of first vertex
     * @param uv1
     *            UV of first vertex
     * @param v2
     *            XYZ of second vertex
     * @param uv2
     *            UV of second vertex
     * @param v3
     *            XYZ of third vertex
     * @param uv3
     *            UV of third vertex
     * @param destTangent
     *            the tangent will be stored here
     * @param destBitangent
     *            the bitangent will be stored here
     */
    public static void tangentBitangent(Vector3f v1, Vector2f uv1, Vector3f v2, Vector2f uv2, Vector3f v3, Vector2f uv3, Vector3f destTangent, Vector3f destBitangent) {
        float DeltaV1 = uv2.y - uv1.y;
        float DeltaV2 = uv3.y - uv1.y;
        float DeltaU1 = uv2.x - uv1.x;
        float DeltaU2 = uv3.x - uv1.x;

        float f = 1.0f / (DeltaU1 * DeltaV2 - DeltaU2 * DeltaV1);

        destTangent.x = f * (DeltaV2 * (v2.x - v1.x) - DeltaV1 * (v3.x - v1.x));
        destTangent.y = f * (DeltaV2 * (v2.y - v1.y) - DeltaV1 * (v3.y - v1.y));
        destTangent.z = f * (DeltaV2 * (v2.z - v1.z) - DeltaV1 * (v3.z - v1.z));
        destTangent.normalize();

        destBitangent.x = f * (-DeltaU2 * (v2.x - v1.x) - DeltaU1 * (v3.x - v1.x));
        destBitangent.y = f * (-DeltaU2 * (v2.y - v1.y) - DeltaU1 * (v3.y - v1.y));
        destBitangent.z = f * (-DeltaU2 * (v2.z - v1.z) - DeltaU1 * (v3.z - v1.z));
        destBitangent.normalize();
    }

}
