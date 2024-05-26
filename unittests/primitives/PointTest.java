package primitives;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

/**
 * A class to test the functionality of the Point class.
 */
public class PointTest {

    /**
     * Test the constructors of the Point class.
     */
    @Test
    public void testConstructors() {
        Point p1 = new Point(1, 2, 3);
        assertEquals(new Double3(1, 2, 3), p1.xyz, "testConstructors: Point should be constructed with correct coordinates");

        Double3 d3 = new Double3(4, 5, 6);
        Point p2 = new Point(d3);
        assertEquals(d3, p2.xyz, "testConstructors: Point should be constructed with correct Double3 coordinates");
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
        assertTrue(p2.subtract(p1).equals(v1), "ERROR: (point2 - point1) does not work correctly");
        // =============== Boundary Values Tests ==================
        // Test subtracting the same point, expecting an exception
        Point p3 = new Point(1, 2, 3);
        assertThrows(IllegalArgumentException.class, () ->
                p1.subtract(p3), "ERROR: (point - itself) throws wrong exception");
    }

    /**
     * Test the add method of the Point class.
     */
    @Test
    public void testAdd() {
        Point p1 = new Point(1, 2, 3);
        Vector v1 = new Vector(1, 2, 3);
        Vector v1Opposite = new Vector(-1, -2, -3);
        Point p2 = new Point(2, 4, 6);
        // ============ Equivalence Partitions Tests ==============
        assertTrue(p1.add(v1).equals(p2),
                "ERROR: (point + vector) = other point does not work correctly");
        // =============== Boundary Values Tests ==================
        // Test adding a vector to the point, expecting the center of coordinates
        assertTrue(p1.add(v1Opposite).equals(Point.ZERO),
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
        assertTrue(isZero(p1.distanceSquared(p2) - 9)|| isZero(p2.distanceSquared(p1) - 9),
                "ERROR: squared distance between points is wrong" );
        // =============== Boundary Values Tests ==================
        // Test squared distance between a point and itself, expecting zero
        assertTrue(isZero(p1.distanceSquared(p1)),
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
        assertTrue(isZero(p1.distance(p2) - 3)|| isZero(p2.distance(p1) - 3),
                "ERROR: distance between points is wrong");
        // =============== Boundary Values Tests ==================
        // Test distance between two points, expecting correct value
        assertTrue(isZero(p1.distance(p1)),
                "ERROR: point distance to itself is not zero");
    }

}
