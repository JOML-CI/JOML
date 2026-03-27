@file:Suppress("NOTHING_TO_INLINE")
@file:JvmSynthetic
package org.joml

import java.nio.ByteBuffer
import java.nio.DoubleBuffer

/* Operators */
public inline operator fun Vector2dc.component1(): Double = x()
public inline operator fun Vector2dc.component2(): Double = y()
public inline operator fun Vector2dc.plus(v: Vector2dc): Vector2d = add(v, Vector2d())
public inline operator fun Vector2dc.plus(v: Vector2fc): Vector2d = add(v, Vector2d())
public inline operator fun Vector2d.plusAssign(v: Vector2dc) { add(v) }
public inline operator fun Vector2d.plusAssign(v: Vector2fc) { add(v) }
public inline operator fun Vector2dc.minus(v: Vector2dc): Vector2d = sub(v, Vector2d())
public inline operator fun Vector2dc.minus(v: Vector2fc): Vector2d = sub(v, Vector2d())
public inline operator fun Vector2d.minusAssign(v: Vector2dc) { sub(v) }
public inline operator fun Vector2d.minusAssign(v: Vector2fc) { sub(v) }
public inline operator fun Vector2dc.times(v: Vector2dc): Vector2d = mul(v, Vector2d())
public inline operator fun Vector2dc.times(s: Double): Vector2d = mul(s, Vector2d())
public inline operator fun Vector2dc.times(m: Matrix2dc): Vector2d = mul(m, Vector2d())
public inline operator fun Vector2dc.times(m: Matrix2fc): Vector2d = mul(m, Vector2d())
public inline operator fun Vector2d.timesAssign(v: Vector2dc) { mul(v) }
public inline operator fun Vector2d.timesAssign(s: Double) { mul(s) }
public inline operator fun Vector2d.timesAssign(m: Matrix2dc) { mul(m) }
public inline operator fun Vector2d.timesAssign(m: Matrix2fc) { mul(m) }
public inline operator fun Vector2dc.div(v: Vector2dc): Vector2d = div(v, Vector2d())
public inline operator fun Vector2dc.div(v: Vector2fc): Vector2d = div(v, Vector2d())
public inline operator fun Vector2d.divAssign(v: Vector2dc) { div(v) }
public inline operator fun Vector2d.divAssign(v: Vector2fc) { div(v) }
public inline operator fun Vector2dc.unaryMinus(): Vector2d = negate(Vector2d())
public inline infix fun Vector2dc.dot(v: Vector2dc): Double = dot(v)
public inline infix fun Vector2dc.angle(v: Vector2dc): Double = angle(v)
public inline infix fun Vector2dc.distance(v: Vector2dc): Double = distance(v)
public inline infix fun Vector2dc.distance(v: Vector2fc): Double = distance(v)
public inline infix fun Vector2dc.distanceSquared(v: Vector2dc): Double = distanceSquared(v)
public inline infix fun Vector2dc.distanceSquared(v: Vector2fc): Double = distanceSquared(v)

/* Buffer Operations */
public inline fun ByteBuffer.getVector2d(): Vector2d = Vector2d(this)
public inline fun ByteBuffer.getVector2d(index: Int): Vector2d = Vector2d(index, this)
public inline fun ByteBuffer.getVector2d(v: Vector2d): Vector2d = v.set(this)
public inline fun ByteBuffer.getVector2d(index: Int, v: Vector2d): Vector2d = v.set(index, this)
public inline fun ByteBuffer.putVector2d(v: Vector2d): ByteBuffer = v.get(this)
public inline fun ByteBuffer.putVector2d(index: Int, v: Vector2d): ByteBuffer = v.get(index, this)
public inline fun DoubleBuffer.getVector2d(): Vector2d = Vector2d(this)
public inline fun DoubleBuffer.getVector2d(index: Int): Vector2d = Vector2d(index, this)
public inline fun DoubleBuffer.getVector2d(v: Vector2d): Vector2d = v.set(this)
public inline fun DoubleBuffer.getVector2d(index: Int, v: Vector2d): Vector2d = v.set(index, this)
public inline fun DoubleBuffer.putVector2d(v: Vector2d): DoubleBuffer = v.get(this)
public inline fun DoubleBuffer.putVector2d(index: Int, v: Vector2d): DoubleBuffer = v.get(index, this)
