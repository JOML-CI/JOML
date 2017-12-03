/*
 * (C) Copyright 2015-2017 Kai Burjack

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

import org.joml.api.vector.IVector2f;
import org.joml.api.vector.IVector3f;
import org.joml.api.vector.Vector3fc;

/**
 * Useful geometry methods.
 * 
 * @author Kai Burjack
 * @author Richard Greenlees
 */
public class GeometryUtils {

    /**
     * Compute two arbitrary vectors perpendicular to the given normalized vector <tt>(x, y, z)</tt>, and store them in <code>dest1</code> and <code>dest2</code>,
     * respectively.
     * <p>
     * The computed vectors will themselves be perpendicular to each another and normalized. So the tree vectors <tt>(x, y, z)</tt>, <code>dest1</code> and
     * <code>dest2</code> form an orthonormal basis.
     * 
     * @param x
     *            the x coordinate of the normalized input vector
     * @param y
     *            the y coordinate of the normalized input vector
     * @param z
     *            the z coordinate of the normalized input vector
     * @param dest1
     *            will hold the first perpendicular vector
     * @param dest2
     *            will hold the second perpendicular vector
     */
    public static void perpendicular(float x, float y, float z, Vector3fc dest1, Vector3fc dest2) {
        float magX = z * z + y * y;
        float magY = z * z + x * x;
        float magZ = y * y + x * x;
        float mag;
        if (magX > magY && magX > magZ) {
            dest1.set(0, z, -y);
            mag = magX;
        } else if (magY > magZ) {
            dest1.set(z, 0, x);
            mag = magY;
        } else {
            dest1.set(y, -x, 0);
            mag = magZ;
        }
        float len = 1.0f / (float) Math.sqrt(mag);
        dest1.set(dest1.x()*len,
                dest1.y()*len,
                dest1.z()*len);
        dest2.set(y * dest1.z() - z * dest1.y(),
                z * dest1.x() - x * dest1.z(),
                x * dest1.y() - y * dest1.x());
    }

    /**
     * Compute two arbitrary vectors perpendicular to the given normalized vector <code>v</code>, and store them in <code>dest1</code> and <code>dest2</code>,
     * respectively.
     * <p>
     * The computed vectors will themselves be perpendicular to each another and normalized. So the tree vectors <code>v</code>, <code>dest1</code> and
     * <code>dest2</code> form an orthonormal basis.
     * 
     * @param v
     *            the {@link Vector3fc#normalize() normalized} input vector
     * @param dest1
     *            will hold the first perpendicular vector
     * @param dest2
     *            will hold the second perpendicular vector
     */
    public static void perpendicular(IVector3f v, Vector3fc dest1, Vector3fc dest2) {
        perpendicular(v.x(), v.y(), v.z(), dest1, dest2);
    }

    /**
     * Calculate the normal of a surface defined by points <code>v1</code>, <code>v2</code> and <code>v3</code> and store it in <code>dest</code>.
     * 
     * @param v0
     *            the first position
     * @param v1
     *            the second position
     * @param v2
     *            the third position
     * @param dest
     *            will hold the result
     */
    public static void normal(IVector3f v0, IVector3f v1, IVector3f v2, Vector3fc dest) {
        normal(v0.x(), v0.y(), v0.z(), v1.x(), v1.y(), v1.z(), v2.x(), v2.y(), v2.z(), dest);
    }

    /**
     * Calculate the normal of a surface defined by points <tt>(v1X, v1Y, v1Z)</tt>, <tt>(v2X, v2Y, v2Z)</tt> and <tt>(v3X, v3Y, v3Z)</tt>
     * and store it in <code>dest</code>.
     * 
     * @param v0X
     *            the x coordinate of the first position
     * @param v0Y
     *            the y coordinate of the first position
     * @param v0Z
     *            the z coordinate of the first position
     * @param v1X
     *            the x coordinate of the second position
     * @param v1Y
     *            the y coordinate of the second position
     * @param v1Z
     *            the z coordinate of the second position
     * @param v2X
     *            the x coordinate of the third position
     * @param v2Y
     *            the y coordinate of the third position
     * @param v2Z
     *            the z coordinate of the third position
     * @param dest
     *            will hold the result
     */
    public static void normal(float v0X, float v0Y, float v0Z, float v1X, float v1Y, float v1Z, float v2X, float v2Y, float v2Z, Vector3fc dest) {
        dest.set(((v1Y - v0Y) * (v2Z - v0Z)) - ((v1Z - v0Z) * (v2Y - v0Y)),
                ((v1Z - v0Z) * (v2X - v0X)) - ((v1X - v0X) * (v2Z - v0Z)),
                ((v1X - v0X) * (v2Y - v0Y)) - ((v1Y - v0Y) * (v2X - v0X)));
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
    public static void tangent(IVector3f v1, IVector2f uv1, IVector3f v2, IVector2f uv2, IVector3f v3, IVector2f uv3, Vector3fc dest) {
        float DeltaV1 = uv2.y() - uv1.y();
        float DeltaV2 = uv3.y() - uv1.y();

        float f = 1.0f / ((uv2.x() - uv1.x()) * DeltaV2 - (uv3.x() - uv1.x()) * DeltaV1);

        dest.set(f * (DeltaV2 * (v2.x() - v1.x()) - DeltaV1 * (v3.x() - v1.x())),
                f * (DeltaV2 * (v2.y() - v1.y()) - DeltaV1 * (v3.y() - v1.y())),
                f * (DeltaV2 * (v2.z() - v1.z()) - DeltaV1 * (v3.z() - v1.z())));
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
    public static void bitangent(IVector3f v1, IVector2f uv1, IVector3f v2, IVector2f uv2, IVector3f v3, IVector2f uv3, Vector3fc dest) {
        float DeltaU1 = uv2.x() - uv1.x();
        float DeltaU2 = uv3.x() - uv1.x();

        float f = 1.0f / (DeltaU1 * (uv3.y() - uv1.y()) - DeltaU2 * (uv2.y() - uv1.y()));

        dest.set(f * (-DeltaU2 * (v2.x() - v1.x()) - DeltaU1 * (v3.x() - v1.x())),
                f * (-DeltaU2 * (v2.y() - v1.y()) - DeltaU1 * (v3.y() - v1.y())),
                f * (-DeltaU2 * (v2.z() - v1.z()) - DeltaU1 * (v3.z() - v1.z())));
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
    public static void tangentBitangent(IVector3f v1, IVector2f uv1, IVector3f v2, IVector2f uv2, IVector3f v3, IVector2f uv3, Vector3fc destTangent, Vector3fc destBitangent) {
        float DeltaV1 = uv2.y() - uv1.y();
        float DeltaV2 = uv3.y() - uv1.y();
        float DeltaU1 = uv2.x() - uv1.x();
        float DeltaU2 = uv3.x() - uv1.x();

        float f = 1.0f / (DeltaU1 * DeltaV2 - DeltaU2 * DeltaV1);

        destTangent.set(f * (DeltaV2 * (v2.x() - v1.x()) - DeltaV1 * (v3.x() - v1.x())),
                f * (DeltaV2 * (v2.y() - v1.y()) - DeltaV1 * (v3.y() - v1.y())),
                f * (DeltaV2 * (v2.z() - v1.z()) - DeltaV1 * (v3.z() - v1.z())));
        destTangent.normalize();

        destBitangent.set(f * (-DeltaU2 * (v2.x() - v1.x()) - DeltaU1 * (v3.x() - v1.x())),
                f * (-DeltaU2 * (v2.y() - v1.y()) - DeltaU1 * (v3.y() - v1.y())),
                f * (-DeltaU2 * (v2.z() - v1.z()) - DeltaU1 * (v3.z() - v1.z())));
        destBitangent.normalize();
    }

}
