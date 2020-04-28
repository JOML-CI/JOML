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
import org.joml.Rectanglef;
import org.joml.Vector2f;

/**
 * Tests for the {@link Rectanglef} class.
 *
 * @author Michael Pollind
 */
public class RectanglefTest extends TestCase {
    public void testRectangleContainsPoints() {
        Rectanglef rect = new Rectanglef(0, 0, 3, 3);

        Assert.assertTrue(rect.isValid());
        Assert.assertTrue(rect.contains(new Vector2f(0, 0)));
        Assert.assertFalse(rect.contains(new Vector2f(-1, -1)));
        Assert.assertFalse(rect.contains(new Vector2f(4, 4)));
        Assert.assertFalse(rect.contains(new Vector2f(4, 4)));
    }

    public void testRectangleIntersection() {
        Rectanglef first = new Rectanglef(0, 0, 3, 3);
        Rectanglef second = new Rectanglef(-1, -1, 2, 2);

        // is valid
        Assert.assertTrue(first.isValid());
        Assert.assertTrue(second.isValid());

        Assert.assertFalse(first.contains(second));
        Assert.assertFalse(second.contains(first));

        Assert.assertTrue(first.intersects(second));
        Assert.assertTrue(second.intersects(first));
        Assert.assertEquals(first.intersection(second, new Rectanglef()), new Rectanglef(0, 0, 2, 2));

    }

    public void testRectangleContains() {
        Rectanglef first = new Rectanglef(-1, -1, 2, 2);
        Rectanglef second = new Rectanglef(0, 0, 1, 1);
        Assert.assertTrue(first.contains(second));
        Assert.assertFalse(second.contains(first));

        Assert.assertTrue(first.intersects(second));
        Assert.assertTrue(second.intersects(first));

        Assert.assertEquals(first.intersection(second, new Rectanglef()), new Rectanglef(0, 0, 1, 1));
    }

    public void testZeroSizeRectangle() {
        Rectanglef rect = new Rectanglef(0, 0, 0, 0);
        Assert.assertFalse(rect.isValid());
    }

}
