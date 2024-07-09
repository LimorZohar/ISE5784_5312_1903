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

    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        Point head = ray.getHead();
        Vector dir = ray.getDirection();

        if (head.equals(center))
            return List.of(new GeoPoint(this, ray.getPoint(radius)));

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
        return t1 <= 0
                ? List.of(new GeoPoint(this, ray.getPoint(t2)))
                : List.of(new GeoPoint(this, ray.getPoint(t1)), new GeoPoint(this, ray.getPoint(t2)));
    }

    @Override
    public Vector getNormal(Point point) {
        return point.subtract(center).normalize();
    }
}
