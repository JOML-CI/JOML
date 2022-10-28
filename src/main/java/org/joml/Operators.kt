package org.joml

/* Matrix2f */

operator fun Matrix2f.get(c: Int, r: Int): Float = get(c, r)
operator fun Matrix2f.minusAssign(m: Matrix2fc) {sub(m)}
operator fun Matrix2fc.minus(m: Matrix2fc) = sub(m,Matrix2f())
operator fun Matrix2f.plusAssign(m: Matrix2fc) {add(m)}
operator fun Matrix2fc.plus(m: Matrix2fc) = add(m,Matrix2f())
operator fun Matrix2f.timesAssign(m: Matrix2fc) {mul(m)}
operator fun Matrix2fc.times(m: Matrix2fc) = mul(m,Matrix2f())
operator fun Matrix2f.timesAssign(v: Vector2f) {transform(v)}
operator fun Matrix2fc.times(v: Vector2f) = transform(v, Vector2f())
infix fun Matrix2fc.transform(v: Vector2f) {transform(v)}

/* Matrix2d */

operator fun Matrix2d.get(c: Int, r: Int): Double = get(c, r)
operator fun Matrix2d.minusAssign(m: Matrix2dc) {sub(m)}
operator fun Matrix2dc.minus(m: Matrix2dc) = sub(m,Matrix2d())
operator fun Matrix2d.plusAssign(m: Matrix2dc) {add(m)}
operator fun Matrix2dc.plus(m: Matrix2dc) = add(m,Matrix2d())
operator fun Matrix2d.timesAssign(m: Matrix2dc) {mul(m)}
operator fun Matrix2dc.times(m: Matrix2dc) = mul(m,Matrix2d())
operator fun Matrix2d.timesAssign(m: Matrix2fc) {mul(m)}
operator fun Matrix2dc.times(m: Matrix2fc) = mul(m,Matrix2d())
operator fun Matrix2d.timesAssign(v: Vector2d) {transform(v)}
operator fun Matrix2dc.times(v: Vector2d) = transform(v,Vector2d())
infix fun Matrix2dc.transform(v: Vector2d) {transform(v)}

/* Matrix3f */

operator fun Matrix3f.get(c: Int, r: Int): Float = get(c, r)
operator fun Matrix3f.minusAssign(m: Matrix3f) {sub(m)}
operator fun Matrix3fc.minus(m: Matrix3f) = sub(m,Matrix3f())
operator fun Matrix3f.plusAssign(m: Matrix3fc) {add(m)}
operator fun Matrix3fc.plus(m: Matrix3fc) = add(m,Matrix3f())
operator fun Matrix3f.timesAssign(m: Matrix3fc) {mul(m)}
operator fun Matrix3fc.times(m: Matrix3fc) = mul(m,Matrix3f())
operator fun Matrix3f.timesAssign(v: Vector3f) {transform(v)}
operator fun Matrix3fc.times(v: Vector3f) = transform(v,Vector3f())
operator fun Matrix3f.timesAssign(q: Quaternionfc) {rotate(q)}
operator fun Matrix3fc.times(q: Quaternionfc) = rotate(q,Matrix3f())
infix fun Matrix3f.rotate(q: Quaternionfc) {rotate(q)}
infix fun Matrix3fc.transform(v: Vector3f) {transform(v)}

/* Matrix3d */

operator fun Matrix3d.get(c: Int, r: Int): Double = get(c, r)
operator fun Matrix3d.minusAssign(m: Matrix3d) {sub(m)}
operator fun Matrix3dc.minus(m: Matrix3d) = sub(m,Matrix3d())
operator fun Matrix3d.plusAssign(m: Matrix3dc) {add(m)}
operator fun Matrix3dc.plus(m: Matrix3dc) = add(m,Matrix3d())
operator fun Matrix3d.timesAssign(m: Matrix3dc) {mul(m)}
operator fun Matrix3dc.times(m: Matrix3dc) = mul(m,Matrix3d())
operator fun Matrix3d.timesAssign(m: Matrix3fc) {mul(m)}
operator fun Matrix3dc.times(m: Matrix3fc) = mul(m,Matrix3d())
operator fun Matrix3d.timesAssign(v: Vector3d) {transform(v)}
operator fun Matrix3dc.times(v: Vector3d) = transform(v,Vector3d())
operator fun Matrix3d.timesAssign(v: Vector3f) {transform(v)}
operator fun Matrix3dc.times(v: Vector3f) = transform(v,Vector3f())
operator fun Matrix3d.timesAssign(q: Quaternionfc) {rotate(q)}
operator fun Matrix3dc.times(q: Quaternionfc) = rotate(q,Matrix3d())
operator fun Matrix3d.timesAssign(q: Quaterniondc) {rotate(q)}
operator fun Matrix3dc.times(q: Quaterniondc) = rotate(q,Matrix3d())
infix fun Matrix3d.rotate(q: Quaternionfc) {rotate(q)}
infix fun Matrix3d.rotate(q: Quaterniondc) {rotate(q)}
infix fun Matrix3dc.transform(v: Vector3f) {transform(v)}
infix fun Matrix3dc.transform(v: Vector3d) {transform(v)}

/* Matrix4x3f */

operator fun Matrix4x3f.get(c: Int, r: Int): Float = get(c, r)
operator fun Matrix4x3f.minusAssign(m: Matrix4x3f) {sub(m)}
operator fun Matrix4x3fc.minus(m: Matrix4x3f) = sub(m,Matrix4x3f())
operator fun Matrix4x3f.plusAssign(m: Matrix4x3fc) {add(m)}
operator fun Matrix4x3fc.plus(m: Matrix4x3fc) = add(m,Matrix4x3f())
operator fun Matrix4x3f.timesAssign(m: Matrix4x3fc) {mul(m)}
operator fun Matrix4x3fc.times(m: Matrix4x3fc) = mul(m,Matrix4x3f())
operator fun Matrix4x3f.timesAssign(v: Vector4f) {transform(v)}
operator fun Matrix4x3fc.times(v: Vector4f) = transform(v,Vector4f())
operator fun Matrix4x3f.timesAssign(q: Quaternionfc) {rotate(q)}
operator fun Matrix4x3fc.times(q: Quaternionfc) = rotate(q,Matrix4x3f())
infix fun Matrix4x3f.rotate(q: Quaternionfc) {rotate(q)}
infix fun Matrix4x3fc.transform(v: Vector4f) {transform(v)}
infix fun Matrix4x3fc.transformPosition(v: Vector3f) {transformPosition(v)}
infix fun Matrix4x3fc.transformDirection(v: Vector3f) {transformDirection(v)}

/* Matrix4x3d */

operator fun Matrix4x3d.get(c: Int, r: Int): Double = get(c, r)
operator fun Matrix4x3d.minusAssign(m: Matrix4x3dc) {sub(m)}
operator fun Matrix4x3dc.minus(m: Matrix4x3dc) = sub(m,Matrix4x3d())
operator fun Matrix4x3d.plusAssign(m: Matrix4x3dc) {add(m)}
operator fun Matrix4x3dc.plus(m: Matrix4x3dc) = add(m,Matrix4x3d())
operator fun Matrix4x3d.timesAssign(m: Matrix4x3fc) {mul(m)}
operator fun Matrix4x3dc.times(m: Matrix4x3fc) = mul(m,Matrix4x3d())
operator fun Matrix4x3d.timesAssign(m: Matrix4x3dc) {mul(m)}
operator fun Matrix4x3dc.times(m: Matrix4x3dc) = mul(m,Matrix4x3d())
operator fun Matrix4x3d.timesAssign(v: Vector4d) {transform(v)}
operator fun Matrix4x3dc.times(v: Vector4d) = transform(v,Vector4d())
operator fun Matrix4x3d.timesAssign(q: Quaternionfc) {rotate(q)}
operator fun Matrix4x3dc.times(q: Quaternionfc) = rotate(q,Matrix4x3d())
operator fun Matrix4x3d.timesAssign(q: Quaterniondc) {rotate(q)}
operator fun Matrix4x3dc.times(q: Quaterniondc) = rotate(q,Matrix4x3d())
infix fun Matrix4x3d.rotate(q: Quaternionfc) {rotate(q)}
infix fun Matrix4x3d.rotate(q: Quaterniondc) {rotate(q)}
infix fun Matrix4x3dc.transform(v: Vector4d) {transform(v)}
infix fun Matrix4x3dc.transformPosition(v: Vector3d) {transformPosition(v)}
infix fun Matrix4x3dc.transformDirection(v: Vector3d) {transformDirection(v)}

/* Matrix4f */

operator fun Matrix4f.get(c: Int, r: Int): Float = get(c, r)
operator fun Matrix4f.minusAssign(m: Matrix4f) {sub(m)}
operator fun Matrix4fc.minus(m: Matrix4f) = sub(m,Matrix4f())
operator fun Matrix4f.plusAssign(m: Matrix4fc) {add(m)}
operator fun Matrix4fc.plus(m: Matrix4fc) = add(m,Matrix4f())
operator fun Matrix4f.timesAssign(m: Matrix4fc) {mul(m)}
operator fun Matrix4fc.times(m: Matrix4fc) = mul(m,Matrix4f())
operator fun Matrix4f.timesAssign(m: Matrix4x3fc) {mul(m, this)}
operator fun Matrix4f.timesAssign(v: Vector4f) {transform(v)}
operator fun Matrix4fc.times(v: Vector4f) = transform(v,Vector4f())
operator fun Matrix4f.timesAssign(q: Quaternionfc) {rotate(q)}
operator fun Matrix4fc.times(q: Quaternionfc) = rotate(q,Matrix4f())
infix fun Matrix4f.mulAffine(m: Matrix4fc) {this.mulAffine(m)}
infix fun Matrix4f.mulAffineR(m: Matrix4fc) {this.mulAffineR(m)}
infix fun Matrix4f.rotate(q: Quaternionfc) {rotate(q)}
infix fun Matrix4f.transform(v: Vector4f) {transform(v)}
infix fun Matrix4f.transformPosition(v: Vector3f) {transformPosition(v)}
infix fun Matrix4f.transformDirection(v: Vector3f) {transformDirection(v)}

/* Matrix4d */

operator fun Matrix4d.get(c: Int, r: Int): Double = get(c, r)
operator fun Matrix4d.minusAssign(m: Matrix4dc) {sub(m)}
operator fun Matrix4dc.minus(m: Matrix4dc) = sub(m,Matrix4d())
operator fun Matrix4d.plusAssign(m: Matrix4dc) {add(m)}
operator fun Matrix4dc.plus(m: Matrix4dc) = add(m,Matrix4d())
operator fun Matrix4d.timesAssign(m: Matrix4dc) {mul(m)}
operator fun Matrix4dc.times(m: Matrix4dc) = mul(m,Matrix4d())
operator fun Matrix4d.timesAssign(m: Matrix4x3fc) {mul(m, this)}
operator fun Matrix4d.timesAssign(m: Matrix4x3dc) {mul(m, this)}
operator fun Matrix4d.timesAssign(v: Vector4d) {transform(v)}
operator fun Matrix4dc.times(v: Vector4d) = transform(v,Vector4d())
operator fun Matrix4d.timesAssign(q: Quaternionfc) {rotate(q)}
operator fun Matrix4dc.times(q: Quaternionfc) = rotate(q,Matrix4d())
operator fun Matrix4d.timesAssign(q: Quaterniondc) {rotate(q)}
operator fun Matrix4dc.times(q: Quaterniondc) = rotate(q,Matrix4d())
infix fun Matrix4d.mulAffine(m: Matrix4dc) {this.mulAffine(m)}
infix fun Matrix4d.mulAffineR(m: Matrix4dc) {this.mulAffineR(m)}
infix fun Matrix4d.rotate(q: Quaternionfc) {rotate(q)}
infix fun Matrix4d.transform(v: Vector4d) {transform(v)}
infix fun Matrix4dc.transformPosition(v: Vector3d) {transformPosition(v)}
infix fun Matrix4dc.transformDirection(v: Vector3f) {transformDirection(v)}
infix fun Matrix4dc.transformDirection(v: Vector3d) {transformDirection(v)}

/* Vector2f */

operator fun Vector2f.get(e: Int): Float = get(e)
operator fun Vector2f.minusAssign(v: Vector2fc) {sub(v)}
operator fun Vector2fc.minus(v: Vector2fc) = sub(v,Vector2f())
operator fun Vector2f.plusAssign(v: Vector2fc) {add(v)}
operator fun Vector2fc.plus(v: Vector2fc) = add(v,Vector2f())
operator fun Vector2f.unaryMinus() {negate()}

/* Vector2d */

operator fun Vector2d.get(e: Int): Double = get(e)
operator fun Vector2d.minusAssign(v: Vector2fc) {sub(v)}
operator fun Vector2dc.minus(v: Vector2fc) = sub(v,Vector2d())
operator fun Vector2d.minusAssign(v: Vector2dc) {sub(v)}
operator fun Vector2dc.minus(v: Vector2dc) = sub(v,Vector2d())
operator fun Vector2d.plusAssign(v: Vector2fc) {add(v)}
operator fun Vector2dc.plus(v: Vector2fc) = add(v,Vector2d())
operator fun Vector2d.plusAssign(v: Vector2dc) {add(v)}
operator fun Vector2dc.plus(v: Vector2dc) = add(v,Vector2d())
operator fun Vector2d.unaryMinus() {negate()}

/* Vector3f */

operator fun Vector3f.get(e: Int): Float = get(e)
operator fun Vector3f.minusAssign(v: Vector3fc) {sub(v)}
operator fun Vector3fc.minus(v: Vector3fc) = sub(v,Vector3f())
operator fun Vector3f.plusAssign(v: Vector3fc) {add(v)}
operator fun Vector3fc.plus(v: Vector3fc) = add(v,Vector3f())
operator fun Vector3f.unaryMinus() {negate()}

/* Vector3d */

operator fun Vector3d.get(e: Int): Double = get(e)
operator fun Vector3d.minusAssign(v: Vector3fc) {sub(v)}
operator fun Vector3dc.minus(v: Vector3fc) = sub(v,Vector3d())
operator fun Vector3d.minusAssign(v: Vector3dc) {sub(v)}
operator fun Vector3dc.minus(v: Vector3dc) = sub(v,Vector3d())
operator fun Vector3d.plusAssign(v: Vector3fc) {add(v)}
operator fun Vector3dc.plus(v: Vector3fc) = add(v,Vector3d())
operator fun Vector3d.plusAssign(v: Vector3dc) {add(v)}
operator fun Vector3dc.plus(v: Vector3dc) = add(v,Vector3d())
operator fun Vector3d.unaryMinus() {negate()}

/* Vector4f */

operator fun Vector4f.get(e: Int): Float = get(e)
operator fun Vector4f.minusAssign(v: Vector4fc) {sub(v)}
operator fun Vector4fc.minus(v: Vector4fc) = sub(v,Vector4f())
operator fun Vector4f.plusAssign(v: Vector4fc) {add(v)}
operator fun Vector4fc.plus(v: Vector4fc) = add(v,Vector4f())
operator fun Vector4f.unaryMinus() {negate()}

/* Vector4d */

operator fun Vector4d.get(e: Int): Double = get(e)
operator fun Vector4d.minusAssign(v: Vector4fc) {sub(v)}
operator fun Vector4dc.minus(v: Vector4fc) = sub(v,Vector4d())
operator fun Vector4d.minusAssign(v: Vector4dc) {sub(v)}
operator fun Vector4dc.minus(v: Vector4dc) = sub(v,Vector4d())
operator fun Vector4d.plusAssign(v: Vector4fc) {add(v)}
operator fun Vector4dc.plus(v: Vector4fc) = add(v,Vector4d())
operator fun Vector4d.plusAssign(v: Vector4dc) {add(v)}
operator fun Vector4dc.plus(v: Vector4dc) = add(v,Vector4d())
operator fun Vector4d.unaryMinus() {negate()}

/* Quaternionf */

operator fun Quaternionf.get(e: Int): Float = get(e)
operator fun Quaternionf.minusAssign(q: Quaternionfc) {mul(q)}
operator fun Quaternionfc.minus(q: Quaternionfc) = mul(q,Quaternionf())
operator fun Quaternionf.unaryMinus() {conjugate()}
operator fun Quaternionf.timesAssign(v: Vector3f) {transform(v)}
operator fun Quaternionfc.times(v: Vector3f) = transform(v,Vector3f())
operator fun Quaternionf.timesAssign(v: Vector4f) {transform(v)}
operator fun Quaternionfc.times(v: Vector4f) = transform(v,Vector4f())

/* Quaterniond */

operator fun Quaterniond.get(e: Int): Double = get(e)
operator fun Quaterniond.minusAssign(q: Quaterniondc) {mul(q)}
operator fun Quaterniondc.minus(q: Quaterniondc) = mul(q,Quaterniond())
operator fun Quaterniond.unaryMinus() {conjugate()}
operator fun Quaterniond.timesAssign(v: Vector3d) {transform(v)}
operator fun Quaterniondc.times(v: Vector3d) = transform(v,Vector3d())
operator fun Quaterniond.timesAssign(v: Vector4d) {transform(v)}
operator fun Quaterniondc.times(v: Vector4d) = transform(v,Vector4d())
