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
 * Generates Poisson samples on a disk.
 * <p>
 * The algorithm implemented here is based on
 * <a href= "http://www.cs.ubc.ca/~rbridson/docs/bridson-siggraph07-poissondisk.pdf">Fast Poisson Disk Sampling in
 * Arbitrary Dimensions</a>.
 * 
 * @author Kai Burjack
 */
public class PoissonSampling {

    private final Vector2f[] grid;
    private final float diskRadius;
    private final float diskRadiusSquared;
    private final float minDist;
    private final float minDistSquared;
    private final float cellSize;
    private final int numCells;
    private final Random rnd;
    private final ArrayList processList;

    /**
     * Create a new instance of {@link PoissonSampling} which computes poisson-distributed samples on a disk with the
     * given radius <code>diskRadius</code> and notifies the given <code>callback</code> for each found sample point.
     * <p>
     * The samples are distributed evenly on the disk with a minimum distance to one another of at least
     * <code>minDist</code>.
     * 
     * @param seed
     *            the seed to initialize the random number generator with
     * @param diskRadius
     *            the disk radius
     * @param minDist
     *            the minimum distance between any two generated samples
     * @param k
     *            determines how many samples are tested before rejection. Higher values produce better results. Typical
     *            values are 20 to 30
     * @param callback
     *            will be notified about each sample point
     */
    public PoissonSampling(long seed, float diskRadius, float minDist, int k, Callback2d callback) {
        this.diskRadius = diskRadius;
        this.diskRadiusSquared = diskRadius * diskRadius;
        this.minDist = minDist;
        this.minDistSquared = minDist * minDist;
        this.rnd = new Random(seed);
        this.cellSize = minDist / (float) Math.sqrt(2.0);
        this.numCells = (int) ((diskRadius * 2) / cellSize) + 1;
        this.grid = new Vector2f[numCells * numCells];
        this.processList = new ArrayList();
        compute(k, callback);
    }

    private void compute(int k, Callback2d callback) {
        Vector2f tmp = new Vector2f();
        randomVectorInDisk(tmp);
        Vector2f initial = new Vector2f(tmp);
        processList.add(initial);
        callback.onNewSample(initial.x, initial.y);
        insert(initial);
        while (!processList.isEmpty()) {
            int i = rnd.nextInt(processList.size());
            Vector2f sample = (Vector2f) processList.get(i);
            boolean found = false;
            search: for (int s = 0; s < k; s++) {
                randomVectorInRto2R(minDist, tmp);
                tmp.add(sample);
                if (tmp.lengthSquared() > diskRadiusSquared)
                    continue search;
                if (!searchNeighbors(tmp)) {
                    found = true;
                    Vector2f f = new Vector2f(tmp);
                    callback.onNewSample(f.x, f.y);
                    processList.add(f);
                    insert(f);
                    break;
                }
            }
            if (!found) {
                processList.remove(i);
            }
        }
    }

    /**
     * Reference: <a href="http://mathworld.wolfram.com/DiskPointPicking.html">http://mathworld.wolfram.com/</a>
     */
    private void randomVectorInDisk(Vector2f out) {
        float r = rnd.nextFloat();
        float a = rnd.nextFloat() * 2.0f * (float) Math.PI;
        float sqrtR = (float) Math.sqrt(r);
        out.x = sqrtR * (float) Math.sin_roquen_9(a + 0.5 * Math.PI);
        out.y = sqrtR * (float) Math.sin_roquen_9(a);
    }

    private void randomVectorInRto2R(float r, Vector2f out) {
        float angle = rnd.nextFloat() * (float) Math.PI2;
        float radius = r * (rnd.nextFloat() + 1.0f);
        out.x = (float) (radius * Math.sin_roquen_9(angle + Math.PIHalf));
        out.y = (float) (radius * Math.sin_roquen_9(angle));
    }

    private boolean searchNeighbors(Vector2f p) {
        int row = (int) ((p.y + diskRadius) / cellSize);
        int col = (int) ((p.x + diskRadius) / cellSize);
        if (grid[row * numCells + col] != null)
            return true;
        int minX = Math.max(0, col - 1);
        int minY = Math.max(0, row - 1);
        int maxX = Math.min(col + 1, numCells - 1);
        int maxY = Math.min(row + 1, numCells - 1);
        for (int y = minY; y <= maxY; y++) {
            for (int x = minX; x <= maxX; x++) {
                Vector2f v = grid[y * numCells + x];
                if (v != null && v.distanceSquared(p) < minDistSquared) {
                    return true;
                }
            }
        }
        return false;
    }

    private void insert(Vector2f p) {
        int row = (int) ((p.y + diskRadius) / cellSize);
        int col = (int) ((p.x + diskRadius) / cellSize);
        grid[row * numCells + col] = p;
    }

}
