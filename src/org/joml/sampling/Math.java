/*
 * (C) Copyright 2016-2019 JOML

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
package org.joml.sampling;

/**
 * Internal Math class used by the sampling package.
 * 
 * @author Kai Burjack
 */
class Math {

    static final double PI = java.lang.Math.PI;
    static final double PI2 = PI * 2.0;
    static final double PIHalf = PI * 0.5;
    static final double PI_INV = 1.0 / PI;
    private static final double s5 = Double.longBitsToDouble(4523227044276562163L);
    private static final double s4 = Double.longBitsToDouble(-4671934770969572232L);
    private static final double s3 = Double.longBitsToDouble(4575957211482072852L);
    private static final double s2 = Double.longBitsToDouble(-4628199223918090387L);
    private static final double s1 = Double.longBitsToDouble(4607182418589157889L);

    /**
     * Reference: <a href=
     * "http://www.java-gaming.org/topics/joml-1-8-0-release/37491/msg/361815/view.html#msg361815">http://www.java-gaming.org/</a>
     * 
     * @author roquendm
     */
    static double sin_roquen_9(double v) {
        double i = java.lang.Math.rint(v * PI_INV);
        double x = v - i * Math.PI;
        double qs = 1 - 2 * ((int) i & 1);
        double x2 = x * x;
        double r;
        x = qs * x;
        r = s5;
        r = r * x2 + s4;
        r = r * x2 + s3;
        r = r * x2 + s2;
        r = r * x2 + s1;
        return x * r;
    }

    static double acos(double a) {
        //return java.lang.Math.acos(a);
        /*
         * http://stackoverflow.com/questions/3380628/fast-arc-cos-algorithm#answer-3380723
         */
        return (-0.69813170079773212 * a * a - 0.87266462599716477) * a + 1.5707963267948966;
    }

    /* Other math functions not yet approximated */

    static double sqrt(double r) {
        return java.lang.Math.sqrt(r);
    }

    static float min(float a, float b) {
        return a < b ? a : b;
    }

    static int min(int a, int b) {
        return a < b ? a : b;
    }

    static int max(int a, int b) {
        return a > b ? a : b;
    }

    static float max(float a, float b) {
        return a > b ? a : b;
    }

    static float abs(float r) {
        return java.lang.Math.abs(r);
    }

}
