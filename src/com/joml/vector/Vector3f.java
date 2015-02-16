/*
 * Feel free to do whatever you want with this code, all I've done is
 * pull together common knowledge into one easy package. Use it as a
 * base for your own work, copy/paste bits or integrate it into your
 * existing project, it's all good. Just add a thanks to me somewhere.
 */
package com.joml.vector;

import com.joml.rot.Quaternion;

/**
 * Vector3f
 * 
 * Contains the definition of a Vector comprising 3 floats and associated transformations.
 * 
 * @author Richard Greenlees
 */
public class Vector3f {

	public float x;
	public float y;
	public float z;

	public Vector3f() {

	}

	public Vector3f(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
        
        public Vector3f(Vector3f clone) {
            this.x = clone.x;
            this.y = clone.y;
            this.z = clone.z;
        }

        /** Sets the x, y and z attributes to match the supplied vector */
	public void set(Vector3f v) {
		x = v.x;
		y = v.y;
		z = v.z;
	}

        /** Sets the x, y and z attributes to the supplied float values */
	public void set(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

        /** Subtracts the supplied vector from this one */
	public void sub(Vector3f v) {
		x -= v.x;
		y -= v.y;
		z -= v.z;
	}
        
        /** Subtracts v2 from v1 and stores the results in dest. Does not modify v1 or v2 */
        public static void sub(Vector3f v1, Vector3f v2, Vector3f dest) {
            dest.set( v1.x - v2.x,
                      v1.y - v2.y,
                      v1.z - v2.z );
        }
        
        /** Adds the supplied vector to this one */
	public void add(Vector3f v) {
		x += v.x;
		y += v.y;
		z += v.z;
	}
        
        /** Adds v2 to v1 and stores the results in dest. Does not modify v1 or v2 */
        public static void add(Vector3f v1, Vector3f v2, Vector3f dest) {
            dest.set( v1.x + v2.x,
                      v1.y + v2.y,
                      v1.z + v2.z );
        }
        
        public void mul(Vector3f v) {
            x *= v.x;
            y *= v.y;
            z *= v.z;
        }
        
        public static void mul(Vector3f v1, Vector3f v2, Vector3f dest) {
            dest.x = v1.x * v2.x;
            dest.y = v1.y * v2.y;
            dest.z = v1.z * v2.z;
        }
        
        public void mul(float scalar) {
            x *= scalar;
            y *= scalar;
            z *= scalar;
        }
        
        public static void mul(Vector3f v, float scalar, Vector3f dest) {
            dest.x = v.x * scalar;
            dest.y = v.y * scalar;
            dest.z = v.z * scalar;
        }

        /** Returns the length squared of this vector */
	public float lengthSquared() {
		return x * x + y * y + z * z;
	}

        /** Returns the length of this vector */
	public float length() {
		return (float) Math.sqrt(lengthSquared());
	}
        
        /** Used internally for the distance function */
        private static float lengthSquared(Vector3f source) {
            return source.x * source.x + source.y * source.y + source.z * source.z;
        }
        
        /** Used internally for the distance function */
        private static float length(Vector3f source) {
            return (float) Math.sqrt(lengthSquared(source));
        }
        


        /** Normalizes this vector */
	public void normalize() {
		float d = length();
                x /= d;
		y /= d;
		z /= d;
	}
        
        /** Normalize the original vector and store the results in dest. Does not modify the original */
	public static void normalize(Vector3f original, Vector3f dest) {
		float d = length(original);
		dest.set ( original.x / d,
                           original.y / d,
                           original.z / d );
	}

        /** Set this vector to be the cross of v1 and v2 */
	public void cross(Vector3f v1, Vector3f v2) {
		set(v1.y * v2.z - v1.z * v2.y,
                    v1.z * v2.x - v1.x * v2.z,
                    v1.x * v2.y - v1.y * v2.x);
	}
        
        /** Calculate the cross of v1 and v2 and store the results in dest. Does modify v1 or v2 */
        public static void cross(Vector3f v1, Vector3f v2, Vector3f dest) {
            dest.set(v1.y * v2.z - v1.z * v2.y,
                     v1.z * v2.x - v1.x * v2.z,
                     v1.x * v2.y - v1.y * v2.x );
        }
        
        /** Returns the distance between the start and end vectors. Does not modify either */
        public static float distance(Vector3f start, Vector3f end) {
            return (float) Math.sqrt( (end.x - start.x) * (end.x - start.x) +
                                      (end.y - start.y) * (end.y - start.y) +
                                      (end.z - start.z) * (end.z - start.z) );
        }
        
        /** Return the dot product of the supplied v1 and v2 vectors */
        public static float dot(Vector3f v1, Vector3f v2) {
            return (v1.x * v2.x) + (v1.y * v2.y) + (v1.z * v2.z);
        }
        
        /** Calculates the normal of a surface defined by points v1, v2 and v3 and stores it in dest. v1, v2 and v3 are not modified */
        public static void normal(Vector3f v1, Vector3f v2, Vector3f v3, Vector3f dest) {
            dest.set ( ((v2.y - v1.y) * (v3.z - v1.z)) - ((v2.z - v1.z) * (v3.y - v1.y)),
                       ((v2.z - v1.z) * (v3.x - v1.x)) - ((v2.x - v1.x) * (v3.z - v1.z)),
                       ((v2.x - v1.x) * (v3.y - v1.y)) - ((v2.y - v1.y) * (v3.x - v1.x)) );
        }
        
        public void transform(Quaternion q) {
            /* y = -y;
               z = -z;
               w = -w; */
            
            /*
                final float newX = other.w * this.x + other.x * this.w + other.y * this.z - other.z * y;
		final float newY = other.w * this.y + other.y * this.w + other.z * this.x - other.x * z;
		final float newZ = other.w * this.z + other.z * this.w + other.x * this.y - other.y * x;
		final float newW = other.w * this.w - other.x * this.x - other.y * this.y - other.z * z;
            */
           /* 
            float x1 = q.x + x * -q.w + y * -q.z - z * -q.y;
            float y1 = -q.y + y * -q.w + z * q.x - x * -q.z;
            float z1 = -q.z + z * -q.w + x * -q.y - z * -q.z;
            float w1 = -q.w - x * q.x - y * -q.y - z * -q.z;
            
            float x2 = w1 * q.x + x1 * -q.w + y1 * -q.z - z1 * -q.y;
            float y2 = w1 * -q.y + y1 * -q.w + z1 * q.x - x1 * -q.z;
            float z2 = w1 * -q.z + z1 * -q.w + x1 * -q.y - z1 * -q.z;
            float w2 = w1 * -q.w - x1 * q.x - y1 * -q.y - z1 * -q.z;
            
            x = x2;
            y = y2;
            z = z2;*/
            
            Quaternion v = new Quaternion(x, y, z, 0.0f);
            Quaternion cq = new Quaternion();
            Quaternion.conjugate(q, cq);
            
            Quaternion result = new Quaternion();
            Quaternion.mulFast(q, v, result);
            Quaternion.mul(result, cq, result);
            
            x = result.x;
            y = result.y;
            z = result.z;
            
        }
        
        public String toString() {
            return "Vector3f { " + x + ", " + y + ", " + z + " }";
        }

}