package org.joml.geometry;

import java.util.function.BiPredicate;

import org.joml.Vector2f;

/**
 * Abstract shape class from which all 2D shapes inherit.
 * 
 * @author dustContributor
 *
 */
@SuppressWarnings( { "unchecked", "rawtypes" } )
public abstract class Shape2D
{
	/**
	 * Type byte enum to hold the type of each Shape2D subclass.
	 * 
	 * Enums would have been more safe but they incur a 4 byte cost and an
	 * indirection to fetch each enum instance's ordinal.
	 * 
	 * @author dustContributor
	 *
	 */
	public static final class Type
	{
		public static final byte AABR = 0;
		public static final byte CIRCLE = 1;
		public static final byte TRIANGLE = 2;

		public static final int COUNT = 3;

		private Type ()
		{
			// Empty.
		}
	}

	/**
	 * If there isn't an implementation for a shape, this function should be set
	 * in the function array.
	 */
	protected static final BiPredicate NO_SHAPE_FOUND_FUNC = ( a, b ) -> false;

	private final BiPredicate[] funcs;

	public final byte type;

	public int x;
	public int y;

	public Shape2D ( final byte type, final BiPredicate[] funcs )
	{
		this( 0, 0, type, funcs );
	}

	public Shape2D ( final int x, final int y, final byte type, final BiPredicate[] funcs )
	{
		super();

		if ( Type.COUNT <= Byte.toUnsignedInt( type ) )
		{
			throw new IllegalArgumentException( "Type is out of range" );
		}

		if ( funcs == null )
		{
			throw new NullPointerException( "Can't pass null functions" );
		}

		if ( (Type.COUNT + Type.COUNT) < funcs.length )
		{
			throw new IllegalArgumentException( "Not enough functions passed" );
		}

		// Now everything is validated.
		this.x = x;
		this.y = y;
		this.type = type;
		this.funcs = funcs;
	}

	public boolean isAligned ( final Shape2D boundingShape )
	{
		return OpsBounds.areAligned( this, boundingShape );
	}

	public abstract void minExtent ( Vector2f dest );

	public abstract void maxExtent ( Vector2f dest );

	public abstract boolean isInside ( int bX, int bY );

	public final boolean intersects ( final AABR aabr )
	{
		return intersectsImpl( Type.AABR, aabr );
	}

	public final boolean intersects ( final Circle circle )
	{
		return intersectsImpl( Type.CIRCLE, circle );
	}

	public final boolean intersects ( final Triangle triangle )
	{
		return intersectsImpl( Type.TRIANGLE, triangle );
	}

	public final boolean contains ( final AABR aabr )
	{
		return containsImpl( Type.AABR, aabr );
	}

	public final boolean contains ( final Circle circle )
	{
		return containsImpl( Type.CIRCLE, circle );
	}

	public final boolean contains ( final Triangle triangle )
	{
		return containsImpl( Type.TRIANGLE, triangle );
	}

	public final boolean intersects ( final Shape2D shape )
	{
		return intersectsImpl( shape.type, shape );
	}

	public final boolean contains ( final Shape2D shape )
	{
		return containsImpl( shape.type, shape );
	}

	private final boolean intersectsImpl ( final byte type, final Shape2D shape )
	{
		return funcs[Byte.toUnsignedInt( type )].test( this, shape );
	}

	private final boolean containsImpl ( final byte type, final Shape2D shape )
	{
		return funcs[Type.COUNT + Byte.toUnsignedInt( type )].test( this, shape );
	}
}