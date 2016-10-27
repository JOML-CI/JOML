/*
 * (C) Copyright 2016 JOML

 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in
 all copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 THE SOFTWARE.

 */
package org.joml.sampling;

import java.util.ArrayList;

import org.joml.Vector2f;

/**
 * Creates samples on a unit disk using the "Best Candidate" algorithm.
 * 
 * @author Kai Burjack
 */
public class BestCandidateSampling {

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
            float xm = minX + hs;
            float ym = minY + hs;
            if (o.x >= xm) {
                if (o.y >= ym) {
                    children[PXPY].insert(o);
                } else {
                    children[PXNY].insert(o);
                }
            } else {
                if (o.y >= ym) {
                    children[NXPY].insert(o);
                } else {
                    children[NXNY].insert(o);
                }
            }
        }

        void insert(Vector2f object) {
            if (children != null) {
                insertIntoChild(object);
                return;
            }
            if (objects != null && objects.size() == MAX_OBJECTS_PER_NODE) {
                split();
                for (int i = 0; i < objects.size(); i++) {
                    insertIntoChild((Vector2f) objects.get(i));
                }
                objects = null;
                insertIntoChild(object);
            } else {
                if (objects == null)
                    objects = new ArrayList(32);
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

        float nearest(float x, float y, float n) {
            float nr = n;
            if (x < minX - n || x > minX + hs*2 + n || y < minY - n || y > minY + hs*2 + n) {
                return nr;
            }
            if (children != null) {
                for (int i = quadrant(x, y), c = 0; c < 4; i = (i + 1) % 4, c++) {
                    float n1 = children[i].nearest(x, y, nr);
                    nr = Math.min(n1, nr);
                }
                return nr;
            }
            float nr2 = nr * nr;
            for (int i = 0; objects != null && i < objects.size(); i++) {
                Vector2f o = (Vector2f) objects.get(i);
                float d = o.distanceSquared(x, y);
                if (d < nr2) {
                    nr2 = d;
                }
            }
            return (float) Math.sqrt(nr2);
        }

        public float nearest(float x, float y) {
            return nearest(x, y, Float.POSITIVE_INFINITY);
        }
    }

    private final Random rnd;

    private final QuadTree qtree;

    /**
     * Create a new instance of {@link BestCandidateSampling}, initialize the random number generator with the given
     * <code>seed</code> and generate <code>numSamples</code> number of 'best candidate' sample positions with each
     * sample tried <code>numCandidates</code> number of times, and call the given <code>callback</code> for each sample
     * generate.
     * 
     * @param seed
     *            the seed to initialize the random number generator with
     * @param numSamples
     *            the number of samples to generate
     * @param numCandidates
     *            the number of candidates to test for each sample
     * @param callback
     *            will be called for each sample generated
     */
    public BestCandidateSampling(long seed, int numSamples, int numCandidates, Sampling2dCallback callback) {
        this.rnd = new Random(seed);
        this.qtree = new QuadTree(-1, -1, 2);
        generate(numSamples, numCandidates, callback);
    }

    private void generate(int numSamples, int numCandidates, Sampling2dCallback callback) {
        for (int i = 0; i < numSamples; i++) {
            float bestX = 0, bestY = 0, bestDist = 0.0f;
            for (int c = 0; c < numCandidates; c++) {
                float r = rnd.nextFloat();
                float a = rnd.nextFloat() * 2.0f * (float) Math.PI;
                float sqrtR = (float) Math.sqrt(r);
                float x = sqrtR * (float) Math.sin_roquen_9(a + 0.5 * Math.PI);
                float y = sqrtR * (float) Math.sin_roquen_9(a);
                float minDist = qtree.nearest(x, y);
                if (minDist > bestDist) {
                    bestDist = minDist;
                    bestX = x;
                    bestY = y;
                }
            }
            callback.onNewSample(bestX, bestY);
            qtree.insert(new Vector2f(bestX, bestY));
        }
    }

}
