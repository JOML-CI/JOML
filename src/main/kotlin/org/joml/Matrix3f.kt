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
public fun Matrix3fc.rotateX(ang: Anglef, dest: Matrix3f): Matrix3f = rotateX(ang.radians, dest)
public fun Matrix3fc.rotateY(ang: Anglef, dest: Matrix3f): Matrix3f = rotateY(ang.radians, dest)
public fun Matrix3fc.rotateZ(ang: Anglef, dest: Matrix3f): Matrix3f = rotateZ(ang.radians, dest)
public fun Matrix3fc.rotate(ang: Anglef, x: Float, y: Float, z: Float, dest: Matrix3f): Matrix3f =
    rotate(ang.radians, x, y, z, dest)
public fun Matrix3fc.rotateLocal(ang: Anglef, x: Float, y: Float, z: Float, dest: Matrix3f): Matrix3f =
    rotateLocal(ang.radians, x, y, z, dest)
public fun Matrix3fc.rotateLocalX(ang: Anglef, dest: Matrix3f): Matrix3f = rotateLocalX(ang.radians, dest)
public fun Matrix3fc.rotateLocalY(ang: Anglef, dest: Matrix3f): Matrix3f = rotateLocalY(ang.radians, dest)
public fun Matrix3fc.rotateLocalZ(ang: Anglef, dest: Matrix3f): Matrix3f = rotateLocalZ(ang.radians, dest)
public fun Matrix3fc.rotate(angle: Anglef, axis: Vector3fc, dest: Matrix3f): Matrix3f =
    rotate(angle.radians, axis, dest)

public fun Matrix3f.rotateX(ang: Anglef): Matrix3f = rotateX(ang.radians)
public fun Matrix3f.rotateY(ang: Anglef): Matrix3f = rotateY(ang.radians)
public fun Matrix3f.rotateZ(ang: Anglef): Matrix3f = rotateZ(ang.radians)
public fun Matrix3f.rotate(ang: Anglef, x: Float, y: Float, z: Float): Matrix3f = rotate(ang.radians, x, y, z)
public fun Matrix3f.rotateLocal(ang: Anglef, x: Float, y: Float, z: Float): Matrix3f = rotateLocal(ang.radians, x, y, z)
public fun Matrix3f.rotateLocalX(ang: Anglef): Matrix3f = rotateLocalX(ang.radians)
public fun Matrix3f.rotateLocalY(ang: Anglef): Matrix3f = rotateLocalY(ang.radians)
public fun Matrix3f.rotateLocalZ(ang: Anglef): Matrix3f = rotateLocalZ(ang.radians)
public fun Matrix3f.rotate(angle: Anglef, axis: Vector3fc): Matrix3f = rotate(angle.radians, axis)