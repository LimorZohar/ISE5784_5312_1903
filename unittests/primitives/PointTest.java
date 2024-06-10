package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.*;

/**
 * A class to test the functionality of the Point class.
 */
public class PointTest {
    /**
     * A small delta value for comparing floating-point numbers.
     */
    private static final double DELTA = 0.00001;

    /**
     * Test the constructors of the Point class.
     */
    @Test
    public void testConstructors() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: Construct Point with individual coordinates
        Point p1 = new Point(1, 2, 3);
        assertEquals(new Double3(1, 2, 3), p1.xyz,
                "testConstructors: Point should be constructed with correct coordinates");

        // TC02: Construct Point with a Double3 object
        Double3 d3 = new Double3(4, 5, 6);
        Point p2 = new Point(d3);
        assertEquals(d3, p2.xyz,
                "testConstructors: Point should be constructed with correct Double3 coordinates");
    }

    /**
     * Test the subtract method of the Point class.
     */
    @Test
    public void testSubtract() {
        Point p1 = new Point(1, 2, 3);
        Point p2 = new Point(2, 4, 6);
        Vector v1 = new Vector(1, 2, 3);

        // ============ Equivalence Partitions Tests ==============

        // TC01: Subtract two different points
        assertEquals(v1, p2.subtract(p1), "ERROR: (point2 - point1) does not work correctly");

        // =============== Boundary Values Tests ==================

        // TC10: Subtracting a point from itself
        Point p3 = new Point(1, 2, 3);
        assertThrows(IllegalArgumentException.class, () -> p1.subtract(p3),
                "ERROR: (point - itself) should throw an IllegalArgumentException");
    }

    /**
     * Test the add method of the Point class.
     */
    @Test
    public void testAdd() {
        Point p1 = new Point(1, 2, 3);
        Vector v1 = new Vector(1, 2, 3);

        // ============ Equivalence Partitions Tests ==============

        // TC01: Add a vector to a point
        assertEquals(new Point(2, 4, 6), p1.add(v1),
                "ERROR: (point + vector) = other point does not work correctly");

        // =============== Boundary Values Tests ==================

        // TC10: Add a vector to a point resulting in the origin
        assertEquals(Point.ZERO, p1.add(new Vector(-1, -2, -3)),
                "ERROR: (point + vector) = center of coordinates does not work correctly");
    }

    /**
     * Test the distanceSquared method of the Point class.
     */
    @Test
    public void testDistanceSquared() {
        Point p1 = new Point(1, 2, 3);
        Point p2 = new Point(2, 4, 5);

        // ============ Equivalence Partitions Tests ==============

        // TC01: Calculate squared distance between two different points
        assertEquals(9, p1.distanceSquared(p2), DELTA,
                "ERROR: squared distance between points is wrong");
        assertEquals(9, p2.distanceSquared(p1), DELTA,
                "ERROR: squared distance between points is wrong");

        // =============== Boundary Values Tests ==================

        // TC10: Calculate squared distance between a point and itself
        assertEquals(0, p1.distanceSquared(p1), DELTA,
                "ERROR: point squared distance to itself is not zero");
    }

    /**
     * Test the distance method of the Point class.
     */
    @Test
    public void testDistance() {
        Point p1 = new Point(1, 2, 3);
        Point p2 = new Point(2, 4, 5);

        // ============ Equivalence Partitions Tests ==============

        // TC01: Calculate distance between two different points
        assertEquals(3, p1.distance(p2), DELTA,
                "ERROR: distance between points is wrong");
        assertEquals(3, p2.distance(p1), DELTA,
                "ERROR: distance between points is wrong");

        // =============== Boundary Values Tests ==================

        // TC10: Calculate distance between a point and itself
        assertEquals(0, p1.distance(p1), DELTA,
                "ERROR: point distance to itself is not zero");
    }
}
