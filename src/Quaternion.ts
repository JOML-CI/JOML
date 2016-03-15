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
            var thiz = this;
            if (x instanceof Quaternion) {
                var q: Quaternion = <Quaternion>x;
                thiz.x = q.x;
                thiz.y = q.y;
                thiz.z = q.z;
                thiz.w = q.w;
            } else if (typeof x === 'undefined') {
                thiz.x = 0.0;
                thiz.y = 0.0;
                thiz.z = 0.0;
                thiz.w = 1.0;
            } else {
                thiz.x = x;
                thiz.y = y;
                thiz.z = z;
                thiz.w = w;
            }
        }

        normalize(dest?: Quaternion): Quaternion {
            var thiz = this;
            dest = dest || thiz;
            var invNorm = 1.0 / Math.sqrt(thiz.x * thiz.x + thiz.y * thiz.y + thiz.z * thiz.z + thiz.w * thiz.w);
            thiz.x *= invNorm;
            thiz.y *= invNorm;
            thiz.z *= invNorm;
            thiz.w *= invNorm;
            return this;
        }

        get(dest: Matrix4): Matrix4 {
            var thiz = this;
            var dx = thiz.x + thiz.x;
            var dy = thiz.y + thiz.y;
            var dz = thiz.z + thiz.z;
            var q00 = dx * thiz.x;
            var q11 = dy * thiz.y;
            var q22 = dz * thiz.z;
            var q01 = dx * thiz.y;
            var q02 = dx * thiz.z;
            var q03 = dx * thiz.w;
            var q12 = dy * thiz.z;
            var q13 = dy * thiz.w;
            var q23 = dz * thiz.w;
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
            var thiz = this;
            thiz.x = x;
            thiz.y = y;
            thiz.z = z;
            thiz.w = w;
            return this;
        }

        setFromNormalized(m: Matrix3): Quaternion;
        setFromNormalized(m: Matrix4): Quaternion;
        setFromNormalized(m: any): Quaternion {
            var t;
            var tr = m.m00 + m.m11 + m.m22;
            var thiz = this;
            if (tr >= 0.0) {
                t = Math.sqrt(tr + 1.0);
                thiz.w = t * 0.5;
                t = 0.5 / t;
                thiz.x = (m.m12 - m.m21) * t;
                thiz.y = (m.m20 - m.m02) * t;
                thiz.z = (m.m01 - m.m10) * t;
            } else {
                if (m.m00 >= m.m11 && m.m00 >= m.m22) {
                    t = Math.sqrt(m.m00 - (m.m11 + m.m22) + 1.0);
                    thiz.x = t * 0.5;
                    t = 0.5 / t;
                    thiz.y = (m.m10 + m.m01) * t;
                    thiz.z = (m.m02 + m.m20) * t;
                    thiz.w = (m.m12 - m.m21) * t;
                } else if (m.m11 > m.m22) {
                    t = Math.sqrt(m.m11 - (m.m22 + m.m00) + 1.0);
                    thiz.y = t * 0.5;
                    t = 0.5 / t;
                    thiz.z = (m.m21 + m.m12) * t;
                    thiz.x = (m.m10 + m.m01) * t;
                    thiz.w = (m.m20 - m.m02) * t;
                } else {
                    t = Math.sqrt(m.m22 - (m.m00 + m.m11) + 1.0);
                    thiz.z = t * 0.5;
                    t = 0.5 / t;
                    thiz.x = (m.m02 + m.m20) * t;
                    thiz.y = (m.m21 + m.m12) * t;
                    thiz.w = (m.m01 - m.m10) * t;
                }
            }
            return this;
        }

        rotationAxis(angle: number, axisX: number, axisY: number, axisZ: number): Quaternion {
            var thiz = this;
            var hangle = angle / 2.0;
            var sinAngle = Math.sin(hangle);
            var invVLength = 1.0 / Math.sqrt(axisX * axisX + axisY * axisY + axisZ * axisZ);
            thiz.x = axisX * invVLength * sinAngle;
            thiz.y = axisY * invVLength * sinAngle;
            thiz.z = axisZ * invVLength * sinAngle;
            thiz.w = Math.cos(hangle);
            return this;
        }

        rotation(angleX: number, angleY: number, angleZ: number): Quaternion {
            var thiz = this;
            var thetaX = angleX * 0.5;
            var thetaY = angleY * 0.5;
            var thetaZ = angleZ * 0.5;
            var thetaMagSq = thetaX * thetaX + thetaY * thetaY + thetaZ * thetaZ;
            var s;
            if (thetaMagSq * thetaMagSq / 24.0 < 1E-8) {
                thiz.w = 1.0 - thetaMagSq / 2.0;
                s = 1.0 - thetaMagSq / 6.0;
            } else {
                var thetaMag = Math.sqrt(thetaMagSq);
                thiz.w = Math.cos(thetaMag);
                s = Math.sin(thetaMag) / thetaMag;
            }
            thiz.x = thetaX * s;
            thiz.y = thetaY * s;
            thiz.z = thetaZ * s;
            return this;
        }

        rotationX(angle: number): Quaternion {
            var thiz = this;
            var cos = Math.cos(angle * 0.5);
            var sin = Math.sin(angle * 0.5);
            thiz.w = cos;
            thiz.x = sin;
            thiz.y = 0.0;
            thiz.z = 0.0;
            return this;
        }

        rotationY(angle: number): Quaternion {
            var thiz = this;
            var cos = Math.cos(angle * 0.5);
            var sin = Math.sin(angle * 0.5);
            thiz.w = cos;
            thiz.x = 0.0;
            thiz.y = sin;
            thiz.z = 0.0;
            return this;
        }

        rotationZ(angle: number): Quaternion {
            var thiz = this;
            var cos = Math.cos(angle * 0.5);
            var sin = Math.sin(angle * 0.5);
            thiz.w = cos;
            thiz.x = 0.0;
            thiz.y = 0.0;
            thiz.z = sin;
            return this;
        }

        mul(q: Quaternion, dest?: Quaternion): Quaternion {
            var thiz = this;
            dest = dest || thiz;
            dest.set(thiz.w * q.x + thiz.x * q.w + thiz.y * q.z - thiz.z * q.y,
                     thiz.w * q.y - thiz.x * q.z + thiz.y * q.w + thiz.z * q.x,
                     thiz.w * q.z + thiz.x * q.y - thiz.y * q.x + thiz.z * q.w,
                     thiz.w * q.w - thiz.x * q.x - thiz.y * q.y - thiz.z * q.z);
            return dest;
        }

        transform(vec: Vector3, dest?: Vector3): Vector3;
        transform(vec: Vector4, dest?: Vector4): Vector4;
        transform(vec: any, dest?: any): any {
            var thiz = this;
            dest = dest || vec;
            var num = thiz.x + thiz.x;
            var num2 = thiz.y + thiz.y;
            var num3 = thiz.z + thiz.z;
            var num4 = thiz.x * num;
            var num5 = thiz.y * num2;
            var num6 = thiz.z * num3;
            var num7 = thiz.x * num2;
            var num8 = thiz.x * num3;
            var num9 = thiz.y * num3;
            var num10 = thiz.w * num;
            var num11 = thiz.w * num2;
            var num12 = thiz.w * num3;
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
            var thiz = this;
            dest = dest || thiz;
            var invNorm = 1.0 / (thiz.x * thiz.x + thiz.y * thiz.y + thiz.z * thiz.z + thiz.w * thiz.w);
            dest.x = -thiz.x * invNorm;
            dest.y = -thiz.y * invNorm;
            dest.z = -thiz.z * invNorm;
            dest.w =  thiz.w * invNorm;
            return dest;
        }

        div(b: Quaternion, dest?: Quaternion): Quaternion {
            var thiz = this;
            dest = dest || thiz;
            var invNorm = 1.0 / (b.x * b.x + b.y * b.y + b.z * b.z + b.w * b.w);
            var x = -b.x * invNorm;
            var y = -b.y * invNorm;
            var z = -b.z * invNorm;
            var w =  b.w * invNorm;
            dest.set(thiz.w * x + thiz.x * w + thiz.y * z - thiz.z * y,
                     thiz.w * y - thiz.x * z + thiz.y * w + thiz.z * x,
                     thiz.w * z + thiz.x * y - thiz.y * x + thiz.z * w,
                     thiz.w * w - thiz.x * x - thiz.y * y - thiz.z * z);
            return dest;
        }

        conjugate(dest?: Quaternion): Quaternion {
            var thiz = this;
            dest = dest || thiz;
            dest.x = -thiz.x;
            dest.y = -thiz.y;
            dest.z = -thiz.z;
            return this;
        }

        identity(): Quaternion {
            var thiz = this;
            thiz.x = 0.0;
            thiz.y = 0.0;
            thiz.z = 0.0;
            thiz.w = 1.0;
            return this;
        }

        slerp(target: Quaternion, alpha: number, dest?: Quaternion): Quaternion {
            var thiz = this;
            dest = dest || thiz;
            var cosom = thiz.x * target.x + thiz.y * target.y + thiz.z * target.z + thiz.w * target.w;
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
            dest.x = scale0 * thiz.x + scale1 * target.x;
            dest.y = scale0 * thiz.y + scale1 * target.y;
            dest.z = scale0 * thiz.z + scale1 * target.z;
            dest.w = scale0 * thiz.w + scale1 * target.w;
            return dest;
        }

        scale(factor: number, dest?: Quaternion): Quaternion {
            var thiz = this;
            dest = dest || thiz;
            var absCosom = Math.abs(thiz.w);
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
            scale1 = thiz.w >= 0.0 ? scale1 : -scale1;
            dest.x = scale1 * thiz.x;
            dest.y = scale1 * thiz.y;
            dest.z = scale1 * thiz.z;
            dest.w = scale0 + scale1 * thiz.w;
            return this;
        }

        rotateLocal(angleX: number, angleY: number, angleZ: number, dest?: Quaternion): Quaternion {
            var thiz = this;
            dest = dest || thiz;
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
            dest.set(dqW * thiz.x + dqX * thiz.w + dqY * thiz.z - dqZ * thiz.y,
                     dqW * thiz.y - dqX * thiz.z + dqY * thiz.w + dqZ * thiz.x,
                     dqW * thiz.z + dqX * thiz.y - dqY * thiz.x + dqZ * thiz.w,
                     dqW * thiz.w - dqX * thiz.x - dqY * thiz.y - dqZ * thiz.z);
            return dest;
        }

        integrate(dt: number, vx: number, vy: number, vz: number, dest?: Quaternion): Quaternion {
            var thiz = this;
            return thiz.rotateLocal(dt * vx, dt * vy, dt * vz, dest);
        }

        nlerp(q: Quaternion, factor: number, dest?: Quaternion): Quaternion {
            var thiz = this;
            dest = dest || thiz;
            var cosom = thiz.x * q.x + thiz.y * q.y + thiz.z * q.z + thiz.w * q.w;
            var scale0 = 1.0 - factor;
            var scale1 = (cosom >= 0.0) ? factor : -factor;
            dest.x = scale0 * thiz.x + scale1 * q.x;
            dest.y = scale0 * thiz.y + scale1 * q.y;
            dest.z = scale0 * thiz.z + scale1 * q.z;
            dest.w = scale0 * thiz.w + scale1 * q.w;
            var s = 1.0 / Math.sqrt(dest.x * dest.x + dest.y * dest.y + dest.z * dest.z + dest.w * dest.w);
            dest.x *= s;
            dest.y *= s;
            dest.z *= s;
            dest.w *= s;
            return dest;
        }

        lookRotate(dirX: number, dirY: number, dirZ: number, upX: number, upY: number, upZ: number, dest?: Quaternion): Quaternion {
            var thiz = this;
            dest = dest || thiz;
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
            dest.set(thiz.w * x + thiz.x * w + thiz.y * z - thiz.z * y,
                     thiz.w * y - thiz.x * z + thiz.y * w + thiz.z * x,
                     thiz.w * z + thiz.x * y - thiz.y * x + thiz.z * w,
                     thiz.w * w - thiz.x * x - thiz.y * y - thiz.z * z);
            return dest;
        }

        rotate(angleX: number, angleY: number, angleZ: number, dest?: Quaternion): Quaternion {
            var thiz = this;
            dest = dest || thiz;
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
            dest.set(thiz.w * dqX + thiz.x * dqW + thiz.y * dqZ - thiz.z * dqY,
                     thiz.w * dqY - thiz.x * dqZ + thiz.y * dqW + thiz.z * dqX,
                     thiz.w * dqZ + thiz.x * dqY - thiz.y * dqX + thiz.z * dqW,
                     thiz.w * dqW - thiz.x * dqX - thiz.y * dqY - thiz.z * dqZ);
            return dest;
        }

        rotateX(angle: number, dest?: Quaternion): Quaternion {
            var thiz = this;
            dest = dest || thiz;
            var cos = Math.cos(angle * 0.5);
            var sin = Math.sin(angle * 0.5);
            dest.set(thiz.w * sin + thiz.x * cos,
                     thiz.y * cos + thiz.z * sin,
                     thiz.z * cos - thiz.y * sin,
                     thiz.w * cos - thiz.x * sin);
            return dest;
        }

        rotateY(angle: number, dest?: Quaternion): Quaternion {
            var thiz = this;
            dest = dest || thiz;
            var cos = Math.cos(angle * 0.5);
            var sin = Math.sin(angle * 0.5);
            dest.set(thiz.x * cos - thiz.z * sin,
                     thiz.w * sin + thiz.y * cos,
                     thiz.x * sin + thiz.z * cos,
                     thiz.w * cos - thiz.y * sin);
            return dest;
        }

        rotateZ(angle: number, dest?: Quaternion): Quaternion {
            var thiz = this;
            dest = dest || thiz;
            var cos = Math.cos(angle * 0.5);
            var sin = Math.sin(angle * 0.5);
            dest.set(thiz.x * cos + thiz.y * sin,
                     thiz.y * cos - thiz.x * sin,
                     thiz.w * sin + thiz.z * cos,
                     thiz.w * cos - thiz.z * sin);
            return dest;
        }

        positiveX(dir: Vector3): Vector3 {
            var thiz = this;
            var invNorm = 1.0 / (thiz.x * thiz.x + thiz.y * thiz.y + thiz.z * thiz.z + thiz.w * thiz.w);
            var nx = -thiz.x * invNorm;
            var ny = -thiz.y * invNorm;
            var nz = -thiz.z * invNorm;
            var nw =  thiz.w * invNorm;
            var dy = ny + ny;
            var dz = nz + nz;
            dir.x = -ny * dy - nz * dz + 1.0;
            dir.y =  nx * dy + nw * dz;
            dir.z =  nx * dz - nw * dy;
            return dir;
        }

        normalizedPositiveX(dir: Vector3): Vector3 {
            var thiz = this;
            var dy = thiz.y + thiz.y;
            var dz = thiz.z + thiz.z;
            dir.x = -thiz.y * dy - thiz.z * dz + 1.0;
            dir.y =  thiz.x * dy - thiz.w * dz;
            dir.z =  thiz.x * dz + thiz.w * dy;
            return dir;
        }

        positiveY(dir: Vector3): Vector3 {
            var thiz = this;
            var invNorm = 1.0 / (thiz.x * thiz.x + thiz.y * thiz.y + thiz.z * thiz.z + thiz.w * thiz.w);
            var nx = -thiz.x * invNorm;
            var ny = -thiz.y * invNorm;
            var nz = -thiz.z * invNorm;
            var nw =  thiz.w * invNorm;
            var dx = nx + nx;
            var dy = ny + ny;
            var dz = nz + nz;
            dir.x =  nx * dy - nw * dz;
            dir.y = -nx * dx - nz * dz + 1.0;
            dir.z =  ny * dz + nw * dx;
            return dir;
        }

        normalizedPositiveY(dir: Vector3): Vector3 {
            var thiz = this;
            var dx = thiz.x + thiz.x;
            var dy = thiz.y + thiz.y;
            var dz = thiz.z + thiz.z;
            dir.x =  thiz.x * dy + thiz.w * dz;
            dir.y = -thiz.x * dx - thiz.z * dz + 1.0;
            dir.z =  thiz.y * dz - thiz.w * dx;
            return dir;
        }

        positiveZ(dir: Vector3): Vector3 {
            var thiz = this;
            var invNorm = 1.0 / (thiz.x * thiz.x + thiz.y * thiz.y + thiz.z * thiz.z + thiz.w * thiz.w);
            var nx = -thiz.x * invNorm;
            var ny = -thiz.y * invNorm;
            var nz = -thiz.z * invNorm;
            var nw =  thiz.w * invNorm;
            var dx = nx + nx;
            var dy = ny + ny;
            var dz = nz + nz;
            dir.x =  nx * dz + nw * dy;
            dir.y =  ny * dz - nw * dx;
            dir.z = -nx * dx - ny * dy + 1.0;
            return dir;
        }

        normalizedPositiveZ(dir: Vector3): Vector3 {
            var thiz = this;
            var dx = thiz.x + thiz.x;
            var dy = thiz.y + thiz.y;
            var dz = thiz.z + thiz.z;
            dir.x =  thiz.x * dz - thiz.w * dy;
            dir.y =  thiz.y * dz + thiz.w * dx;
            dir.z = -thiz.x * dx - thiz.y * dy + 1.0;
            return dir;
        }

        toString(): string {
            var thiz = this;
            return "(" + thiz.x + " " + thiz.y + " " + thiz.z + " " + thiz.w + ")";
        }
    }
}
