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
import org.joml.Rectangled;
import org.joml.Vector2d;

public class RectangledTest extends TestCase {

    public void testRectangleContainsPoints() {
        Rectangled rect = new Rectangled(0, 0, 3, 3);

        Assert.assertTrue(rect.isValid());
        Assert.assertTrue(rect.containsPoint(new Vector2d(0, 0)));
        Assert.assertFalse(rect.containsPoint(new Vector2d(-1, -1)));
        Assert.assertFalse(rect.containsPoint(new Vector2d(4, 4)));
    }

    public void testRectangleIntersection() {
        Rectangled first = new Rectangled(0, 0, 3, 3);
        Rectangled second = new Rectangled(-1, -1, 2, 2);

        // is valid
        Assert.assertTrue(first.isValid());
        Assert.assertTrue(second.isValid());

        Assert.assertFalse(first.containsRectangle(second));
        Assert.assertFalse(second.containsRectangle(first));

        Assert.assertTrue(first.intersectsRectangle(second));
        Assert.assertTrue(second.intersectsRectangle(first));
        Assert.assertEquals(first.intersection(second, new Rectangled()), new Rectangled(0, 0, 2, 2));

    }

    public void testRectangleContains() {
        Rectangled first = new Rectangled(-1, -1, 2, 2);
        Rectangled second = new Rectangled(0, 0, 1, 1);
        Assert.assertTrue(first.containsRectangle(second));
        Assert.assertFalse(second.containsRectangle(first));

        Assert.assertTrue(first.intersectsRectangle(second));
        Assert.assertTrue(second.intersectsRectangle(first));

        Assert.assertEquals(first.intersection(second, new Rectangled()), new Rectangled(0, 0, 1, 1));
    }

    public void testZeroSizeRectangle() {
        Rectangled rect = new Rectangled(0, 0, 0, 0);
        Assert.assertFalse(rect.isValid());
    }
}
