/*
 * The MIT License
 *
 * Copyright (c) 2022-2023 JOML
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
package org.joml.jmh;

//#ifdef __HAS_FOREIGN_MEMORY_ACCESS_API__
import java.lang.foreign.MemorySegment;
//#endif
import jdk.incubator.vector.FloatVector;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.text.DecimalFormat;

import static java.nio.ByteBuffer.allocateDirect;
import static java.nio.ByteOrder.nativeOrder;
import static jdk.incubator.vector.FloatVector.*;
import static org.joml.jmh.Matrix4fv.*;

/**
 * 4x4 matrix backed by direct ByteBuffer.
 */
public class Matrix4fvBB {
    private final ByteBuffer es = allocateDirect(16 << 2).order(nativeOrder());

    public Matrix4fvBB() {
        // Initialize to identity
        es.putFloat(0, 1f);
        es.putFloat(5<<2, 1f);
        es.putFloat(10<<2, 1f);
        es.putFloat(15<<2, 1f);
    }

//#ifdef __HAS_FOREIGN_MEMORY_ACCESS_API__
    public Matrix4fvBB mul128Loop(Matrix4fvBB o) {
        /*
         * Adapted from:
         * https://stackoverflow.com/questions/18499971/efficient-4x4-matrix-multiplication-c-vs-assembly#answer-18508113
         */
        MemorySegment oms = MemorySegment.ofBuffer(o.es);
        MemorySegment tms = MemorySegment.ofBuffer(es);
        FloatVector row1 = fromMemorySegment(SPECIES_128, oms, 0, nativeOrder());
        FloatVector row2 = fromMemorySegment(SPECIES_128, oms, 16, nativeOrder());
        FloatVector row3 = fromMemorySegment(SPECIES_128, oms, 32, nativeOrder());
        FloatVector row4 = fromMemorySegment(SPECIES_128, oms, 48, nativeOrder());
        for (int i = 0; i < 4; i++) {
            FloatVector r = fromMemorySegment(SPECIES_128, tms, i<<4, nativeOrder());
            // _mm_set1_ps(A[4*i + 0])
            FloatVector b0 = r.rearrange(s0000);
            // _mm_set1_ps(A[4*i + 1])
            FloatVector b1 = r.rearrange(s1111);
            // _mm_set1_ps(A[4*i + 2])
            FloatVector b2 = r.rearrange(s2222);
            // _mm_set1_ps(A[4*i + 3])
            FloatVector b3 = r.rearrange(s3333);
            b0.fma(row1, b1.fma(row2, b2.fma(row3, b3.mul(row4)))).intoMemorySegment(tms, i<<4, nativeOrder());
        }
        return this;
    }

    public Matrix4fvBB mul128Unrolled(Matrix4fvBB o) {
        /*
         * Adapted from:
         * https://stackoverflow.com/questions/18499971/efficient-4x4-matrix-multiplication-c-vs-assembly#answer-18508113
         */
        MemorySegment oms = MemorySegment.ofBuffer(o.es);
        MemorySegment tms = MemorySegment.ofBuffer(es);
        FloatVector row1 = fromMemorySegment(SPECIES_128, oms, 0, nativeOrder());
        FloatVector row2 = fromMemorySegment(SPECIES_128, oms, 16, nativeOrder());
        FloatVector row3 = fromMemorySegment(SPECIES_128, oms, 32, nativeOrder());
        FloatVector row4 = fromMemorySegment(SPECIES_128, oms, 48, nativeOrder());
        FloatVector r0, r1, r2, r3;
        r0 = fromMemorySegment(SPECIES_128, tms, 0, nativeOrder());
        r0.rearrange(s0000).fma(row1, r0.rearrange(s1111).fma(row2, r0.rearrange(s2222).fma(row3, r0.rearrange(s3333).mul(row4)))).intoMemorySegment(tms, 0, nativeOrder());
        r1 = fromMemorySegment(SPECIES_128, tms, 16, nativeOrder());
        r1.rearrange(s0000).fma(row1, r1.rearrange(s1111).fma(row2, r1.rearrange(s2222).fma(row3, r1.rearrange(s3333).mul(row4)))).intoMemorySegment(tms, 16, nativeOrder());
        r2 = fromMemorySegment(SPECIES_128, tms, 32, nativeOrder());
        r2.rearrange(s0000).fma(row1, r2.rearrange(s1111).fma(row2, r2.rearrange(s2222).fma(row3, r2.rearrange(s3333).mul(row4)))).intoMemorySegment(tms, 32, nativeOrder());
        r3 = fromMemorySegment(SPECIES_128, tms, 48, nativeOrder());
        r3.rearrange(s0000).fma(row1, r3.rearrange(s1111).fma(row2, r3.rearrange(s2222).fma(row3, r3.rearrange(s3333).mul(row4)))).intoMemorySegment(tms, 48, nativeOrder());
        return this;
    }

    public Matrix4fvBB mul256(Matrix4fvBB o) {
        /*
         * Adapted from:
         * https://stackoverflow.com/questions/19806222/matrix-vector-multiplication-in-avx-not-proportionately-faster-than-in-sse#answer-46058667
         */
        MemorySegment oms = MemorySegment.ofBuffer(o.es);
        MemorySegment tms = MemorySegment.ofBuffer(es);
        FloatVector t0 = fromMemorySegment(SPECIES_256, tms, 0, nativeOrder());
        FloatVector t1 = fromMemorySegment(SPECIES_256, tms, 32, nativeOrder());
        FloatVector u0 = fromMemorySegment(SPECIES_256, oms, 0, nativeOrder());
        FloatVector u1 = fromMemorySegment(SPECIES_256, oms, 32, nativeOrder());
        FloatVector u0r00 = u0.rearrange(s01230123);
        FloatVector u1r00 = u1.rearrange(s01230123);
        FloatVector u0r11 = u0.rearrange(s45674567);
        FloatVector u1r11 = u1.rearrange(s45674567);
        t0.rearrange(s00004444).fma(u0r00, t0.rearrange(s11115555).mul(u0r11))
                .add(t0.rearrange(s33337777).fma(u1r11, t0.rearrange(s22226666).mul(u1r00)))
                .intoMemorySegment(tms, 0, nativeOrder());
        t1.rearrange(s00004444).fma(u0r00, t1.rearrange(s11115555).mul(u0r11))
                .add(t1.rearrange(s33337777).fma(u1r11, t1.rearrange(s22226666).mul(u1r00)))
                .intoMemorySegment(tms, 32, nativeOrder());
        return this;
    }
//#endif

    @Override
    public String toString() {
        DecimalFormat f = new DecimalFormat(" 0.000E0;-");
        FloatBuffer fb = es.asFloatBuffer();
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                str.append(f.format(fb.get(j * 4 + i)));
            }
            if (i < 3) {
                str.append("\n");
            }
        }
        return str.toString();
    }
}
//#endif