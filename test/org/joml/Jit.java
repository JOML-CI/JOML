package org.joml;

import java.nio.ByteBuffer;

public class Jit {
    static {
        System.loadLibrary("joml");
    }

    static native long jit(long opcodesAddr, int opcodesLength);

    public static Sequence jit(ByteBuffer opcodes) {
        long opcodesAddr = NativeUtil.addressOf(opcodes) + opcodes.position();
        int opcodesLength = opcodes.remaining() - opcodes.position();
        long funcAddr = jit(opcodesAddr, opcodesLength);
        return new Sequence(funcAddr);
    }

}
