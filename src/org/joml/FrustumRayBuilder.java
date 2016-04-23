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
 * Provides methods to compute rays through an arbitrary perspective transformation defined by a {@link Matrix4f}.
 * <p>
 * This can be used to compute the eye-rays in simple software-based raycasting/raytracing.
 * <p>
 * To obtain the origin of the rays call {@link #origin(Vector3f)}.
 * Then to compute the directions of subsequent rays use {@link #dir(float, float, Vector3f)}.
 * 
 * @author Kai Burjack
 */
public class FrustumRayBuilder {

    private float nxnyX, nxnyY, nxnyZ;
    private float pxnyX, pxnyY, pxnyZ;
    private float pxpyX, pxpyY, pxpyZ;
    private float nxpyX, nxpyY, nxpyZ;
    private float cx, cy, cz;

    /**
     * Create a new {@link FrustumRayBuilder} with an undefined frustum.
     * <p>
     * Before obtaining ray directions, make sure to define the frustum using {@link #set(Matrix4f)}.
     */
    public FrustumRayBuilder() {
    }

    /**
     * Create a new {@link FrustumRayBuilder} from the given {@link Matrix4f matrix} by extracing the matrix's frustum.
     * 
     * @param m
     *          the {@link Matrix4f} to create the frustum from
     */
    public FrustumRayBuilder(Matrix4f m) {
        set(m);
    }

    /**
     * Update the stored frustum corner rays and origin of <code>this</code> {@link FrustumRayBuilder} with the given {@link Matrix4f matrix}.
     * <p>
     * Reference: <a href="http://gamedevs.org/uploads/fast-extraction-viewing-frustum-planes-from-world-view-projection-matrix.pdf">
     * Fast Extraction of Viewing Frustum Planes from the World-View-Projection Matrix</a>
     * <p>
     * Reference: <a href="http://geomalgorithms.com/a05-_intersect-1.html">http://geomalgorithms.com</a>
     * 
     * @param m
     *          the {@link Matrix4f matrix} to update the frustum corner rays and origin with
     * @return this
     */
    public FrustumRayBuilder set(Matrix4f m) {
        float nxX = m.ms[Matrix4f.M03] + m.ms[Matrix4f.M00], nxY = m.ms[Matrix4f.M13] + m.ms[Matrix4f.M10], nxZ = m.ms[Matrix4f.M23] + m.ms[Matrix4f.M20], d1 = m.ms[Matrix4f.M33] + m.ms[Matrix4f.M30];
        float pxX = m.ms[Matrix4f.M03] - m.ms[Matrix4f.M00], pxY = m.ms[Matrix4f.M13] - m.ms[Matrix4f.M10], pxZ = m.ms[Matrix4f.M23] - m.ms[Matrix4f.M20], d2 = m.ms[Matrix4f.M33] - m.ms[Matrix4f.M30];
        float nyX = m.ms[Matrix4f.M03] + m.ms[Matrix4f.M01], nyY = m.ms[Matrix4f.M13] + m.ms[Matrix4f.M11], nyZ = m.ms[Matrix4f.M23] + m.ms[Matrix4f.M21];
        float pyX = m.ms[Matrix4f.M03] - m.ms[Matrix4f.M01], pyY = m.ms[Matrix4f.M13] - m.ms[Matrix4f.M11], pyZ = m.ms[Matrix4f.M23] - m.ms[Matrix4f.M21], d3 = m.ms[Matrix4f.M33] - m.ms[Matrix4f.M31];
        // bottom left
        nxnyX = nyY * nxZ - nyZ * nxY;
        nxnyY = nyZ * nxX - nyX * nxZ;
        nxnyZ = nyX * nxY - nyY * nxX;
        // bottom right
        pxnyX = pxY * nyZ - pxZ * nyY;
        pxnyY = pxZ * nyX - pxX * nyZ;
        pxnyZ = pxX * nyY - pxY * nyX;
        // top left
        nxpyX = nxY * pyZ - nxZ * pyY;
        nxpyY = nxZ * pyX - nxX * pyZ;
        nxpyZ = nxX * pyY - nxY * pyX;
        // top right
        pxpyX = pyY * pxZ - pyZ * pxY;
        pxpyY = pyZ * pxX - pyX * pxZ;
        pxpyZ = pyX * pxY - pyY * pxX;
        // compute origin
        float pxnxX, pxnxY, pxnxZ;
        pxnxX = pxY * nxZ - pxZ * nxY;
        pxnxY = pxZ * nxX - pxX * nxZ;
        pxnxZ = pxX * nxY - pxY * nxX;
        float invDot = 1.0f / (nxX * pxpyX + nxY * pxpyY + nxZ * pxpyZ);
        cx = (-pxpyX * d1 - nxpyX * d2 - pxnxX * d3) * invDot;
        cy = (-pxpyY * d1 - nxpyY * d2 - pxnxY * d3) * invDot;
        cz = (-pxpyZ * d1 - nxpyZ * d2 - pxnxZ * d3) * invDot;
        return this;
    }

    /**
     * Store the eye/origin of the perspective frustum in the given <code>origin</code>.
     * 
     * @param origin
     *          will hold the perspective origin
     * @return the <code>origin</code> vector
     */
    public Vector3f origin(Vector3f origin) {
        origin.x = cx;
        origin.y = cy;
        origin.z = cz;
        return origin;
    }

    /**
     * Obtain the normalized direction of a ray starting at the center of the coordinate system and going 
     * through the near frustum plane.
     * <p>
     * The parameters <code>x</code> and <code>y</code> are used to interpolate the generated ray direction
     * from the bottom-left to the top-right frustum corners.
     * 
     * @param x
     *          the interpolation factor along the left-to-right frustum planes, within <tt>[0..1]</tt>
     * @param y
     *          the interpolation factor along the bottom-to-top frustum planes, within <tt>[0..1]</tt>
     * @param dir
     *          will hold the normalized ray direction
     * @return the <code>dir</code> vector
     */
    public Vector3f dir(float x, float y, Vector3f dir) {
        float y1x = nxnyX + (nxpyX - nxnyX) * y;
        float y1y = nxnyY + (nxpyY - nxnyY) * y;
        float y1z = nxnyZ + (nxpyZ - nxnyZ) * y;
        float y2x = pxnyX + (pxpyX - pxnyX) * y;
        float y2y = pxnyY + (pxpyY - pxnyY) * y;
        float y2z = pxnyZ + (pxpyZ - pxnyZ) * y;
        float dx = y1x + (y2x - y1x) * x;
        float dy = y1y + (y2y - y1y) * x;
        float dz = y1z + (y2z - y1z) * x;
        // normalize the vector
        float invLen = (float) (1.0 / Math.sqrt(dx * dx + dy * dy + dz * dz));
        dir.x = dx * invLen;
        dir.y = dy * invLen;
        dir.z = dz * invLen;
        return dir;
    }

}
