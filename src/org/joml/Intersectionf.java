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
 * Contains intersection and distance tests for some geometric primitives.
 * 
 * @author Kai Burjack
 */
public class Intersectionf {

    /**
     * Return value of {@link #findClosestPointOnTriangle(float, float, float, float, float, float, float, float, Vector2f)}
     * and {@link #findClosestPointOnTriangle(Vector2f, Vector2f, Vector2f, Vector2f, Vector2f)} to signal that the closest point is a vertex of the triangle.
     */
    public static final int POINT_ON_TRIANGLE_VERTEX = 0;
    /**
     * Return value of {@link #findClosestPointOnTriangle(float, float, float, float, float, float, float, float, Vector2f)}
     * and {@link #findClosestPointOnTriangle(Vector2f, Vector2f, Vector2f, Vector2f, Vector2f)} to signal that the closest point lies on an edge of the triangle.
     */
    public static final int POINT_ON_TRIANGLE_EDGE = 1;
    /**
     * Return value of {@link #findClosestPointOnTriangle(float, float, float, float, float, float, float, float, Vector2f)}
     * and {@link #findClosestPointOnTriangle(Vector2f, Vector2f, Vector2f, Vector2f, Vector2f)} to signal that the closest point lies within the triangle.
     */
    public static final int POINT_ON_TRIANGLE_FACE = 2;

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
    public static boolean testLineCircle(float a, float b, float c, float centerX, float centerY, float radius) {
        float denom = (float) Math.sqrt(a * a + b * b);
        float dist = (a * centerX + b * centerY + c) / denom;
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
    public static boolean intersectLineCircle(float a, float b, float c, float centerX, float centerY, float radius, Vector3f intersectionCenterAndHL) {
        float denom = (float) Math.sqrt(a * a + b * b + c * c);
        float dist = (a * centerX + b * centerY + c) / denom;
        if (-radius <= dist && dist <= radius) {
            intersectionCenterAndHL.x = centerX + dist * a;
            intersectionCenterAndHL.y = centerY + dist * b;
            intersectionCenterAndHL.z = (float) Math.sqrt(radius * radius - dist * dist);
            return true;
        }
        return false;
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
    public static boolean testAarLine(float minX, float minY, float maxX, float maxY, float a, float b, float c) {
        float pX, pY, nX, nY;
        if (a > 0.0f) {
            pX = maxX;
            nX = minX;
        } else {
            pX = minX;
            nX = maxX;
        }
        if (b > 0.0f) {
            pY = maxY;
            nY = minY;
        } else {
            pY = minY;
            nY = maxY;
        }
        float distN = c + a * nX + b * nY;
        float distP = c + a * pX + b * pY;
        return distN <= 0.0f && distP >= 0.0f;
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
    public static boolean testAarLine(Vector2f min, Vector2f max, float a, float b, float c) {
        return testAarLine(min.x, min.y, max.x, max.y, a, b, c);
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
    public static boolean testAarAar(float minXA, float minYA, float maxXA, float maxYA, float minXB, float minYB, float maxXB, float maxYB) {
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
    public static boolean testAarAar(Vector2f minA, Vector2f maxA, Vector2f minB, Vector2f maxB) {
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
    public static boolean intersectCircleCircle(float aX, float aY, float radiusSquaredA, float bX, float bY, float radiusSquaredB, Vector3f intersectionCenterAndHL) {
        float dX = bX - aX, dY = bY - aY;
        float distSquared = dX * dX + dY * dY;
        float h = 0.5f + (radiusSquaredA - radiusSquaredB) / distSquared;
        float r_i = (float) Math.sqrt(radiusSquaredA - h * h * distSquared);
        if (r_i >= 0.0f) {
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
    public static boolean intersectCircleCircle(Vector2f centerA, float radiusSquaredA, Vector2f centerB, float radiusSquaredB, Vector3f intersectionCenterAndHL) {
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
    public static boolean testCircleCircle(float aX, float aY, float radiusSquaredA, float bX, float bY, float radiusSquaredB) {
        float dX = bX - aX, dY = bY - aY;
        float distSquared = dX * dX + dY * dY;
        float h = 0.5f + (radiusSquaredA - radiusSquaredB) / distSquared;
        float r_i = radiusSquaredA - h * h * distSquared;
        return r_i >= 0.0f;
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
    public static boolean testCircleCircle(Vector2f centerA, float radiusSquaredA, Vector2f centerB, float radiusSquaredB) {
        return testCircleCircle(centerA.x, centerA.y, radiusSquaredA, centerB.x, centerB.y, radiusSquaredB);
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
    public static float intersectRayLine(float originX, float originY, float dirX, float dirY, float pointX, float pointY, float normalX, float normalY, float epsilon) {
        float denom = normalX * dirX + normalY * dirY;
        if (denom < epsilon) {
            float t = ((pointX - originX) * normalX + (pointY - originY) * normalY) / denom;
            if (t >= 0.0f)
                return t;
        }
        return -1.0f;
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
    public static float intersectRayLine(Vector2f origin, Vector2f dir, Vector2f point, Vector2f normal, float epsilon) {
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
    public static boolean testAarCircle(float minX, float minY, float maxX, float maxY, float centerX, float centerY, float radiusSquared) {
        float radius2 = radiusSquared;
        if (centerX < minX) {
            float d = (centerX - minX);
            radius2 -= d * d;
        } else if (centerX > maxX) {
            float d = (centerX - maxX);
            radius2 -= d * d;
        }
        if (centerY < minY) {
            float d = (centerY - minY);
            radius2 -= d * d;
        } else if (centerY > maxY) {
            float d = (centerY - maxY);
            radius2 -= d * d;
        }
        return radius2 >= 0.0f;
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
    public static boolean testAarCircle(Vector2f min, Vector2f max, Vector2f center, float radiusSquared) {
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
    public static int findClosestPointOnTriangle(float v0X, float v0Y, float v1X, float v1Y, float v2X, float v2Y, float pX, float pY, Vector2f result) {
        float aX = v0X - pX, aY = v0Y - pY;
        float bX = v1X - pX, bY = v1Y - pY;
        float cX = v2X - pX, cY = v2Y - pY;
        float abX = bX - aX, abY = bY - aY;
        float acX = cX - aX, acY = cY - aY;
        float d1 = -(abX * aX + abY * aY);
        float d2 = -(acX * aX + acY * aY);
        if (d1 <= 0.0f && d2 <= 0.0f) {
            result.set(v0X, v0Y);
            return POINT_ON_TRIANGLE_VERTEX;
        }
        float d3 = -(abX * bX + abY * bY);
        float d4 = -(acX * bX + acY * bY);
        if (d3 >= 0.0f && d4 <= d3) {
            result.set(v1X, v1Y);
            return POINT_ON_TRIANGLE_VERTEX;
        }
        float vc = d1 * d4 - d3 * d2;
        if (vc <= 0.0f && d1 >= 0.0f && d3 <= 0.0f) {
            float v = d1 / (d1 - d3);
            result.set(v0X + abX * v, v0Y + abY * v);
            return POINT_ON_TRIANGLE_EDGE;
        }
        float d5 = -(abX * cX + abY * cY );
        float d6 = -(acX * cX + acY * cY);
        if (d6 >= 0.0f && d5 <= d6) {
            result.set(v2X, v2Y);
            return POINT_ON_TRIANGLE_VERTEX;
        }
        float vb = d5 * d2 - d1 * d6;
        if (vb <= 0.0f && d2 >= 0.0f && d6 <= 0.0f) {
            float w = d2 / (d2 - d6);
            result.set(v0X + acX * w, v0Y + acY * w);
            return POINT_ON_TRIANGLE_EDGE;
        }
        float va = d3 * d6 - d5 * d4;
        if (va <= 0.0f && (d4 - d3) >= 0.0f && (d5 - d6) >= 0.0f) {
            float w = (d4 - d3) / (d4 - d3 + d5 - d6);
            result.set(v1X + (cX - bX) * w, v1Y + (cY - bY) * w);
            return POINT_ON_TRIANGLE_EDGE;
        }
        float denom = 1.0f / (va + vb + vc);
        float vn = vb * denom;
        float wn = vc * denom;
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
    public static int findClosestPointOnTriangle(Vector2f v0, Vector2f v1, Vector2f v2, Vector2f p, Vector2f result) {
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
     *              a float[] array that will contain the values of the parameter <i>t</i> in the ray equation
     *              <i>p(t) = origin + t * dir</i> for both points (near and far) of intersections with the circle
     * @return <code>true</code> if the ray intersects the circle; <code>false</code> otherwise
     */
    public static boolean intersectRayCircle(float originX, float originY, float dirX, float dirY, 
            float centerX, float centerY, float radiusSquared, float[] result) {
        float Lx = centerX - originX;
        float Ly = centerY - originY;
        float tca = Lx * dirX + Ly * dirY;
        float d2 = Lx * Lx + Ly * Ly - tca * tca;
        if (d2 > radiusSquared)
            return false;
        float thc = (float) Math.sqrt(radiusSquared - d2);
        float t0 = tca - thc;
        float t1 = tca + thc;
        if (t0 < t1 && t1 >= 0.0f) {
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
     *              a float[] array that will contain the values of the parameter <i>t</i> in the ray equation
     *              <i>p(t) = origin + t * dir</i> for both points (near and far) of intersections with the circle
     * @return <code>true</code> if the ray intersects the circle; <code>false</code> otherwise
     */
    public static boolean intersectRayCircle(Vector2f origin, Vector2f dir, Vector2f center, float radiusSquared, float[] result) {
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
    public static boolean testRayCircle(float originX, float originY, float dirX, float dirY, 
            float centerX, float centerY, float radiusSquared) {
        float Lx = centerX - originX;
        float Ly = centerY - originY;
        float tca = Lx * dirX + Ly * dirY;
        float d2 = Lx * Lx + Ly * Ly - tca * tca;
        if (d2 > radiusSquared)
            return false;
        float thc = (float) Math.sqrt(radiusSquared - d2);
        float t0 = tca - thc;
        float t1 = tca + thc;
        return t0 < t1 && t1 >= 0.0f;
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
    public static boolean testRayCircle(Vector2f origin, Vector2f dir, Vector2f center, float radiusSquared) {
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
     * @see #intersectRayAar(Vector2f, Vector2f, Vector2f, Vector2f, float[])
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
     *              the float[] array which will hold the resulting values of the parameter
     *              <i>t</i> in the ray equation <i>p(t) = origin + t * dir</i> of the near and far point of intersection
     *              iff the ray intersects the axis-aligned rectangle
     * @return <code>true</code> if the given ray intersects the axis-aligned rectangle; <code>false</code> otherwise
     */
    public static boolean intersectRayAar(float originX, float originY, float dirX, float dirY, 
            float aX, float aY, float bX, float bY, float[] result) {
        float invDirX = 1.0f / dirX, invDirY = 1.0f / dirY;
        float tMinX = (aX - originX) * invDirX;
        float tMinY = (aY - originY) * invDirY;
        float tMaxX = (bX - originX) * invDirX;
        float tMaxY = (bY - originY) * invDirY;
        float t1X = tMinX < tMaxX ? tMinX : tMaxX;
        float t1Y = tMinY < tMaxY ? tMinY : tMaxY;
        float t2X = tMinX > tMaxX ? tMinX : tMaxX;
        float t2Y = tMinY > tMaxY ? tMinY : tMaxY;
        float tNear = t1X > t1Y ? t1X : t1Y;
        float tFar = t2X < t2Y ? t2X : t2Y;
        if (tNear < tFar && tFar >= 0.0f) {
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
     * @see #intersectRayAar(float, float, float, float, float, float, float, float, float[])
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
     *              the float[] array which will hold the resulting values of the parameter
     *              <i>t</i> in the ray equation <i>p(t) = origin + t * dir</i> of the near and far point of intersection
     *              iff the ray intersects the axis-aligned rectangle
     * @return <code>true</code> if the given ray intersects the axis-aligned rectangle; <code>false</code> otherwise
     */
    public static boolean intersectRayAar(Vector2f origin, Vector2f dir, Vector2f a, Vector2f b, float[] result) {
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
     * @see #testRayAar(Vector2f, Vector2f, Vector2f, Vector2f)
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
    public static boolean testRayAar(float originX, float originY, float dirX, float dirY, float aX, float aY, float bX, float bY) {
        float invDirX = 1.0f / dirX, invDirY = 1.0f / dirY;
        float tMinX = (aX - originX) * invDirX;
        float tMinY = (aY - originY) * invDirY;
        float tMaxX = (bX - originX) * invDirX;
        float tMaxY = (bY - originY) * invDirY;
        float t1X = tMinX < tMaxX ? tMinX : tMaxX;
        float t1Y = tMinY < tMaxY ? tMinY : tMaxY;
        float t2X = tMinX > tMaxX ? tMinX : tMaxX;
        float t2Y = tMinY > tMaxY ? tMinY : tMaxY;
        float tNear = t1X > t1Y ? t1X : t1Y;
        float tFar = t2X < t2Y ? t2X : t2Y;
        return tNear < tFar && tFar >= 0.0f;
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
     * @see #testRayAar(float, float, float, float, float, float, float, float)
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
    public static boolean testRayAar(Vector2f origin, Vector2f dir, Vector2f a, Vector2f b) {
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
    public static boolean testPointTriangle(float pX, float pY, float v0X, float v0Y, float v1X, float v1Y, float v2X, float v2Y) {
        boolean b1 = (pX - v1X) * (v0Y - v1Y) - (v0X - v1X) * (pY - v1Y) < 0.0f;
        boolean b2 = (pX - v2X) * (v1Y - v2Y) - (v1X - v2X) * (pY - v2Y) < 0.0f;
        if (b1 != b2)
            return false;
        boolean b3 = (pX - v0X) * (v2Y - v0Y) - (v2X - v0X) * (pY - v0Y) < 0.0f;
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
    public static boolean testPointTriangle(Vector2f point, Vector2f v0, Vector2f v1, Vector2f v2) {
        return testPointTriangle(point.x, point.y, v0.x, v0.y, v1.x, v1.y, v2.x, v2.y);
    }

}
