package org.joml.test;

import java.util.Random;

import junit.framework.TestCase;

import org.joml.Matrix4f;
import org.joml.TrapezoidOrthoCrop;
import org.joml.Vector3f;

/**
 * Tests for the {@link TrapezoidOrthoCrop} class.
 * 
 * @author Kai Burjack
 */
public class TrapezoidOrthoCropTest extends TestCase {

    public static void testRandomNdcWithinBounds() {
        Matrix4f camViewProj = new Matrix4f().perspective((float) Math.toRadians(90.0), 1.0f, 0.1f, 10.0f).lookAt(0, 0, 5, 0, 0, 0, 0, 1, 0);
        Matrix4f invCamViewProj = new Matrix4f();
        camViewProj.invert(invCamViewProj);
        Matrix4f lightView = new Matrix4f().lookAt(0, 5, 0, 0, 0, 0, 0, 0, -1);
        Matrix4f crop = new TrapezoidOrthoCrop().compute(camViewProj, lightView, 3.0f, new Matrix4f());
        Vector3f corner = new Vector3f();
        Random rnd = new Random();
        for (int i = 0; i < 5000; i++) {
            float x = rnd.nextFloat() * 2.0f - 1.0f;
            float y = rnd.nextFloat() * 2.0f - 1.0f;
            float z = rnd.nextFloat() * 2.0f - 1.0f;
            corner.set(x, y, z);
            invCamViewProj.transformProject(corner);
            lightView.transformProject(corner);
            crop.transformProject(corner);
            float d = 1E-5f;
            boolean withinBounds =
                    corner.x >= -1.0f - d && corner.x <= +1.0f + d &&
                    corner.y >= -1.0f - d && corner.y <= +1.0f + d &&
                    corner.z >= -1.0f - d && corner.z <= +1.0f + d;
            assertTrue(withinBounds);
        }
    }

}
