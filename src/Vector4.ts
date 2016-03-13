module JOML {
    export class Vector4 {
        x: number;
        y: number;
        z: number;
        w: number;

        constructor();
        constructor(x: number, y: number, z: number, w: number);
        constructor(v: Vector4);
        constructor(vx?: any, y?: number, z?: number, w?: number) {
            if (vx instanceof Vector4) {
                this.x = vx.x;
                this.y = vx.y;
                this.z = vx.z;
                this.w = vx.w;
            } else if (typeof (vx) === "number") {
                this.x = <number>vx;
                this.y = y;
                this.z = z;
                this.w = w;
            } else {
                this.x = 0.0;
                this.y = 0.0;
                this.z = 0.0;
                this.w = 1.0;
            }
        }

        set(x: number, y: number, z: number, w: number): Vector4 {
            this.x = x;
            this.y = y;
            this.z = z;
            this.w = w;
            return this;
        }

        div(scalar: number): Vector4 {
            this.x /= scalar;
            this.y /= scalar;
            this.z /= scalar;
            this.w /= scalar;
            return this;
        }

        normalize3(): Vector4 {
            var invLength = (1.0 / Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z));
            this.x *= invLength;
            this.y *= invLength;
            this.z *= invLength;
            this.w *= invLength;
            return this;
        }

        toString(): string {
            return "(" + this.x + " " + this.y + " " + this.z + " " + this.w + ")";
        }
    }
}
