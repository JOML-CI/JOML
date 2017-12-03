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

import org.joml.api.AxisAngle4dc;
import org.joml.api.AxisAngle4fc;
import org.joml.api.Planedc;
import org.joml.api.matrix.*;
import org.joml.api.quaternion.IQuaterniond;
import org.joml.api.quaternion.IQuaternionf;
import org.joml.api.quaternion.Quaterniondc;
import org.joml.api.quaternion.Quaternionfc;
import org.joml.api.vector.*;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
//#ifdef __HAS_NIO__
import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
//#endif
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Contains the definition of an affine 4x3 matrix (4 columns, 3 rows) of doubles, and associated functions to transform
 * it. The matrix is column-major to match OpenGL's interpretation, and it looks like this:
 * <p>
 * m00  m10  m20  m30<br> m01  m11  m21  m31<br> m02  m12  m22  m32<br>
 *
 * @author Richard Greenlees
 * @author Kai Burjack
 */
public class Matrix4x3d extends Matrix4x3dc implements Externalizable {

    private static final long serialVersionUID = 1L;

    double m00, m01, m02;
    double m10, m11, m12;
    double m20, m21, m22;
    double m30, m31, m32;

    byte properties;

    /**
     * Create a new {@link Matrix4x3d} and set it to {@link #identity() identity}.
     */
    public Matrix4x3d() {
        m00 = 1.0;
        m11 = 1.0;
        m22 = 1.0;
        properties = PROPERTY_IDENTITY | PROPERTY_TRANSLATION;
    }

    /**
     * Create a new {@link Matrix4x3d} and make it a copy of the given matrix.
     *
     * @param mat the {@link IMatrix4x3d} to copy the values from
     */
    public Matrix4x3d(IMatrix4x3d mat) {
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
        properties = mat.properties();
    }

    /**
     * Create a new {@link Matrix4x3d} and make it a copy of the given matrix.
     *
     * @param mat the {@link IMatrix4x3f} to copy the values from
     */
    public Matrix4x3d(IMatrix4x3f mat) {
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
        properties = mat.properties();
    }

    /**
     * Create a new {@link Matrix4x3d} by setting its left 3x3 submatrix to the values of the given {@link IMatrix3d}
     * and the rest to identity.
     *
     * @param mat the {@link IMatrix3d}
     */
    public Matrix4x3d(IMatrix3d mat) {
        m00 = mat.m00();
        m01 = mat.m01();
        m02 = mat.m02();
        m10 = mat.m10();
        m11 = mat.m11();
        m12 = mat.m12();
        m20 = mat.m20();
        m21 = mat.m21();
        m22 = mat.m22();
        properties = 0;
    }

    /**
     * Create a new 4x4 matrix using the supplied double values.
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
    public Matrix4x3d(double m00, double m01, double m02,
                      double m10, double m11, double m12,
                      double m20, double m21, double m22,
                      double m30, double m31, double m32) {
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
     * Create a new {@link Matrix4x3d} by reading its 12 double components from the given {@link DoubleBuffer} at the
     * buffer's current position.
     * <p>
     * That DoubleBuffer is expected to hold the values in column-major order.
     * <p>
     * The buffer's position will not be changed by this method.
     *
     * @param buffer the {@link DoubleBuffer} to read the matrix values from
     */
    public Matrix4x3d(DoubleBuffer buffer) {
        MemUtil.INSTANCE.get(this, buffer.position(), buffer);
    }
    //#endif

    public Matrix4x3dc assumeNothing() {
        properties = 0;
        return this;
    }

    public byte properties() {
        return properties;
    }

    public double m00() {
        return m00;
    }

    public double m01() {
        return m01;
    }

    public double m02() {
        return m02;
    }

    public double m10() {
        return m10;
    }

    public double m11() {
        return m11;
    }

    public double m12() {
        return m12;
    }

    public double m20() {
        return m20;
    }

    public double m21() {
        return m21;
    }

    public double m22() {
        return m22;
    }

    public double m30() {
        return m30;
    }

    public double m31() {
        return m31;
    }

    public double m32() {
        return m32;
    }

    public Matrix4x3dc m00(double m00) {
        this.m00 = m00;
        properties &= ~(PROPERTY_IDENTITY | PROPERTY_TRANSLATION);
        return this;
    }

    public Matrix4x3dc m01(double m01) {
        this.m01 = m01;
        properties &= ~(PROPERTY_IDENTITY | PROPERTY_TRANSLATION);
        return this;
    }

    public Matrix4x3dc m02(double m02) {
        this.m02 = m02;
        properties &= ~(PROPERTY_IDENTITY | PROPERTY_TRANSLATION);
        return this;
    }

    public Matrix4x3dc m10(double m10) {
        this.m10 = m10;
        properties &= ~(PROPERTY_IDENTITY | PROPERTY_TRANSLATION);
        return this;
    }

    public Matrix4x3dc m11(double m11) {
        this.m11 = m11;
        properties &= ~(PROPERTY_IDENTITY | PROPERTY_TRANSLATION);
        return this;
    }

    public Matrix4x3dc m12(double m12) {
        this.m12 = m12;
        properties &= ~(PROPERTY_IDENTITY | PROPERTY_TRANSLATION);
        return this;
    }

    public Matrix4x3dc m20(double m20) {
        this.m20 = m20;
        properties &= ~(PROPERTY_IDENTITY | PROPERTY_TRANSLATION);
        return this;
    }

    public Matrix4x3dc m21(double m21) {
        this.m21 = m21;
        properties &= ~(PROPERTY_IDENTITY | PROPERTY_TRANSLATION);
        return this;
    }

    public Matrix4x3dc m22(double m22) {
        this.m22 = m22;
        properties &= ~(PROPERTY_IDENTITY | PROPERTY_TRANSLATION);
        return this;
    }

    public Matrix4x3dc m30(double m30) {
        this.m30 = m30;
        properties &= ~(PROPERTY_IDENTITY);
        return this;
    }

    public Matrix4x3dc m31(double m31) {
        this.m31 = m31;
        properties &= ~(PROPERTY_IDENTITY);
        return this;
    }

    public Matrix4x3dc m32(double m32) {
        this.m32 = m32;
        properties &= ~(PROPERTY_IDENTITY);
        return this;
    }

    public Matrix4x3dc properties(int properties) {
        this.properties = (byte) properties;
        return this;
    }

    public Matrix4x3dc identity() {
        m00 = 1.0;
        m01 = 0.0;
        m02 = 0.0;
        m10 = 0.0;
        m11 = 1.0;
        m12 = 0.0;
        m20 = 0.0;
        m21 = 0.0;
        m22 = 1.0;
        m30 = 0.0;
        m31 = 0.0;
        m32 = 0.0;
        properties = PROPERTY_IDENTITY | PROPERTY_TRANSLATION;
        return this;
    }

    public Matrix4x3dc set(IMatrix4x3d m) {
        m00 = m.m00();
        m01 = m.m01();
        m02 = m.m02();
        m10 = m.m10();
        m11 = m.m11();
        m12 = m.m12();
        m20 = m.m20();
        m21 = m.m21();
        m22 = m.m22();
        m30 = m.m30();
        m31 = m.m31();
        m32 = m.m32();
        properties = m.properties();
        return this;
    }

    public Matrix4x3dc set(IMatrix4x3f m) {
        m00 = m.m00();
        m01 = m.m01();
        m02 = m.m02();
        m10 = m.m10();
        m11 = m.m11();
        m12 = m.m12();
        m20 = m.m20();
        m21 = m.m21();
        m22 = m.m22();
        m30 = m.m30();
        m31 = m.m31();
        m32 = m.m32();
        properties = m.properties();
        return this;
    }

    public Matrix4x3dc set(IMatrix4d m) {
        m00 = m.m00();
        m01 = m.m01();
        m02 = m.m02();
        m10 = m.m10();
        m11 = m.m11();
        m12 = m.m12();
        m20 = m.m20();
        m21 = m.m21();
        m22 = m.m22();
        m30 = m.m30();
        m31 = m.m31();
        m32 = m.m32();
        properties = (byte) (m.properties() & (PROPERTY_IDENTITY | PROPERTY_TRANSLATION));
        return this;
    }

    public Matrix4dc get(Matrix4dc dest) {
        return dest.set4x3(this);
    }

    public Matrix4x3dc set(IMatrix3d mat) {
        m00 = mat.m00();
        m01 = mat.m01();
        m02 = mat.m02();
        m10 = mat.m10();
        m11 = mat.m11();
        m12 = mat.m12();
        m20 = mat.m20();
        m21 = mat.m21();
        m22 = mat.m22();
        m30 = 0.0;
        m31 = 0.0;
        m32 = 0.0;
        properties = 0;
        return this;
    }

    public Matrix4x3dc set(IVector3d col0,
                           IVector3d col1,
                           IVector3d col2,
                           IVector3d col3) {
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
        this.properties = 0;
        return this;
    }

    public Matrix4x3dc set3x3(IMatrix4x3d mat) {
        m00 = mat.m00();
        m01 = mat.m01();
        m02 = mat.m02();
        m10 = mat.m10();
        m11 = mat.m11();
        m12 = mat.m12();
        m20 = mat.m20();
        m21 = mat.m21();
        m22 = mat.m22();
        properties &= mat.properties();
        return this;
    }

    public Matrix4x3dc set(AxisAngle4fc axisAngle) {
        double x = axisAngle.x();
        double y = axisAngle.y();
        double z = axisAngle.z();
        double angle = axisAngle.angle();
        double invLength = 1.0 / Math.sqrt(x * x + y * y + z * z);
        x *= invLength;
        y *= invLength;
        z *= invLength;
        double s = Math.sin(angle);
        double c = Math.cosFromSin(s, angle);
        double omc = 1.0 - c;
        m00 = c + x * x * omc;
        m11 = c + y * y * omc;
        m22 = c + z * z * omc;
        double tmp1 = x * y * omc;
        double tmp2 = z * s;
        m10 = tmp1 - tmp2;
        m01 = tmp1 + tmp2;
        tmp1 = x * z * omc;
        tmp2 = y * s;
        m20 = tmp1 + tmp2;
        m02 = tmp1 - tmp2;
        tmp1 = y * z * omc;
        tmp2 = x * s;
        m21 = tmp1 - tmp2;
        m12 = tmp1 + tmp2;
        m30 = 0.0;
        m31 = 0.0;
        m32 = 0.0;
        properties = 0;
        return this;
    }

    public Matrix4x3dc set(AxisAngle4dc axisAngle) {
        double x = axisAngle.x();
        double y = axisAngle.y();
        double z = axisAngle.z();
        double angle = axisAngle.angle();
        double invLength = 1.0 / Math.sqrt(x * x + y * y + z * z);
        x *= invLength;
        y *= invLength;
        z *= invLength;
        double s = Math.sin(angle);
        double c = Math.cosFromSin(s, angle);
        double omc = 1.0 - c;
        m00 = c + x * x * omc;
        m11 = c + y * y * omc;
        m22 = c + z * z * omc;
        double tmp1 = x * y * omc;
        double tmp2 = z * s;
        m10 = tmp1 - tmp2;
        m01 = tmp1 + tmp2;
        tmp1 = x * z * omc;
        tmp2 = y * s;
        m20 = tmp1 + tmp2;
        m02 = tmp1 - tmp2;
        tmp1 = y * z * omc;
        tmp2 = x * s;
        m21 = tmp1 - tmp2;
        m12 = tmp1 + tmp2;
        m30 = 0.0;
        m31 = 0.0;
        m32 = 0.0;
        properties = 0;
        return this;
    }

    public Matrix4x3dc set(IQuaternionf q) {
        return rotation(q);
    }

    public Matrix4x3dc set(IQuaterniond q) {
        return rotation(q);
    }

    public Matrix4x3dc mul(IMatrix4x3d right) {
        return mul(right, this);
    }

    public Matrix4x3dc mul(IMatrix4x3d right, Matrix4x3dc dest) {
        if ((properties & PROPERTY_IDENTITY) != 0)
            return dest.set(right);
        else if ((right.properties() & PROPERTY_IDENTITY) != 0)
            return dest.set(this);
        else if ((properties & PROPERTY_TRANSLATION) != 0)
            return mulTranslation(right, dest);
        return mulGeneric(right, dest);
    }

    private Matrix4x3dc mulGeneric(IMatrix4x3d right, Matrix4x3dc dest) {
        double nm00 = m00 * right.m00() + m10 * right.m01() + m20 * right.m02();
        double nm01 = m01 * right.m00() + m11 * right.m01() + m21 * right.m02();
        double nm02 = m02 * right.m00() + m12 * right.m01() + m22 * right.m02();
        double nm10 = m00 * right.m10() + m10 * right.m11() + m20 * right.m12();
        double nm11 = m01 * right.m10() + m11 * right.m11() + m21 * right.m12();
        double nm12 = m02 * right.m10() + m12 * right.m11() + m22 * right.m12();
        double nm20 = m00 * right.m20() + m10 * right.m21() + m20 * right.m22();
        double nm21 = m01 * right.m20() + m11 * right.m21() + m21 * right.m22();
        double nm22 = m02 * right.m20() + m12 * right.m21() + m22 * right.m22();
        double nm30 = m00 * right.m30() + m10 * right.m31() + m20 * right.m32() + m30;
        double nm31 = m01 * right.m30() + m11 * right.m31() + m21 * right.m32() + m31;
        double nm32 = m02 * right.m30() + m12 * right.m31() + m22 * right.m32() + m32;
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

    public Matrix4x3dc mul(IMatrix4x3f right) {
        return mul(right, this);
    }

    public Matrix4x3dc mul(IMatrix4x3f right, Matrix4x3dc dest) {
        if ((properties & PROPERTY_IDENTITY) != 0)
            return dest.set(right);
        else if ((right.properties() & PROPERTY_IDENTITY) != 0)
            return dest.set(this);
        else if ((properties & PROPERTY_TRANSLATION) != 0)
            return mulTranslation(right, dest);
        return mulGeneric(right, dest);
    }

    private Matrix4x3dc mulGeneric(IMatrix4x3f right, Matrix4x3dc dest) {
        double nm00 = m00 * right.m00() + m10 * right.m01() + m20 * right.m02();
        double nm01 = m01 * right.m00() + m11 * right.m01() + m21 * right.m02();
        double nm02 = m02 * right.m00() + m12 * right.m01() + m22 * right.m02();
        double nm10 = m00 * right.m10() + m10 * right.m11() + m20 * right.m12();
        double nm11 = m01 * right.m10() + m11 * right.m11() + m21 * right.m12();
        double nm12 = m02 * right.m10() + m12 * right.m11() + m22 * right.m12();
        double nm20 = m00 * right.m20() + m10 * right.m21() + m20 * right.m22();
        double nm21 = m01 * right.m20() + m11 * right.m21() + m21 * right.m22();
        double nm22 = m02 * right.m20() + m12 * right.m21() + m22 * right.m22();
        double nm30 = m00 * right.m30() + m10 * right.m31() + m20 * right.m32() + m30;
        double nm31 = m01 * right.m30() + m11 * right.m31() + m21 * right.m32() + m31;
        double nm32 = m02 * right.m30() + m12 * right.m31() + m22 * right.m32() + m32;
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

    public Matrix4x3dc mulTranslation(IMatrix4x3d right, Matrix4x3dc dest) {
        double nm00 = right.m00();
        double nm01 = right.m01();
        double nm02 = right.m02();
        double nm10 = right.m10();
        double nm11 = right.m11();
        double nm12 = right.m12();
        double nm20 = right.m20();
        double nm21 = right.m21();
        double nm22 = right.m22();
        double nm30 = right.m30() + m30;
        double nm31 = right.m31() + m31;
        double nm32 = right.m32() + m32;
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

    public Matrix4x3dc mulTranslation(IMatrix4x3f right, Matrix4x3dc dest) {
        double nm00 = right.m00();
        double nm01 = right.m01();
        double nm02 = right.m02();
        double nm10 = right.m10();
        double nm11 = right.m11();
        double nm12 = right.m12();
        double nm20 = right.m20();
        double nm21 = right.m21();
        double nm22 = right.m22();
        double nm30 = right.m30() + m30;
        double nm31 = right.m31() + m31;
        double nm32 = right.m32() + m32;
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

    public Matrix4x3dc mulOrtho(IMatrix4x3d view) {
        return mulOrtho(view, this);
    }

    public Matrix4x3dc mulOrtho(IMatrix4x3d view, Matrix4x3dc dest) {
        double nm00 = m00 * view.m00();
        double nm01 = m11 * view.m01();
        double nm02 = m22 * view.m02();
        double nm10 = m00 * view.m10();
        double nm11 = m11 * view.m11();
        double nm12 = m22 * view.m12();
        double nm20 = m00 * view.m20();
        double nm21 = m11 * view.m21();
        double nm22 = m22 * view.m22();
        double nm30 = m00 * view.m30() + m30;
        double nm31 = m11 * view.m31() + m31;
        double nm32 = m22 * view.m32() + m32;
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

    public Matrix4x3dc fma(IMatrix4x3d other, double otherFactor) {
        return fma(other, otherFactor, this);
    }

    public Matrix4x3dc fma(IMatrix4x3d other, double otherFactor, Matrix4x3dc dest) {
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

    public Matrix4x3dc fma(IMatrix4x3f other, double otherFactor) {
        return fma(other, otherFactor, this);
    }

    public Matrix4x3dc fma(IMatrix4x3f other, double otherFactor, Matrix4x3dc dest) {
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

    public Matrix4x3dc add(IMatrix4x3d other) {
        return add(other, this);
    }

    public Matrix4x3dc add(IMatrix4x3d other, Matrix4x3dc dest) {
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

    public Matrix4x3dc add(IMatrix4x3f other) {
        return add(other, this);
    }

    public Matrix4x3dc add(IMatrix4x3f other, Matrix4x3dc dest) {
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

    public Matrix4x3dc sub(IMatrix4x3d subtrahend) {
        return sub(subtrahend, this);
    }

    public Matrix4x3dc sub(IMatrix4x3d subtrahend, Matrix4x3dc dest) {
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

    public Matrix4x3dc sub(IMatrix4x3f subtrahend) {
        return sub(subtrahend, this);
    }

    public Matrix4x3dc sub(IMatrix4x3f subtrahend, Matrix4x3dc dest) {
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

    public Matrix4x3dc mulComponentWise(IMatrix4x3d other) {
        return mulComponentWise(other, this);
    }

    public Matrix4x3dc mulComponentWise(IMatrix4x3d other, Matrix4x3dc dest) {
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

    public Matrix4x3dc set(double m00, double m01, double m02,
                           double m10, double m11, double m12,
                           double m20, double m21, double m22,
                           double m30, double m31, double m32) {
        this.m00 = m00;
        this.m10 = m10;
        this.m20 = m20;
        this.m30 = m30;
        this.m01 = m01;
        this.m11 = m11;
        this.m21 = m21;
        this.m31 = m31;
        this.m02 = m02;
        this.m12 = m12;
        this.m22 = m22;
        this.m32 = m32;
        properties = 0;
        return this;
    }

    public Matrix4x3dc set(double m[], int off) {
        m00 = m[off + 0];
        m01 = m[off + 1];
        m02 = m[off + 2];
        m10 = m[off + 3];
        m11 = m[off + 4];
        m12 = m[off + 5];
        m20 = m[off + 6];
        m21 = m[off + 7];
        m22 = m[off + 8];
        m30 = m[off + 9];
        m31 = m[off + 10];
        m32 = m[off + 11];
        properties = 0;
        return this;
    }

    public Matrix4x3dc set(double m[]) {
        return set(m, 0);
    }

    public Matrix4x3dc set(float m[], int off) {
        m00 = m[off + 0];
        m01 = m[off + 1];
        m02 = m[off + 2];
        m10 = m[off + 3];
        m11 = m[off + 4];
        m12 = m[off + 5];
        m20 = m[off + 6];
        m21 = m[off + 7];
        m22 = m[off + 8];
        m30 = m[off + 9];
        m31 = m[off + 10];
        m32 = m[off + 11];
        properties = 0;
        return this;
    }

    public Matrix4x3dc set(float m[]) {
        return set(m, 0);
    }

    //#ifdef __HAS_NIO__

    public Matrix4x3dc set(DoubleBuffer buffer) {
        MemUtil.INSTANCE.get(this, buffer.position(), buffer);
        properties = 0;
        return this;
    }

    public Matrix4x3dc set(FloatBuffer buffer) {
        MemUtil.INSTANCE.getf(this, buffer.position(), buffer);
        properties = 0;
        return this;
    }

    public Matrix4x3dc set(ByteBuffer buffer) {
        MemUtil.INSTANCE.get(this, buffer.position(), buffer);
        properties = 0;
        return this;
    }

    public Matrix4x3dc setFloats(ByteBuffer buffer) {
        MemUtil.INSTANCE.getf(this, buffer.position(), buffer);
        properties = 0;
        return this;
    }
    //#endif

    public double determinant() {
        return (m00 * m11 - m01 * m10) * m22
                + (m02 * m10 - m00 * m12) * m21
                + (m01 * m12 - m02 * m11) * m20;
    }

    public Matrix4x3dc invert() {
        return invert(this);
    }

    public Matrix4x3dc invert(Matrix4x3dc dest) {
        if ((properties & PROPERTY_IDENTITY) != 0)
            return dest.identity();
        return invertGeneric(dest);
    }

    private Matrix4x3dc invertGeneric(Matrix4x3dc dest) {
        double m11m00 = m00 * m11, m10m01 = m01 * m10, m10m02 = m02 * m10;
        double m12m00 = m00 * m12, m12m01 = m01 * m12, m11m02 = m02 * m11;
        double s = 1.0f / ((m11m00 - m10m01) * m22 + (m10m02 - m12m00) * m21 + (m12m01 - m11m02) * m20);
        double m10m22 = m10 * m22, m10m21 = m10 * m21, m11m22 = m11 * m22;
        double m11m20 = m11 * m20, m12m21 = m12 * m21, m12m20 = m12 * m20;
        double m20m02 = m20 * m02, m20m01 = m20 * m01, m21m02 = m21 * m02;
        double m21m00 = m21 * m00, m22m01 = m22 * m01, m22m00 = m22 * m00;
        double nm00 = (m11m22 - m12m21) * s;
        double nm01 = (m21m02 - m22m01) * s;
        double nm02 = (m12m01 - m11m02) * s;
        double nm10 = (m12m20 - m10m22) * s;
        double nm11 = (m22m00 - m20m02) * s;
        double nm12 = (m10m02 - m12m00) * s;
        double nm20 = (m10m21 - m11m20) * s;
        double nm21 = (m20m01 - m21m00) * s;
        double nm22 = (m11m00 - m10m01) * s;
        double nm30 = (m10m22 * m31 - m10m21 * m32 + m11m20 * m32 - m11m22 * m30 + m12m21 * m30 - m12m20 * m31) * s;
        double nm31 = (m20m02 * m31 - m20m01 * m32 + m21m00 * m32 - m21m02 * m30 + m22m01 * m30 - m22m00 * m31) * s;
        double nm32 = (m11m02 * m30 - m12m01 * m30 + m12m00 * m31 - m10m02 * m31 + m10m01 * m32 - m11m00 * m32) * s;
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

    public Matrix4x3dc invertOrtho(Matrix4x3dc dest) {
        double invM00 = 1.0 / m00;
        double invM11 = 1.0 / m11;
        double invM22 = 1.0 / m22;
        dest.set(invM00, 0, 0,
                0, invM11, 0,
                0, 0, invM22,
                -m30 * invM00, -m31 * invM11, -m32 * invM22);
        dest.properties(0);
        return dest;
    }

    public Matrix4x3dc invertOrtho() {
        return invertOrtho(this);
    }

    public Matrix4x3dc invertUnitScale(Matrix4x3dc dest) {
        dest.set(m00, m10, m20,
                m01, m11, m21,
                m02, m12, m22,
                -m00 * m30 - m01 * m31 - m02 * m32,
                -m10 * m30 - m11 * m31 - m12 * m32,
                -m20 * m30 - m21 * m31 - m22 * m32);
        dest.properties(0);
        return dest;
    }

    public Matrix4x3dc invertUnitScale() {
        return invertUnitScale(this);
    }

    public Matrix4x3dc transpose3x3() {
        return transpose3x3(this);
    }

    public Matrix4x3dc transpose3x3(Matrix4x3dc dest) {
        double nm00 = m00;
        double nm01 = m10;
        double nm02 = m20;
        double nm10 = m01;
        double nm11 = m11;
        double nm12 = m21;
        double nm20 = m02;
        double nm21 = m12;
        double nm22 = m22;
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

    public Matrix3dc transpose3x3(Matrix3dc dest) {
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

    public Matrix4x3dc translation(double x, double y, double z) {
        m00 = 1.0;
        m01 = 0.0;
        m02 = 0.0;
        m10 = 0.0;
        m11 = 1.0;
        m12 = 0.0;
        m20 = 0.0;
        m21 = 0.0;
        m22 = 1.0;
        m30 = x;
        m31 = y;
        m32 = z;
        properties = PROPERTY_TRANSLATION;
        return this;
    }

    public Matrix4x3dc translation(IVector3f offset) {
        return translation(offset.x(), offset.y(), offset.z());
    }

    public Matrix4x3dc translation(IVector3d offset) {
        return translation(offset.x(), offset.y(), offset.z());
    }

    public Matrix4x3dc setTranslation(double x, double y, double z) {
        m30 = x;
        m31 = y;
        m32 = z;
        properties = 0;
        return this;
    }

    public Matrix4x3dc setTranslation(IVector3d xyz) {
        return setTranslation(xyz.x(), xyz.y(), xyz.z());
    }

    public Vector3dc getTranslation(Vector3dc dest) {
        dest.set(m30, m31, m32);
        return dest;
    }

    public Vector3dc getScale(Vector3dc dest) {
        dest.set(Math.sqrt(m00 * m00 + m01 * m01 + m02 * m02),
                Math.sqrt(m10 * m10 + m11 * m11 + m12 * m12),
                Math.sqrt(m20 * m20 + m21 * m21 + m22 * m22));
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

    public Matrix4x3dc get(Matrix4x3dc dest) {
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

    //#ifdef __HAS_NIO__

    public DoubleBuffer get(DoubleBuffer buffer) {
        return get(buffer.position(), buffer);
    }

    public DoubleBuffer get(int index, DoubleBuffer buffer) {
        MemUtil.INSTANCE.put(this, index, buffer);
        return buffer;
    }

    public FloatBuffer get(FloatBuffer buffer) {
        return get(buffer.position(), buffer);
    }

    public FloatBuffer get(int index, FloatBuffer buffer) {
        MemUtil.INSTANCE.putf(this, index, buffer);
        return buffer;
    }

    public ByteBuffer get(ByteBuffer buffer) {
        return get(buffer.position(), buffer);
    }

    public ByteBuffer get(int index, ByteBuffer buffer) {
        MemUtil.INSTANCE.put(this, index, buffer);
        return buffer;
    }

    public ByteBuffer getFloats(ByteBuffer buffer) {
        return getFloats(buffer.position(), buffer);
    }

    public ByteBuffer getFloats(int index, ByteBuffer buffer) {
        MemUtil.INSTANCE.putf(this, index, buffer);
        return buffer;
    }
    //#endif

    public double[] get(double[] arr, int offset) {
        arr[offset + 0] = m00;
        arr[offset + 1] = m01;
        arr[offset + 2] = m02;
        arr[offset + 3] = m10;
        arr[offset + 4] = m11;
        arr[offset + 5] = m12;
        arr[offset + 6] = m20;
        arr[offset + 7] = m21;
        arr[offset + 8] = m22;
        arr[offset + 9] = m30;
        arr[offset + 10] = m31;
        arr[offset + 11] = m32;
        return arr;
    }

    public double[] get(double[] arr) {
        return get(arr, 0);
    }

    public float[] get(float[] arr, int offset) {
        arr[offset + 0] = (float) m00;
        arr[offset + 1] = (float) m01;
        arr[offset + 2] = (float) m02;
        arr[offset + 3] = (float) m10;
        arr[offset + 4] = (float) m11;
        arr[offset + 5] = (float) m12;
        arr[offset + 6] = (float) m20;
        arr[offset + 7] = (float) m21;
        arr[offset + 8] = (float) m22;
        arr[offset + 9] = (float) m30;
        arr[offset + 10] = (float) m31;
        arr[offset + 11] = (float) m32;
        return arr;
    }

    public float[] get(float[] arr) {
        return get(arr, 0);
    }

    //#ifdef __HAS_NIO__

    public DoubleBuffer get4x4(DoubleBuffer buffer) {
        return get4x4(buffer.position(), buffer);
    }

    public DoubleBuffer get4x4(int index, DoubleBuffer buffer) {
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

    public DoubleBuffer getTransposed(DoubleBuffer buffer) {
        return getTransposed(buffer.position(), buffer);
    }

    public DoubleBuffer getTransposed(int index, DoubleBuffer buffer) {
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

    public FloatBuffer getTransposed(FloatBuffer buffer) {
        return getTransposed(buffer.position(), buffer);
    }

    public FloatBuffer getTransposed(int index, FloatBuffer buffer) {
        MemUtil.INSTANCE.putfTransposed(this, index, buffer);
        return buffer;
    }

    public ByteBuffer getTransposedFloats(ByteBuffer buffer) {
        return getTransposed(buffer.position(), buffer);
    }

    public ByteBuffer getTransposedFloats(int index, ByteBuffer buffer) {
        MemUtil.INSTANCE.putfTransposed(this, index, buffer);
        return buffer;
    }
    //#endif

    public double[] getTransposed(double[] arr, int offset) {
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

    public double[] getTransposed(double[] arr) {
        return getTransposed(arr, 0);
    }

    public Matrix4x3dc zero() {
        m00 = 0.0;
        m01 = 0.0;
        m02 = 0.0;
        m10 = 0.0;
        m11 = 0.0;
        m12 = 0.0;
        m20 = 0.0;
        m21 = 0.0;
        m22 = 0.0;
        m30 = 0.0;
        m31 = 0.0;
        m32 = 0.0;
        properties = 0;
        return this;
    }

    public Matrix4x3dc scaling(double factor) {
        return scaling(factor, factor, factor);
    }

    public Matrix4x3dc scaling(double x, double y, double z) {
        m00 = x;
        m01 = 0.0;
        m02 = 0.0;
        m10 = 0.0;
        m11 = y;
        m12 = 0.0;
        m20 = 0.0;
        m21 = 0.0;
        m22 = z;
        m30 = 0.0;
        m31 = 0.0;
        m32 = 0.0;
        properties = 0;
        return this;
    }

    public Matrix4x3dc scaling(IVector3d xyz) {
        return scaling(xyz.x(), xyz.y(), xyz.z());
    }

    public Matrix4x3dc rotation(double angle, double x, double y, double z) {
        double sin = Math.sin(angle);
        double cos = Math.cosFromSin(sin, angle);
        double C = 1.0 - cos;
        double xy = x * y, xz = x * z, yz = y * z;
        m00 = cos + x * x * C;
        m10 = xy * C - z * sin;
        m20 = xz * C + y * sin;
        m30 = 0.0;
        m01 = xy * C + z * sin;
        m11 = cos + y * y * C;
        m21 = yz * C - x * sin;
        m31 = 0.0;
        m02 = xz * C - y * sin;
        m12 = yz * C + x * sin;
        m22 = cos + z * z * C;
        m32 = 0.0;
        properties = 0;
        return this;
    }

    public Matrix4x3dc rotationX(double ang) {
        double sin, cos;
        if (ang == Math.PI || ang == -Math.PI) {
            cos = -1.0;
            sin = 0.0;
        } else if (ang == Math.PI * 0.5 || ang == -Math.PI * 1.5) {
            cos = 0.0;
            sin = 1.0;
        } else if (ang == -Math.PI * 0.5 || ang == Math.PI * 1.5) {
            cos = 0.0;
            sin = -1.0;
        } else {
            sin = Math.sin(ang);
            cos = Math.cosFromSin(sin, ang);
        }
        m00 = 1.0;
        m01 = 0.0;
        m02 = 0.0;
        m10 = 0.0;
        m11 = cos;
        m12 = sin;
        m20 = 0.0;
        m21 = -sin;
        m22 = cos;
        m30 = 0.0;
        m31 = 0.0;
        m32 = 0.0;
        properties = 0;
        return this;
    }

    public Matrix4x3dc rotationY(double ang) {
        double sin, cos;
        if (ang == Math.PI || ang == -Math.PI) {
            cos = -1.0;
            sin = 0.0;
        } else if (ang == Math.PI * 0.5 || ang == -Math.PI * 1.5) {
            cos = 0.0;
            sin = 1.0;
        } else if (ang == -Math.PI * 0.5 || ang == Math.PI * 1.5) {
            cos = 0.0;
            sin = -1.0;
        } else {
            sin = Math.sin(ang);
            cos = Math.cosFromSin(sin, ang);
        }
        m00 = cos;
        m01 = 0.0;
        m02 = -sin;
        m10 = 0.0;
        m11 = 1.0;
        m12 = 0.0;
        m20 = sin;
        m21 = 0.0;
        m22 = cos;
        m30 = 0.0;
        m31 = 0.0;
        m32 = 0.0;
        properties = 0;
        return this;
    }

    public Matrix4x3dc rotationZ(double ang) {
        double sin, cos;
        if (ang == Math.PI || ang == -Math.PI) {
            cos = -1.0;
            sin = 0.0;
        } else if (ang == Math.PI * 0.5 || ang == -Math.PI * 1.5) {
            cos = 0.0;
            sin = 1.0;
        } else if (ang == -Math.PI * 0.5 || ang == Math.PI * 1.5) {
            cos = 0.0;
            sin = -1.0;
        } else {
            sin = Math.sin(ang);
            cos = Math.cosFromSin(sin, ang);
        }
        m00 = cos;
        m01 = sin;
        m02 = 0.0;
        m10 = -sin;
        m11 = cos;
        m12 = 0.0;
        m20 = 0.0;
        m21 = 0.0;
        m22 = 1.0;
        m30 = 0.0;
        m31 = 0.0;
        m32 = 0.0;
        properties = 0;
        return this;
    }

    public Matrix4x3dc rotationXYZ(double angleX, double angleY, double angleZ) {
        double sinX = Math.sin(angleX);
        double cosX = Math.cosFromSin(sinX, angleX);
        double sinY = Math.sin(angleY);
        double cosY = Math.cosFromSin(sinY, angleY);
        double sinZ = Math.sin(angleZ);
        double cosZ = Math.cosFromSin(sinZ, angleZ);
        double m_sinX = -sinX;
        double m_sinY = -sinY;
        double m_sinZ = -sinZ;

        // rotateX
        double nm11 = cosX;
        double nm12 = sinX;
        double nm21 = m_sinX;
        double nm22 = cosX;
        // rotateY
        double nm00 = cosY;
        double nm01 = nm21 * m_sinY;
        double nm02 = nm22 * m_sinY;
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
        m30 = 0.0;
        m31 = 0.0;
        m32 = 0.0;
        properties = 0;
        return this;
    }

    public Matrix4x3dc rotationZYX(double angleZ, double angleY, double angleX) {
        double sinX = Math.sin(angleX);
        double cosX = Math.cosFromSin(sinX, angleX);
        double sinY = Math.sin(angleY);
        double cosY = Math.cosFromSin(sinY, angleY);
        double sinZ = Math.sin(angleZ);
        double cosZ = Math.cosFromSin(sinZ, angleZ);
        double m_sinZ = -sinZ;
        double m_sinY = -sinY;
        double m_sinX = -sinX;

        // rotateZ
        double nm00 = cosZ;
        double nm01 = sinZ;
        double nm10 = m_sinZ;
        double nm11 = cosZ;
        // rotateY
        double nm20 = nm00 * sinY;
        double nm21 = nm01 * sinY;
        double nm22 = cosY;
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
        m30 = 0.0;
        m31 = 0.0;
        m32 = 0.0;
        properties = 0;
        return this;
    }

    public Matrix4x3dc rotationYXZ(double angleY, double angleX, double angleZ) {
        double sinX = Math.sin(angleX);
        double cosX = Math.cosFromSin(sinX, angleX);
        double sinY = Math.sin(angleY);
        double cosY = Math.cosFromSin(sinY, angleY);
        double sinZ = Math.sin(angleZ);
        double cosZ = Math.cosFromSin(sinZ, angleZ);
        double m_sinY = -sinY;
        double m_sinX = -sinX;
        double m_sinZ = -sinZ;

        // rotateY
        double nm00 = cosY;
        double nm02 = m_sinY;
        double nm20 = sinY;
        double nm22 = cosY;
        // rotateX
        double nm10 = nm20 * sinX;
        double nm11 = cosX;
        double nm12 = nm22 * sinX;
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
        m30 = 0.0;
        m31 = 0.0;
        m32 = 0.0;
        properties = 0;
        return this;
    }

    public Matrix4x3dc setRotationXYZ(double angleX, double angleY, double angleZ) {
        double sinX = Math.sin(angleX);
        double cosX = Math.cosFromSin(sinX, angleX);
        double sinY = Math.sin(angleY);
        double cosY = Math.cosFromSin(sinY, angleY);
        double sinZ = Math.sin(angleZ);
        double cosZ = Math.cosFromSin(sinZ, angleZ);
        double m_sinX = -sinX;
        double m_sinY = -sinY;
        double m_sinZ = -sinZ;

        // rotateX
        double nm11 = cosX;
        double nm12 = sinX;
        double nm21 = m_sinX;
        double nm22 = cosX;
        // rotateY
        double nm00 = cosY;
        double nm01 = nm21 * m_sinY;
        double nm02 = nm22 * m_sinY;
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

    public Matrix4x3dc setRotationZYX(double angleZ, double angleY, double angleX) {
        double sinX = Math.sin(angleX);
        double cosX = Math.cosFromSin(sinX, angleX);
        double sinY = Math.sin(angleY);
        double cosY = Math.cosFromSin(sinY, angleY);
        double sinZ = Math.sin(angleZ);
        double cosZ = Math.cosFromSin(sinZ, angleZ);
        double m_sinZ = -sinZ;
        double m_sinY = -sinY;
        double m_sinX = -sinX;

        // rotateZ
        double nm00 = cosZ;
        double nm01 = sinZ;
        double nm10 = m_sinZ;
        double nm11 = cosZ;
        // rotateY
        double nm20 = nm00 * sinY;
        double nm21 = nm01 * sinY;
        double nm22 = cosY;
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

    public Matrix4x3dc setRotationYXZ(double angleY, double angleX, double angleZ) {
        double sinX = Math.sin(angleX);
        double cosX = Math.cosFromSin(sinX, angleX);
        double sinY = Math.sin(angleY);
        double cosY = Math.cosFromSin(sinY, angleY);
        double sinZ = Math.sin(angleZ);
        double cosZ = Math.cosFromSin(sinZ, angleZ);
        double m_sinY = -sinY;
        double m_sinX = -sinX;
        double m_sinZ = -sinZ;

        // rotateY
        double nm00 = cosY;
        double nm02 = m_sinY;
        double nm20 = sinY;
        double nm22 = cosY;
        // rotateX
        double nm10 = nm20 * sinX;
        double nm11 = cosX;
        double nm12 = nm22 * sinX;
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

    public Matrix4x3dc rotation(double angle, IVector3d axis) {
        return rotation(angle, axis.x(), axis.y(), axis.z());
    }

    public Matrix4x3dc rotation(double angle, IVector3f axis) {
        return rotation(angle, axis.x(), axis.y(), axis.z());
    }

    public Vector4dc transform(Vector4dc v) {
        return v.mul(this);
    }

    public Vector4dc transform(IVector4d v, Vector4dc dest) {
        return v.mul(this, dest);
    }

    public Vector3dc transformPosition(Vector3dc v) {
        v.set(m00 * v.x() + m10 * v.y() + m20 * v.z() + m30,
                m01 * v.x() + m11 * v.y() + m21 * v.z() + m31,
                m02 * v.x() + m12 * v.y() + m22 * v.z() + m32);
        return v;
    }

    public Vector3dc transformPosition(IVector3d v, Vector3dc dest) {
        dest.set(m00 * v.x() + m10 * v.y() + m20 * v.z() + m30,
                m01 * v.x() + m11 * v.y() + m21 * v.z() + m31,
                m02 * v.x() + m12 * v.y() + m22 * v.z() + m32);
        return dest;
    }

    public Vector3dc transformDirection(Vector3dc v) {
        v.set(m00 * v.x() + m10 * v.y() + m20 * v.z(),
                m01 * v.x() + m11 * v.y() + m21 * v.z(),
                m02 * v.x() + m12 * v.y() + m22 * v.z());
        return v;
    }

    public Vector3dc transformDirection(IVector3d v, Vector3dc dest) {
        dest.set(m00 * v.x() + m10 * v.y() + m20 * v.z(),
                m01 * v.x() + m11 * v.y() + m21 * v.z(),
                m02 * v.x() + m12 * v.y() + m22 * v.z());
        return dest;
    }

    public Matrix4x3dc set3x3(IMatrix3d mat) {
        m00 = mat.m00();
        m01 = mat.m01();
        m02 = mat.m02();
        m10 = mat.m10();
        m11 = mat.m11();
        m12 = mat.m12();
        m20 = mat.m20();
        m21 = mat.m21();
        m22 = mat.m22();
        properties &= ~(PROPERTY_IDENTITY | PROPERTY_TRANSLATION);
        return this;
    }

    public Matrix4x3dc set3x3(IMatrix3f mat) {
        m00 = mat.m00();
        m01 = mat.m01();
        m02 = mat.m02();
        m10 = mat.m10();
        m11 = mat.m11();
        m12 = mat.m12();
        m20 = mat.m20();
        m21 = mat.m21();
        m22 = mat.m22();
        properties &= ~(PROPERTY_IDENTITY | PROPERTY_TRANSLATION);
        return this;
    }

    public Matrix4x3dc scale(IVector3d xyz, Matrix4x3dc dest) {
        return scale(xyz.x(), xyz.y(), xyz.z(), dest);
    }

    public Matrix4x3dc scale(IVector3d xyz) {
        return scale(xyz.x(), xyz.y(), xyz.z(), this);
    }

    public Matrix4x3dc scale(double x, double y, double z, Matrix4x3dc dest) {
        if ((properties & PROPERTY_IDENTITY) != 0)
            return dest.scaling(x, y, z);
        return scaleGeneric(x, y, z, dest);
    }

    private Matrix4x3dc scaleGeneric(double x, double y, double z, Matrix4x3dc dest) {
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

    public Matrix4x3dc scale(double x, double y, double z) {
        return scale(x, y, z, this);
    }

    public Matrix4x3dc scale(double xyz, Matrix4x3dc dest) {
        return scale(xyz, xyz, xyz, dest);
    }

    public Matrix4x3dc scale(double xyz) {
        return scale(xyz, xyz, xyz);
    }

    public Matrix4x3dc scaleLocal(double x, double y, double z, Matrix4x3dc dest) {
        if ((properties & PROPERTY_IDENTITY) != 0)
            return dest.scaling(x, y, z);

        double nm00 = x * m00;
        double nm01 = y * m01;
        double nm02 = z * m02;
        double nm10 = x * m10;
        double nm11 = y * m11;
        double nm12 = z * m12;
        double nm20 = x * m20;
        double nm21 = y * m21;
        double nm22 = z * m22;
        double nm30 = x * m30;
        double nm31 = y * m31;
        double nm32 = z * m32;
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

    public Matrix4x3dc scaleLocal(double x, double y, double z) {
        return scaleLocal(x, y, z, this);
    }

    public Matrix4x3dc rotate(double ang, double x, double y, double z, Matrix4x3dc dest) {
        if ((properties & PROPERTY_IDENTITY) != 0)
            return dest.rotation(ang, x, y, z);
        else if ((properties & PROPERTY_TRANSLATION) != 0)
            return rotateTranslation(ang, x, y, z, dest);
        return rotateGeneric(ang, x, y, z, dest);
    }

    private Matrix4x3dc rotateGeneric(double ang, double x, double y, double z, Matrix4x3dc dest) {
        double s = Math.sin(ang);
        double c = Math.cosFromSin(s, ang);
        double C = 1.0 - c;
        double xx = x * x, xy = x * y, xz = x * z;
        double yy = y * y, yz = y * z;
        double zz = z * z;
        double rm00 = xx * C + c;
        double rm01 = xy * C + z * s;
        double rm02 = xz * C - y * s;
        double rm10 = xy * C - z * s;
        double rm11 = yy * C + c;
        double rm12 = yz * C + x * s;
        double rm20 = xz * C + y * s;
        double rm21 = yz * C - x * s;
        double rm22 = zz * C + c;
        // add temporaries for dependent values
        double nm00 = m00 * rm00 + m10 * rm01 + m20 * rm02;
        double nm01 = m01 * rm00 + m11 * rm01 + m21 * rm02;
        double nm02 = m02 * rm00 + m12 * rm01 + m22 * rm02;
        double nm10 = m00 * rm10 + m10 * rm11 + m20 * rm12;
        double nm11 = m01 * rm10 + m11 * rm11 + m21 * rm12;
        double nm12 = m02 * rm10 + m12 * rm11 + m22 * rm12;
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
        dest.m30(m30);
        dest.m31(m31);
        dest.m32(m32);
        dest.properties((byte) (properties & ~(PROPERTY_IDENTITY | PROPERTY_TRANSLATION)));
        return dest;
    }

    public Matrix4x3dc rotate(double ang, double x, double y, double z) {
        return rotate(ang, x, y, z, this);
    }

    public Matrix4x3dc rotateTranslation(double ang, double x, double y, double z, Matrix4x3dc dest) {
        double s = Math.sin(ang);
        double c = Math.cosFromSin(s, ang);
        double C = 1.0 - c;
        double xx = x * x, xy = x * y, xz = x * z;
        double yy = y * y, yz = y * z;
        double zz = z * z;
        double rm00 = xx * C + c;
        double rm01 = xy * C + z * s;
        double rm02 = xz * C - y * s;
        double rm10 = xy * C - z * s;
        double rm11 = yy * C + c;
        double rm12 = yz * C + x * s;
        double rm20 = xz * C + y * s;
        double rm21 = yz * C - x * s;
        double rm22 = zz * C + c;
        double nm00 = rm00;
        double nm01 = rm01;
        double nm02 = rm02;
        double nm10 = rm10;
        double nm11 = rm11;
        double nm12 = rm12;
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

    public Matrix4x3dc rotateLocal(double ang, double x, double y, double z, Matrix4x3dc dest) {
        double s = Math.sin(ang);
        double c = Math.cosFromSin(s, ang);
        double C = 1.0 - c;
        double xx = x * x, xy = x * y, xz = x * z;
        double yy = y * y, yz = y * z;
        double zz = z * z;
        double lm00 = xx * C + c;
        double lm01 = xy * C + z * s;
        double lm02 = xz * C - y * s;
        double lm10 = xy * C - z * s;
        double lm11 = yy * C + c;
        double lm12 = yz * C + x * s;
        double lm20 = xz * C + y * s;
        double lm21 = yz * C - x * s;
        double lm22 = zz * C + c;
        double nm00 = lm00 * m00 + lm10 * m01 + lm20 * m02;
        double nm01 = lm01 * m00 + lm11 * m01 + lm21 * m02;
        double nm02 = lm02 * m00 + lm12 * m01 + lm22 * m02;
        double nm10 = lm00 * m10 + lm10 * m11 + lm20 * m12;
        double nm11 = lm01 * m10 + lm11 * m11 + lm21 * m12;
        double nm12 = lm02 * m10 + lm12 * m11 + lm22 * m12;
        double nm20 = lm00 * m20 + lm10 * m21 + lm20 * m22;
        double nm21 = lm01 * m20 + lm11 * m21 + lm21 * m22;
        double nm22 = lm02 * m20 + lm12 * m21 + lm22 * m22;
        double nm30 = lm00 * m30 + lm10 * m31 + lm20 * m32;
        double nm31 = lm01 * m30 + lm11 * m31 + lm21 * m32;
        double nm32 = lm02 * m30 + lm12 * m31 + lm22 * m32;
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

    public Matrix4x3dc rotateLocal(double ang, double x, double y, double z) {
        return rotateLocal(ang, x, y, z, this);
    }

    public Matrix4x3dc translate(IVector3d offset) {
        return translate(offset.x(), offset.y(), offset.z());
    }

    public Matrix4x3dc translate(IVector3d offset, Matrix4x3dc dest) {
        return translate(offset.x(), offset.y(), offset.z(), dest);
    }

    public Matrix4x3dc translate(IVector3f offset) {
        return translate(offset.x(), offset.y(), offset.z());
    }

    public Matrix4x3dc translate(IVector3f offset, Matrix4x3dc dest) {
        return translate(offset.x(), offset.y(), offset.z(), dest);
    }

    public Matrix4x3dc translate(double x, double y, double z, Matrix4x3dc dest) {
        if ((properties & PROPERTY_IDENTITY) != 0)
            return dest.translation(x, y, z);
        return translateGeneric(x, y, z, dest);
    }

    private Matrix4x3dc translateGeneric(double x, double y, double z, Matrix4x3dc dest) {
        dest.m00(m00);
        dest.m01(m01);
        dest.m02(m02);
        dest.m10(m10);
        dest.m11(m11);
        dest.m12(m12);
        dest.m20(m20);
        dest.m21(m21);
        dest.m22(m22);
        dest.m30(m00 * x + m10 * y + m20 * z + m30);
        dest.m31(m01 * x + m11 * y + m21 * z + m31);
        dest.m32(m02 * x + m12 * y + m22 * z + m32);
        dest.properties((byte) (properties & ~(PROPERTY_IDENTITY)));
        return dest;
    }

    public Matrix4x3dc translate(double x, double y, double z) {
        if ((properties & PROPERTY_IDENTITY) != 0)
            return translation(x, y, z);
        Matrix4x3dc c = this;
        c.m30(c.m00() * x + c.m10() * y + c.m20() * z + c.m30());
        c.m31(c.m01() * x + c.m11() * y + c.m21() * z + c.m31());
        c.m32(c.m02() * x + c.m12() * y + c.m22() * z + c.m32());
        c.properties(c.properties() & ~(PROPERTY_IDENTITY));
        return this;
    }

    public Matrix4x3dc translateLocal(IVector3f offset) {
        return translateLocal(offset.x(), offset.y(), offset.z());
    }

    public Matrix4x3dc translateLocal(IVector3f offset, Matrix4x3dc dest) {
        return translateLocal(offset.x(), offset.y(), offset.z(), dest);
    }

    public Matrix4x3dc translateLocal(IVector3d offset) {
        return translateLocal(offset.x(), offset.y(), offset.z());
    }

    public Matrix4x3dc translateLocal(IVector3d offset, Matrix4x3dc dest) {
        return translateLocal(offset.x(), offset.y(), offset.z(), dest);
    }

    public Matrix4x3dc translateLocal(double x, double y, double z, Matrix4x3dc dest) {
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

    public Matrix4x3dc translateLocal(double x, double y, double z) {
        return translateLocal(x, y, z, this);
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeDouble(m00);
        out.writeDouble(m01);
        out.writeDouble(m02);
        out.writeDouble(m10);
        out.writeDouble(m11);
        out.writeDouble(m12);
        out.writeDouble(m20);
        out.writeDouble(m21);
        out.writeDouble(m22);
        out.writeDouble(m30);
        out.writeDouble(m31);
        out.writeDouble(m32);
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        m00 = in.readDouble();
        m01 = in.readDouble();
        m02 = in.readDouble();
        m10 = in.readDouble();
        m11 = in.readDouble();
        m12 = in.readDouble();
        m20 = in.readDouble();
        m21 = in.readDouble();
        m22 = in.readDouble();
        m30 = in.readDouble();
        m31 = in.readDouble();
        m32 = in.readDouble();
    }

    public Matrix4x3dc rotateX(double ang, Matrix4x3dc dest) {
        if ((properties & PROPERTY_IDENTITY) != 0)
            return dest.rotationX(ang);

        double sin, cos;
        if (ang == Math.PI || ang == -Math.PI) {
            cos = -1.0;
            sin = 0.0;
        } else if (ang == Math.PI * 0.5 || ang == -Math.PI * 1.5) {
            cos = 0.0;
            sin = 1.0;
        } else if (ang == -Math.PI * 0.5 || ang == Math.PI * 1.5) {
            cos = 0.0;
            sin = -1.0;
        } else {
            sin = Math.sin(ang);
            cos = Math.cosFromSin(sin, ang);
        }
        double rm11 = cos;
        double rm12 = sin;
        double rm21 = -sin;
        double rm22 = cos;

        // add temporaries for dependent values
        double nm10 = m10 * rm11 + m20 * rm12;
        double nm11 = m11 * rm11 + m21 * rm12;
        double nm12 = m12 * rm11 + m22 * rm12;
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

    public Matrix4x3dc rotateX(double ang) {
        return rotateX(ang, this);
    }

    public Matrix4x3dc rotateY(double ang, Matrix4x3dc dest) {
        if ((properties & PROPERTY_IDENTITY) != 0)
            return dest.rotationY(ang);

        double sin, cos;
        if (ang == Math.PI || ang == -Math.PI) {
            cos = -1.0;
            sin = 0.0;
        } else if (ang == Math.PI * 0.5 || ang == -Math.PI * 1.5) {
            cos = 0.0;
            sin = 1.0;
        } else if (ang == -Math.PI * 0.5 || ang == Math.PI * 1.5) {
            cos = 0.0;
            sin = -1.0;
        } else {
            sin = Math.sin(ang);
            cos = Math.cosFromSin(sin, ang);
        }
        double rm00 = cos;
        double rm02 = -sin;
        double rm20 = sin;
        double rm22 = cos;

        // add temporaries for dependent values
        double nm00 = m00 * rm00 + m20 * rm02;
        double nm01 = m01 * rm00 + m21 * rm02;
        double nm02 = m02 * rm00 + m22 * rm02;
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

    public Matrix4x3dc rotateY(double ang) {
        return rotateY(ang, this);
    }

    public Matrix4x3dc rotateZ(double ang, Matrix4x3dc dest) {
        if ((properties & PROPERTY_IDENTITY) != 0)
            return dest.rotationZ(ang);

        double sin, cos;
        if (ang == Math.PI || ang == -Math.PI) {
            cos = -1.0;
            sin = 0.0;
        } else if (ang == Math.PI * 0.5 || ang == -Math.PI * 1.5) {
            cos = 0.0;
            sin = 1.0;
        } else if (ang == -Math.PI * 0.5 || ang == Math.PI * 1.5) {
            cos = 0.0;
            sin = -1.0;
        } else {
            sin = Math.sin(ang);
            cos = Math.cosFromSin(sin, ang);
        }
        double rm00 = cos;
        double rm01 = sin;
        double rm10 = -sin;
        double rm11 = cos;

        // add temporaries for dependent values
        double nm00 = m00 * rm00 + m10 * rm01;
        double nm01 = m01 * rm00 + m11 * rm01;
        double nm02 = m02 * rm00 + m12 * rm01;
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

    public Matrix4x3dc rotateZ(double ang) {
        return rotateZ(ang, this);
    }

    public Matrix4x3dc rotateXYZ(Vector3dc angles) {
        return rotateXYZ(angles.x(), angles.y(), angles.z());
    }

    public Matrix4x3dc rotateXYZ(double angleX, double angleY, double angleZ) {
        return rotateXYZ(angleX, angleY, angleZ, this);
    }

    public Matrix4x3dc rotateXYZ(double angleX, double angleY, double angleZ, Matrix4x3dc dest) {
        if ((properties & PROPERTY_IDENTITY) != 0)
            return dest.rotationXYZ(angleX, angleY, angleZ);

        double sinX = Math.sin(angleX);
        double cosX = Math.cosFromSin(sinX, angleX);
        double sinY = Math.sin(angleY);
        double cosY = Math.cosFromSin(sinY, angleY);
        double sinZ = Math.sin(angleZ);
        double cosZ = Math.cosFromSin(sinZ, angleZ);
        double m_sinX = -sinX;
        double m_sinY = -sinY;
        double m_sinZ = -sinZ;

        // rotateX
        double nm10 = m10 * cosX + m20 * sinX;
        double nm11 = m11 * cosX + m21 * sinX;
        double nm12 = m12 * cosX + m22 * sinX;
        double nm20 = m10 * m_sinX + m20 * cosX;
        double nm21 = m11 * m_sinX + m21 * cosX;
        double nm22 = m12 * m_sinX + m22 * cosX;
        // rotateY
        double nm00 = m00 * cosY + nm20 * m_sinY;
        double nm01 = m01 * cosY + nm21 * m_sinY;
        double nm02 = m02 * cosY + nm22 * m_sinY;
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

    public Matrix4x3dc rotateZYX(Vector3dc angles) {
        return rotateZYX(angles.z(), angles.y(), angles.x());
    }

    public Matrix4x3dc rotateZYX(double angleZ, double angleY, double angleX) {
        return rotateZYX(angleZ, angleY, angleX, this);
    }

    public Matrix4x3dc rotateZYX(double angleZ, double angleY, double angleX, Matrix4x3dc dest) {
        if ((properties & PROPERTY_IDENTITY) != 0)
            return dest.rotationZYX(angleZ, angleY, angleX);

        double sinX = Math.sin(angleX);
        double cosX = Math.cosFromSin(sinX, angleX);
        double sinY = Math.sin(angleY);
        double cosY = Math.cosFromSin(sinY, angleY);
        double sinZ = Math.sin(angleZ);
        double cosZ = Math.cosFromSin(sinZ, angleZ);
        double m_sinZ = -sinZ;
        double m_sinY = -sinY;
        double m_sinX = -sinX;

        // rotateZ
        double nm00 = m00 * cosZ + m10 * sinZ;
        double nm01 = m01 * cosZ + m11 * sinZ;
        double nm02 = m02 * cosZ + m12 * sinZ;
        double nm10 = m00 * m_sinZ + m10 * cosZ;
        double nm11 = m01 * m_sinZ + m11 * cosZ;
        double nm12 = m02 * m_sinZ + m12 * cosZ;
        // rotateY
        double nm20 = nm00 * sinY + m20 * cosY;
        double nm21 = nm01 * sinY + m21 * cosY;
        double nm22 = nm02 * sinY + m22 * cosY;
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

    public Matrix4x3dc rotateYXZ(Vector3dc angles) {
        return rotateYXZ(angles.y(), angles.x(), angles.z());
    }

    public Matrix4x3dc rotateYXZ(double angleY, double angleX, double angleZ) {
        return rotateYXZ(angleY, angleX, angleZ, this);
    }

    public Matrix4x3dc rotateYXZ(double angleY, double angleX, double angleZ, Matrix4x3dc dest) {
        if ((properties & PROPERTY_IDENTITY) != 0)
            return dest.rotationYXZ(angleY, angleX, angleZ);

        double sinX = Math.sin(angleX);
        double cosX = Math.cosFromSin(sinX, angleX);
        double sinY = Math.sin(angleY);
        double cosY = Math.cosFromSin(sinY, angleY);
        double sinZ = Math.sin(angleZ);
        double cosZ = Math.cosFromSin(sinZ, angleZ);
        double m_sinY = -sinY;
        double m_sinX = -sinX;
        double m_sinZ = -sinZ;

        // rotateY
        double nm20 = m00 * sinY + m20 * cosY;
        double nm21 = m01 * sinY + m21 * cosY;
        double nm22 = m02 * sinY + m22 * cosY;
        double nm00 = m00 * cosY + m20 * m_sinY;
        double nm01 = m01 * cosY + m21 * m_sinY;
        double nm02 = m02 * cosY + m22 * m_sinY;
        // rotateX
        double nm10 = m10 * cosX + nm20 * sinX;
        double nm11 = m11 * cosX + nm21 * sinX;
        double nm12 = m12 * cosX + nm22 * sinX;
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

    public Matrix4x3dc rotation(AxisAngle4fc angleAxis) {
        return rotation(angleAxis.angle(), angleAxis.x(), angleAxis.y(), angleAxis.z());
    }

    public Matrix4x3dc rotation(AxisAngle4dc angleAxis) {
        return rotation(angleAxis.angle(), angleAxis.x(), angleAxis.y(), angleAxis.z());
    }

    public Matrix4x3dc rotation(IQuaterniond quat) {
        double w2 = quat.w() * quat.w();
        double x2 = quat.x() * quat.x();
        double y2 = quat.y() * quat.y();
        double z2 = quat.z() * quat.z();
        double zw = quat.z() * quat.w();
        double xy = quat.x() * quat.y();
        double xz = quat.x() * quat.z();
        double yw = quat.y() * quat.w();
        double yz = quat.y() * quat.z();
        double xw = quat.x() * quat.w();
        m00 = w2 + x2 - z2 - y2;
        m01 = xy + zw + zw + xy;
        m02 = xz - yw + xz - yw;
        m10 = -zw + xy - zw + xy;
        m11 = y2 - z2 + w2 - x2;
        m12 = yz + yz + xw + xw;
        m20 = yw + xz + xz + yw;
        m21 = yz + yz - xw - xw;
        m22 = z2 - y2 - x2 + w2;
        m30 = 0.0f;
        m31 = 0.0f;
        m32 = 0.0f;
        properties = 0;
        return this;
    }

    public Matrix4x3dc rotation(IQuaternionf quat) {
        double w2 = quat.w() * quat.w();
        double x2 = quat.x() * quat.x();
        double y2 = quat.y() * quat.y();
        double z2 = quat.z() * quat.z();
        double zw = quat.z() * quat.w();
        double xy = quat.x() * quat.y();
        double xz = quat.x() * quat.z();
        double yw = quat.y() * quat.w();
        double yz = quat.y() * quat.z();
        double xw = quat.x() * quat.w();
        m00 = w2 + x2 - z2 - y2;
        m01 = xy + zw + zw + xy;
        m02 = xz - yw + xz - yw;
        m10 = -zw + xy - zw + xy;
        m11 = y2 - z2 + w2 - x2;
        m12 = yz + yz + xw + xw;
        m20 = yw + xz + xz + yw;
        m21 = yz + yz - xw - xw;
        m22 = z2 - y2 - x2 + w2;
        m30 = 0.0f;
        m31 = 0.0f;
        m32 = 0.0f;
        properties = 0;
        return this;
    }

    public Matrix4x3dc translationRotateScale(double tx, double ty, double tz,
                                              double qx, double qy, double qz, double qw,
                                              double sx, double sy, double sz) {
        double dqx = qx + qx, dqy = qy + qy, dqz = qz + qz;
        double q00 = dqx * qx;
        double q11 = dqy * qy;
        double q22 = dqz * qz;
        double q01 = dqx * qy;
        double q02 = dqx * qz;
        double q03 = dqx * qw;
        double q12 = dqy * qz;
        double q13 = dqy * qw;
        double q23 = dqz * qw;
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

    public Matrix4x3dc translationRotateScale(IVector3f translation,
                                              IQuaternionf quat,
                                              IVector3f scale) {
        return translationRotateScale(translation.x(), translation.y(), translation.z(), quat.x(), quat.y(), quat.z(), quat.w(), scale.x(), scale.y(), scale.z());
    }

    public Matrix4x3dc translationRotateScale(IVector3d translation,
                                              IQuaterniond quat,
                                              IVector3d scale) {
        return translationRotateScale(translation.x(), translation.y(), translation.z(), quat.x(), quat.y(), quat.z(), quat.w(), scale.x(), scale.y(), scale.z());
    }

    public Matrix4x3dc translationRotateScaleMul(
            double tx, double ty, double tz,
            double qx, double qy, double qz, double qw,
            double sx, double sy, double sz,
            IMatrix4x3d m) {
        double dqx = qx + qx;
        double dqy = qy + qy;
        double dqz = qz + qz;
        double q00 = dqx * qx;
        double q11 = dqy * qy;
        double q22 = dqz * qz;
        double q01 = dqx * qy;
        double q02 = dqx * qz;
        double q03 = dqx * qw;
        double q12 = dqy * qz;
        double q13 = dqy * qw;
        double q23 = dqz * qw;
        double nm00 = sx - (q11 + q22) * sx;
        double nm01 = (q01 + q23) * sx;
        double nm02 = (q02 - q13) * sx;
        double nm10 = (q01 - q23) * sy;
        double nm11 = sy - (q22 + q00) * sy;
        double nm12 = (q12 + q03) * sy;
        double nm20 = (q02 + q13) * sz;
        double nm21 = (q12 - q03) * sz;
        double nm22 = sz - (q11 + q00) * sz;
        double m00 = nm00 * m.m00() + nm10 * m.m01() + nm20 * m.m02();
        double m01 = nm01 * m.m00() + nm11 * m.m01() + nm21 * m.m02();
        m02 = nm02 * m.m00() + nm12 * m.m01() + nm22 * m.m02();
        this.m00 = m00;
        this.m01 = m01;
        double m10 = nm00 * m.m10() + nm10 * m.m11() + nm20 * m.m12();
        double m11 = nm01 * m.m10() + nm11 * m.m11() + nm21 * m.m12();
        m12 = nm02 * m.m10() + nm12 * m.m11() + nm22 * m.m12();
        this.m10 = m10;
        this.m11 = m11;
        double m20 = nm00 * m.m20() + nm10 * m.m21() + nm20 * m.m22();
        double m21 = nm01 * m.m20() + nm11 * m.m21() + nm21 * m.m22();
        m22 = nm02 * m.m20() + nm12 * m.m21() + nm22 * m.m22();
        this.m20 = m20;
        this.m21 = m21;
        double m30 = nm00 * m.m30() + nm10 * m.m31() + nm20 * m.m32() + tx;
        double m31 = nm01 * m.m30() + nm11 * m.m31() + nm21 * m.m32() + ty;
        m32 = nm02 * m.m30() + nm12 * m.m31() + nm22 * m.m32() + tz;
        this.m30 = m30;
        this.m31 = m31;
        properties = 0;
        return this;
    }

    public Matrix4x3dc translationRotateScaleMul(IVector3d translation, IQuaterniond quat, IVector3d scale, IMatrix4x3d m) {
        return translationRotateScaleMul(translation.x(), translation.y(), translation.z(), quat.x(), quat.y(), quat.z(), quat.w(), scale.x(), scale.y(), scale.z(), m);
    }

    public Matrix4x3dc translationRotate(double tx, double ty, double tz, IQuaterniond quat) {
        double dqx = quat.x() + quat.x(), dqy = quat.y() + quat.y(), dqz = quat.z() + quat.z();
        double q00 = dqx * quat.x();
        double q11 = dqy * quat.y();
        double q22 = dqz * quat.z();
        double q01 = dqx * quat.y();
        double q02 = dqx * quat.z();
        double q03 = dqx * quat.w();
        double q12 = dqy * quat.z();
        double q13 = dqy * quat.w();
        double q23 = dqz * quat.w();
        m00 = 1.0 - (q11 + q22);
        m01 = q01 + q23;
        m02 = q02 - q13;
        m10 = q01 - q23;
        m11 = 1.0 - (q22 + q00);
        m12 = q12 + q03;
        m20 = q02 + q13;
        m21 = q12 - q03;
        m22 = 1.0 - (q11 + q00);
        m30 = tx;
        m31 = ty;
        m32 = tz;
        properties = 0;
        return this;
    }

    public Matrix4x3dc translationRotateMul(double tx, double ty, double tz, IQuaternionf quat, IMatrix4x3d mat) {
        return translationRotateMul(tx, ty, tz, quat.x(), quat.y(), quat.z(), quat.w(), mat);
    }

    public Matrix4x3dc translationRotateMul(double tx, double ty, double tz, double qx, double qy, double qz, double qw, IMatrix4x3d mat) {
        double w2 = qw * qw;
        double x2 = qx * qx;
        double y2 = qy * qy;
        double z2 = qz * qz;
        double zw = qz * qw;
        double xy = qx * qy;
        double xz = qx * qz;
        double yw = qy * qw;
        double yz = qy * qz;
        double xw = qx * qw;
        double nm00 = w2 + x2 - z2 - y2;
        double nm01 = xy + zw + zw + xy;
        double nm02 = xz - yw + xz - yw;
        double nm10 = -zw + xy - zw + xy;
        double nm11 = y2 - z2 + w2 - x2;
        double nm12 = yz + yz + xw + xw;
        double nm20 = yw + xz + xz + yw;
        double nm21 = yz + yz - xw - xw;
        double nm22 = z2 - y2 - x2 + w2;
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

    public Matrix4x3dc rotate(IQuaterniond quat, Matrix4x3dc dest) {
        if ((properties & PROPERTY_IDENTITY) != 0)
            return dest.rotation(quat);
        else if ((properties & PROPERTY_TRANSLATION) != 0)
            return rotateTranslation(quat, dest);
        return rotateGeneric(quat, dest);
    }

    private Matrix4x3dc rotateGeneric(IQuaterniond quat, Matrix4x3dc dest) {
        double w2 = quat.w() * quat.w();
        double x2 = quat.x() * quat.x();
        double y2 = quat.y() * quat.y();
        double z2 = quat.z() * quat.z();
        double zw = quat.z() * quat.w();
        double xy = quat.x() * quat.y();
        double xz = quat.x() * quat.z();
        double yw = quat.y() * quat.w();
        double yz = quat.y() * quat.z();
        double xw = quat.x() * quat.w();
        double rm00 = w2 + x2 - z2 - y2;
        double rm01 = xy + zw + zw + xy;
        double rm02 = xz - yw + xz - yw;
        double rm10 = -zw + xy - zw + xy;
        double rm11 = y2 - z2 + w2 - x2;
        double rm12 = yz + yz + xw + xw;
        double rm20 = yw + xz + xz + yw;
        double rm21 = yz + yz - xw - xw;
        double rm22 = z2 - y2 - x2 + w2;
        double nm00 = m00 * rm00 + m10 * rm01 + m20 * rm02;
        double nm01 = m01 * rm00 + m11 * rm01 + m21 * rm02;
        double nm02 = m02 * rm00 + m12 * rm01 + m22 * rm02;
        double nm10 = m00 * rm10 + m10 * rm11 + m20 * rm12;
        double nm11 = m01 * rm10 + m11 * rm11 + m21 * rm12;
        double nm12 = m02 * rm10 + m12 * rm11 + m22 * rm12;
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

    public Matrix4x3dc rotate(IQuaternionf quat, Matrix4x3dc dest) {
        if ((properties & PROPERTY_IDENTITY) != 0)
            return dest.rotation(quat);
        else if ((properties & PROPERTY_TRANSLATION) != 0)
            return rotateTranslation(quat, dest);
        return rotateGeneric(quat, dest);
    }

    private Matrix4x3dc rotateGeneric(IQuaternionf quat, Matrix4x3dc dest) {
        double w2 = quat.w() * quat.w();
        double x2 = quat.x() * quat.x();
        double y2 = quat.y() * quat.y();
        double z2 = quat.z() * quat.z();
        double zw = quat.z() * quat.w();
        double xy = quat.x() * quat.y();
        double xz = quat.x() * quat.z();
        double yw = quat.y() * quat.w();
        double yz = quat.y() * quat.z();
        double xw = quat.x() * quat.w();
        double rm00 = w2 + x2 - z2 - y2;
        double rm01 = xy + zw + zw + xy;
        double rm02 = xz - yw + xz - yw;
        double rm10 = -zw + xy - zw + xy;
        double rm11 = y2 - z2 + w2 - x2;
        double rm12 = yz + yz + xw + xw;
        double rm20 = yw + xz + xz + yw;
        double rm21 = yz + yz - xw - xw;
        double rm22 = z2 - y2 - x2 + w2;
        double nm00 = m00 * rm00 + m10 * rm01 + m20 * rm02;
        double nm01 = m01 * rm00 + m11 * rm01 + m21 * rm02;
        double nm02 = m02 * rm00 + m12 * rm01 + m22 * rm02;
        double nm10 = m00 * rm10 + m10 * rm11 + m20 * rm12;
        double nm11 = m01 * rm10 + m11 * rm11 + m21 * rm12;
        double nm12 = m02 * rm10 + m12 * rm11 + m22 * rm12;
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

    public Matrix4x3dc rotate(IQuaterniond quat) {
        return rotate(quat, this);
    }

    public Matrix4x3dc rotate(IQuaternionf quat) {
        return rotate(quat, this);
    }

    public Matrix4x3dc rotateTranslation(IQuaterniond quat, Matrix4x3dc dest) {
        double w2 = quat.w() * quat.w();
        double x2 = quat.x() * quat.x();
        double y2 = quat.y() * quat.y();
        double z2 = quat.z() * quat.z();
        double zw = quat.z() * quat.w();
        double xy = quat.x() * quat.y();
        double xz = quat.x() * quat.z();
        double yw = quat.y() * quat.w();
        double yz = quat.y() * quat.z();
        double xw = quat.x() * quat.w();
        double rm00 = w2 + x2 - z2 - y2;
        double rm01 = xy + zw + zw + xy;
        double rm02 = xz - yw + xz - yw;
        double rm10 = -zw + xy - zw + xy;
        double rm11 = y2 - z2 + w2 - x2;
        double rm12 = yz + yz + xw + xw;
        double rm20 = yw + xz + xz + yw;
        double rm21 = yz + yz - xw - xw;
        double rm22 = z2 - y2 - x2 + w2;
        double nm00 = rm00;
        double nm01 = rm01;
        double nm02 = rm02;
        double nm10 = rm10;
        double nm11 = rm11;
        double nm12 = rm12;
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

    public Matrix4x3dc rotateTranslation(IQuaternionf quat, Matrix4x3dc dest) {
        double w2 = quat.w() * quat.w();
        double x2 = quat.x() * quat.x();
        double y2 = quat.y() * quat.y();
        double z2 = quat.z() * quat.z();
        double zw = quat.z() * quat.w();
        double xy = quat.x() * quat.y();
        double xz = quat.x() * quat.z();
        double yw = quat.y() * quat.w();
        double yz = quat.y() * quat.z();
        double xw = quat.x() * quat.w();
        double rm00 = w2 + x2 - z2 - y2;
        double rm01 = xy + zw + zw + xy;
        double rm02 = xz - yw + xz - yw;
        double rm10 = -zw + xy - zw + xy;
        double rm11 = y2 - z2 + w2 - x2;
        double rm12 = yz + yz + xw + xw;
        double rm20 = yw + xz + xz + yw;
        double rm21 = yz + yz - xw - xw;
        double rm22 = z2 - y2 - x2 + w2;
        double nm00 = rm00;
        double nm01 = rm01;
        double nm02 = rm02;
        double nm10 = rm10;
        double nm11 = rm11;
        double nm12 = rm12;
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

    public Matrix4x3dc rotateLocal(IQuaterniond quat, Matrix4x3dc dest) {
        double w2 = quat.w() * quat.w();
        double x2 = quat.x() * quat.x();
        double y2 = quat.y() * quat.y();
        double z2 = quat.z() * quat.z();
        double zw = quat.z() * quat.w();
        double xy = quat.x() * quat.y();
        double xz = quat.x() * quat.z();
        double yw = quat.y() * quat.w();
        double yz = quat.y() * quat.z();
        double xw = quat.x() * quat.w();
        double lm00 = w2 + x2 - z2 - y2;
        double lm01 = xy + zw + zw + xy;
        double lm02 = xz - yw + xz - yw;
        double lm10 = -zw + xy - zw + xy;
        double lm11 = y2 - z2 + w2 - x2;
        double lm12 = yz + yz + xw + xw;
        double lm20 = yw + xz + xz + yw;
        double lm21 = yz + yz - xw - xw;
        double lm22 = z2 - y2 - x2 + w2;
        double nm00 = lm00 * m00 + lm10 * m01 + lm20 * m02;
        double nm01 = lm01 * m00 + lm11 * m01 + lm21 * m02;
        double nm02 = lm02 * m00 + lm12 * m01 + lm22 * m02;
        double nm10 = lm00 * m10 + lm10 * m11 + lm20 * m12;
        double nm11 = lm01 * m10 + lm11 * m11 + lm21 * m12;
        double nm12 = lm02 * m10 + lm12 * m11 + lm22 * m12;
        double nm20 = lm00 * m20 + lm10 * m21 + lm20 * m22;
        double nm21 = lm01 * m20 + lm11 * m21 + lm21 * m22;
        double nm22 = lm02 * m20 + lm12 * m21 + lm22 * m22;
        double nm30 = lm00 * m30 + lm10 * m31 + lm20 * m32;
        double nm31 = lm01 * m30 + lm11 * m31 + lm21 * m32;
        double nm32 = lm02 * m30 + lm12 * m31 + lm22 * m32;
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

    public Matrix4x3dc rotateLocal(IQuaterniond quat) {
        return rotateLocal(quat, this);
    }

    public Matrix4x3dc rotateLocal(IQuaternionf quat, Matrix4x3dc dest) {
        double w2 = quat.w() * quat.w();
        double x2 = quat.x() * quat.x();
        double y2 = quat.y() * quat.y();
        double z2 = quat.z() * quat.z();
        double zw = quat.z() * quat.w();
        double xy = quat.x() * quat.y();
        double xz = quat.x() * quat.z();
        double yw = quat.y() * quat.w();
        double yz = quat.y() * quat.z();
        double xw = quat.x() * quat.w();
        double lm00 = w2 + x2 - z2 - y2;
        double lm01 = xy + zw + zw + xy;
        double lm02 = xz - yw + xz - yw;
        double lm10 = -zw + xy - zw + xy;
        double lm11 = y2 - z2 + w2 - x2;
        double lm12 = yz + yz + xw + xw;
        double lm20 = yw + xz + xz + yw;
        double lm21 = yz + yz - xw - xw;
        double lm22 = z2 - y2 - x2 + w2;
        double nm00 = lm00 * m00 + lm10 * m01 + lm20 * m02;
        double nm01 = lm01 * m00 + lm11 * m01 + lm21 * m02;
        double nm02 = lm02 * m00 + lm12 * m01 + lm22 * m02;
        double nm10 = lm00 * m10 + lm10 * m11 + lm20 * m12;
        double nm11 = lm01 * m10 + lm11 * m11 + lm21 * m12;
        double nm12 = lm02 * m10 + lm12 * m11 + lm22 * m12;
        double nm20 = lm00 * m20 + lm10 * m21 + lm20 * m22;
        double nm21 = lm01 * m20 + lm11 * m21 + lm21 * m22;
        double nm22 = lm02 * m20 + lm12 * m21 + lm22 * m22;
        double nm30 = lm00 * m30 + lm10 * m31 + lm20 * m32;
        double nm31 = lm01 * m30 + lm11 * m31 + lm21 * m32;
        double nm32 = lm02 * m30 + lm12 * m31 + lm22 * m32;
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

    public Matrix4x3dc rotateLocal(IQuaternionf quat) {
        return rotateLocal(quat, this);
    }

    public Matrix4x3dc rotate(AxisAngle4fc axisAngle) {
        return rotate(axisAngle.angle(), axisAngle.x(), axisAngle.y(), axisAngle.z());
    }

    public Matrix4x3dc rotate(AxisAngle4fc axisAngle, Matrix4x3dc dest) {
        return rotate(axisAngle.angle(), axisAngle.x(), axisAngle.y(), axisAngle.z(), dest);
    }

    public Matrix4x3dc rotate(AxisAngle4dc axisAngle) {
        return rotate(axisAngle.angle(), axisAngle.x(), axisAngle.y(), axisAngle.z());
    }

    public Matrix4x3dc rotate(AxisAngle4dc axisAngle, Matrix4x3dc dest) {
        return rotate(axisAngle.angle(), axisAngle.x(), axisAngle.y(), axisAngle.z(), dest);
    }

    public Matrix4x3dc rotate(double angle, IVector3d axis) {
        return rotate(angle, axis.x(), axis.y(), axis.z());
    }

    public Matrix4x3dc rotate(double angle, IVector3d axis, Matrix4x3dc dest) {
        return rotate(angle, axis.x(), axis.y(), axis.z(), dest);
    }

    public Matrix4x3dc rotate(double angle, IVector3f axis) {
        return rotate(angle, axis.x(), axis.y(), axis.z());
    }

    public Matrix4x3dc rotate(double angle, IVector3f axis, Matrix4x3dc dest) {
        return rotate(angle, axis.x(), axis.y(), axis.z(), dest);
    }

    public Vector4dc getRow(int row, Vector4dc dest) throws IndexOutOfBoundsException {
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

    public Matrix4x3dc setRow(int row, IVector4d src) throws IndexOutOfBoundsException {
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

    public Vector3dc getColumn(int column, Vector3dc dest) throws IndexOutOfBoundsException {
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

    public Matrix4x3dc setColumn(int column, IVector3d src) throws IndexOutOfBoundsException {
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

    public Matrix4x3dc normal() {
        return normal(this);
    }

    public Matrix4x3dc normal(Matrix4x3dc dest) {
        double m00m11 = m00 * m11;
        double m01m10 = m01 * m10;
        double m02m10 = m02 * m10;
        double m00m12 = m00 * m12;
        double m01m12 = m01 * m12;
        double m02m11 = m02 * m11;
        double det = (m00m11 - m01m10) * m22 + (m02m10 - m00m12) * m21 + (m01m12 - m02m11) * m20;
        double s = 1.0 / det;
        /* Invert and transpose in one go */
        double nm00 = (m11 * m22 - m21 * m12) * s;
        double nm01 = (m20 * m12 - m10 * m22) * s;
        double nm02 = (m10 * m21 - m20 * m11) * s;
        double nm10 = (m21 * m02 - m01 * m22) * s;
        double nm11 = (m00 * m22 - m20 * m02) * s;
        double nm12 = (m20 * m01 - m00 * m21) * s;
        double nm20 = (m01m12 - m02m11) * s;
        double nm21 = (m02m10 - m00m12) * s;
        double nm22 = (m00m11 - m01m10) * s;
        dest.m00(nm00);
        dest.m01(nm01);
        dest.m02(nm02);
        dest.m10(nm10);
        dest.m11(nm11);
        dest.m12(nm12);
        dest.m20(nm20);
        dest.m21(nm21);
        dest.m22(nm22);
        dest.m30(0.0);
        dest.m31(0.0);
        dest.m32(0.0);
        dest.properties(0);
        return dest;
    }

    public Matrix3dc normal(Matrix3dc dest) {
        double m00m11 = m00 * m11;
        double m01m10 = m01 * m10;
        double m02m10 = m02 * m10;
        double m00m12 = m00 * m12;
        double m01m12 = m01 * m12;
        double m02m11 = m02 * m11;
        double det = (m00m11 - m01m10) * m22 + (m02m10 - m00m12) * m21 + (m01m12 - m02m11) * m20;
        double s = 1.0 / det;
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

    public Matrix4x3dc normalize3x3() {
        return normalize3x3(this);
    }

    public Matrix4x3dc normalize3x3(Matrix4x3dc dest) {
        double invXlen = 1.0 / Math.sqrt(m00 * m00 + m01 * m01 + m02 * m02);
        double invYlen = 1.0 / Math.sqrt(m10 * m10 + m11 * m11 + m12 * m12);
        double invZlen = 1.0 / Math.sqrt(m20 * m20 + m21 * m21 + m22 * m22);
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

    public Matrix3dc normalize3x3(Matrix3dc dest) {
        double invXlen = 1.0 / Math.sqrt(m00 * m00 + m01 * m01 + m02 * m02);
        double invYlen = 1.0 / Math.sqrt(m10 * m10 + m11 * m11 + m12 * m12);
        double invZlen = 1.0 / Math.sqrt(m20 * m20 + m21 * m21 + m22 * m22);
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

    public Matrix4x3dc reflect(double a, double b, double c, double d, Matrix4x3dc dest) {
        if ((properties & PROPERTY_IDENTITY) != 0)
            return dest.reflection(a, b, c, d);

        double da = a + a, db = b + b, dc = c + c, dd = d + d;
        double rm00 = 1.0 - da * a;
        double rm01 = -da * b;
        double rm02 = -da * c;
        double rm10 = -db * a;
        double rm11 = 1.0 - db * b;
        double rm12 = -db * c;
        double rm20 = -dc * a;
        double rm21 = -dc * b;
        double rm22 = 1.0 - dc * c;
        double rm30 = -dd * a;
        double rm31 = -dd * b;
        double rm32 = -dd * c;

        // matrix multiplication
        dest.m30(m00 * rm30 + m10 * rm31 + m20 * rm32 + m30);
        dest.m31(m01 * rm30 + m11 * rm31 + m21 * rm32 + m31);
        dest.m32(m02 * rm30 + m12 * rm31 + m22 * rm32 + m32);
        double nm00 = m00 * rm00 + m10 * rm01 + m20 * rm02;
        double nm01 = m01 * rm00 + m11 * rm01 + m21 * rm02;
        double nm02 = m02 * rm00 + m12 * rm01 + m22 * rm02;
        double nm10 = m00 * rm10 + m10 * rm11 + m20 * rm12;
        double nm11 = m01 * rm10 + m11 * rm11 + m21 * rm12;
        double nm12 = m02 * rm10 + m12 * rm11 + m22 * rm12;
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

    public Matrix4x3dc reflect(double a, double b, double c, double d) {
        return reflect(a, b, c, d, this);
    }

    public Matrix4x3dc reflect(double nx, double ny, double nz, double px, double py, double pz) {
        return reflect(nx, ny, nz, px, py, pz, this);
    }

    public Matrix4x3dc reflect(double nx, double ny, double nz, double px, double py, double pz, Matrix4x3dc dest) {
        double invLength = 1.0 / Math.sqrt(nx * nx + ny * ny + nz * nz);
        double nnx = nx * invLength;
        double nny = ny * invLength;
        double nnz = nz * invLength;
        /* See: http://mathworld.wolfram.com/Plane.html */
        return reflect(nnx, nny, nnz, -nnx * px - nny * py - nnz * pz, dest);
    }

    public Matrix4x3dc reflect(IVector3d normal, IVector3d point) {
        return reflect(normal.x(), normal.y(), normal.z(), point.x(), point.y(), point.z());
    }

    public Matrix4x3dc reflect(IQuaterniond orientation, IVector3d point) {
        return reflect(orientation, point, this);
    }

    public Matrix4x3dc reflect(IQuaterniond orientation, IVector3d point, Matrix4x3dc dest) {
        double num1 = orientation.x() + orientation.x();
        double num2 = orientation.y() + orientation.y();
        double num3 = orientation.z() + orientation.z();
        double normalX = orientation.x() * num3 + orientation.w() * num2;
        double normalY = orientation.y() * num3 - orientation.w() * num1;
        double normalZ = 1.0 - (orientation.x() * num1 + orientation.y() * num2);
        return reflect(normalX, normalY, normalZ, point.x(), point.y(), point.z(), dest);
    }

    public Matrix4x3dc reflect(IVector3d normal, IVector3d point, Matrix4x3dc dest) {
        return reflect(normal.x(), normal.y(), normal.z(), point.x(), point.y(), point.z(), dest);
    }

    public Matrix4x3dc reflection(double a, double b, double c, double d) {
        double da = a + a, db = b + b, dc = c + c, dd = d + d;
        m00 = 1.0 - da * a;
        m01 = -da * b;
        m02 = -da * c;
        m10 = -db * a;
        m11 = 1.0 - db * b;
        m12 = -db * c;
        m20 = -dc * a;
        m21 = -dc * b;
        m22 = 1.0 - dc * c;
        m30 = -dd * a;
        m31 = -dd * b;
        m32 = -dd * c;
        properties = 0;
        return this;
    }

    public Matrix4x3dc reflection(double nx, double ny, double nz, double px, double py, double pz) {
        double invLength = 1.0 / Math.sqrt(nx * nx + ny * ny + nz * nz);
        double nnx = nx * invLength;
        double nny = ny * invLength;
        double nnz = nz * invLength;
        /* See: http://mathworld.wolfram.com/Plane.html */
        return reflection(nnx, nny, nnz, -nnx * px - nny * py - nnz * pz);
    }

    public Matrix4x3dc reflection(IVector3d normal, IVector3d point) {
        return reflection(normal.x(), normal.y(), normal.z(), point.x(), point.y(), point.z());
    }

    public Matrix4x3dc reflection(IQuaterniond orientation, IVector3d point) {
        double num1 = orientation.x() + orientation.x();
        double num2 = orientation.y() + orientation.y();
        double num3 = orientation.z() + orientation.z();
        double normalX = orientation.x() * num3 + orientation.w() * num2;
        double normalY = orientation.y() * num3 - orientation.w() * num1;
        double normalZ = 1.0 - (orientation.x() * num1 + orientation.y() * num2);
        return reflection(normalX, normalY, normalZ, point.x(), point.y(), point.z());
    }

    public Matrix4x3dc ortho(double left, double right, double bottom, double top, double zNear, double zFar, boolean zZeroToOne, Matrix4x3dc dest) {
        // calculate right matrix elements
        double rm00 = 2.0 / (right - left);
        double rm11 = 2.0 / (top - bottom);
        double rm22 = (zZeroToOne ? 1.0 : 2.0) / (zNear - zFar);
        double rm30 = (left + right) / (left - right);
        double rm31 = (top + bottom) / (bottom - top);
        double rm32 = (zZeroToOne ? zNear : (zFar + zNear)) / (zNear - zFar);

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

    public Matrix4x3dc ortho(double left, double right, double bottom, double top, double zNear, double zFar, Matrix4x3dc dest) {
        return ortho(left, right, bottom, top, zNear, zFar, false, dest);
    }

    public Matrix4x3dc ortho(double left, double right, double bottom, double top, double zNear, double zFar, boolean zZeroToOne) {
        return ortho(left, right, bottom, top, zNear, zFar, zZeroToOne, this);
    }

    public Matrix4x3dc ortho(double left, double right, double bottom, double top, double zNear, double zFar) {
        return ortho(left, right, bottom, top, zNear, zFar, false);
    }

    public Matrix4x3dc orthoLH(double left, double right, double bottom, double top, double zNear, double zFar, boolean zZeroToOne, Matrix4x3dc dest) {
        // calculate right matrix elements
        double rm00 = 2.0 / (right - left);
        double rm11 = 2.0 / (top - bottom);
        double rm22 = (zZeroToOne ? 1.0 : 2.0) / (zFar - zNear);
        double rm30 = (left + right) / (left - right);
        double rm31 = (top + bottom) / (bottom - top);
        double rm32 = (zZeroToOne ? zNear : (zFar + zNear)) / (zNear - zFar);

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

    public Matrix4x3dc orthoLH(double left, double right, double bottom, double top, double zNear, double zFar, Matrix4x3dc dest) {
        return orthoLH(left, right, bottom, top, zNear, zFar, false, dest);
    }

    public Matrix4x3dc orthoLH(double left, double right, double bottom, double top, double zNear, double zFar, boolean zZeroToOne) {
        return orthoLH(left, right, bottom, top, zNear, zFar, zZeroToOne, this);
    }

    public Matrix4x3dc orthoLH(double left, double right, double bottom, double top, double zNear, double zFar) {
        return orthoLH(left, right, bottom, top, zNear, zFar, false);
    }

    public Matrix4x3dc setOrtho(double left, double right, double bottom, double top, double zNear, double zFar, boolean zZeroToOne) {
        m00 = 2.0 / (right - left);
        m01 = 0.0;
        m02 = 0.0;
        m10 = 0.0;
        m11 = 2.0 / (top - bottom);
        m12 = 0.0;
        m20 = 0.0;
        m21 = 0.0;
        m22 = (zZeroToOne ? 1.0 : 2.0) / (zNear - zFar);
        m30 = (right + left) / (left - right);
        m31 = (top + bottom) / (bottom - top);
        m32 = (zZeroToOne ? zNear : (zFar + zNear)) / (zNear - zFar);
        properties = 0;
        return this;
    }

    public Matrix4x3dc setOrtho(double left, double right, double bottom, double top, double zNear, double zFar) {
        return setOrtho(left, right, bottom, top, zNear, zFar, false);
    }

    public Matrix4x3dc setOrthoLH(double left, double right, double bottom, double top, double zNear, double zFar, boolean zZeroToOne) {
        m00 = 2.0 / (right - left);
        m01 = 0.0;
        m02 = 0.0;
        m10 = 0.0;
        m11 = 2.0 / (top - bottom);
        m12 = 0.0;
        m20 = 0.0;
        m21 = 0.0;
        m22 = (zZeroToOne ? 1.0 : 2.0) / (zFar - zNear);
        m30 = (right + left) / (left - right);
        m31 = (top + bottom) / (bottom - top);
        m32 = (zZeroToOne ? zNear : (zFar + zNear)) / (zNear - zFar);
        properties = 0;
        return this;
    }

    public Matrix4x3dc setOrthoLH(double left, double right, double bottom, double top, double zNear, double zFar) {
        return setOrthoLH(left, right, bottom, top, zNear, zFar, false);
    }

    public Matrix4x3dc orthoSymmetric(double width, double height, double zNear, double zFar, boolean zZeroToOne, Matrix4x3dc dest) {
        // calculate right matrix elements
        double rm00 = 2.0 / width;
        double rm11 = 2.0 / height;
        double rm22 = (zZeroToOne ? 1.0 : 2.0) / (zNear - zFar);
        double rm32 = (zZeroToOne ? zNear : (zFar + zNear)) / (zNear - zFar);

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

    public Matrix4x3dc orthoSymmetric(double width, double height, double zNear, double zFar, Matrix4x3dc dest) {
        return orthoSymmetric(width, height, zNear, zFar, false, dest);
    }

    public Matrix4x3dc orthoSymmetric(double width, double height, double zNear, double zFar, boolean zZeroToOne) {
        return orthoSymmetric(width, height, zNear, zFar, zZeroToOne, this);
    }

    public Matrix4x3dc orthoSymmetric(double width, double height, double zNear, double zFar) {
        return orthoSymmetric(width, height, zNear, zFar, false, this);
    }

    public Matrix4x3dc orthoSymmetricLH(double width, double height, double zNear, double zFar, boolean zZeroToOne, Matrix4x3dc dest) {
        // calculate right matrix elements
        double rm00 = 2.0f / width;
        double rm11 = 2.0f / height;
        double rm22 = (zZeroToOne ? 1.0f : 2.0f) / (zFar - zNear);
        double rm32 = (zZeroToOne ? zNear : (zFar + zNear)) / (zNear - zFar);

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

    public Matrix4x3dc orthoSymmetricLH(double width, double height, double zNear, double zFar, Matrix4x3dc dest) {
        return orthoSymmetricLH(width, height, zNear, zFar, false, dest);
    }

    public Matrix4x3dc orthoSymmetricLH(double width, double height, double zNear, double zFar, boolean zZeroToOne) {
        return orthoSymmetricLH(width, height, zNear, zFar, zZeroToOne, this);
    }

    public Matrix4x3dc orthoSymmetricLH(double width, double height, double zNear, double zFar) {
        return orthoSymmetricLH(width, height, zNear, zFar, false, this);
    }

    public Matrix4x3dc setOrthoSymmetric(double width, double height, double zNear, double zFar, boolean zZeroToOne) {
        m00 = 2.0 / width;
        m01 = 0.0;
        m02 = 0.0;
        m10 = 0.0;
        m11 = 2.0 / height;
        m12 = 0.0;
        m20 = 0.0;
        m21 = 0.0;
        m22 = (zZeroToOne ? 1.0 : 2.0) / (zNear - zFar);
        m30 = 0.0;
        m31 = 0.0;
        m32 = (zZeroToOne ? zNear : (zFar + zNear)) / (zNear - zFar);
        properties = 0;
        return this;
    }

    public Matrix4x3dc setOrthoSymmetric(double width, double height, double zNear, double zFar) {
        return setOrthoSymmetric(width, height, zNear, zFar, false);
    }

    public Matrix4x3dc setOrthoSymmetricLH(double width, double height, double zNear, double zFar, boolean zZeroToOne) {
        m00 = 2.0 / width;
        m01 = 0.0;
        m02 = 0.0;
        m10 = 0.0;
        m11 = 2.0 / height;
        m12 = 0.0;
        m20 = 0.0;
        m21 = 0.0;
        m22 = (zZeroToOne ? 1.0 : 2.0) / (zFar - zNear);
        m30 = 0.0;
        m31 = 0.0;
        m32 = (zZeroToOne ? zNear : (zFar + zNear)) / (zNear - zFar);
        properties = 0;
        return this;
    }

    public Matrix4x3dc setOrthoSymmetricLH(double width, double height, double zNear, double zFar) {
        return setOrthoSymmetricLH(width, height, zNear, zFar, false);
    }

    public Matrix4x3dc ortho2D(double left, double right, double bottom, double top, Matrix4x3dc dest) {
        // calculate right matrix elements
        double rm00 = 2.0 / (right - left);
        double rm11 = 2.0 / (top - bottom);
        double rm30 = -(right + left) / (right - left);
        double rm31 = -(top + bottom) / (top - bottom);

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

    public Matrix4x3dc ortho2D(double left, double right, double bottom, double top) {
        return ortho2D(left, right, bottom, top, this);
    }

    public Matrix4x3dc ortho2DLH(double left, double right, double bottom, double top, Matrix4x3dc dest) {
        // calculate right matrix elements
        double rm00 = 2.0 / (right - left);
        double rm11 = 2.0 / (top - bottom);
        double rm30 = -(right + left) / (right - left);
        double rm31 = -(top + bottom) / (top - bottom);

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

    public Matrix4x3dc ortho2DLH(double left, double right, double bottom, double top) {
        return ortho2DLH(left, right, bottom, top, this);
    }

    public Matrix4x3dc setOrtho2D(double left, double right, double bottom, double top) {
        m00 = 2.0 / (right - left);
        m01 = 0.0;
        m02 = 0.0;
        m10 = 0.0;
        m11 = 2.0 / (top - bottom);
        m12 = 0.0;
        m20 = 0.0;
        m21 = 0.0;
        m22 = -1.0;
        m30 = -(right + left) / (right - left);
        m31 = -(top + bottom) / (top - bottom);
        m32 = 0.0;
        properties = 0;
        return this;
    }

    public Matrix4x3dc setOrtho2DLH(double left, double right, double bottom, double top) {
        m00 = 2.0 / (right - left);
        m01 = 0.0;
        m02 = 0.0;
        m10 = 0.0;
        m11 = 2.0 / (top - bottom);
        m12 = 0.0;
        m20 = 0.0;
        m21 = 0.0;
        m22 = 1.0;
        m30 = -(right + left) / (right - left);
        m31 = -(top + bottom) / (top - bottom);
        m32 = 0.0;
        properties = 0;
        return this;
    }

    public Matrix4x3dc lookAlong(IVector3d dir, IVector3d up) {
        return lookAlong(dir.x(), dir.y(), dir.z(), up.x(), up.y(), up.z(), this);
    }

    public Matrix4x3dc lookAlong(IVector3d dir, IVector3d up, Matrix4x3dc dest) {
        return lookAlong(dir.x(), dir.y(), dir.z(), up.x(), up.y(), up.z(), dest);
    }

    public Matrix4x3dc lookAlong(double dirX, double dirY, double dirZ,
                                 double upX, double upY, double upZ, Matrix4x3dc dest) {
        if ((properties & PROPERTY_IDENTITY) != 0)
            return setLookAlong(dirX, dirY, dirZ, upX, upY, upZ);

        // Normalize direction
        double invDirLength = 1.0 / Math.sqrt(dirX * dirX + dirY * dirY + dirZ * dirZ);
        double dirnX = -dirX * invDirLength;
        double dirnY = -dirY * invDirLength;
        double dirnZ = -dirZ * invDirLength;
        // right = direction x up
        double rightX, rightY, rightZ;
        rightX = dirnY * upZ - dirnZ * upY;
        rightY = dirnZ * upX - dirnX * upZ;
        rightZ = dirnX * upY - dirnY * upX;
        // normalize right
        double invRightLength = 1.0 / Math.sqrt(rightX * rightX + rightY * rightY + rightZ * rightZ);
        rightX *= invRightLength;
        rightY *= invRightLength;
        rightZ *= invRightLength;
        // up = right x direction
        double upnX = rightY * dirnZ - rightZ * dirnY;
        double upnY = rightZ * dirnX - rightX * dirnZ;
        double upnZ = rightX * dirnY - rightY * dirnX;

        // calculate right matrix elements
        double rm00 = rightX;
        double rm01 = upnX;
        double rm02 = -dirnX;
        double rm10 = rightY;
        double rm11 = upnY;
        double rm12 = -dirnY;
        double rm20 = rightZ;
        double rm21 = upnZ;
        double rm22 = -dirnZ;

        // perform optimized matrix multiplication
        // introduce temporaries for dependent results
        double nm00 = m00 * rm00 + m10 * rm01 + m20 * rm02;
        double nm01 = m01 * rm00 + m11 * rm01 + m21 * rm02;
        double nm02 = m02 * rm00 + m12 * rm01 + m22 * rm02;
        double nm10 = m00 * rm10 + m10 * rm11 + m20 * rm12;
        double nm11 = m01 * rm10 + m11 * rm11 + m21 * rm12;
        double nm12 = m02 * rm10 + m12 * rm11 + m22 * rm12;
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

    public Matrix4x3dc lookAlong(double dirX, double dirY, double dirZ,
                                 double upX, double upY, double upZ) {
        return lookAlong(dirX, dirY, dirZ, upX, upY, upZ, this);
    }

    public Matrix4x3dc setLookAlong(IVector3d dir, IVector3d up) {
        return setLookAlong(dir.x(), dir.y(), dir.z(), up.x(), up.y(), up.z());
    }

    public Matrix4x3dc setLookAlong(double dirX, double dirY, double dirZ,
                                    double upX, double upY, double upZ) {
        // Normalize direction
        double invDirLength = 1.0 / Math.sqrt(dirX * dirX + dirY * dirY + dirZ * dirZ);
        double dirnX = -dirX * invDirLength;
        double dirnY = -dirY * invDirLength;
        double dirnZ = -dirZ * invDirLength;
        // right = direction x up
        double rightX, rightY, rightZ;
        rightX = dirnY * upZ - dirnZ * upY;
        rightY = dirnZ * upX - dirnX * upZ;
        rightZ = dirnX * upY - dirnY * upX;
        // normalize right
        double invRightLength = 1.0 / Math.sqrt(rightX * rightX + rightY * rightY + rightZ * rightZ);
        rightX *= invRightLength;
        rightY *= invRightLength;
        rightZ *= invRightLength;
        // up = right x direction
        double upnX = rightY * dirnZ - rightZ * dirnY;
        double upnY = rightZ * dirnX - rightX * dirnZ;
        double upnZ = rightX * dirnY - rightY * dirnX;

        m00 = rightX;
        m01 = upnX;
        m02 = -dirnX;
        m10 = rightY;
        m11 = upnY;
        m12 = -dirnY;
        m20 = rightZ;
        m21 = upnZ;
        m22 = -dirnZ;
        m30 = 0.0;
        m31 = 0.0;
        m32 = 0.0;
        properties = 0;

        return this;
    }

    public Matrix4x3dc setLookAt(IVector3d eye, IVector3d center, IVector3d up) {
        return setLookAt(eye.x(), eye.y(), eye.z(), center.x(), center.y(), center.z(), up.x(), up.y(), up.z());
    }

    public Matrix4x3dc setLookAt(double eyeX, double eyeY, double eyeZ,
                                 double centerX, double centerY, double centerZ,
                                 double upX, double upY, double upZ) {
        // Compute direction from position to lookAt
        double dirX, dirY, dirZ;
        dirX = eyeX - centerX;
        dirY = eyeY - centerY;
        dirZ = eyeZ - centerZ;
        // Normalize direction
        double invDirLength = 1.0 / Math.sqrt(dirX * dirX + dirY * dirY + dirZ * dirZ);
        dirX *= invDirLength;
        dirY *= invDirLength;
        dirZ *= invDirLength;
        // left = up x direction
        double leftX, leftY, leftZ;
        leftX = upY * dirZ - upZ * dirY;
        leftY = upZ * dirX - upX * dirZ;
        leftZ = upX * dirY - upY * dirX;
        // normalize left
        double invLeftLength = 1.0 / Math.sqrt(leftX * leftX + leftY * leftY + leftZ * leftZ);
        leftX *= invLeftLength;
        leftY *= invLeftLength;
        leftZ *= invLeftLength;
        // up = direction x left
        double upnX = dirY * leftZ - dirZ * leftY;
        double upnY = dirZ * leftX - dirX * leftZ;
        double upnZ = dirX * leftY - dirY * leftX;

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

    public Matrix4x3dc lookAt(IVector3d eye, IVector3d center, IVector3d up, Matrix4x3dc dest) {
        return lookAt(eye.x(), eye.y(), eye.z(), center.x(), center.y(), center.z(), up.x(), up.y(), up.z(), dest);
    }

    public Matrix4x3dc lookAt(IVector3d eye, IVector3d center, IVector3d up) {
        return lookAt(eye.x(), eye.y(), eye.z(), center.x(), center.y(), center.z(), up.x(), up.y(), up.z(), this);
    }

    public Matrix4x3dc lookAt(double eyeX, double eyeY, double eyeZ,
                              double centerX, double centerY, double centerZ,
                              double upX, double upY, double upZ, Matrix4x3dc dest) {
        if ((properties & PROPERTY_IDENTITY) != 0)
            return dest.setLookAt(eyeX, eyeY, eyeZ, centerX, centerY, centerZ, upX, upY, upZ);
        return lookAtGeneric(eyeX, eyeY, eyeZ, centerX, centerY, centerZ, upX, upY, upZ, dest);
    }

    private Matrix4x3dc lookAtGeneric(double eyeX, double eyeY, double eyeZ,
                                      double centerX, double centerY, double centerZ,
                                      double upX, double upY, double upZ, Matrix4x3dc dest) {
        // Compute direction from position to lookAt
        double dirX, dirY, dirZ;
        dirX = eyeX - centerX;
        dirY = eyeY - centerY;
        dirZ = eyeZ - centerZ;
        // Normalize direction
        double invDirLength = 1.0 / Math.sqrt(dirX * dirX + dirY * dirY + dirZ * dirZ);
        dirX *= invDirLength;
        dirY *= invDirLength;
        dirZ *= invDirLength;
        // left = up x direction
        double leftX, leftY, leftZ;
        leftX = upY * dirZ - upZ * dirY;
        leftY = upZ * dirX - upX * dirZ;
        leftZ = upX * dirY - upY * dirX;
        // normalize left
        double invLeftLength = 1.0 / Math.sqrt(leftX * leftX + leftY * leftY + leftZ * leftZ);
        leftX *= invLeftLength;
        leftY *= invLeftLength;
        leftZ *= invLeftLength;
        // up = direction x left
        double upnX = dirY * leftZ - dirZ * leftY;
        double upnY = dirZ * leftX - dirX * leftZ;
        double upnZ = dirX * leftY - dirY * leftX;

        // calculate right matrix elements
        double rm00 = leftX;
        double rm01 = upnX;
        double rm02 = dirX;
        double rm10 = leftY;
        double rm11 = upnY;
        double rm12 = dirY;
        double rm20 = leftZ;
        double rm21 = upnZ;
        double rm22 = dirZ;
        double rm30 = -(leftX * eyeX + leftY * eyeY + leftZ * eyeZ);
        double rm31 = -(upnX * eyeX + upnY * eyeY + upnZ * eyeZ);
        double rm32 = -(dirX * eyeX + dirY * eyeY + dirZ * eyeZ);

        // perform optimized matrix multiplication
        // compute last column first, because others do not depend on it
        dest.m30(m00 * rm30 + m10 * rm31 + m20 * rm32 + m30);
        dest.m31(m01 * rm30 + m11 * rm31 + m21 * rm32 + m31);
        dest.m32(m02 * rm30 + m12 * rm31 + m22 * rm32 + m32);
        // introduce temporaries for dependent results
        double nm00 = m00 * rm00 + m10 * rm01 + m20 * rm02;
        double nm01 = m01 * rm00 + m11 * rm01 + m21 * rm02;
        double nm02 = m02 * rm00 + m12 * rm01 + m22 * rm02;
        double nm10 = m00 * rm10 + m10 * rm11 + m20 * rm12;
        double nm11 = m01 * rm10 + m11 * rm11 + m21 * rm12;
        double nm12 = m02 * rm10 + m12 * rm11 + m22 * rm12;
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

    public Matrix4x3dc lookAt(double eyeX, double eyeY, double eyeZ,
                              double centerX, double centerY, double centerZ,
                              double upX, double upY, double upZ) {
        return lookAt(eyeX, eyeY, eyeZ, centerX, centerY, centerZ, upX, upY, upZ, this);
    }

    public Matrix4x3dc setLookAtLH(IVector3d eye, IVector3d center, IVector3d up) {
        return setLookAtLH(eye.x(), eye.y(), eye.z(), center.x(), center.y(), center.z(), up.x(), up.y(), up.z());
    }

    public Matrix4x3dc setLookAtLH(double eyeX, double eyeY, double eyeZ,
                                   double centerX, double centerY, double centerZ,
                                   double upX, double upY, double upZ) {
        // Compute direction from position to lookAt
        double dirX, dirY, dirZ;
        dirX = centerX - eyeX;
        dirY = centerY - eyeY;
        dirZ = centerZ - eyeZ;
        // Normalize direction
        double invDirLength = 1.0 / Math.sqrt(dirX * dirX + dirY * dirY + dirZ * dirZ);
        dirX *= invDirLength;
        dirY *= invDirLength;
        dirZ *= invDirLength;
        // left = up x direction
        double leftX, leftY, leftZ;
        leftX = upY * dirZ - upZ * dirY;
        leftY = upZ * dirX - upX * dirZ;
        leftZ = upX * dirY - upY * dirX;
        // normalize left
        double invLeftLength = 1.0 / Math.sqrt(leftX * leftX + leftY * leftY + leftZ * leftZ);
        leftX *= invLeftLength;
        leftY *= invLeftLength;
        leftZ *= invLeftLength;
        // up = direction x left
        double upnX = dirY * leftZ - dirZ * leftY;
        double upnY = dirZ * leftX - dirX * leftZ;
        double upnZ = dirX * leftY - dirY * leftX;

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

    public Matrix4x3dc lookAtLH(IVector3d eye, IVector3d center, IVector3d up, Matrix4x3dc dest) {
        return lookAtLH(eye.x(), eye.y(), eye.z(), center.x(), center.y(), center.z(), up.x(), up.y(), up.z(), dest);
    }

    public Matrix4x3dc lookAtLH(IVector3d eye, IVector3d center, IVector3d up) {
        return lookAtLH(eye.x(), eye.y(), eye.z(), center.x(), center.y(), center.z(), up.x(), up.y(), up.z(), this);
    }

    public Matrix4x3dc lookAtLH(double eyeX, double eyeY, double eyeZ,
                                double centerX, double centerY, double centerZ,
                                double upX, double upY, double upZ, Matrix4x3dc dest) {
        if ((properties & PROPERTY_IDENTITY) != 0)
            return dest.setLookAtLH(eyeX, eyeY, eyeZ, centerX, centerY, centerZ, upX, upY, upZ);
        return lookAtLHGeneric(eyeX, eyeY, eyeZ, centerX, centerY, centerZ, upX, upY, upZ, dest);
    }

    private Matrix4x3dc lookAtLHGeneric(double eyeX, double eyeY, double eyeZ,
                                        double centerX, double centerY, double centerZ,
                                        double upX, double upY, double upZ, Matrix4x3dc dest) {
        // Compute direction from position to lookAt
        double dirX, dirY, dirZ;
        dirX = centerX - eyeX;
        dirY = centerY - eyeY;
        dirZ = centerZ - eyeZ;
        // Normalize direction
        double invDirLength = 1.0 / Math.sqrt(dirX * dirX + dirY * dirY + dirZ * dirZ);
        dirX *= invDirLength;
        dirY *= invDirLength;
        dirZ *= invDirLength;
        // left = up x direction
        double leftX, leftY, leftZ;
        leftX = upY * dirZ - upZ * dirY;
        leftY = upZ * dirX - upX * dirZ;
        leftZ = upX * dirY - upY * dirX;
        // normalize left
        double invLeftLength = 1.0 / Math.sqrt(leftX * leftX + leftY * leftY + leftZ * leftZ);
        leftX *= invLeftLength;
        leftY *= invLeftLength;
        leftZ *= invLeftLength;
        // up = direction x left
        double upnX = dirY * leftZ - dirZ * leftY;
        double upnY = dirZ * leftX - dirX * leftZ;
        double upnZ = dirX * leftY - dirY * leftX;

        // calculate right matrix elements
        double rm00 = leftX;
        double rm01 = upnX;
        double rm02 = dirX;
        double rm10 = leftY;
        double rm11 = upnY;
        double rm12 = dirY;
        double rm20 = leftZ;
        double rm21 = upnZ;
        double rm22 = dirZ;
        double rm30 = -(leftX * eyeX + leftY * eyeY + leftZ * eyeZ);
        double rm31 = -(upnX * eyeX + upnY * eyeY + upnZ * eyeZ);
        double rm32 = -(dirX * eyeX + dirY * eyeY + dirZ * eyeZ);

        // perform optimized matrix multiplication
        // compute last column first, because others do not depend on it
        dest.m30(m00 * rm30 + m10 * rm31 + m20 * rm32 + m30);
        dest.m31(m01 * rm30 + m11 * rm31 + m21 * rm32 + m31);
        dest.m32(m02 * rm30 + m12 * rm31 + m22 * rm32 + m32);
        // introduce temporaries for dependent results
        double nm00 = m00 * rm00 + m10 * rm01 + m20 * rm02;
        double nm01 = m01 * rm00 + m11 * rm01 + m21 * rm02;
        double nm02 = m02 * rm00 + m12 * rm01 + m22 * rm02;
        double nm10 = m00 * rm10 + m10 * rm11 + m20 * rm12;
        double nm11 = m01 * rm10 + m11 * rm11 + m21 * rm12;
        double nm12 = m02 * rm10 + m12 * rm11 + m22 * rm12;
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

    public Matrix4x3dc lookAtLH(double eyeX, double eyeY, double eyeZ,
                                double centerX, double centerY, double centerZ,
                                double upX, double upY, double upZ) {
        return lookAtLH(eyeX, eyeY, eyeZ, centerX, centerY, centerZ, upX, upY, upZ, this);
    }

    public Planedc frustumPlane(int which, Planedc plane) {
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

    public Vector3dc positiveZ(Vector3dc dir) {
        dir.set(m10 * m21 - m11 * m20, m20 * m01 - m21 * m00, m00 * m11 - m01 * m10);
        dir.normalize();
        return dir;
    }

    public Vector3dc normalizedPositiveZ(Vector3dc dir) {
        dir.set(m02, m12, m22);
        return dir;
    }

    public Vector3dc positiveX(Vector3dc dir) {
        dir.set(m11 * m22 - m12 * m21, m02 * m21 - m01 * m22, m01 * m12 - m02 * m11);
        dir.normalize();
        return dir;
    }

    public Vector3dc normalizedPositiveX(Vector3dc dir) {
        dir.set(m00, m10, m20);
        return dir;
    }

    public Vector3dc positiveY(Vector3dc dir) {
        dir.set(m12 * m20 - m10 * m22, m00 * m22 - m02 * m20, m02 * m10 - m00 * m12);
        dir.normalize();
        return dir;
    }

    public Vector3dc normalizedPositiveY(Vector3dc dir) {
        dir.set(m01, m11, m21);
        return dir;
    }

    public Vector3dc origin(Vector3dc origin) {
        double a = m00 * m11 - m01 * m10;
        double b = m00 * m12 - m02 * m10;
        double d = m01 * m12 - m02 * m11;
        double g = m20 * m31 - m21 * m30;
        double h = m20 * m32 - m22 * m30;
        double j = m21 * m32 - m22 * m31;
        origin.set(-m10 * j + m11 * h - m12 * g,
                m00 * j - m01 * h + m02 * g,
                -m30 * d + m31 * b - m32 * a);
        return origin;
    }

    public Matrix4x3dc shadow(IVector4d light, double a, double b, double c, double d) {
        return shadow(light.x(), light.y(), light.z(), light.w(), a, b, c, d, this);
    }

    public Matrix4x3dc shadow(IVector4d light, double a, double b, double c, double d, Matrix4x3dc dest) {
        return shadow(light.x(), light.y(), light.z(), light.w(), a, b, c, d, dest);
    }

    public Matrix4x3dc shadow(double lightX, double lightY, double lightZ, double lightW, double a, double b, double c, double d) {
        return shadow(lightX, lightY, lightZ, lightW, a, b, c, d, this);
    }

    public Matrix4x3dc shadow(double lightX, double lightY, double lightZ, double lightW, double a, double b, double c, double d, Matrix4x3dc dest) {
        // normalize plane
        double invPlaneLen = 1.0 / Math.sqrt(a * a + b * b + c * c);
        double an = a * invPlaneLen;
        double bn = b * invPlaneLen;
        double cn = c * invPlaneLen;
        double dn = d * invPlaneLen;

        double dot = an * lightX + bn * lightY + cn * lightZ + dn * lightW;

        // compute right matrix elements
        double rm00 = dot - an * lightX;
        double rm01 = -an * lightY;
        double rm02 = -an * lightZ;
        double rm03 = -an * lightW;
        double rm10 = -bn * lightX;
        double rm11 = dot - bn * lightY;
        double rm12 = -bn * lightZ;
        double rm13 = -bn * lightW;
        double rm20 = -cn * lightX;
        double rm21 = -cn * lightY;
        double rm22 = dot - cn * lightZ;
        double rm23 = -cn * lightW;
        double rm30 = -dn * lightX;
        double rm31 = -dn * lightY;
        double rm32 = -dn * lightZ;
        double rm33 = dot - dn * lightW;

        // matrix multiplication
        double nm00 = m00 * rm00 + m10 * rm01 + m20 * rm02 + m30 * rm03;
        double nm01 = m01 * rm00 + m11 * rm01 + m21 * rm02 + m31 * rm03;
        double nm02 = m02 * rm00 + m12 * rm01 + m22 * rm02 + m32 * rm03;
        double nm10 = m00 * rm10 + m10 * rm11 + m20 * rm12 + m30 * rm13;
        double nm11 = m01 * rm10 + m11 * rm11 + m21 * rm12 + m31 * rm13;
        double nm12 = m02 * rm10 + m12 * rm11 + m22 * rm12 + m32 * rm13;
        double nm20 = m00 * rm20 + m10 * rm21 + m20 * rm22 + m30 * rm23;
        double nm21 = m01 * rm20 + m11 * rm21 + m21 * rm22 + m31 * rm23;
        double nm22 = m02 * rm20 + m12 * rm21 + m22 * rm22 + m32 * rm23;
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

    public Matrix4x3dc shadow(IVector4d light, IMatrix4x3d planeTransform, Matrix4x3dc dest) {
        // compute plane equation by transforming (y = 0)
        double a = planeTransform.m10();
        double b = planeTransform.m11();
        double c = planeTransform.m12();
        double d = -a * planeTransform.m30() - b * planeTransform.m31() - c * planeTransform.m32();
        return shadow(light.x(), light.y(), light.z(), light.w(), a, b, c, d, dest);
    }

    public Matrix4x3dc shadow(IVector4d light, IMatrix4x3d planeTransform) {
        return shadow(light, planeTransform, this);
    }

    public Matrix4x3dc shadow(double lightX, double lightY, double lightZ, double lightW, IMatrix4x3d planeTransform, Matrix4x3dc dest) {
        // compute plane equation by transforming (y = 0)
        double a = planeTransform.m10();
        double b = planeTransform.m11();
        double c = planeTransform.m12();
        double d = -a * planeTransform.m30() - b * planeTransform.m31() - c * planeTransform.m32();
        return shadow(lightX, lightY, lightZ, lightW, a, b, c, d, dest);
    }

    public Matrix4x3dc shadow(double lightX, double lightY, double lightZ, double lightW, IMatrix4x3d planeTransform) {
        return shadow(lightX, lightY, lightZ, lightW, planeTransform, this);
    }

    public Matrix4x3dc billboardCylindrical(IVector3d objPos, IVector3d targetPos, IVector3d up) {
        double dirX = targetPos.x() - objPos.x();
        double dirY = targetPos.y() - objPos.y();
        double dirZ = targetPos.z() - objPos.z();
        // left = up x dir
        double leftX = up.y() * dirZ - up.z() * dirY;
        double leftY = up.z() * dirX - up.x() * dirZ;
        double leftZ = up.x() * dirY - up.y() * dirX;
        // normalize left
        double invLeftLen = 1.0 / Math.sqrt(leftX * leftX + leftY * leftY + leftZ * leftZ);
        leftX *= invLeftLen;
        leftY *= invLeftLen;
        leftZ *= invLeftLen;
        // recompute dir by constraining rotation around 'up'
        // dir = left x up
        dirX = leftY * up.z() - leftZ * up.y();
        dirY = leftZ * up.x() - leftX * up.z();
        dirZ = leftX * up.y() - leftY * up.x();
        // normalize dir
        double invDirLen = 1.0 / Math.sqrt(dirX * dirX + dirY * dirY + dirZ * dirZ);
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

    public Matrix4x3dc billboardSpherical(IVector3d objPos, IVector3d targetPos, IVector3d up) {
        double dirX = targetPos.x() - objPos.x();
        double dirY = targetPos.y() - objPos.y();
        double dirZ = targetPos.z() - objPos.z();
        // normalize dir
        double invDirLen = 1.0 / Math.sqrt(dirX * dirX + dirY * dirY + dirZ * dirZ);
        dirX *= invDirLen;
        dirY *= invDirLen;
        dirZ *= invDirLen;
        // left = up x dir
        double leftX = up.y() * dirZ - up.z() * dirY;
        double leftY = up.z() * dirX - up.x() * dirZ;
        double leftZ = up.x() * dirY - up.y() * dirX;
        // normalize left
        double invLeftLen = 1.0 / Math.sqrt(leftX * leftX + leftY * leftY + leftZ * leftZ);
        leftX *= invLeftLen;
        leftY *= invLeftLen;
        leftZ *= invLeftLen;
        // up = dir x left
        double upX = dirY * leftZ - dirZ * leftY;
        double upY = dirZ * leftX - dirX * leftZ;
        double upZ = dirX * leftY - dirY * leftX;
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

    public Matrix4x3dc billboardSpherical(IVector3d objPos, IVector3d targetPos) {
        double toDirX = targetPos.x() - objPos.x();
        double toDirY = targetPos.y() - objPos.y();
        double toDirZ = targetPos.z() - objPos.z();
        double x = -toDirY;
        double y = toDirX;
        double w = Math.sqrt(toDirX * toDirX + toDirY * toDirY + toDirZ * toDirZ) + toDirZ;
        double invNorm = 1.0 / Math.sqrt(x * x + y * y + w * w);
        x *= invNorm;
        y *= invNorm;
        w *= invNorm;
        double q00 = (x + x) * x;
        double q11 = (y + y) * y;
        double q01 = (x + x) * y;
        double q03 = (x + x) * w;
        double q13 = (y + y) * w;
        m00 = 1.0 - q11;
        m01 = q01;
        m02 = -q13;
        m10 = q01;
        m11 = 1.0 - q00;
        m12 = q03;
        m20 = q13;
        m21 = -q03;
        m22 = 1.0 - q11 - q00;
        m30 = objPos.x();
        m31 = objPos.y();
        m32 = objPos.z();
        properties = 0;
        return this;
    }

    public int hashCode() {
        final int prime = 31;
        int result = 1;
        long temp;
        temp = Double.doubleToLongBits(m00);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(m01);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(m02);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(m10);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(m11);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(m12);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(m20);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(m21);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(m22);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(m30);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(m31);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(m32);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Matrix4x3dc))
            return false;
        Matrix4x3dc other = (Matrix4x3dc) obj;
        if (Double.doubleToLongBits(m00) != Double.doubleToLongBits(other.m00()))
            return false;
        if (Double.doubleToLongBits(m01) != Double.doubleToLongBits(other.m01()))
            return false;
        if (Double.doubleToLongBits(m02) != Double.doubleToLongBits(other.m02()))
            return false;
        if (Double.doubleToLongBits(m10) != Double.doubleToLongBits(other.m10()))
            return false;
        if (Double.doubleToLongBits(m11) != Double.doubleToLongBits(other.m11()))
            return false;
        if (Double.doubleToLongBits(m12) != Double.doubleToLongBits(other.m12()))
            return false;
        if (Double.doubleToLongBits(m20) != Double.doubleToLongBits(other.m20()))
            return false;
        if (Double.doubleToLongBits(m21) != Double.doubleToLongBits(other.m21()))
            return false;
        if (Double.doubleToLongBits(m22) != Double.doubleToLongBits(other.m22()))
            return false;
        if (Double.doubleToLongBits(m30) != Double.doubleToLongBits(other.m30()))
            return false;
        if (Double.doubleToLongBits(m31) != Double.doubleToLongBits(other.m31()))
            return false;
        if (Double.doubleToLongBits(m32) != Double.doubleToLongBits(other.m32()))
            return false;
        return true;
    }

    public Matrix4x3dc pick(double x, double y, double width, double height, int[] viewport, Matrix4x3dc dest) {
        double sx = viewport[2] / width;
        double sy = viewport[3] / height;
        double tx = (viewport[2] + 2.0 * (viewport[0] - x)) / width;
        double ty = (viewport[3] + 2.0 * (viewport[1] - y)) / height;
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

    public Matrix4x3dc pick(double x, double y, double width, double height, int[] viewport) {
        return pick(x, y, width, height, viewport, this);
    }

    public Matrix4x3dc swap(Matrix4x3dc other) {
        double tmp;
        tmp = m00;
        m00 = other.m00();
        other.m00(tmp);
        tmp = m01;
        m01 = other.m01();
        other.m01(tmp);
        tmp = m02;
        m02 = other.m02();
        other.m02(tmp);
        tmp = m10;
        m10 = other.m10();
        other.m10(tmp);
        tmp = m11;
        m11 = other.m11();
        other.m11(tmp);
        tmp = m12;
        m12 = other.m12();
        other.m12(tmp);
        tmp = m20;
        m20 = other.m20();
        other.m20(tmp);
        tmp = m21;
        m21 = other.m21();
        other.m21(tmp);
        tmp = m22;
        m22 = other.m22();
        other.m22(tmp);
        tmp = m30;
        m30 = other.m30();
        other.m30(tmp);
        tmp = m31;
        m31 = other.m31();
        other.m31(tmp);
        tmp = m32;
        m32 = other.m32();
        other.m32(tmp);
        byte props = properties;
        this.properties = other.properties();
        other.properties(props);
        return this;
    }

    public Matrix4x3dc arcball(double radius, double centerX, double centerY, double centerZ, double angleX, double angleY, Matrix4x3dc dest) {
        double m30 = m20 * -radius + this.m30;
        double m31 = m21 * -radius + this.m31;
        double m32 = m22 * -radius + this.m32;
        double sin = Math.sin(angleX);
        double cos = Math.cosFromSin(sin, angleX);
        double nm10 = m10 * cos + m20 * sin;
        double nm11 = m11 * cos + m21 * sin;
        double nm12 = m12 * cos + m22 * sin;
        double m20 = this.m20 * cos - m10 * sin;
        double m21 = this.m21 * cos - m11 * sin;
        double m22 = this.m22 * cos - m12 * sin;
        sin = Math.sin(angleY);
        cos = Math.cosFromSin(sin, angleY);
        double nm00 = m00 * cos - m20 * sin;
        double nm01 = m01 * cos - m21 * sin;
        double nm02 = m02 * cos - m22 * sin;
        double nm20 = m00 * sin + m20 * cos;
        double nm21 = m01 * sin + m21 * cos;
        double nm22 = m02 * sin + m22 * cos;
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

    public Matrix4x3dc arcball(double radius, IVector3d center, double angleX, double angleY, Matrix4x3dc dest) {
        return arcball(radius, center.x(), center.y(), center.z(), angleX, angleY, dest);
    }

    public Matrix4x3dc arcball(double radius, double centerX, double centerY, double centerZ, double angleX, double angleY) {
        return arcball(radius, centerX, centerY, centerZ, angleX, angleY, this);
    }

    public Matrix4x3dc arcball(double radius, IVector3d center, double angleX, double angleY) {
        return arcball(radius, center.x(), center.y(), center.z(), angleX, angleY, this);
    }

    public Matrix4x3dc transformAab(double minX, double minY, double minZ, double maxX, double maxY, double maxZ, Vector3dc outMin, Vector3dc outMax) {
        double xax = m00 * minX, xay = m01 * minX, xaz = m02 * minX;
        double xbx = m00 * maxX, xby = m01 * maxX, xbz = m02 * maxX;
        double yax = m10 * minY, yay = m11 * minY, yaz = m12 * minY;
        double ybx = m10 * maxY, yby = m11 * maxY, ybz = m12 * maxY;
        double zax = m20 * minZ, zay = m21 * minZ, zaz = m22 * minZ;
        double zbx = m20 * maxZ, zby = m21 * maxZ, zbz = m22 * maxZ;
        double xminx, xminy, xminz, yminx, yminy, yminz, zminx, zminy, zminz;
        double xmaxx, xmaxy, xmaxz, ymaxx, ymaxy, ymaxz, zmaxx, zmaxy, zmaxz;
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

    public Matrix4x3dc transformAab(IVector3d min, IVector3d max, Vector3dc outMin, Vector3dc outMax) {
        return transformAab(min.x(), min.y(), min.z(), max.x(), max.y(), max.z(), outMin, outMax);
    }

    public Matrix4x3dc lerp(IMatrix4x3d other, double t) {
        return lerp(other, t, this);
    }

    public Matrix4x3dc lerp(IMatrix4x3d other, double t, Matrix4x3dc dest) {
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

    public Matrix4x3dc rotateTowards(IVector3d dir, IVector3d up, Matrix4x3dc dest) {
        return rotateTowards(dir.x(), dir.y(), dir.z(), up.x(), up.y(), up.z(), dest);
    }

    public Matrix4x3dc rotateTowards(IVector3d dir, IVector3d up) {
        return rotateTowards(dir.x(), dir.y(), dir.z(), up.x(), up.y(), up.z(), this);
    }

    public Matrix4x3dc rotateTowards(double dirX, double dirY, double dirZ, double upX, double upY, double upZ) {
        return rotateTowards(dirX, dirY, dirZ, upX, upY, upZ, this);
    }

    public Matrix4x3dc rotateTowards(double dirX, double dirY, double dirZ, double upX, double upY, double upZ, Matrix4x3dc dest) {
        // Normalize direction
        double invDirLength = 1.0 / Math.sqrt(dirX * dirX + dirY * dirY + dirZ * dirZ);
        double ndirX = dirX * invDirLength;
        double ndirY = dirY * invDirLength;
        double ndirZ = dirZ * invDirLength;
        // left = up x direction
        double leftX, leftY, leftZ;
        leftX = upY * ndirZ - upZ * ndirY;
        leftY = upZ * ndirX - upX * ndirZ;
        leftZ = upX * ndirY - upY * ndirX;
        // normalize left
        double invLeftLength = 1.0 / Math.sqrt(leftX * leftX + leftY * leftY + leftZ * leftZ);
        leftX *= invLeftLength;
        leftY *= invLeftLength;
        leftZ *= invLeftLength;
        // up = direction x left
        double upnX = ndirY * leftZ - ndirZ * leftY;
        double upnY = ndirZ * leftX - ndirX * leftZ;
        double upnZ = ndirX * leftY - ndirY * leftX;
        double rm00 = leftX;
        double rm01 = leftY;
        double rm02 = leftZ;
        double rm10 = upnX;
        double rm11 = upnY;
        double rm12 = upnZ;
        double rm20 = ndirX;
        double rm21 = ndirY;
        double rm22 = ndirZ;
        dest.m30(m30);
        dest.m31(m31);
        dest.m32(m32);
        double nm00 = m00 * rm00 + m10 * rm01 + m20 * rm02;
        double nm01 = m01 * rm00 + m11 * rm01 + m21 * rm02;
        double nm02 = m02 * rm00 + m12 * rm01 + m22 * rm02;
        double nm10 = m00 * rm10 + m10 * rm11 + m20 * rm12;
        double nm11 = m01 * rm10 + m11 * rm11 + m21 * rm12;
        double nm12 = m02 * rm10 + m12 * rm11 + m22 * rm12;
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

    public Matrix4x3dc rotationTowards(IVector3d dir, IVector3d up) {
        return rotationTowards(dir.x(), dir.y(), dir.z(), up.x(), up.y(), up.z());
    }

    public Matrix4x3dc rotationTowards(double dirX, double dirY, double dirZ, double upX, double upY, double upZ) {
        // Normalize direction
        double invDirLength = 1.0 / Math.sqrt(dirX * dirX + dirY * dirY + dirZ * dirZ);
        double ndirX = dirX * invDirLength;
        double ndirY = dirY * invDirLength;
        double ndirZ = dirZ * invDirLength;
        // left = up x direction
        double leftX, leftY, leftZ;
        leftX = upY * ndirZ - upZ * ndirY;
        leftY = upZ * ndirX - upX * ndirZ;
        leftZ = upX * ndirY - upY * ndirX;
        // normalize left
        double invLeftLength = 1.0 / Math.sqrt(leftX * leftX + leftY * leftY + leftZ * leftZ);
        leftX *= invLeftLength;
        leftY *= invLeftLength;
        leftZ *= invLeftLength;
        // up = direction x left
        double upnX = ndirY * leftZ - ndirZ * leftY;
        double upnY = ndirZ * leftX - ndirX * leftZ;
        double upnZ = ndirX * leftY - ndirY * leftX;
        this.m00 = leftX;
        this.m01 = leftY;
        this.m02 = leftZ;
        this.m10 = upnX;
        this.m11 = upnY;
        this.m12 = upnZ;
        this.m20 = ndirX;
        this.m21 = ndirY;
        this.m22 = ndirZ;
        this.m30 = 0.0;
        this.m31 = 0.0;
        this.m32 = 0.0;
        properties = 0;
        return this;
    }

    public Matrix4x3dc translationRotateTowards(IVector3d pos, IVector3d dir, IVector3d up) {
        return translationRotateTowards(pos.x(), pos.y(), pos.z(), dir.x(), dir.y(), dir.z(), up.x(), up.y(), up.z());
    }

    public Matrix4x3dc translationRotateTowards(double posX, double posY, double posZ, double dirX, double dirY, double dirZ, double upX, double upY, double upZ) {
        // Normalize direction
        double invDirLength = 1.0f / Math.sqrt(dirX * dirX + dirY * dirY + dirZ * dirZ);
        double ndirX = dirX * invDirLength;
        double ndirY = dirY * invDirLength;
        double ndirZ = dirZ * invDirLength;
        // left = up x direction
        double leftX, leftY, leftZ;
        leftX = upY * ndirZ - upZ * ndirY;
        leftY = upZ * ndirX - upX * ndirZ;
        leftZ = upX * ndirY - upY * ndirX;
        // normalize left
        double invLeftLength = 1.0f / Math.sqrt(leftX * leftX + leftY * leftY + leftZ * leftZ);
        leftX *= invLeftLength;
        leftY *= invLeftLength;
        leftZ *= invLeftLength;
        // up = direction x left
        double upnX = ndirY * leftZ - ndirZ * leftY;
        double upnY = ndirZ * leftX - ndirX * leftZ;
        double upnZ = ndirX * leftY - ndirY * leftX;
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

    public Vector3dc getEulerAnglesZYX(Vector3dc dest) {
        dest.set(Math.atan2(m12, m22), Math.atan2(-m02, Math.sqrt(m12 * m12 + m22 * m22)), Math.atan2(m01, m00));
        return dest;
    }
}
