@file:Suppress("NOTHING_TO_INLINE")
@file:JvmSynthetic
package org.joml

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
