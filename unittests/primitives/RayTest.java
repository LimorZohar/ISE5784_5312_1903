package primitives;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for primitives.Ray class.
 */
class RayTest {
    /**
     * Test method for {@link primitives.Ray#getPoint(double)}.
     */
    @Test
    void testGetPoint() {
        Ray ray1 = new Ray(new Point(0, 0, 0), new Vector(1, 0, 0));
        Ray ray2 = new Ray(new Point(0, 0, 0), new Vector(1, 0, 0));
        Ray ray3 = new Ray(new Point(0, 0, 0), new Vector(1, 0, 0));

        // =============== Boundary Values Tests ==================
        // TC11 : Test Negative distance
        assertEquals(new Point(-1, 0, 0), ray1.getPoint(-1), "Negative distance");

        // TC12 : Test Zero distance
        assertEquals(new Point(0, 0, 0), ray2.getPoint(0), "Zero distance");

        // TC13 : Test Positive distance
        assertEquals(new Point(2, 0, 0), ray3.getPoint(2), "Positive distance");
    }

    /**
     * Test method for {@link primitives.Ray#findClosestPoint(List)}.
     * This test checks if the method correctly finds the closest point to the ray's head from a list of points.
     */
    @Test
    void testFindClosestPoint() {
        Ray ray = new Ray(new Point(0, 0, 0), new Vector(1, 0, 0));

        // ============ Equivalence Partitions Tests ==============
        // TC01: Point in the middle is the closest
        List<Point> points = List.of(new Point(5, 0, 0), new Point(2, 0, 0),
                new Point(10, 0, 0));
        assertEquals(new Point(2, 0, 0), ray.findClosestPoint(points),
                "EP: The closest point is in the middle of the list");

        // =============== Boundary Values Tests ==================
        // TC02: Empty list (method should return null)
        points = List.of();
        assertNull(ray.findClosestPoint(points),
                "BVA: The list is empty, method should return null");

        // TC03: First point is the closest
        points = List.of(new Point(1, 0, 0), new Point(5, 0, 0), new Point(10, 0, 0));
        assertEquals(new Point(1, 0, 0), ray.findClosestPoint(points),
                "BVA: The first point is the closest");

        // TC04: Last point is the closest
        points = List.of(new Point(5, 0, 0), new Point(10, 0, 0), new Point(1, 0, 0));
        assertEquals(new Point(1, 0, 0), ray.findClosestPoint(points),
                "BVA: The last point is the closest");
    }
}
