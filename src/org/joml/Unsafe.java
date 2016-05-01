/*
 * (C) Copyright 2015-2016 Richard Greenlees

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

/**
 * Provides direct access to sun.misc.Unsafe.
 * 
 * @author Kai Burjack
 */
class Unsafe {

    static final sun.misc.Unsafe UNSAFE = getUnsafeInstance();

    private static final native long allocate();

    private static final native void mulNative(long a, long b, long dest);

    private static final native void invertNative(long a, long dest);

    private static sun.misc.Unsafe getUnsafeInstance() {
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

    static final float get(long address) {
        return UNSAFE.getFloat(address);
    }

    static final double getDouble(long address) {
        return UNSAFE.getDouble(address);
    }

    static final void set(long address, float value) {
        UNSAFE.putFloat(address, value);
    }

    static final void set(long address, double value) {
        UNSAFE.putDouble(address, value);
    }

}
