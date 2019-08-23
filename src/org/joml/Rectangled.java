/*
 * The MIT License
 *
 * Copyright (c) 2017-2019 JOML
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.joml;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.joml.internal.Options;
import org.joml.internal.Runtime;

/**
 * Represents a 2D axis-aligned rectangle.
 * 
 * @author Kai Burjack, AnthoFoxo
 */
public class Rectangled {

	public double minX, minY;
	public double maxX, maxY;

	/**
	 * Create a new {@link Rectangled} with a minimum and maximum corner of
	 * <code>(0, 0)</code>.
	 */
	public Rectangled() {
	}

	/**
	 * Create a new {@link Rectangled} as a copy of the given <code>source</code>.
	 * 
	 * @param source the {@link Rectangled} to copy from
	 */
	public Rectangled(Rectangled source) {
		this.minX = source.minX;
		this.minY = source.minY;
		this.maxX = source.maxX;
		this.maxY = source.maxY;
	}

	/**
	 * Create a new {@link Rectangled} with the given <code>min</code> and
	 * <code>max</code> corner coordinates.
	 * 
	 * @param min the minimum coordinates
	 * @param max the maximum coordinates
	 */
	public Rectangled(Vector2dc min, Vector2dc max) {
		this.minX = min.x();
		this.minY = min.y();
		this.maxX = max.x();
		this.maxY = max.y();
	}

	/**
	 * Create a new {@link Rectangled} with the given minimum and maximum corner
	 * coordinates.
	 * 
	 * @param minX the x coordinate of the minimum corner
	 * @param minY the y coordinate of the minimum corner
	 * @param maxX the x coordinate of the maximum corner
	 * @param maxY the y coordinate of the maximum corner
	 */
	public Rectangled(double minX, double minY, double maxX, double maxY) {
		super();
		this.minX = minX;
		this.minY = minY;
		this.maxX = maxX;
		this.maxY = maxY;
	}

	/**
	 * Tests if a this rectangle intersects with another rectangle
	 * 
	 * @param r The rectangle to test against
	 * @return If this rectangle intersects rectangle r
	 */
	public boolean intersects(Rectangled r) {

		double tw = maxX - minX;
		double th = maxY - minY;
		double rw = r.maxX - r.minX;
		double rh = r.maxY - r.minY;

		if (rw <= 0D || rh <= 0D || tw <= 0D || th <= 0D) return false;

		rw += r.minX;
		rh += r.minY;
		tw += minX;
		th += minY;

		return ((rw < r.minX || rw > minX) && (rh < r.minY || rh > minY) && (tw < minX || tw > r.minX) && (th < minY || th > r.minY));

	}

	/**
	 * Tests if a coordinate is contained within this rectangle
	 * 
	 * @param v The vector representing a point
	 * @return If this rectangle contains the point x, y
	 */
	public boolean contains(Vector2dc v) {

		return contains(v.x(), v.y());

	}

	/**
	 * Tests if a coordinate is contained within this rectangle
	 * 
	 * @param x The x coordinate
	 * @param y The y coordinate
	 * @return If this rectangle contains the point x, y
	 */
	public boolean contains(double x, double y) {

		double w = maxX - minX;
		double h = maxY - minY;

		if (x < minX || y < minY) return false;

		w += minX;
		h += minY;

		return ((w < minX || w > x) && (h < minY || h > y));

	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(maxX);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(maxY);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(minX);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(minY);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Rectangled other = (Rectangled) obj;
		if (Double.doubleToLongBits(maxX) != Double.doubleToLongBits(other.maxX)) return false;
		if (Double.doubleToLongBits(maxY) != Double.doubleToLongBits(other.maxY)) return false;
		if (Double.doubleToLongBits(minX) != Double.doubleToLongBits(other.minX)) return false;
		if (Double.doubleToLongBits(minY) != Double.doubleToLongBits(other.minY)) return false;
		return true;
	}

	/**
	 * Return a string representation of this rectangle.
	 * <p>
	 * This method creates a new {@link DecimalFormat} on every invocation with the
	 * format string "<code>0.000E0;-</code>".
	 * 
	 * @return the string representation
	 */
	public String toString() {
		return Runtime.formatNumbers(toString(Options.NUMBER_FORMAT));
	}

	/**
	 * Return a string representation of this rectangle by formatting the vector
	 * components with the given {@link NumberFormat}.
	 * 
	 * @param formatter the {@link NumberFormat} used to format the vector
	 *                  components with
	 * @return the string representation
	 */
	public String toString(NumberFormat formatter) {
		return "(" + formatter.format(minX) + " " + formatter.format(minY) + ") < " + "(" + formatter.format(maxX) + " " + formatter.format(maxY) + ")";
	}

}
