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
            this.m00 = 1.0;
            this.m01 = 0.0;
            this.m02 = 0.0;
            this.m03 = 0.0;
            this.m10 = 0.0;
            this.m11 = 1.0;
            this.m12 = 0.0;
            this.m13 = 0.0;
            this.m20 = 0.0;
            this.m21 = 0.0;
            this.m22 = 1.0;
            this.m23 = 0.0;
            this.m30 = 0.0;
            this.m31 = 0.0;
            this.m32 = 0.0;
            this.m33 = 1.0;
        }

        get(b: Float32Array): Float32Array {
            b[0] = this.m00;
            b[1] = this.m01;
            b[2] = this.m02;
            b[3] = this.m03;
            b[4] = this.m10;
            b[5] = this.m11;
            b[6] = this.m12;
            b[7] = this.m13;
            b[8] = this.m20;
            b[9] = this.m21;
            b[10] = this.m22;
            b[11] = this.m23;
            b[12] = this.m30;
            b[13] = this.m31;
            b[14] = this.m32;
            b[15] = this.m33;
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
            if (m00 instanceof Float32Array) {
                var b: Float32Array = m00;
                this.m01 = b[1];
                this.m02 = b[2];
                this.m03 = b[3];
                this.m10 = b[4];
                this.m11 = b[5];
                this.m12 = b[6];
                this.m13 = b[7];
                this.m20 = b[8];
                this.m21 = b[9];
                this.m22 = b[10];
                this.m23 = b[11];
                this.m30 = b[12];
                this.m31 = b[13];
                this.m32 = b[14];
                this.m33 = b[15];
            } else if (m00 instanceof Matrix4) {
                var m: Matrix4 = m00;
                this.m00 = m.m00;
                this.m01 = m.m01;
                this.m02 = m.m02;
                this.m03 = m.m03;
                this.m10 = m.m10;
                this.m11 = m.m11;
                this.m12 = m.m12;
                this.m13 = m.m13;
                this.m20 = m.m20;
                this.m21 = m.m21;
                this.m22 = m.m22;
                this.m23 = m.m23;
                this.m30 = m.m30;
                this.m31 = m.m31;
                this.m32 = m.m32;
                this.m33 = m.m33;
            } else {
                this.m00 = <number>m00;
                this.m01 = m01;
                this.m02 = m02;
                this.m03 = m03;
                this.m10 = m10;
                this.m11 = m11;
                this.m12 = m12;
                this.m13 = m13;
                this.m20 = m20;
                this.m21 = m21;
                this.m22 = m22;
                this.m23 = m23;
                this.m30 = m30;
                this.m31 = m31;
                this.m32 = m32;
                this.m33 = m33;
            }
            return this;
        }

        determinant(): number {
            return (this.m00 * this.m11 - this.m01 * this.m10) * (this.m22 * this.m33 - this.m23 * this.m32)
                 + (this.m02 * this.m10 - this.m00 * this.m12) * (this.m21 * this.m33 - this.m23 * this.m31)
                 + (this.m00 * this.m13 - this.m03 * this.m10) * (this.m21 * this.m32 - this.m22 * this.m31)
                 + (this.m01 * this.m12 - this.m02 * this.m11) * (this.m20 * this.m33 - this.m23 * this.m30)
                 + (this.m03 * this.m11 - this.m01 * this.m13) * (this.m20 * this.m32 - this.m22 * this.m30)
                 + (this.m02 * this.m13 - this.m03 * this.m12) * (this.m20 * this.m31 - this.m21 * this.m30);
        }

        determinant3x3(): number {
            return (this.m00 * this.m11 - this.m01 * this.m10) * this.m22
                 + (this.m02 * this.m10 - this.m00 * this.m12) * this.m21
                 + (this.m01 * this.m12 - this.m02 * this.m11) * this.m20;
        }

        invert(dest?: Matrix4): Matrix4 {
            dest = dest || this;
            var a = this.m00 * this.m11 - this.m01 * this.m10;
            var b = this.m00 * this.m12 - this.m02 * this.m10;
            var c = this.m00 * this.m13 - this.m03 * this.m10;
            var d = this.m01 * this.m12 - this.m02 * this.m11;
            var e = this.m01 * this.m13 - this.m03 * this.m11;
            var f = this.m02 * this.m13 - this.m03 * this.m12;
            var g = this.m20 * this.m31 - this.m21 * this.m30;
            var h = this.m20 * this.m32 - this.m22 * this.m30;
            var i = this.m20 * this.m33 - this.m23 * this.m30;
            var j = this.m21 * this.m32 - this.m22 * this.m31;
            var k = this.m21 * this.m33 - this.m23 * this.m31;
            var l = this.m22 * this.m33 - this.m23 * this.m32;
            var det = a * l - b * k + c * j + d * i - e * h + f * g;
            det = 1.0 / det;
            dest.set(( this.m11 * l - this.m12 * k + this.m13 * j) * det,
                     (-this.m01 * l + this.m02 * k - this.m03 * j) * det,
                     ( this.m31 * f - this.m32 * e + this.m33 * d) * det,
                     (-this.m21 * f + this.m22 * e - this.m23 * d) * det,
                     (-this.m10 * l + this.m12 * i - this.m13 * h) * det,
                     ( this.m00 * l - this.m02 * i + this.m03 * h) * det,
                     (-this.m30 * f + this.m32 * c - this.m33 * b) * det,
                     ( this.m20 * f - this.m22 * c + this.m23 * b) * det,
                     ( this.m10 * k - this.m11 * i + this.m13 * g) * det,
                     (-this.m00 * k + this.m01 * i - this.m03 * g) * det,
                     ( this.m30 * e - this.m31 * c + this.m33 * a) * det,
                     (-this.m20 * e + this.m21 * c - this.m23 * a) * det,
                     (-this.m10 * j + this.m11 * h - this.m12 * g) * det,
                     ( this.m00 * j - this.m01 * h + this.m02 * g) * det,
                     (-this.m30 * d + this.m31 * b - this.m32 * a) * det,
                     ( this.m20 * d - this.m21 * b + this.m22 * a) * det);
            return dest;
        }

        invertAffine(dest?: Matrix4): Matrix4 {
            dest = dest || this;
            var s = this.determinant3x3();
            s = 1.0 / s;
            var m10m22 = this.m10 * this.m22;
            var m10m21 = this.m10 * this.m21;
            var m10m02 = this.m10 * this.m02;
            var m10m01 = this.m10 * this.m01;
            var m11m22 = this.m11 * this.m22;
            var m11m20 = this.m11 * this.m20;
            var m11m02 = this.m11 * this.m02;
            var m11m00 = this.m11 * this.m00;
            var m12m21 = this.m12 * this.m21;
            var m12m20 = this.m12 * this.m20;
            var m12m01 = this.m12 * this.m01;
            var m12m00 = this.m12 * this.m00;
            var m20m02 = this.m20 * this.m02;
            var m20m01 = this.m20 * this.m01;
            var m21m02 = this.m21 * this.m02;
            var m21m00 = this.m21 * this.m00;
            var m22m01 = this.m22 * this.m01;
            var m22m00 = this.m22 * this.m00;
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
                     (m10m22 * this.m31 - m10m21 * this.m32 + m11m20 * this.m32 - m11m22 * this.m30 + m12m21 * this.m30 - m12m20 * this.m31) * s,
                     (m20m02 * this.m31 - m20m01 * this.m32 + m21m00 * this.m32 - m21m02 * this.m30 + m22m01 * this.m30 - m22m00 * this.m31) * s,
                     (m11m02 * this.m30 - m12m01 * this.m30 + m12m00 * this.m31 - m10m02 * this.m31 + m10m01 * this.m32 - m11m00 * this.m32) * s,
                     1.0);
            return dest;
        }

        transpose(dest?: Matrix4): Matrix4 {
            dest = dest || this;
            dest.set(this.m00, this.m10, this.m20, this.m30,
                     this.m01, this.m11, this.m21, this.m31,
                     this.m02, this.m12, this.m22, this.m32,
                     this.m03, this.m13, this.m23, this.m33);
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
            this.m00 = 1.0;
            this.m01 = 0.0;
            this.m02 = 0.0;
            this.m03 = 0.0;
            this.m10 = 0.0;
            this.m11 = 1.0;
            this.m12 = 0.0;
            this.m13 = 0.0;
            this.m20 = 0.0;
            this.m21 = 0.0;
            this.m22 = 1.0;
            this.m23 = 0.0;
            this.m30 = tx;
            this.m31 = ty;
            this.m32 = tz;
            this.m33 = 1.0;
            return this;
        }

        rotation(angle: number, x: number, y: number, z: number): Matrix4 {
            var cos = Math.cos(angle);
            var sin = Math.sin(angle);
            var C = 1.0 - cos;
            var xy = x * y, xz = x * z, yz = y * z;
            this.m00 = cos + x * x * C;
            this.m10 = xy * C - z * sin;
            this.m20 = xz * C + y * sin;
            this.m30 = 0.0;
            this.m01 = xy * C + z * sin;
            this.m11 = cos + y * y * C;
            this.m21 = yz * C - x * sin;
            this.m31 = 0.0;
            this.m02 = xz * C - y * sin;
            this.m12 = yz * C + x * sin;
            this.m22 = cos + z * z * C;
            this.m32 = 0.0;
            this.m03 = 0.0;
            this.m13 = 0.0;
            this.m23 = 0.0;
            this.m33 = 1.0;
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
            this.m00 = 1.0;
            this.m01 = 0.0;
            this.m02 = 0.0;
            this.m03 = 0.0;
            this.m10 = 0.0;
            this.m11 = cos;
            this.m12 = sin;
            this.m13 = 0.0;
            this.m20 = 0.0;
            this.m21 = -sin;
            this.m22 = cos;
            this.m23 = 0.0;
            this.m30 = 0.0;
            this.m31 = 0.0;
            this.m32 = 0.0;
            this.m33 = 1.0;
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
            this.m00 = cos;
            this.m01 = 0.0;
            this.m02 = -sin;
            this.m03 = 0.0;
            this.m10 = 0.0;
            this.m11 = 1.0;
            this.m12 = 0.0;
            this.m13 = 0.0;
            this.m20 = sin;
            this.m21 = 0.0;
            this.m22 = cos;
            this.m23 = 0.0;
            this.m30 = 0.0;
            this.m31 = 0.0;
            this.m32 = 0.0;
            this.m33 = 1.0;
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
            this.m00 = cos;
            this.m01 = sin;
            this.m02 = 0.0;
            this.m03 = 0.0;
            this.m10 = -sin;
            this.m11 = cos;
            this.m12 = 0.0;
            this.m13 = 0.0;
            this.m20 = 0.0;
            this.m21 = 0.0;
            this.m22 = 1.0;
            this.m23 = 0.0;
            this.m30 = 0.0;
            this.m31 = 0.0;
            this.m32 = 0.0;
            this.m33 = 1.0;
            return this;
        }

        zero(): Matrix4 {
            this.m00 = 0.0;
            this.m01 = 0.0;
            this.m02 = 0.0;
            this.m03 = 0.0;
            this.m10 = 0.0;
            this.m11 = 0.0;
            this.m12 = 0.0;
            this.m13 = 0.0;
            this.m20 = 0.0;
            this.m21 = 0.0;
            this.m22 = 0.0;
            this.m23 = 0.0;
            this.m30 = 0.0;
            this.m31 = 0.0;
            this.m32 = 0.0;
            this.m33 = 0.0;
            return this;
        }

        identity(): Matrix4 {
            this.m00 = 1.0;
            this.m01 = 0.0;
            this.m02 = 0.0;
            this.m03 = 0.0;
            this.m10 = 0.0;
            this.m11 = 1.0;
            this.m12 = 0.0;
            this.m13 = 0.0;
            this.m20 = 0.0;
            this.m21 = 0.0;
            this.m22 = 1.0;
            this.m23 = 0.0;
            this.m30 = 0.0;
            this.m31 = 0.0;
            this.m32 = 0.0;
            this.m33 = 1.0;
            return this;
        }

        scaling(x: number, y: number, z: number): Matrix4 {
            this.m00 = x;
            this.m01 = 0.0;
            this.m02 = 0.0;
            this.m03 = 0.0;
            this.m10 = 0.0;
            this.m11 = y;
            this.m12 = 0.0;
            this.m13 = 0.0;
            this.m20 = 0.0;
            this.m21 = 0.0;
            this.m22 = z;
            this.m23 = 0.0;
            this.m30 = 0.0;
            this.m31 = 0.0;
            this.m32 = 0.0;
            this.m33 = 1.0;
            return this;
        }

        transform(v: Vector4, dest?: Vector4): Vector4 {
            dest = dest || v;
            dest.set(this.m00 * v.x + this.m10 * v.y + this.m20 * v.z + this.m30 * v.w,
                     this.m01 * v.x + this.m11 * v.y + this.m21 * v.z + this.m31 * v.w,
                     this.m02 * v.x + this.m12 * v.y + this.m22 * v.z + this.m32 * v.w,
                     this.m03 * v.x + this.m13 * v.y + this.m23 * v.z + this.m33 * v.w);
            return dest;
        }

        transformProject(v: Vector3, dest?: Vector3): Vector3;
        transformProject(v: Vector4, dest?: Vector4): Vector4;
        transformProject(v: any, dest?: any): any {
            dest = dest || v;
            if (v instanceof Vector4) {
                var d: Vector4 = <Vector4>dest;
                var invW = 1.0 / (this.m03 * v.x + this.m13 * v.y + this.m23 * v.z + this.m33 * v.w);
                d.set((this.m00 * v.x + this.m10 * v.y + this.m20 * v.z + this.m30 * v.w) * invW,
                      (this.m01 * v.x + this.m11 * v.y + this.m21 * v.z + this.m31 * v.w) * invW,
                      (this.m02 * v.x + this.m12 * v.y + this.m22 * v.z + this.m32 * v.w) * invW,
                      1.0);
            } else {
                var d3: Vector3 = <Vector3>dest;
                var invW = 1.0 / (this.m03 * v.x + this.m13 * v.y + this.m23 * v.z + this.m33);
                d3.set((this.m00 * v.x + this.m10 * v.y + this.m20 * v.z + this.m30) * invW,
                       (this.m01 * v.x + this.m11 * v.y + this.m21 * v.z + this.m31) * invW,
                       (this.m02 * v.x + this.m12 * v.y + this.m22 * v.z + this.m32) * invW);
                return dest;
            }
            return dest;
        }

        transformPosition(v: Vector3, dest?: Vector3): Vector3 {
            dest = dest || v;
            dest.set(this.m00 * v.x + this.m10 * v.y + this.m20 * v.z + this.m30,
                     this.m01 * v.x + this.m11 * v.y + this.m21 * v.z + this.m31,
                     this.m02 * v.x + this.m12 * v.y + this.m22 * v.z + this.m32);
            return dest;
        }

        transformDirection(v: Vector3, dest?: Vector3): Vector3 {
            dest = dest || v;
            dest.set(this.m00 * v.x + this.m10 * v.y + this.m20 * v.z,
                     this.m01 * v.x + this.m11 * v.y + this.m21 * v.z,
                     this.m02 * v.x + this.m12 * v.y + this.m22 * v.z);
            return dest;
        }

        transformAffine(v: Vector4, dest?: Vector4): Vector4 {
            dest = dest || v;
            v.set(this.m00 * v.x + this.m10 * v.y + this.m20 * v.z + this.m30 * v.w,
                  this.m01 * v.x + this.m11 * v.y + this.m21 * v.z + this.m31 * v.w,
                  this.m02 * v.x + this.m12 * v.y + this.m22 * v.z + this.m32 * v.w,
                  v.w);
            return dest;
        }

        scale(x: number, y: number, z: number, dest?: Matrix4): Matrix4 {
            dest = dest || this;
            dest.m00 = this.m00 * x;
            dest.m01 = this.m01 * x;
            dest.m02 = this.m02 * x;
            dest.m03 = this.m03 * x;
            dest.m10 = this.m10 * y;
            dest.m11 = this.m11 * y;
            dest.m12 = this.m12 * y;
            dest.m13 = this.m13 * y;
            dest.m20 = this.m20 * z;
            dest.m21 = this.m21 * z;
            dest.m22 = this.m22 * z;
            dest.m23 = this.m23 * z;
            dest.m30 = this.m30;
            dest.m31 = this.m31;
            dest.m32 = this.m32;
            dest.m32 = this.m33;
            return dest;
        }

        rotateX(ang: number, dest?: Matrix4): Matrix4 {
            dest = dest || this;
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
            var nm10 = this.m10 * rm11 + this.m20 * rm12;
            var nm11 = this.m11 * rm11 + this.m21 * rm12;
            var nm12 = this.m12 * rm11 + this.m22 * rm12;
            var nm13 = this.m13 * rm11 + this.m23 * rm12;
            dest.m20 = this.m10 * rm21 + this.m20 * rm22;
            dest.m21 = this.m11 * rm21 + this.m21 * rm22;
            dest.m22 = this.m12 * rm21 + this.m22 * rm22;
            dest.m23 = this.m13 * rm21 + this.m23 * rm22;
            dest.m10 = nm10;
            dest.m11 = nm11;
            dest.m12 = nm12;
            dest.m13 = nm13;
            dest.m00 = this.m00;
            dest.m01 = this.m01;
            dest.m02 = this.m02;
            dest.m03 = this.m03;
            dest.m30 = this.m30;
            dest.m31 = this.m31;
            dest.m32 = this.m32;
            dest.m33 = this.m33;
            return dest;
        }

        rotateY(ang: number, dest?: Matrix4): Matrix4 {
            dest = dest || this;
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
            var nm00 = this.m00 * rm00 + this.m20 * rm02;
            var nm01 = this.m01 * rm00 + this.m21 * rm02;
            var nm02 = this.m02 * rm00 + this.m22 * rm02;
            var nm03 = this.m03 * rm00 + this.m23 * rm02;
            dest.m20 = this.m00 * rm20 + this.m20 * rm22;
            dest.m21 = this.m01 * rm20 + this.m21 * rm22;
            dest.m22 = this.m02 * rm20 + this.m22 * rm22;
            dest.m23 = this.m03 * rm20 + this.m23 * rm22;
            dest.m00 = nm00;
            dest.m01 = nm01;
            dest.m02 = nm02;
            dest.m03 = nm03;
            dest.m10 = this.m10;
            dest.m11 = this.m11;
            dest.m12 = this.m12;
            dest.m13 = this.m13;
            dest.m30 = this.m30;
            dest.m31 = this.m31;
            dest.m32 = this.m32;
            dest.m33 = this.m33;
            return dest;
        }

        rotateZ(ang: number, dest?: Matrix4): Matrix4 {
            dest = dest || this;
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
            var nm00 = this.m00 * rm00 + this.m10 * rm01;
            var nm01 = this.m01 * rm00 + this.m11 * rm01;
            var nm02 = this.m02 * rm00 + this.m12 * rm01;
            var nm03 = this.m03 * rm00 + this.m13 * rm01;
            dest.m10 = this.m00 * rm10 + this.m10 * rm11;
            dest.m11 = this.m01 * rm10 + this.m11 * rm11;
            dest.m12 = this.m02 * rm10 + this.m12 * rm11;
            dest.m13 = this.m03 * rm10 + this.m13 * rm11;
            dest.m00 = nm00;
            dest.m01 = nm01;
            dest.m02 = nm02;
            dest.m03 = nm03;
            dest.m20 = this.m20;
            dest.m21 = this.m21;
            dest.m22 = this.m22;
            dest.m23 = this.m23;
            dest.m30 = this.m30;
            dest.m31 = this.m31;
            dest.m32 = this.m32;
            dest.m33 = this.m33;
            return dest;
        }

        rotate(quat: Quaternion, dest?: Matrix4): Matrix4;
        rotate(ang: number, axis: Vector3, dest?: Matrix4): Matrix4;
        rotate(ang: number, x: number, y: number, z: number, dest?: Matrix4): Matrix4;
        rotate(ang: any, axisX: any, destY?: any, zParam?: number, otherDest?: Matrix4): Matrix4 {
            if (ang instanceof Quaternion) {
                return this.rotateQuaternion(<Quaternion>ang, <Matrix4>axisX);
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
            var nm00 = this.m00 * rm00 + this.m10 * rm01 + this.m20 * rm02;
            var nm01 = this.m01 * rm00 + this.m11 * rm01 + this.m21 * rm02;
            var nm02 = this.m02 * rm00 + this.m12 * rm01 + this.m22 * rm02;
            var nm03 = this.m03 * rm00 + this.m13 * rm01 + this.m23 * rm02;
            var nm10 = this.m00 * rm10 + this.m10 * rm11 + this.m20 * rm12;
            var nm11 = this.m01 * rm10 + this.m11 * rm11 + this.m21 * rm12;
            var nm12 = this.m02 * rm10 + this.m12 * rm11 + this.m22 * rm12;
            var nm13 = this.m03 * rm10 + this.m13 * rm11 + this.m23 * rm12;
            dest.m20 = this.m00 * rm20 + this.m10 * rm21 + this.m20 * rm22;
            dest.m21 = this.m01 * rm20 + this.m11 * rm21 + this.m21 * rm22;
            dest.m22 = this.m02 * rm20 + this.m12 * rm21 + this.m22 * rm22;
            dest.m23 = this.m03 * rm20 + this.m13 * rm21 + this.m23 * rm22;
            dest.m00 = nm00;
            dest.m01 = nm01;
            dest.m02 = nm02;
            dest.m03 = nm03;
            dest.m10 = nm10;
            dest.m11 = nm11;
            dest.m12 = nm12;
            dest.m13 = nm13;
            dest.m30 = this.m30;
            dest.m31 = this.m31;
            dest.m32 = this.m32;
            dest.m33 = this.m33;
            return dest;
        }

        private rotateQuaternion(quat: Quaternion, dest?: Matrix4): Matrix4 {
            dest = dest || this;
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
            var nm00 = this.m00 * rm00 + this.m10 * rm01 + this.m20 * rm02;
            var nm01 = this.m01 * rm00 + this.m11 * rm01 + this.m21 * rm02;
            var nm02 = this.m02 * rm00 + this.m12 * rm01 + this.m22 * rm02;
            var nm03 = this.m03 * rm00 + this.m13 * rm01 + this.m23 * rm02;
            var nm10 = this.m00 * rm10 + this.m10 * rm11 + this.m20 * rm12;
            var nm11 = this.m01 * rm10 + this.m11 * rm11 + this.m21 * rm12;
            var nm12 = this.m02 * rm10 + this.m12 * rm11 + this.m22 * rm12;
            var nm13 = this.m03 * rm10 + this.m13 * rm11 + this.m23 * rm12;
            dest.m20 = this.m00 * rm20 + this.m10 * rm21 + this.m20 * rm22;
            dest.m21 = this.m01 * rm20 + this.m11 * rm21 + this.m21 * rm22;
            dest.m22 = this.m02 * rm20 + this.m12 * rm21 + this.m22 * rm22;
            dest.m23 = this.m03 * rm20 + this.m13 * rm21 + this.m23 * rm22;
            dest.m00 = nm00;
            dest.m01 = nm01;
            dest.m02 = nm02;
            dest.m03 = nm03;
            dest.m10 = nm10;
            dest.m11 = nm11;
            dest.m12 = nm12;
            dest.m13 = nm13;
            dest.m30 = this.m30;
            dest.m31 = this.m31;
            dest.m32 = this.m32;
            dest.m33 = this.m33;
            return dest;
        }

        translate(offset: Vector3, dest?: Matrix4): Matrix4;
        translate(x: number, y: number, z: number, dest?: Matrix4): Matrix4;
        translate(offsetX: any, yDest?: any, zDest?: any, dest?: Matrix4): Matrix4 {
            var rm30: number;
            var rm31: number;
            var rm32: number;
            if (offsetX instanceof Vector3) {
                rm30 = offsetX.x;
                rm31 = offsetX.y;
                rm32 = offsetX.z;
                dest = <Matrix4>yDest || this;
            } else {
                rm30 = <number>offsetX;
                rm31 = <number>yDest;
                rm32 = <number>zDest;
                dest = dest || this;
            }
            dest.m30 = this.m00 * rm30 + this.m10 * rm31 + this.m20 * rm32 + this.m30;
            dest.m31 = this.m01 * rm30 + this.m11 * rm31 + this.m21 * rm32 + this.m31;
            dest.m32 = this.m02 * rm30 + this.m12 * rm31 + this.m22 * rm32 + this.m32;
            dest.m33 = this.m02 * rm30 + this.m12 * rm31 + this.m22 * rm32 + this.m33;
            dest.m00 = this.m00;
            dest.m01 = this.m01;
            dest.m02 = this.m02;
            dest.m03 = this.m03;
            dest.m10 = this.m10;
            dest.m11 = this.m11;
            dest.m12 = this.m12;
            dest.m13 = this.m13;
            dest.m20 = this.m20;
            dest.m21 = this.m21;
            dest.m22 = this.m22;
            dest.m23 = this.m23;
            return dest;
        }

        ortho(left: number, right: number, bottom: number, top: number, zNear: number, zFar: number, dest?: Matrix4): Matrix4;
        ortho(left: number, right: number, bottom: number, top: number, zNear: number, zFar: number, zZeroToOne: boolean, dest?: Matrix4): Matrix4
        ortho(left: number, right: number, bottom: number, top: number, zNear: number, zFar: number, zZeroToOneDest: any, otherDest?: Matrix4): Matrix4 {
            var dest: Matrix4, zZeroToOne: boolean;
            if (typeof (zZeroToOneDest) === 'boolean') {
                dest = otherDest || this;
                zZeroToOne = <boolean>zZeroToOneDest;
            } else {
                dest = zZeroToOneDest || this;
                zZeroToOne = false;
            }
            var rm00 = 2.0 / (right - left);
            var rm11 = 2.0 / (top - bottom);
            var rm22 = (zZeroToOne ? 1.0 : 2.0) / (zNear - zFar);
            var rm30 = (left + right) / (left - right);
            var rm31 = (top + bottom) / (bottom - top);
            var rm32 = (zZeroToOne ? zNear : (zFar + zNear)) / (zNear - zFar);
            dest.m30 = this.m00 * rm30 + this.m10 * rm31 + this.m20 * rm32 + this.m30;
            dest.m31 = this.m01 * rm30 + this.m11 * rm31 + this.m21 * rm32 + this.m31;
            dest.m32 = this.m02 * rm30 + this.m12 * rm31 + this.m22 * rm32 + this.m32;
            dest.m33 = this.m03 * rm30 + this.m13 * rm31 + this.m23 * rm32 + this.m33;
            dest.m00 = this.m00 * rm00;
            dest.m01 = this.m01 * rm00;
            dest.m02 = this.m02 * rm00;
            dest.m03 = this.m03 * rm00;
            dest.m10 = this.m10 * rm11;
            dest.m11 = this.m11 * rm11;
            dest.m12 = this.m12 * rm11;
            dest.m13 = this.m13 * rm11;
            dest.m20 = this.m20 * rm22;
            dest.m21 = this.m21 * rm22;
            dest.m22 = this.m22 * rm22;
            dest.m23 = this.m23 * rm22;
            return dest;
        }

        setOrtho(left: number, right: number, bottom: number, top: number, zNear: number, zFar: number, zZeroToOne?: boolean): Matrix4 {
            this.m00 = 2.0 / (right - left);
            this.m01 = 0.0;
            this.m02 = 0.0;
            this.m03 = 0.0;
            this.m10 = 0.0;
            this.m11 = 2.0 / (top - bottom);
            this.m12 = 0.0;
            this.m13 = 0.0;
            this.m20 = 0.0;
            this.m21 = 0.0;
            this.m22 = (zZeroToOne ? 1.0 : 2.0) / (zNear - zFar);
            this.m23 = 0.0;
            this.m30 = (right + left) / (left - right);
            this.m31 = (top + bottom) / (bottom - top);
            this.m32 = (zZeroToOne ? zNear : (zFar + zNear)) / (zNear - zFar);
            this.m33 = 1.0;
            return this;
        }

        ortho2D(left: number, right: number, bottom: number, top: number, dest?: Matrix4): Matrix4 {
            dest = dest || this;
            var rm00 = 2.0 / (right - left);
            var rm11 = 2.0 / (top - bottom);
            var rm30 = -(right + left) / (right - left);
            var rm31 = -(top + bottom) / (top - bottom);
            dest.m30 = this.m00 * rm30 + this.m10 * rm31 + this.m30;
            dest.m31 = this.m01 * rm30 + this.m11 * rm31 + this.m31;
            dest.m32 = this.m02 * rm30 + this.m12 * rm31 + this.m32;
            dest.m33 = this.m03 * rm30 + this.m13 * rm31 + this.m33;
            dest.m00 = this.m00 * rm00;
            dest.m01 = this.m01 * rm00;
            dest.m02 = this.m02 * rm00;
            dest.m03 = this.m03 * rm00;
            dest.m10 = this.m10 * rm11;
            dest.m11 = this.m11 * rm11;
            dest.m12 = this.m12 * rm11;
            dest.m13 = this.m13 * rm11;
            dest.m20 = -this.m20;
            dest.m21 = -this.m21;
            dest.m22 = -this.m22;
            dest.m23 = -this.m23;
            return dest;
        }

        lookAt(eyeX: number, eyeY: number, eyeZ: number,
               centerX: number, centerY: number, centerZ: number,
               upX: number, upY: number, upZ: number, dest?: Matrix4): Matrix4 {
            dest = dest || this;
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
            dest.m30 = this.m00 * rm30 + this.m10 * rm31 + this.m20 * rm32 + this.m30;
            dest.m31 = this.m01 * rm30 + this.m11 * rm31 + this.m21 * rm32 + this.m31;
            dest.m32 = this.m02 * rm30 + this.m12 * rm31 + this.m22 * rm32 + this.m32;
            dest.m33 = this.m03 * rm30 + this.m13 * rm31 + this.m23 * rm32 + this.m33;
            var nm00 = this.m00 * rm00 + this.m10 * rm01 + this.m20 * rm02;
            var nm01 = this.m01 * rm00 + this.m11 * rm01 + this.m21 * rm02;
            var nm02 = this.m02 * rm00 + this.m12 * rm01 + this.m22 * rm02;
            var nm03 = this.m03 * rm00 + this.m13 * rm01 + this.m23 * rm02;
            var nm10 = this.m00 * rm10 + this.m10 * rm11 + this.m20 * rm12;
            var nm11 = this.m01 * rm10 + this.m11 * rm11 + this.m21 * rm12;
            var nm12 = this.m02 * rm10 + this.m12 * rm11 + this.m22 * rm12;
            var nm13 = this.m03 * rm10 + this.m13 * rm11 + this.m23 * rm12;
            dest.m20 = this.m00 * rm20 + this.m10 * rm21 + this.m20 * rm22;
            dest.m21 = this.m01 * rm20 + this.m11 * rm21 + this.m21 * rm22;
            dest.m22 = this.m02 * rm20 + this.m12 * rm21 + this.m22 * rm22;
            dest.m23 = this.m03 * rm20 + this.m13 * rm21 + this.m23 * rm22;
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
        perspective(fovy: number, aspect: number, zNear: number, zFar: number, dest?: Matrix4): Matrix4;
        perspective(fovy: number, aspect: number, zNear: number, zFar: number, zZeroToOne: boolean, dest?: Matrix4): Matrix4;
        perspective(fovy: number, aspect: number, zNear: number, zFar: number, zZeroToOneDest: any, otherDest?: Matrix4): Matrix4 {
            var dest: Matrix4, zZeroToOne: boolean;
            if (typeof (zZeroToOneDest) === 'boolean') {
                dest = otherDest || this;
                zZeroToOne = <boolean>zZeroToOneDest;
            } else {
                dest = zZeroToOneDest || this;
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
            var nm20 = this.m20 * rm22 - this.m30;
            var nm21 = this.m21 * rm22 - this.m31;
            var nm22 = this.m22 * rm22 - this.m32;
            var nm23 = this.m23 * rm22 - this.m33;
            dest.m00 = this.m00 * rm00;
            dest.m01 = this.m01 * rm00;
            dest.m02 = this.m02 * rm00;
            dest.m03 = this.m03 * rm00;
            dest.m10 = this.m10 * rm11;
            dest.m11 = this.m11 * rm11;
            dest.m12 = this.m12 * rm11;
            dest.m13 = this.m13 * rm11;
            dest.m30 = this.m20 * rm32;
            dest.m31 = this.m21 * rm32;
            dest.m32 = this.m22 * rm32;
            dest.m33 = this.m23 * rm32;
            dest.m20 = nm20;
            dest.m21 = nm21;
            dest.m22 = nm22;
            dest.m23 = nm23;
            return dest;
        }

        frustum(left: number, right: number, bottom: number, top: number, zNear: number, zFar: number, dest?: Matrix4): Matrix4;
        frustum(left: number, right: number, bottom: number, top: number, zNear: number, zFar: number, zZeroToOne: boolean, dest?: Matrix4): Matrix4;
        frustum(left: number, right: number, bottom: number, top: number, zNear: number, zFar: number, zZeroToOneDest: any, otherDest?: Matrix4): Matrix4 {
            var dest: Matrix4, zZeroToOne: boolean;
            if (typeof (zZeroToOneDest) === 'boolean') {
                dest = otherDest || this;
                zZeroToOne = <boolean>zZeroToOneDest;
            } else {
                dest = zZeroToOneDest || this;
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
            var nm20 = this.m00 * rm20 + this.m10 * rm21 + this.m20 * rm22 - this.m30;
            var nm21 = this.m01 * rm20 + this.m11 * rm21 + this.m21 * rm22 - this.m31;
            var nm22 = this.m02 * rm20 + this.m12 * rm21 + this.m22 * rm22 - this.m32;
            var nm23 = this.m03 * rm20 + this.m13 * rm21 + this.m23 * rm22 - this.m33;
            dest.m00 = this.m00 * rm00;
            dest.m01 = this.m01 * rm00;
            dest.m02 = this.m02 * rm00;
            dest.m03 = this.m03 * rm00;
            dest.m10 = this.m10 * rm11;
            dest.m11 = this.m11 * rm11;
            dest.m12 = this.m12 * rm11;
            dest.m13 = this.m13 * rm11;
            dest.m30 = this.m20 * rm32;
            dest.m31 = this.m21 * rm32;
            dest.m32 = this.m22 * rm32;
            dest.m33 = this.m23 * rm32;
            dest.m20 = nm20;
            dest.m21 = nm21;
            dest.m22 = nm22;
            dest.m23 = nm23;
            dest.m30 = this.m30;
            dest.m31 = this.m31;
            dest.m32 = this.m32;
            dest.m33 = this.m33;
            return dest;
        }

        project(x: number, y: number, z: number, viewport: number[], winCoordsDest: Vector4);
        project(x: number, y: number, z: number, viewport: number[], winCoordsDest: Vector3);
        project(x: number, y: number, z: number, viewport: number[], winCoordsDest: any) {
            winCoordsDest.x = this.m00 * x + this.m10 * y + this.m20 * z + this.m30;
            winCoordsDest.y = this.m01 * x + this.m11 * y + this.m21 * z + this.m31;
            winCoordsDest.z = this.m02 * x + this.m12 * y + this.m22 * z + this.m32;
            if (winCoordsDest instanceof Vector3) {
                var w = this.m03 * x + this.m13 * y + this.m23 * z + this.m33;
                winCoordsDest.div(w);
                winCoordsDest.x = (winCoordsDest.x*0.5+0.5) * viewport[2] + viewport[0];
                winCoordsDest.y = (winCoordsDest.y*0.5+0.5) * viewport[3] + viewport[1];
                winCoordsDest.z = (1.0+winCoordsDest.z)*0.5;
                return winCoordsDest;
            } else {
                winCoordsDest.w = this.m03 * x + this.m13 * y + this.m23 * z + this.m33;
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
            var a = this.m00 * this.m11 - this.m01 * this.m10;
            var b = this.m00 * this.m12 - this.m02 * this.m10;
            var c = this.m00 * this.m13 - this.m03 * this.m10;
            var d = this.m01 * this.m12 - this.m02 * this.m11;
            var e = this.m01 * this.m13 - this.m03 * this.m11;
            var f = this.m02 * this.m13 - this.m03 * this.m12;
            var g = this.m20 * this.m31 - this.m21 * this.m30;
            var h = this.m20 * this.m32 - this.m22 * this.m30;
            var i = this.m20 * this.m33 - this.m23 * this.m30;
            var j = this.m21 * this.m32 - this.m22 * this.m31;
            var k = this.m21 * this.m33 - this.m23 * this.m31;
            var l = this.m22 * this.m33 - this.m23 * this.m32;
            var det = a * l - b * k + c * j + d * i - e * h + f * g;
            det = 1.0 / det;
            var im00 = ( this.m11 * l - this.m12 * k + this.m13 * j) * det;
            var im01 = (-this.m01 * l + this.m02 * k - this.m03 * j) * det;
            var im02 = ( this.m31 * f - this.m32 * e + this.m33 * d) * det;
            var im03 = (-this.m21 * f + this.m22 * e - this.m23 * d) * det;
            var im10 = (-this.m10 * l + this.m12 * i - this.m13 * h) * det;
            var im11 = ( this.m00 * l - this.m02 * i + this.m03 * h) * det;
            var im12 = (-this.m30 * f + this.m32 * c - this.m33 * b) * det;
            var im13 = ( this.m20 * f - this.m22 * c + this.m23 * b) * det;
            var im20 = ( this.m10 * k - this.m11 * i + this.m13 * g) * det;
            var im21 = (-this.m00 * k + this.m01 * i - this.m03 * g) * det;
            var im22 = ( this.m30 * e - this.m31 * c + this.m33 * a) * det;
            var im23 = (-this.m20 * e + this.m21 * c - this.m23 * a) * det;
            var im30 = (-this.m10 * j + this.m11 * h - this.m12 * g) * det;
            var im31 = ( this.m00 * j - this.m01 * h + this.m02 * g) * det;
            var im32 = (-this.m30 * d + this.m31 * b - this.m32 * a) * det;
            var im33 = ( this.m20 * d - this.m21 * b + this.m22 * a) * det;
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
            var ndcX = (winX-viewport[0])/viewport[2]*2.0-1.0;
            var ndcY = (winY-viewport[1])/viewport[3]*2.0-1.0;
            var ndcZ = winZ+winZ-1.0;
            dest.x = this.m00 * ndcX + this.m10 * ndcY + this.m20 * ndcZ + this.m30;
            dest.y = this.m01 * ndcX + this.m11 * ndcY + this.m21 * ndcZ + this.m31;
            dest.z = this.m02 * ndcX + this.m12 * ndcY + this.m22 * ndcZ + this.m32;
            if (dest instanceof Vector4) {
                dest.w = this.m03 * ndcX + this.m13 * ndcY + this.m23 * ndcZ + this.m33;
                dest.div(dest.w);
            } else {
                var w = this.m03 * ndcX + this.m13 * ndcY + this.m23 * ndcZ + this.m33;
                dest.div(w);
            }
            return dest;
        }

        reflect(a: number, b: number, c: number, d: number, dest?: Matrix4): Matrix4 {
            dest = dest || this;
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
            dest.m30 = this.m00 * rm30 + this.m10 * rm31 + this.m20 * rm32 + this.m30;
            dest.m31 = this.m01 * rm30 + this.m11 * rm31 + this.m21 * rm32 + this.m31;
            dest.m32 = this.m02 * rm30 + this.m12 * rm31 + this.m22 * rm32 + this.m32;
            dest.m33 = this.m03 * rm30 + this.m13 * rm31 + this.m23 * rm32 + this.m33;
            var nm00 = this.m00 * rm00 + this.m10 * rm01 + this.m20 * rm02;
            var nm01 = this.m01 * rm00 + this.m11 * rm01 + this.m21 * rm02;
            var nm02 = this.m02 * rm00 + this.m12 * rm01 + this.m22 * rm02;
            var nm03 = this.m03 * rm00 + this.m13 * rm01 + this.m23 * rm02;
            var nm10 = this.m00 * rm10 + this.m10 * rm11 + this.m20 * rm12;
            var nm11 = this.m01 * rm10 + this.m11 * rm11 + this.m21 * rm12;
            var nm12 = this.m02 * rm10 + this.m12 * rm11 + this.m22 * rm12;
            var nm13 = this.m03 * rm10 + this.m13 * rm11 + this.m23 * rm12;
            dest.m20 = this.m00 * rm20 + this.m10 * rm21 + this.m20 * rm22;
            dest.m21 = this.m01 * rm20 + this.m11 * rm21 + this.m21 * rm22;
            dest.m22 = this.m02 * rm20 + this.m12 * rm21 + this.m22 * rm22;
            dest.m23 = this.m03 * rm20 + this.m13 * rm21 + this.m23 * rm22;
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
            this.m00 = 1.0 - da * a;
            this.m01 = -da * b;
            this.m02 = -da * c;
            this.m03 = 0.0;
            this.m10 = -db * a;
            this.m11 = 1.0 - db * b;
            this.m12 = -db * c;
            this.m13 = 0.0;
            this.m20 = -dc * a;
            this.m21 = -dc * b;
            this.m22 = 1.0 - dc * c;
            this.m23 = 0.0;
            this.m30 = -dd * a;
            this.m31 = -dd * b;
            this.m32 = -dd * c;
            this.m33 = 1.0;
            return this;
        }

        normal(dest: Matrix4): Matrix4 {
            var det = this.determinant3x3();
            var s = 1.0 / det;
            /* Invert and transpose in one go */
            dest.set((this.m11 * this.m22 - this.m21 * this.m12) * s,
                     (this.m20 * this.m12 - this.m10 * this.m22) * s,
                     (this.m10 * this.m21 - this.m20 * this.m11) * s,
                     0.0,
                     (this.m21 * this.m02 - this.m01 * this.m22) * s,
                     (this.m00 * this.m22 - this.m20 * this.m02) * s,
                     (this.m20 * this.m01 - this.m00 * this.m21) * s,
                     0.0,
                     (this.m01 * this.m12 - this.m11 * this.m02) * s,
                     (this.m10 * this.m02 - this.m00 * this.m12) * s,
                     (this.m00 * this.m11 - this.m10 * this.m01) * s,
                     0.0,
                     0.0, 0.0, 0.0, 1.0);
            return dest;
        }

        frustumPlane(plane: number, planeEquation: Vector4): Vector4 {
            switch (plane) {
            case Matrix4.PLANE_NX:
                planeEquation.set(this.m03 + this.m00, this.m13 + this.m10, this.m23 + this.m20, this.m33 + this.m30).normalize3();
                break;
            case Matrix4.PLANE_PX:
                planeEquation.set(this.m03 - this.m00, this.m13 - this.m10, this.m23 - this.m20, this.m33 - this.m30).normalize3();
                break;
            case Matrix4.PLANE_NY:
                planeEquation.set(this.m03 + this.m01, this.m13 + this.m11, this.m23 + this.m21, this.m33 + this.m31).normalize3();
                break;
            case Matrix4.PLANE_PY:
                planeEquation.set(this.m03 - this.m01, this.m13 - this.m11, this.m23 - this.m21, this.m33 - this.m31).normalize3();
                break;
            case Matrix4.PLANE_NZ:
                planeEquation.set(this.m03 + this.m02, this.m13 + this.m12, this.m23 + this.m22, this.m33 + this.m32).normalize3();
                break;
            case Matrix4.PLANE_PZ:
                planeEquation.set(this.m03 - this.m02, this.m13 - this.m12, this.m23 - this.m22, this.m33 - this.m32).normalize3();
                break;
            default:
                throw "IllegalArgumentException: plane";
            }
            return planeEquation;
        }

        frustumCorner(corner: number, point: Vector3): Vector3 {
            var d1, d2, d3;
            var n1x, n1y, n1z, n2x, n2y, n2z, n3x, n3y, n3z;
            switch (corner) {
            case Matrix4.CORNER_NXNYNZ: // left, bottom, near
                n1x = this.m03 + this.m00; n1y = this.m13 + this.m10; n1z = this.m23 + this.m20; d1 = this.m33 + this.m30; // left
                n2x = this.m03 + this.m01; n2y = this.m13 + this.m11; n2z = this.m23 + this.m21; d2 = this.m33 + this.m31; // bottom
                n3x = this.m03 + this.m02; n3y = this.m13 + this.m12; n3z = this.m23 + this.m22; d3 = this.m33 + this.m32; // near
                break;
            case Matrix4.CORNER_PXNYNZ: // right, bottom, near
                n1x = this.m03 - this.m00; n1y = this.m13 - this.m10; n1z = this.m23 - this.m20; d1 = this.m33 - this.m30; // right
                n2x = this.m03 + this.m01; n2y = this.m13 + this.m11; n2z = this.m23 + this.m21; d2 = this.m33 + this.m31; // bottom
                n3x = this.m03 + this.m02; n3y = this.m13 + this.m12; n3z = this.m23 + this.m22; d3 = this.m33 + this.m32; // near
                break;
            case Matrix4.CORNER_PXPYNZ: // right, top, near
                n1x = this.m03 - this.m00; n1y = this.m13 - this.m10; n1z = this.m23 - this.m20; d1 = this.m33 - this.m30; // right
                n2x = this.m03 - this.m01; n2y = this.m13 - this.m11; n2z = this.m23 - this.m21; d2 = this.m33 - this.m31; // top
                n3x = this.m03 + this.m02; n3y = this.m13 + this.m12; n3z = this.m23 + this.m22; d3 = this.m33 + this.m32; // near
                break;
            case Matrix4.CORNER_NXPYNZ: // left, top, near
                n1x = this.m03 + this.m00; n1y = this.m13 + this.m10; n1z = this.m23 + this.m20; d1 = this.m33 + this.m30; // left
                n2x = this.m03 - this.m01; n2y = this.m13 - this.m11; n2z = this.m23 - this.m21; d2 = this.m33 - this.m31; // top
                n3x = this.m03 + this.m02; n3y = this.m13 + this.m12; n3z = this.m23 + this.m22; d3 = this.m33 + this.m32; // near
                break;
            case Matrix4.CORNER_PXNYPZ: // right, bottom, far
                n1x = this.m03 - this.m00; n1y = this.m13 - this.m10; n1z = this.m23 - this.m20; d1 = this.m33 - this.m30; // right
                n2x = this.m03 + this.m01; n2y = this.m13 + this.m11; n2z = this.m23 + this.m21; d2 = this.m33 + this.m31; // bottom
                n3x = this.m03 - this.m02; n3y = this.m13 - this.m12; n3z = this.m23 - this.m22; d3 = this.m33 - this.m32; // far
                break;
            case Matrix4.CORNER_NXNYPZ: // left, bottom, far
                n1x = this.m03 + this.m00; n1y = this.m13 + this.m10; n1z = this.m23 + this.m20; d1 = this.m33 + this.m30; // left
                n2x = this.m03 + this.m01; n2y = this.m13 + this.m11; n2z = this.m23 + this.m21; d2 = this.m33 + this.m31; // bottom
                n3x = this.m03 - this.m02; n3y = this.m13 - this.m12; n3z = this.m23 - this.m22; d3 = this.m33 - this.m32; // far
                break;
            case Matrix4.CORNER_NXPYPZ: // left, top, far
                n1x = this.m03 + this.m00; n1y = this.m13 + this.m10; n1z = this.m23 + this.m20; d1 = this.m33 + this.m30; // left
                n2x = this.m03 - this.m01; n2y = this.m13 - this.m11; n2z = this.m23 - this.m21; d2 = this.m33 - this.m31; // top
                n3x = this.m03 - this.m02; n3y = this.m13 - this.m12; n3z = this.m23 - this.m22; d3 = this.m33 - this.m32; // far
                break;
            case Matrix4.CORNER_PXPYPZ: // right, top, far
                n1x = this.m03 - this.m00; n1y = this.m13 - this.m10; n1z = this.m23 - this.m20; d1 = this.m33 - this.m30; // right
                n2x = this.m03 - this.m01; n2y = this.m13 - this.m11; n2z = this.m23 - this.m21; d2 = this.m33 - this.m31; // top
                n3x = this.m03 - this.m02; n3y = this.m13 - this.m12; n3z = this.m23 - this.m22; d3 = this.m33 - this.m32; // far
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
            var d1, d2, d3;
            var n1x, n1y, n1z, n2x, n2y, n2z, n3x, n3y, n3z;
            n1x = this.m03 + this.m00; n1y = this.m13 + this.m10; n1z = this.m23 + this.m20; d1 = this.m33 + this.m30; // left
            n2x = this.m03 - this.m00; n2y = this.m13 - this.m10; n2z = this.m23 - this.m20; d2 = this.m33 - this.m30; // right
            n3x = this.m03 - this.m01; n3y = this.m13 - this.m11; n3z = this.m23 - this.m21; d3 = this.m33 - this.m31; // top
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
            var n1x, n1y, n1z, n2x, n2y, n2z;
            n1x = this.m03 + this.m01; n1y = this.m13 + this.m11; n1z = this.m23 + this.m21; // bottom
            n2x = this.m01 - this.m03; n2y = this.m11 - this.m13; n2z = this.m21 - this.m23; // top
            var n1len = Math.sqrt(n1x * n1x + n1y * n1y + n1z * n1z);
            var n2len = Math.sqrt(n2x * n2x + n2y * n2y + n2z * n2z);
            return Math.acos((n1x * n2x + n1y * n2y + n1z * n2z) / (n1len * n2len));
        }

        frustumRayDir(x: number, y: number, dir: Vector3): Vector3 {
            var a = this.m10 * this.m23, b = this.m13 * this.m21, c = this.m10 * this.m21, d = this.m11 * this.m23, e = this.m13 * this.m20, f = this.m11 * this.m20;
            var g = this.m03 * this.m20, h = this.m01 * this.m23, i = this.m01 * this.m20, j = this.m03 * this.m21, k = this.m00 * this.m23, l = this.m00 * this.m21;
            var m = this.m00 * this.m13, n = this.m03 * this.m11, o = this.m00 * this.m11, p = this.m01 * this.m13, q = this.m03 * this.m10, r = this.m01 * this.m10;
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
            dir.x = this.m11 * this.m22 - this.m12 * this.m21;
            dir.y = this.m02 * this.m21 - this.m01 * this.m22;
            dir.z = this.m01 * this.m12 - this.m02 * this.m11;
            dir.normalize();
            return dir;
        }

        normalizedPositiveX(dir: Vector3): Vector3 {
            dir.x = this.m00;
            dir.y = this.m10;
            dir.z = this.m20;
            return dir;
        }

        positiveY(dir: Vector3): Vector3 {
            dir.x = this.m12 * this.m20 - this.m10 * this.m22;
            dir.y = this.m00 * this.m22 - this.m02 * this.m20;
            dir.z = this.m02 * this.m10 - this.m00 * this.m12;
            dir.normalize();
            return dir;
        }

        normalizedPositiveY(dir: Vector3): Vector3 {
            dir.x = this.m01;
            dir.y = this.m11;
            dir.z = this.m21;
            return dir;
        }

        positiveZ(dir: Vector3): Vector3 {
            dir.x = this.m10 * this.m21 - this.m11 * this.m20;
            dir.y = this.m20 * this.m01 - this.m21 * this.m00;
            dir.z = this.m00 * this.m11 - this.m01 * this.m10;
            dir.normalize();
            return dir;
        }

        normalizedPositiveZ(dir: Vector3): Vector3 {
            dir.x = this.m02;
            dir.y = this.m12;
            dir.z = this.m22;
            return dir;
        }

        origin(origin: Vector3): Vector3 {
            var a = this.m00 * this.m11 - this.m01 * this.m10;
            var b = this.m00 * this.m12 - this.m02 * this.m10;
            var d = this.m01 * this.m12 - this.m02 * this.m11;
            var g = this.m20 * this.m31 - this.m21 * this.m30;
            var h = this.m20 * this.m32 - this.m22 * this.m30;
            var j = this.m21 * this.m32 - this.m22 * this.m31;
            origin.x = -this.m10 * j + this.m11 * h - this.m12 * g;
            origin.y =  this.m00 * j - this.m01 * h + this.m02 * g;
            origin.z = -this.m30 * d + this.m31 * b - this.m32 * a;
            return origin;
        }

        shadow(lightX: number, lightY: number, lightZ: number, lightW: number, a: number, b: number, c: number, d: number, dest?: Matrix4): Matrix4 {
            dest = dest || this;
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
            var nm00 = this.m00 * rm00 + this.m10 * rm01 + this.m20 * rm02 + this.m30 * rm03;
            var nm01 = this.m01 * rm00 + this.m11 * rm01 + this.m21 * rm02 + this.m31 * rm03;
            var nm02 = this.m02 * rm00 + this.m12 * rm01 + this.m22 * rm02 + this.m32 * rm03;
            var nm03 = this.m03 * rm00 + this.m13 * rm01 + this.m23 * rm02 + this.m33 * rm03;
            var nm10 = this.m00 * rm10 + this.m10 * rm11 + this.m20 * rm12 + this.m30 * rm13;
            var nm11 = this.m01 * rm10 + this.m11 * rm11 + this.m21 * rm12 + this.m31 * rm13;
            var nm12 = this.m02 * rm10 + this.m12 * rm11 + this.m22 * rm12 + this.m32 * rm13;
            var nm13 = this.m03 * rm10 + this.m13 * rm11 + this.m23 * rm12 + this.m33 * rm13;
            var nm20 = this.m00 * rm20 + this.m10 * rm21 + this.m20 * rm22 + this.m30 * rm23;
            var nm21 = this.m01 * rm20 + this.m11 * rm21 + this.m21 * rm22 + this.m31 * rm23;
            var nm22 = this.m02 * rm20 + this.m12 * rm21 + this.m22 * rm22 + this.m32 * rm23;
            var nm23 = this.m03 * rm20 + this.m13 * rm21 + this.m23 * rm22 + this.m33 * rm23;
            dest.m30 = this.m00 * rm30 + this.m10 * rm31 + this.m20 * rm32 + this.m30 * rm33;
            dest.m31 = this.m01 * rm30 + this.m11 * rm31 + this.m21 * rm32 + this.m31 * rm33;
            dest.m32 = this.m02 * rm30 + this.m12 * rm31 + this.m22 * rm32 + this.m32 * rm33;
            dest.m33 = this.m03 * rm30 + this.m13 * rm31 + this.m23 * rm32 + this.m33 * rm33;
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
            this.m00 = leftX;
            this.m01 = leftY;
            this.m02 = leftZ;
            this.m03 = 0.0;
            this.m10 = up.x;
            this.m11 = up.y;
            this.m12 = up.z;
            this.m13 = 0.0;
            this.m20 = dirX;
            this.m21 = dirY;
            this.m22 = dirZ;
            this.m23 = 0.0;
            this.m30 = objPos.x;
            this.m31 = objPos.y;
            this.m32 = objPos.z;
            this.m33 = 1.0;
            return this;
        }

        billboardSpherical(objPos: Vector3, targetPos: Vector3, up?: Vector3): Matrix4 {
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
                this.m00 = leftX;
                this.m01 = leftY;
                this.m02 = leftZ;
                this.m03 = 0.0;
                this.m10 = upX;
                this.m11 = upY;
                this.m12 = upZ;
                this.m13 = 0.0;
                this.m20 = dirX;
                this.m21 = dirY;
                this.m22 = dirZ;
                this.m23 = 0.0;
                this.m30 = objPos.x;
                this.m31 = objPos.y;
                this.m32 = objPos.z;
                this.m33 = 1.0;
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
                this.m00 = 1.0 - q11;
                this.m01 = q01;
                this.m02 = -q13;
                this.m03 = 0.0;
                this.m10 = q01;
                this.m11 = 1.0 - q00;
                this.m12 = q03;
                this.m13 = 0.0;
                this.m20 = q13;
                this.m21 = -q03;
                this.m22 = 1.0 - q11 - q00;
                this.m23 = 0.0;
                this.m30 = objPos.x;
                this.m31 = objPos.y;
                this.m32 = objPos.z;
                this.m33 = 1.0;
            }
            return this;
        }

        pick(x: number, y: number, width: number, height: number, viewport: number[], dest?: Matrix4): Matrix4 {
            dest = dest || this;
            var sx = viewport[2] / width;
            var sy = viewport[3] / height;
            var tx = (viewport[2] + 2.0 * (viewport[0] - x)) / width;
            var ty = (viewport[3] + 2.0 * (viewport[1] - y)) / height;
            dest.m30 = this.m00 * tx + this.m10 * ty + this.m30;
            dest.m31 = this.m01 * tx + this.m11 * ty + this.m31;
            dest.m32 = this.m02 * tx + this.m12 * ty + this.m32;
            dest.m33 = this.m03 * tx + this.m13 * ty + this.m33;
            dest.m00 = this.m00 * sx;
            dest.m01 = this.m01 * sx;
            dest.m02 = this.m02 * sx;
            dest.m03 = this.m03 * sx;
            dest.m10 = this.m10 * sy;
            dest.m11 = this.m11 * sy;
            dest.m12 = this.m12 * sy;
            dest.m13 = this.m13 * sy;
            return dest;
        }

        isAffine(): boolean {
            return this.m03 == 0.0 && this.m13 == 0.0 && this.m23 == 0.0 && this.m33 == 1.0;
        }

        arcball(radius: number, center: Vector3, angleX: number, angleY: number): Matrix4 {
            return this.translate(0, 0, -radius).rotateX(angleX).rotateY(angleY).translate(-center.x, -center.y, -center.z);
        }
    }
}
