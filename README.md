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
Vector4f v = new Vector4f(1.0f, 0.0f, 0.0f, 1.0f);
new Matrix4f().translate(2.0f, 0.0f, 0.0f)
              .scale(0.5f);
              .transform(v);
// v is now transformed by the specified transformation
```

Common transformation idioms, such as rotating about a given axis using a specific rotation center, can be expressed in a simple way. The following example rotates the point (0, 4, 4) about the x-axis and uses (0, 3, 4) as the rotation center:
```Java
Vector3f center = new Vector3f(0.0f, 3.0f, 4.0f);
Vector4f pointToRotate = new Vector4f(0.0f, 4.0f, 4.0f, 1.0f);
new Matrix4f().translate(center)
              .rotate(90.0f, 1.0f, 0.0f, 0.0f)
              .translate(center.negate())
              .transform(pointToRotate);
```
The vector *pointToRotate* will now represent (0, 3, 5).

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
     .get(fb);
fb.rewind();
glUniformMatrix4fv(mat4Location, false, fb);
```

Staying allocation-free
-----------------------
JOML is designed to be completely allocation-free for all methods. That means JOML will never allocate Java objects on the heap unless you as the client specifically requests to do so via the *new* keyword when creating a new matrix or vector or calling the *toString()* method on them.

Since you have to create a matrix or a vector at some point in order to make any computations with JOML on them, you are advised to do so once at the initialization of your program. Those objects will then be the *working memory/objects* for JOML. These working objects can then be reused in your hot path of your application without incurring any additional allocations. The following example shows a typical usecase with LWJGL:

```Java
FloatBuffer fb;
Matrix4f m;

void init() {
  fb = BufferUtils.createFloatBuffer(16);
  m = new Matrix4f();
  ...
}

void frame() {
  ..
  m.identity()
   .perspective(45.0f, (float)width/height, 0.01f, 100.0f)
   .lookAt(0.0f, 0.0f, 10.0f,
           0.0f, 0.0f, 0.0f,
           0.0f, 1.0f, 0.0f)
   .get(fb);
  fb.rewind();
  glUniformMatrix4fv(mat4Location, false, fb);
  ...
}
```
In the example above, a single Matrix4f is allocated during some initialization time when the *init()* method is called. Then each *frame()* we reinitialize the same matrix with the *identity()* and recompute the camera transformation based on some other parameters.

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
