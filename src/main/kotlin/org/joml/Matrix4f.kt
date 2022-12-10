package org.joml

import java.nio.ByteBuffer
import java.nio.FloatBuffer

/* Operators */
public operator fun Matrix4fc.plus(m: Matrix4fc): Matrix4f = add(m, Matrix4f())
public operator fun Matrix4f.plusAssign(m: Matrix4fc) { add(m) }
public operator fun Matrix4fc.minus(m: Matrix4fc): Matrix4f = sub(m, Matrix4f())
public operator fun Matrix4f.minusAssign(m: Matrix4fc) { sub(m) }
public operator fun Matrix4fc.times(m: Matrix4fc): Matrix4f = mul(m, Matrix4f())
public operator fun Matrix4fc.times(q: Quaternionfc): Matrix4f = rotate(q, Matrix4f())
public operator fun Matrix4fc.times(a: AxisAngle4f): Matrix4f = rotate(a, Matrix4f())
public operator fun Matrix4fc.times(v: Vector4fc): Vector4f = transform(v, Vector4f())
public operator fun Matrix4f.timesAssign(m: Matrix4fc) { mul(m) }
public operator fun Matrix4f.timesAssign(q: Quaternionfc) { rotate(q) }
public operator fun Matrix4f.timesAssign(a: AxisAngle4f) { rotate(a) }
public infix fun Matrix4fc.mulComponentWise(m: Matrix4fc): Matrix4f = mulComponentWise(m, Matrix4f())

/* Buffer Operations */
public fun ByteBuffer.getMatrix4f(v: Matrix4f): Matrix4f = v.set(this)
public fun ByteBuffer.putMatrix4f(v: Matrix4f): ByteBuffer = v.get(this)
public fun ByteBuffer.putMatrix4f(index: Int, v: Matrix4f): ByteBuffer = v.get(index, this)
public fun FloatBuffer.getMatrix4f(): Matrix4f = Matrix4f(this)
public fun FloatBuffer.getMatrix4f(v: Matrix4f): Matrix4f = v.set(this)
public fun FloatBuffer.putMatrix4f(v: Matrix4f): FloatBuffer = v.get(this)
public fun FloatBuffer.putMatrix4f(index: Int, v: Matrix4f): FloatBuffer = v.get(index, this)
