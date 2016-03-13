/// <reference path="Vector2.ts"/>
/// <reference path="Vector3.ts"/>
/// <reference path="Vector4.ts"/>
/// <reference path="Matrix4.ts"/>

module JOML {
    export function toRadians(degrees: number): number {
        return degrees / 180.0 * Math.PI;
    }
}
