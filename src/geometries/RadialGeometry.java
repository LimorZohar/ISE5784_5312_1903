package geometries;

/**
 * This abstract class represents a geometric shape with a radial attribute, such as a sphere or a cylinder.
 * It implements the Geometry interface.
 */
public abstract class RadialGeometry extends Geometry {

    /**
     * The radius of the radial geometry.
     */
    final protected double radius;

    /**
     * The squared radius of the radial geometry.
     */
    final protected double radiusSquared;

    /**
     * Constructs a RadialGeometry object with the specified radius.
     *
     * @param radius The radius of the radial geometry.
     */
    protected RadialGeometry(double radius) {
        this.radius = radius;
        this.radiusSquared = radius * radius;
    }

    /**
     * Returns the radius of the radial geometry.
     *
     * @return The radius of the radial geometry.
     */
    public double getRadius() {
        return radius;
    }

    /**
     * Returns the squared radius of the radial geometry.
     *
     * @return The squared radius of the radial geometry.
     */
    public double getRadiusSquared() {
        return radiusSquared;
    }
}
