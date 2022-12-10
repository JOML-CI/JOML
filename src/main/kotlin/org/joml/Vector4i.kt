package org.joml

import java.nio.ByteBuffer
import java.nio.IntBuffer

/* Operators */
public operator fun Vector4ic.component1(): Int = x()
public operator fun Vector4ic.component2(): Int = y()
public operator fun Vector4ic.component3(): Int = z()
public operator fun Vector4ic.component4(): Int = w()
public operator fun Vector4ic.plus(v: Vector4ic): Vector4i = add(v, Vector4i())
public operator fun Vector4i.plusAssign(v: Vector4ic) { add(v) }
public operator fun Vector4ic.minus(v: Vector4ic): Vector4i = sub(v, Vector4i())
public operator fun Vector4i.minusAssign(v: Vector4ic) { sub(v) }
public operator fun Vector4ic.times(v: Vector4ic): Vector4i = mul(v, Vector4i())
public operator fun Vector4ic.times(s: Int): Vector4i = mul(s, Vector4i())
public operator fun Vector4i.timesAssign(v: Vector4ic) { mul(v) }
public operator fun Vector4i.timesAssign(s: Int) { mul(s) }
public operator fun Vector4ic.div(v: Vector4ic): Vector4i = div(v, Vector4i())
public operator fun Vector4ic.div(s: Float): Vector4i = div(s, Vector4i())
public operator fun Vector4ic.div(s: Int): Vector4i = div(s, Vector4i())
public operator fun Vector4i.divAssign(v: Vector4ic) { div(v) }
public operator fun Vector4i.divAssign(s: Float) { div(s) }
public operator fun Vector4i.divAssign(s: Int) { div(s) }
public operator fun Vector4ic.unaryMinus(): Vector4i = negate(Vector4i())
public infix fun Vector4ic.dot(v: Vector4ic): Long = dot(v)
public infix fun Vector4ic.distance(v: Vector4ic): Double = distance(v)
public infix fun Vector4ic.distanceSquared(v: Vector4ic): Long = distanceSquared(v)
public infix fun Vector4ic.gridDistance(v: Vector4ic): Long = gridDistance(v)

/* Buffer Operations */
public fun ByteBuffer.getVector4i(): Vector4i = Vector4i(this)
public fun ByteBuffer.getVector4i(index: Int): Vector4i = Vector4i(index, this)
public fun ByteBuffer.getVector4i(v: Vector4i): Vector4i = v.set(this)
public fun ByteBuffer.getVector4i(index: Int, v: Vector4i): Vector4i = v.set(index, this)
public fun ByteBuffer.putVector4i(v: Vector4i): ByteBuffer = v.get(this)
public fun ByteBuffer.putVector4i(index: Int, v: Vector4i): ByteBuffer = v.get(index, this)
public fun IntBuffer.getVector4i(): Vector4i = Vector4i(this)
public fun IntBuffer.getVector4i(index: Int): Vector4i = Vector4i(index, this)
public fun IntBuffer.getVector4i(v: Vector4i): Vector4i = v.set(this)
public fun IntBuffer.getVector4i(index: Int, v: Vector4i): Vector4i = v.set(index, this)
public fun IntBuffer.putVector4i(v: Vector4i): IntBuffer = v.get(this)
public fun IntBuffer.putVector4i(index: Int, v: Vector4i): IntBuffer = v.get(index, this)
