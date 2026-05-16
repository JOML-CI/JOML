package org.joml.test;

import org.joml.*;
import org.joml.Math;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class MemUtilTest {
    // Load the inner static class based on the path.
    private static Class<?> memUtilNIOInnerClass;

    // Creating an instance of the inner class.
    private static Object instance;

    @BeforeAll
    static void setup() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        // Load the inner static class based on the path.
        memUtilNIOInnerClass = Class.forName("org.joml.MemUtil$MemUtilNIO");

        // Creating an instance of the inner class.
        instance = memUtilNIOInnerClass.newInstance();
    }

    private static Stream<Arguments> provideAllCombinationsOf3x3() {
        final int[] matrixColumnIndexes = {0, 1, 2, 3};
        final int[] matrixRowIndexes = {0, 1, 2, 3};

        return provideAllCombinations(matrixColumnIndexes, matrixRowIndexes);
    }

    private static Stream<Arguments> provideAllCombinationsOf4x4() {
        final int[] matrixColumnIndexes = {0, 1, 2, 3, 4};
        final int[] matrixRowIndexes = {0, 1, 2, 3, 4};

        return provideAllCombinations(matrixColumnIndexes, matrixRowIndexes);
    }

    private static Stream<Arguments> provideAllCombinations(int[] source1, int[] source2) {
        final List<Arguments> arguments = new ArrayList<>();

        for (int value1 : source1) {
            for (int value2 : source2) {
                arguments.add(Arguments.of(value1, value2));
            }
        }

        return arguments.stream();
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4})
    void testMemUtilSetColumnWithColumnIndexesAndVector4fc(int columnIndex) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // Creating a reference to the "put" method inside the "MemUtilNIO"
        final Method method = memUtilNIOInnerClass.getDeclaredMethod("setColumn", Vector4fc.class, int.class, Matrix4f.class);

        // Setting up variables to use inside the method.
        final float[] matrixCells = new float[] {
                1, 2, 3, 4,
                5, 6, 7, 8,
                9, 10, 11, 12,
                13, 14, 15, 16};
        final Matrix4f matrix = new Matrix4f(matrixCells);
        final float[] resultVectorContent = new float[] {0, 0, 0, 0};
        final Vector4fc nullVector = new Vector4f(resultVectorContent);

        if (columnIndex < Math.sqrt(matrixCells.length)) {
            final Matrix4f resultMatrix = (Matrix4f) method.invoke(instance, nullVector, columnIndex, matrix);
            assertEquals(nullVector, resultMatrix.getColumn(columnIndex, new Vector4f()));
        } else {
            assertThrows(InvocationTargetException.class, () -> method.invoke(instance, nullVector, columnIndex, matrix));
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4})
    void testMemUtilSetColumnWithColumnIndexes(int columnIndex) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        final Method method = memUtilNIOInnerClass.getDeclaredMethod("setColumn", Vector4f.class, int.class, Matrix4f.class);

        // Setting up variables to use inside the method.
        final float[] matrixCells = new float[] {
                1, 2, 3, 4,
                5, 6, 7, 8,
                9, 10, 11, 12,
                13, 14, 15, 16};
        final Matrix4f matrix = new Matrix4f(matrixCells);
        final float[] resultVectorContent = new float[] {0, 0, 0, 0};
        final Vector4f nullVector = new Vector4f(resultVectorContent);

        if (columnIndex < Math.sqrt(matrixCells.length)) {
            final Matrix4f resultMatrix = (Matrix4f) method.invoke(instance, nullVector, columnIndex, matrix);
            assertEquals(nullVector, resultMatrix.getColumn(columnIndex, new Vector4f()));
        } else {
            assertThrows(InvocationTargetException.class, () -> method.invoke(instance, nullVector, columnIndex, matrix));
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4})
    void testMemUtilGetColumnWithColumnIndexes(int columnIndex) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        final Method method = memUtilNIOInnerClass.getDeclaredMethod("getColumn", Matrix4f.class, int.class, Vector4f.class);

        // Setting up variables to use inside the method.
        final float[] matrixCells = new float[] {
                1, 2, 3, 4,
                5, 6, 7, 8,
                9, 10, 11, 12,
                13, 14, 15, 16};
        final Matrix4f matrix = new Matrix4f(matrixCells);
        final Vector4f destinationVector1 = new Vector4f();
        final Vector4f destinationVector2 = new Vector4f();

        if (columnIndex < Math.sqrt(matrixCells.length)) {
            final Vector4f vector4f = (Vector4f) method.invoke(instance, matrix, columnIndex, destinationVector1);
            assertEquals(vector4f, matrix.getColumn(columnIndex, destinationVector2));
        } else {
            assertThrows(InvocationTargetException.class, () -> method.invoke(instance, matrix, columnIndex, destinationVector1));
        }
    }

    @ParameterizedTest
    @MethodSource("provideAllCombinationsOf3x3")
    void testMemUtilSetWithMatrix3fAndColumnAndRowIndexes(int columnIndex, int rowIndex) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        final Method method = memUtilNIOInnerClass.getDeclaredMethod("set", Matrix3f.class, int.class, int.class, float.class);

        // Setting up variables to use inside the method.
        final float[] matrixContent = new float[] {
                1, 2, 3,
                4, 5, 6,
                7, 8, 9};
        final Matrix3f matrix = new Matrix3f(matrixContent);
        final float valueToSet = 10.f;

        if (columnIndex < 3 && rowIndex < 3) {
            final Matrix3f modifiedMatrix = (Matrix3f) method.invoke(instance, matrix, columnIndex, rowIndex, valueToSet);
            assertEquals(valueToSet, modifiedMatrix.getRowColumn(rowIndex, columnIndex));
        } else {
            assertThrows(InvocationTargetException.class, () -> method.invoke(instance, matrix, columnIndex, rowIndex, valueToSet));
        }
    }

    @ParameterizedTest
    @MethodSource("provideAllCombinationsOf3x3")
    void testMemUtilSetWithMatrix3dAndColumnAndRowIndexes(int columnIndex, int rowIndex) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        final Method method = memUtilNIOInnerClass.getDeclaredMethod("set", Matrix3d.class, int.class, int.class, double.class);

        // Setting up variables to use inside the method.
        final double[] matrixContent = new double[] {
                1, 2, 3,
                4, 5, 6,
                7, 8, 9};
        final Matrix3d matrix = new Matrix3d(matrixContent);
        final double valueToSet = 10.0;

        if (columnIndex < 3 && rowIndex < 3) {
            final Matrix3d modifiedMatrix = (Matrix3d) method.invoke(instance, matrix, columnIndex, rowIndex, valueToSet);
            assertEquals(valueToSet, modifiedMatrix.getRowColumn(rowIndex, columnIndex));
        } else {
            assertThrows(InvocationTargetException.class, () -> method.invoke(instance, matrix, columnIndex, rowIndex, valueToSet));
        }
    }

    @ParameterizedTest
    @MethodSource("provideAllCombinationsOf3x3")
    void testMemUtilGetWithMatrix3dAndColumnAndRowIndexes(int columnIndex, int rowIndex) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        final Method method = memUtilNIOInnerClass.getDeclaredMethod("get", Matrix3d.class, int.class, int.class);

        // Setting up variables to use inside the method.
        final double[] matrixContent = new double[] {
                1, 2, 3,
                4, 5, 6,
                7, 8, 9};
        final Matrix3d matrix = new Matrix3d(matrixContent);

        if (columnIndex < 3 && rowIndex < 3) {
            final double matrixCell = (double) method.invoke(instance, matrix, columnIndex, rowIndex);
            assertEquals(matrixContent[columnIndex*3 + rowIndex], matrixCell);
        } else {
            assertThrows(InvocationTargetException.class, () -> method.invoke(instance, matrix, columnIndex, rowIndex));
        }
    }

    @ParameterizedTest
    @MethodSource("provideAllCombinationsOf4x4")
    void testMemUtilGetWithMatrix3fAndColumnAndRowIndexes(int columnIndex, int rowIndex) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        final Method method = memUtilNIOInnerClass.getDeclaredMethod("get", Matrix3f.class, int.class, int.class);

        // Setting up variables to use inside the method.
        final float[] matrixContent = new float[] {
                1, 2, 3,
                4, 5, 6,
                7, 8, 9};
        final Matrix3f matrix = new Matrix3f(matrixContent);

        if (columnIndex < 3 && rowIndex < 3) {
            final float matrixCell = (float) method.invoke(instance, matrix, columnIndex, rowIndex);
            assertEquals(matrixContent[columnIndex*3 + rowIndex], matrixCell);
        } else {
            assertThrows(InvocationTargetException.class, () -> method.invoke(instance, matrix, columnIndex, rowIndex));
        }
    }

    @ParameterizedTest
    @MethodSource("provideAllCombinationsOf4x4")
    void testMemUtilGetWithMatrix4dAndColumnAndRowIndexes(int columnIndex, int rowIndex) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        final Method method = memUtilNIOInnerClass.getDeclaredMethod("get", Matrix4d.class, int.class, int.class);

        // Setting up variables to use inside the method.
        final double[] matrixContent = new double[] {
                1, 2, 3, 4,
                5, 6, 7, 8,
                9, 10, 11, 12,
                13, 14, 15, 16};
        final Matrix4d matrix = new Matrix4d(matrixContent);

        if (columnIndex < 4 && rowIndex < 4) {
            final double matrixCell = (double) method.invoke(instance, matrix, columnIndex, rowIndex);
            assertEquals(matrixContent[columnIndex*4 + rowIndex], matrixCell);
        } else {
            assertThrows(InvocationTargetException.class, () -> method.invoke(instance, matrix, columnIndex, rowIndex));
        }
    }

    @Test
    void testMemUtilSetWithMatrix4fAndColumnAndRowIndexesAndDoubleValue() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        final Method method = memUtilNIOInnerClass.getDeclaredMethod("set", Matrix4d.class, int.class, int.class, double.class);

        // Setting up variables to use inside the method.
        final Matrix4d matrix = new Matrix4d(new float[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16});

        for (int column=0; column<5; column++) {
            for (int row=0; row<5; row++) {
                if (column < 4 && row < 4) {
                    final Matrix4d modifiedMatrix = (Matrix4d) method.invoke(instance, matrix, column, row, 0);
                    assertEquals(0, modifiedMatrix.getRowColumn(row, column));
                } else {
                    int columnTmp = column;
                    int rowTmp = row;
                    assertThrows(InvocationTargetException.class, () -> method.invoke(instance, matrix, columnTmp, rowTmp, 0));
                    break;
                }
            }
        }
    }

    @Test
    void testMemUtilSetWithMatrix4fAndColumnAndRowIndexesAndFloatValue() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        final Method method = memUtilNIOInnerClass.getDeclaredMethod("set", Matrix4f.class, int.class, int.class, float.class);

        // Setting up variables to use inside the method.
        final Matrix4f matrix = new Matrix4f(new float[] {
                1, 2, 3, 4,
                5, 6, 7, 8,
                9, 10, 11, 12,
                13, 14, 15, 16});

        for (int column=0; column<5; column++) {
            for (int row=0; row<5; row++) {
                if (column < 4 && row < 4) {
                    final Matrix4f modifiedMatrix = (Matrix4f) method.invoke(instance, matrix, column, row, 0);
                    assertEquals(0, modifiedMatrix.getRowColumn(row, column));
                } else {
                    int columnTmp = column;
                    int rowTmp = row;
                    assertThrows(InvocationTargetException.class, () -> method.invoke(instance, matrix, columnTmp, rowTmp, 0));
                    break;
                }
            }
        }
    }

    @Test
    void testMemUtilGetWithMatrix4fAndColumnAndRowIndexes() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        final Method method = memUtilNIOInnerClass.getDeclaredMethod("get", Matrix4f.class, int.class, int.class);

        // Setting up variables to use inside the method.
        final float[] matrixContent = new float[] {
                1, 2, 3, 4,
                5, 6, 7, 8,
                9, 10, 11, 12,
                13, 14, 15, 16};
        final Matrix4f matrix = new Matrix4f(matrixContent);

        for (int column=0; column<5; column++) {
            for (int row=0; row<5; row++) {
                if (column < 4 && row < 4) {
                    final float matrixCell = (float) method.invoke(instance, matrix, column, row);
                    assertEquals(matrixContent[column*4 + row], matrixCell);
                } else {
                    int columnTmp = column;
                    int rowTmp = row;
                    assertThrows(InvocationTargetException.class, () -> method.invoke(instance, matrix, columnTmp, rowTmp));
                    break;
                }
            }
        }
    }

    @Test
    void testMemUtilPut3x4WithMatrix3fAndByteBufferAndOffset() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        final Method method = memUtilNIOInnerClass.getDeclaredMethod("put3x4", Matrix3f.class, int.class, ByteBuffer.class);

        // Setting up variables to use inside the method.
        final int offset = 4;
        final int bufferSize = 48;
        final ByteBuffer buffer1 = ByteBuffer.wrap(new byte[bufferSize]);
        final ByteBuffer buffer2 = ByteBuffer.wrap(new byte[bufferSize + offset*4]);
        final Matrix3f matrix = new Matrix3f(new float[] {
                1, 2, 3,
                4, 5, 6,
                7, 8, 9});

        // Calling the method.
        method.invoke(instance, matrix, 0, buffer1);
        method.invoke(instance, matrix, offset, buffer2);

        // Test
        assertEquals(ByteBuffer.wrap(new byte[] {
                63, -128, 0, 0, 64, 0, 0, 0, 64, 64, 0, 0, 0, 0, 0, 0, 64, -128, 0, 0, 64, -96, 0, 0, 64, -64, 0, 0, 0, 0, 0, 0, 64, -32, 0, 0, 65, 0, 0, 0, 65, 16, 0, 0, 0, 0, 0, 0
        }), buffer1);
        assertEquals(ByteBuffer.wrap(new byte[] {
                0, 0, 0, 0, 63, -128, 0, 0, 64, 0, 0, 0, 64, 64, 0, 0, 0, 0, 0, 0, 64, -128, 0, 0, 64, -96, 0, 0, 64, -64, 0, 0, 0, 0, 0, 0, 64, -32, 0, 0, 65, 0, 0, 0, 65, 16, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
        }), buffer2);
    }

    @Test
    void testMemUtilPut3x4WithMatrix3fAndFloatBufferAndOffset() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        final Method method = memUtilNIOInnerClass.getDeclaredMethod("put3x4", Matrix3f.class, int.class, FloatBuffer.class);

        // Setting up variables to use inside the method.
        final int offset = 4;
        final int bufferSize = 12;
        final FloatBuffer buffer1 = FloatBuffer.wrap(new float[bufferSize]);
        final FloatBuffer buffer2 = FloatBuffer.wrap(new float[bufferSize + offset]);
        final Matrix3f matrix = new Matrix3f(new float[] {
                1, 2, 3,
                4, 5, 6,
                7, 8, 9});

        // Calling the method.
        method.invoke(instance, matrix, 0, buffer1);
        method.invoke(instance, matrix, offset, buffer2);

        // Test
        assertEquals(FloatBuffer.wrap(new float[] {
                1, 2, 3, 0,
                4, 5, 6, 0,
                7, 8, 9, 0,
        }), buffer1);
        assertEquals(FloatBuffer.wrap(new float[] {
                0, 0, 0, 0,
                1, 2, 3, 0,
                4, 5, 6, 0,
                7, 8, 9, 0,
        }), buffer2);
    }

    @Test
    void testMemUtilPutWithMatrix3fAndByteBufferAndOffset() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // Creating a reference to the "put" method inside the "MemUtilNIO"
        final Method method = memUtilNIOInnerClass.getDeclaredMethod("put", Matrix3f.class, int.class, ByteBuffer.class);

        // Setting up variables to use inside the method.
        final int offset = 3;
        final int bufferSize = 36;
        final ByteBuffer buffer1 = ByteBuffer.wrap(new byte[bufferSize]);
        final ByteBuffer buffer2 = ByteBuffer.wrap(new byte[bufferSize + offset*4]);
        final Matrix3f matrix = new Matrix3f(new float[] {
                1, 2, 3,
                4, 5, 6,
                7, 8, 9});

        // Calling the method.
        method.invoke(instance, matrix, 0, buffer1);
        method.invoke(instance, matrix, offset, buffer2);

        // Test
        assertEquals(ByteBuffer.wrap(new byte[] {
                63, -128, 0, 0, 64, 0, 0, 0, 64, 64, 0, 0, 64, -128, 0, 0, 64, -96, 0, 0, 64, -64, 0, 0, 64, -32, 0, 0, 65, 0, 0, 0, 65, 16, 0, 0
        }), buffer1);
        assertEquals(ByteBuffer.wrap(new byte[] {
                0, 0, 0, 63, -128, 0, 0, 64, 0, 0, 0, 64, 64, 0, 0, 64, -128, 0, 0, 64, -96, 0, 0, 64, -64, 0, 0, 64, -32, 0, 0, 65, 0, 0, 0, 65, 16, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
        }), buffer2);
    }

    @Test
    void testMemUtilPutWithMatrix3fAndFloatBufferAndOffset() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // Creating a reference to the "put" method inside the "MemUtilNIO"
        final Method method = memUtilNIOInnerClass.getDeclaredMethod("put", Matrix3f.class, int.class, FloatBuffer.class);

        // Setting up variables to use inside the method.
        final int offset = 3;
        final int bufferSize = 9;
        final FloatBuffer buffer1 = FloatBuffer.wrap(new float[bufferSize]);
        final FloatBuffer buffer2 = FloatBuffer.wrap(new float[bufferSize + offset]);
        final Matrix3f matrix = new Matrix3f(new float[] {
                1, 2, 3,
                4, 5, 6,
                7, 8, 9});

        // Calling the method.
        method.invoke(instance, matrix, 0, buffer1);
        method.invoke(instance, matrix, offset, buffer2);

        // Test
        assertEquals(FloatBuffer.wrap(new float[] {
                1, 2, 3,
                4, 5, 6,
                7, 8, 9
        }), buffer1);
        assertEquals(FloatBuffer.wrap(new float[] {
                0, 0, 0,
                1, 2, 3,
                4, 5, 6,
                7, 8, 9
        }), buffer2);
    }

    @Test
    void testMemUtilPutWithMatrix4fAndByteBufferAndOffset() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // Creating a reference to the "put" method inside the "MemUtilNIO"
        final Method method = memUtilNIOInnerClass.getDeclaredMethod("put", Matrix4f.class, int.class, ByteBuffer.class);

        // Setting up variables to use inside the method.
        final ByteBuffer buffer1 = ByteBuffer.wrap(new byte[64]);
        final ByteBuffer buffer2 = ByteBuffer.wrap(new byte[80]);
        final Matrix4f matrix4f = new Matrix4f(new float[] {
                1, 2, 3, 4,
                5, 6, 7, 8,
                9, 10, 11, 12,
                13, 14, 15, 16});

        // Calling the method.
        method.invoke(instance, matrix4f, 0, buffer1);
        method.invoke(instance, matrix4f, 4, buffer2);

        // Test
        assertEquals(ByteBuffer.wrap(new byte[] {
                63, -128, 0, 0, 64, 0, 0, 0, 64, 64, 0, 0, 64, -128, 0, 0, 64, -96, 0, 0, 64, -64, 0, 0, 64, -32, 0, 0, 65, 0, 0, 0, 65, 16, 0, 0, 65, 32, 0, 0, 65, 48, 0, 0, 65, 64, 0, 0, 65, 80, 0, 0, 65, 96, 0, 0, 65, 112, 0, 0, 65, -128, 0, 0
        }), buffer1);
        assertEquals(ByteBuffer.wrap(new byte[] {
                0, 0, 0, 0, 63, -128, 0, 0, 64, 0, 0, 0, 64, 64, 0, 0, 64, -128, 0, 0, 64, -96, 0, 0, 64, -64, 0, 0, 64, -32, 0, 0, 65, 0, 0, 0, 65, 16, 0, 0, 65, 32, 0, 0, 65, 48, 0, 0, 65, 64, 0, 0, 65, 80, 0, 0, 65, 96, 0, 0, 65, 112, 0, 0, 65, -128, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
        }), buffer2);
    }

    @Test
    void testMemUtilPutWithMatrix4fAndFloatBufferAndOffset() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // Creating a reference to the "put" method inside the "MemUtilNIO"
        final Method method = memUtilNIOInnerClass.getDeclaredMethod("put", Matrix4f.class, int.class, FloatBuffer.class);

        // Setting up variables to use inside the method.
        final FloatBuffer buffer1 = FloatBuffer.wrap(new float[16]);
        final FloatBuffer buffer2 = FloatBuffer.wrap(new float[20]);
        final Matrix4f matrix4f = new Matrix4f(new float[] {
                1, 2, 3, 4,
                5, 6, 7, 8,
                9, 10, 11, 12,
                13, 14, 15, 16});

        // Calling the method.
        method.invoke(instance, matrix4f, 0, buffer1);
        method.invoke(instance, matrix4f, 4, buffer2);

        // Test
        assertEquals(FloatBuffer.wrap(new float[] {
                1, 2, 3, 4,
                5, 6, 7, 8,
                9, 10, 11, 12,
                13, 14, 15, 16
        }), buffer1);
        assertEquals(FloatBuffer.wrap(new float[] {
                0, 0, 0, 0,
                1, 2, 3, 4,
                5, 6, 7, 8,
                9, 10, 11, 12,
                13, 14, 15, 16
        }), buffer2);
    }
}
