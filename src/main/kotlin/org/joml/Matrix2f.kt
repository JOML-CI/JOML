@file:Suppress("NOTHING_TO_INLINE")
@file:JvmSynthetic
package org.joml

import java.nio.ByteBuffer
import java.nio.FloatBuffer

/* Operators */
public inline operator fun Matrix2fc.plus(m: Matrix2fc): Matrix2f = add(m, Matrix2f())
public inline operator fun Matrix2f.plusAssign(m: Matrix2fc) { add(m) }
public inline operator fun Matrix2fc.minus(m: Matrix2fc): Matrix2f = sub(m, Matrix2f())
public inline operator fun Matrix2f.minusAssign(m: Matrix2fc) { sub(m) }
public inline operator fun Matrix2fc.times(m: Matrix2fc): Matrix2f = mul(m, Matrix2f())
public inline operator fun Matrix2fc.times(v: Vector2fc): Vector2f = transform(v, Vector2f())
public inline operator fun Matrix2f.timesAssign(m: Matrix2fc) { mul(m) }
public inline infix fun Matrix2fc.mulComponentWise(m: Matrix2fc): Matrix2f = mulComponentWise(m, Matrix2f())

/* Buffer Operations */
public inline fun ByteBuffer.getMatrix2f(v: Matrix2f): Matrix2f = v.set(this)
public inline fun ByteBuffer.putMatrix2f(v: Matrix2f): ByteBuffer = v.get(this)
public inline fun ByteBuffer.putMatrix2f(index: Int, v: Matrix2f): ByteBuffer = v.get(index, this)
public inline fun FloatBuffer.getMatrix2f(): Matrix2f = Matrix2f(this)
public inline fun FloatBuffer.getMatrix2f(v: Matrix2f): Matrix2f = v.set(this)
public inline fun FloatBuffer.putMatrix2f(v: Matrix2f): FloatBuffer = v.get(this)
public inline fun FloatBuffer.putMatrix2f(index: Int, v: Matrix2f): FloatBuffer = v.get(index, this)
