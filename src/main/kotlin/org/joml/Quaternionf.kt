@file:Suppress("NOTHING_TO_INLINE")
@file:JvmSynthetic
package org.joml

import org.joml.Anglef.Companion.radians

/* Operators */
public inline operator fun Quaternionfc.plus(q: Quaternionfc): Quaternionf = add(q, Quaternionf())
public inline operator fun Quaternionf.plusAssign(q: Quaternionfc) { add(q) }
public inline operator fun Quaternionfc.minus(q: Quaternionfc): Quaternionf = sub(q, Quaternionf())
public inline operator fun Quaternionf.minusAssign(q: Quaternionfc) { sub(q) }
public inline operator fun Quaternionfc.times(q: Quaternionfc): Quaternionf = mul(q, Quaternionf())
public inline operator fun Quaternionfc.times(s: Float): Quaternionf = mul(s, Quaternionf())
public inline operator fun Quaternionfc.times(v: Vector4fc): Vector4f = transform(v, Vector4f())
public inline operator fun Quaternionfc.times(v: Vector3fc): Vector3f = transform(v, Vector3f())
public inline operator fun Quaternionf.timesAssign(q: Quaternionfc) { mul(q) }
public inline operator fun Quaternionf.timesAssign(s: Float) { mul(s) }
public inline operator fun Quaternionfc.div(q: Quaternionfc): Quaternionf = div(q, Quaternionf())
public inline operator fun Quaternionfc.div(s: Float): Quaternionf = div(s, Quaternionf())
public inline operator fun Quaternionf.divAssign(q: Quaternionfc) { div(q) }
public inline operator fun Quaternionf.divAssign(s: Float) { div(s) }
public inline infix fun Quaternionfc.difference(q: Quaternionfc): Quaternionf = difference(q, Quaternionf())

/* Angle operations */
public fun Quaternionfc.anglef(): Anglef = angle().radians
public fun Quaternionfc.rotateXYZ(angleX: Anglef, angleY: Anglef, angleZ: Anglef, dest: Quaternionf): Quaternionf = rotateXYZ(angleX.radians, angleY.radians, angleZ.radians, dest)
public fun Quaternionfc.rotateZYX(angleZ: Anglef, angleY: Anglef, angleX: Anglef, dest: Quaternionf): Quaternionf = rotateZYX(angleZ.radians, angleY.radians, angleX.radians, dest)
public fun Quaternionfc.rotateYXZ(angleY: Anglef, angleX: Anglef, angleZ: Anglef, dest: Quaternionf): Quaternionf = rotateYXZ(angleY.radians, angleX.radians, angleZ.radians, dest)
public fun Quaternionfc.rotateX(angle: Anglef, dest: Quaternionf): Quaternionf = rotateX(angle.radians, dest)
public fun Quaternionfc.rotateY(angle: Anglef, dest: Quaternionf): Quaternionf = rotateY(angle.radians, dest)
public fun Quaternionfc.rotateZ(angle: Anglef, dest: Quaternionf): Quaternionf = rotateZ(angle.radians, dest)
public fun Quaternionfc.rotateLocalX(angle: Anglef, dest: Quaternionf): Quaternionf = rotateLocalX(angle.radians, dest)
public fun Quaternionfc.rotateLocalY(angle: Anglef, dest: Quaternionf): Quaternionf = rotateLocalY(angle.radians, dest)
public fun Quaternionfc.rotateLocalZ(angle: Anglef, dest: Quaternionf): Quaternionf = rotateLocalZ(angle.radians, dest)
public fun Quaternionfc.rotateAxis(angle: Anglef, axisX: Float, axisY: Float, axisZ: Float, dest: Quaternionf): Quaternionf = rotateAxis(angle.radians, axisX, axisY, axisZ, dest)
public fun Quaternionfc.rotateAxis(angle: Anglef, axis: Vector3fc, dest: Quaternionf): Quaternionf = rotateAxis(angle.radians, axis, dest)

public fun Quaternionf.rotateXYZ(angleX: Anglef, angleY: Anglef, angleZ: Anglef): Quaternionf = rotateXYZ(angleX.radians, angleY.radians, angleZ.radians)
public fun Quaternionf.rotateZYX(angleZ: Anglef, angleY: Anglef, angleX: Anglef): Quaternionf = rotateZYX(angleZ.radians, angleY.radians, angleX.radians)
public fun Quaternionf.rotateYXZ(angleY: Anglef, angleX: Anglef, angleZ: Anglef): Quaternionf = rotateYXZ(angleY.radians, angleX.radians, angleZ.radians)
public fun Quaternionf.rotateX(angle: Anglef): Quaternionf = rotateX(angle.radians)
public fun Quaternionf.rotateY(angle: Anglef): Quaternionf = rotateY(angle.radians)
public fun Quaternionf.rotateZ(angle: Anglef): Quaternionf = rotateZ(angle.radians)
public fun Quaternionf.rotateLocalX(angle: Anglef): Quaternionf = rotateLocalX(angle.radians)
public fun Quaternionf.rotateLocalY(angle: Anglef): Quaternionf = rotateLocalY(angle.radians)
public fun Quaternionf.rotateLocalZ(angle: Anglef): Quaternionf = rotateLocalZ(angle.radians)
public fun Quaternionf.rotateAxis(angle: Anglef, axisX: Float, axisY: Float, axisZ: Float): Quaternionf = rotateAxis(angle.radians, axisX, axisY, axisZ)
public fun Quaternionf.rotateAxis(angle: Anglef, axis: Vector3fc): Quaternionf = rotateAxis(angle.radians, axis)
public fun Quaternionf.setAngleAxis(angle: Anglef, x: Float, y: Float, z: Float): Quaternionf = setAngleAxis(angle.radians, x, y, z)
public fun Quaternionf.setAngleAxis(angle: Angled, x: Double, y: Double, z: Double): Quaternionf = setAngleAxis(angle.radians, x, y, z)
public fun Quaternionf.fromAxisAngleRad(axis: Vector3fc, angle: Anglef): Quaternionf = fromAxisAngleRad(axis, angle.radians)
public fun Quaternionf.fromAxisAngleRad(axisX: Float, axisY: Float, axisZ: Float, angle: Anglef): Quaternionf = fromAxisAngleRad(axisX, axisY, axisZ, angle.radians)
public fun Quaternionf.fromAxisAngleDeg(axis: Vector3fc, angle: Anglef): Quaternionf = fromAxisAngleDeg(axis, angle.radians)
public fun Quaternionf.fromAxisAngleDeg(axisX: Float, axisY: Float, axisZ: Float, angle: Anglef): Quaternionf = fromAxisAngleDeg(axisX, axisY, axisZ, angle.radians)
public fun Quaternionf.rotationAxis(angle: Anglef, axisX: Float, axisY: Float, axisZ: Float): Quaternionf = rotationAxis(angle.radians, axisX, axisY, axisZ)
public fun Quaternionf.rotationAxis(angle: Anglef, axis: Vector3fc): Quaternionf = rotationAxis(angle.radians, axis)
public fun Quaternionf.rotationX(angle: Anglef): Quaternionf = rotationX(angle.radians)
public fun Quaternionf.rotationY(angle: Anglef): Quaternionf = rotationY(angle.radians)
public fun Quaternionf.rotationZ(angle: Anglef): Quaternionf = rotationZ(angle.radians)
public fun Quaternionf.rotationXYZ(angleX: Anglef, angleY: Anglef, angleZ: Anglef): Quaternionf = rotationXYZ(angleX.radians, angleY.radians, angleZ.radians)
public fun Quaternionf.rotationZYX(angleZ: Anglef, angleY: Anglef, angleX: Anglef): Quaternionf = rotationZYX(angleZ.radians, angleY.radians, angleX.radians)
public fun Quaternionf.rotationYXZ(angleY: Anglef, angleX: Anglef, angleZ: Anglef): Quaternionf = rotationYXZ(angleY.radians, angleX.radians, angleZ.radians)
