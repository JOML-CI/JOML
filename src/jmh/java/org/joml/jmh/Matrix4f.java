/*
 * The MIT License
 *
 * Copyright (c) 2022 JOML
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
package org.joml.jmh;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.text.DecimalFormat;

import static org.joml.jmh.Matrix4fv.A;
import static org.joml.jmh.Matrix4fv.U;

/**
 * 4x4 matrix backed by 16 float fields.
 */
public class Matrix4f {

    private static final long M00 = m00Offset();

    private static long m00Offset() {
        try {
            long m00 = U.objectFieldOffset(Matrix4f.class.getDeclaredField("m00"));
            for (int i = 1; i < 16; i++) {
                if (U.objectFieldOffset(Matrix4f.class.getDeclaredField("m" + (i>>>2) + "" + (i&3))) != m00 + (i<<2))
                    throw new AssertionError("unsupported offset");
            }
            return m00;
        } catch (Exception e) {
            throw new AssertionError(e);
        }
    }

    private float pad0; // <- to align m00 on 8 bytes for Unsafe.getLong()
    // column0
    private float m00, m01, m02, m03;
    // column1
    private float m10, m11, m12, m13;
    // column2
    private float m20, m21, m22, m23;
    // column3
    private float m30, m31, m32, m33;

    public Matrix4f() {
        // Initialize to identity.
        m00 = 1f;
        m11 = 1f;
        m22 = 1f;
        m33 = 1f;
    }

    /**
     * On CPUs with FMA3 support, this is usually slower than {@link #mulFma(Matrix4f)}.
     */
    public Matrix4f mul(Matrix4f right) {
        float nm00 = m00 * right.m00 + m10 * right.m01 + m20 * right.m02 + m30 * right.m03;
        float nm01 = m01 * right.m00 + m11 * right.m01 + m21 * right.m02 + m31 * right.m03;
        float nm02 = m02 * right.m00 + m12 * right.m01 + m22 * right.m02 + m32 * right.m03;
        float nm03 = m03 * right.m00 + m13 * right.m01 + m23 * right.m02 + m33 * right.m03;
        float nm10 = m00 * right.m10 + m10 * right.m11 + m20 * right.m12 + m30 * right.m13;
        float nm11 = m01 * right.m10 + m11 * right.m11 + m21 * right.m12 + m31 * right.m13;
        float nm12 = m02 * right.m10 + m12 * right.m11 + m22 * right.m12 + m32 * right.m13;
        float nm13 = m03 * right.m10 + m13 * right.m11 + m23 * right.m12 + m33 * right.m13;
        float nm20 = m00 * right.m20 + m10 * right.m21 + m20 * right.m22 + m30 * right.m23;
        float nm21 = m01 * right.m20 + m11 * right.m21 + m21 * right.m22 + m31 * right.m23;
        float nm22 = m02 * right.m20 + m12 * right.m21 + m22 * right.m22 + m32 * right.m23;
        float nm23 = m03 * right.m20 + m13 * right.m21 + m23 * right.m22 + m33 * right.m23;
        float nm30 = m00 * right.m30 + m10 * right.m31 + m20 * right.m32 + m30 * right.m33;
        float nm31 = m01 * right.m30 + m11 * right.m31 + m21 * right.m32 + m31 * right.m33;
        float nm32 = m02 * right.m30 + m12 * right.m31 + m22 * right.m32 + m32 * right.m33;
        float nm33 = m03 * right.m30 + m13 * right.m31 + m23 * right.m32 + m33 * right.m33;
        m00 = nm00;
        m01 = nm01;
        m02 = nm02;
        m03 = nm03;
        m10 = nm10;
        m11 = nm11;
        m12 = nm12;
        m13 = nm13;
        m20 = nm20;
        m21 = nm21;
        m22 = nm22;
        m23 = nm23;
        m30 = nm30;
        m31 = nm31;
        m32 = nm32;
        m33 = nm33;
        return this;
    }

    /**
     * ONLY EXECUTE THIS METHOD ON CPUs THAT SUPPORT FMA3!!!
     * <p>
     * Sadly, the JVM does not have any mechanisms for user-code to check whether the CPU does support it
     * in order to route a generalized mul() method to this mulFma() method only if it does. We have to rely
     * on JNI code querying CPUID for this...
     */
    public Matrix4f mulFma(Matrix4f right) {
        float nm00 = Math.fma(m00, right.m00, Math.fma(m10, right.m01, Math.fma(m20, right.m02, m30 * right.m03)));
        float nm01 = Math.fma(m01, right.m00, Math.fma(m11, right.m01, Math.fma(m21, right.m02, m31 * right.m03)));
        float nm02 = Math.fma(m02, right.m00, Math.fma(m12, right.m01, Math.fma(m22, right.m02, m32 * right.m03)));
        float nm03 = Math.fma(m03, right.m00, Math.fma(m13, right.m01, Math.fma(m23, right.m02, m33 * right.m03)));
        float nm10 = Math.fma(m00, right.m10, Math.fma(m10, right.m11, Math.fma(m20, right.m12, m30 * right.m13)));
        float nm11 = Math.fma(m01, right.m10, Math.fma(m11, right.m11, Math.fma(m21, right.m12, m31 * right.m13)));
        float nm12 = Math.fma(m02, right.m10, Math.fma(m12, right.m11, Math.fma(m22, right.m12, m32 * right.m13)));
        float nm13 = Math.fma(m03, right.m10, Math.fma(m13, right.m11, Math.fma(m23, right.m12, m33 * right.m13)));
        float nm20 = Math.fma(m00, right.m20, Math.fma(m10, right.m21, Math.fma(m20, right.m22, m30 * right.m23)));
        float nm21 = Math.fma(m01, right.m20, Math.fma(m11, right.m21, Math.fma(m21, right.m22, m31 * right.m23)));
        float nm22 = Math.fma(m02, right.m20, Math.fma(m12, right.m21, Math.fma(m22, right.m22, m32 * right.m23)));
        float nm23 = Math.fma(m03, right.m20, Math.fma(m13, right.m21, Math.fma(m23, right.m22, m33 * right.m23)));
        float nm30 = Math.fma(m00, right.m30, Math.fma(m10, right.m31, Math.fma(m20, right.m32, m30 * right.m33)));
        float nm31 = Math.fma(m01, right.m30, Math.fma(m11, right.m31, Math.fma(m21, right.m32, m31 * right.m33)));
        float nm32 = Math.fma(m02, right.m30, Math.fma(m12, right.m31, Math.fma(m22, right.m32, m32 * right.m33)));
        float nm33 = Math.fma(m03, right.m30, Math.fma(m13, right.m31, Math.fma(m23, right.m32, m33 * right.m33)));
        m00 = nm00;
        m01 = nm01;
        m02 = nm02;
        m03 = nm03;
        m10 = nm10;
        m11 = nm11;
        m12 = nm12;
        m13 = nm13;
        m20 = nm20;
        m21 = nm21;
        m22 = nm22;
        m23 = nm23;
        m30 = nm30;
        m31 = nm31;
        m32 = nm32;
        m33 = nm33;
        return this;
    }

    public Matrix4f transpose(Matrix4f dest) {
        float nm10 = m01;
        float nm20 = m02;
        float nm21 = m12;
        float nm30 = m03;
        float nm31 = m13;
        float nm32 = m23;
        dest.m01 = m10;
        dest.m02 = m20;
        dest.m03 = m30;
        dest.m10 = nm10;
        dest.m12 = m21;
        dest.m13 = m31;
        dest.m20 = nm20;
        dest.m21 = nm21;
        dest.m23 = m32;
        dest.m30 = nm30;
        dest.m31 = nm31;
        dest.m32 = nm32;
        return dest;
    }

    public Matrix4f mulAffineFma(Matrix4f right) {
        float nm00 = Math.fma(m00, right.m00, Math.fma(m10, right.m01, m20 * right.m02));
        float nm01 = Math.fma(m01, right.m00, Math.fma(m11, right.m01, m21 * right.m02));
        float nm02 = Math.fma(m02, right.m00, Math.fma(m12, right.m01, m22 * right.m02));
        float nm10 = Math.fma(m00, right.m10, Math.fma(m10, right.m11, m20 * right.m12));
        float nm11 = Math.fma(m01, right.m10, Math.fma(m11, right.m11, m21 * right.m12));
        float nm12 = Math.fma(m02, right.m10, Math.fma(m12, right.m11, m22 * right.m12));
        float nm20 = Math.fma(m00, right.m20, Math.fma(m10, right.m21, m20 * right.m22));
        float nm21 = Math.fma(m01, right.m20, Math.fma(m11, right.m21, m21 * right.m22));
        float nm22 = Math.fma(m02, right.m20, Math.fma(m12, right.m21, m22 * right.m22));
        float nm30 = Math.fma(m00, right.m30, Math.fma(m10, right.m31, Math.fma(m20, right.m32, m30)));
        float nm31 = Math.fma(m01, right.m30, Math.fma(m11, right.m31, Math.fma(m21, right.m32, m31)));
        float nm32 = Math.fma(m02, right.m30, Math.fma(m12, right.m31, Math.fma(m22, right.m32, m32)));
        m00 = nm00;
        m01 = nm01;
        m02 = nm02;
        m03 = 0.0f;
        m10 = nm10;
        m11 = nm11;
        m12 = nm12;
        m13 = 0.0f;
        m20 = nm20;
        m21 = nm21;
        m22 = nm22;
        m23 = 0.0f;
        m30 = nm30;
        m31 = nm31;
        m32 = nm32;
        m33 = 1.0f;
        return this;
    }

    public Matrix4f invert(Matrix4f dest) {
        float a = m00 * m11 - m01 * m10;
        float b = m00 * m12 - m02 * m10;
        float c = m00 * m13 - m03 * m10;
        float d = m01 * m12 - m02 * m11;
        float e = m01 * m13 - m03 * m11;
        float f = m02 * m13 - m03 * m12;
        float g = m20 * m31 - m21 * m30;
        float h = m20 * m32 - m22 * m30;
        float i = m20 * m33 - m23 * m30;
        float j = m21 * m32 - m22 * m31;
        float k = m21 * m33 - m23 * m31;
        float l = m22 * m33 - m23 * m32;
        float det = a * l - b * k + c * j + d * i - e * h + f * g;
        det = 1.0f / det;
        float nm00 = Math.fma( m11, l, Math.fma(-m12, k,  m13 * j)) * det;
        float nm01 = Math.fma(-m01, l, Math.fma( m02, k, -m03 * j)) * det;
        float nm02 = Math.fma( m31, f, Math.fma(-m32, e,  m33 * d)) * det;
        float nm03 = Math.fma(-m21, f, Math.fma( m22, e, -m23 * d)) * det;
        float nm10 = Math.fma(-m10, l, Math.fma( m12, i, -m13 * h)) * det;
        float nm11 = Math.fma( m00, l, Math.fma(-m02, i,  m03 * h)) * det;
        float nm12 = Math.fma(-m30, f, Math.fma( m32, c, -m33 * b)) * det;
        float nm13 = Math.fma( m20, f, Math.fma(-m22, c,  m23 * b)) * det;
        float nm20 = Math.fma( m10, k, Math.fma(-m11, i,  m13 * g)) * det;
        float nm21 = Math.fma(-m00, k, Math.fma( m01, i, -m03 * g)) * det;
        float nm22 = Math.fma( m30, e, Math.fma(-m31, c,  m33 * a)) * det;
        float nm23 = Math.fma(-m20, e, Math.fma( m21, c, -m23 * a)) * det;
        float nm30 = Math.fma(-m10, j, Math.fma( m11, h, -m12 * g)) * det;
        float nm31 = Math.fma( m00, j, Math.fma(-m01, h,  m02 * g)) * det;
        float nm32 = Math.fma(-m30, d, Math.fma( m31, b, -m32 * a)) * det;
        float nm33 = Math.fma( m20, d, Math.fma(-m21, b,  m22 * a)) * det;
        dest.m00 = nm00;
        dest.m01 = nm01;
        dest.m02 = nm02;
        dest.m03 = nm03;
        dest.m10 = nm10;
        dest.m11 = nm11;
        dest.m12 = nm12;
        dest.m13 = nm13;
        dest.m20 = nm20;
        dest.m21 = nm21;
        dest.m22 = nm22;
        dest.m23 = nm23;
        dest.m30 = nm30;
        dest.m31 = nm31;
        dest.m32 = nm32;
        dest.m33 = nm33;
        return dest;
    }

    public String toString() {
        DecimalFormat f = new DecimalFormat(" 0.000E0;-");
        return f.format(m00) + " " + f.format(m10) + " " + f.format(m20) + " " + f.format(m30) + "\n"
             + f.format(m01) + " " + f.format(m11) + " " + f.format(m21) + " " + f.format(m31) + "\n"
             + f.format(m02) + " " + f.format(m12) + " " + f.format(m22) + " " + f.format(m32) + "\n"
             + f.format(m03) + " " + f.format(m13) + " " + f.format(m23) + " " + f.format(m33);
    }
    public FloatBuffer storePutFB(FloatBuffer fb) {
        fb.put(0,  m00);
        fb.put(1,  m01);
        fb.put(2,  m02);
        fb.put(3, m03);
        fb.put(4, m10);
        fb.put(5, m11);
        fb.put(6, m12);
        fb.put(7, m13);
        fb.put(8, m20);
        fb.put(9, m21);
        fb.put(10, m22);
        fb.put(11, m23);
        fb.put(12, m30);
        fb.put(13, m31);
        fb.put(14, m32);
        fb.put(15, m33);
        return fb;
    }
    public ByteBuffer storePutBB(ByteBuffer bb) {
        bb.putFloat(0,  m00);
        bb.putFloat(4,  m01);
        bb.putFloat(8,  m02);
        bb.putFloat(12, m03);
        bb.putFloat(16, m10);
        bb.putFloat(20, m11);
        bb.putFloat(24, m12);
        bb.putFloat(28, m13);
        bb.putFloat(32, m20);
        bb.putFloat(36, m21);
        bb.putFloat(40, m22);
        bb.putFloat(44, m23);
        bb.putFloat(48, m30);
        bb.putFloat(52, m31);
        bb.putFloat(56, m32);
        bb.putFloat(60, m33);
        return bb;
    }

    public ByteBuffer storeU(ByteBuffer bb) {
        long addr = U.getLong(bb, A);
        for (int i = 0; i < 8; i++)
            U.putLong(addr + (i<<3), U.getLong(this, M00 + (i<<3)));
        return bb;
    }

    public Matrix4f set(Matrix4f dest) {
        dest.m00 = m00;
        dest.m01 = m01;
        dest.m02 = m02;
        dest.m03 = m03;
        dest.m10 = m10;
        dest.m11 = m11;
        dest.m12 = m12;
        dest.m13 = m13;
        dest.m20 = m20;
        dest.m21 = m21;
        dest.m22 = m22;
        dest.m23 = m23;
        dest.m30 = m30;
        dest.m31 = m31;
        dest.m32 = m32;
        dest.m33 = m33;
        return this;
    }

}