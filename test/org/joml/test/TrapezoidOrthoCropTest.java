package org.joml.test;

import java.util.Random;

import junit.framework.TestCase;

import org.joml.Matrix4f;
import org.joml.TrapezoidOrthoCrop;
import org.joml.Vector4f;

/**
 * Tests for the {@link TrapezoidOrthoCrop} class.
 * 
 * @author Kai Burjack
 */
public class TrapezoidOrthoCropTest extends TestCase {

    public static void testRandomNdcWithinBounds() {
        Matrix4f camViewProj = new Matrix4f()
            .perspective((float) Math.toRadians(90.0), 1.0f, 0.1f, 10.0f)
            .lookAt(0, 0, 5,
                    0, 0, 0,
                    0, 1, 0);
        Matrix4f invCamViewProj = camViewProj.invert(new Matrix4f());
        Matrix4f lightView = new Matrix4f().lookAt(2, 4, 1,
                                                   0, 0, 0,
                                                   0, 0, -1);
        Matrix4f crop = new TrapezoidOrthoCrop().compute(camViewProj, lightView, Float.MAX_VALUE, 3.0f, new Matrix4f());
        Vector4f corner = new Vector4f();
        Random rnd = new Random(123L);
        for (int i = 0; i < 50000; i++) {
            float x = rnd.nextFloat() * 2.0f - 1.0f;
            float y = rnd.nextFloat() * 2.0f - 1.0f;
            float z = rnd.nextFloat() * 2.0f - 1.0f;
            corner.set(x, y, z, 1)
                .mulProject(invCamViewProj)
                .mulProject(lightView)
                .mul(crop);
            corner.x /= corner.w; // <- perspective divide only for x
            corner.y /= corner.w; // <- and y
            float d = 1E-5f;
            boolean withinBounds =
                    corner.x >= -1.0f - d && corner.x <= +1.0f + d &&
                    corner.y >= -1.0f - d && corner.y <= +1.0f + d &&
                    corner.z >= -1.0f - d && corner.z <= +1.0f + d;
            assertTrue(withinBounds);
        }
    }

}
