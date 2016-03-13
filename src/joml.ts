/// <reference path="Vector2.ts"/>
/// <reference path="Matrix3.ts"/>

module JOML {
    export function toRadians(degrees: number): number {
        return degrees / 180.0 * Math.PI;
    }
}
