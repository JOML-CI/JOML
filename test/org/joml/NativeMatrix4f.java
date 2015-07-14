package org.joml;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * This is a representation of a 4x4 matrix using the new JIT execution
 * environment in JOML 2.0.
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
        FloatBuffer matrix2 = ByteBuffer.allocateDirect(4 * 16).order(ByteOrder.nativeOrder()).asFloatBuffer();
        Matrix4f m = new Matrix4f().rotateZ((float) Math.PI / 3.0f).rotateY((float) Math.PI / 1.6f).rotateZ((float)Math.PI / 5.3f);
        System.err.println("Origin matrix:");
        System.err.println(m);
        Matrix4f m2 = new Matrix4f().rotateX(0.152f).rotateY(0.0123f).translate(1, 2, 3);
        m.get(matrix);
        m2.get(matrix2);
        NativeMatrix4f nm = new NativeMatrix4f(matrix);
        NativeMatrix4f nm2 = new NativeMatrix4f(matrix2);
        for (int i = 0; i < 1000; i++) {
            nm.transpose();
        }
        Sequence seq = nm.terminate();
        seq.setArguments(nm.arguments);
        long time1 = System.nanoTime();
        for (int i = 0; i < 1000; i++)
            seq.call();
        long time2 = System.nanoTime();
        System.err.println("SSE result (" + (time2 - time1) / 1E3 + "):");
        Matrix4f res = new Matrix4f();
        res.set(matrix);
        System.err.println(res);
        time1 = System.nanoTime();
        for (int i = 0; i < 1E6; i++) {
            m.transpose();
        }
        time2 = System.nanoTime();
        System.err.println("JOML result (" + (time2 - time1) / 1E3 + "):");
        System.err.println(m);
    }

}
