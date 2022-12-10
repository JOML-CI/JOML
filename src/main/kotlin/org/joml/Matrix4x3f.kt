package org.joml

import java.nio.ByteBuffer
import java.nio.FloatBuffer

/* Operators */
public operator fun Matrix4x3fc.plus(m: Matrix4x3fc): Matrix4x3f = add(m, Matrix4x3f())
public operator fun Matrix4x3f.plusAssign(m: Matrix4x3fc) { add(m) }
public operator fun Matrix4x3fc.minus(m: Matrix4x3fc): Matrix4x3f = sub(m, Matrix4x3f())
public operator fun Matrix4x3f.minusAssign(m: Matrix4x3fc) { sub(m) }
public operator fun Matrix4x3fc.times(m: Matrix4x3fc): Matrix4x3f = mul(m, Matrix4x3f())
public operator fun Matrix4x3fc.times(q: Quaternionfc): Matrix4x3f = rotate(q, Matrix4x3f())
public operator fun Matrix4x3fc.times(a: AxisAngle4f): Matrix4x3f = rotate(a, Matrix4x3f())
public operator fun Matrix4x3fc.times(v: Vector4fc): Vector4f = transform(v, Vector4f())
public operator fun Matrix4x3f.timesAssign(m: Matrix4x3fc) { mul(m) }
public operator fun Matrix4x3f.timesAssign(q: Quaternionfc) { rotate(q) }
public operator fun Matrix4x3f.timesAssign(a: AxisAngle4f) { rotate(a) }
public infix fun Matrix4x3fc.mulComponentWise(m: Matrix4x3fc): Matrix4x3f = mulComponentWise(m, Matrix4x3f())

/* Buffer Operations */
public fun ByteBuffer.getMatrix4x3f(v: Matrix4x3f): Matrix4x3f = v.set(this)
public fun ByteBuffer.putMatrix4x3f(v: Matrix4x3f): ByteBuffer = v.get(this)
public fun ByteBuffer.putMatrix4x3f(index: Int, v: Matrix4x3f): ByteBuffer = v.get(index, this)
public fun FloatBuffer.getMatrix4x3f(): Matrix4x3f = Matrix4x3f(this)
public fun FloatBuffer.getMatrix4x3f(v: Matrix4x3f): Matrix4x3f = v.set(this)
public fun FloatBuffer.putMatrix4x3f(v: Matrix4x3f): FloatBuffer = v.get(this)
public fun FloatBuffer.putMatrix4x3f(index: Int, v: Matrix4x3f): FloatBuffer = v.get(index, this)
