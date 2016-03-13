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

        set(b: Float32Array): Matrix3;
        set(m00: number, m01: number, m02: number,
            m10: number, m11: number, m12: number,
            m20: number, m21: number, m22: number): Matrix3;
        set(m00: (Float32Array | number), m01?: number, m02?: number,
            m10?: number, m11?: number, m12?: number,
            m20?: number, m21?: number, m22?: number): Matrix3 {
            if (m00 instanceof Float32Array) {
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

        translate(offset: Vector2, dest?: Matrix3): Matrix3;
        translate(x: number, y: number, dest?: Matrix3): Matrix3;
        translate(offsetX: (Vector2 | number), yDest?: (Matrix3 | number), dest?: Matrix3): Matrix3 {
            var rm20: number;
            var rm21: number;
            if (offsetX instanceof Vector2) {
                rm20 = offsetX.x;
                rm21 = offsetX.y;
                dest = <Matrix3>yDest || this;
            } else {
                rm20 = <number>offsetX;
                rm21 = <number>yDest;
                dest = dest || this;
            }
            dest.m20 = this.m00 * rm20 + this.m10 * rm21 + this.m20;
            dest.m21 = this.m01 * rm20 + this.m11 * rm21 + this.m21;
            dest.m22 = this.m02 * rm20 + this.m12 * rm21 + this.m22;
            dest.m00 = this.m00;
            dest.m01 = this.m01;
            dest.m02 = this.m02;
            dest.m10 = this.m10;
            dest.m11 = this.m11;
            dest.m12 = this.m12;
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

        scale(x: number, y: number, dest: Matrix3): Matrix3 {
            dest = dest || this;
            dest.m00 = this.m00 * x;
            dest.m01 = this.m01 * x;
            dest.m02 = this.m02 * x;
            dest.m10 = this.m10 * y;
            dest.m11 = this.m11 * y;
            dest.m12 = this.m12 * y;
            dest.m20 = this.m20;
            dest.m21 = this.m21;
            dest.m22 = this.m22;
            return dest;
        }

        scaling(x: number, y: number): Matrix3 {
            this.m00 = x;
            this.m01 = 0.0;
            this.m02 = 0.0;
            this.m10 = 0.0;
            this.m11 = y;
            this.m12 = 0.0;
            this.m20 = 0.0;
            this.m21 = 0.0;
            this.m22 = 1.0;
            return this;
        }

        rotation(angle: number): Matrix3 {
            var cos = Math.cos(angle);
            var sin = Math.sin(angle);
            this.m00 = cos;
            this.m10 = -sin;
            this.m20 = 0.0;
            this.m01 = sin;
            this.m11 = cos;
            this.m21 = 0.0;
            this.m02 = 0.0;
            this.m12 = 0.0;
            this.m22 = 1.0;
            return this;
        }

        transformPosition(v: Vector2, dest?: Vector2): Vector2 {
            dest = dest || v;
            dest.set(this.m00 * v.x + this.m10 * v.y + this.m20,
                this.m01 * v.x + this.m11 * v.y + this.m21);
            return dest;
        }

        transformDirection(v: Vector2, dest?: Vector2): Vector2 {
            dest = dest || v;
            dest.set(this.m00 * v.x + this.m10 * v.y,
                this.m01 * v.x + this.m11 * v.y);
            return dest;
        }

        rotate(ang: number, dest?: Matrix3): Matrix3 {
            dest = dest || this;
            var cos = Math.cos(ang);
            var sin = Math.sin(ang);
            var rm00 = cos;
            var rm01 = sin;
            var rm10 = -sin;
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

        rotateAbout(ang: number, x: number, y: number, dest?: Matrix3): Matrix3 {
            dest = dest || this;
            var tm20 = this.m00 * x + this.m10 * y + this.m20;
            var tm21 = this.m01 * x + this.m11 * y + this.m21;
            var tm22 = this.m02 * x + this.m12 * y + this.m22;
            var cos = Math.cos(ang);
            var sin = Math.sin(ang);
            var nm00 = this.m00 * cos + this.m10 * sin;
            var nm01 = this.m01 * cos + this.m11 * sin;
            var nm02 = this.m02 * cos + this.m12 * sin;
            dest.m10 = this.m00 * -sin + this.m10 * cos;
            dest.m11 = this.m01 * -sin + this.m11 * cos;
            dest.m12 = this.m02 * -sin + this.m12 * cos;
            dest.m00 = nm00;
            dest.m01 = nm01;
            dest.m02 = nm02;
            dest.m20 = dest.m00 * -x + dest.m10 * -y + tm20;
            dest.m21 = dest.m01 * -x + dest.m11 * -y + tm21;
            dest.m22 = dest.m02 * -x + dest.m12 * -y + tm22;
            return dest;
        }

        rotateTo(fromDir: Vector2, toDir: Vector2, dest?: Matrix3): Matrix3 {
            var dot = fromDir.x * toDir.x + fromDir.y * toDir.y;
            var det = fromDir.x * toDir.y - fromDir.y * toDir.x;
            var rm00 = dot;
            var rm01 = det;
            var rm10 = -det;
            var rm11 = dot;
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

        view(left: number, right: number, bottom: number, top: number, dest?: Matrix3): Matrix3 {
            dest = dest || this;
            var rm00 = 2.0 / (right - left);
            var rm11 = 2.0 / (top - bottom);
            var rm20 = (left + right) / (left - right);
            var rm21 = (bottom + top) / (bottom - top);
            var rm22 = -1.0;
            dest.m20 = this.m00 * rm20 + this.m10 * rm21 + this.m20 * rm22 + this.m20;
            dest.m21 = this.m01 * rm20 + this.m11 * rm21 + this.m21 * rm22 + this.m21;
            dest.m22 = 1.0;
            dest.m00 = this.m00 * rm00;
            dest.m01 = this.m01 * rm00;
            dest.m02 = this.m02 * rm00;
            dest.m10 = this.m10 * rm11;
            dest.m11 = this.m11 * rm11;
            dest.m12 = this.m12 * rm11;

            return dest;
        }

        setView(left: number, right: number, bottom: number, top: number): Matrix3 {
            this.m00 = 2.0 / (right - left);
            this.m01 = 0.0;
            this.m02 = 0.0;
            this.m10 = 0.0;
            this.m11 = 2.0 / (top - bottom);
            this.m12 = 0.0;
            this.m20 = (left + right) / (left - right);
            this.m21 = (bottom + top) / (bottom - top);
            this.m22 = -1.0;
            return this;
        }

        origin(origin: Vector2): Vector2 {
            return origin.set(this.m10 * this.m21 - this.m11 * this.m20, this.m01 * this.m20 - this.m00 * this.m21);
        }

        viewArea(area: number[]): number[] {
            var s = 1.0 / (this.m00 * this.m11 - this.m01 * this.m10);
            var rm00 = this.m11 * s;
            var rm01 = -this.m01 * s;
            var rm10 = -this.m10 * s;
            var rm11 = this.m00 * s;
            var rm20 = (this.m10 * this.m21 - this.m20 * this.m11) * s;
            var rm21 = (this.m20 * this.m01 - this.m00 * this.m21) * s;
            var nxnyX = -rm00 - rm10;
            var nxnyY = -rm01 - rm11;
            var pxnyX = rm00 - rm10;
            var pxnyY = rm01 - rm11;
            var nxpyX = -rm00 + rm10;
            var nxpyY = -rm01 + rm11;
            var pxpyX = rm00 + rm10;
            var pxpyY = rm01 + rm11;
            var minX = nxnyX;
            minX = minX < nxpyX ? minX : nxpyX;
            minX = minX < pxnyX ? minX : pxnyX;
            minX = minX < pxpyX ? minX : pxpyX;
            var minY = nxnyY;
            minY = minY < nxpyY ? minY : nxpyY;
            minY = minY < pxnyY ? minY : pxnyY;
            minY = minY < pxpyY ? minY : pxpyY;
            var maxX = nxnyX;
            maxX = maxX > nxpyX ? maxX : nxpyX;
            maxX = maxX > pxnyX ? maxX : pxnyX;
            maxX = maxX > pxpyX ? maxX : pxpyX;
            var maxY = nxnyY;
            maxY = maxY > nxpyY ? maxY : nxpyY;
            maxY = maxY > pxnyY ? maxY : pxnyY;
            maxY = maxY > pxpyY ? maxY : pxpyY;
            area[0] = minX + rm20;
            area[1] = minY + rm21;
            area[2] = maxX + rm20;
            area[3] = maxY + rm21;
            return area;
        }
    }
}
