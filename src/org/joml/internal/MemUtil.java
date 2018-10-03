/*
 * (C) Copyright 2016-2018 Kai Burjack

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
package org.joml.internal;

//#ifndef __GWT__
import java.lang.reflect.Field;
//#endif
//#ifdef __HAS_NIO__
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
//#endif

import org.joml.Matrix3d;
import org.joml.Matrix3f;
import org.joml.Matrix3x2d;
import org.joml.Matrix3x2f;
import org.joml.Matrix4d;
import org.joml.Matrix4f;
import org.joml.Matrix4x3d;
import org.joml.Matrix4x3f;
import org.joml.Quaternionf;
import org.joml.Vector2d;
import org.joml.Vector2f;
import org.joml.Vector2i;
import org.joml.Vector3d;
import org.joml.Vector3f;
import org.joml.Vector3i;
import org.joml.Vector4d;
import org.joml.Vector4f;
import org.joml.Vector4i;

/**
 * Helper class to do efficient memory operations on all JOML objects, NIO buffers and primitive arrays.
 * This class is used internally throughout JOML, is undocumented and is subject to change.
 * Use with extreme caution!
 * 
 * @author The LWJGL authors
 * @author Kai Burjack
 */
public abstract class MemUtil {
    public static final MemUtil INSTANCE = createInstance();
    private static MemUtil createInstance() {
        MemUtil accessor;
//#ifdef __GWT__
        accessor = new MemUtilNIO();
//#else
        try {
            if (Options.NO_UNSAFE)
                accessor = new MemUtilNIO();
            else
                accessor = new MemUtilUnsafe();
        } catch (Throwable e) {
            accessor = new MemUtilNIO();
        }
//#endif
        return accessor;
    }

//#ifdef __HAS_NIO__
    public abstract void put(Matrix4f m, int offset, FloatBuffer dest);
    public abstract void put(Matrix4f m, int offset, ByteBuffer dest);
    public abstract void put(Matrix4x3f m, int offset, FloatBuffer dest);
    public abstract void put(Matrix4x3f m, int offset, ByteBuffer dest);
    public abstract void put4x4(Matrix4x3f m, int offset, FloatBuffer dest);
    public abstract void put4x4(Matrix4x3f m, int offset, ByteBuffer dest);
    public abstract void put4x4(Matrix4x3d m, int offset, DoubleBuffer dest);
    public abstract void put4x4(Matrix4x3d m, int offset, ByteBuffer dest);
    public abstract void put4x4(Matrix3x2f m, int offset, FloatBuffer dest);
    public abstract void put4x4(Matrix3x2f m, int offset, ByteBuffer dest);
    public abstract void put4x4(Matrix3x2d m, int offset, DoubleBuffer dest);
    public abstract void put4x4(Matrix3x2d m, int offset, ByteBuffer dest);
    public abstract void put3x3(Matrix3x2f m, int offset, FloatBuffer dest);
    public abstract void put3x3(Matrix3x2f m, int offset, ByteBuffer dest);
    public abstract void put3x3(Matrix3x2d m, int offset, DoubleBuffer dest);
    public abstract void put3x3(Matrix3x2d m, int offset, ByteBuffer dest);
    public abstract void put4x3(Matrix4f m, int offset, FloatBuffer dest);
    public abstract void put4x3(Matrix4f m, int offset, ByteBuffer dest);
    public abstract void putTransposed(Matrix4f m, int offset, FloatBuffer dest);
    public abstract void putTransposed(Matrix4f m, int offset, ByteBuffer dest);
    public abstract void put4x3Transposed(Matrix4f m, int offset, FloatBuffer dest);
    public abstract void put4x3Transposed(Matrix4f m, int offset, ByteBuffer dest);
    public abstract void putTransposed(Matrix4x3f m, int offset, FloatBuffer dest);
    public abstract void putTransposed(Matrix4x3f m, int offset, ByteBuffer dest);
    public abstract void putTransposed(Matrix3f m, int offset, FloatBuffer dest);
    public abstract void putTransposed(Matrix3f m, int offset, ByteBuffer dest);
    public abstract void put(Matrix4d m, int offset, DoubleBuffer dest);
    public abstract void put(Matrix4d m, int offset, ByteBuffer dest);
    public abstract void put(Matrix4x3d m, int offset, DoubleBuffer dest);
    public abstract void put(Matrix4x3d m, int offset, ByteBuffer dest);
    public abstract void putf(Matrix4d m, int offset, FloatBuffer dest);
    public abstract void putf(Matrix4d m, int offset, ByteBuffer dest);
    public abstract void putf(Matrix4x3d m, int offset, FloatBuffer dest);
    public abstract void putf(Matrix4x3d m, int offset, ByteBuffer dest);
    public abstract void putTransposed(Matrix4d m, int offset, DoubleBuffer dest);
    public abstract void putTransposed(Matrix4d m, int offset, ByteBuffer dest);
    public abstract void put4x3Transposed(Matrix4d m, int offset, DoubleBuffer dest);
    public abstract void put4x3Transposed(Matrix4d m, int offset, ByteBuffer dest);
    public abstract void putTransposed(Matrix4x3d m, int offset, DoubleBuffer dest);
    public abstract void putTransposed(Matrix4x3d m, int offset, ByteBuffer dest);
    public abstract void putfTransposed(Matrix4d m, int offset, FloatBuffer dest);
    public abstract void putfTransposed(Matrix4d m, int offset, ByteBuffer dest);
    public abstract void putfTransposed(Matrix4x3d m, int offset, FloatBuffer dest);
    public abstract void putfTransposed(Matrix4x3d m, int offset, ByteBuffer dest);
    public abstract void put(Matrix3f m, int offset, FloatBuffer dest);
    public abstract void put(Matrix3f m, int offset, ByteBuffer dest);
    public abstract void put(Matrix3d m, int offset, DoubleBuffer dest);
    public abstract void put(Matrix3d m, int offset, ByteBuffer dest);
    public abstract void putf(Matrix3d m, int offset, FloatBuffer dest);
    public abstract void putf(Matrix3d m, int offset, ByteBuffer dest);
    public abstract void put(Matrix3x2f m, int offset, FloatBuffer dest);
    public abstract void put(Matrix3x2f m, int offset, ByteBuffer dest);
    public abstract void put(Matrix3x2d m, int offset, DoubleBuffer dest);
    public abstract void put(Matrix3x2d m, int offset, ByteBuffer dest);
    public abstract void put(Vector4d src, int offset, DoubleBuffer dest);
    public abstract void put(Vector4d src, int offset, ByteBuffer dest);
    public abstract void put(Vector4f src, int offset, FloatBuffer dest);
    public abstract void put(Vector4f src, int offset, ByteBuffer dest);
    public abstract void put(Vector4i src, int offset, IntBuffer dest);
    public abstract void put(Vector4i src, int offset, ByteBuffer dest);
    public abstract void put(Vector3f src, int offset, FloatBuffer dest);
    public abstract void put(Vector3f src, int offset, ByteBuffer dest);
    public abstract void put(Vector3d src, int offset, DoubleBuffer dest);
    public abstract void put(Vector3d src, int offset, ByteBuffer dest);
    public abstract void put(Vector3i src, int offset, IntBuffer dest);
    public abstract void put(Vector3i src, int offset, ByteBuffer dest);
    public abstract void put(Vector2f src, int offset, FloatBuffer dest);
    public abstract void put(Vector2f src, int offset, ByteBuffer dest);
    public abstract void put(Vector2d src, int offset, DoubleBuffer dest);
    public abstract void put(Vector2d src, int offset, ByteBuffer dest);
    public abstract void put(Vector2i src, int offset, IntBuffer dest);
    public abstract void put(Vector2i src, int offset, ByteBuffer dest);
    public abstract void get(Matrix4f m, int offset, FloatBuffer src);
    public abstract void get(Matrix4f m, int offset, ByteBuffer src);
    public abstract void get(Matrix4x3f m, int offset, FloatBuffer src);
    public abstract void get(Matrix4x3f m, int offset, ByteBuffer src);
    public abstract void get(Matrix4d m, int offset, DoubleBuffer src);
    public abstract void get(Matrix4d m, int offset, ByteBuffer src);
    public abstract void get(Matrix4x3d m, int offset, DoubleBuffer src);
    public abstract void get(Matrix4x3d m, int offset, ByteBuffer src);
    public abstract void getf(Matrix4d m, int offset, FloatBuffer src);
    public abstract void getf(Matrix4d m, int offset, ByteBuffer src);
    public abstract void getf(Matrix4x3d m, int offset, FloatBuffer src);
    public abstract void getf(Matrix4x3d m, int offset, ByteBuffer src);
    public abstract void get(Matrix3f m, int offset, FloatBuffer src);
    public abstract void get(Matrix3f m, int offset, ByteBuffer src);
    public abstract void get(Matrix3d m, int offset, DoubleBuffer src);
    public abstract void get(Matrix3d m, int offset, ByteBuffer src);
    public abstract void get(Matrix3x2f m, int offset, FloatBuffer src);
    public abstract void get(Matrix3x2f m, int offset, ByteBuffer src);
    public abstract void get(Matrix3x2d m, int offset, DoubleBuffer src);
    public abstract void get(Matrix3x2d m, int offset, ByteBuffer src);
    public abstract void getf(Matrix3d m, int offset, FloatBuffer src);
    public abstract void getf(Matrix3d m, int offset, ByteBuffer src);
    public abstract void get(Vector4d dst, int offset, DoubleBuffer src);
    public abstract void get(Vector4d dst, int offset, ByteBuffer src);
    public abstract void get(Vector4f dst, int offset, FloatBuffer src);
    public abstract void get(Vector4f dst, int offset, ByteBuffer src);
    public abstract void get(Vector4i dst, int offset, IntBuffer src);
    public abstract void get(Vector4i dst, int offset, ByteBuffer src);
    public abstract void get(Vector3f dst, int offset, FloatBuffer src);
    public abstract void get(Vector3f dst, int offset, ByteBuffer src);
    public abstract void get(Vector3d dst, int offset, DoubleBuffer src);
    public abstract void get(Vector3d dst, int offset, ByteBuffer src);
    public abstract void get(Vector3i dst, int offset, IntBuffer src);
    public abstract void get(Vector3i dst, int offset, ByteBuffer src);
    public abstract void get(Vector2f dst, int offset, FloatBuffer src);
    public abstract void get(Vector2f dst, int offset, ByteBuffer src);
    public abstract void get(Vector2d dst, int offset, DoubleBuffer src);
    public abstract void get(Vector2d dst, int offset, ByteBuffer src);
    public abstract void get(Vector2i dst, int offset, IntBuffer src);
    public abstract void get(Vector2i dst, int offset, ByteBuffer src);
//#endif

    public abstract void copy(Matrix4f src, Matrix4f dest);
    public abstract void copy(Matrix4x3f src, Matrix4x3f dest);
    public abstract void copy(Matrix4f src, Matrix4x3f dest);
    public abstract void copy(Matrix4x3f src, Matrix4f dest);
    public abstract void copy(Matrix3f src, Matrix3f dest);
    public abstract void copy(Matrix3f src, Matrix4f dest);
    public abstract void copy(Matrix4f src, Matrix3f dest);
    public abstract void copy(Matrix3f src, Matrix4x3f dest);
    public abstract void copy(Matrix3x2f src, Matrix3x2f dest);
    public abstract void copy(Matrix3x2d src, Matrix3x2d dest);
    public abstract void copy3x3(Matrix4f src, Matrix4f dest);
    public abstract void copy3x3(Matrix4x3f src, Matrix4x3f dest);
    public abstract void copy3x3(Matrix3f src, Matrix4x3f dest);
    public abstract void copy3x3(Matrix3f src, Matrix4f dest);
    public abstract void copy4x3(Matrix4f src, Matrix4f dest);
    public abstract void copy4x3(Matrix4x3f src, Matrix4f dest);
    public abstract void copy(Vector4f src, Vector4f dst);
    public abstract void copy(Vector4i src, Vector4i dst);
    public abstract void copy(Quaternionf src, Quaternionf dst);
    public abstract void copy(float[] arr, int off, Matrix4f dest);
    public abstract void copy(float[] arr, int off, Matrix3f dest);
    public abstract void copy(float[] arr, int off, Matrix4x3f dest);
    public abstract void copy(float[] arr, int off, Matrix3x2f dest);
    public abstract void copy(double[] arr, int off, Matrix3x2d dest);
    public abstract void copy(Matrix4f src, float[] dest, int off);
    public abstract void copy(Matrix3f src, float[] dest, int off);
    public abstract void copy(Matrix4x3f src, float[] dest, int off);
    public abstract void copy(Matrix3x2f src, float[] dest, int off);
    public abstract void copy(Matrix3x2d src, double[] dest, int off);
    public abstract void copy4x4(Matrix4x3f src, float[] dest, int off);
    public abstract void copy4x4(Matrix4x3d src, float[] dest, int off);
    public abstract void copy4x4(Matrix4x3d src, double[] dest, int off);
    public abstract void copy4x4(Matrix3x2f src, float[] dest, int off);
    public abstract void copy4x4(Matrix3x2d src, double[] dest, int off);
    public abstract void copy3x3(Matrix3x2f src, float[] dest, int off);
    public abstract void copy3x3(Matrix3x2d src, double[] dest, int off);
    public abstract void identity(Matrix4f dest);
    public abstract void identity(Matrix4x3f dest);
    public abstract void identity(Matrix3f dest);
    public abstract void identity(Matrix3x2f dest);
    public abstract void identity(Matrix3x2d dest);
    public abstract void identity(Quaternionf dest);
    public abstract void swap(Matrix4f m1, Matrix4f m2);
    public abstract void swap(Matrix4x3f m1, Matrix4x3f m2);
    public abstract void swap(Matrix3f m1, Matrix3f m2);
    public abstract void zero(Matrix4f dest);
    public abstract void zero(Matrix4x3f dest);
    public abstract void zero(Matrix3f dest);
    public abstract void zero(Matrix3x2f dest);
    public abstract void zero(Matrix3x2d dest);
    public abstract void zero(Vector4f dest);
    public abstract void zero(Vector4i dest);

//#ifdef __HAS_NIO__
    public abstract void putMatrix3f(Quaternionf q, int position, ByteBuffer dest);
    public abstract void putMatrix3f(Quaternionf q, int position, FloatBuffer dest);
    public abstract void putMatrix4f(Quaternionf q, int position, ByteBuffer dest);
    public abstract void putMatrix4f(Quaternionf q, int position, FloatBuffer dest);
    public abstract void putMatrix4x3f(Quaternionf q, int position, ByteBuffer dest);
    public abstract void putMatrix4x3f(Quaternionf q, int position, FloatBuffer dest);
//#endif

    public abstract void set(Matrix4f dest, Vector4f col0, Vector4f col1, Vector4f col2, Vector4f col3);
    public abstract void set(Matrix4x3f dest, Vector3f col0, Vector3f col1, Vector3f col2, Vector3f col3);
    public abstract void set(Matrix3f dest, Vector3f col0, Vector3f col1, Vector3f col2);

    public abstract void putColumn0(Matrix4f src, Vector4f dest);
    public abstract void putColumn1(Matrix4f src, Vector4f dest);
    public abstract void putColumn2(Matrix4f src, Vector4f dest);
    public abstract void putColumn3(Matrix4f src, Vector4f dest);

    public abstract void putColumn0(Matrix4f src, Vector3f dest);
    public abstract void putColumn1(Matrix4f src, Vector3f dest);
    public abstract void putColumn2(Matrix4f src, Vector3f dest);
    public abstract void putColumn3(Matrix4f src, Vector3f dest);

    public abstract void getColumn0(Matrix4f dest, Vector4f src);
    public abstract void getColumn1(Matrix4f dest, Vector4f src);
    public abstract void getColumn2(Matrix4f dest, Vector4f src);
    public abstract void getColumn3(Matrix4f dest, Vector4f src);

    public abstract void broadcast(float c, Vector4f dest);
    public abstract void broadcast(int c, Vector4i dest);

    public static class MemUtilNIO extends MemUtil {
//#ifdef __HAS_NIO__
        public void put0(Matrix4f m, FloatBuffer dest) {
            dest.put(0,  m.m00());
            dest.put(1,  m.m01());
            dest.put(2,  m.m02());
            dest.put(3,  m.m03());
            dest.put(4,  m.m10());
            dest.put(5,  m.m11());
            dest.put(6,  m.m12());
            dest.put(7,  m.m13());
            dest.put(8,  m.m20());
            dest.put(9,  m.m21());
            dest.put(10, m.m22());
            dest.put(11, m.m23());
            dest.put(12, m.m30());
            dest.put(13, m.m31());
            dest.put(14, m.m32());
            dest.put(15, m.m33());
        }
        public void putN(Matrix4f m, int offset, FloatBuffer dest) {
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
        public void put(Matrix4f m, int offset, FloatBuffer dest) {
            if (offset == 0)
                put0(m, dest);
            else
                putN(m, offset, dest);
        }

        public void put0(Matrix4f m, ByteBuffer dest) {
            dest.putFloat(0,  m.m00());
            dest.putFloat(4,  m.m01());
            dest.putFloat(8,  m.m02());
            dest.putFloat(12, m.m03());
            dest.putFloat(16, m.m10());
            dest.putFloat(20, m.m11());
            dest.putFloat(24, m.m12());
            dest.putFloat(28, m.m13());
            dest.putFloat(32, m.m20());
            dest.putFloat(36, m.m21());
            dest.putFloat(40, m.m22());
            dest.putFloat(44, m.m23());
            dest.putFloat(48, m.m30());
            dest.putFloat(52, m.m31());
            dest.putFloat(56, m.m32());
            dest.putFloat(60, m.m33());
        }
        private void putN(Matrix4f m, int offset, ByteBuffer dest) {
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
        public void put(Matrix4f m, int offset, ByteBuffer dest) {
            if (offset == 0)
                put0(m, dest);
            else
                putN(m, offset, dest);
        }

        public void put4x3_0(Matrix4f m, FloatBuffer dest) {
            dest.put(0,  m.m00());
            dest.put(1,  m.m01());
            dest.put(2,  m.m02());
            dest.put(3,  m.m10());
            dest.put(4,  m.m11());
            dest.put(5,  m.m12());
            dest.put(6,  m.m20());
            dest.put(7,  m.m21());
            dest.put(8,  m.m22());
            dest.put(9,  m.m30());
            dest.put(10, m.m31());
            dest.put(11, m.m32());
        }
        public void put4x3_N(Matrix4f m, int offset, FloatBuffer dest) {
            dest.put(offset,    m.m00());
            dest.put(offset+1,  m.m01());
            dest.put(offset+2,  m.m02());
            dest.put(offset+3,  m.m10());
            dest.put(offset+4,  m.m11());
            dest.put(offset+5,  m.m12());
            dest.put(offset+6,  m.m20());
            dest.put(offset+7,  m.m21());
            dest.put(offset+8,  m.m22());
            dest.put(offset+9,  m.m30());
            dest.put(offset+10, m.m31());
            dest.put(offset+11, m.m32());
        }
        public void put4x3(Matrix4f m, int offset, FloatBuffer dest) {
            if (offset == 0)
                put4x3_0(m, dest);
            else
                put4x3_N(m, offset, dest);
        }

        public void put4x3_0(Matrix4f m, ByteBuffer dest) {
            dest.putFloat(0,  m.m00());
            dest.putFloat(4,  m.m01());
            dest.putFloat(8,  m.m02());
            dest.putFloat(12, m.m10());
            dest.putFloat(16, m.m11());
            dest.putFloat(20, m.m12());
            dest.putFloat(24, m.m20());
            dest.putFloat(28, m.m21());
            dest.putFloat(32, m.m22());
            dest.putFloat(36, m.m30());
            dest.putFloat(40, m.m31());
            dest.putFloat(44, m.m32());
        }
        private void put4x3_N(Matrix4f m, int offset, ByteBuffer dest) {
            dest.putFloat(offset,    m.m00());
            dest.putFloat(offset+4,  m.m01());
            dest.putFloat(offset+8,  m.m02());
            dest.putFloat(offset+12, m.m10());
            dest.putFloat(offset+16, m.m11());
            dest.putFloat(offset+20, m.m12());
            dest.putFloat(offset+24, m.m20());
            dest.putFloat(offset+28, m.m21());
            dest.putFloat(offset+32, m.m22());
            dest.putFloat(offset+36, m.m30());
            dest.putFloat(offset+40, m.m31());
            dest.putFloat(offset+44, m.m32());
        }
        public void put4x3(Matrix4f m, int offset, ByteBuffer dest) {
            if (offset == 0)
                put4x3_0(m, dest);
            else
                put4x3_N(m, offset, dest);
        }

        public void put0(Matrix4x3f m, FloatBuffer dest) {
            dest.put(0,  m.m00());
            dest.put(1,  m.m01());
            dest.put(2,  m.m02());
            dest.put(3,  m.m10());
            dest.put(4,  m.m11());
            dest.put(5,  m.m12());
            dest.put(6,  m.m20());
            dest.put(7,  m.m21());
            dest.put(8,  m.m22());
            dest.put(9,  m.m30());
            dest.put(10, m.m31());
            dest.put(11, m.m32());
        }
        public void putN(Matrix4x3f m, int offset, FloatBuffer dest) {
            dest.put(offset,    m.m00());
            dest.put(offset+1,  m.m01());
            dest.put(offset+2,  m.m02());
            dest.put(offset+3,  m.m10());
            dest.put(offset+4,  m.m11());
            dest.put(offset+5,  m.m12());
            dest.put(offset+6,  m.m20());
            dest.put(offset+7,  m.m21());
            dest.put(offset+8,  m.m22());
            dest.put(offset+9,  m.m30());
            dest.put(offset+10, m.m31());
            dest.put(offset+11, m.m32());
        }
        public void put(Matrix4x3f m, int offset, FloatBuffer dest) {
            if (offset == 0)
                put0(m, dest);
            else
                putN(m, offset, dest);
        }

        public void put0(Matrix4x3f m, ByteBuffer dest) {
            dest.putFloat(0,  m.m00());
            dest.putFloat(4,  m.m01());
            dest.putFloat(8,  m.m02());
            dest.putFloat(12, m.m10());
            dest.putFloat(16, m.m11());
            dest.putFloat(20, m.m12());
            dest.putFloat(24, m.m20());
            dest.putFloat(28, m.m21());
            dest.putFloat(32, m.m22());
            dest.putFloat(36, m.m30());
            dest.putFloat(40, m.m31());
            dest.putFloat(44, m.m32());
        }
        public void putN(Matrix4x3f m, int offset, ByteBuffer dest) {
            dest.putFloat(offset,    m.m00());
            dest.putFloat(offset+4,  m.m01());
            dest.putFloat(offset+8,  m.m02());
            dest.putFloat(offset+12, m.m10());
            dest.putFloat(offset+16, m.m11());
            dest.putFloat(offset+20, m.m12());
            dest.putFloat(offset+24, m.m20());
            dest.putFloat(offset+28, m.m21());
            dest.putFloat(offset+32, m.m22());
            dest.putFloat(offset+36, m.m30());
            dest.putFloat(offset+40, m.m31());
            dest.putFloat(offset+44, m.m32());
        }
        public void put(Matrix4x3f m, int offset, ByteBuffer dest) {
            if (offset == 0)
                put0(m, dest);
            else
                putN(m, offset, dest);
        }

        public void put4x4(Matrix4x3f m, int offset, FloatBuffer dest) {
            dest.put(offset,    m.m00());
            dest.put(offset+1,  m.m01());
            dest.put(offset+2,  m.m02());
            dest.put(offset+3,  0.0f);
            dest.put(offset+4,  m.m10());
            dest.put(offset+5,  m.m11());
            dest.put(offset+6,  m.m12());
            dest.put(offset+7,  0.0f);
            dest.put(offset+8,  m.m20());
            dest.put(offset+9,  m.m21());
            dest.put(offset+10, m.m22());
            dest.put(offset+11, 0.0f);
            dest.put(offset+12, m.m30());
            dest.put(offset+13, m.m31());
            dest.put(offset+14, m.m32());
            dest.put(offset+15, 1.0f);
        }

        public void put4x4(Matrix4x3f m, int offset, ByteBuffer dest) {
            dest.putFloat(offset,    m.m00());
            dest.putFloat(offset+4,  m.m01());
            dest.putFloat(offset+8,  m.m02());
            dest.putFloat(offset+12, 0.0f);
            dest.putFloat(offset+16, m.m10());
            dest.putFloat(offset+20, m.m11());
            dest.putFloat(offset+24, m.m12());
            dest.putFloat(offset+28, 0.0f);
            dest.putFloat(offset+32, m.m20());
            dest.putFloat(offset+36, m.m21());
            dest.putFloat(offset+40, m.m22());
            dest.putFloat(offset+44, 0.0f);
            dest.putFloat(offset+48, m.m30());
            dest.putFloat(offset+52, m.m31());
            dest.putFloat(offset+56, m.m32());
            dest.putFloat(offset+60, 1.0f);
        }

        public void put4x4(Matrix4x3d m, int offset, DoubleBuffer dest) {
            dest.put(offset,    m.m00());
            dest.put(offset+1,  m.m01());
            dest.put(offset+2,  m.m02());
            dest.put(offset+3,  0.0);
            dest.put(offset+4,  m.m10());
            dest.put(offset+5,  m.m11());
            dest.put(offset+6,  m.m12());
            dest.put(offset+7,  0.0);
            dest.put(offset+8,  m.m20());
            dest.put(offset+9,  m.m21());
            dest.put(offset+10, m.m22());
            dest.put(offset+11, 0.0);
            dest.put(offset+12, m.m30());
            dest.put(offset+13, m.m31());
            dest.put(offset+14, m.m32());
            dest.put(offset+15, 1.0);
        }

        public void put4x4(Matrix4x3d m, int offset, ByteBuffer dest) {
            dest.putDouble(offset,    m.m00());
            dest.putDouble(offset+4,  m.m01());
            dest.putDouble(offset+8,  m.m02());
            dest.putDouble(offset+12, 0.0);
            dest.putDouble(offset+16, m.m10());
            dest.putDouble(offset+20, m.m11());
            dest.putDouble(offset+24, m.m12());
            dest.putDouble(offset+28, 0.0);
            dest.putDouble(offset+32, m.m20());
            dest.putDouble(offset+36, m.m21());
            dest.putDouble(offset+40, m.m22());
            dest.putDouble(offset+44, 0.0);
            dest.putDouble(offset+48, m.m30());
            dest.putDouble(offset+52, m.m31());
            dest.putDouble(offset+56, m.m32());
            dest.putDouble(offset+60, 1.0);
        }

        public void put4x4(Matrix3x2f m, int offset, FloatBuffer dest) {
            dest.put(offset,    m.m00());
            dest.put(offset+1,  m.m01());
            dest.put(offset+2,  0.0f);
            dest.put(offset+3,  0.0f);
            dest.put(offset+4,  m.m10());
            dest.put(offset+5,  m.m11());
            dest.put(offset+6,  0.0f);
            dest.put(offset+7,  0.0f);
            dest.put(offset+8,  0.0f);
            dest.put(offset+9,  0.0f);
            dest.put(offset+10, 1.0f);
            dest.put(offset+11, 0.0f);
            dest.put(offset+12, m.m20());
            dest.put(offset+13, m.m21());
            dest.put(offset+14, 0.0f);
            dest.put(offset+15, 1.0f);
        }

        public void put4x4(Matrix3x2f m, int offset, ByteBuffer dest) {
            dest.putFloat(offset,    m.m00());
            dest.putFloat(offset+4,  m.m01());
            dest.putFloat(offset+8,  0.0f);
            dest.putFloat(offset+12, 0.0f);
            dest.putFloat(offset+16, m.m10());
            dest.putFloat(offset+20, m.m11());
            dest.putFloat(offset+24, 0.0f);
            dest.putFloat(offset+28, 0.0f);
            dest.putFloat(offset+32, 0.0f);
            dest.putFloat(offset+36, 0.0f);
            dest.putFloat(offset+40, 1.0f);
            dest.putFloat(offset+44, 0.0f);
            dest.putFloat(offset+48, m.m20());
            dest.putFloat(offset+52, m.m21());
            dest.putFloat(offset+56, 0.0f);
            dest.putFloat(offset+60, 1.0f);
        }

        public void put4x4(Matrix3x2d m, int offset, DoubleBuffer dest) {
            dest.put(offset,    m.m00());
            dest.put(offset+1,  m.m01());
            dest.put(offset+2,  0.0);
            dest.put(offset+3,  0.0);
            dest.put(offset+4,  m.m10());
            dest.put(offset+5,  m.m11());
            dest.put(offset+6,  0.0);
            dest.put(offset+7,  0.0);
            dest.put(offset+8,  0.0);
            dest.put(offset+9,  0.0);
            dest.put(offset+10, 1.0);
            dest.put(offset+11, 0.0);
            dest.put(offset+12, m.m20());
            dest.put(offset+13, m.m21());
            dest.put(offset+14, 0.0);
            dest.put(offset+15, 1.0);
        }

        public void put4x4(Matrix3x2d m, int offset, ByteBuffer dest) {
            dest.putDouble(offset,     m.m00());
            dest.putDouble(offset+8,   m.m01());
            dest.putDouble(offset+16,  0.0);
            dest.putDouble(offset+24,  0.0);
            dest.putDouble(offset+32,  m.m10());
            dest.putDouble(offset+40,  m.m11());
            dest.putDouble(offset+48,  0.0);
            dest.putDouble(offset+56,  0.0);
            dest.putDouble(offset+64,  0.0);
            dest.putDouble(offset+72,  0.0);
            dest.putDouble(offset+80,  1.0);
            dest.putDouble(offset+88,  0.0);
            dest.putDouble(offset+96,  m.m20());
            dest.putDouble(offset+104, m.m21());
            dest.putDouble(offset+112, 0.0);
            dest.putDouble(offset+120, 1.0);
        }

        public void put3x3(Matrix3x2f m, int offset, FloatBuffer dest) {
            dest.put(offset,   m.m00());
            dest.put(offset+1, m.m01());
            dest.put(offset+2, 0.0f);
            dest.put(offset+3, m.m10());
            dest.put(offset+4, m.m11());
            dest.put(offset+5, 0.0f);
            dest.put(offset+6, m.m20());
            dest.put(offset+7, m.m21());
            dest.put(offset+8, 1.0f);
        }

        public void put3x3(Matrix3x2f m, int offset, ByteBuffer dest) {
            dest.putFloat(offset,    m.m00());
            dest.putFloat(offset+4,  m.m01());
            dest.putFloat(offset+8,  0.0f);
            dest.putFloat(offset+12, m.m10());
            dest.putFloat(offset+16, m.m11());
            dest.putFloat(offset+20, 0.0f);
            dest.putFloat(offset+24, m.m20());
            dest.putFloat(offset+28, m.m21());
            dest.putFloat(offset+32, 1.0f);
        }

        public void put3x3(Matrix3x2d m, int offset, DoubleBuffer dest) {
            dest.put(offset,   m.m00());
            dest.put(offset+1, m.m01());
            dest.put(offset+2, 0.0);
            dest.put(offset+3, m.m10());
            dest.put(offset+4, m.m11());
            dest.put(offset+5, 0.0);
            dest.put(offset+6, m.m20());
            dest.put(offset+7, m.m21());
            dest.put(offset+8, 1.0);
        }

        public void put3x3(Matrix3x2d m, int offset, ByteBuffer dest) {
            dest.putDouble(offset,    m.m00());
            dest.putDouble(offset+8,  m.m01());
            dest.putDouble(offset+16, 0.0);
            dest.putDouble(offset+24, m.m10());
            dest.putDouble(offset+32, m.m11());
            dest.putDouble(offset+40, 0.0);
            dest.putDouble(offset+48, m.m20());
            dest.putDouble(offset+56, m.m21());
            dest.putDouble(offset+64, 1.0);
        }

        public void putTransposed(Matrix4f m, int offset, FloatBuffer dest) {
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

        public void putTransposed(Matrix4f m, int offset, ByteBuffer dest) {
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

        public void put4x3Transposed(Matrix4f m, int offset, FloatBuffer dest) {
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
        }

        public void put4x3Transposed(Matrix4f m, int offset, ByteBuffer dest) {
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
        }

        public void putTransposed(Matrix4x3f m, int offset, FloatBuffer dest) {
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
        }

        public void putTransposed(Matrix4x3f m, int offset, ByteBuffer dest) {
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
        }

        public void putTransposed(Matrix3f m, int offset, FloatBuffer dest) {
            dest.put(offset,   m.m00());
            dest.put(offset+1, m.m10());
            dest.put(offset+2, m.m20());
            dest.put(offset+3, m.m01());
            dest.put(offset+4, m.m11());
            dest.put(offset+5, m.m21());
            dest.put(offset+6, m.m02());
            dest.put(offset+7, m.m12());
            dest.put(offset+8, m.m22());
        }

        public void putTransposed(Matrix3f m, int offset, ByteBuffer dest) {
            dest.putFloat(offset,    m.m00());
            dest.putFloat(offset+4,  m.m10());
            dest.putFloat(offset+8,  m.m20());
            dest.putFloat(offset+12, m.m01());
            dest.putFloat(offset+16, m.m11());
            dest.putFloat(offset+20, m.m21());
            dest.putFloat(offset+24, m.m02());
            dest.putFloat(offset+28, m.m12());
            dest.putFloat(offset+32, m.m22());
        }

        public void put(Matrix4d m, int offset, DoubleBuffer dest) {
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

        public void put(Matrix4d m, int offset, ByteBuffer dest) {
            dest.putDouble(offset,    m.m00());
            dest.putDouble(offset+4,  m.m01());
            dest.putDouble(offset+8,  m.m02());
            dest.putDouble(offset+12, m.m03());
            dest.putDouble(offset+16, m.m10());
            dest.putDouble(offset+20, m.m11());
            dest.putDouble(offset+24, m.m12());
            dest.putDouble(offset+28, m.m13());
            dest.putDouble(offset+32, m.m20());
            dest.putDouble(offset+36, m.m21());
            dest.putDouble(offset+40, m.m22());
            dest.putDouble(offset+44, m.m23());
            dest.putDouble(offset+48, m.m30());
            dest.putDouble(offset+52, m.m31());
            dest.putDouble(offset+56, m.m32());
            dest.putDouble(offset+60, m.m33());
        }

        public void put(Matrix4x3d m, int offset, DoubleBuffer dest) {
            dest.put(offset,    m.m00());
            dest.put(offset+1,  m.m01());
            dest.put(offset+2,  m.m02());
            dest.put(offset+3,  m.m10());
            dest.put(offset+4,  m.m11());
            dest.put(offset+5,  m.m12());
            dest.put(offset+6,  m.m20());
            dest.put(offset+7,  m.m21());
            dest.put(offset+8,  m.m22());
            dest.put(offset+9,  m.m30());
            dest.put(offset+10, m.m31());
            dest.put(offset+11, m.m32());
        }

        public void put(Matrix4x3d m, int offset, ByteBuffer dest) {
            dest.putDouble(offset,    m.m00());
            dest.putDouble(offset+4,  m.m01());
            dest.putDouble(offset+8,  m.m02());
            dest.putDouble(offset+12, m.m10());
            dest.putDouble(offset+16, m.m11());
            dest.putDouble(offset+20, m.m12());
            dest.putDouble(offset+24, m.m20());
            dest.putDouble(offset+28, m.m21());
            dest.putDouble(offset+32, m.m22());
            dest.putDouble(offset+36, m.m30());
            dest.putDouble(offset+40, m.m31());
            dest.putDouble(offset+44, m.m32());
        }

        public void putf(Matrix4d m, int offset, FloatBuffer dest) {
            dest.put(offset,    (float)m.m00());
            dest.put(offset+1,  (float)m.m01());
            dest.put(offset+2,  (float)m.m02());
            dest.put(offset+3,  (float)m.m03());
            dest.put(offset+4,  (float)m.m10());
            dest.put(offset+5,  (float)m.m11());
            dest.put(offset+6,  (float)m.m12());
            dest.put(offset+7,  (float)m.m13());
            dest.put(offset+8,  (float)m.m20());
            dest.put(offset+9,  (float)m.m21());
            dest.put(offset+10, (float)m.m22());
            dest.put(offset+11, (float)m.m23());
            dest.put(offset+12, (float)m.m30());
            dest.put(offset+13, (float)m.m31());
            dest.put(offset+14, (float)m.m32());
            dest.put(offset+15, (float)m.m33());
        }

        public void putf(Matrix4d m, int offset, ByteBuffer dest) {
            dest.putFloat(offset,    (float)m.m00());
            dest.putFloat(offset+4,  (float)m.m01());
            dest.putFloat(offset+8,  (float)m.m02());
            dest.putFloat(offset+12, (float)m.m03());
            dest.putFloat(offset+16, (float)m.m10());
            dest.putFloat(offset+20, (float)m.m11());
            dest.putFloat(offset+24, (float)m.m12());
            dest.putFloat(offset+28, (float)m.m13());
            dest.putFloat(offset+32, (float)m.m20());
            dest.putFloat(offset+36, (float)m.m21());
            dest.putFloat(offset+40, (float)m.m22());
            dest.putFloat(offset+44, (float)m.m23());
            dest.putFloat(offset+48, (float)m.m30());
            dest.putFloat(offset+52, (float)m.m31());
            dest.putFloat(offset+56, (float)m.m32());
            dest.putFloat(offset+60, (float)m.m33());
        }

        public void putf(Matrix4x3d m, int offset, FloatBuffer dest) {
            dest.put(offset,    (float)m.m00());
            dest.put(offset+1,  (float)m.m01());
            dest.put(offset+2,  (float)m.m02());
            dest.put(offset+3,  (float)m.m10());
            dest.put(offset+4,  (float)m.m11());
            dest.put(offset+5,  (float)m.m12());
            dest.put(offset+6,  (float)m.m20());
            dest.put(offset+7,  (float)m.m21());
            dest.put(offset+8,  (float)m.m22());
            dest.put(offset+9,  (float)m.m30());
            dest.put(offset+10, (float)m.m31());
            dest.put(offset+11, (float)m.m32());
        }

        public void putf(Matrix4x3d m, int offset, ByteBuffer dest) {
            dest.putFloat(offset,    (float)m.m00());
            dest.putFloat(offset+4,  (float)m.m01());
            dest.putFloat(offset+8,  (float)m.m02());
            dest.putFloat(offset+12, (float)m.m10());
            dest.putFloat(offset+16, (float)m.m11());
            dest.putFloat(offset+20, (float)m.m12());
            dest.putFloat(offset+24, (float)m.m20());
            dest.putFloat(offset+28, (float)m.m21());
            dest.putFloat(offset+32, (float)m.m22());
            dest.putFloat(offset+36, (float)m.m30());
            dest.putFloat(offset+40, (float)m.m31());
            dest.putFloat(offset+44, (float)m.m32());
        }

        public void putTransposed(Matrix4d m, int offset, DoubleBuffer dest) {
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

        public void putTransposed(Matrix4d m, int offset, ByteBuffer dest) {
            dest.putDouble(offset,     m.m00());
            dest.putDouble(offset+8,   m.m10());
            dest.putDouble(offset+16,  m.m20());
            dest.putDouble(offset+24,  m.m30());
            dest.putDouble(offset+32,  m.m01());
            dest.putDouble(offset+40,  m.m11());
            dest.putDouble(offset+48,  m.m21());
            dest.putDouble(offset+56,  m.m31());
            dest.putDouble(offset+64,  m.m02());
            dest.putDouble(offset+72,  m.m12());
            dest.putDouble(offset+80,  m.m22());
            dest.putDouble(offset+88,  m.m32());
            dest.putDouble(offset+96,  m.m03());
            dest.putDouble(offset+104, m.m13());
            dest.putDouble(offset+112, m.m23());
            dest.putDouble(offset+120, m.m33());
        }

        public void put4x3Transposed(Matrix4d m, int offset, DoubleBuffer dest) {
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
        }

        public void put4x3Transposed(Matrix4d m, int offset, ByteBuffer dest) {
            dest.putDouble(offset,     m.m00());
            dest.putDouble(offset+8,   m.m10());
            dest.putDouble(offset+16,  m.m20());
            dest.putDouble(offset+24,  m.m30());
            dest.putDouble(offset+32,  m.m01());
            dest.putDouble(offset+40,  m.m11());
            dest.putDouble(offset+48,  m.m21());
            dest.putDouble(offset+56,  m.m31());
            dest.putDouble(offset+64,  m.m02());
            dest.putDouble(offset+72,  m.m12());
            dest.putDouble(offset+80,  m.m22());
            dest.putDouble(offset+88,  m.m32());
        }

        public void putTransposed(Matrix4x3d m, int offset, DoubleBuffer dest) {
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
        }

        public void putTransposed(Matrix4x3d m, int offset, ByteBuffer dest) {
            dest.putDouble(offset,    m.m00());
            dest.putDouble(offset+4,  m.m10());
            dest.putDouble(offset+8,  m.m20());
            dest.putDouble(offset+12, m.m30());
            dest.putDouble(offset+16, m.m01());
            dest.putDouble(offset+20, m.m11());
            dest.putDouble(offset+24, m.m21());
            dest.putDouble(offset+28, m.m31());
            dest.putDouble(offset+32, m.m02());
            dest.putDouble(offset+36, m.m12());
            dest.putDouble(offset+40, m.m22());
            dest.putDouble(offset+44, m.m32());
        }

        public void putfTransposed(Matrix4x3d m, int offset, FloatBuffer dest) {
            dest.put(offset,    (float)m.m00());
            dest.put(offset+1,  (float)m.m10());
            dest.put(offset+2,  (float)m.m20());
            dest.put(offset+3,  (float)m.m30());
            dest.put(offset+4,  (float)m.m01());
            dest.put(offset+5,  (float)m.m11());
            dest.put(offset+6,  (float)m.m21());
            dest.put(offset+7,  (float)m.m31());
            dest.put(offset+8,  (float)m.m02());
            dest.put(offset+9,  (float)m.m12());
            dest.put(offset+10, (float)m.m22());
            dest.put(offset+11, (float)m.m32());
        }

        public void putfTransposed(Matrix4x3d m, int offset, ByteBuffer dest) {
            dest.putFloat(offset,    (float)m.m00());
            dest.putFloat(offset+4,  (float)m.m10());
            dest.putFloat(offset+8,  (float)m.m20());
            dest.putFloat(offset+12, (float)m.m30());
            dest.putFloat(offset+16, (float)m.m01());
            dest.putFloat(offset+20, (float)m.m11());
            dest.putFloat(offset+24, (float)m.m21());
            dest.putFloat(offset+28, (float)m.m31());
            dest.putFloat(offset+32, (float)m.m02());
            dest.putFloat(offset+36, (float)m.m12());
            dest.putFloat(offset+40, (float)m.m22());
            dest.putFloat(offset+44, (float)m.m32());
        }

        public void putfTransposed(Matrix4d m, int offset, FloatBuffer dest) {
            dest.put(offset,    (float)m.m00());
            dest.put(offset+1,  (float)m.m10());
            dest.put(offset+2,  (float)m.m20());
            dest.put(offset+3,  (float)m.m30());
            dest.put(offset+4,  (float)m.m01());
            dest.put(offset+5,  (float)m.m11());
            dest.put(offset+6,  (float)m.m21());
            dest.put(offset+7,  (float)m.m31());
            dest.put(offset+8,  (float)m.m02());
            dest.put(offset+9,  (float)m.m12());
            dest.put(offset+10, (float)m.m22());
            dest.put(offset+11, (float)m.m32());
            dest.put(offset+12, (float)m.m03());
            dest.put(offset+13, (float)m.m13());
            dest.put(offset+14, (float)m.m23());
            dest.put(offset+15, (float)m.m33());
        }

        public void putfTransposed(Matrix4d m, int offset, ByteBuffer dest) {
            dest.putFloat(offset,    (float)m.m00());
            dest.putFloat(offset+4,  (float)m.m10());
            dest.putFloat(offset+8,  (float)m.m20());
            dest.putFloat(offset+12, (float)m.m30());
            dest.putFloat(offset+16, (float)m.m01());
            dest.putFloat(offset+20, (float)m.m11());
            dest.putFloat(offset+24, (float)m.m21());
            dest.putFloat(offset+28, (float)m.m31());
            dest.putFloat(offset+32, (float)m.m02());
            dest.putFloat(offset+36, (float)m.m12());
            dest.putFloat(offset+40, (float)m.m22());
            dest.putFloat(offset+44, (float)m.m32());
            dest.putFloat(offset+48, (float)m.m03());
            dest.putFloat(offset+52, (float)m.m13());
            dest.putFloat(offset+56, (float)m.m23());
            dest.putFloat(offset+60, (float)m.m33());
        }

        public void put(Matrix3f m, int offset, FloatBuffer dest) {
            dest.put(offset,   m.m00());
            dest.put(offset+1, m.m01());
            dest.put(offset+2, m.m02());
            dest.put(offset+3, m.m10());
            dest.put(offset+4, m.m11());
            dest.put(offset+5, m.m12());
            dest.put(offset+6, m.m20());
            dest.put(offset+7, m.m21());
            dest.put(offset+8, m.m22());
        }

        public void put(Matrix3f m, int offset, ByteBuffer dest) {
            dest.putFloat(offset,    m.m00());
            dest.putFloat(offset+4,  m.m01());
            dest.putFloat(offset+8,  m.m02());
            dest.putFloat(offset+12, m.m10());
            dest.putFloat(offset+16, m.m11());
            dest.putFloat(offset+20, m.m12());
            dest.putFloat(offset+24, m.m20());
            dest.putFloat(offset+28, m.m21());
            dest.putFloat(offset+32, m.m22());
        }

        public void put(Matrix3d m, int offset, DoubleBuffer dest) {
            dest.put(offset,   m.m00());
            dest.put(offset+1, m.m01());
            dest.put(offset+2, m.m02());
            dest.put(offset+3, m.m10());
            dest.put(offset+4, m.m11());
            dest.put(offset+5, m.m12());
            dest.put(offset+6, m.m20());
            dest.put(offset+7, m.m21());
            dest.put(offset+8, m.m22());
        }

        public void put(Matrix3d m, int offset, ByteBuffer dest) {
            dest.putDouble(offset,    m.m00());
            dest.putDouble(offset+8,  m.m01());
            dest.putDouble(offset+16, m.m02());
            dest.putDouble(offset+24, m.m10());
            dest.putDouble(offset+32, m.m11());
            dest.putDouble(offset+40, m.m12());
            dest.putDouble(offset+48, m.m20());
            dest.putDouble(offset+56, m.m21());
            dest.putDouble(offset+64, m.m22());
        }

        public void put(Matrix3x2f m, int offset, FloatBuffer dest) {
            dest.put(offset,   m.m00());
            dest.put(offset+1, m.m01());
            dest.put(offset+2, m.m10());
            dest.put(offset+3, m.m11());
            dest.put(offset+4, m.m20());
            dest.put(offset+5, m.m21());
        }

        public void put(Matrix3x2f m, int offset, ByteBuffer dest) {
            dest.putFloat(offset,    m.m00());
            dest.putFloat(offset+4,  m.m01());
            dest.putFloat(offset+8,  m.m10());
            dest.putFloat(offset+12, m.m11());
            dest.putFloat(offset+16, m.m20());
            dest.putFloat(offset+20, m.m21());
        }

        public void put(Matrix3x2d m, int offset, DoubleBuffer dest) {
            dest.put(offset,   m.m00());
            dest.put(offset+1, m.m01());
            dest.put(offset+2, m.m10());
            dest.put(offset+3, m.m11());
            dest.put(offset+4, m.m20());
            dest.put(offset+5, m.m21());
        }

        public void put(Matrix3x2d m, int offset, ByteBuffer dest) {
            dest.putDouble(offset,    m.m00());
            dest.putDouble(offset+8,  m.m01());
            dest.putDouble(offset+16, m.m10());
            dest.putDouble(offset+24, m.m11());
            dest.putDouble(offset+32, m.m20());
            dest.putDouble(offset+40, m.m21());
        }

        public void putf(Matrix3d m, int offset, FloatBuffer dest) {
            dest.put(offset,   (float)m.m00());
            dest.put(offset+1, (float)m.m01());
            dest.put(offset+2, (float)m.m02());
            dest.put(offset+3, (float)m.m10());
            dest.put(offset+4, (float)m.m11());
            dest.put(offset+5, (float)m.m12());
            dest.put(offset+6, (float)m.m20());
            dest.put(offset+7, (float)m.m21());
            dest.put(offset+8, (float)m.m22());
        }

        public void putf(Matrix3d m, int offset, ByteBuffer dest) {
            dest.putFloat(offset,    (float)m.m00());
            dest.putFloat(offset+4,  (float)m.m01());
            dest.putFloat(offset+8,  (float)m.m02());
            dest.putFloat(offset+12, (float)m.m10());
            dest.putFloat(offset+16, (float)m.m11());
            dest.putFloat(offset+20, (float)m.m12());
            dest.putFloat(offset+24, (float)m.m20());
            dest.putFloat(offset+28, (float)m.m21());
            dest.putFloat(offset+32, (float)m.m22());
        }

        public void put(Vector4d src, int offset, DoubleBuffer dest) {
            dest.put(offset,   src.x);
            dest.put(offset+1, src.y);
            dest.put(offset+2, src.z);
            dest.put(offset+3, src.w);
        }

        public void put(Vector4d src, int offset, ByteBuffer dest) {
            dest.putDouble(offset,    src.x);
            dest.putDouble(offset+8,  src.y);
            dest.putDouble(offset+16, src.z);
            dest.putDouble(offset+24, src.w);
        }

        public void put(Vector4f src, int offset, FloatBuffer dest) {
            dest.put(offset,   src.x);
            dest.put(offset+1, src.y);
            dest.put(offset+2, src.z);
            dest.put(offset+3, src.w);
        }

        public void put(Vector4f src, int offset, ByteBuffer dest) {
            dest.putFloat(offset,    src.x);
            dest.putFloat(offset+4,  src.y);
            dest.putFloat(offset+8,  src.z);
            dest.putFloat(offset+12, src.w);
        }

        public void put(Vector4i src, int offset, IntBuffer dest) {
            dest.put(offset,   src.x);
            dest.put(offset+1, src.y);
            dest.put(offset+2, src.z);
            dest.put(offset+3, src.w);
        }

        public void put(Vector4i src, int offset, ByteBuffer dest) {
            dest.putInt(offset,    src.x);
            dest.putInt(offset+4,  src.y);
            dest.putInt(offset+8,  src.z);
            dest.putInt(offset+12, src.w);
        }

        public void put(Vector3f src, int offset, FloatBuffer dest) {
            dest.put(offset,   src.x);
            dest.put(offset+1, src.y);
            dest.put(offset+2, src.z);
        }

        public void put(Vector3f src, int offset, ByteBuffer dest) {
            dest.putFloat(offset,   src.x);
            dest.putFloat(offset+4, src.y);
            dest.putFloat(offset+8, src.z);
        }

        public void put(Vector3d src, int offset, DoubleBuffer dest) {
            dest.put(offset,   src.x);
            dest.put(offset+1, src.y);
            dest.put(offset+2, src.z);
        }

        public void put(Vector3d src, int offset, ByteBuffer dest) {
            dest.putDouble(offset,    src.x);
            dest.putDouble(offset+8,  src.y);
            dest.putDouble(offset+16, src.z);
        }

        public void put(Vector3i src, int offset, IntBuffer dest) {
            dest.put(offset,   src.x);
            dest.put(offset+1, src.y);
            dest.put(offset+2, src.z);
        }

        public void put(Vector3i src, int offset, ByteBuffer dest) {
            dest.putInt(offset,   src.x);
            dest.putInt(offset+4, src.y);
            dest.putInt(offset+8, src.z);
        }

        public void put(Vector2f src, int offset, FloatBuffer dest) {
            dest.put(offset,   src.x);
            dest.put(offset+1, src.y);
        }

        public void put(Vector2f src, int offset, ByteBuffer dest) {
            dest.putFloat(offset,   src.x);
            dest.putFloat(offset+4, src.y);
        }

        public void put(Vector2d src, int offset, DoubleBuffer dest) {
            dest.put(offset,   src.x);
            dest.put(offset+1, src.y);
        }

        public void put(Vector2d src, int offset, ByteBuffer dest) {
            dest.putDouble(offset,   src.x);
            dest.putDouble(offset+8, src.y);
        }

        public void put(Vector2i src, int offset, IntBuffer dest) {
            dest.put(offset,   src.x);
            dest.put(offset+1, src.y);
        }

        public void put(Vector2i src, int offset, ByteBuffer dest) {
            dest.putInt(offset,   src.x);
            dest.putInt(offset+4, src.y);
        }

        public void get(Matrix4f m, int offset, FloatBuffer src) {
            m._m00(src.get(offset));
            m._m01(src.get(offset+1));
            m._m02(src.get(offset+2));
            m._m03(src.get(offset+3));
            m._m10(src.get(offset+4));
            m._m11(src.get(offset+5));
            m._m12(src.get(offset+6));
            m._m13(src.get(offset+7));
            m._m20(src.get(offset+8));
            m._m21(src.get(offset+9));
            m._m22(src.get(offset+10));
            m._m23(src.get(offset+11));
            m._m30(src.get(offset+12));
            m._m31(src.get(offset+13));
            m._m32(src.get(offset+14));
            m._m33(src.get(offset+15));
        }

        public void get(Matrix4f m, int offset, ByteBuffer src) {
            m._m00(src.getFloat(offset));
            m._m01(src.getFloat(offset+4));
            m._m02(src.getFloat(offset+8));
            m._m03(src.getFloat(offset+12));
            m._m10(src.getFloat(offset+16));
            m._m11(src.getFloat(offset+20));
            m._m12(src.getFloat(offset+24));
            m._m13(src.getFloat(offset+28));
            m._m20(src.getFloat(offset+32));
            m._m21(src.getFloat(offset+36));
            m._m22(src.getFloat(offset+40));
            m._m23(src.getFloat(offset+44));
            m._m30(src.getFloat(offset+48));
            m._m31(src.getFloat(offset+52));
            m._m32(src.getFloat(offset+56));
            m._m33(src.getFloat(offset+60));
        }

        public void get(Matrix4x3f m, int offset, FloatBuffer src) {
            m._m00(src.get(offset));
            m._m01(src.get(offset+1));
            m._m02(src.get(offset+2));
            m._m10(src.get(offset+3));
            m._m11(src.get(offset+4));
            m._m12(src.get(offset+5));
            m._m20(src.get(offset+6));
            m._m21(src.get(offset+7));
            m._m22(src.get(offset+8));
            m._m30(src.get(offset+9));
            m._m31(src.get(offset+10));
            m._m32(src.get(offset+11));
        }

        public void get(Matrix4x3f m, int offset, ByteBuffer src) {
            m._m00(src.getFloat(offset));
            m._m01(src.getFloat(offset+4));
            m._m02(src.getFloat(offset+8));
            m._m10(src.getFloat(offset+12));
            m._m11(src.getFloat(offset+16));
            m._m12(src.getFloat(offset+20));
            m._m20(src.getFloat(offset+24));
            m._m21(src.getFloat(offset+28));
            m._m22(src.getFloat(offset+32));
            m._m30(src.getFloat(offset+36));
            m._m31(src.getFloat(offset+40));
            m._m32(src.getFloat(offset+44));
        }

        public void get(Matrix4d m, int offset, DoubleBuffer src) {
            m._m00(src.get(offset));
            m._m01(src.get(offset+1));
            m._m02(src.get(offset+2));
            m._m03(src.get(offset+3));
            m._m10(src.get(offset+4));
            m._m11(src.get(offset+5));
            m._m12(src.get(offset+6));
            m._m13(src.get(offset+7));
            m._m20(src.get(offset+8));
            m._m21(src.get(offset+9));
            m._m22(src.get(offset+10));
            m._m23(src.get(offset+11));
            m._m30(src.get(offset+12));
            m._m31(src.get(offset+13));
            m._m32(src.get(offset+14));
            m._m33(src.get(offset+15));
        }

        public void get(Matrix4d m, int offset, ByteBuffer src) {
            m._m00(src.getDouble(offset));
            m._m01(src.getDouble(offset+8));
            m._m02(src.getDouble(offset+16));
            m._m03(src.getDouble(offset+24));
            m._m10(src.getDouble(offset+32));
            m._m11(src.getDouble(offset+40));
            m._m12(src.getDouble(offset+48));
            m._m13(src.getDouble(offset+56));
            m._m20(src.getDouble(offset+64));
            m._m21(src.getDouble(offset+72));
            m._m22(src.getDouble(offset+80));
            m._m23(src.getDouble(offset+88));
            m._m30(src.getDouble(offset+96));
            m._m31(src.getDouble(offset+104));
            m._m32(src.getDouble(offset+112));
            m._m33(src.getDouble(offset+120));
        }

        public void get(Matrix4x3d m, int offset, DoubleBuffer src) {
            m._m00(src.get(offset));
            m._m01(src.get(offset+1));
            m._m02(src.get(offset+2));
            m._m10(src.get(offset+3));
            m._m11(src.get(offset+4));
            m._m12(src.get(offset+5));
            m._m20(src.get(offset+6));
            m._m21(src.get(offset+7));
            m._m22(src.get(offset+8));
            m._m30(src.get(offset+9));
            m._m31(src.get(offset+10));
            m._m32(src.get(offset+11));
        }

        public void get(Matrix4x3d m, int offset, ByteBuffer src) {
            m._m00(src.getDouble(offset));
            m._m01(src.getDouble(offset+8));
            m._m02(src.getDouble(offset+16));
            m._m10(src.getDouble(offset+24));
            m._m11(src.getDouble(offset+32));
            m._m12(src.getDouble(offset+40));
            m._m20(src.getDouble(offset+48));
            m._m21(src.getDouble(offset+56));
            m._m22(src.getDouble(offset+64));
            m._m30(src.getDouble(offset+72));
            m._m31(src.getDouble(offset+80));
            m._m32(src.getDouble(offset+88));
        }

        public void getf(Matrix4d m, int offset, FloatBuffer src) {
            m._m00(src.get(offset));
            m._m01(src.get(offset+1));
            m._m02(src.get(offset+2));
            m._m03(src.get(offset+3));
            m._m10(src.get(offset+4));
            m._m11(src.get(offset+5));
            m._m12(src.get(offset+6));
            m._m13(src.get(offset+7));
            m._m20(src.get(offset+8));
            m._m21(src.get(offset+9));
            m._m22(src.get(offset+10));
            m._m23(src.get(offset+11));
            m._m30(src.get(offset+12));
            m._m31(src.get(offset+13));
            m._m32(src.get(offset+14));
            m._m33(src.get(offset+15));
        }

        public void getf(Matrix4d m, int offset, ByteBuffer src) {
            m._m00(src.getFloat(offset));
            m._m01(src.getFloat(offset+4));
            m._m02(src.getFloat(offset+8));
            m._m03(src.getFloat(offset+12));
            m._m10(src.getFloat(offset+16));
            m._m11(src.getFloat(offset+20));
            m._m12(src.getFloat(offset+24));
            m._m13(src.getFloat(offset+28));
            m._m20(src.getFloat(offset+32));
            m._m21(src.getFloat(offset+36));
            m._m22(src.getFloat(offset+40));
            m._m23(src.getFloat(offset+44));
            m._m30(src.getFloat(offset+48));
            m._m31(src.getFloat(offset+52));
            m._m32(src.getFloat(offset+56));
            m._m33(src.getFloat(offset+60));
        }

        public void getf(Matrix4x3d m, int offset, FloatBuffer src) {
            m._m00(src.get(offset));
            m._m01(src.get(offset+1));
            m._m02(src.get(offset+2));
            m._m10(src.get(offset+3));
            m._m11(src.get(offset+4));
            m._m12(src.get(offset+5));
            m._m20(src.get(offset+6));
            m._m21(src.get(offset+7));
            m._m22(src.get(offset+8));
            m._m30(src.get(offset+9));
            m._m31(src.get(offset+10));
            m._m32(src.get(offset+11));
        }

        public void getf(Matrix4x3d m, int offset, ByteBuffer src) {
            m._m00(src.getFloat(offset));
            m._m01(src.getFloat(offset+4));
            m._m02(src.getFloat(offset+8));
            m._m10(src.getFloat(offset+12));
            m._m11(src.getFloat(offset+16));
            m._m12(src.getFloat(offset+20));
            m._m20(src.getFloat(offset+24));
            m._m21(src.getFloat(offset+28));
            m._m22(src.getFloat(offset+32));
            m._m30(src.getFloat(offset+36));
            m._m31(src.getFloat(offset+40));
            m._m32(src.getFloat(offset+44));
        }

        public void get(Matrix3f m, int offset, FloatBuffer src) {
            m._m00(src.get(offset));
            m._m01(src.get(offset+1));
            m._m02(src.get(offset+2));
            m._m10(src.get(offset+3));
            m._m11(src.get(offset+4));
            m._m12(src.get(offset+5));
            m._m20(src.get(offset+6));
            m._m21(src.get(offset+7));
            m._m22(src.get(offset+8));
        }

        public void get(Matrix3f m, int offset, ByteBuffer src) {
            m._m00(src.getFloat(offset));
            m._m01(src.getFloat(offset+4));
            m._m02(src.getFloat(offset+8));
            m._m10(src.getFloat(offset+12));
            m._m11(src.getFloat(offset+16));
            m._m12(src.getFloat(offset+20));
            m._m20(src.getFloat(offset+24));
            m._m21(src.getFloat(offset+28));
            m._m22(src.getFloat(offset+32));
        }

        public void get(Matrix3d m, int offset, DoubleBuffer src) {
            m._m00(src.get(offset));
            m._m01(src.get(offset+1));
            m._m02(src.get(offset+2));
            m._m10(src.get(offset+3));
            m._m11(src.get(offset+4));
            m._m12(src.get(offset+5));
            m._m20(src.get(offset+6));
            m._m21(src.get(offset+7));
            m._m22(src.get(offset+8));
        }

        public void get(Matrix3d m, int offset, ByteBuffer src) {
            m._m00(src.getDouble(offset));
            m._m01(src.getDouble(offset+8));
            m._m02(src.getDouble(offset+16));
            m._m10(src.getDouble(offset+24));
            m._m11(src.getDouble(offset+32));
            m._m12(src.getDouble(offset+40));
            m._m20(src.getDouble(offset+48));
            m._m21(src.getDouble(offset+56));
            m._m22(src.getDouble(offset+64));
        }

        public void get(Matrix3x2f m, int offset, FloatBuffer src) {
            m._m00(src.get(offset));
            m._m01(src.get(offset+1));
            m._m10(src.get(offset+2));
            m._m11(src.get(offset+3));
            m._m20(src.get(offset+4));
            m._m21(src.get(offset+5));
        }

        public void get(Matrix3x2f m, int offset, ByteBuffer src) {
            m._m00(src.getFloat(offset));
            m._m01(src.getFloat(offset+4));
            m._m10(src.getFloat(offset+8));
            m._m11(src.getFloat(offset+12));
            m._m20(src.getFloat(offset+16));
            m._m21(src.getFloat(offset+20));
        }

        public void get(Matrix3x2d m, int offset, DoubleBuffer src) {
            m._m00(src.get(offset));
            m._m01(src.get(offset+1));
            m._m10(src.get(offset+2));
            m._m11(src.get(offset+3));
            m._m20(src.get(offset+4));
            m._m21(src.get(offset+5));
        }

        public void get(Matrix3x2d m, int offset, ByteBuffer src) {
            m._m00(src.getDouble(offset));
            m._m01(src.getDouble(offset+8));
            m._m10(src.getDouble(offset+16));
            m._m11(src.getDouble(offset+24));
            m._m20(src.getDouble(offset+32));
            m._m21(src.getDouble(offset+40));
        }

        public void getf(Matrix3d m, int offset, FloatBuffer src) {
            m._m00(src.get(offset));
            m._m01(src.get(offset+1));
            m._m02(src.get(offset+2));
            m._m10(src.get(offset+3));
            m._m11(src.get(offset+4));
            m._m12(src.get(offset+5));
            m._m20(src.get(offset+6));
            m._m21(src.get(offset+7));
            m._m22(src.get(offset+8));
        }

        public void getf(Matrix3d m, int offset, ByteBuffer src) {
            m._m00(src.getFloat(offset));
            m._m01(src.getFloat(offset+4));
            m._m02(src.getFloat(offset+8));
            m._m10(src.getFloat(offset+12));
            m._m11(src.getFloat(offset+16));
            m._m12(src.getFloat(offset+20));
            m._m20(src.getFloat(offset+24));
            m._m21(src.getFloat(offset+28));
            m._m22(src.getFloat(offset+32));
        }

        public void get(Vector4d dst, int offset, DoubleBuffer src) {
            dst.x = src.get(offset);
            dst.y = src.get(offset+1);
            dst.z = src.get(offset+2);
            dst.w = src.get(offset+3);
        }

        public void get(Vector4d dst, int offset, ByteBuffer src) {
            dst.x = src.getDouble(offset);
            dst.y = src.getDouble(offset+8);
            dst.z = src.getDouble(offset+16);
            dst.w = src.getDouble(offset+24);
        }

        public void get(Vector4f dst, int offset, FloatBuffer src) {
            dst.x = src.get(offset);
            dst.y = src.get(offset+1);
            dst.z = src.get(offset+2);
            dst.w = src.get(offset+3);
        }

        public void get(Vector4f dst, int offset, ByteBuffer src) {
            dst.x = src.getFloat(offset);
            dst.y = src.getFloat(offset+4);
            dst.z = src.getFloat(offset+8);
            dst.w = src.getFloat(offset+12);
        }

        public void get(Vector4i dst, int offset, IntBuffer src) {
            dst.x = src.get(offset);
            dst.y = src.get(offset+1);
            dst.z = src.get(offset+2);
            dst.w = src.get(offset+3);
        }

        public void get(Vector4i dst, int offset, ByteBuffer src) {
            dst.x = src.getInt(offset);
            dst.y = src.getInt(offset+4);
            dst.z = src.getInt(offset+8);
            dst.w = src.getInt(offset+12);
        }

        public void get(Vector3f dst, int offset, FloatBuffer src) {
            dst.x = src.get(offset);
            dst.y = src.get(offset+1);
            dst.z = src.get(offset+2);
        }

        public void get(Vector3f dst, int offset, ByteBuffer src) {
            dst.x = src.getFloat(offset);
            dst.y = src.getFloat(offset+4);
            dst.z = src.getFloat(offset+8);
        }

        public void get(Vector3d dst, int offset, DoubleBuffer src) {
            dst.x = src.get(offset);
            dst.y = src.get(offset+1);
            dst.z = src.get(offset+2);
        }

        public void get(Vector3d dst, int offset, ByteBuffer src) {
            dst.x = src.getDouble(offset);
            dst.y = src.getDouble(offset+8);
            dst.z = src.getDouble(offset+16);
        }

        public void get(Vector3i dst, int offset, IntBuffer src) {
            dst.x = src.get(offset);
            dst.y = src.get(offset+1);
            dst.z = src.get(offset+2);
        }

        public void get(Vector3i dst, int offset, ByteBuffer src) {
            dst.x = src.getInt(offset);
            dst.y = src.getInt(offset+4);
            dst.z = src.getInt(offset+8);
        }

        public void get(Vector2f dst, int offset, FloatBuffer src) {
            dst.x = src.get(offset);
            dst.y = src.get(offset+1);
        }

        public void get(Vector2f dst, int offset, ByteBuffer src) {
            dst.x = src.getFloat(offset);
            dst.y = src.getFloat(offset+4);
        }

        public void get(Vector2d dst, int offset, DoubleBuffer src) {
            dst.x = src.get(offset);
            dst.y = src.get(offset+1);
        }

        public void get(Vector2d dst, int offset, ByteBuffer src) {
            dst.x = src.getDouble(offset);
            dst.y = src.getDouble(offset+8);
        }

        public void get(Vector2i dst, int offset, IntBuffer src) {
            dst.x = src.get(offset);
            dst.y = src.get(offset+1);
        }

        public void get(Vector2i dst, int offset, ByteBuffer src) {
            dst.x = src.getInt(offset);
            dst.y = src.getInt(offset+4);
        }
//#endif

        public void copy(Matrix4f src, Matrix4f dest) {
            dest._m00(src.m00());
            dest._m01(src.m01());
            dest._m02(src.m02());
            dest._m03(src.m03());
            dest._m10(src.m10());
            dest._m11(src.m11());
            dest._m12(src.m12());
            dest._m13(src.m13());
            dest._m20(src.m20());
            dest._m21(src.m21());
            dest._m22(src.m22());
            dest._m23(src.m23());
            dest._m30(src.m30());
            dest._m31(src.m31());
            dest._m32(src.m32());
            dest._m33(src.m33());
        }

        public void copy(Matrix3f src, Matrix4f dest) {
            dest._m00(src.m00());
            dest._m01(src.m01());
            dest._m02(src.m02());
            dest._m03(0.0f);
            dest._m10(src.m10());
            dest._m11(src.m11());
            dest._m12(src.m12());
            dest._m13(0.0f);
            dest._m20(src.m20());
            dest._m21(src.m21());
            dest._m22(src.m22());
            dest._m23(0.0f);
            dest._m30(0.0f);
            dest._m31(0.0f);
            dest._m32(0.0f);
            dest._m33(1.0f);
        }

        public void copy(Matrix4f src, Matrix3f dest) {
            dest._m00(src.m00());
            dest._m01(src.m01());
            dest._m02(src.m02());
            dest._m10(src.m10());
            dest._m11(src.m11());
            dest._m12(src.m12());
            dest._m20(src.m20());
            dest._m21(src.m21());
            dest._m22(src.m22());
        }

        public void copy(Matrix3f src, Matrix4x3f dest) {
            dest._m00(src.m00());
            dest._m01(src.m01());
            dest._m02(src.m02());
            dest._m10(src.m10());
            dest._m11(src.m11());
            dest._m12(src.m12());
            dest._m20(src.m20());
            dest._m21(src.m21());
            dest._m22(src.m22());
            dest._m30(0.0f);
            dest._m31(0.0f);
            dest._m32(0.0f);
        }

        public void copy(Matrix3x2f src, Matrix3x2f dest) {
            dest._m00(src.m00());
            dest._m01(src.m01());
            dest._m10(src.m10());
            dest._m11(src.m11());
            dest._m20(src.m20());
            dest._m21(src.m21());
        }

        public void copy(Matrix3x2d src, Matrix3x2d dest) {
            dest._m00(src.m00());
            dest._m01(src.m01());
            dest._m10(src.m10());
            dest._m11(src.m11());
            dest._m20(src.m20());
            dest._m21(src.m21());
        }

        public void copy3x3(Matrix4f src, Matrix4f dest) {
            dest._m00(src.m00());
            dest._m01(src.m01());
            dest._m02(src.m02());
            dest._m10(src.m10());
            dest._m11(src.m11());
            dest._m12(src.m12());
            dest._m20(src.m20());
            dest._m21(src.m21());
            dest._m22(src.m22());
        }

        public void copy3x3(Matrix4x3f src, Matrix4x3f dest) {
            dest._m00(src.m00());
            dest._m01(src.m01());
            dest._m02(src.m02());
            dest._m10(src.m10());
            dest._m11(src.m11());
            dest._m12(src.m12());
            dest._m20(src.m20());
            dest._m21(src.m21());
            dest._m22(src.m22());
        }

        public void copy3x3(Matrix3f src, Matrix4x3f dest) {
            dest._m00(src.m00());
            dest._m01(src.m01());
            dest._m02(src.m02());
            dest._m10(src.m10());
            dest._m11(src.m11());
            dest._m12(src.m12());
            dest._m20(src.m20());
            dest._m21(src.m21());
            dest._m22(src.m22());
        }

        public void copy3x3(Matrix3f src, Matrix4f dest) {
            dest._m00(src.m00());
            dest._m01(src.m01());
            dest._m02(src.m02());
            dest._m10(src.m10());
            dest._m11(src.m11());
            dest._m12(src.m12());
            dest._m20(src.m20());
            dest._m21(src.m21());
            dest._m22(src.m22());
        }

        public void copy4x3(Matrix4x3f src, Matrix4f dest) {
            dest._m00(src.m00());
            dest._m01(src.m01());
            dest._m02(src.m02());
            dest._m10(src.m10());
            dest._m11(src.m11());
            dest._m12(src.m12());
            dest._m20(src.m20());
            dest._m21(src.m21());
            dest._m22(src.m22());
            dest._m30(src.m30());
            dest._m31(src.m31());
            dest._m32(src.m32());
        }

        public void copy(Vector4f src, Vector4f dst) {
            dst.x = src.x;
            dst.y = src.y;
            dst.z = src.z;
            dst.w = src.w;
        }

        public void copy(Vector4i src, Vector4i dst) {
            dst.x = src.x;
            dst.y = src.y;
            dst.z = src.z;
            dst.w = src.w;
        }

        public void copy(Quaternionf src, Quaternionf dst) {
            dst.x = src.x;
            dst.y = src.y;
            dst.z = src.z;
            dst.w = src.w;
        }

        public void copy4x3(Matrix4f src, Matrix4f dest) {
            dest._m00(src.m00());
            dest._m01(src.m01());
            dest._m02(src.m02());
            dest._m10(src.m10());
            dest._m11(src.m11());
            dest._m12(src.m12());
            dest._m20(src.m20());
            dest._m21(src.m21());
            dest._m22(src.m22());
            dest._m30(src.m30());
            dest._m31(src.m31());
            dest._m32(src.m32());
        }

        public void copy(Matrix4f src, Matrix4x3f dest) {
            dest._m00(src.m00());
            dest._m01(src.m01());
            dest._m02(src.m02());
            dest._m10(src.m10());
            dest._m11(src.m11());
            dest._m12(src.m12());
            dest._m20(src.m20());
            dest._m21(src.m21());
            dest._m22(src.m22());
            dest._m30(src.m30());
            dest._m31(src.m31());
            dest._m32(src.m32());
        }

        public void copy(Matrix4x3f src, Matrix4f dest) {
            dest._m00(src.m00());
            dest._m01(src.m01());
            dest._m02(src.m02());
            dest._m03(0.0f);
            dest._m10(src.m10());
            dest._m11(src.m11());
            dest._m12(src.m12());
            dest._m13(0.0f);
            dest._m20(src.m20());
            dest._m21(src.m21());
            dest._m22(src.m22());
            dest._m23(0.0f);
            dest._m30(src.m30());
            dest._m31(src.m31());
            dest._m32(src.m32());
            dest._m33(1.0f);
        }

        public void copy(Matrix4x3f src, Matrix4x3f dest) {
            dest._m00(src.m00());
            dest._m01(src.m01());
            dest._m02(src.m02());
            dest._m10(src.m10());
            dest._m11(src.m11());
            dest._m12(src.m12());
            dest._m20(src.m20());
            dest._m21(src.m21());
            dest._m22(src.m22());
            dest._m30(src.m30());
            dest._m31(src.m31());
            dest._m32(src.m32());
        }

        public void copy(Matrix3f src, Matrix3f dest) {
            dest._m00(src.m00());
            dest._m01(src.m01());
            dest._m02(src.m02());
            dest._m10(src.m10());
            dest._m11(src.m11());
            dest._m12(src.m12());
            dest._m20(src.m20());
            dest._m21(src.m21());
            dest._m22(src.m22());
        }

        public void copy(float[] arr, int off, Matrix4f dest) {
            dest._m00(arr[off+0]);
            dest._m01(arr[off+1]);
            dest._m02(arr[off+2]);
            dest._m03(arr[off+3]);
            dest._m10(arr[off+4]);
            dest._m11(arr[off+5]);
            dest._m12(arr[off+6]);
            dest._m13(arr[off+7]);
            dest._m20(arr[off+8]);
            dest._m21(arr[off+9]);
            dest._m22(arr[off+10]);
            dest._m23(arr[off+11]);
            dest._m30(arr[off+12]);
            dest._m31(arr[off+13]);
            dest._m32(arr[off+14]);
            dest._m33(arr[off+15]);
        }

        public void copy(float[] arr, int off, Matrix3f dest) {
            dest._m00(arr[off+0]);
            dest._m01(arr[off+1]);
            dest._m02(arr[off+2]);
            dest._m10(arr[off+3]);
            dest._m11(arr[off+4]);
            dest._m12(arr[off+5]);
            dest._m20(arr[off+6]);
            dest._m21(arr[off+7]);
            dest._m22(arr[off+8]);
        }

        public void copy(float[] arr, int off, Matrix4x3f dest) {
            dest._m00(arr[off+0]);
            dest._m01(arr[off+1]);
            dest._m02(arr[off+2]);
            dest._m10(arr[off+3]);
            dest._m11(arr[off+4]);
            dest._m12(arr[off+5]);
            dest._m20(arr[off+6]);
            dest._m21(arr[off+7]);
            dest._m22(arr[off+8]);
            dest._m30(arr[off+9]);
            dest._m31(arr[off+10]);
            dest._m32(arr[off+11]);
        }

        public void copy(float[] arr, int off, Matrix3x2f dest) {
            dest._m00(arr[off+0]);
            dest._m01(arr[off+1]);
            dest._m10(arr[off+2]);
            dest._m11(arr[off+3]);
            dest._m20(arr[off+4]);
            dest._m21(arr[off+5]);
        }

        public void copy(double[] arr, int off, Matrix3x2d dest) {
            dest._m00(arr[off+0]);
            dest._m01(arr[off+1]);
            dest._m10(arr[off+2]);
            dest._m11(arr[off+3]);
            dest._m20(arr[off+4]);
            dest._m21(arr[off+5]);
        }

        public void copy(Matrix4f src, float[] dest, int off) {
            dest[off+0]  = src.m00();
            dest[off+1]  = src.m01();
            dest[off+2]  = src.m02();
            dest[off+3]  = src.m03();
            dest[off+4]  = src.m10();
            dest[off+5]  = src.m11();
            dest[off+6]  = src.m12();
            dest[off+7]  = src.m13();
            dest[off+8]  = src.m20();
            dest[off+9]  = src.m21();
            dest[off+10] = src.m22();
            dest[off+11] = src.m23();
            dest[off+12] = src.m30();
            dest[off+13] = src.m31();
            dest[off+14] = src.m32();
            dest[off+15] = src.m33();
        }

        public void copy(Matrix3f src, float[] dest, int off) {
            dest[off+0] = src.m00();
            dest[off+1] = src.m01();
            dest[off+2] = src.m02();
            dest[off+3] = src.m10();
            dest[off+4] = src.m11();
            dest[off+5] = src.m12();
            dest[off+6] = src.m20();
            dest[off+7] = src.m21();
            dest[off+8] = src.m22();
        }

        public void copy(Matrix4x3f src, float[] dest, int off) {
            dest[off+0]  = src.m00();
            dest[off+1]  = src.m01();
            dest[off+2]  = src.m02();
            dest[off+3]  = src.m10();
            dest[off+4]  = src.m11();
            dest[off+5]  = src.m12();
            dest[off+6]  = src.m20();
            dest[off+7]  = src.m21();
            dest[off+8]  = src.m22();
            dest[off+9]  = src.m30();
            dest[off+10] = src.m31();
            dest[off+11] = src.m32();
        }

        public void copy(Matrix3x2f src, float[] dest, int off) {
            dest[off+0] = src.m00();
            dest[off+1] = src.m01();
            dest[off+2] = src.m10();
            dest[off+3] = src.m11();
            dest[off+4] = src.m20();
            dest[off+5] = src.m21();
        }

        public void copy(Matrix3x2d src, double[] dest, int off) {
            dest[off+0] = src.m00();
            dest[off+1] = src.m01();
            dest[off+2] = src.m10();
            dest[off+3] = src.m11();
            dest[off+4] = src.m20();
            dest[off+5] = src.m21();
        }

        public void copy4x4(Matrix4x3f src, float[] dest, int off) {
            dest[off+0]  = src.m00();
            dest[off+1]  = src.m01();
            dest[off+2]  = src.m02();
            dest[off+3]  = 0.0f;
            dest[off+4]  = src.m10();
            dest[off+5]  = src.m11();
            dest[off+6]  = src.m12();
            dest[off+7]  = 0.0f;
            dest[off+8]  = src.m20();
            dest[off+9]  = src.m21();
            dest[off+10] = src.m22();
            dest[off+11] = 0.0f;
            dest[off+12] = src.m30();
            dest[off+13] = src.m31();
            dest[off+14] = src.m32();
            dest[off+15] = 1.0f;
        }

        public void copy4x4(Matrix4x3d src, float[] dest, int off) {
            dest[off+0]  = (float) src.m00();
            dest[off+1]  = (float) src.m01();
            dest[off+2]  = (float) src.m02();
            dest[off+3]  = 0.0f;
            dest[off+4]  = (float) src.m10();
            dest[off+5]  = (float) src.m11();
            dest[off+6]  = (float) src.m12();
            dest[off+7]  = 0.0f;
            dest[off+8]  = (float) src.m20();
            dest[off+9]  = (float) src.m21();
            dest[off+10] = (float) src.m22();
            dest[off+11] = 0.0f;
            dest[off+12] = (float) src.m30();
            dest[off+13] = (float) src.m31();
            dest[off+14] = (float) src.m32();
            dest[off+15] = 1.0f;
        }

        public void copy4x4(Matrix4x3d src, double[] dest, int off) {
            dest[off+0]  = src.m00();
            dest[off+1]  = src.m01();
            dest[off+2]  = src.m02();
            dest[off+3]  = 0.0;
            dest[off+4]  = src.m10();
            dest[off+5]  = src.m11();
            dest[off+6]  = src.m12();
            dest[off+7]  = 0.0;
            dest[off+8]  = src.m20();
            dest[off+9]  = src.m21();
            dest[off+10] = src.m22();
            dest[off+11] = 0.0;
            dest[off+12] = src.m30();
            dest[off+13] = src.m31();
            dest[off+14] = src.m32();
            dest[off+15] = 1.0;
        }

        public void copy3x3(Matrix3x2f src, float[] dest, int off) {
            dest[off+0] = src.m00();
            dest[off+1] = src.m01();
            dest[off+2] = 0.0f;
            dest[off+3] = src.m10();
            dest[off+4] = src.m11();
            dest[off+5] = 0.0f;
            dest[off+6] = src.m20();
            dest[off+7] = src.m21();
            dest[off+8] = 1.0f;
        }

        public void copy3x3(Matrix3x2d src, double[] dest, int off) {
            dest[off+0] = src.m00();
            dest[off+1] = src.m01();
            dest[off+2] = 0.0;
            dest[off+3] = src.m10();
            dest[off+4] = src.m11();
            dest[off+5] = 0.0;
            dest[off+6] = src.m20();
            dest[off+7] = src.m21();
            dest[off+8] = 1.0;
        }

        public void copy4x4(Matrix3x2f src, float[] dest, int off) {
            dest[off+0]  = src.m00();
            dest[off+1]  = src.m01();
            dest[off+2]  = 0.0f;
            dest[off+3]  = 0.0f;
            dest[off+4]  = src.m10();
            dest[off+5]  = src.m11();
            dest[off+6]  = 0.0f;
            dest[off+7]  = 0.0f;
            dest[off+8]  = 0.0f;
            dest[off+9]  = 0.0f;
            dest[off+10] = 1.0f;
            dest[off+11] = 0.0f;
            dest[off+12] = src.m20();
            dest[off+13] = src.m21();
            dest[off+14] = 0.0f;
            dest[off+15] = 1.0f;
        }

        public void copy4x4(Matrix3x2d src, double[] dest, int off) {
            dest[off+0]  = src.m00();
            dest[off+1]  = src.m01();
            dest[off+2]  = 0.0;
            dest[off+3]  = 0.0;
            dest[off+4]  = src.m10();
            dest[off+5]  = src.m11();
            dest[off+6]  = 0.0;
            dest[off+7]  = 0.0;
            dest[off+8]  = 0.0;
            dest[off+9]  = 0.0;
            dest[off+10] = 1.0;
            dest[off+11] = 0.0;
            dest[off+12] = src.m20();
            dest[off+13] = src.m21();
            dest[off+14] = 0.0;
            dest[off+15] = 1.0;
        }

        public void identity(Matrix4f dest) {
            dest._m00(1.0f);
            dest._m01(0.0f);
            dest._m02(0.0f);
            dest._m03(0.0f);
            dest._m10(0.0f);
            dest._m11(1.0f);
            dest._m12(0.0f);
            dest._m13(0.0f);
            dest._m20(0.0f);
            dest._m21(0.0f);
            dest._m22(1.0f);
            dest._m23(0.0f);
            dest._m30(0.0f);
            dest._m31(0.0f);
            dest._m32(0.0f);
            dest._m33(1.0f);
        }

        public void identity(Matrix4x3f dest) {
            dest._m00(1.0f);
            dest._m01(0.0f);
            dest._m02(0.0f);
            dest._m10(0.0f);
            dest._m11(1.0f);
            dest._m12(0.0f);
            dest._m20(0.0f);
            dest._m21(0.0f);
            dest._m22(1.0f);
            dest._m30(0.0f);
            dest._m31(0.0f);
            dest._m32(0.0f);
        }

        public void identity(Matrix3f dest) {
            dest._m00(1.0f);
            dest._m01(0.0f);
            dest._m02(0.0f);
            dest._m10(0.0f);
            dest._m11(1.0f);
            dest._m12(0.0f);
            dest._m20(0.0f);
            dest._m21(0.0f);
            dest._m22(1.0f);
        }

        public void identity(Matrix3x2f dest) {
            dest._m00(1.0f);
            dest._m01(0.0f);
            dest._m10(0.0f);
            dest._m11(1.0f);
            dest._m20(0.0f);
            dest._m21(0.0f);
        }

        public void identity(Matrix3x2d dest) {
            dest._m00(1.0);
            dest._m01(0.0);
            dest._m10(0.0);
            dest._m11(1.0);
            dest._m20(0.0);
            dest._m21(0.0);
        }

        public void identity(Quaternionf dest) {
            dest.x = 0.0f;
            dest.y = 0.0f;
            dest.z = 0.0f;
            dest.w = 1.0f;
        }

        public void swap(Matrix4f m1, Matrix4f m2) {
            float tmp;
            tmp = m1.m00(); m1._m00(m2.m00()); m2._m00(tmp);
            tmp = m1.m01(); m1._m01(m2.m01()); m2._m01(tmp);
            tmp = m1.m02(); m1._m02(m2.m02()); m2._m02(tmp);
            tmp = m1.m03(); m1._m03(m2.m03()); m2._m03(tmp);
            tmp = m1.m10(); m1._m10(m2.m10()); m2._m10(tmp);
            tmp = m1.m11(); m1._m11(m2.m11()); m2._m11(tmp);
            tmp = m1.m12(); m1._m12(m2.m12()); m2._m12(tmp);
            tmp = m1.m13(); m1._m13(m2.m13()); m2._m13(tmp);
            tmp = m1.m20(); m1._m20(m2.m20()); m2._m20(tmp);
            tmp = m1.m21(); m1._m21(m2.m21()); m2._m21(tmp);
            tmp = m1.m22(); m1._m22(m2.m22()); m2._m22(tmp);
            tmp = m1.m23(); m1._m23(m2.m23()); m2._m23(tmp);
            tmp = m1.m30(); m1._m30(m2.m30()); m2._m30(tmp);
            tmp = m1.m31(); m1._m31(m2.m31()); m2._m31(tmp);
            tmp = m1.m32(); m1._m32(m2.m32()); m2._m32(tmp);
            tmp = m1.m33(); m1._m33(m2.m33()); m2._m33(tmp);
        }

        public void swap(Matrix4x3f m1, Matrix4x3f m2) {
            float tmp;
            tmp = m1.m00(); m1._m00(m2.m00()); m2._m00(tmp);
            tmp = m1.m01(); m1._m01(m2.m01()); m2._m01(tmp);
            tmp = m1.m02(); m1._m02(m2.m02()); m2._m02(tmp);
            tmp = m1.m10(); m1._m10(m2.m10()); m2._m10(tmp);
            tmp = m1.m11(); m1._m11(m2.m11()); m2._m11(tmp);
            tmp = m1.m12(); m1._m12(m2.m12()); m2._m12(tmp);
            tmp = m1.m20(); m1._m20(m2.m20()); m2._m20(tmp);
            tmp = m1.m21(); m1._m21(m2.m21()); m2._m21(tmp);
            tmp = m1.m22(); m1._m22(m2.m22()); m2._m22(tmp);
            tmp = m1.m30(); m1._m30(m2.m30()); m2._m30(tmp);
            tmp = m1.m31(); m1._m31(m2.m31()); m2._m31(tmp);
            tmp = m1.m32(); m1._m32(m2.m32()); m2._m32(tmp);
        }
        
        public void swap(Matrix3f m1, Matrix3f m2) {
            float tmp;
            tmp = m1.m00(); m1._m00(m2.m00()); m2._m00(tmp);
            tmp = m1.m01(); m1._m01(m2.m01()); m2._m01(tmp);
            tmp = m1.m02(); m1._m02(m2.m02()); m2._m02(tmp);
            tmp = m1.m10(); m1._m10(m2.m10()); m2._m10(tmp);
            tmp = m1.m11(); m1._m11(m2.m11()); m2._m11(tmp);
            tmp = m1.m12(); m1._m12(m2.m12()); m2._m12(tmp);
            tmp = m1.m20(); m1._m20(m2.m20()); m2._m20(tmp);
            tmp = m1.m21(); m1._m21(m2.m21()); m2._m21(tmp);
            tmp = m1.m22(); m1._m22(m2.m22()); m2._m22(tmp);
        }

        public void zero(Matrix4f dest) {
            dest._m00(0.0f);
            dest._m01(0.0f);
            dest._m02(0.0f);
            dest._m03(0.0f);
            dest._m10(0.0f);
            dest._m11(0.0f);
            dest._m12(0.0f);
            dest._m13(0.0f);
            dest._m20(0.0f);
            dest._m21(0.0f);
            dest._m22(0.0f);
            dest._m23(0.0f);
            dest._m30(0.0f);
            dest._m31(0.0f);
            dest._m32(0.0f);
            dest._m33(0.0f);
        }

        public void zero(Matrix4x3f dest) {
            dest._m00(0.0f);
            dest._m01(0.0f);
            dest._m02(0.0f);
            dest._m10(0.0f);
            dest._m11(0.0f);
            dest._m12(0.0f);
            dest._m20(0.0f);
            dest._m21(0.0f);
            dest._m22(0.0f);
            dest._m30(0.0f);
            dest._m31(0.0f);
            dest._m32(0.0f);
        }

        public void zero(Matrix3f dest) {
            dest._m00(0.0f);
            dest._m01(0.0f);
            dest._m02(0.0f);
            dest._m10(0.0f);
            dest._m11(0.0f);
            dest._m12(0.0f);
            dest._m20(0.0f);
            dest._m21(0.0f);
            dest._m22(0.0f);
        }

        public void zero(Matrix3x2f dest) {
            dest._m00(0.0f);
            dest._m01(0.0f);
            dest._m10(0.0f);
            dest._m11(0.0f);
            dest._m20(0.0f);
            dest._m21(0.0f);
        }

        public void zero(Matrix3x2d dest) {
            dest._m00(0.0);
            dest._m01(0.0);
            dest._m10(0.0);
            dest._m11(0.0);
            dest._m20(0.0);
            dest._m21(0.0);
        }

        public void zero(Vector4f dest) {
            dest.x = 0.0f;
            dest.y = 0.0f;
            dest.z = 0.0f;
            dest.w = 0.0f;
        }

        public void zero(Vector4i dest) {
            dest.x = 0;
            dest.y = 0;
            dest.z = 0;
            dest.w = 0;
        }

//#ifdef __HAS_NIO__
        public void putMatrix3f(Quaternionf q, int position, ByteBuffer dest) {
            float w2 = q.w * q.w;
            float x2 = q.x * q.x;
            float y2 = q.y * q.y;
            float z2 = q.z * q.z;
            float zw = q.z * q.w;
            float xy = q.x * q.y;
            float xz = q.x * q.z;
            float yw = q.y * q.w;
            float yz = q.y * q.z;
            float xw = q.x * q.w;
            dest.putFloat(position, w2 + x2 - z2 - y2);
            dest.putFloat(position + 4, xy + zw + zw + xy);
            dest.putFloat(position + 8, xz - yw + xz - yw);
            dest.putFloat(position + 12, -zw + xy - zw + xy);
            dest.putFloat(position + 16, y2 - z2 + w2 - x2);
            dest.putFloat(position + 20, yz + yz + xw + xw);
            dest.putFloat(position + 24, yw + xz + xz + yw);
            dest.putFloat(position + 28, yz + yz - xw - xw);
            dest.putFloat(position + 32, z2 - y2 - x2 + w2);
        }

        public void putMatrix3f(Quaternionf q, int position, FloatBuffer dest) {
            float w2 = q.w * q.w;
            float x2 = q.x * q.x;
            float y2 = q.y * q.y;
            float z2 = q.z * q.z;
            float zw = q.z * q.w;
            float xy = q.x * q.y;
            float xz = q.x * q.z;
            float yw = q.y * q.w;
            float yz = q.y * q.z;
            float xw = q.x * q.w;
            dest.put(position, w2 + x2 - z2 - y2);
            dest.put(position + 1, xy + zw + zw + xy);
            dest.put(position + 2, xz - yw + xz - yw);
            dest.put(position + 3, -zw + xy - zw + xy);
            dest.put(position + 4, y2 - z2 + w2 - x2);
            dest.put(position + 5, yz + yz + xw + xw);
            dest.put(position + 6, yw + xz + xz + yw);
            dest.put(position + 7, yz + yz - xw - xw);
            dest.put(position + 8, z2 - y2 - x2 + w2);
        }

        public void putMatrix4f(Quaternionf q, int position, ByteBuffer dest) {
            float w2 = q.w * q.w;
            float x2 = q.x * q.x;
            float y2 = q.y * q.y;
            float z2 = q.z * q.z;
            float zw = q.z * q.w;
            float xy = q.x * q.y;
            float xz = q.x * q.z;
            float yw = q.y * q.w;
            float yz = q.y * q.z;
            float xw = q.x * q.w;
            dest.putFloat(position, w2 + x2 - z2 - y2);
            dest.putFloat(position + 4, xy + zw + zw + xy);
            dest.putFloat(position + 8, xz - yw + xz - yw);
            dest.putFloat(position + 12, 0.0f);
            dest.putFloat(position + 16, -zw + xy - zw + xy);
            dest.putFloat(position + 20, y2 - z2 + w2 - x2);
            dest.putFloat(position + 24, yz + yz + xw + xw);
            dest.putFloat(position + 28, 0.0f);
            dest.putFloat(position + 32, yw + xz + xz + yw);
            dest.putFloat(position + 36, yz + yz - xw - xw);
            dest.putFloat(position + 40, z2 - y2 - x2 + w2);
            dest.putFloat(position + 44, 0.0f);
            dest.putLong(position + 48, 0L);
            dest.putLong(position + 56, 0x3F80000000000000L);
        }

        public void putMatrix4f(Quaternionf q, int position, FloatBuffer dest) {
            float w2 = q.w * q.w;
            float x2 = q.x * q.x;
            float y2 = q.y * q.y;
            float z2 = q.z * q.z;
            float zw = q.z * q.w;
            float xy = q.x * q.y;
            float xz = q.x * q.z;
            float yw = q.y * q.w;
            float yz = q.y * q.z;
            float xw = q.x * q.w;
            dest.put(position, w2 + x2 - z2 - y2);
            dest.put(position + 1, xy + zw + zw + xy);
            dest.put(position + 2, xz - yw + xz - yw);
            dest.put(position + 3, 0.0f);
            dest.put(position + 4, -zw + xy - zw + xy);
            dest.put(position + 5, y2 - z2 + w2 - x2);
            dest.put(position + 6, yz + yz + xw + xw);
            dest.put(position + 7, 0.0f);
            dest.put(position + 8, yw + xz + xz + yw);
            dest.put(position + 9, yz + yz - xw - xw);
            dest.put(position + 10, z2 - y2 - x2 + w2);
            dest.put(position + 11, 0.0f);
            dest.put(position + 12, 0.0f);
            dest.put(position + 13, 0.0f);
            dest.put(position + 14, 0.0f);
            dest.put(position + 15, 1.0f);
        }

        public void putMatrix4x3f(Quaternionf q, int position, ByteBuffer dest) {
            float w2 = q.w * q.w;
            float x2 = q.x * q.x;
            float y2 = q.y * q.y;
            float z2 = q.z * q.z;
            float zw = q.z * q.w;
            float xy = q.x * q.y;
            float xz = q.x * q.z;
            float yw = q.y * q.w;
            float yz = q.y * q.z;
            float xw = q.x * q.w;
            dest.putFloat(position, w2 + x2 - z2 - y2);
            dest.putFloat(position + 4, xy + zw + zw + xy);
            dest.putFloat(position + 8, xz - yw + xz - yw);
            dest.putFloat(position + 12, -zw + xy - zw + xy);
            dest.putFloat(position + 16, y2 - z2 + w2 - x2);
            dest.putFloat(position + 20, yz + yz + xw + xw);
            dest.putFloat(position + 24, yw + xz + xz + yw);
            dest.putFloat(position + 28, yz + yz - xw - xw);
            dest.putFloat(position + 32, z2 - y2 - x2 + w2);
            dest.putLong(position + 36, 0L);
            dest.putFloat(position + 44, 0.0f);
        }

        public void putMatrix4x3f(Quaternionf q, int position, FloatBuffer dest) {
            float w2 = q.w * q.w;
            float x2 = q.x * q.x;
            float y2 = q.y * q.y;
            float z2 = q.z * q.z;
            float zw = q.z * q.w;
            float xy = q.x * q.y;
            float xz = q.x * q.z;
            float yw = q.y * q.w;
            float yz = q.y * q.z;
            float xw = q.x * q.w;
            dest.put(position, w2 + x2 - z2 - y2);
            dest.put(position + 1, xy + zw + zw + xy);
            dest.put(position + 2, xz - yw + xz - yw);
            dest.put(position + 3, -zw + xy - zw + xy);
            dest.put(position + 4, y2 - z2 + w2 - x2);
            dest.put(position + 5, yz + yz + xw + xw);
            dest.put(position + 6, yw + xz + xz + yw);
            dest.put(position + 7, yz + yz - xw - xw);
            dest.put(position + 8, z2 - y2 - x2 + w2);
            dest.put(position + 9, 0.0f);
            dest.put(position + 10, 0.0f);
            dest.put(position + 11, 0.0f);
        }
//#endif

        public void set(Matrix4f m, Vector4f col0, Vector4f col1, Vector4f col2, Vector4f col3) {
            m._m00(col0.x);
            m._m01(col0.y);
            m._m02(col0.z);
            m._m03(col0.w);
            m._m10(col1.x);
            m._m11(col1.y);
            m._m12(col1.z);
            m._m13(col1.w);
            m._m20(col2.x);
            m._m21(col2.y);
            m._m22(col2.z);
            m._m23(col2.w);
            m._m30(col3.x);
            m._m31(col3.y);
            m._m32(col3.z);
            m._m33(col3.w);
        }

        public void set(Matrix4x3f m, Vector3f col0, Vector3f col1, Vector3f col2, Vector3f col3) {
            m._m00(col0.x);
            m._m01(col0.y);
            m._m02(col0.z);
            m._m10(col1.x);
            m._m11(col1.y);
            m._m12(col1.z);
            m._m20(col2.x);
            m._m21(col2.y);
            m._m22(col2.z);
            m._m30(col3.x);
            m._m31(col3.y);
            m._m32(col3.z);
        }

        public void set(Matrix3f m, Vector3f col0, Vector3f col1, Vector3f col2) {
            m._m00(col0.x);
            m._m01(col0.y);
            m._m02(col0.z);
            m._m10(col1.x);
            m._m11(col1.y);
            m._m12(col1.z);
            m._m20(col2.x);
            m._m21(col2.y);
            m._m22(col2.z);
        }

        public void putColumn0(Matrix4f src, Vector4f dest) {
            dest.x = src.m00();
            dest.y = src.m01();
            dest.z = src.m02();
            dest.w = src.m03();
        }

        public void putColumn1(Matrix4f src, Vector4f dest) {
            dest.x = src.m10();
            dest.y = src.m11();
            dest.z = src.m12();
            dest.w = src.m13();
        }

        public void putColumn2(Matrix4f src, Vector4f dest) {
            dest.x = src.m20();
            dest.y = src.m21();
            dest.z = src.m22();
            dest.w = src.m23();
        }

        public void putColumn3(Matrix4f src, Vector4f dest) {
            dest.x = src.m30();
            dest.y = src.m31();
            dest.z = src.m32();
            dest.w = src.m33();
        }

        public void putColumn0(Matrix4f src, Vector3f dest) {
            dest.x = src.m00();
            dest.y = src.m01();
            dest.z = src.m02();
        }

        public void putColumn1(Matrix4f src, Vector3f dest) {
            dest.x = src.m10();
            dest.y = src.m11();
            dest.z = src.m12();
        }

        public void putColumn2(Matrix4f src, Vector3f dest) {
            dest.x = src.m20();
            dest.y = src.m21();
            dest.z = src.m22();
        }

        public void putColumn3(Matrix4f src, Vector3f dest) {
            dest.x = src.m30();
            dest.y = src.m31();
            dest.z = src.m32();
        }

        public void getColumn0(Matrix4f dest, Vector4f src) {
            dest._m00(src.x);
            dest._m01(src.y);
            dest._m02(src.z);
            dest._m03(src.w);
        }

        public void getColumn1(Matrix4f dest, Vector4f src) {
            dest._m10(src.x);
            dest._m11(src.y);
            dest._m12(src.z);
            dest._m13(src.w);
        }

        public void getColumn2(Matrix4f dest, Vector4f src) {
            dest._m20(src.x);
            dest._m21(src.y);
            dest._m22(src.z);
            dest._m23(src.w);
        }

        public void getColumn3(Matrix4f dest, Vector4f src) {
            dest._m30(src.x);
            dest._m31(src.y);
            dest._m32(src.z);
            dest._m33(src.w);
        }

        public void broadcast(float c, Vector4f dest) {
            dest.x = c;
            dest.y = c;
            dest.z = c;
            dest.w = c;
        }

        public void broadcast(int c, Vector4i dest) {
            dest.x = c;
            dest.y = c;
            dest.z = c;
            dest.w = c;
        }
    }

//#ifndef __GWT__
    public static class MemUtilUnsafe extends MemUtilNIO {
        public static final sun.misc.Unsafe UNSAFE;

//#ifdef __HAS_NIO__
        public static final long ADDRESS;
//#endif
        public static final long Matrix3f_m00;
        public static final long Matrix4f_m00;
        public static final long Matrix4x3f_m00;
        public static final long Matrix3x2f_m00;
        public static final long Vector4f_x;
        public static final long Vector4d_x;
        public static final long Vector4i_x;
        public static final long Vector3f_x;
        public static final long Vector3d_x;
        public static final long Vector3i_x;
        public static final long Vector2f_x;
        public static final long Vector2d_x;
        public static final long Vector2i_x;
        public static final long Quaternionf_x;
        public static final long floatArrayOffset;

        static {
            UNSAFE = getUnsafeInstance();
            try {
//#ifdef __HAS_NIO__
                ADDRESS = findBufferAddress();
//#endif
                Matrix4f_m00 = checkMatrix4f();
                Matrix4x3f_m00 = checkMatrix4x3f();
                Matrix3f_m00 = checkMatrix3f();
                Matrix3x2f_m00 = checkMatrix3x2f();
                Vector4f_x = checkVector4f();
                Vector4d_x = checkVector4d();
                Vector4i_x = checkVector4i();
                Vector3f_x = checkVector3f();
                Vector3d_x = checkVector3d();
                Vector3i_x = checkVector3i();
                Vector2f_x = checkVector2f();
                Vector2d_x = checkVector2d();
                Vector2i_x = checkVector2i();
                Quaternionf_x = checkQuaternionf();
                floatArrayOffset = UNSAFE.arrayBaseOffset(float[].class);
                // Check if we can use object field offset/address put/get methods
                sun.misc.Unsafe.class.getDeclaredMethod("getLong", new Class[] {Object.class, long.class});
                sun.misc.Unsafe.class.getDeclaredMethod("putLong", new Class[] {Object.class, long.class, long.class});
            } catch (NoSuchFieldException e) {
                throw new UnsupportedOperationException(e);
            } catch (NoSuchMethodException e) {
                throw new UnsupportedOperationException(e);
            }
        }

//#ifdef __HAS_NIO__
        private static long findBufferAddress() {
            try {
                return UNSAFE.objectFieldOffset(getDeclaredField(Buffer.class, "address")); //$NON-NLS-1$
            } catch (Exception e) {
                throw new UnsupportedOperationException(e);
            }
        }
//#endif

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
                    throw new UnsupportedOperationException("Unexpected Matrix4f element offset");
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
                    throw new UnsupportedOperationException("Unexpected Matrix4x3f element offset");
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
                    throw new UnsupportedOperationException("Unexpected Matrix3f element offset");
            }
            return Matrix3f_m00;
        }

        private static long checkMatrix3x2f() throws NoSuchFieldException, SecurityException {
            Field f = Matrix3x2f.class.getDeclaredField("m00");
            long Matrix3x2f_m00 = UNSAFE.objectFieldOffset(f);
            // Validate expected field offsets
            for (int i = 1; i < 6; i++) {
                int c = i / 2;
                int r = i % 2;
                f = Matrix3x2f.class.getDeclaredField("m" + c + r);
                long offset = UNSAFE.objectFieldOffset(f);
                if (offset != Matrix3x2f_m00 + (i << 2))
                    throw new UnsupportedOperationException("Unexpected Matrix3x2f element offset");
            }
            return Matrix3x2f_m00;
        }

        private static long checkVector4f() throws NoSuchFieldException, SecurityException {
            Field f = Vector4f.class.getDeclaredField("x");
            long Vector4f_x = UNSAFE.objectFieldOffset(f);
            // Validate expected field offsets
            String[] names = {"y", "z", "w"};
            for (int i = 1; i < 4; i++) {
                f = Vector4f.class.getDeclaredField(names[i-1]);
                long offset = UNSAFE.objectFieldOffset(f);
                if (offset != Vector4f_x + (i << 2))
                    throw new UnsupportedOperationException("Unexpected Vector4f element offset");
            }
            return Vector4f_x;
        }

        private static long checkVector4d() throws NoSuchFieldException, SecurityException {
            Field f = Vector4d.class.getDeclaredField("x");
            long Vector4d_x = UNSAFE.objectFieldOffset(f);
            // Validate expected field offsets
            String[] names = {"y", "z", "w"};
            for (int i = 1; i < 4; i++) {
                f = Vector4d.class.getDeclaredField(names[i-1]);
                long offset = UNSAFE.objectFieldOffset(f);
                if (offset != Vector4d_x + (i << 3))
                    throw new UnsupportedOperationException("Unexpected Vector4d element offset");
            }
            return Vector4d_x;
        }

        private static long checkVector4i() throws NoSuchFieldException, SecurityException {
            Field f = Vector4i.class.getDeclaredField("x");
            long Vector4i_x = UNSAFE.objectFieldOffset(f);
            // Validate expected field offsets
            String[] names = {"y", "z", "w"};
            for (int i = 1; i < 4; i++) {
                f = Vector4i.class.getDeclaredField(names[i-1]);
                long offset = UNSAFE.objectFieldOffset(f);
                if (offset != Vector4i_x + (i << 2))
                    throw new UnsupportedOperationException("Unexpected Vector4i element offset");
            }
            return Vector4i_x;
        }

        private static long checkVector3f() throws NoSuchFieldException, SecurityException {
            Field f = Vector3f.class.getDeclaredField("x");
            long Vector3f_x = UNSAFE.objectFieldOffset(f);
            // Validate expected field offsets
            String[] names = {"y", "z"};
            for (int i = 1; i < 3; i++) {
                f = Vector3f.class.getDeclaredField(names[i-1]);
                long offset = UNSAFE.objectFieldOffset(f);
                if (offset != Vector3f_x + (i << 2))
                    throw new UnsupportedOperationException("Unexpected Vector3f element offset");
            }
            return Vector3f_x;
        }

        private static long checkVector3d() throws NoSuchFieldException, SecurityException {
            Field f = Vector3d.class.getDeclaredField("x");
            long Vector3d_x = UNSAFE.objectFieldOffset(f);
            // Validate expected field offsets
            String[] names = {"y", "z"};
            for (int i = 1; i < 3; i++) {
                f = Vector3d.class.getDeclaredField(names[i-1]);
                long offset = UNSAFE.objectFieldOffset(f);
                if (offset != Vector3d_x + (i << 3))
                    throw new UnsupportedOperationException("Unexpected Vector3d element offset");
            }
            return Vector3d_x;
        }

        private static long checkVector3i() throws NoSuchFieldException, SecurityException {
            Field f = Vector3i.class.getDeclaredField("x");
            long Vector3i_x = UNSAFE.objectFieldOffset(f);
            // Validate expected field offsets
            String[] names = {"y", "z"};
            for (int i = 1; i < 3; i++) {
                f = Vector3i.class.getDeclaredField(names[i-1]);
                long offset = UNSAFE.objectFieldOffset(f);
                if (offset != Vector3i_x + (i << 2))
                    throw new UnsupportedOperationException("Unexpected Vector3i element offset");
            }
            return Vector3i_x;
        }

        private static long checkVector2f() throws NoSuchFieldException, SecurityException {
            Field f = Vector2f.class.getDeclaredField("x");
            long Vector2f_x = UNSAFE.objectFieldOffset(f);
            // Validate expected field offsets
            f = Vector2f.class.getDeclaredField("y");
            long offset = UNSAFE.objectFieldOffset(f);
            if (offset != Vector2f_x + (1 << 2))
                throw new UnsupportedOperationException("Unexpected Vector2f element offset");
            return Vector2f_x;
        }

        private static long checkVector2d() throws NoSuchFieldException, SecurityException {
            Field f = Vector2d.class.getDeclaredField("x");
            long Vector2d_x = UNSAFE.objectFieldOffset(f);
            // Validate expected field offsets
            f = Vector2d.class.getDeclaredField("y");
            long offset = UNSAFE.objectFieldOffset(f);
            if (offset != Vector2d_x + (1 << 3))
                throw new UnsupportedOperationException("Unexpected Vector2d element offset");
            return Vector2d_x;
        }

        private static long checkVector2i() throws NoSuchFieldException, SecurityException {
            Field f = Vector2i.class.getDeclaredField("x");
            long Vector2i_x = UNSAFE.objectFieldOffset(f);
            // Validate expected field offsets
            f = Vector2i.class.getDeclaredField("y");
            long offset = UNSAFE.objectFieldOffset(f);
            if (offset != Vector2i_x + (1 << 2))
                throw new UnsupportedOperationException("Unexpected Vector2i element offset");
            return Vector2i_x;
        }

        private static long checkQuaternionf() throws NoSuchFieldException, SecurityException {
            Field f = Quaternionf.class.getDeclaredField("x");
            long Quaternionf_x = UNSAFE.objectFieldOffset(f);
            // Validate expected field offsets
            String[] names = {"y", "z", "w"};
            for (int i = 1; i < 4; i++) {
                f = Quaternionf.class.getDeclaredField(names[i-1]);
                long offset = UNSAFE.objectFieldOffset(f);
                if (offset != Quaternionf_x + (i << 2))
                    throw new UnsupportedOperationException("Unexpected Quaternionf element offset");
            }
            return Quaternionf_x;
        }

        private static java.lang.reflect.Field getDeclaredField(Class root, String fieldName) throws NoSuchFieldException {
            Class type = root;
            do {
                try {
                    java.lang.reflect.Field field = type.getDeclaredField(fieldName);
                    return field;
                } catch (NoSuchFieldException e) {
                    type = type.getSuperclass();
                } catch (SecurityException e) {
                    type = type.getSuperclass();
                }
            } while (type != null);
            throw new NoSuchFieldException(fieldName + " does not exist in " + root.getName() + " or any of its superclasses."); //$NON-NLS-1$ //$NON-NLS-2$
        }

        public static sun.misc.Unsafe getUnsafeInstance() throws SecurityException {
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

//#ifdef __HAS_NIO__
        public long addressOf(Buffer buffer) {
            return UNSAFE.getLong(buffer, ADDRESS);
        }
//#endif

        public static void putLong(Object obj, long off, long val) {
            UNSAFE.putLong(obj, off, val);
        }

        public void put(Matrix4f m, long destAddr) {
            for (int i = 0; i < 8; i++) {
                putLong(null, destAddr + (i << 3), UNSAFE.getLong(m, Matrix4f_m00 + (i << 3)));
            }
        }

        public void put4x3(Matrix4f m, long destAddr) {
            for (int i = 0; i < 4; i++) {
                putLong(null, destAddr + 12 * i, UNSAFE.getLong(m, Matrix4f_m00 + (i << 4)));
            }
            UNSAFE.putFloat(null, destAddr +  8, m.m02());
            UNSAFE.putFloat(null, destAddr + 20, m.m12());
            UNSAFE.putFloat(null, destAddr + 32, m.m22());
            UNSAFE.putFloat(null, destAddr + 44, m.m32());
        }

        public void put(Matrix4x3f m, long destAddr) {
            for (int i = 0; i < 6; i++) {
                putLong(null, destAddr + (i << 3), UNSAFE.getLong(m, Matrix4x3f_m00 + (i << 3)));
            }
        }

        public void put4x4(Matrix4x3f m, long destAddr) {
            for (int i = 0; i < 4; i++) {
                putLong(null, destAddr + (i << 4), UNSAFE.getLong(m, Matrix4x3f_m00 + 12 * i));
                long lng = UNSAFE.getInt(m, Matrix4x3f_m00 + 8 + 12 * i) & 0xFFFFFFFFL;
                putLong(null, destAddr + 8 + (i << 4), lng);
            }
            UNSAFE.putFloat(null, destAddr + 60, 1.0f);
        }

        public void put4x4(Matrix4x3d m, long destAddr) {
            UNSAFE.putDouble(null, destAddr,       m.m00());
            UNSAFE.putDouble(null, destAddr + 8,   m.m01());
            UNSAFE.putDouble(null, destAddr + 16,  m.m02());
            UNSAFE.putDouble(null, destAddr + 24,  0.0);
            UNSAFE.putDouble(null, destAddr + 32,  m.m10());
            UNSAFE.putDouble(null, destAddr + 40,  m.m11());
            UNSAFE.putDouble(null, destAddr + 48,  m.m12());
            UNSAFE.putDouble(null, destAddr + 56,  0.0);
            UNSAFE.putDouble(null, destAddr + 64,  m.m20());
            UNSAFE.putDouble(null, destAddr + 72,  m.m21());
            UNSAFE.putDouble(null, destAddr + 80,  m.m22());
            UNSAFE.putDouble(null, destAddr + 88,  0.0);
            UNSAFE.putDouble(null, destAddr + 96,  m.m30());
            UNSAFE.putDouble(null, destAddr + 104, m.m31());
            UNSAFE.putDouble(null, destAddr + 112, m.m32());
            UNSAFE.putDouble(null, destAddr + 120, 1.0);
        }

        public void put4x4(Matrix3x2f m, long destAddr) {
            UNSAFE.putLong(null, destAddr,    UNSAFE.getLong(m, Matrix3x2f_m00));
            UNSAFE.putLong(null, destAddr+8,  0L);
            UNSAFE.putLong(null, destAddr+16, UNSAFE.getLong(m, Matrix3x2f_m00+8));
            UNSAFE.putLong(null, destAddr+24, 0L);
            UNSAFE.putLong(null, destAddr+32, 0L);
            UNSAFE.putLong(null, destAddr+40, 0x3F800000L);
            UNSAFE.putLong(null, destAddr+48, UNSAFE.getLong(m, Matrix3x2f_m00+16));
            UNSAFE.putLong(null, destAddr+56, 0x3F80000000000000L);
        }

        public void put4x4(Matrix3x2d m, long destAddr) {
            UNSAFE.putDouble(null, destAddr,       m.m00());
            UNSAFE.putDouble(null, destAddr + 8,   m.m01());
            UNSAFE.putDouble(null, destAddr + 16,  0.0);
            UNSAFE.putDouble(null, destAddr + 24,  0.0);
            UNSAFE.putDouble(null, destAddr + 32,  m.m10());
            UNSAFE.putDouble(null, destAddr + 40,  m.m11());
            UNSAFE.putDouble(null, destAddr + 48,  0.0);
            UNSAFE.putDouble(null, destAddr + 56,  0.0);
            UNSAFE.putDouble(null, destAddr + 64,  0.0);
            UNSAFE.putDouble(null, destAddr + 72,  0.0);
            UNSAFE.putDouble(null, destAddr + 80,  1.0);
            UNSAFE.putDouble(null, destAddr + 88,  0.0);
            UNSAFE.putDouble(null, destAddr + 96,  m.m20());
            UNSAFE.putDouble(null, destAddr + 104, m.m21());
            UNSAFE.putDouble(null, destAddr + 112, 0.0);
            UNSAFE.putDouble(null, destAddr + 120, 1.0);
        }

        public void put3x3(Matrix3x2f m, long destAddr) {
            UNSAFE.putLong( null, destAddr,    UNSAFE.getLong(m, Matrix3x2f_m00));
            UNSAFE.putInt(  null, destAddr+8,  0);
            UNSAFE.putLong( null, destAddr+12, UNSAFE.getLong(m, Matrix3x2f_m00+8));
            UNSAFE.putInt(  null, destAddr+20, 0);
            UNSAFE.putLong( null, destAddr+24, UNSAFE.getLong(m, Matrix3x2f_m00+16));
            UNSAFE.putFloat(null, destAddr+32, 0.0f);
        }

        public void put3x3(Matrix3x2d m, long destAddr) {
            UNSAFE.putDouble(null, destAddr,      m.m00());
            UNSAFE.putDouble(null, destAddr + 8,  m.m01());
            UNSAFE.putDouble(null, destAddr + 16, 0.0);
            UNSAFE.putDouble(null, destAddr + 24, m.m10());
            UNSAFE.putDouble(null, destAddr + 32, m.m11());
            UNSAFE.putDouble(null, destAddr + 40, 0.0);
            UNSAFE.putDouble(null, destAddr + 48, m.m20());
            UNSAFE.putDouble(null, destAddr + 56, m.m21());
            UNSAFE.putDouble(null, destAddr + 64, 1.0);
        }

        public void putTransposed(Matrix4f m, long destAddr) {
            UNSAFE.putFloat(null, destAddr,      m.m00());
            UNSAFE.putFloat(null, destAddr + 4,  m.m10());
            UNSAFE.putFloat(null, destAddr + 8,  m.m20());
            UNSAFE.putFloat(null, destAddr + 12, m.m30());
            UNSAFE.putFloat(null, destAddr + 16, m.m01());
            UNSAFE.putFloat(null, destAddr + 20, m.m11());
            UNSAFE.putFloat(null, destAddr + 24, m.m21());
            UNSAFE.putFloat(null, destAddr + 28, m.m31());
            UNSAFE.putFloat(null, destAddr + 32, m.m02());
            UNSAFE.putFloat(null, destAddr + 36, m.m12());
            UNSAFE.putFloat(null, destAddr + 40, m.m22());
            UNSAFE.putFloat(null, destAddr + 44, m.m32());
            UNSAFE.putFloat(null, destAddr + 48, m.m03());
            UNSAFE.putFloat(null, destAddr + 52, m.m13());
            UNSAFE.putFloat(null, destAddr + 56, m.m23());
            UNSAFE.putFloat(null, destAddr + 60, m.m33());
        }

        public void put4x3Transposed(Matrix4f m, long destAddr) {
            UNSAFE.putFloat(null, destAddr,      m.m00());
            UNSAFE.putFloat(null, destAddr + 4,  m.m10());
            UNSAFE.putFloat(null, destAddr + 8,  m.m20());
            UNSAFE.putFloat(null, destAddr + 12, m.m30());
            UNSAFE.putFloat(null, destAddr + 16, m.m01());
            UNSAFE.putFloat(null, destAddr + 20, m.m11());
            UNSAFE.putFloat(null, destAddr + 24, m.m21());
            UNSAFE.putFloat(null, destAddr + 28, m.m31());
            UNSAFE.putFloat(null, destAddr + 32, m.m02());
            UNSAFE.putFloat(null, destAddr + 36, m.m12());
            UNSAFE.putFloat(null, destAddr + 40, m.m22());
            UNSAFE.putFloat(null, destAddr + 44, m.m32());
        }

        public void putTransposed(Matrix4x3f m, long destAddr) {
            UNSAFE.putFloat(null, destAddr,      m.m00());
            UNSAFE.putFloat(null, destAddr + 4,  m.m10());
            UNSAFE.putFloat(null, destAddr + 8,  m.m20());
            UNSAFE.putFloat(null, destAddr + 12, m.m30());
            UNSAFE.putFloat(null, destAddr + 16, m.m01());
            UNSAFE.putFloat(null, destAddr + 20, m.m11());
            UNSAFE.putFloat(null, destAddr + 24, m.m21());
            UNSAFE.putFloat(null, destAddr + 28, m.m31());
            UNSAFE.putFloat(null, destAddr + 32, m.m02());
            UNSAFE.putFloat(null, destAddr + 36, m.m12());
            UNSAFE.putFloat(null, destAddr + 40, m.m22());
            UNSAFE.putFloat(null, destAddr + 44, m.m32());
        }

        public void putTransposed(Matrix3f m, long destAddr) {
            UNSAFE.putFloat(null, destAddr,      m.m00());
            UNSAFE.putFloat(null, destAddr + 4,  m.m10());
            UNSAFE.putFloat(null, destAddr + 8,  m.m20());
            UNSAFE.putFloat(null, destAddr + 12, m.m01());
            UNSAFE.putFloat(null, destAddr + 16, m.m11());
            UNSAFE.putFloat(null, destAddr + 20, m.m21());
            UNSAFE.putFloat(null, destAddr + 24, m.m02());
            UNSAFE.putFloat(null, destAddr + 28, m.m12());
            UNSAFE.putFloat(null, destAddr + 32, m.m22());
        }

        public void put(Matrix4d m, long destAddr) {
            UNSAFE.putDouble(null, destAddr,       m.m00());
            UNSAFE.putDouble(null, destAddr + 8,   m.m01());
            UNSAFE.putDouble(null, destAddr + 16,  m.m02());
            UNSAFE.putDouble(null, destAddr + 24,  m.m03());
            UNSAFE.putDouble(null, destAddr + 32,  m.m10());
            UNSAFE.putDouble(null, destAddr + 40,  m.m11());
            UNSAFE.putDouble(null, destAddr + 48,  m.m12());
            UNSAFE.putDouble(null, destAddr + 56,  m.m13());
            UNSAFE.putDouble(null, destAddr + 64,  m.m20());
            UNSAFE.putDouble(null, destAddr + 72,  m.m21());
            UNSAFE.putDouble(null, destAddr + 80,  m.m22());
            UNSAFE.putDouble(null, destAddr + 88,  m.m23());
            UNSAFE.putDouble(null, destAddr + 96,  m.m30());
            UNSAFE.putDouble(null, destAddr + 104, m.m31());
            UNSAFE.putDouble(null, destAddr + 112, m.m32());
            UNSAFE.putDouble(null, destAddr + 120, m.m33());
        }

        public void put(Matrix4x3d m, long destAddr) {
            UNSAFE.putDouble(null, destAddr,      m.m00());
            UNSAFE.putDouble(null, destAddr + 8,  m.m01());
            UNSAFE.putDouble(null, destAddr + 16, m.m02());
            UNSAFE.putDouble(null, destAddr + 24, m.m10());
            UNSAFE.putDouble(null, destAddr + 32, m.m11());
            UNSAFE.putDouble(null, destAddr + 40, m.m12());
            UNSAFE.putDouble(null, destAddr + 48, m.m20());
            UNSAFE.putDouble(null, destAddr + 56, m.m21());
            UNSAFE.putDouble(null, destAddr + 64, m.m22());
            UNSAFE.putDouble(null, destAddr + 72, m.m30());
            UNSAFE.putDouble(null, destAddr + 80, m.m31());
            UNSAFE.putDouble(null, destAddr + 88, m.m32());
        }

        public void putTransposed(Matrix4d m, long destAddr) {
            UNSAFE.putDouble(null, destAddr,       m.m00());
            UNSAFE.putDouble(null, destAddr + 8,   m.m10());
            UNSAFE.putDouble(null, destAddr + 16,  m.m20());
            UNSAFE.putDouble(null, destAddr + 24,  m.m30());
            UNSAFE.putDouble(null, destAddr + 32,  m.m01());
            UNSAFE.putDouble(null, destAddr + 40,  m.m11());
            UNSAFE.putDouble(null, destAddr + 48,  m.m21());
            UNSAFE.putDouble(null, destAddr + 56,  m.m31());
            UNSAFE.putDouble(null, destAddr + 64,  m.m02());
            UNSAFE.putDouble(null, destAddr + 72,  m.m12());
            UNSAFE.putDouble(null, destAddr + 80,  m.m22());
            UNSAFE.putDouble(null, destAddr + 88,  m.m32());
            UNSAFE.putDouble(null, destAddr + 96,  m.m03());
            UNSAFE.putDouble(null, destAddr + 104, m.m13());
            UNSAFE.putDouble(null, destAddr + 112, m.m23());
            UNSAFE.putDouble(null, destAddr + 120, m.m33());
        }

        public void putfTransposed(Matrix4d m, long destAddr) {
            UNSAFE.putFloat(null, destAddr,      (float)m.m00());
            UNSAFE.putFloat(null, destAddr + 4,  (float)m.m10());
            UNSAFE.putFloat(null, destAddr + 8,  (float)m.m20());
            UNSAFE.putFloat(null, destAddr + 12, (float)m.m30());
            UNSAFE.putFloat(null, destAddr + 16, (float)m.m01());
            UNSAFE.putFloat(null, destAddr + 20, (float)m.m11());
            UNSAFE.putFloat(null, destAddr + 24, (float)m.m21());
            UNSAFE.putFloat(null, destAddr + 28, (float)m.m31());
            UNSAFE.putFloat(null, destAddr + 32, (float)m.m02());
            UNSAFE.putFloat(null, destAddr + 36, (float)m.m12());
            UNSAFE.putFloat(null, destAddr + 40, (float)m.m22());
            UNSAFE.putFloat(null, destAddr + 44, (float)m.m32());
            UNSAFE.putFloat(null, destAddr + 48, (float)m.m03());
            UNSAFE.putFloat(null, destAddr + 52, (float)m.m13());
            UNSAFE.putFloat(null, destAddr + 56, (float)m.m23());
            UNSAFE.putFloat(null, destAddr + 60, (float)m.m33());
        }

        public void put4x3Transposed(Matrix4d m, long destAddr) {
            UNSAFE.putDouble(null, destAddr,      m.m00());
            UNSAFE.putDouble(null, destAddr + 8,  m.m10());
            UNSAFE.putDouble(null, destAddr + 16, m.m20());
            UNSAFE.putDouble(null, destAddr + 24, m.m30());
            UNSAFE.putDouble(null, destAddr + 32, m.m01());
            UNSAFE.putDouble(null, destAddr + 40, m.m11());
            UNSAFE.putDouble(null, destAddr + 48, m.m21());
            UNSAFE.putDouble(null, destAddr + 56, m.m31());
            UNSAFE.putDouble(null, destAddr + 64, m.m02());
            UNSAFE.putDouble(null, destAddr + 72, m.m12());
            UNSAFE.putDouble(null, destAddr + 80, m.m22());
            UNSAFE.putDouble(null, destAddr + 88, m.m32());
        }

        public void putTransposed(Matrix4x3d m, long destAddr) {
            UNSAFE.putDouble(null, destAddr,      m.m00());
            UNSAFE.putDouble(null, destAddr + 8,  m.m10());
            UNSAFE.putDouble(null, destAddr + 16, m.m20());
            UNSAFE.putDouble(null, destAddr + 24, m.m30());
            UNSAFE.putDouble(null, destAddr + 32, m.m01());
            UNSAFE.putDouble(null, destAddr + 40, m.m11());
            UNSAFE.putDouble(null, destAddr + 48, m.m21());
            UNSAFE.putDouble(null, destAddr + 56, m.m31());
            UNSAFE.putDouble(null, destAddr + 64, m.m02());
            UNSAFE.putDouble(null, destAddr + 72, m.m12());
            UNSAFE.putDouble(null, destAddr + 80, m.m22());
            UNSAFE.putDouble(null, destAddr + 88, m.m32());
        }

        public void putfTransposed(Matrix4x3d m, long destAddr) {
            UNSAFE.putFloat(null, destAddr,      (float)m.m00());
            UNSAFE.putFloat(null, destAddr + 4,  (float)m.m10());
            UNSAFE.putFloat(null, destAddr + 8,  (float)m.m20());
            UNSAFE.putFloat(null, destAddr + 12, (float)m.m30());
            UNSAFE.putFloat(null, destAddr + 16, (float)m.m01());
            UNSAFE.putFloat(null, destAddr + 20, (float)m.m11());
            UNSAFE.putFloat(null, destAddr + 24, (float)m.m21());
            UNSAFE.putFloat(null, destAddr + 28, (float)m.m31());
            UNSAFE.putFloat(null, destAddr + 32, (float)m.m02());
            UNSAFE.putFloat(null, destAddr + 36, (float)m.m12());
            UNSAFE.putFloat(null, destAddr + 40, (float)m.m22());
            UNSAFE.putFloat(null, destAddr + 44, (float)m.m32());
        }

        public void putf(Matrix4d m, long destAddr) {
            UNSAFE.putFloat(null, destAddr,      (float)m.m00());
            UNSAFE.putFloat(null, destAddr + 4,  (float)m.m01());
            UNSAFE.putFloat(null, destAddr + 8,  (float)m.m02());
            UNSAFE.putFloat(null, destAddr + 12, (float)m.m03());
            UNSAFE.putFloat(null, destAddr + 16, (float)m.m10());
            UNSAFE.putFloat(null, destAddr + 20, (float)m.m11());
            UNSAFE.putFloat(null, destAddr + 24, (float)m.m12());
            UNSAFE.putFloat(null, destAddr + 28, (float)m.m13());
            UNSAFE.putFloat(null, destAddr + 32, (float)m.m20());
            UNSAFE.putFloat(null, destAddr + 36, (float)m.m21());
            UNSAFE.putFloat(null, destAddr + 40, (float)m.m22());
            UNSAFE.putFloat(null, destAddr + 44, (float)m.m23());
            UNSAFE.putFloat(null, destAddr + 48, (float)m.m30());
            UNSAFE.putFloat(null, destAddr + 52, (float)m.m31());
            UNSAFE.putFloat(null, destAddr + 56, (float)m.m32());
            UNSAFE.putFloat(null, destAddr + 60, (float)m.m33());
        }

        public void putf(Matrix4x3d m, long destAddr) {
            UNSAFE.putFloat(null, destAddr,      (float)m.m00());
            UNSAFE.putFloat(null, destAddr + 4,  (float)m.m01());
            UNSAFE.putFloat(null, destAddr + 8,  (float)m.m02());
            UNSAFE.putFloat(null, destAddr + 12, (float)m.m10());
            UNSAFE.putFloat(null, destAddr + 16, (float)m.m11());
            UNSAFE.putFloat(null, destAddr + 20, (float)m.m12());
            UNSAFE.putFloat(null, destAddr + 24, (float)m.m20());
            UNSAFE.putFloat(null, destAddr + 28, (float)m.m21());
            UNSAFE.putFloat(null, destAddr + 32, (float)m.m22());
            UNSAFE.putFloat(null, destAddr + 36, (float)m.m30());
            UNSAFE.putFloat(null, destAddr + 40, (float)m.m31());
            UNSAFE.putFloat(null, destAddr + 44, (float)m.m32());
        }

        public void put(Matrix3f m, long destAddr) {
            for (int i = 0; i < 4; i++) {
                putLong(null, destAddr + (i << 3), UNSAFE.getLong(m, Matrix3f_m00 + (i << 3)));
            }
            UNSAFE.putFloat(null, destAddr + 32, m.m22());
        }

        public void put(Matrix3d m, long destAddr) {
            UNSAFE.putDouble(null, destAddr,      m.m00());
            UNSAFE.putDouble(null, destAddr + 8,  m.m01());
            UNSAFE.putDouble(null, destAddr + 16, m.m02());
            UNSAFE.putDouble(null, destAddr + 24, m.m10());
            UNSAFE.putDouble(null, destAddr + 32, m.m11());
            UNSAFE.putDouble(null, destAddr + 40, m.m12());
            UNSAFE.putDouble(null, destAddr + 48, m.m20());
            UNSAFE.putDouble(null, destAddr + 56, m.m21());
            UNSAFE.putDouble(null, destAddr + 64, m.m22());
        }

        public void put(Matrix3x2f m, long destAddr) {
            for (int i = 0; i < 3; i++) {
                putLong(null, destAddr + (i << 3), UNSAFE.getLong(m, Matrix3x2f_m00 + (i << 3)));
            }
        }

        public void put(Matrix3x2d m, long destAddr) {
            UNSAFE.putDouble(null, destAddr,      m.m00());
            UNSAFE.putDouble(null, destAddr + 8,  m.m01());
            UNSAFE.putDouble(null, destAddr + 16, m.m10());
            UNSAFE.putDouble(null, destAddr + 24, m.m11());
            UNSAFE.putDouble(null, destAddr + 32, m.m20());
            UNSAFE.putDouble(null, destAddr + 40, m.m21());
        }

        public void putf(Matrix3d m, long destAddr) {
            UNSAFE.putFloat(null, destAddr,      (float)m.m00());
            UNSAFE.putFloat(null, destAddr + 4,  (float)m.m01());
            UNSAFE.putFloat(null, destAddr + 8,  (float)m.m02());
            UNSAFE.putFloat(null, destAddr + 12, (float)m.m10());
            UNSAFE.putFloat(null, destAddr + 16, (float)m.m11());
            UNSAFE.putFloat(null, destAddr + 20, (float)m.m12());
            UNSAFE.putFloat(null, destAddr + 24, (float)m.m20());
            UNSAFE.putFloat(null, destAddr + 28, (float)m.m21());
            UNSAFE.putFloat(null, destAddr + 32, (float)m.m22());
        }

        public void put(Vector4d src, long destAddr) {
            UNSAFE.putDouble(null, destAddr,    src.x);
            UNSAFE.putDouble(null, destAddr+8,  src.y);
            UNSAFE.putDouble(null, destAddr+16, src.z);
            UNSAFE.putDouble(null, destAddr+24, src.w);
        }

        public void put(Vector4f src, long destAddr) {
            UNSAFE.putFloat(null, destAddr,    src.x);
            UNSAFE.putFloat(null, destAddr+4,  src.y);
            UNSAFE.putFloat(null, destAddr+8,  src.z);
            UNSAFE.putFloat(null, destAddr+12, src.w);
        }

        public void put(Vector4i src, long destAddr) {
            UNSAFE.putInt(null, destAddr,    src.x);
            UNSAFE.putInt(null, destAddr+4,  src.y);
            UNSAFE.putInt(null, destAddr+8,  src.z);
            UNSAFE.putInt(null, destAddr+12, src.w);
        }

        public void put(Vector3f src, long destAddr) {
            UNSAFE.putFloat(null, destAddr,   src.x);
            UNSAFE.putFloat(null, destAddr+4, src.y);
            UNSAFE.putFloat(null, destAddr+8, src.z);
        }

        public void put(Vector3d src, long destAddr) {
            UNSAFE.putDouble(null, destAddr,    src.x);
            UNSAFE.putDouble(null, destAddr+8,  src.y);
            UNSAFE.putDouble(null, destAddr+16, src.z);
        }

        public void put(Vector3i src, long destAddr) {
            UNSAFE.putInt(null, destAddr,   src.x);
            UNSAFE.putInt(null, destAddr+4, src.y);
            UNSAFE.putInt(null, destAddr+8, src.z);
        }

        public void put(Vector2f src, long destAddr) {
            UNSAFE.putFloat(null, destAddr,   src.x);
            UNSAFE.putFloat(null, destAddr+4, src.y);
        }

        public void put(Vector2d src, long destAddr) {
            UNSAFE.putDouble(null, destAddr,   UNSAFE.getDouble(src, Vector2d_x));
            UNSAFE.putDouble(null, destAddr+8, UNSAFE.getDouble(src, Vector2d_x+8));
        }

        public void put(Vector2i src, long destAddr) {
            UNSAFE.putInt(null, destAddr,   src.x);
            UNSAFE.putInt(null, destAddr+4, src.y);
        }

        public void get(Matrix4f m, long srcAddr) {
            for (int i = 0; i < 8; i++) {
                putLong(m, Matrix4f_m00 + (i << 3), UNSAFE.getLong(srcAddr + (i << 3)));
            }
        }

        public void get(Matrix4x3f m, long srcAddr) {
            for (int i = 0; i < 6; i++) {
                putLong(m, Matrix4x3f_m00 + (i << 3), UNSAFE.getLong(srcAddr + (i << 3)));
            }
        }

        public void get(Matrix4d m, long srcAddr) {
            m._m00(UNSAFE.getDouble(null, srcAddr));
            m._m01(UNSAFE.getDouble(null, srcAddr+8));
            m._m02(UNSAFE.getDouble(null, srcAddr+16));
            m._m03(UNSAFE.getDouble(null, srcAddr+24));
            m._m10(UNSAFE.getDouble(null, srcAddr+32));
            m._m11(UNSAFE.getDouble(null, srcAddr+40));
            m._m12(UNSAFE.getDouble(null, srcAddr+48));
            m._m13(UNSAFE.getDouble(null, srcAddr+56));
            m._m20(UNSAFE.getDouble(null, srcAddr+64));
            m._m21(UNSAFE.getDouble(null, srcAddr+72));
            m._m22(UNSAFE.getDouble(null, srcAddr+80));
            m._m23(UNSAFE.getDouble(null, srcAddr+88));
            m._m30(UNSAFE.getDouble(null, srcAddr+96));
            m._m31(UNSAFE.getDouble(null, srcAddr+104));
            m._m32(UNSAFE.getDouble(null, srcAddr+112));
            m._m33(UNSAFE.getDouble(null, srcAddr+120));
        }

        public void get(Matrix4x3d m, long srcAddr) {
            m._m00(UNSAFE.getDouble(null, srcAddr));
            m._m01(UNSAFE.getDouble(null, srcAddr+8));
            m._m02(UNSAFE.getDouble(null, srcAddr+16));
            m._m10(UNSAFE.getDouble(null, srcAddr+24));
            m._m11(UNSAFE.getDouble(null, srcAddr+32));
            m._m12(UNSAFE.getDouble(null, srcAddr+40));
            m._m20(UNSAFE.getDouble(null, srcAddr+48));
            m._m21(UNSAFE.getDouble(null, srcAddr+56));
            m._m22(UNSAFE.getDouble(null, srcAddr+64));
            m._m30(UNSAFE.getDouble(null, srcAddr+72));
            m._m31(UNSAFE.getDouble(null, srcAddr+80));
            m._m32(UNSAFE.getDouble(null, srcAddr+88));
        }

        public void getf(Matrix4d m, long srcAddr) {
            m._m00(UNSAFE.getFloat(null, srcAddr));
            m._m01(UNSAFE.getFloat(null, srcAddr+4));
            m._m02(UNSAFE.getFloat(null, srcAddr+8));
            m._m03(UNSAFE.getFloat(null, srcAddr+12));
            m._m10(UNSAFE.getFloat(null, srcAddr+16));
            m._m11(UNSAFE.getFloat(null, srcAddr+20));
            m._m12(UNSAFE.getFloat(null, srcAddr+24));
            m._m13(UNSAFE.getFloat(null, srcAddr+28));
            m._m20(UNSAFE.getFloat(null, srcAddr+32));
            m._m21(UNSAFE.getFloat(null, srcAddr+36));
            m._m22(UNSAFE.getFloat(null, srcAddr+40));
            m._m23(UNSAFE.getFloat(null, srcAddr+44));
            m._m30(UNSAFE.getFloat(null, srcAddr+48));
            m._m31(UNSAFE.getFloat(null, srcAddr+52));
            m._m32(UNSAFE.getFloat(null, srcAddr+56));
            m._m33(UNSAFE.getFloat(null, srcAddr+60));
        }

        public void getf(Matrix4x3d m, long srcAddr) {
            m._m00(UNSAFE.getFloat(null, srcAddr));
            m._m01(UNSAFE.getFloat(null, srcAddr+4));
            m._m02(UNSAFE.getFloat(null, srcAddr+8));
            m._m10(UNSAFE.getFloat(null, srcAddr+12));
            m._m11(UNSAFE.getFloat(null, srcAddr+16));
            m._m12(UNSAFE.getFloat(null, srcAddr+20));
            m._m20(UNSAFE.getFloat(null, srcAddr+24));
            m._m21(UNSAFE.getFloat(null, srcAddr+28));
            m._m22(UNSAFE.getFloat(null, srcAddr+32));
            m._m30(UNSAFE.getFloat(null, srcAddr+36));
            m._m31(UNSAFE.getFloat(null, srcAddr+40));
            m._m32(UNSAFE.getFloat(null, srcAddr+44));
        }

        public void get(Matrix3f m, long srcAddr) {
            for (int i = 0; i < 4; i++) {
                putLong(m, Matrix3f_m00 + (i << 3), UNSAFE.getLong(null, srcAddr + (i << 3)));
            }
            m._m22(UNSAFE.getFloat(null, srcAddr+32));
        }

        public void get(Matrix3d m, long srcAddr) {
            m._m00(UNSAFE.getDouble(null, srcAddr));
            m._m01(UNSAFE.getDouble(null, srcAddr+8));
            m._m02(UNSAFE.getDouble(null, srcAddr+16));
            m._m10(UNSAFE.getDouble(null, srcAddr+24));
            m._m11(UNSAFE.getDouble(null, srcAddr+32));
            m._m12(UNSAFE.getDouble(null, srcAddr+40));
            m._m20(UNSAFE.getDouble(null, srcAddr+48));
            m._m21(UNSAFE.getDouble(null, srcAddr+56));
            m._m22(UNSAFE.getDouble(null, srcAddr+64));
        }

        public void get(Matrix3x2f m, long srcAddr) {
            for (int i = 0; i < 3; i++) {
                putLong(m, Matrix3x2f_m00 + (i << 3), UNSAFE.getLong(null, srcAddr + (i << 3)));
            }
        }

        public void get(Matrix3x2d m, long srcAddr) {
            m._m00(UNSAFE.getDouble(null, srcAddr));
            m._m01(UNSAFE.getDouble(null, srcAddr+8));
            m._m10(UNSAFE.getDouble(null, srcAddr+16));
            m._m11(UNSAFE.getDouble(null, srcAddr+24));
            m._m20(UNSAFE.getDouble(null, srcAddr+32));
            m._m21(UNSAFE.getDouble(null, srcAddr+40));
        }

        public void getf(Matrix3d m, long srcAddr) {
            m._m00(UNSAFE.getFloat(null, srcAddr));
            m._m01(UNSAFE.getFloat(null, srcAddr+4));
            m._m02(UNSAFE.getFloat(null, srcAddr+8));
            m._m10(UNSAFE.getFloat(null, srcAddr+12));
            m._m11(UNSAFE.getFloat(null, srcAddr+16));
            m._m12(UNSAFE.getFloat(null, srcAddr+20));
            m._m20(UNSAFE.getFloat(null, srcAddr+24));
            m._m21(UNSAFE.getFloat(null, srcAddr+28));
            m._m22(UNSAFE.getFloat(null, srcAddr+32));
        }

        public void get(Vector4d dst, long srcAddr) {
            dst.x = UNSAFE.getLong(null, srcAddr);
            dst.y = UNSAFE.getLong(null, srcAddr+8);
            dst.z = UNSAFE.getLong(null, srcAddr+16);
            dst.w = UNSAFE.getLong(null, srcAddr+24);
        }

        public void get(Vector4f dst, long srcAddr) {
            dst.x = UNSAFE.getFloat(null, srcAddr);
            dst.y = UNSAFE.getFloat(null, srcAddr+4);
            dst.z = UNSAFE.getFloat(null, srcAddr+8);
            dst.w = UNSAFE.getFloat(null, srcAddr+12);
        }

        public void get(Vector4i dst, long srcAddr) {
            dst.x = UNSAFE.getInt(null, srcAddr);
            dst.y = UNSAFE.getInt(null, srcAddr+4);
            dst.z = UNSAFE.getInt(null, srcAddr+8);
            dst.w = UNSAFE.getInt(null, srcAddr+12);
        }

        public void get(Vector3f dst, long srcAddr) {
            dst.x = UNSAFE.getFloat(null, srcAddr);
            dst.y = UNSAFE.getFloat(null, srcAddr+4);
            dst.z = UNSAFE.getFloat(null, srcAddr+8);
        }

        public void get(Vector3d dst, long srcAddr) {
            dst.x = UNSAFE.getDouble(null, srcAddr);
            dst.y = UNSAFE.getDouble(null, srcAddr+8);
            dst.z = UNSAFE.getDouble(null, srcAddr+16);
        }

        public void get(Vector3i dst, long srcAddr) {
            dst.x = UNSAFE.getInt(null, srcAddr);
            dst.y = UNSAFE.getInt(null, srcAddr+4);
            dst.z = UNSAFE.getInt(null, srcAddr+8);
        }

        public void get(Vector2f dst, long srcAddr) {
            dst.x = UNSAFE.getFloat(null, srcAddr);
            dst.y = UNSAFE.getFloat(null, srcAddr+4);
        }

        public void get(Vector2d dst, long srcAddr) {
            dst.x = UNSAFE.getDouble(null, srcAddr);
            dst.y = UNSAFE.getDouble(null, srcAddr+8);
        }

        public void get(Vector2i dst, long srcAddr) {
            dst.x = UNSAFE.getInt(null, srcAddr);
            dst.y = UNSAFE.getInt(null, srcAddr+4);
        }

        public void putMatrix3f(Quaternionf q, long addr) {
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

        public void putMatrix4f(Quaternionf q, long addr) {
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
            UNSAFE.putFloat(null, addr, 1.0f - q11 - q22);
            UNSAFE.putFloat(null, addr + 4, q01 + q23);
            putLong(null, addr + 8, Float.floatToRawIntBits(q02 - q13) & 0xFFFFFFFFL);
            UNSAFE.putFloat(null, addr + 16, q01 - q23);
            UNSAFE.putFloat(null, addr + 20, 1.0f - q22 - q00);
            putLong(null, addr + 24, Float.floatToRawIntBits(q12 + q03) & 0xFFFFFFFFL);
            UNSAFE.putFloat(null, addr + 32, q02 + q13);
            UNSAFE.putFloat(null, addr + 36, q12 - q03);
            putLong(null, addr + 40, Float.floatToRawIntBits(1.0f - q11 - q00) & 0xFFFFFFFFL);
            putLong(null, addr + 48, 0L);
            putLong(null, addr + 56, 0x3F80000000000000L);
        }

        public void putMatrix4x3f(Quaternionf q, long addr) {
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
            UNSAFE.putFloat(null, addr, 1.0f - q11 - q22);
            UNSAFE.putFloat(null, addr + 4, q01 + q23);
            UNSAFE.putFloat(null, addr + 8, q02 - q13);
            UNSAFE.putFloat(null, addr + 12, q01 - q23);
            UNSAFE.putFloat(null, addr + 16, 1.0f - q22 - q00);
            UNSAFE.putFloat(null, addr + 20, q12 + q03);
            UNSAFE.putFloat(null, addr + 24, q02 + q13);
            UNSAFE.putFloat(null, addr + 28, q12 - q03);
            UNSAFE.putFloat(null, addr + 32, 1.0f - q11 - q00);
            putLong(null, addr + 36, 0L);
            UNSAFE.putFloat(null, addr + 44, 0.0f);
        }

        private static void throwNoDirectBufferException() {
            throw new IllegalArgumentException("Must use a direct buffer");
        }

//#ifdef __HAS_NIO__
        public void putMatrix3f(Quaternionf q, int position, ByteBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            long addr = addressOf(dest) + position;
            putMatrix3f(q, addr);
        }

        public void putMatrix3f(Quaternionf q, int position, FloatBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            long addr = addressOf(dest) + (position << 2);
            putMatrix3f(q, addr);
        }

        public void putMatrix4f(Quaternionf q, int position, ByteBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            long addr = addressOf(dest) + position;
            putMatrix4f(q, addr);
        }

        public void putMatrix4f(Quaternionf q, int position, FloatBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            long addr = addressOf(dest) + (position << 2);
            putMatrix4f(q, addr);
        }

        public void putMatrix4x3f(Quaternionf q, int position, ByteBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            long addr = addressOf(dest) + position;
            putMatrix4x3f(q, addr);
        }

        public void putMatrix4x3f(Quaternionf q, int position, FloatBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            long addr = addressOf(dest) + (position << 2);
            putMatrix4x3f(q, addr);
        }

        public void put(Matrix4f m, int offset, FloatBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            put(m, addressOf(dest) + (offset << 2));
        }

        public void put(Matrix4f m, int offset, ByteBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            put(m, addressOf(dest) + offset);
        }

        public void put4x3(Matrix4f m, int offset, FloatBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            put4x3(m, addressOf(dest) + (offset << 2));
        }

        public void put4x3(Matrix4f m, int offset, ByteBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            put4x3(m, addressOf(dest) + offset);
        }

        public void put(Matrix4x3f m, int offset, FloatBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            put(m, addressOf(dest) + (offset << 2));
        }

        public void put(Matrix4x3f m, int offset, ByteBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            put(m, addressOf(dest) + offset);
        }

        public void put4x4(Matrix4x3f m, int offset, FloatBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            put4x4(m, addressOf(dest) + (offset << 2));
        }

        public void put4x4(Matrix4x3f m, int offset, ByteBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            put4x4(m, addressOf(dest) + offset);
        }

        public void put4x4(Matrix4x3d m, int offset, DoubleBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            put4x4(m, addressOf(dest) + (offset << 3));
        }

        public void put4x4(Matrix4x3d m, int offset, ByteBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            put4x4(m, addressOf(dest) + offset);
        }

        public void put4x4(Matrix3x2f m, int offset, FloatBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            put4x4(m, addressOf(dest) + (offset << 2));
        }

        public void put4x4(Matrix3x2f m, int offset, ByteBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            put4x4(m, addressOf(dest) + offset);
        }

        public void put4x4(Matrix3x2d m, int offset, DoubleBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            put4x4(m, addressOf(dest) + (offset << 2));
        }

        public void put4x4(Matrix3x2d m, int offset, ByteBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            put4x4(m, addressOf(dest) + offset);
        }

        public void put3x3(Matrix3x2f m, int offset, FloatBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            put3x3(m, addressOf(dest) + (offset << 2));
        }

        public void put3x3(Matrix3x2f m, int offset, ByteBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            put3x3(m, addressOf(dest) + offset);
        }

        public void put3x3(Matrix3x2d m, int offset, DoubleBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            put3x3(m, addressOf(dest) + (offset << 2));
        }

        public void put3x3(Matrix3x2d m, int offset, ByteBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            put3x3(m, addressOf(dest) + offset);
        }

        public void putTransposed(Matrix4f m, int offset, FloatBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            putTransposed(m, addressOf(dest) + (offset << 2));
        }

        public void putTransposed(Matrix4f m, int offset, ByteBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            putTransposed(m, addressOf(dest) + offset);
        }

        public void put4x3Transposed(Matrix4f m, int offset, FloatBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            put4x3Transposed(m, addressOf(dest) + (offset << 2));
        }

        public void put4x3Transposed(Matrix4f m, int offset, ByteBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            put4x3Transposed(m, addressOf(dest) + offset);
        }

        public void putTransposed(Matrix4x3f m, int offset, FloatBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            putTransposed(m, addressOf(dest) + (offset << 2));
        }

        public void putTransposed(Matrix4x3f m, int offset, ByteBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            putTransposed(m, addressOf(dest) + offset);
        }

        public void putTransposed(Matrix3f m, int offset, FloatBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            putTransposed(m, addressOf(dest) + (offset << 2));
        }

        public void putTransposed(Matrix3f m, int offset, ByteBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            putTransposed(m, addressOf(dest) + offset);
        }

        public void put(Matrix4d m, int offset, DoubleBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            put(m, addressOf(dest) + (offset << 3));
        }

        public void put(Matrix4d m, int offset, ByteBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            put(m, addressOf(dest) + offset);
        }

        public void put(Matrix4x3d m, int offset, DoubleBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            put(m, addressOf(dest) + (offset << 3));
        }

        public void put(Matrix4x3d m, int offset, ByteBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            put(m, addressOf(dest) + offset);
        }

        public void putf(Matrix4d m, int offset, FloatBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            putf(m, addressOf(dest) + (offset << 2));
        }

        public void putf(Matrix4d m, int offset, ByteBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            putf(m, addressOf(dest) + offset);
        }

        public void putf(Matrix4x3d m, int offset, FloatBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            putf(m, addressOf(dest) + (offset << 2));
        }

        public void putf(Matrix4x3d m, int offset, ByteBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            putf(m, addressOf(dest) + offset);
        }

        public void putTransposed(Matrix4d m, int offset, DoubleBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            putTransposed(m, addressOf(dest) + (offset << 3));
        }

        public void putTransposed(Matrix4d m, int offset, ByteBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            putTransposed(m, addressOf(dest) + offset);
        }

        public void put4x3Transposed(Matrix4d m, int offset, DoubleBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            put4x3Transposed(m, addressOf(dest) + (offset << 3));
        }

        public void put4x3Transposed(Matrix4d m, int offset, ByteBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            put4x3Transposed(m, addressOf(dest) + offset);
        }

        public void putTransposed(Matrix4x3d m, int offset, DoubleBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            putTransposed(m, addressOf(dest) + (offset << 3));
        }

        public void putTransposed(Matrix4x3d m, int offset, ByteBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            putTransposed(m, addressOf(dest) + offset);
        }

        public void putfTransposed(Matrix4d m, int offset, FloatBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            putfTransposed(m, addressOf(dest) + (offset << 2));
        }

        public void putfTransposed(Matrix4d m, int offset, ByteBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            putfTransposed(m, addressOf(dest) + offset);
        }

        public void putfTransposed(Matrix4x3d m, int offset, FloatBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            putfTransposed(m, addressOf(dest) + (offset << 2));
        }

        public void putfTransposed(Matrix4x3d m, int offset, ByteBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            putfTransposed(m, addressOf(dest) + offset);
        }

        public void put(Matrix3f m, int offset, FloatBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            put(m, addressOf(dest) + (offset << 2));
        }

        public void put(Matrix3f m, int offset, ByteBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            put(m, addressOf(dest) + offset);
        }

        public void put(Matrix3d m, int offset, DoubleBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            put(m, addressOf(dest) + (offset << 3));
        }

        public void put(Matrix3d m, int offset, ByteBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            put(m, addressOf(dest) + offset);
        }

        public void put(Matrix3x2f m, int offset, FloatBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            put(m, addressOf(dest) + (offset << 2));
        }

        public void put(Matrix3x2f m, int offset, ByteBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            put(m, addressOf(dest) + offset);
        }

        public void put(Matrix3x2d m, int offset, DoubleBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            put(m, addressOf(dest) + (offset << 2));
        }

        public void put(Matrix3x2d m, int offset, ByteBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            put(m, addressOf(dest) + offset);
        }

        public void putf(Matrix3d m, int offset, FloatBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            putf(m, addressOf(dest) + (offset << 2));
        }

        public void putf(Matrix3d m, int offset, ByteBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            putf(m, addressOf(dest) + offset);
        }

        public void put(Vector4d src, int offset, DoubleBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            put(src, addressOf(dest) + (offset << 3));
        }

        public void put(Vector4d src, int offset, ByteBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            put(src, addressOf(dest) + offset);
        }

        public void put(Vector4f src, int offset, FloatBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            put(src, addressOf(dest) + (offset << 2));
        }

        public void put(Vector4f src, int offset, ByteBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            put(src, addressOf(dest) + offset);
        }

        public void put(Vector4i src, int offset, IntBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            put(src, addressOf(dest) + (offset << 2));
        }

        public void put(Vector4i src, int offset, ByteBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            put(src, addressOf(dest) + offset);
        }

        public void put(Vector3f src, int offset, FloatBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            put(src, addressOf(dest) + (offset << 2));
        }

        public void put(Vector3f src, int offset, ByteBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            put(src, addressOf(dest) + offset);
        }

        public void put(Vector3d src, int offset, DoubleBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            put(src, addressOf(dest) + (offset << 3));
        }

        public void put(Vector3d src, int offset, ByteBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            put(src, addressOf(dest) + offset);
        }

        public void put(Vector3i src, int offset, IntBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            put(src, addressOf(dest) + (offset << 2));
        }

        public void put(Vector3i src, int offset, ByteBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            put(src, addressOf(dest) + offset);
        }

        public void put(Vector2f src, int offset, FloatBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            put(src, addressOf(dest) + (offset << 2));
        }

        public void put(Vector2f src, int offset, ByteBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            put(src, addressOf(dest) + offset);
        }

        public void put(Vector2d src, int offset, DoubleBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            put(src, addressOf(dest) + (offset << 3));
        }

        public void put(Vector2d src, int offset, ByteBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            put(src, addressOf(dest) + offset);
        }

        public void put(Vector2i src, int offset, IntBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            put(src, addressOf(dest) + (offset << 2));
        }

        public void put(Vector2i src, int offset, ByteBuffer dest) {
            if (Options.DEBUG && !dest.isDirect()) {
                throwNoDirectBufferException();
            }
            put(src, addressOf(dest) + offset);
        }

        public void get(Matrix4f m, int offset, FloatBuffer src) {
            if (Options.DEBUG && !src.isDirect()) {
                throwNoDirectBufferException();
            }
            get(m, addressOf(src) + (offset << 2));
        }

        public void get(Matrix4f m, int offset, ByteBuffer src) {
            if (Options.DEBUG && !src.isDirect()) {
                throwNoDirectBufferException();
            }
            get(m, addressOf(src) + offset);
        }

        public void get(Matrix4x3f m, int offset, FloatBuffer src) {
            if (Options.DEBUG && !src.isDirect()) {
                throwNoDirectBufferException();
            }
            get(m, addressOf(src) + (offset << 2));
        }

        public void get(Matrix4x3f m, int offset, ByteBuffer src) {
            if (Options.DEBUG && !src.isDirect()) {
                throwNoDirectBufferException();
            }
            get(m, addressOf(src) + offset);
        }

        public void get(Matrix4d m, int offset, DoubleBuffer src) {
            if (Options.DEBUG && !src.isDirect()) {
                throwNoDirectBufferException();
            }
            get(m, addressOf(src) + (offset << 3));
        }

        public void get(Matrix4d m, int offset, ByteBuffer src) {
            if (Options.DEBUG && !src.isDirect()) {
                throwNoDirectBufferException();
            }
            get(m, addressOf(src) + offset);
        }

        public void get(Matrix4x3d m, int offset, DoubleBuffer src) {
            if (Options.DEBUG && !src.isDirect()) {
                throwNoDirectBufferException();
            }
            get(m, addressOf(src) + (offset << 3));
        }

        public void get(Matrix4x3d m, int offset, ByteBuffer src) {
            if (Options.DEBUG && !src.isDirect()) {
                throwNoDirectBufferException();
            }
            get(m, addressOf(src) + offset);
        }

        public void getf(Matrix4d m, int offset, FloatBuffer src) {
            if (Options.DEBUG && !src.isDirect()) {
                throwNoDirectBufferException();
            }
            getf(m, addressOf(src) + (offset << 2));
        }

        public void getf(Matrix4d m, int offset, ByteBuffer src) {
            if (Options.DEBUG && !src.isDirect()) {
                throwNoDirectBufferException();
            }
            getf(m, addressOf(src) + offset);
        }

        public void getf(Matrix4x3d m, int offset, FloatBuffer src) {
            if (Options.DEBUG && !src.isDirect()) {
                throwNoDirectBufferException();
            }
            getf(m, addressOf(src) + (offset << 2));
        }

        public void getf(Matrix4x3d m, int offset, ByteBuffer src) {
            if (Options.DEBUG && !src.isDirect()) {
                throwNoDirectBufferException();
            }
            getf(m, addressOf(src) + offset);
        }

        public void get(Matrix3f m, int offset, FloatBuffer src) {
            if (Options.DEBUG && !src.isDirect()) {
                throwNoDirectBufferException();
            }
            get(m, addressOf(src) + (offset << 2));
        }

        public void get(Matrix3f m, int offset, ByteBuffer src) {
            if (Options.DEBUG && !src.isDirect()) {
                throwNoDirectBufferException();
            }
            get(m, addressOf(src) + offset);
        }

        public void get(Matrix3d m, int offset, DoubleBuffer src) {
            if (Options.DEBUG && !src.isDirect()) {
                throwNoDirectBufferException();
            }
            get(m, addressOf(src) + (offset << 3));
        }

        public void get(Matrix3d m, int offset, ByteBuffer src) {
            if (Options.DEBUG && !src.isDirect()) {
                throwNoDirectBufferException();
            }
            get(m, addressOf(src) + offset);
        }

        public void get(Matrix3x2f m, int offset, FloatBuffer src) {
            if (Options.DEBUG && !src.isDirect()) {
                throwNoDirectBufferException();
            }
            get(m, addressOf(src) + (offset << 2));
        }

        public void get(Matrix3x2f m, int offset, ByteBuffer src) {
            if (Options.DEBUG && !src.isDirect()) {
                throwNoDirectBufferException();
            }
            get(m, addressOf(src) + offset);
        }

        public void get(Matrix3x2d m, int offset, DoubleBuffer src) {
            if (Options.DEBUG && !src.isDirect()) {
                throwNoDirectBufferException();
            }
            get(m, addressOf(src) + (offset << 2));
        }

        public void get(Matrix3x2d m, int offset, ByteBuffer src) {
            if (Options.DEBUG && !src.isDirect()) {
                throwNoDirectBufferException();
            }
            get(m, addressOf(src) + offset);
        }

        public void getf(Matrix3d m, int offset, FloatBuffer src) {
            if (Options.DEBUG && !src.isDirect()) {
                throwNoDirectBufferException();
            }
            getf(m, addressOf(src) + (offset << 2));
        }

        public void getf(Matrix3d m, int offset, ByteBuffer src) {
            if (Options.DEBUG && !src.isDirect()) {
                throwNoDirectBufferException();
            }
            getf(m, addressOf(src) + offset);
        }

        public void get(Vector4d dst, int offset, DoubleBuffer src) {
            if (Options.DEBUG && !src.isDirect()) {
                throwNoDirectBufferException();
            }
            get(dst, addressOf(src) + (offset << 3));
        }

        public void get(Vector4d dst, int offset, ByteBuffer src) {
            if (Options.DEBUG && !src.isDirect()) {
                throwNoDirectBufferException();
            }
            get(dst, addressOf(src) + offset);
        }

        public void get(Vector4f dst, int offset, FloatBuffer src) {
            if (Options.DEBUG && !src.isDirect()) {
                throwNoDirectBufferException();
            }
            get(dst, addressOf(src) + (offset << 2));
        }

        public void get(Vector4f dst, int offset, ByteBuffer src) {
            if (Options.DEBUG && !src.isDirect()) {
                throwNoDirectBufferException();
            }
            get(dst, addressOf(src) + offset);
        }

        public void get(Vector4i dst, int offset, IntBuffer src) {
            if (Options.DEBUG && !src.isDirect()) {
                throwNoDirectBufferException();
            }
            get(dst, addressOf(src) + (offset << 2));
        }

        public void get(Vector4i dst, int offset, ByteBuffer src) {
            if (Options.DEBUG && !src.isDirect()) {
                throwNoDirectBufferException();
            }
            get(dst, addressOf(src) + offset);
        }

        public void get(Vector3f dst, int offset, FloatBuffer src) {
            if (Options.DEBUG && !src.isDirect()) {
                throwNoDirectBufferException();
            }
            get(dst, addressOf(src) + (offset << 2));
        }

        public void get(Vector3f dst, int offset, ByteBuffer src) {
            if (Options.DEBUG && !src.isDirect()) {
                throwNoDirectBufferException();
            }
            get(dst, addressOf(src) + offset);
        }

        public void get(Vector3d dst, int offset, DoubleBuffer src) {
            if (Options.DEBUG && !src.isDirect()) {
                throwNoDirectBufferException();
            }
            get(dst, addressOf(src) + (offset << 3));
        }

        public void get(Vector3d dst, int offset, ByteBuffer src) {
            if (Options.DEBUG && !src.isDirect()) {
                throwNoDirectBufferException();
            }
            get(dst, addressOf(src) + offset);
        }

        public void get(Vector3i dst, int offset, IntBuffer src) {
            if (Options.DEBUG && !src.isDirect()) {
                throwNoDirectBufferException();
            }
            get(dst, addressOf(src) + (offset << 2));
        }

        public void get(Vector3i dst, int offset, ByteBuffer src) {
            if (Options.DEBUG && !src.isDirect()) {
                throwNoDirectBufferException();
            }
            get(dst, addressOf(src) + offset);
        }

        public void get(Vector2f dst, int offset, FloatBuffer src) {
            if (Options.DEBUG && !src.isDirect()) {
                throwNoDirectBufferException();
            }
            get(dst, addressOf(src) + (offset << 2));
        }

        public void get(Vector2f dst, int offset, ByteBuffer src) {
            if (Options.DEBUG && !src.isDirect()) {
                throwNoDirectBufferException();
            }
            get(dst, addressOf(src) + offset);
        }

        public void get(Vector2d dst, int offset, DoubleBuffer src) {
            if (Options.DEBUG && !src.isDirect()) {
                throwNoDirectBufferException();
            }
            get(dst, addressOf(src) + (offset << 3));
        }

        public void get(Vector2d dst, int offset, ByteBuffer src) {
            if (Options.DEBUG && !src.isDirect()) {
                throwNoDirectBufferException();
            }
            get(dst, addressOf(src) + offset);
        }

        public void get(Vector2i dst, int offset, IntBuffer src) {
            if (Options.DEBUG && !src.isDirect()) {
                throwNoDirectBufferException();
            }
            get(dst, addressOf(src) + (offset << 2));
        }

        public void get(Vector2i dst, int offset, ByteBuffer src) {
            if (Options.DEBUG && !src.isDirect()) {
                throwNoDirectBufferException();
            }
            get(dst, addressOf(src) + offset);
        }
//#endif
    }
//#endif
}
