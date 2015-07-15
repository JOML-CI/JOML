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

    public void testRotateZ() {
        Sequence seq = new Sequence();
        NativeMatrix4f nm = new NativeMatrix4f(seq);
        {
            nm.identity();
            nm.rotateZ((float) Math.PI);
        }
        seq.terminate();
        seq.call();
        Matrix4f actual = new Matrix4f();
        nm.get(actual);
        Matrix4f expected = new Matrix4f().rotateZ((float) Math.PI);
        TestUtil.assertMatrix4fEquals(expected, actual, 0.0f);
    }

    public void testRotateX() {
        Sequence seq = new Sequence();
        NativeMatrix4f nm = new NativeMatrix4f(seq);
        {
            nm.identity();
            nm.rotateX((float) Math.PI);
        }
        seq.terminate();
        seq.call();
        Matrix4f actual = new Matrix4f();
        nm.get(actual);
        Matrix4f expected = new Matrix4f().rotateX((float) Math.PI);
        TestUtil.assertMatrix4fEquals(expected, actual, 0.0f);
    }

    public void testMulMatrix() {
        Sequence seq = new Sequence();
        NativeMatrix4f nm = new NativeMatrix4f(seq);
        NativeMatrix4f nm2 = new NativeMatrix4f(seq);
        {
            nm.identity().rotateZ(0.123f);
            nm2.identity().rotateZ(0.23f);
            nm.mul(nm2);
        }
        seq.terminate();
        seq.call();
        Matrix4f actual = new Matrix4f();
        nm.get(actual);
        Matrix4f expected = new Matrix4f();
        new Matrix4f()
          .rotateZ(0.123f)
          .mul(new Matrix4f().rotateZ(0.23f), expected);
        TestUtil.assertMatrix4fEquals(expected, actual, 0.0f);
    }

}
