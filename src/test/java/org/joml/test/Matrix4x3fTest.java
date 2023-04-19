/*
 * The MIT License
 *
 * Copyright (c) 2016-2023 JOML.
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

import org.joml.Matrix4x3f;
import org.joml.Vector3f;
import org.joml.Math;
import org.junit.jupiter.api.Test;

import static org.joml.test.TestUtil.*;

/**
 * Tests for the {@link Matrix4x3f} class.
 * 
 * @author Kai Burjack
 */
class Matrix4x3fTest {

    @Test
    void testLookAt() {
        Matrix4x3f m1, m2;
        m1 = new Matrix4x3f().lookAt(0, 2, 3, 0, 0, 0, 0, 1, 0);
        m2 = new Matrix4x3f().translate(0, 0, -(float) Math.sqrt(2 * 2 + 3 * 3)).rotateX(
                Math.atan2(2, 3));
        assertMatrix4x3fEquals(m1, m2, 1E-5f);
        m1 = new Matrix4x3f().lookAt(3, 2, 0, 0, 0, 0, 0, 1, 0);
        m2 = new Matrix4x3f().translate(0, 0, -(float) Math.sqrt(2 * 2 + 3 * 3))
                .rotateX(Math.atan2(2, 3)).rotateY(Math.toRadians(-90));
        assertMatrix4x3fEquals(m1, m2, 1E-4f);
    }

    @Test
    void testPositiveXRotateY() {
        Vector3f dir = new Vector3f();
        Matrix4x3f m = new Matrix4x3f()
                .rotateY(Math.toRadians(90));
        m.positiveX(dir);
        assertVector3fEquals(new Vector3f(0, 0, 1), dir, 1E-7f);
    }

    @Test
    void testPositiveYRotateX() {
        Vector3f dir = new Vector3f();
        Matrix4x3f m = new Matrix4x3f()
                .rotateX(Math.toRadians(90));
        m.positiveY(dir);
        assertVector3fEquals(new Vector3f(0, 0, -1), dir, 1E-7f);
    }

    @Test
    void testPositiveZRotateX() {
        Vector3f dir = new Vector3f();
        Matrix4x3f m = new Matrix4x3f()
                .rotateX(Math.toRadians(90));
        m.positiveZ(dir);
        assertVector3fEquals(new Vector3f(0, 1, 0), dir, 1E-7f);
    }

    @Test
    void testPositiveXRotateXY() {
        Vector3f dir = new Vector3f();
        Matrix4x3f m = new Matrix4x3f()
                .rotateY(Math.toRadians(90)).rotateX(Math.toRadians(45));
        m.positiveX(dir);
        assertVector3fEquals(new Vector3f(0, 1, 1).normalize(), dir, 1E-7f);
    }

    @Test
    void testPositiveXYZLookAt() {
        Vector3f dir = new Vector3f();
        Matrix4x3f m = new Matrix4x3f()
                .lookAt(0, 0, 0, -1, 0, 0, 0, 1, 0);
        m.positiveX(dir);
        assertVector3fEquals(new Vector3f(0, 0, -1).normalize(), dir, 1E-7f);
        m.positiveY(dir);
        assertVector3fEquals(new Vector3f(0, 1, 0).normalize(), dir, 1E-7f);
        m.positiveZ(dir);
        assertVector3fEquals(new Vector3f(1, 0, 0).normalize(), dir, 1E-7f);
    }

    @Test
    void testPositiveXYZSameAsInvert() {
        Vector3f dir = new Vector3f();
        Vector3f dir2 = new Vector3f();
        Matrix4x3f m = new Matrix4x3f().rotateXYZ(0.12f, 1.25f, -2.56f);
        Matrix4x3f inv = new Matrix4x3f(m).invert();
        m.positiveX(dir);
        inv.transformDirection(dir2.set(1, 0, 0));
        assertVector3fEquals(dir2, dir, 1E-6f);
        m.positiveY(dir);
        inv.transformDirection(dir2.set(0, 1, 0));
        assertVector3fEquals(dir2, dir, 1E-6f);
        m.positiveZ(dir);
        inv.transformDirection(dir2.set(0, 0, 1));
        assertVector3fEquals(dir2, dir, 1E-6f);
    }

    @Test
    void testNormal() {
        Matrix4x3f r = new Matrix4x3f().rotateY((float) Math.PI / 2);
        Matrix4x3f s = new Matrix4x3f(r).scale(0.2f);
        Matrix4x3f n = new Matrix4x3f();
        s.normal(n);
        n.normalize3x3();
        assertMatrix4x3fEquals(r, n, 1E-8f);
    }

    @Test
    void testInvert() {
        Matrix4x3f invm = new Matrix4x3f();
        Matrix4x3f m = new Matrix4x3f();
        m.rotateX(1.2f).rotateY(0.2f).rotateZ(0.1f).translate(1, 2, 3).invert(invm);
        Vector3f orig = new Vector3f(4, -6, 8);
        Vector3f v = new Vector3f();
        Vector3f w = new Vector3f();
        m.transformPosition(orig, v);
        invm.transformPosition(v, w);
        assertVector3fEquals(orig, w, 1E-6f);
        invm.invert();
        assertMatrix4x3fEquals(m, invm, 1E-6f);
    }

    @Test
    void testRotateXYZ() {
        Matrix4x3f m = new Matrix4x3f().rotateX(0.12f).rotateY(0.0623f).rotateZ(0.95f);
        Matrix4x3f n = new Matrix4x3f().rotateXYZ(0.12f, 0.0623f, 0.95f);
        assertMatrix4x3fEquals(m, n, 1E-6f);
    }

    @Test
    void testRotateZYX() {
        Matrix4x3f m = new Matrix4x3f().rotateZ(1.12f).rotateY(0.0623f).rotateX(0.95f);
        Matrix4x3f n = new Matrix4x3f().rotateZYX(1.12f, 0.0623f, 0.95f);
        assertMatrix4x3fEquals(m, n, 1E-6f);
    }

    @Test
    void testRotateYXZ() {
        Matrix4x3f m = new Matrix4x3f().rotateY(1.12f).rotateX(0.0623f).rotateZ(0.95f);
        Matrix4x3f n = new Matrix4x3f().rotateYXZ(1.12f, 0.0623f, 0.95f);
        assertMatrix4x3fEquals(m, n, 1E-6f);
    }

    @Test
    void testRotateAffineXYZ() {
        Matrix4x3f m = new Matrix4x3f().rotateX(0.12f).rotateY(0.0623f).rotateZ(0.95f);
        Matrix4x3f n = new Matrix4x3f().rotateXYZ(0.12f, 0.0623f, 0.95f);
        assertMatrix4x3fEquals(m, n, 1E-6f);
    }

    @Test
    void testRotateAffineZYX() {
        Matrix4x3f m = new Matrix4x3f().rotateZ(1.12f).rotateY(0.0623f).rotateX(0.95f);
        Matrix4x3f n = new Matrix4x3f().rotateZYX(1.12f, 0.0623f, 0.95f);
        assertMatrix4x3fEquals(m, n, 1E-6f);
    }

    @Test
    void testRotateAffineYXZ() {
        Matrix4x3f m = new Matrix4x3f().rotateY(1.12f).rotateX(0.0623f).rotateZ(0.95f);
        Matrix4x3f n = new Matrix4x3f().rotateYXZ(1.12f, 0.0623f, 0.95f);
        assertMatrix4x3fEquals(m, n, 1E-6f);
    }

    @Test
    void testRotationXYZ() {
        Matrix4x3f m = new Matrix4x3f().rotationX(0.32f).rotateY(0.5623f).rotateZ(0.95f);
        Matrix4x3f n = new Matrix4x3f().rotationXYZ(0.32f, 0.5623f, 0.95f);
        assertMatrix4x3fEquals(m, n, 1E-6f);
    }

    @Test
    void testRotationZYX() {
        Matrix4x3f m = new Matrix4x3f().rotationZ(0.12f).rotateY(0.0623f).rotateX(0.95f);
        Matrix4x3f n = new Matrix4x3f().rotationZYX(0.12f, 0.0623f, 0.95f);
        assertMatrix4x3fEquals(m, n, 1E-6f);
    }

    @Test
    void testRotationYXZ() {
        Matrix4x3f m = new Matrix4x3f().rotationY(0.12f).rotateX(0.0623f).rotateZ(0.95f);
        Matrix4x3f n = new Matrix4x3f().rotationYXZ(0.12f, 0.0623f, 0.95f);
        assertMatrix4x3fEquals(m, n, 1E-6f);
    }

}
