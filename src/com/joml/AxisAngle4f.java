/*
 * (C) Copyright 2015 Kai Burjack
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software
 * and associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 *  1) The above copyright notice and this permission notice shall be included
 *     in all copies or substantial portions of the Software.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 */
package com.joml;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;

/**
 * Represents a 3D rotation about an axis represented as an unit 3D vector.
 * 
 * @author Kai Burjack
 */
public class AxisAngle4f implements Serializable, Externalizable {

    public float x;
    public float y;
    public float z;
    public float angle;

    /**
     * Create a new {@link AxisAngle4f} with zero rotation about (0, 0, 1).
     */
    public AxisAngle4f() {
        z = 1.0f;
    }

    /**
     * Create a new {@link AxisAngle4f} with the same values of <code>a</code>.
     * 
     * @param a
     *            the {@link AxisAngle4f} to copy the values from
     */
    public AxisAngle4f(AxisAngle4f a) {
        x = a.x;
        y = a.y;
        z = a.z;
        angle = a.angle;
    }

    /**
     * Create a new {@link AxisAngle4f} from the given {@link Quaternion}.
     * <p>
     * Reference: <a href=
     * "http://www.euclideanspace.com/maths/geometry/rotations/conversions/quaternionToAngle/"
     * >http://www.euclideanspace.com</a>
     * 
     * @param q
     *            the {@link Quaternion}
     */
    public AxisAngle4f(Quaternion q) {
        float acos = (float) Math.acos(q.w);
        float sqrt = (float) Math.sqrt(1.0 - q.w * q.w);
        this.x = q.x / sqrt;
        this.y = q.y / sqrt;
        this.z = q.z / sqrt;
        this.angle = 2.0f * acos;
    }

    /**
     * Create a new {@link AxisAngle4f} with the given values.
     * 
     * @param x
     *            the x-coordinate of the rotation axis
     * @param y
     *            the y-coordinate of the rotation axis
     * @param z
     *            the z-coordinate of the rotation axis
     * @param angle
     *            the angle in radians
     */
    public AxisAngle4f(float x, float y, float z, float angle) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.angle = angle;
    }

    /**
     * Set this {@link AxisAngle4f} to the values of <code>a</code>.
     * 
     * @param a
     *            the {@link AxisAngle4f} to copy the values from
     * @return this
     */
    public AxisAngle4f set(AxisAngle4f a) {
        x = a.x;
        y = a.y;
        z = a.z;
        angle = a.angle;
        return this;
    }

    /**
     * Set this {@link AxisAngle4f} to the given values.
     * 
     * @param x
     *            the x-coordinate of the rotation axis
     * @param y
     *            the y-coordinate of the rotation axis
     * @param z
     *            the z-coordinate of the rotation axis
     * @param angle
     *            the angle in radians
     * @return this
     */
    public AxisAngle4f set(float x, float y, float z, float angle) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.angle = angle;
        return this;
    }

    /**
     * Set this {@link AxisAngle4f} to be equivalent to the given
     * {@link Quaternion}.
     * 
     * @param q
     *            the {@link Quaternion}
     * @return this
     */
    public AxisAngle4f set(Quaternion q) {
        float acos = (float) Math.acos(q.w);
        float sqrt = (float) Math.sqrt(1.0 - q.w * q.w);
        this.x = q.x / sqrt;
        this.y = q.y / sqrt;
        this.z = q.z / sqrt;
        this.angle = 2.0f * acos;
        return this;
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeFloat(x);
        out.writeFloat(y);
        out.writeFloat(z);
        out.writeFloat(angle);
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        x = in.readFloat();
        y = in.readFloat();
        z = in.readFloat();
        angle = in.readFloat();
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

    public String toString() {
        return "AxisAngle4f {" + x + ", " + y + ", " + z + ", " + angle + "}";
    }

}
