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

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the Sutherland-Hodgman algorithm to clip a poylgon by another convex polygon.
 * <p>
 * Reference: <a href="https://rosettacode.org/wiki/Sutherland-Hodgman_polygon_clipping#Java">https://rosettacode.org/</a>
 * 
 * @author Kai Burjack
 */
public class SutherlandHodgman {

    /**
     * Clip the given <code>subject</code> polygon against the given <code>clipper</code> polygon and store the resulting
     * clipped polygon into <code>result</code>.
     * 
     * @param subject
     *          the subject polygon, which is a convex or concave simple polygon
     * @param clipper
     *          the convex polygon to clip the subject polygon against
     * @param result
     *          will hold the result polygon
     * @return result
     */
    public static List/* <Vector2f> */compute(List/* <Vector2f> */subject, List/* <Vector2f> */clipper, List/* <Vector2f> */result) {
        ArrayList/* <Vector2f> */res = new ArrayList/* <Vector2f> */(subject);
        ArrayList/* <Vector2f> */tmp = new ArrayList/* <Vector2f> */();
        int len = clipper.size();
        for (int i = 0; i < len; i++) {
            int len2 = res.size();
            tmp.clear();
            tmp.addAll(res);
            res.clear();
            Vector2f A = (Vector2f) clipper.get((i + len - 1) % len);
            Vector2f B = (Vector2f) clipper.get(i);
            for (int j = 0; j < len2; j++) {
                Vector2f P = (Vector2f) tmp.get((j + len2 - 1) % len2);
                Vector2f Q = (Vector2f) tmp.get(j);
                if (isInside(A, B, Q)) {
                    if (!isInside(A, B, P)) {
                        Vector2f isect = intersection(A, B, P, Q);
                        //if (!contains(res, isect))
                            res.add(isect);
                    }
                    res.add(Q);
                } else if (isInside(A, B, P)) {
                    Vector2f isect = intersection(A, B, P, Q);
                    //if (!contains(res, isect))
                        res.add(isect);
                }
            }
        }
        result.clear();
        result.addAll(res);
        return result;
    }

//    private static boolean contains(ArrayList list, Vector2f p) {
//        for (int i = 0; i < list.size(); i++) {
//            if (equal(p, (Vector2f) list.get(i)))
//                return true;
//        }
//        return false;
//    }
//
//    private static boolean equal(Vector2f p1, Vector2f p2) {
//        return Math.abs(p1.x - p2.x) < 1E-5f && Math.abs(p1.y - p2.y) < 1E-5f;
//    }

    private static boolean isInside(Vector2f a, Vector2f b, Vector2f c) {
        return (a.x - c.x) * (b.y - c.y) > (a.y - c.y) * (b.x - c.x);
    }

    private static Vector2f intersection(Vector2f a, Vector2f b, Vector2f p, Vector2f q) {
        float A1 = b.y - a.y;
        float B1 = a.x - b.x;
        float C1 = A1 * a.x + B1 * a.y;
        float A2 = q.y - p.y;
        float B2 = p.x - q.x;
        float C2 = A2 * p.x + B2 * p.y;
        float det = A1 * B2 - A2 * B1;
        float x = (B2 * C1 - B1 * C2) / det;
        float y = (A1 * C2 - A2 * C1) / det;
        return new Vector2f(x, y);
    }

}
