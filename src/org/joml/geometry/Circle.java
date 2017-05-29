package org.joml.geometry;

import static java.lang.Math.max;

import java.util.Arrays;
import java.util.function.BiPredicate;

import org.joml.Vector2f;

/**
 * Bounding circle.
 * 
 * Minimum radius is 1, minimum diameter is 2.
 * 
 * @author dustContributor
 *
 */
@SuppressWarnings( { "rawtypes" } )
public final class Circle extends Shape2D
{
	private static final BiPredicate[] FUNCS;

	static
	{
		FUNCS = new BiPredicate[Type.COUNT + Type.COUNT];
		// Initialize safely.
		Arrays.fill( FUNCS, Shape2D.NO_SHAPE_FOUND_FUNC );

		final BiPredicate<Circle, AABR> iaabr = OpsBounds::intersects;
		final BiPredicate<Circle, Circle> icircle = OpsBounds::intersects;
		final BiPredicate<Circle, Triangle> itriangle = OpsBounds::intersects;

		FUNCS[Type.AABR] = iaabr;
		FUNCS[Type.CIRCLE] = icircle;
		FUNCS[Type.TRIANGLE] = itriangle;

		final BiPredicate<Circle, AABR> caabr = OpsBounds::contains;
		final BiPredicate<Circle, Circle> ccircle = OpsBounds::contains;
		final BiPredicate<Circle, Triangle> ctriangle = OpsBounds::contains;

		FUNCS[Type.COUNT + Type.AABR] = caabr;
		FUNCS[Type.COUNT + Type.CIRCLE] = ccircle;
		FUNCS[Type.COUNT + Type.TRIANGLE] = ctriangle;
	}

	public int radius;
	public int radiusSqrd;

	public Circle ()
	{
		// Everything is 0.
		this( 0, 0, 0 );
	}

	public Circle ( final Circle circle )
	{
		this( circle.x, circle.y, circle.radius );
	}

	public Circle ( final int x, final int y, final int radius )
	{
		super( x, y, Type.CIRCLE, FUNCS );
		setRadius( radius );
	}

	@Override
	public final String toString ()
	{
		final String newLine = System.lineSeparator();
		final StringBuilder str = new StringBuilder( 50 );

		str.append( "x: " ).append( x ).append( " y: " ).append( y );
		str.append( newLine );
		str.append( "radiusSquared: " ).append( radiusSqrd ).append( " radius : " ).append( radius );
		str.append( newLine );

		return str.toString();
	}

	@Override
	public final boolean isInside ( final int bX, final int bY )
	{
		return OpsBounds.isInside( this, bX, bY );
	}

	public final void setRadius ( int radius )
	{
		radius = max( radius, 1 );

		this.radius = radius;
		this.radiusSqrd = radius * radius;
	}

	@Override
	public final void minExtent ( final Vector2f dest )
	{
		dest.x = x - radius;
		dest.y = y - radius;
	}

	@Override
	public final void maxExtent ( final Vector2f dest )
	{
		dest.x = x + radius;
		dest.y = y + radius;
	}

	public final int leftExtent ()
	{
		return x - radius;
	}

	public final int rightExtent ()
	{
		return x + radius;
	}

	public final int lowerExtent ()
	{
		return y - radius;
	}

	public final int upperExtent ()
	{
		return y + radius;
	}

}