package org.joml.geometry;

import java.util.Arrays;
import java.util.function.BiPredicate;

import org.joml.Vector2f;

/**
 * Axis aligned bounding rectangle.
 * 
 * On both axis minimum half size is 1.
 * 
 * @author dustContributor
 *
 */
@SuppressWarnings( "rawtypes" )
public final class AABR extends Shape2D
{
	private static final BiPredicate[] FUNCS;

	static
	{
		FUNCS = new BiPredicate[Type.COUNT + Type.COUNT];
		// Initialize safely.
		Arrays.fill( FUNCS, Shape2D.NO_SHAPE_FOUND_FUNC );

		final BiPredicate<AABR, AABR> iaabr = OpsBounds::intersects;
		final BiPredicate<AABR, Circle> icircle = OpsBounds::intersects;
		final BiPredicate<AABR, Triangle> itriangle = OpsBounds::intersects;

		FUNCS[Type.AABR] = iaabr;
		FUNCS[Type.CIRCLE] = icircle;
		FUNCS[Type.TRIANGLE] = itriangle;

		final BiPredicate<AABR, AABR> caabr = OpsBounds::contains;
		final BiPredicate<AABR, Circle> ccircle = OpsBounds::contains;
		final BiPredicate<AABR, Triangle> ctriangle = OpsBounds::contains;

		FUNCS[Type.COUNT + Type.AABR] = caabr;
		FUNCS[Type.COUNT + Type.CIRCLE] = ccircle;
		FUNCS[Type.COUNT + Type.TRIANGLE] = ctriangle;
	}

	public int halfSizeX;
	public int halfSizeY;

	public AABR ()
	{
		// Everything is 0.
		this( 0, 0, 0 );
	}

	public AABR ( final AABR aabr )
	{
		this( aabr.x, aabr.y, aabr.halfSizeX, aabr.halfSizeY );
	}

	public AABR ( final int x, final int y, final int halfSize )
	{
		this( x, y, halfSize, halfSize );
	}

	public AABR ( final int x, final int y, final int halfSizeX, final int halfSizeY )
	{
		super( x, y, Type.AABR, FUNCS );
		setHalfSizeX( halfSizeX );
		setHalfSizeY( halfSizeY );
	}

	@Override
	public final String toString ()
	{
		final String newLine = System.lineSeparator();
		final StringBuilder str = new StringBuilder( 50 );

		str.append( "x: " ).append( x ).append( " y: " ).append( y );
		str.append( newLine );
		str.append( "halfSizeX: " ).append( halfSizeX ).append( " halfSizeY: " ).append( halfSizeY );
		str.append( newLine );

		return str.toString();
	}

	@Override
	public final boolean isInside ( final int bX, final int bY )
	{
		return OpsBounds.isInside( this, bX, bY );
	}

	@Override
	public final void minExtent ( final Vector2f dest )
	{
		dest.x = leftExtent();
		dest.y = lowerExtent();
	}

	@Override
	public final void maxExtent ( final Vector2f dest )
	{
		dest.x = rightExtent();
		dest.y = upperExtent();
	}

	public final int leftExtent ()
	{
		return x - halfSizeX;
	}

	public final int rightExtent ()
	{
		return x + halfSizeX;
	}

	public final int lowerExtent ()
	{
		return y - halfSizeY;
	}

	public final int upperExtent ()
	{
		return y + halfSizeY;
	}

	public final void lowerLeftCorner ( final Vector2f dest )
	{
		dest.set( leftExtent(), lowerExtent() );
	}

	public final void lowerRightCorner ( final Vector2f dest )
	{
		dest.set( rightExtent(), lowerExtent() );
	}

	public final void upperLeftCorner ( final Vector2f dest )
	{
		dest.set( leftExtent(), upperExtent() );
	}

	public final void upperRightCorner ( final Vector2f dest )
	{
		dest.set( rightExtent(), upperExtent() );
	}

	public final void setHalfSizeX ( final int halfSizeX )
	{
		this.halfSizeX = Math.max( halfSizeX, 1 );
	}

	public final void setHalfSizeY ( final int halfSizeY )
	{
		this.halfSizeY = Math.max( halfSizeY, 1 );
	}

}