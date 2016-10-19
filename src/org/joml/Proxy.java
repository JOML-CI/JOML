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

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

/**
 * Generate interface proxies.
 * <p>
 * This is used to create immutable views on JOML objects when casts to the
 * underlying implementation class should be strictly forbidden.
 * 
 * @author Kai Burjack
 */
class Proxy implements Opcodes {

    private static Class vector4f_proxy;
    private static Constructor vector4f_proxy_ctor;

    private static Class vector3f_proxy;
    private static Constructor vector3f_proxy_ctor;

    private static Class quaternionfc_proxy;
    private static Constructor quaternionfc_proxy_ctor;

    static Vector4fc createVector4fc(Vector4fc delegate) {
        try {
            if (vector4f_proxy != null)
                return (Vector4fc) vector4f_proxy_ctor.newInstance(new Object[] { delegate });
            synchronized (Proxy.class) {
                if (vector4f_proxy != null)
                    return (Vector4fc) vector4f_proxy_ctor.newInstance(new Object[] { delegate });
                vector4f_proxy = proxy(Vector4fc.class);
                vector4f_proxy_ctor = vector4f_proxy.getConstructor(new Class[] { Vector4fc.class });
            }
            return (Vector4fc) vector4f_proxy_ctor.newInstance(new Object[] { delegate });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static Vector3fc createVector3fc(Vector3fc delegate) {
        try {
            if (vector3f_proxy != null)
                return (Vector3fc) vector3f_proxy_ctor.newInstance(new Object[] { delegate });
            synchronized (Proxy.class) {
                if (vector3f_proxy != null)
                    return (Vector3fc) vector3f_proxy_ctor.newInstance(new Object[] { delegate });
                vector3f_proxy = proxy(Vector3fc.class);
                vector3f_proxy_ctor = vector3f_proxy.getConstructor(new Class[] { Vector3fc.class });
            }
            return (Vector3fc) vector3f_proxy_ctor.newInstance(new Object[] { delegate });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static Quaternionfc createQuaternionfc(Quaternionfc delegate) {
        try {
            if (quaternionfc_proxy != null)
                return (Quaternionfc) quaternionfc_proxy_ctor.newInstance(new Object[] { delegate });
            synchronized (Proxy.class) {
                if (quaternionfc_proxy != null)
                    return (Quaternionfc) quaternionfc_proxy_ctor.newInstance(new Object[] { delegate });
                quaternionfc_proxy = proxy(Quaternionfc.class);
                quaternionfc_proxy_ctor = quaternionfc_proxy.getConstructor(new Class[] { Quaternionfc.class });
            }
            return (Quaternionfc) quaternionfc_proxy_ctor.newInstance(new Object[] { delegate });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static Class proxy(final Class clazz) {
        if (!clazz.isInterface())
            throw new AssertionError("Can only proxy interfaces");
        final String targetInternalName = clazz.getName().replace('.', '/');
        final String targetDesc = "L" + targetInternalName + ";";
        final String proxyInternalName = targetInternalName + "$Proxy";
        try {
            ClassReader cr = new ClassReader(targetInternalName);
            ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
            cr.accept(new ClassVisitor(ASM5, cw) {
                public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
                    cv.visit(version, ACC_SYNTHETIC, proxyInternalName, signature, "java/lang/Object", new String[] { targetInternalName });
                    cv.visitField(ACC_PRIVATE | ACC_SYNTHETIC | ACC_FINAL, "$delegate", targetDesc, null, null);
                    MethodVisitor mv = cv.visitMethod(ACC_PUBLIC, "<init>", "(" + targetDesc + ")V", null, null);
                    mv.visitCode();
                    mv.visitVarInsn(ALOAD, 0);
                    mv.visitInsn(DUP);
                    mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
                    mv.visitVarInsn(ALOAD, 1);
                    mv.visitFieldInsn(PUTFIELD, proxyInternalName, "$delegate", targetDesc);
                    mv.visitInsn(RETURN);
                    mv.visitMaxs(-1, -1);
                    mv.visitEnd();
                }

                public void visitSource(String source, String debug) {
                    cv.visitSource(source + "$Debug", debug);
                }

                public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
                    MethodVisitor mv = cv.visitMethod(ACC_PUBLIC, name, desc, signature, exceptions);
                    mv.visitCode();
                    mv.visitVarInsn(ALOAD, 0);
                    mv.visitFieldInsn(GETFIELD, proxyInternalName, "$delegate", targetDesc);
                    Type[] args = Type.getArgumentTypes(desc);
                    Type ret = Type.getReturnType(desc);
                    int argIndex = 1;
                    for (int i = 0; i < args.length; i++) {
                        Type arg = args[i];
                        mv.visitVarInsn(arg.getOpcode(ILOAD), argIndex);
                        argIndex += arg.getSize();
                    }
                    mv.visitMethodInsn(INVOKEINTERFACE, targetInternalName, name, desc, true);
                    mv.visitInsn(ret.getOpcode(IRETURN));
                    mv.visitMaxs(-1, -1);
                    mv.visitEnd();
                    return null;
                }
            }, ClassReader.SKIP_DEBUG | ClassReader.SKIP_CODE | ClassReader.SKIP_FRAMES);
            byte[] arr = cw.toByteArray();
            return ClassDefineUtils.defineClass(clazz.getClassLoader(), clazz, proxyInternalName, arr);
        } catch (Exception e) {
            throw new RuntimeException("Could not proxy " + clazz, e);
        }
    }

}
