package org.joml;

import java.nio.Buffer;

public class NativeVector4f {

    public Buffer buffer;
    final long bufferAddress;

    public NativeVector4f(Buffer buffer) {
        super();
        this.buffer = buffer;
        bufferAddress = NativeUtil.addressOf(buffer);
    }

}
