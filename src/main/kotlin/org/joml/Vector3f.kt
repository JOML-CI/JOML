@file:Suppress("NOTHING_TO_INLINE")
@file:JvmName("-Vector3f")
package org.joml

import java.nio.ByteBuffer
import java.nio.FloatBuffer

/* Operators */
public inline operator fun Vector3fc.component1(): Float = x()
public inline operator fun Vector3fc.component2(): Float = y()
public inline operator fun Vector3fc.component3(): Float = z()
public inline operator fun Vector3fc.plus(v: Vector3fc): Vector3f = add(v, Vector3f())
public inline operator fun Vector3f.plusAssign(v: Vector3fc) { add(v) }
public inline operator fun Vector3fc.minus(v: Vector3fc): Vector3f = sub(v, Vector3f())
public inline operator fun Vector3f.minusAssign(v: Vector3fc) { sub(v) }
public inline operator fun Vector3fc.times(v: Vector3fc): Vector3f = mul(v, Vector3f())
public inline operator fun Vector3fc.times(s: Float): Vector3f = mul(s, Vector3f())
public inline operator fun Vector3fc.times(m: Matrix3fc): Vector3f = mul(m, Vector3f())
public inline operator fun Vector3fc.times(m: Matrix3x2fc): Vector3f = mul(m, Vector3f())
public inline operator fun Vector3fc.times(m: Matrix3dc): Vector3f = mul(m, Vector3f())
public inline operator fun Vector3fc.times(m: Matrix3x2dc): Vector3f = mul(m, Vector3f())
public inline operator fun Vector3f.timesAssign(v: Vector3fc) { mul(v) }
public inline operator fun Vector3f.timesAssign(s: Float) { mul(s) }
public inline operator fun Vector3f.timesAssign(m: Matrix3fc) { mul(m) }
public inline operator fun Vector3f.timesAssign(m: Matrix3x2fc) { mul(m) }
public inline operator fun Vector3f.timesAssign(m: Matrix3dc) { mul(m) }
public inline operator fun Vector3f.timesAssign(m: Matrix3x2dc) { mul(m) }
public inline operator fun Vector3fc.div(v: Vector3fc): Vector3f = div(v, Vector3f())
public inline operator fun Vector3fc.div(s: Float): Vector3f = div(s, Vector3f())
public inline operator fun Vector3f.divAssign(v: Vector3fc) { div(v) }
public inline operator fun Vector3f.divAssign(s: Float) { div(s) }
public inline operator fun Vector3fc.unaryMinus(): Vector3f = negate(Vector3f())
public inline infix fun Vector3fc.dot(v: Vector3fc): Float = dot(v)
public inline infix fun Vector3fc.cross(v: Vector3fc): Vector3f = cross(v, Vector3f())
public inline infix fun Vector3fc.distance(v: Vector3fc): Float = distance(v)
public inline infix fun Vector3fc.distanceSquared(v: Vector3fc): Float = distanceSquared(v)
public inline infix fun Vector3fc.angleCos(v: Vector3fc): Float = angleCos(v)
public inline infix fun Vector3fc.angle(v: Vector3fc): Float = angle(v)

/* Buffer Operations */
public inline fun ByteBuffer.getVector3f(): Vector3f = Vector3f(this)
public inline fun ByteBuffer.getVector3f(index: Int): Vector3f = Vector3f(index, this)
public inline fun ByteBuffer.getVector3f(v: Vector3f): Vector3f = v.set(this)
public inline fun ByteBuffer.getVector3f(index: Int, v: Vector3f): Vector3f = v.set(index, this)
public inline fun ByteBuffer.putVector3f(v: Vector3f): ByteBuffer = v.get(this)
public inline fun ByteBuffer.putVector3f(index: Int, v: Vector3f): ByteBuffer = v.get(index, this)
public inline fun FloatBuffer.getVector3f(): Vector3f = Vector3f(this)
public inline fun FloatBuffer.getVector3f(index: Int): Vector3f = Vector3f(index, this)
public inline fun FloatBuffer.getVector3f(v: Vector3f): Vector3f = v.set(this)
public inline fun FloatBuffer.getVector3f(index: Int, v: Vector3f): Vector3f = v.set(index, this)
public inline fun FloatBuffer.putVector3f(v: Vector3f): FloatBuffer = v.get(this)
public inline fun FloatBuffer.putVector3f(index: Int, v: Vector3f): FloatBuffer = v.get(index, this)
