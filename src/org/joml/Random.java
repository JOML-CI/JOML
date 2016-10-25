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
 * Random number generator.
 * 
 * @author Kai Burjack
 */
class Random {

    /**
     * Currently, we just use Java's {@link java.util.Random} class.
     */
    private final java.util.Random rnd;

    /**
     * Create a new instance of {@link Random} and initialize it with the given <code>seed</code>.
     * 
     * @param seed
     *            the seed number
     */
    Random(long seed) {
        this.rnd = new java.util.Random(seed);
    }

    /**
     * @return a random float within [0..1)
     */
    float nextFloat() {
        return rnd.nextFloat();
    }

}
