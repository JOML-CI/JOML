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
 * Represents an axis-aligned box defined via the minimum and maximum corner coordinates as single-precision floats.
 * 
 * @author Kai Burjack
 */
public class AABBf implements Externalizable, AABBfc {

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
    public AABBf set(AABBfc source){
        this.minX = source.minX();
        this.minY = source.minY();
        this.minZ = source.minZ();
        this.maxX = source.maxX();
        this.maxY = source.maxY();
        this.maxZ = source.maxZ();
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

    public float minX() {
        return minX;
    }

    public float minY() {
        return minY;
    }

    public float minZ() {
        return minZ;
    }

    public float maxX() {
        return maxX;
    }

    public float maxY() {
        return maxY;
    }

    public float maxZ() {
        return maxZ;
    }

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
    public float lengthX(){
        return maxX - minX;
    }

    /**
     * Return the length along the y component
     *
     * @return length in the y dimension
     */
    public float lengthY(){
        return maxY - minY;
    }

    /**
     * Return the length along the z component
     *
     * @return length in the z dimension
     */
    public float lengthZ(){
        return maxZ - minZ;
    }

    /**
     * Get the size of the aabb.
     *
     * @param dest
     *         will hold the result
     * @return dest
     */
    public Vector3f getSize(Vector3f dest) {
        return dest.set(lengthX(), lengthY(), lengthZ());
    }

    /**
     * Get the size of the aabb.
     *
     * @param dest
     *         will hold the result
     * @return dest
     */
    public Vector3d getSize(Vector3d dest) {
        return dest.set(lengthX(), lengthY(), lengthZ());
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

    public AABBf union(float x, float y, float z, AABBf dest) {
        dest.minX = this.minX < x ? this.minX : x;
        dest.minY = this.minY < y ? this.minY : y;
        dest.minZ = this.minZ < z ? this.minZ : z;
        dest.maxX = this.maxX > x ? this.maxX : x;
        dest.maxY = this.maxY > y ? this.maxY : y;
        dest.maxZ = this.maxZ > z ? this.maxZ : z;
        return dest;
    }

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

    public AABBf translate(float x, float y, float z, AABBf dest) {
        dest.minX = minX + x;
        dest.minY = minY + y;
        dest.minZ = minZ + z;
        dest.maxX = maxX + x;
        dest.maxY = maxY + y;
        dest.maxZ = maxZ + z;
        return dest;
    }

    public AABBf intersection(AABBfc other, AABBf dest) {
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
     * will have a value of {@link Float#POSITIVE_INFINITY} and the maximum coordinates will have a value of
     * {@link Float#NEGATIVE_INFINITY}.
     *
     * @param other
     *           the other AABB
     * @return this
     */
    public AABBf intersection(AABBfc other) {
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

    public boolean containsPoint(float x, float y, float z) {
        return x > minX && y > minY && z > minZ && x < maxX && y < maxY && z < maxZ;
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

    public int intersectsLineSegment(float p0X, float p0Y, float p0Z, float p1X, float p1Y, float p1Z, Vector2f result) {
        return Intersectionf.intersectLineSegmentAab(p0X, p0Y, p0Z, p1X, p1Y, p1Z, minX, minY, minZ, maxX, maxY, maxZ, result);
    }

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
