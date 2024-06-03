package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * Represents a sphere in three-dimensional space.
 */
public class Sphere extends RadialGeometry {
    /**
     * The center point of the sphere.
     */
    final protected Point center;

    /**
     * Constructs a new Sphere with the specified center point and radius.
     *
     * @param center The center point of the sphere.
     * @param radius The radius of the sphere.
     */
    public Sphere(Point center, double radius) {
        super(radius);
        this.center = center;
    }

    /**
     * Calculates the normal vector to the sphere at a given point.
     * <p>
     * The normal vector at a given point on the surface of a sphere is calculated
     * by taking the vector from the center of the sphere to the given point and normalizing it.
     *
     * @param point The point on the sphere to calculate the normal at.
     * @return The normal vector to the sphere at the given point.
     */
    @Override
    public Vector getNormal(Point point) {
        return point.subtract(center).normalize();
    }
}
