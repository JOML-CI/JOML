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
 * Creates samples on a spiral around a center point.
 * 
 * @author Kai Burjack
 */
public class SpiralSampling {
    private final Random rnd;

    /**
     * Create a new instance of {@link SpiralSampling} and initialize the random number generator with the given <code>seed</code>.
     * 
     * @param seed
     *            the seed to initialize the random number generator with
     */
    public SpiralSampling(long seed) {
        rnd = new Random(seed);
    }

    /**
     * Create <code>numSamples</code> number of samples on a spiral with maximum radius <code>radius</code> around the center using <code>numRotations</code> number of rotations
     * along the spiral, and call the given <code>callback</code> for each sample generated.
     * <p>
     * The generated sample points are distributed with equal angle differences around the spiral, so they concentrate towards the center.
     * 
     * @param radius
     *            the maximum radius of the spiral
     * @param numRotations
     *            the number of rotations of the spiral
     * @param numSamples
     *            the number of samples to generate
     * @param callback
     *            will be called for each sample generated
     */
    public void createEquiAngle(float radius, int numRotations, int numSamples, Callback2d callback) {
        for (int sample = 0; sample < numSamples; sample++) {
            float angle = 2.0f * (float) Math.PI * (sample * numRotations) / numSamples;
            float r = radius * sample / (numSamples - 1);
            float x = (float) Math.sin_roquen_9(angle + 0.5f * (float) Math.PI) * r;
            float y = (float) Math.sin_roquen_9(angle) * r;
            callback.onNewSample(x, y);
        }
    }

    /**
     * Create <code>numSamples</code> number of samples on a spiral with maximum radius <code>radius</code> around the center using <code>numRotations</code> number of rotations
     * along the spiral, and call the given <code>callback</code> for each sample generated.
     * <p>
     * The generated sample points are distributed with equal angle differences around the spiral, so they concentrate towards the center.
     * <p>
     * Additionally, the radius of each sample point is jittered by the given <code>jitter</code> factor.
     * 
     * @param radius
     *            the maximum radius of the spiral
     * @param numRotations
     *            the number of rotations of the spiral
     * @param numSamples
     *            the number of samples to generate
     * @param jitter
     *            the factor by which the radius of each sample point is jittered. Possible values are <code>[0..1]</code>
     * @param callback
     *            will be called for each sample generated
     */
    public void createEquiAngle(float radius, int numRotations, int numSamples, float jitter, Callback2d callback) {
        float spacing = radius / numRotations;
        for (int sample = 0; sample < numSamples; sample++) {
            float angle = 2.0f * (float) Math.PI * (sample * numRotations) / numSamples;
            float r = radius * sample / (numSamples - 1) + (rnd.nextFloat() * 2.0f - 1.0f) * spacing * jitter;
            float x = (float) Math.sin_roquen_9(angle + 0.5f * (float) Math.PI) * r;
            float y = (float) Math.sin_roquen_9(angle) * r;
            callback.onNewSample(x, y);
        }
    }

}
