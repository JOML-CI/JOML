package org.joml.test;

import java.nio.ByteBuffer;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.joml.Matrix4f;
import org.joml.NativeMatrix4f;
import org.joml.NativeVector4f;
import org.joml.Quaternionf;
import org.joml.Sequence;
import org.joml.Vector4f;

public class NativeMatrix4fTest extends TestCase {

    public void testIdentity() {
        Sequence seq = new Sequence();
        NativeMatrix4f nm = new NativeMatrix4f(seq);
        {
            nm.identity();
        }
        seq.call();
        Matrix4f expected = new Matrix4f();
        Matrix4f actual = new Matrix4f();
        nm.get(actual);
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

    public void testScaleDest() {
        Sequence seq = new Sequence();
        NativeMatrix4f nm = new NativeMatrix4f(seq);
        NativeMatrix4f nm2 = new NativeMatrix4f(seq);
        {
            nm.identity().scale(2.0f).scale(3.0f, nm2);
        }
        seq.call();
        Matrix4f actual = new Matrix4f();
        nm2.get(actual);
        Matrix4f expected = new Matrix4f().scale(2.0f).scale(3.0f);
        TestUtil.assertMatrix4fEquals(expected, actual, 0.0f);
    }

    public void testVectorNegate() {
        Sequence seq = new Sequence();
        NativeVector4f nv = new NativeVector4f(1.0f, 2.0f, 3.0f, 4.0f, seq);
        {
            nv.negate();
        }
        seq.call();
        Vector4f v = new Vector4f();
        nv.get(v);
        TestUtil.assertVector4fEquals(new Vector4f(-1.0f, -2.0f, -3.0f, -4.0f), v, 0.0f);
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

    public void testSetCopy() {
        Sequence seq = new Sequence();
        NativeMatrix4f nm = new NativeMatrix4f(seq);
        NativeMatrix4f nm2 = new NativeMatrix4f(seq);
        {
            nm.identity().rotateX(0.123f);
            nm2.set(nm);
        }
        seq.call();
        Matrix4f actual = new Matrix4f();
        nm2.get(actual);
        Matrix4f expected = new Matrix4f().rotateX(0.123f);
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
        Matrix4f expected = m.transpose();
        TestUtil.assertMatrix4fEquals(expected, actual, 0.0f);
    }

    public void testMulMatrix() {
        Sequence seq = new Sequence();
        Matrix4f m1 = new Matrix4f().rotateX(0.345f).translate(12, -4, 5).scale(1.2f, 0.5f, 1.5f);
        Matrix4f m2 = new Matrix4f().rotateX(-0.715f).translate(61, 1, -0.5f).scale(0.2f, 15.4f, 13.2f);
        NativeMatrix4f nm = new NativeMatrix4f(seq).set(m1);
        NativeMatrix4f nm2 = new NativeMatrix4f(seq).set(m2);
        {
            nm.mul(nm2);
        }
        seq.call();
        Matrix4f actual = new Matrix4f();
        Matrix4f expected = new Matrix4f(m1).mul(m2);
        nm.get(actual);
        TestUtil.assertMatrix4fEquals(expected, actual, 0.0f);
    }

    public void testMatrixTranslationRotateScale() {
        Sequence seq = new Sequence(20002);
        Quaternionf q = new Quaternionf().rotateX(0.1234f).rotateY(0.5124f).rotateZ(0.01623f);
        Matrix4f expected = new Matrix4f();
        for (int i = 1; i < 1000000; i++)
        expected.translationRotateScale(i/100.0f+1, i/50.0f, i, q.x+0.001f*i, q.y, q.z, q.w-0.001f*i, 1.0f, 1.0f, 1.0f);

        long hstime1 = System.nanoTime();
        for (int i = 0; i < 20000; i++)
        expected.translationRotateScale(i/100.0f+1, i/50.0f, i, q.x+0.001f*i, q.y, q.z, q.w-0.001f*i, 1.0f, 1.0f, 1.0f);
        long hstime2 = System.nanoTime();
        System.err.println("Took: " + (hstime2 - hstime1) / 1E3 + " µs.");

        NativeMatrix4f nm = new NativeMatrix4f(seq);
        {
            for (int i = 0; i < 20000; i++)
            nm.translationRotateScale(i/100.0f+1, i/50.0f, i, q.x+0.001f*i, q.y, q.z, q.w-0.001f*i, 1.0f, 1.0f, 1.0f);
        }
        seq.call();
        long ntime1 = System.nanoTime();
        seq.call();
        long ntime2 = System.nanoTime();
        System.err.println("Took: " + (ntime2 - ntime1) / 1E3 + " µs.");
        System.err.println(((float)(hstime2-hstime1) - (float)(ntime2-ntime1)) / (ntime2-ntime1)*100.0f + "% faster");
        Matrix4f actual = new Matrix4f();
        nm.get(actual);
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
