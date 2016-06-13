package org.joml.test;

import junit.framework.TestCase;

import org.joml.Matrix3x2f;
import org.joml.Vector2f;

public class Matrix3x2Test extends TestCase {

	public static void testRotateTranslateInvert() {
		Matrix3x2f m = new Matrix3x2f().rotate((float) Math.toRadians(45)).translate(1, 4);
		Vector2f actual = new Vector2f(1, 2);
		Vector2f expected = new Vector2f(actual);
		m.transformPosition(actual);
		m.invert();
		m.transformPosition(actual);
		TestUtil.assertVector2fEquals(expected, actual, 1E-6f);
	}

	public static void testView() {
		Matrix3x2f view = new Matrix3x2f().view(-10, 5, -2, 10);
		Vector2f min = new Vector2f();
		Vector2f max = new Vector2f();
		view.viewArea(min, max);
		TestUtil.assertVector2fEquals(new Vector2f(-10, -2), min, 1E-6f);
		TestUtil.assertVector2fEquals(new Vector2f(5, 10), max, 1E-6f);
	}

}
