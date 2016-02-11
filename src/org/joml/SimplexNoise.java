/*
 * (C) Copyright 2016 JOML

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
 * A simplex noise algorithm for 2D, 3D and 4D input.
 * <p>
 * It was originally authored by Stefan Gustavson.
 * <p>
 * The original implementation can be found here: <a
 * href="http://staffwww.itn.liu.se/~stegu/simplexnoise/SimplexNoise.java">http://http://staffwww.itn.liu.se/</a>.
 */
public class SimplexNoise {
    private static class Vector3b {
        byte x, y, z;
        Vector3b(int x, int y, int z) {
            super();
            this.x = (byte) x;
            this.y = (byte) y;
            this.z = (byte) z;
        }
    }
    private static class Vector4b {
        byte x, y, z, w;
        Vector4b(int x, int y, int z, int w) {
            super();
            this.x = (byte) x;
            this.y = (byte) y;
            this.z = (byte) z;
            this.w = (byte) w;
        }
    }

    // Kai Burjack:
    // Use a three-component vector here to save memory. (instead of using 4-component 'Grad' class)
    // And as the original author mentioned on the 'Grad' class, using a class to store the gradient components
    // is indeed faster compared to using a simple int[] array...
    private static final Vector3b[] grad3 = { new Vector3b(1, 1, 0), new Vector3b(-1, 1, 0), new Vector3b(1, -1, 0), new Vector3b(-1, -1, 0),
            new Vector3b(1, 0, 1), new Vector3b(-1, 0, 1), new Vector3b(1, 0, -1), new Vector3b(-1, 0, -1), new Vector3b(0, 1, 1), new Vector3b(0, -1, 1),
            new Vector3b(0, 1, -1), new Vector3b(0, -1, -1) };

    // Kai Burjack:
    // As the original author mentioned on the 'Grad' class, using a class to store the gradient components
    // is indeed faster compared to using a simple int[] array...
    private static final Vector4b[] grad4 = { new Vector4b(0, 1, 1, 1), new Vector4b(0, 1, 1, -1), new Vector4b(0, 1, -1, 1), new Vector4b(0, 1, -1, -1),
            new Vector4b(0, -1, 1, 1), new Vector4b(0, -1, 1, -1), new Vector4b(0, -1, -1, 1), new Vector4b(0, -1, -1, -1), new Vector4b(1, 0, 1, 1),
            new Vector4b(1, 0, 1, -1), new Vector4b(1, 0, -1, 1), new Vector4b(1, 0, -1, -1), new Vector4b(-1, 0, 1, 1), new Vector4b(-1, 0, 1, -1),
            new Vector4b(-1, 0, -1, 1), new Vector4b(-1, 0, -1, -1), new Vector4b(1, 1, 0, 1), new Vector4b(1, 1, 0, -1), new Vector4b(1, -1, 0, 1),
            new Vector4b(1, -1, 0, -1), new Vector4b(-1, 1, 0, 1), new Vector4b(-1, 1, 0, -1), new Vector4b(-1, -1, 0, 1), new Vector4b(-1, -1, 0, -1),
            new Vector4b(1, 1, 1, 0), new Vector4b(1, 1, -1, 0), new Vector4b(1, -1, 1, 0), new Vector4b(1, -1, -1, 0), new Vector4b(-1, 1, 1, 0),
            new Vector4b(-1, 1, -1, 0), new Vector4b(-1, -1, 1, 0), new Vector4b(-1, -1, -1, 0) };

    // Kai Burjack:
    // Use a byte[] instead of a short[] to save memory
    private static final byte[] p = { -105, -96, -119, 91, 90, 15, -125, 13, -55, 95, 96, 53, -62, -23, 7, -31, -116, 36, 103, 30, 69, -114, 8, 99, 37, -16,
            21, 10, 23, -66, 6, -108, -9, 120, -22, 75, 0, 26, -59, 62, 94, -4, -37, -53, 117, 35, 11, 32, 57, -79, 33, 88, -19, -107, 56, 87, -82, 20, 125,
            -120, -85, -88, 68, -81, 74, -91, 71, -122, -117, 48, 27, -90, 77, -110, -98, -25, 83, 111, -27, 122, 60, -45, -123, -26, -36, 105, 92, 41, 55, 46,
            -11, 40, -12, 102, -113, 54, 65, 25, 63, -95, 1, -40, 80, 73, -47, 76, -124, -69, -48, 89, 18, -87, -56, -60, -121, -126, 116, -68, -97, 86, -92,
            100, 109, -58, -83, -70, 3, 64, 52, -39, -30, -6, 124, 123, 5, -54, 38, -109, 118, 126, -1, 82, 85, -44, -49, -50, 59, -29, 47, 16, 58, 17, -74,
            -67, 28, 42, -33, -73, -86, -43, 119, -8, -104, 2, 44, -102, -93, 70, -35, -103, 101, -101, -89, 43, -84, 9, -127, 22, 39, -3, 19, 98, 108, 110,
            79, 113, -32, -24, -78, -71, 112, 104, -38, -10, 97, -28, -5, 34, -14, -63, -18, -46, -112, 12, -65, -77, -94, -15, 81, 51, -111, -21, -7, 14, -17,
            107, 49, -64, -42, 31, -75, -57, 106, -99, -72, 84, -52, -80, 115, 121, 50, 45, 127, 4, -106, -2, -118, -20, -51, 93, -34, 114, 67, 29, 24, 72,
            -13, -115, -128, -61, 78, 66, -41, 61, -100, -76 };
    // To remove the need for index wrapping, float the permutation table length
    private static final byte[] perm = new byte[512];
    private static final byte[] permMod12 = new byte[512];
    static {
        for (int i = 0; i < 512; i++) {
            perm[i] = p[i & 255];
            permMod12[i] = (byte) ((perm[i]&0xFF) % 12);
        }
    }

    // Skewing and unskewing factors for 2, 3, and 4 dimensions
    private static final float F2 = 0.3660254037844386f; // <- (float) (0.5f * (Math.sqrt(3.0f) - 1.0f));
    private static final float G2 = 0.21132486540518713f; // <- (float) ((3.0f - Math.sqrt(3.0f)) / 6.0f);
    private static final float F3 = 1.0f / 3.0f;
    private static final float G3 = 1.0f / 6.0f;
    private static final float F4 = 0.30901699437494745f; // <- (float) ((Math.sqrt(5.0f) - 1.0f) / 4.0f);
    private static final float G4 = 0.1381966011250105f; // <- (float) ((5.0f - Math.sqrt(5.0f)) / 20.0f);

    // This method is a *lot* faster than using (int)Math.floor(x)
    private static int fastfloor(float x) {
        int xi = (int) x;
        return x < xi ? xi - 1 : xi;
    }

    private static float dot(Vector3b g, float x, float y) {
        return g.x * x + g.y * y;
    }

    private static float dot(Vector3b g, float x, float y, float z) {
        return g.x * x + g.y * y + g.z * z;
    }

    private static float dot(Vector4b g, float x, float y, float z, float w) {
        return g.x * x + g.y * y + g.z * z + g.w * w;
    }

    /**
     * Compute 2D simplex noise for the given input vector <tt>(x, y)</tt>.
     * <p>
     * The result is in the range <tt>[-1..+1]</tt>.
     * 
     * @param x
     *          the x coordinate
     * @param y
     *          the y coordinate
     * @return the noise value (within <tt>[-1..+1]</tt>)
     */
    public static float noise(float x, float y) {
        float n0, n1, n2; // Noise contributions from the three corners
        // Skew the input space to determine which simplex cell we're in
        float s = (x + y) * F2; // Hairy factor for 2D
        int i = fastfloor(x + s);
        int j = fastfloor(y + s);
        float t = (i + j) * G2;
        float X0 = i - t; // Unskew the cell origin back to (x,y) space
        float Y0 = j - t;
        float x0 = x - X0; // The x,y distances from the cell origin
        float y0 = y - Y0;
        // For the 2D case, the simplex shape is an equilateral triangle.
        // Determine which simplex we are in.
        int i1, j1; // Offsets for second (middle) corner of simplex in (i,j) coords
        if (x0 > y0) {
            i1 = 1;
            j1 = 0;
        } // lower triangle, XY order: (0,0)->(1,0)->(1,1)
        else {
            i1 = 0;
            j1 = 1;
        } // upper triangle, YX order: (0,0)->(0,1)->(1,1)
          // A step of (1,0) in (i,j) means a step of (1-c,-c) in (x,y), and
          // a step of (0,1) in (i,j) means a step of (-c,1-c) in (x,y), where
          // c = (3-sqrt(3))/6
        float x1 = x0 - i1 + G2; // Offsets for middle corner in (x,y) unskewed coords
        float y1 = y0 - j1 + G2;
        float x2 = x0 - 1.0f + 2.0f * G2; // Offsets for last corner in (x,y) unskewed coords
        float y2 = y0 - 1.0f + 2.0f * G2;
        // Work out the hashed gradient indices of the three simplex corners
        int ii = i & 255;
        int jj = j & 255;
        int gi0 = permMod12[ii + perm[jj]&0xFF]&0xFF;
        int gi1 = permMod12[ii + i1 + perm[jj + j1]&0xFF]&0xFF;
        int gi2 = permMod12[ii + 1 + perm[jj + 1]&0xFF]&0xFF;
        // Calculate the contribution from the three corners
        float t0 = 0.5f - x0 * x0 - y0 * y0;
        if (t0 < 0.0f)
            n0 = 0.0f;
        else {
            t0 *= t0;
            n0 = t0 * t0 * dot(grad3[gi0], x0, y0); // (x,y) of grad3 used for 2D gradient
        }
        float t1 = 0.5f - x1 * x1 - y1 * y1;
        if (t1 < 0.0f)
            n1 = 0.0f;
        else {
            t1 *= t1;
            n1 = t1 * t1 * dot(grad3[gi1], x1, y1);
        }
        float t2 = 0.5f - x2 * x2 - y2 * y2;
        if (t2 < 0.0f)
            n2 = 0.0f;
        else {
            t2 *= t2;
            n2 = t2 * t2 * dot(grad3[gi2], x2, y2);
        }
        // Add contributions from each corner to get the final noise value.
        // The result is scaled to return values in the interval [-1,1].
        return 70.0f * (n0 + n1 + n2);
    }

    /**
     * Compute 3D simplex noise for the given input vector <tt>(x, y, z)</tt>.
     * <p>
     * The result is in the range <tt>[-1..+1]</tt>.
     * 
     * @param x
     *          the x coordinate
     * @param y
     *          the y coordinate
     * @param z
     *          the z coordinate
     * @return the noise value (within <tt>[-1..+1]</tt>)
     */
    public static float noise(float x, float y, float z) {
        float n0, n1, n2, n3; // Noise contributions from the four corners
        // Skew the input space to determine which simplex cell we're in
        float s = (x + y + z) * F3; // Very nice and simple skew factor for 3D
        int i = fastfloor(x + s);
        int j = fastfloor(y + s);
        int k = fastfloor(z + s);
        float t = (i + j + k) * G3;
        float X0 = i - t; // Unskew the cell origin back to (x,y,z) space
        float Y0 = j - t;
        float Z0 = k - t;
        float x0 = x - X0; // The x,y,z distances from the cell origin
        float y0 = y - Y0;
        float z0 = z - Z0;
        // For the 3D case, the simplex shape is a slightly irregular tetrahedron.
        // Determine which simplex we are in.
        int i1, j1, k1; // Offsets for second corner of simplex in (i,j,k) coords
        int i2, j2, k2; // Offsets for third corner of simplex in (i,j,k) coords
        if (x0 >= y0) {
            if (y0 >= z0) {
                i1 = 1;
                j1 = 0;
                k1 = 0;
                i2 = 1;
                j2 = 1;
                k2 = 0;
            } // X Y Z order
            else if (x0 >= z0) {
                i1 = 1;
                j1 = 0;
                k1 = 0;
                i2 = 1;
                j2 = 0;
                k2 = 1;
            } // X Z Y order
            else {
                i1 = 0;
                j1 = 0;
                k1 = 1;
                i2 = 1;
                j2 = 0;
                k2 = 1;
            } // Z X Y order
        } else { // x0<y0
            if (y0 < z0) {
                i1 = 0;
                j1 = 0;
                k1 = 1;
                i2 = 0;
                j2 = 1;
                k2 = 1;
            } // Z Y X order
            else if (x0 < z0) {
                i1 = 0;
                j1 = 1;
                k1 = 0;
                i2 = 0;
                j2 = 1;
                k2 = 1;
            } // Y Z X order
            else {
                i1 = 0;
                j1 = 1;
                k1 = 0;
                i2 = 1;
                j2 = 1;
                k2 = 0;
            } // Y X Z order
        }
        // A step of (1,0,0) in (i,j,k) means a step of (1-c,-c,-c) in (x,y,z),
        // a step of (0,1,0) in (i,j,k) means a step of (-c,1-c,-c) in (x,y,z), and
        // a step of (0,0,1) in (i,j,k) means a step of (-c,-c,1-c) in (x,y,z), where
        // c = 1/6.
        float x1 = x0 - i1 + G3; // Offsets for second corner in (x,y,z) coords
        float y1 = y0 - j1 + G3;
        float z1 = z0 - k1 + G3;
        float x2 = x0 - i2 + 2.0f * G3; // Offsets for third corner in (x,y,z) coords
        float y2 = y0 - j2 + 2.0f * G3;
        float z2 = z0 - k2 + 2.0f * G3;
        float x3 = x0 - 1.0f + 3.0f * G3; // Offsets for last corner in (x,y,z) coords
        float y3 = y0 - 1.0f + 3.0f * G3;
        float z3 = z0 - 1.0f + 3.0f * G3;
        // Work out the hashed gradient indices of the four simplex corners
        int ii = i & 255;
        int jj = j & 255;
        int kk = k & 255;
        int gi0 = permMod12[ii + perm[jj + perm[kk]&0xFF]&0xFF]&0xFF;
        int gi1 = permMod12[ii + i1 + perm[jj + j1 + perm[kk + k1]&0xFF]&0xFF]&0xFF;
        int gi2 = permMod12[ii + i2 + perm[jj + j2 + perm[kk + k2]&0xFF]&0xFF]&0xFF;
        int gi3 = permMod12[ii + 1 + perm[jj + 1 + perm[kk + 1]&0xFF]&0xFF]&0xFF;
        // Calculate the contribution from the four corners
        float t0 = 0.6f - x0 * x0 - y0 * y0 - z0 * z0;
        if (t0 < 0.0f)
            n0 = 0.0f;
        else {
            t0 *= t0;
            n0 = t0 * t0 * dot(grad3[gi0], x0, y0, z0);
        }
        float t1 = 0.6f - x1 * x1 - y1 * y1 - z1 * z1;
        if (t1 < 0.0f)
            n1 = 0.0f;
        else {
            t1 *= t1;
            n1 = t1 * t1 * dot(grad3[gi1], x1, y1, z1);
        }
        float t2 = 0.6f - x2 * x2 - y2 * y2 - z2 * z2;
        if (t2 < 0.0f)
            n2 = 0.0f;
        else {
            t2 *= t2;
            n2 = t2 * t2 * dot(grad3[gi2], x2, y2, z2);
        }
        float t3 = 0.6f - x3 * x3 - y3 * y3 - z3 * z3;
        if (t3 < 0.0f)
            n3 = 0.0f;
        else {
            t3 *= t3;
            n3 = t3 * t3 * dot(grad3[gi3], x3, y3, z3);
        }
        // Add contributions from each corner to get the final noise value.
        // The result is scaled to stay just inside [-1,1]
        return 32.0f * (n0 + n1 + n2 + n3);
    }

    /**
     * Compute 4D simplex noise for the given input vector <tt>(x, y, z, w)</tt>.
     * <p>
     * The result is in the range <tt>[-1..+1]</tt>.
     * 
     * @param x
     *          the x coordinate
     * @param y
     *          the y coordinate
     * @param z
     *          the z coordinate
     * @param w
     *          the w coordinate
     * @return the noise value (within <tt>[-1..+1]</tt>)
     */
    public static float noise(float x, float y, float z, float w) {
        float n0, n1, n2, n3, n4; // Noise contributions from the five corners
        // Skew the (x,y,z,w) space to determine which cell of 24 simplices we're in
        float s = (x + y + z + w) * F4; // Factor for 4D skewing
        int i = fastfloor(x + s);
        int j = fastfloor(y + s);
        int k = fastfloor(z + s);
        int l = fastfloor(w + s);
        float t = (i + j + k + l) * G4; // Factor for 4D unskewing
        float X0 = i - t; // Unskew the cell origin back to (x,y,z,w) space
        float Y0 = j - t;
        float Z0 = k - t;
        float W0 = l - t;
        float x0 = x - X0; // The x,y,z,w distances from the cell origin
        float y0 = y - Y0;
        float z0 = z - Z0;
        float w0 = w - W0;
        // For the 4D case, the simplex is a 4D shape I won't even try to describe.
        // To find out which of the 24 possible simplices we're in, we need to
        // determine the magnitude ordering of x0, y0, z0 and w0.
        // Six pair-wise comparisons are performed between each possible pair
        // of the four coordinates, and the results are used to rank the numbers.
        int rankx = 0;
        int ranky = 0;
        int rankz = 0;
        int rankw = 0;
        if (x0 > y0)
            rankx++;
        else
            ranky++;
        if (x0 > z0)
            rankx++;
        else
            rankz++;
        if (x0 > w0)
            rankx++;
        else
            rankw++;
        if (y0 > z0)
            ranky++;
        else
            rankz++;
        if (y0 > w0)
            ranky++;
        else
            rankw++;
        if (z0 > w0)
            rankz++;
        else
            rankw++;
        int i1, j1, k1, l1; // The integer offsets for the second simplex corner
        int i2, j2, k2, l2; // The integer offsets for the third simplex corner
        int i3, j3, k3, l3; // The integer offsets for the fourth simplex corner
        // simplex[c] is a 4-vector with the numbers 0, 1, 2 and 3 in some order.
        // Many values of c will never occur, since e.g. x>y>z>w makes x<z, y<w and x<w
        // impossible. Only the 24 indices which have non-zero entries make any sense.
        // We use a thresholding to set the coordinates in turn from the largest magnitude.
        // Rank 3 denotes the largest coordinate.
        i1 = rankx >= 3 ? 1 : 0;
        j1 = ranky >= 3 ? 1 : 0;
        k1 = rankz >= 3 ? 1 : 0;
        l1 = rankw >= 3 ? 1 : 0;
        // Rank 2 denotes the second largest coordinate.
        i2 = rankx >= 2 ? 1 : 0;
        j2 = ranky >= 2 ? 1 : 0;
        k2 = rankz >= 2 ? 1 : 0;
        l2 = rankw >= 2 ? 1 : 0;
        // Rank 1 denotes the second smallest coordinate.
        i3 = rankx >= 1 ? 1 : 0;
        j3 = ranky >= 1 ? 1 : 0;
        k3 = rankz >= 1 ? 1 : 0;
        l3 = rankw >= 1 ? 1 : 0;
        // The fifth corner has all coordinate offsets = 1, so no need to compute that.
        float x1 = x0 - i1 + G4; // Offsets for second corner in (x,y,z,w) coords
        float y1 = y0 - j1 + G4;
        float z1 = z0 - k1 + G4;
        float w1 = w0 - l1 + G4;
        float x2 = x0 - i2 + 2.0f * G4; // Offsets for third corner in (x,y,z,w) coords
        float y2 = y0 - j2 + 2.0f * G4;
        float z2 = z0 - k2 + 2.0f * G4;
        float w2 = w0 - l2 + 2.0f * G4;
        float x3 = x0 - i3 + 3.0f * G4; // Offsets for fourth corner in (x,y,z,w) coords
        float y3 = y0 - j3 + 3.0f * G4;
        float z3 = z0 - k3 + 3.0f * G4;
        float w3 = w0 - l3 + 3.0f * G4;
        float x4 = x0 - 1.0f + 4.0f * G4; // Offsets for last corner in (x,y,z,w) coords
        float y4 = y0 - 1.0f + 4.0f * G4;
        float z4 = z0 - 1.0f + 4.0f * G4;
        float w4 = w0 - 1.0f + 4.0f * G4;
        // Work out the hashed gradient indices of the five simplex corners
        int ii = i & 255;
        int jj = j & 255;
        int kk = k & 255;
        int ll = l & 255;
        int gi0 = (perm[ii + perm[jj + perm[kk + perm[ll]&0xFF]&0xFF]&0xFF]&0xFF) % 32;
        int gi1 = (perm[ii + i1 + perm[jj + j1 + perm[kk + k1 + perm[ll + l1]&0xFF]&0xFF]&0xFF]&0xFF) % 32;
        int gi2 = (perm[ii + i2 + perm[jj + j2 + perm[kk + k2 + perm[ll + l2]&0xFF]&0xFF]&0xFF]&0xFF) % 32;
        int gi3 = (perm[ii + i3 + perm[jj + j3 + perm[kk + k3 + perm[ll + l3]&0xFF]&0xFF]&0xFF]&0xFF) % 32;
        int gi4 = (perm[ii + 1 + perm[jj + 1 + perm[kk + 1 + perm[ll + 1]&0xFF]&0xFF]&0xFF]&0xFF) % 32;
        // Calculate the contribution from the five corners
        float t0 = 0.6f - x0 * x0 - y0 * y0 - z0 * z0 - w0 * w0;
        if (t0 < 0.0f)
            n0 = 0.0f;
        else {
            t0 *= t0;
            n0 = t0 * t0 * dot(grad4[gi0], x0, y0, z0, w0);
        }
        float t1 = 0.6f - x1 * x1 - y1 * y1 - z1 * z1 - w1 * w1;
        if (t1 < 0.0f)
            n1 = 0.0f;
        else {
            t1 *= t1;
            n1 = t1 * t1 * dot(grad4[gi1], x1, y1, z1, w1);
        }
        float t2 = 0.6f - x2 * x2 - y2 * y2 - z2 * z2 - w2 * w2;
        if (t2 < 0.0f)
            n2 = 0.0f;
        else {
            t2 *= t2;
            n2 = t2 * t2 * dot(grad4[gi2], x2, y2, z2, w2);
        }
        float t3 = 0.6f - x3 * x3 - y3 * y3 - z3 * z3 - w3 * w3;
        if (t3 < 0.0f)
            n3 = 0.0f;
        else {
            t3 *= t3;
            n3 = t3 * t3 * dot(grad4[gi3], x3, y3, z3, w3);
        }
        float t4 = 0.6f - x4 * x4 - y4 * y4 - z4 * z4 - w4 * w4;
        if (t4 < 0.0f)
            n4 = 0.0f;
        else {
            t4 *= t4;
            n4 = t4 * t4 * dot(grad4[gi4], x4, y4, z4, w4);
        }
        // Sum up and scale the result to cover the range [-1,1]
        return 27.0f * (n0 + n1 + n2 + n3 + n4);
    }

}
