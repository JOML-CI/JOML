package org.joml;

import org.joml.Quaternionf;
import org.joml.QuaternionfInterpolator;
import org.joml.Vector3f;
import org.joml.Math;

import junit.framework.TestCase;

/**
 * Test class for {@link QuaternionfInterpolator}.
 * 
 * @author Kai Burjack
 */
public class QuaternionfInterpolatorTest extends TestCase {

    /**
     * Average between three quaternions, each with a weight of 1/3.
     */
    public static void testOneThird() {
        Quaternionf q0 = new Quaternionf().rotateX((float) Math.toRadians(90));
        Quaternionf q1 = new Quaternionf().rotateY((float) Math.toRadians(90));
        Quaternionf q2 = new Quaternionf().rotateZ((float) Math.toRadians(90));
        Quaternionf dest = new Quaternionf();
        QuaternionfInterpolator inter = new QuaternionfInterpolator();
        inter.computeWeightedAverage(new Quaternionf[] { q0, q1, q2 }, new float[] { 1.0f/3.0f, 1.0f/3.0f, 1.0f/3.0f }, 30, dest);
        Vector3f v = new Vector3f(0, 0, 1);
        dest.transform(v);
        assertEquals(1.0f, v.length(), 1E-6f);
        TestUtil.assertVector3fEquals(new Vector3f(2.0f/3.0f, -1.0f/3.0f, 2.0f/3.0f), v, 1E-6f);
    }

}
