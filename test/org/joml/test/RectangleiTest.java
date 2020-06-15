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
import org.joml.Rectanglei;
import org.joml.Vector2f;
import org.joml.Vector2i;

/**
 * Tests for the {@link Rectanglei} class.
 *
 * @author Michael Pollind
 */
public class RectangleiTest extends TestCase {

    public void testRectangleContainsPoints() {
        Rectanglei rect = new Rectanglei(0, 0, 3, 3);

        Assert.assertTrue(rect.isValid());
        Assert.assertTrue(rect.containsPoint(new Vector2i(0, 0)));
        Assert.assertFalse(rect.containsPoint(new Vector2i(-1, -1)));
        Assert.assertFalse(rect.containsPoint(new Vector2i(4, 4)));
    }

    public void testRectangleIntersection() {
        Rectanglei first = new Rectanglei(0, 0, 3, 3);
        Rectanglei second = new Rectanglei(-1, -1, 2, 2);

        // is valid
        Assert.assertTrue(first.isValid());
        Assert.assertTrue(second.isValid());

        Assert.assertFalse(first.containsRectangle(second));
        Assert.assertFalse(second.containsRectangle(first));

        Assert.assertTrue(first.intersectsRectangle(second));
        Assert.assertTrue(second.intersectsRectangle(first));
        Assert.assertEquals(first.intersection(second, new Rectanglei()), new Rectanglei(0, 0, 2, 2));

    }

    public void testRectangleiEdgeIntersection() {
        Rectanglei center = new Rectanglei(0, 0, 1, 1);
        Rectanglei right = new Rectanglei(1, 0, 2, 1);
        Rectanglei left = new Rectanglei(-1, 0, 0, 1);
        Rectanglei top = new Rectanglei(0, 1, 1, 2);
        Rectanglei bottom = new Rectanglei(0, -1, 1, 0);

        Assert.assertTrue(center.isValid());
        Assert.assertTrue(right.isValid());
        Assert.assertTrue(left.isValid());
        Assert.assertTrue(top.isValid());
        Assert.assertTrue(bottom.isValid());

        Assert.assertFalse(center.containsRectangle(right));
        Assert.assertFalse(center.containsRectangle(left));
        Assert.assertFalse(center.containsRectangle(top));
        Assert.assertFalse(center.containsRectangle(bottom));

        Assert.assertTrue(center.intersectsRectangle(right));
        Assert.assertTrue(center.intersectsRectangle(left));
        Assert.assertTrue(center.intersectsRectangle(top));
        Assert.assertTrue(center.intersectsRectangle(bottom));

        Assert.assertTrue(right.intersectsRectangle(center));
        Assert.assertTrue(left.intersectsRectangle(center));
        Assert.assertTrue(top.intersectsRectangle(center));
        Assert.assertTrue(bottom.intersectsRectangle(center));
    }

    public void testRectangleiContainsPoint() {
        Rectanglei center = new Rectanglei(0, 0, 1, 1);

        Assert.assertTrue(center.isValid());

        Assert.assertTrue(center.containsPoint(0, 0));
        Assert.assertTrue(center.containsPoint(1, 0));
        Assert.assertTrue(center.containsPoint(0, 1));
        Assert.assertTrue(center.containsPoint(1, 1));
    }

    public void testRectangleContains() {
        Rectanglei first = new Rectanglei(-1, -1, 2, 2);
        Rectanglei second = new Rectanglei(0, 0, 1, 1);
        Assert.assertTrue(first.containsRectangle(second));
        Assert.assertFalse(second.containsRectangle(first));

        Assert.assertTrue(first.intersectsRectangle(second));
        Assert.assertTrue(second.intersectsRectangle(first));

        Assert.assertEquals(first.intersection(second, new Rectanglei()), new Rectanglei(0, 0, 1, 1));
    }

    public void testZeroSizeRectangle() {
        Rectanglei rect = new Rectanglei(0, 0, 0, 0);
        Assert.assertFalse(rect.isValid());
    }
}
