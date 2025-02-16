package geometries;

import org.junit.jupiter.api.Test;
import primitives.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Tube class.
 */
class TubeTest {

    /**
     * Test case for constructing a tube.
     * This is a placeholder test. It should be implemented to test the Tube constructor.
     */
    @Test
    void testTube() {
        // TODO: Implement test for Tube constructor
    }

    /**
     * Test case for getting the normal vector of a tube at a given point.
     * This is a placeholder test. It should be implemented to test the getNormal method.
     */
    @Test
    void getNormal() {
        Tube tube = new Tube(2.0, new Ray(new Point(1, 1, 1), new Vector(0, 0, 1)));

        // ============ Equivalence Partitions Tests ==============
        // TC01: Test point on the tube
        assertEquals(new Vector(1, 0, 0), tube.getNormal(new Point(3, 1, 2)), "getNormal() for Tube did not return the expected normal");

        // =============== Boundary Values Tests ==================
        // TC10: Test point at the boundary of the tube (should be a point on the surface)
        assertEquals(new Vector(1, 0, 0), tube.getNormal(new Point(3, 1, 3)), "getNormal() for Tube at boundary did not return the expected normal");
    }

    /**
     * Test method for {@link geometries.Tube#findIntersections(Ray)}.
     */
    @Test
    public void testFindIntersections() {
        Tube tube = new Tube(1.0, new Ray(new Point(0, 0, 0), new Vector(0, 0, 1)));

        // =============== Boundary Values Tests ==================
        // Test case 1: Ray intersects the tube
        List<Intersectable.GeoPoint> intersections1 = tube.findGeoIntersections(new Ray(new Point(0, 0, -1), new Vector(1, 1, 1)));
        assertNotNull(intersections1, "Expected intersections with the tube");
        assertFalse(intersections1.isEmpty(), "Expected intersections with the tube");

        // Test case 2: Ray does not intersect the tube
        List<Intersectable.GeoPoint> intersections2 = tube.findGeoIntersections(new Ray(new Point(10, 10, 10), new Vector(0, 0, 1)));
        assertNull(intersections2, "Expected no intersections with the tube");
    }
}


