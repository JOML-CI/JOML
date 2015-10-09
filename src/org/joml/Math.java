/*
 * (C) Copyright 2015 JOML

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
 * Contains fast approximations of some {@link java.lang.Math} operations.
 * 
 * @author Kai Burjack
 */
public class Math {

    /*
     * The following implementation of an approximation of sine and cosine was
     * thankfully donated by Riven from http://java-gaming.org/.
     * 
     * The code for linear interpolation was gratefully donated by theagentd
     * from the same site.
     */
    public static final float PI = (float) java.lang.Math.PI;
    private static final float PI2 = PI * 2;
    private static final float PIHalf = PI * 0.5f;
    private static final int lookupBits = Integer.parseInt(System.getProperty("org.joml.Math.sinLookupTableBits", "9"));
    private static final int lookupTableSize = 1 << lookupBits;
    private static final int lookupTableSizeMinus1 = lookupTableSize - 1;
    private static final int lookupTableSizeWithMargin = lookupTableSize + 1;
    private static final float sinTable[] = new float[lookupTableSizeWithMargin];
    private static final float pi2OverLookupSize = PI2 / lookupTableSize;
    private static final float lookupSizeOverPi2 = lookupTableSize / PI2;
    private static final int BIG_ENOUGH_INT = 65536; // whatever makes sense
    private static final float BIG_ENOUGH_FLOAT = BIG_ENOUGH_INT;
    static {
        for (int i = 0; i < lookupTableSizeWithMargin; i++) {
            double d = i * pi2OverLookupSize;
            sinTable[i] = (float) java.lang.Math.sin(d);
        }
    }

    /**
     * @author Riven from <a href=
     *         "http://www.java-gaming.org/topics/extremely-fast-sine-cosine/36469/msg/349546/view.html#msg349546"
     *         >java-gaming.org</a>
     * @param x
     *            within [-BIG_ENOUGH..+inf)
     */
    private static int fastFloor(float x) {
        return (int) (x + BIG_ENOUGH_FLOAT) - BIG_ENOUGH_INT;
    }

    public static float sin(float rad) {
        float index = rad * lookupSizeOverPi2;
        int ii = fastFloor(index);
        float alpha = index - ii;
        int i = ii & lookupTableSizeMinus1;
        float sin1 = sinTable[i];
        float sin2 = sinTable[i + 1];
        // fast but unusual lerp.
        // See:
        // http://www.java-gaming.org/topics/extremely-fast-sine-cosine/36469/msg/349515/view.html#msg349515
        return sin1 + (sin2 - sin1) * alpha;
    }

    public static float cos(float rad) {
        return sin(rad + PIHalf);
    }

    public static double sin(double rad) {
        return sin((float) rad);
    }

    public static double cos(double rad) {
        return cos((float) rad);
    }

    /* Other math functions not yet approximated */

    public static double sqrt(double r) {
        return java.lang.Math.sqrt(r);
    }

    public static double tan(double r) {
        return java.lang.Math.tan(r);
    }

    public static double acos(double r) {
        return java.lang.Math.acos(r);
    }

    public static double atan2(double y, double x) {
        return java.lang.Math.atan2(y, x);
    }

    public static double asin(double r) {
        return java.lang.Math.asin(r);
    }

    public static double abs(double r) {
        return java.lang.Math.abs(r);
    }

    public static float abs(float r) {
        return java.lang.Math.abs(r);
    }

    public static float max(float x, float y) {
        return java.lang.Math.max(x, y);
    }

    public static float min(float x, float y) {
        return java.lang.Math.min(x, y);
    }

    public static double max(double x, double y) {
        return java.lang.Math.max(x, y);
    }

    public static double min(double x, double y) {
        return java.lang.Math.min(x, y);
    }

}
