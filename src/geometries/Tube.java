package geometries;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

import primitives.Ray;
import primitives.Point;
import primitives.Vector;

import java.util.List;

/**
 * Represents a tube in three-dimensional space.
 * A tube is defined by its radius and a central axis represented by a ray.
 */
public class Tube extends RadialGeometry {

    /**
     * The axis of the tube defined by a ray.
     */
    final protected Ray axis;

    /**
     * Constructs a new Tube with the specified radius and axis.
     *
     * @param radius The radius of the tube.
     * @param axis   The axis of the tube defined by a ray.
     */
    public Tube(double radius, Ray axis) {
        super(radius);
        this.axis = axis;
    }

    /**
     * Calculates the normal vector to the tube at a given point.
     * <p>
     * The normal vector at a given point on the surface of a tube is calculated
     * by projecting the point onto the axis of the tube, then finding the vector
     * from the axis to the point and normalizing it.
     * <p>
     * The method follows these steps:
     * <ol>
     *   <li>Calculate the vector from the start of the axis (p0) to the given point.</li>
     *   <li>Project this vector onto the direction vector of the axis to find the projection length (proj).</li>
     *   <li>If the projection length is zero, the projection point on the axis is the start of the axis (p0).</li>
     *   <li>Otherwise, calculate the projection point on the axis by scaling the direction vector of the axis by the projection length and adding it to the start of the axis (p0).</li>
     *   <li>Calculate the vector from the projection point on the axis to the given point.</li>
     *   <li>Normalize this vector to obtain the normal vector.</li>
     * </ol>
     *
     * @param point The point on the tube to calculate the normal at.
     * @return The normal vector to the tube at the given point.
     */
    @Override
    public Vector getNormal(Point point) {
        // Step 1: Calculate the vector from the start of the axis (p0) to the given point
        Vector vectorToPoint = point.subtract(axis.getHead());

        // Step 2: Project this vector onto the direction vector of the axis to find the projection length (proj)
        double proj = axis.getDirection().dotProduct(vectorToPoint);

        // Step 3: If the projection length is zero, the projection point on the axis is the start of the axis (p0)
        Point projectionPoint = isZero(proj) ? axis.getHead() : axis.getHead().add(axis.getDirection().scale(proj));

        // Step 4: Calculate the vector from the projection point on the axis to the given point
        Vector normalVector = point.subtract(projectionPoint);

        // Step 5: Normalize this vector to obtain the normal vector
        return normalVector.normalize();
    }
    @Override
    public List<Point> findIntersections(Ray ray) {
        Vector ray_dir = ray.getDirection();
        Vector axis_dir = axis.getDirection();

        // Calculating v_rat_dir = ray_dir - axis_dir * (ray_dir,axis_dir)
        Vector v_ray_dir = ray_dir;
        double d = ray_dir.dotProduct(axis_dir);
        if (!isZero(d)) {
            Vector axis_dir_d = axis_dir.scale(d);
            if (ray_dir.equals(axis_dir_d)) {
                return null;
            }
            v_ray_dir = ray_dir.subtract(axis_dir_d);
        }

        double d1 = 0;
        double d2 = 0;
        if (!ray.getHead().equals(axis.getHead())) {
            Vector dp = ray.getHead().subtract(axis.getHead());
            Vector tempV = dp;
            double dpv0 = dp.dotProduct(axis_dir);
            if (isZero(dpv0)) {
                d1 = v_ray_dir.dotProduct(tempV);
                d2 = tempV.lengthSquared();
            } else {
                Vector v0dpv0 = axis_dir.scale(dpv0);
                if (!dp.equals(v0dpv0)) {
                    tempV = dp.subtract(v0dpv0);
                    d1 = v_ray_dir.dotProduct(tempV);
                    d2 = tempV.lengthSquared();
                }
            }
        }

        // Getting the quadratic equation: a(v_ray_dir)^2 +b(v_ray_dir) + c = 0
        double a = v_ray_dir.lengthSquared();
        double b = 2 * d1;
        double c = alignZero(d2 - radius * radius);

        double squaredDelta = alignZero(b * b - 4 * a * c);
        if (squaredDelta <= 0) {
            return null;
        }

        double delta = Math.sqrt(squaredDelta);
        double t1 = alignZero((-b + delta) / (2 * a));
        double t2 = alignZero((-b - delta) / (2 * a));

        if (t1 > 0 && t2 > 0) {
            return List.of(ray.getPoint(t1), ray.getPoint(t2));
        }
        if (t1 > 0) {
            return List.of(ray.getPoint(t1));
        }
        if (t2 > 0) {
            return List.of(ray.getPoint(t2));
        }

        return null;
    }
}
