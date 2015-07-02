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
 * Useful geometry methods.
 * 
 * @author Kai Burjack
 */
public class GeometryUtils {

    /**
     * Calculate the frustum planes of the given transformation matrix, which
     * can be a projection matrix or a combined modelview-projection matrix.
     * <p>
     * Generally, this method computes the frustum planes in the local frame of
     * any coordinate system that existed before the given <code>mvp</code>
     * transformation was applied to it.
     * <p>
     * Each of the four planes <code>left</code>, <code>right</code>,
     * <code>bottom</code> and <code>top</code> are given in plane equations:
     * <tt>a*x + b*y + c*z + d = 0</tt>, where the {@link Vector4f} components
     * hold the <tt>(a, b, c, d)</tt> values of each plane equation.
     * <p>
     * The plane normals, which are the <tt>(a, b, c)</tt> parameters, are directed "inwards" of the frustum.
     * Any plane/point test using <tt>a*x + b*y + c*z + d</tt> therefore will yield a result greater than zero
     * if the point is within the frustum (i.e. at the <i>positive</i> side of each frustum plane).
     * 
     * @param mvp
     *            the transformation matrix whose frustum planes should be
     *            computed
     * @param left
     *            the plane equation components of the left frustum plane
     * @param right
     *            the plane equation components of the right frustum plane
     * @param bottom
     *            the plane equation components of the bottom frustum plane
     * @param top
     *            the plane equation components of the top frustum plane
     */
    public static void calculateFrustumPlanes(Matrix4f mvp, Vector4f left, Vector4f right, Vector4f bottom, Vector4f top) {
        // "http://web.archive.org/web/20120531231005/http://crazyjoke.free.fr/doc/3D/plane%20extraction.pdf"
        // changed to use OpenGL's right-handed coordinate system
        // (and use column-major matrix indices, which the paper did not use,
        //  although it said it would in the code-section at the end).
        right.set(mvp.m03 - mvp.m00,
                  mvp.m13 - mvp.m10,
                  mvp.m23 - mvp.m20,
                  mvp.m33 - mvp.m30).normalize3();
        left.set(mvp.m03 + mvp.m00,
                 mvp.m13 + mvp.m10,
                 mvp.m23 + mvp.m20,
                 mvp.m33 + mvp.m30).normalize3();
        bottom.set(mvp.m03 + mvp.m01,
                   mvp.m13 + mvp.m11,
                   mvp.m23 + mvp.m21,
                   mvp.m33 + mvp.m31).normalize3();
        top.set(mvp.m03 - mvp.m01,
                mvp.m13 - mvp.m11,
                mvp.m23 - mvp.m21,
                mvp.m33 - mvp.m31).normalize3();
    }

}
