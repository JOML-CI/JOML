@file:Suppress("NOTHING_TO_INLINE")
@file:JvmSynthetic
package org.joml

import java.nio.ByteBuffer
import java.nio.FloatBuffer

/* Operators */
public inline operator fun Matrix4x3fc.plus(m: Matrix4x3fc): Matrix4x3f = add(m, Matrix4x3f())
public inline operator fun Matrix4x3f.plusAssign(m: Matrix4x3fc) { add(m) }
public inline operator fun Matrix4x3fc.minus(m: Matrix4x3fc): Matrix4x3f = sub(m, Matrix4x3f())
public inline operator fun Matrix4x3f.minusAssign(m: Matrix4x3fc) { sub(m) }
public inline operator fun Matrix4x3fc.times(m: Matrix4x3fc): Matrix4x3f = mul(m, Matrix4x3f())
public inline operator fun Matrix4x3fc.times(q: Quaternionfc): Matrix4x3f = rotate(q, Matrix4x3f())
public inline operator fun Matrix4x3fc.times(a: AxisAngle4f): Matrix4x3f = rotate(a, Matrix4x3f())
public inline operator fun Matrix4x3fc.times(v: Vector4fc): Vector4f = transform(v, Vector4f())
public inline operator fun Matrix4x3f.timesAssign(m: Matrix4x3fc) { mul(m) }
public inline operator fun Matrix4x3f.timesAssign(q: Quaternionfc) { rotate(q) }
public inline operator fun Matrix4x3f.timesAssign(a: AxisAngle4f) { rotate(a) }
public inline infix fun Matrix4x3fc.mulComponentWise(m: Matrix4x3fc): Matrix4x3f = mulComponentWise(m, Matrix4x3f())

/* Buffer Operations */
public inline fun ByteBuffer.getMatrix4x3f(v: Matrix4x3f): Matrix4x3f = v.set(this)
public inline fun ByteBuffer.putMatrix4x3f(v: Matrix4x3f): ByteBuffer = v.get(this)
public inline fun ByteBuffer.putMatrix4x3f(index: Int, v: Matrix4x3f): ByteBuffer = v.get(index, this)
public inline fun FloatBuffer.getMatrix4x3f(): Matrix4x3f = Matrix4x3f(this)
public inline fun FloatBuffer.getMatrix4x3f(v: Matrix4x3f): Matrix4x3f = v.set(this)
public inline fun FloatBuffer.putMatrix4x3f(v: Matrix4x3f): FloatBuffer = v.get(this)
public inline fun FloatBuffer.putMatrix4x3f(index: Int, v: Matrix4x3f): FloatBuffer = v.get(index, this)

/* Angle operations */
public fun Matrix4x3fc.rotateX(angle: Anglef, dest: Matrix4x3f): Matrix4x3f = rotateX(angle.radians, dest)
public fun Matrix4x3fc.rotateY(angle: Anglef, dest: Matrix4x3f): Matrix4x3f = rotateY(angle.radians, dest)
public fun Matrix4x3fc.rotateZ(angle: Anglef, dest: Matrix4x3f): Matrix4x3f = rotateZ(angle.radians, dest)
public fun Matrix4x3fc.rotate(angle: Anglef, x: Float, y: Float, z: Float, dest: Matrix4x3f): Matrix4x3f = rotate(angle.radians, x, y, z, dest)
public fun Matrix4x3fc.rotateTranslation(angle: Anglef, x: Float, y: Float, z: Float, dest: Matrix4x3f): Matrix4x3f = rotateTranslation(angle.radians, x, y, z, dest)
public fun Matrix4x3fc.rotateLocal(angle: Anglef, x: Float, y: Float, z: Float, dest: Matrix4x3f): Matrix4x3f = rotateLocal(angle.radians, x, y, z, dest)
public fun Matrix4x3fc.rotate(angle: Anglef, axis: Vector3fc, dest: Matrix4x3f): Matrix4x3f = rotate(angle.radians, axis, dest)
public fun Matrix4x3fc.rotateXYZ(angleX: Anglef, angleY: Anglef, angleZ: Anglef, dest: Matrix4x3f): Matrix4x3f = rotateXYZ(angleX.radians, angleY.radians, angleZ.radians, dest)
public fun Matrix4x3fc.rotateZYX(angleZ: Anglef, angleY: Anglef, angleX: Anglef, dest: Matrix4x3f): Matrix4x3f = rotateZYX(angleZ.radians, angleY.radians, angleX.radians, dest)
public fun Matrix4x3fc.rotateYXZ(angleY: Anglef, angleX: Anglef, angleZ: Anglef, dest: Matrix4x3f): Matrix4x3f = rotateYXZ(angleY.radians, angleX.radians, angleZ.radians, dest)
public fun Matrix4x3fc.arcball(radius: Float, centerX: Float, centerY: Float, centerZ: Float, angleX: Anglef, angleY: Anglef, dest: Matrix4x3f): Matrix4x3f = arcball(radius, centerX, centerY, centerZ, angleX.radians, angleY.radians, dest)
public fun Matrix4x3fc.arcball(radius: Float, center: Vector3fc, angleX: Anglef, angleY: Anglef, dest: Matrix4x3f): Matrix4x3f = arcball(radius, center, angleX.radians, angleY.radians, dest)

public fun Matrix4x3f.rotateX(angle: Anglef): Matrix4x3f = rotateX(angle.radians)
public fun Matrix4x3f.rotateY(angle: Anglef): Matrix4x3f = rotateY(angle.radians)
public fun Matrix4x3f.rotateZ(angle: Anglef): Matrix4x3f = rotateZ(angle.radians)
public fun Matrix4x3f.rotate(angle: Anglef, x: Float, y: Float, z: Float): Matrix4x3f = rotate(angle.radians, x, y, z)
public fun Matrix4x3f.rotateLocal(angle: Anglef, x: Float, y: Float, z: Float): Matrix4x3f = rotateLocal(angle.radians, x, y, z)
public fun Matrix4x3f.rotate(angle: Anglef, axis: Vector3fc): Matrix4x3f = rotate(angle.radians, axis)
public fun Matrix4x3f.rotateXYZ(angleX: Anglef, angleY: Anglef, angleZ: Anglef): Matrix4x3f = rotateXYZ(angleX.radians, angleY.radians, angleZ.radians)
public fun Matrix4x3f.rotateZYX(angleZ: Anglef, angleY: Anglef, angleX: Anglef): Matrix4x3f = rotateZYX(angleZ.radians, angleY.radians, angleX.radians)
public fun Matrix4x3f.rotateYXZ(angleY: Anglef, angleX: Anglef, angleZ: Anglef): Matrix4x3f = rotateYXZ(angleY.radians, angleX.radians, angleZ.radians)
public fun Matrix4x3f.rotation(angle: Anglef, axis: Vector3fc): Matrix4x3f = rotation(angle.radians, axis)
public fun Matrix4x3f.rotation(angle: Anglef, x: Float, y: Float, z: Float): Matrix4x3f = rotation(angle.radians, x, y, z)
public fun Matrix4x3f.rotationX(angle: Anglef): Matrix4x3f = rotationX(angle.radians)
public fun Matrix4x3f.rotationY(angle: Anglef): Matrix4x3f = rotationY(angle.radians)
public fun Matrix4x3f.rotationZ(angle: Anglef): Matrix4x3f = rotationZ(angle.radians)
public fun Matrix4x3f.rotationXYZ(angleX: Anglef, angleY: Anglef, angleZ: Anglef): Matrix4x3f = rotationXYZ(angleX.radians, angleY.radians, angleZ.radians)
public fun Matrix4x3f.rotationZYX(angleZ: Anglef, angleY: Anglef, angleX: Anglef): Matrix4x3f = rotationZYX(angleZ.radians, angleY.radians, angleX.radians)
public fun Matrix4x3f.rotationYXZ(angleY: Anglef, angleX: Anglef, angleZ: Anglef): Matrix4x3f = rotationYXZ(angleY.radians, angleX.radians, angleZ.radians)
public fun Matrix4x3f.setRotationXYZ(angleX: Anglef, angleY: Anglef, angleZ: Anglef): Matrix4x3f = setRotationXYZ(angleX.radians, angleY.radians, angleZ.radians)
public fun Matrix4x3f.setRotationZYX(angleZ: Anglef, angleY: Anglef, angleX: Anglef): Matrix4x3f = setRotationZYX(angleZ.radians, angleY.radians, angleX.radians)
public fun Matrix4x3f.setRotationYXZ(angleY: Anglef, angleX: Anglef, angleZ: Anglef): Matrix4x3f = setRotationYXZ(angleY.radians, angleX.radians, angleZ.radians)
public fun Matrix4x3f.arcball(radius: Float, centerX: Float, centerY: Float, centerZ: Float, angleX: Anglef, angleY: Anglef): Matrix4x3f = arcball(radius, centerX, centerY, centerZ, angleX.radians, angleY.radians)
public fun Matrix4x3f.arcball(radius: Float, center: Vector3fc, angleX: Anglef, angleY: Anglef): Matrix4x3f = arcball(radius, center, angleX.radians, angleY.radians)
