/*
 * (C) Copyright 2015 The JOML Authors

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

    // Opcode masks
    public static final byte OPCODE_MASK_SECOND = (byte) 0x80;

    // Arithmetic opcodes
    public static final byte OPCODE_MATRIX_IDENTITY = 0x01;
    public static final byte OPCODE_MATRIX_MUL_MATRIX = 0x02;
    public static final byte OPCODE_MATRIX_TRANSPOSE = 0x03;
    public static final byte OPCODE_MATRIX_ROTATEX = 0x04;
    public static final byte OPCODE_MATRIX_ROTATEY = 0x05;
    public static final byte OPCODE_MATRIX_ROTATEZ = 0x06;
    public static final byte OPCODE_MATRIX_TRANSLATE = 0x07;
    public static final byte OPCODE_MATRIX_SCALE = 0x08;
    public static final byte OPCODE_MATRIX_INVERT = 0x09;
    public static final byte OPCODE_MATRIX_TRANSFORM_VECTOR = 0x0A;
    public static final byte OPCODE_MATRIX_ROTATE_QUATERNION = 0x0B;
    public static final byte OPCODE_MATRIX_TRANSLATION_ROTATE_SCALE = 0x0C;
    public static final byte OPCODE_VECTOR_NEGATE = 0x0D;

    // Memory management opcodes
    public static final byte OPCODE_STORE = (byte) 0x40;
    public static final byte OPCODE_LOAD = (byte) 0x41;
    public static final byte OPCODE_EXCHANGE = (byte) 0x42;
    public static final byte OPCODE_COPY = (byte) 0x43;
    public static final byte OPCODE_COPY_MEM = (byte) 0x44;

    int maxOperationsCount = 8192;
    ByteBuffer codeSizeBuffer = ByteBuffer.allocateDirect(4).order(ByteOrder.nativeOrder());
    ByteBuffer operations;
    long operationsAddr;
    ByteBuffer arguments;
    long argumentsAddr;
    long functionAddr;
    int codeSize;
    boolean terminated;
    boolean mayExpand;

    /* State when building the sequence */
    long first = 0L;
    boolean firstInSync;
    long second = 0L;
    boolean secondInSync;

    /**
     * Create a new {@link Sequence} using the given {@link ByteBuffer} to store the <code>operations</code> as well as another to store the <code>arguments</code>.
     * <p>
     * The Sequence will not allocate a new ByteBuffer for either of them once their limit is reached.
     * For the operations and arguments to be able to grow dynamically, use the no-args constructor {@link #Sequence()}.
     * 
     * @param operations
     *              a user-provided ByteBuffer to hold the operation opcodes
     * @param arguments
     *              a user-provided ByteBuffer for the operation's actual arguments
     */
    public Sequence(ByteBuffer operations, ByteBuffer arguments) {
        this.operations = operations.slice();
        this.arguments = arguments.slice();
    }

    /**
     * Create a new {@link Sequence} by allocating a {@link ByteBuffer} to hold the operations and one to hold
     * the actual arguments and limiting the maximum number of operations to 8192.
     * <p>
     * Each ByteBuffer can grow dynamically.
     * <p>
     * It is also possible to make the Sequence use provided ByteBuffers by using the {@link #Sequence(ByteBuffer, ByteBuffer)}
     * constructor.  
     */
    public Sequence() {
        operations = ByteBuffer.allocateDirect(32);
        operationsAddr = Native.addressOf(operations);
        arguments = ByteBuffer.allocateDirect(32 * 16 * 4).order(ByteOrder.nativeOrder());
        argumentsAddr = Native.addressOf(arguments);
        mayExpand = true;
    }

    /**
     * Create a new {@link Sequence} by allocating a {@link ByteBuffer} to hold the operations and one to hold
     * the actual arguments and limiting the maximum operations to the given number.
     * <p>
     * Each ByteBuffer can grow dynamically.
     * <p>
     * It is also possible to make the Sequence use provided ByteBuffers by using the {@link #Sequence(ByteBuffer, ByteBuffer)}
     * constructor.  
     * 
     * @param maxOperationCount
     *          the maximum number of operations allowed
     */
    public Sequence(int maxOperationCount) {
        this();
        this.maxOperationsCount = maxOperationCount;
    }

    public int getMaxNumOperations() {
        return maxOperationsCount;
    }

    public void setMaxOperationsCount(int maxOperationsCount) {
        this.maxOperationsCount = maxOperationsCount;
    }

    private void ensureOperationsSize(int size) {
        if (operations.remaining() < size) {
            if (!mayExpand) {
                throw new IllegalStateException("operations buffer full");
            }
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
            if (!mayExpand) {
                throw new IllegalStateException("arguments buffer full");
            }
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
        if (terminated) {
            // validate opcode instead of writing it.
            if (!operations.hasRemaining() || operations.get() != opcode) {
                throw new IllegalStateException("wrong sequence");
            }
            return this;
        }
        if (operations.position() >= maxOperationsCount) {
            throw new IllegalStateException("max operations reached");
        }
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

    private void loadFirst(long addr) {
        if (first == addr) {
            // Already loaded
            return;
        }
        if (first != 0L && !firstInSync) {
            // spill current 'first' to memory
            storeFirst();
        }
        putOperation(OPCODE_LOAD);
        putArg(addr);
        putArg(0L);
        first = addr;
        firstInSync = true;
    }

    private void loadSecond(long addr) {
        if (second == addr) {
            // Already loaded
            return;
        }
        if (second != 0L && !secondInSync) {
            // spill current 'second' to memory
            storeSecond();
        }
        putOperation((byte) (OPCODE_LOAD | OPCODE_MASK_SECOND));
        putArg(addr);
        putArg(0L);
        second = addr;
        secondInSync = true;
    }

    private void storeFirst() {
        if (first != 0L) {
            putOperation(OPCODE_STORE);
            putArg(first);
            putArg(0L);
            firstInSync = true;
        }
    }

    private void storeFirst(long addr) {
        putOperation(OPCODE_STORE);
        putArg(addr);
        putArg(0L);
        if (addr == first) {
            firstInSync = true;
        }
    }

    private void storeSecond() {
        if (second != 0L) {
            putOperation((byte) (OPCODE_STORE | OPCODE_MASK_SECOND));
            putArg(second);
            putArg(0L);
            secondInSync = true;
        }
    }

    private void storeSecond(long addr) {
        if (addr != 0L) {
            putOperation((byte) (OPCODE_STORE | OPCODE_MASK_SECOND));
            putArg(addr);
            putArg(0L);
            if (addr == second) {
                secondInSync = true;
            }
        }
    }

    public Sequence set(NativeMatrix4f src, NativeMatrix4f dst) {
        return copy(src.matrixBufferAddr, dst.matrixBufferAddr);
    }

    public Sequence set(NativeMatrix4f src, ByteBuffer dst) {
        return copy(src.matrixBufferAddr, Native.addressOf(dst) + dst.position());
    }

    public Sequence set(ByteBuffer src, NativeMatrix4f dst) {
        return copy(Native.addressOf(src) + src.position(), dst.matrixBufferAddr);
    }

    public Sequence copy(long src, long dst) {
        if (first == dst && second == src) {
            putOperation(OPCODE_COPY);
        } else if (second == dst && first == src) {
            putOperation((byte) (OPCODE_COPY | OPCODE_MASK_SECOND));
        } else if (first == dst) {
            loadFirst(src);
        } else if (second == dst) {
            loadSecond(src);
        } else if (first == src) {
            storeFirst(dst);
        } else if (second == src) {
            storeSecond(dst);
        } else {
            /* Neither of them is in registers */
            putOperation(OPCODE_COPY_MEM);
            putArg(src);
            putArg(dst);
        }
        return this;
    }

    public Sequence mul(NativeMatrix4f left, NativeMatrix4f right) {
        return mul(left, right, left);
    }

    public Sequence mul(NativeMatrix4f left, NativeMatrix4f right, NativeMatrix4f dest) {
        loadFirst(left.matrixBufferAddr);
        loadSecond(right.matrixBufferAddr);
        byte mask = 0x00;
        if (left.matrixBufferAddr != dest.matrixBufferAddr) {
            mask |= OPCODE_MASK_SECOND;
            secondInSync = false;
        } else {
            firstInSync = false;
        }
        putOperation((byte) (OPCODE_MATRIX_MUL_MATRIX | mask));
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

    public Sequence transpose(NativeMatrix4f matrix) {
        return transpose(matrix, matrix);
    }

    public Sequence transpose(NativeMatrix4f matrix, NativeMatrix4f dest) {
        byte mask = 0x00;
        loadFirst(matrix.matrixBufferAddr);
        if (matrix.matrixBufferAddr != dest.matrixBufferAddr) {
            mask |= OPCODE_MASK_SECOND;
            loadSecond(dest.matrixBufferAddr);
            secondInSync = false;
        } else {
            firstInSync = false;
        }
        putOperation((byte) (OPCODE_MATRIX_TRANSPOSE | mask));
        return this;
    }

    public Sequence translationRotateScale(
            NativeMatrix4f matrix,
            float tx, float ty, float tz,
            float qx, float qy, float qz, float qw,
            float sx, float sy, float sz) {
        loadFirst(matrix.matrixBufferAddr);
        firstInSync = false;
        putOperation(OPCODE_MATRIX_TRANSLATION_ROTATE_SCALE);
        // always put 4 floats for nice alignment for movaps
        putArg(tx).putArg(ty).putArg(tz).putArg(1.0f);
        putArg(qx).putArg(qy).putArg(qz).putArg(qw);
        putArg(sx).putArg(sy).putArg(sz).putArg(0.0f);
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

    public Sequence identity(NativeMatrix4f matrix) {
        loadFirst(matrix.matrixBufferAddr);
        putOperation(OPCODE_MATRIX_IDENTITY);
        firstInSync = false;
        return this;
    }

    public Sequence rotateX(NativeMatrix4f matrix, float angle) {
        return rotateX(matrix, angle, matrix);
    }

    public Sequence rotateX(NativeMatrix4f matrix, float angle, NativeMatrix4f dest) {
        byte mask = 0x00;
        loadFirst(matrix.matrixBufferAddr);
        if (matrix.matrixBufferAddr != dest.matrixBufferAddr) {
            mask |= OPCODE_MASK_SECOND;
            loadSecond(dest.matrixBufferAddr);
            secondInSync = false;
        } else {
            firstInSync = false;
        }
        putOperation((byte) (OPCODE_MATRIX_ROTATEX | mask));
        putArg((float) Math.sin(angle));
        putArg((float) Math.cos(angle));
        putArg(0L);
        return this;
    }

    public Sequence rotateY(NativeMatrix4f matrix, float angle) {
        return rotateY(matrix, angle, matrix);
    }

    public Sequence rotateY(NativeMatrix4f matrix, float angle, NativeMatrix4f dest) {
        byte mask = 0x00;
        loadFirst(matrix.matrixBufferAddr);
        if (matrix.matrixBufferAddr != dest.matrixBufferAddr) {
            mask |= OPCODE_MASK_SECOND;
            loadSecond(dest.matrixBufferAddr);
            secondInSync = false;
        } else {
            firstInSync = false;
        }
        putOperation((byte) (OPCODE_MATRIX_ROTATEY | mask));
        putArg((float) Math.sin(angle));
        putArg((float) Math.cos(angle));
        putArg(0L);
        return this;
    }

    public Sequence rotateZ(NativeMatrix4f matrix, float angle) {
        return rotateZ(matrix, angle, matrix);
    }

    public Sequence rotateZ(NativeMatrix4f matrix, float angle, NativeMatrix4f dest) {
        byte mask = 0x00;
        loadFirst(matrix.matrixBufferAddr);
        if (matrix.matrixBufferAddr != dest.matrixBufferAddr) {
            mask |= OPCODE_MASK_SECOND;
            loadSecond(dest.matrixBufferAddr);
            secondInSync = false;
        } else {
            firstInSync = false;
        }
        putOperation((byte) (OPCODE_MATRIX_ROTATEZ | mask));
        putArg((float) Math.sin(angle));
        putArg((float) Math.cos(angle));
        putArg(0L);
        return this;
    }

    public Sequence translate(NativeMatrix4f matrix, float x, float y, float z) {
        return translate(matrix, x, y, z, matrix);
    }

    public Sequence translate(NativeMatrix4f matrix, float x, float y, float z, NativeMatrix4f dest) {
        byte mask = 0x00;
        loadFirst(matrix.matrixBufferAddr);
        if (matrix.matrixBufferAddr != dest.matrixBufferAddr) {
            mask |= OPCODE_MASK_SECOND;
            loadSecond(dest.matrixBufferAddr);
            secondInSync = false;
        } else {
            firstInSync = false;
        }
        putOperation((byte) (OPCODE_MATRIX_TRANSLATE | mask));
        putArg(x).putArg(y).putArg(z).putArg(0.0f);
        return this;
    }

    public Sequence scale(NativeMatrix4f matrix, float x, float y, float z) {
        return scale(matrix, x, y, z, matrix);
    }

    public Sequence scale(NativeMatrix4f matrix, float x, float y, float z, NativeMatrix4f dest) {
        byte mask = 0x00;
        loadFirst(matrix.matrixBufferAddr);
        if (matrix.matrixBufferAddr != dest.matrixBufferAddr) {
            mask |= OPCODE_MASK_SECOND;
            loadSecond(dest.matrixBufferAddr);
            secondInSync = false;
        } else {
            firstInSync = false;
        }
        putOperation((byte) (OPCODE_MATRIX_SCALE | mask));
        putArg(x).putArg(y).putArg(z).putArg(1.0f);
        return this;
    }

    public Sequence invert(NativeMatrix4f matrix) {
        loadFirst(matrix.matrixBufferAddr);
        firstInSync = false;
        putOperation(OPCODE_MATRIX_INVERT);
        return this;
    }

    public Sequence negate(NativeVector4f vector) {
        putOperation(OPCODE_VECTOR_NEGATE);
        putArg(vector.bufferAddress);
        putArg(0L);
        return this;
    }

    public Sequence terminate() {
        if (terminated) {
            return this;
        }
        if (first != 0L && !firstInSync) {
            storeFirst();
        }
        if (second != 0L && !secondInSync) {
            storeSecond();
        }
        terminated = true;
        operations.flip();
        functionAddr = Native.jit(operationsAddr + operations.position(), operations.remaining(), Native.addressOf(codeSizeBuffer));
        codeSize = codeSizeBuffer.getInt(0);
        arguments.flip();
        return this;
    }

    public Sequence call() {
        if (!terminated) {
            terminate();
        }
        first = 0L;
        firstInSync = false;
        second = 0L;
        secondInSync = false;
        operations.rewind();
        arguments.rewind();
        Native.call(functionAddr, argumentsAddr);
        return this;
    }

    public void clear() {
        if (terminated) {
            if (Native.free(functionAddr, codeSize) != 1) {
                throw new IllegalStateException("could not free Sequence");
            }
            functionAddr = 0L;
            terminated = false;
            codeSize = 0;
        }
        first = 0L;
        firstInSync = false;
        second = 0L;
        secondInSync = false;
        operations.clear();
        arguments.clear();
    }

}
