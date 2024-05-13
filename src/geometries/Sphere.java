package geometries;
import primitives.Point;
import primitives.Vector;

/**
 * Represents a sphere in three-dimensional space.
 */
public class Sphere extends RadialGeometry {
    /** The center point of the sphere. */
    final Point center;

    /**
     * Constructs a new Sphere with the specified center point and radius.
     * @param center The center point of the sphere.
     * @param radius The radius of the sphere.
     */
    public Sphere(Point center, double radius) {
        super(radius);
        this.center = center;
    }

    /**
     * Calculates the normal vector to the sphere at a given point (implementation required).
     * @param p The point on the sphere to calculate the normal at.
     * @return The normal vector to the sphere at the given point.
     */
    @Override
    public Vector getNormal(Point p) {
        // Implement this method based on the specific sphere equation
        // For now, return null as the normal calculation is not provided
        return null;
    }
}
