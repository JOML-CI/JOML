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
 * Internal random number generator.
 * 
 * @author Kai Burjack
 */
class Random {

    /**
     * Reference <a href="http://xoroshiro.di.unimi.it/xoroshiro128plus.c">http://xoroshiro.di.unimi.it/</a>
     */
    private static final class Xorshiro128 {
        /**
         * = 0x1p-24f
         */
        private static final float INT_TO_FLOAT = Float.intBitsToFloat(864026624);

        private static final boolean HAS_Long_rotateLeft = hasLongRotateLeft();
        private static boolean hasLongRotateLeft() {
            try {
                Long.class.getDeclaredMethod("rotateLeft", new Class[] { long.class, int.class });
                return true;
            } catch (NoSuchMethodException e) {
                return false;
            }
        }

        /**
         * Xorshiro128 state
         */
        private long _s0;
        private long _s1;

        /**
         * SplitMix64 State
         */
        private long state;

        Xorshiro128(long seed) {
            this.state = seed;
            this._s0 = nextSplitMix64();
            this._s0 = nextSplitMix64();
        }

        private long nextSplitMix64() {
            long z = state += 0x9e3779b97f4a7c15L;
            z = (z ^ (z >>> 30)) * 0xbf58476d1ce4e5b9L;
            z = (z ^ (z >>> 27)) * 0x94d049bb133111ebL;
            return z ^ (z >>> 31);
        }

        /**
         * Reference: <a href="https://github.com/roquendm/JGO-Grabbag/blob/master/src/roquen/math/rng/PRNG.java">https://github.com/roquendm/</a>
         * 
         * @author roquendm
         */
        final float nextFloat() {
            return (nextInt() >>> 8) * INT_TO_FLOAT;
        }

        private int nextInt() {
            long s0 = _s0;
            long s1 = _s1;
            long result = s0 + s1;
            s1 ^= s0;
            rotateLeft(s0, s1);
            return (int) (result & 0xFFFFFFFF);
        }

        private static long rotl_JDK4(final long x, final int k) {
            return (x << k) | (x >>> (64 - k));
        }
        private static long rotl_JDK5(final long x, final int k) {
            return Long.rotateLeft(x, k);
        }
        private static long rotl(final long x, final int k) {
            if (HAS_Long_rotateLeft)
                return rotl_JDK5(x, k);
            return rotl_JDK4(x, k);
        }
        private void rotateLeft(long s0, long s1) {
            _s0 = rotl(s0, 55) ^ s1 ^ (s1 << 14);
            _s1 = rotl(s1, 36);
        }

        /**
         * Reference: <a href="https://github.com/roquendm/JGO-Grabbag/blob/master/src/roquen/math/rng/PRNG.java">https://github.com/roquendm/</a>
         * 
         * @author roquendm
         */
        final int nextInt(int n) {
            // See notes in nextInt. This is
            // (on average) a better choice for
            // 64-bit VMs.
            long r = nextInt() >>> 1;
            // sign doesn't matter here
            r = (r * n) >> 31;
            return (int) r;
        }
    }

    private final Xorshiro128 rnd;

    /**
     * Create a new instance of {@link Random} and initialize it with the given <code>seed</code>.
     * 
     * @param seed
     *            the seed number
     */
    Random(long seed) {
        this.rnd = new Xorshiro128(seed);
    }

    /**
     * @return a random float within [0..1)
     */
    float nextFloat() {
        return rnd.nextFloat();
    }

    /**
     * @return a random integer in the range [0..n)
     */
    int nextInt(int n) {
        return rnd.nextInt(n);
    }

}
