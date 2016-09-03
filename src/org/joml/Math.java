/*
 * (C) Copyright 2015-2016 JOML

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
 * <p>
 * By default, {@link java.lang.Math} methods will be used by all other JOML classes. In order to use the approximations in this class, start the JVM with the parameter <tt>-Djoml.fastmath</tt>.
 * <p>
 * There are two algorithms for approximating sin/cos:
 * <ol>
 * <li>L. Spiro's algorithm from <a href="http://www.gamedev.net/topic/681723-faster-sin-and-cos/#entry5308906">http://www.gamedev.net/</a>
 * <li>theagentd's <a href="http://www.java-gaming.org/topics/extremely-fast-sine-cosine/36469/msg/346213/view.html#msg346213">linear interpolation</a> variant of Riven's algorithm from
 * <a href="http://www.java-gaming.org/topics/extremely-fast-sine-cosine/36469/view.html">http://www.java-gaming.org/</a>
 * </ol>
 * By default, the first algorithm is being used. In order to use the second one, start the JVM with <tt>-Djoml.sinLookup</tt>. The lookup table bit length of the second algorithm can also be adjusted
 * for improved accuracy via <tt>-Djoml.sinLookup.bits=&lt;n&gt;</tt>, where &lt;n&gt; is the number of bits of the lookup table.
 * 
 * @author Kai Burjack
 */
public class Math {

    private static final boolean fastMath = hasOption("joml.fastmath");
    private static final boolean sinlookup = hasOption("joml.sinLookup");
    private static boolean hasOption(String option) {
        String v = System.getProperty(option);
        if (v == null)
            return false;
        if (v.trim().length() == 0)
            return true;
        return Boolean.valueOf(v).booleanValue();
    }

    /*
     * The following implementation of an approximation of sine and cosine was
     * thankfully donated by Riven from http://java-gaming.org/.
     * 
     * The code for linear interpolation was gratefully donated by theagentd
     * from the same site.
     */
    public static final double PI = java.lang.Math.PI;
    private static final double PI2 = PI * 2.0;
    private static final double PIHalf = PI * 0.5;
    private static final int lookupBits = Integer.parseInt(System.getProperty("joml.sinLookup.bits", "14")); //$NON-NLS-1$ //$NON-NLS-2$
    private static final int lookupTableSize = 1 << lookupBits;
    private static final int lookupTableSizeMinus1 = lookupTableSize - 1;
    private static final int lookupTableSizeWithMargin = lookupTableSize + 1;
    private static final double pi2OverLookupSize = PI2 / lookupTableSize;
    private static final double lookupSizeOverPi2 = lookupTableSize / PI2;
    private static final int BIG_ENOUGH_INT = 65536; // whatever makes sense
    private static final float BIG_ENOUGH_FLOAT = BIG_ENOUGH_INT;
    private static final float sinTable[];
    static {
        if (fastMath && sinlookup) {
            sinTable = new float[lookupTableSizeWithMargin];
            for (int i = 0; i < lookupTableSizeWithMargin; i++) {
                double d = i * pi2OverLookupSize;
                sinTable[i] = (float) java.lang.Math.sin(d);
            }
        } else {
            sinTable = null;
        }
    }

    /**
     * @author Riven from <a href="http://www.java-gaming.org/topics/extremely-fast-sine-cosine/36469/msg/349546/view.html#msg349546" >java-gaming.org</a>
     * @param x
     *            within [-BIG_ENOUGH..+inf)
     */
    private static int fastFloor(float x) {
        return (int) (x + BIG_ENOUGH_FLOAT) - BIG_ENOUGH_INT;
    }

    /**
     * Reference: <a href="http://www.gamedev.net/topic/681723-faster-sin-and-cos/#entry5308906">http://www.gamedev.net/</a>
     */
    private static double sin_L_Spiro(double rad) {
        int i32I = (int)( rad * (1.0f / PI) );
        double rad_ = (rad - i32I * PI);
        double rad2 = rad_ * rad_;
        return (i32I & 1) != 0
               ?
               -rad_ * ((float)( 1.00000000000000000000e+00) +
                rad2 * ((float)(-1.66666671633720397949e-01) +
                rad2 * ((float)( 8.33333376795053482056e-03) +
                rad2 * ((float)(-1.98412497411482036114e-04) +
                rad2 * ((float)( 2.75565571428160183132e-06) +
                rad2 * ((float)(-2.50368472620721149724e-08) +
                rad2 * ((float)( 1.58849267073435385100e-10) +
                rad2 *  (float)(-6.58925550841432672300e-13))))))))
               :
                rad_ * ((float)( 1.00000000000000000000e+00) +
                rad2 * ((float)(-1.66666671633720397949e-01) +
                rad2 * ((float)( 8.33333376795053482056e-03) +
                rad2 * ((float)(-1.98412497411482036114e-04) +
                rad2 * ((float)( 2.75565571428160183132e-06) +
                rad2 * ((float)(-2.50368472620721149724e-08) +
                rad2 * ((float)( 1.58849267073435385100e-10) +
                rad2 *  (float)(-6.58925550841432672300e-13))))))));
    }

    /**
     * Reference: <a href="http://www.java-gaming.org/topics/extremely-fast-sine-cosine/36469/msg/349515/view.html#msg349515">http://www.java-gaming.org/</a>
     */
    private static double sin_theagentd(double rad) {
        float index = (float) (rad * lookupSizeOverPi2);
        int ii = fastFloor(index);
        float alpha = index - ii;
        int i = ii & lookupTableSizeMinus1;
        float sin1 = sinTable[i];
        float sin2 = sinTable[i + 1];
        return sin1 + (sin2 - sin1) * alpha;
    }

    public static double sin(double rad) {
        if (fastMath) {
            if (sinlookup)
                return sin_theagentd(rad);
            return sin_L_Spiro(rad);
        }
        return java.lang.Math.sin(rad);
    }

    public static double cos(double rad) {
        if (fastMath)
            return sin(rad + PIHalf);
        return java.lang.Math.cos(rad);
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

    public static int max(int x, int y) {
        return java.lang.Math.max(x, y);
    }

    public static int min(int x, int y) {
        return java.lang.Math.min(x, y);
    }

    public static float min(float a, float b) {
        return a < b ? a : b;
    }

    public static float max(float a, float b) {
        return a > b ? a : b;
    }

    public static double min(double a, double b) {
        return a < b ? a : b;
    }

    public static double max(double a, double b) {
        return a > b ? a : b;
    }

}
