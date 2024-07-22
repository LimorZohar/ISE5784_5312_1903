package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;

import static primitives.Util.alignZero;

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
                double t = alignZero(axis.getDirection().dotProduct(gp.point.subtract(axis.getHead())));
                if (alignZero(t) > 0 && alignZero(t - height) < 0) {
                    intersections.add(gp);
                }
            }
        }

        // Find intersections with the bottom and top caps
        Point p0 = axis.getHead();
        Vector v = axis.getDirection();
        Point p1 = p0.add(v.scale(height));

        intersections.addAll(findCapIntersections(ray, p0, v, true));
        intersections.addAll(findCapIntersections(ray, p1, v.scale(-1), false));

        return intersections.isEmpty() ? null : intersections;
    }

    /**
     * Finds the intersection points between a ray and one of the cylinder's caps.
     *
     * @param ray         The ray to intersect with the cap.
     * @param capCenter   The center point of the cap.
     * @param normal      The normal vector of the cap plane.
     * @param isBottomCap A boolean indicating whether this is the bottom cap (true) or top cap (false).
     * @return A list of GeoPoint objects representing the intersection points with the cap.
     */
    private List<GeoPoint> findCapIntersections(Ray ray, Point capCenter, Vector normal, boolean isBottomCap) {
        List<GeoPoint> capIntersections = new LinkedList<>();
        Plane cap = new Plane(capCenter, normal);
        List<GeoPoint> planeIntersections = cap.findGeoIntersections(ray);
        if (planeIntersections != null) {
            for (GeoPoint geoPoint : planeIntersections) {
                if (alignZero(geoPoint.point.distanceSquared(capCenter) - radiusSquared) <= 0) {
                    capIntersections.add(new GeoPoint(this, geoPoint.point));
                }
            }
        }
        return capIntersections;
    }
}