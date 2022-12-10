package org.joml

import java.nio.ByteBuffer
import java.nio.FloatBuffer

/* Operators */
public operator fun Matrix3x2fc.times(m: Matrix3x2fc): Matrix3x2f = mul(m, Matrix3x2f())
public operator fun Matrix3x2fc.times(v: Vector3fc): Vector3f = transform(v, Vector3f())
public operator fun Matrix3x2f.timesAssign(m: Matrix3x2fc) { mul(m) }

/* Buffer Operations */
public fun ByteBuffer.getMatrix3x2f(v: Matrix3x2f): Matrix3x2f = v.set(this)
public fun ByteBuffer.putMatrix3x2f(v: Matrix3x2f): ByteBuffer = v.get(this)
public fun ByteBuffer.putMatrix3x2f(index: Int, v: Matrix3x2f): ByteBuffer = v.get(index, this)
public fun FloatBuffer.getMatrix3x2f(): Matrix3x2f = Matrix3x2f(this)
public fun FloatBuffer.getMatrix3x2f(v: Matrix3x2f): Matrix3x2f = v.set(this)
public fun FloatBuffer.putMatrix3x2f(v: Matrix3x2f): FloatBuffer = v.get(this)
public fun FloatBuffer.putMatrix3x2f(index: Int, v: Matrix3x2f): FloatBuffer = v.get(index, this)
