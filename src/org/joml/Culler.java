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
public class Culler {

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
    public static final int PLANE_MASK_NX = 1<<0;
    /**
     * The value in a bitmask for
     * {@link #isAabInsideFrustumMasked(float, float, float, float, float, float, int) isAabInsideFrustumMasked()}
     * that identifies the plane with equation <tt>x=1</tt> when using the identity frustum.
     */
    public static final int PLANE_MASK_PX = 1<<1;
    /**
     * The value in a bitmask for
     * {@link #isAabInsideFrustumMasked(float, float, float, float, float, float, int) isAabInsideFrustumMasked()}
     * that identifies the plane with equation <tt>y=-1</tt> when using the identity frustum.
     */
    public static final int PLANE_MASK_NY = 1<<2;
    /**
     * The value in a bitmask for
     * {@link #isAabInsideFrustumMasked(float, float, float, float, float, float, int) isAabInsideFrustumMasked()}
     * that identifies the plane with equation <tt>y=1</tt> when using the identity frustum.
     */
    public static final int PLANE_MASK_PY = 1<<3;
    /**
     * The value in a bitmask for
     * {@link #isAabInsideFrustumMasked(float, float, float, float, float, float, int) isAabInsideFrustumMasked()}
     * that identifies the plane with equation <tt>z=-1</tt> when using the identity frustum.
     */
    public static final int PLANE_MASK_NZ = 1<<4;
    /**
     * The value in a bitmask for
     * {@link #isAabInsideFrustumMasked(float, float, float, float, float, float, int) isAabInsideFrustumMasked()}
     * that identifies the plane with equation <tt>z=1</tt> when using the identity frustum.
     */
    public static final int PLANE_MASK_PZ = 1<<5;

    private final Vector4f nx = new Vector4f();
    private final Vector4f px = new Vector4f();
    private final Vector4f ny = new Vector4f();
    private final Vector4f py = new Vector4f();
    private final Vector4f nz = new Vector4f();
    private final Vector4f pz = new Vector4f();

    /**
     * Create a new {@link Culler} from the given {@link Matrix4f matrix} by extracing the matrix's frustum planes.
     * 
     * @param m
     *          the {@link Matrix4f} to create the culler from
     */
    public Culler(Matrix4f m) {
        nx.set(m.m03 + m.m00, m.m13 + m.m10, m.m23 + m.m20, m.m33 + m.m30).normalize3();
        px.set(m.m03 - m.m00, m.m13 - m.m10, m.m23 - m.m20, m.m33 - m.m30).normalize3();
        ny.set(m.m03 + m.m01, m.m13 + m.m11, m.m23 + m.m21, m.m33 + m.m31).normalize3();
        py.set(m.m03 - m.m01, m.m13 - m.m11, m.m23 - m.m21, m.m33 - m.m31).normalize3();
        nz.set(m.m03 + m.m02, m.m13 + m.m12, m.m23 + m.m22, m.m33 + m.m32).normalize3();
        pz.set(m.m03 - m.m02, m.m13 - m.m12, m.m23 - m.m22, m.m33 - m.m32).normalize3();
    }

    /**
     * Update the stored frustum planes of <code>this</code> {@link Culler} with the given {@link Matrix4f matrix}.
     * 
     * @param m
     *          the {@link Matrix4f matrix} to update <code>this</code> culler's frustum planes from
     * @return this
     */
    public Culler set(Matrix4f m) {
        nx.set(m.m03 + m.m00, m.m13 + m.m10, m.m23 + m.m20, m.m33 + m.m30).normalize3();
        px.set(m.m03 - m.m00, m.m13 - m.m10, m.m23 - m.m20, m.m33 - m.m30).normalize3();
        ny.set(m.m03 + m.m01, m.m13 + m.m11, m.m23 + m.m21, m.m33 + m.m31).normalize3();
        py.set(m.m03 - m.m01, m.m13 - m.m11, m.m23 - m.m21, m.m33 - m.m31).normalize3();
        nz.set(m.m03 + m.m02, m.m13 + m.m12, m.m23 + m.m22, m.m33 + m.m32).normalize3();
        pz.set(m.m03 - m.m02, m.m13 - m.m12, m.m23 - m.m22, m.m33 - m.m32).normalize3();
        return this;
    }

    /**
     * Determine whether the given point is within the viewing frustum
     * defined by <code>this</code> culler.
     * 
     * @param point
     *          the point to test
     * @return <code>true</code> if the given point is inside the clipping frustum; <code>false</code> otherwise
     */
    public boolean isPointInsideFrustum(Vector3f point) {
        return isPointInsideFrustum(point.x, point.y, point.z);
    }

    /**
     * Determine whether the given point <tt>(x, y, z)</tt> is within the viewing frustum defined by <code>this</code> culler.
     * <p>
     * Reference: <a href="http://www.cs.otago.ac.nz/postgrads/alexis/planeExtraction.pdf">
     * Fast Extraction of Viewing Frustum Planes from the World-View-Projection Matrix</a>
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
        return (nx.x * x + nx.y * y + nx.z * z + nx.w >= 0 &&
                px.x * x + px.y * y + px.z * z + px.w >= 0 &&
                ny.x * x + ny.y * y + ny.z * z + ny.w >= 0 &&
                py.x * x + py.y * y + py.z * z + py.w >= 0 &&
                nz.x * x + nz.y * y + nz.z * z + nz.w >= 0 &&
                pz.x * x + pz.y * y + pz.z * z + pz.w >= 0);
    }

    /**
     * Determine whether the given sphere is partly or completely within the viewing frustum defined by <code>this</code> culler.
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
     * Determine whether the given sphere is partly or completely within the viewing frustum defined by <code>this</code> culler.
     * <p>
     * Reference: <a href="http://www.cs.otago.ac.nz/postgrads/alexis/planeExtraction.pdf">
     * Fast Extraction of Viewing Frustum Planes from the World-View-Projection Matrix</a>
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
        return nx.x * x + nx.y * y + nx.z * z + nx.w >= -r &&
               px.x * x + px.y * y + px.z * z + px.w >= -r &&
               ny.x * x + ny.y * y + ny.z * z + ny.w >= -r &&
               py.x * x + py.y * y + py.z * z + py.w >= -r &&
               nz.x * x + nz.y * y + nz.z * z + nz.w >= -r &&
               pz.x * x + pz.y * y + pz.z * z + pz.w >= -r;
    }

    /**
     * Determine whether the given axis-aligned box is partly or completely within the viewing frustum defined by <code>this</code> culler
     * and, if the box is not inside this frustum, return the index of the plane that culled it.
     * The box is specified via its <code>min</code> and <code>max</code> corner coordinates.
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
     * Determine whether the given axis-aligned box is partly or completely within the viewing frustum defined by <code>this</code> culler
     * and, if the box is not inside this frustum, return the index of the plane that culled it.
     * The box is specified via its min and max corner coordinates.
     * <p>
     * Reference: <a href="http://www.cescg.org/CESCG-2002/DSykoraJJelinek/">Efficient View Frustum Culling</a>
     * <p>
     * Reference: <a href="http://www.cs.otago.ac.nz/postgrads/alexis/planeExtraction.pdf">
     * Fast Extraction of Viewing Frustum Planes from the World-View-Projection Matrix</a>
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
        if (nx.x * (nx.x < 0 ? minX : maxX) + nx.y * (nx.y < 0 ? minY : maxY) + nx.z * (nx.z < 0 ? minZ : maxZ) >= -nx.w && ++plane != 0 &&
            px.x * (px.x < 0 ? minX : maxX) + px.y * (px.y < 0 ? minY : maxY) + px.z * (px.z < 0 ? minZ : maxZ) >= -px.w && ++plane != 0 &&
            ny.x * (ny.x < 0 ? minX : maxX) + ny.y * (ny.y < 0 ? minY : maxY) + ny.z * (ny.z < 0 ? minZ : maxZ) >= -ny.w && ++plane != 0 &&
            py.x * (py.x < 0 ? minX : maxX) + py.y * (py.y < 0 ? minY : maxY) + py.z * (py.z < 0 ? minZ : maxZ) >= -py.w && ++plane != 0 &&
            nz.x * (nz.x < 0 ? minX : maxX) + nz.y * (nz.y < 0 ? minY : maxY) + nz.z * (nz.z < 0 ? minZ : maxZ) >= -nz.w && ++plane != 0 &&
            pz.x * (pz.x < 0 ? minX : maxX) + pz.y * (pz.y < 0 ? minY : maxY) + pz.z * (pz.z < 0 ? minZ : maxZ) >= -pz.w)
            return -1;
        return plane;
    }

    /**
     * Determine whether the given axis-aligned box is partly or completely within the viewing frustum defined by <code>this</code> culler
     * and, if the box is not inside this frustum, return the index of the plane that culled it.
     * The box is specified via its <code>min</code> and <code>max</code> corner coordinates.
     * <p>
     * This method differs from {@link #isAabInsideFrustum(Vector3f, Vector3f) isAabInsideFrustum()} in that
     * it allows to mask-off planes that should not be calculated. For example, in order to only test a box against the
     * left frustum plane, use a mask of {@link #PLANE_MASK_NX}. Or in order to test all planes <i>except</i> the left plane, use 
     * a mask of <tt>(~0 ^ PLANE_MASK_NX)</tt>.
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
     * Determine whether the given axis-aligned box is partly or completely within the viewing frustum defined by <code>this</code> culler
     * and, if the box is not inside this frustum, return the index of the plane that culled it.
     * The box is specified via its min and max corner coordinates.
     * <p>
     * This method differs from {@link #isAabInsideFrustum(float, float, float, float, float, float) isAabInsideFrustum()} in that
     * it allows to mask-off planes that should not be calculated. For example, in order to only test a box against the
     * left frustum plane, use a mask of {@link #PLANE_MASK_NX}. Or in order to test all planes <i>except</i> the left plane, use 
     * a mask of <tt>(~0 ^ PLANE_MASK_NX)</tt>.
     * <p>
     * Reference: <a href="http://www.cescg.org/CESCG-2002/DSykoraJJelinek/">Efficient View Frustum Culling</a>
     * <p>
     * Reference: <a href="http://www.cs.otago.ac.nz/postgrads/alexis/planeExtraction.pdf">
     * Fast Extraction of Viewing Frustum Planes from the World-View-Projection Matrix</a>
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
        if (((mask & Matrix4f.PLANE_MASK_NX) == 0 || nx.x * (nx.x < 0 ? minX : maxX) + nx.y * (nx.y < 0 ? minY : maxY) + nx.z * (nx.z < 0 ? minZ : maxZ) >= -nx.w) && ++plane != 0 &&
            ((mask & Matrix4f.PLANE_MASK_PX) == 0 || px.x * (px.x < 0 ? minX : maxX) + px.y * (px.y < 0 ? minY : maxY) + px.z * (px.z < 0 ? minZ : maxZ) >= -px.w) && ++plane != 0 &&
            ((mask & Matrix4f.PLANE_MASK_NY) == 0 || ny.x * (ny.x < 0 ? minX : maxX) + ny.y * (ny.y < 0 ? minY : maxY) + ny.z * (ny.z < 0 ? minZ : maxZ) >= -ny.w) && ++plane != 0 &&
            ((mask & Matrix4f.PLANE_MASK_PY) == 0 || py.x * (py.x < 0 ? minX : maxX) + py.y * (py.y < 0 ? minY : maxY) + py.z * (py.z < 0 ? minZ : maxZ) >= -py.w) && ++plane != 0 &&
            ((mask & Matrix4f.PLANE_MASK_NZ) == 0 || nz.x * (nz.x < 0 ? minX : maxX) + nz.y * (nz.y < 0 ? minY : maxY) + nz.z * (nz.z < 0 ? minZ : maxZ) >= -nz.w) && ++plane != 0 &&
            ((mask & Matrix4f.PLANE_MASK_PZ) == 0 || pz.x * (pz.x < 0 ? minX : maxX) + pz.y * (pz.y < 0 ? minY : maxY) + pz.z * (pz.z < 0 ? minZ : maxZ) >= -pz.w))
            return -1;
        return plane;
    }

}
