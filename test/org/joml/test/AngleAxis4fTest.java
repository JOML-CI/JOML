package org.joml.test;

import junit.framework.TestCase;

import org.joml.AngleAxis4f;

/**
 * Tests for the {@link AngleAxis4f} class.
 * 
 * @author Kai Burjack
 */
public class AngleAxis4fTest extends TestCase {

    public void testAngleNormalization() {
        AngleAxis4f a1 = new AngleAxis4f((float) Math.toRadians(20), 1.0f, 0.0f, 0.0f);
        AngleAxis4f a2 = new AngleAxis4f((float) Math.toRadians(380), 1.0f, 0.0f, 0.0f);
        assertEquals(a1.angle, a2.angle, 1E-5f);

        a1 = new AngleAxis4f((float) Math.toRadians(-20), 1.0f, 0.0f, 0.0f);
        a2 = new AngleAxis4f((float) Math.toRadians(-380.0f), 1.0f, 0.0f, 0.0f);
        assertEquals(a1.angle, a2.angle, 1E-5f);

        a1 = new AngleAxis4f((float) Math.toRadians(-20.0f) * 10.0f, 1.0f, 0.0f, 0.0f);
        a2 = new AngleAxis4f((float) Math.toRadians(-380.0f) * 10.0f, 1.0f, 0.0f, 0.0f);
        assertEquals(a1.angle, a2.angle, 1E-5f);
    }

}
