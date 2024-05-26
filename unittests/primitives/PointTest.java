package primitives;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PointTest {

    @Test
    public void testConstructors() {
        Point p1 = new Point(1, 2, 3);
        assertEquals(new Double3(1, 2, 3), p1.xyz, "Point should be constructed with correct coordinates");

        Double3 d3 = new Double3(4, 5, 6);
        Point p2 = new Point(d3);
        assertEquals(d3, p2.xyz, "Point should be constructed with correct Double3 coordinates");
    }

    @Test
    public void testSubtract() {
        Point p1 = new Point(1, 2, 3);
        Point p2 = new Point(4, 5, 6);
        Vector v = p1.subtract(p2);
        assertEquals(new Vector(-3, -3, -3), v,
                "testSubtract(): Vector should be the correct result of subtracting two points");

        // Test subtracting the same point, expecting an exception
        Point p3 = new Point(1, 2, 3);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            p1.subtract(p3);
        });
        String expectedMessage = "Result cannot be zero";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage),
                "testSubtract(): Exception message should indicate that result cannot be zero");
    }

    @Test
    public void testAdd() {
        Point p1 = new Point(1, 2, 3);
        Vector v = new Vector(4, 5, 6);
        Point p2 = p1.add(v);
        assertEquals(new Point(5, 7, 9), p2, "Point should be the correct result of adding a vector");
    }

    @Test
    public void testDistanceSquared() {
        Point p1 = new Point(1, 2, 3);
        Point p2 = new Point(4, 5, 6);
        double distSquared = p1.distanceSquared(p2);
        assertEquals(27, distSquared, "Distance squared between points should be correct");

        Point p3 = new Point(1, 2, 3);
        assertEquals(0, p1.distanceSquared(p3), "Distance squared between the same points should be zero");
    }

    @Test
    public void testDistance() {
        Point p1 = new Point(1, 2, 3);
        Point p2 = new Point(4, 5, 6);
        double dist = p1.distance(p2);
        assertEquals(Math.sqrt(27), dist, "Distance between points should be correct");

        Point p3 = new Point(1, 2, 3);
        assertEquals(0, p1.distance(p3), "Distance between the same points should be zero");
    }

}
