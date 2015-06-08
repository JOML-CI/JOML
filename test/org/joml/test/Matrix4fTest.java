package org.joml.test;

import java.nio.IntBuffer;

import junit.framework.TestCase;

import org.joml.Matrix4f;
import org.joml.Vector4f;

/**
 * Tests for the {@link Matrix4f} class.
 * 
 * @author Kai Burjack
 */
public class Matrix4fTest extends TestCase {

    /**
     * Test that project and unproject are each other's inverse operations.
     */
    public void testProjectUnproject() {
        /* Build some arbitrary viewport. */
        IntBuffer viewport = IntBuffer.wrap(new int[]{0, 0, 800, 800});

        Vector4f expected = new Vector4f(1.0f, 2.0f, -3.0f, 1.0f);
        Vector4f actual = new Vector4f();
        Matrix4f inverse = new Matrix4f();

        /* Build a perspective projection and then project and unproject. */
        new Matrix4f()
        .perspective(45.0f, 1.0f, 0.01f, 100.0f)
        .project(expected.x, expected.y, expected.z, viewport, actual)
        .unproject(actual.x, actual.y, actual.z, viewport, inverse, actual);

        /* Check for equality of the components */
        assertEquals(expected.x, actual.x, TestUtil.MANY_OPS_AROUND_ZERO_PRECISION_FLOAT);
        assertEquals(expected.y, actual.y, TestUtil.MANY_OPS_AROUND_ZERO_PRECISION_FLOAT);
        assertEquals(expected.z, actual.z, TestUtil.MANY_OPS_AROUND_ZERO_PRECISION_FLOAT);
        assertEquals(expected.w, actual.w, TestUtil.MANY_OPS_AROUND_ZERO_PRECISION_FLOAT);
    }

}
