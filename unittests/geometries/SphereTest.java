package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import geometries.Intersectable.GeoPoint;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Sphere class.
 */
class SphereTest {

    /**
     * Test case for the getNormal method of Sphere.
     * This test ensures that the normal vector at a point on the sphere's surface is calculated correctly.
     */
    @Test
    public void testGetNormal() {
        // Create a sphere centered at the origin with a radius of 1
        Sphere sphere = new Sphere(new Point(0, 0, 0), 1.0);

        // ============ Equivalence Partitions Tests ==============
        // TC01: Test that the normal at the point is as expected
        assertEquals(new Vector(1, 0, 0).normalize(), sphere.getNormal(new Point(1, 0, 0)), "getNormal() for Sphere did not return the expected normal");
    }

    /**
     * Point on the Z-axis.
     */
    private final Point p001 = new Point(0, 0, 1);

    /**
     * Point on the X-axis.
     */
    private final Point p100 = new Point(1, 0, 0);

    /**
     * Vector along the Z-axis.
     */
    private final Vector v001 = new Vector(0, 0, 1);


    /**
     * Test method for {@link Intersectable#findGeoIntersectionsHelper(Ray)}
     */
    @Test
    public void findIntsersectionsTest() {
        Sphere sphere = new Sphere(new Point(1, 0, 0), 1d);

        // ============ Equivalence Partitions Tests ==============
        Point p1 = new Point(0.0651530771650466, 0.355051025721682, 0);
        Point p2 = new Point(1.53484692283495, 0.844948974278318, 0);

        // TC01: Ray's line is outside the sphere (0 points)
        assertNull(sphere.findGeoIntersections(new Ray(new Point(-1, 0, 0), new Vector(1, 1, 0))),
                "Ray's line out of sphere");

        // TC02: Ray starts before and crosses the sphere (2 points)
        List<GeoPoint> sphereIntersections = sphere.findGeoIntersections(new Ray(new Point(-1, 0, 0), new Vector(3, 1, 0)));

        assertEquals(2, sphereIntersections.size(),
                "Wrong number of points");

        if (sphereIntersections.get(0).point.getX() > sphereIntersections.get(1).point.getX()) {
            sphereIntersections = List.of(sphereIntersections.get(1), sphereIntersections.get(0));
        }
        assertEquals(List.of(new GeoPoint(sphere, p1), new GeoPoint(sphere, p2)),
                sphereIntersections,
                "Ray crosses sphere");

        // TC03: Ray starts inside the sphere (1 point)
        assertEquals(List.of(new GeoPoint(sphere, p2)),
                List.of(sphere.findGeoIntersections(new Ray(new Point(0.5, 0.5, 0), new Vector(3, 1, 0))).get(0)),
                "Ray from inside sphere");

        // TC04: Ray starts after the sphere (0 points)
        assertNull(sphere.findGeoIntersections(new Ray(new Point(2, 1, 0), new Vector(3, 1, 0))),
                "Sphere behind Ray");

        // =============== Boundary Values Tests ==================
        // **** Group: Ray's line crosses the sphere (but not the center)
        // TC05: Ray starts at sphere and goes inside (1 point)
        assertEquals(List.of(new GeoPoint(sphere, new Point(2, 0, 0))),
                List.of(sphere.findGeoIntersections(new Ray(new Point(1, -1, 0), new Vector(1, 1, 0))).getFirst()),
                "Ray from sphere inside");

        // TC06: Ray starts at sphere and goes outside (0 points)
        assertNull(sphere.findGeoIntersections(new Ray(new Point(2, 0, 0), new Vector(1, 1, 0))),
                "Ray from sphere outside");

        // **** Group: Ray's line goes through the center
        // TC07: Ray starts before the sphere (2 points)
        assertEquals(2,
                sphereIntersections.size(),
                "Wrong number of points");
        sphereIntersections = sphere.findGeoIntersections(new Ray(new Point(1, -2, 0), new Vector(0, 1, 0)));

        if (sphereIntersections.get(0).point.getY() > sphereIntersections.get(1).point.getY()) {
            sphereIntersections = List.of(sphereIntersections.get(1), sphereIntersections.get(0));
        }

        assertEquals(List.of(new GeoPoint(sphere, new Point(1, -1, 0)),
                        new GeoPoint(sphere, new Point(1, 1, 0))),
                sphereIntersections,
                "Line through O, ray crosses sphere");

        // TC08: Ray starts at sphere and goes inside (1 point)
        assertEquals(List.of(new GeoPoint(sphere, new Point(1, 1, 0))),
                List.of(sphere.findGeoIntersections(new Ray(new Point(1, -1, 0), new Vector(0, 1, 0))).get(0)),
                "Line through O, ray from and crosses sphere");

        // TC09: Ray starts inside (1 point)
        assertEquals(List.of(new GeoPoint(sphere, new Point(1, 1, 0))),
                List.of(sphere.findGeoIntersections(new Ray(new Point(1, 0.5, 0), new Vector(0, 1, 0))).get(0)),
                "Line through O, ray from inside sphere");

        // TC10: Ray starts at the center (1 point)
        assertEquals(List.of(new GeoPoint(sphere, new Point(1, 1, 0))),
                List.of(sphere.findGeoIntersections(new Ray(new Point(1, 0, 0), new Vector(0, 1, 0))).get(0)),
                "Line through O, ray from O");

        // TC11: Ray starts at sphere and goes outside (0 points)
        assertNull(sphere.findGeoIntersections(new Ray(new Point(1, 1, 0), new Vector(0, 1, 0))),
                "Line through O, ray from sphere outside");

        // TC12: Ray starts after sphere (0 points)
        assertNull(sphere.findGeoIntersections(new Ray(new Point(1, 2, 0), new Vector(0, 1, 0))),
                "Line through O, ray outside sphere");

        // **** Group: Ray's line is tangent to the sphere (all tests 0 points)
        // TC13: Ray starts before the tangent point
        assertNull(sphere.findGeoIntersections(new Ray(new Point(0, 1, 0), new Vector(1, 0, 0))),
                "Tangent line, ray before sphere");

        // TC14: Ray starts at the tangent point
        assertNull(sphere.findGeoIntersections(new Ray(new Point(1, 1, 0), new Vector(1, 0, 0))),
                "Tangent line, ray at sphere");

        // TC14: Ray starts after the tangent point
        assertNull(sphere.findGeoIntersections(new Ray(new Point(2, 1, 0), new Vector(1, 0, 0))),
                "Tangent line, ray after sphere");

        // **** Group: Special cases
        // TC15: Ray's line is outside, ray is orthogonal to ray start to sphere's center line
        assertNull(sphere.findGeoIntersections(new Ray(new Point(-1, 0, 0), new Vector(0, 0, 1))),
                "Ray orthogonal to ray head -> O line");

    }
}