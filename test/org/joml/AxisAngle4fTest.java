package org.joml;

import junit.framework.TestCase;

import org.joml.AxisAngle4f;
import org.joml.Math;

/**
 * Tests for the {@link AxisAngle4f} class.
 * 
 * @author Kai Burjack
 */
public class AxisAngle4fTest extends TestCase {

    public static void testAngleNormalization() {
        AxisAngle4f a1 = new AxisAngle4f((float) Math.toRadians(20), 1.0f, 0.0f, 0.0f);
        AxisAngle4f a2 = new AxisAngle4f((float) Math.toRadians(380), 1.0f, 0.0f, 0.0f);
        assertEquals(a1.angle, a2.angle, 1E-5f);

        a1 = new AxisAngle4f((float) Math.toRadians(-20), 1.0f, 0.0f, 0.0f);
        a2 = new AxisAngle4f((float) Math.toRadians(-380.0f), 1.0f, 0.0f, 0.0f);
        assertEquals(a1.angle, a2.angle, 1E-5f);

        a1 = new AxisAngle4f((float) Math.toRadians(-20.0f) * 10.0f, 1.0f, 0.0f, 0.0f);
        a2 = new AxisAngle4f((float) Math.toRadians(-380.0f) * 10.0f, 1.0f, 0.0f, 0.0f);
        assertEquals(a1.angle, a2.angle, 1E-5f);
    }

}
