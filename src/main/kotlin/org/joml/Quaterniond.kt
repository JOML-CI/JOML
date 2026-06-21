@file:Suppress("NOTHING_TO_INLINE")
@file:JvmSynthetic
package org.joml

import org.joml.Angled.Companion.radians

/* Operators */
public inline operator fun Quaterniondc.plus(q: Quaterniondc): Quaterniond = add(q, Quaterniond())
public inline operator fun Quaterniond.plusAssign(q: Quaterniondc) { add(q) }
public inline operator fun Quaterniondc.minus(q: Quaterniondc): Quaterniond = sub(q, Quaterniond())
public inline operator fun Quaterniond.minusAssign(q: Quaterniondc) { sub(q) }
public inline operator fun Quaterniondc.times(q: Quaterniondc): Quaterniond = mul(q, Quaterniond())
public inline operator fun Quaterniondc.times(s: Double): Quaterniond = mul(s, Quaterniond())
public inline operator fun Quaterniondc.times(v: Vector4dc): Vector4d = transform(v, Vector4d())
public inline operator fun Quaterniondc.times(v: Vector4fc): Vector4f = transform(v, Vector4f())
public inline operator fun Quaterniondc.times(v: Vector3dc): Vector3d = transform(v, Vector3d())
public inline operator fun Quaterniondc.times(v: Vector3fc): Vector3f = transform(v, Vector3f())
public inline operator fun Quaterniond.timesAssign(q: Quaterniondc) { mul(q) }
public inline operator fun Quaterniond.timesAssign(s: Double) { mul(s) }
public inline operator fun Quaterniondc.div(q: Quaterniondc): Quaterniond = div(q, Quaterniond())
public inline operator fun Quaterniondc.div(s: Double): Quaterniond = div(s, Quaterniond())
public inline operator fun Quaterniond.divAssign(q: Quaterniondc) { div(q) }
public inline operator fun Quaterniond.divAssign(s: Double) { div(s) }
public inline infix fun Quaterniondc.difference(q: Quaterniondc): Quaterniond = difference(q, Quaterniond())

/* Angle operations */
public fun Quaterniondc.angled(): Angled = angle().radians
public fun Quaterniondc.rotateXYZ(angleX: Angled, angleY: Angled, angleZ: Angled, dest: Quaterniond): Quaterniond = rotateXYZ(angleX.radians, angleY.radians, angleZ.radians, dest)
public fun Quaterniondc.rotateZYX(angleZ: Angled, angleY: Angled, angleX: Angled, dest: Quaterniond): Quaterniond = rotateZYX(angleZ.radians, angleY.radians, angleX.radians, dest)
public fun Quaterniondc.rotateYXZ(angleY: Angled, angleX: Angled, angleZ: Angled, dest: Quaterniond): Quaterniond = rotateYXZ(angleY.radians, angleX.radians, angleZ.radians, dest)
public fun Quaterniondc.rotateX(angle: Angled, dest: Quaterniond): Quaterniond = rotateX(angle.radians, dest)
public fun Quaterniondc.rotateY(angle: Angled, dest: Quaterniond): Quaterniond = rotateY(angle.radians, dest)
public fun Quaterniondc.rotateZ(angle: Angled, dest: Quaterniond): Quaterniond = rotateZ(angle.radians, dest)
public fun Quaterniondc.rotateLocalX(angle: Angled, dest: Quaterniond): Quaterniond = rotateLocalX(angle.radians, dest)
public fun Quaterniondc.rotateLocalY(angle: Angled, dest: Quaterniond): Quaterniond = rotateLocalY(angle.radians, dest)
public fun Quaterniondc.rotateLocalZ(angle: Angled, dest: Quaterniond): Quaterniond = rotateLocalZ(angle.radians, dest)
public fun Quaterniondc.rotateAxis(angle: Angled, axisX: Double, axisY: Double, axisZ: Double, dest: Quaterniond): Quaterniond = rotateAxis(angle.radians, axisX, axisY, axisZ, dest)
public fun Quaterniondc.rotateAxis(angle: Angled, axis: Vector3dc, dest: Quaterniond): Quaterniond = rotateAxis(angle.radians, axis, dest)

public fun Quaterniond.rotateXYZ(angleX: Angled, angleY: Angled, angleZ: Angled): Quaterniond = rotateXYZ(angleX.radians, angleY.radians, angleZ.radians)
public fun Quaterniond.rotateZYX(angleZ: Angled, angleY: Angled, angleX: Angled): Quaterniond = rotateZYX(angleZ.radians, angleY.radians, angleX.radians)
public fun Quaterniond.rotateYXZ(angleY: Angled, angleX: Angled, angleZ: Angled): Quaterniond = rotateYXZ(angleY.radians, angleX.radians, angleZ.radians)
public fun Quaterniond.rotateX(angle: Angled): Quaterniond = rotateX(angle.radians)
public fun Quaterniond.rotateY(angle: Angled): Quaterniond = rotateY(angle.radians)
public fun Quaterniond.rotateZ(angle: Angled): Quaterniond = rotateZ(angle.radians)
public fun Quaterniond.rotateLocalX(angle: Angled): Quaterniond = rotateLocalX(angle.radians)
public fun Quaterniond.rotateLocalY(angle: Angled): Quaterniond = rotateLocalY(angle.radians)
public fun Quaterniond.rotateLocalZ(angle: Angled): Quaterniond = rotateLocalZ(angle.radians)
public fun Quaterniond.rotateAxis(angle: Angled, axisX: Double, axisY: Double, axisZ: Double): Quaterniond = rotateAxis(angle.radians, axisX, axisY, axisZ)
public fun Quaterniond.rotateAxis(angle: Angled, axis: Vector3dc): Quaterniond = rotateAxis(angle.radians, axis)
public fun Quaterniond.setAngleAxis(angle: Angled, x: Double, y: Double, z: Double): Quaterniond = setAngleAxis(angle.radians, x, y, z)
public fun Quaterniond.setAngleAxis(angle: Angled, v: Vector3dc): Quaterniond = setAngleAxis(angle.radians, v)
public fun Quaterniond.fromAxisAngleRad(axis: Vector3dc, angle: Angled): Quaterniond = fromAxisAngleRad(axis, angle.radians)
public fun Quaterniond.fromAxisAngleRad(axisX: Double, axisY: Double, axisZ: Double, angle: Angled): Quaterniond = fromAxisAngleRad(axisX, axisY, axisZ, angle.radians)
public fun Quaterniond.fromAxisAngleDeg(axis: Vector3dc, angle: Angled): Quaterniond = fromAxisAngleDeg(axis, angle.radians)
public fun Quaterniond.fromAxisAngleDeg(axisX: Double, axisY: Double, axisZ: Double, angle: Angled): Quaterniond = fromAxisAngleDeg(axisX, axisY, axisZ, angle.radians)
public fun Quaterniond.rotationAxis(angle: Angled, axisX: Double, axisY: Double, axisZ: Double): Quaterniond = rotationAxis(angle.radians, axisX, axisY, axisZ)
public fun Quaterniond.rotationX(angle: Angled): Quaterniond = rotationX(angle.radians)
public fun Quaterniond.rotationY(angle: Angled): Quaterniond = rotationY(angle.radians)
public fun Quaterniond.rotationZ(angle: Angled): Quaterniond = rotationZ(angle.radians)
public fun Quaterniond.rotationXYZ(angleX: Angled, angleY: Angled, angleZ: Angled): Quaterniond = rotationXYZ(angleX.radians, angleY.radians, angleZ.radians)
public fun Quaterniond.rotationZYX(angleZ: Angled, angleY: Angled, angleX: Angled): Quaterniond = rotationZYX(angleZ.radians, angleY.radians, angleX.radians)
public fun Quaterniond.rotationYXZ(angleY: Angled, angleX: Angled, angleZ: Angled): Quaterniond = rotationYXZ(angleY.radians, angleX.radians, angleZ.radians)
