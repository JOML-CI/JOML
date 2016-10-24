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
package org.joml;

import java.util.ArrayList;
import java.util.Random;

/**
 * Generates Poisson samples on a disk.
 * <p>
 * The algorithm implemented here is based on <a href=
 * "http://www.cs.ubc.ca/~rbridson/docs/bridson-siggraph07-poissondisk.pdf">Fast
 * Poisson Disk Sampling in Arbitrary Dimensions</a>.
 * 
 * @author Kai Burjack
 */
public class Poisson {

	private final Vector2f[] quadtree;
	private final float diskRadius;
	private final float diskRadiusSquared;
	private final float minDist;
	private final float cellSize;
	private final int numCells;
	private Random rnd;
	private ArrayList processList;

	/**
	 * Callback used for notifying about a new generated Poisson sample.
	 * 
	 * @author Kai Burjack
	 */
	public static interface PoissonCallback {
		/**
		 * Will be called whenever a new sample with the given coordinates <tt>(x, y)</tt> is generated.
		 * 
		 * @param x
		 * 			the x coordinate of the new sample point
		 * @param y
		 * 			the y coordinate of the new sample point
		 */
		void onNewSample(float x, float y);
	}

	/**
	 * Create a new instance of {@link Poisson} which computes poisson-distributed samples on a disk with the given radius <code>diskRadius</code> and 
	 * notifies the given <code>callback</code> for each found sample point.
	 * <p>
	 * The samples are distributed evenly on the disk with a minimum distance to one another of at least <code>minDist</code>.
	 * 
	 * @param diskRadius
	 *            the disk radius
	 * @param minDist
	 *            the minimum distance between any two generated samples
	 * @param k
	 *            determines how many samples are tested before rejection. Higher values produce better results. Typical values are 20 to 30
	 * @param callback
	 *            will be notified about each sample point
	 */
	public Poisson(float diskRadius, float minDist, int k, PoissonCallback callback) {
		this.diskRadius = diskRadius;
		this.diskRadiusSquared = diskRadius * diskRadius;
		this.minDist = minDist;
		this.rnd = new Random();
		this.cellSize = minDist / (float) Math.sqrt(2.0);
		this.numCells = (int) ((diskRadius * 2) / cellSize) + 1;
		this.quadtree = new Vector2f[numCells * numCells];
		this.processList = new ArrayList();
		compute(k, callback);
	}

	private void compute(int k, PoissonCallback callback) {
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
				randomVectorInRadii(minDist, 2 * minDist, tmp);
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

	private void randomVectorInDisk(Vector2f out) {
		do {
			out.x = (rnd.nextFloat() - 0.5f) * 2.0f * diskRadius;
			out.y = (rnd.nextFloat() - 0.5f) * 2.0f * diskRadius;
		} while (out.lengthSquared() > diskRadiusSquared);
	}

	private void randomVectorInRadii(float r1, float r2, Vector2f out) {
		float angle = rnd.nextFloat() * (float) Math.PI2;
		float radius = r1 * (rnd.nextFloat() + 1);
		out.x = (float) (radius * Math.sin_roquen_9(angle + Math.PIHalf));
		out.y = (float) (radius * Math.sin_roquen_9(angle));
	}

	private boolean searchNeighbors(Vector2f p) {
		int row = (int) ((p.y + diskRadius) / cellSize);
		int col = (int) ((p.x + diskRadius) / cellSize);
		if (quadtree[row * numCells + col] != null)
			return true;
		for (int y = -1; y <= +1; y++) {
			if (y + row < 0 || y + row > numCells - 1)
				continue;
			for (int x = -1; x <= +1; x++) {
				if (x + col < 0 || x + col > numCells - 1)
					continue;
				Vector2f v = quadtree[(row + y) * numCells + (col + x)];
				if (v != null && v.distanceSquared(p) < minDist) {
					return true;
				}
			}
		}
		return false;
	}

	private void insert(Vector2f p) {
		int row = (int) ((p.y + diskRadius) / cellSize);
		int col = (int) ((p.x + diskRadius) / cellSize);
		quadtree[row * numCells + col] = p;
	}

}
