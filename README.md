# [JOML.js](http://joml-ci.github.io/JOML) – JavaScript OpenGL Math Library [![Build Status](https://travis-ci.org/JOML-CI/JOML.svg?branch=2d-js)](https://travis-ci.org/JOML-CI/JOML)
A JavaScript-based Math library for WebGL/OpenGL rendering calculations

Matrix API
----------
Using JOML you can build matrices out of basic transformations, such as scale, translate and rotate, using a fluent interface style. All such operations directly modify the matrix instance on which they are invoked.
The following example builds a transformation matrix which effectively first scales all axes by 0.5
and then translates x by 2.0:
```TypeScript
var v:JOML.Vector2 = ...; // <- some position to transform
new JOML.Matrix3().translate(2.0, 0.0)
                  .scale(0.5);
                  .transformPosition(v);
// v is now transformed by the specified transformation
```

Common transformation idioms, such as rotating using a specific rotation center, can be expressed in a simple way. The following example rotates the point (0, 3) and uses (3, 0) as the rotation center:
```TypeScript
var center = new JOML.Vector2(3.0, 0.0);
var pointToRotate = new JOML.Vector2(0.0, 3.0);
new JOML.Matrix3().translate(center)
                  .rotate(JOML.toRadians(90.0))
                  .translate(center.negate())
                  .transformPosition(pointToRotate);
```
The vector *pointToRotate* will now represent (0, -3).

Post-multiplication
-------------------
All transformation operations in the matrix class act in the same way as OpenGL for Desktop and GLU by post-multiplying the operation's result to the object on which they are invoked. This allows to chain multiple transformations in the same way as with OpenGL's legacy matrix stack operations, and allows to decompose the resulting effective matrix as a decomposition of multiple matrix multiplications.

When invoking transformation methods in JOML's matrix class, a convenient way now is to think of Java's _dot_ operator as a matrix multiplication. If multiple matrix operations are chained after one another, as shown in the above example, each individual operation/method creates its matrix which is then post-multiplied to the matrices built before.

In addition to the post-multiplying methods, there are still ways to set a matrix to a given transformation regardless of what that matrix or quaternion was before:

```TypeScript
var m = new JOML.Matrix3();
var point = new JOML.Vector2(1.0, 2.0);
var offset = new JOML.Vector2(1.0, 0.0);
...
m.translation(offset).transformPosition(point);
```
In the above example, the matrix _m_ is being set to a translation, instead of applying the translation to it.
These methods are useful when the same matrix is being used in a sequence of consecutive operations or repeatedly in a loop without having to set it to the identity each time.

Building a view transformation
--------------------------------
In the same way that you can concatenate multiple simple translations and rotations, you can use the method view() to specify the visible extents of the scene via the bottom-left and top-right corner coordinates.
This method resembles the _gluOrtho2D_ known from GLU and acts in the same way (i.e. they apply their transformations to an already existing transformation) although it does not do a projection:
```Java
var m = new JOML.Matrix3()
     .view(-10.0, 10.0, -10.0, 10.0)
     .translate(1.0, 2.0);
// the view transformation is now in m
```
The above transformation defines a visible view ranging from -10 to 10 in each dimension and then translates the camera by (-1, -2).

Using with WebGL
----------------
JOML.js is meant to be used with WebGL in a Browser environment. Using JOML.js you can build a transformation matrix and set it as a uniform mat3 in a shader. For this, the Matrix3 class provides a method to transfer a matrix into a JavaScript Float32Array, which can then be used by WebGL:
```TypeScript
var fa = new Float32Array(9);
new JOML.Matrix3().view(-10.0, 10.0, -10.0, 10.0)
                  .rotate(JOML.toRadians(45.0))
                  .get(fa);
gl.uniformMatrix3fv(mat3Location, false, fa);
```
The above example first creates a transformation matrix and then uploads that matrix to a uniform variable of the active shader program using the WebGL function uniformMatrix3fv on the WebGLRenderingContext object.
