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

import junit.framework.Assert;
import junit.framework.TestCase;
import org.joml.AABBi;

public class AABBiTest extends TestCase {
    public void testAABBiEdgeIntersection() {
        AABBi center = new AABBi(0, 0, 0, 1, 1,1);
        AABBi right = new AABBi(1, 0,0, 2, 1,1);
        AABBi left = new AABBi(-1, 0, 0, 0, 1,1);
        AABBi front = new AABBi(0, 1, 0, 1, 2,1);
        AABBi back = new AABBi(0, -1, 0, 1, 0,1);
        AABBi top = new AABBi(0, 0, 1, 1, 1,2);
        AABBi bottom = new AABBi(0, 0, -1, 1, 1,0);


        Assert.assertTrue(center.isValid());
        Assert.assertTrue(right.isValid());
        Assert.assertTrue(left.isValid());
        Assert.assertTrue(top.isValid());
        Assert.assertTrue(bottom.isValid());
        Assert.assertTrue(front.isValid());
        Assert.assertTrue(back.isValid());

        // check contains
        Assert.assertFalse(center.containsAABB(right));
        Assert.assertFalse(center.containsAABB(left));
        Assert.assertFalse(center.containsAABB(front));
        Assert.assertFalse(center.containsAABB(back));
        Assert.assertFalse(center.containsAABB(top));

        // test intersection
        Assert.assertTrue(center.intersectsAABB(right));
        Assert.assertTrue(center.intersectsAABB(left));
        Assert.assertTrue(center.intersectsAABB(front));
        Assert.assertTrue(center.intersectsAABB(back));
        Assert.assertTrue(center.intersectsAABB(top));

        Assert.assertTrue(right.intersectsAABB(center));
        Assert.assertTrue(left.intersectsAABB(center));
        Assert.assertTrue(front.intersectsAABB(center));
        Assert.assertTrue(back.intersectsAABB(center));
        Assert.assertTrue(top.intersectsAABB(center));
    }


    public void testAABBiCornerIntersection() {
        AABBi center = new AABBi(0, 0, 0, 1, 1,1);

        AABBi frontLeft = new AABBi(-1, 1,-1, 0, 2,0);
        AABBi frontRight = new AABBi(1, 1,-1, 2, 2,0);
        AABBi backLeft = new AABBi(-1, -1,-1, 0, 0,0);
        AABBi backRight = new AABBi(1, -1,-1, 2, 0,0);

        AABBi topFrontLeft = new AABBi(-1, 1,1, 0, 2,2);
        AABBi topFrontRight = new AABBi(1, 1,1, 2, 2,2);
        AABBi topBackLeft = new AABBi(-1, -1,1, 0, 0,2);
        AABBi topBackRight = new AABBi(1, -1,1, 2, 0,2);

        Assert.assertTrue(center.isValid());

        // bottom corners
        Assert.assertTrue(frontLeft.isValid());
        Assert.assertTrue(frontRight.isValid());
        Assert.assertTrue(backLeft.isValid());
        Assert.assertTrue(backRight.isValid());

        // top corners
        Assert.assertTrue(topFrontLeft.isValid());
        Assert.assertTrue(topFrontRight.isValid());
        Assert.assertTrue(topBackLeft.isValid());
        Assert.assertTrue(topBackRight.isValid());

        // test contains
        // top corners intersection
        Assert.assertFalse(topFrontLeft.containsAABB(center));
        Assert.assertFalse(topFrontRight.containsAABB(center));
        Assert.assertFalse(topBackLeft.containsAABB(center));
        Assert.assertFalse(topBackRight.containsAABB(center));

        // bottom corners intersection
        Assert.assertFalse(frontLeft.containsAABB(center));
        Assert.assertFalse(frontRight.containsAABB(center));
        Assert.assertFalse(backLeft.containsAABB(center));
        Assert.assertFalse(backRight.containsAABB(center));

        // test intersection
        // top corners intersection
        Assert.assertTrue(topFrontLeft.intersectsAABB(center));
        Assert.assertTrue(topFrontRight.intersectsAABB(center));
        Assert.assertTrue(topBackLeft.intersectsAABB(center));
        Assert.assertTrue(topBackRight.intersectsAABB(center));

        // bottom corners intersection
        Assert.assertTrue(frontLeft.intersectsAABB(center));
        Assert.assertTrue(frontRight.intersectsAABB(center));
        Assert.assertTrue(backLeft.intersectsAABB(center));
        Assert.assertTrue(backRight.intersectsAABB(center));



    }
}
