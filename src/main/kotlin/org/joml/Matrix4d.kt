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

/* Angle operations */
public fun Matrix4dc.rotateX(angle: Angled, dest: Matrix4d): Matrix4d = rotateX(angle.radians, dest)
public fun Matrix4dc.rotateY(angle: Angled, dest: Matrix4d): Matrix4d = rotateY(angle.radians, dest)
public fun Matrix4dc.rotateZ(angle: Angled, dest: Matrix4d): Matrix4d = rotateZ(angle.radians, dest)
public fun Matrix4dc.rotate(angle: Angled, x: Double, y: Double, z: Double, dest: Matrix4d): Matrix4d = rotate(angle.radians, x, y, z, dest)
public fun Matrix4dc.rotateTranslation(angle: Angled, x: Double, y: Double, z: Double, dest: Matrix4d): Matrix4d = rotateTranslation(angle.radians, x, y, z, dest)
public fun Matrix4dc.rotateAffine(angle: Angled, x: Double, y: Double, z: Double, dest: Matrix4d): Matrix4d = rotateAffine(angle.radians, x, y, z, dest)
public fun Matrix4dc.rotateLocal(angle: Angled, x: Double, y: Double, z: Double, dest: Matrix4d): Matrix4d = rotateLocal(angle.radians, x, y, z, dest)
public fun Matrix4dc.rotateLocalX(angle: Angled, dest: Matrix4d): Matrix4d = rotateLocalX(angle.radians, dest)
public fun Matrix4dc.rotateLocalY(angle: Angled, dest: Matrix4d): Matrix4d = rotateLocalY(angle.radians, dest)
public fun Matrix4dc.rotateLocalZ(angle: Angled, dest: Matrix4d): Matrix4d = rotateLocalZ(angle.radians, dest)
public fun Matrix4dc.rotate(angle: Angled, axis: Vector3fc, dest: Matrix4d): Matrix4d = rotate(angle.radians, axis, dest)
public fun Matrix4dc.rotateXYZ(angleX: Angled, angleY: Angled, angleZ: Angled, dest: Matrix4d): Matrix4d = rotateXYZ(angleX.radians, angleY.radians, angleZ.radians, dest)
public fun Matrix4dc.rotateZYX(angleZ: Angled, angleY: Angled, angleX: Angled, dest: Matrix4d): Matrix4d = rotateZYX(angleZ.radians, angleY.radians, angleX.radians, dest)
public fun Matrix4dc.rotateYXZ(angleY: Angled, angleX: Angled, angleZ: Angled, dest: Matrix4d): Matrix4d = rotateYXZ(angleY.radians, angleX.radians, angleZ.radians, dest)
public fun Matrix4dc.rotateAffineXYZ(angleX: Angled, angleY: Angled, angleZ: Angled, dest: Matrix4d): Matrix4d = rotateAffineXYZ(angleX.radians, angleY.radians, angleZ.radians, dest)
public fun Matrix4dc.rotateAffineZYX(angleZ: Angled, angleY: Angled, angleX: Angled, dest: Matrix4d): Matrix4d = rotateAffineZYX(angleZ.radians, angleY.radians, angleX.radians, dest)
public fun Matrix4dc.rotateAffineYXZ(angleY: Angled, angleX: Angled, angleZ: Angled, dest: Matrix4d): Matrix4d = rotateAffineYXZ(angleY.radians, angleX.radians, angleZ.radians, dest)
public fun Matrix4dc.rotate(angle: Angled, axis: Vector3dc, dest: Matrix4d): Matrix4d = rotate(angle.radians, axis, dest)

public fun Matrix4d.rotateX(angle: Angled): Matrix4d = rotateX(angle.radians)
public fun Matrix4d.rotateY(angle: Angled): Matrix4d = rotateY(angle.radians)
public fun Matrix4d.rotateZ(angle: Angled): Matrix4d = rotateZ(angle.radians)
public fun Matrix4d.rotate(angle: Angled, x: Double, y: Double, z: Double): Matrix4d = rotate(angle.radians, x, y, z)
public fun Matrix4d.rotateAffine(angle: Angled, x: Double, y: Double, z: Double): Matrix4d = rotateAffine(angle.radians, x, y, z)
public fun Matrix4d.rotateLocal(angle: Angled, x: Double, y: Double, z: Double): Matrix4d = rotateLocal(angle.radians, x, y, z)
public fun Matrix4d.rotateLocalX(angle: Angled): Matrix4d = rotateLocalX(angle.radians)
public fun Matrix4d.rotateLocalY(angle: Angled): Matrix4d = rotateLocalY(angle.radians)
public fun Matrix4d.rotateLocalZ(angle: Angled): Matrix4d = rotateLocalZ(angle.radians)
public fun Matrix4d.rotate(angle: Angled, axis: Vector3fc): Matrix4d = rotate(angle.radians, axis)
public fun Matrix4d.rotateXYZ(angleX: Angled, angleY: Angled, angleZ: Angled): Matrix4d = rotateXYZ(angleX.radians, angleY.radians, angleZ.radians)
public fun Matrix4d.rotateZYX(angleZ: Angled, angleY: Angled, angleX: Angled): Matrix4d = rotateZYX(angleZ.radians, angleY.radians, angleX.radians)
public fun Matrix4d.rotateYXZ(angleY: Angled, angleX: Angled, angleZ: Angled): Matrix4d = rotateYXZ(angleY.radians, angleX.radians, angleZ.radians)
public fun Matrix4d.rotateAffineXYZ(angleX: Angled, angleY: Angled, angleZ: Angled): Matrix4d = rotateAffineXYZ(angleX.radians, angleY.radians, angleZ.radians)
public fun Matrix4d.rotateAffineZYX(angleZ: Angled, angleY: Angled, angleX: Angled): Matrix4d = rotateAffineZYX(angleZ.radians, angleY.radians, angleX.radians)
public fun Matrix4d.rotateAffineYXZ(angleY: Angled, angleX: Angled, angleZ: Angled): Matrix4d = rotateAffineYXZ(angleY.radians, angleX.radians, angleZ.radians)
public fun Matrix4d.rotate(angle: Angled, axis: Vector3dc): Matrix4d = rotate(angle.radians, axis)

public fun Matrix4d.rotation(angle: Angled, axis: Vector3dc): Matrix4d = rotation(angle.radians, axis)
public fun Matrix4d.rotation(angle: Angled, axis: Vector3fc): Matrix4d = rotation(angle.radians, axis)
public fun Matrix4d.rotation(angle: Angled, x: Double, y: Double, z: Double): Matrix4d = rotation(angle.radians, x, y, z)
