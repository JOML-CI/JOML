package org.joml;

public enum FpMath 
{
  ;

  /** Same contract as {@link java.lang.Math#abs(float)}. */
  public static final float abs(float a)
  {
    // in fact: this is the implementation in the documentation.
    return Float.intBitsToFloat((Float.floatToRawIntBits(a) & 0x7fffffff));
  }
  
  /** Same contract as {@link java.lang.Math#abs(double)}. */
  public static final double abs(double a)
  {
    // in fact: this is the implementation in the documentation.
    return Double.longBitsToDouble((Double.doubleToLongBits(a)<<1)>>>1);
  }
  
  /** ROQUEN: temp hack - waiting for feedback */
  public static double conicalAngle(double angle) 
  {
    return (angle < 0.0 ? 2.0 * Math.PI + angle % (2.0 * Math.PI) : angle) % (2.0 * Math.PI);
  }
  
  /** Ditto */
  public static float conicalAngle(float angle) 
  {
    return (angle < 0.f ? (float)(2.0 * Math.PI) + angle % (float)(2.0 * Math.PI) : angle) % (float)(2.0 * Math.PI);
  }
}
