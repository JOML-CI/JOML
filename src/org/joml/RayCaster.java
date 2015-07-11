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
 * Provides methods to compute rays through an arbitrary perspective transformation defined by a {@link Matrix4f}.
 * <p>
 * This can be used to compute the eye-rays in simple software-based raycasting/raytracing.
 * <p>
 * To obtain the origin of the rays call {@link #origin(Vector3f)}.
 * Then to compute the directions of subsequent rays {@link #dir(float, float, Vector3f)}.
 * 
 * @author Kai Burjack
 */
public class RayCaster {

    private float nxnyX, nxnyY, nxnyZ;
    private float pxnyX, pxnyY, pxnyZ;
    private float pxpyX, pxpyY, pxpyZ;
    private float nxpyX, nxpyY, nxpyZ;
    private float cx, cy, cz;

    /**
     * Create a new {@link RayCaster} with an undefined frustum.
     * <p>
     * Before obtaining ray directions, make sure to define the frustum using {@link #set(Matrix4f)}.
     */
    public RayCaster() {
    }

    /**
     * Create a new {@link RayCaster} from the given {@link Matrix4f matrix} by extracing the matrix's frustum.
     * 
     * @param m
     *          the {@link Matrix4f} to create the ray caster from
     */
    public RayCaster(Matrix4f m) {
        set(m);
    }

    /**
     * Update the stored frustum corner rays and origin of <code>this</code> {@link RayCaster} with the given {@link Matrix4f matrix}.
     * 
     * @param m
     *          the {@link Matrix4f matrix} to update <code>this</code> ray caster's frustum corner rays and origin with
     * @return this
     */
    public RayCaster set(Matrix4f m) {
        float nxX = m.m03 + m.m00, nxY = m.m13 + m.m10, nxZ = m.m23 + m.m20, d1 = m.m33 + m.m30;
        float pxX = m.m03 - m.m00, pxY = m.m13 - m.m10, pxZ = m.m23 - m.m20, d2 = m.m33 - m.m30;
        float nyX = m.m03 + m.m01, nyY = m.m13 + m.m11, nyZ = m.m23 + m.m21;
        float pyX = m.m03 - m.m01, pyY = m.m13 - m.m11, pyZ = m.m23 - m.m21, d3 = m.m33 - m.m31;
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
        float c12x, c12y, c12z;
        c12x = pxY * nxZ - pxZ * nxY;
        c12y = pxZ * nxX - pxX * nxZ;
        c12z = pxX * nxY - pxY * nxX;
        float dot = nxX * pxpyX + nxY * pxpyY + nxZ * pxpyZ;
        cx = (-pxpyX * d1 - nxpyX * d2 - c12x * d3) / dot;
        cy = (-pxpyY * d1 - nxpyY * d2 - c12y * d3) / dot;
        cz = (-pxpyZ * d1 - nxpyZ * d2 - c12z * d3) / dot;
        return this;
    }

    /**
     * Store the eye/origin of the perspective frustum in the given <code>origin</code>.
     * 
     * @param origin
     *          will hold the perspective origin
     * @return this
     */
    public RayCaster origin(Vector3f origin) {
        origin.x = cx;
        origin.y = cy;
        origin.z = cz;
        return this;
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
     * @return this
     */
    public RayCaster dir(float x, float y, Vector3f dir) {
        float oneMinusY = 1.0f - y, oneMinusX = 1.0f - x;
        float y1x = nxnyX * oneMinusY + nxpyX * y;
        float y1y = nxnyY * oneMinusY + nxpyY * y;
        float y1z = nxnyZ * oneMinusY + nxpyZ * y;
        float y2x = pxnyX * oneMinusY + pxpyX * y;
        float y2y = pxnyY * oneMinusY + pxpyY * y;
        float y2z = pxnyZ * oneMinusY + pxpyZ * y;
        float dx = y1x * oneMinusX + y2x * x;
        float dy = y1y * oneMinusX + y2y * x;
        float dz = y1z * oneMinusX + y2z * x;
        // normalize the vector
        float len = (float) Math.sqrt(dx * dx + dy * dy + dz * dz);
        dx /= len;
        dy /= len;
        dz /= len;
        dir.x = dx;
        dir.y = dy;
        dir.z = dz;
        return this;
    }

}
