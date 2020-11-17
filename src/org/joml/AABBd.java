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
 * Represents an axis-aligned box defined via the minimum and maximum corner coordinates as double-precision floats.
 * 
 * @author Kai Burjack
 */
public class AABBd implements Externalizable, AABBdc {

    /**
     * The x coordinate of the minimum corner.
     */
    public double minX = Double.POSITIVE_INFINITY;
    /**
     * The y coordinate of the minimum corner.
     */
    public double minY = Double.POSITIVE_INFINITY;
    /**
     * The z coordinate of the minimum corner.
     */
    public double minZ = Double.POSITIVE_INFINITY;
    /**
     * The x coordinate of the maximum corner.
     */
    public double maxX = Double.NEGATIVE_INFINITY;
    /**
     * The y coordinate of the maximum corner.
     */
    public double maxY = Double.NEGATIVE_INFINITY;
    /**
     * The z coordinate of the maximum corner.
     */
    public double maxZ = Double.NEGATIVE_INFINITY;

    /**
     * Create a new {@link AABBd} representing the box with
     * <code>(minX, minY, minZ)=(+inf, +inf, +inf)</code> and <code>(maxX, maxY, maxZ)=(-inf, -inf, -inf)</code>.
     */
    public AABBd() {
    }

    /**
     * Create a new {@link AABBd} as a copy of the given <code>source</code>.
     * 
     * @param source
     *          the {@link AABBd} to copy from
     */
    public AABBd(AABBdc source) {
        this.minX = source.minX();
        this.minY = source.minY();
        this.minZ = source.minZ();
        this.maxX = source.maxX();
        this.maxY = source.maxY();
        this.maxZ = source.maxZ();
    }

    /**
     * Create a new {@link AABBd} with the given minimum and maximum corner coordinates.
     * 
     * @param min
     *          the minimum coordinates
     * @param max
     *          the maximum coordinates
     */
    public AABBd(Vector3fc min, Vector3fc max) {
        this.minX = min.x();
        this.minY = min.y();
        this.minZ = min.z();
        this.maxX = max.x();
        this.maxY = max.y();
        this.maxZ = max.z();
    }

    /**
     * Create a new {@link AABBd} with the given minimum and maximum corner coordinates.
     * 
     * @param min
     *          the minimum coordinates
     * @param max
     *          the maximum coordinates
     */
    public AABBd(Vector3dc min, Vector3dc max) {
        this.minX = min.x();
        this.minY = min.y();
        this.minZ = min.z();
        this.maxX = max.x();
        this.maxY = max.y();
        this.maxZ = max.z();
    }

    /**
     * Create a new {@link AABBd} with the given minimum and maximum corner coordinates.
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
    public AABBd(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
        this.minX = minX;
        this.minY = minY;
        this.minZ = minZ;
        this.maxX = maxX;
        this.maxY = maxY;
        this.maxZ = maxZ;
    }

    public double minX() {
        return minX;
    }

    public double minY() {
        return minY;
    }

    public double minZ() {
        return minZ;
    }

    public double maxX() {
        return maxX;
    }

    public double maxY() {
        return maxY;
    }

    public double maxZ() {
        return maxZ;
    }

    /**
     * Set this {@link AABBd} to be a clone of <code>source</code>.
     *
     * @param source
     *            the {@link AABBd} to copy from
     * @return this
     */
    public AABBd set(AABBdc source){
        this.minX = source.minX();
        this.minY = source.minY();
        this.minZ = source.minZ();
        this.maxX = source.maxX();
        this.maxY = source.maxY();
        this.maxZ = source.maxZ();
        return this;
    }

    private AABBd validate() {
        if (!isValid()) {
            minX = Double.POSITIVE_INFINITY;
            minY = Double.POSITIVE_INFINITY;
            minZ = Double.POSITIVE_INFINITY;

            maxX = Double.NEGATIVE_INFINITY;
            maxY = Double.NEGATIVE_INFINITY;
            maxZ = Double.NEGATIVE_INFINITY;
        }
        return this;
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
    public AABBd setMin(double minX, double minY, double minZ) {
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
    public AABBd setMax(double maxX, double maxY, double maxZ) {
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
    public AABBd setMin(Vector3dc min) {
        return this.setMin(min.x(), min.y(), min.z());
    }

    /**
     * Set the maximum corner coordinates.
     * 
     * @param max
     *          the maximum coordinates
     * @return this
     */
    public AABBd setMax(Vector3dc max) {
        return this.setMax(max.x(), max.y(), max.z());
    }

    public double getMax(int component) throws IllegalArgumentException {
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

    public double getMin(int component) throws IllegalArgumentException {
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

    public Vector3d center(Vector3d dest) {
        return dest.set(minX + ((maxX - minX) / 2.0), minY + ((maxY - minY) / 2.0), minZ + ((maxZ - minZ) / 2.0));
    }

    public Vector3f center(Vector3f dest) {
        return dest.set(minX + ((maxX - minX) / 2.0f), minY + ((maxY - minY) / 2.0f), minZ + ((maxZ - minZ) / 2.0f));
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
    public double lengthX(){
        return maxX - minX;
    }

    /**
     * Return the length along the y component
     *
     * @return length in the y dimension
     */
    public double lengthY(){
        return maxY - minY;
    }

    /**
     * Return the length along the z component
     *
     * @return length in the z dimension
     */
    public double lengthZ(){
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
    public AABBd union(double x, double y, double z) {
        return union(x, y, z, this);
    }

    /**
     * Set <code>this</code> to the union of <code>this</code> and the given point <code>p</code>.
     * 
     * @param p
     *          the point
     * @return this
     */
    public AABBd union(Vector3dc p) {
        return union(p.x(), p.y(), p.z(), this);
    }

    public AABBd union(double x, double y, double z, AABBd dest) {
        dest.minX = this.minX < x ? this.minX : x;
        dest.minY = this.minY < y ? this.minY : y;
        dest.minZ = this.minZ < z ? this.minZ : z;
        dest.maxX = this.maxX > x ? this.maxX : x;
        dest.maxY = this.maxY > y ? this.maxY : y;
        dest.maxZ = this.maxZ > z ? this.maxZ : z;
        return dest;
    }

    public AABBd union(Vector3dc p, AABBd dest) {
        return union(p.x(), p.y(), p.z(), dest);
    }

    /**
     * Set <code>this</code> to the union of <code>this</code> and <code>other</code>.
     * 
     * @param other
     *          the other {@link AABBd}
     * @return this
     */
    public AABBd union(AABBdc other) {
        return this.union(other, this);
    }

    public AABBd union(AABBdc other, AABBd dest) {
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
    public AABBd correctBounds() {
        double tmp;
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
    public AABBd translate(Vector3dc xyz) {
        return translate(xyz.x(), xyz.y(), xyz.z(), this);
    }

    public AABBd translate(Vector3dc xyz, AABBd dest) {
        return translate(xyz.x(), xyz.y(), xyz.z(), dest);
    }

    /**
     * Translate <code>this</code> by the given vector <code>xyz</code>.
     * 
     * @param xyz
     *          the vector to translate by
     * @return this
     */
    public AABBd translate(Vector3fc xyz) {
        return translate(xyz.x(), xyz.y(), xyz.z(), this);
    }

    public AABBd translate(Vector3fc xyz, AABBd dest) {
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
    public AABBd translate(double x, double y, double z) {
        return translate(x, y, z, this);
    }

    public AABBd translate(double x, double y, double z, AABBd dest) {
        dest.minX = minX + x;
        dest.minY = minY + y;
        dest.minZ = minZ + z;
        dest.maxX = maxX + x;
        dest.maxY = maxY + y;
        dest.maxZ = maxZ + z;
        return dest;
    }

    public AABBd intersection(AABBdc other, AABBd dest) {
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
     * will have a value of {@link Double#POSITIVE_INFINITY} and the maximum coordinates will have a value of
     * {@link Double#NEGATIVE_INFINITY}.
     *
     * @param other
     *           the other AABB
     * @return this
     */
    public AABBd intersection(AABBdc other) {
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

    public boolean containsPoint(double x, double y, double z) {
        return x > minX && y > minY && z > minZ && x < maxX && y < maxY && z < maxZ;
    }

    public boolean containsPoint(Vector3dc point) {
        return containsPoint(point.x(), point.y(), point.z());
    }

    public boolean intersectsPlane(double a, double b, double c, double d) {
        return Intersectiond.testAabPlane(minX, minY, minZ, maxX, maxY, maxZ, a, b, c, d);
    }

    public boolean intersectsPlane(Planed plane) {
        return Intersectiond.testAabPlane(this, plane);
    }

    public boolean intersectsAABB(AABBd other) {
        return this.maxX > other.minX && this.maxY > other.minY && this.maxZ > other.minZ &&
               this.minX < other.maxX && this.minY < other.maxY && this.minZ < other.maxZ;
    }

    public boolean intersectsSphere(double centerX, double centerY, double centerZ, double radiusSquared) {
        return Intersectiond.testAabSphere(minX, minY, minZ, maxX, maxY, maxZ, centerX, centerY, centerZ, radiusSquared);
    }

    public boolean intersectsSphere(Spheref sphere) {
        return Intersectiond.testAabSphere(this, sphere);
    }

    public boolean intersectsRay(double originX, double originY, double originZ, double dirX, double dirY, double dirZ) {
        return Intersectiond.testRayAab(originX, originY, originZ, dirX, dirY, dirZ, minX, minY, minZ, maxX, maxY, maxZ);
    }

    public boolean intersectsRay(Rayd ray) {
        return Intersectiond.testRayAab(ray, this);
    }

    public boolean intersectsRay(double originX, double originY, double originZ, double dirX, double dirY, double dirZ, Vector2d result) {
        return Intersectiond.intersectRayAab(originX, originY, originZ, dirX, dirY, dirZ, minX, minY, minZ, maxX, maxY, maxZ, result);
    }

    public boolean intersectsRay(Rayd ray, Vector2d result) {
        return Intersectiond.intersectRayAab(ray, this, result);
    }

    public int intersectsLineSegment(double p0X, double p0Y, double p0Z, double p1X, double p1Y, double p1Z, Vector2d result) {
        return Intersectiond.intersectLineSegmentAab(p0X, p0Y, p0Z, p1X, p1Y, p1Z, minX, minY, minZ, maxX, maxY, maxZ, result);
    }

    public int intersectsLineSegment(LineSegmentf lineSegment, Vector2d result) {
        return Intersectiond.intersectLineSegmentAab(lineSegment, this, result);
    }

    /**
     * Apply the given {@link Matrix4dc#isAffine() affine} transformation to this {@link AABBd}.
     * <p>
     * The matrix in <code>m</code> <i>must</i> be {@link Matrix4dc#isAffine() affine}.
     *
     * @param m
     *          the affine transformation matrix
     * @return this
     */
    public AABBd transform(Matrix4dc m) {
        return transform(m, this);
    }

    public AABBd transform(Matrix4dc m, AABBd dest) {
        double dx = maxX - minX, dy = maxY - minY, dz = maxZ - minZ;
        double minx = Double.POSITIVE_INFINITY, miny = Double.POSITIVE_INFINITY, minz = Double.POSITIVE_INFINITY;
        double maxx = Double.NEGATIVE_INFINITY, maxy = Double.NEGATIVE_INFINITY, maxz = Double.NEGATIVE_INFINITY;
        for (int i = 0; i < 8; i++) {
            double x = minX + (i & 1) * dx, y = minY + (i >> 1 & 1) * dy, z = minZ + (i >> 2 & 1) * dz;
            double tx = m.m00() * x + m.m10() * y + m.m20() * z + m.m30();
            double ty = m.m01() * x + m.m11() * y + m.m21() * z + m.m31();
            double tz = m.m02() * x + m.m12() * y + m.m22() * z + m.m32();
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
        long temp;
        temp = Double.doubleToLongBits(maxX);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(maxY);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(maxZ);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(minX);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(minY);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(minZ);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        AABBd other = (AABBd) obj;
        if (Double.doubleToLongBits(maxX) != Double.doubleToLongBits(other.maxX))
            return false;
        if (Double.doubleToLongBits(maxY) != Double.doubleToLongBits(other.maxY))
            return false;
        if (Double.doubleToLongBits(maxZ) != Double.doubleToLongBits(other.maxZ))
            return false;
        if (Double.doubleToLongBits(minX) != Double.doubleToLongBits(other.minX))
            return false;
        if (Double.doubleToLongBits(minY) != Double.doubleToLongBits(other.minY))
            return false;
        if (Double.doubleToLongBits(minZ) != Double.doubleToLongBits(other.minZ))
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
        out.writeDouble(minX);
        out.writeDouble(minY);
        out.writeDouble(minZ);
        out.writeDouble(maxX);
        out.writeDouble(maxY);
        out.writeDouble(maxZ);
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        minX = in.readDouble();
        minY = in.readDouble();
        minZ = in.readDouble();
        maxX = in.readDouble();
        maxY = in.readDouble();
        maxZ = in.readDouble();
    }

}
