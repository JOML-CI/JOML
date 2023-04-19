/*
 * The MIT License
 *
 * Copyright (c) 2015-2023 JOML.
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

import org.joml.FrustumIntersection;
import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.joml.Math;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the {@link FrustumIntersection} class.
 * 
 * @author Kai Burjack
 */
class FrustumIntersectionTest {
    @Test
    void testIsSphereInFrustumOrtho() {
        Matrix4f m = new Matrix4f().ortho(-1, 1, -1, 1, -1, 1);
        FrustumIntersection c = new FrustumIntersection(m);
        assertTrue(c.testSphere(1, 0, 0, 0.1f));
        assertFalse(c.testSphere(1.2f, 0, 0, 0.1f));
    }

    @Test
    void testIsSphereInFrustumPerspective() {
        Matrix4f m = new Matrix4f().perspective((float) Math.PI / 2.0f, 1.0f, 0.1f, 100.0f);
        FrustumIntersection c = new FrustumIntersection(m);
        assertTrue(c.testSphere(1, 0, -2, 0.1f));
        assertFalse(c.testSphere(4f, 0, -2, 1.0f));
    }

    @Test
    void testIsAabInFrustumOrtho() {
        Matrix4f m = new Matrix4f().ortho(-1, 1, -1, 1, -1, 1);
        FrustumIntersection c = new FrustumIntersection(m);
        assertEquals(FrustumIntersection.INTERSECT, c.intersectAab(-20, -2, 0, 20, 2, 0));
        assertEquals(FrustumIntersection.INSIDE, c.intersectAab(-0.5f, -0.5f, -0.5f, 0.5f, 0.5f, 0.5f));
        assertEquals(Matrix4fc.PLANE_PX, c.intersectAab(1.1f, 0, 0, 2, 2, 2));
        c.set(new Matrix4f().ortho(-1, 1, -1, 1, -1, 1));
        assertEquals(FrustumIntersection.INTERSECT, c.intersectAab(0, 0, 0, 2, 2, 2));
        assertEquals(Matrix4fc.PLANE_PX, c.intersectAab(1.1f, 0, 0, 2, 2, 2));
        c.set(new Matrix4f());
        assertEquals(FrustumIntersection.INTERSECT, c.intersectAab(0.5f, 0.5f, 0.5f, 2, 2, 2));
        assertEquals(Matrix4fc.PLANE_PX, c.intersectAab(1.5f, 0.5f, 0.5f, 2, 2, 2));
        assertEquals(Matrix4fc.PLANE_NX, c.intersectAab(-2.5f, 0.5f, 0.5f, -1.5f, 2, 2));
        assertEquals(Matrix4fc.PLANE_NY, c.intersectAab(-0.5f, -2.5f, 0.5f, 1.5f, -2, 2));
    }

    @Test
    void testIsAabInPerspective() {
        Matrix4f m = new Matrix4f().perspective((float) Math.PI / 2.0f, 1.0f, 0.1f, 100.0f);
        FrustumIntersection c = new FrustumIntersection(m);
        assertEquals(FrustumIntersection.INSIDE, c.intersectAab(0, 0, -7, 1, 1, -5));
        assertEquals(FrustumIntersection.PLANE_PX, c.intersectAab(1.1f, 0, 0, 2, 2, 2));
        assertEquals(FrustumIntersection.PLANE_PX, c.intersectAab(4, 4, -3, 5, 5, -5));
        assertEquals(FrustumIntersection.PLANE_NY, c.intersectAab(-6, -6, -2, -1, -4, -4));
    }

    @Test
    void testIsPointInPerspective() {
        Matrix4f m = new Matrix4f().perspective((float) Math.PI / 2.0f, 1.0f, 0.1f, 100.0f);
        FrustumIntersection c = new FrustumIntersection(m);
        assertTrue(c.testPoint(0, 0, -5));
        assertFalse(c.testPoint(0, 6, -5));
    }

    @Test
    void testIsAabInPerspectiveMask() {
        Matrix4f m = new Matrix4f().perspective((float) Math.PI / 2.0f, 1.0f, 0.1f, 100.0f);
        FrustumIntersection c = new FrustumIntersection(m);
        assertEquals(FrustumIntersection.INTERSECT, c.intersectAab(5.1f, 0, -3, 8, 2, -2, ~0 ^ FrustumIntersection.PLANE_MASK_PX));
        assertEquals(FrustumIntersection.INTERSECT, c.intersectAab(-6.1f, 0, -3, -5, 2, -2, ~0 ^ FrustumIntersection.PLANE_MASK_NX));
        assertEquals(Matrix4fc.PLANE_NX, c.intersectAab(-6.1f, 0, -3, -5, 2, -2, FrustumIntersection.PLANE_MASK_NX));
        assertEquals(Matrix4fc.PLANE_NX, c.intersectAab(-6.1f, 0, -3, -5, 2, -2, ~0, Matrix4fc.PLANE_NX));
    }
}
