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

        set(m00: number, m01: number, m02: number, m03: number,
            m10: number, m11: number, m12: number, m13: number,
            m20: number, m21: number, m22: number, m23: number,
            m30: number, m31: number, m32: number, m33: number): Matrix4 {
            this.m00 = m00;
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
            return this;
        }

        transpose(dest?: Matrix4): Matrix4 {
            dest = dest || this;
            dest.set(this.m00, this.m10, this.m20, this.m30,
                     this.m01, this.m11, this.m21, this.m31,
                     this.m02, this.m12, this.m22, this.m32,
                     this.m03, this.m13, this.m23, this.m33);
            return dest;
        }

        translate(offset: Vector3, dest?: Matrix4): Matrix4;
        translate(x: number, y: number, z: number, dest?: Matrix4): Matrix4;
        translate(offsetX: (Vector3 | number), yDest?: (Matrix4 | number), zDest?: (Matrix4 | number), dest?: Matrix4): Matrix4 {
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
    }
}
