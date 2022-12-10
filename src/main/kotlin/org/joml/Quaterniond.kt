package org.joml

/* Operators */
public operator fun Quaterniondc.plus(q: Quaterniondc): Quaterniond = add(q, Quaterniond())
public operator fun Quaterniond.plusAssign(q: Quaterniondc) { add(q) }
public operator fun Quaterniondc.minus(q: Quaterniondc): Quaterniond = sub(q, Quaterniond())
public operator fun Quaterniond.minusAssign(q: Quaterniondc) { sub(q) }
public operator fun Quaterniondc.times(q: Quaterniondc): Quaterniond = mul(q, Quaterniond())
public operator fun Quaterniondc.times(s: Double): Quaterniond = mul(s, Quaterniond())
public operator fun Quaterniondc.times(v: Vector4dc): Vector4d = transform(v, Vector4d())
public operator fun Quaterniondc.times(v: Vector4fc): Vector4f = transform(v, Vector4f())
public operator fun Quaterniondc.times(v: Vector3dc): Vector3d = transform(v, Vector3d())
public operator fun Quaterniondc.times(v: Vector3fc): Vector3f = transform(v, Vector3f())
public operator fun Quaterniond.timesAssign(q: Quaterniondc) { mul(q) }
public operator fun Quaterniond.timesAssign(s: Double) { mul(s) }
public operator fun Quaterniondc.div(q: Quaterniondc): Quaterniond = div(q, Quaterniond())
public operator fun Quaterniondc.div(s: Double): Quaterniond = div(s, Quaterniond())
public operator fun Quaterniond.divAssign(q: Quaterniondc) { div(q) }
public operator fun Quaterniond.divAssign(s: Double) { div(s) }
public infix fun Quaterniondc.difference(q: Quaterniondc): Quaterniond = difference(q, Quaterniond())
