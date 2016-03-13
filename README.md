# [JOML.js](http://joml-ci.github.io/JOML) - JavaScript OpenGL Math Library
A JavaScript-based Math library for WebGL/OpenGL rendering calculations

Matrix API
----------
Using JOML you can build matrices out of basic transformations, such as scale, translate and rotate, using a fluent interface style. All such operations directly modify the matrix instance on which they are invoked.
The following example builds a transformation matrix which effectively first scales all axes by 0.5
and then translates x by 2.0:
```TypeScript
var v:Vector2 = ...; // <- some position to transform
new Matrix3().translate(2.0, 0.0)
             .scale(0.5);
             .transformPosition(v);
// v is now transformed by the specified transformation
```

Common transformation idioms, such as rotating using a specific rotation center, can be expressed in a simple way. The following example rotates the point (0, 3) and uses (3, 0) as the rotation center:
```TypeScript
var center = new Vector2(3.0, 0.0);
var pointToRotate = new Vector2(0.0, 3.0);
new Matrix3().translate(center)
             .rotate(JOML.toRadians(90.0))
             .translate(center.negate())
             .transformPosition(pointToRotate);
```
The vector *pointToRotate* will now represent (0, -3).

Using with WebGL
----------------
JOML.js is meant to be used with WebGL in a Browser environment. Using JOML.js you can build a transformation matrix and set it as a uniform mat3 in a shader. For this, the Matrix3 class provides a method to transfer a matrix into a JavaScript Float32Array, which can then be used by WebGL:
```TypeScript
var fa = new Float32Array(9);
new Matrix3().view(-10.0, 10.0, -10.0, 10.0)
             .rotate(JOML.toRadians(45.0f))
             .get(fa);
gl.uniformMatrix3fv(mat3Location, false, fa);
```
The above example first creates a transformation matrix and then uploads that matrix to a uniform variable of the active shader program using the WebGL function uniformMatrix3fv on the WebGLRenderingContext object.
