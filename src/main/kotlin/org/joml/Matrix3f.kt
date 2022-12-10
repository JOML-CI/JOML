package org.joml

import java.nio.ByteBuffer
import java.nio.FloatBuffer

/* Operators */
public operator fun Matrix3fc.plus(m: Matrix3fc): Matrix3f = add(m, Matrix3f())
public operator fun Matrix3f.plusAssign(m: Matrix3fc) { add(m) }
public operator fun Matrix3fc.minus(m: Matrix3fc): Matrix3f = sub(m, Matrix3f())
public operator fun Matrix3f.minusAssign(m: Matrix3fc) { sub(m) }
public operator fun Matrix3fc.times(m: Matrix3fc): Matrix3f = mul(m, Matrix3f())
public operator fun Matrix3fc.times(q: Quaternionfc): Matrix3f = rotate(q, Matrix3f())
public operator fun Matrix3fc.times(a: AxisAngle4f): Matrix3f = rotate(a, Matrix3f())
public operator fun Matrix3fc.times(v: Vector3fc): Vector3f = transform(v, Vector3f())
public operator fun Matrix3f.timesAssign(m: Matrix3fc) { mul(m) }
public operator fun Matrix3f.timesAssign(q: Quaternionfc) { rotate(q) }
public operator fun Matrix3f.timesAssign(a: AxisAngle4f) { rotate(a) }
public infix fun Matrix3fc.mulComponentWise(m: Matrix3fc): Matrix3f = mulComponentWise(m, Matrix3f())

/* Buffer Operations */
public fun ByteBuffer.getMatrix3f(v: Matrix3f): Matrix3f = v.set(this)
public fun ByteBuffer.putMatrix3f(v: Matrix3f): ByteBuffer = v.get(this)
public fun ByteBuffer.putMatrix3f(index: Int, v: Matrix3f): ByteBuffer = v.get(index, this)
public fun FloatBuffer.getMatrix3f(): Matrix3f = Matrix3f(this)
public fun FloatBuffer.getMatrix3f(v: Matrix3f): Matrix3f = v.set(this)
public fun FloatBuffer.putMatrix3f(v: Matrix3f): FloatBuffer = v.get(this)
public fun FloatBuffer.putMatrix3f(index: Int, v: Matrix3f): FloatBuffer = v.get(index, this)
