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
public fun Matrix4fc.rotateX(angle: Anglef, dest: Matrix4f): Matrix4f = rotateX(angle.radians, dest)
public fun Matrix4fc.rotateY(angle: Anglef, dest: Matrix4f): Matrix4f = rotateY(angle.radians, dest)
public fun Matrix4fc.rotateZ(angle: Anglef, dest: Matrix4f): Matrix4f = rotateZ(angle.radians, dest)
public fun Matrix4fc.rotate(angle: Anglef, x: Float, y: Float, z: Float, dest: Matrix4f): Matrix4f = rotate(angle.radians, x, y, z, dest)
public fun Matrix4fc.rotateTranslation(angle: Anglef, x: Float, y: Float, z: Float, dest: Matrix4f): Matrix4f = rotateTranslation(angle.radians, x, y, z, dest)
public fun Matrix4fc.rotateAffine(angle: Anglef, x: Float, y: Float, z: Float, dest: Matrix4f): Matrix4f = rotateAffine(angle.radians, x, y, z, dest)
public fun Matrix4fc.rotateLocal(angle: Anglef, x: Float, y: Float, z: Float, dest: Matrix4f): Matrix4f = rotateLocal(angle.radians, x, y, z, dest)
public fun Matrix4fc.rotateLocalX(angle: Anglef, dest: Matrix4f): Matrix4f = rotateLocalX(angle.radians, dest)
public fun Matrix4fc.rotateLocalY(angle: Anglef, dest: Matrix4f): Matrix4f = rotateLocalY(angle.radians, dest)
public fun Matrix4fc.rotateLocalZ(angle: Anglef, dest: Matrix4f): Matrix4f = rotateLocalZ(angle.radians, dest)
public fun Matrix4fc.rotate(angle: Anglef, axis: Vector3fc, dest: Matrix4f): Matrix4f = rotate(angle.radians, axis, dest)
public fun Matrix4fc.rotateXYZ(angleX: Anglef, angleY: Anglef, angleZ: Anglef, dest: Matrix4f): Matrix4f = rotateXYZ(angleX.radians, angleY.radians, angleZ.radians, dest)
public fun Matrix4fc.rotateZYX(angleZ: Anglef, angleY: Anglef, angleX: Anglef, dest: Matrix4f): Matrix4f = rotateZYX(angleZ.radians, angleY.radians, angleX.radians, dest)
public fun Matrix4fc.rotateYXZ(angleY: Anglef, angleX: Anglef, angleZ: Anglef, dest: Matrix4f): Matrix4f = rotateYXZ(angleY.radians, angleX.radians, angleZ.radians, dest)
public fun Matrix4fc.rotateAffineXYZ(angleX: Anglef, angleY: Anglef, angleZ: Anglef, dest: Matrix4f): Matrix4f = rotateAffineXYZ(angleX.radians, angleY.radians, angleZ.radians, dest)
public fun Matrix4fc.rotateAffineZYX(angleZ: Anglef, angleY: Anglef, angleX: Anglef, dest: Matrix4f): Matrix4f = rotateAffineZYX(angleZ.radians, angleY.radians, angleX.radians, dest)
public fun Matrix4fc.rotateAffineYXZ(angleY: Anglef, angleX: Anglef, angleZ: Anglef, dest: Matrix4f): Matrix4f = rotateAffineYXZ(angleY.radians, angleX.radians, angleZ.radians, dest)
public fun Matrix4fc.arcball(radius: Float, centerX: Float, centerY: Float, centerZ: Float, angleX: Anglef, angleY: Anglef, dest: Matrix4f): Matrix4f = arcball(radius, centerX, centerY, centerZ, angleX.radians, angleY.radians, dest)
public fun Matrix4fc.arcball(radius: Float, center: Vector3fc, angleX: Anglef, angleY: Anglef, dest: Matrix4f): Matrix4f = arcball(radius, center, angleX.radians, angleY.radians, dest)

public fun Matrix4f.rotateX(angle: Anglef): Matrix4f = rotateX(angle.radians)
public fun Matrix4f.rotateY(angle: Anglef): Matrix4f = rotateY(angle.radians)
public fun Matrix4f.rotateZ(angle: Anglef): Matrix4f = rotateZ(angle.radians)
public fun Matrix4f.rotate(angle: Anglef, x: Float, y: Float, z: Float): Matrix4f = rotate(angle.radians, x, y, z)
public fun Matrix4f.rotateAffine(angle: Anglef, x: Float, y: Float, z: Float): Matrix4f = rotateAffine(angle.radians, x, y, z)
public fun Matrix4f.rotateLocal(angle: Anglef, x: Float, y: Float, z: Float): Matrix4f = rotateLocal(angle.radians, x, y, z)
public fun Matrix4f.rotateLocalX(angle: Anglef): Matrix4f = rotateLocalX(angle.radians)
public fun Matrix4f.rotateLocalY(angle: Anglef): Matrix4f = rotateLocalY(angle.radians)
public fun Matrix4f.rotateLocalZ(angle: Anglef): Matrix4f = rotateLocalZ(angle.radians)
public fun Matrix4f.rotate(angle: Anglef, axis: Vector3fc): Matrix4f = rotate(angle.radians, axis)
public fun Matrix4f.rotateXYZ(angleX: Anglef, angleY: Anglef, angleZ: Anglef): Matrix4f = rotateXYZ(angleX.radians, angleY.radians, angleZ.radians)
public fun Matrix4f.rotateZYX(angleZ: Anglef, angleY: Anglef, angleX: Anglef): Matrix4f = rotateZYX(angleZ.radians, angleY.radians, angleX.radians)
public fun Matrix4f.rotateYXZ(angleY: Anglef, angleX: Anglef, angleZ: Anglef): Matrix4f = rotateYXZ(angleY.radians, angleX.radians, angleZ.radians)
public fun Matrix4f.rotateAffineXYZ(angleX: Anglef, angleY: Anglef, angleZ: Anglef): Matrix4f = rotateAffineXYZ(angleX.radians, angleY.radians, angleZ.radians)
public fun Matrix4f.rotateAffineZYX(angleZ: Anglef, angleY: Anglef, angleX: Anglef): Matrix4f = rotateAffineZYX(angleZ.radians, angleY.radians, angleX.radians)
public fun Matrix4f.rotateAffineYXZ(angleY: Anglef, angleX: Anglef, angleZ: Anglef): Matrix4f = rotateAffineYXZ(angleY.radians, angleX.radians, angleZ.radians)
public fun Matrix4f.rotation(angle: Anglef, axis: Vector3fc): Matrix4f = rotation(angle.radians, axis)
public fun Matrix4f.rotation(angle: Anglef, x: Float, y: Float, z: Float): Matrix4f = rotation(angle.radians, x, y, z)
public fun Matrix4f.rotationX(angle: Anglef): Matrix4f = rotationX(angle.radians)
public fun Matrix4f.rotationY(angle: Anglef): Matrix4f = rotationY(angle.radians)
public fun Matrix4f.rotationZ(angle: Anglef): Matrix4f = rotationZ(angle.radians)
public fun Matrix4f.rotationXYZ(angleX: Anglef, angleY: Anglef, angleZ: Anglef): Matrix4f = rotationXYZ(angleX.radians, angleY.radians, angleZ.radians)
public fun Matrix4f.rotationZYX(angleZ: Anglef, angleY: Anglef, angleX: Anglef): Matrix4f = rotationZYX(angleZ.radians, angleY.radians, angleX.radians)
public fun Matrix4f.rotationYXZ(angleY: Anglef, angleX: Anglef, angleZ: Anglef): Matrix4f = rotationYXZ(angleY.radians, angleX.radians, angleZ.radians)
public fun Matrix4f.setRotationXYZ(angleX: Anglef, angleY: Anglef, angleZ: Anglef): Matrix4f = setRotationXYZ(angleX.radians, angleY.radians, angleZ.radians)
public fun Matrix4f.setRotationZYX(angleZ: Anglef, angleY: Anglef, angleX: Anglef): Matrix4f = setRotationZYX(angleZ.radians, angleY.radians, angleX.radians)
public fun Matrix4f.setRotationYXZ(angleY: Anglef, angleX: Anglef, angleZ: Anglef): Matrix4f = setRotationYXZ(angleY.radians, angleX.radians, angleZ.radians)
public fun Matrix4f.arcball(radius: Float, centerX: Float, centerY: Float, centerZ: Float, angleX: Anglef, angleY: Anglef): Matrix4f = arcball(radius, centerX, centerY, centerZ, angleX.radians, angleY.radians)
public fun Matrix4f.arcball(radius: Float, center: Vector3fc, angleX: Anglef, angleY: Anglef): Matrix4f = arcball(radius, center, angleX.radians, angleY.radians)
