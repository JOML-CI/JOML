/*
 * (C) Copyright 2015 Richard Greenlees

 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in
 all copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 THE SOFTWARE.

 */
package org.joml;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * Represents a vector whose operations will not be immediately but instead
 * batched using a {@link Sequence} for later execution.
 * 
 * @since 2.0.0
 * 
 * @author Kai Burjack
 */
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
