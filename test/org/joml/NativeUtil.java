package org.joml;

import java.nio.Buffer;

public class NativeUtil {
    static {
        System.loadLibrary("joml");
    }

    public static final native long addressOf(Buffer buffer);

}
