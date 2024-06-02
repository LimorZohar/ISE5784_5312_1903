package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

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
        Tube tube = new Tube(1.0, new Ray(new Point(0, 0, 0), new Vector(0, 0, 1)));

        // ============ Equivalence Partitions Tests ==============
        // test point on the tube
        assertEquals(new Vector(0, 1, 0), tube.getNormal(new Point(1, 0, 1)),
                "getNormal() for Tube did not return the expected normal");
    }
}
