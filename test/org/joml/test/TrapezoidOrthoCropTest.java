package org.joml.test;

import junit.framework.TestCase;

import org.joml.Matrix4f;
import org.joml.TrapezoidOrthoCrop;
import org.joml.Vector4f;
import org.joml.Math;

/**
 * Tests for the {@link TrapezoidOrthoCrop} class.
 * 
 * @author Kai Burjack
 */
public class TrapezoidOrthoCropTest extends TestCase {

    public static void testRandomNdcWithinBounds() {
        Matrix4f camViewProj = new Matrix4f()
            .perspective((float) Math.toRadians(90.0), 1.0f, 5f, 8.0f)
            .lookAt(0, 1, 5,
                    0, 0, 0,
                    0, 1, 0);
        Matrix4f invCamViewProj = camViewProj.invert(new Matrix4f());
        Matrix4f lightView = new Matrix4f().lookAt(0, 2, 5,
                                                   0, 0, 0,
                                                   0, 1, 0);
        float minLightZ = -17.016415f;
        float maxLightZ = 6.441378f;
        Matrix4f zscale = new Matrix4f().ortho(-1, 1, -1, 1, -maxLightZ, -minLightZ);
        Matrix4f crop = new TrapezoidOrthoCrop().compute(camViewProj, lightView, new Matrix4f());
        Vector4f corner = new Vector4f();
        for (int i = 0; i < 8; i++) {
            float x = ((i & 1) << 1) - 1.0f;
            float y = (((i >>> 1) & 1) << 1) - 1.0f;
            float z = (((i >>> 2) & 1) << 1) - 1.0f;
            corner.set(x, y, z, 1)
                .mulProject(invCamViewProj)
                .mul(lightView)
                .mul(crop);
            corner.x /= corner.w; // <- perspective divide only for x
            corner.y /= corner.w; // <- and y
            corner.w = 1.0f;
            corner.mul(zscale);
            float d = 1E-5f;
            boolean withinBounds =
                    corner.x >= -1.0f - d && corner.x <= +1.0f + d &&
                    corner.y >= -1.0f - d && corner.y <= +1.0f + d &&
                    corner.z >= -1.0f - d && corner.z <= +1.0f + d;
            assertTrue(withinBounds);
        }
    }

    public static void testPerspectiveLightWithinBounds() {
        Matrix4f camViewProj = new Matrix4f()
            .perspective((float) Math.toRadians(90.0), 1.0f, 0.1f, 10.0f)
            .lookAt(0, 0, 5,
                    0, 0, 0,
                    0, 1, 0);
        Matrix4f invCamViewProj = camViewProj.invert(new Matrix4f());
        Matrix4f lightView = new Matrix4f().lookAt(2, 15, 1,
                                                   0, 0, 0,
                                                   0, 0, -1);
        Matrix4f crop = new TrapezoidOrthoCrop().compute(camViewProj, lightView, new Matrix4f());
        Matrix4f lightProj = new Matrix4f().perspective((float) Math.toRadians(90), 1.0f, 4.0f, 27.0f);
        Vector4f corner = new Vector4f();
        for (int i = 0; i < 8; i++) {
            float x = ((i & 1) << 1) - 1.0f;
            float y = (((i >>> 1) & 1) << 1) - 1.0f;
            float z = (((i >>> 2) & 1) << 1) - 1.0f;
            corner.set(x, y, z, 1)
                .mulProject(invCamViewProj)
                .mulProject(lightView)
                .mulProject(lightProj)
                .mul(crop);
            corner.x /= corner.w; // <- perspective divide only for x
            corner.y /= corner.w; // <- and y
            corner.w = 1.0f;
            float d = 1E-5f;
            boolean withinBounds =
                    corner.x >= -1.0f - d && corner.x <= +1.0f + d &&
                    corner.y >= -1.0f - d && corner.y <= +1.0f + d &&
                    corner.z >= -1.0f - d && corner.z <= +1.0f + d;
            assertTrue(withinBounds);
        }
    }

}
