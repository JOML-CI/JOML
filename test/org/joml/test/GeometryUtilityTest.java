package org.joml.test;

import junit.framework.TestCase;

import org.joml.GeometryUtils;
import org.joml.Matrix4f;
import org.joml.Vector4f;

/**
 * Tests for the {@link GeometryUtils} class.
 * 
 * @author Kai Burjack
 */
public class GeometryUtilityTest extends TestCase {

    /**
     * Test computing the frustum planes with a simple orthographic projection matrix.
     */
    public void testCalculateFrustumOrtho() {
        Matrix4f m = new Matrix4f().ortho2D(-5, 1, -7, 2);
        Vector4f left = new Vector4f();
        Vector4f right = new Vector4f();
        Vector4f top = new Vector4f();
        Vector4f bottom = new Vector4f();
        Vector4f near = new Vector4f();
        Vector4f far = new Vector4f();
        GeometryUtils.calculateFrustumPlanes(m, left, right, bottom, top, near, far);

        Vector4f expectedLeft = new Vector4f(1, 0, 0, 5).normalize3();
        Vector4f expectedRight = new Vector4f(-1, 0, 0, 1).normalize3();
        Vector4f expectedTop = new Vector4f(0, -1, 0, 2).normalize3();
        Vector4f expectedBottom = new Vector4f(0, 1, 0, 7).normalize3();
        Vector4f expectedNear = new Vector4f(0, 0, -1, 1).normalize3();
        Vector4f expectedFar = new Vector4f(0, 0, 1, 1).normalize3();

        TestUtil.assertVector4fEquals(expectedLeft, left, 1E-5f);
        TestUtil.assertVector4fEquals(expectedRight, right, 1E-5f);
        TestUtil.assertVector4fEquals(expectedTop, top, 1E-5f);
        TestUtil.assertVector4fEquals(expectedBottom, bottom, 1E-5f);
        TestUtil.assertVector4fEquals(expectedNear, near, 1E-5f);
        TestUtil.assertVector4fEquals(expectedFar, far, 1E-4f);
    }

    /**
     * Test computing the frustum planes with a simple perspective projection matrix.
     */
    public void testCalculateFrustumPerspective() {
        Matrix4f m = new Matrix4f().perspective(90.0f, 1.0f, 0.1f, 100.0f);
        Vector4f left = new Vector4f();
        Vector4f right = new Vector4f();
        Vector4f top = new Vector4f();
        Vector4f bottom = new Vector4f();
        Vector4f near = new Vector4f();
        Vector4f far = new Vector4f();
        GeometryUtils.calculateFrustumPlanes(m, left, right, bottom, top, near, far);

        Vector4f expectedLeft = new Vector4f(1, 0, -1, 0).normalize3();
        Vector4f expectedRight = new Vector4f(-1, 0, -1, 0).normalize3();
        Vector4f expectedTop = new Vector4f(0, -1, -1, 0).normalize3();
        Vector4f expectedBottom = new Vector4f(0, 1, -1, 0).normalize3();
        Vector4f expectedNear = new Vector4f(0, 0, -1, -0.1f).normalize3();
        Vector4f expectedFar = new Vector4f(0, 0, 1, 100.0f).normalize3();

        TestUtil.assertVector4fEquals(expectedLeft, left, 1E-5f);
        TestUtil.assertVector4fEquals(expectedRight, right, 1E-5f);
        TestUtil.assertVector4fEquals(expectedTop, top, 1E-5f);
        TestUtil.assertVector4fEquals(expectedBottom, bottom, 1E-5f);
        TestUtil.assertVector4fEquals(expectedNear, near, 1E-5f);
        TestUtil.assertVector4fEquals(expectedFar, far, 1E-4f);
    }

    /**
     * Test computing the frustum planes with a combined view-projection matrix.
     */
    public void testCalcluateFrustumPerspectiveRotate() {
        /* Rotate the camera clock-wise 90 degrees around Y */
        Matrix4f m = new Matrix4f().perspective(90.0f, 1.0f, 0.1f, 100.0f).rotateY(90);
        Vector4f left = new Vector4f();
        Vector4f right = new Vector4f();
        Vector4f top = new Vector4f();
        Vector4f bottom = new Vector4f();
        Vector4f near = new Vector4f();
        Vector4f far = new Vector4f();
        GeometryUtils.calculateFrustumPlanes(m, left, right, bottom, top, near, far);

        Vector4f expectedLeft = new Vector4f(1, 0, 1, 0).normalize3();
        Vector4f expectedRight = new Vector4f(1, 0, -1, 0).normalize3();
        Vector4f expectedTop = new Vector4f(1, -1, 0, 0).normalize3();
        Vector4f expectedBottom = new Vector4f(1, 1, 0, 0).normalize3();
        Vector4f expectedNear = new Vector4f(1, 0, 0, -0.1f).normalize3();
        Vector4f expectedFar = new Vector4f(-1, 0, 0, 100.0f).normalize3();

        TestUtil.assertVector4fEquals(expectedLeft, left, 1E-5f);
        TestUtil.assertVector4fEquals(expectedRight, right, 1E-5f);
        TestUtil.assertVector4fEquals(expectedTop, top, 1E-5f);
        TestUtil.assertVector4fEquals(expectedBottom, bottom, 1E-5f);
        TestUtil.assertVector4fEquals(expectedNear, near, 1E-5f);
        TestUtil.assertVector4fEquals(expectedFar, far, 1E-4f);
    }

    /**
     * Test computing the frustum planes with a combined view-projection matrix with translation.
     */
    public void testCalcluateFrustumPerspectiveRotateTranslate() {
        /* Move the camera 5 units "up" and rotate it clock-wise 90 degrees around Y */
        Matrix4f m = new Matrix4f().perspective(90.0f, 1.0f, 0.1f, 100.0f).rotateY(90).translate(0, -5, 0);
        Vector4f left = new Vector4f();
        Vector4f right = new Vector4f();
        Vector4f top = new Vector4f();
        Vector4f bottom = new Vector4f();
        Vector4f near = new Vector4f();
        Vector4f far = new Vector4f();
        GeometryUtils.calculateFrustumPlanes(m, left, right, bottom, top, near, far);

        Vector4f expectedLeft = new Vector4f(1, 0, 1, 0).normalize3();
        Vector4f expectedRight = new Vector4f(1, 0, -1, 0).normalize3();
        Vector4f expectedTop = new Vector4f(1, -1, 0, 5).normalize3();
        Vector4f expectedBottom = new Vector4f(1, 1, 0, -5).normalize3();
        Vector4f expectedNear = new Vector4f(1, 0, 0, -0.1f).normalize3();
        Vector4f expectedFar = new Vector4f(-1, 0, 0, 100.0f).normalize3();

        TestUtil.assertVector4fEquals(expectedLeft, left, 1E-5f);
        TestUtil.assertVector4fEquals(expectedRight, right, 1E-5f);
        TestUtil.assertVector4fEquals(expectedTop, top, 1E-5f);
        TestUtil.assertVector4fEquals(expectedBottom, bottom, 1E-5f);
        TestUtil.assertVector4fEquals(expectedNear, near, 1E-5f);
        TestUtil.assertVector4fEquals(expectedFar, far, 1E-4f);
    }

}
