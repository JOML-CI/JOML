/*
 * (C) Copyright 2016 Kai Burjack

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
import java.util.List;

/**
 * A simple quadtree implementation that allows its objects to have rectangular bounds (not just points) and also allows to query all objects within a
 * rectangular area.
 * <p>
 * The type of objects added to this quadtree must implement the {@link Boundable} interface.
 * 
 * @author Kai Burjack
 */
public class Quadtree {

    /**
     * A simple rectangle. Will describe the bounds of a {@link Boundable} and of the quadtree.
     */
    public static class Rectangle {
        public float x;
        public float y;
        public float width;
        public float height;

        public Rectangle() {
        }

        public Rectangle(float x, float y, float width, float height) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }
    }

    /**
     * Simple implementation of a point as a {@link Boundable} to be added into a {@link Quadtree}.
     */
    public static class Point implements Boundable {
        public final float x, y;
        public final Object userData;
        private final Rectangle rect;

        public Point(float x, float y, Object userData) {
            this.x = x;
            this.y = y;
            this.userData = userData;
            this.rect = new Rectangle(x, y, 0, 0);
        }

        public Rectangle getBounds() {
            return rect;
        }
    }

    /**
     * Interface implemented by anything that can be added to this quadtree.
     */
    public interface Boundable {
        /**
         * @return the rectangular bounds of the object
         */
        Rectangle getBounds();
    }

    /**
     * The maximum number of objects that can be stored in a quadtree node before splitting.
     */
    int maxObjectsPerNode = 10;

    /**
     * The root node.
     */
    private final Node root;

    /**
     * Represents a node in the quadtree.
     */
    private final class Node {
        // Constants for the quadrants of the quadtree
        private static final int PXNY = 0;
        private static final int NXNY = 1;
        private static final int NXPY = 2;
        private static final int PXPY = 3;

        private Rectangle bounds;
        private ArrayList/* <Boundable> */ objects;
        private Node[] children;

        public Node(Rectangle bounds) {
            super();
            this.bounds = bounds;
        }

        private void split() {
            float hw = bounds.width / 2.0f;
            float hh = bounds.height / 2.0f;
            float x = bounds.x;
            float y = bounds.y;
            children = new Node[4];
            children[NXNY] = new Node(new Rectangle(x, y, hw, hh));
            children[PXNY] = new Node(new Rectangle(x + hw, y, hw, hh));
            children[NXPY] = new Node(new Rectangle(x, y + hh, hw, hh));
            children[PXPY] = new Node(new Rectangle(x + hw, y + hh, hw, hh));
        }

        private boolean insertIntoChild(Boundable o) {
            // we can insert the object into a child if the object's bounds are
            // completely within any of the child bounds.
            Rectangle r = o.getBounds();
            float xm = bounds.x + bounds.width / 2.0f;
            float ym = bounds.y + bounds.height / 2.0f;
            boolean inserted = false;
            if (r.x >= xm && r.x + r.width < bounds.x + bounds.width) {
                if (r.y >= ym && r.y + r.height < bounds.y + bounds.height) {
                    inserted = children[PXPY].insert(o);
                } else if (r.y >= bounds.y && r.y + r.height < ym) {
                    inserted = children[PXNY].insert(o);
                }
            } else if (r.x >= bounds.x && r.x + r.width < xm) {
                if (r.y >= ym && r.y + r.height < bounds.y + bounds.height) {
                    inserted = children[NXPY].insert(o);
                } else if (r.y >= bounds.y && r.y + r.height < ym) {
                    inserted = children[NXNY].insert(o);
                }
            }
            return inserted;
        }

        boolean insert(Boundable object) {
            if (children != null && insertIntoChild(object))
                return true;
            if (objects != null && objects.size() == maxObjectsPerNode) {
                // too many objects in this quadtree level
                if (children == null) {
                    // split this quadtree once
                    split();
                    // and try to redistribute the objects into the children
                    for (int i = 0; i < objects.size(); i++) {
                        if (insertIntoChild((Boundable) objects.get(i))) {
                            // succeeded with that one -> it fitted within a child!
                            objects.remove(i);
                            i--;
                        }
                    }
                }
                if (!insertIntoChild(object)) {
                    // cannot distribute the object to any child
                    if (objects.size() == maxObjectsPerNode) {
                        // and we are still full!
                        // -> cannot insert
                        return false;
                    }
                    objects.add(object);
                }
            } else {
                if (objects == null)
                    objects = new ArrayList();
                objects.add(object);
            }
            return true;
        }

        int query(Rectangle rect, List res) {
            int count = 0;
            if (children != null) {
                // query children
                float xm = bounds.x + bounds.width / 2.0f;
                float ym = bounds.y + bounds.height / 2.0f;
                boolean intersectsNx = rect.x < xm && rect.x + rect.width >= bounds.x;
                boolean intersectsPx = rect.x < bounds.x + bounds.width && rect.x + rect.width >= xm;
                boolean intersectsNy = rect.y < ym && rect.y + rect.height >= bounds.y;
                boolean intersectsPy = rect.y < bounds.y + bounds.height && rect.y + rect.height >= ym;
                if (intersectsNy) {
                    if (intersectsPx)
                        count += children[PXNY].query(rect, res);
                    if (intersectsNx)
                        count += children[NXNY].query(rect, res);
                }
                if (intersectsPy) {
                    if (intersectsPx)
                        count += children[PXPY].query(rect, res);
                    if (intersectsNx)
                        count += children[NXPY].query(rect, res);
                }
            }
            if (objects != null) {
                // query objects in this level
                if (rect.x < bounds.x && rect.y < bounds.y && rect.x + rect.width >= bounds.x + bounds.width
                        && rect.y + rect.height >= bounds.y + bounds.height) {
                    // node lies completely within query -> simply add all objects
                    count += objects.size();
                    if (res != null)
                        res.addAll(objects);
                } else {
                    // must check each object individually
                    for (int i = 0; i < objects.size(); i++) {
                        Boundable o = (Boundable) objects.get(i);
                        Rectangle bounds = o.getBounds();
                        if (rect.x < bounds.x + bounds.width && rect.x + rect.width >= bounds.x && rect.y < bounds.y + bounds.height
                                && rect.y + rect.height >= bounds.y) {
                            count++;
                            if (res != null)
                                res.add(o);
                        }
                    }
                }
            }
            return count;
        }
    }

    /**
     * Create a new {@link Quadtree} with the given extent.
     * 
     * @param bounds
     *            the bounding rectangle of the quad tree
     * @param maxObjectsPerNode
     *            the maximum number of objects that can be stored in a node before splitting that node
     */
    public Quadtree(Rectangle bounds, int maxObjectsPerNode) {
        if (bounds == null)
            throw new IllegalArgumentException("bounds must not be null"); //$NON-NLS-1$
        if (maxObjectsPerNode <= 0)
            throw new IllegalArgumentException("maxObjectsPerNode must be great than 0"); //$NON-NLS-1$
        this.maxObjectsPerNode = maxObjectsPerNode;
        this.root = new Node(bounds);
    }

    /**
     * Insert the given {@link Boundable} into this Quadtree.
     * 
     * @param object
     *            the {@link Boundable} to add
     * @return <code>true</code> if the given object could be inserted anywhere; <code>false</code> if not
     */
    public boolean insert(Boundable object) {
        if (object == null)
            throw new IllegalArgumentException("object must not be null"); //$NON-NLS-1$
        return root.insert(object);
    }

    /**
     * Query this Quadtree for all objects within the given search rectangle <code>rect</code>.
     * <p>
     * All found objects will be added to the given <code>res</code> list if it is not <code>null</code>.
     * 
     * @param rect
     *            the search rectangle
     * @param res
     *            the list to add all found objects (may be <code>null</code>)
     * @return the number of objects within the search rectangle
     */
    public int query(Rectangle rect, List res) {
        if (rect == null)
            throw new IllegalArgumentException("rect must not be null"); //$NON-NLS-1$
        return root.query(rect, res);
    }
}
