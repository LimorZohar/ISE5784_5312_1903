package geometries;

import org.junit.jupiter.api.Test;
import primitives.*;
import primitives.Ray;
import primitives.Vector;
import geometries.Intersectable.GeoPoint;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Triangle class.
 */
class TriangleTest {
    /**
     * Delta value for accuracy when comparing the numbers of type 'double' in
     * assertEquals
     */
    private final double DELTA = 0.00001;

    /**
     * Test case for getting the normal vector of a triangle.
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test the normal vector calculation of a triangle defined by three points
        Triangle triangle = new Triangle(new Point(0, 0, 0), new Point(1, 0, 0),
                new Point(0, 1, 0));
        Vector result = triangle.getNormal(new Point(0, 0, 0));
        // Test that the length of the normal is 1
        assertEquals(1, result.length(), DELTA, "ERROR: the length of the normal is not 1");
        // Check that the normal vector is orthogonal to the plane
        assertEquals(0.0, result.dotProduct(new Vector(1, 0, 0)), DELTA,
                "ERROR: the normal vector is not orthogonal to the vector (1, 0, 0)");
        assertEquals(0.0, result.dotProduct(new Vector(0, 1, 0)), DELTA,
                "ERROR: the normal vector is not orthogonal to the vector (0, 1, 0)");
        // Check that the normal vector is as expected
        assertEquals(new Vector(0, 0, 1), result,
                "getNormal() did not return the expected normal vector.");
    }
//

    /**
     * Test method for {@link geometries.Triangle#findGeoIntersectionsHelper(Ray)}
     */
    @Test
    public void testFindIntersections() {
        Triangle triangle = new Triangle(
                new Point(1, 0, 0),
                new Point(0, 1, 0),
                new Point(0, 0, 1));

        // ============ Equivalence Partitions Tests ==============
        //TC01: Ray intersects the triangle
        List<GeoPoint> result = triangle.findGeoIntersections(new Ray(new Point(-1, -1, -2), new Vector(1, 1, 2)));

        assertEquals(1,
                result.size(),
                "Wrong number of points");
        assertEquals(new GeoPoint(triangle, new Point(0.25d, 0.25d, 0.5d)), result.getFirst(),
                "Ray doesn't intersect the triangle");

        //TC02:Ray outside against vertex
        assertNull(triangle.findGeoIntersections(new Ray(new Point(-2, -2, -2),
                        new Vector(1, 1, 2))),
                "Ray isn't outside against vertex");

        //TC03: Ray outside against edge
        assertNull(triangle.findGeoIntersections(new Ray(new Point(-1, -2, -2),
                        new Vector(1, 1, 2))),
                "Ray isn't outside against edge");

        //TC04:Ray inside the triangle
        assertNull(triangle.findGeoIntersections(new Ray(new Point(0.5, 0.5, 0.2),
                        new Vector(0.5, 0.5, 1.8d))),
                "Ray  isn't inside the triangle");

        // ============ Boundary Values Tests =============
        //TC05: Ray On edge
        assertNull(triangle.findGeoIntersections(new Ray(new Point(0, 0.5d, 0.5d),
                        new Vector(-2.9d, 0.85d, -0.5d))),
                "Ray On edge");

        //TC06: Ray in vertex
        assertNull(triangle.findGeoIntersections(new Ray(new Point(1, 0, 0),
                        new Vector(0.32d, -0.09d, 0))),
                "Ray On vertex");

        //TC07: Ray On edge's continuation
        assertNull(triangle.findGeoIntersections(new Ray(new Point(0, -0.5d, 1.5d),
                        new Vector(-2.31d, -1d, -1.5d))),
                "Ray On edge's continuation");
    }
}
