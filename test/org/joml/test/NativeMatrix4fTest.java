package org.joml.test;

import junit.framework.Assert;
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
        seq.call();
        Matrix4f actual = new Matrix4f();
        nm.get(actual);
        Matrix4f expected = new Matrix4f().rotateX((float) Math.PI);
        TestUtil.assertMatrix4fEquals(expected, actual, 0.0f);
    }

    public void testRotateY() {
        Sequence seq = new Sequence();
        NativeMatrix4f nm = new NativeMatrix4f(seq);
        {
            nm.identity();
            nm.rotateY((float) Math.PI);
        }
        seq.call();
        Matrix4f actual = new Matrix4f();
        nm.get(actual);
        Matrix4f expected = new Matrix4f().rotateY((float) Math.PI);
        TestUtil.assertMatrix4fEquals(expected, actual, 0.0f);
    }

    public void testScale() {
        Sequence seq = new Sequence();
        NativeMatrix4f nm = new NativeMatrix4f(seq);
        {
            nm.identity();
            nm.scale(2.0f).scale(3.0f);
        }
        seq.call();
        Matrix4f actual = new Matrix4f();
        nm.get(actual);
        Matrix4f expected = new Matrix4f().scale(2.0f).scale(3.0f);
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
        seq.call();
        Matrix4f actual = new Matrix4f();
        nm.get(actual);
        Matrix4f expected = new Matrix4f();
        new Matrix4f()
          .rotateZ(0.123f)
          .mul(new Matrix4f().rotateZ(0.23f), expected);
        TestUtil.assertMatrix4fEquals(expected, actual, 0.0f);
    }

    public void testWrongSequence() {
        Sequence seq = new Sequence();
        NativeMatrix4f nm = new NativeMatrix4f(seq);
        {
            nm.identity();
        }
        seq.call();
        {
            try {
                nm.rotateX(0.0f);
                Assert.fail("Should have thrown IllegalStateException: wrong sequence");
            } catch (IllegalStateException e) {
            }
        }
    }

    public void testWrongSequenceMore() {
        Sequence seq = new Sequence();
        NativeMatrix4f nm = new NativeMatrix4f(seq);
        {
            nm.identity();
        }
        seq.call();
        {
            try {
                nm.identity();
                nm.rotateX(0.0f);
                Assert.fail("Should have thrown IllegalStateException: wrong sequence");
            } catch (IllegalStateException e) {
            }
        }
    }

    /**
     * Calls a sequence of matrix operations on the same {@link Sequence} twice.
     * <p>
     * This should check that {@link Sequence#terminate()} is actually a no-op
     * when the {@link Sequence} is already terminated.
     * And this is so that client code can remain the same for both non-terminated
     * and terminated sequences.
     */
    public void testMulMatrixTwoTimes() {
        Sequence seq = new Sequence();
        NativeMatrix4f nm = new NativeMatrix4f(seq);
        NativeMatrix4f nm2 = new NativeMatrix4f(seq);
        for (int i = 0; i < 2; i++) {
            nm.identity().rotateZ(0.123f);
            nm2.identity().rotateZ(0.23f);
            nm.mul(nm2);
            seq.call();
        }
        seq.clear();
        Matrix4f actual = new Matrix4f();
        nm.get(actual);
        Matrix4f expected = new Matrix4f();
        new Matrix4f()
          .rotateZ(0.123f)
          .mul(new Matrix4f().rotateZ(0.23f), expected);
        TestUtil.assertMatrix4fEquals(expected, actual, 0.0f);
    }

}
