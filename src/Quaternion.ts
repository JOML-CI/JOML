module JOML {
    export class Quaternion {
        x: number;
        y: number;
        z: number;
        w: number;

        constructor();
        constructor(q: Quaternion);
        constructor(x: number, y: number, z: number, w: number);
        constructor(x?: any, y?: number, z?: number, w?: number) {
            if (x instanceof Quaternion) {
                var q: Quaternion = <Quaternion>x;
                this.x = q.x;
                this.y = q.y;
                this.z = q.z;
                this.w = q.w;
            } else if (typeof x === 'undefined') {
                this.x = 0.0;
                this.y = 0.0;
                this.z = 0.0;
                this.w = 1.0;
            } else {
                this.x = x;
                this.y = y;
                this.z = z;
                this.w = w;
            }
        }

        normalize(dest?: Quaternion): Quaternion {
            dest = dest || this;
            var invNorm = 1.0 / Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z + this.w * this.w);
            this.x *= invNorm;
            this.y *= invNorm;
            this.z *= invNorm;
            this.w *= invNorm;
            return this;
        }

        get(dest: Matrix4): Matrix4 {
            var dx = this.x + this.x;
            var dy = this.y + this.y;
            var dz = this.z + this.z;
            var q00 = dx * this.x;
            var q11 = dy * this.y;
            var q22 = dz * this.z;
            var q01 = dx * this.y;
            var q02 = dx * this.z;
            var q03 = dx * this.w;
            var q12 = dy * this.z;
            var q13 = dy * this.w;
            var q23 = dz * this.w;
            dest.m00 = 1.0 - q11 - q22;
            dest.m01 = q01 + q23;
            dest.m02 = q02 - q13;
            dest.m03 = 0.0;
            dest.m10 = q01 - q23;
            dest.m11 = 1.0 - q22 - q00;
            dest.m12 = q12 + q03;
            dest.m13 = 0.0;
            dest.m20 = q02 + q13;
            dest.m21 = q12 - q03;
            dest.m22 = 1.0 - q11 - q00;
            dest.m23 = 0.0;
            dest.m30 = 0.0;
            dest.m31 = 0.0;
            dest.m32 = 0.0;
            dest.m33 = 1.0;
            return dest;
        }

        set(x: number, y: number, z: number, w: number): Quaternion {
            this.x = x;
            this.y = y;
            this.z = z;
            this.w = w;
            return this;
        }

        setFromNormalized(m: Matrix3): Quaternion;
        setFromNormalized(m: Matrix4): Quaternion;
        setFromNormalized(m: any): Quaternion {
            var t;
            var tr = m.m00 + m.m11 + m.m22;
            if (tr >= 0.0) {
                t = Math.sqrt(tr + 1.0);
                this.w = t * 0.5;
                t = 0.5 / t;
                this.x = (m.m12 - m.m21) * t;
                this.y = (m.m20 - m.m02) * t;
                this.z = (m.m01 - m.m10) * t;
            } else {
                if (m.m00 >= m.m11 && m.m00 >= m.m22) {
                    t = Math.sqrt(m.m00 - (m.m11 + m.m22) + 1.0);
                    this.x = t * 0.5;
                    t = 0.5 / t;
                    this.y = (m.m10 + m.m01) * t;
                    this.z = (m.m02 + m.m20) * t;
                    this.w = (m.m12 - m.m21) * t;
                } else if (m.m11 > m.m22) {
                    t = Math.sqrt(m.m11 - (m.m22 + m.m00) + 1.0);
                    this.y = t * 0.5;
                    t = 0.5 / t;
                    this.z = (m.m21 + m.m12) * t;
                    this.x = (m.m10 + m.m01) * t;
                    this.w = (m.m20 - m.m02) * t;
                } else {
                    t = Math.sqrt(m.m22 - (m.m00 + m.m11) + 1.0);
                    this.z = t * 0.5;
                    t = 0.5 / t;
                    this.x = (m.m02 + m.m20) * t;
                    this.y = (m.m21 + m.m12) * t;
                    this.w = (m.m01 - m.m10) * t;
                }
            }
            return this;
        }

        rotationAxis(angle: number, axisX: number, axisY: number, axisZ: number): Quaternion {
            var hangle = angle / 2.0;
            var sinAngle = Math.sin(hangle);
            var invVLength = 1.0 / Math.sqrt(axisX * axisX + axisY * axisY + axisZ * axisZ);
            this.x = axisX * invVLength * sinAngle;
            this.y = axisY * invVLength * sinAngle;
            this.z = axisZ * invVLength * sinAngle;
            this.w = Math.cos(hangle);
            return this;
        }

        rotation(angleX: number, angleY: number, angleZ: number): Quaternion {
            var thetaX = angleX * 0.5;
            var thetaY = angleY * 0.5;
            var thetaZ = angleZ * 0.5;
            var thetaMagSq = thetaX * thetaX + thetaY * thetaY + thetaZ * thetaZ;
            var s;
            if (thetaMagSq * thetaMagSq / 24.0 < 1E-8) {
                this.w = 1.0 - thetaMagSq / 2.0;
                s = 1.0 - thetaMagSq / 6.0;
            } else {
                var thetaMag = Math.sqrt(thetaMagSq);
                this.w = Math.cos(thetaMag);
                s = Math.sin(thetaMag) / thetaMag;
            }
            this.x = thetaX * s;
            this.y = thetaY * s;
            this.z = thetaZ * s;
            return this;
        }

        rotationX(angle: number): Quaternion {
            var cos = Math.cos(angle * 0.5);
            var sin = Math.sin(angle * 0.5);
            this.w = cos;
            this.x = sin;
            this.y = 0.0;
            this.z = 0.0;
            return this;
        }

        rotationY(angle: number): Quaternion {
            var cos = Math.cos(angle * 0.5);
            var sin = Math.sin(angle * 0.5);
            this.w = cos;
            this.x = 0.0;
            this.y = sin;
            this.z = 0.0;
            return this;
        }

        rotationZ(angle: number): Quaternion {
            var cos = Math.cos(angle * 0.5);
            var sin = Math.sin(angle * 0.5);
            this.w = cos;
            this.x = 0.0;
            this.y = 0.0;
            this.z = sin;
            return this;
        }

        mul(q: Quaternion, dest?: Quaternion): Quaternion {
            dest = dest || this;
            dest.set(this.w * q.x + this.x * q.w + this.y * q.z - this.z * q.y,
                     this.w * q.y - this.x * q.z + this.y * q.w + this.z * q.x,
                     this.w * q.z + this.x * q.y - this.y * q.x + this.z * q.w,
                     this.w * q.w - this.x * q.x - this.y * q.y - this.z * q.z);
            return dest;
        }

        transform(vec: Vector3, dest?: Vector3): Vector3;
        transform(vec: Vector4, dest?: Vector4): Vector4;
        transform(vec: any, dest?: any): any {
            dest = dest || vec;
            var num = this.x + this.x;
            var num2 = this.y + this.y;
            var num3 = this.z + this.z;
            var num4 = this.x * num;
            var num5 = this.y * num2;
            var num6 = this.z * num3;
            var num7 = this.x * num2;
            var num8 = this.x * num3;
            var num9 = this.y * num3;
            var num10 = this.w * num;
            var num11 = this.w * num2;
            var num12 = this.w * num3;
            if (vec instanceof Vector3) {
                dest.set((1.0 - (num5 + num6)) * vec.x + (num7 - num12) * vec.y + (num8 + num11) * vec.z,
                         (num7 + num12) * vec.x + (1.0 - (num4 + num6)) * vec.y + (num9 - num10) * vec.z,
                         (num8 - num11) * vec.x + (num9 + num10) * vec.y + (1.0 - (num4 + num5)) * vec.z);
            } else {
                dest.set((1.0 - (num5 + num6)) * vec.x + (num7 - num12) * vec.y + (num8 + num11) * vec.z,
                         (num7 + num12) * vec.x + (1.0 - (num4 + num6)) * vec.y + (num9 - num10) * vec.z,
                         (num8 - num11) * vec.x + (num9 + num10) * vec.y + (1.0 - (num4 + num5)) * vec.z,
                         dest.w);
            }
            return dest;
        }

        invert(dest?: Quaternion): Quaternion {
            dest = dest || this;
            var invNorm = 1.0 / (this.x * this.x + this.y * this.y + this.z * this.z + this.w * this.w);
            dest.x = -this.x * invNorm;
            dest.y = -this.y * invNorm;
            dest.z = -this.z * invNorm;
            dest.w =  this.w * invNorm;
            return dest;
        }

        div(b: Quaternion, dest?: Quaternion): Quaternion {
            dest = dest || this;
            var invNorm = 1.0 / (b.x * b.x + b.y * b.y + b.z * b.z + b.w * b.w);
            var x = -b.x * invNorm;
            var y = -b.y * invNorm;
            var z = -b.z * invNorm;
            var w =  b.w * invNorm;
            dest.set(this.w * x + this.x * w + this.y * z - this.z * y,
                     this.w * y - this.x * z + this.y * w + this.z * x,
                     this.w * z + this.x * y - this.y * x + this.z * w,
                     this.w * w - this.x * x - this.y * y - this.z * z);
            return dest;
        }

        conjugate(dest?: Quaternion): Quaternion {
            dest = dest || this;
            dest.x = -this.x;
            dest.y = -this.y;
            dest.z = -this.z;
            return this;
        }

        identity(): Quaternion {
            this.x = 0.0;
            this.y = 0.0;
            this.z = 0.0;
            this.w = 1.0;
            return this;
        }

        slerp(target: Quaternion, alpha: number, dest?: Quaternion): Quaternion {
            dest = dest || this;
            var cosom = this.x * target.x + this.y * target.y + this.z * target.z + this.w * target.w;
            var absCosom = Math.abs(cosom);
            var scale0, scale1;
            if (1.0 - absCosom > 1E-6) {
                var sinSqr = 1.0 - absCosom * absCosom;
                var sinom = 1.0 / Math.sqrt(sinSqr);
                var omega = Math.atan2(sinSqr * sinom, absCosom);
                scale0 = Math.sin((1.0 - alpha) * omega) * sinom;
                scale1 = Math.sin(alpha * omega) * sinom;
            } else {
                scale0 = 1.0 - alpha;
                scale1 = alpha;
            }
            scale1 = cosom >= 0.0 ? scale1 : -scale1;
            dest.x = scale0 * this.x + scale1 * target.x;
            dest.y = scale0 * this.y + scale1 * target.y;
            dest.z = scale0 * this.z + scale1 * target.z;
            dest.w = scale0 * this.w + scale1 * target.w;
            return dest;
        }

        scale(factor: number, dest?: Quaternion): Quaternion {
            dest = dest || this;
            var absCosom = Math.abs(this.w);
            var scale0, scale1;
            if (1.0 - absCosom > 1E-6) {
                var sinSqr = 1.0 - absCosom * absCosom;
                var sinom = 1.0 / Math.sqrt(sinSqr);
                var omega = Math.atan2(sinSqr * sinom, absCosom);
                scale0 = Math.sin((1.0 - factor) * omega) * sinom;
                scale1 = Math.sin(factor * omega) * sinom;
            } else {
                scale0 = 1.0 - factor;
                scale1 = factor;
            }
            scale1 = this.w >= 0.0 ? scale1 : -scale1;
            dest.x = scale1 * this.x;
            dest.y = scale1 * this.y;
            dest.z = scale1 * this.z;
            dest.w = scale0 + scale1 * this.w;
            return this;
        }

        rotateLocal(angleX: number, angleY: number, angleZ: number, dest?: Quaternion): Quaternion {
            dest = dest || this;
            var thetaX = angleX * 0.5;
            var thetaY = angleY * 0.5;
            var thetaZ = angleZ * 0.5;
            var thetaMagSq = thetaX * thetaX + thetaY * thetaY + thetaZ * thetaZ;
            var s;
            var dqX, dqY, dqZ, dqW;
            if (thetaMagSq * thetaMagSq / 24.0 < 1E-8) {
                dqW = 1.0 - thetaMagSq * 0.5;
                s = 1.0 - thetaMagSq / 6.0;
            } else {
                var thetaMag = Math.sqrt(thetaMagSq);
                dqW = Math.cos(thetaMag);
                s = Math.sin(thetaMag) / thetaMag;
            }
            dqX = thetaX * s;
            dqY = thetaY * s;
            dqZ = thetaZ * s;
            dest.set(dqW * this.x + dqX * this.w + dqY * this.z - dqZ * this.y,
                     dqW * this.y - dqX * this.z + dqY * this.w + dqZ * this.x,
                     dqW * this.z + dqX * this.y - dqY * this.x + dqZ * this.w,
                     dqW * this.w - dqX * this.x - dqY * this.y - dqZ * this.z);
            return dest;
        }

        integrate(dt: number, vx: number, vy: number, vz: number, dest?: Quaternion): Quaternion {
            return this.rotateLocal(dt * vx, dt * vy, dt * vz, dest);
        }

        nlerp(q: Quaternion, factor: number, dest?: Quaternion): Quaternion {
            dest = dest || this;
            var cosom = this.x * q.x + this.y * q.y + this.z * q.z + this.w * q.w;
            var scale0 = 1.0 - factor;
            var scale1 = (cosom >= 0.0) ? factor : -factor;
            dest.x = scale0 * this.x + scale1 * q.x;
            dest.y = scale0 * this.y + scale1 * q.y;
            dest.z = scale0 * this.z + scale1 * q.z;
            dest.w = scale0 * this.w + scale1 * q.w;
            var s = 1.0 / Math.sqrt(dest.x * dest.x + dest.y * dest.y + dest.z * dest.z + dest.w * dest.w);
            dest.x *= s;
            dest.y *= s;
            dest.z *= s;
            dest.w *= s;
            return dest;
        }

        lookRotate(dirX: number, dirY: number, dirZ: number, upX: number, upY: number, upZ: number, dest?: Quaternion): Quaternion {
            dest = dest || this;
            var invDirLength = 1.0 / Math.sqrt(dirX * dirX + dirY * dirY + dirZ * dirZ);
            var dirnX = dirX * invDirLength;
            var dirnY = dirY * invDirLength;
            var dirnZ = dirZ * invDirLength;
            var leftX, leftY, leftZ;
            leftX = upY * dirnZ - upZ * dirnY;
            leftY = upZ * dirnX - upX * dirnZ;
            leftZ = upX * dirnY - upY * dirnX;
            var invLeftLength = 1.0 / Math.sqrt(leftX * leftX + leftY * leftY + leftZ * leftZ);
            leftX *= invLeftLength;
            leftY *= invLeftLength;
            leftZ *= invLeftLength;
            var upnX = dirnY * leftZ - dirnZ * leftY;
            var upnY = dirnZ * leftX - dirnX * leftZ;
            var upnZ = dirnX * leftY - dirnY * leftX;
            var x, y, z, w;
            var t;
            var tr = leftX + upnY + dirnZ;
            if (tr >= 0.0) {
                t = Math.sqrt(tr + 1.0);
                w = t * 0.5;
                t = 0.5 / t;
                x = (dirnY - upnZ) * t;
                y = (leftZ - dirnX) * t;
                z = (upnX - leftY) * t;
            } else {
                if (leftX > upnY && leftX > dirnZ) {
                    t = Math.sqrt(1.0 + leftX - upnY - dirnZ);
                    x = t * 0.5;
                    t = 0.5 / t;
                    y = (leftY + upnX) * t;
                    z = (dirnX + leftZ) * t;
                    w = (dirnY - upnZ) * t;
                } else if (upnY > dirnZ) {
                    t = Math.sqrt(1.0 + upnY - leftX - dirnZ);
                    y = t * 0.5;
                    t = 0.5 / t;
                    x = (leftY + upnX) * t;
                    z = (upnZ + dirnY) * t;
                    w = (leftZ - dirnX) * t;
                } else {
                    t = Math.sqrt(1.0 + dirnZ - leftX - upnY);
                    z = t * 0.5;
                    t = 0.5 / t;
                    x = (dirnX + leftZ) * t;
                    y = (upnZ + dirnY) * t;
                    w = (upnX - leftY) * t;
                }
            }
            dest.set(this.w * x + this.x * w + this.y * z - this.z * y,
                     this.w * y - this.x * z + this.y * w + this.z * x,
                     this.w * z + this.x * y - this.y * x + this.z * w,
                     this.w * w - this.x * x - this.y * y - this.z * z);
            return dest;
        }

        rotate(angleX: number, angleY: number, angleZ: number, dest?: Quaternion): Quaternion {
            dest = dest || this;
            var thetaX = angleX * 0.5;
            var thetaY = angleY * 0.5;
            var thetaZ = angleZ * 0.5;
            var thetaMagSq = thetaX * thetaX + thetaY * thetaY + thetaZ * thetaZ;
            var s;
            var dqX, dqY, dqZ, dqW;
            if (thetaMagSq * thetaMagSq / 24.0 < 1E-8) {
                dqW = 1.0 - thetaMagSq / 2.0;
                s = 1.0 - thetaMagSq / 6.0;
            } else {
                var thetaMag = Math.sqrt(thetaMagSq);
                dqW = Math.cos(thetaMag);
                s = Math.sin(thetaMag) / thetaMag;
            }
            dqX = thetaX * s;
            dqY = thetaY * s;
            dqZ = thetaZ * s;
            dest.set(this.w * dqX + this.x * dqW + this.y * dqZ - this.z * dqY,
                     this.w * dqY - this.x * dqZ + this.y * dqW + this.z * dqX,
                     this.w * dqZ + this.x * dqY - this.y * dqX + this.z * dqW,
                     this.w * dqW - this.x * dqX - this.y * dqY - this.z * dqZ);
            return dest;
        }

        rotateX(angle: number, dest?: Quaternion): Quaternion {
            dest = dest || this;
            var cos = Math.cos(angle * 0.5);
            var sin = Math.sin(angle * 0.5);
            dest.set(this.w * sin + this.x * cos,
                     this.y * cos + this.z * sin,
                     this.z * cos - this.y * sin,
                     this.w * cos - this.x * sin);
            return dest;
        }

        rotateY(angle: number, dest?: Quaternion): Quaternion {
            dest = dest || this;
            var cos = Math.cos(angle * 0.5);
            var sin = Math.sin(angle * 0.5);
            dest.set(this.x * cos - this.z * sin,
                     this.w * sin + this.y * cos,
                     this.x * sin + this.z * cos,
                     this.w * cos - this.y * sin);
            return dest;
        }

        rotateZ(angle: number, dest?: Quaternion): Quaternion {
            dest = dest || this;
            var cos = Math.cos(angle * 0.5);
            var sin = Math.sin(angle * 0.5);
            dest.set(this.x * cos + this.y * sin,
                     this.y * cos - this.x * sin,
                     this.w * sin + this.z * cos,
                     this.w * cos - this.z * sin);
            return dest;
        }

        positiveX(dir: Vector3): Vector3 {
            var invNorm = 1.0 / (this.x * this.x + this.y * this.y + this.z * this.z + this.w * this.w);
            var nx = -this.x * invNorm;
            var ny = -this.y * invNorm;
            var nz = -this.z * invNorm;
            var nw =  this.w * invNorm;
            var dy = ny + ny;
            var dz = nz + nz;
            dir.x = -ny * dy - nz * dz + 1.0;
            dir.y =  nx * dy + nw * dz;
            dir.z =  nx * dz - nw * dy;
            return dir;
        }

        normalizedPositiveX(dir: Vector3): Vector3 {
            var dy = this.y + this.y;
            var dz = this.z + this.z;
            dir.x = -this.y * dy - this.z * dz + 1.0;
            dir.y =  this.x * dy - this.w * dz;
            dir.z =  this.x * dz + this.w * dy;
            return dir;
        }

        positiveY(dir: Vector3): Vector3 {
            var invNorm = 1.0 / (this.x * this.x + this.y * this.y + this.z * this.z + this.w * this.w);
            var nx = -this.x * invNorm;
            var ny = -this.y * invNorm;
            var nz = -this.z * invNorm;
            var nw =  this.w * invNorm;
            var dx = nx + nx;
            var dy = ny + ny;
            var dz = nz + nz;
            dir.x =  nx * dy - nw * dz;
            dir.y = -nx * dx - nz * dz + 1.0;
            dir.z =  ny * dz + nw * dx;
            return dir;
        }

        normalizedPositiveY(dir: Vector3): Vector3 {
            var dx = this.x + this.x;
            var dy = this.y + this.y;
            var dz = this.z + this.z;
            dir.x =  this.x * dy + this.w * dz;
            dir.y = -this.x * dx - this.z * dz + 1.0;
            dir.z =  this.y * dz - this.w * dx;
            return dir;
        }

        positiveZ(dir: Vector3): Vector3 {
            var invNorm = 1.0 / (this.x * this.x + this.y * this.y + this.z * this.z + this.w * this.w);
            var nx = -this.x * invNorm;
            var ny = -this.y * invNorm;
            var nz = -this.z * invNorm;
            var nw =  this.w * invNorm;
            var dx = nx + nx;
            var dy = ny + ny;
            var dz = nz + nz;
            dir.x =  nx * dz + nw * dy;
            dir.y =  ny * dz - nw * dx;
            dir.z = -nx * dx - ny * dy + 1.0;
            return dir;
        }

        normalizedPositiveZ(dir: Vector3): Vector3 {
            var dx = this.x + this.x;
            var dy = this.y + this.y;
            var dz = this.z + this.z;
            dir.x =  this.x * dz - this.w * dy;
            dir.y =  this.y * dz + this.w * dx;
            dir.z = -this.x * dx - this.y * dy + 1.0;
            return dir;
        }

        toString(): string {
            return "(" + this.x + " " + this.y + " " + this.z + " " + this.w + ")";
        }
    }
}
