@file:Suppress("NOTHING_TO_INLINE")
@file:JvmSynthetic
package org.joml

import java.nio.ByteBuffer
import java.nio.FloatBuffer

/* Operators */
public inline operator fun Vector4fc.component1(): Float = x()
public inline operator fun Vector4fc.component2(): Float = y()
public inline operator fun Vector4fc.component3(): Float = z()
public inline operator fun Vector4fc.component4(): Float = w()
public inline operator fun Vector4fc.plus(v: Vector4fc): Vector4f = add(v, Vector4f())
public inline operator fun Vector4f.plusAssign(v: Vector4fc) { add(v) }
public inline operator fun Vector4fc.minus(v: Vector4fc): Vector4f = sub(v, Vector4f())
public inline operator fun Vector4f.minusAssign(v: Vector4fc) { sub(v) }
public inline operator fun Vector4fc.times(v: Vector4fc): Vector4f = mul(v, Vector4f())
public inline operator fun Vector4fc.times(s: Float): Vector4f = mul(s, Vector4f())
public inline operator fun Vector4fc.times(m: Matrix4fc): Vector4f = mul(m, Vector4f())
public inline operator fun Vector4fc.times(m: Matrix4x3fc): Vector4f = mul(m, Vector4f())
public inline operator fun Vector4f.timesAssign(v: Vector4fc) { mul(v) }
public inline operator fun Vector4f.timesAssign(s: Float) { mul(s) }
public inline operator fun Vector4f.timesAssign(m: Matrix4fc) { mul(m) }
public inline operator fun Vector4f.timesAssign(m: Matrix4x3fc) { mul(m) }
public inline operator fun Vector4fc.div(v: Vector4fc): Vector4f = div(v, Vector4f())
public inline operator fun Vector4fc.div(s: Float): Vector4f = div(s, Vector4f())
public inline operator fun Vector4f.divAssign(v: Vector4fc) { div(v) }
public inline operator fun Vector4f.divAssign(s: Float) { div(s) }
public inline operator fun Vector4fc.unaryMinus(): Vector4f = negate(Vector4f())
public inline infix fun Vector4fc.dot(v: Vector4fc): Float = dot(v)
public inline infix fun Vector4fc.distance(v: Vector4fc): Float = distance(v)
public inline infix fun Vector4fc.distanceSquared(v: Vector4fc): Float = distanceSquared(v)
public inline infix fun Vector4fc.angleCos(v: Vector4fc): Float = angleCos(v)
public inline infix fun Vector4fc.angle(v: Vector4fc): Float = angle(v)

/* Buffer Operations */
public inline fun ByteBuffer.getVector4f(): Vector4f = Vector4f(this)
public inline fun ByteBuffer.getVector4f(index: Int): Vector4f = Vector4f(index, this)
public inline fun ByteBuffer.getVector4f(v: Vector4f): Vector4f = v.set(this)
public inline fun ByteBuffer.getVector4f(index: Int, v: Vector4f): Vector4f = v.set(index, this)
public inline fun ByteBuffer.putVector4f(v: Vector4f): ByteBuffer = v.get(this)
public inline fun ByteBuffer.putVector4f(index: Int, v: Vector4f): ByteBuffer = v.get(index, this)
public inline fun FloatBuffer.getVector4f(): Vector4f = Vector4f(this)
public inline fun FloatBuffer.getVector4f(index: Int): Vector4f = Vector4f(index, this)
public inline fun FloatBuffer.getVector4f(v: Vector4f): Vector4f = v.set(this)
public inline fun FloatBuffer.getVector4f(index: Int, v: Vector4f): Vector4f = v.set(index, this)
public inline fun FloatBuffer.putVector4f(v: Vector4f): FloatBuffer = v.get(this)
public inline fun FloatBuffer.putVector4f(index: Int, v: Vector4f): FloatBuffer = v.get(index, this)
