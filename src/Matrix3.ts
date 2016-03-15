module JOML {
    export class Matrix3 {
        m00: number; m10: number; m20: number;
        m01: number; m11: number; m21: number;
        m02: number; m12: number; m22: number;

        constructor() {
            var thiz = this;
            thiz.m00 = 1.0;
            thiz.m01 = 0.0;
            thiz.m02 = 0.0;
            thiz.m10 = 0.0;
            thiz.m11 = 1.0;
            thiz.m12 = 0.0;
            thiz.m20 = 0.0;
            thiz.m21 = 0.0;
            thiz.m22 = 1.0;
        }

        get(b: Float32Array): Float32Array {
            var thiz = this;
            b[0] = thiz.m00;
            b[1] = thiz.m01;
            b[2] = thiz.m02;
            b[3] = thiz.m10;
            b[4] = thiz.m11;
            b[5] = thiz.m12;
            b[6] = thiz.m20;
            b[7] = thiz.m21;
            b[8] = thiz.m22;
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
            var thiz = this;
            if (m00 instanceof Quaternion) {
                return thiz.setQuaternion(<Quaternion>m00);
            } else if (m00 instanceof Float32Array) {
                var b: Float32Array = m00;
                thiz.m01 = b[1];
                thiz.m02 = b[2];
                thiz.m10 = b[3];
                thiz.m11 = b[4];
                thiz.m12 = b[5];
                thiz.m20 = b[6];
                thiz.m21 = b[7];
                thiz.m22 = b[8];
            } else {
                thiz.m00 = <number>m00;
                thiz.m01 = m01;
                thiz.m02 = m02;
                thiz.m10 = m10;
                thiz.m11 = m11;
                thiz.m12 = m12;
                thiz.m20 = m20;
                thiz.m21 = m21;
                thiz.m22 = m22;
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
            var thiz = this;
            thiz.m00 = 1.0 - q11 - q22;
            thiz.m01 = q01 + q23;
            thiz.m02 = q02 - q13;
            thiz.m10 = q01 - q23;
            thiz.m11 = 1.0 - q22 - q00;
            thiz.m12 = q12 + q03;
            thiz.m20 = q02 + q13;
            thiz.m21 = q12 - q03;
            thiz.m22 = 1.0 - q11 - q00;
            return this;
        }

        mul(right: Matrix3, dest?: Matrix3): Matrix3 {
            var thiz = this;
            dest = dest || thiz;
            dest.set(thiz.m00 * right.m00 + thiz.m10 * right.m01 + thiz.m20 * right.m02,
                     thiz.m01 * right.m00 + thiz.m11 * right.m01 + thiz.m21 * right.m02,
                     thiz.m02 * right.m00 + thiz.m12 * right.m01 + thiz.m22 * right.m02,
                     thiz.m00 * right.m10 + thiz.m10 * right.m11 + thiz.m20 * right.m12,
                     thiz.m01 * right.m10 + thiz.m11 * right.m11 + thiz.m21 * right.m12,
                     thiz.m02 * right.m10 + thiz.m12 * right.m11 + thiz.m22 * right.m12,
                     thiz.m00 * right.m20 + thiz.m10 * right.m21 + thiz.m20 * right.m22,
                     thiz.m01 * right.m20 + thiz.m11 * right.m21 + thiz.m21 * right.m22,
                     thiz.m02 * right.m20 + thiz.m12 * right.m21 + thiz.m22 * right.m22);
            return dest;
        }

        determinant(): number {
            var thiz = this;
            return (thiz.m00 * thiz.m11 - thiz.m01 * thiz.m10) * thiz.m22
                 + (thiz.m02 * thiz.m10 - thiz.m00 * thiz.m12) * thiz.m21
                 + (thiz.m01 * thiz.m12 - thiz.m02 * thiz.m11) * thiz.m20;
        }

        invert(dest?: Matrix3): Matrix3 {
            var thiz = this;
            var s = thiz.determinant();
            s = 1.0 / s;
            dest.set((thiz.m11 * thiz.m22 - thiz.m21 * thiz.m12) * s,
                     (thiz.m21 * thiz.m02 - thiz.m01 * thiz.m22) * s,
                     (thiz.m01 * thiz.m12 - thiz.m11 * thiz.m02) * s,
                     (thiz.m20 * thiz.m12 - thiz.m10 * thiz.m22) * s,
                     (thiz.m00 * thiz.m22 - thiz.m20 * thiz.m02) * s,
                     (thiz.m10 * thiz.m02 - thiz.m00 * thiz.m12) * s,
                     (thiz.m10 * thiz.m21 - thiz.m20 * thiz.m11) * s,
                     (thiz.m20 * thiz.m01 - thiz.m00 * thiz.m21) * s,
                     (thiz.m00 * thiz.m11 - thiz.m10 * thiz.m01) * s);
            return dest;
        }

        transpose(dest?: Matrix3): Matrix3 {
            var thiz = this;
            dest = dest || thiz;
            dest.set(thiz.m00, thiz.m10, thiz.m20,
                     thiz.m01, thiz.m11, thiz.m21,
                     thiz.m02, thiz.m12, thiz.m22);
            return dest;
        }

        zero(): Matrix3 {
            var thiz = this;
            thiz.m00 = 0.0;
            thiz.m01 = 0.0;
            thiz.m02 = 0.0;
            thiz.m10 = 0.0;
            thiz.m11 = 0.0;
            thiz.m12 = 0.0;
            thiz.m20 = 0.0;
            thiz.m21 = 0.0;
            thiz.m22 = 0.0;
            return this;
        }

        identity(): Matrix3 {
            var thiz = this;
            thiz.m00 = 1.0;
            thiz.m01 = 0.0;
            thiz.m02 = 0.0;
            thiz.m10 = 0.0;
            thiz.m11 = 1.0;
            thiz.m12 = 0.0;
            thiz.m20 = 0.0;
            thiz.m21 = 0.0;
            thiz.m22 = 1.0;
            return this;
        }

        scale(x: number, y: number, z: number, dest: Matrix3): Matrix3 {
            var thiz = this;
            dest = dest || thiz;
            dest.m00 = thiz.m00 * x;
            dest.m01 = thiz.m01 * x;
            dest.m02 = thiz.m02 * x;
            dest.m10 = thiz.m10 * y;
            dest.m11 = thiz.m11 * y;
            dest.m12 = thiz.m12 * y;
            dest.m20 = thiz.m20 * z;
            dest.m21 = thiz.m21 * z;
            dest.m22 = thiz.m22 * z;
            return dest;
        }

        scaling(x: number, y: number, z: number): Matrix3 {
            var thiz = this;
            thiz.m00 = x;
            thiz.m01 = 0.0;
            thiz.m02 = 0.0;
            thiz.m10 = 0.0;
            thiz.m11 = y;
            thiz.m12 = 0.0;
            thiz.m20 = 0.0;
            thiz.m21 = 0.0;
            thiz.m22 = z;
            return this;
        }

        rotation(quat: Quaternion): Matrix3;
        rotation(angle: number, x: number, y: number, z: number): Matrix3;
        rotation(angle: any, x?: number, y?: number, z?: number): Matrix3 {
            var thiz = this;
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
            thiz.m01 = xy * C + z * sin;
            thiz.m11 = cos + y * y * C;
            thiz.m21 = yz * C - x * sin;
            thiz.m02 = xz * C - y * sin;
            thiz.m12 = yz * C + x * sin;
            thiz.m22 = cos + z * z * C;
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
            var thiz = this;
            thiz.m00 = 1.0 - q11 - q22;
            thiz.m01 = q01 + q23;
            thiz.m02 = q02 - q13;
            thiz.m10 = q01 - q23;
            thiz.m11 = 1.0 - q22 - q00;
            thiz.m12 = q12 + q03;
            thiz.m20 = q02 + q13;
            thiz.m21 = q12 - q03;
            thiz.m22 = 1.0 - q11 - q00;
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
            var thiz = this;
            thiz.m00 = 1.0;
            thiz.m01 = 0.0;
            thiz.m02 = 0.0;
            thiz.m10 = 0.0;
            thiz.m11 = cos;
            thiz.m12 = sin;
            thiz.m20 = 0.0;
            thiz.m21 = -sin;
            thiz.m22 = cos;
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
            var thiz = this;
            thiz.m00 = cos;
            thiz.m01 = 0.0;
            thiz.m02 = -sin;
            thiz.m10 = 0.0;
            thiz.m11 = 1.0;
            thiz.m12 = 0.0;
            thiz.m20 = sin;
            thiz.m21 = 0.0;
            thiz.m22 = cos;
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
            var thiz = this;
            thiz.m00 = cos;
            thiz.m01 = sin;
            thiz.m02 = 0.0;
            thiz.m10 = -sin;
            thiz.m11 = cos;
            thiz.m12 = 0.0;
            thiz.m20 = 0.0;
            thiz.m21 = 0.0;
            thiz.m22 = 1.0;
            return this;
        }

        transform(v: Vector3, dest?: Vector3): Vector3 {
            dest = dest || v;
            var thiz = this;
            dest.set(thiz.m00 * v.x + thiz.m10 * v.y + thiz.m20 * v.z,
                     thiz.m01 * v.x + thiz.m11 * v.y + thiz.m21 * v.z,
                     thiz.m02 * v.x + thiz.m12 * v.y + thiz.m22 * v.z);
            return dest;
        }

        rotateX(ang: number, dest?: Matrix3): Matrix3 {
            var thiz = this;
            dest = dest || thiz;
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
            var nm10 = thiz.m10 * rm11 + thiz.m20 * rm12;
            var nm11 = thiz.m11 * rm11 + thiz.m21 * rm12;
            var nm12 = thiz.m12 * rm11 + thiz.m22 * rm12;
            dest.m20 = thiz.m10 * rm21 + thiz.m20 * rm22;
            dest.m21 = thiz.m11 * rm21 + thiz.m21 * rm22;
            dest.m22 = thiz.m12 * rm21 + thiz.m22 * rm22;
            dest.m10 = nm10;
            dest.m11 = nm11;
            dest.m12 = nm12;
            dest.m00 = thiz.m00;
            dest.m01 = thiz.m01;
            dest.m02 = thiz.m02;
            return dest;
        }

        rotateY(ang: number, dest?: Matrix3): Matrix3 {
            var thiz = this;
            dest = dest || thiz;
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
            var nm00 = thiz.m00 * rm00 + thiz.m20 * rm02;
            var nm01 = thiz.m01 * rm00 + thiz.m21 * rm02;
            var nm02 = thiz.m02 * rm00 + thiz.m22 * rm02;
            dest.m20 = thiz.m00 * rm20 + thiz.m20 * rm22;
            dest.m21 = thiz.m01 * rm20 + thiz.m21 * rm22;
            dest.m22 = thiz.m02 * rm20 + thiz.m22 * rm22;
            dest.m00 = nm00;
            dest.m01 = nm01;
            dest.m02 = nm02;
            dest.m10 = thiz.m10;
            dest.m11 = thiz.m11;
            dest.m12 = thiz.m12;
            return dest;
        }

        rotateZ(ang: number, dest?: Matrix3): Matrix3 {
            var thiz = this;
            dest = dest || thiz;
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
            var nm00 = thiz.m00 * rm00 + thiz.m10 * rm01;
            var nm01 = thiz.m01 * rm00 + thiz.m11 * rm01;
            var nm02 = thiz.m02 * rm00 + thiz.m12 * rm01;
            dest.m10 = thiz.m00 * rm10 + thiz.m10 * rm11;
            dest.m11 = thiz.m01 * rm10 + thiz.m11 * rm11;
            dest.m12 = thiz.m02 * rm10 + thiz.m12 * rm11;
            dest.m00 = nm00;
            dest.m01 = nm01;
            dest.m02 = nm02;
            dest.m20 = thiz.m20;
            dest.m21 = thiz.m21;
            dest.m22 = thiz.m22;
            return dest;
        }

        rotate(quat: Quaternion, dest?: Matrix3): Matrix3;
        rotate(ang: number, x: number, y: number, z: number, dest?: Matrix3): Matrix3;
        rotate(ang: any, xOrDest?: any, y?: number, z?: number, dest?: Matrix3): Matrix3 {
            var thiz = this;
            if (ang instanceof Quaternion) {
                return thiz.rotateQuaternion(ang, dest);
            }
            var x = <number>xOrDest;
            dest = dest || thiz;
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
            var nm10 = thiz.m00 * rm10 + thiz.m10 * rm11 + thiz.m20 * rm12;
            var nm11 = thiz.m01 * rm10 + thiz.m11 * rm11 + thiz.m21 * rm12;
            var nm12 = thiz.m02 * rm10 + thiz.m12 * rm11 + thiz.m22 * rm12;
            dest.m20 = thiz.m00 * rm20 + thiz.m10 * rm21 + thiz.m20 * rm22;
            dest.m21 = thiz.m01 * rm20 + thiz.m11 * rm21 + thiz.m21 * rm22;
            dest.m22 = thiz.m02 * rm20 + thiz.m12 * rm21 + thiz.m22 * rm22;
            dest.m00 = nm00;
            dest.m01 = nm01;
            dest.m02 = nm02;
            dest.m10 = nm10;
            dest.m11 = nm11;
            dest.m12 = nm12;
            return dest;
        }

        private rotateQuaternion(quat: Quaternion, dest?: Matrix3): Matrix3 {
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
            var nm10 = thiz.m00 * rm10 + thiz.m10 * rm11 + thiz.m20 * rm12;
            var nm11 = thiz.m01 * rm10 + thiz.m11 * rm11 + thiz.m21 * rm12;
            var nm12 = thiz.m02 * rm10 + thiz.m12 * rm11 + thiz.m22 * rm12;
            dest.m20 = thiz.m00 * rm20 + thiz.m10 * rm21 + thiz.m20 * rm22;
            dest.m21 = thiz.m01 * rm20 + thiz.m11 * rm21 + thiz.m21 * rm22;
            dest.m22 = thiz.m02 * rm20 + thiz.m12 * rm21 + thiz.m22 * rm22;
            dest.m00 = nm00;
            dest.m01 = nm01;
            dest.m02 = nm02;
            dest.m10 = nm10;
            dest.m11 = nm11;
            dest.m12 = nm12;
            return dest;
        }

        normal(dest: Matrix3): Matrix3 {
            var thiz = this;
            var det = thiz.determinant();
            var s = 1.0 / det;
            /* Invert and transpose in one go */
            dest.set((thiz.m11 * thiz.m22 - thiz.m21 * thiz.m12) * s,
                     (thiz.m20 * thiz.m12 - thiz.m10 * thiz.m22) * s,
                     (thiz.m10 * thiz.m21 - thiz.m20 * thiz.m11) * s,
                     (thiz.m21 * thiz.m02 - thiz.m01 * thiz.m22) * s,
                     (thiz.m00 * thiz.m22 - thiz.m20 * thiz.m02) * s,
                     (thiz.m20 * thiz.m01 - thiz.m00 * thiz.m21) * s,
                     (thiz.m01 * thiz.m12 - thiz.m11 * thiz.m02) * s,
                     (thiz.m10 * thiz.m02 - thiz.m00 * thiz.m12) * s,
                     (thiz.m00 * thiz.m11 - thiz.m10 * thiz.m01) * s);
            return dest;
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
    }
}
