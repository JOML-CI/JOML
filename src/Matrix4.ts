module JOML {
    export class Matrix4 {
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

        determinant():number {
            return (this.m00 * this.m11 - this.m01 * this.m10) * (this.m22 * this.m33 - this.m23 * this.m32)
                 + (this.m02 * this.m10 - this.m00 * this.m12) * (this.m21 * this.m33 - this.m23 * this.m31)
                 + (this.m00 * this.m13 - this.m03 * this.m10) * (this.m21 * this.m32 - this.m22 * this.m31)
                 + (this.m01 * this.m12 - this.m02 * this.m11) * (this.m20 * this.m33 - this.m23 * this.m30)
                 + (this.m03 * this.m11 - this.m01 * this.m13) * (this.m20 * this.m32 - this.m22 * this.m30)
                 + (this.m02 * this.m13 - this.m03 * this.m12) * (this.m20 * this.m31 - this.m21 * this.m30);
        }

        determinant3x3():number {
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
            dest.set((this.m11 * l - this.m12 * k + this.m13 * j) * det,
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
                (m11m02 * this.m30 - m12m01 * this.m30 + m12m00 * this.m31 - m10m02 * this.m31 + m10m01 * this.m32 - m11m00 * this. m32) * s,
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

        rotation(angle:number, x:number, y:number, z:number): Matrix4 {
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

        rotationY(ang:number): Matrix4 {
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
                var d: Vector4 = <Vector4> dest;
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

        scale(x: number, y: number, z: number, dest: Matrix4): Matrix4 {
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

        rotate(ang: number, x: number, y: number, z: number, dest?: Matrix4): Matrix4 {
            dest = dest || this;
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
                rm31 = <number>zDest;
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

        ortho(left: number, right: number, bottom: number, top: number, zNear: number, zFar: number, zZeroToOne: boolean, dest?: Matrix4): Matrix4 {
            dest = dest || this;
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

        perspective(fovy: number, aspect: number, zNear: number, zFar: number, zZeroToOne: boolean, dest?: Matrix4): Matrix4 {
            dest = dest || this;
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

        frustum(left: number, right: number, bottom: number, top: number, zNear: number, zFar: number, zZeroToOne: boolean, dest?: Matrix4): Matrix4 {
            dest = dest || this;
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
    }
}
