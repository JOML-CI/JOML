package org.joml;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class NativeMatrix4f {
    static {
        System.loadLibrary("joml");
    }

    public static final byte OPCODE_MUL_MATRIX = 0x01;
    public static final byte OPCODE_MUL_VECTOR = 0x02;
    public static final byte OPCODE_TRANSPOSE = 0x03;
    public static final byte OPCODE_INVERT = 0x04;

    long sequenceFunction;
    long argumentsAddr;

    static native long alloc();

    static native long free();

    static native long jit(long opcodesAddr, int opcodesLength);

    static native void call(long id, long argumentsAddr);

    ByteBuffer operations;
    ByteBuffer arguments;
    Buffer matrixBuffer;
    long matrixBufferAddr;

    public NativeMatrix4f(Buffer matrixBuffer) {
        super();
        this.matrixBuffer = matrixBuffer;
        this.matrixBufferAddr = NativeUtil.addressOf(matrixBuffer) + matrixBuffer.position() * 4;
        int initialNumOfOperations = 8;
        operations = ByteBuffer.allocateDirect(initialNumOfOperations);
        int avgArgumentSizePerOperation = 2 * 8;
        arguments = ByteBuffer.allocateDirect(avgArgumentSizePerOperation * initialNumOfOperations).order(
                ByteOrder.nativeOrder());
        argumentsAddr = NativeUtil.addressOf(arguments);
    }

    private void ensureOperationsSize(int size) {
        if (operations.remaining() < size) {
            int newCap = operations.capacity() * 2;
            ByteBuffer newOperations = ByteBuffer.allocateDirect(newCap);
            operations.rewind();
            newOperations.put(operations);
            operations = newOperations;
        }
    }

    private void ensureArgumentsSize(int size) {
        if (arguments.remaining() < size) {
            int newCap = arguments.capacity() * 2;
            ByteBuffer newArguments = ByteBuffer.allocateDirect(newCap);
            arguments.rewind();
            newArguments.put(arguments);
            arguments = newArguments;
        }
    }

    private void putOperation(byte opcode) {
        ensureOperationsSize(1);
        operations.put(opcode);
    }

    private NativeMatrix4f putArg(long val) {
        ensureArgumentsSize(8);
        arguments.putLong(val);
        return this;
    }

    private NativeMatrix4f putArg(float val) {
        ensureArgumentsSize(4);
        arguments.putFloat(val);
        return this;
    }

    public NativeMatrix4f mulMatrix(long bufferAddress) {
        putOperation(OPCODE_MUL_MATRIX);
        putArg(bufferAddress); // <- the Buffer address storing the other matrix
        return this;
    }

    public NativeMatrix4f mulVector(long vectorBufferAddress) {
        putOperation(OPCODE_MUL_VECTOR);
        putArg(vectorBufferAddress); // the Buffer address storing the vector elements
        putArg(matrixBufferAddr); // the Buffer address of the matrix elements
        return this;
    }

    public NativeMatrix4f call() {
        if (arguments.position() > 0) {
            arguments.rewind();
            call(sequenceFunction, argumentsAddr);
        }
        return this;
    }

    public void terminate() {
        operations.flip();
        sequenceFunction = jit(NativeUtil.addressOf(operations) + operations.position(), operations.remaining());
    }

    public static void main(String[] args) {
        FloatBuffer matrix = ByteBuffer.allocateDirect(4 * 16).order(ByteOrder.nativeOrder()).asFloatBuffer();
        Matrix4f m = new Matrix4f().rotateZ((float) Math.PI);
        m.get(matrix);
        FloatBuffer vector = ByteBuffer.allocateDirect(4 * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        vector.put(1.0f).put(0.0f).put(0.0f).put(1.0f);
        for (int i = 0; i < 4; i++)
        System.err.print(vector.get(i) + " ");
        System.err.println();
        NativeMatrix4f nm = new NativeMatrix4f(matrix);
        nm.mulVector(NativeUtil.addressOf(vector));
        nm.terminate();
        nm.call();
        for (int i = 0; i < 4; i++)
        System.err.print(vector.get(i) + " ");
        System.err.println();

        Vector4f v = new Vector4f(1.0f, 0.0f, 0.0f, 1.0f);
        long time1 = System.nanoTime();
        for (int i = 0; i < 1E8; i++)
            m.transform(v);
        long time2 = System.nanoTime();
        System.err.println("Took: " + (time2 - time1) / 1E6 + " ms.");
        System.err.println((time2 - time1) / 1E8 + " ns. per invocation");
        System.err.println(v);
    }

}
