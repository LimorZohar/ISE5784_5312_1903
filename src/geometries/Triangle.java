package geometries;

import primitives.*;

import java.util.List;

import static primitives.Util.*;

/**
 * Represents a triangle in three-dimensional space.
 * Extends the Polygon class.
 */
public class Triangle extends Polygon {

    /**
     * Constructs a triangle with the specified vertices.
     *
     * @param a The first vertex of the triangle.
     * @param b The second vertex of the triangle.
     * @param c The third vertex of the triangle.
     */
    public Triangle(Point a, Point b, Point c) {
        super(a, b, c);
    }

    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        List<Point> intersections = plane.findIntersections(ray);
        //there are no intersection points (or just one intersection)
        if (intersections == null)
            return null;

        Point head = ray.getHead();//the start ray point
        Vector dir = ray.getDirection();

        Vector v1 = vertices.get(0).subtract(head);
        Vector v2 = vertices.get(1).subtract(head);
        double s1 = alignZero(dir.dotProduct(v1.crossProduct(v2)));
        //checks the point is out of triangle
        if (s1 == 0) return null;

        Vector v3 = vertices.get(2).subtract(head);
        double s2 = alignZero(dir.dotProduct(v2.crossProduct(v3)));
        //checks the point is out of triangle
        if (s1 * s2 <= 0) return null;

        double s3 = alignZero(dir.dotProduct(v3.crossProduct(v1)));
        //checks the point is out of triangle
        if (s1 * s3 <= 0) return null;

        return List.of(new GeoPoint(this, intersections.getFirst()));
    }
}
