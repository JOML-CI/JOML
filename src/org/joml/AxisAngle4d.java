/*
 * (C) Copyright 2015 Kai Burjack

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
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Represents a 3D rotation of a given radians about an axis represented as an
 * unit 3D vector.
 * <p>
 * This class uses double-precision components.
 * </p>
 * 
 * @author Kai Burjack
 */
public class AxisAngle4d extends AxisAngle4dr implements Externalizable {

    private static final long serialVersionUID = 1L;

    /**
     * The angle in radians.
     */
    public double angle;
    /**
     * The x-component of the rotation axis.
     */
    public double x;
    /**
     * The y-component of the rotation axis.
     */
    public double y;
    /**
     * The z-component of the rotation axis.
     */
    public double z;

    /**
     * Create a new {@link AxisAngle4d} with zero rotation about <tt>(0, 0, 1)</tt>.
     */
    public AxisAngle4d() {
        z = 1.0;
    }

    /**
     * Create a new {@link AxisAngle4d} with the same values of <code>a</code>.
     * 
     * @param a
     *            the AngleAxis4dr to copy the values from
     */
    public AxisAngle4d(AxisAngle4dr a) {
        x = a.x();
        y = a.y();
        z = a.z();
        angle = (a.angle() < 0.0 ? 2.0 * Math.PI + a.angle() % (2.0 * Math.PI) : a.angle()) % (2.0 * Math.PI);
    }

    /**
     * Create a new {@link AxisAngle4d} with the same values of <code>a</code>.
     * 
     * @param a
     *            the AngleAxis4fr to copy the values from
     */
    public AxisAngle4d(AxisAngle4fr a) {
        x = a.x();
        y = a.y();
        z = a.z();
        angle = (a.angle() < 0.0 ? 2.0 * Math.PI + a.angle() % (2.0 * Math.PI) : a.angle()) % (2.0 * Math.PI);
    }

    /**
     * Create a new {@link AxisAngle4d} from the given {@link Quaternionfr}.
     * <p>
     * Reference: <a href=
     * "http://www.euclideanspace.com/maths/geometry/rotations/conversions/quaternionToAngle/"
     * >http://www.euclideanspace.com</a>
     * </p>
     * 
     * @param q
     *            the quaternion from which to create the new AngleAxis4f
     */
    public AxisAngle4d(Quaternionfr q) {
        double acos = Math.acos(q.w());
        double invSqrt = 1.0 / Math.sqrt(1.0 - q.w() * q.w());
        x = q.x() * invSqrt;
        y = q.y() * invSqrt;
        z = q.z() * invSqrt;
        angle = 2.0 * acos;
    }

    /**
     * Create a new {@link AxisAngle4d} from the given {@link Quaterniondr}.
     * <p>
     * Reference: <a href=
     * "http://www.euclideanspace.com/maths/geometry/rotations/conversions/quaternionToAngle/"
     * >http://www.euclideanspace.com</a>
     * </p>
     * 
     * @param q
     *            the quaternion from which to create the new AngleAxis4d
     */
    public AxisAngle4d(Quaterniondr q) {
        double acos = Math.acos(q.w());
        double invSqrt = 1.0 / Math.sqrt(1.0 - q.w() * q.w());
        x = q.x() * invSqrt;
        y = q.y() * invSqrt;
        z = q.z() * invSqrt;
        angle = 2.0 * acos;
    }

    /**
     * Create a new {@link AxisAngle4d} with the given values.
     *
     * @param angle
     *            the angle in radians
     * @param x
     *            the x-coordinate of the rotation axis
     * @param y
     *            the y-coordinate of the rotation axis
     * @param z
     *            the z-coordinate of the rotation axis
     */
    public AxisAngle4d(double angle, double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.angle = (angle < 0.0 ? 2.0 * Math.PI + angle % (2.0 * Math.PI) : angle) % (2.0 * Math.PI);
    }

    /**
     * Create a new {@link AxisAngle4d} with the given values.
     *
     * @param angle the angle in radians
     * @param v     the rotation axis as a {@link Vector3dr}
     */
    public AxisAngle4d(double angle, Vector3dr v) {
        this(angle, v.x(), v.y(), v.z());
    }

    /**
     * Create a new {@link AxisAngle4d} with the given values.
     *
     * @param angle the angle in radians
     * @param v     the rotation axis as a {@link Vector3fr}
     */
    public AxisAngle4d(double angle, Vector3fr v) {
        this(angle, v.x(), v.y(), v.z());
    }

    public double angle() {
        return angle;
    }

    public double x() {
        return x;
    }

    public double y() {
        return y;
    }

    public double z() {
        return z;
    }

    /**
     * Set this {@link AxisAngle4d} to the values of <code>a</code>.
     * 
     * @param a
     *            the AngleAxis4dr to copy the values from
     * @return this
     */
    public AxisAngle4d set(AxisAngle4dr a) {
        x = a.x();
        y = a.y();
        z = a.z();
        angle = (a.angle() < 0.0 ? 2.0 * Math.PI + a.angle() % (2.0 * Math.PI) : a.angle()) % (2.0 * Math.PI);
        return this;
    }

    /**
     * Set this {@link AxisAngle4d} to the values of <code>a</code>.
     * 
     * @param a
     *            the AngleAxis4fr to copy the values from
     * @return this
     */
    public AxisAngle4d set(AxisAngle4fr a) {
        x = a.x();
        y = a.y();
        z = a.z();
        angle = (a.angle() < 0.0 ? 2.0 * Math.PI + a.angle() % (2.0 * Math.PI) : a.angle()) % (2.0 * Math.PI);
        return this;
    }

    /**
     * Set this {@link AxisAngle4d} to the given values.
     * 
     * @param angle
     *            the angle in radians
     * @param x
     *            the x-coordinate of the rotation axis
     * @param y
     *            the y-coordinate of the rotation axis
     * @param z
     *            the z-coordinate of the rotation axis
     * @return this
     */
    public AxisAngle4d set(double angle, double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.angle = (angle < 0.0 ? 2.0 * Math.PI + angle % (2.0 * Math.PI) : angle) % (2.0 * Math.PI);
        return this;
    }

    /**
     * Set this {@link AxisAngle4d} to the given values.
     *
     * @param angle
     *            the angle in radians
     * @param v    
     *            the rotation axis as a {@link Vector3dr}
     * @return this
     */
    public AxisAngle4d set(double angle, Vector3dr v) {
        return set(angle, v.x(), v.y(), v.z());
    }

    /**
     * Set this {@link AxisAngle4d} to the given values.
     *
     * @param angle
     *            the angle in radians
     * @param v    
     *            the rotation axis as a {@link Vector3fr}
     * @return this
     */
    public AxisAngle4d set(double angle, Vector3fr v) {
        return set(angle, v.x(), v.y(), v.z());
    }

    /**
     * Set this {@link AxisAngle4d} to be equivalent to the given
     * {@link Quaternionfr}.
     * 
     * @param q
     *            the quaternion to set this AngleAxis4d from
     * @return this
     */
    public AxisAngle4d set(Quaternionfr q) {
        double acos = Math.acos(q.w());
        double invSqrt = 1.0 / Math.sqrt(1.0 - q.w() * q.w());
        this.x = q.x() * invSqrt;
        this.y = q.y() * invSqrt;
        this.z = q.z() * invSqrt;
        this.angle = 2.0f * acos;
        return this;
    }

    /**
     * Set this {@link AxisAngle4d} to be equivalent to the given
     * {@link Quaterniondr}.
     * 
     * @param q
     *            the quaternion to set this AngleAxis4d from
     * @return this
     */
    public AxisAngle4d set(Quaterniondr q) {
        double acos = Math.acos(q.w());
        double invSqrt = 1.0 / Math.sqrt(1.0 - q.w() * q.w());
        this.x = q.x() * invSqrt;
        this.y = q.y() * invSqrt;
        this.z = q.z() * invSqrt;
        this.angle = 2.0f * acos;
        return this;
    }

    /**
     * Set this {@link AxisAngle4d} to be equivalent to the rotation 
     * of the given {@link Matrix3fr}.
     * 
     * @param m
     *            the Matrix3fr to set this AngleAxis4d from
     * @return this
     */
    public AxisAngle4d set(Matrix3fr m) {
        double cos = (m.m00() + m.m11() + m.m22() - 1.0)*0.5;
        x = m.m12() - m.m21();
        y = m.m20() - m.m02();
        z = m.m01() - m.m10();
        double sin = 0.5*Math.sqrt(x*x + y*y + z*z);
        angle = Math.atan2(sin, cos);
        return this;
    }

    /**
     * Set this {@link AxisAngle4d} to be equivalent to the rotation 
     * of the given {@link Matrix3dr}.
     * 
     * @param m
     *            the Matrix3dr to set this AngleAxis4d from
     * @return this
     */
    public AxisAngle4d set(Matrix3dr m) {
        double cos = (m.m00() + m.m11() + m.m22() - 1.0)*0.5;
        x = m.m12() - m.m21();
        y = m.m20() - m.m02();
        z = m.m01() - m.m10();
        double sin = 0.5*Math.sqrt(x*x + y*y + z*z);
        angle = Math.atan2(sin, cos);
        return this;
    }

    /**
     * Set this {@link AxisAngle4d} to be equivalent to the rotational component 
     * of the given {@link Matrix4fr}.
     * 
     * @param m
     *            the Matrix4fr to set this AngleAxis4d from
     * @return this
     */
    public AxisAngle4d set(Matrix4fr m) {
        double cos = (m.m00() + m.m11() + m.m22() - 1.0)*0.5;
        x = m.m12() - m.m21();
        y = m.m20() - m.m02();
        z = m.m01() - m.m10();
        double sin = 0.5*Math.sqrt(x*x + y*y + z*z);
        angle = Math.atan2(sin, cos);
        return this;
    }

    /**
     * Set this {@link AxisAngle4d} to be equivalent to the rotational component 
     * of the given {@link Matrix4dr}.
     * 
     * @param m
     *            the Matrix4dr to set this AngleAxis4d from
     * @return this
     */
    public AxisAngle4d set(Matrix4dr m) {
        double cos = (m.m00() + m.m11() + m.m22() - 1.0)*0.5;
        x = m.m12() - m.m21();
        y = m.m20() - m.m02();
        z = m.m01() - m.m10();
        double sin = 0.5*Math.sqrt(x*x + y*y + z*z);
        angle = Math.atan2(sin, cos);
        return this;
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeDouble(angle);
        out.writeDouble(x);
        out.writeDouble(y);
        out.writeDouble(z);
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        angle = in.readDouble();
        x = in.readDouble();
        y = in.readDouble();
        z = in.readDouble();
    }

    /**
     * Normalize the axis vector.
     * 
     * @return this
     */
    public AxisAngle4d normalize() {
        double invLength = 1.0 / Math.sqrt(x * x + y * y + z * z);
        x *= invLength;
        y *= invLength;
        z *= invLength;
        return this;
    }

    /**
     * Increase the rotation angle by the given amount.
     * <p>
     * This method also takes care of wrapping around.
     * </p>
     * 
     * @param ang
     *          the angle increase
     * @return this
     */
    public AxisAngle4d rotate(double ang) {
        angle += ang;
        angle = (angle < 0.0 ? 2.0 * Math.PI + angle % (2.0 * Math.PI) : angle) % (2.0 * Math.PI);
        return this;
    }

}
