package geometries;

import org.junit.jupiter.api.Test;
import primitives.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * A class to test the functionality of the Cylinder class.
 */
public class CylinderTest {
    private double DELTA = 0.00001;

    /**
     * Test case for constructing a Cylinder.
     */
    @Test
    public void testConstructor() {
        double radius = 1.5;
        // Create a direction for the axis
        Ray axis = new Ray(new Point(0, 0, 0), new Vector(0, 0, 1));
        double height = 5.0;
        // Create a cylinder
        Cylinder cylinder = new Cylinder(radius, axis, height);
        // ============ Equivalence Partitions Tests ==============

        // TC01: Check if the cylinder was constructed correctly
        assertEquals(radius, cylinder.radius, DELTA,
                "ERROR: Cylinder radius is not correct");
        assertEquals(axis, cylinder.axis,
                "ERROR: Cylinder axis is not correct");
        assertEquals(height, cylinder.height, DELTA,
                "ERROR: Cylinder height is not correct");
    }
}
