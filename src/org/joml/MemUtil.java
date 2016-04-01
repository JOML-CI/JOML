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
        } catch (Throwable t0) {
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
    }

    static final class MemUtilUnsafe extends MemUtil {
        private final sun.misc.Unsafe UNSAFE;
        private final long ADDRESS;
        {
            UNSAFE = getUnsafeInstance();
            try {
                ADDRESS = UNSAFE.objectFieldOffset(getDeclaredField(Buffer.class, "address")); //$NON-NLS-1$
            } catch (NoSuchFieldException e) {
                throw new AssertionError();
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
                }
            } while (type != null);
            throw new NoSuchFieldException(fieldName + " does not exist in " + root.getName() + " or any of its superclasses."); //$NON-NLS-1$ //$NON-NLS-2$
        }

        private static final sun.misc.Unsafe getUnsafeInstance() {
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

        private final void put(Matrix4f m, long destAddr) {
            memPutFloat(destAddr,      m.m00);
            memPutFloat(destAddr + 4,  m.m01);
            memPutFloat(destAddr + 8,  m.m02);
            memPutFloat(destAddr + 12, m.m03);
            memPutFloat(destAddr + 16, m.m10);
            memPutFloat(destAddr + 20, m.m11);
            memPutFloat(destAddr + 24, m.m12);
            memPutFloat(destAddr + 28, m.m13);
            memPutFloat(destAddr + 32, m.m20);
            memPutFloat(destAddr + 36, m.m21);
            memPutFloat(destAddr + 40, m.m22);
            memPutFloat(destAddr + 44, m.m23);
            memPutFloat(destAddr + 48, m.m30);
            memPutFloat(destAddr + 52, m.m31);
            memPutFloat(destAddr + 56, m.m32);
            memPutFloat(destAddr + 60, m.m33);
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

        private final void put(Matrix4d m, long destAddr) {
            memPutDouble(destAddr,      m.m00);
            memPutDouble(destAddr + 4,  m.m01);
            memPutDouble(destAddr + 8,  m.m02);
            memPutDouble(destAddr + 12, m.m03);
            memPutDouble(destAddr + 16, m.m10);
            memPutDouble(destAddr + 20, m.m11);
            memPutDouble(destAddr + 24, m.m12);
            memPutDouble(destAddr + 28, m.m13);
            memPutDouble(destAddr + 32, m.m20);
            memPutDouble(destAddr + 36, m.m21);
            memPutDouble(destAddr + 40, m.m22);
            memPutDouble(destAddr + 44, m.m23);
            memPutDouble(destAddr + 48, m.m30);
            memPutDouble(destAddr + 52, m.m31);
            memPutDouble(destAddr + 56, m.m32);
            memPutDouble(destAddr + 60, m.m33);
        }

        private final void putTransposed(Matrix4d m, long destAddr) {
            memPutDouble(destAddr,      m.m00);
            memPutDouble(destAddr + 4,  m.m10);
            memPutDouble(destAddr + 8,  m.m20);
            memPutDouble(destAddr + 12, m.m30);
            memPutDouble(destAddr + 16, m.m01);
            memPutDouble(destAddr + 20, m.m11);
            memPutDouble(destAddr + 24, m.m21);
            memPutDouble(destAddr + 28, m.m31);
            memPutDouble(destAddr + 32, m.m02);
            memPutDouble(destAddr + 36, m.m12);
            memPutDouble(destAddr + 40, m.m22);
            memPutDouble(destAddr + 44, m.m32);
            memPutDouble(destAddr + 48, m.m03);
            memPutDouble(destAddr + 52, m.m13);
            memPutDouble(destAddr + 56, m.m23);
            memPutDouble(destAddr + 60, m.m33);
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
    }
}
