/*
 * (C) Copyright 2017-2018 JOML

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
package org.joml.internal;

public final class Runtime {

//#ifndef __GWT__
    public static final boolean HAS_floatToRawIntBits = hasFloatToRawIntBits();
    public static final boolean HAS_doubleToRawLongBits = hasDoubleToRawLongBits();

    private Runtime() {
    }

    private static boolean hasFloatToRawIntBits() {
        try {
            Float.class.getDeclaredMethod("floatToRawIntBits", new Class[] { float.class });
            return true;
        } catch (NoSuchMethodException e) {
            return false;
        }
    }

    private static boolean hasDoubleToRawLongBits() {
        try {
            Double.class.getDeclaredMethod("doubleToRawLongBits", new Class[] { double.class });
            return true;
        } catch (NoSuchMethodException e) {
            return false;
        }
    }
//#endif

    public static int floatToIntBits(float flt) {
//#ifndef __GWT__
        if (HAS_floatToRawIntBits)
            return floatToIntBits1_3(flt);
//#endif
        return floatToIntBits1_2(flt);
    }
//#ifndef __GWT__
    private static int floatToIntBits1_3(float flt) {
        return Float.floatToRawIntBits(flt);
    }
//#endif
    private static int floatToIntBits1_2(float flt) {
        return Float.floatToIntBits(flt);
    }

    public static long doubleToLongBits(double dbl) {
//#ifndef __GWT__
        if (HAS_doubleToRawLongBits)
            return doubleToLongBits1_3(dbl);
//#endif
        return doubleToLongBits1_2(dbl);
    }
//#ifndef __GWT__
    private static long doubleToLongBits1_3(double dbl) {
        return Double.doubleToRawLongBits(dbl);
    }
//#endif
    private static long doubleToLongBits1_2(double dbl) {
        return Double.doubleToLongBits(dbl);
    }

    public static String formatNumbers(String str) {
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

    /*
     * From the JRE's Float.compare().
     */
    private static int compare(float f1, float f2) {
        if (f1 < f2)
            return -1;           // Neither val is NaN, thisVal is smaller
        if (f1 > f2)
            return 1;            // Neither val is NaN, thisVal is larger

        // Cannot use floatToRawIntBits because of possibility of NaNs.
        int thisBits    = Float.floatToIntBits(f1);
        int anotherBits = Float.floatToIntBits(f2);

        return (thisBits == anotherBits ?  0 : // Values are equal
                (thisBits < anotherBits ? -1 : // (-0.0, 0.0) or (!NaN, NaN)
                 1));                          // (0.0, -0.0) or (NaN, !NaN)
    }

    /*
     * From the JRE's Double.compare().
     */
    private static int compare(double d1, double d2) {
        if (d1 < d2)
            return -1;           // Neither val is NaN, thisVal is smaller
        if (d1 > d2)
            return 1;            // Neither val is NaN, thisVal is larger

        // Cannot use doubleToRawLongBits because of possibility of NaNs.
        long thisBits    = Double.doubleToLongBits(d1);
        long anotherBits = Double.doubleToLongBits(d2);

        return (thisBits == anotherBits ?  0 : // Values are equal
                (thisBits < anotherBits ? -1 : // (-0.0, 0.0) or (!NaN, NaN)
                 1));                          // (0.0, -0.0) or (NaN, !NaN)
    }

    public static boolean equals(float a, float b, float delta) {
        if (compare(a, b) == 0)
            return true;
        if (Math.abs(a - b) > delta)
            return false;
        return true;
    }

    public static boolean equals(double a, double b, double delta) {
        if (compare(a, b) == 0)
            return true;
        if (Math.abs(a - b) > delta)
            return false;
        return true;
    }

}
