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
    Buffer matrixBuffer;
    long matrixBufferAddr;
    Sequence sequence;

    public NativeMatrix4f(Sequence sequence) {
        ByteBuffer bb = ByteBuffer.allocateDirect(4 * 16).order(ByteOrder.nativeOrder());
        this.matrixBuffer = bb;
        this.matrixBufferAddr = Native.addressOf(matrixBuffer);
        this.sequence = sequence;
    }

    public NativeMatrix4f(Buffer matrixBuffer, long offsetIn16Bytes, Sequence sequence) {
        super();
        this.matrixBuffer = matrixBuffer;
        this.matrixBufferAddr = Native.addressOf(matrixBuffer) + offsetIn16Bytes * 16;
        this.sequence = sequence;
    }

    public NativeMatrix4f mul(NativeMatrix4f m) {
        sequence.mul(this, m);
        return this;
    }

    public NativeMatrix4f mul(NativeMatrix4f m, NativeMatrix4f dest) {
        sequence.mul(this, m, dest);
        return this;
    }

    public NativeMatrix4f transform(NativeVector4f v) {
        sequence.transform(this, v);
        return this;
    }

    public NativeMatrix4f transform(NativeVector4f v, NativeVector4f dest) {
        sequence.transform(this, v, dest);
        return this;
    }

    public NativeMatrix4f rotateZ(float angle) {
        sequence.rotateZ(this, angle);
        return this;
    }

    public NativeMatrix4f rotateZ(float angle, NativeMatrix4f dest) {
        sequence.rotateZ(this, angle, dest);
        return this;
    }

    public NativeMatrix4f transpose() {
        sequence.transpose(this);
        return this;
    }

    public NativeMatrix4f transpose(NativeMatrix4f dest) {
        sequence.transpose(this, dest);
        return this;
    }

    public NativeMatrix4f translationRotateScale(
            float tx, float ty, float tz, 
            float qx, float qy, float qz, float qw,
            float sx, float sy, float sz) {
        sequence.translationRotateScale(this, tx, ty, tz, qx, qy, qz, qw, sx, sy, sz);
        return this;
    }

    public NativeMatrix4f rotate(float qx, float qy, float qz, float qw) {
        sequence.rotate(this, qx, qy, qz, qw);
        return this;
    }

    public NativeMatrix4f rotate(float qx, float qy, float qz, float qw, NativeMatrix4f dest) {
        sequence.rotate(this, qx, qy, qz, qw, dest);
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

    public NativeMatrix4f get(ByteBuffer buffer) {
        sequence.get(this, buffer);
        return this;
    }

    public NativeMatrix4f identity() {
        sequence.identity(this);
        return this;
    }

}
