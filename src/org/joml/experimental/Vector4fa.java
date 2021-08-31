/*
 * The MIT License
 *
 * Copyright (c) 2021 JOML
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
//#ifdef __HAS_VECTOR_API__
package org.joml.experimental;

import jdk.incubator.vector.*;

/**
 * 4D vector backed by a Java primitive float array.
 * 
 * @author Kai Burjack
 */
public class Vector4fa {
    private final float[] arr = new float[4];

    public Vector4fa() {
        arr[3] = 1;
    }

    public Vector4fa(float x, float y, float z, float w) {
        arr[0] = x;
        arr[1] = y;
        arr[2] = z;
        arr[3] = w;
    }

    public Vector4fa add(Vector4fa v) {
        FloatVector.fromArray(FloatVector.SPECIES_128, arr, 0)
                .add(FloatVector.fromArray(FloatVector.SPECIES_128, v.arr, 0))
                .intoArray(arr, 0);
        return this;
    }

    @Override
    public String toString() {
        return arr[0] + " " + arr[1] + " " + arr[2] + " " + arr[3];
    }
}
//#endif