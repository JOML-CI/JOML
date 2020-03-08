/*
 * The MIT License
 *
 * Copyright (c) 2017-2020 JOML
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.joml;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Represents a ray with a given origin and direction using single-precision floating-point numbers.
 *
 * @author Kai Burjack
 */
public class Rayf implements Externalizable {

    /**
     * The x coordinate of the ray's origin.
     */
    public float oX;
    /**
     * The y coordinate of the ray's origin.
     */
    public float oY;
    /**
     * The z coordinate of the ray's origin.
     */
    public float oZ;
    /**
     * The x coordinate of the ray's direction.
     */
    public float dX;
    /**
     * The y coordinate of the ray's direction.
     */
    public float dY;
    /**
     * The z coordinate of the ray's direction.
     */
    public float dZ;

    /**
     * Create a new {@link Rayf} with origin <code>(0, 0, 0)</code> and no direction.
     */
    public Rayf() {
    }

    /**
     * Create a new {@link Rayf} as a copy of the given <code>source</code>.
     * 
     * @param source
     *          the {@link Rayf} to copy from
     */
    public Rayf(Rayf source) {
        this.oX = source.oX;
        this.oY = source.oY;
        this.oZ = source.oZ;
        this.dX = source.dX;
        this.dY = source.dY;
        this.dZ = source.dZ;
    }

    /**
     * Create a new {@link Rayf} with the given <code>origin</code> and <code>direction</code>.
     * 
     * @param origin
     *          the origin of the ray
     * @param direction
     *          the direction of the ray
     */
    public Rayf(Vector3fc origin, Vector3fc direction) {
        this.oX = origin.x();
        this.oY = origin.y();
        this.oZ = origin.z();
        this.dX = direction.x();
        this.dY = direction.y();
        this.dZ = direction.z();
    }

    /**
     * Create a new {@link Rayf} with the given origin and direction.
     * 
     * @param oX
     *          the x coordinate of the ray's origin
     * @param oY
     *          the y coordinate of the ray's origin
     * @param oZ
     *          the z coordinate of the ray's origin
     * @param dX
     *          the x coordinate of the ray's direction
     * @param dY
     *          the y coordinate of the ray's direction
     * @param dZ
     *          the z coordinate of the ray's direction
     */
    public Rayf(float oX, float oY, float oZ, float dX, float dY, float dZ) {
        this.oX = oX;
        this.oY = oY;
        this.oZ = oZ;
        this.dX = dX;
        this.dY = dY;
        this.dZ = dZ;
    }

    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Float.floatToIntBits(dX);
        result = prime * result + Float.floatToIntBits(dY);
        result = prime * result + Float.floatToIntBits(dZ);
        result = prime * result + Float.floatToIntBits(oX);
        result = prime * result + Float.floatToIntBits(oY);
        result = prime * result + Float.floatToIntBits(oZ);
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Rayf other = (Rayf) obj;
        if (Float.floatToIntBits(dX) != Float.floatToIntBits(other.dX))
            return false;
        if (Float.floatToIntBits(dY) != Float.floatToIntBits(other.dY))
            return false;
        if (Float.floatToIntBits(dZ) != Float.floatToIntBits(other.dZ))
            return false;
        if (Float.floatToIntBits(oX) != Float.floatToIntBits(other.oX))
            return false;
        if (Float.floatToIntBits(oY) != Float.floatToIntBits(other.oY))
            return false;
        if (Float.floatToIntBits(oZ) != Float.floatToIntBits(other.oZ))
            return false;
        return true;
    }

    /**
     * Return a string representation of this ray.
     * <p>
     * This method creates a new {@link DecimalFormat} on every invocation with the format string "<code>0.000E0;-</code>".
     * 
     * @return the string representation
     */
    public String toString() {
        return Runtime.formatNumbers(toString(Options.NUMBER_FORMAT));
    }

    /**
     * Return a string representation of this ray by formatting the vector components with the given {@link NumberFormat}.
     * 
     * @param formatter
     *          the {@link NumberFormat} used to format the vector components with
     * @return the string representation
     */
    public String toString(NumberFormat formatter) {
        return "(" + Runtime.format(oX, formatter) + " " + Runtime.format(oY, formatter) + " " + Runtime.format(oZ, formatter) + ") -> "
             + "(" + Runtime.format(dX, formatter) + " " + Runtime.format(dY, formatter) + " " + Runtime.format(dZ, formatter) + ")";
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeFloat(oX);
        out.writeFloat(oY);
        out.writeFloat(oZ);
        out.writeFloat(dX);
        out.writeFloat(dY);
        out.writeFloat(dZ);
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        oX = in.readFloat();
        oY = in.readFloat();
        oZ = in.readFloat();
        dX = in.readFloat();
        dY = in.readFloat();
        dZ = in.readFloat();
    }

}
