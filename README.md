# [JOML](http://joml-ci.github.io/JOML) - Java OpenGL Math Library [![Build Status](https://travis-ci.org/JOML-CI/JOML.svg?branch=master)](https://travis-ci.org/JOML-CI/JOML)
A Java-based math library for OpenGL rendering calculations

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
              .scale(0.5f);
              .transformPoint(v);
// v is now transformed by the specified transformation
```

Common transformation idioms, such as rotating about a given axis using a specific rotation center, can be expressed in a simple way. The following example rotates the point (0, 4, 4) about the x-axis and uses (0, 3, 4) as the rotation center:
```Java
Vector3f center = new Vector3f(0.0f, 3.0f, 4.0f);
Vector3f pointToRotate = new Vector3f(0.0f, 4.0f, 4.0f);
new Matrix4f().translate(center)
              .rotate((float) Math.toRadians(90.0f), 1.0f, 0.0f, 0.0f)
              .translate(center.negate())
              .transformPoint(pointToRotate);
```
The vector *pointToRotate* will now represent (0, 3, 5).

Post-multiplication
-------------------
All transformation operations in the matrix and quaternion classes act in the same way as OpenGL and GLU by post-multiplying the operation's result to the object on which they are invoked. This allows to chain multiple transformations in the same way as with OpenGL's legacy matrix stack operations, and allows to decompose the resulting effective matrix as a decomposition of multiple matrix multiplications.
One such common decomposition are the _projection_ and _modelview_ matrices, written as: `P * MV`. The _modelview_ matrix of course can be further decomposed into individual matrix multiplications, as fas as this seems necessary.

When invoking transformation methods in JOML's matrix classes, a convenient way now is to think of Java's _dot_ operator as a matrix multiplication. If multiple matrix operations are chained after one another, as shown in the above example, each individual operation/method creates its matrix which is then post-multiplied to the matrices built before.

In addition to the post-multiplying methods, there are still ways to set a matrix or quaternion to a given transformation regardless of what that matrix or quaternion was before:

```Java
Matrix4f m = new Matrix4f();
Vector3f point = new Vector3f(1.0f, 2.0f, 3.0f);
Vector3f offset = new Vector3f(1.0f, 0.0f, 0.0f);
...
m.translation(offset).transformPoint(point);
```
In the above example, the matrix _m_ is being set to a translation, instead of applying the translation to it.
These methods are useful when the same matrix is being used in a sequence of consecutive operations or repeatedly in a loop without having to set it to the identity each time.

Building a camera transformation
--------------------------------
In the same way that you can concatenate multiple simple affine transformations, you can use the methods perspective(), frustum() and ortho() to specify a perspective or orthogonal projection and lookAt() to create an orthonormal transformation that mimics a camera *looking* at a given point.
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

Using with [LWJGL](https://github.com/LWJGL/lwjgl3)
---------------------------------------------------
JOML can be used together with LWJGL to build a transformation matrix and set it as a uniform mat4 in a shader. For this, the Matrix4f class provides a method to transfer a matrix into a Java NIO FloatBuffer, which can then be used by LWJGL when calling into OpenGL:
```Java
FloatBuffer fb = BufferUtils.createFloatBuffer(16);
new Matrix4f().perspective((float) Math.toRadians(45.0f), 1.0f, 0.01f, 100.0f)
              .lookAt(0.0f, 0.0f, 10.0f,
                      0.0f, 0.0f, 0.0f,
                      0.0f, 1.0f, 0.0f).get(fb);
glUniformMatrix4fv(mat4Location, false, fb);
```
The above example first creates a transformation matrix and then uploads that matrix to a uniform variable of the active shader program using the LWJGL 3 method [*glUniformMatrix4fv*](http://javadoc.lwjgl.org/org/lwjgl/opengl/GL20.html#glUniformMatrix4fv%28int,%20boolean,%20java.nio.FloatBuffer%29).

If you prefer not to use shaders but the fixed-function pipeline and want to use JOML to build the transformation matrices, you can do so. Instead of uploading the matrix as a shader uniform you can then use the OpenGL API call [*glLoadMatrixf()*](http://javadoc.lwjgl.org/org/lwjgl/opengl/GL11.html#glLoadMatrixf%28java.nio.FloatBuffer%29) provided by LWJGL to set a JOML matrix as the current matrix in OpenGL's matrix stack:
```Java
FloatBuffer fb = BufferUtils.createFloatBuffer(16);
Matrix4f m = new Matrix4f();
m.setPerspective((float) Math.toRadians(45.0f), 1.0f, 0.01f, 100.0f).get(fb);
glMatrixMode(GL_PROJECTION);
glLoadMatrixf(fb);
m.setLookAt(0.0f, 0.0f, 10.0f,
            0.0f, 0.0f, 0.0f,
            0.0f, 1.0f, 0.0f).get(fb);
glMatrixMode(GL_MODELVIEW);
glLoadMatrixf(fb);
```

Using with [JOGL](http://jogamp.org/jogl/www/)
---------------------------------------------------
JOML can be used together with JOGL to build a transformation matrix and set it as a uniform mat4 in a shader (for example as a replacement of com.jogamp.opengl.util.glsl.fixedfunc.FixedFuncUtil and com.jogamp.opengl.util.PMVMatrix to emulate the fixed pipeline). For this, the Matrix4f class provides a method to transfer a matrix into a Java NIO FloatBuffer, which can then be used by JOGL when calling into OpenGL:
```Java
FloatBuffer fb = Buffers.newDirectFloatBuffer(16);
new Matrix4f().perspective((float) Math.toRadians(45.0f), 1.0f, 0.01f, 100.0f)
              .lookAt(0.0f, 0.0f, 10.0f,
                      0.0f, 0.0f, 0.0f,
                      0.0f, 1.0f, 0.0f).get(fb);
gl.glUniformMatrix4fv(mat4Location, 1, false, fb);
```
The above example first creates a transformation matrix and then uploads that matrix to a uniform variable of the active shader program using the JOGL 2 method [*glUniformMatrix4fv*](http://jogamp.org/deployment/jogamp-next/javadoc/jogl/javadoc/com/jogamp/opengl/GL2ES2.html#glUniformMatrix4fv(int,%20int,%20boolean,%20java.nio.FloatBuffer)).

If you prefer not to use shaders but the fixed-function pipeline and want to use JOML to build the transformation matrices, you can do so. Instead of uploading the matrix as a shader uniform you can then use the OpenGL API call [*glLoadMatrixf()*](http://jogamp.org/deployment/jogamp-next/javadoc/jogl/javadoc/com/jogamp/opengl/fixedfunc/GLMatrixFunc.html#glLoadMatrixf(java.nio.FloatBuffer)) provided by JOGL to set a JOML matrix as the current matrix in OpenGL's matrix stack:
```Java
FloatBuffer fb = Buffers.newDirectFloatBuffer(16);
Matrix4f m = new Matrix4f();
m.setPerspective((float) Math.toRadians(45.0f), 1.0f, 0.01f, 100.0f).get(fb);
gl.glMatrixMode(GLMatrixFunc.GL_PROJECTION);
gl.glLoadMatrixf(fb);
m.setLookAt(0.0f, 0.0f, 10.0f,
            0.0f, 0.0f, 0.0f,
            0.0f, 1.0f, 0.0f).get(fb);
gl.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);
gl.glLoadMatrixf(fb);
```

Staying allocation-free
-----------------------
JOML is designed to be completely allocation-free for all methods. That means JOML will never allocate Java objects on the heap unless you as the client specifically requests to do so via the *new* keyword when creating a new matrix or vector or calling the *toString()* method on them.

*JOML also does not allocate any unexpected internal helper/temporary/working objects itself, neither in instance nor static fields, thus giving you full control over object allocations.*

Since you have to create a matrix or a vector at some point in order to make any computations with JOML on them, you are advised to do so once at the initialization of your program. Those objects will then be the *working memory/objects* for JOML. These working objects can then be reused in the hot path of your application without incurring any additional allocations. The following example shows a typical usecase with LWJGL:

```Java
FloatBuffer fb;
Matrix4f m;

void init() {
  fb = BufferUtils.createFloatBuffer(16);
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
  m.get(fb);
  glUniformMatrix4fv(mat4Location, false, fb);
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
s.rotate((float) Math.toRadians(45.0f), 0.0f, 0.0f, 1.0f);
s.get(result);
// do something with result
```
