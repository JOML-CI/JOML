package org.joml.test;

import junit.framework.TestCase;
import org.joml.Math;

/**
 * Tests for {@link Math#vecLength(double...)}, {@link Math#vecLength(float...)},
 * {@link Math#vecLengthSquared(double...)} and {@link Math#vecLengthSquared(float...)}
 * - addressing <a href="https://github.com/JOML-CI/JOML/issues/131">Issue #131</a>
 *
 * @author F. Neurath
 */
public class MathTest extends TestCase {

	public static void testDoubleVecLength() {
		// Integer value tests
		assertEquals(1., Math.vecLength(1), .0001);
		assertEquals(5., Math.vecLength(4, 3), .0001);
		assertEquals(6., Math.vecLength(2, -4, 4), .0001);
		assertEquals(3., Math.vecLength(2, -1, 0, -2), .0001);

		// Floating point value tests
		assertEquals(.7, Math.vecLength(.7), .0001);
		assertEquals(1., Math.vecLength(.5, .5, .5, .5), .0001);
		assertEquals(Math.sqrt(.41), Math.vecLength(.4, -.5), .0001);
		assertEquals(Math.sqrt(.3), Math.vecLength(.1, -.5, .2), .0001);
	}

	public static void testFloatVecLength() {
		// Integer value tests
		assertEquals(1.f, Math.vecLength(1f), .0001f);
		assertEquals(5.f, Math.vecLength(4f, 3f), .0001f);
		assertEquals(6.f, Math.vecLength(2f, -4f, 4f), .0001f);
		assertEquals(3.f, Math.vecLength(2f, -1f, 0f, -2f), .0001f);

		// Floating point value tests
		assertEquals(.7f, Math.vecLength(.7f), .0001f);
		assertEquals(1.f, Math.vecLength(.5f, .5f, .5f, .5f), .0001f);
		assertEquals((float) Math.sqrt(.41f), Math.vecLength(.4f, -.5f), .0001f);
		assertEquals((float) Math.sqrt(.3f), Math.vecLength(.1f, -.5f, .2f), .0001f);
	}

	public static void testDoubleVecLengthSq() {
		// Integer value tests
		assertEquals(1., Math.vecLengthSquared(1), .0001);
		assertEquals(25., Math.vecLengthSquared(4, 3), .0001);
		assertEquals(36., Math.vecLengthSquared(2, -4, 4), .0001);
		assertEquals(9., Math.vecLengthSquared(2, -1, 0, -2), .0001);

		// Floating point value tests
		assertEquals(.49, Math.vecLengthSquared(.7), .0001);
		assertEquals(1., Math.vecLengthSquared(.5, .5, .5, .5), .0001);
		assertEquals(.41, Math.vecLengthSquared(.4, -.5), .0001);
		assertEquals(.3, Math.vecLengthSquared(.1, -.5, .2), .0001);
	}

	public static void testFloatVecLengthSq() {
		// Integer value tests
		assertEquals(1.f, Math.vecLengthSquared(1f), .0001f);
		assertEquals(25.f, Math.vecLengthSquared(4f, 3f), .0001f);
		assertEquals(36.f, Math.vecLengthSquared(2f, -4f, 4f), .0001f);
		assertEquals(9.f, Math.vecLengthSquared(2f, -1f, 0f, -2f), .0001f);

		// Floating point value tests
		assertEquals(.49f, Math.vecLengthSquared(.7f), .0001f);
		assertEquals(1.f, Math.vecLengthSquared(.5f, .5f, .5f, .5f), .0001f);
		assertEquals(.41f, Math.vecLengthSquared(.4f, -.5f), .0001f);
		assertEquals(.3f, Math.vecLengthSquared(.1f, -.5f, .2f), .0001f);
	}
}
