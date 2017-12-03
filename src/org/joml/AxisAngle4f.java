/*
 * (C) Copyright 2015-2017 Kai Burjack

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

import org.joml.api.AxisAngle4fc;
import org.joml.api.matrix.*;
import org.joml.api.quaternion.IQuaterniond;
import org.joml.api.quaternion.IQuaternionf;
import org.joml.api.quaternion.Quaterniondc;
import org.joml.api.quaternion.Quaternionfc;
import org.joml.api.vector.IVector3f;
import org.joml.api.vector.IVector4f;
import org.joml.api.vector.Vector3fc;
import org.joml.api.vector.Vector4fc;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Represents a 3D rotation of a given radians about an axis represented as an unit 3D vector.
 * <p>
 * This class uses single-precision components.
 *
 * @author Kai Burjack
 */
public class AxisAngle4f extends AxisAngle4fc implements Externalizable {

    private static final long serialVersionUID = 1L;

    /**
     * The angle in radians.
     */
    public float angle;

    /**
     * The x-component of the rotation axis.
     */
    public float x;

    /**
     * The y-component of the rotation axis.
     */
    public float y;

    /**
     * The z-component of the rotation axis.
     */
    public float z;

    /**
     * Create a new {@link AxisAngle4f} with zero rotation about <tt>(0, 0, 1)</tt>.
     */
    public AxisAngle4f() {
        z = 1.0f;
    }

    /**
     * Create a new {@link AxisAngle4f} with the same values of <code>a</code>.
     *
     * @param a the AngleAxis4f to copy the values from
     */
    public AxisAngle4f(AxisAngle4fc a) {
        x = a.x();
        y = a.y();
        z = a.z();
        angle = (float) ((a.angle() < 0.0 ? Math.PI + Math.PI + a.angle() % (Math.PI + Math.PI) : a.angle()) % (Math.PI + Math.PI));
    }

    /**
     * Create a new {@link AxisAngle4f} from the given {@link Quaternionfc}.
     * <p>
     * Reference: <a href= "http://www.euclideanspace.com/maths/geometry/rotations/conversions/quaternionToAngle/"
     * >http://www.euclideanspace.com</a>
     *
     * @param q the quaternion from which to create the new AngleAxis4f
     */
    public AxisAngle4f(IQuaternionf q) {
        float acos = (float) safeAcos(q.w());
        float invSqrt = (float) (1.0 / Math.sqrt(1.0 - q.w() * q.w()));
        this.x = q.x() * invSqrt;
        this.y = q.y() * invSqrt;
        this.z = q.z() * invSqrt;
        this.angle = acos + acos;
    }

    /**
     * Create a new {@link AxisAngle4f} with the given values.
     *
     * @param angle the angle in radians
     * @param x     the x-coordinate of the rotation axis
     * @param y     the y-coordinate of the rotation axis
     * @param z     the z-coordinate of the rotation axis
     */
    public AxisAngle4f(float angle, float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.angle = (float) ((angle < 0.0 ? Math.PI + Math.PI + angle % (Math.PI + Math.PI) : angle) % (Math.PI + Math.PI));
    }

    /**
     * Create a new {@link AxisAngle4f} with the given values.
     *
     * @param angle the angle in radians
     * @param v     the rotation axis as a {@link Vector3f}
     */
    public AxisAngle4f(float angle, IVector3f v) {
        this(angle, v.x(), v.y(), v.z());
    }

    /**
     * @param v
     * @return
     */
    private static double safeAcos(double v) {
        if (v < -1.0)
            return Math.PI;
        else if (v > +1.0)
            return 0.0;
        else
            return Math.acos(v);
    }

    @Override
    public float x() {
        return x;
    }

    @Override
    public float y() {
        return y;
    }

    @Override
    public float z() {
        return z;
    }

    @Override
    public float angle() {
        return angle;
    }

    @Override
    public AxisAngle4fc set(AxisAngle4fc a) {
        x = a.x();
        y = a.y();
        z = a.z();
        angle = a.angle();
        angle = (float) ((angle < 0.0 ? Math.PI + Math.PI + angle % (Math.PI + Math.PI) : angle) % (Math.PI + Math.PI));
        return this;
    }

    @Override
    public AxisAngle4fc set(float angle, float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.angle = (float) ((angle < 0.0 ? Math.PI + Math.PI + angle % (Math.PI + Math.PI) : angle) % (Math.PI + Math.PI));
        return this;
    }

    @Override
    public AxisAngle4fc set(float angle, IVector3f v) {
        return set(angle, v.x(), v.y(), v.z());
    }

    @Override
    public AxisAngle4fc set(IQuaternionf q) {
        double acos = safeAcos(q.w());
        double invSqrt = 1.0 / Math.sqrt(1.0 - q.w() * q.w());
        this.x = (float) (q.x() * invSqrt);
        this.y = (float) (q.y() * invSqrt);
        this.z = (float) (q.z() * invSqrt);
        this.angle = (float) (acos + acos);
        return this;
    }

    @Override
    public AxisAngle4fc set(IQuaterniond q) {
        double acos = safeAcos(q.w());
        double invSqrt = 1.0 / Math.sqrt(1.0 - q.w() * q.w());
        this.x = (float) (q.x() * invSqrt);
        this.y = (float) (q.y() * invSqrt);
        this.z = (float) (q.z() * invSqrt);
        this.angle = (float) (acos + acos);
        return this;
    }

    @Override
    public AxisAngle4fc set(IMatrix3f m) {
        double nm00 = m.m00(), nm01 = m.m01(), nm02 = m.m02();
        double nm10 = m.m10(), nm11 = m.m11(), nm12 = m.m12();
        double nm20 = m.m20(), nm21 = m.m21(), nm22 = m.m22();
        double lenX = 1.0 / Math.sqrt(m.m00() * m.m00() + m.m01() * m.m01() + m.m02() * m.m02());
        double lenY = 1.0 / Math.sqrt(m.m10() * m.m10() + m.m11() * m.m11() + m.m12() * m.m12());
        double lenZ = 1.0 / Math.sqrt(m.m20() * m.m20() + m.m21() * m.m21() + m.m22() * m.m22());
        nm00 *= lenX;
        nm01 *= lenX;
        nm02 *= lenX;
        nm10 *= lenY;
        nm11 *= lenY;
        nm12 *= lenY;
        nm20 *= lenZ;
        nm21 *= lenZ;
        nm22 *= lenZ;
        double epsilon = 1E-4;
        if ((Math.abs(nm10 - nm01) < epsilon) && (Math.abs(nm20 - nm02) < epsilon) && (Math.abs(nm21 - nm12) < epsilon)) {
            angle = (float) Math.PI;
            double xx = (nm00 + 1) / 2;
            double yy = (nm11 + 1) / 2;
            double zz = (nm22 + 1) / 2;
            double xy = (nm10 + nm01) / 4;
            double xz = (nm20 + nm02) / 4;
            double yz = (nm21 + nm12) / 4;
            if ((xx > yy) && (xx > zz)) {
                x = (float) Math.sqrt(xx);
                y = (float) (xy / x);
                z = (float) (xz / x);
            } else if (yy > zz) {
                y = (float) Math.sqrt(yy);
                x = (float) (xy / y);
                z = (float) (yz / y);
            } else {
                z = (float) Math.sqrt(zz);
                x = (float) (xz / z);
                y = (float) (yz / z);
            }
            return this;
        }
        double s = Math.sqrt((nm12 - nm21) * (nm12 - nm21) + (nm20 - nm02) * (nm20 - nm02) + (nm01 - nm10) * (nm01 - nm10));
        angle = (float) safeAcos((nm00 + nm11 + nm22 - 1) / 2);
        x = (float) ((nm12 - nm21) / s);
        y = (float) ((nm20 - nm02) / s);
        z = (float) ((nm01 - nm10) / s);
        return this;
    }

    @Override
    public AxisAngle4fc set(IMatrix3d m) {
        double nm00 = m.m00(), nm01 = m.m01(), nm02 = m.m02();
        double nm10 = m.m10(), nm11 = m.m11(), nm12 = m.m12();
        double nm20 = m.m20(), nm21 = m.m21(), nm22 = m.m22();
        double lenX = 1.0 / Math.sqrt(m.m00() * m.m00() + m.m01() * m.m01() + m.m02() * m.m02());
        double lenY = 1.0 / Math.sqrt(m.m10() * m.m10() + m.m11() * m.m11() + m.m12() * m.m12());
        double lenZ = 1.0 / Math.sqrt(m.m20() * m.m20() + m.m21() * m.m21() + m.m22() * m.m22());
        nm00 *= lenX;
        nm01 *= lenX;
        nm02 *= lenX;
        nm10 *= lenY;
        nm11 *= lenY;
        nm12 *= lenY;
        nm20 *= lenZ;
        nm21 *= lenZ;
        nm22 *= lenZ;
        double epsilon = 1E-4;
        if ((Math.abs(nm10 - nm01) < epsilon) && (Math.abs(nm20 - nm02) < epsilon) && (Math.abs(nm21 - nm12) < epsilon)) {
            angle = (float) Math.PI;
            double xx = (nm00 + 1) / 2;
            double yy = (nm11 + 1) / 2;
            double zz = (nm22 + 1) / 2;
            double xy = (nm10 + nm01) / 4;
            double xz = (nm20 + nm02) / 4;
            double yz = (nm21 + nm12) / 4;
            if ((xx > yy) && (xx > zz)) {
                x = (float) Math.sqrt(xx);
                y = (float) (xy / x);
                z = (float) (xz / x);
            } else if (yy > zz) {
                y = (float) Math.sqrt(yy);
                x = (float) (xy / y);
                z = (float) (yz / y);
            } else {
                z = (float) Math.sqrt(zz);
                x = (float) (xz / z);
                y = (float) (yz / z);
            }
            return this;
        }
        double s = Math.sqrt((nm12 - nm21) * (nm12 - nm21) + (nm20 - nm02) * (nm20 - nm02) + (nm01 - nm10) * (nm01 - nm10));
        angle = (float) safeAcos((nm00 + nm11 + nm22 - 1) / 2);
        x = (float) ((nm12 - nm21) / s);
        y = (float) ((nm20 - nm02) / s);
        z = (float) ((nm01 - nm10) / s);
        return this;
    }

    @Override
    public AxisAngle4fc set(IMatrix4f m) {
        double nm00 = m.m00(), nm01 = m.m01(), nm02 = m.m02();
        double nm10 = m.m10(), nm11 = m.m11(), nm12 = m.m12();
        double nm20 = m.m20(), nm21 = m.m21(), nm22 = m.m22();
        double lenX = 1.0 / Math.sqrt(m.m00() * m.m00() + m.m01() * m.m01() + m.m02() * m.m02());
        double lenY = 1.0 / Math.sqrt(m.m10() * m.m10() + m.m11() * m.m11() + m.m12() * m.m12());
        double lenZ = 1.0 / Math.sqrt(m.m20() * m.m20() + m.m21() * m.m21() + m.m22() * m.m22());
        nm00 *= lenX;
        nm01 *= lenX;
        nm02 *= lenX;
        nm10 *= lenY;
        nm11 *= lenY;
        nm12 *= lenY;
        nm20 *= lenZ;
        nm21 *= lenZ;
        nm22 *= lenZ;
        double epsilon = 1E-4;
        if ((Math.abs(nm10 - nm01) < epsilon) && (Math.abs(nm20 - nm02) < epsilon) && (Math.abs(nm21 - nm12) < epsilon)) {
            angle = (float) Math.PI;
            double xx = (nm00 + 1) / 2;
            double yy = (nm11 + 1) / 2;
            double zz = (nm22 + 1) / 2;
            double xy = (nm10 + nm01) / 4;
            double xz = (nm20 + nm02) / 4;
            double yz = (nm21 + nm12) / 4;
            if ((xx > yy) && (xx > zz)) {
                x = (float) Math.sqrt(xx);
                y = (float) (xy / x);
                z = (float) (xz / x);
            } else if (yy > zz) {
                y = (float) Math.sqrt(yy);
                x = (float) (xy / y);
                z = (float) (yz / y);
            } else {
                z = (float) Math.sqrt(zz);
                x = (float) (xz / z);
                y = (float) (yz / z);
            }
            return this;
        }
        double s = Math.sqrt((nm12 - nm21) * (nm12 - nm21) + (nm20 - nm02) * (nm20 - nm02) + (nm01 - nm10) * (nm01 - nm10));
        angle = (float) safeAcos((nm00 + nm11 + nm22 - 1) / 2);
        x = (float) ((nm12 - nm21) / s);
        y = (float) ((nm20 - nm02) / s);
        z = (float) ((nm01 - nm10) / s);
        return this;
    }

    @Override
    public AxisAngle4fc set(IMatrix4x3f m) {
        double nm00 = m.m00(), nm01 = m.m01(), nm02 = m.m02();
        double nm10 = m.m10(), nm11 = m.m11(), nm12 = m.m12();
        double nm20 = m.m20(), nm21 = m.m21(), nm22 = m.m22();
        double lenX = 1.0 / Math.sqrt(m.m00() * m.m00() + m.m01() * m.m01() + m.m02() * m.m02());
        double lenY = 1.0 / Math.sqrt(m.m10() * m.m10() + m.m11() * m.m11() + m.m12() * m.m12());
        double lenZ = 1.0 / Math.sqrt(m.m20() * m.m20() + m.m21() * m.m21() + m.m22() * m.m22());
        nm00 *= lenX;
        nm01 *= lenX;
        nm02 *= lenX;
        nm10 *= lenY;
        nm11 *= lenY;
        nm12 *= lenY;
        nm20 *= lenZ;
        nm21 *= lenZ;
        nm22 *= lenZ;
        double epsilon = 1E-4;
        if ((Math.abs(nm10 - nm01) < epsilon) && (Math.abs(nm20 - nm02) < epsilon) && (Math.abs(nm21 - nm12) < epsilon)) {
            angle = (float) Math.PI;
            double xx = (nm00 + 1) / 2;
            double yy = (nm11 + 1) / 2;
            double zz = (nm22 + 1) / 2;
            double xy = (nm10 + nm01) / 4;
            double xz = (nm20 + nm02) / 4;
            double yz = (nm21 + nm12) / 4;
            if ((xx > yy) && (xx > zz)) {
                x = (float) Math.sqrt(xx);
                y = (float) (xy / x);
                z = (float) (xz / x);
            } else if (yy > zz) {
                y = (float) Math.sqrt(yy);
                x = (float) (xy / y);
                z = (float) (yz / y);
            } else {
                z = (float) Math.sqrt(zz);
                x = (float) (xz / z);
                y = (float) (yz / z);
            }
            return this;
        }
        double s = Math.sqrt((nm12 - nm21) * (nm12 - nm21) + (nm20 - nm02) * (nm20 - nm02) + (nm01 - nm10) * (nm01 - nm10));
        angle = (float) safeAcos((nm00 + nm11 + nm22 - 1) / 2);
        x = (float) ((nm12 - nm21) / s);
        y = (float) ((nm20 - nm02) / s);
        z = (float) ((nm01 - nm10) / s);
        return this;
    }

    @Override
    public AxisAngle4fc set(IMatrix4d m) {
        double nm00 = m.m00(), nm01 = m.m01(), nm02 = m.m02();
        double nm10 = m.m10(), nm11 = m.m11(), nm12 = m.m12();
        double nm20 = m.m20(), nm21 = m.m21(), nm22 = m.m22();
        double lenX = 1.0 / Math.sqrt(m.m00() * m.m00() + m.m01() * m.m01() + m.m02() * m.m02());
        double lenY = 1.0 / Math.sqrt(m.m10() * m.m10() + m.m11() * m.m11() + m.m12() * m.m12());
        double lenZ = 1.0 / Math.sqrt(m.m20() * m.m20() + m.m21() * m.m21() + m.m22() * m.m22());
        nm00 *= lenX;
        nm01 *= lenX;
        nm02 *= lenX;
        nm10 *= lenY;
        nm11 *= lenY;
        nm12 *= lenY;
        nm20 *= lenZ;
        nm21 *= lenZ;
        nm22 *= lenZ;
        double epsilon = 1E-4;
        if ((Math.abs(nm10 - nm01) < epsilon) && (Math.abs(nm20 - nm02) < epsilon) && (Math.abs(nm21 - nm12) < epsilon)) {
            angle = (float) Math.PI;
            double xx = (nm00 + 1) / 2;
            double yy = (nm11 + 1) / 2;
            double zz = (nm22 + 1) / 2;
            double xy = (nm10 + nm01) / 4;
            double xz = (nm20 + nm02) / 4;
            double yz = (nm21 + nm12) / 4;
            if ((xx > yy) && (xx > zz)) {
                x = (float) Math.sqrt(xx);
                y = (float) (xy / x);
                z = (float) (xz / x);
            } else if (yy > zz) {
                y = (float) Math.sqrt(yy);
                x = (float) (xy / y);
                z = (float) (yz / y);
            } else {
                z = (float) Math.sqrt(zz);
                x = (float) (xz / z);
                y = (float) (yz / z);
            }
            return this;
        }
        double s = Math.sqrt((nm12 - nm21) * (nm12 - nm21) + (nm20 - nm02) * (nm20 - nm02) + (nm01 - nm10) * (nm01 - nm10));
        angle = (float) safeAcos((nm00 + nm11 + nm22 - 1) / 2);
        x = (float) ((nm12 - nm21) / s);
        y = (float) ((nm20 - nm02) / s);
        z = (float) ((nm01 - nm10) / s);
        return this;
    }

    @Override
    public Quaternionfc get(Quaternionfc q) {
        return q.set(this);
    }

    @Override
    public Quaterniondc get(Quaterniondc q) {
        return q.set(this);
    }

    @Override
    public Matrix4fc get(Matrix4fc m) {
        return m.set(this);
    }

    @Override
    public Matrix3fc get(Matrix3fc m) {
        return m.set(this);
    }

    @Override
    public Matrix4dc get(Matrix4dc m) {
        return m.set(this);
    }

    @Override
    public Matrix3dc get(Matrix3dc m) {
        return m.set(this);
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeFloat(angle);
        out.writeFloat(x);
        out.writeFloat(y);
        out.writeFloat(z);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        angle = in.readFloat();
        x = in.readFloat();
        y = in.readFloat();
        z = in.readFloat();
    }

    @Override
    public AxisAngle4fc normalize() {
        float invLength = (float) (1.0 / Math.sqrt(x * x + y * y + z * z));
        x *= invLength;
        y *= invLength;
        z *= invLength;
        return this;
    }

    @Override
    public AxisAngle4fc rotate(float ang) {
        angle += ang;
        angle = (float) ((angle < 0.0 ? Math.PI + Math.PI + angle % (Math.PI + Math.PI) : angle) % (Math.PI + Math.PI));
        return this;
    }

    @Override
    public Vector3fc transform(Vector3fc v) {
        return transform(v, v);
    }

    @Override
    public Vector3fc transform(IVector3f v, Vector3fc dest) {
        double sin = Math.sin(angle);
        double cos = Math.cosFromSin(sin, angle);
        float dot = x * v.x() + y * v.y() + z * v.z();
        dest.set((float) (v.x() * cos + sin * (y * v.z() - z * v.y()) + (1.0 - cos) * dot * x),
                (float) (v.y() * cos + sin * (z * v.x() - x * v.z()) + (1.0 - cos) * dot * y),
                (float) (v.z() * cos + sin * (x * v.y() - y * v.x()) + (1.0 - cos) * dot * z));
        return dest;
    }

    @Override
    public Vector4fc transform(Vector4fc v) {
        return transform(v, v);
    }

    @Override
    public Vector4fc transform(IVector4f v, Vector4fc dest) {
        double sin = Math.sin(angle);
        double cos = Math.cosFromSin(sin, angle);
        float dot = x * v.x() + y * v.y() + z * v.z();
        dest.set((float) (v.x() * cos + sin * (y * v.z() - z * v.y()) + (1.0 - cos) * dot * x),
                (float) (v.y() * cos + sin * (z * v.x() - x * v.z()) + (1.0 - cos) * dot * y),
                (float) (v.z() * cos + sin * (x * v.y() - y * v.x()) + (1.0 - cos) * dot * z),
                dest.w());
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
        return "(" + formatter.format(x) + formatter.format(y) + formatter.format(z) + " <|" + formatter.format(angle) + " )"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        float nangle = (float) ((angle < 0.0 ? Math.PI + Math.PI + angle % (Math.PI + Math.PI) : angle) % (Math.PI + Math.PI));
        result = prime * result + Float.floatToIntBits(nangle);
        result = prime * result + Float.floatToIntBits(x);
        result = prime * result + Float.floatToIntBits(y);
        result = prime * result + Float.floatToIntBits(z);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof AxisAngle4fc))
            return false;
        AxisAngle4fc other = (AxisAngle4fc) obj;
        float nangle = (float) ((angle < 0.0 ? Math.PI + Math.PI + angle % (Math.PI + Math.PI) : angle) % (Math.PI + Math.PI));
        float nangleOther = (float) ((other.angle() < 0.0 ? Math.PI + Math.PI + other.angle() % (Math.PI + Math.PI) : other.angle()) % (Math.PI + Math.PI));
        if (Float.floatToIntBits(nangle) != Float.floatToIntBits(nangleOther))
            return false;
        if (Float.floatToIntBits(x) != Float.floatToIntBits(other.x()))
            return false;
        if (Float.floatToIntBits(y) != Float.floatToIntBits(other.y()))
            return false;
        if (Float.floatToIntBits(z) != Float.floatToIntBits(other.z()))
            return false;
        return true;
    }
}
