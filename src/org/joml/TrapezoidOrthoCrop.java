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
     * The convex hull of {@link #projectFrustumCorners()}.
     */
    private final Vector2f[] convexHull = new Vector2f[16];
    /**
     * The number of points actually used in {@link #convexHull}.
     */
    private int convexHullSize;

    /**
     * The light's view transformation
     */
    private final Matrix4f lightView = new Matrix4f();

    /**
     * The camera's inverse view-projection transformation.
     */
    private final Matrix4f invCamViewProj = new Matrix4f();

    /**
     * Create a new {@link TrapezoidOrthoCrop} using the given "camera" <code>camViewProj</code> matrix
     * and the specified "light" <code>lightView</code> transformation.
     * 
     * @param camViewProj
     *          the view-projection transformation of a typical perspective camera
     * @param lightView
     *          the view transformation of a directional light
     */
    public TrapezoidOrthoCrop(Matrix4f camViewProj, Matrix4f lightView) {
        update(camViewProj, lightView);
    }

    /**
     * Recompute the trapezoidal transformation based on a new camera view-projection and light view transformation.
     * 
     * @param camViewProj
     *          the new view-projection transformation of a typical perspective camera
     * @param lightView
     *          the new view transformation of a directional light
     * @return this
     */
    public TrapezoidOrthoCrop update(Matrix4f camViewProj, Matrix4f lightView) {
        this.lightView.set(lightView);
        camViewProj.invert(invCamViewProj);
        projectFrustumCorners();
        computeConvexHull();
        return this;
    }

    /**
     * Unproject NDC frustum corners to world-space and then project it back with an orthographic projection
     * using the given <code>view</code> matrix.
     */
    private void projectFrustumCorners() {
        for (int t = 0; t < 8; t++) {
            float x = ((t & 1) << 1) - 1.0f;
            float y = (((t >>> 1) & 1) << 1) - 1.0f;
            float z = (((t >>> 2) & 1) << 1) - 1.0f;
            float invW = 1.0f / (invCamViewProj.m03 * x + invCamViewProj.m13 * y + invCamViewProj.m23 * z + invCamViewProj.m33);
            float wx = (invCamViewProj.m00 * x + invCamViewProj.m10 * y + invCamViewProj.m20 * z + invCamViewProj.m30) * invW;
            float wy = (invCamViewProj.m01 * x + invCamViewProj.m11 * y + invCamViewProj.m21 * z + invCamViewProj.m31) * invW;
            float wz = (invCamViewProj.m02 * x + invCamViewProj.m12 * y + invCamViewProj.m22 * z + invCamViewProj.m32) * invW;
            invW = 1.0f / (lightView.m03 * wx + lightView.m13 * wy + lightView.m23 * wz + lightView.m33);
            float pvx = lightView.m00 * wx + lightView.m10 * wy + lightView.m20 * wz + lightView.m30;
            float pvy = lightView.m01 * wx + lightView.m11 * wy + lightView.m21 * wz + lightView.m31;
            projectedFrustumCorners[t].set(pvx, pvy);
        }
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

    /**
     * Based on the convex hull of the camera's frustum computed by the methods above,
     * now calculate the final trapezoidal crop projection matrix and store the result
     * into <code>dest</code>.
     * 
     * @param dest
     *          will hold the computed trapezoidal projection transformation
     * @return dest
     */
    public Matrix4f calculate(Matrix4f dest) {
        // TODO
        return dest;
    }

}
