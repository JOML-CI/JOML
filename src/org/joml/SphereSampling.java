/*
 * (C) Copyright 2016 JOML

 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in
 all copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 THE SOFTWARE.

 */
package org.joml;

/**
 * Creates samples on a unit sphere.
 * 
 * @author Kai Burjack
 */
public class SphereSampling {

    private final Random rnd;

    /**
     * Create a new instance of {@link SphereSampling} and initialize the random number generator with the given
     * <code>seed</code>.
     * 
     * @param seed
     *            the seed to initialize the random number generator with
     */
    public SphereSampling(long seed) {
        this.rnd = new Random(seed);
    }

    /**
     * Create <code>numSamples</code> number of samples which are uniformly distributed on a unit sphere, and call the
     * given <code>callback</code> for each sample generated.
     * <p>
     * Reference: <a href="http://mathworld.wolfram.com/SpherePointPicking.html">http://mathworld.wolfram.com/</a>
     * 
     * @param numSamples
     *            the number of samples to generate
     * @param callback
     *            will be called for each sample generated
     */
    public void generateUniform(int numSamples, Sampling3dCallback callback) {
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
