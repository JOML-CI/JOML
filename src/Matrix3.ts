module JOML {
    export class Matrix3 {
        m00: number; m10: number; m20: number;
        m01: number; m11: number; m21: number;
        m02: number; m12: number; m22: number;

        constructor() {
            this.m00 = 1.0;
            this.m01 = 0.0;
            this.m02 = 0.0;
            this.m10 = 0.0;
            this.m11 = 1.0;
            this.m12 = 0.0;
            this.m20 = 0.0;
            this.m21 = 0.0;
            this.m22 = 1.0;
        }

        get(b: Float32Array): Float32Array {
            b[0] = this.m00;
            b[1] = this.m01;
            b[2] = this.m02;
            b[3] = this.m10;
            b[4] = this.m11;
            b[5] = this.m12;
            b[6] = this.m20;
            b[7] = this.m21;
            b[8] = this.m22;
            return b;
        }

        set(q: Quaternion): Matrix3;
        set(b: Float32Array): Matrix3;
        set(m00: number, m01: number, m02: number,
            m10: number, m11: number, m12: number,
            m20: number, m21: number, m22: number): Matrix3;
        set(m00: any, m01?: number, m02?: number,
            m10?: number, m11?: number, m12?: number,
            m20?: number, m21?: number, m22?: number): Matrix3 {
            if (m00 instanceof Quaternion) {
                return this.setQuaternion(<Quaternion>m00);
            } else if (m00 instanceof Float32Array) {
                var b: Float32Array = m00;
                this.m01 = b[1];
                this.m02 = b[2];
                this.m10 = b[3];
                this.m11 = b[4];
                this.m12 = b[5];
                this.m20 = b[6];
                this.m21 = b[7];
                this.m22 = b[8];
            } else {
                this.m00 = <number>m00;
                this.m01 = m01;
                this.m02 = m02;
                this.m10 = m10;
                this.m11 = m11;
                this.m12 = m12;
                this.m20 = m20;
                this.m21 = m21;
                this.m22 = m22;
            }
            return this;
        }

        private setQuaternion(q: Quaternion): Matrix3 {
            var dx = q.x + q.x;
            var dy = q.y + q.y;
            var dz = q.z + q.z;
            var q00 = dx * q.x;
            var q11 = dy * q.y;
            var q22 = dz * q.z;
            var q01 = dx * q.y;
            var q02 = dx * q.z;
            var q03 = dx * q.w;
            var q12 = dy * q.z;
            var q13 = dy * q.w;
            var q23 = dz * q.w;
            this.m00 = 1.0 - q11 - q22;
            this.m01 = q01 + q23;
            this.m02 = q02 - q13;
            this.m10 = q01 - q23;
            this.m11 = 1.0 - q22 - q00;
            this.m12 = q12 + q03;
            this.m20 = q02 + q13;
            this.m21 = q12 - q03;
            this.m22 = 1.0 - q11 - q00;
            return this;
        }

        mul(right: Matrix3, dest?: Matrix3): Matrix3 {
            dest = dest || this;
            dest.set(this.m00 * right.m00 + this.m10 * right.m01 + this.m20 * right.m02,
                     this.m01 * right.m00 + this.m11 * right.m01 + this.m21 * right.m02,
                     this.m02 * right.m00 + this.m12 * right.m01 + this.m22 * right.m02,
                     this.m00 * right.m10 + this.m10 * right.m11 + this.m20 * right.m12,
                     this.m01 * right.m10 + this.m11 * right.m11 + this.m21 * right.m12,
                     this.m02 * right.m10 + this.m12 * right.m11 + this.m22 * right.m12,
                     this.m00 * right.m20 + this.m10 * right.m21 + this.m20 * right.m22,
                     this.m01 * right.m20 + this.m11 * right.m21 + this.m21 * right.m22,
                     this.m02 * right.m20 + this.m12 * right.m21 + this.m22 * right.m22);
            return dest;
        }

        determinant(): number {
            return (this.m00 * this.m11 - this.m01 * this.m10) * this.m22
                 + (this.m02 * this.m10 - this.m00 * this.m12) * this.m21
                 + (this.m01 * this.m12 - this.m02 * this.m11) * this.m20;
        }

        invert(dest?: Matrix3): Matrix3 {
            var s = this.determinant();
            s = 1.0 / s;
            dest.set((this.m11 * this.m22 - this.m21 * this.m12) * s,
                     (this.m21 * this.m02 - this.m01 * this.m22) * s,
                     (this.m01 * this.m12 - this.m11 * this.m02) * s,
                     (this.m20 * this.m12 - this.m10 * this.m22) * s,
                     (this.m00 * this.m22 - this.m20 * this.m02) * s,
                     (this.m10 * this.m02 - this.m00 * this.m12) * s,
                     (this.m10 * this.m21 - this.m20 * this.m11) * s,
                     (this.m20 * this.m01 - this.m00 * this.m21) * s,
                     (this.m00 * this.m11 - this.m10 * this.m01) * s);
            return dest;
        }

        transpose(dest?: Matrix3): Matrix3 {
            dest = dest || this;
            dest.set(this.m00, this.m10, this.m20,
                     this.m01, this.m11, this.m21,
                     this.m02, this.m12, this.m22);
            return dest;
        }

        zero(): Matrix3 {
            this.m00 = 0.0;
            this.m01 = 0.0;
            this.m02 = 0.0;
            this.m10 = 0.0;
            this.m11 = 0.0;
            this.m12 = 0.0;
            this.m20 = 0.0;
            this.m21 = 0.0;
            this.m22 = 0.0;
            return this;
        }

        identity(): Matrix3 {
            this.m00 = 1.0;
            this.m01 = 0.0;
            this.m02 = 0.0;
            this.m10 = 0.0;
            this.m11 = 1.0;
            this.m12 = 0.0;
            this.m20 = 0.0;
            this.m21 = 0.0;
            this.m22 = 1.0;
            return this;
        }

        scale(x: number, y: number, z: number, dest: Matrix3): Matrix3 {
            dest = dest || this;
            dest.m00 = this.m00 * x;
            dest.m01 = this.m01 * x;
            dest.m02 = this.m02 * x;
            dest.m10 = this.m10 * y;
            dest.m11 = this.m11 * y;
            dest.m12 = this.m12 * y;
            dest.m20 = this.m20 * z;
            dest.m21 = this.m21 * z;
            dest.m22 = this.m22 * z;
            return dest;
        }

        scaling(x: number, y: number, z: number): Matrix3 {
            this.m00 = x;
            this.m01 = 0.0;
            this.m02 = 0.0;
            this.m10 = 0.0;
            this.m11 = y;
            this.m12 = 0.0;
            this.m20 = 0.0;
            this.m21 = 0.0;
            this.m22 = z;
            return this;
        }

        rotation(quat: Quaternion): Matrix3;
        rotation(angle: number, x: number, y: number, z: number): Matrix3;
        rotation(angle: any, x?: number, y?: number, z?: number): Matrix3 {
            if (angle instanceof Quaternion) {
                return this.rotationQuaternion(<Quaternion>angle);
            }
            var cos = Math.cos(angle);
            var sin = Math.sin(angle);
            var C = 1.0 - cos;
            var xy = x * y, xz = x * z, yz = y * z;
            this.m00 = cos + x * x * C;
            this.m10 = xy * C - z * sin;
            this.m20 = xz * C + y * sin;
            this.m01 = xy * C + z * sin;
            this.m11 = cos + y * y * C;
            this.m21 = yz * C - x * sin;
            this.m02 = xz * C - y * sin;
            this.m12 = yz * C + x * sin;
            this.m22 = cos + z * z * C;
            return this;
        }

        private rotationQuaternion(quat: Quaternion): Matrix3 {
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
            this.m00 = 1.0 - q11 - q22;
            this.m01 = q01 + q23;
            this.m02 = q02 - q13;
            this.m10 = q01 - q23;
            this.m11 = 1.0 - q22 - q00;
            this.m12 = q12 + q03;
            this.m20 = q02 + q13;
            this.m21 = q12 - q03;
            this.m22 = 1.0 - q11 - q00;
            return this;
        }

        rotationX(ang: number): Matrix3 {
            var sin, cos;
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
            this.m10 = 0.0;
            this.m11 = cos;
            this.m12 = sin;
            this.m20 = 0.0;
            this.m21 = -sin;
            this.m22 = cos;
            return this;
        }

        rotationY(ang: number): Matrix3 {
            var sin, cos;
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
            this.m10 = 0.0;
            this.m11 = 1.0;
            this.m12 = 0.0;
            this.m20 = sin;
            this.m21 = 0.0;
            this.m22 = cos;
            return this;
        }

        rotationZ(ang: number): Matrix3 {
            var sin, cos;
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
            this.m10 = -sin;
            this.m11 = cos;
            this.m12 = 0.0;
            this.m20 = 0.0;
            this.m21 = 0.0;
            this.m22 = 1.0;
            return this;
        }

        transform(v: Vector3, dest?: Vector3): Vector3 {
            dest = dest || v;
            dest.set(this.m00 * v.x + this.m10 * v.y + this.m20 * v.z,
                     this.m01 * v.x + this.m11 * v.y + this.m21 * v.z,
                     this.m02 * v.x + this.m12 * v.y + this.m22 * v.z);
            return dest;
        }

        rotateX(ang: number, dest?: Matrix3): Matrix3 {
            dest = dest || this;
            var sin, cos;
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
            var rm21 = -sin;
            var rm12 = sin;
            var rm22 = cos;
            var nm10 = this.m10 * rm11 + this.m20 * rm12;
            var nm11 = this.m11 * rm11 + this.m21 * rm12;
            var nm12 = this.m12 * rm11 + this.m22 * rm12;
            dest.m20 = this.m10 * rm21 + this.m20 * rm22;
            dest.m21 = this.m11 * rm21 + this.m21 * rm22;
            dest.m22 = this.m12 * rm21 + this.m22 * rm22;
            dest.m10 = nm10;
            dest.m11 = nm11;
            dest.m12 = nm12;
            dest.m00 = this.m00;
            dest.m01 = this.m01;
            dest.m02 = this.m02;
            return dest;
        }

        rotateY(ang: number, dest?: Matrix3): Matrix3 {
            dest = dest || this;
            var sin, cos;
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
            var rm20 = sin;
            var rm02 = -sin;
            var rm22 = cos;
            var nm00 = this.m00 * rm00 + this.m20 * rm02;
            var nm01 = this.m01 * rm00 + this.m21 * rm02;
            var nm02 = this.m02 * rm00 + this.m22 * rm02;
            dest.m20 = this.m00 * rm20 + this.m20 * rm22;
            dest.m21 = this.m01 * rm20 + this.m21 * rm22;
            dest.m22 = this.m02 * rm20 + this.m22 * rm22;
            dest.m00 = nm00;
            dest.m01 = nm01;
            dest.m02 = nm02;
            dest.m10 = this.m10;
            dest.m11 = this.m11;
            dest.m12 = this.m12;
            return dest;
        }

        rotateZ(ang: number, dest?: Matrix3): Matrix3 {
            dest = dest || this;
            var sin, cos;
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
            var rm10 = -sin;
            var rm01 = sin;
            var rm11 = cos;
            var nm00 = this.m00 * rm00 + this.m10 * rm01;
            var nm01 = this.m01 * rm00 + this.m11 * rm01;
            var nm02 = this.m02 * rm00 + this.m12 * rm01;
            dest.m10 = this.m00 * rm10 + this.m10 * rm11;
            dest.m11 = this.m01 * rm10 + this.m11 * rm11;
            dest.m12 = this.m02 * rm10 + this.m12 * rm11;
            dest.m00 = nm00;
            dest.m01 = nm01;
            dest.m02 = nm02;
            dest.m20 = this.m20;
            dest.m21 = this.m21;
            dest.m22 = this.m22;
            return dest;
        }

        rotate(quat: Quaternion, dest?: Matrix3): Matrix3;
        rotate(ang: number, x: number, y: number, z: number, dest?: Matrix3): Matrix3;
        rotate(ang: any, xOrDest?: any, y?: number, z?: number, dest?: Matrix3): Matrix3 {
            if (ang instanceof Quaternion) {
                return this.rotateQuaternion(ang, dest);
            }
            var x = <number>xOrDest;
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
            var nm10 = this.m00 * rm10 + this.m10 * rm11 + this.m20 * rm12;
            var nm11 = this.m01 * rm10 + this.m11 * rm11 + this.m21 * rm12;
            var nm12 = this.m02 * rm10 + this.m12 * rm11 + this.m22 * rm12;
            dest.m20 = this.m00 * rm20 + this.m10 * rm21 + this.m20 * rm22;
            dest.m21 = this.m01 * rm20 + this.m11 * rm21 + this.m21 * rm22;
            dest.m22 = this.m02 * rm20 + this.m12 * rm21 + this.m22 * rm22;
            dest.m00 = nm00;
            dest.m01 = nm01;
            dest.m02 = nm02;
            dest.m10 = nm10;
            dest.m11 = nm11;
            dest.m12 = nm12;
            return dest;
        }

        private rotateQuaternion(quat: Quaternion, dest?: Matrix3): Matrix3 {
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
            var nm10 = this.m00 * rm10 + this.m10 * rm11 + this.m20 * rm12;
            var nm11 = this.m01 * rm10 + this.m11 * rm11 + this.m21 * rm12;
            var nm12 = this.m02 * rm10 + this.m12 * rm11 + this.m22 * rm12;
            dest.m20 = this.m00 * rm20 + this.m10 * rm21 + this.m20 * rm22;
            dest.m21 = this.m01 * rm20 + this.m11 * rm21 + this.m21 * rm22;
            dest.m22 = this.m02 * rm20 + this.m12 * rm21 + this.m22 * rm22;
            dest.m00 = nm00;
            dest.m01 = nm01;
            dest.m02 = nm02;
            dest.m10 = nm10;
            dest.m11 = nm11;
            dest.m12 = nm12;
            return dest;
        }

        normal(dest: Matrix3): Matrix3 {
            var det = this.determinant();
            var s = 1.0 / det;
            /* Invert and transpose in one go */
            dest.set((this.m11 * this.m22 - this.m21 * this.m12) * s,
                     (this.m20 * this.m12 - this.m10 * this.m22) * s,
                     (this.m10 * this.m21 - this.m20 * this.m11) * s,
                     (this.m21 * this.m02 - this.m01 * this.m22) * s,
                     (this.m00 * this.m22 - this.m20 * this.m02) * s,
                     (this.m20 * this.m01 - this.m00 * this.m21) * s,
                     (this.m01 * this.m12 - this.m11 * this.m02) * s,
                     (this.m10 * this.m02 - this.m00 * this.m12) * s,
                     (this.m00 * this.m11 - this.m10 * this.m01) * s);
            return dest;
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
    }
}
