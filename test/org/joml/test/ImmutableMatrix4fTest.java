package org.joml.test;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import org.joml.ImmutableException;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import junit.framework.TestCase;

public class ImmutableMatrix4fTest extends TestCase {

    public void testGetBuffer() {
        FloatBuffer fb = ByteBuffer.allocateDirect(16 << 2).order(ByteOrder.nativeOrder()).asFloatBuffer();
        Matrix4f m = new Matrix4f().assumeNothing();
        m.m00(2.0f);
        m.toImmutable().get(fb);
        assertEquals(2.0f, fb.get(0), 0);
    }

    public void testM00() {
        Matrix4f m = new Matrix4f().assumeNothing();
        m.m00(2.0f);
        assertEquals(2.0f, m.toImmutable().m00(), 0);
    }

    public void testM00_fail() {
        Matrix4f imm = new Matrix4f().assumeNothing().toImmutable();
        try {
            imm.m00(2.0f);
            fail();
        } catch (ImmutableException e) {
        }
    }

    public void testTranslation() {
        Matrix4f m = new Matrix4f().assumeNothing();
        Matrix4f imm = m.toImmutable();
        m.translation(new Vector3f(1, 2, 3));
        assertEquals(1.0f, imm.m30(), 0);
        assertEquals(2.0f, imm.m31(), 0);
        assertEquals(3.0f, imm.m32(), 0);
    }

    public void testTranslationFail() {
        Matrix4f m = new Matrix4f().assumeNothing();
        Matrix4f imm = m.toImmutable();
        try {
            imm.translation(new Vector3f(1, 2, 3));
        } catch (ImmutableException e) {
        }
    }

    public void testTranslate() {
        Matrix4f m = new Matrix4f().assumeNothing();
        Matrix4f imm = m.toImmutable();
        m.translate(new Vector3f(1, 2, 3));
        assertEquals(1.0f, imm.m30(), 0);
        assertEquals(2.0f, imm.m31(), 0);
        assertEquals(3.0f, imm.m32(), 0);
    }

    public void testTranslate2() {
        Matrix4f m = new Matrix4f().assumeNothing();
        Matrix4f imm = m.toImmutable();
        m.translate(1, 2, 3);
        assertEquals(1.0f, imm.m30(), 0);
        assertEquals(2.0f, imm.m31(), 0);
        assertEquals(3.0f, imm.m32(), 0);
    }

    public void testTranslateOther() {
        Matrix4f m = new Matrix4f().assumeNothing();
        Matrix4f m2 = new Matrix4f().assumeNothing();
        Matrix4f imm = m.toImmutable();
        imm.translate(1, 2, 3, m2);
        assertEquals(1.0f, m2.m30(), 0);
        assertEquals(2.0f, m2.m31(), 0);
        assertEquals(3.0f, m2.m32(), 0);
    }

    public void testTranslateOtherImmutable() {
        Matrix4f m1 = new Matrix4f().assumeNothing();
        Matrix4f m2 = new Matrix4f().assumeNothing();
        Matrix4f imm1 = m2.toImmutable();
        Matrix4f imm2 = m1.toImmutable();
        try {
            imm1.translate(1, 2, 3, imm2);
            fail();
        } catch (ImmutableException e) {
        }
    }

    public void testTranslateThis() {
        Matrix4f m = new Matrix4f().assumeNothing();
        Matrix4f imm = m.toImmutable();
        try {
            imm.translate(1, 2, 3);
            fail();
        } catch (ImmutableException e) {
        }
    }

    public void testTranslateThis2() {
        Matrix4f m = new Matrix4f().assumeNothing();
        Matrix4f imm = m.toImmutable();
        try {
            imm.translate(1, 2, 3, imm);
            fail();
        } catch (ImmutableException e) {
        }
    }

    public void testTranslate_fail() {
        Matrix4f imm = new Matrix4f().assumeNothing().toImmutable();
        try {
            imm.translate(new Vector3f(1, 2, 3));
            fail();
        } catch (ImmutableException e) {
        }
    }

    public void testMulThis() {
        Matrix4f m = new Matrix4f().assumeNothing();
        Matrix4f m2 = new Matrix4f();
        Matrix4f imm = m.toImmutable();
        try {
            imm.mul(m2);
            fail();
        } catch (ImmutableException e) {
        }
    }

    public void testMulThis2() {
        Matrix4f m = new Matrix4f().assumeNothing();
        Matrix4f m2 = new Matrix4f();
        Matrix4f imm = m.toImmutable();
        try {
            imm.mul(m2, imm);
            fail();
        } catch (ImmutableException e) {
        }
    }

    public void testMulMutable() {
        Matrix4f m = new Matrix4f().assumeNothing();
        Matrix4f m2 = new Matrix4f().assumeNothing();
        Matrix4f imm = m.toImmutable();
        imm.mul(m2, m);
    }

    public void testMulAffineThis() {
        Matrix4f m = new Matrix4f().assumeAffine();
        Matrix4f m2 = new Matrix4f().assumeAffine();
        Matrix4f imm = m.toImmutable();
        try {
            imm.mulAffine(m2);
            fail();
        } catch (ImmutableException e) {
        }
    }

    public void testMulAffineThis2() {
        Matrix4f m = new Matrix4f().assumeAffine();
        Matrix4f m2 = new Matrix4f().assumeAffine();
        Matrix4f imm = m.toImmutable();
        try {
            imm.mulAffine(m2, imm);
            fail();
        } catch (ImmutableException e) {
        }
    }

    public void testMulAffineParam() {
        Matrix4f m = new Matrix4f().scale(2);
        Matrix4f m2 = new Matrix4f().scale(3).toImmutable();
        Matrix4f m3 = new Matrix4f();
        m.mulAffine(m2, m3);
        Matrix4f expected = new Matrix4f().scale(6);
        TestUtil.assertMatrix4fEquals(expected, m3, 0);
    }

    public void testMulAffineImmutableDest() {
        Matrix4f m = new Matrix4f().scale(2);
        Matrix4f m2 = new Matrix4f().scale(3);
        Matrix4f m3 = new Matrix4f().toImmutable();
        try {
            m.mulAffine(m2, m3);
            fail();
        } catch (ImmutableException e) {
        }
    }

    public void testImmutableToImmutable() {
        Matrix4f m = new Matrix4f();
        Matrix4f imm = m.assumeNothing().toImmutable();
        Matrix4f imm2 = imm.toImmutable();
        try {
            imm2.translate(1, 2, 3, imm);
            fail();
        } catch (ImmutableException e) {
        }
    }

    public void testImmutableToImmutableIsSame() {
        Matrix4f m = new Matrix4f();
        Matrix4f imm = m.assumeNothing().toImmutable();
        Matrix4f imm2 = imm.toImmutable();
        assertSame(imm, imm2);
        assertNotSame(m, imm);
    }

    public void testSet() {
        Matrix4f m = new Matrix4f();
        Matrix4f imm = m.toImmutable();
        try {
            imm.set(m);
            fail();
        } catch (ImmutableException e) {
        }
    }

    public void testSwap() {
        Matrix4f m = new Matrix4f();
        Matrix4f imm = m.toImmutable();
        try {
            imm.swap(m);
            fail();
        } catch (ImmutableException e) {
        }
    }

    public void testSwap2() {
        Matrix4f m = new Matrix4f();
        m.m00(2.0f);
        Matrix4f m2 = new Matrix4f();
        Matrix4f imm = m2.toImmutable();
        try {
            m.swap(imm);
            fail();
        } catch (ImmutableException e) {
        }
        assertEquals(2.0f, m.m00(), 0);
        assertEquals(1.0f, imm.m00(), 0);
    }

    public void testIdentity() {
        Matrix4f m = new Matrix4f();
        Matrix4f imm = m.toImmutable();
        try {
            imm.identity();
            fail();
        } catch (ImmutableException e) {
        }
    }

    public void testSetVisible() {
        Matrix4f m1 = new Matrix4f().translate(1, 2, 3);
        Matrix4f m2 = new Matrix4f();
        Matrix4f imm = m2.toImmutable();
        m2.set(m1);
        TestUtil.assertMatrix4fEquals(m1, imm, 0);
    }

    public void testMutateExternally() {
        Matrix4f m = new Matrix4f();
        Matrix4f imm = m.toImmutable();
        Quaternionf q = new Quaternionf();
        try {
            q.get(imm);
            fail();
        } catch (ImmutableException e) {
        }
    }

    public void testRotation() {
        Matrix4f m = new Matrix4f();
        Matrix4f imm = m.toImmutable();
        try {
            imm.rotation(1, 2, 3, 4);
            fail();
        } catch (ImmutableException e) {
        }
    }

}
