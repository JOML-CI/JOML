package org.joml.geometry;

/**
 * Class to hold various bounding shape functions such as checking if point is
 * inside shape, if shape is inside another shape, or if two shapes intersect.
 * 
 * @author dustContributor
 */
public final class OpsBounds
{
	/** Non-instantiable class */
	private OpsBounds ()
	{
		// Empty.
	}

	/**
	 * Determines if point (bX, bY) is inside AABR 'a'.
	 * 
	 * @param a AABR.
	 * @param bX point X coordinate.
	 * @param bY point Y coordinate.
	 * @return {@literal true} if point (bX, bY) is inside AABR 'a',
	 *         {@literal false} otherwise.
	 */
	public static final boolean isInside ( final AABR a, final int bX, final int bY )
	{
		int dX = a.x - bX;
		int dY = a.y - bY;
		int hX = a.halfSizeX;
		int hY = a.halfSizeY;

		dX *= dX;
		dY *= dY;
		hX *= hX;
		hY *= hY;

		return (dX < hX) && (dY < hY);
	}

	/**
	 * Determines if point (bX, bY) is inside Circle 'a'.
	 * 
	 * @param a Circle.
	 * @param bX point X coordinate.
	 * @param bY point Y coordinate.
	 * @return {@literal true} if point (bX, bY) is inside Circle 'a',
	 *         {@literal false} otherwise.
	 */
	public static final boolean isInside ( final Circle a, final int bX, final int bY )
	{
		int dx = a.x - bX;
		int dy = a.y - bY;
		int distSqrd = (dx * dx) + (dy * dy);

		return distSqrd <= a.radiusSqrd;
	}

	/**
	 * Determines if point (bX, bY) is inside Triangle 'a'.
	 * 
	 * @param a Triangle
	 * @param bx point X coordinate.
	 * @param by point Y coordinate.
	 * @return {@literal true} if point (bX, bY) is inside Triangle 'a',
	 *         {@literal false} otherwise.
	 */
	public static final boolean isInside ( final Triangle a, final int bx, final int by )
	{
		// // Compute vectors
		// v0 = C - A
		// v1 = B - A
		// v2 = P - A
		//
		// // Compute dot products
		// dot00 = dot(v0, v0)
		// dot01 = dot(v0, v1)
		// dot02 = dot(v0, v2)
		// dot11 = dot(v1, v1)
		// dot12 = dot(v1, v2)
		//
		// // Compute barycentric coordinates
		// invDenom = 1 / (dot00 * dot11 - dot01 * dot01)
		// u = (dot11 * dot02 - dot01 * dot12) * invDenom
		// v = (dot00 * dot12 - dot01 * dot02) * invDenom
		//
		// // Check if point is in triangle
		// return (u >= 0) && (v >= 0) && (u + v < 1)

		int v0x = a.cx - a.ax;
		int v0y = a.cy - a.ay;
		int v1x = a.bx - a.ax;
		int v1y = a.by - a.ay;
		int v2x = bx - a.ax;
		int v2y = by - a.ay;

		int dot00 = v0x * v0x + v0y * v0y;
		int dot01 = v0x * v1x + v0y * v1y;
		int dot02 = v0x * v2x + v0y * v2y;
		int dot11 = v1x * v1x + v1y * v1y;
		int dot12 = v1x * v2x + v1y * v2y;

		int tmpa = dot00 * dot11 - dot01 * dot01;
		int tmpb = dot11 * dot02 - dot01 * dot12;
		int tmpc = dot00 * dot12 - dot01 * dot02;

		final float invDenorm = 1.0f / tmpa;

		final float u = tmpb * invDenorm;
		final float v = tmpc * invDenorm;

		return (u >= 0.0f) && (v >= 0.0f) && (u + v < 1.0f);
	}

	/**
	 * Determines if AABR 'b' is contained within AABR 'a'.
	 * 
	 * @param a AABR 'a'.
	 * @param b AABR 'b'.
	 * @return true if AABR 'b' is contained within AABR 'a'. False otherwise.
	 */
	public static final boolean contains ( final AABR a, final AABR b )
	{
		int aRight = a.rightExtent();
		int aLeft = a.leftExtent();
		int aUp = a.upperExtent();
		int aDown = a.lowerExtent();

		int bRight = b.rightExtent();
		int bLeft = b.leftExtent();
		int bUp = b.upperExtent();
		int bDown = b.lowerExtent();

		return !((bRight > aRight) || (bLeft < aLeft) || (bUp > aUp) || (bDown < aDown));
	}

	/**
	 * Determines if Circle 'b' is contained within Circle 'a'.
	 * 
	 * @param a Circle 'a'.
	 * @param b Circle 'b'.
	 * @return true if Circle 'b' is contained within Circle 'a'. False otherwise.
	 */
	public static final boolean contains ( final Circle a, final Circle b )
	{
		int dx = a.x - b.x;
		int dy = a.y - b.y;
		int distSqrd = (dx * dx) + (dy * dy);

		return distSqrd + b.radiusSqrd <= a.radiusSqrd;
	}

	/**
	 * Determines if Circle 'b' is contained within AABR 'a'.
	 * 
	 * @param a AABR 'a'.
	 * @param b Circle 'b'.
	 * @return true if Circle 'b' is contained within AABR 'a'. False otherwise.
	 */
	public static final boolean contains ( final AABR a, final Circle b )
	{
		int aRight = a.rightExtent();
		int aLeft = a.leftExtent();
		int aUp = a.upperExtent();
		int aDown = a.lowerExtent();

		int bRight = b.rightExtent();
		int bLeft = b.leftExtent();
		int bUp = b.upperExtent();
		int bDown = b.lowerExtent();

		return !((bRight > aRight) || (bLeft < aLeft) || (bUp > aUp) || (bDown < aDown));
	}

	public static final boolean contains ( final Circle a, final AABR b )
	{
		// If AABR center isn't inside the circle.
		if ( !isInside( a, b.x, b.y ) )
			return false;

		// Corners of the AABR.
		int bxlft = b.leftExtent();
		int bxrgt = b.rightExtent();
		int bydwn = b.lowerExtent();
		int byup = b.upperExtent();

		// All corners need to be inside the Circle.
		return isInside( a, bxlft, bydwn ) && isInside( a, bxrgt, bydwn )
				&& isInside( a, bxlft, byup ) && isInside( a, bxrgt, byup );
	}

	/**
	 * Determines if Triangle 'b' is contained within AABR 'a'.
	 * 
	 * @param a
	 * @param b
	 * @return true if 'b' is contained within AABR 'a'. False otherwise.
	 */
	public static final boolean contains ( final AABR a, final Triangle b )
	{
		return isInside( a, b.ax, b.ay ) && isInside( a, b.bx, b.by ) && isInside( a, b.cx, b.cy );
	}

	/**
	 * Determines if Triangle 'b' is contained within Circle 'a'.
	 * 
	 * @param a
	 * @param b
	 * @return true if 'b' is contained within Circle 'a'. False otherwise.
	 */
	public static final boolean contains ( final Circle a, final Triangle b )
	{
		return isInside( a, b.ax, b.ay ) && isInside( a, b.bx, b.by ) && isInside( a, b.cx, b.cy );
	}

	/**
	 * Determines if AABR 'b' is contained within Triangle 'a'.
	 * 
	 * @param a
	 * @param b
	 * @return true if 'b' is contained within 'a', false otherwise.
	 */
	public static final boolean contains ( final Triangle a, final AABR b )
	{
		// Compute extents only once.
		final int minx = b.leftExtent();
		final int miny = b.lowerExtent();
		final int maxx = b.rightExtent();
		final int maxy = b.upperExtent();
		// Check if all corners are inside the triangle.
		return isInside( a, minx, miny ) &&
				isInside( a, maxx, maxy ) &&
				isInside( a, minx, maxy ) &&
				isInside( a, maxx, miny );
	}

	/**
	 * Determines if Circle 'b' is contained within Triangle 'a'. <br>
	 * <b>NOTE</b>: This treats the circle as an {@link AABR}, so its approximate
	 * and conservative.
	 * 
	 * @param a
	 * @param b
	 * @return true if 'b' is contained within 'a', false otherwise.
	 */
	public static final boolean contains ( final Triangle a, final Circle b )
	{
		// Compute extents only once.
		final int minx = b.leftExtent();
		final int miny = b.lowerExtent();
		final int maxx = b.rightExtent();
		final int maxy = b.upperExtent();
		// Check if all corners are inside the triangle.
		return isInside( a, minx, miny ) &&
				isInside( a, maxx, maxy ) &&
				isInside( a, minx, maxy ) &&
				isInside( a, maxx, miny );
	}

	public static final boolean intersects ( final AABR a, final AABR b )
	{
		int dX = Math.abs( a.x - b.x );
		int dY = Math.abs( a.y - b.y );
		int dhx = a.halfSizeX + b.halfSizeX;
		int dhy = a.halfSizeY + b.halfSizeY;

		return (dX <= dhx && dY <= dhy);
	}

	/**
	 * Determines if Circle 'a' intersects with Circle 'b'.
	 * 
	 * @param a first Circle.
	 * @param b second Circle.
	 * @return true if both Circle intersect, false otherwise.
	 */
	public static final boolean intersects ( final Circle a, final Circle b )
	{
		int rarb = a.radius + b.radius;
		int dx = a.x - b.x;
		int dy = a.y - b.y;
		int distSqrd = (dx * dx) + (dy * dy);

		return distSqrd < (rarb * rarb);
	}

	/**
	 * Determines if Circle 'a' intersects with AABR 'b'.
	 * 
	 * @param a Circle.
	 * @param b AABR.
	 * @return true if both shapes intersect, false otherwise.
	 */
	public static final boolean intersects ( final Circle a, final AABR b )
	{
		return intersects( b, a );
	}

	/**
	 * Determines if AABR 'a' intersects with Circle 'b'.
	 * 
	 * @param a AABR.
	 * @param b Circle.
	 * @return true if both shapes intersect, false otherwise.
	 */
	public static final boolean intersects ( final AABR a, final Circle b )
	{
		// If Circle center is inside the AABR.
		if ( isInside( a, b.x, b.y ) )
			return true;

		// Clamp corners of the AABR.
		int pX = clamp( a.leftExtent(), a.rightExtent(), b.x );
		int pY = clamp( a.lowerExtent(), a.upperExtent(), b.y );

		return isInside( b, pX, pY );
	}

	private static final boolean lineAabrIntersects (
			final int x1,
			final int y1,
			final int x2,
			final int y2,
			final int minx,
			final int miny,
			final int maxx,
			final int maxy )
	{
		// Completely outside.
		if ( (x1 <= minx && x2 <= minx) ||
				(y1 <= miny && y2 <= miny) ||
				(x1 >= maxx && x2 >= maxx) ||
				(y1 >= maxy && y2 >= maxy) )
		{
			return false;
		}

		final float m = (y2 - y1) / (float) (x2 - x1);
		final float ya = m * (minx - x1) + y1;
		final float yb = m * (maxx - x1) + y1;
		final float xa = (miny - y1) / (m + x1);
		final float xb = (maxy - y1) / m + x1;

		return (ya > miny && ya < maxy) || (yb > miny && yb < maxy) ||
				(xa > minx && xa < maxx) || (xb > minx && xb < maxx);
	}

	/**
	 * Checks if AABR 'b' intersects Triangle 'a'.
	 * 
	 * @param a
	 * @param b
	 * @return true if they intersect, false otherwise.
	 */
	public static final boolean intersects ( final Triangle a, final AABR b )
	{
		return intersects( b, a );
	}

	/**
	 * Checks if Triangle 'b' intersects AABR 'a'.
	 * 
	 * @param a
	 * @param b
	 * @return true if they intersect, false otherwise.
	 */
	public static final boolean intersects ( final AABR a, final Triangle b )
	{
		// Compute extents only once.
		final int minx = a.leftExtent();
		final int miny = a.lowerExtent();
		final int maxx = a.rightExtent();
		final int maxy = a.upperExtent();
		// Check if the three lines of the triangle intersect the rectangle.
		return lineAabrIntersects( b.ax, b.ay, b.bx, b.by, minx, miny, maxx, maxy ) ||
				lineAabrIntersects( b.bx, b.by, b.cx, b.cy, minx, miny, maxx, maxy ) ||
				lineAabrIntersects( b.cx, b.cy, b.ax, b.ay, minx, miny, maxx, maxy );
	}

	/**
	 * Checks if Circle 'b' intersects Triangle 'a'. <br>
	 * <b>NOTE</b>: This treats the circle as an {@link AABR}, so its approximate
	 * and conservative.
	 * 
	 * @param a
	 * @param b
	 * @return true if they intersect, false otherwise.
	 */
	public static final boolean intersects ( final Triangle a, final Circle b )
	{
		return intersects( b, a );
	}

	/**
	 * Checks if Triangle 'b' intersects Circle 'a'. <br>
	 * <b>NOTE</b>: This treats the circle as an {@link AABR}, so its approximate
	 * and conservative.
	 * 
	 * @param a
	 * @param b
	 * @return true if they intersect, false otherwise.
	 */
	public static final boolean intersects ( final Circle a, final Triangle b )
	{
		// Compute extents only once.
		final int minx = a.leftExtent();
		final int miny = a.lowerExtent();
		final int maxx = a.rightExtent();
		final int maxy = a.upperExtent();
		// Check if the three lines of the triangle intersect the rectangle.
		return lineAabrIntersects( b.ax, b.ay, b.bx, b.by, minx, miny, maxx, maxy ) ||
				lineAabrIntersects( b.bx, b.by, b.cx, b.cy, minx, miny, maxx, maxy ) ||
				lineAabrIntersects( b.cx, b.cy, b.ax, b.ay, minx, miny, maxx, maxy );
	}

	/**
	 * Checks if line 'b' intersects AABR 'a'.
	 * 
	 * @param a
	 * @param bx1
	 * @param by1
	 * @param bx2
	 * @param by2
	 * @return true if they intersect, false otherwise.
	 */
	public static final boolean intersects (
			final AABR a,
			final int bx1,
			final int by1,
			final int bx2,
			final int by2 )
	{
		return lineAabrIntersects( bx1, by1, bx2, by2, a.leftExtent(), a.lowerExtent(), a.rightExtent(), a.upperExtent() );
	}

	/**
	 * Checks if BoundingShape a and BoundingShape b are located on the same axis.
	 * 
	 * @param a BoundingShape.
	 * @param b BoundingShape.
	 * @return true if a and b share at least one axis.
	 */
	public static final boolean areAligned ( final Shape2D a, final Shape2D b )
	{
		return (a.x == b.x || a.y == b.y);
	}

	private static final int clamp ( final int lower, final int upper, final int value )
	{
		return Math.min( upper, Math.max( value, lower ) );
	}
}
