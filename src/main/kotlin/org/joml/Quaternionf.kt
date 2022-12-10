package org.joml

/* Operators */
public operator fun Quaternionfc.plus(q: Quaternionfc): Quaternionf = add(q, Quaternionf())
public operator fun Quaternionf.plusAssign(q: Quaternionfc) { add(q) }
public operator fun Quaternionfc.minus(q: Quaternionfc): Quaternionf = sub(q, Quaternionf())
public operator fun Quaternionf.minusAssign(q: Quaternionfc) { sub(q) }
public operator fun Quaternionfc.times(q: Quaternionfc): Quaternionf = mul(q, Quaternionf())
public operator fun Quaternionfc.times(s: Float): Quaternionf = mul(s, Quaternionf())
public operator fun Quaternionfc.times(v: Vector4fc): Vector4f = transform(v, Vector4f())
public operator fun Quaternionfc.times(v: Vector3fc): Vector3f = transform(v, Vector3f())
public operator fun Quaternionf.timesAssign(q: Quaternionfc) { mul(q) }
public operator fun Quaternionf.timesAssign(s: Float) { mul(s) }
public operator fun Quaternionfc.div(q: Quaternionfc): Quaternionf = div(q, Quaternionf())
public operator fun Quaternionfc.div(s: Float): Quaternionf = div(s, Quaternionf())
public operator fun Quaternionf.divAssign(q: Quaternionfc) { div(q) }
public operator fun Quaternionf.divAssign(s: Float) { div(s) }
public infix fun Quaternionfc.difference(q: Quaternionfc): Quaternionf = difference(q, Quaternionf())
