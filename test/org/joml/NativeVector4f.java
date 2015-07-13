package org.joml;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class NativeVector4f {
    static {
        System.loadLibrary("joml"); //$NON-NLS-1$
    }

    long id;

    static native long alloc();
    static native void free(long id);
    static native int callSequence(long id);
    static native void get(long id, long destAddr);
    static native void set(long id, long origAddr);
    static native void jit(long id);
    static native long addressOf(Buffer buffer);

    public NativeVector4f() {
        id = alloc();
    }

    public NativeVector4f broadcast() {
        jit(id);
        long time1 = System.nanoTime();
        for (int i = 0; i < 100000000; i++)
        callSequence(id);
        long time2 = System.nanoTime();
        System.err.println("Took: " + (time2 - time1) / 1E6 + " ms.");
        System.err.println((time2 - time1) / 1E8 + " ns. per invocation");
        return this;
    }
    public NativeVector4f set(FloatBuffer orig) {
        set(id, addressOf(orig));
        return this;
    }
    public NativeVector4f get(FloatBuffer dest) {
        get(id, addressOf(dest));
        return this;
    }

    public static void main(String[] args) {
        FloatBuffer fb = ByteBuffer.allocateDirect(4 * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        fb.put(0, 2.0f);
        NativeVector4f v = new NativeVector4f();
        v.set(fb);
        v.broadcast();
        v.get(fb);
        System.err.print(fb.get(0) + ", " + fb.get(1) + ", " + fb.get(2) + ", " + fb.get(3));
        System.err.println();
    }

}
