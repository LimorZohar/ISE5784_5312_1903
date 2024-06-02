package geometries;

import primitives.Ray;
import primitives.Point;
import primitives.Vector;

/**
 * Represents a tube in three-dimensional space.
 */
public class Tube extends RadialGeometry {
    /**
     * The axis of the tube defined by a ray.
     */
    final protected Ray axis;

    /**
     * Constructs a new Tube with the specified radius and axis.
     *
     * @param radius The radius of the tube.
     * @param axis   The axis of the tube defined by a ray.
     */
    protected Tube(double radius, Ray axis) {
        super(radius);
        this.axis = axis;
    }

    /**
     * Calculates the normal vector to the tube at a given point.
     * <p>
     * The normal vector at a given point on the surface of a tube is calculated
     * by projecting the point onto the axis of the tube, then finding the vector
     * from the axis to the point and normalizing it.
     *
     * @param point The point on the tube to calculate the normal at.
     * @return The normal vector to the tube at the given point.
     */
    @Override
    public Vector getNormal(Point point) {
        return axis.getDirection().crossProduct(point.subtract(axis.getHead())).normalize();
    }
}
