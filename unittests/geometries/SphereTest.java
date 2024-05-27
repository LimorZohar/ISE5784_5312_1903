package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Sphere class.
 */
class SphereTest {

    /**
     * A small delta value for floating-point comparison accuracy.
     */
    private final double DELTA = 0.000001;

    /**
     * Test case for the getNormal method of Sphere.
     * This test ensures that the normal vector at a point on the sphere's surface is calculated correctly.
     */
    @Test
    public void testGetNormal() {
        // Create a sphere centered at the origin with a radius of 1
        Sphere sphere = new Sphere(new Point(0, 0, 0), 1.0);
        // Define a point on the sphere's surface
        Point p = new Point(1, 0, 0);
        // Define the expected normal vector at the given point
        Vector expect = new Vector(1, 0, 0).normalize();

        // ============ Equivalence Partitions Tests ==============
        // Test that the normal at the point is as expected
        assertEquals(expect, sphere.getNormal(p), "getNormal() for Sphere did not return the expected normal");
    }
}
