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
