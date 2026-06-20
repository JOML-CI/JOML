@file:Suppress("NOTHING_TO_INLINE")
@file:JvmSynthetic
package org.joml

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
public fun Quaterniondc.rotateXYZ(angleX: Angled, angleY: Angled, angleZ: Angled, dest: Quaterniond): Quaterniond =
    rotateXYZ(angleX.radians, angleY.radians, angleZ.radians, dest)
public fun Quaterniondc.rotateZYX(angleZ: Angled, angleY: Angled, angleX: Angled, dest: Quaterniond): Quaterniond =
    rotateZYX(angleZ.radians, angleY.radians, angleX.radians, dest)
public fun Quaterniondc.rotateYXZ(angleY: Angled, angleX: Angled, angleZ: Angled, dest: Quaterniond): Quaterniond =
    rotateYXZ(angleY.radians, angleX.radians, angleZ.radians, dest)
public fun Quaterniondc.rotateX(angle: Angled, dest: Quaterniond): Quaterniond = rotateX(angle.radians, dest)
public fun Quaterniondc.rotateY(angle: Angled, dest: Quaterniond): Quaterniond = rotateY(angle.radians, dest)
public fun Quaterniondc.rotateZ(angle: Angled, dest: Quaterniond): Quaterniond = rotateZ(angle.radians, dest)
public fun Quaterniondc.rotateLocalX(angle: Angled, dest: Quaterniond): Quaterniond = rotateLocalX(angle.radians, dest)
public fun Quaterniondc.rotateLocalY(angle: Angled, dest: Quaterniond): Quaterniond = rotateLocalY(angle.radians, dest)
public fun Quaterniondc.rotateLocalZ(angle: Angled, dest: Quaterniond): Quaterniond = rotateLocalZ(angle.radians, dest)
public fun Quaterniondc.rotateAxis(angle: Angled, axisX: Double, axisY: Double, axisZ: Double, dest: Quaterniond): Quaterniond =
    rotateAxis(angle.radians, axisX, axisY, axisZ, dest)
public fun Quaterniondc.rotateAxis(angle: Angled, axis: Vector3dc, dest: Quaterniond): Quaterniond =
    rotateAxis(angle.radians, axis, dest)
