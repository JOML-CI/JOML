package org.joml

import java.nio.ByteBuffer
import java.nio.DoubleBuffer

/* Operators */
public operator fun Vector3dc.component1(): Double = x()
public operator fun Vector3dc.component2(): Double = y()
public operator fun Vector3dc.component3(): Double = z()
public operator fun Vector3dc.plus(v: Vector3dc): Vector3d = add(v, Vector3d())
public operator fun Vector3dc.plus(v: Vector3fc): Vector3d = add(v, Vector3d())
public operator fun Vector3d.plusAssign(v: Vector3dc) { add(v) }
public operator fun Vector3d.plusAssign(v: Vector3fc) { add(v) }
public operator fun Vector3dc.minus(v: Vector3dc): Vector3d = sub(v, Vector3d())
public operator fun Vector3dc.minus(v: Vector3fc): Vector3d = sub(v, Vector3d())
public operator fun Vector3d.minusAssign(v: Vector3dc) { sub(v) }
public operator fun Vector3d.minusAssign(v: Vector3fc) { sub(v) }
public operator fun Vector3dc.times(v: Vector3dc): Vector3d = mul(v, Vector3d())
public operator fun Vector3dc.times(v: Vector3fc): Vector3d = mul(v, Vector3d())
public operator fun Vector3dc.times(s: Double): Vector3d = mul(s, Vector3d())
public operator fun Vector3dc.times(m: Matrix3dc): Vector3d = mul(m, Vector3d())
public operator fun Vector3dc.times(m: Matrix3x2dc): Vector3d = mul(m, Vector3d())
public operator fun Vector3dc.times(m: Matrix3fc): Vector3d = mul(m, Vector3d())
public operator fun Vector3dc.times(m: Matrix3x2fc): Vector3d = mul(m, Vector3d())
public operator fun Vector3d.timesAssign(v: Vector3dc) { mul(v) }
public operator fun Vector3d.timesAssign(v: Vector3fc) { mul(v) }
public operator fun Vector3d.timesAssign(s: Double) { mul(s) }
public operator fun Vector3d.timesAssign(m: Matrix3dc) { mul(m) }
public operator fun Vector3d.timesAssign(m: Matrix3x2dc) { mul(m) }
public operator fun Vector3d.timesAssign(m: Matrix3fc) { mul(m) }
public operator fun Vector3d.timesAssign(m: Matrix3x2fc) { mul(m) }
public operator fun Vector3dc.div(v: Vector3dc): Vector3d = div(v, Vector3d())
public operator fun Vector3dc.div(v: Vector3fc): Vector3d = div(v, Vector3d())
public operator fun Vector3dc.div(s: Double): Vector3d = div(s, Vector3d())
public operator fun Vector3d.divAssign(v: Vector3dc) { div(v) }
public operator fun Vector3d.divAssign(v: Vector3fc) { div(v) }
public operator fun Vector3d.divAssign(s: Double) { div(s) }
public operator fun Vector3dc.unaryMinus(): Vector3d = negate(Vector3d())
public infix fun Vector3dc.dot(v: Vector3dc): Double = dot(v)
public infix fun Vector3dc.cross(v: Vector3dc): Vector3d = cross(v, Vector3d())
public infix fun Vector3dc.distance(v: Vector3dc): Double = distance(v)
public infix fun Vector3dc.distanceSquared(v: Vector3dc): Double = distanceSquared(v)
public infix fun Vector3dc.angleCos(v: Vector3dc): Double = angleCos(v)
public infix fun Vector3dc.angle(v: Vector3dc): Double = angle(v)

/* Buffer Operations */
public fun ByteBuffer.getVector3d(): Vector3d = Vector3d(this)
public fun ByteBuffer.getVector3d(index: Int): Vector3d = Vector3d(index, this)
public fun ByteBuffer.getVector3d(v: Vector3d): Vector3d = v.set(this)
public fun ByteBuffer.getVector3d(index: Int, v: Vector3d): Vector3d = v.set(index, this)
public fun ByteBuffer.putVector3d(v: Vector3d): ByteBuffer = v.get(this)
public fun ByteBuffer.putVector3d(index: Int, v: Vector3d): ByteBuffer = v.get(index, this)
public fun DoubleBuffer.getVector3d(): Vector3d = Vector3d(this)
public fun DoubleBuffer.getVector3d(index: Int): Vector3d = Vector3d(index, this)
public fun DoubleBuffer.getVector3d(v: Vector3d): Vector3d = v.set(this)
public fun DoubleBuffer.getVector3d(index: Int, v: Vector3d): Vector3d = v.set(index, this)
public fun DoubleBuffer.putVector3d(v: Vector3d): DoubleBuffer = v.get(this)
public fun DoubleBuffer.putVector3d(index: Int, v: Vector3d): DoubleBuffer = v.get(index, this)
