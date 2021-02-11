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

import static java.nio.ByteOrder.nativeOrder;

//#ifdef __HAS_VECTOR_API__

import static jdk.incubator.vector.FloatVector.*;

import java.nio.*;
import java.text.*;

import org.joml.Math;
import org.joml.Runtime;
import org.joml.Vector3fc;

import jdk.incubator.vector.*;

/**
 * @author Kai Burjack
 */
public class Matrix4fva {

    private static float[] IDENTITY = {1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1};
    private static final VectorSpecies<Float> S4 = SPECIES_128;
    private static final VectorSpecies<Float> S8 = SPECIES_256;
    private static final VectorSpecies<Float> S16 = SPECIES_512;
    private static final VectorShuffle<Float> s0000 = S4.shuffleFromValues(0, 0, 0, 0);
    private static final VectorShuffle<Float> s1111 = S4.shuffleFromValues(1, 1, 1, 1);
    private static final VectorShuffle<Float> s2222 = S4.shuffleFromValues(2, 2, 2, 2);
    private static final VectorShuffle<Float> s3333 = S4.shuffleFromValues(3, 3, 3, 3);
    private static final VectorShuffle<Float> s0415 = S4.shuffleFromValues(0, 4, 1, 5);
    private static final VectorShuffle<Float> s2637 = S4.shuffleFromValues(2, 6, 3, 7);
    private static final VectorShuffle<Float> s2301 = S4.shuffleFromValues(2, 3, 0, 1);
    private static final VectorShuffle<Float> s1032 = S4.shuffleFromValues(1, 0, 3, 2);
    // _MM_SHUFFLE(0, 0, 0, 0)
    private static final VectorShuffle<Float> _00004444 = S8.shuffleFromValues(0, 0, 0, 0, 4, 4, 4, 4);
    // _MM_SHUFFLE(1, 1, 1, 1)
    private static final VectorShuffle<Float> _11115555 = S8.shuffleFromValues(1, 1, 1, 1, 5, 5, 5, 5);
    // _MM_SHUFFLE(2, 2, 2, 2)
    private static final VectorShuffle<Float> _22226666 = S8.shuffleFromValues(2, 2, 2, 2, 6, 6, 6, 6);
    // _MM_SHUFFLE(3, 3, 3, 3)
    private static final VectorShuffle<Float> _33337777 = S8.shuffleFromValues(3, 3, 3, 3, 7, 7, 7, 7);
    // _mm256_permute2f128_ps(..., 0x00);
    private static final VectorShuffle<Float> _01230123 = S8.shuffleFromValues(0, 1, 2, 3, 0, 1, 2, 3);
    // _mm256_permute2f128_ps(..., 0x11);
    private static final VectorShuffle<Float> _45674567 = S8.shuffleFromValues(4, 5, 6, 7, 4, 5, 6, 7);

    private static final boolean HAS_AVX = SPECIES_PREFERRED.length() >= 8;
    private static final boolean HAS_AVX512 = SPECIES_PREFERRED.length() >= 16;

    final float[] m = new float[16];

    public Matrix4fva() {
        m[0] = 1.0f;
        m[5] = 1.0f;
        m[10] = 1.0f;
        m[15] = 1.0f;
    }

    public Matrix4fva(float m00, float m01, float m02, float m03, float m10, float m11, float m12, float m13, float m20,
            float m21, float m22, float m23, float m30, float m31, float m32, float m33) {
        m[0] = m00;
        m[1] = m01;
        m[2] = m02;
        m[3] = m03;
        m[4] = m10;
        m[5] = m11;
        m[6] = m12;
        m[7] = m13;
        m[8] = m20;
        m[9] = m21;
        m[10] = m22;
        m[11] = m23;
        m[12] = m30;
        m[13] = m31;
        m[14] = m32;
        m[15] = m33;
    }

    public Matrix4fva(Matrix4fva m) {
        set(m);
    }

    public Matrix4fva(float[] ms) {
        set(ms);
    }

    public Matrix4fva(Vector4fva c0, Vector4fva c1, Vector4fva c2, Vector4fva c3) {
        fromArray(S4, c0.v, 0).intoArray(m, 0);
        fromArray(S4, c1.v, 0).intoArray(m, 4);
        fromArray(S4, c2.v, 0).intoArray(m, 8);
        fromArray(S4, c3.v, 0).intoArray(m, 12);
    }

    public Matrix4fva set(Matrix4fva m) {
        return set(m.m);
    }

    public Matrix4fva set(float[] ms) {
        if (HAS_AVX512) {
            set512(ms);
        } else if (HAS_AVX) {
            set256(ms);
        } else {
            set128(ms);
        }
        return this;
    }

    public Matrix4fva identity() {
        if (HAS_AVX512) {
            set512(IDENTITY);
        } else if (HAS_AVX) {
            set256(IDENTITY);
        } else {
            set128(IDENTITY);
        }
        return this;
    }

    private void set128(float[] arr) {
        fromArray(S4, arr, 0).intoArray(m, 0);
        fromArray(S4, arr, 4).intoArray(m, 4);
        fromArray(S4, arr, 8).intoArray(m, 8);
        fromArray(S4, arr, 12).intoArray(m, 12);
    }

    private void set256(float[] arr) {
        fromArray(S8, arr, 0).intoArray(m, 0);
        fromArray(S8, arr, 8).intoArray(m, 8);
    }

    private void set512(float[] arr) {
        fromArray(S16, arr, 0).intoArray(m, 0);
    }

    public Matrix4fva mul(Matrix4fva o) {
        return mul(o, this);
    }
    public Matrix4fva mul(Matrix4fva o, Matrix4fva dest) {
        if (HAS_AVX) {
            return mul256(o, dest);
        }
        return mul128(o, dest);
    }

    private Matrix4fva mul128(Matrix4fva o, Matrix4fva dest) {
        FloatVector c4 = fromArray(S4, o.m, 12);
        FloatVector r0 = fromArray(S4, m, 0);
        FloatVector v1 = r0.rearrange(s3333).mul(c4);
        FloatVector r1 = fromArray(S4, m, 4);
        FloatVector v2 = r1.rearrange(s3333).mul(c4);
        FloatVector r2 = fromArray(S4, m, 8);
        FloatVector v3 = r2.rearrange(s3333).mul(c4);
        FloatVector r3 = fromArray(S4, m, 12);
        FloatVector v4 = r3.rearrange(s3333).mul(c4);
        FloatVector c3 = fromArray(S4, o.m, 8);
        FloatVector t1 = r0.rearrange(s2222).fma(c3, v1);
        FloatVector t2 = r1.rearrange(s2222).fma(c3, v2);
        FloatVector t3 = r2.rearrange(s2222).fma(c3, v3);
        FloatVector t4 = r3.rearrange(s2222).fma(c3, v4);
        FloatVector c2 = fromArray(S4, o.m, 4);
        FloatVector k1 = r0.rearrange(s1111).fma(c2, t1);
        FloatVector k2 = r1.rearrange(s1111).fma(c2, t2);
        FloatVector k3 = r2.rearrange(s1111).fma(c2, t3);
        FloatVector k4 = r3.rearrange(s1111).fma(c2, t4);
        FloatVector c1 = fromArray(S4, o.m, 0);
        r0.rearrange(s0000).fma(c1, k1).intoArray(dest.m, 0);
        r1.rearrange(s0000).fma(c1, k2).intoArray(dest.m, 4);
        r2.rearrange(s0000).fma(c1, k3).intoArray(dest.m, 8);
        r3.rearrange(s0000).fma(c1, k4).intoArray(dest.m, 12);
        return dest;
    }

    private Matrix4fva mul256(Matrix4fva o, Matrix4fva dest) {
        FloatVector t0 = fromArray(S8, o.m, 0);
        FloatVector t1 = fromArray(S8, o.m, 8);
        FloatVector t2 = fromArray(S8, m, 0);
        t2.rearrange(_00004444).fma(t0.rearrange(_01230123), t2.rearrange(_11115555).mul(t0.rearrange(_45674567)))
                .add(t2.rearrange(_33337777).fma(t1.rearrange(_45674567),
                        t2.rearrange(_22226666).mul(t1.rearrange(_01230123))))
                .intoArray(dest.m, 0);
        FloatVector t3 = fromArray(S8, m, 8);
        t3.rearrange(_00004444).fma(t0.rearrange(_01230123), t3.rearrange(_11115555).mul(t0.rearrange(_45674567)))
                .add(t3.rearrange(_33337777).fma(t1.rearrange(_45674567),
                        t3.rearrange(_22226666).mul(t1.rearrange(_01230123))))
                .intoArray(dest.m, 8);
        return dest;
    }

    public Vector4fva mul(Vector4fva v) {
        return v.premul(this, v);
    }

    public Vector4fva mul(Vector4fva v, Vector4fva dest) {
        return v.premul(this, dest);
    }

    public Matrix4fva invert() {
        return invert(this);
    }

    public Matrix4fva invert(Matrix4fva dest) {
        invert128(dest);
        return dest;
    }

    private void invert128(Matrix4fva dest) {
        /*
         * Adapted from: https://github.com/niswegmann/small-matrix-inverse/blob/master/invert4x4_llvm.h
         */
        FloatVector col0 = fromArray(S4, m, 0);
        FloatVector col1 = fromArray(S4, m, 4);
        FloatVector col2 = fromArray(S4, m, 8);
        FloatVector col3 = fromArray(S4, m, 12);
        // tmp1 = __builtin_shufflevector(col0, col2, 0, 4, 1, 5);
        FloatVector tmp1 = col0.rearrange(s0415, col2);
        // row1 = __builtin_shufflevector(col1, col3, 0, 4, 1, 5);
        FloatVector row1 = col1.rearrange(s0415, col3);
        // row0 = __builtin_shufflevector(tmp1, row1, 0, 4, 1, 5);
        FloatVector row0 = tmp1.rearrange(s0415, row1);
        // row1 = __builtin_shufflevector(tmp1, row1, 2, 6, 3, 7);
        row1 = tmp1.rearrange(s2637, row1);
        // tmp1 = __builtin_shufflevector(col0, col2, 2, 6, 3, 7);
        tmp1 = col0.rearrange(s2637, col2);
        // row3 = __builtin_shufflevector(col1, col3, 2, 6, 3, 7);
        FloatVector row3 = col1.rearrange(s2637, col3);
        // row2 = __builtin_shufflevector(tmp1, row3, 0, 4, 1, 5);
        FloatVector row2 = tmp1.rearrange(s0415, row3);
        // row3 = __builtin_shufflevector(tmp1, row3, 2, 6, 3, 7);
        row3 = tmp1.rearrange(s2637, row3);
        // row1 = __builtin_shufflevector(row1, row1, 2, 3, 0, 1);
        row1 = row1.rearrange(s2301);
        // row3 = __builtin_shufflevector(row3, row3, 2, 3, 0, 1);
        row3 = row3.rearrange(s2301);
        // tmp1 = row2 * row3;
        tmp1 = row2.mul(row3);
        // tmp1 = __builtin_shufflevector(tmp1, tmp1, 1, 0, 7, 6);
        tmp1 = tmp1.rearrange(s1032);
        // col0 = row1 * tmp1;
        col0 = row1.mul(tmp1);
        // col1 = row0 * tmp1;
        col1 = row0.mul(tmp1);
        // tmp1 = __builtin_shufflevector(tmp1, tmp1, 2, 3, 4, 5);
        tmp1 = tmp1.rearrange(s2301);
        // col0 = row1 * tmp1 - col0;
        col0 = row1.fma(tmp1, col0.neg());
        // col1 = row0 * tmp1 - col1;
        col1 = row0.fma(tmp1, col1.neg());
        // col1 = __builtin_shufflevector(col1, col1, 2, 3, 4, 5);
        col1 = col1.rearrange(s2301);
        // tmp1 = row1 * row2;
        tmp1 = row1.mul(row2);
        // tmp1 = __builtin_shufflevector(tmp1, tmp1, 1, 0, 7, 6);
        tmp1 = tmp1.rearrange(s1032);
        // col0 = row3 * tmp1 + col0;
        col0 = row3.fma(tmp1, col0);
        // col3 = row0 * tmp1;
        col3 = row0.mul(tmp1);
        // tmp1 = __builtin_shufflevector(tmp1, tmp1, 2, 3, 4, 5);
        tmp1 = tmp1.rearrange(s2301);
        // col0 = col0 - row3 * tmp1;
        col0 = col0.sub(row3.mul(tmp1));
        // col3 = row0 * tmp1 - col3;
        col3 = row0.fma(tmp1, col3.neg());
        // col3 = __builtin_shufflevector(col3, col3, 2, 3, 4, 5);
        col3 = col3.rearrange(s2301);
        // tmp1 = __builtin_shufflevector(row1, row1, 2, 3, 4, 5) * row3;
        tmp1 = row1.rearrange(s2301).mul(row3);
        // tmp1 = __builtin_shufflevector(tmp1, tmp1, 1, 0, 7, 6);
        tmp1 = tmp1.rearrange(s1032);
        // row2 = __builtin_shufflevector(row2, row2, 2, 3, 4, 5);
        row2 = row2.rearrange(s2301);
        // col0 = row2 * tmp1 + col0;
        col0 = row2.fma(tmp1, col0);
        // col2 = row0 * tmp1;
        col2 = row0.mul(tmp1);
        // tmp1 = __builtin_shufflevector(tmp1, tmp1, 2, 3, 4, 5);
        tmp1 = tmp1.rearrange(s2301);
        // col0 = col0 - row2 * tmp1;
        col0 = col0.sub(row2.mul(tmp1));
        // col2 = row0 * tmp1 - col2;
        col2 = row0.fma(tmp1, col2.neg());
        // col2 = __builtin_shufflevector(col2, col2, 2, 3, 4, 5);
        col2 = col2.rearrange(s2301);
        // tmp1 = row0 * row1;
        tmp1 = row0.mul(row1);
        // tmp1 = __builtin_shufflevector(tmp1, tmp1, 1, 0, 7, 6);
        tmp1 = tmp1.rearrange(s1032);
        // col2 = row3 * tmp1 + col2;
        col2 = row3.fma(tmp1, col2);
        // col3 = row2 * tmp1 - col3;
        col3 = row2.fma(tmp1, col3.neg());
        // tmp1 = __builtin_shufflevector(tmp1, tmp1, 2, 3, 4, 5);
        tmp1 = tmp1.rearrange(s2301);
        // col2 = row3 * tmp1 - col2;
        col2 = row3.fma(tmp1, col2.neg());
        // col3 = col3 - row2 * tmp1;
        col3 = col3.sub(row2.mul(tmp1));
        // tmp1 = row0 * row3;
        tmp1 = row0.mul(row3);
        // tmp1 = __builtin_shufflevector(tmp1, tmp1, 1, 0, 7, 6);
        tmp1 = tmp1.rearrange(s1032);
        // col1 = col1 - row2 * tmp1;
        col1 = col1.sub(row2.mul(tmp1));
        // col2 = row1 * tmp1 + col2;
        col2 = row1.fma(tmp1, col2);
        // tmp1 = __builtin_shufflevector(tmp1, tmp1, 2, 3, 4, 5);
        tmp1 = tmp1.rearrange(s2301);
        // col1 = row2 * tmp1 + col1;
        col1 = row2.fma(tmp1, col1);
        // col2 = col2 - row1 * tmp1;
        col2 = col2.sub(row1.mul(tmp1));
        // tmp1 = row0 * row2;
        tmp1 = row0.mul(row2);
        // tmp1 = __builtin_shufflevector(tmp1, tmp1, 1, 0, 7, 6);
        tmp1 = tmp1.rearrange(s1032);
        // col1 = row3 * tmp1 + col1;
        col1 = row3.fma(tmp1, col1);
        // col3 = col3 - row1 * tmp1;
        col3 = col3.sub(row1.mul(tmp1));
        // tmp1 = __builtin_shufflevector(tmp1, tmp1, 2, 3, 4, 5);
        tmp1 = tmp1.rearrange(s2301);
        // col1 = col1 - row3 * tmp1;
        col1 = col1.sub(row3.mul(tmp1));
        // col3 = row1 * tmp1 + col3;
        col3 = row1.fma(tmp1, col3);
        // det = row0 * col0;
        FloatVector det = row0.mul(col0);
        // det = __builtin_shufflevector(det, det, 2, 3, 4, 5) + det;
        det = det.rearrange(s2301).add(det);
        // det = __builtin_shufflevector(det, det, 1, 0, 7, 6) + det;
        det = det.rearrange(s1032).add(det);
        // det = 1.0f / det;
        det = broadcast(S4, 1.0f).div(det);
        // col0 = col0 * det;
        col0.mul(det).intoArray(dest.m, 0);
        // col1 = col1 * det;
        col1.mul(det).intoArray(dest.m, 4);
        // col2 = col2 * det;
        col2.mul(det).intoArray(dest.m, 8);
        // col3 = col3 * det;
        col3.mul(det).intoArray(dest.m, 12);
    }

    public Vector4fva transform(Vector4fva v) {
        return mul(v);
    }

    public Vector4fva transform(Vector4fva v, Vector4fva dest) {
        return mul(v, dest);
    }

    public Matrix4fva translate(Vector4fva v) {
        return translate(v, this);
    }
    public Matrix4fva translate(Vector4fva v, Matrix4fva dest) {
        fromArray(S4, m, 0)
                .fma(broadcast(S4, v.v[0]),
                        fromArray(S4, m, 4).fma(broadcast(S4, v.v[1]),
                                fromArray(S4, m, 8).fma(broadcast(S4, v.v[2]), fromArray(S4, m, 12))))
                .intoArray(dest.m, 12);
        return dest;
    }

    public Matrix4fva rotateX(float ang, Matrix4fva dest) {
        float sin = Math.sin(ang), cos = Math.cosFromSin(sin, ang);
        FloatVector s = broadcast(S4, sin), c = broadcast(S4, cos);
        FloatVector c1 = fromArray(S4, m, 4);
        FloatVector c2 = fromArray(S4, m, 8);
        c1.fma(c, c2.mul(s)).intoArray(dest.m, 4);
        c1.fma(s.neg(), c2.mul(c)).intoArray(dest.m, 8);
        fromArray(S4, m, 0).intoArray(dest.m, 0);
        fromArray(S4, m, 12).intoArray(dest.m, 12);
        return dest;
    }

    public Matrix4fva rotateX(float ang) {
        return rotateX(ang, this);
    }

    public Matrix4fva rotateY(float ang, Matrix4fva dest) {
        float sin = Math.sin(ang), cos = Math.cosFromSin(sin, ang);
        FloatVector s = broadcast(S4, sin), c = broadcast(S4, cos);
        FloatVector c0 = fromArray(S4, m, 0);
        FloatVector c2 = fromArray(S4, m, 8);
        c0.fma(c, c2.mul(s.neg())).intoArray(dest.m, 0);
        c0.fma(s, c2.mul(c)).intoArray(dest.m, 8);
        fromArray(S4, m, 4).intoArray(dest.m, 4);
        fromArray(S4, m, 12).intoArray(dest.m, 12);
        return dest;
    }

    public Matrix4fva rotateY(float ang) {
        return rotateY(ang, this);
    }

    public Matrix4fva rotateZ(float ang, Matrix4fva dest) {
        float sin = Math.sin(ang), cos = Math.cosFromSin(sin, ang);
        FloatVector s = broadcast(S4, sin), c = broadcast(S4, cos);
        FloatVector c0 = fromArray(S4, m, 0);
        FloatVector c1 = fromArray(S4, m, 4);
        c0.fma(c, c1.mul(s)).intoArray(dest.m, 0);
        c0.fma(s.neg(), c1.mul(c)).intoArray(dest.m, 4);
        fromArray(S4, m, 8).intoArray(dest.m, 8);
        fromArray(S4, m, 12).intoArray(dest.m, 12);
        return dest;
    }

    public Matrix4fva rotateZ(float ang) {
        return rotateZ(ang, this);
    }

    public Matrix4fva scale(float xyz) {
        return scale(xyz, xyz, xyz, this);
    }

    public Matrix4fva scale(float xyz, Matrix4fva dest) {
        return scale(xyz, xyz, xyz, dest);
    }

    public Matrix4fva scale(float x, float y, float z) {
        return scale(x, y, z, this);
    }

    public Matrix4fva scale(float x, float y, float z, Matrix4fva dest) {
        return scaleGeneric(x, y, z, dest);
    }

    private Matrix4fva scaleGeneric(float x, float y, float z, Matrix4fva dest) {
        fromArray(S4, m, 0).mul(broadcast(S4, x)).intoArray(dest.m, 0);
        fromArray(S4, m, 4).mul(broadcast(S4, y)).intoArray(dest.m, 4);
        fromArray(S4, m, 8).mul(broadcast(S4, z)).intoArray(dest.m, 8);
        fromArray(S4, m, 12).intoArray(dest.m, 12);
        return dest;
    }

    public Matrix4fva perspective(float fovy, float aspect, float zNear, float zFar) {
        return perspective(fovy, aspect, zNear, zFar, false, this);
    }
    public Matrix4fva perspective(float fovy, float aspect, float zNear, float zFar, boolean zZeroToOne) {
        return perspective(fovy, aspect, zNear, zFar, zZeroToOne, this);
    }
    public Matrix4fva perspective(float fovy, float aspect, float zNear, float zFar, boolean zZeroToOne, Matrix4fva dest) {
        float h = Math.tan(fovy * 0.5f);
        float rm00 = 1.0f / (h * aspect);
        float rm11 = 1.0f / h;
        float rm22;
        float rm32;
        boolean farInf = zFar > 0 && Float.isInfinite(zFar);
        boolean nearInf = zNear > 0 && Float.isInfinite(zNear);
        if (farInf) {
            float e = 1E-6f;
            rm22 = e - 1.0f;
            rm32 = (e - (zZeroToOne ? 1.0f : 2.0f)) * zNear;
        } else if (nearInf) {
            float e = 1E-6f;
            rm22 = (zZeroToOne ? 0.0f : 1.0f) - e;
            rm32 = ((zZeroToOne ? 1.0f : 2.0f) - e) * zFar;
        } else {
            rm22 = (zZeroToOne ? zFar : zFar + zNear) / (zNear - zFar);
            rm32 = (zZeroToOne ? zFar : zFar + zFar) * zNear / (zNear - zFar);
        }
        fromArray(S4, m, 0).mul(broadcast(S4, rm00)).intoArray(dest.m, 0);
        fromArray(S4, m, 4).mul(broadcast(S4, rm11)).intoArray(dest.m, 4);
        FloatVector t2 = fromArray(S4, m, 8);
        t2.fma(broadcast(S4, rm22), fromArray(S4, m, 12).neg()).intoArray(dest.m, 8);
        t2.mul(broadcast(S4, rm32)).intoArray(dest.m, 12);
        return dest;
    }

    public Matrix4fva lookAt(Vector3fc eye, Vector3fc center, Vector3fc up) {
        return lookAt(eye.x(), eye.y(), eye.z(), center.x(), center.y(), center.z(), up.x(), up.y(), up.z(), this);
    }
    public Matrix4fva lookAt(Vector3fc eye, Vector3fc center, Vector3fc up, Matrix4fva dest) {
        return lookAt(eye.x(), eye.y(), eye.z(), center.x(), center.y(), center.z(), up.x(), up.y(), up.z(), dest);
    }
    public Matrix4fva lookAt(
            float eyeX, float eyeY, float eyeZ,
            float centerX, float centerY, float centerZ,
            float upX, float upY, float upZ) {
        return lookAt(eyeX, eyeY, eyeZ, centerX, centerY, centerZ, upX, upY, upZ, this);
    }
    public Matrix4fva lookAt(
            float eyeX, float eyeY, float eyeZ,
            float centerX, float centerY, float centerZ,
            float upX, float upY, float upZ, Matrix4fva dest) {
        float dirX, dirY, dirZ;
        dirX = eyeX - centerX;
        dirY = eyeY - centerY;
        dirZ = eyeZ - centerZ;
        float invDirLength = Math.invsqrt(dirX * dirX + dirY * dirY + dirZ * dirZ);
        dirX *= invDirLength;
        dirY *= invDirLength;
        dirZ *= invDirLength;
        float leftX, leftY, leftZ;
        leftX = upY * dirZ - upZ * dirY;
        leftY = upZ * dirX - upX * dirZ;
        leftZ = upX * dirY - upY * dirX;
        float invLeftLength = Math.invsqrt(leftX * leftX + leftY * leftY + leftZ * leftZ);
        leftX *= invLeftLength;
        leftY *= invLeftLength;
        leftZ *= invLeftLength;
        float upnX = dirY * leftZ - dirZ * leftY;
        float upnY = dirZ * leftX - dirX * leftZ;
        float upnZ = dirX * leftY - dirY * leftX;
        float rm30 = -(leftX * eyeX + leftY * eyeY + leftZ * eyeZ);
        float rm31 = -(upnX * eyeX + upnY * eyeY + upnZ * eyeZ);
        float rm32 = -(dirX * eyeX + dirY * eyeY + dirZ * eyeZ);
        FloatVector c0 = fromArray(S4, m, 0);
        FloatVector c1 = fromArray(S4, m, 4);
        FloatVector c2 = fromArray(S4, m, 8);
        c0.fma(broadcast(S4, leftX), c1.fma(broadcast(S4, upnX), c2.mul(broadcast(S4, dirX)))).intoArray(dest.m, 0);
        c0.fma(broadcast(S4, leftY), c1.fma(broadcast(S4, upnY), c2.mul(broadcast(S4, dirY)))).intoArray(dest.m, 4);
        c0.fma(broadcast(S4, leftZ), c1.fma(broadcast(S4, upnZ), c2.mul(broadcast(S4, dirZ)))).intoArray(dest.m, 8);
        c0.fma(broadcast(S4, rm30), c1.fma(broadcast(S4, rm31), c2.fma(broadcast(S4, rm32), fromArray(S4, m, 12)))).intoArray(dest.m, 12);
        return dest;
    }

    public String toString() {
        DecimalFormat formatter = new DecimalFormat(" 0.000E0;-");
        String str = toString(formatter);
        StringBuffer res = new StringBuffer();
        int eIndex = Integer.MIN_VALUE;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c == 'E') {
                eIndex = i;
            } else if (c == ' ' && eIndex == i - 1) {
                // workaround Java 1.4 DecimalFormat bug
                res.append('+');
                continue;
            } else if (Character.isDigit(c) && eIndex == i - 1) {
                res.append('+');
            }
            res.append(c);
        }
        return res.toString();
    }

    public ByteBuffer get(ByteBuffer bb) {
        if (HAS_AVX512) {
            return store512(bb);
        } else if (HAS_AVX) {
            return store256(bb);
        }
        return store128(bb);
    }
    private ByteBuffer store128(ByteBuffer bb) {
        fromArray(S4, m, 0).intoByteBuffer(bb, bb.position(), nativeOrder());
        fromArray(S4, m, 4).intoByteBuffer(bb, bb.position() + 16, nativeOrder());
        fromArray(S4, m, 8).intoByteBuffer(bb, bb.position() + 32, nativeOrder());
        fromArray(S4, m, 12).intoByteBuffer(bb, bb.position() + 48, nativeOrder());
        return bb;
    }
    private ByteBuffer store256(ByteBuffer bb) {
        fromArray(S8, m, 0).intoByteBuffer(bb, bb.position(), nativeOrder());
        fromArray(S8, m, 8).intoByteBuffer(bb, bb.position() + 32, nativeOrder());
        return bb;
    }
    private ByteBuffer store512(ByteBuffer bb) {
        fromArray(S16, m, 0).intoByteBuffer(bb, bb.position(), nativeOrder());
        return bb;
    }

    public String toString(NumberFormat formatter) {
        return Runtime.format(m[0], formatter) + " " + Runtime.format(m[4], formatter) + " " + Runtime.format(m[8], formatter) + " " + Runtime.format(m[12], formatter) + "\n"
             + Runtime.format(m[1], formatter) + " " + Runtime.format(m[5], formatter) + " " + Runtime.format(m[9], formatter) + " " + Runtime.format(m[13], formatter) + "\n"
             + Runtime.format(m[2], formatter) + " " + Runtime.format(m[6], formatter) + " " + Runtime.format(m[10], formatter) + " " + Runtime.format(m[14], formatter) + "\n"
             + Runtime.format(m[3], formatter) + " " + Runtime.format(m[7], formatter) + " " + Runtime.format(m[11], formatter) + " " + Runtime.format(m[15], formatter) + "\n";
    }

}
//#endif
