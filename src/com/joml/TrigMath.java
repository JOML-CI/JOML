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
package com.joml;

/**
 * TrigMath
 * 
 * Contains some generally useful functions for trigonometry calculations.
 * 
 * @author Richard Greenlees
 */
public class TrigMath {
    
    public static final double degreesToRadians = (Math.PI / 180.0);
    public static final double radiansToDegrees = (180.0 / Math.PI);

    /** Return the coTangent of the supplied angle */
    public static double coTangent(double angle) {
        return 1.0 / Math.tan(angle);
    }

    /** Convert the supplied degrees to radians */
    public static double degreesToRadians(double degrees) {
        return degrees * degreesToRadians;
    }

}
