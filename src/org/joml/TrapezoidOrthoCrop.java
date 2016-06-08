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
 * <p>
 * PLEASE NOTE THAT THE ALGORITHM IMPLEMENTED BY THIS CLASS <a href="http://www.google.com/patents/US20070040832">IS PATENTED</a>
 * WITH AN ISSUE DATE OF JUL 30TH, 2004 AND WILL REMAIN SO UNTIL JUL 30TH, 2024. THAT MEANS, YOU CAN USE THE ALGORITHM FREELY
 * IN NON-COMMERCIAL APPLICATIONS BUT YOU HAVE TO CONTACT THE PATENT HOLDER AND PAY THEM SOME FEE IF YOU WANT TO USE
 * THE ALGORITHM BEFORE JUL 30TH, 2024 IN COMMERCIAL APPLICATIONS. SEE THE SECOND PARAGRAPH OF <a href="http://www.comp.nus.edu.sg/~tants/tsm.html">THIS SITE</a>
 * UNDER SECTION "ABSTRACT" FOR MORE INFORMATION.
 * 
 * @author Kai Burjack
 */
public class TrapezoidOrthoCrop {

    /**
     * Sort by x asc, y asc.
     */
    private static final class Vector2fComparator implements Comparator {
        Vector2fComparator() {}
        public int compare(Object a, Object b) {
            Vector2f p1 = (Vector2f) a;
            Vector2f p2 = (Vector2f) b;
            if (p1.x == p2.x)
                return p1.y > p2.y ? 1 : p1.y < p2.y ? -1 : 0;
            return p1.x > p2.x ? 1 : p1.x < p2.x ? -1 : 0;
        }
    }

    /**
     *  
     */
    public static float START_PRECENTAGE = 0.95f;

    /**
     *  
     */
    public static float FOCUS_LINE_FRACTION = 0.3f;

    /**
     * Comparator for Vector2f points.
     */
    private static final Vector2fComparator cmp = new Vector2fComparator();

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
    /**
     * Working memory to compute the area of the trapezoidal-projected convex hull.
     */
    private final Vector2f[] projectedConvexHull = new Vector2f[6];
    {
        for (int i = 0; i < 6; i++)
            projectedConvexHull[i] = new Vector2f();
    }

    /* The centroid of the near plane in light/view space */
    private float Nx, Ny;
    /* The centroid of the far plane in light/view space */
    private float Fx, Fy;

    /**
     * The camera's inverse view-projection transformation.
     */
    private final Matrix4f invCamViewProj = new Matrix4f();

    /**
     * Working matrix.
     */
    private final Matrix4f work = new Matrix4f();

    /**
     * Compute the trapezoidal transformation based on the given camera view-projection and light view transformation
     * and store the resulting matrix into <code>dest</code>.
     * <p>
     * The transformation matrix computed by this method can be applied to coordinates in light's view-space to obtain the 
     * coordinates on trapezoidal texture space.
     * 
     * @param camViewProj
     *          the view-projection transformation of a typical perspective camera
     * @param lightView
     *          the view transformation of a directional light
     * @param dest
     *          will hold the computed transformation matrix
     * @return dest
     */
    public Matrix4f compute(Matrix4f camViewProj, Matrix4f lightView, Matrix4f dest) {
        camViewProj.invert(invCamViewProj);
        projectFrustumCorners(lightView);
        computeConvexHull();
        computeMatrix(dest);
        return dest;
    }

    /**
     * Reference: <a href="http://www.mathwords.com/a/area_convex_polygon.htm">http://www.mathwords.com/</a>.
     */
    private static float convexPolygonArea(Vector2f[] vs, int count) {
        float sum0 = 0.0f, sum1 = 0.0f;
        for (int i0 = 0; i0 < count; i0++) {
            int i1 = (i0 + 1) % count;
            Vector2f v0 = vs[i0], v1 = vs[i1];
            sum0 += v0.x * v1.y;
            sum1 += v0.y * v1.x;
        }
        return 0.5f * (sum0 - sum1);
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
    private void computeMatrix(Matrix4f dest) {
        float aX = Fx - Nx, aY = Fy - Ny;
        float aLen = (float) Math.sqrt(aX * aX + aY * aY);
        float invLen = 1.0f / aLen;
        aX *= invLen; aY *= invLen;
        float tTop = 0.0f, tBase = 0.0f;
        // Compute the distances from N to the top and base lines of the trapezoid
        for (int i = 0; i < convexHullSize; i++) {
            float x = convexHull[i].x - Nx;
            float y = convexHull[i].y - Ny;
            float ti = (x * aX) + (y * aY);
            tTop = tTop < ti ? tTop : ti;
            tBase = tBase > ti ? tBase : ti;
        }
        float lambda = tBase - tTop;
        float projectedArea = 0.0f;
        float delta = aLen * FOCUS_LINE_FRACTION - tTop;
        // use the fraction (start with 80%) which makes best use of shadow map area to find Q (the center of projection)
        for (float frac = START_PRECENTAGE; frac > 0.0f; frac -= 0.02f) {
            float xi = 1.0f - 2.0f * frac;
            float eta = (lambda * delta + lambda * delta * xi) / (lambda - 2 * delta - lambda * xi);
            // Compute center of project (Q)
            float qX = Nx + (tTop - eta) * aX;
            float qY = Ny + (tTop - eta) * aY;
            // Find the left-most and right-most corners of the convex hull to build the trapezoid
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
            // Compute the four trapezoid corners in the order expected by Matrix4f.trapezoidCrop()
            float tB = eta + lambda;
            float tT = eta;
            float invULdotA = 1.0f / (uLx * aX + uLy * aY);
            float invURdotA = 1.0f / (uRx * aX + uRy * aY);
            float p3x = qX + tB * invULdotA * uLx;
            float p3y = qY + tB * invULdotA * uLy;
            float p2x = qX + tB * invURdotA * uRx;
            float p2y = qY + tB * invURdotA * uRy;
            float p1x = qX + tT * invURdotA * uRx;
            float p1y = qY + tT * invURdotA * uRy;
            float p0x = qX + tT * invULdotA * uLx;
            float p0y = qY + tT * invULdotA * uLy;
            // Build the TSM matrix
            work.trapezoidCrop(p0x, p0y, p1x, p1y, p2x, p2y, p3x, p3y);
            // Project the convex hull into TSM space
            for (int c = 0; c < convexHullSize; c++) {
                float x = convexHull[c].x;
                float y = convexHull[c].y;
                float invW = 1.0f / (work.m03() * x + work.m13() * y + work.m33());
                float tx = (work.m00() * x + work.m10() * y + work.m30()) * invW;
                float ty = (work.m01() * x + work.m11() * y + work.m31()) * invW;
                projectedConvexHull[c].set(tx, ty);
            }
            // Compute area
            float area = convexPolygonArea(projectedConvexHull, convexHullSize);
            if (area > projectedArea) {
                projectedArea = area;
                dest.set(work);
            } else {
                break;
            }
        }
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
            float invW = 1.0f / (invCamViewProj.m03() * x + invCamViewProj.m13() * y + invCamViewProj.m23() * z + invCamViewProj.m33());
            float wx = (invCamViewProj.m00() * x + invCamViewProj.m10() * y + invCamViewProj.m20() * z + invCamViewProj.m30()) * invW;
            float wy = (invCamViewProj.m01() * x + invCamViewProj.m11() * y + invCamViewProj.m21() * z + invCamViewProj.m31()) * invW;
            float wz = (invCamViewProj.m02() * x + invCamViewProj.m12() * y + invCamViewProj.m22() * z + invCamViewProj.m32()) * invW;
            invW = 1.0f / (view.m03() * wx + view.m13() * wy + view.m23() * wz + view.m33());
            float pvx = view.m00() * wx + view.m10() * wy + view.m20() * wz + view.m30();
            float pvy = view.m01() * wx + view.m11() * wy + view.m21() * wz + view.m31();
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
     * Reference: <a href="http://www.algorithmist.com/index.php/Monotone_Chain_Convex_Hull.java">Monotone_Chain_Convex_Hull.java</a>
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
