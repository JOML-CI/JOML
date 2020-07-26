/*
 * The MIT License
 *
 * Copyright (c) 2020 JOML.
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
package org.joml.test;

import org.joml.Intersectiond;
import org.joml.Vector2d;
import org.joml.Vector3d;

import junit.framework.TestCase;

/**
 * Tests for the {@link Intersectiond} class.
 *
 * @author Dmitrii Ivaniusin
 */
public class IntersectiondTest extends TestCase {

    public static void testFindClosestPointOnRectangle() {
        final double EPSILON = 1E-4d;
        Vector3d a = new Vector3d(0, 0, 0);
        Vector3d b = new Vector3d(0, 1d, 0);
        Vector3d c = new Vector3d(1d, 0, 0);
        Vector3d p1 = new Vector3d(0.5d, 0.5d, 0);
        Vector3d v1 = Intersectiond.findClosestPointOnRectangle(a.x, a.y, a.z, b.x, b.y, b.z, c.x, c.y, c.z, p1.x, p1.y, p1.z, new Vector3d());
        TestUtil.assertVector3dEquals(new Vector3d(0.5d, 0.5d, 0), v1, EPSILON);
        Vector3d p2 = new Vector3d(-1d, -0.5d, 0);
        Vector3d v2 = Intersectiond.findClosestPointOnRectangle(a.x, a.y, a.z, b.x, b.y, b.z, c.x, c.y, c.z, p2.x, p2.y, p2.z, new Vector3d());
        TestUtil.assertVector3dEquals(new Vector3d(0, 0, 0), v2, EPSILON);
        Vector3d p3 = new Vector3d(-0.5d, 2d, 0);
        Vector3d v3 = Intersectiond.findClosestPointOnRectangle(a.x, a.y, a.z, b.x, b.y, b.z, c.x, c.y, c.z, p3.x, p3.y, p3.z, new Vector3d());
        TestUtil.assertVector3dEquals(new Vector3d(0, 1d, 0), v3, EPSILON);
        Vector3d p4 = new Vector3d(-1d, 0.5d, 0);
        Vector3d v4 = Intersectiond.findClosestPointOnRectangle(a.x, a.y, a.z, b.x, b.y, b.z, c.x, c.y, c.z, p4.x, p4.y, p4.z, new Vector3d());
        TestUtil.assertVector3dEquals(new Vector3d(0, 0.5d, 0), v4, EPSILON);
        Vector3d p5 = new Vector3d(0.5d, 1d, 0);
        Vector3d v5 = Intersectiond.findClosestPointOnRectangle(a.x, a.y, a.z, b.x, b.y, b.z, c.x, c.y, c.z, p5.x, p5.y, p5.z, new Vector3d());
        TestUtil.assertVector3dEquals(new Vector3d(0.5d, 1d, 0), v5, EPSILON);
    }
    
    public static void testLineSegmentAar() {
        Vector2d p = new Vector2d();
        assertEquals(Intersectiond.ONE_INTERSECTION, Intersectiond.intersectLineSegmentAar(0, 0, 1, 0, 0.5, -1, 1.5, 1, p));
        TestUtil.assertVector2dEquals(new Vector2d(0.5, 0.5), p, 1E-6f);
        assertEquals(Intersectiond.ONE_INTERSECTION, Intersectiond.intersectLineSegmentAar(1, 0, 0, 0, 0.5, -1, 1.5, 1, p));
        TestUtil.assertVector2dEquals(new Vector2d(0.5, 0.5), p, 1E-6f);
        assertEquals(Intersectiond.ONE_INTERSECTION, Intersectiond.intersectLineSegmentAar(1, 0, 2, 0, 0, -1, 2, 1, p));
        TestUtil.assertVector2dEquals(new Vector2d(1, 1), p, 1E-6f);
        assertEquals(Intersectiond.INSIDE, Intersectiond.intersectLineSegmentAar(0, 0, 1, 0, -0.5, -1, 1.5, 1, p));
        TestUtil.assertVector2dEquals(new Vector2d(-0.5, 1.5), p, 1E-6f);
        assertEquals(Intersectiond.INSIDE, Intersectiond.intersectLineSegmentAar(1, 0, 0, 0, -0.5, -1, 1.5, 1, p));
        TestUtil.assertVector2dEquals(new Vector2d(-0.5, 1.5), p, 1E-6f);
        assertEquals(Intersectiond.OUTSIDE, Intersectiond.intersectLineSegmentAar(0, 0, 1, 0, 1.5, -1, 2.5, 1, p));
        assertEquals(Intersectiond.OUTSIDE, Intersectiond.intersectLineSegmentAar(1, 0, 0, 0, 1.5, -1, 2.5, 1, p));
        assertEquals(Intersectiond.TWO_INTERSECTION, Intersectiond.intersectLineSegmentAar(0, 0, 1, 0, 0.5, -1, 0.75, 1, p));
        TestUtil.assertVector2dEquals(new Vector2d(0.5, 0.75), p, 1E-6f);
        assertEquals(Intersectiond.TWO_INTERSECTION, Intersectiond.intersectLineSegmentAar(1, 0, 0, 0, 0.5, -1, 0.75, 1, p));
        TestUtil.assertVector2dEquals(new Vector2d(0.25, 0.5), p, 1E-6f);
        assertEquals(Intersectiond.ONE_INTERSECTION, Intersectiond.intersectLineSegmentAar(0, 0, 1, 0, 1, -1, 2, 1, p));
        TestUtil.assertVector2dEquals(new Vector2d(1, 1), p, 1E-6f);
        assertEquals(Intersectiond.ONE_INTERSECTION, Intersectiond.intersectLineSegmentAar(0, 0, -1, 0, -2, -1, -1, 1, p));
        TestUtil.assertVector2dEquals(new Vector2d(1, 1), p, 1E-6f);
        assertEquals(Intersectiond.TWO_INTERSECTION, Intersectiond.intersectLineSegmentAar(0, 0, 1, 0, 0.5, -1, 1, 1, p));
        TestUtil.assertVector2dEquals(new Vector2d(0.5, 1), p, 1E-6f);
        assertEquals(Intersectiond.TWO_INTERSECTION, Intersectiond.intersectLineSegmentAar(0, 0, 0, 1, -0.5, 0, 0, 1, p));
        TestUtil.assertVector2dEquals(new Vector2d(0, 1), p, 1E-6f);
        assertEquals(Intersectiond.TWO_INTERSECTION, Intersectiond.intersectLineSegmentAar(0, 0, 1, 0, 0, 0, 1, 1, p));
        TestUtil.assertVector2dEquals(new Vector2d(0, 1), p, 1E-6f);
        assertEquals(Intersectiond.TWO_INTERSECTION, Intersectiond.intersectLineSegmentAar(1, 0, 0, 0, 0, 0, 1, 1, p));
        TestUtil.assertVector2dEquals(new Vector2d(0, 1), p, 1E-6f);
        assertEquals(Intersectiond.TWO_INTERSECTION, Intersectiond.intersectLineSegmentAar(0, 1, 0, 0, 0, 0, 1, 1, p));
        TestUtil.assertVector2dEquals(new Vector2d(0, 1), p, 1E-6f);
        assertEquals(Intersectiond.TWO_INTERSECTION, Intersectiond.intersectLineSegmentAar(0, 0, 0, 1, 0, 0, 1, 1, p));
        TestUtil.assertVector2dEquals(new Vector2d(0, 1), p, 1E-6f);
        assertEquals(Intersectiond.TWO_INTERSECTION, Intersectiond.intersectLineSegmentAar(0, -1, 0, 0, -1, -1, 0, 0, p));
        TestUtil.assertVector2dEquals(new Vector2d(0, 1), p, 1E-6f);
        assertEquals(Intersectiond.TWO_INTERSECTION, Intersectiond.intersectLineSegmentAar(0, -1, 0, 1, -1, -1, 0, 0, p));
        TestUtil.assertVector2dEquals(new Vector2d(0, 0.5), p, 1E-6f);
    }

    public static void testLineSegmentAab() {
        Vector2d p = new Vector2d();
        assertEquals(Intersectiond.ONE_INTERSECTION, Intersectiond.intersectLineSegmentAab(0, 0, 0, 0, 0, 1, -0.5, -1, 1, 0.5, 1, 2, p));
        TestUtil.assertVector2dEquals(new Vector2d(1, 1), p, 1E-6f);
        assertEquals(Intersectiond.ONE_INTERSECTION, Intersectiond.intersectLineSegmentAab(1, 0, 0, 2, 0, 0, 0, -1, -1, 2, 0, 0, p));
        TestUtil.assertVector2dEquals(new Vector2d(1, 1), p, 1E-6f);
        assertEquals(Intersectiond.TWO_INTERSECTION, Intersectiond.intersectLineSegmentAab(0, 0, -1, 0, 0, 0, 0, -1, -1, 1, 1, 0, p));
        TestUtil.assertVector2dEquals(new Vector2d(0, 1), p, 1E-6f);
    }

}
