module JOML {
    export class Vector3 {
        x: number;
        y: number;
        z: number;

        constructor();
        constructor(x: number, y: number, z: number);
        constructor(v: Vector3);
        constructor(vx?: any, y?: number, z?: number) {
            if (vx instanceof Vector3) {
                this.x = vx.x;
                this.y = vx.y;
                this.z = vx.z;
            } else if (typeof (vx) === "number") {
                this.x = <number>vx;
                this.y = y;
                this.z = z;
            } else {
                this.x = 0.0;
                this.y = 0.0;
                this.z = 0.0;
            }
        }

        add(x: number, y: number, z: number): Vector3;
        add(v: Vector3): Vector3;
        add(vx: any, y?: number, z?: number): Vector3 {
            if (vx instanceof Vector3) {
                this.x += vx.x;
                this.y += vx.y;
                this.z += vx.z;
            } else {
                this.x += <number>vx;
                this.y += y;
                this.z += z;
            }
            return this;
        }

        set(x: number, y: number, z: number): Vector3 {
            this.x = x;
            this.y = y;
            this.z = z;
            return this;
        }

        div(scalar: number): Vector3 {
            this.x /= scalar;
            this.y /= scalar;
            this.z /= scalar;
            return this;
        }

        lengthSquared(): number {
            return this.x * this.x + this.y * this.y + this.z * this.z;
        }

        length(): number {
            return Math.sqrt(this.lengthSquared());
        }

        normalize(dest?: Vector3): Vector3 {
            dest = dest || this;
            var invLength = 1.0 / this.length();
            dest.x = this.x * invLength;
            dest.y = this.y * invLength;
            dest.z = this.z * invLength;
            return dest;
        }

        toString(): string {
            return "(" + this.x + " " + this.y + " " + this.z + ")";
        }
    }
}
