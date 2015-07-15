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
    public static final byte OPCODE_TRANSLATION_ROTATE_SCALE = 0x05;
    public static final byte OPCODE_ROTATEZ = 0x06;
    public static final byte OPCODE_VECTOR_NEGATE = 0x07;

    long sequenceFunction;

    ByteBuffer operations;
    ByteBuffer arguments;
    Buffer matrixBuffer;
    long matrixBufferAddr;

    public NativeMatrix4f() {
        ByteBuffer bb = ByteBuffer.allocateDirect(4 * 16).order(ByteOrder.nativeOrder());
        FloatBuffer fb = bb.asFloatBuffer();
        fb.put(0, 1.0f);
        fb.put(5, 1.0f);
        fb.put(10, 1.0f);
        fb.put(15, 1.0f);
        this.matrixBuffer = bb;
        this.matrixBufferAddr = NativeUtil.addressOf(matrixBuffer);
        int initialNumOfOperations = 8;
        operations = ByteBuffer.allocateDirect(initialNumOfOperations);
        int avgArgumentSizePerOperation = 2 * 8;
        arguments = ByteBuffer.allocateDirect(avgArgumentSizePerOperation * initialNumOfOperations).order(
                ByteOrder.nativeOrder());
    }

    public NativeMatrix4f(Buffer matrixBuffer, long offsetIn16Bytes) {
        super();
        this.matrixBuffer = matrixBuffer;
        this.matrixBufferAddr = NativeUtil.addressOf(matrixBuffer) + offsetIn16Bytes * 16;
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

    private NativeMatrix4f putArg(int val) {
        ensureArgumentsSize(4);
        arguments.putInt(val);
        return this;
    }

    private NativeMatrix4f putArg(float val) {
        ensureArgumentsSize(4);
        arguments.putFloat(val);
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

    public NativeMatrix4f rotateZ(float angle) {
        putOperation(OPCODE_ROTATEZ);
        putArg(matrixBufferAddr);
        putArg((float) Math.sin(angle));
        putArg((float) Math.cos(angle));
        return this;
    }

    public NativeMatrix4f transpose() {
        putOperation(OPCODE_TRANSPOSE);
        putArg(matrixBufferAddr);
        // pad to ensure 16 byte alignment (some operations need it!)
        putArg(0L);
        return this;
    }

    public NativeMatrix4f negate(NativeVector4f v) {
        putOperation(OPCODE_VECTOR_NEGATE);
        putArg(v.bufferAddress);
        putArg(0L);
        return this;
    }

    public NativeMatrix4f translationRotateScale(
            float tx, float ty, float tz, 
            float qx, float qy, float qz, float qw, 
            float sx, float sy, float sz) {
        putOperation(OPCODE_TRANSLATION_ROTATE_SCALE);
        putArg(matrixBufferAddr);
        // pad to ensure 16 byte alignment
        putArg(0L);
        // always put 4 floats for nice alignment for movaps
        putArg(tx).putArg(ty).putArg(tz).putArg(0.0f);
        putArg(qx).putArg(qy).putArg(qz).putArg(qw);
        putArg(sx).putArg(sy).putArg(sz).putArg(0.0f);
        return this;
    }

    public NativeMatrix4f set(Matrix4f m) {
        if (matrixBuffer instanceof ByteBuffer) {
            ByteBuffer byteBuffer = (ByteBuffer) matrixBuffer;
            FloatBuffer fb = byteBuffer.asFloatBuffer();
            m.get(fb);
        } else if (matrixBuffer instanceof FloatBuffer) {
            FloatBuffer floatBuffer = (FloatBuffer) matrixBuffer;
            m.get(floatBuffer);
        }
        return this;
    }

    public NativeMatrix4f get(Matrix4f m) {
        if (matrixBuffer instanceof ByteBuffer) {
            ByteBuffer byteBuffer = (ByteBuffer) matrixBuffer;
            FloatBuffer fb = byteBuffer.asFloatBuffer();
            m.set(fb);
        } else if (matrixBuffer instanceof FloatBuffer) {
            FloatBuffer floatBuffer = (FloatBuffer) matrixBuffer;
            m.set(floatBuffer);
        }
        return this;
    }

    public Sequence terminate() {
        operations.flip();
        sequenceFunction = Jit.jit(NativeUtil.addressOf(operations) + operations.position(), operations.remaining());
        operations.clear();
        Sequence seq = new Sequence(sequenceFunction);
        seq.setArguments(arguments);
        return seq;
    }

    public static void main(String[] args) {
        NativeVector4f nv = new NativeVector4f(1.0f, 2.0f, 3.0f);
        NativeMatrix4f nm = new NativeMatrix4f();
        nm.rotateZ(0.1263f);
        Sequence seq = nm.terminate();
        long time1 = System.nanoTime();
        seq.call();
        Matrix4f m = new Matrix4f().rotateZ(0.1263f);
        System.err.println(m);
        nm.get(m);
        System.err.println(m.toString());
        long time2 = System.nanoTime();
        System.err.println("SSE result (" + (time2 - time1) / 1E3 + " µs):");
        
        Vector4f v = new Vector4f(1, 2, 3, 1);
        time1 = System.nanoTime();
        for (int i = 0; i < 500000; i++)
            v.negate();
        time2 = System.nanoTime();
        System.err.println("JOML result (" + (time2 - time1) / 1E3 + " µs):");
    }

}
