/*
 * The MIT License
 *
 * Copyright (c) 2026 OblivRuinDev, Kai Burjack
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
package org.joml;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

class MemUtil0 {
    static void getTransposed(Matrix4f m, int offset, FloatBuffer src) {
        m._m00(src.get(offset))
                ._m10(src.get(offset+1))
                ._m20(src.get(offset+2))
                ._m30(src.get(offset+3))
                ._m01(src.get(offset+4))
                ._m11(src.get(offset+5))
                ._m21(src.get(offset+6))
                ._m31(src.get(offset+7))
                ._m02(src.get(offset+8))
                ._m12(src.get(offset+9))
                ._m22(src.get(offset+10))
                ._m32(src.get(offset+11))
                ._m03(src.get(offset+12))
                ._m13(src.get(offset+13))
                ._m23(src.get(offset+14))
                ._m33(src.get(offset+15));
    }

    static void getTransposed(Matrix4f m, int offset, ByteBuffer src) {
        m._m00(src.getFloat(offset))
                ._m10(src.getFloat(offset+4))
                ._m20(src.getFloat(offset+8))
                ._m30(src.getFloat(offset+12))
                ._m01(src.getFloat(offset+16))
                ._m11(src.getFloat(offset+20))
                ._m21(src.getFloat(offset+24))
                ._m31(src.getFloat(offset+28))
                ._m02(src.getFloat(offset+32))
                ._m12(src.getFloat(offset+36))
                ._m22(src.getFloat(offset+40))
                ._m32(src.getFloat(offset+44))
                ._m03(src.getFloat(offset+48))
                ._m13(src.getFloat(offset+52))
                ._m23(src.getFloat(offset+56))
                ._m33(src.getFloat(offset+60));
    }
}
