package org.joml.geometry;

import java.util.Arrays;
import java.util.function.BiPredicate;

import org.joml.Vector2f;

/**
 * Triangle shape.
 * 
 * Doesn't enforces size constraints.
 * 
 * @author dustContributor
 *
 */
@SuppressWarnings( "rawtypes" )
public final class Triangle extends Shape2D
{
	private static final BiPredicate[] FUNCS;

	static
	{
		FUNCS = new BiPredicate[Type.COUNT + Type.COUNT];
		// Initialize safely.
		Arrays.fill( FUNCS, Shape2D.NO_SHAPE_FOUND_FUNC );

		final BiPredicate<Triangle, AABR> iaabr = OpsBounds::intersects;
		final BiPredicate<Triangle, Circle> icircle = OpsBounds::intersects;
		// Not implemented.
		// final BiPredicate<Triangle, Triangle> itriangle = OpsBounds::intersects;

		FUNCS[Type.AABR] = iaabr;
		FUNCS[Type.CIRCLE] = icircle;
		// FUNCS[Type.TRIANGLE] = itriangle;

		final BiPredicate<Triangle, AABR> caabr = OpsBounds::contains;
		final BiPredicate<Triangle, Circle> ccircle = OpsBounds::contains;
		// Not implemented.
		// final BiPredicate<Triangle, Triangle> ctriangle = OpsBounds::contains;

		FUNCS[Type.COUNT + Type.AABR] = caabr;
		FUNCS[Type.COUNT + Type.CIRCLE] = ccircle;
		// FUNCS[Type.COUNT + Type.TRIANGLE] = ctriangle;
	}

	public int ax;
	public int ay;
	public int bx;
	public int by;
	public int cx;
	public int cy;

	public Triangle ()
	{
		// Everything is 0.
		super( Type.TRIANGLE, FUNCS );
	}

	@Override
	public final void minExtent ( final Vector2f dest )
	{
		dest.x = Math.min( ax, Math.min( bx, cx ) );
		dest.y = Math.min( ay, Math.min( by, cy ) );
	}

	@Override
	public final void maxExtent ( final Vector2f dest )
	{
		dest.x = Math.max( ax, Math.max( bx, cx ) );
		dest.y = Math.max( ay, Math.max( by, cy ) );
	}

	@Override
	public final boolean isInside ( final int bX, final int bY )
	{
		return OpsBounds.isInside( this, bX, bY );
	}
}
