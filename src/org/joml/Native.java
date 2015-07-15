package org.joml;

import java.nio.Buffer;

public class Native {
    static {
        System.loadLibrary("joml");
    }

    public static native long addressOf(Buffer buffer);
    public static native long jit(long opcodesAddr, int opcodesLength);
    public static native void call(long functionAddr, long argumentsAddr);

}
