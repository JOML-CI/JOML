package org.joml.test;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.joml.Matrix3f;
import org.joml.Vector2f;

public class Matrix3fTest extends TestCase {

    public void testRotate() {
        Matrix3f m = new Matrix3f().rotate((float) Math.toRadians(90));
        Vector2f expected = new Vector2f(-1.0f, 1.0f);
        Vector2f actual = new Vector2f(1.0f, 1.0f);
        m.transform(actual);
        Assert.assertEquals(expected.x, actual.x, 1E-7f);
        Assert.assertEquals(expected.y, actual.y, 1E-7f);
    }

    public void testRotateTo() {
        Vector2f from = new Vector2f(1.0f, 1.0f).normalize();
        Vector2f to = new Vector2f(-1.0f, 1.0f).normalize();
        Matrix3f m = new Matrix3f().rotateTo(from, to);
        m.transform(from);
        Assert.assertEquals(to.x, from.x, 1E-7f);
        Assert.assertEquals(to.y, from.y, 1E-7f);
    }

    public void testRotateTo2() {
        Vector2f from = new Vector2f(-1.0f, 1.0f).normalize();
        Vector2f to = new Vector2f(1.0f, 1.0f).normalize();
        Matrix3f m = new Matrix3f().rotateTo(from, to);
        m.transform(from);
        Assert.assertEquals(to.x, from.x, 1E-7f);
        Assert.assertEquals(to.y, from.y, 1E-7f);
    }

    public void testTranslate() {
        Matrix3f m = new Matrix3f().translate(3.0f, -7.0f);
        Vector2f expected = new Vector2f(4.0f, -6.0f);
        Vector2f actual = new Vector2f(1.0f, 1.0f);
        m.transform(actual);
        Assert.assertEquals(expected.x, actual.x, 1E-7f);
        Assert.assertEquals(expected.y, actual.y, 1E-7f);
    }

    public void testScale() {
        Matrix3f m = new Matrix3f().scale(3.0f, -2.0f);
        Vector2f expected = new Vector2f(1.5f, -4.0f);
        Vector2f actual = new Vector2f(0.5f, 2.0f);
        m.transform(actual);
        Assert.assertEquals(expected.x, actual.x, 1E-7f);
        Assert.assertEquals(expected.y, actual.y, 1E-7f);
    }

}
