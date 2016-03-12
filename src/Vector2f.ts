module JOML {
    export class Vector2f {
        x: number;
        y: number;

        constructor();
        constructor(x: number, y: number);
        constructor(v: Vector2f);
        constructor(vx?: (Vector2f | number), y?: number) {
            if (vx instanceof Vector2f) {
                this.x = vx.x;
                this.y = vx.y;
            } else if (typeof (vx) === "number") {
                this.x = <number>vx;
                this.y = y;
            } else {
                this.x = 0.0;
                this.y = 0.0;
            }
        }

        add(x: number, y: number): Vector2f;
        add(v: Vector2f): Vector2f;
        add(vx: (Vector2f | number), y?: number): Vector2f {
            if (vx instanceof Vector2f) {
                this.x += vx.x;
                this.y += vx.y;
            } else {
                this.x += <number>vx;
                this.y += y;
            }
            return this;
        }

        set(x: number, y: number): Vector2f {
            this.x = x;
            this.y = y;
            return this;
        }
    }
}
