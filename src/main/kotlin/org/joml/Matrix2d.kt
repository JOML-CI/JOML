package org.joml

import java.nio.ByteBuffer
import java.nio.DoubleBuffer

/* Operators */
public operator fun Matrix2dc.plus(m: Matrix2dc): Matrix2d = add(m, Matrix2d())
public operator fun Matrix2d.plusAssign(m: Matrix2dc) { add(m) }
public operator fun Matrix2dc.minus(m: Matrix2dc): Matrix2d = sub(m, Matrix2d())
public operator fun Matrix2d.minusAssign(m: Matrix2dc) { sub(m) }
public operator fun Matrix2dc.times(m: Matrix2dc): Matrix2d = mul(m, Matrix2d())
public operator fun Matrix2dc.times(m: Matrix2fc): Matrix2d = mul(m, Matrix2d())
public operator fun Matrix2dc.times(v: Vector2dc): Vector2d = transform(v, Vector2d())
public operator fun Matrix2d.timesAssign(m: Matrix2dc) { mul(m) }
public operator fun Matrix2d.timesAssign(m: Matrix2fc) { mul(m) }
public infix fun Matrix2dc.mulComponentWise(m: Matrix2dc): Matrix2d = mulComponentWise(m, Matrix2d())

/* Buffer Operations */
public fun ByteBuffer.getMatrix2d(v: Matrix2d): Matrix2d = v.set(this)
public fun ByteBuffer.putMatrix2d(v: Matrix2d): ByteBuffer = v.get(this)
public fun ByteBuffer.putMatrix2d(index: Int, v: Matrix2d): ByteBuffer = v.get(index, this)
public fun DoubleBuffer.getMatrix2d(): Matrix2d = Matrix2d(this)
public fun DoubleBuffer.getMatrix2d(v: Matrix2d): Matrix2d = v.set(this)
public fun DoubleBuffer.putMatrix2d(v: Matrix2d): DoubleBuffer = v.get(this)
public fun DoubleBuffer.putMatrix2d(index: Int, v: Matrix2d): DoubleBuffer = v.get(index, this)
