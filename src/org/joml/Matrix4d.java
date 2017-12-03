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
 * Contains the definition of a 4x4 Matrix of doubles, and associated functions to transform it. The matrix is
 * column-major to match OpenGL's interpretation, and it looks like this:
 * <p>
 * m00  m10  m20  m30<br> m01  m11  m21  m31<br> m02  m12  m22  m32<br> m03  m13  m23  m33<br>
 *
 * @author Richard Greenlees
 * @author Kai Burjack
 */
public class Matrix4d extends Matrix4dc implements Externalizable {

    private static final long serialVersionUID = 1L;

    double m00, m01, m02, m03;
    double m10, m11, m12, m13;
    double m20, m21, m22, m23;
    double m30, m31, m32, m33;

    byte properties;

    /**
     * Create a new {@link Matrix4dc} and set it to {@link #identity() identity}.
     */
    public Matrix4d() {
        m00 = 1.0;
        m11 = 1.0;
        m22 = 1.0;
        m33 = 1.0;
        properties = PROPERTY_IDENTITY | PROPERTY_AFFINE | PROPERTY_TRANSLATION;
    }

    /**
     * Create a new {@link Matrix4dc} and make it a copy of the given matrix.
     *
     * @param mat the {@link IMatrix4d} to copy the values from
     */
    public Matrix4d(IMatrix4d mat) {
        m00 = mat.m00();
        m01 = mat.m01();
        m02 = mat.m02();
        m03 = mat.m03();
        m10 = mat.m10();
        m11 = mat.m11();
        m12 = mat.m12();
        m13 = mat.m13();
        m20 = mat.m20();
        m21 = mat.m21();
        m22 = mat.m22();
        m23 = mat.m23();
        m30 = mat.m30();
        m31 = mat.m31();
        m32 = mat.m32();
        m33 = mat.m33();
        properties = mat.properties();
    }

    /**
     * Create a new {@link Matrix4dc} and make it a copy of the given matrix.
     *
     * @param mat the {@link IMatrix4f} to copy the values from
     */
    public Matrix4d(IMatrix4f mat) {
        m00 = mat.m00();
        m01 = mat.m01();
        m02 = mat.m02();
        m03 = mat.m03();
        m10 = mat.m10();
        m11 = mat.m11();
        m12 = mat.m12();
        m13 = mat.m13();
        m20 = mat.m20();
        m21 = mat.m21();
        m22 = mat.m22();
        m23 = mat.m23();
        m30 = mat.m30();
        m31 = mat.m31();
        m32 = mat.m32();
        m33 = mat.m33();
        properties = mat.properties();
    }

    /**
     * Create a new {@link Matrix4dc} and set its upper 4x3 submatrix to the given matrix <code>mat</code> and all other
     * elements to identity.
     *
     * @param mat the {@link IMatrix4x3d} to copy the values from
     */
    public Matrix4d(IMatrix4x3d mat) {
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
        m33 = 1.0;
        properties = (byte) (mat.properties() | PROPERTY_AFFINE);
    }

    /**
     * Create a new {@link Matrix4dc} and set its upper 4x3 submatrix to the given matrix <code>mat</code> and all other
     * elements to identity.
     *
     * @param mat the {@link IMatrix4x3f} to copy the values from
     */
    public Matrix4d(IMatrix4x3f mat) {
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
        m33 = 1.0;
        properties = (byte) (mat.properties() | PROPERTY_AFFINE);
    }

    /**
     * Create a new {@link Matrix4dc} by setting its uppper left 3x3 submatrix to the values of the given {@link
     * IMatrix3d} and the rest to identity.
     *
     * @param mat the {@link IMatrix3d}
     */
    public Matrix4d(IMatrix3d mat) {
        m00 = mat.m00();
        m01 = mat.m01();
        m02 = mat.m02();
        m10 = mat.m10();
        m11 = mat.m11();
        m12 = mat.m12();
        m20 = mat.m20();
        m21 = mat.m21();
        m22 = mat.m22();
        m33 = 1.0;
        properties = PROPERTY_AFFINE;
    }

    /**
     * Create a new 4x4 matrix using the supplied double values.
     * <p>
     * The matrix layout will be:<br><br> m00, m10, m20, m30<br> m01, m11, m21, m31<br> m02, m12, m22, m32<br> m03, m13,
     * m23, m33
     *
     * @param m00 the value of m00
     * @param m01 the value of m01
     * @param m02 the value of m02
     * @param m03 the value of m03
     * @param m10 the value of m10
     * @param m11 the value of m11
     * @param m12 the value of m12
     * @param m13 the value of m13
     * @param m20 the value of m20
     * @param m21 the value of m21
     * @param m22 the value of m22
     * @param m23 the value of m23
     * @param m30 the value of m30
     * @param m31 the value of m31
     * @param m32 the value of m32
     * @param m33 the value of m33
     */
    public Matrix4d(double m00, double m01, double m02, double m03,
                    double m10, double m11, double m12, double m13,
                    double m20, double m21, double m22, double m23,
                    double m30, double m31, double m32, double m33) {
        this.m00 = m00;
        this.m01 = m01;
        this.m02 = m02;
        this.m03 = m03;
        this.m10 = m10;
        this.m11 = m11;
        this.m12 = m12;
        this.m13 = m13;
        this.m20 = m20;
        this.m21 = m21;
        this.m22 = m22;
        this.m23 = m23;
        this.m30 = m30;
        this.m31 = m31;
        this.m32 = m32;
        this.m33 = m33;
        properties = 0;
    }

    //#ifdef __HAS_NIO__

    /**
     * Create a new {@link Matrix4dc} by reading its 16 double components from the given {@link DoubleBuffer} at the
     * buffer's current position.
     * <p>
     * That DoubleBuffer is expected to hold the values in column-major order.
     * <p>
     * The buffer's position will not be changed by this method.
     *
     * @param buffer the {@link DoubleBuffer} to read the matrix values from
     */
    public Matrix4d(DoubleBuffer buffer) {
        MemUtil.INSTANCE.get(this, buffer.position(), buffer);
    }
    //#endif

    /**
     * Create a new {@link Matrix4dc} and initialize its four columns using the supplied vectors.
     *
     * @param col0 the first column
     * @param col1 the second column
     * @param col2 the third column
     * @param col3 the fourth column
     */
    public Matrix4d(IVector4d col0, IVector4d col1, IVector4d col2, IVector4d col3) {
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

    public Matrix4dc assumeNothing() {
        properties = 0;
        return this;
    }

    public Matrix4dc assumeAffine() {
        properties = PROPERTY_AFFINE;
        return this;
    }

    public Matrix4dc assumePerspective() {
        properties = PROPERTY_PERSPECTIVE;
        return this;
    }

    public Matrix4dc property(int property) {
        this.properties = (byte) property;
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

    public double m03() {
        return m03;
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

    public double m13() {
        return m13;
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

    public double m23() {
        return m23;
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

    public double m33() {
        return m33;
    }

    public Matrix4dc m00(double m00) {
        this.m00 = m00;
        properties &= ~(PROPERTY_IDENTITY | PROPERTY_TRANSLATION);
        return this;
    }

    public Matrix4dc m01(double m01) {
        this.m01 = m01;
        properties &= ~(PROPERTY_IDENTITY | PROPERTY_PERSPECTIVE | PROPERTY_TRANSLATION);
        return this;
    }

    public Matrix4dc m02(double m02) {
        this.m02 = m02;
        properties &= ~(PROPERTY_IDENTITY | PROPERTY_PERSPECTIVE | PROPERTY_TRANSLATION);
        return this;
    }

    public Matrix4dc m03(double m03) {
        this.m03 = m03;
        properties = 0;
        return this;
    }

    public Matrix4dc m10(double m10) {
        this.m10 = m10;
        properties &= ~(PROPERTY_IDENTITY | PROPERTY_PERSPECTIVE | PROPERTY_TRANSLATION);
        return this;
    }

    public Matrix4dc m11(double m11) {
        this.m11 = m11;
        properties &= ~(PROPERTY_IDENTITY | PROPERTY_TRANSLATION);
        return this;
    }

    public Matrix4dc m12(double m12) {
        this.m12 = m12;
        properties &= ~(PROPERTY_IDENTITY | PROPERTY_PERSPECTIVE | PROPERTY_TRANSLATION);
        return this;
    }

    public Matrix4dc m13(double m13) {
        this.m13 = m13;
        properties = 0;
        return this;
    }

    public Matrix4dc m20(double m20) {
        this.m20 = m20;
        properties &= ~(PROPERTY_IDENTITY | PROPERTY_PERSPECTIVE | PROPERTY_TRANSLATION);
        return this;
    }

    public Matrix4dc m21(double m21) {
        this.m21 = m21;
        properties &= ~(PROPERTY_IDENTITY | PROPERTY_PERSPECTIVE | PROPERTY_TRANSLATION);
        return this;
    }

    public Matrix4dc m22(double m22) {
        this.m22 = m22;
        properties &= ~(PROPERTY_IDENTITY | PROPERTY_TRANSLATION);
        return this;
    }

    public Matrix4dc m23(double m23) {
        this.m23 = m23;
        properties &= ~(PROPERTY_IDENTITY | PROPERTY_AFFINE | PROPERTY_TRANSLATION);
        return this;
    }

    public Matrix4dc m30(double m30) {
        this.m30 = m30;
        properties &= ~(PROPERTY_IDENTITY | PROPERTY_PERSPECTIVE);
        return this;
    }

    public Matrix4dc m31(double m31) {
        this.m31 = m31;
        properties &= ~(PROPERTY_IDENTITY | PROPERTY_PERSPECTIVE);
        return this;
    }

    public Matrix4dc m32(double m32) {
        this.m32 = m32;
        properties &= ~(PROPERTY_IDENTITY | PROPERTY_PERSPECTIVE);
        return this;
    }

    public Matrix4dc m33(double m33) {
        this.m33 = m33;
        properties = 0;
        return this;
    }

    public Matrix4dc identity() {
        if ((properties & PROPERTY_IDENTITY) != 0)
            return this;
        m00 = 1.0;
        m10 = 0.0;
        m20 = 0.0;
        m30 = 0.0;
        m01 = 0.0;
        m11 = 1.0;
        m21 = 0.0;
        m31 = 0.0;
        m02 = 0.0;
        m12 = 0.0;
        m22 = 1.0;
        m32 = 0.0;
        m03 = 0.0;
        m13 = 0.0;
        m23 = 0.0;
        m33 = 1.0;
        properties = PROPERTY_IDENTITY | PROPERTY_AFFINE | PROPERTY_TRANSLATION;
        return this;
    }

    public Matrix4dc set(IMatrix4d m) {
        m00 = m.m00();
        m01 = m.m01();
        m02 = m.m02();
        m03 = m.m03();
        m10 = m.m10();
        m11 = m.m11();
        m12 = m.m12();
        m13 = m.m13();
        m20 = m.m20();
        m21 = m.m21();
        m22 = m.m22();
        m23 = m.m23();
        m30 = m.m30();
        m31 = m.m31();
        m32 = m.m32();
        m33 = m.m33();
        properties = m.properties();
        return this;
    }

    public Matrix4dc set(IMatrix4f m) {
        m00 = m.m00();
        m01 = m.m01();
        m02 = m.m02();
        m03 = m.m03();
        m10 = m.m10();
        m11 = m.m11();
        m12 = m.m12();
        m13 = m.m13();
        m20 = m.m20();
        m21 = m.m21();
        m22 = m.m22();
        m23 = m.m23();
        m30 = m.m30();
        m31 = m.m31();
        m32 = m.m32();
        m33 = m.m33();
        properties = m.properties();
        return this;
    }

    public Matrix4dc set(IMatrix4x3d m) {
        m00 = m.m00();
        m01 = m.m01();
        m02 = m.m02();
        m03 = 0.0;
        m10 = m.m10();
        m11 = m.m11();
        m12 = m.m12();
        m13 = 0.0;
        m20 = m.m20();
        m21 = m.m21();
        m22 = m.m22();
        m23 = 0.0;
        m30 = m.m30();
        m31 = m.m31();
        m32 = m.m32();
        m33 = 1.0;
        properties = (byte) (m.properties() | PROPERTY_AFFINE);
        return this;
    }

    public Matrix4dc set(IMatrix4x3f m) {
        m00 = m.m00();
        m01 = m.m01();
        m02 = m.m02();
        m03 = 0.0;
        m10 = m.m10();
        m11 = m.m11();
        m12 = m.m12();
        m13 = 0.0;
        m20 = m.m20();
        m21 = m.m21();
        m22 = m.m22();
        m23 = 0.0;
        m30 = m.m30();
        m31 = m.m31();
        m32 = m.m32();
        m33 = 1.0;
        properties = (byte) (m.properties() | PROPERTY_AFFINE);
        return this;
    }

    public Matrix4dc set(IMatrix3d mat) {
        m00 = mat.m00();
        m01 = mat.m01();
        m02 = mat.m02();
        m03 = 0.0;
        m10 = mat.m10();
        m11 = mat.m11();
        m12 = mat.m12();
        m13 = 0.0;
        m20 = mat.m20();
        m21 = mat.m21();
        m22 = mat.m22();
        m23 = 0.0;
        m30 = 0.0;
        m31 = 0.0;
        m32 = 0.0;
        m33 = 1.0;
        properties = PROPERTY_AFFINE;
        return this;
    }

    public Matrix4dc set3x3(IMatrix4d mat) {
        m00 = mat.m00();
        m01 = mat.m01();
        m02 = mat.m02();
        m10 = mat.m10();
        m11 = mat.m11();
        m12 = mat.m12();
        m20 = mat.m20();
        m21 = mat.m21();
        m22 = mat.m22();
        properties &= mat.properties() & ~(PROPERTY_PERSPECTIVE);
        return this;
    }

    public Matrix4dc set4x3(IMatrix4x3d mat) {
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
        properties &= mat.properties() & ~(PROPERTY_PERSPECTIVE);
        return this;
    }

    public Matrix4dc set4x3(IMatrix4x3f mat) {
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
        properties &= mat.properties() & ~(PROPERTY_PERSPECTIVE);
        return this;
    }

    public Matrix4dc set4x3(IMatrix4d mat) {
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
        properties &= mat.properties() & ~(PROPERTY_PERSPECTIVE);
        return this;
    }

    public Matrix4dc set(AxisAngle4fc axisAngle) {
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
        m03 = 0.0;
        m13 = 0.0;
        m23 = 0.0;
        m30 = 0.0;
        m31 = 0.0;
        m32 = 0.0;
        m33 = 1.0;
        properties = PROPERTY_AFFINE;
        return this;
    }

    public Matrix4dc set(AxisAngle4dc axisAngle) {
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
        m03 = 0.0;
        m13 = 0.0;
        m23 = 0.0;
        m30 = 0.0;
        m31 = 0.0;
        m32 = 0.0;
        m33 = 1.0;
        properties = PROPERTY_AFFINE;
        return this;
    }

    public Matrix4dc set(IQuaternionf q) {
        return rotation(q);
    }

    public Matrix4dc set(IQuaterniond q) {
        return rotation(q);
    }

    public Matrix4dc mul(IMatrix4d right) {
        return mul(right, this);
    }

    public Matrix4dc mul(IMatrix4d right, Matrix4dc dest) {
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

    private Matrix4dc mulGeneric(IMatrix4d right, Matrix4dc dest) {
        double nm00 = m00 * right.m00() + m10 * right.m01() + m20 * right.m02() + m30 * right.m03();
        double nm01 = m01 * right.m00() + m11 * right.m01() + m21 * right.m02() + m31 * right.m03();
        double nm02 = m02 * right.m00() + m12 * right.m01() + m22 * right.m02() + m32 * right.m03();
        double nm03 = m03 * right.m00() + m13 * right.m01() + m23 * right.m02() + m33 * right.m03();
        double nm10 = m00 * right.m10() + m10 * right.m11() + m20 * right.m12() + m30 * right.m13();
        double nm11 = m01 * right.m10() + m11 * right.m11() + m21 * right.m12() + m31 * right.m13();
        double nm12 = m02 * right.m10() + m12 * right.m11() + m22 * right.m12() + m32 * right.m13();
        double nm13 = m03 * right.m10() + m13 * right.m11() + m23 * right.m12() + m33 * right.m13();
        double nm20 = m00 * right.m20() + m10 * right.m21() + m20 * right.m22() + m30 * right.m23();
        double nm21 = m01 * right.m20() + m11 * right.m21() + m21 * right.m22() + m31 * right.m23();
        double nm22 = m02 * right.m20() + m12 * right.m21() + m22 * right.m22() + m32 * right.m23();
        double nm23 = m03 * right.m20() + m13 * right.m21() + m23 * right.m22() + m33 * right.m23();
        double nm30 = m00 * right.m30() + m10 * right.m31() + m20 * right.m32() + m30 * right.m33();
        double nm31 = m01 * right.m30() + m11 * right.m31() + m21 * right.m32() + m31 * right.m33();
        double nm32 = m02 * right.m30() + m12 * right.m31() + m22 * right.m32() + m32 * right.m33();
        double nm33 = m03 * right.m30() + m13 * right.m31() + m23 * right.m32() + m33 * right.m33();
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
        dest.property(0);
        return dest;
    }

    public Matrix4dc mulLocal(IMatrix4d left) {
        return mulLocal(left, this);
    }

    public Matrix4dc mulLocal(IMatrix4d left, Matrix4dc dest) {
        if ((properties & PROPERTY_IDENTITY) != 0)
            return dest.set(left);
        else if ((left.properties() & PROPERTY_IDENTITY) != 0)
            return dest.set(this);
        else if ((properties & PROPERTY_AFFINE) != 0 && (left.properties() & PROPERTY_AFFINE) != 0)
            return mulLocalAffine(left, dest);
        return mulLocalGeneric(left, dest);
    }

    private Matrix4dc mulLocalGeneric(IMatrix4d left, Matrix4dc dest) {
        double nm00 = left.m00() * m00 + left.m10() * m01 + left.m20() * m02 + left.m30() * m03;
        double nm01 = left.m01() * m00 + left.m11() * m01 + left.m21() * m02 + left.m31() * m03;
        double nm02 = left.m02() * m00 + left.m12() * m01 + left.m22() * m02 + left.m32() * m03;
        double nm03 = left.m03() * m00 + left.m13() * m01 + left.m23() * m02 + left.m33() * m03;
        double nm10 = left.m00() * m10 + left.m10() * m11 + left.m20() * m12 + left.m30() * m13;
        double nm11 = left.m01() * m10 + left.m11() * m11 + left.m21() * m12 + left.m31() * m13;
        double nm12 = left.m02() * m10 + left.m12() * m11 + left.m22() * m12 + left.m32() * m13;
        double nm13 = left.m03() * m10 + left.m13() * m11 + left.m23() * m12 + left.m33() * m13;
        double nm20 = left.m00() * m20 + left.m10() * m21 + left.m20() * m22 + left.m30() * m23;
        double nm21 = left.m01() * m20 + left.m11() * m21 + left.m21() * m22 + left.m31() * m23;
        double nm22 = left.m02() * m20 + left.m12() * m21 + left.m22() * m22 + left.m32() * m23;
        double nm23 = left.m03() * m20 + left.m13() * m21 + left.m23() * m22 + left.m33() * m23;
        double nm30 = left.m00() * m30 + left.m10() * m31 + left.m20() * m32 + left.m30() * m33;
        double nm31 = left.m01() * m30 + left.m11() * m31 + left.m21() * m32 + left.m31() * m33;
        double nm32 = left.m02() * m30 + left.m12() * m31 + left.m22() * m32 + left.m32() * m33;
        double nm33 = left.m03() * m30 + left.m13() * m31 + left.m23() * m32 + left.m33() * m33;
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
        dest.property(0);
        return dest;
    }

    public Matrix4dc mulLocalAffine(IMatrix4d left) {
        return mulLocalAffine(left, this);
    }

    public Matrix4dc mulLocalAffine(IMatrix4d left, Matrix4dc dest) {
        double nm00 = left.m00() * m00 + left.m10() * m01 + left.m20() * m02;
        double nm01 = left.m01() * m00 + left.m11() * m01 + left.m21() * m02;
        double nm02 = left.m02() * m00 + left.m12() * m01 + left.m22() * m02;
        double nm03 = left.m03();
        double nm10 = left.m00() * m10 + left.m10() * m11 + left.m20() * m12;
        double nm11 = left.m01() * m10 + left.m11() * m11 + left.m21() * m12;
        double nm12 = left.m02() * m10 + left.m12() * m11 + left.m22() * m12;
        double nm13 = left.m13();
        double nm20 = left.m00() * m20 + left.m10() * m21 + left.m20() * m22;
        double nm21 = left.m01() * m20 + left.m11() * m21 + left.m21() * m22;
        double nm22 = left.m02() * m20 + left.m12() * m21 + left.m22() * m22;
        double nm23 = left.m23();
        double nm30 = left.m00() * m30 + left.m10() * m31 + left.m20() * m32 + left.m30();
        double nm31 = left.m01() * m30 + left.m11() * m31 + left.m21() * m32 + left.m31();
        double nm32 = left.m02() * m30 + left.m12() * m31 + left.m22() * m32 + left.m32();
        double nm33 = left.m33();
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
        dest.property(PROPERTY_AFFINE);
        return dest;
    }

    public Matrix4dc mul(IMatrix4x3d right) {
        return mul(right, this);
    }

    public Matrix4dc mul(IMatrix4x3d right, Matrix4dc dest) {
        if ((properties & PROPERTY_IDENTITY) != 0)
            return dest.set(right);
        else if ((right.properties() & PROPERTY_IDENTITY) != 0)
            return dest.set(this);
        return mulGeneric(right, dest);
    }

    private Matrix4dc mulGeneric(IMatrix4x3d right, Matrix4dc dest) {
        double nm00 = m00 * right.m00() + m10 * right.m01() + m20 * right.m02();
        double nm01 = m01 * right.m00() + m11 * right.m01() + m21 * right.m02();
        double nm02 = m02 * right.m00() + m12 * right.m01() + m22 * right.m02();
        double nm03 = m03 * right.m00() + m13 * right.m01() + m23 * right.m02();
        double nm10 = m00 * right.m10() + m10 * right.m11() + m20 * right.m12();
        double nm11 = m01 * right.m10() + m11 * right.m11() + m21 * right.m12();
        double nm12 = m02 * right.m10() + m12 * right.m11() + m22 * right.m12();
        double nm13 = m03 * right.m10() + m13 * right.m11() + m23 * right.m12();
        double nm20 = m00 * right.m20() + m10 * right.m21() + m20 * right.m22();
        double nm21 = m01 * right.m20() + m11 * right.m21() + m21 * right.m22();
        double nm22 = m02 * right.m20() + m12 * right.m21() + m22 * right.m22();
        double nm23 = m03 * right.m20() + m13 * right.m21() + m23 * right.m22();
        double nm30 = m00 * right.m30() + m10 * right.m31() + m20 * right.m32() + m30;
        double nm31 = m01 * right.m30() + m11 * right.m31() + m21 * right.m32() + m31;
        double nm32 = m02 * right.m30() + m12 * right.m31() + m22 * right.m32() + m32;
        double nm33 = m03 * right.m30() + m13 * right.m31() + m23 * right.m32() + m33;
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
        dest.property(properties & ~(PROPERTY_IDENTITY | PROPERTY_PERSPECTIVE | PROPERTY_TRANSLATION));
        return dest;
    }

    public Matrix4dc mul(IMatrix4x3f right, Matrix4dc dest) {
        if ((properties & PROPERTY_IDENTITY) != 0)
            return dest.set(right);
        else if ((right.properties() & PROPERTY_IDENTITY) != 0)
            return dest.set(this);
        return mulGeneric(right, dest);
    }

    private Matrix4dc mulGeneric(IMatrix4x3f right, Matrix4dc dest) {
        double nm00 = m00 * right.m00() + m10 * right.m01() + m20 * right.m02();
        double nm01 = m01 * right.m00() + m11 * right.m01() + m21 * right.m02();
        double nm02 = m02 * right.m00() + m12 * right.m01() + m22 * right.m02();
        double nm03 = m03 * right.m00() + m13 * right.m01() + m23 * right.m02();
        double nm10 = m00 * right.m10() + m10 * right.m11() + m20 * right.m12();
        double nm11 = m01 * right.m10() + m11 * right.m11() + m21 * right.m12();
        double nm12 = m02 * right.m10() + m12 * right.m11() + m22 * right.m12();
        double nm13 = m03 * right.m10() + m13 * right.m11() + m23 * right.m12();
        double nm20 = m00 * right.m20() + m10 * right.m21() + m20 * right.m22();
        double nm21 = m01 * right.m20() + m11 * right.m21() + m21 * right.m22();
        double nm22 = m02 * right.m20() + m12 * right.m21() + m22 * right.m22();
        double nm23 = m03 * right.m20() + m13 * right.m21() + m23 * right.m22();
        double nm30 = m00 * right.m30() + m10 * right.m31() + m20 * right.m32() + m30;
        double nm31 = m01 * right.m30() + m11 * right.m31() + m21 * right.m32() + m31;
        double nm32 = m02 * right.m30() + m12 * right.m31() + m22 * right.m32() + m32;
        double nm33 = m03 * right.m30() + m13 * right.m31() + m23 * right.m32() + m33;
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
        dest.property(properties & ~(PROPERTY_IDENTITY | PROPERTY_PERSPECTIVE | PROPERTY_TRANSLATION));
        return dest;
    }

    public Matrix4dc mul(IMatrix3x2d right) {
        return mul(right, this);
    }

    public Matrix4dc mul(IMatrix3x2d right, Matrix4dc dest) {
        double nm00 = m00 * right.m00() + m10 * right.m01();
        double nm01 = m01 * right.m00() + m11 * right.m01();
        double nm02 = m02 * right.m00() + m12 * right.m01();
        double nm03 = m03 * right.m00() + m13 * right.m01();
        double nm10 = m00 * right.m10() + m10 * right.m11();
        double nm11 = m01 * right.m10() + m11 * right.m11();
        double nm12 = m02 * right.m10() + m12 * right.m11();
        double nm13 = m03 * right.m10() + m13 * right.m11();
        double nm30 = m00 * right.m20() + m10 * right.m21() + m30;
        double nm31 = m01 * right.m20() + m11 * right.m21() + m31;
        double nm32 = m02 * right.m20() + m12 * right.m21() + m32;
        double nm33 = m03 * right.m20() + m13 * right.m21() + m33;
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
        dest.property(properties & ~(PROPERTY_IDENTITY | PROPERTY_PERSPECTIVE | PROPERTY_TRANSLATION));
        return dest;
    }

    public Matrix4dc mul(IMatrix3x2f right) {
        return mul(right, this);
    }

    public Matrix4dc mul(IMatrix3x2f right, Matrix4dc dest) {
        double nm00 = m00 * right.m00() + m10 * right.m01();
        double nm01 = m01 * right.m00() + m11 * right.m01();
        double nm02 = m02 * right.m00() + m12 * right.m01();
        double nm03 = m03 * right.m00() + m13 * right.m01();
        double nm10 = m00 * right.m10() + m10 * right.m11();
        double nm11 = m01 * right.m10() + m11 * right.m11();
        double nm12 = m02 * right.m10() + m12 * right.m11();
        double nm13 = m03 * right.m10() + m13 * right.m11();
        double nm30 = m00 * right.m20() + m10 * right.m21() + m30;
        double nm31 = m01 * right.m20() + m11 * right.m21() + m31;
        double nm32 = m02 * right.m20() + m12 * right.m21() + m32;
        double nm33 = m03 * right.m20() + m13 * right.m21() + m33;
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
        dest.property(properties & ~(PROPERTY_IDENTITY | PROPERTY_PERSPECTIVE | PROPERTY_TRANSLATION));
        return dest;
    }

    public Matrix4dc mul(Matrix4fc right) {
        return mul(right, this);
    }

    public Matrix4dc mul(IMatrix4f right, Matrix4dc dest) {
        if ((properties & PROPERTY_IDENTITY) != 0)
            return dest.set(right);
        else if ((right.properties() & PROPERTY_IDENTITY) != 0)
            return dest.set(this);

        double nm00 = m00 * right.m00() + m10 * right.m01() + m20 * right.m02() + m30 * right.m03();
        double nm01 = m01 * right.m00() + m11 * right.m01() + m21 * right.m02() + m31 * right.m03();
        double nm02 = m02 * right.m00() + m12 * right.m01() + m22 * right.m02() + m32 * right.m03();
        double nm03 = m03 * right.m00() + m13 * right.m01() + m23 * right.m02() + m33 * right.m03();
        double nm10 = m00 * right.m10() + m10 * right.m11() + m20 * right.m12() + m30 * right.m13();
        double nm11 = m01 * right.m10() + m11 * right.m11() + m21 * right.m12() + m31 * right.m13();
        double nm12 = m02 * right.m10() + m12 * right.m11() + m22 * right.m12() + m32 * right.m13();
        double nm13 = m03 * right.m10() + m13 * right.m11() + m23 * right.m12() + m33 * right.m13();
        double nm20 = m00 * right.m20() + m10 * right.m21() + m20 * right.m22() + m30 * right.m23();
        double nm21 = m01 * right.m20() + m11 * right.m21() + m21 * right.m22() + m31 * right.m23();
        double nm22 = m02 * right.m20() + m12 * right.m21() + m22 * right.m22() + m32 * right.m23();
        double nm23 = m03 * right.m20() + m13 * right.m21() + m23 * right.m22() + m33 * right.m23();
        double nm30 = m00 * right.m30() + m10 * right.m31() + m20 * right.m32() + m30 * right.m33();
        double nm31 = m01 * right.m30() + m11 * right.m31() + m21 * right.m32() + m31 * right.m33();
        double nm32 = m02 * right.m30() + m12 * right.m31() + m22 * right.m32() + m32 * right.m33();
        double nm33 = m03 * right.m30() + m13 * right.m31() + m23 * right.m32() + m33 * right.m33();
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
        dest.property(0);
        return dest;
    }

    public Matrix4dc mulPerspectiveAffine(IMatrix4d view) {
        return mulPerspectiveAffine(view, this);
    }

    public Matrix4dc mulPerspectiveAffine(IMatrix4d view, Matrix4dc dest) {
        double nm00 = m00 * view.m00();
        double nm01 = m11 * view.m01();
        double nm02 = m22 * view.m02();
        double nm03 = m23 * view.m02();
        double nm10 = m00 * view.m10();
        double nm11 = m11 * view.m11();
        double nm12 = m22 * view.m12();
        double nm13 = m23 * view.m12();
        double nm20 = m00 * view.m20();
        double nm21 = m11 * view.m21();
        double nm22 = m22 * view.m22();
        double nm23 = m23 * view.m22();
        double nm30 = m00 * view.m30();
        double nm31 = m11 * view.m31();
        double nm32 = m22 * view.m32() + m32;
        double nm33 = m23 * view.m32();
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
        dest.property(0);
        return dest;
    }

    public Matrix4dc mulAffineR(IMatrix4d right) {
        return mulAffineR(right, this);
    }

    public Matrix4dc mulAffineR(IMatrix4d right, Matrix4dc dest) {
        double nm00 = m00 * right.m00() + m10 * right.m01() + m20 * right.m02();
        double nm01 = m01 * right.m00() + m11 * right.m01() + m21 * right.m02();
        double nm02 = m02 * right.m00() + m12 * right.m01() + m22 * right.m02();
        double nm03 = m03 * right.m00() + m13 * right.m01() + m23 * right.m02();
        double nm10 = m00 * right.m10() + m10 * right.m11() + m20 * right.m12();
        double nm11 = m01 * right.m10() + m11 * right.m11() + m21 * right.m12();
        double nm12 = m02 * right.m10() + m12 * right.m11() + m22 * right.m12();
        double nm13 = m03 * right.m10() + m13 * right.m11() + m23 * right.m12();
        double nm20 = m00 * right.m20() + m10 * right.m21() + m20 * right.m22();
        double nm21 = m01 * right.m20() + m11 * right.m21() + m21 * right.m22();
        double nm22 = m02 * right.m20() + m12 * right.m21() + m22 * right.m22();
        double nm23 = m03 * right.m20() + m13 * right.m21() + m23 * right.m22();
        double nm30 = m00 * right.m30() + m10 * right.m31() + m20 * right.m32() + m30;
        double nm31 = m01 * right.m30() + m11 * right.m31() + m21 * right.m32() + m31;
        double nm32 = m02 * right.m30() + m12 * right.m31() + m22 * right.m32() + m32;
        double nm33 = m03 * right.m30() + m13 * right.m31() + m23 * right.m32() + m33;
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
        dest.property(properties & ~(PROPERTY_IDENTITY | PROPERTY_PERSPECTIVE | PROPERTY_TRANSLATION));
        return dest;
    }

    public Matrix4dc mulAffine(IMatrix4d right) {
        return mulAffine(right, this);
    }

    public Matrix4dc mulAffine(IMatrix4d right, Matrix4dc dest) {
        double nm00 = m00 * right.m00() + m10 * right.m01() + m20 * right.m02();
        double nm01 = m01 * right.m00() + m11 * right.m01() + m21 * right.m02();
        double nm02 = m02 * right.m00() + m12 * right.m01() + m22 * right.m02();
        double nm03 = m03;
        double nm10 = m00 * right.m10() + m10 * right.m11() + m20 * right.m12();
        double nm11 = m01 * right.m10() + m11 * right.m11() + m21 * right.m12();
        double nm12 = m02 * right.m10() + m12 * right.m11() + m22 * right.m12();
        double nm13 = m13;
        double nm20 = m00 * right.m20() + m10 * right.m21() + m20 * right.m22();
        double nm21 = m01 * right.m20() + m11 * right.m21() + m21 * right.m22();
        double nm22 = m02 * right.m20() + m12 * right.m21() + m22 * right.m22();
        double nm23 = m23;
        double nm30 = m00 * right.m30() + m10 * right.m31() + m20 * right.m32() + m30;
        double nm31 = m01 * right.m30() + m11 * right.m31() + m21 * right.m32() + m31;
        double nm32 = m02 * right.m30() + m12 * right.m31() + m22 * right.m32() + m32;
        double nm33 = m33;
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
        dest.property(PROPERTY_AFFINE);
        return dest;
    }

    public Matrix4dc mulTranslationAffine(IMatrix4d right, Matrix4dc dest) {
        double nm00 = right.m00();
        double nm01 = right.m01();
        double nm02 = right.m02();
        double nm03 = m03;
        double nm10 = right.m10();
        double nm11 = right.m11();
        double nm12 = right.m12();
        double nm13 = m13;
        double nm20 = right.m20();
        double nm21 = right.m21();
        double nm22 = right.m22();
        double nm23 = m23;
        double nm30 = right.m30() + m30;
        double nm31 = right.m31() + m31;
        double nm32 = right.m32() + m32;
        double nm33 = m33;
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
        dest.property(PROPERTY_AFFINE);
        return dest;
    }

    public Matrix4dc mulOrthoAffine(IMatrix4d view) {
        return mulOrthoAffine(view, this);
    }

    public Matrix4dc mulOrthoAffine(IMatrix4d view, Matrix4dc dest) {
        double nm00 = m00 * view.m00();
        double nm01 = m11 * view.m01();
        double nm02 = m22 * view.m02();
        double nm03 = 0.0;
        double nm10 = m00 * view.m10();
        double nm11 = m11 * view.m11();
        double nm12 = m22 * view.m12();
        double nm13 = 0.0;
        double nm20 = m00 * view.m20();
        double nm21 = m11 * view.m21();
        double nm22 = m22 * view.m22();
        double nm23 = 0.0;
        double nm30 = m00 * view.m30() + m30;
        double nm31 = m11 * view.m31() + m31;
        double nm32 = m22 * view.m32() + m32;
        double nm33 = 1.0;
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
        dest.property(PROPERTY_AFFINE);
        return dest;
    }

    public Matrix4dc fma4x3(IMatrix4d other, double otherFactor) {
        return fma4x3(other, otherFactor, this);
    }

    public Matrix4dc fma4x3(IMatrix4d other, double otherFactor, Matrix4dc dest) {
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
        dest.property(0);
        return dest;
    }

    public Matrix4dc add(IMatrix4d other) {
        return add(other, this);
    }

    public Matrix4dc add(IMatrix4d other, Matrix4dc dest) {
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
        dest.property(0);
        return dest;
    }

    public Matrix4dc sub(IMatrix4d subtrahend) {
        return sub(subtrahend, this);
    }

    public Matrix4dc sub(IMatrix4d subtrahend, Matrix4dc dest) {
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
        dest.property(0);
        return dest;
    }

    public Matrix4dc mulComponentWise(IMatrix4d other) {
        return mulComponentWise(other, this);
    }

    public Matrix4dc mulComponentWise(IMatrix4d other, Matrix4dc dest) {
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
        dest.property(0);
        return dest;
    }

    public Matrix4dc add4x3(IMatrix4d other) {
        return add4x3(other, this);
    }

    public Matrix4dc add4x3(IMatrix4d other, Matrix4dc dest) {
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
        dest.property(0);
        return dest;
    }

    public Matrix4dc add4x3(IMatrix4f other) {
        return add4x3(other, this);
    }

    public Matrix4dc add4x3(IMatrix4f other, Matrix4dc dest) {
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
        dest.property(0);
        return dest;
    }

    public Matrix4dc sub4x3(IMatrix4d subtrahend) {
        return sub4x3(subtrahend, this);
    }

    public Matrix4dc sub4x3(IMatrix4d subtrahend, Matrix4dc dest) {
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
        dest.property(0);
        return dest;
    }

    public Matrix4dc mul4x3ComponentWise(IMatrix4d other) {
        return mul4x3ComponentWise(other, this);
    }

    public Matrix4dc mul4x3ComponentWise(IMatrix4d other, Matrix4dc dest) {
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
        dest.property(0);
        return dest;
    }

    public Matrix4dc set(double m00, double m01, double m02, double m03,
                         double m10, double m11, double m12, double m13,
                         double m20, double m21, double m22, double m23,
                         double m30, double m31, double m32, double m33) {
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
        this.m03 = m03;
        this.m13 = m13;
        this.m23 = m23;
        this.m33 = m33;
        properties = 0;
        return this;
    }

    public Matrix4dc set(double m[], int off) {
        m00 = m[off + 0];
        m01 = m[off + 1];
        m02 = m[off + 2];
        m03 = m[off + 3];
        m10 = m[off + 4];
        m11 = m[off + 5];
        m12 = m[off + 6];
        m13 = m[off + 7];
        m20 = m[off + 8];
        m21 = m[off + 9];
        m22 = m[off + 10];
        m23 = m[off + 11];
        m30 = m[off + 12];
        m31 = m[off + 13];
        m32 = m[off + 14];
        m33 = m[off + 15];
        properties = 0;
        return this;
    }

    public Matrix4dc set(double m[]) {
        return set(m, 0);
    }

    public Matrix4dc set(float m[], int off) {
        m00 = m[off + 0];
        m01 = m[off + 1];
        m02 = m[off + 2];
        m03 = m[off + 3];
        m10 = m[off + 4];
        m11 = m[off + 5];
        m12 = m[off + 6];
        m13 = m[off + 7];
        m20 = m[off + 8];
        m21 = m[off + 9];
        m22 = m[off + 10];
        m23 = m[off + 11];
        m30 = m[off + 12];
        m31 = m[off + 13];
        m32 = m[off + 14];
        m33 = m[off + 15];
        properties = 0;
        return this;
    }

    public Matrix4dc set(float m[]) {
        return set(m, 0);
    }

    //#ifdef __HAS_NIO__

    public Matrix4dc set(DoubleBuffer buffer) {
        MemUtil.INSTANCE.get(this, buffer.position(), buffer);
        properties = 0;
        return this;
    }

    public Matrix4dc set(FloatBuffer buffer) {
        MemUtil.INSTANCE.getf(this, buffer.position(), buffer);
        properties = 0;
        return this;
    }

    public Matrix4dc set(ByteBuffer buffer) {
        MemUtil.INSTANCE.get(this, buffer.position(), buffer);
        properties = 0;
        return this;
    }

    public Matrix4dc setFloats(ByteBuffer buffer) {
        MemUtil.INSTANCE.getf(this, buffer.position(), buffer);
        properties = 0;
        return this;
    }
    //#endif

    public Matrix4dc set(IVector4d col0, IVector4d col1, IVector4d col2, IVector4d col3) {
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
        this.properties = 0;
        return this;
    }

    public double determinant() {
        if ((properties & PROPERTY_AFFINE) != 0)
            return determinantAffine();
        return (m00 * m11 - m01 * m10) * (m22 * m33 - m23 * m32)
                + (m02 * m10 - m00 * m12) * (m21 * m33 - m23 * m31)
                + (m00 * m13 - m03 * m10) * (m21 * m32 - m22 * m31)
                + (m01 * m12 - m02 * m11) * (m20 * m33 - m23 * m30)
                + (m03 * m11 - m01 * m13) * (m20 * m32 - m22 * m30)
                + (m02 * m13 - m03 * m12) * (m20 * m31 - m21 * m30);
    }

    public double determinant3x3() {
        return (m00 * m11 - m01 * m10) * m22
                + (m02 * m10 - m00 * m12) * m21
                + (m01 * m12 - m02 * m11) * m20;
    }

    public double determinantAffine() {
        return (m00 * m11 - m01 * m10) * m22
                + (m02 * m10 - m00 * m12) * m21
                + (m01 * m12 - m02 * m11) * m20;
    }

    public Matrix4dc invert() {
        return invert(this);
    }

    public Matrix4dc invert(Matrix4dc dest) {
        if ((properties & PROPERTY_IDENTITY) != 0)
            return dest.identity();
        else if ((properties & PROPERTY_AFFINE) != 0)
            return invertAffine(dest);
        else if ((properties & PROPERTY_PERSPECTIVE) != 0)
            return invertPerspective(dest);
        return invertGeneric(dest);
    }

    private Matrix4dc invertGeneric(Matrix4dc dest) {
        double a = m00 * m11 - m01 * m10;
        double b = m00 * m12 - m02 * m10;
        double c = m00 * m13 - m03 * m10;
        double d = m01 * m12 - m02 * m11;
        double e = m01 * m13 - m03 * m11;
        double f = m02 * m13 - m03 * m12;
        double g = m20 * m31 - m21 * m30;
        double h = m20 * m32 - m22 * m30;
        double i = m20 * m33 - m23 * m30;
        double j = m21 * m32 - m22 * m31;
        double k = m21 * m33 - m23 * m31;
        double l = m22 * m33 - m23 * m32;
        double det = a * l - b * k + c * j + d * i - e * h + f * g;
        det = 1.0 / det;
        double nm00 = (m11 * l - m12 * k + m13 * j) * det;
        double nm01 = (-m01 * l + m02 * k - m03 * j) * det;
        double nm02 = (m31 * f - m32 * e + m33 * d) * det;
        double nm03 = (-m21 * f + m22 * e - m23 * d) * det;
        double nm10 = (-m10 * l + m12 * i - m13 * h) * det;
        double nm11 = (m00 * l - m02 * i + m03 * h) * det;
        double nm12 = (-m30 * f + m32 * c - m33 * b) * det;
        double nm13 = (m20 * f - m22 * c + m23 * b) * det;
        double nm20 = (m10 * k - m11 * i + m13 * g) * det;
        double nm21 = (-m00 * k + m01 * i - m03 * g) * det;
        double nm22 = (m30 * e - m31 * c + m33 * a) * det;
        double nm23 = (-m20 * e + m21 * c - m23 * a) * det;
        double nm30 = (-m10 * j + m11 * h - m12 * g) * det;
        double nm31 = (m00 * j - m01 * h + m02 * g) * det;
        double nm32 = (-m30 * d + m31 * b - m32 * a) * det;
        double nm33 = (m20 * d - m21 * b + m22 * a) * det;
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
        dest.property(0);
        return dest;
    }

    public Matrix4dc invertPerspective(Matrix4dc dest) {
        double a = 1.0 / (m00 * m11);
        double l = -1.0 / (m23 * m32);
        dest.set(m11 * a, 0, 0, 0,
                0, m00 * a, 0, 0,
                0, 0, 0, -m23 * l,
                0, 0, -m32 * l, m22 * l);
        return dest;
    }

    public Matrix4dc invertPerspective() {
        return invertPerspective(this);
    }

    public Matrix4dc invertFrustum(Matrix4dc dest) {
        double invM00 = 1.0 / m00;
        double invM11 = 1.0 / m11;
        double invM23 = 1.0 / m23;
        double invM32 = 1.0 / m32;
        dest.set(invM00, 0, 0, 0,
                0, invM11, 0, 0,
                0, 0, 0, invM32,
                -m20 * invM00 * invM23, -m21 * invM11 * invM23, invM23, -m22 * invM23 * invM32);
        dest.property(0);
        return dest;
    }

    public Matrix4dc invertFrustum() {
        return invertFrustum(this);
    }

    public Matrix4dc invertOrtho(Matrix4dc dest) {
        double invM00 = 1.0 / m00;
        double invM11 = 1.0 / m11;
        double invM22 = 1.0 / m22;
        dest.set(invM00, 0, 0, 0,
                0, invM11, 0, 0,
                0, 0, invM22, 0,
                -m30 * invM00, -m31 * invM11, -m32 * invM22, 1);
        dest.property(PROPERTY_AFFINE);
        return dest;
    }

    public Matrix4dc invertOrtho() {
        return invertOrtho(this);
    }

    public Matrix4dc invertPerspectiveView(IMatrix4d view, Matrix4dc dest) {
        double a = 1.0 / (m00 * m11);
        double l = -1.0 / (m23 * m32);
        double pm00 = m11 * a;
        double pm11 = m00 * a;
        double pm23 = -m23 * l;
        double pm32 = -m32 * l;
        double pm33 = m22 * l;
        double vm30 = -view.m00() * view.m30() - view.m01() * view.m31() - view.m02() * view.m32();
        double vm31 = -view.m10() * view.m30() - view.m11() * view.m31() - view.m12() * view.m32();
        double vm32 = -view.m20() * view.m30() - view.m21() * view.m31() - view.m22() * view.m32();
        dest.set(view.m00() * pm00, view.m10() * pm00, view.m20() * pm00, 0.0,
                view.m01() * pm11, view.m11() * pm11, view.m21() * pm11, 0.0,
                vm30 * pm23, vm31 * pm23, vm32 * pm23, pm23,
                view.m02() * pm32 + vm30 * pm33, view.m12() * pm32 + vm31 * pm33, view.m22() * pm32 + vm32 * pm33, pm33);
        dest.property(0);
        return dest;
    }

    public Matrix4dc invertAffine(Matrix4dc dest) {
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
        dest.m03(0.0);
        dest.m10(nm10);
        dest.m11(nm11);
        dest.m12(nm12);
        dest.m13(0.0);
        dest.m20(nm20);
        dest.m21(nm21);
        dest.m22(nm22);
        dest.m23(0.0);
        dest.m30(nm30);
        dest.m31(nm31);
        dest.m32(nm32);
        dest.m33(1.0);
        dest.property(PROPERTY_AFFINE);
        return dest;
    }

    public Matrix4dc invertAffine() {
        return invertAffine(this);
    }

    public Matrix4dc invertAffineUnitScale(Matrix4dc dest) {
        dest.set(m00, m10, m20, 0.0,
                m01, m11, m21, 0.0,
                m02, m12, m22, 0.0,
                -m00 * m30 - m01 * m31 - m02 * m32,
                -m10 * m30 - m11 * m31 - m12 * m32,
                -m20 * m30 - m21 * m31 - m22 * m32,
                1.0);
        dest.property(PROPERTY_AFFINE);
        return dest;
    }

    public Matrix4dc invertAffineUnitScale() {
        return invertAffineUnitScale(this);
    }

    public Matrix4dc invertLookAt(Matrix4dc dest) {
        return invertAffineUnitScale(dest);
    }

    public Matrix4dc invertLookAt() {
        return invertAffineUnitScale(this);
    }

    public Matrix4dc transpose() {
        return transpose(this);
    }

    public Matrix4dc transpose(Matrix4dc dest) {
        double nm00 = m00;
        double nm01 = m10;
        double nm02 = m20;
        double nm03 = m30;
        double nm10 = m01;
        double nm11 = m11;
        double nm12 = m21;
        double nm13 = m31;
        double nm20 = m02;
        double nm21 = m12;
        double nm22 = m22;
        double nm23 = m32;
        double nm30 = m03;
        double nm31 = m13;
        double nm32 = m23;
        double nm33 = m33;
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
        dest.property(properties & ~(PROPERTY_PERSPECTIVE));
        return dest;
    }

    public Matrix4dc transpose3x3() {
        return transpose3x3(this);
    }

    public Matrix4dc transpose3x3(Matrix4dc dest) {
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
        dest.property(0);
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

    public Matrix4dc translation(double x, double y, double z) {
        m00 = 1.0;
        m01 = 0.0;
        m02 = 0.0;
        m03 = 0.0;
        m10 = 0.0;
        m11 = 1.0;
        m12 = 0.0;
        m13 = 0.0;
        m20 = 0.0;
        m21 = 0.0;
        m22 = 1.0;
        m23 = 0.0;
        m30 = x;
        m31 = y;
        m32 = z;
        m33 = 1.0;
        properties = PROPERTY_AFFINE | PROPERTY_TRANSLATION;
        return this;
    }

    public Matrix4dc translation(IVector3f offset) {
        return translation(offset.x(), offset.y(), offset.z());
    }

    public Matrix4dc translation(IVector3d offset) {
        return translation(offset.x(), offset.y(), offset.z());
    }

    public Matrix4dc setTranslation(double x, double y, double z) {
        m30 = x;
        m31 = y;
        m32 = z;
        properties &= ~(PROPERTY_PERSPECTIVE | PROPERTY_IDENTITY);
        return this;
    }

    public Matrix4dc setTranslation(IVector3d xyz) {
        return setTranslation(xyz.x(), xyz.y(), xyz.z());
    }

    public Vector3dc getTranslation(Vector3dc dest) {
        dest.set(m30, m31, m32);
        return dest;
    }

    public Vector3dc getScale(Vector3dc dest) {
        dest.set(Math.sqrt(m00 * m00 + m01 * m01 + m02 * m02), Math.sqrt(m10 * m10 + m11 * m11 + m12 * m12), Math.sqrt(m20 * m20 + m21 * m21 + m22 * m22));
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
                + formatter.format(m02) + " " + formatter.format(m12) + " " + formatter.format(m22) + " " + formatter.format(m32) + "\n"
                + formatter.format(m03) + " " + formatter.format(m13) + " " + formatter.format(m23) + " " + formatter.format(m33) + "\n";
    }

    public Matrix4dc get(Matrix4dc dest) {
        return dest.set(this);
    }

    public Matrix4x3dc get4x3(Matrix4x3dc dest) {
        return dest.set(this);
    }

    public Matrix3dc get3x3(Matrix3dc dest) {
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

    public DoubleBuffer get(DoubleBuffer dest) {
        return get(dest.position(), dest);
    }

    public DoubleBuffer get(int index, DoubleBuffer dest) {
        MemUtil.INSTANCE.put(this, index, dest);
        return dest;
    }

    public FloatBuffer get(FloatBuffer dest) {
        return get(dest.position(), dest);
    }

    public FloatBuffer get(int index, FloatBuffer dest) {
        MemUtil.INSTANCE.putf(this, index, dest);
        return dest;
    }

    public ByteBuffer get(ByteBuffer dest) {
        return get(dest.position(), dest);
    }

    public ByteBuffer get(int index, ByteBuffer dest) {
        MemUtil.INSTANCE.put(this, index, dest);
        return dest;
    }

    public ByteBuffer getFloats(ByteBuffer dest) {
        return getFloats(dest.position(), dest);
    }

    public ByteBuffer getFloats(int index, ByteBuffer dest) {
        MemUtil.INSTANCE.putf(this, index, dest);
        return dest;
    }
    //#endif

    public double[] get(double[] dest, int offset) {
        dest[offset + 0] = m00;
        dest[offset + 1] = m01;
        dest[offset + 2] = m02;
        dest[offset + 3] = m03;
        dest[offset + 4] = m10;
        dest[offset + 5] = m11;
        dest[offset + 6] = m12;
        dest[offset + 7] = m13;
        dest[offset + 8] = m20;
        dest[offset + 9] = m21;
        dest[offset + 10] = m22;
        dest[offset + 11] = m23;
        dest[offset + 12] = m30;
        dest[offset + 13] = m31;
        dest[offset + 14] = m32;
        dest[offset + 15] = m33;
        return dest;
    }

    public double[] get(double[] dest) {
        return get(dest, 0);
    }

    public float[] get(float[] dest, int offset) {
        dest[offset + 0] = (float) m00;
        dest[offset + 1] = (float) m01;
        dest[offset + 2] = (float) m02;
        dest[offset + 3] = (float) m03;
        dest[offset + 4] = (float) m10;
        dest[offset + 5] = (float) m11;
        dest[offset + 6] = (float) m12;
        dest[offset + 7] = (float) m13;
        dest[offset + 8] = (float) m20;
        dest[offset + 9] = (float) m21;
        dest[offset + 10] = (float) m22;
        dest[offset + 11] = (float) m23;
        dest[offset + 12] = (float) m30;
        dest[offset + 13] = (float) m31;
        dest[offset + 14] = (float) m32;
        dest[offset + 15] = (float) m33;
        return dest;
    }

    public float[] get(float[] dest) {
        return get(dest, 0);
    }

    //#ifdef __HAS_NIO__

    public DoubleBuffer getTransposed(DoubleBuffer dest) {
        return getTransposed(dest.position(), dest);
    }

    public DoubleBuffer getTransposed(int index, DoubleBuffer dest) {
        MemUtil.INSTANCE.putTransposed(this, index, dest);
        return dest;
    }

    public ByteBuffer getTransposed(ByteBuffer dest) {
        return getTransposed(dest.position(), dest);
    }

    public ByteBuffer getTransposed(int index, ByteBuffer dest) {
        MemUtil.INSTANCE.putTransposed(this, index, dest);
        return dest;
    }

    public DoubleBuffer get4x3Transposed(DoubleBuffer dest) {
        return get4x3Transposed(dest.position(), dest);
    }

    public DoubleBuffer get4x3Transposed(int index, DoubleBuffer dest) {
        MemUtil.INSTANCE.put4x3Transposed(this, index, dest);
        return dest;
    }

    public ByteBuffer get4x3Transposed(ByteBuffer dest) {
        return get4x3Transposed(dest.position(), dest);
    }

    public ByteBuffer get4x3Transposed(int index, ByteBuffer dest) {
        MemUtil.INSTANCE.put4x3Transposed(this, index, dest);
        return dest;
    }
    //#endif

    public Matrix4dc zero() {
        m00 = 0.0;
        m01 = 0.0;
        m02 = 0.0;
        m03 = 0.0;
        m10 = 0.0;
        m11 = 0.0;
        m12 = 0.0;
        m13 = 0.0;
        m20 = 0.0;
        m21 = 0.0;
        m22 = 0.0;
        m23 = 0.0;
        m30 = 0.0;
        m31 = 0.0;
        m32 = 0.0;
        m33 = 0.0;
        properties = 0;
        return this;
    }

    public Matrix4dc scaling(double factor) {
        return scaling(factor, factor, factor);
    }

    public Matrix4dc scaling(double x, double y, double z) {
        m00 = x;
        m01 = 0.0;
        m02 = 0.0;
        m03 = 0.0;
        m10 = 0.0;
        m11 = y;
        m12 = 0.0;
        m13 = 0.0;
        m20 = 0.0;
        m21 = 0.0;
        m22 = z;
        m23 = 0.0;
        m30 = 0.0;
        m31 = 0.0;
        m32 = 0.0;
        m33 = 1.0;
        properties = PROPERTY_AFFINE;
        return this;
    }

    public Matrix4dc scaling(IVector3d xyz) {
        return scaling(xyz.x(), xyz.y(), xyz.z());
    }

    public Matrix4dc rotation(double angle, double x, double y, double z) {
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
        m03 = 0.0;
        m13 = 0.0;
        m23 = 0.0;
        m33 = 1.0;
        properties = PROPERTY_AFFINE;
        return this;
    }

    public Matrix4dc rotationX(double ang) {
        double sin, cos;
        sin = Math.sin(ang);
        cos = Math.cosFromSin(sin, ang);
        m00 = 1.0;
        m01 = 0.0;
        m02 = 0.0;
        m03 = 0.0;
        m10 = 0.0;
        m11 = cos;
        m12 = sin;
        m13 = 0.0;
        m20 = 0.0;
        m21 = -sin;
        m22 = cos;
        m23 = 0.0;
        m30 = 0.0;
        m31 = 0.0;
        m32 = 0.0;
        m33 = 1.0;
        properties = PROPERTY_AFFINE;
        return this;
    }

    public Matrix4dc rotationY(double ang) {
        double sin, cos;
        sin = Math.sin(ang);
        cos = Math.cosFromSin(sin, ang);
        m00 = cos;
        m01 = 0.0;
        m02 = -sin;
        m03 = 0.0;
        m10 = 0.0;
        m11 = 1.0;
        m12 = 0.0;
        m13 = 0.0;
        m20 = sin;
        m21 = 0.0;
        m22 = cos;
        m23 = 0.0;
        m30 = 0.0;
        m31 = 0.0;
        m32 = 0.0;
        m33 = 1.0;
        properties = PROPERTY_AFFINE;
        return this;
    }

    public Matrix4dc rotationZ(double ang) {
        double sin, cos;
        sin = Math.sin(ang);
        cos = Math.cosFromSin(sin, ang);
        m00 = cos;
        m01 = sin;
        m02 = 0.0;
        m03 = 0.0;
        m10 = -sin;
        m11 = cos;
        m12 = 0.0;
        m13 = 0.0;
        m20 = 0.0;
        m21 = 0.0;
        m22 = 1.0;
        m23 = 0.0;
        m30 = 0.0;
        m31 = 0.0;
        m32 = 0.0;
        m33 = 1.0;
        properties = PROPERTY_AFFINE;
        return this;
    }

    public Matrix4dc rotationTowardsXY(double dirX, double dirY) {
        this.m00 = dirY;
        this.m01 = dirX;
        this.m02 = 0.0;
        this.m03 = 0.0;
        this.m10 = -dirX;
        this.m11 = dirY;
        this.m12 = 0.0;
        this.m13 = 0.0;
        this.m20 = 0.0;
        this.m21 = 0.0;
        this.m22 = 1.0;
        this.m23 = 0.0;
        this.m30 = 0.0;
        this.m31 = 0.0;
        this.m32 = 0.0;
        this.m33 = 1.0;
        properties = PROPERTY_AFFINE;
        return this;
    }

    public Matrix4dc rotationXYZ(double angleX, double angleY, double angleZ) {
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
        m23 = 0.0;
        // rotateZ
        m00 = nm00 * cosZ;
        m01 = nm01 * cosZ + nm11 * sinZ;
        m02 = nm02 * cosZ + nm12 * sinZ;
        m03 = 0.0;
        m10 = nm00 * m_sinZ;
        m11 = nm01 * m_sinZ + nm11 * cosZ;
        m12 = nm02 * m_sinZ + nm12 * cosZ;
        m13 = 0.0;
        // set last column to identity
        m30 = 0.0;
        m31 = 0.0;
        m32 = 0.0;
        m33 = 1.0;
        properties = PROPERTY_AFFINE;
        return this;
    }

    public Matrix4dc rotationZYX(double angleZ, double angleY, double angleX) {
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
        m03 = 0.0;
        // rotateX
        m10 = nm10 * cosX + nm20 * sinX;
        m11 = nm11 * cosX + nm21 * sinX;
        m12 = nm22 * sinX;
        m13 = 0.0;
        m20 = nm10 * m_sinX + nm20 * cosX;
        m21 = nm11 * m_sinX + nm21 * cosX;
        m22 = nm22 * cosX;
        m23 = 0.0;
        // set last column to identity
        m30 = 0.0;
        m31 = 0.0;
        m32 = 0.0;
        m33 = 1.0;
        properties = PROPERTY_AFFINE;
        return this;
    }

    public Matrix4dc rotationYXZ(double angleY, double angleX, double angleZ) {
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
        m23 = 0.0;
        // rotateZ
        m00 = nm00 * cosZ + nm10 * sinZ;
        m01 = nm11 * sinZ;
        m02 = nm02 * cosZ + nm12 * sinZ;
        m03 = 0.0;
        m10 = nm00 * m_sinZ + nm10 * cosZ;
        m11 = nm11 * cosZ;
        m12 = nm02 * m_sinZ + nm12 * cosZ;
        m13 = 0.0;
        // set last column to identity
        m30 = 0.0;
        m31 = 0.0;
        m32 = 0.0;
        m33 = 1.0;
        properties = PROPERTY_AFFINE;
        return this;
    }

    public Matrix4dc setRotationXYZ(double angleX, double angleY, double angleZ) {
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
        properties &= ~(PROPERTY_PERSPECTIVE | PROPERTY_IDENTITY | PROPERTY_TRANSLATION);
        return this;
    }

    public Matrix4dc setRotationZYX(double angleZ, double angleY, double angleX) {
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
        properties &= ~(PROPERTY_PERSPECTIVE | PROPERTY_IDENTITY | PROPERTY_TRANSLATION);
        return this;
    }

    public Matrix4dc setRotationYXZ(double angleY, double angleX, double angleZ) {
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
        properties &= ~(PROPERTY_PERSPECTIVE | PROPERTY_IDENTITY | PROPERTY_TRANSLATION);
        return this;
    }

    public Matrix4dc rotation(double angle, IVector3d axis) {
        return rotation(angle, axis.x(), axis.y(), axis.z());
    }

    public Matrix4dc rotation(double angle, IVector3f axis) {
        return rotation(angle, axis.x(), axis.y(), axis.z());
    }

    public Vector4dc transform(Vector4dc v) {
        return v.mul(this);
    }

    public Vector4dc transform(IVector4d v, Vector4dc dest) {
        return v.mul(this, dest);
    }

    public Vector4dc transform(double x, double y, double z, double w, Vector4dc dest) {
        dest.set(m00 * x + m10 * y + m20 * z + m30 * w,
                m01 * x + m11 * y + m21 * z + m31 * w,
                m02 * x + m12 * y + m22 * z + m32 * w,
                m03 * x + m13 * y + m23 * z + m33 * w);
        return dest;
    }

    public Vector4dc transformProject(Vector4dc v) {
        return v.mulProject(this);
    }

    public Vector4dc transformProject(IVector4d v, Vector4dc dest) {
        return v.mulProject(this, dest);
    }

    public Vector4dc transformProject(double x, double y, double z, double w, Vector4dc dest) {
        double invW = 1.0 / (m03 * x + m13 * y + m23 * z + m33 * w);
        dest.set((m00 * x + m10 * y + m20 * z + m30 * w) * invW,
                (m01 * x + m11 * y + m21 * z + m31 * w) * invW,
                (m02 * x + m12 * y + m22 * z + m32 * w) * invW,
                1.0);
        return dest;
    }

    public Vector3dc transformProject(Vector3dc v) {
        return v.mulProject(this);
    }

    public Vector3dc transformProject(IVector3d v, Vector3dc dest) {
        return v.mulProject(this, dest);
    }

    public Vector3dc transformProject(double x, double y, double z, Vector3dc dest) {
        double invW = 1.0 / (m03 * x + m13 * y + m23 * z + m33);
        dest.set((m00 * x + m10 * y + m20 * z + m30) * invW,
                (m01 * x + m11 * y + m21 * z + m31) * invW,
                (m02 * x + m12 * y + m22 * z + m32) * invW);
        return dest;
    }

    public Vector3dc transformPosition(Vector3dc dest) {
        dest.set(m00 * dest.x() + m10 * dest.y() + m20 * dest.z() + m30,
                m01 * dest.x() + m11 * dest.y() + m21 * dest.z() + m31,
                m02 * dest.x() + m12 * dest.y() + m22 * dest.z() + m32);
        return dest;
    }

    public Vector3dc transformPosition(IVector3d v, Vector3dc dest) {
        return transformPosition(v.x(), v.y(), v.z(), dest);
    }

    public Vector3dc transformPosition(double x, double y, double z, Vector3dc dest) {
        dest.set(m00 * x + m10 * y + m20 * z + m30,
                m01 * x + m11 * y + m21 * z + m31,
                m02 * x + m12 * y + m22 * z + m32);
        return dest;
    }

    public Vector3dc transformDirection(Vector3dc dest) {
        dest.set(m00 * dest.x() + m10 * dest.y() + m20 * dest.z(),
                m01 * dest.x() + m11 * dest.y() + m21 * dest.z(),
                m02 * dest.x() + m12 * dest.y() + m22 * dest.z());
        return dest;
    }

    public Vector3dc transformDirection(IVector3d v, Vector3dc dest) {
        dest.set(m00 * v.x() + m10 * v.y() + m20 * v.z(),
                m01 * v.x() + m11 * v.y() + m21 * v.z(),
                m02 * v.x() + m12 * v.y() + m22 * v.z());
        return dest;
    }

    public Vector3dc transformDirection(double x, double y, double z, Vector3dc dest) {
        dest.set(m00 * x + m10 * y + m20 * z,
                m01 * x + m11 * y + m21 * z,
                m02 * x + m12 * y + m22 * z);
        return dest;
    }

    public Vector3fc transformDirection(Vector3fc dest) {
        dest.set((float) (m00 * dest.x() + m10 * dest.y() + m20 * dest.z()),
                (float) (m01 * dest.x() + m11 * dest.y() + m21 * dest.z()),
                (float) (m02 * dest.x() + m12 * dest.y() + m22 * dest.z()));
        return dest;
    }

    public Vector3fc transformDirection(IVector3f v, Vector3fc dest) {
        dest.set((float) (m00 * v.x() + m10 * v.y() + m20 * v.z()),
                (float) (m01 * v.x() + m11 * v.y() + m21 * v.z()),
                (float) (m02 * v.x() + m12 * v.y() + m22 * v.z()));
        return dest;
    }

    public Vector3fc transformDirection(double x, double y, double z, Vector3fc dest) {
        dest.set((float) (m00 * x + m10 * y + m20 * z),
                (float) (m01 * x + m11 * y + m21 * z),
                (float) (m02 * x + m12 * y + m22 * z));
        return dest;
    }

    public Vector4dc transformAffine(Vector4dc dest) {
        dest.set(m00 * dest.x() + m10 * dest.y() + m20 * dest.z() + m30 * dest.w(),
                m01 * dest.x() + m11 * dest.y() + m21 * dest.z() + m31 * dest.w(),
                m02 * dest.x() + m12 * dest.y() + m22 * dest.z() + m32 * dest.w(),
                dest.w());
        return dest;
    }

    public Vector4dc transformAffine(IVector4d v, Vector4dc dest) {
        return transformAffine(v.x(), v.y(), v.z(), v.w(), dest);
    }

    public Vector4dc transformAffine(double x, double y, double z, double w, Vector4dc dest) {
        dest.set(m00 * x + m10 * y + m20 * z + m30 * w,
                m01 * x + m11 * y + m21 * z + m31 * w,
                m02 * x + m12 * y + m22 * z + m32 * w,
                w);
        return dest;
    }

    public Matrix4dc set3x3(IMatrix3d mat) {
        m00 = mat.m00();
        m01 = mat.m01();
        m02 = mat.m02();
        m10 = mat.m10();
        m11 = mat.m11();
        m12 = mat.m12();
        m20 = mat.m20();
        m21 = mat.m21();
        m22 = mat.m22();
        properties &= ~(PROPERTY_PERSPECTIVE | PROPERTY_IDENTITY | PROPERTY_TRANSLATION);
        return this;
    }

    public Matrix4dc scale(IVector3d xyz, Matrix4dc dest) {
        return scale(xyz.x(), xyz.y(), xyz.z(), dest);
    }

    public Matrix4dc scale(IVector3d xyz) {
        return scale(xyz.x(), xyz.y(), xyz.z(), this);
    }

    public Matrix4dc scale(double x, double y, double z, Matrix4dc dest) {
        if ((properties & PROPERTY_IDENTITY) != 0)
            return dest.scaling(x, y, z);
        return scaleGeneric(x, y, z, dest);
    }

    private Matrix4dc scaleGeneric(double x, double y, double z, Matrix4dc dest) {
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
        dest.property(properties & ~(PROPERTY_PERSPECTIVE | PROPERTY_IDENTITY | PROPERTY_TRANSLATION));
        return dest;
    }

    public Matrix4dc scale(double x, double y, double z) {
        return scale(x, y, z, this);
    }

    public Matrix4dc scale(double xyz, Matrix4dc dest) {
        return scale(xyz, xyz, xyz, dest);
    }

    public Matrix4dc scale(double xyz) {
        return scale(xyz, xyz, xyz);
    }

    public Matrix4dc scaleAround(double sx, double sy, double sz, double ox, double oy, double oz, Matrix4dc dest) {
        double nm30 = m00 * ox + m10 * oy + m20 * oz + m30;
        double nm31 = m01 * ox + m11 * oy + m21 * oz + m31;
        double nm32 = m02 * ox + m12 * oy + m22 * oz + m32;
        double nm33 = m03 * ox + m13 * oy + m23 * oz + m33;
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
        dest.property(properties & ~(PROPERTY_PERSPECTIVE | PROPERTY_IDENTITY | PROPERTY_TRANSLATION));
        return dest;
    }

    public Matrix4dc scaleAround(double sx, double sy, double sz, double ox, double oy, double oz) {
        return scaleAround(sx, sy, sz, ox, oy, oz, this);
    }

    public Matrix4dc scaleAround(double factor, double ox, double oy, double oz) {
        return scaleAround(factor, factor, factor, ox, oy, oz, this);
    }

    public Matrix4dc scaleAround(double factor, double ox, double oy, double oz, Matrix4dc dest) {
        return scaleAround(factor, factor, factor, ox, oy, oz, dest);
    }

    public Matrix4dc scaleLocal(double x, double y, double z, Matrix4dc dest) {
        if ((properties & PROPERTY_IDENTITY) != 0)
            return dest.scaling(x, y, z);

        double nm00 = x * m00;
        double nm01 = y * m01;
        double nm02 = z * m02;
        double nm03 = m03;
        double nm10 = x * m10;
        double nm11 = y * m11;
        double nm12 = z * m12;
        double nm13 = m13;
        double nm20 = x * m20;
        double nm21 = y * m21;
        double nm22 = z * m22;
        double nm23 = m23;
        double nm30 = x * m30;
        double nm31 = y * m31;
        double nm32 = z * m32;
        double nm33 = m33;
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
        dest.property(properties & ~(PROPERTY_PERSPECTIVE | PROPERTY_IDENTITY | PROPERTY_TRANSLATION));
        return dest;
    }

    public Matrix4dc scaleLocal(double xyz, Matrix4dc dest) {
        return scaleLocal(xyz, xyz, xyz, dest);
    }

    public Matrix4dc scaleLocal(double xyz) {
        return scaleLocal(xyz, this);
    }

    public Matrix4dc scaleLocal(double x, double y, double z) {
        return scaleLocal(x, y, z, this);
    }

    public Matrix4dc scaleAroundLocal(double sx, double sy, double sz, double ox, double oy, double oz, Matrix4dc dest) {
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
        dest.property(properties & ~(PROPERTY_PERSPECTIVE | PROPERTY_IDENTITY | PROPERTY_TRANSLATION));
        return dest;
    }

    public Matrix4dc scaleAroundLocal(double sx, double sy, double sz, double ox, double oy, double oz) {
        return scaleAroundLocal(sx, sy, sz, ox, oy, oz, this);
    }

    public Matrix4dc scaleAroundLocal(double factor, double ox, double oy, double oz) {
        return scaleAroundLocal(factor, factor, factor, ox, oy, oz, this);
    }

    public Matrix4dc scaleAroundLocal(double factor, double ox, double oy, double oz, Matrix4dc dest) {
        return scaleAroundLocal(factor, factor, factor, ox, oy, oz, dest);
    }

    public Matrix4dc rotate(double ang, double x, double y, double z, Matrix4dc dest) {
        if ((properties & PROPERTY_IDENTITY) != 0)
            return dest.rotation(ang, x, y, z);
        else if ((properties & PROPERTY_TRANSLATION) != 0)
            return rotateTranslation(ang, x, y, z, dest);
        else if ((properties & PROPERTY_AFFINE) != 0)
            return rotateAffine(ang, x, y, z, dest);
        return rotateGeneric(ang, x, y, z, dest);
    }

    private Matrix4dc rotateGeneric(double ang, double x, double y, double z, Matrix4dc dest) {
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
        double nm00 = m00 * rm00 + m10 * rm01 + m20 * rm02;
        double nm01 = m01 * rm00 + m11 * rm01 + m21 * rm02;
        double nm02 = m02 * rm00 + m12 * rm01 + m22 * rm02;
        double nm03 = m03 * rm00 + m13 * rm01 + m23 * rm02;
        double nm10 = m00 * rm10 + m10 * rm11 + m20 * rm12;
        double nm11 = m01 * rm10 + m11 * rm11 + m21 * rm12;
        double nm12 = m02 * rm10 + m12 * rm11 + m22 * rm12;
        double nm13 = m03 * rm10 + m13 * rm11 + m23 * rm12;
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
        dest.property(properties & ~(PROPERTY_PERSPECTIVE | PROPERTY_IDENTITY | PROPERTY_TRANSLATION));
        return dest;
    }

    public Matrix4dc rotate(double ang, double x, double y, double z) {
        return rotate(ang, x, y, z, this);
    }

    public Matrix4dc rotateTranslation(double ang, double x, double y, double z, Matrix4dc dest) {
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
        dest.m03(0.0);
        dest.m10(nm10);
        dest.m11(nm11);
        dest.m12(nm12);
        dest.m13(0.0);
        dest.m30(m30);
        dest.m31(m31);
        dest.m32(m32);
        dest.m33(m33);
        dest.property(properties & ~(PROPERTY_PERSPECTIVE | PROPERTY_IDENTITY | PROPERTY_TRANSLATION));

        return dest;
    }

    public Matrix4dc rotateAffine(double ang, double x, double y, double z, Matrix4dc dest) {
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
        dest.m23(0.0);
        // set other values
        dest.m00(nm00);
        dest.m01(nm01);
        dest.m02(nm02);
        dest.m03(0.0);
        dest.m10(nm10);
        dest.m11(nm11);
        dest.m12(nm12);
        dest.m13(0.0);
        dest.m30(m30);
        dest.m31(m31);
        dest.m32(m32);
        dest.m33(m33);
        dest.property(properties & ~(PROPERTY_PERSPECTIVE | PROPERTY_IDENTITY | PROPERTY_TRANSLATION));
        return dest;
    }

    public Matrix4dc rotateAffine(double ang, double x, double y, double z) {
        return rotateAffine(ang, x, y, z, this);
    }

    public Matrix4dc rotateAround(IQuaterniond quat, double ox, double oy, double oz) {
        return rotateAround(quat, ox, oy, oz, this);
    }

    public Matrix4dc rotateAround(IQuaterniond quat, double ox, double oy, double oz, Matrix4dc dest) {
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
        double tm30 = m00 * ox + m10 * oy + m20 * oz + m30;
        double tm31 = m01 * ox + m11 * oy + m21 * oz + m31;
        double tm32 = m02 * ox + m12 * oy + m22 * oz + m32;
        double nm00 = m00 * rm00 + m10 * rm01 + m20 * rm02;
        double nm01 = m01 * rm00 + m11 * rm01 + m21 * rm02;
        double nm02 = m02 * rm00 + m12 * rm01 + m22 * rm02;
        double nm03 = m03 * rm00 + m13 * rm01 + m23 * rm02;
        double nm10 = m00 * rm10 + m10 * rm11 + m20 * rm12;
        double nm11 = m01 * rm10 + m11 * rm11 + m21 * rm12;
        double nm12 = m02 * rm10 + m12 * rm11 + m22 * rm12;
        double nm13 = m03 * rm10 + m13 * rm11 + m23 * rm12;
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
        dest.property(properties & ~(PROPERTY_PERSPECTIVE | PROPERTY_IDENTITY | PROPERTY_TRANSLATION));
        return dest;
    }

    public Matrix4dc rotateLocal(double ang, double x, double y, double z, Matrix4dc dest) {
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
        double nm03 = m03;
        double nm10 = lm00 * m10 + lm10 * m11 + lm20 * m12;
        double nm11 = lm01 * m10 + lm11 * m11 + lm21 * m12;
        double nm12 = lm02 * m10 + lm12 * m11 + lm22 * m12;
        double nm13 = m13;
        double nm20 = lm00 * m20 + lm10 * m21 + lm20 * m22;
        double nm21 = lm01 * m20 + lm11 * m21 + lm21 * m22;
        double nm22 = lm02 * m20 + lm12 * m21 + lm22 * m22;
        double nm23 = m23;
        double nm30 = lm00 * m30 + lm10 * m31 + lm20 * m32;
        double nm31 = lm01 * m30 + lm11 * m31 + lm21 * m32;
        double nm32 = lm02 * m30 + lm12 * m31 + lm22 * m32;
        double nm33 = m33;
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
        dest.property(properties & ~(PROPERTY_PERSPECTIVE | PROPERTY_IDENTITY | PROPERTY_TRANSLATION));
        return dest;
    }

    public Matrix4dc rotateLocal(double ang, double x, double y, double z) {
        return rotateLocal(ang, x, y, z, this);
    }

    public Matrix4dc rotateAroundLocal(IQuaterniond quat, double ox, double oy, double oz, Matrix4dc dest) {
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
        double tm00 = m00 - ox * m03;
        double tm01 = m01 - oy * m03;
        double tm02 = m02 - oz * m03;
        double tm10 = m10 - ox * m13;
        double tm11 = m11 - oy * m13;
        double tm12 = m12 - oz * m13;
        double tm20 = m20 - ox * m23;
        double tm21 = m21 - oy * m23;
        double tm22 = m22 - oz * m23;
        double tm30 = m30 - ox * m33;
        double tm31 = m31 - oy * m33;
        double tm32 = m32 - oz * m33;
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
        dest.property(properties & ~(PROPERTY_PERSPECTIVE | PROPERTY_IDENTITY | PROPERTY_TRANSLATION));
        return dest;
    }

    public Matrix4dc rotateAroundLocal(IQuaterniond quat, double ox, double oy, double oz) {
        return rotateAroundLocal(quat, ox, oy, oz, this);
    }

    public Matrix4dc translate(IVector3d offset) {
        return translate(offset.x(), offset.y(), offset.z());
    }

    public Matrix4dc translate(IVector3d offset, Matrix4dc dest) {
        return translate(offset.x(), offset.y(), offset.z(), dest);
    }

    public Matrix4dc translate(IVector3f offset) {
        return translate(offset.x(), offset.y(), offset.z());
    }

    public Matrix4dc translate(IVector3f offset, Matrix4dc dest) {
        return translate(offset.x(), offset.y(), offset.z(), dest);
    }

    public Matrix4dc translate(double x, double y, double z, Matrix4dc dest) {
        if ((properties & PROPERTY_IDENTITY) != 0)
            return dest.translation(x, y, z);
        return translateGeneric(x, y, z, dest);
    }

    private Matrix4dc translateGeneric(double x, double y, double z, Matrix4dc dest) {
        dest.m00(m00);
        dest.m01(m01);
        dest.m02(m02);
        dest.m03(m03);
        dest.m10(m10);
        dest.m11(m11);
        dest.m12(m12);
        dest.m13(m13);
        dest.m20(m20);
        dest.m21(m21);
        dest.m22(m22);
        dest.m23(m23);
        dest.m30(m00 * x + m10 * y + m20 * z + m30);
        dest.m31(m01 * x + m11 * y + m21 * z + m31);
        dest.m32(m02 * x + m12 * y + m22 * z + m32);
        dest.m33(m03 * x + m13 * y + m23 * z + m33);
        dest.property(properties & ~(PROPERTY_PERSPECTIVE | PROPERTY_IDENTITY));
        return dest;
    }

    public Matrix4dc translate(double x, double y, double z) {
        if ((properties & PROPERTY_IDENTITY) != 0)
            return translation(x, y, z);
        Matrix4dc c = this;
        c.m30(c.m00() * x + c.m10() * y + c.m20() * z + c.m30());
        c.m31(c.m01() * x + c.m11() * y + c.m21() * z + c.m31());
        c.m32(c.m02() * x + c.m12() * y + c.m22() * z + c.m32());
        c.m33(c.m03() * x + c.m13() * y + c.m23() * z + c.m33());
        c.property(c.properties() & ~(PROPERTY_PERSPECTIVE | PROPERTY_IDENTITY));
        return this;
    }

    public Matrix4dc translateLocal(IVector3f offset) {
        return translateLocal(offset.x(), offset.y(), offset.z());
    }

    public Matrix4dc translateLocal(IVector3f offset, Matrix4dc dest) {
        return translateLocal(offset.x(), offset.y(), offset.z(), dest);
    }

    public Matrix4dc translateLocal(IVector3d offset) {
        return translateLocal(offset.x(), offset.y(), offset.z());
    }

    public Matrix4dc translateLocal(IVector3d offset, Matrix4dc dest) {
        return translateLocal(offset.x(), offset.y(), offset.z(), dest);
    }

    public Matrix4dc translateLocal(double x, double y, double z, Matrix4dc dest) {
        double nm00 = m00 + x * m03;
        double nm01 = m01 + y * m03;
        double nm02 = m02 + z * m03;
        double nm03 = m03;
        double nm10 = m10 + x * m13;
        double nm11 = m11 + y * m13;
        double nm12 = m12 + z * m13;
        double nm13 = m13;
        double nm20 = m20 + x * m23;
        double nm21 = m21 + y * m23;
        double nm22 = m22 + z * m23;
        double nm23 = m23;
        double nm30 = m30 + x * m33;
        double nm31 = m31 + y * m33;
        double nm32 = m32 + z * m33;
        double nm33 = m33;
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
        dest.property(properties & ~(PROPERTY_PERSPECTIVE | PROPERTY_IDENTITY));
        return dest;
    }

    public Matrix4dc translateLocal(double x, double y, double z) {
        return translateLocal(x, y, z, this);
    }

    public Matrix4dc rotateLocalX(double ang, Matrix4dc dest) {
        double sin = Math.sin(ang);
        double cos = Math.cosFromSin(sin, ang);
        double nm01 = cos * m01 - sin * m02;
        double nm02 = sin * m01 + cos * m02;
        double nm11 = cos * m11 - sin * m12;
        double nm12 = sin * m11 + cos * m12;
        double nm21 = cos * m21 - sin * m22;
        double nm22 = sin * m21 + cos * m22;
        double nm31 = cos * m31 - sin * m32;
        double nm32 = sin * m31 + cos * m32;
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
        dest.property(properties & ~(PROPERTY_PERSPECTIVE | PROPERTY_IDENTITY | PROPERTY_TRANSLATION));
        return dest;
    }

    public Matrix4dc rotateLocalX(double ang) {
        return rotateLocalX(ang, this);
    }

    public Matrix4dc rotateLocalY(double ang, Matrix4dc dest) {
        double sin = Math.sin(ang);
        double cos = Math.cosFromSin(sin, ang);
        double nm00 = cos * m00 + sin * m02;
        double nm02 = -sin * m00 + cos * m02;
        double nm10 = cos * m10 + sin * m12;
        double nm12 = -sin * m10 + cos * m12;
        double nm20 = cos * m20 + sin * m22;
        double nm22 = -sin * m20 + cos * m22;
        double nm30 = cos * m30 + sin * m32;
        double nm32 = -sin * m30 + cos * m32;
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
        dest.property(properties & ~(PROPERTY_PERSPECTIVE | PROPERTY_IDENTITY | PROPERTY_TRANSLATION));
        return dest;
    }

    public Matrix4dc rotateLocalY(double ang) {
        return rotateLocalY(ang, this);
    }

    public Matrix4dc rotateLocalZ(double ang, Matrix4dc dest) {
        double sin = Math.sin(ang);
        double cos = Math.cosFromSin(sin, ang);
        double nm00 = cos * m00 - sin * m01;
        double nm01 = sin * m00 + cos * m01;
        double nm10 = cos * m10 - sin * m11;
        double nm11 = sin * m10 + cos * m11;
        double nm20 = cos * m20 - sin * m21;
        double nm21 = sin * m20 + cos * m21;
        double nm30 = cos * m30 - sin * m31;
        double nm31 = sin * m30 + cos * m31;
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
        dest.property(properties & ~(PROPERTY_PERSPECTIVE | PROPERTY_IDENTITY | PROPERTY_TRANSLATION));
        return dest;
    }

    public Matrix4dc rotateLocalZ(double ang) {
        return rotateLocalZ(ang, this);
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeDouble(m00);
        out.writeDouble(m01);
        out.writeDouble(m02);
        out.writeDouble(m03);
        out.writeDouble(m10);
        out.writeDouble(m11);
        out.writeDouble(m12);
        out.writeDouble(m13);
        out.writeDouble(m20);
        out.writeDouble(m21);
        out.writeDouble(m22);
        out.writeDouble(m23);
        out.writeDouble(m30);
        out.writeDouble(m31);
        out.writeDouble(m32);
        out.writeDouble(m33);
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        m00 = in.readDouble();
        m01 = in.readDouble();
        m02 = in.readDouble();
        m03 = in.readDouble();
        m10 = in.readDouble();
        m11 = in.readDouble();
        m12 = in.readDouble();
        m13 = in.readDouble();
        m20 = in.readDouble();
        m21 = in.readDouble();
        m22 = in.readDouble();
        m23 = in.readDouble();
        m30 = in.readDouble();
        m31 = in.readDouble();
        m32 = in.readDouble();
        m33 = in.readDouble();
    }

    public Matrix4dc rotateX(double ang, Matrix4dc dest) {
        if ((properties & PROPERTY_IDENTITY) != 0)
            return dest.rotationX(ang);
        double sin, cos;
        sin = Math.sin(ang);
        cos = Math.cosFromSin(sin, ang);
        double rm11 = cos;
        double rm12 = sin;
        double rm21 = -sin;
        double rm22 = cos;

        // add temporaries for dependent values
        double nm10 = m10 * rm11 + m20 * rm12;
        double nm11 = m11 * rm11 + m21 * rm12;
        double nm12 = m12 * rm11 + m22 * rm12;
        double nm13 = m13 * rm11 + m23 * rm12;
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
        dest.property(properties & ~(PROPERTY_PERSPECTIVE | PROPERTY_IDENTITY | PROPERTY_TRANSLATION));
        return dest;
    }

    public Matrix4dc rotateX(double ang) {
        return rotateX(ang, this);
    }

    public Matrix4dc rotateY(double ang, Matrix4dc dest) {
        if ((properties & PROPERTY_IDENTITY) != 0)
            return dest.rotationY(ang);
        double sin, cos;
        sin = Math.sin(ang);
        cos = Math.cosFromSin(sin, ang);
        double rm00 = cos;
        double rm02 = -sin;
        double rm20 = sin;
        double rm22 = cos;

        // add temporaries for dependent values
        double nm00 = m00 * rm00 + m20 * rm02;
        double nm01 = m01 * rm00 + m21 * rm02;
        double nm02 = m02 * rm00 + m22 * rm02;
        double nm03 = m03 * rm00 + m23 * rm02;
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
        dest.property(properties & ~(PROPERTY_PERSPECTIVE | PROPERTY_IDENTITY | PROPERTY_TRANSLATION));
        return dest;
    }

    public Matrix4dc rotateY(double ang) {
        return rotateY(ang, this);
    }

    public Matrix4dc rotateZ(double ang, Matrix4dc dest) {
        if ((properties & PROPERTY_IDENTITY) != 0)
            return dest.rotationZ(ang);
        double sin = Math.sin(ang);
        double cos = Math.cosFromSin(sin, ang);
        return rotateTowardsXY(sin, cos, dest);
    }

    public Matrix4dc rotateZ(double ang) {
        return rotateZ(ang, this);
    }

    public Matrix4dc rotateTowardsXY(double dirX, double dirY) {
        return rotateTowardsXY(dirX, dirY, this);
    }

    public Matrix4dc rotateTowardsXY(double dirX, double dirY, Matrix4dc dest) {
        if ((properties & PROPERTY_IDENTITY) != 0)
            return dest.rotationTowardsXY(dirX, dirY);
        double rm00 = dirY;
        double rm01 = dirX;
        double rm10 = -dirX;
        double rm11 = dirY;
        double nm00 = m00 * rm00 + m10 * rm01;
        double nm01 = m01 * rm00 + m11 * rm01;
        double nm02 = m02 * rm00 + m12 * rm01;
        double nm03 = m03 * rm00 + m13 * rm01;
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
        dest.property(properties & ~(PROPERTY_PERSPECTIVE | PROPERTY_IDENTITY | PROPERTY_TRANSLATION));
        return dest;
    }

    public Matrix4dc rotateXYZ(Vector3dc angles) {
        return rotateXYZ(angles.x(), angles.y(), angles.z());
    }

    public Matrix4dc rotateXYZ(double angleX, double angleY, double angleZ) {
        return rotateXYZ(angleX, angleY, angleZ, this);
    }

    public Matrix4dc rotateXYZ(double angleX, double angleY, double angleZ, Matrix4dc dest) {
        if ((properties & PROPERTY_IDENTITY) != 0)
            return dest.rotationXYZ(angleX, angleY, angleZ);
        else if ((properties & PROPERTY_AFFINE) != 0)
            return dest.rotateAffineXYZ(angleX, angleY, angleZ);

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
        double nm13 = m13 * cosX + m23 * sinX;
        double nm20 = m10 * m_sinX + m20 * cosX;
        double nm21 = m11 * m_sinX + m21 * cosX;
        double nm22 = m12 * m_sinX + m22 * cosX;
        double nm23 = m13 * m_sinX + m23 * cosX;
        // rotateY
        double nm00 = m00 * cosY + nm20 * m_sinY;
        double nm01 = m01 * cosY + nm21 * m_sinY;
        double nm02 = m02 * cosY + nm22 * m_sinY;
        double nm03 = m03 * cosY + nm23 * m_sinY;
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
        dest.property(properties & ~(PROPERTY_PERSPECTIVE | PROPERTY_IDENTITY | PROPERTY_TRANSLATION));
        return dest;
    }

    public Matrix4dc rotateAffineXYZ(double angleX, double angleY, double angleZ) {
        return rotateAffineXYZ(angleX, angleY, angleZ, this);
    }

    public Matrix4dc rotateAffineXYZ(double angleX, double angleY, double angleZ, Matrix4dc dest) {
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
        dest.m23(0.0);
        // rotateZ
        dest.m00(nm00 * cosZ + nm10 * sinZ);
        dest.m01(nm01 * cosZ + nm11 * sinZ);
        dest.m02(nm02 * cosZ + nm12 * sinZ);
        dest.m03(0.0);
        dest.m10(nm00 * m_sinZ + nm10 * cosZ);
        dest.m11(nm01 * m_sinZ + nm11 * cosZ);
        dest.m12(nm02 * m_sinZ + nm12 * cosZ);
        dest.m13(0.0);
        // copy last column from 'this'
        dest.m30(m30);
        dest.m31(m31);
        dest.m32(m32);
        dest.m33(m33);
        dest.property(properties & ~(PROPERTY_PERSPECTIVE | PROPERTY_IDENTITY | PROPERTY_TRANSLATION));
        return dest;
    }

    public Matrix4dc rotateZYX(Vector3dc angles) {
        return rotateZYX(angles.z(), angles.y(), angles.x());
    }

    public Matrix4dc rotateZYX(double angleZ, double angleY, double angleX) {
        return rotateZYX(angleZ, angleY, angleX, this);
    }

    public Matrix4dc rotateZYX(double angleZ, double angleY, double angleX, Matrix4dc dest) {
        if ((properties & PROPERTY_IDENTITY) != 0)
            return dest.rotationZYX(angleZ, angleY, angleX);
        else if ((properties & PROPERTY_AFFINE) != 0)
            return dest.rotateAffineZYX(angleZ, angleY, angleX);

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
        double nm03 = m03 * cosZ + m13 * sinZ;
        double nm10 = m00 * m_sinZ + m10 * cosZ;
        double nm11 = m01 * m_sinZ + m11 * cosZ;
        double nm12 = m02 * m_sinZ + m12 * cosZ;
        double nm13 = m03 * m_sinZ + m13 * cosZ;
        // rotateY
        double nm20 = nm00 * sinY + m20 * cosY;
        double nm21 = nm01 * sinY + m21 * cosY;
        double nm22 = nm02 * sinY + m22 * cosY;
        double nm23 = nm03 * sinY + m23 * cosY;
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
        dest.property(properties & ~(PROPERTY_PERSPECTIVE | PROPERTY_IDENTITY | PROPERTY_TRANSLATION));
        return dest;
    }

    public Matrix4dc rotateAffineZYX(double angleZ, double angleY, double angleX) {
        return rotateAffineZYX(angleZ, angleY, angleX, this);
    }

    public Matrix4dc rotateAffineZYX(double angleZ, double angleY, double angleX, Matrix4dc dest) {
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
        dest.m03(0.0);
        // rotateX
        dest.m10(nm10 * cosX + nm20 * sinX);
        dest.m11(nm11 * cosX + nm21 * sinX);
        dest.m12(nm12 * cosX + nm22 * sinX);
        dest.m13(0.0);
        dest.m20(nm10 * m_sinX + nm20 * cosX);
        dest.m21(nm11 * m_sinX + nm21 * cosX);
        dest.m22(nm12 * m_sinX + nm22 * cosX);
        dest.m23(0.0);
        // copy last column from 'this'
        dest.m30(m30);
        dest.m31(m31);
        dest.m32(m32);
        dest.m33(m33);
        dest.property(properties & ~(PROPERTY_PERSPECTIVE | PROPERTY_IDENTITY | PROPERTY_TRANSLATION));
        return dest;
    }

    public Matrix4dc rotateYXZ(Vector3dc angles) {
        return rotateYXZ(angles.y(), angles.x(), angles.z());
    }

    public Matrix4dc rotateYXZ(double angleY, double angleX, double angleZ) {
        return rotateYXZ(angleY, angleX, angleZ, this);
    }

    public Matrix4dc rotateYXZ(double angleY, double angleX, double angleZ, Matrix4dc dest) {
        if ((properties & PROPERTY_IDENTITY) != 0)
            return dest.rotationYXZ(angleY, angleX, angleZ);
        else if ((properties & PROPERTY_AFFINE) != 0)
            return dest.rotateAffineYXZ(angleY, angleX, angleZ);

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
        double nm23 = m03 * sinY + m23 * cosY;
        double nm00 = m00 * cosY + m20 * m_sinY;
        double nm01 = m01 * cosY + m21 * m_sinY;
        double nm02 = m02 * cosY + m22 * m_sinY;
        double nm03 = m03 * cosY + m23 * m_sinY;
        // rotateX
        double nm10 = m10 * cosX + nm20 * sinX;
        double nm11 = m11 * cosX + nm21 * sinX;
        double nm12 = m12 * cosX + nm22 * sinX;
        double nm13 = m13 * cosX + nm23 * sinX;
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
        dest.property(properties & ~(PROPERTY_PERSPECTIVE | PROPERTY_IDENTITY | PROPERTY_TRANSLATION));
        return dest;
    }

    public Matrix4dc rotateAffineYXZ(double angleY, double angleX, double angleZ) {
        return rotateAffineYXZ(angleY, angleX, angleZ, this);
    }

    public Matrix4dc rotateAffineYXZ(double angleY, double angleX, double angleZ, Matrix4dc dest) {
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
        dest.m23(0.0);
        // rotateZ
        dest.m00(nm00 * cosZ + nm10 * sinZ);
        dest.m01(nm01 * cosZ + nm11 * sinZ);
        dest.m02(nm02 * cosZ + nm12 * sinZ);
        dest.m03(0.0);
        dest.m10(nm00 * m_sinZ + nm10 * cosZ);
        dest.m11(nm01 * m_sinZ + nm11 * cosZ);
        dest.m12(nm02 * m_sinZ + nm12 * cosZ);
        dest.m13(0.0);
        // copy last column from 'this'
        dest.m30(m30);
        dest.m31(m31);
        dest.m32(m32);
        dest.m33(m33);
        dest.property(properties & ~(PROPERTY_PERSPECTIVE | PROPERTY_IDENTITY | PROPERTY_TRANSLATION));
        return dest;
    }

    public Matrix4dc rotation(AxisAngle4fc angleAxis) {
        return rotation(angleAxis.angle(), angleAxis.x(), angleAxis.y(), angleAxis.z());
    }

    public Matrix4dc rotation(AxisAngle4dc angleAxis) {
        return rotation(angleAxis.angle(), angleAxis.x(), angleAxis.y(), angleAxis.z());
    }

    public Matrix4dc rotation(IQuaterniond quat) {
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
        m03 = 0.0;
        m10 = -zw + xy - zw + xy;
        m11 = y2 - z2 + w2 - x2;
        m12 = yz + yz + xw + xw;
        m13 = 0.0;
        m20 = yw + xz + xz + yw;
        m21 = yz + yz - xw - xw;
        m22 = z2 - y2 - x2 + w2;
        m23 = 0.0;
        m30 = 0.0;
        m31 = 0.0;
        m32 = 0.0;
        m33 = 1.0;
        properties = PROPERTY_AFFINE;
        return this;
    }

    public Matrix4dc rotation(IQuaternionf quat) {
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
        m03 = 0.0;
        m10 = -zw + xy - zw + xy;
        m11 = y2 - z2 + w2 - x2;
        m12 = yz + yz + xw + xw;
        m13 = 0.0;
        m20 = yw + xz + xz + yw;
        m21 = yz + yz - xw - xw;
        m22 = z2 - y2 - x2 + w2;
        m23 = 0.0;
        m30 = 0.0;
        m31 = 0.0;
        m32 = 0.0;
        m33 = 1.0;
        properties = PROPERTY_AFFINE;
        return this;
    }

    public Matrix4dc translationRotateScale(double tx, double ty, double tz,
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
        m03 = 0.0;
        m10 = (q01 - q23) * sy;
        m11 = sy - (q22 + q00) * sy;
        m12 = (q12 + q03) * sy;
        m13 = 0.0;
        m20 = (q02 + q13) * sz;
        m21 = (q12 - q03) * sz;
        m22 = sz - (q11 + q00) * sz;
        m23 = 0.0;
        m30 = tx;
        m31 = ty;
        m32 = tz;
        m33 = 1.0;
        properties = PROPERTY_AFFINE;
        return this;
    }

    public Matrix4dc translationRotateScale(IVector3f translation,
                                            IQuaternionf quat,
                                            IVector3f scale) {
        return translationRotateScale(translation.x(), translation.y(), translation.z(), quat.x(), quat.y(), quat.z(), quat.w(), scale.x(), scale.y(), scale.z());
    }

    public Matrix4dc translationRotateScale(IVector3d translation,
                                            IQuaterniond quat,
                                            IVector3d scale) {
        return translationRotateScale(translation.x(), translation.y(), translation.z(), quat.x(), quat.y(), quat.z(), quat.w(), scale.x(), scale.y(), scale.z());
    }

    public Matrix4dc translationRotateScale(double tx, double ty, double tz,
                                            double qx, double qy, double qz, double qw,
                                            double scale) {
        return translationRotateScale(tx, ty, tz, qx, qy, qz, qw, scale, scale, scale);
    }

    public Matrix4dc translationRotateScale(IVector3d translation,
                                            IQuaterniond quat,
                                            double scale) {
        return translationRotateScale(translation.x(), translation.y(), translation.z(), quat.x(), quat.y(), quat.z(), quat.w(), scale, scale, scale);
    }

    public Matrix4dc translationRotateScale(IVector3f translation,
                                            IQuaternionf quat,
                                            double scale) {
        return translationRotateScale(translation.x(), translation.y(), translation.z(), quat.x(), quat.y(), quat.z(), quat.w(), scale, scale, scale);
    }

    public Matrix4dc translationRotateScaleInvert(double tx, double ty, double tz,
                                                  double qx, double qy, double qz, double qw,
                                                  double sx, double sy, double sz) {
        double nqx = -qx, nqy = -qy, nqz = -qz;
        double dqx = nqx + nqx;
        double dqy = nqy + nqy;
        double dqz = nqz + nqz;
        double q00 = dqx * nqx;
        double q11 = dqy * nqy;
        double q22 = dqz * nqz;
        double q01 = dqx * nqy;
        double q02 = dqx * nqz;
        double q03 = dqx * qw;
        double q12 = dqy * nqz;
        double q13 = dqy * qw;
        double q23 = dqz * qw;
        double isx = 1 / sx, isy = 1 / sy, isz = 1 / sz;
        m00 = isx * (1.0 - q11 - q22);
        m01 = isy * (q01 + q23);
        m02 = isz * (q02 - q13);
        m03 = 0.0;
        m10 = isx * (q01 - q23);
        m11 = isy * (1.0 - q22 - q00);
        m12 = isz * (q12 + q03);
        m13 = 0.0;
        m20 = isx * (q02 + q13);
        m21 = isy * (q12 - q03);
        m22 = isz * (1.0 - q11 - q00);
        m23 = 0.0;
        m30 = -m00 * tx - m10 * ty - m20 * tz;
        m31 = -m01 * tx - m11 * ty - m21 * tz;
        m32 = -m02 * tx - m12 * ty - m22 * tz;
        m33 = 1.0;
        properties = PROPERTY_AFFINE;
        return this;
    }

    public Matrix4dc translationRotateScaleInvert(IVector3d translation,
                                                  IQuaterniond quat,
                                                  IVector3d scale) {
        return translationRotateScaleInvert(translation.x(), translation.y(), translation.z(), quat.x(), quat.y(), quat.z(), quat.w(), scale.x(), scale.y(), scale.z());
    }

    public Matrix4dc translationRotateScaleInvert(IVector3f translation,
                                                  IQuaternionf quat,
                                                  IVector3f scale) {
        return translationRotateScaleInvert(translation.x(), translation.y(), translation.z(), quat.x(), quat.y(), quat.z(), quat.w(), scale.x(), scale.y(), scale.z());
    }

    public Matrix4dc translationRotateScaleInvert(IVector3d translation,
                                                  IQuaterniond quat,
                                                  double scale) {
        return translationRotateScaleInvert(translation.x(), translation.y(), translation.z(), quat.x(), quat.y(), quat.z(), quat.w(), scale, scale, scale);
    }

    public Matrix4dc translationRotateScaleInvert(IVector3f translation,
                                                  IQuaternionf quat,
                                                  double scale) {
        return translationRotateScaleInvert(translation.x(), translation.y(), translation.z(), quat.x(), quat.y(), quat.z(), quat.w(), scale, scale, scale);
    }

    public Matrix4dc translationRotateScaleMulAffine(double tx, double ty, double tz,
                                                     double qx, double qy, double qz, double qw,
                                                     double sx, double sy, double sz,
                                                     Matrix4dc m) {
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
        double m00 = nm00 * m.m00() + nm10 * m.m01() + nm20 * m.m02();
        double m01 = nm01 * m.m00() + nm11 * m.m01() + nm21 * m.m02();
        this.m02 = nm02 * m.m00() + nm12 * m.m01() + nm22 * m.m02();
        this.m00 = m00;
        this.m01 = m01;
        this.m03 = 0.0;
        double m10 = nm00 * m.m10() + nm10 * m.m11() + nm20 * m.m12();
        double m11 = nm01 * m.m10() + nm11 * m.m11() + nm21 * m.m12();
        this.m12 = nm02 * m.m10() + nm12 * m.m11() + nm22 * m.m12();
        this.m10 = m10;
        this.m11 = m11;
        this.m13 = 0.0;
        double m20 = nm00 * m.m20() + nm10 * m.m21() + nm20 * m.m22();
        double m21 = nm01 * m.m20() + nm11 * m.m21() + nm21 * m.m22();
        this.m22 = nm02 * m.m20() + nm12 * m.m21() + nm22 * m.m22();
        this.m20 = m20;
        this.m21 = m21;
        this.m23 = 0.0;
        double m30 = nm00 * m.m30() + nm10 * m.m31() + nm20 * m.m32() + tx;
        double m31 = nm01 * m.m30() + nm11 * m.m31() + nm21 * m.m32() + ty;
        this.m32 = nm02 * m.m30() + nm12 * m.m31() + nm22 * m.m32() + tz;
        this.m30 = m30;
        this.m31 = m31;
        this.m33 = 1.0;
        this.properties = PROPERTY_AFFINE;
        return this;
    }

    public Matrix4dc translationRotateScaleMulAffine(IVector3f translation,
                                                     IQuaterniond quat,
                                                     IVector3f scale,
                                                     Matrix4dc m) {
        return translationRotateScaleMulAffine(translation.x(), translation.y(), translation.z(), quat.x(), quat.y(), quat.z(), quat.w(), scale.x(), scale.y(), scale.z(), m);
    }

    public Matrix4dc translationRotate(double tx, double ty, double tz, double qx, double qy, double qz, double qw) {
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
        this.m00 = w2 + x2 - z2 - y2;
        this.m01 = xy + zw + zw + xy;
        this.m02 = xz - yw + xz - yw;
        this.m10 = -zw + xy - zw + xy;
        this.m11 = y2 - z2 + w2 - x2;
        this.m12 = yz + yz + xw + xw;
        this.m20 = yw + xz + xz + yw;
        this.m21 = yz + yz - xw - xw;
        this.m22 = z2 - y2 - x2 + w2;
        this.m30 = tx;
        this.m31 = ty;
        this.m32 = tz;
        this.m33 = 1.0;
        this.properties = PROPERTY_AFFINE;
        return this;
    }

    public Matrix4dc translationRotate(double tx, double ty, double tz, IQuaterniond quat) {
        return translationRotate(tx, ty, tz, quat.x(), quat.y(), quat.z(), quat.w());
    }

    public Matrix4dc rotate(IQuaterniond quat, Matrix4dc dest) {
        if ((properties & PROPERTY_IDENTITY) != 0)
            return dest.rotation(quat);
        else if ((properties & PROPERTY_TRANSLATION) != 0)
            return rotateTranslation(quat, dest);
        else if ((properties & PROPERTY_AFFINE) != 0)
            return rotateAffine(quat, dest);
        return rotateGeneric(quat, dest);
    }

    private Matrix4dc rotateGeneric(IQuaterniond quat, Matrix4dc dest) {
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
        double nm03 = m03 * rm00 + m13 * rm01 + m23 * rm02;
        double nm10 = m00 * rm10 + m10 * rm11 + m20 * rm12;
        double nm11 = m01 * rm10 + m11 * rm11 + m21 * rm12;
        double nm12 = m02 * rm10 + m12 * rm11 + m22 * rm12;
        double nm13 = m03 * rm10 + m13 * rm11 + m23 * rm12;
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
        dest.property(properties & ~(PROPERTY_PERSPECTIVE | PROPERTY_IDENTITY | PROPERTY_TRANSLATION));
        return dest;
    }

    public Matrix4dc rotate(IQuaternionf quat, Matrix4dc dest) {
        if ((properties & PROPERTY_IDENTITY) != 0)
            return dest.rotation(quat);
        else if ((properties & PROPERTY_TRANSLATION) != 0)
            return rotateTranslation(quat, dest);
        else if ((properties & PROPERTY_AFFINE) != 0)
            return rotateAffine(quat, dest);
        return rotateGeneric(quat, dest);
    }

    private Matrix4dc rotateGeneric(IQuaternionf quat, Matrix4dc dest) {
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
        double nm03 = m03 * rm00 + m13 * rm01 + m23 * rm02;
        double nm10 = m00 * rm10 + m10 * rm11 + m20 * rm12;
        double nm11 = m01 * rm10 + m11 * rm11 + m21 * rm12;
        double nm12 = m02 * rm10 + m12 * rm11 + m22 * rm12;
        double nm13 = m03 * rm10 + m13 * rm11 + m23 * rm12;
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
        dest.property(properties & ~(PROPERTY_PERSPECTIVE | PROPERTY_IDENTITY | PROPERTY_TRANSLATION));
        return dest;
    }

    public Matrix4dc rotate(IQuaterniond quat) {
        return rotate(quat, this);
    }

    public Matrix4dc rotate(IQuaternionf quat) {
        return rotate(quat, this);
    }

    public Matrix4dc rotateAffine(IQuaterniond quat, Matrix4dc dest) {
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
        dest.m23(0.0);
        dest.m00(nm00);
        dest.m01(nm01);
        dest.m02(nm02);
        dest.m03(0.0);
        dest.m10(nm10);
        dest.m11(nm11);
        dest.m12(nm12);
        dest.m13(0.0);
        dest.m30(m30);
        dest.m31(m31);
        dest.m32(m32);
        dest.m33(m33);
        dest.property(properties & ~(PROPERTY_PERSPECTIVE | PROPERTY_IDENTITY | PROPERTY_TRANSLATION));
        return dest;
    }

    public Matrix4dc rotateAffine(IQuaterniond quat) {
        return rotateAffine(quat, this);
    }

    public Matrix4dc rotateTranslation(IQuaterniond quat, Matrix4dc dest) {
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
        dest.m23(0.0);
        dest.m00(nm00);
        dest.m01(nm01);
        dest.m02(nm02);
        dest.m03(0.0);
        dest.m10(nm10);
        dest.m11(nm11);
        dest.m12(nm12);
        dest.m13(0.0);
        dest.m30(m30);
        dest.m31(m31);
        dest.m32(m32);
        dest.m33(m33);
        dest.property(properties & ~(PROPERTY_PERSPECTIVE | PROPERTY_IDENTITY | PROPERTY_TRANSLATION));
        return dest;
    }

    public Matrix4dc rotateTranslation(IQuaternionf quat, Matrix4dc dest) {
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
        dest.m23(0.0);
        dest.m00(nm00);
        dest.m01(nm01);
        dest.m02(nm02);
        dest.m03(0.0);
        dest.m10(nm10);
        dest.m11(nm11);
        dest.m12(nm12);
        dest.m13(0.0);
        dest.m30(m30);
        dest.m31(m31);
        dest.m32(m32);
        dest.m33(m33);
        dest.property(properties & ~(PROPERTY_PERSPECTIVE | PROPERTY_IDENTITY | PROPERTY_TRANSLATION));
        return dest;
    }

    public Matrix4dc rotateLocal(IQuaterniond quat, Matrix4dc dest) {
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
        double nm03 = m03;
        double nm10 = lm00 * m10 + lm10 * m11 + lm20 * m12;
        double nm11 = lm01 * m10 + lm11 * m11 + lm21 * m12;
        double nm12 = lm02 * m10 + lm12 * m11 + lm22 * m12;
        double nm13 = m13;
        double nm20 = lm00 * m20 + lm10 * m21 + lm20 * m22;
        double nm21 = lm01 * m20 + lm11 * m21 + lm21 * m22;
        double nm22 = lm02 * m20 + lm12 * m21 + lm22 * m22;
        double nm23 = m23;
        double nm30 = lm00 * m30 + lm10 * m31 + lm20 * m32;
        double nm31 = lm01 * m30 + lm11 * m31 + lm21 * m32;
        double nm32 = lm02 * m30 + lm12 * m31 + lm22 * m32;
        double nm33 = m33;
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
        dest.property(properties & ~(PROPERTY_PERSPECTIVE | PROPERTY_IDENTITY | PROPERTY_TRANSLATION));
        return dest;
    }

    public Matrix4dc rotateLocal(IQuaterniond quat) {
        return rotateLocal(quat, this);
    }

    public Matrix4dc rotateAffine(IQuaternionf quat, Matrix4dc dest) {
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
        dest.m23(0.0);
        dest.m00(nm00);
        dest.m01(nm01);
        dest.m02(nm02);
        dest.m03(0.0);
        dest.m10(nm10);
        dest.m11(nm11);
        dest.m12(nm12);
        dest.m13(0.0);
        dest.m30(m30);
        dest.m31(m31);
        dest.m32(m32);
        dest.m33(m33);
        dest.property(properties & ~(PROPERTY_PERSPECTIVE | PROPERTY_IDENTITY | PROPERTY_TRANSLATION));
        return dest;
    }

    public Matrix4dc rotateAffine(IQuaternionf quat) {
        return rotateAffine(quat, this);
    }

    public Matrix4dc rotateLocal(IQuaternionf quat, Matrix4dc dest) {
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
        double nm03 = m03;
        double nm10 = lm00 * m10 + lm10 * m11 + lm20 * m12;
        double nm11 = lm01 * m10 + lm11 * m11 + lm21 * m12;
        double nm12 = lm02 * m10 + lm12 * m11 + lm22 * m12;
        double nm13 = m13;
        double nm20 = lm00 * m20 + lm10 * m21 + lm20 * m22;
        double nm21 = lm01 * m20 + lm11 * m21 + lm21 * m22;
        double nm22 = lm02 * m20 + lm12 * m21 + lm22 * m22;
        double nm23 = m23;
        double nm30 = lm00 * m30 + lm10 * m31 + lm20 * m32;
        double nm31 = lm01 * m30 + lm11 * m31 + lm21 * m32;
        double nm32 = lm02 * m30 + lm12 * m31 + lm22 * m32;
        double nm33 = m33;
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
        dest.property(properties & ~(PROPERTY_PERSPECTIVE | PROPERTY_IDENTITY | PROPERTY_TRANSLATION));
        return dest;
    }

    public Matrix4dc rotateLocal(IQuaternionf quat) {
        return rotateLocal(quat, this);
    }

    public Matrix4dc rotate(AxisAngle4fc axisAngle) {
        return rotate(axisAngle.angle(), axisAngle.x(), axisAngle.y(), axisAngle.z());
    }

    public Matrix4dc rotate(AxisAngle4fc axisAngle, Matrix4dc dest) {
        return rotate(axisAngle.angle(), axisAngle.x(), axisAngle.y(), axisAngle.z(), dest);
    }

    public Matrix4dc rotate(AxisAngle4dc axisAngle) {
        return rotate(axisAngle.angle(), axisAngle.x(), axisAngle.y(), axisAngle.z());
    }

    public Matrix4dc rotate(AxisAngle4dc axisAngle, Matrix4dc dest) {
        return rotate(axisAngle.angle(), axisAngle.x(), axisAngle.y(), axisAngle.z(), dest);
    }

    public Matrix4dc rotate(double angle, IVector3d axis) {
        return rotate(angle, axis.x(), axis.y(), axis.z());
    }

    public Matrix4dc rotate(double angle, IVector3d axis, Matrix4dc dest) {
        return rotate(angle, axis.x(), axis.y(), axis.z(), dest);
    }

    public Matrix4dc rotate(double angle, IVector3f axis) {
        return rotate(angle, axis.x(), axis.y(), axis.z());
    }

    public Matrix4dc rotate(double angle, IVector3f axis, Matrix4dc dest) {
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
            case 3:
                dest.set(m03, m13, m23, m33);
                break;
            default:
                throw new IndexOutOfBoundsException();
        }
        return dest;
    }

    public Matrix4dc setRow(int row, IVector4d src) throws IndexOutOfBoundsException {
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
            case 3:
                this.m03 = src.x();
                this.m13 = src.y();
                this.m23 = src.z();
                this.m33 = src.w();
                break;
            default:
                throw new IndexOutOfBoundsException();
        }
        return this;
    }

    public Vector4dc getColumn(int column, Vector4dc dest) throws IndexOutOfBoundsException {
        switch (column) {
            case 0:
                dest.set(m00, m01, m02, m03);
                break;
            case 1:
                dest.set(m10, m11, m12, m13);
                break;
            case 2:
                dest.set(m20, m21, m22, m23);
                break;
            case 3:
                dest.set(m30, m31, m32, m33);
                break;
            default:
                throw new IndexOutOfBoundsException();
        }
        return dest;
    }

    public Matrix4dc setColumn(int column, IVector4d src) throws IndexOutOfBoundsException {
        switch (column) {
            case 0:
                this.m00 = src.x();
                this.m01 = src.y();
                this.m02 = src.z();
                this.m03 = src.w();
                break;
            case 1:
                this.m10 = src.x();
                this.m11 = src.y();
                this.m12 = src.z();
                this.m13 = src.w();
                break;
            case 2:
                this.m20 = src.x();
                this.m21 = src.y();
                this.m22 = src.z();
                this.m23 = src.w();
                break;
            case 3:
                this.m30 = src.x();
                this.m31 = src.y();
                this.m32 = src.z();
                this.m33 = src.w();
                break;
            default:
                throw new IndexOutOfBoundsException();
        }
        return this;
    }

    public Matrix4dc normal() {
        return normal(this);
    }

    public Matrix4dc normal(Matrix4dc dest) {
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
        dest.m03(0.0);
        dest.m10(nm10);
        dest.m11(nm11);
        dest.m12(nm12);
        dest.m13(0.0);
        dest.m20(nm20);
        dest.m21(nm21);
        dest.m22(nm22);
        dest.m23(0.0);
        dest.m30(0.0);
        dest.m31(0.0);
        dest.m32(0.0);
        dest.m33(1.0);
        dest.property(PROPERTY_AFFINE);
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

    public Matrix4dc normalize3x3() {
        return normalize3x3(this);
    }

    public Matrix4dc normalize3x3(Matrix4dc dest) {
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

    public Vector4dc unproject(double winX, double winY, double winZ, int[] viewport, Vector4dc dest) {
        double a = m00 * m11 - m01 * m10;
        double b = m00 * m12 - m02 * m10;
        double c = m00 * m13 - m03 * m10;
        double d = m01 * m12 - m02 * m11;
        double e = m01 * m13 - m03 * m11;
        double f = m02 * m13 - m03 * m12;
        double g = m20 * m31 - m21 * m30;
        double h = m20 * m32 - m22 * m30;
        double i = m20 * m33 - m23 * m30;
        double j = m21 * m32 - m22 * m31;
        double k = m21 * m33 - m23 * m31;
        double l = m22 * m33 - m23 * m32;
        double det = a * l - b * k + c * j + d * i - e * h + f * g;
        det = 1.0 / det;
        double im00 = (m11 * l - m12 * k + m13 * j) * det;
        double im01 = (-m01 * l + m02 * k - m03 * j) * det;
        double im02 = (m31 * f - m32 * e + m33 * d) * det;
        double im03 = (-m21 * f + m22 * e - m23 * d) * det;
        double im10 = (-m10 * l + m12 * i - m13 * h) * det;
        double im11 = (m00 * l - m02 * i + m03 * h) * det;
        double im12 = (-m30 * f + m32 * c - m33 * b) * det;
        double im13 = (m20 * f - m22 * c + m23 * b) * det;
        double im20 = (m10 * k - m11 * i + m13 * g) * det;
        double im21 = (-m00 * k + m01 * i - m03 * g) * det;
        double im22 = (m30 * e - m31 * c + m33 * a) * det;
        double im23 = (-m20 * e + m21 * c - m23 * a) * det;
        double im30 = (-m10 * j + m11 * h - m12 * g) * det;
        double im31 = (m00 * j - m01 * h + m02 * g) * det;
        double im32 = (-m30 * d + m31 * b - m32 * a) * det;
        double im33 = (m20 * d - m21 * b + m22 * a) * det;
        double ndcX = (winX - viewport[0]) / viewport[2] * 2.0 - 1.0;
        double ndcY = (winY - viewport[1]) / viewport[3] * 2.0 - 1.0;
        double ndcZ = winZ + winZ - 1.0;
        dest.set(im00 * ndcX + im10 * ndcY + im20 * ndcZ + im30,
                im01 * ndcX + im11 * ndcY + im21 * ndcZ + im31,
                im02 * ndcX + im12 * ndcY + im22 * ndcZ + im32,
                im03 * ndcX + im13 * ndcY + im23 * ndcZ + im33);
        dest.div(dest.w());
        return dest;
    }

    public Vector3dc unproject(double winX, double winY, double winZ, int[] viewport, Vector3dc dest) {
        double a = m00 * m11 - m01 * m10;
        double b = m00 * m12 - m02 * m10;
        double c = m00 * m13 - m03 * m10;
        double d = m01 * m12 - m02 * m11;
        double e = m01 * m13 - m03 * m11;
        double f = m02 * m13 - m03 * m12;
        double g = m20 * m31 - m21 * m30;
        double h = m20 * m32 - m22 * m30;
        double i = m20 * m33 - m23 * m30;
        double j = m21 * m32 - m22 * m31;
        double k = m21 * m33 - m23 * m31;
        double l = m22 * m33 - m23 * m32;
        double det = a * l - b * k + c * j + d * i - e * h + f * g;
        det = 1.0 / det;
        double im00 = (m11 * l - m12 * k + m13 * j) * det;
        double im01 = (-m01 * l + m02 * k - m03 * j) * det;
        double im02 = (m31 * f - m32 * e + m33 * d) * det;
        double im03 = (-m21 * f + m22 * e - m23 * d) * det;
        double im10 = (-m10 * l + m12 * i - m13 * h) * det;
        double im11 = (m00 * l - m02 * i + m03 * h) * det;
        double im12 = (-m30 * f + m32 * c - m33 * b) * det;
        double im13 = (m20 * f - m22 * c + m23 * b) * det;
        double im20 = (m10 * k - m11 * i + m13 * g) * det;
        double im21 = (-m00 * k + m01 * i - m03 * g) * det;
        double im22 = (m30 * e - m31 * c + m33 * a) * det;
        double im23 = (-m20 * e + m21 * c - m23 * a) * det;
        double im30 = (-m10 * j + m11 * h - m12 * g) * det;
        double im31 = (m00 * j - m01 * h + m02 * g) * det;
        double im32 = (-m30 * d + m31 * b - m32 * a) * det;
        double im33 = (m20 * d - m21 * b + m22 * a) * det;
        double ndcX = (winX - viewport[0]) / viewport[2] * 2.0 - 1.0;
        double ndcY = (winY - viewport[1]) / viewport[3] * 2.0 - 1.0;
        double ndcZ = winZ + winZ - 1.0;
        dest.set(im00 * ndcX + im10 * ndcY + im20 * ndcZ + im30,
                im01 * ndcX + im11 * ndcY + im21 * ndcZ + im31,
                im02 * ndcX + im12 * ndcY + im22 * ndcZ + im32);
        double w = im03 * ndcX + im13 * ndcY + im23 * ndcZ + im33;
        dest.div(w);
        return dest;
    }

    public Vector4dc unproject(IVector3d winCoords, int[] viewport, Vector4dc dest) {
        return unproject(winCoords.x(), winCoords.y(), winCoords.z(), viewport, dest);
    }

    public Vector3dc unproject(IVector3d winCoords, int[] viewport, Vector3dc dest) {
        return unproject(winCoords.x(), winCoords.y(), winCoords.z(), viewport, dest);
    }

    public Matrix4dc unprojectRay(double winX, double winY, int[] viewport, Vector3dc originDest, Vector3dc dirDest) {
        double a = m00 * m11 - m01 * m10;
        double b = m00 * m12 - m02 * m10;
        double c = m00 * m13 - m03 * m10;
        double d = m01 * m12 - m02 * m11;
        double e = m01 * m13 - m03 * m11;
        double f = m02 * m13 - m03 * m12;
        double g = m20 * m31 - m21 * m30;
        double h = m20 * m32 - m22 * m30;
        double i = m20 * m33 - m23 * m30;
        double j = m21 * m32 - m22 * m31;
        double k = m21 * m33 - m23 * m31;
        double l = m22 * m33 - m23 * m32;
        double det = a * l - b * k + c * j + d * i - e * h + f * g;
        det = 1.0 / det;
        double im00 = (m11 * l - m12 * k + m13 * j) * det;
        double im01 = (-m01 * l + m02 * k - m03 * j) * det;
        double im02 = (m31 * f - m32 * e + m33 * d) * det;
        double im03 = (-m21 * f + m22 * e - m23 * d) * det;
        double im10 = (-m10 * l + m12 * i - m13 * h) * det;
        double im11 = (m00 * l - m02 * i + m03 * h) * det;
        double im12 = (-m30 * f + m32 * c - m33 * b) * det;
        double im13 = (m20 * f - m22 * c + m23 * b) * det;
        double im20 = (m10 * k - m11 * i + m13 * g) * det;
        double im21 = (-m00 * k + m01 * i - m03 * g) * det;
        double im22 = (m30 * e - m31 * c + m33 * a) * det;
        double im23 = (-m20 * e + m21 * c - m23 * a) * det;
        double im30 = (-m10 * j + m11 * h - m12 * g) * det;
        double im31 = (m00 * j - m01 * h + m02 * g) * det;
        double im32 = (-m30 * d + m31 * b - m32 * a) * det;
        double im33 = (m20 * d - m21 * b + m22 * a) * det;
        double ndcX = (winX - viewport[0]) / viewport[2] * 2.0 - 1.0;
        double ndcY = (winY - viewport[1]) / viewport[3] * 2.0 - 1.0;
        double nearX = im00 * ndcX + im10 * ndcY - im20 + im30;
        double nearY = im01 * ndcX + im11 * ndcY - im21 + im31;
        double nearZ = im02 * ndcX + im12 * ndcY - im22 + im32;
        double invNearW = 1.0 / (im03 * ndcX + im13 * ndcY - im23 + im33);
        nearX *= invNearW;
        nearY *= invNearW;
        nearZ *= invNearW;
        double farX = im00 * ndcX + im10 * ndcY + im20 + im30;
        double farY = im01 * ndcX + im11 * ndcY + im21 + im31;
        double farZ = im02 * ndcX + im12 * ndcY + im22 + im32;
        double invFarW = 1.0 / (im03 * ndcX + im13 * ndcY + im23 + im33);
        farX *= invFarW;
        farY *= invFarW;
        farZ *= invFarW;
        originDest.set(nearX, nearY, nearZ);
        dirDest.set(farX - nearX, farY - nearY, farZ - nearZ);
        return this;
    }

    public Matrix4dc unprojectRay(IVector2d winCoords, int[] viewport, Vector3dc originDest, Vector3dc dirDest) {
        return unprojectRay(winCoords.x(), winCoords.y(), viewport, originDest, dirDest);
    }

    public Vector4dc unprojectInv(IVector3d winCoords, int[] viewport, Vector4dc dest) {
        return unprojectInv(winCoords.x(), winCoords.y(), winCoords.z(), viewport, dest);
    }

    public Vector4dc unprojectInv(double winX, double winY, double winZ, int[] viewport, Vector4dc dest) {
        double ndcX = (winX - viewport[0]) / viewport[2] * 2.0 - 1.0;
        double ndcY = (winY - viewport[1]) / viewport[3] * 2.0 - 1.0;
        double ndcZ = winZ + winZ - 1.0;
        dest.set(m00 * ndcX + m10 * ndcY + m20 * ndcZ + m30,
                m01 * ndcX + m11 * ndcY + m21 * ndcZ + m31,
                m02 * ndcX + m12 * ndcY + m22 * ndcZ + m32,
                m03 * ndcX + m13 * ndcY + m23 * ndcZ + m33);
        dest.div(dest.w());
        return dest;
    }

    public Vector3dc unprojectInv(IVector3d winCoords, int[] viewport, Vector3dc dest) {
        return unprojectInv(winCoords.x(), winCoords.y(), winCoords.z(), viewport, dest);
    }

    public Vector3dc unprojectInv(double winX, double winY, double winZ, int[] viewport, Vector3dc dest) {
        double ndcX = (winX - viewport[0]) / viewport[2] * 2.0 - 1.0;
        double ndcY = (winY - viewport[1]) / viewport[3] * 2.0 - 1.0;
        double ndcZ = winZ + winZ - 1.0;
        dest.set(m00 * ndcX + m10 * ndcY + m20 * ndcZ + m30,
                m01 * ndcX + m11 * ndcY + m21 * ndcZ + m31,
                m02 * ndcX + m12 * ndcY + m22 * ndcZ + m32);
        double w = m03 * ndcX + m13 * ndcY + m23 * ndcZ + m33;
        dest.div(w);
        return dest;
    }

    public Matrix4dc unprojectInvRay(IVector2d winCoords, int[] viewport, Vector3dc originDest, Vector3dc dirDest) {
        return unprojectInvRay(winCoords.x(), winCoords.y(), viewport, originDest, dirDest);
    }

    public Matrix4dc unprojectInvRay(double winX, double winY, int[] viewport, Vector3dc originDest, Vector3dc dirDest) {
        double ndcX = (winX - viewport[0]) / viewport[2] * 2.0 - 1.0;
        double ndcY = (winY - viewport[1]) / viewport[3] * 2.0 - 1.0;
        double nearX = m00 * ndcX + m10 * ndcY - m20 + m30;
        double nearY = m01 * ndcX + m11 * ndcY - m21 + m31;
        double nearZ = m02 * ndcX + m12 * ndcY - m22 + m32;
        double invNearW = 1.0 / (m03 * ndcX + m13 * ndcY - m23 + m33);
        nearX *= invNearW;
        nearY *= invNearW;
        nearZ *= invNearW;
        double farX = m00 * ndcX + m10 * ndcY + m20 + m30;
        double farY = m01 * ndcX + m11 * ndcY + m21 + m31;
        double farZ = m02 * ndcX + m12 * ndcY + m22 + m32;
        double invFarW = 1.0 / (m03 * ndcX + m13 * ndcY + m23 + m33);
        farX *= invFarW;
        farY *= invFarW;
        farZ *= invFarW;
        originDest.set(nearX, nearY, nearZ);
        dirDest.set(farX - nearX, farY - nearY, farZ - nearZ);
        return this;
    }

    public Vector4dc project(double x, double y, double z, int[] viewport, Vector4dc dest) {
        dest.set(m00 * x + m10 * y + m20 * z + m30, m01 * x + m11 * y + m21 * z + m31, m02 * x + m12 * y + m22 * z + m32, m03 * x + m13 * y + m23 * z + m33);
        dest.div(dest.w());
        dest.set((dest.x() * 0.5 + 0.5) * viewport[2] + viewport[0],
                (dest.y() * 0.5 + 0.5) * viewport[3] + viewport[1],
                (1.0 + dest.z()) * 0.5,
                dest.w());
        return dest;
    }

    public Vector3dc project(double x, double y, double z, int[] viewport, Vector3dc dest) {
        dest.set(m00 * x + m10 * y + m20 * z + m30, m01 * x + m11 * y + m21 * z + m31, m02 * x + m12 * y + m22 * z + m32);
        double w = m03 * x + m13 * y + m23 * z + m33;
        dest.div(w);
        dest.set((dest.x() * 0.5 + 0.5) * viewport[2] + viewport[0],
                (dest.y() * 0.5 + 0.5) * viewport[3] + viewport[1],
                (1.0 + dest.z()) * 0.5);
        return dest;
    }

    public Vector4dc project(IVector3d position, int[] viewport, Vector4dc dest) {
        return project(position.x(), position.y(), position.z(), viewport, dest);
    }

    public Vector3dc project(IVector3d position, int[] viewport, Vector3dc dest) {
        return project(position.x(), position.y(), position.z(), viewport, dest);
    }

    public Matrix4dc reflect(double a, double b, double c, double d, Matrix4dc dest) {
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
        dest.m33(m03 * rm30 + m13 * rm31 + m23 * rm32 + m33);
        double nm00 = m00 * rm00 + m10 * rm01 + m20 * rm02;
        double nm01 = m01 * rm00 + m11 * rm01 + m21 * rm02;
        double nm02 = m02 * rm00 + m12 * rm01 + m22 * rm02;
        double nm03 = m03 * rm00 + m13 * rm01 + m23 * rm02;
        double nm10 = m00 * rm10 + m10 * rm11 + m20 * rm12;
        double nm11 = m01 * rm10 + m11 * rm11 + m21 * rm12;
        double nm12 = m02 * rm10 + m12 * rm11 + m22 * rm12;
        double nm13 = m03 * rm10 + m13 * rm11 + m23 * rm12;
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
        dest.property(properties & ~(PROPERTY_PERSPECTIVE | PROPERTY_IDENTITY | PROPERTY_TRANSLATION));

        return dest;
    }

    public Matrix4dc reflect(double a, double b, double c, double d) {
        return reflect(a, b, c, d, this);
    }

    public Matrix4dc reflect(double nx, double ny, double nz, double px, double py, double pz) {
        return reflect(nx, ny, nz, px, py, pz, this);
    }

    public Matrix4dc reflect(double nx, double ny, double nz, double px, double py, double pz, Matrix4dc dest) {
        double invLength = 1.0 / Math.sqrt(nx * nx + ny * ny + nz * nz);
        double nnx = nx * invLength;
        double nny = ny * invLength;
        double nnz = nz * invLength;
        /* See: http://mathworld.wolfram.com/Plane.html */
        return reflect(nnx, nny, nnz, -nnx * px - nny * py - nnz * pz, dest);
    }

    public Matrix4dc reflect(IVector3d normal, IVector3d point) {
        return reflect(normal.x(), normal.y(), normal.z(), point.x(), point.y(), point.z());
    }

    public Matrix4dc reflect(IQuaterniond orientation, IVector3d point) {
        return reflect(orientation, point, this);
    }

    public Matrix4dc reflect(IQuaterniond orientation, IVector3d point, Matrix4dc dest) {
        double num1 = orientation.x() + orientation.x();
        double num2 = orientation.y() + orientation.y();
        double num3 = orientation.z() + orientation.z();
        double normalX = orientation.x() * num3 + orientation.w() * num2;
        double normalY = orientation.y() * num3 - orientation.w() * num1;
        double normalZ = 1.0 - (orientation.x() * num1 + orientation.y() * num2);
        return reflect(normalX, normalY, normalZ, point.x(), point.y(), point.z(), dest);
    }

    public Matrix4dc reflect(IVector3d normal, IVector3d point, Matrix4dc dest) {
        return reflect(normal.x(), normal.y(), normal.z(), point.x(), point.y(), point.z(), dest);
    }

    public Matrix4dc reflection(double a, double b, double c, double d) {
        double da = a + a, db = b + b, dc = c + c, dd = d + d;
        m00 = 1.0 - da * a;
        m01 = -da * b;
        m02 = -da * c;
        m03 = 0.0;
        m10 = -db * a;
        m11 = 1.0 - db * b;
        m12 = -db * c;
        m13 = 0.0;
        m20 = -dc * a;
        m21 = -dc * b;
        m22 = 1.0 - dc * c;
        m23 = 0.0;
        m30 = -dd * a;
        m31 = -dd * b;
        m32 = -dd * c;
        m33 = 1.0;
        properties = PROPERTY_AFFINE;
        return this;
    }

    public Matrix4dc reflection(double nx, double ny, double nz, double px, double py, double pz) {
        double invLength = 1.0 / Math.sqrt(nx * nx + ny * ny + nz * nz);
        double nnx = nx * invLength;
        double nny = ny * invLength;
        double nnz = nz * invLength;
        /* See: http://mathworld.wolfram.com/Plane.html */
        return reflection(nnx, nny, nnz, -nnx * px - nny * py - nnz * pz);
    }

    public Matrix4dc reflection(IVector3d normal, IVector3d point) {
        return reflection(normal.x(), normal.y(), normal.z(), point.x(), point.y(), point.z());
    }

    public Matrix4dc reflection(IQuaterniond orientation, IVector3d point) {
        double num1 = orientation.x() + orientation.x();
        double num2 = orientation.y() + orientation.y();
        double num3 = orientation.z() + orientation.z();
        double normalX = orientation.x() * num3 + orientation.w() * num2;
        double normalY = orientation.y() * num3 - orientation.w() * num1;
        double normalZ = 1.0 - (orientation.x() * num1 + orientation.y() * num2);
        return reflection(normalX, normalY, normalZ, point.x(), point.y(), point.z());
    }

    public Matrix4dc ortho(double left, double right, double bottom, double top, double zNear, double zFar, boolean zZeroToOne, Matrix4dc dest) {
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
        dest.property(properties & ~(PROPERTY_PERSPECTIVE | PROPERTY_IDENTITY | PROPERTY_TRANSLATION));

        return dest;
    }

    public Matrix4dc ortho(double left, double right, double bottom, double top, double zNear, double zFar, Matrix4dc dest) {
        return ortho(left, right, bottom, top, zNear, zFar, false, dest);
    }

    public Matrix4dc ortho(double left, double right, double bottom, double top, double zNear, double zFar, boolean zZeroToOne) {
        return ortho(left, right, bottom, top, zNear, zFar, zZeroToOne, this);
    }

    public Matrix4dc ortho(double left, double right, double bottom, double top, double zNear, double zFar) {
        return ortho(left, right, bottom, top, zNear, zFar, false);
    }

    public Matrix4dc orthoLH(double left, double right, double bottom, double top, double zNear, double zFar, boolean zZeroToOne, Matrix4dc dest) {
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
        dest.property(properties & ~(PROPERTY_PERSPECTIVE | PROPERTY_IDENTITY | PROPERTY_TRANSLATION));

        return dest;
    }

    public Matrix4dc orthoLH(double left, double right, double bottom, double top, double zNear, double zFar, Matrix4dc dest) {
        return orthoLH(left, right, bottom, top, zNear, zFar, false, dest);
    }

    public Matrix4dc orthoLH(double left, double right, double bottom, double top, double zNear, double zFar, boolean zZeroToOne) {
        return orthoLH(left, right, bottom, top, zNear, zFar, zZeroToOne, this);
    }

    public Matrix4dc orthoLH(double left, double right, double bottom, double top, double zNear, double zFar) {
        return orthoLH(left, right, bottom, top, zNear, zFar, false);
    }

    public Matrix4dc setOrtho(double left, double right, double bottom, double top, double zNear, double zFar, boolean zZeroToOne) {
        m00 = 2.0 / (right - left);
        m01 = 0.0;
        m02 = 0.0;
        m03 = 0.0;
        m10 = 0.0;
        m11 = 2.0 / (top - bottom);
        m12 = 0.0;
        m13 = 0.0;
        m20 = 0.0;
        m21 = 0.0;
        m22 = (zZeroToOne ? 1.0 : 2.0) / (zNear - zFar);
        m23 = 0.0;
        m30 = (right + left) / (left - right);
        m31 = (top + bottom) / (bottom - top);
        m32 = (zZeroToOne ? zNear : (zFar + zNear)) / (zNear - zFar);
        m33 = 1.0;
        properties = PROPERTY_AFFINE;
        return this;
    }

    public Matrix4dc setOrtho(double left, double right, double bottom, double top, double zNear, double zFar) {
        return setOrtho(left, right, bottom, top, zNear, zFar, false);
    }

    public Matrix4dc setOrthoLH(double left, double right, double bottom, double top, double zNear, double zFar, boolean zZeroToOne) {
        m00 = 2.0 / (right - left);
        m01 = 0.0;
        m02 = 0.0;
        m03 = 0.0;
        m10 = 0.0;
        m11 = 2.0 / (top - bottom);
        m12 = 0.0;
        m13 = 0.0;
        m20 = 0.0;
        m21 = 0.0;
        m22 = (zZeroToOne ? 1.0 : 2.0) / (zFar - zNear);
        m23 = 0.0;
        m30 = (right + left) / (left - right);
        m31 = (top + bottom) / (bottom - top);
        m32 = (zZeroToOne ? zNear : (zFar + zNear)) / (zNear - zFar);
        m33 = 1.0;
        properties = PROPERTY_AFFINE;
        return this;
    }

    public Matrix4dc setOrthoLH(double left, double right, double bottom, double top, double zNear, double zFar) {
        return setOrthoLH(left, right, bottom, top, zNear, zFar, false);
    }

    public Matrix4dc orthoSymmetric(double width, double height, double zNear, double zFar, boolean zZeroToOne, Matrix4dc dest) {
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
        dest.property(properties & ~(PROPERTY_PERSPECTIVE | PROPERTY_IDENTITY | PROPERTY_TRANSLATION));

        return dest;
    }

    public Matrix4dc orthoSymmetric(double width, double height, double zNear, double zFar, Matrix4dc dest) {
        return orthoSymmetric(width, height, zNear, zFar, false, dest);
    }

    public Matrix4dc orthoSymmetric(double width, double height, double zNear, double zFar, boolean zZeroToOne) {
        return orthoSymmetric(width, height, zNear, zFar, zZeroToOne, this);
    }

    public Matrix4dc orthoSymmetric(double width, double height, double zNear, double zFar) {
        return orthoSymmetric(width, height, zNear, zFar, false, this);
    }

    public Matrix4dc orthoSymmetricLH(double width, double height, double zNear, double zFar, boolean zZeroToOne, Matrix4dc dest) {
        // calculate right matrix elements
        double rm00 = 2.0 / width;
        double rm11 = 2.0 / height;
        double rm22 = (zZeroToOne ? 1.0 : 2.0) / (zFar - zNear);
        double rm32 = (zZeroToOne ? zNear : (zFar + zNear)) / (zNear - zFar);

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
        dest.property(properties & ~(PROPERTY_PERSPECTIVE | PROPERTY_IDENTITY | PROPERTY_TRANSLATION));

        return dest;
    }

    public Matrix4dc orthoSymmetricLH(double width, double height, double zNear, double zFar, Matrix4dc dest) {
        return orthoSymmetricLH(width, height, zNear, zFar, false, dest);
    }

    public Matrix4dc orthoSymmetricLH(double width, double height, double zNear, double zFar, boolean zZeroToOne) {
        return orthoSymmetricLH(width, height, zNear, zFar, zZeroToOne, this);
    }

    public Matrix4dc orthoSymmetricLH(double width, double height, double zNear, double zFar) {
        return orthoSymmetricLH(width, height, zNear, zFar, false, this);
    }

    public Matrix4dc setOrthoSymmetric(double width, double height, double zNear, double zFar, boolean zZeroToOne) {
        m00 = 2.0 / width;
        m01 = 0.0;
        m02 = 0.0;
        m03 = 0.0;
        m10 = 0.0;
        m11 = 2.0 / height;
        m12 = 0.0;
        m13 = 0.0;
        m20 = 0.0;
        m21 = 0.0;
        m22 = (zZeroToOne ? 1.0 : 2.0) / (zNear - zFar);
        m23 = 0.0;
        m30 = 0.0;
        m31 = 0.0;
        m32 = (zZeroToOne ? zNear : (zFar + zNear)) / (zNear - zFar);
        m33 = 1.0;
        properties = PROPERTY_AFFINE;
        return this;
    }

    public Matrix4dc setOrthoSymmetric(double width, double height, double zNear, double zFar) {
        return setOrthoSymmetric(width, height, zNear, zFar, false);
    }

    public Matrix4dc setOrthoSymmetricLH(double width, double height, double zNear, double zFar, boolean zZeroToOne) {
        m00 = 2.0 / width;
        m01 = 0.0;
        m02 = 0.0;
        m03 = 0.0;
        m10 = 0.0;
        m11 = 2.0 / height;
        m12 = 0.0;
        m13 = 0.0;
        m20 = 0.0;
        m21 = 0.0;
        m22 = (zZeroToOne ? 1.0 : 2.0) / (zFar - zNear);
        m23 = 0.0;
        m30 = 0.0;
        m31 = 0.0;
        m32 = (zZeroToOne ? zNear : (zFar + zNear)) / (zNear - zFar);
        m33 = 1.0;
        properties = PROPERTY_AFFINE;
        return this;
    }

    public Matrix4dc setOrthoSymmetricLH(double width, double height, double zNear, double zFar) {
        return setOrthoSymmetricLH(width, height, zNear, zFar, false);
    }

    public Matrix4dc ortho2D(double left, double right, double bottom, double top, Matrix4dc dest) {
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
        dest.property(properties & ~(PROPERTY_PERSPECTIVE | PROPERTY_IDENTITY | PROPERTY_TRANSLATION));

        return dest;
    }

    public Matrix4dc ortho2D(double left, double right, double bottom, double top) {
        return ortho2D(left, right, bottom, top, this);
    }

    public Matrix4dc ortho2DLH(double left, double right, double bottom, double top, Matrix4dc dest) {
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
        dest.property(properties & ~(PROPERTY_PERSPECTIVE | PROPERTY_IDENTITY | PROPERTY_TRANSLATION));

        return dest;
    }

    public Matrix4dc ortho2DLH(double left, double right, double bottom, double top) {
        return ortho2DLH(left, right, bottom, top, this);
    }

    public Matrix4dc setOrtho2D(double left, double right, double bottom, double top) {
        m00 = 2.0 / (right - left);
        m01 = 0.0;
        m02 = 0.0;
        m03 = 0.0;
        m10 = 0.0;
        m11 = 2.0 / (top - bottom);
        m12 = 0.0;
        m13 = 0.0;
        m20 = 0.0;
        m21 = 0.0;
        m22 = -1.0;
        m23 = 0.0;
        m30 = -(right + left) / (right - left);
        m31 = -(top + bottom) / (top - bottom);
        m32 = 0.0;
        m33 = 1.0;
        properties = PROPERTY_AFFINE;
        return this;
    }

    public Matrix4dc setOrtho2DLH(double left, double right, double bottom, double top) {
        m00 = 2.0 / (right - left);
        m01 = 0.0;
        m02 = 0.0;
        m03 = 0.0;
        m10 = 0.0;
        m11 = 2.0 / (top - bottom);
        m12 = 0.0;
        m13 = 0.0;
        m20 = 0.0;
        m21 = 0.0;
        m22 = 1.0;
        m23 = 0.0;
        m30 = -(right + left) / (right - left);
        m31 = -(top + bottom) / (top - bottom);
        m32 = 0.0;
        m33 = 1.0;
        properties = PROPERTY_AFFINE;
        return this;
    }

    public Matrix4dc lookAlong(IVector3d dir, IVector3d up) {
        return lookAlong(dir.x(), dir.y(), dir.z(), up.x(), up.y(), up.z(), this);
    }

    public Matrix4dc lookAlong(IVector3d dir, IVector3d up, Matrix4dc dest) {
        return lookAlong(dir.x(), dir.y(), dir.z(), up.x(), up.y(), up.z(), dest);
    }

    public Matrix4dc lookAlong(double dirX, double dirY, double dirZ,
                               double upX, double upY, double upZ, Matrix4dc dest) {
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
        double nm03 = m03 * rm00 + m13 * rm01 + m23 * rm02;
        double nm10 = m00 * rm10 + m10 * rm11 + m20 * rm12;
        double nm11 = m01 * rm10 + m11 * rm11 + m21 * rm12;
        double nm12 = m02 * rm10 + m12 * rm11 + m22 * rm12;
        double nm13 = m03 * rm10 + m13 * rm11 + m23 * rm12;
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
        dest.property(properties & ~(PROPERTY_PERSPECTIVE | PROPERTY_IDENTITY | PROPERTY_TRANSLATION));

        return dest;
    }

    public Matrix4dc lookAlong(double dirX, double dirY, double dirZ,
                               double upX, double upY, double upZ) {
        return lookAlong(dirX, dirY, dirZ, upX, upY, upZ, this);
    }

    public Matrix4dc setLookAlong(IVector3d dir, IVector3d up) {
        return setLookAlong(dir.x(), dir.y(), dir.z(), up.x(), up.y(), up.z());
    }

    public Matrix4dc setLookAlong(double dirX, double dirY, double dirZ,
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
        m03 = 0.0;
        m10 = rightY;
        m11 = upnY;
        m12 = -dirnY;
        m13 = 0.0;
        m20 = rightZ;
        m21 = upnZ;
        m22 = -dirnZ;
        m23 = 0.0;
        m30 = 0.0;
        m31 = 0.0;
        m32 = 0.0;
        m33 = 1.0;
        properties = PROPERTY_AFFINE;

        return this;
    }

    public Matrix4dc setLookAt(IVector3d eye, IVector3d center, IVector3d up) {
        return setLookAt(eye.x(), eye.y(), eye.z(), center.x(), center.y(), center.z(), up.x(), up.y(), up.z());
    }

    public Matrix4dc setLookAt(double eyeX, double eyeY, double eyeZ,
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
        m03 = 0.0;
        m10 = leftY;
        m11 = upnY;
        m12 = dirY;
        m13 = 0.0;
        m20 = leftZ;
        m21 = upnZ;
        m22 = dirZ;
        m23 = 0.0;
        m30 = -(leftX * eyeX + leftY * eyeY + leftZ * eyeZ);
        m31 = -(upnX * eyeX + upnY * eyeY + upnZ * eyeZ);
        m32 = -(dirX * eyeX + dirY * eyeY + dirZ * eyeZ);
        m33 = 1.0;
        properties = PROPERTY_AFFINE;

        return this;
    }

    public Matrix4dc lookAt(IVector3d eye, IVector3d center, IVector3d up, Matrix4dc dest) {
        return lookAt(eye.x(), eye.y(), eye.z(), center.x(), center.y(), center.z(), up.x(), up.y(), up.z(), dest);
    }

    public Matrix4dc lookAt(IVector3d eye, IVector3d center, IVector3d up) {
        return lookAt(eye.x(), eye.y(), eye.z(), center.x(), center.y(), center.z(), up.x(), up.y(), up.z(), this);
    }

    public Matrix4dc lookAt(double eyeX, double eyeY, double eyeZ,
                            double centerX, double centerY, double centerZ,
                            double upX, double upY, double upZ, Matrix4dc dest) {
        if ((properties & PROPERTY_IDENTITY) != 0)
            return dest.setLookAt(eyeX, eyeY, eyeZ, centerX, centerY, centerZ, upX, upY, upZ);
        else if ((properties & PROPERTY_PERSPECTIVE) != 0)
            return lookAtPerspective(eyeX, eyeY, eyeZ, centerX, centerY, centerZ, upX, upY, upZ, dest);
        return lookAtGeneric(eyeX, eyeY, eyeZ, centerX, centerY, centerZ, upX, upY, upZ, dest);
    }

    private Matrix4dc lookAtGeneric(double eyeX, double eyeY, double eyeZ,
                                    double centerX, double centerY, double centerZ,
                                    double upX, double upY, double upZ, Matrix4dc dest) {
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
        dest.m33(m03 * rm30 + m13 * rm31 + m23 * rm32 + m33);
        // introduce temporaries for dependent results
        double nm00 = m00 * rm00 + m10 * rm01 + m20 * rm02;
        double nm01 = m01 * rm00 + m11 * rm01 + m21 * rm02;
        double nm02 = m02 * rm00 + m12 * rm01 + m22 * rm02;
        double nm03 = m03 * rm00 + m13 * rm01 + m23 * rm02;
        double nm10 = m00 * rm10 + m10 * rm11 + m20 * rm12;
        double nm11 = m01 * rm10 + m11 * rm11 + m21 * rm12;
        double nm12 = m02 * rm10 + m12 * rm11 + m22 * rm12;
        double nm13 = m03 * rm10 + m13 * rm11 + m23 * rm12;
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
        dest.property(properties & ~(PROPERTY_PERSPECTIVE | PROPERTY_IDENTITY | PROPERTY_TRANSLATION));

        return dest;
    }

    public Matrix4dc lookAt(double eyeX, double eyeY, double eyeZ,
                            double centerX, double centerY, double centerZ,
                            double upX, double upY, double upZ) {
        return lookAt(eyeX, eyeY, eyeZ, centerX, centerY, centerZ, upX, upY, upZ, this);
    }

    public Matrix4dc lookAtPerspective(double eyeX, double eyeY, double eyeZ,
                                       double centerX, double centerY, double centerZ,
                                       double upX, double upY, double upZ, Matrix4dc dest) {
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

        double nm00 = m00 * rm00;
        double nm01 = m11 * rm01;
        double nm02 = m22 * rm02;
        double nm03 = m23 * rm02;
        double nm10 = m00 * rm10;
        double nm11 = m11 * rm11;
        double nm12 = m22 * rm12;
        double nm13 = m23 * rm12;
        double nm20 = m00 * rm20;
        double nm21 = m11 * rm21;
        double nm22 = m22 * rm22;
        double nm23 = m23 * rm22;
        double nm30 = m00 * rm30;
        double nm31 = m11 * rm31;
        double nm32 = m22 * rm32 + m32;
        double nm33 = m23 * rm32;
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
        dest.property(0);

        return dest;
    }

    public Matrix4dc setLookAtLH(IVector3d eye, IVector3d center, IVector3d up) {
        return setLookAtLH(eye.x(), eye.y(), eye.z(), center.x(), center.y(), center.z(), up.x(), up.y(), up.z());
    }

    public Matrix4dc setLookAtLH(double eyeX, double eyeY, double eyeZ,
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
        m03 = 0.0;
        m10 = leftY;
        m11 = upnY;
        m12 = dirY;
        m13 = 0.0;
        m20 = leftZ;
        m21 = upnZ;
        m22 = dirZ;
        m23 = 0.0;
        m30 = -(leftX * eyeX + leftY * eyeY + leftZ * eyeZ);
        m31 = -(upnX * eyeX + upnY * eyeY + upnZ * eyeZ);
        m32 = -(dirX * eyeX + dirY * eyeY + dirZ * eyeZ);
        m33 = 1.0;
        properties = PROPERTY_AFFINE;

        return this;
    }

    public Matrix4dc lookAtLH(IVector3d eye, IVector3d center, IVector3d up, Matrix4dc dest) {
        return lookAtLH(eye.x(), eye.y(), eye.z(), center.x(), center.y(), center.z(), up.x(), up.y(), up.z(), dest);
    }

    public Matrix4dc lookAtLH(IVector3d eye, IVector3d center, IVector3d up) {
        return lookAtLH(eye.x(), eye.y(), eye.z(), center.x(), center.y(), center.z(), up.x(), up.y(), up.z(), this);
    }

    public Matrix4dc lookAtLH(double eyeX, double eyeY, double eyeZ,
                              double centerX, double centerY, double centerZ,
                              double upX, double upY, double upZ, Matrix4dc dest) {
        if ((properties & PROPERTY_IDENTITY) != 0)
            return dest.setLookAtLH(eyeX, eyeY, eyeZ, centerX, centerY, centerZ, upX, upY, upZ);
        else if ((properties & PROPERTY_PERSPECTIVE) != 0)
            return lookAtPerspectiveLH(eyeX, eyeY, eyeZ, centerX, centerY, centerZ, upX, upY, upZ, dest);
        return lookAtLHGeneric(eyeX, eyeY, eyeZ, centerX, centerY, centerZ, upX, upY, upZ, dest);
    }

    private Matrix4dc lookAtLHGeneric(double eyeX, double eyeY, double eyeZ,
                                      double centerX, double centerY, double centerZ,
                                      double upX, double upY, double upZ, Matrix4dc dest) {
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
        dest.m33(m03 * rm30 + m13 * rm31 + m23 * rm32 + m33);
        // introduce temporaries for dependent results
        double nm00 = m00 * rm00 + m10 * rm01 + m20 * rm02;
        double nm01 = m01 * rm00 + m11 * rm01 + m21 * rm02;
        double nm02 = m02 * rm00 + m12 * rm01 + m22 * rm02;
        double nm03 = m03 * rm00 + m13 * rm01 + m23 * rm02;
        double nm10 = m00 * rm10 + m10 * rm11 + m20 * rm12;
        double nm11 = m01 * rm10 + m11 * rm11 + m21 * rm12;
        double nm12 = m02 * rm10 + m12 * rm11 + m22 * rm12;
        double nm13 = m03 * rm10 + m13 * rm11 + m23 * rm12;
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
        dest.property(properties & ~(PROPERTY_PERSPECTIVE | PROPERTY_IDENTITY | PROPERTY_TRANSLATION));

        return dest;
    }

    public Matrix4dc lookAtLH(double eyeX, double eyeY, double eyeZ,
                              double centerX, double centerY, double centerZ,
                              double upX, double upY, double upZ) {
        return lookAtLH(eyeX, eyeY, eyeZ, centerX, centerY, centerZ, upX, upY, upZ, this);
    }

    public Matrix4dc lookAtPerspectiveLH(double eyeX, double eyeY, double eyeZ,
                                         double centerX, double centerY, double centerZ,
                                         double upX, double upY, double upZ, Matrix4dc dest) {
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

        double nm00 = m00 * rm00;
        double nm01 = m11 * rm01;
        double nm02 = m22 * rm02;
        double nm03 = m23 * rm02;
        double nm10 = m00 * rm10;
        double nm11 = m11 * rm11;
        double nm12 = m22 * rm12;
        double nm13 = m23 * rm12;
        double nm20 = m00 * rm20;
        double nm21 = m11 * rm21;
        double nm22 = m22 * rm22;
        double nm23 = m23 * rm22;
        double nm30 = m00 * rm30;
        double nm31 = m11 * rm31;
        double nm32 = m22 * rm32 + m32;
        double nm33 = m23 * rm32;
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
        dest.property(0);

        return dest;
    }

    public Matrix4dc perspective(double fovy, double aspect, double zNear, double zFar, boolean zZeroToOne, Matrix4dc dest) {
        if ((properties & PROPERTY_IDENTITY) != 0)
            return dest.setPerspective(fovy, aspect, zNear, zFar, zZeroToOne);
        return perspectiveGeneric(fovy, aspect, zNear, zFar, zZeroToOne, dest);
    }

    private Matrix4dc perspectiveGeneric(double fovy, double aspect, double zNear, double zFar, boolean zZeroToOne, Matrix4dc dest) {
        double h = Math.tan(fovy * 0.5);
        // calculate right matrix elements
        double rm00 = 1.0 / (h * aspect);
        double rm11 = 1.0 / h;
        double rm22;
        double rm32;
        boolean farInf = zFar > 0 && Double.isInfinite(zFar);
        boolean nearInf = zNear > 0 && Double.isInfinite(zNear);
        if (farInf) {
            // See: "Infinite Projection Matrix" (http://www.terathon.com/gdc07_lengyel.pdf)
            double e = 1E-6;
            rm22 = e - 1.0;
            rm32 = (e - (zZeroToOne ? 1.0 : 2.0)) * zNear;
        } else if (nearInf) {
            double e = 1E-6;
            rm22 = (zZeroToOne ? 0.0 : 1.0) - e;
            rm32 = ((zZeroToOne ? 1.0 : 2.0) - e) * zFar;
        } else {
            rm22 = (zZeroToOne ? zFar : zFar + zNear) / (zNear - zFar);
            rm32 = (zZeroToOne ? zFar : zFar + zFar) * zNear / (zNear - zFar);
        }
        // perform optimized matrix multiplication
        double nm20 = m20 * rm22 - m30;
        double nm21 = m21 * rm22 - m31;
        double nm22 = m22 * rm22 - m32;
        double nm23 = m23 * rm22 - m33;
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
        dest.property(properties & ~(PROPERTY_AFFINE | PROPERTY_IDENTITY | PROPERTY_TRANSLATION));

        return dest;
    }

    public Matrix4dc perspective(double fovy, double aspect, double zNear, double zFar, Matrix4dc dest) {
        return perspective(fovy, aspect, zNear, zFar, false, dest);
    }

    public Matrix4dc perspective(double fovy, double aspect, double zNear, double zFar, boolean zZeroToOne) {
        return perspective(fovy, aspect, zNear, zFar, zZeroToOne, this);
    }

    public Matrix4dc perspective(double fovy, double aspect, double zNear, double zFar) {
        return perspective(fovy, aspect, zNear, zFar, this);
    }

    public Matrix4dc setPerspective(double fovy, double aspect, double zNear, double zFar, boolean zZeroToOne) {
        double h = Math.tan(fovy * 0.5);
        m00 = 1.0 / (h * aspect);
        m01 = 0.0;
        m02 = 0.0;
        m03 = 0.0;
        m10 = 0.0;
        m11 = 1.0 / h;
        m12 = 0.0;
        m13 = 0.0;
        m20 = 0.0;
        m21 = 0.0;
        boolean farInf = zFar > 0 && Double.isInfinite(zFar);
        boolean nearInf = zNear > 0 && Double.isInfinite(zNear);
        if (farInf) {
            // See: "Infinite Projection Matrix" (http://www.terathon.com/gdc07_lengyel.pdf)
            double e = 1E-6;
            m22 = e - 1.0;
            m32 = (e - (zZeroToOne ? 1.0 : 2.0)) * zNear;
        } else if (nearInf) {
            double e = 1E-6;
            m22 = (zZeroToOne ? 0.0 : 1.0) - e;
            m32 = ((zZeroToOne ? 1.0 : 2.0) - e) * zFar;
        } else {
            m22 = (zZeroToOne ? zFar : zFar + zNear) / (zNear - zFar);
            m32 = (zZeroToOne ? zFar : zFar + zFar) * zNear / (zNear - zFar);
        }
        m23 = -1.0;
        m30 = 0.0;
        m31 = 0.0;
        m33 = 0.0;
        properties = PROPERTY_PERSPECTIVE;
        return this;
    }

    public Matrix4dc setPerspective(double fovy, double aspect, double zNear, double zFar) {
        return setPerspective(fovy, aspect, zNear, zFar, false);
    }

    public Matrix4dc perspectiveLH(double fovy, double aspect, double zNear, double zFar, boolean zZeroToOne, Matrix4dc dest) {
        if ((properties & PROPERTY_IDENTITY) != 0)
            return dest.setPerspectiveLH(fovy, aspect, zNear, zFar, zZeroToOne);
        return perspectiveLHGeneric(fovy, aspect, zNear, zFar, zZeroToOne, dest);
    }

    private Matrix4dc perspectiveLHGeneric(double fovy, double aspect, double zNear, double zFar, boolean zZeroToOne, Matrix4dc dest) {
        double h = Math.tan(fovy * 0.5);
        // calculate right matrix elements
        double rm00 = 1.0 / (h * aspect);
        double rm11 = 1.0 / h;
        double rm22;
        double rm32;
        boolean farInf = zFar > 0 && Double.isInfinite(zFar);
        boolean nearInf = zNear > 0 && Double.isInfinite(zNear);
        if (farInf) {
            // See: "Infinite Projection Matrix" (http://www.terathon.com/gdc07_lengyel.pdf)
            double e = 1E-6;
            rm22 = 1.0 - e;
            rm32 = (e - (zZeroToOne ? 1.0 : 2.0)) * zNear;
        } else if (nearInf) {
            double e = 1E-6;
            rm22 = (zZeroToOne ? 0.0 : 1.0) - e;
            rm32 = ((zZeroToOne ? 1.0 : 2.0) - e) * zFar;
        } else {
            rm22 = (zZeroToOne ? zFar : zFar + zNear) / (zFar - zNear);
            rm32 = (zZeroToOne ? zFar : zFar + zFar) * zNear / (zNear - zFar);
        }
        // perform optimized matrix multiplication
        double nm20 = m20 * rm22 + m30;
        double nm21 = m21 * rm22 + m31;
        double nm22 = m22 * rm22 + m32;
        double nm23 = m23 * rm22 + m33;
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
        dest.property(properties & ~(PROPERTY_AFFINE | PROPERTY_IDENTITY | PROPERTY_TRANSLATION));
        return dest;
    }

    public Matrix4dc perspectiveLH(double fovy, double aspect, double zNear, double zFar, boolean zZeroToOne) {
        return perspectiveLH(fovy, aspect, zNear, zFar, zZeroToOne, this);
    }

    public Matrix4dc perspectiveLH(double fovy, double aspect, double zNear, double zFar, Matrix4dc dest) {
        return perspectiveLH(fovy, aspect, zNear, zFar, false, dest);
    }

    public Matrix4dc perspectiveLH(double fovy, double aspect, double zNear, double zFar) {
        return perspectiveLH(fovy, aspect, zNear, zFar, this);
    }

    public Matrix4dc setPerspectiveLH(double fovy, double aspect, double zNear, double zFar, boolean zZeroToOne) {
        double h = Math.tan(fovy * 0.5);
        m00 = 1.0 / (h * aspect);
        m01 = 0.0;
        m02 = 0.0;
        m03 = 0.0;
        m10 = 0.0;
        m11 = 1.0 / h;
        m12 = 0.0;
        m13 = 0.0;
        m20 = 0.0;
        m21 = 0.0;
        boolean farInf = zFar > 0 && Double.isInfinite(zFar);
        boolean nearInf = zNear > 0 && Double.isInfinite(zNear);
        if (farInf) {
            // See: "Infinite Projection Matrix" (http://www.terathon.com/gdc07_lengyel.pdf)
            double e = 1E-6;
            m22 = 1.0 - e;
            m32 = (e - (zZeroToOne ? 1.0 : 2.0)) * zNear;
        } else if (nearInf) {
            double e = 1E-6;
            m22 = (zZeroToOne ? 0.0 : 1.0) - e;
            m32 = ((zZeroToOne ? 1.0 : 2.0) - e) * zFar;
        } else {
            m22 = (zZeroToOne ? zFar : zFar + zNear) / (zFar - zNear);
            m32 = (zZeroToOne ? zFar : zFar + zFar) * zNear / (zNear - zFar);
        }
        m23 = 1.0;
        m30 = 0.0;
        m31 = 0.0;
        m33 = 0.0;
        properties = PROPERTY_PERSPECTIVE;
        return this;
    }

    public Matrix4dc setPerspectiveLH(double fovy, double aspect, double zNear, double zFar) {
        return setPerspectiveLH(fovy, aspect, zNear, zFar, false);
    }

    public Matrix4dc frustum(double left, double right, double bottom, double top, double zNear, double zFar, boolean zZeroToOne, Matrix4dc dest) {
        // calculate right matrix elements
        double rm00 = (zNear + zNear) / (right - left);
        double rm11 = (zNear + zNear) / (top - bottom);
        double rm20 = (right + left) / (right - left);
        double rm21 = (top + bottom) / (top - bottom);
        double rm22;
        double rm32;
        boolean farInf = zFar > 0 && Double.isInfinite(zFar);
        boolean nearInf = zNear > 0 && Double.isInfinite(zNear);
        if (farInf) {
            // See: "Infinite Projection Matrix" (http://www.terathon.com/gdc07_lengyel.pdf)
            double e = 1E-6;
            rm22 = e - 1.0;
            rm32 = (e - (zZeroToOne ? 1.0 : 2.0)) * zNear;
        } else if (nearInf) {
            double e = 1E-6;
            rm22 = (zZeroToOne ? 0.0 : 1.0) - e;
            rm32 = ((zZeroToOne ? 1.0 : 2.0) - e) * zFar;
        } else {
            rm22 = (zZeroToOne ? zFar : zFar + zNear) / (zNear - zFar);
            rm32 = (zZeroToOne ? zFar : zFar + zFar) * zNear / (zNear - zFar);
        }
        // perform optimized matrix multiplication
        double nm20 = m00 * rm20 + m10 * rm21 + m20 * rm22 - m30;
        double nm21 = m01 * rm20 + m11 * rm21 + m21 * rm22 - m31;
        double nm22 = m02 * rm20 + m12 * rm21 + m22 * rm22 - m32;
        double nm23 = m03 * rm20 + m13 * rm21 + m23 * rm22 - m33;
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
        dest.property(0);

        return dest;
    }

    public Matrix4dc frustum(double left, double right, double bottom, double top, double zNear, double zFar, Matrix4dc dest) {
        return frustum(left, right, bottom, top, zNear, zFar, false, dest);
    }

    public Matrix4dc frustum(double left, double right, double bottom, double top, double zNear, double zFar, boolean zZeroToOne) {
        return frustum(left, right, bottom, top, zNear, zFar, zZeroToOne, this);
    }

    public Matrix4dc frustum(double left, double right, double bottom, double top, double zNear, double zFar) {
        return frustum(left, right, bottom, top, zNear, zFar, this);
    }

    public Matrix4dc setFrustum(double left, double right, double bottom, double top, double zNear, double zFar, boolean zZeroToOne) {
        m00 = (zNear + zNear) / (right - left);
        m01 = 0.0;
        m02 = 0.0;
        m03 = 0.0;
        m10 = 0.0;
        m11 = (zNear + zNear) / (top - bottom);
        m12 = 0.0;
        m13 = 0.0;
        m20 = (right + left) / (right - left);
        m21 = (top + bottom) / (top - bottom);
        boolean farInf = zFar > 0 && Double.isInfinite(zFar);
        boolean nearInf = zNear > 0 && Double.isInfinite(zNear);
        if (farInf) {
            // See: "Infinite Projection Matrix" (http://www.terathon.com/gdc07_lengyel.pdf)
            double e = 1E-6;
            m22 = e - 1.0;
            m32 = (e - (zZeroToOne ? 1.0 : 2.0)) * zNear;
        } else if (nearInf) {
            double e = 1E-6;
            m22 = (zZeroToOne ? 0.0 : 1.0) - e;
            m32 = ((zZeroToOne ? 1.0 : 2.0) - e) * zFar;
        } else {
            m22 = (zZeroToOne ? zFar : zFar + zNear) / (zNear - zFar);
            m32 = (zZeroToOne ? zFar : zFar + zFar) * zNear / (zNear - zFar);
        }
        m23 = -1.0;
        m30 = 0.0;
        m31 = 0.0;
        m33 = 0.0;
        properties = 0;
        return this;
    }

    public Matrix4dc setFrustum(double left, double right, double bottom, double top, double zNear, double zFar) {
        return setFrustum(left, right, bottom, top, zNear, zFar, false);
    }

    public Matrix4dc frustumLH(double left, double right, double bottom, double top, double zNear, double zFar, boolean zZeroToOne, Matrix4dc dest) {
        // calculate right matrix elements
        double rm00 = (zNear + zNear) / (right - left);
        double rm11 = (zNear + zNear) / (top - bottom);
        double rm20 = (right + left) / (right - left);
        double rm21 = (top + bottom) / (top - bottom);
        double rm22;
        double rm32;
        boolean farInf = zFar > 0 && Double.isInfinite(zFar);
        boolean nearInf = zNear > 0 && Double.isInfinite(zNear);
        if (farInf) {
            // See: "Infinite Projection Matrix" (http://www.terathon.com/gdc07_lengyel.pdf)
            double e = 1E-6;
            rm22 = 1.0 - e;
            rm32 = (e - (zZeroToOne ? 1.0 : 2.0)) * zNear;
        } else if (nearInf) {
            double e = 1E-6;
            rm22 = (zZeroToOne ? 0.0 : 1.0) - e;
            rm32 = ((zZeroToOne ? 1.0 : 2.0) - e) * zFar;
        } else {
            rm22 = (zZeroToOne ? zFar : zFar + zNear) / (zFar - zNear);
            rm32 = (zZeroToOne ? zFar : zFar + zFar) * zNear / (zNear - zFar);
        }
        // perform optimized matrix multiplication
        double nm20 = m00 * rm20 + m10 * rm21 + m20 * rm22 + m30;
        double nm21 = m01 * rm20 + m11 * rm21 + m21 * rm22 + m31;
        double nm22 = m02 * rm20 + m12 * rm21 + m22 * rm22 + m32;
        double nm23 = m03 * rm20 + m13 * rm21 + m23 * rm22 + m33;
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
        dest.property(0);
        return dest;
    }

    public Matrix4dc frustumLH(double left, double right, double bottom, double top, double zNear, double zFar, boolean zZeroToOne) {
        return frustumLH(left, right, bottom, top, zNear, zFar, zZeroToOne, this);
    }

    public Matrix4dc frustumLH(double left, double right, double bottom, double top, double zNear, double zFar, Matrix4dc dest) {
        return frustumLH(left, right, bottom, top, zNear, zFar, false, dest);
    }

    public Matrix4dc frustumLH(double left, double right, double bottom, double top, double zNear, double zFar) {
        return frustumLH(left, right, bottom, top, zNear, zFar, this);
    }

    public Matrix4dc setFrustumLH(double left, double right, double bottom, double top, double zNear, double zFar, boolean zZeroToOne) {
        m00 = (zNear + zNear) / (right - left);
        m01 = 0.0;
        m02 = 0.0;
        m03 = 0.0;
        m10 = 0.0;
        m11 = (zNear + zNear) / (top - bottom);
        m12 = 0.0;
        m13 = 0.0;
        m20 = (right + left) / (right - left);
        m21 = (top + bottom) / (top - bottom);
        boolean farInf = zFar > 0 && Double.isInfinite(zFar);
        boolean nearInf = zNear > 0 && Double.isInfinite(zNear);
        if (farInf) {
            // See: "Infinite Projection Matrix" (http://www.terathon.com/gdc07_lengyel.pdf)
            double e = 1E-6;
            m22 = 1.0 - e;
            m32 = (e - (zZeroToOne ? 1.0 : 2.0)) * zNear;
        } else if (nearInf) {
            double e = 1E-6;
            m22 = (zZeroToOne ? 0.0 : 1.0) - e;
            m32 = ((zZeroToOne ? 1.0 : 2.0) - e) * zFar;
        } else {
            m22 = (zZeroToOne ? zFar : zFar + zNear) / (zFar - zNear);
            m32 = (zZeroToOne ? zFar : zFar + zFar) * zNear / (zNear - zFar);
        }
        m23 = 1.0;
        m30 = 0.0;
        m31 = 0.0;
        m33 = 0.0;
        properties = 0;
        return this;
    }

    public Matrix4dc setFrustumLH(double left, double right, double bottom, double top, double zNear, double zFar) {
        return setFrustumLH(left, right, bottom, top, zNear, zFar, false);
    }

    public Matrix4dc setFromIntrinsic(double alphaX, double alphaY, double gamma, double u0, double v0, int imgWidth, int imgHeight, double near, double far) {
        double l00 = 2.0 / imgWidth;
        double l11 = 2.0 / imgHeight;
        double l22 = 2.0 / (near - far);
        this.m00 = l00 * alphaX;
        this.m01 = 0.0;
        this.m02 = 0.0;
        this.m03 = 0.0;
        this.m10 = l00 * gamma;
        this.m11 = l11 * alphaY;
        this.m12 = 0.0;
        this.m13 = 0.0;
        this.m20 = l00 * u0 - 1.0;
        this.m21 = l11 * v0 - 1.0;
        this.m22 = l22 * -(near + far) + (far + near) / (near - far);
        this.m23 = -1.0;
        this.m30 = 0.0;
        this.m31 = 0.0;
        this.m32 = l22 * -near * far;
        this.m33 = 0.0;
        this.properties = PROPERTY_PERSPECTIVE;
        return this;
    }

    public Vector4dc frustumPlane(int plane, Vector4dc dest) {
        switch (plane) {
            case PLANE_NX:
                dest.set(m03 + m00, m13 + m10, m23 + m20, m33 + m30).normalize3();
                break;
            case PLANE_PX:
                dest.set(m03 - m00, m13 - m10, m23 - m20, m33 - m30).normalize3();
                break;
            case PLANE_NY:
                dest.set(m03 + m01, m13 + m11, m23 + m21, m33 + m31).normalize3();
                break;
            case PLANE_PY:
                dest.set(m03 - m01, m13 - m11, m23 - m21, m33 - m31).normalize3();
                break;
            case PLANE_NZ:
                dest.set(m03 + m02, m13 + m12, m23 + m22, m33 + m32).normalize3();
                break;
            case PLANE_PZ:
                dest.set(m03 - m02, m13 - m12, m23 - m22, m33 - m32).normalize3();
                break;
            default:
                throw new IllegalArgumentException("plane"); //$NON-NLS-1$
        }
        return dest;
    }

    public Planedc frustumPlane(int which, Planedc plane) {
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

    public Vector3dc frustumCorner(int corner, Vector3dc dest) {
        double d1, d2, d3;
        double n1x, n1y, n1z, n2x, n2y, n2z, n3x, n3y, n3z;
        switch (corner) {
            case CORNER_NXNYNZ: // left, bottom, near
                n1x = m03 + m00;
                n1y = m13 + m10;
                n1z = m23 + m20;
                d1 = m33 + m30; // left
                n2x = m03 + m01;
                n2y = m13 + m11;
                n2z = m23 + m21;
                d2 = m33 + m31; // bottom
                n3x = m03 + m02;
                n3y = m13 + m12;
                n3z = m23 + m22;
                d3 = m33 + m32; // near
                break;
            case CORNER_PXNYNZ: // right, bottom, near
                n1x = m03 - m00;
                n1y = m13 - m10;
                n1z = m23 - m20;
                d1 = m33 - m30; // right
                n2x = m03 + m01;
                n2y = m13 + m11;
                n2z = m23 + m21;
                d2 = m33 + m31; // bottom
                n3x = m03 + m02;
                n3y = m13 + m12;
                n3z = m23 + m22;
                d3 = m33 + m32; // near
                break;
            case CORNER_PXPYNZ: // right, top, near
                n1x = m03 - m00;
                n1y = m13 - m10;
                n1z = m23 - m20;
                d1 = m33 - m30; // right
                n2x = m03 - m01;
                n2y = m13 - m11;
                n2z = m23 - m21;
                d2 = m33 - m31; // top
                n3x = m03 + m02;
                n3y = m13 + m12;
                n3z = m23 + m22;
                d3 = m33 + m32; // near
                break;
            case CORNER_NXPYNZ: // left, top, near
                n1x = m03 + m00;
                n1y = m13 + m10;
                n1z = m23 + m20;
                d1 = m33 + m30; // left
                n2x = m03 - m01;
                n2y = m13 - m11;
                n2z = m23 - m21;
                d2 = m33 - m31; // top
                n3x = m03 + m02;
                n3y = m13 + m12;
                n3z = m23 + m22;
                d3 = m33 + m32; // near
                break;
            case CORNER_PXNYPZ: // right, bottom, far
                n1x = m03 - m00;
                n1y = m13 - m10;
                n1z = m23 - m20;
                d1 = m33 - m30; // right
                n2x = m03 + m01;
                n2y = m13 + m11;
                n2z = m23 + m21;
                d2 = m33 + m31; // bottom
                n3x = m03 - m02;
                n3y = m13 - m12;
                n3z = m23 - m22;
                d3 = m33 - m32; // far
                break;
            case CORNER_NXNYPZ: // left, bottom, far
                n1x = m03 + m00;
                n1y = m13 + m10;
                n1z = m23 + m20;
                d1 = m33 + m30; // left
                n2x = m03 + m01;
                n2y = m13 + m11;
                n2z = m23 + m21;
                d2 = m33 + m31; // bottom
                n3x = m03 - m02;
                n3y = m13 - m12;
                n3z = m23 - m22;
                d3 = m33 - m32; // far
                break;
            case CORNER_NXPYPZ: // left, top, far
                n1x = m03 + m00;
                n1y = m13 + m10;
                n1z = m23 + m20;
                d1 = m33 + m30; // left
                n2x = m03 - m01;
                n2y = m13 - m11;
                n2z = m23 - m21;
                d2 = m33 - m31; // top
                n3x = m03 - m02;
                n3y = m13 - m12;
                n3z = m23 - m22;
                d3 = m33 - m32; // far
                break;
            case CORNER_PXPYPZ: // right, top, far
                n1x = m03 - m00;
                n1y = m13 - m10;
                n1z = m23 - m20;
                d1 = m33 - m30; // right
                n2x = m03 - m01;
                n2y = m13 - m11;
                n2z = m23 - m21;
                d2 = m33 - m31; // top
                n3x = m03 - m02;
                n3y = m13 - m12;
                n3z = m23 - m22;
                d3 = m33 - m32; // far
                break;
            default:
                throw new IllegalArgumentException("corner"); //$NON-NLS-1$
        }
        double c23x, c23y, c23z;
        c23x = n2y * n3z - n2z * n3y;
        c23y = n2z * n3x - n2x * n3z;
        c23z = n2x * n3y - n2y * n3x;
        double c31x, c31y, c31z;
        c31x = n3y * n1z - n3z * n1y;
        c31y = n3z * n1x - n3x * n1z;
        c31z = n3x * n1y - n3y * n1x;
        double c12x, c12y, c12z;
        c12x = n1y * n2z - n1z * n2y;
        c12y = n1z * n2x - n1x * n2z;
        c12z = n1x * n2y - n1y * n2x;
        double invDot = 1.0 / (n1x * c23x + n1y * c23y + n1z * c23z);
        dest.set((-c23x * d1 - c31x * d2 - c12x * d3) * invDot, (-c23y * d1 - c31y * d2 - c12y * d3) * invDot, (-c23z * d1 - c31z * d2 - c12z * d3) * invDot);
        return dest;
    }

    public Vector3dc perspectiveOrigin(Vector3dc dest) {
        /*
         * Simply compute the intersection point of the left, right and top frustum plane.
         */
        double d1, d2, d3;
        double n1x, n1y, n1z, n2x, n2y, n2z, n3x, n3y, n3z;
        n1x = m03 + m00;
        n1y = m13 + m10;
        n1z = m23 + m20;
        d1 = m33 + m30; // left
        n2x = m03 - m00;
        n2y = m13 - m10;
        n2z = m23 - m20;
        d2 = m33 - m30; // right
        n3x = m03 - m01;
        n3y = m13 - m11;
        n3z = m23 - m21;
        d3 = m33 - m31; // top
        double c23x, c23y, c23z;
        c23x = n2y * n3z - n2z * n3y;
        c23y = n2z * n3x - n2x * n3z;
        c23z = n2x * n3y - n2y * n3x;
        double c31x, c31y, c31z;
        c31x = n3y * n1z - n3z * n1y;
        c31y = n3z * n1x - n3x * n1z;
        c31z = n3x * n1y - n3y * n1x;
        double c12x, c12y, c12z;
        c12x = n1y * n2z - n1z * n2y;
        c12y = n1z * n2x - n1x * n2z;
        c12z = n1x * n2y - n1y * n2x;
        double invDot = 1.0 / (n1x * c23x + n1y * c23y + n1z * c23z);
        dest.set((-c23x * d1 - c31x * d2 - c12x * d3) * invDot, (-c23y * d1 - c31y * d2 - c12y * d3) * invDot, (-c23z * d1 - c31z * d2 - c12z * d3) * invDot);
        return dest;
    }

    public double perspectiveFov() {
        /*
         * Compute the angle between the bottom and top frustum plane normals.
         */
        double n1x, n1y, n1z, n2x, n2y, n2z;
        n1x = m03 + m01;
        n1y = m13 + m11;
        n1z = m23 + m21; // bottom
        n2x = m01 - m03;
        n2y = m11 - m13;
        n2z = m21 - m23; // top
        double n1len = Math.sqrt(n1x * n1x + n1y * n1y + n1z * n1z);
        double n2len = Math.sqrt(n2x * n2x + n2y * n2y + n2z * n2z);
        return Math.acos((n1x * n2x + n1y * n2y + n1z * n2z) / (n1len * n2len));
    }

    public double perspectiveNear() {
        return m32 / (m23 + m22);
    }

    public double perspectiveFar() {
        return m32 / (m22 - m23);
    }

    public Vector3dc frustumRayDir(double x, double y, Vector3dc dest) {
        /*
         * This method works by first obtaining the frustum plane normals,
         * then building the cross product to obtain the corner rays,
         * and finally bilinearly interpolating to obtain the desired direction.
         * The code below uses a condense form of doing all this making use
         * of some mathematical identities to simplify the overall expression.
         */
        double a = m10 * m23, b = m13 * m21, c = m10 * m21, d = m11 * m23, e = m13 * m20, f = m11 * m20;
        double g = m03 * m20, h = m01 * m23, i = m01 * m20, j = m03 * m21, k = m00 * m23, l = m00 * m21;
        double m = m00 * m13, n = m03 * m11, o = m00 * m11, p = m01 * m13, q = m03 * m10, r = m01 * m10;
        double m1x, m1y, m1z;
        m1x = (d + e + f - a - b - c) * (1.0 - y) + (a - b - c + d - e + f) * y;
        m1y = (j + k + l - g - h - i) * (1.0 - y) + (g - h - i + j - k + l) * y;
        m1z = (p + q + r - m - n - o) * (1.0 - y) + (m - n - o + p - q + r) * y;
        double m2x, m2y, m2z;
        m2x = (b - c - d + e + f - a) * (1.0 - y) + (a + b - c - d - e + f) * y;
        m2y = (h - i - j + k + l - g) * (1.0 - y) + (g + h - i - j - k + l) * y;
        m2z = (n - o - p + q + r - m) * (1.0 - y) + (m + n - o - p - q + r) * y;
        dest.set(m1x * (1.0 - x) + m2x * x, m1y * (1.0 - x) + m2y * x, m1z * (1.0 - x) + m2z * x);
        dest.normalize();
        return dest;
    }

    public Vector3dc positiveZ(Vector3dc dest) {
        dest.set(m10 * m21 - m11 * m20, m20 * m01 - m21 * m00, m00 * m11 - m01 * m10);
        dest.normalize();
        return dest;
    }

    public Vector3dc normalizedPositiveZ(Vector3dc dest) {
        dest.set(m02, m12, m22);
        return dest;
    }

    public Vector3dc positiveX(Vector3dc dest) {
        dest.set(m11 * m22 - m12 * m21, m02 * m21 - m01 * m22, m01 * m12 - m02 * m11);
        dest.normalize();
        return dest;
    }

    public Vector3dc normalizedPositiveX(Vector3dc dest) {
        dest.set(m00, m10, m20);
        return dest;
    }

    public Vector3dc positiveY(Vector3dc dest) {
        dest.set(m12 * m20 - m10 * m22, m00 * m22 - m02 * m20, m02 * m10 - m00 * m12);
        dest.normalize();
        return dest;
    }

    public Vector3dc normalizedPositiveY(Vector3dc dest) {
        dest.set(m01, m11, m21);
        return dest;
    }

    public Vector3dc originAffine(Vector3dc dest) {
        double a = m00 * m11 - m01 * m10;
        double b = m00 * m12 - m02 * m10;
        double d = m01 * m12 - m02 * m11;
        double g = m20 * m31 - m21 * m30;
        double h = m20 * m32 - m22 * m30;
        double j = m21 * m32 - m22 * m31;
        dest.set(-m10 * j + m11 * h - m12 * g, m00 * j - m01 * h + m02 * g, -m30 * d + m31 * b - m32 * a);
        return dest;
    }

    public Vector3dc origin(Vector3dc dest) {
        double a = m00 * m11 - m01 * m10;
        double b = m00 * m12 - m02 * m10;
        double c = m00 * m13 - m03 * m10;
        double d = m01 * m12 - m02 * m11;
        double e = m01 * m13 - m03 * m11;
        double f = m02 * m13 - m03 * m12;
        double g = m20 * m31 - m21 * m30;
        double h = m20 * m32 - m22 * m30;
        double i = m20 * m33 - m23 * m30;
        double j = m21 * m32 - m22 * m31;
        double k = m21 * m33 - m23 * m31;
        double l = m22 * m33 - m23 * m32;
        double det = a * l - b * k + c * j + d * i - e * h + f * g;
        double invDet = 1.0 / det;
        double nm30 = (-m10 * j + m11 * h - m12 * g) * invDet;
        double nm31 = (m00 * j - m01 * h + m02 * g) * invDet;
        double nm32 = (-m30 * d + m31 * b - m32 * a) * invDet;
        double nm33 = det / (m20 * d - m21 * b + m22 * a);
        double x = nm30 * nm33;
        double y = nm31 * nm33;
        double z = nm32 * nm33;
        return dest.set(x, y, z);
    }

    public Matrix4dc shadow(IVector4d light, double a, double b, double c, double d) {
        return shadow(light.x(), light.y(), light.z(), light.w(), a, b, c, d, this);
    }

    public Matrix4dc shadow(IVector4d light, double a, double b, double c, double d, Matrix4dc dest) {
        return shadow(light.x(), light.y(), light.z(), light.w(), a, b, c, d, dest);
    }

    public Matrix4dc shadow(double lightX, double lightY, double lightZ, double lightW, double a, double b, double c, double d) {
        return shadow(lightX, lightY, lightZ, lightW, a, b, c, d, this);
    }

    public Matrix4dc shadow(double lightX, double lightY, double lightZ, double lightW, double a, double b, double c, double d, Matrix4dc dest) {
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
        double nm03 = m03 * rm00 + m13 * rm01 + m23 * rm02 + m33 * rm03;
        double nm10 = m00 * rm10 + m10 * rm11 + m20 * rm12 + m30 * rm13;
        double nm11 = m01 * rm10 + m11 * rm11 + m21 * rm12 + m31 * rm13;
        double nm12 = m02 * rm10 + m12 * rm11 + m22 * rm12 + m32 * rm13;
        double nm13 = m03 * rm10 + m13 * rm11 + m23 * rm12 + m33 * rm13;
        double nm20 = m00 * rm20 + m10 * rm21 + m20 * rm22 + m30 * rm23;
        double nm21 = m01 * rm20 + m11 * rm21 + m21 * rm22 + m31 * rm23;
        double nm22 = m02 * rm20 + m12 * rm21 + m22 * rm22 + m32 * rm23;
        double nm23 = m03 * rm20 + m13 * rm21 + m23 * rm22 + m33 * rm23;
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
        dest.property(properties & ~(PROPERTY_PERSPECTIVE | PROPERTY_IDENTITY | PROPERTY_TRANSLATION));

        return dest;
    }

    public Matrix4dc shadow(IVector4d light, IMatrix4d planeTransform, Matrix4dc dest) {
        // compute plane equation by transforming (y = 0)
        double a = planeTransform.m10();
        double b = planeTransform.m11();
        double c = planeTransform.m12();
        double d = -a * planeTransform.m30() - b * planeTransform.m31() - c * planeTransform.m32();
        return shadow(light.x(), light.y(), light.z(), light.w(), a, b, c, d, dest);
    }

    public Matrix4dc shadow(Vector4dc light, Matrix4dc planeTransform) {
        return shadow(light, planeTransform, this);
    }

    public Matrix4dc shadow(double lightX, double lightY, double lightZ, double lightW, IMatrix4d planeTransform, Matrix4dc dest) {
        // compute plane equation by transforming (y = 0)
        double a = planeTransform.m10();
        double b = planeTransform.m11();
        double c = planeTransform.m12();
        double d = -a * planeTransform.m30() - b * planeTransform.m31() - c * planeTransform.m32();
        return shadow(lightX, lightY, lightZ, lightW, a, b, c, d, dest);
    }

    public Matrix4dc shadow(double lightX, double lightY, double lightZ, double lightW, IMatrix4d planeTransform) {
        return shadow(lightX, lightY, lightZ, lightW, planeTransform, this);
    }

    public Matrix4dc billboardCylindrical(IVector3d objPos, IVector3d targetPos, IVector3d up) {
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
        m03 = 0.0;
        m10 = up.x();
        m11 = up.y();
        m12 = up.z();
        m13 = 0.0;
        m20 = dirX;
        m21 = dirY;
        m22 = dirZ;
        m23 = 0.0;
        m30 = objPos.x();
        m31 = objPos.y();
        m32 = objPos.z();
        m33 = 1.0;
        properties = PROPERTY_AFFINE;
        return this;
    }

    public Matrix4dc billboardSpherical(IVector3d objPos, IVector3d targetPos, IVector3d up) {
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
        m03 = 0.0;
        m10 = upX;
        m11 = upY;
        m12 = upZ;
        m13 = 0.0;
        m20 = dirX;
        m21 = dirY;
        m22 = dirZ;
        m23 = 0.0;
        m30 = objPos.x();
        m31 = objPos.y();
        m32 = objPos.z();
        m33 = 1.0;
        properties = PROPERTY_AFFINE;
        return this;
    }

    public Matrix4dc billboardSpherical(IVector3d objPos, IVector3d targetPos) {
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
        m03 = 0.0;
        m10 = q01;
        m11 = 1.0 - q00;
        m12 = q03;
        m13 = 0.0;
        m20 = q13;
        m21 = -q03;
        m22 = 1.0 - q11 - q00;
        m23 = 0.0;
        m30 = objPos.x();
        m31 = objPos.y();
        m32 = objPos.z();
        m33 = 1.0;
        properties = PROPERTY_AFFINE;
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
        temp = Double.doubleToLongBits(m03);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(m10);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(m11);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(m12);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(m13);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(m20);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(m21);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(m22);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(m23);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(m30);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(m31);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(m32);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(m33);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Matrix4dc))
            return false;
        Matrix4dc other = (Matrix4dc) obj;
        if (Double.doubleToLongBits(m00) != Double.doubleToLongBits(other.m00()))
            return false;
        if (Double.doubleToLongBits(m01) != Double.doubleToLongBits(other.m01()))
            return false;
        if (Double.doubleToLongBits(m02) != Double.doubleToLongBits(other.m02()))
            return false;
        if (Double.doubleToLongBits(m03) != Double.doubleToLongBits(other.m03()))
            return false;
        if (Double.doubleToLongBits(m10) != Double.doubleToLongBits(other.m10()))
            return false;
        if (Double.doubleToLongBits(m11) != Double.doubleToLongBits(other.m11()))
            return false;
        if (Double.doubleToLongBits(m12) != Double.doubleToLongBits(other.m12()))
            return false;
        if (Double.doubleToLongBits(m13) != Double.doubleToLongBits(other.m13()))
            return false;
        if (Double.doubleToLongBits(m20) != Double.doubleToLongBits(other.m20()))
            return false;
        if (Double.doubleToLongBits(m21) != Double.doubleToLongBits(other.m21()))
            return false;
        if (Double.doubleToLongBits(m22) != Double.doubleToLongBits(other.m22()))
            return false;
        if (Double.doubleToLongBits(m23) != Double.doubleToLongBits(other.m23()))
            return false;
        if (Double.doubleToLongBits(m30) != Double.doubleToLongBits(other.m30()))
            return false;
        if (Double.doubleToLongBits(m31) != Double.doubleToLongBits(other.m31()))
            return false;
        if (Double.doubleToLongBits(m32) != Double.doubleToLongBits(other.m32()))
            return false;
        if (Double.doubleToLongBits(m33) != Double.doubleToLongBits(other.m33()))
            return false;
        return true;
    }

    public Matrix4dc pick(double x, double y, double width, double height, int[] viewport, Matrix4dc dest) {
        double sx = viewport[2] / width;
        double sy = viewport[3] / height;
        double tx = (viewport[2] + 2.0 * (viewport[0] - x)) / width;
        double ty = (viewport[3] + 2.0 * (viewport[1] - y)) / height;
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
        dest.property(0);
        return dest;
    }

    public Matrix4dc pick(double x, double y, double width, double height, int[] viewport) {
        return pick(x, y, width, height, viewport, this);
    }

    public boolean isAffine() {
        return m03 == 0.0 && m13 == 0.0 && m23 == 0.0 && m33 == 1.0;
    }

    public Matrix4dc swap(Matrix4dc other) {
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
        tmp = m03;
        m03 = other.m03();
        other.m03(tmp);
        tmp = m10;
        m10 = other.m10();
        other.m10(tmp);
        tmp = m11;
        m11 = other.m11();
        other.m11(tmp);
        tmp = m12;
        m12 = other.m12();
        other.m12(tmp);
        tmp = m13;
        m13 = other.m13();
        other.m13(tmp);
        tmp = m20;
        m20 = other.m20();
        other.m20(tmp);
        tmp = m21;
        m21 = other.m21();
        other.m21(tmp);
        tmp = m22;
        m22 = other.m22();
        other.m22(tmp);
        tmp = m23;
        m23 = other.m23();
        other.m23(tmp);
        tmp = m30;
        m30 = other.m30();
        other.m30(tmp);
        tmp = m31;
        m31 = other.m31();
        other.m31(tmp);
        tmp = m32;
        m32 = other.m32();
        other.m32(tmp);
        tmp = m33;
        m33 = other.m33();
        other.m33(tmp);
        byte props = properties;
        this.properties = other.properties();
        other.property(props);
        return this;
    }

    public Matrix4dc arcball(double radius, double centerX, double centerY, double centerZ, double angleX, double angleY, Matrix4dc dest) {
        double m30 = m20 * -radius + this.m30;
        double m31 = m21 * -radius + this.m31;
        double m32 = m22 * -radius + this.m32;
        double m33 = m23 * -radius + this.m33;
        double sin = Math.sin(angleX);
        double cos = Math.cosFromSin(sin, angleX);
        double nm10 = m10 * cos + m20 * sin;
        double nm11 = m11 * cos + m21 * sin;
        double nm12 = m12 * cos + m22 * sin;
        double nm13 = m13 * cos + m23 * sin;
        double m20 = this.m20 * cos - m10 * sin;
        double m21 = this.m21 * cos - m11 * sin;
        double m22 = this.m22 * cos - m12 * sin;
        double m23 = this.m23 * cos - m13 * sin;
        sin = Math.sin(angleY);
        cos = Math.cosFromSin(sin, angleY);
        double nm00 = m00 * cos - m20 * sin;
        double nm01 = m01 * cos - m21 * sin;
        double nm02 = m02 * cos - m22 * sin;
        double nm03 = m03 * cos - m23 * sin;
        double nm20 = m00 * sin + m20 * cos;
        double nm21 = m01 * sin + m21 * cos;
        double nm22 = m02 * sin + m22 * cos;
        double nm23 = m03 * sin + m23 * cos;
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
        dest.property(properties & ~(PROPERTY_PERSPECTIVE | PROPERTY_IDENTITY | PROPERTY_TRANSLATION));
        return dest;
    }

    public Matrix4dc arcball(double radius, IVector3d center, double angleX, double angleY, Matrix4dc dest) {
        return arcball(radius, center.x(), center.y(), center.z(), angleX, angleY, dest);
    }

    public Matrix4dc arcball(double radius, double centerX, double centerY, double centerZ, double angleX, double angleY) {
        return arcball(radius, centerX, centerY, centerZ, angleX, angleY, this);
    }

    public Matrix4dc arcball(double radius, IVector3d center, double angleX, double angleY) {
        return arcball(radius, center.x(), center.y(), center.z(), angleX, angleY, this);
    }

    public Matrix4dc frustumAabb(Vector3dc min, Vector3dc max) {
        double minX = Double.POSITIVE_INFINITY;
        double minY = Double.POSITIVE_INFINITY;
        double minZ = Double.POSITIVE_INFINITY;
        double maxX = Double.NEGATIVE_INFINITY;
        double maxY = Double.NEGATIVE_INFINITY;
        double maxZ = Double.NEGATIVE_INFINITY;
        for (int t = 0; t < 8; t++) {
            double x = ((t & 1) << 1) - 1.0;
            double y = (((t >>> 1) & 1) << 1) - 1.0;
            double z = (((t >>> 2) & 1) << 1) - 1.0;
            double invW = 1.0 / (m03 * x + m13 * y + m23 * z + m33);
            double nx = (m00 * x + m10 * y + m20 * z + m30) * invW;
            double ny = (m01 * x + m11 * y + m21 * z + m31) * invW;
            double nz = (m02 * x + m12 * y + m22 * z + m32) * invW;
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

    public Matrix4dc projectedGridRange(IMatrix4d projector, double sLower, double sUpper, Matrix4dc dest) {
        // Compute intersection with frustum edges and plane
        double minX = Double.POSITIVE_INFINITY, minY = Double.POSITIVE_INFINITY;
        double maxX = Double.NEGATIVE_INFINITY, maxY = Double.NEGATIVE_INFINITY;
        boolean intersection = false;
        for (int t = 0; t < 3 * 4; t++) {
            double c0X, c0Y, c0Z;
            double c1X, c1Y, c1Z;
            if (t < 4) {
                // all x edges
                c0X = -1;
                c1X = +1;
                c0Y = c1Y = ((t & 1) << 1) - 1.0;
                c0Z = c1Z = (((t >>> 1) & 1) << 1) - 1.0;
            } else if (t < 8) {
                // all y edges
                c0Y = -1;
                c1Y = +1;
                c0X = c1X = ((t & 1) << 1) - 1.0;
                c0Z = c1Z = (((t >>> 1) & 1) << 1) - 1.0;
            } else {
                // all z edges
                c0Z = -1;
                c1Z = +1;
                c0X = c1X = ((t & 1) << 1) - 1.0;
                c0Y = c1Y = (((t >>> 1) & 1) << 1) - 1.0;
            }
            // unproject corners
            double invW = 1.0 / (m03 * c0X + m13 * c0Y + m23 * c0Z + m33);
            double p0x = (m00 * c0X + m10 * c0Y + m20 * c0Z + m30) * invW;
            double p0y = (m01 * c0X + m11 * c0Y + m21 * c0Z + m31) * invW;
            double p0z = (m02 * c0X + m12 * c0Y + m22 * c0Z + m32) * invW;
            invW = 1.0 / (m03 * c1X + m13 * c1Y + m23 * c1Z + m33);
            double p1x = (m00 * c1X + m10 * c1Y + m20 * c1Z + m30) * invW;
            double p1y = (m01 * c1X + m11 * c1Y + m21 * c1Z + m31) * invW;
            double p1z = (m02 * c1X + m12 * c1Y + m22 * c1Z + m32) * invW;
            double dirX = p1x - p0x;
            double dirY = p1y - p0y;
            double dirZ = p1z - p0z;
            double invDenom = 1.0 / dirY;
            // test for intersection
            for (int s = 0; s < 2; s++) {
                double isectT = -(p0y + (s == 0 ? sLower : sUpper)) * invDenom;
                if (isectT >= 0.0 && isectT <= 1.0) {
                    intersection = true;
                    // project with projector matrix
                    double ix = p0x + isectT * dirX;
                    double iz = p0z + isectT * dirZ;
                    invW = 1.0 / (projector.m03() * ix + projector.m23() * iz + projector.m33());
                    double px = (projector.m00() * ix + projector.m20() * iz + projector.m30()) * invW;
                    double py = (projector.m01() * ix + projector.m21() * iz + projector.m31()) * invW;
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
        dest.property(PROPERTY_AFFINE);
        return dest;
    }

    public Matrix4dc perspectiveFrustumSlice(double near, double far, Matrix4dc dest) {
        double invOldNear = (m23 + m22) / m32;
        double invNearFar = 1.0 / (near - far);
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
        dest.property(properties & ~(PROPERTY_IDENTITY | PROPERTY_TRANSLATION));
        return dest;
    }

    public Matrix4dc orthoCrop(IMatrix4d view, Matrix4dc dest) {
        // determine min/max world z and min/max orthographically view-projected x/y
        double minX = Double.POSITIVE_INFINITY, maxX = Double.NEGATIVE_INFINITY;
        double minY = Double.POSITIVE_INFINITY, maxY = Double.NEGATIVE_INFINITY;
        double minZ = Double.POSITIVE_INFINITY, maxZ = Double.NEGATIVE_INFINITY;
        for (int t = 0; t < 8; t++) {
            double x = ((t & 1) << 1) - 1.0;
            double y = (((t >>> 1) & 1) << 1) - 1.0;
            double z = (((t >>> 2) & 1) << 1) - 1.0;
            double invW = 1.0 / (m03 * x + m13 * y + m23 * z + m33);
            double wx = (m00 * x + m10 * y + m20 * z + m30) * invW;
            double wy = (m01 * x + m11 * y + m21 * z + m31) * invW;
            double wz = (m02 * x + m12 * y + m22 * z + m32) * invW;
            invW = 1.0 / (view.m03() * wx + view.m13() * wy + view.m23() * wz + view.m33());
            double vx = view.m00() * wx + view.m10() * wy + view.m20() * wz + view.m30();
            double vy = view.m01() * wx + view.m11() * wy + view.m21() * wz + view.m31();
            double vz = (view.m02() * wx + view.m12() * wy + view.m22() * wz + view.m32()) * invW;
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

    public Matrix4dc trapezoidCrop(double p0x, double p0y, double p1x, double p1y, double p2x, double p2y, double p3x, double p3y) {
        double aX = p1y - p0y, aY = p0x - p1x;
        double m00 = aY;
        double m10 = -aX;
        double m30 = aX * p0y - aY * p0x;
        double m01 = aX;
        double m11 = aY;
        double m31 = -(aX * p0x + aY * p0y);
        double c3x = m00 * p3x + m10 * p3y + m30;
        double c3y = m01 * p3x + m11 * p3y + m31;
        double s = -c3x / c3y;
        m00 += s * m01;
        m10 += s * m11;
        m30 += s * m31;
        double d1x = m00 * p1x + m10 * p1y + m30;
        double d2x = m00 * p2x + m10 * p2y + m30;
        double d = d1x * c3y / (d2x - d1x);
        m31 += d;
        double sx = 2.0 / d2x;
        double sy = 1.0 / (c3y + d);
        double u = (sy + sy) * d / (1.0 - sy * d);
        double m03 = m01 * sy;
        double m13 = m11 * sy;
        double m33 = m31 * sy;
        m01 = (u + 1.0) * m03;
        m11 = (u + 1.0) * m13;
        m31 = (u + 1.0) * m33 - u;
        m00 = sx * m00 - m03;
        m10 = sx * m10 - m13;
        m30 = sx * m30 - m33;
        set(m00, m01, 0, m03,
                m10, m11, 0, m13,
                0, 0, 1, 0,
                m30, m31, 0, m33);
        properties = 0;
        return this;
    }

    public Matrix4dc transformAab(double minX, double minY, double minZ, double maxX, double maxY, double maxZ, Vector3dc outMin, Vector3dc outMax) {
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

    public Matrix4dc transformAab(IVector3d min, IVector3d max, Vector3dc outMin, Vector3dc outMax) {
        return transformAab(min.x(), min.y(), min.z(), max.x(), max.y(), max.z(), outMin, outMax);
    }

    public Matrix4dc lerp(IMatrix4d other, double t) {
        return lerp(other, t, this);
    }

    public Matrix4dc lerp(IMatrix4d other, double t, Matrix4dc dest) {
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

    public Matrix4dc rotateTowards(IVector3d direction, IVector3d up, Matrix4dc dest) {
        return rotateTowards(direction.x(), direction.y(), direction.z(), up.x(), up.y(), up.z(), dest);
    }

    public Matrix4dc rotateTowards(IVector3d direction, IVector3d up) {
        return rotateTowards(direction.x(), direction.y(), direction.z(), up.x(), up.y(), up.z(), this);
    }

    public Matrix4dc rotateTowards(double dirX, double dirY, double dirZ, double upX, double upY, double upZ) {
        return rotateTowards(dirX, dirY, dirZ, upX, upY, upZ, this);
    }

    public Matrix4dc rotateTowards(double dirX, double dirY, double dirZ, double upX, double upY, double upZ, Matrix4dc dest) {
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
        dest.m33(m33);
        double nm00 = m00 * rm00 + m10 * rm01 + m20 * rm02;
        double nm01 = m01 * rm00 + m11 * rm01 + m21 * rm02;
        double nm02 = m02 * rm00 + m12 * rm01 + m22 * rm02;
        double nm03 = m03 * rm00 + m13 * rm01 + m23 * rm02;
        double nm10 = m00 * rm10 + m10 * rm11 + m20 * rm12;
        double nm11 = m01 * rm10 + m11 * rm11 + m21 * rm12;
        double nm12 = m02 * rm10 + m12 * rm11 + m22 * rm12;
        double nm13 = m03 * rm10 + m13 * rm11 + m23 * rm12;
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
        dest.property(properties & ~(PROPERTY_PERSPECTIVE | PROPERTY_IDENTITY | PROPERTY_TRANSLATION));
        return dest;
    }

    public Matrix4dc rotationTowards(IVector3d dir, IVector3d up) {
        return rotationTowards(dir.x(), dir.y(), dir.z(), up.x(), up.y(), up.z());
    }

    public Matrix4dc rotationTowards(double dirX, double dirY, double dirZ, double upX, double upY, double upZ) {
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
        this.m03 = 0.0;
        this.m10 = upnX;
        this.m11 = upnY;
        this.m12 = upnZ;
        this.m13 = 0.0;
        this.m20 = ndirX;
        this.m21 = ndirY;
        this.m22 = ndirZ;
        this.m23 = 0.0;
        this.m30 = 0.0;
        this.m31 = 0.0;
        this.m32 = 0.0;
        this.m33 = 1.0;
        properties = PROPERTY_AFFINE;
        return this;
    }

    public Matrix4dc translationRotateTowards(IVector3d pos, IVector3d dir, IVector3d up) {
        return translationRotateTowards(pos.x(), pos.y(), pos.z(), dir.x(), dir.y(), dir.z(), up.x(), up.y(), up.z());
    }

    public Matrix4dc translationRotateTowards(double posX, double posY, double posZ, double dirX, double dirY, double dirZ, double upX, double upY, double upZ) {
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
        this.m03 = 0.0;
        this.m10 = upnX;
        this.m11 = upnY;
        this.m12 = upnZ;
        this.m13 = 0.0;
        this.m20 = ndirX;
        this.m21 = ndirY;
        this.m22 = ndirZ;
        this.m23 = 0.0;
        this.m30 = posX;
        this.m31 = posY;
        this.m32 = posZ;
        this.m33 = 1.0;
        properties = PROPERTY_AFFINE;
        return this;
    }

    public Vector3dc getEulerAnglesZYX(Vector3dc dest) {
        dest.set(Math.atan2(m12, m22), Math.atan2(-m02, Math.sqrt(m12 * m12 + m22 * m22)), Math.atan2(m01, m00));
        return dest;
    }

    public Matrix4dc affineSpan(Vector3dc corner, Vector3dc xDir, Vector3dc yDir, Vector3dc zDir) {
        double a = m10 * m22, b = m10 * m21, c = m10 * m02, d = m10 * m01;
        double e = m11 * m22, f = m11 * m20, g = m11 * m02, h = m11 * m00;
        double i = m12 * m21, j = m12 * m20, k = m12 * m01, l = m12 * m00;
        double m = m20 * m02, n = m20 * m01, o = m21 * m02, p = m21 * m00;
        double q = m22 * m01, r = m22 * m00;
        double s = 1.0 / (m00 * m11 - m01 * m10) * m22 + (m02 * m10 - m00 * m12) * m21 + (m01 * m12 - m02 * m11) * m20;
        double nm00 = (e - i) * s, nm01 = (o - q) * s, nm02 = (k - g) * s;
        double nm10 = (j - a) * s, nm11 = (r - m) * s, nm12 = (c - l) * s;
        double nm20 = (b - f) * s, nm21 = (n - p) * s, nm22 = (h - d) * s;
        corner.set(-nm00 - nm10 - nm20 + (a * m31 - b * m32 + f * m32 - e * m30 + i * m30 - j * m31) * s,
                -nm01 - nm11 - nm21 + (m * m31 - n * m32 + p * m32 - o * m30 + q * m30 - r * m31) * s,
                -nm02 - nm12 - nm22 + (g * m30 - k * m30 + l * m31 - c * m31 + d * m32 - h * m32) * s);
        xDir.set(2.0 * nm00, 2.0 * nm01, 2.0 * nm02);
        yDir.set(2.0 * nm10, 2.0 * nm11, 2.0 * nm12);
        zDir.set(2.0 * nm20, 2.0 * nm21, 2.0 * nm22);
        return this;
    }

    public boolean testPoint(double x, double y, double z) {
        double nxX = m03 + m00, nxY = m13 + m10, nxZ = m23 + m20, nxW = m33 + m30;
        double pxX = m03 - m00, pxY = m13 - m10, pxZ = m23 - m20, pxW = m33 - m30;
        double nyX = m03 + m01, nyY = m13 + m11, nyZ = m23 + m21, nyW = m33 + m31;
        double pyX = m03 - m01, pyY = m13 - m11, pyZ = m23 - m21, pyW = m33 - m31;
        double nzX = m03 + m02, nzY = m13 + m12, nzZ = m23 + m22, nzW = m33 + m32;
        double pzX = m03 - m02, pzY = m13 - m12, pzZ = m23 - m22, pzW = m33 - m32;
        return nxX * x + nxY * y + nxZ * z + nxW >= 0 && pxX * x + pxY * y + pxZ * z + pxW >= 0 &&
                nyX * x + nyY * y + nyZ * z + nyW >= 0 && pyX * x + pyY * y + pyZ * z + pyW >= 0 &&
                nzX * x + nzY * y + nzZ * z + nzW >= 0 && pzX * x + pzY * y + pzZ * z + pzW >= 0;
    }

    public boolean testSphere(double x, double y, double z, double r) {
        double invl;
        double nxX = m03 + m00, nxY = m13 + m10, nxZ = m23 + m20, nxW = m33 + m30;
        invl = 1.0 / Math.sqrt(nxX * nxX + nxY * nxY + nxZ * nxZ);
        nxX *= invl;
        nxY *= invl;
        nxZ *= invl;
        nxW *= invl;
        double pxX = m03 - m00, pxY = m13 - m10, pxZ = m23 - m20, pxW = m33 - m30;
        invl = 1.0 / Math.sqrt(pxX * pxX + pxY * pxY + pxZ * pxZ);
        pxX *= invl;
        pxY *= invl;
        pxZ *= invl;
        pxW *= invl;
        double nyX = m03 + m01, nyY = m13 + m11, nyZ = m23 + m21, nyW = m33 + m31;
        invl = 1.0 / Math.sqrt(nyX * nyX + nyY * nyY + nyZ * nyZ);
        nyX *= invl;
        nyY *= invl;
        nyZ *= invl;
        nyW *= invl;
        double pyX = m03 - m01, pyY = m13 - m11, pyZ = m23 - m21, pyW = m33 - m31;
        invl = 1.0 / Math.sqrt(pyX * pyX + pyY * pyY + pyZ * pyZ);
        pyX *= invl;
        pyY *= invl;
        pyZ *= invl;
        pyW *= invl;
        double nzX = m03 + m02, nzY = m13 + m12, nzZ = m23 + m22, nzW = m33 + m32;
        invl = 1.0 / Math.sqrt(nzX * nzX + nzY * nzY + nzZ * nzZ);
        nzX *= invl;
        nzY *= invl;
        nzZ *= invl;
        nzW *= invl;
        double pzX = m03 - m02, pzY = m13 - m12, pzZ = m23 - m22, pzW = m33 - m32;
        invl = 1.0 / Math.sqrt(pzX * pzX + pzY * pzY + pzZ * pzZ);
        pzX *= invl;
        pzY *= invl;
        pzZ *= invl;
        pzW *= invl;
        return nxX * x + nxY * y + nxZ * z + nxW >= -r && pxX * x + pxY * y + pxZ * z + pxW >= -r &&
                nyX * x + nyY * y + nyZ * z + nyW >= -r && pyX * x + pyY * y + pyZ * z + pyW >= -r &&
                nzX * x + nzY * y + nzZ * z + nzW >= -r && pzX * x + pzY * y + pzZ * z + pzW >= -r;
    }

    public boolean testAab(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
        double nxX = m03 + m00, nxY = m13 + m10, nxZ = m23 + m20, nxW = m33 + m30;
        double pxX = m03 - m00, pxY = m13 - m10, pxZ = m23 - m20, pxW = m33 - m30;
        double nyX = m03 + m01, nyY = m13 + m11, nyZ = m23 + m21, nyW = m33 + m31;
        double pyX = m03 - m01, pyY = m13 - m11, pyZ = m23 - m21, pyW = m33 - m31;
        double nzX = m03 + m02, nzY = m13 + m12, nzZ = m23 + m22, nzW = m33 + m32;
        double pzX = m03 - m02, pzY = m13 - m12, pzZ = m23 - m22, pzW = m33 - m32;
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
            Vector3dc eye, Vector3dc p, Vector3dc x, Vector3dc y, double nearFarDist, boolean zeroToOne,
            Matrix4dc projDest, Matrix4dc viewDest) {
        double zx = y.y() * x.z() - y.z() * x.y(), zy = y.z() * x.x() - y.x() * x.z(), zz = y.x() * x.y() - y.y() * x.x();
        double zd = zx * (p.x() - eye.x()) + zy * (p.y() - eye.y()) + zz * (p.z() - eye.z());
        double zs = zd >= 0 ? 1 : -1;
        zx *= zs;
        zy *= zs;
        zz *= zs;
        zd *= zs;
        viewDest.setLookAt(eye.x(), eye.y(), eye.z(), eye.x() + zx, eye.y() + zy, eye.z() + zz, y.x(), y.y(), y.z());
        double px = viewDest.m00() * p.x() + viewDest.m10() * p.y() + viewDest.m20() * p.z() + viewDest.m30();
        double py = viewDest.m01() * p.x() + viewDest.m11() * p.y() + viewDest.m21() * p.z() + viewDest.m31();
        double tx = viewDest.m00() * x.x() + viewDest.m10() * x.y() + viewDest.m20() * x.z();
        double ty = viewDest.m01() * y.x() + viewDest.m11() * y.y() + viewDest.m21() * y.z();
        double len = Math.sqrt(zx * zx + zy * zy + zz * zz);
        double near = zd / len, far;
        if (Double.isInfinite(nearFarDist) && nearFarDist < 0.0) {
            far = near;
            near = Double.POSITIVE_INFINITY;
        } else if (Double.isInfinite(nearFarDist) && nearFarDist > 0.0) {
            far = Double.POSITIVE_INFINITY;
        } else if (nearFarDist < 0.0) {
            far = near;
            near = near + nearFarDist;
        } else {
            far = near + nearFarDist;
        }
        projDest.setFrustum(px, px + tx, py, py + ty, near, far, zeroToOne);
    }
}
