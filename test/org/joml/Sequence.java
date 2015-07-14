package org.joml;

import java.nio.Buffer;

public class Sequence {

    private final long functionAddr;
    private long argumentsAddr;

    static native void call0(long functionAddr, long argumentsAddr);

    public Sequence(long functionAddr) {
        super();
        this.functionAddr = functionAddr;
    }

    public void setArguments(Buffer arguments) {
        this.argumentsAddr = NativeUtil.addressOf(arguments);
    }

    public void call() {
        call0(functionAddr, argumentsAddr);
    }

}
