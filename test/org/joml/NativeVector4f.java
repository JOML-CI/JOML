package org.joml;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class NativeVector4f {

    Buffer buffer;
    long offset;
    long bufferAddress;

    public NativeVector4f() {
        this.buffer = ByteBuffer.allocateDirect(4 * 4).order(ByteOrder.nativeOrder());
        this.bufferAddress = Native.addressOf(buffer);
    }

    public NativeVector4f(float x, float y, float z, float w) {
        ByteBuffer bb = ByteBuffer.allocateDirect(4 * 4).order(ByteOrder.nativeOrder());
        bb.putFloat(x).putFloat(y).putFloat(z).putFloat(w).rewind();
        this.buffer = bb;
        this.bufferAddress = Native.addressOf(buffer);
    }

    public NativeVector4f(float x, float y, float z) {
        ByteBuffer bb = ByteBuffer.allocateDirect(4 * 4).order(ByteOrder.nativeOrder());
        bb.putFloat(x).putFloat(y).putFloat(z).putFloat(1.0f).rewind();
        this.buffer = bb;
        this.bufferAddress = Native.addressOf(buffer);
    }

    public NativeVector4f(Buffer buffer, long offsetIn16Bytes) {
        this.buffer = buffer;
        bufferAddress = Native.addressOf(buffer) + 16 * offsetIn16Bytes;
    }

    public String toString() {
        if (buffer instanceof ByteBuffer) {
            ByteBuffer bb = (ByteBuffer) buffer;
            return bb.getFloat(0) + ", " +
                   bb.getFloat(4) + ", " +
                   bb.getFloat(8) + ", " +
                   bb.getFloat(12);
        } else if (buffer instanceof FloatBuffer) {
            FloatBuffer bb = (FloatBuffer) buffer;
            return bb.get(0) + ", " +
                   bb.get(1) + ", " +
                   bb.get(2) + ", " +
                   bb.get(3);
        }
        return null;
    }

}
