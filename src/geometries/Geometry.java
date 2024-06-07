package geometries;

import primitives.*;

/**
 * Represents a geometric object in a three-dimensional space.
 */
public interface Geometry extends Intersectable {
    /**
     * Calculates the normal vector to this geometry at the given point.
     *
     * @param p The point on the geometry to calculate the normal at.
     * @return The normal vector to the geometry at the given point.
     */
    public Vector getNormal(Point p);

}
