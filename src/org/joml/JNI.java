package org.joml;

abstract class JNI {

    private static final boolean nojni = Options.hasOption("joml.nojni");

    private static final int SSE = 1;
    private static final int AVX = 2;

    static final int supported = supported();
    static final boolean hasSse = (supported & SSE) == SSE;
    static final boolean hasAvx = (supported & AVX) == AVX;
    static final boolean supportsNative = supported != 0;

    private static int supported() {
        if (nojni)
            return 0;
        try {
            SharedLibraryLoader.load();
            return supportedExtensions();
        } catch (Exception e) {
            return 0;
        }
    }

    private JNI() {
    }

    private static final native int supportedExtensions();

}
