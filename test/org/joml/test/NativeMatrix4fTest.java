package org.joml.test;

import junit.framework.TestCase;

import org.joml.Matrix4f;
import org.joml.NativeMatrix4f;
import org.joml.Sequence;

public class NativeMatrix4fTest extends TestCase {

    public void testIdentity() {
        Sequence seq = new Sequence();
        NativeMatrix4f nm = new NativeMatrix4f(seq);
        {
            nm.identity();
        }
        seq.terminate();
        seq.call();
        Matrix4f actual = new Matrix4f();
        nm.get(actual);
        Matrix4f expected = new Matrix4f();
        TestUtil.assertMatrix4fEquals(expected, actual, 0.0f);
    }

}
