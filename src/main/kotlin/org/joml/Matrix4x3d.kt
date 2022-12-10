package org.joml

import java.nio.ByteBuffer
import java.nio.DoubleBuffer

/* Operators */
public operator fun Matrix4x3dc.plus(m: Matrix4x3dc): Matrix4x3d = add(m, Matrix4x3d())
public operator fun Matrix4x3d.plusAssign(m: Matrix4x3dc) { add(m) }
public operator fun Matrix4x3dc.minus(m: Matrix4x3dc): Matrix4x3d = sub(m, Matrix4x3d())
public operator fun Matrix4x3d.minusAssign(m: Matrix4x3dc) { sub(m) }
public operator fun Matrix4x3dc.times(m: Matrix4x3dc): Matrix4x3d = mul(m, Matrix4x3d())
public operator fun Matrix4x3dc.times(m: Matrix4x3fc): Matrix4x3d = mul(m, Matrix4x3d())
public operator fun Matrix4x3dc.times(q: Quaterniondc): Matrix4x3d = rotate(q, Matrix4x3d())
public operator fun Matrix4x3dc.times(q: Quaternionfc): Matrix4x3d = rotate(q, Matrix4x3d())
public operator fun Matrix4x3dc.times(a: AxisAngle4d): Matrix4x3d = rotate(a, Matrix4x3d())
public operator fun Matrix4x3dc.times(a: AxisAngle4f): Matrix4x3d = rotate(a, Matrix4x3d())
public operator fun Matrix4x3dc.times(v: Vector4dc): Vector4d = transform(v, Vector4d())
public operator fun Matrix4x3d.timesAssign(m: Matrix4x3dc) { mul(m) }
public operator fun Matrix4x3d.timesAssign(m: Matrix4x3fc) { mul(m) }
public operator fun Matrix4x3d.timesAssign(q: Quaterniondc) { rotate(q) }
public operator fun Matrix4x3d.timesAssign(q: Quaternionfc) { rotate(q) }
public operator fun Matrix4x3d.timesAssign(a: AxisAngle4d) { rotate(a) }
public operator fun Matrix4x3d.timesAssign(a: AxisAngle4f) { rotate(a) }
public infix fun Matrix4x3dc.mulComponentWise(m: Matrix4x3dc): Matrix4x3d = mulComponentWise(m, Matrix4x3d())

/* Buffer Operations */
public fun ByteBuffer.getMatrix4x3d(v: Matrix4x3d): Matrix4x3d = v.set(this)
public fun ByteBuffer.putMatrix4x3d(v: Matrix4x3d): ByteBuffer = v.get(this)
public fun ByteBuffer.putMatrix4x3d(index: Int, v: Matrix4x3d): ByteBuffer = v.get(index, this)
public fun DoubleBuffer.getMatrix4x3d(): Matrix4x3d = Matrix4x3d(this)
public fun DoubleBuffer.getMatrix4x3d(v: Matrix4x3d): Matrix4x3d = v.set(this)
public fun DoubleBuffer.putMatrix4x3d(v: Matrix4x3d): DoubleBuffer = v.get(this)
public fun DoubleBuffer.putMatrix4x3d(index: Int, v: Matrix4x3d): DoubleBuffer = v.get(index, this)
