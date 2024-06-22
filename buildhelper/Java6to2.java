/*
 * The MIT License
 *
 * Copyright (c) 2017-2024 JOML
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Handle;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

/**
 * Transforms the compiled Java 1.6 classes to be compatible with Java 1.2.
 * <p>
 * JOML uses no Java 1.6 specific classes or language features. However, two things need to be transformed:
 * <ul>
 * <li>string concatenation uses java.lang.StringBuilder in Java 1.6. We replace this with java.lang.StringBuffer
 * <li>loading class literals (as done by the MemUtilUnsafe class) uses a LDC opcode variant in Java 1.6 which is unsupported in earlier Java versions. We change that to the same
 * replacement code emitted by JDK8 when using target 1.2
 * <li>since JEP 280 when targeting Java 9 the javac compiler uses invokedynamic for string concatenations. we will rewrite those to use java.lang.StringBuffer again
 * </ul>
 * 
 * @author Kai Burjack
 */
public class Java6to2 implements Opcodes {

    private static byte[] transform(InputStream classfile) throws IOException {
        ClassReader cr = new ClassReader(classfile);
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        ClassVisitor cv = new ClassVisitor(ASM9, cw) {
            String internalName;
            boolean classLookupMethodGenerated;
            Map classToSyntheticField = new HashMap();
            int stringConcatMethod = 0;

            public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
                /* Change class file version to 1.2 */
                super.cv.visit(V1_2, access, name, signature, superName, interfaces);
                this.internalName = name;
            }

            /**
             * Generates the synthetic "class$" method, used to lookup classes via Class.forName(). This uses the exact same code as does JDK8 javac for target 1.2.
             */
            void generateSyntheticClassLookupMethod() {
                MethodVisitor mv = super.cv.visitMethod(ACC_PRIVATE | ACC_STATIC | ACC_SYNTHETIC, "class$", "(Ljava/lang/String;)Ljava/lang/Class;", null, null);
                {
                    Label start = new Label();
                    Label end = new Label();
                    Label handler = new Label();
                    mv.visitTryCatchBlock(start, end, handler, "java/lang/ClassNotFoundException");
                    mv.visitLabel(start);
                    mv.visitVarInsn(ALOAD, 0);
                    mv.visitMethodInsn(INVOKESTATIC, "java/lang/Class", "forName", "(Ljava/lang/String;)Ljava/lang/Class;", false);
                    mv.visitLabel(end);
                    mv.visitInsn(ARETURN);
                    mv.visitLabel(handler);
                    mv.visitVarInsn(ASTORE, 1);
                    mv.visitTypeInsn(NEW, "java/lang/NoClassDefFoundError");
                    mv.visitInsn(DUP);
                    mv.visitMethodInsn(INVOKESPECIAL, "java/lang/NoClassDefFoundError", "<init>", "()V", false);
                    mv.visitVarInsn(ALOAD, 1);
                    mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/NoClassDefFoundError", "initCause", "(Ljava/lang/Throwable;)Ljava/lang/Throwable;", false);
                    mv.visitInsn(ATHROW);
                }
                mv.visitMaxs(2, 2);
                mv.visitEnd();
            }

            public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
                MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
                final ClassVisitor cv = this.cv;
                return new MethodVisitor(ASM9, mv) {
                    /**
                     * Intercepts class instantiations to see whether they instantiate a StringBuilder. Those instructions were generated by javac for string concatenations. But
                     * StringBuilder is not available on JRE1.2, so we just replace it with StringBuffer.
                     */
                    public void visitTypeInsn(int opcode, String type) {
                        if (opcode == NEW && "java/lang/StringBuilder".equals(type)) {
                            super.mv.visitTypeInsn(opcode, "java/lang/StringBuffer");
                        } else {
                            super.mv.visitTypeInsn(opcode, type);
                        }
                    }

                    /**
                     * Intercepts method invocations to see whether they do something with StringBuilder. Those instructions were generated by javac for string concatenations. But
                     * StringBuilder is not available on JRE1.2, so we just replace it with StringBuffer.
                     */
                    public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
                        if ("java/lang/StringBuilder".equals(owner)) {
                            super.mv.visitMethodInsn(opcode, "java/lang/StringBuffer", name, desc.replace("java/lang/StringBuilder", "java/lang/StringBuffer"), itf);
                        } else {
                            super.mv.visitMethodInsn(opcode, owner, name, desc, itf);
                        }
                    }

                    public void visitInvokeDynamicInsn(String name, String descriptor, Handle bootstrapMethodHandle,
                            Object[] bootstrapMethodArguments) {
                        if ("makeConcatWithConstants".equals(name)) {
                            rewriteStringConcatenation(descriptor, (String) bootstrapMethodArguments[0]);
                        } else {
                            throw new AssertionError("Unknown invokedynamic method: " + name);
                        }
                    }

                    private void rewriteStringConcatenation(String descriptor, String recipe) {
                        // replace this with a generated method which performs the string concatenation using StringBuffer
                        // the arguments are already on the stack so we just need to synthesize a new method
                        String methodName = "$$strc$$" + stringConcatMethod++;
                        MethodVisitor mv = cv.visitMethod(ACC_SYNTHETIC | ACC_PRIVATE | ACC_STATIC, methodName, descriptor, null, null);
                        mv.visitCode();
                        mv.visitTypeInsn(NEW, "java/lang/StringBuffer");
                        mv.visitInsn(DUP);
                        mv.visitMethodInsn(INVOKESPECIAL, "java/lang/StringBuffer", "<init>", "()V", false);
                        Type[] types = Type.getArgumentTypes(descriptor);
                        int varIndex = 0, argIndex = 0;
                        StringBuffer s = new StringBuffer();
                        for (int i = 0; i < recipe.length(); i++) {
                            int cp = recipe.codePointAt(i);
                            if (cp == '\1') {
                                if (s.length() > 0) {
                                    mv.visitLdcInsn(s.toString());
                                    s.setLength(0);
                                    mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuffer", "append", "(Ljava/lang/String;)Ljava/lang/StringBuffer;", false);                                    
                                }
                                Type t = types[argIndex];
                                mv.visitVarInsn(t.getOpcode(ILOAD), varIndex);
                                if (t.getSort() == Type.OBJECT) {
                                    if ("Ljava/lang/String;".equals(t.getDescriptor())) {
                                        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuffer", "append", "(Ljava/lang/String;)Ljava/lang/StringBuffer;", false);
                                    } else {
                                        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuffer", "append", "(Ljava/lang/Object;)Ljava/lang/StringBuffer;", false);
                                    }
                                } else {
                                    mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuffer", "append", "(" + t.getDescriptor() + ")Ljava/lang/StringBuffer;", false);
                                }
                                varIndex += t.getSize();
                                argIndex++;
                            } else {
                                s.appendCodePoint(cp);
                            }
                        }
                        if (s.length() > 0) {
                            mv.visitLdcInsn(s.toString());
                            s.setLength(0);
                            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuffer", "append", "(Ljava/lang/String;)Ljava/lang/StringBuffer;", false);                                    
                        }
                        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuffer", "toString", "()Ljava/lang/String;", false);
                        mv.visitInsn(ARETURN);
                        mv.visitMaxs(-1, -1);
                        mv.visitEnd();
                        super.mv.visitMethodInsn(INVOKESTATIC, internalName, methodName, descriptor, false);
                    }

                    /**
                     * Intercepts LDC instructions and check whether they are used to load a class. This is not supported on Java 1.2, so we convert it to the same code used by the
                     * JDK8 javac:
                     * <ul>
                     * <li>create synthetic fields holding the resolved class objects
                     * <li>create a synthetic method called "class$" which does Class.forName
                     * </ul>
                     */
                    public void visitLdcInsn(Object cst) {
                        if (cst instanceof Type) {
                            Type t = (Type) cst;
                            if (!classLookupMethodGenerated) {
                                /* Emit the synthetic "class$" method, used to lookup classes via Class.forName() */
                                generateSyntheticClassLookupMethod();
                                classLookupMethodGenerated = true;
                            }
                            String syntheticField = (String) classToSyntheticField.get(t.getInternalName());
                            if (syntheticField == null) {
                                syntheticField = "class$" + classToSyntheticField.size();
                                /* Generate a synthetic field holding the resolved Class object */
                                cv.visitField(ACC_PRIVATE | ACC_STATIC | ACC_SYNTHETIC, syntheticField, "Ljava/lang/Class;", null, null);
                                classToSyntheticField.put(t.getInternalName(), syntheticField);
                            }
                            super.mv.visitFieldInsn(GETSTATIC, internalName, syntheticField, "Ljava/lang/Class;");
                            Label nonNull = new Label();
                            super.mv.visitJumpInsn(IFNONNULL, nonNull);
                            super.mv.visitLdcInsn(t.getInternalName().replace('/', '.'));
                            super.mv.visitMethodInsn(INVOKESTATIC, internalName, "class$", "(Ljava/lang/String;)Ljava/lang/Class;", false);
                            super.mv.visitInsn(DUP);
                            super.mv.visitFieldInsn(PUTSTATIC, internalName, syntheticField, "Ljava/lang/Class;");
                            Label cnt = new Label();
                            super.mv.visitJumpInsn(GOTO, cnt);
                            super.mv.visitLabel(nonNull);
                            super.mv.visitFieldInsn(GETSTATIC, internalName, syntheticField, "Ljava/lang/Class;");
                            super.mv.visitLabel(cnt);
                        } else {
                            super.mv.visitLdcInsn(cst);
                        }
                    }
                };
            }
        };
        cr.accept(cv, ClassReader.SKIP_FRAMES); // <- Frames are not used in Java 1.2, so skip them
        return cw.toByteArray();
    }

    private static void walk(File root) throws IOException {
        File[] list = root.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                File f = new File(dir, name);
                return f.isDirectory() || name.endsWith(".class") && !name.equals("module-info.class");
            }
        });
        if (list == null)
            return;
        for (int i = 0; i < list.length; i++) {
            File f = list[i];
            if (f.isDirectory()) {
                walk(f);
            } else {
                FileInputStream fis = new FileInputStream(f);
                byte[] transformed = transform(fis);
                fis.close();
                FileOutputStream fos = new FileOutputStream(f);
                fos.write(transformed);
                fos.close();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        walk(new File(args[0]));
    }

}
