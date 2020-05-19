/*
 * The MIT License
 *
 * Copyright (c) 2017-2020 JOML
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.joml;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Represents an axis-aligned box defined via the minimum and maximum corner coordinates.
 * 
 * @author Kai Burjack
 */
public class AABBf implements Externalizable {

    /**
     * The x coordinate of the minimum corner.
     */
    public float minX = Float.POSITIVE_INFINITY;
    /**
     * The y coordinate of the minimum corner.
     */
    public float minY = Float.POSITIVE_INFINITY;
    /**
     * The z coordinate of the minimum corner.
     */
    public float minZ = Float.POSITIVE_INFINITY;
    /**
     * The x coordinate of the maximum corner.
     */
    public float maxX = Float.NEGATIVE_INFINITY;
    /**
     * The y coordinate of the maximum corner.
     */
    public float maxY = Float.NEGATIVE_INFINITY;
    /**
     * The z coordinate of the maximum corner.
     */
    public float maxZ = Float.NEGATIVE_INFINITY;

    /**
     * Create a new {@link AABBf} representing the box with
     * <code>(minX, minY, minZ)=(+inf, +inf, +inf)</code> and <code>(maxX, maxY, maxZ)=(-inf, -inf, -inf)</code>.
     */
    public AABBf() {
    }

    /**
     * Create a new {@link AABBf} as a copy of the given <code>source</code>.
     * 
     * @param source
     *          the {@link AABBf} to copy from
     */
    public AABBf(AABBf source) {
        this.minX = source.minX;
        this.minY = source.minY;
        this.minZ = source.minZ;
        this.maxX = source.maxX;
        this.maxY = source.maxY;
        this.maxZ = source.maxZ;
    }

    /**
     * Create a new {@link AABBf} with the given minimum and maximum corner coordinates.
     * 
     * @param min
     *          the minimum coordinates
     * @param max
     *          the maximum coordinates
     */
    public AABBf(Vector3fc min, Vector3fc max) {
        this.minX = min.x();
        this.minY = min.y();
        this.minZ = min.z();
        this.maxX = max.x();
        this.maxY = max.y();
        this.maxZ = max.z();
    }

    /**
     * Create a new {@link AABBf} with the given minimum and maximum corner coordinates.
     * 
     * @param minX
     *          the x coordinate of the minimum corner
     * @param minY
     *          the y coordinate of the minimum corner
     * @param minZ
     *          the z coordinate of the minimum corner
     * @param maxX
     *          the x coordinate of the maximum corner
     * @param maxY
     *          the y coordinate of the maximum corner
     * @param maxZ
     *          the z coordinate of the maximum corner
     */
    public AABBf(float minX, float minY, float minZ, float maxX, float maxY, float maxZ) {
        this.minX = minX;
        this.minY = minY;
        this.minZ = minZ;
        this.maxX = maxX;
        this.maxY = maxY;
        this.maxZ = maxZ;
    }

    /**
     * Set this {@link AABBf} to be a clone of <code>source</code>.
     *
     * @param source
     *            the {@link AABBf} to copy from
     * @return this
     */
    public AABBf set(AABBf source){
        this.minX = source.minX;
        this.minY = source.minY;
        this.minZ = source.minZ;
        this.maxX = source.maxX;
        this.maxY = source.maxY;
        this.maxZ = source.maxZ;
        return this;
    }

    private AABBf validate() {
        if (!isValid()) {
            minX = Float.POSITIVE_INFINITY;
            minY = Float.POSITIVE_INFINITY;
            minZ = Float.POSITIVE_INFINITY;

            maxX = Float.NEGATIVE_INFINITY;
            maxY = Float.NEGATIVE_INFINITY;
            maxZ = Float.NEGATIVE_INFINITY;
        }
        return this;
    }

    /**
     * Check whether <code>this</code> rectangle represents a valid AABB.
     *
     * @return <code>true</code> iff this rectangle is valid; <code>false</code> otherwise
     */
    public boolean isValid() {
        return minX < maxX && minY < maxY && minZ < maxZ;
    }


    /**
     * Set the minimum corner coordinates.
     * 
     * @param minX
     *          the x coordinate of the minimum corner
     * @param minY
     *          the y coordinate of the minimum corner
     * @param minZ
     *          the z coordinate of the minimum corner
     * @return this
     */
    public AABBf setMin(float minX, float minY, float minZ) {
        this.minX = minX;
        this.minY = minY;
        this.minZ = minZ;
        return this;
    }

    /**
     * Set the maximum corner coordinates.
     * 
     * @param maxX
     *          the x coordinate of the maximum corner
     * @param maxY
     *          the y coordinate of the maximum corner
     * @param maxZ
     *          the z coordinate of the maximum corner
     * @return this
     */
    public AABBf setMax(float maxX, float maxY, float maxZ) {
        this.maxX = maxX;
        this.maxY = maxY;
        this.maxZ = maxZ;
        return this;
    }

    /**
     * Set the minimum corner coordinates.
     * 
     * @param min
     *          the minimum coordinates
     * @return this
     */
    public AABBf setMin(Vector3fc min) {
        return this.setMin(min.x(), min.y(), min.z());
    }

    /**
     * Set the maximum corner coordinates.
     * 
     * @param max
     *          the maximum coordinates
     * @return this
     */
    public AABBf setMax(Vector3fc max) {
        return this.setMax(max.x(), max.y(), max.z());
    }

    /**
     * Get the maximum corner coordinate of the given component.
     * 
     * @param component
     *          the component, within <code>[0..2]</code>
     * @return the maximum coordinate
     * @throws IllegalArgumentException if <code>component</code> is not within <code>[0..2]</code>
     */
    public float getMax(int component) throws IllegalArgumentException {
        switch (component) {
        case 0:
            return maxX;
        case 1:
            return maxY;
        case 2:
            return maxZ;
        default:
            throw new IllegalArgumentException();
        }
    }

    /**
     * Get the minimum corner coordinate of the given component.
     * 
     * @param component
     *          the component, within <code>[0..2]</code>
     * @return the maximum coordinate
     * @throws IllegalArgumentException if <code>component</code> is not within <code>[0..2]</code>
     */
    public float getMin(int component) throws IllegalArgumentException {
        switch (component) {
        case 0:
            return minX;
        case 1:
            return minY;
        case 2:
            return minZ;
        default:
            throw new IllegalArgumentException();
        }
    }

    /**
     * Set <code>this</code> to the union of <code>this</code> and the given point <code>(x, y, z)</code>.
     * 
     * @param x
     *          the x coordinate of the point
     * @param y
     *          the y coordinate of the point
     * @param z
     *          the z coordinate of the point
     * @return this
     */
    public AABBf union(float x, float y, float z) {
        return union(x, y, z, this);
    }

    /**
     * Set <code>this</code> to the union of <code>this</code> and the given point <code>p</code>.
     * 
     * @param p
     *          the point
     * @return this
     */
    public AABBf union(Vector3fc p) {
        return union(p.x(), p.y(), p.z(), this);
    }

    /**
     * Compute the union of <code>this</code> and the given point <code>(x, y, z)</code> and store the result in <code>dest</code>.
     * 
     * @param x
     *          the x coordinate of the point
     * @param y
     *          the y coordinate of the point
     * @param z
     *          the z coordinate of the point
     * @param dest
     *          will hold the result
     * @return dest
     */
    public AABBf union(float x, float y, float z, AABBf dest) {
        dest.minX = this.minX < x ? this.minX : x;
        dest.minY = this.minY < y ? this.minY : y;
        dest.minZ = this.minZ < z ? this.minZ : z;
        dest.maxX = this.maxX > x ? this.maxX : x;
        dest.maxY = this.maxY > y ? this.maxY : y;
        dest.maxZ = this.maxZ > z ? this.maxZ : z;
        return dest;
    }

    /**
     * Compute the union of <code>this</code> and the given point <code>p</code> and store the result in <code>dest</code>.
     * 
     * @param p
     *          the point
     * @param dest
     *          will hold the result
     * @return dest
     */
    public AABBf union(Vector3fc p, AABBf dest) {
        return union(p.x(), p.y(), p.z(), dest);
    }

    /**
     * Set <code>this</code> to the union of <code>this</code> and <code>other</code>.
     * 
     * @param other
     *          the other {@link AABBf}
     * @return this
     */
    public AABBf union(AABBf other) {
        return this.union(other, this);
    }

    /**
     * Compute the union of <code>this</code> and <code>other</code> and store the result in <code>dest</code>.
     * 
     * @param other
     *          the other {@link AABBf}
     * @param dest
     *          will hold the result
     * @return dest
     */
    public AABBf union(AABBf other, AABBf dest) {
        dest.minX = this.minX < other.minX ? this.minX : other.minX;
        dest.minY = this.minY < other.minY ? this.minY : other.minY;
        dest.minZ = this.minZ < other.minZ ? this.minZ : other.minZ;
        dest.maxX = this.maxX > other.maxX ? this.maxX : other.maxX;
        dest.maxY = this.maxY > other.maxY ? this.maxY : other.maxY;
        dest.maxZ = this.maxZ > other.maxZ ? this.maxZ : other.maxZ;
        return dest;
    }

    /**
     * Ensure that the minimum coordinates are strictly less than or equal to the maximum coordinates by swapping
     * them if necessary.
     * 
     * @return this
     */
    public AABBf correctBounds() {
        float tmp;
        if (this.minX > this.maxX) {
            tmp = this.minX;
            this.minX = this.maxX;
            this.maxX = tmp;
        }
        if (this.minY > this.maxY) {
            tmp = this.minY;
            this.minY = this.maxY;
            this.maxY = tmp;
        }
        if (this.minZ > this.maxZ) {
            tmp = this.minZ;
            this.minZ = this.maxZ;
            this.maxZ = tmp;
        }
        return this;
    }

    /**
     * Translate <code>this</code> by the given vector <code>xyz</code>.
     * 
     * @param xyz
     *          the vector to translate by
     * @return this
     */
    public AABBf translate(Vector3fc xyz) {
        return translate(xyz.x(), xyz.y(), xyz.z(), this);
    }

    /**
     * Translate <code>this</code> by the given vector <code>xyz</code> and store the result in <code>dest</code>.
     * 
     * @param xyz
     *          the vector to translate by
     * @param dest
     *          will hold the result
     * @return dest
     */
    public AABBf translate(Vector3fc xyz, AABBf dest) {
        return translate(xyz.x(), xyz.y(), xyz.z(), dest);
    }

    /**
     * Translate <code>this</code> by the vector <code>(x, y, z)</code>.
     * 
     * @param x
     *          the x coordinate to translate by
     * @param y
     *          the y coordinate to translate by
     * @param z
     *          the z coordinate to translate by
     * @return this
     */
    public AABBf translate(float x, float y, float z) {
        return translate(x, y, z, this);
    }

    /**
     * Translate <code>this</code> by the vector <code>(x, y, z)</code> and store the result in <code>dest</code>.
     * 
     * @param x
     *          the x coordinate to translate by
     * @param y
     *          the y coordinate to translate by
     * @param z
     *          the z coordinate to translate by
     * @param dest
     *          will hold the result
     * @return dest
     */
    public AABBf translate(float x, float y, float z, AABBf dest) {
        dest.minX = minX + x;
        dest.minY = minY + y;
        dest.minZ = minZ + z;
        dest.maxX = maxX + x;
        dest.maxY = maxY + y;
        dest.maxZ = maxZ + z;
        return dest;
    }

    /**
     * Compute the AABB of intersection between <code>this</code> and the given AABB.
     * <p>
     * If the two rectangles do not intersect, then the minimum coordinates of <code>this</code>
     * will have a value of {@link Float#POSITIVE_INFINITY} and the maximum coordinates will have a value of
     * {@link Float#NEGATIVE_INFINITY}.
     *
     * @param other
     *           the other AABB
     * @param dest
     *          will hold the result
     * @return dest
     */
    public AABBf intersection(AABBf other, AABBf dest) {
        dest.minX = Math.max(minX, other.minX);
        dest.minY = Math.max(minY, other.minY);
        dest.minZ = Math.max(minZ, other.minZ);

        dest.maxX = Math.min(maxX, other.maxX);
        dest.maxY = Math.min(maxY, other.maxY);
        dest.maxZ = Math.min(maxZ, other.maxZ);
        return dest.validate();
    }


    /**
     * Compute the AABB of intersection between <code>this</code> and the given AABB.
     * <p>
     * If the two rectangles do not intersect, then the minimum coordinates of <code>this</code>
     * will have a value of {@link Float#POSITIVE_INFINITY} and the maximum coordinates will have a value of
     * {@link Float#NEGATIVE_INFINITY}.
     *
     * @param other
     *           the other AABB
     * @return this
     */
    public AABBf intersection(AABBf other) {
        return intersection(other, this);
    }

    /**
     * Check if this rectangle contains the given <code>rectangle</code>.
     *
     * @param aabb
     *          the rectangle to test
     * @return <code>true</code> iff this rectangle contains the rectangle; <code>false</code> otherwise
     */
    public boolean containsAABB(AABBd aabb) {
        return aabb.minX >= minX && aabb.maxX <= maxX &&
            aabb.minY >= minY && aabb.maxY <= maxY &&
            aabb.minZ >= minZ && aabb.maxZ <= maxZ;
    }

    /**
     * Check if this rectangle contains the given <code>rectangle</code>.
     *
     * @param aabb
     *          the rectangle to test
     * @return <code>true</code> iff this rectangle contains the rectangle; <code>false</code> otherwise
     */
    public boolean containsAABB(AABBf aabb) {
        return aabb.minX >= minX && aabb.maxX <= maxX &&
            aabb.minY >= minY && aabb.maxY <= maxY &&
            aabb.minZ >= minZ && aabb.maxZ <= maxZ;
    }

    /**
     * Check if this rectangle contains the given <code>rectangle</code>.
     *
     * @param aabb
     *          the rectangle to test
     * @return <code>true</code> iff this rectangle contains the rectangle; <code>false</code> otherwise
     */
    public boolean containsAABB(AABBi aabb) {
        return aabb.minX >= minX && aabb.maxX <= maxX &&
            aabb.minY >= minY && aabb.maxY <= maxY &&
            aabb.minZ >= minZ && aabb.maxZ <= maxZ;
    }


    /**
     * Test whether the point <code>(x, y, z)</code> lies inside this AABB.
     * 
     * @param x
     *          the x coordinate of the point
     * @param y
     *          the y coordinate of the point
     * @param z
     *          the z coordinate of the point
     * @return <code>true</code> iff the given point lies inside this AABB; <code>false</code> otherwise
     */
    public boolean containsPoint(float x, float y, float z) {
        return x >= minX && y >= minY && z >= minZ && x <= maxX && y <= maxY && z <= maxZ;
    }

    /**
     * Test whether the given point lies inside this AABB.
     * 
     * @param point
     *          the coordinates of the point
     * @return <code>true</code> iff the given point lies inside this AABB; <code>false</code> otherwise
     */
    public boolean containsPoint(Vector3fc point) {
        return containsPoint(point.x(), point.y(), point.z());
    }

    /**
     * Test whether the plane given via its plane equation <code>a*x + b*y + c*z + d = 0</code> intersects this AABB.
     * <p>
     * Reference: <a href="http://www.lighthouse3d.com/tutorials/view-frustum-culling/geometric-approach-testing-boxes-ii/">http://www.lighthouse3d.com</a> ("Geometric Approach - Testing Boxes II")
     * 
     * @param a
     *          the x factor in the plane equation
     * @param b
     *          the y factor in the plane equation
     * @param c
     *          the z factor in the plane equation
     * @param d
     *          the constant in the plane equation
     * @return <code>true</code> iff the plane intersects this AABB; <code>false</code> otherwise
     */
    public boolean intersectsPlane(float a, float b, float c, float d) {
        return Intersectionf.testAabPlane(minX, minY, minZ, maxX, maxY, maxZ, a, b, c, d);
    }

    /**
     * Test whether the given plane intersects this AABB.
     * <p>
     * Reference: <a href="http://www.lighthouse3d.com/tutorials/view-frustum-culling/geometric-approach-testing-boxes-ii/">http://www.lighthouse3d.com</a> ("Geometric Approach - Testing Boxes II")
     * 
     * @param plane
     *          the plane
     * @return <code>true</code> iff the plane intersects this AABB; <code>false</code> otherwise
     */
    public boolean intersectsPlane(Planef plane) {
        return Intersectionf.testAabPlane(this, plane);
    }

    /**
     * Test whether <code>this</code> and <code>other</code> intersect.
     * 
     * @param other
     *          the other AABB
     * @return <code>true</code> iff both AABBs intersect; <code>false</code> otherwise
     */
    public boolean intersectsAABB(AABBf other) {
        return this.maxX >= other.minX && this.maxY >= other.minY && this.maxZ >= other.minZ &&
               this.minX <= other.maxX && this.minY <= other.maxY && this.minZ <= other.maxZ;
    }

    /**
     * Test whether this AABB intersects the given sphere with equation
     * <code>(x - centerX)^2 + (y - centerY)^2 + (z - centerZ)^2 - radiusSquared = 0</code>.
     * <p>
     * Reference: <a href="http://stackoverflow.com/questions/4578967/cube-sphere-intersection-test#answer-4579069">http://stackoverflow.com</a>
     * 
     * @param centerX
     *          the x coordinate of the center of the sphere
     * @param centerY
     *          the y coordinate of the center of the sphere
     * @param centerZ
     *          the z coordinate of the center of the sphere
     * @param radiusSquared
     *          the square radius of the sphere
     * @return <code>true</code> iff this AABB and the sphere intersect; <code>false</code> otherwise
     */
    public boolean intersectsSphere(float centerX, float centerY, float centerZ, float radiusSquared) {
        return Intersectionf.testAabSphere(minX, minY, minZ, maxX, maxY, maxZ, centerX, centerY, centerZ, radiusSquared);
    }

    /**
     * Test whether this AABB intersects the given sphere.
     * <p>
     * Reference: <a href="http://stackoverflow.com/questions/4578967/cube-sphere-intersection-test#answer-4579069">http://stackoverflow.com</a>
     * 
     * @param sphere
     *          the sphere
     * @return <code>true</code> iff this AABB and the sphere intersect; <code>false</code> otherwise
     */
    public boolean intersectsSphere(Spheref sphere) {
        return Intersectionf.testAabSphere(this, sphere);
    }

    /**
     * Test whether the given ray with the origin <code>(originX, originY, originZ)</code> and direction <code>(dirX, dirY, dirZ)</code>
     * intersects this AABB.
     * <p>
     * This method returns <code>true</code> for a ray whose origin lies inside this AABB.
     * <p>
     * Reference: <a href="https://dl.acm.org/citation.cfm?id=1198748">An Efficient and Robust Ray–Box Intersection</a>
     * 
     * @param originX
     *          the x coordinate of the ray's origin
     * @param originY
     *          the y coordinate of the ray's origin
     * @param originZ
     *          the z coordinate of the ray's origin
     * @param dirX
     *          the x coordinate of the ray's direction
     * @param dirY
     *          the y coordinate of the ray's direction
     * @param dirZ
     *          the z coordinate of the ray's direction
     * @return <code>true</code> if this AABB and the ray intersect; <code>false</code> otherwise
     */
    public boolean intersectsRay(float originX, float originY, float originZ, float dirX, float dirY, float dirZ) {
        return Intersectionf.testRayAab(originX, originY, originZ, dirX, dirY, dirZ, minX, minY, minZ, maxX, maxY, maxZ);
    }

    /**
     * Test whether the given ray intersects this AABB.
     * <p>
     * This method returns <code>true</code> for a ray whose origin lies inside this AABB.
     * <p>
     * Reference: <a href="https://dl.acm.org/citation.cfm?id=1198748">An Efficient and Robust Ray–Box Intersection</a>
     * 
     * @param ray
     *          the ray
     * @return <code>true</code> if this AABB and the ray intersect; <code>false</code> otherwise
     */
    public boolean intersectsRay(Rayf ray) {
        return Intersectionf.testRayAab(ray, this);
    }

    /**
     * Determine whether the given ray with the origin <code>(originX, originY, originZ)</code> and direction <code>(dirX, dirY, dirZ)</code>
     * intersects this AABB, and return the values of the parameter <i>t</i> in the ray equation
     * <i>p(t) = origin + t * dir</i> of the near and far point of intersection.
     * <p>
     * This method returns <code>true</code> for a ray whose origin lies inside this AABB.
     * <p>
     * Reference: <a href="https://dl.acm.org/citation.cfm?id=1198748">An Efficient and Robust Ray–Box Intersection</a>
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
     * @param result
     *              a vector which will hold the resulting values of the parameter
     *              <i>t</i> in the ray equation <i>p(t) = origin + t * dir</i> of the near and far point of intersection
     *              iff the ray intersects this AABB
     * @return <code>true</code> if the given ray intersects this AABB; <code>false</code> otherwise
     */
    public boolean intersectsRay(float originX, float originY, float originZ, float dirX, float dirY, float dirZ, Vector2f result) {
        return Intersectionf.intersectRayAab(originX, originY, originZ, dirX, dirY, dirZ, minX, minY, minZ, maxX, maxY, maxZ, result);
    }

    /**
     * Determine whether the given ray intersects this AABB, and return the values of the parameter <i>t</i> in the ray equation
     * <i>p(t) = origin + t * dir</i> of the near and far point of intersection.
     * <p>
     * This method returns <code>true</code> for a ray whose origin lies inside this AABB.
     * <p>
     * Reference: <a href="https://dl.acm.org/citation.cfm?id=1198748">An Efficient and Robust Ray–Box Intersection</a>
     * 
     * @param ray
     *              the ray
     * @param result
     *              a vector which will hold the resulting values of the parameter
     *              <i>t</i> in the ray equation <i>p(t) = origin + t * dir</i> of the near and far point of intersection
     *              iff the ray intersects this AABB
     * @return <code>true</code> if the given ray intersects this AABB; <code>false</code> otherwise
     */
    public boolean intersectsRay(Rayf ray, Vector2f result) {
        return Intersectionf.intersectRayAab(ray, this, result);
    }

    /**
     * Determine whether the undirected line segment with the end points <code>(p0X, p0Y, p0Z)</code> and <code>(p1X, p1Y, p1Z)</code>
     * intersects this AABB, and return the values of the parameter <i>t</i> in the ray equation
     * <i>p(t) = origin + p0 * (p1 - p0)</i> of the near and far point of intersection.
     * <p>
     * This method returns <code>true</code> for a line segment whose either end point lies inside this AABB.
     * <p>
     * Reference: <a href="https://dl.acm.org/citation.cfm?id=1198748">An Efficient and Robust Ray–Box Intersection</a>
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
     * @param result
     *              a vector which will hold the resulting values of the parameter
     *              <i>t</i> in the ray equation <i>p(t) = p0 + t * (p1 - p0)</i> of the near and far point of intersection
     *              iff the line segment intersects this AABB
     * @return {@link Intersectionf#INSIDE} if the line segment lies completely inside of this AABB; or
     *         {@link Intersectionf#OUTSIDE} if the line segment lies completely outside of this AABB; or
     *         {@link Intersectionf#ONE_INTERSECTION} if one of the end points of the line segment lies inside of this AABB; or
     *         {@link Intersectionf#TWO_INTERSECTION} if the line segment intersects two sides of this AABB or lies on an edge or a side of this AABB
     */
    public int intersectsLineSegment(float p0X, float p0Y, float p0Z, float p1X, float p1Y, float p1Z, Vector2f result) {
        return Intersectionf.intersectLineSegmentAab(p0X, p0Y, p0Z, p1X, p1Y, p1Z, minX, minY, minZ, maxX, maxY, maxZ, result);
    }

    /**
     * Determine whether the given undirected line segment intersects this AABB, and return the values of the parameter <i>t</i> in the ray equation
     * <i>p(t) = origin + p0 * (p1 - p0)</i> of the near and far point of intersection.
     * <p>
     * This method returns <code>true</code> for a line segment whose either end point lies inside this AABB.
     * <p>
     * Reference: <a href="https://dl.acm.org/citation.cfm?id=1198748">An Efficient and Robust Ray–Box Intersection</a>
     * 
     * @param lineSegment
     *              the line segment
     * @param result
     *              a vector which will hold the resulting values of the parameter
     *              <i>t</i> in the ray equation <i>p(t) = p0 + t * (p1 - p0)</i> of the near and far point of intersection
     *              iff the line segment intersects this AABB
     * @return {@link Intersectionf#INSIDE} if the line segment lies completely inside of this AABB; or
     *         {@link Intersectionf#OUTSIDE} if the line segment lies completely outside of this AABB; or
     *         {@link Intersectionf#ONE_INTERSECTION} if one of the end points of the line segment lies inside of this AABB; or
     *         {@link Intersectionf#TWO_INTERSECTION} if the line segment intersects two sides of this AABB or lies on an edge or a side of this AABB
     */
    public int intersectsLineSegment(LineSegmentf lineSegment, Vector2f result) {
        return Intersectionf.intersectLineSegmentAab(lineSegment, this, result);
    }

    /**
     * Apply the given {@link Matrix4fc#isAffine() affine} transformation to this {@link AABBf}.
     * <p>
     * The matrix in <code>m</code> <i>must</i> be {@link Matrix4fc#isAffine() affine}.
     * 
     * @param m
     *          the affine transformation matrix
     * @return this
     */
    public AABBf transform(Matrix4fc m) {
        return transform(m, this);
    }

    /**
     * Apply the given {@link Matrix4fc#isAffine() affine} transformation to this
     * {@link AABBf} and store the resulting AABB into <code>dest</code>.
     * <p>
     * The matrix in <code>m</code> <i>must</i> be {@link Matrix4fc#isAffine() affine}.
     * 
     * @param m
     *          the affine transformation matrix
     * @param dest
     *          will hold the result
     * @return dest
     */
    public AABBf transform(Matrix4fc m, AABBf dest) {
        float dx = maxX - minX, dy = maxY - minY, dz = maxZ - minZ;
        float minx = Float.POSITIVE_INFINITY, miny = Float.POSITIVE_INFINITY, minz = Float.POSITIVE_INFINITY;
        float maxx = Float.NEGATIVE_INFINITY, maxy = Float.NEGATIVE_INFINITY, maxz = Float.NEGATIVE_INFINITY;
        for (int i = 0; i < 8; i++) {
            float x = minX + (i & 1) * dx, y = minY + (i >> 1 & 1) * dy, z = minZ + (i >> 2 & 1) * dz;
            float tx = m.m00() * x + m.m10() * y + m.m20() * z + m.m30();
            float ty = m.m01() * x + m.m11() * y + m.m21() * z + m.m31();
            float tz = m.m02() * x + m.m12() * y + m.m22() * z + m.m32();
            minx = Math.min(tx, minx);
            miny = Math.min(ty, miny);
            minz = Math.min(tz, minz);
            maxx = Math.max(tx, maxx);
            maxy = Math.max(ty, maxy);
            maxz = Math.max(tz, maxz);
        }
        dest.minX = minx;
        dest.minY = miny;
        dest.minZ = minz;
        dest.maxX = maxx;
        dest.maxY = maxy;
        dest.maxZ = maxz;
        return dest;
    }

    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Float.floatToIntBits(maxX);
        result = prime * result + Float.floatToIntBits(maxY);
        result = prime * result + Float.floatToIntBits(maxZ);
        result = prime * result + Float.floatToIntBits(minX);
        result = prime * result + Float.floatToIntBits(minY);
        result = prime * result + Float.floatToIntBits(minZ);
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        AABBf other = (AABBf) obj;
        if (Float.floatToIntBits(maxX) != Float.floatToIntBits(other.maxX))
            return false;
        if (Float.floatToIntBits(maxY) != Float.floatToIntBits(other.maxY))
            return false;
        if (Float.floatToIntBits(maxZ) != Float.floatToIntBits(other.maxZ))
            return false;
        if (Float.floatToIntBits(minX) != Float.floatToIntBits(other.minX))
            return false;
        if (Float.floatToIntBits(minY) != Float.floatToIntBits(other.minY))
            return false;
        if (Float.floatToIntBits(minZ) != Float.floatToIntBits(other.minZ))
            return false;
        return true;
    }

    /**
     * Return a string representation of this AABB.
     * <p>
     * This method creates a new {@link DecimalFormat} on every invocation with the format string "<code>0.000E0;-</code>".
     * 
     * @return the string representation
     */
    public String toString() {
        return Runtime.formatNumbers(toString(Options.NUMBER_FORMAT));
    }

    /**
     * Return a string representation of this AABB by formatting the vector components with the given {@link NumberFormat}.
     * 
     * @param formatter
     *          the {@link NumberFormat} used to format the vector components with
     * @return the string representation
     */
    public String toString(NumberFormat formatter) {
        return "(" + Runtime.format(minX, formatter) + " " + Runtime.format(minY, formatter) + " " + Runtime.format(minZ, formatter) + ") < "
             + "(" + Runtime.format(maxX, formatter) + " " + Runtime.format(maxY, formatter) + " " + Runtime.format(maxZ, formatter) + ")";
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeFloat(minX);
        out.writeFloat(minY);
        out.writeFloat(minZ);
        out.writeFloat(maxX);
        out.writeFloat(maxY);
        out.writeFloat(maxZ);
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        minX = in.readFloat();
        minY = in.readFloat();
        minZ = in.readFloat();
        maxX = in.readFloat();
        maxY = in.readFloat();
        maxZ = in.readFloat();
    }

}
