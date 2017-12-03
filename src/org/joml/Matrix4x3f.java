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
import org.joml.api.vector.IVector3f;
import org.joml.api.vector.IVector4f;
import org.joml.api.vector.Vector3fc;
import org.joml.api.vector.Vector4fc;
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
 * Contains the definition of an affine 4x3 matrix (4 columns, 3 rows) of floats, and associated functions to transform
 * it. The matrix is column-major to match OpenGL's interpretation, and it looks like this:
 * <p>
 * m00  m10  m20  m30<br> m01  m11  m21  m31<br> m02  m12  m22  m32<br>
 *
 * @author Richard Greenlees
 * @author Kai Burjack
 */
public class Matrix4x3f extends Matrix4x3fc implements Externalizable {

    private static final long serialVersionUID = 1L;

    float m00, m01, m02;
    float m10, m11, m12;
    float m20, m21, m22;
    float m30, m31, m32;

    byte properties;

    /**
     * Create a new {@link Matrix4x3f} and set it to {@link #identity() identity}.
     */
    public Matrix4x3f() {
        m00 = 1.0f;
        m11 = 1.0f;
        m22 = 1.0f;
        properties = PROPERTY_IDENTITY | PROPERTY_TRANSLATION;
    }

    /**
     * Create a new {@link Matrix4x3f} by setting its left 3x3 submatrix to the values of the given {@link IMatrix3f}
     * and the rest to identity.
     *
     * @param mat the {@link IMatrix3f}
     */
    public Matrix4x3f(IMatrix3f mat) {
        if (mat instanceof Matrix3f) {
            MemUtil.INSTANCE.copy((Matrix3f) mat, this);
        } else {
            set3x3Matrix3fc(mat);
        }
    }

    /**
     * Create a new {@link Matrix4x3f} and make it a copy of the given matrix.
     *
     * @param mat the {@link IMatrix4x3f} to copy the values from
     */
    public Matrix4x3f(IMatrix4x3f mat) {
        if (mat instanceof Matrix4x3f) {
            MemUtil.INSTANCE.copy((Matrix4x3f) mat, this);
        } else {
            setMatrix4x3fc(mat);
        }
        properties = mat.properties();
    }

    /**
     * Create a new 4x4 matrix using the supplied float values.
     *
     * @param m00 the value of m00
     * @param m01 the value of m01
     * @param m02 the value of m02
     * @param m10 the value of m10
     * @param m11 the value of m11
     * @param m12 the value of m12
     * @param m20 the value of m20
     * @param m21 the value of m21
     * @param m22 the value of m22
     * @param m30 the value of m30
     * @param m31 the value of m31
     * @param m32 the value of m32
     */
    public Matrix4x3f(float m00, float m01, float m02,
                      float m10, float m11, float m12,
                      float m20, float m21, float m22,
                      float m30, float m31, float m32) {
        this.m00 = m00;
        this.m01 = m01;
        this.m02 = m02;
        this.m10 = m10;
        this.m11 = m11;
        this.m12 = m12;
        this.m20 = m20;
        this.m21 = m21;
        this.m22 = m22;
        this.m30 = m30;
        this.m31 = m31;
        this.m32 = m32;
        properties = 0;
    }

    //#ifdef __HAS_NIO__

    /**
     * Create a new {@link Matrix4x3f} by reading its 12 float components from the given {@link FloatBuffer} at the
     * buffer's current position.
     * <p>
     * That FloatBuffer is expected to hold the values in column-major order.
     * <p>
     * The buffer's position will not be changed by this method.
     *
     * @param buffer the {@link FloatBuffer} to read the matrix values from
     */
    public Matrix4x3f(FloatBuffer buffer) {
        MemUtil.INSTANCE.get(this, buffer.position(), buffer);
    }
    //#endif

    /**
     * Create a new {@link Matrix4x3f} and initialize its four columns using the supplied vectors.
     *
     * @param col0 the first column
     * @param col1 the second column
     * @param col2 the third column
     * @param col3 the fourth column
     */
    public Matrix4x3f(IVector3f col0, IVector3f col1, IVector3f col2, IVector3f col3) {
        if (col0 instanceof Vector3f &&
                col1 instanceof Vector3f &&
                col2 instanceof Vector3f &&
                col3 instanceof Vector3f) {
            MemUtil.INSTANCE.set(this, (Vector3f) col0, (Vector3f) col1, (Vector3f) col2, (Vector3f) col3);
        } else {
            setVector3fc(col0, col1, col2, col3);
        }
    }

    public Matrix4x3fc assumeNothing() {
        properties = 0;
        return this;
    }

    public byte properties() {
        return properties;
    }

    public float m00() {
        return m00;
    }

    public float m01() {
        return m01;
    }

    public float m02() {
        return m02;
    }

    public float m10() {
        return m10;
    }

    public float m11() {
        return m11;
    }

    public float m12() {
        return m12;
    }

    public float m20() {
        return m20;
    }

    public float m21() {
        return m21;
    }

    public float m22() {
        return m22;
    }

    public float m30() {
        return m30;
    }

    public float m31() {
        return m31;
    }

    public float m32() {
        return m32;
    }

    public Matrix4x3fc m00(float m00) {
        this.m00 = m00;
        properties &= ~(PROPERTY_IDENTITY | PROPERTY_TRANSLATION);
        return this;
    }

    public Matrix4x3fc m01(float m01) {
        this.m01 = m01;
        properties &= ~(PROPERTY_IDENTITY | PROPERTY_TRANSLATION);
        return this;
    }

    public Matrix4x3fc m02(float m02) {
        this.m02 = m02;
        properties &= ~(PROPERTY_IDENTITY | PROPERTY_TRANSLATION);
        return this;
    }

    public Matrix4x3fc m10(float m10) {
        this.m10 = m10;
        properties &= ~(PROPERTY_IDENTITY | PROPERTY_TRANSLATION);
        return this;
    }

    public Matrix4x3fc m11(float m11) {
        this.m11 = m11;
        properties &= ~(PROPERTY_IDENTITY | PROPERTY_TRANSLATION);
        return this;
    }

    public Matrix4x3fc m12(float m12) {
        this.m12 = m12;
        properties &= ~(PROPERTY_IDENTITY | PROPERTY_TRANSLATION);
        return this;
    }

    public Matrix4x3fc m20(float m20) {
        this.m20 = m20;
        properties &= ~(PROPERTY_IDENTITY | PROPERTY_TRANSLATION);
        return this;
    }

    public Matrix4x3fc m21(float m21) {
        this.m21 = m21;
        properties &= ~(PROPERTY_IDENTITY | PROPERTY_TRANSLATION);
        return this;
    }

    public Matrix4x3fc m22(float m22) {
        this.m22 = m22;
        properties &= ~(PROPERTY_IDENTITY | PROPERTY_TRANSLATION);
        return this;
    }

    public Matrix4x3fc m30(float m30) {
        this.m30 = m30;
        properties &= ~(PROPERTY_IDENTITY);
        return this;
    }

    public Matrix4x3fc m31(float m31) {
        this.m31 = m31;
        properties &= ~(PROPERTY_IDENTITY);
        return this;
    }

    public Matrix4x3fc m32(float m32) {
        this.m32 = m32;
        properties &= ~(PROPERTY_IDENTITY);
        return this;
    }

    public Matrix4x3fc properties(int properties) {
        this.properties = (byte) properties;
        return this;
    }

    public Matrix4x3fc identity() {
        MemUtil.INSTANCE.identity(this);
        properties = PROPERTY_IDENTITY | PROPERTY_TRANSLATION;
        return this;
    }

    public Matrix4x3fc set(IMatrix4x3f m) {
        if (m instanceof Matrix4x3f) {
            MemUtil.INSTANCE.copy((Matrix4x3f) m, this);
        } else {
            setMatrix4x3fc(m);
        }
        properties = m.properties();
        return this;
    }

    private void setMatrix4x3fc(IMatrix4x3f mat) {
        m00 = mat.m00();
        m01 = mat.m01();
        m02 = mat.m02();
        m10 = mat.m10();
        m11 = mat.m11();
        m12 = mat.m12();
        m20 = mat.m20();
        m21 = mat.m21();
        m22 = mat.m22();
        m30 = mat.m30();
        m31 = mat.m31();
        m32 = mat.m32();
    }

    public Matrix4x3fc set(IMatrix4f m) {
        if (m instanceof Matrix4f) {
            MemUtil.INSTANCE.copy((Matrix4f) m, this);
        } else {
            setMatrix4fc(m);
        }
        properties = (byte) (m.properties() & (PROPERTY_IDENTITY | PROPERTY_TRANSLATION));
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
        m30 = mat.m30();
        m31 = mat.m31();
        m32 = mat.m32();
    }

    public Matrix4fc get(Matrix4fc dest) {
        return dest.set4x3(this);
    }

    public Matrix4dc get(Matrix4dc dest) {
        return dest.set4x3(this);
    }

    public Matrix4x3fc set(IMatrix3f mat) {
        if (mat instanceof Matrix3f) {
            MemUtil.INSTANCE.copy((Matrix3f) mat, this);
        } else {
            setMatrix3fc(mat);
        }
        properties = 0;
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
        m30 = 0.0f;
        m31 = 0.0f;
        m32 = 0.0f;
    }

    public Matrix4x3fc set(AxisAngle4fc axisAngle) {
        float x = axisAngle.x();
        float y = axisAngle.y();
        float z = axisAngle.z();
        double angle = axisAngle.angle();
        double n = Math.sqrt(x * x + y * y + z * z);
        n = 1 / n;
        x *= n;
        y *= n;
        z *= n;
        double s = Math.sin(angle);
        double c = Math.cosFromSin(s, angle);
        double omc = 1.0 - c;
        m00 = (float) (c + x * x * omc);
        m11 = (float) (c + y * y * omc);
        m22 = (float) (c + z * z * omc);
        double tmp1 = x * y * omc;
        double tmp2 = z * s;
        m10 = (float) (tmp1 - tmp2);
        m01 = (float) (tmp1 + tmp2);
        tmp1 = x * z * omc;
        tmp2 = y * s;
        m20 = (float) (tmp1 + tmp2);
        m02 = (float) (tmp1 - tmp2);
        tmp1 = y * z * omc;
        tmp2 = x * s;
        m21 = (float) (tmp1 - tmp2);
        m12 = (float) (tmp1 + tmp2);
        m30 = 0.0f;
        m31 = 0.0f;
        m32 = 0.0f;
        properties = 0;
        return this;
    }

    public Matrix4x3fc set(AxisAngle4dc axisAngle) {
        double x = axisAngle.x();
        double y = axisAngle.y();
        double z = axisAngle.z();
        double angle = axisAngle.angle();
        double n = Math.sqrt(x * x + y * y + z * z);
        n = 1 / n;
        x *= n;
        y *= n;
        z *= n;
        double s = Math.sin(angle);
        double c = Math.cosFromSin(s, angle);
        double omc = 1.0 - c;
        m00 = (float) (c + x * x * omc);
        m11 = (float) (c + y * y * omc);
        m22 = (float) (c + z * z * omc);
        double tmp1 = x * y * omc;
        double tmp2 = z * s;
        m10 = (float) (tmp1 - tmp2);
        m01 = (float) (tmp1 + tmp2);
        tmp1 = x * z * omc;
        tmp2 = y * s;
        m20 = (float) (tmp1 + tmp2);
        m02 = (float) (tmp1 - tmp2);
        tmp1 = y * z * omc;
        tmp2 = x * s;
        m21 = (float) (tmp1 - tmp2);
        m12 = (float) (tmp1 + tmp2);
        m30 = 0.0f;
        m31 = 0.0f;
        m32 = 0.0f;
        properties = 0;
        return this;
    }

    public Matrix4x3fc set(IQuaternionf q) {
        return rotation(q);
    }

    public Matrix4x3fc set(IQuaterniond q) {
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
        properties = 0;
        return this;
    }

    public Matrix4x3fc set(IVector3f col0, IVector3f col1, IVector3f col2, IVector3f col3) {
        if (col0 instanceof Vector3f &&
                col1 instanceof Vector3f &&
                col2 instanceof Vector3f &&
                col3 instanceof Vector3f) {
            MemUtil.INSTANCE.set(this, (Vector3f) col0, (Vector3f) col1, (Vector3f) col2, (Vector3f) col3);
        } else {
            setVector3fc(col0, col1, col2, col3);
        }
        this.properties = 0;
        return this;
    }

    private void setVector3fc(IVector3f col0, IVector3f col1, IVector3f col2, IVector3f col3) {
        this.m00 = col0.x();
        this.m01 = col0.y();
        this.m02 = col0.z();
        this.m10 = col1.x();
        this.m11 = col1.y();
        this.m12 = col1.z();
        this.m20 = col2.x();
        this.m21 = col2.y();
        this.m22 = col2.z();
        this.m30 = col3.x();
        this.m31 = col3.y();
        this.m32 = col3.z();
    }

    public Matrix4x3fc set3x3(IMatrix4x3f mat) {
        if (mat instanceof Matrix4x3f) {
            MemUtil.INSTANCE.copy3x3((Matrix4x3f) mat, this);
        } else {
            set3x3Matrix4x3fc(mat);
        }
        properties &= mat.properties();
        return this;
    }

    private void set3x3Matrix4x3fc(IMatrix4x3f mat) {
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

    public Matrix4x3fc mul(IMatrix4x3f right) {
        return mul(right, this);
    }

    public Matrix4x3fc mul(IMatrix4x3f right, Matrix4x3fc dest) {
        if ((properties & PROPERTY_IDENTITY) != 0)
            return dest.set(right);
        else if ((right.properties() & PROPERTY_IDENTITY) != 0)
            return dest.set(this);
        else if ((properties & PROPERTY_TRANSLATION) != 0)
            return mulTranslation(right, dest);
        return mulGeneric(right, dest);
    }

    private Matrix4x3fc mulGeneric(IMatrix4x3f right, Matrix4x3fc dest) {
        float nm00 = m00 * right.m00() + m10 * right.m01() + m20 * right.m02();
        float nm01 = m01 * right.m00() + m11 * right.m01() + m21 * right.m02();
        float nm02 = m02 * right.m00() + m12 * right.m01() + m22 * right.m02();
        float nm10 = m00 * right.m10() + m10 * right.m11() + m20 * right.m12();
        float nm11 = m01 * right.m10() + m11 * right.m11() + m21 * right.m12();
        float nm12 = m02 * right.m10() + m12 * right.m11() + m22 * right.m12();
        float nm20 = m00 * right.m20() + m10 * right.m21() + m20 * right.m22();
        float nm21 = m01 * right.m20() + m11 * right.m21() + m21 * right.m22();
        float nm22 = m02 * right.m20() + m12 * right.m21() + m22 * right.m22();
        float nm30 = m00 * right.m30() + m10 * right.m31() + m20 * right.m32() + m30;
        float nm31 = m01 * right.m30() + m11 * right.m31() + m21 * right.m32() + m31;
        float nm32 = m02 * right.m30() + m12 * right.m31() + m22 * right.m32() + m32;
        dest.m00(nm00);
        dest.m01(nm01);
        dest.m02(nm02);
        dest.m10(nm10);
        dest.m11(nm11);
        dest.m12(nm12);
        dest.m20(nm20);
        dest.m21(nm21);
        dest.m22(nm22);
        dest.m30(nm30);
        dest.m31(nm31);
        dest.m32(nm32);
        dest.properties(0);
        return dest;
    }

    public Matrix4x3fc mulTranslation(IMatrix4x3f right, Matrix4x3fc dest) {
        float nm00 = right.m00();
        float nm01 = right.m01();
        float nm02 = right.m02();
        float nm10 = right.m10();
        float nm11 = right.m11();
        float nm12 = right.m12();
        float nm20 = right.m20();
        float nm21 = right.m21();
        float nm22 = right.m22();
        float nm30 = right.m30() + m30;
        float nm31 = right.m31() + m31;
        float nm32 = right.m32() + m32;
        dest.m00(nm00);
        dest.m01(nm01);
        dest.m02(nm02);
        dest.m10(nm10);
        dest.m11(nm11);
        dest.m12(nm12);
        dest.m20(nm20);
        dest.m21(nm21);
        dest.m22(nm22);
        dest.m30(nm30);
        dest.m31(nm31);
        dest.m32(nm32);
        dest.properties(0);
        return dest;
    }

    public Matrix4x3fc mulOrtho(IMatrix4x3f view) {
        return mulOrtho(view, this);
    }

    public Matrix4x3fc mulOrtho(IMatrix4x3f view, Matrix4x3fc dest) {
        float nm00 = m00 * view.m00();
        float nm01 = m11 * view.m01();
        float nm02 = m22 * view.m02();
        float nm10 = m00 * view.m10();
        float nm11 = m11 * view.m11();
        float nm12 = m22 * view.m12();
        float nm20 = m00 * view.m20();
        float nm21 = m11 * view.m21();
        float nm22 = m22 * view.m22();
        float nm30 = m00 * view.m30() + m30;
        float nm31 = m11 * view.m31() + m31;
        float nm32 = m22 * view.m32() + m32;
        dest.m00(nm00);
        dest.m01(nm01);
        dest.m02(nm02);
        dest.m10(nm10);
        dest.m11(nm11);
        dest.m12(nm12);
        dest.m20(nm20);
        dest.m21(nm21);
        dest.m22(nm22);
        dest.m30(nm30);
        dest.m31(nm31);
        dest.m32(nm32);
        dest.properties(0);
        return dest;
    }

    public Matrix4x3fc fma(IMatrix4x3f other, float otherFactor) {
        return fma(other, otherFactor, this);
    }

    public Matrix4x3fc fma(IMatrix4x3f other, float otherFactor, Matrix4x3fc dest) {
        dest.m00(m00 + other.m00() * otherFactor);
        dest.m01(m01 + other.m01() * otherFactor);
        dest.m02(m02 + other.m02() * otherFactor);
        dest.m10(m10 + other.m10() * otherFactor);
        dest.m11(m11 + other.m11() * otherFactor);
        dest.m12(m12 + other.m12() * otherFactor);
        dest.m20(m20 + other.m20() * otherFactor);
        dest.m21(m21 + other.m21() * otherFactor);
        dest.m22(m22 + other.m22() * otherFactor);
        dest.m30(m30 + other.m30() * otherFactor);
        dest.m31(m31 + other.m31() * otherFactor);
        dest.m32(m32 + other.m32() * otherFactor);
        dest.properties(0);
        return dest;
    }

    public Matrix4x3fc add(IMatrix4x3f other) {
        return add(other, this);
    }

    public Matrix4x3fc add(IMatrix4x3f other, Matrix4x3fc dest) {
        dest.m00(m00 + other.m00());
        dest.m01(m01 + other.m01());
        dest.m02(m02 + other.m02());
        dest.m10(m10 + other.m10());
        dest.m11(m11 + other.m11());
        dest.m12(m12 + other.m12());
        dest.m20(m20 + other.m20());
        dest.m21(m21 + other.m21());
        dest.m22(m22 + other.m22());
        dest.m30(m30 + other.m30());
        dest.m31(m31 + other.m31());
        dest.m32(m32 + other.m32());
        dest.properties(0);
        return dest;
    }

    public Matrix4x3fc sub(IMatrix4x3f subtrahend) {
        return sub(subtrahend, this);
    }

    public Matrix4x3fc sub(IMatrix4x3f subtrahend, Matrix4x3fc dest) {
        dest.m00(m00 - subtrahend.m00());
        dest.m01(m01 - subtrahend.m01());
        dest.m02(m02 - subtrahend.m02());
        dest.m10(m10 - subtrahend.m10());
        dest.m11(m11 - subtrahend.m11());
        dest.m12(m12 - subtrahend.m12());
        dest.m20(m20 - subtrahend.m20());
        dest.m21(m21 - subtrahend.m21());
        dest.m22(m22 - subtrahend.m22());
        dest.m30(m30 - subtrahend.m30());
        dest.m31(m31 - subtrahend.m31());
        dest.m32(m32 - subtrahend.m32());
        dest.properties(0);
        return dest;
    }

    public Matrix4x3fc mulComponentWise(IMatrix4x3f other) {
        return mulComponentWise(other, this);
    }

    public Matrix4x3fc mulComponentWise(IMatrix4x3f other, Matrix4x3fc dest) {
        dest.m00(m00 * other.m00());
        dest.m01(m01 * other.m01());
        dest.m02(m02 * other.m02());
        dest.m10(m10 * other.m10());
        dest.m11(m11 * other.m11());
        dest.m12(m12 * other.m12());
        dest.m20(m20 * other.m20());
        dest.m21(m21 * other.m21());
        dest.m22(m22 * other.m22());
        dest.m30(m30 * other.m30());
        dest.m31(m31 * other.m31());
        dest.m32(m32 * other.m32());
        dest.properties(0);
        return dest;
    }

    public Matrix4x3fc set(float m00, float m01, float m02,
                           float m10, float m11, float m12,
                           float m20, float m21, float m22,
                           float m30, float m31, float m32) {
        this.m00 = m00;
        this.m01 = m01;
        this.m02 = m02;
        this.m10 = m10;
        this.m11 = m11;
        this.m12 = m12;
        this.m20 = m20;
        this.m21 = m21;
        this.m22 = m22;
        this.m30 = m30;
        this.m31 = m31;
        this.m32 = m32;
        properties = 0;
        return this;
    }

    public Matrix4x3fc set(float m[], int off) {
        MemUtil.INSTANCE.copy(m, off, this);
        properties = 0;
        return this;
    }

    public Matrix4x3fc set(float m[]) {
        return set(m, 0);
    }

    //#ifdef __HAS_NIO__

    public Matrix4x3fc set(FloatBuffer buffer) {
        MemUtil.INSTANCE.get(this, buffer.position(), buffer);
        properties = 0;
        return this;
    }

    public Matrix4x3fc set(ByteBuffer buffer) {
        MemUtil.INSTANCE.get(this, buffer.position(), buffer);
        properties = 0;
        return this;
    }
    //#endif

    public float determinant() {
        return (m00 * m11 - m01 * m10) * m22
                + (m02 * m10 - m00 * m12) * m21
                + (m01 * m12 - m02 * m11) * m20;
    }

    public Matrix4x3fc invert(Matrix4x3fc dest) {
        if ((properties & PROPERTY_IDENTITY) != 0)
            return dest.identity();
        return invertGeneric(dest);
    }

    private Matrix4x3fc invertGeneric(Matrix4x3fc dest) {
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
        dest.m10(nm10);
        dest.m11(nm11);
        dest.m12(nm12);
        dest.m20(nm20);
        dest.m21(nm21);
        dest.m22(nm22);
        dest.m30(nm30);
        dest.m31(nm31);
        dest.m32(nm32);
        dest.properties(0);
        return dest;
    }

    public Matrix4x3fc invert() {
        return invert(this);
    }

    public Matrix4x3fc invertOrtho(Matrix4x3fc dest) {
        float invM00 = 1.0f / m00;
        float invM11 = 1.0f / m11;
        float invM22 = 1.0f / m22;
        dest.set(invM00, 0, 0,
                0, invM11, 0,
                0, 0, invM22,
                -m30 * invM00, -m31 * invM11, -m32 * invM22);
        dest.properties(0);
        return dest;
    }

    public Matrix4x3fc invertOrtho() {
        return invertOrtho(this);
    }

    public Matrix4x3fc invertUnitScale(Matrix4x3fc dest) {
        dest.set(m00, m10, m20,
                m01, m11, m21,
                m02, m12, m22,
                -m00 * m30 - m01 * m31 - m02 * m32,
                -m10 * m30 - m11 * m31 - m12 * m32,
                -m20 * m30 - m21 * m31 - m22 * m32);
        dest.properties(0);
        return dest;
    }

    public Matrix4x3fc invertUnitScale() {
        return invertUnitScale(this);
    }

    public Matrix4x3fc transpose3x3() {
        return transpose3x3(this);
    }

    public Matrix4x3fc transpose3x3(Matrix4x3fc dest) {
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
        dest.properties(properties);
        return dest;
    }

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

    public Matrix4x3fc translation(float x, float y, float z) {
        MemUtil.INSTANCE.identity(this);
        m30 = x;
        m31 = y;
        m32 = z;
        properties = PROPERTY_TRANSLATION;
        return this;
    }

    public Matrix4x3fc translation(IVector3f offset) {
        return translation(offset.x(), offset.y(), offset.z());
    }

    public Matrix4x3fc setTranslation(float x, float y, float z) {
        m30 = x;
        m31 = y;
        m32 = z;
        properties = 0;
        return this;
    }

    public Matrix4x3fc setTranslation(IVector3f xyz) {
        return setTranslation(xyz.x(), xyz.y(), xyz.z());
    }

    public Vector3fc getTranslation(Vector3fc dest) {
        dest.set(m30, m31, m32);
        return dest;
    }

    public Vector3fc getScale(Vector3fc dest) {
        dest.set((float) Math.sqrt(m00 * m00 + m01 * m01 + m02 * m02), (float) Math.sqrt(m10 * m10 + m11 * m11 + m12 * m12), (float) Math.sqrt(m20 * m20 + m21 * m21 + m22 * m22));
        return dest;
    }

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

    public String toString(NumberFormat formatter) {
        return formatter.format(m00) + " " + formatter.format(m10) + " " + formatter.format(m20) + " " + formatter.format(m30) + "\n"
                + formatter.format(m01) + " " + formatter.format(m11) + " " + formatter.format(m21) + " " + formatter.format(m31) + "\n"
                + formatter.format(m02) + " " + formatter.format(m12) + " " + formatter.format(m22) + " " + formatter.format(m32) + "\n";
    }

    public Matrix4x3fc get(Matrix4x3fc dest) {
        return dest.set(this);
    }

    public Matrix4x3dc get(Matrix4x3dc dest) {
        return dest.set(this);
    }

    public AxisAngle4fc getRotation(AxisAngle4fc dest) {
        return dest.set(this);
    }

    public AxisAngle4dc getRotation(AxisAngle4dc dest) {
        return dest.set(this);
    }

    public Quaternionfc getUnnormalizedRotation(Quaternionfc dest) {
        return dest.setFromUnnormalized(this);
    }

    public Quaternionfc getNormalizedRotation(Quaternionfc dest) {
        return dest.setFromNormalized(this);
    }

    public Quaterniondc getUnnormalizedRotation(Quaterniondc dest) {
        return dest.setFromUnnormalized(this);
    }

    public Quaterniondc getNormalizedRotation(Quaterniondc dest) {
        return dest.setFromNormalized(this);
    }

    //#ifdef __GWT__

    public Float32Array get(Float32Array buffer) {
        buffer.set(0, m00);
        buffer.set(1, m01);
        buffer.set(2, m02);
        buffer.set(3, m10);
        buffer.set(4, m11);
        buffer.set(5, m12);
        buffer.set(6, m20);
        buffer.set(7, m21);
        buffer.set(8, m22);
        buffer.set(9, m30);
        buffer.set(10, m31);
        buffer.set(11, m32);
        return buffer;
    }

    public Float32Array get(int index, Float32Array buffer) {
        buffer.set(index + 0, m00);
        buffer.set(index + 1, m01);
        buffer.set(index + 2, m02);
        buffer.set(index + 3, m10);
        buffer.set(index + 4, m11);
        buffer.set(index + 5, m12);
        buffer.set(index + 6, m20);
        buffer.set(index + 7, m21);
        buffer.set(index + 8, m22);
        buffer.set(index + 9, m30);
        buffer.set(index + 10, m31);
        buffer.set(index + 11, m32);
        return buffer;
    }
    //#endif

    //#ifdef __HAS_NIO__

    public FloatBuffer get(FloatBuffer buffer) {
        return get(buffer.position(), buffer);
    }

    public FloatBuffer get(int index, FloatBuffer buffer) {
        MemUtil.INSTANCE.put(this, index, buffer);
        return buffer;
    }

    public ByteBuffer get(ByteBuffer buffer) {
        return get(buffer.position(), buffer);
    }

    public ByteBuffer get(int index, ByteBuffer buffer) {
        MemUtil.INSTANCE.put(this, index, buffer);
        return buffer;
    }
    //#endif

    public float[] get(float[] arr, int offset) {
        MemUtil.INSTANCE.copy(this, arr, offset);
        return arr;
    }

    public float[] get(float[] arr) {
        return get(arr, 0);
    }

    public float[] get4x4(float[] arr, int offset) {
        MemUtil.INSTANCE.copy4x4(this, arr, offset);
        return arr;
    }

    public float[] get4x4(float[] arr) {
        return get4x4(arr, 0);
    }

    //#ifdef __HAS_NIO__

    public FloatBuffer get4x4(FloatBuffer buffer) {
        return get4x4(buffer.position(), buffer);
    }

    public FloatBuffer get4x4(int index, FloatBuffer buffer) {
        MemUtil.INSTANCE.put4x4(this, index, buffer);
        return buffer;
    }

    public ByteBuffer get4x4(ByteBuffer buffer) {
        return get4x4(buffer.position(), buffer);
    }

    public ByteBuffer get4x4(int index, ByteBuffer buffer) {
        MemUtil.INSTANCE.put4x4(this, index, buffer);
        return buffer;
    }

    public FloatBuffer getTransposed(FloatBuffer buffer) {
        return getTransposed(buffer.position(), buffer);
    }

    public FloatBuffer getTransposed(int index, FloatBuffer buffer) {
        MemUtil.INSTANCE.putTransposed(this, index, buffer);
        return buffer;
    }

    public ByteBuffer getTransposed(ByteBuffer buffer) {
        return getTransposed(buffer.position(), buffer);
    }

    public ByteBuffer getTransposed(int index, ByteBuffer buffer) {
        MemUtil.INSTANCE.putTransposed(this, index, buffer);
        return buffer;
    }
    //#endif

    public float[] getTransposed(float[] arr, int offset) {
        arr[offset + 0] = m00;
        arr[offset + 1] = m10;
        arr[offset + 2] = m20;
        arr[offset + 3] = m30;
        arr[offset + 4] = m01;
        arr[offset + 5] = m11;
        arr[offset + 6] = m21;
        arr[offset + 7] = m31;
        arr[offset + 8] = m02;
        arr[offset + 9] = m12;
        arr[offset + 10] = m22;
        arr[offset + 11] = m32;
        return arr;
    }

    public float[] getTransposed(float[] arr) {
        return getTransposed(arr, 0);
    }

    public Matrix4x3fc zero() {
        MemUtil.INSTANCE.zero(this);
        properties = 0;
        return this;
    }

    public Matrix4x3fc scaling(float factor) {
        return scaling(factor, factor, factor);
    }

    public Matrix4x3fc scaling(float x, float y, float z) {
        MemUtil.INSTANCE.identity(this);
        m00 = x;
        m11 = y;
        m22 = z;
        properties = 0;
        return this;
    }

    public Matrix4x3fc scaling(IVector3f xyz) {
        return scaling(xyz.x(), xyz.y(), xyz.z());
    }

    public Matrix4x3fc rotation(float angle, IVector3f axis) {
        return rotation(angle, axis.x(), axis.y(), axis.z());
    }

    public Matrix4x3fc rotation(AxisAngle4fc axisAngle) {
        return rotation(axisAngle.angle(), axisAngle.x(), axisAngle.y(), axisAngle.z());
    }

    public Matrix4x3fc rotation(float angle, float x, float y, float z) {
        float sin = (float) Math.sin(angle);
        float cos = (float) Math.cosFromSin(sin, angle);
        float C = 1.0f - cos;
        float xy = x * y, xz = x * z, yz = y * z;
        m00 = cos + x * x * C;
        m10 = xy * C - z * sin;
        m20 = xz * C + y * sin;
        m30 = 0.0f;
        m01 = xy * C + z * sin;
        m11 = cos + y * y * C;
        m21 = yz * C - x * sin;
        m31 = 0.0f;
        m02 = xz * C - y * sin;
        m12 = yz * C + x * sin;
        m22 = cos + z * z * C;
        m32 = 0.0f;
        properties = 0;
        return this;
    }

    public Matrix4x3fc rotationX(float ang) {
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
        m00 = 1.0f;
        m01 = 0.0f;
        m02 = 0.0f;
        m10 = 0.0f;
        m11 = cos;
        m12 = sin;
        m20 = 0.0f;
        m21 = -sin;
        m22 = cos;
        m30 = 0.0f;
        m31 = 0.0f;
        m32 = 0.0f;
        properties = 0;
        return this;
    }

    public Matrix4x3fc rotationY(float ang) {
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
        m00 = cos;
        m01 = 0.0f;
        m02 = -sin;
        m10 = 0.0f;
        m11 = 1.0f;
        m12 = 0.0f;
        m20 = sin;
        m21 = 0.0f;
        m22 = cos;
        m30 = 0.0f;
        m31 = 0.0f;
        m32 = 0.0f;
        properties = 0;
        return this;
    }

    public Matrix4x3fc rotationZ(float ang) {
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
        m00 = cos;
        m01 = sin;
        m02 = 0.0f;
        m10 = -sin;
        m11 = cos;
        m12 = 0.0f;
        m20 = 0.0f;
        m21 = 0.0f;
        m22 = 1.0f;
        m30 = 0.0f;
        m31 = 0.0f;
        m32 = 0.0f;
        properties = 0;
        return this;
    }

    public Matrix4x3fc rotationXYZ(float angleX, float angleY, float angleZ) {
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
        // set last column to identity
        m30 = 0.0f;
        m31 = 0.0f;
        m32 = 0.0f;
        properties = 0;
        return this;
    }

    public Matrix4x3fc rotationZYX(float angleZ, float angleY, float angleX) {
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
        // set last column to identity
        m30 = 0.0f;
        m31 = 0.0f;
        m32 = 0.0f;
        properties = 0;
        return this;
    }

    public Matrix4x3fc rotationYXZ(float angleY, float angleX, float angleZ) {
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
        // set last column to identity
        m30 = 0.0f;
        m31 = 0.0f;
        m32 = 0.0f;
        properties = 0;
        return this;
    }

    public Matrix4x3fc setRotationXYZ(float angleX, float angleY, float angleZ) {
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
        properties &= ~(PROPERTY_IDENTITY | PROPERTY_TRANSLATION);
        return this;
    }

    public Matrix4x3fc setRotationZYX(float angleZ, float angleY, float angleX) {
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
        properties &= ~(PROPERTY_IDENTITY | PROPERTY_TRANSLATION);
        return this;
    }

    public Matrix4x3fc setRotationYXZ(float angleY, float angleX, float angleZ) {
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
        properties &= ~(PROPERTY_IDENTITY | PROPERTY_TRANSLATION);
        return this;
    }

    public Matrix4x3fc rotation(IQuaternionf quat) {
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
        properties = 0;
        return this;
    }

    public Matrix4x3fc translationRotateScale(float tx, float ty, float tz,
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
        m00 = sx - (q11 + q22) * sx;
        m01 = (q01 + q23) * sx;
        m02 = (q02 - q13) * sx;
        m10 = (q01 - q23) * sy;
        m11 = sy - (q22 + q00) * sy;
        m12 = (q12 + q03) * sy;
        m20 = (q02 + q13) * sz;
        m21 = (q12 - q03) * sz;
        m22 = sz - (q11 + q00) * sz;
        m30 = tx;
        m31 = ty;
        m32 = tz;
        properties = 0;
        return this;
    }

    public Matrix4x3fc translationRotateScale(IVector3f translation,
                                              IQuaternionf quat,
                                              IVector3f scale) {
        return translationRotateScale(translation.x(), translation.y(), translation.z(), quat.x(), quat.y(), quat.z(), quat.w(), scale.x(), scale.y(), scale.z());
    }

    public Matrix4x3fc translationRotateScaleMul(float tx, float ty, float tz,
                                                 float qx, float qy, float qz, float qw,
                                                 float sx, float sy, float sz,
                                                 Matrix4x3fc m) {
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
        float nm00 = sx - (q11 + q22) * sx;
        float nm01 = (q01 + q23) * sx;
        float nm02 = (q02 - q13) * sx;
        float nm10 = (q01 - q23) * sy;
        float nm11 = sy - (q22 + q00) * sy;
        float nm12 = (q12 + q03) * sy;
        float nm20 = (q02 + q13) * sz;
        float nm21 = (q12 - q03) * sz;
        float nm22 = sz - (q11 + q00) * sz;
        float m00 = nm00 * m.m00() + nm10 * m.m01() + nm20 * m.m02();
        float m01 = nm01 * m.m00() + nm11 * m.m01() + nm21 * m.m02();
        m02 = nm02 * m.m00() + nm12 * m.m01() + nm22 * m.m02();
        this.m00 = m00;
        this.m01 = m01;
        float m10 = nm00 * m.m10() + nm10 * m.m11() + nm20 * m.m12();
        float m11 = nm01 * m.m10() + nm11 * m.m11() + nm21 * m.m12();
        m12 = nm02 * m.m10() + nm12 * m.m11() + nm22 * m.m12();
        this.m10 = m10;
        this.m11 = m11;
        float m20 = nm00 * m.m20() + nm10 * m.m21() + nm20 * m.m22();
        float m21 = nm01 * m.m20() + nm11 * m.m21() + nm21 * m.m22();
        m22 = nm02 * m.m20() + nm12 * m.m21() + nm22 * m.m22();
        this.m20 = m20;
        this.m21 = m21;
        float m30 = nm00 * m.m30() + nm10 * m.m31() + nm20 * m.m32() + tx;
        float m31 = nm01 * m.m30() + nm11 * m.m31() + nm21 * m.m32() + ty;
        m32 = nm02 * m.m30() + nm12 * m.m31() + nm22 * m.m32() + tz;
        this.m30 = m30;
        this.m31 = m31;
        properties = 0;
        return this;
    }

    public Matrix4x3fc translationRotateScaleMul(IVector3f translation, IQuaternionf quat, IVector3f scale, Matrix4x3fc m) {
        return translationRotateScaleMul(translation.x(), translation.y(), translation.z(), quat.x(), quat.y(), quat.z(), quat.w(), scale.x(), scale.y(), scale.z(), m);
    }

    public Matrix4x3fc translationRotate(float tx, float ty, float tz, IQuaternionf quat) {
        float dqx = quat.x() + quat.x();
        float dqy = quat.y() + quat.y();
        float dqz = quat.z() + quat.z();
        float q00 = dqx * quat.x();
        float q11 = dqy * quat.y();
        float q22 = dqz * quat.z();
        float q01 = dqx * quat.y();
        float q02 = dqx * quat.z();
        float q03 = dqx * quat.w();
        float q12 = dqy * quat.z();
        float q13 = dqy * quat.w();
        float q23 = dqz * quat.w();
        m00 = 1.0f - (q11 + q22);
        m01 = q01 + q23;
        m02 = q02 - q13;
        m10 = q01 - q23;
        m11 = 1.0f - (q22 + q00);
        m12 = q12 + q03;
        m20 = q02 + q13;
        m21 = q12 - q03;
        m22 = 1.0f - (q11 + q00);
        m30 = tx;
        m31 = ty;
        m32 = tz;
        properties = 0;
        return this;
    }

    public Matrix4x3fc translationRotateMul(float tx, float ty, float tz, IQuaternionf quat, IMatrix4x3f mat) {
        return translationRotateMul(tx, ty, tz, quat.x(), quat.y(), quat.z(), quat.w(), mat);
    }

    public Matrix4x3fc translationRotateMul(float tx, float ty, float tz, float qx, float qy, float qz, float qw, IMatrix4x3f mat) {
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
        m00 = nm00 * mat.m00() + nm10 * mat.m01() + nm20 * mat.m02();
        m01 = nm01 * mat.m00() + nm11 * mat.m01() + nm21 * mat.m02();
        m02 = nm02 * mat.m00() + nm12 * mat.m01() + nm22 * mat.m02();
        m10 = nm00 * mat.m10() + nm10 * mat.m11() + nm20 * mat.m12();
        m11 = nm01 * mat.m10() + nm11 * mat.m11() + nm21 * mat.m12();
        m12 = nm02 * mat.m10() + nm12 * mat.m11() + nm22 * mat.m12();
        m20 = nm00 * mat.m20() + nm10 * mat.m21() + nm20 * mat.m22();
        m21 = nm01 * mat.m20() + nm11 * mat.m21() + nm21 * mat.m22();
        m22 = nm02 * mat.m20() + nm12 * mat.m21() + nm22 * mat.m22();
        m30 = nm00 * mat.m30() + nm10 * mat.m31() + nm20 * mat.m32() + tx;
        m31 = nm01 * mat.m30() + nm11 * mat.m31() + nm21 * mat.m32() + ty;
        m32 = nm02 * mat.m30() + nm12 * mat.m31() + nm22 * mat.m32() + tz;
        this.properties = 0;
        return this;
    }

    public Matrix4x3fc set3x3(IMatrix3f mat) {
        if (mat instanceof Matrix3f) {
            MemUtil.INSTANCE.copy3x3((Matrix3f) mat, this);
        } else {
            set3x3Matrix3fc(mat);
        }
        properties &= ~(PROPERTY_IDENTITY | PROPERTY_TRANSLATION);
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

    public Vector4fc transform(Vector4fc v) {
        return v.mul(this);
    }

    public Vector4fc transform(IVector4f v, Vector4fc dest) {
        return v.mul(this, dest);
    }

    public Vector3fc transformPosition(Vector3fc v) {
        v.set(m00 * v.x() + m10 * v.y() + m20 * v.z() + m30,
                m01 * v.x() + m11 * v.y() + m21 * v.z() + m31,
                m02 * v.x() + m12 * v.y() + m22 * v.z() + m32);
        return v;
    }

    public Vector3fc transformPosition(IVector3f v, Vector3fc dest) {
        dest.set(m00 * v.x() + m10 * v.y() + m20 * v.z() + m30,
                m01 * v.x() + m11 * v.y() + m21 * v.z() + m31,
                m02 * v.x() + m12 * v.y() + m22 * v.z() + m32);
        return dest;
    }

    public Vector3fc transformDirection(Vector3fc v) {
        v.set(m00 * v.x() + m10 * v.y() + m20 * v.z(),
                m01 * v.x() + m11 * v.y() + m21 * v.z(),
                m02 * v.x() + m12 * v.y() + m22 * v.z());
        return v;
    }

    public Vector3fc transformDirection(IVector3f v, Vector3fc dest) {
        dest.set(m00 * v.x() + m10 * v.y() + m20 * v.z(),
                m01 * v.x() + m11 * v.y() + m21 * v.z(),
                m02 * v.x() + m12 * v.y() + m22 * v.z());
        return dest;
    }

    public Matrix4x3fc scale(IVector3f xyz, Matrix4x3fc dest) {
        return scale(xyz.x(), xyz.y(), xyz.z(), dest);
    }

    public Matrix4x3fc scale(IVector3f xyz) {
        return scale(xyz.x(), xyz.y(), xyz.z(), this);
    }

    public Matrix4x3fc scale(float xyz, Matrix4x3fc dest) {
        return scale(xyz, xyz, xyz, dest);
    }

    public Matrix4x3fc scale(float xyz) {
        return scale(xyz, xyz, xyz);
    }

    public Matrix4x3fc scale(float x, float y, float z, Matrix4x3fc dest) {
        if ((properties & PROPERTY_IDENTITY) != 0)
            return dest.scaling(x, y, z);
        return scaleGeneric(x, y, z, dest);
    }

    private Matrix4x3fc scaleGeneric(float x, float y, float z, Matrix4x3fc dest) {
        dest.m00(m00 * x);
        dest.m01(m01 * x);
        dest.m02(m02 * x);
        dest.m10(m10 * y);
        dest.m11(m11 * y);
        dest.m12(m12 * y);
        dest.m20(m20 * z);
        dest.m21(m21 * z);
        dest.m22(m22 * z);
        dest.m30(m30);
        dest.m31(m31);
        dest.m32(m32);
        dest.properties((byte) (properties & ~(PROPERTY_IDENTITY | PROPERTY_TRANSLATION)));
        return dest;
    }

    public Matrix4x3fc scale(float x, float y, float z) {
        return scale(x, y, z, this);
    }

    public Matrix4x3fc scaleLocal(float x, float y, float z, Matrix4x3fc dest) {
        if ((properties & PROPERTY_IDENTITY) != 0)
            return dest.scaling(x, y, z);
        float nm00 = x * m00;
        float nm01 = y * m01;
        float nm02 = z * m02;
        float nm10 = x * m10;
        float nm11 = y * m11;
        float nm12 = z * m12;
        float nm20 = x * m20;
        float nm21 = y * m21;
        float nm22 = z * m22;
        float nm30 = x * m30;
        float nm31 = y * m31;
        float nm32 = z * m32;
        dest.m00(nm00);
        dest.m01(nm01);
        dest.m02(nm02);
        dest.m10(nm10);
        dest.m11(nm11);
        dest.m12(nm12);
        dest.m20(nm20);
        dest.m21(nm21);
        dest.m22(nm22);
        dest.m30(nm30);
        dest.m31(nm31);
        dest.m32(nm32);
        dest.properties((byte) (properties & ~(PROPERTY_IDENTITY | PROPERTY_TRANSLATION)));
        return dest;
    }

    public Matrix4x3fc scaleLocal(float x, float y, float z) {
        return scaleLocal(x, y, z, this);
    }

    public Matrix4x3fc rotateX(float ang, Matrix4x3fc dest) {
        if ((properties & PROPERTY_IDENTITY) != 0)
            return dest.rotationX(ang);

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
        float rm12 = sin;
        float rm21 = -sin;
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
        dest.m30(m30);
        dest.m31(m31);
        dest.m32(m32);
        dest.properties((byte) (properties & ~(PROPERTY_IDENTITY | PROPERTY_TRANSLATION)));
        return dest;
    }

    public Matrix4x3fc rotateX(float ang) {
        return rotateX(ang, this);
    }

    public Matrix4x3fc rotateY(float ang, Matrix4x3fc dest) {
        if ((properties & PROPERTY_IDENTITY) != 0)
            return dest.rotationY(ang);

        float cos, sin;
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
        float rm02 = -sin;
        float rm20 = sin;
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
        dest.m30(m30);
        dest.m31(m31);
        dest.m32(m32);
        dest.properties((byte) (properties & ~(PROPERTY_IDENTITY | PROPERTY_TRANSLATION)));
        return dest;
    }

    public Matrix4x3fc rotateY(float ang) {
        return rotateY(ang, this);
    }

    public Matrix4x3fc rotateZ(float ang, Matrix4x3fc dest) {
        if ((properties & PROPERTY_IDENTITY) != 0)
            return dest.rotationZ(ang);

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
        float rm01 = sin;
        float rm10 = -sin;
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
        dest.m30(m30);
        dest.m31(m31);
        dest.m32(m32);
        dest.properties((byte) (properties & ~(PROPERTY_IDENTITY | PROPERTY_TRANSLATION)));
        return dest;
    }

    public Matrix4x3fc rotateZ(float ang) {
        return rotateZ(ang, this);
    }

    public Matrix4x3fc rotateXYZ(Vector3fc angles) {
        return rotateXYZ(angles.x(), angles.y(), angles.z());
    }

    public Matrix4x3fc rotateXYZ(float angleX, float angleY, float angleZ) {
        return rotateXYZ(angleX, angleY, angleZ, this);
    }

    public Matrix4x3fc rotateXYZ(float angleX, float angleY, float angleZ, Matrix4x3fc dest) {
        if ((properties & PROPERTY_IDENTITY) != 0)
            return dest.rotationXYZ(angleX, angleY, angleZ);

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
        // copy last column from 'this'
        dest.m30(m30);
        dest.m31(m31);
        dest.m32(m32);
        dest.properties((byte) (properties & ~(PROPERTY_IDENTITY | PROPERTY_TRANSLATION)));
        return dest;
    }

    public Matrix4x3fc rotateZYX(Vector3fc angles) {
        return rotateZYX(angles.z(), angles.y(), angles.x());
    }

    public Matrix4x3fc rotateZYX(float angleZ, float angleY, float angleX) {
        return rotateZYX(angleZ, angleY, angleX, this);
    }

    public Matrix4x3fc rotateZYX(float angleZ, float angleY, float angleX, Matrix4x3fc dest) {
        if ((properties & PROPERTY_IDENTITY) != 0)
            return dest.rotationZYX(angleZ, angleY, angleX);

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
        // copy last column from 'this'
        dest.m30(m30);
        dest.m31(m31);
        dest.m32(m32);
        dest.properties((byte) (properties & ~(PROPERTY_IDENTITY | PROPERTY_TRANSLATION)));
        return dest;
    }

    public Matrix4x3fc rotateYXZ(Vector3fc angles) {
        return rotateYXZ(angles.y(), angles.x(), angles.z());
    }

    public Matrix4x3fc rotateYXZ(float angleY, float angleX, float angleZ) {
        return rotateYXZ(angleY, angleX, angleZ, this);
    }

    public Matrix4x3fc rotateYXZ(float angleY, float angleX, float angleZ, Matrix4x3fc dest) {
        if ((properties & PROPERTY_IDENTITY) != 0)
            return dest.rotationYXZ(angleY, angleX, angleZ);

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
        // copy last column from 'this'
        dest.m30(m30);
        dest.m31(m31);
        dest.m32(m32);
        dest.properties((byte) (properties & ~(PROPERTY_IDENTITY | PROPERTY_TRANSLATION)));
        return dest;
    }

    public Matrix4x3fc rotate(float ang, float x, float y, float z, Matrix4x3fc dest) {
        if ((properties & PROPERTY_IDENTITY) != 0)
            return dest.rotation(ang, x, y, z);
        else if ((properties & PROPERTY_TRANSLATION) != 0)
            return rotateTranslation(ang, x, y, z, dest);
        return rotateGeneric(ang, x, y, z, dest);
    }

    private Matrix4x3fc rotateGeneric(float ang, float x, float y, float z, Matrix4x3fc dest) {
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
        dest.m30(m30);
        dest.m31(m31);
        dest.m32(m32);
        dest.properties((byte) (properties & ~(PROPERTY_IDENTITY | PROPERTY_TRANSLATION)));
        return dest;
    }

    public Matrix4x3fc rotate(float ang, float x, float y, float z) {
        return rotate(ang, x, y, z, this);
    }

    public Matrix4x3fc rotateTranslation(float ang, float x, float y, float z, Matrix4x3fc dest) {
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
        dest.m10(nm10);
        dest.m11(nm11);
        dest.m12(nm12);
        dest.m30(m30);
        dest.m31(m31);
        dest.m32(m32);
        dest.properties((byte) (properties & ~(PROPERTY_IDENTITY | PROPERTY_TRANSLATION)));

        return dest;
    }

    public Matrix4x3fc rotateLocal(float ang, float x, float y, float z, Matrix4x3fc dest) {
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
        float nm30 = lm00 * m30 + lm10 * m31 + lm20 * m32;
        float nm31 = lm01 * m30 + lm11 * m31 + lm21 * m32;
        float nm32 = lm02 * m30 + lm12 * m31 + lm22 * m32;
        dest.m00(nm00);
        dest.m01(nm01);
        dest.m02(nm02);
        dest.m10(nm10);
        dest.m11(nm11);
        dest.m12(nm12);
        dest.m20(nm20);
        dest.m21(nm21);
        dest.m22(nm22);
        dest.m30(nm30);
        dest.m31(nm31);
        dest.m32(nm32);
        dest.properties((byte) (properties & ~(PROPERTY_IDENTITY | PROPERTY_TRANSLATION)));
        return dest;
    }

    public Matrix4x3fc rotateLocal(float ang, float x, float y, float z) {
        return rotateLocal(ang, x, y, z, this);
    }

    public Matrix4x3fc translate(IVector3f offset) {
        return translate(offset.x(), offset.y(), offset.z());
    }

    public Matrix4x3fc translate(IVector3f offset, Matrix4x3fc dest) {
        return translate(offset.x(), offset.y(), offset.z(), dest);
    }

    public Matrix4x3fc translate(float x, float y, float z, Matrix4x3fc dest) {
        if ((properties & PROPERTY_IDENTITY) != 0)
            return dest.translation(x, y, z);
        return translateGeneric(x, y, z, dest);
    }

    private Matrix4x3fc translateGeneric(float x, float y, float z, Matrix4x3fc dest) {
        dest.m00(this.m00);
        dest.m01(this.m01);
        dest.m02(this.m02);
        dest.m10(this.m10);
        dest.m11(this.m11);
        dest.m12(this.m12);
        dest.m20(this.m20);
        dest.m21(this.m21);
        dest.m22(this.m22);
        dest.m30(this.m30);
        dest.m31(this.m31);
        dest.m32(this.m32);
        dest.m30(m00 * x + m10 * y + m20 * z + m30);
        dest.m31(m01 * x + m11 * y + m21 * z + m31);
        dest.m32(m02 * x + m12 * y + m22 * z + m32);
        dest.properties((byte) (properties & ~(PROPERTY_IDENTITY)));
        return dest;
    }

    public Matrix4x3fc translate(float x, float y, float z) {
        if ((properties & PROPERTY_IDENTITY) != 0)
            return translation(x, y, z);
        Matrix4x3fc c = this;
        c.m30(c.m00() * x + c.m10() * y + c.m20() * z + c.m30());
        c.m31(c.m01() * x + c.m11() * y + c.m21() * z + c.m31());
        c.m32(c.m02() * x + c.m12() * y + c.m22() * z + c.m32());
        c.properties(c.properties() & ~(PROPERTY_IDENTITY));
        return this;
    }

    public Matrix4x3fc translateLocal(IVector3f offset) {
        return translateLocal(offset.x(), offset.y(), offset.z());
    }

    public Matrix4x3fc translateLocal(IVector3f offset, Matrix4x3fc dest) {
        return translateLocal(offset.x(), offset.y(), offset.z(), dest);
    }

    public Matrix4x3fc translateLocal(float x, float y, float z, Matrix4x3fc dest) {
        dest.m00(m00);
        dest.m01(m01);
        dest.m02(m02);
        dest.m10(m10);
        dest.m11(m11);
        dest.m12(m12);
        dest.m20(m20);
        dest.m21(m21);
        dest.m22(m22);
        dest.m30(m30 + x);
        dest.m31(m31 + y);
        dest.m32(m32 + z);
        dest.properties((byte) (properties & ~(PROPERTY_IDENTITY)));
        return dest;
    }

    public Matrix4x3fc translateLocal(float x, float y, float z) {
        return translateLocal(x, y, z, this);
    }

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
        out.writeFloat(m30);
        out.writeFloat(m31);
        out.writeFloat(m32);
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        m00 = in.readFloat();
        m01 = in.readFloat();
        m02 = in.readFloat();
        m10 = in.readFloat();
        m11 = in.readFloat();
        m12 = in.readFloat();
        m20 = in.readFloat();
        m21 = in.readFloat();
        m22 = in.readFloat();
        m30 = in.readFloat();
        m31 = in.readFloat();
        m32 = in.readFloat();
        properties = 0;
    }

    public Matrix4x3fc ortho(float left, float right, float bottom, float top, float zNear, float zFar, boolean zZeroToOne, Matrix4x3fc dest) {
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
        dest.m00(m00 * rm00);
        dest.m01(m01 * rm00);
        dest.m02(m02 * rm00);
        dest.m10(m10 * rm11);
        dest.m11(m11 * rm11);
        dest.m12(m12 * rm11);
        dest.m20(m20 * rm22);
        dest.m21(m21 * rm22);
        dest.m22(m22 * rm22);
        dest.properties((byte) (properties & ~(PROPERTY_IDENTITY | PROPERTY_TRANSLATION)));

        return dest;
    }

    public Matrix4x3fc ortho(float left, float right, float bottom, float top, float zNear, float zFar, Matrix4x3fc dest) {
        return ortho(left, right, bottom, top, zNear, zFar, false, dest);
    }

    public Matrix4x3fc ortho(float left, float right, float bottom, float top, float zNear, float zFar, boolean zZeroToOne) {
        return ortho(left, right, bottom, top, zNear, zFar, zZeroToOne, this);
    }

    public Matrix4x3fc ortho(float left, float right, float bottom, float top, float zNear, float zFar) {
        return ortho(left, right, bottom, top, zNear, zFar, false);
    }

    public Matrix4x3fc orthoLH(float left, float right, float bottom, float top, float zNear, float zFar, boolean zZeroToOne, Matrix4x3fc dest) {
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
        dest.m00(m00 * rm00);
        dest.m01(m01 * rm00);
        dest.m02(m02 * rm00);
        dest.m10(m10 * rm11);
        dest.m11(m11 * rm11);
        dest.m12(m12 * rm11);
        dest.m20(m20 * rm22);
        dest.m21(m21 * rm22);
        dest.m22(m22 * rm22);
        dest.properties((byte) (properties & ~(PROPERTY_IDENTITY | PROPERTY_TRANSLATION)));

        return dest;
    }

    public Matrix4x3fc orthoLH(float left, float right, float bottom, float top, float zNear, float zFar, Matrix4x3fc dest) {
        return orthoLH(left, right, bottom, top, zNear, zFar, false, dest);
    }

    public Matrix4x3fc orthoLH(float left, float right, float bottom, float top, float zNear, float zFar, boolean zZeroToOne) {
        return orthoLH(left, right, bottom, top, zNear, zFar, zZeroToOne, this);
    }

    public Matrix4x3fc orthoLH(float left, float right, float bottom, float top, float zNear, float zFar) {
        return orthoLH(left, right, bottom, top, zNear, zFar, false);
    }

    public Matrix4x3fc setOrtho(float left, float right, float bottom, float top, float zNear, float zFar, boolean zZeroToOne) {
        MemUtil.INSTANCE.identity(this);
        m00 = 2.0f / (right - left);
        m11 = 2.0f / (top - bottom);
        m22 = (zZeroToOne ? 1.0f : 2.0f) / (zNear - zFar);
        m30 = (right + left) / (left - right);
        m31 = (top + bottom) / (bottom - top);
        m32 = (zZeroToOne ? zNear : (zFar + zNear)) / (zNear - zFar);
        properties = 0;
        return this;
    }

    public Matrix4x3fc setOrtho(float left, float right, float bottom, float top, float zNear, float zFar) {
        return setOrtho(left, right, bottom, top, zNear, zFar, false);
    }

    public Matrix4x3fc setOrthoLH(float left, float right, float bottom, float top, float zNear, float zFar, boolean zZeroToOne) {
        MemUtil.INSTANCE.identity(this);
        m00 = 2.0f / (right - left);
        m11 = 2.0f / (top - bottom);
        m22 = (zZeroToOne ? 1.0f : 2.0f) / (zFar - zNear);
        m30 = (right + left) / (left - right);
        m31 = (top + bottom) / (bottom - top);
        m32 = (zZeroToOne ? zNear : (zFar + zNear)) / (zNear - zFar);
        properties = 0;
        return this;
    }

    public Matrix4x3fc setOrthoLH(float left, float right, float bottom, float top, float zNear, float zFar) {
        return setOrthoLH(left, right, bottom, top, zNear, zFar, false);
    }

    public Matrix4x3fc orthoSymmetric(float width, float height, float zNear, float zFar, boolean zZeroToOne, Matrix4x3fc dest) {
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
        dest.m00(m00 * rm00);
        dest.m01(m01 * rm00);
        dest.m02(m02 * rm00);
        dest.m10(m10 * rm11);
        dest.m11(m11 * rm11);
        dest.m12(m12 * rm11);
        dest.m20(m20 * rm22);
        dest.m21(m21 * rm22);
        dest.m22(m22 * rm22);
        dest.properties((byte) (properties & ~(PROPERTY_IDENTITY | PROPERTY_TRANSLATION)));

        return dest;
    }

    public Matrix4x3fc orthoSymmetric(float width, float height, float zNear, float zFar, Matrix4x3fc dest) {
        return orthoSymmetric(width, height, zNear, zFar, false, dest);
    }

    public Matrix4x3fc orthoSymmetric(float width, float height, float zNear, float zFar, boolean zZeroToOne) {
        return orthoSymmetric(width, height, zNear, zFar, zZeroToOne, this);
    }

    public Matrix4x3fc orthoSymmetric(float width, float height, float zNear, float zFar) {
        return orthoSymmetric(width, height, zNear, zFar, false, this);
    }

    public Matrix4x3fc orthoSymmetricLH(float width, float height, float zNear, float zFar, boolean zZeroToOne, Matrix4x3fc dest) {
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
        dest.m00(m00 * rm00);
        dest.m01(m01 * rm00);
        dest.m02(m02 * rm00);
        dest.m10(m10 * rm11);
        dest.m11(m11 * rm11);
        dest.m12(m12 * rm11);
        dest.m20(m20 * rm22);
        dest.m21(m21 * rm22);
        dest.m22(m22 * rm22);
        dest.properties((byte) (properties & ~(PROPERTY_IDENTITY | PROPERTY_TRANSLATION)));

        return dest;
    }

    public Matrix4x3fc orthoSymmetricLH(float width, float height, float zNear, float zFar, Matrix4x3fc dest) {
        return orthoSymmetricLH(width, height, zNear, zFar, false, dest);
    }

    public Matrix4x3fc orthoSymmetricLH(float width, float height, float zNear, float zFar, boolean zZeroToOne) {
        return orthoSymmetricLH(width, height, zNear, zFar, zZeroToOne, this);
    }

    public Matrix4x3fc orthoSymmetricLH(float width, float height, float zNear, float zFar) {
        return orthoSymmetricLH(width, height, zNear, zFar, false, this);
    }

    public Matrix4x3fc setOrthoSymmetric(float width, float height, float zNear, float zFar, boolean zZeroToOne) {
        MemUtil.INSTANCE.identity(this);
        m00 = 2.0f / width;
        m11 = 2.0f / height;
        m22 = (zZeroToOne ? 1.0f : 2.0f) / (zNear - zFar);
        m32 = (zZeroToOne ? zNear : (zFar + zNear)) / (zNear - zFar);
        properties = 0;
        return this;
    }

    public Matrix4x3fc setOrthoSymmetric(float width, float height, float zNear, float zFar) {
        return setOrthoSymmetric(width, height, zNear, zFar, false);
    }

    public Matrix4x3fc setOrthoSymmetricLH(float width, float height, float zNear, float zFar, boolean zZeroToOne) {
        MemUtil.INSTANCE.identity(this);
        m00 = 2.0f / width;
        m11 = 2.0f / height;
        m22 = (zZeroToOne ? 1.0f : 2.0f) / (zFar - zNear);
        m32 = (zZeroToOne ? zNear : (zFar + zNear)) / (zNear - zFar);
        properties = 0;
        return this;
    }

    public Matrix4x3fc setOrthoSymmetricLH(float width, float height, float zNear, float zFar) {
        return setOrthoSymmetricLH(width, height, zNear, zFar, false);
    }

    public Matrix4x3fc ortho2D(float left, float right, float bottom, float top, Matrix4x3fc dest) {
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
        dest.m00(m00 * rm00);
        dest.m01(m01 * rm00);
        dest.m02(m02 * rm00);
        dest.m10(m10 * rm11);
        dest.m11(m11 * rm11);
        dest.m12(m12 * rm11);
        dest.m20(-m20);
        dest.m21(-m21);
        dest.m22(-m22);
        dest.properties((byte) (properties & ~(PROPERTY_IDENTITY | PROPERTY_TRANSLATION)));

        return dest;
    }

    public Matrix4x3fc ortho2D(float left, float right, float bottom, float top) {
        return ortho2D(left, right, bottom, top, this);
    }

    public Matrix4x3fc ortho2DLH(float left, float right, float bottom, float top, Matrix4x3fc dest) {
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
        dest.m00(m00 * rm00);
        dest.m01(m01 * rm00);
        dest.m02(m02 * rm00);
        dest.m10(m10 * rm11);
        dest.m11(m11 * rm11);
        dest.m12(m12 * rm11);
        dest.m20(m20);
        dest.m21(m21);
        dest.m22(m22);
        dest.properties((byte) (properties & ~(PROPERTY_IDENTITY | PROPERTY_TRANSLATION)));

        return dest;
    }

    public Matrix4x3fc ortho2DLH(float left, float right, float bottom, float top) {
        return ortho2DLH(left, right, bottom, top, this);
    }

    public Matrix4x3fc setOrtho2D(float left, float right, float bottom, float top) {
        MemUtil.INSTANCE.identity(this);
        m00 = 2.0f / (right - left);
        m11 = 2.0f / (top - bottom);
        m22 = -1.0f;
        m30 = -(right + left) / (right - left);
        m31 = -(top + bottom) / (top - bottom);
        properties = 0;
        return this;
    }

    public Matrix4x3fc setOrtho2DLH(float left, float right, float bottom, float top) {
        MemUtil.INSTANCE.identity(this);
        m00 = 2.0f / (right - left);
        m11 = 2.0f / (top - bottom);
        m22 = 1.0f;
        m30 = -(right + left) / (right - left);
        m31 = -(top + bottom) / (top - bottom);
        properties = 0;
        return this;
    }

    public Matrix4x3fc lookAlong(IVector3f dir, IVector3f up) {
        return lookAlong(dir.x(), dir.y(), dir.z(), up.x(), up.y(), up.z(), this);
    }

    public Matrix4x3fc lookAlong(IVector3f dir, IVector3f up, Matrix4x3fc dest) {
        return lookAlong(dir.x(), dir.y(), dir.z(), up.x(), up.y(), up.z(), dest);
    }

    public Matrix4x3fc lookAlong(float dirX, float dirY, float dirZ, float upX, float upY, float upZ, Matrix4x3fc dest) {
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
        dest.m30(m30);
        dest.m31(m31);
        dest.m32(m32);
        dest.properties((byte) (properties & ~(PROPERTY_IDENTITY | PROPERTY_TRANSLATION)));

        return dest;
    }

    public Matrix4x3fc lookAlong(float dirX, float dirY, float dirZ, float upX, float upY, float upZ) {
        return lookAlong(dirX, dirY, dirZ, upX, upY, upZ, this);
    }

    public Matrix4x3fc setLookAlong(IVector3f dir, IVector3f up) {
        return setLookAlong(dir.x(), dir.y(), dir.z(), up.x(), up.y(), up.z());
    }

    public Matrix4x3fc setLookAlong(float dirX, float dirY, float dirZ, float upX, float upY, float upZ) {
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

        m00 = rightX;
        m01 = upnX;
        m02 = -dirnX;
        m10 = rightY;
        m11 = upnY;
        m12 = -dirnY;
        m20 = rightZ;
        m21 = upnZ;
        m22 = -dirnZ;
        m30 = 0.0f;
        m31 = 0.0f;
        m32 = 0.0f;
        properties = 0;

        return this;
    }

    public Matrix4x3fc setLookAt(IVector3f eye, IVector3f center, IVector3f up) {
        return setLookAt(eye.x(), eye.y(), eye.z(), center.x(), center.y(), center.z(), up.x(), up.y(), up.z());
    }

    public Matrix4x3fc setLookAt(float eyeX, float eyeY, float eyeZ,
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

        m00 = leftX;
        m01 = upnX;
        m02 = dirX;
        m10 = leftY;
        m11 = upnY;
        m12 = dirY;
        m20 = leftZ;
        m21 = upnZ;
        m22 = dirZ;
        m30 = -(leftX * eyeX + leftY * eyeY + leftZ * eyeZ);
        m31 = -(upnX * eyeX + upnY * eyeY + upnZ * eyeZ);
        m32 = -(dirX * eyeX + dirY * eyeY + dirZ * eyeZ);
        properties = 0;

        return this;
    }

    public Matrix4x3fc lookAt(IVector3f eye, IVector3f center, IVector3f up, Matrix4x3fc dest) {
        return lookAt(eye.x(), eye.y(), eye.z(), center.x(), center.y(), center.z(), up.x(), up.y(), up.z(), dest);
    }

    public Matrix4x3fc lookAt(IVector3f eye, IVector3f center, IVector3f up) {
        return lookAt(eye.x(), eye.y(), eye.z(), center.x(), center.y(), center.z(), up.x(), up.y(), up.z(), this);
    }

    public Matrix4x3fc lookAt(float eyeX, float eyeY, float eyeZ,
                              float centerX, float centerY, float centerZ,
                              float upX, float upY, float upZ, Matrix4x3fc dest) {
        if ((properties & PROPERTY_IDENTITY) != 0)
            return dest.setLookAt(eyeX, eyeY, eyeZ, centerX, centerY, centerZ, upX, upY, upZ);
        return lookAtGeneric(eyeX, eyeY, eyeZ, centerX, centerY, centerZ, upX, upY, upZ, dest);
    }

    private Matrix4x3fc lookAtGeneric(float eyeX, float eyeY, float eyeZ,
                                      float centerX, float centerY, float centerZ,
                                      float upX, float upY, float upZ, Matrix4x3fc dest) {
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
        dest.properties((byte) (properties & ~(PROPERTY_IDENTITY | PROPERTY_TRANSLATION)));
        return dest;
    }

    public Matrix4x3fc lookAt(float eyeX, float eyeY, float eyeZ,
                              float centerX, float centerY, float centerZ,
                              float upX, float upY, float upZ) {
        return lookAt(eyeX, eyeY, eyeZ, centerX, centerY, centerZ, upX, upY, upZ, this);
    }

    public Matrix4x3fc setLookAtLH(IVector3f eye, IVector3f center, IVector3f up) {
        return setLookAtLH(eye.x(), eye.y(), eye.z(), center.x(), center.y(), center.z(), up.x(), up.y(), up.z());
    }

    public Matrix4x3fc setLookAtLH(float eyeX, float eyeY, float eyeZ,
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

        m00 = leftX;
        m01 = upnX;
        m02 = dirX;
        m10 = leftY;
        m11 = upnY;
        m12 = dirY;
        m20 = leftZ;
        m21 = upnZ;
        m22 = dirZ;
        m30 = -(leftX * eyeX + leftY * eyeY + leftZ * eyeZ);
        m31 = -(upnX * eyeX + upnY * eyeY + upnZ * eyeZ);
        m32 = -(dirX * eyeX + dirY * eyeY + dirZ * eyeZ);
        properties = 0;

        return this;
    }

    public Matrix4x3fc lookAtLH(IVector3f eye, IVector3f center, IVector3f up, Matrix4x3fc dest) {
        return lookAtLH(eye.x(), eye.y(), eye.z(), center.x(), center.y(), center.z(), up.x(), up.y(), up.z(), dest);
    }

    public Matrix4x3fc lookAtLH(IVector3f eye, IVector3f center, IVector3f up) {
        return lookAtLH(eye.x(), eye.y(), eye.z(), center.x(), center.y(), center.z(), up.x(), up.y(), up.z(), this);
    }

    public Matrix4x3fc lookAtLH(float eyeX, float eyeY, float eyeZ,
                                float centerX, float centerY, float centerZ,
                                float upX, float upY, float upZ, Matrix4x3fc dest) {
        if ((properties & PROPERTY_IDENTITY) != 0)
            return dest.setLookAtLH(eyeX, eyeY, eyeZ, centerX, centerY, centerZ, upX, upY, upZ);
        return lookAtLHGeneric(eyeX, eyeY, eyeZ, centerX, centerY, centerZ, upX, upY, upZ, dest);
    }

    private Matrix4x3fc lookAtLHGeneric(float eyeX, float eyeY, float eyeZ,
                                        float centerX, float centerY, float centerZ,
                                        float upX, float upY, float upZ, Matrix4x3fc dest) {
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
        dest.properties((byte) (properties & ~(PROPERTY_IDENTITY | PROPERTY_TRANSLATION)));

        return dest;
    }

    public Matrix4x3fc lookAtLH(float eyeX, float eyeY, float eyeZ,
                                float centerX, float centerY, float centerZ,
                                float upX, float upY, float upZ) {
        return lookAtLH(eyeX, eyeY, eyeZ, centerX, centerY, centerZ, upX, upY, upZ, this);
    }

    public Matrix4x3fc rotate(IQuaternionf quat, Matrix4x3fc dest) {
        if ((properties & PROPERTY_IDENTITY) != 0)
            return dest.rotation(quat);
        else if ((properties & PROPERTY_TRANSLATION) != 0)
            return rotateTranslation(quat, dest);
        return rotateGeneric(quat, dest);
    }

    private Matrix4x3fc rotateGeneric(IQuaternionf quat, Matrix4x3fc dest) {
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
        dest.m30(m30);
        dest.m31(m31);
        dest.m32(m32);
        dest.properties((byte) (properties & ~(PROPERTY_IDENTITY | PROPERTY_TRANSLATION)));
        return dest;
    }

    public Matrix4x3fc rotate(IQuaternionf quat) {
        return rotate(quat, this);
    }

    public Matrix4x3fc rotateTranslation(IQuaternionf quat, Matrix4x3fc dest) {
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
        dest.m00(nm00);
        dest.m01(nm01);
        dest.m02(nm02);
        dest.m10(nm10);
        dest.m11(nm11);
        dest.m12(nm12);
        dest.m30(m30);
        dest.m31(m31);
        dest.m32(m32);
        dest.properties((byte) (properties & ~(PROPERTY_IDENTITY | PROPERTY_TRANSLATION)));
        return dest;
    }

    public Matrix4x3fc rotateLocal(IQuaternionf quat, Matrix4x3fc dest) {
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
        float nm30 = lm00 * m30 + lm10 * m31 + lm20 * m32;
        float nm31 = lm01 * m30 + lm11 * m31 + lm21 * m32;
        float nm32 = lm02 * m30 + lm12 * m31 + lm22 * m32;
        dest.m00(nm00);
        dest.m01(nm01);
        dest.m02(nm02);
        dest.m10(nm10);
        dest.m11(nm11);
        dest.m12(nm12);
        dest.m20(nm20);
        dest.m21(nm21);
        dest.m22(nm22);
        dest.m30(nm30);
        dest.m31(nm31);
        dest.m32(nm32);
        dest.properties((byte) (properties & ~(PROPERTY_IDENTITY | PROPERTY_TRANSLATION)));
        return dest;
    }

    public Matrix4x3fc rotateLocal(IQuaternionf quat) {
        return rotateLocal(quat, this);
    }

    public Matrix4x3fc rotate(AxisAngle4fc axisAngle) {
        return rotate(axisAngle.angle(), axisAngle.x(), axisAngle.y(), axisAngle.z());
    }

    public Matrix4x3fc rotate(AxisAngle4fc axisAngle, Matrix4x3fc dest) {
        return rotate(axisAngle.angle(), axisAngle.x(), axisAngle.y(), axisAngle.z(), dest);
    }

    public Matrix4x3fc rotate(float angle, IVector3f axis) {
        return rotate(angle, axis.x(), axis.y(), axis.z());
    }

    public Matrix4x3fc rotate(float angle, IVector3f axis, Matrix4x3fc dest) {
        return rotate(angle, axis.x(), axis.y(), axis.z(), dest);
    }

    public Matrix4x3fc reflect(float a, float b, float c, float d, Matrix4x3fc dest) {
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
        dest.properties((byte) (properties & ~(PROPERTY_IDENTITY | PROPERTY_TRANSLATION)));

        return dest;
    }

    public Matrix4x3fc reflect(float a, float b, float c, float d) {
        return reflect(a, b, c, d, this);
    }

    public Matrix4x3fc reflect(float nx, float ny, float nz, float px, float py, float pz) {
        return reflect(nx, ny, nz, px, py, pz, this);
    }

    public Matrix4x3fc reflect(float nx, float ny, float nz, float px, float py, float pz, Matrix4x3fc dest) {
        float invLength = 1.0f / (float) Math.sqrt(nx * nx + ny * ny + nz * nz);
        float nnx = nx * invLength;
        float nny = ny * invLength;
        float nnz = nz * invLength;
        /* See: http://mathworld.wolfram.com/Plane.html */
        return reflect(nnx, nny, nnz, -nnx * px - nny * py - nnz * pz, dest);
    }

    public Matrix4x3fc reflect(IVector3f normal, IVector3f point) {
        return reflect(normal.x(), normal.y(), normal.z(), point.x(), point.y(), point.z());
    }

    public Matrix4x3fc reflect(IQuaternionf orientation, IVector3f point) {
        return reflect(orientation, point, this);
    }

    public Matrix4x3fc reflect(IQuaternionf orientation, IVector3f point, Matrix4x3fc dest) {
        double num1 = orientation.x() + orientation.x();
        double num2 = orientation.y() + orientation.y();
        double num3 = orientation.z() + orientation.z();
        float normalX = (float) (orientation.x() * num3 + orientation.w() * num2);
        float normalY = (float) (orientation.y() * num3 - orientation.w() * num1);
        float normalZ = (float) (1.0 - (orientation.x() * num1 + orientation.y() * num2));
        return reflect(normalX, normalY, normalZ, point.x(), point.y(), point.z(), dest);
    }

    public Matrix4x3fc reflect(IVector3f normal, IVector3f point, Matrix4x3fc dest) {
        return reflect(normal.x(), normal.y(), normal.z(), point.x(), point.y(), point.z(), dest);
    }

    public Matrix4x3fc reflection(float a, float b, float c, float d) {
        float da = a + a, db = b + b, dc = c + c, dd = d + d;
        m00 = 1.0f - da * a;
        m01 = -da * b;
        m02 = -da * c;
        m10 = -db * a;
        m11 = 1.0f - db * b;
        m12 = -db * c;
        m20 = -dc * a;
        m21 = -dc * b;
        m22 = 1.0f - dc * c;
        m30 = -dd * a;
        m31 = -dd * b;
        m32 = -dd * c;
        properties = 0;
        return this;
    }

    public Matrix4x3fc reflection(float nx, float ny, float nz, float px, float py, float pz) {
        float invLength = 1.0f / (float) Math.sqrt(nx * nx + ny * ny + nz * nz);
        float nnx = nx * invLength;
        float nny = ny * invLength;
        float nnz = nz * invLength;
        /* See: http://mathworld.wolfram.com/Plane.html */
        return reflection(nnx, nny, nnz, -nnx * px - nny * py - nnz * pz);
    }

    public Matrix4x3fc reflection(IVector3f normal, IVector3f point) {
        return reflection(normal.x(), normal.y(), normal.z(), point.x(), point.y(), point.z());
    }

    public Matrix4x3fc reflection(IQuaternionf orientation, IVector3f point) {
        double num1 = orientation.x() + orientation.x();
        double num2 = orientation.y() + orientation.y();
        double num3 = orientation.z() + orientation.z();
        float normalX = (float) (orientation.x() * num3 + orientation.w() * num2);
        float normalY = (float) (orientation.y() * num3 - orientation.w() * num1);
        float normalZ = (float) (1.0 - (orientation.x() * num1 + orientation.y() * num2));
        return reflection(normalX, normalY, normalZ, point.x(), point.y(), point.z());
    }

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
            default:
                throw new IndexOutOfBoundsException();
        }
        return dest;
    }

    public Matrix4x3fc setRow(int row, IVector4f src) throws IndexOutOfBoundsException {
        switch (row) {
            case 0:
                this.m00 = src.x();
                this.m10 = src.y();
                this.m20 = src.z();
                this.m30 = src.w();
                break;
            case 1:
                this.m01 = src.x();
                this.m11 = src.y();
                this.m21 = src.z();
                this.m31 = src.w();
                break;
            case 2:
                this.m02 = src.x();
                this.m12 = src.y();
                this.m22 = src.z();
                this.m32 = src.w();
                break;
            default:
                throw new IndexOutOfBoundsException();
        }
        return this;
    }

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
            case 3:
                dest.set(m30, m31, m32);
                break;
            default:
                throw new IndexOutOfBoundsException();
        }
        return dest;
    }

    public Matrix4x3fc setColumn(int column, IVector3f src) throws IndexOutOfBoundsException {
        switch (column) {
            case 0:
                this.m00 = src.x();
                this.m01 = src.y();
                this.m02 = src.z();
                break;
            case 1:
                this.m10 = src.x();
                this.m11 = src.y();
                this.m12 = src.z();
                break;
            case 2:
                this.m20 = src.x();
                this.m21 = src.y();
                this.m22 = src.z();
                break;
            case 3:
                this.m30 = src.x();
                this.m31 = src.y();
                this.m32 = src.z();
                break;
            default:
                throw new IndexOutOfBoundsException();
        }
        return this;
    }

    public Matrix4x3fc normal() {
        return normal(this);
    }

    public Matrix4x3fc normal(Matrix4x3fc dest) {
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
        dest.m30(0.0f);
        dest.m31(0.0f);
        dest.m32(0.0f);
        dest.properties(0);
        return dest;
    }

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

    public Matrix4x3fc normalize3x3() {
        return normalize3x3(this);
    }

    public Matrix4x3fc normalize3x3(Matrix4x3fc dest) {
        float invXlen = (float) (1.0 / Math.sqrt(m00 * m00 + m01 * m01 + m02 * m02));
        float invYlen = (float) (1.0 / Math.sqrt(m10 * m10 + m11 * m11 + m12 * m12));
        float invZlen = (float) (1.0 / Math.sqrt(m20 * m20 + m21 * m21 + m22 * m22));
        dest.m00(m00 * invXlen);
        dest.m01(m01 * invXlen);
        dest.m02(m02 * invXlen);
        dest.m10(m10 * invYlen);
        dest.m11(m11 * invYlen);
        dest.m12(m12 * invYlen);
        dest.m20(m20 * invZlen);
        dest.m21(m21 * invZlen);
        dest.m22(m22 * invZlen);
        dest.properties(properties);
        return dest;
    }

    public Matrix3fc normalize3x3(Matrix3fc dest) {
        float invXlen = (float) (1.0 / Math.sqrt(m00 * m00 + m01 * m01 + m02 * m02));
        float invYlen = (float) (1.0 / Math.sqrt(m10 * m10 + m11 * m11 + m12 * m12));
        float invZlen = (float) (1.0 / Math.sqrt(m20 * m20 + m21 * m21 + m22 * m22));
        dest.m00(m00 * invXlen);
        dest.m01(m01 * invXlen);
        dest.m02(m02 * invXlen);
        dest.m10(m10 * invYlen);
        dest.m11(m11 * invYlen);
        dest.m12(m12 * invYlen);
        dest.m20(m20 * invZlen);
        dest.m21(m21 * invZlen);
        dest.m22(m22 * invZlen);
        return dest;
    }

    public Planefc frustumPlane(int which, Planefc plane) {
        switch (which) {
            case PLANE_NX:
                plane.set(m00, m10, m20, 1.0f + m30).normalize();
                break;
            case PLANE_PX:
                plane.set(-m00, -m10, -m20, 1.0f - m30).normalize();
                break;
            case PLANE_NY:
                plane.set(m01, m11, m21, 1.0f + m31).normalize();
                break;
            case PLANE_PY:
                plane.set(-m01, -m11, -m21, 1.0f - m31).normalize();
                break;
            case PLANE_NZ:
                plane.set(m02, m12, m22, 1.0f + m32).normalize();
                break;
            case PLANE_PZ:
                plane.set(-m02, -m12, -m22, 1.0f - m32).normalize();
                break;
            default:
                throw new IllegalArgumentException("which"); //$NON-NLS-1$
        }
        return plane;
    }

    public Vector3fc positiveZ(Vector3fc dir) {
        dir.set(m10 * m21 - m11 * m20, m20 * m01 - m21 * m00, m00 * m11 - m01 * m10);
        dir.normalize();
        return dir;
    }

    public Vector3fc normalizedPositiveZ(Vector3fc dir) {
        dir.set(m02, m12, m22);
        return dir;
    }

    public Vector3fc positiveX(Vector3fc dir) {
        dir.set(m11 * m22 - m12 * m21, m02 * m21 - m01 * m22, m01 * m12 - m02 * m11);
        dir.normalize();
        return dir;
    }

    public Vector3fc normalizedPositiveX(Vector3fc dir) {
        dir.set(m00, m10, m20);
        return dir;
    }

    public Vector3fc positiveY(Vector3fc dir) {
        dir.set(m12 * m20 - m10 * m22, m00 * m22 - m02 * m20, m02 * m10 - m00 * m12);
        dir.normalize();
        return dir;
    }

    public Vector3fc normalizedPositiveY(Vector3fc dir) {
        dir.set(m01, m11, m21);
        return dir;
    }

    public Vector3fc origin(Vector3fc origin) {
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

    public Matrix4x3fc shadow(IVector4f light, float a, float b, float c, float d) {
        return shadow(light.x(), light.y(), light.z(), light.w(), a, b, c, d, this);
    }

    public Matrix4x3fc shadow(IVector4f light, float a, float b, float c, float d, Matrix4x3fc dest) {
        return shadow(light.x(), light.y(), light.z(), light.w(), a, b, c, d, dest);
    }

    public Matrix4x3fc shadow(float lightX, float lightY, float lightZ, float lightW, float a, float b, float c, float d) {
        return shadow(lightX, lightY, lightZ, lightW, a, b, c, d, this);
    }

    public Matrix4x3fc shadow(float lightX, float lightY, float lightZ, float lightW, float a, float b, float c, float d, Matrix4x3fc dest) {
        // normalize plane
        float invPlaneLen = (float) (1.0 / Math.sqrt(a * a + b * b + c * c));
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
        float nm10 = m00 * rm10 + m10 * rm11 + m20 * rm12 + m30 * rm13;
        float nm11 = m01 * rm10 + m11 * rm11 + m21 * rm12 + m31 * rm13;
        float nm12 = m02 * rm10 + m12 * rm11 + m22 * rm12 + m32 * rm13;
        float nm20 = m00 * rm20 + m10 * rm21 + m20 * rm22 + m30 * rm23;
        float nm21 = m01 * rm20 + m11 * rm21 + m21 * rm22 + m31 * rm23;
        float nm22 = m02 * rm20 + m12 * rm21 + m22 * rm22 + m32 * rm23;
        dest.m30(m00 * rm30 + m10 * rm31 + m20 * rm32 + m30 * rm33);
        dest.m31(m01 * rm30 + m11 * rm31 + m21 * rm32 + m31 * rm33);
        dest.m32(m02 * rm30 + m12 * rm31 + m22 * rm32 + m32 * rm33);
        dest.m00(nm00);
        dest.m01(nm01);
        dest.m02(nm02);
        dest.m10(nm10);
        dest.m11(nm11);
        dest.m12(nm12);
        dest.m20(nm20);
        dest.m21(nm21);
        dest.m22(nm22);
        dest.properties((byte) (properties & ~(PROPERTY_IDENTITY | PROPERTY_TRANSLATION)));

        return dest;
    }

    public Matrix4x3fc shadow(IVector4f light, IMatrix4x3f planeTransform, Matrix4x3fc dest) {
        // compute plane equation by transforming (y = 0)
        float a = planeTransform.m10();
        float b = planeTransform.m11();
        float c = planeTransform.m12();
        float d = -a * planeTransform.m30() - b * planeTransform.m31() - c * planeTransform.m32();
        return shadow(light.x(), light.y(), light.z(), light.w(), a, b, c, d, dest);
    }

    public Matrix4x3fc shadow(IVector4f light, IMatrix4x3f planeTransform) {
        return shadow(light, planeTransform, this);
    }

    public Matrix4x3fc shadow(float lightX, float lightY, float lightZ, float lightW, IMatrix4x3f planeTransform, Matrix4x3fc dest) {
        // compute plane equation by transforming (y = 0)
        float a = planeTransform.m10();
        float b = planeTransform.m11();
        float c = planeTransform.m12();
        float d = -a * planeTransform.m30() - b * planeTransform.m31() - c * planeTransform.m32();
        return shadow(lightX, lightY, lightZ, lightW, a, b, c, d, dest);
    }

    public Matrix4x3fc shadow(float lightX, float lightY, float lightZ, float lightW, Matrix4x3fc planeTransform) {
        return shadow(lightX, lightY, lightZ, lightW, planeTransform, this);
    }

    public Matrix4x3fc billboardCylindrical(IVector3f objPos, IVector3f targetPos, IVector3f up) {
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
        m00 = leftX;
        m01 = leftY;
        m02 = leftZ;
        m10 = up.x();
        m11 = up.y();
        m12 = up.z();
        m20 = dirX;
        m21 = dirY;
        m22 = dirZ;
        m30 = objPos.x();
        m31 = objPos.y();
        m32 = objPos.z();
        properties = 0;
        return this;
    }

    public Matrix4x3fc billboardSpherical(IVector3f objPos, IVector3f targetPos, IVector3f up) {
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
        m00 = leftX;
        m01 = leftY;
        m02 = leftZ;
        m10 = upX;
        m11 = upY;
        m12 = upZ;
        m20 = dirX;
        m21 = dirY;
        m22 = dirZ;
        m30 = objPos.x();
        m31 = objPos.y();
        m32 = objPos.z();
        properties = 0;
        return this;
    }

    public Matrix4x3fc billboardSpherical(IVector3f objPos, IVector3f targetPos) {
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
        m00 = 1.0f - q11;
        m01 = q01;
        m02 = -q13;
        m10 = q01;
        m11 = 1.0f - q00;
        m12 = q03;
        m20 = q13;
        m21 = -q03;
        m22 = 1.0f - q11 - q00;
        m30 = objPos.x();
        m31 = objPos.y();
        m32 = objPos.z();
        properties = 0;
        return this;
    }

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
        result = prime * result + Float.floatToIntBits(m30);
        result = prime * result + Float.floatToIntBits(m31);
        result = prime * result + Float.floatToIntBits(m32);
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Matrix4x3fc))
            return false;
        Matrix4x3fc other = (Matrix4x3fc) obj;
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
        if (Float.floatToIntBits(m30) != Float.floatToIntBits(other.m30()))
            return false;
        if (Float.floatToIntBits(m31) != Float.floatToIntBits(other.m31()))
            return false;
        if (Float.floatToIntBits(m32) != Float.floatToIntBits(other.m32()))
            return false;
        return true;
    }

    public Matrix4x3fc pick(float x, float y, float width, float height, int[] viewport, Matrix4x3fc dest) {
        float sx = viewport[2] / width;
        float sy = viewport[3] / height;
        float tx = (viewport[2] + 2.0f * (viewport[0] - x)) / width;
        float ty = (viewport[3] + 2.0f * (viewport[1] - y)) / height;
        dest.m30(m00 * tx + m10 * ty + m30);
        dest.m31(m01 * tx + m11 * ty + m31);
        dest.m32(m02 * tx + m12 * ty + m32);
        dest.m00(m00 * sx);
        dest.m01(m01 * sx);
        dest.m02(m02 * sx);
        dest.m10(m10 * sy);
        dest.m11(m11 * sy);
        dest.m12(m12 * sy);
        dest.properties(0);
        return dest;
    }

    public Matrix4x3fc pick(float x, float y, float width, float height, int[] viewport) {
        return pick(x, y, width, height, viewport, this);
    }

    public Matrix4x3fc swap(Matrix4x3fc other) {
        float tmp;
        tmp = this.m00;
        this.m00 = other.m00();
        other.m00(tmp);
        tmp = this.m01;
        this.m01 = other.m01();
        other.m01(tmp);
        tmp = this.m02;
        this.m02 = other.m02();
        other.m02(tmp);
        tmp = this.m10;
        this.m10 = other.m10();
        other.m10(tmp);
        tmp = this.m11;
        this.m11 = other.m11();
        other.m11(tmp);
        tmp = this.m12;
        this.m12 = other.m12();
        other.m12(tmp);
        tmp = this.m20;
        this.m20 = other.m20();
        other.m20(tmp);
        tmp = this.m21;
        this.m21 = other.m21();
        other.m21(tmp);
        tmp = this.m22;
        this.m22 = other.m22();
        other.m22(tmp);
        tmp = this.m30;
        this.m30 = other.m30();
        other.m30(tmp);
        tmp = this.m31;
        this.m31 = other.m31();
        other.m31(tmp);
        tmp = this.m32;
        this.m32 = other.m32();
        other.m32(tmp);
        byte props = properties;
        this.properties = other.properties();
        other.properties(props);
        return this;
    }

    public Matrix4x3fc arcball(float radius, float centerX, float centerY, float centerZ, float angleX, float angleY, Matrix4x3fc dest) {
        float m30 = m20 * -radius + this.m30;
        float m31 = m21 * -radius + this.m31;
        float m32 = m22 * -radius + this.m32;
        float sin = (float) Math.sin(angleX);
        float cos = (float) Math.cosFromSin(sin, angleX);
        float nm10 = m10 * cos + m20 * sin;
        float nm11 = m11 * cos + m21 * sin;
        float nm12 = m12 * cos + m22 * sin;
        float m20 = this.m20 * cos - m10 * sin;
        float m21 = this.m21 * cos - m11 * sin;
        float m22 = this.m22 * cos - m12 * sin;
        sin = (float) Math.sin(angleY);
        cos = (float) Math.cosFromSin(sin, angleY);
        float nm00 = m00 * cos - m20 * sin;
        float nm01 = m01 * cos - m21 * sin;
        float nm02 = m02 * cos - m22 * sin;
        float nm20 = m00 * sin + m20 * cos;
        float nm21 = m01 * sin + m21 * cos;
        float nm22 = m02 * sin + m22 * cos;
        dest.m30(-nm00 * centerX - nm10 * centerY - nm20 * centerZ + m30);
        dest.m31(-nm01 * centerX - nm11 * centerY - nm21 * centerZ + m31);
        dest.m32(-nm02 * centerX - nm12 * centerY - nm22 * centerZ + m32);
        dest.m20(nm20);
        dest.m21(nm21);
        dest.m22(nm22);
        dest.m10(nm10);
        dest.m11(nm11);
        dest.m12(nm12);
        dest.m00(nm00);
        dest.m01(nm01);
        dest.m02(nm02);
        dest.properties((byte) (properties & ~(PROPERTY_IDENTITY | PROPERTY_TRANSLATION)));
        return dest;
    }

    public Matrix4x3fc arcball(float radius, IVector3f center, float angleX, float angleY, Matrix4x3fc dest) {
        return arcball(radius, center.x(), center.y(), center.z(), angleX, angleY, dest);
    }

    public Matrix4x3fc arcball(float radius, float centerX, float centerY, float centerZ, float angleX, float angleY) {
        return arcball(radius, centerX, centerY, centerZ, angleX, angleY, this);
    }

    public Matrix4x3fc arcball(float radius, IVector3f center, float angleX, float angleY) {
        return arcball(radius, center.x(), center.y(), center.z(), angleX, angleY, this);
    }

    public Matrix4x3fc transformAab(float minX, float minY, float minZ, float maxX, float maxY, float maxZ, Vector3fc outMin, Vector3fc outMax) {
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

    public Matrix4x3fc transformAab(IVector3f min, IVector3f max, Vector3fc outMin, Vector3fc outMax) {
        return transformAab(min.x(), min.y(), min.z(), max.x(), max.y(), max.z(), outMin, outMax);
    }

    public Matrix4x3fc lerp(IMatrix4x3f other, float t) {
        return lerp(other, t, this);
    }

    public Matrix4x3fc lerp(IMatrix4x3f other, float t, Matrix4x3fc dest) {
        dest.m00(m00 + (other.m00() - m00) * t);
        dest.m01(m01 + (other.m01() - m01) * t);
        dest.m02(m02 + (other.m02() - m02) * t);
        dest.m10(m10 + (other.m10() - m10) * t);
        dest.m11(m11 + (other.m11() - m11) * t);
        dest.m12(m12 + (other.m12() - m12) * t);
        dest.m20(m20 + (other.m20() - m20) * t);
        dest.m21(m21 + (other.m21() - m21) * t);
        dest.m22(m22 + (other.m22() - m22) * t);
        dest.m30(m30 + (other.m30() - m30) * t);
        dest.m31(m31 + (other.m31() - m31) * t);
        dest.m32(m32 + (other.m32() - m32) * t);
        return dest;
    }

    public Matrix4x3fc rotateTowards(IVector3f dir, IVector3f up, Matrix4x3fc dest) {
        return rotateTowards(dir.x(), dir.y(), dir.z(), up.x(), up.y(), up.z(), dest);
    }

    public Matrix4x3fc rotateTowards(IVector3f dir, IVector3f up) {
        return rotateTowards(dir.x(), dir.y(), dir.z(), up.x(), up.y(), up.z(), this);
    }

    public Matrix4x3fc rotateTowards(float dirX, float dirY, float dirZ, float upX, float upY, float upZ) {
        return rotateTowards(dirX, dirY, dirZ, upX, upY, upZ, this);
    }

    public Matrix4x3fc rotateTowards(float dirX, float dirY, float dirZ, float upX, float upY, float upZ, Matrix4x3fc dest) {
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
        dest.properties((byte) (properties & ~(PROPERTY_IDENTITY | PROPERTY_TRANSLATION)));
        return dest;
    }

    public Matrix4x3fc rotationTowards(IVector3f dir, IVector3f up) {
        return rotationTowards(dir.x(), dir.y(), dir.z(), up.x(), up.y(), up.z());
    }

    public Matrix4x3fc rotationTowards(float dirX, float dirY, float dirZ, float upX, float upY, float upZ) {
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
        this.m30 = 0.0f;
        this.m31 = 0.0f;
        this.m32 = 0.0f;
        properties = 0;
        return this;
    }

    public Matrix4x3fc translationRotateTowards(IVector3f pos, IVector3f dir, IVector3f up) {
        return translationRotateTowards(pos.x(), pos.y(), pos.z(), dir.x(), dir.y(), dir.z(), up.x(), up.y(), up.z());
    }

    public Matrix4x3fc translationRotateTowards(float posX, float posY, float posZ, float dirX, float dirY, float dirZ, float upX, float upY, float upZ) {
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
        this.m30 = posX;
        this.m31 = posY;
        this.m32 = posZ;
        properties = 0;
        return this;
    }

    public Vector3fc getEulerAnglesZYX(Vector3fc dest) {
        dest.set((float) Math.atan2(m12, m22),
                (float) Math.atan2(-m02, (float) Math.sqrt(m12 * m12 + m22 * m22)),
                (float) Math.atan2(m01, m00));
        return dest;
    }
}
