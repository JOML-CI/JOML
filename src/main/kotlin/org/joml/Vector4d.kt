package org.joml

import java.nio.ByteBuffer
import java.nio.DoubleBuffer

/* Operators */
public operator fun Vector4dc.component1(): Double = x()
public operator fun Vector4dc.component2(): Double = y()
public operator fun Vector4dc.component3(): Double = z()
public operator fun Vector4dc.component4(): Double = w()
public operator fun Vector4dc.plus(v: Vector4dc): Vector4d = add(v, Vector4d())
public operator fun Vector4dc.plus(v: Vector4fc): Vector4d = add(v, Vector4d())
public operator fun Vector4d.plusAssign(v: Vector4dc) { add(v) }
public operator fun Vector4d.plusAssign(v: Vector4fc) { add(v) }
public operator fun Vector4dc.minus(v: Vector4dc): Vector4d = sub(v, Vector4d())
public operator fun Vector4dc.minus(v: Vector4fc): Vector4d = sub(v, Vector4d())
public operator fun Vector4d.minusAssign(v: Vector4dc) { sub(v) }
public operator fun Vector4d.minusAssign(v: Vector4fc) { sub(v) }
public operator fun Vector4dc.times(v: Vector4dc): Vector4d = mul(v, Vector4d())
public operator fun Vector4dc.times(v: Vector4fc): Vector4d = mul(v, Vector4d())
public operator fun Vector4dc.times(s: Double): Vector4d = mul(s, Vector4d())
public operator fun Vector4dc.times(m: Matrix4dc): Vector4d = mul(m, Vector4d())
public operator fun Vector4dc.times(m: Matrix4x3dc): Vector4d = mul(m, Vector4d())
public operator fun Vector4dc.times(m: Matrix4fc): Vector4d = mul(m, Vector4d())
public operator fun Vector4dc.times(m: Matrix4x3fc): Vector4d = mul(m, Vector4d())
public operator fun Vector4d.timesAssign(v: Vector4dc) { mul(v) }
public operator fun Vector4d.timesAssign(v: Vector4fc) { mul(v) }
public operator fun Vector4d.timesAssign(s: Double) { mul(s) }
public operator fun Vector4d.timesAssign(m: Matrix4dc) { mul(m) }
public operator fun Vector4d.timesAssign(m: Matrix4x3dc) { mul(m) }
public operator fun Vector4d.timesAssign(m: Matrix4fc) { mul(m) }
public operator fun Vector4d.timesAssign(m: Matrix4x3fc) { mul(m) }
public operator fun Vector4dc.div(v: Vector4dc): Vector4d = div(v, Vector4d())
public operator fun Vector4dc.div(s: Double): Vector4d = div(s, Vector4d())
public operator fun Vector4d.divAssign(v: Vector4dc) { div(v) }
public operator fun Vector4d.divAssign(s: Double) { div(s) }
public operator fun Vector4dc.unaryMinus(): Vector4d = negate(Vector4d())
public infix fun Vector4dc.dot(v: Vector4dc): Double = dot(v)
public infix fun Vector4dc.distance(v: Vector4dc): Double = distance(v)
public infix fun Vector4dc.distanceSquared(v: Vector4dc): Double = distanceSquared(v)
public infix fun Vector4dc.angleCos(v: Vector4dc): Double = angleCos(v)
public infix fun Vector4dc.angle(v: Vector4dc): Double = angle(v)

/* Buffer Operations */
public fun ByteBuffer.getVector4d(): Vector4d = Vector4d(this)
public fun ByteBuffer.getVector4d(index: Int): Vector4d = Vector4d(index, this)
public fun ByteBuffer.getVector4d(v: Vector4d): Vector4d = v.set(this)
public fun ByteBuffer.getVector4d(index: Int, v: Vector4d): Vector4d = v.set(index, this)
public fun ByteBuffer.putVector4d(v: Vector4d): ByteBuffer = v.get(this)
public fun ByteBuffer.putVector4d(index: Int, v: Vector4d): ByteBuffer = v.get(index, this)
public fun DoubleBuffer.getVector4d(): Vector4d = Vector4d(this)
public fun DoubleBuffer.getVector4d(index: Int): Vector4d = Vector4d(index, this)
public fun DoubleBuffer.getVector4d(v: Vector4d): Vector4d = v.set(this)
public fun DoubleBuffer.getVector4d(index: Int, v: Vector4d): Vector4d = v.set(index, this)
public fun DoubleBuffer.putVector4d(v: Vector4d): DoubleBuffer = v.get(this)
public fun DoubleBuffer.putVector4d(index: Int, v: Vector4d): DoubleBuffer = v.get(index, this)
