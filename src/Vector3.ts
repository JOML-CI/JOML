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

        toString(): string {
            return "(" + this.x + " " + this.y + " " + this.z + ")";
        }
    }
}
