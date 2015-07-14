package org.joml;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class NativeMatrix4f {
    static {
        System.loadLibrary("joml"); //$NON-NLS-1$
    }

    long id;

    static native long alloc();
    static native void mulVector(long matrixId, long vectorId);
    static native void get(long id, long destAddr);
    static native void set(long id, long origAddr);
    static native void jit(long id);
    static native long addressOf(Buffer buffer);

    public NativeMatrix4f() {
        id = alloc();
    }

    public NativeMatrix4f mulVector(long vectorId) {
        jit(id);
        long time1 = System.nanoTime();
        for (int i = 0; i < 1E8; i++)
        mulVector(id, vectorId);
        long time2 = System.nanoTime();
        System.err.println("Took: " + (time2 - time1) / 1E6 + " ms.");
        System.err.println((time2 - time1) / 1E8 + " ns. per invocation");
        return this;
    }
    public NativeMatrix4f set(FloatBuffer orig) {
        set(id, addressOf(orig));
        return this;
    }
    public NativeMatrix4f get(FloatBuffer dest) {
        get(id, addressOf(dest));
        return this;
    }

    public static void main(String[] args) {
    	FloatBuffer matrix = ByteBuffer.allocateDirect(4 * 16).order(ByteOrder.nativeOrder()).asFloatBuffer();
    	Matrix4f m = new Matrix4f().rotateZ((float) Math.PI);
    	m.get(matrix);
        FloatBuffer vector = ByteBuffer.allocateDirect(4 * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        vector.put(1.0f).put(0.0f).put(0.0f).put(1.0f).rewind();
        NativeMatrix4f nm = new NativeMatrix4f();
        nm.set(matrix);
        nm.mulVector(addressOf(vector));
        System.err.print(vector.get(0) + ", " + vector.get(1) + ", " + vector.get(2) + ", " + vector.get(3));
        System.err.println();
        
        Vector4f v = new Vector4f(1.0f, 0.0f, 0.0f, 1.0f);
        long time1 = System.nanoTime();
        for (int i = 0; i < 1E8; i++)
        m.transform(v);
        long time2 = System.nanoTime();
        System.err.println("Took: " + (time2 - time1) / 1E6 + " ms.");
        System.err.println((time2 - time1) / 1E8 + " ns. per invocation");
    }

}
