/*
 * (C) Copyright 2016 Kai Burjack

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

import java.util.Comparator;

/**
 * Transforms the trapezoid of a given view-projection frustum as seen by a
 * directional light (i.e. ortographic view transformation) to the unit cube.
 * <p>
 * This calculation step is necessary when using <a href="http://www.comp.nus.edu.sg/~tants/tsm.html">Trapezoidal Shadow Maps</a>.
 * <p>
 * The algorithm implemented by this class is described in the paper
 * <a href="https://kenai.com/downloads/wpbdc/Documents/tsm.pdf">Notes On Implementation Of Trapezoidal Shadow Maps</a>.
 * 
 * @author Kai Burjack
 */
public class TrapezoidOrthoCrop {

    /**
     * Sort by x asc, y asc.
     */
    private static final class PointComparator implements Comparator {
        PointComparator() {}
        public int compare(Object a, Object b) {
            Vector2f p1 = (Vector2f) a;
            Vector2f p2 = (Vector2f) b;
            if (p1.x == p2.x)
                return p1.y > p2.y ? 1 : p1.y < p2.y ? -1 : 0;
            return p1.x > p2.x ? 1 : p1.x < p2.x ? -1 : 0;
        }
    }

    /**
     * Comparator for Vector2f points.
     */
    private static final PointComparator cmp = new PointComparator();

    /**
     * Frustum corners of the camera when projected via the light's ortographic view.
     */
    private final Vector2f[] projectedFrustumCorners = new Vector2f[8];
    {
        for (int i = 0; i < 8; i++)
            projectedFrustumCorners[i] = new Vector2f();
    }

    /**
     * The convex hull of {@link #projectedFrustumCorners}.
     */
    private final Vector2f[] convexHull = new Vector2f[16];
    /**
     * The number of points actually used in {@link #convexHull}.
     */
    private int convexHullSize;

    /* The centroid of the near plane in light/view space */
    private float Nx, Ny;
    /* The centroid of the far plane in light/view space */
    private float Fx, Fy;

    /**
     * The camera's inverse view-projection transformation.
     */
    private final Matrix4f invCamViewProj = new Matrix4f();

    /**
     * Compute the trapezoidal transformation based on the given camera view-projection and light view transformation
     * as well as the given <i>focus frustum</i> distance <code>delta</code> and store the resulting matrix into <code>dest</code>.
     * <p>
     * The transformation matrix computed by this method can be applied to coordinates in light's view-space to obtain the 
     * coordinates on trapezoidal texture space.
     * 
     * @param camViewProj
     *          the new view-projection transformation of a typical perspective camera
     * @param lightView
     *          the new view transformation of a directional light
     * @param delta
     *          the distance from the camera's frustum near plane which specifies the are to map to 80% of the space
     *          after applying the trapezoidal crop matrix computed by this method
     * @param dest
     *          will hold the computed transformation matrix
     * @return dest
     */
    public Matrix4f compute(Matrix4f camViewProj, Matrix4f lightView, float delta, Matrix4f dest) {
        camViewProj.invert(invCamViewProj);
        projectFrustumCorners(lightView);
        computeConvexHull();
        computeMatrix(delta, dest);
        return dest;
    }

    /**
     * Compute the trapezoid and the final transformation matrix.
     * 
     * References:
     * <ul>
     * <li><a href="https://kenai.com/downloads/wpbdc/Documents/tsm.pdf">Notes On Implementation Of Trapezoidal Shadow Maps</a>
     * <li><a href="http://www.comp.nus.edu.sg/~tants/tsm/tsm.pdf">Anti-aliasing and Continuity with Trapezoidal Shadow Maps</a>
     * </ul>
     */
    private void computeMatrix(float delta, Matrix4f dest) {
        float aX = Fx - Nx, aY = Fy - Ny;
        float invLen = 1.0f / (float) Math.sqrt(aX * aX + aY * aY);
        aX *= invLen; aY *= invLen;
        float tTop = 0.0f, tBase = 0.0f;
        // Compute the distances from N to the top and base lines of the trapezoid
        for (int i = 0; i < convexHullSize; i++) {
            float x = convexHull[i].x - Nx;
            float y = convexHull[i].y - Ny;
            float ti = (x * aX) + (y * aY);
            tTop = tTop < ti ? tTop : ti;
            tBase = tBase > ti ? tTop : ti;
        }
        // use the 80% rule to find Q (the center of projection)
        float lambda = tBase - tTop;
        float xi = -0.6f; // <- see original TSM paper section 6.2
        float eta = (lambda * delta + lambda * delta * xi) / (lambda - 2 * delta - lambda * xi);
        float qX = Nx + (tTop - eta) * aX;
        float qY = Ny + (tTop - eta) * aY;
        // Now find the left-most and right-most corners of the convex hull to build the trapezoid
        float maxS = -Float.MAX_VALUE, minS = Float.MAX_VALUE;
        float uLx = 0.0f, uLy = 0.0f;
        float uRx = 0.0f, uRy = 0.0f;
        for (int i = 0; i < convexHullSize; i++) {
            float uX = convexHull[i].x - qX;
            float uY = convexHull[i].y - qY;
            invLen = 1.0f / (float) Math.sqrt(uX * uX + uY * uY);
            uX *= invLen; uY *= invLen;
            float aPerpX = -aY, aPerpY =  aX;
            float si = aPerpX * uX + aPerpY * uY;
            if (si > maxS) {
                maxS = si;
                uLx = uX;
                uLy = uY;
            }
            if (si < minS) {
                minS = si;
                uRx = uX;
                uRy = uY;
            }
        }
        // Compute the four trapezoid corners (2 near and 2 far)
        float tB = eta + lambda;
        float tT = eta;
        float p3x = qX + tB / (uLx * aX + uLy * aY) * uLx;
        float p3y = qY + tB / (uLx * aX + uLy * aY) * uLy;
        float p2x = qX + tB / (uRx * aX + uRy * aY) * uRx;
        float p2y = qY + tB / (uRx * aX + uRy * aY) * uRy;
        float p1x = qX + tT / (uRx * aX + uRy * aY) * uRx;
        float p1y = qY + tT / (uRx * aX + uRy * aY) * uRy;
        float p0x = qX + tT / (uLx * aX + uLy * aY) * uLx;
        float p0y = qY + tT / (uLx * aX + uLy * aY) * uLy;
        // Compute final transformation matrix
        float m00 = aY;
        float m10 = -aX;
        float m20 = aX * p0y - aY * p0x;
        float m01 = aX;
        float m11 = aY;
        float m21 = -(aX * p0x + aY * p0y);
        float c3x = m00 * p3x + m10 * p3y + m20;
        float c3y = m01 * p3x + m11 * p3y + m21;
        float s = -c3x / c3y;
        m00 += s * m01;
        m10 += s * m11;
        m20 += s * m21;
        float d1x = m00 * p1x + m10 * p1y + m20;
        float d2x = m00 * p2x + m10 * p2y + m20;
        float d = d1x * c3y / (d2x - d1x);
        m21 += d;
        float sx = 1.0f / d2x;
        float sy = 1.0f / (c3y + d);
        float u = 2.0f * sy * d / (1.0f - sy * d);
        float m02 = m01 * sy;
        float m12 = m11 * sy;
        float m22 = m21 * sy;
        m01 = (u + 1.0f) * m02;
        m11 = (u + 1.0f) * m12;
        m21 = (u + 1.0f) * m22 - u;
        m00 = 2.0f * sx * m00 - m02;
        m10 = 2.0f * sx * m10 - m12;
        m20 = 2.0f * sx * m20 - m22;
        dest.set(m00, m01, 0, m02,
                 m10, m11, 0, m12,
                   0,   0, 1,   0,
                 m20, m21, 0, m22);
    }

    /**
     * Unproject NDC frustum corners to world-space and then project it back with an orthographic projection
     * using the given <code>view</code> matrix.
     */
    private void projectFrustumCorners(Matrix4f view) {
        for (int t = 0; t < 8; t++) {
            float x = ((t & 1) << 1) - 1.0f;
            float y = (((t >>> 1) & 1) << 1) - 1.0f;
            float z = (((t >>> 2) & 1) << 1) - 1.0f;
            float invW = 1.0f / (invCamViewProj.m03 * x + invCamViewProj.m13 * y + invCamViewProj.m23 * z + invCamViewProj.m33);
            float wx = (invCamViewProj.m00 * x + invCamViewProj.m10 * y + invCamViewProj.m20 * z + invCamViewProj.m30) * invW;
            float wy = (invCamViewProj.m01 * x + invCamViewProj.m11 * y + invCamViewProj.m21 * z + invCamViewProj.m31) * invW;
            float wz = (invCamViewProj.m02 * x + invCamViewProj.m12 * y + invCamViewProj.m22 * z + invCamViewProj.m32) * invW;
            invW = 1.0f / (view.m03 * wx + view.m13 * wy + view.m23 * wz + view.m33);
            float pvx = view.m00 * wx + view.m10 * wy + view.m20 * wz + view.m30;
            float pvy = view.m01 * wx + view.m11 * wy + view.m21 * wz + view.m31;
            projectedFrustumCorners[t].set(pvx, pvy);
        }
        Fx = Fy = Nx = Ny = 0.0f;
        for (int i = 0; i < 4; i++) {
            Nx += projectedFrustumCorners[i].x;
            Ny += projectedFrustumCorners[i].y;
            Fx += projectedFrustumCorners[i+4].x;
            Fy += projectedFrustumCorners[i+4].y;
        }
        Fx *= 0.25f;
        Fy *= 0.25f;
        Nx *= 0.25f;
        Ny *= 0.25f;
    }

    /**
     * Compute the convex hull of the view-projected camera frustum corners.
     * 
     * Reference: http://www.algorithmist.com/index.php/Monotone_Chain_Convex_Hull.java
     */
    private void computeConvexHull() {
        java.util.Arrays.sort(projectedFrustumCorners, cmp);
        int k = 0;
        int start = 0;
        for (int i = 0; i < 8; i++) {
            Vector2f p = projectedFrustumCorners[i];
            while (k - start >= 2) {
                float x1 = p.x - convexHull[k-1].x;
                float y1 = p.y - convexHull[k-1].y;
                float x2 = p.x - convexHull[k-2].x;
                float y2 = p.y - convexHull[k-2].y;
                float c = x1 * y2 - y1 * x2;
                if (c < 0)
                    break;
                k--;
            }
            convexHull[k++] = p;
        }
        k--;
        start = k;
        for (int i = 7; i >= 0; i--) {
            Vector2f p = projectedFrustumCorners[i];
            while (k - start >= 2) {
                float x1 = p.x - convexHull[k-1].x;
                float y1 = p.y - convexHull[k-1].y;
                float x2 = p.x - convexHull[k-2].x;
                float y2 = p.y - convexHull[k-2].y;
                float c = x1 * y2 - y1 * x2;
                if (c < 0)
                    break;
                k--;
            }
            convexHull[k++] = p;
        }
        k--;
        convexHullSize = k;
    }

}
