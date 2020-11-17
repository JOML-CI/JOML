/*
 * The MIT License
 *
 * Copyright (c) 2020 JOML.
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

/**
 * Represents an axis-aligned box defined via the minimum and maximum corner coordinates as ints.
 * 
 * @author Kai Burjack
 */
public class AABBi implements Externalizable, AABBic {

    /**
     * The x coordinate of the minimum corner.
     */
    public int minX = Integer.MAX_VALUE;
    /**
     * The y coordinate of the minimum corner.
     */
    public int minY = Integer.MAX_VALUE;
    /**
     * The z coordinate of the minimum corner.
     */
    public int minZ = Integer.MAX_VALUE;
    /**
     * The x coordinate of the maximum corner.
     */
    public int maxX = Integer.MIN_VALUE;
    /**
     * The y coordinate of the maximum corner.
     */
    public int maxY = Integer.MIN_VALUE;
    /**
     * The z coordinate of the maximum corner.
     */
    public int maxZ = Integer.MIN_VALUE;


    /**
     * Create a new {@link AABBi} representing the box with
     * <code>(minX, minY, minZ)=(+inf, +inf, +inf)</code> and <code>(maxX, maxY, maxZ)=(-inf, -inf, -inf)</code>.
     */
    public AABBi() {
    }

    /**
     * Create a new {@link AABBi} as a copy of the given <code>source</code>.
     *
     * @param source
     *          the {@link AABBi} to copy from
     */
    public AABBi(AABBic source) {
        this.minX = source.minX();
        this.minY = source.minY();
        this.minZ = source.minZ();
        this.maxX = source.maxX();
        this.maxY = source.maxY();
        this.maxZ = source.maxZ();
    }

    /**
     * Create a new {@link AABBi} with the given minimum and maximum corner coordinates.
     *
     * @param min
     *          the minimum coordinates
     * @param max
     *          the maximum coordinates
     */
    public AABBi(Vector3ic min, Vector3ic max) {
        this.minX = min.x();
        this.minY = min.y();
        this.minZ = min.z();
        this.maxX = max.x();
        this.maxY = max.y();
        this.maxZ = max.z();
    }

    /**
     * Create a new {@link AABBi} with the given minimum and maximum corner coordinates.
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
    public AABBi(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
        this.minX = minX;
        this.minY = minY;
        this.minZ = minZ;
        this.maxX = maxX;
        this.maxY = maxY;
        this.maxZ = maxZ;
    }

    public int minX() {
        return minX;
    }

    public int minY() {
        return minY;
    }

    public int minZ() {
        return minZ;
    }

    public int maxX() {
        return maxX;
    }

    public int maxY() {
        return maxY;
    }

    public int maxZ() {
        return maxZ;
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
    public AABBi setMin(int minX, int minY, int minZ) {
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
    public AABBi setMax(int maxX, int maxY, int maxZ) {
        this.maxX = maxX;
        this.maxY = maxY;
        this.maxZ = maxZ;
        return this;
    }

    /**
     * Set this {@link AABBi} to be a clone of <code>source</code>.
     *
     * @param source
     *            the {@link AABBi} to copy from
     * @return this
     */
    public AABBi set(AABBic source){
        this.minX = source.minX();
        this.minY = source.minY();
        this.minZ = source.minZ();
        this.maxX = source.maxX();
        this.maxY = source.maxY();
        this.maxZ = source.maxZ();
        return this;
    }

    private AABBi validate() {
        if (!isValid()) {
            minX = Integer.MAX_VALUE;
            minY = Integer.MAX_VALUE;
            minZ = Integer.MAX_VALUE;

            maxX = Integer.MIN_VALUE;
            maxY = Integer.MIN_VALUE;
            maxZ = Integer.MIN_VALUE;
        }
        return this;
    }

    public boolean isValid() {
        return minX < maxX && minY < maxY && minZ < maxZ;
    }

    /**
     * Set the minimum corner coordinates.
     *
     * @param min
     *          the minimum coordinates
     * @return this
     */
    public AABBi setMin(Vector3ic min) {
        return this.setMin(min.x(), min.y(), min.z());
    }

    /**
     * Set the maximum corner coordinates.
     *
     * @param max
     *          the maximum coordinates
     * @return this
     */
    public AABBi setMax(Vector3ic max) {
        return this.setMax(max.x(), max.y(), max.z());
    }

    public int getMax(int component) throws IllegalArgumentException {
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

    public int getMin(int component) throws IllegalArgumentException {
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

    public Vector3f center(Vector3f dest) {
        return dest.set(minX + ((maxX - minX) / 2.0f), minY + ((maxY - minY) / 2.0f), minZ + ((maxZ - minZ) / 2.0f));
    }

    public Vector3d center(Vector3d dest) {
        return dest.set(minX + ((maxX - minX) / 2.0), minY + ((maxY - minY) / 2.0), minZ + ((maxZ - minZ) / 2.0));
    }

    public Vector3d extent(Vector3d dest) {
        return dest.set((maxX - minX) / 2.0, (maxY - minY) / 2.0, (maxZ - minZ) / 2.0);
    }

    public Vector3f extent(Vector3f dest) {
        return dest.set((maxX - minX) / 2.0f, (maxY - minY) / 2.0f, (maxZ - minZ) / 2.0f);
    }

    /**
     * Return the length along the x component
     *
     * @return length in the x dimension
     */
    public int lengthX(){
        return maxX - minX;
    }

    /**
     * Return the length along the y component
     *
     * @return length in the y dimension
     */
    public int lengthY(){
        return maxY - minY;
    }

    /**
     * Return the length along the z component.
     *
     * @return length in the z dimension
     */
    public int lengthZ(){
        return maxZ - minZ;
    }

    /**
     * Get the size of the aabb.
     *
     * @param dest
     *         will hold the result
     * @return dest
     */
    public Vector3i getSize(Vector3i dest) {
        return dest.set(maxX - minX, maxY - minY, maxZ - minZ);
    }

    /**
     * Get the size of the aabb.
     *
     * @param dest
     *         will hold the result
     * @return dest
     */
    public Vector3f getSize(Vector3f dest) {
        return dest.set(maxX - minX, maxY - minY, maxZ - minZ);
    }

    /**
     * Get the size of the aabb.
     *
     * @param dest
     *         will hold the result
     * @return dest
     */
    public Vector3d getSize(Vector3d dest) {
        return dest.set(maxX - minX, maxY - minY, maxZ - minZ);
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
    public AABBi union(int x, int y, int z) {
        return union(x, y, z, this);
    }

    /**
     * Set <code>this</code> to the union of <code>this</code> and the given point <code>p</code>.
     *
     * @param p
     *          the point
     * @return this
     */
    public AABBi union(Vector3ic p) {
        return union(p.x(), p.y(), p.z(), this);
    }

    public AABBi union(int x, int y, int z, AABBi dest) {
        dest.minX = this.minX < x ? this.minX : x;
        dest.minY = this.minY < y ? this.minY : y;
        dest.minZ = this.minZ < z ? this.minZ : z;
        dest.maxX = this.maxX > x ? this.maxX : x;
        dest.maxY = this.maxY > y ? this.maxY : y;
        dest.maxZ = this.maxZ > z ? this.maxZ : z;
        return dest;
    }

    public AABBi union(Vector3ic p, AABBi dest) {
        return union(p.x(), p.y(), p.z(), dest);
    }

    /**
     * Set <code>this</code> to the union of <code>this</code> and <code>other</code>.
     *
     * @param other
     *          the other {@link AABBi}
     * @return this
     */
    public AABBi union(AABBic other) {
        return this.union(other, this);
    }

    public AABBi union(AABBic other, AABBi dest) {
        dest.minX = this.minX < other.minX() ? this.minX : other.minX();
        dest.minY = this.minY < other.minY() ? this.minY : other.minY();
        dest.minZ = this.minZ < other.minZ() ? this.minZ : other.minZ();
        dest.maxX = this.maxX > other.maxX() ? this.maxX : other.maxX();
        dest.maxY = this.maxY > other.maxY() ? this.maxY : other.maxY();
        dest.maxZ = this.maxZ > other.maxZ() ? this.maxZ : other.maxZ();
        return dest;
    }

    /**
     * Ensure that the minimum coordinates are strictly less than or equal to the maximum coordinates by swapping
     * them if necessary.
     *
     * @return this
     */
    public AABBi correctBounds() {
        int tmp;
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
    public AABBi translate(Vector3ic xyz) {
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
    public AABBi translate(Vector3ic xyz, AABBi dest) {
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
    public AABBi translate(int x, int y, int z) {
        return translate(x, y, z, this);
    }

    public AABBi translate(int x, int y, int z, AABBi dest) {
        dest.minX = minX + x;
        dest.minY = minY + y;
        dest.minZ = minZ + z;
        dest.maxX = maxX + x;
        dest.maxY = maxY + y;
        dest.maxZ = maxZ + z;
        return dest;
    }

    public AABBi intersection(AABBic other, AABBi dest) {
        dest.minX = Math.max(minX, other.minX());
        dest.minY = Math.max(minY, other.minY());
        dest.minZ = Math.max(minZ, other.minZ());

        dest.maxX = Math.min(maxX, other.maxX());
        dest.maxY = Math.min(maxY, other.maxY());
        dest.maxZ = Math.min(maxZ, other.maxZ());
        return dest.validate();
    }


    /**
     * Compute the AABB of intersection between <code>this</code> and the given AABB.
     * <p>
     * If the two AABBs do not intersect, then the minimum coordinates of <code>this</code>
     * will have a value of {@link Integer#MAX_VALUE} and the maximum coordinates will have a value of
     * {@link Integer#MIN_VALUE}.
     *
     * @param other
     *           the other AABB
     * @return this
     */
    public AABBi intersection(AABBic other) {
        return intersection(other, this);
    }

    public boolean containsAABB(AABBdc aabb) {
        return aabb.minX() >= minX && aabb.maxX() <= maxX &&
            aabb.minY() >= minY && aabb.maxY() <= maxY &&
            aabb.minZ() >= minZ && aabb.maxZ() <= maxZ;
    }

    public boolean containsAABB(AABBfc aabb) {
        return aabb.minX() >= minX && aabb.maxX() <= maxX &&
            aabb.minY() >= minY && aabb.maxY() <= maxY &&
            aabb.minZ() >= minZ && aabb.maxZ() <= maxZ;
    }

    public boolean containsAABB(AABBic aabb) {
        return aabb.minX() >= minX && aabb.maxX() <= maxX &&
            aabb.minY() >= minY && aabb.maxY() <= maxY &&
            aabb.minZ() >= minZ && aabb.maxZ() <= maxZ;
    }

    public boolean containsPoint(int x, int y, int z){
        return x > minX && y > minY && z > minZ && x < maxX && y < maxY && z < maxZ;
    }

    public boolean containsPoint(float x, float y, float z){
        return x > minX && y > minY && z > minZ && x < maxX && y < maxY && z < maxZ;
    }

    public boolean containsPoint(Vector3ic point) {
        return containsPoint(point.x(), point.y(), point.z());
    }

    public boolean containsPoint(Vector3fc point) {
        return containsPoint(point.x(), point.y(), point.z());
    }

    public boolean intersectsPlane(float a, float b, float c, float d) {
        return Intersectionf.testAabPlane(minX, minY, minZ, maxX, maxY, maxZ, a, b, c, d);
    }

    public boolean intersectsPlane(Planef plane) {
        return Intersectionf.testAabPlane(this, plane);
    }

    public boolean intersectsAABB(AABBic other) {
        return this.maxX >= other.minX() && this.maxY >= other.minY() && this.maxZ >= other.minZ() &&
            this.minX <= other.maxX() && this.minY <= other.maxY() && this.minZ <= other.maxZ();
    }

    public boolean intersectsAABB(AABBfc other) {
        return this.maxX >= other.minX() && this.maxY >= other.minY() && this.maxZ >= other.minZ() &&
            this.minX <= other.maxX() && this.minY <= other.maxY() && this.minZ <= other.maxZ();
    }

    public boolean intersectsSphere(float centerX, float centerY, float centerZ, float radiusSquared) {
        return Intersectionf.testAabSphere(minX, minY, minZ, maxX, maxY, maxZ, centerX, centerY, centerZ, radiusSquared);
    }

    public boolean intersectsSphere(Spheref sphere) {
        return Intersectionf.testAabSphere(this, sphere);
    }

    public boolean intersectsRay(float originX, float originY, float originZ, float dirX, float dirY, float dirZ) {
        return Intersectionf.testRayAab(originX, originY, originZ, dirX, dirY, dirZ, minX, minY, minZ, maxX, maxY, maxZ);
    }

    public boolean intersectsRay(Rayf ray) {
        return Intersectionf.testRayAab(ray, this);
    }

    public boolean intersectsRay(float originX, float originY, float originZ, float dirX, float dirY, float dirZ, Vector2f result) {
        return Intersectionf.intersectRayAab(originX, originY, originZ, dirX, dirY, dirZ, minX, minY, minZ, maxX, maxY, maxZ, result);
    }

    public boolean intersectsRay(Rayf ray, Vector2f result) {
        return Intersectionf.intersectRayAab(ray, this, result);
    }

    public int intersectLineSegment(float p0X, float p0Y, float p0Z, float p1X, float p1Y, float p1Z, Vector2f result) {
        return Intersectionf.intersectLineSegmentAab(p0X, p0Y, p0Z, p1X, p1Y, p1Z, minX, minY, minZ, maxX, maxY, maxZ, result);
    }

    public int intersectLineSegment(LineSegmentf lineSegment, Vector2f result) {
        return Intersectionf.intersectLineSegmentAab(lineSegment, this, result);
    }


    /**
     * Apply the given {@link Matrix4fc#isAffine() affine} transformation to this {@link AABBi}.
     * <p>
     * The matrix in <code>m</code> <i>must</i> be {@link Matrix4fc#isAffine() affine}.
     *
     * @param m
     *          the affine transformation matrix
     * @return this
     */
    public AABBi transform(Matrix4fc m) {
        return transform(m, this);
    }

    public AABBi transform(Matrix4fc m, AABBi dest) {
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
        dest.minX = Math.roundUsing(minx, RoundingMode.FLOOR);
        dest.minY = Math.roundUsing(miny, RoundingMode.FLOOR);
        dest.minZ = Math.roundUsing(minz, RoundingMode.FLOOR);
        dest.maxX = Math.roundUsing(maxx, RoundingMode.CEILING);
        dest.maxY = Math.roundUsing(maxy, RoundingMode.CEILING);
        dest.maxZ = Math.roundUsing(maxz, RoundingMode.CEILING);
        return dest;
    }

    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + minX;
        result = prime * result + minY;
        result = prime * result + minZ;
        result = prime * result + maxX;
        result = prime * result + maxY;
        result = prime * result + maxZ;
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        AABBi aabBi = (AABBi) obj;
        return minX == aabBi.minX &&
            minY == aabBi.minY &&
            minZ == aabBi.minZ &&
            maxX == aabBi.maxX &&
            maxY == aabBi.maxY &&
            maxZ == aabBi.maxZ;
    }

    public String toString() {
        return "(" + this.minX + " " + this.minY + " " + this.minZ + ") < " +
            "(" + this.maxX + " " + this.maxY + " " + this.maxZ + ")";
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeInt(minX);
        out.writeInt(minY);
        out.writeInt(minZ);
        out.writeInt(maxX);
        out.writeInt(maxY);
        out.writeInt(maxZ);
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        minX = in.readInt();
        minY = in.readInt();
        minZ = in.readInt();
        maxX = in.readInt();
        maxY = in.readInt();
        maxZ = in.readInt();
    }
}
