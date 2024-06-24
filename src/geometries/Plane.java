package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.*;

/**
 * Represents a plane in three-dimensional space.
 */
public class Plane implements Geometry {
    /**
     * The center point of the plane.
     */
    final protected Point center;

    /**
     * The normal vector to the plane (normalized).
     */
    final protected Vector vNormal;

    /**
     * Constructs a new Plane using three points.
     * The normal vector is calculated as the normalized cross product of two vectors formed by these points.
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
        // Set the normal vector
        vNormal = v01.crossProduct(v02).normalize();
    }

    /**
     * Constructs a new Plane using a center point and a normal vector.
     *
     * @param center The center point of the plane.
     * @param normal The normal vector to the plane.
     */
    public Plane(Point center, Vector normal) {
        this.center = center;
        this.vNormal = normal.normalize();
    }

    /**
     * Calculates the normal vector to the plane at a given point.
     * Since the normal vector is constant for a plane, it returns the pre-calculated normal vector.
     *
     * @param p The point on the plane to calculate the normal at.
     * @return The normal vector to the plane at the given point.
     */
    @Override
    public Vector getNormal(Point p) {
        // For a plane, the normal is constant everywhere, so return the pre-calculated normal vector
        return vNormal;
    }

    /**
     * Returns the normal vector to the plane.
     *
     * @return The normal vector to the plane.
     */
    public Vector getNormal() {
        // For a plane, the normal is constant everywhere, so return the pre-calculated normal vector
        return vNormal;
    }

    /**
     * Returns the center point to the plane.
     *
     * @return The center point to the plane.
     */
    public Point getCenter() {
        return center;
    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        Vector dir = ray.getDirection();
        //denominator
        double ndir = alignZero(vNormal.dotProduct(dir));
        //ray is lying in the plane axis
        if (isZero(ndir))
            return null;

        Point head = ray.getHead();
        if (center.equals(head)) return null;

        Vector head_q = center.subtract((head));
        //numerator
        double nhead_q = alignZero(vNormal.dotProduct(head_q));
        double t = alignZero(nhead_q / ndir);
        return t <= 0 ? null : List.of(ray.getPoint(t));
    }
}
