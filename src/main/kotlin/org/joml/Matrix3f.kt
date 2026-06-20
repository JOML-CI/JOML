@file:Suppress("NOTHING_TO_INLINE")
@file:JvmSynthetic
package org.joml

import java.nio.ByteBuffer
import java.nio.FloatBuffer

/* Operators */
public inline operator fun Matrix3fc.plus(m: Matrix3fc): Matrix3f = add(m, Matrix3f())
public inline operator fun Matrix3f.plusAssign(m: Matrix3fc) { add(m) }
public inline operator fun Matrix3fc.minus(m: Matrix3fc): Matrix3f = sub(m, Matrix3f())
public inline operator fun Matrix3f.minusAssign(m: Matrix3fc) { sub(m) }
public inline operator fun Matrix3fc.times(m: Matrix3fc): Matrix3f = mul(m, Matrix3f())
public inline operator fun Matrix3fc.times(q: Quaternionfc): Matrix3f = rotate(q, Matrix3f())
public inline operator fun Matrix3fc.times(a: AxisAngle4f): Matrix3f = rotate(a, Matrix3f())
public inline operator fun Matrix3fc.times(v: Vector3fc): Vector3f = transform(v, Vector3f())
public inline operator fun Matrix3f.timesAssign(m: Matrix3fc) { mul(m) }
public inline operator fun Matrix3f.timesAssign(q: Quaternionfc) { rotate(q) }
public inline operator fun Matrix3f.timesAssign(a: AxisAngle4f) { rotate(a) }
public inline infix fun Matrix3fc.mulComponentWise(m: Matrix3fc): Matrix3f = mulComponentWise(m, Matrix3f())

/* Buffer Operations */
public inline fun ByteBuffer.getMatrix3f(v: Matrix3f): Matrix3f = v.set(this)
public inline fun ByteBuffer.putMatrix3f(v: Matrix3f): ByteBuffer = v.get(this)
public inline fun ByteBuffer.putMatrix3f(index: Int, v: Matrix3f): ByteBuffer = v.get(index, this)
public inline fun FloatBuffer.getMatrix3f(): Matrix3f = Matrix3f(this)
public inline fun FloatBuffer.getMatrix3f(v: Matrix3f): Matrix3f = v.set(this)
public inline fun FloatBuffer.putMatrix3f(v: Matrix3f): FloatBuffer = v.get(this)
public inline fun FloatBuffer.putMatrix3f(index: Int, v: Matrix3f): FloatBuffer = v.get(index, this)

/* Angle operations */
public fun Matrix3fc.rotateX(angle: Anglef, dest: Matrix3f): Matrix3f = rotateX(angle.radians, dest)
public fun Matrix3fc.rotateY(angle: Anglef, dest: Matrix3f): Matrix3f = rotateY(angle.radians, dest)
public fun Matrix3fc.rotateZ(angle: Anglef, dest: Matrix3f): Matrix3f = rotateZ(angle.radians, dest)
public fun Matrix3fc.rotate(angle: Anglef, x: Float, y: Float, z: Float, dest: Matrix3f): Matrix3f = rotate(angle.radians, x, y, z, dest)
public fun Matrix3fc.rotateLocal(angle: Anglef, x: Float, y: Float, z: Float, dest: Matrix3f): Matrix3f = rotateLocal(angle.radians, x, y, z, dest)
public fun Matrix3fc.rotateLocalX(angle: Anglef, dest: Matrix3f): Matrix3f = rotateLocalX(angle.radians, dest)
public fun Matrix3fc.rotateLocalY(angle: Anglef, dest: Matrix3f): Matrix3f = rotateLocalY(angle.radians, dest)
public fun Matrix3fc.rotateLocalZ(angle: Anglef, dest: Matrix3f): Matrix3f = rotateLocalZ(angle.radians, dest)
public fun Matrix3fc.rotate(angle: Anglef, axis: Vector3fc, dest: Matrix3f): Matrix3f = rotate(angle.radians, axis, dest)
public fun Matrix3fc.rotateXYZ(angleX: Anglef, angleY: Anglef, angleZ: Anglef, dest: Matrix3f): Matrix3f = rotateXYZ(angleX.radians, angleY.radians, angleZ.radians, dest)
public fun Matrix3fc.rotateZYX(angleZ: Anglef, angleY: Anglef, angleX: Anglef, dest: Matrix3f): Matrix3f = rotateZYX(angleZ.radians, angleY.radians, angleX.radians, dest)
public fun Matrix3fc.rotateYXZ(angleY: Anglef, angleX: Anglef, angleZ: Anglef, dest: Matrix3f): Matrix3f = rotateYXZ(angleY.radians, angleX.radians, angleZ.radians, dest)

public fun Matrix3f.rotateX(angle: Anglef): Matrix3f = rotateX(angle.radians)
public fun Matrix3f.rotateY(angle: Anglef): Matrix3f = rotateY(angle.radians)
public fun Matrix3f.rotateZ(angle: Anglef): Matrix3f = rotateZ(angle.radians)
public fun Matrix3f.rotate(angle: Anglef, x: Float, y: Float, z: Float): Matrix3f = rotate(angle.radians, x, y, z)
public fun Matrix3f.rotateLocal(angle: Anglef, x: Float, y: Float, z: Float): Matrix3f = rotateLocal(angle.radians, x, y, z)
public fun Matrix3f.rotateLocalX(angle: Anglef): Matrix3f = rotateLocalX(angle.radians)
public fun Matrix3f.rotateLocalY(angle: Anglef): Matrix3f = rotateLocalY(angle.radians)
public fun Matrix3f.rotateLocalZ(angle: Anglef): Matrix3f = rotateLocalZ(angle.radians)
public fun Matrix3f.rotate(angle: Anglef, axis: Vector3fc): Matrix3f = rotate(angle.radians, axis)
public fun Matrix3f.rotateXYZ(angleX: Anglef, angleY: Anglef, angleZ: Anglef): Matrix3f = rotateXYZ(angleX.radians, angleY.radians, angleZ.radians)
public fun Matrix3f.rotateZYX(angleZ: Anglef, angleY: Anglef, angleX: Anglef): Matrix3f = rotateZYX(angleZ.radians, angleY.radians, angleX.radians)
public fun Matrix3f.rotateYXZ(angleY: Anglef, angleX: Anglef, angleZ: Anglef): Matrix3f = rotateYXZ(angleY.radians, angleX.radians, angleZ.radians)

public fun Matrix3f.rotation(angle: Anglef, axis: Vector3fc): Matrix3f = rotation(angle.radians, axis)
public fun Matrix3f.rotation(angle: Anglef, x: Float, y: Float, z: Float): Matrix3f = rotation(angle.radians, x, y, z)
