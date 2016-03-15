module JOML {
    export class Matrix4 {
        public static PLANE_NX:number = 0;
        public static PLANE_PX:number = 1;
        public static PLANE_NY:number= 2;
        public static PLANE_PY:number = 3;
        public static PLANE_NZ:number = 4;
        public static PLANE_PZ:number = 5;

        public static CORNER_NXNYNZ:number = 0;
        public static CORNER_PXNYNZ:number = 1;
        public static CORNER_PXPYNZ:number = 2;
        public static CORNER_NXPYNZ:number = 3;
        public static CORNER_PXNYPZ:number = 4;
        public static CORNER_NXNYPZ:number = 5;
        public static CORNER_NXPYPZ:number = 6;
        public static CORNER_PXPYPZ:number = 7;

        m00: number; m10: number; m20: number; m30: number;
        m01: number; m11: number; m21: number; m31: number;
        m02: number; m12: number; m22: number; m32: number;
        m03: number; m13: number; m23: number; m33: number;

        constructor() {
            var thiz = this;
            thiz.m00 = 1.0;
            thiz.m01 = 0.0;
            thiz.m02 = 0.0;
            thiz.m03 = 0.0;
            thiz.m10 = 0.0;
            thiz.m11 = 1.0;
            thiz.m12 = 0.0;
            thiz.m13 = 0.0;
            thiz.m20 = 0.0;
            thiz.m21 = 0.0;
            thiz.m22 = 1.0;
            thiz.m23 = 0.0;
            thiz.m30 = 0.0;
            thiz.m31 = 0.0;
            thiz.m32 = 0.0;
            thiz.m33 = 1.0;
        }

        get(b: Float32Array): Float32Array {
            var thiz = this;
            b[0] = thiz.m00;
            b[1] = thiz.m01;
            b[2] = thiz.m02;
            b[3] = thiz.m03;
            b[4] = thiz.m10;
            b[5] = thiz.m11;
            b[6] = thiz.m12;
            b[7] = thiz.m13;
            b[8] = thiz.m20;
            b[9] = thiz.m21;
            b[10] = thiz.m22;
            b[11] = thiz.m23;
            b[12] = thiz.m30;
            b[13] = thiz.m31;
            b[14] = thiz.m32;
            b[15] = thiz.m33;
            return b;
        }

        set(m: Matrix4): Matrix4;
        set(b: Float32Array): Matrix4;
        set(m00: number, m01: number, m02: number, m03: number,
            m10: number, m11: number, m12: number, m13: number,
            m20: number, m21: number, m22: number, m23: number,
            m30: number, m31: number, m32: number, m33: number): Matrix4;
        set(m00: any, m01?: number, m02?: number, m03?: number,
            m10?: number, m11?: number, m12?: number, m13?: number,
            m20?: number, m21?: number, m22?: number, m23?: number,
            m30?: number, m31?: number, m32?: number, m33?: number): Matrix4 {
            var thiz = this;
            if (m00 instanceof Float32Array) {
                var b: Float32Array = m00;
                thiz.m01 = b[1];
                thiz.m02 = b[2];
                thiz.m03 = b[3];
                thiz.m10 = b[4];
                thiz.m11 = b[5];
                thiz.m12 = b[6];
                thiz.m13 = b[7];
                thiz.m20 = b[8];
                thiz.m21 = b[9];
                thiz.m22 = b[10];
                thiz.m23 = b[11];
                thiz.m30 = b[12];
                thiz.m31 = b[13];
                thiz.m32 = b[14];
                thiz.m33 = b[15];
            } else if (m00 instanceof Matrix4) {
                var m: Matrix4 = m00;
                thiz.m00 = m.m00;
                thiz.m01 = m.m01;
                thiz.m02 = m.m02;
                thiz.m03 = m.m03;
                thiz.m10 = m.m10;
                thiz.m11 = m.m11;
                thiz.m12 = m.m12;
                thiz.m13 = m.m13;
                thiz.m20 = m.m20;
                thiz.m21 = m.m21;
                thiz.m22 = m.m22;
                thiz.m23 = m.m23;
                thiz.m30 = m.m30;
                thiz.m31 = m.m31;
                thiz.m32 = m.m32;
                thiz.m33 = m.m33;
            } else {
                thiz.m00 = <number>m00;
                thiz.m01 = m01;
                thiz.m02 = m02;
                thiz.m03 = m03;
                thiz.m10 = m10;
                thiz.m11 = m11;
                thiz.m12 = m12;
                thiz.m13 = m13;
                thiz.m20 = m20;
                thiz.m21 = m21;
                thiz.m22 = m22;
                thiz.m23 = m23;
                thiz.m30 = m30;
                thiz.m31 = m31;
                thiz.m32 = m32;
                thiz.m33 = m33;
            }
            return this;
        }

        determinant(): number {
            var thiz = this;
            return (thiz.m00 * thiz.m11 - thiz.m01 * thiz.m10) * (thiz.m22 * thiz.m33 - thiz.m23 * thiz.m32)
                 + (thiz.m02 * thiz.m10 - thiz.m00 * thiz.m12) * (thiz.m21 * thiz.m33 - thiz.m23 * thiz.m31)
                 + (thiz.m00 * thiz.m13 - thiz.m03 * thiz.m10) * (thiz.m21 * thiz.m32 - thiz.m22 * thiz.m31)
                 + (thiz.m01 * thiz.m12 - thiz.m02 * thiz.m11) * (thiz.m20 * thiz.m33 - thiz.m23 * thiz.m30)
                 + (thiz.m03 * thiz.m11 - thiz.m01 * thiz.m13) * (thiz.m20 * thiz.m32 - thiz.m22 * thiz.m30)
                 + (thiz.m02 * thiz.m13 - thiz.m03 * thiz.m12) * (thiz.m20 * thiz.m31 - thiz.m21 * thiz.m30);
        }

        determinant3x3(): number {
            var thiz = this;
            return (thiz.m00 * thiz.m11 - thiz.m01 * thiz.m10) * thiz.m22
                 + (thiz.m02 * thiz.m10 - thiz.m00 * thiz.m12) * thiz.m21
                 + (thiz.m01 * thiz.m12 - thiz.m02 * thiz.m11) * thiz.m20;
        }

        invert(dest?: Matrix4): Matrix4 {
            var thiz = this;
            dest = dest || thiz;
            var a = thiz.m00 * thiz.m11 - thiz.m01 * thiz.m10;
            var b = thiz.m00 * thiz.m12 - thiz.m02 * thiz.m10;
            var c = thiz.m00 * thiz.m13 - thiz.m03 * thiz.m10;
            var d = thiz.m01 * thiz.m12 - thiz.m02 * thiz.m11;
            var e = thiz.m01 * thiz.m13 - thiz.m03 * thiz.m11;
            var f = thiz.m02 * thiz.m13 - thiz.m03 * thiz.m12;
            var g = thiz.m20 * thiz.m31 - thiz.m21 * thiz.m30;
            var h = thiz.m20 * thiz.m32 - thiz.m22 * thiz.m30;
            var i = thiz.m20 * thiz.m33 - thiz.m23 * thiz.m30;
            var j = thiz.m21 * thiz.m32 - thiz.m22 * thiz.m31;
            var k = thiz.m21 * thiz.m33 - thiz.m23 * thiz.m31;
            var l = thiz.m22 * thiz.m33 - thiz.m23 * thiz.m32;
            var det = a * l - b * k + c * j + d * i - e * h + f * g;
            det = 1.0 / det;
            dest.set(( thiz.m11 * l - thiz.m12 * k + thiz.m13 * j) * det,
                     (-thiz.m01 * l + thiz.m02 * k - thiz.m03 * j) * det,
                     ( thiz.m31 * f - thiz.m32 * e + thiz.m33 * d) * det,
                     (-thiz.m21 * f + thiz.m22 * e - thiz.m23 * d) * det,
                     (-thiz.m10 * l + thiz.m12 * i - thiz.m13 * h) * det,
                     ( thiz.m00 * l - thiz.m02 * i + thiz.m03 * h) * det,
                     (-thiz.m30 * f + thiz.m32 * c - thiz.m33 * b) * det,
                     ( thiz.m20 * f - thiz.m22 * c + thiz.m23 * b) * det,
                     ( thiz.m10 * k - thiz.m11 * i + thiz.m13 * g) * det,
                     (-thiz.m00 * k + thiz.m01 * i - thiz.m03 * g) * det,
                     ( thiz.m30 * e - thiz.m31 * c + thiz.m33 * a) * det,
                     (-thiz.m20 * e + thiz.m21 * c - thiz.m23 * a) * det,
                     (-thiz.m10 * j + thiz.m11 * h - thiz.m12 * g) * det,
                     ( thiz.m00 * j - thiz.m01 * h + thiz.m02 * g) * det,
                     (-thiz.m30 * d + thiz.m31 * b - thiz.m32 * a) * det,
                     ( thiz.m20 * d - thiz.m21 * b + thiz.m22 * a) * det);
            return dest;
        }

        invertAffine(dest?: Matrix4): Matrix4 {
            var thiz = this;
            dest = dest || thiz;
            var s = thiz.determinant3x3();
            s = 1.0 / s;
            var m10m22 = thiz.m10 * thiz.m22;
            var m10m21 = thiz.m10 * thiz.m21;
            var m10m02 = thiz.m10 * thiz.m02;
            var m10m01 = thiz.m10 * thiz.m01;
            var m11m22 = thiz.m11 * thiz.m22;
            var m11m20 = thiz.m11 * thiz.m20;
            var m11m02 = thiz.m11 * thiz.m02;
            var m11m00 = thiz.m11 * thiz.m00;
            var m12m21 = thiz.m12 * thiz.m21;
            var m12m20 = thiz.m12 * thiz.m20;
            var m12m01 = thiz.m12 * thiz.m01;
            var m12m00 = thiz.m12 * thiz.m00;
            var m20m02 = thiz.m20 * thiz.m02;
            var m20m01 = thiz.m20 * thiz.m01;
            var m21m02 = thiz.m21 * thiz.m02;
            var m21m00 = thiz.m21 * thiz.m00;
            var m22m01 = thiz.m22 * thiz.m01;
            var m22m00 = thiz.m22 * thiz.m00;
            dest.set((m11m22 - m12m21) * s,
                     (m21m02 - m22m01) * s,
                     (m12m01 - m11m02) * s,
                     0.0,
                     (m12m20 - m10m22) * s,
                     (m22m00 - m20m02) * s,
                     (m10m02 - m12m00) * s,
                     0.0,
                     (m10m21 - m11m20) * s,
                     (m20m01 - m21m00) * s,
                     (m11m00 - m10m01) * s,
                     0.0,
                     (m10m22 * thiz.m31 - m10m21 * thiz.m32 + m11m20 * thiz.m32 - m11m22 * thiz.m30 + m12m21 * thiz.m30 - m12m20 * thiz.m31) * s,
                     (m20m02 * thiz.m31 - m20m01 * thiz.m32 + m21m00 * thiz.m32 - m21m02 * thiz.m30 + m22m01 * thiz.m30 - m22m00 * thiz.m31) * s,
                     (m11m02 * thiz.m30 - m12m01 * thiz.m30 + m12m00 * thiz.m31 - m10m02 * thiz.m31 + m10m01 * thiz.m32 - m11m00 * thiz.m32) * s,
                     1.0);
            return dest;
        }

        transpose(dest?: Matrix4): Matrix4 {
            var thiz = this;
            dest = dest || thiz;
            dest.set(thiz.m00, thiz.m10, thiz.m20, thiz.m30,
                     thiz.m01, thiz.m11, thiz.m21, thiz.m31,
                     thiz.m02, thiz.m12, thiz.m22, thiz.m32,
                     thiz.m03, thiz.m13, thiz.m23, thiz.m33);
            return dest;
        }

        translation(x: number, y: number, z: number): Matrix4;
        translation(v: Vector3): Matrix4;
        translation(xv: any, y?: number, z?: number): Matrix4 {
            var tx, ty, tz;
            if (xv instanceof Vector3) {
                tx = xv.x;
                ty = xv.y;
                tz = xv.z;
            } else {
                tx = <number>xv;
                ty = y;
                tz = z;
            }
            var thiz = this;
            thiz.m00 = 1.0;
            thiz.m01 = 0.0;
            thiz.m02 = 0.0;
            thiz.m03 = 0.0;
            thiz.m10 = 0.0;
            thiz.m11 = 1.0;
            thiz.m12 = 0.0;
            thiz.m13 = 0.0;
            thiz.m20 = 0.0;
            thiz.m21 = 0.0;
            thiz.m22 = 1.0;
            thiz.m23 = 0.0;
            thiz.m30 = tx;
            thiz.m31 = ty;
            thiz.m32 = tz;
            thiz.m33 = 1.0;
            return this;
        }

        rotation(quat: Quaternion): Matrix4;
        rotation(angle: number, x: number, y: number, z: number): Matrix4;
        rotation(angle: any, x?: number, y?: number, z?: number): Matrix4 {
            var thiz = thiz;
            if (angle instanceof Quaternion) {
                return thiz.rotationQuaternion(<Quaternion>angle);
            }
            var cos = Math.cos(angle);
            var sin = Math.sin(angle);
            var C = 1.0 - cos;
            var xy = x * y, xz = x * z, yz = y * z;
            thiz.m00 = cos + x * x * C;
            thiz.m10 = xy * C - z * sin;
            thiz.m20 = xz * C + y * sin;
            thiz.m30 = 0.0;
            thiz.m01 = xy * C + z * sin;
            thiz.m11 = cos + y * y * C;
            thiz.m21 = yz * C - x * sin;
            thiz.m31 = 0.0;
            thiz.m02 = xz * C - y * sin;
            thiz.m12 = yz * C + x * sin;
            thiz.m22 = cos + z * z * C;
            thiz.m32 = 0.0;
            thiz.m03 = 0.0;
            thiz.m13 = 0.0;
            thiz.m23 = 0.0;
            thiz.m33 = 1.0;
            return this;
        }

        private rotationQuaternion(quat: Quaternion): Matrix4 {
            var dqx = quat.x + quat.x;
            var dqy = quat.y + quat.y;
            var dqz = quat.z + quat.z;
            var q00 = dqx * quat.x;
            var q11 = dqy * quat.y;
            var q22 = dqz * quat.z;
            var q01 = dqx * quat.y;
            var q02 = dqx * quat.z;
            var q03 = dqx * quat.w;
            var q12 = dqy * quat.z;
            var q13 = dqy * quat.w;
            var q23 = dqz * quat.w;
            var thiz = this;
            thiz.m00 = 1.0 - q11 - q22;
            thiz.m01 = q01 + q23;
            thiz.m02 = q02 - q13;
            thiz.m03 = 0.0;
            thiz.m10 = q01 - q23;
            thiz.m11 = 1.0 - q22 - q00;
            thiz.m12 = q12 + q03;
            thiz.m13 = 0.0;
            thiz.m20 = q02 + q13;
            thiz.m21 = q12 - q03;
            thiz.m22 = 1.0 - q11 - q00;
            thiz.m23 = 0.0;
            thiz.m30 = 0.0;
            thiz.m31 = 0.0;
            thiz.m32 = 0.0;
            thiz.m33 = 1.0;
            return this;
        }

        rotationX(ang: number): Matrix4 {
            var sin: number, cos: number;
            if (ang == Math.PI || ang == -Math.PI) {
                cos = -1.0;
                sin = 0.0;
            } else if (ang == Math.PI * 0.5 || ang == -Math.PI * 1.5) {
                cos = 0.0;
                sin = 1.0;
            } else if (ang == -Math.PI * 0.5 || ang == Math.PI * 1.5) {
                cos = 0.0;
                sin = -1.0;
            } else {
                cos = Math.cos(ang);
                sin = Math.sin(ang);
            }
            var thiz = this;
            thiz.m00 = 1.0;
            thiz.m01 = 0.0;
            thiz.m02 = 0.0;
            thiz.m03 = 0.0;
            thiz.m10 = 0.0;
            thiz.m11 = cos;
            thiz.m12 = sin;
            thiz.m13 = 0.0;
            thiz.m20 = 0.0;
            thiz.m21 = -sin;
            thiz.m22 = cos;
            thiz.m23 = 0.0;
            thiz.m30 = 0.0;
            thiz.m31 = 0.0;
            thiz.m32 = 0.0;
            thiz.m33 = 1.0;
            return this;
        }

        rotationY(ang: number): Matrix4 {
            var sin: number, cos: number;
            if (ang == Math.PI || ang == -Math.PI) {
                cos = -1.0;
                sin = 0.0;
            } else if (ang == Math.PI * 0.5 || ang == -Math.PI * 1.5) {
                cos = 0.0;
                sin = 1.0;
            } else if (ang == -Math.PI * 0.5 || ang == Math.PI * 1.5) {
                cos = 0.0;
                sin = -1.0;
            } else {
                cos = Math.cos(ang);
                sin = Math.sin(ang);
            }
            var thiz = this;
            thiz.m00 = cos;
            thiz.m01 = 0.0;
            thiz.m02 = -sin;
            thiz.m03 = 0.0;
            thiz.m10 = 0.0;
            thiz.m11 = 1.0;
            thiz.m12 = 0.0;
            thiz.m13 = 0.0;
            thiz.m20 = sin;
            thiz.m21 = 0.0;
            thiz.m22 = cos;
            thiz.m23 = 0.0;
            thiz.m30 = 0.0;
            thiz.m31 = 0.0;
            thiz.m32 = 0.0;
            thiz.m33 = 1.0;
            return this;
        }

        rotationZ(ang: number): Matrix4 {
            var sin: number, cos: number;
            if (ang == Math.PI || ang == -Math.PI) {
                cos = -1.0;
                sin = 0.0;
            } else if (ang == Math.PI * 0.5 || ang == -Math.PI * 1.5) {
                cos = 0.0;
                sin = 1.0;
            } else if (ang == -Math.PI * 0.5 || ang == Math.PI * 1.5) {
                cos = 0.0;
                sin = -1.0;
            } else {
                cos = Math.cos(ang);
                sin = Math.sin(ang);
            }
            var thiz = this;
            thiz.m00 = cos;
            thiz.m01 = sin;
            thiz.m02 = 0.0;
            thiz.m03 = 0.0;
            thiz.m10 = -sin;
            thiz.m11 = cos;
            thiz.m12 = 0.0;
            thiz.m13 = 0.0;
            thiz.m20 = 0.0;
            thiz.m21 = 0.0;
            thiz.m22 = 1.0;
            thiz.m23 = 0.0;
            thiz.m30 = 0.0;
            thiz.m31 = 0.0;
            thiz.m32 = 0.0;
            thiz.m33 = 1.0;
            return this;
        }

        zero(): Matrix4 {
            var thiz = this;
            thiz.m00 = 0.0;
            thiz.m01 = 0.0;
            thiz.m02 = 0.0;
            thiz.m03 = 0.0;
            thiz.m10 = 0.0;
            thiz.m11 = 0.0;
            thiz.m12 = 0.0;
            thiz.m13 = 0.0;
            thiz.m20 = 0.0;
            thiz.m21 = 0.0;
            thiz.m22 = 0.0;
            thiz.m23 = 0.0;
            thiz.m30 = 0.0;
            thiz.m31 = 0.0;
            thiz.m32 = 0.0;
            thiz.m33 = 0.0;
            return this;
        }

        identity(): Matrix4 {
            var thiz = this;
            thiz.m00 = 1.0;
            thiz.m01 = 0.0;
            thiz.m02 = 0.0;
            thiz.m03 = 0.0;
            thiz.m10 = 0.0;
            thiz.m11 = 1.0;
            thiz.m12 = 0.0;
            thiz.m13 = 0.0;
            thiz.m20 = 0.0;
            thiz.m21 = 0.0;
            thiz.m22 = 1.0;
            thiz.m23 = 0.0;
            thiz.m30 = 0.0;
            thiz.m31 = 0.0;
            thiz.m32 = 0.0;
            thiz.m33 = 1.0;
            return this;
        }

        scaling(x: number, y: number, z: number): Matrix4 {
            var thiz = this;
            thiz.m00 = x;
            thiz.m01 = 0.0;
            thiz.m02 = 0.0;
            thiz.m03 = 0.0;
            thiz.m10 = 0.0;
            thiz.m11 = y;
            thiz.m12 = 0.0;
            thiz.m13 = 0.0;
            thiz.m20 = 0.0;
            thiz.m21 = 0.0;
            thiz.m22 = z;
            thiz.m23 = 0.0;
            thiz.m30 = 0.0;
            thiz.m31 = 0.0;
            thiz.m32 = 0.0;
            thiz.m33 = 1.0;
            return this;
        }

        transform(v: Vector4, dest?: Vector4): Vector4 {
            var thiz = this;
            dest = dest || v;
            dest.set(thiz.m00 * v.x + thiz.m10 * v.y + thiz.m20 * v.z + thiz.m30 * v.w,
                     thiz.m01 * v.x + thiz.m11 * v.y + thiz.m21 * v.z + thiz.m31 * v.w,
                     thiz.m02 * v.x + thiz.m12 * v.y + thiz.m22 * v.z + thiz.m32 * v.w,
                     thiz.m03 * v.x + thiz.m13 * v.y + thiz.m23 * v.z + thiz.m33 * v.w);
            return dest;
        }

        transformProject(v: Vector3, dest?: Vector3): Vector3;
        transformProject(v: Vector4, dest?: Vector4): Vector4;
        transformProject(v: any, dest?: any): any {
            dest = dest || v;
            var thiz = this;
            if (v instanceof Vector4) {
                var d: Vector4 = <Vector4>dest;
                var invW = 1.0 / (thiz.m03 * v.x + thiz.m13 * v.y + thiz.m23 * v.z + thiz.m33 * v.w);
                d.set((thiz.m00 * v.x + thiz.m10 * v.y + thiz.m20 * v.z + thiz.m30 * v.w) * invW,
                      (thiz.m01 * v.x + thiz.m11 * v.y + thiz.m21 * v.z + thiz.m31 * v.w) * invW,
                      (thiz.m02 * v.x + thiz.m12 * v.y + thiz.m22 * v.z + thiz.m32 * v.w) * invW,
                      1.0);
            } else {
                var d3: Vector3 = <Vector3>dest;
                var invW = 1.0 / (thiz.m03 * v.x + thiz.m13 * v.y + thiz.m23 * v.z + thiz.m33);
                d3.set((thiz.m00 * v.x + thiz.m10 * v.y + thiz.m20 * v.z + thiz.m30) * invW,
                       (thiz.m01 * v.x + thiz.m11 * v.y + thiz.m21 * v.z + thiz.m31) * invW,
                       (thiz.m02 * v.x + thiz.m12 * v.y + thiz.m22 * v.z + thiz.m32) * invW);
                return dest;
            }
            return dest;
        }

        transformPosition(v: Vector3, dest?: Vector3): Vector3 {
            dest = dest || v;
            var thiz = this;
            dest.set(thiz.m00 * v.x + thiz.m10 * v.y + thiz.m20 * v.z + thiz.m30,
                     thiz.m01 * v.x + thiz.m11 * v.y + thiz.m21 * v.z + thiz.m31,
                     thiz.m02 * v.x + thiz.m12 * v.y + thiz.m22 * v.z + thiz.m32);
            return dest;
        }

        transformDirection(v: Vector3, dest?: Vector3): Vector3 {
            dest = dest || v;
            var thiz = this;
            dest.set(thiz.m00 * v.x + thiz.m10 * v.y + thiz.m20 * v.z,
                     thiz.m01 * v.x + thiz.m11 * v.y + thiz.m21 * v.z,
                     thiz.m02 * v.x + thiz.m12 * v.y + thiz.m22 * v.z);
            return dest;
        }

        transformAffine(v: Vector4, dest?: Vector4): Vector4 {
            dest = dest || v;
            var thiz = this;
            v.set(thiz.m00 * v.x + thiz.m10 * v.y + thiz.m20 * v.z + thiz.m30 * v.w,
                  thiz.m01 * v.x + thiz.m11 * v.y + thiz.m21 * v.z + thiz.m31 * v.w,
                  thiz.m02 * v.x + thiz.m12 * v.y + thiz.m22 * v.z + thiz.m32 * v.w,
                  v.w);
            return dest;
        }

        scale(x: number, y: number, z: number, dest?: Matrix4): Matrix4 {
            var thiz = this;
            dest = dest || thiz;
            dest.m00 = thiz.m00 * x;
            dest.m01 = thiz.m01 * x;
            dest.m02 = thiz.m02 * x;
            dest.m03 = thiz.m03 * x;
            dest.m10 = thiz.m10 * y;
            dest.m11 = thiz.m11 * y;
            dest.m12 = thiz.m12 * y;
            dest.m13 = thiz.m13 * y;
            dest.m20 = thiz.m20 * z;
            dest.m21 = thiz.m21 * z;
            dest.m22 = thiz.m22 * z;
            dest.m23 = thiz.m23 * z;
            dest.m30 = thiz.m30;
            dest.m31 = thiz.m31;
            dest.m32 = thiz.m32;
            dest.m32 = thiz.m33;
            return dest;
        }

        rotateX(ang: number, dest?: Matrix4): Matrix4 {
            var thiz = this;
            dest = dest || thiz;
            var sin: number, cos: number;
            if (ang == Math.PI || ang == -Math.PI) {
                cos = -1.0;
                sin = 0.0;
            } else if (ang == Math.PI * 0.5 || ang == -Math.PI * 1.5) {
                cos = 0.0;
                sin = 1.0;
            } else if (ang == -Math.PI * 0.5 || ang == Math.PI * 1.5) {
                cos = 0.0;
                sin = -1.0;
            } else {
                cos = Math.cos(ang);
                sin = Math.sin(ang);
            }
            var rm11 = cos;
            var rm12 = sin;
            var rm21 = -sin;
            var rm22 = cos;
            var nm10 = thiz.m10 * rm11 + thiz.m20 * rm12;
            var nm11 = thiz.m11 * rm11 + thiz.m21 * rm12;
            var nm12 = thiz.m12 * rm11 + thiz.m22 * rm12;
            var nm13 = thiz.m13 * rm11 + thiz.m23 * rm12;
            dest.m20 = thiz.m10 * rm21 + thiz.m20 * rm22;
            dest.m21 = thiz.m11 * rm21 + thiz.m21 * rm22;
            dest.m22 = thiz.m12 * rm21 + thiz.m22 * rm22;
            dest.m23 = thiz.m13 * rm21 + thiz.m23 * rm22;
            dest.m10 = nm10;
            dest.m11 = nm11;
            dest.m12 = nm12;
            dest.m13 = nm13;
            dest.m00 = thiz.m00;
            dest.m01 = thiz.m01;
            dest.m02 = thiz.m02;
            dest.m03 = thiz.m03;
            dest.m30 = thiz.m30;
            dest.m31 = thiz.m31;
            dest.m32 = thiz.m32;
            dest.m33 = thiz.m33;
            return dest;
        }

        rotateY(ang: number, dest?: Matrix4): Matrix4 {
            var thiz = this;
            dest = dest || thiz;
            var sin: number, cos: number;
            if (ang == Math.PI || ang == -Math.PI) {
                cos = -1.0;
                sin = 0.0;
            } else if (ang == Math.PI * 0.5 || ang == -Math.PI * 1.5) {
                cos = 0.0;
                sin = 1.0;
            } else if (ang == -Math.PI * 0.5 || ang == Math.PI * 1.5) {
                cos = 0.0;
                sin = -1.0;
            } else {
                cos = Math.cos(ang);
                sin = Math.sin(ang);
            }
            var rm00 = cos;
            var rm02 = -sin;
            var rm20 = sin;
            var rm22 = cos;
            var nm00 = thiz.m00 * rm00 + thiz.m20 * rm02;
            var nm01 = thiz.m01 * rm00 + thiz.m21 * rm02;
            var nm02 = thiz.m02 * rm00 + thiz.m22 * rm02;
            var nm03 = thiz.m03 * rm00 + thiz.m23 * rm02;
            dest.m20 = thiz.m00 * rm20 + thiz.m20 * rm22;
            dest.m21 = thiz.m01 * rm20 + thiz.m21 * rm22;
            dest.m22 = thiz.m02 * rm20 + thiz.m22 * rm22;
            dest.m23 = thiz.m03 * rm20 + thiz.m23 * rm22;
            dest.m00 = nm00;
            dest.m01 = nm01;
            dest.m02 = nm02;
            dest.m03 = nm03;
            dest.m10 = thiz.m10;
            dest.m11 = thiz.m11;
            dest.m12 = thiz.m12;
            dest.m13 = thiz.m13;
            dest.m30 = thiz.m30;
            dest.m31 = thiz.m31;
            dest.m32 = thiz.m32;
            dest.m33 = thiz.m33;
            return dest;
        }

        rotateZ(ang: number, dest?: Matrix4): Matrix4 {
            var thiz = this;
            dest = dest || thiz;
            var sin: number, cos: number;
            if (ang == Math.PI || ang == -Math.PI) {
                cos = -1.0;
                sin = 0.0;
            } else if (ang == Math.PI * 0.5 || ang == -Math.PI * 1.5) {
                cos = 0.0;
                sin = 1.0;
            } else if (ang == -Math.PI * 0.5 || ang == Math.PI * 1.5) {
                cos = 0.0;
                sin = -1.0;
            } else {
                cos = Math.cos(ang);
                sin = Math.sin(ang);
            }
            var rm00 = cos;
            var rm01 = sin;
            var rm10 = -sin;
            var rm11 = cos;
            var nm00 = thiz.m00 * rm00 + thiz.m10 * rm01;
            var nm01 = thiz.m01 * rm00 + thiz.m11 * rm01;
            var nm02 = thiz.m02 * rm00 + thiz.m12 * rm01;
            var nm03 = thiz.m03 * rm00 + thiz.m13 * rm01;
            dest.m10 = thiz.m00 * rm10 + thiz.m10 * rm11;
            dest.m11 = thiz.m01 * rm10 + thiz.m11 * rm11;
            dest.m12 = thiz.m02 * rm10 + thiz.m12 * rm11;
            dest.m13 = thiz.m03 * rm10 + thiz.m13 * rm11;
            dest.m00 = nm00;
            dest.m01 = nm01;
            dest.m02 = nm02;
            dest.m03 = nm03;
            dest.m20 = thiz.m20;
            dest.m21 = thiz.m21;
            dest.m22 = thiz.m22;
            dest.m23 = thiz.m23;
            dest.m30 = thiz.m30;
            dest.m31 = thiz.m31;
            dest.m32 = thiz.m32;
            dest.m33 = thiz.m33;
            return dest;
        }

        rotate(quat: Quaternion, dest?: Matrix4): Matrix4;
        rotate(ang: number, axis: Vector3, dest?: Matrix4): Matrix4;
        rotate(ang: number, x: number, y: number, z: number, dest?: Matrix4): Matrix4;
        rotate(ang: any, axisX: any, destY?: any, zParam?: number, otherDest?: Matrix4): Matrix4 {
            var thiz = this;
            if (ang instanceof Quaternion) {
                return thiz.rotateQuaternion(<Quaternion>ang, <Matrix4>axisX);
            }
            var x, y, z;
            var dest: Matrix4;
            if (axisX instanceof Vector3) {
                x = axisX.x;
                y = axisX.y;
                z = axisX.z;
                dest = <Matrix4>destY || this;
            } else {
                x = <number>axisX;
                y = <number>destY;
                z = zParam;
                dest = otherDest || this;
            }
            var s = Math.sin(ang);
            var c = Math.cos(ang);
            var C = 1.0 - c;
            var xx = x * x, xy = x * y, xz = x * z;
            var yy = y * y, yz = y * z;
            var zz = z * z;
            var rm00 = xx * C + c;
            var rm01 = xy * C + z * s;
            var rm02 = xz * C - y * s;
            var rm10 = xy * C - z * s;
            var rm11 = yy * C + c;
            var rm12 = yz * C + x * s;
            var rm20 = xz * C + y * s;
            var rm21 = yz * C - x * s;
            var rm22 = zz * C + c;
            var nm00 = thiz.m00 * rm00 + thiz.m10 * rm01 + thiz.m20 * rm02;
            var nm01 = thiz.m01 * rm00 + thiz.m11 * rm01 + thiz.m21 * rm02;
            var nm02 = thiz.m02 * rm00 + thiz.m12 * rm01 + thiz.m22 * rm02;
            var nm03 = thiz.m03 * rm00 + thiz.m13 * rm01 + thiz.m23 * rm02;
            var nm10 = thiz.m00 * rm10 + thiz.m10 * rm11 + thiz.m20 * rm12;
            var nm11 = thiz.m01 * rm10 + thiz.m11 * rm11 + thiz.m21 * rm12;
            var nm12 = thiz.m02 * rm10 + thiz.m12 * rm11 + thiz.m22 * rm12;
            var nm13 = thiz.m03 * rm10 + thiz.m13 * rm11 + thiz.m23 * rm12;
            dest.m20 = thiz.m00 * rm20 + thiz.m10 * rm21 + thiz.m20 * rm22;
            dest.m21 = thiz.m01 * rm20 + thiz.m11 * rm21 + thiz.m21 * rm22;
            dest.m22 = thiz.m02 * rm20 + thiz.m12 * rm21 + thiz.m22 * rm22;
            dest.m23 = thiz.m03 * rm20 + thiz.m13 * rm21 + thiz.m23 * rm22;
            dest.m00 = nm00;
            dest.m01 = nm01;
            dest.m02 = nm02;
            dest.m03 = nm03;
            dest.m10 = nm10;
            dest.m11 = nm11;
            dest.m12 = nm12;
            dest.m13 = nm13;
            dest.m30 = thiz.m30;
            dest.m31 = thiz.m31;
            dest.m32 = thiz.m32;
            dest.m33 = thiz.m33;
            return dest;
        }

        private rotateQuaternion(quat: Quaternion, dest?: Matrix4): Matrix4 {
            var thiz = this;
            dest = dest || thiz;
            var dqx = quat.x + quat.x;
            var dqy = quat.y + quat.y;
            var dqz = quat.z + quat.z;
            var q00 = dqx * quat.x;
            var q11 = dqy * quat.y;
            var q22 = dqz * quat.z;
            var q01 = dqx * quat.y;
            var q02 = dqx * quat.z;
            var q03 = dqx * quat.w;
            var q12 = dqy * quat.z;
            var q13 = dqy * quat.w;
            var q23 = dqz * quat.w;
            var rm00 = 1.0 - q11 - q22;
            var rm01 = q01 + q23;
            var rm02 = q02 - q13;
            var rm10 = q01 - q23;
            var rm11 = 1.0 - q22 - q00;
            var rm12 = q12 + q03;
            var rm20 = q02 + q13;
            var rm21 = q12 - q03;
            var rm22 = 1.0 - q11 - q00;
            var nm00 = thiz.m00 * rm00 + thiz.m10 * rm01 + thiz.m20 * rm02;
            var nm01 = thiz.m01 * rm00 + thiz.m11 * rm01 + thiz.m21 * rm02;
            var nm02 = thiz.m02 * rm00 + thiz.m12 * rm01 + thiz.m22 * rm02;
            var nm03 = thiz.m03 * rm00 + thiz.m13 * rm01 + thiz.m23 * rm02;
            var nm10 = thiz.m00 * rm10 + thiz.m10 * rm11 + thiz.m20 * rm12;
            var nm11 = thiz.m01 * rm10 + thiz.m11 * rm11 + thiz.m21 * rm12;
            var nm12 = thiz.m02 * rm10 + thiz.m12 * rm11 + thiz.m22 * rm12;
            var nm13 = thiz.m03 * rm10 + thiz.m13 * rm11 + thiz.m23 * rm12;
            dest.m20 = thiz.m00 * rm20 + thiz.m10 * rm21 + thiz.m20 * rm22;
            dest.m21 = thiz.m01 * rm20 + thiz.m11 * rm21 + thiz.m21 * rm22;
            dest.m22 = thiz.m02 * rm20 + thiz.m12 * rm21 + thiz.m22 * rm22;
            dest.m23 = thiz.m03 * rm20 + thiz.m13 * rm21 + thiz.m23 * rm22;
            dest.m00 = nm00;
            dest.m01 = nm01;
            dest.m02 = nm02;
            dest.m03 = nm03;
            dest.m10 = nm10;
            dest.m11 = nm11;
            dest.m12 = nm12;
            dest.m13 = nm13;
            dest.m30 = thiz.m30;
            dest.m31 = thiz.m31;
            dest.m32 = thiz.m32;
            dest.m33 = thiz.m33;
            return dest;
        }

        translate(offset: Vector3, dest?: Matrix4): Matrix4;
        translate(x: number, y: number, z: number, dest?: Matrix4): Matrix4;
        translate(offsetX: any, yDest?: any, zDest?: any, dest?: Matrix4): Matrix4 {
            var thiz = this;
            var rm30: number;
            var rm31: number;
            var rm32: number;
            if (offsetX instanceof Vector3) {
                rm30 = offsetX.x;
                rm31 = offsetX.y;
                rm32 = offsetX.z;
                dest = <Matrix4>yDest || thiz;
            } else {
                rm30 = <number>offsetX;
                rm31 = <number>yDest;
                rm32 = <number>zDest;
                dest = dest || thiz;
            }
            dest.m30 = thiz.m00 * rm30 + thiz.m10 * rm31 + thiz.m20 * rm32 + thiz.m30;
            dest.m31 = thiz.m01 * rm30 + thiz.m11 * rm31 + thiz.m21 * rm32 + thiz.m31;
            dest.m32 = thiz.m02 * rm30 + thiz.m12 * rm31 + thiz.m22 * rm32 + thiz.m32;
            dest.m33 = thiz.m02 * rm30 + thiz.m12 * rm31 + thiz.m22 * rm32 + thiz.m33;
            dest.m00 = thiz.m00;
            dest.m01 = thiz.m01;
            dest.m02 = thiz.m02;
            dest.m03 = thiz.m03;
            dest.m10 = thiz.m10;
            dest.m11 = thiz.m11;
            dest.m12 = thiz.m12;
            dest.m13 = thiz.m13;
            dest.m20 = thiz.m20;
            dest.m21 = thiz.m21;
            dest.m22 = thiz.m22;
            dest.m23 = thiz.m23;
            return dest;
        }

        ortho(left: number, right: number, bottom: number, top: number, zNear: number, zFar: number, dest?: Matrix4): Matrix4;
        ortho(left: number, right: number, bottom: number, top: number, zNear: number, zFar: number, zZeroToOne: boolean, dest?: Matrix4): Matrix4
        ortho(left: number, right: number, bottom: number, top: number, zNear: number, zFar: number, zZeroToOneDest: any, otherDest?: Matrix4): Matrix4 {
            var dest: Matrix4, zZeroToOne: boolean;
            var thiz = this;
            if (typeof (zZeroToOneDest) === 'boolean') {
                dest = otherDest || thiz;
                zZeroToOne = <boolean>zZeroToOneDest;
            } else {
                dest = zZeroToOneDest || thiz;
                zZeroToOne = false;
            }
            var rm00 = 2.0 / (right - left);
            var rm11 = 2.0 / (top - bottom);
            var rm22 = (zZeroToOne ? 1.0 : 2.0) / (zNear - zFar);
            var rm30 = (left + right) / (left - right);
            var rm31 = (top + bottom) / (bottom - top);
            var rm32 = (zZeroToOne ? zNear : (zFar + zNear)) / (zNear - zFar);
            dest.m30 = thiz.m00 * rm30 + thiz.m10 * rm31 + thiz.m20 * rm32 + thiz.m30;
            dest.m31 = thiz.m01 * rm30 + thiz.m11 * rm31 + thiz.m21 * rm32 + thiz.m31;
            dest.m32 = thiz.m02 * rm30 + thiz.m12 * rm31 + thiz.m22 * rm32 + thiz.m32;
            dest.m33 = thiz.m03 * rm30 + thiz.m13 * rm31 + thiz.m23 * rm32 + thiz.m33;
            dest.m00 = thiz.m00 * rm00;
            dest.m01 = thiz.m01 * rm00;
            dest.m02 = thiz.m02 * rm00;
            dest.m03 = thiz.m03 * rm00;
            dest.m10 = thiz.m10 * rm11;
            dest.m11 = thiz.m11 * rm11;
            dest.m12 = thiz.m12 * rm11;
            dest.m13 = thiz.m13 * rm11;
            dest.m20 = thiz.m20 * rm22;
            dest.m21 = thiz.m21 * rm22;
            dest.m22 = thiz.m22 * rm22;
            dest.m23 = thiz.m23 * rm22;
            return dest;
        }

        setOrtho(left: number, right: number, bottom: number, top: number, zNear: number, zFar: number, zZeroToOne?: boolean): Matrix4 {
            var thiz = this;
            thiz.m00 = 2.0 / (right - left);
            thiz.m01 = 0.0;
            thiz.m02 = 0.0;
            thiz.m03 = 0.0;
            thiz.m10 = 0.0;
            thiz.m11 = 2.0 / (top - bottom);
            thiz.m12 = 0.0;
            thiz.m13 = 0.0;
            thiz.m20 = 0.0;
            thiz.m21 = 0.0;
            thiz.m22 = (zZeroToOne ? 1.0 : 2.0) / (zNear - zFar);
            thiz.m23 = 0.0;
            thiz.m30 = (right + left) / (left - right);
            thiz.m31 = (top + bottom) / (bottom - top);
            thiz.m32 = (zZeroToOne ? zNear : (zFar + zNear)) / (zNear - zFar);
            thiz.m33 = 1.0;
            return this;
        }

        ortho2D(left: number, right: number, bottom: number, top: number, dest?: Matrix4): Matrix4 {
            var thiz = this;
            dest = dest || thiz;
            var rm00 = 2.0 / (right - left);
            var rm11 = 2.0 / (top - bottom);
            var rm30 = -(right + left) / (right - left);
            var rm31 = -(top + bottom) / (top - bottom);
            dest.m30 = thiz.m00 * rm30 + thiz.m10 * rm31 + thiz.m30;
            dest.m31 = thiz.m01 * rm30 + thiz.m11 * rm31 + thiz.m31;
            dest.m32 = thiz.m02 * rm30 + thiz.m12 * rm31 + thiz.m32;
            dest.m33 = thiz.m03 * rm30 + thiz.m13 * rm31 + thiz.m33;
            dest.m00 = thiz.m00 * rm00;
            dest.m01 = thiz.m01 * rm00;
            dest.m02 = thiz.m02 * rm00;
            dest.m03 = thiz.m03 * rm00;
            dest.m10 = thiz.m10 * rm11;
            dest.m11 = thiz.m11 * rm11;
            dest.m12 = thiz.m12 * rm11;
            dest.m13 = thiz.m13 * rm11;
            dest.m20 = -thiz.m20;
            dest.m21 = -thiz.m21;
            dest.m22 = -thiz.m22;
            dest.m23 = -thiz.m23;
            return dest;
        }

        lookAt(eyeX: number, eyeY: number, eyeZ: number,
               centerX: number, centerY: number, centerZ: number,
               upX: number, upY: number, upZ: number, dest?: Matrix4): Matrix4 {
            var thiz = this;
            dest = dest || thiz;
            var dirX, dirY, dirZ;
            dirX = eyeX - centerX;
            dirY = eyeY - centerY;
            dirZ = eyeZ - centerZ;
            var invDirLength = 1.0 / Math.sqrt(dirX * dirX + dirY * dirY + dirZ * dirZ);
            dirX *= invDirLength;
            dirY *= invDirLength;
            dirZ *= invDirLength;
            var leftX, leftY, leftZ;
            leftX = upY * dirZ - upZ * dirY;
            leftY = upZ * dirX - upX * dirZ;
            leftZ = upX * dirY - upY * dirX;
            var invLeftLength = 1.0 / Math.sqrt(leftX * leftX + leftY * leftY + leftZ * leftZ);
            leftX *= invLeftLength;
            leftY *= invLeftLength;
            leftZ *= invLeftLength;
            var upnX = dirY * leftZ - dirZ * leftY;
            var upnY = dirZ * leftX - dirX * leftZ;
            var upnZ = dirX * leftY - dirY * leftX;
            var rm00 = leftX;
            var rm01 = upnX;
            var rm02 = dirX;
            var rm10 = leftY;
            var rm11 = upnY;
            var rm12 = dirY;
            var rm20 = leftZ;
            var rm21 = upnZ;
            var rm22 = dirZ;
            var rm30 = -(leftX * eyeX + leftY * eyeY + leftZ * eyeZ);
            var rm31 = -(upnX * eyeX + upnY * eyeY + upnZ * eyeZ);
            var rm32 = -(dirX * eyeX + dirY * eyeY + dirZ * eyeZ);
            dest.m30 = thiz.m00 * rm30 + thiz.m10 * rm31 + thiz.m20 * rm32 + thiz.m30;
            dest.m31 = thiz.m01 * rm30 + thiz.m11 * rm31 + thiz.m21 * rm32 + thiz.m31;
            dest.m32 = thiz.m02 * rm30 + thiz.m12 * rm31 + thiz.m22 * rm32 + thiz.m32;
            dest.m33 = thiz.m03 * rm30 + thiz.m13 * rm31 + thiz.m23 * rm32 + thiz.m33;
            var nm00 = thiz.m00 * rm00 + thiz.m10 * rm01 + thiz.m20 * rm02;
            var nm01 = thiz.m01 * rm00 + thiz.m11 * rm01 + thiz.m21 * rm02;
            var nm02 = thiz.m02 * rm00 + thiz.m12 * rm01 + thiz.m22 * rm02;
            var nm03 = thiz.m03 * rm00 + thiz.m13 * rm01 + thiz.m23 * rm02;
            var nm10 = thiz.m00 * rm10 + thiz.m10 * rm11 + thiz.m20 * rm12;
            var nm11 = thiz.m01 * rm10 + thiz.m11 * rm11 + thiz.m21 * rm12;
            var nm12 = thiz.m02 * rm10 + thiz.m12 * rm11 + thiz.m22 * rm12;
            var nm13 = thiz.m03 * rm10 + thiz.m13 * rm11 + thiz.m23 * rm12;
            dest.m20 = thiz.m00 * rm20 + thiz.m10 * rm21 + thiz.m20 * rm22;
            dest.m21 = thiz.m01 * rm20 + thiz.m11 * rm21 + thiz.m21 * rm22;
            dest.m22 = thiz.m02 * rm20 + thiz.m12 * rm21 + thiz.m22 * rm22;
            dest.m23 = thiz.m03 * rm20 + thiz.m13 * rm21 + thiz.m23 * rm22;
            dest.m00 = nm00;
            dest.m01 = nm01;
            dest.m02 = nm02;
            dest.m03 = nm03;
            dest.m10 = nm10;
            dest.m11 = nm11;
            dest.m12 = nm12;
            dest.m13 = nm13;
            return dest;
        }

        setLookAt(eyeX: number, eyeY: number, eyeZ: number,
                  centerX: number, centerY: number, centerZ: number,
                  upX: number, upY: number, upZ: number): Matrix4 {
            var thiz = this;
            var dirX, dirY, dirZ;
            dirX = eyeX - centerX;
            dirY = eyeY - centerY;
            dirZ = eyeZ - centerZ;
            var invDirLength = 1.0 / Math.sqrt(dirX * dirX + dirY * dirY + dirZ * dirZ);
            dirX *= invDirLength;
            dirY *= invDirLength;
            dirZ *= invDirLength;
            var leftX, leftY, leftZ;
            leftX = upY * dirZ - upZ * dirY;
            leftY = upZ * dirX - upX * dirZ;
            leftZ = upX * dirY - upY * dirX;
            var invLeftLength = 1.0 / Math.sqrt(leftX * leftX + leftY * leftY + leftZ * leftZ);
            leftX *= invLeftLength;
            leftY *= invLeftLength;
            leftZ *= invLeftLength;
            var upnX = dirY * leftZ - dirZ * leftY;
            var upnY = dirZ * leftX - dirX * leftZ;
            var upnZ = dirX * leftY - dirY * leftX;
            thiz.m00 = leftX;
            thiz.m01 = upnX;
            thiz.m02 = dirX;
            thiz.m03 = 0.0;
            thiz.m10 = leftY;
            thiz.m11 = upnY;
            thiz.m12 = dirY;
            thiz.m13 = 0.0;
            thiz.m20 = leftZ;
            thiz.m21 = upnZ;
            thiz.m22 = dirZ;
            thiz.m23 = 0.0;
            thiz.m30 = -(leftX * eyeX + leftY * eyeY + leftZ * eyeZ);
            thiz.m31 = -(upnX * eyeX + upnY * eyeY + upnZ * eyeZ);
            thiz.m32 = -(dirX * eyeX + dirY * eyeY + dirZ * eyeZ);
            thiz.m33 = 1.0;
            return this;
        }

        perspective(fovy: number, aspect: number, zNear: number, zFar: number, dest?: Matrix4): Matrix4;
        perspective(fovy: number, aspect: number, zNear: number, zFar: number, zZeroToOne: boolean, dest?: Matrix4): Matrix4;
        perspective(fovy: number, aspect: number, zNear: number, zFar: number, zZeroToOneDest: any, otherDest?: Matrix4): Matrix4 {
            var thiz = this;
            var dest: Matrix4, zZeroToOne: boolean;
            if (typeof (zZeroToOneDest) === 'boolean') {
                dest = otherDest || thiz;
                zZeroToOne = <boolean>zZeroToOneDest;
            } else {
                dest = zZeroToOneDest || thiz;
                zZeroToOne = false;
            }
            var h = Math.tan(fovy * 0.5);
            var rm00 = 1.0 / (h * aspect);
            var rm11 = 1.0 / h;
            var rm22;
            var rm32;
            var farInf = zFar > 0 && !isFinite(zFar);
            var nearInf = zNear > 0 && !isFinite(zNear);
            if (farInf) {
                var e = 1E-6;
                rm22 = e - 1.0;
                rm32 = (e - (zZeroToOne ? 1.0 : 2.0)) * zNear;
            } else if (nearInf) {
                var e = 1E-6;
                rm22 = (zZeroToOne ? 0.0 : 1.0) - e;
                rm32 = ((zZeroToOne ? 1.0 : 2.0) - e) * zFar;
            } else {
                rm22 = (zZeroToOne ? zFar : zFar + zNear) / (zNear - zFar);
                rm32 = (zZeroToOne ? zFar : zFar + zFar) * zNear / (zNear - zFar);
            }
            var nm20 = thiz.m20 * rm22 - thiz.m30;
            var nm21 = thiz.m21 * rm22 - thiz.m31;
            var nm22 = thiz.m22 * rm22 - thiz.m32;
            var nm23 = thiz.m23 * rm22 - thiz.m33;
            dest.m00 = thiz.m00 * rm00;
            dest.m01 = thiz.m01 * rm00;
            dest.m02 = thiz.m02 * rm00;
            dest.m03 = thiz.m03 * rm00;
            dest.m10 = thiz.m10 * rm11;
            dest.m11 = thiz.m11 * rm11;
            dest.m12 = thiz.m12 * rm11;
            dest.m13 = thiz.m13 * rm11;
            dest.m30 = thiz.m20 * rm32;
            dest.m31 = thiz.m21 * rm32;
            dest.m32 = thiz.m22 * rm32;
            dest.m33 = thiz.m23 * rm32;
            dest.m20 = nm20;
            dest.m21 = nm21;
            dest.m22 = nm22;
            dest.m23 = nm23;
            return dest;
        }

        setPerspective(fovy: number, aspect: number, zNear: number, zFar: number, zZeroToOne?: boolean): Matrix4 {
            var thiz = this;
            var h = Math.tan(fovy * 0.5);
            thiz.m00 = 1.0 / (h * aspect);
            thiz.m01 = 0.0;
            thiz.m02 = 0.0;
            thiz.m03 = 0.0;
            thiz.m10 = 0.0;
            thiz.m11 = 1.0 / h;
            thiz.m12 = 0.0;
            thiz.m13 = 0.0;
            thiz.m20 = 0.0;
            thiz.m21 = 0.0;
            var farInf = zFar > 0 && !isFinite(zFar);
            var nearInf = zNear > 0 && !isFinite(zNear);
            if (farInf) {
                var e = 1E-6;
                thiz.m22 = e - 1.0;
                thiz.m32 = (e - (zZeroToOne ? 1.0 : 2.0)) * zNear;
            } else if (nearInf) {
                var e = 1E-6;
                thiz.m22 = (zZeroToOne ? 0.0 : 1.0) - e;
                thiz.m32 = ((zZeroToOne ? 1.0 : 2.0) - e) * zFar;
            } else {
                thiz.m22 = (zZeroToOne ? zFar : zFar + zNear) / (zNear - zFar);
                thiz.m32 = (zZeroToOne ? zFar : zFar + zFar) * zNear / (zNear - zFar);
            }
            thiz.m23 = -1.0;
            thiz.m30 = 0.0;
            thiz.m31 = 0.0;
            thiz.m33 = 0.0;
            return this;
        }

        frustum(left: number, right: number, bottom: number, top: number, zNear: number, zFar: number, dest?: Matrix4): Matrix4;
        frustum(left: number, right: number, bottom: number, top: number, zNear: number, zFar: number, zZeroToOne: boolean, dest?: Matrix4): Matrix4;
        frustum(left: number, right: number, bottom: number, top: number, zNear: number, zFar: number, zZeroToOneDest: any, otherDest?: Matrix4): Matrix4 {
            var dest: Matrix4, zZeroToOne: boolean;
            var thiz = this;
            if (typeof (zZeroToOneDest) === 'boolean') {
                dest = otherDest || thiz;
                zZeroToOne = <boolean>zZeroToOneDest;
            } else {
                dest = zZeroToOneDest || thiz;
                zZeroToOne = false;
            }
            var rm00 = (zNear + zNear) / (right - left);
            var rm11 = (zNear + zNear) / (top - bottom);
            var rm20 = (right + left) / (right - left);
            var rm21 = (top + bottom) / (top - bottom);
            var rm22;
            var rm32;
            var farInf = zFar > 0 && !isFinite(zFar);
            var nearInf = zNear > 0 && !isFinite(zNear);
            if (farInf) {
                var e = 1E-6;
                rm22 = e - 1.0;
                rm32 = (e - (zZeroToOne ? 1.0 : 2.0)) * zNear;
            } else if (nearInf) {
                var e = 1E-6;
                rm22 = (zZeroToOne ? 0.0 : 1.0) - e;
                rm32 = ((zZeroToOne ? 1.0 : 2.0) - e) * zFar;
            } else {
                rm22 = (zZeroToOne ? zFar : zFar + zNear) / (zNear - zFar);
                rm32 = (zZeroToOne ? zFar : zFar + zFar) * zNear / (zNear - zFar);
            }
            // perform optimized matrix multiplication
            var nm20 = thiz.m00 * rm20 + thiz.m10 * rm21 + thiz.m20 * rm22 - thiz.m30;
            var nm21 = thiz.m01 * rm20 + thiz.m11 * rm21 + thiz.m21 * rm22 - thiz.m31;
            var nm22 = thiz.m02 * rm20 + thiz.m12 * rm21 + thiz.m22 * rm22 - thiz.m32;
            var nm23 = thiz.m03 * rm20 + thiz.m13 * rm21 + thiz.m23 * rm22 - thiz.m33;
            dest.m00 = thiz.m00 * rm00;
            dest.m01 = thiz.m01 * rm00;
            dest.m02 = thiz.m02 * rm00;
            dest.m03 = thiz.m03 * rm00;
            dest.m10 = thiz.m10 * rm11;
            dest.m11 = thiz.m11 * rm11;
            dest.m12 = thiz.m12 * rm11;
            dest.m13 = thiz.m13 * rm11;
            dest.m30 = thiz.m20 * rm32;
            dest.m31 = thiz.m21 * rm32;
            dest.m32 = thiz.m22 * rm32;
            dest.m33 = thiz.m23 * rm32;
            dest.m20 = nm20;
            dest.m21 = nm21;
            dest.m22 = nm22;
            dest.m23 = nm23;
            dest.m30 = thiz.m30;
            dest.m31 = thiz.m31;
            dest.m32 = thiz.m32;
            dest.m33 = thiz.m33;
            return dest;
        }

        project(x: number, y: number, z: number, viewport: number[], winCoordsDest: Vector4);
        project(x: number, y: number, z: number, viewport: number[], winCoordsDest: Vector3);
        project(x: number, y: number, z: number, viewport: number[], winCoordsDest: any) {
            var thiz = thiz;
            winCoordsDest.x = thiz.m00 * x + thiz.m10 * y + thiz.m20 * z + thiz.m30;
            winCoordsDest.y = thiz.m01 * x + thiz.m11 * y + thiz.m21 * z + thiz.m31;
            winCoordsDest.z = thiz.m02 * x + thiz.m12 * y + thiz.m22 * z + thiz.m32;
            if (winCoordsDest instanceof Vector3) {
                var w = thiz.m03 * x + thiz.m13 * y + thiz.m23 * z + thiz.m33;
                winCoordsDest.div(w);
                winCoordsDest.x = (winCoordsDest.x*0.5+0.5) * viewport[2] + viewport[0];
                winCoordsDest.y = (winCoordsDest.y*0.5+0.5) * viewport[3] + viewport[1];
                winCoordsDest.z = (1.0+winCoordsDest.z)*0.5;
                return winCoordsDest;
            } else {
                winCoordsDest.w = thiz.m03 * x + thiz.m13 * y + thiz.m23 * z + thiz.m33;
                winCoordsDest.div(winCoordsDest.w);
                winCoordsDest.x = (winCoordsDest.x*0.5+0.5) * viewport[2] + viewport[0];
                winCoordsDest.y = (winCoordsDest.y*0.5+0.5) * viewport[3] + viewport[1];
                winCoordsDest.z = (1.0+winCoordsDest.z)*0.5;
                return winCoordsDest;
            }
        }

        unproject(winX: number, winY: number, winZ: number, viewport: number[], dest: Vector4);
        unproject(winX: number, winY: number, winZ: number, viewport: number[], dest: Vector3)
        unproject(winX: number, winY: number, winZ: number, viewport: number[], dest: any) {
            var thiz = thiz;
            var a = thiz.m00 * thiz.m11 - thiz.m01 * thiz.m10;
            var b = thiz.m00 * thiz.m12 - thiz.m02 * thiz.m10;
            var c = thiz.m00 * thiz.m13 - thiz.m03 * thiz.m10;
            var d = thiz.m01 * thiz.m12 - thiz.m02 * thiz.m11;
            var e = thiz.m01 * thiz.m13 - thiz.m03 * thiz.m11;
            var f = thiz.m02 * thiz.m13 - thiz.m03 * thiz.m12;
            var g = thiz.m20 * thiz.m31 - thiz.m21 * thiz.m30;
            var h = thiz.m20 * thiz.m32 - thiz.m22 * thiz.m30;
            var i = thiz.m20 * thiz.m33 - thiz.m23 * thiz.m30;
            var j = thiz.m21 * thiz.m32 - thiz.m22 * thiz.m31;
            var k = thiz.m21 * thiz.m33 - thiz.m23 * thiz.m31;
            var l = thiz.m22 * thiz.m33 - thiz.m23 * thiz.m32;
            var det = a * l - b * k + c * j + d * i - e * h + f * g;
            det = 1.0 / det;
            var im00 = ( thiz.m11 * l - thiz.m12 * k + thiz.m13 * j) * det;
            var im01 = (-thiz.m01 * l + thiz.m02 * k - thiz.m03 * j) * det;
            var im02 = ( thiz.m31 * f - thiz.m32 * e + thiz.m33 * d) * det;
            var im03 = (-thiz.m21 * f + thiz.m22 * e - thiz.m23 * d) * det;
            var im10 = (-thiz.m10 * l + thiz.m12 * i - thiz.m13 * h) * det;
            var im11 = ( thiz.m00 * l - thiz.m02 * i + thiz.m03 * h) * det;
            var im12 = (-thiz.m30 * f + thiz.m32 * c - thiz.m33 * b) * det;
            var im13 = ( thiz.m20 * f - thiz.m22 * c + thiz.m23 * b) * det;
            var im20 = ( thiz.m10 * k - thiz.m11 * i + thiz.m13 * g) * det;
            var im21 = (-thiz.m00 * k + thiz.m01 * i - thiz.m03 * g) * det;
            var im22 = ( thiz.m30 * e - thiz.m31 * c + thiz.m33 * a) * det;
            var im23 = (-thiz.m20 * e + thiz.m21 * c - thiz.m23 * a) * det;
            var im30 = (-thiz.m10 * j + thiz.m11 * h - thiz.m12 * g) * det;
            var im31 = ( thiz.m00 * j - thiz.m01 * h + thiz.m02 * g) * det;
            var im32 = (-thiz.m30 * d + thiz.m31 * b - thiz.m32 * a) * det;
            var im33 = ( thiz.m20 * d - thiz.m21 * b + thiz.m22 * a) * det;
            var ndcX = (winX-viewport[0])/viewport[2]*2.0-1.0;
            var ndcY = (winY-viewport[1])/viewport[3]*2.0-1.0;
            var ndcZ = winZ+winZ-1.0;
            dest.x = im00 * ndcX + im10 * ndcY + im20 * ndcZ + im30;
            dest.y = im01 * ndcX + im11 * ndcY + im21 * ndcZ + im31;
            dest.z = im02 * ndcX + im12 * ndcY + im22 * ndcZ + im32;
            if (dest instanceof Vector4) {
                dest.w = im03 * ndcX + im13 * ndcY + im23 * ndcZ + im33;
                dest.div(dest.w);
            } else {
                var w = im03 * ndcX + im13 * ndcY + im23 * ndcZ + im33;
                dest.div(w);
            }
            return dest;
        }

        unprojectInv(winX: number, winY: number, winZ: number, viewport: number[], dest: Vector4);
        unprojectInv(winX: number, winY: number, winZ: number, viewport: number[], dest: Vector3);
        unprojectInv(winX: number, winY: number, winZ: number, viewport: number[], dest: any) {
            var thiz = thiz;
            var ndcX = (winX-viewport[0])/viewport[2]*2.0-1.0;
            var ndcY = (winY-viewport[1])/viewport[3]*2.0-1.0;
            var ndcZ = winZ+winZ-1.0;
            dest.x = thiz.m00 * ndcX + thiz.m10 * ndcY + thiz.m20 * ndcZ + thiz.m30;
            dest.y = thiz.m01 * ndcX + thiz.m11 * ndcY + thiz.m21 * ndcZ + thiz.m31;
            dest.z = thiz.m02 * ndcX + thiz.m12 * ndcY + thiz.m22 * ndcZ + thiz.m32;
            if (dest instanceof Vector4) {
                dest.w = thiz.m03 * ndcX + thiz.m13 * ndcY + thiz.m23 * ndcZ + thiz.m33;
                dest.div(dest.w);
            } else {
                var w = thiz.m03 * ndcX + thiz.m13 * ndcY + thiz.m23 * ndcZ + thiz.m33;
                dest.div(w);
            }
            return dest;
        }

        reflect(a: number, b: number, c: number, d: number, dest?: Matrix4): Matrix4 {
            var thiz = this;
            dest = dest || thiz;
            var da = a + a, db = b + b, dc = c + c, dd = d + d;
            var rm00 = 1.0 - da * a;
            var rm01 = -da * b;
            var rm02 = -da * c;
            var rm10 = -db * a;
            var rm11 = 1.0 - db * b;
            var rm12 = -db * c;
            var rm20 = -dc * a;
            var rm21 = -dc * b;
            var rm22 = 1.0 - dc * c;
            var rm30 = -dd * a;
            var rm31 = -dd * b;
            var rm32 = -dd * c;
            dest.m30 = thiz.m00 * rm30 + thiz.m10 * rm31 + thiz.m20 * rm32 + thiz.m30;
            dest.m31 = thiz.m01 * rm30 + thiz.m11 * rm31 + thiz.m21 * rm32 + thiz.m31;
            dest.m32 = thiz.m02 * rm30 + thiz.m12 * rm31 + thiz.m22 * rm32 + thiz.m32;
            dest.m33 = thiz.m03 * rm30 + thiz.m13 * rm31 + thiz.m23 * rm32 + thiz.m33;
            var nm00 = thiz.m00 * rm00 + thiz.m10 * rm01 + thiz.m20 * rm02;
            var nm01 = thiz.m01 * rm00 + thiz.m11 * rm01 + thiz.m21 * rm02;
            var nm02 = thiz.m02 * rm00 + thiz.m12 * rm01 + thiz.m22 * rm02;
            var nm03 = thiz.m03 * rm00 + thiz.m13 * rm01 + thiz.m23 * rm02;
            var nm10 = thiz.m00 * rm10 + thiz.m10 * rm11 + thiz.m20 * rm12;
            var nm11 = thiz.m01 * rm10 + thiz.m11 * rm11 + thiz.m21 * rm12;
            var nm12 = thiz.m02 * rm10 + thiz.m12 * rm11 + thiz.m22 * rm12;
            var nm13 = thiz.m03 * rm10 + thiz.m13 * rm11 + thiz.m23 * rm12;
            dest.m20 = thiz.m00 * rm20 + thiz.m10 * rm21 + thiz.m20 * rm22;
            dest.m21 = thiz.m01 * rm20 + thiz.m11 * rm21 + thiz.m21 * rm22;
            dest.m22 = thiz.m02 * rm20 + thiz.m12 * rm21 + thiz.m22 * rm22;
            dest.m23 = thiz.m03 * rm20 + thiz.m13 * rm21 + thiz.m23 * rm22;
            dest.m00 = nm00;
            dest.m01 = nm01;
            dest.m02 = nm02;
            dest.m03 = nm03;
            dest.m10 = nm10;
            dest.m11 = nm11;
            dest.m12 = nm12;
            dest.m13 = nm13;
            return dest;
        }

        reflection(a: number, b: number, c: number, d: number): Matrix4 {
            var da = a + a, db = b + b, dc = c + c, dd = d + d;
            var thiz = thiz;
            thiz.m00 = 1.0 - da * a;
            thiz.m01 = -da * b;
            thiz.m02 = -da * c;
            thiz.m03 = 0.0;
            thiz.m10 = -db * a;
            thiz.m11 = 1.0 - db * b;
            thiz.m12 = -db * c;
            thiz.m13 = 0.0;
            thiz.m20 = -dc * a;
            thiz.m21 = -dc * b;
            thiz.m22 = 1.0 - dc * c;
            thiz.m23 = 0.0;
            thiz.m30 = -dd * a;
            thiz.m31 = -dd * b;
            thiz.m32 = -dd * c;
            thiz.m33 = 1.0;
            return this;
        }

        normal(dest: Matrix4): Matrix4 {
            var thiz = thiz;
            var det = thiz.determinant3x3();
            var s = 1.0 / det;
            /* Invert and transpose in one go */
            dest.set((thiz.m11 * thiz.m22 - thiz.m21 * thiz.m12) * s,
                     (thiz.m20 * thiz.m12 - thiz.m10 * thiz.m22) * s,
                     (thiz.m10 * thiz.m21 - thiz.m20 * thiz.m11) * s,
                     0.0,
                     (thiz.m21 * thiz.m02 - thiz.m01 * thiz.m22) * s,
                     (thiz.m00 * thiz.m22 - thiz.m20 * thiz.m02) * s,
                     (thiz.m20 * thiz.m01 - thiz.m00 * thiz.m21) * s,
                     0.0,
                     (thiz.m01 * thiz.m12 - thiz.m11 * thiz.m02) * s,
                     (thiz.m10 * thiz.m02 - thiz.m00 * thiz.m12) * s,
                     (thiz.m00 * thiz.m11 - thiz.m10 * thiz.m01) * s,
                     0.0,
                     0.0, 0.0, 0.0, 1.0);
            return dest;
        }

        frustumPlane(plane: number, planeEquation: Vector4): Vector4 {
            var thiz = thiz;
            switch (plane) {
            case Matrix4.PLANE_NX:
                planeEquation.set(thiz.m03 + thiz.m00, thiz.m13 + thiz.m10, thiz.m23 + thiz.m20, thiz.m33 + thiz.m30).normalize3();
                break;
            case Matrix4.PLANE_PX:
                planeEquation.set(thiz.m03 - thiz.m00, thiz.m13 - thiz.m10, thiz.m23 - thiz.m20, thiz.m33 - thiz.m30).normalize3();
                break;
            case Matrix4.PLANE_NY:
                planeEquation.set(thiz.m03 + thiz.m01, thiz.m13 + thiz.m11, thiz.m23 + thiz.m21, thiz.m33 + thiz.m31).normalize3();
                break;
            case Matrix4.PLANE_PY:
                planeEquation.set(thiz.m03 - thiz.m01, thiz.m13 - thiz.m11, thiz.m23 - thiz.m21, thiz.m33 - thiz.m31).normalize3();
                break;
            case Matrix4.PLANE_NZ:
                planeEquation.set(thiz.m03 + thiz.m02, thiz.m13 + thiz.m12, thiz.m23 + thiz.m22, thiz.m33 + thiz.m32).normalize3();
                break;
            case Matrix4.PLANE_PZ:
                planeEquation.set(thiz.m03 - thiz.m02, thiz.m13 - thiz.m12, thiz.m23 - thiz.m22, thiz.m33 - thiz.m32).normalize3();
                break;
            default:
                throw "IllegalArgumentException: plane";
            }
            return planeEquation;
        }

        frustumCorner(corner: number, point: Vector3): Vector3 {
            var thiz = thiz;
            var d1, d2, d3;
            var n1x, n1y, n1z, n2x, n2y, n2z, n3x, n3y, n3z;
            switch (corner) {
            case Matrix4.CORNER_NXNYNZ: // left, bottom, near
                n1x = thiz.m03 + thiz.m00; n1y = thiz.m13 + thiz.m10; n1z = thiz.m23 + thiz.m20; d1 = thiz.m33 + thiz.m30; // left
                n2x = thiz.m03 + thiz.m01; n2y = thiz.m13 + thiz.m11; n2z = thiz.m23 + thiz.m21; d2 = thiz.m33 + thiz.m31; // bottom
                n3x = thiz.m03 + thiz.m02; n3y = thiz.m13 + thiz.m12; n3z = thiz.m23 + thiz.m22; d3 = thiz.m33 + thiz.m32; // near
                break;
            case Matrix4.CORNER_PXNYNZ: // right, bottom, near
                n1x = thiz.m03 - thiz.m00; n1y = thiz.m13 - thiz.m10; n1z = thiz.m23 - thiz.m20; d1 = thiz.m33 - thiz.m30; // right
                n2x = thiz.m03 + thiz.m01; n2y = thiz.m13 + thiz.m11; n2z = thiz.m23 + thiz.m21; d2 = thiz.m33 + thiz.m31; // bottom
                n3x = thiz.m03 + thiz.m02; n3y = thiz.m13 + thiz.m12; n3z = thiz.m23 + thiz.m22; d3 = thiz.m33 + thiz.m32; // near
                break;
            case Matrix4.CORNER_PXPYNZ: // right, top, near
                n1x = thiz.m03 - thiz.m00; n1y = thiz.m13 - thiz.m10; n1z = thiz.m23 - thiz.m20; d1 = thiz.m33 - thiz.m30; // right
                n2x = thiz.m03 - thiz.m01; n2y = thiz.m13 - thiz.m11; n2z = thiz.m23 - thiz.m21; d2 = thiz.m33 - thiz.m31; // top
                n3x = thiz.m03 + thiz.m02; n3y = thiz.m13 + thiz.m12; n3z = thiz.m23 + thiz.m22; d3 = thiz.m33 + thiz.m32; // near
                break;
            case Matrix4.CORNER_NXPYNZ: // left, top, near
                n1x = thiz.m03 + thiz.m00; n1y = thiz.m13 + thiz.m10; n1z = thiz.m23 + thiz.m20; d1 = thiz.m33 + thiz.m30; // left
                n2x = thiz.m03 - thiz.m01; n2y = thiz.m13 - thiz.m11; n2z = thiz.m23 - thiz.m21; d2 = thiz.m33 - thiz.m31; // top
                n3x = thiz.m03 + thiz.m02; n3y = thiz.m13 + thiz.m12; n3z = thiz.m23 + thiz.m22; d3 = thiz.m33 + thiz.m32; // near
                break;
            case Matrix4.CORNER_PXNYPZ: // right, bottom, far
                n1x = thiz.m03 - thiz.m00; n1y = thiz.m13 - thiz.m10; n1z = thiz.m23 - thiz.m20; d1 = thiz.m33 - thiz.m30; // right
                n2x = thiz.m03 + thiz.m01; n2y = thiz.m13 + thiz.m11; n2z = thiz.m23 + thiz.m21; d2 = thiz.m33 + thiz.m31; // bottom
                n3x = thiz.m03 - thiz.m02; n3y = thiz.m13 - thiz.m12; n3z = thiz.m23 - thiz.m22; d3 = thiz.m33 - thiz.m32; // far
                break;
            case Matrix4.CORNER_NXNYPZ: // left, bottom, far
                n1x = thiz.m03 + thiz.m00; n1y = thiz.m13 + thiz.m10; n1z = thiz.m23 + thiz.m20; d1 = thiz.m33 + thiz.m30; // left
                n2x = thiz.m03 + thiz.m01; n2y = thiz.m13 + thiz.m11; n2z = thiz.m23 + thiz.m21; d2 = thiz.m33 + thiz.m31; // bottom
                n3x = thiz.m03 - thiz.m02; n3y = thiz.m13 - thiz.m12; n3z = thiz.m23 - thiz.m22; d3 = thiz.m33 - thiz.m32; // far
                break;
            case Matrix4.CORNER_NXPYPZ: // left, top, far
                n1x = thiz.m03 + thiz.m00; n1y = thiz.m13 + thiz.m10; n1z = thiz.m23 + thiz.m20; d1 = thiz.m33 + thiz.m30; // left
                n2x = thiz.m03 - thiz.m01; n2y = thiz.m13 - thiz.m11; n2z = thiz.m23 - thiz.m21; d2 = thiz.m33 - thiz.m31; // top
                n3x = thiz.m03 - thiz.m02; n3y = thiz.m13 - thiz.m12; n3z = thiz.m23 - thiz.m22; d3 = thiz.m33 - thiz.m32; // far
                break;
            case Matrix4.CORNER_PXPYPZ: // right, top, far
                n1x = thiz.m03 - thiz.m00; n1y = thiz.m13 - thiz.m10; n1z = thiz.m23 - thiz.m20; d1 = thiz.m33 - thiz.m30; // right
                n2x = thiz.m03 - thiz.m01; n2y = thiz.m13 - thiz.m11; n2z = thiz.m23 - thiz.m21; d2 = thiz.m33 - thiz.m31; // top
                n3x = thiz.m03 - thiz.m02; n3y = thiz.m13 - thiz.m12; n3z = thiz.m23 - thiz.m22; d3 = thiz.m33 - thiz.m32; // far
                break;
            default:
                throw "IllegalArgumentException: corner";
            }
            var c23x, c23y, c23z;
            c23x = n2y * n3z - n2z * n3y;
            c23y = n2z * n3x - n2x * n3z;
            c23z = n2x * n3y - n2y * n3x;
            var c31x, c31y, c31z;
            c31x = n3y * n1z - n3z * n1y;
            c31y = n3z * n1x - n3x * n1z;
            c31z = n3x * n1y - n3y * n1x;
            var c12x, c12y, c12z;
            c12x = n1y * n2z - n1z * n2y;
            c12y = n1z * n2x - n1x * n2z;
            c12z = n1x * n2y - n1y * n2x;
            var invDot = 1.0 / (n1x * c23x + n1y * c23y + n1z * c23z);
            point.x = (-c23x * d1 - c31x * d2 - c12x * d3) * invDot;
            point.y = (-c23y * d1 - c31y * d2 - c12y * d3) * invDot;
            point.z = (-c23z * d1 - c31z * d2 - c12z * d3) * invDot;
            return point;
        }

        perspectiveOrigin(origin: Vector3): Vector3 {
            var thiz = thiz;
            var d1, d2, d3;
            var n1x, n1y, n1z, n2x, n2y, n2z, n3x, n3y, n3z;
            n1x = thiz.m03 + thiz.m00; n1y = thiz.m13 + thiz.m10; n1z = thiz.m23 + thiz.m20; d1 = thiz.m33 + thiz.m30; // left
            n2x = thiz.m03 - thiz.m00; n2y = thiz.m13 - thiz.m10; n2z = thiz.m23 - thiz.m20; d2 = thiz.m33 - thiz.m30; // right
            n3x = thiz.m03 - thiz.m01; n3y = thiz.m13 - thiz.m11; n3z = thiz.m23 - thiz.m21; d3 = thiz.m33 - thiz.m31; // top
            var c23x, c23y, c23z;
            c23x = n2y * n3z - n2z * n3y;
            c23y = n2z * n3x - n2x * n3z;
            c23z = n2x * n3y - n2y * n3x;
            var c31x, c31y, c31z;
            c31x = n3y * n1z - n3z * n1y;
            c31y = n3z * n1x - n3x * n1z;
            c31z = n3x * n1y - n3y * n1x;
            var c12x, c12y, c12z;
            c12x = n1y * n2z - n1z * n2y;
            c12y = n1z * n2x - n1x * n2z;
            c12z = n1x * n2y - n1y * n2x;
            var invDot = 1.0 / (n1x * c23x + n1y * c23y + n1z * c23z);
            origin.x = (-c23x * d1 - c31x * d2 - c12x * d3) * invDot;
            origin.y = (-c23y * d1 - c31y * d2 - c12y * d3) * invDot;
            origin.z = (-c23z * d1 - c31z * d2 - c12z * d3) * invDot;
            return origin;
        }

        perspectiveFov(): number {
            var thiz = thiz;
            var n1x, n1y, n1z, n2x, n2y, n2z;
            n1x = thiz.m03 + thiz.m01; n1y = thiz.m13 + thiz.m11; n1z = thiz.m23 + thiz.m21; // bottom
            n2x = thiz.m01 - thiz.m03; n2y = thiz.m11 - thiz.m13; n2z = thiz.m21 - thiz.m23; // top
            var n1len = Math.sqrt(n1x * n1x + n1y * n1y + n1z * n1z);
            var n2len = Math.sqrt(n2x * n2x + n2y * n2y + n2z * n2z);
            return Math.acos((n1x * n2x + n1y * n2y + n1z * n2z) / (n1len * n2len));
        }

        frustumRayDir(x: number, y: number, dir: Vector3): Vector3 {
            var thiz = thiz;
            var a = thiz.m10 * thiz.m23, b = thiz.m13 * thiz.m21, c = thiz.m10 * thiz.m21, d = thiz.m11 * thiz.m23, e = thiz.m13 * thiz.m20, f = thiz.m11 * thiz.m20;
            var g = thiz.m03 * thiz.m20, h = thiz.m01 * thiz.m23, i = thiz.m01 * thiz.m20, j = thiz.m03 * thiz.m21, k = thiz.m00 * thiz.m23, l = thiz.m00 * thiz.m21;
            var m = thiz.m00 * thiz.m13, n = thiz.m03 * thiz.m11, o = thiz.m00 * thiz.m11, p = thiz.m01 * thiz.m13, q = thiz.m03 * thiz.m10, r = thiz.m01 * thiz.m10;
            var m1x, m1y, m1z;
            m1x = (d + e + f - a - b - c) * (1.0 - y) + (a - b - c + d - e + f) * y;
            m1y = (j + k + l - g - h - i) * (1.0 - y) + (g - h - i + j - k + l) * y;
            m1z = (p + q + r - m - n - o) * (1.0 - y) + (m - n - o + p - q + r) * y;
            var m2x, m2y, m2z;
            m2x = (b - c - d + e + f - a) * (1.0 - y) + (a + b - c - d - e + f) * y;
            m2y = (h - i - j + k + l - g) * (1.0 - y) + (g + h - i - j - k + l) * y;
            m2z = (n - o - p + q + r - m) * (1.0 - y) + (m + n - o - p - q + r) * y;
            dir.x = m1x + (m2x - m1x) * x;
            dir.y = m1y + (m2y - m1y) * x;
            dir.z = m1z + (m2z - m1z) * x;
            dir.normalize();
            return dir;
        }

        positiveX(dir: Vector3): Vector3 {
            var thiz = this;
            dir.x = thiz.m11 * thiz.m22 - thiz.m12 * thiz.m21;
            dir.y = thiz.m02 * thiz.m21 - thiz.m01 * thiz.m22;
            dir.z = thiz.m01 * thiz.m12 - thiz.m02 * thiz.m11;
            dir.normalize();
            return dir;
        }

        normalizedPositiveX(dir: Vector3): Vector3 {
            var thiz = this;
            dir.x = thiz.m00;
            dir.y = thiz.m10;
            dir.z = thiz.m20;
            return dir;
        }

        positiveY(dir: Vector3): Vector3 {
            var thiz = this;
            dir.x = thiz.m12 * thiz.m20 - thiz.m10 * thiz.m22;
            dir.y = thiz.m00 * thiz.m22 - thiz.m02 * thiz.m20;
            dir.z = thiz.m02 * thiz.m10 - thiz.m00 * thiz.m12;
            dir.normalize();
            return dir;
        }

        normalizedPositiveY(dir: Vector3): Vector3 {
            var thiz = this;
            dir.x = thiz.m01;
            dir.y = thiz.m11;
            dir.z = thiz.m21;
            return dir;
        }

        positiveZ(dir: Vector3): Vector3 {
            var thiz = this;
            dir.x = thiz.m10 * thiz.m21 - thiz.m11 * thiz.m20;
            dir.y = thiz.m20 * thiz.m01 - thiz.m21 * thiz.m00;
            dir.z = thiz.m00 * thiz.m11 - thiz.m01 * thiz.m10;
            dir.normalize();
            return dir;
        }

        normalizedPositiveZ(dir: Vector3): Vector3 {
            var thiz = this;
            dir.x = thiz.m02;
            dir.y = thiz.m12;
            dir.z = thiz.m22;
            return dir;
        }

        origin(origin: Vector3): Vector3 {
            var thiz = this;
            var a = thiz.m00 * thiz.m11 - thiz.m01 * thiz.m10;
            var b = thiz.m00 * thiz.m12 - thiz.m02 * thiz.m10;
            var d = thiz.m01 * thiz.m12 - thiz.m02 * thiz.m11;
            var g = thiz.m20 * thiz.m31 - thiz.m21 * thiz.m30;
            var h = thiz.m20 * thiz.m32 - thiz.m22 * thiz.m30;
            var j = thiz.m21 * thiz.m32 - thiz.m22 * thiz.m31;
            origin.x = -thiz.m10 * j + thiz.m11 * h - thiz.m12 * g;
            origin.y =  thiz.m00 * j - thiz.m01 * h + thiz.m02 * g;
            origin.z = -thiz.m30 * d + thiz.m31 * b - thiz.m32 * a;
            return origin;
        }

        shadow(lightX: number, lightY: number, lightZ: number, lightW: number, a: number, b: number, c: number, d: number, dest?: Matrix4): Matrix4 {
            var thiz = this;
            dest = dest || thiz;
            var invPlaneLen = 1.0 / Math.sqrt(a*a + b*b + c*c);
            var an = a * invPlaneLen;
            var bn = b * invPlaneLen;
            var cn = c * invPlaneLen;
            var dn = d * invPlaneLen;
            var dot = an * lightX + bn * lightY + cn * lightZ + dn * lightW;
            var rm00 = dot - an * lightX;
            var rm01 = -an * lightY;
            var rm02 = -an * lightZ;
            var rm03 = -an * lightW;
            var rm10 = -bn * lightX;
            var rm11 = dot - bn * lightY;
            var rm12 = -bn * lightZ;
            var rm13 = -bn * lightW;
            var rm20 = -cn * lightX;
            var rm21 = -cn * lightY;
            var rm22 = dot - cn * lightZ;
            var rm23 = -cn * lightW;
            var rm30 = -dn * lightX;
            var rm31 = -dn * lightY;
            var rm32 = -dn * lightZ;
            var rm33 = dot - dn * lightW;
            var nm00 = thiz.m00 * rm00 + thiz.m10 * rm01 + thiz.m20 * rm02 + thiz.m30 * rm03;
            var nm01 = thiz.m01 * rm00 + thiz.m11 * rm01 + thiz.m21 * rm02 + thiz.m31 * rm03;
            var nm02 = thiz.m02 * rm00 + thiz.m12 * rm01 + thiz.m22 * rm02 + thiz.m32 * rm03;
            var nm03 = thiz.m03 * rm00 + thiz.m13 * rm01 + thiz.m23 * rm02 + thiz.m33 * rm03;
            var nm10 = thiz.m00 * rm10 + thiz.m10 * rm11 + thiz.m20 * rm12 + thiz.m30 * rm13;
            var nm11 = thiz.m01 * rm10 + thiz.m11 * rm11 + thiz.m21 * rm12 + thiz.m31 * rm13;
            var nm12 = thiz.m02 * rm10 + thiz.m12 * rm11 + thiz.m22 * rm12 + thiz.m32 * rm13;
            var nm13 = thiz.m03 * rm10 + thiz.m13 * rm11 + thiz.m23 * rm12 + thiz.m33 * rm13;
            var nm20 = thiz.m00 * rm20 + thiz.m10 * rm21 + thiz.m20 * rm22 + thiz.m30 * rm23;
            var nm21 = thiz.m01 * rm20 + thiz.m11 * rm21 + thiz.m21 * rm22 + thiz.m31 * rm23;
            var nm22 = thiz.m02 * rm20 + thiz.m12 * rm21 + thiz.m22 * rm22 + thiz.m32 * rm23;
            var nm23 = thiz.m03 * rm20 + thiz.m13 * rm21 + thiz.m23 * rm22 + thiz.m33 * rm23;
            dest.m30 = thiz.m00 * rm30 + thiz.m10 * rm31 + thiz.m20 * rm32 + thiz.m30 * rm33;
            dest.m31 = thiz.m01 * rm30 + thiz.m11 * rm31 + thiz.m21 * rm32 + thiz.m31 * rm33;
            dest.m32 = thiz.m02 * rm30 + thiz.m12 * rm31 + thiz.m22 * rm32 + thiz.m32 * rm33;
            dest.m33 = thiz.m03 * rm30 + thiz.m13 * rm31 + thiz.m23 * rm32 + thiz.m33 * rm33;
            dest.m00 = nm00;
            dest.m01 = nm01;
            dest.m02 = nm02;
            dest.m03 = nm03;
            dest.m10 = nm10;
            dest.m11 = nm11;
            dest.m12 = nm12;
            dest.m13 = nm13;
            dest.m20 = nm20;
            dest.m21 = nm21;
            dest.m22 = nm22;
            dest.m23 = nm23;
            return dest;
        }

        billboardCylindrical(objPos: Vector3, targetPos: Vector3, up: Vector3): Matrix4 {
            var thiz = thiz;
            var dirX = targetPos.x - objPos.x;
            var dirY = targetPos.y - objPos.y;
            var dirZ = targetPos.z - objPos.z;
            var leftX = up.y * dirZ - up.z * dirY;
            var leftY = up.z * dirX - up.x * dirZ;
            var leftZ = up.x * dirY - up.y * dirX;
            var invLeftLen = 1.0 / Math.sqrt(leftX * leftX + leftY * leftY + leftZ * leftZ);
            leftX *= invLeftLen;
            leftY *= invLeftLen;
            leftZ *= invLeftLen;
            dirX = leftY * up.z - leftZ * up.y;
            dirY = leftZ * up.x - leftX * up.z;
            dirZ = leftX * up.y - leftY * up.x;
            var invDirLen = 1.0 / Math.sqrt(dirX * dirX + dirY * dirY + dirZ * dirZ);
            dirX *= invDirLen;
            dirY *= invDirLen;
            dirZ *= invDirLen;
            thiz.m00 = leftX;
            thiz.m01 = leftY;
            thiz.m02 = leftZ;
            thiz.m03 = 0.0;
            thiz.m10 = up.x;
            thiz.m11 = up.y;
            thiz.m12 = up.z;
            thiz.m13 = 0.0;
            thiz.m20 = dirX;
            thiz.m21 = dirY;
            thiz.m22 = dirZ;
            thiz.m23 = 0.0;
            thiz.m30 = objPos.x;
            thiz.m31 = objPos.y;
            thiz.m32 = objPos.z;
            thiz.m33 = 1.0;
            return this;
        }

        billboardSpherical(objPos: Vector3, targetPos: Vector3, up?: Vector3): Matrix4 {
            var thiz = thiz;
            if (up) {
                var dirX = targetPos.x - objPos.x;
                var dirY = targetPos.y - objPos.y;
                var dirZ = targetPos.z - objPos.z;
                var invDirLen = 1.0 / Math.sqrt(dirX * dirX + dirY * dirY + dirZ * dirZ);
                dirX *= invDirLen;
                dirY *= invDirLen;
                dirZ *= invDirLen;
                var leftX = up.y * dirZ - up.z * dirY;
                var leftY = up.z * dirX - up.x * dirZ;
                var leftZ = up.x * dirY - up.y * dirX;
                var invLeftLen = 1.0 / Math.sqrt(leftX * leftX + leftY * leftY + leftZ * leftZ);
                leftX *= invLeftLen;
                leftY *= invLeftLen;
                leftZ *= invLeftLen;
                var upX = dirY * leftZ - dirZ * leftY;
                var upY = dirZ * leftX - dirX * leftZ;
                var upZ = dirX * leftY - dirY * leftX;
                thiz.m00 = leftX;
                thiz.m01 = leftY;
                thiz.m02 = leftZ;
                thiz.m03 = 0.0;
                thiz.m10 = upX;
                thiz.m11 = upY;
                thiz.m12 = upZ;
                thiz.m13 = 0.0;
                thiz.m20 = dirX;
                thiz.m21 = dirY;
                thiz.m22 = dirZ;
                thiz.m23 = 0.0;
                thiz.m30 = objPos.x;
                thiz.m31 = objPos.y;
                thiz.m32 = objPos.z;
                thiz.m33 = 1.0;
            } else {
                var toDirX = targetPos.x - objPos.x;
                var toDirY = targetPos.y - objPos.y;
                var toDirZ = targetPos.z - objPos.z;
                var x = -toDirY;
                var y = toDirX;
                var w = Math.sqrt(toDirX * toDirX + toDirY * toDirY + toDirZ * toDirZ) + toDirZ;
                var invNorm = 1.0 / Math.sqrt(x * x + y * y + w * w);
                x *= invNorm;
                y *= invNorm;
                w *= invNorm;
                var q00 = (x + x) * x;
                var q11 = (y + y) * y;
                var q01 = (x + x) * y;
                var q03 = (x + x) * w;
                var q13 = (y + y) * w;
                thiz.m00 = 1.0 - q11;
                thiz.m01 = q01;
                thiz.m02 = -q13;
                thiz.m03 = 0.0;
                thiz.m10 = q01;
                thiz.m11 = 1.0 - q00;
                thiz.m12 = q03;
                thiz.m13 = 0.0;
                thiz.m20 = q13;
                thiz.m21 = -q03;
                thiz.m22 = 1.0 - q11 - q00;
                thiz.m23 = 0.0;
                thiz.m30 = objPos.x;
                thiz.m31 = objPos.y;
                thiz.m32 = objPos.z;
                thiz.m33 = 1.0;
            }
            return this;
        }

        pick(x: number, y: number, width: number, height: number, viewport: number[], dest?: Matrix4): Matrix4 {
            var thiz = this;
            dest = dest || thiz;
            var sx = viewport[2] / width;
            var sy = viewport[3] / height;
            var tx = (viewport[2] + 2.0 * (viewport[0] - x)) / width;
            var ty = (viewport[3] + 2.0 * (viewport[1] - y)) / height;
            dest.m30 = thiz.m00 * tx + thiz.m10 * ty + thiz.m30;
            dest.m31 = thiz.m01 * tx + thiz.m11 * ty + thiz.m31;
            dest.m32 = thiz.m02 * tx + thiz.m12 * ty + thiz.m32;
            dest.m33 = thiz.m03 * tx + thiz.m13 * ty + thiz.m33;
            dest.m00 = thiz.m00 * sx;
            dest.m01 = thiz.m01 * sx;
            dest.m02 = thiz.m02 * sx;
            dest.m03 = thiz.m03 * sx;
            dest.m10 = thiz.m10 * sy;
            dest.m11 = thiz.m11 * sy;
            dest.m12 = thiz.m12 * sy;
            dest.m13 = thiz.m13 * sy;
            return dest;
        }

        isAffine(): boolean {
            var thiz = thiz;
            return thiz.m03 == 0.0 && thiz.m13 == 0.0 && thiz.m23 == 0.0 && thiz.m33 == 1.0;
        }

        arcball(radius: number, center: Vector3, angleX: number, angleY: number): Matrix4 {
            var thiz = thiz;
            return thiz.translate(0, 0, -radius).rotateX(angleX).rotateY(angleY).translate(-center.x, -center.y, -center.z);
        }
    }
}
