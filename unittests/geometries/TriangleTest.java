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
    //לתקן
    @Test
    void testGetNormal() {
        // Arrange
        Triangle triangle = new Triangle(new Point(0, 0, 0), new Point(1, 0, 0),
                new Point(0, 1, 0));

        // Act
        Vector normal = triangle.getNormal(new Point(0, 0, 0));

        // Assert
        assertEquals(new Vector(0, 0, 1), normal,
                "getNormal() did not return the expected normal vector.");
    }
}
