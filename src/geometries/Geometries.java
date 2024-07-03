package geometries;

import primitives.Material;
import primitives.Point;
import primitives.Ray;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Geometries class represents a collection of intersectable geometries.
 */
public class Geometries extends Intersectable {
    /**
     * A list that holds the intersectable geometries.
     */
    private final List<Intersectable> geometries = new LinkedList<>();

    /**
     * The material associated with the geometries.
     */
    private Material material = new Material();

    /**
     * Constructs an empty Geometries object.
     */
    public Geometries() {
    }

    /**
     * Constructs a Geometries object with a given array of intersectable geometries.
     *
     * @param geometries An array of intersectable geometries to add to the collection.
     */
    public Geometries(Intersectable... geometries) {
        add(geometries);
    }

    /**
     * Returns the material of the geometries.
     *
     * @return The material of the geometries.
     */
    public Material getMaterial() {
        return material;
    }

    /**
     * Sets the material of the geometries.
     *
     * @param material The material to set.
     * @return The current Geometries object for method chaining.
     */
    public Geometries setMaterial(Material material) {
        this.material = material;
        return this;
    }

    /**
     * Adds an array of intersectable geometries to the collection.
     *
     * @param geometries An array of intersectable geometries to add.
     */
    public void add(Intersectable... geometries) {
        Collections.addAll(this.geometries, geometries);
    }

    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        // Returns the list of the geometries that intersect with the ray (input)
        List<GeoPoint> pointList = null;
        for (Intersectable item : geometries) {
            List<GeoPoint> itemPointList = item.findGeoIntersections(ray);
            if (itemPointList != null) {
                if (pointList == null)
                    pointList = new LinkedList<>(itemPointList);
                else
                    pointList.addAll(itemPointList);
            }
        }
        return pointList;
    }

}
