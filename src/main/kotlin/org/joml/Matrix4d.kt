package org.joml

import java.nio.ByteBuffer
import java.nio.DoubleBuffer

/* Operators */
public operator fun Matrix4dc.plus(m: Matrix4dc): Matrix4d = add(m, Matrix4d())
public operator fun Matrix4d.plusAssign(m: Matrix4dc) { add(m) }
public operator fun Matrix4dc.minus(m: Matrix4dc): Matrix4d = sub(m, Matrix4d())
public operator fun Matrix4d.minusAssign(m: Matrix4dc) { sub(m) }
public operator fun Matrix4dc.times(m: Matrix4dc): Matrix4d = mul(m, Matrix4d())
public operator fun Matrix4dc.times(m: Matrix4fc): Matrix4d = mul(m, Matrix4d())
public operator fun Matrix4dc.times(q: Quaterniondc): Matrix4d = rotate(q, Matrix4d())
public operator fun Matrix4dc.times(q: Quaternionfc): Matrix4d = rotate(q, Matrix4d())
public operator fun Matrix4dc.times(a: AxisAngle4d): Matrix4d = rotate(a, Matrix4d())
public operator fun Matrix4dc.times(a: AxisAngle4f): Matrix4d = rotate(a, Matrix4d())
public operator fun Matrix4dc.times(v: Vector4dc): Vector4d = transform(v, Vector4d())
public operator fun Matrix4d.timesAssign(m: Matrix4dc) { mul(m) }
public operator fun Matrix4d.timesAssign(m: Matrix4fc) { mul(m) }
public operator fun Matrix4d.timesAssign(q: Quaterniondc) { rotate(q) }
public operator fun Matrix4d.timesAssign(q: Quaternionfc) { rotate(q) }
public operator fun Matrix4d.timesAssign(a: AxisAngle4d) { rotate(a) }
public operator fun Matrix4d.timesAssign(a: AxisAngle4f) { rotate(a) }
public infix fun Matrix4dc.mulComponentWise(m: Matrix4dc): Matrix4d = mulComponentWise(m, Matrix4d())

/* Buffer Operations */
public fun ByteBuffer.getMatrix4d(v: Matrix4d): Matrix4d = v.set(this)
public fun ByteBuffer.putMatrix4d(v: Matrix4d): ByteBuffer = v.get(this)
public fun ByteBuffer.putMatrix4d(index: Int, v: Matrix4d): ByteBuffer = v.get(index, this)
public fun DoubleBuffer.getMatrix4d(): Matrix4d = Matrix4d(this)
public fun DoubleBuffer.getMatrix4d(v: Matrix4d): Matrix4d = v.set(this)
public fun DoubleBuffer.putMatrix4d(v: Matrix4d): DoubleBuffer = v.get(this)
public fun DoubleBuffer.putMatrix4d(index: Int, v: Matrix4d): DoubleBuffer = v.get(index, this)
