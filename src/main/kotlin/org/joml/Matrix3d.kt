package org.joml

import java.nio.ByteBuffer
import java.nio.DoubleBuffer

/* Operators */
public operator fun Matrix3dc.plus(m: Matrix3dc): Matrix3d = add(m, Matrix3d())
public operator fun Matrix3d.plusAssign(m: Matrix3dc) { add(m) }
public operator fun Matrix3dc.minus(m: Matrix3dc): Matrix3d = sub(m, Matrix3d())
public operator fun Matrix3d.minusAssign(m: Matrix3dc) { sub(m) }
public operator fun Matrix3dc.times(m: Matrix3dc): Matrix3d = mul(m, Matrix3d())
public operator fun Matrix3dc.times(m: Matrix3fc): Matrix3d = mul(m, Matrix3d())
public operator fun Matrix3dc.times(q: Quaterniondc): Matrix3d = rotate(q, Matrix3d())
public operator fun Matrix3dc.times(q: Quaternionfc): Matrix3d = rotate(q, Matrix3d())
public operator fun Matrix3dc.times(a: AxisAngle4d): Matrix3d = rotate(a, Matrix3d())
public operator fun Matrix3dc.times(a: AxisAngle4f): Matrix3d = rotate(a, Matrix3d())
public operator fun Matrix3dc.times(v: Vector3dc): Vector3d = transform(v, Vector3d())
public operator fun Matrix3dc.times(v: Vector3fc): Vector3f = transform(v, Vector3f())
public operator fun Matrix3d.timesAssign(m: Matrix3dc) { mul(m) }
public operator fun Matrix3d.timesAssign(m: Matrix3fc) { mul(m) }
public operator fun Matrix3d.timesAssign(q: Quaterniondc) { rotate(q) }
public operator fun Matrix3d.timesAssign(q: Quaternionfc) { rotate(q) }
public operator fun Matrix3d.timesAssign(a: AxisAngle4d) { rotate(a) }
public operator fun Matrix3d.timesAssign(a: AxisAngle4f) { rotate(a) }
public infix fun Matrix3dc.mulComponentWise(m: Matrix3dc): Matrix3d = mulComponentWise(m, Matrix3d())

/* Buffer Operations */
public fun ByteBuffer.getMatrix3d(v: Matrix3d): Matrix3d = v.set(this)
public fun ByteBuffer.putMatrix3d(v: Matrix3d): ByteBuffer = v.get(this)
public fun ByteBuffer.putMatrix3d(index: Int, v: Matrix3d): ByteBuffer = v.get(index, this)
public fun DoubleBuffer.getMatrix3d(): Matrix3d = Matrix3d(this)
public fun DoubleBuffer.getMatrix3d(v: Matrix3d): Matrix3d = v.set(this)
public fun DoubleBuffer.putMatrix3d(v: Matrix3d): DoubleBuffer = v.get(this)
public fun DoubleBuffer.putMatrix3d(index: Int, v: Matrix3d): DoubleBuffer = v.get(index, this)
