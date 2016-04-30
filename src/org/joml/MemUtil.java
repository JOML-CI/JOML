/*
 * (C) Copyright 2015-2016 Kai Burjack

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

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;

/**
 * Helper class to do efficient memory copies.
 * 
 * @author The LWJGL authors
 * @author Kai Burjack
 */
abstract class MemUtil {
    static final MemUtil INSTANCE = createInstance();

    private static final MemUtil createInstance() {
        MemUtil accessor;
        try {
            accessor = new MemUtilUnsafe();
        } catch (UnsupportedOperationException e) {
            accessor = new MemUtilNIO();
        }
        return accessor;
    }

    abstract void put(Matrix4f m, int offset, FloatBuffer dest);
    abstract void put(Matrix4f m, int offset, ByteBuffer dest);
    abstract void putTransposed(Matrix4f m, int offset, FloatBuffer dest);
    abstract void putTransposed(Matrix4f m, int offset, ByteBuffer dest);
    abstract void put(Matrix4d m, int offset, DoubleBuffer dest);
    abstract void put(Matrix4d m, int offset, ByteBuffer dest);
    abstract void putf(Matrix4d m, int offset, FloatBuffer dest);
    abstract void putf(Matrix4d m, int offset, ByteBuffer dest);
    abstract void putTransposed(Matrix4d m, int offset, DoubleBuffer dest);
    abstract void putTransposed(Matrix4d m, int offset, ByteBuffer dest);
    abstract void putfTransposed(Matrix4d m, int offset, FloatBuffer dest);
    abstract void putfTransposed(Matrix4d m, int offset, ByteBuffer dest);
    abstract void put(Matrix3f m, int offset, FloatBuffer dest);
    abstract void put(Matrix3f m, int offset, ByteBuffer dest);
    abstract void put(Matrix3d m, int offset, DoubleBuffer dest);
    abstract void put(Matrix3d m, int offset, ByteBuffer dest);
    abstract void putf(Matrix3d m, int offset, FloatBuffer dest);
    abstract void putf(Matrix3d m, int offset, ByteBuffer dest);
    abstract void get(Matrix4f m, int offset, FloatBuffer src);
    abstract void get(Matrix4f m, int offset, ByteBuffer src);
    abstract void get(Matrix4d m, int offset, DoubleBuffer src);
    abstract void get(Matrix4d m, int offset, ByteBuffer src);
    abstract void getf(Matrix4d m, int offset, FloatBuffer src);
    abstract void getf(Matrix4d m, int offset, ByteBuffer src);
    abstract void get(Matrix3f m, int offset, FloatBuffer src);
    abstract void get(Matrix3f m, int offset, ByteBuffer src);
    abstract void get(Matrix3d m, int offset, DoubleBuffer src);
    abstract void get(Matrix3d m, int offset, ByteBuffer src);
    abstract void getf(Matrix3d m, int offset, FloatBuffer src);
    abstract void getf(Matrix3d m, int offset, ByteBuffer src);

    static final class MemUtilNIO extends MemUtil {
        final void put(Matrix4f m, int offset, FloatBuffer dest) {
            dest.put(offset,    m.m00());
            dest.put(offset+1,  m.m01());
            dest.put(offset+2,  m.m02());
            dest.put(offset+3,  m.m03());
            dest.put(offset+4,  m.m10());
            dest.put(offset+5,  m.m11());
            dest.put(offset+6,  m.m12());
            dest.put(offset+7,  m.m13());
            dest.put(offset+8,  m.m20());
            dest.put(offset+9,  m.m21());
            dest.put(offset+10, m.m22());
            dest.put(offset+11, m.m23());
            dest.put(offset+12, m.m30());
            dest.put(offset+13, m.m31());
            dest.put(offset+14, m.m32());
            dest.put(offset+15, m.m33());
        }

        final void put(Matrix4f m, int offset, ByteBuffer dest) {
            dest.putFloat(offset,    m.m00());
            dest.putFloat(offset+4,  m.m01());
            dest.putFloat(offset+8,  m.m02());
            dest.putFloat(offset+12, m.m03());
            dest.putFloat(offset+16, m.m10());
            dest.putFloat(offset+20, m.m11());
            dest.putFloat(offset+24, m.m12());
            dest.putFloat(offset+28, m.m13());
            dest.putFloat(offset+32, m.m20());
            dest.putFloat(offset+36, m.m21());
            dest.putFloat(offset+40, m.m22());
            dest.putFloat(offset+44, m.m23());
            dest.putFloat(offset+48, m.m30());
            dest.putFloat(offset+52, m.m31());
            dest.putFloat(offset+56, m.m32());
            dest.putFloat(offset+60, m.m33());
        }

        final void putTransposed(Matrix4f m, int offset, FloatBuffer dest) {
            dest.put(offset,    m.m00());
            dest.put(offset+1,  m.m10());
            dest.put(offset+2,  m.m20());
            dest.put(offset+3,  m.m30());
            dest.put(offset+4,  m.m01());
            dest.put(offset+5,  m.m11());
            dest.put(offset+6,  m.m21());
            dest.put(offset+7,  m.m31());
            dest.put(offset+8,  m.m02());
            dest.put(offset+9,  m.m12());
            dest.put(offset+10, m.m22());
            dest.put(offset+11, m.m32());
            dest.put(offset+12, m.m03());
            dest.put(offset+13, m.m13());
            dest.put(offset+14, m.m23());
            dest.put(offset+15, m.m33());
        }

        final void putTransposed(Matrix4f m, int offset, ByteBuffer dest) {
            dest.putFloat(offset,    m.m00());
            dest.putFloat(offset+4,  m.m10());
            dest.putFloat(offset+8,  m.m20());
            dest.putFloat(offset+12, m.m30());
            dest.putFloat(offset+16, m.m01());
            dest.putFloat(offset+20, m.m11());
            dest.putFloat(offset+24, m.m21());
            dest.putFloat(offset+28, m.m31());
            dest.putFloat(offset+32, m.m02());
            dest.putFloat(offset+36, m.m12());
            dest.putFloat(offset+40, m.m22());
            dest.putFloat(offset+44, m.m32());
            dest.putFloat(offset+48, m.m03());
            dest.putFloat(offset+52, m.m13());
            dest.putFloat(offset+56, m.m23());
            dest.putFloat(offset+60, m.m33());
        }

        final void put(Matrix4d m, int offset, DoubleBuffer dest) {
            dest.put(offset,    m.ms[Matrix4d.M00]);
            dest.put(offset+1,  m.ms[Matrix4d.M01]);
            dest.put(offset+2,  m.ms[Matrix4d.M02]);
            dest.put(offset+3,  m.ms[Matrix4d.M03]);
            dest.put(offset+4,  m.ms[Matrix4d.M10]);
            dest.put(offset+5,  m.ms[Matrix4d.M11]);
            dest.put(offset+6,  m.ms[Matrix4d.M12]);
            dest.put(offset+7,  m.ms[Matrix4d.M13]);
            dest.put(offset+8,  m.ms[Matrix4d.M20]);
            dest.put(offset+9,  m.ms[Matrix4d.M21]);
            dest.put(offset+10, m.ms[Matrix4d.M22]);
            dest.put(offset+11, m.ms[Matrix4d.M23]);
            dest.put(offset+12, m.ms[Matrix4d.M30]);
            dest.put(offset+13, m.ms[Matrix4d.M31]);
            dest.put(offset+14, m.ms[Matrix4d.M32]);
            dest.put(offset+15, m.ms[Matrix4d.M33]);
        }

        final void put(Matrix4d m, int offset, ByteBuffer dest) {
            dest.putDouble(offset,    m.ms[Matrix4d.M00]);
            dest.putDouble(offset+4,  m.ms[Matrix4d.M01]);
            dest.putDouble(offset+8,  m.ms[Matrix4d.M02]);
            dest.putDouble(offset+12, m.ms[Matrix4d.M03]);
            dest.putDouble(offset+16, m.ms[Matrix4d.M10]);
            dest.putDouble(offset+20, m.ms[Matrix4d.M11]);
            dest.putDouble(offset+24, m.ms[Matrix4d.M12]);
            dest.putDouble(offset+28, m.ms[Matrix4d.M13]);
            dest.putDouble(offset+32, m.ms[Matrix4d.M20]);
            dest.putDouble(offset+36, m.ms[Matrix4d.M21]);
            dest.putDouble(offset+40, m.ms[Matrix4d.M22]);
            dest.putDouble(offset+44, m.ms[Matrix4d.M23]);
            dest.putDouble(offset+48, m.ms[Matrix4d.M30]);
            dest.putDouble(offset+52, m.ms[Matrix4d.M31]);
            dest.putDouble(offset+56, m.ms[Matrix4d.M32]);
            dest.putDouble(offset+60, m.ms[Matrix4d.M33]);
        }

        final void putf(Matrix4d m, int offset, FloatBuffer dest) {
            dest.put(offset,    (float)m.ms[Matrix4d.M00]);
            dest.put(offset+1,  (float)m.ms[Matrix4d.M01]);
            dest.put(offset+2,  (float)m.ms[Matrix4d.M02]);
            dest.put(offset+3,  (float)m.ms[Matrix4d.M03]);
            dest.put(offset+4,  (float)m.ms[Matrix4d.M10]);
            dest.put(offset+5,  (float)m.ms[Matrix4d.M11]);
            dest.put(offset+6,  (float)m.ms[Matrix4d.M12]);
            dest.put(offset+7,  (float)m.ms[Matrix4d.M13]);
            dest.put(offset+8,  (float)m.ms[Matrix4d.M20]);
            dest.put(offset+9,  (float)m.ms[Matrix4d.M21]);
            dest.put(offset+10, (float)m.ms[Matrix4d.M22]);
            dest.put(offset+11, (float)m.ms[Matrix4d.M23]);
            dest.put(offset+12, (float)m.ms[Matrix4d.M30]);
            dest.put(offset+13, (float)m.ms[Matrix4d.M31]);
            dest.put(offset+14, (float)m.ms[Matrix4d.M32]);
            dest.put(offset+15, (float)m.ms[Matrix4d.M33]);
        }

        final void putf(Matrix4d m, int offset, ByteBuffer dest) {
            dest.putFloat(offset,    (float)m.ms[Matrix4d.M00]);
            dest.putFloat(offset+4,  (float)m.ms[Matrix4d.M01]);
            dest.putFloat(offset+8,  (float)m.ms[Matrix4d.M02]);
            dest.putFloat(offset+12, (float)m.ms[Matrix4d.M03]);
            dest.putFloat(offset+16, (float)m.ms[Matrix4d.M10]);
            dest.putFloat(offset+20, (float)m.ms[Matrix4d.M11]);
            dest.putFloat(offset+24, (float)m.ms[Matrix4d.M12]);
            dest.putFloat(offset+28, (float)m.ms[Matrix4d.M13]);
            dest.putFloat(offset+32, (float)m.ms[Matrix4d.M20]);
            dest.putFloat(offset+36, (float)m.ms[Matrix4d.M21]);
            dest.putFloat(offset+40, (float)m.ms[Matrix4d.M22]);
            dest.putFloat(offset+44, (float)m.ms[Matrix4d.M23]);
            dest.putFloat(offset+48, (float)m.ms[Matrix4d.M30]);
            dest.putFloat(offset+52, (float)m.ms[Matrix4d.M31]);
            dest.putFloat(offset+56, (float)m.ms[Matrix4d.M32]);
            dest.putFloat(offset+60, (float)m.ms[Matrix4d.M33]);
        }

        final void putTransposed(Matrix4d m, int offset, DoubleBuffer dest) {
            dest.put(offset,    m.ms[Matrix4d.M00]);
            dest.put(offset+1,  m.ms[Matrix4d.M10]);
            dest.put(offset+2,  m.ms[Matrix4d.M20]);
            dest.put(offset+3,  m.ms[Matrix4d.M30]);
            dest.put(offset+4,  m.ms[Matrix4d.M01]);
            dest.put(offset+5,  m.ms[Matrix4d.M11]);
            dest.put(offset+6,  m.ms[Matrix4d.M21]);
            dest.put(offset+7,  m.ms[Matrix4d.M31]);
            dest.put(offset+8,  m.ms[Matrix4d.M02]);
            dest.put(offset+9,  m.ms[Matrix4d.M12]);
            dest.put(offset+10, m.ms[Matrix4d.M22]);
            dest.put(offset+11, m.ms[Matrix4d.M32]);
            dest.put(offset+12, m.ms[Matrix4d.M03]);
            dest.put(offset+13, m.ms[Matrix4d.M13]);
            dest.put(offset+14, m.ms[Matrix4d.M23]);
            dest.put(offset+15, m.ms[Matrix4d.M33]);
        }

        final void putTransposed(Matrix4d m, int offset, ByteBuffer dest) {
            dest.putDouble(offset,     m.ms[Matrix4d.M00]);
            dest.putDouble(offset+8,   m.ms[Matrix4d.M10]);
            dest.putDouble(offset+16,  m.ms[Matrix4d.M20]);
            dest.putDouble(offset+24,  m.ms[Matrix4d.M30]);
            dest.putDouble(offset+32,  m.ms[Matrix4d.M01]);
            dest.putDouble(offset+40,  m.ms[Matrix4d.M11]);
            dest.putDouble(offset+48,  m.ms[Matrix4d.M21]);
            dest.putDouble(offset+56,  m.ms[Matrix4d.M31]);
            dest.putDouble(offset+64,  m.ms[Matrix4d.M02]);
            dest.putDouble(offset+72,  m.ms[Matrix4d.M12]);
            dest.putDouble(offset+80,  m.ms[Matrix4d.M22]);
            dest.putDouble(offset+88,  m.ms[Matrix4d.M32]);
            dest.putDouble(offset+96,  m.ms[Matrix4d.M03]);
            dest.putDouble(offset+104, m.ms[Matrix4d.M13]);
            dest.putDouble(offset+112, m.ms[Matrix4d.M23]);
            dest.putDouble(offset+120, m.ms[Matrix4d.M33]);
        }

        final void putfTransposed(Matrix4d m, int offset, FloatBuffer dest) {
            dest.put(offset,    (float)m.ms[Matrix4d.M00]);
            dest.put(offset+1,  (float)m.ms[Matrix4d.M10]);
            dest.put(offset+2,  (float)m.ms[Matrix4d.M20]);
            dest.put(offset+3,  (float)m.ms[Matrix4d.M30]);
            dest.put(offset+4,  (float)m.ms[Matrix4d.M01]);
            dest.put(offset+5,  (float)m.ms[Matrix4d.M11]);
            dest.put(offset+6,  (float)m.ms[Matrix4d.M21]);
            dest.put(offset+7,  (float)m.ms[Matrix4d.M31]);
            dest.put(offset+8,  (float)m.ms[Matrix4d.M02]);
            dest.put(offset+9,  (float)m.ms[Matrix4d.M12]);
            dest.put(offset+10, (float)m.ms[Matrix4d.M22]);
            dest.put(offset+11, (float)m.ms[Matrix4d.M32]);
            dest.put(offset+12, (float)m.ms[Matrix4d.M03]);
            dest.put(offset+13, (float)m.ms[Matrix4d.M13]);
            dest.put(offset+14, (float)m.ms[Matrix4d.M23]);
            dest.put(offset+15, (float)m.ms[Matrix4d.M33]);
        }

        final void putfTransposed(Matrix4d m, int offset, ByteBuffer dest) {
            dest.putFloat(offset,    (float)m.ms[Matrix4d.M00]);
            dest.putFloat(offset+4,  (float)m.ms[Matrix4d.M10]);
            dest.putFloat(offset+8,  (float)m.ms[Matrix4d.M20]);
            dest.putFloat(offset+12, (float)m.ms[Matrix4d.M30]);
            dest.putFloat(offset+16, (float)m.ms[Matrix4d.M01]);
            dest.putFloat(offset+20, (float)m.ms[Matrix4d.M11]);
            dest.putFloat(offset+24, (float)m.ms[Matrix4d.M21]);
            dest.putFloat(offset+28, (float)m.ms[Matrix4d.M31]);
            dest.putFloat(offset+32, (float)m.ms[Matrix4d.M02]);
            dest.putFloat(offset+36, (float)m.ms[Matrix4d.M12]);
            dest.putFloat(offset+40, (float)m.ms[Matrix4d.M22]);
            dest.putFloat(offset+44, (float)m.ms[Matrix4d.M32]);
            dest.putFloat(offset+48, (float)m.ms[Matrix4d.M03]);
            dest.putFloat(offset+52, (float)m.ms[Matrix4d.M13]);
            dest.putFloat(offset+56, (float)m.ms[Matrix4d.M23]);
            dest.putFloat(offset+60, (float)m.ms[Matrix4d.M33]);
        }

        final void put(Matrix3f m, int offset, FloatBuffer dest) {
            dest.put(offset,   m.ms[Matrix3f.M00]);
            dest.put(offset+1, m.ms[Matrix3f.M01]);
            dest.put(offset+2, m.ms[Matrix3f.M02]);
            dest.put(offset+3, m.ms[Matrix3f.M10]);
            dest.put(offset+4, m.ms[Matrix3f.M11]);
            dest.put(offset+5, m.ms[Matrix3f.M12]);
            dest.put(offset+6, m.ms[Matrix3f.M20]);
            dest.put(offset+7, m.ms[Matrix3f.M21]);
            dest.put(offset+8, m.ms[Matrix3f.M22]);
        }

        final void put(Matrix3f m, int offset, ByteBuffer dest) {
            dest.putFloat(offset,    m.ms[Matrix3f.M00]);
            dest.putFloat(offset+4,  m.ms[Matrix3f.M01]);
            dest.putFloat(offset+8,  m.ms[Matrix3f.M02]);
            dest.putFloat(offset+12, m.ms[Matrix3f.M10]);
            dest.putFloat(offset+16, m.ms[Matrix3f.M11]);
            dest.putFloat(offset+20, m.ms[Matrix3f.M12]);
            dest.putFloat(offset+24, m.ms[Matrix3f.M20]);
            dest.putFloat(offset+28, m.ms[Matrix3f.M21]);
            dest.putFloat(offset+32, m.ms[Matrix3f.M22]);
        }

        final void put(Matrix3d m, int offset, DoubleBuffer dest) {
            dest.put(offset,   m.ms[Matrix3d.M00]);
            dest.put(offset+1, m.ms[Matrix3d.M01]);
            dest.put(offset+2, m.ms[Matrix3d.M02]);
            dest.put(offset+3, m.ms[Matrix3d.M10]);
            dest.put(offset+4, m.ms[Matrix3d.M11]);
            dest.put(offset+5, m.ms[Matrix3d.M12]);
            dest.put(offset+6, m.ms[Matrix3d.M20]);
            dest.put(offset+7, m.ms[Matrix3d.M21]);
            dest.put(offset+8, m.ms[Matrix3d.M22]);
        }

        final void put(Matrix3d m, int offset, ByteBuffer dest) {
            dest.putDouble(offset,    m.ms[Matrix3d.M00]);
            dest.putDouble(offset+8,  m.ms[Matrix3d.M01]);
            dest.putDouble(offset+16, m.ms[Matrix3d.M02]);
            dest.putDouble(offset+24, m.ms[Matrix3d.M10]);
            dest.putDouble(offset+32, m.ms[Matrix3d.M11]);
            dest.putDouble(offset+40, m.ms[Matrix3d.M12]);
            dest.putDouble(offset+48, m.ms[Matrix3d.M20]);
            dest.putDouble(offset+56, m.ms[Matrix3d.M21]);
            dest.putDouble(offset+64, m.ms[Matrix3d.M22]);
        }

        final void putf(Matrix3d m, int offset, FloatBuffer dest) {
            dest.put(offset,   (float)m.ms[Matrix3d.M00]);
            dest.put(offset+1, (float)m.ms[Matrix3d.M01]);
            dest.put(offset+2, (float)m.ms[Matrix3d.M02]);
            dest.put(offset+3, (float)m.ms[Matrix3d.M10]);
            dest.put(offset+4, (float)m.ms[Matrix3d.M11]);
            dest.put(offset+5, (float)m.ms[Matrix3d.M12]);
            dest.put(offset+6, (float)m.ms[Matrix3d.M20]);
            dest.put(offset+7, (float)m.ms[Matrix3d.M21]);
            dest.put(offset+8, (float)m.ms[Matrix3d.M22]);
        }

        final void putf(Matrix3d m, int offset, ByteBuffer dest) {
            dest.putFloat(offset,    (float)m.ms[Matrix3d.M00]);
            dest.putFloat(offset+4,  (float)m.ms[Matrix3d.M01]);
            dest.putFloat(offset+8,  (float)m.ms[Matrix3d.M02]);
            dest.putFloat(offset+12, (float)m.ms[Matrix3d.M10]);
            dest.putFloat(offset+16, (float)m.ms[Matrix3d.M11]);
            dest.putFloat(offset+20, (float)m.ms[Matrix3d.M12]);
            dest.putFloat(offset+24, (float)m.ms[Matrix3d.M20]);
            dest.putFloat(offset+28, (float)m.ms[Matrix3d.M21]);
            dest.putFloat(offset+32, (float)m.ms[Matrix3d.M22]);
        }

        final void get(Matrix4f m, int offset, FloatBuffer src) {
            m.m00(src.get(offset));
            m.m01(src.get(offset+1));
            m.m02(src.get(offset+2));
            m.m03(src.get(offset+3));
            m.m10(src.get(offset+4));
            m.m11(src.get(offset+5));
            m.m12(src.get(offset+6));
            m.m13(src.get(offset+7));
            m.m20(src.get(offset+8));
            m.m21(src.get(offset+9));
            m.m22(src.get(offset+10));
            m.m23(src.get(offset+11));
            m.m30(src.get(offset+12));
            m.m31(src.get(offset+13));
            m.m32(src.get(offset+14));
            m.m33(src.get(offset+15));
        }

        final void get(Matrix4f m, int offset, ByteBuffer src) {
            m.m00(src.getFloat(offset));
            m.m01(src.getFloat(offset+4));
            m.m02(src.getFloat(offset+8));
            m.m03(src.getFloat(offset+12));
            m.m10(src.getFloat(offset+16));
            m.m11(src.getFloat(offset+20));
            m.m12(src.getFloat(offset+24));
            m.m13(src.getFloat(offset+28));
            m.m20(src.getFloat(offset+32));
            m.m21(src.getFloat(offset+36));
            m.m22(src.getFloat(offset+40));
            m.m23(src.getFloat(offset+44));
            m.m30(src.getFloat(offset+48));
            m.m31(src.getFloat(offset+52));
            m.m32(src.getFloat(offset+56));
            m.m33(src.getFloat(offset+60));
        }

        final void get(Matrix4d m, int offset, DoubleBuffer src) {
            m.ms[Matrix4d.M00] = src.get(offset);
            m.ms[Matrix4d.M01] = src.get(offset+1);
            m.ms[Matrix4d.M02] = src.get(offset+2);
            m.ms[Matrix4d.M03] = src.get(offset+3);
            m.ms[Matrix4d.M10] = src.get(offset+4);
            m.ms[Matrix4d.M11] = src.get(offset+5);
            m.ms[Matrix4d.M12] = src.get(offset+6);
            m.ms[Matrix4d.M13] = src.get(offset+7);
            m.ms[Matrix4d.M20] = src.get(offset+8);
            m.ms[Matrix4d.M21] = src.get(offset+9);
            m.ms[Matrix4d.M22] = src.get(offset+10);
            m.ms[Matrix4d.M23] = src.get(offset+11);
            m.ms[Matrix4d.M30] = src.get(offset+12);
            m.ms[Matrix4d.M31] = src.get(offset+13);
            m.ms[Matrix4d.M32] = src.get(offset+14);
            m.ms[Matrix4d.M33] = src.get(offset+15);
        }

        final void get(Matrix4d m, int offset, ByteBuffer src) {
            m.ms[Matrix4d.M00] = src.getDouble(offset);
            m.ms[Matrix4d.M01] = src.getDouble(offset+8);
            m.ms[Matrix4d.M02] = src.getDouble(offset+16);
            m.ms[Matrix4d.M03] = src.getDouble(offset+24);
            m.ms[Matrix4d.M10] = src.getDouble(offset+32);
            m.ms[Matrix4d.M11] = src.getDouble(offset+40);
            m.ms[Matrix4d.M12] = src.getDouble(offset+48);
            m.ms[Matrix4d.M13] = src.getDouble(offset+56);
            m.ms[Matrix4d.M20] = src.getDouble(offset+64);
            m.ms[Matrix4d.M21] = src.getDouble(offset+72);
            m.ms[Matrix4d.M22] = src.getDouble(offset+80);
            m.ms[Matrix4d.M23] = src.getDouble(offset+88);
            m.ms[Matrix4d.M30] = src.getDouble(offset+96);
            m.ms[Matrix4d.M31] = src.getDouble(offset+104);
            m.ms[Matrix4d.M32] = src.getDouble(offset+112);
            m.ms[Matrix4d.M33] = src.getDouble(offset+120);
        }

        final void getf(Matrix4d m, int offset, FloatBuffer src) {
            m.ms[Matrix4d.M00] = src.get(offset);
            m.ms[Matrix4d.M01] = src.get(offset+1);
            m.ms[Matrix4d.M02] = src.get(offset+2);
            m.ms[Matrix4d.M03] = src.get(offset+3);
            m.ms[Matrix4d.M10] = src.get(offset+4);
            m.ms[Matrix4d.M11] = src.get(offset+5);
            m.ms[Matrix4d.M12] = src.get(offset+6);
            m.ms[Matrix4d.M13] = src.get(offset+7);
            m.ms[Matrix4d.M20] = src.get(offset+8);
            m.ms[Matrix4d.M21] = src.get(offset+9);
            m.ms[Matrix4d.M22] = src.get(offset+10);
            m.ms[Matrix4d.M23] = src.get(offset+11);
            m.ms[Matrix4d.M30] = src.get(offset+12);
            m.ms[Matrix4d.M31] = src.get(offset+13);
            m.ms[Matrix4d.M32] = src.get(offset+14);
            m.ms[Matrix4d.M33] = src.get(offset+15);
        }

        final void getf(Matrix4d m, int offset, ByteBuffer src) {
            m.ms[Matrix4d.M00] = src.getFloat(offset);
            m.ms[Matrix4d.M01] = src.getFloat(offset+4);
            m.ms[Matrix4d.M02] = src.getFloat(offset+8);
            m.ms[Matrix4d.M03] = src.getFloat(offset+12);
            m.ms[Matrix4d.M10] = src.getFloat(offset+16);
            m.ms[Matrix4d.M11] = src.getFloat(offset+20);
            m.ms[Matrix4d.M12] = src.getFloat(offset+24);
            m.ms[Matrix4d.M13] = src.getFloat(offset+28);
            m.ms[Matrix4d.M20] = src.getFloat(offset+32);
            m.ms[Matrix4d.M21] = src.getFloat(offset+36);
            m.ms[Matrix4d.M22] = src.getFloat(offset+40);
            m.ms[Matrix4d.M23] = src.getFloat(offset+44);
            m.ms[Matrix4d.M30] = src.getFloat(offset+48);
            m.ms[Matrix4d.M31] = src.getFloat(offset+52);
            m.ms[Matrix4d.M32] = src.getFloat(offset+56);
            m.ms[Matrix4d.M33] = src.getFloat(offset+60);
        }

        final void get(Matrix3f m, int offset, FloatBuffer src) {
            m.ms[Matrix3f.M00] = src.get(offset);
            m.ms[Matrix3f.M01] = src.get(offset+1);
            m.ms[Matrix3f.M02] = src.get(offset+2);
            m.ms[Matrix3f.M10] = src.get(offset+3);
            m.ms[Matrix3f.M11] = src.get(offset+4);
            m.ms[Matrix3f.M12] = src.get(offset+5);
            m.ms[Matrix3f.M20] = src.get(offset+6);
            m.ms[Matrix3f.M21] = src.get(offset+7);
            m.ms[Matrix3f.M22] = src.get(offset+8);
        }

        final void get(Matrix3f m, int offset, ByteBuffer src) {
            m.ms[Matrix3f.M00] = src.getFloat(offset);
            m.ms[Matrix3f.M01] = src.getFloat(offset+4);
            m.ms[Matrix3f.M02] = src.getFloat(offset+8);
            m.ms[Matrix3f.M10] = src.getFloat(offset+12);
            m.ms[Matrix3f.M11] = src.getFloat(offset+16);
            m.ms[Matrix3f.M12] = src.getFloat(offset+20);
            m.ms[Matrix3f.M20] = src.getFloat(offset+24);
            m.ms[Matrix3f.M21] = src.getFloat(offset+28);
            m.ms[Matrix3f.M22] = src.getFloat(offset+32);
        }

        final void get(Matrix3d m, int offset, DoubleBuffer src) {
            m.ms[Matrix3d.M00] = src.get(offset);
            m.ms[Matrix3d.M01] = src.get(offset+1);
            m.ms[Matrix3d.M02] = src.get(offset+2);
            m.ms[Matrix3d.M10] = src.get(offset+3);
            m.ms[Matrix3d.M11] = src.get(offset+4);
            m.ms[Matrix3d.M12] = src.get(offset+5);
            m.ms[Matrix3d.M20] = src.get(offset+6);
            m.ms[Matrix3d.M21] = src.get(offset+7);
            m.ms[Matrix3d.M22] = src.get(offset+8);
        }

        final void get(Matrix3d m, int offset, ByteBuffer src) {
            m.ms[Matrix3d.M00] = src.getDouble(offset);
            m.ms[Matrix3d.M01] = src.getDouble(offset+8);
            m.ms[Matrix3d.M02] = src.getDouble(offset+16);
            m.ms[Matrix3d.M10] = src.getDouble(offset+24);
            m.ms[Matrix3d.M11] = src.getDouble(offset+32);
            m.ms[Matrix3d.M12] = src.getDouble(offset+40);
            m.ms[Matrix3d.M20] = src.getDouble(offset+48);
            m.ms[Matrix3d.M21] = src.getDouble(offset+56);
            m.ms[Matrix3d.M22] = src.getDouble(offset+64);
        }

        final void getf(Matrix3d m, int offset, FloatBuffer src) {
            m.ms[Matrix3d.M00] = src.get(offset);
            m.ms[Matrix3d.M01] = src.get(offset+1);
            m.ms[Matrix3d.M02] = src.get(offset+2);
            m.ms[Matrix3d.M10] = src.get(offset+3);
            m.ms[Matrix3d.M11] = src.get(offset+4);
            m.ms[Matrix3d.M12] = src.get(offset+5);
            m.ms[Matrix3d.M20] = src.get(offset+6);
            m.ms[Matrix3d.M21] = src.get(offset+7);
            m.ms[Matrix3d.M22] = src.get(offset+8);
        }

        final void getf(Matrix3d m, int offset, ByteBuffer src) {
            m.ms[Matrix3d.M00] = src.getFloat(offset);
            m.ms[Matrix3d.M01] = src.getFloat(offset+4);
            m.ms[Matrix3d.M02] = src.getFloat(offset+8);
            m.ms[Matrix3d.M10] = src.getFloat(offset+12);
            m.ms[Matrix3d.M11] = src.getFloat(offset+16);
            m.ms[Matrix3d.M12] = src.getFloat(offset+20);
            m.ms[Matrix3d.M20] = src.getFloat(offset+24);
            m.ms[Matrix3d.M21] = src.getFloat(offset+28);
            m.ms[Matrix3d.M22] = src.getFloat(offset+32);
        }
    }

    static final class MemUtilUnsafe extends MemUtil {
        private final sun.misc.Unsafe UNSAFE;
        private final long ADDRESS;

        MemUtilUnsafe() throws UnsupportedOperationException {
            UNSAFE = getUnsafeInstance();
            try {
                ADDRESS = UNSAFE.objectFieldOffset(getDeclaredField(Buffer.class, "address")); //$NON-NLS-1$
            } catch (NoSuchFieldException e) {
                throw new UnsupportedOperationException();
            }
        }

        private static final java.lang.reflect.Field getDeclaredField(Class root, String fieldName) throws NoSuchFieldException {
            Class type = root;
            do {
                try {
                    java.lang.reflect.Field field = type.getDeclaredField(fieldName);
                    field.setAccessible(true);
                    return field;
                } catch (NoSuchFieldException e) {
                    type = type.getSuperclass();
                } catch (SecurityException e) {
                    type = type.getSuperclass();
                }
            } while (type != null);
            throw new NoSuchFieldException(fieldName + " does not exist in " + root.getName() + " or any of its superclasses."); //$NON-NLS-1$ //$NON-NLS-2$
        }

        private static final sun.misc.Unsafe getUnsafeInstance() throws SecurityException {
            java.lang.reflect.Field[] fields = sun.misc.Unsafe.class.getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                java.lang.reflect.Field field = fields[i];
                if (!field.getType().equals(sun.misc.Unsafe.class))
                    continue;
                int modifiers = field.getModifiers();
                if (!(java.lang.reflect.Modifier.isStatic(modifiers) && java.lang.reflect.Modifier.isFinal(modifiers)))
                    continue;
                field.setAccessible(true);
                try {
                    return (sun.misc.Unsafe) field.get(null);
                } catch (IllegalAccessException e) {
                    /* Ignore */
                }
                break;
            }
            throw new UnsupportedOperationException();
        }

        private final long addressOf(Buffer buffer) {
            return UNSAFE.getLong(buffer, ADDRESS);
        }

        private final void memPutFloat(long ptr, float value) {
            UNSAFE.putFloat(ptr, value);
        }

        private final void memPutDouble(long ptr, double value) {
            UNSAFE.putDouble(ptr, value);
        }

        private final float memGetFloat(long ptr) {
            return UNSAFE.getFloat(ptr);
        }

        private final double memGetDouble(long ptr) {
            return UNSAFE.getDouble(ptr);
        }

        private final void put(Matrix4f m, long destAddr) {
            memPutFloat(destAddr,      m.m00());
            memPutFloat(destAddr + 4,  m.m01());
            memPutFloat(destAddr + 8,  m.m02());
            memPutFloat(destAddr + 12, m.m03());
            memPutFloat(destAddr + 16, m.m10());
            memPutFloat(destAddr + 20, m.m11());
            memPutFloat(destAddr + 24, m.m12());
            memPutFloat(destAddr + 28, m.m13());
            memPutFloat(destAddr + 32, m.m20());
            memPutFloat(destAddr + 36, m.m21());
            memPutFloat(destAddr + 40, m.m22());
            memPutFloat(destAddr + 44, m.m23());
            memPutFloat(destAddr + 48, m.m30());
            memPutFloat(destAddr + 52, m.m31());
            memPutFloat(destAddr + 56, m.m32());
            memPutFloat(destAddr + 60, m.m33());
        }

        private final void putTransposed(Matrix4f m, long destAddr) {
            memPutFloat(destAddr,      m.m00());
            memPutFloat(destAddr + 4,  m.m10());
            memPutFloat(destAddr + 8,  m.m20());
            memPutFloat(destAddr + 12, m.m30());
            memPutFloat(destAddr + 16, m.m01());
            memPutFloat(destAddr + 20, m.m11());
            memPutFloat(destAddr + 24, m.m21());
            memPutFloat(destAddr + 28, m.m31());
            memPutFloat(destAddr + 32, m.m02());
            memPutFloat(destAddr + 36, m.m12());
            memPutFloat(destAddr + 40, m.m22());
            memPutFloat(destAddr + 44, m.m32());
            memPutFloat(destAddr + 48, m.m03());
            memPutFloat(destAddr + 52, m.m13());
            memPutFloat(destAddr + 56, m.m23());
            memPutFloat(destAddr + 60, m.m33());
        }

        private final void put(Matrix4d m, long destAddr) {
            memPutDouble(destAddr,       m.ms[Matrix4d.M00]);
            memPutDouble(destAddr + 8,   m.ms[Matrix4d.M01]);
            memPutDouble(destAddr + 16,  m.ms[Matrix4d.M02]);
            memPutDouble(destAddr + 24,  m.ms[Matrix4d.M03]);
            memPutDouble(destAddr + 32,  m.ms[Matrix4d.M10]);
            memPutDouble(destAddr + 40,  m.ms[Matrix4d.M11]);
            memPutDouble(destAddr + 48,  m.ms[Matrix4d.M12]);
            memPutDouble(destAddr + 56,  m.ms[Matrix4d.M13]);
            memPutDouble(destAddr + 64,  m.ms[Matrix4d.M20]);
            memPutDouble(destAddr + 72,  m.ms[Matrix4d.M21]);
            memPutDouble(destAddr + 80,  m.ms[Matrix4d.M22]);
            memPutDouble(destAddr + 88,  m.ms[Matrix4d.M23]);
            memPutDouble(destAddr + 96,  m.ms[Matrix4d.M30]);
            memPutDouble(destAddr + 104, m.ms[Matrix4d.M31]);
            memPutDouble(destAddr + 112, m.ms[Matrix4d.M32]);
            memPutDouble(destAddr + 120, m.ms[Matrix4d.M33]);
        }

        private final void putTransposed(Matrix4d m, long destAddr) {
            memPutDouble(destAddr,       m.ms[Matrix4d.M00]);
            memPutDouble(destAddr + 8,   m.ms[Matrix4d.M10]);
            memPutDouble(destAddr + 16,  m.ms[Matrix4d.M20]);
            memPutDouble(destAddr + 24,  m.ms[Matrix4d.M30]);
            memPutDouble(destAddr + 32,  m.ms[Matrix4d.M01]);
            memPutDouble(destAddr + 40,  m.ms[Matrix4d.M11]);
            memPutDouble(destAddr + 48,  m.ms[Matrix4d.M21]);
            memPutDouble(destAddr + 56,  m.ms[Matrix4d.M31]);
            memPutDouble(destAddr + 64,  m.ms[Matrix4d.M02]);
            memPutDouble(destAddr + 72,  m.ms[Matrix4d.M12]);
            memPutDouble(destAddr + 80,  m.ms[Matrix4d.M22]);
            memPutDouble(destAddr + 88,  m.ms[Matrix4d.M32]);
            memPutDouble(destAddr + 96,  m.ms[Matrix4d.M03]);
            memPutDouble(destAddr + 104, m.ms[Matrix4d.M13]);
            memPutDouble(destAddr + 112, m.ms[Matrix4d.M23]);
            memPutDouble(destAddr + 120, m.ms[Matrix4d.M33]);
        }

        private final void putf(Matrix4d m, long destAddr) {
            memPutFloat(destAddr,      (float)m.ms[Matrix4d.M00]);
            memPutFloat(destAddr + 4,  (float)m.ms[Matrix4d.M01]);
            memPutFloat(destAddr + 8,  (float)m.ms[Matrix4d.M02]);
            memPutFloat(destAddr + 12, (float)m.ms[Matrix4d.M03]);
            memPutFloat(destAddr + 16, (float)m.ms[Matrix4d.M10]);
            memPutFloat(destAddr + 20, (float)m.ms[Matrix4d.M11]);
            memPutFloat(destAddr + 24, (float)m.ms[Matrix4d.M12]);
            memPutFloat(destAddr + 28, (float)m.ms[Matrix4d.M13]);
            memPutFloat(destAddr + 32, (float)m.ms[Matrix4d.M20]);
            memPutFloat(destAddr + 36, (float)m.ms[Matrix4d.M21]);
            memPutFloat(destAddr + 40, (float)m.ms[Matrix4d.M22]);
            memPutFloat(destAddr + 44, (float)m.ms[Matrix4d.M23]);
            memPutFloat(destAddr + 48, (float)m.ms[Matrix4d.M30]);
            memPutFloat(destAddr + 52, (float)m.ms[Matrix4d.M31]);
            memPutFloat(destAddr + 56, (float)m.ms[Matrix4d.M32]);
            memPutFloat(destAddr + 60, (float)m.ms[Matrix4d.M33]);
        }

        private final void putfTransposed(Matrix4d m, long destAddr) {
            memPutFloat(destAddr,      (float)m.ms[Matrix4d.M00]);
            memPutFloat(destAddr + 4,  (float)m.ms[Matrix4d.M10]);
            memPutFloat(destAddr + 8,  (float)m.ms[Matrix4d.M20]);
            memPutFloat(destAddr + 12, (float)m.ms[Matrix4d.M30]);
            memPutFloat(destAddr + 16, (float)m.ms[Matrix4d.M01]);
            memPutFloat(destAddr + 20, (float)m.ms[Matrix4d.M11]);
            memPutFloat(destAddr + 24, (float)m.ms[Matrix4d.M21]);
            memPutFloat(destAddr + 28, (float)m.ms[Matrix4d.M31]);
            memPutFloat(destAddr + 32, (float)m.ms[Matrix4d.M02]);
            memPutFloat(destAddr + 36, (float)m.ms[Matrix4d.M12]);
            memPutFloat(destAddr + 40, (float)m.ms[Matrix4d.M22]);
            memPutFloat(destAddr + 44, (float)m.ms[Matrix4d.M32]);
            memPutFloat(destAddr + 48, (float)m.ms[Matrix4d.M03]);
            memPutFloat(destAddr + 52, (float)m.ms[Matrix4d.M13]);
            memPutFloat(destAddr + 56, (float)m.ms[Matrix4d.M23]);
            memPutFloat(destAddr + 60, (float)m.ms[Matrix4d.M33]);
        }

        private final void put(Matrix3f m, long destAddr) {
            memPutFloat(destAddr,      m.ms[Matrix3f.M00]);
            memPutFloat(destAddr + 4,  m.ms[Matrix3f.M01]);
            memPutFloat(destAddr + 8,  m.ms[Matrix3f.M02]);
            memPutFloat(destAddr + 12, m.ms[Matrix3f.M10]);
            memPutFloat(destAddr + 16, m.ms[Matrix3f.M11]);
            memPutFloat(destAddr + 20, m.ms[Matrix3f.M12]);
            memPutFloat(destAddr + 24, m.ms[Matrix3f.M20]);
            memPutFloat(destAddr + 28, m.ms[Matrix3f.M21]);
            memPutFloat(destAddr + 32, m.ms[Matrix3f.M22]);
        }

        private final void put(Matrix3d m, long destAddr) {
            memPutDouble(destAddr,      m.ms[Matrix3d.M00]);
            memPutDouble(destAddr + 8,  m.ms[Matrix3d.M01]);
            memPutDouble(destAddr + 16, m.ms[Matrix3d.M02]);
            memPutDouble(destAddr + 24, m.ms[Matrix3d.M10]);
            memPutDouble(destAddr + 32, m.ms[Matrix3d.M11]);
            memPutDouble(destAddr + 40, m.ms[Matrix3d.M12]);
            memPutDouble(destAddr + 48, m.ms[Matrix3d.M20]);
            memPutDouble(destAddr + 56, m.ms[Matrix3d.M21]);
            memPutDouble(destAddr + 64, m.ms[Matrix3d.M22]);
        }

        private final void putf(Matrix3d m, long destAddr) {
            memPutFloat(destAddr,      (float)m.ms[Matrix3d.M00]);
            memPutFloat(destAddr + 4,  (float)m.ms[Matrix3d.M01]);
            memPutFloat(destAddr + 8,  (float)m.ms[Matrix3d.M02]);
            memPutFloat(destAddr + 12, (float)m.ms[Matrix3d.M10]);
            memPutFloat(destAddr + 16, (float)m.ms[Matrix3d.M11]);
            memPutFloat(destAddr + 20, (float)m.ms[Matrix3d.M12]);
            memPutFloat(destAddr + 24, (float)m.ms[Matrix3d.M20]);
            memPutFloat(destAddr + 28, (float)m.ms[Matrix3d.M21]);
            memPutFloat(destAddr + 32, (float)m.ms[Matrix3d.M22]);
        }

        private final void get(Matrix4f m, long srcAddr) {
            m.m00(memGetFloat(srcAddr));
            m.m01(memGetFloat(srcAddr+4));
            m.m02(memGetFloat(srcAddr+8));
            m.m03(memGetFloat(srcAddr+12));
            m.m10(memGetFloat(srcAddr+16));
            m.m11(memGetFloat(srcAddr+20));
            m.m12(memGetFloat(srcAddr+24));
            m.m13(memGetFloat(srcAddr+28));
            m.m20(memGetFloat(srcAddr+32));
            m.m21(memGetFloat(srcAddr+36));
            m.m22(memGetFloat(srcAddr+40));
            m.m23(memGetFloat(srcAddr+44));
            m.m30(memGetFloat(srcAddr+48));
            m.m31(memGetFloat(srcAddr+52));
            m.m32(memGetFloat(srcAddr+56));
            m.m33(memGetFloat(srcAddr+60));
        }

        private final void get(Matrix4d m, long srcAddr) {
            m.ms[Matrix4d.M00] = memGetDouble(srcAddr);
            m.ms[Matrix4d.M01] = memGetDouble(srcAddr+8);
            m.ms[Matrix4d.M02] = memGetDouble(srcAddr+16);
            m.ms[Matrix4d.M03] = memGetDouble(srcAddr+24);
            m.ms[Matrix4d.M10] = memGetDouble(srcAddr+32);
            m.ms[Matrix4d.M11] = memGetDouble(srcAddr+40);
            m.ms[Matrix4d.M12] = memGetDouble(srcAddr+48);
            m.ms[Matrix4d.M13] = memGetDouble(srcAddr+56);
            m.ms[Matrix4d.M20] = memGetDouble(srcAddr+64);
            m.ms[Matrix4d.M21] = memGetDouble(srcAddr+72);
            m.ms[Matrix4d.M22] = memGetDouble(srcAddr+80);
            m.ms[Matrix4d.M23] = memGetDouble(srcAddr+88);
            m.ms[Matrix4d.M30] = memGetDouble(srcAddr+96);
            m.ms[Matrix4d.M31] = memGetDouble(srcAddr+104);
            m.ms[Matrix4d.M32] = memGetDouble(srcAddr+112);
            m.ms[Matrix4d.M33] = memGetDouble(srcAddr+120);
        }

        private final void getf(Matrix4d m, long srcAddr) {
            m.ms[Matrix4d.M00] = memGetFloat(srcAddr);
            m.ms[Matrix4d.M01] = memGetFloat(srcAddr+4);
            m.ms[Matrix4d.M02] = memGetFloat(srcAddr+8);
            m.ms[Matrix4d.M03] = memGetFloat(srcAddr+12);
            m.ms[Matrix4d.M10] = memGetFloat(srcAddr+16);
            m.ms[Matrix4d.M11] = memGetFloat(srcAddr+20);
            m.ms[Matrix4d.M12] = memGetFloat(srcAddr+24);
            m.ms[Matrix4d.M13] = memGetFloat(srcAddr+28);
            m.ms[Matrix4d.M20] = memGetFloat(srcAddr+32);
            m.ms[Matrix4d.M21] = memGetFloat(srcAddr+36);
            m.ms[Matrix4d.M22] = memGetFloat(srcAddr+40);
            m.ms[Matrix4d.M23] = memGetFloat(srcAddr+44);
            m.ms[Matrix4d.M30] = memGetFloat(srcAddr+48);
            m.ms[Matrix4d.M31] = memGetFloat(srcAddr+52);
            m.ms[Matrix4d.M32] = memGetFloat(srcAddr+56);
            m.ms[Matrix4d.M33] = memGetFloat(srcAddr+60);
        }

        private final void get(Matrix3f m, long srcAddr) {
            m.ms[Matrix3f.M00] = memGetFloat(srcAddr);
            m.ms[Matrix3f.M01] = memGetFloat(srcAddr+4);
            m.ms[Matrix3f.M02] = memGetFloat(srcAddr+8);
            m.ms[Matrix3f.M10] = memGetFloat(srcAddr+12);
            m.ms[Matrix3f.M11] = memGetFloat(srcAddr+16);
            m.ms[Matrix3f.M12] = memGetFloat(srcAddr+20);
            m.ms[Matrix3f.M20] = memGetFloat(srcAddr+24);
            m.ms[Matrix3f.M21] = memGetFloat(srcAddr+28);
            m.ms[Matrix3f.M22] = memGetFloat(srcAddr+32);
        }

        private final void get(Matrix3d m, long srcAddr) {
            m.ms[Matrix3d.M00] = memGetDouble(srcAddr);
            m.ms[Matrix3d.M01] = memGetDouble(srcAddr+8);
            m.ms[Matrix3d.M02] = memGetDouble(srcAddr+16);
            m.ms[Matrix3d.M10] = memGetDouble(srcAddr+24);
            m.ms[Matrix3d.M11] = memGetDouble(srcAddr+32);
            m.ms[Matrix3d.M12] = memGetDouble(srcAddr+40);
            m.ms[Matrix3d.M20] = memGetDouble(srcAddr+48);
            m.ms[Matrix3d.M21] = memGetDouble(srcAddr+56);
            m.ms[Matrix3d.M22] = memGetDouble(srcAddr+64);
        }

        private final void getf(Matrix3d m, long srcAddr) {
            m.ms[Matrix3d.M00] = memGetFloat(srcAddr);
            m.ms[Matrix3d.M01] = memGetFloat(srcAddr+4);
            m.ms[Matrix3d.M02] = memGetFloat(srcAddr+8);
            m.ms[Matrix3d.M10] = memGetFloat(srcAddr+12);
            m.ms[Matrix3d.M11] = memGetFloat(srcAddr+16);
            m.ms[Matrix3d.M12] = memGetFloat(srcAddr+20);
            m.ms[Matrix3d.M20] = memGetFloat(srcAddr+24);
            m.ms[Matrix3d.M21] = memGetFloat(srcAddr+28);
            m.ms[Matrix3d.M22] = memGetFloat(srcAddr+32);
        }

        final void put(Matrix4f m, int offset, FloatBuffer dest) {
            put(m, addressOf(dest) + offset * 4);
        }

        final void put(Matrix4f m, int offset, ByteBuffer dest) {
            put(m, addressOf(dest) + offset);
        }

        final void putTransposed(Matrix4f m, int offset, FloatBuffer dest) {
            putTransposed(m, addressOf(dest) + offset * 4);
        }

        final void putTransposed(Matrix4f m, int offset, ByteBuffer dest) {
            putTransposed(m, addressOf(dest) + offset);
        }

        final void put(Matrix4d m, int offset, DoubleBuffer dest) {
            put(m, addressOf(dest) + offset * 8);
        }

        final void put(Matrix4d m, int offset, ByteBuffer dest) {
            put(m, addressOf(dest) + offset);
        }

        final void putf(Matrix4d m, int offset, FloatBuffer dest) {
            putf(m, addressOf(dest) + offset * 4);
        }

        final void putf(Matrix4d m, int offset, ByteBuffer dest) {
            putf(m, addressOf(dest) + offset);
        }

        final void putTransposed(Matrix4d m, int offset, DoubleBuffer dest) {
            putTransposed(m, addressOf(dest) + offset * 8);
        }

        final void putTransposed(Matrix4d m, int offset, ByteBuffer dest) {
            putTransposed(m, addressOf(dest) + offset);
        }

        final void putfTransposed(Matrix4d m, int offset, FloatBuffer dest) {
            putfTransposed(m, addressOf(dest) + offset * 4);
        }

        final void putfTransposed(Matrix4d m, int offset, ByteBuffer dest) {
            putfTransposed(m, addressOf(dest) + offset);
        }

        final void put(Matrix3f m, int offset, FloatBuffer dest) {
            put(m, addressOf(dest) + offset * 4);
        }

        final void put(Matrix3f m, int offset, ByteBuffer dest) {
            put(m, addressOf(dest) + offset);
        }

        final void put(Matrix3d m, int offset, DoubleBuffer dest) {
            put(m, addressOf(dest) + offset * 4);
        }

        final void put(Matrix3d m, int offset, ByteBuffer dest) {
            put(m, addressOf(dest) + offset);
        }

        final void putf(Matrix3d m, int offset, FloatBuffer dest) {
            putf(m, addressOf(dest) + offset * 4);
        }

        final void putf(Matrix3d m, int offset, ByteBuffer dest) {
            putf(m, addressOf(dest) + offset);
        }

        final void get(Matrix4f m, int offset, FloatBuffer src) {
            get(m, addressOf(src) + offset * 4);
        }

        final void get(Matrix4f m, int offset, ByteBuffer src) {
            get(m, addressOf(src) + offset);
        }

        final void get(Matrix4d m, int offset, DoubleBuffer src) {
            get(m, addressOf(src) + offset * 8);
        }

        final void get(Matrix4d m, int offset, ByteBuffer src) {
            get(m, addressOf(src) + offset);
        }

        final void getf(Matrix4d m, int offset, FloatBuffer src) {
            getf(m, addressOf(src) + offset * 4);
        }

        final void getf(Matrix4d m, int offset, ByteBuffer src) {
            getf(m, addressOf(src) + offset);
        }

        final void get(Matrix3f m, int offset, FloatBuffer src) {
            get(m, addressOf(src) + offset * 4);
        }

        final void get(Matrix3f m, int offset, ByteBuffer src) {
            get(m, addressOf(src) + offset);
        }

        final void get(Matrix3d m, int offset, DoubleBuffer src) {
            get(m, addressOf(src) + offset * 4);
        }

        final void get(Matrix3d m, int offset, ByteBuffer src) {
            get(m, addressOf(src) + offset);
        }

        final void getf(Matrix3d m, int offset, FloatBuffer src) {
            getf(m, addressOf(src) + offset * 4);
        }

        final void getf(Matrix3d m, int offset, ByteBuffer src) {
            getf(m, addressOf(src) + offset);
        }
    }
}
