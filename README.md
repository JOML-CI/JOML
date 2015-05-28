# JOML - Java-OpenGL-Math-Library
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
Vector3f v = new Vector3f();
Vector3f a = new Vector3f(1.0f, 0.0f, 0.0f);
// v = v + a
v.add(a);
// a = cross product of v and a
a.cross(v);
```

Matrix stack
------------
JOML features an interface that resembles the matrix stack from legacy OpenGL.
This allows you to compose the final transformation matrix using a sequence of simple operations,
but without the otherwise necessary JNI calls into the graphics driver.

The following example builds a transformation matrix which effectively first scales all axes by 0.5
and then translates x by 2.0:
```Java
MatrixStack stack = new MatrixStack();
// Compose the final matrix
stack.translate(2.0f, 0.0f, 0.0f);
stack.scale(0.5f, 0.5f, 0.5f);
// Obtain the final matrix
Matrix4f result = new Matrix4f();
stack.get(result);
// Or store it into a FloatBuffer for final submission to OpenGL as uniform mat4
FloatBuffer fb = ByteBuffer.allocateDirect(4 * 16).order(ByteOrder.nativeOrder()).asFloatBuffer();
stack.get(fb);
fb.rewind();
```
