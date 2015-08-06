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

/**
 * Represents a 3D rotation of a given radians about an axis represented as an
 * unit 3D vector.
 *
 * @author Kai Burjack
 * @author Sri Harsha Chilakapati
 */
public class AxisAngle4f {

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
     * @param a
     *            the AngleAxis4f to copy the values from
     */
    public AxisAngle4f(AxisAngle4f a) {
        x = a.x;
        y = a.y;
        z = a.z;
        angle = (float) ((angle < 0.0 ? 2.0 * Math.PI + angle % (2.0 * Math.PI) : angle) % (2.0 * Math.PI));
    }

    /**
     * Create a new {@link AxisAngle4f} from the given {@link Quaternionf}.
     * <p>
     * Reference: <a href=
     * "http://www.euclideanspace.com/maths/geometry/rotations/conversions/quaternionToAngle/"
     * >http://www.euclideanspace.com</a>
     *
     * @param q
     *            the quaternion from which to create the new AngleAxis4f
     */
    public AxisAngle4f(Quaternionf q) {
        float acos = (float) Math.acos(q.w);
        float sqrt = (float) Math.sqrt(1.0 - q.w * q.w);
        this.x = q.x / sqrt;
        this.y = q.y / sqrt;
        this.z = q.z / sqrt;
        this.angle = (float) 2.0 * acos;
    }

    /**
     * Create a new {@link AxisAngle4f} with the given values.
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
    public AxisAngle4f(float angle, float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.angle = (float) ((angle < 0.0 ? 2.0 * Math.PI + angle % (2.0 * Math.PI) : angle) % (2.0 * Math.PI));
    }

    /**
     * Create a new {@link AxisAngle4f} with the given values.
     *
     * @param angle the angle in radians
     * @param v     the rotation axis as a {@link Vector3f}
     */
    public AxisAngle4f(float angle, Vector3f v) {
        this(angle, v.x, v.y, v.z);
    }

    /**
     * Set this {@link AxisAngle4f} to the values of <code>a</code>.
     *
     * @param a
     *            the AngleAxis4f to copy the values from
     * @return this
     */
    public AxisAngle4f set(AxisAngle4f a) {
        x = a.x;
        y = a.y;
        z = a.z;
        angle = (float) ((angle < 0.0 ? 2.0 * Math.PI + angle % (2.0 * Math.PI) : angle) % (2.0 * Math.PI));
        return this;
    }

    /**
     * Set this {@link AxisAngle4f} to the given values.
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
    public AxisAngle4f set(float angle, float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.angle = (float) ((angle < 0.0 ? 2.0 * Math.PI + angle % (2.0 * Math.PI) : angle) % (2.0 * Math.PI));
        return this;
    }

    /**
     * Set this {@link AxisAngle4f} to the given values.
     *
     * @param angle
     *            the angle in radians
     * @param v
     *            the rotation axis as a {@link Vector3f}
     * @return this
     */
    public AxisAngle4f set(float angle, Vector3f v) {
        return set(angle, v.x, v.y, v.z);
    }

    /**
     * Set this {@link AxisAngle4f} to be equivalent to the given
     * {@link Quaternionf}.
     *
     * @param q
     *            the quaternion to set this AngleAxis4f from
     * @return this
     */
    public AxisAngle4f set(Quaternionf q) {
        double acos = Math.acos(q.w);
        double sqrt = Math.sqrt(1.0 - q.w * q.w);
        this.x = (float) (q.x / sqrt);
        this.y = (float) (q.y / sqrt);
        this.z = (float) (q.z / sqrt);
        this.angle = (float) (2.0f * acos);
        return this;
    }

    /**
     * Set this {@link AxisAngle4f} to be equivalent to the given
     * {@link Quaterniond}.
     *
     * @param q
     *            the quaternion to set this AngleAxis4f from
     * @return this
     */
    public AxisAngle4f set(Quaterniond q) {
        double acos = Math.acos(q.w);
        double sqrt = Math.sqrt(1.0 - q.w * q.w);
        this.x = (float) (q.x / sqrt);
        this.y = (float) (q.y / sqrt);
        this.z = (float) (q.z / sqrt);
        this.angle = (float) (2.0f * acos);
        return this;
    }

    /**
     * Set this {@link AxisAngle4f} to be equivalent to the rotation
     * of the given {@link Matrix3f}.
     *
     * @param m
     *            the Matrix3f to set this AngleAxis4f from
     * @return this
     */
    public AxisAngle4f set(Matrix3f m) {
        double cos = (m.m00 + m.m11 + m.m22 - 1.0)*0.5;
        x = m.m12 - m.m21;
        y = m.m20 - m.m02;
        z = m.m01 - m.m10;
        double sin = 0.5*Math.sqrt(x*x + y*y + z*z);
        angle = (float) Math.atan2(sin, cos);
        return this;
    }

    /**
     * Set this {@link AxisAngle4f} to be equivalent to the rotation
     * of the given {@link Matrix3d}.
     *
     * @param m
     *            the Matrix3d to set this AngleAxis4f from
     * @return this
     */
    public AxisAngle4f set(Matrix3d m) {
        double cos = (m.m00 + m.m11 + m.m22 - 1.0)*0.5;
        x = (float) (m.m12 - m.m21);
        y = (float) (m.m20 - m.m02);
        z = (float) (m.m01 - m.m10);
        double sin = 0.5*Math.sqrt(x*x + y*y + z*z);
        angle = (float) Math.atan2(sin, cos);
        return this;
    }

    /**
     * Set this {@link AxisAngle4f} to be equivalent to the rotational component
     * of the given {@link Matrix4f}.
     *
     * @param m
     *            the Matrix4f to set this AngleAxis4f from
     * @return this
     */
    public AxisAngle4f set(Matrix4f m) {
        double cos = (m.m00 + m.m11 + m.m22 - 1.0)*0.5;
        x = m.m12 - m.m21;
        y = m.m20 - m.m02;
        z = m.m01 - m.m10;
        double sin = 0.5*Math.sqrt(x*x + y*y + z*z);
        angle = (float) Math.atan2(sin, cos);
        return this;
    }

    /**
     * Set this {@link AxisAngle4f} to be equivalent to the rotational component
     * of the given {@link Matrix4d}.
     *
     * @param m
     *            the Matrix4d to set this AngleAxis4f from
     * @return this
     */
    public AxisAngle4f set(Matrix4d m) {
        double cos = (m.m00 + m.m11 + m.m22 - 1.0)*0.5;
        x = (float) (m.m12 - m.m21);
        y = (float) (m.m20 - m.m02);
        z = (float) (m.m01 - m.m10);
        double sin = 0.5*Math.sqrt(x*x + y*y + z*z);
        angle = (float) Math.atan2(sin, cos);
        return this;
    }

    /**
     * Set the given {@link Quaternionf} to be equivalent to this {@link AxisAngle4f} rotation.
     *
     * @see Quaternionf#set(AxisAngle4f)
     *
     * @param q
     *          the quaternion to set
     * @return this
     */
    public AxisAngle4f get(Quaternionf q) {
        q.set(this);
        return this;
    }

    /**
     * Set the given {@link Quaterniond} to be equivalent to this {@link AxisAngle4f} rotation.
     *
     * @see Quaterniond#set(AxisAngle4f)
     *
     * @param q
     *          the quaternion to set
     * @return this
     */
    public AxisAngle4f get(Quaterniond q) {
        q.set(this);
        return this;
    }

    /**
     * Set the given {@link Matrix4f} to a rotation transformation equivalent to this {@link AxisAngle4f}.
     *
     * @see Matrix4f#set(AxisAngle4f)
     *
     * @param m
     *          the matrix to set
     * @return this
     */
    public AxisAngle4f get(Matrix4f m) {
        m.set(this);
        return this;
    }

    /**
     * Set the given {@link Matrix3f} to a rotation transformation equivalent to this {@link AxisAngle4f}.
     *
     * @see Matrix3f#set(AxisAngle4f)
     *
     * @param m
     *          the matrix to set
     * @return this
     */
    public AxisAngle4f get(Matrix3f m) {
        m.set(this);
        return this;
    }

    /**
     * Set the given {@link Matrix4d} to a rotation transformation equivalent to this {@link AxisAngle4f}.
     *
     * @see Matrix4f#set(AxisAngle4f)
     *
     * @param m
     *          the matrix to set
     * @return this
     */
    public AxisAngle4f get(Matrix4d m) {
        m.set(this);
        return this;
    }

    /**
     * Set the given {@link Matrix3d} to a rotation transformation equivalent to this {@link AxisAngle4f}.
     *
     * @see Matrix3f#set(AxisAngle4f)
     *
     * @param m
     *          the matrix to set
     * @return this
     */
    public AxisAngle4f get(Matrix3d m) {
        m.set(this);
        return this;
    }

    /**
     * Normalize the axis vector.
     *
     * @return this
     */
    public AxisAngle4f normalize() {
        float length = (float) Math.sqrt(x * x + y * y + z * z);
        x /= length;
        y /= length;
        z /= length;
        return this;
    }

    /**
     * Increase the rotation angle by the given amount.
     * <p>
     * This method also takes care of wrapping around.
     *
     * @param ang
     *          the angle increase
     * @return this
     */
    public AxisAngle4f rotate(float ang) {
        angle += ang;
        angle = (float) ((angle < 0.0 ? 2.0 * Math.PI + angle % (2.0 * Math.PI) : angle) % (2.0 * Math.PI));
        return this;
    }

    /**
     * Transform the given vector by the rotation transformation described by this {@link AxisAngle4f}.
     *
     * @param v
     *          the vector to transform
     * @return this
     */
    public AxisAngle4f transform(Vector3f v) {
        return transform(v, v);
    }

    /**
     * Transform the given vector by the rotation transformation described by this {@link AxisAngle4f}
     * and store the result in <code>dest</code>.
     *
     * @param v
     *          the vector to transform
     * @param dest
     *          will hold the result
     * @return this
     */
    public AxisAngle4f transform(Vector3f v, Vector3f dest) {
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);
        float dot = x * v.x + y * v.y + z * v.z;
        dest.set((float) (v.x * cos + sin * (y * v.z - z * v.y) + (1.0 - cos) * dot * x),
                 (float) (v.y * cos + sin * (z * v.x - x * v.z) + (1.0 - cos) * dot * y),
                 (float) (v.z * cos + sin * (x * v.y - y * v.x) + (1.0 - cos) * dot * z));
        return this;
    }

    /**
     * Transform the given vector by the rotation transformation described by this {@link AxisAngle4f}.
     *
     * @param v
     *          the vector to transform
     * @return this
     */
    public AxisAngle4f transform(Vector4f v) {
        return transform(v, v);
    }

    /**
     * Transform the given vector by the rotation transformation described by this {@link AxisAngle4f}
     * and store the result in <code>dest</code>.
     *
     * @param v
     *          the vector to transform
     * @param dest
     *          will hold the result
     * @return this
     */
    public AxisAngle4f transform(Vector4f v, Vector4f dest) {
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);
        float dot = x * v.x + y * v.y + z * v.z;
        dest.set((float) (v.x * cos + sin * (y * v.z - z * v.y) + (1.0 - cos) * dot * x),
                 (float) (v.y * cos + sin * (z * v.x - x * v.z) + (1.0 - cos) * dot * y),
                 (float) (v.z * cos + sin * (x * v.y - y * v.x) + (1.0 - cos) * dot * z),
                 dest.w);
        return this;
    }

    /**
     * Return a string representation of this {@link AxisAngle4f}.
     *
     * @return the string representation
     */
    public String toString() {
        return "(" + x + ", " + y + ", " + z + " | " + angle + ")";
    }

    public int hashCode() {
        final int prime = 31;
        int result = 1;
        float nangle = (float) ((angle < 0.0 ? 2.0 * Math.PI + angle % (2.0 * Math.PI) : angle) % (2.0 * Math.PI));
        result = prime * result + Float.floatToIntBits(nangle);
        result = prime * result + Float.floatToIntBits(x);
        result = prime * result + Float.floatToIntBits(y);
        result = prime * result + Float.floatToIntBits(z);
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        AxisAngle4f other = (AxisAngle4f) obj;
        float nangle = (float) ((angle < 0.0 ? 2.0 * Math.PI + angle % (2.0 * Math.PI) : angle) % (2.0 * Math.PI));
        float nangleOther = (float) ((other.angle < 0.0 ? 2.0 * Math.PI + other.angle % (2.0 * Math.PI) : other.angle) % (2.0 * Math.PI));
        if (Float.floatToIntBits(nangle) != Float.floatToIntBits(nangleOther))
            return false;
        if (Float.floatToIntBits(x) != Float.floatToIntBits(other.x))
            return false;
        if (Float.floatToIntBits(y) != Float.floatToIntBits(other.y))
            return false;
        if (Float.floatToIntBits(z) != Float.floatToIntBits(other.z))
            return false;
        return true;
    }

    /**
     * Return the specified {@link Vector3f}.
     * <p>
     * When using method chaining in a fluent interface style, this method can be used to switch
     * the <i>context object</i>, on which further method invocations operate, to be the given vector.
     *
     * @param v
     *          the {@link Vector3f} to return
     * @return that vector
     */
    public Vector3f with(Vector3f v) {
        return v;
    }

    /**
     * Return the specified {@link Vector4f}.
     * <p>
     * When using method chaining in a fluent interface style, this method can be used to switch
     * the <i>context object</i>, on which further method invocations operate, to be the given vector.
     *
     * @param v
     *          the {@link Vector4f} to return
     * @return that vector
     */
    public Vector4f with(Vector4f v) {
        return v;
    }

    /**
     * Return the specified {@link Quaternionf}.
     * <p>
     * When using method chaining in a fluent interface style, this method can be used to switch
     * the <i>context object</i>, on which further method invocations operate, to be the given quaternion.
     *
     * @param q
     *          the {@link Quaternionf} to return
     * @return that quaternion
     */
    public Quaternionf with(Quaternionf q) {
        return q;
    }

    /**
     * Return the specified {@link Quaterniond}.
     * <p>
     * When using method chaining in a fluent interface style, this method can be used to switch
     * the <i>context object</i>, on which further method invocations operate, to be the given quaternion.
     *
     * @param q
     *          the {@link Quaterniond} to return
     * @return that quaternion
     */
    public Quaterniond with(Quaterniond q) {
        return q;
    }

    /**
     * Return the specified {@link AxisAngle4f}.
     * <p>
     * When using method chaining in a fluent interface style, this method can be used to switch
     * the <i>context object</i>, on which further method invocations operate, to be the given {@link AxisAngle4f}.
     *
     * @param a
     *          the {@link AxisAngle4f} to return
     * @return that quaternion
     */
    public AxisAngle4f with(AxisAngle4f a) {
        return a;
    }

    /**
     * Return the specified {@link Matrix3f}.
     * <p>
     * When using method chaining in a fluent interface style, this method can be used to switch
     * the <i>context object</i>, on which further method invocations operate, to be the given matrix.
     *
     * @param m
     *          the {@link Matrix3f} to return
     * @return that matrix
     */
    public Matrix3f with(Matrix3f m) {
        return m;
    }

    /**
     * Return the specified {@link Matrix4f}.
     * <p>
     * When using method chaining in a fluent interface style, this method can be used to switch
     * the <i>context object</i>, on which further method invocations operate, to be the given matrix.
     *
     * @param m
     *          the {@link Matrix4f} to return
     * @return that matrix
     */
    public Matrix4f with(Matrix4f m) {
        return m;
    }

    /**
     * Return the specified {@link Matrix3d}.
     * <p>
     * When using method chaining in a fluent interface style, this method can be used to switch
     * the <i>context object</i>, on which further method invocations operate, to be the given matrix.
     *
     * @param m
     *          the {@link Matrix3d} to return
     * @return that matrix
     */
    public Matrix3d with(Matrix3d m) {
        return m;
    }

    /**
     * Return the specified {@link Matrix4d}.
     * <p>
     * When using method chaining in a fluent interface style, this method can be used to switch
     * the <i>context object</i>, on which further method invocations operate, to be the given matrix.
     *
     * @param m
     *          the {@link Matrix4d} to return
     * @return that matrix
     */
    public Matrix4d with(Matrix4d m) {
        return m;
    }

}
