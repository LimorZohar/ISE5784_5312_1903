package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;

/**
 * Represents a cylinder in three-dimensional space, extending the Tube class.
 */
public class Cylinder extends Tube {

    /**
     * The height of the cylinder.
     */
    final protected double height;

    /**
     * Constructs a new Cylinder with the specified radius, axis, and height.
     *
     * @param radius The radius of the cylinder.
     * @param axis   The axis of the cylinder defined by a ray.
     * @param height The height of the cylinder.
     */
    public Cylinder(double radius, Ray axis, double height) {
        super(radius, axis);
        this.height = height;
    }

    /**
     * Finds the intersection points between a ray and the cylinder.
     * This includes intersections with the cylindrical surface as well as the top and bottom caps.
     *
     * @param ray The ray to intersect with the cylinder.
     * @return A list of GeoPoint objects representing the intersection points, or null if there are no intersections.
     */
    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        List<GeoPoint> intersections = new LinkedList<>();

        // Find intersections with the tube part
        List<GeoPoint> tubeIntersections = super.findGeoIntersectionsHelper(ray);
        if (tubeIntersections != null) {
            for (GeoPoint gp : tubeIntersections) {
                double t = axis.getDirection().dotProduct(gp.point.subtract(axis.getHead()));
                if (t > 0 && t < height) {
                    intersections.add(gp);
                }
            }
        }

        // Find intersections with the bottom and top caps
        Point p0 = axis.getHead();
        Vector v = axis.getDirection();
        Point p1 = p0.add(v.scale(height));

        Plane bottomCap = new Plane(p0, v);
        Plane topCap = new Plane(p1, v.scale(-1));  // Note the direction change for the top cap

        // Check intersection with bottom cap
        List<GeoPoint> bottomIntersections = bottomCap.findGeoIntersections(ray);
        if (bottomIntersections != null) {
            for (GeoPoint geoPoint : bottomIntersections) {
                if (geoPoint.point.distanceSquared(p0) <= radius * radius) {
                    intersections.add(new GeoPoint(this, geoPoint.point));
                }
            }
        }

        // Check intersection with top cap
        List<GeoPoint> topIntersections = topCap.findGeoIntersections(ray);
        if (topIntersections != null) {
            for (GeoPoint geoPoint : topIntersections) {
                if (geoPoint.point.distanceSquared(p1) <= radius * radius) {
                    intersections.add(new GeoPoint(this, geoPoint.point));
                }
            }
        }

        return intersections.isEmpty() ? null : intersections;
    }
}
