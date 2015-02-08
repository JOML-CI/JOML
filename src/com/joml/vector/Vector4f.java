/*
 * Feel free to do whatever you want with this code, all I've done is
 * pull together common knowledge into one easy package. Use it as a
 * base for your own work, copy/paste bits or integrate it into your
 * existing project, it's all good. Just add a thanks to me somewhere.
 */
package com.joml.vector;

/**
 * Vector4f
 * 
 * Contains the definition of a Vector comprising 4 floats and associated transformations.
 * 
 * @author Richard Greenlees
 */
public class Vector4f {

	public float x;
	public float y;
	public float z;
	public float w;

	public Vector4f() {
	}
        
        public Vector4f(float newX, float newY, float newZ, float newW) {
            x = newX;
            y = newY;
            z = newZ;
            w = newW;
        }

	public final void set(Vector4f v) {
		x = v.x;
		y = v.y;
		z = v.z;
		w = v.w;
	}

	public final void set(float x, float y, float z, float w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}

}