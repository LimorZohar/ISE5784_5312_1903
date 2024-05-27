package geometries;

import primitives.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlaneTest {
    /**
     * Test case for constructing a plane using three points.
     * Verifies that a plane is correctly constructed using three coplanar points,
     * and that an exception is thrown when attempting to construct a plane with non-coplanar points.
     */
    @Test
    public void testConstructor() {
        // Define three coplanar points
        Point p1 = new Point(0, 0, 0);
        Point p2 = new Point(1, 0, 0);
        Point p3 = new Point(0, 1, 0);
        // Construct a plane with the three points
        Plane plane = new Plane(p1, p2, p3);
        // ============ Equivalence Partitions Tests ==============
        // Verify that the calculated normal is correct
        assertEquals(new Vector(0, 0, 1), plane.getNormal(),
                "ERROR: Incorrect normal vector calculation");
        // =============== Boundary Values Tests ==================
        // Define three non-coplanar points
        Point p4 = new Point(0, 0, 1);
        // Verify that constructing a plane with non-coplanar points throws an exception
        assertThrows(IllegalArgumentException.class, () -> new Plane(p1, p2, p4), "Expected exception when constructing a plane with non-coplanar points");
    }

    /**
     * Test case for getNormal() method of Plane class.
     */
    @Test
    public void testGetNormal() {
        // Define three coplanar points
        Point p1 = new Point(0, 0, 0);
        Point p2 = new Point(1, 0, 0);
        Point p3 = new Point(0, 1, 0);

        // Construct a plane with the three points
        Plane plane = new Plane(p1, p2, p3);

        // Test getNormal() method for a point on the plane
        assertEquals(new Vector(0, 0, 1), plane.getNormal(p1), "Incorrect normal vector for a point on the plane");

        // Test getNormal() method for a different point on the plane
        assertEquals(new Vector(0, 0, 1), plane.getNormal(p2), "Incorrect normal vector for a point on the plane");

        // Test getNormal() method for a third point on the plane
        assertEquals(new Vector(0, 0, 1), plane.getNormal(p3), "Incorrect normal vector for a point on the plane");

        // Define three non-coplanar points
        Point p4 = new Point(0, 0, 1);

        // Check if attempting to calculate normal for a non-coplanar point throws an exception
        assertThrows(IllegalArgumentException.class, () -> plane.getNormal(p4), "Expected exception when calculating normal for a non-coplanar point");
    }

}



