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

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Abstract base class for readable representations of a 3D rotation of a given radius in radians about an axis represented as a
 * unit 3D vector.
 * <p>
 * This class uses single-precision components.
 * </p>
 */
public abstract class AxisAngle4fr {

    /**
     * @return The angle in radians.
     */
    public abstract float angle();

    /**
     * @return The x-component of the rotation axis.
     */
    public abstract float x();

    /**
     * @return The y-component of the rotation axis.
     */
    public abstract float y();

    /**
     * @return The z-component of the rotation axis.
     */
    public abstract float z();

    /**
     * Set the given {@link org.joml.Quaternionf} to be equivalent to this {@link org.joml.AxisAngle4fr} rotation.
     *
     * @param q the quaternion to set
     * @return q
     * @see org.joml.Quaternionf#set(AxisAngle4fr)
     */
    public Quaternionf get(Quaternionf q) {
        return q.set(this);
    }

    /**
     * Set the given {@link org.joml.Quaterniond} to be equivalent to this {@link org.joml.AxisAngle4fr} rotation.
     *
     * @param q the quaternion to set
     * @return q
     * @see org.joml.Quaterniond#set(AxisAngle4fr)
     */
    public Quaterniond get(Quaterniond q) {
        return q.set(this);
    }

    /**
     * Set the given {@link org.joml.Matrix4f} to a rotation transformation equivalent to this {@link org.joml.AxisAngle4fr}.
     *
     * @param m the matrix to set
     * @return m
     * @see org.joml.Matrix4f#set(AxisAngle4fr)
     */
    public Matrix4f get(Matrix4f m) {
        return m.set(this);
    }

    /**
     * Set the given {@link org.joml.Matrix3f} to a rotation transformation equivalent to this {@link org.joml.AxisAngle4fr}.
     *
     * @param m the matrix to set
     * @return m
     * @see org.joml.Matrix3f#set(AxisAngle4fr)
     */
    public Matrix3f get(Matrix3f m) {
        return m.set(this);
    }

    /**
     * Set the given {@link org.joml.Matrix4d} to a rotation transformation equivalent to this {@link org.joml.AxisAngle4fr}.
     *
     * @param m the matrix to set
     * @return m
     * @see org.joml.Matrix4f#set(AxisAngle4fr)
     */
    public Matrix4d get(Matrix4d m) {
        return m.set(this);
    }

    /**
     * Set the given {@link org.joml.Matrix3d} to a rotation transformation equivalent to this {@link org.joml.AxisAngle4fr}.
     *
     * @param m the matrix to set
     * @return m
     * @see org.joml.Matrix3f#set(AxisAngle4fr)
     */
    public Matrix3d get(Matrix3d m) {
        return m.set(this);
    }

    /**
     * Transform the given vector by the rotation transformation described by this {@link org.joml.AxisAngle4fr}.
     *
     * @param v the vector to transform
     * @return v
     */
    public Vector3f transform(Vector3f v) {
        return transform(v, v);
    }

    /**
     * Transform the given vector by the rotation transformation described by this {@link org.joml.AxisAngle4fr}
     * and store the result in <code>dest</code>.
     *
     * @param v    the vector to transform
     * @param dest will hold the result
     * @return dest
     */
    public Vector3f transform(Vector3fr v, Vector3f dest) {
        double cos = Math.cos(angle());
        double sin = Math.sin(angle());
        float dot = x() * v.x() + y() * v.y() + z() * v.z();
        dest.set((float) (v.x() * cos + sin * (y() * v.z() - z() * v.y()) + (1.0 - cos) * dot * x()),
                (float) (v.y() * cos + sin * (z() * v.x() - x() * v.z()) + (1.0 - cos) * dot * y()),
                (float) (v.z() * cos + sin * (x() * v.y() - y() * v.x()) + (1.0 - cos) * dot * z()));
        return dest;
    }

    /**
     * Transform the given vector by the rotation transformation described by this {@link org.joml.AxisAngle4fr}.
     *
     * @param v the vector to transform
     * @return v
     */
    public Vector4f transform(Vector4f v) {
        return transform(v, v);
    }

    /**
     * Transform the given vector by the rotation transformation described by this {@link org.joml.AxisAngle4fr}
     * and store the result in <code>dest</code>.
     *
     * @param v    the vector to transform
     * @param dest will hold the result
     * @return dest
     */
    public Vector4f transform(Vector4fr v, Vector4f dest) {
        double cos = Math.cos(angle());
        double sin = Math.sin(angle());
        float dot = x() * v.x() + y() * v.y() + z() * v.z();
        dest.set((float) (v.x() * cos + sin * (y() * v.z() - z() * v.y()) + (1.0 - cos) * dot * x()),
                (float) (v.y() * cos + sin * (z() * v.x() - x() * v.z()) + (1.0 - cos) * dot * y()),
                (float) (v.z() * cos + sin * (x() * v.y() - y() * v.x()) + (1.0 - cos) * dot * z()),
                dest.w);
        return dest;
    }

    /**
     * Return a string representation of this {@link org.joml.AxisAngle4fr}.
     * <p>
     * This method creates a new {@link java.text.DecimalFormat} on every invocation with the format string "<tt> 0.000E0;-</tt>".
     * </p>
     *
     * @return the string representation
     */
    public String toString() {
        DecimalFormat formatter = new DecimalFormat(" 0.000E0;-"); //$NON-NLS-1$
        return toString(formatter).replaceAll("E(\\d+)", "E+$1"); //$NON-NLS-1$ //$NON-NLS-2$
    }

    /**
     * Return a string representation of this {@link org.joml.AxisAngle4fr} by formatting the components with the given {@link java.text.NumberFormat}.
     *
     * @param formatter the {@link java.text.NumberFormat} used to format the vector components with
     * @return the string representation
     */
    public String toString(NumberFormat formatter) {
        return "(" + formatter.format(x()) + formatter.format(y()) + formatter.format(z()) + " <|" + formatter.format(angle()) + " )"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
    }

    public int hashCode() {
        final int prime = 31;
        int result = 1;
        float nangle = (float) ((angle() < 0.0 ? 2.0 * Math.PI + angle() % (2.0 * Math.PI) : angle()) % (2.0 * Math.PI));
        result = prime * result + Float.floatToIntBits(nangle);
        result = prime * result + Float.floatToIntBits(x());
        result = prime * result + Float.floatToIntBits(y());
        result = prime * result + Float.floatToIntBits(z());
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof AxisAngle4fr) {
            AxisAngle4fr other = (AxisAngle4fr) obj;
            float nangle = (float) ((angle() < 0.0 ? 2.0 * Math.PI + angle() % (2.0 * Math.PI) : angle()) % (2.0 * Math.PI));
            float nangleOther = (float) ((other.angle() < 0.0 ? 2.0 * Math.PI + other.angle() % (2.0 * Math.PI) : other.angle()) % (2.0 * Math.PI));
            return Float.floatToIntBits(nangle) == Float.floatToIntBits(nangleOther)
                    && Float.floatToIntBits(x()) == Float.floatToIntBits(other.x())
                    && Float.floatToIntBits(y()) == Float.floatToIntBits(other.y())
                    && Float.floatToIntBits(z()) == Float.floatToIntBits(other.z());
        }
        return false;
    }
}
