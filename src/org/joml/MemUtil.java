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
		} catch (Throwable t0) {
			accessor = new MemUtilNIO();
		}
		return accessor;
	}

	abstract void put(Matrix4f m, int offset, FloatBuffer dest);
	abstract void put(Matrix4f m, int offset, ByteBuffer dest);
	abstract void putTransposed(Matrix4f m, int offset, FloatBuffer dest);
	abstract void putTransposed(Matrix4f m, int offset, ByteBuffer dest);

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
	}

	static final class MemUtilUnsafe extends MemUtil {
	    private final sun.misc.Unsafe UNSAFE;
	    private final long ADDRESS;
	    {
	        UNSAFE = getUnsafeInstance();
	        try {
	            ADDRESS = UNSAFE.objectFieldOffset(getDeclaredField(Buffer.class, "address"));
	        } catch (NoSuchFieldException e) {
	            throw new AssertionError();
	        }
	    }

	    private final java.lang.reflect.Field getDeclaredField(Class root, String fieldName) throws NoSuchFieldException {
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
	        throw new NoSuchFieldException(fieldName + " does not exist in " + root.getName() + " or any of its superclasses.");
	    }

	    private final sun.misc.Unsafe getUnsafeInstance() {
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
	            }
	            break;
	        }

	        throw new UnsupportedOperationException();
	    }

	    private final long memAddress0(Buffer buffer) {
	        return UNSAFE.getLong(buffer, ADDRESS);
	    }

	    private final void memPutFloat(long ptr, float value) {
	        UNSAFE.putFloat(ptr, value);
	    }

	    private final void put(Matrix4f m, long destAddr) {
	        memPutFloat(destAddr, m.m00);
	        memPutFloat(destAddr + 4, m.m01);
	        memPutFloat(destAddr + 8, m.m02);
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
	        memPutFloat(destAddr, m.m00);
	        memPutFloat(destAddr + 4, m.m10);
	        memPutFloat(destAddr + 8, m.m20);
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

	    final void put(Matrix4f m, int offset, FloatBuffer dest) {
	        put(m, memAddress0(dest) + offset * 4);
	    }

	    final void put(Matrix4f m, int offset, ByteBuffer dest) {
	        put(m, memAddress0(dest) + offset);
	    }

	    final void putTransposed(Matrix4f m, int offset, FloatBuffer dest) {
	        putTransposed(m, memAddress0(dest) + offset * 4);
	    }

	    final void putTransposed(Matrix4f m, int offset, ByteBuffer dest) {
	        putTransposed(m, memAddress0(dest) + offset);
	    }
	}
}
