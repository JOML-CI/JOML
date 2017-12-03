/*
 * (C) Copyright 2015-2017 Richard Greenlees

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
import org.joml.api.AxisAngle4dc;
import org.joml.api.AxisAngle4fc;
import org.joml.api.matrix.IMatrix3f;
import org.joml.api.matrix.IMatrix4f;
import org.joml.api.matrix.Matrix3fc;
import org.joml.api.matrix.Matrix4fc;
import org.joml.api.quaternion.IQuaterniond;
import org.joml.api.quaternion.IQuaternionf;
import org.joml.api.quaternion.Quaterniondc;
import org.joml.api.quaternion.Quaternionfc;
import org.joml.api.vector.IVector3f;
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
 * Contains the definition of a 3x3 matrix of floats, and associated functions to transform
 * it. The matrix is column-major to match OpenGL's interpretation, and it looks like this:
 * <p>
 *      m00  m10  m20<br>
 *      m01  m11  m21<br>
 *      m02  m12  m22<br>
 * 
 * @author Richard Greenlees
 * @author Kai Burjack
 */
public class Matrix3f extends Matrix3fc implements Externalizable {

    private static final long serialVersionUID = 1L;

    public float m00, m01, m02;
    public float m10, m11, m12;
    public float m20, m21, m22;

    /**
     * Create a new {@link Matrix3f} and set it to {@link #identity() identity}.
     */
    public Matrix3f() {
        m00 = 1.0f;
        m11 = 1.0f;
        m22 = 1.0f;
    }

    /**
     * Create a new {@link Matrix3f} and make it a copy of the given matrix.
     * 
     * @param mat
     *          the {@link Matrix3fc} to copy the values from
     */
    public Matrix3f(IMatrix3f mat) {
        if (mat instanceof Matrix3f) {
            MemUtil.INSTANCE.copy((Matrix3f) mat, this);
        } else {
            setMatrix3fc(mat);
        }
    }

    /**
     * Create a new {@link Matrix3f} and make it a copy of the upper left 3x3 of the given {@link IMatrix4f}.
     * 
     * @param mat
     *          the {@link IMatrix4f} to copy the values from
     */
    public Matrix3f(IMatrix4f mat) {
        if (mat instanceof Matrix4f) {
            MemUtil.INSTANCE.copy((Matrix4f) mat, this);
        } else {
            setMatrix4fc(mat);
        }
    }

    /**
     * Create a new 3x3 matrix using the supplied float values. The order of the parameter is column-major, 
     * so the first three parameters specify the three elements of the first column.
     * 
     * @param m00
     *          the value of m00
     * @param m01
     *          the value of m01
     * @param m02
     *          the value of m02
     * @param m10
     *          the value of m10
     * @param m11
     *          the value of m11
     * @param m12
     *          the value of m12
     * @param m20
     *          the value of m20
     * @param m21
     *          the value of m21
     * @param m22
     *          the value of m22
     */
    public Matrix3f(float m00, float m01, float m02,
                    float m10, float m11, float m12, 
                    float m20, float m21, float m22) {
        this.m00 = m00;
        this.m01 = m01;
        this.m02 = m02;
        this.m10 = m10;
        this.m11 = m11;
        this.m12 = m12;
        this.m20 = m20;
        this.m21 = m21;
        this.m22 = m22;
    }

//#ifdef __HAS_NIO__
    /**
     * Create a new {@link Matrix3f} by reading its 9 float components from the given {@link FloatBuffer}
     * at the buffer's current position.
     * <p>
     * That FloatBuffer is expected to hold the values in column-major order.
     * <p>
     * The buffer's position will not be changed by this method.
     * 
     * @param buffer
     *          the {@link FloatBuffer} to read the matrix values from
     */
    public Matrix3f(FloatBuffer buffer) {
        MemUtil.INSTANCE.get(this, buffer.position(), buffer);
    }
//#endif

    /**
     * Create a new {@link Matrix3f} and initialize its three columns using the supplied vectors.
     * 
     * @param col0
     *          the first column
     * @param col1
     *          the second column
     * @param col2
     *          the third column
     */
    public Matrix3f(IVector3f col0, IVector3f col1, IVector3f col2) {
        if (col0 instanceof Vector3f &&
            col1 instanceof Vector3f &&
            col2 instanceof Vector3f) {
            MemUtil.INSTANCE.set(this, (Vector3f) col0, (Vector3f) col1, (Vector3f) col2);
        } else {
            setVector3fc(col0, col1, col2);
        }
    }

    @Override
    public float m00() {
        return m00;
    }

    @Override
    public float m01() {
        return m01;
    }

    @Override
    public float m02() {
        return m02;
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
    public float m12() {
        return m12;
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
    public float m22() {
        return m22;
    }

    @Override
    public Matrix3fc m00(float m00) {
        this.m00 = m00;
        return this;
    }

    @Override
    public Matrix3fc m01(float m01) {
        this.m01 = m01;
        return this;
    }

    @Override
    public Matrix3fc m02(float m02) {
        this.m02 = m02;
        return this;
    }

    @Override
    public Matrix3fc m10(float m10) {
        this.m10 = m10;
        return this;
    }

    @Override
    public Matrix3fc m11(float m11) {
        this.m11 = m11;
        return this;
    }

    @Override
    public Matrix3fc m12(float m12) {
        this.m12 = m12;
        return this;
    }

    @Override
    public Matrix3fc m20(float m20) {
        this.m20 = m20;
        return this;
    }

    @Override
    public Matrix3fc m21(float m21) {
        this.m21 = m21;
        return this;
    }

    @Override
    public Matrix3fc m22(float m22) {
        this.m22 = m22;
        return this;
    }

    @Override
    public Matrix3fc set(IMatrix3f m) {
        if (m instanceof Matrix3f) {
            MemUtil.INSTANCE.copy((Matrix3f) m, this);
        } else {
            setMatrix3fc(m);
        }
        return this;
    }

    private void setMatrix3fc(IMatrix3f mat) {
        m00 = mat.m00();
        m01 = mat.m01();
        m02 = mat.m02();
        m10 = mat.m10();
        m11 = mat.m11();
        m12 = mat.m12();
        m20 = mat.m20();
        m21 = mat.m21();
        m22 = mat.m22();
    }

    @Override
    public Matrix3fc set(IMatrix4f mat) {
        if (mat instanceof Matrix4f) {
            MemUtil.INSTANCE.copy((Matrix4f) mat, this);
        } else {
            setMatrix4fc(mat);
        }
        return this;
    }

    private void setMatrix4fc(IMatrix4f mat) {
        m00 = mat.m00();
        m01 = mat.m01();
        m02 = mat.m02();
        m10 = mat.m10();
        m11 = mat.m11();
        m12 = mat.m12();
        m20 = mat.m20();
        m21 = mat.m21();
        m22 = mat.m22();
    }

    @Override
    public Matrix3fc set(AxisAngle4fc axisAngle) {
        float x = axisAngle.x();
        float y = axisAngle.y();
        float z = axisAngle.z();
        float angle = axisAngle.angle();
        float invLength = (float) (1.0 / Math.sqrt(x*x + y*y + z*z));
        x *= invLength;
        y *= invLength;
        z *= invLength;
        float s = (float) Math.sin(angle);
        float c = (float) Math.cosFromSin(s, angle);
        float omc = 1.0f - c;
        m00 = c + x*x*omc;
        m11 = c + y*y*omc;
        m22 = c + z*z*omc;
        float tmp1 = x*y*omc;
        float tmp2 = z*s;
        m10 = tmp1 - tmp2;
        m01 = tmp1 + tmp2;
        tmp1 = x*z*omc;
        tmp2 = y*s;
        m20 = tmp1 + tmp2;
        m02 = tmp1 - tmp2;
        tmp1 = y*z*omc;
        tmp2 = x*s;
        m21 = tmp1 - tmp2;
        m12 = tmp1 + tmp2;
        return this;
    }

    @Override
    public Matrix3fc set(AxisAngle4dc axisAngle) {
        double x = axisAngle.x();
        double y = axisAngle.y();
        double z = axisAngle.z();
        double angle = axisAngle.angle();
        double invLength = 1.0 / Math.sqrt(x*x + y*y + z*z);
        x *= invLength;
        y *= invLength;
        z *= invLength;
        double s = Math.sin(angle);
        double c = Math.cosFromSin(s, angle);
        double omc = 1.0f - c;
        m00 = (float)(c + x*x*omc);
        m11 = (float)(c + y*y*omc);
        m22 = (float)(c + z*z*omc);
        double tmp1 = x*y*omc;
        double tmp2 = z*s;
        m10 = (float)(tmp1 - tmp2);
        m01 = (float)(tmp1 + tmp2);
        tmp1 = x*z*omc;
        tmp2 = y*s;
        m20 = (float)(tmp1 + tmp2);
        m02 = (float)(tmp1 - tmp2);
        tmp1 = y*z*omc;
        tmp2 = x*s;
        m21 = (float)(tmp1 - tmp2);
        m12 = (float)(tmp1 + tmp2);
        return this;
    }

    @Override
    public Matrix3fc set(IQuaternionf q) {
        return rotation(q);
    }

    @Override
    public Matrix3fc set(IQuaterniond q) {
        double w2 = q.w() * q.w();
        double x2 = q.x() * q.x();
        double y2 = q.y() * q.y();
        double z2 = q.z() * q.z();
        double zw = q.z() * q.w();
        double xy = q.x() * q.y();
        double xz = q.x() * q.z();
        double yw = q.y() * q.w();
        double yz = q.y() * q.z();
        double xw = q.x() * q.w();
        m00 = (float) (w2 + x2 - z2 - y2);
        m01 = (float) (xy + zw + zw + xy);
        m02 = (float) (xz - yw + xz - yw);
        m10 = (float) (-zw + xy - zw + xy);
        m11 = (float) (y2 - z2 + w2 - x2);
        m12 = (float) (yz + yz + xw + xw);
        m20 = (float) (yw + xz + xz + yw);
        m21 = (float) (yz + yz - xw - xw);
        m22 = (float) (z2 - y2 - x2 + w2);
        return this;
    }

    @Override
    public Matrix3fc mul(IMatrix3f right) {
        return mul(right, this);
    }

    @Override
    public Matrix3fc mul(IMatrix3f right, Matrix3fc dest) {
        float nm00 = m00 * right.m00() + m10 * right.m01() + m20 * right.m02();
        float nm01 = m01 * right.m00() + m11 * right.m01() + m21 * right.m02();
        float nm02 = m02 * right.m00() + m12 * right.m01() + m22 * right.m02();
        float nm10 = m00 * right.m10() + m10 * right.m11() + m20 * right.m12();
        float nm11 = m01 * right.m10() + m11 * right.m11() + m21 * right.m12();
        float nm12 = m02 * right.m10() + m12 * right.m11() + m22 * right.m12();
        float nm20 = m00 * right.m20() + m10 * right.m21() + m20 * right.m22();
        float nm21 = m01 * right.m20() + m11 * right.m21() + m21 * right.m22();
        float nm22 = m02 * right.m20() + m12 * right.m21() + m22 * right.m22();
        dest.m00(nm00);
        dest.m01(nm01);
        dest.m02(nm02);
        dest.m10(nm10);
        dest.m11(nm11);
        dest.m12(nm12);
        dest.m20(nm20);
        dest.m21(nm21);
        dest.m22(nm22);
        return dest;
    }

    @Override
    public Matrix3fc mulLocal(IMatrix3f left) {
       return mulLocal(left, this);
    }

    @Override
    public Matrix3fc mulLocal(IMatrix3f left, Matrix3fc dest) {
        float nm00 = left.m00() * m00 + left.m10() * m01 + left.m20() * m02;
        float nm01 = left.m01() * m00 + left.m11() * m01 + left.m20() * m02;
        float nm10 = left.m00() * m10 + left.m10() * m11 + left.m20() * m12;
        float nm11 = left.m01() * m10 + left.m11() * m11 + left.m20() * m12;
        float nm20 = left.m00() * m20 + left.m10() * m21 + left.m20() * m22;
        float nm21 = left.m01() * m20 + left.m11() * m21 + left.m21() * m22;
        dest.m00(nm00);
        dest.m01(nm01);
        dest.m10(nm10);
        dest.m11(nm11);
        dest.m20(nm20);
        dest.m21(nm21);
        return dest;
    }

    @Override
    public Matrix3fc set(float m00, float m01, float m02,
                        float m10, float m11, float m12, 
                        float m20, float m21, float m22) {
        this.m00 = m00;
        this.m01 = m01;
        this.m02 = m02;
        this.m10 = m10;
        this.m11 = m11;
        this.m12 = m12;
        this.m20 = m20;
        this.m21 = m21;
        this.m22 = m22;
        return this;
    }

    @Override
    public Matrix3fc set(float m[]) {
        MemUtil.INSTANCE.copy(m, 0, this);
        return this;
    }

    @Override
    public Matrix3fc set(IVector3f col0, IVector3f col1, IVector3f col2) {
        if (col0 instanceof Vector3f &&
            col1 instanceof Vector3f &&
            col2 instanceof Vector3f) {
            MemUtil.INSTANCE.set(this, (Vector3f) col0, (Vector3f) col1, (Vector3f) col2);
        } else {
            setVector3fc(col0, col1, col2);
        }
        return this;
    }

    private void setVector3fc(IVector3f col0, IVector3f col1, IVector3f col2) {
        this.m00 = col0.x();
        this.m01 = col0.y();
        this.m02 = col0.z();
        this.m10 = col1.x();
        this.m11 = col1.y();
        this.m12 = col1.z();
        this.m20 = col2.x();
        this.m21 = col2.y();
        this.m22 = col2.z();
    }

    @Override
    public float determinant() {
        return (m00 * m11 - m01 * m10) * m22
             + (m02 * m10 - m00 * m12) * m21
             + (m01 * m12 - m02 * m11) * m20;
    }

    @Override
    public Matrix3fc invert() {
        return invert(this);
    }

    @Override
    public Matrix3fc invert(Matrix3fc dest) {
        float s = determinant();
        // client must make sure that matrix is invertible
        s = 1.0f / s;
        float nm00 = (m11 * m22 - m21 * m12) * s;
        float nm01 = (m21 * m02 - m01 * m22) * s;
        float nm02 = (m01 * m12 - m11 * m02) * s;
        float nm10 = (m20 * m12 - m10 * m22) * s;
        float nm11 = (m00 * m22 - m20 * m02) * s;
        float nm12 = (m10 * m02 - m00 * m12) * s;
        float nm20 = (m10 * m21 - m20 * m11) * s;
        float nm21 = (m20 * m01 - m00 * m21) * s;
        float nm22 = (m00 * m11 - m10 * m01) * s;
        dest.m00(nm00);
        dest.m01(nm01);
        dest.m02(nm02);
        dest.m10(nm10);
        dest.m11(nm11);
        dest.m12(nm12);
        dest.m20(nm20);
        dest.m21(nm21);
        dest.m22(nm22);
        return dest;
    }

    @Override
    public Matrix3fc transpose() {
        return transpose(this);
    }

    @Override
    public Matrix3fc transpose(Matrix3fc dest) {
        dest.set(m00, m10, m20,
                 m01, m11, m21,
                 m02, m12, m22);
        return dest;
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
             + formatter.format(m01) + " " + formatter.format(m11) + " " + formatter.format(m21) + "\n"
             + formatter.format(m02) + " " + formatter.format(m12) + " " + formatter.format(m22) + "\n";
    }

    @Override
    public Matrix3fc get(Matrix3fc dest) {
        return dest.set(this);
    }

    @Override
    public Matrix4fc get(Matrix4fc dest) {
        return dest.set(this);
    }

    @Override
    public AxisAngle4fc getRotation(AxisAngle4fc dest) {
        return dest.set(this);
    }

    @Override
    public Quaternionfc getUnnormalizedRotation(Quaternionfc dest) {
        return dest.setFromUnnormalized(this);
    }

    @Override
    public Quaternionfc getNormalizedRotation(Quaternionfc dest) {
        return dest.setFromNormalized(this);
    }

    @Override
    public Quaterniondc getUnnormalizedRotation(Quaterniondc dest) {
        return dest.setFromUnnormalized(this);
    }

    @Override
    public Quaterniondc getNormalizedRotation(Quaterniondc dest) {
        return dest.setFromNormalized(this);
    }

//#ifdef __GWT__
    @Override
    public Float32Array get(Float32Array buffer) {
        buffer.set(0,  m00);
        buffer.set(1,  m01);
        buffer.set(2,  m02);
        buffer.set(3,  m10);
        buffer.set(4,  m11);
        buffer.set(5,  m12);
        buffer.set(6,  m20);
        buffer.set(7,  m21);
        buffer.set(8,  m22);
        return buffer;
    }

    @Override
    public Float32Array get(int index, Float32Array buffer) {
        buffer.set(index,    m00);
        buffer.set(index+1,  m01);
        buffer.set(index+2,  m02);
        buffer.set(index+3,  m10);
        buffer.set(index+4,  m11);
        buffer.set(index+5,  m12);
        buffer.set(index+6,  m20);
        buffer.set(index+7,  m21);
        buffer.set(index+8,  m22);
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
    public FloatBuffer getTransposed(FloatBuffer buffer) {
        return getTransposed(buffer.position(), buffer);
    }

    @Override
    public FloatBuffer getTransposed(int index, FloatBuffer buffer) {
        MemUtil.INSTANCE.putTransposed(this, index, buffer);
        return buffer;
    }

    @Override
    public ByteBuffer getTransposed(ByteBuffer buffer) {
        return getTransposed(buffer.position(), buffer);
    }

    @Override
    public ByteBuffer getTransposed(int index, ByteBuffer buffer) {
        MemUtil.INSTANCE.putTransposed(this, index, buffer);
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

//#ifdef __HAS_NIO__
    @Override
    public Matrix3fc set(FloatBuffer buffer) {
        MemUtil.INSTANCE.get(this, buffer.position(), buffer);
        return this;
    }

    @Override
    public Matrix3fc set(ByteBuffer buffer) {
        MemUtil.INSTANCE.get(this, buffer.position(), buffer);
        return this;
    }
//#endif

    @Override
    public Matrix3fc zero() {
        MemUtil.INSTANCE.zero(this);
        return this;
    }

    @Override
    public Matrix3fc identity() {
        MemUtil.INSTANCE.identity(this);
        return this;
    }

    @Override
    public Matrix3fc scale(IVector3f xyz, Matrix3fc dest) {
        return scale(xyz.x(), xyz.y(), xyz.z(), dest);
    }

    @Override
    public Matrix3fc scale(IVector3f xyz) {
        return scale(xyz.x(), xyz.y(), xyz.z(), this);
    }

    @Override
    public Matrix3fc scale(float x, float y, float z, Matrix3fc dest) {
        // scale matrix elements:
        // m00 = x, m11 = y, m22 = z
        // all others = 0
        dest.m00(m00 * x);
        dest.m01(m01 * x);
        dest.m02(m02 * x);
        dest.m10(m10 * y);
        dest.m11(m11 * y);
        dest.m12(m12 * y);
        dest.m20(m20 * z);
        dest.m21(m21 * z);
        dest.m22(m22 * z);
        return dest;
    }

    @Override
    public Matrix3fc scale(float x, float y, float z) {
        return scale(x, y, z, this);
    }

    @Override
    public Matrix3fc scale(float xyz, Matrix3fc dest) {
        return scale(xyz, xyz, xyz, dest);
    }

    @Override
    public Matrix3fc scale(float xyz) {
        return scale(xyz, xyz, xyz);
    }

    @Override
    public Matrix3fc scaleLocal(float x, float y, float z, Matrix3fc dest) {
        float nm00 = x * m00;
        float nm01 = y * m01;
        float nm02 = z * m02;
        float nm10 = x * m10;
        float nm11 = y * m11;
        float nm12 = z * m12;
        float nm20 = x * m20;
        float nm21 = y * m21;
        float nm22 = z * m22;
        dest.m00(nm00);
        dest.m01(nm01);
        dest.m02(nm02);
        dest.m10(nm10);
        dest.m11(nm11);
        dest.m12(nm12);
        dest.m20(nm20);
        dest.m21(nm21);
        dest.m22(nm22);
        return dest;
    }

    @Override
    public Matrix3fc scaleLocal(float x, float y, float z) {
        return scaleLocal(x, y, z, this);
    }

    @Override
    public Matrix3fc scaling(float factor) {
        MemUtil.INSTANCE.zero(this);
        m00 = factor;
        m11 = factor;
        m22 = factor;
        return this;
    }

    @Override
    public Matrix3fc scaling(float x, float y, float z) {
        MemUtil.INSTANCE.zero(this);
        m00 = x;
        m11 = y;
        m22 = z;
        return this;
    }

    @Override
    public Matrix3fc scaling(IVector3f xyz) {
        return scaling(xyz.x(), xyz.y(), xyz.z());
    }

    @Override
    public Matrix3fc rotation(float angle, IVector3f axis) {
        return rotation(angle, axis.x(), axis.y(), axis.z());
    }

    @Override
    public Matrix3fc rotation(AxisAngle4fc axisAngle) {
        return rotation(axisAngle.angle(), axisAngle.x(), axisAngle.y(), axisAngle.z());
    }

    @Override
    public Matrix3fc rotation(float angle, float x, float y, float z) {
        float sin = (float) Math.sin(angle);
        float cos = (float) Math.cosFromSin(sin, angle);
        float C = 1.0f - cos;
        float xy = x * y, xz = x * z, yz = y * z;
        m00 = cos + x * x * C;
        m10 = xy * C - z * sin;
        m20 = xz * C + y * sin;
        m01 = xy * C + z * sin;
        m11 = cos + y * y * C;
        m21 = yz * C - x * sin;
        m02 = xz * C - y * sin;
        m12 = yz * C + x * sin;
        m22 = cos + z * z * C;
        return this;
    }

    @Override
    public Matrix3fc rotationX(float ang) {
        float sin, cos;
        sin = (float) Math.sin(ang);
        cos = (float) Math.cosFromSin(sin, ang);
        m00 = 1.0f;
        m01 = 0.0f;
        m02 = 0.0f;
        m10 = 0.0f;
        m11 = cos;
        m12 = sin;
        m20 = 0.0f;
        m21 = -sin;
        m22 = cos;
        return this;
    }

    @Override
    public Matrix3fc rotationY(float ang) {
        float sin, cos;
        sin = (float) Math.sin(ang);
        cos = (float) Math.cosFromSin(sin, ang);
        m00 = cos;
        m01 = 0.0f;
        m02 = -sin;
        m10 = 0.0f;
        m11 = 1.0f;
        m12 = 0.0f;
        m20 = sin;
        m21 = 0.0f;
        m22 = cos;
        return this;
    }

    @Override
    public Matrix3fc rotationZ(float ang) {
        float sin, cos;
        sin = (float) Math.sin(ang);
        cos = (float) Math.cosFromSin(sin, ang);
        m00 = cos;
        m01 = sin;
        m02 = 0.0f;
        m10 = -sin;
        m11 = cos;
        m12 = 0.0f;
        m20 = 0.0f;
        m21 = 0.0f;
        m22 = 1.0f;
        return this;
    }

    @Override
    public Matrix3fc rotationXYZ(float angleX, float angleY, float angleZ) {
        float sinX = (float) Math.sin(angleX);
        float cosX = (float) Math.cosFromSin(sinX, angleX);
        float sinY = (float) Math.sin(angleY);
        float cosY = (float) Math.cosFromSin(sinY, angleY);
        float sinZ = (float) Math.sin(angleZ);
        float cosZ = (float) Math.cosFromSin(sinZ, angleZ);
        float m_sinX = -sinX;
        float m_sinY = -sinY;
        float m_sinZ = -sinZ;

        // rotateX
        float nm11 = cosX;
        float nm12 = sinX;
        float nm21 = m_sinX;
        float nm22 = cosX;
        // rotateY
        float nm00 = cosY;
        float nm01 = nm21 * m_sinY;
        float nm02 = nm22 * m_sinY;
        m20 = sinY;
        m21 = nm21 * cosY;
        m22 = nm22 * cosY;
        // rotateZ
        m00 = nm00 * cosZ;
        m01 = nm01 * cosZ + nm11 * sinZ;
        m02 = nm02 * cosZ + nm12 * sinZ;
        m10 = nm00 * m_sinZ;
        m11 = nm01 * m_sinZ + nm11 * cosZ;
        m12 = nm02 * m_sinZ + nm12 * cosZ;
        return this;
    }

    @Override
    public Matrix3fc rotationZYX(float angleZ, float angleY, float angleX) {
        float sinX = (float) Math.sin(angleX);
        float cosX = (float) Math.cosFromSin(sinX, angleX);
        float sinY = (float) Math.sin(angleY);
        float cosY = (float) Math.cosFromSin(sinY, angleY);
        float sinZ = (float) Math.sin(angleZ);
        float cosZ = (float) Math.cosFromSin(sinZ, angleZ);
        float m_sinZ = -sinZ;
        float m_sinY = -sinY;
        float m_sinX = -sinX;

        // rotateZ
        float nm00 = cosZ;
        float nm01 = sinZ;
        float nm10 = m_sinZ;
        float nm11 = cosZ;
        // rotateY
        float nm20 = nm00 * sinY;
        float nm21 = nm01 * sinY;
        float nm22 = cosY;
        m00 = nm00 * cosY;
        m01 = nm01 * cosY;
        m02 = m_sinY;
        // rotateX
        m10 = nm10 * cosX + nm20 * sinX;
        m11 = nm11 * cosX + nm21 * sinX;
        m12 = nm22 * sinX;
        m20 = nm10 * m_sinX + nm20 * cosX;
        m21 = nm11 * m_sinX + nm21 * cosX;
        m22 = nm22 * cosX;
        return this;
    }

    @Override
    public Matrix3fc rotationYXZ(float angleY, float angleX, float angleZ) {
        float sinX = (float) Math.sin(angleX);
        float cosX = (float) Math.cosFromSin(sinX, angleX);
        float sinY = (float) Math.sin(angleY);
        float cosY = (float) Math.cosFromSin(sinY, angleY);
        float sinZ = (float) Math.sin(angleZ);
        float cosZ = (float) Math.cosFromSin(sinZ, angleZ);
        float m_sinY = -sinY;
        float m_sinX = -sinX;
        float m_sinZ = -sinZ;

        // rotateY
        float nm00 = cosY;
        float nm02 = m_sinY;
        float nm20 = sinY;
        float nm22 = cosY;
        // rotateX
        float nm10 = nm20 * sinX;
        float nm11 = cosX;
        float nm12 = nm22 * sinX;
        m20 = nm20 * cosX;
        m21 = m_sinX;
        m22 = nm22 * cosX;
        // rotateZ
        m00 = nm00 * cosZ + nm10 * sinZ;
        m01 = nm11 * sinZ;
        m02 = nm02 * cosZ + nm12 * sinZ;
        m10 = nm00 * m_sinZ + nm10 * cosZ;
        m11 = nm11 * cosZ;
        m12 = nm02 * m_sinZ + nm12 * cosZ;
        return this;
    }

    @Override
    public Matrix3fc rotation(IQuaternionf quat) {
        float w2 = quat.w() * quat.w();
        float x2 = quat.x() * quat.x();
        float y2 = quat.y() * quat.y();
        float z2 = quat.z() * quat.z();
        float zw = quat.z() * quat.w();
        float xy = quat.x() * quat.y();
        float xz = quat.x() * quat.z();
        float yw = quat.y() * quat.w();
        float yz = quat.y() * quat.z();
        float xw = quat.x() * quat.w();
        m00 = w2 + x2 - z2 - y2;
        m01 = xy + zw + zw + xy;
        m02 = xz - yw + xz - yw;
        m10 = -zw + xy - zw + xy;
        m11 = y2 - z2 + w2 - x2;
        m12 = yz + yz + xw + xw;
        m20 = yw + xz + xz + yw;
        m21 = yz + yz - xw - xw;
        m22 = z2 - y2 - x2 + w2;
        return this;
    }

    @Override
    public Vector3fc transform(Vector3fc v) {
        return v.mul(this);
    }

    @Override
    public Vector3fc transform(IVector3f v, Vector3fc dest) {
        v.mul(this, dest);
        return dest;
    }

    @Override
    public Vector3fc transform(float x, float y, float z, Vector3fc dest) {
        dest.set(m00 * x + m10 * y + m20 * z,
                 m01 * x + m11 * y + m21 * z,
                 m02 * x + m12 * y + m22 * z);
        return dest;
    }

    @Override
    public Vector3fc transformTranspose(Vector3fc v) {
        return v.mulTranspose(this);
    }

    @Override
    public Vector3fc transformTranspose(IVector3f v, Vector3fc dest) {
        v.mulTranspose(this, dest);
        return dest;
    }

    @Override
    public Vector3fc transformTranspose(float x, float y, float z, Vector3fc dest) {
        dest.set(m00 * x + m01 * y + m02 * z,
                 m10 * x + m11 * y + m12 * z,
                 m20 * x + m21 * y + m22 * z);
        return dest;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeFloat(m00);
        out.writeFloat(m01);
        out.writeFloat(m02);
        out.writeFloat(m10);
        out.writeFloat(m11);
        out.writeFloat(m12);
        out.writeFloat(m20);
        out.writeFloat(m21);
        out.writeFloat(m22);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException {
        m00 = in.readFloat();
        m01 = in.readFloat();
        m02 = in.readFloat();
        m10 = in.readFloat();
        m11 = in.readFloat();
        m12 = in.readFloat();
        m20 = in.readFloat();
        m21 = in.readFloat();
        m22 = in.readFloat();
    }

    @Override
    public Matrix3fc rotateX(float ang, Matrix3fc dest) {
        float sin, cos;
        if (ang == (float) Math.PI || ang == -(float) Math.PI) {
            cos = -1.0f;
            sin = 0.0f;
        } else if (ang == (float) Math.PI * 0.5f || ang == -(float) Math.PI * 1.5f) {
            cos = 0.0f;
            sin = 1.0f;
        } else if (ang == (float) -Math.PI * 0.5f || ang == (float) Math.PI * 1.5f) {
            cos = 0.0f;
            sin = -1.0f;
        } else {
            sin = (float) Math.sin(ang);
            cos = (float) Math.cosFromSin(sin, ang);
        }
        float rm11 = cos;
        float rm21 = -sin;
        float rm12 = sin;
        float rm22 = cos;

        // add temporaries for dependent values
        float nm10 = m10 * rm11 + m20 * rm12;
        float nm11 = m11 * rm11 + m21 * rm12;
        float nm12 = m12 * rm11 + m22 * rm12;
        // set non-dependent values directly
        dest.m20(m10 * rm21 + m20 * rm22);
        dest.m21(m11 * rm21 + m21 * rm22);
        dest.m22(m12 * rm21 + m22 * rm22);
        // set other values
        dest.m10(nm10);
        dest.m11(nm11);
        dest.m12(nm12);
        dest.m00(m00);
        dest.m01(m01);
        dest.m02(m02);

        return dest;
    }

    @Override
    public Matrix3fc rotateX(float ang) {
        return rotateX(ang, this);
    }

    @Override
    public Matrix3fc rotateY(float ang, Matrix3fc dest) {
        float sin, cos;
        if (ang == (float) Math.PI || ang == -(float) Math.PI) {
            cos = -1.0f;
            sin = 0.0f;
        } else if (ang == (float) Math.PI * 0.5f || ang == -(float) Math.PI * 1.5f) {
            cos = 0.0f;
            sin = 1.0f;
        } else if (ang == (float) -Math.PI * 0.5f || ang == (float) Math.PI * 1.5f) {
            cos = 0.0f;
            sin = -1.0f;
        } else {
            sin = (float) Math.sin(ang);
            cos = (float) Math.cosFromSin(sin, ang);
        }
        float rm00 = cos;
        float rm20 = sin;
        float rm02 = -sin;
        float rm22 = cos;

        // add temporaries for dependent values
        float nm00 = m00 * rm00 + m20 * rm02;
        float nm01 = m01 * rm00 + m21 * rm02;
        float nm02 = m02 * rm00 + m22 * rm02;
        // set non-dependent values directly
        dest.m20(m00 * rm20 + m20 * rm22);
        dest.m21(m01 * rm20 + m21 * rm22);
        dest.m22(m02 * rm20 + m22 * rm22);
        // set other values
        dest.m00(nm00);
        dest.m01(nm01);
        dest.m02(nm02);
        dest.m10(m10);
        dest.m11(m11);
        dest.m12(m12);

        return dest;
    }

    @Override
    public Matrix3fc rotateY(float ang) {
        return rotateY(ang, this);
    }

    @Override
    public Matrix3fc rotateZ(float ang, Matrix3fc dest) {
        float sin, cos;
        if (ang == (float) Math.PI || ang == -(float) Math.PI) {
            cos = -1.0f;
            sin = 0.0f;
        } else if (ang == (float) Math.PI * 0.5f || ang == -(float) Math.PI * 1.5f) {
            cos = 0.0f;
            sin = 1.0f;
        } else if (ang == (float) -Math.PI * 0.5f || ang == (float) Math.PI * 1.5f) {
            cos = 0.0f;
            sin = -1.0f;
        } else {
            sin = (float) Math.sin(ang);
            cos = (float) Math.cosFromSin(sin, ang);
        }
        float rm00 = cos;
        float rm10 = -sin;
        float rm01 = sin;
        float rm11 = cos;

        // add temporaries for dependent values
        float nm00 = m00 * rm00 + m10 * rm01;
        float nm01 = m01 * rm00 + m11 * rm01;
        float nm02 = m02 * rm00 + m12 * rm01;
        // set non-dependent values directly
        dest.m10(m00 * rm10 + m10 * rm11);
        dest.m11(m01 * rm10 + m11 * rm11);
        dest.m12(m02 * rm10 + m12 * rm11);
        // set other values
        dest.m00(nm00);
        dest.m01(nm01);
        dest.m02(nm02);
        dest.m20(m20);
        dest.m21(m21);
        dest.m22(m22);

        return dest;
    }

    @Override
    public Matrix3fc rotateZ(float ang) {
        return rotateZ(ang, this);
    }

    @Override
    public Matrix3fc rotateXYZ(Vector3fc angles) {
        return rotateXYZ(angles.x(), angles.y(), angles.z());
    }

    @Override
    public Matrix3fc rotateXYZ(float angleX, float angleY, float angleZ) {
        return rotateXYZ(angleX, angleY, angleZ, this);
    }

    @Override
    public Matrix3fc rotateXYZ(float angleX, float angleY, float angleZ, Matrix3fc dest) {
        float sinX = (float) Math.sin(angleX);
        float cosX = (float) Math.cosFromSin(sinX, angleX);
        float sinY = (float) Math.sin(angleY);
        float cosY = (float) Math.cosFromSin(sinY, angleY);
        float sinZ = (float) Math.sin(angleZ);
        float cosZ = (float) Math.cosFromSin(sinZ, angleZ);
        float m_sinX = -sinX;
        float m_sinY = -sinY;
        float m_sinZ = -sinZ;

        // rotateX
        float nm10 = m10 * cosX + m20 * sinX;
        float nm11 = m11 * cosX + m21 * sinX;
        float nm12 = m12 * cosX + m22 * sinX;
        float nm20 = m10 * m_sinX + m20 * cosX;
        float nm21 = m11 * m_sinX + m21 * cosX;
        float nm22 = m12 * m_sinX + m22 * cosX;
        // rotateY
        float nm00 = m00 * cosY + nm20 * m_sinY;
        float nm01 = m01 * cosY + nm21 * m_sinY;
        float nm02 = m02 * cosY + nm22 * m_sinY;
        dest.m20(m00 * sinY + nm20 * cosY);
        dest.m21(m01 * sinY + nm21 * cosY);
        dest.m22(m02 * sinY + nm22 * cosY);
        // rotateZ
        dest.m00(nm00 * cosZ + nm10 * sinZ);
        dest.m01(nm01 * cosZ + nm11 * sinZ);
        dest.m02(nm02 * cosZ + nm12 * sinZ);
        dest.m10(nm00 * m_sinZ + nm10 * cosZ);
        dest.m11(nm01 * m_sinZ + nm11 * cosZ);
        dest.m12(nm02 * m_sinZ + nm12 * cosZ);
        return dest;
    }

    @Override
    public Matrix3fc rotateZYX(Vector3fc angles) {
        return rotateZYX(angles.z(), angles.y(), angles.x());
    }

    @Override
    public Matrix3fc rotateZYX(float angleZ, float angleY, float angleX) {
        return rotateZYX(angleZ, angleY, angleX, this);
    }

    @Override
    public Matrix3fc rotateZYX(float angleZ, float angleY, float angleX, Matrix3fc dest) {
        float sinX = (float) Math.sin(angleX);
        float cosX = (float) Math.cosFromSin(sinX, angleX);
        float sinY = (float) Math.sin(angleY);
        float cosY = (float) Math.cosFromSin(sinY, angleY);
        float sinZ = (float) Math.sin(angleZ);
        float cosZ = (float) Math.cosFromSin(sinZ, angleZ);
        float m_sinZ = -sinZ;
        float m_sinY = -sinY;
        float m_sinX = -sinX;

        // rotateZ
        float nm00 = m00 * cosZ + m10 * sinZ;
        float nm01 = m01 * cosZ + m11 * sinZ;
        float nm02 = m02 * cosZ + m12 * sinZ;
        float nm10 = m00 * m_sinZ + m10 * cosZ;
        float nm11 = m01 * m_sinZ + m11 * cosZ;
        float nm12 = m02 * m_sinZ + m12 * cosZ;
        // rotateY
        float nm20 = nm00 * sinY + m20 * cosY;
        float nm21 = nm01 * sinY + m21 * cosY;
        float nm22 = nm02 * sinY + m22 * cosY;
        dest.m00(nm00 * cosY + m20 * m_sinY);
        dest.m01(nm01 * cosY + m21 * m_sinY);
        dest.m02(nm02 * cosY + m22 * m_sinY);
        // rotateX
        dest.m10(nm10 * cosX + nm20 * sinX);
        dest.m11(nm11 * cosX + nm21 * sinX);
        dest.m12(nm12 * cosX + nm22 * sinX);
        dest.m20(nm10 * m_sinX + nm20 * cosX);
        dest.m21(nm11 * m_sinX + nm21 * cosX);
        dest.m22(nm12 * m_sinX + nm22 * cosX);
        return dest;
    }

    @Override
    public Matrix3fc rotateYXZ(Vector3fc angles) {
        return rotateYXZ(angles.y(), angles.x(), angles.z());
    }

    @Override
    public Matrix3fc rotateYXZ(float angleY, float angleX, float angleZ) {
        return rotateYXZ(angleY, angleX, angleZ, this);
    }

    @Override
    public Matrix3fc rotateYXZ(float angleY, float angleX, float angleZ, Matrix3fc dest) {
        float sinX = (float) Math.sin(angleX);
        float cosX = (float) Math.cosFromSin(sinX, angleX);
        float sinY = (float) Math.sin(angleY);
        float cosY = (float) Math.cosFromSin(sinY, angleY);
        float sinZ = (float) Math.sin(angleZ);
        float cosZ = (float) Math.cosFromSin(sinZ, angleZ);
        float m_sinY = -sinY;
        float m_sinX = -sinX;
        float m_sinZ = -sinZ;

        // rotateY
        float nm20 = m00 * sinY + m20 * cosY;
        float nm21 = m01 * sinY + m21 * cosY;
        float nm22 = m02 * sinY + m22 * cosY;
        float nm00 = m00 * cosY + m20 * m_sinY;
        float nm01 = m01 * cosY + m21 * m_sinY;
        float nm02 = m02 * cosY + m22 * m_sinY;
        // rotateX
        float nm10 = m10 * cosX + nm20 * sinX;
        float nm11 = m11 * cosX + nm21 * sinX;
        float nm12 = m12 * cosX + nm22 * sinX;
        dest.m20(m10 * m_sinX + nm20 * cosX);
        dest.m21(m11 * m_sinX + nm21 * cosX);
        dest.m22(m12 * m_sinX + nm22 * cosX);
        // rotateZ
        dest.m00(nm00 * cosZ + nm10 * sinZ);
        dest.m01(nm01 * cosZ + nm11 * sinZ);
        dest.m02(nm02 * cosZ + nm12 * sinZ);
        dest.m10(nm00 * m_sinZ + nm10 * cosZ);
        dest.m11(nm01 * m_sinZ + nm11 * cosZ);
        dest.m12(nm02 * m_sinZ + nm12 * cosZ);
        return dest;
    }

    @Override
    public Matrix3fc rotate(float ang, float x, float y, float z) {
        return rotate(ang, x, y, z, this);
    }

    @Override
    public Matrix3fc rotate(float ang, float x, float y, float z, Matrix3fc dest) {
        float s = (float) Math.sin(ang);
        float c = (float) Math.cosFromSin(s, ang);
        float C = 1.0f - c;

        // rotation matrix elements:
        // m30, m31, m32, m03, m13, m23 = 0
        float xx = x * x, xy = x * y, xz = x * z;
        float yy = y * y, yz = y * z;
        float zz = z * z;
        float rm00 = xx * C + c;
        float rm01 = xy * C + z * s;
        float rm02 = xz * C - y * s;
        float rm10 = xy * C - z * s;
        float rm11 = yy * C + c;
        float rm12 = yz * C + x * s;
        float rm20 = xz * C + y * s;
        float rm21 = yz * C - x * s;
        float rm22 = zz * C + c;

        // add temporaries for dependent values
        float nm00 = m00 * rm00 + m10 * rm01 + m20 * rm02;
        float nm01 = m01 * rm00 + m11 * rm01 + m21 * rm02;
        float nm02 = m02 * rm00 + m12 * rm01 + m22 * rm02;
        float nm10 = m00 * rm10 + m10 * rm11 + m20 * rm12;
        float nm11 = m01 * rm10 + m11 * rm11 + m21 * rm12;
        float nm12 = m02 * rm10 + m12 * rm11 + m22 * rm12;
        // set non-dependent values directly
        dest.m20(m00 * rm20 + m10 * rm21 + m20 * rm22);
        dest.m21(m01 * rm20 + m11 * rm21 + m21 * rm22);
        dest.m22(m02 * rm20 + m12 * rm21 + m22 * rm22);
        // set other values
        dest.m00(nm00);
        dest.m01(nm01);
        dest.m02(nm02);
        dest.m10(nm10);
        dest.m11(nm11);
        dest.m12(nm12);

        return dest;
    }

    @Override
    public Matrix3fc rotateLocal(float ang, float x, float y, float z, Matrix3fc dest) {
        float s = (float) Math.sin(ang);
        float c = (float) Math.cosFromSin(s, ang);
        float C = 1.0f - c;
        float xx = x * x, xy = x * y, xz = x * z;
        float yy = y * y, yz = y * z;
        float zz = z * z;
        float lm00 = xx * C + c;
        float lm01 = xy * C + z * s;
        float lm02 = xz * C - y * s;
        float lm10 = xy * C - z * s;
        float lm11 = yy * C + c;
        float lm12 = yz * C + x * s;
        float lm20 = xz * C + y * s;
        float lm21 = yz * C - x * s;
        float lm22 = zz * C + c;
        float nm00 = lm00 * m00 + lm10 * m01 + lm20 * m02;
        float nm01 = lm01 * m00 + lm11 * m01 + lm21 * m02;
        float nm02 = lm02 * m00 + lm12 * m01 + lm22 * m02;
        float nm10 = lm00 * m10 + lm10 * m11 + lm20 * m12;
        float nm11 = lm01 * m10 + lm11 * m11 + lm21 * m12;
        float nm12 = lm02 * m10 + lm12 * m11 + lm22 * m12;
        float nm20 = lm00 * m20 + lm10 * m21 + lm20 * m22;
        float nm21 = lm01 * m20 + lm11 * m21 + lm21 * m22;
        float nm22 = lm02 * m20 + lm12 * m21 + lm22 * m22;
        dest.m00(nm00);
        dest.m01(nm01);
        dest.m02(nm02);
        dest.m10(nm10);
        dest.m11(nm11);
        dest.m12(nm12);
        dest.m20(nm20);
        dest.m21(nm21);
        dest.m22(nm22);
        return dest;
    }

    @Override
    public Matrix3fc rotateLocal(float ang, float x, float y, float z) {
        return rotateLocal(ang, x, y, z, this);
    }

    @Override
    public Matrix3fc rotateLocalX(float ang, Matrix3fc dest) {
        float sin = (float) Math.sin(ang);
        float cos = (float) Math.cosFromSin(sin, ang);
        float nm01 = cos * m01 - sin * m02;
        float nm02 = sin * m01 + cos * m02;
        float nm11 = cos * m11 - sin * m12;
        float nm12 = sin * m11 + cos * m12;
        float nm21 = cos * m21 - sin * m22;
        float nm22 = sin * m21 + cos * m22;
        dest.m00(m00);
        dest.m01(nm01);
        dest.m02(nm02);
        dest.m10(m10);
        dest.m11(nm11);
        dest.m12(nm12);
        dest.m20(m20);
        dest.m21(nm21);
        dest.m22(nm22);
        return dest;
    }

    @Override
    public Matrix3fc rotateLocalX(float ang) {
        return rotateLocalX(ang, this);
    }

    @Override
    public Matrix3fc rotateLocalY(float ang, Matrix3fc dest) {
        float sin = (float) Math.sin(ang);
        float cos = (float) Math.cosFromSin(sin, ang);
        float nm00 =  cos * m00 + sin * m02;
        float nm02 = -sin * m00 + cos * m02;
        float nm10 =  cos * m10 + sin * m12;
        float nm12 = -sin * m10 + cos * m12;
        float nm20 =  cos * m20 + sin * m22;
        float nm22 = -sin * m20 + cos * m22;
        dest.m00(nm00);
        dest.m01(m01);
        dest.m02(nm02);
        dest.m10(nm10);
        dest.m11(m11);
        dest.m12(nm12);
        dest.m20(nm20);
        dest.m21(m21);
        dest.m22(nm22);
        return dest;
    }

    @Override
    public Matrix3fc rotateLocalY(float ang) {
        return rotateLocalY(ang, this);
    }

    @Override
    public Matrix3fc rotateLocalZ(float ang, Matrix3fc dest) {
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
        dest.m02(m02);
        dest.m10(nm10);
        dest.m11(nm11);
        dest.m12(m12);
        dest.m20(nm20);
        dest.m21(nm21);
        dest.m22(m22);
        return dest;
    }

    @Override
    public Matrix3fc rotateLocalZ(float ang) {
        return rotateLocalZ(ang, this);
    }

    @Override
    public Matrix3fc rotate(IQuaternionf quat) {
        return rotate(quat, this);
    }

    @Override
    public Matrix3fc rotate(IQuaternionf quat, Matrix3fc dest) {
        float w2 = quat.w() * quat.w();
        float x2 = quat.x() * quat.x();
        float y2 = quat.y() * quat.y();
        float z2 = quat.z() * quat.z();
        float zw = quat.z() * quat.w();
        float xy = quat.x() * quat.y();
        float xz = quat.x() * quat.z();
        float yw = quat.y() * quat.w();
        float yz = quat.y() * quat.z();
        float xw = quat.x() * quat.w();
        float rm00 = w2 + x2 - z2 - y2;
        float rm01 = xy + zw + zw + xy;
        float rm02 = xz - yw + xz - yw;
        float rm10 = -zw + xy - zw + xy;
        float rm11 = y2 - z2 + w2 - x2;
        float rm12 = yz + yz + xw + xw;
        float rm20 = yw + xz + xz + yw;
        float rm21 = yz + yz - xw - xw;
        float rm22 = z2 - y2 - x2 + w2;
        float nm00 = m00 * rm00 + m10 * rm01 + m20 * rm02;
        float nm01 = m01 * rm00 + m11 * rm01 + m21 * rm02;
        float nm02 = m02 * rm00 + m12 * rm01 + m22 * rm02;
        float nm10 = m00 * rm10 + m10 * rm11 + m20 * rm12;
        float nm11 = m01 * rm10 + m11 * rm11 + m21 * rm12;
        float nm12 = m02 * rm10 + m12 * rm11 + m22 * rm12;
        dest.m20(m00 * rm20 + m10 * rm21 + m20 * rm22);
        dest.m21(m01 * rm20 + m11 * rm21 + m21 * rm22);
        dest.m22(m02 * rm20 + m12 * rm21 + m22 * rm22);
        dest.m00(nm00);
        dest.m01(nm01);
        dest.m02(nm02);
        dest.m10(nm10);
        dest.m11(nm11);
        dest.m12(nm12);
        return dest;
    }

    @Override
    public Matrix3fc rotateLocal(IQuaternionf quat, Matrix3fc dest) {
        float w2 = quat.w() * quat.w();
        float x2 = quat.x() * quat.x();
        float y2 = quat.y() * quat.y();
        float z2 = quat.z() * quat.z();
        float zw = quat.z() * quat.w();
        float xy = quat.x() * quat.y();
        float xz = quat.x() * quat.z();
        float yw = quat.y() * quat.w();
        float yz = quat.y() * quat.z();
        float xw = quat.x() * quat.w();
        float lm00 = w2 + x2 - z2 - y2;
        float lm01 = xy + zw + zw + xy;
        float lm02 = xz - yw + xz - yw;
        float lm10 = -zw + xy - zw + xy;
        float lm11 = y2 - z2 + w2 - x2;
        float lm12 = yz + yz + xw + xw;
        float lm20 = yw + xz + xz + yw;
        float lm21 = yz + yz - xw - xw;
        float lm22 = z2 - y2 - x2 + w2;
        float nm00 = lm00 * m00 + lm10 * m01 + lm20 * m02;
        float nm01 = lm01 * m00 + lm11 * m01 + lm21 * m02;
        float nm02 = lm02 * m00 + lm12 * m01 + lm22 * m02;
        float nm10 = lm00 * m10 + lm10 * m11 + lm20 * m12;
        float nm11 = lm01 * m10 + lm11 * m11 + lm21 * m12;
        float nm12 = lm02 * m10 + lm12 * m11 + lm22 * m12;
        float nm20 = lm00 * m20 + lm10 * m21 + lm20 * m22;
        float nm21 = lm01 * m20 + lm11 * m21 + lm21 * m22;
        float nm22 = lm02 * m20 + lm12 * m21 + lm22 * m22;
        dest.m00(nm00);
        dest.m01(nm01);
        dest.m02(nm02);
        dest.m10(nm10);
        dest.m11(nm11);
        dest.m12(nm12);
        dest.m20(nm20);
        dest.m21(nm21);
        dest.m22(nm22);
        return dest;
    }

    @Override
    public Matrix3fc rotateLocal(IQuaternionf quat) {
        return rotateLocal(quat, this);
    }

    @Override
    public Matrix3fc rotate(AxisAngle4fc axisAngle) {
        return rotate(axisAngle.angle(), axisAngle.x(), axisAngle.y(), axisAngle.z());
    }

    @Override
    public Matrix3fc rotate(AxisAngle4fc axisAngle, Matrix3fc dest) {
        return rotate(axisAngle.angle(), axisAngle.x(), axisAngle.y(), axisAngle.z(), dest);
    }

    @Override
    public Matrix3fc rotate(float angle, IVector3f axis) {
        return rotate(angle, axis.x(), axis.y(), axis.z());
    }

    @Override
    public Matrix3fc rotate(float angle, IVector3f axis, Matrix3fc dest) {
        return rotate(angle, axis.x(), axis.y(), axis.z(), dest);
    }

    @Override
    public Matrix3fc lookAlong(IVector3f dir, IVector3f up) {
        return lookAlong(dir.x(), dir.y(), dir.z(), up.x(), up.y(), up.z(), this);
    }

    @Override
    public Matrix3fc lookAlong(IVector3f dir, IVector3f up, Matrix3fc dest) {
        return lookAlong(dir.x(), dir.y(), dir.z(), up.x(), up.y(), up.z(), dest);
    }

    @Override
    public Matrix3fc lookAlong(float dirX, float dirY, float dirZ,
                              float upX, float upY, float upZ, Matrix3fc dest) {
        // Normalize direction
        float invDirLength = (float) (1.0 / Math.sqrt(dirX * dirX + dirY * dirY + dirZ * dirZ));
        float dirnX = -dirX * invDirLength;
        float dirnY = -dirY * invDirLength;
        float dirnZ = -dirZ * invDirLength;
        // right = direction x up
        float rightX, rightY, rightZ;
        rightX = dirnY * upZ - dirnZ * upY;
        rightY = dirnZ * upX - dirnX * upZ;
        rightZ = dirnX * upY - dirnY * upX;
        // normalize right
        float invRightLength = (float) (1.0 / Math.sqrt(rightX * rightX + rightY * rightY + rightZ * rightZ));
        rightX *= invRightLength;
        rightY *= invRightLength;
        rightZ *= invRightLength;
        // up = right x direction
        float upnX = rightY * dirnZ - rightZ * dirnY;
        float upnY = rightZ * dirnX - rightX * dirnZ;
        float upnZ = rightX * dirnY - rightY * dirnX;

        // calculate right matrix elements
        float rm00 = rightX;
        float rm01 = upnX;
        float rm02 = -dirnX;
        float rm10 = rightY;
        float rm11 = upnY;
        float rm12 = -dirnY;
        float rm20 = rightZ;
        float rm21 = upnZ;
        float rm22 = -dirnZ;

        // perform optimized matrix multiplication
        // introduce temporaries for dependent results
        float nm00 = m00 * rm00 + m10 * rm01 + m20 * rm02;
        float nm01 = m01 * rm00 + m11 * rm01 + m21 * rm02;
        float nm02 = m02 * rm00 + m12 * rm01 + m22 * rm02;
        float nm10 = m00 * rm10 + m10 * rm11 + m20 * rm12;
        float nm11 = m01 * rm10 + m11 * rm11 + m21 * rm12;
        float nm12 = m02 * rm10 + m12 * rm11 + m22 * rm12;
        dest.m20(m00 * rm20 + m10 * rm21 + m20 * rm22);
        dest.m21(m01 * rm20 + m11 * rm21 + m21 * rm22);
        dest.m22(m02 * rm20 + m12 * rm21 + m22 * rm22);
        // set the rest of the matrix elements
        dest.m00(nm00);
        dest.m01(nm01);
        dest.m02(nm02);
        dest.m10(nm10);
        dest.m11(nm11);
        dest.m12(nm12);

        return dest;
    }

    @Override
    public Matrix3fc lookAlong(float dirX, float dirY, float dirZ,
                              float upX, float upY, float upZ) {
        return lookAlong(dirX, dirY, dirZ, upX, upY, upZ, this);
    }

    @Override
    public Matrix3fc setLookAlong(IVector3f dir, IVector3f up) {
        return setLookAlong(dir.x(), dir.y(), dir.z(), up.x(), up.y(), up.z());
    }

    @Override
    public Matrix3fc setLookAlong(float dirX, float dirY, float dirZ,
                                 float upX, float upY, float upZ) {
        // Normalize direction
        float invDirLength = (float) (1.0 / Math.sqrt(dirX * dirX + dirY * dirY + dirZ * dirZ));
        float dirnX = -dirX * invDirLength;
        float dirnY = -dirY * invDirLength;
        float dirnZ = -dirZ * invDirLength;
        // right = direction x up
        float rightX, rightY, rightZ;
        rightX = dirnY * upZ - dirnZ * upY;
        rightY = dirnZ * upX - dirnX * upZ;
        rightZ = dirnX * upY - dirnY * upX;
        // normalize right
        float invRightLength = (float) (1.0 / Math.sqrt(rightX * rightX + rightY * rightY + rightZ * rightZ));
        rightX *= invRightLength;
        rightY *= invRightLength;
        rightZ *= invRightLength;
        // up = right x direction
        float upnX = rightY * dirnZ - rightZ * dirnY;
        float upnY = rightZ * dirnX - rightX * dirnZ;
        float upnZ = rightX * dirnY - rightY * dirnX;

        m00 = rightX;
        m01 = upnX;
        m02 = -dirnX;
        m10 = rightY;
        m11 = upnY;
        m12 = -dirnY;
        m20 = rightZ;
        m21 = upnZ;
        m22 = -dirnZ;

        return this;
    }

    @Override
    public Vector3fc getRow(int row, Vector3fc dest) throws IndexOutOfBoundsException {
        switch (row) {
        case 0:
            dest.set(m00, m10, m20);
            break;
        case 1:
            dest.set(m01, m11, m21);
            break;
        case 2:
            dest.set(m02, m12, m22);
            break;
        default:
            throw new IndexOutOfBoundsException();
        }
        
        return dest;
    }

    @Override
    public Matrix3fc setRow(int row, IVector3f src) throws IndexOutOfBoundsException {
        return setRow(row, src.x(), src.y(), src.z());
    }

    @Override
    public Matrix3fc setRow(int row, float x, float y, float z) throws IndexOutOfBoundsException {
        switch (row) {
        case 0:
            this.m00 = x;
            this.m01 = y;
            this.m02 = z;
            break;
        case 1:
            this.m10 = x;
            this.m11 = y;
            this.m12 = z;
            break;
        case 2:
            this.m20 = x;
            this.m21 = y;
            this.m22 = z;
            break;
        default:
            throw new IndexOutOfBoundsException();
        }
        return this;
    }

    @Override
    public Vector3fc getColumn(int column, Vector3fc dest) throws IndexOutOfBoundsException {
        switch (column) {
        case 0:
            dest.set(m00, m01, m02);
            break;
        case 1:
            dest.set(m10, m11, m12);
            break;
        case 2:
            dest.set(m20, m21, m22);
            break;
        default:
            throw new IndexOutOfBoundsException();
        }
        return dest;
    }

    @Override
    public Matrix3fc setColumn(int column, IVector3f src) throws IndexOutOfBoundsException {
        return setColumn(column, src.x(), src.y(), src.z());
    }

    @Override
    public Matrix3fc setColumn(int column, float x, float y, float z) throws IndexOutOfBoundsException {
        switch (column) {
        case 0:
            this.m00 = x;
            this.m01 = y;
            this.m02 = z;
            break;
        case 1:
            this.m10 = x;
            this.m11 = y;
            this.m12 = z;
            break;
        case 2:
            this.m20 = x;
            this.m21 = y;
            this.m22 = z;
            break;
        default:
            throw new IndexOutOfBoundsException();
        }
        return this;
    }

    @Override
    public float get(int column, int row) {
        switch (column) {
        case 0:
            switch (row) {
            case 0:
                return m00;
            case 1:
                return m01;
            case 2:
                return m02;
            default:
                break;
            }
            break;
        case 1:
            switch (row) {
            case 0:
                return m10;
            case 1:
                return m11;
            case 2:
                return m12;
            default:
                break;
            }
            break;
        case 2:
            switch (row) {
            case 0:
                return m20;
            case 1:
                return m21;
            case 2:
                return m22;
            default:
                break;
            }
            break;
        default:
            break;
        }
        throw new IllegalArgumentException();
    }

    @Override
    public Matrix3fc set(int column, int row, float value) {
        switch (column) {
        case 0:
            switch (row) {
            case 0:
                this.m00 = value;
                return this;
            case 1:
                this.m01 = value;
                return this;
            case 2:
                this.m02 = value;
                return this;
            default:
                break;
            }
            break;
        case 1:
            switch (row) {
            case 0:
                this.m10 = value;
                return this;
            case 1:
                this.m11 = value;
                return this;
            case 2:
                this.m12 = value;
                return this;
            default:
                break;
            }
            break;
        case 2:
            switch (row) {
            case 0:
                this.m20 = value;
                return this;
            case 1:
                this.m21 = value;
                return this;
            case 2:
                this.m22 = value;
                return this;
            default:
                break;
            }
            break;
        default:
            break;
        }
        throw new IllegalArgumentException();
    }

    @Override
    public Matrix3fc normal() {
        return normal(this);
    }

    @Override
    public Matrix3fc normal(Matrix3fc dest) {
        float m00m11 = m00 * m11;
        float m01m10 = m01 * m10;
        float m02m10 = m02 * m10;
        float m00m12 = m00 * m12;
        float m01m12 = m01 * m12;
        float m02m11 = m02 * m11;
        float det = (m00m11 - m01m10) * m22 + (m02m10 - m00m12) * m21 + (m01m12 - m02m11) * m20;
        float s = 1.0f / det;
        /* Invert and transpose in one go */
        float nm00 = (m11 * m22 - m21 * m12) * s;
        float nm01 = (m20 * m12 - m10 * m22) * s;
        float nm02 = (m10 * m21 - m20 * m11) * s;
        float nm10 = (m21 * m02 - m01 * m22) * s;
        float nm11 = (m00 * m22 - m20 * m02) * s;
        float nm12 = (m20 * m01 - m00 * m21) * s;
        float nm20 = (m01m12 - m02m11) * s;
        float nm21 = (m02m10 - m00m12) * s;
        float nm22 = (m00m11 - m01m10) * s;
        dest.m00(nm00);
        dest.m01(nm01);
        dest.m02(nm02);
        dest.m10(nm10);
        dest.m11(nm11);
        dest.m12(nm12);
        dest.m20(nm20);
        dest.m21(nm21);
        dest.m22(nm22);
        return dest;
    }

    @Override
    public Vector3fc getScale(Vector3fc dest) {
        dest.set((float) Math.sqrt(m00 * m00 + m01 * m01 + m02 * m02), (float) Math.sqrt(m10 * m10 + m11 * m11 + m12 * m12), (float) Math.sqrt(m20 * m20 + m21 * m21 + m22 * m22));
        return dest;
    }

    @Override
    public Vector3fc positiveZ(Vector3fc dir) {
        dir.set(m10 * m21 - m11 * m20, m20 * m01 - m21 * m00, m00 * m11 - m01 * m10);
        dir.normalize();
        return dir;
    }

    @Override
    public Vector3fc normalizedPositiveZ(Vector3fc dir) {
        dir.set(m02, m12, m22);
        return dir;
    }

    @Override
    public Vector3fc positiveX(Vector3fc dir) {
        dir.set(m11 * m22 - m12 * m21, m02 * m21 - m01 * m22, m01 * m12 - m02 * m11);
        dir.normalize();
        return dir;
    }

    @Override
    public Vector3fc normalizedPositiveX(Vector3fc dir) {
        dir.set(m00, m10, m20);
        return dir;
    }

    @Override
    public Vector3fc positiveY(Vector3fc dir) {
        dir.set(m12 * m20 - m10 * m22, m00 * m22 - m02 * m20, m02 * m10 - m00 * m12);
        dir.normalize();
        return dir;
    }

    @Override
    public Vector3fc normalizedPositiveY(Vector3fc dir) {
        dir.set(m01, m11, m21);
        return dir;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Float.floatToIntBits(m00);
        result = prime * result + Float.floatToIntBits(m01);
        result = prime * result + Float.floatToIntBits(m02);
        result = prime * result + Float.floatToIntBits(m10);
        result = prime * result + Float.floatToIntBits(m11);
        result = prime * result + Float.floatToIntBits(m12);
        result = prime * result + Float.floatToIntBits(m20);
        result = prime * result + Float.floatToIntBits(m21);
        result = prime * result + Float.floatToIntBits(m22);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Matrix3fc))
            return false;
        Matrix3fc other = (Matrix3fc) obj;
        if (Float.floatToIntBits(m00) != Float.floatToIntBits(other.m00()))
            return false;
        if (Float.floatToIntBits(m01) != Float.floatToIntBits(other.m01()))
            return false;
        if (Float.floatToIntBits(m02) != Float.floatToIntBits(other.m02()))
            return false;
        if (Float.floatToIntBits(m10) != Float.floatToIntBits(other.m10()))
            return false;
        if (Float.floatToIntBits(m11) != Float.floatToIntBits(other.m11()))
            return false;
        if (Float.floatToIntBits(m12) != Float.floatToIntBits(other.m12()))
            return false;
        if (Float.floatToIntBits(m20) != Float.floatToIntBits(other.m20()))
            return false;
        if (Float.floatToIntBits(m21) != Float.floatToIntBits(other.m21()))
            return false;
        if (Float.floatToIntBits(m22) != Float.floatToIntBits(other.m22()))
            return false;
        return true;
    }

    @Override
    public Matrix3fc swap(Matrix3fc other) {
        float tmp;
        tmp = m00; m00 = other.m00(); other.m00(tmp);
        tmp = m01; m01 = other.m01(); other.m01(tmp);
        tmp = m02; m02 = other.m02(); other.m02(tmp);
        tmp = m10; m10 = other.m10(); other.m10(tmp);
        tmp = m11; m11 = other.m11(); other.m11(tmp);
        tmp = m12; m12 = other.m12(); other.m12(tmp);
        tmp = m20; m20 = other.m20(); other.m20(tmp);
        tmp = m21; m21 = other.m21(); other.m21(tmp);
        tmp = m22; m22 = other.m22(); other.m22(tmp);
        return this;
    }

    @Override
    public Matrix3fc add(IMatrix3f other) {
        return add(other, this);
    }

    @Override
    public Matrix3fc add(IMatrix3f other, Matrix3fc dest) {
        dest.m00(m00 + other.m00());
        dest.m01(m01 + other.m01());
        dest.m02(m02 + other.m02());
        dest.m10(m10 + other.m10());
        dest.m11(m11 + other.m11());
        dest.m12(m12 + other.m12());
        dest.m20(m20 + other.m20());
        dest.m21(m21 + other.m21());
        dest.m22(m22 + other.m22());
        return dest;
    }

    @Override
    public Matrix3fc sub(IMatrix3f subtrahend) {
        return sub(subtrahend, this);
    }

    @Override
    public Matrix3fc sub(IMatrix3f subtrahend, Matrix3fc dest) {
        dest.m00(m00 - subtrahend.m00());
        dest.m01(m01 - subtrahend.m01());
        dest.m02(m02 - subtrahend.m02());
        dest.m10(m10 - subtrahend.m10());
        dest.m11(m11 - subtrahend.m11());
        dest.m12(m12 - subtrahend.m12());
        dest.m20(m20 - subtrahend.m20());
        dest.m21(m21 - subtrahend.m21());
        dest.m22(m22 - subtrahend.m22());
        return dest;
    }

    @Override
    public Matrix3fc mulComponentWise(IMatrix3f other) {
        return mulComponentWise(other, this);
    }

    @Override
    public Matrix3fc mulComponentWise(IMatrix3f other, Matrix3fc dest) {
        dest.m00(m00 * other.m00());
        dest.m01(m01 * other.m01());
        dest.m02(m02 * other.m02());
        dest.m10(m10 * other.m10());
        dest.m11(m11 * other.m11());
        dest.m12(m12 * other.m12());
        dest.m20(m20 * other.m20());
        dest.m21(m21 * other.m21());
        dest.m22(m22 * other.m22());
        return dest;
    }

    @Override
    public Matrix3fc setSkewSymmetric(float a, float b, float c) {
        m00 = m11 = m22 = 0;
        m01 = -a;
        m02 = b;
        m10 = a;
        m12 = -c;
        m20 = -b;
        m21 = c;
        return this;
    }

    @Override
    public Matrix3fc lerp(IMatrix3f other, float t) {
        return lerp(other, t, this);
    }

    @Override
    public Matrix3fc lerp(IMatrix3f other, float t, Matrix3fc dest) {
        dest.m00(m00 + (other.m00() - m00) * t);
        dest.m01(m01 + (other.m01() - m01) * t);
        dest.m02(m02 + (other.m02() - m02) * t);
        dest.m10(m10 + (other.m10() - m10) * t);
        dest.m11(m11 + (other.m11() - m11) * t);
        dest.m12(m12 + (other.m12() - m12) * t);
        dest.m20(m20 + (other.m20() - m20) * t);
        dest.m21(m21 + (other.m21() - m21) * t);
        dest.m22(m22 + (other.m22() - m22) * t);
        return dest;
    }

    @Override
    public Matrix3fc rotateTowards(IVector3f direction, IVector3f up, Matrix3fc dest) {
        return rotateTowards(direction.x(), direction.y(), direction.z(), up.x(), up.y(), up.z(), dest);
    }

    @Override
    public Matrix3fc rotateTowards(IVector3f direction, IVector3f up) {
        return rotateTowards(direction.x(), direction.y(), direction.z(), up.x(), up.y(), up.z(), this);
    }

    @Override
    public Matrix3fc rotateTowards(float dirX, float dirY, float dirZ, float upX, float upY, float upZ) {
        return rotateTowards(dirX, dirY, dirZ, upX, upY, upZ, this);
    }

    @Override
    public Matrix3fc rotateTowards(float dirX, float dirY, float dirZ, float upX, float upY, float upZ, Matrix3fc dest) {
        // Normalize direction
        float invDirLength = 1.0f / (float) Math.sqrt(dirX * dirX + dirY * dirY + dirZ * dirZ);
        float ndirX = dirX * invDirLength;
        float ndirY = dirY * invDirLength;
        float ndirZ = dirZ * invDirLength;
        // left = up x direction
        float leftX, leftY, leftZ;
        leftX = upY * ndirZ - upZ * ndirY;
        leftY = upZ * ndirX - upX * ndirZ;
        leftZ = upX * ndirY - upY * ndirX;
        // normalize left
        float invLeftLength = 1.0f / (float) Math.sqrt(leftX * leftX + leftY * leftY + leftZ * leftZ);
        leftX *= invLeftLength;
        leftY *= invLeftLength;
        leftZ *= invLeftLength;
        // up = direction x left
        float upnX = ndirY * leftZ - ndirZ * leftY;
        float upnY = ndirZ * leftX - ndirX * leftZ;
        float upnZ = ndirX * leftY - ndirY * leftX;
        float rm00 = leftX;
        float rm01 = leftY;
        float rm02 = leftZ;
        float rm10 = upnX;
        float rm11 = upnY;
        float rm12 = upnZ;
        float rm20 = ndirX;
        float rm21 = ndirY;
        float rm22 = ndirZ;
        float nm00 = m00 * rm00 + m10 * rm01 + m20 * rm02;
        float nm01 = m01 * rm00 + m11 * rm01 + m21 * rm02;
        float nm02 = m02 * rm00 + m12 * rm01 + m22 * rm02;
        float nm10 = m00 * rm10 + m10 * rm11 + m20 * rm12;
        float nm11 = m01 * rm10 + m11 * rm11 + m21 * rm12;
        float nm12 = m02 * rm10 + m12 * rm11 + m22 * rm12;
        dest.m20(m00 * rm20 + m10 * rm21 + m20 * rm22);
        dest.m21(m01 * rm20 + m11 * rm21 + m21 * rm22);
        dest.m22(m02 * rm20 + m12 * rm21 + m22 * rm22);
        dest.m00(nm00);
        dest.m01(nm01);
        dest.m02(nm02);
        dest.m10(nm10);
        dest.m11(nm11);
        dest.m12(nm12);
        return dest;
    }

    @Override
    public Matrix3fc rotationTowards(IVector3f dir, IVector3f up) {
        return rotationTowards(dir.x(), dir.y(), dir.z(), up.x(), up.y(), up.z());
    }

    @Override
    public Matrix3fc rotationTowards(float dirX, float dirY, float dirZ, float upX, float upY, float upZ) {
        // Normalize direction
        float invDirLength = 1.0f / (float) Math.sqrt(dirX * dirX + dirY * dirY + dirZ * dirZ);
        float ndirX = dirX * invDirLength;
        float ndirY = dirY * invDirLength;
        float ndirZ = dirZ * invDirLength;
        // left = up x direction
        float leftX, leftY, leftZ;
        leftX = upY * ndirZ - upZ * ndirY;
        leftY = upZ * ndirX - upX * ndirZ;
        leftZ = upX * ndirY - upY * ndirX;
        // normalize left
        float invLeftLength = 1.0f / (float) Math.sqrt(leftX * leftX + leftY * leftY + leftZ * leftZ);
        leftX *= invLeftLength;
        leftY *= invLeftLength;
        leftZ *= invLeftLength;
        // up = direction x left
        float upnX = ndirY * leftZ - ndirZ * leftY;
        float upnY = ndirZ * leftX - ndirX * leftZ;
        float upnZ = ndirX * leftY - ndirY * leftX;
        this.m00 = leftX;
        this.m01 = leftY;
        this.m02 = leftZ;
        this.m10 = upnX;
        this.m11 = upnY;
        this.m12 = upnZ;
        this.m20 = ndirX;
        this.m21 = ndirY;
        this.m22 = ndirZ;
        return this;
    }

    @Override
    public Vector3fc getEulerAnglesZYX(Vector3fc dest) {
        dest.set((float) Math.atan2(m12, m22),
                (float) Math.atan2(-m02, (float) Math.sqrt(m12 * m12 + m22 * m22)),
                (float) Math.atan2(m01, m00));
        return dest;
    }
}
