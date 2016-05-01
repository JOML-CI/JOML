package org.joml;

import java.io.IOException;

abstract class JNI {

    static final boolean hasSse;
    static {
        try {
            SharedLibraryLoader.load();
            hasSse = hasSse();
        } catch (IOException e) {
            e.printStackTrace();
            throw new AssertionError("Could not load JOML shared library: " + e.getMessage());
        }
    }

    private JNI() {
    }

    static final void touch() {
        if (!hasSse) {
            throw new AssertionError("Your CPU does not support the Streaming SIMD Extensions (SSE) instructions.");
        }
    }

    private static final native void registerNatives();

    static final native boolean hasSse();

}
