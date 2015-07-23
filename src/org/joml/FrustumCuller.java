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
 * Performs frustum culling by caching the frustum planes of an arbitrary transformation {@link Matrix4f matrix}.
 * <p>
 * This class is preferred over the frustum culling methods in {@link Matrix4f} when many objects need to be culled
 * by the same static frustum.
 * 
 * @author Kai Burjack
 */
public class FrustumCuller {

    /**
     * Return value of {@link #isAabInsideFrustum(float, float, float, float, float, float) isAabInsideFrustum()} or
     * {@link #isAabInsideFrustumMasked(float, float, float, float, float, float, int) isAabInsideFrustumMasked()}
     * identifying the plane with equation <tt>x=-1</tt> when using the identity frustum.  
     */
    public static final int PLANE_NX = 0;
    /**
     * Return value of {@link #isAabInsideFrustum(float, float, float, float, float, float) isAabInsideFrustum()} or
     * {@link #isAabInsideFrustumMasked(float, float, float, float, float, float, int) isAabInsideFrustumMasked()}
     * identifying the plane with equation <tt>x=1</tt> when using the identity frustum.  
     */
    public static final int PLANE_PX = 1;
    /**
     * Return value of {@link #isAabInsideFrustum(float, float, float, float, float, float) isAabInsideFrustum()} or
     * {@link #isAabInsideFrustumMasked(float, float, float, float, float, float, int) isAabInsideFrustumMasked()}
     * identifying the plane with equation <tt>y=-1</tt> when using the identity frustum.  
     */
    public static final int PLANE_NY= 2;
    /**
     * Return value of {@link #isAabInsideFrustum(float, float, float, float, float, float) isAabInsideFrustum()} or
     * {@link #isAabInsideFrustumMasked(float, float, float, float, float, float, int) isAabInsideFrustumMasked()}
     * identifying the plane with equation <tt>y=1</tt> when using the identity frustum.  
     */
    public static final int PLANE_PY = 3;
    /**
     * Return value of {@link #isAabInsideFrustum(float, float, float, float, float, float) isAabInsideFrustum()} or
     * {@link #isAabInsideFrustumMasked(float, float, float, float, float, float, int) isAabInsideFrustumMasked()}
     * identifying the plane with equation <tt>z=-1</tt> when using the identity frustum.  
     */
    public static final int PLANE_NZ = 4;
    /**
     * Return value of {@link #isAabInsideFrustum(float, float, float, float, float, float) isAabInsideFrustum()} or
     * {@link #isAabInsideFrustumMasked(float, float, float, float, float, float, int) isAabInsideFrustumMasked()}
     * identifying the plane with equation <tt>z=1</tt> when using the identity frustum.  
     */
    public static final int PLANE_PZ = 5;

    /**
     * The value in a bitmask for
     * {@link #isAabInsideFrustumMasked(float, float, float, float, float, float, int) isAabInsideFrustumMasked()}
     * that identifies the plane with equation <tt>x=-1</tt> when using the identity frustum.
     */
    public static final int PLANE_MASK_NX = 1<<PLANE_NX;
    /**
     * The value in a bitmask for
     * {@link #isAabInsideFrustumMasked(float, float, float, float, float, float, int) isAabInsideFrustumMasked()}
     * that identifies the plane with equation <tt>x=1</tt> when using the identity frustum.
     */
    public static final int PLANE_MASK_PX = 1<<PLANE_PX;
    /**
     * The value in a bitmask for
     * {@link #isAabInsideFrustumMasked(float, float, float, float, float, float, int) isAabInsideFrustumMasked()}
     * that identifies the plane with equation <tt>y=-1</tt> when using the identity frustum.
     */
    public static final int PLANE_MASK_NY = 1<<PLANE_NY;
    /**
     * The value in a bitmask for
     * {@link #isAabInsideFrustumMasked(float, float, float, float, float, float, int) isAabInsideFrustumMasked()}
     * that identifies the plane with equation <tt>y=1</tt> when using the identity frustum.
     */
    public static final int PLANE_MASK_PY = 1<<PLANE_PY;
    /**
     * The value in a bitmask for
     * {@link #isAabInsideFrustumMasked(float, float, float, float, float, float, int) isAabInsideFrustumMasked()}
     * that identifies the plane with equation <tt>z=-1</tt> when using the identity frustum.
     */
    public static final int PLANE_MASK_NZ = 1<<PLANE_NZ;
    /**
     * The value in a bitmask for
     * {@link #isAabInsideFrustumMasked(float, float, float, float, float, float, int) isAabInsideFrustumMasked()}
     * that identifies the plane with equation <tt>z=1</tt> when using the identity frustum.
     */
    public static final int PLANE_MASK_PZ = 1<<PLANE_PZ;

    private float nxX, nxY, nxZ, nxW;
    private float pxX, pxY, pxZ, pxW;
    private float nyX, nyY, nyZ, nyW;
    private float pyX, pyY, pyZ, pyW;
    private float nzX, nzY, nzZ, nzW;
    private float pzX, pzY, pzZ, pzW;

    /**
     * Create a new {@link FrustumCuller} with undefined frustum planes.
     * <p>
     * Before using any of the frustum culling methods, make sure to define the frustum planes using {@link #set(Matrix4f)}.
     */
    public FrustumCuller() {
    }

    /**
     * Create a new {@link FrustumCuller} from the given {@link Matrix4f matrix} by extracing the matrix's frustum planes.
     * 
     * @param m
     *          the {@link Matrix4f} to create the frustum culler from
     */
    public FrustumCuller(Matrix4f m) {
        set(m);
    }

    /**
     * Update the stored frustum planes of <code>this</code> {@link FrustumCuller} with the given {@link Matrix4f matrix}.
     * 
     * @param m
     *          the {@link Matrix4f matrix} to update <code>this</code> frustum culler's frustum planes from
     * @return this
     */
    public FrustumCuller set(Matrix4f m) {
        float l;
        nxX = m.m03 + m.m00; nxY = m.m13 + m.m10; nxZ = m.m23 + m.m20; nxW = m.m33 + m.m30;
        l = (float) Math.sqrt(nxX * nxX + nxY * nxY + nxZ * nxZ);
        nxX /= l; nxY /= l; nxZ /= l; nxW /= l;
        pxX = m.m03 - m.m00; pxY = m.m13 - m.m10; pxZ = m.m23 - m.m20; pxW = m.m33 - m.m30;
        l = (float) Math.sqrt(pxX * pxX + pxY * pxY + pxZ * pxZ);
        pxX /= l; pxY /= l; pxZ /= l; pxW /= l;
        nyX = m.m03 + m.m01; nyY = m.m13 + m.m11; nyZ = m.m23 + m.m21; nyW = m.m33 + m.m31;
        l = (float) Math.sqrt(nyX * nyX + nyY * nyY + nyZ * nyZ);
        nyX /= l; nyY /= l; nyZ /= l; nyW /= l;
        pyX = m.m03 - m.m01; pyY = m.m13 - m.m11; pyZ = m.m23 - m.m21; pyW = m.m33 - m.m31;
        l = (float) Math.sqrt(pyX * pyX + pyY * pyY + pyZ * pyZ);
        pyX /= l; pyY /= l; pyZ /= l; pyW /= l;
        nzX = m.m03 + m.m02; nzY = m.m13 + m.m12; nzZ = m.m23 + m.m22; nzW = m.m33 + m.m32;
        l = (float) Math.sqrt(nzX * nzX + nzY * nzY + nzZ * nzZ);
        nzX /= l; nzY /= l; nzZ /= l; nzW /= l;
        pzX = m.m03 - m.m02; pzY = m.m13 - m.m12; pzZ = m.m23 - m.m22; pzW = m.m33 - m.m32;
        l = (float) Math.sqrt(pzX * pzX + pzY * pzY + pzZ * pzZ);
        pzX /= l; pzY /= l; pzZ /= l; pzW /= l;
        return this;
    }

    /**
     * Determine whether the given point is within the viewing frustum
     * defined by <code>this</code> frustum culler.
     * 
     * @param point
     *          the point to test
     * @return <code>true</code> if the given point is inside the clipping frustum; <code>false</code> otherwise
     */
    public boolean isPointInsideFrustum(Vector3f point) {
        return isPointInsideFrustum(point.x, point.y, point.z);
    }

    /**
     * Determine whether the given point <tt>(x, y, z)</tt> is within the viewing frustum defined by <code>this</code> frustum culler.
     * 
     * @param x
     *          the x-coordinate of the point
     * @param y
     *          the y-coordinate of the point
     * @param z
     *          the z-coordinate of the point
     * @return <code>true</code> if the given point is inside the clipping frustum; <code>false</code> otherwise
     */
    public boolean isPointInsideFrustum(float x, float y, float z) {
        return nxX * x + nxY * y + nxZ * z + nxW >= 0 &&
               pxX * x + pxY * y + pxZ * z + pxW >= 0 &&
               nyX * x + nyY * y + nyZ * z + nyW >= 0 &&
               pyX * x + pyY * y + pyZ * z + pyW >= 0 &&
               nzX * x + nzY * y + nzZ * z + nzW >= 0 &&
               pzX * x + pzY * y + pzZ * z + pzW >= 0;
    }

    /**
     * Determine whether the given sphere is partly or completely within the viewing frustum defined by <code>this</code> frustum culler.
     * <p>
     * The algorithm implemented by this method is conservative. This means that in certain circumstances a <i>false positive</i>
     * can occur, when the method returns <tt>true</tt> for spheres that are actually not visible.
     * See <a href="http://iquilezles.org/www/articles/frustumcorrect/frustumcorrect.htm">iquilezles.org</a> for an examination of this problem.
     * 
     * @param center
     *          the sphere's center
     * @param radius
     *          the sphere's radius
     * @return <code>true</code> if the given sphere is partly or completely inside the clipping frustum;
     *         <code>false</code> otherwise
     */
    public boolean isSphereInsideFrustum(Vector3f center, float radius) {
        return isSphereInsideFrustum(center.x, center.y, center.z, radius);
    }

    /**
     * Determine whether the given sphere is partly or completely within the viewing frustum defined by <code>this</code> frustum culler.
     * <p>
     * The algorithm implemented by this method is conservative. This means that in certain circumstances a <i>false positive</i>
     * can occur, when the method returns <tt>true</tt> for spheres that are actually not visible.
     * See <a href="http://iquilezles.org/www/articles/frustumcorrect/frustumcorrect.htm">iquilezles.org</a> for an examination of this problem.
     * 
     * @param x
     *          the x-coordinate of the sphere's center
     * @param y
     *          the y-coordinate of the sphere's center
     * @param z
     *          the z-coordinate of the sphere's center
     * @param r
     *          the sphere's radius
     * @return <code>true</code> if the given sphere is partly or completely inside the clipping frustum;
     *         <code>false</code> otherwise
     */
    public boolean isSphereInsideFrustum(float x, float y, float z, float r) {
        return nxX * x + nxY * y + nxZ * z + nxW >= -r &&
               pxX * x + pxY * y + pxZ * z + pxW >= -r &&
               nyX * x + nyY * y + nyZ * z + nyW >= -r &&
               pyX * x + pyY * y + pyZ * z + pyW >= -r &&
               nzX * x + nzY * y + nzZ * z + nzW >= -r &&
               pzX * x + pzY * y + pzZ * z + pzW >= -r;
    }

    /**
     * Determine whether the given axis-aligned box is partly or completely within the viewing frustum defined by <code>this</code> frustum culler
     * and, if the box is not inside this frustum, return the index of the plane that culled it.
     * The box is specified via its <code>min</code> and <code>max</code> corner coordinates.
     * <p>
     * The algorithm implemented by this method is conservative. This means that in certain circumstances a <i>false positive</i>
     * can occur, when the method returns <tt>true</tt> for boxes that are actually not visible.
     * See <a href="http://iquilezles.org/www/articles/frustumcorrect/frustumcorrect.htm">iquilezles.org</a> for an examination of this problem.
     * 
     * @param min
     *          the minimum corner coordinates of the axis-aligned box
     * @param max
     *          the maximum corner coordinates of the axis-aligned box
     * @return the index of the first plane that culled the box, if the box does not intersect the frustum;
     *         or <tt>-1</tt> if the box intersects the frustum.
     *         The plane index is one of
     *         {@link #PLANE_NX}, {@link #PLANE_PX},
     *         {@link #PLANE_NY}, {@link #PLANE_PY},
     *         {@link #PLANE_NZ} and {@link #PLANE_PZ}
     */
    public int isAabInsideFrustum(Vector3f min, Vector3f max) {
        return isAabInsideFrustum(min.x, min.y, min.z, max.x, max.y, max.z);
    }

    /**
     * Determine whether the given axis-aligned box is partly or completely within the viewing frustum defined by <code>this</code> frustum culler
     * and, if the box is not inside this frustum, return the index of the plane that culled it.
     * The box is specified via its min and max corner coordinates.
     * <p>
     * The algorithm implemented by this method is conservative. This means that in certain circumstances a <i>false positive</i>
     * can occur, when the method returns <tt>true</tt> for boxes that are actually not visible.
     * See <a href="http://iquilezles.org/www/articles/frustumcorrect/frustumcorrect.htm">iquilezles.org</a> for an examination of this problem.
     * <p>
     * Reference: <a href="http://www.cescg.org/CESCG-2002/DSykoraJJelinek/">Efficient View Frustum Culling</a>
     * 
     * @param minX
     *          the x-coordinate of the minimum corner
     * @param minY
     *          the y-coordinate of the minimum corner
     * @param minZ
     *          the z-coordinate of the minimum corner
     * @param maxX
     *          the x-coordinate of the maximum corner
     * @param maxY
     *          the y-coordinate of the maximum corner
     * @param maxZ
     *          the z-coordinate of the maximum corner
     * @return the index of the first plane that culled the box, if the box does not intersect the frustum;
     *         or <tt>-1</tt> if the box intersects the frustum.
     *         The plane index is one of
     *         {@link #PLANE_NX}, {@link #PLANE_PX},
     *         {@link #PLANE_NY}, {@link #PLANE_PY},
     *         {@link #PLANE_NZ} and {@link #PLANE_PZ}
     */
    public int isAabInsideFrustum(float minX, float minY, float minZ, float maxX, float maxY, float maxZ) {
        /*
         * This is an implementation of the "2.4 Basic intersection test" of the mentioned site.
         * It does not distinguish between partially inside and fully inside, though, so the test with the 'p' vertex is omitted.
         * 
         * In addition to the algorithm in the paper, this method also returns the index of the first plane that culled the box
         * or -1 if the box intersects the frustum.
         */
        int plane = 0;
        if (nxX * (nxX < 0 ? minX : maxX) + nxY * (nxY < 0 ? minY : maxY) + nxZ * (nxZ < 0 ? minZ : maxZ) >= -nxW && ++plane != 0 &&
            pxX * (pxX < 0 ? minX : maxX) + pxY * (pxY < 0 ? minY : maxY) + pxZ * (pxZ < 0 ? minZ : maxZ) >= -pxW && ++plane != 0 &&
            nyX * (nyX < 0 ? minX : maxX) + nyY * (nyY < 0 ? minY : maxY) + nyZ * (nyZ < 0 ? minZ : maxZ) >= -nyW && ++plane != 0 &&
            pyX * (pyX < 0 ? minX : maxX) + pyY * (pyY < 0 ? minY : maxY) + pyZ * (pyZ < 0 ? minZ : maxZ) >= -pyW && ++plane != 0 &&
            nzX * (nzX < 0 ? minX : maxX) + nzY * (nzY < 0 ? minY : maxY) + nzZ * (nzZ < 0 ? minZ : maxZ) >= -nzW && ++plane != 0 &&
            pzX * (pzX < 0 ? minX : maxX) + pzY * (pzY < 0 ? minY : maxY) + pzZ * (pzZ < 0 ? minZ : maxZ) >= -pzW)
            return -1;
        return plane;
    }

    /**
     * Determine whether the given axis-aligned box is partly or completely within the viewing frustum defined by <code>this</code> frustum culler
     * and, if the box is not inside this frustum, return the index of the plane that culled it.
     * The box is specified via its <code>min</code> and <code>max</code> corner coordinates.
     * <p>
     * This method differs from {@link #isAabInsideFrustum(Vector3f, Vector3f) isAabInsideFrustum()} in that
     * it allows to mask-off planes that should not be calculated. For example, in order to only test a box against the
     * left frustum plane, use a mask of {@link #PLANE_MASK_NX}. Or in order to test all planes <i>except</i> the left plane, use 
     * a mask of <tt>(~0 ^ PLANE_MASK_NX)</tt>.
     * <p>
     * The algorithm implemented by this method is conservative. This means that in certain circumstances a <i>false positive</i>
     * can occur, when the method returns <tt>true</tt> for boxes that are actually not visible.
     * See <a href="http://iquilezles.org/www/articles/frustumcorrect/frustumcorrect.htm">iquilezles.org</a> for an examination of this problem.
     * 
     * @param min
     *          the minimum corner coordinates of the axis-aligned box
     * @param max
     *          the maximum corner coordinates of the axis-aligned box
     * @param mask
     *          contains as bitset all the planes that should be tested.
     *          This value can be any combination of 
     *          {@link #PLANE_MASK_NX}, {@link #PLANE_MASK_PY},
     *          {@link #PLANE_MASK_NY}, {@link #PLANE_MASK_PY}, 
     *          {@link #PLANE_MASK_NZ} and {@link #PLANE_MASK_PZ}
     * @return the index of the first plane that culled the box, if the box does not intersect the frustum;
     *         or <tt>-1</tt> if the box intersects the frustum.
     *         The plane index is one of
     *         {@link #PLANE_NX}, {@link #PLANE_PX},
     *         {@link #PLANE_NY}, {@link #PLANE_PY},
     *         {@link #PLANE_NZ} and {@link #PLANE_PZ}
     */
    public int isAabInsideFrustumMasked(Vector3f min, Vector3f max, int mask) {
        return isAabInsideFrustumMasked(min.x, min.y, min.z, max.x, max.y, max.z, mask);
    }

    /**
     * Determine whether the given axis-aligned box is partly or completely within the viewing frustum defined by <code>this</code> frustum culler
     * and, if the box is not inside this frustum, return the index of the plane that culled it.
     * The box is specified via its min and max corner coordinates.
     * <p>
     * This method differs from {@link #isAabInsideFrustum(float, float, float, float, float, float) isAabInsideFrustum()} in that
     * it allows to mask-off planes that should not be calculated. For example, in order to only test a box against the
     * left frustum plane, use a mask of {@link #PLANE_MASK_NX}. Or in order to test all planes <i>except</i> the left plane, use 
     * a mask of <tt>(~0 ^ PLANE_MASK_NX)</tt>.
     * <p>
     * The algorithm implemented by this method is conservative. This means that in certain circumstances a <i>false positive</i>
     * can occur, when the method returns <tt>true</tt> for boxes that are actually not visible.
     * See <a href="http://iquilezles.org/www/articles/frustumcorrect/frustumcorrect.htm">iquilezles.org</a> for an examination of this problem.
     * <p>
     * Reference: <a href="http://www.cescg.org/CESCG-2002/DSykoraJJelinek/">Efficient View Frustum Culling</a>
     * 
     * @param minX
     *          the x-coordinate of the minimum corner
     * @param minY
     *          the y-coordinate of the minimum corner
     * @param minZ
     *          the z-coordinate of the minimum corner
     * @param maxX
     *          the x-coordinate of the maximum corner
     * @param maxY
     *          the y-coordinate of the maximum corner
     * @param maxZ
     *          the z-coordinate of the maximum corner
     * @param mask
     *          contains as bitset all the planes that should be tested.
     *          This value can be any combination of 
     *          {@link #PLANE_MASK_NX}, {@link #PLANE_MASK_PY},
     *          {@link #PLANE_MASK_NY}, {@link #PLANE_MASK_PY}, 
     *          {@link #PLANE_MASK_NZ} and {@link #PLANE_MASK_PZ}
     * @return the index of the first plane that culled the box, if the box does not intersect the frustum;
     *         or <tt>-1</tt> if the box intersects the frustum.
     *         The plane index is one of
     *         {@link #PLANE_NX}, {@link #PLANE_PX},
     *         {@link #PLANE_NY}, {@link #PLANE_PY},
     *         {@link #PLANE_NZ} and {@link #PLANE_PZ}
     */
    public int isAabInsideFrustumMasked(float minX, float minY, float minZ, float maxX, float maxY, float maxZ, int mask) {
        /*
         * This is an implementation of the "2.5 Plane masking and coherency" of the mentioned site.
         * It does not distinguish between partially inside and fully inside, though, so the test with the 'p' vertex is omitted.
         * 
         * In addition to the algorithm in the paper, this method also returns the index of the first plane that culled the box
         * or -1 if the box intersects the frustum.
         */
        int plane = 0;
        if (((mask & PLANE_MASK_NX) == 0 || nxX * (nxX < 0 ? minX : maxX) + nxY * (nxY < 0 ? minY : maxY) + nxZ * (nxZ < 0 ? minZ : maxZ) >= -nxW) && ++plane != 0 &&
            ((mask & PLANE_MASK_PX) == 0 || pxX * (pxX < 0 ? minX : maxX) + pxY * (pxY < 0 ? minY : maxY) + pxZ * (pxZ < 0 ? minZ : maxZ) >= -pxW) && ++plane != 0 &&
            ((mask & PLANE_MASK_NY) == 0 || nyX * (nyX < 0 ? minX : maxX) + nyY * (nyY < 0 ? minY : maxY) + nyZ * (nyZ < 0 ? minZ : maxZ) >= -nyW) && ++plane != 0 &&
            ((mask & PLANE_MASK_PY) == 0 || pyX * (pyX < 0 ? minX : maxX) + pyY * (pyY < 0 ? minY : maxY) + pyZ * (pyZ < 0 ? minZ : maxZ) >= -pyW) && ++plane != 0 &&
            ((mask & PLANE_MASK_NZ) == 0 || nzX * (nzX < 0 ? minX : maxX) + nzY * (nzY < 0 ? minY : maxY) + nzZ * (nzZ < 0 ? minZ : maxZ) >= -nzW) && ++plane != 0 &&
            ((mask & PLANE_MASK_PZ) == 0 || pzX * (pzX < 0 ? minX : maxX) + pzY * (pzY < 0 ? minY : maxY) + pzZ * (pzZ < 0 ? minZ : maxZ) >= -pzW))
            return -1;
        return plane;
    }

}
