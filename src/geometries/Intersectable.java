package geometries;

import primitives.*;

import java.util.List;
import java.util.Objects;

/**
 * The Intersectable interface represents geometric objects that can be intersected by a ray.
 */
public abstract class Intersectable {

    /**
     * Finds the intersection points between the geometric object and a given ray.
     *
     * @param ray The ray to intersect with the geometric object.
     * @return A list of intersection points, or null if there are no intersections.
     */
    public abstract List<Point> findIntersections(Ray ray);

    /**
     * GeoPoint is a static helper class that represents a point associated with a geometry.
     */
    public static class GeoPoint {

        /**
         * The geometry associated with this point.
         */
        public Geometry geometry;

        /**
         * The point associated with this geometry.
         */
        public Point point;

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
            if (obj == null || getClass() != obj.getClass()) return false;
            GeoPoint geoPoint = (GeoPoint) obj;
            return geometry == geoPoint.geometry && Objects.equals(point, geoPoint.point);
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
     * Finds the GeoIntersection points between the geometric object and a given ray.
     *
     * @param ray The ray to intersect with the geometric object.
     * @return A list of GeoPoints, or null if there are no intersections.
     */
    public List<GeoPoint> findGeoIntersections(Ray ray) {
        return findGeoIntersectionsHelper(ray);
    }

    /**
     * Helper method to find the GeoIntersection points between the geometric object and a given ray.
     *
     * @param ray The ray to intersect with the geometric object.
     * @return A list of GeoPoints, or null if there are no intersections.
     */
    protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray);
}
