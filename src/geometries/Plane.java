package geometries;
import primitives.*;

/**
 * Represents a plane in three-dimensional space.
 */
public class Plane implements Geometry {
    /** The center point of the plane. */
    final  Point center;
    /** The normal vector to the plane (normalized). */
    final Vector Vnormal;

    /**
     * Constructs a new Plane using three points and additional parameters.
     *
     * @param v0 The first point.
     * @param v1 The second point.
     * @param v2 The third point.
     */
    public Plane(Point v0, Point v1, Point v2) {
        center = v0;
        Vnormal = null;
    }
    public Plane(Point center, Vector Vnormal) {
        this.center = center;
        this.Vnormal = (Vector) Vnormal.normalize();
    }

    /**
     * Calculates the normal vector to the plane at a given point (implementation required).
     * @param p The point on the plane to calculate the normal at.
     * @return The normal vector to the plane at the given point.
     */
    @Override
    public Vector getNormal(Point p) {
        // Implement this method based on the specific plane equation
        // For now, return the pre-calculated normal vector (Vnormal)
        return Vnormal;
    }

    /**
     * Returns the normal vector to the plane (implementation required).
     * @return The normal vector to the plane.
     */
    public Vector getNormal() {
        // Implement this method based on the specific plane equation
        // For now, return the pre-calculated normal vector (Vnormal)
        return Vnormal;
    }
}
