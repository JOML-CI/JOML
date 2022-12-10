package org.joml

import java.nio.ByteBuffer
import java.nio.IntBuffer

/* Operators */
public operator fun Vector3ic.component1(): Int = x()
public operator fun Vector3ic.component2(): Int = y()
public operator fun Vector3ic.component3(): Int = z()
public operator fun Vector3ic.plus(v: Vector3ic): Vector3i = add(v, Vector3i())
public operator fun Vector3i.plusAssign(v: Vector3ic) { add(v) }
public operator fun Vector3ic.minus(v: Vector3ic): Vector3i = sub(v, Vector3i())
public operator fun Vector3i.minusAssign(v: Vector3ic) { sub(v) }
public operator fun Vector3ic.times(v: Vector3ic): Vector3i = mul(v, Vector3i())
public operator fun Vector3ic.times(s: Int): Vector3i = mul(s, Vector3i())
public operator fun Vector3i.timesAssign(v: Vector3ic) { mul(v) }
public operator fun Vector3i.timesAssign(s: Int) { mul(s) }
public operator fun Vector3ic.div(s: Float): Vector3i = div(s, Vector3i())
public operator fun Vector3ic.div(s: Int): Vector3i = div(s, Vector3i())
public operator fun Vector3i.divAssign(s: Float) { div(s) }
public operator fun Vector3i.divAssign(s: Int) { div(s) }
public operator fun Vector3ic.unaryMinus(): Vector3i = negate(Vector3i())
public infix fun Vector3ic.distance(v: Vector3ic): Double = distance(v)
public infix fun Vector3ic.distanceSquared(v: Vector3ic): Long = distanceSquared(v)
public infix fun Vector3ic.gridDistance(v: Vector3ic): Long = gridDistance(v)

/* Buffer Operations */
public fun ByteBuffer.getVector3i(): Vector3i = Vector3i(this)
public fun ByteBuffer.getVector3i(index: Int): Vector3i = Vector3i(index, this)
public fun ByteBuffer.getVector3i(v: Vector3i): Vector3i = v.set(this)
public fun ByteBuffer.getVector3i(index: Int, v: Vector3i): Vector3i = v.set(index, this)
public fun ByteBuffer.putVector3i(v: Vector3i): ByteBuffer = v.get(this)
public fun ByteBuffer.putVector3i(index: Int, v: Vector3i): ByteBuffer = v.get(index, this)
public fun IntBuffer.getVector3i(): Vector3i = Vector3i(this)
public fun IntBuffer.getVector3i(index: Int): Vector3i = Vector3i(index, this)
public fun IntBuffer.getVector3i(v: Vector3i): Vector3i = v.set(this)
public fun IntBuffer.getVector3i(index: Int, v: Vector3i): Vector3i = v.set(index, this)
public fun IntBuffer.putVector3i(v: Vector3i): IntBuffer = v.get(this)
public fun IntBuffer.putVector3i(index: Int, v: Vector3i): IntBuffer = v.get(index, this)
