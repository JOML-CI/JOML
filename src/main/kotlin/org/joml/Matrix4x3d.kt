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
public fun Matrix4x3dc.rotateX(angle: Angled, dest: Matrix4x3d): Matrix4x3d = rotateX(angle.radians, dest)
public fun Matrix4x3dc.rotateY(angle: Angled, dest: Matrix4x3d): Matrix4x3d = rotateY(angle.radians, dest)
public fun Matrix4x3dc.rotateZ(angle: Angled, dest: Matrix4x3d): Matrix4x3d = rotateZ(angle.radians, dest)
public fun Matrix4x3dc.rotate(angle: Angled, x: Double, y: Double, z: Double, dest: Matrix4x3d): Matrix4x3d = rotate(angle.radians, x, y, z, dest)
public fun Matrix4x3dc.rotateTranslation(angle: Angled, x: Double, y: Double, z: Double, dest: Matrix4x3d): Matrix4x3d = rotateTranslation(angle.radians, x, y, z, dest)
public fun Matrix4x3dc.rotateLocal(angle: Angled, x: Double, y: Double, z: Double, dest: Matrix4x3d): Matrix4x3d = rotateLocal(angle.radians, x, y, z, dest)
public fun Matrix4x3dc.rotate(angle: Angled, axis: Vector3dc, dest: Matrix4x3d): Matrix4x3d = rotate(angle.radians, axis, dest)
public fun Matrix4x3dc.rotateXYZ(angleX: Angled, angleY: Angled, angleZ: Angled, dest: Matrix4x3d): Matrix4x3d = rotateXYZ(angleX.radians, angleY.radians, angleZ.radians, dest)
public fun Matrix4x3dc.rotateZYX(angleZ: Angled, angleY: Angled, angleX: Angled, dest: Matrix4x3d): Matrix4x3d = rotateZYX(angleZ.radians, angleY.radians, angleX.radians, dest)
public fun Matrix4x3dc.rotateYXZ(angleY: Angled, angleX: Angled, angleZ: Angled, dest: Matrix4x3d): Matrix4x3d = rotateYXZ(angleY.radians, angleX.radians, angleZ.radians, dest)
public fun Matrix4x3dc.rotate(angle: Angled, axis: Vector3fc, dest: Matrix4x3d): Matrix4x3d = rotate(angle.radians, axis, dest)

public fun Matrix4x3d.rotateX(angle: Angled): Matrix4x3d = rotateX(angle.radians)
public fun Matrix4x3d.rotateY(angle: Angled): Matrix4x3d = rotateY(angle.radians)
public fun Matrix4x3d.rotateZ(angle: Angled): Matrix4x3d = rotateZ(angle.radians)
public fun Matrix4x3d.rotate(angle: Angled, x: Double, y: Double, z: Double): Matrix4x3d = rotate(angle.radians, x, y, z)
public fun Matrix4x3d.rotateLocal(angle: Angled, x: Double, y: Double, z: Double): Matrix4x3d = rotateLocal(angle.radians, x, y, z)
public fun Matrix4x3d.rotate(angle: Angled, axis: Vector3dc): Matrix4x3d = rotate(angle.radians, axis)
public fun Matrix4x3d.rotateXYZ(angleX: Angled, angleY: Angled, angleZ: Angled): Matrix4x3d = rotateXYZ(angleX.radians, angleY.radians, angleZ.radians)
public fun Matrix4x3d.rotateZYX(angleZ: Angled, angleY: Angled, angleX: Angled): Matrix4x3d = rotateZYX(angleZ.radians, angleY.radians, angleX.radians)
public fun Matrix4x3d.rotateYXZ(angleY: Angled, angleX: Angled, angleZ: Angled): Matrix4x3d = rotateYXZ(angleY.radians, angleX.radians, angleZ.radians)
public fun Matrix4x3d.rotate(angle: Angled, axis: Vector3fc): Matrix4x3d = rotate(angle.radians, axis)

public fun Matrix4x3d.rotation(angle: Angled, axis: Vector3dc): Matrix4x3d = rotation(angle.radians, axis)
public fun Matrix4x3d.rotation(angle: Angled, axis: Vector3fc): Matrix4x3d = rotation(angle.radians, axis)
public fun Matrix4x3d.rotation(angle: Angled, x: Double, y: Double, z: Double): Matrix4x3d = rotation(angle.radians, x, y, z)
