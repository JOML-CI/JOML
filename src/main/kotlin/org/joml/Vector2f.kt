package org.joml

import java.nio.ByteBuffer
import java.nio.FloatBuffer

/* Operators */
public operator fun Vector2fc.component1(): Float = x()
public operator fun Vector2fc.component2(): Float = y()
public operator fun Vector2fc.plus(v: Vector2fc): Vector2f = add(v, Vector2f())
public operator fun Vector2f.plusAssign(v: Vector2fc) { add(v) }
public operator fun Vector2fc.minus(v: Vector2fc): Vector2f = sub(v, Vector2f())
public operator fun Vector2f.minusAssign(v: Vector2fc) { sub(v) }
public operator fun Vector2fc.times(v: Vector2fc): Vector2f = mul(v, Vector2f())
public operator fun Vector2fc.times(s: Float): Vector2f = mul(s, Vector2f())
public operator fun Vector2fc.times(m: Matrix2fc): Vector2f = mul(m, Vector2f())
public operator fun Vector2f.timesAssign(v: Vector2fc) { mul(v) }
public operator fun Vector2f.timesAssign(s: Float) { mul(s) }
public operator fun Vector2f.timesAssign(m: Matrix2fc) { mul(m) }
public operator fun Vector2fc.div(v: Vector2fc): Vector2f = div(v, Vector2f())
public operator fun Vector2fc.div(s: Float): Vector2f = div(s, Vector2f())
public operator fun Vector2f.divAssign(v: Vector2fc) { div(v) }
public operator fun Vector2f.divAssign(s: Float) { div(s) }
public operator fun Vector2fc.unaryMinus(): Vector2f = negate(Vector2f())
public infix fun Vector2fc.dot(v: Vector2fc): Float = dot(v)
public infix fun Vector2fc.angle(v: Vector2fc): Float = angle(v)
public infix fun Vector2fc.distance(v: Vector2fc): Float = distance(v)
public infix fun Vector2fc.distanceSquared(v: Vector2fc): Float = distanceSquared(v)

/* Buffer Operations */
public fun ByteBuffer.getVector2f(): Vector2f = Vector2f(this)
public fun ByteBuffer.getVector2f(index: Int): Vector2f = Vector2f(index, this)
public fun ByteBuffer.getVector2f(v: Vector2f): Vector2f = v.set(this)
public fun ByteBuffer.getVector2f(index: Int, v: Vector2f): Vector2f = v.set(index, this)
public fun ByteBuffer.putVector2f(v: Vector2f): ByteBuffer = v.get(this)
public fun ByteBuffer.putVector2f(index: Int, v: Vector2f): ByteBuffer = v.get(index, this)
public fun FloatBuffer.getVector2f(): Vector2f = Vector2f(this)
public fun FloatBuffer.getVector2f(index: Int): Vector2f = Vector2f(index, this)
public fun FloatBuffer.getVector2f(v: Vector2f): Vector2f = v.set(this)
public fun FloatBuffer.getVector2f(index: Int, v: Vector2f): Vector2f = v.set(index, this)
public fun FloatBuffer.putVector2f(v: Vector2f): FloatBuffer = v.get(this)
public fun FloatBuffer.putVector2f(index: Int, v: Vector2f): FloatBuffer = v.get(index, this)
