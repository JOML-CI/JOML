package org.joml;

import java.io.IOException;

abstract class JNI {

    private static final int SSE = 1;
    private static final int AVX = 2;

    static final boolean hasSse;
    static final boolean hasAvx;
    static {
        try {
            SharedLibraryLoader.load();
            int supportedExtensions = supportedExtensions();
            hasSse = (supportedExtensions & SSE) != 0;
            hasAvx = (supportedExtensions & AVX) != 0;
        } catch (IOException e) {
            e.printStackTrace();
            throw new AssertionError("Could not load JOML shared library: " + e.getMessage());
        }
    }

    private JNI() {
    }

    static final void touch() {
    }

    private static final native void registerNatives();

    static final native int supportedExtensions();

}
