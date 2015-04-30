/*
 * (C) Copyright 2015 Richard Greenlees
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software
 * and associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 *  1) The above copyright notice and this permission notice shall be included
 *     in all copies or substantial portions of the Software.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 */
package com.joml.vector;

/**
 *
 * @author RGreenlees
 */
public class Vector2d {

    public double x;
    public double y;

    public Vector2d() {

    }

    public Vector2d(double newX, double newY) {
        x = newX;
        y = newY;
    }

    public Vector2d(Vector2d clone) {
        x = clone.x;
        y = clone.y;
    }

    /** Sets the X and Y attributes using the supplied float values */
    public final void set(double newX, double newY) {
        x = newX;
        y = newY;
    }

    /** Sets this Vector2d to be a clone of v */
    public final void set(Vector2d v) {
        x = v.x;
        y = v.y;
    }

    /** Stores the perpendicular of v in dest. Does not modify v */
    public final static void perpendicular(Vector2d v, Vector2d dest) {
        dest.x = v.y;
        dest.y = v.x * -1;
    }

    /** Sets this Vector2d to be its perpendicular */
    public final void perpendicular() {
        set(y, x * -1);
    }

    /** Subtracts b from a and stores the result in dest. Does not modify a or b */
    public final static void sub(Vector2d a, Vector2d b, Vector2d dest) {
        dest.x = a.x - b.x;
        dest.y = a.y - b.y;
    }

    /** Subtracts b from a and stores the result in dest. Does not modify a or b */
    public final static void sub(Vector2f a, Vector2d b, Vector2d dest) {
        dest.x = a.x - b.x;
        dest.y = a.y - b.y;
    }

    /** Subtracts b from a and stores the result in dest. Does not modify a or b */
    public final static void sub(Vector2d a, Vector2f b, Vector2d dest) {
        dest.x = a.x - b.x;
        dest.y = a.y - b.y;
    }

    /** Subtracts v from this Vector2d */
    public final void sub(Vector2d v) {
        x -= v.x;
        y -= v.y;
    }

    /** Subtracts v from this Vector2d */
    public final void sub(Vector2f v) {
        x -= v.x;
        y -= v.y;
    }

    /** Returns the dot product of a and b */
    public static double dot(Vector2d a, Vector2d b) {
        return ((a.x * b.x) + (a.y * b.y));
    }

    /** Returns the dot product of this vector and v */
    public double dot(Vector2d v) {
        return ((x * v.x) + (y * v.y));
    }

    /** Returns the length of a */
    public static double length(Vector2d a) {
        return Math.sqrt((a.x * a.x) + (a.y * a.y));
    }

    /** Returns the length of this Vector2d */
    public double length() {
        return Math.sqrt((x * x) + (y * y));
    }

    /** Returns the distance between the start and end vectors */
    public static double distance(Vector2d start, Vector2d end) {
        return Math.sqrt((end.x - start.x) * (end.x - start.x)
                + (end.y - start.y) * (end.y - start.y));
    }

    /** Returns the distance between the start and end vectors */
    public static double distance(Vector2d start, Vector2f end) {
        return Math.sqrt((end.x - start.x) * (end.x - start.x)
                + (end.y - start.y) * (end.y - start.y));
    }

    /** Returns the distance between the start and end vectors */
    public static double distance(Vector2f start, Vector2d end) {
        return Math.sqrt((end.x - start.x) * (end.x - start.x)
                + (end.y - start.y) * (end.y - start.y));
    }

    /** Returns the distance between this Vector and v */
    public double distance(Vector2d v) {
        return Math.sqrt((v.x - x) * (v.x - x)
                + (v.y - y) * (v.y - y));
    }

    /** Stores a normalized copy of the supplied Vector2d in dest. Does not modify a */
    public final static void normalize(Vector2d a, Vector2d dest) {
        double length = Math.sqrt((a.x * a.x) + (a.y * a.y));
        dest.x = a.x / length;
        dest.y = a.y / length;
    }

    /** Stores a normalized copy of the supplied Vector2f in dest. Does not modify a */
    public final static void normalize(Vector2f a, Vector2d dest) {
        double length = Math.sqrt((a.x * a.x) + (a.y * a.y));
        dest.x = a.x / length;
        dest.y = a.y / length;
    }

    /** Normalizes this Vector2d */
    public final void normalize() {
        double length = Math.sqrt((x * x) + (y * y));
        x /= length;
        y /= length;
    }

    /** Adds v to this Vector2d */
    public final void add(Vector2d v) {
        x += v.x;
        y += v.y;
    }

    /** Adds v to this Vector2f */
    public final void add(Vector2f v) {
        x += v.x;
        y += v.y;
    }

    /** Adds b to a and stores the results in dest */
    public final static void add(Vector2d a, Vector2d b, Vector2d dest) {
        dest.x = a.x + b.x;
        dest.y = a.y + b.y;
    }

    /** Adds b to a and stores the results in dest */
    public final static void add(Vector2d a, Vector2f b, Vector2d dest) {
        dest.x = a.x + b.x;
        dest.y = a.y + b.y;
    }

    /** Adds b to a and stores the results in dest */
    public final static void add(Vector2f a, Vector2d b, Vector2d dest) {
        dest.x = a.x + b.x;
        dest.y = a.y + b.y;
    }

    /**
     * Set all components to zero.
     */
    public void zero() {
        this.x = 0.0;
        this.y = 0.0;
    }


}
