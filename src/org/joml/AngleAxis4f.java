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
import java.io.Serializable;

/**
 * Represents a 3D rotation of a given degree about an axis represented as an
 * unit 3D vector.
 * 
 * @author Kai Burjack
 */
public class AngleAxis4f implements Serializable, Externalizable {

    /**
     * The angle in degrees.
     */
    public float angle;

    public float x;
    public float y;
    public float z;

    /**
     * Create a new {@link AngleAxis4f} with zero rotation about (0, 0, 1).
     */
    public AngleAxis4f() {
        z = 1.0f;
    }

    /**
     * Create a new {@link AngleAxis4f} with the same values of <code>a</code>.
     * 
     * @param a
     *            the {@link AngleAxis4f} to copy the values from
     */
    public AngleAxis4f(AngleAxis4f a) {
        x = a.x;
        y = a.y;
        z = a.z;
        angle = a.angle;
    }

    /**
     * Create a new {@link AngleAxis4f} from the given {@link Quaternion}.
     * <p>
     * Reference: <a href=
     * "http://www.euclideanspace.com/maths/geometry/rotations/conversions/quaternionToAngle/"
     * >http://www.euclideanspace.com</a>
     * 
     * @param q
     *            the {@link Quaternion}
     */
    public AngleAxis4f(Quaternion q) {
        float acos = (float) Math.acos(q.w);
        float sqrt = (float) Math.sqrt(1.0 - q.w * q.w);
        this.x = q.x / sqrt;
        this.y = q.y / sqrt;
        this.z = q.z / sqrt;
        this.angle = (float) Math.toDegrees(2.0 * acos);
    }

    /**
     * Create a new {@link AngleAxis4f} with the given values.
     *
     * @param angle
     *            the angle in degrees
     * @param x
     *            the x-coordinate of the rotation axis
     * @param y
     *            the y-coordinate of the rotation axis
     * @param z
     *            the z-coordinate of the rotation axis
     */
    public AngleAxis4f(float angle, float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.angle = angle;
    }

    /**
     * Set this {@link AngleAxis4f} to the values of <code>a</code>.
     * 
     * @param a
     *            the {@link AngleAxis4f} to copy the values from
     * @return this
     */
    public AngleAxis4f set(AngleAxis4f a) {
        x = a.x;
        y = a.y;
        z = a.z;
        angle = a.angle;
        return this;
    }

    /**
     * Set this {@link AngleAxis4f} to the given values.
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
    public AngleAxis4f set(float angle, float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.angle = angle;
        return this;
    }

    /**
     * Set this {@link AngleAxis4f} to be equivalent to the given
     * {@link Quaternion}.
     * 
     * @param q
     *            the {@link Quaternion}
     * @return this
     */
    public AngleAxis4f set(Quaternion q) {
        float acos = (float) Math.acos(q.w);
        float sqrt = (float) Math.sqrt(1.0 - q.w * q.w);
        this.x = q.x / sqrt;
        this.y = q.y / sqrt;
        this.z = q.z / sqrt;
        this.angle = (float) Math.toDegrees(2.0f * acos);
        return this;
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeFloat(angle);
        out.writeFloat(x);
        out.writeFloat(y);
        out.writeFloat(z);
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        angle = in.readFloat();
        x = in.readFloat();
        y = in.readFloat();
        z = in.readFloat();
    }

    /**
     * Normalize the axis vector.
     * 
     * @return this
     */
    public AngleAxis4f normalize() {
        float length = (float) Math.sqrt(x * x + y * y + z * z);
        x /= length;
        y /= length;
        z /= length;
        return this;
    }

    public String toString() {
        return "(" + x + ", " + y + ", " + z + " <| " + angle + ")";
    }

}
