package org.joml

import java.nio.ByteBuffer
import java.nio.DoubleBuffer

/* Operators */
public operator fun Matrix3x2dc.times(m: Matrix3x2dc): Matrix3x2d = mul(m, Matrix3x2d())
public operator fun Matrix3x2dc.times(v: Vector3dc): Vector3d = transform(v, Vector3d())
public operator fun Matrix3x2d.timesAssign(m: Matrix3x2dc) { mul(m) }

/* Buffer Operations */
public fun ByteBuffer.getMatrix3x2d(v: Matrix3x2d): Matrix3x2d = v.set(this)
public fun ByteBuffer.putMatrix3x2d(v: Matrix3x2d): ByteBuffer = v.get(this)
public fun ByteBuffer.putMatrix3x2d(index: Int, v: Matrix3x2d): ByteBuffer = v.get(index, this)
public fun DoubleBuffer.getMatrix3x2d(): Matrix3x2d = Matrix3x2d(this)
public fun DoubleBuffer.getMatrix3x2d(v: Matrix3x2d): Matrix3x2d = v.set(this)
public fun DoubleBuffer.putMatrix3x2d(v: Matrix3x2d): DoubleBuffer = v.get(this)
public fun DoubleBuffer.putMatrix3x2d(index: Int, v: Matrix3x2d): DoubleBuffer = v.get(index, this)
