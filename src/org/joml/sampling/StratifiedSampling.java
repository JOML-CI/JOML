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
 * Creates samples on a unit quad using an NxN strata grid.
 * 
 * @author Kai Burjack
 */
public class StratifiedSampling {

    private final Random rnd;

    /**
     * Create a new instance of {@link StratifiedSampling} and initialize the random number generator with the given
     * <code>seed</code>.
     * 
     * @param seed
     *            the seed to initialize the random number generator with
     */
    public StratifiedSampling(long seed) {
        this.rnd = new Random(seed);
    }

    /**
     * Generate <code>n * n</code> random sample positions in the unit square of <code>x, y = [-1..+1]</code>.
     * <p>
     * Each sample within its stratum is distributed randomly.
     * 
     * @param n
     *            the number of strata in each dimension
     * @param callback
     *            will be called for each generated sample position
     */
    public void generateRandom(int n, Callback2d callback) {
        for (int y = 0; y < n; y++) {
            for (int x = 0; x < n; x++) {
                float sampleX = (rnd.nextFloat() / n + (float) x / n) * 2.0f - 1.0f;
                float sampleY = (rnd.nextFloat() / n + (float) y / n) * 2.0f - 1.0f;
                callback.onNewSample(sampleX, sampleY);
            }
        }
    }

    /**
     * Generate <code>n * n</code> random sample positions in the unit square of <code>x, y = [-1..+1]</code>.
     * <p>
     * Each sample within its stratum is confined to be within <code>[-centering/2..1-centering]</code> of its stratum.
     * 
     * @param n
     *            the number of strata in each dimension
     * @param centering
     *            determines how much the random samples in each stratum are confined to be near the center of the
     *            stratum. Possible values are <code>[0..1]</code>
     * @param callback
     *            will be called for each generated sample position
     */
    public void generateCentered(int n, float centering, Callback2d callback) {
        float start = centering * 0.5f;
        float end = 1.0f - centering;
        for (int y = 0; y < n; y++) {
            for (int x = 0; x < n; x++) {
                float sampleX = ((start + rnd.nextFloat() * end) / n + (float) x / n) * 2.0f - 1.0f;
                float sampleY = ((start + rnd.nextFloat() * end) / n + (float) y / n) * 2.0f - 1.0f;
                callback.onNewSample(sampleX, sampleY);
            }
        }
    }

}
