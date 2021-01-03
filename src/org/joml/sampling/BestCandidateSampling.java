/*
 * The MIT License
 *
 * Copyright (c) 2016-2021 JOML
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
package org.joml.sampling;

//#ifdef __HAS_NIO__
import java.nio.FloatBuffer;
//#endif
import java.util.ArrayList;

import org.joml.Random;
import org.joml.Vector2f;
import org.joml.Vector3f;

/**
 * Creates samples using the "Best Candidate" algorithm.
 * 
 * @author Kai Burjack
 */
public class BestCandidateSampling {

    private static final class IntHolder {
        int value;
    }

    /**
     * Generates Best Candidate samples on a unit sphere.
     * <p>
     * References:
     * <ul>
     * <li><a href="https://arxiv.org/ftp/cs/papers/0701/0701164.pdf">Indexing the Sphere with the Hierarchical Triangular Mesh</a>
     * <li><a href="http://math.stackexchange.com/questions/1244512/point-in-a-spherical-triangle-test">Point in a spherical triangle test</a>
     * </ul>
     * 
     * @author Kai Burjack
     */
    public static class Sphere {
        /**
         * Implementation of a Hierarchical Triangular Mesh structure to index the sample points on the unit sphere for accelerating 1-nearest neighbor searches.
         * 
         * @author Kai Burjack
         */
        private static final class Node {
            private static final int MAX_OBJECTS_PER_NODE = 32;

            private float v0x, v0y, v0z;
            private float v1x, v1y, v1z;
            private float v2x, v2y, v2z;
            private float cx, cy, cz;
            private float arc;

            private ArrayList objects;
            private Node[] children;

            Node() {
                this.children = new Node[8];
                float s = 1f;
                this.arc = 2.0f * (float) Math.PI;
                /*
                 * See: https://arxiv.org/ftp/cs/papers/0701/0701164.pdf
                 */
                this.children[0] = new Node(-s, 0, 0, 0, 0, s, 0, s, 0);
                this.children[1] = new Node(0, 0, s, s, 0, 0, 0, s, 0);
                this.children[2] = new Node(s, 0, 0, 0, 0, -s, 0, s, 0);
                this.children[3] = new Node(0, 0, -s, -s, 0, 0, 0, s, 0);
                this.children[4] = new Node(-s, 0, 0, 0, -s, 0, 0, 0, s);
                this.children[5] = new Node(0, 0, s, 0, -s, 0, s, 0, 0);
                this.children[6] = new Node(s, 0, 0, 0, -s, 0, 0, 0, -s);
                this.children[7] = new Node(0, 0, -s, 0, -s, 0, -s, 0, 0);
            }

            private Node(float x0, float y0, float z0, float x1, float y1, float z1, float x2, float y2, float z2) {
                this.v0x = x0;
                this.v0y = y0;
                this.v0z = z0;
                this.v1x = x1;
                this.v1y = y1;
                this.v1z = z1;
                this.v2x = x2;
                this.v2y = y2;
                this.v2z = z2;
                cx = (v0x + v1x + v2x) / 3.0f;
                cy = (v0y + v1y + v2y) / 3.0f;
                cz = (v0z + v1z + v2z) / 3.0f;
                float invCLen = Math.invsqrt(cx * cx + cy * cy + cz * cz);
                cx *= invCLen;
                cy *= invCLen;
                cz *= invCLen;
                // Compute maximum radius around triangle centroid
                float arc1 = greatCircleDist(cx, cy, cz, v0x, v0y, v0z);
                float arc2 = greatCircleDist(cx, cy, cz, v1x, v1y, v1z);
                float arc3 = greatCircleDist(cx, cy, cz, v2x, v2y, v2z);
                float dist = Math.max(Math.max(arc1, arc2), arc3);
                /*
                 * Convert radius into diameter, but also take into account the linear
                 * arccos approximation we use.
                 * This value was obtained empirically!
                 */
                dist *= 1.7f;
                this.arc = dist;
            }

            private void split() {
                float w0x = v1x + v2x;
                float w0y = v1y + v2y;
                float w0z = v1z + v2z;
                float len0 = Math.invsqrt(w0x * w0x + w0y * w0y + w0z * w0z);
                w0x *= len0;
                w0y *= len0;
                w0z *= len0;
                float w1x = v0x + v2x;
                float w1y = v0y + v2y;
                float w1z = v0z + v2z;
                float len1 = Math.invsqrt(w1x * w1x + w1y * w1y + w1z * w1z);
                w1x *= len1;
                w1y *= len1;
                w1z *= len1;
                float w2x = v0x + v1x;
                float w2y = v0y + v1y;
                float w2z = v0z + v1z;
                float len2 = Math.invsqrt(w2x * w2x + w2y * w2y + w2z * w2z);
                w2x *= len2;
                w2y *= len2;
                w2z *= len2;
                children = new Node[4];
                /*
                 * See: https://arxiv.org/ftp/cs/papers/0701/0701164.pdf
                 */
                children[0] = new Node(v0x, v0y, v0z, w2x, w2y, w2z, w1x, w1y, w1z);
                children[1] = new Node(v1x, v1y, v1z, w0x, w0y, w0z, w2x, w2y, w2z);
                children[2] = new Node(v2x, v2y, v2z, w1x, w1y, w1z, w0x, w0y, w0z);
                children[3] = new Node(w0x, w0y, w0z, w1x, w1y, w1z, w2x, w2y, w2z);
            }

            private void insertIntoChild(Vector3f o) {
                for (int i = 0; i < children.length; i++) {
                    Node c = children[i];
                    /*
                     * Idea: Test whether ray from origin towards point cuts triangle
                     * 
                     * See: http://math.stackexchange.com/questions/1244512/point-in-a-spherical-triangle-test
                     */
                    if (isPointOnSphericalTriangle(o.x, o.y, o.z, c.v0x, c.v0y, c.v0z, c.v1x, c.v1y, c.v1z, c.v2x, c.v2y, c.v2z, 1E-6f)) {
                        c.insert(o);
                        return;
                    }
                }
            }

            void insert(Vector3f object) {
                if (children != null) {
                    insertIntoChild(object);
                    return;
                }
                if (objects != null && objects.size() == MAX_OBJECTS_PER_NODE) {
                    split();
                    for (int i = 0; i < MAX_OBJECTS_PER_NODE; i++)
                        insertIntoChild((Vector3f) objects.get(i));
                    objects = null;
                    insertIntoChild(object);
                } else {
                    if (objects == null)
                        objects = new ArrayList(MAX_OBJECTS_PER_NODE);
                    objects.add(object);
                }
            }

            /**
             * This is essentially a ray cast from the origin of the sphere to the point to test and then checking whether that ray goes through the triangle.
             * <p>
             * Reference: <a href="http://www.graphics.cornell.edu/pubs/1997/MT97.pdf">Fast,
             * Minimum Storage Ray/Triangle Intersection</a>
             */
            private static boolean isPointOnSphericalTriangle(float x, float y, float z, float v0X, float v0Y, float v0Z, float v1X, float v1Y, float v1Z, float v2X, float v2Y,
                    float v2Z, float epsilon) {
                float edge1X = v1X - v0X;
                float edge1Y = v1Y - v0Y;
                float edge1Z = v1Z - v0Z;
                float edge2X = v2X - v0X;
                float edge2Y = v2Y - v0Y;
                float edge2Z = v2Z - v0Z;
                float pvecX = y * edge2Z - z * edge2Y;
                float pvecY = z * edge2X - x * edge2Z;
                float pvecZ = x * edge2Y - y * edge2X;
                float det = edge1X * pvecX + edge1Y * pvecY + edge1Z * pvecZ;
                if (det > -epsilon && det < epsilon)
                    return false;
                float tvecX = -v0X;
                float tvecY = -v0Y;
                float tvecZ = -v0Z;
                float invDet = 1.0f / det;
                float u = (tvecX * pvecX + tvecY * pvecY + tvecZ * pvecZ) * invDet;
                if (u < 0.0f || u > 1.0f)
                    return false;
                float qvecX = tvecY * edge1Z - tvecZ * edge1Y;
                float qvecY = tvecZ * edge1X - tvecX * edge1Z;
                float qvecZ = tvecX * edge1Y - tvecY * edge1X;
                float v = (x * qvecX + y * qvecY + z * qvecZ) * invDet;
                if (v < 0.0f || u + v > 1.0f)
                    return false;
                float t = (edge2X * qvecX + edge2Y * qvecY + edge2Z * qvecZ) * invDet;
                return t >= epsilon;
            }

            private int child(float x, float y, float z) {
                for (int i = 0; i < children.length; i++) {
                    Node c = children[i];
                    if (isPointOnSphericalTriangle(x, y, z, c.v0x, c.v0y, c.v0z, c.v1x, c.v1y, c.v1z, c.v2x, c.v2y, c.v2z, 1E-5f))
                        return i;
                }
                // No child found. This can happen in 'nearest()' when querying possible nearby nodes
                return 0;
            }

            /**
             * Reference: <a href="https://en.wikipedia.org/wiki/Great-circle_distance#Vector_version">https://en.wikipedia.org/</a>
             */
            private float greatCircleDist(float x1, float y1, float z1, float x2, float y2, float z2) {
                float dot = x1 * x2 + y1 * y2 + z1 * z2;
                /*
                 * Just use a linear function, because we (mostly) do less-than comparisons on the result.
                 * We just need a linear function which:
                 * f(-1) = PI
                 * f(0)  = PI/2
                 * f(1)  = 0
                 */
                return (float) (-Math.PIHalf * dot + Math.PIHalf);
                //return (float) Math.acos(dot);
            }

            float nearest(float x, float y, float z) {
                return nearest(x, y, z, Float.POSITIVE_INFINITY);
            }
            float nearest(float x, float y, float z, float n) {
                float gcd = greatCircleDist(x, y, z, cx, cy, cz);
                /*
                 * If great-circle-distance between query point and centroid is larger than the current smallest distance 'n' plus the great circle diameter 'arc', we abort here,
                 * because then it is not possible for any point in the triangle patch to be closer to the query point than 'n'.
                 */
                /*
                 * Yes, we are subtracting two great-circle distances from one another here, which we did not even compute correctly
                 * using our overly linear arccos approximation. But the 1.7 factor above will take care that we still stay conservative
                 * enough here and not rejecting triangle patches which would contain samples nearer than 'n'.
                 */
                if (gcd - arc > n)
                    return n;
                float nr = n;
                if (children != null) {
                    int num = children.length, mod = num-1;
                    for (int i = child(x, y, z), c = 0; c < num; i = (i + 1) & mod, c++) {
                        float n1 = children[i].nearest(x, y, z, nr);
                        nr = Math.min(n1, nr);
                    }
                    return nr;
                }
                for (int i = 0; objects != null && i < objects.size(); i++) {
                    Vector3f o = (Vector3f) objects.get(i);
                    float d = greatCircleDist(o.x, o.y, o.z, x, y, z);
                    if (d < nr)
                        nr = d;
                }
                return nr;
            }
        }

        private boolean onHemisphere;
        private int numSamples;
        private int numCandidates = 60; // <- use a reasonable default
        private long seed;

        /**
         * Create a new instance of {@link Sphere} to configure and generate 'best candidate' sample positions on the unit sphere.
         */
        public Sphere() {}

        /**
         * Generate 'best candidate' sample positions and store the coordinates of all generated samples into the given <code>xyzs</code> float array.
         * <p>
         * <em>This method performs heap allocations, so should be used sparingly.</em>
         * 
         * @param xyzs
         *            will hold the x, y and z coordinates of all samples in the order <code>XYZXYZXYZ...</code>.
         *            This array must have a length of at least <code>numSamples</code>
         * @return this
         */
        public Sphere generate(final float[] xyzs) {
            final IntHolder i = new IntHolder();
            return generate(new Callback3d() {
                public void onNewSample(float x, float y, float z) {
                    xyzs[3 * i.value + 0] = x;
                    xyzs[3 * i.value + 1] = y;
                    xyzs[3 * i.value + 2] = z;
                    i.value++;
                }
            });
        }

//#ifdef __HAS_NIO__
        /**
         * Generate 'best candidate' sample positions and store the coordinates of all generated samples into the given <code>xyzs</code> FloatBuffer.
         * <p>
         * The samples will be written starting at the current position of the FloatBuffer. The position of the FloatBuffer will not be modified.
         * <p>
         * <em>This method performs heap allocations, so should be used sparingly.</em>
         * 
         * @param xyzs
         *            will hold the x, y and z coordinates of all samples in the order <code>XYZXYZXYZ...</code>.
         *            This FloatBuffer must have at least <code>numSamples</code> remaining elements.
         *            The position of the buffer will not be modified by this method
         * @return this
         */
        public Sphere generate(final FloatBuffer xyzs) {
            final IntHolder i = new IntHolder();
            final int pos = xyzs.position();
            return generate(new Callback3d() {
                public void onNewSample(float x, float y, float z) {
                    xyzs.put(pos + 3 * i.value + 0, x);
                    xyzs.put(pos + 3 * i.value + 1, y);
                    xyzs.put(pos + 3 * i.value + 2, z);
                    i.value++;
                }
            });
        }
//#endif

        /**
         * Set the seed to initialize the pseudo-random number generator with.
         * 
         * @param seed
         *          the seed value
         * @return this
         */
        public Sphere seed(long seed) {
            this.seed = seed;
            return this;
        }

        /**
         * Set the number of samples to generate.
         * 
         * @param numSamples
         *          the number of samples
         * @return this
         */
        public Sphere numSamples(int numSamples) {
            this.numSamples = numSamples;
            return this;
        }

        /**
         * Set the number of candidates to try for each generated sample.
         * 
         * @param numCandidates
         *          the number of candidates to try
         * @return this
         */
        public Sphere numCandidates(int numCandidates) {
            this.numCandidates = numCandidates;
            return this;
        }

        /**
         * Set whether to generate samples on a hemisphere around the <code>+Z</code> axis.
         * <p>
         * The default is <code>false</code>, which will generate samples on the whole unit sphere.
         * 
         * @param onHemisphere
         *          whether to generate samples on the hemisphere
         * @return this
         */
        public Sphere onHemisphere(boolean onHemisphere) {
            this.onHemisphere = onHemisphere;
            return this;
        }

        /**
         * Generate 'best candidate' sample call the given <code>callback</code> for each generated sample.
         * <p>
         * <em>This method performs heap allocations, so should be used sparingly.</em>
         * 
         * @param callback
         *            will be called with the coordinates of each generated sample position
         * @return this
         */
        public Sphere generate(Callback3d callback) {
            Random rnd = new Random(seed);
            Node otree = new Node();
            for (int i = 0; i < numSamples; i++) {
                float bestX = Float.NaN, bestY = Float.NaN, bestZ = Float.NaN, bestDist = 0.0f;
                for (int c = 0; c < numCandidates; c++) {
                    /*
                     * Random point on sphere
                     * 
                     * Reference: <a href="http://mathworld.wolfram.com/SpherePointPicking.html">http://mathworld.wolfram.com/</a>
                     */
                    float x1, x2;
                    do {
                        x1 = rnd.nextFloat() * 2.0f - 1.0f;
                        x2 = rnd.nextFloat() * 2.0f - 1.0f;
                    } while (x1 * x1 + x2 * x2 > 1.0f);
                    float sqrt = (float) Math.sqrt(1.0 - x1 * x1 - x2 * x2);
                    float x = 2 * x1 * sqrt;
                    float y = 2 * x2 * sqrt;
                    float z = 1.0f - 2.0f * (x1 * x1 + x2 * x2);
                    if (onHemisphere) {
                        z = Math.abs(z);
                    }
                    float minDist = otree.nearest(x, y, z);
                    if (minDist > bestDist) {
                        bestDist = minDist;
                        bestX = x;
                        bestY = y;
                        bestZ = z;
                    }
                }
                callback.onNewSample(bestX, bestY, bestZ);
                otree.insert(new Vector3f(bestX, bestY, bestZ));
            }
            return this;
        }
    }

    /**
     * Simple quatree that can handle points and 1-nearest neighbor search.
     * 
     * @author Kai Burjack
     */
    private static class QuadTree {
        private static final int MAX_OBJECTS_PER_NODE = 32;

        // Constants for the quadrants of the quadtree
        private static final int PXNY = 0;
        private static final int NXNY = 1;
        private static final int NXPY = 2;
        private static final int PXPY = 3;

        private float minX, minY, hs;
        private ArrayList objects;
        private QuadTree[] children;

        QuadTree(float minX, float minY, float size) {
            this.minX = minX;
            this.minY = minY;
            this.hs = size * 0.5f;
        }

        private void split() {
            children = new QuadTree[4];
            children[NXNY] = new QuadTree(minX, minY, hs);
            children[PXNY] = new QuadTree(minX + hs, minY, hs);
            children[NXPY] = new QuadTree(minX, minY + hs, hs);
            children[PXPY] = new QuadTree(minX + hs, minY + hs, hs);
        }

        private void insertIntoChild(Vector2f o) {
            children[quadrant(o.x, o.y)].insert(o);
        }

        void insert(Vector2f object) {
            if (children != null) {
                insertIntoChild(object);
                return;
            }
            if (objects != null && objects.size() == MAX_OBJECTS_PER_NODE) {
                split();
                for (int i = 0; i < objects.size(); i++)
                    insertIntoChild((Vector2f) objects.get(i));
                objects = null;
                insertIntoChild(object);
            } else {
                if (objects == null)
                    objects = new ArrayList(MAX_OBJECTS_PER_NODE);
                objects.add(object);
            }
        }

        private int quadrant(float x, float y) {
            if (x < minX + hs) {
                if (y < minY + hs)
                    return NXNY;
                return NXPY;
            }
            if (y < minY + hs)
                return PXNY;
            return PXPY;
        }

        float nearest(float x, float y, float lowerBound, float upperBound) {
            float ub = upperBound;
            if (x < minX - upperBound || x > minX + hs * 2 + upperBound || y < minY - upperBound 
                    || y > minY + hs * 2 + upperBound)
                return ub;
            if (children != null) {
                for (int i = quadrant(x, y), c = 0; c < 4; i = (i + 1) & 3, c++) {
                    float n1 = children[i].nearest(x, y, lowerBound, ub);
                    ub = Math.min(n1, ub);
                    if (ub <= lowerBound)
                        return lowerBound;
                }
                return ub;
            }
            float ub2 = ub * ub;
            float lb2 = lowerBound * lowerBound;
            for (int i = 0; objects != null && i < objects.size(); i++) {
                Vector2f o = (Vector2f) objects.get(i);
                float d = o.distanceSquared(x, y);
                if (d <= lb2)
                    return lowerBound;
                if (d < ub2)
                    ub2 = d;
            }
            return (float) Math.sqrt(ub2);
        }
    }

    /**
     * Generates Best Candidate samples on a unit disk.
     * 
     * @author Kai Burjack
     */
    public static class Disk {
        private int numSamples;
        private int numCandidates = 60; // <- use a reasonable default
        private long seed;

        /**
         * Create a new instance of {@link Disk} to configure and generate 'best candidate' sample positions on the unit disk.
         */
        public Disk() {}

        /**
         * Set the seed to initialize the pseudo-random number generator with.
         * 
         * @param seed
         *          the seed value
         * @return this
         */
        public Disk seed(long seed) {
            this.seed = seed;
            return this;
        }

        /**
         * Set the number of samples to generate.
         * 
         * @param numSamples
         *          the number of samples
         * @return this
         */
        public Disk numSamples(int numSamples) {
            this.numSamples = numSamples;
            return this;
        }

        /**
         * Set the number of candidates to try for each generated sample.
         * 
         * @param numCandidates
         *          the number of candidates to try
         * @return this
         */
        public Disk numCandidates(int numCandidates) {
            this.numCandidates = numCandidates;
            return this;
        }

        /**
         * Generate 'best candidate' sample positions and store the coordinates of all generated samples into the given <code>xys</code> float array.
         * <p>
         * <em>This method performs heap allocations, so should be used sparingly.</em>
         * 
         * @param xys
         *            will hold the x and y coordinates of all samples in the order <code>XYXYXY...</code>.
         *            This array must have a length of at least <code>numSamples</code>
         * @return this
         */
        public Disk generate(final float[] xys) {
            final IntHolder i = new IntHolder();
            return generate(new Callback2d() {
                public void onNewSample(float x, float y) {
                    xys[2 * i.value + 0] = x;
                    xys[2 * i.value + 1] = y;
                    i.value++;
                }
            });
        }

//#ifdef __HAS_NIO__
        /**
         * Generate 'best candidate' sample positions and store the coordinates of all generated samples into the given <code>xys</code> FloatBuffer.
         * <p>
         * The samples will be written starting at the current position of the FloatBuffer. The position of the FloatBuffer will not be modified.
         * <p>
         * <em>This method performs heap allocations, so should be used sparingly.</em>
         * 
         * @param xys
         *            will hold the x and y coordinates of all samples in the order <code>XYXYXY...</code>. This FloatBuffer must have at least <code>numSamples</code> remaining elements. The
         *            position of the buffer will not be modified by this method
         * @return this
         */
        public Disk generate(final FloatBuffer xys) {
            final IntHolder i = new IntHolder();
            final int pos = xys.position();
            return generate(new Callback2d() {
                public void onNewSample(float x, float y) {
                    xys.put(pos + 3 * i.value + 0, x);
                    xys.put(pos + 3 * i.value + 1, y);
                    i.value++;
                }
            });
        }
//#endif

        /**
         * Generate 'best candidate' sample positions and call the given <code>callback</code> for each generated sample.
         * <p>
         * <em>This method performs heap allocations, so should be used sparingly.</em>
         * 
         * @param callback
         *            will be called with the coordinates of each generated sample position
         * @return this
         */
        public Disk generate(Callback2d callback) {
            QuadTree qtree = new QuadTree(-1, -1, 2);
            Random rnd = new Random(seed);
            for (int i = 0; i < numSamples; i++) {
                float bestX = 0, bestY = 0, bestDist = 0.0f;
                for (int c = 0; c < numCandidates; c++) {
                    float x, y;
                    do {
                        x = rnd.nextFloat() * 2.0f - 1.0f;
                        y = rnd.nextFloat() * 2.0f - 1.0f;
                    } while (x * x + y * y > 1.0f);
                    float minDist = qtree.nearest(x, y, bestDist, Float.POSITIVE_INFINITY);
                    if (minDist > bestDist) {
                        bestDist = minDist;
                        bestX = x;
                        bestY = y;
                    }
                }
                callback.onNewSample(bestX, bestY);
                qtree.insert(new Vector2f(bestX, bestY));
            }
            return this;
        }
    }

    /**
     * Generates Best Candidate samples on a unit quad.
     * 
     * @author Kai Burjack
     */
    public static class Quad {
        private int numSamples;
        private int numCandidates = 60; // <- use a reasonable default
        private long seed;

        /**
         * Create a new instance of {@link Quad} to configure and generate 'best candidate' sample positions on the unit quad.
         */
        public Quad() {}

        /**
         * Set the seed to initialize the pseudo-random number generator with.
         * 
         * @param seed
         *          the seed value
         * @return this
         */
        public Quad seed(long seed) {
            this.seed = seed;
            return this;
        }

        /**
         * Set the number of samples to generate.
         * 
         * @param numSamples
         *          the number of samples
         * @return this
         */
        public Quad numSamples(int numSamples) {
            this.numSamples = numSamples;
            return this;
        }

        /**
         * Set the number of candidates to try for each generated sample.
         * 
         * @param numCandidates
         *          the number of candidates to try
         * @return this
         */
        public Quad numCandidates(int numCandidates) {
            this.numCandidates = numCandidates;
            return this;
        }

        /**
         * Generate 'best candidate' sample positions and store the coordinates of all generated samples into the given <code>xyzs</code> float array.
         * <p>
         * <em>This method performs heap allocations, so should be used sparingly.</em>
         * 
         * @param xyzs
         *            will hold the x, y and z coordinates of all samples in the order <code>XYZXYZXYZ...</code>.
         *            This array must have a length of at least <code>numSamples</code>
         * @return this
         */
        public Quad generate(final float[] xyzs) {
            final IntHolder i = new IntHolder();
            return generate(new Callback2d() {
                public void onNewSample(float x, float y) {
                    xyzs[2 * i.value + 0] = x;
                    xyzs[2 * i.value + 1] = y;
                    i.value++;
                }
            });
        }

//#ifdef __HAS_NIO__
        /**
         * Generate 'best candidate' sample positions and store the coordinates of all generated samples into the given <code>xys</code> FloatBuffer.
         * <p>
         * The samples will be written starting at the current position of the FloatBuffer. The position of the FloatBuffer will not be modified.
         * <p>
         * <em>This method performs heap allocations, so should be used sparingly.</em>
         * 
         * @param xys
         *            will hold the x and y coordinates of all samples in the order <code>XYXYXY...</code>. This FloatBuffer must have at least <code>numSamples</code> remaining elements. The position of
         *            the buffer will not be modified by this method
         * @return this
         */
        public Quad generate(final FloatBuffer xys) {
            final IntHolder i = new IntHolder();
            final int pos = xys.position();
            return generate(new Callback2d() {
                public void onNewSample(float x, float y) {
                    xys.put(pos + 3 * i.value + 0, x);
                    xys.put(pos + 3 * i.value + 1, y);
                    i.value++;
                }
            });
        }
//#endif

        /**
         * Generate 'best candidate' sample positions and call the given <code>callback</code> for each generated sample.
         * <p>
         * <em>This method performs heap allocations, so should be used sparingly.</em>
         * 
         * @param callback
         *            will be called with the coordinates of each generated sample position
         * @return this
         */
        public Quad generate(Callback2d callback) {
            QuadTree qtree = new QuadTree(-1, -1, 2);
            Random rnd = new Random(seed);
            for (int i = 0; i < numSamples; i++) {
                float bestX = 0, bestY = 0, bestDist = 0.0f;
                for (int c = 0; c < numCandidates; c++) {
                    float x = rnd.nextFloat() * 2.0f - 1.0f;
                    float y = rnd.nextFloat() * 2.0f - 1.0f;
                    float minDist = qtree.nearest(x, y, bestDist, Float.POSITIVE_INFINITY);
                    if (minDist > bestDist) {
                        bestDist = minDist;
                        bestX = x;
                        bestY = y;
                    }
                }
                callback.onNewSample(bestX, bestY);
                qtree.insert(new Vector2f(bestX, bestY));
            }
            return this;
        }
    }

    /**
     * Simple octree for points and 1-nearest neighbor distance query.
     * 
     * @author Kai Burjack
     */
    private static class Octree {
        private static final int MAX_OBJECTS_PER_NODE = 32;

        // Constants for the octants of the octree
        private static final int PXNYNZ = 0;
        private static final int NXNYNZ = 1;
        private static final int NXPYNZ = 2;
        private static final int PXPYNZ = 3;
        private static final int PXNYPZ = 4;
        private static final int NXNYPZ = 5;
        private static final int NXPYPZ = 6;
        private static final int PXPYPZ = 7;

        private float minX, minY, minZ, hs;
        private ArrayList objects;
        private Octree[] children;

        Octree(float minX, float minY, float minZ, float size) {
            this.minX = minX;
            this.minY = minY;
            this.minZ = minZ;
            this.hs = size * 0.5f;
        }

        private void split() {
            children = new Octree[8];
            children[NXNYNZ] = new Octree(minX, minY, minZ, hs);
            children[PXNYNZ] = new Octree(minX + hs, minY, minZ, hs);
            children[NXPYNZ] = new Octree(minX, minY + hs, minZ, hs);
            children[PXPYNZ] = new Octree(minX + hs, minY + hs, minZ, hs);
            children[NXNYPZ] = new Octree(minX, minY, minZ + hs, hs);
            children[PXNYPZ] = new Octree(minX + hs, minY, minZ + hs, hs);
            children[NXPYPZ] = new Octree(minX, minY + hs, minZ + hs, hs);
            children[PXPYPZ] = new Octree(minX + hs, minY + hs, minZ + hs, hs);
        }

        private void insertIntoChild(Vector3f o) {
            children[octant(o.x, o.y, o.z)].insert(o);
        }

        void insert(Vector3f object) {
            if (children != null) {
                insertIntoChild(object);
                return;
            }
            if (objects != null && objects.size() == MAX_OBJECTS_PER_NODE) {
                split();
                for (int i = 0; i < objects.size(); i++)
                    insertIntoChild((Vector3f) objects.get(i));
                objects = null;
                insertIntoChild(object);
            } else {
                if (objects == null)
                    objects = new ArrayList(MAX_OBJECTS_PER_NODE);
                objects.add(object);
            }
        }

        private int octant(float x, float y, float z) {
            if (x < minX + hs)
                if (y < minY + hs) {
                    if (z < minZ + hs)
                        return NXNYNZ;
                    return NXNYPZ;
                } else if (z < minZ + hs)
                    return NXPYNZ;
                else
                    return NXPYPZ;
            else if (y < minY + hs) {
                if (z < minZ + hs)
                    return PXNYNZ;
                return PXNYPZ;
            } else if (z < minZ + hs)
                return PXPYNZ;
            else
                return PXPYPZ;
        }

        float nearest(float x, float y, float z, float lowerBound, float upperBound) {
            float up = upperBound;
            if (x < minX - upperBound || x > minX + hs * 2 + upperBound || y < minY - upperBound || y > minY + hs * 2 + upperBound ||
                    z < minZ - upperBound || z > minZ + hs * 2 + upperBound)
                return up;
            if (children != null) {
                for (int i = octant(x, y, z), c = 0; c < 8; i = (i + 1) & 7, c++) {
                    float n1 = children[i].nearest(x, y, z, lowerBound, up);
                    up = Math.min(n1, up);
                    if (up <= lowerBound)
                        return lowerBound;
                }
                return up;
            }
            float up2 = up * up;
            float lb2 = lowerBound * lowerBound;
            for (int i = 0; objects != null && i < objects.size(); i++) {
                Vector3f o = (Vector3f) objects.get(i);
                float d = o.distanceSquared(x, y, z);
                if (d <= lb2)
                    return lowerBound;
                if (d < up2)
                    up2 = d;
            }
            return (float) Math.sqrt(up2);
        }
    }

    /**
     * Generates Best Candidate samples inside a unit cube.
     * 
     * @author Kai Burjack
     */
    public static class Cube {
        private int numSamples;
        private int numCandidates = 60; // <- use a reasonable default
        private long seed;

        /**
         * Create a new instance of {@link Cube} to configure and generate 'best candidate' sample positions
         * on the unit cube with each sample tried <code>numCandidates</code> number of times, and call the 
         * given <code>callback</code> for each sample generate.
         */
        public Cube() {}

        /**
         * Set the seed to initialize the pseudo-random number generator with.
         * 
         * @param seed
         *          the seed value
         * @return this
         */
        public Cube seed(long seed) {
            this.seed = seed;
            return this;
        }

        /**
         * Set the number of samples to generate.
         * 
         * @param numSamples
         *          the number of samples
         * @return this
         */
        public Cube numSamples(int numSamples) {
            this.numSamples = numSamples;
            return this;
        }

        /**
         * Set the number of candidates to try for each generated sample.
         * 
         * @param numCandidates
         *          the number of candidates to try
         * @return this
         */
        public Cube numCandidates(int numCandidates) {
            this.numCandidates = numCandidates;
            return this;
        }

        /**
         * Generate 'best candidate' sample positions and store the coordinates of all generated samples into the given <code>xyzs</code> float array.
         * <p>
         * <em>This method performs heap allocations, so should be used sparingly.</em>
         * 
         * @param xyzs
         *            will hold the x, y and z coordinates of all samples in the order <code>XYZXYZXYZ...</code>.
         *            This array must have a length of at least <code>numSamples</code>
         * @return this
         */
        public Cube generate(final float[] xyzs) {
            final IntHolder i = new IntHolder();
            return generate(new Callback3d() {
                public void onNewSample(float x, float y, float z) {
                    xyzs[3 * i.value + 0] = x;
                    xyzs[3 * i.value + 1] = y;
                    xyzs[3 * i.value + 2] = z;
                    i.value++;
                }
            });
        }

//#ifdef __HAS_NIO__
        /**
         * Generate 'best candidate' sample positions and store the coordinates of all generated samples into the given <code>xyzs</code> FloatBuffer.
         * <p>
         * The samples will be written starting at the current position of the FloatBuffer. The position of the FloatBuffer will not be modified.
         * <p>
         * <em>This method performs heap allocations, so should be used sparingly.</em>
         * 
         * @param xyzs
         *            will hold the x, y and z coordinates of all samples in the order <code>XYZXYZXYZ...</code>.
         *            This FloatBuffer must have at least <code>numSamples</code> remaining elements.
         *            The position of the buffer will not be modified by this method
         * @return this
         */
        public Cube generate(final FloatBuffer xyzs) {
            final IntHolder i = new IntHolder();
            final int pos = xyzs.position();
            return generate(new Callback3d() {
                public void onNewSample(float x, float y, float z) {
                    xyzs.put(pos + 3 * i.value + 0, x);
                    xyzs.put(pos + 3 * i.value + 1, y);
                    xyzs.put(pos + 3 * i.value + 2, z);
                    i.value++;
                }
            });
        }
//#endif

        /**
         * Generate 'best candidate' sample positions and call the given <code>callback</code> for each generated sample.
         * <p>
         * <em>This method performs heap allocations, so should be used sparingly.</em>
         * 
         * @param callback
         *            will be called with the coordinates of each generated sample position
         * @return this
         */
        public Cube generate(Callback3d callback) {
            Octree octree = new Octree(-1, -1, -1, 2);
            Random rnd = new Random(seed);
            for (int i = 0; i < numSamples; i++) {
                float bestX = 0, bestY = 0, bestZ = 0, bestDist = 0.0f;
                for (int c = 0; c < numCandidates; c++) {
                    float x = rnd.nextFloat() * 2.0f - 1.0f;
                    float y = rnd.nextFloat() * 2.0f - 1.0f;
                    float z = rnd.nextFloat() * 2.0f - 1.0f;
                    float minDist = octree.nearest(x, y, z, bestDist, Float.POSITIVE_INFINITY);
                    if (minDist > bestDist) {
                        bestDist = minDist;
                        bestX = x;
                        bestY = y;
                        bestZ = z;
                    }
                }
                callback.onNewSample(bestX, bestY, bestZ);
                octree.insert(new Vector3f(bestX, bestY, bestZ));
            }
            return this;
        }
    }

}
