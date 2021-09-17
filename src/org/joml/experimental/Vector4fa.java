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

import java.text.NumberFormat;

import org.joml.Options;
import org.joml.Runtime;

import jdk.incubator.vector.FloatVector;

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

    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Float.floatToIntBits(arr[0]);
        result = prime * result + Float.floatToIntBits(arr[1]);
        result = prime * result + Float.floatToIntBits(arr[2]);
        result = prime * result + Float.floatToIntBits(arr[3]);
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Vector4fa other = (Vector4fa) obj;
        return equals(other.arr[0], other.arr[1], other.arr[2], other.arr[3]);
    }

    public boolean equals(Vector4fa v, float delta) {
        if (this == v)
            return true;
        if (v == null)
            return false;
        if (!Runtime.equals(arr[0], v.arr[0], delta))
            return false;
        if (!Runtime.equals(arr[1], v.arr[1], delta))
            return false;
        if (!Runtime.equals(arr[2], v.arr[2], delta))
            return false;
        if (!Runtime.equals(arr[3], v.arr[3], delta))
            return false;
        return true;
    }

    public boolean equals(float x, float y, float z, float w) {
        if (Float.floatToIntBits(arr[0]) != Float.floatToIntBits(x))
            return false;
        if (Float.floatToIntBits(arr[1]) != Float.floatToIntBits(y))
            return false;
        if (Float.floatToIntBits(arr[2]) != Float.floatToIntBits(z))
            return false;
        if (Float.floatToIntBits(arr[3]) != Float.floatToIntBits(w))
            return false;
        return true;
    }

    public String toString() {
        return Runtime.formatNumbers(toString(Options.NUMBER_FORMAT));
    }

    public String toString(NumberFormat formatter) {
        return "(" + Runtime.format(arr[0], formatter) + " " + Runtime.format(arr[1], formatter) + " " + Runtime.format(arr[2], formatter) + " " + Runtime.format(arr[3], formatter) + ")";
    }
}
//#endif