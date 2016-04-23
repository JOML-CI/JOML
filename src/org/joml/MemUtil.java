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
    abstract void get(Matrix4f m, int offset, FloatBuffer src);
    abstract void get(Matrix4f m, int offset, ByteBuffer src);
    abstract void putTransposed(Matrix4f m, int offset, FloatBuffer dest);
    abstract void putTransposed(Matrix4f m, int offset, ByteBuffer dest);

    static final class MemUtilNIO extends MemUtil {
        final void put(Matrix4f m, int offset, FloatBuffer dest) {
            dest.put(offset,    m.ms[Matrix4f.M00]);
            dest.put(offset+1,  m.ms[Matrix4f.M01]);
            dest.put(offset+2,  m.ms[Matrix4f.M02]);
            dest.put(offset+3,  m.ms[Matrix4f.M03]);
            dest.put(offset+4,  m.ms[Matrix4f.M10]);
            dest.put(offset+5,  m.ms[Matrix4f.M11]);
            dest.put(offset+6,  m.ms[Matrix4f.M12]);
            dest.put(offset+7,  m.ms[Matrix4f.M13]);
            dest.put(offset+8,  m.ms[Matrix4f.M20]);
            dest.put(offset+9,  m.ms[Matrix4f.M21]);
            dest.put(offset+10, m.ms[Matrix4f.M22]);
            dest.put(offset+11, m.ms[Matrix4f.M23]);
            dest.put(offset+12, m.ms[Matrix4f.M30]);
            dest.put(offset+13, m.ms[Matrix4f.M31]);
            dest.put(offset+14, m.ms[Matrix4f.M32]);
            dest.put(offset+15, m.ms[Matrix4f.M33]);
        }

        final void put(Matrix4f m, int offset, ByteBuffer dest) {
            dest.putFloat(offset,    m.ms[Matrix4f.M00]);
            dest.putFloat(offset+4,  m.ms[Matrix4f.M01]);
            dest.putFloat(offset+8,  m.ms[Matrix4f.M02]);
            dest.putFloat(offset+12, m.ms[Matrix4f.M03]);
            dest.putFloat(offset+16, m.ms[Matrix4f.M10]);
            dest.putFloat(offset+20, m.ms[Matrix4f.M11]);
            dest.putFloat(offset+24, m.ms[Matrix4f.M12]);
            dest.putFloat(offset+28, m.ms[Matrix4f.M13]);
            dest.putFloat(offset+32, m.ms[Matrix4f.M20]);
            dest.putFloat(offset+36, m.ms[Matrix4f.M21]);
            dest.putFloat(offset+40, m.ms[Matrix4f.M22]);
            dest.putFloat(offset+44, m.ms[Matrix4f.M23]);
            dest.putFloat(offset+48, m.ms[Matrix4f.M30]);
            dest.putFloat(offset+52, m.ms[Matrix4f.M31]);
            dest.putFloat(offset+56, m.ms[Matrix4f.M32]);
            dest.putFloat(offset+60, m.ms[Matrix4f.M33]);
        }

        final void get(Matrix4f m, int offset, FloatBuffer src) {
            m.ms[Matrix4f.M00] = src.get(offset);
            m.ms[Matrix4f.M01] = src.get(offset+1);
            m.ms[Matrix4f.M02] = src.get(offset+2);
            m.ms[Matrix4f.M03] = src.get(offset+3);
            m.ms[Matrix4f.M10] = src.get(offset+4);
            m.ms[Matrix4f.M11] = src.get(offset+5);
            m.ms[Matrix4f.M12] = src.get(offset+6);
            m.ms[Matrix4f.M13] = src.get(offset+7);
            m.ms[Matrix4f.M20] = src.get(offset+8);
            m.ms[Matrix4f.M21] = src.get(offset+9);
            m.ms[Matrix4f.M22] = src.get(offset+10);
            m.ms[Matrix4f.M23] = src.get(offset+11);
            m.ms[Matrix4f.M30] = src.get(offset+12);
            m.ms[Matrix4f.M31] = src.get(offset+13);
            m.ms[Matrix4f.M32] = src.get(offset+14);
            m.ms[Matrix4f.M33] = src.get(offset+15);
        }

        final void get(Matrix4f m, int offset, ByteBuffer src) {
            m.ms[Matrix4f.M00] = src.getFloat(offset);
            m.ms[Matrix4f.M01] = src.getFloat(offset+4);
            m.ms[Matrix4f.M02] = src.getFloat(offset+8);
            m.ms[Matrix4f.M03] = src.getFloat(offset+12);
            m.ms[Matrix4f.M10] = src.getFloat(offset+16);
            m.ms[Matrix4f.M11] = src.getFloat(offset+20);
            m.ms[Matrix4f.M12] = src.getFloat(offset+24);
            m.ms[Matrix4f.M13] = src.getFloat(offset+28);
            m.ms[Matrix4f.M20] = src.getFloat(offset+32);
            m.ms[Matrix4f.M21] = src.getFloat(offset+36);
            m.ms[Matrix4f.M22] = src.getFloat(offset+40);
            m.ms[Matrix4f.M23] = src.getFloat(offset+44);
            m.ms[Matrix4f.M30] = src.getFloat(offset+48);
            m.ms[Matrix4f.M31] = src.getFloat(offset+52);
            m.ms[Matrix4f.M32] = src.getFloat(offset+56);
            m.ms[Matrix4f.M33] = src.getFloat(offset+60);
        }

        final void putTransposed(Matrix4f m, int offset, FloatBuffer dest) {
            dest.put(offset,    m.ms[Matrix4f.M00]);
            dest.put(offset+1,  m.ms[Matrix4f.M10]);
            dest.put(offset+2,  m.ms[Matrix4f.M20]);
            dest.put(offset+3,  m.ms[Matrix4f.M30]);
            dest.put(offset+4,  m.ms[Matrix4f.M01]);
            dest.put(offset+5,  m.ms[Matrix4f.M11]);
            dest.put(offset+6,  m.ms[Matrix4f.M21]);
            dest.put(offset+7,  m.ms[Matrix4f.M31]);
            dest.put(offset+8,  m.ms[Matrix4f.M02]);
            dest.put(offset+9,  m.ms[Matrix4f.M12]);
            dest.put(offset+10, m.ms[Matrix4f.M22]);
            dest.put(offset+11, m.ms[Matrix4f.M32]);
            dest.put(offset+12, m.ms[Matrix4f.M03]);
            dest.put(offset+13, m.ms[Matrix4f.M13]);
            dest.put(offset+14, m.ms[Matrix4f.M23]);
            dest.put(offset+15, m.ms[Matrix4f.M33]);
        }

        final void putTransposed(Matrix4f m, int offset, ByteBuffer dest) {
            dest.putFloat(offset,    m.ms[Matrix4f.M00]);
            dest.putFloat(offset+4,  m.ms[Matrix4f.M10]);
            dest.putFloat(offset+8,  m.ms[Matrix4f.M20]);
            dest.putFloat(offset+12, m.ms[Matrix4f.M30]);
            dest.putFloat(offset+16, m.ms[Matrix4f.M01]);
            dest.putFloat(offset+20, m.ms[Matrix4f.M11]);
            dest.putFloat(offset+24, m.ms[Matrix4f.M21]);
            dest.putFloat(offset+28, m.ms[Matrix4f.M31]);
            dest.putFloat(offset+32, m.ms[Matrix4f.M02]);
            dest.putFloat(offset+36, m.ms[Matrix4f.M12]);
            dest.putFloat(offset+40, m.ms[Matrix4f.M22]);
            dest.putFloat(offset+44, m.ms[Matrix4f.M32]);
            dest.putFloat(offset+48, m.ms[Matrix4f.M03]);
            dest.putFloat(offset+52, m.ms[Matrix4f.M13]);
            dest.putFloat(offset+56, m.ms[Matrix4f.M23]);
            dest.putFloat(offset+60, m.ms[Matrix4f.M33]);
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

        private final float memGetFloat(long ptr) {
            return UNSAFE.getFloat(ptr);
        }

        private final void put(Matrix4f m, long destAddr) {
            memPutFloat(destAddr,      m.ms[Matrix4f.M00]);
            memPutFloat(destAddr + 4,  m.ms[Matrix4f.M01]);
            memPutFloat(destAddr + 8,  m.ms[Matrix4f.M02]);
            memPutFloat(destAddr + 12, m.ms[Matrix4f.M03]);
            memPutFloat(destAddr + 16, m.ms[Matrix4f.M10]);
            memPutFloat(destAddr + 20, m.ms[Matrix4f.M11]);
            memPutFloat(destAddr + 24, m.ms[Matrix4f.M12]);
            memPutFloat(destAddr + 28, m.ms[Matrix4f.M13]);
            memPutFloat(destAddr + 32, m.ms[Matrix4f.M20]);
            memPutFloat(destAddr + 36, m.ms[Matrix4f.M21]);
            memPutFloat(destAddr + 40, m.ms[Matrix4f.M22]);
            memPutFloat(destAddr + 44, m.ms[Matrix4f.M23]);
            memPutFloat(destAddr + 48, m.ms[Matrix4f.M30]);
            memPutFloat(destAddr + 52, m.ms[Matrix4f.M31]);
            memPutFloat(destAddr + 56, m.ms[Matrix4f.M32]);
            memPutFloat(destAddr + 60, m.ms[Matrix4f.M33]);
        }

        private final void get(Matrix4f m, long srcAddr) {
            m.ms[Matrix4f.M00] = memGetFloat(srcAddr);
            m.ms[Matrix4f.M01] = memGetFloat(srcAddr+4);
            m.ms[Matrix4f.M02] = memGetFloat(srcAddr+8);
            m.ms[Matrix4f.M03] = memGetFloat(srcAddr+12);
            m.ms[Matrix4f.M10] = memGetFloat(srcAddr+16);
            m.ms[Matrix4f.M11] = memGetFloat(srcAddr+20);
            m.ms[Matrix4f.M12] = memGetFloat(srcAddr+24);
            m.ms[Matrix4f.M13] = memGetFloat(srcAddr+28);
            m.ms[Matrix4f.M20] = memGetFloat(srcAddr+32);
            m.ms[Matrix4f.M21] = memGetFloat(srcAddr+36);
            m.ms[Matrix4f.M22] = memGetFloat(srcAddr+40);
            m.ms[Matrix4f.M23] = memGetFloat(srcAddr+44);
            m.ms[Matrix4f.M30] = memGetFloat(srcAddr+48);
            m.ms[Matrix4f.M31] = memGetFloat(srcAddr+52);
            m.ms[Matrix4f.M32] = memGetFloat(srcAddr+56);
            m.ms[Matrix4f.M33] = memGetFloat(srcAddr+60);
        }

        private final void putTransposed(Matrix4f m, long destAddr) {
            memPutFloat(destAddr,      m.ms[Matrix4f.M00]);
            memPutFloat(destAddr + 4,  m.ms[Matrix4f.M10]);
            memPutFloat(destAddr + 8,  m.ms[Matrix4f.M20]);
            memPutFloat(destAddr + 12, m.ms[Matrix4f.M30]);
            memPutFloat(destAddr + 16, m.ms[Matrix4f.M01]);
            memPutFloat(destAddr + 20, m.ms[Matrix4f.M11]);
            memPutFloat(destAddr + 24, m.ms[Matrix4f.M21]);
            memPutFloat(destAddr + 28, m.ms[Matrix4f.M31]);
            memPutFloat(destAddr + 32, m.ms[Matrix4f.M02]);
            memPutFloat(destAddr + 36, m.ms[Matrix4f.M12]);
            memPutFloat(destAddr + 40, m.ms[Matrix4f.M22]);
            memPutFloat(destAddr + 44, m.ms[Matrix4f.M32]);
            memPutFloat(destAddr + 48, m.ms[Matrix4f.M03]);
            memPutFloat(destAddr + 52, m.ms[Matrix4f.M13]);
            memPutFloat(destAddr + 56, m.ms[Matrix4f.M23]);
            memPutFloat(destAddr + 60, m.ms[Matrix4f.M33]);
        }

        final void put(Matrix4f m, int offset, FloatBuffer dest) {
            put(m, addressOf(dest) + offset * 4);
        }

        final void put(Matrix4f m, int offset, ByteBuffer dest) {
            put(m, addressOf(dest) + offset);
        }

        final void get(Matrix4f m, int offset, FloatBuffer src) {
            get(m, addressOf(src) + offset * 4);
        }

        final void get(Matrix4f m, int offset, ByteBuffer src) {
            get(m, addressOf(src) + offset);
        }

        final void putTransposed(Matrix4f m, int offset, FloatBuffer dest) {
            putTransposed(m, addressOf(dest) + offset * 4);
        }

        final void putTransposed(Matrix4f m, int offset, ByteBuffer dest) {
            putTransposed(m, addressOf(dest) + offset);
        }
    }
}
