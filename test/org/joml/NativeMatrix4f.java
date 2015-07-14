package org.joml;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * This is a representation of a 4x4 matrix using the new JIT execution
 * environment in JOML's 2.
 * <p>
 * The API is almost identical to the {@link Matrix4f}, only that the
 * {@link NativeMatrix4f} does not execute the operations immediately but
 * instead stores them for subsequent batch execution by a runtime-generated
 * native function.
 * <p>
 * The {@link NativeMatrix4f} is therefore a convenient interface to the
 * underlying JIT engine in order to generate native functions using known
 * matrix operations.
 * 
 * @author Kai Burjack
 */
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

    ByteBuffer operations;
    ByteBuffer arguments;
    long matrixBufferAddr;

    public NativeMatrix4f(Buffer matrixBuffer) {
        super();
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
            ByteBuffer newArguments = ByteBuffer.allocateDirect(newCap).order(ByteOrder.nativeOrder());
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

    public NativeMatrix4f mul(NativeMatrix4f m) {
        putOperation(OPCODE_MUL_MATRIX);
        putArg(matrixBufferAddr);
        putArg(m.matrixBufferAddr);
        return this;
    }

    public NativeMatrix4f mul(NativeVector4f v) {
        putOperation(OPCODE_MUL_VECTOR);
        putArg(v.bufferAddress);
        putArg(matrixBufferAddr);
        return this;
    }

    public NativeMatrix4f transpose() {
        putOperation(OPCODE_TRANSPOSE);
        putArg(matrixBufferAddr);
        return this;
    }

    public Sequence terminate() {
        operations.flip();
        sequenceFunction = Jit.jit(NativeUtil.addressOf(operations) + operations.position(), operations.remaining());
        return new Sequence(sequenceFunction);
    }

    public static void main(String[] args) {
        FloatBuffer matrix = ByteBuffer.allocateDirect(4 * 16).order(ByteOrder.nativeOrder()).asFloatBuffer();
        Matrix4f m = new Matrix4f().rotateZ((float) Math.PI);
        System.err.println(m);
        m.get(matrix);
        NativeMatrix4f nm = new NativeMatrix4f(matrix);
        nm.transpose();
        Sequence seq = nm.terminate();
        seq.setArguments(nm.arguments);
        long time1 = System.nanoTime();
        seq.call();
        long time2 = System.nanoTime();
        System.err.println("Took " + (time2- time1) / 1E6f + " ms.");
        m.set(matrix);
        System.err.println(m);
        time1 = System.nanoTime();
        for (int i = 0; i < 1E7; i++)
        m.transpose();
        time2 = System.nanoTime();
        System.err.println("Took " + (time2- time1) / 1E6f + " ms.");
    }

}
