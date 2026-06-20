@file:Suppress("NOTHING_TO_INLINE")
@file:JvmSynthetic
package org.joml

import java.nio.ByteBuffer
import java.nio.FloatBuffer

/* Operators */
public inline operator fun Matrix4fc.plus(m: Matrix4fc): Matrix4f = add(m, Matrix4f())
public inline operator fun Matrix4f.plusAssign(m: Matrix4fc) { add(m) }
public inline operator fun Matrix4fc.minus(m: Matrix4fc): Matrix4f = sub(m, Matrix4f())
public inline operator fun Matrix4f.minusAssign(m: Matrix4fc) { sub(m) }
public inline operator fun Matrix4fc.times(m: Matrix4fc): Matrix4f = mul(m, Matrix4f())
public inline operator fun Matrix4fc.times(q: Quaternionfc): Matrix4f = rotate(q, Matrix4f())
public inline operator fun Matrix4fc.times(a: AxisAngle4f): Matrix4f = rotate(a, Matrix4f())
public inline operator fun Matrix4fc.times(v: Vector4fc): Vector4f = transform(v, Vector4f())
public inline operator fun Matrix4f.timesAssign(m: Matrix4fc) { mul(m) }
public inline operator fun Matrix4f.timesAssign(q: Quaternionfc) { rotate(q) }
public inline operator fun Matrix4f.timesAssign(a: AxisAngle4f) { rotate(a) }
public inline infix fun Matrix4fc.mulComponentWise(m: Matrix4fc): Matrix4f = mulComponentWise(m, Matrix4f())

/* Buffer Operations */
public inline fun ByteBuffer.getMatrix4f(v: Matrix4f): Matrix4f = v.set(this)
public inline fun ByteBuffer.putMatrix4f(v: Matrix4f): ByteBuffer = v.get(this)
public inline fun ByteBuffer.putMatrix4f(index: Int, v: Matrix4f): ByteBuffer = v.get(index, this)
public inline fun FloatBuffer.getMatrix4f(): Matrix4f = Matrix4f(this)
public inline fun FloatBuffer.getMatrix4f(v: Matrix4f): Matrix4f = v.set(this)
public inline fun FloatBuffer.putMatrix4f(v: Matrix4f): FloatBuffer = v.get(this)
public inline fun FloatBuffer.putMatrix4f(index: Int, v: Matrix4f): FloatBuffer = v.get(index, this)

/* Angle operations */
public fun Matrix4fc.rotateX(ang: Anglef, dest: Matrix4f): Matrix4f = rotateX(ang.radians, dest)
public fun Matrix4fc.rotateY(ang: Anglef, dest: Matrix4f): Matrix4f = rotateY(ang.radians, dest)
public fun Matrix4fc.rotateZ(ang: Anglef, dest: Matrix4f): Matrix4f = rotateZ(ang.radians, dest)
public fun Matrix4fc.rotate(ang: Anglef, x: Float, y: Float, z: Float, dest: Matrix4f): Matrix4f =
    rotate(ang.radians, x, y, z, dest)
public fun Matrix4fc.rotateTranslation(ang: Anglef, x: Float, y: Float, z: Float, dest: Matrix4f): Matrix4f =
    rotateTranslation(ang.radians, x, y, z, dest)
public fun Matrix4fc.rotateAffine(ang: Anglef, x: Float, y: Float, z: Float, dest: Matrix4f): Matrix4f =
    rotateAffine(ang.radians, x, y, z, dest)
public fun Matrix4fc.rotateLocal(ang: Anglef, x: Float, y: Float, z: Float, dest: Matrix4f): Matrix4f =
    rotateLocal(ang.radians, x, y, z, dest)
public fun Matrix4fc.rotateLocalX(ang: Anglef, dest: Matrix4f): Matrix4f = rotateLocalX(ang.radians, dest)
public fun Matrix4fc.rotateLocalY(ang: Anglef, dest: Matrix4f): Matrix4f = rotateLocalY(ang.radians, dest)
public fun Matrix4fc.rotateLocalZ(ang: Anglef, dest: Matrix4f): Matrix4f = rotateLocalZ(ang.radians, dest)
public fun Matrix4fc.rotate(angle: Anglef, axis: Vector3fc, dest: Matrix4f): Matrix4f =
    rotate(angle.radians, axis, dest)
