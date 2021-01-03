/*
 * The MIT License
 *
 * Copyright (c) 2016-2021 JOML
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
package org.joml.sampling;

import org.joml.Random;

/**
 * Generates uniform samples.
 * 
 * @author Kai Burjack
 */
public class UniformSampling {

    /**
     * Generates uniform samples on a unit disk.
     * 
     * @author Kai Burjack
     */
    public static class Disk {
        private final Random rnd;

        /**
         * Create a new instance of {@link Disk}, initialize the random number generator with the given <code>seed</code> and generate <code>numSamples</code> number of sample
         * positions on the unit disk, and call the given <code>callback</code> for each sample generate.
         * 
         * @param seed
         *            the seed to initialize the random number generator with
         * @param numSamples
         *            the number of samples to generate
         * @param callback
         *            will be called for each sample generated
         */
        public Disk(long seed, int numSamples, Callback2d callback) {
            this.rnd = new Random(seed);
            generate(numSamples, callback);
        }

        private void generate(int numSamples, Callback2d callback) {
            for (int i = 0; i < numSamples; i++) {
                float r = rnd.nextFloat();
                float a = rnd.nextFloat() * 2.0f * (float) Math.PI;
                float sqrtR = (float) Math.sqrt(r);
                float x = sqrtR * (float) Math.sin_roquen_9(a + 0.5 * Math.PI);
                float y = sqrtR * (float) Math.sin_roquen_9(a);
                callback.onNewSample(x, y);
            }
        }
    }

    /**
     * Generates uniform samples on a unit sphere.
     * 
     * @author Kai Burjack
     */
    public static class Sphere {
        private final Random rnd;

        /**
         * Create a new instance of {@link Sphere}, initialize the random number generator with the given <code>seed</code> and generate <code>numSamples</code> number of sample
         * positions on the unit sphere, and call the given <code>callback</code> for each sample generate.
         * 
         * @param seed
         *            the seed to initialize the random number generator with
         * @param numSamples
         *            the number of samples to generate
         * @param callback
         *            will be called for each sample generated
         */
        public Sphere(long seed, int numSamples, Callback3d callback) {
            this.rnd = new Random(seed);
            generate(numSamples, callback);
        }

        /**
         * Create <code>numSamples</code> number of samples which are uniformly distributed on a unit sphere, and call the given <code>callback</code> for each sample generated.
         * <p>
         * Reference: <a href="http://mathworld.wolfram.com/SpherePointPicking.html">http://mathworld.wolfram.com/</a>
         * 
         * @param numSamples
         *            the number of samples to generate
         * @param callback
         *            will be called for each sample generated
         */
        public void generate(int numSamples, Callback3d callback) {
            for (int i = 0; i < numSamples;) {
                float x1 = rnd.nextFloat() * 2.0f - 1.0f;
                float x2 = rnd.nextFloat() * 2.0f - 1.0f;
                if (x1 * x1 + x2 * x2 >= 1.0f)
                    continue;
                float sqrt = (float) Math.sqrt(1.0 - x1 * x1 - x2 * x2);
                float x = 2 * x1 * sqrt;
                float y = 2 * x2 * sqrt;
                float z = 1.0f - 2.0f * (x1 * x1 + x2 * x2);
                callback.onNewSample(x, y, z);
                i++;
            }
        }
    }

}
