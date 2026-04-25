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

import java.lang.reflect.Field;
import java.nio.Buffer;

//#ifdef __HAS_UNSAFE__
class MemUtil$$Field {
//#ifdef __HAS_NIO__
    public static final long ADDRESS;
//#endif
    public static final long Matrix2f_m00;
    public static final long Matrix3f_m00;
    public static final long Matrix3d_m00;
    public static final long Matrix4f_m00;
    public static final long Matrix4d_m00;
    public static final long Matrix4x3f_m00;
    public static final long Matrix3x2f_m00;
    public static final long Vector4f_x;
    public static final long Vector4i_x;
    public static final long Vector3f_x;
    public static final long Vector3i_x;
    public static final long Vector2f_x;
    public static final long Vector2i_x;
    static {
        try {
//#ifdef __HAS_NIO__
            // TODO: The call to getDeclaredField has been removed, but it is not known whether this is feasible.
            ADDRESS = offset(Buffer.class, "address");
//#endif
            Matrix4f_m00 = checkMatrix4f();
            Matrix4d_m00 = checkMatrix4d();
            Matrix4x3f_m00 = checkMatrix4x3f();
            Matrix3f_m00 = checkMatrix3f();
            Matrix3d_m00 = checkMatrix3d();
            Matrix3x2f_m00 = checkMatrix3x2f();
            Matrix2f_m00 = checkMatrix2f();
            Vector4f_x = checkVector4f();
            Vector4i_x = checkVector4i();
            Vector3f_x = checkVector3f();
            Vector3i_x = checkVector3i();
            Vector2f_x = checkVector2f();
            Vector2i_x = checkVector2i();
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    private static long offset(Class<?> clazz, String fieldName) throws NoSuchFieldException {
        if (MemUtil.U1) {
            return MemUtil$$U1.UNSAFE.objectFieldOffset(clazz.getDeclaredField(fieldName));
        } else if (MemUtil.U2) {
            return MemUtil$$U2.UNSAFE.objectFieldOffset(clazz, fieldName);
        }
        //todo: throw or return 0L or -1L, need discuss
        throw new IllegalStateException("Object field offset need Unsafe");
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

    private static long checkMatrix4f() throws NoSuchFieldException, SecurityException {
        long Matrix4f_m00 = offset(Matrix4f.class, "m00");
        // Validate expected field offsets
        for (int i = 1; i < 16; i++) {
            int c = i >>> 2;
            int r = i & 3;
            long offset = offset(Matrix4f.class, "m" + c + r);
            if (offset != Matrix4f_m00 + (i << 2))
                throw new UnsupportedOperationException("Unexpected Matrix4f element offset");
        }
        return Matrix4f_m00;
    }

    private static long checkMatrix4d() throws NoSuchFieldException, SecurityException {
        long Matrix4d_m00 = offset(Matrix4d.class, "m00");
        // Validate expected field offsets
        for (int i = 1; i < 16; i++) {
            int c = i >>> 2;
            int r = i & 3;
            long offset = offset(Matrix4d.class, "m" + c + r);
            if (offset != Matrix4d_m00 + (i << 3))
                throw new UnsupportedOperationException("Unexpected Matrix4d element offset");
        }
        return Matrix4d_m00;
    }

    private static long checkMatrix4x3f() throws NoSuchFieldException, SecurityException {
        long Matrix4x3f_m00 = offset(Matrix4x3f.class, "m00");
        // Validate expected field offsets
        for (int i = 1; i < 12; i++) {
            int c = i / 3;
            int r = i % 3;
            long offset = offset(Matrix4x3f.class, "m" + c + r);
            if (offset != Matrix4x3f_m00 + (i << 2))
                throw new UnsupportedOperationException("Unexpected Matrix4x3f element offset");
        }
        return Matrix4x3f_m00;
    }

    private static long checkMatrix3f() throws NoSuchFieldException, SecurityException {
        long Matrix3f_m00 = offset(Matrix3f.class, "m00");
        // Validate expected field offsets
        for (int i = 1; i < 9; i++) {
            int c = i / 3;
            int r = i % 3;
            long offset = offset(Matrix3f.class, "m" + c + r);
            if (offset != Matrix3f_m00 + (i << 2))
                throw new UnsupportedOperationException("Unexpected Matrix3f element offset");
        }
        return Matrix3f_m00;
    }

    private static long checkMatrix3d() throws NoSuchFieldException, SecurityException {
        long Matrix3d_m00 = offset(Matrix3d.class, "m00");
        // Validate expected field offsets
        for (int i = 1; i < 9; i++) {
            int c = i / 3;
            int r = i % 3;
            long offset = offset(Matrix3d.class, "m" + c + r);
            if (offset != Matrix3d_m00 + (i << 3))
                throw new UnsupportedOperationException("Unexpected Matrix3d element offset");
        }
        return Matrix3d_m00;
    }

    private static long checkMatrix3x2f() throws NoSuchFieldException, SecurityException {
        long Matrix3x2f_m00 = offset(Matrix3x2f.class, "m00");
        // Validate expected field offsets
        for (int i = 1; i < 6; i++) {
            int c = i / 2;
            int r = i % 2;
            long offset = offset(Matrix3x2f.class, "m" + c + r);
            if (offset != Matrix3x2f_m00 + (i << 2))
                throw new UnsupportedOperationException("Unexpected Matrix3x2f element offset");
        }
        return Matrix3x2f_m00;
    }

    private static long checkMatrix2f() throws NoSuchFieldException, SecurityException {
        long Matrix2f_m00 = offset(Matrix2f.class, "m00");
        // Validate expected field offsets
        for (int i = 1; i < 4; i++) {
            int c = i / 2;
            int r = i % 2;
            long offset = offset(Matrix2f.class, "m" + c + r);
            if (offset != Matrix2f_m00 + (i << 2))
                throw new UnsupportedOperationException("Unexpected Matrix2f element offset");
        }
        return Matrix2f_m00;
    }

    private static long checkXYZW_b4(Class<?> clazz, int dism) throws NoSuchFieldException {
        long base = offset(clazz, "x");
        // Validate expected field offsets
        switch (dism) {
            case 4:
                if (offset(clazz, "w") != base + (3 << 2))
                    break;
            case 3:
                if (offset(clazz, "z") != base + (2 << 2))
                    break;
            case 2:
                if (offset(clazz, "y") == base + (1 << 2))
                    return base;
                else
                    break;
            default:
                throw new IllegalArgumentException("Invalid dism: " + dism);
        }
        throw new UnsupportedOperationException("Unexcepted " + clazz.getSimpleName() + " element offset");
    }

    private static long checkVector4f() throws NoSuchFieldException, SecurityException {
        return checkXYZW_b4(Vector4f.class, 4);
    }

    private static long checkVector4i() throws NoSuchFieldException, SecurityException {
        return checkXYZW_b4(Vector4i.class, 4);
    }

    private static long checkVector3f() throws NoSuchFieldException, SecurityException {
        return checkXYZW_b4(Vector3f.class, 3);
    }

    private static long checkVector3i() throws NoSuchFieldException, SecurityException {
        return checkXYZW_b4(Vector3i.class, 3);
    }

    private static long checkVector2f() throws NoSuchFieldException, SecurityException {
        return checkXYZW_b4(Vector2f.class, 2);
    }

    private static long checkVector2i() throws NoSuchFieldException, SecurityException {
        return checkXYZW_b4(Vector2i.class, 2);
    }
}
//#endif
