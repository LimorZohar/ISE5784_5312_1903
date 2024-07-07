package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.*;

/**
 * Represents a sphere in three-dimensional space.
 */
public class Sphere extends RadialGeometry {

    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        Point head = ray.getHead();
        Vector dir = ray.getDirection();

        if (head.equals(center)) {
            return List.of(new GeoPoint(this, ray.getPoint(radius)));
        }

        Vector cHead = center.subtract(head);
        double tm = dir.dotProduct(cHead);
        double dSquared = cHead.lengthSquared() - tm * tm;
        double thSquared = radiusSquared - dSquared;
        if (alignZero(thSquared) <= 0)
            return null;

        double th = Math.sqrt(thSquared); // t1 < t2 (always)
        double t2 = alignZero(tm + th);
        if (t2 <= 0) return null;

        double t1 = alignZero(tm - th);
        return t1 <= 0 ? List.of(new GeoPoint(this, ray.getPoint(t2))) : List.of(new GeoPoint(this, ray.getPoint(t1)), new GeoPoint(this, ray.getPoint(t2)));
    }

    /**
     * The center point of the sphere.
     */
    final protected Point center;

    /**
     * Constructs a new Sphere with the specified center point and radius.
     *
     * @param center The center point of the sphere.
     * @param radius The radius of the sphere.
     */
    public Sphere(Point center, double radius) {
        super(radius);
        this.center = center;
    }

    /**
     * Calculates the normal vector to the sphere at a given point.
     * <p>
     * The normal vector at a given point on the surface of a sphere is calculated
     * by taking the vector from the center of the sphere to the given point and normalizing it.
     *
     * @param point The point on the sphere to calculate the normal at.
     * @return The normal vector to the sphere at the given point.
     */
    @Override
    public Vector getNormal(Point point) {
        return point.subtract(center).normalize();
    }
}
