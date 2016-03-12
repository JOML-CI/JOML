module JOML {
    export class Vector2 {
        x: number;
        y: number;

        constructor();
        constructor(x: number, y: number);
        constructor(v: Vector2);
        constructor(vx?: (Vector2 | number), y?: number) {
            if (vx instanceof Vector2) {
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

        add(x: number, y: number): Vector2;
        add(v: Vector2): Vector2;
        add(vx: (Vector2 | number), y?: number): Vector2 {
            if (vx instanceof Vector2) {
                this.x += vx.x;
                this.y += vx.y;
            } else {
                this.x += <number>vx;
                this.y += y;
            }
            return this;
        }

        set(x: number, y: number): Vector2 {
            this.x = x;
            this.y = y;
            return this;
        }
    }
}
