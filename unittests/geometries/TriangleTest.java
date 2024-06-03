package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Triangle class.
 */
class TriangleTest {
    /**
     * Test case for getting the normal vector of a triangle.
     */
    @Test
    void testGetNormal() {
        // Arrange
        Triangle triangle = new Triangle(new Point(0, 0, 0), new Point(1, 0, 0),
                new Point(0, 1, 0));

        // ============ Equivalence Partitions Tests ==============

        // TC01: Correct normal vector calculation
        assertEquals(new Vector(0, 0, 1), triangle.getNormal(new Point(0, 0, 0)),
                "getNormal() did not return the expected normal vector.");
    }
}
