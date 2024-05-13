package geometries;
import primitives.Ray;
import primitives.Point;
import primitives.Vector;

/**
 * Represents a tube in three-dimensional space.
 */
public class Tube extends RadialGeometry {
    /** The axis of the tube defined by a ray. */
    final Ray axis;

    /**
     * Constructs a new Tube with the specified radius and axis.
     * @param radius The radius of the tube.
     * @param axis The axis of the tube defined by a ray.
     */
    protected Tube(double radius, Ray axis) {
        super(radius);
        this.axis = axis;
    }

    /**
     * Calculates the normal vector to the tube at a given point (implementation required).
     * @param p The point on the tube to calculate the normal at.
     * @return The normal vector to the tube at the given point.
     */
    @Override
    public Vector getNormal(Point p) {
        // Implement this method based on the specific tube equation
        // For now, return null as the normal calculation is not provided
        return null;
    }
}
