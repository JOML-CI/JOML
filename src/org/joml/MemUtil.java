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

import java.lang.reflect.Field;
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
    private static final boolean nounsafe = hasOption("joml.nounsafe");
    static final MemUtil INSTANCE = createInstance();

    private static boolean hasOption(String option) {
    	String v = System.getProperty(option);
    	if (v == null)
    		return false;
    	if (v.trim().length() == 0)
    		return true;
    	return Boolean.valueOf(v).booleanValue();
    }

    private static final MemUtil createInstance() {
        MemUtil accessor;
        try {
            if (nounsafe)
                accessor = new MemUtilNIO();
            else
                accessor = new MemUtilUnsafe();
        } catch (Throwable e) {
            accessor = new MemUtilNIO();
        }
        return accessor;
    }

    abstract void put(Matrix4f m, int offset, FloatBuffer dest);
    abstract void put(Matrix4f m, int offset, ByteBuffer dest);
    abstract void put(Matrix4x3f m, int offset, FloatBuffer dest);
    abstract void put(Matrix4x3f m, int offset, ByteBuffer dest);
    abstract void put4x4(Matrix4x3f m, int offset, FloatBuffer dest);
    abstract void put4x4(Matrix4x3f m, int offset, ByteBuffer dest);
    abstract void putTransposed(Matrix4f m, int offset, FloatBuffer dest);
    abstract void putTransposed(Matrix4f m, int offset, ByteBuffer dest);
    abstract void put4x3Transposed(Matrix4f m, int offset, FloatBuffer dest);
    abstract void put4x3Transposed(Matrix4f m, int offset, ByteBuffer dest);
    abstract void putTransposed(Matrix4x3f m, int offset, FloatBuffer dest);
    abstract void putTransposed(Matrix4x3f m, int offset, ByteBuffer dest);
    abstract void put(Matrix4d m, int offset, DoubleBuffer dest);
    abstract void put(Matrix4d m, int offset, ByteBuffer dest);
    abstract void put(Matrix4x3d m, int offset, DoubleBuffer dest);
    abstract void put(Matrix4x3d m, int offset, ByteBuffer dest);
    abstract void putf(Matrix4d m, int offset, FloatBuffer dest);
    abstract void putf(Matrix4d m, int offset, ByteBuffer dest);
    abstract void putf(Matrix4x3d m, int offset, FloatBuffer dest);
    abstract void putf(Matrix4x3d m, int offset, ByteBuffer dest);
    abstract void putTransposed(Matrix4d m, int offset, DoubleBuffer dest);
    abstract void putTransposed(Matrix4d m, int offset, ByteBuffer dest);
    abstract void put4x3Transposed(Matrix4d m, int offset, DoubleBuffer dest);
    abstract void put4x3Transposed(Matrix4d m, int offset, ByteBuffer dest);
    abstract void putTransposed(Matrix4x3d m, int offset, DoubleBuffer dest);
    abstract void putTransposed(Matrix4x3d m, int offset, ByteBuffer dest);
    abstract void putfTransposed(Matrix4d m, int offset, FloatBuffer dest);
    abstract void putfTransposed(Matrix4d m, int offset, ByteBuffer dest);
    abstract void putfTransposed(Matrix4x3d m, int offset, FloatBuffer dest);
    abstract void putfTransposed(Matrix4x3d m, int offset, ByteBuffer dest);
    abstract void put(Matrix3f m, int offset, FloatBuffer dest);
    abstract void put(Matrix3f m, int offset, ByteBuffer dest);
    abstract void put(Matrix3d m, int offset, DoubleBuffer dest);
    abstract void put(Matrix3d m, int offset, ByteBuffer dest);
    abstract void putf(Matrix3d m, int offset, FloatBuffer dest);
    abstract void putf(Matrix3d m, int offset, ByteBuffer dest);
    abstract void get(Matrix4f m, int offset, FloatBuffer src);
    abstract void get(Matrix4f m, int offset, ByteBuffer src);
    abstract void get(Matrix4x3f m, int offset, FloatBuffer src);
    abstract void get(Matrix4x3f m, int offset, ByteBuffer src);
    abstract void get(Matrix4d m, int offset, DoubleBuffer src);
    abstract void get(Matrix4d m, int offset, ByteBuffer src);
    abstract void get(Matrix4x3d m, int offset, DoubleBuffer src);
    abstract void get(Matrix4x3d m, int offset, ByteBuffer src);
    abstract void getf(Matrix4d m, int offset, FloatBuffer src);
    abstract void getf(Matrix4d m, int offset, ByteBuffer src);
    abstract void getf(Matrix4x3d m, int offset, FloatBuffer src);
    abstract void getf(Matrix4x3d m, int offset, ByteBuffer src);
    abstract void get(Matrix3f m, int offset, FloatBuffer src);
    abstract void get(Matrix3f m, int offset, ByteBuffer src);
    abstract void get(Matrix3d m, int offset, DoubleBuffer src);
    abstract void get(Matrix3d m, int offset, ByteBuffer src);
    abstract void getf(Matrix3d m, int offset, FloatBuffer src);
    abstract void getf(Matrix3d m, int offset, ByteBuffer src);

    abstract void copy(Matrix4f src, Matrix4f dest);
    abstract void copy(Matrix4x3f src, Matrix4x3f dest);
    abstract void copy(Matrix4f src, Matrix4x3f dest);
    abstract void copy(Matrix3f src, Matrix3f dest);
    abstract void copy(Matrix3f src, Matrix4f dest);
    abstract void copy(Matrix4f src, Matrix3f dest);
    abstract void copy(Matrix3f src, Matrix4x3f dest);
    abstract void copy3x3(Matrix4f src, Matrix4f dest);
    abstract void copy4x3(Matrix4f src, Matrix4f dest);
    abstract void copy4x3(Matrix4x3f src, Matrix4f dest);
    abstract void identity(Matrix4f dest);
    abstract void identity(Matrix4x3f dest);
    abstract void identity(Matrix3f dest);
    abstract void swap(Matrix4f m1, Matrix4f m2);
    abstract void swap(Matrix4x3f m1, Matrix4x3f m2);
    abstract void swap(Matrix3f m1, Matrix3f m2);
    abstract void zero(Matrix4f dest);
    abstract void zero(Matrix4x3f dest);
    abstract void zero(Matrix3f dest);

    abstract void identity(DualQuaternionf dq);
    abstract void zero(DualQuaternionf dq);
    abstract void copy(DualQuaternionf src, DualQuaternionf dest);
    abstract void putMatrix4f(DualQuaternionf dq, int position, ByteBuffer dest);
    abstract void putMatrix4f(DualQuaternionf dq, int position, FloatBuffer dest);
    abstract void putMatrix4x3f(DualQuaternionf dq, int position, ByteBuffer dest);
    abstract void putMatrix4x3f(DualQuaternionf dq, int position, FloatBuffer dest);
    abstract void putMatrix3f(Quaternionf q, int position, ByteBuffer dest);
    abstract void putMatrix3f(Quaternionf q, int position, FloatBuffer dest);

    static final class MemUtilNIO extends MemUtil {
        final void put(Matrix4f m, int offset, FloatBuffer dest) {
            dest.put(offset,    m.m00);
            dest.put(offset+1,  m.m01);
            dest.put(offset+2,  m.m02);
            dest.put(offset+3,  m.m03);
            dest.put(offset+4,  m.m10);
            dest.put(offset+5,  m.m11);
            dest.put(offset+6,  m.m12);
            dest.put(offset+7,  m.m13);
            dest.put(offset+8,  m.m20);
            dest.put(offset+9,  m.m21);
            dest.put(offset+10, m.m22);
            dest.put(offset+11, m.m23);
            dest.put(offset+12, m.m30);
            dest.put(offset+13, m.m31);
            dest.put(offset+14, m.m32);
            dest.put(offset+15, m.m33);
        }

        final void put(Matrix4f m, int offset, ByteBuffer dest) {
            dest.putFloat(offset,    m.m00);
            dest.putFloat(offset+4,  m.m01);
            dest.putFloat(offset+8,  m.m02);
            dest.putFloat(offset+12, m.m03);
            dest.putFloat(offset+16, m.m10);
            dest.putFloat(offset+20, m.m11);
            dest.putFloat(offset+24, m.m12);
            dest.putFloat(offset+28, m.m13);
            dest.putFloat(offset+32, m.m20);
            dest.putFloat(offset+36, m.m21);
            dest.putFloat(offset+40, m.m22);
            dest.putFloat(offset+44, m.m23);
            dest.putFloat(offset+48, m.m30);
            dest.putFloat(offset+52, m.m31);
            dest.putFloat(offset+56, m.m32);
            dest.putFloat(offset+60, m.m33);
        }

        final void put(Matrix4x3f m, int offset, FloatBuffer dest) {
            dest.put(offset,    m.m00);
            dest.put(offset+1,  m.m01);
            dest.put(offset+2,  m.m02);
            dest.put(offset+3,  m.m10);
            dest.put(offset+4,  m.m11);
            dest.put(offset+5,  m.m12);
            dest.put(offset+6,  m.m20);
            dest.put(offset+7,  m.m21);
            dest.put(offset+8,  m.m22);
            dest.put(offset+9,  m.m30);
            dest.put(offset+10, m.m31);
            dest.put(offset+11, m.m32);
        }

        final void put(Matrix4x3f m, int offset, ByteBuffer dest) {
            dest.putFloat(offset,    m.m00);
            dest.putFloat(offset+4,  m.m01);
            dest.putFloat(offset+8,  m.m02);
            dest.putFloat(offset+12, m.m10);
            dest.putFloat(offset+16, m.m11);
            dest.putFloat(offset+20, m.m12);
            dest.putFloat(offset+24, m.m20);
            dest.putFloat(offset+28, m.m21);
            dest.putFloat(offset+32, m.m22);
            dest.putFloat(offset+36, m.m30);
            dest.putFloat(offset+40, m.m31);
            dest.putFloat(offset+44, m.m32);
        }

        final void put4x4(Matrix4x3f m, int offset, FloatBuffer dest) {
            dest.put(offset,    m.m00);
            dest.put(offset+1,  m.m01);
            dest.put(offset+2,  m.m02);
            dest.put(offset+3,  0.0f);
            dest.put(offset+4,  m.m10);
            dest.put(offset+5,  m.m11);
            dest.put(offset+6,  m.m12);
            dest.put(offset+7,  0.0f);
            dest.put(offset+8,  m.m20);
            dest.put(offset+9,  m.m21);
            dest.put(offset+10, m.m22);
            dest.put(offset+11, 0.0f);
            dest.put(offset+12, m.m30);
            dest.put(offset+13, m.m31);
            dest.put(offset+14, m.m32);
            dest.put(offset+15, 1.0f);
        }

        final void put4x4(Matrix4x3f m, int offset, ByteBuffer dest) {
            dest.putFloat(offset,    m.m00);
            dest.putFloat(offset+4,  m.m01);
            dest.putFloat(offset+8,  m.m02);
            dest.putFloat(offset+12, 0.0f);
            dest.putFloat(offset+16, m.m10);
            dest.putFloat(offset+20, m.m11);
            dest.putFloat(offset+24, m.m12);
            dest.putFloat(offset+28, 0.0f);
            dest.putFloat(offset+32, m.m20);
            dest.putFloat(offset+36, m.m21);
            dest.putFloat(offset+40, m.m22);
            dest.putFloat(offset+44, 0.0f);
            dest.putFloat(offset+48, m.m30);
            dest.putFloat(offset+52, m.m31);
            dest.putFloat(offset+56, m.m32);
            dest.putFloat(offset+60, 1.0f);
        }

        final void putTransposed(Matrix4f m, int offset, FloatBuffer dest) {
            dest.put(offset,    m.m00);
            dest.put(offset+1,  m.m10);
            dest.put(offset+2,  m.m20);
            dest.put(offset+3,  m.m30);
            dest.put(offset+4,  m.m01);
            dest.put(offset+5,  m.m11);
            dest.put(offset+6,  m.m21);
            dest.put(offset+7,  m.m31);
            dest.put(offset+8,  m.m02);
            dest.put(offset+9,  m.m12);
            dest.put(offset+10, m.m22);
            dest.put(offset+11, m.m32);
            dest.put(offset+12, m.m03);
            dest.put(offset+13, m.m13);
            dest.put(offset+14, m.m23);
            dest.put(offset+15, m.m33);
        }

        final void putTransposed(Matrix4f m, int offset, ByteBuffer dest) {
            dest.putFloat(offset,    m.m00);
            dest.putFloat(offset+4,  m.m10);
            dest.putFloat(offset+8,  m.m20);
            dest.putFloat(offset+12, m.m30);
            dest.putFloat(offset+16, m.m01);
            dest.putFloat(offset+20, m.m11);
            dest.putFloat(offset+24, m.m21);
            dest.putFloat(offset+28, m.m31);
            dest.putFloat(offset+32, m.m02);
            dest.putFloat(offset+36, m.m12);
            dest.putFloat(offset+40, m.m22);
            dest.putFloat(offset+44, m.m32);
            dest.putFloat(offset+48, m.m03);
            dest.putFloat(offset+52, m.m13);
            dest.putFloat(offset+56, m.m23);
            dest.putFloat(offset+60, m.m33);
        }

        final void put4x3Transposed(Matrix4f m, int offset, FloatBuffer dest) {
            dest.put(offset,    m.m00);
            dest.put(offset+1,  m.m10);
            dest.put(offset+2,  m.m20);
            dest.put(offset+3,  m.m30);
            dest.put(offset+4,  m.m01);
            dest.put(offset+5,  m.m11);
            dest.put(offset+6,  m.m21);
            dest.put(offset+7,  m.m31);
            dest.put(offset+8,  m.m02);
            dest.put(offset+9,  m.m12);
            dest.put(offset+10, m.m22);
            dest.put(offset+11, m.m32);
        }

        final void put4x3Transposed(Matrix4f m, int offset, ByteBuffer dest) {
            dest.putFloat(offset,    m.m00);
            dest.putFloat(offset+4,  m.m10);
            dest.putFloat(offset+8,  m.m20);
            dest.putFloat(offset+12, m.m30);
            dest.putFloat(offset+16, m.m01);
            dest.putFloat(offset+20, m.m11);
            dest.putFloat(offset+24, m.m21);
            dest.putFloat(offset+28, m.m31);
            dest.putFloat(offset+32, m.m02);
            dest.putFloat(offset+36, m.m12);
            dest.putFloat(offset+40, m.m22);
            dest.putFloat(offset+44, m.m32);
        }

        final void putTransposed(Matrix4x3f m, int offset, FloatBuffer dest) {
            dest.put(offset,    m.m00);
            dest.put(offset+1,  m.m10);
            dest.put(offset+2,  m.m20);
            dest.put(offset+3,  m.m30);
            dest.put(offset+4,  m.m01);
            dest.put(offset+5,  m.m11);
            dest.put(offset+6,  m.m21);
            dest.put(offset+7,  m.m31);
            dest.put(offset+8,  m.m02);
            dest.put(offset+9,  m.m12);
            dest.put(offset+10, m.m22);
            dest.put(offset+11, m.m32);
        }

        final void putTransposed(Matrix4x3f m, int offset, ByteBuffer dest) {
            dest.putFloat(offset,    m.m00);
            dest.putFloat(offset+4,  m.m10);
            dest.putFloat(offset+8,  m.m20);
            dest.putFloat(offset+12, m.m30);
            dest.putFloat(offset+16, m.m01);
            dest.putFloat(offset+20, m.m11);
            dest.putFloat(offset+24, m.m21);
            dest.putFloat(offset+28, m.m31);
            dest.putFloat(offset+32, m.m02);
            dest.putFloat(offset+36, m.m12);
            dest.putFloat(offset+40, m.m22);
            dest.putFloat(offset+44, m.m32);
        }

        final void put(Matrix4d m, int offset, DoubleBuffer dest) {
            dest.put(offset,    m.m00);
            dest.put(offset+1,  m.m01);
            dest.put(offset+2,  m.m02);
            dest.put(offset+3,  m.m03);
            dest.put(offset+4,  m.m10);
            dest.put(offset+5,  m.m11);
            dest.put(offset+6,  m.m12);
            dest.put(offset+7,  m.m13);
            dest.put(offset+8,  m.m20);
            dest.put(offset+9,  m.m21);
            dest.put(offset+10, m.m22);
            dest.put(offset+11, m.m23);
            dest.put(offset+12, m.m30);
            dest.put(offset+13, m.m31);
            dest.put(offset+14, m.m32);
            dest.put(offset+15, m.m33);
        }

        final void put(Matrix4d m, int offset, ByteBuffer dest) {
            dest.putDouble(offset,    m.m00);
            dest.putDouble(offset+4,  m.m01);
            dest.putDouble(offset+8,  m.m02);
            dest.putDouble(offset+12, m.m03);
            dest.putDouble(offset+16, m.m10);
            dest.putDouble(offset+20, m.m11);
            dest.putDouble(offset+24, m.m12);
            dest.putDouble(offset+28, m.m13);
            dest.putDouble(offset+32, m.m20);
            dest.putDouble(offset+36, m.m21);
            dest.putDouble(offset+40, m.m22);
            dest.putDouble(offset+44, m.m23);
            dest.putDouble(offset+48, m.m30);
            dest.putDouble(offset+52, m.m31);
            dest.putDouble(offset+56, m.m32);
            dest.putDouble(offset+60, m.m33);
        }

        final void put(Matrix4x3d m, int offset, DoubleBuffer dest) {
            dest.put(offset,    m.m00);
            dest.put(offset+1,  m.m01);
            dest.put(offset+2,  m.m02);
            dest.put(offset+3,  m.m10);
            dest.put(offset+4,  m.m11);
            dest.put(offset+5,  m.m12);
            dest.put(offset+6,  m.m20);
            dest.put(offset+7,  m.m21);
            dest.put(offset+8,  m.m22);
            dest.put(offset+9,  m.m30);
            dest.put(offset+10, m.m31);
            dest.put(offset+11, m.m32);
        }

        final void put(Matrix4x3d m, int offset, ByteBuffer dest) {
            dest.putDouble(offset,    m.m00);
            dest.putDouble(offset+4,  m.m01);
            dest.putDouble(offset+8,  m.m02);
            dest.putDouble(offset+12, m.m10);
            dest.putDouble(offset+16, m.m11);
            dest.putDouble(offset+20, m.m12);
            dest.putDouble(offset+24, m.m20);
            dest.putDouble(offset+28, m.m21);
            dest.putDouble(offset+32, m.m22);
            dest.putDouble(offset+36, m.m30);
            dest.putDouble(offset+40, m.m31);
            dest.putDouble(offset+44, m.m32);
        }

        final void putf(Matrix4d m, int offset, FloatBuffer dest) {
            dest.put(offset,    (float)m.m00);
            dest.put(offset+1,  (float)m.m01);
            dest.put(offset+2,  (float)m.m02);
            dest.put(offset+3,  (float)m.m03);
            dest.put(offset+4,  (float)m.m10);
            dest.put(offset+5,  (float)m.m11);
            dest.put(offset+6,  (float)m.m12);
            dest.put(offset+7,  (float)m.m13);
            dest.put(offset+8,  (float)m.m20);
            dest.put(offset+9,  (float)m.m21);
            dest.put(offset+10, (float)m.m22);
            dest.put(offset+11, (float)m.m23);
            dest.put(offset+12, (float)m.m30);
            dest.put(offset+13, (float)m.m31);
            dest.put(offset+14, (float)m.m32);
            dest.put(offset+15, (float)m.m33);
        }

        final void putf(Matrix4d m, int offset, ByteBuffer dest) {
            dest.putFloat(offset,    (float)m.m00);
            dest.putFloat(offset+4,  (float)m.m01);
            dest.putFloat(offset+8,  (float)m.m02);
            dest.putFloat(offset+12, (float)m.m03);
            dest.putFloat(offset+16, (float)m.m10);
            dest.putFloat(offset+20, (float)m.m11);
            dest.putFloat(offset+24, (float)m.m12);
            dest.putFloat(offset+28, (float)m.m13);
            dest.putFloat(offset+32, (float)m.m20);
            dest.putFloat(offset+36, (float)m.m21);
            dest.putFloat(offset+40, (float)m.m22);
            dest.putFloat(offset+44, (float)m.m23);
            dest.putFloat(offset+48, (float)m.m30);
            dest.putFloat(offset+52, (float)m.m31);
            dest.putFloat(offset+56, (float)m.m32);
            dest.putFloat(offset+60, (float)m.m33);
        }

        final void putf(Matrix4x3d m, int offset, FloatBuffer dest) {
            dest.put(offset,    (float)m.m00);
            dest.put(offset+1,  (float)m.m01);
            dest.put(offset+2,  (float)m.m02);
            dest.put(offset+3,  (float)m.m10);
            dest.put(offset+4,  (float)m.m11);
            dest.put(offset+5,  (float)m.m12);
            dest.put(offset+6,  (float)m.m20);
            dest.put(offset+7,  (float)m.m21);
            dest.put(offset+8,  (float)m.m22);
            dest.put(offset+9,  (float)m.m30);
            dest.put(offset+10, (float)m.m31);
            dest.put(offset+11, (float)m.m32);
        }

        final void putf(Matrix4x3d m, int offset, ByteBuffer dest) {
            dest.putFloat(offset,    (float)m.m00);
            dest.putFloat(offset+4,  (float)m.m01);
            dest.putFloat(offset+8,  (float)m.m02);
            dest.putFloat(offset+12, (float)m.m10);
            dest.putFloat(offset+16, (float)m.m11);
            dest.putFloat(offset+20, (float)m.m12);
            dest.putFloat(offset+24, (float)m.m20);
            dest.putFloat(offset+28, (float)m.m21);
            dest.putFloat(offset+32, (float)m.m22);
            dest.putFloat(offset+36, (float)m.m30);
            dest.putFloat(offset+40, (float)m.m31);
            dest.putFloat(offset+44, (float)m.m32);
        }

        final void putTransposed(Matrix4d m, int offset, DoubleBuffer dest) {
            dest.put(offset,    m.m00);
            dest.put(offset+1,  m.m10);
            dest.put(offset+2,  m.m20);
            dest.put(offset+3,  m.m30);
            dest.put(offset+4,  m.m01);
            dest.put(offset+5,  m.m11);
            dest.put(offset+6,  m.m21);
            dest.put(offset+7,  m.m31);
            dest.put(offset+8,  m.m02);
            dest.put(offset+9,  m.m12);
            dest.put(offset+10, m.m22);
            dest.put(offset+11, m.m32);
            dest.put(offset+12, m.m03);
            dest.put(offset+13, m.m13);
            dest.put(offset+14, m.m23);
            dest.put(offset+15, m.m33);
        }

        final void putTransposed(Matrix4d m, int offset, ByteBuffer dest) {
            dest.putDouble(offset,     m.m00);
            dest.putDouble(offset+8,   m.m10);
            dest.putDouble(offset+16,  m.m20);
            dest.putDouble(offset+24,  m.m30);
            dest.putDouble(offset+32,  m.m01);
            dest.putDouble(offset+40,  m.m11);
            dest.putDouble(offset+48,  m.m21);
            dest.putDouble(offset+56,  m.m31);
            dest.putDouble(offset+64,  m.m02);
            dest.putDouble(offset+72,  m.m12);
            dest.putDouble(offset+80,  m.m22);
            dest.putDouble(offset+88,  m.m32);
            dest.putDouble(offset+96,  m.m03);
            dest.putDouble(offset+104, m.m13);
            dest.putDouble(offset+112, m.m23);
            dest.putDouble(offset+120, m.m33);
        }

        final void put4x3Transposed(Matrix4d m, int offset, DoubleBuffer dest) {
            dest.put(offset,    m.m00);
            dest.put(offset+1,  m.m10);
            dest.put(offset+2,  m.m20);
            dest.put(offset+3,  m.m30);
            dest.put(offset+4,  m.m01);
            dest.put(offset+5,  m.m11);
            dest.put(offset+6,  m.m21);
            dest.put(offset+7,  m.m31);
            dest.put(offset+8,  m.m02);
            dest.put(offset+9,  m.m12);
            dest.put(offset+10, m.m22);
            dest.put(offset+11, m.m32);
        }

        final void put4x3Transposed(Matrix4d m, int offset, ByteBuffer dest) {
            dest.putDouble(offset,     m.m00);
            dest.putDouble(offset+8,   m.m10);
            dest.putDouble(offset+16,  m.m20);
            dest.putDouble(offset+24,  m.m30);
            dest.putDouble(offset+32,  m.m01);
            dest.putDouble(offset+40,  m.m11);
            dest.putDouble(offset+48,  m.m21);
            dest.putDouble(offset+56,  m.m31);
            dest.putDouble(offset+64,  m.m02);
            dest.putDouble(offset+72,  m.m12);
            dest.putDouble(offset+80,  m.m22);
            dest.putDouble(offset+88,  m.m32);
        }

        final void putTransposed(Matrix4x3d m, int offset, DoubleBuffer dest) {
            dest.put(offset,    m.m00);
            dest.put(offset+1,  m.m10);
            dest.put(offset+2,  m.m20);
            dest.put(offset+3,  m.m30);
            dest.put(offset+4,  m.m01);
            dest.put(offset+5,  m.m11);
            dest.put(offset+6,  m.m21);
            dest.put(offset+7,  m.m31);
            dest.put(offset+8,  m.m02);
            dest.put(offset+9,  m.m12);
            dest.put(offset+10, m.m22);
            dest.put(offset+11, m.m32);
        }

        final void putTransposed(Matrix4x3d m, int offset, ByteBuffer dest) {
            dest.putDouble(offset,    m.m00);
            dest.putDouble(offset+4,  m.m10);
            dest.putDouble(offset+8,  m.m20);
            dest.putDouble(offset+12, m.m30);
            dest.putDouble(offset+16, m.m01);
            dest.putDouble(offset+20, m.m11);
            dest.putDouble(offset+24, m.m21);
            dest.putDouble(offset+28, m.m31);
            dest.putDouble(offset+32, m.m02);
            dest.putDouble(offset+36, m.m12);
            dest.putDouble(offset+40, m.m22);
            dest.putDouble(offset+44, m.m32);
        }

        final void putfTransposed(Matrix4x3d m, int offset, FloatBuffer dest) {
            dest.put(offset,    (float)m.m00);
            dest.put(offset+1,  (float)m.m10);
            dest.put(offset+2,  (float)m.m20);
            dest.put(offset+3,  (float)m.m30);
            dest.put(offset+4,  (float)m.m01);
            dest.put(offset+5,  (float)m.m11);
            dest.put(offset+6,  (float)m.m21);
            dest.put(offset+7,  (float)m.m31);
            dest.put(offset+8,  (float)m.m02);
            dest.put(offset+9,  (float)m.m12);
            dest.put(offset+10, (float)m.m22);
            dest.put(offset+11, (float)m.m32);
        }

        final void putfTransposed(Matrix4x3d m, int offset, ByteBuffer dest) {
            dest.putFloat(offset,    (float)m.m00);
            dest.putFloat(offset+4,  (float)m.m10);
            dest.putFloat(offset+8,  (float)m.m20);
            dest.putFloat(offset+12, (float)m.m30);
            dest.putFloat(offset+16, (float)m.m01);
            dest.putFloat(offset+20, (float)m.m11);
            dest.putFloat(offset+24, (float)m.m21);
            dest.putFloat(offset+28, (float)m.m31);
            dest.putFloat(offset+32, (float)m.m02);
            dest.putFloat(offset+36, (float)m.m12);
            dest.putFloat(offset+40, (float)m.m22);
            dest.putFloat(offset+44, (float)m.m32);
        }

        final void putfTransposed(Matrix4d m, int offset, FloatBuffer dest) {
            dest.put(offset,    (float)m.m00);
            dest.put(offset+1,  (float)m.m10);
            dest.put(offset+2,  (float)m.m20);
            dest.put(offset+3,  (float)m.m30);
            dest.put(offset+4,  (float)m.m01);
            dest.put(offset+5,  (float)m.m11);
            dest.put(offset+6,  (float)m.m21);
            dest.put(offset+7,  (float)m.m31);
            dest.put(offset+8,  (float)m.m02);
            dest.put(offset+9,  (float)m.m12);
            dest.put(offset+10, (float)m.m22);
            dest.put(offset+11, (float)m.m32);
            dest.put(offset+12, (float)m.m03);
            dest.put(offset+13, (float)m.m13);
            dest.put(offset+14, (float)m.m23);
            dest.put(offset+15, (float)m.m33);
        }

        final void putfTransposed(Matrix4d m, int offset, ByteBuffer dest) {
            dest.putFloat(offset,    (float)m.m00);
            dest.putFloat(offset+4,  (float)m.m10);
            dest.putFloat(offset+8,  (float)m.m20);
            dest.putFloat(offset+12, (float)m.m30);
            dest.putFloat(offset+16, (float)m.m01);
            dest.putFloat(offset+20, (float)m.m11);
            dest.putFloat(offset+24, (float)m.m21);
            dest.putFloat(offset+28, (float)m.m31);
            dest.putFloat(offset+32, (float)m.m02);
            dest.putFloat(offset+36, (float)m.m12);
            dest.putFloat(offset+40, (float)m.m22);
            dest.putFloat(offset+44, (float)m.m32);
            dest.putFloat(offset+48, (float)m.m03);
            dest.putFloat(offset+52, (float)m.m13);
            dest.putFloat(offset+56, (float)m.m23);
            dest.putFloat(offset+60, (float)m.m33);
        }

        final void put(Matrix3f m, int offset, FloatBuffer dest) {
            dest.put(offset,   m.m00);
            dest.put(offset+1, m.m01);
            dest.put(offset+2, m.m02);
            dest.put(offset+3, m.m10);
            dest.put(offset+4, m.m11);
            dest.put(offset+5, m.m12);
            dest.put(offset+6, m.m20);
            dest.put(offset+7, m.m21);
            dest.put(offset+8, m.m22);
        }

        final void put(Matrix3f m, int offset, ByteBuffer dest) {
            dest.putFloat(offset,    m.m00);
            dest.putFloat(offset+4,  m.m01);
            dest.putFloat(offset+8,  m.m02);
            dest.putFloat(offset+12, m.m10);
            dest.putFloat(offset+16, m.m11);
            dest.putFloat(offset+20, m.m12);
            dest.putFloat(offset+24, m.m20);
            dest.putFloat(offset+28, m.m21);
            dest.putFloat(offset+32, m.m22);
        }

        final void put(Matrix3d m, int offset, DoubleBuffer dest) {
            dest.put(offset,   m.m00);
            dest.put(offset+1, m.m01);
            dest.put(offset+2, m.m02);
            dest.put(offset+3, m.m10);
            dest.put(offset+4, m.m11);
            dest.put(offset+5, m.m12);
            dest.put(offset+6, m.m20);
            dest.put(offset+7, m.m21);
            dest.put(offset+8, m.m22);
        }

        final void put(Matrix3d m, int offset, ByteBuffer dest) {
            dest.putDouble(offset,    m.m00);
            dest.putDouble(offset+8,  m.m01);
            dest.putDouble(offset+16, m.m02);
            dest.putDouble(offset+24, m.m10);
            dest.putDouble(offset+32, m.m11);
            dest.putDouble(offset+40, m.m12);
            dest.putDouble(offset+48, m.m20);
            dest.putDouble(offset+56, m.m21);
            dest.putDouble(offset+64, m.m22);
        }

        final void putf(Matrix3d m, int offset, FloatBuffer dest) {
            dest.put(offset,   (float)m.m00);
            dest.put(offset+1, (float)m.m01);
            dest.put(offset+2, (float)m.m02);
            dest.put(offset+3, (float)m.m10);
            dest.put(offset+4, (float)m.m11);
            dest.put(offset+5, (float)m.m12);
            dest.put(offset+6, (float)m.m20);
            dest.put(offset+7, (float)m.m21);
            dest.put(offset+8, (float)m.m22);
        }

        final void putf(Matrix3d m, int offset, ByteBuffer dest) {
            dest.putFloat(offset,    (float)m.m00);
            dest.putFloat(offset+4,  (float)m.m01);
            dest.putFloat(offset+8,  (float)m.m02);
            dest.putFloat(offset+12, (float)m.m10);
            dest.putFloat(offset+16, (float)m.m11);
            dest.putFloat(offset+20, (float)m.m12);
            dest.putFloat(offset+24, (float)m.m20);
            dest.putFloat(offset+28, (float)m.m21);
            dest.putFloat(offset+32, (float)m.m22);
        }

        final void get(Matrix4f m, int offset, FloatBuffer src) {
            m.m00 = src.get(offset);
            m.m01 = src.get(offset+1);
            m.m02 = src.get(offset+2);
            m.m03 = src.get(offset+3);
            m.m10 = src.get(offset+4);
            m.m11 = src.get(offset+5);
            m.m12 = src.get(offset+6);
            m.m13 = src.get(offset+7);
            m.m20 = src.get(offset+8);
            m.m21 = src.get(offset+9);
            m.m22 = src.get(offset+10);
            m.m23 = src.get(offset+11);
            m.m30 = src.get(offset+12);
            m.m31 = src.get(offset+13);
            m.m32 = src.get(offset+14);
            m.m33 = src.get(offset+15);
        }

        final void get(Matrix4f m, int offset, ByteBuffer src) {
            m.m00 = src.getFloat(offset);
            m.m01 = src.getFloat(offset+4);
            m.m02 = src.getFloat(offset+8);
            m.m03 = src.getFloat(offset+12);
            m.m10 = src.getFloat(offset+16);
            m.m11 = src.getFloat(offset+20);
            m.m12 = src.getFloat(offset+24);
            m.m13 = src.getFloat(offset+28);
            m.m20 = src.getFloat(offset+32);
            m.m21 = src.getFloat(offset+36);
            m.m22 = src.getFloat(offset+40);
            m.m23 = src.getFloat(offset+44);
            m.m30 = src.getFloat(offset+48);
            m.m31 = src.getFloat(offset+52);
            m.m32 = src.getFloat(offset+56);
            m.m33 = src.getFloat(offset+60);
        }

        final void get(Matrix4x3f m, int offset, FloatBuffer src) {
            m.m00 = src.get(offset);
            m.m01 = src.get(offset+1);
            m.m02 = src.get(offset+2);
            m.m10 = src.get(offset+3);
            m.m11 = src.get(offset+4);
            m.m12 = src.get(offset+5);
            m.m20 = src.get(offset+6);
            m.m21 = src.get(offset+7);
            m.m22 = src.get(offset+8);
            m.m30 = src.get(offset+9);
            m.m31 = src.get(offset+10);
            m.m32 = src.get(offset+11);
        }

        final void get(Matrix4x3f m, int offset, ByteBuffer src) {
            m.m00 = src.getFloat(offset);
            m.m01 = src.getFloat(offset+4);
            m.m02 = src.getFloat(offset+8);
            m.m10 = src.getFloat(offset+12);
            m.m11 = src.getFloat(offset+16);
            m.m12 = src.getFloat(offset+20);
            m.m20 = src.getFloat(offset+24);
            m.m21 = src.getFloat(offset+28);
            m.m22 = src.getFloat(offset+32);
            m.m30 = src.getFloat(offset+36);
            m.m31 = src.getFloat(offset+40);
            m.m32 = src.getFloat(offset+44);
        }

        final void get(Matrix4d m, int offset, DoubleBuffer src) {
            m.m00 = src.get(offset);
            m.m01 = src.get(offset+1);
            m.m02 = src.get(offset+2);
            m.m03 = src.get(offset+3);
            m.m10 = src.get(offset+4);
            m.m11 = src.get(offset+5);
            m.m12 = src.get(offset+6);
            m.m13 = src.get(offset+7);
            m.m20 = src.get(offset+8);
            m.m21 = src.get(offset+9);
            m.m22 = src.get(offset+10);
            m.m23 = src.get(offset+11);
            m.m30 = src.get(offset+12);
            m.m31 = src.get(offset+13);
            m.m32 = src.get(offset+14);
            m.m33 = src.get(offset+15);
        }

        final void get(Matrix4d m, int offset, ByteBuffer src) {
            m.m00 = src.getDouble(offset);
            m.m01 = src.getDouble(offset+8);
            m.m02 = src.getDouble(offset+16);
            m.m03 = src.getDouble(offset+24);
            m.m10 = src.getDouble(offset+32);
            m.m11 = src.getDouble(offset+40);
            m.m12 = src.getDouble(offset+48);
            m.m13 = src.getDouble(offset+56);
            m.m20 = src.getDouble(offset+64);
            m.m21 = src.getDouble(offset+72);
            m.m22 = src.getDouble(offset+80);
            m.m23 = src.getDouble(offset+88);
            m.m30 = src.getDouble(offset+96);
            m.m31 = src.getDouble(offset+104);
            m.m32 = src.getDouble(offset+112);
            m.m33 = src.getDouble(offset+120);
        }

        final void get(Matrix4x3d m, int offset, DoubleBuffer src) {
            m.m00 = src.get(offset);
            m.m01 = src.get(offset+1);
            m.m02 = src.get(offset+2);
            m.m10 = src.get(offset+3);
            m.m11 = src.get(offset+4);
            m.m12 = src.get(offset+5);
            m.m20 = src.get(offset+6);
            m.m21 = src.get(offset+7);
            m.m22 = src.get(offset+8);
            m.m30 = src.get(offset+9);
            m.m31 = src.get(offset+10);
            m.m32 = src.get(offset+11);
        }

        final void get(Matrix4x3d m, int offset, ByteBuffer src) {
            m.m00 = src.getDouble(offset);
            m.m01 = src.getDouble(offset+8);
            m.m02 = src.getDouble(offset+16);
            m.m10 = src.getDouble(offset+24);
            m.m11 = src.getDouble(offset+32);
            m.m12 = src.getDouble(offset+40);
            m.m20 = src.getDouble(offset+48);
            m.m21 = src.getDouble(offset+56);
            m.m22 = src.getDouble(offset+64);
            m.m30 = src.getDouble(offset+72);
            m.m31 = src.getDouble(offset+80);
            m.m32 = src.getDouble(offset+88);
        }

        final void getf(Matrix4d m, int offset, FloatBuffer src) {
            m.m00 = src.get(offset);
            m.m01 = src.get(offset+1);
            m.m02 = src.get(offset+2);
            m.m03 = src.get(offset+3);
            m.m10 = src.get(offset+4);
            m.m11 = src.get(offset+5);
            m.m12 = src.get(offset+6);
            m.m13 = src.get(offset+7);
            m.m20 = src.get(offset+8);
            m.m21 = src.get(offset+9);
            m.m22 = src.get(offset+10);
            m.m23 = src.get(offset+11);
            m.m30 = src.get(offset+12);
            m.m31 = src.get(offset+13);
            m.m32 = src.get(offset+14);
            m.m33 = src.get(offset+15);
        }

        final void getf(Matrix4d m, int offset, ByteBuffer src) {
            m.m00 = src.getFloat(offset);
            m.m01 = src.getFloat(offset+4);
            m.m02 = src.getFloat(offset+8);
            m.m03 = src.getFloat(offset+12);
            m.m10 = src.getFloat(offset+16);
            m.m11 = src.getFloat(offset+20);
            m.m12 = src.getFloat(offset+24);
            m.m13 = src.getFloat(offset+28);
            m.m20 = src.getFloat(offset+32);
            m.m21 = src.getFloat(offset+36);
            m.m22 = src.getFloat(offset+40);
            m.m23 = src.getFloat(offset+44);
            m.m30 = src.getFloat(offset+48);
            m.m31 = src.getFloat(offset+52);
            m.m32 = src.getFloat(offset+56);
            m.m33 = src.getFloat(offset+60);
        }

        final void getf(Matrix4x3d m, int offset, FloatBuffer src) {
            m.m00 = src.get(offset);
            m.m01 = src.get(offset+1);
            m.m02 = src.get(offset+2);
            m.m10 = src.get(offset+3);
            m.m11 = src.get(offset+4);
            m.m12 = src.get(offset+5);
            m.m20 = src.get(offset+6);
            m.m21 = src.get(offset+7);
            m.m22 = src.get(offset+8);
            m.m30 = src.get(offset+9);
            m.m31 = src.get(offset+10);
            m.m32 = src.get(offset+11);
        }

        final void getf(Matrix4x3d m, int offset, ByteBuffer src) {
            m.m00 = src.getFloat(offset);
            m.m01 = src.getFloat(offset+4);
            m.m02 = src.getFloat(offset+8);
            m.m10 = src.getFloat(offset+12);
            m.m11 = src.getFloat(offset+16);
            m.m12 = src.getFloat(offset+20);
            m.m20 = src.getFloat(offset+24);
            m.m21 = src.getFloat(offset+28);
            m.m22 = src.getFloat(offset+32);
            m.m30 = src.getFloat(offset+36);
            m.m31 = src.getFloat(offset+40);
            m.m32 = src.getFloat(offset+44);
        }

        final void get(Matrix3f m, int offset, FloatBuffer src) {
            m.m00 = src.get(offset);
            m.m01 = src.get(offset+1);
            m.m02 = src.get(offset+2);
            m.m10 = src.get(offset+3);
            m.m11 = src.get(offset+4);
            m.m12 = src.get(offset+5);
            m.m20 = src.get(offset+6);
            m.m21 = src.get(offset+7);
            m.m22 = src.get(offset+8);
        }

        final void get(Matrix3f m, int offset, ByteBuffer src) {
            m.m00 = src.getFloat(offset);
            m.m01 = src.getFloat(offset+4);
            m.m02 = src.getFloat(offset+8);
            m.m10 = src.getFloat(offset+12);
            m.m11 = src.getFloat(offset+16);
            m.m12 = src.getFloat(offset+20);
            m.m20 = src.getFloat(offset+24);
            m.m21 = src.getFloat(offset+28);
            m.m22 = src.getFloat(offset+32);
        }

        final void get(Matrix3d m, int offset, DoubleBuffer src) {
            m.m00 = src.get(offset);
            m.m01 = src.get(offset+1);
            m.m02 = src.get(offset+2);
            m.m10 = src.get(offset+3);
            m.m11 = src.get(offset+4);
            m.m12 = src.get(offset+5);
            m.m20 = src.get(offset+6);
            m.m21 = src.get(offset+7);
            m.m22 = src.get(offset+8);
        }

        final void get(Matrix3d m, int offset, ByteBuffer src) {
            m.m00 = src.getDouble(offset);
            m.m01 = src.getDouble(offset+8);
            m.m02 = src.getDouble(offset+16);
            m.m10 = src.getDouble(offset+24);
            m.m11 = src.getDouble(offset+32);
            m.m12 = src.getDouble(offset+40);
            m.m20 = src.getDouble(offset+48);
            m.m21 = src.getDouble(offset+56);
            m.m22 = src.getDouble(offset+64);
        }

        final void getf(Matrix3d m, int offset, FloatBuffer src) {
            m.m00 = src.get(offset);
            m.m01 = src.get(offset+1);
            m.m02 = src.get(offset+2);
            m.m10 = src.get(offset+3);
            m.m11 = src.get(offset+4);
            m.m12 = src.get(offset+5);
            m.m20 = src.get(offset+6);
            m.m21 = src.get(offset+7);
            m.m22 = src.get(offset+8);
        }

        final void getf(Matrix3d m, int offset, ByteBuffer src) {
            m.m00 = src.getFloat(offset);
            m.m01 = src.getFloat(offset+4);
            m.m02 = src.getFloat(offset+8);
            m.m10 = src.getFloat(offset+12);
            m.m11 = src.getFloat(offset+16);
            m.m12 = src.getFloat(offset+20);
            m.m20 = src.getFloat(offset+24);
            m.m21 = src.getFloat(offset+28);
            m.m22 = src.getFloat(offset+32);
        }

        final void copy(Matrix4f src, Matrix4f dest) {
            dest.m00 = src.m00;
            dest.m01 = src.m01;
            dest.m02 = src.m02;
            dest.m03 = src.m03;
            dest.m10 = src.m10;
            dest.m11 = src.m11;
            dest.m12 = src.m12;
            dest.m13 = src.m13;
            dest.m20 = src.m20;
            dest.m21 = src.m21;
            dest.m22 = src.m22;
            dest.m23 = src.m23;
            dest.m30 = src.m30;
            dest.m31 = src.m31;
            dest.m32 = src.m32;
            dest.m33 = src.m33;
        }

        final void copy(Matrix3f src, Matrix4f dest) {
            dest.m00 = src.m00;
            dest.m01 = src.m01;
            dest.m02 = src.m02;
            dest.m03 = 0.0f;
            dest.m10 = src.m10;
            dest.m11 = src.m11;
            dest.m12 = src.m12;
            dest.m13 = 0.0f;
            dest.m20 = src.m20;
            dest.m21 = src.m21;
            dest.m22 = src.m22;
            dest.m23 = 0.0f;
            dest.m30 = 0.0f;
            dest.m31 = 0.0f;
            dest.m32 = 0.0f;
            dest.m33 = 1.0f;
        }

        final void copy(Matrix4f src, Matrix3f dest) {
            dest.m00 = src.m00;
            dest.m01 = src.m01;
            dest.m02 = src.m02;
            dest.m10 = src.m10;
            dest.m11 = src.m11;
            dest.m12 = src.m12;
            dest.m20 = src.m20;
            dest.m21 = src.m21;
            dest.m22 = src.m22;
        }

        final void copy(Matrix3f src, Matrix4x3f dest) {
            dest.m00 = src.m00;
            dest.m01 = src.m01;
            dest.m02 = src.m02;
            dest.m10 = src.m10;
            dest.m11 = src.m11;
            dest.m12 = src.m12;
            dest.m20 = src.m20;
            dest.m21 = src.m21;
            dest.m22 = src.m22;
            dest.m30 = 0.0f;
            dest.m31 = 0.0f;
            dest.m32 = 0.0f;
        }

        final void copy3x3(Matrix4f src, Matrix4f dest) {
            dest.m00 = src.m00;
            dest.m01 = src.m01;
            dest.m02 = src.m02;
            dest.m10 = src.m10;
            dest.m11 = src.m11;
            dest.m12 = src.m12;
            dest.m20 = src.m20;
            dest.m21 = src.m21;
            dest.m22 = src.m22;
        }

        final void copy4x3(Matrix4x3f src, Matrix4f dest) {
            dest.m00 = src.m00;
            dest.m01 = src.m01;
            dest.m02 = src.m02;
            dest.m10 = src.m10;
            dest.m11 = src.m11;
            dest.m12 = src.m12;
            dest.m20 = src.m20;
            dest.m21 = src.m21;
            dest.m22 = src.m22;
            dest.m30 = src.m30;
            dest.m31 = src.m31;
            dest.m32 = src.m32;
        }

        final void copy4x3(Matrix4f src, Matrix4f dest) {
            dest.m00 = src.m00;
            dest.m01 = src.m01;
            dest.m02 = src.m02;
            dest.m10 = src.m10;
            dest.m11 = src.m11;
            dest.m12 = src.m12;
            dest.m20 = src.m20;
            dest.m21 = src.m21;
            dest.m22 = src.m22;
            dest.m30 = src.m30;
            dest.m31 = src.m31;
            dest.m32 = src.m32;
        }

        final void copy(Matrix4f src, Matrix4x3f dest) {
            dest.m00 = src.m00;
            dest.m01 = src.m01;
            dest.m02 = src.m02;
            dest.m10 = src.m10;
            dest.m11 = src.m11;
            dest.m12 = src.m12;
            dest.m20 = src.m20;
            dest.m21 = src.m21;
            dest.m22 = src.m22;
            dest.m30 = src.m30;
            dest.m31 = src.m31;
            dest.m32 = src.m32;
        }

        final void copy(Matrix4x3f src, Matrix4x3f dest) {
            dest.m00 = src.m00;
            dest.m01 = src.m01;
            dest.m02 = src.m02;
            dest.m10 = src.m10;
            dest.m11 = src.m11;
            dest.m12 = src.m12;
            dest.m20 = src.m20;
            dest.m21 = src.m21;
            dest.m22 = src.m22;
            dest.m30 = src.m30;
            dest.m31 = src.m31;
            dest.m32 = src.m32;
        }

        final void copy(Matrix3f src, Matrix3f dest) {
            dest.m00 = src.m00;
            dest.m01 = src.m01;
            dest.m02 = src.m02;
            dest.m10 = src.m10;
            dest.m11 = src.m11;
            dest.m12 = src.m12;
            dest.m20 = src.m20;
            dest.m21 = src.m21;
            dest.m22 = src.m22;
        }

        final void identity(Matrix4f dest) {
            dest.m00 = 1.0f;
            dest.m01 = 0.0f;
            dest.m02 = 0.0f;
            dest.m03 = 0.0f;
            dest.m10 = 0.0f;
            dest.m11 = 1.0f;
            dest.m12 = 0.0f;
            dest.m13 = 0.0f;
            dest.m20 = 0.0f;
            dest.m21 = 0.0f;
            dest.m22 = 1.0f;
            dest.m23 = 0.0f;
            dest.m30 = 0.0f;
            dest.m31 = 0.0f;
            dest.m32 = 0.0f;
            dest.m33 = 1.0f;
        }

        final void identity(Matrix4x3f dest) {
            dest.m00 = 1.0f;
            dest.m01 = 0.0f;
            dest.m02 = 0.0f;
            dest.m10 = 0.0f;
            dest.m11 = 1.0f;
            dest.m12 = 0.0f;
            dest.m20 = 0.0f;
            dest.m21 = 0.0f;
            dest.m22 = 1.0f;
            dest.m30 = 0.0f;
            dest.m31 = 0.0f;
            dest.m32 = 0.0f;
        }

        final void identity(Matrix3f dest) {
            dest.m00 = 1.0f;
            dest.m01 = 0.0f;
            dest.m02 = 0.0f;
            dest.m10 = 0.0f;
            dest.m11 = 1.0f;
            dest.m12 = 0.0f;
            dest.m20 = 0.0f;
            dest.m21 = 0.0f;
            dest.m22 = 1.0f;
        }

        void swap(Matrix4f m1, Matrix4f m2) {
            float tmp;
            tmp = m1.m00; m1.m00 = m2.m00; m2.m00 = tmp;
            tmp = m1.m01; m1.m01 = m2.m01; m2.m01 = tmp;
            tmp = m1.m02; m1.m02 = m2.m02; m2.m02 = tmp;
            tmp = m1.m03; m1.m03 = m2.m03; m2.m03 = tmp;
            tmp = m1.m10; m1.m10 = m2.m10; m2.m10 = tmp;
            tmp = m1.m11; m1.m11 = m2.m11; m2.m11 = tmp;
            tmp = m1.m12; m1.m12 = m2.m12; m2.m12 = tmp;
            tmp = m1.m13; m1.m13 = m2.m13; m2.m13 = tmp;
            tmp = m1.m20; m1.m20 = m2.m20; m2.m20 = tmp;
            tmp = m1.m21; m1.m21 = m2.m21; m2.m21 = tmp;
            tmp = m1.m22; m1.m22 = m2.m22; m2.m22 = tmp;
            tmp = m1.m23; m1.m23 = m2.m23; m2.m23 = tmp;
            tmp = m1.m30; m1.m30 = m2.m30; m2.m30 = tmp;
            tmp = m1.m31; m1.m31 = m2.m31; m2.m31 = tmp;
            tmp = m1.m32; m1.m32 = m2.m32; m2.m32 = tmp;
            tmp = m1.m33; m1.m33 = m2.m33; m2.m33 = tmp;
        }

        void swap(Matrix4x3f m1, Matrix4x3f m2) {
            float tmp;
            tmp = m1.m00; m1.m00 = m2.m00; m2.m00 = tmp;
            tmp = m1.m01; m1.m01 = m2.m01; m2.m01 = tmp;
            tmp = m1.m02; m1.m02 = m2.m02; m2.m02 = tmp;
            tmp = m1.m10; m1.m10 = m2.m10; m2.m10 = tmp;
            tmp = m1.m11; m1.m11 = m2.m11; m2.m11 = tmp;
            tmp = m1.m12; m1.m12 = m2.m12; m2.m12 = tmp;
            tmp = m1.m20; m1.m20 = m2.m20; m2.m20 = tmp;
            tmp = m1.m21; m1.m21 = m2.m21; m2.m21 = tmp;
            tmp = m1.m22; m1.m22 = m2.m22; m2.m22 = tmp;
            tmp = m1.m30; m1.m30 = m2.m30; m2.m30 = tmp;
            tmp = m1.m31; m1.m31 = m2.m31; m2.m31 = tmp;
            tmp = m1.m32; m1.m32 = m2.m32; m2.m32 = tmp;
        }
        
        void swap(Matrix3f m1, Matrix3f m2) {
            float tmp;
            tmp = m1.m00; m1.m00 = m2.m00; m2.m00 = tmp;
            tmp = m1.m01; m1.m01 = m2.m01; m2.m01 = tmp;
            tmp = m1.m02; m1.m02 = m2.m02; m2.m02 = tmp;
            tmp = m1.m10; m1.m10 = m2.m10; m2.m10 = tmp;
            tmp = m1.m11; m1.m11 = m2.m11; m2.m11 = tmp;
            tmp = m1.m12; m1.m12 = m2.m12; m2.m12 = tmp;
            tmp = m1.m20; m1.m20 = m2.m20; m2.m20 = tmp;
            tmp = m1.m21; m1.m21 = m2.m21; m2.m21 = tmp;
            tmp = m1.m22; m1.m22 = m2.m22; m2.m22 = tmp;
        }

        final void zero(Matrix4f dest) {
            dest.m00 = 0.0f;
            dest.m01 = 0.0f;
            dest.m02 = 0.0f;
            dest.m03 = 0.0f;
            dest.m10 = 0.0f;
            dest.m11 = 0.0f;
            dest.m12 = 0.0f;
            dest.m13 = 0.0f;
            dest.m20 = 0.0f;
            dest.m21 = 0.0f;
            dest.m22 = 0.0f;
            dest.m23 = 0.0f;
            dest.m30 = 0.0f;
            dest.m31 = 0.0f;
            dest.m32 = 0.0f;
            dest.m33 = 0.0f;
        }

        final void zero(Matrix4x3f dest) {
            dest.m00 = 0.0f;
            dest.m01 = 0.0f;
            dest.m02 = 0.0f;
            dest.m10 = 0.0f;
            dest.m11 = 0.0f;
            dest.m12 = 0.0f;
            dest.m20 = 0.0f;
            dest.m21 = 0.0f;
            dest.m22 = 0.0f;
            dest.m30 = 0.0f;
            dest.m31 = 0.0f;
            dest.m32 = 0.0f;
        }

        final void zero(Matrix3f dest) {
            dest.m00 = 0.0f;
            dest.m01 = 0.0f;
            dest.m02 = 0.0f;
            dest.m10 = 0.0f;
            dest.m11 = 0.0f;
            dest.m12 = 0.0f;
            dest.m20 = 0.0f;
            dest.m21 = 0.0f;
            dest.m22 = 0.0f;
        }

        final void identity(DualQuaternionf dq) {
            dq.rx = 0.0f;
            dq.ry = 0.0f;
            dq.rz = 0.0f;
            dq.rw = 1.0f;
            dq.tx = 0.0f;
            dq.ty = 0.0f;
            dq.tz = 0.0f;
            dq.tw = 0.0f;
        }

        final void zero(DualQuaternionf dq) {
            dq.rx = 0.0f;
            dq.ry = 0.0f;
            dq.rz = 0.0f;
            dq.rw = 0.0f;
            dq.tx = 0.0f;
            dq.ty = 0.0f;
            dq.tz = 0.0f;
            dq.tw = 0.0f;
        }

        final void putMatrix4f(DualQuaternionf dq, int position, ByteBuffer dest) {
            float dqx = dq.rx + dq.rx;
            float dqy = dq.ry + dq.ry;
            float dqz = dq.rz + dq.rz;
            float q00 = dqx * dq.rx;
            float q11 = dqy * dq.ry;
            float q22 = dqz * dq.rz;
            float q01 = dqx * dq.ry;
            float q02 = dqx * dq.rz;
            float q03 = dqx * dq.rw;
            float q12 = dqy * dq.rz;
            float q13 = dqy * dq.rw;
            float q23 = dqz * dq.rw;
            dest.putFloat(position, 1.0f - q11 - q22);
            dest.putFloat(position + 4, q01 + q23);
            dest.putFloat(position + 8, q02 - q13);
            dest.putFloat(position + 12, 0.0f);
            dest.putFloat(position + 16, q01 - q23);
            dest.putFloat(position + 20, 1.0f - q22 - q00);
            dest.putFloat(position + 24, q12 + q03);
            dest.putFloat(position + 28, 0.0f);
            dest.putFloat(position + 32, q02 + q13);
            dest.putFloat(position + 36, q12 - q03);
            dest.putFloat(position + 40, 1.0f - q11 - q00);
            dest.putFloat(position + 44, 0.0f);
            dest.putFloat(position + 48, 2.0f * (dq.rw*dq.tx - dq.rx*dq.tw + dq.ry*dq.tz - dq.rz*dq.ty));
            dest.putFloat(position + 52, 2.0f * (dq.rw*dq.ty - dq.ry*dq.tw - dq.rx*dq.tz + dq.rz*dq.tx));
            dest.putFloat(position + 56, 2.0f * (dq.rw*dq.tz - dq.rz*dq.tw + dq.rx*dq.ty - dq.ry*dq.tx));
            dest.putFloat(position + 60, 1.0f);
        }

        final void putMatrix4f(DualQuaternionf dq, int position, FloatBuffer dest) {
            float dqx = dq.rx + dq.rx;
            float dqy = dq.ry + dq.ry;
            float dqz = dq.rz + dq.rz;
            float q00 = dqx * dq.rx;
            float q11 = dqy * dq.ry;
            float q22 = dqz * dq.rz;
            float q01 = dqx * dq.ry;
            float q02 = dqx * dq.rz;
            float q03 = dqx * dq.rw;
            float q12 = dqy * dq.rz;
            float q13 = dqy * dq.rw;
            float q23 = dqz * dq.rw;
            dest.put(position, 1.0f - q11 - q22);
            dest.put(position + 1, q01 + q23);
            dest.put(position + 2, q02 - q13);
            dest.put(position + 3, 0.0f);
            dest.put(position + 4, q01 - q23);
            dest.put(position + 5, 1.0f - q22 - q00);
            dest.put(position + 6, q12 + q03);
            dest.put(position + 7, 0.0f);
            dest.put(position + 8, q02 + q13);
            dest.put(position + 9, q12 - q03);
            dest.put(position + 10, 1.0f - q11 - q00);
            dest.put(position + 11, 0.0f);
            dest.put(position + 12, 2.0f * (dq.rw*dq.tx - dq.rx*dq.tw + dq.ry*dq.tz - dq.rz*dq.ty));
            dest.put(position + 13, 2.0f * (dq.rw*dq.ty - dq.ry*dq.tw - dq.rx*dq.tz + dq.rz*dq.tx));
            dest.put(position + 14, 2.0f * (dq.rw*dq.tz - dq.rz*dq.tw + dq.rx*dq.ty - dq.ry*dq.tx));
            dest.put(position + 15, 1.0f);
        }

        final void putMatrix4x3f(DualQuaternionf dq, int position, ByteBuffer dest) {
            float dqx = dq.rx + dq.rx;
            float dqy = dq.ry + dq.ry;
            float dqz = dq.rz + dq.rz;
            float q00 = dqx * dq.rx;
            float q11 = dqy * dq.ry;
            float q22 = dqz * dq.rz;
            float q01 = dqx * dq.ry;
            float q02 = dqx * dq.rz;
            float q03 = dqx * dq.rw;
            float q12 = dqy * dq.rz;
            float q13 = dqy * dq.rw;
            float q23 = dqz * dq.rw;
            dest.putFloat(position, 1.0f - q11 - q22);
            dest.putFloat(position + 4, q01 + q23);
            dest.putFloat(position + 8, q02 - q13);
            dest.putFloat(position + 12, q01 - q23);
            dest.putFloat(position + 16, 1.0f - q22 - q00);
            dest.putFloat(position + 20, q12 + q03);
            dest.putFloat(position + 24, q02 + q13);
            dest.putFloat(position + 28, q12 - q03);
            dest.putFloat(position + 32, 1.0f - q11 - q00);
            dest.putFloat(position + 36, 2.0f * (dq.rw*dq.tx - dq.rx*dq.tw + dq.ry*dq.tz - dq.rz*dq.ty));
            dest.putFloat(position + 40, 2.0f * (dq.rw*dq.ty - dq.ry*dq.tw - dq.rx*dq.tz + dq.rz*dq.tx));
            dest.putFloat(position + 44, 2.0f * (dq.rw*dq.tz - dq.rz*dq.tw + dq.rx*dq.ty - dq.ry*dq.tx));
        }

        final void putMatrix4x3f(DualQuaternionf dq, int position, FloatBuffer dest) {
            float dqx = dq.rx + dq.rx;
            float dqy = dq.ry + dq.ry;
            float dqz = dq.rz + dq.rz;
            float q00 = dqx * dq.rx;
            float q11 = dqy * dq.ry;
            float q22 = dqz * dq.rz;
            float q01 = dqx * dq.ry;
            float q02 = dqx * dq.rz;
            float q03 = dqx * dq.rw;
            float q12 = dqy * dq.rz;
            float q13 = dqy * dq.rw;
            float q23 = dqz * dq.rw;
            dest.put(position, 1.0f - q11 - q22);
            dest.put(position + 1, q01 + q23);
            dest.put(position + 2, q02 - q13);
            dest.put(position + 3, q01 - q23);
            dest.put(position + 4, 1.0f - q22 - q00);
            dest.put(position + 5, q12 + q03);
            dest.put(position + 6, q02 + q13);
            dest.put(position + 7, q12 - q03);
            dest.put(position + 8, 1.0f - q11 - q00);
            dest.put(position + 9, 2.0f * (dq.rw*dq.tx - dq.rx*dq.tw + dq.ry*dq.tz - dq.rz*dq.ty));
            dest.put(position + 10, 2.0f * (dq.rw*dq.ty - dq.ry*dq.tw - dq.rx*dq.tz + dq.rz*dq.tx));
            dest.put(position + 11, 2.0f * (dq.rw*dq.tz - dq.rz*dq.tw + dq.rx*dq.ty - dq.ry*dq.tx));
        }

        final void putMatrix3f(Quaternionf q, int position, ByteBuffer dest) {
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
            dest.putFloat(position, 1.0f - q11 - q22);
            dest.putFloat(position + 4, q01 + q23);
            dest.putFloat(position + 8, q02 - q13);
            dest.putFloat(position + 12, q01 - q23);
            dest.putFloat(position + 16, 1.0f - q22 - q00);
            dest.putFloat(position + 20, q12 + q03);
            dest.putFloat(position + 24, q02 + q13);
            dest.putFloat(position + 28, q12 - q03);
            dest.putFloat(position + 32, 1.0f - q11 - q00);
        }

        final void putMatrix3f(Quaternionf q, int position, FloatBuffer dest) {
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
            dest.put(position, 1.0f - q11 - q22);
            dest.put(position + 1, q01 + q23);
            dest.put(position + 2, q02 - q13);
            dest.put(position + 3, q01 - q23);
            dest.put(position + 4, 1.0f - q22 - q00);
            dest.put(position + 5, q12 + q03);
            dest.put(position + 6, q02 + q13);
            dest.put(position + 7, q12 - q03);
            dest.put(position + 8, 1.0f - q11 - q00);
        }

        final void copy(DualQuaternionf src, DualQuaternionf dest) {
            dest.rx = src.rx;
            dest.ry = src.ry;
            dest.rz = src.rz;
            dest.rw = src.rw;
            dest.tx = src.tx;
            dest.ty = src.ty;
            dest.tz = src.tz;
            dest.tw = src.tw;
        }
    }

    static final class MemUtilUnsafe extends MemUtil {
        private static final sun.misc.Unsafe UNSAFE;
        private static final long ADDRESS;
        private static final long Matrix3f_m00;
        private static final long Matrix4f_m00;
        private static final long Matrix4x3f_m00;
        private static final long DualQuaternionf_rx;

        static {
            UNSAFE = getUnsafeInstance();
            try {
                ADDRESS = UNSAFE.objectFieldOffset(getDeclaredField(Buffer.class, "address")); //$NON-NLS-1$
                Matrix4f_m00 = checkMatrix4f();
                Matrix4x3f_m00 = checkMatrix4x3f();
                Matrix3f_m00 = checkMatrix3f();
                DualQuaternionf_rx = checkDualQuaternionf();
                // Check if we can use object field offset/address put/get methods
                sun.misc.Unsafe.class.getDeclaredMethod("getLong", new Class[] {Object.class, long.class});
                sun.misc.Unsafe.class.getDeclaredMethod("putOrderedLong", new Class[] {Object.class, long.class, long.class});
            } catch (NoSuchFieldException e) {
                throw new UnsupportedOperationException();
            } catch (NoSuchMethodException e) {
                throw new UnsupportedOperationException();
            }
        }

        private static long checkMatrix4f() throws NoSuchFieldException, SecurityException {
            Field f = Matrix4f.class.getDeclaredField("m00");
            long Matrix4f_m00 = UNSAFE.objectFieldOffset(f);
            // Validate expected field offsets
            for (int i = 1; i < 16; i++) {
                int c = i >>> 2;
                int r = i & 3;
                f = Matrix4f.class.getDeclaredField("m" + c + r);
                long offset = UNSAFE.objectFieldOffset(f);
                if (offset != Matrix4f_m00 + (i << 2))
                    throw new UnsupportedOperationException();
            }
            return Matrix4f_m00;
        }

        private static long checkMatrix4x3f() throws NoSuchFieldException, SecurityException {
            Field f = Matrix4x3f.class.getDeclaredField("m00");
            long Matrix4x3f_m00 = UNSAFE.objectFieldOffset(f);
            // Validate expected field offsets
            for (int i = 1; i < 12; i++) {
                int c = i / 3;
                int r = i % 3;
                f = Matrix4x3f.class.getDeclaredField("m" + c + r);
                long offset = UNSAFE.objectFieldOffset(f);
                if (offset != Matrix4x3f_m00 + (i << 2))
                    throw new UnsupportedOperationException();
            }
            return Matrix4x3f_m00;
        }

        private static long checkMatrix3f() throws NoSuchFieldException, SecurityException {
            Field f = Matrix3f.class.getDeclaredField("m00");
            long Matrix3f_m00 = UNSAFE.objectFieldOffset(f);
            // Validate expected field offsets
            for (int i = 1; i < 9; i++) {
                int c = i / 3;
                int r = i % 3;
                f = Matrix3f.class.getDeclaredField("m" + c + r);
                long offset = UNSAFE.objectFieldOffset(f);
                if (offset != Matrix3f_m00 + (i << 2))
                    throw new UnsupportedOperationException();
            }
            return Matrix3f_m00;
        }

        private static long checkDualQuaternionf() throws NoSuchFieldException, SecurityException {
            Field f = DualQuaternionf.class.getDeclaredField("rx");
            long DualQuaternionf_rx = UNSAFE.objectFieldOffset(f);
            // Validate expected field offsets
            String[] fields = {"ry", "rz", "rw", "tx", "ty", "tz", "tw"};
            for (int i = 0; i < 7; i++) {
                f = DualQuaternionf.class.getDeclaredField(fields[i]);
                long offset = UNSAFE.objectFieldOffset(f);
                if (offset != DualQuaternionf_rx + ((i+1) << 2))
                    throw new UnsupportedOperationException();
            }
            return DualQuaternionf_rx;
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
            for (int i = 0; i < 8; i++) {
                UNSAFE.putOrderedLong(null, destAddr + (i << 3), UNSAFE.getLong(m, Matrix4f_m00 + (i << 3)));
            }
        }

        private final void put(Matrix4x3f m, long destAddr) {
            for (int i = 0; i < 6; i++) {
                UNSAFE.putOrderedLong(null, destAddr + (i << 3), UNSAFE.getLong(m, Matrix4x3f_m00 + (i << 3)));
            }
        }

        private final void put4x4(Matrix4x3f m, long destAddr) {
            for (int i = 0; i < 4; i++) {
                UNSAFE.putOrderedLong(null, destAddr + (i << 4), UNSAFE.getLong(m, Matrix4x3f_m00 + 12 * i));
            }
            for (int i = 0; i < 3; i++) {
                memPutFloat(destAddr + 12 + (i << 4), 0.0f);
            }
            memPutFloat(destAddr + 8,  m.m02);
            memPutFloat(destAddr + 24, m.m12);
            memPutFloat(destAddr + 40, m.m22);
            memPutFloat(destAddr + 56, m.m32);
            memPutFloat(destAddr + 60, 1.0f);
        }

        private final void putTransposed(Matrix4f m, long destAddr) {
            memPutFloat(destAddr,      m.m00);
            memPutFloat(destAddr + 4,  m.m10);
            memPutFloat(destAddr + 8,  m.m20);
            memPutFloat(destAddr + 12, m.m30);
            memPutFloat(destAddr + 16, m.m01);
            memPutFloat(destAddr + 20, m.m11);
            memPutFloat(destAddr + 24, m.m21);
            memPutFloat(destAddr + 28, m.m31);
            memPutFloat(destAddr + 32, m.m02);
            memPutFloat(destAddr + 36, m.m12);
            memPutFloat(destAddr + 40, m.m22);
            memPutFloat(destAddr + 44, m.m32);
            memPutFloat(destAddr + 48, m.m03);
            memPutFloat(destAddr + 52, m.m13);
            memPutFloat(destAddr + 56, m.m23);
            memPutFloat(destAddr + 60, m.m33);
        }

        private final void put4x3Transposed(Matrix4f m, long destAddr) {
            memPutFloat(destAddr,      m.m00);
            memPutFloat(destAddr + 4,  m.m10);
            memPutFloat(destAddr + 8,  m.m20);
            memPutFloat(destAddr + 12, m.m30);
            memPutFloat(destAddr + 16, m.m01);
            memPutFloat(destAddr + 20, m.m11);
            memPutFloat(destAddr + 24, m.m21);
            memPutFloat(destAddr + 28, m.m31);
            memPutFloat(destAddr + 32, m.m02);
            memPutFloat(destAddr + 36, m.m12);
            memPutFloat(destAddr + 40, m.m22);
            memPutFloat(destAddr + 44, m.m32);
        }

        private final void putTransposed(Matrix4x3f m, long destAddr) {
            memPutFloat(destAddr,      m.m00);
            memPutFloat(destAddr + 4,  m.m10);
            memPutFloat(destAddr + 8,  m.m20);
            memPutFloat(destAddr + 12, m.m30);
            memPutFloat(destAddr + 16, m.m01);
            memPutFloat(destAddr + 20, m.m11);
            memPutFloat(destAddr + 24, m.m21);
            memPutFloat(destAddr + 28, m.m31);
            memPutFloat(destAddr + 32, m.m02);
            memPutFloat(destAddr + 36, m.m12);
            memPutFloat(destAddr + 40, m.m22);
            memPutFloat(destAddr + 44, m.m32);
        }

        private final void put(Matrix4d m, long destAddr) {
            memPutDouble(destAddr,       m.m00);
            memPutDouble(destAddr + 8,   m.m01);
            memPutDouble(destAddr + 16,  m.m02);
            memPutDouble(destAddr + 24,  m.m03);
            memPutDouble(destAddr + 32,  m.m10);
            memPutDouble(destAddr + 40,  m.m11);
            memPutDouble(destAddr + 48,  m.m12);
            memPutDouble(destAddr + 56,  m.m13);
            memPutDouble(destAddr + 64,  m.m20);
            memPutDouble(destAddr + 72,  m.m21);
            memPutDouble(destAddr + 80,  m.m22);
            memPutDouble(destAddr + 88,  m.m23);
            memPutDouble(destAddr + 96,  m.m30);
            memPutDouble(destAddr + 104, m.m31);
            memPutDouble(destAddr + 112, m.m32);
            memPutDouble(destAddr + 120, m.m33);
        }

        private final void put(Matrix4x3d m, long destAddr) {
            memPutDouble(destAddr,      m.m00);
            memPutDouble(destAddr + 8,  m.m01);
            memPutDouble(destAddr + 16, m.m02);
            memPutDouble(destAddr + 24, m.m10);
            memPutDouble(destAddr + 32, m.m11);
            memPutDouble(destAddr + 40, m.m12);
            memPutDouble(destAddr + 48, m.m20);
            memPutDouble(destAddr + 56, m.m21);
            memPutDouble(destAddr + 64, m.m22);
            memPutDouble(destAddr + 72, m.m30);
            memPutDouble(destAddr + 80, m.m31);
            memPutDouble(destAddr + 88, m.m32);
        }

        private final void putTransposed(Matrix4d m, long destAddr) {
            memPutDouble(destAddr,       m.m00);
            memPutDouble(destAddr + 8,   m.m10);
            memPutDouble(destAddr + 16,  m.m20);
            memPutDouble(destAddr + 24,  m.m30);
            memPutDouble(destAddr + 32,  m.m01);
            memPutDouble(destAddr + 40,  m.m11);
            memPutDouble(destAddr + 48,  m.m21);
            memPutDouble(destAddr + 56,  m.m31);
            memPutDouble(destAddr + 64,  m.m02);
            memPutDouble(destAddr + 72,  m.m12);
            memPutDouble(destAddr + 80,  m.m22);
            memPutDouble(destAddr + 88,  m.m32);
            memPutDouble(destAddr + 96,  m.m03);
            memPutDouble(destAddr + 104, m.m13);
            memPutDouble(destAddr + 112, m.m23);
            memPutDouble(destAddr + 120, m.m33);
        }

        private final void putfTransposed(Matrix4d m, long destAddr) {
            memPutFloat(destAddr,      (float)m.m00);
            memPutFloat(destAddr + 4,  (float)m.m10);
            memPutFloat(destAddr + 8,  (float)m.m20);
            memPutFloat(destAddr + 12, (float)m.m30);
            memPutFloat(destAddr + 16, (float)m.m01);
            memPutFloat(destAddr + 20, (float)m.m11);
            memPutFloat(destAddr + 24, (float)m.m21);
            memPutFloat(destAddr + 28, (float)m.m31);
            memPutFloat(destAddr + 32, (float)m.m02);
            memPutFloat(destAddr + 36, (float)m.m12);
            memPutFloat(destAddr + 40, (float)m.m22);
            memPutFloat(destAddr + 44, (float)m.m32);
            memPutFloat(destAddr + 48, (float)m.m03);
            memPutFloat(destAddr + 52, (float)m.m13);
            memPutFloat(destAddr + 56, (float)m.m23);
            memPutFloat(destAddr + 60, (float)m.m33);
        }

        private final void put4x3Transposed(Matrix4d m, long destAddr) {
            memPutDouble(destAddr,      m.m00);
            memPutDouble(destAddr + 8,  m.m10);
            memPutDouble(destAddr + 16, m.m20);
            memPutDouble(destAddr + 24, m.m30);
            memPutDouble(destAddr + 32, m.m01);
            memPutDouble(destAddr + 40, m.m11);
            memPutDouble(destAddr + 48, m.m21);
            memPutDouble(destAddr + 56, m.m31);
            memPutDouble(destAddr + 64, m.m02);
            memPutDouble(destAddr + 72, m.m12);
            memPutDouble(destAddr + 80, m.m22);
            memPutDouble(destAddr + 88, m.m32);
        }

        private final void putTransposed(Matrix4x3d m, long destAddr) {
            memPutDouble(destAddr,      m.m00);
            memPutDouble(destAddr + 8,  m.m10);
            memPutDouble(destAddr + 16, m.m20);
            memPutDouble(destAddr + 24, m.m30);
            memPutDouble(destAddr + 32, m.m01);
            memPutDouble(destAddr + 40, m.m11);
            memPutDouble(destAddr + 48, m.m21);
            memPutDouble(destAddr + 56, m.m31);
            memPutDouble(destAddr + 64, m.m02);
            memPutDouble(destAddr + 72, m.m12);
            memPutDouble(destAddr + 80, m.m22);
            memPutDouble(destAddr + 88, m.m32);
        }

        private final void putfTransposed(Matrix4x3d m, long destAddr) {
            memPutFloat(destAddr,      (float)m.m00);
            memPutFloat(destAddr + 4,  (float)m.m10);
            memPutFloat(destAddr + 8,  (float)m.m20);
            memPutFloat(destAddr + 12, (float)m.m30);
            memPutFloat(destAddr + 16, (float)m.m01);
            memPutFloat(destAddr + 20, (float)m.m11);
            memPutFloat(destAddr + 24, (float)m.m21);
            memPutFloat(destAddr + 28, (float)m.m31);
            memPutFloat(destAddr + 32, (float)m.m02);
            memPutFloat(destAddr + 36, (float)m.m12);
            memPutFloat(destAddr + 40, (float)m.m22);
            memPutFloat(destAddr + 44, (float)m.m32);
        }

        private final void putf(Matrix4d m, long destAddr) {
            memPutFloat(destAddr,      (float)m.m00);
            memPutFloat(destAddr + 4,  (float)m.m01);
            memPutFloat(destAddr + 8,  (float)m.m02);
            memPutFloat(destAddr + 12, (float)m.m03);
            memPutFloat(destAddr + 16, (float)m.m10);
            memPutFloat(destAddr + 20, (float)m.m11);
            memPutFloat(destAddr + 24, (float)m.m12);
            memPutFloat(destAddr + 28, (float)m.m13);
            memPutFloat(destAddr + 32, (float)m.m20);
            memPutFloat(destAddr + 36, (float)m.m21);
            memPutFloat(destAddr + 40, (float)m.m22);
            memPutFloat(destAddr + 44, (float)m.m23);
            memPutFloat(destAddr + 48, (float)m.m30);
            memPutFloat(destAddr + 52, (float)m.m31);
            memPutFloat(destAddr + 56, (float)m.m32);
            memPutFloat(destAddr + 60, (float)m.m33);
        }

        private final void putf(Matrix4x3d m, long destAddr) {
            memPutFloat(destAddr,      (float)m.m00);
            memPutFloat(destAddr + 4,  (float)m.m01);
            memPutFloat(destAddr + 8,  (float)m.m02);
            memPutFloat(destAddr + 12, (float)m.m10);
            memPutFloat(destAddr + 16, (float)m.m11);
            memPutFloat(destAddr + 20, (float)m.m12);
            memPutFloat(destAddr + 24, (float)m.m20);
            memPutFloat(destAddr + 28, (float)m.m21);
            memPutFloat(destAddr + 32, (float)m.m22);
            memPutFloat(destAddr + 36, (float)m.m30);
            memPutFloat(destAddr + 40, (float)m.m31);
            memPutFloat(destAddr + 44, (float)m.m32);
        }

        private final void put(Matrix3f m, long destAddr) {
            for (int i = 0; i < 4; i++) {
                UNSAFE.putOrderedLong(null, destAddr + (i << 3), UNSAFE.getLong(m, Matrix3f_m00 + (i << 3)));
            }
            memPutFloat(destAddr + 32, m.m22);
        }

        private final void put(Matrix3d m, long destAddr) {
            memPutDouble(destAddr,      m.m00);
            memPutDouble(destAddr + 8,  m.m01);
            memPutDouble(destAddr + 16, m.m02);
            memPutDouble(destAddr + 24, m.m10);
            memPutDouble(destAddr + 32, m.m11);
            memPutDouble(destAddr + 40, m.m12);
            memPutDouble(destAddr + 48, m.m20);
            memPutDouble(destAddr + 56, m.m21);
            memPutDouble(destAddr + 64, m.m22);
        }

        private final void putf(Matrix3d m, long destAddr) {
            memPutFloat(destAddr,      (float)m.m00);
            memPutFloat(destAddr + 4,  (float)m.m01);
            memPutFloat(destAddr + 8,  (float)m.m02);
            memPutFloat(destAddr + 12, (float)m.m10);
            memPutFloat(destAddr + 16, (float)m.m11);
            memPutFloat(destAddr + 20, (float)m.m12);
            memPutFloat(destAddr + 24, (float)m.m20);
            memPutFloat(destAddr + 28, (float)m.m21);
            memPutFloat(destAddr + 32, (float)m.m22);
        }

        private final void get(Matrix4f m, long srcAddr) {
            for (int i = 0; i < 8; i++) {
                UNSAFE.putOrderedLong(m, Matrix4f_m00 + (i << 3), UNSAFE.getLong(srcAddr + (i << 3)));
            }
        }

        private final void get(Matrix4x3f m, long srcAddr) {
            for (int i = 0; i < 6; i++) {
                UNSAFE.putOrderedLong(m, Matrix4x3f_m00 + (i << 3), UNSAFE.getLong(srcAddr + (i << 3)));
            }
        }

        private final void get(Matrix4d m, long srcAddr) {
            m.m00 = memGetDouble(srcAddr);
            m.m01 = memGetDouble(srcAddr+8);
            m.m02 = memGetDouble(srcAddr+16);
            m.m03 = memGetDouble(srcAddr+24);
            m.m10 = memGetDouble(srcAddr+32);
            m.m11 = memGetDouble(srcAddr+40);
            m.m12 = memGetDouble(srcAddr+48);
            m.m13 = memGetDouble(srcAddr+56);
            m.m20 = memGetDouble(srcAddr+64);
            m.m21 = memGetDouble(srcAddr+72);
            m.m22 = memGetDouble(srcAddr+80);
            m.m23 = memGetDouble(srcAddr+88);
            m.m30 = memGetDouble(srcAddr+96);
            m.m31 = memGetDouble(srcAddr+104);
            m.m32 = memGetDouble(srcAddr+112);
            m.m33 = memGetDouble(srcAddr+120);
        }

        private final void get(Matrix4x3d m, long srcAddr) {
            m.m00 = memGetDouble(srcAddr);
            m.m01 = memGetDouble(srcAddr+8);
            m.m02 = memGetDouble(srcAddr+16);
            m.m10 = memGetDouble(srcAddr+24);
            m.m11 = memGetDouble(srcAddr+32);
            m.m12 = memGetDouble(srcAddr+40);
            m.m20 = memGetDouble(srcAddr+48);
            m.m21 = memGetDouble(srcAddr+56);
            m.m22 = memGetDouble(srcAddr+64);
            m.m30 = memGetDouble(srcAddr+72);
            m.m31 = memGetDouble(srcAddr+80);
            m.m32 = memGetDouble(srcAddr+88);
        }

        private final void getf(Matrix4d m, long srcAddr) {
            m.m00 = memGetFloat(srcAddr);
            m.m01 = memGetFloat(srcAddr+4);
            m.m02 = memGetFloat(srcAddr+8);
            m.m03 = memGetFloat(srcAddr+12);
            m.m10 = memGetFloat(srcAddr+16);
            m.m11 = memGetFloat(srcAddr+20);
            m.m12 = memGetFloat(srcAddr+24);
            m.m13 = memGetFloat(srcAddr+28);
            m.m20 = memGetFloat(srcAddr+32);
            m.m21 = memGetFloat(srcAddr+36);
            m.m22 = memGetFloat(srcAddr+40);
            m.m23 = memGetFloat(srcAddr+44);
            m.m30 = memGetFloat(srcAddr+48);
            m.m31 = memGetFloat(srcAddr+52);
            m.m32 = memGetFloat(srcAddr+56);
            m.m33 = memGetFloat(srcAddr+60);
        }

        private final void getf(Matrix4x3d m, long srcAddr) {
            m.m00 = memGetFloat(srcAddr);
            m.m01 = memGetFloat(srcAddr+4);
            m.m02 = memGetFloat(srcAddr+8);
            m.m10 = memGetFloat(srcAddr+12);
            m.m11 = memGetFloat(srcAddr+16);
            m.m12 = memGetFloat(srcAddr+20);
            m.m20 = memGetFloat(srcAddr+24);
            m.m21 = memGetFloat(srcAddr+28);
            m.m22 = memGetFloat(srcAddr+32);
            m.m30 = memGetFloat(srcAddr+36);
            m.m31 = memGetFloat(srcAddr+40);
            m.m32 = memGetFloat(srcAddr+44);
        }

        private final void get(Matrix3f m, long srcAddr) {
            for (int i = 0; i < 4; i++) {
                UNSAFE.putOrderedLong(m, Matrix3f_m00 + (i << 3), UNSAFE.getLong(null, srcAddr + (i << 3)));
            }
            m.m22 = memGetFloat(srcAddr+32);
        }

        private final void get(Matrix3d m, long srcAddr) {
            m.m00 = memGetDouble(srcAddr);
            m.m01 = memGetDouble(srcAddr+8);
            m.m02 = memGetDouble(srcAddr+16);
            m.m10 = memGetDouble(srcAddr+24);
            m.m11 = memGetDouble(srcAddr+32);
            m.m12 = memGetDouble(srcAddr+40);
            m.m20 = memGetDouble(srcAddr+48);
            m.m21 = memGetDouble(srcAddr+56);
            m.m22 = memGetDouble(srcAddr+64);
        }

        private final void getf(Matrix3d m, long srcAddr) {
            m.m00 = memGetFloat(srcAddr);
            m.m01 = memGetFloat(srcAddr+4);
            m.m02 = memGetFloat(srcAddr+8);
            m.m10 = memGetFloat(srcAddr+12);
            m.m11 = memGetFloat(srcAddr+16);
            m.m12 = memGetFloat(srcAddr+20);
            m.m20 = memGetFloat(srcAddr+24);
            m.m21 = memGetFloat(srcAddr+28);
            m.m22 = memGetFloat(srcAddr+32);
        }

        final void copy(Matrix4f src, Matrix4f dest) {
            for (int i = 0; i < 8; i++) {
                UNSAFE.putOrderedLong(dest, Matrix4f_m00 + (i << 3), UNSAFE.getLong(src, Matrix4f_m00 + (i << 3)));
            }
        }

        final void copy(Matrix3f src, Matrix4f dest) {
            for (int i = 0; i < 3; i++) {
                UNSAFE.putOrderedLong(dest, Matrix4f_m00 + (i << 4), UNSAFE.getLong(src, Matrix3f_m00 + 12 * i));
            }
            UNSAFE.putOrderedLong(dest, Matrix4f_m00 + 48, 0L);
            UNSAFE.putOrderedLong(dest, Matrix4f_m00 + 56, 0x3F80000000000000L);
            dest.m02 = src.m02;
            dest.m03 = 0.0f;
            dest.m12 = src.m12;
            dest.m13 = 0.0f;
            dest.m22 = src.m22;
            dest.m23 = 0.0f;
        }

        final void copy(Matrix4f src, Matrix3f dest) {
            for (int i = 0; i < 3; i++) {
                UNSAFE.putOrderedLong(dest, Matrix3f_m00 + 12 * i, UNSAFE.getLong(src, Matrix4f_m00 + (i << 4)));
            }
            dest.m02 = src.m02;
            dest.m12 = src.m12;
            dest.m22 = src.m22;
        }

        final void copy(Matrix3f src, Matrix4x3f dest) {
            for (int i = 0; i < 3; i++) {
                UNSAFE.putOrderedLong(dest, Matrix4x3f_m00 + 12 * i, UNSAFE.getLong(src, Matrix3f_m00 + 12 * i));
            }
            UNSAFE.putOrderedLong(dest, Matrix4x3f_m00 + 36, 0L);
            dest.m02 = src.m02;
            dest.m12 = src.m12;
            dest.m22 = src.m22;
            dest.m32 = 0.0f;
        }

        final void copy3x3(Matrix4f src, Matrix4f dest) {
            for (int i = 0; i < 3; i++) {
                UNSAFE.putOrderedLong(dest, Matrix4f_m00 + (i << 4), UNSAFE.getLong(src, Matrix4f_m00 + (i << 4)));
            }
            dest.m02 = src.m02;
            dest.m12 = src.m12;
            dest.m22 = src.m22;
        }

        final void copy4x3(Matrix4x3f src, Matrix4f dest) {
            for (int i = 0; i < 4; i++) {
                UNSAFE.putOrderedLong(dest, Matrix4f_m00 + (i << 4), UNSAFE.getLong(src, Matrix4x3f_m00 + 12 * i));
            }
            dest.m02 = src.m02;
            dest.m12 = src.m12;
            dest.m22 = src.m22;
            dest.m32 = src.m32;
        }

        final void copy4x3(Matrix4f src, Matrix4f dest) {
            for (int i = 0; i < 4; i++) {
                UNSAFE.putOrderedLong(dest, Matrix4f_m00 + (i << 4), UNSAFE.getLong(src, Matrix4f_m00 + (i << 4)));
            }
            dest.m02 = src.m02;
            dest.m12 = src.m12;
            dest.m22 = src.m22;
            dest.m32 = src.m32;
        }

        final void copy(Matrix4f src, Matrix4x3f dest) {
            for (int i = 0; i < 4; i++) {
                UNSAFE.putOrderedLong(dest, Matrix4x3f_m00 + 12 * i, UNSAFE.getLong(src, Matrix4f_m00 + (i << 4)));
            }
            dest.m02 = src.m02;
            dest.m12 = src.m12;
            dest.m22 = src.m22;
            dest.m32 = src.m32;
        }

        final void copy(Matrix4x3f src, Matrix4x3f dest) {
            for (int i = 0; i < 6; i++) {
                UNSAFE.putOrderedLong(dest, Matrix4x3f_m00 + (i << 3), UNSAFE.getLong(src, Matrix4x3f_m00 + (i << 3)));
            }
        }

        final void copy(Matrix3f src, Matrix3f dest) {
            for (int i = 0; i < 4; i++) {
                UNSAFE.putOrderedLong(dest, Matrix3f_m00 + (i << 3), UNSAFE.getLong(src, Matrix3f_m00 + (i << 3)));
            }
            dest.m22 = src.m22;
        }

        final void identity(Matrix4f dest) {
            UNSAFE.putOrderedLong(dest, Matrix4f_m00,    0x3F800000L);
            UNSAFE.putOrderedLong(dest, Matrix4f_m00+8,  0L);
            UNSAFE.putOrderedLong(dest, Matrix4f_m00+16, 0x3F80000000000000L);
            UNSAFE.putOrderedLong(dest, Matrix4f_m00+24, 0L);
            UNSAFE.putOrderedLong(dest, Matrix4f_m00+32, 0L);
            UNSAFE.putOrderedLong(dest, Matrix4f_m00+40, 0x3F800000L);
            UNSAFE.putOrderedLong(dest, Matrix4f_m00+48, 0L);
            UNSAFE.putOrderedLong(dest, Matrix4f_m00+56, 0x3F80000000000000L);
        }

        final void identity(Matrix4x3f dest) {
            UNSAFE.putOrderedLong(dest, Matrix4x3f_m00,    0x3F800000L);
            UNSAFE.putOrderedLong(dest, Matrix4x3f_m00+8,  0L);
            UNSAFE.putOrderedLong(dest, Matrix4x3f_m00+16, 0x3F800000L);
            UNSAFE.putOrderedLong(dest, Matrix4x3f_m00+24, 0L);
            UNSAFE.putOrderedLong(dest, Matrix4x3f_m00+32, 0x3F800000L);
            UNSAFE.putOrderedLong(dest, Matrix4x3f_m00+40, 0L);
        }

        final void identity(Matrix3f dest) {
            UNSAFE.putOrderedLong(dest, Matrix3f_m00,    0x3F800000L);
            UNSAFE.putOrderedLong(dest, Matrix3f_m00+8,  0L);
            UNSAFE.putOrderedLong(dest, Matrix3f_m00+16, 0x3F800000L);
            UNSAFE.putOrderedLong(dest, Matrix3f_m00+24, 0L);
            dest.m22 = 1.0f;
        }

        final void swap(Matrix4f m1, Matrix4f m2) {
            for (int i = 0; i < 8; i++) {
                long tmp;
                tmp = UNSAFE.getLong(m1, Matrix4f_m00 + (i << 3));
                UNSAFE.putOrderedLong(m1, Matrix4f_m00 + (i << 3), UNSAFE.getLong(m2, Matrix4f_m00 + (i << 3)));
                UNSAFE.putOrderedLong(m2, Matrix4f_m00 + (i << 3), tmp);
            }
        }

        final void swap(Matrix4x3f m1, Matrix4x3f m2) {
            for (int i = 0; i < 6; i++) {
                long tmp;
                tmp = UNSAFE.getLong(m1, Matrix4x3f_m00 + (i << 3));
                UNSAFE.putOrderedLong(m1, Matrix4x3f_m00 + (i << 3), UNSAFE.getLong(m2, Matrix4x3f_m00 + (i << 3)));
                UNSAFE.putOrderedLong(m2, Matrix4x3f_m00 + (i << 3), tmp);
            }
        }

        final void swap(Matrix3f m1, Matrix3f m2) {
            for (int i = 0; i < 4; i++) {
                long tmp;
                tmp = UNSAFE.getLong(m1, Matrix3f_m00 + (i << 3));
                UNSAFE.putOrderedLong(m1, Matrix3f_m00 + (i << 3), UNSAFE.getLong(m2, Matrix3f_m00 + (i << 3)));
                UNSAFE.putOrderedLong(m2, Matrix3f_m00 + (i << 3), tmp);
            }
            float tmp2 = m1.m22;
            m1.m22 = m2.m22;
            m2.m22 = tmp2;
        }

        final void zero(Matrix4f dest) {
            for (int i = 0; i < 8; i++) {
                UNSAFE.putOrderedLong(dest, Matrix4f_m00 + (i << 3), 0L);
            }
        }

        final void zero(Matrix4x3f dest) {
            for (int i = 0; i < 6; i++) {
                UNSAFE.putOrderedLong(dest, Matrix4x3f_m00 + (i << 3), 0L);
            }
        }

        final void zero(Matrix3f dest) {
            for (int i = 0; i < 4; i++) {
                UNSAFE.putOrderedLong(dest, Matrix3f_m00 + (i << 3), 0L);
            }
            dest.m22 = 0.0f;
        }

        final void identity(DualQuaternionf dq) {
            UNSAFE.putOrderedLong(dq, DualQuaternionf_rx, 0L);
            UNSAFE.putOrderedLong(dq, DualQuaternionf_rx + 8, 0x3F80000000000000L);
            UNSAFE.putOrderedLong(dq, DualQuaternionf_rx + 16, 0L);
            UNSAFE.putOrderedLong(dq, DualQuaternionf_rx + 24, 0L);
        }

        final void zero(DualQuaternionf dq) {
            for (int i = 0; i < 4; i++) {
                UNSAFE.putOrderedLong(dq, DualQuaternionf_rx + (i << 3), 0L);
            }
        }

        final void putMatrix4f(DualQuaternionf dq, int position, ByteBuffer dest) {
            float dqx = dq.rx + dq.rx;
            float dqy = dq.ry + dq.ry;
            float dqz = dq.rz + dq.rz;
            float q00 = dqx * dq.rx;
            float q11 = dqy * dq.ry;
            float q22 = dqz * dq.rz;
            float q01 = dqx * dq.ry;
            float q02 = dqx * dq.rz;
            float q03 = dqx * dq.rw;
            float q12 = dqy * dq.rz;
            float q13 = dqy * dq.rw;
            float q23 = dqz * dq.rw;
            long addr = addressOf(dest) + position;
            UNSAFE.putFloat(null, addr, 1.0f - q11 - q22);
            UNSAFE.putFloat(null, addr + 4, q01 + q23);
            UNSAFE.putFloat(null, addr + 8, q02 - q13);
            UNSAFE.putFloat(null, addr + 12, 0.0f);
            UNSAFE.putFloat(null, addr + 16, q01 - q23);
            UNSAFE.putFloat(null, addr + 20, 1.0f - q22 - q00);
            UNSAFE.putFloat(null, addr + 24, q12 + q03);
            UNSAFE.putFloat(null, addr + 28, 0.0f);
            UNSAFE.putFloat(null, addr + 32, q02 + q13);
            UNSAFE.putFloat(null, addr + 36, q12 - q03);
            UNSAFE.putFloat(null, addr + 40, 1.0f - q11 - q00);
            UNSAFE.putFloat(null, addr + 44, 0.0f);
            UNSAFE.putFloat(null, addr + 48, 2.0f * (dq.rw*dq.tx - dq.rx*dq.tw + dq.ry*dq.tz - dq.rz*dq.ty));
            UNSAFE.putFloat(null, addr + 52, 2.0f * (dq.rw*dq.ty - dq.ry*dq.tw - dq.rx*dq.tz + dq.rz*dq.tx));
            UNSAFE.putFloat(null, addr + 56, 2.0f * (dq.rw*dq.tz - dq.rz*dq.tw + dq.rx*dq.ty - dq.ry*dq.tx));
            UNSAFE.putFloat(null, addr + 60, 1.0f);
        }

        final void putMatrix4f(DualQuaternionf dq, int position, FloatBuffer dest) {
            float dqx = dq.rx + dq.rx;
            float dqy = dq.ry + dq.ry;
            float dqz = dq.rz + dq.rz;
            float q00 = dqx * dq.rx;
            float q11 = dqy * dq.ry;
            float q22 = dqz * dq.rz;
            float q01 = dqx * dq.ry;
            float q02 = dqx * dq.rz;
            float q03 = dqx * dq.rw;
            float q12 = dqy * dq.rz;
            float q13 = dqy * dq.rw;
            float q23 = dqz * dq.rw;
            long addr = addressOf(dest) + (position << 2);
            UNSAFE.putFloat(null, addr, 1.0f - q11 - q22);
            UNSAFE.putFloat(null, addr + 4, q01 + q23);
            UNSAFE.putFloat(null, addr + 8, q02 - q13);
            UNSAFE.putFloat(null, addr + 12, 0.0f);
            UNSAFE.putFloat(null, addr + 16, q01 - q23);
            UNSAFE.putFloat(null, addr + 20, 1.0f - q22 - q00);
            UNSAFE.putFloat(null, addr + 24, q12 + q03);
            UNSAFE.putFloat(null, addr + 28, 0.0f);
            UNSAFE.putFloat(null, addr + 32, q02 + q13);
            UNSAFE.putFloat(null, addr + 36, q12 - q03);
            UNSAFE.putFloat(null, addr + 40, 1.0f - q11 - q00);
            UNSAFE.putFloat(null, addr + 44, 0.0f);
            UNSAFE.putFloat(null, addr + 48, 2.0f * (dq.rw*dq.tx - dq.rx*dq.tw + dq.ry*dq.tz - dq.rz*dq.ty));
            UNSAFE.putFloat(null, addr + 52, 2.0f * (dq.rw*dq.ty - dq.ry*dq.tw - dq.rx*dq.tz + dq.rz*dq.tx));
            UNSAFE.putFloat(null, addr + 56, 2.0f * (dq.rw*dq.tz - dq.rz*dq.tw + dq.rx*dq.ty - dq.ry*dq.tx));
            UNSAFE.putFloat(null, addr + 60, 1.0f);
        }

        final void putMatrix4x3f(DualQuaternionf dq, int position, ByteBuffer dest) {
            float dqx = dq.rx + dq.rx;
            float dqy = dq.ry + dq.ry;
            float dqz = dq.rz + dq.rz;
            float q00 = dqx * dq.rx;
            float q11 = dqy * dq.ry;
            float q22 = dqz * dq.rz;
            float q01 = dqx * dq.ry;
            float q02 = dqx * dq.rz;
            float q03 = dqx * dq.rw;
            float q12 = dqy * dq.rz;
            float q13 = dqy * dq.rw;
            float q23 = dqz * dq.rw;
            long addr = addressOf(dest) + position;
            UNSAFE.putFloat(null, addr, 1.0f - q11 - q22);
            UNSAFE.putFloat(null, addr + 4, q01 + q23);
            UNSAFE.putFloat(null, addr + 8, q02 - q13);
            UNSAFE.putFloat(null, addr + 12, q01 - q23);
            UNSAFE.putFloat(null, addr + 16, 1.0f - q22 - q00);
            UNSAFE.putFloat(null, addr + 20, q12 + q03);
            UNSAFE.putFloat(null, addr + 24, q02 + q13);
            UNSAFE.putFloat(null, addr + 28, q12 - q03);
            UNSAFE.putFloat(null, addr + 32, 1.0f - q11 - q00);
            UNSAFE.putFloat(null, addr + 36, 2.0f * (dq.rw*dq.tx - dq.rx*dq.tw + dq.ry*dq.tz - dq.rz*dq.ty));
            UNSAFE.putFloat(null, addr + 40, 2.0f * (dq.rw*dq.ty - dq.ry*dq.tw - dq.rx*dq.tz + dq.rz*dq.tx));
            UNSAFE.putFloat(null, addr + 44, 2.0f * (dq.rw*dq.tz - dq.rz*dq.tw + dq.rx*dq.ty - dq.ry*dq.tx));
        }

        final void putMatrix4x3f(DualQuaternionf dq, int position, FloatBuffer dest) {
            float dqx = dq.rx + dq.rx;
            float dqy = dq.ry + dq.ry;
            float dqz = dq.rz + dq.rz;
            float q00 = dqx * dq.rx;
            float q11 = dqy * dq.ry;
            float q22 = dqz * dq.rz;
            float q01 = dqx * dq.ry;
            float q02 = dqx * dq.rz;
            float q03 = dqx * dq.rw;
            float q12 = dqy * dq.rz;
            float q13 = dqy * dq.rw;
            float q23 = dqz * dq.rw;
            long addr = addressOf(dest) + (position << 2);
            UNSAFE.putFloat(null, addr, 1.0f - q11 - q22);
            UNSAFE.putFloat(null, addr + 4, q01 + q23);
            UNSAFE.putFloat(null, addr + 8, q02 - q13);
            UNSAFE.putFloat(null, addr + 12, q01 - q23);
            UNSAFE.putFloat(null, addr + 16, 1.0f - q22 - q00);
            UNSAFE.putFloat(null, addr + 20, q12 + q03);
            UNSAFE.putFloat(null, addr + 24, q02 + q13);
            UNSAFE.putFloat(null, addr + 28, q12 - q03);
            UNSAFE.putFloat(null, addr + 32, 1.0f - q11 - q00);
            UNSAFE.putFloat(null, addr + 36, 2.0f * (dq.rw*dq.tx - dq.rx*dq.tw + dq.ry*dq.tz - dq.rz*dq.ty));
            UNSAFE.putFloat(null, addr + 40, 2.0f * (dq.rw*dq.ty - dq.ry*dq.tw - dq.rx*dq.tz + dq.rz*dq.tx));
            UNSAFE.putFloat(null, addr + 44, 2.0f * (dq.rw*dq.tz - dq.rz*dq.tw + dq.rx*dq.ty - dq.ry*dq.tx));
        }

        final void putMatrix3f(Quaternionf q, int position, ByteBuffer dest) {
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
            long addr = addressOf(dest) + position;
            UNSAFE.putFloat(null, addr, 1.0f - q11 - q22);
            UNSAFE.putFloat(null, addr + 4, q01 + q23);
            UNSAFE.putFloat(null, addr + 8, q02 - q13);
            UNSAFE.putFloat(null, addr + 12, q01 - q23);
            UNSAFE.putFloat(null, addr + 16, 1.0f - q22 - q00);
            UNSAFE.putFloat(null, addr + 20, q12 + q03);
            UNSAFE.putFloat(null, addr + 24, q02 + q13);
            UNSAFE.putFloat(null, addr + 28, q12 - q03);
            UNSAFE.putFloat(null, addr + 32, 1.0f - q11 - q00);
        }

        final void putMatrix3f(Quaternionf q, int position, FloatBuffer dest) {
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
            long addr = addressOf(dest) + (position << 2);
            UNSAFE.putFloat(null, addr, 1.0f - q11 - q22);
            UNSAFE.putFloat(null, addr + 4, q01 + q23);
            UNSAFE.putFloat(null, addr + 8, q02 - q13);
            UNSAFE.putFloat(null, addr + 12, q01 - q23);
            UNSAFE.putFloat(null, addr + 16, 1.0f - q22 - q00);
            UNSAFE.putFloat(null, addr + 20, q12 + q03);
            UNSAFE.putFloat(null, addr + 24, q02 + q13);
            UNSAFE.putFloat(null, addr + 28, q12 - q03);
            UNSAFE.putFloat(null, addr + 32, 1.0f - q11 - q00);
        }

        final void copy(DualQuaternionf src, DualQuaternionf dest) {
            for (int i = 0; i < 4; i++) {
                UNSAFE.putOrderedLong(dest, DualQuaternionf_rx + (i << 2), UNSAFE.getLong(src, DualQuaternionf_rx + (i << 2)));
            }
        }

        final void put(Matrix4f m, int offset, FloatBuffer dest) {
            put(m, addressOf(dest) + (offset << 2));
        }

        final void put(Matrix4f m, int offset, ByteBuffer dest) {
            put(m, addressOf(dest) + offset);
        }

        final void put(Matrix4x3f m, int offset, FloatBuffer dest) {
            put(m, addressOf(dest) + (offset << 2));
        }

        final void put(Matrix4x3f m, int offset, ByteBuffer dest) {
            put(m, addressOf(dest) + offset);
        }

        final void put4x4(Matrix4x3f m, int offset, FloatBuffer dest) {
            put4x4(m, addressOf(dest) + (offset << 2));
        }

        final void put4x4(Matrix4x3f m, int offset, ByteBuffer dest) {
            put4x4(m, addressOf(dest) + offset);
        }

        final void putTransposed(Matrix4f m, int offset, FloatBuffer dest) {
            putTransposed(m, addressOf(dest) + (offset << 2));
        }

        final void putTransposed(Matrix4f m, int offset, ByteBuffer dest) {
            putTransposed(m, addressOf(dest) + offset);
        }

        final void put4x3Transposed(Matrix4f m, int offset, FloatBuffer dest) {
            put4x3Transposed(m, addressOf(dest) + (offset << 2));
        }

        final void put4x3Transposed(Matrix4f m, int offset, ByteBuffer dest) {
            put4x3Transposed(m, addressOf(dest) + offset);
        }

        final void putTransposed(Matrix4x3f m, int offset, FloatBuffer dest) {
            putTransposed(m, addressOf(dest) + (offset << 2));
        }

        final void putTransposed(Matrix4x3f m, int offset, ByteBuffer dest) {
            putTransposed(m, addressOf(dest) + offset);
        }

        final void put(Matrix4d m, int offset, DoubleBuffer dest) {
            put(m, addressOf(dest) + (offset << 3));
        }

        final void put(Matrix4d m, int offset, ByteBuffer dest) {
            put(m, addressOf(dest) + offset);
        }

        final void put(Matrix4x3d m, int offset, DoubleBuffer dest) {
            put(m, addressOf(dest) + (offset << 3));
        }

        final void put(Matrix4x3d m, int offset, ByteBuffer dest) {
            put(m, addressOf(dest) + offset);
        }

        final void putf(Matrix4d m, int offset, FloatBuffer dest) {
            putf(m, addressOf(dest) + (offset << 2));
        }

        final void putf(Matrix4d m, int offset, ByteBuffer dest) {
            putf(m, addressOf(dest) + offset);
        }

        final void putf(Matrix4x3d m, int offset, FloatBuffer dest) {
            putf(m, addressOf(dest) + (offset << 2));
        }

        final void putf(Matrix4x3d m, int offset, ByteBuffer dest) {
            putf(m, addressOf(dest) + offset);
        }

        final void putTransposed(Matrix4d m, int offset, DoubleBuffer dest) {
            putTransposed(m, addressOf(dest) + (offset << 3));
        }

        final void putTransposed(Matrix4d m, int offset, ByteBuffer dest) {
            putTransposed(m, addressOf(dest) + offset);
        }

        final void put4x3Transposed(Matrix4d m, int offset, DoubleBuffer dest) {
            put4x3Transposed(m, addressOf(dest) + (offset << 3));
        }

        final void put4x3Transposed(Matrix4d m, int offset, ByteBuffer dest) {
            put4x3Transposed(m, addressOf(dest) + offset);
        }

        final void putTransposed(Matrix4x3d m, int offset, DoubleBuffer dest) {
            putTransposed(m, addressOf(dest) + (offset << 3));
        }

        final void putTransposed(Matrix4x3d m, int offset, ByteBuffer dest) {
            putTransposed(m, addressOf(dest) + offset);
        }

        final void putfTransposed(Matrix4d m, int offset, FloatBuffer dest) {
            putfTransposed(m, addressOf(dest) + (offset << 2));
        }

        final void putfTransposed(Matrix4d m, int offset, ByteBuffer dest) {
            putfTransposed(m, addressOf(dest) + offset);
        }

        final void putfTransposed(Matrix4x3d m, int offset, FloatBuffer dest) {
            putfTransposed(m, addressOf(dest) + (offset << 2));
        }

        final void putfTransposed(Matrix4x3d m, int offset, ByteBuffer dest) {
            putfTransposed(m, addressOf(dest) + offset);
        }

        final void put(Matrix3f m, int offset, FloatBuffer dest) {
            put(m, addressOf(dest) + (offset << 2));
        }

        final void put(Matrix3f m, int offset, ByteBuffer dest) {
            put(m, addressOf(dest) + offset);
        }

        final void put(Matrix3d m, int offset, DoubleBuffer dest) {
            put(m, addressOf(dest) + (offset << 3));
        }

        final void put(Matrix3d m, int offset, ByteBuffer dest) {
            put(m, addressOf(dest) + offset);
        }

        final void putf(Matrix3d m, int offset, FloatBuffer dest) {
            putf(m, addressOf(dest) + (offset << 2));
        }

        final void putf(Matrix3d m, int offset, ByteBuffer dest) {
            putf(m, addressOf(dest) + offset);
        }

        final void get(Matrix4f m, int offset, FloatBuffer src) {
            get(m, addressOf(src) + (offset << 2));
        }

        final void get(Matrix4f m, int offset, ByteBuffer src) {
            get(m, addressOf(src) + offset);
        }

        final void get(Matrix4x3f m, int offset, FloatBuffer src) {
            get(m, addressOf(src) + (offset << 2));
        }

        final void get(Matrix4x3f m, int offset, ByteBuffer src) {
            get(m, addressOf(src) + offset);
        }

        final void get(Matrix4d m, int offset, DoubleBuffer src) {
            get(m, addressOf(src) + (offset << 3));
        }

        final void get(Matrix4d m, int offset, ByteBuffer src) {
            get(m, addressOf(src) + offset);
        }

        final void get(Matrix4x3d m, int offset, DoubleBuffer src) {
            get(m, addressOf(src) + (offset << 3));
        }

        final void get(Matrix4x3d m, int offset, ByteBuffer src) {
            get(m, addressOf(src) + offset);
        }

        final void getf(Matrix4d m, int offset, FloatBuffer src) {
            getf(m, addressOf(src) + (offset << 2));
        }

        final void getf(Matrix4d m, int offset, ByteBuffer src) {
            getf(m, addressOf(src) + offset);
        }

        final void getf(Matrix4x3d m, int offset, FloatBuffer src) {
            getf(m, addressOf(src) + (offset << 2));
        }

        final void getf(Matrix4x3d m, int offset, ByteBuffer src) {
            getf(m, addressOf(src) + offset);
        }

        final void get(Matrix3f m, int offset, FloatBuffer src) {
            get(m, addressOf(src) + (offset << 2));
        }

        final void get(Matrix3f m, int offset, ByteBuffer src) {
            get(m, addressOf(src) + offset);
        }

        final void get(Matrix3d m, int offset, DoubleBuffer src) {
            get(m, addressOf(src) + (offset << 3));
        }

        final void get(Matrix3d m, int offset, ByteBuffer src) {
            get(m, addressOf(src) + offset);
        }

        final void getf(Matrix3d m, int offset, FloatBuffer src) {
            getf(m, addressOf(src) + (offset << 2));
        }

        final void getf(Matrix3d m, int offset, ByteBuffer src) {
            getf(m, addressOf(src) + offset);
        }
    }
}
