/*
 * The MIT License
 *
 * Copyright (c) 2017-2021 JOML
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
import java.io.FileOutputStream;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.ModuleVisitor;
import org.objectweb.asm.Opcodes;

/**
 * Generate a Java 9 module-info.class file to the target/classes directory.
 * 
 * @author Kai Burjack
 */
public class ModuleInfoGenerator implements Opcodes {
    public static void main(String[] args) throws Exception {
        File dest = new File(args[0]);
        ClassWriter cw = new ClassWriter(0);
        cw.visit(V9, Opcodes.ACC_MODULE, "module-info", null, null, null);
        ModuleVisitor mv = cw.visitModule("org.joml", 0, args[1]);
        mv.visitRequire("java.base", ACC_MANDATED, "9");
        mv.visitRequire("jdk.unsupported", ACC_STATIC_PHASE, null);
        mv.visitRequire("jdk.incubator.vector", ACC_STATIC_PHASE, null);
        mv.visitExport("org/joml", 0, (String[]) null);
        mv.visitExport("org/joml/sampling", 0, (String[]) null);
        mv.visitExport("org/joml/experimental", 0, (String[]) null);
        mv.visitEnd();
        cw.visitEnd();
        FileOutputStream fos = new FileOutputStream(new File(dest, "module-info.class"));
        fos.write(cw.toByteArray());
        fos.close();
    }
}
