# [JOML](http://joml-ci.github.io/JOML) – Java OpenGL Math Library
A Java math library for OpenGL rendering calculations | use it on: [Desktop](https://github.com/JOML-CI/JOML/wiki#maven-setup-for-desktop) / [Android](https://github.com/JOML-CI/JOML/wiki#gradle-setup-for-android) / [GWT](https://github.com/JOML-CI/JOML/wiki#gradle-setup-for-gwt)

![Build and Publish](https://github.com/JOML-CI/JOML/actions/workflows/main.yml/badge.svg) [![Maven Release](https://img.shields.io/maven-metadata/v/https/repo.maven.apache.org/maven2/org/joml/joml/maven-metadata.xml.svg)](https://search.maven.org/#search%7Cgav%7C1%7Cg%3Aorg.joml%20a%3Ajoml) [![Maven Snapshot](https://img.shields.io/nexus/s/https/oss.sonatype.org/org.joml/joml.svg)](https://oss.sonatype.org/content/repositories/snapshots/org/joml/joml/)

Design goals
------------

The goal of JOML [ʤˈɒml̩] is to provide easy-to-use, feature-rich and efficient linear algebra operations, needed by any 3D application. At the same time, JOML tries to pose the lowest possible requirements to an execution environment by being compatible with Java 1.4 and not making use of JNI.

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

Using with [LWJGL](https://github.com/LWJGL/lwjgl3)
---------------------------------------------------
JOML can be used together with LWJGL to build a transformation matrix and set it as a uniform mat4 in a shader. For this, the Matrix4f class provides a method to transfer a matrix into a Java NIO FloatBuffer, which can then be used by LWJGL when calling into OpenGL:
```Java
try (MemoryStack stack = MemoryStack.stackPush()) {
  FloatBuffer fb = new Matrix4f()
    .perspective((float) Math.toRadians(45.0f), 1.0f, 0.01f, 100.0f)
    .lookAt(0.0f, 0.0f, 10.0f,
            0.0f, 0.0f, 0.0f,
            0.0f, 1.0f, 0.0f)
    .get(stack.mallocFloat(16));
  glUniformMatrix4fv(mat4Location, false, fb);
}
```
The above example first creates a transformation matrix and then uploads that matrix to a uniform variable of the active shader program using the LWJGL 3 method [*glUniformMatrix4fv*](https://javadoc.lwjgl.org/org/lwjgl/opengl/GL20.html#glUniformMatrix4fv(int,boolean,java.nio.FloatBuffer)).

Also please read [Memory Management in LWJGL 3](https://blog.lwjgl.org/memory-management-in-lwjgl-3/) on how to properly manage native memory that JOML will store the matrices to.

Instead of using the uniform methods, one or multiple matrices can also be uploaded to an OpenGL buffer object and then sourced from that buffer object from within a shader when used as an uniform buffer object or a shader storage buffer object.
The following uploads a matrix to an OpenGL buffer object which can then be used as an uniform buffer object in a shader:
```Java
Matrix4f m = ...; // <- the matrix to upload
int ubo = ...; // <- name of a created and already initialized UBO
try (MemoryStack stack = MemoryStack.stackPush()) {
  glBindBuffer(GL_UNIFORM_BUFFER, ubo);
  glBufferSubData(GL_UNIFORM_BUFFER, 0, m.get(stack.mallocFloat(16)));
}
```

If you prefer not to use shaders but the fixed-function pipeline and want to use JOML to build the transformation matrices, you can do so. Instead of uploading the matrix as a shader uniform you can then use the OpenGL API call [*glLoadMatrixf()*](https://javadoc.lwjgl.org/org/lwjgl/opengl/GL11.html#glLoadMatrixf(java.nio.FloatBuffer)) provided by LWJGL to set a JOML matrix as the current matrix in OpenGL's matrix stack:
```Java
Matrix4f m = new Matrix4f();
m.setPerspective((float) Math.toRadians(45.0f), 1.0f, 0.01f, 100.0f);
glMatrixMode(GL_PROJECTION);
try (MemoryStack stack = MemoryStack.stackPush()) {
  glLoadMatrixf(m.get(stack.mallocFloat(16)));
}
m.setLookAt(0.0f, 0.0f, 10.0f,
            0.0f, 0.0f, 0.0f,
            0.0f, 1.0f, 0.0f);
glMatrixMode(GL_MODELVIEW);
try (MemoryStack stack = MemoryStack.stackPush()) {
  glLoadMatrixf(m.get(stack.mallocFloat(16)));
}
```

Using with Vulkan and LWJGL 3
----------------------------
You can use [VK10.vkMapMemory()](https://javadoc.lwjgl.org/org/lwjgl/vulkan/VK10.html#vkMapMemory(org.lwjgl.vulkan.VkDevice,long,long,long,int,org.lwjgl.PointerBuffer)) provided by LWJGL to map a Vulkan memory object, which may be the backing store of a Uniform Buffer, into Java and use the returned Java NIO ByteBuffer to upload the matrix like you would with OpenGL by calling [get()](http://joml-ci.github.io/JOML/apidocs/org/joml/Matrix4f.html#get-java.nio.ByteBuffer-) on the matrix:
```Java
Matrix4f m = ...;
VkDevice device = ...; // <- the vulkan device
long memory = ...; // <- handle to the vulkan device memory
try (MemoryStack stack = MemoryStack.stackPush()) {
  PointerBuffer pb = stack.mallocPointer(1);
  if (vkMapMemory(device, memory, 0, 16 << 2, 0, pb) == VK_SUCCESS) {
    m.get(MemoryUtil.memByteBuffer(pb.get(0), 16 << 2));
    vkUnmapMemory(device, memory);
  }
}
```

Since Vulkan uses a clip space z range between *0 <= z <= w* you need to tell JOML about it when creating a projection matrix. For this, the projection methods on the Matrix4f class have an additional overload taking a boolean parameter to indicate whether Z should be within [0..1] like in Vulkan or [-1..+1] like in OpenGL. The existing method overload without that parameter will default to OpenGL behaviour.

Alternatively, you can use Vulkan's Push Constants to quickly upload a matrix in a shader push-constant when recording a command buffer. The following code updates a Matrix4f used as a push-constant in the vertex shader:
```Java
Matrix4f m = ...;
VkCommandBuffer cmdBuf = ...; // <- the VkCommandBuffer
long layout = ...; // <- handle to a Vulkan VkPipelineLayout
try (MemoryStack stack = MemoryStack.stackPush()) {
  vkCmdPushConstants(cmdBuf, layout, VK_SHADER_STAGE_VERTEX_BIT,
                     0, m.get(stack.mallocFloat(16)));
}
```

Also, care must be taken regarding the difference between Vulkan's viewport transformation on the one side and Direct3D's and OpenGL's different viewport transformation on the other side. Since Vulkan does not perform any inversion of the Y-axis from NDC to window coordinates, NDC space and clip space will have its +Y axis pointing downwards (with regard to the screen).
In order to account for this, you need to use a premultiplied scaling transformation that inverts the Y-axis.

In essence, to create a projection transformation which will work with Vulkan, use the following code:
```Java
Matrix4f m = new Matrix4f();
m.scale(1.0f, -1.0f, 1.0f) // <- inversion of Y axis
 .perspective((float) Math.toRadians(45.0f),
               1.0f, 0.01f, 100.0f, true); // <- true indicates Z in [0..1]
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
m.setPerspective((float) Math.toRadians(45.0f), 1.0f, 0.01f, 100.0f);
gl.glMatrixMode(GLMatrixFunc.GL_PROJECTION);
gl.glLoadMatrixf(m.get(fb));
m.setLookAt(0.0f, 0.0f, 10.0f,
            0.0f, 0.0f, 0.0f,
            0.0f, 1.0f, 0.0f);
gl.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);
gl.glLoadMatrixf(m.get(fb));
```

Using with [Java 2D](https://docs.oracle.com/javase/tutorial/2d/)
---------------------------------------------------
Java 2D holds the transformation applied to drawn primitives in an instance of the [AffineTransform](https://docs.oracle.com/javase/7/docs/api/java/awt/geom/AffineTransform.html) class, which can be manipulated directly or operated on via methods on the [Graphics2D](https://docs.oracle.com/javase/7/docs/api/java/awt/Graphics2D.html) class, such as [rotate()](https://docs.oracle.com/javase/7/docs/api/java/awt/Graphics2D.html#rotate(double)) or [translate()](https://docs.oracle.com/javase/7/docs/api/java/awt/Graphics2D.html#translate(double,%20double)).
Instead of the AffineTransformation, JOML's matrix classes can be used to build the transformations. When a matrix has been built in JOML, its represented transformation can be copied to an AffineTransform instance like so:
```Java
Matrix4f m = ...; // <- any affine 2D transformation built with JOML
AffineTransform t = ...; // <- any Java 2D AffineTransform (e.g. Graphics2D.getTransform())
t.setTransform(m.m00(), m.m01(), m.m10(), m.m11(), m.m30(), m.m31());
```
The above will copy any affine 2D transformation, represented by the Matrix4f, to the provided AffineTransform instance.

Since Java 2D cannot represent 3D transformations, using a Matrix4f in JOML is not necessary. For 2D transformations JOML provides the Matrix3x2f and Matrix3x2d classes. If those are used, copying the 2D transformation into a Java 2D AffineTransform instance works like this:
```Java
Matrix3x2f m = ...; // <- any 2D transformation built with JOML
AffineTransform t = ...; // <- any Java 2D AffineTransform
t.setTransform(m.m00, m.m01, m.m10, m.m11, m.m20, m.m21);
```
*(Note: The AffineTransform class uses a different order for the row and column indices of a matrix element. Here, the row index comes first, and then the column index)*

Using with [JavaFX](http://docs.oracle.com/javafx/)
---------------------------------------------------
JavaFX holds arbitrary affine transformations in an instance of the [Affine](https://docs.oracle.com/javase/8/javafx/api/javafx/scene/transform/Affine.html) class.
Instead of operating on Affine objects, JOML's matrix classes can be used to build the transformations. When a matrix has been built in JOML, its represented transformation can be copied to an Affine instance like so:
```Java
Matrix4f m = ...; // <- any affine transformation built with JOML
Affine t = ...; // <- any JavaFX Affine instance
t.setToTransform(m.m00(), m.m10(), m.m20(), m.m30(),
                 m.m01(), m.m11(), m.m21(), m.m31(),
                 m.m02(), m.m12(), m.m22(), m.m32());
```
The above will copy any affine transformation, represented by the Matrix4f, to the provided Affine instance.

*(Note: The Affine class uses a different order for the row and column indices of a matrix element. Here, the row index comes first, and then the column index)*

Staying allocation-free
-----------------------
JOML is designed to be completely allocation-free for all methods. That means JOML will never allocate Java objects on the heap unless you as the client specifically requests to do so via the *new* keyword when creating a new matrix or vector or calling the *toString()* method on them.

*JOML also does not allocate any unexpected internal helper/temporary/working objects itself, neither in instance nor static fields, thus giving you full control over object allocations.*

Since you have to create a matrix or a vector at some point in order to make any computations with JOML on them, you are advised to do so once at the initialization of your program. Those objects will then be the *working memory/objects* for JOML. These working objects can then be reused in the hot path of your application without incurring any additional allocations. The following example shows a typical usecase with LWJGL:

```Java
FloatBuffer fb;
Matrix4f m;

void init() {
  fb = MemoryUtil.memAllocFloat(16);
  m = new Matrix4f();
  ...
}
void destroy() {
  MemoryUtil.memFree(fb);
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
  glUniformMatrix4fv(mat4Location, false, m.get(fb));
  ...
}
```
In the example above, a single Matrix4f is allocated during some initialization time when the *init()* method is called. Then each *frame()* we reinitialize the same matrix with the *identity()* and recompute the camera transformation based on some other parameters.

Read-only Views
---------------

In more complex applications with multiple API providers and consumers/clients, it is sometimes desirable to communicate the intent that a certain object returned by an API call or consumed by a call should not get modified by the caller or the callee, respectively. This can be helpful to encourage efficient memory usage by sharing objects and to discourage/disallow unintended object mutations.

For this, JOML provides read-only views on each class, realized via a Java interface implemented by each class. This interface only contains methods that will not mutate the object on which it is called.

```Java
private Vector4f sharedVector;
public Vector4fc getVector() {
  return sharedVector;
}
public void consume(Vector4fc v) {
  // ...
}
```
Using read-only views, it is possible to declare who is responsible of mutating an object and who is not allowed to.

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
Matrix4fStack s = new Matrix4fStack(2);
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

Literature
----------
This section contains a list of books/tutorials/articles featuring JOML.

* [Computer Graphics Programming in OpenGL with Java, 2nd Edition](https://www.amazon.com/dp/1683922190) – [Mercury Learning](http://www.merclearning.com/titles/Computer_Graphics_Programming_in_OpenGL_with_Java_Second_Edition.html)
* [3D Game Development with LWJGL 3](https://ahbejarano.gitbook.io/lwjglgamedev/)
