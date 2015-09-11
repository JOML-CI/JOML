/*
 * (C) Copyright 2015 Kai Burjack

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
     * Calculate the normal of a surface defined by points <code>v1</code>, <code>v2</code> and <code>v3</code> and
     * store it in <code>dest</code>.
     * 
     * @param v1
     *          the first position
     * @param v2
     *          the second position
     * @param v3
     *          the third position
     * @param dest
     *          will hold the result
     */
    public static void normal(Vector3fr v1, Vector3fr v2, Vector3fr v3, Vector3f dest) {
        dest.x = ((v2.y() - v1.y()) * (v3.z() - v1.z())) - ((v2.z() - v1.z()) * (v3.y() - v1.y()));
        dest.y = ((v2.z() - v1.z()) * (v3.x() - v1.x())) - ((v2.x() - v1.x()) * (v3.z() - v1.z()));
        dest.z = ((v2.x() - v1.x()) * (v3.y() - v1.y())) - ((v2.y() - v1.y()) * (v3.x() - v1.x()));
        dest.normalize();
    }

    /**
     * Calculate the surface tangent for the three supplied vertices and UV
     * coordinates and store the result in <code>dest</code>.
     *
     * @param v1 XYZ of first vertex
     * @param uv1 UV of first vertex
     * @param v2 XYZ of second vertex
     * @param uv2 UV of second vertex
     * @param v3 XYZ of third vertex
     * @param uv3 UV of third vertex
     * @param dest the tangent will be stored here
     */
    public static void tangent(Vector3fr v1, Vector2fr uv1, Vector3fr v2, Vector2fr uv2, Vector3fr v3, Vector2fr uv3, Vector3f dest) {
        float deltaV1 = uv2.y() - uv1.y();
        float deltaV2 = uv3.y() - uv1.y();

        float f = 1.0f / ((uv2.x() - uv1.x()) * deltaV2 - (uv3.x() - uv1.x()) * deltaV1);

        dest.x = f * (deltaV2 * (v2.x() - v1.x()) - deltaV1 * (v3.x() - v1.x()));
        dest.y = f * (deltaV2 * (v2.y() - v1.y()) - deltaV1 * (v3.y() - v1.y()));
        dest.z = f * (deltaV2 * (v2.z() - v1.z()) - deltaV1 * (v3.z() - v1.z()));

        dest.normalize();
    }

    /**
     * Calculate the surface bitangent for the three supplied vertices and UV
     * coordinates and store the result in <code>dest</code>.
     *
     * @param v1 XYZ of first vertex
     * @param uv1 UV of first vertex
     * @param v2 XYZ of second vertex
     * @param uv2 UV of second vertex
     * @param v3 XYZ of third vertex
     * @param uv3 UV of third vertex
     * @param dest the binormal will be stored here
     */
    public static void bitangent(Vector3fr v1, Vector2fr uv1, Vector3fr v2, Vector2fr uv2, Vector3fr v3, Vector2fr uv3, Vector3f dest) {
        float deltaU1 = uv2.x() - uv1.x();
        float deltaU2 = uv3.x() - uv1.x();

        float f = 1.0f / (deltaU1 * (uv3.y() - uv1.y()) - deltaU2 * (uv2.y() - uv1.y()));

        dest.x = f * (-deltaU2 * (v2.x() - v1.x()) - deltaU1 * (v3.x() - v1.x()));
        dest.y = f * (-deltaU2 * (v2.y() - v1.y()) - deltaU1 * (v3.y() - v1.y()));
        dest.z = f * (-deltaU2 * (v2.z() - v1.z()) - deltaU1 * (v3.z() - v1.z()));

        dest.normalize();
    }

    /**
     * Calculate the surface tangent and bitangent for the three supplied vertices
     * and UV coordinates and store the result in <code>dest</code>.
     *
     * @param v1 XYZ of first vertex
     * @param uv1 UV of first vertex
     * @param v2 XYZ of second vertex
     * @param uv2 UV of second vertex
     * @param v3 XYZ of third vertex
     * @param uv3 UV of third vertex
     * @param destTangent the tangent will be stored here
     * @param destBitangent the bitangent will be stored here
     */
    public static void tangentBitangent(Vector3fr v1, Vector2fr uv1, Vector3fr v2, Vector2fr uv2, Vector3fr v3, Vector2fr uv3, Vector3f destTangent, Vector3f destBitangent) {
        float deltaV1 = uv2.y() - uv1.y();
        float deltaV2 = uv3.y() - uv1.y();
        float deltaU1 = uv2.x() - uv1.x();
        float deltaU2 = uv3.x() - uv1.x();

        float f = 1.0f / (deltaU1 * deltaV2 - deltaU2 * deltaV1);

        destTangent.x = f * (deltaV2 * (v2.x() - v1.x()) - deltaV1 * (v3.x() - v1.x()));
        destTangent.y = f * (deltaV2 * (v2.y() - v1.y()) - deltaV1 * (v3.y() - v1.y()));
        destTangent.z = f * (deltaV2 * (v2.z() - v1.z()) - deltaV1 * (v3.z() - v1.z()));

        destTangent.normalize();

        destBitangent.x = f * (-deltaU2 * (v2.x() - v1.x()) - deltaU1 * (v3.x() - v1.x()));
        destBitangent.y = f * (-deltaU2 * (v2.y() - v1.y()) - deltaU1 * (v3.y() - v1.y()));
        destBitangent.z = f * (-deltaU2 * (v2.z() - v1.z()) - deltaU1 * (v3.z() - v1.z()));

        destBitangent.normalize();
    }

}
