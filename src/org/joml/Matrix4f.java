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
import org.joml.api.Planefc;
import org.joml.api.matrix.*;
import org.joml.api.quaternion.IQuaterniond;
import org.joml.api.quaternion.IQuaternionf;
import org.joml.api.quaternion.Quaterniondc;
import org.joml.api.quaternion.Quaternionfc;
import org.joml.api.vector.*;
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
 * Contains the definition of a 4x4 matrix of floats, and associated functions to transform
 * it. The matrix is column-major to match OpenGL's interpretation, and it looks like this:
 * <p>
 *      m00  m10  m20  m30<br>
 *      m01  m11  m21  m31<br>
 *      m02  m12  m22  m32<br>
 *      m03  m13  m23  m33<br>
 * 
 * @author Richard Greenlees
 * @author Kai Burjack
 */
public class Matrix4f extends Matrix4fc implements Externalizable {

    private static final long serialVersionUID = 1L;

    float m00, m01, m02, m03;
    float m10, m11, m12, m13;
    float m20, m21, m22, m23;
    float m30, m31, m32, m33;

    byte properties;

    /**
     * Create a new {@link Matrix4f} and set it to {@link #identity() identity}.
     */
    public Matrix4f() {
        m00 = 1.0f;
        m11 = 1.0f;
        m22 = 1.0f;
        m33 = 1.0f;
        properties = PROPERTY_IDENTITY | PROPERTY_AFFINE | PROPERTY_TRANSLATION;
    }

    /**
     * Create a new {@link Matrix4f} by setting its uppper left 3x3 submatrix to the values of the given {@link IMatrix3f}
     * and the rest to identity.
     * 
     * @param mat
     *          the {@link IMatrix3f}
     */
    public Matrix4f(IMatrix3f mat) {
        if (mat instanceof Matrix3f) {
            MemUtil.INSTANCE.copy3x3((Matrix3f) mat, this);
        } else {
            set3x3Matrix3fc(mat);
        }
        m33 = 1.0f;
        properties = PROPERTY_AFFINE;
    }

    /**
     * Create a new {@link Matrix4f} and make it a copy of the given matrix.
     * 
     * @param mat
     *          the {@link IMatrix4f} to copy the values from
     */
    public Matrix4f(IMatrix4f mat) {
        if (mat instanceof Matrix4f) {
            MemUtil.INSTANCE.copy((Matrix4f) mat, this);
        } else {
            setMatrix4fc(mat);
        }
        properties = mat.properties();
    }

    /**
     * Create a new {@link Matrix4f} and set its upper 4x3 submatrix to the given matrix <code>mat</code>
     * and all other elements to identity.
     * 
     * @param mat
     *          the {@link IMatrix4x3f} to copy the values from
     */
    public Matrix4f(IMatrix4x3f mat) {
        if (mat instanceof Matrix4x3f) {
            MemUtil.INSTANCE.copy4x3((Matrix4x3f) mat, this);
        } else {
            set4x3Matrix4x3fc(mat);
        }
        this.m33 = 1.0f;
        properties = (byte) (mat.properties() | PROPERTY_AFFINE);
    }

    /**
     * Create a new {@link Matrix4f} and make it a copy of the given matrix.
     * <p>
     * Note that due to the given {@link IMatrix4d} storing values in double-precision and the constructed {@link Matrix4f} storing them
     * in single-precision, there is the possibility of losing precision.
     * 
     * @param mat
     *          the {@link IMatrix4d} to copy the values from
     */
    public Matrix4f(IMatrix4d mat) {
        m00 = (float) mat.m00();
        m01 = (float) mat.m01();
        m02 = (float) mat.m02();
        m03 = (float) mat.m03();
        m10 = (float) mat.m10();
        m11 = (float) mat.m11();
        m12 = (float) mat.m12();
        m13 = (float) mat.m13();
        m20 = (float) mat.m20();
        m21 = (float) mat.m21();
        m22 = (float) mat.m22();
        m23 = (float) mat.m23();
        m30 = (float) mat.m30();
        m31 = (float) mat.m31();
        m32 = (float) mat.m32();
        m33 = (float) mat.m33();
        properties = mat.properties();
    }

    /**
     * Create a new 4x4 matrix using the supplied float values.
     * <p>
     * The matrix layout will be:<br><br>
     *   m00, m10, m20, m30<br>
     *   m01, m11, m21, m31<br>
     *   m02, m12, m22, m32<br>
     *   m03, m13, m23, m33
     * 
     * @param m00
     *          the value of m00
     * @param m01
     *          the value of m01
     * @param m02
     *          the value of m02
     * @param m03
     *          the value of m03
     * @param m10
     *          the value of m10
     * @param m11
     *          the value of m11
     * @param m12
     *          the value of m12
     * @param m13
     *          the value of m13
     * @param m20
     *          the value of m20
     * @param m21
     *          the value of m21
     * @param m22
     *          the value of m22
     * @param m23
     *          the value of m23
     * @param m30
     *          the value of m30
     * @param m31
     *          the value of m31
     * @param m32
     *          the value of m32
     * @param m33
     *          the value of m33
     */
    public Matrix4f(float m00, float m01, float m02, float m03, 
                    float m10, float m11, float m12, float m13, 
                    float m20, float m21, float m22, float m23,
                    float m30, float m31, float m32, float m33) {
        this._m00(m00);
        this._m01(m01);
        this._m02(m02);
        this._m03(m03);
        this._m10(m10);
        this._m11(m11);
        this._m12(m12);
        this._m13(m13);
        this._m20(m20);
        this._m21(m21);
        this._m22(m22);
        this._m23(m23);
        this._m30(m30);
        this._m31(m31);
        this._m32(m32);
        this._m33(m33);
        properties = 0;
    }

//#ifdef __HAS_NIO__
    /**
     * Create a new {@link Matrix4f} by reading its 16 float components from the given {@link FloatBuffer}
     * at the buffer's current position.
     * <p>
     * That FloatBuffer is expected to hold the values in column-major order.
     * <p>
     * The buffer's position will not be changed by this method.
     * 
     * @param buffer
     *          the {@link FloatBuffer} to read the matrix values from
     */
    public Matrix4f(FloatBuffer buffer) {
        MemUtil.INSTANCE.get(this, buffer.position(), buffer);
    }
//#endif

    /**
     * Create a new {@link Matrix4f} and initialize its four columns using the supplied vectors.
     * 
     * @param col0
     *          the first column
     * @param col1
     *          the second column
     * @param col2
     *          the third column
     * @param col3
     *          the fourth column
     */
    public Matrix4f(IVector4f col0, IVector4f col1, IVector4f col2, IVector4f col3) {
        if (col0 instanceof Vector4f &&
            col1 instanceof Vector4f &&
            col2 instanceof Vector4f &&
            col3 instanceof Vector4f) {
            MemUtil.INSTANCE.set(this, (Vector4f) col0, (Vector4f) col1, (Vector4f) col2, (Vector4f) col3);
        } else {
            setVector4fc(col0, col1, col2, col3);
        }
    }

    @Override
    public void properties(int properties) {
        this.properties = (byte) properties;
    }

    @Override
    public Matrix4fc assumeNothing() {
        this.properties(0);
        return this;
    }

    @Override
    public Matrix4fc assumeAffine() {
        this.properties(PROPERTY_AFFINE);
        return this;
    }

    @Override
    public Matrix4fc assumePerspective() {
        this.properties(PROPERTY_PERSPECTIVE);
        return this;
    }

    @Override
    public byte properties() {
        return properties;
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
    public float m03() {
        return m03;
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
    public float m13() {
        return m13;
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
    public float m23() {
        return m23;
    }

    @Override
    public float m30() {
        return m30;
    }

    @Override
    public float m31() {
        return m31;
    }

    @Override
    public float m32() {
        return m32;
    }

    @Override
    public float m33() {
        return m33;
    }

    @Override
    public Matrix4fc m00(float m00) {
        this.m00 = m00;
        properties &= ~(PROPERTY_IDENTITY | PROPERTY_TRANSLATION);
        return this;
    }

    @Override
    public Matrix4fc m01(float m01) {
        this.m01 = m01;
        properties &= ~(PROPERTY_IDENTITY | PROPERTY_PERSPECTIVE | PROPERTY_TRANSLATION);
        return this;
    }

    @Override
    public Matrix4fc m02(float m02) {
        this.m02 = m02;
        properties &= ~(PROPERTY_IDENTITY | PROPERTY_PERSPECTIVE | PROPERTY_TRANSLATION);
        return this;
    }

    @Override
    public Matrix4fc m03(float m03) {
        this.m03 = m03;
        this.properties(0);
        return this;
    }

    @Override
    public Matrix4fc m10(float m10) {
        this.m10 = m10;
        properties &= ~(PROPERTY_IDENTITY | PROPERTY_PERSPECTIVE | PROPERTY_TRANSLATION);
        return this;
    }

    @Override
    public Matrix4fc m11(float m11) {
        this.m11 = m11;
        properties &= ~(PROPERTY_IDENTITY | PROPERTY_TRANSLATION);
        return this;
    }

    @Override
    public Matrix4fc m12(float m12) {
        this.m12 = m12;
        properties &= ~(PROPERTY_IDENTITY | PROPERTY_PERSPECTIVE | PROPERTY_TRANSLATION);
        return this;
    }

    @Override
    public Matrix4fc m13(float m13) {
        this.m13 = m13;
        this.properties(0);
        return this;
    }

    @Override
    public Matrix4fc m20(float m20) {
        this.m20 = m20;
        properties &= ~(PROPERTY_IDENTITY | PROPERTY_PERSPECTIVE | PROPERTY_TRANSLATION);
        return this;
    }

    @Override
    public Matrix4fc m21(float m21) {
        this.m21 = m21;
        properties &= ~(PROPERTY_IDENTITY | PROPERTY_PERSPECTIVE | PROPERTY_TRANSLATION);
        return this;
    }

    @Override
    public Matrix4fc m22(float m22) {
        this.m22 = m22;
        properties &= ~(PROPERTY_IDENTITY | PROPERTY_TRANSLATION);
        return this;
    }

    @Override
    public Matrix4fc m23(float m23) {
        this.m23 = m23;
        properties &= ~(PROPERTY_IDENTITY | PROPERTY_AFFINE | PROPERTY_TRANSLATION);
        return this;
    }

    @Override
    public Matrix4fc m30(float m30) {
        this.m30 = m30;
        properties &= ~(PROPERTY_IDENTITY | PROPERTY_PERSPECTIVE);
        return this;
    }

    @Override
    public Matrix4fc m31(float m31) {
        this.m31 = m31;
        properties &= ~(PROPERTY_IDENTITY | PROPERTY_PERSPECTIVE);
        return this;
    }

    @Override
    public Matrix4fc m32(float m32) {
        this.m32 = m32;
        properties &= ~(PROPERTY_IDENTITY | PROPERTY_PERSPECTIVE);
        return this;
    }

    @Override
    public Matrix4fc m33(float m33) {
        this.m33 = m33;
        this.properties(0);
        return this;
    }

    @Override
    protected void _m00(float m00) {
        this.m00 = m00;
    }

    @Override
    protected void _m01(float m01) {
        this.m01 = m01;
    }

    @Override
    protected void _m02(float m02) {
        this.m02 = m02;
    }

    @Override
    protected void _m03(float m03) {
        this.m03 = m03;
    }

    @Override
    protected void _m10(float m10) {
        this.m10 = m10;
    }

    @Override
    protected void _m11(float m11) {
        this.m11 = m11;
    }

    @Override
    protected void _m12(float m12) {
        this.m12 = m12;
    }

    @Override
    protected void _m13(float m13) {
        this.m13 = m13;
    }

    @Override
    protected void _m20(float m20) {
        this.m20 = m20;
    }

    @Override
    protected void _m21(float m21) {
        this.m21 = m21;
    }

    @Override
    protected void _m22(float m22) {
        this.m22 = m22;
    }

    @Override
    protected void _m23(float m23) {
        this.m23 = m23;
    }

    @Override
    protected void _m30(float m30) {
        this.m30 = m30;
    }

    @Override
    protected void _m31(float m31) {
        this.m31 = m31;
    }

    @Override
    protected void _m32(float m32) {
        this.m32 = m32;
    }

    @Override
    protected void _m33(float m33) {
        this.m33 = m33;
    }

    @Override
    public Matrix4fc identity() {
        if ((properties & PROPERTY_IDENTITY) != 0)
            return this;
        MemUtil.INSTANCE.identity(this);
        this.properties(PROPERTY_IDENTITY | PROPERTY_AFFINE | PROPERTY_TRANSLATION);
        return this;
    }

    @Override
    public Matrix4fc set(IMatrix4f m) {
        if (m instanceof Matrix4f) {
            MemUtil.INSTANCE.copy((Matrix4f) m, this);
        } else {
            setMatrix4fc(m);
        }
        this.properties(m.properties());
        return this;
    }

    private void setMatrix4fc(IMatrix4f mat) {
        _m00(mat.m00());
        _m01(mat.m01());
        _m02(mat.m02());
        _m03(mat.m03());
        _m10(mat.m10());
        _m11(mat.m11());
        _m12(mat.m12());
        _m13(mat.m13());
        _m20(mat.m20());
        _m21(mat.m21());
        _m22(mat.m22());
        _m23(mat.m23());
        _m30(mat.m30());
        _m31(mat.m31());
        _m32(mat.m32());
        _m33(mat.m33());
    }

    @Override
    public Matrix4fc set(IMatrix4x3f m) {
        if (m instanceof Matrix4x3f) {
            MemUtil.INSTANCE.copy((Matrix4x3f) m, this);
        } else {
            setMatrix4x3fc(m);
        }
        this.properties((byte) (m.properties() | PROPERTY_AFFINE));
        return this;
    }

    private void setMatrix4x3fc(IMatrix4x3f mat) {
        _m00(mat.m00());
        _m01(mat.m01());
        _m02(mat.m02());
        _m03(0.0f);
        _m10(mat.m10());
        _m11(mat.m11());
        _m12(mat.m12());
        _m13(0.0f);
        _m20(mat.m20());
        _m21(mat.m21());
        _m22(mat.m22());
        _m23(0.0f);
        _m30(mat.m30());
        _m31(mat.m31());
        _m32(mat.m32());
        _m33(1.0f);
    }

    @Override
    public Matrix4fc set(IMatrix4d m) {
        this._m00((float) m.m00());
        this._m01((float) m.m01());
        this._m02((float) m.m02());
        this._m03((float) m.m03());
        this._m10((float) m.m10());
        this._m11((float) m.m11());
        this._m12((float) m.m12());
        this._m13((float) m.m13());
        this._m20((float) m.m20());
        this._m21((float) m.m21());
        this._m22((float) m.m22());
        this._m23((float) m.m23());
        this._m30((float) m.m30());
        this._m31((float) m.m31());
        this._m32((float) m.m32());
        this._m33((float) m.m33());
        this.properties(m.properties());
        return this;
    }

    @Override
    public Matrix4fc set(IMatrix3f mat) {
        if (mat instanceof Matrix3f) {
            MemUtil.INSTANCE.copy((Matrix3f) mat, this);
        } else {
            setMatrix3fc(mat);
        }
        this.properties(PROPERTY_AFFINE);
        return this;
    }

    private void setMatrix3fc(IMatrix3f mat) {
        m00 = mat.m00();
        m01 = mat.m01();
        m02 = mat.m02();
        m03 = 0.0f;
        m10 = mat.m10();
        m11 = mat.m11();
        m12 = mat.m12();
        m13 = 0.0f;
        m20 = mat.m20();
        m21 = mat.m21();
        m22 = mat.m22();
        m23 = 0.0f;
        m30 = 0.0f;
        m31 = 0.0f;
        m32 = 0.0f;
        m33 = 1.0f;
    }

    @Override
    public Matrix4fc set(AxisAngle4fc axisAngle) {
        float x = axisAngle.x();
        float y = axisAngle.y();
        float z = axisAngle.z();
        double angle = axisAngle.angle();
        double n = Math.sqrt(x*x + y*y + z*z);
        n = 1/n;
        x *= n;
        y *= n;
        z *= n;
        double s = Math.sin(angle);
        double c = Math.cosFromSin(s, angle);
        double omc = 1.0 - c;
        this._m00((float)(c + x*x*omc));
        this._m11((float)(c + y*y*omc));
        this._m22((float)(c + z*z*omc));
        double tmp1 = x*y*omc;
        double tmp2 = z*s;
        this._m10((float)(tmp1 - tmp2));
        this._m01((float)(tmp1 + tmp2));
        tmp1 = x*z*omc;
        tmp2 = y*s;
        this._m20((float)(tmp1 + tmp2));
        this._m02((float)(tmp1 - tmp2));
        tmp1 = y*z*omc;
        tmp2 = x*s;
        this._m21((float)(tmp1 - tmp2));
        this._m12((float)(tmp1 + tmp2));
        this._m03(0.0f);
        this._m13(0.0f);
        this._m23(0.0f);
        this._m30(0.0f);
        this._m31(0.0f);
        this._m32(0.0f);
        this._m33(1.0f);
        this.properties(PROPERTY_AFFINE);
        return this;
    }

    @Override
    public Matrix4fc set(AxisAngle4dc axisAngle) {
        double x = axisAngle.x();
        double y = axisAngle.y();
        double z = axisAngle.z();
        double angle = axisAngle.angle();
        double n = Math.sqrt(x*x + y*y + z*z);
        n = 1/n;
        x *= n;
        y *= n;
        z *= n;
        double s = Math.sin(angle);
        double c = Math.cosFromSin(s, angle);
        double omc = 1.0 - c;
        this._m00((float)(c + x*x*omc));
        this._m11((float)(c + y*y*omc));
        this._m22((float)(c + z*z*omc));
        double tmp1 = x*y*omc;
        double tmp2 = z*s;
        this._m10((float)(tmp1 - tmp2));
        this._m01((float)(tmp1 + tmp2));
        tmp1 = x*z*omc;
        tmp2 = y*s;
        this._m20((float)(tmp1 + tmp2));
        this._m02((float)(tmp1 - tmp2));
        tmp1 = y*z*omc;
        tmp2 = x*s;
        this._m21((float)(tmp1 - tmp2));
        this._m12((float)(tmp1 + tmp2));
        this._m03(0.0f);
        this._m13(0.0f);
        this._m23(0.0f);
        this._m30(0.0f);
        this._m31(0.0f);
        this._m32(0.0f);
        this._m33(1.0f);
        this.properties(PROPERTY_AFFINE);
        return this;
    }

    @Override
    public Matrix4fc set(IQuaternionf q) {
        return rotation(q);
    }

    @Override
    public Matrix4fc set(IQuaterniond q) {
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
        _m00((float) (w2 + x2 - z2 - y2));
        _m01((float) (xy + zw + zw + xy));
        _m02((float) (xz - yw + xz - yw));
        _m03(0.0f);
        _m10((float) (-zw + xy - zw + xy));
        _m11((float) (y2 - z2 + w2 - x2));
        _m12((float) (yz + yz + xw + xw));
        _m13(0.0f);
        _m20((float) (yw + xz + xz + yw));
        _m21((float) (yz + yz - xw - xw));
        _m22((float) (z2 - y2 - x2 + w2));
        _m30(0.0f);
        _m31(0.0f);
        _m32(0.0f);
        _m33(1.0f);
        this.properties(PROPERTY_AFFINE);
        return this;
    }

    @Override
    public Matrix4fc set3x3(Matrix4fc mat) {
        if (mat instanceof Matrix4f) {
            MemUtil.INSTANCE.copy3x3((Matrix4f) mat, this);
        } else {
            this.m00 = mat.m00();
            this.m01 = mat.m01();
            this.m02 = mat.m02();
            this.m10 = mat.m10();
            this.m11 = mat.m11();
            this.m12 = mat.m12();
            this.m20 = mat.m20();
            this.m21 = mat.m21();
            this.m22 = mat.m22();
        }
        properties &= mat.properties() & ~(PROPERTY_PERSPECTIVE);
        return this;
    }

    @Override
    public Matrix4fc set4x3(IMatrix4x3f mat) {
        if (mat instanceof Matrix4x3f) {
            MemUtil.INSTANCE.copy4x3((Matrix4x3f) mat, this);
        } else {
            set4x3Matrix4x3fc(mat);
        }
        properties &= mat.properties() & ~(PROPERTY_PERSPECTIVE);
        return this;
    }

    private void set4x3Matrix4x3fc(IMatrix4x3f mat) {
        _m00(mat.m00());
        _m01(mat.m01());
        _m02(mat.m02());
        _m10(mat.m10());
        _m11(mat.m11());
        _m12(mat.m12());
        _m20(mat.m20());
        _m21(mat.m21());
        _m22(mat.m22());
        _m30(mat.m30());
        _m31(mat.m31());
        _m32(mat.m32());
    }

    @Override
    public Matrix4fc set4x3(Matrix4fc mat) {
        if (mat instanceof Matrix4f) {
            MemUtil.INSTANCE.copy4x3((Matrix4f) mat, this);
        } else {
            this.m00 = mat.m00();
            this.m01 = mat.m01();
            this.m02 = mat.m02();
            this.m10 = mat.m10();
            this.m11 = mat.m11();
            this.m12 = mat.m12();
            this.m20 = mat.m20();
            this.m21 = mat.m21();
            this.m22 = mat.m22();
            this.m30 = mat.m30();
            this.m31 = mat.m31();
            this.m32 = mat.m32();
        }
        properties &= mat.properties() & ~(PROPERTY_PERSPECTIVE);
        return this;
    }

    @Override
    public Matrix4fc mul(IMatrix4f right) {
       return mul(right, this);
    }

    @Override
    public Matrix4fc mul(IMatrix4f right, Matrix4fc dest) {
        if ((properties & PROPERTY_IDENTITY) != 0)
            return dest.set(right);
        else if ((right.properties() & PROPERTY_IDENTITY) != 0)
            return dest.set(this);
        else if ((properties & PROPERTY_TRANSLATION) != 0 && (right.properties() & PROPERTY_AFFINE) != 0)
            return mulTranslationAffine(right, dest);
        else if ((properties & PROPERTY_AFFINE) != 0 && (right.properties() & PROPERTY_AFFINE) != 0)
            return mulAffine(right, dest);
        else if ((properties & PROPERTY_PERSPECTIVE) != 0 && (right.properties() & PROPERTY_AFFINE) != 0)
            return mulPerspectiveAffine(right, dest);
        else if ((right.properties() & PROPERTY_AFFINE) != 0)
            return mulAffineR(right, dest);
        return mulGeneric(right, dest);
    }

    private Matrix4fc mulGeneric(IMatrix4f right, Matrix4fc dest) {
        float nm00 = m00 * right.m00() + m10 * right.m01() + m20 * right.m02() + m30 * right.m03();
        float nm01 = m01 * right.m00() + m11 * right.m01() + m21 * right.m02() + m31 * right.m03();
        float nm02 = m02 * right.m00() + m12 * right.m01() + m22 * right.m02() + m32 * right.m03();
        float nm03 = m03 * right.m00() + m13 * right.m01() + m23 * right.m02() + m33 * right.m03();
        float nm10 = m00 * right.m10() + m10 * right.m11() + m20 * right.m12() + m30 * right.m13();
        float nm11 = m01 * right.m10() + m11 * right.m11() + m21 * right.m12() + m31 * right.m13();
        float nm12 = m02 * right.m10() + m12 * right.m11() + m22 * right.m12() + m32 * right.m13();
        float nm13 = m03 * right.m10() + m13 * right.m11() + m23 * right.m12() + m33 * right.m13();
        float nm20 = m00 * right.m20() + m10 * right.m21() + m20 * right.m22() + m30 * right.m23();
        float nm21 = m01 * right.m20() + m11 * right.m21() + m21 * right.m22() + m31 * right.m23();
        float nm22 = m02 * right.m20() + m12 * right.m21() + m22 * right.m22() + m32 * right.m23();
        float nm23 = m03 * right.m20() + m13 * right.m21() + m23 * right.m22() + m33 * right.m23();
        float nm30 = m00 * right.m30() + m10 * right.m31() + m20 * right.m32() + m30 * right.m33();
        float nm31 = m01 * right.m30() + m11 * right.m31() + m21 * right.m32() + m31 * right.m33();
        float nm32 = m02 * right.m30() + m12 * right.m31() + m22 * right.m32() + m32 * right.m33();
        float nm33 = m03 * right.m30() + m13 * right.m31() + m23 * right.m32() + m33 * right.m33();
        dest.m00(nm00);
        dest.m01(nm01);
        dest.m02(nm02);
        dest.m03(nm03);
        dest.m10(nm10);
        dest.m11(nm11);
        dest.m12(nm12);
        dest.m13(nm13);
        dest.m20(nm20);
        dest.m21(nm21);
        dest.m22(nm22);
        dest.m23(nm23);
        dest.m30(nm30);
        dest.m31(nm31);
        dest.m32(nm32);
        dest.m33(nm33);
        dest.properties(0);
        return dest;
    }

    @Override
    public Matrix4fc mulLocal(IMatrix4f left) {
       return mulLocal(left, this);
    }

    @Override
    public Matrix4fc mulLocal(IMatrix4f left, Matrix4fc dest) {
        if ((properties & PROPERTY_IDENTITY) != 0)
            return dest.set(left);
        else if ((left.properties() & PROPERTY_IDENTITY) != 0)
            return dest.set(this);
        else if ((properties & PROPERTY_AFFINE) != 0 && (left.properties() & PROPERTY_AFFINE) != 0)
            return mulLocalAffine(left, dest);
        return mulLocalGeneric(left, dest);
    }

    private Matrix4fc mulLocalGeneric(IMatrix4f left, Matrix4fc dest) {
        float nm00 = left.m00() * m00 + left.m10() * m01 + left.m20() * m02 + left.m30() * m03;
        float nm01 = left.m01() * m00 + left.m11() * m01 + left.m21() * m02 + left.m31() * m03;
        float nm02 = left.m02() * m00 + left.m12() * m01 + left.m22() * m02 + left.m32() * m03;
        float nm03 = left.m03() * m00 + left.m13() * m01 + left.m23() * m02 + left.m33() * m03;
        float nm10 = left.m00() * m10 + left.m10() * m11 + left.m20() * m12 + left.m30() * m13;
        float nm11 = left.m01() * m10 + left.m11() * m11 + left.m21() * m12 + left.m31() * m13;
        float nm12 = left.m02() * m10 + left.m12() * m11 + left.m22() * m12 + left.m32() * m13;
        float nm13 = left.m03() * m10 + left.m13() * m11 + left.m23() * m12 + left.m33() * m13;
        float nm20 = left.m00() * m20 + left.m10() * m21 + left.m20() * m22 + left.m30() * m23;
        float nm21 = left.m01() * m20 + left.m11() * m21 + left.m21() * m22 + left.m31() * m23;
        float nm22 = left.m02() * m20 + left.m12() * m21 + left.m22() * m22 + left.m32() * m23;
        float nm23 = left.m03() * m20 + left.m13() * m21 + left.m23() * m22 + left.m33() * m23;
        float nm30 = left.m00() * m30 + left.m10() * m31 + left.m20() * m32 + left.m30() * m33;
        float nm31 = left.m01() * m30 + left.m11() * m31 + left.m21() * m32 + left.m31() * m33;
        float nm32 = left.m02() * m30 + left.m12() * m31 + left.m22() * m32 + left.m32() * m33;
        float nm33 = left.m03() * m30 + left.m13() * m31 + left.m23() * m32 + left.m33() * m33;
        dest.m00(nm00);
        dest.m01(nm01);
        dest.m02(nm02);
        dest.m03(nm03);
        dest.m10(nm10);
        dest.m11(nm11);
        dest.m12(nm12);
        dest.m13(nm13);
        dest.m20(nm20);
        dest.m21(nm21);
        dest.m22(nm22);
        dest.m23(nm23);
        dest.m30(nm30);
        dest.m31(nm31);
        dest.m32(nm32);
        dest.m33(nm33);
        dest.properties(0);
        return dest;
    }

    @Override
    public Matrix4fc mulLocalAffine(IMatrix4f left) {
       return mulLocalAffine(left, this);
    }

    @Override
    public Matrix4fc mulLocalAffine(IMatrix4f left, Matrix4fc dest) {
        float nm00 = left.m00() * m00 + left.m10() * m01 + left.m20() * m02;
        float nm01 = left.m01() * m00 + left.m11() * m01 + left.m21() * m02;
        float nm02 = left.m02() * m00 + left.m12() * m01 + left.m22() * m02;
        float nm03 = left.m03();
        float nm10 = left.m00() * m10 + left.m10() * m11 + left.m20() * m12;
        float nm11 = left.m01() * m10 + left.m11() * m11 + left.m21() * m12;
        float nm12 = left.m02() * m10 + left.m12() * m11 + left.m22() * m12;
        float nm13 = left.m13();
        float nm20 = left.m00() * m20 + left.m10() * m21 + left.m20() * m22;
        float nm21 = left.m01() * m20 + left.m11() * m21 + left.m21() * m22;
        float nm22 = left.m02() * m20 + left.m12() * m21 + left.m22() * m22;
        float nm23 = left.m23();
        float nm30 = left.m00() * m30 + left.m10() * m31 + left.m20() * m32 + left.m30();
        float nm31 = left.m01() * m30 + left.m11() * m31 + left.m21() * m32 + left.m31();
        float nm32 = left.m02() * m30 + left.m12() * m31 + left.m22() * m32 + left.m32();
        float nm33 = left.m33();
        dest.m00(nm00);
        dest.m01(nm01);
        dest.m02(nm02);
        dest.m03(nm03);
        dest.m10(nm10);
        dest.m11(nm11);
        dest.m12(nm12);
        dest.m13(nm13);
        dest.m20(nm20);
        dest.m21(nm21);
        dest.m22(nm22);
        dest.m23(nm23);
        dest.m30(nm30);
        dest.m31(nm31);
        dest.m32(nm32);
        dest.m33(nm33);
        dest.properties(PROPERTY_AFFINE);
        return dest;
    }

    @Override
    public Matrix4fc mul(IMatrix4x3f right) {
        return mul(right, this);
    }

    @Override
    public Matrix4fc mul(IMatrix4x3f right, Matrix4fc dest) {
        if ((properties & PROPERTY_IDENTITY) != 0)
            return dest.set(right);
        else if ((right.properties() & PROPERTY_IDENTITY) != 0)
            return dest.set(this);
        else if ((properties & PROPERTY_PERSPECTIVE) != 0 && (right.properties() & PROPERTY_AFFINE) != 0)
            return mulPerspectiveAffine(right, dest);
        return mulGeneric(right, dest);
    }

    private Matrix4fc mulGeneric(IMatrix4x3f right, Matrix4fc dest) {
        float nm00 = m00 * right.m00() + m10 * right.m01() + m20 * right.m02();
        float nm01 = m01 * right.m00() + m11 * right.m01() + m21 * right.m02();
        float nm02 = m02 * right.m00() + m12 * right.m01() + m22 * right.m02();
        float nm03 = m03 * right.m00() + m13 * right.m01() + m23 * right.m02();
        float nm10 = m00 * right.m10() + m10 * right.m11() + m20 * right.m12();
        float nm11 = m01 * right.m10() + m11 * right.m11() + m21 * right.m12();
        float nm12 = m02 * right.m10() + m12 * right.m11() + m22 * right.m12();
        float nm13 = m03 * right.m10() + m13 * right.m11() + m23 * right.m12();
        float nm20 = m00 * right.m20() + m10 * right.m21() + m20 * right.m22();
        float nm21 = m01 * right.m20() + m11 * right.m21() + m21 * right.m22();
        float nm22 = m02 * right.m20() + m12 * right.m21() + m22 * right.m22();
        float nm23 = m03 * right.m20() + m13 * right.m21() + m23 * right.m22();
        float nm30 = m00 * right.m30() + m10 * right.m31() + m20 * right.m32() + m30;
        float nm31 = m01 * right.m30() + m11 * right.m31() + m21 * right.m32() + m31;
        float nm32 = m02 * right.m30() + m12 * right.m31() + m22 * right.m32() + m32;
        float nm33 = m03 * right.m30() + m13 * right.m31() + m23 * right.m32() + m33;
        dest.m00(nm00);
        dest.m01(nm01);
        dest.m02(nm02);
        dest.m03(nm03);
        dest.m10(nm10);
        dest.m11(nm11);
        dest.m12(nm12);
        dest.m13(nm13);
        dest.m20(nm20);
        dest.m21(nm21);
        dest.m22(nm22);
        dest.m23(nm23);
        dest.m30(nm30);
        dest.m31(nm31);
        dest.m32(nm32);
        dest.m33(nm33);
        dest.properties((byte) (properties & ~(PROPERTY_IDENTITY | PROPERTY_PERSPECTIVE | PROPERTY_TRANSLATION)));
        return dest;
    }

    @Override
    public Matrix4fc mul(IMatrix3x2f right) {
        return mul(right, this);
    }

    @Override
    public Matrix4fc mul(IMatrix3x2f right, Matrix4fc dest) {
        float nm00 = m00 * right.m00() + m10 * right.m01();
        float nm01 = m01 * right.m00() + m11 * right.m01();
        float nm02 = m02 * right.m00() + m12 * right.m01();
        float nm03 = m03 * right.m00() + m13 * right.m01();
        float nm10 = m00 * right.m10() + m10 * right.m11();
        float nm11 = m01 * right.m10() + m11 * right.m11();
        float nm12 = m02 * right.m10() + m12 * right.m11();
        float nm13 = m03 * right.m10() + m13 * right.m11();
        float nm30 = m00 * right.m20() + m10 * right.m21() + m30;
        float nm31 = m01 * right.m20() + m11 * right.m21() + m31;
        float nm32 = m02 * right.m20() + m12 * right.m21() + m32;
        float nm33 = m03 * right.m20() + m13 * right.m21() + m33;
        dest.m00(nm00);
        dest.m01(nm01);
        dest.m02(nm02);
        dest.m03(nm03);
        dest.m10(nm10);
        dest.m11(nm11);
        dest.m12(nm12);
        dest.m13(nm13);
        dest.m20(m20);
        dest.m21(m21);
        dest.m22(m22);
        dest.m23(m23);
        dest.m30(nm30);
        dest.m31(nm31);
        dest.m32(nm32);
        dest.m33(nm33);
        dest.properties((byte) (properties & ~(PROPERTY_IDENTITY | PROPERTY_PERSPECTIVE | PROPERTY_TRANSLATION)));
        return dest;
    }

    @Override
    public Matrix4fc mulPerspectiveAffine(IMatrix4f view) {
       return mulPerspectiveAffine(view, this);
    }

    @Override
    public Matrix4fc mulPerspectiveAffine(IMatrix4f view, Matrix4fc dest) {
        float nm00 = m00 * view.m00();
        float nm01 = m11 * view.m01();
        float nm02 = m22 * view.m02();
        float nm03 = m23 * view.m02();
        float nm10 = m00 * view.m10();
        float nm11 = m11 * view.m11();
        float nm12 = m22 * view.m12();
        float nm13 = m23 * view.m12();
        float nm20 = m00 * view.m20();
        float nm21 = m11 * view.m21();
        float nm22 = m22 * view.m22();
        float nm23 = m23 * view.m22();
        float nm30 = m00 * view.m30();
        float nm31 = m11 * view.m31();
        float nm32 = m22 * view.m32() + m32;
        float nm33 = m23 * view.m32();
        dest.m00(nm00);
        dest.m01(nm01);
        dest.m02(nm02);
        dest.m03(nm03);
        dest.m10(nm10);
        dest.m11(nm11);
        dest.m12(nm12);
        dest.m13(nm13);
        dest.m20(nm20);
        dest.m21(nm21);
        dest.m22(nm22);
        dest.m23(nm23);
        dest.m30(nm30);
        dest.m31(nm31);
        dest.m32(nm32);
        dest.m33(nm33);
        dest.properties(0);
        return dest;
    }

    @Override
    public Matrix4fc mulPerspectiveAffine(IMatrix4x3f view) {
       return mulPerspectiveAffine(view, this);
    }

    @Override
    public Matrix4fc mulPerspectiveAffine(IMatrix4x3f view, Matrix4fc dest) {
        float nm00 = m00 * view.m00();
        float nm01 = m11 * view.m01();
        float nm02 = m22 * view.m02();
        float nm03 = m23 * view.m02();
        float nm10 = m00 * view.m10();
        float nm11 = m11 * view.m11();
        float nm12 = m22 * view.m12();
        float nm13 = m23 * view.m12();
        float nm20 = m00 * view.m20();
        float nm21 = m11 * view.m21();
        float nm22 = m22 * view.m22();
        float nm23 = m23 * view.m22();
        float nm30 = m00 * view.m30();
        float nm31 = m11 * view.m31();
        float nm32 = m22 * view.m32() + m32;
        float nm33 = m23 * view.m32();
        dest.m00(nm00);
        dest.m01(nm01);
        dest.m02(nm02);
        dest.m03(nm03);
        dest.m10(nm10);
        dest.m11(nm11);
        dest.m12(nm12);
        dest.m13(nm13);
        dest.m20(nm20);
        dest.m21(nm21);
        dest.m22(nm22);
        dest.m23(nm23);
        dest.m30(nm30);
        dest.m31(nm31);
        dest.m32(nm32);
        dest.m33(nm33);
        dest.properties(0);
        return dest;
    }

    @Override
    public Matrix4fc mulAffineR(IMatrix4f right) {
       return mulAffineR(right, this);
    }

    @Override
    public Matrix4fc mulAffineR(IMatrix4f right, Matrix4fc dest) {
        float nm00 = m00 * right.m00() + m10 * right.m01() + m20 * right.m02();
        float nm01 = m01 * right.m00() + m11 * right.m01() + m21 * right.m02();
        float nm02 = m02 * right.m00() + m12 * right.m01() + m22 * right.m02();
        float nm03 = m03 * right.m00() + m13 * right.m01() + m23 * right.m02();
        float nm10 = m00 * right.m10() + m10 * right.m11() + m20 * right.m12();
        float nm11 = m01 * right.m10() + m11 * right.m11() + m21 * right.m12();
        float nm12 = m02 * right.m10() + m12 * right.m11() + m22 * right.m12();
        float nm13 = m03 * right.m10() + m13 * right.m11() + m23 * right.m12();
        float nm20 = m00 * right.m20() + m10 * right.m21() + m20 * right.m22();
        float nm21 = m01 * right.m20() + m11 * right.m21() + m21 * right.m22();
        float nm22 = m02 * right.m20() + m12 * right.m21() + m22 * right.m22();
        float nm23 = m03 * right.m20() + m13 * right.m21() + m23 * right.m22();
        float nm30 = m00 * right.m30() + m10 * right.m31() + m20 * right.m32() + m30;
        float nm31 = m01 * right.m30() + m11 * right.m31() + m21 * right.m32() + m31;
        float nm32 = m02 * right.m30() + m12 * right.m31() + m22 * right.m32() + m32;
        float nm33 = m03 * right.m30() + m13 * right.m31() + m23 * right.m32() + m33;
        dest.m00(nm00);
        dest.m01(nm01);
        dest.m02(nm02);
        dest.m03(nm03);
        dest.m10(nm10);
        dest.m11(nm11);
        dest.m12(nm12);
        dest.m13(nm13);
        dest.m20(nm20);
        dest.m21(nm21);
        dest.m22(nm22);
        dest.m23(nm23);
        dest.m30(nm30);
        dest.m31(nm31);
        dest.m32(nm32);
        dest.m33(nm33);
        dest.properties((byte) (properties & ~(PROPERTY_IDENTITY | PROPERTY_PERSPECTIVE | PROPERTY_TRANSLATION)));
        return dest;
    }

    @Override
    public Matrix4fc mulAffine(IMatrix4f right) {
       return mulAffine(right, this);
    }

    @Override
    public Matrix4fc mulAffine(IMatrix4f right, Matrix4fc dest) {
        float nm00 = m00 * right.m00() + m10 * right.m01() + m20 * right.m02();
        float nm01 = m01 * right.m00() + m11 * right.m01() + m21 * right.m02();
        float nm02 = m02 * right.m00() + m12 * right.m01() + m22 * right.m02();
        float nm03 = m03;
        float nm10 = m00 * right.m10() + m10 * right.m11() + m20 * right.m12();
        float nm11 = m01 * right.m10() + m11 * right.m11() + m21 * right.m12();
        float nm12 = m02 * right.m10() + m12 * right.m11() + m22 * right.m12();
        float nm13 = m13;
        float nm20 = m00 * right.m20() + m10 * right.m21() + m20 * right.m22();
        float nm21 = m01 * right.m20() + m11 * right.m21() + m21 * right.m22();
        float nm22 = m02 * right.m20() + m12 * right.m21() + m22 * right.m22();
        float nm23 = m23;
        float nm30 = m00 * right.m30() + m10 * right.m31() + m20 * right.m32() + m30;
        float nm31 = m01 * right.m30() + m11 * right.m31() + m21 * right.m32() + m31;
        float nm32 = m02 * right.m30() + m12 * right.m31() + m22 * right.m32() + m32;
        float nm33 = m33;
        dest.m00(nm00);
        dest.m01(nm01);
        dest.m02(nm02);
        dest.m03(nm03);
        dest.m10(nm10);
        dest.m11(nm11);
        dest.m12(nm12);
        dest.m13(nm13);
        dest.m20(nm20);
        dest.m21(nm21);
        dest.m22(nm22);
        dest.m23(nm23);
        dest.m30(nm30);
        dest.m31(nm31);
        dest.m32(nm32);
        dest.m33(nm33);
        dest.properties(PROPERTY_AFFINE);
        return dest;
    }

    @Override
    public Matrix4fc mulTranslationAffine(IMatrix4f right, Matrix4fc dest) {
        float nm00 = right.m00();
        float nm01 = right.m01();
        float nm02 = right.m02();
        float nm03 = m03;
        float nm10 = right.m10();
        float nm11 = right.m11();
        float nm12 = right.m12();
        float nm13 = m13;
        float nm20 = right.m20();
        float nm21 = right.m21();
        float nm22 = right.m22();
        float nm23 = m23;
        float nm30 = right.m30() + m30;
        float nm31 = right.m31() + m31;
        float nm32 = right.m32() + m32;
        float nm33 = m33;
        dest.m00(nm00);
        dest.m01(nm01);
        dest.m02(nm02);
        dest.m03(nm03);
        dest.m10(nm10);
        dest.m11(nm11);
        dest.m12(nm12);
        dest.m13(nm13);
        dest.m20(nm20);
        dest.m21(nm21);
        dest.m22(nm22);
        dest.m23(nm23);
        dest.m30(nm30);
        dest.m31(nm31);
        dest.m32(nm32);
        dest.m33(nm33);
        dest.properties(PROPERTY_AFFINE);
        return dest;
    }

    @Override
    public Matrix4fc mulOrthoAffine(IMatrix4f view) {
        return mulOrthoAffine(view, this);
    }

    @Override
    public Matrix4fc mulOrthoAffine(IMatrix4f view, Matrix4fc dest) {
        float nm00 = m00 * view.m00();
        float nm01 = m11 * view.m01();
        float nm02 = m22 * view.m02();
        float nm03 = 0.0f;
        float nm10 = m00 * view.m10();
        float nm11 = m11 * view.m11();
        float nm12 = m22 * view.m12();
        float nm13 = 0.0f;
        float nm20 = m00 * view.m20();
        float nm21 = m11 * view.m21();
        float nm22 = m22 * view.m22();
        float nm23 = 0.0f;
        float nm30 = m00 * view.m30() + m30;
        float nm31 = m11 * view.m31() + m31;
        float nm32 = m22 * view.m32() + m32;
        float nm33 = 1.0f;
        dest.m00(nm00);
        dest.m01(nm01);
        dest.m02(nm02);
        dest.m03(nm03);
        dest.m10(nm10);
        dest.m11(nm11);
        dest.m12(nm12);
        dest.m13(nm13);
        dest.m20(nm20);
        dest.m21(nm21);
        dest.m22(nm22);
        dest.m23(nm23);
        dest.m30(nm30);
        dest.m31(nm31);
        dest.m32(nm32);
        dest.m33(nm33);
        dest.properties(PROPERTY_AFFINE);
        return dest;
    }

    @Override
    public Matrix4fc fma4x3(IMatrix4f other, float otherFactor) {
        return fma4x3(other, otherFactor, this);
    }

    @Override
    public Matrix4fc fma4x3(IMatrix4f other, float otherFactor, Matrix4fc dest) {
        dest.m00(m00 + other.m00() * otherFactor);
        dest.m01(m01 + other.m01() * otherFactor);
        dest.m02(m02 + other.m02() * otherFactor);
        dest.m03(m03);
        dest.m10(m10 + other.m10() * otherFactor);
        dest.m11(m11 + other.m11() * otherFactor);
        dest.m12(m12 + other.m12() * otherFactor);
        dest.m13(m13);
        dest.m20(m20 + other.m20() * otherFactor);
        dest.m21(m21 + other.m21() * otherFactor);
        dest.m22(m22 + other.m22() * otherFactor);
        dest.m23(m23);
        dest.m30(m30 + other.m30() * otherFactor);
        dest.m31(m31 + other.m31() * otherFactor);
        dest.m32(m32 + other.m32() * otherFactor);
        dest.m33(m33);
        dest.properties(0);
        return dest;
    }

    @Override
    public Matrix4fc add(IMatrix4f other) {
        return add(other, this);
    }

    @Override
    public Matrix4fc add(IMatrix4f other, Matrix4fc dest) {
        dest.m00(m00 + other.m00());
        dest.m01(m01 + other.m01());
        dest.m02(m02 + other.m02());
        dest.m03(m03 + other.m03());
        dest.m10(m10 + other.m10());
        dest.m11(m11 + other.m11());
        dest.m12(m12 + other.m12());
        dest.m13(m13 + other.m13());
        dest.m20(m20 + other.m20());
        dest.m21(m21 + other.m21());
        dest.m22(m22 + other.m22());
        dest.m23(m23 + other.m23());
        dest.m30(m30 + other.m30());
        dest.m31(m31 + other.m31());
        dest.m32(m32 + other.m32());
        dest.m33(m33 + other.m33());
        dest.properties(0);
        return dest;
    }

    @Override
    public Matrix4fc sub(IMatrix4f subtrahend) {
        return sub(subtrahend, this);
    }

    @Override
    public Matrix4fc sub(IMatrix4f subtrahend, Matrix4fc dest) {
        dest.m00(m00 - subtrahend.m00());
        dest.m01(m01 - subtrahend.m01());
        dest.m02(m02 - subtrahend.m02());
        dest.m03(m03 - subtrahend.m03());
        dest.m10(m10 - subtrahend.m10());
        dest.m11(m11 - subtrahend.m11());
        dest.m12(m12 - subtrahend.m12());
        dest.m13(m13 - subtrahend.m13());
        dest.m20(m20 - subtrahend.m20());
        dest.m21(m21 - subtrahend.m21());
        dest.m22(m22 - subtrahend.m22());
        dest.m23(m23 - subtrahend.m23());
        dest.m30(m30 - subtrahend.m30());
        dest.m31(m31 - subtrahend.m31());
        dest.m32(m32 - subtrahend.m32());
        dest.m33(m33 - subtrahend.m33());
        dest.properties(0);
        return dest;
    }

    @Override
    public Matrix4fc mulComponentWise(IMatrix4f other) {
        return mulComponentWise(other, this);
    }

    @Override
    public Matrix4fc mulComponentWise(IMatrix4f other, Matrix4fc dest) {
        dest.m00(m00 * other.m00());
        dest.m01(m01 * other.m01());
        dest.m02(m02 * other.m02());
        dest.m03(m03 * other.m03());
        dest.m10(m10 * other.m10());
        dest.m11(m11 * other.m11());
        dest.m12(m12 * other.m12());
        dest.m13(m13 * other.m13());
        dest.m20(m20 * other.m20());
        dest.m21(m21 * other.m21());
        dest.m22(m22 * other.m22());
        dest.m23(m23 * other.m23());
        dest.m30(m30 * other.m30());
        dest.m31(m31 * other.m31());
        dest.m32(m32 * other.m32());
        dest.m33(m33 * other.m33());
        dest.properties(0);
        return dest;
    }

    @Override
    public Matrix4fc add4x3(IMatrix4f other) {
        return add4x3(other, this);
    }

    @Override
    public Matrix4fc add4x3(IMatrix4f other, Matrix4fc dest) {
        dest.m00(m00 + other.m00());
        dest.m01(m01 + other.m01());
        dest.m02(m02 + other.m02());
        dest.m03(m03);
        dest.m10(m10 + other.m10());
        dest.m11(m11 + other.m11());
        dest.m12(m12 + other.m12());
        dest.m13(m13);
        dest.m20(m20 + other.m20());
        dest.m21(m21 + other.m21());
        dest.m22(m22 + other.m22());
        dest.m23(m23);
        dest.m30(m30 + other.m30());
        dest.m31(m31 + other.m31());
        dest.m32(m32 + other.m32());
        dest.m33(m33);
        dest.properties(0);
        return dest;
    }

    @Override
    public Matrix4fc sub4x3(Matrix4fc subtrahend) {
        return sub4x3(subtrahend, this);
    }

    @Override
    public Matrix4fc sub4x3(IMatrix4f subtrahend, Matrix4fc dest) {
        dest.m00(m00 - subtrahend.m00());
        dest.m01(m01 - subtrahend.m01());
        dest.m02(m02 - subtrahend.m02());
        dest.m03(m03);
        dest.m10(m10 - subtrahend.m10());
        dest.m11(m11 - subtrahend.m11());
        dest.m12(m12 - subtrahend.m12());
        dest.m13(m13);
        dest.m20(m20 - subtrahend.m20());
        dest.m21(m21 - subtrahend.m21());
        dest.m22(m22 - subtrahend.m22());
        dest.m23(m23);
        dest.m30(m30 - subtrahend.m30());
        dest.m31(m31 - subtrahend.m31());
        dest.m32(m32 - subtrahend.m32());
        dest.m33(m33);
        dest.properties(0);
        return dest;
    }

    @Override
    public Matrix4fc mul4x3ComponentWise(IMatrix4f other) {
        return mul4x3ComponentWise(other, this);
    }

    @Override
    public Matrix4fc mul4x3ComponentWise(IMatrix4f other, Matrix4fc dest) {
        dest.m00(m00 * other.m00());
        dest.m01(m01 * other.m01());
        dest.m02(m02 * other.m02());
        dest.m03(m03);
        dest.m10(m10 * other.m10());
        dest.m11(m11 * other.m11());
        dest.m12(m12 * other.m12());
        dest.m13(m13);
        dest.m20(m20 * other.m20());
        dest.m21(m21 * other.m21());
        dest.m22(m22 * other.m22());
        dest.m23(m23);
        dest.m30(m30 * other.m30());
        dest.m31(m31 * other.m31());
        dest.m32(m32 * other.m32());
        dest.m33(m33);
        dest.properties(0);
        return dest;
    }

    @Override
    public Matrix4fc set(float m00, float m01, float m02, float m03,
                        float m10, float m11, float m12, float m13,
                        float m20, float m21, float m22, float m23, 
                        float m30, float m31, float m32, float m33) {
        this._m00(m00);
        this._m10(m10);
        this._m20(m20);
        this._m30(m30);
        this._m01(m01);
        this._m11(m11);
        this._m21(m21);
        this._m31(m31);
        this._m02(m02);
        this._m12(m12);
        this._m22(m22);
        this._m32(m32);
        this._m03(m03);
        this._m13(m13);
        this._m23(m23);
        this._m33(m33);
        properties(0);
        return this;
    }

    @Override
    public Matrix4fc set(float m[], int off) {
        MemUtil.INSTANCE.copy(m, off, this);
        properties(0);
        return this;
    }

    @Override
    public Matrix4fc set(float m[]) {
        return set(m, 0);
    }

//#ifdef __HAS_NIO__
    @Override
    public Matrix4fc set(FloatBuffer buffer) {
        MemUtil.INSTANCE.get(this, buffer.position(), buffer);
        properties(0);
        return this;
    }

    @Override
    public Matrix4fc set(ByteBuffer buffer) {
        MemUtil.INSTANCE.get(this, buffer.position(), buffer);
        properties(0);
        return this;
    }
//#endif

    @Override
    public Matrix4fc set(IVector4f col0, IVector4f col1, IVector4f col2, IVector4f col3) {
        if (col0 instanceof Vector4f &&
            col1 instanceof Vector4f &&
            col2 instanceof Vector4f &&
            col3 instanceof Vector4f) {
            MemUtil.INSTANCE.set(this, (Vector4f) col0, (Vector4f) col1, (Vector4f) col2, (Vector4f) col3);
        } else {
            setVector4fc(col0, col1, col2, col3);
        }
        this.properties = 0;
        return this;
    }

    private void setVector4fc(IVector4f col0, IVector4f col1, IVector4f col2, IVector4f col3) {
        this.m00 = col0.x();
        this.m01 = col0.y();
        this.m02 = col0.z();
        this.m03 = col0.w();
        this.m10 = col1.x();
        this.m11 = col1.y();
        this.m12 = col1.z();
        this.m13 = col1.w();
        this.m20 = col2.x();
        this.m21 = col2.y();
        this.m22 = col2.z();
        this.m23 = col2.w();
        this.m30 = col3.x();
        this.m31 = col3.y();
        this.m32 = col3.z();
        this.m33 = col3.w();
    }

    @Override
    public float determinant() {
        if ((properties & PROPERTY_AFFINE) != 0)
            return determinantAffine();
        return (m00 * m11 - m01 * m10) * (m22 * m33 - m23 * m32)
             + (m02 * m10 - m00 * m12) * (m21 * m33 - m23 * m31)
             + (m00 * m13 - m03 * m10) * (m21 * m32 - m22 * m31)
             + (m01 * m12 - m02 * m11) * (m20 * m33 - m23 * m30)
             + (m03 * m11 - m01 * m13) * (m20 * m32 - m22 * m30)
             + (m02 * m13 - m03 * m12) * (m20 * m31 - m21 * m30);
    }

    @Override
    public float determinant3x3() {
        return (m00 * m11 - m01 * m10) * m22
             + (m02 * m10 - m00 * m12) * m21
             + (m01 * m12 - m02 * m11) * m20;
    }

    @Override
    public float determinantAffine() {
        return (m00 * m11 - m01 * m10) * m22
             + (m02 * m10 - m00 * m12) * m21
             + (m01 * m12 - m02 * m11) * m20;
    }

    @Override
    public Matrix4fc invert(Matrix4fc dest) {
        if ((properties & PROPERTY_IDENTITY) != 0)
            return dest.identity();
        else if ((properties & PROPERTY_AFFINE) != 0)
            return invertAffine(dest);
        else if ((properties & PROPERTY_PERSPECTIVE) != 0)
            return invertPerspective(dest);
        return invertGeneric(dest);
    }

    private Matrix4fc invertGeneric(Matrix4fc dest) {
        float a = m00 * m11 - m01 * m10;
        float b = m00 * m12 - m02 * m10;
        float c = m00 * m13 - m03 * m10;
        float d = m01 * m12 - m02 * m11;
        float e = m01 * m13 - m03 * m11;
        float f = m02 * m13 - m03 * m12;
        float g = m20 * m31 - m21 * m30;
        float h = m20 * m32 - m22 * m30;
        float i = m20 * m33 - m23 * m30;
        float j = m21 * m32 - m22 * m31;
        float k = m21 * m33 - m23 * m31;
        float l = m22 * m33 - m23 * m32;
        float det = a * l - b * k + c * j + d * i - e * h + f * g;
        det = 1.0f / det;
        float nm00 = ( m11 * l - m12 * k + m13 * j) * det;
        float nm01 = (-m01 * l + m02 * k - m03 * j) * det;
        float nm02 = ( m31 * f - m32 * e + m33 * d) * det;
        float nm03 = (-m21 * f + m22 * e - m23 * d) * det;
        float nm10 = (-m10 * l + m12 * i - m13 * h) * det;
        float nm11 = ( m00 * l - m02 * i + m03 * h) * det;
        float nm12 = (-m30 * f + m32 * c - m33 * b) * det;
        float nm13 = ( m20 * f - m22 * c + m23 * b) * det;
        float nm20 = ( m10 * k - m11 * i + m13 * g) * det;
        float nm21 = (-m00 * k + m01 * i - m03 * g) * det;
        float nm22 = ( m30 * e - m31 * c + m33 * a) * det;
        float nm23 = (-m20 * e + m21 * c - m23 * a) * det;
        float nm30 = (-m10 * j + m11 * h - m12 * g) * det;
        float nm31 = ( m00 * j - m01 * h + m02 * g) * det;
        float nm32 = (-m30 * d + m31 * b - m32 * a) * det;
        float nm33 = ( m20 * d - m21 * b + m22 * a) * det;
        dest.m00(nm00);
        dest.m01(nm01);
        dest.m02(nm02);
        dest.m03(nm03);
        dest.m10(nm10);
        dest.m11(nm11);
        dest.m12(nm12);
        dest.m13(nm13);
        dest.m20(nm20);
        dest.m21(nm21);
        dest.m22(nm22);
        dest.m23(nm23);
        dest.m30(nm30);
        dest.m31(nm31);
        dest.m32(nm32);
        dest.m33(nm33);
        dest.properties(0);
        return dest;
    }

    @Override
    public Matrix4fc invert() {
        return invert(this);
    }

    @Override
    public Matrix4fc invertPerspective(Matrix4fc dest) {
        float a =  1.0f / (m00 * m11);
        float l = -1.0f / (m23 * m32);
        dest.set(m11 * a, 0, 0, 0,
                 0, m00 * a, 0, 0,
                 0, 0, 0, -m23 * l,
                 0, 0, -m32 * l, m22 * l);
        dest.properties(0);
        return dest;
    }

    @Override
    public Matrix4fc invertPerspective() {
        return invertPerspective(this);
    }

    @Override
    public Matrix4fc invertFrustum(Matrix4fc dest) {
        float invM00 = 1.0f / m00;
        float invM11 = 1.0f / m11;
        float invM23 = 1.0f / m23;
        float invM32 = 1.0f / m32;
        dest.set(invM00, 0, 0, 0,
                 0, invM11, 0, 0,
                 0, 0, 0, invM32,
                 -m20 * invM00 * invM23, -m21 * invM11 * invM23, invM23, -m22 * invM23 * invM32);
        dest.properties(0);
        return dest;
    }

    @Override
    public Matrix4fc invertFrustum() {
        return invertFrustum(this);
    }

    @Override
    public Matrix4fc invertOrtho(Matrix4fc dest) {
        float invM00 = 1.0f / m00;
        float invM11 = 1.0f / m11;
        float invM22 = 1.0f / m22;
        dest.set(invM00, 0, 0, 0,
                 0, invM11, 0, 0,
                 0, 0, invM22, 0,
                 -m30 * invM00, -m31 * invM11, -m32 * invM22, 1);
        dest.properties(PROPERTY_AFFINE);
        return dest;
    }

    @Override
    public Matrix4fc invertOrtho() {
        return invertOrtho(this);
    }

    @Override
    public Matrix4fc invertPerspectiveView(IMatrix4f view, Matrix4fc dest) {
        float a =  1.0f / (m00 * m11);
        float l = -1.0f / (m23 * m32);
        float pm00 =  m11 * a;
        float pm11 =  m00 * a;
        float pm23 = -m23 * l;
        float pm32 = -m32 * l;
        float pm33 =  m22 * l;
        float vm30 = -view.m00() * view.m30() - view.m01() * view.m31() - view.m02() * view.m32();
        float vm31 = -view.m10() * view.m30() - view.m11() * view.m31() - view.m12() * view.m32();
        float vm32 = -view.m20() * view.m30() - view.m21() * view.m31() - view.m22() * view.m32();
        dest.set(view.m00() * pm00, view.m10() * pm00, view.m20() * pm00, 0.0f,
                 view.m01() * pm11, view.m11() * pm11, view.m21() * pm11, 0.0f,
                 vm30 * pm23, vm31 * pm23, vm32 * pm23, pm23,
                 view.m02() * pm32 + vm30 * pm33, view.m12() * pm32 + vm31 * pm33, view.m22() * pm32 + vm32 * pm33, pm33);
        dest.properties(0);
        return dest;
    }

    @Override
    public Matrix4fc invertPerspectiveView(IMatrix4x3f view, Matrix4fc dest) {
        float a =  1.0f / (m00 * m11);
        float l = -1.0f / (m23 * m32);
        float pm00 =  m11 * a;
        float pm11 =  m00 * a;
        float pm23 = -m23 * l;
        float pm32 = -m32 * l;
        float pm33 =  m22 * l;
        float vm30 = -view.m00() * view.m30() - view.m01() * view.m31() - view.m02() * view.m32();
        float vm31 = -view.m10() * view.m30() - view.m11() * view.m31() - view.m12() * view.m32();
        float vm32 = -view.m20() * view.m30() - view.m21() * view.m31() - view.m22() * view.m32();
        dest.set(view.m00() * pm00, view.m10() * pm00, view.m20() * pm00, 0.0f,
                 view.m01() * pm11, view.m11() * pm11, view.m21() * pm11, 0.0f,
                 vm30 * pm23, vm31 * pm23, vm32 * pm23, pm23,
                 view.m02() * pm32 + vm30 * pm33, view.m12() * pm32 + vm31 * pm33, view.m22() * pm32 + vm32 * pm33, pm33);
        dest.properties(0);
        return dest;
    }

    @Override
    public Matrix4fc invertAffine(Matrix4fc dest) {
        float m11m00 = m00 * m11, m10m01 = m01 * m10, m10m02 = m02 * m10;
        float m12m00 = m00 * m12, m12m01 = m01 * m12, m11m02 = m02 * m11;
        float s = 1.0f / ((m11m00 - m10m01) * m22 + (m10m02 - m12m00) * m21 + (m12m01 - m11m02) * m20);
        float m10m22 = m10 * m22, m10m21 = m10 * m21, m11m22 = m11 * m22;
        float m11m20 = m11 * m20, m12m21 = m12 * m21, m12m20 = m12 * m20;
        float m20m02 = m20 * m02, m20m01 = m20 * m01, m21m02 = m21 * m02;
        float m21m00 = m21 * m00, m22m01 = m22 * m01, m22m00 = m22 * m00;
        float nm00 = (m11m22 - m12m21) * s;
        float nm01 = (m21m02 - m22m01) * s;
        float nm02 = (m12m01 - m11m02) * s;
        float nm10 = (m12m20 - m10m22) * s;
        float nm11 = (m22m00 - m20m02) * s;
        float nm12 = (m10m02 - m12m00) * s;
        float nm20 = (m10m21 - m11m20) * s;
        float nm21 = (m20m01 - m21m00) * s;
        float nm22 = (m11m00 - m10m01) * s;
        float nm30 = (m10m22 * m31 - m10m21 * m32 + m11m20 * m32 - m11m22 * m30 + m12m21 * m30 - m12m20 * m31) * s;
        float nm31 = (m20m02 * m31 - m20m01 * m32 + m21m00 * m32 - m21m02 * m30 + m22m01 * m30 - m22m00 * m31) * s;
        float nm32 = (m11m02 * m30 - m12m01 * m30 + m12m00 * m31 - m10m02 * m31 + m10m01 * m32 - m11m00 * m32) * s;
        dest.m00(nm00);
        dest.m01(nm01);
        dest.m02(nm02);
        dest.m03(0.0f);
        dest.m10(nm10);
        dest.m11(nm11);
        dest.m12(nm12);
        dest.m13(0.0f);
        dest.m20(nm20);
        dest.m21(nm21);
        dest.m22(nm22);
        dest.m23(0.0f);
        dest.m30(nm30);
        dest.m31(nm31);
        dest.m32(nm32);
        dest.m33(1.0f);
        dest.properties(PROPERTY_AFFINE);
        return dest;
    }

    @Override
    public Matrix4fc invertAffine() {
        return invertAffine(this);
    }

    @Override
    public Matrix4fc invertAffineUnitScale(Matrix4fc dest) {
        dest.set(m00, m10, m20, 0.0f,
                 m01, m11, m21, 0.0f,
                 m02, m12, m22, 0.0f,
                 -m00 * m30 - m01 * m31 - m02 * m32,
                 -m10 * m30 - m11 * m31 - m12 * m32,
                 -m20 * m30 - m21 * m31 - m22 * m32,
                 1.0f);
        dest.properties(PROPERTY_AFFINE);
        return dest;
    }

    @Override
    public Matrix4fc invertAffineUnitScale() {
        return invertAffineUnitScale(this);
    }

    @Override
    public Matrix4fc invertLookAt(Matrix4fc dest) {
        return invertAffineUnitScale(dest);
    }

    @Override
    public Matrix4fc invertLookAt() {
        return invertAffineUnitScale(this);
    }

    @Override
    public Matrix4fc transpose(Matrix4fc dest) {
        float nm00 = m00;
        float nm01 = m10;
        float nm02 = m20;
        float nm03 = m30;
        float nm10 = m01;
        float nm11 = m11;
        float nm12 = m21;
        float nm13 = m31;
        float nm20 = m02;
        float nm21 = m12;
        float nm22 = m22;
        float nm23 = m32;
        float nm30 = m03;
        float nm31 = m13;
        float nm32 = m23;
        float nm33 = m33;
        dest.m00(nm00);
        dest.m01(nm01);
        dest.m02(nm02);
        dest.m03(nm03);
        dest.m10(nm10);
        dest.m11(nm11);
        dest.m12(nm12);
        dest.m13(nm13);
        dest.m20(nm20);
        dest.m21(nm21);
        dest.m22(nm22);
        dest.m23(nm23);
        dest.m30(nm30);
        dest.m31(nm31);
        dest.m32(nm32);
        dest.m33(nm33);
        dest.properties((byte) (properties & ~(PROPERTY_PERSPECTIVE)));
        return dest;
    }

    @Override
    public Matrix4fc transpose3x3() {
        return transpose3x3(this);
    }

    @Override
    public Matrix4fc transpose3x3(Matrix4fc dest) {
        float nm00 = m00;
        float nm01 = m10;
        float nm02 = m20;
        float nm10 = m01;
        float nm11 = m11;
        float nm12 = m21;
        float nm20 = m02;
        float nm21 = m12;
        float nm22 = m22;
        dest.m00(nm00);
        dest.m01(nm01);
        dest.m02(nm02);
        dest.m10(nm10);
        dest.m11(nm11);
        dest.m12(nm12);
        dest.m20(nm20);
        dest.m21(nm21);
        dest.m22(nm22);
        dest.properties(0);
        return dest;
    }

    @Override
    public Matrix3fc transpose3x3(Matrix3fc dest) {
        dest.m00(m00);
        dest.m01(m10);
        dest.m02(m20);
        dest.m10(m01);
        dest.m11(m11);
        dest.m12(m21);
        dest.m20(m02);
        dest.m21(m12);
        dest.m22(m22);
        return dest;
    }

    @Override
    public Matrix4fc transpose() {
        return transpose(this);
    }

    @Override
    public Matrix4fc translation(float x, float y, float z) {
        MemUtil.INSTANCE.identity(this);
        this._m30(x);
        this._m31(y);
        this._m32(z);
        properties(PROPERTY_AFFINE | PROPERTY_TRANSLATION);
        return this;
    }

    @Override
    public Matrix4fc translation(IVector3f offset) {
        return translation(offset.x(), offset.y(), offset.z());
    }

    @Override
    public Matrix4fc setTranslation(float x, float y, float z) {
        this._m30(x);
        this._m31(y);
        this._m32(z);
        properties &= ~(PROPERTY_PERSPECTIVE | PROPERTY_IDENTITY);
        return this;
    }

    @Override
    public Matrix4fc setTranslation(IVector3f xyz) {
        return setTranslation(xyz.x(), xyz.y(), xyz.z());
    }

    @Override
    public Vector3fc getTranslation(Vector3fc dest) {
        dest.set(m30, m31, m32);
        return dest;
    }

    @Override
    public Vector3fc getScale(Vector3fc dest) {
        dest.set((float) Math.sqrt(m00 * m00 + m01 * m01 + m02 * m02),
                (float) Math.sqrt(m10 * m10 + m11 * m11 + m12 * m12),
                (float) Math.sqrt(m20 * m20 + m21 * m21 + m22 * m22));
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
        return formatter.format(m00) + " " + formatter.format(m10) + " " + formatter.format(m20) + " " + formatter.format(m30) + "\n"
             + formatter.format(m01) + " " + formatter.format(m11) + " " + formatter.format(m21) + " " + formatter.format(m31) + "\n"
             + formatter.format(m02) + " " + formatter.format(m12) + " " + formatter.format(m22) + " " + formatter.format(m32) + "\n"
             + formatter.format(m03) + " " + formatter.format(m13) + " " + formatter.format(m23) + " " + formatter.format(m33) + "\n";
    }

    @Override
    public Matrix4fc get(Matrix4fc dest) {
        return dest.set(this);
    }

    @Override
    public Matrix4x3fc get4x3(Matrix4x3fc dest) {
        return dest.set(this);
    }

    @Override
    public Matrix4dc get(Matrix4dc dest) {
        return dest.set(this);
    }

    @Override
    public Matrix3fc get3x3(Matrix3fc dest) {
        return dest.set(this);
    }

    @Override
    public Matrix3dc get3x3(Matrix3dc dest) {
        return dest.set(this);
    }

    @Override
    public AxisAngle4fc getRotation(AxisAngle4fc dest) {
        return dest.set(this);
    }

    @Override
    public AxisAngle4dc getRotation(AxisAngle4dc dest) {
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
        buffer.set(3,  m03);
        buffer.set(4,  m10);
        buffer.set(5,  m11);
        buffer.set(6,  m12);
        buffer.set(7,  m13);
        buffer.set(8,  m20);
        buffer.set(9,  m21);
        buffer.set(10, m22);
        buffer.set(11, m23);
        buffer.set(12, m30);
        buffer.set(13, m31);
        buffer.set(14, m32);
        buffer.set(15, m33);
        return buffer;
    }

    @Override
    public Float32Array get(int index, Float32Array buffer) {
        buffer.set(index+0,  m00);
        buffer.set(index+1,  m01);
        buffer.set(index+2,  m02);
        buffer.set(index+3,  m03);
        buffer.set(index+4,  m10);
        buffer.set(index+5,  m11);
        buffer.set(index+6,  m12);
        buffer.set(index+7,  m13);
        buffer.set(index+8,  m20);
        buffer.set(index+9,  m21);
        buffer.set(index+10, m22);
        buffer.set(index+11, m23);
        buffer.set(index+12, m30);
        buffer.set(index+13, m31);
        buffer.set(index+14, m32);
        buffer.set(index+15, m33);
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

    @Override
    public FloatBuffer get4x3Transposed(FloatBuffer buffer) {
        return get4x3Transposed(buffer.position(), buffer);
    }

    @Override
    public FloatBuffer get4x3Transposed(int index, FloatBuffer buffer) {
        MemUtil.INSTANCE.put4x3Transposed(this, index, buffer);
        return buffer;
    }

    @Override
    public ByteBuffer get4x3Transposed(ByteBuffer buffer) {
        return get4x3Transposed(buffer.position(), buffer);
    }

    @Override
    public ByteBuffer get4x3Transposed(int index, ByteBuffer buffer) {
        MemUtil.INSTANCE.put4x3Transposed(this, index, buffer);
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
    public Matrix4fc zero() {
        MemUtil.INSTANCE.zero(this);
        properties(0);
        return this;
    }

    @Override
    public Matrix4fc scaling(float factor) {
        return scaling(factor, factor, factor);
    }

    @Override
    public Matrix4fc scaling(float x, float y, float z) {
        MemUtil.INSTANCE.identity(this);
        this._m00(x);
        this._m11(y);
        this._m22(z);
        properties(PROPERTY_AFFINE);
        return this;
    }
    
    @Override
    public Matrix4fc scaling(IVector3f xyz) {
        return scaling(xyz.x(), xyz.y(), xyz.z());
    }

    @Override
    public Matrix4fc rotation(float angle, IVector3f axis) {
        return rotation(angle, axis.x(), axis.y(), axis.z());
    }

    @Override
    public Matrix4fc rotation(AxisAngle4fc axisAngle) {
        return rotation(axisAngle.angle(), axisAngle.x(), axisAngle.y(), axisAngle.z());
    }

    @Override
    public Matrix4fc rotation(float angle, float x, float y, float z) {
        float sin = (float) Math.sin(angle);
        float cos = (float) Math.cosFromSin(sin, angle);
        float C = 1.0f - cos;
        float xy = x * y, xz = x * z, yz = y * z;
        this._m00(cos + x * x * C);
        this._m10(xy * C - z * sin);
        this._m20(xz * C + y * sin);
        this._m30(0.0f);
        this._m01(xy * C + z * sin);
        this._m11(cos + y * y * C);
        this._m21(yz * C - x * sin);
        this._m31(0.0f);
        this._m02(xz * C - y * sin);
        this._m12(yz * C + x * sin);
        this._m22(cos + z * z * C);
        this._m32(0.0f);
        this._m03(0.0f);
        this._m13(0.0f);
        this._m23(0.0f);
        this._m33(1.0f);
        properties(PROPERTY_AFFINE);
        return this;
    }

    @Override
    public Matrix4fc rotationX(float ang) {
        float sin, cos;
        sin = (float) Math.sin(ang);
        cos = (float) Math.cosFromSin(sin, ang);
        MemUtil.INSTANCE.identity(this);
        this._m11(cos);
        this._m12(sin);
        this._m21(-sin);
        this._m22(cos);
        properties(PROPERTY_AFFINE);
        return this;
    }

    @Override
    public Matrix4fc rotationY(float ang) {
        float sin, cos;
        sin = (float) Math.sin(ang);
        cos = (float) Math.cosFromSin(sin, ang);
        MemUtil.INSTANCE.identity(this);
        this._m00(cos);
        this._m02(-sin);
        this._m20(sin);
        this._m22(cos);
        properties(PROPERTY_AFFINE);
        return this;
    }

    @Override
    public Matrix4fc rotationZ(float ang) {
        float sin, cos;
        sin = (float) Math.sin(ang);
        cos = (float) Math.cosFromSin(sin, ang);
        MemUtil.INSTANCE.identity(this);
        this._m00(cos);
        this._m01(sin);
        this._m10(-sin);
        this._m11(cos);
        properties(PROPERTY_AFFINE);
        return this;
    }

    @Override
    public Matrix4fc rotationTowardsXY(float dirX, float dirY) {
        MemUtil.INSTANCE.identity(this);
        this._m00(dirY);
        this._m01(dirX);
        this._m10(-dirX);
        this._m11(dirY);
        properties(PROPERTY_AFFINE);
        return this;
    }

    @Override
    public Matrix4fc rotationXYZ(float angleX, float angleY, float angleZ) {
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
        this._m20(sinY);
        this._m21(nm21 * cosY);
        this._m22(nm22 * cosY);
        this._m23(0.0f);
        // rotateZ
        this._m00(nm00 * cosZ);
        this._m01(nm01 * cosZ + nm11 * sinZ);
        this._m02(nm02 * cosZ + nm12 * sinZ);
        this._m03(0.0f);
        this._m10(nm00 * m_sinZ);
        this._m11(nm01 * m_sinZ + nm11 * cosZ);
        this._m12(nm02 * m_sinZ + nm12 * cosZ);
        this._m13(0.0f);
        // set last column to identity
        this._m30(0.0f);
        this._m31(0.0f);
        this._m32(0.0f);
        this._m33(1.0f);
        properties(PROPERTY_AFFINE);
        return this;
    }

    @Override
    public Matrix4fc rotationZYX(float angleZ, float angleY, float angleX) {
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
        this._m00(nm00 * cosY);
        this._m01(nm01 * cosY);
        this._m02(m_sinY);
        this._m03(0.0f);
        // rotateX
        this._m10(nm10 * cosX + nm20 * sinX);
        this._m11(nm11 * cosX + nm21 * sinX);
        this._m12(nm22 * sinX);
        this._m13(0.0f);
        this._m20(nm10 * m_sinX + nm20 * cosX);
        this._m21(nm11 * m_sinX + nm21 * cosX);
        this._m22(nm22 * cosX);
        this._m23(0.0f);
        // set last column to identity
        this._m30(0.0f);
        this._m31(0.0f);
        this._m32(0.0f);
        this._m33(1.0f);
        properties(PROPERTY_AFFINE);
        return this;
    }

    @Override
    public Matrix4fc rotationYXZ(float angleY, float angleX, float angleZ) {
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
        this._m20(nm20 * cosX);
        this._m21(m_sinX);
        this._m22(nm22 * cosX);
        this._m23(0.0f);
        // rotateZ
        this._m00(nm00 * cosZ + nm10 * sinZ);
        this._m01(nm11 * sinZ);
        this._m02(nm02 * cosZ + nm12 * sinZ);
        this._m03(0.0f);
        this._m10(nm00 * m_sinZ + nm10 * cosZ);
        this._m11(nm11 * cosZ);
        this._m12(nm02 * m_sinZ + nm12 * cosZ);
        this._m13(0.0f);
        // set last column to identity
        this._m30(0.0f);
        this._m31(0.0f);
        this._m32(0.0f);
        this._m33(1.0f);
        properties(PROPERTY_AFFINE);
        return this;
    }

    @Override
    public Matrix4fc setRotationXYZ(float angleX, float angleY, float angleZ) {
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
        this._m20(sinY);
        this._m21(nm21 * cosY);
        this._m22(nm22 * cosY);
        // rotateZ
        this._m00(nm00 * cosZ);
        this._m01(nm01 * cosZ + nm11 * sinZ);
        this._m02(nm02 * cosZ + nm12 * sinZ);
        this._m10(nm00 * m_sinZ);
        this._m11(nm01 * m_sinZ + nm11 * cosZ);
        this._m12(nm02 * m_sinZ + nm12 * cosZ);
        properties &= ~(PROPERTY_PERSPECTIVE | PROPERTY_IDENTITY | PROPERTY_TRANSLATION);
        return this;
    }

    @Override
    public Matrix4fc setRotationZYX(float angleZ, float angleY, float angleX) {
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
        this._m00(nm00 * cosY);
        this._m01(nm01 * cosY);
        this._m02(m_sinY);
        // rotateX
        this._m10(nm10 * cosX + nm20 * sinX);
        this._m11(nm11 * cosX + nm21 * sinX);
        this._m12(nm22 * sinX);
        this._m20(nm10 * m_sinX + nm20 * cosX);
        this._m21(nm11 * m_sinX + nm21 * cosX);
        this._m22(nm22 * cosX);
        properties &= ~(PROPERTY_PERSPECTIVE | PROPERTY_IDENTITY | PROPERTY_TRANSLATION);
        return this;
    }

    @Override
    public Matrix4fc setRotationYXZ(float angleY, float angleX, float angleZ) {
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
        this._m20(nm20 * cosX);
        this._m21(m_sinX);
        this._m22(nm22 * cosX);
        // rotateZ
        this._m00(nm00 * cosZ + nm10 * sinZ);
        this._m01(nm11 * sinZ);
        this._m02(nm02 * cosZ + nm12 * sinZ);
        this._m10(nm00 * m_sinZ + nm10 * cosZ);
        this._m11(nm11 * cosZ);
        this._m12(nm02 * m_sinZ + nm12 * cosZ);
        properties &= ~(PROPERTY_PERSPECTIVE | PROPERTY_IDENTITY | PROPERTY_TRANSLATION);
        return this;
    }

    @Override
    public Matrix4fc rotation(IQuaternionf quat) {
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
        _m00(w2 + x2 - z2 - y2);
        _m01(xy + zw + zw + xy);
        _m02(xz - yw + xz - yw);
        _m03(0.0f);
        _m10(-zw + xy - zw + xy);
        _m11(y2 - z2 + w2 - x2);
        _m12(yz + yz + xw + xw);
        _m13(0.0f);
        _m20(yw + xz + xz + yw);
        _m21(yz + yz - xw - xw);
        _m22(z2 - y2 - x2 + w2);
        _m30(0.0f);
        _m31(0.0f);
        _m32(0.0f);
        _m33(1.0f);
        properties(PROPERTY_AFFINE);
        return this;
    }

    @Override
    public Matrix4fc translationRotateScale(float tx, float ty, float tz, 
                                           float qx, float qy, float qz, float qw, 
                                           float sx, float sy, float sz) {
        float dqx = qx + qx;
        float dqy = qy + qy;
        float dqz = qz + qz;
        float q00 = dqx * qx;
        float q11 = dqy * qy;
        float q22 = dqz * qz;
        float q01 = dqx * qy;
        float q02 = dqx * qz;
        float q03 = dqx * qw;
        float q12 = dqy * qz;
        float q13 = dqy * qw;
        float q23 = dqz * qw;
        this._m00(sx - (q11 + q22) * sx);
        this._m01((q01 + q23) * sx);
        this._m02((q02 - q13) * sx);
        this._m03(0.0f);
        this._m10((q01 - q23) * sy);
        this._m11(sy - (q22 + q00) * sy);
        this._m12((q12 + q03) * sy);
        this._m13(0.0f);
        this._m20((q02 + q13) * sz);
        this._m21((q12 - q03) * sz);
        this._m22(sz - (q11 + q00) * sz);
        this._m23(0.0f);
        this._m30(tx);
        this._m31(ty);
        this._m32(tz);
        this._m33(1.0f);
        properties(PROPERTY_AFFINE);
        return this;
    }

    @Override
    public Matrix4fc translationRotateScale(IVector3f translation, 
                                           IQuaternionf quat, 
                                           IVector3f scale) {
        return translationRotateScale(translation.x(), translation.y(), translation.z(), quat.x(), quat.y(), quat.z(), quat.w(), scale.x(), scale.y(), scale.z());
    }

    @Override
    public Matrix4fc translationRotateScale(float tx, float ty, float tz, 
                                           float qx, float qy, float qz, float qw, 
                                           float scale) {
        return translationRotateScale(tx, ty, tz, qx, qy, qz, qw, scale, scale, scale);
    }

    @Override
    public Matrix4fc translationRotateScale(IVector3f translation, 
                                           IQuaternionf quat, 
                                           float scale) {
        return translationRotateScale(translation.x(), translation.y(), translation.z(), quat.x(), quat.y(), quat.z(), quat.w(), scale, scale, scale);
    }

    @Override
    public Matrix4fc translationRotateScaleInvert(float tx, float ty, float tz, 
                                                 float qx, float qy, float qz, float qw, 
                                                 float sx, float sy, float sz) {
        float nqx = -qx, nqy = -qy, nqz = -qz;
        float dqx = nqx + nqx;
        float dqy = nqy + nqy;
        float dqz = nqz + nqz;
        float q00 = dqx * nqx;
        float q11 = dqy * nqy;
        float q22 = dqz * nqz;
        float q01 = dqx * nqy;
        float q02 = dqx * nqz;
        float q03 = dqx * qw;
        float q12 = dqy * nqz;
        float q13 = dqy * qw;
        float q23 = dqz * qw;
        float isx = 1/sx, isy = 1/sy, isz = 1/sz;
        this._m00(isx * (1.0f - q11 - q22));
        this._m01(isy * (q01 + q23));
        this._m02(isz * (q02 - q13));
        this._m03(0.0f);
        this._m10(isx * (q01 - q23));
        this._m11(isy * (1.0f - q22 - q00));
        this._m12(isz * (q12 + q03));
        this._m13(0.0f);
        this._m20(isx * (q02 + q13));
        this._m21(isy * (q12 - q03));
        this._m22(isz * (1.0f - q11 - q00));
        this._m23(0.0f);
        this._m30(-m00 * tx - m10 * ty - m20 * tz);
        this._m31(-m01 * tx - m11 * ty - m21 * tz);
        this._m32(-m02 * tx - m12 * ty - m22 * tz);
        this._m33(1.0f);
        properties(PROPERTY_AFFINE);
        return this;
    }

    @Override
    public Matrix4fc translationRotateScaleInvert(IVector3f translation, 
                                                 IQuaternionf quat, 
                                                 IVector3f scale) {
        return translationRotateScaleInvert(translation.x(), translation.y(), translation.z(), quat.x(), quat.y(), quat.z(), quat.w(), scale.x(), scale.y(), scale.z());
    }

    @Override
    public Matrix4fc translationRotateScaleInvert(IVector3f translation, 
                                                 IQuaternionf quat, 
                                                 float scale) {
        return translationRotateScaleInvert(translation.x(), translation.y(), translation.z(), quat.x(), quat.y(), quat.z(), quat.w(), scale, scale, scale);
    }

    @Override
    public Matrix4fc translationRotateScaleMulAffine(float tx, float ty, float tz, 
                                                    float qx, float qy, float qz, float qw, 
                                                    float sx, float sy, float sz,
                                                    Matrix4fc m) {
        float w2 = qw * qw;
        float x2 = qx * qx;
        float y2 = qy * qy;
        float z2 = qz * qz;
        float zw = qz * qw;
        float xy = qx * qy;
        float xz = qx * qz;
        float yw = qy * qw;
        float yz = qy * qz;
        float xw = qx * qw;
        float nm00 = w2 + x2 - z2 - y2;
        float nm01 = xy + zw + zw + xy;
        float nm02 = xz - yw + xz - yw;
        float nm10 = -zw + xy - zw + xy;
        float nm11 = y2 - z2 + w2 - x2;
        float nm12 = yz + yz + xw + xw;
        float nm20 = yw + xz + xz + yw;
        float nm21 = yz + yz - xw - xw;
        float nm22 = z2 - y2 - x2 + w2;
        float m00 = nm00 * m.m00() + nm10 * m.m01() + nm20 * m.m02();
        float m01 = nm01 * m.m00() + nm11 * m.m01() + nm21 * m.m02();
        this._m02(nm02 * m.m00() + nm12 * m.m01() + nm22 * m.m02());
        this._m00(m00);
        this._m01(m01);
        this._m03(0.0f);
        float m10 = nm00 * m.m10() + nm10 * m.m11() + nm20 * m.m12();
        float m11 = nm01 * m.m10() + nm11 * m.m11() + nm21 * m.m12();
        this._m12(nm02 * m.m10() + nm12 * m.m11() + nm22 * m.m12());
        this._m10(m10);
        this._m11(m11);
        this._m13(0.0f);
        float m20 = nm00 * m.m20() + nm10 * m.m21() + nm20 * m.m22();
        float m21 = nm01 * m.m20() + nm11 * m.m21() + nm21 * m.m22();
        this._m22(nm02 * m.m20() + nm12 * m.m21() + nm22 * m.m22());
        this._m20(m20);
        this._m21(m21);
        this._m23(0.0f);
        float m30 = nm00 * m.m30() + nm10 * m.m31() + nm20 * m.m32() + tx;
        float m31 = nm01 * m.m30() + nm11 * m.m31() + nm21 * m.m32() + ty;
        this._m32(nm02 * m.m30() + nm12 * m.m31() + nm22 * m.m32() + tz);
        this._m30(m30);
        this._m31(m31);
        this._m33(1.0f);
        properties(PROPERTY_AFFINE);
        return this;
    }

    @Override
    public Matrix4fc translationRotateScaleMulAffine(IVector3f translation, 
                                                    IQuaternionf quat, 
                                                    IVector3f scale,
                                                    Matrix4fc m) {
        return translationRotateScaleMulAffine(translation.x(), translation.y(), translation.z(), quat.x(), quat.y(), quat.z(), quat.w(), scale.x(), scale.y(), scale.z(), m);
    }

    @Override
    public Matrix4fc translationRotate(float tx, float ty, float tz, float qx, float qy, float qz, float qw) {
        float w2 = qw * qw;
        float x2 = qx * qx;
        float y2 = qy * qy;
        float z2 = qz * qz;
        float zw = qz * qw;
        float xy = qx * qy;
        float xz = qx * qz;
        float yw = qy * qw;
        float yz = qy * qz;
        float xw = qx * qw;
        this._m00(w2 + x2 - z2 - y2);
        this._m01(xy + zw + zw + xy);
        this._m02(xz - yw + xz - yw);
        this._m10(-zw + xy - zw + xy);
        this._m11(y2 - z2 + w2 - x2);
        this._m12(yz + yz + xw + xw);
        this._m20(yw + xz + xz + yw);
        this._m21(yz + yz - xw - xw);
        this._m22(z2 - y2 - x2 + w2);
        this._m30(tx);
        this._m31(ty);
        this._m32(tz);
        this._m33(1.0f);
        properties(PROPERTY_AFFINE);
        return this;
    }

    @Override
    public Matrix4fc translationRotate(float tx, float ty, float tz, IQuaternionf quat) {
        return translationRotate(tx, ty, tz, quat.x(), quat.y(), quat.z(), quat.w());
    }

    @Override
    public Matrix4fc set3x3(IMatrix3f mat) {
        if (mat instanceof Matrix3f) {
            MemUtil.INSTANCE.copy3x3((Matrix3f) mat, this);
        } else {
            set3x3Matrix3fc(mat);
        }
        properties &= ~(PROPERTY_PERSPECTIVE | PROPERTY_IDENTITY | PROPERTY_TRANSLATION);
        return this;
    }

    private void set3x3Matrix3fc(IMatrix3f mat) {
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
    public Vector4fc transform(Vector4fc v) {
        return v.mul(this);
    }

    @Override
    public Vector4fc transform(IVector4f v, Vector4fc dest) {
        return v.mul(this, dest);
    }

    @Override
    public Vector4fc transform(float x, float y, float z, float w, Vector4fc dest) {
        dest.set(m00 * x + m10 * y + m20 * z + m30 * w,
                 m01 * x + m11 * y + m21 * z + m31 * w,
                 m02 * x + m12 * y + m22 * z + m32 * w,
                 m03 * x + m13 * y + m23 * z + m33 * w);
       return dest;
    }

    @Override
    public Vector4fc transformProject(Vector4fc v) {
        return v.mulProject(this);
    }

    @Override
    public Vector4fc transformProject(IVector4f v, Vector4fc dest) {
        return v.mulProject(this, dest);
    }

    @Override
    public Vector4fc transformProject(float x, float y, float z, float w, Vector4fc dest) {
        float invW = 1.0f / (m03 * x + m13 * y + m23 * z + m33 * w);
        dest.set((m00 * x + m10 * y + m20 * z + m30 * w) * invW,
                 (m01 * x + m11 * y + m21 * z + m31 * w) * invW,
                 (m02 * x + m12 * y + m22 * z + m32 * w) * invW,
                 1.0f);
        return dest;
    }

    @Override
    public Vector3fc transformProject(Vector3fc v) {
        return v.mulProject(this);
    }

    @Override
    public Vector3fc transformProject(IVector3f v, Vector3fc dest) {
        return v.mulProject(this, dest);
    }

    @Override
    public Vector3fc transformProject(float x, float y, float z, Vector3fc dest) {
        float invW = 1.0f / (m03 * x + m13 * y + m23 * z + m33);
        dest.set((m00 * x + m10 * y + m20 * z + m30) * invW,
                 (m01 * x + m11 * y + m21 * z + m31) * invW,
                 (m02 * x + m12 * y + m22 * z + m32) * invW);
        return dest;
    }

    @Override
    public Vector3fc transformPosition(Vector3fc v) {
        v.set(m00 * v.x() + m10 * v.y() + m20 * v.z() + m30,
              m01 * v.x() + m11 * v.y() + m21 * v.z() + m31,
              m02 * v.x() + m12 * v.y() + m22 * v.z() + m32);
        return v;
    }

    @Override
    public Vector3fc transformPosition(IVector3f v, Vector3fc dest) {
        return transformPosition(v.x(), v.y(), v.z(), dest);
    }

    @Override
    public Vector3fc transformPosition(float x, float y, float z, Vector3fc dest) {
        dest.set(m00 * x + m10 * y + m20 * z + m30,
                 m01 * x + m11 * y + m21 * z + m31,
                 m02 * x + m12 * y + m22 * z + m32);
        return dest;
    }

    @Override
    public Vector3fc transformDirection(Vector3fc v) {
        v.set(m00 * v.x() + m10 * v.y() + m20 * v.z(),
              m01 * v.x() + m11 * v.y() + m21 * v.z(),
              m02 * v.x() + m12 * v.y() + m22 * v.z());
        return v;
    }

    @Override
    public Vector3fc transformDirection(IVector3f v, Vector3fc dest) {
        return transformDirection(v.x(), v.y(), v.z(), dest);
    }

    @Override
    public Vector3fc transformDirection(float x, float y, float z, Vector3fc dest) {
        dest.set(m00 * x + m10 * y + m20 * z,
                 m01 * x + m11 * y + m21 * z,
                 m02 * x + m12 * y + m22 * z);
        return dest;
    }

    @Override
    public Vector4fc transformAffine(Vector4fc v) {
        v.set(m00 * v.x() + m10 * v.y() + m20 * v.z() + m30 * v.w(),
              m01 * v.x() + m11 * v.y() + m21 * v.z() + m31 * v.w(),
              m02 * v.x() + m12 * v.y() + m22 * v.z() + m32 * v.w(),
              v.w());
        return v;
    }

    @Override
    public Vector4fc transformAffine(IVector4f v, Vector4fc dest) {
        return transformAffine(v.x(), v.y(), v.z(), v.w(), dest);
    }

    @Override
    public Vector4fc transformAffine(float x, float y, float z, float w, Vector4fc dest) {
        dest.set(m00 * x + m10 * y + m20 * z + m30 * w,
                 m01 * x + m11 * y + m21 * z + m31 * w,
                 m02 * x + m12 * y + m22 * z + m32 * w,
                 w);
        return dest;
    }

    @Override
    public Matrix4fc scale(IVector3f xyz, Matrix4fc dest) {
        return scale(xyz.x(), xyz.y(), xyz.z(), dest);
    }

    @Override
    public Matrix4fc scale(IVector3f xyz) {
        return scale(xyz.x(), xyz.y(), xyz.z(), this);
    }

    @Override
    public Matrix4fc scale(float xyz, Matrix4fc dest) {
        return scale(xyz, xyz, xyz, dest);
    }

    @Override
    public Matrix4fc scale(float xyz) {
        return scale(xyz, xyz, xyz);
    }

    @Override
    public Matrix4fc scale(float x, float y, float z, Matrix4fc dest) {
        if ((properties & PROPERTY_IDENTITY) != 0)
            return dest.scaling(x, y, z);
        return scaleGeneric(x, y, z, dest);
    }

    private Matrix4fc scaleGeneric(float x, float y, float z, Matrix4fc dest) {
        dest.m00(m00 * x);
        dest.m01(m01 * x);
        dest.m02(m02 * x);
        dest.m03(m03 * x);
        dest.m10(m10 * y);
        dest.m11(m11 * y);
        dest.m12(m12 * y);
        dest.m13(m13 * y);
        dest.m20(m20 * z);
        dest.m21(m21 * z);
        dest.m22(m22 * z);
        dest.m23(m23 * z);
        dest.m30(m30);
        dest.m31(m31);
        dest.m32(m32);
        dest.m33(m33);
        dest.properties((byte) (properties & ~(PROPERTY_PERSPECTIVE | PROPERTY_IDENTITY | PROPERTY_TRANSLATION)));
        return dest;
    }

    @Override
    public Matrix4fc scale(float x, float y, float z) {
        return scale(x, y, z, this);
    }

    @Override
    public Matrix4fc scaleAround(float sx, float sy, float sz, float ox, float oy, float oz, Matrix4fc dest) {
        float nm30 = m00 * ox + m10 * oy + m20 * oz + m30;
        float nm31 = m01 * ox + m11 * oy + m21 * oz + m31;
        float nm32 = m02 * ox + m12 * oy + m22 * oz + m32;
        float nm33 = m03 * ox + m13 * oy + m23 * oz + m33;
        dest.m00(m00 * sx);
        dest.m01(m01 * sx);
        dest.m02(m02 * sx);
        dest.m03(m03 * sx);
        dest.m10(m10 * sy);
        dest.m11(m11 * sy);
        dest.m12(m12 * sy);
        dest.m13(m13 * sy);
        dest.m20(m20 * sz);
        dest.m21(m21 * sz);
        dest.m22(m22 * sz);
        dest.m23(m23 * sz);
        dest.m30(-m00 * ox - m10 * oy - m20 * oz + nm30);
        dest.m31(-m01 * ox - m11 * oy - m21 * oz + nm31);
        dest.m32(-m02 * ox - m12 * oy - m22 * oz + nm32);
        dest.m33(-m03 * ox - m13 * oy - m23 * oz + nm33);
        dest.properties((byte) (properties & ~(PROPERTY_PERSPECTIVE | PROPERTY_IDENTITY | PROPERTY_TRANSLATION)));
        return dest;
    }

    @Override
    public Matrix4fc scaleAround(float sx, float sy, float sz, float ox, float oy, float oz) {
        return scaleAround(sx, sy, sz, ox, oy, oz, this);
    }

    @Override
    public Matrix4fc scaleAround(float factor, float ox, float oy, float oz) {
        return scaleAround(factor, factor, factor, ox, oy, oz, this);
    }

    @Override
    public Matrix4fc scaleAround(float factor, float ox, float oy, float oz, Matrix4fc dest) {
        return scaleAround(factor, factor, factor, ox, oy, oz, dest);
    }

    @Override
    public Matrix4fc scaleLocal(float x, float y, float z, Matrix4fc dest) {
        if ((properties & PROPERTY_IDENTITY) != 0)
            return dest.scaling(x, y, z);

        float nm00 = x * m00;
        float nm01 = y * m01;
        float nm02 = z * m02;
        float nm03 = m03;
        float nm10 = x * m10;
        float nm11 = y * m11;
        float nm12 = z * m12;
        float nm13 = m13;
        float nm20 = x * m20;
        float nm21 = y * m21;
        float nm22 = z * m22;
        float nm23 = m23;
        float nm30 = x * m30;
        float nm31 = y * m31;
        float nm32 = z * m32;
        float nm33 = m33;
        dest.m00(nm00);
        dest.m01(nm01);
        dest.m02(nm02);
        dest.m03(nm03);
        dest.m10(nm10);
        dest.m11(nm11);
        dest.m12(nm12);
        dest.m13(nm13);
        dest.m20(nm20);
        dest.m21(nm21);
        dest.m22(nm22);
        dest.m23(nm23);
        dest.m30(nm30);
        dest.m31(nm31);
        dest.m32(nm32);
        dest.m33(nm33);
        dest.properties((byte) (properties & ~(PROPERTY_PERSPECTIVE | PROPERTY_IDENTITY | PROPERTY_TRANSLATION)));
        return dest;
    }

    @Override
    public Matrix4fc scaleLocal(float xyz, Matrix4fc dest) {
        return scaleLocal(xyz, xyz, xyz, dest);
    }

    @Override
    public Matrix4fc scaleLocal(float xyz) {
        return scaleLocal(xyz, this);
    }

    @Override
    public Matrix4fc scaleLocal(float x, float y, float z) {
        return scaleLocal(x, y, z, this);
    }

    @Override
    public Matrix4fc scaleAroundLocal(float sx, float sy, float sz, float ox, float oy, float oz, Matrix4fc dest) {
        dest.m00(sx * (m00 - ox * m03) + ox * m03);
        dest.m01(sy * (m01 - oy * m03) + oy * m03);
        dest.m02(sz * (m02 - oz * m03) + oz * m03);
        dest.m03(m03);
        dest.m10(sx * (m10 - ox * m13) + ox * m13);
        dest.m11(sy * (m11 - oy * m13) + oy * m13);
        dest.m12(sz * (m12 - oz * m13) + oz * m13);
        dest.m13(m13);
        dest.m20(sx * (m20 - ox * m23) + ox * m23);
        dest.m21(sy * (m21 - oy * m23) + oy * m23);
        dest.m22(sz * (m22 - oz * m23) + oz * m23);
        dest.m23(m23);
        dest.m30(sx * (m30 - ox * m33) + ox * m33);
        dest.m31(sy * (m31 - oy * m33) + oy * m33);
        dest.m32(sz * (m32 - oz * m33) + oz * m33);
        dest.m33(m33);
        dest.properties((byte) (properties & ~(PROPERTY_PERSPECTIVE | PROPERTY_IDENTITY | PROPERTY_TRANSLATION)));
        return dest;
    }

    @Override
    public Matrix4fc scaleAroundLocal(float sx, float sy, float sz, float ox, float oy, float oz) {
        return scaleAroundLocal(sx, sy, sz, ox, oy, oz, this);
    }

    @Override
    public Matrix4fc scaleAroundLocal(float factor, float ox, float oy, float oz) {
        return scaleAroundLocal(factor, factor, factor, ox, oy, oz, this);
    }

    @Override
    public Matrix4fc scaleAroundLocal(float factor, float ox, float oy, float oz, Matrix4fc dest) {
        return scaleAroundLocal(factor, factor, factor, ox, oy, oz, dest);
    }

    @Override
    public Matrix4fc rotateX(float ang, Matrix4fc dest) {
        if ((properties & PROPERTY_IDENTITY) != 0)
            return dest.rotationX(ang);
        float sin, cos;
        sin = (float) Math.sin(ang);
        cos = (float) Math.cosFromSin(sin, ang);
        float rm11 = cos;
        float rm12 = sin;
        float rm21 = -sin;
        float rm22 = cos;

        // add temporaries for dependent values
        float nm10 = m10 * rm11 + m20 * rm12;
        float nm11 = m11 * rm11 + m21 * rm12;
        float nm12 = m12 * rm11 + m22 * rm12;
        float nm13 = m13 * rm11 + m23 * rm12;
        // set non-dependent values directly
        dest.m20(m10 * rm21 + m20 * rm22);
        dest.m21(m11 * rm21 + m21 * rm22);
        dest.m22(m12 * rm21 + m22 * rm22);
        dest.m23(m13 * rm21 + m23 * rm22);
        // set other values
        dest.m10(nm10);
        dest.m11(nm11);
        dest.m12(nm12);
        dest.m13(nm13);
        dest.m00(m00);
        dest.m01(m01);
        dest.m02(m02);
        dest.m03(m03);
        dest.m30(m30);
        dest.m31(m31);
        dest.m32(m32);
        dest.m33(m33);
        dest.properties((byte) (properties & ~(PROPERTY_PERSPECTIVE | PROPERTY_IDENTITY | PROPERTY_TRANSLATION)));
        return dest;
    }

    @Override
    public Matrix4fc rotateX(float ang) {
        return rotateX(ang, this);
    }

    @Override
    public Matrix4fc rotateY(float ang, Matrix4fc dest) {
        if ((properties & PROPERTY_IDENTITY) != 0)
            return dest.rotationY(ang);
        float cos, sin;
        sin = (float) Math.sin(ang);
        cos = (float) Math.cosFromSin(sin, ang);
        float rm00 = cos;
        float rm02 = -sin;
        float rm20 = sin;
        float rm22 = cos;

        // add temporaries for dependent values
        float nm00 = m00 * rm00 + m20 * rm02;
        float nm01 = m01 * rm00 + m21 * rm02;
        float nm02 = m02 * rm00 + m22 * rm02;
        float nm03 = m03 * rm00 + m23 * rm02;
        // set non-dependent values directly
        dest.m20(m00 * rm20 + m20 * rm22);
        dest.m21(m01 * rm20 + m21 * rm22);
        dest.m22(m02 * rm20 + m22 * rm22);
        dest.m23(m03 * rm20 + m23 * rm22);
        // set other values
        dest.m00(nm00);
        dest.m01(nm01);
        dest.m02(nm02);
        dest.m03(nm03);
        dest.m10(m10);
        dest.m11(m11);
        dest.m12(m12);
        dest.m13(m13);
        dest.m30(m30);
        dest.m31(m31);
        dest.m32(m32);
        dest.m33(m33);
        dest.properties((byte) (properties & ~(PROPERTY_PERSPECTIVE | PROPERTY_IDENTITY | PROPERTY_TRANSLATION)));
        return dest;
    }

    @Override
    public Matrix4fc rotateY(float ang) {
        return rotateY(ang, this);
    }

    @Override
    public Matrix4fc rotateZ(float ang, Matrix4fc dest) {
        if ((properties & PROPERTY_IDENTITY) != 0)
            return dest.rotationZ(ang);
        float sin = (float) Math.sin(ang);
        float cos = (float) Math.cosFromSin(sin, ang);
        return rotateTowardsXY(sin, cos, dest);
    }

    @Override
    public Matrix4fc rotateZ(float ang) {
        return rotateZ(ang, this);
    }

    @Override
    public Matrix4fc rotateTowardsXY(float dirX, float dirY) {
        return rotateTowardsXY(dirX, dirY, this);
    }

    @Override
    public Matrix4fc rotateTowardsXY(float dirX, float dirY, Matrix4fc dest) {
        if ((properties & PROPERTY_IDENTITY) != 0)
            return dest.rotationTowardsXY(dirX, dirY);
        float rm00 = dirY;
        float rm01 = dirX;
        float rm10 = -dirX;
        float rm11 = dirY;
        float nm00 = m00 * rm00 + m10 * rm01;
        float nm01 = m01 * rm00 + m11 * rm01;
        float nm02 = m02 * rm00 + m12 * rm01;
        float nm03 = m03 * rm00 + m13 * rm01;
        dest.m10(m00 * rm10 + m10 * rm11);
        dest.m11(m01 * rm10 + m11 * rm11);
        dest.m12(m02 * rm10 + m12 * rm11);
        dest.m13(m03 * rm10 + m13 * rm11);
        dest.m00(nm00);
        dest.m01(nm01);
        dest.m02(nm02);
        dest.m03(nm03);
        dest.m20(m20);
        dest.m21(m21);
        dest.m22(m22);
        dest.m23(m23);
        dest.m30(m30);
        dest.m31(m31);
        dest.m32(m32);
        dest.m33(m33);
        dest.properties((byte) (properties & ~(PROPERTY_PERSPECTIVE | PROPERTY_IDENTITY | PROPERTY_TRANSLATION)));
        return dest;
    }

    @Override
    public Matrix4fc rotateXYZ(Vector3fc angles) {
        return rotateXYZ(angles.x(), angles.y(), angles.z());
    }

    @Override
    public Matrix4fc rotateXYZ(float angleX, float angleY, float angleZ) {
        return rotateXYZ(angleX, angleY, angleZ, this);
    }

    @Override
    public Matrix4fc rotateXYZ(float angleX, float angleY, float angleZ, Matrix4fc dest) {
        if ((properties & PROPERTY_IDENTITY) != 0)
            return dest.rotationXYZ(angleX, angleY, angleZ);
        else if ((properties & PROPERTY_AFFINE) != 0)
            return dest.rotateAffineXYZ(angleX, angleY, angleZ);

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
        float nm13 = m13 * cosX + m23 * sinX;
        float nm20 = m10 * m_sinX + m20 * cosX;
        float nm21 = m11 * m_sinX + m21 * cosX;
        float nm22 = m12 * m_sinX + m22 * cosX;
        float nm23 = m13 * m_sinX + m23 * cosX;
        // rotateY
        float nm00 = m00 * cosY + nm20 * m_sinY;
        float nm01 = m01 * cosY + nm21 * m_sinY;
        float nm02 = m02 * cosY + nm22 * m_sinY;
        float nm03 = m03 * cosY + nm23 * m_sinY;
        dest.m20(m00 * sinY + nm20 * cosY);
        dest.m21(m01 * sinY + nm21 * cosY);
        dest.m22(m02 * sinY + nm22 * cosY);
        dest.m23(m03 * sinY + nm23 * cosY);
        // rotateZ
        dest.m00(nm00 * cosZ + nm10 * sinZ);
        dest.m01(nm01 * cosZ + nm11 * sinZ);
        dest.m02(nm02 * cosZ + nm12 * sinZ);
        dest.m03(nm03 * cosZ + nm13 * sinZ);
        dest.m10(nm00 * m_sinZ + nm10 * cosZ);
        dest.m11(nm01 * m_sinZ + nm11 * cosZ);
        dest.m12(nm02 * m_sinZ + nm12 * cosZ);
        dest.m13(nm03 * m_sinZ + nm13 * cosZ);
        // copy last column from 'this'
        dest.m30(m30);
        dest.m31(m31);
        dest.m32(m32);
        dest.m33(m33);
        dest.properties((byte) (properties & ~(PROPERTY_PERSPECTIVE | PROPERTY_IDENTITY | PROPERTY_TRANSLATION)));
        return dest;
    }

    @Override
    public Matrix4fc rotateAffineXYZ(float angleX, float angleY, float angleZ) {
        return rotateAffineXYZ(angleX, angleY, angleZ, this);
    }

    @Override
    public Matrix4fc rotateAffineXYZ(float angleX, float angleY, float angleZ, Matrix4fc dest) {
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
        dest.m23(0.0f);
        // rotateZ
        dest.m00(nm00 * cosZ + nm10 * sinZ);
        dest.m01(nm01 * cosZ + nm11 * sinZ);
        dest.m02(nm02 * cosZ + nm12 * sinZ);
        dest.m03(0.0f);
        dest.m10(nm00 * m_sinZ + nm10 * cosZ);
        dest.m11(nm01 * m_sinZ + nm11 * cosZ);
        dest.m12(nm02 * m_sinZ + nm12 * cosZ);
        dest.m13(0.0f);
        // copy last column from 'this'
        dest.m30(m30);
        dest.m31(m31);
        dest.m32(m32);
        dest.m33(m33);
        dest.properties((byte) (properties & ~(PROPERTY_PERSPECTIVE | PROPERTY_IDENTITY | PROPERTY_TRANSLATION)));
        return dest;
    }

    @Override
    public Matrix4fc rotateZYX(Vector3fc angles) {
        return rotateZYX(angles.z(), angles.y(), angles.x());
    }

    @Override
    public Matrix4fc rotateZYX(float angleZ, float angleY, float angleX) {
        return rotateZYX(angleZ, angleY, angleX, this);
    }

    @Override
    public Matrix4fc rotateZYX(float angleZ, float angleY, float angleX, Matrix4fc dest) {
        if ((properties & PROPERTY_IDENTITY) != 0)
            return dest.rotationZYX(angleZ, angleY, angleX);
        else if ((properties & PROPERTY_AFFINE) != 0)
            return dest.rotateAffineZYX(angleZ, angleY, angleX);

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
        float nm03 = m03 * cosZ + m13 * sinZ;
        float nm10 = m00 * m_sinZ + m10 * cosZ;
        float nm11 = m01 * m_sinZ + m11 * cosZ;
        float nm12 = m02 * m_sinZ + m12 * cosZ;
        float nm13 = m03 * m_sinZ + m13 * cosZ;
        // rotateY
        float nm20 = nm00 * sinY + m20 * cosY;
        float nm21 = nm01 * sinY + m21 * cosY;
        float nm22 = nm02 * sinY + m22 * cosY;
        float nm23 = nm03 * sinY + m23 * cosY;
        dest.m00(nm00 * cosY + m20 * m_sinY);
        dest.m01(nm01 * cosY + m21 * m_sinY);
        dest.m02(nm02 * cosY + m22 * m_sinY);
        dest.m03(nm03 * cosY + m23 * m_sinY);
        // rotateX
        dest.m10(nm10 * cosX + nm20 * sinX);
        dest.m11(nm11 * cosX + nm21 * sinX);
        dest.m12(nm12 * cosX + nm22 * sinX);
        dest.m13(nm13 * cosX + nm23 * sinX);
        dest.m20(nm10 * m_sinX + nm20 * cosX);
        dest.m21(nm11 * m_sinX + nm21 * cosX);
        dest.m22(nm12 * m_sinX + nm22 * cosX);
        dest.m23(nm13 * m_sinX + nm23 * cosX);
        // copy last column from 'this'
        dest.m30(m30);
        dest.m31(m31);
        dest.m32(m32);
        dest.m33(m33);
        dest.properties((byte) (properties & ~(PROPERTY_PERSPECTIVE | PROPERTY_IDENTITY | PROPERTY_TRANSLATION)));
        return dest;
    }

    @Override
    public Matrix4fc rotateAffineZYX(float angleZ, float angleY, float angleX) {
        return rotateAffineZYX(angleZ, angleY, angleX, this);
    }

    @Override
    public Matrix4fc rotateAffineZYX(float angleZ, float angleY, float angleX, Matrix4fc dest) {
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
        dest.m03(0.0f);
        // rotateX
        dest.m10(nm10 * cosX + nm20 * sinX);
        dest.m11(nm11 * cosX + nm21 * sinX);
        dest.m12(nm12 * cosX + nm22 * sinX);
        dest.m13(0.0f);
        dest.m20(nm10 * m_sinX + nm20 * cosX);
        dest.m21(nm11 * m_sinX + nm21 * cosX);
        dest.m22(nm12 * m_sinX + nm22 * cosX);
        dest.m23(0.0f);
        // copy last column from 'this'
        dest.m30(m30);
        dest.m31(m31);
        dest.m32(m32);
        dest.m33(m33);
        dest.properties((byte) (properties & ~(PROPERTY_PERSPECTIVE | PROPERTY_IDENTITY | PROPERTY_TRANSLATION)));
        return dest;
    }

    @Override
    public Matrix4fc rotateYXZ(Vector3fc angles) {
        return rotateYXZ(angles.y(), angles.x(), angles.z());
    }

    @Override
    public Matrix4fc rotateYXZ(float angleY, float angleX, float angleZ) {
        return rotateYXZ(angleY, angleX, angleZ, this);
    }

    @Override
    public Matrix4fc rotateYXZ(float angleY, float angleX, float angleZ, Matrix4fc dest) {
        if ((properties & PROPERTY_IDENTITY) != 0)
            return dest.rotationYXZ(angleY, angleX, angleZ);
        else if ((properties & PROPERTY_AFFINE) != 0)
            return dest.rotateAffineYXZ(angleY, angleX, angleZ);

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
        float nm23 = m03 * sinY + m23 * cosY;
        float nm00 = m00 * cosY + m20 * m_sinY;
        float nm01 = m01 * cosY + m21 * m_sinY;
        float nm02 = m02 * cosY + m22 * m_sinY;
        float nm03 = m03 * cosY + m23 * m_sinY;
        // rotateX
        float nm10 = m10 * cosX + nm20 * sinX;
        float nm11 = m11 * cosX + nm21 * sinX;
        float nm12 = m12 * cosX + nm22 * sinX;
        float nm13 = m13 * cosX + nm23 * sinX;
        dest.m20(m10 * m_sinX + nm20 * cosX);
        dest.m21(m11 * m_sinX + nm21 * cosX);
        dest.m22(m12 * m_sinX + nm22 * cosX);
        dest.m23(m13 * m_sinX + nm23 * cosX);
        // rotateZ
        dest.m00(nm00 * cosZ + nm10 * sinZ);
        dest.m01(nm01 * cosZ + nm11 * sinZ);
        dest.m02(nm02 * cosZ + nm12 * sinZ);
        dest.m03(nm03 * cosZ + nm13 * sinZ);
        dest.m10(nm00 * m_sinZ + nm10 * cosZ);
        dest.m11(nm01 * m_sinZ + nm11 * cosZ);
        dest.m12(nm02 * m_sinZ + nm12 * cosZ);
        dest.m13(nm03 * m_sinZ + nm13 * cosZ);
        // copy last column from 'this'
        dest.m30(m30);
        dest.m31(m31);
        dest.m32(m32);
        dest.m33(m33);
        dest.properties((byte) (properties & ~(PROPERTY_PERSPECTIVE | PROPERTY_IDENTITY | PROPERTY_TRANSLATION)));
        return dest;
    }

    @Override
    public Matrix4fc rotateAffineYXZ(float angleY, float angleX, float angleZ) {
        return rotateAffineYXZ(angleY, angleX, angleZ, this);
    }

    @Override
    public Matrix4fc rotateAffineYXZ(float angleY, float angleX, float angleZ, Matrix4fc dest) {
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
        dest.m23(0.0f);
        // rotateZ
        dest.m00(nm00 * cosZ + nm10 * sinZ);
        dest.m01(nm01 * cosZ + nm11 * sinZ);
        dest.m02(nm02 * cosZ + nm12 * sinZ);
        dest.m03(0.0f);
        dest.m10(nm00 * m_sinZ + nm10 * cosZ);
        dest.m11(nm01 * m_sinZ + nm11 * cosZ);
        dest.m12(nm02 * m_sinZ + nm12 * cosZ);
        dest.m13(0.0f);
        // copy last column from 'this'
        dest.m30(m30);
        dest.m31(m31);
        dest.m32(m32);
        dest.m33(m33);
        dest.properties((byte) (properties & ~(PROPERTY_PERSPECTIVE | PROPERTY_IDENTITY | PROPERTY_TRANSLATION)));
        return dest;
    }

    @Override
    public Matrix4fc rotate(float ang, float x, float y, float z, Matrix4fc dest) {
        if ((properties & PROPERTY_IDENTITY) != 0)
            return dest.rotation(ang, x, y, z);
        else if ((properties & PROPERTY_TRANSLATION) != 0)
            return rotateTranslation(ang, x, y, z, dest);
        else if ((properties & PROPERTY_AFFINE) != 0)
            return rotateAffine(ang, x, y, z, dest);
        return rotateGeneric(ang, x, y, z, dest);
    }

    private Matrix4fc rotateGeneric(float ang, float x, float y, float z, Matrix4fc dest) {
        float s = (float) Math.sin(ang);
        float c = (float) Math.cosFromSin(s, ang);
        float C = 1.0f - c;
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
        float nm00 = m00 * rm00 + m10 * rm01 + m20 * rm02;
        float nm01 = m01 * rm00 + m11 * rm01 + m21 * rm02;
        float nm02 = m02 * rm00 + m12 * rm01 + m22 * rm02;
        float nm03 = m03 * rm00 + m13 * rm01 + m23 * rm02;
        float nm10 = m00 * rm10 + m10 * rm11 + m20 * rm12;
        float nm11 = m01 * rm10 + m11 * rm11 + m21 * rm12;
        float nm12 = m02 * rm10 + m12 * rm11 + m22 * rm12;
        float nm13 = m03 * rm10 + m13 * rm11 + m23 * rm12;
        dest.m20(m00 * rm20 + m10 * rm21 + m20 * rm22);
        dest.m21(m01 * rm20 + m11 * rm21 + m21 * rm22);
        dest.m22(m02 * rm20 + m12 * rm21 + m22 * rm22);
        dest.m23(m03 * rm20 + m13 * rm21 + m23 * rm22);
        dest.m00(nm00);
        dest.m01(nm01);
        dest.m02(nm02);
        dest.m03(nm03);
        dest.m10(nm10);
        dest.m11(nm11);
        dest.m12(nm12);
        dest.m13(nm13);
        dest.m30(m30);
        dest.m31(m31);
        dest.m32(m32);
        dest.m33(m33);
        dest.properties((byte) (properties & ~(PROPERTY_PERSPECTIVE | PROPERTY_IDENTITY | PROPERTY_TRANSLATION)));
        return dest;
    }

    @Override
    public Matrix4fc rotate(float ang, float x, float y, float z) {
        return rotate(ang, x, y, z, this);
    }

    @Override
    public Matrix4fc rotateTranslation(float ang, float x, float y, float z, Matrix4fc dest) {
        float s = (float) Math.sin(ang);
        float c = (float) Math.cosFromSin(s, ang);
        float C = 1.0f - c;
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
        float nm00 = rm00;
        float nm01 = rm01;
        float nm02 = rm02;
        float nm10 = rm10;
        float nm11 = rm11;
        float nm12 = rm12;
        // set non-dependent values directly
        dest.m20(rm20);
        dest.m21(rm21);
        dest.m22(rm22);
        // set other values
        dest.m00(nm00);
        dest.m01(nm01);
        dest.m02(nm02);
        dest.m03(0.0f);
        dest.m10(nm10);
        dest.m11(nm11);
        dest.m12(nm12);
        dest.m13(0.0f);
        dest.m30(m30);
        dest.m31(m31);
        dest.m32(m32);
        dest.m33(m33);
        dest.properties((byte) (properties & ~(PROPERTY_PERSPECTIVE | PROPERTY_IDENTITY | PROPERTY_TRANSLATION)));

        return dest;
    }

    @Override
    public Matrix4fc rotateAffine(float ang, float x, float y, float z, Matrix4fc dest) {
        float s = (float) Math.sin(ang);
        float c = (float) Math.cosFromSin(s, ang);
        float C = 1.0f - c;
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
        dest.m23(0.0f);
        // set other values
        dest.m00(nm00);
        dest.m01(nm01);
        dest.m02(nm02);
        dest.m03(0.0f);
        dest.m10(nm10);
        dest.m11(nm11);
        dest.m12(nm12);
        dest.m13(0.0f);
        dest.m30(m30);
        dest.m31(m31);
        dest.m32(m32);
        dest.m33(m33);
        dest.properties((byte) (properties & ~(PROPERTY_PERSPECTIVE | PROPERTY_IDENTITY | PROPERTY_TRANSLATION)));
        return dest;
    }

    @Override
    public Matrix4fc rotateAffine(float ang, float x, float y, float z) {
        return rotateAffine(ang, x, y, z, this);
    }

    @Override
    public Matrix4fc rotateLocal(float ang, float x, float y, float z, Matrix4fc dest) {
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
        float nm03 = m03;
        float nm10 = lm00 * m10 + lm10 * m11 + lm20 * m12;
        float nm11 = lm01 * m10 + lm11 * m11 + lm21 * m12;
        float nm12 = lm02 * m10 + lm12 * m11 + lm22 * m12;
        float nm13 = m13;
        float nm20 = lm00 * m20 + lm10 * m21 + lm20 * m22;
        float nm21 = lm01 * m20 + lm11 * m21 + lm21 * m22;
        float nm22 = lm02 * m20 + lm12 * m21 + lm22 * m22;
        float nm23 = m23;
        float nm30 = lm00 * m30 + lm10 * m31 + lm20 * m32;
        float nm31 = lm01 * m30 + lm11 * m31 + lm21 * m32;
        float nm32 = lm02 * m30 + lm12 * m31 + lm22 * m32;
        float nm33 = m33;
        dest.m00(nm00);
        dest.m01(nm01);
        dest.m02(nm02);
        dest.m03(nm03);
        dest.m10(nm10);
        dest.m11(nm11);
        dest.m12(nm12);
        dest.m13(nm13);
        dest.m20(nm20);
        dest.m21(nm21);
        dest.m22(nm22);
        dest.m23(nm23);
        dest.m30(nm30);
        dest.m31(nm31);
        dest.m32(nm32);
        dest.m33(nm33);
        dest.properties((byte) (properties & ~(PROPERTY_PERSPECTIVE | PROPERTY_IDENTITY | PROPERTY_TRANSLATION)));
        return dest;
    }

    @Override
    public Matrix4fc rotateLocal(float ang, float x, float y, float z) {
        return rotateLocal(ang, x, y, z, this);
    }

    @Override
    public Matrix4fc rotateLocalX(float ang, Matrix4fc dest) {
        float sin = (float) Math.sin(ang);
        float cos = (float) Math.cosFromSin(sin, ang);
        float nm01 = cos * m01 - sin * m02;
        float nm02 = sin * m01 + cos * m02;
        float nm11 = cos * m11 - sin * m12;
        float nm12 = sin * m11 + cos * m12;
        float nm21 = cos * m21 - sin * m22;
        float nm22 = sin * m21 + cos * m22;
        float nm31 = cos * m31 - sin * m32;
        float nm32 = sin * m31 + cos * m32;
        dest.m00(m00);
        dest.m01(nm01);
        dest.m02(nm02);
        dest.m03(m03);
        dest.m10(m10);
        dest.m11(nm11);
        dest.m12(nm12);
        dest.m13(m13);
        dest.m20(m20);
        dest.m21(nm21);
        dest.m22(nm22);
        dest.m23(m23);
        dest.m30(m30);
        dest.m31(nm31);
        dest.m32(nm32);
        dest.m33(m33);
        dest.properties((byte) (properties & ~(PROPERTY_PERSPECTIVE | PROPERTY_IDENTITY | PROPERTY_TRANSLATION)));
        return dest;
    }

    @Override
    public Matrix4fc rotateLocalX(float ang) {
        return rotateLocalX(ang, this);
    }

    @Override
    public Matrix4fc rotateLocalY(float ang, Matrix4fc dest) {
        float sin = (float) Math.sin(ang);
        float cos = (float) Math.cosFromSin(sin, ang);
        float nm00 =  cos * m00 + sin * m02;
        float nm02 = -sin * m00 + cos * m02;
        float nm10 =  cos * m10 + sin * m12;
        float nm12 = -sin * m10 + cos * m12;
        float nm20 =  cos * m20 + sin * m22;
        float nm22 = -sin * m20 + cos * m22;
        float nm30 =  cos * m30 + sin * m32;
        float nm32 = -sin * m30 + cos * m32;
        dest.m00(nm00);
        dest.m01(m01);
        dest.m02(nm02);
        dest.m03(m03);
        dest.m10(nm10);
        dest.m11(m11);
        dest.m12(nm12);
        dest.m13(m13);
        dest.m20(nm20);
        dest.m21(m21);
        dest.m22(nm22);
        dest.m23(m23);
        dest.m30(nm30);
        dest.m31(m31);
        dest.m32(nm32);
        dest.m33(m33);
        dest.properties((byte) (properties & ~(PROPERTY_PERSPECTIVE | PROPERTY_IDENTITY | PROPERTY_TRANSLATION)));
        return dest;
    }

    @Override
    public Matrix4fc rotateLocalY(float ang) {
        return rotateLocalY(ang, this);
    }

    @Override
    public Matrix4fc rotateLocalZ(float ang, Matrix4fc dest) {
        float sin = (float) Math.sin(ang);
        float cos = (float) Math.cosFromSin(sin, ang);
        float nm00 = cos * m00 - sin * m01;
        float nm01 = sin * m00 + cos * m01;
        float nm10 = cos * m10 - sin * m11;
        float nm11 = sin * m10 + cos * m11;
        float nm20 = cos * m20 - sin * m21;
        float nm21 = sin * m20 + cos * m21;
        float nm30 = cos * m30 - sin * m31;
        float nm31 = sin * m30 + cos * m31;
        dest.m00(nm00);
        dest.m01(nm01);
        dest.m02(m02);
        dest.m03(m03);
        dest.m10(nm10);
        dest.m11(nm11);
        dest.m12(m12);
        dest.m13(m13);
        dest.m20(nm20);
        dest.m21(nm21);
        dest.m22(m22);
        dest.m23(m23);
        dest.m30(nm30);
        dest.m31(nm31);
        dest.m32(m32);
        dest.m33(m33);
        dest.properties((byte) (properties & ~(PROPERTY_PERSPECTIVE | PROPERTY_IDENTITY | PROPERTY_TRANSLATION)));
        return dest;
    }

    @Override
    public Matrix4fc rotateLocalZ(float ang) {
        return rotateLocalZ(ang, this);
    }

    @Override
    public Matrix4fc translate(IVector3f offset) {
        return translate(offset.x(), offset.y(), offset.z());
    }

    @Override
    public Matrix4fc translate(IVector3f offset, Matrix4fc dest) {
        return translate(offset.x(), offset.y(), offset.z(), dest);
    }

    @Override
    public Matrix4fc translate(float x, float y, float z, Matrix4fc dest) {
        if ((properties & PROPERTY_IDENTITY) != 0)
            return dest.translation(x, y, z);
        return translateGeneric(x, y, z, dest);
    }

    private Matrix4fc translateGeneric(float x, float y, float z, Matrix4fc dest) {
        _m00(dest.m00());
        _m01(dest.m01());
        _m02(dest.m02());
        _m03(dest.m03());
        _m10(dest.m10());
        _m11(dest.m11());
        _m12(dest.m12());
        _m13(dest.m13());
        _m20(dest.m20());
        _m21(dest.m21());
        _m22(dest.m22());
        _m23(dest.m23());
        _m30(dest.m30());
        _m31(dest.m31());
        _m32(dest.m32());
        _m33(dest.m33());
        dest.m30(m00 * x + m10 * y + m20 * z + m30);
        dest.m31(m01 * x + m11 * y + m21 * z + m31);
        dest.m32(m02 * x + m12 * y + m22 * z + m32);
        dest.m33(m03 * x + m13 * y + m23 * z + m33);
        dest.properties((byte) (properties & ~(PROPERTY_PERSPECTIVE | PROPERTY_IDENTITY)));
        return dest;
    }

    @Override
    public Matrix4fc translate(float x, float y, float z) {
        if ((properties & PROPERTY_IDENTITY) != 0)
            return translation(x, y, z);
        this._m30(m00 * x + m10 * y + m20 * z + m30);
        this._m31(m01 * x + m11 * y + m21 * z + m31);
        this._m32(m02 * x + m12 * y + m22 * z + m32);
        this._m33(m03 * x + m13 * y + m23 * z + m33);
        properties &= ~(PROPERTY_PERSPECTIVE | PROPERTY_IDENTITY);
        return this;
    }

    @Override
    public Matrix4fc translateLocal(IVector3f offset) {
        return translateLocal(offset.x(), offset.y(), offset.z());
    }

    @Override
    public Matrix4fc translateLocal(IVector3f offset, Matrix4fc dest) {
        return translateLocal(offset.x(), offset.y(), offset.z(), dest);
    }

    @Override
    public Matrix4fc translateLocal(float x, float y, float z, Matrix4fc dest) {
        float nm00 = m00 + x * m03;
        float nm01 = m01 + y * m03;
        float nm02 = m02 + z * m03;
        float nm03 = m03;
        float nm10 = m10 + x * m13;
        float nm11 = m11 + y * m13;
        float nm12 = m12 + z * m13;
        float nm13 = m13;
        float nm20 = m20 + x * m23;
        float nm21 = m21 + y * m23;
        float nm22 = m22 + z * m23;
        float nm23 = m23;
        float nm30 = m30 + x * m33;
        float nm31 = m31 + y * m33;
        float nm32 = m32 + z * m33;
        float nm33 = m33;
        dest.m00(nm00);
        dest.m01(nm01);
        dest.m02(nm02);
        dest.m03(nm03);
        dest.m10(nm10);
        dest.m11(nm11);
        dest.m12(nm12);
        dest.m13(nm13);
        dest.m20(nm20);
        dest.m21(nm21);
        dest.m22(nm22);
        dest.m23(nm23);
        dest.m30(nm30);
        dest.m31(nm31);
        dest.m32(nm32);
        dest.m33(nm33);
        dest.properties((byte) (properties & ~(PROPERTY_PERSPECTIVE | PROPERTY_IDENTITY)));
        return dest;
    }

    @Override
    public Matrix4fc translateLocal(float x, float y, float z) {
        return translateLocal(x, y, z, this);
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeFloat(m00);
        out.writeFloat(m01);
        out.writeFloat(m02);
        out.writeFloat(m03);
        out.writeFloat(m10);
        out.writeFloat(m11);
        out.writeFloat(m12);
        out.writeFloat(m13);
        out.writeFloat(m20);
        out.writeFloat(m21);
        out.writeFloat(m22);
        out.writeFloat(m23);
        out.writeFloat(m30);
        out.writeFloat(m31);
        out.writeFloat(m32);
        out.writeFloat(m33);
    }

    public void readExternal(ObjectInput in) throws IOException,
            ClassNotFoundException {
        this._m00(in.readFloat());
        this._m01(in.readFloat());
        this._m02(in.readFloat());
        this._m03(in.readFloat());
        this._m10(in.readFloat());
        this._m11(in.readFloat());
        this._m12(in.readFloat());
        this._m13(in.readFloat());
        this._m20(in.readFloat());
        this._m21(in.readFloat());
        this._m22(in.readFloat());
        this._m23(in.readFloat());
        this._m30(in.readFloat());
        this._m31(in.readFloat());
        this._m32(in.readFloat());
        this._m33(in.readFloat());
        properties(0);
    }

    @Override
    public Matrix4fc ortho(float left, float right, float bottom, float top, float zNear, float zFar, boolean zZeroToOne, Matrix4fc dest) {
        // calculate right matrix elements
        float rm00 = 2.0f / (right - left);
        float rm11 = 2.0f / (top - bottom);
        float rm22 = (zZeroToOne ? 1.0f : 2.0f) / (zNear - zFar);
        float rm30 = (left + right) / (left - right);
        float rm31 = (top + bottom) / (bottom - top);
        float rm32 = (zZeroToOne ? zNear : (zFar + zNear)) / (zNear - zFar);

        // perform optimized multiplication
        // compute the last column first, because other columns do not depend on it
        dest.m30(m00 * rm30 + m10 * rm31 + m20 * rm32 + m30);
        dest.m31(m01 * rm30 + m11 * rm31 + m21 * rm32 + m31);
        dest.m32(m02 * rm30 + m12 * rm31 + m22 * rm32 + m32);
        dest.m33(m03 * rm30 + m13 * rm31 + m23 * rm32 + m33);
        dest.m00(m00 * rm00);
        dest.m01(m01 * rm00);
        dest.m02(m02 * rm00);
        dest.m03(m03 * rm00);
        dest.m10(m10 * rm11);
        dest.m11(m11 * rm11);
        dest.m12(m12 * rm11);
        dest.m13(m13 * rm11);
        dest.m20(m20 * rm22);
        dest.m21(m21 * rm22);
        dest.m22(m22 * rm22);
        dest.m23(m23 * rm22);
        dest.properties((byte) (properties & ~(PROPERTY_PERSPECTIVE | PROPERTY_IDENTITY | PROPERTY_TRANSLATION)));

        return dest;
    }

    @Override
    public Matrix4fc ortho(float left, float right, float bottom, float top, float zNear, float zFar, Matrix4fc dest) {
        return ortho(left, right, bottom, top, zNear, zFar, false, dest);
    }

    @Override
    public Matrix4fc ortho(float left, float right, float bottom, float top, float zNear, float zFar, boolean zZeroToOne) {
        return ortho(left, right, bottom, top, zNear, zFar, zZeroToOne, this);
    }

    @Override
    public Matrix4fc ortho(float left, float right, float bottom, float top, float zNear, float zFar) {
        return ortho(left, right, bottom, top, zNear, zFar, false);
    }

    @Override
    public Matrix4fc orthoLH(float left, float right, float bottom, float top, float zNear, float zFar, boolean zZeroToOne, Matrix4fc dest) {
        // calculate right matrix elements
        float rm00 = 2.0f / (right - left);
        float rm11 = 2.0f / (top - bottom);
        float rm22 = (zZeroToOne ? 1.0f : 2.0f) / (zFar - zNear);
        float rm30 = (left + right) / (left - right);
        float rm31 = (top + bottom) / (bottom - top);
        float rm32 = (zZeroToOne ? zNear : (zFar + zNear)) / (zNear - zFar);

        // perform optimized multiplication
        // compute the last column first, because other columns do not depend on it
        dest.m30(m00 * rm30 + m10 * rm31 + m20 * rm32 + m30);
        dest.m31(m01 * rm30 + m11 * rm31 + m21 * rm32 + m31);
        dest.m32(m02 * rm30 + m12 * rm31 + m22 * rm32 + m32);
        dest.m33(m03 * rm30 + m13 * rm31 + m23 * rm32 + m33);
        dest.m00(m00 * rm00);
        dest.m01(m01 * rm00);
        dest.m02(m02 * rm00);
        dest.m03(m03 * rm00);
        dest.m10(m10 * rm11);
        dest.m11(m11 * rm11);
        dest.m12(m12 * rm11);
        dest.m13(m13 * rm11);
        dest.m20(m20 * rm22);
        dest.m21(m21 * rm22);
        dest.m22(m22 * rm22);
        dest.m23(m23 * rm22);
        dest.properties((byte) (properties & ~(PROPERTY_PERSPECTIVE | PROPERTY_IDENTITY | PROPERTY_TRANSLATION)));

        return dest;
    }

    @Override
    public Matrix4fc orthoLH(float left, float right, float bottom, float top, float zNear, float zFar, Matrix4fc dest) {
        return orthoLH(left, right, bottom, top, zNear, zFar, false, dest);
    }

    @Override
    public Matrix4fc orthoLH(float left, float right, float bottom, float top, float zNear, float zFar, boolean zZeroToOne) {
        return orthoLH(left, right, bottom, top, zNear, zFar, zZeroToOne, this);
    }

    @Override
    public Matrix4fc orthoLH(float left, float right, float bottom, float top, float zNear, float zFar) {
        return orthoLH(left, right, bottom, top, zNear, zFar, false);
    }

    @Override
    public Matrix4fc setOrtho(float left, float right, float bottom, float top, float zNear, float zFar, boolean zZeroToOne) {
        MemUtil.INSTANCE.identity(this);
        this._m00(2.0f / (right - left));
        this._m11(2.0f / (top - bottom));
        this._m22((zZeroToOne ? 1.0f : 2.0f) / (zNear - zFar));
        this._m30((right + left) / (left - right));
        this._m31((top + bottom) / (bottom - top));
        this._m32((zZeroToOne ? zNear : (zFar + zNear)) / (zNear - zFar));
        properties(PROPERTY_AFFINE);
        return this;
    }

    @Override
    public Matrix4fc setOrtho(float left, float right, float bottom, float top, float zNear, float zFar) {
        return setOrtho(left, right, bottom, top, zNear, zFar, false);
    }

    @Override
    public Matrix4fc setOrthoLH(float left, float right, float bottom, float top, float zNear, float zFar, boolean zZeroToOne) {
        MemUtil.INSTANCE.identity(this);
        this._m00(2.0f / (right - left));
        this._m11(2.0f / (top - bottom));
        this._m22((zZeroToOne ? 1.0f : 2.0f) / (zFar - zNear));
        this._m30((right + left) / (left - right));
        this._m31((top + bottom) / (bottom - top));
        this._m32((zZeroToOne ? zNear : (zFar + zNear)) / (zNear - zFar));
        properties(PROPERTY_AFFINE);
        return this;
    }

    @Override
    public Matrix4fc setOrthoLH(float left, float right, float bottom, float top, float zNear, float zFar) {
        return setOrthoLH(left, right, bottom, top, zNear, zFar, false);
    }

    @Override
    public Matrix4fc orthoSymmetric(float width, float height, float zNear, float zFar, boolean zZeroToOne, Matrix4fc dest) {
        // calculate right matrix elements
        float rm00 = 2.0f / width;
        float rm11 = 2.0f / height;
        float rm22 = (zZeroToOne ? 1.0f : 2.0f) / (zNear - zFar);
        float rm32 = (zZeroToOne ? zNear : (zFar + zNear)) / (zNear - zFar);

        // perform optimized multiplication
        // compute the last column first, because other columns do not depend on it
        dest.m30(m20 * rm32 + m30);
        dest.m31(m21 * rm32 + m31);
        dest.m32(m22 * rm32 + m32);
        dest.m33(m23 * rm32 + m33);
        dest.m00(m00 * rm00);
        dest.m01(m01 * rm00);
        dest.m02(m02 * rm00);
        dest.m03(m03 * rm00);
        dest.m10(m10 * rm11);
        dest.m11(m11 * rm11);
        dest.m12(m12 * rm11);
        dest.m13(m13 * rm11);
        dest.m20(m20 * rm22);
        dest.m21(m21 * rm22);
        dest.m22(m22 * rm22);
        dest.m23(m23 * rm22);
        dest.properties((byte) (properties & ~(PROPERTY_PERSPECTIVE | PROPERTY_IDENTITY | PROPERTY_TRANSLATION)));

        return dest;
    }

    @Override
    public Matrix4fc orthoSymmetric(float width, float height, float zNear, float zFar, Matrix4fc dest) {
        return orthoSymmetric(width, height, zNear, zFar, false, dest);
    }

    @Override
    public Matrix4fc orthoSymmetric(float width, float height, float zNear, float zFar, boolean zZeroToOne) {
        return orthoSymmetric(width, height, zNear, zFar, zZeroToOne, this);
    }

    @Override
    public Matrix4fc orthoSymmetric(float width, float height, float zNear, float zFar) {
        return orthoSymmetric(width, height, zNear, zFar, false, this);
    }

    @Override
    public Matrix4fc orthoSymmetricLH(float width, float height, float zNear, float zFar, boolean zZeroToOne, Matrix4fc dest) {
        // calculate right matrix elements
        float rm00 = 2.0f / width;
        float rm11 = 2.0f / height;
        float rm22 = (zZeroToOne ? 1.0f : 2.0f) / (zFar - zNear);
        float rm32 = (zZeroToOne ? zNear : (zFar + zNear)) / (zNear - zFar);

        // perform optimized multiplication
        // compute the last column first, because other columns do not depend on it
        dest.m30(m20 * rm32 + m30);
        dest.m31(m21 * rm32 + m31);
        dest.m32(m22 * rm32 + m32);
        dest.m33(m23 * rm32 + m33);
        dest.m00(m00 * rm00);
        dest.m01(m01 * rm00);
        dest.m02(m02 * rm00);
        dest.m03(m03 * rm00);
        dest.m10(m10 * rm11);
        dest.m11(m11 * rm11);
        dest.m12(m12 * rm11);
        dest.m13(m13 * rm11);
        dest.m20(m20 * rm22);
        dest.m21(m21 * rm22);
        dest.m22(m22 * rm22);
        dest.m23(m23 * rm22);
        dest.properties((byte) (properties & ~(PROPERTY_PERSPECTIVE | PROPERTY_IDENTITY | PROPERTY_TRANSLATION)));

        return dest;
    }

    @Override
    public Matrix4fc orthoSymmetricLH(float width, float height, float zNear, float zFar, Matrix4fc dest) {
        return orthoSymmetricLH(width, height, zNear, zFar, false, dest);
    }

    @Override
    public Matrix4fc orthoSymmetricLH(float width, float height, float zNear, float zFar, boolean zZeroToOne) {
        return orthoSymmetricLH(width, height, zNear, zFar, zZeroToOne, this);
    }

    @Override
    public Matrix4fc orthoSymmetricLH(float width, float height, float zNear, float zFar) {
        return orthoSymmetricLH(width, height, zNear, zFar, false, this);
    }

    @Override
    public Matrix4fc setOrthoSymmetric(float width, float height, float zNear, float zFar, boolean zZeroToOne) {
        MemUtil.INSTANCE.identity(this);
        this._m00(2.0f / width);
        this._m11(2.0f / height);
        this._m22((zZeroToOne ? 1.0f : 2.0f) / (zNear - zFar));
        this._m32((zZeroToOne ? zNear : (zFar + zNear)) / (zNear - zFar));
        properties(PROPERTY_AFFINE);
        return this;
    }

    @Override
    public Matrix4fc setOrthoSymmetric(float width, float height, float zNear, float zFar) {
        return setOrthoSymmetric(width, height, zNear, zFar, false);
    }

    @Override
    public Matrix4fc setOrthoSymmetricLH(float width, float height, float zNear, float zFar, boolean zZeroToOne) {
        MemUtil.INSTANCE.identity(this);
        this._m00(2.0f / width);
        this._m11(2.0f / height);
        this._m22((zZeroToOne ? 1.0f : 2.0f) / (zFar - zNear));
        this._m32((zZeroToOne ? zNear : (zFar + zNear)) / (zNear - zFar));
        properties(PROPERTY_AFFINE);
        return this;
    }

    @Override
    public Matrix4fc setOrthoSymmetricLH(float width, float height, float zNear, float zFar) {
        return setOrthoSymmetricLH(width, height, zNear, zFar, false);
    }

    @Override
    public Matrix4fc ortho2D(float left, float right, float bottom, float top, Matrix4fc dest) {
        // calculate right matrix elements
        float rm00 = 2.0f / (right - left);
        float rm11 = 2.0f / (top - bottom);
        float rm30 = -(right + left) / (right - left);
        float rm31 = -(top + bottom) / (top - bottom);

        // perform optimized multiplication
        // compute the last column first, because other columns do not depend on it
        dest.m30(m00 * rm30 + m10 * rm31 + m30);
        dest.m31(m01 * rm30 + m11 * rm31 + m31);
        dest.m32(m02 * rm30 + m12 * rm31 + m32);
        dest.m33(m03 * rm30 + m13 * rm31 + m33);
        dest.m00(m00 * rm00);
        dest.m01(m01 * rm00);
        dest.m02(m02 * rm00);
        dest.m03(m03 * rm00);
        dest.m10(m10 * rm11);
        dest.m11(m11 * rm11);
        dest.m12(m12 * rm11);
        dest.m13(m13 * rm11);
        dest.m20(-m20);
        dest.m21(-m21);
        dest.m22(-m22);
        dest.m23(-m23);
        dest.properties((byte) (properties & ~(PROPERTY_PERSPECTIVE | PROPERTY_IDENTITY | PROPERTY_TRANSLATION)));

        return dest;
    }

    @Override
    public Matrix4fc ortho2D(float left, float right, float bottom, float top) {
        return ortho2D(left, right, bottom, top, this);
    }

    @Override
    public Matrix4fc ortho2DLH(float left, float right, float bottom, float top, Matrix4fc dest) {
        // calculate right matrix elements
        float rm00 = 2.0f / (right - left);
        float rm11 = 2.0f / (top - bottom);
        float rm30 = -(right + left) / (right - left);
        float rm31 = -(top + bottom) / (top - bottom);

        // perform optimized multiplication
        // compute the last column first, because other columns do not depend on it
        dest.m30(m00 * rm30 + m10 * rm31 + m30);
        dest.m31(m01 * rm30 + m11 * rm31 + m31);
        dest.m32(m02 * rm30 + m12 * rm31 + m32);
        dest.m33(m03 * rm30 + m13 * rm31 + m33);
        dest.m00(m00 * rm00);
        dest.m01(m01 * rm00);
        dest.m02(m02 * rm00);
        dest.m03(m03 * rm00);
        dest.m10(m10 * rm11);
        dest.m11(m11 * rm11);
        dest.m12(m12 * rm11);
        dest.m13(m13 * rm11);
        dest.m20(m20);
        dest.m21(m21);
        dest.m22(m22);
        dest.m23(m23);
        dest.properties((byte) (properties & ~(PROPERTY_PERSPECTIVE | PROPERTY_IDENTITY | PROPERTY_TRANSLATION)));

        return dest;
    }

    @Override
    public Matrix4fc ortho2DLH(float left, float right, float bottom, float top) {
        return ortho2DLH(left, right, bottom, top, this);
    }

    @Override
    public Matrix4fc setOrtho2D(float left, float right, float bottom, float top) {
        MemUtil.INSTANCE.identity(this);
        this._m00(2.0f / (right - left));
        this._m11(2.0f / (top - bottom));
        this._m22(-1.0f);
        this._m30(-(right + left) / (right - left));
        this._m31(-(top + bottom) / (top - bottom));
        properties(PROPERTY_AFFINE);
        return this;
    }

    @Override
    public Matrix4fc setOrtho2DLH(float left, float right, float bottom, float top) {
        MemUtil.INSTANCE.identity(this);
        this._m00(2.0f / (right - left));
        this._m11(2.0f / (top - bottom));
        this._m22(1.0f);
        this._m30(-(right + left) / (right - left));
        this._m31(-(top + bottom) / (top - bottom));
        properties(PROPERTY_AFFINE);
        return this;
    }

    @Override
    public Matrix4fc lookAlong(IVector3f dir, IVector3f up) {
        return lookAlong(dir.x(), dir.y(), dir.z(), up.x(), up.y(), up.z(), this);
    }

    @Override
    public Matrix4fc lookAlong(IVector3f dir, IVector3f up, Matrix4fc dest) {
        return lookAlong(dir.x(), dir.y(), dir.z(), up.x(), up.y(), up.z(), dest);
    }

    @Override
    public Matrix4fc lookAlong(float dirX, float dirY, float dirZ,
                              float upX, float upY, float upZ, Matrix4fc dest) {
        if ((properties & PROPERTY_IDENTITY) != 0)
            return setLookAlong(dirX, dirY, dirZ, upX, upY, upZ);

        // Normalize direction
        float invDirLength = 1.0f / (float) Math.sqrt(dirX * dirX + dirY * dirY + dirZ * dirZ);
        float dirnX = -dirX * invDirLength;
        float dirnY = -dirY * invDirLength;
        float dirnZ = -dirZ * invDirLength;
        // right = direction x up
        float rightX, rightY, rightZ;
        rightX = dirnY * upZ - dirnZ * upY;
        rightY = dirnZ * upX - dirnX * upZ;
        rightZ = dirnX * upY - dirnY * upX;
        // normalize right
        float invRightLength = 1.0f / (float) Math.sqrt(rightX * rightX + rightY * rightY + rightZ * rightZ);
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
        float nm03 = m03 * rm00 + m13 * rm01 + m23 * rm02;
        float nm10 = m00 * rm10 + m10 * rm11 + m20 * rm12;
        float nm11 = m01 * rm10 + m11 * rm11 + m21 * rm12;
        float nm12 = m02 * rm10 + m12 * rm11 + m22 * rm12;
        float nm13 = m03 * rm10 + m13 * rm11 + m23 * rm12;
        dest.m20(m00 * rm20 + m10 * rm21 + m20 * rm22);
        dest.m21(m01 * rm20 + m11 * rm21 + m21 * rm22);
        dest.m22(m02 * rm20 + m12 * rm21 + m22 * rm22);
        dest.m23(m03 * rm20 + m13 * rm21 + m23 * rm22);
        // set the rest of the matrix elements
        dest.m00(nm00);
        dest.m01(nm01);
        dest.m02(nm02);
        dest.m03(nm03);
        dest.m10(nm10);
        dest.m11(nm11);
        dest.m12(nm12);
        dest.m13(nm13);
        dest.m30(m30);
        dest.m31(m31);
        dest.m32(m32);
        dest.m33(m33);
        dest.properties((byte) (properties & ~(PROPERTY_PERSPECTIVE | PROPERTY_IDENTITY | PROPERTY_TRANSLATION)));

        return dest;
    }

    @Override
    public Matrix4fc lookAlong(float dirX, float dirY, float dirZ,
                              float upX, float upY, float upZ) {
        return lookAlong(dirX, dirY, dirZ, upX, upY, upZ, this);
    }

    @Override
    public Matrix4fc setLookAlong(IVector3f dir, IVector3f up) {
        return setLookAlong(dir.x(), dir.y(), dir.z(), up.x(), up.y(), up.z());
    }

    @Override
    public Matrix4fc setLookAlong(float dirX, float dirY, float dirZ,
                                 float upX, float upY, float upZ) {
        // Normalize direction
        float invDirLength = 1.0f / (float) Math.sqrt(dirX * dirX + dirY * dirY + dirZ * dirZ);
        float dirnX = -dirX * invDirLength;
        float dirnY = -dirY * invDirLength;
        float dirnZ = -dirZ * invDirLength;
        // right = direction x up
        float rightX, rightY, rightZ;
        rightX = dirnY * upZ - dirnZ * upY;
        rightY = dirnZ * upX - dirnX * upZ;
        rightZ = dirnX * upY - dirnY * upX;
        // normalize right
        float invRightLength = 1.0f / (float) Math.sqrt(rightX * rightX + rightY * rightY + rightZ * rightZ);
        rightX *= invRightLength;
        rightY *= invRightLength;
        rightZ *= invRightLength;
        // up = right x direction
        float upnX = rightY * dirnZ - rightZ * dirnY;
        float upnY = rightZ * dirnX - rightX * dirnZ;
        float upnZ = rightX * dirnY - rightY * dirnX;

        this._m00(rightX);
        this._m01(upnX);
        this._m02(-dirnX);
        this._m03(0.0f);
        this._m10(rightY);
        this._m11(upnY);
        this._m12(-dirnY);
        this._m13(0.0f);
        this._m20(rightZ);
        this._m21(upnZ);
        this._m22(-dirnZ);
        this._m23(0.0f);
        this._m30(0.0f);
        this._m31(0.0f);
        this._m32(0.0f);
        this._m33(1.0f);
        properties(PROPERTY_AFFINE);

        return this;
    }

    @Override
    public Matrix4fc setLookAt(IVector3f eye, IVector3f center, IVector3f up) {
        return setLookAt(eye.x(), eye.y(), eye.z(), center.x(), center.y(), center.z(), up.x(), up.y(), up.z());
    }

    @Override
    public Matrix4fc setLookAt(float eyeX, float eyeY, float eyeZ,
                              float centerX, float centerY, float centerZ,
                              float upX, float upY, float upZ) {
        // Compute direction from position to lookAt
        float dirX, dirY, dirZ;
        dirX = eyeX - centerX;
        dirY = eyeY - centerY;
        dirZ = eyeZ - centerZ;
        // Normalize direction
        float invDirLength = 1.0f / (float) Math.sqrt(dirX * dirX + dirY * dirY + dirZ * dirZ);
        dirX *= invDirLength;
        dirY *= invDirLength;
        dirZ *= invDirLength;
        // left = up x direction
        float leftX, leftY, leftZ;
        leftX = upY * dirZ - upZ * dirY;
        leftY = upZ * dirX - upX * dirZ;
        leftZ = upX * dirY - upY * dirX;
        // normalize left
        float invLeftLength = 1.0f / (float) Math.sqrt(leftX * leftX + leftY * leftY + leftZ * leftZ);
        leftX *= invLeftLength;
        leftY *= invLeftLength;
        leftZ *= invLeftLength;
        // up = direction x left
        float upnX = dirY * leftZ - dirZ * leftY;
        float upnY = dirZ * leftX - dirX * leftZ;
        float upnZ = dirX * leftY - dirY * leftX;

        this._m00(leftX);
        this._m01(upnX);
        this._m02(dirX);
        this._m03(0.0f);
        this._m10(leftY);
        this._m11(upnY);
        this._m12(dirY);
        this._m13(0.0f);
        this._m20(leftZ);
        this._m21(upnZ);
        this._m22(dirZ);
        this._m23(0.0f);
        this._m30(-(leftX * eyeX + leftY * eyeY + leftZ * eyeZ));
        this._m31(-(upnX * eyeX + upnY * eyeY + upnZ * eyeZ));
        this._m32(-(dirX * eyeX + dirY * eyeY + dirZ * eyeZ));
        this._m33(1.0f);
        properties(PROPERTY_AFFINE);

        return this;
    }

    @Override
    public Matrix4fc lookAt(IVector3f eye, IVector3f center, IVector3f up, Matrix4fc dest) {
        return lookAt(eye.x(), eye.y(), eye.z(), center.x(), center.y(), center.z(), up.x(), up.y(), up.z(), dest);
    }

    @Override
    public Matrix4fc lookAt(IVector3f eye, IVector3f center, IVector3f up) {
        return lookAt(eye.x(), eye.y(), eye.z(), center.x(), center.y(), center.z(), up.x(), up.y(), up.z(), this);
    }

    @Override
    public Matrix4fc lookAt(float eyeX, float eyeY, float eyeZ,
                           float centerX, float centerY, float centerZ,
                           float upX, float upY, float upZ, Matrix4fc dest) {
        if ((properties & PROPERTY_IDENTITY) != 0)
            return dest.setLookAt(eyeX, eyeY, eyeZ, centerX, centerY, centerZ, upX, upY, upZ);
        else if ((properties & PROPERTY_PERSPECTIVE) != 0)
            return lookAtPerspective(eyeX, eyeY, eyeZ, centerX, centerY, centerZ, upX, upY, upZ, dest);
        return lookAtGeneric(eyeX, eyeY, eyeZ, centerX, centerY, centerZ, upX, upY, upZ, dest);
    }

    private Matrix4fc lookAtGeneric(float eyeX, float eyeY, float eyeZ,
                                   float centerX, float centerY, float centerZ,
                                   float upX, float upY, float upZ, Matrix4fc dest) {
        // Compute direction from position to lookAt
        float dirX, dirY, dirZ;
        dirX = eyeX - centerX;
        dirY = eyeY - centerY;
        dirZ = eyeZ - centerZ;
        // Normalize direction
        float invDirLength = 1.0f / (float) Math.sqrt(dirX * dirX + dirY * dirY + dirZ * dirZ);
        dirX *= invDirLength;
        dirY *= invDirLength;
        dirZ *= invDirLength;
        // left = up x direction
        float leftX, leftY, leftZ;
        leftX = upY * dirZ - upZ * dirY;
        leftY = upZ * dirX - upX * dirZ;
        leftZ = upX * dirY - upY * dirX;
        // normalize left
        float invLeftLength = 1.0f / (float) Math.sqrt(leftX * leftX + leftY * leftY + leftZ * leftZ);
        leftX *= invLeftLength;
        leftY *= invLeftLength;
        leftZ *= invLeftLength;
        // up = direction x left
        float upnX = dirY * leftZ - dirZ * leftY;
        float upnY = dirZ * leftX - dirX * leftZ;
        float upnZ = dirX * leftY - dirY * leftX;

        // calculate right matrix elements
        float rm00 = leftX;
        float rm01 = upnX;
        float rm02 = dirX;
        float rm10 = leftY;
        float rm11 = upnY;
        float rm12 = dirY;
        float rm20 = leftZ;
        float rm21 = upnZ;
        float rm22 = dirZ;
        float rm30 = -(leftX * eyeX + leftY * eyeY + leftZ * eyeZ);
        float rm31 = -(upnX * eyeX + upnY * eyeY + upnZ * eyeZ);
        float rm32 = -(dirX * eyeX + dirY * eyeY + dirZ * eyeZ);

        // perform optimized matrix multiplication
        // compute last column first, because others do not depend on it
        dest.m30(m00 * rm30 + m10 * rm31 + m20 * rm32 + m30);
        dest.m31(m01 * rm30 + m11 * rm31 + m21 * rm32 + m31);
        dest.m32(m02 * rm30 + m12 * rm31 + m22 * rm32 + m32);
        dest.m33(m03 * rm30 + m13 * rm31 + m23 * rm32 + m33);
        // introduce temporaries for dependent results
        float nm00 = m00 * rm00 + m10 * rm01 + m20 * rm02;
        float nm01 = m01 * rm00 + m11 * rm01 + m21 * rm02;
        float nm02 = m02 * rm00 + m12 * rm01 + m22 * rm02;
        float nm03 = m03 * rm00 + m13 * rm01 + m23 * rm02;
        float nm10 = m00 * rm10 + m10 * rm11 + m20 * rm12;
        float nm11 = m01 * rm10 + m11 * rm11 + m21 * rm12;
        float nm12 = m02 * rm10 + m12 * rm11 + m22 * rm12;
        float nm13 = m03 * rm10 + m13 * rm11 + m23 * rm12;
        dest.m20(m00 * rm20 + m10 * rm21 + m20 * rm22);
        dest.m21(m01 * rm20 + m11 * rm21 + m21 * rm22);
        dest.m22(m02 * rm20 + m12 * rm21 + m22 * rm22);
        dest.m23(m03 * rm20 + m13 * rm21 + m23 * rm22);
        // set the rest of the matrix elements
        dest.m00(nm00);
        dest.m01(nm01);
        dest.m02(nm02);
        dest.m03(nm03);
        dest.m10(nm10);
        dest.m11(nm11);
        dest.m12(nm12);
        dest.m13(nm13);
        dest.properties((byte) (properties & ~(PROPERTY_PERSPECTIVE | PROPERTY_IDENTITY | PROPERTY_TRANSLATION)));
        return dest;
    }

    @Override
    public Matrix4fc lookAtPerspective(float eyeX, float eyeY, float eyeZ,
            float centerX, float centerY, float centerZ,
            float upX, float upY, float upZ, Matrix4fc dest) {
        // Compute direction from position to lookAt
        float dirX, dirY, dirZ;
        dirX = eyeX - centerX;
        dirY = eyeY - centerY;
        dirZ = eyeZ - centerZ;
        // Normalize direction
        float invDirLength = 1.0f / (float) Math.sqrt(dirX * dirX + dirY * dirY + dirZ * dirZ);
        dirX *= invDirLength;
        dirY *= invDirLength;
        dirZ *= invDirLength;
        // left = up x direction
        float leftX, leftY, leftZ;
        leftX = upY * dirZ - upZ * dirY;
        leftY = upZ * dirX - upX * dirZ;
        leftZ = upX * dirY - upY * dirX;
        // normalize left
        float invLeftLength = 1.0f / (float) Math.sqrt(leftX * leftX + leftY * leftY + leftZ * leftZ);
        leftX *= invLeftLength;
        leftY *= invLeftLength;
        leftZ *= invLeftLength;
        // up = direction x left
        float upnX = dirY * leftZ - dirZ * leftY;
        float upnY = dirZ * leftX - dirX * leftZ;
        float upnZ = dirX * leftY - dirY * leftX;

        // calculate right matrix elements
        float rm00 = leftX;
        float rm01 = upnX;
        float rm02 = dirX;
        float rm10 = leftY;
        float rm11 = upnY;
        float rm12 = dirY;
        float rm20 = leftZ;
        float rm21 = upnZ;
        float rm22 = dirZ;
        float rm30 = -(leftX * eyeX + leftY * eyeY + leftZ * eyeZ);
        float rm31 = -(upnX * eyeX + upnY * eyeY + upnZ * eyeZ);
        float rm32 = -(dirX * eyeX + dirY * eyeY + dirZ * eyeZ);

        float nm00 = m00 * rm00;
        float nm01 = m11 * rm01;
        float nm02 = m22 * rm02;
        float nm03 = m23 * rm02;
        float nm10 = m00 * rm10;
        float nm11 = m11 * rm11;
        float nm12 = m22 * rm12;
        float nm13 = m23 * rm12;
        float nm20 = m00 * rm20;
        float nm21 = m11 * rm21;
        float nm22 = m22 * rm22;
        float nm23 = m23 * rm22;
        float nm30 = m00 * rm30;
        float nm31 = m11 * rm31;
        float nm32 = m22 * rm32 + m32;
        float nm33 = m23 * rm32;
        dest.m00(nm00);
        dest.m01(nm01);
        dest.m02(nm02);
        dest.m03(nm03);
        dest.m10(nm10);
        dest.m11(nm11);
        dest.m12(nm12);
        dest.m13(nm13);
        dest.m20(nm20);
        dest.m21(nm21);
        dest.m22(nm22);
        dest.m23(nm23);
        dest.m30(nm30);
        dest.m31(nm31);
        dest.m32(nm32);
        dest.m33(nm33);
        dest.properties(0);

        return dest;
    }

    @Override
    public Matrix4fc lookAt(float eyeX, float eyeY, float eyeZ,
                           float centerX, float centerY, float centerZ,
                           float upX, float upY, float upZ) {
        return lookAt(eyeX, eyeY, eyeZ, centerX, centerY, centerZ, upX, upY, upZ, this);
    }

    @Override
    public Matrix4fc setLookAtLH(IVector3f eye, IVector3f center, IVector3f up) {
        return setLookAtLH(eye.x(), eye.y(), eye.z(), center.x(), center.y(), center.z(), up.x(), up.y(), up.z());
    }

    @Override
    public Matrix4fc setLookAtLH(float eyeX, float eyeY, float eyeZ,
                                float centerX, float centerY, float centerZ,
                                float upX, float upY, float upZ) {
        // Compute direction from position to lookAt
        float dirX, dirY, dirZ;
        dirX = centerX - eyeX;
        dirY = centerY - eyeY;
        dirZ = centerZ - eyeZ;
        // Normalize direction
        float invDirLength = 1.0f / (float) Math.sqrt(dirX * dirX + dirY * dirY + dirZ * dirZ);
        dirX *= invDirLength;
        dirY *= invDirLength;
        dirZ *= invDirLength;
        // left = up x direction
        float leftX, leftY, leftZ;
        leftX = upY * dirZ - upZ * dirY;
        leftY = upZ * dirX - upX * dirZ;
        leftZ = upX * dirY - upY * dirX;
        // normalize left
        float invLeftLength = 1.0f / (float) Math.sqrt(leftX * leftX + leftY * leftY + leftZ * leftZ);
        leftX *= invLeftLength;
        leftY *= invLeftLength;
        leftZ *= invLeftLength;
        // up = direction x left
        float upnX = dirY * leftZ - dirZ * leftY;
        float upnY = dirZ * leftX - dirX * leftZ;
        float upnZ = dirX * leftY - dirY * leftX;

        this._m00(leftX);
        this._m01(upnX);
        this._m02(dirX);
        this._m03(0.0f);
        this._m10(leftY);
        this._m11(upnY);
        this._m12(dirY);
        this._m13(0.0f);
        this._m20(leftZ);
        this._m21(upnZ);
        this._m22(dirZ);
        this._m23(0.0f);
        this._m30(-(leftX * eyeX + leftY * eyeY + leftZ * eyeZ));
        this._m31(-(upnX * eyeX + upnY * eyeY + upnZ * eyeZ));
        this._m32(-(dirX * eyeX + dirY * eyeY + dirZ * eyeZ));
        this._m33(1.0f);
        properties(PROPERTY_AFFINE);

        return this;
    }

    @Override
    public Matrix4fc lookAtLH(IVector3f eye, IVector3f center, IVector3f up, Matrix4fc dest) {
        return lookAtLH(eye.x(), eye.y(), eye.z(), center.x(), center.y(), center.z(), up.x(), up.y(), up.z(), dest);
    }

    @Override
    public Matrix4fc lookAtLH(IVector3f eye, IVector3f center, IVector3f up) {
        return lookAtLH(eye.x(), eye.y(), eye.z(), center.x(), center.y(), center.z(), up.x(), up.y(), up.z(), this);
    }

    @Override
    public Matrix4fc lookAtLH(float eyeX, float eyeY, float eyeZ,
                             float centerX, float centerY, float centerZ,
                             float upX, float upY, float upZ, Matrix4fc dest) {
        if ((properties & PROPERTY_IDENTITY) != 0)
            return dest.setLookAtLH(eyeX, eyeY, eyeZ, centerX, centerY, centerZ, upX, upY, upZ);
        else if ((properties & PROPERTY_PERSPECTIVE) != 0)
            return lookAtPerspectiveLH(eyeX, eyeY, eyeZ, centerX, centerY, centerZ, upX, upY, upZ, dest);
        return lookAtLHGeneric(eyeX, eyeY, eyeZ, centerX, centerY, centerZ, upX, upY, upZ, dest);
    }
    private Matrix4fc lookAtLHGeneric(float eyeX, float eyeY, float eyeZ,
                                     float centerX, float centerY, float centerZ,
                                     float upX, float upY, float upZ, Matrix4fc dest) {
        // Compute direction from position to lookAt
        float dirX, dirY, dirZ;
        dirX = centerX - eyeX;
        dirY = centerY - eyeY;
        dirZ = centerZ - eyeZ;
        // Normalize direction
        float invDirLength = 1.0f / (float) Math.sqrt(dirX * dirX + dirY * dirY + dirZ * dirZ);
        dirX *= invDirLength;
        dirY *= invDirLength;
        dirZ *= invDirLength;
        // left = up x direction
        float leftX, leftY, leftZ;
        leftX = upY * dirZ - upZ * dirY;
        leftY = upZ * dirX - upX * dirZ;
        leftZ = upX * dirY - upY * dirX;
        // normalize left
        float invLeftLength = 1.0f / (float) Math.sqrt(leftX * leftX + leftY * leftY + leftZ * leftZ);
        leftX *= invLeftLength;
        leftY *= invLeftLength;
        leftZ *= invLeftLength;
        // up = direction x left
        float upnX = dirY * leftZ - dirZ * leftY;
        float upnY = dirZ * leftX - dirX * leftZ;
        float upnZ = dirX * leftY - dirY * leftX;

        // calculate right matrix elements
        float rm00 = leftX;
        float rm01 = upnX;
        float rm02 = dirX;
        float rm10 = leftY;
        float rm11 = upnY;
        float rm12 = dirY;
        float rm20 = leftZ;
        float rm21 = upnZ;
        float rm22 = dirZ;
        float rm30 = -(leftX * eyeX + leftY * eyeY + leftZ * eyeZ);
        float rm31 = -(upnX * eyeX + upnY * eyeY + upnZ * eyeZ);
        float rm32 = -(dirX * eyeX + dirY * eyeY + dirZ * eyeZ);

        // perform optimized matrix multiplication
        // compute last column first, because others do not depend on it
        dest.m30(m00 * rm30 + m10 * rm31 + m20 * rm32 + m30);
        dest.m31(m01 * rm30 + m11 * rm31 + m21 * rm32 + m31);
        dest.m32(m02 * rm30 + m12 * rm31 + m22 * rm32 + m32);
        dest.m33(m03 * rm30 + m13 * rm31 + m23 * rm32 + m33);
        // introduce temporaries for dependent results
        float nm00 = m00 * rm00 + m10 * rm01 + m20 * rm02;
        float nm01 = m01 * rm00 + m11 * rm01 + m21 * rm02;
        float nm02 = m02 * rm00 + m12 * rm01 + m22 * rm02;
        float nm03 = m03 * rm00 + m13 * rm01 + m23 * rm02;
        float nm10 = m00 * rm10 + m10 * rm11 + m20 * rm12;
        float nm11 = m01 * rm10 + m11 * rm11 + m21 * rm12;
        float nm12 = m02 * rm10 + m12 * rm11 + m22 * rm12;
        float nm13 = m03 * rm10 + m13 * rm11 + m23 * rm12;
        dest.m20(m00 * rm20 + m10 * rm21 + m20 * rm22);
        dest.m21(m01 * rm20 + m11 * rm21 + m21 * rm22);
        dest.m22(m02 * rm20 + m12 * rm21 + m22 * rm22);
        dest.m23(m03 * rm20 + m13 * rm21 + m23 * rm22);
        // set the rest of the matrix elements
        dest.m00(nm00);
        dest.m01(nm01);
        dest.m02(nm02);
        dest.m03(nm03);
        dest.m10(nm10);
        dest.m11(nm11);
        dest.m12(nm12);
        dest.m13(nm13);
        dest.properties((byte) (properties & ~(PROPERTY_PERSPECTIVE | PROPERTY_IDENTITY | PROPERTY_TRANSLATION)));

        return dest;
    }

    @Override
    public Matrix4fc lookAtLH(float eyeX, float eyeY, float eyeZ,
                             float centerX, float centerY, float centerZ,
                             float upX, float upY, float upZ) {
        return lookAtLH(eyeX, eyeY, eyeZ, centerX, centerY, centerZ, upX, upY, upZ, this);
    }

    @Override
    public Matrix4fc lookAtPerspectiveLH(float eyeX, float eyeY, float eyeZ,
            float centerX, float centerY, float centerZ,
            float upX, float upY, float upZ, Matrix4fc dest) {
        // Compute direction from position to lookAt
        float dirX, dirY, dirZ;
        dirX = centerX - eyeX;
        dirY = centerY - eyeY;
        dirZ = centerZ - eyeZ;
        // Normalize direction
        float invDirLength = 1.0f / (float) Math.sqrt(dirX * dirX + dirY * dirY + dirZ * dirZ);
        dirX *= invDirLength;
        dirY *= invDirLength;
        dirZ *= invDirLength;
        // left = up x direction
        float leftX, leftY, leftZ;
        leftX = upY * dirZ - upZ * dirY;
        leftY = upZ * dirX - upX * dirZ;
        leftZ = upX * dirY - upY * dirX;
        // normalize left
        float invLeftLength = 1.0f / (float) Math.sqrt(leftX * leftX + leftY * leftY + leftZ * leftZ);
        leftX *= invLeftLength;
        leftY *= invLeftLength;
        leftZ *= invLeftLength;
        // up = direction x left
        float upnX = dirY * leftZ - dirZ * leftY;
        float upnY = dirZ * leftX - dirX * leftZ;
        float upnZ = dirX * leftY - dirY * leftX;

        // calculate right matrix elements
        float rm00 = leftX;
        float rm01 = upnX;
        float rm02 = dirX;
        float rm10 = leftY;
        float rm11 = upnY;
        float rm12 = dirY;
        float rm20 = leftZ;
        float rm21 = upnZ;
        float rm22 = dirZ;
        float rm30 = -(leftX * eyeX + leftY * eyeY + leftZ * eyeZ);
        float rm31 = -(upnX * eyeX + upnY * eyeY + upnZ * eyeZ);
        float rm32 = -(dirX * eyeX + dirY * eyeY + dirZ * eyeZ);

        float nm00 = m00 * rm00;
        float nm01 = m11 * rm01;
        float nm02 = m22 * rm02;
        float nm03 = m23 * rm02;
        float nm10 = m00 * rm10;
        float nm11 = m11 * rm11;
        float nm12 = m22 * rm12;
        float nm13 = m23 * rm12;
        float nm20 = m00 * rm20;
        float nm21 = m11 * rm21;
        float nm22 = m22 * rm22;
        float nm23 = m23 * rm22;
        float nm30 = m00 * rm30;
        float nm31 = m11 * rm31;
        float nm32 = m22 * rm32 + m32;
        float nm33 = m23 * rm32;
        dest.m00(nm00);
        dest.m01(nm01);
        dest.m02(nm02);
        dest.m03(nm03);
        dest.m10(nm10);
        dest.m11(nm11);
        dest.m12(nm12);
        dest.m13(nm13);
        dest.m20(nm20);
        dest.m21(nm21);
        dest.m22(nm22);
        dest.m23(nm23);
        dest.m30(nm30);
        dest.m31(nm31);
        dest.m32(nm32);
        dest.m33(nm33);
        dest.properties(0);

        return dest;
    }

    @Override
    public Matrix4fc perspective(float fovy, float aspect, float zNear, float zFar, boolean zZeroToOne, Matrix4fc dest) {
        if ((properties & PROPERTY_IDENTITY) != 0)
            return dest.setPerspective(fovy, aspect, zNear, zFar, zZeroToOne);
        return perspectiveGeneric(fovy, aspect, zNear, zFar, zZeroToOne, dest);
    }

    private Matrix4fc perspectiveGeneric(float fovy, float aspect, float zNear, float zFar, boolean zZeroToOne, Matrix4fc dest) {
        float h = (float) Math.tan(fovy * 0.5f);
        // calculate right matrix elements
        float rm00 = 1.0f / (h * aspect);
        float rm11 = 1.0f / h;
        float rm22;
        float rm32;
        boolean farInf = zFar > 0 && Float.isInfinite(zFar);
        boolean nearInf = zNear > 0 && Float.isInfinite(zNear);
        if (farInf) {
            // See: "Infinite Projection Matrix" (http://www.terathon.com/gdc07_lengyel.pdf)
            float e = 1E-6f;
            rm22 = e - 1.0f;
            rm32 = (e - (zZeroToOne ? 1.0f : 2.0f)) * zNear;
        } else if (nearInf) {
            float e = 1E-6f;
            rm22 = (zZeroToOne ? 0.0f : 1.0f) - e;
            rm32 = ((zZeroToOne ? 1.0f : 2.0f) - e) * zFar;
        } else {
            rm22 = (zZeroToOne ? zFar : zFar + zNear) / (zNear - zFar);
            rm32 = (zZeroToOne ? zFar : zFar + zFar) * zNear / (zNear - zFar);
        }
        // perform optimized matrix multiplication
        float nm20 = m20 * rm22 - m30;
        float nm21 = m21 * rm22 - m31;
        float nm22 = m22 * rm22 - m32;
        float nm23 = m23 * rm22 - m33;
        dest.m00(m00 * rm00);
        dest.m01(m01 * rm00);
        dest.m02(m02 * rm00);
        dest.m03(m03 * rm00);
        dest.m10(m10 * rm11);
        dest.m11(m11 * rm11);
        dest.m12(m12 * rm11);
        dest.m13(m13 * rm11);
        dest.m30(m20 * rm32);
        dest.m31(m21 * rm32);
        dest.m32(m22 * rm32);
        dest.m33(m23 * rm32);
        dest.m20(nm20);
        dest.m21(nm21);
        dest.m22(nm22);
        dest.m23(nm23);
        dest.properties((byte) (properties & ~(PROPERTY_AFFINE | PROPERTY_IDENTITY | PROPERTY_TRANSLATION)));

        return dest;
    }

    @Override
    public Matrix4fc perspective(float fovy, float aspect, float zNear, float zFar, Matrix4fc dest) {
        return perspective(fovy, aspect, zNear, zFar, false, dest);
    }

    @Override
    public Matrix4fc perspective(float fovy, float aspect, float zNear, float zFar, boolean zZeroToOne) {
        return perspective(fovy, aspect, zNear, zFar, zZeroToOne, this);
    }

    @Override
    public Matrix4fc perspective(float fovy, float aspect, float zNear, float zFar) {
        return perspective(fovy, aspect, zNear, zFar, this);
    }

    @Override
    public Matrix4fc setPerspective(float fovy, float aspect, float zNear, float zFar, boolean zZeroToOne) {
        MemUtil.INSTANCE.zero(this);
        float h = (float) Math.tan(fovy * 0.5f);
        this._m00(1.0f / (h * aspect));
        this._m11(1.0f / h);
        boolean farInf = zFar > 0 && Float.isInfinite(zFar);
        boolean nearInf = zNear > 0 && Float.isInfinite(zNear);
        if (farInf) {
            // See: "Infinite Projection Matrix" (http://www.terathon.com/gdc07_lengyel.pdf)
            float e = 1E-6f;
            this._m22(e - 1.0f);
            this._m32((e - (zZeroToOne ? 1.0f : 2.0f)) * zNear);
        } else if (nearInf) {
            float e = 1E-6f;
            this._m22((zZeroToOne ? 0.0f : 1.0f) - e);
            this._m32(((zZeroToOne ? 1.0f : 2.0f) - e) * zFar);
        } else {
            this._m22((zZeroToOne ? zFar : zFar + zNear) / (zNear - zFar));
            this._m32((zZeroToOne ? zFar : zFar + zFar) * zNear / (zNear - zFar));
        }
        this._m23(-1.0f);
        properties(PROPERTY_PERSPECTIVE);
        return this;
    }

    @Override
    public Matrix4fc setPerspective(float fovy, float aspect, float zNear, float zFar) {
        return setPerspective(fovy, aspect, zNear, zFar, false);
    }

    @Override
    public Matrix4fc perspectiveLH(float fovy, float aspect, float zNear, float zFar, boolean zZeroToOne, Matrix4fc dest) {
        if ((properties & PROPERTY_IDENTITY) != 0)
            return dest.setPerspectiveLH(fovy, aspect, zNear, zFar, zZeroToOne);
        return perspectiveLHGeneric(fovy, aspect, zNear, zFar, zZeroToOne, dest);
    }

    private Matrix4fc perspectiveLHGeneric(float fovy, float aspect, float zNear, float zFar, boolean zZeroToOne, Matrix4fc dest) {
        float h = (float) Math.tan(fovy * 0.5f);
        // calculate right matrix elements
        float rm00 = 1.0f / (h * aspect);
        float rm11 = 1.0f / h;
        float rm22;
        float rm32;
        boolean farInf = zFar > 0 && Float.isInfinite(zFar);
        boolean nearInf = zNear > 0 && Float.isInfinite(zNear);
        if (farInf) {
            // See: "Infinite Projection Matrix" (http://www.terathon.com/gdc07_lengyel.pdf)
            float e = 1E-6f;
            rm22 = 1.0f - e;
            rm32 = (e - (zZeroToOne ? 1.0f : 2.0f)) * zNear;
        } else if (nearInf) {
            float e = 1E-6f;
            rm22 = (zZeroToOne ? 0.0f : 1.0f) - e;
            rm32 = ((zZeroToOne ? 1.0f : 2.0f) - e) * zFar;
        } else {
            rm22 = (zZeroToOne ? zFar : zFar + zNear) / (zFar - zNear);
            rm32 = (zZeroToOne ? zFar : zFar + zFar) * zNear / (zNear - zFar);
        }
        // perform optimized matrix multiplication
        float nm20 = m20 * rm22 + m30;
        float nm21 = m21 * rm22 + m31;
        float nm22 = m22 * rm22 + m32;
        float nm23 = m23 * rm22 + m33;
        dest.m00(m00 * rm00);
        dest.m01(m01 * rm00);
        dest.m02(m02 * rm00);
        dest.m03(m03 * rm00);
        dest.m10(m10 * rm11);
        dest.m11(m11 * rm11);
        dest.m12(m12 * rm11);
        dest.m13(m13 * rm11);
        dest.m30(m20 * rm32);
        dest.m31(m21 * rm32);
        dest.m32(m22 * rm32);
        dest.m33(m23 * rm32);
        dest.m20(nm20);
        dest.m21(nm21);
        dest.m22(nm22);
        dest.m23(nm23);
        dest.properties((byte) (properties & ~(PROPERTY_AFFINE | PROPERTY_IDENTITY | PROPERTY_TRANSLATION)));
        return dest;
    }

    @Override
    public Matrix4fc perspectiveLH(float fovy, float aspect, float zNear, float zFar, boolean zZeroToOne) {
        return perspectiveLH(fovy, aspect, zNear, zFar, zZeroToOne, this);
    }

    @Override
    public Matrix4fc perspectiveLH(float fovy, float aspect, float zNear, float zFar, Matrix4fc dest) {
        return perspectiveLH(fovy, aspect, zNear, zFar, false, dest);
    }

    @Override
    public Matrix4fc perspectiveLH(float fovy, float aspect, float zNear, float zFar) {
        return perspectiveLH(fovy, aspect, zNear, zFar, this);
    }

    @Override
    public Matrix4fc setPerspectiveLH(float fovy, float aspect, float zNear, float zFar, boolean zZeroToOne) {
        MemUtil.INSTANCE.zero(this);
        float h = (float) Math.tan(fovy * 0.5f);
        this._m00(1.0f / (h * aspect));
        this._m11(1.0f / h);
        boolean farInf = zFar > 0 && Float.isInfinite(zFar);
        boolean nearInf = zNear > 0 && Float.isInfinite(zNear);
        if (farInf) {
            // See: "Infinite Projection Matrix" (http://www.terathon.com/gdc07_lengyel.pdf)
            float e = 1E-6f;
            this._m22(1.0f - e);
            this._m32((e - (zZeroToOne ? 1.0f : 2.0f)) * zNear);
        } else if (nearInf) {
            float e = 1E-6f;
            this._m22((zZeroToOne ? 0.0f : 1.0f) - e);
            this._m32(((zZeroToOne ? 1.0f : 2.0f) - e) * zFar);
        } else {
            this._m22((zZeroToOne ? zFar : zFar + zNear) / (zFar - zNear));
            this._m32((zZeroToOne ? zFar : zFar + zFar) * zNear / (zNear - zFar));
        }
        this._m23(1.0f);
        properties(PROPERTY_PERSPECTIVE);
        return this;
    }

    @Override
    public Matrix4fc setPerspectiveLH(float fovy, float aspect, float zNear, float zFar) {
        return setPerspectiveLH(fovy, aspect, zNear, zFar, false);
    }

    @Override
    public Matrix4fc frustum(float left, float right, float bottom, float top, float zNear, float zFar, boolean zZeroToOne, Matrix4fc dest) {
        // calculate right matrix elements
        float rm00 = (zNear + zNear) / (right - left);
        float rm11 = (zNear + zNear) / (top - bottom);
        float rm20 = (right + left) / (right - left);
        float rm21 = (top + bottom) / (top - bottom);
        float rm22;
        float rm32;
        boolean farInf = zFar > 0 && Float.isInfinite(zFar);
        boolean nearInf = zNear > 0 && Float.isInfinite(zNear);
        if (farInf) {
            // See: "Infinite Projection Matrix" (http://www.terathon.com/gdc07_lengyel.pdf)
            float e = 1E-6f;
            rm22 = e - 1.0f;
            rm32 = (e - (zZeroToOne ? 1.0f : 2.0f)) * zNear;
        } else if (nearInf) {
            float e = 1E-6f;
            rm22 = (zZeroToOne ? 0.0f : 1.0f) - e;
            rm32 = ((zZeroToOne ? 1.0f : 2.0f) - e) * zFar;
        } else {
            rm22 = (zZeroToOne ? zFar : zFar + zNear) / (zNear - zFar);
            rm32 = (zZeroToOne ? zFar : zFar + zFar) * zNear / (zNear - zFar);
        }
        // perform optimized matrix multiplication
        float nm20 = m00 * rm20 + m10 * rm21 + m20 * rm22 - m30;
        float nm21 = m01 * rm20 + m11 * rm21 + m21 * rm22 - m31;
        float nm22 = m02 * rm20 + m12 * rm21 + m22 * rm22 - m32;
        float nm23 = m03 * rm20 + m13 * rm21 + m23 * rm22 - m33;
        dest.m00(m00 * rm00);
        dest.m01(m01 * rm00);
        dest.m02(m02 * rm00);
        dest.m03(m03 * rm00);
        dest.m10(m10 * rm11);
        dest.m11(m11 * rm11);
        dest.m12(m12 * rm11);
        dest.m13(m13 * rm11);
        dest.m30(m20 * rm32);
        dest.m31(m21 * rm32);
        dest.m32(m22 * rm32);
        dest.m33(m23 * rm32);
        dest.m20(nm20);
        dest.m21(nm21);
        dest.m22(nm22);
        dest.m23(nm23);
        dest.m30(m30);
        dest.m31(m31);
        dest.m32(m32);
        dest.m33(m33);
        dest.properties(0);

        return dest;
    }

    @Override
    public Matrix4fc frustum(float left, float right, float bottom, float top, float zNear, float zFar, Matrix4fc dest) {
        return frustum(left, right, bottom, top, zNear, zFar, false, dest);
    }

    @Override
    public Matrix4fc frustum(float left, float right, float bottom, float top, float zNear, float zFar, boolean zZeroToOne) {
        return frustum(left, right, bottom, top, zNear, zFar, zZeroToOne, this);
    }

    @Override
    public Matrix4fc frustum(float left, float right, float bottom, float top, float zNear, float zFar) {
        return frustum(left, right, bottom, top, zNear, zFar, this);
    }

    @Override
    public Matrix4fc setFrustum(float left, float right, float bottom, float top, float zNear, float zFar, boolean zZeroToOne) {
        MemUtil.INSTANCE.zero(this);
        this._m00((zNear + zNear) / (right - left));
        this._m11((zNear + zNear) / (top - bottom));
        this._m20((right + left) / (right - left));
        this._m21((top + bottom) / (top - bottom));
        boolean farInf = zFar > 0 && Float.isInfinite(zFar);
        boolean nearInf = zNear > 0 && Float.isInfinite(zNear);
        if (farInf) {
            // See: "Infinite Projection Matrix" (http://www.terathon.com/gdc07_lengyel.pdf)
            float e = 1E-6f;
            this._m22(e - 1.0f);
            this._m32((e - (zZeroToOne ? 1.0f : 2.0f)) * zNear);
        } else if (nearInf) {
            float e = 1E-6f;
            this._m22((zZeroToOne ? 0.0f : 1.0f) - e);
            this._m32(((zZeroToOne ? 1.0f : 2.0f) - e) * zFar);
        } else {
            this._m22((zZeroToOne ? zFar : zFar + zNear) / (zNear - zFar));
            this._m32((zZeroToOne ? zFar : zFar + zFar) * zNear / (zNear - zFar));
        }
        this._m23(-1.0f);
        properties(0);
        return this;
    }

    @Override
    public Matrix4fc setFrustum(float left, float right, float bottom, float top, float zNear, float zFar) {
        return setFrustum(left, right, bottom, top, zNear, zFar, false);
    }

    @Override
    public Matrix4fc frustumLH(float left, float right, float bottom, float top, float zNear, float zFar, boolean zZeroToOne, Matrix4fc dest) {
        // calculate right matrix elements
        float rm00 = (zNear + zNear) / (right - left);
        float rm11 = (zNear + zNear) / (top - bottom);
        float rm20 = (right + left) / (right - left);
        float rm21 = (top + bottom) / (top - bottom);
        float rm22;
        float rm32;
        boolean farInf = zFar > 0 && Float.isInfinite(zFar);
        boolean nearInf = zNear > 0 && Float.isInfinite(zNear);
        if (farInf) {
            // See: "Infinite Projection Matrix" (http://www.terathon.com/gdc07_lengyel.pdf)
            float e = 1E-6f;
            rm22 = 1.0f - e;
            rm32 = (e - (zZeroToOne ? 1.0f : 2.0f)) * zNear;
        } else if (nearInf) {
            float e = 1E-6f;
            rm22 = (zZeroToOne ? 0.0f : 1.0f) - e;
            rm32 = ((zZeroToOne ? 1.0f : 2.0f) - e) * zFar;
        } else {
            rm22 = (zZeroToOne ? zFar : zFar + zNear) / (zFar - zNear);
            rm32 = (zZeroToOne ? zFar : zFar + zFar) * zNear / (zNear - zFar);
        }
        // perform optimized matrix multiplication
        float nm20 = m00 * rm20 + m10 * rm21 + m20 * rm22 + m30;
        float nm21 = m01 * rm20 + m11 * rm21 + m21 * rm22 + m31;
        float nm22 = m02 * rm20 + m12 * rm21 + m22 * rm22 + m32;
        float nm23 = m03 * rm20 + m13 * rm21 + m23 * rm22 + m33;
        dest.m00(m00 * rm00);
        dest.m01(m01 * rm00);
        dest.m02(m02 * rm00);
        dest.m03(m03 * rm00);
        dest.m10(m10 * rm11);
        dest.m11(m11 * rm11);
        dest.m12(m12 * rm11);
        dest.m13(m13 * rm11);
        dest.m30(m20 * rm32);
        dest.m31(m21 * rm32);
        dest.m32(m22 * rm32);
        dest.m33(m23 * rm32);
        dest.m20(nm20);
        dest.m21(nm21);
        dest.m22(nm22);
        dest.m23(nm23);
        dest.m30(m30);
        dest.m31(m31);
        dest.m32(m32);
        dest.m33(m33);
        properties(0);
        return dest;
    }

    @Override
    public Matrix4fc frustumLH(float left, float right, float bottom, float top, float zNear, float zFar, boolean zZeroToOne) {
        return frustumLH(left, right, bottom, top, zNear, zFar, zZeroToOne, this);
    }

    @Override
    public Matrix4fc frustumLH(float left, float right, float bottom, float top, float zNear, float zFar, Matrix4fc dest) {
        return frustumLH(left, right, bottom, top, zNear, zFar, false, dest);
    }

    @Override
    public Matrix4fc frustumLH(float left, float right, float bottom, float top, float zNear, float zFar) {
        return frustumLH(left, right, bottom, top, zNear, zFar, this);
    }

    @Override
    public Matrix4fc setFrustumLH(float left, float right, float bottom, float top, float zNear, float zFar, boolean zZeroToOne) {
        MemUtil.INSTANCE.zero(this);
        this._m00((zNear + zNear) / (right - left));
        this._m11((zNear + zNear) / (top - bottom));
        this._m20((right + left) / (right - left));
        this._m21((top + bottom) / (top - bottom));
        boolean farInf = zFar > 0 && Float.isInfinite(zFar);
        boolean nearInf = zNear > 0 && Float.isInfinite(zNear);
        if (farInf) {
            // See: "Infinite Projection Matrix" (http://www.terathon.com/gdc07_lengyel.pdf)
            float e = 1E-6f;
            this._m22(1.0f - e);
            this._m32((e - (zZeroToOne ? 1.0f : 2.0f)) * zNear);
        } else if (nearInf) {
            float e = 1E-6f;
            this._m22((zZeroToOne ? 0.0f : 1.0f) - e);
            this._m32(((zZeroToOne ? 1.0f : 2.0f) - e) * zFar);
        } else {
            this._m22((zZeroToOne ? zFar : zFar + zNear) / (zFar - zNear));
            this._m32((zZeroToOne ? zFar : zFar + zFar) * zNear / (zNear - zFar));
        }
        this._m23(1.0f);
        properties(0);
        return this;
    }

    @Override
    public Matrix4fc setFrustumLH(float left, float right, float bottom, float top, float zNear, float zFar) {
        return setFrustumLH(left, right, bottom, top, zNear, zFar, false);
    }

    @Override
    public Matrix4fc setFromIntrinsic(float alphaX, float alphaY, float gamma, float u0, float v0, int imgWidth, int imgHeight, float near, float far) {
        float l00 = 2.0f / imgWidth;
        float l11 = 2.0f / imgHeight;
        float l22 = 2.0f / (near - far);
        this.m00 = l00 * alphaX;
        this.m01 = 0.0f;
        this.m02 = 0.0f;
        this.m03 = 0.0f;
        this.m10 = l00 * gamma;
        this.m11 = l11 * alphaY;
        this.m12 = 0.0f;
        this.m13 = 0.0f;
        this.m20 = l00 * u0 - 1.0f;
        this.m21 = l11 * v0 - 1.0f;
        this.m22 = l22 * -(near + far) + (far + near) / (near - far);
        this.m23 = -1.0f;
        this.m30 = 0.0f;
        this.m31 = 0.0f;
        this.m32 = l22 * -near * far;
        this.m33 = 0.0f;
        this.properties = PROPERTY_PERSPECTIVE;
        return this;
    }

    @Override
    public Matrix4fc rotate(IQuaternionf quat, Matrix4fc dest) {
        if ((properties & PROPERTY_IDENTITY) != 0)
            return dest.rotation(quat);
        else if ((properties & PROPERTY_TRANSLATION) != 0)
            return rotateTranslation(quat, dest);
        else if ((properties & PROPERTY_AFFINE) != 0)
            return rotateAffine(quat, dest);
        return rotateGeneric(quat, dest);
    }

    private Matrix4fc rotateGeneric(IQuaternionf quat, Matrix4fc dest) {
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
        float nm03 = m03 * rm00 + m13 * rm01 + m23 * rm02;
        float nm10 = m00 * rm10 + m10 * rm11 + m20 * rm12;
        float nm11 = m01 * rm10 + m11 * rm11 + m21 * rm12;
        float nm12 = m02 * rm10 + m12 * rm11 + m22 * rm12;
        float nm13 = m03 * rm10 + m13 * rm11 + m23 * rm12;
        dest.m20(m00 * rm20 + m10 * rm21 + m20 * rm22);
        dest.m21(m01 * rm20 + m11 * rm21 + m21 * rm22);
        dest.m22(m02 * rm20 + m12 * rm21 + m22 * rm22);
        dest.m23(m03 * rm20 + m13 * rm21 + m23 * rm22);
        dest.m00(nm00);
        dest.m01(nm01);
        dest.m02(nm02);
        dest.m03(nm03);
        dest.m10(nm10);
        dest.m11(nm11);
        dest.m12(nm12);
        dest.m13(nm13);
        dest.m30(m30);
        dest.m31(m31);
        dest.m32(m32);
        dest.m33(m33);
        dest.properties((byte) (properties & ~(PROPERTY_PERSPECTIVE | PROPERTY_IDENTITY | PROPERTY_TRANSLATION)));
        return dest;
    }

    @Override
    public Matrix4fc rotate(IQuaternionf quat) {
        return rotate(quat, this);
    }

    @Override
    public Matrix4fc rotateAffine(IQuaternionf quat, Matrix4fc dest) {
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
        dest.m23(0.0f);
        dest.m00(nm00);
        dest.m01(nm01);
        dest.m02(nm02);
        dest.m03(0.0f);
        dest.m10(nm10);
        dest.m11(nm11);
        dest.m12(nm12);
        dest.m13(0.0f);
        dest.m30(m30);
        dest.m31(m31);
        dest.m32(m32);
        dest.m33(m33);
        dest.properties((byte) (properties & ~(PROPERTY_PERSPECTIVE | PROPERTY_IDENTITY | PROPERTY_TRANSLATION)));

        return dest;
    }

    @Override
    public Matrix4fc rotateAffine(IQuaternionf quat) {
        return rotateAffine(quat, this);
    }

    @Override
    public Matrix4fc rotateTranslation(IQuaternionf quat, Matrix4fc dest) {
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
        float nm00 = rm00;
        float nm01 = rm01;
        float nm02 = rm02;
        float nm10 = rm10;
        float nm11 = rm11;
        float nm12 = rm12;
        dest.m20(rm20);
        dest.m21(rm21);
        dest.m22(rm22);
        dest.m23(0.0f);
        dest.m00(nm00);
        dest.m01(nm01);
        dest.m02(nm02);
        dest.m03(0.0f);
        dest.m10(nm10);
        dest.m11(nm11);
        dest.m12(nm12);
        dest.m13(0.0f);
        dest.m30(m30);
        dest.m31(m31);
        dest.m32(m32);
        dest.m33(m33);
        dest.properties((byte) (properties & ~(PROPERTY_PERSPECTIVE | PROPERTY_IDENTITY | PROPERTY_TRANSLATION)));

        return dest;
    }

    @Override
    public Matrix4fc rotateAround(IQuaternionf quat, float ox, float oy, float oz) {
        return rotateAround(quat, ox, oy, oz, this);
    }

    @Override
    public Matrix4fc rotateAround(IQuaternionf quat, float ox, float oy, float oz, Matrix4fc dest) {
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
        float tm30 = m00 * ox + m10 * oy + m20 * oz + m30;
        float tm31 = m01 * ox + m11 * oy + m21 * oz + m31;
        float tm32 = m02 * ox + m12 * oy + m22 * oz + m32;
        float nm00 = m00 * rm00 + m10 * rm01 + m20 * rm02;
        float nm01 = m01 * rm00 + m11 * rm01 + m21 * rm02;
        float nm02 = m02 * rm00 + m12 * rm01 + m22 * rm02;
        float nm03 = m03 * rm00 + m13 * rm01 + m23 * rm02;
        float nm10 = m00 * rm10 + m10 * rm11 + m20 * rm12;
        float nm11 = m01 * rm10 + m11 * rm11 + m21 * rm12;
        float nm12 = m02 * rm10 + m12 * rm11 + m22 * rm12;
        float nm13 = m03 * rm10 + m13 * rm11 + m23 * rm12;
        dest.m20(m00 * rm20 + m10 * rm21 + m20 * rm22);
        dest.m21(m01 * rm20 + m11 * rm21 + m21 * rm22);
        dest.m22(m02 * rm20 + m12 * rm21 + m22 * rm22);
        dest.m23(m03 * rm20 + m13 * rm21 + m23 * rm22);
        dest.m00(nm00);
        dest.m01(nm01);
        dest.m02(nm02);
        dest.m03(nm03);
        dest.m10(nm10);
        dest.m11(nm11);
        dest.m12(nm12);
        dest.m13(nm13);
        dest.m30(-nm00 * ox - nm10 * oy - m20 * oz + tm30);
        dest.m31(-nm01 * ox - nm11 * oy - m21 * oz + tm31);
        dest.m32(-nm02 * ox - nm12 * oy - m22 * oz + tm32);
        dest.m33(m33);
        dest.properties((byte) (properties & ~(PROPERTY_PERSPECTIVE | PROPERTY_IDENTITY | PROPERTY_TRANSLATION)));
        return dest;
    }

    @Override
    public Matrix4fc rotateLocal(IQuaternionf quat, Matrix4fc dest) {
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
        float nm03 = m03;
        float nm10 = lm00 * m10 + lm10 * m11 + lm20 * m12;
        float nm11 = lm01 * m10 + lm11 * m11 + lm21 * m12;
        float nm12 = lm02 * m10 + lm12 * m11 + lm22 * m12;
        float nm13 = m13;
        float nm20 = lm00 * m20 + lm10 * m21 + lm20 * m22;
        float nm21 = lm01 * m20 + lm11 * m21 + lm21 * m22;
        float nm22 = lm02 * m20 + lm12 * m21 + lm22 * m22;
        float nm23 = m23;
        float nm30 = lm00 * m30 + lm10 * m31 + lm20 * m32;
        float nm31 = lm01 * m30 + lm11 * m31 + lm21 * m32;
        float nm32 = lm02 * m30 + lm12 * m31 + lm22 * m32;
        float nm33 = m33;
        dest.m00(nm00);
        dest.m01(nm01);
        dest.m02(nm02);
        dest.m03(nm03);
        dest.m10(nm10);
        dest.m11(nm11);
        dest.m12(nm12);
        dest.m13(nm13);
        dest.m20(nm20);
        dest.m21(nm21);
        dest.m22(nm22);
        dest.m23(nm23);
        dest.m30(nm30);
        dest.m31(nm31);
        dest.m32(nm32);
        dest.m33(nm33);
        dest.properties((byte) (properties & ~(PROPERTY_PERSPECTIVE | PROPERTY_IDENTITY | PROPERTY_TRANSLATION)));
        return dest;
    }

    @Override
    public Matrix4fc rotateLocal(IQuaternionf quat) {
        return rotateLocal(quat, this);
    }

    @Override
    public Matrix4fc rotateAroundLocal(IQuaternionf quat, float ox, float oy, float oz, Matrix4fc dest) {
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
        float tm00 = m00 - ox * m03;
        float tm01 = m01 - oy * m03;
        float tm02 = m02 - oz * m03;
        float tm10 = m10 - ox * m13;
        float tm11 = m11 - oy * m13;
        float tm12 = m12 - oz * m13;
        float tm20 = m20 - ox * m23;
        float tm21 = m21 - oy * m23;
        float tm22 = m22 - oz * m23;
        float tm30 = m30 - ox * m33;
        float tm31 = m31 - oy * m33;
        float tm32 = m32 - oz * m33;
        dest.m00(lm00 * tm00 + lm10 * tm01 + lm20 * tm02 + ox * m03);
        dest.m01(lm01 * tm00 + lm11 * tm01 + lm21 * tm02 + oy * m03);
        dest.m02(lm02 * tm00 + lm12 * tm01 + lm22 * tm02 + oz * m03);
        dest.m03(m03);
        dest.m10(lm00 * tm10 + lm10 * tm11 + lm20 * tm12 + ox * m13);
        dest.m11(lm01 * tm10 + lm11 * tm11 + lm21 * tm12 + oy * m13);
        dest.m12(lm02 * tm10 + lm12 * tm11 + lm22 * tm12 + oz * m13);
        dest.m13(m13);
        dest.m20(lm00 * tm20 + lm10 * tm21 + lm20 * tm22 + ox * m23);
        dest.m21(lm01 * tm20 + lm11 * tm21 + lm21 * tm22 + oy * m23);
        dest.m22(lm02 * tm20 + lm12 * tm21 + lm22 * tm22 + oz * m23);
        dest.m23(m23);
        dest.m30(lm00 * tm30 + lm10 * tm31 + lm20 * tm32 + ox * m33);
        dest.m31(lm01 * tm30 + lm11 * tm31 + lm21 * tm32 + oy * m33);
        dest.m32(lm02 * tm30 + lm12 * tm31 + lm22 * tm32 + oz * m33);
        dest.m33(m33);
        dest.properties((byte) (properties & ~(PROPERTY_PERSPECTIVE | PROPERTY_IDENTITY | PROPERTY_TRANSLATION)));
        return dest;
    }

    @Override
    public Matrix4fc rotateAroundLocal(IQuaternionf quat, float ox, float oy, float oz) {
        return rotateAroundLocal(quat, ox, oy, oz, this);
    }

    @Override
    public Matrix4fc rotate(AxisAngle4fc axisAngle) {
        return rotate(axisAngle.angle(), axisAngle.x(), axisAngle.y(), axisAngle.z());
    }

    @Override
    public Matrix4fc rotate(AxisAngle4fc axisAngle, Matrix4fc dest) {
        return rotate(axisAngle.angle(), axisAngle.x(), axisAngle.y(), axisAngle.z(), dest);
    }

    @Override
    public Matrix4fc rotate(float angle, IVector3f axis) {
        return rotate(angle, axis.x(), axis.y(), axis.z());
    }

    @Override
    public Matrix4fc rotate(float angle, IVector3f axis, Matrix4fc dest) {
        return rotate(angle, axis.x(), axis.y(), axis.z(), dest);
    }

    @Override
    public Vector4fc unproject(float winX, float winY, float winZ, int[] viewport, Vector4fc dest) {
        float a = m00 * m11 - m01 * m10;
        float b = m00 * m12 - m02 * m10;
        float c = m00 * m13 - m03 * m10;
        float d = m01 * m12 - m02 * m11;
        float e = m01 * m13 - m03 * m11;
        float f = m02 * m13 - m03 * m12;
        float g = m20 * m31 - m21 * m30;
        float h = m20 * m32 - m22 * m30;
        float i = m20 * m33 - m23 * m30;
        float j = m21 * m32 - m22 * m31;
        float k = m21 * m33 - m23 * m31;
        float l = m22 * m33 - m23 * m32;
        float det = a * l - b * k + c * j + d * i - e * h + f * g;
        det = 1.0f / det;
        float im00 = ( m11 * l - m12 * k + m13 * j) * det;
        float im01 = (-m01 * l + m02 * k - m03 * j) * det;
        float im02 = ( m31 * f - m32 * e + m33 * d) * det;
        float im03 = (-m21 * f + m22 * e - m23 * d) * det;
        float im10 = (-m10 * l + m12 * i - m13 * h) * det;
        float im11 = ( m00 * l - m02 * i + m03 * h) * det;
        float im12 = (-m30 * f + m32 * c - m33 * b) * det;
        float im13 = ( m20 * f - m22 * c + m23 * b) * det;
        float im20 = ( m10 * k - m11 * i + m13 * g) * det;
        float im21 = (-m00 * k + m01 * i - m03 * g) * det;
        float im22 = ( m30 * e - m31 * c + m33 * a) * det;
        float im23 = (-m20 * e + m21 * c - m23 * a) * det;
        float im30 = (-m10 * j + m11 * h - m12 * g) * det;
        float im31 = ( m00 * j - m01 * h + m02 * g) * det;
        float im32 = (-m30 * d + m31 * b - m32 * a) * det;
        float im33 = ( m20 * d - m21 * b + m22 * a) * det;
        float ndcX = (winX-viewport[0])/viewport[2]*2.0f-1.0f;
        float ndcY = (winY-viewport[1])/viewport[3]*2.0f-1.0f;
        float ndcZ = winZ+winZ-1.0f;
        dest.set(im00 * ndcX + im10 * ndcY + im20 * ndcZ + im30,
                im01 * ndcX + im11 * ndcY + im21 * ndcZ + im31,
                im02 * ndcX + im12 * ndcY + im22 * ndcZ + im32,
                im03 * ndcX + im13 * ndcY + im23 * ndcZ + im33);
        dest.div(dest.w());
        return dest;
    }

    @Override
    public Vector3fc unproject(float winX, float winY, float winZ, int[] viewport, Vector3fc dest) {
        float a = m00 * m11 - m01 * m10;
        float b = m00 * m12 - m02 * m10;
        float c = m00 * m13 - m03 * m10;
        float d = m01 * m12 - m02 * m11;
        float e = m01 * m13 - m03 * m11;
        float f = m02 * m13 - m03 * m12;
        float g = m20 * m31 - m21 * m30;
        float h = m20 * m32 - m22 * m30;
        float i = m20 * m33 - m23 * m30;
        float j = m21 * m32 - m22 * m31;
        float k = m21 * m33 - m23 * m31;
        float l = m22 * m33 - m23 * m32;
        float det = a * l - b * k + c * j + d * i - e * h + f * g;
        det = 1.0f / det;
        float im00 = ( m11 * l - m12 * k + m13 * j) * det;
        float im01 = (-m01 * l + m02 * k - m03 * j) * det;
        float im02 = ( m31 * f - m32 * e + m33 * d) * det;
        float im03 = (-m21 * f + m22 * e - m23 * d) * det;
        float im10 = (-m10 * l + m12 * i - m13 * h) * det;
        float im11 = ( m00 * l - m02 * i + m03 * h) * det;
        float im12 = (-m30 * f + m32 * c - m33 * b) * det;
        float im13 = ( m20 * f - m22 * c + m23 * b) * det;
        float im20 = ( m10 * k - m11 * i + m13 * g) * det;
        float im21 = (-m00 * k + m01 * i - m03 * g) * det;
        float im22 = ( m30 * e - m31 * c + m33 * a) * det;
        float im23 = (-m20 * e + m21 * c - m23 * a) * det;
        float im30 = (-m10 * j + m11 * h - m12 * g) * det;
        float im31 = ( m00 * j - m01 * h + m02 * g) * det;
        float im32 = (-m30 * d + m31 * b - m32 * a) * det;
        float im33 = ( m20 * d - m21 * b + m22 * a) * det;
        float ndcX = (winX-viewport[0])/viewport[2]*2.0f-1.0f;
        float ndcY = (winY-viewport[1])/viewport[3]*2.0f-1.0f;
        float ndcZ = winZ+winZ-1.0f;
        dest.set(im00 * ndcX + im10 * ndcY + im20 * ndcZ + im30,
                im01 * ndcX + im11 * ndcY + im21 * ndcZ + im31,
                im02 * ndcX + im12 * ndcY + im22 * ndcZ + im32);
        float w = im03 * ndcX + im13 * ndcY + im23 * ndcZ + im33;
        dest.div(w);
        return dest;
    }

    @Override
    public Vector4fc unproject(IVector3f winCoords, int[] viewport, Vector4fc dest) {
        return unproject(winCoords.x(), winCoords.y(), winCoords.z(), viewport, dest);
    }

    @Override
    public Vector3fc unproject(IVector3f winCoords, int[] viewport, Vector3fc dest) {
        return unproject(winCoords.x(), winCoords.y(), winCoords.z(), viewport, dest);
    }

    @Override
    public Matrix4fc unprojectRay(float winX, float winY, int[] viewport, Vector3fc originDest, Vector3fc dirDest) {
        float a = m00 * m11 - m01 * m10;
        float b = m00 * m12 - m02 * m10;
        float c = m00 * m13 - m03 * m10;
        float d = m01 * m12 - m02 * m11;
        float e = m01 * m13 - m03 * m11;
        float f = m02 * m13 - m03 * m12;
        float g = m20 * m31 - m21 * m30;
        float h = m20 * m32 - m22 * m30;
        float i = m20 * m33 - m23 * m30;
        float j = m21 * m32 - m22 * m31;
        float k = m21 * m33 - m23 * m31;
        float l = m22 * m33 - m23 * m32;
        float det = a * l - b * k + c * j + d * i - e * h + f * g;
        det = 1.0f / det;
        float im00 = ( m11 * l - m12 * k + m13 * j) * det;
        float im01 = (-m01 * l + m02 * k - m03 * j) * det;
        float im02 = ( m31 * f - m32 * e + m33 * d) * det;
        float im03 = (-m21 * f + m22 * e - m23 * d) * det;
        float im10 = (-m10 * l + m12 * i - m13 * h) * det;
        float im11 = ( m00 * l - m02 * i + m03 * h) * det;
        float im12 = (-m30 * f + m32 * c - m33 * b) * det;
        float im13 = ( m20 * f - m22 * c + m23 * b) * det;
        float im20 = ( m10 * k - m11 * i + m13 * g) * det;
        float im21 = (-m00 * k + m01 * i - m03 * g) * det;
        float im22 = ( m30 * e - m31 * c + m33 * a) * det;
        float im23 = (-m20 * e + m21 * c - m23 * a) * det;
        float im30 = (-m10 * j + m11 * h - m12 * g) * det;
        float im31 = ( m00 * j - m01 * h + m02 * g) * det;
        float im32 = (-m30 * d + m31 * b - m32 * a) * det;
        float im33 = ( m20 * d - m21 * b + m22 * a) * det;
        float ndcX = (winX-viewport[0])/viewport[2]*2.0f-1.0f;
        float ndcY = (winY-viewport[1])/viewport[3]*2.0f-1.0f;
        float nearX = im00 * ndcX + im10 * ndcY - im20 + im30;
        float nearY = im01 * ndcX + im11 * ndcY - im21 + im31;
        float nearZ = im02 * ndcX + im12 * ndcY - im22 + im32;
        float invNearW = 1.0f / (im03 * ndcX + im13 * ndcY - im23 + im33);
        nearX *= invNearW; nearY *= invNearW; nearZ *= invNearW;
        float farX = im00 * ndcX + im10 * ndcY + im20 + im30;
        float farY = im01 * ndcX + im11 * ndcY + im21 + im31;
        float farZ = im02 * ndcX + im12 * ndcY + im22 + im32;
        float invFarW = 1.0f / (im03 * ndcX + im13 * ndcY + im23 + im33);
        farX *= invFarW; farY *= invFarW; farZ *= invFarW;
        originDest.set(nearX, nearY, nearZ);
        dirDest.set(farX - nearX, farY - nearY, farZ - nearZ);
        return this;
    }

    @Override
    public Matrix4fc unprojectRay(IVector2f winCoords, int[] viewport, Vector3fc originDest, Vector3fc dirDest) {
        return unprojectRay(winCoords.x(), winCoords.y(), viewport, originDest, dirDest);
    }

    @Override
    public Vector4fc unprojectInv(IVector3f winCoords, int[] viewport, Vector4fc dest) {
        return unprojectInv(winCoords.x(), winCoords.y(), winCoords.z(), viewport, dest);
    }

    @Override
    public Vector4fc unprojectInv(float winX, float winY, float winZ, int[] viewport, Vector4fc dest) {
        float ndcX = (winX-viewport[0])/viewport[2]*2.0f-1.0f;
        float ndcY = (winY-viewport[1])/viewport[3]*2.0f-1.0f;
        float ndcZ = winZ+winZ-1.0f;
        dest.set(m00 * ndcX + m10 * ndcY + m20 * ndcZ + m30,
                m01 * ndcX + m11 * ndcY + m21 * ndcZ + m31,
                m02 * ndcX + m12 * ndcY + m22 * ndcZ + m32,
                m03 * ndcX + m13 * ndcY + m23 * ndcZ + m33);
        dest.div(dest.w());
        return dest;
    }

    @Override
    public Matrix4fc unprojectInvRay(IVector2f winCoords, int[] viewport, Vector3fc originDest, Vector3fc dirDest) {
        return unprojectInvRay(winCoords.x(), winCoords.y(), viewport, originDest, dirDest);
    }

    @Override
    public Matrix4fc unprojectInvRay(float winX, float winY, int[] viewport, Vector3fc originDest, Vector3fc dirDest) {
        float ndcX = (winX-viewport[0])/viewport[2]*2.0f-1.0f;
        float ndcY = (winY-viewport[1])/viewport[3]*2.0f-1.0f;
        float nearX = m00 * ndcX + m10 * ndcY - m20 + m30;
        float nearY = m01 * ndcX + m11 * ndcY - m21 + m31;
        float nearZ = m02 * ndcX + m12 * ndcY - m22 + m32;
        float invNearW = 1.0f / (m03 * ndcX + m13 * ndcY - m23 + m33);
        nearX *= invNearW; nearY *= invNearW; nearZ *= invNearW;
        float farX = m00 * ndcX + m10 * ndcY + m20 + m30;
        float farY = m01 * ndcX + m11 * ndcY + m21 + m31;
        float farZ = m02 * ndcX + m12 * ndcY + m22 + m32;
        float invFarW = 1.0f / (m03 * ndcX + m13 * ndcY + m23 + m33);
        farX *= invFarW; farY *= invFarW; farZ *= invFarW;
        originDest.set(nearX, nearY, nearZ);
        dirDest.set(farX - nearX, farY - nearY, farZ - nearZ);
        return this;
    }

    @Override
    public Vector3fc unprojectInv(IVector3f winCoords, int[] viewport, Vector3fc dest) {
        return unprojectInv(winCoords.x(), winCoords.y(), winCoords.z(), viewport, dest);
    }

    @Override
    public Vector3fc unprojectInv(float winX, float winY, float winZ, int[] viewport, Vector3fc dest) {
        float ndcX = (winX-viewport[0])/viewport[2]*2.0f-1.0f;
        float ndcY = (winY-viewport[1])/viewport[3]*2.0f-1.0f;
        float ndcZ = winZ+winZ-1.0f;
        dest.set(m00 * ndcX + m10 * ndcY + m20 * ndcZ + m30, m01 * ndcX + m11 * ndcY + m21 * ndcZ + m31, m02 * ndcX + m12 * ndcY + m22 * ndcZ + m32);
        float w = m03 * ndcX + m13 * ndcY + m23 * ndcZ + m33;
        dest.div(w);
        return dest;
    }

    @Override
    public Vector4fc project(float x, float y, float z, int[] viewport, Vector4fc winCoordsDest) {
        winCoordsDest.set(m00 * x + m10 * y + m20 * z + m30,
                m01 * x + m11 * y + m21 * z + m31,
                m02 * x + m12 * y + m22 * z + m32,
                m03 * x + m13 * y + m23 * z + m33);
        winCoordsDest.div(winCoordsDest.w());
        winCoordsDest.set((winCoordsDest.x()*0.5f+0.5f) * viewport[2] + viewport[0],
                (winCoordsDest.y()*0.5f+0.5f) * viewport[3] + viewport[1],
                (1.0f+winCoordsDest.z())*0.5f,
                winCoordsDest.w());
        return winCoordsDest;
    }

    @Override
    public Vector3fc project(float x, float y, float z, int[] viewport, Vector3fc winCoordsDest) {
        winCoordsDest.set(m00 * x + m10 * y + m20 * z + m30,
                m01 * x + m11 * y + m21 * z + m31,
                m02 * x + m12 * y + m22 * z + m32);
        float w = m03 * x + m13 * y + m23 * z + m33;
        winCoordsDest.div(w);
        winCoordsDest.set((winCoordsDest.x()*0.5f+0.5f) * viewport[2] + viewport[0],
                (winCoordsDest.y()*0.5f+0.5f) * viewport[3] + viewport[1],
                (1.0f+winCoordsDest.z())*0.5f);
        return winCoordsDest;
    }

    @Override
    public Vector4fc project(IVector3f position, int[] viewport, Vector4fc winCoordsDest) {
        return project(position.x(), position.y(), position.z(), viewport, winCoordsDest);
    }

    @Override
    public Vector3fc project(IVector3f position, int[] viewport, Vector3fc winCoordsDest) {
        return project(position.x(), position.y(), position.z(), viewport, winCoordsDest);
    }

    @Override
    public Matrix4fc reflect(float a, float b, float c, float d, Matrix4fc dest) {
        if ((properties & PROPERTY_IDENTITY) != 0)
            return dest.reflection(a, b, c, d);

        float da = a + a, db = b + b, dc = c + c, dd = d + d;
        float rm00 = 1.0f - da * a;
        float rm01 = -da * b;
        float rm02 = -da * c;
        float rm10 = -db * a;
        float rm11 = 1.0f - db * b;
        float rm12 = -db * c;
        float rm20 = -dc * a;
        float rm21 = -dc * b;
        float rm22 = 1.0f - dc * c;
        float rm30 = -dd * a;
        float rm31 = -dd * b;
        float rm32 = -dd * c;

        // matrix multiplication
        dest.m30(m00 * rm30 + m10 * rm31 + m20 * rm32 + m30);
        dest.m31(m01 * rm30 + m11 * rm31 + m21 * rm32 + m31);
        dest.m32(m02 * rm30 + m12 * rm31 + m22 * rm32 + m32);
        dest.m33(m03 * rm30 + m13 * rm31 + m23 * rm32 + m33);
        float nm00 = m00 * rm00 + m10 * rm01 + m20 * rm02;
        float nm01 = m01 * rm00 + m11 * rm01 + m21 * rm02;
        float nm02 = m02 * rm00 + m12 * rm01 + m22 * rm02;
        float nm03 = m03 * rm00 + m13 * rm01 + m23 * rm02;
        float nm10 = m00 * rm10 + m10 * rm11 + m20 * rm12;
        float nm11 = m01 * rm10 + m11 * rm11 + m21 * rm12;
        float nm12 = m02 * rm10 + m12 * rm11 + m22 * rm12;
        float nm13 = m03 * rm10 + m13 * rm11 + m23 * rm12;
        dest.m20(m00 * rm20 + m10 * rm21 + m20 * rm22);
        dest.m21(m01 * rm20 + m11 * rm21 + m21 * rm22);
        dest.m22(m02 * rm20 + m12 * rm21 + m22 * rm22);
        dest.m23(m03 * rm20 + m13 * rm21 + m23 * rm22);
        dest.m00(nm00);
        dest.m01(nm01);
        dest.m02(nm02);
        dest.m03(nm03);
        dest.m10(nm10);
        dest.m11(nm11);
        dest.m12(nm12);
        dest.m13(nm13);
        dest.properties((byte) (properties & ~(PROPERTY_PERSPECTIVE | PROPERTY_IDENTITY | PROPERTY_TRANSLATION)));

        return dest;
    }

    @Override
    public Matrix4fc reflect(float a, float b, float c, float d) {
        return reflect(a, b, c, d, this);
    }

    @Override
    public Matrix4fc reflect(float nx, float ny, float nz, float px, float py, float pz) {
        return reflect(nx, ny, nz, px, py, pz, this);
    }

    @Override
    public Matrix4fc reflect(float nx, float ny, float nz, float px, float py, float pz, Matrix4fc dest) {
        float invLength = 1.0f / (float) Math.sqrt(nx * nx + ny * ny + nz * nz);
        float nnx = nx * invLength;
        float nny = ny * invLength;
        float nnz = nz * invLength;
        /* See: http://mathworld.wolfram.com/Plane.html */
        return reflect(nnx, nny, nnz, -nnx * px - nny * py - nnz * pz, dest);
    }

    @Override
    public Matrix4fc reflect(IVector3f normal, IVector3f point) {
        return reflect(normal.x(), normal.y(), normal.z(), point.x(), point.y(), point.z());
    }

    @Override
    public Matrix4fc reflect(IQuaternionf orientation, IVector3f point) {
        return reflect(orientation, point, this);
    }

    @Override
    public Matrix4fc reflect(IQuaternionf orientation, IVector3f point, Matrix4fc dest) {
        double num1 = orientation.x() + orientation.x();
        double num2 = orientation.y() + orientation.y();
        double num3 = orientation.z() + orientation.z();
        float normalX = (float) (orientation.x() * num3 + orientation.w() * num2);
        float normalY = (float) (orientation.y() * num3 - orientation.w() * num1);
        float normalZ = (float) (1.0 - (orientation.x() * num1 + orientation.y() * num2));
        return reflect(normalX, normalY, normalZ, point.x(), point.y(), point.z(), dest);
    }

    @Override
    public Matrix4fc reflect(IVector3f normal, IVector3f point, Matrix4fc dest) {
        return reflect(normal.x(), normal.y(), normal.z(), point.x(), point.y(), point.z(), dest);
    }

    @Override
    public Matrix4fc reflection(float a, float b, float c, float d) {
        float da = a + a, db = b + b, dc = c + c, dd = d + d;
        this._m00(1.0f - da * a);
        this._m01(-da * b);
        this._m02(-da * c);
        this._m03(0.0f);
        this._m10(-db * a);
        this._m11(1.0f - db * b);
        this._m12(-db * c);
        this._m13(0.0f);
        this._m20(-dc * a);
        this._m21(-dc * b);
        this._m22(1.0f - dc * c);
        this._m23(0.0f);
        this._m30(-dd * a);
        this._m31(-dd * b);
        this._m32(-dd * c);
        this._m33(1.0f);
        properties(PROPERTY_AFFINE);
        return this;
    }

    @Override
    public Matrix4fc reflection(float nx, float ny, float nz, float px, float py, float pz) {
        float invLength = 1.0f / (float) Math.sqrt(nx * nx + ny * ny + nz * nz);
        float nnx = nx * invLength;
        float nny = ny * invLength;
        float nnz = nz * invLength;
        /* See: http://mathworld.wolfram.com/Plane.html */
        return reflection(nnx, nny, nnz, -nnx * px - nny * py - nnz * pz);
    }

    @Override
    public Matrix4fc reflection(IVector3f normal, IVector3f point) {
        return reflection(normal.x(), normal.y(), normal.z(), point.x(), point.y(), point.z());
    }

    @Override
    public Matrix4fc reflection(IQuaternionf orientation, IVector3f point) {
        double num1 = orientation.x() + orientation.x();
        double num2 = orientation.y() + orientation.y();
        double num3 = orientation.z() + orientation.z();
        float normalX = (float) (orientation.x() * num3 + orientation.w() * num2);
        float normalY = (float) (orientation.y() * num3 - orientation.w() * num1);
        float normalZ = (float) (1.0 - (orientation.x() * num1 + orientation.y() * num2));
        return reflection(normalX, normalY, normalZ, point.x(), point.y(), point.z());
    }

    @Override
    public Vector4fc getRow(int row, Vector4fc dest) throws IndexOutOfBoundsException {
        switch (row) {
        case 0:
            dest.set(m00, m10, m20, m30);
            break;
        case 1:
            dest.set(m01, m11, m21, m31);
            break;
        case 2:
            dest.set(m02, m12, m22, m32);
            break;
        case 3:
            dest.set(m03, m13, m23, m33);
            break;
        default:
            throw new IndexOutOfBoundsException();
        }
        return dest;
    }

    @Override
    public Matrix4fc setRow(int row, IVector4f src) throws IndexOutOfBoundsException {
        switch (row) {
        case 0:
            this._m00(src.x());
            this._m10(src.y());
            this._m20(src.z());
            this._m30(src.w());
            break;
        case 1:
            this._m01(src.x());
            this._m11(src.y());
            this._m21(src.z());
            this._m31(src.w());
            break;
        case 2:
            this._m02(src.x());
            this._m12(src.y());
            this._m22(src.z());
            this._m32(src.w());
            break;
        case 3:
            this._m03(src.x());
            this._m13(src.y());
            this._m23(src.z());
            this._m33(src.w());
            break;
        default:
            throw new IndexOutOfBoundsException();
        }
        return this;
    }

    @Override
    public Vector4fc getColumn(int column, Vector4fc dest) throws IndexOutOfBoundsException {
        switch (column) {
        case 0:
            dest.set(this.m00, this.m01, this.m02, this.m03);
            break;
        case 1:
            dest.set(this.m10, this.m11, this.m12, this.m13);
            break;
        case 2:
            dest.set(this.m20, this.m21, this.m22, this.m23);
            break;
        case 3:
            dest.set(this.m30, this.m31, this.m32, this.m33);
            break;
        default:
            throw new IndexOutOfBoundsException();
        }
        return dest;
    }

    @Override
    public Matrix4fc setColumn(int column, IVector4f src) throws IndexOutOfBoundsException {
        switch (column) {
        case 0:
            if (src instanceof Vector4f) {
                MemUtil.INSTANCE.getColumn0(this, (Vector4f) src);
            } else {
                this._m00(src.x());
                this._m01(src.y());
                this._m02(src.z());
                this._m03(src.w());
            }
            break;
        case 1:
            if (src instanceof Vector4f) {
                MemUtil.INSTANCE.getColumn1(this, (Vector4f) src);
            } else {
                this._m10(src.x());
                this._m11(src.y());
                this._m12(src.z());
                this._m13(src.w());
            }
            break;
        case 2:
            if (src instanceof Vector4f) {
                MemUtil.INSTANCE.getColumn2(this, (Vector4f) src);
            } else {
                this._m20(src.x());
                this._m21(src.y());
                this._m22(src.z());
                this._m23(src.w());
            }
            break;
        case 3:
            if (src instanceof Vector4f) {
                MemUtil.INSTANCE.getColumn3(this, (Vector4f) src);
            } else {
                this._m30(src.x());
                this._m31(src.y());
                this._m32(src.z());
                this._m33(src.w());
            }
            break;
        default:
            throw new IndexOutOfBoundsException();
        }
        return this;
    }

    @Override
    public Matrix4fc normal() {
        return normal(this);
    }

    @Override
    public Matrix4fc normal(Matrix4fc dest) {
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
        dest.m03(0.0f);
        dest.m10(nm10);
        dest.m11(nm11);
        dest.m12(nm12);
        dest.m13(0.0f);
        dest.m20(nm20);
        dest.m21(nm21);
        dest.m22(nm22);
        dest.m23(0.0f);
        dest.m30(0.0f);
        dest.m31(0.0f);
        dest.m32(0.0f);
        dest.m33(1.0f);
        dest.properties(PROPERTY_AFFINE);
        return dest;
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
        dest.m00((m11 * m22 - m21 * m12) * s);
        dest.m01((m20 * m12 - m10 * m22) * s);
        dest.m02((m10 * m21 - m20 * m11) * s);
        dest.m10((m21 * m02 - m01 * m22) * s);
        dest.m11((m00 * m22 - m20 * m02) * s);
        dest.m12((m20 * m01 - m00 * m21) * s);
        dest.m20((m01m12 - m02m11) * s);
        dest.m21((m02m10 - m00m12) * s);
        dest.m22((m00m11 - m01m10) * s);
        return dest;
    }

    @Override
    public Matrix4fc normalize3x3() {
        return normalize3x3(this);
    }

    @Override
    public Matrix4fc normalize3x3(Matrix4fc dest) {
        float invXlen = (float) (1.0 / Math.sqrt(m00 * m00 + m01 * m01 + m02 * m02));
        float invYlen = (float) (1.0 / Math.sqrt(m10 * m10 + m11 * m11 + m12 * m12));
        float invZlen = (float) (1.0 / Math.sqrt(m20 * m20 + m21 * m21 + m22 * m22));
        dest.m00(m00 * invXlen); dest.m01(m01 * invXlen); dest.m02(m02 * invXlen);
        dest.m10(m10 * invYlen); dest.m11(m11 * invYlen); dest.m12(m12 * invYlen);
        dest.m20(m20 * invZlen); dest.m21(m21 * invZlen); dest.m22(m22 * invZlen);
        dest.properties(properties);
        return dest;
    }

    @Override
    public Matrix3fc normalize3x3(Matrix3fc dest) {
        float invXlen = (float) (1.0 / Math.sqrt(m00 * m00 + m01 * m01 + m02 * m02));
        float invYlen = (float) (1.0 / Math.sqrt(m10 * m10 + m11 * m11 + m12 * m12));
        float invZlen = (float) (1.0 / Math.sqrt(m20 * m20 + m21 * m21 + m22 * m22));
        dest.m00(m00 * invXlen); dest.m01(m01 * invXlen); dest.m02(m02 * invXlen);
        dest.m10(m10 * invYlen); dest.m11(m11 * invYlen); dest.m12(m12 * invYlen);
        dest.m20(m20 * invZlen); dest.m21(m21 * invZlen); dest.m22(m22 * invZlen);
        return dest;
    }

    @Override
    public Vector4fc frustumPlane(int plane, Vector4fc planeEquation) {
        switch (plane) {
        case PLANE_NX:
            planeEquation.set(m03 + m00, m13 + m10, m23 + m20, m33 + m30).normalize3();
            break;
        case PLANE_PX:
            planeEquation.set(m03 - m00, m13 - m10, m23 - m20, m33 - m30).normalize3();
            break;
        case PLANE_NY:
            planeEquation.set(m03 + m01, m13 + m11, m23 + m21, m33 + m31).normalize3();
            break;
        case PLANE_PY:
            planeEquation.set(m03 - m01, m13 - m11, m23 - m21, m33 - m31).normalize3();
            break;
        case PLANE_NZ:
            planeEquation.set(m03 + m02, m13 + m12, m23 + m22, m33 + m32).normalize3();
            break;
        case PLANE_PZ:
            planeEquation.set(m03 - m02, m13 - m12, m23 - m22, m33 - m32).normalize3();
            break;
        default:
            throw new IllegalArgumentException("plane"); //$NON-NLS-1$
        }
        return planeEquation;
    }

    @Override
    public Planefc frustumPlane(int which, Planefc plane) {
        switch (which) {
        case PLANE_NX:
            plane.set(m03 + m00, m13 + m10, m23 + m20, m33 + m30).normalize();
            break;
        case PLANE_PX:
            plane.set(m03 - m00, m13 - m10, m23 - m20, m33 - m30).normalize();
            break;
        case PLANE_NY:
            plane.set(m03 + m01, m13 + m11, m23 + m21, m33 + m31).normalize();
            break;
        case PLANE_PY:
            plane.set(m03 - m01, m13 - m11, m23 - m21, m33 - m31).normalize();
            break;
        case PLANE_NZ:
            plane.set(m03 + m02, m13 + m12, m23 + m22, m33 + m32).normalize();
            break;
        case PLANE_PZ:
            plane.set(m03 - m02, m13 - m12, m23 - m22, m33 - m32).normalize();
            break;
        default:
            throw new IllegalArgumentException("which"); //$NON-NLS-1$
        }
        return plane;
    }

    @Override
    public Vector3fc frustumCorner(int corner, Vector3fc point) {
        float d1, d2, d3;
        float n1x, n1y, n1z, n2x, n2y, n2z, n3x, n3y, n3z;
        switch (corner) {
        case CORNER_NXNYNZ: // left, bottom, near
            n1x = m03 + m00; n1y = m13 + m10; n1z = m23 + m20; d1 = m33 + m30; // left
            n2x = m03 + m01; n2y = m13 + m11; n2z = m23 + m21; d2 = m33 + m31; // bottom
            n3x = m03 + m02; n3y = m13 + m12; n3z = m23 + m22; d3 = m33 + m32; // near
            break;
        case CORNER_PXNYNZ: // right, bottom, near
            n1x = m03 - m00; n1y = m13 - m10; n1z = m23 - m20; d1 = m33 - m30; // right
            n2x = m03 + m01; n2y = m13 + m11; n2z = m23 + m21; d2 = m33 + m31; // bottom
            n3x = m03 + m02; n3y = m13 + m12; n3z = m23 + m22; d3 = m33 + m32; // near
            break;
        case CORNER_PXPYNZ: // right, top, near
            n1x = m03 - m00; n1y = m13 - m10; n1z = m23 - m20; d1 = m33 - m30; // right
            n2x = m03 - m01; n2y = m13 - m11; n2z = m23 - m21; d2 = m33 - m31; // top
            n3x = m03 + m02; n3y = m13 + m12; n3z = m23 + m22; d3 = m33 + m32; // near
            break;
        case CORNER_NXPYNZ: // left, top, near
            n1x = m03 + m00; n1y = m13 + m10; n1z = m23 + m20; d1 = m33 + m30; // left
            n2x = m03 - m01; n2y = m13 - m11; n2z = m23 - m21; d2 = m33 - m31; // top
            n3x = m03 + m02; n3y = m13 + m12; n3z = m23 + m22; d3 = m33 + m32; // near
            break;
        case CORNER_PXNYPZ: // right, bottom, far
            n1x = m03 - m00; n1y = m13 - m10; n1z = m23 - m20; d1 = m33 - m30; // right
            n2x = m03 + m01; n2y = m13 + m11; n2z = m23 + m21; d2 = m33 + m31; // bottom
            n3x = m03 - m02; n3y = m13 - m12; n3z = m23 - m22; d3 = m33 - m32; // far
            break;
        case CORNER_NXNYPZ: // left, bottom, far
            n1x = m03 + m00; n1y = m13 + m10; n1z = m23 + m20; d1 = m33 + m30; // left
            n2x = m03 + m01; n2y = m13 + m11; n2z = m23 + m21; d2 = m33 + m31; // bottom
            n3x = m03 - m02; n3y = m13 - m12; n3z = m23 - m22; d3 = m33 - m32; // far
            break;
        case CORNER_NXPYPZ: // left, top, far
            n1x = m03 + m00; n1y = m13 + m10; n1z = m23 + m20; d1 = m33 + m30; // left
            n2x = m03 - m01; n2y = m13 - m11; n2z = m23 - m21; d2 = m33 - m31; // top
            n3x = m03 - m02; n3y = m13 - m12; n3z = m23 - m22; d3 = m33 - m32; // far
            break;
        case CORNER_PXPYPZ: // right, top, far
            n1x = m03 - m00; n1y = m13 - m10; n1z = m23 - m20; d1 = m33 - m30; // right
            n2x = m03 - m01; n2y = m13 - m11; n2z = m23 - m21; d2 = m33 - m31; // top
            n3x = m03 - m02; n3y = m13 - m12; n3z = m23 - m22; d3 = m33 - m32; // far
            break;
        default:
            throw new IllegalArgumentException("corner"); //$NON-NLS-1$
        }
        float c23x, c23y, c23z;
        c23x = n2y * n3z - n2z * n3y;
        c23y = n2z * n3x - n2x * n3z;
        c23z = n2x * n3y - n2y * n3x;
        float c31x, c31y, c31z;
        c31x = n3y * n1z - n3z * n1y;
        c31y = n3z * n1x - n3x * n1z;
        c31z = n3x * n1y - n3y * n1x;
        float c12x, c12y, c12z;
        c12x = n1y * n2z - n1z * n2y;
        c12y = n1z * n2x - n1x * n2z;
        c12z = n1x * n2y - n1y * n2x;
        float invDot = 1.0f / (n1x * c23x + n1y * c23y + n1z * c23z);
        point.set((-c23x * d1 - c31x * d2 - c12x * d3) * invDot,
                (-c23y * d1 - c31y * d2 - c12y * d3) * invDot,
                (-c23z * d1 - c31z * d2 - c12z * d3) * invDot);
        return point;
    }

    /**
     * Compute the eye/origin of the perspective frustum transformation defined by <code>this</code> matrix, 
     * which can be a projection matrix or a combined modelview-projection matrix, and store the result
     * in the given <code>origin</code>.
     * <p>
     * Note that this method will only work using perspective projections obtained via one of the
     * perspective methods, such as {@link #perspective(float, float, float, float) perspective()}
     * or {@link #frustum(float, float, float, float, float, float) frustum()}.
     * <p>
     * Generally, this method computes the origin in the local frame of
     * any coordinate system that existed before <code>this</code>
     * transformation was applied to it in order to yield homogeneous clipping space.
     * <p>
     * Reference: <a href="http://geomalgorithms.com/a05-_intersect-1.html">http://geomalgorithms.com</a>
     * <p>
     * Reference: <a href="http://gamedevs.org/uploads/fast-extraction-viewing-frustum-planes-from-world-view-projection-matrix.pdf">
     * Fast Extraction of Viewing Frustum Planes from the World-View-Projection Matrix</a>
     * 
     * @param origin
     *          will hold the origin of the coordinate system before applying <code>this</code>
     *          perspective projection transformation
     * @return origin
     */
    public Vector3fc perspectiveOrigin(Vector3fc origin) {
        /*
         * Simply compute the intersection point of the left, right and top frustum plane.
         */
        float d1, d2, d3;
        float n1x, n1y, n1z, n2x, n2y, n2z, n3x, n3y, n3z;
        n1x = m03 + m00; n1y = m13 + m10; n1z = m23 + m20; d1 = m33 + m30; // left
        n2x = m03 - m00; n2y = m13 - m10; n2z = m23 - m20; d2 = m33 - m30; // right
        n3x = m03 - m01; n3y = m13 - m11; n3z = m23 - m21; d3 = m33 - m31; // top
        float c23x, c23y, c23z;
        c23x = n2y * n3z - n2z * n3y;
        c23y = n2z * n3x - n2x * n3z;
        c23z = n2x * n3y - n2y * n3x;
        float c31x, c31y, c31z;
        c31x = n3y * n1z - n3z * n1y;
        c31y = n3z * n1x - n3x * n1z;
        c31z = n3x * n1y - n3y * n1x;
        float c12x, c12y, c12z;
        c12x = n1y * n2z - n1z * n2y;
        c12y = n1z * n2x - n1x * n2z;
        c12z = n1x * n2y - n1y * n2x;
        float invDot = 1.0f / (n1x * c23x + n1y * c23y + n1z * c23z);
        origin.set((-c23x * d1 - c31x * d2 - c12x * d3) * invDot,
                (-c23y * d1 - c31y * d2 - c12y * d3) * invDot,
                (-c23z * d1 - c31z * d2 - c12z * d3) * invDot);
        return origin;
    }

    @Override
    public float perspectiveFov() {
        /*
         * Compute the angle between the bottom and top frustum plane normals.
         */
        float n1x, n1y, n1z, n2x, n2y, n2z;
        n1x = m03 + m01; n1y = m13 + m11; n1z = m23 + m21; // bottom
        n2x = m01 - m03; n2y = m11 - m13; n2z = m21 - m23; // top
        float n1len = (float) Math.sqrt(n1x * n1x + n1y * n1y + n1z * n1z);
        float n2len = (float) Math.sqrt(n2x * n2x + n2y * n2y + n2z * n2z);
        return (float) Math.acos((n1x * n2x + n1y * n2y + n1z * n2z) / (n1len * n2len));
    }

    @Override
    public float perspectiveNear() {
        return m32 / (m23 + m22);
    }

    @Override
    public float perspectiveFar() {
        return m32 / (m22 - m23);
    }

    @Override
    public Vector3fc frustumRayDir(float x, float y, Vector3fc dir) {
        /*
         * This method works by first obtaining the frustum plane normals,
         * then building the cross product to obtain the corner rays,
         * and finally bilinearly interpolating to obtain the desired direction.
         * The code below uses a condense form of doing all this making use
         * of some mathematical identities to simplify the overall expression.
         */
        float a = m10 * m23, b = m13 * m21, c = m10 * m21, d = m11 * m23, e = m13 * m20, f = m11 * m20;
        float g = m03 * m20, h = m01 * m23, i = m01 * m20, j = m03 * m21, k = m00 * m23, l = m00 * m21;
        float m = m00 * m13, n = m03 * m11, o = m00 * m11, p = m01 * m13, q = m03 * m10, r = m01 * m10;
        float m1x, m1y, m1z;
        m1x = (d + e + f - a - b - c) * (1.0f - y) + (a - b - c + d - e + f) * y;
        m1y = (j + k + l - g - h - i) * (1.0f - y) + (g - h - i + j - k + l) * y;
        m1z = (p + q + r - m - n - o) * (1.0f - y) + (m - n - o + p - q + r) * y;
        float m2x, m2y, m2z;
        m2x = (b - c - d + e + f - a) * (1.0f - y) + (a + b - c - d - e + f) * y;
        m2y = (h - i - j + k + l - g) * (1.0f - y) + (g + h - i - j - k + l) * y;
        m2z = (n - o - p + q + r - m) * (1.0f - y) + (m + n - o - p - q + r) * y;
        dir.set(m1x + (m2x - m1x) * x,
                m1y + (m2y - m1y) * x,
                m1z + (m2z - m1z) * x);
        dir.normalize();
        return dir;
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
    public Vector3fc originAffine(Vector3fc origin) {
        float a = m00 * m11 - m01 * m10;
        float b = m00 * m12 - m02 * m10;
        float d = m01 * m12 - m02 * m11;
        float g = m20 * m31 - m21 * m30;
        float h = m20 * m32 - m22 * m30;
        float j = m21 * m32 - m22 * m31;
        origin.set(-m10 * j + m11 * h - m12 * g,
                m00 * j - m01 * h + m02 * g,
                -m30 * d + m31 * b - m32 * a);
        return origin;
    }

    @Override
    public Vector3fc origin(Vector3fc origin) {
        float a = m00 * m11 - m01 * m10;
        float b = m00 * m12 - m02 * m10;
        float c = m00 * m13 - m03 * m10;
        float d = m01 * m12 - m02 * m11;
        float e = m01 * m13 - m03 * m11;
        float f = m02 * m13 - m03 * m12;
        float g = m20 * m31 - m21 * m30;
        float h = m20 * m32 - m22 * m30;
        float i = m20 * m33 - m23 * m30;
        float j = m21 * m32 - m22 * m31;
        float k = m21 * m33 - m23 * m31;
        float l = m22 * m33 - m23 * m32;
        float det = a * l - b * k + c * j + d * i - e * h + f * g;
        float invDet = 1.0f / det;
        float nm30 = (-m10 * j + m11 * h - m12 * g) * invDet;
        float nm31 = ( m00 * j - m01 * h + m02 * g) * invDet;
        float nm32 = (-m30 * d + m31 * b - m32 * a) * invDet;
        float nm33 = det / ( m20 * d - m21 * b + m22 * a);
        float x = nm30 * nm33;
        float y = nm31 * nm33;
        float z = nm32 * nm33;
        return origin.set(x, y, z);
    }

    @Override
    public Matrix4fc shadow(Vector4fc light, float a, float b, float c, float d) {
        return shadow(light.x(), light.y(), light.z(), light.w(), a, b, c, d, this);
    }

    @Override
    public Matrix4fc shadow(Vector4fc light, float a, float b, float c, float d, Matrix4fc dest) {
        return shadow(light.x(), light.y(), light.z(), light.w(), a, b, c, d, dest);
    }

    @Override
    public Matrix4fc shadow(float lightX, float lightY, float lightZ, float lightW, float a, float b, float c, float d) {
        return shadow(lightX, lightY, lightZ, lightW, a, b, c, d, this);
    }

    @Override
    public Matrix4fc shadow(float lightX, float lightY, float lightZ, float lightW, float a, float b, float c, float d, Matrix4fc dest) {
        // normalize plane
        float invPlaneLen = (float) (1.0 / Math.sqrt(a*a + b*b + c*c));
        float an = a * invPlaneLen;
        float bn = b * invPlaneLen;
        float cn = c * invPlaneLen;
        float dn = d * invPlaneLen;

        float dot = an * lightX + bn * lightY + cn * lightZ + dn * lightW;

        // compute right matrix elements
        float rm00 = dot - an * lightX;
        float rm01 = -an * lightY;
        float rm02 = -an * lightZ;
        float rm03 = -an * lightW;
        float rm10 = -bn * lightX;
        float rm11 = dot - bn * lightY;
        float rm12 = -bn * lightZ;
        float rm13 = -bn * lightW;
        float rm20 = -cn * lightX;
        float rm21 = -cn * lightY;
        float rm22 = dot - cn * lightZ;
        float rm23 = -cn * lightW;
        float rm30 = -dn * lightX;
        float rm31 = -dn * lightY;
        float rm32 = -dn * lightZ;
        float rm33 = dot - dn * lightW;

        // matrix multiplication
        float nm00 = m00 * rm00 + m10 * rm01 + m20 * rm02 + m30 * rm03;
        float nm01 = m01 * rm00 + m11 * rm01 + m21 * rm02 + m31 * rm03;
        float nm02 = m02 * rm00 + m12 * rm01 + m22 * rm02 + m32 * rm03;
        float nm03 = m03 * rm00 + m13 * rm01 + m23 * rm02 + m33 * rm03;
        float nm10 = m00 * rm10 + m10 * rm11 + m20 * rm12 + m30 * rm13;
        float nm11 = m01 * rm10 + m11 * rm11 + m21 * rm12 + m31 * rm13;
        float nm12 = m02 * rm10 + m12 * rm11 + m22 * rm12 + m32 * rm13;
        float nm13 = m03 * rm10 + m13 * rm11 + m23 * rm12 + m33 * rm13;
        float nm20 = m00 * rm20 + m10 * rm21 + m20 * rm22 + m30 * rm23;
        float nm21 = m01 * rm20 + m11 * rm21 + m21 * rm22 + m31 * rm23;
        float nm22 = m02 * rm20 + m12 * rm21 + m22 * rm22 + m32 * rm23;
        float nm23 = m03 * rm20 + m13 * rm21 + m23 * rm22 + m33 * rm23;
        dest.m30(m00 * rm30 + m10 * rm31 + m20 * rm32 + m30 * rm33);
        dest.m31(m01 * rm30 + m11 * rm31 + m21 * rm32 + m31 * rm33);
        dest.m32(m02 * rm30 + m12 * rm31 + m22 * rm32 + m32 * rm33);
        dest.m33(m03 * rm30 + m13 * rm31 + m23 * rm32 + m33 * rm33);
        dest.m00(nm00);
        dest.m01(nm01);
        dest.m02(nm02);
        dest.m03(nm03);
        dest.m10(nm10);
        dest.m11(nm11);
        dest.m12(nm12);
        dest.m13(nm13);
        dest.m20(nm20);
        dest.m21(nm21);
        dest.m22(nm22);
        dest.m23(nm23);
        dest.properties((byte) (properties & ~(PROPERTY_PERSPECTIVE | PROPERTY_IDENTITY | PROPERTY_TRANSLATION)));

        return dest;
    }

    @Override
    public Matrix4fc shadow(Vector4fc light, IMatrix4f planeTransform, Matrix4fc dest) {
        // compute plane equation by transforming (y = 0)
        float a = planeTransform.m10();
        float b = planeTransform.m11();
        float c = planeTransform.m12();
        float d = -a * planeTransform.m30() - b * planeTransform.m31() - c * planeTransform.m32();
        return shadow(light.x(), light.y(), light.z(), light.w(), a, b, c, d, dest);
    }

    @Override
    public Matrix4fc shadow(Vector4fc light, Matrix4fc planeTransform) {
        return shadow(light, planeTransform, this);
    }

    @Override
    public Matrix4fc shadow(float lightX, float lightY, float lightZ, float lightW, IMatrix4f planeTransform, Matrix4fc dest) {
        // compute plane equation by transforming (y = 0)
        float a = planeTransform.m10();
        float b = planeTransform.m11();
        float c = planeTransform.m12();
        float d = -a * planeTransform.m30() - b * planeTransform.m31() - c * planeTransform.m32();
        return shadow(lightX, lightY, lightZ, lightW, a, b, c, d, dest);
    }

    @Override
    public Matrix4fc shadow(float lightX, float lightY, float lightZ, float lightW, Matrix4fc planeTransform) {
        return shadow(lightX, lightY, lightZ, lightW, planeTransform, this);
    }

    @Override
    public Matrix4fc billboardCylindrical(IVector3f objPos, IVector3f targetPos, IVector3f up) {
        float dirX = targetPos.x() - objPos.x();
        float dirY = targetPos.y() - objPos.y();
        float dirZ = targetPos.z() - objPos.z();
        // left = up x dir
        float leftX = up.y() * dirZ - up.z() * dirY;
        float leftY = up.z() * dirX - up.x() * dirZ;
        float leftZ = up.x() * dirY - up.y() * dirX;
        // normalize left
        float invLeftLen = 1.0f / (float) Math.sqrt(leftX * leftX + leftY * leftY + leftZ * leftZ);
        leftX *= invLeftLen;
        leftY *= invLeftLen;
        leftZ *= invLeftLen;
        // recompute dir by constraining rotation around 'up'
        // dir = left x up
        dirX = leftY * up.z() - leftZ * up.y();
        dirY = leftZ * up.x() - leftX * up.z();
        dirZ = leftX * up.y() - leftY * up.x();
        // normalize dir
        float invDirLen = 1.0f / (float) Math.sqrt(dirX * dirX + dirY * dirY + dirZ * dirZ);
        dirX *= invDirLen;
        dirY *= invDirLen;
        dirZ *= invDirLen;
        // set matrix elements
        this._m00(leftX);
        this._m01(leftY);
        this._m02(leftZ);
        this._m03(0.0f);
        this._m10(up.x());
        this._m11(up.y());
        this._m12(up.z());
        this._m13(0.0f);
        this._m20(dirX);
        this._m21(dirY);
        this._m22(dirZ);
        this._m23(0.0f);
        this._m30(objPos.x());
        this._m31(objPos.y());
        this._m32(objPos.z());
        this._m33(1.0f);
        properties(PROPERTY_AFFINE);
        return this;
    }

    @Override
    public Matrix4fc billboardSpherical(IVector3f objPos, IVector3f targetPos, IVector3f up) {
        float dirX = targetPos.x() - objPos.x();
        float dirY = targetPos.y() - objPos.y();
        float dirZ = targetPos.z() - objPos.z();
        // normalize dir
        float invDirLen = 1.0f / (float) Math.sqrt(dirX * dirX + dirY * dirY + dirZ * dirZ);
        dirX *= invDirLen;
        dirY *= invDirLen;
        dirZ *= invDirLen;
        // left = up x dir
        float leftX = up.y() * dirZ - up.z() * dirY;
        float leftY = up.z() * dirX - up.x() * dirZ;
        float leftZ = up.x() * dirY - up.y() * dirX;
        // normalize left
        float invLeftLen = 1.0f / (float) Math.sqrt(leftX * leftX + leftY * leftY + leftZ * leftZ);
        leftX *= invLeftLen;
        leftY *= invLeftLen;
        leftZ *= invLeftLen;
        // up = dir x left
        float upX = dirY * leftZ - dirZ * leftY;
        float upY = dirZ * leftX - dirX * leftZ;
        float upZ = dirX * leftY - dirY * leftX;
        // set matrix elements
        this._m00(leftX);
        this._m01(leftY);
        this._m02(leftZ);
        this._m03(0.0f);
        this._m10(upX);
        this._m11(upY);
        this._m12(upZ);
        this._m13(0.0f);
        this._m20(dirX);
        this._m21(dirY);
        this._m22(dirZ);
        this._m23(0.0f);
        this._m30(objPos.x());
        this._m31(objPos.y());
        this._m32(objPos.z());
        this._m33(1.0f);
        properties(PROPERTY_AFFINE);
        return this;
    }

    @Override
    public Matrix4fc billboardSpherical(IVector3f objPos, IVector3f targetPos) {
        float toDirX = targetPos.x() - objPos.x();
        float toDirY = targetPos.y() - objPos.y();
        float toDirZ = targetPos.z() - objPos.z();
        float x = -toDirY;
        float y = toDirX;
        float w = (float) Math.sqrt(toDirX * toDirX + toDirY * toDirY + toDirZ * toDirZ) + toDirZ;
        float invNorm = (float) (1.0 / Math.sqrt(x * x + y * y + w * w));
        x *= invNorm;
        y *= invNorm;
        w *= invNorm;
        float q00 = (x + x) * x;
        float q11 = (y + y) * y;
        float q01 = (x + x) * y;
        float q03 = (x + x) * w;
        float q13 = (y + y) * w;
        this._m00(1.0f - q11);
        this._m01(q01);
        this._m02(-q13);
        this._m03(0.0f);
        this._m10(q01);
        this._m11(1.0f - q00);
        this._m12(q03);
        this._m13(0.0f);
        this._m20(q13);
        this._m21(-q03);
        this._m22(1.0f - q11 - q00);
        this._m23(0.0f);
        this._m30(objPos.x());
        this._m31(objPos.y());
        this._m32(objPos.z());
        this._m33(1.0f);
        properties(PROPERTY_AFFINE);
        return this;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Float.floatToIntBits(m00);
        result = prime * result + Float.floatToIntBits(m01);
        result = prime * result + Float.floatToIntBits(m02);
        result = prime * result + Float.floatToIntBits(m03);
        result = prime * result + Float.floatToIntBits(m10);
        result = prime * result + Float.floatToIntBits(m11);
        result = prime * result + Float.floatToIntBits(m12);
        result = prime * result + Float.floatToIntBits(m13);
        result = prime * result + Float.floatToIntBits(m20);
        result = prime * result + Float.floatToIntBits(m21);
        result = prime * result + Float.floatToIntBits(m22);
        result = prime * result + Float.floatToIntBits(m23);
        result = prime * result + Float.floatToIntBits(m30);
        result = prime * result + Float.floatToIntBits(m31);
        result = prime * result + Float.floatToIntBits(m32);
        result = prime * result + Float.floatToIntBits(m33);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Matrix4fc))
            return false;
        IMatrix4f other = (IMatrix4f) obj;
        if (Float.floatToIntBits(m00) != Float.floatToIntBits(other.m00()))
            return false;
        if (Float.floatToIntBits(m01) != Float.floatToIntBits(other.m01()))
            return false;
        if (Float.floatToIntBits(m02) != Float.floatToIntBits(other.m02()))
            return false;
        if (Float.floatToIntBits(m03) != Float.floatToIntBits(other.m03()))
            return false;
        if (Float.floatToIntBits(m10) != Float.floatToIntBits(other.m10()))
            return false;
        if (Float.floatToIntBits(m11) != Float.floatToIntBits(other.m11()))
            return false;
        if (Float.floatToIntBits(m12) != Float.floatToIntBits(other.m12()))
            return false;
        if (Float.floatToIntBits(m13) != Float.floatToIntBits(other.m13()))
            return false;
        if (Float.floatToIntBits(m20) != Float.floatToIntBits(other.m20()))
            return false;
        if (Float.floatToIntBits(m21) != Float.floatToIntBits(other.m21()))
            return false;
        if (Float.floatToIntBits(m22) != Float.floatToIntBits(other.m22()))
            return false;
        if (Float.floatToIntBits(m23) != Float.floatToIntBits(other.m23()))
            return false;
        if (Float.floatToIntBits(m30) != Float.floatToIntBits(other.m30()))
            return false;
        if (Float.floatToIntBits(m31) != Float.floatToIntBits(other.m31()))
            return false;
        if (Float.floatToIntBits(m32) != Float.floatToIntBits(other.m32()))
            return false;
        if (Float.floatToIntBits(m33) != Float.floatToIntBits(other.m33()))
            return false;
        return true;
    }

    @Override
    public Matrix4fc pick(float x, float y, float width, float height, int[] viewport, Matrix4fc dest) {
        float sx = viewport[2] / width;
        float sy = viewport[3] / height;
        float tx = (viewport[2] + 2.0f * (viewport[0] - x)) / width;
        float ty = (viewport[3] + 2.0f * (viewport[1] - y)) / height;
        dest.m30(m00 * tx + m10 * ty + m30);
        dest.m31(m01 * tx + m11 * ty + m31);
        dest.m32(m02 * tx + m12 * ty + m32);
        dest.m33(m03 * tx + m13 * ty + m33);
        dest.m00(m00 * sx);
        dest.m01(m01 * sx);
        dest.m02(m02 * sx);
        dest.m03(m03 * sx);
        dest.m10(m10 * sy);
        dest.m11(m11 * sy);
        dest.m12(m12 * sy);
        dest.m13(m13 * sy);
        dest.properties(0);
        return dest;
    }

    @Override
    public Matrix4fc pick(float x, float y, float width, float height, int[] viewport) {
        return pick(x, y, width, height, viewport, this);
    }

    @Override
    public boolean isAffine() {
        return m03 == 0.0f && m13 == 0.0f && m23 == 0.0f && m33 == 1.0f;
    }

    @Override
    public Matrix4fc swap(Matrix4fc other) {
        float tmp;
        tmp = this.m00; this.m00 = other.m00(); other.m00(tmp);
        tmp = this.m01; this.m01 = other.m01(); other.m01(tmp);
        tmp = this.m02; this.m02 = other.m02(); other.m02(tmp);
        tmp = this.m03; this.m03 = other.m03(); other.m03(tmp);
        tmp = this.m10; this.m10 = other.m10(); other.m10(tmp);
        tmp = this.m11; this.m11 = other.m11(); other.m11(tmp);
        tmp = this.m12; this.m12 = other.m12(); other.m12(tmp);
        tmp = this.m13; this.m13 = other.m13(); other.m13(tmp);
        tmp = this.m20; this.m20 = other.m20(); other.m20(tmp);
        tmp = this.m21; this.m21 = other.m21(); other.m21(tmp);
        tmp = this.m22; this.m22 = other.m22(); other.m22(tmp);
        tmp = this.m23; this.m23 = other.m23(); other.m23(tmp);
        tmp = this.m30; this.m30 = other.m30(); other.m30(tmp);
        tmp = this.m31; this.m31 = other.m31(); other.m31(tmp);
        tmp = this.m32; this.m32 = other.m32(); other.m32(tmp);
        tmp = this.m33; this.m33 = other.m33(); other.m33(tmp);
        byte props = properties;
        this.properties = other.properties();
        other.properties(props);
        return this;
    }

    @Override
    public Matrix4fc arcball(float radius, float centerX, float centerY, float centerZ, float angleX, float angleY, Matrix4fc dest) {
        float m30 = m20 * -radius + this.m30;
        float m31 = m21 * -radius + this.m31;
        float m32 = m22 * -radius + this.m32;
        float m33 = m23 * -radius + this.m33;
        float sin = (float) Math.sin(angleX);
        float cos = (float) Math.cosFromSin(sin, angleX);
        float nm10 = m10 * cos + m20 * sin;
        float nm11 = m11 * cos + m21 * sin;
        float nm12 = m12 * cos + m22 * sin;
        float nm13 = m13 * cos + m23 * sin;
        float m20 = this.m20 * cos - m10 * sin;
        float m21 = this.m21 * cos - m11 * sin;
        float m22 = this.m22 * cos - m12 * sin;
        float m23 = this.m23 * cos - m13 * sin;
        sin = (float) Math.sin(angleY);
        cos = (float) Math.cosFromSin(sin, angleY);
        float nm00 = m00 * cos - m20 * sin;
        float nm01 = m01 * cos - m21 * sin;
        float nm02 = m02 * cos - m22 * sin;
        float nm03 = m03 * cos - m23 * sin;
        float nm20 = m00 * sin + m20 * cos;
        float nm21 = m01 * sin + m21 * cos;
        float nm22 = m02 * sin + m22 * cos;
        float nm23 = m03 * sin + m23 * cos;
        dest.m30(-nm00 * centerX - nm10 * centerY - nm20 * centerZ + m30);
        dest.m31(-nm01 * centerX - nm11 * centerY - nm21 * centerZ + m31);
        dest.m32(-nm02 * centerX - nm12 * centerY - nm22 * centerZ + m32);
        dest.m33(-nm03 * centerX - nm13 * centerY - nm23 * centerZ + m33);
        dest.m20(nm20);
        dest.m21(nm21);
        dest.m22(nm22);
        dest.m23(nm23);
        dest.m10(nm10);
        dest.m11(nm11);
        dest.m12(nm12);
        dest.m13(nm13);
        dest.m00(nm00);
        dest.m01(nm01);
        dest.m02(nm02);
        dest.m03(nm03);
        dest.properties((byte) (properties & ~(PROPERTY_PERSPECTIVE | PROPERTY_IDENTITY | PROPERTY_TRANSLATION)));
        return dest;
    }

    @Override
    public Matrix4fc arcball(float radius, IVector3f center, float angleX, float angleY, Matrix4fc dest) {
        return arcball(radius, center.x(), center.y(), center.z(), angleX, angleY, dest);
    }

    @Override
    public Matrix4fc arcball(float radius, float centerX, float centerY, float centerZ, float angleX, float angleY) {
        return arcball(radius, centerX, centerY, centerZ, angleX, angleY, this);
    }

    @Override
    public Matrix4fc arcball(float radius, IVector3f center, float angleX, float angleY) {
        return arcball(radius, center.x(), center.y(), center.z(), angleX, angleY, this);
    }

    @Override
    public Matrix4fc frustumAabb(Vector3fc min, Vector3fc max) {
        float minX = Float.POSITIVE_INFINITY;
        float minY = Float.POSITIVE_INFINITY;
        float minZ = Float.POSITIVE_INFINITY;
        float maxX = Float.NEGATIVE_INFINITY;
        float maxY = Float.NEGATIVE_INFINITY;
        float maxZ = Float.NEGATIVE_INFINITY;
        for (int t = 0; t < 8; t++) {
            float x = ((t & 1) << 1) - 1.0f;
            float y = (((t >>> 1) & 1) << 1) - 1.0f;
            float z = (((t >>> 2) & 1) << 1) - 1.0f;
            float invW = 1.0f / (m03 * x + m13 * y + m23 * z + m33);
            float nx = (m00 * x + m10 * y + m20 * z + m30) * invW;
            float ny = (m01 * x + m11 * y + m21 * z + m31) * invW;
            float nz = (m02 * x + m12 * y + m22 * z + m32) * invW;
            minX = minX < nx ? minX : nx;
            minY = minY < ny ? minY : ny;
            minZ = minZ < nz ? minZ : nz;
            maxX = maxX > nx ? maxX : nx;
            maxY = maxY > ny ? maxY : ny;
            maxZ = maxZ > nz ? maxZ : nz;
        }
        min.set(minX, minY, minZ);
        max.set(maxX, maxY, maxZ);
        return this;
    }

    @Override
    public Matrix4fc projectedGridRange(IMatrix4f projector, float sLower, float sUpper, Matrix4fc dest) {
        // Compute intersection with frustum edges and plane
        float minX = Float.POSITIVE_INFINITY, minY = Float.POSITIVE_INFINITY;
        float maxX = Float.NEGATIVE_INFINITY, maxY = Float.NEGATIVE_INFINITY;
        boolean intersection = false;
        for (int t = 0; t < 3 * 4; t++) {
            float c0X, c0Y, c0Z;
            float c1X, c1Y, c1Z;
            if (t < 4) {
                // all x edges
                c0X = -1; c1X = +1;
                c0Y = c1Y = ((t & 1) << 1) - 1.0f;
                c0Z = c1Z = (((t >>> 1) & 1) << 1) - 1.0f;
            } else if (t < 8) {
                // all y edges
                c0Y = -1; c1Y = +1;
                c0X = c1X = ((t & 1) << 1) - 1.0f;
                c0Z = c1Z = (((t >>> 1) & 1) << 1) - 1.0f;
            } else {
                // all z edges
                c0Z = -1; c1Z = +1;
                c0X = c1X = ((t & 1) << 1) - 1.0f;
                c0Y = c1Y = (((t >>> 1) & 1) << 1) - 1.0f;
            }
            // unproject corners
            float invW = 1.0f / (m03 * c0X + m13 * c0Y + m23 * c0Z + m33);
            float p0x = (m00 * c0X + m10 * c0Y + m20 * c0Z + m30) * invW;
            float p0y = (m01 * c0X + m11 * c0Y + m21 * c0Z + m31) * invW;
            float p0z = (m02 * c0X + m12 * c0Y + m22 * c0Z + m32) * invW;
            invW = 1.0f / (m03 * c1X + m13 * c1Y + m23 * c1Z + m33);
            float p1x = (m00 * c1X + m10 * c1Y + m20 * c1Z + m30) * invW;
            float p1y = (m01 * c1X + m11 * c1Y + m21 * c1Z + m31) * invW;
            float p1z = (m02 * c1X + m12 * c1Y + m22 * c1Z + m32) * invW;
            float dirX = p1x - p0x;
            float dirY = p1y - p0y;
            float dirZ = p1z - p0z;
            float invDenom = 1.0f / dirY;
            // test for intersection
            for (int s = 0; s < 2; s++) {
                float isectT = -(p0y + (s == 0 ? sLower : sUpper)) * invDenom;
                if (isectT >= 0.0f && isectT <= 1.0f) {
                    intersection = true;
                    // project with projector matrix
                    float ix = p0x + isectT * dirX;
                    float iz = p0z + isectT * dirZ;
                    invW = 1.0f / (projector.m03() * ix + projector.m23() * iz + projector.m33());
                    float px = (projector.m00() * ix + projector.m20() * iz + projector.m30()) * invW;
                    float py = (projector.m01() * ix + projector.m21() * iz + projector.m31()) * invW;
                    minX = minX < px ? minX : px;
                    minY = minY < py ? minY : py;
                    maxX = maxX > px ? maxX : px;
                    maxY = maxY > py ? maxY : py;
                }
            }
        }
        if (!intersection)
            return null; // <- projected grid is not visible
        dest.set(maxX - minX, 0, 0, 0, 0, maxY - minY, 0, 0, 0, 0, 1, 0, minX, minY, 0, 1);
        dest.properties(PROPERTY_AFFINE);
        return dest;
    }

    @Override
    public Matrix4fc perspectiveFrustumSlice(float near, float far, Matrix4fc dest) {
        float invOldNear = (m23 + m22) / m32;
        float invNearFar = 1.0f / (near - far);
        dest.m00(m00 * invOldNear * near);
        dest.m01(m01);
        dest.m02(m02);
        dest.m03(m03);
        dest.m10(m10);
        dest.m11(m11 * invOldNear * near);
        dest.m12(m12);
        dest.m13(m13);
        dest.m20(m20);
        dest.m21(m21);
        dest.m22((far + near) * invNearFar);
        dest.m23(m23);
        dest.m30(m30);
        dest.m31(m31);
        dest.m32((far + far) * near * invNearFar);
        dest.m33(m33);
        dest.properties((byte) (properties & ~(PROPERTY_IDENTITY | PROPERTY_TRANSLATION)));
        return dest;
    }

    @Override
    public Matrix4fc orthoCrop(IMatrix4f view, Matrix4fc dest) {
        // determine min/max world z and min/max orthographically view-projected x/y
        float minX = Float.POSITIVE_INFINITY, maxX = Float.NEGATIVE_INFINITY;
        float minY = Float.POSITIVE_INFINITY, maxY = Float.NEGATIVE_INFINITY;
        float minZ = Float.POSITIVE_INFINITY, maxZ = Float.NEGATIVE_INFINITY;
        for (int t = 0; t < 8; t++) {
            float x = ((t & 1) << 1) - 1.0f;
            float y = (((t >>> 1) & 1) << 1) - 1.0f;
            float z = (((t >>> 2) & 1) << 1) - 1.0f;
            float invW = 1.0f / (m03 * x + m13 * y + m23 * z + m33);
            float wx = (m00 * x + m10 * y + m20 * z + m30) * invW;
            float wy = (m01 * x + m11 * y + m21 * z + m31) * invW;
            float wz = (m02 * x + m12 * y + m22 * z + m32) * invW;
            invW = 1.0f / (view.m03() * wx + view.m13() * wy + view.m23() * wz + view.m33());
            float vx = view.m00() * wx + view.m10() * wy + view.m20() * wz + view.m30();
            float vy = view.m01() * wx + view.m11() * wy + view.m21() * wz + view.m31();
            float vz = (view.m02() * wx + view.m12() * wy + view.m22() * wz + view.m32()) * invW;
            minX = minX < vx ? minX : vx;
            maxX = maxX > vx ? maxX : vx;
            minY = minY < vy ? minY : vy;
            maxY = maxY > vy ? maxY : vy;
            minZ = minZ < vz ? minZ : vz;
            maxZ = maxZ > vz ? maxZ : vz;
        }
        // build crop projection matrix to fit 'this' frustum into view
        return dest.setOrtho(minX, maxX, minY, maxY, -maxZ, -minZ);
    }

    @Override
    public Matrix4fc trapezoidCrop(float p0x, float p0y, float p1x, float p1y, float p2x, float p2y, float p3x, float p3y) {
        float aX = p1y - p0y, aY = p0x - p1x;
        float m00 = aY;
        float m10 = -aX;
        float m30 = aX * p0y - aY * p0x;
        float m01 = aX;
        float m11 = aY;
        float m31 = -(aX * p0x + aY * p0y);
        float c3x = m00 * p3x + m10 * p3y + m30;
        float c3y = m01 * p3x + m11 * p3y + m31;
        float s = -c3x / c3y;
        m00 += s * m01;
        m10 += s * m11;
        m30 += s * m31;
        float d1x = m00 * p1x + m10 * p1y + m30;
        float d2x = m00 * p2x + m10 * p2y + m30;
        float d = d1x * c3y / (d2x - d1x);
        m31 += d;
        float sx = 2.0f / d2x;
        float sy = 1.0f / (c3y + d);
        float u = (sy + sy) * d / (1.0f - sy * d);
        float m03 = m01 * sy;
        float m13 = m11 * sy;
        float m33 = m31 * sy;
        m01 = (u + 1.0f) * m03;
        m11 = (u + 1.0f) * m13;
        m31 = (u + 1.0f) * m33 - u;
        m00 = sx * m00 - m03;
        m10 = sx * m10 - m13;
        m30 = sx * m30 - m33;
        set(m00, m01, 0, m03,
            m10, m11, 0, m13,
              0,   0, 1,   0,
            m30, m31, 0, m33);
        properties(0);
        return this;
    }

    @Override
    public Matrix4fc transformAab(float minX, float minY, float minZ, float maxX, float maxY, float maxZ, Vector3fc outMin, Vector3fc outMax) {
        float xax = m00 * minX, xay = m01 * minX, xaz = m02 * minX;
        float xbx = m00 * maxX, xby = m01 * maxX, xbz = m02 * maxX;
        float yax = m10 * minY, yay = m11 * minY, yaz = m12 * minY;
        float ybx = m10 * maxY, yby = m11 * maxY, ybz = m12 * maxY;
        float zax = m20 * minZ, zay = m21 * minZ, zaz = m22 * minZ;
        float zbx = m20 * maxZ, zby = m21 * maxZ, zbz = m22 * maxZ;
        float xminx, xminy, xminz, yminx, yminy, yminz, zminx, zminy, zminz;
        float xmaxx, xmaxy, xmaxz, ymaxx, ymaxy, ymaxz, zmaxx, zmaxy, zmaxz;
        if (xax < xbx) {
            xminx = xax;
            xmaxx = xbx;
        } else {
            xminx = xbx;
            xmaxx = xax;
        }
        if (xay < xby) {
            xminy = xay;
            xmaxy = xby;
        } else {
            xminy = xby;
            xmaxy = xay;
        }
        if (xaz < xbz) {
            xminz = xaz;
            xmaxz = xbz;
        } else {
            xminz = xbz;
            xmaxz = xaz;
        }
        if (yax < ybx) {
            yminx = yax;
            ymaxx = ybx;
        } else {
            yminx = ybx;
            ymaxx = yax;
        }
        if (yay < yby) {
            yminy = yay;
            ymaxy = yby;
        } else {
            yminy = yby;
            ymaxy = yay;
        }
        if (yaz < ybz) {
            yminz = yaz;
            ymaxz = ybz;
        } else {
            yminz = ybz;
            ymaxz = yaz;
        }
        if (zax < zbx) {
            zminx = zax;
            zmaxx = zbx;
        } else {
            zminx = zbx;
            zmaxx = zax;
        }
        if (zay < zby) {
            zminy = zay;
            zmaxy = zby;
        } else {
            zminy = zby;
            zmaxy = zay;
        }
        if (zaz < zbz) {
            zminz = zaz;
            zmaxz = zbz;
        } else {
            zminz = zbz;
            zmaxz = zaz;
        }
        outMin.set(xminx + yminx + zminx + m30,
                xminy + yminy + zminy + m31,
                xminz + yminz + zminz + m32);
        outMax.set(xmaxx + ymaxx + zmaxx + m30,
                xmaxy + ymaxy + zmaxy + m31,
                xmaxz + ymaxz + zmaxz + m32);
        return this;
    }

    @Override
    public Matrix4fc transformAab(IVector3f min, IVector3f max, Vector3fc outMin, Vector3fc outMax) {
        return transformAab(min.x(), min.y(), min.z(), max.x(), max.y(), max.z(), outMin, outMax);
    }

    @Override
    public Matrix4fc lerp(IMatrix4f other, float t) {
        return lerp(other, t, this);
    }

    @Override
    public Matrix4fc lerp(IMatrix4f other, float t, Matrix4fc dest) {
        dest.m00(m00 + (other.m00() - m00) * t);
        dest.m01(m01 + (other.m01() - m01) * t);
        dest.m02(m02 + (other.m02() - m02) * t);
        dest.m03(m03 + (other.m03() - m03) * t);
        dest.m10(m10 + (other.m10() - m10) * t);
        dest.m11(m11 + (other.m11() - m11) * t);
        dest.m12(m12 + (other.m12() - m12) * t);
        dest.m13(m13 + (other.m13() - m13) * t);
        dest.m20(m20 + (other.m20() - m20) * t);
        dest.m21(m21 + (other.m21() - m21) * t);
        dest.m22(m22 + (other.m22() - m22) * t);
        dest.m23(m23 + (other.m23() - m23) * t);
        dest.m30(m30 + (other.m30() - m30) * t);
        dest.m31(m31 + (other.m31() - m31) * t);
        dest.m32(m32 + (other.m32() - m32) * t);
        dest.m33(m33 + (other.m33() - m33) * t);
        return dest;
    }

    @Override
    public Matrix4fc rotateTowards(IVector3f dir, IVector3f up, Matrix4fc dest) {
        return rotateTowards(dir.x(), dir.y(), dir.z(), up.x(), up.y(), up.z(), dest);
    }

    @Override
    public Matrix4fc rotateTowards(IVector3f dir, IVector3f up) {
        return rotateTowards(dir.x(), dir.y(), dir.z(), up.x(), up.y(), up.z(), this);
    }

    @Override
    public Matrix4fc rotateTowards(float dirX, float dirY, float dirZ, float upX, float upY, float upZ) {
        return rotateTowards(dirX, dirY, dirZ, upX, upY, upZ, this);
    }

    @Override
    public Matrix4fc rotateTowards(float dirX, float dirY, float dirZ, float upX, float upY, float upZ, Matrix4fc dest) {
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
        dest.m30(m30);
        dest.m31(m31);
        dest.m32(m32);
        dest.m33(m33);
        float nm00 = m00 * rm00 + m10 * rm01 + m20 * rm02;
        float nm01 = m01 * rm00 + m11 * rm01 + m21 * rm02;
        float nm02 = m02 * rm00 + m12 * rm01 + m22 * rm02;
        float nm03 = m03 * rm00 + m13 * rm01 + m23 * rm02;
        float nm10 = m00 * rm10 + m10 * rm11 + m20 * rm12;
        float nm11 = m01 * rm10 + m11 * rm11 + m21 * rm12;
        float nm12 = m02 * rm10 + m12 * rm11 + m22 * rm12;
        float nm13 = m03 * rm10 + m13 * rm11 + m23 * rm12;
        dest.m20(m00 * rm20 + m10 * rm21 + m20 * rm22);
        dest.m21(m01 * rm20 + m11 * rm21 + m21 * rm22);
        dest.m22(m02 * rm20 + m12 * rm21 + m22 * rm22);
        dest.m23(m03 * rm20 + m13 * rm21 + m23 * rm22);
        dest.m00(nm00);
        dest.m01(nm01);
        dest.m02(nm02);
        dest.m03(nm03);
        dest.m10(nm10);
        dest.m11(nm11);
        dest.m12(nm12);
        dest.m13(nm13);
        dest.properties((byte) (properties & ~(PROPERTY_PERSPECTIVE | PROPERTY_IDENTITY | PROPERTY_TRANSLATION)));
        return dest;
    }

    @Override
    public Matrix4fc rotationTowards(IVector3f dir, IVector3f up) {
        return rotationTowards(dir.x(), dir.y(), dir.z(), up.x(), up.y(), up.z());
    }

    @Override
    public Matrix4fc rotationTowards(float dirX, float dirY, float dirZ, float upX, float upY, float upZ) {
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
        this._m00(leftX);
        this._m01(leftY);
        this._m02(leftZ);
        this._m03(0.0f);
        this._m10(upnX);
        this._m11(upnY);
        this._m12(upnZ);
        this._m13(0.0f);
        this._m20(ndirX);
        this._m21(ndirY);
        this._m22(ndirZ);
        this._m23(0.0f);
        this._m30(0.0f);
        this._m31(0.0f);
        this._m32(0.0f);
        this._m33(1.0f);
        properties(PROPERTY_AFFINE);
        return this;
    }

    @Override
    public Matrix4fc translationRotateTowards(IVector3f pos, IVector3f dir, IVector3f up) {
        return translationRotateTowards(pos.x(), pos.y(), pos.z(), dir.x(), dir.y(), dir.z(), up.x(), up.y(), up.z());
    }

    @Override
    public Matrix4fc translationRotateTowards(float posX, float posY, float posZ, float dirX, float dirY, float dirZ, float upX, float upY, float upZ) {
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
        this._m00(leftX);
        this._m01(leftY);
        this._m02(leftZ);
        this._m03(0.0f);
        this._m10(upnX);
        this._m11(upnY);
        this._m12(upnZ);
        this._m13(0.0f);
        this._m20(ndirX);
        this._m21(ndirY);
        this._m22(ndirZ);
        this._m23(0.0f);
        this._m30(posX);
        this._m31(posY);
        this._m32(posZ);
        this._m33(1.0f);
        properties(PROPERTY_AFFINE);
        return this;
    }

    @Override
    public Vector3fc getEulerAnglesZYX(Vector3fc dest) {
        dest.set((float) Math.atan2(m12, m22), (float) Math.atan2(-m02, (float) Math.sqrt(m12 * m12 + m22 * m22)), (float) Math.atan2(m01, m00));
        return dest;
    }

    @Override
    public Matrix4fc affineSpan(Vector3fc corner, Vector3fc xDir, Vector3fc yDir, Vector3fc zDir) {
        float a = m10 * m22, b = m10 * m21, c = m10 * m02, d = m10 * m01;
        float e = m11 * m22, f = m11 * m20, g = m11 * m02, h = m11 * m00;
        float i = m12 * m21, j = m12 * m20, k = m12 * m01, l = m12 * m00;
        float m = m20 * m02, n = m20 * m01, o = m21 * m02, p = m21 * m00;
        float q = m22 * m01, r = m22 * m00;
        float s = 1.0f / (m00 * m11 - m01 * m10) * m22 + (m02 * m10 - m00 * m12) * m21 + (m01 * m12 - m02 * m11) * m20;
        float nm00 = (e - i) * s, nm01 = (o - q) * s, nm02 = (k - g) * s;
        float nm10 = (j - a) * s, nm11 = (r - m) * s, nm12 = (c - l) * s;
        float nm20 = (b - f) * s, nm21 = (n - p) * s, nm22 = (h - d) * s;
        corner.set(-nm00 - nm10 - nm20 + (a * m31 - b * m32 + f * m32 - e * m30 + i * m30 - j * m31) * s,
                -nm01 - nm11 - nm21 + (m * m31 - n * m32 + p * m32 - o * m30 + q * m30 - r * m31) * s,
                -nm02 - nm12 - nm22 + (g * m30 - k * m30 + l * m31 - c * m31 + d * m32 - h * m32) * s);
        xDir.set(2.0f * nm00, 2.0f * nm01, 2.0f * nm02);
        yDir.set(2.0f * nm10, 2.0f * nm11, 2.0f * nm12);
        zDir.set(2.0f * nm20, 2.0f * nm21, 2.0f * nm22);
        return this;
    }

    @Override
    public boolean testPoint(float x, float y, float z) {
        float nxX = m03 + m00, nxY = m13 + m10, nxZ = m23 + m20, nxW = m33 + m30;
        float pxX = m03 - m00, pxY = m13 - m10, pxZ = m23 - m20, pxW = m33 - m30;
        float nyX = m03 + m01, nyY = m13 + m11, nyZ = m23 + m21, nyW = m33 + m31;
        float pyX = m03 - m01, pyY = m13 - m11, pyZ = m23 - m21, pyW = m33 - m31;
        float nzX = m03 + m02, nzY = m13 + m12, nzZ = m23 + m22, nzW = m33 + m32;
        float pzX = m03 - m02, pzY = m13 - m12, pzZ = m23 - m22, pzW = m33 - m32;
        return nxX * x + nxY * y + nxZ * z + nxW >= 0 && pxX * x + pxY * y + pxZ * z + pxW >= 0 &&
               nyX * x + nyY * y + nyZ * z + nyW >= 0 && pyX * x + pyY * y + pyZ * z + pyW >= 0 &&
               nzX * x + nzY * y + nzZ * z + nzW >= 0 && pzX * x + pzY * y + pzZ * z + pzW >= 0;
    }

    @Override
    public boolean testSphere(float x, float y, float z, float r) {
        float invl;
        float nxX = m03 + m00, nxY = m13 + m10, nxZ = m23 + m20, nxW = m33 + m30;
        invl = (float) (1.0 / Math.sqrt(nxX * nxX + nxY * nxY + nxZ * nxZ));
        nxX *= invl; nxY *= invl; nxZ *= invl; nxW *= invl;
        float pxX = m03 - m00, pxY = m13 - m10, pxZ = m23 - m20, pxW = m33 - m30;
        invl = (float) (1.0 / Math.sqrt(pxX * pxX + pxY * pxY + pxZ * pxZ));
        pxX *= invl; pxY *= invl; pxZ *= invl; pxW *= invl;
        float nyX = m03 + m01, nyY = m13 + m11, nyZ = m23 + m21, nyW = m33 + m31;
        invl = (float) (1.0 / Math.sqrt(nyX * nyX + nyY * nyY + nyZ * nyZ));
        nyX *= invl; nyY *= invl; nyZ *= invl; nyW *= invl;
        float pyX = m03 - m01, pyY = m13 - m11, pyZ = m23 - m21, pyW = m33 - m31;
        invl = (float) (1.0 / Math.sqrt(pyX * pyX + pyY * pyY + pyZ * pyZ));
        pyX *= invl; pyY *= invl; pyZ *= invl; pyW *= invl;
        float nzX = m03 + m02, nzY = m13 + m12, nzZ = m23 + m22, nzW = m33 + m32;
        invl = (float) (1.0 / Math.sqrt(nzX * nzX + nzY * nzY + nzZ * nzZ));
        nzX *= invl; nzY *= invl; nzZ *= invl; nzW *= invl;
        float pzX = m03 - m02, pzY = m13 - m12, pzZ = m23 - m22, pzW = m33 - m32;
        invl = (float) (1.0 / Math.sqrt(pzX * pzX + pzY * pzY + pzZ * pzZ));
        pzX *= invl; pzY *= invl; pzZ *= invl; pzW *= invl;
        return nxX * x + nxY * y + nxZ * z + nxW >= -r && pxX * x + pxY * y + pxZ * z + pxW >= -r &&
               nyX * x + nyY * y + nyZ * z + nyW >= -r && pyX * x + pyY * y + pyZ * z + pyW >= -r &&
               nzX * x + nzY * y + nzZ * z + nzW >= -r && pzX * x + pzY * y + pzZ * z + pzW >= -r;
    }

    @Override
    public boolean testAab(float minX, float minY, float minZ, float maxX, float maxY, float maxZ) {
        float nxX = m03 + m00, nxY = m13 + m10, nxZ = m23 + m20, nxW = m33 + m30;
        float pxX = m03 - m00, pxY = m13 - m10, pxZ = m23 - m20, pxW = m33 - m30;
        float nyX = m03 + m01, nyY = m13 + m11, nyZ = m23 + m21, nyW = m33 + m31;
        float pyX = m03 - m01, pyY = m13 - m11, pyZ = m23 - m21, pyW = m33 - m31;
        float nzX = m03 + m02, nzY = m13 + m12, nzZ = m23 + m22, nzW = m33 + m32;
        float pzX = m03 - m02, pzY = m13 - m12, pzZ = m23 - m22, pzW = m33 - m32;
        /*
         * This is an implementation of the "2.4 Basic intersection test" of the mentioned site.
         * It does not distinguish between partially inside and fully inside, though, so the test with the 'p' vertex is omitted.
         */
        return nxX * (nxX < 0 ? minX : maxX) + nxY * (nxY < 0 ? minY : maxY) + nxZ * (nxZ < 0 ? minZ : maxZ) >= -nxW &&
               pxX * (pxX < 0 ? minX : maxX) + pxY * (pxY < 0 ? minY : maxY) + pxZ * (pxZ < 0 ? minZ : maxZ) >= -pxW &&
               nyX * (nyX < 0 ? minX : maxX) + nyY * (nyY < 0 ? minY : maxY) + nyZ * (nyZ < 0 ? minZ : maxZ) >= -nyW &&
               pyX * (pyX < 0 ? minX : maxX) + pyY * (pyY < 0 ? minY : maxY) + pyZ * (pyZ < 0 ? minZ : maxZ) >= -pyW &&
               nzX * (nzX < 0 ? minX : maxX) + nzY * (nzY < 0 ? minY : maxY) + nzZ * (nzZ < 0 ? minZ : maxZ) >= -nzW &&
               pzX * (pzX < 0 ? minX : maxX) + pzY * (pzY < 0 ? minY : maxY) + pzZ * (pzZ < 0 ? minZ : maxZ) >= -pzW;
    }
    
    public static void projViewFromRectangle(
            Vector3fc eye, Vector3fc p, Vector3fc x, Vector3fc y, float nearFarDist, boolean zeroToOne,
            Matrix4fc projDest, Matrix4fc viewDest) {
        float zx = y.y() * x.z() - y.z() * x.y(), zy = y.z() * x.x() - y.x() * x.z(), zz = y.x() * x.y() - y.y() * x.x();
        float zd = zx * (p.x() - eye.x()) + zy * (p.y() - eye.y()) + zz * (p.z() - eye.z());
        float zs = zd >= 0 ? 1 : -1; zx *= zs; zy *= zs; zz *= zs; zd *= zs; 
        viewDest.setLookAt(eye.x(), eye.y(), eye.z(), eye.x() + zx, eye.y() + zy, eye.z() + zz, y.x(), y.y(), y.z());
        float px = viewDest.m00() * p.x() + viewDest.m10() * p.y() + viewDest.m20() * p.z() + viewDest.m30();
        float py = viewDest.m01() * p.x() + viewDest.m11() * p.y() + viewDest.m21() * p.z() + viewDest.m31();
        float tx = viewDest.m00() * x.x() + viewDest.m10() * x.y() + viewDest.m20() * x.z();
        float ty = viewDest.m01() * y.x() + viewDest.m11() * y.y() + viewDest.m21() * y.z();
        float len = (float) Math.sqrt(zx * zx + zy * zy + zz * zz);
        float near = zd / len, far;
        if (Float.isInfinite(nearFarDist) && nearFarDist < 0.0f) {
            far = near;
            near = Float.POSITIVE_INFINITY;
        } else if (Float.isInfinite(nearFarDist) && nearFarDist > 0.0f) {
            far = Float.POSITIVE_INFINITY;
        } else if (nearFarDist < 0.0f) {
            far = near;
            near = near + nearFarDist;
        } else {
            far = near + nearFarDist;
        }
        projDest.setFrustum(px, px + tx, py, py + ty, near, far, zeroToOne);
    }
}
