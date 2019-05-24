package org.joml

import java.nio.ByteBuffer
import java.nio.DoubleBuffer
import java.nio.FloatBuffer
import java.nio.IntBuffer

/* Vector2f */

/* ByteBuffer */
fun ByteBuffer.getVector2f() = Vector2f(this)
fun ByteBuffer.getVector2f(index: Int) = Vector2f(index, this)
fun ByteBuffer.getVector2f(v: Vector2f) = v.set(this)
fun ByteBuffer.getVector2f(index: Int, v: Vector2f) = v.set(index, this)
fun ByteBuffer.putVector2f(v: Vector2f) = v.get(this)
fun ByteBuffer.putVector2f(index: Int, v: Vector2f) = v.get(index, this)

/* FloatBuffer */
fun FloatBuffer.getVector2f() = Vector2f(this)
fun FloatBuffer.getVector2f(index: Int) = Vector2f(index, this)
fun FloatBuffer.getVector2f(v: Vector2f) = v.set(this)
fun FloatBuffer.getVector2f(index: Int, v: Vector2f) = v.set(index, this)
fun FloatBuffer.putVector2f(v: Vector2f) = v.get(this)
fun FloatBuffer.putVector2f(index: Int, v: Vector2f) = v.get(index, this)

/* Vector2d */

/* ByteBuffer */
fun ByteBuffer.getVector2d() = Vector2d(this)
fun ByteBuffer.getVector2d(index: Int) = Vector2d(index, this)
fun ByteBuffer.getVector2d(v: Vector2d) = v.set(this)
fun ByteBuffer.getVector2d(index: Int, v: Vector2d) = v.set(index, this)
fun ByteBuffer.putVector2d(v: Vector2d) = v.get(this)
fun ByteBuffer.putVector2d(index: Int, v: Vector2d) = v.get(index, this)

/* DoubleBuffer */
fun DoubleBuffer.getVector2d() = Vector2d(this)
fun DoubleBuffer.getVector2d(index: Int) = Vector2d(index, this)
fun DoubleBuffer.getVector2d(v: Vector2d) = v.set(this)
fun DoubleBuffer.getVector2d(index: Int, v: Vector2d) = v.set(index, this)
fun DoubleBuffer.putVector2d(v: Vector2d) = v.get(this)
fun DoubleBuffer.putVector2d(index: Int, v: Vector2d) = v.get(index, this)

/* Vector2i */

/* ByteBuffer */
fun ByteBuffer.getVector2i() = Vector2i(this)
fun ByteBuffer.getVector2i(index: Int) = Vector2i(index, this)
fun ByteBuffer.getVector2i(v: Vector2i) = v.set(this)
fun ByteBuffer.getVector2i(index: Int, v: Vector2i) = v.set(index, this)
fun ByteBuffer.putVector2i(v: Vector2i) = v.get(this)
fun ByteBuffer.putVector2i(index: Int, v: Vector2i) = v.get(index, this)

/* IntBuffer */
fun IntBuffer.getVector2i() = Vector2i(this)
fun IntBuffer.getVector2i(index: Int) = Vector2i(index, this)
fun IntBuffer.getVector2i(v: Vector2i) = v.set(this)
fun IntBuffer.getVector2i(index: Int, v: Vector2i) = v.set(index, this)
fun IntBuffer.putVector2i(v: Vector2i) = v.get(this)
fun IntBuffer.putVector2i(index: Int, v: Vector2i) = v.get(index, this)

/* Vector3f */

/* ByteBuffer */
fun ByteBuffer.getVector3f() = Vector3f(this)
fun ByteBuffer.getVector3f(index: Int) = Vector3f(index, this)
fun ByteBuffer.getVector3f(v: Vector3f) = v.set(this)
fun ByteBuffer.getVector3f(index: Int, v: Vector3f) = v.set(index, this)
fun ByteBuffer.putVector3f(v: Vector3f) = v.get(this)
fun ByteBuffer.putVector3f(index: Int, v: Vector3f) = v.get(index, this)

/* FloatBuffer */
fun FloatBuffer.getVector3f() = Vector3f(this)
fun FloatBuffer.getVector3f(index: Int) = Vector3f(index, this)
fun FloatBuffer.getVector3f(v: Vector3f) = v.set(this)
fun FloatBuffer.getVector3f(index: Int, v: Vector3f) = v.set(index, this)
fun FloatBuffer.putVector3f(v: Vector3f) = v.get(this)
fun FloatBuffer.putVector3f(index: Int, v: Vector3f) = v.get(index, this)

/* Vector3d */

/* ByteBuffer */
fun ByteBuffer.getVector3d() = Vector3d(this)
fun ByteBuffer.getVector3d(index: Int) = Vector3d(index, this)
fun ByteBuffer.getVector3d(v: Vector3d) = v.set(this)
fun ByteBuffer.getVector3d(index: Int, v: Vector3d) = v.set(index, this)
fun ByteBuffer.putVector3d(v: Vector3d) = v.get(this)
fun ByteBuffer.putVector3d(index: Int, v: Vector3d) = v.get(index, this)

/* DoubleBuffer */
fun DoubleBuffer.getVector3d() = Vector3d(this)
fun DoubleBuffer.getVector3d(index: Int) = Vector3d(index, this)
fun DoubleBuffer.getVector3d(v: Vector3d) = v.set(this)
fun DoubleBuffer.getVector3d(index: Int, v: Vector3d) = v.set(index, this)
fun DoubleBuffer.putVector3d(v: Vector3d) = v.get(this)
fun DoubleBuffer.putVector3d(index: Int, v: Vector3d) = v.get(index, this)

/* Vector3i */

/* ByteBuffer */
fun ByteBuffer.getVector3i() = Vector3i(this)
fun ByteBuffer.getVector3i(index: Int) = Vector3i(index, this)
fun ByteBuffer.getVector3i(v: Vector3i) = v.set(this)
fun ByteBuffer.getVector3i(index: Int, v: Vector3i) = v.set(index, this)
fun ByteBuffer.putVector3i(v: Vector3i) = v.get(this)
fun ByteBuffer.putVector3i(index: Int, v: Vector3i) = v.get(index, this)

/* IntBuffer */
fun IntBuffer.getVector3i() = Vector3i(this)
fun IntBuffer.getVector3i(index: Int) = Vector3i(index, this)
fun IntBuffer.getVector3i(v: Vector3i) = v.set(this)
fun IntBuffer.getVector3i(index: Int, v: Vector3i) = v.set(index, this)
fun IntBuffer.putVector3i(v: Vector3i) = v.get(this)
fun IntBuffer.putVector3i(index: Int, v: Vector3i) = v.get(index, this)

/* Vector4f */

/* ByteBuffer */
fun ByteBuffer.getVector4f() = Vector4f(this)
fun ByteBuffer.getVector4f(index: Int) = Vector4f(index, this)
fun ByteBuffer.getVector4f(v: Vector4f) = v.set(this)
fun ByteBuffer.getVector4f(index: Int, v: Vector4f) = v.set(index, this)
fun ByteBuffer.putVector4f(v: Vector4f) = v.get(this)
fun ByteBuffer.putVector4f(index: Int, v: Vector4f) = v.get(index, this)

/* FloatBuffer */
fun FloatBuffer.getVector4f() = Vector4f(this)
fun FloatBuffer.getVector4f(index: Int) = Vector4f(index, this)
fun FloatBuffer.getVector4f(v: Vector4f) = v.set(this)
fun FloatBuffer.getVector4f(index: Int, v: Vector4f) = v.set(index, this)
fun FloatBuffer.putVector4f(v: Vector4f) = v.get(this)
fun FloatBuffer.putVector4f(index: Int, v: Vector4f) = v.get(index, this)

/* Vector4d */

/* ByteBuffer */
fun ByteBuffer.getVector4d() = Vector4d(this)
fun ByteBuffer.getVector4d(index: Int) = Vector4d(index, this)
fun ByteBuffer.getVector4d(v: Vector4d) = v.set(this)
fun ByteBuffer.getVector4d(index: Int, v: Vector4d) = v.set(index, this)
fun ByteBuffer.putVector4d(v: Vector4d) = v.get(this)
fun ByteBuffer.putVector4d(index: Int, v: Vector4d) = v.get(index, this)

/* DoubleBuffer */
fun DoubleBuffer.getVector4d() = Vector4d(this)
fun DoubleBuffer.getVector4d(index: Int) = Vector4d(index, this)
fun DoubleBuffer.getVector4d(v: Vector4d) = v.set(this)
fun DoubleBuffer.getVector4d(index: Int, v: Vector4d) = v.set(index, this)
fun DoubleBuffer.putVector4d(v: Vector4d) = v.get(this)
fun DoubleBuffer.putVector4d(index: Int, v: Vector4d) = v.get(index, this)

/* Vector4i */

/* ByteBuffer */
fun ByteBuffer.getVector4i() = Vector4i(this)
fun ByteBuffer.getVector4i(index: Int) = Vector4i(index, this)
fun ByteBuffer.getVector4i(v: Vector4i) = v.set(this)
fun ByteBuffer.getVector4i(index: Int, v: Vector4i) = v.set(index, this)
fun ByteBuffer.putVector4i(v: Vector4i) = v.get(this)
fun ByteBuffer.putVector4i(index: Int, v: Vector4i) = v.get(index, this)

/* IntBuffer */
fun IntBuffer.getVector4i() = Vector4i(this)
fun IntBuffer.getVector4i(index: Int) = Vector4i(index, this)
fun IntBuffer.getVector4i(v: Vector4i) = v.set(this)
fun IntBuffer.getVector4i(index: Int, v: Vector4i) = v.set(index, this)
fun IntBuffer.putVector4i(v: Vector4i) = v.get(this)
fun IntBuffer.putVector4i(index: Int, v: Vector4i) = v.get(index, this)

/* Matrix2f */

/* ByteBuffer */
fun ByteBuffer.getMatrix2f(v: Matrix2f) = v.set(this)
fun ByteBuffer.putMatrix2f(v: Matrix2f) = v.get(this)
fun ByteBuffer.putMatrix2f(index: Int, v: Matrix2f) = v.get(index, this)

/* FloatBuffer */
fun FloatBuffer.getMatrix2f() = Matrix2f(this)
fun FloatBuffer.getMatrix2f(v: Matrix2f) = v.set(this)
fun FloatBuffer.putMatrix2f(v: Matrix2f) = v.get(this)
fun FloatBuffer.putMatrix2f(index: Int, v: Matrix2f) = v.get(index, this)

/* Matrix2d */

/* ByteBuffer */
fun ByteBuffer.getMatrix2d(v: Matrix2d) = v.set(this)
fun ByteBuffer.putMatrix2d(v: Matrix2d) = v.get(this)
fun ByteBuffer.putMatrix2d(index: Int, v: Matrix2d) = v.get(index, this)

/* FloatBuffer */
fun DoubleBuffer.getMatrix2d() = Matrix2d(this)
fun DoubleBuffer.getMatrix2d(v: Matrix2d) = v.set(this)
fun DoubleBuffer.putMatrix2d(v: Matrix2d) = v.get(this)
fun DoubleBuffer.putMatrix2d(index: Int, v: Matrix2d) = v.get(index, this)

/* Matrix3f */

/* ByteBuffer */
fun ByteBuffer.getMatrix3f(v: Matrix3f) = v.set(this)
fun ByteBuffer.putMatrix3f(v: Matrix3f) = v.get(this)
fun ByteBuffer.putMatrix3f(index: Int, v: Matrix3f) = v.get(index, this)

/* FloatBuffer */
fun FloatBuffer.getMatrix3f() = Matrix3f(this)
fun FloatBuffer.getMatrix3f(v: Matrix3f) = v.set(this)
fun FloatBuffer.putMatrix3f(v: Matrix3f) = v.get(this)
fun FloatBuffer.putMatrix3f(index: Int, v: Matrix3f) = v.get(index, this)

/* Matrix3d */

/* ByteBuffer */
fun ByteBuffer.getMatrix3d(v: Matrix3d) = v.set(this)
fun ByteBuffer.putMatrix3d(v: Matrix3d) = v.get(this)
fun ByteBuffer.putMatrix3d(index: Int, v: Matrix3d) = v.get(index, this)

/* DoubleBuffer */
fun DoubleBuffer.getMatrix3d() = Matrix3d(this)
fun DoubleBuffer.getMatrix3d(v: Matrix3d) = v.set(this)
fun DoubleBuffer.putMatrix3d(v: Matrix3d) = v.get(this)
fun DoubleBuffer.putMatrix3d(index: Int, v: Matrix3d) = v.get(index, this)

/* Matrix4f */

/* ByteBuffer */
fun ByteBuffer.getMatrix4f(v: Matrix4f) = v.set(this)
fun ByteBuffer.putMatrix4f(v: Matrix4f) = v.get(this)
fun ByteBuffer.putMatrix4f(index: Int, v: Matrix4f) = v.get(index, this)

/* FloatBuffer */
fun FloatBuffer.getMatrix4f() = Matrix4f(this)
fun FloatBuffer.getMatrix4f(v: Matrix4f) = v.set(this)
fun FloatBuffer.putMatrix4f(v: Matrix4f) = v.get(this)
fun FloatBuffer.putMatrix4f(index: Int, v: Matrix4f) = v.get(index, this)

/* Matrix4d */

/* ByteBuffer */
fun ByteBuffer.getMatrix4d(v: Matrix4d) = v.set(this)
fun ByteBuffer.putMatrix4d(v: Matrix4d) = v.get(this)
fun ByteBuffer.putMatrix4d(index: Int, v: Matrix4d) = v.get(index, this)

/* DoubleBuffer */
fun DoubleBuffer.getMatrix4d() = Matrix4d(this)
fun DoubleBuffer.getMatrix4d(v: Matrix4d) = v.set(this)
fun DoubleBuffer.putMatrix4d(v: Matrix4d) = v.get(this)
fun DoubleBuffer.putMatrix4d(index: Int, v: Matrix4d) = v.get(index, this)

/* Matrix3x2f */

/* ByteBuffer */
fun ByteBuffer.getMatrix3x2f(v: Matrix3x2f) = v.set(this)
fun ByteBuffer.putMatrix3x2f(v: Matrix3x2f) = v.get(this)
fun ByteBuffer.putMatrix3x2f(index: Int, v: Matrix3x2f) = v.get(index, this)

/* FloatBuffer */
fun FloatBuffer.getMatrix3x2f() = Matrix3x2f(this)
fun FloatBuffer.getMatrix3x2f(v: Matrix3x2f) = v.set(this)
fun FloatBuffer.putMatrix3x2f(v: Matrix3x2f) = v.get(this)
fun FloatBuffer.putMatrix3x2f(index: Int, v: Matrix3x2f) = v.get(index, this)

/* Matrix3x2d */

/* ByteBuffer */
fun ByteBuffer.getMatrix3x2d(v: Matrix3x2d) = v.set(this)
fun ByteBuffer.putMatrix3x2d(v: Matrix3x2d) = v.get(this)
fun ByteBuffer.putMatrix3x2d(index: Int, v: Matrix3x2d) = v.get(index, this)

/* DoubleBuffer */
fun DoubleBuffer.getMatrix3x2d() = Matrix3x2d(this)
fun DoubleBuffer.getMatrix3x2d(v: Matrix3x2d) = v.set(this)
fun DoubleBuffer.putMatrix3x2d(v: Matrix3x2d) = v.get(this)
fun DoubleBuffer.putMatrix3x2d(index: Int, v: Matrix3x2d) = v.get(index, this)

/* Matrix4x3f */

/* ByteBuffer */
fun ByteBuffer.getMatrix4x3f(v: Matrix4x3f) = v.set(this)
fun ByteBuffer.putMatrix4x3f(v: Matrix4x3f) = v.get(this)
fun ByteBuffer.putMatrix4x3f(index: Int, v: Matrix4x3f) = v.get(index, this)

/* FloatBuffer */
fun FloatBuffer.getMatrix4x3f() = Matrix4x3f(this)
fun FloatBuffer.getMatrix4x3f(v: Matrix4x3f) = v.set(this)
fun FloatBuffer.putMatrix4x3f(v: Matrix4x3f) = v.get(this)
fun FloatBuffer.putMatrix4x3f(index: Int, v: Matrix4x3f) = v.get(index, this)

/* Matrix4x3d */

/* ByteBuffer */
fun ByteBuffer.getMatrix4x3d(v: Matrix4x3d) = v.set(this)
fun ByteBuffer.putMatrix4x3d(v: Matrix4x3d) = v.get(this)
fun ByteBuffer.putMatrix4x3d(index: Int, v: Matrix4x3d) = v.get(index, this)

/* DoubleBuffer */
fun DoubleBuffer.getMatrix4x3d() = Matrix4x3d(this)
fun DoubleBuffer.getMatrix4x3d(v: Matrix4x3d) = v.set(this)
fun DoubleBuffer.putMatrix4x3d(v: Matrix4x3d) = v.get(this)
fun DoubleBuffer.putMatrix4x3d(index: Int, v: Matrix4x3d) = v.get(index, this)
