package org.joml.test;

import org.joml.*;

import junit.framework.TestCase;

/**
 * Tests for the {@link GeometryUtils} class.
 *
 * @author Jarosław Piotrowski
 */
public class GeometryUtilsTest extends TestCase {

    public static void testTangents() {
        Vector3f pos1 = new Vector3f(-1.0f,  1.0f, 0.0f);
        Vector3f pos2 = new Vector3f( 1.0f, -1.0f, 0.0f);
        Vector3f pos3 = new Vector3f( 1.0f,  1.0f, 0.0f);

        Vector2f uv1 = new Vector2f(0.0f, 1.0f);
        Vector2f uv2 = new Vector2f(1.0f, 0.0f);
        Vector2f uv3 = new Vector2f(1.0f, 1.0f);

        Vector3f t = new Vector3f(1, 0, 0);
        Vector3f b = new Vector3f(0, 1, 0);

        Vector3f tangent = new Vector3f();
        Vector3f bitangent = new Vector3f();

        GeometryUtils.tangent(pos1, uv1, pos2, uv2, pos3, uv3, tangent);
        assertEquals(t, tangent);

        GeometryUtils.bitangent(pos1, uv1, pos2, uv2, pos3, uv3, bitangent);
        assertEquals(b, bitangent);

        GeometryUtils.tangentBitangent(pos1, uv1, pos2, uv2, pos3, uv3, tangent, bitangent);
        assertEquals(t, tangent);
        assertEquals(b, bitangent);
    }

}
