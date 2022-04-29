/*
 * The MIT License
 *
 * Copyright (c) 2020-2022 JOML
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
import java.io.*;
import java.util.List;

import javassist.bytecode.*;

public class InlineAdvisor {
    private static final int FREQ_INLINE_THRESHOLD = Integer.valueOf(System.getProperty("InlineAdvisor.FreqInlineThreshold")).intValue();

    public static void main(String[] args) throws IOException {
        walk(new File(args[0]));
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
                check(f);
            }
        }
    }

    private static void check(File file) throws IOException {
        ClassFile cf = new ClassFile(new DataInputStream(new FileInputStream(file)));
        List methods = cf.getMethods();
        for (int i = 0; i < methods.size(); i++) {
            MethodInfo mi = (MethodInfo) methods.get(i);
            CodeAttribute ca = mi.getCodeAttribute();
            if (ca == null)
                continue;
            int len = ca.getCode().length;
            if (len > FREQ_INLINE_THRESHOLD)
                System.err.println("Method will never be inlined(" + len + "): " + file.getName() + "." + mi.getName() + mi.getDescriptor());
        }
    }
}
