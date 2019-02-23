/*
 * The MIT License
 *
 * Copyright (c) 2016-2019 JOML.
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

import org.joml.Quaternionf;
import org.joml.QuaternionfInterpolator;
import org.joml.Vector3f;
import org.joml.Math;

import junit.framework.TestCase;

/**
 * Test class for {@link QuaternionfInterpolator}.
 * 
 * @author Kai Burjack
 */
public class QuaternionfInterpolatorTest extends TestCase {

    /**
     * Average between three quaternions, each with a weight of 1/3.
     */
    public static void testOneThird() {
        Quaternionf q0 = new Quaternionf().rotateX((float) Math.toRadians(90));
        Quaternionf q1 = new Quaternionf().rotateY((float) Math.toRadians(90));
        Quaternionf q2 = new Quaternionf().rotateZ((float) Math.toRadians(90));
        Quaternionf dest = new Quaternionf();
        QuaternionfInterpolator inter = new QuaternionfInterpolator();
        inter.computeWeightedAverage(new Quaternionf[] { q0, q1, q2 }, new float[] { 1.0f/3.0f, 1.0f/3.0f, 1.0f/3.0f }, 30, dest);
        Vector3f v = new Vector3f(0, 0, 1);
        dest.transform(v);
        assertEquals(1.0f, v.length(), 1E-6f);
        TestUtil.assertVector3fEquals(new Vector3f(2.0f/3.0f, -1.0f/3.0f, 2.0f/3.0f), v, 1E-6f);
    }

}
