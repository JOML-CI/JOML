package org.joml.test;

import junit.framework.TestCase;

import org.joml.Rectangled;
import org.joml.Rectanglef;
import org.joml.Rectanglei;

/**
 * Tests of {@link Rectangled} scaling functions.
 */
public class RectangleScaleTest extends TestCase {

    static final float  F32_EPSILON = 1e-7f;
    static final double F64_EPSILON = 1e-16d;

    static boolean fpEqual (float a, float b) {
        return Math.abs (a - b) <= F32_EPSILON;
    }

    static boolean fpEqual (double a, double b) {
        return Math.abs (a - b) <= F64_EPSILON;
    }

    public void testNonUniformAnchoredScalingNoDestination () {
        Rectangled testA = new Rectangled (-1, -1, +1, +1);
        Rectanglef testB = new Rectanglef (-1, -1, +1, +1);
        Rectanglei testC = new Rectanglei (-1, -1, +1, +1);

        assertEquals (new Rectangled (-1, -1, +3, +5), testA.scale (2, 3, -1, -1));
        assertEquals (new Rectanglef (-1, -1, +3, +5), testB.scale (2, 3, -1, -1));
        assertEquals (new Rectanglei (-1, -1, +3, +5), testC.scale (2, 3, -1, -1));
    }

    public void testNonUniformAnchoredScalingWithDestination () {
        Rectangled testASubject = new Rectangled (-1, -1, +1, +1);
        Rectangled testATarget  = new Rectangled ( 0,  0,  0,  0);

        Rectanglef testBSubject = new Rectanglef (-1, -1, +1, +1);
        Rectanglef testBTarget  = new Rectanglef ( 0,  0,  0,  0);

        Rectanglei testCSubject = new Rectanglei (-1, -1, +1, +1);
        Rectanglei testCTarget  = new Rectanglei ( 0,  0,  0,  0);

        assertEquals (new Rectangled (-1, -1, +3, +5), testASubject.scale (2, 3, -1, -1, testATarget));
        assertEquals (new Rectanglef (-1, -1, +3, +5), testBSubject.scale (2, 3, -1, -1, testBTarget));
        assertEquals (new Rectanglei (-1, -1, +3, +5), testCSubject.scale (2, 3, -1, -1, testCTarget));
    }
}
