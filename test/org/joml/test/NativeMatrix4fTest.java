package org.joml.test;

import java.nio.ByteBuffer;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.joml.Matrix4f;
import org.joml.NativeMatrix4f;
import org.joml.Sequence;

public class NativeMatrix4fTest extends TestCase {

    public void testIdentity() {
        Sequence seq = new Sequence(90002);
        NativeMatrix4f nm = new NativeMatrix4f(seq);
        {
            for (int i = 0; i < 90000; i++)
            nm.identity();
        }
        seq.call();
        long time1 = System.nanoTime();
        for (int i = 0; i < 10; i++) {
        seq.call();
        }
        long time2 = System.nanoTime();
        System.err.println((time2 - time1) / 1E3);
        
        Matrix4f m = new Matrix4f();
        time1 = System.nanoTime();
        for (int i = 0; i < 90000 * 10; i++)
        m.identity();
        time2 = System.nanoTime();
        System.err.println((time2 - time1) / 1E3);
        
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

    public void testTranslate() {
        Sequence seq = new Sequence();
        NativeMatrix4f nm = new NativeMatrix4f(seq);
        {
            nm.identity();
            nm.translate(2.0f, 3.0f, 4.0f).translate(1.0f, -5.0f, 0.5f);
        }
        seq.call();
        Matrix4f actual = new Matrix4f();
        nm.get(actual);
        Matrix4f expected = new Matrix4f().translate(2.0f, 3.0f, 4.0f).translate(1.0f, -5.0f, 0.5f);
        TestUtil.assertMatrix4fEquals(expected, actual, 0.0f);
    }

    public void testTranspose() {
        Sequence seq = new Sequence();
        Matrix4f m = new Matrix4f().rotateX((float) Math.PI);
        NativeMatrix4f nm = new NativeMatrix4f(seq);
        {
            nm.set(m).transpose();
        }
        seq.call();
        Matrix4f actual = new Matrix4f();
        nm.get(actual);
        System.err.println(actual);
        Matrix4f expected = m.transpose();
        System.err.println(expected);
        TestUtil.assertMatrix4fEquals(expected, actual, 0.0f);
    }

    public void testMulMatrix() {
        int num = 50;
        Sequence seq = new Sequence(8192 * 10);
        NativeMatrix4f nm = new NativeMatrix4f(seq);
        NativeMatrix4f nm2 = new NativeMatrix4f(seq);
        {
            nm.identity();
            nm2.identity();
            for (int i = 0; i < num; i++)
            nm.mul(nm2);
        }
        seq.call();
        long time1 = System.nanoTime();
        for (int i = 0; i < 1; i++) {
        seq.call();
        }
        long time2 = System.nanoTime();
        System.err.println((time2 - time1));
        Matrix4f m = new Matrix4f();
        Matrix4f m2 = new Matrix4f();
        for (int i = 0; i < 100000000; i++)
            m.mul(m2);
        time1 = System.nanoTime();
        for (int i = 0; i < num; i++)
          m.mul(m2);
        time2 = System.nanoTime();
        System.err.println((time2 - time1));
        Matrix4f m3 = new Matrix4f();
        nm.get(m3);
        TestUtil.assertMatrix4fEquals(m, m3, 0.0f);
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

    public void testUserSuppliedByteBufferOverFull() {
        ByteBuffer operations = ByteBuffer.allocateDirect(1);
        ByteBuffer arguments = ByteBuffer.allocateDirect(16);
        Sequence seq = new Sequence(operations, arguments);
        NativeMatrix4f nm = new NativeMatrix4f(seq);
        {
            try {
                nm.identity();
                nm.identity();
                Assert.fail("Should have thrown IllegalStateException: operations buffer full");
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
