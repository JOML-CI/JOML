package org.joml.test;

import junit.framework.TestCase;
import org.joml.RoundingMode;
import org.joml.Vector2d;
import org.joml.Vector2f;
import org.joml.Vector3d;
import org.joml.Vector3f;
import org.joml.Vector3i;

/**
 * Test class for {@link Vector3i}.
 */
public class Vector3iTest extends TestCase {
    public static void testVector3iRounding() {
        Vector3i v1 = new Vector3i(0.0f,.6f,.7f, RoundingMode.FLOOR);
        Vector3i v2 = new Vector3i(9.5f,1.6f,5.0f, RoundingMode.FLOOR);
        Vector3i v3 = new Vector3i(new Vector3f(9.5f,1.6f,5.0f), RoundingMode.FLOOR);
        Vector3i v4 = new Vector3i(new Vector3d(9.5f,1.6f,5.0f), RoundingMode.FLOOR);

        assertEquals(v1, new Vector3i(0,0,0));
        assertEquals(v2, new Vector3i(9,1,5));
        assertEquals(v3, new Vector3i(0,0,0));
        assertEquals(v4, new Vector3i(9,1,5));
    }

    public static void testVector3iRoundingVector2() {
        Vector3i v1 = new Vector3i(new Vector2f(0.0f,.6f),.7f, RoundingMode.FLOOR);
        Vector3i v2 = new Vector3i(new Vector2f(9.5f,1.6f),5.0f, RoundingMode.FLOOR);
        Vector3i v3 = new Vector3i(new Vector2d(0.0f,.6f),.7f, RoundingMode.FLOOR);
        Vector3i v4 = new Vector3i(new Vector2d(9.5f,1.6f),5.0f, RoundingMode.FLOOR);

        assertEquals(v1, new Vector3i(0,0,0));
        assertEquals(v2, new Vector3i(9,1,5));
        assertEquals(v3, new Vector3i(0,0,0));
        assertEquals(v4, new Vector3i(9,1,5));
    }
}
