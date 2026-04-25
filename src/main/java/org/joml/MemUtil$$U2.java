/*
 * The MIT License
 *
 * Copyright (c) 2026 OblivRuinDev, Kai Burjack
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
package org.joml;

import java.nio.*;

import static org.joml.MemUtil$$Field.*;

//#ifdef __HAS_UNSAFE__
@SuppressWarnings("Since15")
class MemUtil$$U2 {
    public static final jdk.internal.misc.Unsafe UNSAFE = jdk.internal.misc.Unsafe.getUnsafe();

    public static void put(Matrix4f m, long destAddr) {
        for (int i = 0; i < 8; i++) {
            UNSAFE.putLongUnaligned(null, destAddr + (i << 3), UNSAFE.getLong(m, Matrix4f_m00 + (i << 3)));
        }
    }

    public static void put4x3(Matrix4f m, long destAddr) {
        jdk.internal.misc.Unsafe u = UNSAFE;
        for (int i = 0; i < 4; i++) {
            u.putLongUnaligned(null, destAddr + 12 * i, u.getLong(m, Matrix4f_m00 + (i << 4)));
        }
        u.putFloat(null, destAddr +  8, m.m02());
        u.putFloat(null, destAddr + 20, m.m12());
        u.putFloat(null, destAddr + 32, m.m22());
        u.putFloat(null, destAddr + 44, m.m32());
    }

    public static void put3x4(Matrix4f m, long destAddr) {
        for (int i = 0; i < 6; i++) {
            UNSAFE.putLongUnaligned(null, destAddr + (i << 3), UNSAFE.getLong(m, Matrix4f_m00 + (i << 3)));
        }
    }

    public static void put(Matrix4x3f m, long destAddr) {
        for (int i = 0; i < 6; i++) {
            UNSAFE.putLongUnaligned(null, destAddr + (i << 3), UNSAFE.getLong(m, Matrix4x3f_m00 + (i << 3)));
        }
    }

    public static void put4x4(Matrix4x3f m, long destAddr) {
        for (int i = 0; i < 4; i++) {
            UNSAFE.putLongUnaligned(null, destAddr + (i << 4), UNSAFE.getLongUnaligned(m, Matrix4x3f_m00 + 12 * i));
            long lng = UNSAFE.getIntUnaligned(m, Matrix4x3f_m00 + 8 + 12 * i) & 0xFFFFFFFFL;
            UNSAFE.putLongUnaligned(null, destAddr + 8 + (i << 4), lng);
        }
        UNSAFE.putFloat(null, destAddr + 60, 1.0f);
    }

    public static void put3x4(Matrix4x3f m, long destAddr) {
        for (int i = 0; i < 3; i++) {
            UNSAFE.putLongUnaligned(null, destAddr + (i << 4), UNSAFE.getLongUnaligned(m, Matrix4x3f_m00 + 12 * i));
            UNSAFE.putFloat(null, destAddr + (i << 4) + 8, UNSAFE.getFloat(m, Matrix4x3f_m00 + 8 + 12 * i));
            UNSAFE.putFloat(null, destAddr + (i << 4) + 12, 0.0f);
        }
    }

    public static void put4x4(Matrix4x3d m, long destAddr) {
        jdk.internal.misc.Unsafe u = UNSAFE;
        u.putDouble(null, destAddr,       m.m00());
        u.putDouble(null, destAddr + 8,   m.m01());
        u.putDouble(null, destAddr + 16,  m.m02());
        u.putDouble(null, destAddr + 24,  0.0);
        u.putDouble(null, destAddr + 32,  m.m10());
        u.putDouble(null, destAddr + 40,  m.m11());
        u.putDouble(null, destAddr + 48,  m.m12());
        u.putDouble(null, destAddr + 56,  0.0);
        u.putDouble(null, destAddr + 64,  m.m20());
        u.putDouble(null, destAddr + 72,  m.m21());
        u.putDouble(null, destAddr + 80,  m.m22());
        u.putDouble(null, destAddr + 88,  0.0);
        u.putDouble(null, destAddr + 96,  m.m30());
        u.putDouble(null, destAddr + 104, m.m31());
        u.putDouble(null, destAddr + 112, m.m32());
        u.putDouble(null, destAddr + 120, 1.0);
    }

    public static void put4x4(Matrix3x2f m, long destAddr) {
        jdk.internal.misc.Unsafe u = UNSAFE;
        u.putLongUnaligned(null, destAddr,    u.getLong(m, Matrix3x2f_m00));
        u.putLongUnaligned(null, destAddr+8,  0L);
        u.putLongUnaligned(null, destAddr+16, u.getLong(m, Matrix3x2f_m00+8));
        u.putLongUnaligned(null, destAddr+24, 0L);
        u.putLongUnaligned(null, destAddr+32, 0L);
        u.putLongUnaligned(null, destAddr+40, 0x3F800000L);
        u.putLongUnaligned(null, destAddr+48, u.getLong(m, Matrix3x2f_m00+16));
        u.putLongUnaligned(null, destAddr+56, 0x3F80000000000000L);
    }

    public static void put4x4(Matrix3x2d m, long destAddr) {
        jdk.internal.misc.Unsafe u = UNSAFE;
        u.putDouble(null, destAddr,       m.m00());
        u.putDouble(null, destAddr + 8,   m.m01());
        u.putDouble(null, destAddr + 16,  0.0);
        u.putDouble(null, destAddr + 24,  0.0);
        u.putDouble(null, destAddr + 32,  m.m10());
        u.putDouble(null, destAddr + 40,  m.m11());
        u.putDouble(null, destAddr + 48,  0.0);
        u.putDouble(null, destAddr + 56,  0.0);
        u.putDouble(null, destAddr + 64,  0.0);
        u.putDouble(null, destAddr + 72,  0.0);
        u.putDouble(null, destAddr + 80,  1.0);
        u.putDouble(null, destAddr + 88,  0.0);
        u.putDouble(null, destAddr + 96,  m.m20());
        u.putDouble(null, destAddr + 104, m.m21());
        u.putDouble(null, destAddr + 112, 0.0);
        u.putDouble(null, destAddr + 120, 1.0);
    }

    public static void put3x3(Matrix3x2f m, long destAddr) {
        jdk.internal.misc.Unsafe u = UNSAFE;
        u.putLongUnaligned( null, destAddr,    u.getLong(m, Matrix3x2f_m00));
        u.putIntUnaligned(  null, destAddr+8,  0);
        u.putLongUnaligned( null, destAddr+12, u.getLong(m, Matrix3x2f_m00+8));
        u.putIntUnaligned(  null, destAddr+20, 0);
        u.putLongUnaligned( null, destAddr+24, u.getLong(m, Matrix3x2f_m00+16));
        u.putFloat(null, destAddr+32, 1.0f);
    }

    public static void put3x3(Matrix3x2d m, long destAddr) {
        jdk.internal.misc.Unsafe u = UNSAFE;
        u.putDouble(null, destAddr,      m.m00());
        u.putDouble(null, destAddr + 8,  m.m01());
        u.putDouble(null, destAddr + 16, 0.0);
        u.putDouble(null, destAddr + 24, m.m10());
        u.putDouble(null, destAddr + 32, m.m11());
        u.putDouble(null, destAddr + 40, 0.0);
        u.putDouble(null, destAddr + 48, m.m20());
        u.putDouble(null, destAddr + 56, m.m21());
        u.putDouble(null, destAddr + 64, 1.0);
    }

    public static void putTransposed(Matrix4f m, long destAddr) {
        jdk.internal.misc.Unsafe u = UNSAFE;
        u.putFloat(null, destAddr,      m.m00());
        u.putFloat(null, destAddr + 4,  m.m10());
        u.putFloat(null, destAddr + 8,  m.m20());
        u.putFloat(null, destAddr + 12, m.m30());
        u.putFloat(null, destAddr + 16, m.m01());
        u.putFloat(null, destAddr + 20, m.m11());
        u.putFloat(null, destAddr + 24, m.m21());
        u.putFloat(null, destAddr + 28, m.m31());
        u.putFloat(null, destAddr + 32, m.m02());
        u.putFloat(null, destAddr + 36, m.m12());
        u.putFloat(null, destAddr + 40, m.m22());
        u.putFloat(null, destAddr + 44, m.m32());
        u.putFloat(null, destAddr + 48, m.m03());
        u.putFloat(null, destAddr + 52, m.m13());
        u.putFloat(null, destAddr + 56, m.m23());
        u.putFloat(null, destAddr + 60, m.m33());
    }

    public static void put4x3Transposed(Matrix4f m, long destAddr) {
        jdk.internal.misc.Unsafe u = UNSAFE;
        u.putFloat(null, destAddr,      m.m00());
        u.putFloat(null, destAddr + 4,  m.m10());
        u.putFloat(null, destAddr + 8,  m.m20());
        u.putFloat(null, destAddr + 12, m.m30());
        u.putFloat(null, destAddr + 16, m.m01());
        u.putFloat(null, destAddr + 20, m.m11());
        u.putFloat(null, destAddr + 24, m.m21());
        u.putFloat(null, destAddr + 28, m.m31());
        u.putFloat(null, destAddr + 32, m.m02());
        u.putFloat(null, destAddr + 36, m.m12());
        u.putFloat(null, destAddr + 40, m.m22());
        u.putFloat(null, destAddr + 44, m.m32());
    }

    public static void putTransposed(Matrix4x3f m, long destAddr) {
        jdk.internal.misc.Unsafe u = UNSAFE;
        u.putFloat(null, destAddr,      m.m00());
        u.putFloat(null, destAddr + 4,  m.m10());
        u.putFloat(null, destAddr + 8,  m.m20());
        u.putFloat(null, destAddr + 12, m.m30());
        u.putFloat(null, destAddr + 16, m.m01());
        u.putFloat(null, destAddr + 20, m.m11());
        u.putFloat(null, destAddr + 24, m.m21());
        u.putFloat(null, destAddr + 28, m.m31());
        u.putFloat(null, destAddr + 32, m.m02());
        u.putFloat(null, destAddr + 36, m.m12());
        u.putFloat(null, destAddr + 40, m.m22());
        u.putFloat(null, destAddr + 44, m.m32());
    }

    public static void putTransposed(Matrix3f m, long destAddr) {
        jdk.internal.misc.Unsafe u = UNSAFE;
        u.putFloat(null, destAddr,      m.m00());
        u.putFloat(null, destAddr + 4,  m.m10());
        u.putFloat(null, destAddr + 8,  m.m20());
        u.putFloat(null, destAddr + 12, m.m01());
        u.putFloat(null, destAddr + 16, m.m11());
        u.putFloat(null, destAddr + 20, m.m21());
        u.putFloat(null, destAddr + 24, m.m02());
        u.putFloat(null, destAddr + 28, m.m12());
        u.putFloat(null, destAddr + 32, m.m22());
    }

    public static void putTransposed(Matrix3x2f m, long destAddr) {
        jdk.internal.misc.Unsafe u = UNSAFE;
        u.putFloat(null, destAddr,      m.m00());
        u.putFloat(null, destAddr + 4,  m.m10());
        u.putFloat(null, destAddr + 8,  m.m20());
        u.putFloat(null, destAddr + 12, m.m01());
        u.putFloat(null, destAddr + 16, m.m11());
        u.putFloat(null, destAddr + 20, m.m21());
    }

    public static void putTransposed(Matrix2f m, long destAddr) {
        UNSAFE.putFloat(null, destAddr,      m.m00());
        UNSAFE.putFloat(null, destAddr + 4,  m.m10());
        UNSAFE.putFloat(null, destAddr + 8,  m.m01());
        UNSAFE.putFloat(null, destAddr + 12, m.m11());
    }

    public static void put(Matrix4d m, long destAddr) {
        jdk.internal.misc.Unsafe u = UNSAFE;
        u.putDouble(null, destAddr,       m.m00());
        u.putDouble(null, destAddr + 8,   m.m01());
        u.putDouble(null, destAddr + 16,  m.m02());
        u.putDouble(null, destAddr + 24,  m.m03());
        u.putDouble(null, destAddr + 32,  m.m10());
        u.putDouble(null, destAddr + 40,  m.m11());
        u.putDouble(null, destAddr + 48,  m.m12());
        u.putDouble(null, destAddr + 56,  m.m13());
        u.putDouble(null, destAddr + 64,  m.m20());
        u.putDouble(null, destAddr + 72,  m.m21());
        u.putDouble(null, destAddr + 80,  m.m22());
        u.putDouble(null, destAddr + 88,  m.m23());
        u.putDouble(null, destAddr + 96,  m.m30());
        u.putDouble(null, destAddr + 104, m.m31());
        u.putDouble(null, destAddr + 112, m.m32());
        u.putDouble(null, destAddr + 120, m.m33());
    }

    public static void put(Matrix4x3d m, long destAddr) {
        jdk.internal.misc.Unsafe u = UNSAFE;
        u.putDouble(null, destAddr,      m.m00());
        u.putDouble(null, destAddr + 8,  m.m01());
        u.putDouble(null, destAddr + 16, m.m02());
        u.putDouble(null, destAddr + 24, m.m10());
        u.putDouble(null, destAddr + 32, m.m11());
        u.putDouble(null, destAddr + 40, m.m12());
        u.putDouble(null, destAddr + 48, m.m20());
        u.putDouble(null, destAddr + 56, m.m21());
        u.putDouble(null, destAddr + 64, m.m22());
        u.putDouble(null, destAddr + 72, m.m30());
        u.putDouble(null, destAddr + 80, m.m31());
        u.putDouble(null, destAddr + 88, m.m32());
    }

    public static void putTransposed(Matrix4d m, long destAddr) {
        jdk.internal.misc.Unsafe u = UNSAFE;
        u.putDouble(null, destAddr,       m.m00());
        u.putDouble(null, destAddr + 8,   m.m10());
        u.putDouble(null, destAddr + 16,  m.m20());
        u.putDouble(null, destAddr + 24,  m.m30());
        u.putDouble(null, destAddr + 32,  m.m01());
        u.putDouble(null, destAddr + 40,  m.m11());
        u.putDouble(null, destAddr + 48,  m.m21());
        u.putDouble(null, destAddr + 56,  m.m31());
        u.putDouble(null, destAddr + 64,  m.m02());
        u.putDouble(null, destAddr + 72,  m.m12());
        u.putDouble(null, destAddr + 80,  m.m22());
        u.putDouble(null, destAddr + 88,  m.m32());
        u.putDouble(null, destAddr + 96,  m.m03());
        u.putDouble(null, destAddr + 104, m.m13());
        u.putDouble(null, destAddr + 112, m.m23());
        u.putDouble(null, destAddr + 120, m.m33());
    }

    public static void putfTransposed(Matrix4d m, long destAddr) {
        jdk.internal.misc.Unsafe u = UNSAFE;
        u.putFloat(null, destAddr,      (float)m.m00());
        u.putFloat(null, destAddr + 4,  (float)m.m10());
        u.putFloat(null, destAddr + 8,  (float)m.m20());
        u.putFloat(null, destAddr + 12, (float)m.m30());
        u.putFloat(null, destAddr + 16, (float)m.m01());
        u.putFloat(null, destAddr + 20, (float)m.m11());
        u.putFloat(null, destAddr + 24, (float)m.m21());
        u.putFloat(null, destAddr + 28, (float)m.m31());
        u.putFloat(null, destAddr + 32, (float)m.m02());
        u.putFloat(null, destAddr + 36, (float)m.m12());
        u.putFloat(null, destAddr + 40, (float)m.m22());
        u.putFloat(null, destAddr + 44, (float)m.m32());
        u.putFloat(null, destAddr + 48, (float)m.m03());
        u.putFloat(null, destAddr + 52, (float)m.m13());
        u.putFloat(null, destAddr + 56, (float)m.m23());
        u.putFloat(null, destAddr + 60, (float)m.m33());
    }

    public static void put4x3Transposed(Matrix4d m, long destAddr) {
        jdk.internal.misc.Unsafe u = UNSAFE;
        u.putDouble(null, destAddr,      m.m00());
        u.putDouble(null, destAddr + 8,  m.m10());
        u.putDouble(null, destAddr + 16, m.m20());
        u.putDouble(null, destAddr + 24, m.m30());
        u.putDouble(null, destAddr + 32, m.m01());
        u.putDouble(null, destAddr + 40, m.m11());
        u.putDouble(null, destAddr + 48, m.m21());
        u.putDouble(null, destAddr + 56, m.m31());
        u.putDouble(null, destAddr + 64, m.m02());
        u.putDouble(null, destAddr + 72, m.m12());
        u.putDouble(null, destAddr + 80, m.m22());
        u.putDouble(null, destAddr + 88, m.m32());
    }

    public static void putTransposed(Matrix4x3d m, long destAddr) {
        jdk.internal.misc.Unsafe u = UNSAFE;
        u.putDouble(null, destAddr,      m.m00());
        u.putDouble(null, destAddr + 8,  m.m10());
        u.putDouble(null, destAddr + 16, m.m20());
        u.putDouble(null, destAddr + 24, m.m30());
        u.putDouble(null, destAddr + 32, m.m01());
        u.putDouble(null, destAddr + 40, m.m11());
        u.putDouble(null, destAddr + 48, m.m21());
        u.putDouble(null, destAddr + 56, m.m31());
        u.putDouble(null, destAddr + 64, m.m02());
        u.putDouble(null, destAddr + 72, m.m12());
        u.putDouble(null, destAddr + 80, m.m22());
        u.putDouble(null, destAddr + 88, m.m32());
    }

    public static void putTransposed(Matrix3d m, long destAddr) {
        jdk.internal.misc.Unsafe u = UNSAFE;
        u.putDouble(null, destAddr,      m.m00());
        u.putDouble(null, destAddr + 8,  m.m10());
        u.putDouble(null, destAddr + 16, m.m20());
        u.putDouble(null, destAddr + 24, m.m01());
        u.putDouble(null, destAddr + 32, m.m11());
        u.putDouble(null, destAddr + 40, m.m21());
        u.putDouble(null, destAddr + 48, m.m02());
        u.putDouble(null, destAddr + 56, m.m12());
        u.putDouble(null, destAddr + 64, m.m22());
    }

    public static void putTransposed(Matrix3x2d m, long destAddr) {
        jdk.internal.misc.Unsafe u = UNSAFE;
        u.putDouble(null, destAddr,      m.m00());
        u.putDouble(null, destAddr + 8,  m.m10());
        u.putDouble(null, destAddr + 16, m.m20());
        u.putDouble(null, destAddr + 24, m.m01());
        u.putDouble(null, destAddr + 32, m.m11());
        u.putDouble(null, destAddr + 40, m.m21());
    }

    public static void putTransposed(Matrix2d m, long destAddr) {
        UNSAFE.putDouble(null, destAddr,      m.m00());
        UNSAFE.putDouble(null, destAddr + 8,  m.m10());
        UNSAFE.putDouble(null, destAddr + 16, m.m10());
        UNSAFE.putDouble(null, destAddr + 24, m.m10());
    }

    public static void putfTransposed(Matrix4x3d m, long destAddr) {
        jdk.internal.misc.Unsafe u = UNSAFE;
        u.putFloat(null, destAddr,      (float)m.m00());
        u.putFloat(null, destAddr + 4,  (float)m.m10());
        u.putFloat(null, destAddr + 8,  (float)m.m20());
        u.putFloat(null, destAddr + 12, (float)m.m30());
        u.putFloat(null, destAddr + 16, (float)m.m01());
        u.putFloat(null, destAddr + 20, (float)m.m11());
        u.putFloat(null, destAddr + 24, (float)m.m21());
        u.putFloat(null, destAddr + 28, (float)m.m31());
        u.putFloat(null, destAddr + 32, (float)m.m02());
        u.putFloat(null, destAddr + 36, (float)m.m12());
        u.putFloat(null, destAddr + 40, (float)m.m22());
        u.putFloat(null, destAddr + 44, (float)m.m32());
    }

    public static void putfTransposed(Matrix3d m, long destAddr) {
        jdk.internal.misc.Unsafe u = UNSAFE;
        u.putFloat(null, destAddr,      (float)m.m00());
        u.putFloat(null, destAddr + 4,  (float)m.m10());
        u.putFloat(null, destAddr + 8,  (float)m.m20());
        u.putFloat(null, destAddr + 12, (float)m.m01());
        u.putFloat(null, destAddr + 16, (float)m.m11());
        u.putFloat(null, destAddr + 20, (float)m.m21());
        u.putFloat(null, destAddr + 24, (float)m.m02());
        u.putFloat(null, destAddr + 28, (float)m.m12());
        u.putFloat(null, destAddr + 32, (float)m.m22());
    }

    public static void putfTransposed(Matrix3x2d m, long destAddr) {
        jdk.internal.misc.Unsafe u = UNSAFE;
        u.putFloat(null, destAddr,      (float)m.m00());
        u.putFloat(null, destAddr + 4,  (float)m.m10());
        u.putFloat(null, destAddr + 8,  (float)m.m20());
        u.putFloat(null, destAddr + 12, (float)m.m01());
        u.putFloat(null, destAddr + 16, (float)m.m11());
        u.putFloat(null, destAddr + 20, (float)m.m21());
    }

    public static void putfTransposed(Matrix2d m, long destAddr) {
        UNSAFE.putFloat(null, destAddr,      (float)m.m00());
        UNSAFE.putFloat(null, destAddr + 4,  (float)m.m00());
        UNSAFE.putFloat(null, destAddr + 8,  (float)m.m00());
        UNSAFE.putFloat(null, destAddr + 12, (float)m.m00());
    }

    public static void putf(Matrix4d m, long destAddr) {
        jdk.internal.misc.Unsafe u = UNSAFE;
        u.putFloat(null, destAddr,      (float)m.m00());
        u.putFloat(null, destAddr + 4,  (float)m.m01());
        u.putFloat(null, destAddr + 8,  (float)m.m02());
        u.putFloat(null, destAddr + 12, (float)m.m03());
        u.putFloat(null, destAddr + 16, (float)m.m10());
        u.putFloat(null, destAddr + 20, (float)m.m11());
        u.putFloat(null, destAddr + 24, (float)m.m12());
        u.putFloat(null, destAddr + 28, (float)m.m13());
        u.putFloat(null, destAddr + 32, (float)m.m20());
        u.putFloat(null, destAddr + 36, (float)m.m21());
        u.putFloat(null, destAddr + 40, (float)m.m22());
        u.putFloat(null, destAddr + 44, (float)m.m23());
        u.putFloat(null, destAddr + 48, (float)m.m30());
        u.putFloat(null, destAddr + 52, (float)m.m31());
        u.putFloat(null, destAddr + 56, (float)m.m32());
        u.putFloat(null, destAddr + 60, (float)m.m33());
    }

    public static void putf(Matrix4x3d m, long destAddr) {
        jdk.internal.misc.Unsafe u = UNSAFE;
        u.putFloat(null, destAddr,      (float)m.m00());
        u.putFloat(null, destAddr + 4,  (float)m.m01());
        u.putFloat(null, destAddr + 8,  (float)m.m02());
        u.putFloat(null, destAddr + 12, (float)m.m10());
        u.putFloat(null, destAddr + 16, (float)m.m11());
        u.putFloat(null, destAddr + 20, (float)m.m12());
        u.putFloat(null, destAddr + 24, (float)m.m20());
        u.putFloat(null, destAddr + 28, (float)m.m21());
        u.putFloat(null, destAddr + 32, (float)m.m22());
        u.putFloat(null, destAddr + 36, (float)m.m30());
        u.putFloat(null, destAddr + 40, (float)m.m31());
        u.putFloat(null, destAddr + 44, (float)m.m32());
    }

    public static void put(Matrix3f m, long destAddr) {
        for (int i = 0; i < 4; i++) {
            UNSAFE.putLongUnaligned(null, destAddr + (i << 3), UNSAFE.getLong(m, Matrix3f_m00 + (i << 3)));
        }
        UNSAFE.putFloat(null, destAddr + 32, m.m22());
    }

    public static void put3x4(Matrix3f m, long destAddr) {
        for (int i = 0; i < 3; i++) {
            UNSAFE.putLongUnaligned(null, destAddr + (i << 4), UNSAFE.getLongUnaligned(m, Matrix3f_m00 + 12 * i));
            UNSAFE.putFloat(null, destAddr + (i << 4) + 8, UNSAFE.getFloat(m, Matrix3f_m00 + 8 + 12 * i));
            UNSAFE.putFloat(null, destAddr + (i << 4) + 12, 0.0f);
        }
    }

    public static void put(Matrix3d m, long destAddr) {
        jdk.internal.misc.Unsafe u = UNSAFE;
        u.putDouble(null, destAddr,      m.m00());
        u.putDouble(null, destAddr + 8,  m.m01());
        u.putDouble(null, destAddr + 16, m.m02());
        u.putDouble(null, destAddr + 24, m.m10());
        u.putDouble(null, destAddr + 32, m.m11());
        u.putDouble(null, destAddr + 40, m.m12());
        u.putDouble(null, destAddr + 48, m.m20());
        u.putDouble(null, destAddr + 56, m.m21());
        u.putDouble(null, destAddr + 64, m.m22());
    }

    public static void put(Matrix3x2f m, long destAddr) {
        for (int i = 0; i < 3; i++) {
            UNSAFE.putLongUnaligned(null, destAddr + (i << 3), UNSAFE.getLong(m, Matrix3x2f_m00 + (i << 3)));
        }
    }

    public static void put(Matrix3x2d m, long destAddr) {
        jdk.internal.misc.Unsafe u = UNSAFE;
        u.putDouble(null, destAddr,      m.m00());
        u.putDouble(null, destAddr + 8,  m.m01());
        u.putDouble(null, destAddr + 16, m.m10());
        u.putDouble(null, destAddr + 24, m.m11());
        u.putDouble(null, destAddr + 32, m.m20());
        u.putDouble(null, destAddr + 40, m.m21());
    }

    public static void putf(Matrix3d m, long destAddr) {
        jdk.internal.misc.Unsafe u = UNSAFE;
        u.putFloat(null, destAddr,      (float)m.m00());
        u.putFloat(null, destAddr + 4,  (float)m.m01());
        u.putFloat(null, destAddr + 8,  (float)m.m02());
        u.putFloat(null, destAddr + 12, (float)m.m10());
        u.putFloat(null, destAddr + 16, (float)m.m11());
        u.putFloat(null, destAddr + 20, (float)m.m12());
        u.putFloat(null, destAddr + 24, (float)m.m20());
        u.putFloat(null, destAddr + 28, (float)m.m21());
        u.putFloat(null, destAddr + 32, (float)m.m22());
    }

    public static void put(Matrix2f m, long destAddr) {
        UNSAFE.putLongUnaligned(null, destAddr,     UNSAFE.getLongUnaligned(m, Matrix2f_m00));
        UNSAFE.putLongUnaligned(null, destAddr + 8, UNSAFE.getLongUnaligned(m, Matrix2f_m00 + 8));
    }

    public static void put(Matrix2d m, long destAddr) {
        UNSAFE.putDouble(null, destAddr,      m.m00());
        UNSAFE.putDouble(null, destAddr + 8,  m.m01());
        UNSAFE.putDouble(null, destAddr + 16, m.m10());
        UNSAFE.putDouble(null, destAddr + 24, m.m11());
    }

    public static void putf(Matrix2d m, long destAddr) {
        UNSAFE.putFloat(null, destAddr,      (float)m.m00());
        UNSAFE.putFloat(null, destAddr + 4,  (float)m.m01());
        UNSAFE.putFloat(null, destAddr + 8,  (float)m.m10());
        UNSAFE.putFloat(null, destAddr + 12, (float)m.m11());
    }

    public static void put(Vector4d src, long destAddr) {
        UNSAFE.putDouble(null, destAddr,    src.x);
        UNSAFE.putDouble(null, destAddr+8,  src.y);
        UNSAFE.putDouble(null, destAddr+16, src.z);
        UNSAFE.putDouble(null, destAddr+24, src.w);
    }

    public static void putf(Vector4d src, long destAddr) {
        UNSAFE.putFloat(null, destAddr,    (float) src.x);
        UNSAFE.putFloat(null, destAddr+4,  (float) src.y);
        UNSAFE.putFloat(null, destAddr+8,  (float) src.z);
        UNSAFE.putFloat(null, destAddr+12, (float) src.w);
    }

    public static void put(Vector4f src, long destAddr) {
        UNSAFE.putLongUnaligned(null, destAddr, UNSAFE.getLongUnaligned(src, Vector4f_x));
        UNSAFE.putLongUnaligned(null, destAddr+8, UNSAFE.getLongUnaligned(src, Vector4f_x+8));
    }

    public static void put(Vector4i src, long destAddr) {
        UNSAFE.putLongUnaligned(null, destAddr, UNSAFE.getLongUnaligned(src, Vector4i_x));
        UNSAFE.putLongUnaligned(null, destAddr+8, UNSAFE.getLongUnaligned(src, Vector4i_x+8));
    }

    public static void put(Vector4L src, long destAddr) {
        UNSAFE.putLongUnaligned(null, destAddr, src.x);
        UNSAFE.putLongUnaligned(null, destAddr+8, src.y);
        UNSAFE.putLongUnaligned(null, destAddr+16, src.z);
        UNSAFE.putLongUnaligned(null, destAddr+24, src.w);
    }

    public static void put(Vector3f src, long destAddr) {
        UNSAFE.putLongUnaligned(null, destAddr, UNSAFE.getLongUnaligned(src, Vector3f_x));
        UNSAFE.putFloat(null, destAddr+8, src.z);
    }

    public static void put(Vector3d src, long destAddr) {
        UNSAFE.putDouble(null, destAddr,    src.x);
        UNSAFE.putDouble(null, destAddr+8,  src.y);
        UNSAFE.putDouble(null, destAddr+16, src.z);
    }

    public static void putf(Vector3d src, long destAddr) {
        UNSAFE.putFloat(null, destAddr,   (float) src.x);
        UNSAFE.putFloat(null, destAddr+4, (float) src.y);
        UNSAFE.putFloat(null, destAddr+8, (float) src.z);
    }

    public static void put(Vector3i src, long destAddr) {
        UNSAFE.putLongUnaligned(null, destAddr, UNSAFE.getLongUnaligned(src, Vector3i_x));
        UNSAFE.putIntUnaligned(null, destAddr+8, src.z);
    }

    public static void put(Vector3L src, long destAddr) {
        UNSAFE.putLongUnaligned(null, destAddr, src.x);
        UNSAFE.putLongUnaligned(null, destAddr+8, src.y);
        UNSAFE.putLongUnaligned(null, destAddr+16, src.z);
    }

    public static void put(Vector2f src, long destAddr) {
        UNSAFE.putLongUnaligned(null, destAddr, UNSAFE.getLongUnaligned(src, Vector2f_x));
    }

    public static void put(Vector2d src, long destAddr) {
        UNSAFE.putDouble(null, destAddr,   src.x);
        UNSAFE.putDouble(null, destAddr+8, src.y);
    }

    public static void put(Vector2i src, long destAddr) {
        UNSAFE.putLongUnaligned(null, destAddr, UNSAFE.getLongUnaligned(src, Vector2i_x));
    }

    public static void put(Vector2L src, long destAddr) {
        UNSAFE.putLongUnaligned(null, destAddr,   src.x);
        UNSAFE.putLongUnaligned(null, destAddr+8, src.y);
    }

    public static void get(Matrix4f m, long srcAddr) {
        for (int i = 0; i < 8; i++) {
            UNSAFE.putLongUnaligned(m, Matrix4f_m00 + (i << 3), UNSAFE.getLongUnaligned(null, srcAddr + (i << 3)));
        }
    }

    public static void getTransposed(Matrix4f m, long srcAddr) {
        jdk.internal.misc.Unsafe u = UNSAFE;
        m._m00(u.getFloat(srcAddr))
                ._m10(u.getFloat(srcAddr + 4))
                ._m20(u.getFloat(srcAddr + 8))
                ._m30(u.getFloat(srcAddr + 12))
                ._m01(u.getFloat(srcAddr + 16))
                ._m11(u.getFloat(srcAddr + 20))
                ._m21(u.getFloat(srcAddr + 24))
                ._m31(u.getFloat(srcAddr + 28))
                ._m02(u.getFloat(srcAddr + 32))
                ._m12(u.getFloat(srcAddr + 36))
                ._m22(u.getFloat(srcAddr + 40))
                ._m32(u.getFloat(srcAddr + 44))
                ._m03(u.getFloat(srcAddr + 48))
                ._m13(u.getFloat(srcAddr + 52))
                ._m23(u.getFloat(srcAddr + 56))
                ._m33(u.getFloat(srcAddr + 60));
    }

    public static void getTransposed(Matrix3f m, long srcAddr) {
        jdk.internal.misc.Unsafe u = UNSAFE;
        m._m00(u.getFloat(srcAddr))
                ._m10(u.getFloat(srcAddr + 4))
                ._m20(u.getFloat(srcAddr + 8))
                ._m01(u.getFloat(srcAddr + 12))
                ._m11(u.getFloat(srcAddr + 16))
                ._m21(u.getFloat(srcAddr + 20))
                ._m02(u.getFloat(srcAddr + 24))
                ._m12(u.getFloat(srcAddr + 28))
                ._m22(u.getFloat(srcAddr + 32));
    }

    public static void getTransposed(Matrix4x3f m, long srcAddr) {
        jdk.internal.misc.Unsafe u = UNSAFE;
        m._m00(u.getFloat(srcAddr))
                ._m10(u.getFloat(srcAddr + 4))
                ._m20(u.getFloat(srcAddr + 8))
                ._m30(u.getFloat(srcAddr + 12))
                ._m01(u.getFloat(srcAddr + 16))
                ._m11(u.getFloat(srcAddr + 20))
                ._m21(u.getFloat(srcAddr + 24))
                ._m31(u.getFloat(srcAddr + 28))
                ._m02(u.getFloat(srcAddr + 32))
                ._m12(u.getFloat(srcAddr + 36))
                ._m22(u.getFloat(srcAddr + 40))
                ._m32(u.getFloat(srcAddr + 44));
    }

    public static void getTransposed(Matrix3x2f m, long srcAddr) {
        jdk.internal.misc.Unsafe u = UNSAFE;
        m._m00(u.getFloat(srcAddr))
                ._m10(u.getFloat(srcAddr + 4))
                ._m20(u.getFloat(srcAddr + 8))
                ._m01(u.getFloat(srcAddr + 12))
                ._m11(u.getFloat(srcAddr + 16))
                ._m21(u.getFloat(srcAddr + 20));
    }

    public static void getTransposed(Matrix2f m, long srcAddr) {
        jdk.internal.misc.Unsafe u = UNSAFE;
        m._m00(u.getFloat(srcAddr))
                ._m10(u.getFloat(srcAddr + 4))
                ._m01(u.getFloat(srcAddr + 8))
                ._m11(u.getFloat(srcAddr + 12));
    }

    public static void getTransposed(Matrix2d m, long srcAddr) {
        jdk.internal.misc.Unsafe u = UNSAFE;
        m._m00(u.getDouble(srcAddr))
                ._m10(u.getDouble(srcAddr + 8))
                ._m01(u.getDouble(srcAddr + 16))
                ._m11(u.getDouble(srcAddr + 24));
    }

    public static void getTransposed(Matrix4x3d m, long srcAddr) {
        jdk.internal.misc.Unsafe u = UNSAFE;
        m._m00(u.getDouble(srcAddr))
                ._m10(u.getDouble(srcAddr + 8))
                ._m20(u.getDouble(srcAddr + 16))
                ._m30(u.getDouble(srcAddr + 24))
                ._m01(u.getDouble(srcAddr + 32))
                ._m11(u.getDouble(srcAddr + 40))
                ._m21(u.getDouble(srcAddr + 48))
                ._m31(u.getDouble(srcAddr + 56))
                ._m02(u.getDouble(srcAddr + 64))
                ._m12(u.getDouble(srcAddr + 72))
                ._m22(u.getDouble(srcAddr + 80))
                ._m32(u.getDouble(srcAddr + 88));
    }

    public static void getTransposed(Matrix3x2d m, long srcAddr) {
        jdk.internal.misc.Unsafe u = UNSAFE;
        m._m00(u.getDouble(srcAddr))
                ._m10(u.getDouble(srcAddr + 8))
                ._m20(u.getDouble(srcAddr + 16))
                ._m01(u.getDouble(srcAddr + 24))
                ._m11(u.getDouble(srcAddr + 32))
                ._m21(u.getDouble(srcAddr + 40));
    }

    public static void getTransposed(Matrix3d m, long srcAddr) {
        jdk.internal.misc.Unsafe u = UNSAFE;
        m._m00(u.getDouble(srcAddr))
                ._m10(u.getDouble(srcAddr + 8))
                ._m20(u.getDouble(srcAddr + 16))
                ._m01(u.getDouble(srcAddr + 24))
                ._m11(u.getDouble(srcAddr + 32))
                ._m21(u.getDouble(srcAddr + 40))
                ._m02(u.getDouble(srcAddr + 48))
                ._m12(u.getDouble(srcAddr + 56))
                ._m22(u.getDouble(srcAddr + 64));
    }

    public static void getTransposed(Matrix4d m, long srcAddr) {
        jdk.internal.misc.Unsafe u = UNSAFE;
        m._m00(u.getDouble(srcAddr))
                ._m10(u.getDouble(srcAddr + 8))
                ._m20(u.getDouble(srcAddr + 16))
                ._m30(u.getDouble(srcAddr + 24))
                ._m01(u.getDouble(srcAddr + 32))
                ._m11(u.getDouble(srcAddr + 40))
                ._m21(u.getDouble(srcAddr + 48))
                ._m31(u.getDouble(srcAddr + 56))
                ._m02(u.getDouble(srcAddr + 64))
                ._m12(u.getDouble(srcAddr + 72))
                ._m22(u.getDouble(srcAddr + 80))
                ._m32(u.getDouble(srcAddr + 88))
                ._m03(u.getDouble(srcAddr + 96))
                ._m13(u.getDouble(srcAddr + 104))
                ._m23(u.getDouble(srcAddr + 112))
                ._m33(u.getDouble(srcAddr + 120));
    }

    public static void get(Matrix4x3f m, long srcAddr) {
        for (int i = 0; i < 6; i++) {
            UNSAFE.putLongUnaligned(m, Matrix4x3f_m00 + (i << 3), UNSAFE.getLongUnaligned(null, srcAddr + (i << 3)));
        }
    }

    public static void get(Matrix4d m, long srcAddr) {
        jdk.internal.misc.Unsafe u = UNSAFE;
        m._m00(u.getDouble(null, srcAddr))
                ._m01(u.getDouble(null, srcAddr+8))
                ._m02(u.getDouble(null, srcAddr+16))
                ._m03(u.getDouble(null, srcAddr+24))
                ._m10(u.getDouble(null, srcAddr+32))
                ._m11(u.getDouble(null, srcAddr+40))
                ._m12(u.getDouble(null, srcAddr+48))
                ._m13(u.getDouble(null, srcAddr+56))
                ._m20(u.getDouble(null, srcAddr+64))
                ._m21(u.getDouble(null, srcAddr+72))
                ._m22(u.getDouble(null, srcAddr+80))
                ._m23(u.getDouble(null, srcAddr+88))
                ._m30(u.getDouble(null, srcAddr+96))
                ._m31(u.getDouble(null, srcAddr+104))
                ._m32(u.getDouble(null, srcAddr+112))
                ._m33(u.getDouble(null, srcAddr+120));
    }

    public static void get(Matrix4x3d m, long srcAddr) {
        jdk.internal.misc.Unsafe u = UNSAFE;
        m._m00(u.getDouble(null, srcAddr))
                ._m01(u.getDouble(null, srcAddr+8))
                ._m02(u.getDouble(null, srcAddr+16))
                ._m10(u.getDouble(null, srcAddr+24))
                ._m11(u.getDouble(null, srcAddr+32))
                ._m12(u.getDouble(null, srcAddr+40))
                ._m20(u.getDouble(null, srcAddr+48))
                ._m21(u.getDouble(null, srcAddr+56))
                ._m22(u.getDouble(null, srcAddr+64))
                ._m30(u.getDouble(null, srcAddr+72))
                ._m31(u.getDouble(null, srcAddr+80))
                ._m32(u.getDouble(null, srcAddr+88));
    }

    public static void getf(Matrix4d m, long srcAddr) {
        jdk.internal.misc.Unsafe u = UNSAFE;
        m._m00(u.getFloat(null, srcAddr))
                ._m01(u.getFloat(null, srcAddr+4))
                ._m02(u.getFloat(null, srcAddr+8))
                ._m03(u.getFloat(null, srcAddr+12))
                ._m10(u.getFloat(null, srcAddr+16))
                ._m11(u.getFloat(null, srcAddr+20))
                ._m12(u.getFloat(null, srcAddr+24))
                ._m13(u.getFloat(null, srcAddr+28))
                ._m20(u.getFloat(null, srcAddr+32))
                ._m21(u.getFloat(null, srcAddr+36))
                ._m22(u.getFloat(null, srcAddr+40))
                ._m23(u.getFloat(null, srcAddr+44))
                ._m30(u.getFloat(null, srcAddr+48))
                ._m31(u.getFloat(null, srcAddr+52))
                ._m32(u.getFloat(null, srcAddr+56))
                ._m33(u.getFloat(null, srcAddr+60));
    }

    public static void getf(Matrix4x3d m, long srcAddr) {
        jdk.internal.misc.Unsafe u = UNSAFE;
        m._m00(u.getFloat(null, srcAddr))
                ._m01(u.getFloat(null, srcAddr+4))
                ._m02(u.getFloat(null, srcAddr+8))
                ._m10(u.getFloat(null, srcAddr+12))
                ._m11(u.getFloat(null, srcAddr+16))
                ._m12(u.getFloat(null, srcAddr+20))
                ._m20(u.getFloat(null, srcAddr+24))
                ._m21(u.getFloat(null, srcAddr+28))
                ._m22(u.getFloat(null, srcAddr+32))
                ._m30(u.getFloat(null, srcAddr+36))
                ._m31(u.getFloat(null, srcAddr+40))
                ._m32(u.getFloat(null, srcAddr+44));
    }

    public static void get(Matrix3f m, long srcAddr) {
        for (int i = 0; i < 4; i++) {
            UNSAFE.putLong(m, Matrix3f_m00 + (i << 3), UNSAFE.getLongUnaligned(null, srcAddr + (i << 3)));
        }
        m._m22(UNSAFE.getFloat(null, srcAddr+32));
    }

    public static void get(Matrix3d m, long srcAddr) {
        jdk.internal.misc.Unsafe u = UNSAFE;
        m._m00(u.getDouble(null, srcAddr))
                ._m01(u.getDouble(null, srcAddr+8))
                ._m02(u.getDouble(null, srcAddr+16))
                ._m10(u.getDouble(null, srcAddr+24))
                ._m11(u.getDouble(null, srcAddr+32))
                ._m12(u.getDouble(null, srcAddr+40))
                ._m20(u.getDouble(null, srcAddr+48))
                ._m21(u.getDouble(null, srcAddr+56))
                ._m22(u.getDouble(null, srcAddr+64));
    }

    public static void get(Matrix3x2f m, long srcAddr) {
        for (int i = 0; i < 3; i++) {
            UNSAFE.putLongUnaligned(m, Matrix3x2f_m00 + (i << 3), UNSAFE.getLongUnaligned(null, srcAddr + (i << 3)));
        }
    }

    public static void get(Matrix3x2d m, long srcAddr) {
        jdk.internal.misc.Unsafe u = UNSAFE;
        m._m00(u.getDouble(null, srcAddr))
                ._m01(u.getDouble(null, srcAddr+8))
                ._m10(u.getDouble(null, srcAddr+16))
                ._m11(u.getDouble(null, srcAddr+24))
                ._m20(u.getDouble(null, srcAddr+32))
                ._m21(u.getDouble(null, srcAddr+40));
    }

    public static void getf(Matrix3d m, long srcAddr) {
        jdk.internal.misc.Unsafe u = UNSAFE;
        m._m00(u.getFloat(null, srcAddr))
                ._m01(u.getFloat(null, srcAddr+4))
                ._m02(u.getFloat(null, srcAddr+8))
                ._m10(u.getFloat(null, srcAddr+12))
                ._m11(u.getFloat(null, srcAddr+16))
                ._m12(u.getFloat(null, srcAddr+20))
                ._m20(u.getFloat(null, srcAddr+24))
                ._m21(u.getFloat(null, srcAddr+28))
                ._m22(u.getFloat(null, srcAddr+32));
    }

    public static void get(Matrix2f m, long srcAddr) {
        UNSAFE.putLongUnaligned(m, Matrix2f_m00,     UNSAFE.getLongUnaligned(null, srcAddr));
        UNSAFE.putLongUnaligned(m, Matrix2f_m00 + 8, UNSAFE.getLongUnaligned(null, srcAddr + 8));
    }

    public static void get(Matrix2d m, long srcAddr) {
        m._m00(UNSAFE.getDouble(null, srcAddr))
                ._m01(UNSAFE.getDouble(null, srcAddr+8))
                ._m10(UNSAFE.getDouble(null, srcAddr+16))
                ._m11(UNSAFE.getDouble(null, srcAddr+24));
    }

    public static void getf(Matrix2d m, long srcAddr) {
        m._m00(UNSAFE.getFloat(null, srcAddr))
                ._m01(UNSAFE.getFloat(null, srcAddr+4))
                ._m10(UNSAFE.getFloat(null, srcAddr+8))
                ._m11(UNSAFE.getFloat(null, srcAddr+12));
    }

    public static void get(Vector4d dst, long srcAddr) {
        dst.x = UNSAFE.getDouble(null, srcAddr);
        dst.y = UNSAFE.getDouble(null, srcAddr+8);
        dst.z = UNSAFE.getDouble(null, srcAddr+16);
        dst.w = UNSAFE.getDouble(null, srcAddr+24);
    }

    public static void get(Vector4f dst, long srcAddr) {
        dst.x = UNSAFE.getFloat(null, srcAddr);
        dst.y = UNSAFE.getFloat(null, srcAddr+4);
        dst.z = UNSAFE.getFloat(null, srcAddr+8);
        dst.w = UNSAFE.getFloat(null, srcAddr+12);
    }

    public static void get(Vector4i dst, long srcAddr) {
        dst.x = UNSAFE.getIntUnaligned(null, srcAddr);
        dst.y = UNSAFE.getIntUnaligned(null, srcAddr+4);
        dst.z = UNSAFE.getIntUnaligned(null, srcAddr+8);
        dst.w = UNSAFE.getIntUnaligned(null, srcAddr+12);
    }

    public static void get(Vector4L dst, long srcAddr) {
        dst.x = UNSAFE.getLongUnaligned(null, srcAddr);
        dst.y = UNSAFE.getLongUnaligned(null, srcAddr+8);
        dst.z = UNSAFE.getLongUnaligned(null, srcAddr+16);
        dst.w = UNSAFE.getLongUnaligned(null, srcAddr+24);
    }

    public static void get(Vector3f dst, long srcAddr) {
        dst.x = UNSAFE.getFloat(null, srcAddr);
        dst.y = UNSAFE.getFloat(null, srcAddr+4);
        dst.z = UNSAFE.getFloat(null, srcAddr+8);
    }

    public static void get(Vector3d dst, long srcAddr) {
        dst.x = UNSAFE.getDouble(null, srcAddr);
        dst.y = UNSAFE.getDouble(null, srcAddr+8);
        dst.z = UNSAFE.getDouble(null, srcAddr+16);
    }

    public static void get(Vector3i dst, long srcAddr) {
        dst.x = UNSAFE.getIntUnaligned(null, srcAddr);
        dst.y = UNSAFE.getIntUnaligned(null, srcAddr+4);
        dst.z = UNSAFE.getIntUnaligned(null, srcAddr+8);
    }

    public static void get(Vector3L dst, long srcAddr) {
        dst.x = UNSAFE.getLongUnaligned(null, srcAddr);
        dst.y = UNSAFE.getLongUnaligned(null, srcAddr+8);
        dst.z = UNSAFE.getLongUnaligned(null, srcAddr+16);
    }

    public static void get(Vector2f dst, long srcAddr) {
        dst.x = UNSAFE.getFloat(null, srcAddr);
        dst.y = UNSAFE.getFloat(null, srcAddr+4);
    }

    public static void get(Vector2d dst, long srcAddr) {
        dst.x = UNSAFE.getDouble(null, srcAddr);
        dst.y = UNSAFE.getDouble(null, srcAddr+8);
    }

    public static void get(Vector2i dst, long srcAddr) {
        dst.x = UNSAFE.getIntUnaligned(null, srcAddr);
        dst.y = UNSAFE.getIntUnaligned(null, srcAddr+4);
    }

    public static void get(Vector2L dst, long srcAddr) {
        dst.x = UNSAFE.getLongUnaligned(null, srcAddr);
        dst.y = UNSAFE.getLongUnaligned(null, srcAddr+8);
    }

    public static void putMatrix3f(Quaternionf q, long addr) {
        float dx = q.x + q.x;
        float dy = q.y + q.y;
        float dz = q.z + q.z;
        float q00 = dx * q.x;
        float q11 = dy * q.y;
        float q22 = dz * q.z;
        float q01 = dx * q.y;
        float q02 = dx * q.z;
        float q03 = dx * q.w;
        float q12 = dy * q.z;
        float q13 = dy * q.w;
        float q23 = dz * q.w;
        jdk.internal.misc.Unsafe u = UNSAFE;
        u.putFloat(null, addr, 1.0f - q11 - q22);
        u.putFloat(null, addr + 4, q01 + q23);
        u.putFloat(null, addr + 8, q02 - q13);
        u.putFloat(null, addr + 12, q01 - q23);
        u.putFloat(null, addr + 16, 1.0f - q22 - q00);
        u.putFloat(null, addr + 20, q12 + q03);
        u.putFloat(null, addr + 24, q02 + q13);
        u.putFloat(null, addr + 28, q12 - q03);
        u.putFloat(null, addr + 32, 1.0f - q11 - q00);
    }

    public static void putMatrix4f(Quaternionf q, long addr) {
        float dx = q.x + q.x;
        float dy = q.y + q.y;
        float dz = q.z + q.z;
        float q00 = dx * q.x;
        float q11 = dy * q.y;
        float q22 = dz * q.z;
        float q01 = dx * q.y;
        float q02 = dx * q.z;
        float q03 = dx * q.w;
        float q12 = dy * q.z;
        float q13 = dy * q.w;
        float q23 = dz * q.w;
        jdk.internal.misc.Unsafe u = UNSAFE;
        u.putFloat(null, addr, 1.0f - q11 - q22);
        u.putFloat(null, addr + 4, q01 + q23);
        u.putLongUnaligned(null, addr + 8, Float.floatToRawIntBits(q02 - q13) & 0xFFFFFFFFL);
        u.putFloat(null, addr + 16, q01 - q23);
        u.putFloat(null, addr + 20, 1.0f - q22 - q00);
        u.putLongUnaligned(null, addr + 24, Float.floatToRawIntBits(q12 + q03) & 0xFFFFFFFFL);
        u.putFloat(null, addr + 32, q02 + q13);
        u.putFloat(null, addr + 36, q12 - q03);
        u.putLongUnaligned(null, addr + 40, Float.floatToRawIntBits(1.0f - q11 - q00) & 0xFFFFFFFFL);
        u.putLongUnaligned(null, addr + 48, 0L);
        u.putLongUnaligned(null, addr + 56, 0x3F80000000000000L);
    }

    public static void putMatrix4x3f(Quaternionf q, long addr) {
        float dx = q.x + q.x;
        float dy = q.y + q.y;
        float dz = q.z + q.z;
        float q00 = dx * q.x;
        float q11 = dy * q.y;
        float q22 = dz * q.z;
        float q01 = dx * q.y;
        float q02 = dx * q.z;
        float q03 = dx * q.w;
        float q12 = dy * q.z;
        float q13 = dy * q.w;
        float q23 = dz * q.w;
        jdk.internal.misc.Unsafe u = UNSAFE;
        u.putFloat(null, addr, 1.0f - q11 - q22);
        u.putFloat(null, addr + 4, q01 + q23);
        u.putFloat(null, addr + 8, q02 - q13);
        u.putFloat(null, addr + 12, q01 - q23);
        u.putFloat(null, addr + 16, 1.0f - q22 - q00);
        u.putFloat(null, addr + 20, q12 + q03);
        u.putFloat(null, addr + 24, q02 + q13);
        u.putFloat(null, addr + 28, q12 - q03);
        u.putFloat(null, addr + 32, 1.0f - q11 - q00);
        u.putLongUnaligned(null, addr + 36, 0L);
        u.putFloat(null, addr + 44, 0.0f);
    }

    private static void throwNoDirectBufferException() {
        throw new IllegalArgumentException("Must use a direct buffer");
    }

    //#ifdef __HAS_NIO__
    public void putMatrix3f(Quaternionf q, int offset, ByteBuffer dest) {
        if (Options.DEBUG) checkPut(offset, dest.isDirect(), dest.capacity(), 9 << 2);
        if (dest.order() == ByteOrder.nativeOrder())
            putMatrix3f(q, UNSAFE.getLong(dest, ADDRESS) + offset);
        else
            MemUtil.putMatrix3f(q, offset, dest);
    }

    public void putMatrix3f(Quaternionf q, int offset, FloatBuffer dest) {
        if (Options.DEBUG) checkPut(offset, dest.isDirect(), dest.capacity(), 9);
        if (dest.order() == ByteOrder.nativeOrder())
            putMatrix3f(q, UNSAFE.getLong(dest, ADDRESS) + ((long) offset << 2));
        else
            MemUtil.putMatrix3f(q, offset, dest);
    }

    private static void checkPut(int offset, boolean direct, int capacity, int i) {
        if (!direct)
            throwNoDirectBufferException();
        if (capacity - offset < i)
            throw new BufferOverflowException();
    }

    public void putMatrix4f(Quaternionf q, int offset, ByteBuffer dest) {
        if (Options.DEBUG) checkPut(offset, dest.isDirect(), dest.capacity(), 16 << 2);
        if (dest.order() == ByteOrder.nativeOrder())
            putMatrix4f(q, UNSAFE.getLong(dest, ADDRESS) + offset);
        else
            MemUtil.putMatrix4f(q, offset, dest);
    }

    public void putMatrix4f(Quaternionf q, int offset, FloatBuffer dest) {
        if (Options.DEBUG) checkPut(offset, dest.isDirect(), dest.capacity(), 16);
        if (dest.order() == ByteOrder.nativeOrder())
            putMatrix4f(q, UNSAFE.getLong(dest, ADDRESS) + ((long) offset << 2));
        else
            MemUtil.putMatrix4f(q, offset, dest);
    }

    public void putMatrix4x3f(Quaternionf q, int offset, ByteBuffer dest) {
        if (Options.DEBUG) checkPut(offset, dest.isDirect(), dest.capacity(), 12 << 2);
        if (dest.order() == ByteOrder.nativeOrder())
            putMatrix4x3f(q, UNSAFE.getLong(dest, ADDRESS) + offset);
        else
            MemUtil.putMatrix4x3f(q, offset, dest);
    }

    public void putMatrix4x3f(Quaternionf q, int offset, FloatBuffer dest) {
        if (Options.DEBUG) checkPut(offset, dest.isDirect(), dest.capacity(), 12);
        if (dest.order() == ByteOrder.nativeOrder())
            putMatrix4x3f(q, UNSAFE.getLong(dest, ADDRESS) + ((long) offset << 2));
        else
            MemUtil.putMatrix4x3f(q, offset, dest);
    }

    public void put(Matrix4f m, int offset, FloatBuffer dest) {
        if (Options.DEBUG) checkPut(offset, dest.isDirect(), dest.capacity(), 16);
        if (dest.order() == ByteOrder.nativeOrder())
            put(m, UNSAFE.getLong(dest, ADDRESS) + ((long) offset << 2));
        else
            MemUtil.put(m, offset, dest);
    }

    public void put(Matrix4f m, int offset, ByteBuffer dest) {
        if (Options.DEBUG) checkPut(offset, dest.isDirect(), dest.capacity(), 16 << 2);
        if (dest.order() == ByteOrder.nativeOrder())
            put(m, UNSAFE.getLong(dest, ADDRESS) + offset);
        else
            MemUtil.put(m, offset, dest);
    }

    public void put4x3(Matrix4f m, int offset, FloatBuffer dest) {
        if (Options.DEBUG) checkPut(offset, dest.isDirect(), dest.capacity(), 12);
        if (dest.order() == ByteOrder.nativeOrder())
            put4x3(m, UNSAFE.getLong(dest, ADDRESS) + ((long) offset << 2));
        else
            MemUtil.put4x3(m, offset, dest);
    }

    public void put4x3(Matrix4f m, int offset, ByteBuffer dest) {
        if (Options.DEBUG) checkPut(offset, dest.isDirect(), dest.capacity(), 12 << 2);
        if (dest.order() == ByteOrder.nativeOrder())
            put4x3(m, UNSAFE.getLong(dest, ADDRESS) + offset);
        else
            MemUtil.put4x3(m, offset, dest);
    }

    public void put3x4(Matrix4f m, int offset, FloatBuffer dest) {
        if (Options.DEBUG) checkPut(offset, dest.isDirect(), dest.capacity(), 12);
        if (dest.order() == ByteOrder.nativeOrder())
            put3x4(m, UNSAFE.getLong(dest, ADDRESS) + ((long) offset << 2));
        else
            MemUtil.put3x4(m, offset, dest);
    }

    public void put3x4(Matrix4f m, int offset, ByteBuffer dest) {
        if (Options.DEBUG) checkPut(offset, dest.isDirect(), dest.capacity(), 12 << 2);
        if (dest.order() == ByteOrder.nativeOrder())
            put3x4(m, UNSAFE.getLong(dest, ADDRESS) + offset);
        else
            MemUtil.put3x4(m, offset, dest);
    }

    public void put(Matrix4x3f m, int offset, FloatBuffer dest) {
        if (Options.DEBUG) checkPut(offset, dest.isDirect(), dest.capacity(), 12);
        if (dest.order() == ByteOrder.nativeOrder())
            put(m, UNSAFE.getLong(dest, ADDRESS) + ((long) offset << 2));
        else
            MemUtil.put(m, offset, dest);
    }

    public void put(Matrix4x3f m, int offset, ByteBuffer dest) {
        if (Options.DEBUG) checkPut(offset, dest.isDirect(), dest.capacity(), 12 << 2);
        if (dest.order() == ByteOrder.nativeOrder())
            put(m, UNSAFE.getLong(dest, ADDRESS) + offset);
        else
            MemUtil.put(m, offset, dest);
    }

    public void put4x4(Matrix4x3f m, int offset, FloatBuffer dest) {
        if (Options.DEBUG) checkPut(offset, dest.isDirect(), dest.capacity(), 16);
        if (dest.order() == ByteOrder.nativeOrder())
            put4x4(m, UNSAFE.getLong(dest, ADDRESS) + ((long) offset << 2));
        else
            MemUtil.put4x4(m, offset, dest);
    }

    public void put4x4(Matrix4x3f m, int offset, ByteBuffer dest) {
        if (Options.DEBUG) checkPut(offset, dest.isDirect(), dest.capacity(), 16 << 2);
        if (dest.order() == ByteOrder.nativeOrder())
            put4x4(m, UNSAFE.getLong(dest, ADDRESS) + offset);
        else
            MemUtil.put4x4(m, offset, dest);
    }

    public void put3x4(Matrix4x3f m, int offset, FloatBuffer dest) {
        if (Options.DEBUG) checkPut(offset, dest.isDirect(), dest.capacity(), 12);
        if (dest.order() == ByteOrder.nativeOrder())
            put3x4(m, UNSAFE.getLong(dest, ADDRESS) + ((long) offset << 2));
        else
            MemUtil.put3x4(m, offset, dest);
    }

    public void put3x4(Matrix4x3f m, int offset, ByteBuffer dest) {
        if (Options.DEBUG) checkPut(offset, dest.isDirect(), dest.capacity(), 12 << 2);
        if (dest.order() == ByteOrder.nativeOrder())
            put3x4(m, UNSAFE.getLong(dest, ADDRESS) + offset);
        else
            MemUtil.put3x4(m, offset, dest);
    }

    public void put4x4(Matrix4x3d m, int offset, DoubleBuffer dest) {
        if (Options.DEBUG) checkPut(offset, dest.isDirect(), dest.capacity(), 16);
        if (dest.order() == ByteOrder.nativeOrder())
            put4x4(m, UNSAFE.getLong(dest, ADDRESS) + ((long) offset << 3));
        else
            MemUtil.put4x4(m, offset, dest);
    }

    public void put4x4(Matrix4x3d m, int offset, ByteBuffer dest) {
        if (Options.DEBUG) checkPut(offset, dest.isDirect(), dest.capacity(), 16 << 3);
        if (dest.order() == ByteOrder.nativeOrder())
            put4x4(m, UNSAFE.getLong(dest, ADDRESS) + offset);
        else
            MemUtil.put4x4(m, offset, dest);
    }

    public void put4x4(Matrix3x2f m, int offset, FloatBuffer dest) {
        if (Options.DEBUG) checkPut(offset, dest.isDirect(), dest.capacity(), 16);
        if (dest.order() == ByteOrder.nativeOrder())
            put4x4(m, UNSAFE.getLong(dest, ADDRESS) + ((long) offset << 2));
        else
            MemUtil.put4x4(m, offset, dest);
    }

    public void put4x4(Matrix3x2f m, int offset, ByteBuffer dest) {
        if (Options.DEBUG) checkPut(offset, dest.isDirect(), dest.capacity(), 16 << 2);
        if (dest.order() == ByteOrder.nativeOrder())
            put4x4(m, UNSAFE.getLong(dest, ADDRESS) + offset);
        else
            MemUtil.put4x4(m, offset, dest);
    }

    public void put4x4(Matrix3x2d m, int offset, DoubleBuffer dest) {
        if (Options.DEBUG) checkPut(offset, dest.isDirect(), dest.capacity(), 16);
        if (dest.order() == ByteOrder.nativeOrder())
            put4x4(m, UNSAFE.getLong(dest, ADDRESS) + ((long) offset << 3));
        else
            MemUtil.put4x4(m, offset, dest);
    }

    public void put4x4(Matrix3x2d m, int offset, ByteBuffer dest) {
        if (Options.DEBUG) checkPut(offset, dest.isDirect(), dest.capacity(), 16 << 3);
        if (dest.order() == ByteOrder.nativeOrder())
            put4x4(m, UNSAFE.getLong(dest, ADDRESS) + offset);
        else
            MemUtil.put4x4(m, offset, dest);
    }

    public void put3x3(Matrix3x2f m, int offset, FloatBuffer dest) {
        if (Options.DEBUG) checkPut(offset, dest.isDirect(), dest.capacity(), 9);
        if (dest.order() == ByteOrder.nativeOrder())
            put3x3(m, UNSAFE.getLong(dest, ADDRESS) + ((long) offset << 2));
        else
            MemUtil.put3x3(m, offset, dest);
    }

    public void put3x3(Matrix3x2f m, int offset, ByteBuffer dest) {
        if (Options.DEBUG) checkPut(offset, dest.isDirect(), dest.capacity(), 9 << 2);
        if (dest.order() == ByteOrder.nativeOrder())
            put3x3(m, UNSAFE.getLong(dest, ADDRESS) + offset);
        else
            MemUtil.put3x3(m, offset, dest);
    }

    public void put3x3(Matrix3x2d m, int offset, DoubleBuffer dest) {
        if (Options.DEBUG) checkPut(offset, dest.isDirect(), dest.capacity(), 9);
        if (dest.order() == ByteOrder.nativeOrder())
            put3x3(m, UNSAFE.getLong(dest, ADDRESS) + ((long) offset << 3));
        else
            MemUtil.put3x3(m, offset, dest);
    }

    public void put3x3(Matrix3x2d m, int offset, ByteBuffer dest) {
        if (Options.DEBUG) checkPut(offset, dest.isDirect(), dest.capacity(), 9 << 3);
        if (dest.order() == ByteOrder.nativeOrder())
            put3x3(m, UNSAFE.getLong(dest, ADDRESS) + offset);
        else
            MemUtil.put3x3(m, offset, dest);
    }

    public void putTransposed(Matrix4f m, int offset, FloatBuffer dest) {
        if (Options.DEBUG) checkPut(offset, dest.isDirect(), dest.capacity(), 16);
        if (dest.order() == ByteOrder.nativeOrder())
            putTransposed(m, UNSAFE.getLong(dest, ADDRESS) + ((long) offset << 2));
        else
            MemUtil.putTransposed(m, offset, dest);
    }

    public void putTransposed(Matrix4f m, int offset, ByteBuffer dest) {
        if (Options.DEBUG) checkPut(offset, dest.isDirect(), dest.capacity(), 16 << 2);
        if (dest.order() == ByteOrder.nativeOrder())
            putTransposed(m, UNSAFE.getLong(dest, ADDRESS) + offset);
        else
            MemUtil.putTransposed(m, offset, dest);
    }

    public void put4x3Transposed(Matrix4f m, int offset, FloatBuffer dest) {
        if (Options.DEBUG) checkPut(offset, dest.isDirect(), dest.capacity(), 12);
        if (dest.order() == ByteOrder.nativeOrder())
            put4x3Transposed(m, UNSAFE.getLong(dest, ADDRESS) + ((long) offset << 2));
        else
            MemUtil.put4x3Transposed(m, offset, dest);
    }

    public void put4x3Transposed(Matrix4f m, int offset, ByteBuffer dest) {
        if (Options.DEBUG) checkPut(offset, dest.isDirect(), dest.capacity(), 12 << 2);
        if (dest.order() == ByteOrder.nativeOrder())
            put4x3Transposed(m, UNSAFE.getLong(dest, ADDRESS) + offset);
        else
            MemUtil.put4x3Transposed(m, offset, dest);
    }

    public void putTransposed(Matrix4x3f m, int offset, FloatBuffer dest) {
        if (Options.DEBUG) checkPut(offset, dest.isDirect(), dest.capacity(), 12);
        if (dest.order() == ByteOrder.nativeOrder())
            putTransposed(m, UNSAFE.getLong(dest, ADDRESS) + ((long) offset << 2));
        else
            MemUtil.putTransposed(m, offset, dest);
    }

    public void putTransposed(Matrix4x3f m, int offset, ByteBuffer dest) {
        if (Options.DEBUG) checkPut(offset, dest.isDirect(), dest.capacity(), 12 << 2);
        if (dest.order() == ByteOrder.nativeOrder())
            putTransposed(m, UNSAFE.getLong(dest, ADDRESS) + offset);
        else
            MemUtil.putTransposed(m, offset, dest);
    }

    public void putTransposed(Matrix3f m, int offset, FloatBuffer dest) {
        if (Options.DEBUG) checkPut(offset, dest.isDirect(), dest.capacity(), 9);
        if (dest.order() == ByteOrder.nativeOrder())
            putTransposed(m, UNSAFE.getLong(dest, ADDRESS) + ((long) offset << 2));
        else
            MemUtil.putTransposed(m, offset, dest);
    }

    public void putTransposed(Matrix3f m, int offset, ByteBuffer dest) {
        if (Options.DEBUG) checkPut(offset, dest.isDirect(), dest.capacity(), 9 << 2);
        if (dest.order() == ByteOrder.nativeOrder())
            putTransposed(m, UNSAFE.getLong(dest, ADDRESS) + offset);
        else
            MemUtil.putTransposed(m, offset, dest);
    }

    public void putTransposed(Matrix3x2f m, int offset, FloatBuffer dest) {
        if (Options.DEBUG) checkPut(offset, dest.isDirect(), dest.capacity(), 6);
        if (dest.order() == ByteOrder.nativeOrder())
            putTransposed(m, UNSAFE.getLong(dest, ADDRESS) + ((long) offset << 2));
        else
            MemUtil.putTransposed(m, offset, dest);
    }

    public void putTransposed(Matrix2f m, int offset, FloatBuffer dest) {
        if (Options.DEBUG) checkPut(offset, dest.isDirect(), dest.capacity(), 4);
        if (dest.order() == ByteOrder.nativeOrder())
            putTransposed(m, UNSAFE.getLong(dest, ADDRESS) + ((long) offset << 2));
        else
            MemUtil.putTransposed(m, offset, dest);
    }

    public void putTransposed(Matrix2f m, int offset, ByteBuffer dest) {
        if (Options.DEBUG) checkPut(offset, dest.isDirect(), dest.capacity(), 4 << 2);
        if (dest.order() == ByteOrder.nativeOrder())
            putTransposed(m, UNSAFE.getLong(dest, ADDRESS) + offset);
        else
            MemUtil.putTransposed(m, offset, dest);
    }

    public void put(Matrix4d m, int offset, DoubleBuffer dest) {
        if (Options.DEBUG) checkPut(offset, dest.isDirect(), dest.capacity(), 16);
        if (dest.order() == ByteOrder.nativeOrder())
            put(m, UNSAFE.getLong(dest, ADDRESS) + ((long) offset << 3));
        else
            MemUtil.put(m, offset, dest);
    }

    public void put(Matrix4d m, int offset, ByteBuffer dest) {
        if (Options.DEBUG) checkPut(offset, dest.isDirect(), dest.capacity(), 16 << 3);
        if (dest.order() == ByteOrder.nativeOrder())
            put(m, UNSAFE.getLong(dest, ADDRESS) + offset);
        else
            MemUtil.put(m, offset, dest);
    }

    public void put(Matrix4x3d m, int offset, DoubleBuffer dest) {
        if (Options.DEBUG) checkPut(offset, dest.isDirect(), dest.capacity(), 12);
        if (dest.order() == ByteOrder.nativeOrder())
            put(m, UNSAFE.getLong(dest, ADDRESS) + ((long) offset << 3));
        else
            MemUtil.put(m, offset, dest);
    }

    public void put(Matrix4x3d m, int offset, ByteBuffer dest) {
        if (Options.DEBUG) checkPut(offset, dest.isDirect(), dest.capacity(), 12 << 3);
        if (dest.order() == ByteOrder.nativeOrder())
            put(m, UNSAFE.getLong(dest, ADDRESS) + offset);
        else
            MemUtil.put(m, offset, dest);
    }

    public void putf(Matrix4d m, int offset, FloatBuffer dest) {
        if (Options.DEBUG) checkPut(offset, dest.isDirect(), dest.capacity(), 16);
        if (dest.order() == ByteOrder.nativeOrder())
            putf(m, UNSAFE.getLong(dest, ADDRESS) + ((long) offset << 2));
        else
            MemUtil.putf(m, offset, dest);
    }

    public void putf(Matrix4d m, int offset, ByteBuffer dest) {
        if (Options.DEBUG) checkPut(offset, dest.isDirect(), dest.capacity(), 16 << 2);
        if (dest.order() == ByteOrder.nativeOrder())
            putf(m, UNSAFE.getLong(dest, ADDRESS) + offset);
        else
            MemUtil.putf(m, offset, dest);
    }

    public void putf(Matrix4x3d m, int offset, FloatBuffer dest) {
        if (Options.DEBUG) checkPut(offset, dest.isDirect(), dest.capacity(), 12);
        if (dest.order() == ByteOrder.nativeOrder())
            putf(m, UNSAFE.getLong(dest, ADDRESS) + ((long) offset << 2));
        else
            MemUtil.putf(m, offset, dest);
    }

    public void putf(Matrix4x3d m, int offset, ByteBuffer dest) {
        if (Options.DEBUG) checkPut(offset, dest.isDirect(), dest.capacity(), 12 << 2);
        if (dest.order() == ByteOrder.nativeOrder())
            putf(m, UNSAFE.getLong(dest, ADDRESS) + offset);
        else
            MemUtil.putf(m, offset, dest);
    }

    public void putTransposed(Matrix4d m, int offset, DoubleBuffer dest) {
        if (Options.DEBUG) checkPut(offset, dest.isDirect(), dest.capacity(), 16);
        if (dest.order() == ByteOrder.nativeOrder())
            putTransposed(m, UNSAFE.getLong(dest, ADDRESS) + ((long) offset << 3));
        else
            MemUtil.putTransposed(m, offset, dest);
    }

    public void putTransposed(Matrix4d m, int offset, ByteBuffer dest) {
        if (Options.DEBUG) checkPut(offset, dest.isDirect(), dest.capacity(), 16 << 3);
        if (dest.order() == ByteOrder.nativeOrder())
            putTransposed(m, UNSAFE.getLong(dest, ADDRESS) + offset);
        else
            MemUtil.putTransposed(m, offset, dest);
    }

    public void put4x3Transposed(Matrix4d m, int offset, DoubleBuffer dest) {
        if (Options.DEBUG) checkPut(offset, dest.isDirect(), dest.capacity(), 12);
        if (dest.order() == ByteOrder.nativeOrder())
            put4x3Transposed(m, UNSAFE.getLong(dest, ADDRESS) + ((long) offset << 3));
        else
            MemUtil.put4x3Transposed(m, offset, dest);
    }

    public void put4x3Transposed(Matrix4d m, int offset, ByteBuffer dest) {
        if (Options.DEBUG) checkPut(offset, dest.isDirect(), dest.capacity(), 12 << 3);
        if (dest.order() == ByteOrder.nativeOrder())
            put4x3Transposed(m, UNSAFE.getLong(dest, ADDRESS) + offset);
        else
            MemUtil.put4x3Transposed(m, offset, dest);
    }

    public void putTransposed(Matrix4x3d m, int offset, DoubleBuffer dest) {
        if (Options.DEBUG) checkPut(offset, dest.isDirect(), dest.capacity(), 12);
        if (dest.order() == ByteOrder.nativeOrder())
            putTransposed(m, UNSAFE.getLong(dest, ADDRESS) + ((long) offset << 3));
        else
            MemUtil.putTransposed(m, offset, dest);
    }

    public void putTransposed(Matrix4x3d m, int offset, ByteBuffer dest) {
        if (Options.DEBUG) checkPut(offset, dest.isDirect(), dest.capacity(), 12 << 3);
        if (dest.order() == ByteOrder.nativeOrder())
            putTransposed(m, UNSAFE.getLong(dest, ADDRESS) + offset);
        else
            MemUtil.putTransposed(m, offset, dest);
    }

    public void putTransposed(Matrix3d m, int offset, DoubleBuffer dest) {
        if (Options.DEBUG) checkPut(offset, dest.isDirect(), dest.capacity(), 9);
        if (dest.order() == ByteOrder.nativeOrder())
            putTransposed(m, UNSAFE.getLong(dest, ADDRESS) + ((long) offset << 3));
        else
            MemUtil.putTransposed(m, offset, dest);
    }

    public void putTransposed(Matrix3d m, int offset, ByteBuffer dest) {
        if (Options.DEBUG) checkPut(offset, dest.isDirect(), dest.capacity(), 9 << 3);
        if (dest.order() == ByteOrder.nativeOrder())
            putTransposed(m, UNSAFE.getLong(dest, ADDRESS) + offset);
        else
            MemUtil.putTransposed(m, offset, dest);
    }

    public void putTransposed(Matrix3x2d m, int offset, DoubleBuffer dest) {
        if (Options.DEBUG) checkPut(offset, dest.isDirect(), dest.capacity(), 6);
        if (dest.order() == ByteOrder.nativeOrder())
            putTransposed(m, UNSAFE.getLong(dest, ADDRESS) + ((long) offset << 3));
        else
            MemUtil.putTransposed(m, offset, dest);
    }

    public void putTransposed(Matrix3x2d m, int offset, ByteBuffer dest) {
        if (Options.DEBUG) checkPut(offset, dest.isDirect(), dest.capacity(), 6 << 3);
        if (dest.order() == ByteOrder.nativeOrder())
            putTransposed(m, UNSAFE.getLong(dest, ADDRESS) + offset);
        else
            MemUtil.putTransposed(m, offset, dest);
    }

    public void putTransposed(Matrix2d m, int offset, DoubleBuffer dest) {
        if (Options.DEBUG) checkPut(offset, dest.isDirect(), dest.capacity(), 4);
        if (dest.order() == ByteOrder.nativeOrder())
            putTransposed(m, UNSAFE.getLong(dest, ADDRESS) + ((long) offset << 3));
        else
            MemUtil.putTransposed(m, offset, dest);
    }

    public void putTransposed(Matrix2d m, int offset, ByteBuffer dest) {
        if (Options.DEBUG) checkPut(offset, dest.isDirect(), dest.capacity(), 4 << 3);
        if (dest.order() == ByteOrder.nativeOrder())
            putTransposed(m, UNSAFE.getLong(dest, ADDRESS) + offset);
        else
            MemUtil.putTransposed(m, offset, dest);
    }

    public void putfTransposed(Matrix4d m, int offset, FloatBuffer dest) {
        if (Options.DEBUG) checkPut(offset, dest.isDirect(), dest.capacity(), 16);
        if (dest.order() == ByteOrder.nativeOrder())
            putfTransposed(m, UNSAFE.getLong(dest, ADDRESS) + ((long) offset << 2));
        else
            MemUtil.putfTransposed(m, offset, dest);
    }

    public void putfTransposed(Matrix4d m, int offset, ByteBuffer dest) {
        if (Options.DEBUG) checkPut(offset, dest.isDirect(), dest.capacity(), 16 << 2);
        if (dest.order() == ByteOrder.nativeOrder())
            putfTransposed(m, UNSAFE.getLong(dest, ADDRESS) + offset);
        else
            MemUtil.putfTransposed(m, offset, dest);
    }

    public void putfTransposed(Matrix4x3d m, int offset, FloatBuffer dest) {
        if (Options.DEBUG) checkPut(offset, dest.isDirect(), dest.capacity(), 12);
        if (dest.order() == ByteOrder.nativeOrder())
            putfTransposed(m, UNSAFE.getLong(dest, ADDRESS) + ((long) offset << 2));
        else
            MemUtil.putfTransposed(m, offset, dest);
    }

    public void putfTransposed(Matrix4x3d m, int offset, ByteBuffer dest) {
        if (Options.DEBUG) checkPut(offset, dest.isDirect(), dest.capacity(), 12 << 2);
        if (dest.order() == ByteOrder.nativeOrder())
            putfTransposed(m, UNSAFE.getLong(dest, ADDRESS) + offset);
        else
            MemUtil.putfTransposed(m, offset, dest);
    }

    public void putfTransposed(Matrix3d m, int offset, FloatBuffer dest) {
        if (Options.DEBUG) checkPut(offset, dest.isDirect(), dest.capacity(), 9);
        if (dest.order() == ByteOrder.nativeOrder())
            putfTransposed(m, UNSAFE.getLong(dest, ADDRESS) + ((long) offset << 2));
        else
            MemUtil.putfTransposed(m, offset, dest);
    }

    public void putfTransposed(Matrix3d m, int offset, ByteBuffer dest) {
        if (Options.DEBUG) checkPut(offset, dest.isDirect(), dest.capacity(), 9 << 2);
        if (dest.order() == ByteOrder.nativeOrder())
            putfTransposed(m, UNSAFE.getLong(dest, ADDRESS) + offset);
        else
            MemUtil.putfTransposed(m, offset, dest);
    }

    public void putfTransposed(Matrix3x2d m, int offset, FloatBuffer dest) {
        if (Options.DEBUG) checkPut(offset, dest.isDirect(), dest.capacity(), 6);
        if (dest.order() == ByteOrder.nativeOrder())
            putfTransposed(m, UNSAFE.getLong(dest, ADDRESS) + ((long) offset << 2));
        else
            MemUtil.putfTransposed(m, offset, dest);
    }

    public void putfTransposed(Matrix3x2d m, int offset, ByteBuffer dest) {
        if (Options.DEBUG) checkPut(offset, dest.isDirect(), dest.capacity(), 6 << 2);
        if (dest.order() == ByteOrder.nativeOrder())
            putfTransposed(m, UNSAFE.getLong(dest, ADDRESS) + offset);
        else
            MemUtil.putfTransposed(m, offset, dest);
    }

    public void putfTransposed(Matrix2d m, int offset, FloatBuffer dest) {
        if (Options.DEBUG) checkPut(offset, dest.isDirect(), dest.capacity(), 4);
        if (dest.order() == ByteOrder.nativeOrder())
            putfTransposed(m, UNSAFE.getLong(dest, ADDRESS) + ((long) offset << 2));
        else
            MemUtil.putfTransposed(m, offset, dest);
    }

    public void putfTransposed(Matrix2d m, int offset, ByteBuffer dest) {
        if (Options.DEBUG) checkPut(offset, dest.isDirect(), dest.capacity(), 4 << 2);
        if (dest.order() == ByteOrder.nativeOrder())
            putfTransposed(m, UNSAFE.getLong(dest, ADDRESS) + offset);
        else
            MemUtil.putfTransposed(m, offset, dest);
    }

    public void put(Matrix3f m, int offset, FloatBuffer dest) {
        if (Options.DEBUG) checkPut(offset, dest.isDirect(), dest.capacity(), 9);
        if (dest.order() == ByteOrder.nativeOrder())
            put(m, UNSAFE.getLong(dest, ADDRESS) + ((long) offset << 2));
        else
            MemUtil.put(m, offset, dest);
    }

    public void put(Matrix3f m, int offset, ByteBuffer dest) {
        if (Options.DEBUG) checkPut(offset, dest.isDirect(), dest.capacity(), 9 << 2);
        if (dest.order() == ByteOrder.nativeOrder())
            put(m, UNSAFE.getLong(dest, ADDRESS) + offset);
        else
            MemUtil.put(m, offset, dest);
    }

    public void put3x4(Matrix3f m, int offset, FloatBuffer dest) {
        if (Options.DEBUG) checkPut(offset, dest.isDirect(), dest.capacity(), 12);
        if (dest.order() == ByteOrder.nativeOrder())
            put3x4(m, UNSAFE.getLong(dest, ADDRESS) + ((long) offset << 2));
        else
            MemUtil.put3x4(m, offset, dest);
    }

    public void put3x4(Matrix3f m, int offset, ByteBuffer dest) {
        if (Options.DEBUG) checkPut(offset, dest.isDirect(), dest.capacity(), 12 << 2);
        if (dest.order() == ByteOrder.nativeOrder())
            put3x4(m, UNSAFE.getLong(dest, ADDRESS) + offset);
        else
            MemUtil.put3x4(m, offset, dest);
    }

    public void put(Matrix3d m, int offset, DoubleBuffer dest) {
        if (Options.DEBUG) checkPut(offset, dest.isDirect(), dest.capacity(), 9);
        if (dest.order() == ByteOrder.nativeOrder())
            put(m, UNSAFE.getLong(dest, ADDRESS) + ((long) offset << 3));
        else
            MemUtil.put(m, offset, dest);
    }

    public void put(Matrix3d m, int offset, ByteBuffer dest) {
        if (Options.DEBUG) checkPut(offset, dest.isDirect(), dest.capacity(), 9 << 3);
        if (dest.order() == ByteOrder.nativeOrder())
            put(m, UNSAFE.getLong(dest, ADDRESS) + offset);
        else
            MemUtil.put(m, offset, dest);
    }

    public void put(Matrix3x2f m, int offset, FloatBuffer dest) {
        if (Options.DEBUG) checkPut(offset, dest.isDirect(), dest.capacity(), 6);
        if (dest.order() == ByteOrder.nativeOrder())
            put(m, UNSAFE.getLong(dest, ADDRESS) + ((long) offset << 2));
        else
            MemUtil.put(m, offset, dest);
    }

    public void put(Matrix3x2f m, int offset, ByteBuffer dest) {
        if (Options.DEBUG) checkPut(offset, dest.isDirect(), dest.capacity(), 6 << 2);
        if (dest.order() == ByteOrder.nativeOrder())
            put(m, UNSAFE.getLong(dest, ADDRESS) + offset);
        else
            MemUtil.put(m, offset, dest);
    }

    public void put(Matrix3x2d m, int offset, DoubleBuffer dest) {
        if (Options.DEBUG) checkPut(offset, dest.isDirect(), dest.capacity(), 6);
        if (dest.order() == ByteOrder.nativeOrder())
            put(m, UNSAFE.getLong(dest, ADDRESS) + ((long) offset << 3));
        else
            MemUtil.put(m, offset, dest);
    }

    public void put(Matrix3x2d m, int offset, ByteBuffer dest) {
        if (Options.DEBUG) checkPut(offset, dest.isDirect(), dest.capacity(), 6 << 3);
        if (dest.order() == ByteOrder.nativeOrder())
            put(m, UNSAFE.getLong(dest, ADDRESS) + offset);
        else
            MemUtil.put(m, offset, dest);
    }

    public void putf(Matrix3d m, int offset, FloatBuffer dest) {
        if (Options.DEBUG) checkPut(offset, dest.isDirect(), dest.capacity(), 9);
        if (dest.order() == ByteOrder.nativeOrder())
            putf(m, UNSAFE.getLong(dest, ADDRESS) + ((long) offset << 2));
        else
            MemUtil.putf(m, offset, dest);
    }

    public void putf(Matrix3d m, int offset, ByteBuffer dest) {
        if (Options.DEBUG) checkPut(offset, dest.isDirect(), dest.capacity(), 9 << 2);
        if (dest.order() == ByteOrder.nativeOrder())
            putf(m, UNSAFE.getLong(dest, ADDRESS) + offset);
        else
            MemUtil.putf(m, offset, dest);
    }

    public void put(Matrix2f m, int offset, FloatBuffer dest) {
        if (Options.DEBUG) checkPut(offset, dest.isDirect(), dest.capacity(), 4);
        if (dest.order() == ByteOrder.nativeOrder())
            put(m, UNSAFE.getLong(dest, ADDRESS) + ((long) offset << 2));
        else
            MemUtil.put(m, offset, dest);
    }

    public void put(Matrix2f m, int offset, ByteBuffer dest) {
        if (Options.DEBUG) checkPut(offset, dest.isDirect(), dest.capacity(), 4 << 2);
        if (dest.order() == ByteOrder.nativeOrder())
            put(m, UNSAFE.getLong(dest, ADDRESS) + offset);
        else
            MemUtil.put(m, offset, dest);
    }

    public void put(Matrix2d m, int offset, DoubleBuffer dest) {
        if (Options.DEBUG) checkPut(offset, dest.isDirect(), dest.capacity(), 4);
        if (dest.order() == ByteOrder.nativeOrder())
            put(m, UNSAFE.getLong(dest, ADDRESS) + ((long) offset << 3));
        else
            MemUtil.put(m, offset, dest);
    }

    public void put(Matrix2d m, int offset, ByteBuffer dest) {
        if (Options.DEBUG) checkPut(offset, dest.isDirect(), dest.capacity(), 4 << 3);
        if (dest.order() == ByteOrder.nativeOrder())
            put(m, UNSAFE.getLong(dest, ADDRESS) + offset);
        else
            MemUtil.put(m, offset, dest);
    }

    public void putf(Matrix2d m, int offset, FloatBuffer dest) {
        if (Options.DEBUG) checkPut(offset, dest.isDirect(), dest.capacity(), 4);
        if (dest.order() == ByteOrder.nativeOrder())
            putf(m, UNSAFE.getLong(dest, ADDRESS) + ((long) offset << 2));
        else
            MemUtil.putf(m, offset, dest);
    }

    public void putf(Matrix2d m, int offset, ByteBuffer dest) {
        if (Options.DEBUG) checkPut(offset, dest.isDirect(), dest.capacity(), 4 << 2);
        if (dest.order() == ByteOrder.nativeOrder())
            putf(m, UNSAFE.getLong(dest, ADDRESS) + offset);
        else
            MemUtil.putf(m, offset, dest);
    }

    public void put(Vector4d src, int offset, DoubleBuffer dest) {
        if (Options.DEBUG) checkPut(offset, dest.isDirect(), dest.capacity(), 4);
        if (dest.order() == ByteOrder.nativeOrder())
            put(src, UNSAFE.getLong(dest, ADDRESS) + ((long) offset << 3));
        else
            MemUtil.put(src, offset, dest);
    }

    public void put(Vector4d src, int offset, FloatBuffer dest) {
        if (Options.DEBUG) checkPut(offset, dest.isDirect(), dest.capacity(), 4);
        if (dest.order() == ByteOrder.nativeOrder())
            putf(src, UNSAFE.getLong(dest, ADDRESS) + ((long) offset << 2));
        else
            MemUtil.put(src, offset, dest);
    }

    public void put(Vector4d src, int offset, ByteBuffer dest) {
        if (Options.DEBUG) checkPut(offset, dest.isDirect(), dest.capacity(), 4 << 3);
        if (dest.order() == ByteOrder.nativeOrder())
            put(src, UNSAFE.getLong(dest, ADDRESS) + offset);
        else
            MemUtil.put(src, offset, dest);
    }

    public void putf(Vector4d src, int offset, ByteBuffer dest) {
        if (Options.DEBUG) checkPut(offset, dest.isDirect(), dest.capacity(), 4 << 2);
        if (dest.order() == ByteOrder.nativeOrder())
            putf(src, UNSAFE.getLong(dest, ADDRESS) + offset);
        else
            MemUtil.putf(src, offset, dest);
    }

    public void put(Vector4f src, int offset, FloatBuffer dest) {
        if (Options.DEBUG) checkPut(offset, dest.isDirect(), dest.capacity(), 4);
        if (dest.order() == ByteOrder.nativeOrder())
            put(src, UNSAFE.getLong(dest, ADDRESS) + ((long) offset << 2));
        else
            MemUtil.put(src, offset, dest);
    }

    public void put(Vector4f src, int offset, ByteBuffer dest) {
        if (Options.DEBUG) checkPut(offset, dest.isDirect(), dest.capacity(), 4 << 2);
        if (dest.order() == ByteOrder.nativeOrder())
            put(src, UNSAFE.getLong(dest, ADDRESS) + offset);
        else
            MemUtil.put(src, offset, dest);
    }

    public void put(Vector4i src, int offset, IntBuffer dest) {
        if (Options.DEBUG) checkPut(offset, dest.isDirect(), dest.capacity(), 4);
        if (dest.order() == ByteOrder.nativeOrder())
            put(src, UNSAFE.getLong(dest, ADDRESS) + ((long) offset << 2));
        else
            MemUtil.put(src, offset, dest);
    }

    public void put(Vector4i src, int offset, ByteBuffer dest) {
        if (Options.DEBUG) checkPut(offset, dest.isDirect(), dest.capacity(), 4 << 2);
        if (dest.order() == ByteOrder.nativeOrder())
            put(src, UNSAFE.getLong(dest, ADDRESS) + offset);
        else
            MemUtil.put(src, offset, dest);
    }

    public void put(Vector3f src, int offset, FloatBuffer dest) {
        if (Options.DEBUG) checkPut(offset, dest.isDirect(), dest.capacity(), 3);
        if (dest.order() == ByteOrder.nativeOrder())
            put(src, UNSAFE.getLong(dest, ADDRESS) + ((long) offset << 2));
        else
            MemUtil.put(src, offset, dest);
    }

    public void put(Vector3f src, int offset, ByteBuffer dest) {
        if (Options.DEBUG) checkPut(offset, dest.isDirect(), dest.capacity(), 3 << 2);
        if (dest.order() == ByteOrder.nativeOrder())
            put(src, UNSAFE.getLong(dest, ADDRESS) + offset);
        else
            MemUtil.put(src, offset, dest);
    }

    public void put(Vector3d src, int offset, DoubleBuffer dest) {
        if (Options.DEBUG) checkPut(offset, dest.isDirect(), dest.capacity(), 3);
        if (dest.order() == ByteOrder.nativeOrder())
            put(src, UNSAFE.getLong(dest, ADDRESS) + ((long) offset << 3));
        else
            MemUtil.put(src, offset, dest);
    }

    public void put(Vector3d src, int offset, FloatBuffer dest) {
        if (Options.DEBUG) checkPut(offset, dest.isDirect(), dest.capacity(), 3);
        if (dest.order() == ByteOrder.nativeOrder())
            putf(src, UNSAFE.getLong(dest, ADDRESS) + ((long) offset << 2));
        else
            MemUtil.put(src, offset, dest);
    }

    public void put(Vector3d src, int offset, ByteBuffer dest) {
        if (Options.DEBUG) checkPut(offset, dest.isDirect(), dest.capacity(), 3 << 3);
        if (dest.order() == ByteOrder.nativeOrder())
            put(src, UNSAFE.getLong(dest, ADDRESS) + offset);
        else
            MemUtil.put(src, offset, dest);
    }

    public void putf(Vector3d src, int offset, ByteBuffer dest) {
        if (Options.DEBUG) checkPut(offset, dest.isDirect(), dest.capacity(), 3 << 2);
        if (dest.order() == ByteOrder.nativeOrder())
            putf(src, UNSAFE.getLong(dest, ADDRESS) + offset);
        else
            MemUtil.putf(src, offset, dest);
    }

    public void put(Vector3i src, int offset, IntBuffer dest) {
        if (Options.DEBUG) checkPut(offset, dest.isDirect(), dest.capacity(), 3);
        if (dest.order() == ByteOrder.nativeOrder())
            put(src, UNSAFE.getLong(dest, ADDRESS) + ((long) offset << 2));
        else
            MemUtil.put(src, offset, dest);
    }

    public void put(Vector3i src, int offset, ByteBuffer dest) {
        if (Options.DEBUG) checkPut(offset, dest.isDirect(), dest.capacity(), 3 << 2);
        if (dest.order() == ByteOrder.nativeOrder())
            put(src, UNSAFE.getLong(dest, ADDRESS) + offset);
        else
            MemUtil.put(src, offset, dest);
    }

    public void put(Vector2f src, int offset, FloatBuffer dest) {
        if (Options.DEBUG) checkPut(offset, dest.isDirect(), dest.capacity(), 2);
        if (dest.order() == ByteOrder.nativeOrder())
            put(src, UNSAFE.getLong(dest, ADDRESS) + ((long) offset << 2));
        else
            MemUtil.put(src, offset, dest);
    }

    public void put(Vector2f src, int offset, ByteBuffer dest) {
        if (Options.DEBUG) checkPut(offset, dest.isDirect(), dest.capacity(), 2 << 2);
        if (dest.order() == ByteOrder.nativeOrder())
            put(src, UNSAFE.getLong(dest, ADDRESS) + offset);
        else
            MemUtil.put(src, offset, dest);
    }

    public void put(Vector2d src, int offset, DoubleBuffer dest) {
        if (Options.DEBUG) checkPut(offset, dest.isDirect(), dest.capacity(), 2);
        if (dest.order() == ByteOrder.nativeOrder())
            put(src, UNSAFE.getLong(dest, ADDRESS) + ((long) offset << 3));
        else
            MemUtil.put(src, offset, dest);
    }

    public void put(Vector2d src, int offset, ByteBuffer dest) {
        if (Options.DEBUG) checkPut(offset, dest.isDirect(), dest.capacity(), 2 << 3);
        if (dest.order() == ByteOrder.nativeOrder())
            put(src, UNSAFE.getLong(dest, ADDRESS) + offset);
        else
            MemUtil.put(src, offset, dest);
    }

    public void put(Vector2i src, int offset, IntBuffer dest) {
        if (Options.DEBUG) checkPut(offset, dest.isDirect(), dest.capacity(), 2);
        if (dest.order() == ByteOrder.nativeOrder())
            put(src, UNSAFE.getLong(dest, ADDRESS) + ((long) offset << 2));
        else
            MemUtil.put(src, offset, dest);
    }

    public void put(Vector2i src, int offset, ByteBuffer dest) {
        if (Options.DEBUG) checkPut(offset, dest.isDirect(), dest.capacity(), 2 << 2);
        if (dest.order() == ByteOrder.nativeOrder())
            put(src, UNSAFE.getLong(dest, ADDRESS) + offset);
        else
            MemUtil.put(src, offset, dest);
    }

    public void put(Vector2L src, int offset, LongBuffer dest) {
        if (Options.DEBUG) checkPut(offset, dest.isDirect(), dest.capacity(), 2);
        if (dest.order() == ByteOrder.nativeOrder())
            put(src, UNSAFE.getLong(dest, ADDRESS) + ((long) offset << 3));
        else
            MemUtil.put(src, offset, dest);
    }

    public void put(Vector2L src, int offset, ByteBuffer dest) {
        if (Options.DEBUG) checkPut(offset, dest.isDirect(), dest.capacity(), 2 << 3);
        if (dest.order() == ByteOrder.nativeOrder())
            put(src, UNSAFE.getLong(dest, ADDRESS) + offset);
        else
            MemUtil.put(src, offset, dest);
    }

    public void get(Matrix4f m, int offset, FloatBuffer src) {
        if (Options.DEBUG) checkGet(offset, src.isDirect(), src.capacity(), 16);
        if (src.order() == ByteOrder.nativeOrder())
            get(m, UNSAFE.getLong(src, ADDRESS) + ((long) offset << 2));
        else
            MemUtil.get(m, offset, src);
    }

    public void get(Matrix4f m, int offset, ByteBuffer src) {
        if (Options.DEBUG) checkGet(offset, src.isDirect(), src.capacity(), 16 << 2);
        if (src.order() == ByteOrder.nativeOrder())
            get(m, UNSAFE.getLong(src, ADDRESS) + offset);
        else
            MemUtil.get(m, offset, src);
    }

    public float get(Matrix4f m, int column, int row) {
        return UNSAFE.getFloat(m, Matrix4f_m00 + ((long) column << 4) + ((long) row << 2));
    }

    public Matrix4f set(Matrix4f m, int column, int row, float value) {
        UNSAFE.putFloat(m, Matrix4f_m00 + ((long) column << 4) + ((long) row << 2), value);
        return m;
    }

    public double get(Matrix4d m, int column, int row) {
        return UNSAFE.getDouble(m, Matrix4d_m00 + ((long) column << 5) + ((long) row << 3));
    }

    public Matrix4d set(Matrix4d m, int column, int row, double value) {
        UNSAFE.putDouble(m, Matrix4d_m00 + ((long) column << 5) + ((long) row << 3), value);
        return m;
    }

    public float get(Matrix3f m, int column, int row) {
        return UNSAFE.getFloat(m, Matrix3f_m00 + ((long) column * (3<<2)) + ((long) row << 2));
    }

    public Matrix3f set(Matrix3f m, int column, int row, float value) {
        UNSAFE.putFloat(m, Matrix3f_m00 + ((long) column * (3<<2)) + ((long) row << 2), value);
        return m;
    }

    public double get(Matrix3d m, int column, int row) {
        return UNSAFE.getDouble(m, Matrix3d_m00 + ((long) column * (3<<3)) + ((long) row << 3));
    }

    public Matrix3d set(Matrix3d m, int column, int row, double value) {
        UNSAFE.putDouble(m, Matrix3d_m00 + ((long) column * (3<<3)) + ((long) row << 3), value);
        return m;
    }

    public void get(Matrix4x3f m, int offset, FloatBuffer src) {
        if (Options.DEBUG) checkGet(offset, src.isDirect(), src.capacity(), 12);
        if (src.order() == ByteOrder.nativeOrder())
            get(m, UNSAFE.getLong(src, ADDRESS) + ((long) offset << 2));
        else
            MemUtil.get(m, offset, src);
    }

    public void get(Matrix4x3f m, int offset, ByteBuffer src) {
        if (Options.DEBUG) checkGet(offset, src.isDirect(), src.capacity(), 12 << 2);
        if (src.order() == ByteOrder.nativeOrder())
            get(m, UNSAFE.getLong(src, ADDRESS) + offset);
        else
            MemUtil.get(m, offset, src);
    }

    public void get(Matrix4d m, int offset, DoubleBuffer src) {
        if (Options.DEBUG) checkGet(offset, src.isDirect(), src.capacity(), 16);
        if (src.order() == ByteOrder.nativeOrder())
            get(m, UNSAFE.getLong(src, ADDRESS) + ((long) offset << 3));
        else
            MemUtil.get(m, offset, src);
    }

    public void get(Matrix4d m, int offset, ByteBuffer src) {
        if (Options.DEBUG) checkGet(offset, src.isDirect(), src.capacity(), 16 << 3);
        if (src.order() == ByteOrder.nativeOrder())
            get(m, UNSAFE.getLong(src, ADDRESS) + offset);
        else
            MemUtil.get(m, offset, src);
    }

    public void get(Matrix4x3d m, int offset, DoubleBuffer src) {
        if (Options.DEBUG) checkGet(offset, src.isDirect(), src.capacity(), 12);
        if (src.order() == ByteOrder.nativeOrder())
            get(m, UNSAFE.getLong(src, ADDRESS) + ((long) offset << 3));
        else
            MemUtil.get(m, offset, src);
    }

    public void get(Matrix4x3d m, int offset, ByteBuffer src) {
        if (Options.DEBUG) checkGet(offset, src.isDirect(), src.capacity(), 12 << 3);
        if (src.order() == ByteOrder.nativeOrder())
            get(m, UNSAFE.getLong(src, ADDRESS) + offset);
        else
            MemUtil.get(m, offset, src);
    }

    public void getf(Matrix4d m, int offset, FloatBuffer src) {
        if (Options.DEBUG) checkGet(offset, src.isDirect(), src.capacity(), 16);
        if (src.order() == ByteOrder.nativeOrder())
            getf(m, UNSAFE.getLong(src, ADDRESS) + ((long) offset << 2));
        else
            MemUtil.getf(m, offset, src);
    }

    public void getf(Matrix4d m, int offset, ByteBuffer src) {
        if (Options.DEBUG) checkGet(offset, src.isDirect(), src.capacity(), 16 << 2);
        if (src.order() == ByteOrder.nativeOrder())
            getf(m, UNSAFE.getLong(src, ADDRESS) + offset);
        else
            MemUtil.getf(m, offset, src);
    }

    public void getf(Matrix4x3d m, int offset, FloatBuffer src) {
        if (Options.DEBUG) checkGet(offset, src.isDirect(), src.capacity(), 12);
        if (src.order() == ByteOrder.nativeOrder())
            getf(m, UNSAFE.getLong(src, ADDRESS) + ((long) offset << 2));
        else
            MemUtil.getf(m, offset, src);
    }

    private static void checkGet(int offset, boolean direct, int capacity, int i) {
        if (!direct)
            throwNoDirectBufferException();
        if (capacity - offset < i)
            throw new BufferUnderflowException();
    }

    public void getf(Matrix4x3d m, int offset, ByteBuffer src) {
        if (Options.DEBUG) checkGet(offset, src.isDirect(), src.capacity(), 12 << 2);
        if (src.order() == ByteOrder.nativeOrder())
            getf(m, UNSAFE.getLong(src, ADDRESS) + offset);
        else
            MemUtil.getf(m, offset, src);
    }

    public void get(Matrix3f m, int offset, FloatBuffer src) {
        if (Options.DEBUG) checkGet(offset, src.isDirect(), src.capacity(), 9);
        if (src.order() == ByteOrder.nativeOrder())
            get(m, UNSAFE.getLong(src, ADDRESS) + ((long) offset << 2));
        else
            MemUtil.get(m, offset, src);
    }

    public void get(Matrix3f m, int offset, ByteBuffer src) {
        if (Options.DEBUG) checkGet(offset, src.isDirect(), src.capacity(), 9 << 2);
        if (src.order() == ByteOrder.nativeOrder())
            get(m, UNSAFE.getLong(src, ADDRESS) + offset);
        else
            MemUtil.get(m, offset, src);
    }

    public void get(Matrix3d m, int offset, DoubleBuffer src) {
        if (Options.DEBUG) checkGet(offset, src.isDirect(), src.capacity(), 9);
        if (src.order() == ByteOrder.nativeOrder())
            get(m, UNSAFE.getLong(src, ADDRESS) + ((long) offset << 3));
        else
            MemUtil.get(m, offset, src);
    }

    public void get(Matrix3d m, int offset, ByteBuffer src) {
        if (Options.DEBUG) checkGet(offset, src.isDirect(), src.capacity(), 9 << 3);
        if (src.order() == ByteOrder.nativeOrder())
            get(m, UNSAFE.getLong(src, ADDRESS) + offset);
        else
            MemUtil.get(m, offset, src);
    }

    public void get(Matrix3x2f m, int offset, FloatBuffer src) {
        if (Options.DEBUG) checkGet(offset, src.isDirect(), src.capacity(), 6);
        if (src.order() == ByteOrder.nativeOrder())
            get(m, UNSAFE.getLong(src, ADDRESS) + ((long) offset << 2));
        else
            MemUtil.get(m, offset, src);
    }

    public void get(Matrix3x2f m, int offset, ByteBuffer src) {
        if (Options.DEBUG) checkGet(offset, src.isDirect(), src.capacity(), 6 << 2);
        if (src.order() == ByteOrder.nativeOrder())
            get(m, UNSAFE.getLong(src, ADDRESS) + offset);
        else
            MemUtil.get(m, offset, src);
    }

    public void get(Matrix3x2d m, int offset, DoubleBuffer src) {
        if (Options.DEBUG) checkGet(offset, src.isDirect(), src.capacity(), 6);
        if (src.order() == ByteOrder.nativeOrder())
            get(m, UNSAFE.getLong(src, ADDRESS) + ((long) offset << 3));
        else
            MemUtil.get(m, offset, src);
    }

    public void get(Matrix3x2d m, int offset, ByteBuffer src) {
        if (Options.DEBUG) checkGet(offset, src.isDirect(), src.capacity(), 6 << 3);
        if (src.order() == ByteOrder.nativeOrder())
            get(m, UNSAFE.getLong(src, ADDRESS) + offset);
        else
            MemUtil.get(m, offset, src);
    }

    public void getf(Matrix3d m, int offset, FloatBuffer src) {
        if (Options.DEBUG) checkGet(offset, src.isDirect(), src.capacity(), 9);
        if (src.order() == ByteOrder.nativeOrder())
            getf(m, UNSAFE.getLong(src, ADDRESS) + ((long) offset << 2));
        else
            MemUtil.getf(m, offset, src);
    }

    public void getf(Matrix3d m, int offset, ByteBuffer src) {
        if (Options.DEBUG) checkGet(offset, src.isDirect(), src.capacity(), 9 << 2);
        if (src.order() == ByteOrder.nativeOrder())
            getf(m, UNSAFE.getLong(src, ADDRESS) + offset);
        else
            MemUtil.getf(m, offset, src);
    }

    public void get(Matrix2f m, int offset, FloatBuffer src) {
        if (Options.DEBUG) checkGet(offset, src.isDirect(), src.capacity(), 4);
        if (src.order() == ByteOrder.nativeOrder())
            get(m, UNSAFE.getLong(src, ADDRESS) + ((long) offset << 2));
        else
            MemUtil.get(m, offset, src);
    }

    public void get(Matrix2f m, int offset, ByteBuffer src) {
        if (Options.DEBUG) checkGet(offset, src.isDirect(), src.capacity(), 4 << 2);
        if (src.order() == ByteOrder.nativeOrder())
            get(m, UNSAFE.getLong(src, ADDRESS) + offset);
        else
            MemUtil.get(m, offset, src);
    }

    public void get(Matrix2d m, int offset, DoubleBuffer src) {
        if (Options.DEBUG) checkGet(offset, src.isDirect(), src.capacity(), 4);
        if (src.order() == ByteOrder.nativeOrder())
            get(m, UNSAFE.getLong(src, ADDRESS) + ((long) offset << 3));
        else
            MemUtil.get(m, offset, src);
    }

    public void get(Matrix2d m, int offset, ByteBuffer src) {
        if (Options.DEBUG) checkGet(offset, src.isDirect(), src.capacity(), 4 << 3);
        if (src.order() == ByteOrder.nativeOrder())
            get(m, UNSAFE.getLong(src, ADDRESS) + offset);
        else
            MemUtil.get(m, offset, src);
    }

    public void getf(Matrix2d m, int offset, FloatBuffer src) {
        if (Options.DEBUG) checkGet(offset, src.isDirect(), src.capacity(), 4);
        if (src.order() == ByteOrder.nativeOrder())
            getf(m, UNSAFE.getLong(src, ADDRESS) + ((long) offset << 2));
        else
            MemUtil.getf(m, offset, src);
    }

    public void getf(Matrix2d m, int offset, ByteBuffer src) {
        if (Options.DEBUG) checkGet(offset, src.isDirect(), src.capacity(), 4 << 2);
        if (src.order() == ByteOrder.nativeOrder())
            getf(m, UNSAFE.getLong(src, ADDRESS) + offset);
        else
            MemUtil.getf(m, offset, src);
    }

    public void get(Vector4d dst, int offset, DoubleBuffer src) {
        if (Options.DEBUG) checkGet(offset, src.isDirect(), src.capacity(), 4);
        if (src.order() == ByteOrder.nativeOrder())
            get(dst, UNSAFE.getLong(src, ADDRESS) + ((long) offset << 3));
        else
            MemUtil.get(dst, offset, src);
    }

    public void get(Vector4d dst, int offset, ByteBuffer src) {
        if (Options.DEBUG) checkGet(offset, src.isDirect(), src.capacity(), 4 << 3);
        if (src.order() == ByteOrder.nativeOrder())
            get(dst, UNSAFE.getLong(src, ADDRESS) + offset);
        else
            MemUtil.get(dst, offset, src);
    }

    public void get(Vector4f dst, int offset, FloatBuffer src) {
        if (Options.DEBUG) checkGet(offset, src.isDirect(), src.capacity(), 4);
        if (src.order() == ByteOrder.nativeOrder())
            get(dst, UNSAFE.getLong(src, ADDRESS) + ((long) offset << 2));
        else
            MemUtil.get(dst, offset, src);
    }

    public void get(Vector4f dst, int offset, ByteBuffer src) {
        if (Options.DEBUG) checkGet(offset, src.isDirect(), src.capacity(), 4 << 2);
        if (src.order() == ByteOrder.nativeOrder())
            get(dst, UNSAFE.getLong(src, ADDRESS) + offset);
        else
            MemUtil.get(dst, offset, src);
    }

    public void get(Vector4i dst, int offset, IntBuffer src) {
        if (Options.DEBUG) checkGet(offset, src.isDirect(), src.capacity(), 4);
        if (src.order() == ByteOrder.nativeOrder())
            get(dst, UNSAFE.getLong(src, ADDRESS) + ((long) offset << 2));
        else
            MemUtil.get(dst, offset, src);
    }

    public void get(Vector4i dst, int offset, ByteBuffer src) {
        if (Options.DEBUG) checkGet(offset, src.isDirect(), src.capacity(), 4 << 2);
        if (src.order() == ByteOrder.nativeOrder())
            get(dst, UNSAFE.getLong(src, ADDRESS) + offset);
        else
            MemUtil.get(dst, offset, src);
    }

    public void get(Vector3f dst, int offset, FloatBuffer src) {
        if (Options.DEBUG) checkGet(offset, src.isDirect(), src.capacity(), 3);
        if (src.order() == ByteOrder.nativeOrder())
            get(dst, UNSAFE.getLong(src, ADDRESS) + ((long) offset << 2));
        else
            MemUtil.get(dst, offset, src);
    }

    public void get(Vector3f dst, int offset, ByteBuffer src) {
        if (Options.DEBUG) checkGet(offset, src.isDirect(), src.capacity(), 3 << 2);
        if (src.order() == ByteOrder.nativeOrder())
            get(dst, UNSAFE.getLong(src, ADDRESS) + offset);
        else
            MemUtil.get(dst, offset, src);
    }

    public void get(Vector3d dst, int offset, DoubleBuffer src) {
        if (Options.DEBUG) checkGet(offset, src.isDirect(), src.capacity(), 3);
        if (src.order() == ByteOrder.nativeOrder())
            get(dst, UNSAFE.getLong(src, ADDRESS) + ((long) offset << 3));
        else
            MemUtil.get(dst, offset, src);
    }

    public void get(Vector3d dst, int offset, ByteBuffer src) {
        if (Options.DEBUG) checkGet(offset, src.isDirect(), src.capacity(), 3 << 3);
        if (src.order() == ByteOrder.nativeOrder())
            get(dst, UNSAFE.getLong(src, ADDRESS) + offset);
        else
            MemUtil.get(dst, offset, src);
    }

    public void get(Vector3i dst, int offset, IntBuffer src) {
        if (Options.DEBUG) checkGet(offset, src.isDirect(), src.capacity(), 3);
        if (src.order() == ByteOrder.nativeOrder())
            get(dst, UNSAFE.getLong(src, ADDRESS) + ((long) offset << 2));
        else
            MemUtil.get(dst, offset, src);
    }

    public void get(Vector3i dst, int offset, ByteBuffer src) {
        if (Options.DEBUG) checkGet(offset, src.isDirect(), src.capacity(), 3 << 2);
        if (src.order() == ByteOrder.nativeOrder())
            get(dst, UNSAFE.getLong(src, ADDRESS) + offset);
        else
            MemUtil.get(dst, offset, src);
    }

    public void get(Vector2f dst, int offset, FloatBuffer src) {
        if (Options.DEBUG) checkGet(offset, src.isDirect(), src.capacity(), 2);
        if (src.order() == ByteOrder.nativeOrder())
            get(dst, UNSAFE.getLong(src, ADDRESS) + ((long) offset << 2));
        else
            MemUtil.get(dst, offset, src);
    }

    public void get(Vector2f dst, int offset, ByteBuffer src) {
        if (Options.DEBUG) checkGet(offset, src.isDirect(), src.capacity(), 2 << 2);
        if (src.order() == ByteOrder.nativeOrder())
            get(dst, UNSAFE.getLong(src, ADDRESS) + offset);
        else
            MemUtil.get(dst, offset, src);
    }

    public void get(Vector2d dst, int offset, DoubleBuffer src) {
        if (Options.DEBUG) checkGet(offset, src.isDirect(), src.capacity(), 2);
        if (src.order() == ByteOrder.nativeOrder())
            get(dst, UNSAFE.getLong(src, ADDRESS) + ((long) offset << 3));
        else
            MemUtil.get(dst, offset, src);
    }

    public void get(Vector2d dst, int offset, ByteBuffer src) {
        if (Options.DEBUG) checkGet(offset, src.isDirect(), src.capacity(), 2 << 3);
        if (src.order() == ByteOrder.nativeOrder())
            get(dst, UNSAFE.getLong(src, ADDRESS) + offset);
        else
            MemUtil.get(dst, offset, src);
    }

    public void get(Vector2i dst, int offset, IntBuffer src) {
        if (Options.DEBUG) checkGet(offset, src.isDirect(), src.capacity(), 2);
        if (src.order() == ByteOrder.nativeOrder())
            get(dst, UNSAFE.getLong(src, ADDRESS) + ((long) offset << 2));
        else
            MemUtil.get(dst, offset, src);
    }

    public void get(Vector2i dst, int offset, ByteBuffer src) {
        if (Options.DEBUG) checkGet(offset, src.isDirect(), src.capacity(), 2 << 2);
        if (src.order() == ByteOrder.nativeOrder())
            get(dst, UNSAFE.getLong(src, ADDRESS) + offset);
        else
            MemUtil.get(dst, offset, src);
    }
//#endif
}
//#endif
