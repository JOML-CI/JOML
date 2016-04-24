package org.joml.test;

import java.util.ArrayList;
import java.util.List;

import org.joml.SutherlandHodgman;
import org.joml.Vector2f;

import junit.framework.TestCase;

public class SutherlandHodgmanTest extends TestCase {

    private static List list(float[] array) {
        List res = new ArrayList(array.length / 2);
        for (int i = 0; i < array.length / 2; i++) {
            res.add(new Vector2f(array[2*i], array[2*i+1]));
        }
        return res;
    }

    public static void testCutEdges() {
        List res = SutherlandHodgman.compute(list(new float[] {-1, -1, 1, -1, 1, 1, -1, 1}), list(new float[] {-1, 0, 0, -1, 1, 0, 0, 1}), new ArrayList());
        assertEquals(4, res.size());
        Vector2f expected;
        expected = new Vector2f(0, 1);
        TestUtil.assertVector2fEquals(expected, (Vector2f)res.get(0), 1E-6f);
        expected = new Vector2f(-1, 0);
        TestUtil.assertVector2fEquals(expected, (Vector2f)res.get(1), 1E-6f);
        expected = new Vector2f(0, -1);
        TestUtil.assertVector2fEquals(expected, (Vector2f)res.get(2), 1E-6f);
        expected = new Vector2f(1, 0);
        TestUtil.assertVector2fEquals(expected, (Vector2f)res.get(3), 1E-6f);
    }

}
