package org.joml

import java.nio.ByteBuffer
import java.nio.IntBuffer

/* Operators */
public operator fun Vector2ic.component1(): Int = x()
public operator fun Vector2ic.component2(): Int = y()
public operator fun Vector2ic.plus(v: Vector2ic): Vector2i = add(v, Vector2i())
public operator fun Vector2i.plusAssign(v: Vector2ic) { add(v) }
public operator fun Vector2ic.minus(v: Vector2ic): Vector2i = sub(v, Vector2i())
public operator fun Vector2i.minusAssign(v: Vector2ic) { sub(v) }
public operator fun Vector2ic.times(v: Vector2ic): Vector2i = mul(v, Vector2i())
public operator fun Vector2ic.times(s: Int): Vector2i = mul(s, Vector2i())
public operator fun Vector2i.timesAssign(v: Vector2ic) { mul(v) }
public operator fun Vector2i.timesAssign(s: Int) { mul(s) }
public operator fun Vector2ic.div(s: Float): Vector2i = div(s, Vector2i())
public operator fun Vector2ic.div(s: Int): Vector2i = div(s, Vector2i())
public operator fun Vector2i.divAssign(s: Float) { div(s) }
public operator fun Vector2i.divAssign(s: Int) { div(s) }
public operator fun Vector2ic.unaryMinus(): Vector2i = negate(Vector2i())
public infix fun Vector2ic.distance(v: Vector2ic): Double = distance(v)
public infix fun Vector2ic.distanceSquared(v: Vector2ic): Long = distanceSquared(v)
public infix fun Vector2ic.gridDistance(v: Vector2ic): Long = gridDistance(v)

/* Buffer Operations */
public fun ByteBuffer.getVector2i(): Vector2i = Vector2i(this)
public fun ByteBuffer.getVector2i(index: Int): Vector2i = Vector2i(index, this)
public fun ByteBuffer.getVector2i(v: Vector2i): Vector2i = v.set(this)
public fun ByteBuffer.getVector2i(index: Int, v: Vector2i): Vector2i = v.set(index, this)
public fun ByteBuffer.putVector2i(v: Vector2i): ByteBuffer = v.get(this)
public fun ByteBuffer.putVector2i(index: Int, v: Vector2i): ByteBuffer = v.get(index, this)
public fun IntBuffer.getVector2i(): Vector2i = Vector2i(this)
public fun IntBuffer.getVector2i(index: Int): Vector2i = Vector2i(index, this)
public fun IntBuffer.getVector2i(v: Vector2i): Vector2i = v.set(this)
public fun IntBuffer.getVector2i(index: Int, v: Vector2i): Vector2i = v.set(index, this)
public fun IntBuffer.putVector2i(v: Vector2i): IntBuffer = v.get(this)
public fun IntBuffer.putVector2i(index: Int, v: Vector2i): IntBuffer = v.get(index, this)
