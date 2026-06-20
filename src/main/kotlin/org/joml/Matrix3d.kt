@file:Suppress("NOTHING_TO_INLINE")
@file:JvmSynthetic
package org.joml

import java.nio.ByteBuffer
import java.nio.DoubleBuffer

/* Operators */
public inline operator fun Matrix3dc.plus(m: Matrix3dc): Matrix3d = add(m, Matrix3d())
public inline operator fun Matrix3d.plusAssign(m: Matrix3dc) { add(m) }
public inline operator fun Matrix3dc.minus(m: Matrix3dc): Matrix3d = sub(m, Matrix3d())
public inline operator fun Matrix3d.minusAssign(m: Matrix3dc) { sub(m) }
public inline operator fun Matrix3dc.times(m: Matrix3dc): Matrix3d = mul(m, Matrix3d())
public inline operator fun Matrix3dc.times(m: Matrix3fc): Matrix3d = mul(m, Matrix3d())
public inline operator fun Matrix3dc.times(q: Quaterniondc): Matrix3d = rotate(q, Matrix3d())
public inline operator fun Matrix3dc.times(q: Quaternionfc): Matrix3d = rotate(q, Matrix3d())
public inline operator fun Matrix3dc.times(a: AxisAngle4d): Matrix3d = rotate(a, Matrix3d())
public inline operator fun Matrix3dc.times(a: AxisAngle4f): Matrix3d = rotate(a, Matrix3d())
public inline operator fun Matrix3dc.times(v: Vector3dc): Vector3d = transform(v, Vector3d())
public inline operator fun Matrix3dc.times(v: Vector3fc): Vector3f = transform(v, Vector3f())
public inline operator fun Matrix3d.timesAssign(m: Matrix3dc) { mul(m) }
public inline operator fun Matrix3d.timesAssign(m: Matrix3fc) { mul(m) }
public inline operator fun Matrix3d.timesAssign(q: Quaterniondc) { rotate(q) }
public inline operator fun Matrix3d.timesAssign(q: Quaternionfc) { rotate(q) }
public inline operator fun Matrix3d.timesAssign(a: AxisAngle4d) { rotate(a) }
public inline operator fun Matrix3d.timesAssign(a: AxisAngle4f) { rotate(a) }
public inline infix fun Matrix3dc.mulComponentWise(m: Matrix3dc): Matrix3d = mulComponentWise(m, Matrix3d())

/* Buffer Operations */
public inline fun ByteBuffer.getMatrix3d(v: Matrix3d): Matrix3d = v.set(this)
public inline fun ByteBuffer.putMatrix3d(v: Matrix3d): ByteBuffer = v.get(this)
public inline fun ByteBuffer.putMatrix3d(index: Int, v: Matrix3d): ByteBuffer = v.get(index, this)
public inline fun DoubleBuffer.getMatrix3d(): Matrix3d = Matrix3d(this)
public inline fun DoubleBuffer.getMatrix3d(v: Matrix3d): Matrix3d = v.set(this)
public inline fun DoubleBuffer.putMatrix3d(v: Matrix3d): DoubleBuffer = v.get(this)
public inline fun DoubleBuffer.putMatrix3d(index: Int, v: Matrix3d): DoubleBuffer = v.get(index, this)

/* Angle operations */
public fun Matrix3dc.rotateX(angle: Angled, dest: Matrix3d): Matrix3d = rotateX(angle.radians, dest)
public fun Matrix3dc.rotateY(angle: Angled, dest: Matrix3d): Matrix3d = rotateY(angle.radians, dest)
public fun Matrix3dc.rotateZ(angle: Angled, dest: Matrix3d): Matrix3d = rotateZ(angle.radians, dest)
public fun Matrix3dc.rotate(angle: Angled, x: Double, y: Double, z: Double, dest: Matrix3d): Matrix3d = rotate(angle.radians, x, y, z, dest)
public fun Matrix3dc.rotateLocal(angle: Angled, x: Double, y: Double, z: Double, dest: Matrix3d): Matrix3d = rotateLocal(angle.radians, x, y, z, dest)
public fun Matrix3dc.rotateLocalX(angle: Angled, dest: Matrix3d): Matrix3d = rotateLocalX(angle.radians, dest)
public fun Matrix3dc.rotateLocalY(angle: Angled, dest: Matrix3d): Matrix3d = rotateLocalY(angle.radians, dest)
public fun Matrix3dc.rotateLocalZ(angle: Angled, dest: Matrix3d): Matrix3d = rotateLocalZ(angle.radians, dest)
public fun Matrix3dc.rotate(angle: Angled, axis: Vector3dc, dest: Matrix3d): Matrix3d = rotate(angle.radians, axis, dest)
public fun Matrix3dc.rotateXYZ(angleX: Angled, angleY: Angled, angleZ: Angled, dest: Matrix3d): Matrix3d = rotateXYZ(angleX.radians, angleY.radians, angleZ.radians, dest)
public fun Matrix3dc.rotateZYX(angleZ: Angled, angleY: Angled, angleX: Angled, dest: Matrix3d): Matrix3d = rotateZYX(angleZ.radians, angleY.radians, angleX.radians, dest)
public fun Matrix3dc.rotateYXZ(angleY: Angled, angleX: Angled, angleZ: Angled, dest: Matrix3d): Matrix3d = rotateYXZ(angleY.radians, angleX.radians, angleZ.radians, dest)
public fun Matrix3dc.rotate(angle: Angled, axis: Vector3fc, dest: Matrix3d): Matrix3d = rotate(angle.radians, axis, dest)

public fun Matrix3d.rotateX(angle: Angled): Matrix3d = rotateX(angle.radians)
public fun Matrix3d.rotateY(angle: Angled): Matrix3d = rotateY(angle.radians)
public fun Matrix3d.rotateZ(angle: Angled): Matrix3d = rotateZ(angle.radians)
public fun Matrix3d.rotate(angle: Angled, x: Double, y: Double, z: Double): Matrix3d = rotate(angle.radians, x, y, z)
public fun Matrix3d.rotateLocal(angle: Angled, x: Double, y: Double, z: Double): Matrix3d = rotateLocal(angle.radians, x, y, z)
public fun Matrix3d.rotateLocalX(angle: Angled): Matrix3d = rotateLocalX(angle.radians)
public fun Matrix3d.rotateLocalY(angle: Angled): Matrix3d = rotateLocalY(angle.radians)
public fun Matrix3d.rotateLocalZ(angle: Angled): Matrix3d = rotateLocalZ(angle.radians)
public fun Matrix3d.rotate(angle: Angled, axis: Vector3dc): Matrix3d = rotate(angle.radians, axis)
public fun Matrix3d.rotateXYZ(angleX: Angled, angleY: Angled, angleZ: Angled): Matrix3d = rotateXYZ(angleX.radians, angleY.radians, angleZ.radians)
public fun Matrix3d.rotateZYX(angleZ: Angled, angleY: Angled, angleX: Angled): Matrix3d = rotateZYX(angleZ.radians, angleY.radians, angleX.radians)
public fun Matrix3d.rotateYXZ(angleY: Angled, angleX: Angled, angleZ: Angled): Matrix3d = rotateYXZ(angleY.radians, angleX.radians, angleZ.radians)
public fun Matrix3d.rotate(angle: Angled, axis: Vector3fc): Matrix3d = rotate(angle.radians, axis)

public fun Matrix3d.rotation(angle: Angled, axis: Vector3dc): Matrix3d = rotation(angle.radians, axis)
public fun Matrix3d.rotation(angle: Angled, axis: Vector3fc): Matrix3d = rotation(angle.radians, axis)
public fun Matrix3d.rotation(angle: Angled, x: Double, y: Double, z: Double): Matrix3d = rotation(angle.radians, x, y, z)
