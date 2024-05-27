/**
 * This abstract class represents a geometric shape with a radial attribute, such as a sphere or a cylinder.
 * It implements the Geometry interface.
 */
package geometries;

public abstract class RadialGeometry implements Geometry {

    /** The radius of the radial geometry. */
    final protected double radius;

    /**
     * Constructs a RadialGeometry object with the specified radius.
     * @param radius The radius of the radial geometry.
     */
    protected RadialGeometry(double radius) {
        this.radius = radius;
    }
}
