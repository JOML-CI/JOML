/*
 * (C) Copyright 2017 JOML

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

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import org.joml.api.matrix.IMatrix3x2f;
import org.joml.api.matrix.Matrix3x2fc;
import org.joml.api.vector.IVector2f;
import org.joml.api.vector.IVector3f;
import org.joml.api.vector.Vector2fc;
import org.joml.api.vector.Vector3fc;
//#ifdef __HAS_NIO__
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
//#endif
import java.text.DecimalFormat;
import java.text.NumberFormat;

//#ifdef __GWT__
import com.google.gwt.typedarrays.shared.Float32Array;
//#endif

/**
 * Contains the definition of a 3x2 matrix of floats, and associated functions to transform
 * it. The matrix is column-major to match OpenGL's interpretation, and it looks like this:
 * <p>
 *      m00  m10  m20<br>
 *      m01  m11  m21<br>
 * 
 * @author Kai Burjack
 */
public class Matrix3x2f extends Matrix3x2fc implements Externalizable {

    private static final long serialVersionUID = 1L;

    public float m00, m01;
    public float m10, m11;
    public float m20, m21;

    /**
     * Create a new {@link Matrix3x2f} and set it to {@link #identity() identity}.
     */
    public Matrix3x2f() {
        this.m00 = 1.0f;
        this.m11 = 1.0f;
    }

    /**
     * Create a new {@link Matrix3x2f} and make it a copy of the given matrix.
     * 
     * @param mat
     *          the {@link Matrix3x2fc} to copy the values from
     */
    public Matrix3x2f(Matrix3x2fc mat) {
        if (mat instanceof Matrix3x2f) {
            MemUtil.INSTANCE.copy((Matrix3x2f) mat, this);
        } else {
            setMatrix3x2fc(mat);
        }
    }

    /**
     * Create a new 3x2 matrix using the supplied float values. The order of the parameter is column-major, 
     * so the first two parameters specify the two elements of the first column.
     * 
     * @param m00
     *          the value of m00
     * @param m01
     *          the value of m01
     * @param m10
     *          the value of m10
     * @param m11
     *          the value of m11
     * @param m20
     *          the value of m20
     * @param m21
     *          the value of m21
     */
    public Matrix3x2f(float m00, float m01,
                      float m10, float m11,
                      float m20, float m21) {
        this.m00 = m00;
        this.m01 = m01;
        this.m10 = m10;
        this.m11 = m11;
        this.m20 = m20;
        this.m21 = m21;
    }

//#ifdef __HAS_NIO__
    /**
     * Create a new {@link Matrix3x2f} by reading its 6 float components from the given {@link FloatBuffer}
     * at the buffer's current position.
     * <p>
     * That FloatBuffer is expected to hold the values in column-major order.
     * <p>
     * The buffer's position will not be changed by this method.
     * 
     * @param buffer
     *          the {@link FloatBuffer} to read the matrix values from
     */
    public Matrix3x2f(FloatBuffer buffer) {
        MemUtil.INSTANCE.get(this, buffer.position(), buffer);
    }
//#endif

    @Override
    public float m00() {
        return m00;
    }
    @Override
    public float m01() {
        return m01;
    }
    @Override
    public float m10() {
        return m10;
    }
    @Override
    public float m11() {
        return m11;
    }
    @Override
    public float m20() {
        return m20;
    }
    @Override
    public float m21() {
        return m21;
    }

    @Override
    public Matrix3x2fc m00(float m00) {
        this.m00 = m00;
        return this;
    }

    @Override
    public Matrix3x2fc m01(float m01) {
        this.m01 = m01;
        return this;
    }

    @Override
    public Matrix3x2fc m10(float m10) {
        this.m10 = m10;
        return this;
    }

    @Override
    public Matrix3x2fc m11(float m11) {
        this.m11 = m11;
        return this;
    }

    @Override
    public Matrix3x2fc m20(float m20) {
        this.m20 = m20;
        return this;
    }

    @Override
    public Matrix3x2fc m21(float m21) {
        this.m21 = m21;
        return this;
    }

    @Override
    public Matrix3x2fc set(IMatrix3x2f m) {
        if (m instanceof Matrix3x2f) {
            MemUtil.INSTANCE.copy((Matrix3x2f) m, this);
        } else {
            setMatrix3x2fc(m);
        }
        return this;
    }

    private void setMatrix3x2fc(IMatrix3x2f mat) {
        m00 = mat.m00();
        m01 = mat.m01();
        m10 = mat.m10();
        m11 = mat.m11();
        m20 = mat.m20();
        m21 = mat.m21();
    }

    @Override
    public Matrix3x2fc mul(IMatrix3x2f right) {
        return mul(right, this);
    }

    @Override
    public Matrix3x2fc mul(IMatrix3x2f right, Matrix3x2fc dest) {
        float nm00 = m00 * right.m00() + m10 * right.m01();
        float nm01 = m01 * right.m00() + m11 * right.m01();
        float nm10 = m00 * right.m10() + m10 * right.m11();
        float nm11 = m01 * right.m10() + m11 * right.m11();
        float nm20 = m00 * right.m20() + m10 * right.m21() + m20;
        float nm21 = m01 * right.m20() + m11 * right.m21() + m21;
        dest.m00(nm00);
        dest.m01(nm01);
        dest.m10(nm10);
        dest.m11(nm11);
        dest.m20(nm20);
        dest.m21(nm21);
        return dest;
    }

    @Override
    public Matrix3x2fc mulLocal(IMatrix3x2f left) {
       return mulLocal(left, this);
    }

    @Override
    public Matrix3x2fc mulLocal(IMatrix3x2f left, Matrix3x2fc dest) {
        float nm00 = left.m00() * m00 + left.m10() * m01;
        float nm01 = left.m01() * m00 + left.m11() * m01;
        float nm10 = left.m00() * m10 + left.m10() * m11;
        float nm11 = left.m01() * m10 + left.m11() * m11;
        float nm20 = left.m00() * m20 + left.m10() * m21 + left.m20();
        float nm21 = left.m01() * m20 + left.m11() * m21 + left.m21();
        dest.m00(nm00);
        dest.m01(nm01);
        dest.m10(nm10);
        dest.m11(nm11);
        dest.m20(nm20);
        dest.m21(nm21);
        return dest;
    }

    @Override
    public Matrix3x2fc set(float m00, float m01,
                          float m10, float m11, 
                          float m20, float m21) {
        this.m00 = m00;
        this.m01 = m01;
        this.m10 = m10;
        this.m11 = m11;
        this.m20 = m20;
        this.m21 = m21;
        return this;
    }

    @Override
    public Matrix3x2fc set(float m[]) {
        MemUtil.INSTANCE.copy(m, 0, this);
        return this;
    }

    @Override
    public float determinant() {
        return m00 * m11 - m01 * m10;
    }

    @Override
    public Matrix3x2fc invert() {
        return invert(this);
    }

    @Override
    public Matrix3x2fc invert(Matrix3x2fc dest) {
        // client must make sure that matrix is invertible
        float s = 1.0f / (m00 * m11 - m01 * m10);
        float nm00 =  m11 * s;
        float nm01 = -m01 * s;
        float nm10 = -m10 * s;
        float nm11 =  m00 * s;
        float nm20 = (m10 * m21 - m20 * m11) * s;
        float nm21 = (m20 * m01 - m00 * m21) * s;
        dest.m00(nm00);
        dest.m01(nm01);
        dest.m10(nm10);
        dest.m11(nm11);
        dest.m20(nm20);
        dest.m21(nm21);
        return dest;
    }

    @Override
    public Matrix3x2fc translation(float x, float y) {
        m00 = 1.0f;
        m01 = 0.0f;
        m10 = 0.0f;
        m11 = 1.0f;
        m20 = x;
        m21 = y;
        return this;
    }

    @Override
    public Matrix3x2fc translation(IVector2f offset) {
        return translation(offset.x(), offset.y());
    }

    @Override
    public Matrix3x2fc setTranslation(float x, float y) {
        m20 = x;
        m21 = y;
        return this;
    }

    @Override
    public Matrix3x2fc setTranslation(Vector2fc offset) {
        return setTranslation(offset.x(), offset.y());
    }

    @Override
    public Matrix3x2fc translate(float x, float y, Matrix3x2fc dest) {
        float rm20 = x;
        float rm21 = y;
        dest.m20(m00 * rm20 + m10 * rm21 + m20);
        dest.m21(m01 * rm20 + m11 * rm21 + m21);
        dest.m00(m00);
        dest.m01(m01);
        dest.m10(m10);
        dest.m11(m11);
        return dest;
    }

    @Override
    public Matrix3x2fc translate(float x, float y) {
        return translate(x, y, this);
    }

    @Override
    public Matrix3x2fc translate(IVector2f offset, Matrix3x2fc dest) {
        return translate(offset.x(), offset.y(), dest);
    }

    @Override
    public Matrix3x2fc translate(IVector2f offset) {
        return translate(offset.x(), offset.y(), this);
    }

    @Override
    public Matrix3x2fc translateLocal(IVector2f offset) {
        return translateLocal(offset.x(), offset.y());
    }

    @Override
    public Matrix3x2fc translateLocal(IVector2f offset, Matrix3x2fc dest) {
        return translateLocal(offset.x(), offset.y(), dest);
    }

    @Override
    public Matrix3x2fc translateLocal(float x, float y, Matrix3x2fc dest) {
        dest.m00(m00);
        dest.m01(m01);
        dest.m10(m10);
        dest.m11(m11);
        dest.m20(m20 + x);
        dest.m21(m21 + y);
        return dest;
    }

    @Override
    public Matrix3x2fc translateLocal(float x, float y) {
        return translateLocal(x, y, this);
    }

    @Override
    public String toString() {
        DecimalFormat formatter = new DecimalFormat(" 0.000E0;-");
        String str = toString(formatter);
        StringBuffer res = new StringBuffer();
        int eIndex = Integer.MIN_VALUE;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c == 'E') {
                eIndex = i;
            } else if (c == ' ' && eIndex == i - 1) {
                // workaround Java 1.4 DecimalFormat bug
                res.append('+');
                continue;
            } else if (Character.isDigit(c) && eIndex == i - 1) {
                res.append('+');
            }
            res.append(c);
        }
        return res.toString();
    }

    @Override
    public String toString(NumberFormat formatter) {
        return formatter.format(m00) + " " + formatter.format(m10) + " " + formatter.format(m20) + "\n"
             + formatter.format(m01) + " " + formatter.format(m11) + " " + formatter.format(m21) + "\n";
    }

    @Override
    public Matrix3x2fc get(Matrix3x2fc dest) {
        return dest.set(this);
    }

    //#ifdef __GWT__
    @Override
    public Float32Array get(Float32Array buffer) {
          buffer.set(0,  m00);
          buffer.set(1,  m01);
          buffer.set(2,  m10);
          buffer.set(3,  m11);
          buffer.set(4,  m20);
          buffer.set(5,  m21);
          return buffer;
      }

    @Override
    public Float32Array get(int index, Float32Array buffer) {
          buffer.set(index,    m00);
          buffer.set(index+1,  m01);
          buffer.set(index+2,  m10);
          buffer.set(index+3,  m11);
          buffer.set(index+4,  m20);
          buffer.set(index+5,  m21);
          return buffer;
      }
    //#endif

    //#ifdef __HAS_NIO__
    @Override
    public FloatBuffer get(FloatBuffer buffer) {
        return get(buffer.position(), buffer);
    }

    @Override
    public FloatBuffer get(int index, FloatBuffer buffer) {
        MemUtil.INSTANCE.put(this, index, buffer);
        return buffer;
    }

    @Override
    public ByteBuffer get(ByteBuffer buffer) {
        return get(buffer.position(), buffer);
    }

    @Override
    public ByteBuffer get(int index, ByteBuffer buffer) {
        MemUtil.INSTANCE.put(this, index, buffer);
        return buffer;
    }

    @Override
    public FloatBuffer get4x4(FloatBuffer buffer) {
        MemUtil.INSTANCE.put4x4(this, 0, buffer);
        return buffer;
    }

    @Override
    public FloatBuffer get4x4(int index, FloatBuffer buffer) {
        MemUtil.INSTANCE.put4x4(this, index, buffer);
        return buffer;
    }

    @Override
    public ByteBuffer get4x4(ByteBuffer buffer) {
        MemUtil.INSTANCE.put4x4(this, 0, buffer);
        return buffer;
    }

    @Override
    public ByteBuffer get4x4(int index, ByteBuffer buffer) {
        MemUtil.INSTANCE.put4x4(this, index, buffer);
        return buffer;
    }
//#endif

    @Override
    public float[] get(float[] arr, int offset) {
        MemUtil.INSTANCE.copy(this, arr, offset);
        return arr;
    }

    @Override
    public float[] get(float[] arr) {
        return get(arr, 0);
    }

    @Override
    public float[] get4x4(float[] arr, int offset) {
        MemUtil.INSTANCE.copy4x4(this, arr, offset);
        return arr;
    }

    @Override
    public float[] get4x4(float[] arr) {
        return get4x4(arr, 0);
    }

//#ifdef __HAS_NIO__
    @Override
    public Matrix3x2fc set(FloatBuffer buffer) {
        int pos = buffer.position();
        MemUtil.INSTANCE.get(this, pos, buffer);
        return this;
    }

    @Override
    public Matrix3x2fc set(ByteBuffer buffer) {
        int pos = buffer.position();
        MemUtil.INSTANCE.get(this, pos, buffer);
        return this;
    }
//#endif

    @Override
    public Matrix3x2fc zero() {
        MemUtil.INSTANCE.zero(this);
        return this;
    }

    @Override
    public Matrix3x2fc identity() {
        MemUtil.INSTANCE.identity(this);
        return this;
    }

    @Override
    public Matrix3x2fc scale(float x, float y, Matrix3x2fc dest) {
        dest.m00(m00 * x);
        dest.m01(m01 * x);
        dest.m10(m10 * y);
        dest.m11(m11 * y);
        dest.m20(m20);
        dest.m21(m21);
        return dest;
    }

    @Override
    public Matrix3x2fc scale(float x, float y) {
        return scale(x, y, this);
    }

    @Override
    public Matrix3x2fc scale(float xy, Matrix3x2fc dest) {
        return scale(xy, xy, dest);
    }

    @Override
    public Matrix3x2fc scale(float xy) {
        return scale(xy, xy);
    }

    @Override
    public Matrix3x2fc scaleLocal(float x, float y, Matrix3x2fc dest) {
        dest.m00(x * m00);
        dest.m01(y * m01);
        dest.m10(x * m10);
        dest.m11(y * m11);
        dest.m20(x * m20);
        dest.m21(y * m21);
        return dest;
    }

    @Override
    public Matrix3x2fc scaleLocal(float x, float y) {
        return scaleLocal(x, y, this);
    }

    @Override
    public Matrix3x2fc scaleLocal(float xy, Matrix3x2fc dest) {
        return scaleLocal(xy, xy, dest);
    }

    @Override
    public Matrix3x2fc scaleLocal(float xy) {
        return scaleLocal(xy, xy, this);
    }

    @Override
    public Matrix3x2fc scaleAround(float sx, float sy, float ox, float oy, Matrix3x2fc dest) {
        float nm20 = m00 * ox + m10 * oy + m20;
        float nm21 = m01 * ox + m11 * oy + m21;
        dest.m00(m00 * sx);
        dest.m01(m01 * sx);
        dest.m10(m10 * sy);
        dest.m11(m11 * sy);
        dest.m20(-m00 * ox - m10 * oy + nm20);
        dest.m21(-m01 * ox - m11 * oy + nm21);
        return dest;
    }

    @Override
    public Matrix3x2fc scaleAround(float sx, float sy, float ox, float oy) {
        return scaleAround(sx, sy, ox, oy, this);
    }

    @Override
    public Matrix3x2fc scaleAround(float factor, float ox, float oy, Matrix3x2fc dest) {
        return scaleAround(factor, factor, ox, oy, this);
    }

    @Override
    public Matrix3x2fc scaleAround(float factor, float ox, float oy) {
        return scaleAround(factor, factor, ox, oy, this);
    }

    @Override
    public Matrix3x2fc scaleAroundLocal(float sx, float sy, float ox, float oy, Matrix3x2fc dest) {
        dest.m00(sx * m00);
        dest.m01(sy * m01);
        dest.m10(sx * m10);
        dest.m11(sy * m11);
        dest.m20(sx * m20 - sx * ox + ox);
        dest.m21(sy * m21 - sy * oy + oy);
        return dest;
    }

    @Override
    public Matrix3x2fc scaleAroundLocal(float factor, float ox, float oy, Matrix3x2fc dest) {
        return scaleAroundLocal(factor, factor, ox, oy, dest);
    }

    @Override
    public Matrix3x2fc scaleAroundLocal(float sx, float sy, float sz, float ox, float oy, float oz) {
        return scaleAroundLocal(sx, sy, ox, oy, this);
    }

    @Override
    public Matrix3x2fc scaleAroundLocal(float factor, float ox, float oy) {
        return scaleAroundLocal(factor, factor, ox, oy, this);
    }

    @Override
    public Matrix3x2fc scaling(float factor) {
        return scaling(factor, factor);
    }

    @Override
    public Matrix3x2fc scaling(float x, float y) {
        m00 = x;
        m01 = 0.0f;
        m10 = 0.0f;
        m11 = y;
        m20 = 0.0f;
        m21 = 0.0f;
        return this;
    }

    @Override
    public Matrix3x2fc rotation(float angle) {
        float cos = (float) Math.cos(angle);
        float sin = (float) Math.sin(angle);
        m00 = cos;
        m10 = -sin;
        m20 = 0.0f;
        m01 = sin;
        m11 = cos;
        m21 = 0.0f;
        return this;
    }

    @Override
    public Vector3fc transform(Vector3fc v) {
        return v.mul(this);
    }

    @Override
    public Vector3fc transform(IVector3f v, Vector3fc dest) {
        return null;
    }

    @Override
    public Vector3fc transform(Vector3fc v, Vector3fc dest) {
        return v.mul(this, dest);
    }

    @Override
    public Vector3fc transform(float x, float y, float z, Vector3fc dest) {
       return dest.set(m00 * x + m10 * y + m20 * z, m01 * x + m11 * y + m21 * z, z);
    }

    @Override
    public Vector2fc transformPosition(Vector2fc v) {
        v.set(m00 * v.x() + m10 * v.y() + m20,
              m01 * v.x() + m11 * v.y() + m21);
        return v;
    }

    @Override
    public Vector2fc transformPosition(IVector2f v, Vector2fc dest) {
        dest.set(m00 * v.x() + m10 * v.y() + m20,
                 m01 * v.x() + m11 * v.y() + m21);
        return dest;
    }

    @Override
    public Vector2fc transformPosition(float x, float y, Vector2fc dest) {
        return dest.set(m00 * x + m10 * y + m20, m01 * x + m11 * y + m21);
    }

    @Override
    public Vector2fc transformDirection(Vector2fc v) {
        v.set(m00 * v.x() + m10 * v.y(),
              m01 * v.x() + m11 * v.y());
        return v;
    }

    @Override
    public Vector2fc transformDirection(IVector2f v, Vector2fc dest) {
        dest.set(m00 * v.x() + m10 * v.y(),
                 m01 * v.x() + m11 * v.y());
        return dest;
    }

    @Override
    public Vector2fc transformDirection(float x, float y, Vector2fc dest) {
        return dest.set(m00 * x + m10 * y, m01 * x + m11 * y);
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeFloat(m00);
        out.writeFloat(m01);
        out.writeFloat(m10);
        out.writeFloat(m11);
        out.writeFloat(m20);
        out.writeFloat(m21);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        m00 = in.readFloat();
        m01 = in.readFloat();
        m10 = in.readFloat();
        m11 = in.readFloat();
        m20 = in.readFloat();
        m21 = in.readFloat();
    }

    @Override
    public Matrix3x2fc rotate(float ang) {
        return rotate(ang, this);
    }

    @Override
    public Matrix3x2fc rotate(float ang, Matrix3x2fc dest) {
        float cos = (float) Math.cos(ang);
        float sin = (float) Math.sin(ang);
        float rm00 = cos;
        float rm01 = sin;
        float rm10 = -sin;
        float rm11 = cos;
        float nm00 = m00 * rm00 + m10 * rm01;
        float nm01 = m01 * rm00 + m11 * rm01;
        dest.m10(m00 * rm10 + m10 * rm11);
        dest.m11(m01 * rm10 + m11 * rm11);
        dest.m00(nm00);
        dest.m01(nm01);
        dest.m20(m20);
        dest.m21(m21);
        return dest;
    }

    @Override
    public Matrix3x2fc rotateLocal(float ang, Matrix3x2fc dest) {
        float sin = (float) Math.sin(ang);
        float cos = (float) Math.cosFromSin(sin, ang);
        float nm00 = cos * m00 - sin * m01;
        float nm01 = sin * m00 + cos * m01;
        float nm10 = cos * m10 - sin * m11;
        float nm11 = sin * m10 + cos * m11;
        float nm20 = cos * m20 - sin * m21;
        float nm21 = sin * m20 + cos * m21;
        dest.m00(nm00);
        dest.m01(nm01);
        dest.m10(nm10);
        dest.m11(nm11);
        dest.m20(nm20);
        dest.m21(nm21);
        return dest;
    }

    @Override
    public Matrix3x2fc rotateLocal(float ang) {
        return rotateLocal(ang, this);
    }

    @Override
    public Matrix3x2fc rotateAbout(float ang, float x, float y) {
        return rotateAbout(ang, x, y, this);
    }

    @Override
    public Matrix3x2fc rotateAbout(float ang, float x, float y, Matrix3x2fc dest) {
        float tm20 = m00 * x + m10 * y + m20;
        float tm21 = m01 * x + m11 * y + m21;
        float cos = (float) Math.cos(ang);
        float sin = (float) Math.sin(ang);
        float nm00 = m00 * cos + m10 * sin;
        float nm01 = m01 * cos + m11 * sin;
        dest.m10(m00 * -sin + m10 * cos);
        dest.m11(m01 * -sin + m11 * cos);
        dest.m00(nm00);
        dest.m01(nm01);
        dest.m20(dest.m00() * -x + dest.m10() * -y + tm20);
        dest.m21(dest.m01() * -x + dest.m11() * -y + tm21);
        return dest;
    }

    @Override
    public Matrix3x2fc rotateTo(IVector2f fromDir, IVector2f toDir, Matrix3x2fc dest) {
        float dot = fromDir.x() * toDir.x() + fromDir.y() * toDir.y();
        float det = fromDir.x() * toDir.y() - fromDir.y() * toDir.x();
        float rm00 = dot;
        float rm01 = det;
        float rm10 = -det;
        float rm11 = dot;
        float nm00 = m00 * rm00 + m10 * rm01;
        float nm01 = m01 * rm00 + m11 * rm01;
        dest.m10(m00 * rm10 + m10 * rm11);
        dest.m11(m01 * rm10 + m11 * rm11);
        dest.m00(nm00);
        dest.m01(nm01);
        dest.m20(m20);
        dest.m21(m21);
        return dest;
    }

    @Override
    public Matrix3x2fc rotateTo(IVector2f fromDir, IVector2f toDir) {
        return rotateTo(fromDir, toDir, this);
    }

    @Override
    public Matrix3x2fc view(float left, float right, float bottom, float top, Matrix3x2fc dest) {
        float rm00 = 2.0f / (right - left);
        float rm11 = 2.0f / (top - bottom);
        float rm20 = (left + right) / (left - right);
        float rm21 = (bottom + top) / (bottom - top);
        dest.m20(m00 * rm20 + m10 * rm21 + m20);
        dest.m21(m01 * rm20 + m11 * rm21 + m21);
        dest.m00(m00 * rm00);
        dest.m01(m01 * rm00);
        dest.m10(m10 * rm11);
        dest.m11(m11 * rm11);
        return dest;
    }

    @Override
    public Matrix3x2fc view(float left, float right, float bottom, float top) {
        return view(left, right, bottom, top, this);
    }

    @Override
    public Matrix3x2fc setView(float left, float right, float bottom, float top) {
        m00 = 2.0f / (right - left);
        m01 = 0.0f;
        m10 = 0.0f;
        m11 = 2.0f / (top - bottom);
        m20 = (left + right) / (left - right);
        m21 = (bottom + top) / (bottom - top);
        return this;
    }

    @Override
    public Vector2fc origin(Vector2fc origin) {
        float s = 1.0f / (m00 * m11 - m01 * m10);
        origin.set((m10 * m21 - m20 * m11) * s,
                (m20 * m01 - m00 * m21) * s);
        return origin;
    }

    @Override
    public float[] viewArea(float[] area) {
        float s = 1.0f / (m00 * m11 - m01 * m10);
        float rm00 =  m11 * s;
        float rm01 = -m01 * s;
        float rm10 = -m10 * s;
        float rm11 =  m00 * s;
        float rm20 = (m10 * m21 - m20 * m11) * s;
        float rm21 = (m20 * m01 - m00 * m21) * s;
        float nxnyX = -rm00 - rm10;
        float nxnyY = -rm01 - rm11;
        float pxnyX =  rm00 - rm10;
        float pxnyY =  rm01 - rm11;
        float nxpyX = -rm00 + rm10;
        float nxpyY = -rm01 + rm11;
        float pxpyX =  rm00 + rm10;
        float pxpyY =  rm01 + rm11;
        float minX = nxnyX;
        minX = minX < nxpyX ? minX : nxpyX;
        minX = minX < pxnyX ? minX : pxnyX;
        minX = minX < pxpyX ? minX : pxpyX;
        float minY = nxnyY;
        minY = minY < nxpyY ? minY : nxpyY;
        minY = minY < pxnyY ? minY : pxnyY;
        minY = minY < pxpyY ? minY : pxpyY;
        float maxX = nxnyX;
        maxX = maxX > nxpyX ? maxX : nxpyX;
        maxX = maxX > pxnyX ? maxX : pxnyX;
        maxX = maxX > pxpyX ? maxX : pxpyX;
        float maxY = nxnyY;
        maxY = maxY > nxpyY ? maxY : nxpyY;
        maxY = maxY > pxnyY ? maxY : pxnyY;
        maxY = maxY > pxpyY ? maxY : pxpyY;
        area[0] = minX + rm20;
        area[1] = minY + rm21;
        area[2] = maxX + rm20;
        area[3] = maxY + rm21;
        return area;
    }

    @Override
    public Vector2fc positiveX(Vector2fc dir) {
        float s = m00 * m11 - m01 * m10;
        s = 1.0f / s;
        dir.set(m11 * s, -m01 * s);
        dir.normalize();
        return dir;
    }

    @Override
    public Vector2fc normalizedPositiveX(Vector2fc dir) {
        dir.set(m11, -m01);
        return dir;
    }

    @Override
    public Vector2fc positiveY(Vector2fc dir) {
        float s = m00 * m11 - m01 * m10;
        s = 1.0f / s;
        dir.set(-m10 * s, m00 * s);
        dir.normalize();
        return dir;
    }

    @Override
    public Vector2fc normalizedPositiveY(Vector2fc dir) {
        dir.set(-m10, m00);
        return dir;
    }

    @Override
    public Vector2fc unproject(float winX, float winY, int[] viewport, Vector2fc dest) {
        float s = 1.0f / (m00 * m11 - m01 * m10);
        float im00 =  m11 * s;
        float im01 = -m01 * s;
        float im10 = -m10 * s;
        float im11 =  m00 * s;
        float im20 = (m10 * m21 - m20 * m11) * s;
        float im21 = (m20 * m01 - m00 * m21) * s;
        float ndcX = (winX-viewport[0])/viewport[2]*2.0f-1.0f;
        float ndcY = (winY-viewport[1])/viewport[3]*2.0f-1.0f;
        dest.set(im00 * ndcX + im10 * ndcY + im20, im01 * ndcX + im11 * ndcY + im21);
        return dest;
    }

    @Override
    public Vector2fc unprojectInv(float winX, float winY, int[] viewport, Vector2fc dest) {
        float ndcX = (winX-viewport[0])/viewport[2]*2.0f-1.0f;
        float ndcY = (winY-viewport[1])/viewport[3]*2.0f-1.0f;
        dest.set(m00 * ndcX + m10 * ndcY + m20, m01 * ndcX + m11 * ndcY + m21);
        return dest;
    }

    @Override
    public Matrix3x2fc shearX(float yFactor) {
        return shearX(yFactor, this);
    }

    @Override
    public Matrix3x2fc shearX(float yFactor, Matrix3x2fc dest) {
        float nm10 = m00 * yFactor + m10;
        float nm11 = m01 * yFactor + m11;
        dest.m00(m00);
        dest.m01(m01);
        dest.m10(nm10);
        dest.m11(nm11);
        dest.m20(m20);
        dest.m21(m21);
        return dest;
    }

    @Override
    public Matrix3x2fc shearY(float xFactor) {
        return shearY(xFactor, this);
    }

    @Override
    public Matrix3x2fc shearY(float xFactor, Matrix3x2fc dest) {
        float nm00 = m00 + m10 * xFactor;
        float nm01 = m01 + m11 * xFactor;
        dest.m00(nm00);
        dest.m01(nm01);
        dest.m10(m10);
        dest.m11(m11);
        dest.m20(m20);
        dest.m21(m21);
        return dest;
    }

    @Override
    public Matrix3x2fc span(Vector2fc corner, Vector2fc xDir, Vector2fc yDir) {
        float s = 1.0f / (m00 * m11 - m01 * m10);
        float nm00 =  m11 * s, nm01 = -m01 * s, nm10 = -m10 * s, nm11 =  m00 * s;
        corner.set(-nm00 - nm10 + (m10 * m21 - m20 * m11) * s,
                -nm01 - nm11 + (m20 * m01 - m00 * m21) * s);
        xDir.set(2.0f * nm00, 2.0f * nm01);
        yDir.set(2.0f * nm10, 2.0f * nm11);
        return this;
    }

    @Override
    public boolean testPoint(float x, float y) {
        float nxX = +m00, nxY = +m10, nxW = 1.0f + m20;
        float pxX = -m00, pxY = -m10, pxW = 1.0f - m20;
        float nyX = +m01, nyY = +m11, nyW = 1.0f + m21;
        float pyX = -m01, pyY = -m11, pyW = 1.0f - m21;
        return nxX * x + nxY * y + nxW >= 0 && pxX * x + pxY * y + pxW >= 0 &&
               nyX * x + nyY * y + nyW >= 0 && pyX * x + pyY * y + pyW >= 0;
    }

    @Override
    public boolean testCircle(float x, float y, float r) {
        float invl;
        float nxX = +m00, nxY = +m10, nxW = 1.0f + m20;
        invl = (float) (1.0 / Math.sqrt(nxX * nxX + nxY * nxY));
        nxX *= invl; nxY *= invl; nxW *= invl;
        float pxX = -m00, pxY = -m10, pxW = 1.0f - m20;
        invl = (float) (1.0 / Math.sqrt(pxX * pxX + pxY * pxY));
        pxX *= invl; pxY *= invl; pxW *= invl;
        float nyX = +m01, nyY = +m11, nyW = 1.0f + m21;
        invl = (float) (1.0 / Math.sqrt(nyX * nyX + nyY * nyY));
        nyX *= invl; nyY *= invl; nyW *= invl;
        float pyX = -m01, pyY = -m11, pyW = 1.0f - m21;
        invl = (float) (1.0 / Math.sqrt(pyX * pyX + pyY * pyY));
        pyX *= invl; pyY *= invl; pyW *= invl;
        return nxX * x + nxY * y + nxW >= -r && pxX * x + pxY * y + pxW >= -r &&
               nyX * x + nyY * y + nyW >= -r && pyX * x + pyY * y + pyW >= -r;
    }

    @Override
    public boolean testAar(float minX, float minY, float maxX, float maxY) {
        float nxX = +m00, nxY = +m10, nxW = 1.0f + m20;
        float pxX = -m00, pxY = -m10, pxW = 1.0f - m20;
        float nyX = +m01, nyY = +m11, nyW = 1.0f + m21;
        float pyX = -m01, pyY = -m11, pyW = 1.0f - m21;
        /*
         * This is an implementation of the "2.4 Basic intersection test" of the mentioned site.
         * It does not distinguish between partially inside and fully inside, though, so the test with the 'p' vertex is omitted.
         */
        return nxX * (nxX < 0 ? minX : maxX) + nxY * (nxY < 0 ? minY : maxY) >= -nxW &&
               pxX * (pxX < 0 ? minX : maxX) + pxY * (pxY < 0 ? minY : maxY) >= -pxW &&
               nyX * (nyX < 0 ? minX : maxX) + nyY * (nyY < 0 ? minY : maxY) >= -nyW &&
               pyX * (pyX < 0 ? minX : maxX) + pyY * (pyY < 0 ? minY : maxY) >= -pyW;
    }
}
