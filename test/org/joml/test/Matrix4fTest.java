package org.joml.test;

import java.nio.IntBuffer;

import junit.framework.TestCase;

import org.joml.Matrix4f;
import org.joml.Vector3f;

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

        Vector3f expected = new Vector3f(1.0f, 2.0f, -3.0f);
        Vector3f actual = new Vector3f();
        Matrix4f inverse = new Matrix4f();

        /* Build a perspective projection and then project and unproject. */
        new Matrix4f()
        .perspective(45.0f, 1.0f, 0.01f, 100.0f)
        .project(expected, viewport, actual)
        .unproject(actual, viewport, inverse, actual);

        /* Check for equality of the components */
        assertEquals(expected.x, actual.x, TestUtil.MANY_OPS_AROUND_ZERO_PRECISION_FLOAT);
        assertEquals(expected.y, actual.y, TestUtil.MANY_OPS_AROUND_ZERO_PRECISION_FLOAT);
        assertEquals(expected.z, actual.z, TestUtil.MANY_OPS_AROUND_ZERO_PRECISION_FLOAT);
    }

    public void testLookAt() {
        {
        Matrix4f m1 = new Matrix4f().lookAt(0.0f, 2.0f, 3.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f);
        Matrix4f m2 = new Matrix4f().translate(0.0f, 0.0f, -(float) Math.sqrt(2*2 + 3*3)).rotateX((float) Math.toDegrees(Math.atan2(2, 3)));
        TestUtil.assertMatrix4fEquals(m1, m2, 1E-15f);
        }
        {
        Matrix4f m1 = new Matrix4f().lookAt(3.0f, 2.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f);
        Matrix4f m2 = new Matrix4f().translate(0.0f, 0.0f, -(float) Math.sqrt(2*2 + 3*3)).rotateX((float) Math.toDegrees(Math.atan2(2, 3))).rotateY(-90.0f);
        TestUtil.assertMatrix4fEquals(m1, m2, 1E-15f);
        }
    }

}
