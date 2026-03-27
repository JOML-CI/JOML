@file:Suppress("NOTHING_TO_INLINE")
@file:JvmSynthetic
package org.joml

import java.nio.ByteBuffer
import java.nio.DoubleBuffer

/* Operators */
public inline operator fun Matrix4dc.plus(m: Matrix4dc): Matrix4d = add(m, Matrix4d())
public inline operator fun Matrix4d.plusAssign(m: Matrix4dc) { add(m) }
public inline operator fun Matrix4dc.minus(m: Matrix4dc): Matrix4d = sub(m, Matrix4d())
public inline operator fun Matrix4d.minusAssign(m: Matrix4dc) { sub(m) }
public inline operator fun Matrix4dc.times(m: Matrix4dc): Matrix4d = mul(m, Matrix4d())
public inline operator fun Matrix4dc.times(m: Matrix4fc): Matrix4d = mul(m, Matrix4d())
public inline operator fun Matrix4dc.times(q: Quaterniondc): Matrix4d = rotate(q, Matrix4d())
public inline operator fun Matrix4dc.times(q: Quaternionfc): Matrix4d = rotate(q, Matrix4d())
public inline operator fun Matrix4dc.times(a: AxisAngle4d): Matrix4d = rotate(a, Matrix4d())
public inline operator fun Matrix4dc.times(a: AxisAngle4f): Matrix4d = rotate(a, Matrix4d())
public inline operator fun Matrix4dc.times(v: Vector4dc): Vector4d = transform(v, Vector4d())
public inline operator fun Matrix4d.timesAssign(m: Matrix4dc) { mul(m) }
public inline operator fun Matrix4d.timesAssign(m: Matrix4fc) { mul(m) }
public inline operator fun Matrix4d.timesAssign(q: Quaterniondc) { rotate(q) }
public inline operator fun Matrix4d.timesAssign(q: Quaternionfc) { rotate(q) }
public inline operator fun Matrix4d.timesAssign(a: AxisAngle4d) { rotate(a) }
public inline operator fun Matrix4d.timesAssign(a: AxisAngle4f) { rotate(a) }
public inline infix fun Matrix4dc.mulComponentWise(m: Matrix4dc): Matrix4d = mulComponentWise(m, Matrix4d())

/* Buffer Operations */
public inline fun ByteBuffer.getMatrix4d(v: Matrix4d): Matrix4d = v.set(this)
public inline fun ByteBuffer.putMatrix4d(v: Matrix4d): ByteBuffer = v.get(this)
public inline fun ByteBuffer.putMatrix4d(index: Int, v: Matrix4d): ByteBuffer = v.get(index, this)
public inline fun DoubleBuffer.getMatrix4d(): Matrix4d = Matrix4d(this)
public inline fun DoubleBuffer.getMatrix4d(v: Matrix4d): Matrix4d = v.set(this)
public inline fun DoubleBuffer.putMatrix4d(v: Matrix4d): DoubleBuffer = v.get(this)
public inline fun DoubleBuffer.putMatrix4d(index: Int, v: Matrix4d): DoubleBuffer = v.get(index, this)
