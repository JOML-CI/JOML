/*
 * (C) Copyright 2016 JOML

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
 * Utility class for reading system properties.
 * 
 * @author Kai Burjack
 */
class Options {

    /**
     * Whether to use JNI to accelerate some operations.
     */
    static final boolean WITH_JNI = Options.hasOption("joml.jni");

    /**
     * Whether certain debugging checks should be made, such as that only direct NIO Buffers are used when Unsafe is active,
     * and a proxy should be created on calls to toImmutable().
     */
    static final boolean DEBUG = hasOption("joml.debug");

    /**
     * Whether <i>not</i> to use sun.misc.Unsafe when copying memory with MemUtil.
     */
    static final boolean NO_UNSAFE = Options.hasOption("joml.nounsafe");

    /**
     * Whether fast approximations of some java.lang.Math operations should be used.
     */
    static final boolean FASTMATH = Options.hasOption("joml.fastmath");

    /**
     * When {@link #FASTMATH} is <code>true</code>, whether to use a lookup table for sin/cos.
     */
    static final boolean SIN_LOOKUP = Options.hasOption("joml.sinLookup");

    /**
     * When {@link #SIN_LOOKUP} is <code>true</code>, this determines the table size.
     */
    static final int SIN_LOOKUP_BITS = Integer.parseInt(System.getProperty("joml.sinLookup.bits", "14"));

    static boolean hasOption(String option) {
        String v = System.getProperty(option);
        if (v == null)
            return false;
        if (v.trim().length() == 0)
            return true;
        return Boolean.valueOf(v).booleanValue();
    }

}
