package geometries;

import primitives.*;

import java.util.List;
import java.util.Objects;

/**
 * The Intersectable interface represents geometric objects that can be intersected by a ray.
 */
public abstract class Intersectable {

    /**
     * GeoPoint is a static helper class that represents a point associated with a geometry.
     */
    public static class GeoPoint {

        /**
         * The geometry associated with this point.
         */
        public final Geometry geometry;

        /**
         * The point associated with this geometry.
         */
        public final Point point;

        /**
         * Constructs a GeoPoint with the given geometry and point.
         *
         * @param geometry the geometry associated with this point
         * @param point    the point associated with this geometry
         */
        public GeoPoint(Geometry geometry, Point point) {
            this.geometry = geometry;
            this.point = point;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            return obj instanceof GeoPoint gp
                    && Objects.equals(geometry, gp.geometry)
                    && Objects.equals(point, gp.point);
        }

        @Override
        public String toString() {
            return "GeoPoint{" +
                    "geometry=" + geometry +
                    ", point=" + point +
                    '}';
        }
    }

    /**
     * Finds the intersection points between the geometric object and a given ray.
     * The intersection points are represented as GeoPoints, which include the point
     * and the geometry it belongs to.
     *
     * @param ray The ray to intersect with the geometric object.
     * @return A list of GeoPoints representing the intersection points, or null if there are no intersections.
     */
    public final List<GeoPoint> findGeoIntersections(Ray ray) {
        return findGeoIntersectionsHelper(ray);
    }

    /**
     * Helper method to find the intersection points between the geometric object and a given ray.
     * This method is to be implemented by subclasses to provide the actual intersection logic.
     *
     * @param ray The ray to intersect with the geometric object.
     * @return A list of GeoPoints representing the intersection points, or null if there are no intersections.
     */
    protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray);

    /**
     * Finds the intersection points between the geometric object and a given ray.
     *
     * @param ray The ray to intersect with the geometric object.
     * @return A list of intersection points, or null if there are no intersections.
     */
    public final List<Point> findIntersections(Ray ray) {
        var geoList = findGeoIntersections(ray);
        return geoList == null ? null : geoList.stream().map(gp -> gp.point).toList();
    }

}
