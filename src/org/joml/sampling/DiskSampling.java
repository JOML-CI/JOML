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
package org.joml.sampling;

/**
 * Creates samples on a unit disk.
 * 
 * @author Kai Burjack
 */
public class DiskSampling {

    private final Random rnd;

    /**
     * Create a new instance of {@link DiskSampling} and initialize the random number generator with the given
     * <code>seed</code>.
     * 
     * @param seed
     *            the seed to initialize the random number generator with
     */
    public DiskSampling(long seed) {
        this.rnd = new Random(seed);
    }

    /**
     * Create <code>numSamples</code> number of samples which are distributed towards the center of a unit disk, and
     * call the given <code>callback</code> for each sample generated.
     * <p>
     * Reference: <a href="http://mathworld.wolfram.com/DiskPointPicking.html">http://mathworld.wolfram.com/</a>
     * 
     * @param numSamples
     *            the number of samples to generate
     * @param callback
     *            will be called for each sample generated
     */
    public void generateCentered(int numSamples, Callback2d callback) {
        for (int i = 0; i < numSamples; i++) {
            float r = rnd.nextFloat();
            float a = rnd.nextFloat() * 2.0f * (float) Math.PI;
            float x = r * (float) Math.sin_roquen_9(a + 0.5 * Math.PI);
            float y = r * (float) Math.sin_roquen_9(a);
            callback.onNewSample(x, y);
        }
    }

    /**
     * Create <code>numSamples</code> number of samples which are uniformly distributed on a unit disk, and call the
     * given <code>callback</code> for each sample generated.
     * <p>
     * Reference: <a href="http://mathworld.wolfram.com/DiskPointPicking.html">http://mathworld.wolfram.com/</a>
     * 
     * @param numSamples
     *            the number of samples to generate
     * @param callback
     *            will be called for each sample generated
     */
    public void generateUniform(int numSamples, Callback2d callback) {
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
