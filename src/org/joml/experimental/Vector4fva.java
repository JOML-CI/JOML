/*
 * The MIT License
 *
 * Copyright (c) 2021 Kai Burjack
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
package org.joml.experimental;

import java.text.NumberFormat;

import org.joml.Options;
import org.joml.Runtime;

//#ifdef __HAS_VECTOR_API__

import jdk.incubator.vector.FloatVector;

/**
 * @author Kai Burjack
 */
public class Vector4fva {

    final float[] v = new float[4];

    public Vector4fva() {
        v[3] = 1.0f;
    }

    public Vector4fva(float x, float y, float z, float w) {
        v[0] = x;
        v[1] = y;
        v[2] = z;
        v[3] = w;
    }

    public Vector4fva(Vector4fva v) {
        this.v[0] = v.v[0];
        this.v[1] = v.v[1];
        this.v[2] = v.v[2];
        this.v[3] = v.v[3];
    }

    public Vector4fva premul(Matrix4fva m, Vector4fva dest) {
        FloatVector p1 = FloatVector.broadcast(FloatVector.SPECIES_128, v[0]).mul(FloatVector.fromArray(FloatVector.SPECIES_128, m.m, 0));
        FloatVector p2 = FloatVector.broadcast(FloatVector.SPECIES_128, v[1]).fma(FloatVector.fromArray(FloatVector.SPECIES_128, m.m, 4), p1);
        FloatVector p3 = FloatVector.broadcast(FloatVector.SPECIES_128, v[2]).mul(FloatVector.fromArray(FloatVector.SPECIES_128, m.m, 8));
        FloatVector p4 = FloatVector.broadcast(FloatVector.SPECIES_128, v[3]).fma(FloatVector.fromArray(FloatVector.SPECIES_128, m.m, 12), p3);
        p2.add(p4).intoArray(dest.v, 0);
        return dest;
    }

    public String toString() {
        return Runtime.formatNumbers(toString(Options.NUMBER_FORMAT));
    }

    public String toString(NumberFormat formatter) {
        return "(" + Runtime.format(v[0], formatter) + " " + Runtime.format(v[1], formatter) + " " + Runtime.format(v[2], formatter) + " " + Runtime.format(v[3], formatter) + ")";
    }

}
//#endif
