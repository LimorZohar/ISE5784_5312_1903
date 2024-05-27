/**
 * Represents a plane in three-dimensional space.
 */
package geometries;

import primitives.*;

public class Plane implements Geometry {
    /**
     * The center point of the plane.
     */
    final protected Point center;
    /**
     * The normal vector to the plane (normalized).
     */
    final protected Vector vnormal;

    /**
     * Constructs a new Plane using three points and additional parameters.
     *
     * @param v0 The first point.
     * @param v1 The second point.
     * @param v2 The third point.
     */
    public Plane(Point v0, Point v1, Point v2) {
        this.center = v1;
        // Calculate the normal vector
        Vector v01 = v1.subtract(v0);
        Vector v02 = v2.subtract(v0);
        // Set the center to be the middle point between v0 and v
        // Set the normal vector
        vnormal = v01.crossProduct(v02).normalize();
    }

    /**
     * Constructs a new Plane using a center point and a normal vector.
     *
     * @param center The center point of the plane.
     * @param normal The normal vector to the plane.
     */
    public Plane(Point center, Vector normal) {
        this.center = center;
        this.vnormal = normal.normalize();
    }

    /**
     * Calculates the normal vector to the plane at a given point.
     *
     * @param p The point on the plane to calculate the normal at.
     * @return The normal vector to the plane at the given point.
     */
    @Override
    public Vector getNormal(Point p) {
        // For a plane, the normal is constant everywhere, so return the pre-calculated normal vector
        return vnormal;
    }

    /**
     * Returns the normal vector to the plane.
     *
     * @return The normal vector to the plane.
     */
    public Vector getNormal() {
        // For a plane, the normal is constant everywhere, so return the pre-calculated normal vector
        return vnormal;
    }
}
