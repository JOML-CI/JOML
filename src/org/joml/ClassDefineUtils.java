/*
 * (C) Copyright 2015-2016 JOML

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
import java.lang.reflect.Method;

/**
 * Contains utility methods to define a {@link Class} within the JVM.
 * 
 * @author Kai Burjack
 */
class ClassDefineUtils {

    /**
     * Whether classes should be defined as <a href="https://blogs.oracle.com/jrose/entry/anonymous_classes_in_the_vm">HotSpot anonymous classes</a>, if supported. This reduces the overhead of class
     * metadata in the JVM.
     * <p>
     * The default value is <code>true</code>.
     */
    private static final boolean USE_ANONYMOUS_CLASSES = !Options.hasOption("joml.noanonymousclass");

    /**
     * We will load the sun.misc.Unsafe class reflectively, because we cannot expect it to be available.
     */
    private static final Object/* <Unsafe> */ theUnsafe;

    /**
     * The "defineAnonymousClass" method in the Unsafe class to use the HotSpot anonymous class feature.
     */
    private static Method defineAnonymousClass;

    /**
     * The "defineClass" method in the {@link ClassLoader} to define classes normally.
     */
    private static final Method defineClass;

    static {
        try {
            defineClass = ClassLoader.class.getDeclaredMethod("defineClass", new Class[] { String.class, byte[].class, int.class, int.class });
            defineClass.setAccessible(true);
            Object unsafe;
            try {
                Class unsafeClass = ClassLoader.getSystemClassLoader().loadClass("sun.misc.Unsafe");
                Field theUnsafeField = unsafeClass.getDeclaredField("theUnsafe");
                theUnsafeField.setAccessible(true);
                unsafe = theUnsafeField.get(null);
                try {
                    if (USE_ANONYMOUS_CLASSES) {
                        /*
                         * Check if "defineAnonymousClass" is in the sun.misc.Unsafe class.
                         */
                        defineAnonymousClass = unsafeClass.getDeclaredMethod("defineAnonymousClass", new Class[] { Class.class, byte[].class, Object[].class });
                    }
                } catch (NoSuchMethodException e) {
                    /* No defineAnonymousClass */
                }
            } catch (ClassNotFoundException e) {
                /* No Unsafe :-( */
                unsafe = null;
            }
            /* Once set 'theUnsafe' to an Unsafe instance or null. */
            theUnsafe = unsafe;
        } catch (Exception e) {
            e.printStackTrace();
            throw new AssertionError("Could not find method: ClassLoader.defineClass");
        }
    }

    /**
     * Define a class as an anonymous class (if supported by the JVM).
     * <p>
     * See <a href="https://blogs.oracle.com/jrose/entry/anonymous_classes_in_the_vm">anonymous classes in the VM</a> for more information.
     * 
     * @param hostClass
     *            the host class
     * @param bytecode
     *            the bytecode of the defined anonymous class
     * @param constantPoolPatch
     *            the constant pool patch; may be <code>null</code>
     * @return the defined {@link Class}
     */
    private static Class defineAnonymousClass(Class hostClass, byte[] bytecode, Object[] constantPoolPatch) {
        if (defineAnonymousClass == null) {
            throw new RuntimeException("Anonymous classes not supported", null);
        }
        Class clazz;
        try {
            clazz = (Class) defineAnonymousClass.invoke(theUnsafe, new Object[] { hostClass, bytecode, constantPoolPatch });
        } catch (Exception e) {
            throw new RuntimeException("Could not define anonymous class in JVM", e);
        }
        return clazz;
    }

    /**
     * Define the class of the given <code>name</code> whose code is stored in the given <code>definition</code> byte array in the JVM via the given {@link ClassLoader}.
     * 
     * @param cl
     *            the {@link ClassLoader} used to define the class
     * @param name
     *            the name of the class (either API name or internal name; conversion is done automatically)
     * @param definition
     *            the definition as a byte array
     * @param offset
     *            the offset into the given byte array
     * @param length
     *            the number of bytes to take from the byte array
     * @return the defined {@link Class}
     */
    private static Class defineClass(ClassLoader cl, String name, byte[] definition, int offset, int length) {
        String apiName = name.replace('/', '.');
        try {
            Class ret = (Class) defineClass.invoke(cl, new Object[] { apiName, definition, Integer.valueOf(offset), Integer.valueOf(length) });
            return ret;
        } catch (Exception e) {
            throw new RuntimeException("Could not define class in JVM: " + name, e);
        }
    }

    /**
     * Define the class of the given <code>name</code> whose code is stored in the given <code>definition</code> byte array in the JVM via the given {@link ClassLoader}.
     * <p>
     * If <a href="https://blogs.oracle.com/jrose/entry/anonymous_classes_in_the_vm">HotSpot anonymous classes</a> is supported and the <code>hostClass</code> is not <code>null</code>, that host will
     * define the lifecycle and visibility of the anonymous class. In that case, the new class will not be defined via the given or any other {@link ClassLoader} but will use a JVM-internal mechanism
     * instead.
     * 
     * @see #defineClass(ClassLoader, Class, String, byte[], Object[])
     * 
     * @param cl
     *            the {@link ClassLoader} used to define the class
     * @param hostClass
     *            the host class whose linkage properties are shared with the generated class (if anonymous classes are used (feature of Hotspot)); may be <code>null</code>
     * @param name
     *            the name of the class (either API name or internal name; conversion is done automatically)
     * @param definition
     *            the definition as a byte array
     * @return the defined {@link Class}
     */
    public static Class defineClass(ClassLoader cl, Class hostClass, String name, byte[] definition) {
        return defineClass(cl, hostClass, name, definition, null);
    }

    /**
     * Define the class of the given <code>name</code> whose code is stored in the given <code>definition</code> byte array in the JVM via the given {@link ClassLoader}.
     * <p>
     * If <a href="https://blogs.oracle.com/jrose/entry/anonymous_classes_in_the_vm">HotSpot anonymous classes</a> is supported and the <code>hostClass</code> is not <code>null</code>, that host will
     * define the lifecycle and visibility of the anonymous class. In that case, the new class will not be defined via the given or any other {@link ClassLoader} but will use a JVM-internal mechanism
     * instead.
     * 
     * @param cl
     *            the {@link ClassLoader} used to define the class
     * @param hostClass
     *            the host {@link Class} (ignored if anonymous classes are unsupported)
     * @param name
     *            the name
     * @param definition
     *            the definition as a byte array
     * @param constantPoolPatch
     *            not used yet
     * @return the defined {@link Class}
     */
    public static Class defineClass(ClassLoader cl, Class hostClass, String name, byte[] definition, Object[] constantPoolPatch) {
        if (defineAnonymousClass != null && hostClass != null) {
            return defineAnonymousClass(hostClass, definition, constantPoolPatch);
        }
        return defineClass(cl, name, definition, 0, definition.length);
    }

}
