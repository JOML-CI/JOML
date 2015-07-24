# [JOML](http://joml-ci.github.io/JOML) - Java OpenGL Math Library [![Build Status](https://travis-ci.org/JOML-CI/JOML.svg?branch=2d)](https://travis-ci.org/JOML-CI/JOML)
A Java-based math library for OpenGL rendering calculations

Design goals
------------

The goal of JOML-2D is to provide easy-to-use, feature-rich and efficient linear algebra operations, needed by any 2D application. At the same time, JOML tries to pose the lowest possible requirements to an execution environment by being compatible with Java 1.4 and not making use of JNI.

If you like to know more about JOML's design, see the corresponding [Wiki page](https://github.com/JOML-CI/JOML/wiki/Design).

Vector arithmetic
-----------------
All operations in JOML are designed to modify the object on which the operation is invoked. This helps in completely eliminating any object allocations, which the client could otherwise not control and which impact the GC performance resulting in small hickups.
The client is responsible to allocate the needed working objects.
```Java
Vector2f v = new Vector2f(0.0f, 1.0f);
Vector2f a = new Vector2f(1.0f, 0.0f);
// v = v + a
v.add(a);
// a = a/|a|
a.normalize();
```

Matrix API
----------
Using JOML you can build matrices out of basic transformations, such as scale, translate and rotate, using a fluent interface style. All such operations directly modify the matrix instance on which they are invoked.
The following example builds a transformation matrix which effectively first scales all axes by 0.5
and then translates x by 2.0:
```Java
Vector2f v = ...;
new Matrix3f().translate(2.0f, 0.0f)
              .scale(0.5f);
              .transform(v);
// v is now transformed by the specified transformation
```

Common transformation idioms, such as rotating using a specific rotation center, can be expressed in a simple way. The following example rotates the point (0, 3) and uses (3, 3) as the rotation center:
```Java
Vector2f center = new Vector2f(3.0f, 0.0f);
Vector2f pointToRotate = new Vector2f(0.0f, 3.0f);
new Matrix3f().translate(center)
              .rotate((float) Math.toRadians(90.0f))
              .translate(center.negate())
              .transform(pointToRotate);
```
The vector *pointToRotate* will now represent (0, -3).

Post-multiplication
-------------------
All transformation operations in the matrix class act in the same way as OpenGL and GLU by post-multiplying the operation's result to the object on which they are invoked. This allows to chain multiple transformations in the same way as with OpenGL's legacy matrix stack operations, and allows to decompose the resulting effective matrix as a decomposition of multiple matrix multiplications.

When invoking transformation methods in JOML's matrix class, a convenient way now is to think of Java's _dot_ operator as a matrix multiplication. If multiple matrix operations are chained after one another, as shown in the above example, each individual operation/method creates its matrix which is then post-multiplied to the matrices built before.

In addition to the post-multiplying methods, there are still ways to set a matrix to a given transformation regardless of what that matrix or quaternion was before:

```Java
Matrix3f m = new Matrix3f();
Vector2f point = new Vector2f(1.0f, 2.0f);
Vector2f offset = new Vector2f(1.0f, 0.0f);
...
m.translation(offset).transform(point);
```
In the above example, the matrix _m_ is being set to a translation, instead of applying the translation to it.
These methods are useful when the same matrix is being used in a sequence of consecutive operations or repeatedly in a loop without having to set it to the identity each time.

Using with [LWJGL](https://github.com/LWJGL/lwjgl3)
---------------------------------------------------
JOML can be used together with LWJGL to build a transformation matrix and set it as a uniform mat3 in a shader. For this, the Matrix3f class provides a method to transfer a matrix into a Java NIO FloatBuffer, which can then be used by LWJGL when calling into OpenGL:
```Java
FloatBuffer fb = BufferUtils.createFloatBuffer(9);
new Matrix3f().view(-10.0f, 10.0f, -10.0f, 10.0f)
              .rotate((float) Math.toRadians(45.0f))
              .get(fb);
glUniformMatrix3fv(mat3Location, false, fb);
```
The above example first creates a transformation matrix and then uploads that matrix to a uniform variable of the active shader program using the LWJGL 3 method [*glUniformMatrix3fv*](http://javadoc.lwjgl.org/org/lwjgl/opengl/GL20.html#glUniformMatrix3fv%28int,%20boolean,%20java.nio.FloatBuffer%29).

Staying allocation-free
-----------------------
JOML is designed to be completely allocation-free for all methods. That means JOML will never allocate Java objects on the heap unless you as the client specifically requests to do so via the *new* keyword when creating a new matrix or vector or calling the *toString()* method on them.

*JOML also does not allocate any unexpected internal helper/temporary/working objects itself, neither in instance nor static fields, thus giving you full control over object allocations.*

Since you have to create a matrix or a vector at some point in order to make any computations with JOML on them, you are advised to do so once at the initialization of your program. Those objects will then be the *working memory/objects* for JOML. These working objects can then be reused in the hot path of your application without incurring any additional allocations. The following example shows a typical usecase with LWJGL:

```Java
FloatBuffer fb;
Matrix3f m;

void init() {
  fb = BufferUtils.createFloatBuffer(9);
  m = new Matrix3f();
  ...
}

void frame() {
  ...
  // compute transformation matrix
  m.identity()
   .view(-10.0f, 10.0f, -10.0f, 10.0f)
   .rotate((float) Math.toRadians(45.0f));
  // possibly apply more model transformations
  m.translate(x, y);
  // get matrix into FloatBuffer and upload to OpenGL
  m.get(fb);
  glUniformMatrix3fv(mat3Location, false, fb);
  ...
}
```
In the example above, a single Matrix4f is allocated during some initialization time when the *init()* method is called. Then each *frame()* we reinitialize the same matrix with the *identity()* and recompute the camera transformation based on some other parameters.

Multithreading
--------------
Due to JOML not using any internal temporary objects during any computations, you can use JOML in a multithreaded application. You only need to make sure not to call a method modifying the same matrix or vector from two different threads. Other than that, there is no internal or external synchronization necessary.
