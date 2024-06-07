package geometries;

import primitives.*;

import java.util.List;

/**
 * The Intersectable interface represents geometric objects that can be intersected by a ray.
 */
public interface Intersectable {

    /**
     * Finds the intersection points between the geometric object and a given ray.
     *
     * @param ray The ray to intersect with the geometric object.
     * @return A list of intersection points, or null if there are no intersections.
     */
    List<Point> findIntersections(Ray ray);
}
