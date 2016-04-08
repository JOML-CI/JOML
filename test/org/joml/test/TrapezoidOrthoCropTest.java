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
        Matrix4f camViewProj = new Matrix4f().perspective((float) Math.toRadians(90.0), 1.0f, 0.1f, 10.0f).lookAt(0, 0, 5, 0, 0, 0, 0, 1, 0);
        Matrix4f invCamViewProj = new Matrix4f();
        camViewProj.invert(invCamViewProj);
        Matrix4f lightView = new Matrix4f().lookAt(2.5f, 4, 0.7f, 0, 0, 0, 0, 0, -1);
        Matrix4f crop = new TrapezoidOrthoCrop().compute(camViewProj, lightView, 3.0f, new Matrix4f());
        Vector4f corner = new Vector4f();
        Random rnd = new Random(123L);
        float minX = Float.MAX_VALUE, minY = Float.MAX_VALUE, minZ = Float.MAX_VALUE;
        float maxX = -Float.MAX_VALUE, maxY = -Float.MAX_VALUE, maxZ = -Float.MAX_VALUE;
        for (int i = 0; i < 50000; i++) {
            float x = rnd.nextFloat() * 2.0f - 1.0f;
            float y = rnd.nextFloat() * 2.0f - 1.0f;
            float z = rnd.nextFloat() * 2.0f - 1.0f;
            corner.set(x, y, z, 1);
            invCamViewProj.transformProject(corner);
            lightView.transformProject(corner);
            crop.transform(corner);
            corner.x /= corner.w;
            corner.y /= corner.w;
            float d = 1E-5f;
            boolean withinBounds =
                    corner.x >= -1.0f - d && corner.x <= +1.0f + d &&
                    corner.y >= -1.0f - d && corner.y <= +1.0f + d &&
                    corner.z >= -1.0f - d && corner.z <= +1.0f + d;
            assertTrue(withinBounds);
            minX = minX < corner.x ? minX : corner.x;
            minY = minY < corner.y ? minY : corner.y;
            minZ = minZ < corner.z ? minZ : corner.z;
            maxX = maxX > corner.x ? maxX : corner.x;
            maxY = maxY > corner.y ? maxY : corner.y;
            maxZ = maxZ > corner.z ? maxZ : corner.z;
        }
    }

}
