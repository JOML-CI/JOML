# [JOML](http://joml-ci.github.io/JOML) – Java OpenGL Math Library [![Build Status](https://travis-ci.org/JOML-CI/JOML.svg?branch=android)](https://travis-ci.org/JOML-CI/JOML) [![Maven Central](https://maven-badges.herokuapp.com/maven-central/org.joml/joml-android/badge.svg)](http://search.maven.org/#search%7Cga%7C1%7Ca%3A%22jom-android%22)
A Java math library for OpenGL rendering calculations

Design goals
------------

The goal of JOML is to provide easy-to-use, feature-rich and efficient linear algebra operations, needed by any 3D application. At the same time, JOML tries to pose the lowest possible requirements to an execution environment by being compatible with Java 1.4 and not making use of JNI.

If you like to know more about JOML's design, see the corresponding [Wiki page](https://github.com/JOML-CI/JOML/wiki/Design).

Vector arithmetic
-----------------
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
----------
Using JOML you can build matrices out of basic transformations, such as scale, translate and rotate, using a fluent interface style. All such operations directly modify the matrix instance on which they are invoked.
The following example builds a transformation matrix which effectively first scales all axes by 0.5
and then translates x by 2.0:
```Java
Vector3f v = ...;
new Matrix4f().translate(2.0f, 0.0f, 0.0f)
              .scale(0.5f)
              .transformPosition(v);
// v is now transformed by the specified transformation
```

Common transformation idioms, such as rotating about a given axis using a specific rotation center, can be expressed in a simple way. The following example rotates the point (0, 4, 4) about the x-axis and uses (0, 3, 4) as the rotation center:
```Java
Vector3f center = new Vector3f(0.0f, 3.0f, 4.0f);
Vector3f pointToRotate = new Vector3f(0.0f, 4.0f, 4.0f);
new Matrix4f().translate(center)
              .rotate((float) Math.toRadians(90.0f), 1.0f, 0.0f, 0.0f)
              .translate(center.negate())
              .transformPosition(pointToRotate);
```
The vector *pointToRotate* will now represent (0, 3, 5).

Post-multiplication
-------------------
All transformation operations in the matrix and quaternion classes act in the same way as OpenGL and GLU by post-multiplying the operation's result to the object on which they are invoked. This allows to chain multiple transformations in the same way as with OpenGL's legacy matrix stack operations, and allows to decompose the resulting effective matrix as a decomposition of multiple matrix multiplications.
One such common decomposition are the _projection_ and _modelview_ matrices, written as: `P * MV`. The _modelview_ matrix of course can be further decomposed into individual matrix multiplications, as far as this seems necessary.

When invoking transformation methods in JOML's matrix classes, a convenient way now is to think of Java's _dot_ operator as a matrix multiplication. If multiple matrix operations are chained after one another, as shown in the above example, each individual operation/method creates its matrix which is then post-multiplied to the matrices built before.

In addition to the post-multiplying methods, there are still ways to set a matrix or quaternion to a given transformation regardless of what that matrix or quaternion was before:

```Java
Matrix4f m = new Matrix4f();
Vector3f point = new Vector3f(1.0f, 2.0f, 3.0f);
Vector3f offset = new Vector3f(1.0f, 0.0f, 0.0f);
...
m.translation(offset).transformPosition(point);
```
In the above example, the matrix _m_ is being set to a translation, instead of applying the translation to it.
These methods are useful when the same matrix is being used in a sequence of consecutive operations or repeatedly in a loop without having to set it to the identity each time.

Building a camera transformation
--------------------------------
In the same way that you can concatenate multiple simple affine transformations, you can use the methods perspective(), frustum() and ortho() to specify a perspective or orthographic projection and lookAt() to create an orthonormal transformation that mimics a camera *looking* at a given point.
Those methods resemble the ones known from GLU and act in the same way (i.e. they apply their transformations to an already existing transformation):
```Java
Matrix4f m = new Matrix4f()
     .perspective((float) Math.toRadians(45.0f), 1.0f, 0.01f, 100.0f)
     .lookAt(0.0f, 0.0f, 10.0f,
             0.0f, 0.0f, 0.0f,
             0.0f, 1.0f, 0.0f);
// the camera transformation is now in m
```
The above transformation can then be used as a "view-projection" matrix in a shader.

By convention, the methods `perspective()` and `lookAt()` will assume a right-handed coordinate system. This convention was established for OpenGL and first realized in the OpenGL Utility Library (GLU). JOML follows this convention.

In addition, JOML also supports a left-handed coordinate system, as is used by Direct3D's matrix library. To use a left-handed coordinate system, there are the methods `perspectiveLH()` and `lookAtLH()`, as well as others, whose names end with `LH`:
```Java
Matrix4f m = new Matrix4f()
     .perspectiveLH((float) Math.toRadians(45.0f), 1.0f, 0.01f, 100.0f)
     .lookAtLH(0.0f, 0.0f, -10.0f,
               0.0f, 0.0f, 0.0f,
               0.0f, 1.0f, 0.0f);
```

Computation result
------------------
Usually, instance methods operate on the object (matrix, vector, quaternion) on which they are invoked by writing the computation result back into that object. Most of the methods however also allow to specify another destination object to write the result into. This is useful if you do not want to overwrite the original object with the computation result.
This can be useful for computing the view-projection matrix and its inverse in one go:
```Java
Matrix4f viewProj = new Matrix4f();
Matrix4f invViewProj = new Matrix4f();
viewProj.perspective((float) Math.toRadians(45.0f), 1.0f, 0.01f, 100.0f)
        .lookAt(0.0f, 1.0f, 3.0f,
                0.0f, 0.0f, 0.0f,
                0.0f, 1.0f, 0.0f)
        .invert(invViewProj);
```
The *invViewProj* matrix now contains the inverse of the *viewProj* matrix, but the latter is still intact.

Using with Android
------------------
JOML can be used in an Android project to build a transformation matrix and set it as a uniform mat4 in a shader. For this, the Matrix4f class provides a method to transfer a matrix into a float[] array, which can then be used when calling into OpenGL:
```Java
float[] array = new float[16];
new Matrix4f().perspective((float) Math.toRadians(45.0f), 1.0f, 0.01f, 100.0f)
              .lookAt(0.0f, 0.0f, 10.0f,
                      0.0f, 0.0f, 0.0f,
                      0.0f, 1.0f, 0.0f).get(array);
glUniformMatrix4fv(mat4Location, 1, false, array, 0);
```
The above example first creates a transformation matrix and then uploads that matrix to a uniform variable of the active shader program using the Android method [*glUniformMatrix4fv*](https://developer.android.com/reference/android/opengl/GLES20.html#glUniformMatrix4fv(int, int, boolean, float[], int)).

Instead of using the uniform methods, one or multiple matrices can also be uploaded to an OpenGL buffer object and then sourced from that buffer object from within a shader when used as an uniform buffer object or a shader storage buffer object.
The following uploads a matrix to an OpenGL buffer object which can then be used as an uniform buffer object in a shader:
```Java
Matrix4f m = ...; // <- the matrix to upload
int ubo = ...; // <- name of a created and already initialized UBO
ByteBuffer bb = ByteBuffer.allocateDirect(16 << 2).order(ByteOrder.nativeOrder());
glBindBuffer(GL_UNIFORM_BUFFER, ubo);
glBufferSubData(GL_UNIFORM_BUFFER, 0, m.get(bb));
```
As the example above shows, JOML objects can also be written into Java NIO Buffers.

Staying allocation-free
-----------------------
JOML is designed to be completely allocation-free for all methods. That means JOML will never allocate Java objects on the heap unless you as the client specifically requests to do so via the *new* keyword when creating a new matrix or vector or calling the *toString()* method on them.

*JOML also does not allocate any unexpected internal helper/temporary/working objects itself, neither in instance nor static fields, thus giving you full control over object allocations.*

Since you have to create a matrix or a vector at some point in order to make any computations with JOML on them, you are advised to do so once at the initialization of your program. Those objects will then be the *working memory/objects* for JOML. These working objects can then be reused in the hot path of your application without incurring any additional allocations. The following example shows a typical usecase:

```Java
FloatBuffer fb;
Matrix4f m;

void init() {
  fb = ByteBuffer.allocateDirect(16 << 2).order(ByteOrder.nativeOrder());
  m = new Matrix4f();
  ...
}

void frame() {
  ...
  // compute view-projection matrix
  m.identity()
   .perspective((float) Math.toRadians(45.0f), (float)width/height, 0.01f, 100.0f)
   .lookAt(0.0f, 0.0f, 10.0f,
           0.0f, 0.0f, 0.0f,
           0.0f, 1.0f, 0.0f);
  // possibly apply more model transformations
  m.rotateY(angle);
  // get matrix into FloatBuffer and upload to OpenGL
  glUniformMatrix4fv(mat4Location, 1, false, m.get(fb));
  ...
}
```
In the example above, a single Matrix4f is allocated during some initialization time when the *init()* method is called. Then each *frame()* we reinitialize the same matrix with the *identity()* and recompute the camera transformation based on some other parameters.

Multithreading
--------------
Due to JOML not using any internal temporary objects during any computations, you can use JOML in a multithreaded application. You only need to make sure not to call a method modifying the same matrix or vector from two different threads. Other than that, there is no internal or external synchronization necessary.

Matrix stack
------------
JOML also features an interface that resembles the matrix stack from legacy OpenGL.
This allows you to use all of the legacy OpenGL matrix stack operations even in modern OpenGL applications,
but without the otherwise necessary JNI calls into the graphics driver.
*Note that JOML does not interface in any way with the OpenGL API. It merely provides matrix and vector arithmetics.*
```Java
MatrixStackf s = new MatrixStackf(2);
s.translate(2.0f, 0.0f, 0.0f);
s.pushMatrix();
{
  s.scale(0.5f, 0.5f, 0.5f);
  // do something with the Matrix4f of 's'
}
s.popMatrix();
s.rotate((float) Math.toRadians(45.0f), 0.0f, 0.0f, 1.0f);
// do something with the Matrix4f of 's'
```

Projects and Organisations using JOML
-------------------------------------
This section names real-world projects and organisations using JOML. All owners of the projects and responsible contacts of an organisation listed here were asked for permission before.

* [We Shall Wake](http://www.weshallwake.com/2015/09/we-shall-wake-post-demo-7-briefing.html) – Post-World Accelerated Action.
* [Computer Graphics course](http://www.cs.unh.edu/~cs770/) at the University of New Hampshire
