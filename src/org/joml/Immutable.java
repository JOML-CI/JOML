/*
 * (C) Copyright 2016 JOML

 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in
 all copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 THE SOFTWARE.

 */
package org.joml;

import java.lang.reflect.Constructor;
import java.util.HashSet;
import java.util.Set;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

/**
 * Create immutable classes/instances from a given object.
 * 
 * @author Kai Burjack
 */
class Immutable implements Opcodes {

    /**
     * Disable creation of immutable objects.
     */
    static final boolean noimmutable = Options.hasOption("joml.noimmutable");

    private static Class immutableMatrix4f;
    private static Constructor immutableMatrix4f_ctor;

    static Matrix4f toImmutable(Matrix4f mat) {
        try {
            if (immutableMatrix4f != null)
                return (Matrix4f) instantiate(mat);
            synchronized (Immutable.class) {
                if (immutableMatrix4f != null)
                    return (Matrix4f) instantiate(mat);
                immutableMatrix4f = create(Matrix4f.class);
                immutableMatrix4f_ctor = immutableMatrix4f.getConstructor(new Class[] { Matrix4f.class });
                return (Matrix4f) instantiate(mat);
            }
        } catch (Exception e) {
            throw new RuntimeException("Could not create immutable instance for " + mat.getClass(), e);
        }
    }

    private static Object instantiate(Object obj) throws Exception {
        return immutableMatrix4f_ctor.newInstance(new Object[] { obj });
    }

    private static Class create(final Class target) {
        final String targetInternalName = Type.getInternalName(target);
        final String targetDesc = Type.getDescriptor(target);
        try {
            ClassReader cr = new ClassReader(targetInternalName);

            final Set writes = new HashSet();
            final Set reads = new HashSet();
            ClassVisitor scan = new ClassVisitor(ASM5) {
                public MethodVisitor visitMethod(int access, final String methodName, final String methodDesc, String signature, String[] exceptions) {
                    if ("<init>".equals(methodName))
                        return null;
                    MethodVisitor mv = new MethodVisitor(ASM5) {
                        public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
                            if ("Lorg/joml/Write;".equals(desc))
                                writes.add(methodName + methodDesc);
                            else if ("Lorg/joml/Read;".equals(desc))
                                reads.add(methodName + methodDesc);
                            return null;
                        }
                    };
                    return mv;
                }
            };
            cr.accept(scan, ClassReader.SKIP_DEBUG | ClassReader.SKIP_FRAMES);

            ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
            final String className = targetInternalName + "$Immutable";
            cr.accept(new ClassVisitor(ASM5, cw) {
                public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
                    super.visit(version, access, className, signature, targetInternalName, interfaces);
                    cv.visitField(ACC_PRIVATE, "$delegate", targetDesc, null, null);
                }

                public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
                    return null;
                }

                public void visitSource(String source, String debug) {
                    cv.visitSource(className.replace('/', '.'), debug);
                }

                private void delegate(int access, String name, String desc) {
                    MethodVisitor mv = super.visitMethod(access, name, desc, null, null);
                    mv.visitCode();
                    mv.visitVarInsn(ALOAD, 0);
                    mv.visitFieldInsn(GETFIELD, className, "$delegate", targetDesc);
                    Type[] args = Type.getArgumentTypes(desc);
                    Type ret = Type.getReturnType(desc);
                    int argIndex = 1;
                    for (int i = 0; i < args.length; i++) {
                        Type arg = args[i];
                        mv.visitVarInsn(arg.getOpcode(ILOAD), argIndex);
                        argIndex += arg.getSize();
                    }
                    mv.visitMethodInsn(INVOKEVIRTUAL, targetInternalName, name, desc, false);
                    mv.visitInsn(ret.getOpcode(IRETURN));
                    mv.visitMaxs(-1, -1);
                    mv.visitEnd();
                }

                void throwImmutableException(MethodVisitor mv) {
                    mv.visitTypeInsn(NEW, "org/joml/ImmutableException");
                    mv.visitInsn(DUP);
                    mv.visitVarInsn(ALOAD, 0);
                    mv.visitInsn(DUP);
                    mv.visitFieldInsn(GETFIELD, className, "$delegate", targetDesc);
                    mv.visitMethodInsn(INVOKESPECIAL, "org/joml/ImmutableException", "<init>", "(Ljava/lang/Object;Ljava/lang/Object;)V", false);
                    mv.visitInsn(ATHROW);
                }

                public MethodVisitor visitMethod(final int access, final String name, final String desc, String signature, String[] exceptions) {
                    if ("<init>".equals(name)) {
                        if (!(("(" + targetDesc + ")V").equals(desc)))
                            return null;
                        MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
                        mv.visitCode();
                        mv.visitVarInsn(ALOAD, 0);
                        mv.visitMethodInsn(INVOKESPECIAL, targetInternalName, "<init>", "()V", false);
                        mv.visitVarInsn(ALOAD, 0);
                        mv.visitVarInsn(ALOAD, 1);
                        mv.visitFieldInsn(PUTFIELD, className, "$delegate", targetDesc);
                        mv.visitInsn(RETURN);
                        mv.visitMaxs(-1, -1);
                        mv.visitEnd();
                        return null;
                    }
                    if ("toImmutable".equals(name)) {
                        MethodVisitor mv = super.visitMethod(access, name, desc, null, null);
                        mv.visitCode();
                        mv.visitVarInsn(ALOAD, 0);
                        mv.visitInsn(ARETURN);
                        mv.visitMaxs(-1, -1);
                        mv.visitEnd();
                        return null;
                    } else if (reads.contains(name + desc)) {
                        delegate(access, name, desc);
                        return null;
                    } else if (writes.contains(name + desc)) {
                        MethodVisitor mv = super.visitMethod(access, name, desc, null, null);
                        mv.visitCode();
                        throwImmutableException(mv);
                        mv.visitMaxs(-1, -1);
                        mv.visitEnd();
                        return null;
                    }
                    return null;
                }
            }, ClassReader.SKIP_DEBUG | ClassReader.SKIP_FRAMES);
            cw.visitEnd();
            byte[] arr = cw.toByteArray();
            //ClassReader tcr = new ClassReader(arr);
            //tcr.accept(new TraceClassVisitor(new PrintWriter(System.err)), 0);
            return ClassDefineUtils.defineClass(target.getClassLoader(), target, className, arr);
        } catch (Throwable e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}
