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
 * Contains intersection tests for some geometric primitives.
 * 
 * @author Kai Burjack
 */
public class Intersection {

    /**
     * Test whether the given ray with the origin <tt>(originX, originY, originZ)</tt> and direction <tt>(dirX, dirY, dirZ)</tt>
     * intersects the given axis-aligned box given as any two opposite corners <tt>(aX, aY, aZ)</tt> and <tt>(bX, bY, bZ)</tt>.
     * <p>
     * This is an implementation of the <a href="http://www.siggraph.org/education/materials/HyperGraph/raytrace/rtinter3.htm">
     * Ray - Box Intersection</a> method, also known as "slab method."
     * <p>
     * If many boxes need to be tested against the same ray, then the {@link RayAabIntersection} class is likely more efficient.
     * 
     * @see #testRayAab(Vector3f, Vector3f, Vector3f, Vector3f)
     * @see RayAabIntersection
     * 
     * @param originX
     *              the x coordinate of the ray's origin
     * @param originY
     *              the y coordinate of the ray's origin
     * @param originZ
     *              the z coordinate of the ray's origin
     * @param dirX
     *              the x coordinate of the ray's direction
     * @param dirY
     *              the y coordinate of the ray's direction
     * @param dirZ
     *              the z coordinate of the ray's direction
     * @param aX
     *              the x coordinate of one corner of the axis-aligned box
     * @param aY
     *              the y coordinate of one corner of the axis-aligned box
     * @param aZ
     *              the z coordinate of one corner of the axis-aligned box
     * @param bX
     *              the x coordinate of the opposite corner of the axis-aligned box
     * @param bY
     *              the y coordinate of the opposite corner of the axis-aligned box
     * @param bZ
     *              the y coordinate of the opposite corner of the axis-aligned box
     * @return <code>true</code> if the given ray intersects the axis-aligned box
     */
    public static boolean testRayAab(float originX, float originY, float originZ, float dirX, float dirY, float dirZ,
            float aX, float aY, float aZ, float bX, float bY, float bZ) {
        float invDirX = 1.0f / dirX, invDirY = 1.0f / dirY, invDirZ = 1.0f / dirZ;
        float tMinX = (aX - originX) * invDirX;
        float tMinY = (aY - originY) * invDirY;
        float tMinZ = (aZ - originZ) * invDirZ;
        float tMaxX = (bX - originX) * invDirX;
        float tMaxY = (bY - originY) * invDirY;
        float tMaxZ = (bZ - originZ) * invDirZ;
        float t1X = Math.min(tMinX, tMaxX);
        float t1Y = Math.min(tMinY, tMaxY);
        float t1Z = Math.min(tMinZ, tMaxZ);
        float t2X = Math.max(tMinX, tMaxX);
        float t2Y = Math.max(tMinY, tMaxY);
        float t2Z = Math.max(tMinZ, tMaxZ);
        float tNear = Math.max(Math.max(t1X, t1Y), t1Z);
        float tFar = Math.min(Math.min(t2X, t2Y), t2Z);
        return tNear < tFar && tFar >= 0.0f;
    }

    /**
     * Test whether the ray with the given <code>origin</code> and direction <code>dir</code>
     * intersects the given axis-aligned box specified as any two opposite corners <code>a</code> and <code>b</code>.
     * <p>
     * This is an implementation of the <a href="http://www.siggraph.org/education/materials/HyperGraph/raytrace/rtinter3.htm">
     * Ray - Box Intersection</a> method, also known as "slab method."
     * <p>
     * If many boxes need to be tested against the same ray, then the {@link RayAabIntersection} class is likely more efficient.
     * 
     * @see #testRayAab(float, float, float, float, float, float, float, float, float, float, float, float)
     * @see RayAabIntersection
     * 
     * @param origin
     *              the ray's origin
     * @param dir
     *              the ray's direction
     * @param a
     *              one corner of the axis-aligned box
     * @param b
     *              the opposite corner of the axis-aligned box
     * @return <code>true</code> if the given ray intersects the axis-aligned box
     */
    public static boolean testRayAab(Vector3f origin, Vector3f dir, Vector3f a, Vector3f b) {
        return testRayAab(origin.x, origin.y, origin.z, dir.x, dir.y, dir.z, a.x, a.y, a.z, b.x, b.y, b.z);
    }

    /**
     * Test whether the given ray with the origin <tt>(originX, originY, originZ)</tt> and direction <tt>(dirX, dirY, dirZ)</tt>
     * intersects with the frontface of the triangle consisting of the three vertices <tt>(v0X, v0Y, v0Z)</tt>, <tt>(v1X, v1Y, v1Z)</tt> and <tt>(v2X, v2Y, v2Z)</tt>.
     * <p>
     * This is an implementation of the <a href="http://www.cs.virginia.edu/~gfx/Courses/2003/ImageSynthesis/papers/Acceleration/Fast%20MinimumStorage%20RayTriangle%20Intersection.pdf">
     * Fast, Minimum Storage Ray/Triangle Intersection</a> method.
     * <p>
     * This test implements backface culling, that is, it will return <code>false</code> when the triangle is in clockwise
     * winding order assuming a <i>right-handed</i> coordinate system when seen along the ray's direction, even if the ray intersects the triangle.
     * This is in compliance with how OpenGL handles backface culling with default frontface/backface settings.
     * 
     * @see #testRayTriangle(Vector3f, Vector3f, Vector3f, Vector3f, Vector3f, float)
     * 
     * @param originX
     *              the x coordinate of the ray's origin
     * @param originY
     *              the y coordinate of the ray's origin
     * @param originZ
     *              the z coordinate of the ray's origin
     * @param dirX
     *              the x coordinate of the ray's direction
     * @param dirY
     *              the y coordinate of the ray's direction
     * @param dirZ
     *              the z coordinate of the ray's direction
     * @param v0X
     *              the x coordinate of the first vertex
     * @param v0Y
     *              the y coordinate of the first vertex
     * @param v0Z
     *              the z coordinate of the first vertex
     * @param v1X
     *              the x coordinate of the second vertex
     * @param v1Y
     *              the y coordinate of the second vertex
     * @param v1Z
     *              the z coordinate of the second vertex
     * @param v2X
     *              the x coordinate of the third vertex
     * @param v2Y
     *              the y coordinate of the second vertex
     * @param v2Z
     *              the z coordinate of the second vertex
     * @param epsilon
     *              a small epsilon when testing rays that are almost parallel to the triangle
     * @return <code>true</code> if the given ray intersects with the frontface of the triangle
     */
    public static boolean testRayTriangle(float originX, float originY, float originZ, float dirX, float dirY, float dirZ,
            float v0X, float v0Y, float v0Z, float v1X, float v1Y, float v1Z, float v2X, float v2Y, float v2Z,
            float epsilon) {
        float edge1X = v1X - v0X;
        float edge1Y = v1Y - v0Y;
        float edge1Z = v1Z - v0Z;
        float edge2X = v2X - v0X;
        float edge2Y = v2Y - v0Y;
        float edge2Z = v2Z - v0Z;
        float pvecX = dirY * edge2Z - dirZ * edge2Y;
        float pvecY = dirZ * edge2X - dirX * edge2Z;
        float pvecZ = dirX * edge2Y - dirY * edge2X;
        float det = edge1X * pvecX + edge1Y * pvecY + edge1Z * pvecZ;
        if (det <= epsilon)
            return false;
        float tvecX = originX - v0X;
        float tvecY = originY - v0Y;
        float tvecZ = originZ - v0Z;
        float u = tvecX * pvecX + tvecY * pvecY + tvecZ * pvecZ;
        if (u < 0.0f || u > det)
            return false;
        float qvecX = tvecY * edge1Z - tvecZ * edge1Y;
        float qvecY = tvecZ * edge1X - tvecX * edge1Z;
        float qvecZ = tvecX * edge1Y - tvecY * edge1X;
        float v = dirX * qvecX + dirY * qvecY + dirZ * qvecZ;
        if (v < 0.0f || u + v > det)
            return false;
        return true;
    }

    /**
     * Test whether the ray with the given <code>origin</code> and the given <code>dir</code> intersects with the frontface of the triangle consisting of the three vertices
     * <code>v0</code>, <code>v1</code> and <code>v2</code>.
     * <p>
     * This is an implementation of the <a href="http://www.cs.virginia.edu/~gfx/Courses/2003/ImageSynthesis/papers/Acceleration/Fast%20MinimumStorage%20RayTriangle%20Intersection.pdf">
     * Fast, Minimum Storage Ray/Triangle Intersection</a> method.
     * <p>
     * This test implements backface culling, that is, it will return <code>false</code> when the triangle is in clockwise
     * winding order assuming a <i>right-handed</i> coordinate system when seen along the ray's direction, even if the ray intersects the triangle.
     * This is in compliance with how OpenGL handles backface culling with default frontface/backface settings.
     * 
     * @see #testRayTriangle(float, float, float, float, float, float, float, float, float, float, float, float, float, float, float, float)
     * 
     * @param origin
     *              the ray's origin
     * @param dir
     *              the ray's direction
     * @param v0
     *              the position of the first vertex
     * @param v1
     *              the position of the second vertex
     * @param v2
     *              the position of the third vertex
     * @param epsilon
     *              a small epsilon when testing rays that are almost parallel to the triangle
     * @return <code>true</code> if the given ray intersects with the frontface of the triangle
     */
    public static boolean testRayTriangle(Vector3f origin, Vector3f dir, Vector3f v0, Vector3f v1, Vector3f v2, float epsilon) {
        return testRayTriangle(origin.x, origin.y, origin.z, dir.x, dir.y, dir.z, v0.x, v0.y, v0.z, v1.x, v1.y, v1.z, v2.x, v2.y, v2.z, epsilon);
    }
    
    /**
     * Test whether the given ray with the origin <tt>(originX, originY, originZ)</tt> and direction <tt>(dirX, dirY, dirZ)</tt>
     * intersects with the frontface of the triangle consisting of the three vertices <tt>(v0X, v0Y, v0Z)</tt>, <tt>(v1X, v1Y, v1Z)</tt> and <tt>(v2X, v2Y, v2Z)</tt>
     * and return the value of the parameter <i>t</i> in the ray equation <i>p(t) = origin + t * dir</i> of the point of intersection.
     * <p>
     * This is an implementation of the <a href="http://www.cs.virginia.edu/~gfx/Courses/2003/ImageSynthesis/papers/Acceleration/Fast%20MinimumStorage%20RayTriangle%20Intersection.pdf">
     * Fast, Minimum Storage Ray/Triangle Intersection</a> method.
     * <p>
     * This test implements backface culling, that is, it will return <code>false</code> when the triangle is in clockwise
     * winding order assuming a <i>right-handed</i> coordinate system when seen along the ray's direction, even if the ray intersects the triangle.
     * This is in compliance with how OpenGL handles backface culling with default frontface/backface settings.
     * 
     * @see #testRayTriangle(Vector3f, Vector3f, Vector3f, Vector3f, Vector3f, float)
     * 
     * @param originX
     *              the x coordinate of the ray's origin
     * @param originY
     *              the y coordinate of the ray's origin
     * @param originZ
     *              the z coordinate of the ray's origin
     * @param dirX
     *              the x coordinate of the ray's direction
     * @param dirY
     *              the y coordinate of the ray's direction
     * @param dirZ
     *              the z coordinate of the ray's direction
     * @param v0X
     *              the x coordinate of the first vertex
     * @param v0Y
     *              the y coordinate of the first vertex
     * @param v0Z
     *              the z coordinate of the first vertex
     * @param v1X
     *              the x coordinate of the second vertex
     * @param v1Y
     *              the y coordinate of the second vertex
     * @param v1Z
     *              the z coordinate of the second vertex
     * @param v2X
     *              the x coordinate of the third vertex
     * @param v2Y
     *              the y coordinate of the second vertex
     * @param v2Z
     *              the z coordinate of the second vertex
     * @param epsilon
     *              a small epsilon when testing rays that are almost parallel to the triangle
     * @return the value of the parameter <i>t</i> in the ray equation <i>p(t) = origin + t * dir</i> of the point of intersection
     *         if the ray intersects with the triangle; <tt>-1.0</tt> otherwise
     */
    public static float intersectRayTriangle(float originX, float originY, float originZ, float dirX, float dirY, float dirZ,
            float v0X, float v0Y, float v0Z, float v1X, float v1Y, float v1Z, float v2X, float v2Y, float v2Z,
            float epsilon) {
        float edge1X = v1X - v0X;
        float edge1Y = v1Y - v0Y;
        float edge1Z = v1Z - v0Z;
        float edge2X = v2X - v0X;
        float edge2Y = v2Y - v0Y;
        float edge2Z = v2Z - v0Z;
        float pvecX = dirY * edge2Z - dirZ * edge2Y;
        float pvecY = dirZ * edge2X - dirX * edge2Z;
        float pvecZ = dirX * edge2Y - dirY * edge2X;
        float det = edge1X * pvecX + edge1Y * pvecY + edge1Z * pvecZ;
        if (det <= epsilon)
            return -1.0f;
        float tvecX = originX - v0X;
        float tvecY = originY - v0Y;
        float tvecZ = originZ - v0Z;
        float u = tvecX * pvecX + tvecY * pvecY + tvecZ * pvecZ;
        if (u < 0.0f || u > det)
            return -1.0f;
        float qvecX = tvecY * edge1Z - tvecZ * edge1Y;
        float qvecY = tvecZ * edge1X - tvecX * edge1Z;
        float qvecZ = tvecX * edge1Y - tvecY * edge1X;
        float v = dirX * qvecX + dirY * qvecY + dirZ * qvecZ;
        if (v < 0.0f || u + v > det)
            return -1.0f;
        float invDet = 1.0f / det;
        float t = (edge2X * qvecX + edge2Y * qvecY + edge2Z * qvecZ) * invDet;
        return t;
    }

    /**
     * Test whether the ray with the given <code>origin</code> and the given <code>dir</code> intersects with the frontface of the triangle consisting of the three vertices
     * <code>v0</code>, <code>v1</code> and <code>v2</code> and return the value of the parameter <i>t</i> in the ray equation <i>p(t) = origin + t * dir</i> of the point of intersection.
     * <p>
     * This is an implementation of the <a href="http://www.cs.virginia.edu/~gfx/Courses/2003/ImageSynthesis/papers/Acceleration/Fast%20MinimumStorage%20RayTriangle%20Intersection.pdf">
     * Fast, Minimum Storage Ray/Triangle Intersection</a> method.
     * <p>
     * This test implements backface culling, that is, it will return <code>false</code> when the triangle is in clockwise
     * winding order assuming a <i>right-handed</i> coordinate system when seen along the ray's direction, even if the ray intersects the triangle.
     * This is in compliance with how OpenGL handles backface culling with default frontface/backface settings.
     * 
     * @see #intersectRayTriangle(float, float, float, float, float, float, float, float, float, float, float, float, float, float, float, float)
     * 
     * @param origin
     *              the ray's origin
     * @param dir
     *              the ray's direction
     * @param v0
     *              the position of the first vertex
     * @param v1
     *              the position of the second vertex
     * @param v2
     *              the position of the third vertex
     * @param epsilon
     *              a small epsilon when testing rays that are almost parallel to the triangle
     * @return the value of the parameter <i>t</i> in the ray equation <i>p(t) = origin + t * dir</i> of the point of intersection
     *         if the ray intersects with the triangle; <tt>-1.0</tt> otherwise
     */
    public static float intersectRayTriangle(Vector3f origin, Vector3f dir, Vector3f v0, Vector3f v1, Vector3f v2, float epsilon) {
        return intersectRayTriangle(origin.x, origin.y, origin.z, dir.x, dir.y, dir.z, v0.x, v0.y, v0.z, v1.x, v1.y, v1.z, v2.x, v2.y, v2.z, epsilon);
    }

}
