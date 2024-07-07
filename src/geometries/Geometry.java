package geometries;

import primitives.*;

/**
 * Represents a geometric object in a three-dimensional space.
 * This is an abstract class that provides a common interface for all geometric objects.
 */
public abstract class Geometry extends Intersectable {

    /**
     * The emission color of the geometry.
     */
    protected Color emission = Color.BLACK;

    private Material material = new Material();

    /**
     * Calculates the normal vector to this geometry at the given point.
     *
     * @param p The point on the geometry to calculate the normal at.
     * @return The normal vector to the geometry at the given point.
     */
    abstract public Vector getNormal(Point p);

    /**
     * Gets the emission color of the geometry.
     *
     * @return The emission color of the geometry.
     */
    public Color getEmission() {
        return emission;
    }

    /**
     * Sets the emission color of the geometry.
     *
     * @param emission The emission color to set.
     * @return The current instance of the geometry, for chaining.
     */
    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }

    /**
     * returns the material
     *
     * @return material
     */
    public Material getMaterial() {
        return material;
    }

    /**
     * set material
     *
     * @param material input
     * @return this
     */
    public Geometry setMaterial(Material material) {
        this.material = material;
        return this;
    }
}
