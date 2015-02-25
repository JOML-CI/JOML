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
 * Vector4f
 * 
 * Contains the definition of a Vector comprising 4 floats and associated transformations.
 * 
 * @author Richard Greenlees
 */
public class Vector4d {

    public double x;
    public double y;
    public double z;
    public double w;

    public Vector4d() {
    }

    public Vector4d(double newX, double newY, double newZ, double newW) {
        x = newX;
        y = newY;
        z = newZ;
        w = newW;
    }

    public final void set(Vector4d v) {
        x = v.x;
        y = v.y;
        z = v.z;
        w = v.w;
    }

    public final void set(Vector4f v) {
        x = v.x;
        y = v.y;
        z = v.z;
        w = v.w;
    }

    public final void set(double x, double y, double z, double w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

}