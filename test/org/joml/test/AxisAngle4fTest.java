package org.joml.test;

import junit.framework.TestCase;

import org.joml.AxisAngle4f;

/**
 * Tests for the {@link AxisAngle4f} class.
 * 
 * @author Kai Burjack
 */
public class AxisAngle4fTest extends TestCase {

    public void testAngleNormalization() {
        AxisAngle4f a1 = new AxisAngle4f(20.0f, 1.0f, 0.0f, 0.0f);
        AxisAngle4f a2 = new AxisAngle4f(380.0f, 1.0f, 0.0f, 0.0f);
        assertEquals(a1.angle, a2.angle, 1E-9f);

        a1 = new AxisAngle4f(-20.0f, 1.0f, 0.0f, 0.0f);
        a2 = new AxisAngle4f(-380.0f, 1.0f, 0.0f, 0.0f);
        assertEquals(a1.angle, a2.angle, 1E-9f);

        a1 = new AxisAngle4f(-20.0f * 10.0f, 1.0f, 0.0f, 0.0f);
        a2 = new AxisAngle4f(-380.0f * 10.0f, 1.0f, 0.0f, 0.0f);
        assertEquals(a1.angle, a2.angle, 1E-9f);
    }

}
