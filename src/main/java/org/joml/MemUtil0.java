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
