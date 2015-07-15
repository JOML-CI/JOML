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

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Represents a sequence of operations on a matrix, vector or quaternion that are
 * executed in a batch by runtime-generated native code.
 * 
 * @since 2.0.0
 * 
 * @author Kai Burjack
 */
public class Sequence {

    public static final byte OPCODE_MATRIX_MUL_MATRIX = 0x01;
    public static final byte OPCODE_MATRIX_TRANSFORM_VECTOR = 0x02;
    public static final byte OPCODE_MATRIX_TRANSPOSE = 0x03;
    public static final byte OPCODE_MATRIX_INVERT = 0x04;
    public static final byte OPCODE_MATRIX_TRANSLATION_ROTATE_SCALE = 0x05;
    public static final byte OPCODE_MATRIX_ROTATEZ = 0x06;
    public static final byte OPCODE_VECTOR_NEGATE = 0x07;
    public static final byte OPCODE_MATRIX_ROTATE_QUATERNION = 0x08;
    public static final byte OPCODE_MATRIX_GET = 0x09;
    public static final byte OPCODE_MATRIX_IDENTITY = 0x0A;
    public static final byte OPCODE_MATRIX_ROTATEX = 0x0B;
    public static final byte OPCODE_MATRIX_ROTATEY = 0x0C;

    ByteBuffer operations;
    long operationsAddr;
    ByteBuffer arguments;
    long argumentsAddr;
    long functionAddr;

    public Sequence() {
        operations = ByteBuffer.allocateDirect(32 * 16);
        operationsAddr = Native.addressOf(operations);
        arguments = ByteBuffer.allocateDirect(32 * 16 * 4).order(ByteOrder.nativeOrder());
        argumentsAddr = Native.addressOf(arguments);
    }

    private void ensureOperationsSize(int size) {
        if (operations.remaining() < size) {
            int newCap = operations.capacity() * 2;
            ByteBuffer newOperations = ByteBuffer.allocateDirect(newCap);
            operations.rewind();
            newOperations.put(operations);
            operations = newOperations;
            operationsAddr = Native.addressOf(operations);
        }
    }

    private void ensureArgumentsSize(int size) {
        if (arguments.remaining() < size) {
            int newCap = arguments.capacity() * 2;
            ByteBuffer newArguments = ByteBuffer.allocateDirect(newCap).order(ByteOrder.nativeOrder());
            arguments.rewind();
            newArguments.put(arguments);
            arguments = newArguments;
            argumentsAddr = Native.addressOf(arguments);
        }
    }

    public Sequence setArguments(ByteBuffer arguments) {
        this.argumentsAddr = Native.addressOf(arguments) + arguments.position();
        return this;
    }

    public Sequence putOperation(byte opcode) {
        ensureOperationsSize(1);
        operations.put(opcode);
        return this;
    }

    public Sequence putArg(long val) {
        ensureArgumentsSize(8);
        arguments.putLong(val);
        return this;
    }

    public Sequence putArg(int val) {
        ensureArgumentsSize(4);
        arguments.putInt(val);
        return this;
    }

    public Sequence putArg(float val) {
        ensureArgumentsSize(4);
        arguments.putFloat(val);
        return this;
    }

    public Sequence mul(NativeMatrix4f left, NativeMatrix4f right) {
        return mul(left, right, left);
    }

    public Sequence mul(NativeMatrix4f left, NativeMatrix4f right, NativeMatrix4f dest) {
        putOperation(OPCODE_MATRIX_MUL_MATRIX);
        putArg(left.matrixBufferAddr);
        putArg(right.matrixBufferAddr);
        putArg(dest.matrixBufferAddr);
        putArg(0L);
        return this;
    }

    public Sequence transform(NativeMatrix4f matrix, NativeVector4f v) {
        return transform(matrix, v, v);
    }

    public Sequence transform(NativeMatrix4f matrix, NativeVector4f v, NativeVector4f dest) {
        putOperation(OPCODE_MATRIX_TRANSFORM_VECTOR);
        putArg(v.bufferAddress);
        putArg(matrix.matrixBufferAddr);
        putArg(dest.bufferAddress);
        putArg(0L);
        return this;
    }

    public Sequence rotateZ(NativeMatrix4f matrix, float angle) {
        return rotateZ(matrix, angle, matrix);
    }

    public Sequence rotateZ(NativeMatrix4f matrix, float angle, NativeMatrix4f dest) {
        putOperation(OPCODE_MATRIX_ROTATEZ);
        putArg(matrix.matrixBufferAddr);
        putArg((float) Math.sin(angle));
        putArg((float) Math.cos(angle));
        putArg(dest.matrixBufferAddr);
        putArg(0L);
        return this;
    }

    public Sequence transpose(NativeMatrix4f matrix) {
        return transpose(matrix, matrix);
    }

    public Sequence transpose(NativeMatrix4f matrix, NativeMatrix4f dest) {
        putOperation(OPCODE_MATRIX_TRANSPOSE);
        putArg(matrix.matrixBufferAddr);
        putArg(dest.matrixBufferAddr);
        return this;
    }

    public Sequence translationRotateScale(
            NativeMatrix4f matrix,
            float tx, float ty, float tz,
            float qx, float qy, float qz, float qw,
            float sx, float sy, float sz) {
        putOperation(OPCODE_MATRIX_TRANSLATION_ROTATE_SCALE);
        putArg(matrix.matrixBufferAddr);
        // pad to ensure 16 byte alignment
        putArg(0L);
        // always put 4 floats for nice alignment for movaps
        putArg(tx).putArg(ty).putArg(tz).putArg(0.0f);
        putArg(qx).putArg(qy).putArg(qz).putArg(qw);
        putArg(sx).putArg(sy).putArg(sz).putArg(1.0f);
        putArg(matrix.matrixBufferAddr);
        putArg(0L);
        return this;
    }

    public Sequence rotate(NativeMatrix4f matrix, float qx, float qy, float qz, float qw) {
        return rotate(matrix, qx, qy, qz, qw, matrix);
    }

    public Sequence rotate(NativeMatrix4f matrix, float qx, float qy, float qz, float qw, NativeMatrix4f dest) {
        putOperation(OPCODE_MATRIX_ROTATE_QUATERNION);
        putArg(matrix.matrixBufferAddr);
        putArg(dest.matrixBufferAddr);
        putArg(qx).putArg(qy).putArg(qz).putArg(qw);
        return this;
    }

    public Sequence get(NativeMatrix4f matrix, ByteBuffer buffer) {
        putOperation(OPCODE_MATRIX_GET);
        putArg(matrix.matrixBufferAddr);
        putArg(Native.addressOf(buffer) + buffer.position());
        return this;
    }

    public Sequence identity(NativeMatrix4f matrix) {
        putOperation(OPCODE_MATRIX_IDENTITY);
        putArg(matrix.matrixBufferAddr);
        putArg(0L);
        return this;
    }

    public Sequence rotateX(NativeMatrix4f matrix, float angle) {
        return rotateX(matrix, angle, matrix);
    }

    public Sequence rotateX(NativeMatrix4f matrix, float angle, NativeMatrix4f dest) {
        putOperation(OPCODE_MATRIX_ROTATEX);
        putArg(matrix.matrixBufferAddr);
        putArg((float) Math.sin(angle));
        putArg((float) Math.cos(angle));
        putArg(dest.matrixBufferAddr);
        putArg(0L);
        return this;
    }

    public Sequence rotateY(NativeMatrix4f matrix, float angle) {
        return rotateY(matrix, angle, matrix);
    }

    public Sequence rotateY(NativeMatrix4f matrix, float angle, NativeMatrix4f dest) {
        putOperation(OPCODE_MATRIX_ROTATEY);
        putArg(matrix.matrixBufferAddr);
        putArg((float) Math.sin(angle));
        putArg((float) Math.cos(angle));
        putArg(dest.matrixBufferAddr);
        putArg(0L);
        return this;
    }

    public Sequence terminate() {
        operations.flip();
        functionAddr = Native.jit(operationsAddr + operations.position(), operations.remaining());
        operations.clear();
        arguments.flip();
        return this;
    }

    public Sequence call() {
        Native.call(functionAddr, argumentsAddr);
        return this;
    }

}
