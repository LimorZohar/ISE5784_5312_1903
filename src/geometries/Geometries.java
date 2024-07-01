package geometries;

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
     * Adds an array of intersectable geometries to the collection.
     *
     * @param geometries An array of intersectable geometries to add.
     */
    public void add(Intersectable... geometries) {
        Collections.addAll(this.geometries, geometries);
    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        // Returns the list of the geometries that intersect with the ray (input)
        List<Point> pointList = null;
        for (Intersectable item : geometries) {
            List<Point> itemPointList = item.findIntersections(ray);
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
