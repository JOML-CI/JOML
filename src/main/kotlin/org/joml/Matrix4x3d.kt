@file:Suppress("NOTHING_TO_INLINE")
@file:JvmSynthetic
package org.joml

import java.nio.ByteBuffer
import java.nio.DoubleBuffer

/* Operators */
public inline operator fun Matrix4x3dc.plus(m: Matrix4x3dc): Matrix4x3d = add(m, Matrix4x3d())
public inline operator fun Matrix4x3d.plusAssign(m: Matrix4x3dc) { add(m) }
public inline operator fun Matrix4x3dc.minus(m: Matrix4x3dc): Matrix4x3d = sub(m, Matrix4x3d())
public inline operator fun Matrix4x3d.minusAssign(m: Matrix4x3dc) { sub(m) }
public inline operator fun Matrix4x3dc.times(m: Matrix4x3dc): Matrix4x3d = mul(m, Matrix4x3d())
public inline operator fun Matrix4x3dc.times(m: Matrix4x3fc): Matrix4x3d = mul(m, Matrix4x3d())
public inline operator fun Matrix4x3dc.times(q: Quaterniondc): Matrix4x3d = rotate(q, Matrix4x3d())
public inline operator fun Matrix4x3dc.times(q: Quaternionfc): Matrix4x3d = rotate(q, Matrix4x3d())
public inline operator fun Matrix4x3dc.times(a: AxisAngle4d): Matrix4x3d = rotate(a, Matrix4x3d())
public inline operator fun Matrix4x3dc.times(a: AxisAngle4f): Matrix4x3d = rotate(a, Matrix4x3d())
public inline operator fun Matrix4x3dc.times(v: Vector4dc): Vector4d = transform(v, Vector4d())
public inline operator fun Matrix4x3d.timesAssign(m: Matrix4x3dc) { mul(m) }
public inline operator fun Matrix4x3d.timesAssign(m: Matrix4x3fc) { mul(m) }
public inline operator fun Matrix4x3d.timesAssign(q: Quaterniondc) { rotate(q) }
public inline operator fun Matrix4x3d.timesAssign(q: Quaternionfc) { rotate(q) }
public inline operator fun Matrix4x3d.timesAssign(a: AxisAngle4d) { rotate(a) }
public inline operator fun Matrix4x3d.timesAssign(a: AxisAngle4f) { rotate(a) }
public inline infix fun Matrix4x3dc.mulComponentWise(m: Matrix4x3dc): Matrix4x3d = mulComponentWise(m, Matrix4x3d())

/* Buffer Operations */
public inline fun ByteBuffer.getMatrix4x3d(v: Matrix4x3d): Matrix4x3d = v.set(this)
public inline fun ByteBuffer.putMatrix4x3d(v: Matrix4x3d): ByteBuffer = v.get(this)
public inline fun ByteBuffer.putMatrix4x3d(index: Int, v: Matrix4x3d): ByteBuffer = v.get(index, this)
public inline fun DoubleBuffer.getMatrix4x3d(): Matrix4x3d = Matrix4x3d(this)
public inline fun DoubleBuffer.getMatrix4x3d(v: Matrix4x3d): Matrix4x3d = v.set(this)
public inline fun DoubleBuffer.putMatrix4x3d(v: Matrix4x3d): DoubleBuffer = v.get(this)
public inline fun DoubleBuffer.putMatrix4x3d(index: Int, v: Matrix4x3d): DoubleBuffer = v.get(index, this)

/* Angle operations */
public fun Matrix4x3dc.rotateX(ang: Angled, dest: Matrix4x3d): Matrix4x3d = rotateX(ang.radians, dest)
public fun Matrix4x3dc.rotateY(ang: Angled, dest: Matrix4x3d): Matrix4x3d = rotateY(ang.radians, dest)
public fun Matrix4x3dc.rotateZ(ang: Angled, dest: Matrix4x3d): Matrix4x3d = rotateZ(ang.radians, dest)
public fun Matrix4x3dc.rotate(ang: Angled, x: Double, y: Double, z: Double, dest: Matrix4x3d): Matrix4x3d =
    rotate(ang.radians, x, y, z, dest)
public fun Matrix4x3dc.rotateTranslation(ang: Angled, x: Double, y: Double, z: Double, dest: Matrix4x3d): Matrix4x3d =
    rotateTranslation(ang.radians, x, y, z, dest)
public fun Matrix4x3dc.rotateLocal(ang: Angled, x: Double, y: Double, z: Double, dest: Matrix4x3d): Matrix4x3d =
    rotateLocal(ang.radians, x, y, z, dest)
public fun Matrix4x3dc.rotate(angle: Angled, axis: Vector3dc, dest: Matrix4x3d): Matrix4x3d = rotate(angle.radians, axis, dest)

public fun Matrix4x3d.rotateX(ang: Angled): Matrix4x3d = rotateX(ang.radians)
public fun Matrix4x3d.rotateY(ang: Angled): Matrix4x3d = rotateY(ang.radians)
public fun Matrix4x3d.rotateZ(ang: Angled): Matrix4x3d = rotateZ(ang.radians)
public fun Matrix4x3d.rotate(ang: Angled, x: Double, y: Double, z: Double): Matrix4x3d =
    rotate(ang.radians, x, y, z)
public fun Matrix4x3d.rotateLocal(ang: Angled, x: Double, y: Double, z: Double): Matrix4x3d =
    rotateLocal(ang.radians, x, y, z)
public fun Matrix4x3d.rotate(angle: Angled, axis: Vector3dc): Matrix4x3d = rotate(angle.radians, axis)