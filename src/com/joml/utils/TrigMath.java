/*
 * Feel free to do whatever you want with this code, all I've done is
 * pull together common knowledge into one easy package. Use it as a
 * base for your own work, copy/paste bits or integrate it into your
 * existing project, it's all good. Just add a thanks to me somewhere.
 */
package com.joml.utils;

import static java.lang.Math.PI;

/**
 * TrigMath
 * 
 * Contains some generally useful functions for trigonometry calculations.
 * 
 * @author Richard Greenlees
 */
public class TrigMath {
    
    public static final float degreesToRadians = (float) (PI / 180d);
    static public final float radiansToDegrees = (float) (180d / PI);

    /** Return the coTangent of the supplied angle */
    public static float coTangent(float angle) {
        return (float) (1f / Math.tan(angle));
    }

    /** Convert the supplied degrees to radians */
    public static float degreesToRadians(float degrees) {
        return degrees * degreesToRadians;
    }
    
    
}
