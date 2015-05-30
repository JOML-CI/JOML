# JOML - Java-OpenGL-Math-Library [![Build Status](https://travis-ci.org/JOML-CI/Java-OpenGL-Math-Library.svg?branch=master)](https://travis-ci.org/JOML-CI/Java-OpenGL-Math-Library)
A Java-based math library for OpenGL rendering calculations

Design goals
------------

The goal of JOML is to provide easy-to-use, feature-rich and efficient linear algebra operations, which are needed by any 3D application developers.

Simple examples
---------------
The following is a collection of simple usage examples.

### Vector arithmetic
All operations in JOML are designed to modify the object on which the operation is invoked. This helps in completely eliminating any object allocations, which the client could otherwise not control and which impact the GC performance resulting in small hickups.
The client is responsible to allocate the needed working objects.
```Java
Vector3f v = new Vector3f(0.0f, 1.0f, 0.0f);
Vector3f a = new Vector3f(1.0f, 0.0f, 0.0f);
// v = v + a
v.add(a);
// a = a x v
a.cross(v);
// a = a/|a|
a.normalize();
```

Matrix API
------------
Using JOML you can build matrices out of basic transformations, such as scale, translate and rotate, using a fluent-interface style. All such operations directly modify the matrix instance on which they are invoked.
The following example builds a transformation matrix which effectively first scales all axes by 0.5
and then translates x by 2.0:
```Java
Matrix4f m = new Matrix4f().translate(2.0f, 0.0f, 0.0f).scale(0.5f);
Vector4f v = new Vector4f(1.0f, 0.0f, 0.0f, 1.0f);
m.transform(v);
// v is now transformed using the transformations in m
```

Common transformation idioms, such as rotating about a given axis using a specific rotation center, can be expressed in a simple way. The following example rotates about the x-axis and uses (2, 3, 4) as the rotation center:
```Java
Matrix4f rot = new Matrix4f();
Vector3f center = new Vector3f(2.0f, 3.0f, 4.0f);
rot.translate(center)
   .rotate(45.0f, 1.0f, 0.0f, 0.0f)
   .translate(center.negate());
```

Perspective projections
------------
In the same way that you can concatenate/multiply multiple simple affine transformations, you can use the methods Matrix4f.perspective(), .ortho() to specify a perspective or orthogonal projection and .lookAt() to create a orthonormal transformation that resembles a camera "looking" into a given direction. Those three methods resemble the ones known from GLU and act in the same way (i.e. they apply their transformations to an already existing transformation):
```Java
Matrix4f m = new Matrix4f()
     .perspective(45.0f, 1.0f, 0.01f, 100.0f)
     .lookAt(0.0f, 0.0f, 10.0f,
             0.0f, 0.0f, 0.0f,
             0.0f, 1.0f, 0.0f);
// the camera transformation is now in m
```
The above transformation can then be used as a "view-projection" matrix in a shader.

Computation result
------------
Usually, the instance methods in Matrix4f operate on the matrix on which they are invoked by writing the computation result into that matrix back. Most of the methods however also allow to specify another destination matrix to write the result into.
This can be useful for computing the view-projection matrix and its inverse in one go:
```Java
Matrix4f viewProj = new Matrix4f();
Matrix4f invViewProj = new Matrix4f();
viewProj.perspective(45.0f, 1.0f, 0.01f, 100.0f)
        .lookAt(0.0f, 1.0f, 3.0f,
                0.0f, 0.0f, 0.0f,
                0.0f, 1.0f, 0.0f)
        .invert(invViewProj);
```
The *invViewProj* matrix now contains the inverse of the *viewProj* matrix, but the latter is still intact.

Using with [LWJGL](https://github.com/LWJGL/lwjgl3)
------------
JOML can be used together with LWJGL to build a transformation matrix and set it as a uniform mat4 in a shader. The Matrix4f class provides a method to transfer a matrix into a Java NIO FloatBuffer, which can then be used by LWJGL when calling into OpenGL:
```Java
FloatBuffer fb = BufferUtils.createFloatBuffer(16);
Matrix4f m = new Matrix4f()
     .perspective(45.0f, 1.0f, 0.01f, 100.0f)
     .lookAt(0.0f, 0.0f, 10.0f,
             0.0f, 0.0f, 0.0f,
             0.0f, 1.0f, 0.0f);
m.get(fb);
fb.rewind();
glUniformMatrix4fv(mat4Location, false, fb);
```

Matrix stack
------------
JOML also features an interface that resembles the matrix stack from legacy OpenGL.
This allows you to use all of the legacy OpenGL matrix stack operations even in modern OpenGL applications,
but without the otherwise necessary JNI calls into the graphics driver.
*Note that JOML does not interface in any way with the OpenGL API. It merely provides matrix and vector arithmetics.*
```Java
MatrixStack s = new MatrixStack(2);
Matrix4f result = new Matrix4f();
s.translate(2.0f, 0.0f, 0.0f);
s.pushMatrix();
{
  s.scale(0.5f, 0.5f, 0.5f);
  s.get(result);
  // do something with result
}
s.popMatrix();
s.rotate(45.0f, 0.0f, 0.0f, 1.0f);
s.get(result);
// do something with result
```
