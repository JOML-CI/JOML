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
 * Contains intersection and distance tests for some 2D and 3D geometric primitives.
 * 
 * @author Kai Burjack
 */
public class Intersectiond {

    /**
     * Return value of
     * {@link #findClosestPointOnTriangle(double, double, double, double, double, double, double, double, double, double, double, double, Vector3d)},
     * {@link #findClosestPointOnTriangle(Vector3d, Vector3d, Vector3d, Vector3d, Vector3d)},
     * {@link #findClosestPointOnTriangle(double, double, double, double, double, double, double, double, Vector2d)} and
     * {@link #findClosestPointOnTriangle(Vector2d, Vector2d, Vector2d, Vector2d, Vector2d)}
     * to signal that the closest point is a vertex of the triangle.
     */
    public static final int POINT_ON_TRIANGLE_VERTEX = 0;
    /**
     * Return value of
     * {@link #findClosestPointOnTriangle(double, double, double, double, double, double, double, double, double, double, double, double, Vector3d)},
     * {@link #findClosestPointOnTriangle(Vector3d, Vector3d, Vector3d, Vector3d, Vector3d)},
     * {@link #findClosestPointOnTriangle(double, double, double, double, double, double, double, double, Vector2d)} and
     * {@link #findClosestPointOnTriangle(Vector2d, Vector2d, Vector2d, Vector2d, Vector2d)}
     * to signal that the closest point lies on an edge of the triangle.
     */
    public static final int POINT_ON_TRIANGLE_EDGE = 1;
    /**
     * Return value of
     * {@link #findClosestPointOnTriangle(double, double, double, double, double, double, double, double, double, double, double, double, Vector3d)},
     * {@link #findClosestPointOnTriangle(Vector3d, Vector3d, Vector3d, Vector3d, Vector3d)},
     * {@link #findClosestPointOnTriangle(double, double, double, double, double, double, double, double, Vector2d)} and
     * {@link #findClosestPointOnTriangle(Vector2d, Vector2d, Vector2d, Vector2d, Vector2d)}
     * to signal that the closest point lies on the face of the triangle.
     */
    public static final int POINT_ON_TRIANGLE_FACE = 2;

    /**
     * Test whether the plane with the general plane equation <i>a*x + b*y + c*z + d = 0</i> intersects the sphere with center
     * <tt>(centerX, centerY, centerZ)</tt> and <code>radius</code>.
     * <p>
     * Reference: <a href="http://math.stackexchange.com/questions/943383/determine-circle-of-intersection-of-plane-and-sphere">http://math.stackexchange.com</a>
     *
     * @param a
     *          the x factor in the plane equation
     * @param b
     *          the y factor in the plane equation
     * @param c
     *          the z factor in the plane equation
     * @param d
     *          the constant in the plane equation
     * @param centerX
     *          the x coordinate of the sphere's center
     * @param centerY
     *          the y coordinate of the sphere's center
     * @param centerZ
     *          the z coordinate of the sphere's center
     * @param radius
     *          the radius of the sphere
     * @return <code>true</code> iff the plane intersects the sphere; <code>false</code> otherwise
     */
    public static boolean testPlaneSphere(
            double a, double b, double c, double d,
            double centerX, double centerY, double centerZ, double radius) {
        double denom = Math.sqrt(a * a + b * b + c * c);
        double dist = (a * centerX + b * centerY + c * centerZ + d) / denom;
        return -radius <= dist && dist <= radius;
    }

    /**
     * Test whether the plane with the general plane equation <i>a*x + b*y + c*z + d = 0</i> intersects the sphere with center
     * <tt>(centerX, centerY, centerZ)</tt> and <code>radius</code>, and store the center of the circle of
     * intersection in the <tt>(x, y, z)</tt> components of the supplied vector and the radius of that circle in the w component.
     * <p>
     * Reference: <a href="http://math.stackexchange.com/questions/943383/determine-circle-of-intersection-of-plane-and-sphere">http://math.stackexchange.com</a>
     *
     * @param a
     *          the x factor in the plane equation
     * @param b
     *          the y factor in the plane equation
     * @param c
     *          the z factor in the plane equation
     * @param d
     *          the constant in the plane equation
     * @param centerX
     *          the x coordinate of the sphere's center
     * @param centerY
     *          the y coordinate of the sphere's center
     * @param centerZ
     *          the z coordinate of the sphere's center
     * @param radius
     *          the radius of the sphere
     * @param intersectionCenterAndRadius
     *          will hold the center of the circle of intersection in the <tt>(x, y, z)</tt> components and the radius in the w component
     * @return <code>true</code> iff the plane intersects the sphere; <code>false</code> otherwise
     */
    public static boolean intersectPlaneSphere(
            double a, double b, double c, double d,
            double centerX, double centerY, double centerZ, double radius,
            Vector4d intersectionCenterAndRadius) {
        double invDenom = 1.0 / Math.sqrt(a * a + b * b + c * c);
        double dist = (a * centerX + b * centerY + c * centerZ + d) * invDenom;
        if (-radius <= dist && dist <= radius) {
            intersectionCenterAndRadius.x = centerX + dist * a * invDenom;
            intersectionCenterAndRadius.y = centerY + dist * b * invDenom;
            intersectionCenterAndRadius.z = centerZ + dist * c * invDenom;
            intersectionCenterAndRadius.w = Math.sqrt(radius * radius - dist * dist);
            return true;
        }
        return false;
    }

    /**
     * Test whether the axis-aligned box with minimum corner <tt>(minX, minY, minZ)</tt> and maximum corner <tt>(maxX, maxY, maxZ)</tt>
     * intersects the plane with the general equation <i>a*x + b*y + c*z + d = 0</i>.
     * <p>
     * Reference: <a href="http://zach.in.tu-clausthal.de/teaching/cg_literatur/lighthouse3d_view_frustum_culling/index.html">http://zach.in.tu-clausthal.de</a> ("Geometric Approach - Testing Boxes II")
     * 
     * @param minX
     *          the x coordinate of the minimum corner of the axis-aligned box
     * @param minY
     *          the y coordinate of the minimum corner of the axis-aligned box
     * @param minZ
     *          the z coordinate of the minimum corner of the axis-aligned box
     * @param maxX
     *          the x coordinate of the maximum corner of the axis-aligned box
     * @param maxY
     *          the y coordinate of the maximum corner of the axis-aligned box
     * @param maxZ
     *          the z coordinate of the maximum corner of the axis-aligned box
     * @param a
     *          the x factor in the plane equation
     * @param b
     *          the y factor in the plane equation
     * @param c
     *          the z factor in the plane equation
     * @param d
     *          the constant in the plane equation
     * @return <code>true</code> iff the axis-aligned box intersects the plane; <code>false</code> otherwise
     */
    public static boolean testAabPlane(
            double minX, double minY, double minZ,
            double maxX, double maxY, double maxZ,
            double a, double b, double c, double d) {
        double pX, pY, pZ, nX, nY, nZ;
        if (a > 0.0) {
            pX = maxX;
            nX = minX;
        } else {
            pX = minX;
            nX = maxX;
        }
        if (b > 0.0) {
            pY = maxY;
            nY = minY;
        } else {
            pY = minY;
            nY = maxY;
        }
        if (c > 0.0) {
            pZ = maxZ;
            nZ = minZ;
        } else {
            pZ = minZ;
            nZ = maxZ;
        }
        double distN = d + a * nX + b * nY + c * nZ;
        double distP = d + a * pX + b * pY + c * pZ;
        return distN <= 0.0 && distP >= 0.0;
    }

    /**
     * Test whether the axis-aligned box with minimum corner <code>min</code> and maximum corner <code>max</code>
     * intersects the plane with the general equation <i>a*x + b*y + c*z + d = 0</i>.
     * <p>
     * Reference: <a href="http://zach.in.tu-clausthal.de/teaching/cg_literatur/lighthouse3d_view_frustum_culling/index.html">http://zach.in.tu-clausthal.de</a> ("Geometric Approach - Testing Boxes II")
     * 
     * @param min
     *          the minimum corner of the axis-aligned box
     * @param max
     *          the maximum corner of the axis-aligned box
     * @param a
     *          the x factor in the plane equation
     * @param b
     *          the y factor in the plane equation
     * @param c
     *          the z factor in the plane equation
     * @param d
     *          the constant in the plane equation
     * @return <code>true</code> iff the axis-aligned box intersects the plane; <code>false</code> otherwise
     */
    public static boolean testAabPlane(Vector3d min, Vector3d max, double a, double b, double c, double d) {
        return testAabPlane(min.x, min.y, min.z, max.x, max.y, max.z, a, b, c, d);
    }

    /**
     * Test whether the axis-aligned box with minimum corner <tt>(minXA, minYA, minZA)</tt> and maximum corner <tt>(maxXA, maxYA, maxZA)</tt>
     * intersects the axis-aligned box with minimum corner <tt>(minXB, minYB, minZB)</tt> and maximum corner <tt>(maxXB, maxYB, maxZB)</tt>.
     * 
     * @param minXA
     *              the x coordinate of the minimum corner of the first axis-aligned box
     * @param minYA
     *              the y coordinate of the minimum corner of the first axis-aligned box
     * @param minZA
     *              the z coordinate of the minimum corner of the first axis-aligned box
     * @param maxXA
     *              the x coordinate of the maximum corner of the first axis-aligned box
     * @param maxYA
     *              the y coordinate of the maximum corner of the first axis-aligned box
     * @param maxZA
     *              the z coordinate of the maximum corner of the first axis-aligned box
     * @param minXB
     *              the x coordinate of the minimum corner of the second axis-aligned box
     * @param minYB
     *              the y coordinate of the minimum corner of the second axis-aligned box
     * @param minZB
     *              the z coordinate of the minimum corner of the second axis-aligned box
     * @param maxXB
     *              the x coordinate of the maximum corner of the second axis-aligned box
     * @param maxYB
     *              the y coordinate of the maximum corner of the second axis-aligned box
     * @param maxZB
     *              the z coordinate of the maximum corner of the second axis-aligned box
     * @return <code>true</code> iff both axis-aligned boxes intersect; <code>false</code> otherwise
     */
    public static boolean testAabAab(
            double minXA, double minYA, double minZA,
            double maxXA, double maxYA, double maxZA,
            double minXB, double minYB, double minZB,
            double maxXB, double maxYB, double maxZB) {
        return maxXA >= minXB && maxYA >= minYB && maxZA >= minZB && 
               minXA <= maxXB && minYA <= maxYB && minZA <= maxZB;
    }

    /**
     * Test whether the axis-aligned box with minimum corner <code>minA</code> and maximum corner <code>maxA</code>
     * intersects the axis-aligned box with minimum corner <code>minB</code> and maximum corner <code>maxB</code>.
     * 
     * @param minA
     *              the minimum corner of the first axis-aligned box
     * @param maxA
     *              the maximum corner of the first axis-aligned box
     * @param minB
     *              the minimum corner of the second axis-aligned box
     * @param maxB
     *              the maximum corner of the second axis-aligned box
     * @return <code>true</code> iff both axis-aligned boxes intersect; <code>false</code> otherwise
     */
    public static boolean testAabAab(Vector3d minA, Vector3d maxA, Vector3d minB, Vector3d maxB) {
        return testAabAab(minA.x, minA.y, minA.z, maxA.x, maxA.y, maxA.z, minB.x, minB.y, minB.z, maxB.x, maxB.y, maxB.z);
    }

    /**
     * Test whether the one sphere with center <tt>(aX, aY, aZ)</tt> and square radius <code>radiusSquaredA</code> intersects the other
     * sphere with center <tt>(bX, bY, bZ)</tt> and square radius <code>radiusSquaredB</code>, and store the center of the circle of
     * intersection in the <tt>(x, y, z)</tt> components of the supplied vector and the radius of that circle in the w component.
     * <p>
     * The normal vector of the circle of intersection can simply be obtained by subtracting the center of either sphere from the other.
     * <p>
     * Reference: <a href="http://gamedev.stackexchange.com/questions/75756/sphere-sphere-intersection-and-circle-sphere-intersection">http://gamedev.stackexchange.com</a>
     * 
     * @param aX
     *              the x coordinate of the first sphere's center
     * @param aY
     *              the y coordinate of the first sphere's center
     * @param aZ
     *              the z coordinate of the first sphere's center
     * @param radiusSquaredA
     *              the square of the first sphere's radius
     * @param bX
     *              the x coordinate of the second sphere's center
     * @param bY
     *              the y coordinate of the second sphere's center
     * @param bZ
     *              the z coordinate of the second sphere's center
     * @param radiusSquaredB
     *              the square of the second sphere's radius
     * @param centerAndRadiusOfIntersectionCircle
     *              will hold the center of the circle of intersection in the <tt>(x, y, z)</tt> components and the radius in the w component
     * @return <code>true</code> iff both spheres intersect; <code>false</code> otherwise
     */
    public static boolean intersectSphereSphere(
            double aX, double aY, double aZ, double radiusSquaredA,
            double bX, double bY, double bZ, double radiusSquaredB,
            Vector4d centerAndRadiusOfIntersectionCircle) {
        double dX = bX - aX, dY = bY - aY, dZ = bZ - aZ;
        double distSquared = dX * dX + dY * dY + dZ * dZ;
        double h = 0.5 + (radiusSquaredA - radiusSquaredB) / distSquared;
        double r_i = radiusSquaredA - h * h * distSquared;
        if (r_i >= 0.0) {
            centerAndRadiusOfIntersectionCircle.x = aX + h * dX;
            centerAndRadiusOfIntersectionCircle.y = aY + h * dY;
            centerAndRadiusOfIntersectionCircle.z = aZ + h * dZ;
            centerAndRadiusOfIntersectionCircle.w = Math.sqrt(r_i);
            return true;
        }
        return false;
    }

    /**
     * Test whether the one sphere with center <code>centerA</code> and square radius <code>radiusSquaredA</code> intersects the other
     * sphere with center <code>centerB</code> and square radius <code>radiusSquaredB</code>, and store the center of the circle of
     * intersection in the <tt>(x, y, z)</tt> components of the supplied vector and the radius of that circle in the w component.
     * <p>
     * The normal vector of the circle of intersection can simply be obtained by subtracting the center of either sphere from the other.
     * <p>
     * Reference: <a href="http://gamedev.stackexchange.com/questions/75756/sphere-sphere-intersection-and-circle-sphere-intersection">http://gamedev.stackexchange.com</a>
     * 
     * @param centerA
     *              the first sphere's center
     * @param radiusSquaredA
     *              the square of the first sphere's radius
     * @param centerB
     *              the second sphere's center
     * @param radiusSquaredB
     *              the square of the second sphere's radius
     * @param centerAndRadiusOfIntersectionCircle
     *              will hold the center of the circle of intersection in the <tt>(x, y, z)</tt> components and the radius in the w component
     * @return <code>true</code> iff both spheres intersect; <code>false</code> otherwise
     */
    public static boolean intersectSphereSphere(Vector3d centerA, double radiusSquaredA, Vector3d centerB, double radiusSquaredB, Vector4d centerAndRadiusOfIntersectionCircle) {
        return intersectSphereSphere(centerA.x, centerA.y, centerA.z, radiusSquaredB, centerB.x, centerB.y, centerB.z, radiusSquaredB, centerAndRadiusOfIntersectionCircle);
    }

    /**
     * Test whether the one sphere with center <tt>(aX, aY, aZ)</tt> and square radius <code>radiusSquaredA</code> intersects the other
     * sphere with center <tt>(bX, bY, bZ)</tt> and square radius <code>radiusSquaredB</code>.
     * <p>
     * Reference: <a href="http://gamedev.stackexchange.com/questions/75756/sphere-sphere-intersection-and-circle-sphere-intersection">http://gamedev.stackexchange.com</a>
     * 
     * @param aX
     *              the x coordinate of the first sphere's center
     * @param aY
     *              the y coordinate of the first sphere's center
     * @param aZ
     *              the z coordinate of the first sphere's center
     * @param radiusSquaredA
     *              the square of the first sphere's radius
     * @param bX
     *              the x coordinate of the second sphere's center
     * @param bY
     *              the y coordinate of the second sphere's center
     * @param bZ
     *              the z coordinate of the second sphere's center
     * @param radiusSquaredB
     *              the square of the second sphere's radius
     * @return <code>true</code> iff both spheres intersect; <code>false</code> otherwise
     */
    public static boolean testSphereSphere(
            double aX, double aY, double aZ, double radiusSquaredA,
            double bX, double bY, double bZ, double radiusSquaredB) {
        double dX = bX - aX, dY = bY - aY, dZ = bZ - aZ;
        double distSquared = dX * dX + dY * dY + dZ * dZ;
        double h = 0.5 + (radiusSquaredA - radiusSquaredB) / distSquared;
        double r_i = radiusSquaredA - h * h * distSquared;
        return r_i >= 0.0;
    }

    /**
     * Test whether the one sphere with center <code>centerA</code> and square radius <code>radiusSquaredA</code> intersects the other
     * sphere with center <code>centerB</code> and square radius <code>radiusSquaredB</code>.
     * <p>
     * Reference: <a href="http://gamedev.stackexchange.com/questions/75756/sphere-sphere-intersection-and-circle-sphere-intersection">http://gamedev.stackexchange.com</a>
     * 
     * @param centerA
     *              the first sphere's center
     * @param radiusSquaredA
     *              the square of the first sphere's radius
     * @param centerB
     *              the second sphere's center
     * @param radiusSquaredB
     *              the square of the second sphere's radius
     * @return <code>true</code> iff both spheres intersect; <code>false</code> otherwise
     */
    public static boolean testSphereSphere(Vector3d centerA, double radiusSquaredA, Vector3d centerB, double radiusSquaredB) {
        return testSphereSphere(centerA.x, centerA.y, centerA.z, radiusSquaredA, centerB.x, centerB.y, centerB.z, radiusSquaredB);
    }

    /**
     * Determine the signed distance of the given point <tt>(pointX, pointY, pointZ)</tt> to the plane specified via its general plane equation
     * <i>a*x + b*y + c*z + d = 0</i>.
     * 
     * @param pointX
     *              the x coordinate of the point
     * @param pointY
     *              the y coordinate of the point
     * @param pointZ
     *              the z coordinate of the point
     * @param a
     *              the x factor in the plane equation
     * @param b
     *              the y factor in the plane equation
     * @param c
     *              the z factor in the plane equation
     * @param d
     *              the constant in the plane equation
     * @return the distance between the point and the plane
     */
    public static double distancePointPlane(double pointX, double pointY, double pointZ, double a, double b, double c, double d) {
        double denom = Math.sqrt(a * a + b * b + c * c);
        return (a * pointX + b * pointY + c * pointZ + d) / denom;
    }

    /**
     * Test whether the ray with given origin <tt>(originX, originY, originZ)</tt> and direction <tt>(dirX, dirY, dirZ)</tt> intersects the plane
     * containing the given point <tt>(pointX, pointY, pointZ)</tt> and having the normal <tt>(normalX, normalY, normalZ)</tt>, and return the
     * value of the parameter <i>t</i> in the ray equation <i>p(t) = origin + t * dir</i> of the intersection point.
     * <p>
     * This method returns <tt>-1.0</tt> if the ray does not intersect the plane, because it is either parallel to the plane or its direction points
     * away from the plane or the ray's origin is on the <i>negative</i> side of the plane (i.e. the plane's normal points away from the ray's origin).
     * <p>
     * Reference: <a href="https://www.siggraph.org/education/materials/HyperGraph/raytrace/rayplane_intersection.htm">https://www.siggraph.org/</a>
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
     * @param pointX
     *              the x coordinate of a point on the plane
     * @param pointY
     *              the y coordinate of a point on the plane
     * @param pointZ
     *              the z coordinate of a point on the plane
     * @param normalX
     *              the x coordinate of the plane's normal
     * @param normalY
     *              the y coordinate of the plane's normal
     * @param normalZ
     *              the z coordinate of the plane's normal
     * @param epsilon
     *              some small epsilon for when the ray is parallel to the plane
     * @return the value of the parameter <i>t</i> in the ray equation <i>p(t) = origin + t * dir</i> of the intersection point, if the ray
     *         intersects the plane; <tt>-1.0</tt> otherwise
     */
    public static double intersectRayPlane(double originX, double originY, double originZ, double dirX, double dirY, double dirZ,
            double pointX, double pointY, double pointZ, double normalX, double normalY, double normalZ, double epsilon) {
        double denom = normalX * dirX + normalY * dirY + normalZ * dirZ;
        if (denom < epsilon) {
            double t = ((pointX - originX) * normalX + (pointY - originY) * normalY + (pointZ - originZ) * normalZ) / denom;
            if (t >= 0.0)
                return t;
        }
        return -1.0;
    }

    /**
     * Test whether the ray with given <code>origin</code> and direction <code>dir</code> intersects the plane
     * containing the given <code>point</code> and having the given <code>normal</code>, and return the
     * value of the parameter <i>t</i> in the ray equation <i>p(t) = origin + t * dir</i> of the intersection point.
     * <p>
     * This method returns <tt>-1.0</tt> if the ray does not intersect the plane, because it is either parallel to the plane or its direction points
     * away from the plane or the ray's origin is on the <i>negative</i> side of the plane (i.e. the plane's normal points away from the ray's origin).
     * <p>
     * Reference: <a href="https://www.siggraph.org/education/materials/HyperGraph/raytrace/rayplane_intersection.htm">https://www.siggraph.org/</a>
     * 
     * @param origin
     *              the ray's origin
     * @param dir
     *              the ray's direction
     * @param point
     *              a point on the plane
     * @param normal
     *              the plane's normal
     * @param epsilon
     *              some small epsilon for when the ray is parallel to the plane
     * @return the value of the parameter <i>t</i> in the ray equation <i>p(t) = origin + t * dir</i> of the intersection point, if the ray
     *         intersects the plane; <tt>-1.0</tt> otherwise
     */
    public static double intersectRayPlane(Vector3d origin, Vector3d dir, Vector3d point, Vector3d normal, double epsilon) {
        return intersectRayPlane(origin.x, origin.y, origin.z, dir.x, dir.y, dir.z, point.x, point.y, point.z, normal.x, normal.y, normal.z, epsilon);
    }

    /**
     * Test whether the ray with given origin <tt>(originX, originY, originZ)</tt> and direction <tt>(dirX, dirY, dirZ)</tt> intersects the plane
     * given as the general plane equation <i>a*x + b*y + c*z + d = 0</i>, and return the
     * value of the parameter <i>t</i> in the ray equation <i>p(t) = origin + t * dir</i> of the intersection point.
     * <p>
     * This method returns <tt>-1.0</tt> if the ray does not intersect the plane, because it is either parallel to the plane or its direction points
     * away from the plane or the ray's origin is on the <i>negative</i> side of the plane (i.e. the plane's normal points away from the ray's origin).
     * <p>
     * Reference: <a href="https://www.siggraph.org/education/materials/HyperGraph/raytrace/rayplane_intersection.htm">https://www.siggraph.org/</a>
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
     * @param a
     *              the x factor in the plane equation
     * @param b
     *              the y factor in the plane equation
     * @param c
     *              the z factor in the plane equation
     * @param d
     *              the constant in the plane equation
     * @param epsilon
     *              some small epsilon for when the ray is parallel to the plane
     * @return the value of the parameter <i>t</i> in the ray equation <i>p(t) = origin + t * dir</i> of the intersection point, if the ray
     *         intersects the plane; <tt>-1.0</tt> otherwise
     */
    public static double intersectRayPlane(double originX, double originY, double originZ, double dirX, double dirY, double dirZ,
            double a, double b, double c, double d, double epsilon) {
        double denom = a * dirX + b * dirY + c * dirZ;
        if (denom < 0.0) {
            double t = -(a * originX + b * originY + c * originZ + d) / denom;
            if (t >= 0.0)
                return t;
        }
        return -1.0;
    }

    /**
     * Test whether the axis-aligned box with minimum corner <tt>(minX, minY, minZ)</tt> and maximum corner <tt>(maxX, maxY, maxZ)</tt>
     * intersects the sphere with the given center <tt>(centerX, centerY, centerZ)</tt> and square radius <code>radiusSquared</code>.
     * <p>
     * Reference: <a href="http://stackoverflow.com/questions/4578967/cube-sphere-intersection-test#answer-4579069">http://stackoverflow.com</a>
     * 
     * @param minX
     *          the x coordinate of the minimum corner of the axis-aligned box
     * @param minY
     *          the y coordinate of the minimum corner of the axis-aligned box
     * @param minZ
     *          the z coordinate of the minimum corner of the axis-aligned box
     * @param maxX
     *          the x coordinate of the maximum corner of the axis-aligned box
     * @param maxY
     *          the y coordinate of the maximum corner of the axis-aligned box
     * @param maxZ
     *          the z coordinate of the maximum corner of the axis-aligned box
     * @param centerX
     *          the x coordinate of the sphere's center
     * @param centerY
     *          the y coordinate of the sphere's center
     * @param centerZ
     *          the z coordinate of the sphere's center
     * @param radiusSquared
     *          the square of the sphere's radius
     * @return <code>true</code> iff the axis-aligned box intersects the sphere; <code>false</code> otherwise
     */
    public static boolean testAabSphere(
            double minX, double minY, double minZ,
            double maxX, double maxY, double maxZ,
            double centerX, double centerY, double centerZ, double radiusSquared) {
        double radius2 = radiusSquared;
        if (centerX < minX) {
            double d = (centerX - minX);
            radius2 -= d * d;
        } else if (centerX > maxX) {
            double d = (centerX - maxX);
            radius2 -= d * d;
        }
        if (centerY < minY) {
            double d = (centerY - minY);
            radius2 -= d * d;
        } else if (centerY > maxY) {
            double d = (centerY - maxY);
            radius2 -= d * d;
        }
        if (centerZ < minZ) {
            double d = (centerZ - minZ);
            radius2 -= d * d;
        } else if (centerZ > maxZ) {
            double d = (centerZ - maxZ);
            radius2 -= d * d;
        }
        return radius2 >= 0.0;
    }

    /**
     * Test whether the axis-aligned box with minimum corner <code>min</code> and maximum corner <code>max</code>
     * intersects the sphere with the given <code>center</code> and square radius <code>radiusSquared</code>.
     * <p>
     * Reference: <a href="http://stackoverflow.com/questions/4578967/cube-sphere-intersection-test#answer-4579069">http://stackoverflow.com</a>
     * 
     * @param min
     *          the minimum corner of the axis-aligned box
     * @param max
     *          the maximum corner of the axis-aligned box
     * @param center
     *          the sphere's center
     * @param radiusSquared
     *          the squared of the sphere's radius
     * @return <code>true</code> iff the axis-aligned box intersects the sphere; <code>false</code> otherwise
     */
    public static boolean testAabSphere(Vector3d min, Vector3d max, Vector3d center, double radiusSquared) {
        return testAabSphere(min.x, min.y, min.z, max.x, max.y, max.z, center.x, center.y, center.z, radiusSquared);
    }

    /**
     * Determine the closest point on the triangle with the given vertices <tt>(v0X, v0Y, v0Z)</tt>, <tt>(v1X, v1Y, v1Z)</tt>, <tt>(v2X, v2Y, v2Z)</tt>
     * between that triangle and the given point <tt>(pX, pY, pZ)</tt> and store that point into the given <code>result</code>.
     * <p>
     * Additionally, this method returns whether the closest point is a {@link #POINT_ON_TRIANGLE_VERTEX vertex} of the triangle, or lies on an
     * {@link #POINT_ON_TRIANGLE_EDGE edge} or on the {@link #POINT_ON_TRIANGLE_FACE face} of the triangle.
     * <p>
     * Reference: Book "Real-Time Collision Detection"
     * 
     * @param v0X
     *          the x coordinate of the first vertex of the triangle
     * @param v0Y
     *          the y coordinate of the first vertex of the triangle
     * @param v0Z
     *          the z coordinate of the first vertex of the triangle
     * @param v1X
     *          the x coordinate of the second vertex of the triangle
     * @param v1Y
     *          the y coordinate of the second vertex of the triangle
     * @param v1Z
     *          the z coordinate of the second vertex of the triangle
     * @param v2X
     *          the x coordinate of the third vertex of the triangle
     * @param v2Y
     *          the y coordinate of the third vertex of the triangle
     * @param v2Z
     *          the z coordinate of the third vertex of the triangle
     * @param pX
     *          the x coordinate of the point
     * @param pY
     *          the y coordinate of the point
     * @param pZ
     *          the y coordinate of the point
     * @param result
     *          will hold the closest point
     * @return one of {@link #POINT_ON_TRIANGLE_VERTEX}, {@link #POINT_ON_TRIANGLE_EDGE} or {@link #POINT_ON_TRIANGLE_FACE}
     */
    public static int findClosestPointOnTriangle(
            double v0X, double v0Y, double v0Z,
            double v1X, double v1Y, double v1Z,
            double v2X, double v2Y, double v2Z,
            double pX, double pY, double pZ,
            Vector3d result) {
        double aX = v0X - pX, aY = v0Y - pY, aZ = v0Z - pZ;
        double bX = v1X - pX, bY = v1Y - pY, bZ = v1Z - pZ;
        double cX = v2X - pX, cY = v2Y - pY, cZ = v2Z - pZ;
        double abX = bX - aX, abY = bY - aY, abZ = bZ - aZ;
        double acX = cX - aX, acY = cY - aY, acZ = cZ - aZ;
        double d1 = -(abX * aX + abY * aY + abZ * aZ);
        double d2 = -(acX * aX + acY * aY + acZ * aZ);
        if (d1 <= 0.0 && d2 <= 0.0) {
            result.set(v0X, v0Y, v0Z);
            return POINT_ON_TRIANGLE_VERTEX;
        }
        double d3 = -(abX * bX + abY * bY + abZ * bZ);
        double d4 = -(acX * bX + acY * bY + acZ * bZ);
        if (d3 >= 0.0 && d4 <= d3) {
            result.set(v1X, v1Y, v1Z);
            return POINT_ON_TRIANGLE_VERTEX;
        }
        double vc = d1 * d4 - d3 * d2;
        if (vc <= 0.0 && d1 >= 0.0 && d3 <= 0.0) {
            double v = d1 / (d1 - d3);
            result.set(v0X + abX * v, v0Y + abY * v, v0Z * abZ * v);
            return POINT_ON_TRIANGLE_EDGE;
        }
        double d5 = -(abX * cX + abY * cY + abZ * cZ);
        double d6 = -(acX * cX + acY * cY + acZ * cZ);
        if (d6 >= 0.0 && d5 <= d6) {
            result.set(v2X, v2Y, v2Z);
            return POINT_ON_TRIANGLE_VERTEX;
        }
        double vb = d5 * d2 - d1 * d6;
        if (vb <= 0.0 && d2 >= 0.0 && d6 <= 0.0) {
            double w = d2 / (d2 - d6);
            result.set(v0X + acX * w, v0Y + acY * w, v0Z + acZ * w);
            return POINT_ON_TRIANGLE_EDGE;
        }
        double va = d3 * d6 - d5 * d4;
        if (va <= 0.0 && (d4 - d3) >= 0.0 && (d5 - d6) >= 0.0) {
            double w = (d4 - d3) / (d4 - d3 + d5 - d6);
            result.set(v1X + (cX - bX) * w, v1Y + (cY - bY) * w, v1Z + (cZ - bZ) * w);
            return POINT_ON_TRIANGLE_EDGE;
        }
        double denom = 1.0 / (va + vb + vc);
        double vn = vb * denom;
        double wn = vc * denom;
        result.set(
            v0X + abX * vn + acX * wn,
            v0Y + abY * vn + acY * wn,
            v0Z + abZ * vn + acZ * wn);
        return POINT_ON_TRIANGLE_FACE;
    }

    /**
     * Determine the closest point on the triangle with the vertices <code>v0</code>, <code>v1</code>, <code>v2</code>
     * between that triangle and the given point <code>p</code> and store that point into the given <code>result</code>.
     * <p>
     * Additionally, this method returns whether the closest point is a {@link #POINT_ON_TRIANGLE_VERTEX vertex} of the triangle, or lies on an
     * {@link #POINT_ON_TRIANGLE_EDGE edge} or on the {@link #POINT_ON_TRIANGLE_FACE face} of the triangle.
     * <p>
     * Reference: Book "Real-Time Collision Detection"
     * 
     * @param v0
     *          the first vertex of the triangle
     * @param v1
     *          the second vertex of the triangle
     * @param v2
     *          the third vertex of the triangle
     * @param p
     *          the point
     * @param result
     *          will hold the closest point
     * @return one of {@link #POINT_ON_TRIANGLE_VERTEX}, {@link #POINT_ON_TRIANGLE_EDGE} or {@link #POINT_ON_TRIANGLE_FACE}
     */
    public static int findClosestPointOnTriangle(Vector3d v0, Vector3d v1, Vector3d v2, Vector3d p, Vector3d result) {
        return findClosestPointOnTriangle(v0.x, v0.y, v0.z, v1.x, v1.y, v1.z, v2.x, v2.y, v2.z, p.x, p.y, p.z, result);
    }

    /**
     * Test whether the given ray with the origin <tt>(originX, originY, originZ)</tt> and normalized direction <tt>(dirX, dirY, dirZ)</tt>
     * intersects the given sphere with center <tt>(centerX, centerY, centerZ)</tt> and square radius <code>radiusSquared</code>,
     * and store the values of the parameter <i>t</i> in the ray equation <i>p(t) = origin + t * dir</i> for both points (near
     * and far) of intersections into the given array <code>result</code>.
     * <p>
     * This method returns <code>true</code> for a ray whose origin lies inside the sphere.
     * <p>
     * Reference: <a href="http://www.scratchapixel.com/lessons/3d-basic-rendering/minimal-ray-tracer-rendering-simple-shapes/ray-sphere-intersection">http://www.scratchapixel.com/</a>
     * 
     * @param originX
     *              the x coordinate of the ray's origin
     * @param originY
     *              the y coordinate of the ray's origin
     * @param originZ
     *              the z coordinate of the ray's origin
     * @param dirX
     *              the x coordinate of the ray's normalized direction
     * @param dirY
     *              the y coordinate of the ray's normalized direction
     * @param dirZ
     *              the z coordinate of the ray's normalized direction
     * @param centerX
     *              the x coordinate of the sphere's center
     * @param centerY
     *              the y coordinate of the sphere's center
     * @param centerZ
     *              the z coordinate of the sphere's center
     * @param radiusSquared
     *              the sphere radius squared
     * @param result
     *              a double[] array that will contain the values of the parameter <i>t</i> in the ray equation
     *              <i>p(t) = origin + t * dir</i> for both points (near and far) of intersections with the sphere
     * @return <code>true</code> if the ray intersects the sphere; <code>false</code> otherwise
     */
    public static boolean intersectRaySphere(double originX, double originY, double originZ, double dirX, double dirY, double dirZ,
            double centerX, double centerY, double centerZ, double radiusSquared, double[] result) {
        double Lx = centerX - originX;
        double Ly = centerY - originY;
        double Lz = centerZ - originZ;
        double tca = Lx * dirX + Ly * dirY + Lz * dirZ;
        double d2 = Lx * Lx + Ly * Ly + Lz * Lz - tca * tca;
        if (d2 > radiusSquared)
            return false;
        double thc = Math.sqrt(radiusSquared - d2);
        double t0 = tca - thc;
        double t1 = tca + thc;
        if (t0 < t1 && t1 >= 0.0) {
            result[0] = t0;
            result[1] = t1;
            return true;
        }
        return false;
    }

    /**
     * Test whether the ray with the given <code>origin</code> and normalized direction <code>dir</code>
     * intersects the sphere with the given <code>center</code> and square radius <code>radiusSquared</code>,
     * and store the values of the parameter <i>t</i> in the ray equation <i>p(t) = origin + t * dir</i> for both points (near
     * and far) of intersections into the given array <code>result</code>.
     * <p>
     * This method returns <code>true</code> for a ray whose origin lies inside the sphere.
     * <p>
     * Reference: <a href="http://www.scratchapixel.com/lessons/3d-basic-rendering/minimal-ray-tracer-rendering-simple-shapes/ray-sphere-intersection">http://www.scratchapixel.com/</a>
     * 
     * @param origin
     *              the ray's origin
     * @param dir
     *              the ray's normalized direction
     * @param center
     *              the sphere's center
     * @param radiusSquared
     *              the sphere radius squared
     * @param result
     *              a double[] array that will contain the values of the parameter <i>t</i> in the ray equation
     *              <i>p(t) = origin + t * dir</i> for both points (near and far) of intersections with the sphere
     * @return <code>true</code> if the ray intersects the sphere; <code>false</code> otherwise
     */
    public static boolean intersectRaySphere(Vector3d origin, Vector3d dir, Vector3d center, double radiusSquared, double[] result) {
        return intersectRaySphere(origin.x, origin.y, origin.z, dir.x, dir.y, dir.z, center.x, center.y, center.z, radiusSquared, result);
    }

    /**
     * Test whether the given ray with the origin <tt>(originX, originY, originZ)</tt> and normalized direction <tt>(dirX, dirY, dirZ)</tt>
     * intersects the given sphere with center <tt>(centerX, centerY, centerZ)</tt> and square radius <code>radiusSquared</code>.
     * <p>
     * This method returns <code>true</code> for a ray whose origin lies inside the sphere.
     * <p>
     * Reference: <a href="http://www.scratchapixel.com/lessons/3d-basic-rendering/minimal-ray-tracer-rendering-simple-shapes/ray-sphere-intersection">http://www.scratchapixel.com/</a>
     * 
     * @param originX
     *              the x coordinate of the ray's origin
     * @param originY
     *              the y coordinate of the ray's origin
     * @param originZ
     *              the z coordinate of the ray's origin
     * @param dirX
     *              the x coordinate of the ray's normalized direction
     * @param dirY
     *              the y coordinate of the ray's normalized direction
     * @param dirZ
     *              the z coordinate of the ray's normalized direction
     * @param centerX
     *              the x coordinate of the sphere's center
     * @param centerY
     *              the y coordinate of the sphere's center
     * @param centerZ
     *              the z coordinate of the sphere's center
     * @param radiusSquared
     *              the sphere radius squared
     * @return <code>true</code> if the ray intersects the sphere; <code>false</code> otherwise
     */
    public static boolean testRaySphere(double originX, double originY, double originZ, double dirX, double dirY, double dirZ,
            double centerX, double centerY, double centerZ, double radiusSquared) {
        double Lx = centerX - originX;
        double Ly = centerY - originY;
        double Lz = centerZ - originZ;
        double tca = Lx * dirX + Ly * dirY + Lz * dirZ;
        double d2 = Lx * Lx + Ly * Ly + Lz * Lz - tca * tca;
        if (d2 > radiusSquared)
            return false;
        double thc = Math.sqrt(radiusSquared - d2);
        double t0 = tca - thc;
        double t1 = tca + thc;
        return t0 < t1 && t1 >= 0.0;
    }

    /**
     * Test whether the ray with the given <code>origin</code> and normalized direction <code>dir</code>
     * intersects the sphere with the given <code>center</code> and square radius.
     * <p>
     * This method returns <code>true</code> for a ray whose origin lies inside the sphere.
     * <p>
     * Reference: <a href="http://www.scratchapixel.com/lessons/3d-basic-rendering/minimal-ray-tracer-rendering-simple-shapes/ray-sphere-intersection">http://www.scratchapixel.com/</a>
     * 
     * @param origin
     *              the ray's origin
     * @param dir
     *              the ray's normalized direction
     * @param center
     *              the sphere's center
     * @param radiusSquared
     *              the sphere radius squared
     * @return <code>true</code> if the ray intersects the sphere; <code>false</code> otherwise
     */
    public static boolean testRaySphere(Vector3d origin, Vector3d dir, Vector3d center, double radiusSquared) {
        return testRaySphere(origin.x, origin.y, origin.z, dir.x, dir.y, dir.z, center.x, center.y, center.z, radiusSquared);
    }

    /**
     * Test whether the given line segment with the end points <tt>(p0X, p0Y, p0Z)</tt> and <tt>(p1X, p1Y, p1Z)</tt>
     * intersects the given sphere with center <tt>(centerX, centerY, centerZ)</tt> and square radius <code>radiusSquared</code>.
     * <p>
     * Reference: <a href="http://paulbourke.net/geometry/circlesphere/index.html#linesphere">http://paulbourke.net/</a>
     * 
     * @param p0X
     *              the x coordinate of the line segment's first end point
     * @param p0Y
     *              the y coordinate of the line segment's first end point
     * @param p0Z
     *              the z coordinate of the line segment's first end point
     * @param p1X
     *              the x coordinate of the line segment's second end point
     * @param p1Y
     *              the y coordinate of the line segment's second end point
     * @param p1Z
     *              the z coordinate of the line segment's second end point
     * @param centerX
     *              the x coordinate of the sphere's center
     * @param centerY
     *              the y coordinate of the sphere's center
     * @param centerZ
     *              the z coordinate of the sphere's center
     * @param radiusSquared
     *              the sphere radius squared
     * @return <code>true</code> if the line segment intersects the sphere; <code>false</code> otherwise
     */
    public static boolean testLineSegmentSphere(double p0X, double p0Y, double p0Z, double p1X, double p1Y, double p1Z,
            double centerX, double centerY, double centerZ, double radiusSquared) {
        double dX = p1X - p0X;
        double dY = p1Y - p0Y;
        double dZ = p1Z - p0Z;
        double nom = (centerX - p0X) * dX + (centerY - p0Y) * dY + (centerZ - p0Z) * dZ;
        double den = dX * dX + dY * dY + dZ * dZ;
        double u = nom / den;
        if (u < 0.0) {
            dX = p0X - centerX;
            dY = p0Y - centerY;
            dZ = p0Z - centerZ;
        } else if (u > 1.0) {
            dX = p1X - centerX;
            dY = p1Y - centerY;
            dZ = p1Z - centerZ;
        } else { // has to be >= 0 and <= 1
            double pX = p0X + u * dX;
            double pY = p0Y + u * dY;
            double pZ = p0Z + u * dZ;
            dX = pX - centerX;
            dY = pY - centerY;
            dZ = pZ - centerZ;
        }
        double dist = dX * dX + dY * dY + dZ * dZ;
        return dist <= radiusSquared;
    }

    /**
     * Test whether the given line segment with the end points <code>p0</code> and <code>p1</code>
     * intersects the given sphere with center <code>center</code> and square radius <code>radiusSquared</code>.
     * <p>
     * Reference: <a href="http://paulbourke.net/geometry/circlesphere/index.html#linesphere">http://paulbourke.net/</a>
     * 
     * @param p0
     *              the line segment's first end point
     * @param p1
     *              the line segment's second end point
     * @param center
     *              the sphere's center
     * @param radiusSquared
     *              the sphere radius squared
     * @return <code>true</code> if the line segment intersects the sphere; <code>false</code> otherwise
     */
    public static boolean testLineSegmentSphere(Vector3d p0, Vector3d p1, Vector3d center, double radiusSquared) {
        return testLineSegmentSphere(p0.x, p0.y, p0.z, p1.x, p1.y, p1.z, center.x, center.y, center.z, radiusSquared);
    }

    /**
     * Test whether the given ray with the origin <tt>(originX, originY, originZ)</tt> and direction <tt>(dirX, dirY, dirZ)</tt>
     * intersects the given axis-aligned box given as any two opposite corners <tt>(aX, aY, aZ)</tt> and <tt>(bX, bY, bZ)</tt>,
     * and return the values of the parameter <i>t</i> in the ray equation <i>p(t) = origin + t * dir</i> of the near and far point of intersection.
     * <p>
     * This is an implementation of the <a href="http://www.siggraph.org/education/materials/HyperGraph/raytrace/rtinter3.htm">
     * Ray - Box Intersection</a> method, also known as "slab method."
     * <p>
     * This method returns <code>true</code> for a ray whose origin lies inside the axis-aligned box.
     * <p>
     * If many boxes need to be tested against the same ray, then the {@link RayAabIntersection} class is likely more efficient.
     * 
     * @see #intersectRayAab(Vector3d, Vector3d, Vector3d, Vector3d, double[])
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
     * @param result
     *              the double[] array which will hold the resulting values of the parameter
     *              <i>t</i> in the ray equation <i>p(t) = origin + t * dir</i> of the near and far point of intersection
     *              iff the ray intersects the axis-aligned box
     * @return <code>true</code> if the given ray intersects the axis-aligned box; <code>false</code> otherwise
     */
    public static boolean intersectRayAab(double originX, double originY, double originZ, double dirX, double dirY, double dirZ,
            double aX, double aY, double aZ, double bX, double bY, double bZ, double[] result) {
        double invDirX = 1.0 / dirX, invDirY = 1.0 / dirY, invDirZ = 1.0 / dirZ;
        double tMinX = (aX - originX) * invDirX;
        double tMinY = (aY - originY) * invDirY;
        double tMinZ = (aZ - originZ) * invDirZ;
        double tMaxX = (bX - originX) * invDirX;
        double tMaxY = (bY - originY) * invDirY;
        double tMaxZ = (bZ - originZ) * invDirZ;
        double t1X = tMinX < tMaxX ? tMinX : tMaxX;
        double t1Y = tMinY < tMaxY ? tMinY : tMaxY;
        double t1Z = tMinZ < tMaxZ ? tMinZ : tMaxZ;
        double t2X = tMinX > tMaxX ? tMinX : tMaxX;
        double t2Y = tMinY > tMaxY ? tMinY : tMaxY;
        double t2Z = tMinZ > tMaxZ ? tMinZ : tMaxZ;
        double t1XY = t1X > t1Y ? t1X : t1Y;
        double tNear = t1XY > t1Z ? t1XY : t1Z;
        double t2XY = t2X < t2Y ? t2X : t2Y;
        double tFar = t2XY < t2Z ? t2XY : t2Z;
        if (tNear < tFar && tFar >= 0.0) {
            result[0] = tNear;
            result[1] = tFar;
            return true;
        }
        return false;
    }

    /**
     * Test whether the ray with the given <code>origin</code> and direction <code>dir</code>
     * intersects the given axis-aligned box specified as any two opposite corners <code>a</code> and <code>b</code>,
     * and return the values of the parameter <i>t</i> in the ray equation <i>p(t) = origin + t * dir</i> of the near and far point of intersection..
     * <p>
     * This is an implementation of the <a href="http://www.siggraph.org/education/materials/HyperGraph/raytrace/rtinter3.htm">
     * Ray - Box Intersection</a> method, also known as "slab method."
     * <p>
     * This method returns <code>true</code> for a ray whose origin lies inside the axis-aligned box.
     * <p>
     * If many boxes need to be tested against the same ray, then the {@link RayAabIntersection} class is likely more efficient.
     * 
     * @see #intersectRayAab(double, double, double, double, double, double, double, double, double, double, double, double, double[])
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
     * @param result
     *              the double[] array which will hold the resulting values of the parameter
     *              <i>t</i> in the ray equation <i>p(t) = origin + t * dir</i> of the near and far point of intersection
     *              iff the ray intersects the axis-aligned box
     * @return <code>true</code> if the given ray intersects the axis-aligned box; <code>false</code> otherwise
     */
    public static boolean intersectRayAab(Vector3d origin, Vector3d dir, Vector3d a, Vector3d b, double[] result) {
        return intersectRayAab(origin.x, origin.y, origin.z, dir.x, dir.y, dir.z, a.x, a.y, a.z, b.x, b.y, b.z, result);
    }

    /**
     * Test whether the given ray with the origin <tt>(originX, originY, originZ)</tt> and direction <tt>(dirX, dirY, dirZ)</tt>
     * intersects the given axis-aligned box given as any two opposite corners <tt>(aX, aY, aZ)</tt> and <tt>(bX, bY, bZ)</tt>.
     * <p>
     * This is an implementation of the <a href="http://www.siggraph.org/education/materials/HyperGraph/raytrace/rtinter3.htm">
     * Ray - Box Intersection</a> method, also known as "slab method."
     * <p>
     * This method returns <code>true</code> for a ray whose origin lies inside the axis-aligned box.
     * <p>
     * If many boxes need to be tested against the same ray, then the {@link RayAabIntersection} class is likely more efficient.
     * 
     * @see #testRayAab(Vector3d, Vector3d, Vector3d, Vector3d)
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
     * @return <code>true</code> if the given ray intersects the axis-aligned box; <code>false</code> otherwise
     */
    public static boolean testRayAab(double originX, double originY, double originZ, double dirX, double dirY, double dirZ,
            double aX, double aY, double aZ, double bX, double bY, double bZ) {
        double invDirX = 1.0 / dirX, invDirY = 1.0 / dirY, invDirZ = 1.0 / dirZ;
        double tMinX = (aX - originX) * invDirX;
        double tMinY = (aY - originY) * invDirY;
        double tMinZ = (aZ - originZ) * invDirZ;
        double tMaxX = (bX - originX) * invDirX;
        double tMaxY = (bY - originY) * invDirY;
        double tMaxZ = (bZ - originZ) * invDirZ;
        double t1X = tMinX < tMaxX ? tMinX : tMaxX;
        double t1Y = tMinY < tMaxY ? tMinY : tMaxY;
        double t1Z = tMinZ < tMaxZ ? tMinZ : tMaxZ;
        double t2X = tMinX > tMaxX ? tMinX : tMaxX;
        double t2Y = tMinY > tMaxY ? tMinY : tMaxY;
        double t2Z = tMinZ > tMaxZ ? tMinZ : tMaxZ;
        double t1XY = t1X > t1Y ? t1X : t1Y;
        double tNear = t1XY > t1Z ? t1XY : t1Z;
        double t2XY = t2X < t2Y ? t2X : t2Y;
        double tFar = t2XY < t2Z ? t2XY : t2Z;
        return tNear < tFar && tFar >= 0.0;
    }

    /**
     * Test whether the ray with the given <code>origin</code> and direction <code>dir</code>
     * intersects the given axis-aligned box specified as any two opposite corners <code>a</code> and <code>b</code>.
     * <p>
     * This is an implementation of the <a href="http://www.siggraph.org/education/materials/HyperGraph/raytrace/rtinter3.htm">
     * Ray - Box Intersection</a> method, also known as "slab method."
     * <p>
     * This method returns <code>true</code> for a ray whose origin lies inside the axis-aligned box.
     * <p>
     * If many boxes need to be tested against the same ray, then the {@link RayAabIntersection} class is likely more efficient.
     * 
     * @see #testRayAab(double, double, double, double, double, double, double, double, double, double, double, double)
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
     * @return <code>true</code> if the given ray intersects the axis-aligned box; <code>false</code> otherwise
     */
    public static boolean testRayAab(Vector3d origin, Vector3d dir, Vector3d a, Vector3d b) {
        return testRayAab(origin.x, origin.y, origin.z, dir.x, dir.y, dir.z, a.x, a.y, a.z, b.x, b.y, b.z);
    }

    /**
     * Test whether the given ray with the origin <tt>(originX, originY, originZ)</tt> and direction <tt>(dirX, dirY, dirZ)</tt>
     * intersects the frontface of the triangle consisting of the three vertices <tt>(v0X, v0Y, v0Z)</tt>, <tt>(v1X, v1Y, v1Z)</tt> and <tt>(v2X, v2Y, v2Z)</tt>.
     * <p>
     * This is an implementation of the <a href="http://www.cs.virginia.edu/~gfx/Courses/2003/ImageSynthesis/papers/Acceleration/Fast%20MinimumStorage%20RayTriangle%20Intersection.pdf">
     * Fast, Minimum Storage Ray/Triangle Intersection</a> method.
     * <p>
     * This test implements backface culling, that is, it will return <code>false</code> when the triangle is in clockwise
     * winding order assuming a <i>right-handed</i> coordinate system when seen along the ray's direction, even if the ray intersects the triangle.
     * This is in compliance with how OpenGL handles backface culling with default frontface/backface settings.
     * 
     * @see #testRayTriangle(Vector3d, Vector3d, Vector3d, Vector3d, Vector3d, double)
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
     * @return <code>true</code> if the given ray intersects with the frontface of the triangle; <code>false</code> otherwise
     */
    public static boolean testRayTriangle(double originX, double originY, double originZ, double dirX, double dirY, double dirZ,
            double v0X, double v0Y, double v0Z, double v1X, double v1Y, double v1Z, double v2X, double v2Y, double v2Z,
            double epsilon) {
        double edge1X = v1X - v0X;
        double edge1Y = v1Y - v0Y;
        double edge1Z = v1Z - v0Z;
        double edge2X = v2X - v0X;
        double edge2Y = v2Y - v0Y;
        double edge2Z = v2Z - v0Z;
        double pvecX = dirY * edge2Z - dirZ * edge2Y;
        double pvecY = dirZ * edge2X - dirX * edge2Z;
        double pvecZ = dirX * edge2Y - dirY * edge2X;
        double det = edge1X * pvecX + edge1Y * pvecY + edge1Z * pvecZ;
        if (det <= epsilon)
            return false;
        double tvecX = originX - v0X;
        double tvecY = originY - v0Y;
        double tvecZ = originZ - v0Z;
        double u = tvecX * pvecX + tvecY * pvecY + tvecZ * pvecZ;
        if (u < 0.0 || u > det)
            return false;
        double qvecX = tvecY * edge1Z - tvecZ * edge1Y;
        double qvecY = tvecZ * edge1X - tvecX * edge1Z;
        double qvecZ = tvecX * edge1Y - tvecY * edge1X;
        double v = dirX * qvecX + dirY * qvecY + dirZ * qvecZ;
        if (v < 0.0 || u + v > det)
            return false;
        return true;
    }

    /**
     * Test whether the ray with the given <code>origin</code> and the given <code>dir</code> intersects the frontface of the triangle consisting of the three vertices
     * <code>v0</code>, <code>v1</code> and <code>v2</code>.
     * <p>
     * This is an implementation of the <a href="http://www.cs.virginia.edu/~gfx/Courses/2003/ImageSynthesis/papers/Acceleration/Fast%20MinimumStorage%20RayTriangle%20Intersection.pdf">
     * Fast, Minimum Storage Ray/Triangle Intersection</a> method.
     * <p>
     * This test implements backface culling, that is, it will return <code>false</code> when the triangle is in clockwise
     * winding order assuming a <i>right-handed</i> coordinate system when seen along the ray's direction, even if the ray intersects the triangle.
     * This is in compliance with how OpenGL handles backface culling with default frontface/backface settings.
     * 
     * @see #testRayTriangle(double, double, double, double, double, double, double, double, double, double, double, double, double, double, double, double)
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
     * @return <code>true</code> if the given ray intersects with the frontface of the triangle; <code>false</code> otherwise
     */
    public static boolean testRayTriangle(Vector3d origin, Vector3d dir, Vector3d v0, Vector3d v1, Vector3d v2, double epsilon) {
        return testRayTriangle(origin.x, origin.y, origin.z, dir.x, dir.y, dir.z, v0.x, v0.y, v0.z, v1.x, v1.y, v1.z, v2.x, v2.y, v2.z, epsilon);
    }

    /**
     * Test whether the given ray with the origin <tt>(originX, originY, originZ)</tt> and direction <tt>(dirX, dirY, dirZ)</tt>
     * intersects the frontface of the triangle consisting of the three vertices <tt>(v0X, v0Y, v0Z)</tt>, <tt>(v1X, v1Y, v1Z)</tt> and <tt>(v2X, v2Y, v2Z)</tt>
     * and return the value of the parameter <i>t</i> in the ray equation <i>p(t) = origin + t * dir</i> of the point of intersection.
     * <p>
     * This is an implementation of the <a href="http://www.cs.virginia.edu/~gfx/Courses/2003/ImageSynthesis/papers/Acceleration/Fast%20MinimumStorage%20RayTriangle%20Intersection.pdf">
     * Fast, Minimum Storage Ray/Triangle Intersection</a> method.
     * <p>
     * This test implements backface culling, that is, it will return <code>false</code> when the triangle is in clockwise
     * winding order assuming a <i>right-handed</i> coordinate system when seen along the ray's direction, even if the ray intersects the triangle.
     * This is in compliance with how OpenGL handles backface culling with default frontface/backface settings.
     * 
     * @see #testRayTriangle(Vector3d, Vector3d, Vector3d, Vector3d, Vector3d, double)
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
    public static double intersectRayTriangle(double originX, double originY, double originZ, double dirX, double dirY, double dirZ,
            double v0X, double v0Y, double v0Z, double v1X, double v1Y, double v1Z, double v2X, double v2Y, double v2Z,
            double epsilon) {
        double edge1X = v1X - v0X;
        double edge1Y = v1Y - v0Y;
        double edge1Z = v1Z - v0Z;
        double edge2X = v2X - v0X;
        double edge2Y = v2Y - v0Y;
        double edge2Z = v2Z - v0Z;
        double pvecX = dirY * edge2Z - dirZ * edge2Y;
        double pvecY = dirZ * edge2X - dirX * edge2Z;
        double pvecZ = dirX * edge2Y - dirY * edge2X;
        double det = edge1X * pvecX + edge1Y * pvecY + edge1Z * pvecZ;
        if (det <= epsilon)
            return -1.0;
        double tvecX = originX - v0X;
        double tvecY = originY - v0Y;
        double tvecZ = originZ - v0Z;
        double u = tvecX * pvecX + tvecY * pvecY + tvecZ * pvecZ;
        if (u < 0.0 || u > det)
            return -1.0;
        double qvecX = tvecY * edge1Z - tvecZ * edge1Y;
        double qvecY = tvecZ * edge1X - tvecX * edge1Z;
        double qvecZ = tvecX * edge1Y - tvecY * edge1X;
        double v = dirX * qvecX + dirY * qvecY + dirZ * qvecZ;
        if (v < 0.0 || u + v > det)
            return -1.0;
        double invDet = 1.0 / det;
        double t = (edge2X * qvecX + edge2Y * qvecY + edge2Z * qvecZ) * invDet;
        return t;
    }

    /**
     * Test whether the ray with the given <code>origin</code> and the given <code>dir</code> intersects the frontface of the triangle consisting of the three vertices
     * <code>v0</code>, <code>v1</code> and <code>v2</code> and return the value of the parameter <i>t</i> in the ray equation <i>p(t) = origin + t * dir</i> of the point of intersection.
     * <p>
     * This is an implementation of the <a href="http://www.cs.virginia.edu/~gfx/Courses/2003/ImageSynthesis/papers/Acceleration/Fast%20MinimumStorage%20RayTriangle%20Intersection.pdf">
     * Fast, Minimum Storage Ray/Triangle Intersection</a> method.
     * <p>
     * This test implements backface culling, that is, it will return <code>false</code> when the triangle is in clockwise
     * winding order assuming a <i>right-handed</i> coordinate system when seen along the ray's direction, even if the ray intersects the triangle.
     * This is in compliance with how OpenGL handles backface culling with default frontface/backface settings.
     * 
     * @see #intersectRayTriangle(double, double, double, double, double, double, double, double, double, double, double, double, double, double, double, double)
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
    public static double intersectRayTriangle(Vector3d origin, Vector3d dir, Vector3d v0, Vector3d v1, Vector3d v2, double epsilon) {
        return intersectRayTriangle(origin.x, origin.y, origin.z, dir.x, dir.y, dir.z, v0.x, v0.y, v0.z, v1.x, v1.y, v1.z, v2.x, v2.y, v2.z, epsilon);
    }
    

    /**
     * Test whether the line with the general line equation <i>a*x + b*y + c = 0</i> intersects the circle with center
     * <tt>(centerX, centerY)</tt> and <code>radius</code>.
     * <p>
     * Reference: <a href="http://math.stackexchange.com/questions/943383/determine-circle-of-intersection-of-plane-and-sphere">http://math.stackexchange.com</a>
     *
     * @param a
     *          the x factor in the line equation
     * @param b
     *          the y factor in the line equation
     * @param c
     *          the constant in the line equation
     * @param centerX
     *          the x coordinate of the circle's center
     * @param centerY
     *          the y coordinate of the circle's center
     * @param radius
     *          the radius of the circle
     * @return <code>true</code> iff the line intersects the circle; <code>false</code> otherwise
     */
    public static boolean testLineCircle(double a, double b, double c, double centerX, double centerY, double radius) {
        double denom = Math.sqrt(a * a + b * b);
        double dist = (a * centerX + b * centerY + c) / denom;
        return -radius <= dist && dist <= radius;
    }

    /**
     * Test whether the line with the general line equation <i>a*x + b*y + c = 0</i> intersects the circle with center
     * <tt>(centerX, centerY)</tt> and <code>radius</code>, and store the center of the line segment of
     * intersection in the <tt>(x, y)</tt> components of the supplied vector and the half-length of that line segment in the z component.
     * <p>
     * Reference: <a href="http://math.stackexchange.com/questions/943383/determine-circle-of-intersection-of-plane-and-sphere">http://math.stackexchange.com</a>
     *
     * @param a
     *          the x factor in the line equation
     * @param b
     *          the y factor in the line equation
     * @param c
     *          the constant in the line equation
     * @param centerX
     *          the x coordinate of the circle's center
     * @param centerY
     *          the y coordinate of the circle's center
     * @param radius
     *          the radius of the circle
     * @param intersectionCenterAndHL
     *          will hold the center of the line segment of intersection in the <tt>(x, y)</tt> components and the half-length in the z component
     * @return <code>true</code> iff the line intersects the circle; <code>false</code> otherwise
     */
    public static boolean intersectLineCircle(double a, double b, double c, double centerX, double centerY, double radius, Vector3d intersectionCenterAndHL) {
        double invDenom = 1.0 / Math.sqrt(a * a + b * b);
        double dist = (a * centerX + b * centerY + c) * invDenom;
        if (-radius <= dist && dist <= radius) {
            intersectionCenterAndHL.x = centerX + dist * a * invDenom;
            intersectionCenterAndHL.y = centerY + dist * b * invDenom;
            intersectionCenterAndHL.z = Math.sqrt(radius * radius - dist * dist);
            return true;
        }
        return false;
    }

    /**
     * Test whether the line defined by the two points <tt>(x0, y0)</tt> and <tt>(x1, y1)</tt> intersects the circle with center
     * <tt>(centerX, centerY)</tt> and <code>radius</code>, and store the center of the line segment of
     * intersection in the <tt>(x, y)</tt> components of the supplied vector and the half-length of that line segment in the z component.
     * <p>
     * Reference: <a href="http://math.stackexchange.com/questions/943383/determine-circle-of-intersection-of-plane-and-sphere">http://math.stackexchange.com</a>
     *
     * @param x0
     *          the x coordinate of the first point on the line
     * @param y0
     *          the y coordinate of the first point on the line
     * @param x1
     *          the x coordinate of the second point on the line
     * @param y1
     *          the y coordinate of the second point on the line
     * @param centerX
     *          the x coordinate of the circle's center
     * @param centerY
     *          the y coordinate of the circle's center
     * @param radius
     *          the radius of the circle
     * @param intersectionCenterAndHL
     *          will hold the center of the line segment of intersection in the <tt>(x, y)</tt> components and the half-length in the z component
     * @return <code>true</code> iff the line intersects the circle; <code>false</code> otherwise
     */
    public static boolean intersectLineCircle(double x0, double y0, double x1, double y1, double centerX, double centerY, double radius, Vector3d intersectionCenterAndHL) {
        // Build general line equation from two points and use the other method
        return intersectLineCircle(y0 - y1, x1 - x0, (x0 - x1) * y0 + (y1 - y0) * x0, centerX, centerY, radius, intersectionCenterAndHL);
    }

    /**
     * Test whether the axis-aligned rectangle with minimum corner <tt>(minX, minY)</tt> and maximum corner <tt>(maxX, maxY)</tt>
     * intersects the line with the general equation <i>a*x + b*y + c = 0</i>.
     * <p>
     * Reference: <a href="http://zach.in.tu-clausthal.de/teaching/cg_literatur/lighthouse3d_view_frustum_culling/index.html">http://zach.in.tu-clausthal.de</a> ("Geometric Approach - Testing Boxes II")
     * 
     * @param minX
     *          the x coordinate of the minimum corner of the axis-aligned rectangle
     * @param minY
     *          the y coordinate of the minimum corner of the axis-aligned rectangle
     * @param maxX
     *          the x coordinate of the maximum corner of the axis-aligned rectangle
     * @param maxY
     *          the y coordinate of the maximum corner of the axis-aligned rectangle
     * @param a
     *          the x factor in the line equation
     * @param b
     *          the y factor in the line equation
     * @param c
     *          the constant in the plane equation
     * @return <code>true</code> iff the axis-aligned rectangle intersects the line; <code>false</code> otherwise
     */
    public static boolean testAarLine(double minX, double minY, double maxX, double maxY, double a, double b, double c) {
        double pX, pY, nX, nY;
        if (a > 0.0) {
            pX = maxX;
            nX = minX;
        } else {
            pX = minX;
            nX = maxX;
        }
        if (b > 0.0) {
            pY = maxY;
            nY = minY;
        } else {
            pY = minY;
            nY = maxY;
        }
        double distN = c + a * nX + b * nY;
        double distP = c + a * pX + b * pY;
        return distN <= 0.0 && distP >= 0.0;
    }

    /**
     * Test whether the axis-aligned rectangle with minimum corner <code>min</code> and maximum corner <code>max</code>
     * intersects the line with the general equation <i>a*x + b*y + c = 0</i>.
     * <p>
     * Reference: <a href="http://zach.in.tu-clausthal.de/teaching/cg_literatur/lighthouse3d_view_frustum_culling/index.html">http://zach.in.tu-clausthal.de</a> ("Geometric Approach - Testing Boxes II")
     * 
     * @param min
     *          the minimum corner of the axis-aligned rectangle
     * @param max
     *          the maximum corner of the axis-aligned rectangle
     * @param a
     *          the x factor in the line equation
     * @param b
     *          the y factor in the line equation
     * @param c
     *          the constant in the line equation
     * @return <code>true</code> iff the axis-aligned rectangle intersects the line; <code>false</code> otherwise
     */
    public static boolean testAarLine(Vector2d min, Vector2d max, double a, double b, double c) {
        return testAarLine(min.x, min.y, max.x, max.y, a, b, c);
    }

    /**
     * Test whether the axis-aligned rectangle with minimum corner <tt>(minX, minY)</tt> and maximum corner <tt>(maxX, maxY)</tt>
     * intersects the line defined by the two points <tt>(x0, y0)</tt> and <tt>(x1, y1)</tt>.
     * <p>
     * Reference: <a href="http://zach.in.tu-clausthal.de/teaching/cg_literatur/lighthouse3d_view_frustum_culling/index.html">http://zach.in.tu-clausthal.de</a> ("Geometric Approach - Testing Boxes II")
     * 
     * @param minX
     *          the x coordinate of the minimum corner of the axis-aligned rectangle
     * @param minY
     *          the y coordinate of the minimum corner of the axis-aligned rectangle
     * @param maxX
     *          the x coordinate of the maximum corner of the axis-aligned rectangle
     * @param maxY
     *          the y coordinate of the maximum corner of the axis-aligned rectangle
     * @param x0
     *          the x coordinate of the first point on the line
     * @param y0
     *          the y coordinate of the first point on the line
     * @param x1
     *          the x coordinate of the second point on the line
     * @param y1
     *          the y coordinate of the second point on the line
     * @return <code>true</code> iff the axis-aligned rectangle intersects the line; <code>false</code> otherwise
     */
    public static boolean testAarLine(double minX, double minY, double maxX, double maxY, double x0, double y0, double x1, double y1) {
        // Build general line equation from two points and use the other method
        return testAarLine(minX, minY, maxX, maxY, y0 - y1, x1 - x0, (x0 - x1) * y0 + (y1 - y0) * x0);
    }

    /**
     * Test whether the axis-aligned rectangle with minimum corner <tt>(minXA, minYA)</tt> and maximum corner <tt>(maxXA, maxYA)</tt>
     * intersects the axis-aligned rectangle with minimum corner <tt>(minXB, minYB)</tt> and maximum corner <tt>(maxXB, maxYB)</tt>.
     * 
     * @param minXA
     *              the x coordinate of the minimum corner of the first axis-aligned rectangle
     * @param minYA
     *              the y coordinate of the minimum corner of the first axis-aligned rectangle
     * @param maxXA
     *              the x coordinate of the maximum corner of the first axis-aligned rectangle
     * @param maxYA
     *              the y coordinate of the maximum corner of the first axis-aligned rectangle
     * @param minXB
     *              the x coordinate of the minimum corner of the second axis-aligned rectangle
     * @param minYB
     *              the y coordinate of the minimum corner of the second axis-aligned rectangle
     * @param maxXB
     *              the x coordinate of the maximum corner of the second axis-aligned rectangle
     * @param maxYB
     *              the y coordinate of the maximum corner of the second axis-aligned rectangle
     * @return <code>true</code> iff both axis-aligned rectangles intersect; <code>false</code> otherwise
     */
    public static boolean testAarAar(double minXA, double minYA, double maxXA, double maxYA, double minXB, double minYB, double maxXB, double maxYB) {
        return maxXA >= minXB && maxYA >= minYB &&  minXA <= maxXB && minYA <= maxYB;
    }

    /**
     * Test whether the axis-aligned rectangle with minimum corner <code>minA</code> and maximum corner <code>maxA</code>
     * intersects the axis-aligned rectangle with minimum corner <code>minB</code> and maximum corner <code>maxB</code>.
     * 
     * @param minA
     *              the minimum corner of the first axis-aligned rectangle
     * @param maxA
     *              the maximum corner of the first axis-aligned rectangle
     * @param minB
     *              the minimum corner of the second axis-aligned rectangle
     * @param maxB
     *              the maximum corner of the second axis-aligned rectangle
     * @return <code>true</code> iff both axis-aligned rectangles intersect; <code>false</code> otherwise
     */
    public static boolean testAarAar(Vector2d minA, Vector2d maxA, Vector2d minB, Vector2d maxB) {
        return testAarAar(minA.x, minA.y, maxA.x, maxA.y, minB.x, minB.y, maxB.x, maxB.y);
    }

    /**
     * Test whether the one circle with center <tt>(aX, aY)</tt> and square radius <code>radiusSquaredA</code> intersects the other
     * circle with center <tt>(bX, bY)</tt> and square radius <code>radiusSquaredB</code>, and store the center of the line segment of
     * intersection in the <tt>(x, y)</tt> components of the supplied vector and the half-length of that line segment in the z component.
     * <p>
     * Reference: <a href="http://gamedev.stackexchange.com/questions/75756/sphere-sphere-intersection-and-circle-sphere-intersection">http://gamedev.stackexchange.com</a>
     * 
     * @param aX
     *              the x coordinate of the first circle's center
     * @param aY
     *              the y coordinate of the first circle's center
     * @param radiusSquaredA
     *              the square of the first circle's radius
     * @param bX
     *              the x coordinate of the second circle's center
     * @param bY
     *              the y coordinate of the second circle's center
     * @param radiusSquaredB
     *              the square of the second circle's radius
     * @param intersectionCenterAndHL
     *              will hold the center of the circle of intersection in the <tt>(x, y, z)</tt> components and the radius in the w component
     * @return <code>true</code> iff both circles intersect; <code>false</code> otherwise
     */
    public static boolean intersectCircleCircle(double aX, double aY, double radiusSquaredA, double bX, double bY, double radiusSquaredB, Vector3d intersectionCenterAndHL) {
        double dX = bX - aX, dY = bY - aY;
        double distSquared = dX * dX + dY * dY;
        double h = 0.5 + (radiusSquaredA - radiusSquaredB) / distSquared;
        double r_i = Math.sqrt(radiusSquaredA - h * h * distSquared);
        if (r_i >= 0.0) {
            intersectionCenterAndHL.x = aX + h * dX;
            intersectionCenterAndHL.y = aY + h * dY;
            intersectionCenterAndHL.z = r_i;
            return true;
        }
        return false;
    }

    /**
     * Test whether the one circle with center <code>centerA</code> and square radius <code>radiusSquaredA</code> intersects the other
     * circle with center <code>centerB</code> and square radius <code>radiusSquaredB</code>, and store the center of the line segment of
     * intersection in the <tt>(x, y)</tt> components of the supplied vector and the half-length of that line segment in the z component.
     * <p>
     * Reference: <a href="http://gamedev.stackexchange.com/questions/75756/sphere-sphere-intersection-and-circle-sphere-intersection">http://gamedev.stackexchange.com</a>
     * 
     * @param centerA
     *              the first circle's center
     * @param radiusSquaredA
     *              the square of the first circle's radius
     * @param centerB
     *              the second circle's center
     * @param radiusSquaredB
     *              the square of the second circle's radius
     * @param intersectionCenterAndHL
     *              will hold the center of the line segment of intersection in the <tt>(x, y)</tt> components and the half-length in the z component
     * @return <code>true</code> iff both circles intersect; <code>false</code> otherwise
     */
    public static boolean intersectCircleCircle(Vector2d centerA, double radiusSquaredA, Vector2d centerB, double radiusSquaredB, Vector3d intersectionCenterAndHL) {
        return intersectCircleCircle(centerA.x, centerA.y, radiusSquaredB, centerB.x, centerB.y, radiusSquaredB, intersectionCenterAndHL);
    }

    /**
     * Test whether the one circle with center <tt>(aX, aY)</tt> and square radius <code>radiusSquaredA</code> intersects the other
     * circle with center <tt>(bX, bY)</tt> and square radius <code>radiusSquaredB</code>.
     * <p>
     * Reference: <a href="http://gamedev.stackexchange.com/questions/75756/sphere-sphere-intersection-and-circle-sphere-intersection">http://gamedev.stackexchange.com</a>
     * 
     * @param aX
     *              the x coordinate of the first circle's center
     * @param aY
     *              the y coordinate of the first circle's center
     * @param radiusSquaredA
     *              the square of the first circle's radius
     * @param bX
     *              the x coordinate of the second circle's center
     * @param bY
     *              the y coordinate of the second circle's center
     * @param radiusSquaredB
     *              the square of the second circle's radius
     * @return <code>true</code> iff both circles intersect; <code>false</code> otherwise
     */
    public static boolean testCircleCircle(double aX, double aY, double radiusSquaredA, double bX, double bY, double radiusSquaredB) {
        double dX = bX - aX, dY = bY - aY;
        double distSquared = dX * dX + dY * dY;
        double h = 0.5 + (radiusSquaredA - radiusSquaredB) / distSquared;
        double r_i = radiusSquaredA - h * h * distSquared;
        return r_i >= 0.0;
    }

    /**
     * Test whether the one circle with center <code>centerA</code> and square radius <code>radiusSquaredA</code> intersects the other
     * circle with center <code>centerB</code> and square radius <code>radiusSquaredB</code>.
     * <p>
     * Reference: <a href="http://gamedev.stackexchange.com/questions/75756/sphere-sphere-intersection-and-circle-sphere-intersection">http://gamedev.stackexchange.com</a>
     * 
     * @param centerA
     *              the first circle's center
     * @param radiusSquaredA
     *              the square of the first circle's radius
     * @param centerB
     *              the second circle's center
     * @param radiusSquaredB
     *              the square of the second circle's radius
     * @return <code>true</code> iff both circles intersect; <code>false</code> otherwise
     */
    public static boolean testCircleCircle(Vector2d centerA, double radiusSquaredA, Vector2d centerB, double radiusSquaredB) {
        return testCircleCircle(centerA.x, centerA.y, radiusSquaredA, centerB.x, centerB.y, radiusSquaredB);
    }

    /**
     * Determine the signed distance of the given point <tt>(pointX, pointY)</tt> to the line specified via its general plane equation
     * <i>a*x + b*y + c = 0</i>.
     * <p>
     * Reference: <a href="http://mathworld.wolfram.com/Point-LineDistance2-Dimensional.html">http://mathworld.wolfram.com</a>
     * 
     * @param pointX
     *              the x coordinate of the point
     * @param pointY
     *              the y coordinate of the point
     * @param a
     *              the x factor in the plane equation
     * @param b
     *              the y factor in the plane equation
     * @param c
     *              the constant in the plane equation
     * @return the distance between the point and the line
     */
    public static double distancePointLine(double pointX, double pointY, double a, double b, double c) {
        double denom = Math.sqrt(a * a + b * b);
        return (a * pointX + b * pointY + c) / denom;
    }

    /**
     * Determine the signed distance of the given point <tt>(pointX, pointY)</tt> to the line defined by the two points <tt>(x0, y0)</tt> and <tt>(x1, y1)</tt>.
     * <p>
     * Reference: <a href="http://mathworld.wolfram.com/Point-LineDistance2-Dimensional.html">http://mathworld.wolfram.com</a>
     * 
     * @param pointX
     *              the x coordinate of the point
     * @param pointY
     *              the y coordinate of the point
     * @param x0
     *              the x coordinate of the first point on the line
     * @param y0
     *              the y coordinate of the first point on the line
     * @param x1
     *              the x coordinate of the second point on the line
     * @param y1
     *              the y coordinate of the second point on the line
     * @return the distance between the point and the line
     */
    public static double distancePointLine(double pointX, double pointY, double x0, double y0, double x1, double y1) {
        double dx = x1 - x0;
        double dy = y1 - y0;
        double denom = Math.sqrt(dx * dx + dy * dy);
        return (dx * (y0 - pointY) - (x0 - pointX) * dy) / denom;
    }

    /**
     * Test whether the ray with given origin <tt>(originX, originY)</tt> and direction <tt>(dirX, dirY)</tt> intersects the line
     * containing the given point <tt>(pointX, pointY)</tt> and having the normal <tt>(normalX, normalY)</tt>, and return the
     * value of the parameter <i>t</i> in the ray equation <i>p(t) = origin + t * dir</i> of the intersection point.
     * <p>
     * This method returns <tt>-1.0</tt> if the ray does not intersect the line, because it is either parallel to the line or its direction points
     * away from the line or the ray's origin is on the <i>negative</i> side of the line (i.e. the line's normal points away from the ray's origin).
     * 
     * @param originX
     *              the x coordinate of the ray's origin
     * @param originY
     *              the y coordinate of the ray's origin
     * @param dirX
     *              the x coordinate of the ray's direction
     * @param dirY
     *              the y coordinate of the ray's direction
     * @param pointX
     *              the x coordinate of a point on the line
     * @param pointY
     *              the y coordinate of a point on the line
     * @param normalX
     *              the x coordinate of the line's normal
     * @param normalY
     *              the y coordinate of the line's normal
     * @param epsilon
     *              some small epsilon for when the ray is parallel to the line
     * @return the value of the parameter <i>t</i> in the ray equation <i>p(t) = origin + t * dir</i> of the intersection point, if the ray
     *         intersects the line; <tt>-1.0</tt> otherwise
     */
    public static double intersectRayLine(double originX, double originY, double dirX, double dirY, double pointX, double pointY, double normalX, double normalY, double epsilon) {
        double denom = normalX * dirX + normalY * dirY;
        if (denom < epsilon) {
            double t = ((pointX - originX) * normalX + (pointY - originY) * normalY) / denom;
            if (t >= 0.0)
                return t;
        }
        return -1.0;
    }

    /**
     * Test whether the ray with given <code>origin</code> and direction <code>dir</code> intersects the line
     * containing the given <code>point</code> and having the given <code>normal</code>, and return the
     * value of the parameter <i>t</i> in the ray equation <i>p(t) = origin + t * dir</i> of the intersection point.
     * <p>
     * This method returns <tt>-1.0</tt> if the ray does not intersect the line, because it is either parallel to the line or its direction points
     * away from the line or the ray's origin is on the <i>negative</i> side of the line (i.e. the line's normal points away from the ray's origin).
     * 
     * @param origin
     *              the ray's origin
     * @param dir
     *              the ray's direction
     * @param point
     *              a point on the line
     * @param normal
     *              the line's normal
     * @param epsilon
     *              some small epsilon for when the ray is parallel to the line
     * @return the value of the parameter <i>t</i> in the ray equation <i>p(t) = origin + t * dir</i> of the intersection point, if the ray
     *         intersects the line; <tt>-1.0</tt> otherwise
     */
    public static double intersectRayLine(Vector2d origin, Vector2d dir, Vector2d point, Vector2d normal, double epsilon) {
        return intersectRayLine(origin.x, origin.y, dir.x, dir.y, point.x, point.y, normal.x, normal.y, epsilon);
    }

    /**
     * Test whether the axis-aligned rectangle with minimum corner <tt>(minX, minY)</tt> and maximum corner <tt>(maxX, maxY)</tt>
     * intersects the circle with the given center <tt>(centerX, centerY)</tt> and square radius <code>radiusSquared</code>.
     * <p>
     * Reference: <a href="http://stackoverflow.com/questions/4578967/cube-sphere-intersection-test#answer-4579069">http://stackoverflow.com</a>
     * 
     * @param minX
     *          the x coordinate of the minimum corner of the axis-aligned rectangle
     * @param minY
     *          the y coordinate of the minimum corner of the axis-aligned rectangle
     * @param maxX
     *          the x coordinate of the maximum corner of the axis-aligned rectangle
     * @param maxY
     *          the y coordinate of the maximum corner of the axis-aligned rectangle
     * @param centerX
     *          the x coordinate of the circle's center
     * @param centerY
     *          the y coordinate of the circle's center
     * @param radiusSquared
     *          the square of the circle's radius
     * @return <code>true</code> iff the axis-aligned rectangle intersects the circle; <code>false</code> otherwise
     */
    public static boolean testAarCircle(double minX, double minY, double maxX, double maxY, double centerX, double centerY, double radiusSquared) {
        double radius2 = radiusSquared;
        if (centerX < minX) {
            double d = (centerX - minX);
            radius2 -= d * d;
        } else if (centerX > maxX) {
            double d = (centerX - maxX);
            radius2 -= d * d;
        }
        if (centerY < minY) {
            double d = (centerY - minY);
            radius2 -= d * d;
        } else if (centerY > maxY) {
            double d = (centerY - maxY);
            radius2 -= d * d;
        }
        return radius2 >= 0.0;
    }

    /**
     * Test whether the axis-aligned rectangle with minimum corner <code>min</code> and maximum corner <code>max</code>
     * intersects the circle with the given <code>center</code> and square radius <code>radiusSquared</code>.
     * <p>
     * Reference: <a href="http://stackoverflow.com/questions/4578967/cube-sphere-intersection-test#answer-4579069">http://stackoverflow.com</a>
     * 
     * @param min
     *          the minimum corner of the axis-aligned rectangle
     * @param max
     *          the maximum corner of the axis-aligned rectangle
     * @param center
     *          the circle's center
     * @param radiusSquared
     *          the squared of the circle's radius
     * @return <code>true</code> iff the axis-aligned rectangle intersects the circle; <code>false</code> otherwise
     */
    public static boolean testAarCircle(Vector2d min, Vector2d max, Vector2d center, double radiusSquared) {
        return testAarCircle(min.x, min.y, max.x, max.y, center.x, center.y, radiusSquared);
    }

    /**
     * Determine the closest point on the triangle with the given vertices <tt>(v0X, v0Y)</tt>, <tt>(v1X, v1Y)</tt>, <tt>(v2X, v2Y)</tt>
     * between that triangle and the given point <tt>(pX, pY)</tt> and store that point into the given <code>result</code>.
     * <p>
     * Additionally, this method returns whether the closest point is a {@link #POINT_ON_TRIANGLE_VERTEX vertex} of the triangle, or lies on an
     * {@link #POINT_ON_TRIANGLE_EDGE edge} or on the {@link #POINT_ON_TRIANGLE_FACE face} of the triangle.
     * <p>
     * Reference: Book "Real-Time Collision Detection"
     * 
     * @param v0X
     *          the x coordinate of the first vertex of the triangle
     * @param v0Y
     *          the y coordinate of the first vertex of the triangle
     * @param v1X
     *          the x coordinate of the second vertex of the triangle
     * @param v1Y
     *          the y coordinate of the second vertex of the triangle
     * @param v2X
     *          the x coordinate of the third vertex of the triangle
     * @param v2Y
     *          the y coordinate of the third vertex of the triangle
     * @param pX
     *          the x coordinate of the point
     * @param pY
     *          the y coordinate of the point
     * @param result
     *          will hold the closest point
     * @return one of {@link #POINT_ON_TRIANGLE_VERTEX}, {@link #POINT_ON_TRIANGLE_EDGE} or {@link #POINT_ON_TRIANGLE_FACE}
     */
    public static int findClosestPointOnTriangle(double v0X, double v0Y, double v1X, double v1Y, double v2X, double v2Y, double pX, double pY, Vector2d result) {
        double aX = v0X - pX, aY = v0Y - pY;
        double bX = v1X - pX, bY = v1Y - pY;
        double cX = v2X - pX, cY = v2Y - pY;
        double abX = bX - aX, abY = bY - aY;
        double acX = cX - aX, acY = cY - aY;
        double d1 = -(abX * aX + abY * aY);
        double d2 = -(acX * aX + acY * aY);
        if (d1 <= 0.0 && d2 <= 0.0) {
            result.set(v0X, v0Y);
            return POINT_ON_TRIANGLE_VERTEX;
        }
        double d3 = -(abX * bX + abY * bY);
        double d4 = -(acX * bX + acY * bY);
        if (d3 >= 0.0 && d4 <= d3) {
            result.set(v1X, v1Y);
            return POINT_ON_TRIANGLE_VERTEX;
        }
        double vc = d1 * d4 - d3 * d2;
        if (vc <= 0.0 && d1 >= 0.0 && d3 <= 0.0) {
            double v = d1 / (d1 - d3);
            result.set(v0X + abX * v, v0Y + abY * v);
            return POINT_ON_TRIANGLE_EDGE;
        }
        double d5 = -(abX * cX + abY * cY );
        double d6 = -(acX * cX + acY * cY);
        if (d6 >= 0.0 && d5 <= d6) {
            result.set(v2X, v2Y);
            return POINT_ON_TRIANGLE_VERTEX;
        }
        double vb = d5 * d2 - d1 * d6;
        if (vb <= 0.0 && d2 >= 0.0 && d6 <= 0.0) {
            double w = d2 / (d2 - d6);
            result.set(v0X + acX * w, v0Y + acY * w);
            return POINT_ON_TRIANGLE_EDGE;
        }
        double va = d3 * d6 - d5 * d4;
        if (va <= 0.0 && (d4 - d3) >= 0.0 && (d5 - d6) >= 0.0) {
            double w = (d4 - d3) / (d4 - d3 + d5 - d6);
            result.set(v1X + (cX - bX) * w, v1Y + (cY - bY) * w);
            return POINT_ON_TRIANGLE_EDGE;
        }
        double denom = 1.0 / (va + vb + vc);
        double vn = vb * denom;
        double wn = vc * denom;
        result.set(v0X + abX * vn + acX * wn, v0Y + abY * vn + acY * wn);
        return POINT_ON_TRIANGLE_FACE;
    }

    /**
     * Determine the closest point on the triangle with the vertices <code>v0</code>, <code>v1</code>, <code>v2</code>
     * between that triangle and the given point <code>p</code> and store that point into the given <code>result</code>.
     * <p>
     * Additionally, this method returns whether the closest point is a {@link #POINT_ON_TRIANGLE_VERTEX vertex} of the triangle, or lies on an
     * {@link #POINT_ON_TRIANGLE_EDGE edge} or on the {@link #POINT_ON_TRIANGLE_FACE face} of the triangle.
     * <p>
     * Reference: Book "Real-Time Collision Detection"
     * 
     * @param v0
     *          the first vertex of the triangle
     * @param v1
     *          the second vertex of the triangle
     * @param v2
     *          the third vertex of the triangle
     * @param p
     *          the point
     * @param result
     *          will hold the closest point
     * @return one of {@link #POINT_ON_TRIANGLE_VERTEX}, {@link #POINT_ON_TRIANGLE_EDGE} or {@link #POINT_ON_TRIANGLE_FACE}
     */
    public static int findClosestPointOnTriangle(Vector2d v0, Vector2d v1, Vector2d v2, Vector2d p, Vector2d result) {
        return findClosestPointOnTriangle(v0.x, v0.y, v1.x, v1.y, v2.x, v2.y, p.x, p.y, result);
    }

    /**
     * Test whether the given ray with the origin <tt>(originX, originY)</tt> and direction <tt>(dirX, dirY)</tt>
     * intersects the given circle with center <tt>(centerX, centerY)</tt> and square radius <code>radiusSquared</code>,
     * and store the values of the parameter <i>t</i> in the ray equation <i>p(t) = origin + t * dir</i> for both points (near
     * and far) of intersections into the given array <code>result</code>.
     * <p>
     * This method returns <code>true</code> for a ray whose origin lies inside the circle.
     * <p>
     * Reference: <a href="http://www.scratchapixel.com/lessons/3d-basic-rendering/minimal-ray-tracer-rendering-simple-shapes/ray-sphere-intersection">http://www.scratchapixel.com/</a>
     * 
     * @param originX
     *              the x coordinate of the ray's origin
     * @param originY
     *              the y coordinate of the ray's origin
     * @param dirX
     *              the x coordinate of the ray's direction
     * @param dirY
     *              the y coordinate of the ray's direction
     * @param centerX
     *              the x coordinate of the circle's center
     * @param centerY
     *              the y coordinate of the circle's center
     * @param radiusSquared
     *              the circle radius squared
     * @param result
     *              a double[] array that will contain the values of the parameter <i>t</i> in the ray equation
     *              <i>p(t) = origin + t * dir</i> for both points (near and far) of intersections with the circle
     * @return <code>true</code> if the ray intersects the circle; <code>false</code> otherwise
     */
    public static boolean intersectRayCircle(double originX, double originY, double dirX, double dirY, 
            double centerX, double centerY, double radiusSquared, double[] result) {
        double Lx = centerX - originX;
        double Ly = centerY - originY;
        double tca = Lx * dirX + Ly * dirY;
        double d2 = Lx * Lx + Ly * Ly - tca * tca;
        if (d2 > radiusSquared)
            return false;
        double thc = Math.sqrt(radiusSquared - d2);
        double t0 = tca - thc;
        double t1 = tca + thc;
        if (t0 < t1 && t1 >= 0.0) {
            result[0] = t0;
            result[1] = t1;
            return true;
        }
        return false;
    }

    /**
     * Test whether the ray with the given <code>origin</code> and direction <code>dir</code>
     * intersects the circle with the given <code>center</code> and square radius <code>radiusSquared</code>,
     * and store the values of the parameter <i>t</i> in the ray equation <i>p(t) = origin + t * dir</i> for both points (near
     * and far) of intersections into the given array <code>result</code>.
     * <p>
     * This method returns <code>true</code> for a ray whose origin lies inside the circle.
     * <p>
     * Reference: <a href="http://www.scratchapixel.com/lessons/3d-basic-rendering/minimal-ray-tracer-rendering-simple-shapes/ray-sphere-intersection">http://www.scratchapixel.com/</a>
     * 
     * @param origin
     *              the ray's origin
     * @param dir
     *              the ray's direction
     * @param center
     *              the circle's center
     * @param radiusSquared
     *              the circle radius squared
     * @param result
     *              a double[] array that will contain the values of the parameter <i>t</i> in the ray equation
     *              <i>p(t) = origin + t * dir</i> for both points (near and far) of intersections with the circle
     * @return <code>true</code> if the ray intersects the circle; <code>false</code> otherwise
     */
    public static boolean intersectRayCircle(Vector2d origin, Vector2d dir, Vector2d center, double radiusSquared, double[] result) {
        return intersectRayCircle(origin.x, origin.y, dir.x, dir.y, center.x, center.y, radiusSquared, result);
    }

    /**
     * Test whether the given ray with the origin <tt>(originX, originY)</tt> and direction <tt>(dirX, dirY)</tt>
     * intersects the given circle with center <tt>(centerX, centerY)</tt> and square radius <code>radiusSquared</code>.
     * <p>
     * This method returns <code>true</code> for a ray whose origin lies inside the circle.
     * <p>
     * Reference: <a href="http://www.scratchapixel.com/lessons/3d-basic-rendering/minimal-ray-tracer-rendering-simple-shapes/ray-sphere-intersection">http://www.scratchapixel.com/</a>
     * 
     * @param originX
     *              the x coordinate of the ray's origin
     * @param originY
     *              the y coordinate of the ray's origin
     * @param dirX
     *              the x coordinate of the ray's direction
     * @param dirY
     *              the y coordinate of the ray's direction
     * @param centerX
     *              the x coordinate of the circle's center
     * @param centerY
     *              the y coordinate of the circle's center
     * @param radiusSquared
     *              the circle radius squared
     * @return <code>true</code> if the ray intersects the circle; <code>false</code> otherwise
     */
    public static boolean testRayCircle(double originX, double originY, double dirX, double dirY, 
            double centerX, double centerY, double radiusSquared) {
        double Lx = centerX - originX;
        double Ly = centerY - originY;
        double tca = Lx * dirX + Ly * dirY;
        double d2 = Lx * Lx + Ly * Ly - tca * tca;
        if (d2 > radiusSquared)
            return false;
        double thc = Math.sqrt(radiusSquared - d2);
        double t0 = tca - thc;
        double t1 = tca + thc;
        return t0 < t1 && t1 >= 0.0;
    }

    /**
     * Test whether the ray with the given <code>origin</code> and direction <code>dir</code>
     * intersects the circle with the given <code>center</code> and square radius.
     * <p>
     * This method returns <code>true</code> for a ray whose origin lies inside the circle.
     * <p>
     * Reference: <a href="http://www.scratchapixel.com/lessons/3d-basic-rendering/minimal-ray-tracer-rendering-simple-shapes/ray-sphere-intersection">http://www.scratchapixel.com/</a>
     * 
     * @param origin
     *              the ray's origin
     * @param dir
     *              the ray's direction
     * @param center
     *              the circle's center
     * @param radiusSquared
     *              the circle radius squared
     * @return <code>true</code> if the ray intersects the circle; <code>false</code> otherwise
     */
    public static boolean testRayCircle(Vector2d origin, Vector2d dir, Vector2d center, double radiusSquared) {
        return testRayCircle(origin.x, origin.y, dir.x, dir.y, center.x, center.y, radiusSquared);
    }

    /**
     * Test whether the given ray with the origin <tt>(originX, originY)</tt> and direction <tt>(dirX, dirY)</tt>
     * intersects the given axis-aligned rectangle given as any two opposite corners <tt>(aX, aY)</tt> and <tt>(bX, bY)</tt>,
     * and return the values of the parameter <i>t</i> in the ray equation <i>p(t) = origin + t * dir</i> of the near and far point of intersection.
     * <p>
     * This is an implementation of the <a href="http://www.siggraph.org/education/materials/HyperGraph/raytrace/rtinter3.htm">
     * Ray - Box Intersection</a> method, also known as "slab method."
     * <p>
     * This method returns <code>true</code> for a ray whose origin lies inside the axis-aligned rectangle.
     * 
     * @see #intersectRayAar(Vector2d, Vector2d, Vector2d, Vector2d, double[])
     * 
     * @param originX
     *              the x coordinate of the ray's origin
     * @param originY
     *              the y coordinate of the ray's origin
     * @param dirX
     *              the x coordinate of the ray's direction
     * @param dirY
     *              the y coordinate of the ray's direction
     * @param aX
     *              the x coordinate of one corner of the axis-aligned rectangle
     * @param aY
     *              the y coordinate of one corner of the axis-aligned rectangle
     * @param bX
     *              the x coordinate of the opposite corner of the axis-aligned rectangle
     * @param bY
     *              the y coordinate of the opposite corner of the axis-aligned rectangle
     * @param result
     *              the double[] array which will hold the resulting values of the parameter
     *              <i>t</i> in the ray equation <i>p(t) = origin + t * dir</i> of the near and far point of intersection
     *              iff the ray intersects the axis-aligned rectangle
     * @return <code>true</code> if the given ray intersects the axis-aligned rectangle; <code>false</code> otherwise
     */
    public static boolean intersectRayAar(double originX, double originY, double dirX, double dirY, 
            double aX, double aY, double bX, double bY, double[] result) {
        double invDirX = 1.0 / dirX, invDirY = 1.0 / dirY;
        double tMinX = (aX - originX) * invDirX;
        double tMinY = (aY - originY) * invDirY;
        double tMaxX = (bX - originX) * invDirX;
        double tMaxY = (bY - originY) * invDirY;
        double t1X = tMinX < tMaxX ? tMinX : tMaxX;
        double t1Y = tMinY < tMaxY ? tMinY : tMaxY;
        double t2X = tMinX > tMaxX ? tMinX : tMaxX;
        double t2Y = tMinY > tMaxY ? tMinY : tMaxY;
        double tNear = t1X > t1Y ? t1X : t1Y;
        double tFar = t2X < t2Y ? t2X : t2Y;
        if (tNear < tFar && tFar >= 0.0) {
            result[0] = tNear;
            result[1] = tFar;
            return true;
        }
        return false;
    }

    /**
     * Test whether the ray with the given <code>origin</code> and direction <code>dir</code>
     * intersects the given axis-aligned rectangle specified as any two opposite corners <code>a</code> and <code>b</code>,
     * and return the values of the parameter <i>t</i> in the ray equation <i>p(t) = origin + t * dir</i> of the near and far point of intersection..
     * <p>
     * This is an implementation of the <a href="http://www.siggraph.org/education/materials/HyperGraph/raytrace/rtinter3.htm">
     * Ray - Box Intersection</a> method, also known as "slab method."
     * <p>
     * This method returns <code>true</code> for a ray whose origin lies inside the axis-aligned rectangle.
     * 
     * @see #intersectRayAar(double, double, double, double, double, double, double, double, double[])
     * 
     * @param origin
     *              the ray's origin
     * @param dir
     *              the ray's direction
     * @param a
     *              one corner of the axis-aligned rectangle
     * @param b
     *              the opposite corner of the axis-aligned rectangle
     * @param result
     *              the double[] array which will hold the resulting values of the parameter
     *              <i>t</i> in the ray equation <i>p(t) = origin + t * dir</i> of the near and far point of intersection
     *              iff the ray intersects the axis-aligned rectangle
     * @return <code>true</code> if the given ray intersects the axis-aligned rectangle; <code>false</code> otherwise
     */
    public static boolean intersectRayAar(Vector2d origin, Vector2d dir, Vector2d a, Vector2d b, double[] result) {
        return intersectRayAar(origin.x, origin.y, dir.x, dir.y, a.x, a.y, b.x, b.y, result);
    }

    /**
     * Test whether the given ray with the origin <tt>(originX, originY)</tt> and direction <tt>(dirX, dirY)</tt>
     * intersects the given axis-aligned rectangle given as any two opposite corners <tt>(aX, aY)</tt> and <tt>(bX, bY)</tt>.
     * <p>
     * This is an implementation of the <a href="http://www.siggraph.org/education/materials/HyperGraph/raytrace/rtinter3.htm">
     * Ray - Box Intersection</a> method, also known as "slab method."
     * <p>
     * This method returns <code>true</code> for a ray whose origin lies inside the axis-aligned rectangle.
     * 
     * @see #testRayAar(Vector2d, Vector2d, Vector2d, Vector2d)
     * 
     * @param originX
     *              the x coordinate of the ray's origin
     * @param originY
     *              the y coordinate of the ray's origin
     * @param dirX
     *              the x coordinate of the ray's direction
     * @param dirY
     *              the y coordinate of the ray's direction
     * @param aX
     *              the x coordinate of one corner of the axis-aligned rectangle
     * @param aY
     *              the y coordinate of one corner of the axis-aligned rectangle
     * @param bX
     *              the x coordinate of the opposite corner of the axis-aligned rectangle
     * @param bY
     *              the y coordinate of the opposite corner of the axis-aligned rectangle
     * @return <code>true</code> if the given ray intersects the axis-aligned rectangle; <code>false</code> otherwise
     */
    public static boolean testRayAar(double originX, double originY, double dirX, double dirY, double aX, double aY, double bX, double bY) {
        double invDirX = 1.0 / dirX, invDirY = 1.0 / dirY;
        double tMinX = (aX - originX) * invDirX;
        double tMinY = (aY - originY) * invDirY;
        double tMaxX = (bX - originX) * invDirX;
        double tMaxY = (bY - originY) * invDirY;
        double t1X = tMinX < tMaxX ? tMinX : tMaxX;
        double t1Y = tMinY < tMaxY ? tMinY : tMaxY;
        double t2X = tMinX > tMaxX ? tMinX : tMaxX;
        double t2Y = tMinY > tMaxY ? tMinY : tMaxY;
        double tNear = t1X > t1Y ? t1X : t1Y;
        double tFar = t2X < t2Y ? t2X : t2Y;
        return tNear < tFar && tFar >= 0.0;
    }

    /**
     * Test whether the ray with the given <code>origin</code> and direction <code>dir</code>
     * intersects the given axis-aligned rectangle specified as any two opposite corners <code>a</code> and <code>b</code>.
     * <p>
     * This is an implementation of the <a href="http://www.siggraph.org/education/materials/HyperGraph/raytrace/rtinter3.htm">
     * Ray - Box Intersection</a> method, also known as "slab method."
     * <p>
     * This method returns <code>true</code> for a ray whose origin lies inside the axis-aligned rectangle.
     * 
     * @see #testRayAar(double, double, double, double, double, double, double, double)
     * 
     * @param origin
     *              the ray's origin
     * @param dir
     *              the ray's direction
     * @param a
     *              one corner of the axis-aligned rectangle
     * @param b
     *              the opposite corner of the axis-aligned rectangle
     * @return <code>true</code> if the given ray intersects the axis-aligned rectangle; <code>false</code> otherwise
     */
    public static boolean testRayAar(Vector2d origin, Vector2d dir, Vector2d a, Vector2d b) {
        return testRayAar(origin.x, origin.y, dir.x, dir.y, a.x, a.y, b.x, b.y);
    }

    /**
     * Test whether the given point <tt>(pX, pY)</tt> lies inside the triangle with the vertices <tt>(v0X, v0Y)</tt>, <tt>(v1X, v1Y)</tt>, <tt>(v2X, v2Y)</tt>.
     * 
     * @param pX
     *          the x coordinate of the point
     * @param pY
     *          the y coordinate of the point
     * @param v0X
     *          the x coordinate of the first vertex of the triangle
     * @param v0Y
     *          the y coordinate of the first vertex of the triangle
     * @param v1X
     *          the x coordinate of the second vertex of the triangle
     * @param v1Y
     *          the y coordinate of the second vertex of the triangle
     * @param v2X
     *          the x coordinate of the third vertex of the triangle
     * @param v2Y
     *          the y coordinate of the third vertex of the triangle
     * @return <code>true</code> iff the point lies inside the triangle; <code>false</code> otherwise
     */
    public static boolean testPointTriangle(double pX, double pY, double v0X, double v0Y, double v1X, double v1Y, double v2X, double v2Y) {
        boolean b1 = (pX - v1X) * (v0Y - v1Y) - (v0X - v1X) * (pY - v1Y) < 0.0;
        boolean b2 = (pX - v2X) * (v1Y - v2Y) - (v1X - v2X) * (pY - v2Y) < 0.0;
        if (b1 != b2)
            return false;
        boolean b3 = (pX - v0X) * (v2Y - v0Y) - (v2X - v0X) * (pY - v0Y) < 0.0;
        return b2 == b3;
    }

    /**
     * Test whether the given <code>point</code> lies inside the triangle with the vertices <code>v0</code>, <code>v1</code>, <code>v2</code>.
     * 
     * @param v0
     *          the first vertex of the triangle
     * @param v1
     *          the second vertex of the triangle
     * @param v2
     *          the third vertex of the triangle
     * @param point
     *          the point
     * @return <code>true</code> iff the point lies inside the triangle; <code>false</code> otherwise
     */
    public static boolean testPointTriangle(Vector2d point, Vector2d v0, Vector2d v1, Vector2d v2) {
        return testPointTriangle(point.x, point.y, v0.x, v0.y, v1.x, v1.y, v2.x, v2.y);
    }

    /**
     * Test whether the given point <tt>(pX, pY)</tt> lies inside the axis-aligned rectangle with the minimum corner <tt>(minX, minY)</tt>
     * and maximum corner <tt>(maxX, maxY)</tt>.
     * 
     * @param pX
     *          the x coordinate of the point
     * @param pY
     *          the y coordinate of the point
     * @param minX
     *          the x coordinate of the minimum corner of the axis-aligned rectangle
     * @param minY
     *          the y coordinate of the minimum corner of the axis-aligned rectangle
     * @param maxX
     *          the x coordinate of the maximum corner of the axis-aligned rectangle
     * @param maxY
     *          the y coordinate of the maximum corner of the axis-aligned rectangle
     * @return <code>true</code> iff the point lies inside the axis-aligned rectangle; <code>false</code> otherwise
     */
    public static boolean testPointAar(double pX, double pY, double minX, double minY, double maxX, double maxY) {
        return pX >= minX && pY >= minY && pX <= maxX && pY <= maxY;
    }

    /**
     * Test whether the point <tt>(pX, pY)</tt> lies inside the circle with center <tt>(centerX, centerY)</tt> and square radius <code>radiusSquared</code>.
     * 
     * @param pX
     *          the x coordinate of the point
     * @param pY
     *          the y coordinate of the point
     * @param centerX
     *          the x coordinate of the circle's center
     * @param centerY
     *          the y coordinate of the circle's center
     * @param radiusSquared
     *          the square radius of the circle
     * @return <code>true</code> iff the point lies inside the circle; <code>false</code> otherwise
     */
    public static boolean testPointCircle(double pX, double pY, double centerX, double centerY, double radiusSquared) {
        double dx = pX - centerX;
        double dy = pY - centerY;
        double dx2 = dx * dx;
        double dy2 = dy * dy;
        return dx2 + dy2 <= radiusSquared;
    }

    /**
     * Test whether the circle with center <tt>(centerX, centerY)</tt> and square radius <code>radiusSquared</code> intersects the triangle with counter-clockwise vertices
     * <tt>(v0X, v0Y)</tt>, <tt>(v1X, v1Y)</tt>, <tt>(v2X, v2Y)</tt>.
     * <p>
     * The vertices of the triangle must be specified in counter-clockwise order.
     * <p>
     * Reference: <a href="http://www.phatcode.net/articles.php?id=459">http://www.phatcode.net/</a>
     * 
     * @param centerX
     *          the x coordinate of the circle's center
     * @param centerY
     *          the y coordinate of the circle's center
     * @param radiusSquared
     *          the square radius of the circle
     * @param v0X
     *          the x coordinate of the first vertex of the triangle
     * @param v0Y
     *          the y coordinate of the first vertex of the triangle
     * @param v1X
     *          the x coordinate of the second vertex of the triangle
     * @param v1Y
     *          the y coordinate of the second vertex of the triangle
     * @param v2X
     *          the x coordinate of the third vertex of the triangle
     * @param v2Y
     *          the y coordinate of the third vertex of the triangle
     * @return <code>true</code> iff the circle intersects the triangle; <code>false</code> otherwise
     */
    public static boolean testCircleTriangle(double centerX, double centerY, double radiusSquared, double v0X, double v0Y, double v1X, double v1Y, double v2X, double v2Y) {
        double c1x = centerX - v0X, c1y = centerY - v0Y;
        double c1sqr = c1x * c1x + c1y * c1y - radiusSquared;
        if (c1sqr <= 0.0)
            return true;
        double c2x = centerX - v1X, c2y = centerY - v1Y;
        double c2sqr = c2x * c2x + c2y * c2y - radiusSquared;
        if (c2sqr <= 0.0)
            return true;
        double c3x = centerX - v2X, c3y = centerY - v2Y;
        double c3sqr = c3x * c3x + c3y * c3y - radiusSquared;
        if (c3sqr <= 0.0)
            return true;
        double e1x = v1X - v0X, e1y = v1Y - v0Y;
        double e2x = v2X - v1X, e2y = v2Y - v1Y;
        double e3x = v0X - v2X, e3y = v0Y - v2Y;
        if (e1x * c1y - e1y * c1x >= 0.0 && e2x * c2y - e2y * c2x >= 0.0 && e3x * c3y - e3y * c3x >= 0.0)
            return true;
        double k = c1x * e1x + c1y * e1y;
        if (k >= 0.0) {
            double len = e1x * e1x + e1y * e1y;
            if (k <= len) {
                if (c1sqr * len <= k * k)
                    return true;
            }
        }
        k = c2x * e2x + c2y * e2y;
        if (k > 0.0) {
            double len = e2x * e2x + e2y * e2y;
            if (k <= len) {
                if (c2sqr * len <= k * k)
                    return true;
            }
        }
        k = c3x * e3x + c3y * e3y;
        if (k >= 0.0) {
            double len = e3x * e3x + e3y * e3y;
            if (k < len) {
                if (c3sqr * len <= k * k)
                    return true;
            }
        }
        return false;
    }

    /**
     * Test whether the circle with given <code>center</code> and square radius <code>radiusSquared</code> intersects the triangle with counter-clockwise vertices
     * <code>v0</code>, <code>v1</code>, <code>v2</code>.
     * <p>
     * The vertices of the triangle must be specified in counter-clockwise order.
     * <p>
     * Reference: <a href="http://www.phatcode.net/articles.php?id=459">http://www.phatcode.net/</a>
     * 
     * @param center
     *          the circle's center
     * @param radiusSquared
     *          the square radius of the circle
     * @param v0
     *          the first vertex of the triangle
     * @param v1
     *          the second vertex of the triangle
     * @param v2
     *          the third vertex of the triangle
     * @return <code>true</code> iff the circle intersects the triangle; <code>false</code> otherwise
     */
    public static boolean testCircleTriangle(Vector2d center, double radiusSquared, Vector2d v0, Vector2d v1, Vector2d v2) {
        return testCircleTriangle(center.x, center.y, radiusSquared, v0.x, v0.y, v1.x, v1.y, v2.x, v2.y);
    }

}
