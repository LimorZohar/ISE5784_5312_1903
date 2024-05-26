package geometries;
import primitives.Ray;

/**
 * Represents a cylinder in three-dimensional space, extending the Tube class.
 */
public class Cylinder extends Tube {

    /** The height of the cylinder. */
    final double height;

    /**
     * Constructs a new Cylinder with the specified radius, axis, and height.
     * @param radius The radius of the cylinder.
     * @param axis The axis of the cylinder defined by a ray.
     * @param height The height of the cylinder.
     */
    protected Cylinder(double radius, Ray axis, double height) {
        super(radius, axis);
        this.height = height;
    }
}
