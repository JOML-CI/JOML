package org.joml;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class NativeUtil {
    static {
        System.loadLibrary("joml");
    }

    public static final native long addressOf(Buffer buffer);

}
