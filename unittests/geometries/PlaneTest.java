package geometries;

import primitives.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Plane class.
 */
class PlaneTest {
    /**
     * A small delta value for comparing floating-point numbers.
     */
    private final double DELTA = 0.000001;

    /**
     * Test case for constructing a plane using three points.
     */
    @Test
    public void testConstructor() {
        // ============ Boundary Values Tests ==================

        // TC10: Test constructor with two identical points
        assertThrows(IllegalArgumentException.class, () -> new Plane(new Point(1, 1, 1),
                        new Point(1, 1, 1), new Point(0, 0, 0)),
                "Plane constructor does not throw an exception for identical points");

        // TC11: Test constructor with collinear points
        assertThrows(IllegalArgumentException.class, () -> new Plane(new Point(1, 1, 1),
                        new Point(2, 2, 2), new Point(3, 3, 3)),
                "Plane constructor does not throw an exception for collinear points");
    }

    /**
     * Test case for getNormal() method of Plane class.
     */
    @Test
    public void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test the normal vector calculation of a plane defined by three points
        Plane p = new Plane(new Point(0, 0, 1), new Point(0, 1, 0), new Point(1, 0, 0));
        Vector result = p.getNormal(new Point(0, 0, 1));
        // Test that the length of the normal is 1
        assertEquals(1, result.length(), DELTA, "ERROR: the length of the normal is not 1");
        // Check that the normal vector is orthogonal to the plane
        assertEquals(0.0, result.dotProduct(new Vector(0, -1, 1)), DELTA, "ERROR: the normal vector is not orthogonal to the vector (0, -1, 1)");
        assertEquals(0.0, result.dotProduct(new Vector(-1, 1, 0)), DELTA, "ERROR: the normal vector is not orthogonal to the vector (-1, 1, 0)");
    }

    /**
     * Test method for {@link geometries.Plane#findIntersections(Ray)}
     */
    @Test
    public void testFindIntersections() {
        Plane plane = new Plane
                (
                        new Point(1, 0, 0),
                        new Point(0, 1, 0),
                        new Point(0, 0, 1)
                );

        // ============ Equivalence Partitions Tests ==============
        // TC01: Ray intersects the plane (1 points)
        List<Point> result = plane.findIntersections(new Ray(new Point(0, 1, 1),
                new Vector(0, 0, -1)));
        assertEquals(result.size(),
                1,
                "Wrong number of points");
        assertEquals(new Point(0, 1, 0), result.get(0),
                "Ray intersects the plane");

        // TC02: Ray doesn't intersect the plane (0 points)
        assertNull(plane.findGeoIntersectionsHelper(new Ray(new Point(0, 1, 1), new Vector(0, 0, 1))),
                "Ray doesn't intersect the plane");

        // =============== Boundary Values Tests ==================
        //**** Group: Ray is parallel to the plane
        //TC03: Ray is included in the plane
        assertNull(plane.findGeoIntersectionsHelper(new Ray(new Point(0, 0, 1), new Vector(1, -1, 0))),
                "Ray is included in the plane. Ray is parallel to the plane");

        //TC04: Ray isn't included in the plane
        assertNull(plane.findGeoIntersectionsHelper(new Ray(new Point(0, 0, 2), new Vector(1, -1, 0))),
                "Ray isn't included in the plane. Ray is parallel to the plane");

        //**** Group: Special case
        //TC05: Ray begins at the plane (p0 is in the plane, but not the ray)
        assertNull(plane.findGeoIntersectionsHelper(new Ray(new Point(1, 0, 0), new Vector(0, 0, -1))),
                "Ray begins at the plane (p0 is in the plane, but not the ray)");

        //TC06: Ray begins in the plane's reference point
        assertNull(plane.findGeoIntersectionsHelper(new Ray(plane.getCenter(), new Vector(1, 0, 0))),
                "Ray begins in the plane's reference point");
    }
}
