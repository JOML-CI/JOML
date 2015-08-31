/*
 * (C) Copyright 2015 Richard Greenlees

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

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Base abstract implementation for 2D single-precision vectors. Provides the common implementations for
 * non-mutating methods.
 */
public abstract class BaseVector2f implements Vector2fr {

    public ByteBuffer get(ByteBuffer buffer) {
        return get(buffer.position(), buffer);
    }

    public ByteBuffer get(int index, ByteBuffer buffer) {
        buffer.putFloat(index,      x());
        buffer.putFloat(index + 4,  y());
        return buffer;
    }

    public FloatBuffer get(FloatBuffer buffer) {
        return get(buffer.position(), buffer);
    }

    public FloatBuffer get(int index, FloatBuffer buffer) {
        buffer.put(index,      x());
        buffer.put(index + 1,  y());
        return buffer;
    }

    public float dot(Vector2fr v) {
        return x() * v.x() + y() * v.y();
    }

    public float angle(Vector2fr v) {
        float dot = x()*v.x() + y()*v.y();
        float det = x()*v.y() - y()*v.x();
        return (float) Math.atan2(det, dot);
    }

    public float length() {
        return (float) Math.sqrt((x() * x()) + (y() * y()));
    }

    public float lengthSquared() {
        return x() * x() + y() * y();
    }

    public float distance(Vector2fr v) {
        float dx = v.x() - x();
        float dy = v.y() - y();
        return (float) Math.sqrt(dx * dx + dy * dy);
    }

    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Float.floatToIntBits(x());
        result = prime * result + Float.floatToIntBits(y());
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Vector2fr) {
            Vector2fr other = (Vector2fr) obj;
            return Float.floatToIntBits(x()) == Float.floatToIntBits(other.x())
                    && Float.floatToIntBits(y()) == Float.floatToIntBits(other.y());
        }
        return false;
    }

    /**
     * Return a string representation of this vector.
     * <p>
     * This method creates a new {@link java.text.DecimalFormat} on every invocation with the format string "<tt> 0.000E0;-</tt>".
     *
     * @return the string representation
     */
    public String toString() {
        DecimalFormat formatter = new DecimalFormat(" 0.000E0;-"); //$NON-NLS-1$
        return toString(formatter).replaceAll("E(\\d+)", "E+$1"); //$NON-NLS-1$ //$NON-NLS-2$
    }

    public String toString(NumberFormat formatter) {
        return "(" + formatter.format(x()) + " " + formatter.format(y()) + ")"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
    }
}
