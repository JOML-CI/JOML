package org.joml;

import java.io.IOException;

abstract class JNI {

    private static final int SSE = 1;
    private static final int AVX = 2;
    private static final int AVX2 = 4;
    private static final int FMA3 = 8;
    private static final int FMA4 = 16;

    static final boolean hasSse;
    static final boolean hasAvx;
    static final boolean hasAvx2;
    static final boolean hasFma3;
    static final boolean hasFma4;
    static {
        try {
            SharedLibraryLoader.load();
            int supportedExtensions = supportedExtensions();
            hasSse = (supportedExtensions & SSE) != 0;
            hasAvx = (supportedExtensions & AVX) != 0;
            hasAvx2 = (supportedExtensions & AVX2) != 0;
            hasFma3 = (supportedExtensions & FMA3) != 0;
            hasFma4 = (supportedExtensions & FMA4) != 0;
        } catch (IOException e) {
            e.printStackTrace();
            throw new AssertionError("Could not load JOML shared library: " + e.getMessage());
        }
    }

    private JNI() {
    }

    static final void touch() {
    }

    static final native int supportedExtensions();

}
