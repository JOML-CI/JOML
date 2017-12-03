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

import org.joml.api.matrix.IMatrix3x2d;
import org.joml.api.matrix.Matrix3x2dc;
import org.joml.api.vector.IVector2d;
import org.joml.api.vector.IVector3d;
import org.joml.api.vector.Vector2dc;
import org.joml.api.vector.Vector3dc;
//#ifdef __HAS_NIO__
import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
//#endif
import java.text.DecimalFormat;
import java.text.NumberFormat;

//#ifdef __GWT__
import com.google.gwt.typedarrays.shared.Float64Array;
//#endif

/**
 * Contains the definition of a 3x2 matrix of doubles, and associated functions to transform it. The matrix is
 * column-major to match OpenGL's interpretation, and it looks like this:
 * <p>
 * m00  m10  m20<br> m01  m11  m21<br>
 *
 * @author Kai Burjack
 */
public class Matrix3x2d extends Matrix3x2dc implements Externalizable {

    private static final long serialVersionUID = 1L;

    public double m00, m01;
    public double m10, m11;
    public double m20, m21;

    /**
     * Create a new {@link Matrix3x2d} and set it to {@link #identity() identity}.
     */
    public Matrix3x2d() {
        this.m00 = 1.0;
        this.m11 = 1.0;
    }

    /**
     * Create a new {@link Matrix3x2d} and make it a copy of the given matrix.
     *
     * @param mat the {@link IMatrix3x2d} to copy the values from
     */
    public Matrix3x2d(IMatrix3x2d mat) {
        if (mat instanceof Matrix3x2d) {
            MemUtil.INSTANCE.copy((Matrix3x2d) mat, this);
        } else {
            setMatrix3x2dc(mat);
        }
    }

    /**
     * Create a new 3x2 matrix using the supplied double values. The order of the parameter is column-major, so the
     * first two parameters specify the two elements of the first column.
     *
     * @param m00 the value of m00
     * @param m01 the value of m01
     * @param m10 the value of m10
     * @param m11 the value of m11
     * @param m20 the value of m20
     * @param m21 the value of m21
     */
    public Matrix3x2d(double m00, double m01,
                      double m10, double m11,
                      double m20, double m21) {
        this.m00 = m00;
        this.m01 = m01;
        this.m10 = m10;
        this.m11 = m11;
        this.m20 = m20;
        this.m21 = m21;
    }

    //#ifdef __HAS_NIO__

    /**
     * Create a new {@link Matrix3x2d} by reading its 6 double components from the given {@link DoubleBuffer} at the
     * buffer's current position.
     * <p>
     * That DoubleBuffer is expected to hold the values in column-major order.
     * <p>
     * The buffer's position will not be changed by this method.
     *
     * @param buffer the {@link DoubleBuffer} to read the matrix values from
     */
    public Matrix3x2d(DoubleBuffer buffer) {
        MemUtil.INSTANCE.get(this, buffer.position(), buffer);
    }
    //#endif

    public double m00() {
        return m00;
    }

    public double m01() {
        return m01;
    }

    public double m10() {
        return m10;
    }

    public double m11() {
        return m11;
    }

    public double m20() {
        return m20;
    }

    public double m21() {
        return m21;
    }

    public Matrix3x2dc m00(double m00) {
        this.m00 = m00;
        return this;
    }

    public Matrix3x2dc m01(double m01) {
        this.m01 = m01;
        return this;
    }

    public Matrix3x2dc m10(double m10) {
        this.m10 = m10;
        return this;
    }

    public Matrix3x2dc m11(double m11) {
        this.m11 = m11;
        return this;
    }

    public Matrix3x2dc m20(double m20) {
        this.m20 = m20;
        return this;
    }

    public Matrix3x2dc m21(double m21) {
        this.m21 = m21;
        return this;
    }

    public Matrix3x2dc set(IMatrix3x2d m) {
        if (m instanceof Matrix3x2d) {
            MemUtil.INSTANCE.copy((Matrix3x2d) m, this);
        } else {
            setMatrix3x2dc(m);
        }
        return this;
    }

    private void setMatrix3x2dc(IMatrix3x2d mat) {
        m00 = mat.m00();
        m01 = mat.m01();
        m10 = mat.m10();
        m11 = mat.m11();
        m20 = mat.m20();
        m21 = mat.m21();
    }

    public Matrix3x2dc mul(IMatrix3x2d right) {
        return mul(right, this);
    }

    public Matrix3x2dc mul(IMatrix3x2d right, Matrix3x2dc dest) {
        double nm00 = m00 * right.m00() + m10 * right.m01();
        double nm01 = m01 * right.m00() + m11 * right.m01();
        double nm10 = m00 * right.m10() + m10 * right.m11();
        double nm11 = m01 * right.m10() + m11 * right.m11();
        double nm20 = m00 * right.m20() + m10 * right.m21() + m20;
        double nm21 = m01 * right.m20() + m11 * right.m21() + m21;
        dest.m00(nm00);
        dest.m01(nm01);
        dest.m10(nm10);
        dest.m11(nm11);
        dest.m20(nm20);
        dest.m21(nm21);
        return dest;
    }

    public Matrix3x2dc mulLocal(IMatrix3x2d left) {
        return mulLocal(left, this);
    }

    public Matrix3x2dc mulLocal(IMatrix3x2d left, Matrix3x2dc dest) {
        double nm00 = left.m00() * m00 + left.m10() * m01;
        double nm01 = left.m01() * m00 + left.m11() * m01;
        double nm10 = left.m00() * m10 + left.m10() * m11;
        double nm11 = left.m01() * m10 + left.m11() * m11;
        double nm20 = left.m00() * m20 + left.m10() * m21 + left.m20();
        double nm21 = left.m01() * m20 + left.m11() * m21 + left.m21();
        dest.m00(nm00);
        dest.m01(nm01);
        dest.m10(nm10);
        dest.m11(nm11);
        dest.m20(nm20);
        dest.m21(nm21);
        return dest;
    }

    public Matrix3x2dc set(double m00, double m01,
                           double m10, double m11,
                           double m20, double m21) {
        this.m00 = m00;
        this.m01 = m01;
        this.m10 = m10;
        this.m11 = m11;
        this.m20 = m20;
        this.m21 = m21;
        return this;
    }

    public Matrix3x2dc set(double m[]) {
        MemUtil.INSTANCE.copy(m, 0, this);
        return this;
    }

    public double determinant() {
        return m00 * m11 - m01 * m10;
    }

    public Matrix3x2dc invert() {
        return invert(this);
    }

    public Matrix3x2dc invert(Matrix3x2dc dest) {
        // client must make sure that matrix is invertible
        double s = 1.0 / (m00 * m11 - m01 * m10);
        double nm00 = m11 * s;
        double nm01 = -m01 * s;
        double nm10 = -m10 * s;
        double nm11 = m00 * s;
        double nm20 = (m10 * m21 - m20 * m11) * s;
        double nm21 = (m20 * m01 - m00 * m21) * s;
        dest.m00(nm00);
        dest.m01(nm01);
        dest.m10(nm10);
        dest.m11(nm11);
        dest.m20(nm20);
        dest.m21(nm21);
        return dest;
    }

    public Matrix3x2dc translation(double x, double y) {
        m00 = 1.0;
        m01 = 0.0;
        m10 = 0.0;
        m11 = 1.0;
        m20 = x;
        m21 = y;
        return this;
    }

    public Matrix3x2dc translation(IVector2d offset) {
        return translation(offset.x(), offset.y());
    }

    public Matrix3x2dc setTranslation(double x, double y) {
        m20 = x;
        m21 = y;
        return this;
    }

    public Matrix3x2dc setTranslation(IVector2d offset) {
        return setTranslation(offset.x(), offset.y());
    }

    public Matrix3x2dc translate(double x, double y, Matrix3x2dc dest) {
        double rm20 = x;
        double rm21 = y;
        dest.m20(m00 * rm20 + m10 * rm21 + m20);
        dest.m21(m01 * rm20 + m11 * rm21 + m21);
        dest.m00(m00);
        dest.m01(m01);
        dest.m10(m10);
        dest.m11(m11);
        return dest;
    }

    public Matrix3x2dc translate(double x, double y) {
        return translate(x, y, this);
    }

    public Matrix3x2dc translate(IVector2d offset, Matrix3x2dc dest) {
        return translate(offset.x(), offset.y(), dest);
    }

    public Matrix3x2dc translate(IVector2d offset) {
        return translate(offset.x(), offset.y(), this);
    }

    public Matrix3x2dc translateLocal(IVector2d offset) {
        return translateLocal(offset.x(), offset.y());
    }

    public Matrix3x2dc translateLocal(IVector2d offset, Matrix3x2dc dest) {
        return translateLocal(offset.x(), offset.y(), dest);
    }

    public Matrix3x2dc translateLocal(double x, double y, Matrix3x2dc dest) {
        dest.m00(m00);
        dest.m01(m01);
        dest.m10(m10);
        dest.m11(m11);
        dest.m20(m20 + x);
        dest.m21(m21 + y);
        return dest;
    }

    public Matrix3x2dc translateLocal(double x, double y) {
        return translateLocal(x, y, this);
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
        return formatter.format(m00) + " " + formatter.format(m10) + " " + formatter.format(m20) + "\n"
                + formatter.format(m01) + " " + formatter.format(m11) + " " + formatter.format(m21) + "\n";
    }

    public Matrix3x2dc get(Matrix3x2dc dest) {
        return dest.set(this);
    }

    //#ifdef __GWT__

    public Float64Array get(Float64Array buffer) {
        buffer.set(0, m00);
        buffer.set(1, m01);
        buffer.set(2, m10);
        buffer.set(3, m11);
        buffer.set(4, m20);
        buffer.set(5, m21);
        return buffer;
    }

    public Float64Array get(int index, Float64Array buffer) {
        buffer.set(index, m00);
        buffer.set(index + 1, m01);
        buffer.set(index + 2, m10);
        buffer.set(index + 3, m11);
        buffer.set(index + 4, m20);
        buffer.set(index + 5, m21);
        return buffer;
    }
    //#endif

    //#ifdef __HAS_NIO__

    public DoubleBuffer get(DoubleBuffer buffer) {
        return get(buffer.position(), buffer);
    }

    public DoubleBuffer get(int index, DoubleBuffer buffer) {
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

    public DoubleBuffer get4x4(DoubleBuffer buffer) {
        MemUtil.INSTANCE.put4x4(this, 0, buffer);
        return buffer;
    }

    public DoubleBuffer get4x4(int index, DoubleBuffer buffer) {
        MemUtil.INSTANCE.put4x4(this, index, buffer);
        return buffer;
    }

    public ByteBuffer get4x4(ByteBuffer buffer) {
        MemUtil.INSTANCE.put4x4(this, 0, buffer);
        return buffer;
    }

    public ByteBuffer get4x4(int index, ByteBuffer buffer) {
        MemUtil.INSTANCE.put4x4(this, index, buffer);
        return buffer;
    }
    //#endif

    public double[] get(double[] arr, int offset) {
        MemUtil.INSTANCE.copy(this, arr, offset);
        return arr;
    }

    public double[] get(double[] arr) {
        return get(arr, 0);
    }

    public double[] get4x4(double[] arr, int offset) {
        MemUtil.INSTANCE.copy4x4(this, arr, offset);
        return arr;
    }

    public double[] get4x4(double[] arr) {
        return get4x4(arr, 0);
    }

    //#ifdef __HAS_NIO__

    public Matrix3x2dc set(DoubleBuffer buffer) {
        int pos = buffer.position();
        MemUtil.INSTANCE.get(this, pos, buffer);
        return this;
    }

    public Matrix3x2dc set(ByteBuffer buffer) {
        int pos = buffer.position();
        MemUtil.INSTANCE.get(this, pos, buffer);
        return this;
    }
    //#endif

    public Matrix3x2dc zero() {
        MemUtil.INSTANCE.zero(this);
        return this;
    }

    public Matrix3x2dc identity() {
        MemUtil.INSTANCE.identity(this);
        return this;
    }

    public Matrix3x2dc scale(double x, double y, Matrix3x2dc dest) {
        dest.m00(m00 * x);
        dest.m01(m01 * x);
        dest.m10(m10 * y);
        dest.m11(m11 * y);
        dest.m20(m20);
        dest.m21(m21);
        return dest;
    }

    public Matrix3x2dc scale(double x, double y) {
        return scale(x, y, this);
    }

    public Matrix3x2dc scale(double xy, Matrix3x2dc dest) {
        return scale(xy, xy, dest);
    }

    public Matrix3x2dc scale(double xy) {
        return scale(xy, xy);
    }

    public Matrix3x2dc scaleLocal(double x, double y, Matrix3x2dc dest) {
        dest.m00(x * m00);
        dest.m01(y * m01);
        dest.m10(x * m10);
        dest.m11(y * m11);
        dest.m20(x * m20);
        dest.m21(y * m21);
        return dest;
    }

    public Matrix3x2dc scaleLocal(double x, double y) {
        return scaleLocal(x, y, this);
    }

    public Matrix3x2dc scaleLocal(double xy, Matrix3x2dc dest) {
        return scaleLocal(xy, xy, dest);
    }

    public Matrix3x2dc scaleLocal(double xy) {
        return scaleLocal(xy, xy, this);
    }

    public Matrix3x2dc scaleAround(double sx, double sy, double ox, double oy, Matrix3x2dc dest) {
        double nm20 = m00 * ox + m10 * oy + m20;
        double nm21 = m01 * ox + m11 * oy + m21;
        dest.m00(m00 * sx);
        dest.m01(m01 * sx);
        dest.m10(m10 * sy);
        dest.m11(m11 * sy);
        dest.m20(-m00 * ox - m10 * oy + nm20);
        dest.m21(-m01 * ox - m11 * oy + nm21);
        return dest;
    }

    public Matrix3x2dc scaleAround(double sx, double sy, double ox, double oy) {
        return scaleAround(sx, sy, ox, oy, this);
    }

    public Matrix3x2dc scaleAround(double factor, double ox, double oy, Matrix3x2dc dest) {
        return scaleAround(factor, factor, ox, oy, this);
    }

    public Matrix3x2dc scaleAround(double factor, double ox, double oy) {
        return scaleAround(factor, factor, ox, oy, this);
    }

    public Matrix3x2dc scaleAroundLocal(double sx, double sy, double ox, double oy, Matrix3x2dc dest) {
        dest.m00(sx * m00);
        dest.m01(sy * m01);
        dest.m10(sx * m10);
        dest.m11(sy * m11);
        dest.m20(sx * m20 - sx * ox + ox);
        dest.m21(sy * m21 - sy * oy + oy);
        return dest;
    }

    public Matrix3x2dc scaleAroundLocal(double factor, double ox, double oy, Matrix3x2dc dest) {
        return scaleAroundLocal(factor, factor, ox, oy, dest);
    }

    public Matrix3x2dc scaleAroundLocal(double sx, double sy, double sz, double ox, double oy, double oz) {
        return scaleAroundLocal(sx, sy, ox, oy, this);
    }

    public Matrix3x2dc scaleAroundLocal(double factor, double ox, double oy) {
        return scaleAroundLocal(factor, factor, ox, oy, this);
    }

    public Matrix3x2dc scaling(double factor) {
        return scaling(factor, factor);
    }

    public Matrix3x2dc scaling(double x, double y) {
        m00 = x;
        m01 = 0.0;
        m10 = 0.0;
        m11 = y;
        m20 = 0.0;
        m21 = 0.0;
        return this;
    }

    public Matrix3x2dc rotation(double angle) {
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);
        m00 = cos;
        m10 = -sin;
        m20 = 0.0;
        m01 = sin;
        m11 = cos;
        m21 = 0.0;
        return this;
    }

    public Vector3dc transform(Vector3dc v) {
        return v.mul(this);
    }

    public Vector3dc transform(IVector3d v, Vector3dc dest) {
        return v.mul(this, dest);
    }

    public Vector3dc transform(double x, double y, double z, Vector3dc dest) {
        return dest.set(m00 * x + m10 * y + m20 * z, m01 * x + m11 * y + m21 * z, z);
    }

    public Vector2dc transformPosition(Vector2dc v) {
        v.set(m00 * v.x() + m10 * v.y() + m20,
                m01 * v.x() + m11 * v.y() + m21);
        return v;
    }

    public Vector2dc transformPosition(IVector2d v, Vector2dc dest) {
        dest.set(m00 * v.x() + m10 * v.y() + m20,
                m01 * v.x() + m11 * v.y() + m21);
        return dest;
    }

    public Vector2dc transformPosition(double x, double y, Vector2dc dest) {
        return dest.set(m00 * x + m10 * y + m20, m01 * x + m11 * y + m21);
    }

    public Vector2dc transformDirection(Vector2dc v) {
        v.set(m00 * v.x() + m10 * v.y(),
                m01 * v.x() + m11 * v.y());
        return v;
    }

    public Vector2dc transformDirection(IVector2d v, Vector2dc dest) {
        dest.set(m00 * v.x() + m10 * v.y(),
                m01 * v.x() + m11 * v.y());
        return dest;
    }

    public Vector2dc transformDirection(double x, double y, Vector2dc dest) {
        return dest.set(m00 * x + m10 * y, m01 * x + m11 * y);
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeDouble(m00);
        out.writeDouble(m01);
        out.writeDouble(m10);
        out.writeDouble(m11);
        out.writeDouble(m20);
        out.writeDouble(m21);
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        m00 = in.readDouble();
        m01 = in.readDouble();
        m10 = in.readDouble();
        m11 = in.readDouble();
        m20 = in.readDouble();
        m21 = in.readDouble();
    }

    public Matrix3x2dc rotate(double ang) {
        return rotate(ang, this);
    }

    public Matrix3x2dc rotate(double ang, Matrix3x2dc dest) {
        double cos = Math.cos(ang);
        double sin = Math.sin(ang);
        double rm00 = cos;
        double rm01 = sin;
        double rm10 = -sin;
        double rm11 = cos;
        double nm00 = m00 * rm00 + m10 * rm01;
        double nm01 = m01 * rm00 + m11 * rm01;
        dest.m10(m00 * rm10 + m10 * rm11);
        dest.m11(m01 * rm10 + m11 * rm11);
        dest.m00(nm00);
        dest.m01(nm01);
        dest.m20(m20);
        dest.m21(m21);
        return dest;
    }

    public Matrix3x2dc rotateLocal(double ang, Matrix3x2dc dest) {
        double sin = Math.sin(ang);
        double cos = Math.cosFromSin(sin, ang);
        double nm00 = cos * m00 - sin * m01;
        double nm01 = sin * m00 + cos * m01;
        double nm10 = cos * m10 - sin * m11;
        double nm11 = sin * m10 + cos * m11;
        double nm20 = cos * m20 - sin * m21;
        double nm21 = sin * m20 + cos * m21;
        dest.m00(nm00);
        dest.m01(nm01);
        dest.m10(nm10);
        dest.m11(nm11);
        dest.m20(nm20);
        dest.m21(nm21);
        return dest;
    }

    public Matrix3x2dc rotateLocal(double ang) {
        return rotateLocal(ang, this);
    }

    public Matrix3x2dc rotateAbout(double ang, double x, double y) {
        return rotateAbout(ang, x, y, this);
    }

    public Matrix3x2dc rotateAbout(double ang, double x, double y, Matrix3x2dc dest) {
        double tm20 = m00 * x + m10 * y + m20;
        double tm21 = m01 * x + m11 * y + m21;
        double cos = Math.cos(ang);
        double sin = Math.sin(ang);
        double nm00 = m00 * cos + m10 * sin;
        double nm01 = m01 * cos + m11 * sin;
        dest.m10(m00 * -sin + m10 * cos);
        dest.m11(m01 * -sin + m11 * cos);
        dest.m00(nm00);
        dest.m01(nm01);
        dest.m20(dest.m00() * -x + dest.m10() * -y + tm20);
        dest.m21(dest.m01() * -x + dest.m11() * -y + tm21);
        return dest;
    }

    public Matrix3x2dc rotateTo(IVector2d fromDir, IVector2d toDir, Matrix3x2dc dest) {
        double dot = fromDir.x() * toDir.x() + fromDir.y() * toDir.y();
        double det = fromDir.x() * toDir.y() - fromDir.y() * toDir.x();
        double rm00 = dot;
        double rm01 = det;
        double rm10 = -det;
        double rm11 = dot;
        double nm00 = m00 * rm00 + m10 * rm01;
        double nm01 = m01 * rm00 + m11 * rm01;
        dest.m10(m00 * rm10 + m10 * rm11);
        dest.m11(m01 * rm10 + m11 * rm11);
        dest.m00(nm00);
        dest.m01(nm01);
        dest.m20(m20);
        dest.m21(m21);
        return dest;
    }

    public Matrix3x2dc rotateTo(IVector2d fromDir, IVector2d toDir) {
        return rotateTo(fromDir, toDir, this);
    }

    public Matrix3x2dc view(double left, double right, double bottom, double top, Matrix3x2dc dest) {
        double rm00 = 2.0 / (right - left);
        double rm11 = 2.0 / (top - bottom);
        double rm20 = (left + right) / (left - right);
        double rm21 = (bottom + top) / (bottom - top);
        dest.m20(m00 * rm20 + m10 * rm21 + m20);
        dest.m21(m01 * rm20 + m11 * rm21 + m21);
        dest.m00(m00 * rm00);
        dest.m01(m01 * rm00);
        dest.m10(m10 * rm11);
        dest.m11(m11 * rm11);
        return dest;
    }

    public Matrix3x2dc view(double left, double right, double bottom, double top) {
        return view(left, right, bottom, top, this);
    }

    public Matrix3x2dc setView(double left, double right, double bottom, double top) {
        m00 = 2.0 / (right - left);
        m01 = 0.0;
        m10 = 0.0;
        m11 = 2.0 / (top - bottom);
        m20 = (left + right) / (left - right);
        m21 = (bottom + top) / (bottom - top);
        return this;
    }

    public Vector2dc origin(Vector2dc origin) {
        double s = 1.0 / (m00 * m11 - m01 * m10);
        origin.set((m10 * m21 - m20 * m11) * s, (m20 * m01 - m00 * m21) * s);
        return origin;
    }

    public double[] viewArea(double[] area) {
        double s = 1.0 / (m00 * m11 - m01 * m10);
        double rm00 = m11 * s;
        double rm01 = -m01 * s;
        double rm10 = -m10 * s;
        double rm11 = m00 * s;
        double rm20 = (m10 * m21 - m20 * m11) * s;
        double rm21 = (m20 * m01 - m00 * m21) * s;
        double nxnyX = -rm00 - rm10;
        double nxnyY = -rm01 - rm11;
        double pxnyX = rm00 - rm10;
        double pxnyY = rm01 - rm11;
        double nxpyX = -rm00 + rm10;
        double nxpyY = -rm01 + rm11;
        double pxpyX = rm00 + rm10;
        double pxpyY = rm01 + rm11;
        double minX = nxnyX;
        minX = minX < nxpyX ? minX : nxpyX;
        minX = minX < pxnyX ? minX : pxnyX;
        minX = minX < pxpyX ? minX : pxpyX;
        double minY = nxnyY;
        minY = minY < nxpyY ? minY : nxpyY;
        minY = minY < pxnyY ? minY : pxnyY;
        minY = minY < pxpyY ? minY : pxpyY;
        double maxX = nxnyX;
        maxX = maxX > nxpyX ? maxX : nxpyX;
        maxX = maxX > pxnyX ? maxX : pxnyX;
        maxX = maxX > pxpyX ? maxX : pxpyX;
        double maxY = nxnyY;
        maxY = maxY > nxpyY ? maxY : nxpyY;
        maxY = maxY > pxnyY ? maxY : pxnyY;
        maxY = maxY > pxpyY ? maxY : pxpyY;
        area[0] = minX + rm20;
        area[1] = minY + rm21;
        area[2] = maxX + rm20;
        area[3] = maxY + rm21;
        return area;
    }

    public Vector2dc positiveX(Vector2dc dir) {
        double s = m00 * m11 - m01 * m10;
        s = 1.0 / s;
        dir.set(m11 * s, -m01 * s);
        dir.normalize();
        return dir;
    }

    public Vector2dc normalizedPositiveX(Vector2dc dir) {
        dir.set(m11, -m01);
        return dir;
    }

    public Vector2dc positiveY(Vector2dc dir) {
        double s = m00 * m11 - m01 * m10;
        s = 1.0 / s;
        dir.set(-m10 * s, m00 * s);
        dir.normalize();
        return dir;
    }

    public Vector2dc normalizedPositiveY(Vector2dc dir) {
        dir.set(-m10, m00);
        return dir;
    }

    public Vector2dc unproject(double winX, double winY, int[] viewport, Vector2dc dest) {
        double s = 1.0 / (m00 * m11 - m01 * m10);
        double im00 = m11 * s;
        double im01 = -m01 * s;
        double im10 = -m10 * s;
        double im11 = m00 * s;
        double im20 = (m10 * m21 - m20 * m11) * s;
        double im21 = (m20 * m01 - m00 * m21) * s;
        double ndcX = (winX - viewport[0]) / viewport[2] * 2.0 - 1.0;
        double ndcY = (winY - viewport[1]) / viewport[3] * 2.0 - 1.0;
        dest.set(im00 * ndcX + im10 * ndcY + im20,
                im01 * ndcX + im11 * ndcY + im21);
        return dest;
    }

    public Vector2dc unprojectInv(double winX, double winY, int[] viewport, Vector2dc dest) {
        double ndcX = (winX - viewport[0]) / viewport[2] * 2.0 - 1.0;
        double ndcY = (winY - viewport[1]) / viewport[3] * 2.0 - 1.0;
        dest.set(m00 * ndcX + m10 * ndcY + m20,
                m01 * ndcX + m11 * ndcY + m21);
        return dest;
    }

    public Matrix3x2dc span(Vector2dc corner, Vector2dc xDir, Vector2dc yDir) {
        double s = 1.0 / (m00 * m11 - m01 * m10);
        double nm00 = m11 * s, nm01 = -m01 * s, nm10 = -m10 * s, nm11 = m00 * s;
        corner.set(-nm00 - nm10 + (m10 * m21 - m20 * m11) * s,
                -nm01 - nm11 + (m20 * m01 - m00 * m21) * s);
        xDir.set(2.0 * nm00, 2.0 * nm01);
        yDir.set(2.0 * nm10, 2.0 * nm11);
        return this;
    }

    public boolean testPoint(double x, double y) {
        double nxX = +m00, nxY = +m10, nxW = 1.0f + m20;
        double pxX = -m00, pxY = -m10, pxW = 1.0f - m20;
        double nyX = +m01, nyY = +m11, nyW = 1.0f + m21;
        double pyX = -m01, pyY = -m11, pyW = 1.0f - m21;
        return nxX * x + nxY * y + nxW >= 0 && pxX * x + pxY * y + pxW >= 0 &&
                nyX * x + nyY * y + nyW >= 0 && pyX * x + pyY * y + pyW >= 0;
    }

    public boolean testCircle(double x, double y, double r) {
        double invl;
        double nxX = +m00, nxY = +m10, nxW = 1.0f + m20;
        invl = 1.0 / Math.sqrt(nxX * nxX + nxY * nxY);
        nxX *= invl;
        nxY *= invl;
        nxW *= invl;
        double pxX = -m00, pxY = -m10, pxW = 1.0f - m20;
        invl = 1.0 / Math.sqrt(pxX * pxX + pxY * pxY);
        pxX *= invl;
        pxY *= invl;
        pxW *= invl;
        double nyX = +m01, nyY = +m11, nyW = 1.0f + m21;
        invl = 1.0 / Math.sqrt(nyX * nyX + nyY * nyY);
        nyX *= invl;
        nyY *= invl;
        nyW *= invl;
        double pyX = -m01, pyY = -m11, pyW = 1.0f - m21;
        invl = 1.0 / Math.sqrt(pyX * pyX + pyY * pyY);
        pyX *= invl;
        pyY *= invl;
        pyW *= invl;
        return nxX * x + nxY * y + nxW >= -r && pxX * x + pxY * y + pxW >= -r &&
                nyX * x + nyY * y + nyW >= -r && pyX * x + pyY * y + pyW >= -r;
    }

    public boolean testAar(double minX, double minY, double maxX, double maxY) {
        double nxX = +m00, nxY = +m10, nxW = 1.0f + m20;
        double pxX = -m00, pxY = -m10, pxW = 1.0f - m20;
        double nyX = +m01, nyY = +m11, nyW = 1.0f + m21;
        double pyX = -m01, pyY = -m11, pyW = 1.0f - m21;
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
